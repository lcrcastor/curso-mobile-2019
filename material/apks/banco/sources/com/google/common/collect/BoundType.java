package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public enum BoundType {
    OPEN {
    },
    CLOSED {
    };

    static BoundType a(boolean z) {
        return z ? CLOSED : OPEN;
    }
}
