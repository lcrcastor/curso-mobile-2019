package ar.com.santander.rio.mbanking.app.exceptions;

public class NotExistPackageShare extends Exception {
    private String a;

    public NotExistPackageShare(String str) {
        this.a = str;
    }

    public String getPkgNameOrigen() {
        return this.a;
    }
}
