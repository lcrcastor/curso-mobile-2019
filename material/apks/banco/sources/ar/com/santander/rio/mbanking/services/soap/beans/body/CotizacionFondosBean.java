package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CotizacionFondosBean implements Parcelable {
    public static final Creator<CotizacionFondosBean> CREATOR = new Creator<CotizacionFondosBean>() {
        public CotizacionFondosBean createFromParcel(Parcel parcel) {
            return new CotizacionFondosBean(parcel);
        }

        public CotizacionFondosBean[] newArray(int i) {
            return new CotizacionFondosBean[i];
        }
    };
    @SerializedName("detalle")
    private String detalle;
    @SerializedName("valor")
    private String valor;

    public int describeContents() {
        return 0;
    }

    public CotizacionFondosBean(String str) {
        this.detalle = str;
    }

    public CotizacionFondosBean() {
    }

    public String getDetalle() {
        return this.detalle;
    }

    public String getValor() {
        return this.valor;
    }

    public void setDetalle(String str) {
        this.detalle = str;
    }

    public void setValor(String str) {
        this.valor = str;
    }

    protected CotizacionFondosBean(Parcel parcel) {
        this.detalle = parcel.readString();
        this.valor = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.detalle);
        parcel.writeString(this.valor);
    }
}
