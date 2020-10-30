package com.squareup.picasso;

public enum NetworkPolicy {
    NO_CACHE(1),
    NO_STORE(2),
    OFFLINE(4);
    
    final int a;

    public static boolean shouldReadFromDiskCache(int i) {
        return (i & NO_CACHE.a) == 0;
    }

    public static boolean shouldWriteToDiskCache(int i) {
        return (i & NO_STORE.a) == 0;
    }

    public static boolean isOfflineOnly(int i) {
        return (i & OFFLINE.a) != 0;
    }

    private NetworkPolicy(int i) {
        this.a = i;
    }
}
