package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
public class TintContextWrapper extends ContextWrapper {
    private static final Object a = new Object();
    private static ArrayList<WeakReference<TintContextWrapper>> b;
    private final Resources c;
    private final Theme d;

    public static Context wrap(@NonNull Context context) {
        if (!a(context)) {
            return context;
        }
        synchronized (a) {
            if (b == null) {
                b = new ArrayList<>();
            } else {
                for (int size = b.size() - 1; size >= 0; size--) {
                    WeakReference weakReference = (WeakReference) b.get(size);
                    if (weakReference == null || weakReference.get() == null) {
                        b.remove(size);
                    }
                }
                for (int size2 = b.size() - 1; size2 >= 0; size2--) {
                    WeakReference weakReference2 = (WeakReference) b.get(size2);
                    TintContextWrapper tintContextWrapper = weakReference2 != null ? (TintContextWrapper) weakReference2.get() : null;
                    if (tintContextWrapper != null && tintContextWrapper.getBaseContext() == context) {
                        return tintContextWrapper;
                    }
                }
            }
            TintContextWrapper tintContextWrapper2 = new TintContextWrapper(context);
            b.add(new WeakReference(tintContextWrapper2));
            return tintContextWrapper2;
        }
    }

    private static boolean a(@NonNull Context context) {
        boolean z = false;
        if ((context instanceof TintContextWrapper) || (context.getResources() instanceof TintResources) || (context.getResources() instanceof VectorEnabledTintResources)) {
            return false;
        }
        if (VERSION.SDK_INT < 21 || VectorEnabledTintResources.shouldBeUsed()) {
            z = true;
        }
        return z;
    }

    private TintContextWrapper(@NonNull Context context) {
        super(context);
        if (VectorEnabledTintResources.shouldBeUsed()) {
            this.c = new VectorEnabledTintResources(this, context.getResources());
            this.d = this.c.newTheme();
            this.d.setTo(context.getTheme());
            return;
        }
        this.c = new TintResources(this, context.getResources());
        this.d = null;
    }

    public Theme getTheme() {
        return this.d == null ? super.getTheme() : this.d;
    }

    public void setTheme(int i) {
        if (this.d == null) {
            super.setTheme(i);
        } else {
            this.d.applyStyle(i, true);
        }
    }

    public Resources getResources() {
        return this.c;
    }

    public AssetManager getAssets() {
        return this.c.getAssets();
    }
}
