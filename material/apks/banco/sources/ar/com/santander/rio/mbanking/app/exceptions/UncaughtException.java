package ar.com.santander.rio.mbanking.app.exceptions;

public class UncaughtException extends Exception {
    public UncaughtException(Throwable th) {
        super(th);
    }
}
