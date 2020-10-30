package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import java.util.concurrent.atomic.AtomicReference;

public final class MpscLinkedQueue<T> implements SimplePlainQueue<T> {
    private final AtomicReference<LinkedQueueNode<T>> a = new AtomicReference<>();
    private final AtomicReference<LinkedQueueNode<T>> b = new AtomicReference<>();

    static final class LinkedQueueNode<E> extends AtomicReference<LinkedQueueNode<E>> {
        private static final long serialVersionUID = 2404266111789071508L;
        private E a;

        LinkedQueueNode() {
        }

        LinkedQueueNode(E e) {
            a(e);
        }

        public E a() {
            E b = b();
            a((E) null);
            return b;
        }

        public E b() {
            return this.a;
        }

        public void a(E e) {
            this.a = e;
        }

        public void a(LinkedQueueNode<E> linkedQueueNode) {
            lazySet(linkedQueueNode);
        }

        public LinkedQueueNode<E> c() {
            return (LinkedQueueNode) get();
        }
    }

    public MpscLinkedQueue() {
        LinkedQueueNode linkedQueueNode = new LinkedQueueNode();
        b(linkedQueueNode);
        a(linkedQueueNode);
    }

    public boolean offer(T t) {
        if (t == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        LinkedQueueNode linkedQueueNode = new LinkedQueueNode(t);
        a(linkedQueueNode).a(linkedQueueNode);
        return true;
    }

    @Nullable
    public T poll() {
        LinkedQueueNode c;
        LinkedQueueNode c2 = c();
        LinkedQueueNode c3 = c2.c();
        if (c3 != null) {
            T a2 = c3.a();
            b(c3);
            return a2;
        } else if (c2 == a()) {
            return null;
        } else {
            do {
                c = c2.c();
            } while (c == null);
            T a3 = c.a();
            b(c);
            return a3;
        }
    }

    public boolean offer(T t, T t2) {
        offer(t);
        offer(t2);
        return true;
    }

    public void clear() {
        while (poll() != null) {
            if (isEmpty()) {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public LinkedQueueNode<T> a() {
        return (LinkedQueueNode) this.a.get();
    }

    /* access modifiers changed from: 0000 */
    public LinkedQueueNode<T> a(LinkedQueueNode<T> linkedQueueNode) {
        return (LinkedQueueNode) this.a.getAndSet(linkedQueueNode);
    }

    /* access modifiers changed from: 0000 */
    public LinkedQueueNode<T> b() {
        return (LinkedQueueNode) this.b.get();
    }

    /* access modifiers changed from: 0000 */
    public LinkedQueueNode<T> c() {
        return (LinkedQueueNode) this.b.get();
    }

    /* access modifiers changed from: 0000 */
    public void b(LinkedQueueNode<T> linkedQueueNode) {
        this.b.lazySet(linkedQueueNode);
    }

    public boolean isEmpty() {
        return b() == a();
    }
}
