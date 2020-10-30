package com.squareup.picasso;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

class Dispatcher {
    final DispatcherThread a = new DispatcherThread();
    final Context b;
    final ExecutorService c;
    final Downloader d;
    final Map<String, BitmapHunter> e;
    final Map<Object, Action> f;
    final Map<Object, Action> g;
    final Set<Object> h;
    final Handler i;
    final Handler j;
    final Cache k;
    final Stats l;
    final List<BitmapHunter> m;
    final NetworkBroadcastReceiver n;
    final boolean o;
    boolean p;

    static class DispatcherHandler extends Handler {
        private final Dispatcher a;

        public DispatcherHandler(Looper looper, Dispatcher dispatcher) {
            super(looper);
            this.a = dispatcher;
        }

        public void handleMessage(final Message message) {
            boolean z = false;
            switch (message.what) {
                case 1:
                    this.a.c((Action) message.obj);
                    return;
                case 2:
                    this.a.d((Action) message.obj);
                    return;
                case 4:
                    this.a.e((BitmapHunter) message.obj);
                    return;
                case 5:
                    this.a.d((BitmapHunter) message.obj);
                    return;
                case 6:
                    this.a.a((BitmapHunter) message.obj, false);
                    return;
                case 7:
                    this.a.b();
                    return;
                case 9:
                    this.a.b((NetworkInfo) message.obj);
                    return;
                case 10:
                    Dispatcher dispatcher = this.a;
                    if (message.arg1 == 1) {
                        z = true;
                    }
                    dispatcher.b(z);
                    return;
                case 11:
                    this.a.c(message.obj);
                    return;
                case 12:
                    this.a.d(message.obj);
                    return;
                default:
                    Picasso.a.post(new Runnable() {
                        public void run() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unknown handler message received: ");
                            sb.append(message.what);
                            throw new AssertionError(sb.toString());
                        }
                    });
                    return;
            }
        }
    }

    static class DispatcherThread extends HandlerThread {
        DispatcherThread() {
            super("Picasso-Dispatcher", 10);
        }
    }

    static class NetworkBroadcastReceiver extends BroadcastReceiver {
        private final Dispatcher a;

        NetworkBroadcastReceiver(Dispatcher dispatcher) {
            this.a = dispatcher;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            if (this.a.o) {
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
            this.a.b.registerReceiver(this, intentFilter);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.a.b.unregisterReceiver(this);
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                    if (intent.hasExtra("state")) {
                        this.a.a(intent.getBooleanExtra("state", false));
                    }
                } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                    this.a.a(((ConnectivityManager) Utils.a(context, "connectivity")).getActiveNetworkInfo());
                }
            }
        }
    }

    Dispatcher(Context context, ExecutorService executorService, Handler handler, Downloader downloader, Cache cache, Stats stats) {
        this.a.start();
        Utils.a(this.a.getLooper());
        this.b = context;
        this.c = executorService;
        this.e = new LinkedHashMap();
        this.f = new WeakHashMap();
        this.g = new WeakHashMap();
        this.h = new HashSet();
        this.i = new DispatcherHandler(this.a.getLooper(), this);
        this.d = downloader;
        this.j = handler;
        this.k = cache;
        this.l = stats;
        this.m = new ArrayList(4);
        this.p = Utils.d(this.b);
        this.o = Utils.b(context, "android.permission.ACCESS_NETWORK_STATE");
        this.n = new NetworkBroadcastReceiver(this);
        this.n.a();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.c instanceof PicassoExecutorService) {
            this.c.shutdown();
        }
        this.d.shutdown();
        this.a.quit();
        Picasso.a.post(new Runnable() {
            public void run() {
                Dispatcher.this.n.b();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void a(Action action) {
        this.i.sendMessage(this.i.obtainMessage(1, action));
    }

    /* access modifiers changed from: 0000 */
    public void b(Action action) {
        this.i.sendMessage(this.i.obtainMessage(2, action));
    }

    /* access modifiers changed from: 0000 */
    public void a(Object obj) {
        this.i.sendMessage(this.i.obtainMessage(11, obj));
    }

    /* access modifiers changed from: 0000 */
    public void b(Object obj) {
        this.i.sendMessage(this.i.obtainMessage(12, obj));
    }

    /* access modifiers changed from: 0000 */
    public void a(BitmapHunter bitmapHunter) {
        this.i.sendMessage(this.i.obtainMessage(4, bitmapHunter));
    }

    /* access modifiers changed from: 0000 */
    public void b(BitmapHunter bitmapHunter) {
        this.i.sendMessageDelayed(this.i.obtainMessage(5, bitmapHunter), 500);
    }

    /* access modifiers changed from: 0000 */
    public void c(BitmapHunter bitmapHunter) {
        this.i.sendMessage(this.i.obtainMessage(6, bitmapHunter));
    }

    /* access modifiers changed from: 0000 */
    public void a(NetworkInfo networkInfo) {
        this.i.sendMessage(this.i.obtainMessage(9, networkInfo));
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        this.i.sendMessage(this.i.obtainMessage(10, z ? 1 : 0, 0));
    }

    /* access modifiers changed from: 0000 */
    public void c(Action action) {
        a(action, true);
    }

    /* access modifiers changed from: 0000 */
    public void a(Action action, boolean z) {
        if (this.h.contains(action.l())) {
            this.g.put(action.d(), action);
            if (action.j().l) {
                String a2 = action.b.a();
                StringBuilder sb = new StringBuilder();
                sb.append("because tag '");
                sb.append(action.l());
                sb.append("' is paused");
                Utils.a("Dispatcher", "paused", a2, sb.toString());
            }
            return;
        }
        BitmapHunter bitmapHunter = (BitmapHunter) this.e.get(action.e());
        if (bitmapHunter != null) {
            bitmapHunter.a(action);
        } else if (this.c.isShutdown()) {
            if (action.j().l) {
                Utils.a("Dispatcher", "ignored", action.b.a(), "because shut down");
            }
        } else {
            BitmapHunter a3 = BitmapHunter.a(action.j(), this, this.k, this.l, action);
            a3.n = this.c.submit(a3);
            this.e.put(action.e(), a3);
            if (z) {
                this.f.remove(action.d());
            }
            if (action.j().l) {
                Utils.a("Dispatcher", "enqueued", action.b.a());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void d(Action action) {
        String e2 = action.e();
        BitmapHunter bitmapHunter = (BitmapHunter) this.e.get(e2);
        if (bitmapHunter != null) {
            bitmapHunter.b(action);
            if (bitmapHunter.b()) {
                this.e.remove(e2);
                if (action.j().l) {
                    Utils.a("Dispatcher", "canceled", action.c().a());
                }
            }
        }
        if (this.h.contains(action.l())) {
            this.g.remove(action.d());
            if (action.j().l) {
                Utils.a("Dispatcher", "canceled", action.c().a(), "because paused request got canceled");
            }
        }
        Action action2 = (Action) this.f.remove(action.d());
        if (action2 != null && action2.j().l) {
            Utils.a("Dispatcher", "canceled", action2.c().a(), "from replaying");
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(Object obj) {
        if (this.h.add(obj)) {
            Iterator it = this.e.values().iterator();
            while (it.hasNext()) {
                BitmapHunter bitmapHunter = (BitmapHunter) it.next();
                boolean z = bitmapHunter.j().l;
                Action i2 = bitmapHunter.i();
                List k2 = bitmapHunter.k();
                boolean z2 = k2 != null && !k2.isEmpty();
                if (i2 != null || z2) {
                    if (i2 != null && i2.l().equals(obj)) {
                        bitmapHunter.b(i2);
                        this.g.put(i2.d(), i2);
                        if (z) {
                            String a2 = i2.b.a();
                            StringBuilder sb = new StringBuilder();
                            sb.append("because tag '");
                            sb.append(obj);
                            sb.append("' was paused");
                            Utils.a("Dispatcher", "paused", a2, sb.toString());
                        }
                    }
                    if (z2) {
                        for (int size = k2.size() - 1; size >= 0; size--) {
                            Action action = (Action) k2.get(size);
                            if (action.l().equals(obj)) {
                                bitmapHunter.b(action);
                                this.g.put(action.d(), action);
                                if (z) {
                                    String a3 = action.b.a();
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("because tag '");
                                    sb2.append(obj);
                                    sb2.append("' was paused");
                                    Utils.a("Dispatcher", "paused", a3, sb2.toString());
                                }
                            }
                        }
                    }
                    if (bitmapHunter.b()) {
                        it.remove();
                        if (z) {
                            Utils.a("Dispatcher", "canceled", Utils.a(bitmapHunter), "all actions paused");
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void d(Object obj) {
        if (this.h.remove(obj)) {
            ArrayList arrayList = null;
            Iterator it = this.g.values().iterator();
            while (it.hasNext()) {
                Action action = (Action) it.next();
                if (action.l().equals(obj)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(action);
                    it.remove();
                }
            }
            if (arrayList != null) {
                this.j.sendMessage(this.j.obtainMessage(13, arrayList));
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void d(BitmapHunter bitmapHunter) {
        if (!bitmapHunter.c()) {
            boolean z = false;
            if (this.c.isShutdown()) {
                a(bitmapHunter, false);
                return;
            }
            NetworkInfo networkInfo = null;
            if (this.o) {
                networkInfo = ((ConnectivityManager) Utils.a(this.b, "connectivity")).getActiveNetworkInfo();
            }
            boolean z2 = networkInfo != null && networkInfo.isConnected();
            boolean a2 = bitmapHunter.a(this.p, networkInfo);
            boolean d2 = bitmapHunter.d();
            if (!a2) {
                if (this.o && d2) {
                    z = true;
                }
                a(bitmapHunter, z);
                if (z) {
                    f(bitmapHunter);
                }
            } else if (!this.o || z2) {
                if (bitmapHunter.j().l) {
                    Utils.a("Dispatcher", "retrying", Utils.a(bitmapHunter));
                }
                if (bitmapHunter.l() instanceof ContentLengthException) {
                    bitmapHunter.i |= NetworkPolicy.NO_CACHE.a;
                }
                bitmapHunter.n = this.c.submit(bitmapHunter);
            } else {
                a(bitmapHunter, d2);
                if (d2) {
                    f(bitmapHunter);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void e(BitmapHunter bitmapHunter) {
        if (MemoryPolicy.b(bitmapHunter.g())) {
            this.k.set(bitmapHunter.f(), bitmapHunter.e());
        }
        this.e.remove(bitmapHunter.f());
        g(bitmapHunter);
        if (bitmapHunter.j().l) {
            Utils.a("Dispatcher", "batched", Utils.a(bitmapHunter), "for completion");
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        ArrayList arrayList = new ArrayList(this.m);
        this.m.clear();
        this.j.sendMessage(this.j.obtainMessage(8, arrayList));
        a((List<BitmapHunter>) arrayList);
    }

    /* access modifiers changed from: 0000 */
    public void a(BitmapHunter bitmapHunter, boolean z) {
        if (bitmapHunter.j().l) {
            String str = "Dispatcher";
            String str2 = "batched";
            String a2 = Utils.a(bitmapHunter);
            StringBuilder sb = new StringBuilder();
            sb.append("for error");
            sb.append(z ? " (will replay)" : "");
            Utils.a(str, str2, a2, sb.toString());
        }
        this.e.remove(bitmapHunter.f());
        g(bitmapHunter);
    }

    /* access modifiers changed from: 0000 */
    public void b(boolean z) {
        this.p = z;
    }

    /* access modifiers changed from: 0000 */
    public void b(NetworkInfo networkInfo) {
        if (this.c instanceof PicassoExecutorService) {
            ((PicassoExecutorService) this.c).a(networkInfo);
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            c();
        }
    }

    private void c() {
        if (!this.f.isEmpty()) {
            Iterator it = this.f.values().iterator();
            while (it.hasNext()) {
                Action action = (Action) it.next();
                it.remove();
                if (action.j().l) {
                    Utils.a("Dispatcher", "replaying", action.c().a());
                }
                a(action, false);
            }
        }
    }

    private void f(BitmapHunter bitmapHunter) {
        Action i2 = bitmapHunter.i();
        if (i2 != null) {
            e(i2);
        }
        List k2 = bitmapHunter.k();
        if (k2 != null) {
            int size = k2.size();
            for (int i3 = 0; i3 < size; i3++) {
                e((Action) k2.get(i3));
            }
        }
    }

    private void e(Action action) {
        Object d2 = action.d();
        if (d2 != null) {
            action.k = true;
            this.f.put(d2, action);
        }
    }

    private void g(BitmapHunter bitmapHunter) {
        if (!bitmapHunter.c()) {
            this.m.add(bitmapHunter);
            if (!this.i.hasMessages(7)) {
                this.i.sendEmptyMessageDelayed(7, 200);
            }
        }
    }

    private void a(List<BitmapHunter> list) {
        if (list != null && !list.isEmpty() && ((BitmapHunter) list.get(0)).j().l) {
            StringBuilder sb = new StringBuilder();
            for (BitmapHunter bitmapHunter : list) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(Utils.a(bitmapHunter));
            }
            Utils.a("Dispatcher", "delivered", sb.toString());
        }
    }
}
