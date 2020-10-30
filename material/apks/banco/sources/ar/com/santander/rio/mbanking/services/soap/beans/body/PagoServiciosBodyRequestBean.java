package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class PagoServiciosBodyRequestBean {
    @SerializedName("cuenta")
    public CuentaDebitoBean accountRequestBean;
    public String aceptaDA;
    @SerializedName("deuda")
    public PagoServicioCBDeudaBean deudaCB;
    public String empServ;
    public String factura;
    @SerializedName("fechaNacimiento")
    public String fechaNacimiento;
    public String flujoOp;
    public String identificacion;
    public String importe;
    public String moneda;
    @SerializedName("nroDocumento")
    public String nroDocumento;
    public String tipoMonto;

    public PagoServiciosBodyRequestBean() {
    }

    public PagoServiciosBodyRequestBean(CuentaDebitoBean cuentaDebitoBean, PagoServicioCBDeudaBean pagoServicioCBDeudaBean, String str, String str2) {
        this.accountRequestBean = new CuentaDebitoBean(cuentaDebitoBean.tipo, cuentaDebitoBean.sucursal, cuentaDebitoBean.numero, null);
        this.deudaCB = pagoServicioCBDeudaBean;
        this.aceptaDA = str;
        this.flujoOp = str2;
    }

    public PagoServiciosBodyRequestBean(String str, String str2, String str3, String str4, CuentaDebitoBean cuentaDebitoBean, String str5, String str6) {
        this.empServ = str;
        this.identificacion = str2;
        this.tipoMonto = str3;
        this.factura = str4;
        this.accountRequestBean = cuentaDebitoBean;
        this.moneda = str5;
        this.importe = str6;
    }

    public void setRecargaSubeData(String str, String str2) {
        this.nroDocumento = str;
        this.fechaNacimiento = str2;
    }
}
