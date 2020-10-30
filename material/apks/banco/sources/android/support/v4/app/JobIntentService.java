package android.support.v4.app;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class JobIntentService extends Service {
    static final Object h = new Object();
    static final HashMap<ComponentName, WorkEnqueuer> i = new HashMap<>();
    CompatJobEngine a;
    WorkEnqueuer b;
    CommandProcessor c;
    boolean d = false;
    boolean e = false;
    boolean f = false;
    final ArrayList<CompatWorkItem> g;

    final class CommandProcessor extends AsyncTask<Void, Void, Void> {
        CommandProcessor() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Void doInBackground(Void... voidArr) {
            while (true) {
                GenericWorkItem c = JobIntentService.this.c();
                if (c == null) {
                    return null;
                }
                JobIntentService.this.onHandleWork(c.a());
                c.b();
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onCancelled(Void voidR) {
            JobIntentService.this.b();
        }

        /* access modifiers changed from: protected */
        /* renamed from: b */
        public void onPostExecute(Void voidR) {
            JobIntentService.this.b();
        }
    }

    interface CompatJobEngine {
        IBinder a();

        GenericWorkItem b();
    }

    static final class CompatWorkEnqueuer extends WorkEnqueuer {
        boolean a;
        boolean b;
        private final Context f;
        private final WakeLock g;
        private final WakeLock h;

        CompatWorkEnqueuer(Context context, ComponentName componentName) {
            super(context, componentName);
            this.f = context.getApplicationContext();
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            StringBuilder sb = new StringBuilder();
            sb.append(componentName.getClassName());
            sb.append(":launch");
            this.g = powerManager.newWakeLock(1, sb.toString());
            this.g.setReferenceCounted(false);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(componentName.getClassName());
            sb2.append(":run");
            this.h = powerManager.newWakeLock(1, sb2.toString());
            this.h.setReferenceCounted(false);
        }

        /* access modifiers changed from: 0000 */
        public void a(Intent intent) {
            Intent intent2 = new Intent(intent);
            intent2.setComponent(this.c);
            if (this.f.startService(intent2) != null) {
                synchronized (this) {
                    if (!this.a) {
                        this.a = true;
                        if (!this.b) {
                            this.g.acquire(60000);
                        }
                    }
                }
            }
        }

        public void a() {
            synchronized (this) {
                this.a = false;
            }
        }

        public void b() {
            synchronized (this) {
                if (!this.b) {
                    this.b = true;
                    this.h.acquire(600000);
                    this.g.release();
                }
            }
        }

        public void c() {
            synchronized (this) {
                if (this.b) {
                    if (this.a) {
                        this.g.acquire(60000);
                    }
                    this.b = false;
                    this.h.release();
                }
            }
        }
    }

    final class CompatWorkItem implements GenericWorkItem {
        final Intent a;
        final int b;

        CompatWorkItem(Intent intent, int i) {
            this.a = intent;
            this.b = i;
        }

        public Intent a() {
            return this.a;
        }

        public void b() {
            JobIntentService.this.stopSelf(this.b);
        }
    }

    interface GenericWorkItem {
        Intent a();

        void b();
    }

    @RequiresApi(26)
    static final class JobServiceEngineImpl extends JobServiceEngine implements CompatJobEngine {
        final JobIntentService a;
        final Object b = new Object();
        JobParameters c;

        final class WrapperWorkItem implements GenericWorkItem {
            final JobWorkItem a;

            WrapperWorkItem(JobWorkItem jobWorkItem) {
                this.a = jobWorkItem;
            }

            public Intent a() {
                return this.a.getIntent();
            }

            public void b() {
                synchronized (JobServiceEngineImpl.this.b) {
                    if (JobServiceEngineImpl.this.c != null) {
                        JobServiceEngineImpl.this.c.completeWork(this.a);
                    }
                }
            }
        }

        JobServiceEngineImpl(JobIntentService jobIntentService) {
            super(jobIntentService);
            this.a = jobIntentService;
        }

        public IBinder a() {
            return getBinder();
        }

        public boolean onStartJob(JobParameters jobParameters) {
            this.c = jobParameters;
            this.a.a(false);
            return true;
        }

        public boolean onStopJob(JobParameters jobParameters) {
            boolean a2 = this.a.a();
            synchronized (this.b) {
                this.c = null;
            }
            return a2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
            r1.getIntent().setExtrasClassLoader(r3.a.getClassLoader());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0025, code lost:
            return new android.support.v4.app.JobIntentService.JobServiceEngineImpl.WrapperWorkItem(r3, r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
            if (r1 == null) goto L_0x0026;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.support.v4.app.JobIntentService.GenericWorkItem b() {
            /*
                r3 = this;
                java.lang.Object r0 = r3.b
                monitor-enter(r0)
                android.app.job.JobParameters r1 = r3.c     // Catch:{ all -> 0x0027 }
                r2 = 0
                if (r1 != 0) goto L_0x000a
                monitor-exit(r0)     // Catch:{ all -> 0x0027 }
                return r2
            L_0x000a:
                android.app.job.JobParameters r1 = r3.c     // Catch:{ all -> 0x0027 }
                android.app.job.JobWorkItem r1 = r1.dequeueWork()     // Catch:{ all -> 0x0027 }
                monitor-exit(r0)     // Catch:{ all -> 0x0027 }
                if (r1 == 0) goto L_0x0026
                android.content.Intent r0 = r1.getIntent()
                android.support.v4.app.JobIntentService r2 = r3.a
                java.lang.ClassLoader r2 = r2.getClassLoader()
                r0.setExtrasClassLoader(r2)
                android.support.v4.app.JobIntentService$JobServiceEngineImpl$WrapperWorkItem r0 = new android.support.v4.app.JobIntentService$JobServiceEngineImpl$WrapperWorkItem
                r0.<init>(r1)
                return r0
            L_0x0026:
                return r2
            L_0x0027:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0027 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.JobIntentService.JobServiceEngineImpl.b():android.support.v4.app.JobIntentService$GenericWorkItem");
        }
    }

    @RequiresApi(26)
    static final class JobWorkEnqueuer extends WorkEnqueuer {
        private final JobInfo a;
        private final JobScheduler b;

        JobWorkEnqueuer(Context context, ComponentName componentName, int i) {
            super(context, componentName);
            a(i);
            this.a = new Builder(i, this.c).setOverrideDeadline(0).build();
            this.b = (JobScheduler) context.getApplicationContext().getSystemService("jobscheduler");
        }

        /* access modifiers changed from: 0000 */
        public void a(Intent intent) {
            this.b.enqueue(this.a, new JobWorkItem(intent));
        }
    }

    static abstract class WorkEnqueuer {
        final ComponentName c;
        boolean d;
        int e;

        public void a() {
        }

        /* access modifiers changed from: 0000 */
        public abstract void a(Intent intent);

        public void b() {
        }

        public void c() {
        }

        WorkEnqueuer(Context context, ComponentName componentName) {
            this.c = componentName;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            if (!this.d) {
                this.d = true;
                this.e = i;
            } else if (this.e != i) {
                StringBuilder sb = new StringBuilder();
                sb.append("Given job ID ");
                sb.append(i);
                sb.append(" is different than previous ");
                sb.append(this.e);
                throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void onHandleWork(@NonNull Intent intent);

    public boolean onStopCurrentWork() {
        return true;
    }

    public JobIntentService() {
        if (VERSION.SDK_INT >= 26) {
            this.g = null;
        } else {
            this.g = new ArrayList<>();
        }
    }

    public void onCreate() {
        super.onCreate();
        if (VERSION.SDK_INT >= 26) {
            this.a = new JobServiceEngineImpl(this);
            this.b = null;
            return;
        }
        this.a = null;
        this.b = a(this, new ComponentName(this, getClass()), false, 0);
    }

    public int onStartCommand(@Nullable Intent intent, int i2, int i3) {
        if (this.g == null) {
            return 2;
        }
        this.b.a();
        synchronized (this.g) {
            ArrayList<CompatWorkItem> arrayList = this.g;
            if (intent == null) {
                intent = new Intent();
            }
            arrayList.add(new CompatWorkItem(intent, i3));
            a(true);
        }
        return 3;
    }

    public IBinder onBind(@NonNull Intent intent) {
        if (this.a != null) {
            return this.a.a();
        }
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.g != null) {
            synchronized (this.g) {
                this.f = true;
                this.b.c();
            }
        }
    }

    public static void enqueueWork(@NonNull Context context, @NonNull Class cls, int i2, @NonNull Intent intent) {
        enqueueWork(context, new ComponentName(context, cls), i2, intent);
    }

    public static void enqueueWork(@NonNull Context context, @NonNull ComponentName componentName, int i2, @NonNull Intent intent) {
        if (intent == null) {
            throw new IllegalArgumentException("work must not be null");
        }
        synchronized (h) {
            WorkEnqueuer a2 = a(context, componentName, true, i2);
            a2.a(i2);
            a2.a(intent);
        }
    }

    static WorkEnqueuer a(Context context, ComponentName componentName, boolean z, int i2) {
        WorkEnqueuer workEnqueuer;
        WorkEnqueuer workEnqueuer2 = (WorkEnqueuer) i.get(componentName);
        if (workEnqueuer2 != null) {
            return workEnqueuer2;
        }
        if (VERSION.SDK_INT < 26) {
            workEnqueuer = new CompatWorkEnqueuer(context, componentName);
        } else if (!z) {
            throw new IllegalArgumentException("Can't be here without a job id");
        } else {
            workEnqueuer = new JobWorkEnqueuer(context, componentName, i2);
        }
        WorkEnqueuer workEnqueuer3 = workEnqueuer;
        i.put(componentName, workEnqueuer3);
        return workEnqueuer3;
    }

    public void setInterruptIfStopped(boolean z) {
        this.d = z;
    }

    public boolean isStopped() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        if (this.c != null) {
            this.c.cancel(this.d);
        }
        this.e = true;
        return onStopCurrentWork();
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        if (this.c == null) {
            this.c = new CommandProcessor();
            if (this.b != null && z) {
                this.b.b();
            }
            this.c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (this.g != null) {
            synchronized (this.g) {
                this.c = null;
                if (this.g != null && this.g.size() > 0) {
                    a(false);
                } else if (!this.f) {
                    this.b.c();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public GenericWorkItem c() {
        if (this.a != null) {
            return this.a.b();
        }
        synchronized (this.g) {
            if (this.g.size() <= 0) {
                return null;
            }
            GenericWorkItem genericWorkItem = (GenericWorkItem) this.g.remove(0);
            return genericWorkItem;
        }
    }
}
