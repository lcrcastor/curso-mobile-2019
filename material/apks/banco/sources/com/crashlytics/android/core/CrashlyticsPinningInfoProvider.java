package com.crashlytics.android.core;

import io.fabric.sdk.android.services.network.PinningInfoProvider;
import java.io.InputStream;

class CrashlyticsPinningInfoProvider implements PinningInfoProvider {
    private final PinningInfoProvider a;

    public long getPinCreationTimeInMillis() {
        return -1;
    }

    public CrashlyticsPinningInfoProvider(PinningInfoProvider pinningInfoProvider) {
        this.a = pinningInfoProvider;
    }

    public InputStream getKeyStoreStream() {
        return this.a.getKeyStoreStream();
    }

    public String getKeyStorePassword() {
        return this.a.getKeyStorePassword();
    }

    public String[] getPins() {
        return this.a.getPins();
    }
}
