package com.squareup.otto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class EventHandler {
    private final Object a;
    private final Method b;
    private final int c;
    private boolean d = true;

    EventHandler(Object obj, Method method) {
        if (obj == null) {
            throw new NullPointerException("EventHandler target cannot be null.");
        } else if (method == null) {
            throw new NullPointerException("EventHandler method cannot be null.");
        } else {
            this.a = obj;
            this.b = method;
            method.setAccessible(true);
            this.c = ((method.hashCode() + 31) * 31) + obj.hashCode();
        }
    }

    public boolean a() {
        return this.d;
    }

    public void b() {
        this.d = false;
    }

    public void a(Object obj) {
        if (!this.d) {
            StringBuilder sb = new StringBuilder();
            sb.append(toString());
            sb.append(" has been invalidated and can no longer handle events.");
            throw new IllegalStateException(sb.toString());
        }
        try {
            this.b.invoke(this.a, new Object[]{obj});
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof Error) {
                throw ((Error) e2.getCause());
            }
            throw e2;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[EventHandler ");
        sb.append(this.b);
        sb.append("]");
        return sb.toString();
    }

    public int hashCode() {
        return this.c;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EventHandler eventHandler = (EventHandler) obj;
        if (!this.b.equals(eventHandler.b) || this.a != eventHandler.a) {
            z = false;
        }
        return z;
    }
}
