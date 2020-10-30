package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import java.io.Closeable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@NotThreadSafe
class ResponseProxyHandler implements InvocationHandler {
    private static final Method a;
    private final HttpResponse b;

    static {
        try {
            a = Closeable.class.getMethod("close", new Class[0]);
        } catch (NoSuchMethodException e) {
            throw new Error(e);
        }
    }

    ResponseProxyHandler(HttpResponse httpResponse) {
        this.b = httpResponse;
    }

    public void a() {
        IOUtils.a(this.b.getEntity());
    }

    public Object invoke(Object obj, Method method, Object[] objArr) {
        if (method.equals(a)) {
            a();
            return null;
        }
        try {
            return method.invoke(this.b, objArr);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                throw cause;
            }
            throw e;
        }
    }
}
