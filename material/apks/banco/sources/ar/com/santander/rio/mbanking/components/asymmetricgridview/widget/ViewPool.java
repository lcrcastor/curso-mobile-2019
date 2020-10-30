package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import java.util.Stack;

class ViewPool<T extends View> implements Parcelable {
    Stack<T> a;
    PoolObjectFactory<T> b;
    PoolStats c;

    static class PoolStats {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        PoolStats() {
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    ViewPool() {
        this.a = new Stack<>();
        this.b = null;
        this.c = new PoolStats();
    }

    ViewPool(PoolObjectFactory<T> poolObjectFactory) {
        this.a = new Stack<>();
        this.b = null;
        this.b = poolObjectFactory;
    }

    /* access modifiers changed from: 0000 */
    public T a() {
        if (this.a.size() > 0) {
            this.c.b++;
            this.c.a--;
            return (View) this.a.pop();
        }
        this.c.c++;
        T createObject = this.b != null ? this.b.createObject() : null;
        if (createObject != null) {
            this.c.d++;
        }
        return createObject;
    }

    /* access modifiers changed from: 0000 */
    public void a(T t) {
        this.a.push(t);
        this.c.a++;
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.c = new PoolStats();
        this.a.clear();
    }
}
