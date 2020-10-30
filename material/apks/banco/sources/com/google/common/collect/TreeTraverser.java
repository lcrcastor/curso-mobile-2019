package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;

@GwtCompatible
@Beta
public abstract class TreeTraverser<T> {

    final class BreadthFirstIterator extends UnmodifiableIterator<T> implements PeekingIterator<T> {
        private final Queue<T> b = new ArrayDeque();

        BreadthFirstIterator(T t) {
            this.b.add(t);
        }

        public boolean hasNext() {
            return !this.b.isEmpty();
        }

        public T peek() {
            return this.b.element();
        }

        public T next() {
            T remove = this.b.remove();
            Iterables.addAll(this.b, TreeTraverser.this.children(remove));
            return remove;
        }
    }

    final class PostOrderIterator extends AbstractIterator<T> {
        private final ArrayDeque<PostOrderNode<T>> b = new ArrayDeque<>();

        PostOrderIterator(T t) {
            this.b.addLast(a(t));
        }

        /* access modifiers changed from: protected */
        public T computeNext() {
            while (!this.b.isEmpty()) {
                PostOrderNode postOrderNode = (PostOrderNode) this.b.getLast();
                if (postOrderNode.b.hasNext()) {
                    this.b.addLast(a(postOrderNode.b.next()));
                } else {
                    this.b.removeLast();
                    return postOrderNode.a;
                }
            }
            return endOfData();
        }

        private PostOrderNode<T> a(T t) {
            return new PostOrderNode<>(t, TreeTraverser.this.children(t).iterator());
        }
    }

    static final class PostOrderNode<T> {
        final T a;
        final Iterator<T> b;

        PostOrderNode(T t, Iterator<T> it) {
            this.a = Preconditions.checkNotNull(t);
            this.b = (Iterator) Preconditions.checkNotNull(it);
        }
    }

    final class PreOrderIterator extends UnmodifiableIterator<T> {
        private final Deque<Iterator<T>> b = new ArrayDeque();

        PreOrderIterator(T t) {
            this.b.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(t)));
        }

        public boolean hasNext() {
            return !this.b.isEmpty();
        }

        public T next() {
            Iterator it = (Iterator) this.b.getLast();
            T checkNotNull = Preconditions.checkNotNull(it.next());
            if (!it.hasNext()) {
                this.b.removeLast();
            }
            Iterator it2 = TreeTraverser.this.children(checkNotNull).iterator();
            if (it2.hasNext()) {
                this.b.addLast(it2);
            }
            return checkNotNull;
        }
    }

    public abstract Iterable<T> children(T t);

    public static <T> TreeTraverser<T> using(final Function<T, ? extends Iterable<T>> function) {
        Preconditions.checkNotNull(function);
        return new TreeTraverser<T>() {
            public Iterable<T> children(T t) {
                return (Iterable) function.apply(t);
            }
        };
    }

    public final FluentIterable<T> preOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() {
            /* renamed from: a */
            public UnmodifiableIterator<T> iterator() {
                return TreeTraverser.this.a(t);
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public UnmodifiableIterator<T> a(T t) {
        return new PreOrderIterator(t);
    }

    public final FluentIterable<T> postOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() {
            /* renamed from: a */
            public UnmodifiableIterator<T> iterator() {
                return TreeTraverser.this.b(t);
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public UnmodifiableIterator<T> b(T t) {
        return new PostOrderIterator(t);
    }

    public final FluentIterable<T> breadthFirstTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() {
            /* renamed from: a */
            public UnmodifiableIterator<T> iterator() {
                return new BreadthFirstIterator(t);
            }
        };
    }
}
