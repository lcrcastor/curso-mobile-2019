package io.fabric.sdk.android;

import android.content.Context;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Task;
import java.io.File;
import java.util.Collection;

public abstract class Kit<Result> implements Comparable<Kit> {
    Fabric b;
    InitializationTask<Result> c = new InitializationTask<>(this);
    Context d;
    InitializationCallback<Result> e;
    IdManager f;
    final DependsOn g = ((DependsOn) getClass().getAnnotation(DependsOn.class));

    /* access modifiers changed from: protected */
    public abstract Result doInBackground();

    public abstract String getIdentifier();

    public abstract String getVersion();

    /* access modifiers changed from: protected */
    public void onCancelled(Result result) {
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Result result) {
    }

    /* access modifiers changed from: protected */
    public boolean onPreExecute() {
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void a(Context context, Fabric fabric, InitializationCallback<Result> initializationCallback, IdManager idManager) {
        this.b = fabric;
        this.d = new FabricContext(context, getIdentifier(), getPath());
        this.e = initializationCallback;
        this.f = idManager;
    }

    /* access modifiers changed from: 0000 */
    public final void w() {
        this.c.executeOnExecutor(this.b.getExecutorService(), null);
    }

    /* access modifiers changed from: protected */
    public IdManager getIdManager() {
        return this.f;
    }

    public Context getContext() {
        return this.d;
    }

    public Fabric getFabric() {
        return this.b;
    }

    public String getPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(".Fabric");
        sb.append(File.separator);
        sb.append(getIdentifier());
        return sb.toString();
    }

    public int compareTo(Kit kit) {
        if (a(kit)) {
            return 1;
        }
        if (kit.a(this)) {
            return -1;
        }
        if (x() && !kit.x()) {
            return 1;
        }
        if (x() || !kit.x()) {
            return 0;
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Kit kit) {
        if (x()) {
            for (Class isAssignableFrom : this.g.value()) {
                if (isAssignableFrom.isAssignableFrom(kit.getClass())) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean x() {
        return this.g != null;
    }

    /* access modifiers changed from: protected */
    public Collection<Task> getDependencies() {
        return this.c.getDependencies();
    }
}
