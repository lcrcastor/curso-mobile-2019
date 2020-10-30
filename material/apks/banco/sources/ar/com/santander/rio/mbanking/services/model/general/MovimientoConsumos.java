package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class MovimientoConsumos {
    @SerializedName("codigo")
    private String codigo;
    @SerializedName("codigoTarjeta")
    private String codigoTarjeta;
    @SerializedName("establecimiento")
    private String establecimiento;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("importe")
    private String importe;
    @SerializedName("ticket")
    private String ticket;
    @SerializedName("tipoMoneda")
    private String tipoMoneda;

    public MovimientoConsumos(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.establecimiento = str;
        this.codigo = str2;
        this.importe = str3;
        this.codigoTarjeta = str4;
        this.fecha = str5;
        this.ticket = str6;
        this.tipoMoneda = str7;
    }

    public MovimientoConsumos() {
    }

    public String getEstablecimiento() {
        return this.establecimiento;
    }

    public void setEstablecimiento(String str) {
        this.establecimiento = str;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String str) {
        this.codigo = str;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public String getCodigoTarjeta() {
        return this.codigoTarjeta;
    }

    public void setCodigoTarjeta(String str) {
        this.codigoTarjeta = str;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public String getTicket() {
        return this.ticket;
    }

    public void setTicket(String str) {
        this.ticket = str;
    }

    public String getTipoMoneda() {
        return this.tipoMoneda;
    }

    public void setTipoMoneda(String str) {
        this.tipoMoneda = str;
    }
}
