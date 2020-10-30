package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

public final class ObservableFromArray<T> extends Observable<T> {
    final T[] a;

    static final class FromArrayDisposable<T> extends BasicQueueDisposable<T> {
        final Observer<? super T> a;
        final T[] b;
        int c;
        boolean d;
        volatile boolean e;

        FromArrayDisposable(Observer<? super T> observer, T[] tArr) {
            this.a = observer;
            this.b = tArr;
        }

        public int requestFusion(int i) {
            if ((i & 1) == 0) {
                return 0;
            }
            this.d = true;
            return 1;
        }

        @Nullable
        public T poll() {
            int i = this.c;
            T[] tArr = this.b;
            if (i == tArr.length) {
                return null;
            }
            this.c = i + 1;
            return ObjectHelper.requireNonNull(tArr[i], "The array element is null");
        }

        public boolean isEmpty() {
            return this.c == this.b.length;
        }

        public void clear() {
            this.c = this.b.length;
        }

        public void dispose() {
            this.e = true;
        }

        public boolean isDisposed() {
            return this.e;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            T[] tArr = this.b;
            int length = tArr.length;
            for (int i = 0; i < length && !isDisposed(); i++) {
                T t = tArr[i];
                if (t == null) {
                    Observer<? super T> observer = this.a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("The ");
                    sb.append(i);
                    sb.append("th element is null");
                    observer.onError(new NullPointerException(sb.toString()));
                    return;
                }
                this.a.onNext(t);
            }
            if (!isDisposed()) {
                this.a.onComplete();
            }
        }
    }

    public ObservableFromArray(T[] tArr) {
        this.a = tArr;
    }

    public void subscribeActual(Observer<? super T> observer) {
        FromArrayDisposable fromArrayDisposable = new FromArrayDisposable(observer, this.a);
        observer.onSubscribe(fromArrayDisposable);
        if (!fromArrayDisposable.d) {
            fromArrayDisposable.a();
        }
    }
}
