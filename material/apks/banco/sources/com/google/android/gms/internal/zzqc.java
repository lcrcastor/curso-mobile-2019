package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;

public class zzqc {

    public static abstract class zza<R extends Result, A extends com.google.android.gms.common.api.Api.zzb> extends zzqe<R> implements zzb<R> {
        private final zzc<A> b;
        private final Api<?> c;

        @Deprecated
        protected zza(zzc<A> zzc, GoogleApiClient googleApiClient) {
            super((GoogleApiClient) zzac.zzb(googleApiClient, (Object) "GoogleApiClient must not be null"));
            this.b = (zzc) zzac.zzy(zzc);
            this.c = null;
        }

        protected zza(Api<?> api, GoogleApiClient googleApiClient) {
            super((GoogleApiClient) zzac.zzb(googleApiClient, (Object) "GoogleApiClient must not be null"));
            this.b = api.zzapp();
            this.c = api;
        }

        private void a(RemoteException remoteException) {
            zzz(new Status(8, remoteException.getLocalizedMessage(), null));
        }

        public /* synthetic */ void setResult(Object obj) {
            super.zzc((Result) obj);
        }

        public abstract void zza(A a);

        public final zzc<A> zzapp() {
            return this.b;
        }

        public final Api<?> zzaqn() {
            return this.c;
        }

        public final void zzb(A a) {
            try {
                zza(a);
            } catch (DeadObjectException e) {
                a(e);
                throw e;
            } catch (RemoteException e2) {
                a(e2);
            }
        }

        /* access modifiers changed from: protected */
        public void zzb(R r) {
        }

        public final void zzz(Status status) {
            zzac.zzb(!status.isSuccess(), (Object) "Failed result must not be success");
            Result zzc = zzc(status);
            zzc(zzc);
            zzb((R) zzc);
        }
    }

    public interface zzb<R> {
        void setResult(R r);

        void zzz(Status status);
    }
}
