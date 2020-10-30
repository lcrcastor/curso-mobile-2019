package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class EnumHashBiMap<K extends Enum<K>, V> extends AbstractBiMap<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient Class<K> b;

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object obj) {
        return super.containsValue(obj);
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public /* bridge */ /* synthetic */ BiMap inverse() {
        return super.inverse();
    }

    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    public /* bridge */ /* synthetic */ void putAll(Map map) {
        super.putAll(map);
    }

    public /* bridge */ /* synthetic */ Object remove(Object obj) {
        return super.remove(obj);
    }

    public /* bridge */ /* synthetic */ Set values() {
        return super.values();
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Class<K> cls) {
        return new EnumHashBiMap<>(cls);
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Map<K, ? extends V> map) {
        EnumHashBiMap<K, V> create = create(EnumBiMap.b(map));
        create.putAll(map);
        return create;
    }

    private EnumHashBiMap(Class<K> cls) {
        super((Map<K, V>) WellBehavedMap.a(new EnumMap(cls)), (Map<V, K>) Maps.newHashMapWithExpectedSize(((Enum[]) cls.getEnumConstants()).length));
        this.b = cls;
    }

    /* access modifiers changed from: 0000 */
    public K a(K k) {
        return (Enum) Preconditions.checkNotNull(k);
    }

    @CanIgnoreReturnValue
    public V put(K k, @Nullable V v) {
        return super.put(k, v);
    }

    @CanIgnoreReturnValue
    public V forcePut(K k, @Nullable V v) {
        return super.forcePut(k, v);
    }

    public Class<K> keyType() {
        return this.b;
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.b);
        Serialization.a((Map<K, V>) this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.b = (Class) objectInputStream.readObject();
        a((Map<K, V>) WellBehavedMap.a(new EnumMap(this.b)), (Map<V, K>) new HashMap<V,K>((((Enum[]) this.b.getEnumConstants()).length * 3) / 2));
        Serialization.a((Map<K, V>) this, objectInputStream);
    }
}
