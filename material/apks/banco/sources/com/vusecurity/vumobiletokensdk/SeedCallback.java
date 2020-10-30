package com.vusecurity.vumobiletokensdk;

public interface SeedCallback {
    void onError(Throwable th);

    void onResultsAvailable(String str);
}
