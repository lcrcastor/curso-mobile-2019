package com.grab.Grab;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.util.Log;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Grab_OpenUDID_manager implements ServiceConnection {
    public static final String PREFS_NAME = "openudid_prefs";
    public static final String PREF_KEY = "openudid";
    public static final String TAG = "OpenUDID";
    private static String f = null;
    private static boolean g = false;
    private final Context a;
    private List<ResolveInfo> b;
    /* access modifiers changed from: private */
    public Map<String, Integer> c = new HashMap();
    private final SharedPreferences d;
    private final Random e = new Random();

    class ValueComparator implements Comparator {
        private ValueComparator() {
        }

        public int compare(Object obj, Object obj2) {
            if (((Integer) Grab_OpenUDID_manager.this.c.get(obj)).intValue() < ((Integer) Grab_OpenUDID_manager.this.c.get(obj2)).intValue()) {
                return 1;
            }
            return Grab_OpenUDID_manager.this.c.get(obj) == Grab_OpenUDID_manager.this.c.get(obj2) ? 0 : -1;
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }

    private Grab_OpenUDID_manager(Context context) {
        this.d = context.getSharedPreferences(PREFS_NAME, 0);
        this.a = context;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            Parcel obtain = Parcel.obtain();
            obtain.writeInt(this.e.nextInt());
            Parcel obtain2 = Parcel.obtain();
            iBinder.transact(1, Parcel.obtain(), obtain2, 0);
            if (obtain.readInt() == obtain2.readInt()) {
                String readString = obtain2.readString();
                if (readString != null) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Received ");
                    sb.append(readString);
                    Log.d(str, sb.toString());
                    if (this.c.containsKey(readString)) {
                        this.c.put(readString, Integer.valueOf(((Integer) this.c.get(readString)).intValue() + 1));
                    } else {
                        this.c.put(readString, Integer.valueOf(1));
                    }
                }
            }
        } catch (RemoteException e2) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("RemoteException: ");
            sb2.append(e2.getMessage());
            Log.e(str2, sb2.toString());
        }
        this.a.unbindService(this);
        c();
    }

    private void a() {
        Editor edit = this.d.edit();
        edit.putString(PREF_KEY, f);
        edit.commit();
    }

    private void b() {
        Log.d(TAG, "Generating openUDID");
        f = Secure.getString(this.a.getContentResolver(), "android_id");
        if (f == null || f.equals("9774d56d682e549c") || f.length() < 15) {
            f = new BigInteger(64, new SecureRandom()).toString(16);
        }
    }

    private void c() {
        if (this.b.size() > 0) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Trying service ");
            sb.append(((ResolveInfo) this.b.get(0)).loadLabel(this.a.getPackageManager()));
            Log.d(str, sb.toString());
            ServiceInfo serviceInfo = ((ResolveInfo) this.b.get(0)).serviceInfo;
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(serviceInfo.applicationInfo.packageName, serviceInfo.name));
            this.b.remove(0);
            try {
                this.a.bindService(intent, this, 1);
            } catch (SecurityException unused) {
                c();
            }
        } else {
            d();
            if (f == null) {
                b();
            }
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("OpenUDID: ");
            sb2.append(f);
            Log.d(str2, sb2.toString());
            a();
            g = true;
        }
    }

    private void d() {
        if (!this.c.isEmpty()) {
            TreeMap treeMap = new TreeMap(new ValueComparator());
            treeMap.putAll(this.c);
            f = (String) treeMap.firstKey();
        }
    }

    public static String getOpenUDID() {
        if (!g) {
            Log.e(TAG, "Initialisation isn't done");
        }
        return f;
    }

    public static boolean isInitialized() {
        return g;
    }

    public static void sync(Context context) {
        Grab_OpenUDID_manager grab_OpenUDID_manager = new Grab_OpenUDID_manager(context);
        f = grab_OpenUDID_manager.d.getString(PREF_KEY, null);
        if (f == null) {
            grab_OpenUDID_manager.b = context.getPackageManager().queryIntentServices(new Intent("org.OpenUDID.GETUDID"), 0);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append(grab_OpenUDID_manager.b.size());
            sb.append(" services matches OpenUDID");
            Log.d(str, sb.toString());
            if (grab_OpenUDID_manager.b != null) {
                grab_OpenUDID_manager.c();
                return;
            }
            return;
        }
        String str2 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("OpenUDID: ");
        sb2.append(f);
        Log.d(str2, sb2.toString());
        g = true;
    }
}
