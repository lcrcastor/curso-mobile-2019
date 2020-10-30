package android.support.v7.widget;

import android.support.annotation.Nullable;
import android.support.v4.os.TraceCompat;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

final class GapWorker implements Runnable {
    static final ThreadLocal<GapWorker> a = new ThreadLocal<>();
    static Comparator<Task> e = new Comparator<Task>() {
        /* renamed from: a */
        public int compare(Task task, Task task2) {
            int i = 1;
            if ((task.d == null) != (task2.d == null)) {
                if (task.d != null) {
                    i = -1;
                }
                return i;
            } else if (task.a != task2.a) {
                if (task.a) {
                    i = -1;
                }
                return i;
            } else {
                int i2 = task2.b - task.b;
                if (i2 != 0) {
                    return i2;
                }
                int i3 = task.c - task2.c;
                if (i3 != 0) {
                    return i3;
                }
                return 0;
            }
        }
    };
    ArrayList<RecyclerView> b = new ArrayList<>();
    long c;
    long d;
    private ArrayList<Task> f = new ArrayList<>();

    static class LayoutPrefetchRegistryImpl implements LayoutPrefetchRegistry {
        int a;
        int b;
        int[] c;
        int d;

        LayoutPrefetchRegistryImpl() {
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        /* access modifiers changed from: 0000 */
        public void a(RecyclerView recyclerView, boolean z) {
            this.d = 0;
            if (this.c != null) {
                Arrays.fill(this.c, -1);
            }
            LayoutManager layoutManager = recyclerView.m;
            if (recyclerView.l != null && layoutManager != null && layoutManager.isItemPrefetchEnabled()) {
                if (z) {
                    if (!recyclerView.e.d()) {
                        layoutManager.collectInitialPrefetchPositions(recyclerView.l.getItemCount(), this);
                    }
                } else if (!recyclerView.hasPendingAdapterUpdates()) {
                    layoutManager.collectAdjacentPrefetchPositions(this.a, this.b, recyclerView.C, this);
                }
                if (this.d > layoutManager.x) {
                    layoutManager.x = this.d;
                    layoutManager.y = z;
                    recyclerView.d.a();
                }
            }
        }

        public void addPosition(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            } else if (i2 < 0) {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            } else {
                int i3 = this.d * 2;
                if (this.c == null) {
                    this.c = new int[4];
                    Arrays.fill(this.c, -1);
                } else if (i3 >= this.c.length) {
                    int[] iArr = this.c;
                    this.c = new int[(i3 * 2)];
                    System.arraycopy(iArr, 0, this.c, 0, iArr.length);
                }
                this.c[i3] = i;
                this.c[i3 + 1] = i2;
                this.d++;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(int i) {
            if (this.c != null) {
                int i2 = this.d * 2;
                for (int i3 = 0; i3 < i2; i3 += 2) {
                    if (this.c[i3] == i) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.c != null) {
                Arrays.fill(this.c, -1);
            }
            this.d = 0;
        }
    }

    static class Task {
        public boolean a;
        public int b;
        public int c;
        public RecyclerView d;
        public int e;

        Task() {
        }

        public void a() {
            this.a = false;
            this.b = 0;
            this.c = 0;
            this.d = null;
            this.e = 0;
        }
    }

    GapWorker() {
    }

    public void a(RecyclerView recyclerView) {
        this.b.add(recyclerView);
    }

    public void b(RecyclerView recyclerView) {
        this.b.remove(recyclerView);
    }

    /* access modifiers changed from: 0000 */
    public void a(RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.isAttachedToWindow() && this.c == 0) {
            this.c = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        recyclerView.B.a(i, i2);
    }

    private void a() {
        Task task;
        int size = this.b.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            RecyclerView recyclerView = (RecyclerView) this.b.get(i2);
            if (recyclerView.getWindowVisibility() == 0) {
                recyclerView.B.a(recyclerView, false);
                i += recyclerView.B.d;
            }
        }
        this.f.ensureCapacity(i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            RecyclerView recyclerView2 = (RecyclerView) this.b.get(i4);
            if (recyclerView2.getWindowVisibility() == 0) {
                LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView2.B;
                int abs = Math.abs(layoutPrefetchRegistryImpl.a) + Math.abs(layoutPrefetchRegistryImpl.b);
                int i5 = i3;
                for (int i6 = 0; i6 < layoutPrefetchRegistryImpl.d * 2; i6 += 2) {
                    if (i5 >= this.f.size()) {
                        task = new Task();
                        this.f.add(task);
                    } else {
                        task = (Task) this.f.get(i5);
                    }
                    int i7 = layoutPrefetchRegistryImpl.c[i6 + 1];
                    task.a = i7 <= abs;
                    task.b = abs;
                    task.c = i7;
                    task.d = recyclerView2;
                    task.e = layoutPrefetchRegistryImpl.c[i6];
                    i5++;
                }
                i3 = i5;
            }
        }
        Collections.sort(this.f, e);
    }

    static boolean a(RecyclerView recyclerView, int i) {
        int c2 = recyclerView.f.c();
        for (int i2 = 0; i2 < c2; i2++) {
            ViewHolder b2 = RecyclerView.b(recyclerView.f.d(i2));
            if (b2.b == i && !b2.j()) {
                return true;
            }
        }
        return false;
    }

    private ViewHolder a(RecyclerView recyclerView, int i, long j) {
        if (a(recyclerView, i)) {
            return null;
        }
        Recycler recycler = recyclerView.d;
        try {
            recyclerView.k();
            ViewHolder a2 = recycler.a(i, false, j);
            if (a2 != null) {
                if (!a2.l() || a2.j()) {
                    recycler.a(a2, false);
                } else {
                    recycler.recycleView(a2.itemView);
                }
            }
            return a2;
        } finally {
            recyclerView.b(false);
        }
    }

    private void a(@Nullable RecyclerView recyclerView, long j) {
        if (recyclerView != null) {
            if (recyclerView.w && recyclerView.f.c() != 0) {
                recyclerView.c();
            }
            LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.B;
            layoutPrefetchRegistryImpl.a(recyclerView, true);
            if (layoutPrefetchRegistryImpl.d != 0) {
                try {
                    TraceCompat.beginSection("RV Nested Prefetch");
                    recyclerView.C.a(recyclerView.l);
                    for (int i = 0; i < layoutPrefetchRegistryImpl.d * 2; i += 2) {
                        a(recyclerView, layoutPrefetchRegistryImpl.c[i], j);
                    }
                } finally {
                    TraceCompat.endSection();
                }
            }
        }
    }

    private void a(Task task, long j) {
        ViewHolder a2 = a(task.d, task.e, task.a ? Long.MAX_VALUE : j);
        if (a2 != null && a2.a != null && a2.l() && !a2.j()) {
            a((RecyclerView) a2.a.get(), j);
        }
    }

    private void b(long j) {
        int i = 0;
        while (i < this.f.size()) {
            Task task = (Task) this.f.get(i);
            if (task.d != null) {
                a(task, j);
                task.a();
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(long j) {
        a();
        b(j);
    }

    public void run() {
        try {
            TraceCompat.beginSection("RV Prefetch");
            if (!this.b.isEmpty()) {
                int size = this.b.size();
                long j = 0;
                for (int i = 0; i < size; i++) {
                    RecyclerView recyclerView = (RecyclerView) this.b.get(i);
                    if (recyclerView.getWindowVisibility() == 0) {
                        j = Math.max(recyclerView.getDrawingTime(), j);
                    }
                }
                if (j == 0) {
                    this.c = 0;
                    TraceCompat.endSection();
                    return;
                }
                a(TimeUnit.MILLISECONDS.toNanos(j) + this.d);
                this.c = 0;
                TraceCompat.endSection();
            }
        } finally {
            this.c = 0;
            TraceCompat.endSection();
        }
    }
}
