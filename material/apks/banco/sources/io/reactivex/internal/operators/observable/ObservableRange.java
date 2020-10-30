package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.observers.BasicIntQueueDisposable;

public final class ObservableRange extends Observable<Integer> {
    private final int a;
    private final long b;

    static final class RangeDisposable extends BasicIntQueueDisposable<Integer> {
        private static final long serialVersionUID = 396518478098735504L;
        final Observer<? super Integer> a;
        final long b;
        long c;
        boolean d;

        RangeDisposable(Observer<? super Integer> observer, long j, long j2) {
            this.a = observer;
            this.c = j;
            this.b = j2;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (!this.d) {
                Observer<? super Integer> observer = this.a;
                long j = this.b;
                for (long j2 = this.c; j2 != j && get() == 0; j2++) {
                    observer.onNext(Integer.valueOf((int) j2));
                }
                if (get() == 0) {
                    lazySet(1);
                    observer.onComplete();
                }
            }
        }

        @Nullable
        /* renamed from: b */
        public Integer poll() {
            long j = this.c;
            if (j != this.b) {
                this.c = j + 1;
                return Integer.valueOf((int) j);
            }
            lazySet(1);
            return null;
        }

        public boolean isEmpty() {
            return this.c == this.b;
        }

        public void clear() {
            this.c = this.b;
            lazySet(1);
        }

        public void dispose() {
            set(1);
        }

        public boolean isDisposed() {
            return get() != 0;
        }

        public int requestFusion(int i) {
            if ((i & 1) == 0) {
                return 0;
            }
            this.d = true;
            return 1;
        }
    }

    public ObservableRange(int i, int i2) {
        this.a = i;
        this.b = ((long) i) + ((long) i2);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super Integer> observer) {
        RangeDisposable rangeDisposable = new RangeDisposable(observer, (long) this.a, this.b);
        observer.onSubscribe(rangeDisposable);
        rangeDisposable.a();
    }
}
