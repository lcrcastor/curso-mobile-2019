package me.leolin.shortcutbadger;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import me.leolin.shortcutbadger.impl.AdwHomeBadger;
import me.leolin.shortcutbadger.impl.ApexHomeBadger;
import me.leolin.shortcutbadger.impl.AsusHomeBadger;
import me.leolin.shortcutbadger.impl.DefaultBadger;
import me.leolin.shortcutbadger.impl.EverythingMeHomeBadger;
import me.leolin.shortcutbadger.impl.HuaweiHomeBadger;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;
import me.leolin.shortcutbadger.impl.NovaHomeBadger;
import me.leolin.shortcutbadger.impl.OPPOHomeBader;
import me.leolin.shortcutbadger.impl.SamsungHomeBadger;
import me.leolin.shortcutbadger.impl.SonyHomeBadger;
import me.leolin.shortcutbadger.impl.VivoHomeBadger;
import me.leolin.shortcutbadger.impl.ZTEHomeBadger;
import me.leolin.shortcutbadger.impl.ZukHomeBadger;

public final class ShortcutBadger {
    private static final List<Class<? extends Badger>> a = new LinkedList();
    private static volatile Boolean b;
    private static final Object c = new Object();
    private static Badger d;
    private static ComponentName e;

    static {
        a.add(AdwHomeBadger.class);
        a.add(ApexHomeBadger.class);
        a.add(DefaultBadger.class);
        a.add(NewHtcHomeBadger.class);
        a.add(NovaHomeBadger.class);
        a.add(SonyHomeBadger.class);
        a.add(AsusHomeBadger.class);
        a.add(HuaweiHomeBadger.class);
        a.add(OPPOHomeBader.class);
        a.add(SamsungHomeBadger.class);
        a.add(ZukHomeBadger.class);
        a.add(VivoHomeBadger.class);
        a.add(ZTEHomeBadger.class);
        a.add(EverythingMeHomeBadger.class);
    }

    public static boolean applyCount(Context context, int i) {
        try {
            applyCountOrThrow(context, i);
            return true;
        } catch (ShortcutBadgeException e2) {
            if (Log.isLoggable("ShortcutBadger", 3)) {
                Log.d("ShortcutBadger", "Unable to execute badge", e2);
            }
            return false;
        }
    }

    public static void applyCountOrThrow(Context context, int i) {
        if (d != null || a(context)) {
            try {
                d.executeBadge(context, e, i);
            } catch (Exception e2) {
                throw new ShortcutBadgeException("Unable to execute badge", e2);
            }
        } else {
            throw new ShortcutBadgeException("No default launcher available");
        }
    }

    public static boolean removeCount(Context context) {
        return applyCount(context, 0);
    }

    public static void removeCountOrThrow(Context context) {
        applyCountOrThrow(context, 0);
    }

    public static boolean isBadgeCounterSupported(Context context) {
        if (b == null) {
            synchronized (c) {
                if (b == null) {
                    String str = null;
                    int i = 0;
                    while (true) {
                        if (i >= 3) {
                            break;
                        }
                        String str2 = "ShortcutBadger";
                        try {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Checking if platform supports badge counters, attempt ");
                            sb.append(String.format("%d/%d.", new Object[]{Integer.valueOf(i + 1), Integer.valueOf(3)}));
                            Log.i(str2, sb.toString());
                            if (a(context)) {
                                d.executeBadge(context, e, 0);
                                b = Boolean.valueOf(true);
                                Log.i("ShortcutBadger", "Badge counter is supported in this platform.");
                                break;
                            }
                            str = "Failed to initialize the badge counter.";
                            i++;
                        } catch (Exception e2) {
                            str = e2.getMessage();
                        }
                    }
                    if (b == null) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Badge counter seems not supported for this platform: ");
                        sb2.append(str);
                        Log.w("ShortcutBadger", sb2.toString());
                        b = Boolean.valueOf(false);
                    }
                }
            }
        }
        return b.booleanValue();
    }

    public static void applyNotification(Context context, Notification notification, int i) {
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            try {
                Object obj = notification.getClass().getDeclaredField("extraNotification").get(notification);
                obj.getClass().getDeclaredMethod("setMessageCount", new Class[]{Integer.TYPE}).invoke(obj, new Object[]{Integer.valueOf(i)});
            } catch (Exception e2) {
                if (Log.isLoggable("ShortcutBadger", 3)) {
                    Log.d("ShortcutBadger", "Unable to execute badge", e2);
                }
            }
        }
    }

    private static boolean a(Context context) {
        Badger badger;
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntentForPackage == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to find launch intent for package ");
            sb.append(context.getPackageName());
            Log.e("ShortcutBadger", sb.toString());
            return false;
        }
        e = launchIntentForPackage.getComponent();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 65536)) {
            String str = resolveInfo.activityInfo.packageName;
            Iterator it = a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                try {
                    badger = (Badger) ((Class) it.next()).newInstance();
                } catch (Exception unused) {
                    badger = null;
                }
                if (badger != null && badger.getSupportLaunchers().contains(str)) {
                    d = badger;
                    break;
                }
            }
            if (d != null) {
                break;
            }
        }
        if (d == null) {
            if (Build.MANUFACTURER.equalsIgnoreCase("ZUK")) {
                d = new ZukHomeBadger();
            } else if (Build.MANUFACTURER.equalsIgnoreCase("OPPO")) {
                d = new OPPOHomeBader();
            } else if (Build.MANUFACTURER.equalsIgnoreCase("VIVO")) {
                d = new VivoHomeBadger();
            } else if (Build.MANUFACTURER.equalsIgnoreCase("ZTE")) {
                d = new ZTEHomeBadger();
            } else {
                d = new DefaultBadger();
            }
        }
        return true;
    }

    private ShortcutBadger() {
    }
}
