package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CompradorBean implements Parcelable {
    public static final Creator<CompradorBean> CREATOR = new Creator<CompradorBean>() {
        public CompradorBean createFromParcel(Parcel parcel) {
            return new CompradorBean(parcel);
        }

        public CompradorBean[] newArray(int i) {
            return new CompradorBean[i];
        }
    };
    @SerializedName("cuentaComprador")
    private CuentaCompradorBean cuentaCompradorBean;
    @SerializedName("cuit")
    private String cuit;
    @SerializedName("titular")
    private String titular = "";

    public int describeContents() {
        return 0;
    }

    public CompradorBean() {
    }

    public CompradorBean(String str, String str2, CuentaCompradorBean cuentaCompradorBean2) {
        this.titular = str;
        this.cuit = str2;
        this.cuentaCompradorBean = cuentaCompradorBean2;
    }

    protected CompradorBean(Parcel parcel) {
        this.titular = parcel.readString();
        this.cuit = parcel.readString();
        this.cuentaCompradorBean = (CuentaCompradorBean) parcel.readParcelable(CuentaCompradorBean.class.getClassLoader());
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String str) {
        this.titular = str;
    }

    public String getCuit() {
        return this.cuit;
    }

    public void setCuit(String str) {
        this.cuit = str;
    }

    public CuentaCompradorBean getCuentaCompradorBean() {
        return this.cuentaCompradorBean;
    }

    public void setCuentaCompradorBean(CuentaCompradorBean cuentaCompradorBean2) {
        this.cuentaCompradorBean = cuentaCompradorBean2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.titular);
        parcel.writeString(this.cuit);
        parcel.writeParcelable(this.cuentaCompradorBean, i);
    }
}
