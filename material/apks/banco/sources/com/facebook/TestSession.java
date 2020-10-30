package com.facebook;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.facebook.Session.AuthorizationRequest;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSession extends Session {
    static final /* synthetic */ boolean a = true;
    private static Map<String, TestAccount> b = null;
    private static String c = null;
    private static String d = null;
    private static final long serialVersionUID = 1;
    private final String e;
    private final List<String> f;
    private final Mode g;
    private String h;
    private String i;
    private boolean j;

    enum Mode {
        PRIVATE,
        SHARED
    }

    interface TestAccount extends GraphObject {
        String a();

        void a(String str);

        String b();

        String c();
    }

    interface TestAccountsResponse extends GraphObject {
        GraphObjectList<TestAccount> a();
    }

    static final class TestTokenCachingStrategy extends TokenCachingStrategy {
        private Bundle a;

        private TestTokenCachingStrategy() {
        }

        public Bundle load() {
            return this.a;
        }

        public void save(Bundle bundle) {
            this.a = bundle;
        }

        public void clear() {
            this.a = null;
        }
    }

    TestSession(Activity activity, List<String> list, TokenCachingStrategy tokenCachingStrategy, String str, Mode mode) {
        super(activity, d, tokenCachingStrategy);
        Validate.notNull(list, NativeProtocol.RESULT_ARGS_PERMISSIONS);
        Validate.notNullOrEmpty(d, "testApplicationId");
        Validate.notNullOrEmpty(c, "testApplicationSecret");
        this.e = str;
        this.g = mode;
        this.f = list;
    }

    public static TestSession createSessionWithPrivateUser(Activity activity, List<String> list) {
        return a(activity, list, Mode.PRIVATE, null);
    }

    public static TestSession createSessionWithSharedUser(Activity activity, List<String> list) {
        return createSessionWithSharedUser(activity, list, null);
    }

    public static TestSession createSessionWithSharedUser(Activity activity, List<String> list, String str) {
        return a(activity, list, Mode.SHARED, str);
    }

    public static synchronized String getTestApplicationId() {
        String str;
        synchronized (TestSession.class) {
            str = d;
        }
        return str;
    }

    public static synchronized void setTestApplicationId(String str) {
        synchronized (TestSession.class) {
            if (d == null || d.equals(str)) {
                d = str;
            } else {
                throw new FacebookException("Can't have more than one test application ID");
            }
        }
    }

    public static synchronized String getTestApplicationSecret() {
        String str;
        synchronized (TestSession.class) {
            str = c;
        }
        return str;
    }

    public static synchronized void setTestApplicationSecret(String str) {
        synchronized (TestSession.class) {
            if (c == null || c.equals(str)) {
                c = str;
            } else {
                throw new FacebookException("Can't have more than one test application secret");
            }
        }
    }

    public final String getTestUserId() {
        return this.h;
    }

    public final String getTestUserName() {
        return this.i;
    }

    private static synchronized TestSession a(Activity activity, List<String> list, Mode mode, String str) {
        TestSession testSession;
        synchronized (TestSession.class) {
            if (!Utility.isNullOrEmpty(d)) {
                if (!Utility.isNullOrEmpty(c)) {
                    if (Utility.isNullOrEmpty((Collection<T>) list)) {
                        list = Arrays.asList(new String[]{"email", "publish_actions"});
                    }
                    testSession = new TestSession(activity, list, new TestTokenCachingStrategy(), str, mode);
                }
            }
            throw new FacebookException("Must provide app ID and secret");
        }
        return testSession;
    }

    private static synchronized void h() {
        synchronized (TestSession.class) {
            if (b == null) {
                b = new HashMap();
                Request.setDefaultBatchApplicationId(d);
                Bundle bundle = new Bundle();
                bundle.putString("access_token", g());
                Request request = new Request(null, "app/accounts/test-users", bundle, null);
                request.setBatchEntryName("testUsers");
                request.setBatchEntryOmitResultOnSuccess(false);
                Bundle bundle2 = new Bundle();
                bundle2.putString("access_token", g());
                bundle2.putString("ids", "{result=testUsers:$.data.*.id}");
                bundle2.putString("fields", "name");
                Request request2 = new Request(null, "", bundle2, null);
                request2.setBatchEntryDependsOn("testUsers");
                List executeBatchAndWait = Request.executeBatchAndWait(request, request2);
                if (executeBatchAndWait != null) {
                    if (executeBatchAndWait.size() == 2) {
                        a((Collection<TestAccount>) ((TestAccountsResponse) ((Response) executeBatchAndWait.get(0)).getGraphObjectAs(TestAccountsResponse.class)).a(), ((Response) executeBatchAndWait.get(1)).getGraphObject());
                        return;
                    }
                }
                throw new FacebookException("Unexpected number of results from TestUsers batch query");
            }
        }
    }

    private static synchronized void a(Collection<TestAccount> collection, GraphObject graphObject) {
        synchronized (TestSession.class) {
            for (TestAccount testAccount : collection) {
                testAccount.a(((GraphUser) graphObject.getPropertyAs(testAccount.a(), GraphUser.class)).getName());
                a(testAccount);
            }
        }
    }

    private static synchronized void a(TestAccount testAccount) {
        synchronized (TestSession.class) {
            b.put(testAccount.a(), testAccount);
        }
    }

    private static synchronized TestAccount b(String str) {
        synchronized (TestSession.class) {
            h();
            for (TestAccount testAccount : b.values()) {
                if (testAccount.c().contains(str)) {
                    return testAccount;
                }
            }
            return null;
        }
    }

    public final String toString() {
        String session = super.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("{TestSession");
        sb.append(" testUserId:");
        sb.append(this.h);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(session);
        sb.append("}");
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public void a(AuthorizationRequest authorizationRequest) {
        if (this.g == Mode.PRIVATE) {
            j();
        } else {
            i();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(SessionState sessionState, SessionState sessionState2, Exception exc) {
        String str = this.h;
        super.a(sessionState, sessionState2, exc);
        if (sessionState2.isClosed() && str != null && this.g == Mode.PRIVATE) {
            a(str, g());
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        boolean d2 = super.d();
        this.j = false;
        return d2;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        this.j = true;
        super.c();
    }

    static final String g() {
        StringBuilder sb = new StringBuilder();
        sb.append(d);
        sb.append("|");
        sb.append(c);
        return sb.toString();
    }

    private void i() {
        TestAccount b2 = b(l());
        if (b2 != null) {
            b(b2);
        } else {
            j();
        }
    }

    private void b(TestAccount testAccount) {
        this.h = testAccount.a();
        this.i = testAccount.c();
        a(AccessToken.a(testAccount.b(), this.f, AccessTokenSource.TEST_USER), (Exception) null);
    }

    private TestAccount j() {
        Bundle bundle = new Bundle();
        bundle.putString("installed", "true");
        bundle.putString(NativeProtocol.RESULT_ARGS_PERMISSIONS, k());
        bundle.putString("access_token", g());
        if (this.g == Mode.SHARED) {
            bundle.putString("name", String.format("Shared %s Testuser", new Object[]{l()}));
        }
        Response executeAndWait = new Request(null, String.format("%s/accounts/test-users", new Object[]{d}), bundle, HttpMethod.POST).executeAndWait();
        FacebookRequestError error = executeAndWait.getError();
        TestAccount testAccount = (TestAccount) executeAndWait.getGraphObjectAs(TestAccount.class);
        if (error != null) {
            a((AccessToken) null, (Exception) error.getException());
            return null;
        } else if (a || testAccount != null) {
            if (this.g == Mode.SHARED) {
                testAccount.a(bundle.getString("name"));
                a(testAccount);
            }
            b(testAccount);
            return testAccount;
        } else {
            throw new AssertionError();
        }
    }

    private void a(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("access_token", str2);
        Response executeAndWait = new Request(null, str, bundle, HttpMethod.DELETE).executeAndWait();
        FacebookRequestError error = executeAndWait.getError();
        GraphObject graphObject = executeAndWait.getGraphObject();
        if (error != null) {
            Log.w("FacebookSDK.TestSession", String.format("Could not delete test account %s: %s", new Object[]{str, error.getException().toString()}));
        } else if (graphObject.getProperty(Response.NON_JSON_RESPONSE_PROPERTY) == Boolean.valueOf(false) || graphObject.getProperty(Response.SUCCESS_KEY) == Boolean.valueOf(false)) {
            Log.w("FacebookSDK.TestSession", String.format("Could not delete test account %s: unknown reason", new Object[]{str}));
        }
    }

    private String k() {
        return TextUtils.join(",", this.f);
    }

    private String l() {
        return a((((long) k().hashCode()) & 4294967295L) ^ (this.e != null ? ((long) this.e.hashCode()) & 4294967295L : 0));
    }

    private String a(long j2) {
        String l = Long.toString(j2);
        StringBuilder sb = new StringBuilder("Perm");
        char[] charArray = l.toCharArray();
        int length = charArray.length;
        char c2 = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char c3 = charArray[i2];
            c2 = c3 == c2 ? (char) (c3 + 10) : c3;
            sb.append((char) ((c2 + 'a') - 48));
        }
        return sb.toString();
    }
}
