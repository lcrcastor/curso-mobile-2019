package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatoSeguroBean implements Parcelable {
    public static final Creator<DatoSeguroBean> CREATOR = new Creator<DatoSeguroBean>() {
        public DatoSeguroBean createFromParcel(Parcel parcel) {
            return new DatoSeguroBean(parcel);
        }

        public DatoSeguroBean[] newArray(int i) {
            return new DatoSeguroBean[i];
        }
    };
    @SerializedName("desc")
    private String desc;
    @SerializedName("valor")
    private String valor;

    public int describeContents() {
        return 0;
    }

    public DatoSeguroBean() {
    }

    public DatoSeguroBean(String str, String str2) {
        this.desc = str;
        this.valor = str2;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String str) {
        this.valor = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.desc);
        parcel.writeString(this.valor);
    }

    protected DatoSeguroBean(Parcel parcel) {
        this.desc = parcel.readString();
        this.valor = parcel.readString();
    }
}
