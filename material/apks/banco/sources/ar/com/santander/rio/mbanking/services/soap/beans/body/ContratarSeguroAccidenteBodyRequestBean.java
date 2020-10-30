package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ContratarSeguroAccidenteBodyRequestBean implements Parcelable {
    public static final Creator<ContratarSeguroAccidenteBodyRequestBean> CREATOR = new Creator<ContratarSeguroAccidenteBodyRequestBean>() {
        public ContratarSeguroAccidenteBodyRequestBean createFromParcel(Parcel parcel) {
            return new ContratarSeguroAccidenteBodyRequestBean(parcel);
        }

        public ContratarSeguroAccidenteBodyRequestBean[] newArray(int i) {
            return new ContratarSeguroAccidenteBodyRequestBean[i];
        }
    };
    @SerializedName("codPlan")
    private String codPlan;
    @SerializedName("codProducto")
    private String codProducto;
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("numCotizacion")
    private String numCotizacion;
    @SerializedName("numeroCuenta")
    private String numeroCuenta;
    @SerializedName("sucursalCuenta")
    private String sucursalCuenta;
    @SerializedName("tipoCuenta")
    private String tipoCuenta;

    public int describeContents() {
        return 0;
    }

    public ContratarSeguroAccidenteBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.codRamo = str;
        this.codProducto = str2;
        this.numCotizacion = str3;
        this.codPlan = str4;
        this.tipoCuenta = str5;
        this.sucursalCuenta = str6;
        this.numeroCuenta = str7;
    }

    public ContratarSeguroAccidenteBodyRequestBean() {
    }

    protected ContratarSeguroAccidenteBodyRequestBean(Parcel parcel) {
        this.codRamo = parcel.readString();
        this.codProducto = parcel.readString();
        this.numCotizacion = parcel.readString();
        this.codPlan = parcel.readString();
        this.tipoCuenta = parcel.readString();
        this.sucursalCuenta = parcel.readString();
        this.numeroCuenta = parcel.readString();
    }

    public String getCodRamo() {
        return this.codRamo;
    }

    public void setCodRamo(String str) {
        this.codRamo = str;
    }

    public String getCodProducto() {
        return this.codProducto;
    }

    public void setCodProducto(String str) {
        this.codProducto = str;
    }

    public String getNumCotizacion() {
        return this.numCotizacion;
    }

    public void setNumCotizacion(String str) {
        this.numCotizacion = str;
    }

    public String getCodPlan() {
        return this.codPlan;
    }

    public void setCodPlan(String str) {
        this.codPlan = str;
    }

    public String getTipoCuenta() {
        return this.tipoCuenta;
    }

    public void setTipoCuenta(String str) {
        this.tipoCuenta = str;
    }

    public String getSucursalCuenta() {
        return this.sucursalCuenta;
    }

    public void setSucursalCuenta(String str) {
        this.sucursalCuenta = str;
    }

    public String getNumeroCuenta() {
        return this.numeroCuenta;
    }

    public void setNumeroCuenta(String str) {
        this.numeroCuenta = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codRamo);
        parcel.writeString(this.codProducto);
        parcel.writeString(this.numCotizacion);
        parcel.writeString(this.codPlan);
        parcel.writeString(this.tipoCuenta);
        parcel.writeString(this.sucursalCuenta);
        parcel.writeString(this.numeroCuenta);
    }
}
