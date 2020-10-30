package android.support.v4.text;

import android.text.SpannableStringBuilder;
import com.google.common.base.Ascii;
import java.util.Locale;

public final class BidiFormatter {
    /* access modifiers changed from: private */
    public static TextDirectionHeuristicCompat a = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    private static final String b = Character.toString(8206);
    private static final String c = Character.toString(8207);
    /* access modifiers changed from: private */
    public static final BidiFormatter d = new BidiFormatter(false, 2, a);
    /* access modifiers changed from: private */
    public static final BidiFormatter e = new BidiFormatter(true, 2, a);
    private final boolean f;
    private final int g;
    private final TextDirectionHeuristicCompat h;

    public static final class Builder {
        private boolean a;
        private int b;
        private TextDirectionHeuristicCompat c;

        public Builder() {
            a(BidiFormatter.b(Locale.getDefault()));
        }

        public Builder(boolean z) {
            a(z);
        }

        public Builder(Locale locale) {
            a(BidiFormatter.b(locale));
        }

        private void a(boolean z) {
            this.a = z;
            this.c = BidiFormatter.a;
            this.b = 2;
        }

        public Builder stereoReset(boolean z) {
            if (z) {
                this.b |= 2;
            } else {
                this.b &= -3;
            }
            return this;
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
            this.c = textDirectionHeuristicCompat;
            return this;
        }

        private static BidiFormatter b(boolean z) {
            return z ? BidiFormatter.e : BidiFormatter.d;
        }

        public BidiFormatter build() {
            if (this.b == 2 && this.c == BidiFormatter.a) {
                return b(this.a);
            }
            return new BidiFormatter(this.a, this.b, this.c);
        }
    }

    static class DirectionalityEstimator {
        private static final byte[] a = new byte[1792];
        private final CharSequence b;
        private final boolean c;
        private final int d;
        private int e;
        private char f;

        static {
            for (int i = 0; i < 1792; i++) {
                a[i] = Character.getDirectionality(i);
            }
        }

        DirectionalityEstimator(CharSequence charSequence, boolean z) {
            this.b = charSequence;
            this.c = z;
            this.d = charSequence.length();
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            this.e = 0;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (this.e < this.d && i == 0) {
                byte c2 = c();
                if (c2 != 9) {
                    switch (c2) {
                        case 0:
                            if (i3 == 0) {
                                return -1;
                            }
                            break;
                        case 1:
                        case 2:
                            if (i3 == 0) {
                                return 1;
                            }
                            break;
                        default:
                            switch (c2) {
                                case 14:
                                case 15:
                                    i3++;
                                    i2 = -1;
                                    continue;
                                case 16:
                                case 17:
                                    i3++;
                                    i2 = 1;
                                    continue;
                                case 18:
                                    i3--;
                                    i2 = 0;
                                    continue;
                                    continue;
                            }
                    }
                    i = i3;
                }
            }
            if (i == 0) {
                return 0;
            }
            if (i2 != 0) {
                return i2;
            }
            while (this.e > 0) {
                switch (d()) {
                    case 14:
                    case 15:
                        if (i != i3) {
                            i3--;
                            break;
                        } else {
                            return -1;
                        }
                    case 16:
                    case 17:
                        if (i != i3) {
                            i3--;
                            break;
                        } else {
                            return 1;
                        }
                    case 18:
                        i3++;
                        break;
                }
            }
            return 0;
        }

        /* access modifiers changed from: 0000 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int b() {
            /*
                r6 = this;
                int r0 = r6.d
                r6.e = r0
                r0 = 0
                r1 = 0
                r2 = 0
            L_0x0007:
                int r3 = r6.e
                if (r3 <= 0) goto L_0x003a
                byte r3 = r6.d()
                r4 = 9
                if (r3 == r4) goto L_0x0007
                r4 = 1
                r5 = -1
                switch(r3) {
                    case 0: goto L_0x0033;
                    case 1: goto L_0x002d;
                    case 2: goto L_0x002d;
                    default: goto L_0x0018;
                }
            L_0x0018:
                switch(r3) {
                    case 14: goto L_0x0027;
                    case 15: goto L_0x0027;
                    case 16: goto L_0x0021;
                    case 17: goto L_0x0021;
                    case 18: goto L_0x001e;
                    default: goto L_0x001b;
                }
            L_0x001b:
                if (r1 != 0) goto L_0x0007
                goto L_0x0038
            L_0x001e:
                int r2 = r2 + 1
                goto L_0x0007
            L_0x0021:
                if (r1 != r2) goto L_0x0024
                return r4
            L_0x0024:
                int r2 = r2 + -1
                goto L_0x0007
            L_0x0027:
                if (r1 != r2) goto L_0x002a
                return r5
            L_0x002a:
                int r2 = r2 + -1
                goto L_0x0007
            L_0x002d:
                if (r2 != 0) goto L_0x0030
                return r4
            L_0x0030:
                if (r1 != 0) goto L_0x0007
                goto L_0x0038
            L_0x0033:
                if (r2 != 0) goto L_0x0036
                return r5
            L_0x0036:
                if (r1 != 0) goto L_0x0007
            L_0x0038:
                r1 = r2
                goto L_0x0007
            L_0x003a:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.text.BidiFormatter.DirectionalityEstimator.b():int");
        }

        private static byte a(char c2) {
            return c2 < 1792 ? a[c2] : Character.getDirectionality(c2);
        }

        /* access modifiers changed from: 0000 */
        public byte c() {
            this.f = this.b.charAt(this.e);
            if (Character.isHighSurrogate(this.f)) {
                int codePointAt = Character.codePointAt(this.b, this.e);
                this.e += Character.charCount(codePointAt);
                return Character.getDirectionality(codePointAt);
            }
            this.e++;
            byte a2 = a(this.f);
            if (this.c) {
                if (this.f == '<') {
                    a2 = e();
                } else if (this.f == '&') {
                    a2 = g();
                }
            }
            return a2;
        }

