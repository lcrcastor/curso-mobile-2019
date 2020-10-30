package com.facebook.internal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.internal.FileLruCache.Limits;
import com.facebook.internal.PlatformServiceClient.CompletedListener;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.Builder;
import com.facebook.widget.FacebookDialog.DialogFeature;
import com.facebook.widget.FacebookDialog.PendingCall;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LikeActionController {
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR = "com.facebook.sdk.LikeActionController.DID_ERROR";
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_RESET = "com.facebook.sdk.LikeActionController.DID_RESET";
    public static final String ACTION_LIKE_ACTION_CONTROLLER_UPDATED = "com.facebook.sdk.LikeActionController.UPDATED";
    public static final String ACTION_OBJECT_ID_KEY = "com.facebook.sdk.LikeActionController.OBJECT_ID";
    public static final String ERROR_INVALID_OBJECT_ID = "Invalid Object Id";
    /* access modifiers changed from: private */
    public static final String a = "LikeActionController";
    /* access modifiers changed from: private */
    public static FileLruCache b;
    /* access modifiers changed from: private */
    public static final ConcurrentHashMap<String, LikeActionController> c = new ConcurrentHashMap<>();
    private static WorkQueue d = new WorkQueue(1);
    private static WorkQueue e = new WorkQueue(1);
    /* access modifiers changed from: private */
    public static Handler f;
    private static String g;
    /* access modifiers changed from: private */
    public static boolean h;
    private static boolean i;
    /* access modifiers changed from: private */
    public static volatile int j;
    /* access modifiers changed from: private */
    public Session k;
    /* access modifiers changed from: private */
    public Context l;
    /* access modifiers changed from: private */
    public String m;
    /* access modifiers changed from: private */
    public boolean n;
    /* access modifiers changed from: private */
    public String o;
    /* access modifiers changed from: private */
    public String p;
    /* access modifiers changed from: private */
    public String q;
    /* access modifiers changed from: private */
    public String r;
    /* access modifiers changed from: private */
    public String s;
    /* access modifiers changed from: private */
    public String t;
    /* access modifiers changed from: private */
    public boolean u;
    /* access modifiers changed from: private */
    public boolean v;
    /* access modifiers changed from: private */
    public boolean w;
    private UUID x;
    private Bundle y;
    /* access modifiers changed from: private */
    public AppEventsLogger z;

    abstract class AbstractRequestWrapper {
        protected String a;
        FacebookRequestError b;
        private Request d;

        /* access modifiers changed from: protected */
        public abstract void a(Response response);

        protected AbstractRequestWrapper(String str) {
            this.a = str;
        }

        /* access modifiers changed from: 0000 */
        public void a(RequestBatch requestBatch) {
            requestBatch.add(this.d);
        }

        /* access modifiers changed from: protected */
        public void a(Request request) {
            this.d = request;
            request.setVersion(ServerProtocol.GRAPH_API_VERSION);
            request.setCallback(new Callback() {
                public void onCompleted(Response response) {
                    AbstractRequestWrapper.this.b = response.getError();
                    if (AbstractRequestWrapper.this.b != null) {
                        AbstractRequestWrapper.this.a(AbstractRequestWrapper.this.b);
                    } else {
                        AbstractRequestWrapper.this.a(response);
                    }
                }
            });
        }

        /* access modifiers changed from: protected */
        public void a(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.a, "Error running request for object '%s' : %s", this.a, facebookRequestError);
        }
    }

    static class CreateLikeActionControllerWorkItem implements Runnable {
        private Context a;
        private String b;
        private CreationCallback c;

        CreateLikeActionControllerWorkItem(Context context, String str, CreationCallback creationCallback) {
            this.a = context;
            this.b = str;
            this.c = creationCallback;
        }

        public void run() {
            LikeActionController.b(this.a, this.b, this.c);
        }
    }

    public interface CreationCallback {
        void onComplete(LikeActionController likeActionController);
    }

    class GetEngagementRequestWrapper extends AbstractRequestWrapper {
        String d = LikeActionController.this.o;
        String e = LikeActionController.this.p;
        String f = LikeActionController.this.q;
        String g = LikeActionController.this.r;

        GetEngagementRequestWrapper(String str) {
            super(str);
            Bundle bundle = new Bundle();
            bundle.putString("fields", "engagement.fields(count_string_with_like,count_string_without_like,social_sentence_with_like,social_sentence_without_like)");
            a(new Request(LikeActionController.this.k, str, bundle, HttpMethod.GET));
        }

        /* access modifiers changed from: protected */
        public void a(Response response) {
            JSONObject tryGetJSONObjectFromResponse = Utility.tryGetJSONObjectFromResponse(response.getGraphObject(), "engagement");
            if (tryGetJSONObjectFromResponse != null) {
                this.d = tryGetJSONObjectFromResponse.optString("count_string_with_like", this.d);
                this.e = tryGetJSONObjectFromResponse.optString("count_string_without_like", this.e);
                this.f = tryGetJSONObjectFromResponse.optString("social_sentence_with_like", this.f);
                this.g = tryGetJSONObjectFromResponse.optString("social_sentence_without_like", this.g);
            }
        }

        /* access modifiers changed from: protected */
        public void a(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.a, "Error fetching engagement for object '%s' : %s", this.a, facebookRequestError);
            LikeActionController.this.a("get_engagement", facebookRequestError);
        }
    }

    class GetOGObjectIdRequestWrapper extends AbstractRequestWrapper {
        String d;

        GetOGObjectIdRequestWrapper(String str) {
            super(str);
            Bundle bundle = new Bundle();
            bundle.putString("fields", "og_object.fields(id)");
            bundle.putString("ids", str);
            a(new Request(LikeActionController.this.k, "", bundle, HttpMethod.GET));
        }

        /* access modifiers changed from: protected */
        public void a(FacebookRequestError facebookRequestError) {
            if (facebookRequestError.getErrorMessage().contains("og_object")) {
                this.b = null;
                return;
            }
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.a, "Error getting the FB id for object '%s' : %s", this.a, facebookRequestError);
        }

        /* access modifiers changed from: protected */
        public void a(Response response) {
            JSONObject tryGetJSONObjectFromResponse = Utility.tryGetJSONObjectFromResponse(response.getGraphObject(), this.a);
            if (tryGetJSONObjectFromResponse != null) {
                JSONObject optJSONObject = tryGetJSONObjectFromResponse.optJSONObject("og_object");
                if (optJSONObject != null) {
                    this.d = optJSONObject.optString("id");
                }
            }
        }
    }

    class GetOGObjectLikesRequestWrapper extends AbstractRequestWrapper {
        boolean d = LikeActionController.this.n;
        String e;

        GetOGObjectLikesRequestWrapper(String str) {
            super(str);
            Bundle bundle = new Bundle();
            bundle.putString("fields", "id,application");
            bundle.putString("object", str);
            a(new Request(LikeActionController.this.k, "me/og.likes", bundle, HttpMethod.GET));
        }

        /* access modifiers changed from: protected */
        public void a(Response response) {
            JSONArray tryGetJSONArrayFromResponse = Utility.tryGetJSONArrayFromResponse(response.getGraphObject(), "data");
            if (tryGetJSONArrayFromResponse != null) {
                for (int i = 0; i < tryGetJSONArrayFromResponse.length(); i++) {
                    JSONObject optJSONObject = tryGetJSONArrayFromResponse.optJSONObject(i);
                    if (optJSONObject != null) {
                        this.d = true;
                        JSONObject optJSONObject2 = optJSONObject.optJSONObject("application");
                        if (optJSONObject2 != null && Utility.areObjectsEqual(LikeActionController.this.k.getApplicationId(), optJSONObject2.optString("id"))) {
                            this.e = optJSONObject.optString("id");
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public void a(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.a, "Error fetching like status for object '%s' : %s", this.a, facebookRequestError);
            LikeActionController.this.a("get_og_object_like", facebookRequestError);
        }
    }

    class GetPageIdRequestWrapper extends AbstractRequestWrapper {
        String d;
        boolean e;

        GetPageIdRequestWrapper(String str) {
            super(str);
            Bundle bundle = new Bundle();
            bundle.putString("fields", "id");
            bundle.putString("ids", str);
            a(new Request(LikeActionController.this.k, "", bundle, HttpMethod.GET));
        }

        /* access modifiers changed from: protected */
        public void a(Response response) {
            JSONObject tryGetJSONObjectFromResponse = Utility.tryGetJSONObjectFromResponse(response.getGraphObject(), this.a);
            if (tryGetJSONObjectFromResponse != null) {
                this.d = tryGetJSONObjectFromResponse.optString("id");
                this.e = !Utility.isNullOrEmpty(this.d);
            }
        }

        /* access modifiers changed from: protected */
        public void a(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.a, "Error getting the FB id for object '%s' : %s", this.a, facebookRequestError);
        }
    }

    static class LikeDialogBuilder extends Builder<LikeDialogBuilder> {
        private String a;

        public LikeDialogBuilder(Activity activity, String str) {
            super(activity);
            this.a = str;
        }

        /* access modifiers changed from: protected */
        public EnumSet<? extends DialogFeature> getDialogFeatures() {
            return EnumSet.of(LikeDialogFeature.LIKE_DIALOG);
        }

        /* access modifiers changed from: protected */
        public Bundle getMethodArguments() {
            Bundle bundle = new Bundle();
            bundle.putString("object_id", this.a);
            return bundle;
        }

        public PendingCall a() {
            return this.appCall;
        }

        public String b() {
            return this.applicationId;
        }

        public String c() {
            return getWebFallbackUrlInternal();
        }
    }

    enum LikeDialogFeature implements DialogFeature {
        LIKE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140701);
        
        private int b;

        public String getAction() {
            return NativeProtocol.ACTION_LIKE_DIALOG;
        }

        private LikeDialogFeature(int i) {
            this.b = i;
        }

        public int getMinVersion() {
            return this.b;
        }
    }

    static class MRUCacheWorkItem implements Runnable {
        private static ArrayList<String> a = new ArrayList<>();
        private String b;
        private boolean c;

        MRUCacheWorkItem(String str, boolean z) {
            this.b = str;
            this.c = z;
        }

        public void run() {
            if (this.b != null) {
                a.remove(this.b);
                a.add(0, this.b);
            }
            if (this.c && a.size() >= 128) {
                while (64 < a.size()) {
                    LikeActionController.c.remove((String) a.remove(a.size() - 1));
                }
            }
        }
    }

    class PublishLikeRequestWrapper extends AbstractRequestWrapper {
        String d;

        PublishLikeRequestWrapper(String str) {
            super(str);
            Bundle bundle = new Bundle();
            bundle.putString("object", str);
            a(new Request(LikeActionController.this.k, "me/og.likes", bundle, HttpMethod.POST));
        }

        /* access modifiers changed from: protected */
        public void a(Response response) {
            this.d = Utility.safeGetStringFromResponse(response.getGraphObject(), "id");
        }

        /* access modifiers changed from: protected */
        public void a(FacebookRequestError facebookRequestError) {
            if (facebookRequestError.getErrorCode() == 3501) {
                this.b = null;
                return;
            }
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.a, "Error liking object '%s' : %s", this.a, facebookRequestError);
            LikeActionController.this.a("publish_like", facebookRequestError);
        }
    }

    class PublishUnlikeRequestWrapper extends AbstractRequestWrapper {
        private String e;

        /* access modifiers changed from: protected */
        public void a(Response response) {
        }

        PublishUnlikeRequestWrapper(String str) {
            super(null);
            this.e = str;
            a(new Request(LikeActionController.this.k, str, null, HttpMethod.DELETE));
        }

        /* access modifiers changed from: protected */
        public void a(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.a, "Error unliking object with unlike token '%s' : %s", this.e, facebookRequestError);
            LikeActionController.this.a("publish_unlike", facebookRequestError);
        }
    }

    interface RequestCompletionCallback {
        void a();
    }

    static class SerializeToDiskWorkItem implements Runnable {
        private String a;
        private String b;

        SerializeToDiskWorkItem(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void run() {
            LikeActionController.b(this.a, this.b);
        }
    }

    public static boolean handleOnActivityResult(Context context, final int i2, final int i3, final Intent intent) {
        final UUID callIdFromIntent = NativeProtocol.getCallIdFromIntent(intent);
        if (callIdFromIntent == null) {
            return false;
        }
        if (Utility.isNullOrEmpty(g)) {
            g = context.getSharedPreferences("com.facebook.LikeActionController.CONTROLLER_STORE_KEY", 0).getString("PENDING_CONTROLLER_KEY", null);
        }
        if (Utility.isNullOrEmpty(g)) {
            return false;
        }
        getControllerForObjectId(context, g, new CreationCallback() {
            public void onComplete(LikeActionController likeActionController) {
                likeActionController.a(i2, i3, intent, callIdFromIntent);
            }
        });
        return true;
    }

    public static void getControllerForObjectId(Context context, String str, CreationCallback creationCallback) {
        if (!i) {
            a(context);
        }
        LikeActionController a2 = a(str);
        if (a2 != null) {
            a(creationCallback, a2);
        } else {
            e.a((Runnable) new CreateLikeActionControllerWorkItem(context, str, creationCallback));
        }
    }

    /* access modifiers changed from: private */
    public static void b(Context context, String str, CreationCallback creationCallback) {
        LikeActionController a2 = a(str);
        if (a2 != null) {
            a(creationCallback, a2);
            return;
        }
        LikeActionController a3 = a(context, str);
        if (a3 == null) {
            a3 = new LikeActionController(context, Session.getActiveSession(), str);
            m(a3);
        }
        a(str, a3);
        f.post(new Runnable(a3) {
            final /* synthetic */ LikeActionController a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.h();
            }
        });
        a(creationCallback, a3);
    }

    private static synchronized void a(Context context) {
        synchronized (LikeActionController.class) {
            if (!i) {
                f = new Handler(Looper.getMainLooper());
                j = context.getSharedPreferences("com.facebook.LikeActionController.CONTROLLER_STORE_KEY", 0).getInt("OBJECT_SUFFIX", 1);
                b = new FileLruCache(context, a, new Limits());
                b(context);
                i = true;
            }
        }
    }

    private static void a(final CreationCallback creationCallback, final LikeActionController likeActionController) {
        if (creationCallback != null) {
            f.post(new Runnable() {
                public void run() {
                    creationCallback.onComplete(likeActionController);
                }
            });
        }
    }

    private static void b(Context context) {
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Session.ACTION_ACTIVE_SESSION_UNSET);
        intentFilter.addAction(Session.ACTION_ACTIVE_SESSION_CLOSED);
        intentFilter.addAction(Session.ACTION_ACTIVE_SESSION_OPENED);
        instance.registerReceiver(new BroadcastReceiver() {
            public void onReceive(final Context context, Intent intent) {
                if (!LikeActionController.h) {
                    String action = intent.getAction();
                    final boolean z = Utility.areObjectsEqual(Session.ACTION_ACTIVE_SESSION_UNSET, action) || Utility.areObjectsEqual(Session.ACTION_ACTIVE_SESSION_CLOSED, action);
                    LikeActionController.h = true;
                    LikeActionController.f.postDelayed(new Runnable() {
                        public void run() {
                            if (z) {
                                LikeActionController.j = (LikeActionController.j + 1) % 1000;
                                context.getSharedPreferences("com.facebook.LikeActionController.CONTROLLER_STORE_KEY", 0).edit().putInt("OBJECT_SUFFIX", LikeActionController.j).apply();
                                LikeActionController.c.clear();
                                LikeActionController.b.clearCache();
                            }
                            LikeActionController.b(context, (LikeActionController) null, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
                            LikeActionController.h = false;
                        }
                    }, 100);
                }
            }
        }, intentFilter);
    }

    private static void a(String str, LikeActionController likeActionController) {
        String b2 = b(str);
        d.a((Runnable) new MRUCacheWorkItem(b2, true));
        c.put(b2, likeActionController);
    }

    private static LikeActionController a(String str) {
        String b2 = b(str);
        LikeActionController likeActionController = (LikeActionController) c.get(b2);
        if (likeActionController != null) {
            d.a((Runnable) new MRUCacheWorkItem(b2, false));
        }
        return likeActionController;
    }

    private static void m(LikeActionController likeActionController) {
        String n2 = n(likeActionController);
        String b2 = b(likeActionController.m);
        if (!Utility.isNullOrEmpty(n2) && !Utility.isNullOrEmpty(b2)) {
            e.a((Runnable) new SerializeToDiskWorkItem(b2, n2));
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0026  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(java.lang.String r2, java.lang.String r3) {
        /*
            r0 = 0
            com.facebook.internal.FileLruCache r1 = b     // Catch:{ IOException -> 0x001c }
            java.io.OutputStream r2 = r1.a(r2)     // Catch:{ IOException -> 0x001c }
            byte[] r3 = r3.getBytes()     // Catch:{ IOException -> 0x0017, all -> 0x0014 }
            r2.write(r3)     // Catch:{ IOException -> 0x0017, all -> 0x0014 }
            if (r2 == 0) goto L_0x0029
            com.facebook.internal.Utility.closeQuietly(r2)
            goto L_0x0029
        L_0x0014:
            r3 = move-exception
            r0 = r2
            goto L_0x002a
        L_0x0017:
            r3 = move-exception
            r0 = r2
            goto L_0x001d
        L_0x001a:
            r3 = move-exception
            goto L_0x002a
        L_0x001c:
            r3 = move-exception
        L_0x001d:
            java.lang.String r2 = a     // Catch:{ all -> 0x001a }
            java.lang.String r1 = "Unable to serialize controller to disk"
            android.util.Log.e(r2, r1, r3)     // Catch:{ all -> 0x001a }
            if (r0 == 0) goto L_0x0029
            com.facebook.internal.Utility.closeQuietly(r0)
        L_0x0029:
            return
        L_0x002a:
            if (r0 == 0) goto L_0x002f
            com.facebook.internal.Utility.closeQuietly(r0)
        L_0x002f:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.LikeActionController.b(java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001f, code lost:
        if (r4 != null) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0021, code lost:
        com.facebook.internal.Utility.closeQuietly(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0031, code lost:
        if (r4 != null) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
        return r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.facebook.internal.LikeActionController a(android.content.Context r3, java.lang.String r4) {
        /*
            r0 = 0
            java.lang.String r4 = b(r4)     // Catch:{ IOException -> 0x0028, all -> 0x0025 }
            com.facebook.internal.FileLruCache r1 = b     // Catch:{ IOException -> 0x0028, all -> 0x0025 }
            java.io.InputStream r4 = r1.get(r4)     // Catch:{ IOException -> 0x0028, all -> 0x0025 }
            if (r4 == 0) goto L_0x001f
            java.lang.String r1 = com.facebook.internal.Utility.readStreamToString(r4)     // Catch:{ IOException -> 0x001d }
            boolean r2 = com.facebook.internal.Utility.isNullOrEmpty(r1)     // Catch:{ IOException -> 0x001d }
            if (r2 != 0) goto L_0x001f
            com.facebook.internal.LikeActionController r3 = b(r3, r1)     // Catch:{ IOException -> 0x001d }
            r0 = r3
            goto L_0x001f
        L_0x001d:
            r3 = move-exception
            goto L_0x002a
        L_0x001f:
            if (r4 == 0) goto L_0x0034
        L_0x0021:
            com.facebook.internal.Utility.closeQuietly(r4)
            goto L_0x0034
        L_0x0025:
            r3 = move-exception
            r4 = r0
            goto L_0x0036
        L_0x0028:
            r3 = move-exception
            r4 = r0
        L_0x002a:
            java.lang.String r1 = a     // Catch:{ all -> 0x0035 }
            java.lang.String r2 = "Unable to deserialize controller from disk"
            android.util.Log.e(r1, r2, r3)     // Catch:{ all -> 0x0035 }
            if (r4 == 0) goto L_0x0034
            goto L_0x0021
        L_0x0034:
            return r0
        L_0x0035:
            r3 = move-exception
        L_0x0036:
            if (r4 == 0) goto L_0x003b
            com.facebook.internal.Utility.closeQuietly(r4)
        L_0x003b:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.LikeActionController.a(android.content.Context, java.lang.String):com.facebook.internal.LikeActionController");
    }

    private static LikeActionController b(Context context, String str) {
        LikeActionController likeActionController;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("com.facebook.internal.LikeActionController.version", -1) != 2) {
                return null;
            }
            likeActionController = new LikeActionController(context, Session.getActiveSession(), jSONObject.getString("object_id"));
            likeActionController.o = jSONObject.optString("like_count_string_with_like", null);
            likeActionController.p = jSONObject.optString("like_count_string_without_like", null);
            likeActionController.q = jSONObject.optString("social_sentence_with_like", null);
            likeActionController.r = jSONObject.optString("social_sentence_without_like", null);
            likeActionController.n = jSONObject.optBoolean("is_object_liked");
            likeActionController.s = jSONObject.optString("unlike_token", null);
            String optString = jSONObject.optString("pending_call_id", null);
            if (!Utility.isNullOrEmpty(optString)) {
                likeActionController.x = UUID.fromString(optString);
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("pending_call_analytics_bundle");
            if (optJSONObject != null) {
                likeActionController.y = BundleJSONConverter.convertToBundle(optJSONObject);
            }
            return likeActionController;
        } catch (JSONException e2) {
            Log.e(a, "Unable to deserialize controller from JSON", e2);
            likeActionController = null;
        }
    }

    private static String n(LikeActionController likeActionController) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("com.facebook.internal.LikeActionController.version", 2);
            jSONObject.put("object_id", likeActionController.m);
            jSONObject.put("like_count_string_with_like", likeActionController.o);
            jSONObject.put("like_count_string_without_like", likeActionController.p);
            jSONObject.put("social_sentence_with_like", likeActionController.q);
            jSONObject.put("social_sentence_without_like", likeActionController.r);
            jSONObject.put("is_object_liked", likeActionController.n);
            jSONObject.put("unlike_token", likeActionController.s);
            if (likeActionController.x != null) {
                jSONObject.put("pending_call_id", likeActionController.x.toString());
            }
            if (likeActionController.y != null) {
                JSONObject convertToJSON = BundleJSONConverter.convertToJSON(likeActionController.y);
                if (convertToJSON != null) {
                    jSONObject.put("pending_call_analytics_bundle", convertToJSON);
                }
            }
            return jSONObject.toString();
        } catch (JSONException e2) {
            Log.e(a, "Unable to serialize controller to JSON", e2);
            return null;
        }
    }

    private static String b(String str) {
        Session activeSession = Session.getActiveSession();
        String accessToken = (activeSession == null || !activeSession.isOpened()) ? null : activeSession.getAccessToken();
        if (accessToken != null) {
            accessToken = Utility.a(accessToken);
        }
        return String.format("%s|%s|com.fb.sdk.like|%d", new Object[]{str, Utility.coerceValueIfNullOrEmpty(accessToken, ""), Integer.valueOf(j)});
    }

    /* access modifiers changed from: private */
    public static void b(Context context, LikeActionController likeActionController, String str) {
        b(context, likeActionController, str, null);
    }

    /* access modifiers changed from: private */
    public static void b(Context context, LikeActionController likeActionController, String str, Bundle bundle) {
        Intent intent = new Intent(str);
        if (likeActionController != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(ACTION_OBJECT_ID_KEY, likeActionController.getObjectId());
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
    }

    private LikeActionController(Context context, Session session, String str) {
        this.l = context;
        this.k = session;
        this.m = str;
        this.z = AppEventsLogger.newLogger(context, session);
    }

    public String getObjectId() {
        return this.m;
    }

    public String getLikeCountString() {
        return this.n ? this.o : this.p;
    }

    public String getSocialSentence() {
        return this.n ? this.q : this.r;
    }

    public boolean isObjectLiked() {
        return this.n;
    }

    public void toggleLike(Activity activity, Bundle bundle) {
        this.z.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_TAP, null, bundle);
        boolean z2 = !this.n;
        if (b(z2)) {
            a(z2, this.o, this.p, this.q, this.r, this.s);
            if (this.w) {
                this.z.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNDO_QUICKLY, null, bundle);
                return;
            }
        }
        a(activity, z2, bundle);
    }

    private void a(Activity activity, boolean z2, Bundle bundle) {
        if (!b(z2)) {
            a(activity, bundle);
        } else if (z2) {
            b(activity, bundle);
        } else {
            c(activity, bundle);
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z2, String str, String str2, String str3, String str4, String str5) {
        String coerceValueIfNullOrEmpty = Utility.coerceValueIfNullOrEmpty(str, null);
        String coerceValueIfNullOrEmpty2 = Utility.coerceValueIfNullOrEmpty(str2, null);
        String coerceValueIfNullOrEmpty3 = Utility.coerceValueIfNullOrEmpty(str3, null);
        String coerceValueIfNullOrEmpty4 = Utility.coerceValueIfNullOrEmpty(str4, null);
        String coerceValueIfNullOrEmpty5 = Utility.coerceValueIfNullOrEmpty(str5, null);
        if (z2 != this.n || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty, this.o) || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty2, this.p) || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty3, this.q) || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty4, this.r) || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty5, this.s)) {
            this.n = z2;
            this.o = coerceValueIfNullOrEmpty;
            this.p = coerceValueIfNullOrEmpty2;
            this.q = coerceValueIfNullOrEmpty3;
            this.r = coerceValueIfNullOrEmpty4;
            this.s = coerceValueIfNullOrEmpty5;
            m(this);
            b(this.l, this, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        }
    }

    /* access modifiers changed from: private */
    public void a(Activity activity, Bundle bundle) {
        LikeDialogBuilder likeDialogBuilder = new LikeDialogBuilder(activity, this.m);
        if (likeDialogBuilder.canPresent()) {
            a(likeDialogBuilder.build().present(), bundle);
            this.z.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_DIALOG, null, bundle);
            return;
        }
        String c2 = likeDialogBuilder.c();
        if (!Utility.isNullOrEmpty(c2) && FacebookWebFallbackDialog.presentWebFallback(activity, c2, likeDialogBuilder.b(), likeDialogBuilder.a(), a(bundle))) {
            this.z.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_FALLBACK, null, bundle);
        }
    }

    /* access modifiers changed from: private */
    public boolean a(int i2, int i3, Intent intent, UUID uuid) {
        if (this.x == null || !this.x.equals(uuid)) {
            return false;
        }
        PendingCall pendingCallById = PendingCallStore.getInstance().getPendingCallById(this.x);
        if (pendingCallById == null) {
            return false;
        }
        FacebookDialog.handleActivityResult(this.l, pendingCallById, i2, intent, a(this.y));
        g();
        return true;
    }

    private FacebookDialog.Callback a(final Bundle bundle) {
        return new FacebookDialog.Callback() {
            public void onComplete(PendingCall pendingCall, Bundle bundle) {
                String str;
                String str2;
                String str3;
                String str4;
                if (bundle != null && bundle.containsKey("object_is_liked")) {
                    boolean z = bundle.getBoolean("object_is_liked");
                    String b2 = LikeActionController.this.o;
                    String c = LikeActionController.this.p;
                    if (bundle.containsKey("like_count_string")) {
                        str2 = bundle.getString("like_count_string");
                        str = str2;
                    } else {
                        str2 = b2;
                        str = c;
                    }
                    String d = LikeActionController.this.q;
                    String e = LikeActionController.this.r;
                    if (bundle.containsKey("social_sentence")) {
                        str4 = bundle.getString("social_sentence");
                        str3 = str4;
                    } else {
                        str4 = d;
                        str3 = e;
                    }
                    String string = bundle.containsKey("object_is_liked") ? bundle.getString("unlike_token") : LikeActionController.this.s;
                    Bundle bundle2 = bundle == null ? new Bundle() : bundle;
                    bundle2.putString(AnalyticsEvents.PARAMETER_CALL_ID, pendingCall.getCallId().toString());
                    LikeActionController.this.z.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DIALOG_DID_SUCCEED, null, bundle2);
                    LikeActionController.this.a(z, str2, str, str4, str3, string);
                }
            }

            public void onError(PendingCall pendingCall, Exception exc, Bundle bundle) {
                Logger.log(LoggingBehavior.REQUESTS, LikeActionController.a, "Like Dialog failed with error : %s", exc);
                Bundle bundle2 = bundle == null ? new Bundle() : bundle;
                bundle2.putString(AnalyticsEvents.PARAMETER_CALL_ID, pendingCall.getCallId().toString());
                LikeActionController.this.a("present_dialog", bundle2);
                LikeActionController.b(LikeActionController.this.l, LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, bundle);
            }
        };
    }

    private void a(PendingCall pendingCall, Bundle bundle) {
        PendingCallStore.getInstance().trackPendingCall(pendingCall);
        this.x = pendingCall.getCallId();
        c(this.m);
        this.y = bundle;
        m(this);
    }

    private void g() {
        PendingCallStore.getInstance().stopTrackingPendingCall(this.x);
        this.x = null;
        this.y = null;
        c((String) null);
    }

    private void c(String str) {
        g = str;
        this.l.getSharedPreferences("com.facebook.LikeActionController.CONTROLLER_STORE_KEY", 0).edit().putString("PENDING_CONTROLLER_KEY", g).apply();
    }

    private boolean b(boolean z2) {
        return !this.u && this.t != null && this.k != null && this.k.getPermissions() != null && this.k.getPermissions().contains("publish_actions") && (z2 || !Utility.isNullOrEmpty(this.s));
    }

    private void b(final Activity activity, final Bundle bundle) {
        this.w = true;
        a((RequestCompletionCallback) new RequestCompletionCallback() {
            public void a() {
                if (Utility.isNullOrEmpty(LikeActionController.this.t)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(NativeProtocol.STATUS_ERROR_DESCRIPTION, LikeActionController.ERROR_INVALID_OBJECT_ID);
                    LikeActionController.b(LikeActionController.this.l, LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, bundle);
                    return;
                }
                RequestBatch requestBatch = new RequestBatch();
                final PublishLikeRequestWrapper publishLikeRequestWrapper = new PublishLikeRequestWrapper(LikeActionController.this.t);
                publishLikeRequestWrapper.a(requestBatch);
                requestBatch.addCallback(new RequestBatch.Callback() {
                    public void onBatchCompleted(RequestBatch requestBatch) {
                        LikeActionController.this.w = false;
                        if (publishLikeRequestWrapper.b != null) {
                            LikeActionController.this.a(false, LikeActionController.this.o, LikeActionController.this.p, LikeActionController.this.q, LikeActionController.this.r, LikeActionController.this.s);
                            LikeActionController.this.a(activity, bundle);
                            return;
                        }
                        LikeActionController.this.s = Utility.coerceValueIfNullOrEmpty(publishLikeRequestWrapper.d, null);
                        LikeActionController.this.v = true;
                        LikeActionController.this.z.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_LIKE, null, bundle);
                        LikeActionController.this.d(activity, bundle);
                    }
                });
                requestBatch.executeAsync();
            }
        });
    }

    private void c(final Activity activity, final Bundle bundle) {
        this.w = true;
        RequestBatch requestBatch = new RequestBatch();
        final PublishUnlikeRequestWrapper publishUnlikeRequestWrapper = new PublishUnlikeRequestWrapper(this.s);
        publishUnlikeRequestWrapper.a(requestBatch);
        requestBatch.addCallback(new RequestBatch.Callback() {
            public void onBatchCompleted(RequestBatch requestBatch) {
                LikeActionController.this.w = false;
                if (publishUnlikeRequestWrapper.b != null) {
                    LikeActionController.this.a(true, LikeActionController.this.o, LikeActionController.this.p, LikeActionController.this.q, LikeActionController.this.r, LikeActionController.this.s);
                    LikeActionController.this.a(activity, bundle);
                    return;
                }
                LikeActionController.this.s = null;
                LikeActionController.this.v = false;
                LikeActionController.this.z.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNLIKE, null, bundle);
                LikeActionController.this.d(activity, bundle);
            }
        });
        requestBatch.executeAsync();
    }

    /* access modifiers changed from: private */
    public void h() {
        if (this.k == null || this.k.isClosed() || SessionState.CREATED.equals(this.k.getState())) {
            i();
        } else {
            a((RequestCompletionCallback) new RequestCompletionCallback() {
                public void a() {
                    final GetOGObjectLikesRequestWrapper getOGObjectLikesRequestWrapper = new GetOGObjectLikesRequestWrapper(LikeActionController.this.t);
                    final GetEngagementRequestWrapper getEngagementRequestWrapper = new GetEngagementRequestWrapper(LikeActionController.this.t);
                    RequestBatch requestBatch = new RequestBatch();
                    getOGObjectLikesRequestWrapper.a(requestBatch);
                    getEngagementRequestWrapper.a(requestBatch);
                    requestBatch.addCallback(new RequestBatch.Callback() {
                        public void onBatchCompleted(RequestBatch requestBatch) {
                            if (getOGObjectLikesRequestWrapper.b == null && getEngagementRequestWrapper.b == null) {
                                LikeActionController.this.a(getOGObjectLikesRequestWrapper.d, getEngagementRequestWrapper.d, getEngagementRequestWrapper.e, getEngagementRequestWrapper.f, getEngagementRequestWrapper.g, getOGObjectLikesRequestWrapper.e);
                                return;
                            }
                            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.a, "Unable to refresh like state for id: '%s'", LikeActionController.this.m);
                        }
                    });
                    requestBatch.executeAsync();
                }
            });
        }
    }

    private void i() {
        LikeStatusClient likeStatusClient = new LikeStatusClient(this.l, Settings.getApplicationId(), this.m);
        if (likeStatusClient.start()) {
            likeStatusClient.setCompletedListener(new CompletedListener() {
                public void completed(Bundle bundle) {
                    if (bundle != null && bundle.containsKey(NativeProtocol.EXTRA_OBJECT_IS_LIKED)) {
                        LikeActionController.this.a(bundle.getBoolean(NativeProtocol.EXTRA_OBJECT_IS_LIKED), bundle.containsKey(NativeProtocol.EXTRA_LIKE_COUNT_STRING_WITH_LIKE) ? bundle.getString(NativeProtocol.EXTRA_LIKE_COUNT_STRING_WITH_LIKE) : LikeActionController.this.o, bundle.containsKey(NativeProtocol.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE) ? bundle.getString(NativeProtocol.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE) : LikeActionController.this.p, bundle.containsKey(NativeProtocol.EXTRA_SOCIAL_SENTENCE_WITH_LIKE) ? bundle.getString(NativeProtocol.EXTRA_SOCIAL_SENTENCE_WITH_LIKE) : LikeActionController.this.q, bundle.containsKey(NativeProtocol.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE) ? bundle.getString(NativeProtocol.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE) : LikeActionController.this.r, bundle.containsKey(NativeProtocol.EXTRA_UNLIKE_TOKEN) ? bundle.getString(NativeProtocol.EXTRA_UNLIKE_TOKEN) : LikeActionController.this.s);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void d(Activity activity, Bundle bundle) {
        if (this.n != this.v) {
            a(activity, this.n, bundle);
        }
    }

    private void a(final RequestCompletionCallback requestCompletionCallback) {
        if (!Utility.isNullOrEmpty(this.t)) {
            if (requestCompletionCallback != null) {
                requestCompletionCallback.a();
            }
            return;
        }
        final GetOGObjectIdRequestWrapper getOGObjectIdRequestWrapper = new GetOGObjectIdRequestWrapper(this.m);
        final GetPageIdRequestWrapper getPageIdRequestWrapper = new GetPageIdRequestWrapper(this.m);
        RequestBatch requestBatch = new RequestBatch();
        getOGObjectIdRequestWrapper.a(requestBatch);
        getPageIdRequestWrapper.a(requestBatch);
        requestBatch.addCallback(new RequestBatch.Callback() {
            public void onBatchCompleted(RequestBatch requestBatch) {
                LikeActionController.this.t = getOGObjectIdRequestWrapper.d;
                if (Utility.isNullOrEmpty(LikeActionController.this.t)) {
                    LikeActionController.this.t = getPageIdRequestWrapper.d;
                    LikeActionController.this.u = getPageIdRequestWrapper.e;
                }
                if (Utility.isNullOrEmpty(LikeActionController.this.t)) {
                    Logger.log(LoggingBehavior.DEVELOPER_ERRORS, LikeActionController.a, "Unable to verify the FB id for '%s'. Verify that it is a valid FB object or page", LikeActionController.this.m);
                    LikeActionController.this.a("get_verified_id", getPageIdRequestWrapper.b != null ? getPageIdRequestWrapper.b : getOGObjectIdRequestWrapper.b);
                }
                if (requestCompletionCallback != null) {
                    requestCompletionCallback.a();
                }
            }
        });
        requestBatch.executeAsync();
    }

    /* access modifiers changed from: private */
    public void a(String str, Bundle bundle) {
        Bundle bundle2 = new Bundle(bundle);
        bundle2.putString("object_id", this.m);
        bundle2.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_CURRENT_ACTION, str);
        this.z.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_ERROR, null, bundle2);
    }

    /* access modifiers changed from: private */
    public void a(String str, FacebookRequestError facebookRequestError) {
        Bundle bundle = new Bundle();
        if (facebookRequestError != null) {
            JSONObject requestResult = facebookRequestError.getRequestResult();
            if (requestResult != null) {
                bundle.putString("error", requestResult.toString());
            }
        }
        a(str, bundle);
    }
}
