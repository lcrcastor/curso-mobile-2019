package android.arch.lifecycle;

import android.arch.core.internal.FastSafeIterableMap;
import android.arch.core.internal.SafeIterableMap.IteratorWithAdditions;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.Lifecycle.State;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

public class LifecycleRegistry extends Lifecycle {
    private FastSafeIterableMap<LifecycleObserver, ObserverWithState> a = new FastSafeIterableMap<>();
    private State b;
    private final WeakReference<LifecycleOwner> c;
    private int d = 0;
    private boolean e = false;
    private boolean f = false;
    private ArrayList<State> g = new ArrayList<>();

    static class ObserverWithState {
        State a;
        GenericLifecycleObserver b;

        ObserverWithState(LifecycleObserver lifecycleObserver, State state) {
            this.b = Lifecycling.a((Object) lifecycleObserver);
            this.a = state;
        }

        /* access modifiers changed from: 0000 */
        public void a(LifecycleOwner lifecycleOwner, Event event) {
            State a2 = LifecycleRegistry.a(event);
            this.a = LifecycleRegistry.a(this.a, a2);
            this.b.onStateChanged(lifecycleOwner, event);
            this.a = a2;
        }
    }

    public LifecycleRegistry(@NonNull LifecycleOwner lifecycleOwner) {
        this.c = new WeakReference<>(lifecycleOwner);
        this.b = State.INITIALIZED;
    }

    @MainThread
    public void markState(@NonNull State state) {
        a(state);
    }

    public void handleLifecycleEvent(@NonNull Event event) {
        a(a(event));
    }

    private void a(State state) {
        if (this.b != state) {
            this.b = state;
            if (this.e || this.d != 0) {
                this.f = true;
                return;
            }
            this.e = true;
            c();
            this.e = false;
        }
    }

    private boolean a() {
        boolean z = true;
        if (this.a.size() == 0) {
            return true;
        }
        State state = ((ObserverWithState) this.a.eldest().getValue()).a;
        State state2 = ((ObserverWithState) this.a.newest().getValue()).a;
        if (!(state == state2 && this.b == state2)) {
            z = false;
        }
        return z;
    }

    private State a(LifecycleObserver lifecycleObserver) {
        Entry ceil = this.a.ceil(lifecycleObserver);
        State state = null;
        State state2 = ceil != null ? ((ObserverWithState) ceil.getValue()).a : null;
        if (!this.g.isEmpty()) {
            state = (State) this.g.get(this.g.size() - 1);
        }
        return a(a(this.b, state2), state);
    }

    public void addObserver(@NonNull LifecycleObserver lifecycleObserver) {
        ObserverWithState observerWithState = new ObserverWithState(lifecycleObserver, this.b == State.DESTROYED ? State.DESTROYED : State.INITIALIZED);
        if (((ObserverWithState) this.a.putIfAbsent(lifecycleObserver, observerWithState)) == null) {
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.c.get();
            if (lifecycleOwner != null) {
                boolean z = this.d != 0 || this.e;
                State a2 = a(lifecycleObserver);
                this.d++;
                while (observerWithState.a.compareTo(a2) < 0 && this.a.contains(lifecycleObserver)) {
                    b(observerWithState.a);
                    observerWithState.a(lifecycleOwner, d(observerWithState.a));
                    b();
                    a2 = a(lifecycleObserver);
                }
                if (!z) {
                    c();
                }
                this.d--;
            }
        }
    }

    private void b() {
        this.g.remove(this.g.size() - 1);
    }

    private void b(State state) {
        this.g.add(state);
    }

    public void removeObserver(@NonNull LifecycleObserver lifecycleObserver) {
        this.a.remove(lifecycleObserver);
    }

    public int getObserverCount() {
        return this.a.size();
    }

    @NonNull
    public State getCurrentState() {
        return this.b;
    }

    static State a(Event event) {
        switch (event) {
            case ON_CREATE:
            case ON_STOP:
                return State.CREATED;
            case ON_START:
            case ON_PAUSE:
                return State.STARTED;
            case ON_RESUME:
                return State.RESUMED;
            case ON_DESTROY:
                return State.DESTROYED;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected event value ");
                sb.append(event);
                throw new IllegalArgumentException(sb.toString());
        }
    }

    private static Event c(State state) {
        switch (state) {
            case INITIALIZED:
                throw new IllegalArgumentException();
            case CREATED:
                return Event.ON_DESTROY;
            case STARTED:
                return Event.ON_STOP;
            case RESUMED:
                return Event.ON_PAUSE;
            case DESTROYED:
                throw new IllegalArgumentException();
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected state value ");
                sb.append(state);
                throw new IllegalArgumentException(sb.toString());
        }
    }

    private static Event d(State state) {
        switch (state) {
            case INITIALIZED:
            case DESTROYED:
                return Event.ON_CREATE;
            case CREATED:
                return Event.ON_START;
            case STARTED:
                return Event.ON_RESUME;
            case RESUMED:
                throw new IllegalArgumentException();
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected state value ");
                sb.append(state);
                throw new IllegalArgumentException(sb.toString());
        }
    }

    private void a(LifecycleOwner lifecycleOwner) {
        IteratorWithAdditions iteratorWithAdditions = this.a.iteratorWithAdditions();
        while (iteratorWithAdditions.hasNext() && !this.f) {
            Entry entry = (Entry) iteratorWithAdditions.next();
            ObserverWithState observerWithState = (ObserverWithState) entry.getValue();
            while (observerWithState.a.compareTo(this.b) < 0 && !this.f && this.a.contains(entry.getKey())) {
                b(observerWithState.a);
                observerWithState.a(lifecycleOwner, d(observerWithState.a));
                b();
            }
        }
    }

    private void b(LifecycleOwner lifecycleOwner) {
        Iterator descendingIterator = this.a.descendingIterator();
        while (descendingIterator.hasNext() && !this.f) {
            Entry entry = (Entry) descendingIterator.next();
            ObserverWithState observerWithState = (ObserverWithState) entry.getValue();
            while (observerWithState.a.compareTo(this.b) > 0 && !this.f && this.a.contains(entry.getKey())) {
                Event c2 = c(observerWithState.a);
                b(a(c2));
                observerWithState.a(lifecycleOwner, c2);
                b();
            }
        }
    }

    private void c() {
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.c.get();
        if (lifecycleOwner == null) {
            Log.w("LifecycleRegistry", "LifecycleOwner is garbage collected, you shouldn't try dispatch new events from it.");
            return;
        }
        while (!a()) {
            this.f = false;
            if (this.b.compareTo(((ObserverWithState) this.a.eldest().getValue()).a) < 0) {
                b(lifecycleOwner);
            }
            Entry newest = this.a.newest();
            if (!this.f && newest != null && this.b.compareTo(((ObserverWithState) newest.getValue()).a) > 0) {
                a(lifecycleOwner);
            }
        }
        this.f = false;
    }

    static State a(@NonNull State state, @Nullable State state2) {
        return (state2 == null || state2.compareTo(state) >= 0) ? state : state2;
    }
}
