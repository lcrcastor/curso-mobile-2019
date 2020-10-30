package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzac;
import java.lang.ref.WeakReference;

public class zzrp<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    /* access modifiers changed from: private */
    public ResultTransform<? super R, ? extends Result> a = null;
    /* access modifiers changed from: private */
    public zzrp<? extends Result> b = null;
    private volatile ResultCallbacks<? super R> c = null;
    private PendingResult<R> d = null;
    /* access modifiers changed from: private */
    public final Object e = new Object();
    private Status f = null;
    /* access modifiers changed from: private */
    public final WeakReference<GoogleApiClient> g;
    /* access modifiers changed from: private */
    public final zza h;
    private boolean i = false;

    final class zza extends Handler {
        public zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    PendingResult pendingResult = (PendingResult) message.obj;
                    synchronized (zzrp.this.e) {
                        if (pendingResult == null) {
                            try {
                                zzrp.this.b.a(new Status(13, "Transform returned null"));
                            } catch (Throwable th) {
                                throw th;
                            }
                        } else if (pendingResult instanceof zzrk) {
                            zzrp.this.b.a(((zzrk) pendingResult).a());
                        } else {
                            zzrp.this.b.zza(pendingResult);
                        }
                    }
                    return;
                case 1:
                    RuntimeException runtimeException = (RuntimeException) message.obj;
                    String str = "TransformedResultImpl";
                    String str2 = "Runtime exception on the transformation worker thread: ";
                    String valueOf = String.valueOf(runtimeException.getMessage());
                    Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                    throw runtimeException;
                default:
                    int i = message.what;
                    StringBuilder sb = new StringBuilder(70);
                    sb.append("TransformationResultHandler received unknown message type: ");
                    sb.append(i);
                    Log.e("TransformedResultImpl", sb.toString());
                    return;
            }
        }
    }

    public zzrp(WeakReference<GoogleApiClient> weakReference) {
        zzac.zzb(weakReference, (Object) "GoogleApiClient reference must not be null");
        this.g = weakReference;
        GoogleApiClient googleApiClient = (GoogleApiClient) this.g.get();
        this.h = new zza(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
    }

    /* access modifiers changed from: private */
    public void a(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e2) {
                String valueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(valueOf);
                Log.w("TransformedResultImpl", sb.toString(), e2);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(Status status) {
        synchronized (this.e) {
            this.f = status;
            b(this.f);
        }
    }

    private void b() {
        if (this.a != null || this.c != null) {
            GoogleApiClient googleApiClient = (GoogleApiClient) this.g.get();
            if (!(this.i || this.a == null || googleApiClient == null)) {
                googleApiClient.zza(this);
                this.i = true;
            }
            if (this.f != null) {
                b(this.f);
                return;
            }
            if (this.d != null) {
                this.d.setResultCallback(this);
            }
        }
    }

    private void b(Status status) {
        synchronized (this.e) {
            if (this.a != null) {
                Status onFailure = this.a.onFailure(status);
                zzac.zzb(onFailure, (Object) "onFailure must not return null");
                this.b.a(onFailure);
            } else if (c()) {
                this.c.onFailure(status);
            }
        }
    }

    private boolean c() {
        return (this.c == null || ((GoogleApiClient) this.g.get()) == null) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.c = null;
    }

    public void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        synchronized (this.e) {
            boolean z = false;
            zzac.zza(this.c == null, (Object) "Cannot call andFinally() twice.");
            if (this.a == null) {
                z = true;
            }
            zzac.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.c = resultCallbacks;
            b();
        }
    }

    public void onResult(final R r) {
        synchronized (this.e) {
            if (!r.getStatus().isSuccess()) {
                a(r.getStatus());
                a((Result) r);
            } else if (this.a != null) {
                zzrj.zzarz().submit(new Runnable() {
                    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0080, code lost:
                        if (r0 == null) goto L_0x0083;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0083, code lost:
                        return;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0046, code lost:
                        if (r0 != null) goto L_0x0048;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0048, code lost:
                        r0.zzb(r5.b);
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:6:0x004d, code lost:
                        return;
                     */
                    @android.support.annotation.WorkerThread
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void run() {
                        /*
                            r5 = this;
                            r0 = 1
                            r1 = 0
                            java.lang.ThreadLocal<java.lang.Boolean> r2 = com.google.android.gms.internal.zzqe.a     // Catch:{ RuntimeException -> 0x0050 }
                            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r0)     // Catch:{ RuntimeException -> 0x0050 }
                            r2.set(r3)     // Catch:{ RuntimeException -> 0x0050 }
                            com.google.android.gms.internal.zzrp r2 = com.google.android.gms.internal.zzrp.this     // Catch:{ RuntimeException -> 0x0050 }
                            com.google.android.gms.common.api.ResultTransform r2 = r2.a     // Catch:{ RuntimeException -> 0x0050 }
                            com.google.android.gms.common.api.Result r3 = r4     // Catch:{ RuntimeException -> 0x0050 }
                            com.google.android.gms.common.api.PendingResult r2 = r2.onSuccess(r3)     // Catch:{ RuntimeException -> 0x0050 }
                            com.google.android.gms.internal.zzrp r3 = com.google.android.gms.internal.zzrp.this     // Catch:{ RuntimeException -> 0x0050 }
                            com.google.android.gms.internal.zzrp$zza r3 = r3.h     // Catch:{ RuntimeException -> 0x0050 }
                            com.google.android.gms.internal.zzrp r4 = com.google.android.gms.internal.zzrp.this     // Catch:{ RuntimeException -> 0x0050 }
                            com.google.android.gms.internal.zzrp$zza r4 = r4.h     // Catch:{ RuntimeException -> 0x0050 }
                            android.os.Message r2 = r4.obtainMessage(r1, r2)     // Catch:{ RuntimeException -> 0x0050 }
                            r3.sendMessage(r2)     // Catch:{ RuntimeException -> 0x0050 }
                            java.lang.ThreadLocal<java.lang.Boolean> r0 = com.google.android.gms.internal.zzqe.a
                            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
                            r0.set(r1)
                            com.google.android.gms.internal.zzrp r0 = com.google.android.gms.internal.zzrp.this
                            com.google.android.gms.common.api.Result r1 = r4
                            r0.a(r1)
                            com.google.android.gms.internal.zzrp r0 = com.google.android.gms.internal.zzrp.this
                            java.lang.ref.WeakReference r0 = r0.g
                            java.lang.Object r0 = r0.get()
                            com.google.android.gms.common.api.GoogleApiClient r0 = (com.google.android.gms.common.api.GoogleApiClient) r0
                            if (r0 == 0) goto L_0x0083
                        L_0x0048:
                            com.google.android.gms.internal.zzrp r1 = com.google.android.gms.internal.zzrp.this
                            r0.zzb(r1)
                            return
                        L_0x004e:
                            r0 = move-exception
                            goto L_0x0084
                        L_0x0050:
                            r2 = move-exception
                            com.google.android.gms.internal.zzrp r3 = com.google.android.gms.internal.zzrp.this     // Catch:{ all -> 0x004e }
                            com.google.android.gms.internal.zzrp$zza r3 = r3.h     // Catch:{ all -> 0x004e }
                            com.google.android.gms.internal.zzrp r4 = com.google.android.gms.internal.zzrp.this     // Catch:{ all -> 0x004e }
                            com.google.android.gms.internal.zzrp$zza r4 = r4.h     // Catch:{ all -> 0x004e }
                            android.os.Message r0 = r4.obtainMessage(r0, r2)     // Catch:{ all -> 0x004e }
                            r3.sendMessage(r0)     // Catch:{ all -> 0x004e }
                            java.lang.ThreadLocal<java.lang.Boolean> r0 = com.google.android.gms.internal.zzqe.a
                            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
                            r0.set(r1)
                            com.google.android.gms.internal.zzrp r0 = com.google.android.gms.internal.zzrp.this
                            com.google.android.gms.common.api.Result r1 = r4
                            r0.a(r1)
                            com.google.android.gms.internal.zzrp r0 = com.google.android.gms.internal.zzrp.this
                            java.lang.ref.WeakReference r0 = r0.g
                            java.lang.Object r0 = r0.get()
                            com.google.android.gms.common.api.GoogleApiClient r0 = (com.google.android.gms.common.api.GoogleApiClient) r0
                            if (r0 == 0) goto L_0x0083
                            goto L_0x0048
                        L_0x0083:
                            return
                        L_0x0084:
                            java.lang.ThreadLocal<java.lang.Boolean> r2 = com.google.android.gms.internal.zzqe.a
                            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
                            r2.set(r1)
                            com.google.android.gms.internal.zzrp r1 = com.google.android.gms.internal.zzrp.this
                            com.google.android.gms.common.api.Result r2 = r4
                            r1.a(r2)
                            com.google.android.gms.internal.zzrp r1 = com.google.android.gms.internal.zzrp.this
                            java.lang.ref.WeakReference r1 = r1.g
                            java.lang.Object r1 = r1.get()
                            com.google.android.gms.common.api.GoogleApiClient r1 = (com.google.android.gms.common.api.GoogleApiClient) r1
                            if (r1 == 0) goto L_0x00a7
                            com.google.android.gms.internal.zzrp r2 = com.google.android.gms.internal.zzrp.this
                            r1.zzb(r2)
                        L_0x00a7:
                            throw r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrp.AnonymousClass1.run():void");
                    }
                });
            } else if (c()) {
                this.c.onSuccess(r);
            }
        }
    }

    @NonNull
    public <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        zzrp<? extends Result> zzrp;
        synchronized (this.e) {
            boolean z = false;
            zzac.zza(this.a == null, (Object) "Cannot call then() twice.");
            if (this.c == null) {
                z = true;
            }
            zzac.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.a = resultTransform;
            zzrp = new zzrp<>(this.g);
            this.b = zzrp;
            b();
        }
        return zzrp;
    }

    public void zza(PendingResult<?> pendingResult) {
        synchronized (this.e) {
            this.d = pendingResult;
            b();
        }
    }
}
