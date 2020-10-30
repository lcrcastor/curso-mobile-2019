package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class BAgendadosEnvEfeDestinatarioBean {
    public String emailDest;
    public String nombreDest;
    public String numeroDocDest;
    public String tipoDocDest;

    public BAgendadosEnvEfeDestinatarioBean() {
    }

    public BAgendadosEnvEfeDestinatarioBean(String str, String str2, String str3, String str4) {
        this.tipoDocDest = str;
        this.numeroDocDest = str2;
        this.nombreDest = str3;
        this.emailDest = str4;
    }
}
