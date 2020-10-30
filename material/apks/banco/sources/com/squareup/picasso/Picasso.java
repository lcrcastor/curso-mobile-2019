package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.internal.view.SupportMenu;
import android.widget.ImageView;
import android.widget.RemoteViews;
import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

public class Picasso {
    static final Handler a = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 3) {
                int i2 = 0;
                if (i == 8) {
                    List list = (List) message.obj;
                    int size = list.size();
                    while (i2 < size) {
                        BitmapHunter bitmapHunter = (BitmapHunter) list.get(i2);
                        bitmapHunter.b.a(bitmapHunter);
                        i2++;
                    }
                } else if (i != 13) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unknown handler message received: ");
                    sb.append(message.what);
                    throw new AssertionError(sb.toString());
                } else {
                    List list2 = (List) message.obj;
                    int size2 = list2.size();
                    while (i2 < size2) {
                        Action action = (Action) list2.get(i2);
                        action.a.c(action);
                        i2++;
                    }
                }
            } else {
                Action action2 = (Action) message.obj;
                if (action2.j().l) {
                    Utils.a("Main", "canceled", action2.b.a(), "target got garbage collected");
                }
                action2.a.a(action2.d());
            }
        }
    };
    static volatile Picasso b;
    final Context c;
    final Dispatcher d;
    final Cache e;
    final Stats f;
    final Map<Object, Action> g;
    final Map<ImageView, DeferredRequestCreator> h;
    final ReferenceQueue<Object> i;
    final Config j;
    boolean k;
    volatile boolean l;
    boolean m;
    private final Listener n;
    private final RequestTransformer o;
    private final CleanupThread p;
    private final List<RequestHandler> q;

    public static class Builder {
        private final Context a;
        private Downloader b;
        private ExecutorService c;
        private Cache d;
        private Listener e;
        private RequestTransformer f;
        private List<RequestHandler> g;
        private Config h;
        private boolean i;
        private boolean j;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.a = context.getApplicationContext();
        }

        public Builder defaultBitmapConfig(Config config) {
            if (config == null) {
                throw new IllegalArgumentException("Bitmap config must not be null.");
            }
            this.h = config;
            return this;
        }

        public Builder downloader(Downloader downloader) {
            if (downloader == null) {
                throw new IllegalArgumentException("Downloader must not be null.");
            } else if (this.b != null) {
                throw new IllegalStateException("Downloader already set.");
            } else {
                this.b = downloader;
                return this;
            }
        }

        public Builder executor(ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("Executor service must not be null.");
            } else if (this.c != null) {
                throw new IllegalStateException("Executor service already set.");
            } else {
                this.c = executorService;
                return this;
            }
        }

        public Builder memoryCache(Cache cache) {
            if (cache == null) {
                throw new IllegalArgumentException("Memory cache must not be null.");
            } else if (this.d != null) {
                throw new IllegalStateException("Memory cache already set.");
            } else {
                this.d = cache;
                return this;
            }
        }

        public Builder listener(Listener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            } else if (this.e != null) {
                throw new IllegalStateException("Listener already set.");
            } else {
                this.e = listener;
                return this;
            }
        }

        public Builder requestTransformer(RequestTransformer requestTransformer) {
            if (requestTransformer == null) {
                throw new IllegalArgumentException("Transformer must not be null.");
            } else if (this.f != null) {
                throw new IllegalStateException("Transformer already set.");
            } else {
                this.f = requestTransformer;
                return this;
            }
        }

        public Builder addRequestHandler(RequestHandler requestHandler) {
            if (requestHandler == null) {
                throw new IllegalArgumentException("RequestHandler must not be null.");
            }
            if (this.g == null) {
                this.g = new ArrayList();
            }
            if (this.g.contains(requestHandler)) {
                throw new IllegalStateException("RequestHandler already registered.");
            }
            this.g.add(requestHandler);
            return this;
        }

        @Deprecated
        public Builder debugging(boolean z) {
            return indicatorsEnabled(z);
        }

        public Builder indicatorsEnabled(boolean z) {
            this.i = z;
            return this;
        }

        public Builder loggingEnabled(boolean z) {
            this.j = z;
            return this;
        }

        public Picasso build() {
            Context context = this.a;
            if (this.b == null) {
                this.b = Utils.a(context);
            }
            if (this.d == null) {
                this.d = new LruCache(context);
            }
            if (this.c == null) {
                this.c = new PicassoExecutorService();
            }
            if (this.f == null) {
                this.f = RequestTransformer.IDENTITY;
            }
            Stats stats = new Stats(this.d);
            Context context2 = context;
            Dispatcher dispatcher = new Dispatcher(context2, this.c, Picasso.a, this.b, this.d, stats);
            Picasso picasso = new Picasso(context2, dispatcher, this.d, this.e, this.f, this.g, stats, this.h, this.i, this.j);
            return picasso;
        }
    }

    static class CleanupThread extends Thread {
        private final ReferenceQueue<Object> a;
        private final Handler b;

        CleanupThread(ReferenceQueue<Object> referenceQueue, Handler handler) {
            this.a = referenceQueue;
            this.b = handler;
            setDaemon(true);
            setName("Picasso-refQueue");
        }

        public void run() {
            Process.setThreadPriority(10);
            while (true) {
                try {
                    RequestWeakReference requestWeakReference = (RequestWeakReference) this.a.remove(1000);
                    Message obtainMessage = this.b.obtainMessage();
                    if (requestWeakReference != null) {
                        obtainMessage.what = 3;
                        obtainMessage.obj = requestWeakReference.a;
                        this.b.sendMessage(obtainMessage);
                    } else {
                        obtainMessage.recycle();
                    }
                } catch (InterruptedException unused) {
                    return;
                } catch (Exception e) {
                    this.b.post(new Runnable() {
                        public void run() {
                            throw new RuntimeException(e);
                        }
                    });
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            interrupt();
        }
    }

    public interface Listener {
        void onImageLoadFailed(Picasso picasso, Uri uri, Exception exc);
    }

    public enum LoadedFrom {
        MEMORY(-16711936),
        DISK(-16776961),
        NETWORK(SupportMenu.CATEGORY_MASK);
        
        final int a;

        private LoadedFrom(int i) {
            this.a = i;
        }
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH
    }

    public interface RequestTransformer {
        public static final RequestTransformer IDENTITY = new RequestTransformer() {
            public Request transformRequest(Request request) {
                return request;
            }
        };

        Request transformRequest(Request request);
    }

    Picasso(Context context, Dispatcher dispatcher, Cache cache, Listener listener, RequestTransformer requestTransformer, List<RequestHandler> list, Stats stats, Config config, boolean z, boolean z2) {
        this.c = context;
        this.d = dispatcher;
        this.e = cache;
        this.n = listener;
        this.o = requestTransformer;
        this.j = config;
        ArrayList arrayList = new ArrayList((list != null ? list.size() : 0) + 7);
        arrayList.add(new ResourceRequestHandler(context));
        if (list != null) {
            arrayList.addAll(list);
        }
        arrayList.add(new ContactsPhotoRequestHandler(context));
        arrayList.add(new MediaStoreRequestHandler(context));
        arrayList.add(new ContentStreamRequestHandler(context));
        arrayList.add(new AssetRequestHandler(context));
        arrayList.add(new FileRequestHandler(context));
        arrayList.add(new NetworkRequestHandler(dispatcher.d, stats));
        this.q = Collections.unmodifiableList(arrayList);
        this.f = stats;
        this.g = new WeakHashMap();
        this.h = new WeakHashMap();
        this.k = z;
        this.l = z2;
        this.i = new ReferenceQueue<>();
        this.p = new CleanupThread(this.i, a);
        this.p.start();
    }

    public void cancelRequest(ImageView imageView) {
        a((Object) imageView);
    }

    public void cancelRequest(Target target) {
        a((Object) target);
    }

    public void cancelRequest(RemoteViews remoteViews, int i2) {
        a((Object) new RemoteViewsTarget(remoteViews, i2));
    }

    public void cancelTag(Object obj) {
        Utils.b();
        ArrayList arrayList = new ArrayList(this.g.values());
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Action action = (Action) arrayList.get(i2);
            if (action.l().equals(obj)) {
                a(action.d());
            }
        }
    }

    public void pauseTag(Object obj) {
        this.d.a(obj);
    }

    public void resumeTag(Object obj) {
        this.d.b(obj);
    }

    public RequestCreator load(Uri uri) {
        return new RequestCreator(this, uri, 0);
    }

    public RequestCreator load(String str) {
        if (str == null) {
            return new RequestCreator(this, null, 0);
        }
        if (str.trim().length() != 0) {
            return load(Uri.parse(str));
        }
        throw new IllegalArgumentException("Path must not be empty.");
    }

    public RequestCreator load(File file) {
        if (file == null) {
            return new RequestCreator(this, null, 0);
        }
        return load(Uri.fromFile(file));
    }

    public RequestCreator load(int i2) {
        if (i2 != 0) {
            return new RequestCreator(this, null, i2);
        }
        throw new IllegalArgumentException("Resource ID must not be zero.");
    }

    public void invalidate(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri == null");
        }
        this.e.clearKeyUri(uri.toString());
    }

    public void invalidate(String str) {
        if (str == null) {
            throw new IllegalArgumentException("path == null");
        }
        invalidate(Uri.parse(str));
    }

    public void invalidate(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        invalidate(Uri.fromFile(file));
    }

    @Deprecated
    public boolean isDebugging() {
        return areIndicatorsEnabled() && isLoggingEnabled();
    }

    @Deprecated
    public void setDebugging(boolean z) {
        setIndicatorsEnabled(z);
    }

    public void setIndicatorsEnabled(boolean z) {
        this.k = z;
    }

    public boolean areIndicatorsEnabled() {
        return this.k;
    }

    public void setLoggingEnabled(boolean z) {
        this.l = z;
    }

    public boolean isLoggingEnabled() {
        return this.l;
    }

    public StatsSnapshot getSnapshot() {
        return this.f.f();
    }

    public void shutdown() {
        if (this == b) {
            throw new UnsupportedOperationException("Default singleton instance cannot be shutdown.");
        } else if (!this.m) {
            this.e.clear();
            this.p.a();
            this.f.c();
            this.d.a();
            for (DeferredRequestCreator a2 : this.h.values()) {
                a2.a();
            }
            this.h.clear();
            this.m = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public List<RequestHandler> a() {
        return this.q;
    }

    /* access modifiers changed from: 0000 */
    public Request a(Request request) {
        Request transformRequest = this.o.transformRequest(request);
        if (transformRequest != null) {
            return transformRequest;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Request transformer ");
        sb.append(this.o.getClass().getCanonicalName());
        sb.append(" returned null for ");
        sb.append(request);
        throw new IllegalStateException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public void a(ImageView imageView, DeferredRequestCreator deferredRequestCreator) {
        this.h.put(imageView, deferredRequestCreator);
    }

    /* access modifiers changed from: 0000 */
    public void a(Action action) {
        Object d2 = action.d();
        if (!(d2 == null || this.g.get(d2) == action)) {
            a(d2);
            this.g.put(d2, action);
        }
        b(action);
    }

    /* access modifiers changed from: 0000 */
    public void b(Action action) {
        this.d.a(action);
    }

    /* access modifiers changed from: 0000 */
    public Bitmap a(String str) {
        Bitmap bitmap = this.e.get(str);
        if (bitmap != null) {
            this.f.a();
        } else {
            this.f.b();
        }
        return bitmap;
    }

    /* access modifiers changed from: 0000 */
    public void a(BitmapHunter bitmapHunter) {
        Action i2 = bitmapHunter.i();
        List k2 = bitmapHunter.k();
        boolean z = true;
        boolean z2 = k2 != null && !k2.isEmpty();
        if (i2 == null && !z2) {
            z = false;
        }
        if (z) {
            Uri uri = bitmapHunter.h().uri;
            Exception l2 = bitmapHunter.l();
            Bitmap e2 = bitmapHunter.e();
            LoadedFrom m2 = bitmapHunter.m();
            if (i2 != null) {
                a(e2, m2, i2);
            }
            if (z2) {
                int size = k2.size();
                for (int i3 = 0; i3 < size; i3++) {
                    a(e2, m2, (Action) k2.get(i3));
                }
            }
            if (!(this.n == null || l2 == null)) {
                this.n.onImageLoadFailed(this, uri, l2);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(Action action) {
        Bitmap a2 = MemoryPolicy.a(action.e) ? a(action.e()) : null;
        if (a2 != null) {
            a(a2, LoadedFrom.MEMORY, action);
            if (this.l) {
                String a3 = action.b.a();
                StringBuilder sb = new StringBuilder();
                sb.append("from ");
                sb.append(LoadedFrom.MEMORY);
                Utils.a("Main", "completed", a3, sb.toString());
                return;
            }
            return;
        }
        a(action);
        if (this.l) {
            Utils.a("Main", "resumed", action.b.a());
        }
    }

    private void a(Bitmap bitmap, LoadedFrom loadedFrom, Action action) {
        if (!action.f()) {
            if (!action.g()) {
                this.g.remove(action.d());
            }
            if (bitmap == null) {
                action.a();
                if (this.l) {
                    Utils.a("Main", "errored", action.b.a());
                }
            } else if (loadedFrom == null) {
                throw new AssertionError("LoadedFrom cannot be null.");
            } else {
                action.a(bitmap, loadedFrom);
                if (this.l) {
                    String a2 = action.b.a();
                    StringBuilder sb = new StringBuilder();
                    sb.append("from ");
                    sb.append(loadedFrom);
                    Utils.a("Main", "completed", a2, sb.toString());
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(Object obj) {
        Utils.b();
        Action action = (Action) this.g.remove(obj);
        if (action != null) {
            action.b();
            this.d.b(action);
        }
        if (obj instanceof ImageView) {
            DeferredRequestCreator deferredRequestCreator = (DeferredRequestCreator) this.h.remove((ImageView) obj);
            if (deferredRequestCreator != null) {
                deferredRequestCreator.a();
            }
        }
    }

    public static Picasso with(Context context) {
        if (b == null) {
            synchronized (Picasso.class) {
                if (b == null) {
                    b = new Builder(context).build();
                }
            }
        }
        return b;
    }

    public static void setSingletonInstance(Picasso picasso) {
        synchronized (Picasso.class) {
            if (b != null) {
                throw new IllegalStateException("Singleton instance already exists.");
            }
            b = picasso;
        }
    }
}
