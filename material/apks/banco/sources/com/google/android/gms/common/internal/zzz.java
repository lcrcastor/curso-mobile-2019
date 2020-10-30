package com.google.android.gms.common.internal;

import java.util.Iterator;

public class zzz {
    private final String a;

    private zzz(String str) {
        this.a = str;
    }

    public static zzz zzhy(String str) {
        return new zzz(str);
    }

    /* access modifiers changed from: 0000 */
    public CharSequence a(Object obj) {
        return obj instanceof CharSequence ? (CharSequence) obj : obj.toString();
    }

    public final String zza(Iterable<?> iterable) {
        return zza(new StringBuilder(), iterable).toString();
    }

    public final StringBuilder zza(StringBuilder sb, Iterable<?> iterable) {
        Iterator it = iterable.iterator();
        if (it.hasNext()) {
            while (true) {
                sb.append(a(it.next()));
                if (!it.hasNext()) {
                    break;
                }
                sb.append(this.a);
            }
        }
        return sb;
    }
}
