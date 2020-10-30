package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class zze<T extends IInterface> {
    public static final String[] Bs = {"service_esmobile", "service_googleme"};
    protected AtomicInteger Br;
    final Handler a;
    private int b;
    private long c;
    private long d;
    private int e;
    private long f;
    private final Context g;
    private final Looper h;
    private final zzn i;
    private final com.google.android.gms.common.zzc j;
    private final Object k;
    /* access modifiers changed from: private */
    public final Object l;
    /* access modifiers changed from: private */
    public zzv m;
    /* access modifiers changed from: private */
    public zzf n;
    private T o;
    /* access modifiers changed from: private */
    public final ArrayList<C0005zze<?>> p;
    private zzh q;
    private int r;
    /* access modifiers changed from: private */
    public final zzb s;
    /* access modifiers changed from: private */
    public final zzc t;
    private final int u;
    private final String v;

    abstract class zza extends C0005zze<Boolean> {
        public final Bundle Bt;
        public final int statusCode;

        @BinderThread
        protected zza(int i, Bundle bundle) {
            super(Boolean.valueOf(true));
            this.statusCode = i;
            this.Bt = bundle;
        }

        /* access modifiers changed from: protected */
        public abstract boolean zzaua();

        /* access modifiers changed from: protected */
        public void zzaub() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: zzc */
        public void zzv(Boolean bool) {
            ConnectionResult connectionResult;
            PendingIntent pendingIntent = null;
            if (bool == null) {
                zze.this.a(1, null);
                return;
            }
            int i = this.statusCode;
            if (i != 0) {
                if (i != 10) {
                    zze.this.a(1, null);
                    if (this.Bt != null) {
                        pendingIntent = (PendingIntent) this.Bt.getParcelable("pendingIntent");
                    }
                    connectionResult = new ConnectionResult(this.statusCode, pendingIntent);
                } else {
                    zze.this.a(1, null);
                    throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                }
            } else if (!zzaua()) {
                zze.this.a(1, null);
                connectionResult = new ConnectionResult(8, null);
            } else {
                return;
            }
            zzm(connectionResult);
        }

        /* access modifiers changed from: protected */
        public abstract void zzm(ConnectionResult connectionResult);
    }

    public interface zzb {
        void onConnected(@Nullable Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface zzc {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    final class zzd extends Handler {
        public zzd(Looper looper) {
            super(looper);
        }

        private void a(Message message) {
            C0005zze zze = (C0005zze) message.obj;
            zze.zzaub();
            zze.unregister();
        }

        private boolean b(Message message) {
            boolean z = true;
            if (!(message.what == 2 || message.what == 1)) {
                if (message.what == 5) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public void handleMessage(Message message) {
            if (zze.this.Br.get() != message.arg1) {
                if (b(message)) {
                    a(message);
                }
            } else if ((message.what == 1 || message.what == 5) && !zze.this.isConnecting()) {
                a(message);
            } else {
                PendingIntent pendingIntent = null;
                if (message.what == 3) {
                    if (message.obj instanceof PendingIntent) {
                        pendingIntent = (PendingIntent) message.obj;
                    }
                    ConnectionResult connectionResult = new ConnectionResult(message.arg2, pendingIntent);
                    zze.this.n.zzh(connectionResult);
                    zze.this.onConnectionFailed(connectionResult);
                } else if (message.what == 4) {
                    zze.this.a(4, null);
                    if (zze.this.s != null) {
                        zze.this.s.onConnectionSuspended(message.arg2);
                    }
                    zze.this.onConnectionSuspended(message.arg2);
                    zze.this.a(4, 1, null);
                } else if (message.what == 2 && !zze.this.isConnected()) {
                    a(message);
                } else if (b(message)) {
                    ((C0005zze) message.obj).zzauc();
                } else {
                    int i = message.what;
                    StringBuilder sb = new StringBuilder(45);
                    sb.append("Don't know how to handle message: ");
                    sb.append(i);
                    Log.wtf("GmsClient", sb.toString(), new Exception());
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze$zze reason: collision with other inner class name */
    public abstract class C0005zze<TListener> {
        private TListener a;
        private boolean c = false;

        public C0005zze(TListener tlistener) {
            this.a = tlistener;
        }

        public void unregister() {
            zzaud();
            synchronized (zze.this.p) {
                zze.this.p.remove(this);
            }
        }

        /* access modifiers changed from: protected */
        public abstract void zzaub();

        public void zzauc() {
            TListener tlistener;
            synchronized (this) {
                tlistener = this.a;
                if (this.c) {
                    String valueOf = String.valueOf(this);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47);
                    sb.append("Callback proxy ");
                    sb.append(valueOf);
                    sb.append(" being reused. This is not safe.");
                    Log.w("GmsClient", sb.toString());
                }
            }
            if (tlistener != null) {
                try {
                    zzv(tlistener);
                } catch (RuntimeException e) {
                    zzaub();
                    throw e;
                }
            } else {
                zzaub();
            }
            synchronized (this) {
                this.c = true;
            }
            unregister();
        }

        public void zzaud() {
            synchronized (this) {
                this.a = null;
            }
        }

        /* access modifiers changed from: protected */
        public abstract void zzv(TListener tlistener);
    }

    public interface zzf {
        void zzh(@NonNull ConnectionResult connectionResult);
    }

    public static final class zzg extends com.google.android.gms.common.internal.zzu.zza {
        private zze a;
        private final int b;

        public zzg(@NonNull zze zze, int i) {
            this.a = zze;
            this.b = i;
        }

        private void a() {
            this.a = null;
        }

        @BinderThread
        public void zza(int i, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
            zzac.zzb(this.a, (Object) "onPostInitComplete can be called only once per call to getRemoteService");
            this.a.zza(i, iBinder, bundle, this.b);
            a();
        }

        @BinderThread
        public void zzb(int i, @Nullable Bundle bundle) {
            Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
        }
    }

    public final class zzh implements ServiceConnection {
        private final int b;

        public zzh(int i) {
            this.b = i;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            zzac.zzb(iBinder, (Object) "Expecting a valid IBinder");
            synchronized (zze.this.l) {
                zze.this.m = com.google.android.gms.common.internal.zzv.zza.zzdv(iBinder);
            }
            zze.this.zza(0, null, this.b);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            synchronized (zze.this.l) {
                zze.this.m = null;
            }
            zze.this.a.sendMessage(zze.this.a.obtainMessage(4, this.b, 1));
        }
    }

    public class zzi implements zzf {
        public zzi() {
        }

        public void zzh(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                zze.this.zza((zzr) null, zze.this.zzatz());
                return;
            }
            if (zze.this.t != null) {
                zze.this.t.onConnectionFailed(connectionResult);
            }
        }
    }

    public final class zzj extends zza {
        public final IBinder By;

        @BinderThread
        public zzj(int i, IBinder iBinder, Bundle bundle) {
            super(i, bundle);
            this.By = iBinder;
        }

        /* access modifiers changed from: protected */
        public boolean zzaua() {
            boolean z = false;
            try {
                String interfaceDescriptor = this.By.getInterfaceDescriptor();
                if (!zze.this.zziy().equals(interfaceDescriptor)) {
                    String valueOf = String.valueOf(zze.this.zziy());
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34 + String.valueOf(interfaceDescriptor).length());
                    sb.append("service descriptor mismatch: ");
                    sb.append(valueOf);
                    sb.append(" vs. ");
                    sb.append(interfaceDescriptor);
                    Log.e("GmsClient", sb.toString());
                    return false;
                }
                IInterface zzh = zze.this.zzh(this.By);
                if (zzh != null && zze.this.a(2, 3, zzh)) {
                    Bundle zzaoe = zze.this.zzaoe();
                    if (zze.this.s != null) {
                        zze.this.s.onConnected(zzaoe);
                    }
                    z = true;
                }
                return z;
            } catch (RemoteException unused) {
                Log.w("GmsClient", "service probably died");
                return false;
            }
        }

        /* access modifiers changed from: protected */
        public void zzm(ConnectionResult connectionResult) {
            if (zze.this.t != null) {
                zze.this.t.onConnectionFailed(connectionResult);
            }
            zze.this.onConnectionFailed(connectionResult);
        }
    }

    public final class zzk extends zza {
        @BinderThread
        public zzk(int i, Bundle bundle) {
            super(i, bundle);
        }

        /* access modifiers changed from: protected */
        public boolean zzaua() {
            zze.this.n.zzh(ConnectionResult.uJ);
            return true;
        }

        /* access modifiers changed from: protected */
        public void zzm(ConnectionResult connectionResult) {
            zze.this.n.zzh(connectionResult);
            zze.this.onConnectionFailed(connectionResult);
        }
    }

    protected zze(Context context, Looper looper, int i2, zzb zzb2, zzc zzc2, String str) {
        this(context, looper, zzn.zzcf(context), com.google.android.gms.common.zzc.zzapd(), i2, (zzb) zzac.zzy(zzb2), (zzc) zzac.zzy(zzc2), str);
    }

    protected zze(Context context, Looper looper, zzn zzn, com.google.android.gms.common.zzc zzc2, int i2, zzb zzb2, zzc zzc3, String str) {
        this.k = new Object();
        this.l = new Object();
        this.p = new ArrayList<>();
        this.r = 1;
        this.Br = new AtomicInteger(0);
        this.g = (Context) zzac.zzb(context, (Object) "Context must not be null");
        this.h = (Looper) zzac.zzb(looper, (Object) "Looper must not be null");
        this.i = (zzn) zzac.zzb(zzn, (Object) "Supervisor must not be null");
        this.j = (com.google.android.gms.common.zzc) zzac.zzb(zzc2, (Object) "API availability must not be null");
        this.a = new zzd(looper);
        this.u = i2;
        this.s = zzb2;
        this.t = zzc3;
        this.v = str;
    }

    private void a() {
        if (this.q != null) {
            String valueOf = String.valueOf(zzix());
            String valueOf2 = String.valueOf(zzatq());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 70 + String.valueOf(valueOf2).length());
            sb.append("Calling connect() while still connected, missing disconnect() for ");
            sb.append(valueOf);
            sb.append(" on ");
            sb.append(valueOf2);
            Log.e("GmsClient", sb.toString());
            this.i.zzb(zzix(), zzatq(), this.q, zzatr());
            this.Br.incrementAndGet();
        }
        this.q = new zzh(this.Br.get());
        if (!this.i.zza(zzix(), zzatq(), this.q, zzatr())) {
            String valueOf3 = String.valueOf(zzix());
            String valueOf4 = String.valueOf(zzatq());
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 34 + String.valueOf(valueOf4).length());
            sb2.append("unable to connect to service: ");
            sb2.append(valueOf3);
            sb2.append(" on ");
            sb2.append(valueOf4);
            Log.e("GmsClient", sb2.toString());
            zza(16, null, this.Br.get());
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, T t2) {
        boolean z = false;
        if ((i2 == 3) == (t2 != null)) {
            z = true;
        }
        zzac.zzbs(z);
        synchronized (this.k) {
            this.r = i2;
            this.o = t2;
            zzc(i2, t2);
            switch (i2) {
                case 1:
                    b();
                    break;
                case 2:
                    a();
                    break;
                case 3:
                    zza(t2);
                    break;
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean a(int i2, int i3, T t2) {
        synchronized (this.k) {
            if (this.r != i2) {
                return false;
            }
            a(i3, t2);
            return true;
        }
    }

    private void b() {
        if (this.q != null) {
            this.i.zzb(zzix(), zzatq(), this.q, zzatr());
            this.q = null;
        }
    }

    public void disconnect() {
        this.Br.incrementAndGet();
        synchronized (this.p) {
            int size = this.p.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((C0005zze) this.p.get(i2)).zzaud();
            }
            this.p.clear();
        }
        synchronized (this.l) {
            this.m = null;
        }
        a(1, (T) null);
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i2;
        T t2;
        String str2;
        String str3;
        synchronized (this.k) {
            i2 = this.r;
            t2 = this.o;
        }
        printWriter.append(str).append("mConnectState=");
        switch (i2) {
            case 1:
                str2 = "DISCONNECTED";
                break;
            case 2:
                str2 = "CONNECTING";
                break;
            case 3:
                str2 = "CONNECTED";
                break;
            case 4:
                str2 = "DISCONNECTING";
                break;
            default:
                str2 = "UNKNOWN";
                break;
        }
        printWriter.print(str2);
        printWriter.append(" mService=");
        if (t2 == null) {
            printWriter.println("null");
        } else {
            printWriter.append(zziy()).append("@").println(Integer.toHexString(System.identityHashCode(t2.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.d > 0) {
            PrintWriter append = printWriter.append(str).append("lastConnectedTime=");
            long j2 = this.d;
            String valueOf = String.valueOf(simpleDateFormat.format(new Date(this.d)));
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21);
            sb.append(j2);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(valueOf);
            append.println(sb.toString());
        }
        if (this.c > 0) {
            printWriter.append(str).append("lastSuspendedCause=");
            switch (this.b) {
                case 1:
                    str3 = "CAUSE_SERVICE_DISCONNECTED";
                    break;
                case 2:
                    str3 = "CAUSE_NETWORK_LOST";
                    break;
                default:
                    str3 = String.valueOf(this.b);
                    break;
            }
            printWriter.append(str3);
            PrintWriter append2 = printWriter.append(" lastSuspendedTime=");
            long j3 = this.c;
            String valueOf2 = String.valueOf(simpleDateFormat.format(new Date(this.c)));
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 21);
            sb2.append(j3);
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(valueOf2);
            append2.println(sb2.toString());
        }
        if (this.f > 0) {
            printWriter.append(str).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.e));
            PrintWriter append3 = printWriter.append(" lastFailedTime=");
            long j4 = this.f;
            String valueOf3 = String.valueOf(simpleDateFormat.format(new Date(this.f)));
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 21);
            sb3.append(j4);
            sb3.append(UtilsCuentas.SEPARAOR2);
            sb3.append(valueOf3);
            append3.println(sb3.toString());
        }
    }

    public Account getAccount() {
        return null;
    }

    public final Context getContext() {
        return this.g;
    }

    public final Looper getLooper() {
        return this.h;
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.k) {
            z = this.r == 3;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.k) {
            z = this.r == 2;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.e = connectionResult.getErrorCode();
        this.f = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onConnectionSuspended(int i2) {
        this.b = i2;
        this.c = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void zza(int i2, @Nullable Bundle bundle, int i3) {
        this.a.sendMessage(this.a.obtainMessage(5, i3, -1, new zzk(i2, bundle)));
    }

    /* access modifiers changed from: protected */
    @BinderThread
    public void zza(int i2, IBinder iBinder, Bundle bundle, int i3) {
        this.a.sendMessage(this.a.obtainMessage(1, i3, -1, new zzj(i2, iBinder, bundle)));
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void zza(@NonNull T t2) {
        this.d = System.currentTimeMillis();
    }

    public void zza(@NonNull zzf zzf2) {
        this.n = (zzf) zzac.zzb(zzf2, (Object) "Connection progress callbacks cannot be null.");
        a(2, (T) null);
    }

    public void zza(zzf zzf2, ConnectionResult connectionResult) {
        this.n = (zzf) zzac.zzb(zzf2, (Object) "Connection progress callbacks cannot be null.");
        this.a.sendMessage(this.a.obtainMessage(3, this.Br.get(), connectionResult.getErrorCode(), connectionResult.getResolution()));
    }

    @WorkerThread
    public void zza(zzr zzr, Set<Scope> set) {
        try {
            GetServiceRequest zzo = new GetServiceRequest(this.u).zzht(this.g.getPackageName()).zzo(zzagl());
            if (set != null) {
                zzo.zzf(set);
            }
            if (zzahd()) {
                zzo.zzd(zzatv()).zzb(zzr);
            } else if (zzaty()) {
                zzo.zzd(getAccount());
            }
            synchronized (this.l) {
                if (this.m != null) {
                    this.m.zza((zzu) new zzg(this, this.Br.get()), zzo);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException unused) {
            Log.w("GmsClient", "service died");
            zzgl(1);
        } catch (RemoteException e2) {
            Log.w("GmsClient", "Remote exception occurred", e2);
        }
    }

    public Bundle zzagl() {
        return new Bundle();
    }

    public boolean zzahd() {
        return false;
    }

    public boolean zzahs() {
        return false;
    }

    public Intent zzaht() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    public Bundle zzaoe() {
        return null;
    }

    public boolean zzapr() {
        return true;
    }

    @Nullable
    public IBinder zzaps() {
        synchronized (this.l) {
            if (this.m == null) {
                return null;
            }
            IBinder asBinder = this.m.asBinder();
            return asBinder;
        }
    }

    /* access modifiers changed from: protected */
    public String zzatq() {
        return "com.google.android.gms";
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final String zzatr() {
        return this.v == null ? this.g.getClass().getName() : this.v;
    }

    public void zzatu() {
        int isGooglePlayServicesAvailable = this.j.isGooglePlayServicesAvailable(this.g);
        if (isGooglePlayServicesAvailable != 0) {
            a(1, (T) null);
            this.n = new zzi();
            this.a.sendMessage(this.a.obtainMessage(3, this.Br.get(), isGooglePlayServicesAvailable));
            return;
        }
        zza((zzf) new zzi());
    }

    public final Account zzatv() {
        return getAccount() != null ? getAccount() : new Account("<<default account>>", "com.google");
    }

    /* access modifiers changed from: protected */
    public final void zzatw() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public final T zzatx() {
        T t2;
        synchronized (this.k) {
            if (this.r == 4) {
                throw new DeadObjectException();
            }
            zzatw();
            zzac.zza(this.o != null, (Object) "Client is connected but service is null");
            t2 = this.o;
        }
        return t2;
    }

    public boolean zzaty() {
        return false;
    }

    /* access modifiers changed from: protected */
    public Set<Scope> zzatz() {
        return Collections.EMPTY_SET;
    }

    /* access modifiers changed from: 0000 */
    public void zzc(int i2, T t2) {
    }

    public void zzgl(int i2) {
        this.a.sendMessage(this.a.obtainMessage(4, this.Br.get(), i2));
    }

    @Nullable
    public abstract T zzh(IBinder iBinder);

    @NonNull
    public abstract String zzix();

    @NonNull
    public abstract String zziy();
}
