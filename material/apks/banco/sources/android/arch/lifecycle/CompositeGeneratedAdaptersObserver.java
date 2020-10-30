package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle.Event;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@RestrictTo({Scope.LIBRARY_GROUP})
public class CompositeGeneratedAdaptersObserver implements GenericLifecycleObserver {
    private final GeneratedAdapter[] a;

    CompositeGeneratedAdaptersObserver(GeneratedAdapter[] generatedAdapterArr) {
        this.a = generatedAdapterArr;
    }

    public void onStateChanged(LifecycleOwner lifecycleOwner, Event event) {
        MethodCallsLogger methodCallsLogger = new MethodCallsLogger();
        for (GeneratedAdapter callMethods : this.a) {
            callMethods.callMethods(lifecycleOwner, event, false, methodCallsLogger);
        }
        for (GeneratedAdapter callMethods2 : this.a) {
            callMethods2.callMethods(lifecycleOwner, event, true, methodCallsLogger);
        }
    }
}
