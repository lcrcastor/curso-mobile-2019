package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@NotThreadSafe
@Deprecated
class CloseableHttpResponseProxy implements InvocationHandler {
    private static final Constructor<?> a;
    private final HttpResponse b;

    static {
        try {
            a = Proxy.getProxyClass(CloseableHttpResponseProxy.class.getClassLoader(), new Class[]{CloseableHttpResponse.class}).getConstructor(new Class[]{InvocationHandler.class});
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    CloseableHttpResponseProxy(HttpResponse httpResponse) {
        this.b = httpResponse;
    }

    public void a() {
        EntityUtils.consume(this.b.getEntity());
    }

    public Object invoke(Object obj, Method method, Object[] objArr) {
        if (method.getName().equals("close")) {
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

    public static CloseableHttpResponse a(HttpResponse httpResponse) {
        try {
            return (CloseableHttpResponse) a.newInstance(new Object[]{new CloseableHttpResponseProxy(httpResponse)});
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e2) {
            throw new IllegalStateException(e2);
        } catch (IllegalAccessException e3) {
            throw new IllegalStateException(e3);
        }
    }
}
