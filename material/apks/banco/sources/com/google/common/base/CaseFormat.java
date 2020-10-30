package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.Serializable;
import javax.annotation.Nullable;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;

@GwtCompatible
public enum CaseFormat {
    LOWER_HYPHEN(CharMatcher.is('-'), "-") {
        /* access modifiers changed from: 0000 */
        public String a(String str) {
            return Ascii.toLowerCase(str);
        }

        /* access modifiers changed from: 0000 */
        public String a(CaseFormat caseFormat, String str) {
            if (caseFormat == LOWER_UNDERSCORE) {
                return str.replace('-', '_');
            }
            if (caseFormat == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(str.replace('-', '_'));
            }
            return CaseFormat.super.a(caseFormat, str);
        }
    },
    LOWER_UNDERSCORE(CharMatcher.is('_'), EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) {
        /* access modifiers changed from: 0000 */
        public String a(String str) {
            return Ascii.toLowerCase(str);
        }

        /* access modifiers changed from: 0000 */
        public String a(CaseFormat caseFormat, String str) {
            if (caseFormat == LOWER_HYPHEN) {
                return str.replace('_', '-');
            }
            if (caseFormat == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(str);
            }
            return CaseFormat.super.a(caseFormat, str);
        }
    },
    LOWER_CAMEL(CharMatcher.inRange('A', Matrix.MATRIX_TYPE_ZERO), "") {
        /* access modifiers changed from: 0000 */
        public String a(String str) {
            return CaseFormat.d(str);
        }
    },
    UPPER_CAMEL(CharMatcher.inRange('A', Matrix.MATRIX_TYPE_ZERO), "") {
        /* access modifiers changed from: 0000 */
        public String a(String str) {
            return CaseFormat.d(str);
        }
    },
    UPPER_UNDERSCORE(CharMatcher.is('_'), EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) {
        /* access modifiers changed from: 0000 */
        public String a(String str) {
            return Ascii.toUpperCase(str);
        }

        /* access modifiers changed from: 0000 */
        public String a(CaseFormat caseFormat, String str) {
            if (caseFormat == LOWER_HYPHEN) {
                return Ascii.toLowerCase(str.replace('_', '-'));
            }
            if (caseFormat == LOWER_UNDERSCORE) {
                return Ascii.toLowerCase(str);
            }
            return CaseFormat.super.a(caseFormat, str);
        }
    };
    
    private final CharMatcher a;
    private final String b;

    static final class StringConverter extends Converter<String, String> implements Serializable {
        private static final long serialVersionUID = 0;
        private final CaseFormat a;
        private final CaseFormat b;

        StringConverter(CaseFormat caseFormat, CaseFormat caseFormat2) {
            this.a = (CaseFormat) Preconditions.checkNotNull(caseFormat);
            this.b = (CaseFormat) Preconditions.checkNotNull(caseFormat2);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doForward(String str) {
            return this.a.to(this.b, str);
        }

        /* access modifiers changed from: protected */
        /* renamed from: b */
        public String doBackward(String str) {
            return this.b.to(this.a, str);
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof StringConverter)) {
                return false;
            }
            StringConverter stringConverter = (StringConverter) obj;
            if (this.a.equals(stringConverter.a) && this.b.equals(stringConverter.b)) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(".converterTo(");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract String a(String str);

    private CaseFormat(CharMatcher charMatcher, String str) {
        this.a = charMatcher;
        this.b = str;
    }

    public final String to(CaseFormat caseFormat, String str) {
        Preconditions.checkNotNull(caseFormat);
        Preconditions.checkNotNull(str);
        return caseFormat == this ? str : a(caseFormat, str);
    }

    /* access modifiers changed from: 0000 */
    public String a(CaseFormat caseFormat, String str) {
        int i = 0;
        StringBuilder sb = null;
        int i2 = -1;
        while (true) {
            i2 = this.a.indexIn(str, i2 + 1);
            if (i2 == -1) {
                break;
            }
            if (i == 0) {
                sb = new StringBuilder(str.length() + (this.b.length() * 4));
                sb.append(caseFormat.c(str.substring(i, i2)));
            } else {
                sb.append(caseFormat.a(str.substring(i, i2)));
            }
            sb.append(caseFormat.b);
            i = this.b.length() + i2;
        }
        if (i == 0) {
            return caseFormat.c(str);
        }
        sb.append(caseFormat.a(str.substring(i)));
        return sb.toString();
    }

    public Converter<String, String> converterTo(CaseFormat caseFormat) {
        return new StringConverter(this, caseFormat);
    }

    private String c(String str) {
        return this == LOWER_CAMEL ? Ascii.toLowerCase(str) : a(str);
    }

    /* access modifiers changed from: private */
    public static String d(String str) {
        if (str.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Ascii.toUpperCase(str.charAt(0)));
        sb.append(Ascii.toLowerCase(str.substring(1)));
        return sb.toString();
    }
}
