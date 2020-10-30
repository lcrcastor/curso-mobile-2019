package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.Iterator;

@GwtCompatible
@Beta
public abstract class BinaryTreeTraverser<T> extends TreeTraverser<T> {

    final class InOrderIterator extends AbstractIterator<T> {
        private final Deque<T> b = new ArrayDeque(8);
        private final BitSet c = new BitSet();

        InOrderIterator(T t) {
            this.b.addLast(t);
        }

        /* access modifiers changed from: protected */
        public T computeNext() {
            while (!this.b.isEmpty()) {
                T last = this.b.getLast();
                if (this.c.get(this.b.size() - 1)) {
                    this.b.removeLast();
                    this.c.clear(this.b.size());
                    BinaryTreeTraverser.b(this.b, BinaryTreeTraverser.this.rightChild(last));
                    return last;
                }
                this.c.set(this.b.size() - 1);
                BinaryTreeTraverser.b(this.b, BinaryTreeTraverser.this.leftChild(last));
            }
            return endOfData();
        }
    }

    public abstract Optional<T> leftChild(T t);

    public abstract Optional<T> rightChild(T t);

    public final Iterable<T> children(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() {
            public Iterator<T> iterator() {
                return new AbstractIterator<T>() {
                    boolean a;
                    boolean b;

                    /* access modifiers changed from: protected */
                    public T computeNext() {
                        if (!this.a) {
                            this.a = true;
                            Optional leftChild = BinaryTreeTraverser.this.leftChild(t);
                            if (leftChild.isPresent()) {
                                return leftChild.get();
                            }
                        }
                        if (!this.b) {
                            this.b = true;
                            Optional rightChild = BinaryTreeTraverser.this.rightChild(t);
                            if (rightChild.isPresent()) {
                                return rightChild.get();
                            }
                        }
                        return endOfData();
                    }
                };
            }
        };
    }

    public final FluentIterable<T> inOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() {
            /* renamed from: a */
            public UnmodifiableIterator<T> iterator() {
                return new InOrderIterator(t);
            }
        };
    }

    /* access modifiers changed from: private */
    public static <T> void b(Deque<T> deque, Optional<T> optional) {
        if (optional.isPresent()) {
            deque.addLast(optional.get());
        }
    }
}
