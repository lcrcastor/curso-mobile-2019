package com.google.common.escape;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
public abstract class ArrayBasedUnicodeEscaper extends UnicodeEscaper {
    private final char[][] a;
    private final int b;
    private final int c;
    private final int d;
    private final char e;
    private final char f;

    /* access modifiers changed from: protected */
    public abstract char[] escapeUnsafe(int i);

    protected ArrayBasedUnicodeEscaper(Map<Character, String> map, int i, int i2, @Nullable String str) {
        this(ArrayBasedEscaperMap.create(map), i, i2, str);
    }

    protected ArrayBasedUnicodeEscaper(ArrayBasedEscaperMap arrayBasedEscaperMap, int i, int i2, @Nullable String str) {
        Preconditions.checkNotNull(arrayBasedEscaperMap);
        this.a = arrayBasedEscaperMap.a();
        this.b = this.a.length;
        if (i2 < i) {
            i2 = -1;
            i = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        }
        this.c = i;
        this.d = i2;
        if (i >= 55296) {
            this.e = 65535;
            this.f = 0;
            return;
        }
        this.e = (char) i;
        this.f = (char) Math.min(i2, 55295);
    }

    public final String escape(String str) {
        Preconditions.checkNotNull(str);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if ((charAt < this.b && this.a[charAt] != null) || charAt > this.f || charAt < this.e) {
                return escapeSlow(str, i);
            }
        }
        return str;
    }

    /* access modifiers changed from: protected */
    public final int nextEscapeIndex(CharSequence charSequence, int i, int i2) {
        while (i < i2) {
            char charAt = charSequence.charAt(i);
            if ((charAt < this.b && this.a[charAt] != null) || charAt > this.f || charAt < this.e) {
                break;
            }
            i++;
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public final char[] escape(int i) {
        if (i < this.b) {
            char[] cArr = this.a[i];
            if (cArr != null) {
                return cArr;
            }
        }
        if (i < this.c || i > this.d) {
            return escapeUnsafe(i);
        }
        return null;
    }
}
