package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ContratarSeguroObjetoBodyRequestBean implements Parcelable {
    public static final Creator<ContratarSeguroObjetoBodyRequestBean> CREATOR = new Creator<ContratarSeguroObjetoBodyRequestBean>() {
        public ContratarSeguroObjetoBodyRequestBean createFromParcel(Parcel parcel) {
            return new ContratarSeguroObjetoBodyRequestBean(parcel);
        }

        public ContratarSeguroObjetoBodyRequestBean[] newArray(int i) {
            return new ContratarSeguroObjetoBodyRequestBean[i];
        }
    };
    @SerializedName("codOcupacion")
    private String codOcupacion;
    @SerializedName("codPlan ")
    private String codPlan;
    @SerializedName("codProducto")
    private String codProducto;
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("fotoIdObjeto")
    private String fotoIdObjeto;
    @SerializedName("fotoObjeto")
    private String fotoObjeto;
    @SerializedName("medioPagoCuenta ")
    private MedioPagoCuentaBean medioPagoCuenta;
    @SerializedName("medioPagoTarjeta ")
    private MedioPagoTarjetaBean medioPagoTarjeta;
    @SerializedName("numCotizacion")
    private String numCotizacion;
    @SerializedName("propietario")
    private String propietario;
    @SerializedName("sumaAsegurada")
    private String sumaAsegurada;
    @SerializedName("ubicacion")
    private UbicacionBean ubicacion;

    public int describeContents() {
        return 0;
    }

    public String getSumaAsegurada() {
        return this.sumaAsegurada;
    }

    public void setSumaAsegurada(String str) {
        this.sumaAsegurada = str;
    }

    public ContratarSeguroObjetoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, UbicacionBean ubicacionBean, MedioPagoCuentaBean medioPagoCuentaBean, MedioPagoTarjetaBean medioPagoTarjetaBean) {
        this.codRamo = str;
        this.codProducto = str2;
        this.sumaAsegurada = str3;
        this.numCotizacion = str4;
        this.codPlan = str5;
        this.propietario = str6;
        this.codOcupacion = str7;
        this.fotoObjeto = str8;
        this.fotoIdObjeto = str9;
        this.ubicacion = ubicacionBean;
        this.medioPagoCuenta = medioPagoCuentaBean;
        this.medioPagoTarjeta = medioPagoTarjetaBean;
    }

    public ContratarSeguroObjetoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, UbicacionBean ubicacionBean, MedioPagoCuentaBean medioPagoCuentaBean, MedioPagoTarjetaBean medioPagoTarjetaBean) {
        this.codRamo = str;
        this.codProducto = str2;
        this.numCotizacion = str3;
        this.codPlan = str4;
        this.propietario = str5;
        this.codOcupacion = str6;
        this.fotoObjeto = str7;
        this.fotoIdObjeto = str8;
        this.ubicacion = ubicacionBean;
        this.medioPagoCuenta = medioPagoCuentaBean;
        this.medioPagoTarjeta = medioPagoTarjetaBean;
    }

    protected ContratarSeguroObjetoBodyRequestBean(Parcel parcel) {
        this.codRamo = parcel.readString();
        this.codProducto = parcel.readString();
        this.sumaAsegurada = parcel.readString();
        this.numCotizacion = parcel.readString();
        this.codPlan = parcel.readString();
        this.propietario = parcel.readString();
        this.codOcupacion = parcel.readString();
        this.fotoObjeto = parcel.readString();
        this.fotoIdObjeto = parcel.readString();
        this.ubicacion = (UbicacionBean) parcel.readParcelable(UbicacionBean.class.getClassLoader());
        this.medioPagoCuenta = (MedioPagoCuentaBean) parcel.readParcelable(MedioPagoCuentaBean.class.getClassLoader());
        this.medioPagoTarjeta = (MedioPagoTarjetaBean) parcel.readParcelable(MedioPagoTarjetaBean.class.getClassLoader());
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

    public String getPropietario() {
        return this.propietario;
    }

    public void setPropietario(String str) {
        this.propietario = str;
    }

    public String getCodOcupacion() {
        return this.codOcupacion;
    }

    public void setCodOcupacion(String str) {
        this.codOcupacion = str;
    }

    public String getFotoObjeto() {
        return this.fotoObjeto;
    }

    public void setFotoObjeto(String str) {
        this.fotoObjeto = str;
    }

    public String getFotoIdObjeto() {
        return this.fotoIdObjeto;
    }

    public void setFotoIdObjeto(String str) {
        this.fotoIdObjeto = str;
    }

    public UbicacionBean getUbicacion() {
        return this.ubicacion;
    }

    public void setUbicacion(UbicacionBean ubicacionBean) {
        this.ubicacion = ubicacionBean;
    }

    public MedioPagoCuentaBean getMedioPagoCuenta() {
        return this.medioPagoCuenta;
    }

    public void setMedioPagoCuenta(MedioPagoCuentaBean medioPagoCuentaBean) {
        this.medioPagoCuenta = medioPagoCuentaBean;
    }

    public MedioPagoTarjetaBean getMedioPagoTarjeta() {
        return this.medioPagoTarjeta;
    }

    public void setMedioPagoTarjeta(MedioPagoTarjetaBean medioPagoTarjetaBean) {
        this.medioPagoTarjeta = medioPagoTarjetaBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codRamo);
        parcel.writeString(this.codProducto);
        parcel.writeString(this.sumaAsegurada);
        parcel.writeString(this.numCotizacion);
        parcel.writeString(this.codPlan);
        parcel.writeString(this.propietario);
        parcel.writeString(this.codOcupacion);
        parcel.writeString(this.fotoObjeto);
        parcel.writeString(this.fotoIdObjeto);
        parcel.writeParcelable(this.ubicacion, i);
        parcel.writeParcelable(this.medioPagoCuenta, i);
        parcel.writeParcelable(this.medioPagoTarjeta, i);
    }
}
