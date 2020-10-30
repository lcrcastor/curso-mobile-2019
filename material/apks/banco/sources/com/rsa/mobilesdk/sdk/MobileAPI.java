package com.rsa.mobilesdk.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.support.v4.content.ContextCompat;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager.RESPONSE_CODES;
import com.rsa.mobilesdk.sdk.DeviceInfo.WiFiNetworksData;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class MobileAPI {
    public static final String ADD_TIMESTAMP_KEY = "Add-timestamp-key";
    public static final int BEST_LOCATION_AGE_MINUTES_DEFAULT_VALUE = 3;
    public static final String BEST_LOCATION_AGE_MINUTES_KEY = "Best-location-age-key";
    public static final int COLLECT_ALL_DEVICE_DATA_AND_LOCATION = 2;
    public static final int COLLECT_BASIC_DEVICE_DATA_ONLY = 0;
    public static final int COLLECT_DEVICE_DATA_ONLY = 1;
    public static final int CONFIGURATION_DEFAULT_VALUE = 0;
    public static final String CONFIGURATION_KEY = "Configuration-key";
    public static final int MAX_ACCURACY_DEFAULT_VALUE = 100;
    public static final String MAX_ACCURACY_KEY = "Max-accuracy-key";
    public static final int MAX_CUSTOM_STRING_LENGTH = 1024;
    public static final int MAX_LOCATION_AGE_DAYS_DEFAULT_VALUE = 2;
    public static final String MAX_LOCATION_AGE_DAYS_KEY = "Max-location-age-key";
    public static final int SILENT_PERIOD_DEFAULT_VALUE = 3;
    public static final String SILENT_PERIOD_MINUTES_KEY = "Silent-period-key";
    public static final int TIMEOUT_DEFAULT_VALUE = 2;
    public static final String TIMEOUT_MINUTES_KEY = "Timeout-key";
    private static MobileAPI d;
    private LooperStatus a = LooperStatus.LooperMissing;
    private final TelephonyManager b;
    private final Context c;
    private int e;
    private int f;
    /* access modifiers changed from: private */
    public DeviceInfo g;
    private LocationRetriever h;
    private EmulatorDetection i;
    private int j = 0;
    /* access modifiers changed from: private */
    public int[] k = new int[23];
    private boolean l = true;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private HashMap<String, Object> r = new HashMap<>();

    public enum CustomElementType {
        BOOLEAN,
        INT,
        FLOAT,
        STRING
    }

    enum LooperStatus {
        LooperExist,
        LooperCreated,
        LooperMissing
    }

    public static synchronized MobileAPI getInstance(Activity activity) {
        MobileAPI mobileAPI;
        synchronized (MobileAPI.class) {
            if (activity != null) {
                try {
                    if (d == null) {
                        d = new MobileAPI(activity);
                    }
                } finally {
                }
            }
            mobileAPI = d;
        }
        return mobileAPI;
    }

    @SuppressLint({"NewApi"})
    private MobileAPI(Activity activity) {
        this.c = activity.getApplicationContext();
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        if (defaultDisplay == null) {
            this.e = 0;
            this.f = 0;
        } else if (VERSION.SDK_INT >= 13) {
            Point point = new Point();
            defaultDisplay.getSize(point);
            this.e = point.x;
            this.f = point.y;
        } else {
            this.e = defaultDisplay.getWidth();
            this.f = defaultDisplay.getHeight();
        }
        this.b = (TelephonyManager) this.c.getSystemService("phone");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0075, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void initSDK(java.util.Properties r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "MobileAPI"
            java.lang.String r1 = "initSDK"
            android.util.Log.i(r0, r1)     // Catch:{ all -> 0x0076 }
            com.rsa.mobilesdk.sdk.DeviceInfo r0 = r3.g     // Catch:{ all -> 0x0076 }
            if (r0 == 0) goto L_0x0015
            java.lang.String r4 = "MobileAPI"
            java.lang.String r0 = "SDK already initialized"
            android.util.Log.w(r4, r0)     // Catch:{ all -> 0x0076 }
            monitor-exit(r3)
            return
        L_0x0015:
            android.os.Looper r0 = android.os.Looper.myLooper()     // Catch:{ all -> 0x0076 }
            if (r0 != 0) goto L_0x0023
            android.os.Looper.prepare()     // Catch:{ all -> 0x0076 }
            com.rsa.mobilesdk.sdk.MobileAPI$LooperStatus r0 = com.rsa.mobilesdk.sdk.MobileAPI.LooperStatus.LooperCreated     // Catch:{ all -> 0x0076 }
            r3.a = r0     // Catch:{ all -> 0x0076 }
            goto L_0x0027
        L_0x0023:
            com.rsa.mobilesdk.sdk.MobileAPI$LooperStatus r0 = com.rsa.mobilesdk.sdk.MobileAPI.LooperStatus.LooperExist     // Catch:{ all -> 0x0076 }
            r3.a = r0     // Catch:{ all -> 0x0076 }
        L_0x0027:
            if (r4 != 0) goto L_0x0040
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0076 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0076 }
            r1.<init>()     // Catch:{ all -> 0x0076 }
            java.lang.String r2 = "Invalid parameter: "
            r1.append(r2)     // Catch:{ all -> 0x0076 }
            r1.append(r4)     // Catch:{ all -> 0x0076 }
            java.lang.String r4 = r1.toString()     // Catch:{ all -> 0x0076 }
            r0.<init>(r4)     // Catch:{ all -> 0x0076 }
            throw r0     // Catch:{ all -> 0x0076 }
        L_0x0040:
            r3.a(r4)     // Catch:{ all -> 0x0076 }
            com.rsa.mobilesdk.sdk.DeviceInfo r0 = new com.rsa.mobilesdk.sdk.DeviceInfo     // Catch:{ all -> 0x0076 }
            int r1 = r3.j     // Catch:{ all -> 0x0076 }
            r0.<init>(r1)     // Catch:{ all -> 0x0076 }
            r3.g = r0     // Catch:{ all -> 0x0076 }
            r0 = 0
        L_0x004d:
            int[] r1 = r3.k     // Catch:{ all -> 0x0076 }
            int r1 = r1.length     // Catch:{ all -> 0x0076 }
            if (r0 >= r1) goto L_0x005a
            int[] r1 = r3.k     // Catch:{ all -> 0x0076 }
            r2 = 2
            r1[r0] = r2     // Catch:{ all -> 0x0076 }
            int r0 = r0 + 1
            goto L_0x004d
        L_0x005a:
            int r0 = r3.j     // Catch:{ all -> 0x0076 }
            switch(r0) {
                case 0: goto L_0x0071;
                case 1: goto L_0x006a;
                case 2: goto L_0x0060;
                default: goto L_0x005f;
            }     // Catch:{ all -> 0x0076 }
        L_0x005f:
            goto L_0x0074
        L_0x0060:
            r3.t()     // Catch:{ all -> 0x0076 }
            r3.u()     // Catch:{ all -> 0x0076 }
            r3.b(r4)     // Catch:{ all -> 0x0076 }
            goto L_0x0074
        L_0x006a:
            r3.t()     // Catch:{ all -> 0x0076 }
            r3.u()     // Catch:{ all -> 0x0076 }
            goto L_0x0074
        L_0x0071:
            r3.t()     // Catch:{ all -> 0x0076 }
        L_0x0074:
            monitor-exit(r3)
            return
        L_0x0076:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rsa.mobilesdk.sdk.MobileAPI.initSDK(java.util.Properties):void");
    }

    private void a(Properties properties) {
        this.j = Utils.a(properties, CONFIGURATION_KEY, 0);
        if (this.j < 0 || this.j > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid configuration key: ");
            sb.append(this.j);
            throw new IllegalArgumentException(sb.toString());
        }
        this.l = Utils.a(properties, ADD_TIMESTAMP_KEY, false);
        if (this.j == 2) {
            this.m = Utils.a(properties, TIMEOUT_MINUTES_KEY, 2);
            if (this.m < 1 || this.m > 4) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("invalid TIMEOUT_MINUTES_KEY: ");
                sb2.append(this.m);
                throw new IllegalArgumentException(sb2.toString());
            }
            this.n = Utils.a(properties, SILENT_PERIOD_MINUTES_KEY, 3);
            if (this.n < 1 || this.n > 60) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("invalid SILENT_PERIOD_MINUTES_KEY: ");
                sb3.append(this.n);
                throw new IllegalArgumentException(sb3.toString());
            }
            this.o = Utils.a(properties, BEST_LOCATION_AGE_MINUTES_KEY, 3);
            if (this.o < 2 || this.o > 4) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("invalid BEST_LOCATION_AGE_MINUTES_KEY: ");
                sb4.append(this.o);
                throw new IllegalArgumentException(sb4.toString());
            }
            this.p = Utils.a(properties, MAX_LOCATION_AGE_DAYS_KEY, 2);
            if (this.p < 1 || this.p > 3) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("invalid MAX_LOCATION_AGE_DAYS_KEY: ");
                sb5.append(this.p);
                throw new IllegalArgumentException(sb5.toString());
            }
            this.q = Utils.a(properties, MAX_ACCURACY_KEY, 100);
            if (this.q < 50 || this.q > 200) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append("invalid MAX_ACCURACY_KEY: ");
                sb6.append(this.q);
                throw new IllegalArgumentException(sb6.toString());
            }
        }
    }

    private void t() {
        this.i = new EmulatorDetection(this.c);
        this.g.a = new Date();
        this.g.b = a();
        this.g.c = b();
        this.g.d = c();
        this.g.q = r();
        this.g.u = n();
        this.g.v = o();
    }

    private void b(Properties properties) {
        if (this.h == null) {
            this.h = new LocationRetriever();
        }
        this.h.a(this.c, (long) this.m, (long) this.n, (long) this.o, (long) this.p, this.q, this.g.e, new OnLocationDataChangedListener() {
            public void a() {
                MobileAPI.this.k[4] = MobileAPI.this.g.e.p;
            }
        });
        a(this.g.m);
        this.g.s = g();
        this.g.t = h();
        this.g.n = p();
        this.g.o = q();
    }

    private void u() {
        this.g.f = i();
        this.g.g = j();
        this.g.h = k();
        this.g.i = l();
        this.g.j = m();
        this.g.p = d();
        this.g.k = e();
        this.g.l = f();
        this.g.w = s();
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        String str;
        this.k[1] = -1;
        try {
            if (ContextCompat.checkSelfPermission(this.c, "android.permission.READ_PHONE_STATE") == 0) {
                str = this.b.getDeviceId();
                this.k[1] = 0;
            } else {
                this.k[1] = 1;
                str = RESPONSE_CODES.ERROR;
            }
            return str;
        } catch (SecurityException e2) {
            this.k[1] = 1;
            Log.e("MobileAPI", e2.toString());
            return RESPONSE_CODES.ERROR;
        }
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        this.k[2] = -1;
        try {
            String subscriberId = this.b.getSubscriberId();
            this.k[2] = 0;
            return subscriberId;
        } catch (SecurityException e2) {
            this.k[2] = 1;
            Log.e("MobileAPI", e2.toString());
            return RESPONSE_CODES.ERROR;
        }
    }

    /* access modifiers changed from: 0000 */
    public String c() {
        this.k[3] = -1;
        try {
            String line1Number = this.b.getLine1Number();
            this.k[3] = 0;
            return line1Number;
        } catch (SecurityException e2) {
            this.k[3] = 1;
            Log.e("MobileAPI", e2.toString());
            return RESPONSE_CODES.ERROR;
        }
    }

    /* access modifiers changed from: 0000 */
    public String d() {
        this.k[15] = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.e));
        sb.append("x");
        sb.append(Integer.toString(this.f));
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String e() {
        this.k[10] = -1;
        try {
            String lowerCase = Locale.getDefault().getLanguage().toLowerCase();
            if (lowerCase.equals("iw")) {
                lowerCase = "he";
            } else if (lowerCase.equals("in")) {
                lowerCase = "id";
            } else if (lowerCase.equals("ji")) {
                lowerCase = "yi";
            }
            this.k[10] = 0;
            return lowerCase;
        } catch (SecurityException e2) {
            this.k[10] = 1;
            Log.e("MobileAPI", e2.toString());
            return RESPONSE_CODES.ERROR;
        }
    }

    /* access modifiers changed from: 0000 */
    public String f() {
        this.k[11] = -1;
        try {
            String macAddress = ((WifiManager) this.c.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            this.k[11] = 0;
            return macAddress;
        } catch (SecurityException e2) {
            this.k[11] = 1;
            Log.e("MobileAPI", e2.toString());
            return RESPONSE_CODES.ERROR;
        }
    }

    /* access modifiers changed from: 0000 */
    public String g() {
        this.k[18] = -1;
        try {
            String networkOperator = this.b.getNetworkOperator();
            this.k[18] = 0;
            if (networkOperator != null && networkOperator.length() >= 3) {
                return networkOperator.substring(0, 3);
            }
            int i2 = this.c.getResources().getConfiguration().mcc;
            if (i2 == 0) {
                return null;
            }
            return Integer.toString(i2);
        } catch (SecurityException e2) {
            this.k[18] = 1;
            Log.e("MobileAPI", e2.toString());
            return RESPONSE_CODES.ERROR;
        }
    }

    /* access modifiers changed from: 0000 */
    public String h() {
        this.k[19] = -1;
        try {
            String networkOperator = this.b.getNetworkOperator();
            this.k[19] = 0;
            if (networkOperator != null && networkOperator.length() >= 3) {
                return networkOperator.substring(3);
            }
            int i2 = this.c.getResources().getConfiguration().mnc;
            if (i2 == 0) {
                return null;
            }
            return Integer.toString(i2);
        } catch (SecurityException e2) {
            this.k[19] = 1;
            Log.e("MobileAPI", e2.toString());
            return RESPONSE_CODES.ERROR;
        }
    }

    /* access modifiers changed from: 0000 */
    public String i() {
        this.k[5] = 0;
        return Build.MODEL;
    }

    /* access modifiers changed from: 0000 */
    public boolean j() {
        this.k[6] = 0;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public String k() {
        this.k[7] = 0;
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                String name = defaultAdapter.getName();
                if (!TextUtils.isEmpty(name)) {
                    return name;
                }
            }
        } catch (SecurityException unused) {
        }
        String str = Build.DEVICE;
        if (!str.equals(i())) {
            return str;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public String l() {
        this.k[8] = 0;
        return Constants.CURRENT_SO;
    }

    /* access modifiers changed from: 0000 */
    public String m() {
        this.k[9] = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(VERSION.SDK_INT);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public int n() {
        this.k[21] = 0;
        return RootedDeviceChecker.a(this.c);
    }

    /* access modifiers changed from: 0000 */
    public int o() {
        this.k[22] = 0;
        return this.i.isEmulator();
    }

    /* access modifiers changed from: 0000 */
    public String a(WiFiNetworksData wiFiNetworksData) {
        this.k[12] = -1;
        try {
            wiFiNetworksData.set(((WifiManager) this.c.getSystemService("wifi")).getConnectionInfo());
            this.k[12] = 0;
            return wiFiNetworksData.toString();
        } catch (SecurityException e2) {
            this.k[12] = 1;
            wiFiNetworksData.setNoPermission();
            Log.e("MobileAPI", e2.toString());
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public String p() {
        this.k[13] = -1;
        try {
            CellLocation cellLocation = this.b.getCellLocation();
            String str = null;
            if (cellLocation instanceof GsmCellLocation) {
                str = Integer.toString(((GsmCellLocation) cellLocation).getCid());
            } else if (cellLocation instanceof CdmaCellLocation) {
                str = Integer.toString(((CdmaCellLocation) cellLocation).getBaseStationId());
            }
            this.k[13] = 0;
            return str;
        } catch (SecurityException e2) {
            this.k[13] = 1;
            Log.e("MobileAPI", e2.toString());
            return RESPONSE_CODES.ERROR;
        }
    }

    /* access modifiers changed from: 0000 */
    public String q() {
        this.k[14] = -1;
        try {
            CellLocation cellLocation = this.b.getCellLocation();
            if (!(cellLocation instanceof GsmCellLocation)) {
                return null;
            }
            this.k[14] = 0;
            return Integer.toString(((GsmCellLocation) cellLocation).getLac());
        } catch (SecurityException e2) {
            this.k[14] = 1;
            Log.e("MobileAPI", e2.toString());
            return RESPONSE_CODES.ERROR;
        }
    }

    /* access modifiers changed from: 0000 */
    public String r() {
        return ApplicationKey.getApplicationKey(this.c);
    }

    /* access modifiers changed from: 0000 */
    public String s() {
        this.k[20] = 0;
        return Secure.getString(this.c.getContentResolver(), "android_id");
    }

    public synchronized String collectInfo() {
        Log.i("MobileAPI", "collectInfo");
        if (this.g == null) {
            throw new IllegalStateException("Mobile SDK was not initialized");
        }
        return this.g.a(this.l, this.r);
    }

    public synchronized void destroy() {
        Log.i("MobileAPI", "destroy");
        x();
        w();
        v();
    }

    private void v() {
        if (this.a.equals(LooperStatus.LooperCreated)) {
            Looper.myLooper().quit();
            this.a = LooperStatus.LooperMissing;
        }
    }

    private void w() {
        if (this.g != null) {
            this.g.a();
            this.g = null;
        }
        this.r.clear();
        if (this.i != null) {
            this.i = null;
        }
    }

    private void x() {
        if (this.h != null) {
            this.h.a();
            this.h = null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0037, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean addCustomElement(com.rsa.mobilesdk.sdk.MobileAPI.CustomElementType r3, java.lang.String r4, java.lang.Object r5) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r5 instanceof java.lang.Integer     // Catch:{ all -> 0x004c }
            r1 = 0
            if (r0 == 0) goto L_0x000c
            com.rsa.mobilesdk.sdk.MobileAPI$CustomElementType r0 = com.rsa.mobilesdk.sdk.MobileAPI.CustomElementType.INT     // Catch:{ all -> 0x004c }
            if (r3 == r0) goto L_0x0042
            monitor-exit(r2)
            return r1
        L_0x000c:
            boolean r0 = r5 instanceof java.lang.Boolean     // Catch:{ all -> 0x004c }
            if (r0 == 0) goto L_0x0016
            com.rsa.mobilesdk.sdk.MobileAPI$CustomElementType r0 = com.rsa.mobilesdk.sdk.MobileAPI.CustomElementType.BOOLEAN     // Catch:{ all -> 0x004c }
            if (r3 == r0) goto L_0x0042
            monitor-exit(r2)
            return r1
        L_0x0016:
            boolean r0 = r5 instanceof java.lang.String     // Catch:{ all -> 0x004c }
            if (r0 == 0) goto L_0x0038
            com.rsa.mobilesdk.sdk.MobileAPI$CustomElementType r0 = com.rsa.mobilesdk.sdk.MobileAPI.CustomElementType.STRING     // Catch:{ all -> 0x004c }
            if (r3 == r0) goto L_0x0020
            monitor-exit(r2)
            return r1
        L_0x0020:
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x004c }
            int r3 = r3.length()     // Catch:{ all -> 0x004c }
            r0 = 1024(0x400, float:1.435E-42)
            if (r3 > r0) goto L_0x0036
            java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x004c }
            int r3 = r3.length()     // Catch:{ all -> 0x004c }
            if (r3 <= r0) goto L_0x0042
        L_0x0036:
            monitor-exit(r2)
            return r1
        L_0x0038:
            boolean r0 = r5 instanceof java.lang.Float     // Catch:{ all -> 0x004c }
            if (r0 == 0) goto L_0x004a
            com.rsa.mobilesdk.sdk.MobileAPI$CustomElementType r0 = com.rsa.mobilesdk.sdk.MobileAPI.CustomElementType.FLOAT     // Catch:{ all -> 0x004c }
            if (r3 == r0) goto L_0x0042
            monitor-exit(r2)
            return r1
        L_0x0042:
            java.util.HashMap<java.lang.String, java.lang.Object> r3 = r2.r     // Catch:{ all -> 0x004c }
            r3.put(r4, r5)     // Catch:{ all -> 0x004c }
            r3 = 1
            monitor-exit(r2)
            return r3
        L_0x004a:
            monitor-exit(r2)
            return r1
        L_0x004c:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rsa.mobilesdk.sdk.MobileAPI.addCustomElement(com.rsa.mobilesdk.sdk.MobileAPI$CustomElementType, java.lang.String, java.lang.Object):boolean");
    }
}
