package com.google.gson.stream;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class JsonWriter implements Closeable, Flushable {
    private static final String[] a = new String[128];
    private static final String[] b = ((String[]) a.clone());
    private final Writer c;
    private int[] d = new int[32];
    private int e = 0;
    private String f;
    private String g;
    private boolean h;
    private boolean i;
    private String j;
    private boolean k;

    static {
        for (int i2 = 0; i2 <= 31; i2++) {
            a[i2] = String.format("\\u%04x", new Object[]{Integer.valueOf(i2)});
        }
        a[34] = "\\\"";
        a[92] = "\\\\";
        a[9] = "\\t";
        a[8] = "\\b";
        a[10] = "\\n";
        a[13] = "\\r";
        a[12] = "\\f";
        b[60] = "\\u003c";
        b[62] = "\\u003e";
        b[38] = "\\u0026";
        b[61] = "\\u003d";
        b[39] = "\\u0027";
    }

    public JsonWriter(Writer writer) {
        a(6);
        this.g = ":";
        this.k = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.c = writer;
    }

    public final void setIndent(String str) {
        if (str.length() == 0) {
            this.f = null;
            this.g = ":";
            return;
        }
        this.f = str;
        this.g = ": ";
    }

    public final void setLenient(boolean z) {
        this.h = z;
    }

    public boolean isLenient() {
        return this.h;
    }

    public final void setHtmlSafe(boolean z) {
        this.i = z;
    }

    public final boolean isHtmlSafe() {
        return this.i;
    }

    public final void setSerializeNulls(boolean z) {
        this.k = z;
    }

    public final boolean getSerializeNulls() {
        return this.k;
    }

    public JsonWriter beginArray() {
        b();
        return a(1, "[");
    }

    public JsonWriter endArray() {
        return a(1, 2, "]");
    }

    public JsonWriter beginObject() {
        b();
        return a(3, "{");
    }

    public JsonWriter endObject() {
        return a(3, 5, "}");
    }

    private JsonWriter a(int i2, String str) {
        e();
        a(i2);
        this.c.write(str);
        return this;
    }

    private JsonWriter a(int i2, int i3, String str) {
        int a2 = a();
        if (a2 != i3 && a2 != i2) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.j != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Dangling name: ");
            sb.append(this.j);
            throw new IllegalStateException(sb.toString());
        } else {
            this.e--;
            if (a2 == i3) {
                c();
            }
            this.c.write(str);
            return this;
        }
    }

    private void a(int i2) {
        if (this.e == this.d.length) {
            int[] iArr = new int[(this.e * 2)];
            System.arraycopy(this.d, 0, iArr, 0, this.e);
            this.d = iArr;
        }
        int[] iArr2 = this.d;
        int i3 = this.e;
        this.e = i3 + 1;
        iArr2[i3] = i2;
    }

    private int a() {
        if (this.e != 0) {
            return this.d[this.e - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private void b(int i2) {
        this.d[this.e - 1] = i2;
    }

    public JsonWriter name(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        } else if (this.j != null) {
            throw new IllegalStateException();
        } else if (this.e == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        } else {
            this.j = str;
            return this;
        }
    }

    private void b() {
        if (this.j != null) {
            d();
            a(this.j);
            this.j = null;
        }
    }

    public JsonWriter value(String str) {
        if (str == null) {
            return nullValue();
        }
        b();
        e();
        a(str);
        return this;
    }

    public JsonWriter jsonValue(String str) {
        if (str == null) {
            return nullValue();
        }
        b();
        e();
        this.c.append(str);
        return this;
    }

    public JsonWriter nullValue() {
        if (this.j != null) {
            if (this.k) {
                b();
            } else {
                this.j = null;
                return this;
            }
        }
        e();
        this.c.write("null");
        return this;
    }

    public JsonWriter value(boolean z) {
        b();
        e();
        this.c.write(z ? "true" : Reintento.Reintento_Falso);
        return this;
    }

    public JsonWriter value(Boolean bool) {
        if (bool == null) {
            return nullValue();
        }
        b();
        e();
        this.c.write(bool.booleanValue() ? "true" : Reintento.Reintento_Falso);
        return this;
    }

    public JsonWriter value(double d2) {
        b();
        if (this.h || (!Double.isNaN(d2) && !Double.isInfinite(d2))) {
            e();
            this.c.append(Double.toString(d2));
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Numeric values must be finite, but was ");
        sb.append(d2);
        throw new IllegalArgumentException(sb.toString());
    }

    public JsonWriter value(long j2) {
        b();
        e();
        this.c.write(Long.toString(j2));
        return this;
    }

    public JsonWriter value(Number number) {
        if (number == null) {
            return nullValue();
        }
        b();
        String obj = number.toString();
        if (this.h || (!obj.equals("-Infinity") && !obj.equals("Infinity") && !obj.equals("NaN"))) {
            e();
            this.c.append(obj);
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Numeric values must be finite, but was ");
        sb.append(number);
        throw new IllegalArgumentException(sb.toString());
    }

    public void flush() {
        if (this.e == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.c.flush();
    }

    public void close() {
        this.c.close();
        int i2 = this.e;
        if (i2 > 1 || (i2 == 1 && this.d[i2 - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.e = 0;
    }

    private void a(String str) {
        String str2;
        String[] strArr = this.i ? b : a;
        this.c.write("\"");
        int length = str.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            if (charAt < 128) {
                str2 = strArr[charAt];
                if (str2 == null) {
                }
            } else if (charAt == 8232) {
                str2 = "\\u2028";
            } else if (charAt == 8233) {
                str2 = "\\u2029";
            }
            if (i2 < i3) {
                this.c.write(str, i2, i3 - i2);
            }
            this.c.write(str2);
            i2 = i3 + 1;
        }
        if (i2 < length) {
            this.c.write(str, i2, length - i2);
        }
        this.c.write("\"");
    }

    private void c() {
        if (this.f != null) {
            this.c.write("\n");
            int i2 = this.e;
            for (int i3 = 1; i3 < i2; i3++) {
                this.c.write(this.f);
            }
        }
    }

    private void d() {
        int a2 = a();
        if (a2 == 5) {
            this.c.write(44);
        } else if (a2 != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        c();
        b(4);
    }

    private void e() {
        switch (a()) {
            case 1:
                b(2);
                c();
                return;
            case 2:
                this.c.append(',');
                c();
                return;
            case 4:
                this.c.append(this.g);
                b(5);
                return;
            case 6:
                break;
            case 7:
                if (!this.h) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
                break;
            default:
                throw new IllegalStateException("Nesting problem.");
        }
        b(7);
    }
}
