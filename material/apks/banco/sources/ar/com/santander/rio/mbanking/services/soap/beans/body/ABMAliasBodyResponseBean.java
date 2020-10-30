package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ABMAliasBodyResponseBean extends ErrorBodyBean {
    @SerializedName("fechaOperacion")
    private String fechaOperacion;
    @SerializedName("idAlias")
    private String idAlias;
    @SerializedName("legales1")
    private String legales1;
    @SerializedName("legales2")
    private String legales2;
    @SerializedName("nroComprobante")
    private String nroComprobante;
    @SerializedName("numeroCuil")
    private String numeroCuil;

    public ABMAliasBodyResponseBean() {
    }

    public ABMAliasBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6) {
        this.nroComprobante = str;
        this.fechaOperacion = str2;
        this.legales1 = str3;
        this.legales2 = str4;
        this.idAlias = str5;
        this.numeroCuil = str6;
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public String getFechaOperacion() {
        return this.fechaOperacion;
    }

    public String getLegales1() {
        return this.legales1;
    }

    public String getLegales2() {
        return this.legales2;
    }

    public String getIdAlias() {
        return this.idAlias;
    }

    public String getNumeroCuil() {
        return this.numeroCuil;
    }
}
