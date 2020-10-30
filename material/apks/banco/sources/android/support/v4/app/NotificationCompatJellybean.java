package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat.Action;
import android.util.Log;
import android.util.SparseArray;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiresApi(16)
class NotificationCompatJellybean {
    private static final Object a = new Object();
    private static Field b;
    private static boolean c;
    private static final Object d = new Object();
    private static Class<?> e;
    private static Field f;
    private static Field g;
    private static Field h;
    private static Field i;
    private static boolean j;

    NotificationCompatJellybean() {
    }

    public static SparseArray<Bundle> a(List<Bundle> list) {
        int size = list.size();
        SparseArray<Bundle> sparseArray = null;
        for (int i2 = 0; i2 < size; i2++) {
            Bundle bundle = (Bundle) list.get(i2);
            if (bundle != null) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                sparseArray.put(i2, bundle);
            }
        }
        return sparseArray;
    }

    public static Bundle a(Notification notification) {
        synchronized (a) {
            if (c) {
                return null;
            }
            try {
                if (b == null) {
                    Field declaredField = Notification.class.getDeclaredField("extras");
                    if (!Bundle.class.isAssignableFrom(declaredField.getType())) {
                        Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
                        c = true;
                        return null;
                    }
                    declaredField.setAccessible(true);
                    b = declaredField;
                }
                Bundle bundle = (Bundle) b.get(notification);
                if (bundle == null) {
                    bundle = new Bundle();
                    b.set(notification, bundle);
                }
                return bundle;
            } catch (IllegalAccessException e2) {
                Log.e("NotificationCompat", "Unable to access notification extras", e2);
                c = true;
                return null;
            } catch (NoSuchFieldException e3) {
                Log.e("NotificationCompat", "Unable to access notification extras", e3);
                c = true;
                return null;
            }
        }
    }

    public static Action a(int i2, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle) {
        boolean z;
        RemoteInput[] remoteInputArr;
        RemoteInput[] remoteInputArr2;
        if (bundle != null) {
            RemoteInput[] a2 = a(a(bundle, NotificationCompatExtras.EXTRA_REMOTE_INPUTS));
            remoteInputArr2 = a2;
            remoteInputArr = a(a(bundle, "android.support.dataRemoteInputs"));
            z = bundle.getBoolean("android.support.allowGeneratedReplies");
        } else {
            remoteInputArr2 = null;
            remoteInputArr = null;
            z = false;
        }
        Action action = new Action(i2, charSequence, pendingIntent, bundle, remoteInputArr2, remoteInputArr, z);
        return action;
    }

    public static Bundle a(Builder builder, Action action) {
        builder.addAction(action.getIcon(), action.getTitle(), action.getActionIntent());
        Bundle bundle = new Bundle(action.getExtras());
        if (action.getRemoteInputs() != null) {
            bundle.putParcelableArray(NotificationCompatExtras.EXTRA_REMOTE_INPUTS, a(action.getRemoteInputs()));
        }
        if (action.getDataOnlyRemoteInputs() != null) {
            bundle.putParcelableArray("android.support.dataRemoteInputs", a(action.getDataOnlyRemoteInputs()));
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        return bundle;
    }

    public static int b(Notification notification) {
        int length;
        synchronized (d) {
            Object[] c2 = c(notification);
            length = c2 != null ? c2.length : 0;
        }
        return length;
    }

    public static Action a(Notification notification, int i2) {
        Bundle bundle;
        synchronized (d) {
            try {
                Object[] c2 = c(notification);
                if (c2 != null) {
                    Object obj = c2[i2];
                    Bundle a2 = a(notification);
                    if (a2 != null) {
                        SparseArray sparseParcelableArray = a2.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
                        if (sparseParcelableArray != null) {
                            bundle = (Bundle) sparseParcelableArray.get(i2);
                            Action a3 = a(g.getInt(obj), (CharSequence) h.get(obj), (PendingIntent) i.get(obj), bundle);
                            return a3;
                        }
                    }
                    bundle = null;
                    Action a32 = a(g.getInt(obj), (CharSequence) h.get(obj), (PendingIntent) i.get(obj), bundle);
                    return a32;
                }
            } catch (IllegalAccessException e2) {
                Log.e("NotificationCompat", "Unable to access notification actions", e2);
                j = true;
            } catch (Throwable th) {
                throw th;
            }
        }
        return null;
    }

    private static Object[] c(Notification notification) {
        synchronized (d) {
            if (!a()) {
                return null;
            }
            try {
                Object[] objArr = (Object[]) f.get(notification);
                return objArr;
            } catch (IllegalAccessException e2) {
                Log.e("NotificationCompat", "Unable to access notification actions", e2);
                j = true;
                return null;
            }
        }
    }

    private static boolean a() {
        if (j) {
            return false;
        }
        try {
            if (f == null) {
                e = Class.forName("android.app.Notification$Action");
                g = e.getDeclaredField(SettingsJsonConstants.APP_ICON_KEY);
                h = e.getDeclaredField("title");
                i = e.getDeclaredField("actionIntent");
                f = Notification.class.getDeclaredField("actions");
                f.setAccessible(true);
            }
        } catch (ClassNotFoundException e2) {
            Log.e("NotificationCompat", "Unable to access notification actions", e2);
            j = true;
        } catch (NoSuchFieldException e3) {
            Log.e("NotificationCompat", "Unable to access notification actions", e3);
            j = true;
        }
        return true ^ j;
    }

    static Action a(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("extras");
        Action action = new Action(bundle.getInt(SettingsJsonConstants.APP_ICON_KEY), bundle.getCharSequence("title"), (PendingIntent) bundle.getParcelable("actionIntent"), bundle.getBundle("extras"), a(a(bundle, "remoteInputs")), a(a(bundle, "dataOnlyRemoteInputs")), bundle2 != null ? bundle2.getBoolean("android.support.allowGeneratedReplies", false) : false);
        return action;
    }

    static Bundle a(Action action) {
        Bundle bundle;
        Bundle bundle2 = new Bundle();
        bundle2.putInt(SettingsJsonConstants.APP_ICON_KEY, action.getIcon());
        bundle2.putCharSequence("title", action.getTitle());
        bundle2.putParcelable("actionIntent", action.getActionIntent());
        if (action.getExtras() != null) {
            bundle = new Bundle(action.getExtras());
        } else {
            bundle = new Bundle();
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        bundle2.putBundle("extras", bundle);
        bundle2.putParcelableArray("remoteInputs", a(action.getRemoteInputs()));
        return bundle2;
    }

    private static RemoteInput b(Bundle bundle) {
        ArrayList stringArrayList = bundle.getStringArrayList("allowedDataTypes");
        HashSet hashSet = new HashSet();
        if (stringArrayList != null) {
            Iterator it = stringArrayList.iterator();
            while (it.hasNext()) {
                hashSet.add((String) it.next());
            }
        }
        RemoteInput remoteInput = new RemoteInput(bundle.getString("resultKey"), bundle.getCharSequence("label"), bundle.getCharSequenceArray("choices"), bundle.getBoolean("allowFreeFormInput"), bundle.getBundle("extras"), hashSet);
        return remoteInput;
    }

    private static Bundle a(RemoteInput remoteInput) {
        Bundle bundle = new Bundle();
        bundle.putString("resultKey", remoteInput.getResultKey());
        bundle.putCharSequence("label", remoteInput.getLabel());
        bundle.putCharSequenceArray("choices", remoteInput.getChoices());
        bundle.putBoolean("allowFreeFormInput", remoteInput.getAllowFreeFormInput());
        bundle.putBundle("extras", remoteInput.getExtras());
        Set<String> allowedDataTypes = remoteInput.getAllowedDataTypes();
        if (allowedDataTypes != null && !allowedDataTypes.isEmpty()) {
            ArrayList arrayList = new ArrayList(allowedDataTypes.size());
            for (String add : allowedDataTypes) {
                arrayList.add(add);
            }
            bundle.putStringArrayList("allowedDataTypes", arrayList);
        }
        return bundle;
    }

    private static RemoteInput[] a(Bundle[] bundleArr) {
        if (bundleArr == null) {
            return null;
        }
        RemoteInput[] remoteInputArr = new RemoteInput[bundleArr.length];
        for (int i2 = 0; i2 < bundleArr.length; i2++) {
            remoteInputArr[i2] = b(bundleArr[i2]);
        }
        return remoteInputArr;
    }

    private static Bundle[] a(RemoteInput[] remoteInputArr) {
        if (remoteInputArr == null) {
            return null;
        }
        Bundle[] bundleArr = new Bundle[remoteInputArr.length];
        for (int i2 = 0; i2 < remoteInputArr.length; i2++) {
            bundleArr[i2] = a(remoteInputArr[i2]);
        }
        return bundleArr;
    }

    private static Bundle[] a(Bundle bundle, String str) {
        Parcelable[] parcelableArray = bundle.getParcelableArray(str);
        if ((parcelableArray instanceof Bundle[]) || parcelableArray == null) {
            return (Bundle[]) parcelableArray;
        }
        Bundle[] bundleArr = (Bundle[]) Arrays.copyOf(parcelableArray, parcelableArray.length, Bundle[].class);
        bundle.putParcelableArray(str, bundleArr);
        return bundleArr;
    }
}
