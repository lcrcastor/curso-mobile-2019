package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle.Event;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@RestrictTo({Scope.LIBRARY_GROUP})
public class SingleGeneratedAdapterObserver implements GenericLifecycleObserver {
    private final GeneratedAdapter a;

    SingleGeneratedAdapterObserver(GeneratedAdapter generatedAdapter) {
        this.a = generatedAdapter;
    }

    public void onStateChanged(LifecycleOwner lifecycleOwner, Event event) {
        this.a.callMethods(lifecycleOwner, event, false, null);
        this.a.callMethods(lifecycleOwner, event, true, null);
    }
}
