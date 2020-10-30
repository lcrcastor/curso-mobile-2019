package ar.com.santander.rio.mbanking.managers.preferences;

public interface Preference<T> {
    T get();

    T getDefaultValue();

    boolean isSet();

    void set(T t);
}
