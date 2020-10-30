package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.PriorityProvider;
import io.fabric.sdk.android.services.concurrency.Task;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DependencyPriorityBlockingQueue<E extends Dependency & Task & PriorityProvider> extends PriorityBlockingQueue<E> {
    final Queue<E> a = new LinkedList();
    private final ReentrantLock b = new ReentrantLock();

    public E take() {
        return b(0, null, null);
    }

    public E peek() {
        try {
            return b(1, null, null);
        } catch (InterruptedException unused) {
            return null;
        }
    }

    public E poll(long j, TimeUnit timeUnit) {
        return b(3, Long.valueOf(j), timeUnit);
    }

    public E poll() {
        try {
            return b(2, null, null);
        } catch (InterruptedException unused) {
            return null;
        }
    }

    public int size() {
        try {
            this.b.lock();
            return this.a.size() + super.size();
        } finally {
            this.b.unlock();
        }
    }

    public <T> T[] toArray(T[] tArr) {
        try {
            this.b.lock();
            return a((T[]) super.toArray(tArr), (T[]) this.a.toArray(tArr));
        } finally {
            this.b.unlock();
        }
    }

    public Object[] toArray() {
        try {
            this.b.lock();
            return a((T[]) super.toArray(), (T[]) this.a.toArray());
        } finally {
            this.b.unlock();
        }
    }

    public int drainTo(Collection<? super E> collection) {
        try {
            this.b.lock();
            int drainTo = super.drainTo(collection) + this.a.size();
            while (!this.a.isEmpty()) {
                collection.add(this.a.poll());
            }
            return drainTo;
        } finally {
            this.b.unlock();
        }
    }

    public int drainTo(Collection<? super E> collection, int i) {
        try {
            this.b.lock();
            int drainTo = super.drainTo(collection, i);
            while (!this.a.isEmpty() && drainTo <= i) {
                collection.add(this.a.poll());
                drainTo++;
            }
            return drainTo;
        } finally {
            this.b.unlock();
        }
    }

    public boolean contains(Object obj) {
        try {
            this.b.lock();
            return super.contains(obj) || this.a.contains(obj);
        } finally {
            this.b.unlock();
        }
    }

    public void clear() {
        try {
            this.b.lock();
            this.a.clear();
            super.clear();
        } finally {
            this.b.unlock();
        }
    }

    public boolean remove(Object obj) {
        try {
            this.b.lock();
            return super.remove(obj) || this.a.remove(obj);
        } finally {
            this.b.unlock();
        }
    }

    public boolean removeAll(Collection<?> collection) {
        try {
            this.b.lock();
            return this.a.removeAll(collection) | super.removeAll(collection);
        } finally {
            this.b.unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public E a(int i, Long l, TimeUnit timeUnit) {
        E e;
        switch (i) {
            case 0:
                e = (Dependency) super.take();
                break;
            case 1:
                e = (Dependency) super.peek();
                break;
            case 2:
                e = (Dependency) super.poll();
                break;
            case 3:
                e = (Dependency) super.poll(l.longValue(), timeUnit);
                break;
            default:
                return null;
        }
        return e;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i, E e) {
        try {
            this.b.lock();
            if (i == 1) {
                super.remove(e);
            }
            boolean offer = this.a.offer(e);
            return offer;
        } finally {
            this.b.unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public E b(int i, Long l, TimeUnit timeUnit) {
        E a2;
        while (true) {
            a2 = a(i, l, timeUnit);
            if (a2 == null || a(a2)) {
                return a2;
            }
            a(i, a2);
        }
        return a2;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(E e) {
        return e.areDependenciesMet();
    }

    public void recycleBlockedQueue() {
        try {
            this.b.lock();
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                Dependency dependency = (Dependency) it.next();
                if (a(dependency)) {
                    super.offer(dependency);
                    it.remove();
                }
            }
        } finally {
            this.b.unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public <T> T[] a(T[] tArr, T[] tArr2) {
        int length = tArr.length;
        int length2 = tArr2.length;
        T[] tArr3 = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), length + length2);
        System.arraycopy(tArr, 0, tArr3, 0, length);
        System.arraycopy(tArr2, 0, tArr3, length, length2);
        return tArr3;
    }
}
