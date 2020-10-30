package com.google.android.gms.internal;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import org.bouncycastle.asn1.eac.EACTags;

public class zzapy implements Closeable {
    private static final char[] a = ")]}'\n".toCharArray();
    private final Reader b;
    private boolean c = false;
    private final char[] d = new char[1024];
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    /* access modifiers changed from: private */
    public int i = 0;
    private long j;
    private int k;
    private String l;
    private int[] m = new int[32];
    private int n = 0;
    private String[] o;
    private int[] p;

    static {
        zzapd.blQ = new zzapd() {
            public void zzi(zzapy zzapy) {
                int i;
                if (zzapy instanceof zzapo) {
                    ((zzapo) zzapy).bq();
                    return;
                }
                int a = zzapy.i;
                if (a == 0) {
                    a = zzapy.a();
                }
                if (a == 13) {
                    i = 9;
                } else if (a == 12) {
                    i = 8;
                } else if (a == 14) {
                    i = 10;
                } else {
                    String valueOf = String.valueOf(zzapy.bn());
                    int c = zzapy.f();
                    int d = zzapy.g();
                    String path = zzapy.getPath();
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 70 + String.valueOf(path).length());
                    sb.append("Expected a name but was ");
                    sb.append(valueOf);
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(" at line ");
                    sb.append(c);
                    sb.append(" column ");
                    sb.append(d);
                    sb.append(" path ");
                    sb.append(path);
                    throw new IllegalStateException(sb.toString());
                }
                zzapy.i = i;
            }
        };
    }

    public zzapy(Reader reader) {
        int[] iArr = this.m;
        int i2 = this.n;
        this.n = i2 + 1;
        iArr[i2] = 6;
        this.o = new String[32];
        this.p = new int[32];
        if (reader == null) {
            throw new NullPointerException("in == null");
        }
        this.b = reader;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0123  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int a() {
        /*
            r15 = this;
            int[] r0 = r15.m
            int r1 = r15.n
            r2 = 1
            int r1 = r1 - r2
            r0 = r0[r1]
            r1 = 39
            r3 = 34
            r4 = 8
            r5 = 3
            r6 = 93
            r7 = 7
            r8 = 59
            r9 = 44
            r10 = 4
            r11 = 2
            if (r0 != r2) goto L_0x0023
            int[] r12 = r15.m
            int r13 = r15.n
            int r13 = r13 - r2
            r12[r13] = r11
            goto L_0x00b2
        L_0x0023:
            if (r0 != r11) goto L_0x003e
            int r12 = r15.a(r2)
            if (r12 == r9) goto L_0x00b2
            if (r12 == r8) goto L_0x0039
            if (r12 == r6) goto L_0x0036
            java.lang.String r0 = "Unterminated array"
            java.io.IOException r0 = r15.b(r0)
            throw r0
        L_0x0036:
            r15.i = r10
            return r10
        L_0x0039:
            r15.h()
            goto L_0x00b2
        L_0x003e:
            r12 = 5
            if (r0 == r5) goto L_0x012e
            if (r0 != r12) goto L_0x0045
            goto L_0x012e
        L_0x0045:
            if (r0 != r10) goto L_0x007e
            int[] r13 = r15.m
            int r14 = r15.n
            int r14 = r14 - r2
            r13[r14] = r12
            int r12 = r15.a(r2)
            r13 = 58
            if (r12 == r13) goto L_0x00b2
            r13 = 61
            if (r12 == r13) goto L_0x0061
            java.lang.String r0 = "Expected ':'"
            java.io.IOException r0 = r15.b(r0)
            throw r0
        L_0x0061:
            r15.h()
            int r12 = r15.e
            int r13 = r15.f
            if (r12 < r13) goto L_0x0070
            boolean r12 = r15.b(r2)
            if (r12 == 0) goto L_0x00b2
        L_0x0070:
            char[] r12 = r15.d
            int r13 = r15.e
            char r12 = r12[r13]
            r13 = 62
            if (r12 != r13) goto L_0x00b2
            int r12 = r15.e
            int r12 = r12 + r2
            goto L_0x00a5
        L_0x007e:
            r12 = 6
            if (r0 != r12) goto L_0x0090
            boolean r12 = r15.c
            if (r12 == 0) goto L_0x0088
            r15.k()
        L_0x0088:
            int[] r12 = r15.m
            int r13 = r15.n
            int r13 = r13 - r2
            r12[r13] = r7
            goto L_0x00b2
        L_0x0090:
            if (r0 != r7) goto L_0x00a8
            r12 = 0
            int r12 = r15.a(r12)
            r13 = -1
            if (r12 != r13) goto L_0x009f
            r0 = 17
        L_0x009c:
            r15.i = r0
            return r0
        L_0x009f:
            r15.h()
            int r12 = r15.e
            int r12 = r12 - r2
        L_0x00a5:
            r15.e = r12
            goto L_0x00b2
        L_0x00a8:
            if (r0 != r4) goto L_0x00b2
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "JsonReader is closed"
            r0.<init>(r1)
            throw r0
        L_0x00b2:
            int r12 = r15.a(r2)
            if (r12 == r3) goto L_0x0123
            if (r12 == r1) goto L_0x011d
            if (r12 == r9) goto L_0x0106
            if (r12 == r8) goto L_0x0106
            r1 = 91
            if (r12 == r1) goto L_0x0103
            if (r12 == r6) goto L_0x00fe
            r0 = 123(0x7b, float:1.72E-43)
            if (r12 == r0) goto L_0x00fb
            int r0 = r15.e
            int r0 = r0 - r2
            r15.e = r0
            int r0 = r15.n
            if (r0 != r2) goto L_0x00d4
            r15.h()
        L_0x00d4:
            int r0 = r15.b()
            if (r0 == 0) goto L_0x00db
            return r0
        L_0x00db:
            int r0 = r15.c()
            if (r0 == 0) goto L_0x00e2
            return r0
        L_0x00e2:
            char[] r0 = r15.d
            int r1 = r15.e
            char r0 = r0[r1]
            boolean r0 = r15.a(r0)
            if (r0 != 0) goto L_0x00f5
            java.lang.String r0 = "Expected value"
            java.io.IOException r0 = r15.b(r0)
            throw r0
        L_0x00f5:
            r15.h()
            r0 = 10
            goto L_0x009c
        L_0x00fb:
            r15.i = r2
            return r2
        L_0x00fe:
            if (r0 != r2) goto L_0x0106
            r15.i = r10
            return r10
        L_0x0103:
            r15.i = r5
            return r5
        L_0x0106:
            if (r0 == r2) goto L_0x0112
            if (r0 != r11) goto L_0x010b
            goto L_0x0112
        L_0x010b:
            java.lang.String r0 = "Unexpected value"
            java.io.IOException r0 = r15.b(r0)
            throw r0
        L_0x0112:
            r15.h()
            int r0 = r15.e
            int r0 = r0 - r2
            r15.e = r0
            r15.i = r7
            return r7
        L_0x011d:
            r15.h()
            r15.i = r4
            return r4
        L_0x0123:
            int r0 = r15.n
            if (r0 != r2) goto L_0x012a
            r15.h()
        L_0x012a:
            r0 = 9
            goto L_0x009c
        L_0x012e:
            int[] r4 = r15.m
            int r5 = r15.n
            int r5 = r5 - r2
            r4[r5] = r10
            r4 = 125(0x7d, float:1.75E-43)
            if (r0 != r12) goto L_0x0150
            int r5 = r15.a(r2)
            if (r5 == r9) goto L_0x0150
            if (r5 == r8) goto L_0x014d
            if (r5 == r4) goto L_0x014a
            java.lang.String r0 = "Unterminated object"
            java.io.IOException r0 = r15.b(r0)
            throw r0
        L_0x014a:
            r15.i = r11
            return r11
        L_0x014d:
            r15.h()
        L_0x0150:
            int r5 = r15.a(r2)
            if (r5 == r3) goto L_0x0187
            if (r5 == r1) goto L_0x0180
            if (r5 == r4) goto L_0x0174
            r15.h()
            int r0 = r15.e
            int r0 = r0 - r2
            r15.e = r0
            char r0 = (char) r5
            boolean r0 = r15.a(r0)
            if (r0 == 0) goto L_0x016d
            r0 = 14
            goto L_0x009c
        L_0x016d:
            java.lang.String r0 = "Expected name"
            java.io.IOException r0 = r15.b(r0)
            throw r0
        L_0x0174:
            if (r0 == r12) goto L_0x0179
            r15.i = r11
            return r11
        L_0x0179:
            java.lang.String r0 = "Expected name"
            java.io.IOException r0 = r15.b(r0)
            throw r0
        L_0x0180:
            r15.h()
            r0 = 12
            goto L_0x009c
        L_0x0187:
            r0 = 13
            goto L_0x009c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzapy.a():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006d, code lost:
        if (r1 != '/') goto L_0x00b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x006f, code lost:
        r7.e = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0072, code lost:
        if (r4 != r2) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0074, code lost:
        r7.e--;
        r2 = b(2);
        r7.e++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0082, code lost:
        if (r2 != false) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0084, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0085, code lost:
        h();
        r2 = r0[r7.e];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008e, code lost:
        if (r2 == '*') goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0090, code lost:
        if (r2 == '/') goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0092, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0093, code lost:
        r7.e++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009d, code lost:
        r7.e++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a8, code lost:
        if (a("*/") != false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b0, code lost:
        throw b("Unterminated comment");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b8, code lost:
        if (r1 != '#') goto L_0x00c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ba, code lost:
        r7.e = r4;
        h();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c0, code lost:
        r7.e = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c2, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(boolean r8) {
        /*
            r7 = this;
            char[] r0 = r7.d
        L_0x0002:
            int r1 = r7.e
        L_0x0004:
            int r2 = r7.f
        L_0x0006:
            r3 = 1
            if (r1 != r2) goto L_0x004e
            r7.e = r1
            boolean r1 = r7.b(r3)
            if (r1 != 0) goto L_0x004a
            if (r8 == 0) goto L_0x0048
            java.io.EOFException r8 = new java.io.EOFException
            java.lang.String r0 = "End of input at line "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r1 = r7.f()
            int r2 = r7.g()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = java.lang.String.valueOf(r0)
            int r4 = r4.length()
            int r4 = r4 + 30
            r3.<init>(r4)
            r3.append(r0)
            r3.append(r1)
            java.lang.String r0 = " column "
            r3.append(r0)
            r3.append(r2)
            java.lang.String r0 = r3.toString()
            r8.<init>(r0)
            throw r8
        L_0x0048:
            r8 = -1
            return r8
        L_0x004a:
            int r1 = r7.e
            int r2 = r7.f
        L_0x004e:
            int r4 = r1 + 1
            char r1 = r0[r1]
            r5 = 10
            if (r1 != r5) goto L_0x005e
            int r1 = r7.g
            int r1 = r1 + r3
            r7.g = r1
            r7.h = r4
            goto L_0x00c3
        L_0x005e:
            r5 = 32
            if (r1 == r5) goto L_0x00c3
            r5 = 13
            if (r1 == r5) goto L_0x00c3
            r5 = 9
            if (r1 != r5) goto L_0x006b
            goto L_0x00c3
        L_0x006b:
            r5 = 47
            if (r1 != r5) goto L_0x00b6
            r7.e = r4
            r6 = 2
            if (r4 != r2) goto L_0x0085
            int r2 = r7.e
            int r2 = r2 - r3
            r7.e = r2
            boolean r2 = r7.b(r6)
            int r4 = r7.e
            int r4 = r4 + r3
            r7.e = r4
            if (r2 != 0) goto L_0x0085
            return r1
        L_0x0085:
            r7.h()
            int r2 = r7.e
            char r2 = r0[r2]
            r4 = 42
            if (r2 == r4) goto L_0x009d
            if (r2 == r5) goto L_0x0093
            return r1
        L_0x0093:
            int r1 = r7.e
            int r1 = r1 + r3
            r7.e = r1
        L_0x0098:
            r7.i()
            goto L_0x0002
        L_0x009d:
            int r1 = r7.e
            int r1 = r1 + r3
            r7.e = r1
            java.lang.String r1 = "*/"
            boolean r1 = r7.a(r1)
            if (r1 != 0) goto L_0x00b1
            java.lang.String r8 = "Unterminated comment"
            java.io.IOException r8 = r7.b(r8)
            throw r8
        L_0x00b1:
            int r1 = r7.e
            int r1 = r1 + r6
            goto L_0x0004
        L_0x00b6:
            r2 = 35
            if (r1 != r2) goto L_0x00c0
            r7.e = r4
            r7.h()
            goto L_0x0098
        L_0x00c0:
            r7.e = r4
            return r1
        L_0x00c3:
            r1 = r4
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzapy.a(boolean):int");
    }

    private void a(int i2) {
        if (this.n == this.m.length) {
            int[] iArr = new int[(this.n * 2)];
            int[] iArr2 = new int[(this.n * 2)];
            String[] strArr = new String[(this.n * 2)];
            System.arraycopy(this.m, 0, iArr, 0, this.n);
            System.arraycopy(this.p, 0, iArr2, 0, this.n);
            System.arraycopy(this.o, 0, strArr, 0, this.n);
            this.m = iArr;
            this.p = iArr2;
            this.o = strArr;
        }
        int[] iArr3 = this.m;
        int i3 = this.n;
        this.n = i3 + 1;
        iArr3[i3] = i2;
    }

    private boolean a(char c2) {
        switch (c2) {
            case 9:
            case 10:
            case 12:
            case 13:
            case ' ':
            case ',':
            case ':':
            case '[':
            case ']':
            case EACTags.SECURITY_ENVIRONMENT_TEMPLATE /*123*/:
            case EACTags.SECURE_MESSAGING_TEMPLATE /*125*/:
                break;
            case '#':
            case '/':
            case ';':
            case '=':
            case '\\':
                h();
                break;
            default:
                return true;
        }
        return false;
    }

    private boolean a(String str) {
        while (true) {
            int i2 = 0;
            if (this.e + str.length() > this.f && !b(str.length())) {
                return false;
            }
            if (this.d[this.e] == 10) {
                this.g++;
                this.h = this.e + 1;
            } else {
                while (i2 < str.length()) {
                    if (this.d[this.e + i2] == str.charAt(i2)) {
                        i2++;
                    }
                }
                return true;
            }
            this.e++;
        }
    }

    private int b() {
        int i2;
        String str;
        String str2;
        char c2 = this.d[this.e];
        if (c2 == 't' || c2 == 'T') {
            str2 = "true";
            str = "TRUE";
            i2 = 5;
        } else if (c2 == 'f' || c2 == 'F') {
            str2 = Reintento.Reintento_Falso;
            str = "FALSE";
            i2 = 6;
        } else if (c2 != 'n' && c2 != 'N') {
            return 0;
        } else {
            str2 = "null";
            str = "NULL";
            i2 = 7;
        }
        int length = str2.length();
        for (int i3 = 1; i3 < length; i3++) {
            if (this.e + i3 >= this.f && !b(i3 + 1)) {
                return 0;
            }
            char c3 = this.d[this.e + i3];
            if (c3 != str2.charAt(i3) && c3 != str.charAt(i3)) {
                return 0;
            }
        }
        if ((this.e + length < this.f || b(length + 1)) && a(this.d[this.e + length])) {
            return 0;
        }
        this.e += length;
        this.i = i2;
        return i2;
    }

    private IOException b(String str) {
        int f2 = f();
        int g2 = g();
        String path = getPath();
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 45 + String.valueOf(path).length());
        sb.append(str);
        sb.append(" at line ");
        sb.append(f2);
        sb.append(" column ");
        sb.append(g2);
        sb.append(" path ");
        sb.append(path);
        throw new zzaqb(sb.toString());
    }

    private String b(char c2) {
        char[] cArr = this.d;
        StringBuilder sb = new StringBuilder();
        while (true) {
            int i2 = this.e;
            int i3 = this.f;
            int i4 = i2;
            while (true) {
                if (i2 < i3) {
                    int i5 = i2 + 1;
                    char c3 = cArr[i2];
                    if (c3 == c2) {
                        this.e = i5;
                        sb.append(cArr, i4, (i5 - i4) - 1);
                        return sb.toString();
                    } else if (c3 == '\\') {
                        this.e = i5;
                        sb.append(cArr, i4, (i5 - i4) - 1);
                        sb.append(j());
                        break;
                    } else {
                        if (c3 == 10) {
                            this.g++;
                            this.h = i5;
                        }
                        i2 = i5;
                    }
                } else {
                    sb.append(cArr, i4, i2 - i4);
                    this.e = i2;
                    if (!b(1)) {
                        throw b("Unterminated string");
                    }
                }
            }
        }
    }

    private boolean b(int i2) {
        char[] cArr = this.d;
        this.h -= this.e;
        if (this.f != this.e) {
            this.f -= this.e;
            System.arraycopy(cArr, this.e, cArr, 0, this.f);
        } else {
            this.f = 0;
        }
        this.e = 0;
        do {
            int read = this.b.read(cArr, this.f, cArr.length - this.f);
            if (read == -1) {
                return false;
            }
            this.f += read;
            if (this.g == 0 && this.h == 0 && this.f > 0 && cArr[0] == 65279) {
                this.e++;
                this.h++;
                i2++;
            }
        } while (this.f < i2);
        return true;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int c() {
        /*
            r21 = this;
            r0 = r21
            char[] r1 = r0.d
            int r2 = r0.e
            int r3 = r0.f
            r6 = 1
            r7 = 0
            r8 = r3
            r3 = 0
            r9 = 0
            r10 = 1
            r11 = 0
            r13 = 0
        L_0x0011:
            int r14 = r2 + r3
            r15 = 2
            if (r14 != r8) goto L_0x0028
            int r2 = r1.length
            if (r3 != r2) goto L_0x001a
            return r7
        L_0x001a:
            int r2 = r3 + 1
            boolean r2 = r0.b(r2)
            if (r2 != 0) goto L_0x0024
            goto L_0x009d
        L_0x0024:
            int r2 = r0.e
            int r8 = r0.f
        L_0x0028:
            int r14 = r2 + r3
            char r14 = r1[r14]
            r7 = 43
            r4 = 3
            r5 = 5
            if (r14 == r7) goto L_0x00ec
            r7 = 69
            if (r14 == r7) goto L_0x00e0
            r7 = 101(0x65, float:1.42E-43)
            if (r14 == r7) goto L_0x00e0
            switch(r14) {
                case 45: goto L_0x00d3;
                case 46: goto L_0x00cb;
                default: goto L_0x003d;
            }
        L_0x003d:
            r7 = 48
            if (r14 < r7) goto L_0x0097
            r7 = 57
            if (r14 <= r7) goto L_0x0046
            goto L_0x0097
        L_0x0046:
            if (r9 == r6) goto L_0x008d
            if (r9 != 0) goto L_0x004b
            goto L_0x008d
        L_0x004b:
            if (r9 != r15) goto L_0x0078
            r18 = 0
            int r4 = (r11 > r18 ? 1 : (r11 == r18 ? 0 : -1))
            if (r4 != 0) goto L_0x0055
            r4 = 0
            return r4
        L_0x0055:
            r4 = 10
            long r4 = r4 * r11
            int r14 = r14 + -48
            long r14 = (long) r14
            long r16 = r4 - r14
            r4 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r7 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r7 > 0) goto L_0x0072
            int r7 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r7 != 0) goto L_0x0070
            int r4 = (r16 > r11 ? 1 : (r16 == r11 ? 0 : -1))
            if (r4 >= 0) goto L_0x0070
            goto L_0x0072
        L_0x0070:
            r4 = 0
            goto L_0x0073
        L_0x0072:
            r4 = 1
        L_0x0073:
            r4 = r4 & r10
            r10 = r4
            r11 = r16
            goto L_0x0086
        L_0x0078:
            r18 = 0
            if (r9 != r4) goto L_0x0080
            r7 = 0
            r9 = 4
            goto L_0x00f3
        L_0x0080:
            if (r9 == r5) goto L_0x0089
            r4 = 6
            if (r9 != r4) goto L_0x0086
            goto L_0x0089
        L_0x0086:
            r7 = 0
            goto L_0x00f3
        L_0x0089:
            r7 = 0
            r9 = 7
            goto L_0x00f3
        L_0x008d:
            r18 = 0
            int r14 = r14 + -48
            int r4 = -r14
            long r4 = (long) r4
            r11 = r4
            r7 = 0
            r9 = 2
            goto L_0x00f3
        L_0x0097:
            boolean r1 = r0.a(r14)
            if (r1 != 0) goto L_0x00c9
        L_0x009d:
            if (r9 != r15) goto L_0x00b9
            if (r10 == 0) goto L_0x00b9
            r1 = -9223372036854775808
            int r4 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r4 != 0) goto L_0x00a9
            if (r13 == 0) goto L_0x00b9
        L_0x00a9:
            if (r13 == 0) goto L_0x00ac
            goto L_0x00ad
        L_0x00ac:
            long r11 = -r11
        L_0x00ad:
            r0.j = r11
            int r1 = r0.e
            int r1 = r1 + r3
            r0.e = r1
            r1 = 15
        L_0x00b6:
            r0.i = r1
            return r1
        L_0x00b9:
            if (r9 == r15) goto L_0x00c4
            r1 = 4
            if (r9 == r1) goto L_0x00c4
            r1 = 7
            if (r9 != r1) goto L_0x00c2
            goto L_0x00c4
        L_0x00c2:
            r7 = 0
            return r7
        L_0x00c4:
            r0.k = r3
            r1 = 16
            goto L_0x00b6
        L_0x00c9:
            r7 = 0
            return r7
        L_0x00cb:
            r7 = 0
            r18 = 0
            if (r9 != r15) goto L_0x00d2
            r9 = 3
            goto L_0x00f3
        L_0x00d2:
            return r7
        L_0x00d3:
            r4 = 6
            r7 = 0
            r18 = 0
            if (r9 != 0) goto L_0x00dc
            r9 = 1
            r13 = 1
            goto L_0x00f3
        L_0x00dc:
            if (r9 != r5) goto L_0x00df
            goto L_0x00f2
        L_0x00df:
            return r7
        L_0x00e0:
            r7 = 0
            r18 = 0
            if (r9 == r15) goto L_0x00ea
            r4 = 4
            if (r9 != r4) goto L_0x00e9
            goto L_0x00ea
        L_0x00e9:
            return r7
        L_0x00ea:
            r9 = 5
            goto L_0x00f3
        L_0x00ec:
            r4 = 6
            r7 = 0
            r18 = 0
            if (r9 != r5) goto L_0x00f7
        L_0x00f2:
            r9 = 6
        L_0x00f3:
            int r3 = r3 + 1
            goto L_0x0011
        L_0x00f7:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzapy.c():int");
    }

    private void c(char c2) {
        char[] cArr = this.d;
        while (true) {
            int i2 = this.e;
            int i3 = this.f;
            while (true) {
                if (i2 < i3) {
                    int i4 = i2 + 1;
                    char c3 = cArr[i2];
                    if (c3 == c2) {
                        this.e = i4;
                        return;
                    } else if (c3 == '\\') {
                        this.e = i4;
                        j();
                        break;
                    } else {
                        if (c3 == 10) {
                            this.g++;
                            this.h = i4;
                        }
                        i2 = i4;
                    }
                } else {
                    this.e = i2;
                    if (!b(1)) {
                        throw b("Unterminated string");
                    }
                }
            }
        }
    }

    private String d() {
        int i2;
        String str;
        int i3 = 0;
        StringBuilder sb = null;
        while (true) {
            i2 = 0;
            while (true) {
                if (this.e + i2 < this.f) {
                    switch (this.d[this.e + i2]) {
                        case 9:
                        case 10:
                        case 12:
                        case 13:
                        case ' ':
                        case ',':
                        case ':':
                        case '[':
                        case ']':
                        case EACTags.SECURITY_ENVIRONMENT_TEMPLATE /*123*/:
                        case EACTags.SECURE_MESSAGING_TEMPLATE /*125*/:
                            break;
                        case '#':
                        case '/':
                        case ';':
                        case '=':
                        case '\\':
                            h();
                            break;
                        default:
                            i2++;
                            break;
                    }
                } else if (i2 >= this.d.length) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(this.d, this.e, i2);
                    this.e += i2;
                    if (!b(1)) {
                    }
                } else if (b(i2 + 1)) {
                }
            }
        }
        i3 = i2;
        if (sb == null) {
            str = new String(this.d, this.e, i3);
        } else {
            sb.append(this.d, this.e, i3);
            str = sb.toString();
        }
        this.e += i3;
        return str;
    }

    private void e() {
        do {
            int i2 = 0;
            while (this.e + i2 < this.f) {
                switch (this.d[this.e + i2]) {
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case EACTags.SECURITY_ENVIRONMENT_TEMPLATE /*123*/:
                    case EACTags.SECURE_MESSAGING_TEMPLATE /*125*/:
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        h();
                        break;
                    default:
                        i2++;
                }
                this.e += i2;
                return;
            }
            this.e += i2;
        } while (b(1));
    }

    /* access modifiers changed from: private */
    public int f() {
        return this.g + 1;
    }

    /* access modifiers changed from: private */
    public int g() {
        return (this.e - this.h) + 1;
    }

    private void h() {
        if (!this.c) {
            throw b("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void i() {
        char c2;
        do {
            if (this.e >= this.f && !b(1)) {
                break;
            }
            char[] cArr = this.d;
            int i2 = this.e;
            this.e = i2 + 1;
            c2 = cArr[i2];
            if (c2 == 10) {
                this.g++;
                this.h = this.e;
                return;
            }
        } while (c2 != 13);
    }

    private char j() {
        int i2;
        int i3;
        if (this.e != this.f || b(1)) {
            char[] cArr = this.d;
            int i4 = this.e;
            this.e = i4 + 1;
            char c2 = cArr[i4];
            if (c2 == 10) {
                this.g++;
                this.h = this.e;
                return c2;
            } else if (c2 == 'b') {
                return 8;
            } else {
                if (c2 == 'f') {
                    return 12;
                }
                if (c2 == 'n') {
                    return 10;
                }
                if (c2 == 'r') {
                    return TokenParser.CR;
                }
                switch (c2) {
                    case 't':
                        return 9;
                    case 'u':
                        if (this.e + 4 <= this.f || b(4)) {
                            char c3 = 0;
                            int i5 = this.e;
                            int i6 = i5 + 4;
                            while (i5 < i6) {
                                char c4 = this.d[i5];
                                char c5 = (char) (c3 << 4);
                                if (c4 < '0' || c4 > '9') {
                                    if (c4 >= 'a' && c4 <= 'f') {
                                        i2 = c4 - 'a';
                                    } else if (c4 < 'A' || c4 > 'F') {
                                        String str = "\\u";
                                        String valueOf = String.valueOf(new String(this.d, this.e, 4));
                                        throw new NumberFormatException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                                    } else {
                                        i2 = c4 - 'A';
                                    }
                                    i3 = i2 + 10;
                                } else {
                                    i3 = c4 - '0';
                                }
                                c3 = (char) (c5 + i3);
                                i5++;
                            }
                            this.e += 4;
                            return c3;
                        }
                        throw b("Unterminated escape sequence");
                    default:
                        return c2;
                }
            }
        } else {
            throw b("Unterminated escape sequence");
        }
    }

    private void k() {
        a(true);
        this.e--;
        if (this.e + a.length <= this.f || b(a.length)) {
            int i2 = 0;
            while (i2 < a.length) {
                if (this.d[this.e + i2] == a[i2]) {
                    i2++;
                } else {
                    return;
                }
            }
            this.e += a.length;
        }
    }

    public void beginArray() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 3) {
            a(1);
            this.p[this.n - 1] = 0;
            this.i = 0;
            return;
        }
        String valueOf = String.valueOf(bn());
        int f2 = f();
        int g2 = g();
        String path = getPath();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 74 + String.valueOf(path).length());
        sb.append("Expected BEGIN_ARRAY but was ");
        sb.append(valueOf);
        sb.append(" at line ");
        sb.append(f2);
        sb.append(" column ");
        sb.append(g2);
        sb.append(" path ");
        sb.append(path);
        throw new IllegalStateException(sb.toString());
    }

    public void beginObject() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 1) {
            a(3);
            this.i = 0;
            return;
        }
        String valueOf = String.valueOf(bn());
        int f2 = f();
        int g2 = g();
        String path = getPath();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 75 + String.valueOf(path).length());
        sb.append("Expected BEGIN_OBJECT but was ");
        sb.append(valueOf);
        sb.append(" at line ");
        sb.append(f2);
        sb.append(" column ");
        sb.append(g2);
        sb.append(" path ");
        sb.append(path);
        throw new IllegalStateException(sb.toString());
    }

    public zzapz bn() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        switch (i2) {
            case 1:
                return zzapz.BEGIN_OBJECT;
            case 2:
                return zzapz.END_OBJECT;
            case 3:
                return zzapz.BEGIN_ARRAY;
            case 4:
                return zzapz.END_ARRAY;
            case 5:
            case 6:
                return zzapz.BOOLEAN;
            case 7:
                return zzapz.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return zzapz.STRING;
            case 12:
            case 13:
            case 14:
                return zzapz.NAME;
            case 15:
            case 16:
                return zzapz.NUMBER;
            case 17:
                return zzapz.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public void close() {
        this.i = 0;
        this.m[0] = 8;
        this.n = 1;
        this.b.close();
    }

    public void endArray() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 4) {
            this.n--;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            this.i = 0;
            return;
        }
        String valueOf = String.valueOf(bn());
        int f2 = f();
        int g2 = g();
        String path = getPath();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 72 + String.valueOf(path).length());
        sb.append("Expected END_ARRAY but was ");
        sb.append(valueOf);
        sb.append(" at line ");
        sb.append(f2);
        sb.append(" column ");
        sb.append(g2);
        sb.append(" path ");
        sb.append(path);
        throw new IllegalStateException(sb.toString());
    }

    public void endObject() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 2) {
            this.n--;
            this.o[this.n] = null;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            this.i = 0;
            return;
        }
        String valueOf = String.valueOf(bn());
        int f2 = f();
        int g2 = g();
        String path = getPath();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 73 + String.valueOf(path).length());
        sb.append("Expected END_OBJECT but was ");
        sb.append(valueOf);
        sb.append(" at line ");
        sb.append(f2);
        sb.append(" column ");
        sb.append(g2);
        sb.append(" path ");
        sb.append(path);
        throw new IllegalStateException(sb.toString());
    }

    public String getPath() {
        StringBuilder sb = new StringBuilder();
        sb.append('$');
        int i2 = this.n;
        for (int i3 = 0; i3 < i2; i3++) {
            switch (this.m[i3]) {
                case 1:
                case 2:
                    sb.append('[');
                    sb.append(this.p[i3]);
                    sb.append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    sb.append('.');
                    if (this.o[i3] == null) {
                        break;
                    } else {
                        sb.append(this.o[i3]);
                        break;
                    }
            }
        }
        return sb.toString();
    }

    public boolean hasNext() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        return (i2 == 2 || i2 == 4) ? false : true;
    }

    public final boolean isLenient() {
        return this.c;
    }

    public boolean nextBoolean() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 5) {
            this.i = 0;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            return true;
        } else if (i2 == 6) {
            this.i = 0;
            int[] iArr2 = this.p;
            int i4 = this.n - 1;
            iArr2[i4] = iArr2[i4] + 1;
            return false;
        } else {
            String valueOf = String.valueOf(bn());
            int f2 = f();
            int g2 = g();
            String path = getPath();
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 72 + String.valueOf(path).length());
            sb.append("Expected a boolean but was ");
            sb.append(valueOf);
            sb.append(" at line ");
            sb.append(f2);
            sb.append(" column ");
            sb.append(g2);
            sb.append(" path ");
            sb.append(path);
            throw new IllegalStateException(sb.toString());
        }
    }

    public double nextDouble() {
        String b2;
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 15) {
            this.i = 0;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            return (double) this.j;
        }
        if (i2 == 16) {
            this.l = new String(this.d, this.e, this.k);
            this.e += this.k;
        } else {
            if (i2 == 8 || i2 == 9) {
                b2 = b(i2 == 8 ? '\'' : TokenParser.DQUOTE);
            } else if (i2 == 10) {
                b2 = d();
            } else if (i2 != 11) {
                String valueOf = String.valueOf(bn());
                int f2 = f();
                int g2 = g();
                String path = getPath();
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71 + String.valueOf(path).length());
                sb.append("Expected a double but was ");
                sb.append(valueOf);
                sb.append(" at line ");
                sb.append(f2);
                sb.append(" column ");
                sb.append(g2);
                sb.append(" path ");
                sb.append(path);
                throw new IllegalStateException(sb.toString());
            }
            this.l = b2;
        }
        this.i = 11;
        double parseDouble = Double.parseDouble(this.l);
        if (this.c || (!Double.isNaN(parseDouble) && !Double.isInfinite(parseDouble))) {
            this.l = null;
            this.i = 0;
            int[] iArr2 = this.p;
            int i4 = this.n - 1;
            iArr2[i4] = iArr2[i4] + 1;
            return parseDouble;
        }
        int f3 = f();
        int g3 = g();
        String path2 = getPath();
        StringBuilder sb2 = new StringBuilder(String.valueOf(path2).length() + 102);
        sb2.append("JSON forbids NaN and infinities: ");
        sb2.append(parseDouble);
        sb2.append(" at line ");
        sb2.append(f3);
        sb2.append(" column ");
        sb2.append(g3);
        sb2.append(" path ");
        sb2.append(path2);
        throw new zzaqb(sb2.toString());
    }

    public int nextInt() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 15) {
            int i3 = (int) this.j;
            if (this.j != ((long) i3)) {
                long j2 = this.j;
                int f2 = f();
                int g2 = g();
                String path = getPath();
                StringBuilder sb = new StringBuilder(String.valueOf(path).length() + 89);
                sb.append("Expected an int but was ");
                sb.append(j2);
                sb.append(" at line ");
                sb.append(f2);
                sb.append(" column ");
                sb.append(g2);
                sb.append(" path ");
                sb.append(path);
                throw new NumberFormatException(sb.toString());
            }
            this.i = 0;
            int[] iArr = this.p;
            int i4 = this.n - 1;
            iArr[i4] = iArr[i4] + 1;
            return i3;
        }
        if (i2 == 16) {
            this.l = new String(this.d, this.e, this.k);
            this.e += this.k;
        } else if (i2 == 8 || i2 == 9) {
            this.l = b(i2 == 8 ? '\'' : TokenParser.DQUOTE);
            try {
                int parseInt = Integer.parseInt(this.l);
                this.i = 0;
                int[] iArr2 = this.p;
                int i5 = this.n - 1;
                iArr2[i5] = iArr2[i5] + 1;
                return parseInt;
            } catch (NumberFormatException unused) {
            }
        } else {
            String valueOf = String.valueOf(bn());
            int f3 = f();
            int g3 = g();
            String path2 = getPath();
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 69 + String.valueOf(path2).length());
            sb2.append("Expected an int but was ");
            sb2.append(valueOf);
            sb2.append(" at line ");
            sb2.append(f3);
            sb2.append(" column ");
            sb2.append(g3);
            sb2.append(" path ");
            sb2.append(path2);
            throw new IllegalStateException(sb2.toString());
        }
        this.i = 11;
        double parseDouble = Double.parseDouble(this.l);
        int i6 = (int) parseDouble;
        if (((double) i6) != parseDouble) {
            String str = this.l;
            int f4 = f();
            int g4 = g();
            String path3 = getPath();
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 69 + String.valueOf(path3).length());
            sb3.append("Expected an int but was ");
            sb3.append(str);
            sb3.append(" at line ");
            sb3.append(f4);
            sb3.append(" column ");
            sb3.append(g4);
            sb3.append(" path ");
            sb3.append(path3);
            throw new NumberFormatException(sb3.toString());
        }
        this.l = null;
        this.i = 0;
        int[] iArr3 = this.p;
        int i7 = this.n - 1;
        iArr3[i7] = iArr3[i7] + 1;
        return i6;
    }

    public long nextLong() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 15) {
            this.i = 0;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            return this.j;
        }
        if (i2 == 16) {
            this.l = new String(this.d, this.e, this.k);
            this.e += this.k;
        } else if (i2 == 8 || i2 == 9) {
            this.l = b(i2 == 8 ? '\'' : TokenParser.DQUOTE);
            try {
                long parseLong = Long.parseLong(this.l);
                this.i = 0;
                int[] iArr2 = this.p;
                int i4 = this.n - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return parseLong;
            } catch (NumberFormatException unused) {
            }
        } else {
            String valueOf = String.valueOf(bn());
            int f2 = f();
            int g2 = g();
            String path = getPath();
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 69 + String.valueOf(path).length());
            sb.append("Expected a long but was ");
            sb.append(valueOf);
            sb.append(" at line ");
            sb.append(f2);
            sb.append(" column ");
            sb.append(g2);
            sb.append(" path ");
            sb.append(path);
            throw new IllegalStateException(sb.toString());
        }
        this.i = 11;
        double parseDouble = Double.parseDouble(this.l);
        long j2 = (long) parseDouble;
        if (((double) j2) != parseDouble) {
            String str = this.l;
            int f3 = f();
            int g3 = g();
            String path2 = getPath();
            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 69 + String.valueOf(path2).length());
            sb2.append("Expected a long but was ");
            sb2.append(str);
            sb2.append(" at line ");
            sb2.append(f3);
            sb2.append(" column ");
            sb2.append(g3);
            sb2.append(" path ");
            sb2.append(path2);
            throw new NumberFormatException(sb2.toString());
        }
        this.l = null;
        this.i = 0;
        int[] iArr3 = this.p;
        int i5 = this.n - 1;
        iArr3[i5] = iArr3[i5] + 1;
        return j2;
    }

    public String nextName() {
        String str;
        char c2;
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 14) {
            str = d();
        } else {
            if (i2 == 12) {
                c2 = '\'';
            } else if (i2 == 13) {
                c2 = TokenParser.DQUOTE;
            } else {
                String valueOf = String.valueOf(bn());
                int f2 = f();
                int g2 = g();
                String path = getPath();
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 69 + String.valueOf(path).length());
                sb.append("Expected a name but was ");
                sb.append(valueOf);
                sb.append(" at line ");
                sb.append(f2);
                sb.append(" column ");
                sb.append(g2);
                sb.append(" path ");
                sb.append(path);
                throw new IllegalStateException(sb.toString());
            }
            str = b(c2);
        }
        this.i = 0;
        this.o[this.n - 1] = str;
        return str;
    }

    public void nextNull() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 7) {
            this.i = 0;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            return;
        }
        String valueOf = String.valueOf(bn());
        int f2 = f();
        int g2 = g();
        String path = getPath();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 67 + String.valueOf(path).length());
        sb.append("Expected null but was ");
        sb.append(valueOf);
        sb.append(" at line ");
        sb.append(f2);
        sb.append(" column ");
        sb.append(g2);
        sb.append(" path ");
        sb.append(path);
        throw new IllegalStateException(sb.toString());
    }

    public String nextString() {
        String str;
        char c2;
        int i2 = this.i;
        if (i2 == 0) {
            i2 = a();
        }
        if (i2 == 10) {
            str = d();
        } else {
            if (i2 == 8) {
                c2 = '\'';
            } else if (i2 == 9) {
                c2 = TokenParser.DQUOTE;
            } else if (i2 == 11) {
                str = this.l;
                this.l = null;
            } else if (i2 == 15) {
                str = Long.toString(this.j);
            } else if (i2 == 16) {
                str = new String(this.d, this.e, this.k);
                this.e += this.k;
            } else {
                String valueOf = String.valueOf(bn());
                int f2 = f();
                int g2 = g();
                String path = getPath();
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71 + String.valueOf(path).length());
                sb.append("Expected a string but was ");
                sb.append(valueOf);
                sb.append(" at line ");
                sb.append(f2);
                sb.append(" column ");
                sb.append(g2);
                sb.append(" path ");
                sb.append(path);
                throw new IllegalStateException(sb.toString());
            }
            str = b(c2);
        }
        this.i = 0;
        int[] iArr = this.p;
        int i3 = this.n - 1;
        iArr[i3] = iArr[i3] + 1;
        return str;
    }

    public final void setLenient(boolean z) {
        this.c = z;
    }

    public void skipValue() {
        char c2;
        int i2 = 0;
        do {
            int i3 = this.i;
            if (i3 == 0) {
                i3 = a();
            }
            if (i3 == 3) {
                a(1);
            } else if (i3 == 1) {
                a(3);
            } else if (i3 == 4 || i3 == 2) {
                this.n--;
                i2--;
                this.i = 0;
            } else if (i3 == 14 || i3 == 10) {
                e();
                this.i = 0;
            } else {
                if (i3 == 8 || i3 == 12) {
                    c2 = '\'';
                } else if (i3 == 9 || i3 == 13) {
                    c2 = TokenParser.DQUOTE;
                } else {
                    if (i3 == 16) {
                        this.e += this.k;
                    }
                    this.i = 0;
                }
                c(c2);
                this.i = 0;
            }
            i2++;
            this.i = 0;
        } while (i2 != 0);
        int[] iArr = this.p;
        int i4 = this.n - 1;
        iArr[i4] = iArr[i4] + 1;
        this.o[this.n - 1] = "null";
    }

    public String toString() {
        String valueOf = String.valueOf(getClass().getSimpleName());
        int f2 = f();
        int g2 = g();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 39);
        sb.append(valueOf);
        sb.append(" at line ");
        sb.append(f2);
        sb.append(" column ");
        sb.append(g2);
        return sb.toString();
    }
}
