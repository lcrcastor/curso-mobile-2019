package android.support.v4.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.support.annotation.GuardedBy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.INotificationSideChannel.Stub;
import android.util.Log;
import com.twincoders.twinpush.sdk.services.NotificationIntentService;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    private static final Object a = new Object();
    @GuardedBy("sEnabledNotificationListenersLock")
    private static String b;
    @GuardedBy("sEnabledNotificationListenersLock")
    private static Set<String> c = new HashSet();
    private static final Object f = new Object();
    @GuardedBy("sLock")
    private static SideChannelManager g;
    private final Context d;
    private final NotificationManager e = ((NotificationManager) this.d.getSystemService(NotificationIntentService.EXTRA_NOTIFICATION));

    static class CancelTask implements Task {
        final String a;
        final int b;
        final String c;
        final boolean d;

        CancelTask(String str) {
            this.a = str;
            this.b = 0;
            this.c = null;
            this.d = true;
        }

        CancelTask(String str, int i, String str2) {
            this.a = str;
            this.b = i;
            this.c = str2;
            this.d = false;
        }

        public void a(INotificationSideChannel iNotificationSideChannel) {
            if (this.d) {
                iNotificationSideChannel.cancelAll(this.a);
            } else {
                iNotificationSideChannel.cancel(this.a, this.b, this.c);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("CancelTask[");
            sb.append("packageName:");
            sb.append(this.a);
            sb.append(", id:");
            sb.append(this.b);
            sb.append(", tag:");
            sb.append(this.c);
            sb.append(", all:");
            sb.append(this.d);
            sb.append("]");
            return sb.toString();
        }
    }

    static class NotifyTask implements Task {
        final String a;
        final int b;
        final String c;
        final Notification d;

        NotifyTask(String str, int i, String str2, Notification notification) {
            this.a = str;
            this.b = i;
            this.c = str2;
            this.d = notification;
        }

        public void a(INotificationSideChannel iNotificationSideChannel) {
            iNotificationSideChannel.notify(this.a, this.b, this.c, this.d);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("NotifyTask[");
            sb.append("packageName:");
            sb.append(this.a);
            sb.append(", id:");
            sb.append(this.b);
            sb.append(", tag:");
            sb.append(this.c);
            sb.append("]");
            return sb.toString();
        }
    }

    static class ServiceConnectedEvent {
        final ComponentName a;
        final IBinder b;

        ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.a = componentName;
            this.b = iBinder;
        }
    }

    static class SideChannelManager implements ServiceConnection, Callback {
        private final Context a;
        private final HandlerThread b;
        private final Handler c;
        private final Map<ComponentName, ListenerRecord> d = new HashMap();
        private Set<String> e = new HashSet();

        static class ListenerRecord {
            final ComponentName a;
            boolean b = false;
            INotificationSideChannel c;
            ArrayDeque<Task> d = new ArrayDeque<>();
            int e = 0;

            ListenerRecord(ComponentName componentName) {
                this.a = componentName;
            }
        }

        SideChannelManager(Context context) {
            this.a = context;
            this.b = new HandlerThread("NotificationManagerCompat");
            this.b.start();
            this.c = new Handler(this.b.getLooper(), this);
        }

        public void a(Task task) {
            this.c.obtainMessage(0, task).sendToTarget();
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    b((Task) message.obj);
                    return true;
                case 1:
                    ServiceConnectedEvent serviceConnectedEvent = (ServiceConnectedEvent) message.obj;
                    a(serviceConnectedEvent.a, serviceConnectedEvent.b);
                    return true;
                case 2:
                    a((ComponentName) message.obj);
                    return true;
                case 3:
                    b((ComponentName) message.obj);
                    return true;
                default:
                    return false;
            }
        }

        private void b(Task task) {
            a();
            for (ListenerRecord listenerRecord : this.d.values()) {
                listenerRecord.d.add(task);
                d(listenerRecord);
            }
        }

        private void a(ComponentName componentName, IBinder iBinder) {
            ListenerRecord listenerRecord = (ListenerRecord) this.d.get(componentName);
            if (listenerRecord != null) {
                listenerRecord.c = Stub.asInterface(iBinder);
                listenerRecord.e = 0;
                d(listenerRecord);
            }
        }

        private void a(ComponentName componentName) {
            ListenerRecord listenerRecord = (ListenerRecord) this.d.get(componentName);
            if (listenerRecord != null) {
                b(listenerRecord);
            }
        }

        private void b(ComponentName componentName) {
            ListenerRecord listenerRecord = (ListenerRecord) this.d.get(componentName);
            if (listenerRecord != null) {
                d(listenerRecord);
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Connected to service ");
                sb.append(componentName);
                Log.d("NotifManCompat", sb.toString());
            }
            this.c.obtainMessage(1, new ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Disconnected from service ");
                sb.append(componentName);
                Log.d("NotifManCompat", sb.toString());
            }
            this.c.obtainMessage(2, componentName).sendToTarget();
        }

        private void a() {
            Set<String> enabledListenerPackages = NotificationManagerCompat.getEnabledListenerPackages(this.a);
            if (!enabledListenerPackages.equals(this.e)) {
                this.e = enabledListenerPackages;
                List<ResolveInfo> queryIntentServices = this.a.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 0);
                HashSet<ComponentName> hashSet = new HashSet<>();
                for (ResolveInfo resolveInfo : queryIntentServices) {
                    if (enabledListenerPackages.contains(resolveInfo.serviceInfo.packageName)) {
                        ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
                        if (resolveInfo.serviceInfo.permission != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Permission present on component ");
                            sb.append(componentName);
                            sb.append(", not adding listener record.");
                            Log.w("NotifManCompat", sb.toString());
                        } else {
                            hashSet.add(componentName);
                        }
                    }
                }
                for (ComponentName componentName2 : hashSet) {
                    if (!this.d.containsKey(componentName2)) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Adding listener record for ");
                            sb2.append(componentName2);
                            Log.d("NotifManCompat", sb2.toString());
                        }
                        this.d.put(componentName2, new ListenerRecord(componentName2));
                    }
                }
                Iterator it = this.d.entrySet().iterator();
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    if (!hashSet.contains(entry.getKey())) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("Removing listener record for ");
                            sb3.append(entry.getKey());
                            Log.d("NotifManCompat", sb3.toString());
                        }
                        b((ListenerRecord) entry.getValue());
                        it.remove();
                    }
                }
            }
        }

        private boolean a(ListenerRecord listenerRecord) {
            if (listenerRecord.b) {
                return true;
            }
            listenerRecord.b = this.a.bindService(new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(listenerRecord.a), this, 33);
            if (listenerRecord.b) {
                listenerRecord.e = 0;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to bind to listener ");
                sb.append(listenerRecord.a);
                Log.w("NotifManCompat", sb.toString());
                this.a.unbindService(this);
            }
            return listenerRecord.b;
        }

        private void b(ListenerRecord listenerRecord) {
            if (listenerRecord.b) {
                this.a.unbindService(this);
                listenerRecord.b = false;
            }
            listenerRecord.c = null;
        }

        private void c(ListenerRecord listenerRecord) {
            if (!this.c.hasMessages(3, listenerRecord.a)) {
                listenerRecord.e++;
                if (listenerRecord.e > 6) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Giving up on delivering ");
                    sb.append(listenerRecord.d.size());
                    sb.append(" tasks to ");
                    sb.append(listenerRecord.a);
                    sb.append(" after ");
                    sb.append(listenerRecord.e);
                    sb.append(" retries");
                    Log.w("NotifManCompat", sb.toString());
                    listenerRecord.d.clear();
                    return;
                }
                int i = (1 << (listenerRecord.e - 1)) * 1000;
                if (Log.isLoggable("NotifManCompat", 3)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Scheduling retry for ");
                    sb2.append(i);
                    sb2.append(" ms");
                    Log.d("NotifManCompat", sb2.toString());
                }
                this.c.sendMessageDelayed(this.c.obtainMessage(3, listenerRecord.a), (long) i);
            }
        }

        private void d(ListenerRecord listenerRecord) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Processing component ");
                sb.append(listenerRecord.a);
                sb.append(", ");
                sb.append(listenerRecord.d.size());
                sb.append(" queued tasks");
                Log.d("NotifManCompat", sb.toString());
            }
            if (!listenerRecord.d.isEmpty()) {
                if (!a(listenerRecord) || listenerRecord.c == null) {
                    c(listenerRecord);
                    return;
                }
                while (true) {
                    Task task = (Task) listenerRecord.d.peek();
                    if (task == null) {
                        break;
                    }
                    try {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Sending task ");
                            sb2.append(task);
                            Log.d("NotifManCompat", sb2.toString());
                        }
                        task.a(listenerRecord.c);
                        listenerRecord.d.remove();
                    } catch (DeadObjectException unused) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("Remote service has died: ");
                            sb3.append(listenerRecord.a);
                            Log.d("NotifManCompat", sb3.toString());
                        }
                    } catch (RemoteException e2) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("RemoteException communicating with ");
                        sb4.append(listenerRecord.a);
                        Log.w("NotifManCompat", sb4.toString(), e2);
                    }
                }
                if (!listenerRecord.d.isEmpty()) {
                    c(listenerRecord);
                }
            }
        }
    }

    interface Task {
        void a(INotificationSideChannel iNotificationSideChannel);
    }

    @NonNull
    public static NotificationManagerCompat from(@NonNull Context context) {
        return new NotificationManagerCompat(context);
    }

    private NotificationManagerCompat(Context context) {
        this.d = context;
    }

    public void cancel(int i) {
        cancel(null, i);
    }

    public void cancel(@Nullable String str, int i) {
        this.e.cancel(str, i);
        if (VERSION.SDK_INT <= 19) {
            a((Task) new CancelTask(this.d.getPackageName(), i, str));
        }
    }

    public void cancelAll() {
        this.e.cancelAll();
        if (VERSION.SDK_INT <= 19) {
            a((Task) new CancelTask(this.d.getPackageName()));
        }
    }

    public void notify(int i, @NonNull Notification notification) {
        notify(null, i, notification);
    }

    public void notify(@Nullable String str, int i, @NonNull Notification notification) {
        if (a(notification)) {
            a((Task) new NotifyTask(this.d.getPackageName(), i, str, notification));
            this.e.cancel(str, i);
            return;
        }
        this.e.notify(str, i, notification);
    }

    public boolean areNotificationsEnabled() {
        if (VERSION.SDK_INT >= 24) {
            return this.e.areNotificationsEnabled();
        }
        boolean z = true;
        if (VERSION.SDK_INT < 19) {
            return true;
        }
        AppOpsManager appOpsManager = (AppOpsManager) this.d.getSystemService("appops");
        ApplicationInfo applicationInfo = this.d.getApplicationInfo();
        String packageName = this.d.getApplicationContext().getPackageName();
        int i = applicationInfo.uid;
        try {
            Class cls = Class.forName(AppOpsManager.class.getName());
            if (((Integer) cls.getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(((Integer) cls.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), packageName})).intValue() != 0) {
                z = false;
            }
            return z;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException unused) {
            return true;
        }
    }

    public int getImportance() {
        if (VERSION.SDK_INT >= 24) {
            return this.e.getImportance();
        }
        return -1000;
    }

    @NonNull
    public static Set<String> getEnabledListenerPackages(@NonNull Context context) {
        Set<String> set;
        String string = Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        synchronized (a) {
            if (string != null) {
                try {
                    if (!string.equals(b)) {
                        String[] split = string.split(":");
                        HashSet hashSet = new HashSet(split.length);
                        for (String unflattenFromString : split) {
                            ComponentName unflattenFromString2 = ComponentName.unflattenFromString(unflattenFromString);
                            if (unflattenFromString2 != null) {
                                hashSet.add(unflattenFromString2.getPackageName());
                            }
                        }
                        c = hashSet;
                        b = string;
                    }
                } finally {
                }
            }
            set = c;
        }
        return set;
    }

    private static boolean a(Notification notification) {
        Bundle extras = NotificationCompat.getExtras(notification);
        return extras != null && extras.getBoolean(EXTRA_USE_SIDE_CHANNEL);
    }

    private void a(Task task) {
        synchronized (f) {
            if (g == null) {
                g = new SideChannelManager(this.d.getApplicationContext());
            }
            g.a(task);
        }
    }
}
