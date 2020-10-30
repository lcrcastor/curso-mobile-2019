package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.model.AsymmetricItem;
import java.util.ArrayList;
import java.util.List;

class RowInfo<T extends AsymmetricItem> implements Parcelable {
    public static final Creator<RowInfo> CREATOR = new Creator<RowInfo>() {
        /* renamed from: a */
        public RowInfo createFromParcel(Parcel parcel) {
            return new RowInfo(parcel);
        }

        /* renamed from: a */
        public RowInfo[] newArray(int i) {
            return new RowInfo[i];
        }
    };
    private final List<T> a;
    private final int b;
    private final float c;

    public int describeContents() {
        return 0;
    }

    public RowInfo(int i, List<T> list, float f) {
        this.b = i;
        this.a = list;
        this.c = f;
    }

    public RowInfo(Parcel parcel) {
        this.b = parcel.readInt();
        this.c = parcel.readFloat();
        int readInt = parcel.readInt();
        this.a = new ArrayList();
        ClassLoader classLoader = AsymmetricItem.class.getClassLoader();
        for (int i = 0; i < readInt; i++) {
            this.a.add((AsymmetricItem) parcel.readParcelable(classLoader));
        }
    }

    public List<T> a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public float c() {
        return this.c;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.b);
        parcel.writeFloat(this.c);
        parcel.writeInt(this.a.size());
        for (int i2 = 0; i2 < this.a.size(); i2++) {
            parcel.writeParcelable((Parcelable) this.a.get(i2), 0);
        }
    }
}
