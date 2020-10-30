package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

public class zza<E> extends AbstractSet<E> {
    private final ArrayMap<E, E> a;

    public zza() {
        this.a = new ArrayMap<>();
    }

    public zza(int i) {
        this.a = new ArrayMap<>(i);
    }

    public zza(Collection<E> collection) {
        this(collection.size());
        addAll(collection);
    }

    public boolean add(E e) {
        if (this.a.containsKey(e)) {
            return false;
        }
        this.a.put(e, e);
        return true;
    }

    public boolean addAll(Collection<? extends E> collection) {
        return collection instanceof zza ? zza((zza) collection) : super.addAll(collection);
    }

    public void clear() {
        this.a.clear();
    }

    public boolean contains(Object obj) {
        return this.a.containsKey(obj);
    }

    public Iterator<E> iterator() {
        return this.a.keySet().iterator();
    }

    public boolean remove(Object obj) {
        if (!this.a.containsKey(obj)) {
            return false;
        }
        this.a.remove(obj);
        return true;
    }

    public int size() {
        return this.a.size();
    }

    public boolean zza(zza<? extends E> zza) {
        int size = size();
        this.a.putAll(zza.a);
        return size() > size;
    }
}
