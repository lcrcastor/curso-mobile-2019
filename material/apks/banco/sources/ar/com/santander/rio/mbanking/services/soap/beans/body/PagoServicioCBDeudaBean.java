package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class PagoServicioCBDeudaBean {
    @SerializedName("cuit")
    public String cuit;
    @SerializedName("cur")
    public String cur;
    @SerializedName("datAdicionales")
    public String datAdicionales;
    @SerializedName("empServ")
    public String empServ;
    @SerializedName("factura")
    public String factura;
    @SerializedName("identificacion")
    public String identificacion;
    @SerializedName("importe")
    public String importe;
    @SerializedName("infoAdicional")
    public String infoAdicional;
    @SerializedName("moneda")
    public String moneda;
    @SerializedName("vencimiento")
    public String vencimiento;

    public PagoServicioCBDeudaBean() {
    }

    public PagoServicioCBDeudaBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        this.empServ = str;
        this.identificacion = str2;
        this.importe = str3;
        this.moneda = str4;
        if (str5 == null) {
            str5 = "";
        }
        this.factura = str5;
        this.vencimiento = str6;
        if (str7 == null) {
            str7 = "";
        }
        this.infoAdicional = str7;
        if (str8 == null) {
            str8 = "";
        }
        this.cur = str8;
        if (str9 == null) {
            str9 = "";
        }
        this.cuit = str9;
        if (str10 == null) {
            str10 = "";
        }
        this.datAdicionales = str10;
    }
}
