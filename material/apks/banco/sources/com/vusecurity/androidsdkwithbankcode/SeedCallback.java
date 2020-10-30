package com.vusecurity.androidsdkwithbankcode;

public interface SeedCallback {
    void onError(Throwable th);

    void onResultsAvailable(String str);
}
