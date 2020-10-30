package io.reactivex.internal.operators.observable;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class BlockingObservableIterable<T> implements Iterable<T> {
    final ObservableSource<? extends T> a;
    final int b;

    static final class BlockingObservableIterator<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable, Iterator<T> {
        private static final long serialVersionUID = 6695226475494099826L;
        final SpscLinkedArrayQueue<T> a;
        final Lock b = new ReentrantLock();
        final Condition c = this.b.newCondition();
        volatile boolean d;
        Throwable e;

        BlockingObservableIterator(int i) {
            this.a = new SpscLinkedArrayQueue<>(i);
        }

        public boolean hasNext() {
            while (true) {
                boolean z = this.d;
                boolean isEmpty = this.a.isEmpty();
                if (z) {
                    Throwable th = this.e;
                    if (th != null) {
                        throw ExceptionHelper.wrapOrThrow(th);
                    } else if (isEmpty) {
                        return false;
                    }
                }
                if (!isEmpty) {
                    return true;
                }
                BlockingHelper.verifyNonBlocking();
                this.b.lock();
                while (!this.d && this.a.isEmpty()) {
                    try {
                        this.c.await();
                    } catch (InterruptedException e2) {
                        DisposableHelper.dispose(this);
                        a();
                        throw ExceptionHelper.wrapOrThrow(e2);
                    } catch (Throwable th2) {
                        this.b.unlock();
                        throw th2;
                    }
                }
                this.b.unlock();
            }
        }

        public T next() {
            if (hasNext()) {
                return this.a.poll();
            }
            throw new NoSuchElementException();
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onNext(T t) {
            this.a.offer(t);
            a();
        }

        public void onError(Throwable th) {
            this.e = th;
            this.d = true;
            a();
        }

        public void onComplete() {
            this.d = true;
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b.lock();
            try {
                this.c.signalAll();
            } finally {
                this.b.unlock();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException(ProductAction.ACTION_REMOVE);
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    public BlockingObservableIterable(ObservableSource<? extends T> observableSource, int i) {
        this.a = observableSource;
        this.b = i;
    }

    public Iterator<T> iterator() {
        BlockingObservableIterator blockingObservableIterator = new BlockingObservableIterator(this.b);
        this.a.subscribe(blockingObservableIterator);
        return blockingObservableIterator;
    }
}
