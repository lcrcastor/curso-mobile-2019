package android.support.v4.app;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.State;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ReportFragment;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.util.SimpleArrayMap;

@RestrictTo({Scope.LIBRARY_GROUP})
public class SupportActivity extends Activity implements LifecycleOwner {
    private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> a = new SimpleArrayMap<>();
    private LifecycleRegistry b = new LifecycleRegistry(this);

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static class ExtraData {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void putExtraData(ExtraData extraData) {
        this.a.put(extraData.getClass(), extraData);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        ReportFragment.injectIfNeededIn(this);
    }

    @CallSuper
    public void onSaveInstanceState(Bundle bundle) {
        this.b.markState(State.CREATED);
        super.onSaveInstanceState(bundle);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public <T extends ExtraData> T getExtraData(Class<T> cls) {
        return (ExtraData) this.a.get(cls);
    }

    public Lifecycle getLifecycle() {
        return this.b;
    }
}
