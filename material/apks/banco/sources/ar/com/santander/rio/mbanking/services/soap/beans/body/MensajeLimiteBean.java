package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MensajeLimiteBean implements Parcelable {
    public static final Creator<MensajeLimiteBean> CREATOR = new Creator<MensajeLimiteBean>() {
        public MensajeLimiteBean createFromParcel(Parcel parcel) {
            return new MensajeLimiteBean(parcel);
        }

        public MensajeLimiteBean[] newArray(int i) {
            return new MensajeLimiteBean[i];
        }
    };
    public String msjCod;
    public String msjDesc;

    public int describeContents() {
        return 0;
    }

    protected MensajeLimiteBean(Parcel parcel) {
        this.msjCod = parcel.readString();
        this.msjDesc = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.msjCod);
        parcel.writeString(this.msjDesc);
    }
}
