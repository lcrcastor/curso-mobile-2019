package com.squareup.okhttp.internal.http;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class RouteException extends Exception {
    private static final Method a;
    private IOException b;

    static {
        Method method;
        try {
            method = Throwable.class.getDeclaredMethod("addSuppressed", new Class[]{Throwable.class});
        } catch (Exception unused) {
            method = null;
        }
        a = method;
    }

    public RouteException(IOException iOException) {
        super(iOException);
        this.b = iOException;
    }

    public IOException getLastConnectException() {
        return this.b;
    }

    public void addConnectException(IOException iOException) {
        a(iOException, this.b);
        this.b = iOException;
    }

    private void a(IOException iOException, IOException iOException2) {
        if (a != null) {
            try {
                a.invoke(iOException, new Object[]{iOException2});
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        }
    }
}
