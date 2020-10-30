package com.facebook;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.Request.Callback;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.SessionAuthorizationType;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class Session implements Serializable {
    public static final String ACTION_ACTIVE_SESSION_CLOSED = "com.facebook.sdk.ACTIVE_SESSION_CLOSED";
    public static final String ACTION_ACTIVE_SESSION_OPENED = "com.facebook.sdk.ACTIVE_SESSION_OPENED";
    public static final String ACTION_ACTIVE_SESSION_SET = "com.facebook.sdk.ACTIVE_SESSION_SET";
    public static final String ACTION_ACTIVE_SESSION_UNSET = "com.facebook.sdk.ACTIVE_SESSION_UNSET";
    public static final int DEFAULT_AUTHORIZE_ACTIVITY_CODE = 64206;
    public static final String TAG = Session.class.getCanonicalName();
    public static final String WEB_VIEW_ERROR_CODE_KEY = "com.facebook.sdk.WebViewErrorCode";
    public static final String WEB_VIEW_FAILING_URL_KEY = "com.facebook.sdk.FailingUrl";
    private static final Object a = new Object();
    private static Session b = null;
    /* access modifiers changed from: private */
    public static volatile Context c = null;
    private static final Set<String> d = new HashSet<String>() {
        {
            add("ads_management");
            add("create_event");
            add("rsvp_event");
        }
    };
    private static final long serialVersionUID = 1;
    private String e;
    /* access modifiers changed from: private */
    public SessionState f;
    /* access modifiers changed from: private */
    public AccessToken g;
    private Date h;
    private AuthorizationRequest i;
    private AuthorizationClient j;
    private volatile Bundle k;
    /* access modifiers changed from: private */
    public final List<StatusCallback> l;
    /* access modifiers changed from: private */
    public Handler m;
    /* access modifiers changed from: private */
    public AutoPublishAsyncTask n;
    /* access modifiers changed from: private */
    public final Object o;
    private TokenCachingStrategy p;
    /* access modifiers changed from: private */
    public volatile TokenRefreshRequest q;
    private AppEventsLogger r;

    public static class AuthorizationRequest implements Serializable {
        private static final long serialVersionUID = 1;
        /* access modifiers changed from: private */
        public final StartActivityDelegate a;
        /* access modifiers changed from: private */
        public SessionLoginBehavior b;
        /* access modifiers changed from: private */
        public int c;
        private StatusCallback d;
        /* access modifiers changed from: private */
        public boolean e;
        /* access modifiers changed from: private */
        public List<String> f;
        /* access modifiers changed from: private */
        public SessionDefaultAudience g;
        private String h;
        private String i;
        private final String j;
        /* access modifiers changed from: private */
        public final Map<String, String> k;

        static class AuthRequestSerializationProxyV1 implements Serializable {
            private static final long serialVersionUID = -8748347685113614927L;
            private final SessionLoginBehavior a;
            private final int b;
            private boolean c;
            private final List<String> d;
            private final String e;
            private final String f;
            private final String g;

            private AuthRequestSerializationProxyV1(SessionLoginBehavior sessionLoginBehavior, int i, List<String> list, String str, boolean z, String str2, String str3) {
                this.a = sessionLoginBehavior;
                this.b = i;
                this.d = list;
                this.e = str;
                this.c = z;
                this.f = str2;
                this.g = str3;
            }

            private Object readResolve() {
                AuthorizationRequest authorizationRequest = new AuthorizationRequest(this.a, this.b, this.d, this.e, this.c, this.f, this.g);
                return authorizationRequest;
            }
        }

        AuthorizationRequest(final Activity activity) {
            this.b = SessionLoginBehavior.SSO_WITH_FALLBACK;
            this.c = Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE;
            this.e = false;
            this.f = Collections.emptyList();
            this.g = SessionDefaultAudience.FRIENDS;
            this.j = UUID.randomUUID().toString();
            this.k = new HashMap();
            this.a = new StartActivityDelegate() {
                public void a(Intent intent, int i) {
                    activity.startActivityForResult(intent, i);
                }

                public Activity a() {
                    return activity;
                }
            };
        }

        AuthorizationRequest(final Fragment fragment) {
            this.b = SessionLoginBehavior.SSO_WITH_FALLBACK;
            this.c = Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE;
            this.e = false;
            this.f = Collections.emptyList();
            this.g = SessionDefaultAudience.FRIENDS;
            this.j = UUID.randomUUID().toString();
            this.k = new HashMap();
            this.a = new StartActivityDelegate() {
                public void a(Intent intent, int i) {
                    fragment.startActivityForResult(intent, i);
                }

                public Activity a() {
                    return fragment.getActivity();
                }
            };
        }

        private AuthorizationRequest(SessionLoginBehavior sessionLoginBehavior, int i2, List<String> list, String str, boolean z, String str2, String str3) {
            this.b = SessionLoginBehavior.SSO_WITH_FALLBACK;
            this.c = Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE;
            this.e = false;
            this.f = Collections.emptyList();
            this.g = SessionDefaultAudience.FRIENDS;
            this.j = UUID.randomUUID().toString();
            this.k = new HashMap();
            this.a = new StartActivityDelegate() {
                public void a(Intent intent, int i) {
                    throw new UnsupportedOperationException("Cannot create an AuthorizationRequest without a valid Activity or Fragment");
                }

                public Activity a() {
                    throw new UnsupportedOperationException("Cannot create an AuthorizationRequest without a valid Activity or Fragment");
                }
            };
            this.b = sessionLoginBehavior;
            this.c = i2;
            this.f = list;
            this.g = SessionDefaultAudience.valueOf(str);
            this.e = z;
            this.h = str2;
            this.i = str3;
        }

        public void setIsLegacy(boolean z) {
            this.e = z;
        }

        /* access modifiers changed from: 0000 */
        public AuthorizationRequest setCallback(StatusCallback statusCallback) {
            this.d = statusCallback;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public StatusCallback a() {
            return this.d;
        }

        /* access modifiers changed from: 0000 */
        public AuthorizationRequest setLoginBehavior(SessionLoginBehavior sessionLoginBehavior) {
            if (sessionLoginBehavior != null) {
                this.b = sessionLoginBehavior;
            }
            return this;
        }

        /* access modifiers changed from: 0000 */
        public SessionLoginBehavior b() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public AuthorizationRequest setRequestCode(int i2) {
            if (i2 >= 0) {
                this.c = i2;
            }
            return this;
        }

        /* access modifiers changed from: 0000 */
        public int c() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public AuthorizationRequest setPermissions(List<String> list) {
            if (list != null) {
                this.f = list;
            }
            return this;
        }

        /* access modifiers changed from: 0000 */
        public AuthorizationRequest setPermissions(String... strArr) {
            return setPermissions(Arrays.asList(strArr));
        }

        /* access modifiers changed from: 0000 */
        public List<String> d() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public AuthorizationRequest setDefaultAudience(SessionDefaultAudience sessionDefaultAudience) {
            if (sessionDefaultAudience != null) {
                this.g = sessionDefaultAudience;
            }
            return this;
        }

        /* access modifiers changed from: 0000 */
        public StartActivityDelegate e() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public void a(String str) {
            this.h = str;
        }

        /* access modifiers changed from: 0000 */
        public void b(String str) {
            this.i = str;
        }

        /* access modifiers changed from: 0000 */
        public String f() {
            return this.j;
        }

        /* access modifiers changed from: 0000 */
        public AuthorizationRequest g() {
            AuthorizationRequest authorizationRequest = new AuthorizationRequest(this.b, this.c, this.e, this.f, this.g, this.h, this.i, new StartActivityDelegate() {
                public void a(Intent intent, int i) {
                    AuthorizationRequest.this.a.a(intent, i);
                }

                public Activity a() {
                    return AuthorizationRequest.this.a.a();
                }
            }, this.j);
            return authorizationRequest;
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            AuthRequestSerializationProxyV1 authRequestSerializationProxyV1 = new AuthRequestSerializationProxyV1(this.b, this.c, this.f, this.g.name(), this.e, this.h, this.i);
            return authRequestSerializationProxyV1;
        }

        private void readObject(ObjectInputStream objectInputStream) {
            throw new InvalidObjectException("Cannot readObject, serialization proxy required");
        }
    }

    class AutoPublishAsyncTask extends AsyncTask<Void, Void, Void> {
        private final String b;
        private final Context c;

        public AutoPublishAsyncTask(String str, Context context) {
            this.b = str;
            this.c = context.getApplicationContext();
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Void doInBackground(Void... voidArr) {
            try {
                Settings.a(this.c, this.b, true);
            } catch (Exception e) {
                Utility.logd("Facebook-publish", e);
            }
            return null;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(Void voidR) {
            synchronized (Session.this) {
                Session.this.n = null;
            }
        }
    }

    public static final class Builder {
        private final Context a;
        private String b;
        private TokenCachingStrategy c;

        public Builder(Context context) {
            this.a = context;
        }

        public Builder setApplicationId(String str) {
            this.b = str;
            return this;
        }

        public Builder setTokenCachingStrategy(TokenCachingStrategy tokenCachingStrategy) {
            this.c = tokenCachingStrategy;
            return this;
        }

        public Session build() {
            return new Session(this.a, this.b, this.c);
        }
    }

    public static final class NewPermissionsRequest extends AuthorizationRequest {
        private static final long serialVersionUID = 1;

        public NewPermissionsRequest(Activity activity, List<String> list) {
            super(activity);
            setPermissions(list);
        }

        public NewPermissionsRequest(Fragment fragment, List<String> list) {
            super(fragment);
            setPermissions(list);
        }

        public NewPermissionsRequest(Activity activity, String... strArr) {
            super(activity);
            setPermissions(strArr);
        }

        public NewPermissionsRequest(Fragment fragment, String... strArr) {
            super(fragment);
            setPermissions(strArr);
        }

        public final NewPermissionsRequest setCallback(StatusCallback statusCallback) {
            super.setCallback(statusCallback);
            return this;
        }

        public final NewPermissionsRequest setLoginBehavior(SessionLoginBehavior sessionLoginBehavior) {
            super.setLoginBehavior(sessionLoginBehavior);
            return this;
        }

        public final NewPermissionsRequest setRequestCode(int i) {
            super.setRequestCode(i);
            return this;
        }

        public final NewPermissionsRequest setDefaultAudience(SessionDefaultAudience sessionDefaultAudience) {
            super.setDefaultAudience(sessionDefaultAudience);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public AuthorizationRequest g() {
            AuthorizationRequest g = super.g();
            g.a(true);
            return g;
        }
    }

    public static final class OpenRequest extends AuthorizationRequest {
        private static final long serialVersionUID = 1;

        public OpenRequest(Activity activity) {
            super(activity);
        }

        public OpenRequest(Fragment fragment) {
            super(fragment);
        }

        public final OpenRequest setCallback(StatusCallback statusCallback) {
            super.setCallback(statusCallback);
            return this;
        }

        public final OpenRequest setLoginBehavior(SessionLoginBehavior sessionLoginBehavior) {
            super.setLoginBehavior(sessionLoginBehavior);
            return this;
        }

        public final OpenRequest setRequestCode(int i) {
            super.setRequestCode(i);
            return this;
        }

        public final OpenRequest setPermissions(List<String> list) {
            super.setPermissions(list);
            return this;
        }

        public final OpenRequest setPermissions(String... strArr) {
            super.setPermissions(strArr);
            return this;
        }

        public final OpenRequest setDefaultAudience(SessionDefaultAudience sessionDefaultAudience) {
            super.setDefaultAudience(sessionDefaultAudience);
            return this;
        }
    }

    static class PermissionsPair {
        List<String> a;
        List<String> b;

        public PermissionsPair(List<String> list, List<String> list2) {
            this.a = list;
            this.b = list2;
        }

        public List<String> a() {
            return this.a;
        }

        public List<String> b() {
            return this.b;
        }
    }

    static class SerializationProxyV1 implements Serializable {
        private static final long serialVersionUID = 7663436173185080063L;
        private final String a;
        private final SessionState b;
        private final AccessToken c;
        private final Date d;
        private final boolean e;
        private final AuthorizationRequest f;

        SerializationProxyV1(String str, SessionState sessionState, AccessToken accessToken, Date date, boolean z, AuthorizationRequest authorizationRequest) {
            this.a = str;
            this.b = sessionState;
            this.c = accessToken;
            this.d = date;
            this.e = z;
            this.f = authorizationRequest;
        }

        private Object readResolve() {
            Session session = new Session(this.a, this.b, this.c, this.d, this.e, this.f);
            return session;
        }
    }

    interface StartActivityDelegate {
        Activity a();

        void a(Intent intent, int i);
    }

    public interface StatusCallback {
        void call(Session session, SessionState sessionState, Exception exc);
    }

    class TokenRefreshRequest implements ServiceConnection {
        final Messenger a = new Messenger(new TokenRefreshRequestHandler(Session.this, this));
        Messenger b = null;

        TokenRefreshRequest() {
        }

        public void a() {
            Intent createTokenRefreshIntent = NativeProtocol.createTokenRefreshIntent(Session.a());
            if (createTokenRefreshIntent == null || !Session.c.bindService(createTokenRefreshIntent, this, 1)) {
                b();
            } else {
                Session.this.a(new Date());
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.b = new Messenger(iBinder);
            c();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            b();
            try {
                Session.c.unbindService(this);
            } catch (IllegalArgumentException unused) {
            }
        }

        /* access modifiers changed from: private */
        public void b() {
            if (Session.this.q == this) {
                Session.this.q = null;
            }
        }

        private void c() {
            Bundle bundle = new Bundle();
            bundle.putString("access_token", Session.this.e().getToken());
            Message obtain = Message.obtain();
            obtain.setData(bundle);
            obtain.replyTo = this.a;
            try {
                this.b.send(obtain);
            } catch (RemoteException unused) {
                b();
            }
        }
    }

    static class TokenRefreshRequestHandler extends Handler {
        private WeakReference<Session> a;
        private WeakReference<TokenRefreshRequest> b;

        TokenRefreshRequestHandler(Session session, TokenRefreshRequest tokenRefreshRequest) {
            super(Looper.getMainLooper());
            this.a = new WeakReference<>(session);
            this.b = new WeakReference<>(tokenRefreshRequest);
        }

        public void handleMessage(Message message) {
            String string = message.getData().getString("access_token");
            Session session = (Session) this.a.get();
            if (!(session == null || string == null)) {
                session.a(message.getData());
            }
            TokenRefreshRequest tokenRefreshRequest = (TokenRefreshRequest) this.b.get();
            if (tokenRefreshRequest != null) {
                Session.c.unbindService(tokenRefreshRequest);
                tokenRefreshRequest.b();
            }
        }
    }

    public int hashCode() {
        return 0;
    }

    private Session(String str, SessionState sessionState, AccessToken accessToken, Date date, boolean z, AuthorizationRequest authorizationRequest) {
        this.h = new Date(0);
        this.o = new Object();
        this.e = str;
        this.f = sessionState;
        this.g = accessToken;
        this.h = date;
        this.i = authorizationRequest;
        this.m = new Handler(Looper.getMainLooper());
        this.q = null;
        this.p = null;
        this.l = new ArrayList();
    }

    public Session(Context context) {
        this(context, null, null, true);
    }

    Session(Context context, String str, TokenCachingStrategy tokenCachingStrategy) {
        this(context, str, tokenCachingStrategy, true);
    }

    Session(Context context, String str, TokenCachingStrategy tokenCachingStrategy, boolean z) {
        this.h = new Date(0);
        this.o = new Object();
        if (context != null && str == null) {
            str = Utility.getMetadataApplicationId(context);
        }
        Validate.notNull(str, "applicationId");
        a(context);
        if (tokenCachingStrategy == null) {
            tokenCachingStrategy = new SharedPreferencesTokenCachingStrategy(c);
        }
        this.e = str;
        this.p = tokenCachingStrategy;
        this.f = SessionState.CREATED;
        Bundle bundle = null;
        this.i = null;
        this.l = new ArrayList();
        this.m = new Handler(Looper.getMainLooper());
        if (z) {
            bundle = tokenCachingStrategy.load();
        }
        if (TokenCachingStrategy.hasTokenInformation(bundle)) {
            Date a2 = TokenCachingStrategy.a(bundle, TokenCachingStrategy.EXPIRATION_DATE_KEY);
            Date date = new Date();
            if (a2 == null || a2.before(date)) {
                tokenCachingStrategy.clear();
                this.g = AccessToken.a();
                return;
            }
            this.g = AccessToken.a(bundle);
            this.f = SessionState.CREATED_TOKEN_LOADED;
            return;
        }
        this.g = AccessToken.a();
    }

    public final Bundle getAuthorizationBundle() {
        Bundle bundle;
        synchronized (this.o) {
            bundle = this.k;
        }
        return bundle;
    }

    public final boolean isOpened() {
        boolean isOpened;
        synchronized (this.o) {
            isOpened = this.f.isOpened();
        }
        return isOpened;
    }

    public final boolean isClosed() {
        boolean isClosed;
        synchronized (this.o) {
            isClosed = this.f.isClosed();
        }
        return isClosed;
    }

    public final SessionState getState() {
        SessionState sessionState;
        synchronized (this.o) {
            sessionState = this.f;
        }
        return sessionState;
    }

    public final String getApplicationId() {
        return this.e;
    }

    public final String getAccessToken() {
        String token;
        synchronized (this.o) {
            token = this.g == null ? null : this.g.getToken();
        }
        return token;
    }

    public final Date getExpirationDate() {
        Date expires;
        synchronized (this.o) {
            expires = this.g == null ? null : this.g.getExpires();
        }
        return expires;
    }

    public final List<String> getPermissions() {
        List<String> permissions;
        synchronized (this.o) {
            permissions = this.g == null ? null : this.g.getPermissions();
        }
        return permissions;
    }

    public boolean isPermissionGranted(String str) {
        List permissions = getPermissions();
        if (permissions != null) {
            return permissions.contains(str);
        }
        return false;
    }

    public final List<String> getDeclinedPermissions() {
        List<String> declinedPermissions;
        synchronized (this.o) {
            declinedPermissions = this.g == null ? null : this.g.getDeclinedPermissions();
        }
        return declinedPermissions;
    }

    public final void openForRead(OpenRequest openRequest) {
        a(openRequest, SessionAuthorizationType.READ);
    }

    public final void openForPublish(OpenRequest openRequest) {
        a(openRequest, SessionAuthorizationType.PUBLISH);
    }

    public final void open(AccessToken accessToken, StatusCallback statusCallback) {
        synchronized (this.o) {
            if (this.i != null) {
                throw new UnsupportedOperationException("Session: an attempt was made to open a session that has a pending request.");
            } else if (this.f.isClosed()) {
                throw new UnsupportedOperationException("Session: an attempt was made to open a previously-closed session.");
            } else if (this.f == SessionState.CREATED || this.f == SessionState.CREATED_TOKEN_LOADED) {
                if (statusCallback != null) {
                    addCallback(statusCallback);
                }
                this.g = accessToken;
                if (this.p != null) {
                    this.p.save(accessToken.b());
                }
                SessionState sessionState = this.f;
                this.f = SessionState.OPENED;
                a(sessionState, this.f, (Exception) null);
            } else {
                throw new UnsupportedOperationException("Session: an attempt was made to open an already opened session.");
            }
        }
        i();
    }

    public final void requestNewReadPermissions(NewPermissionsRequest newPermissionsRequest) {
        a(newPermissionsRequest, SessionAuthorizationType.READ);
    }

    public final void requestNewPublishPermissions(NewPermissionsRequest newPermissionsRequest) {
        a(newPermissionsRequest, SessionAuthorizationType.PUBLISH);
    }

    public final void refreshPermissions() {
        Request request = new Request(this, "me/permissions");
        request.setCallback(new Callback() {
            public void onCompleted(Response response) {
                PermissionsPair a2 = Session.a(response);
                if (a2 != null) {
                    synchronized (Session.this.o) {
                        Session.this.g = AccessToken.a(Session.this.g, a2.a(), a2.b());
                        Session.this.a(Session.this.f, SessionState.OPENED_TOKEN_UPDATED, (Exception) null);
                    }
                }
            }
        });
        request.executeAsync();
    }

    static PermissionsPair a(Response response) {
        if (response.getError() != null) {
            return null;
        }
        GraphMultiResult graphMultiResult = (GraphMultiResult) response.getGraphObjectAs(GraphMultiResult.class);
        if (graphMultiResult == null) {
            return null;
        }
        GraphObjectList<GraphObject> data = graphMultiResult.getData();
        if (data == null || data.size() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(data.size());
        ArrayList arrayList2 = new ArrayList(data.size());
        GraphObject graphObject = (GraphObject) data.get(0);
        if (graphObject.getProperty("permission") != null) {
            for (GraphObject graphObject2 : data) {
                String str = (String) graphObject2.getProperty("permission");
                if (!str.equals("installed")) {
                    String str2 = (String) graphObject2.getProperty("status");
                    if (str2.equals("granted")) {
                        arrayList.add(str);
                    } else if (str2.equals("declined")) {
                        arrayList2.add(str);
                    }
                }
            }
        } else {
            for (Entry entry : graphObject.asMap().entrySet()) {
                if (!((String) entry.getKey()).equals("installed") && ((Integer) entry.getValue()).intValue() == 1) {
                    arrayList.add(entry.getKey());
                }
            }
        }
        return new PermissionsPair(arrayList, arrayList2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        if (r7 == null) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
        r2 = (com.facebook.AuthorizationClient.Result) r7.getSerializableExtra("com.facebook.LoginActivity:Result");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0027, code lost:
        if (r2 == null) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        a(r6, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        if (r3.j == null) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
        r3.j.a(r5, r6, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0036, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
        if (r6 != 0) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0039, code lost:
        r4 = new com.facebook.FacebookOperationCanceledException("User canceled operation.");
        r5 = com.facebook.AuthorizationClient.Result.Code.CANCEL;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        r5 = r4;
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0045, code lost:
        if (r4 != null) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0047, code lost:
        r4 = new com.facebook.FacebookException("Unexpected call to Session.onActivityResult");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004e, code lost:
        a(r5, null, (java.lang.Exception) r4);
        a((com.facebook.AccessToken) null, (java.lang.Exception) r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0054, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
        r4 = com.facebook.AuthorizationClient.Result.Code.ERROR;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onActivityResult(android.app.Activity r4, int r5, int r6, android.content.Intent r7) {
        /*
            r3 = this;
            java.lang.String r0 = "currentActivity"
            com.facebook.internal.Validate.notNull(r4, r0)
            a(r4)
            java.lang.Object r4 = r3.o
            monitor-enter(r4)
            com.facebook.Session$AuthorizationRequest r0 = r3.i     // Catch:{ all -> 0x0058 }
            if (r0 == 0) goto L_0x0055
            com.facebook.Session$AuthorizationRequest r0 = r3.i     // Catch:{ all -> 0x0058 }
            int r0 = r0.c()     // Catch:{ all -> 0x0058 }
            if (r5 == r0) goto L_0x0018
            goto L_0x0055
        L_0x0018:
            monitor-exit(r4)     // Catch:{ all -> 0x0058 }
            com.facebook.AuthorizationClient$Result$Code r4 = com.facebook.AuthorizationClient.Result.Code.ERROR
            r0 = 1
            r1 = 0
            if (r7 == 0) goto L_0x0037
            java.lang.String r2 = "com.facebook.LoginActivity:Result"
            java.io.Serializable r2 = r7.getSerializableExtra(r2)
            com.facebook.AuthorizationClient$Result r2 = (com.facebook.AuthorizationClient.Result) r2
            if (r2 == 0) goto L_0x002d
            r3.a(r6, r2)
            return r0
        L_0x002d:
            com.facebook.AuthorizationClient r2 = r3.j
            if (r2 == 0) goto L_0x0043
            com.facebook.AuthorizationClient r4 = r3.j
            r4.a(r5, r6, r7)
            return r0
        L_0x0037:
            if (r6 != 0) goto L_0x0043
            com.facebook.FacebookOperationCanceledException r4 = new com.facebook.FacebookOperationCanceledException
            java.lang.String r5 = "User canceled operation."
            r4.<init>(r5)
            com.facebook.AuthorizationClient$Result$Code r5 = com.facebook.AuthorizationClient.Result.Code.CANCEL
            goto L_0x0045
        L_0x0043:
            r5 = r4
            r4 = r1
        L_0x0045:
            if (r4 != 0) goto L_0x004e
            com.facebook.FacebookException r4 = new com.facebook.FacebookException
            java.lang.String r6 = "Unexpected call to Session.onActivityResult"
            r4.<init>(r6)
        L_0x004e:
            r3.a(r5, r1, r4)
            r3.a(r1, r4)
            return r0
        L_0x0055:
            r5 = 0
            monitor-exit(r4)     // Catch:{ all -> 0x0058 }
            return r5
        L_0x0058:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0058 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Session.onActivityResult(android.app.Activity, int, int, android.content.Intent):boolean");
    }

    public final void close() {
        synchronized (this.o) {
            SessionState sessionState = this.f;
            switch (this.f) {
                case CREATED:
                case OPENING:
                    this.f = SessionState.CLOSED_LOGIN_FAILED;
                    a(sessionState, this.f, (Exception) new FacebookException("Log in attempt aborted."));
                    break;
                case CREATED_TOKEN_LOADED:
                case OPENED:
                case OPENED_TOKEN_UPDATED:
                    this.f = SessionState.CLOSED;
                    a(sessionState, this.f, (Exception) null);
                    break;
            }
        }
    }

    public final void closeAndClearTokenInformation() {
        if (this.p != null) {
            this.p.clear();
        }
        Utility.clearFacebookCookies(c);
        Utility.clearCaches(c);
        close();
    }

    public final void addCallback(StatusCallback statusCallback) {
        synchronized (this.l) {
            if (statusCallback != null) {
                try {
                    if (!this.l.contains(statusCallback)) {
                        this.l.add(statusCallback);
                    }
                } finally {
                }
            }
        }
    }

    public final void removeCallback(StatusCallback statusCallback) {
        synchronized (this.l) {
            this.l.remove(statusCallback);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{Session");
        sb.append(" state:");
        sb.append(this.f);
        sb.append(", token:");
        sb.append(this.g == null ? "null" : this.g);
        sb.append(", appId:");
        sb.append(this.e == null ? "null" : this.e);
        sb.append("}");
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0037, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.os.Bundle r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.o
            monitor-enter(r0)
            com.facebook.SessionState r1 = r4.f     // Catch:{ all -> 0x0050 }
            int[] r2 = com.facebook.Session.AnonymousClass5.a     // Catch:{ all -> 0x0050 }
            com.facebook.SessionState r3 = r4.f     // Catch:{ all -> 0x0050 }
            int r3 = r3.ordinal()     // Catch:{ all -> 0x0050 }
            r2 = r2[r3]     // Catch:{ all -> 0x0050 }
            switch(r2) {
                case 4: goto L_0x0015;
                case 5: goto L_0x001f;
                default: goto L_0x0012;
            }     // Catch:{ all -> 0x0050 }
        L_0x0012:
            java.lang.String r5 = TAG     // Catch:{ all -> 0x0050 }
            goto L_0x0038
        L_0x0015:
            com.facebook.SessionState r2 = com.facebook.SessionState.OPENED_TOKEN_UPDATED     // Catch:{ all -> 0x0050 }
            r4.f = r2     // Catch:{ all -> 0x0050 }
            com.facebook.SessionState r2 = r4.f     // Catch:{ all -> 0x0050 }
            r3 = 0
            r4.a(r1, r2, r3)     // Catch:{ all -> 0x0050 }
        L_0x001f:
            com.facebook.AccessToken r1 = r4.g     // Catch:{ all -> 0x0050 }
            com.facebook.AccessToken r5 = com.facebook.AccessToken.a(r1, r5)     // Catch:{ all -> 0x0050 }
            r4.g = r5     // Catch:{ all -> 0x0050 }
            com.facebook.TokenCachingStrategy r5 = r4.p     // Catch:{ all -> 0x0050 }
            if (r5 == 0) goto L_0x0036
            com.facebook.TokenCachingStrategy r5 = r4.p     // Catch:{ all -> 0x0050 }
            com.facebook.AccessToken r1 = r4.g     // Catch:{ all -> 0x0050 }
            android.os.Bundle r1 = r1.b()     // Catch:{ all -> 0x0050 }
            r5.save(r1)     // Catch:{ all -> 0x0050 }
        L_0x0036:
            monitor-exit(r0)     // Catch:{ all -> 0x0050 }
            return
        L_0x0038:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0050 }
            r1.<init>()     // Catch:{ all -> 0x0050 }
            java.lang.String r2 = "refreshToken ignored in state "
            r1.append(r2)     // Catch:{ all -> 0x0050 }
            com.facebook.SessionState r2 = r4.f     // Catch:{ all -> 0x0050 }
            r1.append(r2)     // Catch:{ all -> 0x0050 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0050 }
            android.util.Log.d(r5, r1)     // Catch:{ all -> 0x0050 }
            monitor-exit(r0)     // Catch:{ all -> 0x0050 }
            return
        L_0x0050:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0050 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Session.a(android.os.Bundle):void");
    }

    private Object writeReplace() {
        SerializationProxyV1 serializationProxyV1 = new SerializationProxyV1(this.e, this.f, this.g, this.h, false, this.i);
        return serializationProxyV1;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Cannot readObject, serialization proxy required");
    }

    public static final void saveSession(Session session, Bundle bundle) {
        if (bundle != null && session != null && !bundle.containsKey("com.facebook.sdk.Session.saveSessionKey")) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                new ObjectOutputStream(byteArrayOutputStream).writeObject(session);
                bundle.putByteArray("com.facebook.sdk.Session.saveSessionKey", byteArrayOutputStream.toByteArray());
                bundle.putBundle("com.facebook.sdk.Session.authBundleKey", session.k);
            } catch (IOException e2) {
                throw new FacebookException("Unable to save session.", e2);
            }
        }
    }

    public static final Session restoreSession(Context context, TokenCachingStrategy tokenCachingStrategy, StatusCallback statusCallback, Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        byte[] byteArray = bundle.getByteArray("com.facebook.sdk.Session.saveSessionKey");
        if (byteArray != null) {
            try {
                Session session = (Session) new ObjectInputStream(new ByteArrayInputStream(byteArray)).readObject();
                a(context);
                if (tokenCachingStrategy != null) {
                    session.p = tokenCachingStrategy;
                } else {
                    session.p = new SharedPreferencesTokenCachingStrategy(context);
                }
                if (statusCallback != null) {
                    session.addCallback(statusCallback);
                }
                session.k = bundle.getBundle("com.facebook.sdk.Session.authBundleKey");
                return session;
            } catch (ClassNotFoundException e2) {
                Log.w(TAG, "Unable to restore session", e2);
            } catch (IOException e3) {
                Log.w(TAG, "Unable to restore session.", e3);
            }
        }
        return null;
    }

    public static final Session getActiveSession() {
        Session session;
        synchronized (a) {
            session = b;
        }
        return session;
    }

    public static final void setActiveSession(Session session) {
        synchronized (a) {
            if (session != b) {
                Session session2 = b;
                if (session2 != null) {
                    session2.close();
                }
                b = session;
                if (session2 != null) {
                    a(ACTION_ACTIVE_SESSION_UNSET);
                }
                if (session != null) {
                    a(ACTION_ACTIVE_SESSION_SET);
                    if (session.isOpened()) {
                        a(ACTION_ACTIVE_SESSION_OPENED);
                    }
                }
            }
        }
    }

    public static Session openActiveSessionFromCache(Context context) {
        return a(context, false, (OpenRequest) null);
    }

    public static Session openActiveSession(Activity activity, boolean z, StatusCallback statusCallback) {
        return a((Context) activity, z, new OpenRequest(activity).setCallback(statusCallback));
    }

    public static Session openActiveSession(Activity activity, boolean z, List<String> list, StatusCallback statusCallback) {
        return a((Context) activity, z, new OpenRequest(activity).setCallback(statusCallback).setPermissions((List) list));
    }

    public static Session openActiveSession(Context context, Fragment fragment, boolean z, StatusCallback statusCallback) {
        return a(context, z, new OpenRequest(fragment).setCallback(statusCallback));
    }

    public static Session openActiveSession(Context context, Fragment fragment, boolean z, List<String> list, StatusCallback statusCallback) {
        return a(context, z, new OpenRequest(fragment).setCallback(statusCallback).setPermissions((List) list));
    }

    public static Session openActiveSessionWithAccessToken(Context context, AccessToken accessToken, StatusCallback statusCallback) {
        Session session = new Session(context, null, null, false);
        setActiveSession(session);
        session.open(accessToken, statusCallback);
        return session;
    }

    private static Session a(Context context, boolean z, OpenRequest openRequest) {
        Session build = new Builder(context).build();
        if (!SessionState.CREATED_TOKEN_LOADED.equals(build.getState()) && !z) {
            return null;
        }
        setActiveSession(build);
        build.openForRead(openRequest);
        return build;
    }

    static Context a() {
        return c;
    }

    static void a(Context context) {
        if (context != null && c == null) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                context = applicationContext;
            }
            c = context;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(AuthorizationRequest authorizationRequest) {
        authorizationRequest.a(this.e);
        i();
        g();
        boolean c2 = c(authorizationRequest);
        this.i.k.put("try_login_activity", c2 ? "1" : "0");
        if (!c2 && authorizationRequest.e) {
            this.i.k.put("try_legacy", "1");
            e(authorizationRequest);
            c2 = true;
        }
        if (!c2) {
            synchronized (this.o) {
                SessionState sessionState = this.f;
                switch (this.f) {
                    case CLOSED:
                    case CLOSED_LOGIN_FAILED:
                        return;
                    default:
                        this.f = SessionState.CLOSED_LOGIN_FAILED;
                        FacebookException facebookException = new FacebookException("Log in attempt failed: LoginActivity could not be started, and not legacy request");
                        a(Code.ERROR, null, (Exception) facebookException);
                        a(sessionState, this.f, (Exception) facebookException);
                        break;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0081, code lost:
        if (r1 != com.facebook.SessionState.OPENING) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0083, code lost:
        a((com.facebook.Session.AuthorizationRequest) r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0086, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.facebook.Session.OpenRequest r4, com.facebook.internal.SessionAuthorizationType r5) {
        /*
            r3 = this;
            r3.a(r4, r5)
            r3.b(r4)
            java.lang.Object r5 = r3.o
            monitor-enter(r5)
            com.facebook.Session$AuthorizationRequest r0 = r3.i     // Catch:{ all -> 0x0087 }
            if (r0 == 0) goto L_0x001d
            com.facebook.SessionState r4 = r3.f     // Catch:{ all -> 0x0087 }
            com.facebook.SessionState r0 = r3.f     // Catch:{ all -> 0x0087 }
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x0087 }
            java.lang.String r2 = "Session: an attempt was made to open a session that has a pending request."
            r1.<init>(r2)     // Catch:{ all -> 0x0087 }
            r3.a(r4, r0, r1)     // Catch:{ all -> 0x0087 }
            monitor-exit(r5)     // Catch:{ all -> 0x0087 }
            return
        L_0x001d:
            com.facebook.SessionState r0 = r3.f     // Catch:{ all -> 0x0087 }
            int[] r1 = com.facebook.Session.AnonymousClass5.a     // Catch:{ all -> 0x0087 }
            com.facebook.SessionState r2 = r3.f     // Catch:{ all -> 0x0087 }
            int r2 = r2.ordinal()     // Catch:{ all -> 0x0087 }
            r1 = r1[r2]     // Catch:{ all -> 0x0087 }
            r2 = 1
            if (r1 == r2) goto L_0x0061
            r2 = 3
            if (r1 == r2) goto L_0x0037
            java.lang.UnsupportedOperationException r4 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x0087 }
            java.lang.String r0 = "Session: an attempt was made to open an already opened session."
            r4.<init>(r0)     // Catch:{ all -> 0x0087 }
            throw r4     // Catch:{ all -> 0x0087 }
        L_0x0037:
            if (r4 == 0) goto L_0x0053
            java.util.List r1 = r4.d()     // Catch:{ all -> 0x0087 }
            boolean r1 = com.facebook.internal.Utility.isNullOrEmpty(r1)     // Catch:{ all -> 0x0087 }
            if (r1 != 0) goto L_0x0053
            java.util.List r1 = r4.d()     // Catch:{ all -> 0x0087 }
            java.util.List r2 = r3.getPermissions()     // Catch:{ all -> 0x0087 }
            boolean r1 = com.facebook.internal.Utility.isSubset(r1, r2)     // Catch:{ all -> 0x0087 }
            if (r1 != 0) goto L_0x0053
            r3.i = r4     // Catch:{ all -> 0x0087 }
        L_0x0053:
            com.facebook.Session$AuthorizationRequest r1 = r3.i     // Catch:{ all -> 0x0087 }
            if (r1 != 0) goto L_0x005c
            com.facebook.SessionState r1 = com.facebook.SessionState.OPENED     // Catch:{ all -> 0x0087 }
            r3.f = r1     // Catch:{ all -> 0x0087 }
            goto L_0x0071
        L_0x005c:
            com.facebook.SessionState r1 = com.facebook.SessionState.OPENING     // Catch:{ all -> 0x0087 }
            r3.f = r1     // Catch:{ all -> 0x0087 }
            goto L_0x0071
        L_0x0061:
            com.facebook.SessionState r1 = com.facebook.SessionState.OPENING     // Catch:{ all -> 0x0087 }
            r3.f = r1     // Catch:{ all -> 0x0087 }
            if (r4 != 0) goto L_0x006f
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0087 }
            java.lang.String r0 = "openRequest cannot be null when opening a new Session"
            r4.<init>(r0)     // Catch:{ all -> 0x0087 }
            throw r4     // Catch:{ all -> 0x0087 }
        L_0x006f:
            r3.i = r4     // Catch:{ all -> 0x0087 }
        L_0x0071:
            if (r4 == 0) goto L_0x007a
            com.facebook.Session$StatusCallback r2 = r4.a()     // Catch:{ all -> 0x0087 }
            r3.addCallback(r2)     // Catch:{ all -> 0x0087 }
        L_0x007a:
            r2 = 0
            r3.a(r0, r1, r2)     // Catch:{ all -> 0x0087 }
            monitor-exit(r5)     // Catch:{ all -> 0x0087 }
            com.facebook.SessionState r5 = com.facebook.SessionState.OPENING
            if (r1 != r5) goto L_0x0086
            r3.a(r4)
        L_0x0086:
            return
        L_0x0087:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0087 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Session.a(com.facebook.Session$OpenRequest, com.facebook.internal.SessionAuthorizationType):void");
    }

    private void a(NewPermissionsRequest newPermissionsRequest, SessionAuthorizationType sessionAuthorizationType) {
        a((AuthorizationRequest) newPermissionsRequest, sessionAuthorizationType);
        b((AuthorizationRequest) newPermissionsRequest);
        if (newPermissionsRequest != null) {
            synchronized (this.o) {
                if (this.i != null) {
                    throw new UnsupportedOperationException("Session: an attempt was made to request new permissions for a session that has a pending request.");
                } else if (this.f.isOpened()) {
                    this.i = newPermissionsRequest;
                } else if (this.f.isClosed()) {
                    throw new UnsupportedOperationException("Session: an attempt was made to request new permissions for a session that has been closed.");
                } else {
                    throw new UnsupportedOperationException("Session: an attempt was made to request new permissions for a session that is not currently open.");
                }
            }
            newPermissionsRequest.b(getAccessToken());
            addCallback(newPermissionsRequest.a());
            a((AuthorizationRequest) newPermissionsRequest);
        }
    }

    private void b(AuthorizationRequest authorizationRequest) {
        if (authorizationRequest != null && !authorizationRequest.e) {
            Intent intent = new Intent();
            intent.setClass(a(), LoginActivity.class);
            if (!a(intent)) {
                throw new FacebookException(String.format("Cannot use SessionLoginBehavior %s when %s is not declared as an activity in AndroidManifest.xml", new Object[]{authorizationRequest.b(), LoginActivity.class.getName()}));
            }
        }
    }

    private void a(AuthorizationRequest authorizationRequest, SessionAuthorizationType sessionAuthorizationType) {
        if (authorizationRequest != null && !Utility.isNullOrEmpty((Collection<T>) authorizationRequest.d())) {
            for (String str : authorizationRequest.d()) {
                if (isPublishPermission(str)) {
                    if (SessionAuthorizationType.READ.equals(sessionAuthorizationType)) {
                        throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", new Object[]{str}));
                    }
                } else if (SessionAuthorizationType.PUBLISH.equals(sessionAuthorizationType)) {
                    Log.w(TAG, String.format("Should not pass a read permission (%s) to a request for publish or manage authorization", new Object[]{str}));
                }
            }
        } else if (SessionAuthorizationType.PUBLISH.equals(sessionAuthorizationType)) {
            throw new FacebookException("Cannot request publish or manage authorization with no permissions.");
        }
    }

    public static boolean isPublishPermission(String str) {
        return str != null && (str.startsWith("publish") || str.startsWith("manage") || d.contains(str));
    }

    /* access modifiers changed from: private */
    public void a(int i2, Result result) {
        Exception exc;
        AccessToken accessToken;
        if (i2 == -1) {
            if (result.a == Code.SUCCESS) {
                accessToken = result.b;
                exc = null;
                a(result.a, result.f, exc);
                this.j = null;
                a(accessToken, exc);
            }
            exc = new FacebookAuthorizationException(result.c);
        } else if (i2 == 0) {
            exc = new FacebookOperationCanceledException(result.c);
        } else {
            exc = null;
            accessToken = null;
            a(result.a, result.f, exc);
            this.j = null;
            a(accessToken, exc);
        }
        accessToken = null;
        a(result.a, result.f, exc);
        this.j = null;
        a(accessToken, exc);
    }

    private void g() {
        Bundle d2 = AuthorizationClient.d(this.i.f());
        d2.putLong("1_timestamp_ms", System.currentTimeMillis());
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("login_behavior", this.i.b.toString());
            jSONObject.put("request_code", this.i.c);
            jSONObject.put("is_legacy", this.i.e);
            jSONObject.put(NativeProtocol.RESULT_ARGS_PERMISSIONS, TextUtils.join(",", this.i.f));
            jSONObject.put(ServerProtocol.DIALOG_PARAM_DEFAULT_AUDIENCE, this.i.g.toString());
            d2.putString("6_extras", jSONObject.toString());
        } catch (JSONException unused) {
        }
        h().logSdkEvent("fb_mobile_login_start", null, d2);
    }

    private void a(Code code, Map<String, String> map, Exception exc) {
        Bundle bundle;
        if (this.i == null) {
            bundle = AuthorizationClient.d("");
            bundle.putString("2_result", Code.ERROR.a());
            bundle.putString("5_error_message", "Unexpected call to logAuthorizationComplete with null pendingAuthorizationRequest.");
        } else {
            Bundle d2 = AuthorizationClient.d(this.i.f());
            if (code != null) {
                d2.putString("2_result", code.a());
            }
            if (!(exc == null || exc.getMessage() == null)) {
                d2.putString("5_error_message", exc.getMessage());
            }
            JSONObject jSONObject = !this.i.k.isEmpty() ? new JSONObject(this.i.k) : null;
            if (map != null) {
                if (jSONObject == null) {
                    jSONObject = new JSONObject();
                }
                try {
                    for (Entry entry : map.entrySet()) {
                        jSONObject.put((String) entry.getKey(), entry.getValue());
                    }
                } catch (JSONException unused) {
                }
            }
            if (jSONObject != null) {
                d2.putString("6_extras", jSONObject.toString());
            }
            bundle = d2;
        }
        bundle.putLong("1_timestamp_ms", System.currentTimeMillis());
        h().logSdkEvent("fb_mobile_login_complete", null, bundle);
    }

    private boolean c(AuthorizationRequest authorizationRequest) {
        Intent d2 = d(authorizationRequest);
        if (!a(d2)) {
            return false;
        }
        try {
            authorizationRequest.e().a(d2, authorizationRequest.c());
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }

    private boolean a(Intent intent) {
        return a().getPackageManager().resolveActivity(intent, 0) != null;
    }

    private Intent d(AuthorizationRequest authorizationRequest) {
        Intent intent = new Intent();
        intent.setClass(a(), LoginActivity.class);
        intent.setAction(authorizationRequest.b().toString());
        intent.putExtras(LoginActivity.a(authorizationRequest.g()));
        return intent;
    }

    private void e(AuthorizationRequest authorizationRequest) {
        this.j = new AuthorizationClient();
        this.j.a((OnCompletedListener) new OnCompletedListener() {
            public void a(Result result) {
                Session.this.a(result.a == Code.CANCEL ? 0 : -1, result);
            }
        });
        this.j.a(a());
        this.j.a(authorizationRequest.g());
    }

    /* access modifiers changed from: 0000 */
    public void a(AccessToken accessToken, Exception exc) {
        if (accessToken != null && accessToken.c()) {
            accessToken = null;
            exc = new FacebookException("Invalid access token.");
        }
        synchronized (this.o) {
            switch (this.f) {
                case CREATED:
                case CREATED_TOKEN_LOADED:
                case CLOSED:
                case CLOSED_LOGIN_FAILED:
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unexpected call to finishAuthOrReauth in state ");
                    sb.append(this.f);
                    Log.d(str, sb.toString());
                    break;
                case OPENING:
                    b(accessToken, exc);
                    break;
                case OPENED:
                case OPENED_TOKEN_UPDATED:
                    c(accessToken, exc);
                    break;
            }
        }
    }

    private void b(AccessToken accessToken, Exception exc) {
        SessionState sessionState = this.f;
        if (accessToken != null) {
            this.g = accessToken;
            a(accessToken);
            this.f = SessionState.OPENED;
        } else if (exc != null) {
            this.f = SessionState.CLOSED_LOGIN_FAILED;
        }
        this.i = null;
        a(sessionState, this.f, exc);
    }

    private void c(AccessToken accessToken, Exception exc) {
        SessionState sessionState = this.f;
        if (accessToken != null) {
            this.g = accessToken;
            a(accessToken);
            this.f = SessionState.OPENED_TOKEN_UPDATED;
        }
        this.i = null;
        a(sessionState, this.f, exc);
    }

    private void a(AccessToken accessToken) {
        if (accessToken != null && this.p != null) {
            this.p.save(accessToken.b());
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(SessionState sessionState, final SessionState sessionState2, final Exception exc) {
        if (sessionState != sessionState2 || sessionState == SessionState.OPENED_TOKEN_UPDATED || exc != null) {
            if (sessionState2.isClosed()) {
                this.g = AccessToken.a();
            }
            b(this.m, (Runnable) new Runnable() {
                public void run() {
                    synchronized (Session.this.l) {
                        for (final StatusCallback statusCallback : Session.this.l) {
                            Session.b(Session.this.m, (Runnable) new Runnable() {
                                public void run() {
                                    statusCallback.call(Session.this, sessionState2, exc);
                                }
                            });
                        }
                    }
                }
            });
            if (this == b && sessionState.isOpened() != sessionState2.isOpened()) {
                if (sessionState2.isOpened()) {
                    a(ACTION_ACTIVE_SESSION_OPENED);
                } else {
                    a(ACTION_ACTIVE_SESSION_CLOSED);
                }
            }
        }
    }

    static void a(String str) {
        LocalBroadcastManager.getInstance(a()).sendBroadcast(new Intent(str));
    }

    /* access modifiers changed from: private */
    public static void b(Handler handler, Runnable runnable) {
        if (handler != null) {
            handler.post(runnable);
        } else {
            Settings.getExecutor().execute(runnable);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (d()) {
            c();
        }
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        TokenRefreshRequest tokenRefreshRequest;
        synchronized (this.o) {
            if (this.q == null) {
                tokenRefreshRequest = new TokenRefreshRequest();
                this.q = tokenRefreshRequest;
            } else {
                tokenRefreshRequest = null;
            }
        }
        if (tokenRefreshRequest != null) {
            tokenRefreshRequest.a();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        boolean z = false;
        if (this.q != null) {
            return false;
        }
        Date date = new Date();
        if (this.f.isOpened() && this.g.getSource().a() && date.getTime() - this.h.getTime() > 3600000 && date.getTime() - this.g.getLastRefresh().getTime() > 86400000) {
            z = true;
        }
        return z;
    }

    private AppEventsLogger h() {
        AppEventsLogger appEventsLogger;
        synchronized (this.o) {
            if (this.r == null) {
                this.r = AppEventsLogger.newLogger(c, this.e);
            }
            appEventsLogger = this.r;
        }
        return appEventsLogger;
    }

    /* access modifiers changed from: 0000 */
    public AccessToken e() {
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public void a(Date date) {
        this.h = date;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Session)) {
            return false;
        }
        Session session = (Session) obj;
        if (a((Object) session.e, (Object) this.e) && a((Object) session.k, (Object) this.k) && a((Object) session.f, (Object) this.f) && a((Object) session.getExpirationDate(), (Object) getExpirationDate())) {
            z = true;
        }
        return z;
    }

    private static boolean a(Object obj, Object obj2) {
        if (obj != null) {
            return obj.equals(obj2);
        }
        return obj2 == null;
    }

    private void i() {
        AutoPublishAsyncTask autoPublishAsyncTask;
        synchronized (this) {
            if (this.n == null && Settings.getShouldAutoPublishInstall()) {
                String str = this.e;
                if (str != null) {
                    autoPublishAsyncTask = new AutoPublishAsyncTask(str, c);
                    this.n = autoPublishAsyncTask;
                }
            }
            autoPublishAsyncTask = null;
        }
        if (autoPublishAsyncTask != null) {
            autoPublishAsyncTask.execute(new Void[0]);
        }
    }
}
