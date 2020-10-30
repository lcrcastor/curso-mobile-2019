package com.nineoldandroids.util;

public abstract class Property<T, V> {
    private final String a;
    private final Class<V> b;

    public abstract V get(T t);

    public boolean isReadOnly() {
        return false;
    }

    public static <T, V> Property<T, V> of(Class<T> cls, Class<V> cls2, String str) {
        return new ReflectiveProperty(cls, cls2, str);
    }

    public Property(Class<V> cls, String str) {
        this.a = str;
        this.b = cls;
    }

    public void set(T t, V v) {
        StringBuilder sb = new StringBuilder();
        sb.append("Property ");
        sb.append(getName());
        sb.append(" is read-only");
        throw new UnsupportedOperationException(sb.toString());
    }

    public String getName() {
        return this.a;
    }

    public Class<V> getType() {
        return this.b;
    }
}
