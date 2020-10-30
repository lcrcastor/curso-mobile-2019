package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzac;
import java.util.concurrent.TimeUnit;

public final class BatchResult implements Result {
    private final Status a;
    private final PendingResult<?>[] b;

    BatchResult(Status status, PendingResult<?>[] pendingResultArr) {
        this.a = status;
        this.b = pendingResultArr;
    }

    public Status getStatus() {
        return this.a;
    }

    public <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        zzac.zzb(batchResultToken.mId < this.b.length, (Object) "The result token does not belong to this batch");
        return this.b[batchResultToken.mId].await(0, TimeUnit.MILLISECONDS);
    }
}
