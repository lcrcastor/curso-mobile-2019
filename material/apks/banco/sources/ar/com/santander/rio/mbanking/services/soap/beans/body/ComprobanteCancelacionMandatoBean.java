package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class ComprobanteCancelacionMandatoBean {
    public String fechaCancelacion;
    public String numComprobante;

    public ComprobanteCancelacionMandatoBean() {
    }

    public ComprobanteCancelacionMandatoBean(String str, String str2) {
        this.fechaCancelacion = str;
        this.numComprobante = str2;
    }
}
