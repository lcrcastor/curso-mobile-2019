package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LimiteExtraccionBean implements Parcelable {
    public static final Creator<LimiteExtraccionBean> CREATOR = new Creator<LimiteExtraccionBean>() {
        public LimiteExtraccionBean createFromParcel(Parcel parcel) {
            return new LimiteExtraccionBean(parcel);
        }

        public LimiteExtraccionBean[] newArray(int i) {
            return new LimiteExtraccionBean[i];
        }
    };

    /* renamed from: id reason: collision with root package name */
    private String f263id;
    private String importe;

    public int describeContents() {
        return 0;
    }

    protected LimiteExtraccionBean(Parcel parcel) {
        this.f263id = parcel.readString();
        this.importe = parcel.readString();
    }

    public String getId() {
        return this.f263id;
    }

    public void setId(String str) {
        this.f263id = str;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f263id);
        parcel.writeString(this.importe);
    }
}
