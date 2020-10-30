package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GeneraMandatoExtEnvComprobanteBean {
    public String codExtraccion;
    public String fechaVencimiento;
    public String idMandato;
    public String numComprobante;

    public GeneraMandatoExtEnvComprobanteBean() {
    }

    public GeneraMandatoExtEnvComprobanteBean(String str, String str2, String str3, String str4) {
        this.codExtraccion = str;
        this.idMandato = str2;
        this.fechaVencimiento = str3;
        this.numComprobante = str4;
    }
}
