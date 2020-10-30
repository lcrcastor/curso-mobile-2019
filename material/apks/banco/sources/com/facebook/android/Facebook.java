package com.facebook.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import com.appsflyer.AppsFlyerLib;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.LegacyHelper;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.TokenCachingStrategy;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Facebook {
    @Deprecated
    public static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    @Deprecated
    public static final Uri ATTRIBUTION_ID_CONTENT_URI = Uri.parse(AppsFlyerLib.ATTRIBUTION_ID_CONTENT_URI);
    @Deprecated
    public static final String CANCEL_URI = "fbconnect://cancel";
    @Deprecated
    protected static String DIALOG_BASE_URL = "https://m.facebook.com/dialog/";
    @Deprecated
    public static final String EXPIRES = "expires_in";
    @Deprecated
    public static final String FB_APP_SIGNATURE = "30820268308201d102044a9c4610300d06092a864886f70d0101040500307a310b3009060355040613025553310b3009060355040813024341311230100603550407130950616c6f20416c746f31183016060355040a130f46616365626f6f6b204d6f62696c653111300f060355040b130846616365626f6f6b311d301b0603550403131446616365626f6f6b20436f72706f726174696f6e3020170d3039303833313231353231365a180f32303530303932353231353231365a307a310b3009060355040613025553310b3009060355040813024341311230100603550407130950616c6f20416c746f31183016060355040a130f46616365626f6f6b204d6f62696c653111300f060355040b130846616365626f6f6b311d301b0603550403131446616365626f6f6b20436f72706f726174696f6e30819f300d06092a864886f70d010101050003818d0030818902818100c207d51df8eb8c97d93ba0c8c1002c928fab00dc1b42fca5e66e99cc3023ed2d214d822bc59e8e35ddcf5f44c7ae8ade50d7e0c434f500e6c131f4a2834f987fc46406115de2018ebbb0d5a3c261bd97581ccfef76afc7135a6d59e8855ecd7eacc8f8737e794c60a761c536b72b11fac8e603f5da1a2d54aa103b8a13c0dbc10203010001300d06092a864886f70d0101040500038181005ee9be8bcbb250648d3b741290a82a1c9dc2e76a0af2f2228f1d9f9c4007529c446a70175c5a900d5141812866db46be6559e2141616483998211f4a673149fb2232a10d247663b26a9031e15f84bc1c74d141ff98a02d76f85b2c8ab2571b6469b232d8e768a7f7ca04f7abe4a775615916c07940656b58717457b42bd928a2";
    @Deprecated
    public static final int FORCE_DIALOG_AUTH = -1;
    @Deprecated
    protected static String GRAPH_BASE_URL = "https://graph.facebook.com/";
    @Deprecated
    public static final String REDIRECT_URI = "fbconnect://success";
    @Deprecated
    protected static String RESTSERVER_URL = "https://api.facebook.com/restserver.php";
    @Deprecated
    public static final String SINGLE_SIGN_ON_DISABLED = "service_disabled";
    @Deprecated
    public static final String TOKEN = "access_token";
    private final Object a = new Object();
    /* access modifiers changed from: private */
    public String b = null;
    /* access modifiers changed from: private */
    public long c = 0;
    /* access modifiers changed from: private */
    public long d = 0;
    private String e;
    private Activity f;
    /* access modifiers changed from: private */
    public String[] g;
    private Session h;
    /* access modifiers changed from: private */
    public volatile Session i;
    private boolean j;
    private SetterTokenCachingStrategy k;
    private volatile Session l;

    public interface DialogListener {
        void onCancel();

        void onComplete(Bundle bundle);

        void onError(DialogError dialogError);

        void onFacebookError(FacebookError facebookError);
    }

    public interface ServiceListener {
        void onComplete(Bundle bundle);

        void onError(Error error);

        void onFacebookError(FacebookError facebookError);
    }

    class SetterTokenCachingStrategy extends TokenCachingStrategy {
        private SetterTokenCachingStrategy() {
        }

        public Bundle load() {
            Bundle bundle = new Bundle();
            if (Facebook.this.b != null) {
                TokenCachingStrategy.putToken(bundle, Facebook.this.b);
                TokenCachingStrategy.putExpirationMilliseconds(bundle, Facebook.this.c);
                TokenCachingStrategy.putPermissions(bundle, Facebook.b(Facebook.this.g));
                TokenCachingStrategy.putSource(bundle, AccessTokenSource.WEB_VIEW);
                TokenCachingStrategy.putLastRefreshMilliseconds(bundle, Facebook.this.d);
            }
            return bundle;
        }

        public void save(Bundle bundle) {
            Facebook.this.b = TokenCachingStrategy.getToken(bundle);
            Facebook.this.c = TokenCachingStrategy.getExpirationMilliseconds(bundle);
            Facebook.this.g = Facebook.b(TokenCachingStrategy.getPermissions(bundle));
            Facebook.this.d = TokenCachingStrategy.getLastRefreshMilliseconds(bundle);
        }

        public void clear() {
            Facebook.this.b = null;
        }
    }

    static class TokenRefreshConnectionHandler extends Handler {
        WeakReference<Facebook> a;
        WeakReference<TokenRefreshServiceConnection> b;

        TokenRefreshConnectionHandler(Facebook facebook, TokenRefreshServiceConnection tokenRefreshServiceConnection) {
            this.a = new WeakReference<>(facebook);
            this.b = new WeakReference<>(tokenRefreshServiceConnection);
        }

        public void handleMessage(Message message) {
            Facebook facebook = (Facebook) this.a.get();
            TokenRefreshServiceConnection tokenRefreshServiceConnection = (TokenRefreshServiceConnection) this.b.get();
            if (facebook != null && tokenRefreshServiceConnection != null) {
                String string = message.getData().getString("access_token");
                long j = message.getData().getLong(Facebook.EXPIRES) * 1000;
                if (string != null) {
                    facebook.setAccessToken(string);
                    facebook.setAccessExpires(j);
                    Session b2 = facebook.i;
                    if (b2 != null) {
                        LegacyHelper.extendTokenCompleted(b2, message.getData());
                    }
                    if (tokenRefreshServiceConnection.b != null) {
                        Bundle bundle = (Bundle) message.getData().clone();
                        bundle.putLong(Facebook.EXPIRES, j);
                        tokenRefreshServiceConnection.b.onComplete(bundle);
                    }
                } else if (tokenRefreshServiceConnection.b != null) {
                    String string2 = message.getData().getString("error");
                    if (message.getData().containsKey(NativeProtocol.BRIDGE_ARG_ERROR_CODE)) {
                        tokenRefreshServiceConnection.b.onFacebookError(new FacebookError(string2, null, message.getData().getInt(NativeProtocol.BRIDGE_ARG_ERROR_CODE)));
                    } else {
                        ServiceListener serviceListener = tokenRefreshServiceConnection.b;
                        if (string2 == null) {
                            string2 = "Unknown service error";
                        }
                        serviceListener.onError(new Error(string2));
                    }
                }
                tokenRefreshServiceConnection.c.unbindService(tokenRefreshServiceConnection);
            }
        }
    }

    class TokenRefreshServiceConnection implements ServiceConnection {
        final Messenger a = new Messenger(new TokenRefreshConnectionHandler(Facebook.this, this));
        final ServiceListener b;
        final Context c;
        Messenger d = null;

        public TokenRefreshServiceConnection(Context context, ServiceListener serviceListener) {
            this.c = context;
            this.b = serviceListener;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.d = new Messenger(iBinder);
            a();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.b.onError(new Error("Service disconnected"));
            try {
                this.c.unbindService(this);
            } catch (IllegalArgumentException unused) {
            }
        }

        private void a() {
            Bundle bundle = new Bundle();
            bundle.putString("access_token", Facebook.this.b);
            Message obtain = Message.obtain();
            obtain.setData(bundle);
            obtain.replyTo = this.a;
            try {
                this.d.send(obtain);
            } catch (RemoteException unused) {
                this.b.onError(new Error("Service connection error"));
            }
        }
    }

    @Deprecated
    public Facebook(String str) {
        if (str == null) {
            throw new IllegalArgumentException("You must specify your application ID when instantiating a Facebook object. See README for details.");
        }
        this.e = str;
    }

    @Deprecated
    public void authorize(Activity activity, DialogListener dialogListener) {
        a(activity, new String[0], 32665, SessionLoginBehavior.SSO_WITH_FALLBACK, dialogListener);
    }

    @Deprecated
    public void authorize(Activity activity, String[] strArr, DialogListener dialogListener) {
        a(activity, strArr, 32665, SessionLoginBehavior.SSO_WITH_FALLBACK, dialogListener);
    }

    @Deprecated
    public void authorize(Activity activity, String[] strArr, int i2, DialogListener dialogListener) {
        a(activity, strArr, i2, i2 >= 0 ? SessionLoginBehavior.SSO_WITH_FALLBACK : SessionLoginBehavior.SUPPRESS_SSO, dialogListener);
    }

    private void a(Activity activity, String[] strArr, int i2, SessionLoginBehavior sessionLoginBehavior, final DialogListener dialogListener) {
        a("authorize");
        this.h = new Builder(activity).setApplicationId(this.e).setTokenCachingStrategy(a()).build();
        this.f = activity;
        boolean z = false;
        if (strArr == null) {
            strArr = new String[0];
        }
        this.g = strArr;
        OpenRequest permissions = new OpenRequest(activity).setCallback((StatusCallback) new StatusCallback() {
            public void call(Session session, SessionState sessionState, Exception exc) {
                Facebook.this.a(session, sessionState, exc, dialogListener);
            }
        }).setLoginBehavior(sessionLoginBehavior).setRequestCode(i2).setPermissions(Arrays.asList(this.g));
        Session session = this.h;
        if (this.g.length > 0) {
            z = true;
        }
        a(session, permissions, z);
    }

    private void a(Session session, OpenRequest openRequest, boolean z) {
        openRequest.setIsLegacy(true);
        if (z) {
            session.openForPublish(openRequest);
        } else {
            session.openForRead(openRequest);
        }
    }

    /* access modifiers changed from: private */
    public void a(Session session, SessionState sessionState, Exception exc, DialogListener dialogListener) {
        Bundle authorizationBundle = session.getAuthorizationBundle();
        if (sessionState == SessionState.OPENED) {
            Session session2 = null;
            synchronized (this.a) {
                if (session != this.i) {
                    session2 = this.i;
                    this.i = session;
                    this.j = false;
                }
            }
            if (session2 != null) {
                session2.close();
            }
            dialogListener.onComplete(authorizationBundle);
        } else if (exc == null) {
        } else {
            if (exc instanceof FacebookOperationCanceledException) {
                dialogListener.onCancel();
            } else if (!(exc instanceof FacebookAuthorizationException) || authorizationBundle == null || !authorizationBundle.containsKey(Session.WEB_VIEW_ERROR_CODE_KEY) || !authorizationBundle.containsKey(Session.WEB_VIEW_FAILING_URL_KEY)) {
                dialogListener.onFacebookError(new FacebookError(exc.getMessage()));
            } else {
                dialogListener.onError(new DialogError(exc.getMessage(), authorizationBundle.getInt(Session.WEB_VIEW_ERROR_CODE_KEY), authorizationBundle.getString(Session.WEB_VIEW_FAILING_URL_KEY)));
            }
        }
    }

    private boolean a(Context context, Intent intent) {
        ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
        if (resolveService == null) {
            return false;
        }
        return a(context, resolveService.serviceInfo.packageName);
    }

    private boolean a(Context context, String str) {
        try {
            for (Signature charsString : context.getPackageManager().getPackageInfo(str, 64).signatures) {
                if (charsString.toCharsString().equals(FB_APP_SIGNATURE)) {
                    return true;
                }
            }
            return false;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    @Deprecated
    public void authorizeCallback(int i2, int i3, Intent intent) {
        a("authorizeCallback");
        Session session = this.h;
        if (session != null && session.onActivityResult(this.f, i2, i3, intent)) {
            this.h = null;
            this.f = null;
            this.g = null;
        }
    }

    @Deprecated
    public boolean extendAccessToken(Context context, ServiceListener serviceListener) {
        a("extendAccessToken");
        Intent intent = new Intent();
        intent.setClassName(Constants.INTENT_FACEBOOK, "com.facebook.katana.platform.TokenRefreshService");
        if (!a(context, intent)) {
            return false;
        }
        return context.bindService(intent, new TokenRefreshServiceConnection(context, serviceListener), 1);
    }

    @Deprecated
    public boolean extendAccessTokenIfNeeded(Context context, ServiceListener serviceListener) {
        a("extendAccessTokenIfNeeded");
        if (shouldExtendAccessToken()) {
            return extendAccessToken(context, serviceListener);
        }
        return true;
    }

    @Deprecated
    public boolean shouldExtendAccessToken() {
        a("shouldExtendAccessToken");
        return isSessionValid() && System.currentTimeMillis() - this.d >= 86400000;
    }

    @Deprecated
    public String logout(Context context) {
        return a(context);
    }

    /* access modifiers changed from: 0000 */
    public String a(Context context) {
        Session session;
        a("logout");
        Bundle bundle = new Bundle();
        bundle.putString("method", "auth.expireSession");
        String request = request(bundle);
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.a) {
            session = this.i;
            this.i = null;
            this.b = null;
            this.c = 0;
            this.d = currentTimeMillis;
            this.j = false;
        }
        if (session != null) {
            session.closeAndClearTokenInformation();
        }
        return request;
    }

    @Deprecated
    public String request(Bundle bundle) {
        if (bundle.containsKey("method")) {
            return a((String) null, bundle, "GET");
        }
        throw new IllegalArgumentException("API method must be specified. (parameters must contain key \"method\" and value). See http://developers.facebook.com/docs/reference/rest/");
    }

    @Deprecated
    public String request(String str) {
        return a(str, new Bundle(), "GET");
    }

    @Deprecated
    public String request(String str, Bundle bundle) {
        return a(str, bundle, "GET");
    }

    @Deprecated
    public String request(String str, Bundle bundle, String str2) {
        return a(str, bundle, str2);
    }

    /* access modifiers changed from: 0000 */
    public String a(String str, Bundle bundle, String str2) {
        String str3;
        bundle.putString("format", "json");
        if (isSessionValid()) {
            bundle.putString("access_token", getAccessToken());
        }
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(GRAPH_BASE_URL);
            sb.append(str);
            str3 = sb.toString();
        } else {
            str3 = RESTSERVER_URL;
        }
        return Util.openUrl(str3, str2, bundle);
    }

    @Deprecated
    public void dialog(Context context, String str, DialogListener dialogListener) {
        dialog(context, str, new Bundle(), dialogListener);
    }

    @Deprecated
    public void dialog(Context context, String str, Bundle bundle, DialogListener dialogListener) {
        bundle.putString(ServerProtocol.DIALOG_PARAM_DISPLAY, ServerProtocol.FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH);
        bundle.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, REDIRECT_URI);
        if (str.equals("oauth")) {
            bundle.putString("type", "user_agent");
            bundle.putString("client_id", this.e);
        } else {
            bundle.putString("app_id", this.e);
            if (isSessionValid()) {
                bundle.putString("access_token", getAccessToken());
            }
        }
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
            Util.showAlert(context, PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, "Application requires permission to access the Internet");
        } else {
            new FbDialog(context, str, bundle, dialogListener).show();
        }
    }

    @Deprecated
    public boolean isSessionValid() {
        return getAccessToken() != null && (getAccessExpires() == 0 || System.currentTimeMillis() < getAccessExpires());
    }

    @Deprecated
    public void setSession(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("session cannot be null");
        }
        synchronized (this.a) {
            this.l = session;
        }
    }

    private void a(String str) {
        if (this.l != null) {
            throw new UnsupportedOperationException(String.format("Cannot call %s after setSession has been called.", new Object[]{str}));
        }
    }

    /* JADX INFO: used method not loaded: com.facebook.Session.OpenRequest.<init>(android.app.Activity):null, types can be incorrect */
    /* JADX INFO: used method not loaded: com.facebook.Session.OpenRequest.setPermissions(java.util.List):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001a, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001b, code lost:
        if (r1 != null) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001d, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x001e, code lost:
        if (r2 == null) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0020, code lost:
        r1 = r2.getPermissions();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0027, code lost:
        if (r5.g == null) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0029, code lost:
        r1 = java.util.Arrays.asList(r5.g);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0030, code lost:
        r1 = java.util.Collections.emptyList();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0034, code lost:
        r2 = new com.facebook.Session.Builder(r5.f).setApplicationId(r5.e).setTokenCachingStrategy(a()).build();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0053, code lost:
        if (r2.getState() == com.facebook.SessionState.CREATED_TOKEN_LOADED) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0055, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        a(r2, new com.facebook.Session.OpenRequest(r5.f).setPermissions(r1), !r1.isEmpty());
        r1 = r5.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x006c, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006f, code lost:
        if (r5.j != false) goto L_0x0078;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0073, code lost:
        if (r5.i != null) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0076, code lost:
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0078, code lost:
        r0 = r5.i;
        r5.i = r2;
        r5.j = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x007f, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0080, code lost:
        if (r0 == null) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0082, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0085, code lost:
        continue;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.facebook.Session getSession() {
        /*
            r5 = this;
        L_0x0000:
            java.lang.Object r0 = r5.a
            monitor-enter(r0)
            com.facebook.Session r1 = r5.l     // Catch:{ all -> 0x008f }
            if (r1 == 0) goto L_0x000b
            com.facebook.Session r1 = r5.l     // Catch:{ all -> 0x008f }
            monitor-exit(r0)     // Catch:{ all -> 0x008f }
            return r1
        L_0x000b:
            com.facebook.Session r1 = r5.i     // Catch:{ all -> 0x008f }
            if (r1 != 0) goto L_0x008b
            boolean r1 = r5.j     // Catch:{ all -> 0x008f }
            if (r1 != 0) goto L_0x0015
            goto L_0x008b
        L_0x0015:
            java.lang.String r1 = r5.b     // Catch:{ all -> 0x008f }
            com.facebook.Session r2 = r5.i     // Catch:{ all -> 0x008f }
            monitor-exit(r0)     // Catch:{ all -> 0x008f }
            r0 = 0
            if (r1 != 0) goto L_0x001e
            return r0
        L_0x001e:
            if (r2 == 0) goto L_0x0025
            java.util.List r1 = r2.getPermissions()
            goto L_0x0034
        L_0x0025:
            java.lang.String[] r1 = r5.g
            if (r1 == 0) goto L_0x0030
            java.lang.String[] r1 = r5.g
            java.util.List r1 = java.util.Arrays.asList(r1)
            goto L_0x0034
        L_0x0030:
            java.util.List r1 = java.util.Collections.emptyList()
        L_0x0034:
            com.facebook.Session$Builder r2 = new com.facebook.Session$Builder
            android.app.Activity r3 = r5.f
            r2.<init>(r3)
            java.lang.String r3 = r5.e
            com.facebook.Session$Builder r2 = r2.setApplicationId(r3)
            com.facebook.TokenCachingStrategy r3 = r5.a()
            com.facebook.Session$Builder r2 = r2.setTokenCachingStrategy(r3)
            com.facebook.Session r2 = r2.build()
            com.facebook.SessionState r3 = r2.getState()
            com.facebook.SessionState r4 = com.facebook.SessionState.CREATED_TOKEN_LOADED
            if (r3 == r4) goto L_0x0056
            return r0
        L_0x0056:
            com.facebook.Session$OpenRequest r3 = new com.facebook.Session$OpenRequest
            android.app.Activity r4 = r5.f
            r3.<init>(r4)
            com.facebook.Session$OpenRequest r3 = r3.setPermissions(r1)
            boolean r1 = r1.isEmpty()
            r1 = r1 ^ 1
            r5.a(r2, r3, r1)
            java.lang.Object r1 = r5.a
            monitor-enter(r1)
            boolean r3 = r5.j     // Catch:{ all -> 0x0088 }
            if (r3 != 0) goto L_0x0078
            com.facebook.Session r3 = r5.i     // Catch:{ all -> 0x0088 }
            if (r3 != 0) goto L_0x0076
            goto L_0x0078
        L_0x0076:
            r2 = r0
            goto L_0x007f
        L_0x0078:
            com.facebook.Session r0 = r5.i     // Catch:{ all -> 0x0088 }
            r5.i = r2     // Catch:{ all -> 0x0088 }
            r3 = 0
            r5.j = r3     // Catch:{ all -> 0x0088 }
        L_0x007f:
            monitor-exit(r1)     // Catch:{ all -> 0x0088 }
            if (r0 == 0) goto L_0x0085
            r0.close()
        L_0x0085:
            if (r2 == 0) goto L_0x0000
            return r2
        L_0x0088:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0088 }
            throw r0
        L_0x008b:
            com.facebook.Session r1 = r5.i     // Catch:{ all -> 0x008f }
            monitor-exit(r0)     // Catch:{ all -> 0x008f }
            return r1
        L_0x008f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x008f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.android.Facebook.getSession():com.facebook.Session");
    }

    @Deprecated
    public String getAccessToken() {
        Session session = getSession();
        if (session != null) {
            return session.getAccessToken();
        }
        return null;
    }

    @Deprecated
    public long getAccessExpires() {
        Session session = getSession();
        if (session != null) {
            return session.getExpirationDate().getTime();
        }
        return this.c;
    }

    @Deprecated
    public long getLastAccessUpdate() {
        return this.d;
    }

    @Deprecated
    public void setTokenFromCache(String str, long j2, long j3) {
        a("setTokenFromCache");
        synchronized (this.a) {
            this.b = str;
            this.c = j2;
            this.d = j3;
        }
    }

    @Deprecated
    public void setAccessToken(String str) {
        a("setAccessToken");
        synchronized (this.a) {
            this.b = str;
            this.d = System.currentTimeMillis();
            this.j = true;
        }
    }

    @Deprecated
    public void setAccessExpires(long j2) {
        a("setAccessExpires");
        synchronized (this.a) {
            this.c = j2;
            this.d = System.currentTimeMillis();
            this.j = true;
        }
    }

    @Deprecated
    public void setAccessExpiresIn(String str) {
        a("setAccessExpiresIn");
        if (str != null) {
            setAccessExpires(str.equals("0") ? 0 : System.currentTimeMillis() + (Long.parseLong(str) * 1000));
        }
    }

    @Deprecated
    public String getAppId() {
        return this.e;
    }

    @Deprecated
    public void setAppId(String str) {
        a("setAppId");
        synchronized (this.a) {
            this.e = str;
            this.j = true;
        }
    }

    private TokenCachingStrategy a() {
        if (this.k == null) {
            this.k = new SetterTokenCachingStrategy();
        }
        return this.k;
    }

    /* access modifiers changed from: private */
    public static String[] b(List<String> list) {
        String[] strArr = new String[(list != null ? list.size() : 0)];
        if (list != null) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                strArr[i2] = (String) list.get(i2);
            }
        }
        return strArr;
    }

    /* access modifiers changed from: private */
    public static List<String> b(String[] strArr) {
        if (strArr != null) {
            return Arrays.asList(strArr);
        }
        return Collections.emptyList();
    }

    @Deprecated
    public static String getAttributionId(ContentResolver contentResolver) {
        return Settings.getAttributionId(contentResolver);
    }

    @Deprecated
    public boolean getShouldAutoPublishInstall() {
        return Settings.getShouldAutoPublishInstall();
    }

    @Deprecated
    public void setShouldAutoPublishInstall(boolean z) {
        Settings.setShouldAutoPublishInstall(z);
    }
}
