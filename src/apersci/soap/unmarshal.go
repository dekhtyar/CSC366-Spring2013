package soap

import (
	"bytes"
	"encoding/xml"
	"errors"
	"io"
)

func Unmarshal(r io.Reader, v interface{}) error {
	d := xml.NewDecoder(r)
	depth := 0
	for {
		token, err := d.Token()
		if err != nil {
			return err
		}
		switch t := token.(type) {
		case xml.StartElement:
			switch t.Name.Local {
			case "Envelope":
				if depth != 0 {
					return errors.New("unexpected start of soap envelope")
				}
				depth++
			case "Header":
				if depth != 1 {
					return errors.New("unexpected start of soap header")
				}
				if err := d.Skip(); err != nil {
					return err
				}
			case "Body":
				if depth != 1 {
					return errors.New("unexpected start of soap body")
				}
				if err := d.Decode(v); err != nil {
					return err
				}
				depth++
			default:
				return errors.New("start of unexpected element")
			}
		case xml.EndElement:
			switch t.Name.Local {
			case "Body":
				if depth != 2 {
					return errors.New("unexpected termination of soap body")
				}
				depth--
			case "Envelope":
				if depth != 1 {
					return errors.New("unexpected termination of soap envelope")
				}
				return nil
			default:
				return errors.New("end of unexpected element")
			}
		case xml.CharData:
			if len(bytes.TrimSpace([]byte(t))) > 0 {
				return errors.New("unexpected non-whitespace in soap envelope")
			}
		case xml.Comment, xml.ProcInst, xml.Directive:
			// ignore comments, processing instructions and directives
		}
	}
}
