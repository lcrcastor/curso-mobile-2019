package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSelectionSelectionMedioPago.MedioPago;
import com.google.gson.annotations.SerializedName;

public class MedioPagoTarjetaBean implements Parcelable {
    public static final Creator<MedioPagoTarjetaBean> CREATOR = new Creator<MedioPagoTarjetaBean>() {
        public MedioPagoTarjetaBean createFromParcel(Parcel parcel) {
            return new MedioPagoTarjetaBean(parcel);
        }

        public MedioPagoTarjetaBean[] newArray(int i) {
            return new MedioPagoTarjetaBean[i];
        }
    };
    @SerializedName("numTarjeta")
    private String numTarjeta;
    @SerializedName("tipo")
    private String tipo;

    public int describeContents() {
        return 0;
    }

    public MedioPagoTarjetaBean() {
    }

    public MedioPagoTarjetaBean(String str, String str2) {
        this.tipo = str;
        this.numTarjeta = str2;
    }

    protected MedioPagoTarjetaBean(Parcel parcel) {
        this.tipo = parcel.readString();
        this.numTarjeta = parcel.readString();
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

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.numTarjeta);
    }

    public MedioPagoTarjetaBean(MedioPago medioPago) {
        if (medioPago.isCreditCard() && !medioPago.isEmpty()) {
            this.tipo = medioPago.getTipo();
            this.numTarjeta = medioPago.getNro();
        }
    }
}
