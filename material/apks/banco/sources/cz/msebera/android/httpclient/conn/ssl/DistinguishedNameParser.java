package cz.msebera.android.httpclient.conn.ssl;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.security.auth.x500.X500Principal;

public final class DistinguishedNameParser {
    private final String a;
    private final int b = this.a.length();
    private int c;
    private int d;
    private int e;
    private int f;
    private char[] g;

    public DistinguishedNameParser(X500Principal x500Principal) {
        this.a = x500Principal.getName("RFC2253");
    }

    private String a() {
        while (this.c < this.b && this.g[this.c] == ' ') {
            this.c++;
        }
        if (this.c == this.b) {
            return null;
        }
        this.d = this.c;
        this.c++;
        while (this.c < this.b && this.g[this.c] != '=' && this.g[this.c] != ' ') {
            this.c++;
        }
        if (this.c >= this.b) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected end of DN: ");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        }
        this.e = this.c;
        if (this.g[this.c] == ' ') {
            while (this.c < this.b && this.g[this.c] != '=' && this.g[this.c] == ' ') {
                this.c++;
            }
            if (this.g[this.c] != '=' || this.c == this.b) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Unexpected end of DN: ");
                sb2.append(this.a);
                throw new IllegalStateException(sb2.toString());
            }
        }
        this.c++;
        while (this.c < this.b && this.g[this.c] == ' ') {
            this.c++;
        }
        if (this.e - this.d > 4 && this.g[this.d + 3] == '.' && ((this.g[this.d] == 'O' || this.g[this.d] == 'o') && ((this.g[this.d + 1] == 'I' || this.g[this.d + 1] == 'i') && (this.g[this.d + 2] == 'D' || this.g[this.d + 2] == 'd')))) {
            this.d += 4;
        }
        return new String(this.g, this.d, this.e - this.d);
    }

    private String b() {
        this.c++;
        this.d = this.c;
        this.e = this.d;
        while (this.c != this.b) {
            if (this.g[this.c] == '\"') {
                this.c++;
                while (this.c < this.b && this.g[this.c] == ' ') {
                    this.c++;
                }
                return new String(this.g, this.d, this.e - this.d);
            }
            if (this.g[this.c] == '\\') {
                this.g[this.e] = e();
            } else {
                this.g[this.e] = this.g[this.c];
            }
            this.c++;
            this.e++;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected end of DN: ");
        sb.append(this.a);
        throw new IllegalStateException(sb.toString());
    }

    private String c() {
        if (this.c + 4 >= this.b) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected end of DN: ");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        }
        this.d = this.c;
        this.c++;
        while (true) {
            if (this.c == this.b || this.g[this.c] == '+' || this.g[this.c] == ',' || this.g[this.c] == ';') {
                this.e = this.c;
            } else if (this.g[this.c] == ' ') {
                this.e = this.c;
                this.c++;
                while (this.c < this.b && this.g[this.c] == ' ') {
                    this.c++;
                }
            } else {
                if (this.g[this.c] >= 'A' && this.g[this.c] <= 'F') {
                    char[] cArr = this.g;
                    int i = this.c;
                    cArr[i] = (char) (cArr[i] + TokenParser.SP);
                }
                this.c++;
            }
        }
        this.e = this.c;
        int i2 = this.e - this.d;
        if (i2 < 5 || (i2 & 1) == 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unexpected end of DN: ");
            sb2.append(this.a);
            throw new IllegalStateException(sb2.toString());
        }
        byte[] bArr = new byte[(i2 / 2)];
        int i3 = this.d + 1;
        for (int i4 = 0; i4 < bArr.length; i4++) {
            bArr[i4] = (byte) a(i3);
            i3 += 2;
        }
        return new String(this.g, this.d, i2);
    }

    private String d() {
        this.d = this.c;
        this.e = this.c;
        while (this.c < this.b) {
            char c2 = this.g[this.c];
            if (c2 != ' ') {
                if (c2 != ';') {
                    if (c2 != '\\') {
                        switch (c2) {
                            case '+':
                            case ',':
                                break;
                            default:
                                char[] cArr = this.g;
                                int i = this.e;
                                this.e = i + 1;
                                cArr[i] = this.g[this.c];
                                this.c++;
                                continue;
                        }
                    } else {
                        char[] cArr2 = this.g;
                        int i2 = this.e;
                        this.e = i2 + 1;
                        cArr2[i2] = e();
                        this.c++;
                    }
                }
                return new String(this.g, this.d, this.e - this.d);
            }
            this.f = this.e;
            this.c++;
            char[] cArr3 = this.g;
            int i3 = this.e;
            this.e = i3 + 1;
            cArr3[i3] = TokenParser.SP;
            while (this.c < this.b && this.g[this.c] == ' ') {
                char[] cArr4 = this.g;
                int i4 = this.e;
                this.e = i4 + 1;
                cArr4[i4] = TokenParser.SP;
                this.c++;
            }
            if (this.c == this.b || this.g[this.c] == ',' || this.g[this.c] == '+' || this.g[this.c] == ';') {
                return new String(this.g, this.d, this.f - this.d);
            }
        }
        return new String(this.g, this.d, this.e - this.d);
    }

    private char e() {
        this.c++;
        if (this.c == this.b) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected end of DN: ");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        }
        char c2 = this.g[this.c];
        if (!(c2 == ' ' || c2 == '%' || c2 == '\\' || c2 == '_')) {
            switch (c2) {
                case '\"':
                case '#':
                    break;
                default:
                    switch (c2) {
                        case '*':
                        case '+':
                        case ',':
                            break;
                        default:
                            switch (c2) {
                                case ';':
                                case '<':
                                case '=':
                                case '>':
                                    break;
                                default:
                                    return f();
                            }
                    }
            }
        }
        return this.g[this.c];
    }

    private char f() {
        int i;
        int i2;
        int a2 = a(this.c);
        this.c++;
        if (a2 < 128) {
            return (char) a2;
        }
        if (a2 < 192 || a2 > 247) {
            return '?';
        }
        if (a2 <= 223) {
            i2 = a2 & 31;
            i = 1;
        } else if (a2 <= 239) {
            i = 2;
            i2 = a2 & 15;
        } else {
            i = 3;
            i2 = a2 & 7;
        }
        for (int i3 = 0; i3 < i; i3++) {
            this.c++;
            if (this.c == this.b || this.g[this.c] != '\\') {
                return '?';
            }
            this.c++;
            int a3 = a(this.c);
            this.c++;
            if ((a3 & 192) != 128) {
                return '?';
            }
            i2 = (i2 << 6) + (a3 & 63);
        }
        return (char) i2;
    }

    private int a(int i) {
        int i2;
        int i3;
        int i4 = i + 1;
        if (i4 >= this.b) {
            StringBuilder sb = new StringBuilder();
            sb.append("Malformed DN: ");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        }
        char c2 = this.g[i];
        if (c2 >= '0' && c2 <= '9') {
            i2 = c2 - TarjetasConstants.ULT_NUM_AMEX;
        } else if (c2 >= 'a' && c2 <= 'f') {
            i2 = c2 - 'W';
        } else if (c2 < 'A' || c2 > 'F') {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Malformed DN: ");
            sb2.append(this.a);
            throw new IllegalStateException(sb2.toString());
        } else {
            i2 = c2 - '7';
        }
        char c3 = this.g[i4];
        if (c3 >= '0' && c3 <= '9') {
            i3 = c3 - TarjetasConstants.ULT_NUM_AMEX;
        } else if (c3 >= 'a' && c3 <= 'f') {
            i3 = c3 - 'W';
        } else if (c3 < 'A' || c3 > 'F') {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Malformed DN: ");
            sb3.append(this.a);
            throw new IllegalStateException(sb3.toString());
        } else {
            i3 = c3 - '7';
        }
        return (i2 << 4) + i3;
    }

    public String findMostSpecific(String str) {
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = this.a.toCharArray();
        String a2 = a();
        if (a2 == null) {
            return null;
        }
        do {
            String str2 = "";
            if (this.c == this.b) {
                return null;
            }
            switch (this.g[this.c]) {
                case '\"':
                    str2 = b();
                    break;
                case '#':
                    str2 = c();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    str2 = d();
                    break;
            }
            if (str.equalsIgnoreCase(a2)) {
                return str2;
            }
            if (this.c >= this.b) {
                return null;
            }
            if (this.g[this.c] == ',' || this.g[this.c] == ';' || this.g[this.c] == '+') {
                this.c++;
                a2 = a();
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Malformed DN: ");
                sb.append(this.a);
                throw new IllegalStateException(sb.toString());
            }
        } while (a2 != null);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Malformed DN: ");
        sb2.append(this.a);
        throw new IllegalStateException(sb2.toString());
    }

    public List<String> getAllMostSpecificFirst(String str) {
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = this.a.toCharArray();
        List<String> emptyList = Collections.emptyList();
        String a2 = a();
        if (a2 == null) {
            return emptyList;
        }
        while (this.c < this.b) {
            String str2 = "";
            switch (this.g[this.c]) {
                case '\"':
                    str2 = b();
                    break;
                case '#':
                    str2 = c();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    str2 = d();
                    break;
            }
            if (str.equalsIgnoreCase(a2)) {
                if (emptyList.isEmpty()) {
                    emptyList = new ArrayList<>();
                }
                emptyList.add(str2);
            }
            if (this.c >= this.b) {
                return emptyList;
            }
            if (this.g[this.c] == ',' || this.g[this.c] == ';' || this.g[this.c] == '+') {
                this.c++;
                a2 = a();
                if (a2 == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Malformed DN: ");
                    sb.append(this.a);
                    throw new IllegalStateException(sb.toString());
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Malformed DN: ");
                sb2.append(this.a);
                throw new IllegalStateException(sb2.toString());
            }
        }
        return emptyList;
    }
}
