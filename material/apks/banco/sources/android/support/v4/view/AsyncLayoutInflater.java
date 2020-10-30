package android.support.v4.view;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.util.Pools.SynchronizedPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.concurrent.ArrayBlockingQueue;

public final class AsyncLayoutInflater {
    LayoutInflater a;
    Handler b;
    InflateThread c;
    private Callback d = new Callback() {
        public boolean handleMessage(Message message) {
            InflateRequest inflateRequest = (InflateRequest) message.obj;
            if (inflateRequest.d == null) {
                inflateRequest.d = AsyncLayoutInflater.this.a.inflate(inflateRequest.c, inflateRequest.b, false);
            }
            inflateRequest.e.onInflateFinished(inflateRequest.d, inflateRequest.c, inflateRequest.b);
            AsyncLayoutInflater.this.c.a(inflateRequest);
            return true;
        }
    };

    static class BasicInflater extends LayoutInflater {
        private static final String[] a = {"android.widget.", "android.webkit.", "android.app."};

        BasicInflater(Context context) {
            super(context);
        }

        public LayoutInflater cloneInContext(Context context) {
            return new BasicInflater(context);
        }

        /* access modifiers changed from: protected */
        public View onCreateView(String str, AttributeSet attributeSet) {
            String[] strArr = a;
            int length = strArr.length;
            int i = 0;
            while (i < length) {
                try {
                    View createView = createView(str, strArr[i], attributeSet);
                    if (createView != null) {
                        return createView;
                    }
                    i++;
                } catch (ClassNotFoundException unused) {
                }
            }
            return super.onCreateView(str, attributeSet);
        }
    }

    static class InflateRequest {
        AsyncLayoutInflater a;
        ViewGroup b;
        int c;
        View d;
        OnInflateFinishedListener e;

        InflateRequest() {
        }
    }

    static class InflateThread extends Thread {
        private static final InflateThread a = new InflateThread();
        private ArrayBlockingQueue<InflateRequest> b = new ArrayBlockingQueue<>(10);
        private SynchronizedPool<InflateRequest> c = new SynchronizedPool<>(10);

        private InflateThread() {
        }

        static {
            a.start();
        }

        public static InflateThread a() {
            return a;
        }

        public void b() {
            try {
                InflateRequest inflateRequest = (InflateRequest) this.b.take();
                try {
                    inflateRequest.d = inflateRequest.a.a.inflate(inflateRequest.c, inflateRequest.b, false);
                } catch (RuntimeException e) {
                    Log.w("AsyncLayoutInflater", "Failed to inflate resource in the background! Retrying on the UI thread", e);
                }
                Message.obtain(inflateRequest.a.b, 0, inflateRequest).sendToTarget();
            } catch (InterruptedException e2) {
                Log.w("AsyncLayoutInflater", e2);
            }
        }

        public void run() {
            while (true) {
                b();
            }
        }

        public InflateRequest c() {
            InflateRequest inflateRequest = (InflateRequest) this.c.acquire();
            return inflateRequest == null ? new InflateRequest() : inflateRequest;
        }

        public void a(InflateRequest inflateRequest) {
            inflateRequest.e = null;
            inflateRequest.a = null;
            inflateRequest.b = null;
            inflateRequest.c = 0;
            inflateRequest.d = null;
            this.c.release(inflateRequest);
        }

        public void b(InflateRequest inflateRequest) {
            try {
                this.b.put(inflateRequest);
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to enqueue async inflate request", e);
            }
        }
    }

    public interface OnInflateFinishedListener {
        void onInflateFinished(@NonNull View view, @LayoutRes int i, @Nullable ViewGroup viewGroup);
    }

    public AsyncLayoutInflater(@NonNull Context context) {
        this.a = new BasicInflater(context);
        this.b = new Handler(this.d);
        this.c = InflateThread.a();
    }

    @UiThread
    public void inflate(@LayoutRes int i, @Nullable ViewGroup viewGroup, @NonNull OnInflateFinishedListener onInflateFinishedListener) {
        if (onInflateFinishedListener == null) {
            throw new NullPointerException("callback argument may not be null!");
        }
        InflateRequest c2 = this.c.c();
        c2.a = this;
        c2.c = i;
        c2.b = viewGroup;
        c2.e = onInflateFinishedListener;
        this.c.b(c2);
    }
}
