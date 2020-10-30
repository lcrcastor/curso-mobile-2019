package com.google.common.reflect;

import com.google.common.annotations.Beta;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import javax.annotation.Nullable;

@Beta
public abstract class AbstractInvocationHandler implements InvocationHandler {
    private static final Object[] a = new Object[0];

    /* access modifiers changed from: protected */
    public abstract Object handleInvocation(Object obj, Method method, Object[] objArr);

    public final Object invoke(Object obj, Method method, @Nullable Object[] objArr) {
        if (objArr == null) {
            objArr = a;
        }
        if (objArr.length == 0 && method.getName().equals("hashCode")) {
            return Integer.valueOf(hashCode());
        }
        boolean z = true;
        if (objArr.length == 1 && method.getName().equals("equals") && method.getParameterTypes()[0] == Object.class) {
            Object obj2 = objArr[0];
            if (obj2 == null) {
                return Boolean.valueOf(false);
            }
            if (obj == obj2) {
                return Boolean.valueOf(true);
            }
            if (!a(obj2, obj.getClass()) || !equals(Proxy.getInvocationHandler(obj2))) {
                z = false;
            }
            return Boolean.valueOf(z);
        } else if (objArr.length != 0 || !method.getName().equals("toString")) {
            return handleInvocation(obj, method, objArr);
        } else {
            return toString();
        }
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        return super.toString();
    }

    private static boolean a(Object obj, Class<?> cls) {
        return cls.isInstance(obj) || (Proxy.isProxyClass(obj.getClass()) && Arrays.equals(obj.getClass().getInterfaces(), cls.getInterfaces()));
    }
}
