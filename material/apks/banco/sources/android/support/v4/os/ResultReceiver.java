package android.support.v4.os;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.os.IResultReceiver.Stub;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ResultReceiver implements Parcelable {
    public static final Creator<ResultReceiver> CREATOR = new Creator<ResultReceiver>() {
        /* renamed from: a */
        public ResultReceiver createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        /* renamed from: a */
        public ResultReceiver[] newArray(int i) {
            return new ResultReceiver[i];
        }
    };
    final boolean a;
    final Handler b;
    IResultReceiver c;

    class MyResultReceiver extends Stub {
        MyResultReceiver() {
        }

        public void send(int i, Bundle bundle) {
            if (ResultReceiver.this.b != null) {
                ResultReceiver.this.b.post(new MyRunnable(i, bundle));
            } else {
                ResultReceiver.this.onReceiveResult(i, bundle);
            }
        }
    }

    class MyRunnable implements Runnable {
        final int a;
        final Bundle b;

        MyRunnable(int i, Bundle bundle) {
            this.a = i;
            this.b = bundle;
        }

        public void run() {
            ResultReceiver.this.onReceiveResult(this.a, this.b);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void onReceiveResult(int i, Bundle bundle) {
    }

    public ResultReceiver(Handler handler) {
        this.a = true;
        this.b = handler;
    }

    public void send(int i, Bundle bundle) {
        if (this.a) {
            if (this.b != null) {
                this.b.post(new MyRunnable(i, bundle));
            } else {
                onReceiveResult(i, bundle);
            }
            return;
        }
        if (this.c != null) {
            try {
                this.c.send(i, bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this) {
            if (this.c == null) {
                this.c = new MyResultReceiver();
            }
            parcel.writeStrongBinder(this.c.asBinder());
        }
    }

    ResultReceiver(Parcel parcel) {
        this.a = false;
        this.b = null;
        this.c = Stub.asInterface(parcel.readStrongBinder());
    }
}
