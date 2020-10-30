package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.concurrency.internal.DefaultRetryPolicy;
import io.fabric.sdk.android.services.concurrency.internal.ExponentialBackoff;
import io.fabric.sdk.android.services.concurrency.internal.RetryState;
import io.fabric.sdk.android.services.events.FilesSender;
import java.io.File;
import java.util.List;

class AnswersRetryFilesSender implements FilesSender {
    private final SessionAnalyticsFilesSender a;
    private final RetryManager b;

    public static AnswersRetryFilesSender a(SessionAnalyticsFilesSender sessionAnalyticsFilesSender) {
        return new AnswersRetryFilesSender(sessionAnalyticsFilesSender, new RetryManager(new RetryState(new RandomBackoff(new ExponentialBackoff(1000, 8), 0.1d), new DefaultRetryPolicy(5))));
    }

    AnswersRetryFilesSender(SessionAnalyticsFilesSender sessionAnalyticsFilesSender, RetryManager retryManager) {
        this.a = sessionAnalyticsFilesSender;
        this.b = retryManager;
    }

    public boolean send(List<File> list) {
        long nanoTime = System.nanoTime();
        if (!this.b.a(nanoTime)) {
            return false;
        }
        if (this.a.send(list)) {
            this.b.a();
            return true;
        }
        this.b.b(nanoTime);
        return false;
    }
}
