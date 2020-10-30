package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@GwtCompatible
@Beta
public final class CharEscaperBuilder {
    private final Map<Character, String> a = new HashMap();
    private int b = -1;

    static class CharArrayDecorator extends CharEscaper {
        private final char[][] a;
        private final int b;

        CharArrayDecorator(char[][] cArr) {
            this.a = cArr;
            this.b = cArr.length;
        }

        public String escape(String str) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (charAt < this.a.length && this.a[charAt] != null) {
                    return escapeSlow(str, i);
                }
            }
            return str;
        }

        /* access modifiers changed from: protected */
        public char[] escape(char c) {
            if (c < this.b) {
                return this.a[c];
            }
            return null;
        }
    }

    @CanIgnoreReturnValue
    public CharEscaperBuilder addEscape(char c, String str) {
        this.a.put(Character.valueOf(c), Preconditions.checkNotNull(str));
        if (c > this.b) {
            this.b = c;
        }
        return this;
    }

    @CanIgnoreReturnValue
    public CharEscaperBuilder addEscapes(char[] cArr, String str) {
        Preconditions.checkNotNull(str);
        for (char addEscape : cArr) {
            addEscape(addEscape, str);
        }
        return this;
    }

    public char[][] toArray() {
        char[][] cArr = new char[(this.b + 1)][];
        for (Entry entry : this.a.entrySet()) {
            cArr[((Character) entry.getKey()).charValue()] = ((String) entry.getValue()).toCharArray();
        }
        return cArr;
    }

    public Escaper toEscaper() {
        return new CharArrayDecorator(toArray());
    }
}
