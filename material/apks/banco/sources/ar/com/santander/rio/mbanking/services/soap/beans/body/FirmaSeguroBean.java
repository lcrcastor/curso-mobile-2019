package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class FirmaSeguroBean implements Parcelable {
    public static final Creator<FirmaSeguroBean> CREATOR = new Creator<FirmaSeguroBean>() {
        public FirmaSeguroBean createFromParcel(Parcel parcel) {
            return new FirmaSeguroBean(parcel);
        }

        public FirmaSeguroBean[] newArray(int i) {
            return new FirmaSeguroBean[i];
        }
    };
    @SerializedName("firmaSeguro")
    private String firmaSeguro;

    public int describeContents() {
        return 0;
    }

    public FirmaSeguroBean(String str) {
        this.firmaSeguro = str;
    }

    public FirmaSeguroBean() {
    }

    public String getFirmaSeguro() {
        return this.firmaSeguro;
    }

    public void setFirmaSeguro(String str) {
        this.firmaSeguro = str;
    }

    protected FirmaSeguroBean(Parcel parcel) {
        this.firmaSeguro = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.firmaSeguro);
    }
}
