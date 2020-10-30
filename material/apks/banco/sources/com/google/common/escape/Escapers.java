package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
public final class Escapers {
    private static final Escaper a = new CharEscaper() {
        /* access modifiers changed from: protected */
        public char[] escape(char c) {
            return null;
        }

        public String escape(String str) {
            return (String) Preconditions.checkNotNull(str);
        }
    };

    @Beta
    public static final class Builder {
        private final Map<Character, String> a;
        private char b;
        private char c;
        /* access modifiers changed from: private */
        public String d;

        private Builder() {
            this.a = new HashMap();
            this.b = 0;
            this.c = 65535;
            this.d = null;
        }

        @CanIgnoreReturnValue
        public Builder setSafeRange(char c2, char c3) {
            this.b = c2;
            this.c = c3;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUnsafeReplacement(@Nullable String str) {
            this.d = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addEscape(char c2, String str) {
            Preconditions.checkNotNull(str);
            this.a.put(Character.valueOf(c2), str);
            return this;
        }

        public Escaper build() {
            return new ArrayBasedCharEscaper(this.a, this.b, this.c) {
                private final char[] b;

                {
                    this.b = Builder.this.d != null ? Builder.this.d.toCharArray() : null;
                }

                /* access modifiers changed from: protected */
                public char[] escapeUnsafe(char c) {
                    return this.b;
                }
            };
        }
    }

    private Escapers() {
    }

    public static Escaper nullEscaper() {
        return a;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static String computeReplacement(CharEscaper charEscaper, char c) {
        return a(charEscaper.escape(c));
    }

    public static String computeReplacement(UnicodeEscaper unicodeEscaper, int i) {
        return a(unicodeEscaper.escape(i));
    }

    private static String a(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        return new String(cArr);
    }
}
