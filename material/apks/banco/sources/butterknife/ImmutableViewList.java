package butterknife;

import android.view.View;
import java.util.AbstractList;
import java.util.RandomAccess;

final class ImmutableViewList<T extends View> extends AbstractList<T> implements RandomAccess {
    private final T[] a;

    ImmutableViewList(T[] tArr) {
        this.a = tArr;
    }

    /* renamed from: a */
    public T get(int i) {
        return this.a[i];
    }

    public int size() {
        return this.a.length;
    }

    public boolean contains(Object obj) {
        for (T t : this.a) {
            if (t == obj) {
                return true;
            }
        }
        return false;
    }
}
