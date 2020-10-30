package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class MovCtasBodyRequestBean {
    @SerializedName("datosCuenta")
    public AccountRequestBean accountRequestBean;
    public String fechaMovDesde;
    public String fechaMovHasta;
    public String idTrx;
    public String importeDesde;
    public String importeHasta;
    public String moneda;
    public String nroPag;
    public String tipoCons;
    public String tipoMov;

    public MovCtasBodyRequestBean() {
        this.idTrx = null;
        this.accountRequestBean = new AccountRequestBean();
    }

    public MovCtasBodyRequestBean(AccountRequestBean accountRequestBean2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.idTrx = null;
        this.accountRequestBean = accountRequestBean2;
        this.fechaMovDesde = str;
        this.fechaMovHasta = str2;
        this.importeDesde = str3;
        this.importeHasta = str4;
        this.moneda = str5;
        this.tipoMov = str6;
        this.nroPag = str7;
        this.tipoCons = str8;
    }
}
