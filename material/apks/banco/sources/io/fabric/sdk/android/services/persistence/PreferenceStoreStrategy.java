package io.fabric.sdk.android.services.persistence;

import android.annotation.SuppressLint;

public class PreferenceStoreStrategy<T> implements PersistenceStrategy<T> {
    private final PreferenceStore a;
    private final SerializationStrategy<T> b;
    private final String c;

    public PreferenceStoreStrategy(PreferenceStore preferenceStore, SerializationStrategy<T> serializationStrategy, String str) {
        this.a = preferenceStore;
        this.b = serializationStrategy;
        this.c = str;
    }

    @SuppressLint({"CommitPrefEdits"})
    public void save(T t) {
        this.a.save(this.a.edit().putString(this.c, this.b.serialize(t)));
    }

    public T restore() {
        return this.b.deserialize(this.a.get().getString(this.c, null));
    }

    public void clear() {
        this.a.edit().remove(this.c).commit();
    }
}
