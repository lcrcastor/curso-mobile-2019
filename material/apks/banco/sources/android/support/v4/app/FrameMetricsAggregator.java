package android.support.v4.app;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.SparseIntArray;
import android.view.FrameMetrics;
import android.view.Window;
import android.view.Window.OnFrameMetricsAvailableListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class FrameMetricsAggregator {
    public static final int ANIMATION_DURATION = 256;
    public static final int ANIMATION_INDEX = 8;
    public static final int COMMAND_DURATION = 32;
    public static final int COMMAND_INDEX = 5;
    public static final int DELAY_DURATION = 128;
    public static final int DELAY_INDEX = 7;
    public static final int DRAW_DURATION = 8;
    public static final int DRAW_INDEX = 3;
    public static final int EVERY_DURATION = 511;
    public static final int INPUT_DURATION = 2;
    public static final int INPUT_INDEX = 1;
    public static final int LAYOUT_MEASURE_DURATION = 4;
    public static final int LAYOUT_MEASURE_INDEX = 2;
    public static final int SWAP_DURATION = 64;
    public static final int SWAP_INDEX = 6;
    public static final int SYNC_DURATION = 16;
    public static final int SYNC_INDEX = 4;
    public static final int TOTAL_DURATION = 1;
    public static final int TOTAL_INDEX = 0;
    private FrameMetricsBaseImpl a;

    @RequiresApi(24)
    static class FrameMetricsApi24Impl extends FrameMetricsBaseImpl {
        private static HandlerThread e;
        private static Handler f;
        OnFrameMetricsAvailableListener a = new OnFrameMetricsAvailableListener() {
            public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int i) {
                if ((FrameMetricsApi24Impl.this.b & 1) != 0) {
                    FrameMetricsApi24Impl.this.a(FrameMetricsApi24Impl.this.c[0], frameMetrics.getMetric(8));
                }
                if ((FrameMetricsApi24Impl.this.b & 2) != 0) {
                    FrameMetricsApi24Impl.this.a(FrameMetricsApi24Impl.this.c[1], frameMetrics.getMetric(1));
                }
                if ((FrameMetricsApi24Impl.this.b & 4) != 0) {
                    FrameMetricsApi24Impl.this.a(FrameMetricsApi24Impl.this.c[2], frameMetrics.getMetric(3));
                }
                if ((FrameMetricsApi24Impl.this.b & 8) != 0) {
                    FrameMetricsApi24Impl.this.a(FrameMetricsApi24Impl.this.c[3], frameMetrics.getMetric(4));
                }
                if ((FrameMetricsApi24Impl.this.b & 16) != 0) {
                    FrameMetricsApi24Impl.this.a(FrameMetricsApi24Impl.this.c[4], frameMetrics.getMetric(5));
                }
                if ((FrameMetricsApi24Impl.this.b & 64) != 0) {
                    FrameMetricsApi24Impl.this.a(FrameMetricsApi24Impl.this.c[6], frameMetrics.getMetric(7));
                }
                if ((FrameMetricsApi24Impl.this.b & 32) != 0) {
                    FrameMetricsApi24Impl.this.a(FrameMetricsApi24Impl.this.c[5], frameMetrics.getMetric(6));
                }
                if ((FrameMetricsApi24Impl.this.b & 128) != 0) {
                    FrameMetricsApi24Impl.this.a(FrameMetricsApi24Impl.this.c[7], frameMetrics.getMetric(0));
                }
                if ((FrameMetricsApi24Impl.this.b & 256) != 0) {
                    FrameMetricsApi24Impl.this.a(FrameMetricsApi24Impl.this.c[8], frameMetrics.getMetric(2));
                }
            }
        };
        /* access modifiers changed from: private */
        public int b;
        /* access modifiers changed from: private */
        public SparseIntArray[] c = new SparseIntArray[9];
        private ArrayList<WeakReference<Activity>> d = new ArrayList<>();

        FrameMetricsApi24Impl(int i) {
            super();
            this.b = i;
        }

        /* access modifiers changed from: 0000 */
        public void a(SparseIntArray sparseIntArray, long j) {
            if (sparseIntArray != null) {
                int i = (int) ((j + 500000) / 1000000);
                if (j >= 0) {
                    sparseIntArray.put(i, sparseIntArray.get(i) + 1);
                }
            }
        }

        public void a(Activity activity) {
            if (e == null) {
                e = new HandlerThread("FrameMetricsAggregator");
                e.start();
                f = new Handler(e.getLooper());
            }
            for (int i = 0; i <= 8; i++) {
                if (this.c[i] == null && (this.b & (1 << i)) != 0) {
                    this.c[i] = new SparseIntArray();
                }
            }
            activity.getWindow().addOnFrameMetricsAvailableListener(this.a, f);
            this.d.add(new WeakReference(activity));
        }

        public SparseIntArray[] b(Activity activity) {
            Iterator it = this.d.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WeakReference weakReference = (WeakReference) it.next();
                if (weakReference.get() == activity) {
                    this.d.remove(weakReference);
                    break;
                }
            }
            activity.getWindow().removeOnFrameMetricsAvailableListener(this.a);
            return this.c;
        }

        public SparseIntArray[] a() {
            for (int size = this.d.size() - 1; size >= 0; size--) {
                WeakReference weakReference = (WeakReference) this.d.get(size);
                Activity activity = (Activity) weakReference.get();
                if (weakReference.get() != null) {
                    activity.getWindow().removeOnFrameMetricsAvailableListener(this.a);
                    this.d.remove(size);
                }
            }
            return this.c;
        }

        public SparseIntArray[] b() {
            return this.c;
        }

        public SparseIntArray[] c() {
            SparseIntArray[] sparseIntArrayArr = this.c;
            this.c = new SparseIntArray[9];
            return sparseIntArrayArr;
        }
    }

    static class FrameMetricsBaseImpl {
        public void a(Activity activity) {
        }

        public SparseIntArray[] a() {
            return null;
        }

        public SparseIntArray[] b() {
            return null;
        }

        public SparseIntArray[] b(Activity activity) {
            return null;
        }

        public SparseIntArray[] c() {
            return null;
        }

        private FrameMetricsBaseImpl() {
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MetricType {
    }

    public FrameMetricsAggregator() {
        this(1);
    }

    public FrameMetricsAggregator(int i) {
        if (VERSION.SDK_INT >= 24) {
            this.a = new FrameMetricsApi24Impl(i);
        } else {
            this.a = new FrameMetricsBaseImpl();
        }
    }

    public void add(@NonNull Activity activity) {
        this.a.a(activity);
    }

    @Nullable
    public SparseIntArray[] remove(@NonNull Activity activity) {
        return this.a.b(activity);
    }

    @Nullable
    public SparseIntArray[] stop() {
        return this.a.a();
    }

    @Nullable
    public SparseIntArray[] reset() {
        return this.a.c();
    }

    @Nullable
    public SparseIntArray[] getMetrics() {
        return this.a.b();
    }
}
