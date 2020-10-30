package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;

public class TimeBasedFileRollOverRunnable implements Runnable {
    private final Context a;
    private final FileRollOverManager b;

    public TimeBasedFileRollOverRunnable(Context context, FileRollOverManager fileRollOverManager) {
        this.a = context;
        this.b = fileRollOverManager;
    }

    public void run() {
        try {
            CommonUtils.logControlled(this.a, "Performing time based file roll over.");
            if (!this.b.rollFileOver()) {
                this.b.cancelTimeBasedFileRollOver();
            }
        } catch (Exception e) {
            CommonUtils.logControlledError(this.a, "Failed to roll over file", e);
        }
    }
}
