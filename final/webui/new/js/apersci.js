var APERSCI = (function() {
    "use strict";

    function spaces(count) {
        var result = '';
        var pattern = ' ';
        while (count > 0) {
            if (count & 1) {
                result += pattern;
            }
            count >>= 1;
            pattern += pattern;
        }
        return result;
    }

    function makeTag(tag, content, depth) {
        var line = spaces(depth);
        tag = 'v4:' + tag;
        if (content === '') {
            return '';
        } else if (content === null) {
            line += '<'+tag+'/>';
        } else {
            line += '<'+tag+'>';
            if (content.charAt(content.length-1) === '\n') {
                line += '\n';
            }
            line += content;
            if (content.charAt(content.length-1) === '\n') {
                line += spaces(depth);
            }
            line += '</'+tag+'>';
        }
        return line;
    }
    function encodeElement(desc, depth) {
        var result, tag;
        if (_.isPlainObject(desc)) {
            if (_.isUndefined(desc.__value)) {
                result = [];
                _.each(desc, function(val, tag) {
                    tag = makeTag(tag, encodeElement(val, depth+2), depth);
                    if (tag) {
                        result.push(tag);
                    }
                });
                return result.join('\n') + '\n';
            }
            return desc.__value;
        } else if (_.isArray(desc)) {
            result = [];
            _.each(desc, function(val) {
                tag = encodeElement(val, depth);
                if (tag) {
                    result.push(tag);
                }
            });
            return result.join('');
        } else {
            return null;
            console.log(desc);
            throw 'encodeElement failure';
        }
    }

    var soapHeader = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">\n  <soapenv:Header/>\n  <soapenv:Body>\n';
    var soapFooter = '  </soapenv:Body>\n</soapenv:Envelope>\n';

    return {
        encodeSOAP: function(desc) {
            return soapHeader + encodeElement(desc, 4) + soapFooter;
        },
        parseCSV: function(input) {
            var len = input.length;
            var result = [];
            var record = [];
            var field = [];
            var quoted = false;
            var line = 1;
            var column = 0;
            var c;

            function pushField() {
                record.push(field.join(''));
                field = [];
            }

            function errorLocation(message) {
                return 'Error at line '+line+' column '+column+': '+message;
            }

            for (var i = 0; i < len; i++) {
                c = input.charAt(i);

                if (c === '\n') {
                    line++;
                    column = 0;
                } else {
                    column++;
                }

                if (quoted) {
                    if (c === '"') {
                        var next = input.charAt(i+1);
                        switch (next) {
                        case '"':
                            field.push('"');
                            column++;
                            i++;
                            break;
                        case ',':
                        case '\n':
                            quoted = false;
                            break;
                        default:
                            if (next === '\r' && input.charAt(i+2) === '\n') {
                                quoted = false;
                            } else {
                                return errorLocation('end quote before end of quoted field');
                            }
                        }
                    } else {
                        field.push(c);
                    }
                } else {
                    switch (c) {
                    case '"':
                        if (field.length === 0) {
                            quoted = true;
                        } else {
                            return errorLocation('start quote in middle of field');
                        }
                        break;
                    case ',':
                        pushField();
                        break;
                    case '\r':
                        if (input.charAt(i+1) !== '\n') {
                            field.push('\r');
                        }
                        break;
                    case '\n':
                        pushField();
                        result.push(record);
                        record = [];
                        break;
                    default:
                        field.push(c);
                    }
                }
            }
            if (c === '\n') {
                return result;
            } else {
                return errorLocation('no newline at end of file');
            }
        }
    };
})();
