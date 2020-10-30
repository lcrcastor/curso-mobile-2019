package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class BeneficiarioMandatoBean {
    public String emailDest;
    public String nombreDest;
    public String numeroDocumento;
    public String tipoDocumento;

    public BeneficiarioMandatoBean() {
        this.tipoDocumento = new String();
        this.numeroDocumento = new String();
        this.nombreDest = new String();
        this.emailDest = new String();
    }

    public BeneficiarioMandatoBean(String str, String str2, String str3, String str4) {
        this.tipoDocumento = str;
        this.numeroDocumento = str2;
        this.nombreDest = str3;
        this.emailDest = str4;
    }
}
