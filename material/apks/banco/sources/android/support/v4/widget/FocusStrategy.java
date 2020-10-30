package android.support.v4.widget;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class FocusStrategy {

    public interface BoundsAdapter<T> {
        void obtainBounds(T t, Rect rect);
    }

    public interface CollectionAdapter<T, V> {
        V get(T t, int i);

        int size(T t);
    }

    static class SequentialComparator<T> implements Comparator<T> {
        private final Rect a = new Rect();
        private final Rect b = new Rect();
        private final boolean c;
        private final BoundsAdapter<T> d;

        SequentialComparator(boolean z, BoundsAdapter<T> boundsAdapter) {
            this.c = z;
            this.d = boundsAdapter;
        }

        public int compare(T t, T t2) {
            Rect rect = this.a;
            Rect rect2 = this.b;
            this.d.obtainBounds(t, rect);
            this.d.obtainBounds(t2, rect2);
            int i = -1;
            if (rect.top < rect2.top) {
                return -1;
            }
            if (rect.top > rect2.top) {
                return 1;
            }
            if (rect.left < rect2.left) {
                if (this.c) {
                    i = 1;
                }
                return i;
            } else if (rect.left > rect2.left) {
                if (!this.c) {
                    i = 1;
                }
                return i;
            } else if (rect.bottom < rect2.bottom) {
                return -1;
            } else {
                if (rect.bottom > rect2.bottom) {
                    return 1;
                }
                if (rect.right < rect2.right) {
                    if (this.c) {
                        i = 1;
                    }
                    return i;
                } else if (rect.right <= rect2.right) {
                    return 0;
                } else {
                    if (!this.c) {
                        i = 1;
                    }
                    return i;
                }
            }
        }
    }

    private static int a(int i, int i2) {
        return (i * 13 * i) + (i2 * i2);
    }

    FocusStrategy() {
    }

    public static <L, T> T a(@NonNull L l, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> boundsAdapter, @Nullable T t, int i, boolean z, boolean z2) {
        int size = collectionAdapter.size(l);
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(collectionAdapter.get(l, i2));
        }
        Collections.sort(arrayList, new SequentialComparator(z, boundsAdapter));
        switch (i) {
            case 1:
                return b(t, arrayList, z2);
            case 2:
                return a(t, arrayList, z2);
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
    }

    private static <T> T a(T t, ArrayList<T> arrayList, boolean z) {
        int i;
        int size = arrayList.size();
        if (t == null) {
            i = -1;
        } else {
            i = arrayList.lastIndexOf(t);
        }
        int i2 = i + 1;
        if (i2 < size) {
            return arrayList.get(i2);
        }
        if (!z || size <= 0) {
            return null;
        }
        return arrayList.get(0);
    }

    private static <T> T b(T t, ArrayList<T> arrayList, boolean z) {
        int i;
        int size = arrayList.size();
        if (t == null) {
            i = size;
        } else {
            i = arrayList.indexOf(t);
        }
        int i2 = i - 1;
        if (i2 >= 0) {
            return arrayList.get(i2);
        }
        if (!z || size <= 0) {
            return null;
        }
        return arrayList.get(size - 1);
    }

    public static <L, T> T a(@NonNull L l, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> boundsAdapter, @Nullable T t, @NonNull Rect rect, int i) {
        Rect rect2 = new Rect(rect);
        if (i == 17) {
            rect2.offset(rect.width() + 1, 0);
        } else if (i == 33) {
            rect2.offset(0, rect.height() + 1);
        } else if (i == 66) {
            rect2.offset(-(rect.width() + 1), 0);
        } else if (i != 130) {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        } else {
            rect2.offset(0, -(rect.height() + 1));
        }
        T t2 = null;
        int size = collectionAdapter.size(l);
        Rect rect3 = new Rect();
        for (int i2 = 0; i2 < size; i2++) {
            T t3 = collectionAdapter.get(l, i2);
            if (t3 != t) {
                boundsAdapter.obtainBounds(t3, rect3);
                if (a(i, rect, rect3, rect2)) {
                    rect2.set(rect3);
                    t2 = t3;
                }
            }
        }
        return t2;
    }

    private static boolean a(int i, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        boolean z = false;
        if (!a(rect, rect2, i)) {
            return false;
        }
        if (!a(rect, rect3, i) || b(i, rect, rect2, rect3)) {
            return true;
        }
        if (b(i, rect, rect3, rect2)) {
            return false;
        }
        if (a(c(i, rect, rect2), g(i, rect, rect2)) < a(c(i, rect, rect3), g(i, rect, rect3))) {
            z = true;
        }
        return z;
    }

    private static boolean b(int i, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        boolean a = a(i, rect, rect2);
        if (a(i, rect, rect3) || !a) {
            return false;
        }
        boolean z = true;
        if (!b(i, rect, rect3) || i == 17 || i == 66) {
            return true;
        }
        if (c(i, rect, rect2) >= e(i, rect, rect3)) {
            z = false;
        }
        return z;
    }

    private static boolean a(@NonNull Rect rect, @NonNull Rect rect2, int i) {
        boolean z = false;
        if (i == 17) {
            if ((rect.right > rect2.right || rect.left >= rect2.right) && rect.left > rect2.left) {
                z = true;
            }
            return z;
        } else if (i == 33) {
            if ((rect.bottom > rect2.bottom || rect.top >= rect2.bottom) && rect.top > rect2.top) {
                z = true;
            }
            return z;
        } else if (i == 66) {
            if ((rect.left < rect2.left || rect.right <= rect2.left) && rect.right < rect2.right) {
                z = true;
            }
            return z;
        } else if (i != 130) {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        } else {
            if ((rect.top < rect2.top || rect.bottom <= rect2.top) && rect.bottom < rect2.bottom) {
                z = true;
            }
            return z;
        }
    }

    private static boolean a(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        boolean z = false;
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            if (rect2.right >= rect.left && rect2.left <= rect.right) {
                z = true;
            }
            return z;
        }
        if (rect2.bottom >= rect.top && rect2.top <= rect.bottom) {
            z = true;
        }
        return z;
    }

    private static boolean b(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        boolean z = false;
        if (i == 17) {
            if (rect.left >= rect2.right) {
                z = true;
            }
            return z;
        } else if (i == 33) {
            if (rect.top >= rect2.bottom) {
                z = true;
            }
            return z;
        } else if (i == 66) {
            if (rect.right <= rect2.left) {
                z = true;
            }
            return z;
        } else if (i != 130) {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        } else {
            if (rect.bottom <= rect2.top) {
                z = true;
            }
            return z;
        }
    }

    private static int c(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        return Math.max(0, d(i, rect, rect2));
    }

    private static int d(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        if (i == 17) {
            return rect.left - rect2.right;
        }
        if (i == 33) {
            return rect.top - rect2.bottom;
        }
        if (i == 66) {
            return rect2.left - rect.right;
        }
        if (i == 130) {
            return rect2.top - rect.bottom;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    private static int e(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        return Math.max(1, f(i, rect, rect2));
    }

    private static int f(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        if (i == 17) {
            return rect.left - rect2.left;
        }
        if (i == 33) {
            return rect.top - rect2.top;
        }
        if (i == 66) {
            return rect2.right - rect.right;
        }
        if (i == 130) {
            return rect2.bottom - rect.bottom;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    private static int g(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return Math.abs((rect.left + (rect.width() / 2)) - (rect2.left + (rect2.width() / 2)));
        }
        return Math.abs((rect.top + (rect.height() / 2)) - (rect2.top + (rect2.height() / 2)));
    }
}
