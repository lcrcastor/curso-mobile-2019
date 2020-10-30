package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class CheckVersionBodyRequestBean {
    public String idRuntime;
    public String version;

    public CheckVersionBodyRequestBean() {
    }

    public CheckVersionBodyRequestBean(String str, String str2) {
        this.idRuntime = str;
        this.version = str2;
    }
}
