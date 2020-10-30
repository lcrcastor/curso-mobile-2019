package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AsistenciaSeguroBean implements Parcelable {
    public static final Creator<AsistenciaSeguroBean> CREATOR = new Creator<AsistenciaSeguroBean>() {
        public AsistenciaSeguroBean createFromParcel(Parcel parcel) {
            return new AsistenciaSeguroBean(parcel);
        }

        public AsistenciaSeguroBean[] newArray(int i) {
            return new AsistenciaSeguroBean[i];
        }
    };
    @SerializedName("desc")
    private String desc;
    @SerializedName("tel")
    private String tel;

    public int describeContents() {
        return 0;
    }

    public AsistenciaSeguroBean() {
    }

    public AsistenciaSeguroBean(String str, String str2) {
        this.desc = str;
        this.tel = str2;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String str) {
        this.tel = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.desc);
        parcel.writeString(this.tel);
    }

    protected AsistenciaSeguroBean(Parcel parcel) {
        this.desc = parcel.readString();
        this.tel = parcel.readString();
    }
}
