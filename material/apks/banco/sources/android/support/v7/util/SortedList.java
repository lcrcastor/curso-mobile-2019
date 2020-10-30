package android.support.v7.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class SortedList<T> {
    public static final int INVALID_POSITION = -1;
    T[] a;
    private T[] b;
    private int c;
    private int d;
    private int e;
    private Callback f;
    private BatchedCallback g;
    private int h;
    private final Class<T> i;

    public static class BatchedCallback<T2> extends Callback<T2> {
        final Callback<T2> a;
        private final BatchingListUpdateCallback b = new BatchingListUpdateCallback(this.a);

        public BatchedCallback(Callback<T2> callback) {
            this.a = callback;
        }

        public int compare(T2 t2, T2 t22) {
            return this.a.compare(t2, t22);
        }

        public void onInserted(int i, int i2) {
            this.b.onInserted(i, i2);
        }

        public void onRemoved(int i, int i2) {
            this.b.onRemoved(i, i2);
        }

        public void onMoved(int i, int i2) {
            this.b.onMoved(i, i2);
        }

        public void onChanged(int i, int i2) {
            this.b.onChanged(i, i2, null);
        }

        public void onChanged(int i, int i2, Object obj) {
            this.b.onChanged(i, i2, obj);
        }

        public boolean areContentsTheSame(T2 t2, T2 t22) {
            return this.a.areContentsTheSame(t2, t22);
        }

        public boolean areItemsTheSame(T2 t2, T2 t22) {
            return this.a.areItemsTheSame(t2, t22);
        }

        @Nullable
        public Object getChangePayload(T2 t2, T2 t22) {
            return this.a.getChangePayload(t2, t22);
        }

        public void dispatchLastEvent() {
            this.b.dispatchLastEvent();
        }
    }

    public static abstract class Callback<T2> implements ListUpdateCallback, Comparator<T2> {
        public abstract boolean areContentsTheSame(T2 t2, T2 t22);

        public abstract boolean areItemsTheSame(T2 t2, T2 t22);

        public abstract int compare(T2 t2, T2 t22);

        @Nullable
        public Object getChangePayload(T2 t2, T2 t22) {
            return null;
        }

        public abstract void onChanged(int i, int i2);

        public void onChanged(int i, int i2, Object obj) {
            onChanged(i, i2);
        }
    }

    public SortedList(Class<T> cls, Callback<T> callback) {
        this(cls, callback, 10);
    }

    public SortedList(Class<T> cls, Callback<T> callback, int i2) {
        this.i = cls;
        this.a = (Object[]) Array.newInstance(cls, i2);
        this.f = callback;
        this.h = 0;
    }

    public int size() {
        return this.h;
    }

    public int add(T t) {
        b();
        return a(t, true);
    }

    public void addAll(T[] tArr, boolean z) {
        b();
        if (tArr.length != 0) {
            if (z) {
                a(tArr);
            } else {
                a((T[]) d(tArr));
            }
        }
    }

    public void addAll(T... tArr) {
        addAll(tArr, false);
    }

    public void addAll(Collection<T> collection) {
        addAll(collection.toArray((Object[]) Array.newInstance(this.i, collection.size())), true);
    }

    public void replaceAll(@NonNull T[] tArr, boolean z) {
        b();
        if (z) {
            b(tArr);
        } else {
            b(d(tArr));
        }
    }

    public void replaceAll(@NonNull T... tArr) {
        replaceAll(tArr, false);
    }

    public void replaceAll(@NonNull Collection<T> collection) {
        replaceAll(collection.toArray((Object[]) Array.newInstance(this.i, collection.size())), true);
    }

    private void a(T[] tArr) {
        if (tArr.length >= 1) {
            int c2 = c(tArr);
            if (this.h == 0) {
                this.a = tArr;
                this.h = c2;
                this.f.onInserted(0, c2);
            } else {
                a(tArr, c2);
            }
        }
    }

    private void b(@NonNull T[] tArr) {
        boolean z = !(this.f instanceof BatchedCallback);
        if (z) {
            beginBatchedUpdates();
        }
        this.c = 0;
        this.d = this.h;
        this.b = this.a;
        this.e = 0;
        int c2 = c(tArr);
        this.a = (Object[]) Array.newInstance(this.i, c2);
        while (true) {
            if (this.e >= c2 && this.c >= this.d) {
                break;
            } else if (this.c >= this.d) {
                int i2 = this.e;
                int i3 = c2 - this.e;
                System.arraycopy(tArr, i2, this.a, i2, i3);
                this.e += i3;
                this.h += i3;
                this.f.onInserted(i2, i3);
                break;
            } else if (this.e >= c2) {
                int i4 = this.d - this.c;
                this.h -= i4;
                this.f.onRemoved(this.e, i4);
                break;
            } else {
                T t = this.b[this.c];
                T t2 = tArr[this.e];
                int compare = this.f.compare(t, t2);
                if (compare < 0) {
                    a();
                } else if (compare > 0) {
                    a(t2);
                } else if (!this.f.areItemsTheSame(t, t2)) {
                    a();
                    a(t2);
                } else {
                    this.a[this.e] = t2;
                    this.c++;
                    this.e++;
                    if (!this.f.areContentsTheSame(t, t2)) {
                        this.f.onChanged(this.e - 1, 1, this.f.getChangePayload(t, t2));
                    }
                }
            }
        }
        this.b = null;
        if (z) {
            endBatchedUpdates();
        }
    }

    private void a(T t) {
        this.a[this.e] = t;
        this.e++;
        this.h++;
        this.f.onInserted(this.e - 1, 1);
    }

    private void a() {
        this.h--;
        this.c++;
        this.f.onRemoved(this.e, 1);
    }

    private int c(@NonNull T[] tArr) {
        int i2 = 0;
        if (tArr.length == 0) {
            return 0;
        }
        Arrays.sort(tArr, this.f);
        int i3 = 1;
        for (int i4 = 1; i4 < tArr.length; i4++) {
            T t = tArr[i4];
            if (this.f.compare(tArr[i2], t) == 0) {
                int a2 = a(t, tArr, i2, i3);
                if (a2 != -1) {
                    tArr[a2] = t;
                } else {
                    if (i3 != i4) {
                        tArr[i3] = t;
                    }
                    i3++;
                }
            } else {
                if (i3 != i4) {
                    tArr[i3] = t;
                }
                int i5 = i3;
                i3++;
                i2 = i5;
            }
        }
        return i3;
    }

    private int a(T t, T[] tArr, int i2, int i3) {
        while (i2 < i3) {
            if (this.f.areItemsTheSame(tArr[i2], t)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private void a(T[] tArr, int i2) {
        int i3 = 0;
        boolean z = !(this.f instanceof BatchedCallback);
        if (z) {
            beginBatchedUpdates();
        }
        this.b = this.a;
        this.c = 0;
        this.d = this.h;
        this.a = (Object[]) Array.newInstance(this.i, this.h + i2 + 10);
        this.e = 0;
        while (true) {
            if (this.c >= this.d && i3 >= i2) {
                break;
            } else if (this.c == this.d) {
                int i4 = i2 - i3;
                System.arraycopy(tArr, i3, this.a, this.e, i4);
                this.e += i4;
                this.h += i4;
                this.f.onInserted(this.e - i4, i4);
                break;
            } else if (i3 == i2) {
                int i5 = this.d - this.c;
                System.arraycopy(this.b, this.c, this.a, this.e, i5);
                this.e += i5;
                break;
            } else {
                T t = this.b[this.c];
                T t2 = tArr[i3];
                int compare = this.f.compare(t, t2);
                if (compare > 0) {
                    T[] tArr2 = this.a;
                    int i6 = this.e;
                    this.e = i6 + 1;
                    tArr2[i6] = t2;
                    this.h++;
                    i3++;
                    this.f.onInserted(this.e - 1, 1);
                } else if (compare != 0 || !this.f.areItemsTheSame(t, t2)) {
                    T[] tArr3 = this.a;
                    int i7 = this.e;
                    this.e = i7 + 1;
                    tArr3[i7] = t;
                    this.c++;
                } else {
                    T[] tArr4 = this.a;
                    int i8 = this.e;
                    this.e = i8 + 1;
                    tArr4[i8] = t2;
                    i3++;
                    this.c++;
                    if (!this.f.areContentsTheSame(t, t2)) {
                        this.f.onChanged(this.e - 1, 1, this.f.getChangePayload(t, t2));
                    }
                }
            }
        }
        this.b = null;
        if (z) {
            endBatchedUpdates();
        }
    }

    private void b() {
        if (this.b != null) {
            throw new IllegalStateException("Data cannot be mutated in the middle of a batch update operation such as addAll or replaceAll.");
        }
    }

    public void beginBatchedUpdates() {
        b();
        if (!(this.f instanceof BatchedCallback)) {
            if (this.g == null) {
                this.g = new BatchedCallback(this.f);
            }
            this.f = this.g;
        }
    }

    public void endBatchedUpdates() {
        b();
        if (this.f instanceof BatchedCallback) {
            ((BatchedCallback) this.f).dispatchLastEvent();
        }
        if (this.f == this.g) {
            this.f = this.g.a;
        }
    }

    private int a(T t, boolean z) {
        int a2 = a(t, this.a, 0, this.h, 1);
        if (a2 == -1) {
            a2 = 0;
        } else if (a2 < this.h) {
            T t2 = this.a[a2];
            if (this.f.areItemsTheSame(t2, t)) {
                if (this.f.areContentsTheSame(t2, t)) {
                    this.a[a2] = t;
                    return a2;
                }
                this.a[a2] = t;
                this.f.onChanged(a2, 1, this.f.getChangePayload(t2, t));
                return a2;
            }
        }
        a(a2, t);
        if (z) {
            this.f.onInserted(a2, 1);
        }
        return a2;
    }

    public boolean remove(T t) {
        b();
        return b(t, true);
    }

    public T removeItemAt(int i2) {
        b();
        T t = get(i2);
        a(i2, true);
        return t;
    }

    private boolean b(T t, boolean z) {
        int a2 = a(t, this.a, 0, this.h, 2);
        if (a2 == -1) {
            return false;
        }
        a(a2, z);
        return true;
    }

    private void a(int i2, boolean z) {
        System.arraycopy(this.a, i2 + 1, this.a, i2, (this.h - i2) - 1);
        this.h--;
        this.a[this.h] = null;
        if (z) {
            this.f.onRemoved(i2, 1);
        }
    }

    public void updateItemAt(int i2, T t) {
        b();
        T t2 = get(i2);
        boolean z = t2 == t || !this.f.areContentsTheSame(t2, t);
        if (t2 == t || this.f.compare(t2, t) != 0) {
            if (z) {
                this.f.onChanged(i2, 1, this.f.getChangePayload(t2, t));
            }
            a(i2, false);
            int a2 = a(t, false);
            if (i2 != a2) {
                this.f.onMoved(i2, a2);
            }
            return;
        }
        this.a[i2] = t;
        if (z) {
            this.f.onChanged(i2, 1, this.f.getChangePayload(t2, t));
        }
    }

    public void recalculatePositionOfItemAt(int i2) {
        b();
        Object obj = get(i2);
        a(i2, false);
        int a2 = a((T) obj, false);
        if (i2 != a2) {
            this.f.onMoved(i2, a2);
        }
    }

    public T get(int i2) {
        if (i2 >= this.h || i2 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Asked to get item at ");
            sb.append(i2);
            sb.append(" but size is ");
            sb.append(this.h);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (this.b == null || i2 < this.e) {
            return this.a[i2];
        } else {
            return this.b[(i2 - this.e) + this.c];
        }
    }

    public int indexOf(T t) {
        if (this.b != null) {
            int a2 = a(t, this.a, 0, this.e, 4);
            if (a2 != -1) {
                return a2;
            }
            int a3 = a(t, this.b, this.c, this.d, 4);
            if (a3 != -1) {
                return (a3 - this.c) + this.e;
            }
            return -1;
        }
        return a(t, this.a, 0, this.h, 4);
    }

    private int a(T t, T[] tArr, int i2, int i3, int i4) {
        while (i2 < i3) {
            int i5 = (i2 + i3) / 2;
            T t2 = tArr[i5];
            int compare = this.f.compare(t2, t);
            if (compare < 0) {
                i2 = i5 + 1;
            } else if (compare != 0) {
                i3 = i5;
            } else if (this.f.areItemsTheSame(t2, t)) {
                return i5;
            } else {
                int a2 = a(t, i5, i2, i3);
                if (i4 != 1) {
                    return a2;
                }
                if (a2 == -1) {
                    a2 = i5;
                }
                return a2;
            }
        }
        if (i4 != 1) {
            i2 = -1;
        }
        return i2;
    }

    private int a(T t, int i2, int i3, int i4) {
        T t2;
        int i5 = i2 - 1;
        while (i5 >= i3) {
            T t3 = this.a[i5];
            if (this.f.compare(t3, t) != 0) {
                break;
            } else if (this.f.areItemsTheSame(t3, t)) {
                return i5;
            } else {
                i5--;
            }
        }
        do {
            i2++;
            if (i2 < i4) {
                t2 = this.a[i2];
                if (this.f.compare(t2, t) != 0) {
                }
            }
            return -1;
        } while (!this.f.areItemsTheSame(t2, t));
        return i2;
    }

    private void a(int i2, T t) {
        if (i2 > this.h) {
            StringBuilder sb = new StringBuilder();
            sb.append("cannot add item to ");
            sb.append(i2);
            sb.append(" because size is ");
            sb.append(this.h);
            throw new IndexOutOfBoundsException(sb.toString());
        }
        if (this.h == this.a.length) {
            T[] tArr = (Object[]) Array.newInstance(this.i, this.a.length + 10);
            System.arraycopy(this.a, 0, tArr, 0, i2);
            tArr[i2] = t;
            System.arraycopy(this.a, i2, tArr, i2 + 1, this.h - i2);
            this.a = tArr;
        } else {
            System.arraycopy(this.a, i2, this.a, i2 + 1, this.h - i2);
            this.a[i2] = t;
        }
        this.h++;
    }

    private T[] d(T[] tArr) {
        T[] tArr2 = (Object[]) Array.newInstance(this.i, tArr.length);
        System.arraycopy(tArr, 0, tArr2, 0, tArr.length);
        return tArr2;
    }

    public void clear() {
        b();
        if (this.h != 0) {
            int i2 = this.h;
            Arrays.fill(this.a, 0, i2, null);
            this.h = 0;
            this.f.onRemoved(0, i2);
        }
    }
}
