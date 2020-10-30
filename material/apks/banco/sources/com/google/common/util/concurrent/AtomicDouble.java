package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

@GwtIncompatible
public class AtomicDouble extends Number implements Serializable {
    private static final AtomicLongFieldUpdater<AtomicDouble> b = AtomicLongFieldUpdater.newUpdater(AtomicDouble.class, "a");
    private static final long serialVersionUID = 0;
    private volatile transient long a;

    public AtomicDouble(double d) {
        this.a = Double.doubleToRawLongBits(d);
    }

    public AtomicDouble() {
    }

    public final double get() {
        return Double.longBitsToDouble(this.a);
    }

    public final void set(double d) {
        this.a = Double.doubleToRawLongBits(d);
    }

    public final void lazySet(double d) {
        set(d);
    }

    public final double getAndSet(double d) {
        return Double.longBitsToDouble(b.getAndSet(this, Double.doubleToRawLongBits(d)));
    }

    public final boolean compareAndSet(double d, double d2) {
        return b.compareAndSet(this, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final boolean weakCompareAndSet(double d, double d2) {
        return b.weakCompareAndSet(this, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    @CanIgnoreReturnValue
    public final double getAndAdd(double d) {
        long j;
        double longBitsToDouble;
        do {
            j = this.a;
            longBitsToDouble = Double.longBitsToDouble(j);
        } while (!b.compareAndSet(this, j, Double.doubleToRawLongBits(longBitsToDouble + d)));
        return longBitsToDouble;
    }

    @CanIgnoreReturnValue
    public final double addAndGet(double d) {
        long j;
        double longBitsToDouble;
        do {
            j = this.a;
            longBitsToDouble = Double.longBitsToDouble(j) + d;
        } while (!b.compareAndSet(this, j, Double.doubleToRawLongBits(longBitsToDouble)));
        return longBitsToDouble;
    }

    public String toString() {
        return Double.toString(get());
    }

    public int intValue() {
        return (int) get();
    }

    public long longValue() {
        return (long) get();
    }

    public float floatValue() {
        return (float) get();
    }

    public double doubleValue() {
        return get();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeDouble(get());
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        set(objectInputStream.readDouble());
    }
}
