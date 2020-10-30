package android.support.v4.app;

import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;

final class FragmentState implements Parcelable {
    public static final Creator<FragmentState> CREATOR = new Creator<FragmentState>() {
        /* renamed from: a */
        public FragmentState createFromParcel(Parcel parcel) {
            return new FragmentState(parcel);
        }

        /* renamed from: a */
        public FragmentState[] newArray(int i) {
            return new FragmentState[i];
        }
    };
    final String a;
    final int b;
    final boolean c;
    final int d;
    final int e;
    final String f;
    final boolean g;
    final boolean h;
    final Bundle i;
    final boolean j;
    Bundle k;
    Fragment l;

    public int describeContents() {
        return 0;
    }

    FragmentState(Fragment fragment) {
        this.a = fragment.getClass().getName();
        this.b = fragment.n;
        this.c = fragment.v;
        this.d = fragment.G;
        this.e = fragment.H;
        this.f = fragment.I;
        this.g = fragment.L;
        this.h = fragment.K;
        this.i = fragment.p;
        this.j = fragment.J;
    }

    FragmentState(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
        boolean z = false;
        this.c = parcel.readInt() != 0;
        this.d = parcel.readInt();
        this.e = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readInt() != 0;
        this.h = parcel.readInt() != 0;
        this.i = parcel.readBundle();
        if (parcel.readInt() != 0) {
            z = true;
        }
        this.j = z;
        this.k = parcel.readBundle();
    }

    public Fragment a(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment, FragmentManagerNonConfig fragmentManagerNonConfig, ViewModelStore viewModelStore) {
        if (this.l == null) {
            Context c2 = fragmentHostCallback.c();
            if (this.i != null) {
                this.i.setClassLoader(c2.getClassLoader());
            }
            if (fragmentContainer != null) {
                this.l = fragmentContainer.instantiate(c2, this.a, this.i);
            } else {
                this.l = Fragment.instantiate(c2, this.a, this.i);
            }
            if (this.k != null) {
                this.k.setClassLoader(c2.getClassLoader());
                this.l.l = this.k;
            }
            this.l.a(this.b, fragment);
            this.l.v = this.c;
            this.l.x = true;
            this.l.G = this.d;
            this.l.H = this.e;
            this.l.I = this.f;
            this.l.L = this.g;
            this.l.K = this.h;
            this.l.J = this.j;
            this.l.A = fragmentHostCallback.d;
            if (FragmentManagerImpl.a) {
                StringBuilder sb = new StringBuilder();
                sb.append("Instantiated fragment ");
                sb.append(this.l);
                Log.v("FragmentManager", sb.toString());
            }
        }
        this.l.D = fragmentManagerNonConfig;
        this.l.E = viewModelStore;
        return this.l;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c ? 1 : 0);
        parcel.writeInt(this.d);
        parcel.writeInt(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.g ? 1 : 0);
        parcel.writeInt(this.h ? 1 : 0);
        parcel.writeBundle(this.i);
        parcel.writeInt(this.j ? 1 : 0);
        parcel.writeBundle(this.k);
    }
}
