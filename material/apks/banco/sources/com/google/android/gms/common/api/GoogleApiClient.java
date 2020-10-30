package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Api.zzg;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzai;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzh.zza;
import com.google.android.gms.internal.zzqa;
import com.google.android.gms.internal.zzqc;
import com.google.android.gms.internal.zzqf;
import com.google.android.gms.internal.zzqp;
import com.google.android.gms.internal.zzqz;
import com.google.android.gms.internal.zzrd;
import com.google.android.gms.internal.zzrl;
import com.google.android.gms.internal.zzrp;
import com.google.android.gms.internal.zzwy;
import com.google.android.gms.internal.zzwz;
import com.google.android.gms.internal.zzxa;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GoogleApiClient {
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;
    /* access modifiers changed from: private */
    public static final Set<GoogleApiClient> a = Collections.newSetFromMap(new WeakHashMap());

    public static final class Builder {
        private Account a;
        private final Set<Scope> b;
        private final Set<Scope> c;
        private int d;
        private View e;
        private String f;
        private String g;
        private final Map<Api<?>, zza> h;
        private final Context i;
        private final Map<Api<?>, ApiOptions> j;
        private zzqz k;
        private int l;
        private OnConnectionFailedListener m;
        private Looper n;
        private GoogleApiAvailability o;
        private Api.zza<? extends zzwz, zzxa> p;
        private final ArrayList<ConnectionCallbacks> q;
        private final ArrayList<OnConnectionFailedListener> r;

        public Builder(@NonNull Context context) {
            this.b = new HashSet();
            this.c = new HashSet();
            this.h = new ArrayMap();
            this.j = new ArrayMap();
            this.l = -1;
            this.o = GoogleApiAvailability.getInstance();
            this.p = zzwy.fb;
            this.q = new ArrayList<>();
            this.r = new ArrayList<>();
            this.i = context;
            this.n = context.getMainLooper();
            this.f = context.getPackageName();
            this.g = context.getClass().getName();
        }

        public Builder(@NonNull Context context, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            zzac.zzb(connectionCallbacks, (Object) "Must provide a connected listener");
            this.q.add(connectionCallbacks);
            zzac.zzb(onConnectionFailedListener, (Object) "Must provide a connection failed listener");
            this.r.add(onConnectionFailedListener);
        }

        private static <C extends zze, O> C a(Api.zza<C, O> zza, Object obj, Context context, Looper looper, zzh zzh, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zza.zza(context, looper, zzh, obj, connectionCallbacks, onConnectionFailedListener);
        }

        private Builder a(@NonNull zzqz zzqz, int i2, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            zzac.zzb(i2 >= 0, (Object) "clientId must be non-negative");
            this.l = i2;
            this.m = onConnectionFailedListener;
            this.k = zzqz;
            return this;
        }

        private GoogleApiClient a() {
            Api api;
            zze zze;
            zzh zzaqd = zzaqd();
            Map zzaui = zzaqd.zzaui();
            ArrayMap arrayMap = new ArrayMap();
            ArrayMap arrayMap2 = new ArrayMap();
            ArrayList arrayList = new ArrayList();
            Iterator it = this.j.keySet().iterator();
            Api api2 = null;
            Api api3 = null;
            while (true) {
                int i2 = 0;
                if (it.hasNext()) {
                    Api api4 = (Api) it.next();
                    Object obj = this.j.get(api4);
                    if (zzaui.get(api4) != null) {
                        i2 = ((zza) zzaui.get(api4)).Cb ? 1 : 2;
                    }
                    arrayMap.put(api4, Integer.valueOf(i2));
                    zzqf zzqf = new zzqf(api4, i2);
                    arrayList.add(zzqf);
                    if (api4.zzapq()) {
                        Api.zzh zzapo = api4.zzapo();
                        api = zzapo.getPriority() == 1 ? api4 : api2;
                        zze = a(zzapo, obj, this.i, this.n, zzaqd, (ConnectionCallbacks) zzqf, (OnConnectionFailedListener) zzqf);
                    } else {
                        Api.zza zzapn = api4.zzapn();
                        api = zzapn.getPriority() == 1 ? api4 : api2;
                        zze = a(zzapn, obj, this.i, this.n, zzaqd, (ConnectionCallbacks) zzqf, (OnConnectionFailedListener) zzqf);
                    }
                    arrayMap2.put(api4.zzapp(), zze);
                    if (zze.zzahs()) {
                        if (api3 != null) {
                            String valueOf = String.valueOf(api4.getName());
                            String valueOf2 = String.valueOf(api3.getName());
                            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21 + String.valueOf(valueOf2).length());
                            sb.append(valueOf);
                            sb.append(" cannot be used with ");
                            sb.append(valueOf2);
                            throw new IllegalStateException(sb.toString());
                        }
                        api3 = api4;
                    }
                    api2 = api;
                } else {
                    if (api3 != null) {
                        if (api2 != null) {
                            String valueOf3 = String.valueOf(api3.getName());
                            String valueOf4 = String.valueOf(api2.getName());
                            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 21 + String.valueOf(valueOf4).length());
                            sb2.append(valueOf3);
                            sb2.append(" cannot be used with ");
                            sb2.append(valueOf4);
                            throw new IllegalStateException(sb2.toString());
                        }
                        zzac.zza(this.a == null, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", api3.getName());
                        zzac.zza(this.b.equals(this.c), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", api3.getName());
                    }
                    zzqp zzqp = new zzqp(this.i, new ReentrantLock(), this.n, zzaqd, this.o, this.p, arrayMap, this.q, this.r, arrayMap2, this.l, zzqp.zza(arrayMap2.values(), true), arrayList);
                    return zzqp;
                }
            }
        }

        private static <C extends zzg, O> zzai a(Api.zzh<C, O> zzh, Object obj, Context context, Looper looper, zzh zzh2, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            zzai zzai = new zzai(context, looper, zzh.zzapt(), connectionCallbacks, onConnectionFailedListener, zzh2, zzh.zzr(obj));
            return zzai;
        }

        private <O extends ApiOptions> void a(Api<O> api, O o2, int i2, Scope... scopeArr) {
            boolean z = true;
            if (i2 != 1) {
                if (i2 == 2) {
                    z = false;
                } else {
                    StringBuilder sb = new StringBuilder(90);
                    sb.append("Invalid resolution mode: '");
                    sb.append(i2);
                    sb.append("', use a constant from GoogleApiClient.ResolutionMode");
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            HashSet hashSet = new HashSet(api.zzapm().zzp(o2));
            for (Scope add : scopeArr) {
                hashSet.add(add);
            }
            this.h.put(api, new zza(hashSet, z));
        }

        private void a(GoogleApiClient googleApiClient) {
            zzqa.zza(this.k).zza(this.l, googleApiClient, this.m);
        }

        public Builder addApi(@NonNull Api<? extends NotRequiredOptions> api) {
            zzac.zzb(api, (Object) "Api must not be null");
            this.j.put(api, null);
            List zzp = api.zzapm().zzp(null);
            this.c.addAll(zzp);
            this.b.addAll(zzp);
            return this;
        }

        public <O extends HasOptions> Builder addApi(@NonNull Api<O> api, @NonNull O o2) {
            zzac.zzb(api, (Object) "Api must not be null");
            zzac.zzb(o2, (Object) "Null options are not permitted for this Api");
            this.j.put(api, o2);
            List zzp = api.zzapm().zzp(o2);
            this.c.addAll(zzp);
            this.b.addAll(zzp);
            return this;
        }

        public <O extends HasOptions> Builder addApiIfAvailable(@NonNull Api<O> api, @NonNull O o2, Scope... scopeArr) {
            zzac.zzb(api, (Object) "Api must not be null");
            zzac.zzb(o2, (Object) "Null options are not permitted for this Api");
            this.j.put(api, o2);
            a(api, o2, 1, scopeArr);
            return this;
        }

        public Builder addApiIfAvailable(@NonNull Api<? extends NotRequiredOptions> api, Scope... scopeArr) {
            zzac.zzb(api, (Object) "Api must not be null");
            this.j.put(api, null);
            a(api, null, 1, scopeArr);
            return this;
        }

        public Builder addConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
            zzac.zzb(connectionCallbacks, (Object) "Listener must not be null");
            this.q.add(connectionCallbacks);
            return this;
        }

        public Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
            zzac.zzb(onConnectionFailedListener, (Object) "Listener must not be null");
            this.r.add(onConnectionFailedListener);
            return this;
        }

        public Builder addScope(@NonNull Scope scope) {
            zzac.zzb(scope, (Object) "Scope must not be null");
            this.b.add(scope);
            return this;
        }

        public GoogleApiClient build() {
            zzac.zzb(!this.j.isEmpty(), (Object) "must call addApi() to add at least one API");
            GoogleApiClient a2 = a();
            synchronized (GoogleApiClient.a) {
                GoogleApiClient.a.add(a2);
            }
            if (this.l >= 0) {
                a(a2);
            }
            return a2;
        }

        public Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, int i2, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return a(new zzqz(fragmentActivity), i2, onConnectionFailedListener);
        }

        public Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
        }

        public Builder setAccountName(String str) {
            this.a = str == null ? null : new Account(str, "com.google");
            return this;
        }

        public Builder setGravityForPopups(int i2) {
            this.d = i2;
            return this;
        }

        public Builder setHandler(@NonNull Handler handler) {
            zzac.zzb(handler, (Object) "Handler must not be null");
            this.n = handler.getLooper();
            return this;
        }

        public Builder setViewForPopups(@NonNull View view) {
            zzac.zzb(view, (Object) "View must not be null");
            this.e = view;
            return this;
        }

        public Builder useDefaultAccount() {
            return setAccountName("<<default account>>");
        }

        public zzh zzaqd() {
            zzxa zzxa = zzxa.aAa;
            if (this.j.containsKey(zzwy.API)) {
                zzxa = (zzxa) this.j.get(zzwy.API);
            }
            zzh zzh = new zzh(this.a, this.b, this.h, this.d, this.e, this.f, this.g, zzxa);
            return zzh;
        }
    }

    public interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(@Nullable Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface OnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    public static void dumpAll(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (a) {
            int i = 0;
            String concat = String.valueOf(str).concat("  ");
            for (GoogleApiClient googleApiClient : a) {
                int i2 = i + 1;
                printWriter.append(str).append("GoogleApiClient#").println(i);
                googleApiClient.dump(concat, fileDescriptor, printWriter, strArr);
                i = i2;
            }
        }
    }

    public static Set<GoogleApiClient> zzaqa() {
        Set<GoogleApiClient> set;
        synchronized (a) {
            set = a;
        }
        return set;
    }

    public abstract ConnectionResult blockingConnect();

    public abstract ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit);

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

    public abstract void connect();

    public void connect(int i) {
        throw new UnsupportedOperationException();
    }

    public abstract void disconnect();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    @NonNull
    public abstract ConnectionResult getConnectionResult(@NonNull Api<?> api);

    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean hasConnectedApi(@NonNull Api<?> api);

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void stopAutoManage(@NonNull FragmentActivity fragmentActivity);

    public abstract void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    @NonNull
    public <C extends zze> C zza(@NonNull zzc<C> zzc) {
        throw new UnsupportedOperationException();
    }

    public void zza(zzrp zzrp) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(@NonNull Api<?> api) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(zzrl zzrl) {
        throw new UnsupportedOperationException();
    }

    public void zzaqb() {
        throw new UnsupportedOperationException();
    }

    public void zzb(zzrp zzrp) {
        throw new UnsupportedOperationException();
    }

    public <A extends zzb, R extends Result, T extends zzqc.zza<R, A>> T zzc(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    public <A extends zzb, T extends zzqc.zza<? extends Result, A>> T zzd(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    public <L> zzrd<L> zzs(@NonNull L l) {
        throw new UnsupportedOperationException();
    }
}
