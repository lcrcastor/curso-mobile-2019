package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class RecargaCelularesBodyRequestBean {
    @SerializedName("cuenta")
    public CuentaDebitoBean accountRequestBean;
    public String aceptaDA;
    @SerializedName("deuda")
    public PagoServicioCBDeudaBean deudaCB;
    public String empServ;
    public String factura;
    public String flujoOp;
    public String identificacion;
    public String importe;
    public String moneda;
    public String tipoMonto;

    public RecargaCelularesBodyRequestBean() {
    }

    public RecargaCelularesBodyRequestBean(CuentaDebitoBean cuentaDebitoBean, PagoServicioCBDeudaBean pagoServicioCBDeudaBean, String str, String str2) {
        this.accountRequestBean = new CuentaDebitoBean(cuentaDebitoBean.tipo, cuentaDebitoBean.sucursal, cuentaDebitoBean.numero, null);
        this.deudaCB = pagoServicioCBDeudaBean;
        this.aceptaDA = str;
        this.flujoOp = str2;
    }

    public RecargaCelularesBodyRequestBean(String str, String str2, String str3, String str4, CuentaDebitoBean cuentaDebitoBean, String str5, String str6) {
        this.empServ = str;
        this.identificacion = str2;
        this.tipoMonto = str3;
        this.factura = str4;
        this.accountRequestBean = cuentaDebitoBean;
        this.moneda = str5;
        this.importe = str6;
    }
}
