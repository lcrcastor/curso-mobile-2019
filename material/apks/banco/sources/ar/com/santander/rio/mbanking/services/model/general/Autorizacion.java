package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Autorizacion {
    @SerializedName("codigo")
    private String codigo;
    private String codigo_establecimiento;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("establecimiento")
    private Establecimiento establecimiento;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("importe")
    private String importe;
    @SerializedName("internacional")
    private String internacional;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("tipo")
    private String tipo;

    public Autorizacion(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.codigo_establecimiento = "";
        this.codigo = "";
        this.descripcion = "";
        this.fecha = "";
        this.importe = "";
        this.internacional = "";
        this.moneda = "";
        this.tipo = "";
        this.codigo_establecimiento = this.codigo_establecimiento;
        this.codigo = str2;
        this.descripcion = str3;
        this.fecha = str4;
        this.importe = str5;
        this.internacional = str6;
        this.moneda = str7;
        this.tipo = str8;
    }

    public Autorizacion() {
        this.codigo_establecimiento = "";
        this.codigo = "";
        this.descripcion = "";
        this.fecha = "";
        this.importe = "";
        this.internacional = "";
        this.moneda = "";
        this.tipo = "";
    }

    public Establecimiento getEstablecimiento() {
        return this.establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento2) {
        this.establecimiento = establecimiento2;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String str) {
        this.codigo = str;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getCodigo_establecimiento() {
        return this.codigo_establecimiento;
    }

    public void setCodigo_establecimiento(String str) {
        this.codigo_establecimiento = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getInternacional() {
        return this.internacional;
    }

    public void setInternacional(String str) {
        this.internacional = str;
    }
}
