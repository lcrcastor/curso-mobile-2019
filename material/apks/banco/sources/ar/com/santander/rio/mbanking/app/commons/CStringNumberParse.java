package ar.com.santander.rio.mbanking.app.commons;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CStringNumberParse {
    private final int a = 0;
    private final int b = 1;
    private Locale c;
    private String d;
    private String e;

    class CStringNumberParseException extends Exception {
        public CStringNumberParseException(String str) {
            super(str);
        }

        public CStringNumberParseException(Throwable th) {
            super(th);
        }
    }

    public CStringNumberParse(Locale locale) {
        this.c = locale;
        this.d = a();
        this.e = b();
    }

    public CStringNumberParse() {
    }

    public static CStringNumberParse getInstance(Locale locale) {
        return new CStringNumberParse(locale);
    }

    private String a() {
        return String.valueOf(c().getDecimalFormatSymbols().getDecimalSeparator());
    }

    private String b() {
        return String.valueOf(c().getDecimalFormatSymbols().getGroupingSeparator());
    }

    public void setSymbolFractionalPart(String str) {
        this.d = str;
    }

    public void setSymbolIntegralPart(String str) {
        this.e = str;
    }

    private DecimalFormat c() {
        return (DecimalFormat) NumberFormat.getNumberInstance(this.c);
    }

    public String getIntegralPart(String str) {
        return getIntegralPart(str, true);
    }

    public String getIntegralPart(String str, boolean z) {
        String a2 = a(str, 0);
        if (z) {
            return a2;
        }
        return a2.replace(this.e, "");
    }

    public Integer getIntegralPartNumeric(String str) {
        return Integer.valueOf(Integer.parseInt(getIntegralPart(str)));
    }

    public String getFractionalPart(String str) {
        return a(str, 1);
    }

    public Integer getFractionalPartNumeric(String str) {
        return Integer.valueOf(Integer.parseInt(getFractionalPart(str)));
    }

    private String a(String str, int i) {
        if (str == null) {
            throw new CStringNumberParseException("Number is null");
        } else if (a(str, this.d) > 1) {
            throw new CStringNumberParseException("Incorrect format number. Only one decimal symbol is permitted");
        } else if (i == 1 && !str.contains(this.d)) {
            return "";
        } else {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("".concat("\\"));
                sb.append(this.d);
                return str.split(sb.toString())[i];
            } catch (Exception e2) {
                throw new CStringNumberParseException((Throwable) e2);
            }
        }
    }

    private int a(String str, String str2) {
        return str.length() - str.replace(str2, "").length();
    }
}
