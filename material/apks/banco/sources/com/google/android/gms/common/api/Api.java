package com.google.android.gms.common.api;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzr;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends ApiOptions> {
    private final zza<?, O> a;
    private final zzh<?, O> b = null;
    private final zzf<?> c;
    private final zzi<?> d;
    private final String e;

    public interface ApiOptions {

        public interface HasOptions extends ApiOptions {
        }

        public static final class NoOptions implements NotRequiredOptions {
            private NoOptions() {
            }
        }

        public interface NotRequiredOptions extends ApiOptions {
        }

        public interface Optional extends HasOptions, NotRequiredOptions {
        }
    }

    public static abstract class zza<T extends zze, O> extends zzd<T, O> {
        public abstract T zza(Context context, Looper looper, com.google.android.gms.common.internal.zzh zzh, O o, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener);
    }

    public interface zzb {
    }

    public static class zzc<C extends zzb> {
    }

    public static abstract class zzd<T extends zzb, O> {
        public int getPriority() {
            return SubsamplingScaleImageView.TILE_SIZE_AUTO;
        }

        public List<Scope> zzp(O o) {
            return Collections.emptyList();
        }
    }

    public interface zze extends zzb {
        void disconnect();

        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

        boolean isConnected();

        boolean isConnecting();

        void zza(com.google.android.gms.common.internal.zze.zzf zzf);

        void zza(zzr zzr, Set<Scope> set);

        boolean zzahd();

        boolean zzahs();

        Intent zzaht();

        boolean zzapr();

        @Nullable
        IBinder zzaps();
    }

    public static final class zzf<C extends zze> extends zzc<C> {
    }

    public interface zzg<T extends IInterface> extends zzb {
        void zza(int i, T t);

        T zzh(IBinder iBinder);

        String zzix();

        String zziy();
    }

    public static abstract class zzh<T extends zzg, O> extends zzd<T, O> {
        public abstract int zzapt();

        public abstract T zzr(O o);
    }

    public static final class zzi<C extends zzg> extends zzc<C> {
    }

    public <C extends zze> Api(String str, zza<C, O> zza2, zzf<C> zzf2) {
        zzac.zzb(zza2, (Object) "Cannot construct an Api with a null ClientBuilder");
        zzac.zzb(zzf2, (Object) "Cannot construct an Api with a null ClientKey");
        this.e = str;
        this.a = zza2;
        this.c = zzf2;
        this.d = null;
    }

    public String getName() {
        return this.e;
    }

    public zzd<?, O> zzapm() {
        if (zzapq()) {
            return null;
        }
        return this.a;
    }

    public zza<?, O> zzapn() {
        zzac.zza(this.a != null, (Object) "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.a;
    }

    public zzh<?, O> zzapo() {
        zzac.zza(false, (Object) "This API was constructed with a ClientBuilder. Use getClientBuilder");
        return null;
    }

    public zzc<?> zzapp() {
        if (this.c != null) {
            return this.c;
        }
        throw new IllegalStateException("This API was constructed with null client keys. This should not be possible.");
    }

    public boolean zzapq() {
        return false;
    }
}
