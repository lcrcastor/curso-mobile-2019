package org.joda.time;

import cz.msebera.android.httpclient.message.TokenParser;

public class IllegalFieldValueException extends IllegalArgumentException {
    private static final long serialVersionUID = 6305711765985447737L;
    private final DateTimeFieldType a;
    private final DurationFieldType b;
    private final String c;
    private final Number d;
    private final String e;
    private final Number f;
    private final Number g;
    private String h;

    private static String a(String str, Number number, Number number2, Number number3, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("Value ");
        sb.append(number);
        sb.append(" for ");
        sb.append(str);
        sb.append(TokenParser.SP);
        if (number2 == null) {
            if (number3 == null) {
                sb.append("is not supported");
            } else {
                sb.append("must not be larger than ");
                sb.append(number3);
            }
        } else if (number3 == null) {
            sb.append("must not be smaller than ");
            sb.append(number2);
        } else {
            sb.append("must be in the range [");
            sb.append(number2);
            sb.append(',');
            sb.append(number3);
            sb.append(']');
        }
        if (str2 != null) {
            sb.append(": ");
            sb.append(str2);
        }
        return sb.toString();
    }

    private static String a(String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Value ");
        if (str2 == null) {
            stringBuffer.append("null");
        } else {
            stringBuffer.append(TokenParser.DQUOTE);
            stringBuffer.append(str2);
            stringBuffer.append(TokenParser.DQUOTE);
        }
        stringBuffer.append(" for ");
        stringBuffer.append(str);
        stringBuffer.append(TokenParser.SP);
        stringBuffer.append("is not supported");
        return stringBuffer.toString();
    }

    public IllegalFieldValueException(DateTimeFieldType dateTimeFieldType, Number number, Number number2, Number number3) {
        super(a(dateTimeFieldType.getName(), number, number2, number3, null));
        this.a = dateTimeFieldType;
        this.b = null;
        this.c = dateTimeFieldType.getName();
        this.d = number;
        this.e = null;
        this.f = number2;
        this.g = number3;
        this.h = super.getMessage();
    }

    public IllegalFieldValueException(DateTimeFieldType dateTimeFieldType, Number number, String str) {
        super(a(dateTimeFieldType.getName(), number, null, null, str));
        this.a = dateTimeFieldType;
        this.b = null;
        this.c = dateTimeFieldType.getName();
        this.d = number;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = super.getMessage();
    }

    public IllegalFieldValueException(DurationFieldType durationFieldType, Number number, Number number2, Number number3) {
        super(a(durationFieldType.getName(), number, number2, number3, null));
        this.a = null;
        this.b = durationFieldType;
        this.c = durationFieldType.getName();
        this.d = number;
        this.e = null;
        this.f = number2;
        this.g = number3;
        this.h = super.getMessage();
    }

    public IllegalFieldValueException(String str, Number number, Number number2, Number number3) {
        super(a(str, number, number2, number3, null));
        this.a = null;
        this.b = null;
        this.c = str;
        this.d = number;
        this.e = null;
        this.f = number2;
        this.g = number3;
        this.h = super.getMessage();
    }

    public IllegalFieldValueException(DateTimeFieldType dateTimeFieldType, String str) {
        super(a(dateTimeFieldType.getName(), str));
        this.a = dateTimeFieldType;
        this.b = null;
        this.c = dateTimeFieldType.getName();
        this.e = str;
        this.d = null;
        this.f = null;
        this.g = null;
        this.h = super.getMessage();
    }

    public IllegalFieldValueException(DurationFieldType durationFieldType, String str) {
        super(a(durationFieldType.getName(), str));
        this.a = null;
        this.b = durationFieldType;
        this.c = durationFieldType.getName();
        this.e = str;
        this.d = null;
        this.f = null;
        this.g = null;
        this.h = super.getMessage();
    }

    public IllegalFieldValueException(String str, String str2) {
        super(a(str, str2));
        this.a = null;
        this.b = null;
        this.c = str;
        this.e = str2;
        this.d = null;
        this.f = null;
        this.g = null;
        this.h = super.getMessage();
    }

    public DateTimeFieldType getDateTimeFieldType() {
        return this.a;
    }

    public DurationFieldType getDurationFieldType() {
        return this.b;
    }

    public String getFieldName() {
        return this.c;
    }

    public Number getIllegalNumberValue() {
        return this.d;
    }

    public String getIllegalStringValue() {
        return this.e;
    }

    public String getIllegalValueAsString() {
        String str = this.e;
        return str == null ? String.valueOf(this.d) : str;
    }

    public Number getLowerBound() {
        return this.f;
    }

    public Number getUpperBound() {
        return this.g;
    }

    public String getMessage() {
        return this.h;
    }

    public void prependMessage(String str) {
        if (this.h == null) {
            this.h = str;
        } else if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append(this.h);
            this.h = sb.toString();
        }
    }
}
