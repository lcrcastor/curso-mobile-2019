package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class VerificaDatosOBBean extends VerificaDatosBean {
    private String cbu;

    public VerificaDatosOBBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        super(str, str2, str3, str4, str5, str6, str8);
        this.cbu = str7;
    }

    public String getCbu() {
        return this.cbu;
    }
}
