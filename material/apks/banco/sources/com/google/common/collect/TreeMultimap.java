package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public class TreeMultimap<K, V> extends AbstractSortedKeySortedSetMultimap<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient Comparator<? super K> a;
    private transient Comparator<? super V> b;

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean containsEntry(Object obj, Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean containsKey(Object obj) {
        return super.containsKey(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object obj) {
        return super.containsValue(obj);
    }

    public /* bridge */ /* synthetic */ Set entries() {
        return super.entries();
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    public /* bridge */ /* synthetic */ boolean put(Object obj, Object obj2) {
        return super.put(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    public /* bridge */ /* synthetic */ boolean putAll(Object obj, Iterable iterable) {
        return super.putAll(obj, iterable);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    public /* bridge */ /* synthetic */ SortedSet removeAll(Object obj) {
        return super.removeAll(obj);
    }

    public /* bridge */ /* synthetic */ SortedSet replaceValues(Object obj, Iterable iterable) {
        return super.replaceValues(obj, iterable);
    }

    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ Collection values() {
        return super.values();
    }

    public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create() {
        return new TreeMultimap<>(Ordering.natural(), Ordering.natural());
    }

    public static <K, V> TreeMultimap<K, V> create(Comparator<? super K> comparator, Comparator<? super V> comparator2) {
        return new TreeMultimap<>((Comparator) Preconditions.checkNotNull(comparator), (Comparator) Preconditions.checkNotNull(comparator2));
    }

    public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new TreeMultimap<>(Ordering.natural(), Ordering.natural(), multimap);
    }

    TreeMultimap(Comparator<? super K> comparator, Comparator<? super V> comparator2) {
        super(new TreeMap(comparator));
        this.a = comparator;
        this.b = comparator2;
    }

    private TreeMultimap(Comparator<? super K> comparator, Comparator<? super V> comparator2, Multimap<? extends K, ? extends V> multimap) {
        this(comparator, comparator2);
        putAll(multimap);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: n */
    public SortedSet<V> c() {
        return new TreeSet(this.b);
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> a(@Nullable K k) {
        if (k == null) {
            keyComparator().compare(k, k);
        }
        return super.a(k);
    }

    public Comparator<? super K> keyComparator() {
        return this.a;
    }

    public Comparator<? super V> valueComparator() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    /* renamed from: p */
    public NavigableMap<K, Collection<V>> m() {
        return (NavigableMap) super.e();
    }

    @GwtIncompatible
    public NavigableSet<V> get(@Nullable K k) {
        return (NavigableSet) super.get((Object) k);
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public Collection<V> a(Collection<V> collection) {
        return Sets.unmodifiableNavigableSet((NavigableSet) collection);
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public Collection<V> a(K k, Collection<V> collection) {
        return new WrappedNavigableSet(k, (NavigableSet) collection, null);
    }

    @GwtIncompatible
    public NavigableSet<K> keySet() {
        return (NavigableSet) super.keySet();
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    /* renamed from: q */
    public NavigableSet<K> f() {
        return new NavigableKeySet(m());
    }

    @GwtIncompatible
    public NavigableMap<K, Collection<V>> asMap() {
        return (NavigableMap) super.asMap();
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    /* renamed from: r */
    public NavigableMap<K, Collection<V>> i() {
        return new NavigableAsMap(m());
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(keyComparator());
        objectOutputStream.writeObject(valueComparator());
        Serialization.a((Multimap<K, V>) this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.a = (Comparator) Preconditions.checkNotNull((Comparator) objectInputStream.readObject());
        this.b = (Comparator) Preconditions.checkNotNull((Comparator) objectInputStream.readObject());
        a((Map<K, Collection<V>>) new TreeMap<K,Collection<V>>(this.a));
        Serialization.a((Multimap<K, V>) this, objectInputStream);
    }
}
