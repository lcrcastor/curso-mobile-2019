package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ABMAliasBodyRequestBean {
    @SerializedName("alias")
    private String alias;
    @SerializedName("aliasOriginal")
    private String aliasOriginal;
    @SerializedName("cuenta")
    private CuentaShortBean cuenta;
    @SerializedName("idAlias")
    private String idAlias;
    @SerializedName("reasigna")
    private String reasigna;
    @SerializedName("tipoOperacion")
    private String tipoOperacion;

    public ABMAliasBodyRequestBean(String str, String str2, String str3, String str4, String str5, CuentaShortBean cuentaShortBean) {
        this.tipoOperacion = str;
        this.alias = str2;
        this.aliasOriginal = str3;
        this.reasigna = str4;
        this.idAlias = str5;
        this.cuenta = cuentaShortBean;
    }
}
