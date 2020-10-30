package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WarningValue {
    private static final Pattern h = Pattern.compile("(((\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?\\.)*\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?\\.?)|(\\d+\\.\\d+\\.\\d+\\.\\d+))(\\:\\d*)?");
    private static final Pattern i = Pattern.compile("\"(((Mon|Tue|Wed|Thu|Fri|Sat|Sun), (\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday), (\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Mon|Tue|Wed|Thu|Fri|Sat|Sun) ((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d) (\\d{2}:\\d{2}:\\d{2}) \\d{4}))\"");
    private int a;
    private int b;
    private final String c;
    private int d;
    private String e;
    private String f;
    private Date g;

    private boolean b(char c2) {
        return c2 >= 0 && c2 <= 127;
    }

    private boolean c(char c2) {
        return c2 == 127 || (c2 >= 0 && c2 <= 31);
    }

    private boolean d(char c2) {
        return c2 == '(' || c2 == ')' || c2 == '<' || c2 == '>' || c2 == '@' || c2 == ',' || c2 == ';' || c2 == ':' || c2 == '\\' || c2 == '\"' || c2 == '/' || c2 == '[' || c2 == ']' || c2 == '?' || c2 == '=' || c2 == '{' || c2 == '}' || c2 == ' ' || c2 == 9;
    }

    WarningValue(String str, int i2) {
        this.b = i2;
        this.a = i2;
        this.c = str;
        h();
    }

    public static WarningValue[] a(Header header) {
        ArrayList arrayList = new ArrayList();
        String value = header.getValue();
        int i2 = 0;
        while (i2 < value.length()) {
            try {
                WarningValue warningValue = new WarningValue(value, i2);
                arrayList.add(warningValue);
                i2 = warningValue.a;
            } catch (IllegalArgumentException unused) {
                int indexOf = value.indexOf(44, i2);
                if (indexOf == -1) {
                    break;
                }
                i2 = indexOf + 1;
            }
        }
        return (WarningValue[]) arrayList.toArray(new WarningValue[0]);
    }

    /* access modifiers changed from: protected */
    public void a() {
        while (this.a < this.c.length()) {
            char charAt = this.c.charAt(this.a);
            if (charAt != 9) {
                if (charAt != 13) {
                    if (charAt != ' ') {
                        return;
                    }
                } else if (this.a + 2 < this.c.length() && this.c.charAt(this.a + 1) == 10 && (this.c.charAt(this.a + 2) == ' ' || this.c.charAt(this.a + 2) == 9)) {
                    this.a += 2;
                } else {
                    return;
                }
            }
            this.a++;
        }
    }

    /* access modifiers changed from: protected */
    public void b() {
        if (!e(this.c.charAt(this.a))) {
            k();
        }
        while (this.a < this.c.length() && e(this.c.charAt(this.a))) {
            this.a++;
        }
    }

    private boolean e(char c2) {
        return b(c2) && !c(c2) && !d(c2);
    }

    /* access modifiers changed from: protected */
    public void c() {
        Matcher matcher = h.matcher(this.c.substring(this.a));
        if (!matcher.find()) {
            k();
        }
        if (matcher.start() != 0) {
            k();
        }
        this.a += matcher.end();
    }

    /* access modifiers changed from: protected */
    public void d() {
        int i2 = this.a;
        try {
            c();
            this.e = this.c.substring(i2, this.a);
            a((char) TokenParser.SP);
        } catch (IllegalArgumentException unused) {
            this.a = i2;
            b();
            this.e = this.c.substring(i2, this.a);
            a((char) TokenParser.SP);
        }
    }

    /* access modifiers changed from: protected */
    public void e() {
        if (this.c.charAt(this.a) != '\"') {
            k();
        }
        this.a++;
        boolean z = false;
        while (this.a < this.c.length() && !z) {
            char charAt = this.c.charAt(this.a);
            if (this.a + 1 < this.c.length() && charAt == '\\' && b(this.c.charAt(this.a + 1))) {
                this.a += 2;
            } else if (charAt == '\"') {
                this.a++;
                z = true;
            } else if (charAt == '\"' || c(charAt)) {
                k();
            } else {
                this.a++;
            }
        }
        if (!z) {
            k();
        }
    }

    /* access modifiers changed from: protected */
    public void f() {
        int i2 = this.a;
        e();
        this.f = this.c.substring(i2, this.a);
    }

    /* access modifiers changed from: protected */
    public void g() {
        int i2 = this.a;
        Matcher matcher = i.matcher(this.c.substring(this.a));
        if (!matcher.lookingAt()) {
            k();
        }
        this.a += matcher.end();
        this.g = DateUtils.parseDate(this.c.substring(i2 + 1, this.a - 1));
    }

    /* access modifiers changed from: protected */
    public void h() {
        a();
        i();
        d();
        f();
        if (this.a + 1 < this.c.length() && this.c.charAt(this.a) == ' ' && this.c.charAt(this.a + 1) == '\"') {
            a((char) TokenParser.SP);
            g();
        }
        a();
        if (this.a != this.c.length()) {
            a(',');
        }
    }

    /* access modifiers changed from: protected */
    public void a(char c2) {
        if (this.a + 1 > this.c.length() || c2 != this.c.charAt(this.a)) {
            k();
        }
        this.a++;
    }

    /* access modifiers changed from: protected */
    public void i() {
        if (this.a + 4 > this.c.length() || !Character.isDigit(this.c.charAt(this.a)) || !Character.isDigit(this.c.charAt(this.a + 1)) || !Character.isDigit(this.c.charAt(this.a + 2)) || this.c.charAt(this.a + 3) != ' ') {
            k();
        }
        this.d = Integer.parseInt(this.c.substring(this.a, this.a + 3));
        this.a += 4;
    }

    private void k() {
        String substring = this.c.substring(this.b);
        StringBuilder sb = new StringBuilder();
        sb.append("Bad warn code \"");
        sb.append(substring);
        sb.append("\"");
        throw new IllegalArgumentException(sb.toString());
    }

    public Date j() {
        return this.g;
    }

    public String toString() {
        if (this.g != null) {
            return String.format("%d %s %s \"%s\"", new Object[]{Integer.valueOf(this.d), this.e, this.f, DateUtils.formatDate(this.g)});
        }
        return String.format("%d %s %s", new Object[]{Integer.valueOf(this.d), this.e, this.f});
    }
}
