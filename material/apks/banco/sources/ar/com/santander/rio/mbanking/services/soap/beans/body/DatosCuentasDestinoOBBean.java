package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class DatosCuentasDestinoOBBean {
    @SerializedName("alias")
    private String alias;
    @SerializedName("bancoDestino")
    private String bancoDestino;
    @SerializedName("bancoReceptor")
    private String bancoReceptor;
    @SerializedName("beneficiario")
    private String beneficiario;
    @SerializedName("caracteristica")
    private String caracteristica;
    @SerializedName("cbu")
    private String cbu;
    @SerializedName("codConcepto")
    private String codConcepto;
    @SerializedName("cuenta")
    private String cuenta;
    @SerializedName("cuit")
    private String cuit;
    @SerializedName("diferido")
    private String diferido;
    @SerializedName("email")
    private String email;
    @SerializedName("fiid")
    private String fiid;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("refConcepto")
    private String refConcepto;
    @SerializedName("tipoCtaFromBane")
    private String tipoCtaFromBane;
    @SerializedName("tipoCtaToBane")
    private String tipoCtaToBane;
    @SerializedName("titular")
    private String titular;
    @SerializedName("user")
    private String user;

    public DatosCuentasDestinoOBBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18) {
        this.beneficiario = str;
        this.nombre = str2;
        this.caracteristica = str3;
        this.cuit = str4;
        this.cuenta = str5;
        this.tipoCtaToBane = str6;
        this.tipoCtaFromBane = str7;
        this.cbu = str8;
        this.bancoReceptor = str9;
        this.codConcepto = str10;
        this.bancoDestino = str11;
        this.refConcepto = str12;
        this.fiid = str13;
        this.user = str14;
        this.titular = str15;
        this.email = str16;
        this.alias = str17;
        this.diferido = str18;
    }

    public String getBeneficiario() {
        return this.beneficiario;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getCaracteristica() {
        return this.caracteristica;
    }

    public String getCuit() {
        return this.cuit;
    }

    public String getCuenta() {
        return this.cuenta;
    }

    public String getTipoCtaToBane() {
        return this.tipoCtaToBane;
    }

    public String getTipoCtaFromBane() {
        return this.tipoCtaFromBane;
    }

    public String getCbu() {
        return this.cbu;
    }

    public String getBancoReceptor() {
        return this.bancoReceptor;
    }

    public String getCodConcepto() {
        return this.codConcepto;
    }

    public String getBancoDestino() {
        return this.bancoDestino;
    }

    public String getRefConcepto() {
        return this.refConcepto;
    }

    public String getFiid() {
        return this.fiid;
    }

    public String getUser() {
        return this.user;
    }

    public String getTitular() {
        return this.titular;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getDiferido() {
        return this.diferido;
    }
}
