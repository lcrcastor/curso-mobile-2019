package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class CambiarLimiteBodyRequestBean {
    public LimiteExtraccionBean limite;
    public String tiempoDeValidez;

    public CambiarLimiteBodyRequestBean(LimiteExtraccionBean limiteExtraccionBean, String str) {
        this.limite = limiteExtraccionBean;
        this.tiempoDeValidez = str;
    }
}
