package com.facebook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import bolts.AppLinks;
import com.facebook.Request.Callback;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppEventsLogger {
    public static final String ACTION_APP_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_FLUSHED";
    public static final String APP_EVENTS_EXTRA_FLUSH_RESULT = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT";
    public static final String APP_EVENTS_EXTRA_NUM_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED";
    /* access modifiers changed from: private */
    public static final String a = AppEventsLogger.class.getCanonicalName();
    /* access modifiers changed from: private */
    public static Map<AccessTokenAppIdPair, SessionEventsState> d = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static ScheduledThreadPoolExecutor e;
    private static FlushBehavior f = FlushBehavior.AUTO;
    private static boolean g;
    /* access modifiers changed from: private */
    public static Context h;
    /* access modifiers changed from: private */
    public static Object i = new Object();
    private static String j;
    private static String k;
    private static boolean l;
    private final Context b;
    private final AccessTokenAppIdPair c;

    static class AccessTokenAppIdPair implements Serializable {
        private static final long serialVersionUID = 1;
        private final String a;
        private final String b;

        static class SerializationProxyV1 implements Serializable {
            private static final long serialVersionUID = -2488473066578201069L;
            private final String a;
            private final String b;

            private SerializationProxyV1(String str, String str2) {
                this.a = str;
                this.b = str2;
            }

            private Object readResolve() {
                return new AccessTokenAppIdPair(this.a, this.b);
            }
        }

        AccessTokenAppIdPair(Session session) {
            this(session.getAccessToken(), session.getApplicationId());
        }

        AccessTokenAppIdPair(String str, String str2) {
            if (Utility.isNullOrEmpty(str)) {
                str = null;
            }
            this.a = str;
            this.b = str2;
        }

        /* access modifiers changed from: 0000 */
        public String a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public String b() {
            return this.b;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.a == null ? 0 : this.a.hashCode();
            if (this.b != null) {
                i = this.b.hashCode();
            }
            return hashCode ^ i;
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof AccessTokenAppIdPair)) {
                return false;
            }
            AccessTokenAppIdPair accessTokenAppIdPair = (AccessTokenAppIdPair) obj;
            if (Utility.areObjectsEqual(accessTokenAppIdPair.a, this.a) && Utility.areObjectsEqual(accessTokenAppIdPair.b, this.b)) {
                z = true;
            }
            return z;
        }

        private Object writeReplace() {
            return new SerializationProxyV1(this.a, this.b);
        }
    }

    static class AppEvent implements Serializable {
        private static final HashSet<String> c = new HashSet<>();
        private static final long serialVersionUID = 1;
        private JSONObject a;
        private boolean b;
        private String d;

        static class SerializationProxyV1 implements Serializable {
            private static final long serialVersionUID = -2488473066578201069L;
            private final String a;
            private final boolean b;

            private SerializationProxyV1(String str, boolean z) {
                this.a = str;
                this.b = z;
            }

            private Object readResolve() {
                return new AppEvent(this.a, this.b);
            }
        }

        public AppEvent(Context context, String str, Double d2, Bundle bundle, boolean z) {
            try {
                a(str);
                this.d = str;
                this.b = z;
                this.a = new JSONObject();
                this.a.put("_eventName", str);
                this.a.put("_logTime", System.currentTimeMillis() / 1000);
                this.a.put("_ui", Utility.getActivityName(context));
                if (d2 != null) {
                    this.a.put("_valueToSum", d2.doubleValue());
                }
                if (this.b) {
                    this.a.put("_implicitlyLogged", "1");
                }
                String appVersion = Settings.getAppVersion();
                if (appVersion != null) {
                    this.a.put("_appVersion", appVersion);
                }
                if (bundle != null) {
                    for (String str2 : bundle.keySet()) {
                        a(str2);
                        Object obj = bundle.get(str2);
                        if ((obj instanceof String) || (obj instanceof Number)) {
                            this.a.put(str2, obj.toString());
                        } else {
                            throw new FacebookException(String.format("Parameter value '%s' for key '%s' should be a string or a numeric type.", new Object[]{obj, str2}));
                        }
                    }
                }
                if (!this.b) {
                    Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Created app event '%s'", this.a.toString());
                }
            } catch (JSONException e) {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "JSON encoding for app event failed: '%s'", e.toString());
                this.a = null;
            } catch (FacebookException e2) {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Invalid app event name or parameter:", e2.toString());
                this.a = null;
            }
        }

        private AppEvent(String str, boolean z) {
            this.a = new JSONObject(str);
            this.b = z;
        }

        public boolean a() {
            return this.b;
        }

        public JSONObject b() {
            return this.a;
        }

        private void a(String str) {
            boolean contains;
            if (str == null || str.length() == 0 || str.length() > 40) {
                if (str == null) {
                    str = "<None Provided>";
                }
                throw new FacebookException(String.format("Identifier '%s' must be less than %d characters", new Object[]{str, Integer.valueOf(40)}));
            }
            synchronized (c) {
                contains = c.contains(str);
            }
            if (contains) {
                return;
            }
            if (str.matches("^[0-9a-zA-Z_]+[0-9a-zA-Z _-]*$")) {
                synchronized (c) {
                    c.add(str);
                }
                return;
            }
            throw new FacebookException(String.format("Skipping event named '%s' due to illegal name - must be under 40 chars and alphanumeric, _, - or space, and not start with a space or hyphen.", new Object[]{str}));
        }

        private Object writeReplace() {
            return new SerializationProxyV1(this.a.toString(), this.b);
        }

        public String toString() {
            return String.format("\"%s\", implicit: %b, json: %s", new Object[]{this.a.optString("_eventName"), Boolean.valueOf(this.b), this.a.toString()});
        }
    }

    public enum FlushBehavior {
        AUTO,
        EXPLICIT_ONLY
    }

    enum FlushReason {
        EXPLICIT,
        TIMER,
        SESSION_CHANGE,
        PERSISTED_EVENTS,
        EVENT_THRESHOLD,
        EAGER_FLUSHING_EVENT
    }

    enum FlushResult {
        SUCCESS,
        SERVER_ERROR,
        NO_CONNECTIVITY,
        UNKNOWN_ERROR
    }

    static class FlushStatistics {
        public int a;
        public FlushResult b;

        private FlushStatistics() {
            this.a = 0;
            this.b = FlushResult.SUCCESS;
        }
    }

    static class PersistedAppSessionInfo {
        private static final Object a = new Object();
        private static boolean b = false;
        private static boolean c = false;
        private static Map<AccessTokenAppIdPair, FacebookTimeSpentData> d;
        private static final Runnable e = new Runnable() {
            public void run() {
                PersistedAppSessionInfo.a(AppEventsLogger.h);
            }
        };

        PersistedAppSessionInfo() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:26:0x0072 A[Catch:{ all -> 0x007c }] */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x0089 A[Catch:{ all -> 0x007c }] */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x00a2 A[Catch:{ all -> 0x007c }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static void b(android.content.Context r9) {
            /*
                java.lang.Object r0 = a
                monitor-enter(r0)
                boolean r1 = c     // Catch:{ all -> 0x00ae }
                if (r1 != 0) goto L_0x00ac
                r1 = 0
                r2 = 1
                r3 = 0
                java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch:{ FileNotFoundException -> 0x0095, Exception -> 0x0046, all -> 0x0041 }
                java.lang.String r5 = "AppEventsLogger.persistedsessioninfo"
                java.io.FileInputStream r5 = r9.openFileInput(r5)     // Catch:{ FileNotFoundException -> 0x0095, Exception -> 0x0046, all -> 0x0041 }
                r4.<init>(r5)     // Catch:{ FileNotFoundException -> 0x0095, Exception -> 0x0046, all -> 0x0041 }
                java.lang.Object r3 = r4.readObject()     // Catch:{ FileNotFoundException -> 0x0096, Exception -> 0x003f }
                java.util.HashMap r3 = (java.util.HashMap) r3     // Catch:{ FileNotFoundException -> 0x0096, Exception -> 0x003f }
                d = r3     // Catch:{ FileNotFoundException -> 0x0096, Exception -> 0x003f }
                com.facebook.LoggingBehavior r3 = com.facebook.LoggingBehavior.APP_EVENTS     // Catch:{ FileNotFoundException -> 0x0096, Exception -> 0x003f }
                java.lang.String r5 = "AppEvents"
                java.lang.String r6 = "App session info loaded"
                com.facebook.internal.Logger.log(r3, r5, r6)     // Catch:{ FileNotFoundException -> 0x0096, Exception -> 0x003f }
                com.facebook.internal.Utility.closeQuietly(r4)     // Catch:{ all -> 0x00ae }
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                r9.deleteFile(r3)     // Catch:{ all -> 0x00ae }
                java.util.Map<com.facebook.AppEventsLogger$AccessTokenAppIdPair, com.facebook.FacebookTimeSpentData> r9 = d     // Catch:{ all -> 0x00ae }
                if (r9 != 0) goto L_0x0039
                java.util.HashMap r9 = new java.util.HashMap     // Catch:{ all -> 0x00ae }
                r9.<init>()     // Catch:{ all -> 0x00ae }
                d = r9     // Catch:{ all -> 0x00ae }
            L_0x0039:
                c = r2     // Catch:{ all -> 0x00ae }
            L_0x003b:
                b = r1     // Catch:{ all -> 0x00ae }
                goto L_0x00ac
            L_0x003f:
                r3 = move-exception
                goto L_0x004a
            L_0x0041:
                r4 = move-exception
                r8 = r4
                r4 = r3
                r3 = r8
                goto L_0x007d
            L_0x0046:
                r4 = move-exception
                r8 = r4
                r4 = r3
                r3 = r8
            L_0x004a:
                java.lang.String r5 = com.facebook.AppEventsLogger.a     // Catch:{ all -> 0x007c }
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x007c }
                r6.<init>()     // Catch:{ all -> 0x007c }
                java.lang.String r7 = "Got unexpected exception: "
                r6.append(r7)     // Catch:{ all -> 0x007c }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x007c }
                r6.append(r3)     // Catch:{ all -> 0x007c }
                java.lang.String r3 = r6.toString()     // Catch:{ all -> 0x007c }
                android.util.Log.d(r5, r3)     // Catch:{ all -> 0x007c }
                com.facebook.internal.Utility.closeQuietly(r4)     // Catch:{ all -> 0x00ae }
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                r9.deleteFile(r3)     // Catch:{ all -> 0x00ae }
                java.util.Map<com.facebook.AppEventsLogger$AccessTokenAppIdPair, com.facebook.FacebookTimeSpentData> r9 = d     // Catch:{ all -> 0x00ae }
                if (r9 != 0) goto L_0x0079
                java.util.HashMap r9 = new java.util.HashMap     // Catch:{ all -> 0x00ae }
                r9.<init>()     // Catch:{ all -> 0x00ae }
                d = r9     // Catch:{ all -> 0x00ae }
            L_0x0079:
                c = r2     // Catch:{ all -> 0x00ae }
                goto L_0x003b
            L_0x007c:
                r3 = move-exception
            L_0x007d:
                com.facebook.internal.Utility.closeQuietly(r4)     // Catch:{ all -> 0x00ae }
                java.lang.String r4 = "AppEventsLogger.persistedsessioninfo"
                r9.deleteFile(r4)     // Catch:{ all -> 0x00ae }
                java.util.Map<com.facebook.AppEventsLogger$AccessTokenAppIdPair, com.facebook.FacebookTimeSpentData> r9 = d     // Catch:{ all -> 0x00ae }
                if (r9 != 0) goto L_0x0090
                java.util.HashMap r9 = new java.util.HashMap     // Catch:{ all -> 0x00ae }
                r9.<init>()     // Catch:{ all -> 0x00ae }
                d = r9     // Catch:{ all -> 0x00ae }
            L_0x0090:
                c = r2     // Catch:{ all -> 0x00ae }
                b = r1     // Catch:{ all -> 0x00ae }
                throw r3     // Catch:{ all -> 0x00ae }
            L_0x0095:
                r4 = r3
            L_0x0096:
                com.facebook.internal.Utility.closeQuietly(r4)     // Catch:{ all -> 0x00ae }
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                r9.deleteFile(r3)     // Catch:{ all -> 0x00ae }
                java.util.Map<com.facebook.AppEventsLogger$AccessTokenAppIdPair, com.facebook.FacebookTimeSpentData> r9 = d     // Catch:{ all -> 0x00ae }
                if (r9 != 0) goto L_0x00a9
                java.util.HashMap r9 = new java.util.HashMap     // Catch:{ all -> 0x00ae }
                r9.<init>()     // Catch:{ all -> 0x00ae }
                d = r9     // Catch:{ all -> 0x00ae }
            L_0x00a9:
                c = r2     // Catch:{ all -> 0x00ae }
                goto L_0x003b
            L_0x00ac:
                monitor-exit(r0)     // Catch:{ all -> 0x00ae }
                return
            L_0x00ae:
                r9 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00ae }
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.AppEventsLogger.PersistedAppSessionInfo.b(android.content.Context):void");
        }

        static void a(Context context) {
            synchronized (a) {
                if (b) {
                    ObjectOutputStream objectOutputStream = null;
                    try {
                        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput("AppEventsLogger.persistedsessioninfo", 0)));
                        try {
                            objectOutputStream2.writeObject(d);
                            b = false;
                            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info saved");
                            Utility.closeQuietly(objectOutputStream2);
                        } catch (Exception e2) {
                            e = e2;
                            objectOutputStream = objectOutputStream2;
                            try {
                                String h = AppEventsLogger.a;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Got unexpected exception: ");
                                sb.append(e.toString());
                                Log.d(h, sb.toString());
                                Utility.closeQuietly(objectOutputStream);
                            } catch (Throwable th) {
                                th = th;
                                Utility.closeQuietly(objectOutputStream);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            objectOutputStream = objectOutputStream2;
                            Utility.closeQuietly(objectOutputStream);
                            throw th;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        String h2 = AppEventsLogger.a;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Got unexpected exception: ");
                        sb2.append(e.toString());
                        Log.d(h2, sb2.toString());
                        Utility.closeQuietly(objectOutputStream);
                    }
                }
            }
        }

        static void a(Context context, AccessTokenAppIdPair accessTokenAppIdPair, AppEventsLogger appEventsLogger, long j, String str) {
            synchronized (a) {
                a(context, accessTokenAppIdPair).a(appEventsLogger, j, str);
                a();
            }
        }

        static void a(Context context, AccessTokenAppIdPair accessTokenAppIdPair, AppEventsLogger appEventsLogger, long j) {
            synchronized (a) {
                a(context, accessTokenAppIdPair).a(appEventsLogger, j);
                a();
            }
        }

        private static FacebookTimeSpentData a(Context context, AccessTokenAppIdPair accessTokenAppIdPair) {
            b(context);
            FacebookTimeSpentData facebookTimeSpentData = (FacebookTimeSpentData) d.get(accessTokenAppIdPair);
            if (facebookTimeSpentData != null) {
                return facebookTimeSpentData;
            }
            FacebookTimeSpentData facebookTimeSpentData2 = new FacebookTimeSpentData();
            d.put(accessTokenAppIdPair, facebookTimeSpentData2);
            return facebookTimeSpentData2;
        }

        private static void a() {
            if (!b) {
                b = true;
                AppEventsLogger.e.schedule(e, 30, TimeUnit.SECONDS);
            }
        }
    }

    static class PersistedEvents {
        private static Object a = new Object();
        private Context b;
        private HashMap<AccessTokenAppIdPair, List<AppEvent>> c = new HashMap<>();

        private PersistedEvents(Context context) {
            this.b = context;
        }

        public static PersistedEvents a(Context context) {
            PersistedEvents persistedEvents;
            synchronized (a) {
                persistedEvents = new PersistedEvents(context);
                persistedEvents.c();
            }
            return persistedEvents;
        }

        public static void a(Context context, AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState sessionEventsState) {
            HashMap hashMap = new HashMap();
            hashMap.put(accessTokenAppIdPair, sessionEventsState);
            a(context, (Map<AccessTokenAppIdPair, SessionEventsState>) hashMap);
        }

        public static void a(Context context, Map<AccessTokenAppIdPair, SessionEventsState> map) {
            synchronized (a) {
                PersistedEvents a2 = a(context);
                for (Entry entry : map.entrySet()) {
                    List b2 = ((SessionEventsState) entry.getValue()).b();
                    if (b2.size() != 0) {
                        a2.a((AccessTokenAppIdPair) entry.getKey(), b2);
                    }
                }
                a2.b();
            }
        }

        public Set<AccessTokenAppIdPair> a() {
            return this.c.keySet();
        }

        public List<AppEvent> a(AccessTokenAppIdPair accessTokenAppIdPair) {
            return (List) this.c.get(accessTokenAppIdPair);
        }

        private void b() {
            ObjectOutputStream objectOutputStream = null;
            try {
                ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new BufferedOutputStream(this.b.openFileOutput("AppEventsLogger.persistedevents", 0)));
                try {
                    objectOutputStream2.writeObject(this.c);
                    Utility.closeQuietly(objectOutputStream2);
                } catch (Exception e) {
                    ObjectOutputStream objectOutputStream3 = objectOutputStream2;
                    e = e;
                    objectOutputStream = objectOutputStream3;
                    try {
                        String h = AppEventsLogger.a;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Got unexpected exception: ");
                        sb.append(e.toString());
                        Log.d(h, sb.toString());
                        Utility.closeQuietly(objectOutputStream);
                    } catch (Throwable th) {
                        th = th;
                        Utility.closeQuietly(objectOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    ObjectOutputStream objectOutputStream4 = objectOutputStream2;
                    th = th2;
                    objectOutputStream = objectOutputStream4;
                    Utility.closeQuietly(objectOutputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                String h2 = AppEventsLogger.a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Got unexpected exception: ");
                sb2.append(e.toString());
                Log.d(h2, sb2.toString());
                Utility.closeQuietly(objectOutputStream);
            }
        }

        private void c() {
            ObjectInputStream objectInputStream;
            Exception e;
            ObjectInputStream objectInputStream2 = null;
            try {
                objectInputStream = new ObjectInputStream(new BufferedInputStream(this.b.openFileInput("AppEventsLogger.persistedevents")));
                try {
                    HashMap<AccessTokenAppIdPair, List<AppEvent>> hashMap = (HashMap) objectInputStream.readObject();
                    this.b.getFileStreamPath("AppEventsLogger.persistedevents").delete();
                    this.c = hashMap;
                } catch (FileNotFoundException unused) {
                    objectInputStream2 = objectInputStream;
                } catch (Exception e2) {
                    e = e2;
                    try {
                        String h = AppEventsLogger.a;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Got unexpected exception: ");
                        sb.append(e.toString());
                        Log.d(h, sb.toString());
                        Utility.closeQuietly(objectInputStream);
                    } catch (Throwable th) {
                        th = th;
                        Utility.closeQuietly(objectInputStream);
                        throw th;
                    }
                }
            } catch (FileNotFoundException unused2) {
                Utility.closeQuietly(objectInputStream2);
                return;
            } catch (Exception e3) {
                Exception exc = e3;
                objectInputStream = null;
                e = exc;
                String h2 = AppEventsLogger.a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Got unexpected exception: ");
                sb2.append(e.toString());
                Log.d(h2, sb2.toString());
                Utility.closeQuietly(objectInputStream);
            } catch (Throwable th2) {
                Throwable th3 = th2;
                objectInputStream = null;
                th = th3;
                Utility.closeQuietly(objectInputStream);
                throw th;
            }
            Utility.closeQuietly(objectInputStream);
        }

        public void a(AccessTokenAppIdPair accessTokenAppIdPair, List<AppEvent> list) {
            if (!this.c.containsKey(accessTokenAppIdPair)) {
                this.c.put(accessTokenAppIdPair, new ArrayList());
            }
            ((List) this.c.get(accessTokenAppIdPair)).addAll(list);
        }
    }

    static class SessionEventsState {
        private List<AppEvent> a = new ArrayList();
        private List<AppEvent> b = new ArrayList();
        private int c;
        private AttributionIdentifiers d;
        private String e;
        private String f;
        private final int g = 1000;

        public SessionEventsState(AttributionIdentifiers attributionIdentifiers, String str, String str2) {
            this.d = attributionIdentifiers;
            this.e = str;
            this.f = str2;
        }

        public synchronized void a(AppEvent appEvent) {
            if (this.a.size() + this.b.size() >= 1000) {
                this.c++;
            } else {
                this.a.add(appEvent);
            }
        }

        public synchronized int a() {
            return this.a.size();
        }

        public synchronized void a(boolean z) {
            if (z) {
                try {
                    this.a.addAll(this.b);
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.b.clear();
            this.c = 0;
        }

        public int a(Request request, boolean z, boolean z2, boolean z3) {
            synchronized (this) {
                int i = this.c;
                this.b.addAll(this.a);
                this.a.clear();
                JSONArray jSONArray = new JSONArray();
                for (AppEvent appEvent : this.b) {
                    if (z || !appEvent.a()) {
                        jSONArray.put(appEvent.b());
                    }
                }
                if (jSONArray.length() == 0) {
                    return 0;
                }
                a(request, i, jSONArray, z2, z3);
                return jSONArray.length();
            }
        }

        public synchronized List<AppEvent> b() {
            List<AppEvent> list;
            list = this.a;
            this.a = new ArrayList();
            return list;
        }

        public synchronized void a(List<AppEvent> list) {
            this.a.addAll(list);
        }

        private void a(Request request, int i, JSONArray jSONArray, boolean z, boolean z2) {
            GraphObject create = Factory.create();
            create.setProperty("event", "CUSTOM_APP_EVENTS");
            if (this.c > 0) {
                create.setProperty("num_skipped_events", Integer.valueOf(i));
            }
            if (z) {
                Utility.setAppEventAttributionParameters(create, this.d, this.f, z2);
            }
            try {
                Utility.setAppEventExtendedDeviceInfoParameters(create, AppEventsLogger.h);
            } catch (Exception unused) {
            }
            create.setProperty("application_package_name", this.e);
            request.setGraphObject(create);
            Bundle parameters = request.getParameters();
            if (parameters == null) {
                parameters = new Bundle();
            }
            String jSONArray2 = jSONArray.toString();
            if (jSONArray2 != null) {
                parameters.putByteArray("custom_events_file", a(jSONArray2));
                request.setTag(jSONArray2);
            }
            request.setParameters(parameters);
        }

        private byte[] a(String str) {
            try {
                return str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e2) {
                Utility.logd("Encoding exception: ", (Exception) e2);
                return null;
            }
        }
    }

    @Deprecated
    public static boolean getLimitEventUsage(Context context) {
        return Settings.getLimitEventAndDataUsage(context);
    }

    @Deprecated
    public static void setLimitEventUsage(Context context, boolean z) {
        Settings.setLimitEventAndDataUsage(context, z);
    }

    public static void activateApp(Context context) {
        Settings.sdkInitialize(context);
        activateApp(context, Utility.getMetadataApplicationId(context));
    }

    public static void activateApp(Context context, String str) {
        if (context == null || str == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        if (context instanceof Activity) {
            a((Activity) context);
        } else {
            c();
            Log.d(AppEventsLogger.class.getName(), "To set source application the context of activateApp must be an instance of Activity");
        }
        Settings.a(context, str, (Callback) null);
        AppEventsLogger appEventsLogger = new AppEventsLogger(context, str, null);
        final long currentTimeMillis = System.currentTimeMillis();
        final String b2 = b();
        e.execute(new Runnable(appEventsLogger) {
            final /* synthetic */ AppEventsLogger a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.a(currentTimeMillis, b2);
            }
        });
    }

    public static void deactivateApp(Context context) {
        deactivateApp(context, Utility.getMetadataApplicationId(context));
    }

    public static void deactivateApp(Context context, String str) {
        if (context == null || str == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        c();
        AppEventsLogger appEventsLogger = new AppEventsLogger(context, str, null);
        final long currentTimeMillis = System.currentTimeMillis();
        e.execute(new Runnable(appEventsLogger) {
            final /* synthetic */ AppEventsLogger a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.a(currentTimeMillis);
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(long j2, String str) {
        PersistedAppSessionInfo.a(h, this.c, this, j2, str);
    }

    /* access modifiers changed from: private */
    public void a(long j2) {
        PersistedAppSessionInfo.a(h, this.c, this, j2);
    }

    public static AppEventsLogger newLogger(Context context) {
        return new AppEventsLogger(context, null, null);
    }

    public static AppEventsLogger newLogger(Context context, Session session) {
        return new AppEventsLogger(context, null, session);
    }

    public static AppEventsLogger newLogger(Context context, String str, Session session) {
        return new AppEventsLogger(context, str, session);
    }

    public static AppEventsLogger newLogger(Context context, String str) {
        return new AppEventsLogger(context, str, null);
    }

    public static FlushBehavior getFlushBehavior() {
        FlushBehavior flushBehavior;
        synchronized (i) {
            flushBehavior = f;
        }
        return flushBehavior;
    }

    public static void setFlushBehavior(FlushBehavior flushBehavior) {
        synchronized (i) {
            f = flushBehavior;
        }
    }

    public void logEvent(String str) {
        logEvent(str, (Bundle) null);
    }

    public void logEvent(String str, double d2) {
        logEvent(str, d2, null);
    }

    public void logEvent(String str, Bundle bundle) {
        a(str, (Double) null, bundle, false);
    }

    public void logEvent(String str, double d2, Bundle bundle) {
        a(str, Double.valueOf(d2), bundle, false);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency) {
        logPurchase(bigDecimal, currency, null);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        if (bigDecimal == null) {
            a("purchaseAmount cannot be null");
        } else if (currency == null) {
            a("currency cannot be null");
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency.getCurrencyCode());
            logEvent(AppEventsConstants.EVENT_NAME_PURCHASED, bigDecimal.doubleValue(), bundle);
            a();
        }
    }

    public void flush() {
        b(FlushReason.EXPLICIT);
    }

    public static void onContextStop() {
        PersistedEvents.a(h, d);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Session session) {
        return this.c.equals(new AccessTokenAppIdPair(session));
    }

    public void logSdkEvent(String str, Double d2, Bundle bundle) {
        a(str, d2, bundle, true);
    }

    public String getApplicationId() {
        return this.c.b();
    }

    private AppEventsLogger(Context context, String str, Session session) {
        Validate.notNull(context, "context");
        this.b = context;
        if (session == null) {
            session = Session.getActiveSession();
        }
        if (session == null || (str != null && !str.equals(session.getApplicationId()))) {
            if (str == null) {
                str = Utility.getMetadataApplicationId(context);
            }
            this.c = new AccessTokenAppIdPair(null, str);
        } else {
            this.c = new AccessTokenAppIdPair(session);
        }
        synchronized (i) {
            if (j == null) {
                j = Utility.getHashedDeviceAndAppID(context, str);
            }
            if (h == null) {
                h = context.getApplicationContext();
            }
        }
        j();
    }

    private static void j() {
        synchronized (i) {
            try {
                if (e == null) {
                    e = new ScheduledThreadPoolExecutor(1);
                    e.scheduleAtFixedRate(new Runnable() {
                        public void run() {
                            if (AppEventsLogger.getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
                                AppEventsLogger.c(FlushReason.TIMER);
                            }
                        }
                    }, 0, 15, TimeUnit.SECONDS);
                    e.scheduleAtFixedRate(new Runnable() {
                        public void run() {
                            HashSet<String> hashSet = new HashSet<>();
                            synchronized (AppEventsLogger.i) {
                                for (AccessTokenAppIdPair b : AppEventsLogger.d.keySet()) {
                                    hashSet.add(b.b());
                                }
                            }
                            for (String queryAppSettings : hashSet) {
                                Utility.queryAppSettings(queryAppSettings, true);
                            }
                        }
                    }, 0, 86400, TimeUnit.SECONDS);
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
    }

    private void a(String str, Double d2, Bundle bundle, boolean z) {
        AppEvent appEvent = new AppEvent(this.b, str, d2, bundle, z);
        a(this.b, appEvent, this.c);
    }

    private static void a(final Context context, final AppEvent appEvent, final AccessTokenAppIdPair accessTokenAppIdPair) {
        Settings.getExecutor().execute(new Runnable() {
            public void run() {
                AppEventsLogger.b(context, accessTokenAppIdPair).a(appEvent);
                AppEventsLogger.k();
            }
        });
    }

    static void a() {
        if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
            b(FlushReason.EAGER_FLUSHING_EVENT);
        }
    }

    /* access modifiers changed from: private */
    public static void k() {
        synchronized (i) {
            if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY && l() > 100) {
                b(FlushReason.EVENT_THRESHOLD);
            }
        }
    }

    private static int l() {
        int i2;
        synchronized (i) {
            i2 = 0;
            for (SessionEventsState a2 : d.values()) {
                i2 += a2.a();
            }
        }
        return i2;
    }

    /* access modifiers changed from: private */
    public static SessionEventsState b(Context context, AccessTokenAppIdPair accessTokenAppIdPair) {
        SessionEventsState sessionEventsState;
        AttributionIdentifiers attributionIdentifiers = ((SessionEventsState) d.get(accessTokenAppIdPair)) == null ? AttributionIdentifiers.getAttributionIdentifiers(context) : null;
        synchronized (i) {
            sessionEventsState = (SessionEventsState) d.get(accessTokenAppIdPair);
            if (sessionEventsState == null) {
                sessionEventsState = new SessionEventsState(attributionIdentifiers, context.getPackageName(), j);
                d.put(accessTokenAppIdPair, sessionEventsState);
            }
        }
        return sessionEventsState;
    }

    private static SessionEventsState a(AccessTokenAppIdPair accessTokenAppIdPair) {
        SessionEventsState sessionEventsState;
        synchronized (i) {
            sessionEventsState = (SessionEventsState) d.get(accessTokenAppIdPair);
        }
        return sessionEventsState;
    }

    private static void b(final FlushReason flushReason) {
        Settings.getExecutor().execute(new Runnable() {
            public void run() {
                AppEventsLogger.c(flushReason);
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r3 = a(r3, (java.util.Set<com.facebook.AppEventsLogger.AccessTokenAppIdPair>) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
        com.facebook.internal.Utility.logd(a, "Caught unexpected exception while flushing: ", r3);
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
        m();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void c(com.facebook.AppEventsLogger.FlushReason r3) {
        /*
            java.lang.Object r0 = i
            monitor-enter(r0)
            boolean r1 = g     // Catch:{ all -> 0x0055 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0055 }
            return
        L_0x0009:
            r1 = 1
            g = r1     // Catch:{ all -> 0x0055 }
            java.util.HashSet r1 = new java.util.HashSet     // Catch:{ all -> 0x0055 }
            java.util.Map<com.facebook.AppEventsLogger$AccessTokenAppIdPair, com.facebook.AppEventsLogger$SessionEventsState> r2 = d     // Catch:{ all -> 0x0055 }
            java.util.Set r2 = r2.keySet()     // Catch:{ all -> 0x0055 }
            r1.<init>(r2)     // Catch:{ all -> 0x0055 }
            monitor-exit(r0)     // Catch:{ all -> 0x0055 }
            m()
            r0 = 0
            com.facebook.AppEventsLogger$FlushStatistics r3 = a(r3, r1)     // Catch:{ Exception -> 0x0021 }
            goto L_0x002a
        L_0x0021:
            r3 = move-exception
            java.lang.String r1 = a
            java.lang.String r2 = "Caught unexpected exception while flushing: "
            com.facebook.internal.Utility.logd(r1, r2, r3)
            r3 = r0
        L_0x002a:
            java.lang.Object r1 = i
            monitor-enter(r1)
            r0 = 0
            g = r0     // Catch:{ all -> 0x0052 }
            monitor-exit(r1)     // Catch:{ all -> 0x0052 }
            if (r3 == 0) goto L_0x0051
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "com.facebook.sdk.APP_EVENTS_FLUSHED"
            r0.<init>(r1)
            java.lang.String r1 = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED"
            int r2 = r3.a
            r0.putExtra(r1, r2)
            java.lang.String r1 = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT"
            com.facebook.AppEventsLogger$FlushResult r3 = r3.b
            r0.putExtra(r1, r3)
            android.content.Context r3 = h
            android.support.v4.content.LocalBroadcastManager r3 = android.support.v4.content.LocalBroadcastManager.getInstance(r3)
            r3.sendBroadcast(r0)
        L_0x0051:
            return
        L_0x0052:
            r3 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0052 }
            throw r3
        L_0x0055:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0055 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.AppEventsLogger.c(com.facebook.AppEventsLogger$FlushReason):void");
    }

    private static FlushStatistics a(FlushReason flushReason, Set<AccessTokenAppIdPair> set) {
        FlushStatistics flushStatistics = new FlushStatistics();
        boolean limitEventAndDataUsage = Settings.getLimitEventAndDataUsage(h);
        ArrayList<Request> arrayList = new ArrayList<>();
        for (AccessTokenAppIdPair accessTokenAppIdPair : set) {
            SessionEventsState a2 = a(accessTokenAppIdPair);
            if (a2 != null) {
                Request a3 = a(accessTokenAppIdPair, a2, limitEventAndDataUsage, flushStatistics);
                if (a3 != null) {
                    arrayList.add(a3);
                }
            }
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        Logger.log(LoggingBehavior.APP_EVENTS, a, "Flushing %d events due to %s.", Integer.valueOf(flushStatistics.a), flushReason.toString());
        for (Request executeAndWait : arrayList) {
            executeAndWait.executeAndWait();
        }
        return flushStatistics;
    }

    private static Request a(final AccessTokenAppIdPair accessTokenAppIdPair, final SessionEventsState sessionEventsState, boolean z, final FlushStatistics flushStatistics) {
        String b2 = accessTokenAppIdPair.b();
        FetchedAppSettings queryAppSettings = Utility.queryAppSettings(b2, false);
        final Request newPostRequest = Request.newPostRequest(null, String.format("%s/activities", new Object[]{b2}), null, null);
        Bundle parameters = newPostRequest.getParameters();
        if (parameters == null) {
            parameters = new Bundle();
        }
        parameters.putString("access_token", accessTokenAppIdPair.a());
        newPostRequest.setParameters(parameters);
        if (queryAppSettings == null) {
            return null;
        }
        int a2 = sessionEventsState.a(newPostRequest, queryAppSettings.supportsImplicitLogging(), queryAppSettings.supportsAttribution(), z);
        if (a2 == 0) {
            return null;
        }
        flushStatistics.a += a2;
        newPostRequest.setCallback(new Callback() {
            public void onCompleted(Response response) {
                AppEventsLogger.b(accessTokenAppIdPair, newPostRequest, response, sessionEventsState, flushStatistics);
            }
        });
        return newPostRequest;
    }

    /* access modifiers changed from: private */
    public static void b(AccessTokenAppIdPair accessTokenAppIdPair, Request request, Response response, SessionEventsState sessionEventsState, FlushStatistics flushStatistics) {
        String str;
        FacebookRequestError error = response.getError();
        String str2 = "Success";
        FlushResult flushResult = FlushResult.SUCCESS;
        boolean z = true;
        if (error != null) {
            if (error.getErrorCode() == -1) {
                str2 = "Failed: No Connectivity";
                flushResult = FlushResult.NO_CONNECTIVITY;
            } else {
                str2 = String.format("Failed:\n  Response: %s\n  Error %s", new Object[]{response.toString(), error.toString()});
                flushResult = FlushResult.SERVER_ERROR;
            }
        }
        if (Settings.isLoggingBehaviorEnabled(LoggingBehavior.APP_EVENTS)) {
            try {
                str = new JSONArray((String) request.getTag()).toString(2);
            } catch (JSONException unused) {
                str = "<Can't encode events for debug logging>";
            }
            Logger.log(LoggingBehavior.APP_EVENTS, a, "Flush completed\nParams: %s\n  Result: %s\n  Events JSON: %s", request.getGraphObject().toString(), str2, str);
        }
        if (error == null) {
            z = false;
        }
        sessionEventsState.a(z);
        if (flushResult == FlushResult.NO_CONNECTIVITY) {
            PersistedEvents.a(h, accessTokenAppIdPair, sessionEventsState);
        }
        if (flushResult != FlushResult.SUCCESS && flushStatistics.b != FlushResult.NO_CONNECTIVITY) {
            flushStatistics.b = flushResult;
        }
    }

    private static int m() {
        PersistedEvents a2 = PersistedEvents.a(h);
        int i2 = 0;
        for (AccessTokenAppIdPair accessTokenAppIdPair : a2.a()) {
            SessionEventsState b2 = b(h, accessTokenAppIdPair);
            List a3 = a2.a(accessTokenAppIdPair);
            b2.a(a3);
            i2 += a3.size();
        }
        return i2;
    }

    private static void a(String str) {
        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, "AppEvents", str);
    }

    private static void a(Activity activity) {
        ComponentName callingActivity = activity.getCallingActivity();
        if (callingActivity != null) {
            String packageName = callingActivity.getPackageName();
            if (packageName.equals(activity.getPackageName())) {
                c();
                return;
            }
            k = packageName;
        }
        Intent intent = activity.getIntent();
        if (intent == null || intent.getBooleanExtra("_fbSourceApplicationHasBeenSet", false)) {
            c();
            return;
        }
        Bundle appLinkData = AppLinks.getAppLinkData(intent);
        if (appLinkData == null) {
            c();
            return;
        }
        l = true;
        Bundle bundle = appLinkData.getBundle("referer_app_link");
        if (bundle == null) {
            k = null;
            return;
        }
        k = bundle.getString("package");
        intent.putExtra("_fbSourceApplicationHasBeenSet", true);
    }

    static String b() {
        String str = "Unclassified";
        if (l) {
            str = "Applink";
        }
        if (k == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("(");
        sb.append(k);
        sb.append(")");
        return sb.toString();
    }

    static void c() {
        k = null;
        l = false;
    }
}