        /* access modifiers changed from: 0000 */
        public byte d() {
            this.f = this.b.charAt(this.e - 1);
            if (Character.isLowSurrogate(this.f)) {
                int codePointBefore = Character.codePointBefore(this.b, this.e);
                this.e -= Character.charCount(codePointBefore);
                return Character.getDirectionality(codePointBefore);
            }
            this.e--;
            byte a2 = a(this.f);
            if (this.c) {
                if (this.f == '>') {
                    a2 = f();
                } else if (this.f == ';') {
                    a2 = h();
                }
            }
            return a2;
        }

        private byte e() {
            int i = this.e;
            while (this.e < this.d) {
                CharSequence charSequence = this.b;
                int i2 = this.e;
                this.e = i2 + 1;
                this.f = charSequence.charAt(i2);
                if (this.f == '>') {
                    return Ascii.FF;
                }
                if (this.f == '\"' || this.f == '\'') {
                    char c2 = this.f;
                    while (this.e < this.d) {
                        CharSequence charSequence2 = this.b;
                        int i3 = this.e;
                        this.e = i3 + 1;
                        char charAt = charSequence2.charAt(i3);
                        this.f = charAt;
                        if (charAt == c2) {
                            break;
                        }
                    }
                }
            }
            this.e = i;
            this.f = '<';
            return Ascii.CR;
        }

        private byte f() {
            int i = this.e;
            while (this.e > 0) {
                CharSequence charSequence = this.b;
                int i2 = this.e - 1;
                this.e = i2;
                this.f = charSequence.charAt(i2);
                if (this.f == '<') {
                    return Ascii.FF;
                }
                if (this.f == '>') {
                    break;
                } else if (this.f == '\"' || this.f == '\'') {
                    char c2 = this.f;
                    while (this.e > 0) {
                        CharSequence charSequence2 = this.b;
                        int i3 = this.e - 1;
                        this.e = i3;
                        char charAt = charSequence2.charAt(i3);
                        this.f = charAt;
                        if (charAt == c2) {
                            break;
                        }
                    }
                }
            }
            this.e = i;
            this.f = '>';
            return Ascii.CR;
        }

        private byte g() {
            while (this.e < this.d) {
                CharSequence charSequence = this.b;
                int i = this.e;
                this.e = i + 1;
                char charAt = charSequence.charAt(i);
                this.f = charAt;
                if (charAt == ';') {
                    break;
                }
            }
            return Ascii.FF;
        }

        private byte h() {
            int i = this.e;
            while (this.e > 0) {
                CharSequence charSequence = this.b;
                int i2 = this.e - 1;
                this.e = i2;
                this.f = charSequence.charAt(i2);
                if (this.f != '&') {
                    if (this.f == ';') {
                        break;
                    }
                } else {
                    return Ascii.FF;
                }
            }
            this.e = i;
            this.f = ';';
            return Ascii.CR;
        }
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    public static BidiFormatter getInstance(boolean z) {
        return new Builder(z).build();
    }

    public static BidiFormatter getInstance(Locale locale) {
        return new Builder(locale).build();
    }

    private BidiFormatter(boolean z, int i, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.f = z;
        this.g = i;
        this.h = textDirectionHeuristicCompat;
    }

    public boolean isRtlContext() {
        return this.f;
    }

    public boolean getStereoReset() {
        return (this.g & 2) != 0;
    }

    private String a(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean isRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (this.f || (!isRtl && a(charSequence) != 1)) {
            return (!this.f || (isRtl && a(charSequence) != -1)) ? "" : c;
        }
        return b;
    }

    private String b(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean isRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (this.f || (!isRtl && b(charSequence) != 1)) {
            return (!this.f || (isRtl && b(charSequence) != -1)) ? "" : c;
        }
        return b;
    }

    public boolean isRtl(String str) {
        return isRtl((CharSequence) str);
    }

    public boolean isRtl(CharSequence charSequence) {
        return this.h.isRtl(charSequence, 0, charSequence.length());
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z) {
        if (str == null) {
            return null;
        }
        return unicodeWrap((CharSequence) str, textDirectionHeuristicCompat, z).toString();
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z) {
        if (charSequence == null) {
            return null;
        }
        boolean isRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (getStereoReset() && z) {
            spannableStringBuilder.append(b(charSequence, isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
        }
        if (isRtl != this.f) {
            spannableStringBuilder.append(isRtl ? (char) 8235 : 8234);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append(8236);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (z) {
            spannableStringBuilder.append(a(charSequence, isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
        }
        return spannableStringBuilder;
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(str, textDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(charSequence, textDirectionHeuristicCompat, true);
    }

    public String unicodeWrap(String str, boolean z) {
        return unicodeWrap(str, this.h, z);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, boolean z) {
        return unicodeWrap(charSequence, this.h, z);
    }

    public String unicodeWrap(String str) {
        return unicodeWrap(str, this.h, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence) {
        return unicodeWrap(charSequence, this.h, true);
    }

    /* access modifiers changed from: private */
    public static boolean b(Locale locale) {
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == 1;
    }

    private static int a(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).b();
    }

    private static int b(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).a();
    }
}
