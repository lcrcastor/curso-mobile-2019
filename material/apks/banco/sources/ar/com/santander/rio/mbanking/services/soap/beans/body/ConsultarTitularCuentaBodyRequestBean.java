package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ConsultarTitularCuentaBodyRequestBean implements Parcelable {
    public static final Creator<ConsultarTitularCuentaBodyRequestBean> CREATOR = new Creator<ConsultarTitularCuentaBodyRequestBean>() {
        public ConsultarTitularCuentaBodyRequestBean createFromParcel(Parcel parcel) {
            return new ConsultarTitularCuentaBodyRequestBean(parcel);
        }

        public ConsultarTitularCuentaBodyRequestBean[] newArray(int i) {
            return new ConsultarTitularCuentaBodyRequestBean[i];
        }
    };
    @SerializedName("alias")
    private String alias;
    @SerializedName("cbu")
    private String cbu;

    public int describeContents() {
        return 0;
    }

    public ConsultarTitularCuentaBodyRequestBean() {
    }

    public ConsultarTitularCuentaBodyRequestBean(String str, String str2) {
        this.cbu = str;
        this.alias = str2;
    }

    protected ConsultarTitularCuentaBodyRequestBean(Parcel parcel) {
        this.cbu = parcel.readString();
        this.alias = parcel.readString();
    }

    public String getCbu() {
        return this.cbu;
    }

    public void setCbu(String str) {
        this.cbu = str;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.cbu);
        parcel.writeString(this.alias);
    }
}
