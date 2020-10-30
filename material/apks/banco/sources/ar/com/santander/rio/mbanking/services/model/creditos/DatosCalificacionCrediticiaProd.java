package ar.com.santander.rio.mbanking.services.model.creditos;

import com.google.gson.annotations.SerializedName;

public class DatosCalificacionCrediticiaProd {
    @SerializedName("codInhabilitCli")
    public String codInhabilitCli;
    @SerializedName("descripInhabilitado")
    public String descripInhabilitado;
    @SerializedName("impDispCuota")
    public String impDispCuota;
    @SerializedName("impDispPrest")
    public String impDispPrest;
    @SerializedName("impIngreso")
    public String impIngreso;
    @SerializedName("porcAfectAcuerdo")
    public String porcAfectAcuerdo;

    public String getPorcAfectAcuerdo() {
        return this.porcAfectAcuerdo;
    }

    public void setPorcAfectAcuerdo(String str) {
        this.porcAfectAcuerdo = str;
    }

    public String getImpDispPrest() {
        return this.impDispPrest;
    }

    public void setImpDispPrest(String str) {
        this.impDispPrest = str;
    }

    public String getImpDispCuota() {
        return this.impDispCuota;
    }

    public void setImpDispCuota(String str) {
        this.impDispCuota = str;
    }

    public String getImpIngreso() {
        return this.impIngreso;
    }

    public void setImpIngreso(String str) {
        this.impIngreso = str;
    }

    public String getCodInhabilitCli() {
        return this.codInhabilitCli;
    }

    public void setCodInhabilitCli(String str) {
        this.codInhabilitCli = str;
    }

    public String getDescripInhabilitado() {
        return this.descripInhabilitado;
    }

    public void setDescripInhabilitado(String str) {
        this.descripInhabilitado = str;
    }
}
