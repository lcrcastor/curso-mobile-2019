package ar.com.santander.rio.mbanking.app.exceptions;

public class EmptyAppShare extends Exception {
    private String a;

    public EmptyAppShare(String str) {
        this.a = str;
    }

    public String getMessage() {
        return this.a;
    }
}
