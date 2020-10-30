package com.squareup.picasso;

public enum MemoryPolicy {
    NO_CACHE(1),
    NO_STORE(2);
    
    final int a;

    static boolean a(int i) {
        return (i & NO_CACHE.a) == 0;
    }

    static boolean b(int i) {
        return (i & NO_STORE.a) == 0;
    }

    private MemoryPolicy(int i) {
        this.a = i;
    }
}
