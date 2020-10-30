package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Map;

@GwtCompatible
@Beta
public final class ArrayBasedEscaperMap {
    private static final char[][] b = ((char[][]) Array.newInstance(char.class, new int[]{0, 0}));
    private final char[][] a;

    public static ArrayBasedEscaperMap create(Map<Character, String> map) {
        return new ArrayBasedEscaperMap(a(map));
    }

    private ArrayBasedEscaperMap(char[][] cArr) {
        this.a = cArr;
    }

    /* access modifiers changed from: 0000 */
    public char[][] a() {
        return this.a;
    }

    @VisibleForTesting
    static char[][] a(Map<Character, String> map) {
        Preconditions.checkNotNull(map);
        if (map.isEmpty()) {
            return b;
        }
        char[][] cArr = new char[(((Character) Collections.max(map.keySet())).charValue() + 1)][];
        for (Character charValue : map.keySet()) {
            char charValue2 = charValue.charValue();
            cArr[charValue2] = ((String) map.get(Character.valueOf(charValue2))).toCharArray();
        }
        return cArr;
    }
}
