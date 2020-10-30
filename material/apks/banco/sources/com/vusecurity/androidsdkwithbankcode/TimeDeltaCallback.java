package com.vusecurity.androidsdkwithbankcode;

public interface TimeDeltaCallback {
    void onError(Throwable th);

    void onResultsAvailable(long j, int i);
}
