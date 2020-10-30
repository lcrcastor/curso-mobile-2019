package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class TarjetaBean {
    public static final Creator<TarjetaBean> CREATOR = new Creator<TarjetaBean>() {
        public TarjetaBean createFromParcel(Parcel parcel) {
            return new TarjetaBean(parcel);
        }

        public TarjetaBean[] newArray(int i) {
            return new TarjetaBean[i];
        }
    };
    private Boolean isChecked;
    private Integer listPosition;
    @SerializedName("numCuenta")
    private String numCuenta;
    @SerializedName("numTarjeta")
    private String numTarjeta;
    @SerializedName("tipo")
    private String tipo;

    public int describeContents() {
        return 0;
    }

    public TarjetaBean(String str, String str2, String str3) {
        this.tipo = str;
        this.numTarjeta = str2;
        this.numCuenta = str3;
    }

    protected TarjetaBean(Parcel parcel) {
        this.tipo = parcel.readString();
        this.numTarjeta = parcel.readString();
        this.numCuenta = parcel.readString();
    }

    public int getListPosition() {
        return this.listPosition.intValue();
    }

    public void setListPosition(int i) {
        this.listPosition = Integer.valueOf(i);
    }

    public Boolean isChecked() {
        if (this.isChecked != null) {
            return this.isChecked;
        }
        return Boolean.valueOf(false);
    }

    public void setChecked(boolean z) {
        this.isChecked = Boolean.valueOf(z);
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getNumTarjeta() {
        return this.numTarjeta;
    }

    public void setNumTarjeta(String str) {
        this.numTarjeta = str;
    }

    public String getNumCuenta() {
        return this.numCuenta;
    }

    public void setNumCuenta(String str) {
        this.numCuenta = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.numTarjeta);
        parcel.writeString(this.numCuenta);
    }
}
