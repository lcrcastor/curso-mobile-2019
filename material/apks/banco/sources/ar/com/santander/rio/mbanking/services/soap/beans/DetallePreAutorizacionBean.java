package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompradorBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VendedorBean;
import com.google.gson.annotations.SerializedName;

public class DetallePreAutorizacionBean implements Parcelable {
    public static final Creator<DetallePreAutorizacionBean> CREATOR = new Creator<DetallePreAutorizacionBean>() {
        public DetallePreAutorizacionBean createFromParcel(Parcel parcel) {
            return new DetallePreAutorizacionBean(parcel);
        }

        public DetallePreAutorizacionBean[] newArray(int i) {
            return new DetallePreAutorizacionBean[i];
        }
    };
    @SerializedName("cantidad")
    private String cantidad;
    @SerializedName("codConcepto")
    private String codConcepto;
    @SerializedName("codEstado")
    private String codEstado;
    @SerializedName("comprador")
    private CompradorBean comprador;
    @SerializedName("detalle")
    private String detalle;
    @SerializedName("fechaCreacion")
    private String fechaCreacion;
    @SerializedName("idPreautorizacion")
    private String idPreautorizacion;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("montoPeriodo")
    private String montoPeriodo;
    @SerializedName("montoPorDebin")
    private String montoPorDebin;
    @SerializedName("mostrarAceptarRechazar")
    private String mostrarAceptarRechazar;
    @SerializedName("mostrarDesvincular")
    private String mostrarDesvincular;
    @SerializedName("periodo")
    private String periodo;
    @SerializedName("vendedor")
    private VendedorBean vendedor;

    public int describeContents() {
        return 0;
    }

    protected DetallePreAutorizacionBean(Parcel parcel) {
        this.comprador = (CompradorBean) parcel.readValue(CompradorBean.class.getClassLoader());
        this.vendedor = (VendedorBean) parcel.readValue(VendedorBean.class.getClassLoader());
        this.idPreautorizacion = (String) parcel.readValue(String.class.getClassLoader());
        this.codEstado = (String) parcel.readValue(String.class.getClassLoader());
        this.montoPeriodo = (String) parcel.readValue(String.class.getClassLoader());
        this.montoPorDebin = (String) parcel.readValue(String.class.getClassLoader());
        this.moneda = (String) parcel.readValue(String.class.getClassLoader());
        this.cantidad = (String) parcel.readValue(String.class.getClassLoader());
        this.detalle = (String) parcel.readValue(String.class.getClassLoader());
        this.fechaCreacion = (String) parcel.readValue(String.class.getClassLoader());
        this.codConcepto = (String) parcel.readValue(String.class.getClassLoader());
        this.periodo = (String) parcel.readValue(String.class.getClassLoader());
        this.mostrarAceptarRechazar = (String) parcel.readValue(String.class.getClassLoader());
        this.mostrarDesvincular = (String) parcel.readValue(String.class.getClassLoader());
    }

    public DetallePreAutorizacionBean() {
    }

    public DetallePreAutorizacionBean(CompradorBean compradorBean, VendedorBean vendedorBean, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        this.comprador = compradorBean;
        this.vendedor = vendedorBean;
        this.idPreautorizacion = str;
        this.codEstado = str2;
        this.montoPeriodo = str3;
        this.montoPorDebin = str4;
        this.moneda = str5;
        this.cantidad = str6;
        this.detalle = str7;
        this.fechaCreacion = str8;
        this.codConcepto = str9;
        this.periodo = str10;
        this.mostrarAceptarRechazar = str11;
        this.mostrarDesvincular = str12;
    }

    public CompradorBean getComprador() {
        return this.comprador;
    }

    public void setComprador(CompradorBean compradorBean) {
        this.comprador = compradorBean;
    }

    public VendedorBean getVendedor() {
        return this.vendedor;
    }

    public void setVendedor(VendedorBean vendedorBean) {
        this.vendedor = vendedorBean;
    }

    public String getIdPreautorizacion() {
        return this.idPreautorizacion;
    }

    public void setIdPreautorizacion(String str) {
        this.idPreautorizacion = str;
    }

    public String getCodEstado() {
        return this.codEstado;
    }

    public void setCodEstado(String str) {
        this.codEstado = str;
    }

    public String getMontoPeriodo() {
        return this.montoPeriodo;
    }

    public void setMontoPeriodo(String str) {
        this.montoPeriodo = str;
    }

    public String getMontoPorDebin() {
        return this.montoPorDebin;
    }

    public void setMontoPorDebin(String str) {
        this.montoPorDebin = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(String str) {
        this.cantidad = str;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public void setDetalle(String str) {
        this.detalle = str;
    }

    public String getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(String str) {
        this.fechaCreacion = str;
    }

    public String getCodConcepto() {
        return this.codConcepto;
    }

    public void setCodConcepto(String str) {
        this.codConcepto = str;
    }

    public String getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(String str) {
        this.periodo = str;
    }

    public String getMostrarAceptarRechazar() {
        return this.mostrarAceptarRechazar;
    }

    public void setMostrarAceptarRechazar(String str) {
        this.mostrarAceptarRechazar = str;
    }

    public String getMostrarDesvincular() {
        return this.mostrarDesvincular;
    }

    public void setMostrarDesvincular(String str) {
        this.mostrarDesvincular = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.comprador);
        parcel.writeValue(this.vendedor);
        parcel.writeValue(this.idPreautorizacion);
        parcel.writeValue(this.codEstado);
        parcel.writeValue(this.montoPeriodo);
        parcel.writeValue(this.montoPorDebin);
        parcel.writeValue(this.moneda);
        parcel.writeValue(this.cantidad);
        parcel.writeValue(this.detalle);
        parcel.writeValue(this.fechaCreacion);
        parcel.writeValue(this.codConcepto);
        parcel.writeValue(this.periodo);
        parcel.writeValue(this.mostrarAceptarRechazar);
        parcel.writeValue(this.mostrarDesvincular);
    }
}
