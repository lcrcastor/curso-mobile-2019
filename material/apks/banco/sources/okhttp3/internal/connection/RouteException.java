package okhttp3.internal.connection;

import java.io.IOException;
import okhttp3.internal.Util;

public final class RouteException extends RuntimeException {
    private IOException a;
    private IOException b;

    public RouteException(IOException iOException) {
        super(iOException);
        this.a = iOException;
        this.b = iOException;
    }

    public IOException getFirstConnectException() {
        return this.a;
    }

    public IOException getLastConnectException() {
        return this.b;
    }

    public void addConnectException(IOException iOException) {
        Util.addSuppressedIfPossible(this.a, iOException);
        this.b = iOException;
    }
}
