package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Map;

@GwtCompatible
@Beta
public abstract class ArrayBasedCharEscaper extends CharEscaper {
    private final char[][] a;
    private final int b;
    private final char c;
    private final char d;

    /* access modifiers changed from: protected */
    public abstract char[] escapeUnsafe(char c2);

    protected ArrayBasedCharEscaper(Map<Character, String> map, char c2, char c3) {
        this(ArrayBasedEscaperMap.create(map), c2, c3);
    }

    protected ArrayBasedCharEscaper(ArrayBasedEscaperMap arrayBasedEscaperMap, char c2, char c3) {
        Preconditions.checkNotNull(arrayBasedEscaperMap);
        this.a = arrayBasedEscaperMap.a();
        this.b = this.a.length;
        if (c3 < c2) {
            c3 = 0;
            c2 = 65535;
        }
        this.c = c2;
        this.d = c3;
    }

    public final String escape(String str) {
        Preconditions.checkNotNull(str);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if ((charAt < this.b && this.a[charAt] != null) || charAt > this.d || charAt < this.c) {
                return escapeSlow(str, i);
            }
        }
        return str;
    }

    /* access modifiers changed from: protected */
    public final char[] escape(char c2) {
        if (c2 < this.b) {
            char[] cArr = this.a[c2];
            if (cArr != null) {
                return cArr;
            }
        }
        if (c2 < this.c || c2 > this.d) {
            return escapeUnsafe(c2);
        }
        return null;
    }
}
