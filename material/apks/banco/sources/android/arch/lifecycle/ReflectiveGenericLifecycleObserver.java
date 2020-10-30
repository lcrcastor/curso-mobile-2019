package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle.Event;

class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {
    private final Object a;
    private final CallbackInfo b = ClassesInfoCache.a.b(this.a.getClass());

    ReflectiveGenericLifecycleObserver(Object obj) {
        this.a = obj;
    }

    public void onStateChanged(LifecycleOwner lifecycleOwner, Event event) {
        this.b.a(lifecycleOwner, event, this.a);
    }
}
