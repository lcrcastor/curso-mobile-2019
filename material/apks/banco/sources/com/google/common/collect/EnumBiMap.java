package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@GwtCompatible(emulated = true)
public final class EnumBiMap<K extends Enum<K>, V extends Enum<V>> extends AbstractBiMap<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient Class<K> b;
    private transient Class<V> c;

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

    public /* bridge */ /* synthetic */ Set values() {
        return super.values();
    }

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Class<K> cls, Class<V> cls2) {
        return new EnumBiMap<>(cls, cls2);
    }

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Map<K, V> map) {
        EnumBiMap<K, V> create = create(b(map), c(map));
        create.putAll(map);
        return create;
    }

    private EnumBiMap(Class<K> cls, Class<V> cls2) {
        super((Map<K, V>) WellBehavedMap.a(new EnumMap(cls)), (Map<V, K>) WellBehavedMap.a(new EnumMap(cls2)));
        this.b = cls;
        this.c = cls2;
    }

    static <K extends Enum<K>> Class<K> b(Map<K, ?> map) {
        if (map instanceof EnumBiMap) {
            return ((EnumBiMap) map).keyType();
        }
        if (map instanceof EnumHashBiMap) {
            return ((EnumHashBiMap) map).keyType();
        }
        Preconditions.checkArgument(!map.isEmpty());
        return ((Enum) map.keySet().iterator().next()).getDeclaringClass();
    }

    private static <V extends Enum<V>> Class<V> c(Map<?, V> map) {
        if (map instanceof EnumBiMap) {
            return ((EnumBiMap) map).c;
        }
        Preconditions.checkArgument(!map.isEmpty());
        return ((Enum) map.values().iterator().next()).getDeclaringClass();
    }

    public Class<K> keyType() {
        return this.b;
    }

    public Class<V> valueType() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public K a(K k) {
        return (Enum) Preconditions.checkNotNull(k);
    }

    /* access modifiers changed from: 0000 */
    public V b(V v) {
        return (Enum) Preconditions.checkNotNull(v);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.b);
        objectOutputStream.writeObject(this.c);
        Serialization.a((Map<K, V>) this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.b = (Class) objectInputStream.readObject();
        this.c = (Class) objectInputStream.readObject();
        a((Map<K, V>) WellBehavedMap.a(new EnumMap(this.b)), (Map<V, K>) WellBehavedMap.a(new EnumMap(this.c)));
        Serialization.a((Map<K, V>) this, objectInputStream);
    }
}
