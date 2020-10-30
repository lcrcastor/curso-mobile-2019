package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle.Event;

class FullLifecycleObserverAdapter implements GenericLifecycleObserver {
    private final FullLifecycleObserver a;

    FullLifecycleObserverAdapter(FullLifecycleObserver fullLifecycleObserver) {
        this.a = fullLifecycleObserver;
    }

    public void onStateChanged(LifecycleOwner lifecycleOwner, Event event) {
        switch (event) {
            case ON_CREATE:
                this.a.a(lifecycleOwner);
                return;
            case ON_START:
                this.a.b(lifecycleOwner);
                return;
            case ON_RESUME:
                this.a.c(lifecycleOwner);
                return;
            case ON_PAUSE:
                this.a.d(lifecycleOwner);
                return;
            case ON_STOP:
                this.a.e(lifecycleOwner);
                return;
            case ON_DESTROY:
                this.a.f(lifecycleOwner);
                return;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
            default:
                return;
        }
    }
}
