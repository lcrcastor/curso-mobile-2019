package cz.msebera.android.httpclient.pool;

public interface ConnFactory<T, C> {
    C create(T t);
}
