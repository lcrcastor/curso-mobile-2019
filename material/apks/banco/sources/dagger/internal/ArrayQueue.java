package dagger.internal;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ArrayQueue<E> extends AbstractCollection<E> implements Serializable, Cloneable, Queue<E> {
    private static final long serialVersionUID = 2340985798034038923L;
    /* access modifiers changed from: private */
    public transient Object[] a;
    /* access modifiers changed from: private */
    public transient int b;
    /* access modifiers changed from: private */
    public transient int c;

    class QueueIterator implements Iterator<E> {
        private int b;
        private int c;
        private int d;

        private QueueIterator() {
            this.b = ArrayQueue.this.b;
            this.c = ArrayQueue.this.c;
            this.d = -1;
        }

        public boolean hasNext() {
            return this.b != this.c;
        }

        public E next() {
            if (this.b == this.c) {
                throw new NoSuchElementException();
            }
            E e = ArrayQueue.this.a[this.b];
            if (ArrayQueue.this.c != this.c || e == null) {
                throw new ConcurrentModificationException();
            }
            this.d = this.b;
            this.b = (this.b + 1) & (ArrayQueue.this.a.length - 1);
            return e;
        }

        public void remove() {
            if (this.d < 0) {
                throw new IllegalStateException();
            }
            if (ArrayQueue.this.b(this.d)) {
                this.b = (this.b - 1) & (ArrayQueue.this.a.length - 1);
                this.c = ArrayQueue.this.c;
            }
            this.d = -1;
        }
    }

    private void a(int i) {
        int i2 = 8;
        if (i >= 8) {
            int i3 = i | (i >>> 1);
            int i4 = i3 | (i3 >>> 2);
            int i5 = i4 | (i4 >>> 4);
            int i6 = i5 | (i5 >>> 8);
            i2 = (i6 | (i6 >>> 16)) + 1;
            if (i2 < 0) {
                i2 >>>= 1;
            }
        }
        this.a = new Object[i2];
    }

    private void a() {
        int i = this.b;
        int length = this.a.length;
        int i2 = length - i;
        int i3 = length << 1;
        if (i3 < 0) {
            throw new IllegalStateException("Sorry, queue too big");
        }
        Object[] objArr = new Object[i3];
        System.arraycopy(this.a, i, objArr, 0, i2);
        System.arraycopy(this.a, 0, objArr, i2, i);
        this.a = objArr;
        this.b = 0;
        this.c = length;
    }

    public ArrayQueue() {
        this.a = new Object[16];
    }

    public ArrayQueue(int i) {
        a(i);
    }

    public ArrayQueue(Collection<? extends E> collection) {
        a(collection.size());
        addAll(collection);
    }

    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException("e == null");
        }
        this.a[this.c] = e;
        int length = (this.c + 1) & (this.a.length - 1);
        this.c = length;
        if (length == this.b) {
            a();
        }
        return true;
    }

    public boolean offer(E e) {
        return add(e);
    }

    public E remove() {
        E poll = poll();
        if (poll != null) {
            return poll;
        }
        throw new NoSuchElementException();
    }

    public E poll() {
        int i = this.b;
        E e = this.a[i];
        if (e == null) {
            return null;
        }
        this.a[i] = null;
        this.b = (i + 1) & (this.a.length - 1);
        return e;
    }

    public E element() {
        E e = this.a[this.b];
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    public E peek() {
        return this.a[this.b];
    }

    /* access modifiers changed from: private */
    public boolean b(int i) {
        Object[] objArr = this.a;
        int length = objArr.length - 1;
        int i2 = this.b;
        int i3 = this.c;
        int i4 = (i - i2) & length;
        int i5 = (i3 - i) & length;
        if (i4 >= ((i3 - i2) & length)) {
            throw new ConcurrentModificationException();
        } else if (i4 < i5) {
            if (i2 <= i) {
                System.arraycopy(objArr, i2, objArr, i2 + 1, i4);
            } else {
                System.arraycopy(objArr, 0, objArr, 1, i);
                objArr[0] = objArr[length];
                System.arraycopy(objArr, i2, objArr, i2 + 1, length - i2);
            }
            objArr[i2] = null;
            this.b = (i2 + 1) & length;
            return false;
        } else {
            if (i < i3) {
                System.arraycopy(objArr, i + 1, objArr, i, i5);
                this.c = i3 - 1;
            } else {
                System.arraycopy(objArr, i + 1, objArr, i, length - i);
                objArr[length] = objArr[0];
                System.arraycopy(objArr, 1, objArr, 0, i3);
                this.c = (i3 - 1) & length;
            }
            return true;
        }
    }

    public int size() {
        return (this.c - this.b) & (this.a.length - 1);
    }

    public boolean isEmpty() {
        return this.b == this.c;
    }

    public Iterator<E> iterator() {
        return new QueueIterator();
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        int length = this.a.length - 1;
        int i = this.b;
        while (true) {
            Object obj2 = this.a[i];
            if (obj2 == null) {
                return false;
            }
            if (obj.equals(obj2)) {
                return true;
            }
            i = (i + 1) & length;
        }
    }

    public boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        int length = this.a.length - 1;
        int i = this.b;
        while (true) {
            Object obj2 = this.a[i];
            if (obj2 == null) {
                return false;
            }
            if (obj.equals(obj2)) {
                b(i);
                return true;
            }
            i = (i + 1) & length;
        }
    }

    public void clear() {
        int i = this.b;
        int i2 = this.c;
        if (i != i2) {
            this.c = 0;
            this.b = 0;
            int length = this.a.length - 1;
            do {
                this.a[i] = null;
                i = (i + 1) & length;
            } while (i != i2);
        }
    }

    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    public <T> T[] toArray(T[] tArr) {
        int size = size();
        if (tArr.length < size) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size);
        }
        if (this.b < this.c) {
            System.arraycopy(this.a, this.b, tArr, 0, size());
        } else if (this.b > this.c) {
            int length = this.a.length - this.b;
            System.arraycopy(this.a, this.b, tArr, 0, length);
            System.arraycopy(this.a, 0, tArr, length, this.c);
        }
        if (tArr.length > size) {
            tArr[size] = null;
        }
        return tArr;
    }

    public ArrayQueue<E> clone() {
        try {
            ArrayQueue<E> arrayQueue = (ArrayQueue) super.clone();
            Object[] objArr = (Object[]) Array.newInstance(this.a.getClass().getComponentType(), this.a.length);
            System.arraycopy(this.a, 0, objArr, 0, this.a.length);
            arrayQueue.a = objArr;
            return arrayQueue;
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        int length = this.a.length - 1;
        for (int i = this.b; i != this.c; i = (i + 1) & length) {
            objectOutputStream.writeObject(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        a(readInt);
        this.b = 0;
        this.c = readInt;
        for (int i = 0; i < readInt; i++) {
            this.a[i] = objectInputStream.readObject();
        }
    }
}
