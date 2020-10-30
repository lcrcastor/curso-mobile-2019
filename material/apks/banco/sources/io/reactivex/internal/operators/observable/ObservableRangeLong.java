package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.observers.BasicIntQueueDisposable;

public final class ObservableRangeLong extends Observable<Long> {
    private final long a;
    private final long b;

    static final class RangeDisposable extends BasicIntQueueDisposable<Long> {
        private static final long serialVersionUID = 396518478098735504L;
        final Observer<? super Long> a;
        final long b;
        long c;
        boolean d;

        RangeDisposable(Observer<? super Long> observer, long j, long j2) {
            this.a = observer;
            this.c = j;
            this.b = j2;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (!this.d) {
                Observer<? super Long> observer = this.a;
                long j = this.b;
                for (long j2 = this.c; j2 != j && get() == 0; j2++) {
                    observer.onNext(Long.valueOf(j2));
                }
                if (get() == 0) {
                    lazySet(1);
                    observer.onComplete();
                }
            }
        }

        @Nullable
        /* renamed from: b */
        public Long poll() {
            long j = this.c;
            if (j != this.b) {
                this.c = j + 1;
                return Long.valueOf(j);
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

    public ObservableRangeLong(long j, long j2) {
        this.a = j;
        this.b = j2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super Long> observer) {
        RangeDisposable rangeDisposable = new RangeDisposable(observer, this.a, this.a + this.b);
        observer.onSubscribe(rangeDisposable);
        rangeDisposable.a();
    }
}
