package com.google.android.gms.internal;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class zzaqa implements Closeable, Flushable {
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

    public zzaqa(Writer writer) {
        a(6);
        this.g = ":";
        this.k = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.c = writer;
    }

    private int a() {
        if (this.e != 0) {
            return this.d[this.e - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private zzaqa a(int i2, int i3, String str) {
        int a2 = a();
        if (a2 != i3 && a2 != i2) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.j != null) {
            String str2 = "Dangling name: ";
            String valueOf = String.valueOf(this.j);
            throw new IllegalStateException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else {
            this.e--;
            if (a2 == i3) {
                c();
            }
            this.c.write(str);
            return this;
        }
    }

    private zzaqa a(int i2, String str) {
        a(true);
        a(i2);
        this.c.write(str);
        return this;
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

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001f, code lost:
        if (r2 != false) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0028, code lost:
        throw new java.lang.IllegalStateException("JSON must start with an array or an object.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        r2 = 7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0033, code lost:
        b(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0036, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0043, code lost:
        c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0046, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001d, code lost:
        if (r1.h != false) goto L_0x0029;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(boolean r2) {
        /*
            r1 = this;
            int r0 = r1.a()
            switch(r0) {
                case 1: goto L_0x003f;
                case 2: goto L_0x0037;
                case 3: goto L_0x0007;
                case 4: goto L_0x002b;
                case 5: goto L_0x0007;
                case 6: goto L_0x001b;
                case 7: goto L_0x000f;
                default: goto L_0x0007;
            }
        L_0x0007:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r0 = "Nesting problem."
            r2.<init>(r0)
            throw r2
        L_0x000f:
            boolean r0 = r1.h
            if (r0 != 0) goto L_0x001b
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r0 = "JSON must have only one top-level value."
            r2.<init>(r0)
            throw r2
        L_0x001b:
            boolean r0 = r1.h
            if (r0 != 0) goto L_0x0029
            if (r2 != 0) goto L_0x0029
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r0 = "JSON must start with an array or an object."
            r2.<init>(r0)
            throw r2
        L_0x0029:
            r2 = 7
            goto L_0x0033
        L_0x002b:
            java.io.Writer r2 = r1.c
            java.lang.String r0 = r1.g
            r2.append(r0)
            r2 = 5
        L_0x0033:
            r1.b(r2)
            return
        L_0x0037:
            java.io.Writer r2 = r1.c
            r0 = 44
            r2.append(r0)
            goto L_0x0043
        L_0x003f:
            r2 = 2
            r1.b(r2)
        L_0x0043:
            r1.c()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaqa.a(boolean):void");
    }

    private void b() {
        if (this.j != null) {
            d();
            a(this.j);
            this.j = null;
        }
    }

    private void b(int i2) {
        this.d[this.e - 1] = i2;
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

    public final boolean bJ() {
        return this.i;
    }

    public final boolean bK() {
        return this.k;
    }

    public zzaqa bt() {
        b();
        return a(1, "[");
    }

    public zzaqa bu() {
        return a(1, 2, "]");
    }

    public zzaqa bv() {
        b();
        return a(3, "{");
    }

    public zzaqa bw() {
        return a(3, 5, "}");
    }

    public zzaqa bx() {
        if (this.j != null) {
            if (this.k) {
                b();
            } else {
                this.j = null;
                return this;
            }
        }
        a(false);
        this.c.write("null");
        return this;
    }

    public void close() {
        this.c.close();
        int i2 = this.e;
        if (i2 > 1 || (i2 == 1 && this.d[i2 - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.e = 0;
    }

    public void flush() {
        if (this.e == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.c.flush();
    }

    public boolean isLenient() {
        return this.h;
    }

    public final void setIndent(String str) {
        String str2;
        if (str.length() == 0) {
            this.f = null;
            str2 = ":";
        } else {
            this.f = str;
            str2 = ": ";
        }
        this.g = str2;
    }

    public final void setLenient(boolean z) {
        this.h = z;
    }

    public zzaqa zza(Number number) {
        if (number == null) {
            return bx();
        }
        b();
        String obj = number.toString();
        if (this.h || (!obj.equals("-Infinity") && !obj.equals("Infinity") && !obj.equals("NaN"))) {
            a(false);
            this.c.append(obj);
            return this;
        }
        String valueOf = String.valueOf(number);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 39);
        sb.append("Numeric values must be finite, but was ");
        sb.append(valueOf);
        throw new IllegalArgumentException(sb.toString());
    }

    public zzaqa zzcu(long j2) {
        b();
        a(false);
        this.c.write(Long.toString(j2));
        return this;
    }

    public zzaqa zzdf(boolean z) {
        b();
        a(false);
        this.c.write(z ? "true" : Reintento.Reintento_Falso);
        return this;
    }

    public final void zzdh(boolean z) {
        this.i = z;
    }

    public final void zzdi(boolean z) {
        this.k = z;
    }

    public zzaqa zzus(String str) {
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

    public zzaqa zzut(String str) {
        if (str == null) {
            return bx();
        }
        b();
        a(false);
        a(str);
        return this;
    }
}
