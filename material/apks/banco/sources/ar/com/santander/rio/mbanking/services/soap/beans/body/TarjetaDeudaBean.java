package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class TarjetaDeudaBean {
    public String Vencimiento;
    public String codigo;
    public String formaPagoTCredito;
    public String importeD;
    public String importeDConvertido;
    public String importeP;
    public String importePConvertido;
    public String importePM;
    public String importe_total_dolares;
    public String importe_total_pesos;
    public String nombreTarjeta;
    public String nroCuentaDebito;
    public String numCuentaProduc;
    public String numTarjeta;
    public String programado;
    public String sucursalCuentaDebito;
    public String tipoCuentaDebito;
    public String tipoTarjeta;

    public TarjetaDeudaBean() {
    }

    public TarjetaDeudaBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18) {
        this.programado = str;
        this.tipoTarjeta = str2;
        this.tipoCuentaDebito = str3;
        this.sucursalCuentaDebito = str4;
        this.nroCuentaDebito = str5;
        this.formaPagoTCredito = str6;
        this.numTarjeta = str7;
        this.nombreTarjeta = str8;
        this.codigo = str9;
        this.importeP = str10;
        this.importePM = str11;
        this.importeD = str12;
        this.Vencimiento = str13;
        this.numCuentaProduc = str14;
        this.importePConvertido = str15;
        this.importeDConvertido = str16;
        this.importe_total_dolares = str17;
        this.importe_total_pesos = str18;
    }
}
