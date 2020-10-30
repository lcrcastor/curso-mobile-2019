package com.google.android.gms.iid;

import android.annotation.TargetApi;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MessengerCompat implements Parcelable {
    public static final Creator<MessengerCompat> CREATOR = new Creator<MessengerCompat>() {
        /* renamed from: a */
        public MessengerCompat createFromParcel(Parcel parcel) {
            IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder != null) {
                return new MessengerCompat(readStrongBinder);
            }
            return null;
        }

        /* renamed from: a */
        public MessengerCompat[] newArray(int i) {
            return new MessengerCompat[i];
        }
    };
    Messenger a;
    zzb b;

    final class zza extends com.google.android.gms.iid.zzb.zza {
        Handler a;

        zza(Handler handler) {
            this.a = handler;
        }

        public void send(Message message) {
            message.arg2 = Binder.getCallingUid();
            this.a.dispatchMessage(message);
        }
    }

    public MessengerCompat(Handler handler) {
        if (VERSION.SDK_INT >= 21) {
            this.a = new Messenger(handler);
        } else {
            this.b = new zza(handler);
        }
    }

    public MessengerCompat(IBinder iBinder) {
        if (VERSION.SDK_INT >= 21) {
            this.a = new Messenger(iBinder);
        } else {
            this.b = com.google.android.gms.iid.zzb.zza.zzgw(iBinder);
        }
    }

    @TargetApi(21)
    private static int a(Message message) {
        return message.sendingUid;
    }

    public static int zzc(Message message) {
        return VERSION.SDK_INT >= 21 ? a(message) : message.arg2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return getBinder().equals(((MessengerCompat) obj).getBinder());
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public IBinder getBinder() {
        return this.a != null ? this.a.getBinder() : this.b.asBinder();
    }

    public int hashCode() {
        return getBinder().hashCode();
    }

    public void send(Message message) {
        if (this.a != null) {
            this.a.send(message);
        } else {
            this.b.send(message);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.a != null ? this.a.getBinder() : this.b.asBinder());
    }
}
