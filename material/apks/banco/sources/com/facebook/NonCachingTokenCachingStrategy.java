package com.facebook;

import android.os.Bundle;

public class NonCachingTokenCachingStrategy extends TokenCachingStrategy {
    public void clear() {
    }

    public Bundle load() {
        return null;
    }

    public void save(Bundle bundle) {
    }
}
