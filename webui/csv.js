var CSV = (function() {
    "use strict";
    return {
        parse: function(input) {
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
