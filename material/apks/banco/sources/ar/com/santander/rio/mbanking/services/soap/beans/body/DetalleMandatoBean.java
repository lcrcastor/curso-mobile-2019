package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class DetalleMandatoBean {
    public String codExtraccion;
    public String estado;
    public String fechaAlta;
    public String fechaModificacion;
    public String fechaVencimiento;
    public String idMandato;
    public String importe;
    public String numComprobante;

    public DetalleMandatoBean() {
    }

    public DetalleMandatoBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.idMandato = str;
        this.codExtraccion = str2;
        this.estado = str3;
        this.fechaAlta = str4;
        this.fechaModificacion = str5;
        this.fechaVencimiento = str6;
        this.importe = str7;
        this.numComprobante = str8;
    }
}
