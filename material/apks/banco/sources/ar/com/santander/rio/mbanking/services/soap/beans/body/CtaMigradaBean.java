package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CtaMigradaBean implements Parcelable {
    public static final Creator<CtaMigradaBean> CREATOR = new Creator<CtaMigradaBean>() {
        public CtaMigradaBean createFromParcel(Parcel parcel) {
            return new CtaMigradaBean(parcel);
        }

        public CtaMigradaBean[] newArray(int i) {
            return new CtaMigradaBean[i];
        }
    };
    @SerializedName("codMensaje")
    @Expose
    private String codMensaje;
    @SerializedName("estadoMigrado")
    @Expose
    private String estadoMigrado;
    @SerializedName("mensajeCtaMigrada")
    @Expose
    private String mensajeCtaMigrada;
    @SerializedName("numero")
    @Expose
    private String numero;
    @SerializedName("sucursal")
    @Expose
    private String sucursal;

    public int describeContents() {
        return 0;
    }

    protected CtaMigradaBean(Parcel parcel) {
        this.estadoMigrado = (String) parcel.readValue(String.class.getClassLoader());
        this.codMensaje = (String) parcel.readValue(String.class.getClassLoader());
        this.mensajeCtaMigrada = (String) parcel.readValue(String.class.getClassLoader());
        this.sucursal = (String) parcel.readValue(String.class.getClassLoader());
        this.numero = (String) parcel.readValue(String.class.getClassLoader());
    }

    public CtaMigradaBean() {
    }

    public CtaMigradaBean(String str, String str2, String str3, String str4, String str5) {
        this.estadoMigrado = str;
        this.codMensaje = str2;
        this.mensajeCtaMigrada = str3;
        this.sucursal = str4;
        this.numero = str5;
    }

    public String getEstadoMigrado() {
        return this.estadoMigrado;
    }

    public void setEstadoMigrado(String str) {
        this.estadoMigrado = str;
    }

    public String getCodMensaje() {
        return this.codMensaje;
    }

    public void setCodMensaje(String str) {
        this.codMensaje = str;
    }

    public String getMensajeCtaMigrada() {
        return this.mensajeCtaMigrada;
    }

    public void setMensajeCtaMigrada(String str) {
        this.mensajeCtaMigrada = str;
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
        parcel.writeValue(this.estadoMigrado);
        parcel.writeValue(this.codMensaje);
        parcel.writeValue(this.mensajeCtaMigrada);
        parcel.writeValue(this.sucursal);
        parcel.writeValue(this.numero);
    }
}
