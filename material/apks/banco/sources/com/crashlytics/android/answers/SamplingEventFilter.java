package com.crashlytics.android.answers;

import java.util.HashSet;
import java.util.Set;

class SamplingEventFilter implements EventFilter {
    static final Set<Type> b = new HashSet<Type>() {
        {
            add(Type.START);
            add(Type.RESUME);
            add(Type.PAUSE);
            add(Type.STOP);
        }
    };
    final int a;

    public SamplingEventFilter(int i) {
        this.a = i;
    }

    public boolean a(SessionEvent sessionEvent) {
        boolean z = b.contains(sessionEvent.c) && sessionEvent.a.g == null;
        boolean z2 = Math.abs(sessionEvent.a.c.hashCode() % this.a) != 0;
        if (!z || !z2) {
            return false;
        }
        return true;
    }
}
