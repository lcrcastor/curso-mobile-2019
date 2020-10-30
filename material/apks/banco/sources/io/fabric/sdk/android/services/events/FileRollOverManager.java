package io.fabric.sdk.android.services.events;

public interface FileRollOverManager {
    void cancelTimeBasedFileRollOver();

    boolean rollFileOver();

    void scheduleTimeBasedRollOverIfNeeded();
}
