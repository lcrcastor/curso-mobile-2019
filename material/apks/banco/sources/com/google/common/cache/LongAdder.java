package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@GwtCompatible(emulated = true)
final class LongAdder extends Striped64 implements LongAddable, Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    /* access modifiers changed from: 0000 */
    public final long a(long j, long j2) {
        return j + j2;
    }

    public void a(long j) {
        Cell[] cellArr = this.d;
        if (cellArr == null) {
            long j2 = this.e;
            if (b(j2, j2 + j)) {
                return;
            }
        }
        int[] iArr = (int[]) a.get();
        boolean z = true;
        if (!(iArr == null || cellArr == null)) {
            int length = cellArr.length;
            if (length >= 1) {
                Cell cell = cellArr[(length - 1) & iArr[0]];
                if (cell != null) {
                    long j3 = cell.a;
                    z = cell.a(j3, j3 + j);
                    if (z) {
                        return;
                    }
                }
            }
        }
        a(j, iArr, z);
    }

    public void a() {
        a(1);
    }

    public long b() {
        long j = this.e;
        Cell[] cellArr = this.d;
        if (cellArr != null) {
            for (Cell cell : cellArr) {
                if (cell != null) {
                    j += cell.a;
                }
            }
        }
        return j;
    }

    public String toString() {
        return Long.toString(b());
    }

    public long longValue() {
        return b();
    }

    public int intValue() {
        return (int) b();
    }

    public float floatValue() {
        return (float) b();
    }

    public double doubleValue() {
        return (double) b();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeLong(b());
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.f = 0;
        this.d = null;
        this.e = objectInputStream.readLong();
    }
}
