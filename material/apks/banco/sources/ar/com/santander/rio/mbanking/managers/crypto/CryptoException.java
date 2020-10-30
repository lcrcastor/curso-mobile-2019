package ar.com.santander.rio.mbanking.managers.crypto;

public class CryptoException extends RuntimeException {
    public CryptoException() {
    }

    public CryptoException(String str) {
        super(str);
    }

    public CryptoException(String str, Throwable th) {
        super(str, th);
    }

    public CryptoException(Throwable th) {
        super(th);
    }
}
