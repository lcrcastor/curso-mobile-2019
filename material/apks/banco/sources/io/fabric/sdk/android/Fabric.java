package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.ActivityLifecycleManager.Callbacks;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fabric {
    public static final String TAG = "Fabric";
    static volatile Fabric a;
    static final Logger b = new DefaultLogger();
    final Logger c;
    final boolean d;
    private final Context e;
    private final Map<Class<? extends Kit>, Kit> f;
    private final ExecutorService g;
    private final Handler h;
    /* access modifiers changed from: private */
    public final InitializationCallback<Fabric> i;
    private final InitializationCallback<?> j;
    private final IdManager k;
    private ActivityLifecycleManager l;
    private WeakReference<Activity> m;
    /* access modifiers changed from: private */
    public AtomicBoolean n = new AtomicBoolean(false);

    public static class Builder {
        private final Context a;
        private Kit[] b;
        private PriorityThreadPoolExecutor c;
        private Handler d;
        private Logger e;
        private boolean f;
        private String g;
        private String h;
        private InitializationCallback<Fabric> i;

        @Deprecated
        public Builder executorService(ExecutorService executorService) {
            return this;
        }

        @Deprecated
        public Builder handler(Handler handler) {
            return this;
        }

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.a = context;
        }

        public Builder kits(Kit... kitArr) {
            if (this.b != null) {
                throw new IllegalStateException("Kits already set.");
            }
            this.b = kitArr;
            return this;
        }

        public Builder threadPoolExecutor(PriorityThreadPoolExecutor priorityThreadPoolExecutor) {
            if (priorityThreadPoolExecutor == null) {
                throw new IllegalArgumentException("PriorityThreadPoolExecutor must not be null.");
            } else if (this.c != null) {
                throw new IllegalStateException("PriorityThreadPoolExecutor already set.");
            } else {
                this.c = priorityThreadPoolExecutor;
                return this;
            }
        }

        public Builder logger(Logger logger) {
            if (logger == null) {
                throw new IllegalArgumentException("Logger must not be null.");
            } else if (this.e != null) {
                throw new IllegalStateException("Logger already set.");
            } else {
                this.e = logger;
                return this;
            }
        }

        public Builder appIdentifier(String str) {
            if (str == null) {
                throw new IllegalArgumentException("appIdentifier must not be null.");
            } else if (this.h != null) {
                throw new IllegalStateException("appIdentifier already set.");
            } else {
                this.h = str;
                return this;
            }
        }

        public Builder appInstallIdentifier(String str) {
            if (str == null) {
                throw new IllegalArgumentException("appInstallIdentifier must not be null.");
            } else if (this.g != null) {
                throw new IllegalStateException("appInstallIdentifier already set.");
            } else {
                this.g = str;
                return this;
            }
        }

        public Builder debuggable(boolean z) {
            this.f = z;
            return this;
        }

        public Builder initializationCallback(InitializationCallback<Fabric> initializationCallback) {
            if (initializationCallback == null) {
                throw new IllegalArgumentException("initializationCallback must not be null.");
            } else if (this.i != null) {
                throw new IllegalStateException("initializationCallback already set.");
            } else {
                this.i = initializationCallback;
                return this;
            }
        }

        public Fabric build() {
            Map a2;
            if (this.c == null) {
                this.c = PriorityThreadPoolExecutor.create();
            }
            if (this.d == null) {
                this.d = new Handler(Looper.getMainLooper());
            }
            if (this.e == null) {
                if (this.f) {
                    this.e = new DefaultLogger(3);
                } else {
                    this.e = new DefaultLogger();
                }
            }
            if (this.h == null) {
                this.h = this.a.getPackageName();
            }
            if (this.i == null) {
                this.i = InitializationCallback.EMPTY;
            }
            if (this.b == null) {
                a2 = new HashMap();
            } else {
                a2 = Fabric.b((Collection<? extends Kit>) Arrays.asList(this.b));
            }
            Map map = a2;
            Fabric fabric = new Fabric(this.a, map, this.c, this.d, this.e, this.f, this.i, new IdManager(this.a, this.h, this.g, map.values()));
            return fabric;
        }
    }

    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    public String getVersion() {
        return "1.3.14.143";
    }

    static Fabric a() {
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("Must Initialize Fabric before using singleton()");
    }

    Fabric(Context context, Map<Class<? extends Kit>, Kit> map, PriorityThreadPoolExecutor priorityThreadPoolExecutor, Handler handler, Logger logger, boolean z, InitializationCallback initializationCallback, IdManager idManager) {
        this.e = context.getApplicationContext();
        this.f = map;
        this.g = priorityThreadPoolExecutor;
        this.h = handler;
        this.c = logger;
        this.d = z;
        this.i = initializationCallback;
        this.j = a(map.size());
        this.k = idManager;
        setCurrentActivity(c(context));
    }

    public static Fabric with(Context context, Kit... kitArr) {
        if (a == null) {
            synchronized (Fabric.class) {
                if (a == null) {
                    c(new Builder(context).kits(kitArr).build());
                }
            }
        }
        return a;
    }

    public static Fabric with(Fabric fabric) {
        if (a == null) {
            synchronized (Fabric.class) {
                if (a == null) {
                    c(fabric);
                }
            }
        }
        return a;
    }

    private static void c(Fabric fabric) {
        a = fabric;
        fabric.b();
    }

    public Fabric setCurrentActivity(Activity activity) {
        this.m = new WeakReference<>(activity);
        return this;
    }

    public Activity getCurrentActivity() {
        if (this.m != null) {
            return (Activity) this.m.get();
        }
        return null;
    }

    private void b() {
        this.l = new ActivityLifecycleManager(this.e);
        this.l.registerCallbacks(new Callbacks() {
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Fabric.this.setCurrentActivity(activity);
            }

            public void onActivityStarted(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }

            public void onActivityResumed(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }
        });
        a(this.e);
    }

    /* access modifiers changed from: 0000 */
    public void a(Context context) {
        StringBuilder sb;
        Future b2 = b(context);
        Collection kits = getKits();
        Onboarding onboarding = new Onboarding(b2, kits);
        ArrayList<Kit> arrayList = new ArrayList<>(kits);
        Collections.sort(arrayList);
        onboarding.a(context, this, InitializationCallback.EMPTY, this.k);
        for (Kit a2 : arrayList) {
            a2.a(context, this, this.j, this.k);
        }
        onboarding.w();
        if (getLogger().isLoggable(TAG, 3)) {
            sb = new StringBuilder("Initializing ");
            sb.append(getIdentifier());
            sb.append(" [Version: ");
            sb.append(getVersion());
            sb.append("], with the following kits:\n");
        } else {
            sb = null;
        }
        for (Kit kit : arrayList) {
            kit.c.addDependency((Task) onboarding.c);
            a(this.f, kit);
            kit.w();
            if (sb != null) {
                sb.append(kit.getIdentifier());
                sb.append(" [Version: ");
                sb.append(kit.getVersion());
                sb.append("]\n");
            }
        }
        if (sb != null) {
            getLogger().d(TAG, sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Map<Class<? extends Kit>, Kit> map, Kit kit) {
        Class[] value;
        DependsOn dependsOn = kit.g;
        if (dependsOn != null) {
            for (Class cls : dependsOn.value()) {
                if (cls.isInterface()) {
                    for (Kit kit2 : map.values()) {
                        if (cls.isAssignableFrom(kit2.getClass())) {
                            kit.c.addDependency((Task) kit2.c);
                        }
                    }
                } else if (((Kit) map.get(cls)) == null) {
                    throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
                } else {
                    kit.c.addDependency((Task) ((Kit) map.get(cls)).c);
                }
            }
        }
    }

    private Activity c(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    public ActivityLifecycleManager getActivityLifecycleManager() {
        return this.l;
    }

    public ExecutorService getExecutorService() {
        return this.g;
    }

    public Handler getMainHandler() {
        return this.h;
    }

    public Collection<Kit> getKits() {
        return this.f.values();
    }

    public static <T extends Kit> T getKit(Class<T> cls) {
        return (Kit) a().f.get(cls);
    }

    public static Logger getLogger() {
        if (a == null) {
            return b;
        }
        return a.c;
    }

    public static boolean isDebuggable() {
        if (a == null) {
            return false;
        }
        return a.d;
    }

    public static boolean isInitialized() {
        return a != null && a.n.get();
    }

    public String getAppIdentifier() {
        return this.k.getAppIdentifier();
    }

    public String getAppInstallIdentifier() {
        return this.k.getAppInstallIdentifier();
    }

    /* access modifiers changed from: private */
    public static Map<Class<? extends Kit>, Kit> b(Collection<? extends Kit> collection) {
        HashMap hashMap = new HashMap(collection.size());
        a((Map<Class<? extends Kit>, Kit>) hashMap, collection);
        return hashMap;
    }

    private static void a(Map<Class<? extends Kit>, Kit> map, Collection<? extends Kit> collection) {
        for (Kit kit : collection) {
            map.put(kit.getClass(), kit);
            if (kit instanceof KitGroup) {
                a(map, ((KitGroup) kit).getKits());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public InitializationCallback<?> a(final int i2) {
        return new InitializationCallback() {
            final CountDownLatch a = new CountDownLatch(i2);

            public void success(Object obj) {
                this.a.countDown();
                if (this.a.getCount() == 0) {
                    Fabric.this.n.set(true);
                    Fabric.this.i.success(Fabric.this);
                }
            }

            public void failure(Exception exc) {
                Fabric.this.i.failure(exc);
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public Future<Map<String, KitInfo>> b(Context context) {
        return getExecutorService().submit(new FabricKitsFinder(context.getPackageCodePath()));
    }
}
