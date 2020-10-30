package android.arch.lifecycle;

import android.arch.core.executor.ArchTaskExecutor;
import android.arch.core.internal.SafeIterableMap;
import android.arch.core.internal.SafeIterableMap.IteratorWithAdditions;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.Lifecycle.State;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Iterator;
import java.util.Map.Entry;

public abstract class LiveData<T> {
    /* access modifiers changed from: private */
    public static final Object b = new Object();
    /* access modifiers changed from: private */
    public final Object a = new Object();
    private SafeIterableMap<Observer<T>, ObserverWrapper> c = new SafeIterableMap<>();
    /* access modifiers changed from: private */
    public int d = 0;
    private volatile Object e = b;
    /* access modifiers changed from: private */
    public volatile Object f = b;
    private int g = -1;
    private boolean h;
    private boolean i;
    private final Runnable j = new Runnable() {
        public void run() {
            Object b;
            synchronized (LiveData.this.a) {
                b = LiveData.this.f;
                LiveData.this.f = LiveData.b;
            }
            LiveData.this.setValue(b);
        }
    };

    class AlwaysActiveObserver extends ObserverWrapper {
        /* access modifiers changed from: 0000 */
        public boolean a() {
            return true;
        }

        AlwaysActiveObserver(Observer<T> observer) {
            super(observer);
        }
    }

    class LifecycleBoundObserver extends ObserverWrapper implements GenericLifecycleObserver {
        @NonNull
        final LifecycleOwner a;

        LifecycleBoundObserver(LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer) {
            super(observer);
            this.a = lifecycleOwner;
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return this.a.getLifecycle().getCurrentState().isAtLeast(State.STARTED);
        }

        public void onStateChanged(LifecycleOwner lifecycleOwner, Event event) {
            if (this.a.getLifecycle().getCurrentState() == State.DESTROYED) {
                LiveData.this.removeObserver(this.c);
            } else {
                a(a());
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(LifecycleOwner lifecycleOwner) {
            return this.a == lifecycleOwner;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.a.getLifecycle().removeObserver(this);
        }
    }

    abstract class ObserverWrapper {
        final Observer<T> c;
        boolean d;
        int e = -1;

        /* access modifiers changed from: 0000 */
        public abstract boolean a();

        /* access modifiers changed from: 0000 */
        public boolean a(LifecycleOwner lifecycleOwner) {
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
        }

        ObserverWrapper(Observer<T> observer) {
            this.c = observer;
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z) {
            if (z != this.d) {
                this.d = z;
                int i = 1;
                boolean z2 = LiveData.this.d == 0;
                LiveData liveData = LiveData.this;
                int c2 = liveData.d;
                if (!this.d) {
                    i = -1;
                }
                liveData.d = c2 + i;
                if (z2 && this.d) {
                    LiveData.this.onActive();
                }
                if (LiveData.this.d == 0 && !this.d) {
                    LiveData.this.onInactive();
                }
                if (this.d) {
                    LiveData.this.b(this);
                }
            }
        }
    }

    public void onActive() {
    }

    public void onInactive() {
    }

    private void a(ObserverWrapper observerWrapper) {
        if (observerWrapper.d) {
            if (!observerWrapper.a()) {
                observerWrapper.a(false);
            } else if (observerWrapper.e < this.g) {
                observerWrapper.e = this.g;
                observerWrapper.c.onChanged(this.e);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(@Nullable ObserverWrapper observerWrapper) {
        if (this.h) {
            this.i = true;
            return;
        }
        this.h = true;
        do {
            this.i = false;
            if (observerWrapper == null) {
                IteratorWithAdditions iteratorWithAdditions = this.c.iteratorWithAdditions();
                while (iteratorWithAdditions.hasNext()) {
                    a((ObserverWrapper) ((Entry) iteratorWithAdditions.next()).getValue());
                    if (this.i) {
                        break;
                    }
                }
            } else {
                a(observerWrapper);
                observerWrapper = null;
            }
        } while (this.i);
        this.h = false;
    }

    @MainThread
    public void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer) {
        if (lifecycleOwner.getLifecycle().getCurrentState() != State.DESTROYED) {
            LifecycleBoundObserver lifecycleBoundObserver = new LifecycleBoundObserver(lifecycleOwner, observer);
            ObserverWrapper observerWrapper = (ObserverWrapper) this.c.putIfAbsent(observer, lifecycleBoundObserver);
            if (observerWrapper != null && !observerWrapper.a(lifecycleOwner)) {
                throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
            } else if (observerWrapper == null) {
                lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver);
            }
        }
    }

    @MainThread
    public void observeForever(@NonNull Observer<T> observer) {
        AlwaysActiveObserver alwaysActiveObserver = new AlwaysActiveObserver(observer);
        ObserverWrapper observerWrapper = (ObserverWrapper) this.c.putIfAbsent(observer, alwaysActiveObserver);
        if (observerWrapper != null && (observerWrapper instanceof LifecycleBoundObserver)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        } else if (observerWrapper == null) {
            alwaysActiveObserver.a(true);
        }
    }

    @MainThread
    public void removeObserver(@NonNull Observer<T> observer) {
        a("removeObserver");
        ObserverWrapper observerWrapper = (ObserverWrapper) this.c.remove(observer);
        if (observerWrapper != null) {
            observerWrapper.b();
            observerWrapper.a(false);
        }
    }

    @MainThread
    public void removeObservers(@NonNull LifecycleOwner lifecycleOwner) {
        a("removeObservers");
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (((ObserverWrapper) entry.getValue()).a(lifecycleOwner)) {
                removeObserver((Observer) entry.getKey());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void postValue(T t) {
        boolean z;
        synchronized (this.a) {
            z = this.f == b;
            this.f = t;
        }
        if (z) {
            ArchTaskExecutor.getInstance().postToMainThread(this.j);
        }
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void setValue(T t) {
        a("setValue");
        this.g++;
        this.e = t;
        b(null);
    }

    @Nullable
    public T getValue() {
        T t = this.e;
        if (t != b) {
            return t;
        }
        return null;
    }

    public boolean hasObservers() {
        return this.c.size() > 0;
    }

    public boolean hasActiveObservers() {
        return this.d > 0;
    }

    private static void a(String str) {
        if (!ArchTaskExecutor.getInstance().isMainThread()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot invoke ");
            sb.append(str);
            sb.append(" on a background");
            sb.append(" thread");
            throw new IllegalStateException(sb.toString());
        }
    }
}
