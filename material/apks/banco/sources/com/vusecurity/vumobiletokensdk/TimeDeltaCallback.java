package com.vusecurity.vumobiletokensdk;

public interface TimeDeltaCallback {
    void onError(Throwable th);

    void onResultsAvailable(long j, int i);
}
