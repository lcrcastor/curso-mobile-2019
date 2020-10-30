package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class FechaLiquidacion {
    @SerializedName("cierre")
    private String cierre;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("vencimiento")
    private String vencimiento;

    public FechaLiquidacion(String str, String str2, String str3) {
        this.cierre = str2;
        this.descripcion = str;
        this.vencimiento = str3;
    }

    public FechaLiquidacion() {
    }

    public String getCierre() {
        return this.cierre;
    }

    public void setCierre(String str) {
        this.cierre = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getVencimiento() {
        return this.vencimiento;
    }

    public void setVencimiento(String str) {
        this.vencimiento = str;
    }
}
