package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;

public abstract class Task implements Parcelable {
    public static final int EXTRAS_LIMIT_BYTES = 10240;
    public static final int NETWORK_STATE_ANY = 2;
    public static final int NETWORK_STATE_CONNECTED = 0;
    public static final int NETWORK_STATE_UNMETERED = 1;
    protected static final long UNINITIALIZED = -1;
    private final String a;
    private final String b;
    private final boolean c;
    private final boolean d;
    private final int e;
    private final boolean f;
    private final zzc g;
    private final Bundle h;

    public static abstract class Builder {
        protected zzc afq = zzc.aff;
        protected Bundle extras;
        protected String gcmTaskService;
        protected boolean isPersisted;
        protected int requiredNetworkState;
        protected boolean requiresCharging;
        protected String tag;
        protected boolean updateCurrent;

        public abstract Task build();

        /* access modifiers changed from: protected */
        @CallSuper
        public void checkConditions() {
            zzac.zzb(this.gcmTaskService != null, (Object) "Must provide an endpoint for this task by calling setService(ComponentName).");
            GcmNetworkManager.a(this.tag);
            Task.zza(this.afq);
            if (this.isPersisted) {
                Task.zzak(this.extras);
            }
        }

        public abstract Builder setExtras(Bundle bundle);

        public abstract Builder setPersisted(boolean z);

        public abstract Builder setRequiredNetwork(int i);

        public abstract Builder setRequiresCharging(boolean z);

        public abstract Builder setService(Class<? extends GcmTaskService> cls);

        public abstract Builder setTag(String str);

        public abstract Builder setUpdateCurrent(boolean z);
    }

    @Deprecated
    Task(Parcel parcel) {
        Log.e("Task", "Constructing a Task object using a parcel.");
        this.a = parcel.readString();
        this.b = parcel.readString();
        boolean z = true;
        this.c = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.d = z;
        this.e = 2;
        this.f = false;
        this.g = zzc.aff;
        this.h = null;
    }

    Task(Builder builder) {
        this.a = builder.gcmTaskService;
        this.b = builder.tag;
        this.c = builder.updateCurrent;
        this.d = builder.isPersisted;
        this.e = builder.requiredNetworkState;
        this.f = builder.requiresCharging;
        this.h = builder.extras;
        this.g = builder.afq != null ? builder.afq : zzc.aff;
    }

    private static boolean a(Object obj) {
        return (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof String) || (obj instanceof Boolean);
    }

    public static void zza(zzc zzc) {
        if (zzc != null) {
            int zzboc = zzc.zzboc();
            if (zzboc == 1 || zzboc == 0) {
                int zzbod = zzc.zzbod();
                int zzboe = zzc.zzboe();
                if (zzboc == 0 && zzbod < 0) {
                    StringBuilder sb = new StringBuilder(52);
                    sb.append("InitialBackoffSeconds can't be negative: ");
                    sb.append(zzbod);
                    throw new IllegalArgumentException(sb.toString());
                } else if (zzboc == 1 && zzbod < 10) {
                    throw new IllegalArgumentException("RETRY_POLICY_LINEAR must have an initial backoff at least 10 seconds.");
                } else if (zzboe < zzbod) {
                    int zzboe2 = zzc.zzboe();
                    StringBuilder sb2 = new StringBuilder(77);
                    sb2.append("MaximumBackoffSeconds must be greater than InitialBackoffSeconds: ");
                    sb2.append(zzboe2);
                    throw new IllegalArgumentException(sb2.toString());
                }
            } else {
                StringBuilder sb3 = new StringBuilder(45);
                sb3.append("Must provide a valid RetryPolicy: ");
                sb3.append(zzboc);
                throw new IllegalArgumentException(sb3.toString());
            }
        }
    }

    public static void zzak(Bundle bundle) {
        if (bundle != null) {
            Parcel obtain = Parcel.obtain();
            bundle.writeToParcel(obtain, 0);
            int dataSize = obtain.dataSize();
            if (dataSize > 10240) {
                obtain.recycle();
                String valueOf = String.valueOf("Extras exceeding maximum size(10240 bytes): ");
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 11);
                sb.append(valueOf);
                sb.append(dataSize);
                throw new IllegalArgumentException(sb.toString());
            }
            obtain.recycle();
            for (String str : bundle.keySet()) {
                if (!a(bundle.get(str))) {
                    throw new IllegalArgumentException("Only the following extra parameter types are supported: Integer, Long, Double, String, and Boolean. ");
                }
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public Bundle getExtras() {
        return this.h;
    }

    public int getRequiredNetwork() {
        return this.e;
    }

    public boolean getRequiresCharging() {
        return this.f;
    }

    public String getServiceName() {
        return this.a;
    }

    public String getTag() {
        return this.b;
    }

    public boolean isPersisted() {
        return this.d;
    }

    public boolean isUpdateCurrent() {
        return this.c;
    }

    public void toBundle(Bundle bundle) {
        bundle.putString("tag", this.b);
        bundle.putBoolean("update_current", this.c);
        bundle.putBoolean("persisted", this.d);
        bundle.putString(NotificationCompat.CATEGORY_SERVICE, this.a);
        bundle.putInt("requiredNetwork", this.e);
        bundle.putBoolean("requiresCharging", this.f);
        bundle.putBundle("retryStrategy", this.g.zzaj(new Bundle()));
        bundle.putBundle("extras", this.h);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c ? 1 : 0);
        parcel.writeInt(this.d ? 1 : 0);
    }
}
