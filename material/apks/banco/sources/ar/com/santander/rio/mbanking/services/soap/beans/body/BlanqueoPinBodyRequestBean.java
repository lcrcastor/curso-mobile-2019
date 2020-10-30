package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class BlanqueoPinBodyRequestBean {
    private String codTareta;
    private String nroTarjeta;
    private String opcionBlanqueo;

    public BlanqueoPinBodyRequestBean() {
    }

    public BlanqueoPinBodyRequestBean(String str, String str2, String str3) {
        this.nroTarjeta = str;
        this.codTareta = str2;
        this.opcionBlanqueo = str3;
    }

    public String getNroTarjeta() {
        return this.nroTarjeta;
    }

    public void setNroTarjeta(String str) {
        this.nroTarjeta = str;
    }

    public String getCodTareta() {
        return this.codTareta;
    }

    public void setCodTareta(String str) {
        this.codTareta = str;
    }

    public String getOpcionBlanqueo() {
        return this.opcionBlanqueo;
    }

    public void setOpcionBlanqueo(String str) {
        this.opcionBlanqueo = str;
    }
}
