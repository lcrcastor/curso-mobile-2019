package com.google.android.gms.common.api;

import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.internal.zzqe;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends zzqe<BatchResult> {
    /* access modifiers changed from: private */
    public int b;
    /* access modifiers changed from: private */
    public boolean c;
    /* access modifiers changed from: private */
    public boolean d;
    /* access modifiers changed from: private */
    public final PendingResult<?>[] e;
    /* access modifiers changed from: private */
    public final Object f;

    public static final class Builder {
        private List<PendingResult<?>> a = new ArrayList();
        private GoogleApiClient b;

        public Builder(GoogleApiClient googleApiClient) {
            this.b = googleApiClient;
        }

        public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken<>(this.a.size());
            this.a.add(pendingResult);
            return batchResultToken;
        }

        public Batch build() {
            return new Batch(this.a, this.b);
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.f = new Object();
        this.b = list.size();
        this.e = new PendingResult[this.b];
        if (list.isEmpty()) {
            zzc(new BatchResult(Status.vY, this.e));
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PendingResult<?> pendingResult = (PendingResult) list.get(i);
            this.e[i] = pendingResult;
            pendingResult.zza(new zza() {
                /* JADX WARNING: Code restructure failed: missing block: B:24:0x0067, code lost:
                    return;
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void zzv(com.google.android.gms.common.api.Status r5) {
                    /*
                        r4 = this;
                        com.google.android.gms.common.api.Batch r0 = com.google.android.gms.common.api.Batch.this
                        java.lang.Object r0 = r0.f
                        monitor-enter(r0)
                        com.google.android.gms.common.api.Batch r1 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        boolean r1 = r1.isCanceled()     // Catch:{ all -> 0x0068 }
                        if (r1 == 0) goto L_0x0011
                        monitor-exit(r0)     // Catch:{ all -> 0x0068 }
                        return
                    L_0x0011:
                        boolean r1 = r5.isCanceled()     // Catch:{ all -> 0x0068 }
                        r2 = 1
                        if (r1 == 0) goto L_0x001e
                        com.google.android.gms.common.api.Batch r5 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        r5.d = r2     // Catch:{ all -> 0x0068 }
                        goto L_0x0029
                    L_0x001e:
                        boolean r5 = r5.isSuccess()     // Catch:{ all -> 0x0068 }
                        if (r5 != 0) goto L_0x0029
                        com.google.android.gms.common.api.Batch r5 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        r5.c = r2     // Catch:{ all -> 0x0068 }
                    L_0x0029:
                        com.google.android.gms.common.api.Batch r5 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        r5.b = r5.b - 1     // Catch:{ all -> 0x0068 }
                        com.google.android.gms.common.api.Batch r5 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        int r5 = r5.b     // Catch:{ all -> 0x0068 }
                        if (r5 != 0) goto L_0x0066
                        com.google.android.gms.common.api.Batch r5 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        boolean r5 = r5.d     // Catch:{ all -> 0x0068 }
                        if (r5 == 0) goto L_0x0044
                        com.google.android.gms.common.api.Batch r5 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        com.google.android.gms.common.api.Batch.super.cancel()     // Catch:{ all -> 0x0068 }
                        goto L_0x0066
                    L_0x0044:
                        com.google.android.gms.common.api.Batch r5 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        boolean r5 = r5.c     // Catch:{ all -> 0x0068 }
                        if (r5 == 0) goto L_0x0054
                        com.google.android.gms.common.api.Status r5 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x0068 }
                        r1 = 13
                        r5.<init>(r1)     // Catch:{ all -> 0x0068 }
                        goto L_0x0056
                    L_0x0054:
                        com.google.android.gms.common.api.Status r5 = com.google.android.gms.common.api.Status.vY     // Catch:{ all -> 0x0068 }
                    L_0x0056:
                        com.google.android.gms.common.api.Batch r1 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        com.google.android.gms.common.api.BatchResult r2 = new com.google.android.gms.common.api.BatchResult     // Catch:{ all -> 0x0068 }
                        com.google.android.gms.common.api.Batch r3 = com.google.android.gms.common.api.Batch.this     // Catch:{ all -> 0x0068 }
                        com.google.android.gms.common.api.PendingResult[] r3 = r3.e     // Catch:{ all -> 0x0068 }
                        r2.<init>(r5, r3)     // Catch:{ all -> 0x0068 }
                        r1.zzc(r2)     // Catch:{ all -> 0x0068 }
                    L_0x0066:
                        monitor-exit(r0)     // Catch:{ all -> 0x0068 }
                        return
                    L_0x0068:
                        r5 = move-exception
                        monitor-exit(r0)     // Catch:{ all -> 0x0068 }
                        throw r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.Batch.AnonymousClass1.zzv(com.google.android.gms.common.api.Status):void");
                }
            });
        }
    }

    public void cancel() {
        super.cancel();
        for (PendingResult<?> cancel : this.e) {
            cancel.cancel();
        }
    }

    /* renamed from: createFailedResult */
    public BatchResult zzc(Status status) {
        return new BatchResult(status, this.e);
    }
}
