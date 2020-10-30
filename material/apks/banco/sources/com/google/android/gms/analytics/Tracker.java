package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzad;
import com.google.android.gms.analytics.internal.zzan;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.analytics.internal.zzd;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzmi;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Tracker extends zzd {
    private boolean a;
    private final Map<String, String> b = new HashMap();
    private final Map<String, String> c = new HashMap();
    /* access modifiers changed from: private */
    public final zzad d;
    /* access modifiers changed from: private */
    public final zza e;
    private ExceptionReporter f;
    /* access modifiers changed from: private */
    public zzan g;

    class zza extends zzd implements zza {
        private boolean b;
        private int c;
        private long d = -1;
        private boolean e;
        private long f;

        protected zza(zzf zzf) {
            super(zzf);
        }

        private void c() {
            if (this.d >= 0 || this.b) {
                zzxo().a((zza) Tracker.this.e);
            } else {
                zzxo().b((zza) Tracker.this.e);
            }
        }

        public void a(long j) {
            this.d = j;
            c();
        }

        public void a(Activity activity) {
            if (this.c == 0 && b()) {
                this.e = true;
            }
            this.c++;
            if (this.b) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    Tracker.this.setCampaignParamsOnNextHit(intent.getData());
                }
                HashMap hashMap = new HashMap();
                hashMap.put("&t", "screenview");
                Tracker.this.set("&cd", Tracker.this.g != null ? Tracker.this.g.zzr(activity) : activity.getClass().getCanonicalName());
                if (TextUtils.isEmpty((CharSequence) hashMap.get("&dr"))) {
                    String a2 = Tracker.a(activity);
                    if (!TextUtils.isEmpty(a2)) {
                        hashMap.put("&dr", a2);
                    }
                }
                Tracker.this.send(hashMap);
            }
        }

        public void a(boolean z) {
            this.b = z;
            c();
        }

        public synchronized boolean a() {
            boolean z;
            z = this.e;
            this.e = false;
            return z;
        }

        public void b(Activity activity) {
            this.c--;
            this.c = Math.max(0, this.c);
            if (this.c == 0) {
                this.f = zzaan().elapsedRealtime();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return zzaan().elapsedRealtime() >= this.f + Math.max(1000, this.d);
        }

        /* access modifiers changed from: protected */
        public void zzym() {
        }
    }

    Tracker(zzf zzf, String str, zzad zzad) {
        super(zzf);
        if (str != null) {
            this.b.put("&tid", str);
        }
        this.b.put("useSecure", "1");
        this.b.put("&a", Integer.toString(new Random().nextInt(SubsamplingScaleImageView.TILE_SIZE_AUTO) + 1));
        if (zzad == null) {
            this.d = new zzad("tracking", zzaan());
        } else {
            this.d = zzad;
        }
        this.e = new zza(zzf);
    }

    static String a(Activity activity) {
        zzac.zzy(activity);
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        if (TextUtils.isEmpty(stringExtra)) {
            return null;
        }
        return stringExtra;
    }

    private static void a(Map<String, String> map, Map<String, String> map2) {
        zzac.zzy(map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String b2 = b(entry);
                if (b2 != null) {
                    map2.put(b2, (String) entry.getValue());
                }
            }
        }
    }

    private static boolean a(Entry<String, String> entry) {
        String str = (String) entry.getKey();
        String str2 = (String) entry.getValue();
        return str.startsWith("&") && str.length() >= 2;
    }

    private static String b(Entry<String, String> entry) {
        if (!a(entry)) {
            return null;
        }
        return ((String) entry.getKey()).substring(1);
    }

    private static void b(Map<String, String> map, Map<String, String> map2) {
        zzac.zzy(map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String b2 = b(entry);
                if (b2 != null && !map2.containsKey(b2)) {
                    map2.put(b2, (String) entry.getValue());
                }
            }
        }
    }

    private boolean b() {
        return this.f != null;
    }

    /* access modifiers changed from: 0000 */
    public void a(zzan zzan) {
        zzep("Loading Tracker config values");
        this.g = zzan;
        if (this.g.zzafs()) {
            String trackingId = this.g.getTrackingId();
            set("&tid", trackingId);
            zza("trackingId loaded", trackingId);
        }
        if (this.g.zzaft()) {
            String d2 = Double.toString(this.g.zzafu());
            set("&sf", d2);
            zza("Sample frequency loaded", d2);
        }
        if (this.g.zzafv()) {
            int sessionTimeout = this.g.getSessionTimeout();
            setSessionTimeout((long) sessionTimeout);
            zza("Session timeout loaded", Integer.valueOf(sessionTimeout));
        }
        if (this.g.zzafw()) {
            boolean zzafx = this.g.zzafx();
            enableAutoActivityTracking(zzafx);
            zza("Auto activity tracking loaded", Boolean.valueOf(zzafx));
        }
        if (this.g.zzafy()) {
            boolean zzafz = this.g.zzafz();
            if (zzafz) {
                set("&aip", "1");
            }
            zza("Anonymize ip loaded", Boolean.valueOf(zzafz));
        }
        enableExceptionReporting(this.g.zzaga());
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.a;
    }

    public void enableAdvertisingIdCollection(boolean z) {
        this.a = z;
    }

    public void enableAutoActivityTracking(boolean z) {
        this.e.a(z);
    }

    public void enableExceptionReporting(boolean z) {
        String str;
        synchronized (this) {
            if (b() != z) {
                if (z) {
                    this.f = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), getContext());
                    Thread.setDefaultUncaughtExceptionHandler(this.f);
                    str = "Uncaught exceptions will be reported to Google Analytics";
                } else {
                    Thread.setDefaultUncaughtExceptionHandler(this.f.b());
                    str = "Uncaught exceptions will not be reported to Google Analytics";
                }
                zzep(str);
            }
        }
    }

    public String get(String str) {
        zzaax();
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (this.b.containsKey(str)) {
            return (String) this.b.get(str);
        }
        if (str.equals("&ul")) {
            return zzao.zza(Locale.getDefault());
        }
        if (str.equals("&cid")) {
            return zzaat().zzacm();
        }
        if (str.equals("&sr")) {
            return zzaaw().zzaec();
        }
        if (str.equals("&aid")) {
            return zzaav().zzabu().zzti();
        }
        if (str.equals("&an")) {
            return zzaav().zzabu().zzys();
        }
        if (str.equals("&av")) {
            return zzaav().zzabu().zzyt();
        }
        if (str.equals("&aiid")) {
            return zzaav().zzabu().zzyu();
        }
        return null;
    }

    public void send(Map<String, String> map) {
        final long currentTimeMillis = zzaan().currentTimeMillis();
        if (zzxo().getAppOptOut()) {
            zzeq("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        final boolean isDryRunEnabled = zzxo().isDryRunEnabled();
        final HashMap hashMap = new HashMap();
        a(this.b, hashMap);
        a(map, hashMap);
        final boolean zzi = zzao.zzi((String) this.b.get("useSecure"), true);
        b(this.c, hashMap);
        this.c.clear();
        final String str = (String) hashMap.get("t");
        if (TextUtils.isEmpty(str)) {
            zzaao().zzh(hashMap, "Missing hit type parameter");
            return;
        }
        final String str2 = (String) hashMap.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zzaao().zzh(hashMap, "Missing tracking id parameter");
            return;
        }
        final boolean a2 = a();
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int parseInt = Integer.parseInt((String) this.b.get("&a")) + 1;
                if (parseInt >= Integer.MAX_VALUE) {
                    parseInt = 1;
                }
                this.b.put("&a", Integer.toString(parseInt));
            }
        }
        zzi zzaaq = zzaaq();
        AnonymousClass1 r1 = new Runnable() {
            public void run() {
                if (Tracker.this.e.a()) {
                    hashMap.put("sc", "start");
                }
                zzao.zzd(hashMap, "cid", Tracker.this.zzxo().zzxs());
                String str = (String) hashMap.get("sf");
                if (str != null) {
                    double zza = zzao.zza(str, 100.0d);
                    if (zzao.zza(zza, (String) hashMap.get("cid"))) {
                        Tracker.this.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(zza));
                        return;
                    }
                }
                com.google.android.gms.analytics.internal.zza b2 = Tracker.this.zzaau();
                if (a2) {
                    zzao.zzb(hashMap, "ate", b2.zzzq());
                    zzao.zzc(hashMap, "adid", b2.zzaab());
                } else {
                    hashMap.remove("ate");
                    hashMap.remove("adid");
                }
                zzmi zzabu = Tracker.this.zzaav().zzabu();
                zzao.zzc(hashMap, "an", zzabu.zzys());
                zzao.zzc(hashMap, "av", zzabu.zzyt());
                zzao.zzc(hashMap, "aid", zzabu.zzti());
                zzao.zzc(hashMap, "aiid", zzabu.zzyu());
                hashMap.put("v", "1");
                hashMap.put("_v", zze.aK);
                zzao.zzc(hashMap, "ul", Tracker.this.zzaaw().zzaeb().getLanguage());
                zzao.zzc(hashMap, "sr", Tracker.this.zzaaw().zzaec());
                if ((str.equals("transaction") || str.equals("item")) || Tracker.this.d.zzaev()) {
                    long zzfg = zzao.zzfg((String) hashMap.get("ht"));
                    if (zzfg == 0) {
                        zzfg = currentTimeMillis;
                    }
                    long j = zzfg;
                    if (isDryRunEnabled) {
                        zzab zzab = new zzab(Tracker.this, hashMap, j, zzi);
                        Tracker.this.zzaao().zzc("Dry run enabled. Would have sent hit", zzab);
                        return;
                    }
                    String str2 = (String) hashMap.get("cid");
                    HashMap hashMap = new HashMap();
                    zzao.zza((Map<String, String>) hashMap, "uid", hashMap);
                    zzao.zza((Map<String, String>) hashMap, "an", hashMap);
                    zzao.zza((Map<String, String>) hashMap, "aid", hashMap);
                    zzao.zza((Map<String, String>) hashMap, "av", hashMap);
                    zzao.zza((Map<String, String>) hashMap, "aiid", hashMap);
                    zzh zzh = new zzh(0, str2, str2, !TextUtils.isEmpty((CharSequence) hashMap.get("adid")), 0, hashMap);
                    hashMap.put("_s", String.valueOf(Tracker.this.zzxu().zza(zzh)));
                    zzab zzab2 = new zzab(Tracker.this, hashMap, j, zzi);
                    Tracker.this.zzxu().zza(zzab2);
                    return;
                }
                Tracker.this.zzaao().zzh(hashMap, "Too many hits sent too quickly, rate limiting invoked");
            }
        };
        zzaaq.zzg(r1);
    }

    public void set(String str, String str2) {
        zzac.zzb(str, (Object) "Key should be non-null");
        if (!TextUtils.isEmpty(str)) {
            this.b.put(str, str2);
        }
    }

    public void setAnonymizeIp(boolean z) {
        set("&aip", zzao.zzaw(z));
    }

    public void setAppId(String str) {
        set("&aid", str);
    }

    public void setAppInstallerId(String str) {
        set("&aiid", str);
    }

    public void setAppName(String str) {
        set("&an", str);
    }

    public void setAppVersion(String str) {
        set("&av", str);
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri != null && !uri.isOpaque()) {
            String queryParameter = uri.getQueryParameter("referrer");
            if (!TextUtils.isEmpty(queryParameter)) {
                String str = "http://hostname/?";
                String valueOf = String.valueOf(queryParameter);
                Uri parse = Uri.parse(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                String queryParameter2 = parse.getQueryParameter("utm_id");
                if (queryParameter2 != null) {
                    this.c.put("&ci", queryParameter2);
                }
                String queryParameter3 = parse.getQueryParameter("anid");
                if (queryParameter3 != null) {
                    this.c.put("&anid", queryParameter3);
                }
                String queryParameter4 = parse.getQueryParameter("utm_campaign");
                if (queryParameter4 != null) {
                    this.c.put("&cn", queryParameter4);
                }
                String queryParameter5 = parse.getQueryParameter("utm_content");
                if (queryParameter5 != null) {
                    this.c.put("&cc", queryParameter5);
                }
                String queryParameter6 = parse.getQueryParameter("utm_medium");
                if (queryParameter6 != null) {
                    this.c.put("&cm", queryParameter6);
                }
                String queryParameter7 = parse.getQueryParameter("utm_source");
                if (queryParameter7 != null) {
                    this.c.put("&cs", queryParameter7);
                }
                String queryParameter8 = parse.getQueryParameter("utm_term");
                if (queryParameter8 != null) {
                    this.c.put("&ck", queryParameter8);
                }
                String queryParameter9 = parse.getQueryParameter("dclid");
                if (queryParameter9 != null) {
                    this.c.put("&dclid", queryParameter9);
                }
                String queryParameter10 = parse.getQueryParameter("gclid");
                if (queryParameter10 != null) {
                    this.c.put("&gclid", queryParameter10);
                }
                String queryParameter11 = parse.getQueryParameter("aclid");
                if (queryParameter11 != null) {
                    this.c.put("&aclid", queryParameter11);
                }
            }
        }
    }

    public void setClientId(String str) {
        set("&cid", str);
    }

    public void setEncoding(String str) {
        set("&de", str);
    }

    public void setHostname(String str) {
        set("&dh", str);
    }

    public void setLanguage(String str) {
        set("&ul", str);
    }

    public void setLocation(String str) {
        set("&dl", str);
    }

    public void setPage(String str) {
        set("&dp", str);
    }

    public void setReferrer(String str) {
        set("&dr", str);
    }

    public void setSampleRate(double d2) {
        set("&sf", Double.toString(d2));
    }

    public void setScreenColors(String str) {
        set("&sd", str);
    }

    public void setScreenName(String str) {
        set("&cd", str);
    }

    public void setScreenResolution(int i, int i2) {
        if (i >= 0 || i2 >= 0) {
            StringBuilder sb = new StringBuilder(23);
            sb.append(i);
            sb.append("x");
            sb.append(i2);
            set("&sr", sb.toString());
            return;
        }
        zzes("Invalid width or height. The values should be non-negative.");
    }

    public void setSessionTimeout(long j) {
        this.e.a(j * 1000);
    }

    public void setTitle(String str) {
        set("&dt", str);
    }

    public void setUseSecure(boolean z) {
        set("useSecure", zzao.zzaw(z));
    }

    public void setViewportSize(String str) {
        set("&vp", str);
    }

    /* access modifiers changed from: protected */
    public void zzym() {
        this.e.initialize();
        String zzys = zzxv().zzys();
        if (zzys != null) {
            set("&an", zzys);
        }
        String zzyt = zzxv().zzyt();
        if (zzyt != null) {
            set("&av", zzyt);
        }
    }
}
