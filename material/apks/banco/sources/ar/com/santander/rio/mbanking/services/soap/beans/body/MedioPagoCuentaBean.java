package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSelectionSelectionMedioPago.MedioPago;
import com.google.gson.annotations.SerializedName;

public class MedioPagoCuentaBean implements Parcelable {
    public static final Creator<MedioPagoCuentaBean> CREATOR = new Creator<MedioPagoCuentaBean>() {
        public MedioPagoCuentaBean createFromParcel(Parcel parcel) {
            return new MedioPagoCuentaBean(parcel);
        }

        public MedioPagoCuentaBean[] newArray(int i) {
            return new MedioPagoCuentaBean[i];
        }
    };
    @SerializedName("numero")
    private String numero;
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;

    public int describeContents() {
        return 0;
    }

    public MedioPagoCuentaBean() {
    }

    public MedioPagoCuentaBean(String str, String str2, String str3) {
        this.tipo = str;
        this.sucursal = str2;
        this.numero = str3;
    }

    protected MedioPagoCuentaBean(Parcel parcel) {
        this.tipo = parcel.readString();
        this.sucursal = parcel.readString();
        this.numero = parcel.readString();
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.numero);
    }

    public MedioPagoCuentaBean(MedioPago medioPago) {
        if (medioPago.isAccount() && !medioPago.isEmpty()) {
            this.numero = medioPago.getNro();
            this.sucursal = medioPago.getSucursal();
            this.tipo = medioPago.getTipo();
            this.numero = medioPago.getNro();
        }
    }
}
