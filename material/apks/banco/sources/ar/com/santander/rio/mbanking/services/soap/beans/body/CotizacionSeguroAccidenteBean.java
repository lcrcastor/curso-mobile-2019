package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CotizacionSeguroAccidenteBean implements Parcelable {
    public static final Creator<CotizacionSeguroAccidenteBean> CREATOR = new Creator<CotizacionSeguroAccidenteBean>() {
        public CotizacionSeguroAccidenteBean createFromParcel(Parcel parcel) {
            return new CotizacionSeguroAccidenteBean(parcel);
        }

        public CotizacionSeguroAccidenteBean[] newArray(int i) {
            return new CotizacionSeguroAccidenteBean[i];
        }
    };
    @SerializedName("beneficiarios")
    private String beneficiarios;
    @SerializedName("codPlan")
    private String codPlan;
    @SerializedName("codProducto")
    private String codProducto;
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("cuota")
    private String cuota;
    @SerializedName("descCobertura")
    private String descCobertura;
    @SerializedName("formaPago")
    private String formaPago;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("numCotizacion")
    private String numCotizacion;
    @SerializedName("sumaAsegurada")
    private String sumaAsegurada;

    public int describeContents() {
        return 0;
    }

    public CotizacionSeguroAccidenteBean() {
    }

    public CotizacionSeguroAccidenteBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        this.sumaAsegurada = str;
        this.codProducto = str2;
        this.formaPago = str3;
        this.codRamo = str4;
        this.cuota = str5;
        this.codPlan = str6;
        this.nombre = str7;
        this.numCotizacion = str8;
        this.descCobertura = str9;
        this.beneficiarios = str10;
    }

    protected CotizacionSeguroAccidenteBean(Parcel parcel) {
        this.sumaAsegurada = parcel.readString();
        this.codProducto = parcel.readString();
        this.formaPago = parcel.readString();
        this.codRamo = parcel.readString();
        this.cuota = parcel.readString();
        this.codPlan = parcel.readString();
        this.nombre = parcel.readString();
        this.numCotizacion = parcel.readString();
        this.descCobertura = parcel.readString();
        this.beneficiarios = parcel.readString();
    }

    public String getSumaAsegurada() {
        return this.sumaAsegurada;
    }

    public void setSumaAsegurada(String str) {
        this.sumaAsegurada = str;
    }

    public String getCodProducto() {
        return this.codProducto;
    }

    public void setCodProducto(String str) {
        this.codProducto = str;
    }

    public String getFormaPago() {
        return this.formaPago;
    }

    public void setFormaPago(String str) {
        this.formaPago = str;
    }

    public String getCodRamo() {
        return this.codRamo;
    }

    public void setCodRamo(String str) {
        this.codRamo = str;
    }

    public String getCuota() {
        return this.cuota;
    }

    public void setCuota(String str) {
        this.cuota = str;
    }

    public String getCodPlan() {
        return this.codPlan;
    }

    public void setCodPlan(String str) {
        this.codPlan = str;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getNumCotizacion() {
        return this.numCotizacion;
    }

    public void setNumCotizacion(String str) {
        this.numCotizacion = str;
    }

    public String getDescCobertura() {
        return this.descCobertura;
    }

    public void setDescCobertura(String str) {
        this.descCobertura = str;
    }

    public String getBeneficiarios() {
        return this.beneficiarios;
    }

    public void setBeneficiarios(String str) {
        this.beneficiarios = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sumaAsegurada);
        parcel.writeString(this.codProducto);
        parcel.writeString(this.formaPago);
        parcel.writeString(this.codRamo);
        parcel.writeString(this.cuota);
        parcel.writeString(this.codPlan);
        parcel.writeString(this.nombre);
        parcel.writeString(this.numCotizacion);
        parcel.writeString(this.descCobertura);
        parcel.writeString(this.beneficiarios);
    }
}
