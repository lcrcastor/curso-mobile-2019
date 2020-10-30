package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatosCuentasBean implements Parcelable {
    public static final Creator<DatosCuentasBean> CREATOR = new Creator<DatosCuentasBean>() {
        public DatosCuentasBean createFromParcel(Parcel parcel) {
            return new DatosCuentasBean(parcel);
        }

        public DatosCuentasBean[] newArray(int i) {
            return new DatosCuentasBean[i];
        }
    };
    @SerializedName("cbuDestino")
    private String cbuDestino;
    @SerializedName("descCtaDebito")
    private String descCtaDebito;
    @SerializedName("descCtaDestino")
    private String descCtaDestino;
    @SerializedName("email")
    private String email;
    @SerializedName("idMoneda")
    private String idMoneda;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("numero")
    private String numero;
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("tipoDescripcion")
    private String tipoDescripcion;
    @SerializedName("tipoDestino")
    private String tipoDestino;

    public int describeContents() {
        return 0;
    }

    public DatosCuentasBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.tipoDestino = str;
        this.tipo = str2;
        this.descCtaDebito = str3;
        this.descCtaDestino = str4;
        this.numero = str5;
        this.sucursal = str6;
        this.moneda = str7;
        this.idMoneda = str8;
        this.tipoDescripcion = str9;
        this.email = str10;
        this.cbuDestino = str11;
    }

    public String getTipoDestino() {
        return this.tipoDestino;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getDescCtaDebito() {
        return this.descCtaDebito;
    }

    public String getDescCtaDestino() {
        return this.descCtaDestino;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public String getIdMoneda() {
        return this.idMoneda;
    }

    public String getTipoDescripcion() {
        return this.tipoDescripcion;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCbuDestino() {
        return this.cbuDestino;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoDestino);
        parcel.writeString(this.tipo);
        parcel.writeString(this.descCtaDebito);
        parcel.writeString(this.descCtaDestino);
        parcel.writeString(this.numero);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.moneda);
        parcel.writeString(this.idMoneda);
        parcel.writeString(this.tipoDescripcion);
        parcel.writeString(this.email);
        parcel.writeString(this.cbuDestino);
    }

    private DatosCuentasBean(Parcel parcel) {
        this.tipoDestino = parcel.readString();
        this.tipo = parcel.readString();
        this.descCtaDebito = parcel.readString();
        this.descCtaDestino = parcel.readString();
        this.numero = parcel.readString();
        this.sucursal = parcel.readString();
        this.moneda = parcel.readString();
        this.idMoneda = parcel.readString();
        this.tipoDescripcion = parcel.readString();
        this.email = parcel.readString();
        this.cbuDestino = parcel.readString();
    }
}
