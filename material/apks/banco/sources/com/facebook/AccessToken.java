package com.facebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.android.Facebook;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public final class AccessToken implements Serializable {
    private static final Date a = new Date(Long.MIN_VALUE);
    private static final Date b = new Date(Long.MAX_VALUE);
    private static final Date c = b;
    private static final Date d = new Date();
    private static final AccessTokenSource e = AccessTokenSource.FACEBOOK_APPLICATION_WEB;
    private static final Date f = a;
    private static final long serialVersionUID = 1;
    private final Date g;
    private final List<String> h;
    private final List<String> i;
    private final String j;
    private final AccessTokenSource k;
    private final Date l;

    static class SerializationProxyV2 implements Serializable {
        private static final long serialVersionUID = -2488473066578201068L;
        private final Date a;
        private final List<String> b;
        private final List<String> c;
        private final String d;
        private final AccessTokenSource e;
        private final Date f;

        private SerializationProxyV2(String str, Date date, List<String> list, List<String> list2, AccessTokenSource accessTokenSource, Date date2) {
            this.a = date;
            this.b = list;
            this.c = list2;
            this.d = str;
            this.e = accessTokenSource;
            this.f = date2;
        }

        private Object readResolve() {
            AccessToken accessToken = new AccessToken(this.d, this.a, this.b, this.c, this.e, this.f);
            return accessToken;
        }
    }

    AccessToken(String str, Date date, List<String> list, List<String> list2, AccessTokenSource accessTokenSource, Date date2) {
        if (list == null) {
            list = Collections.emptyList();
        }
        if (list2 == null) {
            list2 = Collections.emptyList();
        }
        this.g = date;
        this.h = Collections.unmodifiableList(list);
        this.i = Collections.unmodifiableList(list2);
        this.j = str;
        this.k = accessTokenSource;
        this.l = date2;
    }

    public String getToken() {
        return this.j;
    }

    public Date getExpires() {
        return this.g;
    }

    public List<String> getPermissions() {
        return this.h;
    }

    public List<String> getDeclinedPermissions() {
        return this.i;
    }

    public AccessTokenSource getSource() {
        return this.k;
    }

    public Date getLastRefresh() {
        return this.l;
    }

    public static AccessToken createFromExistingAccessToken(String str, Date date, Date date2, AccessTokenSource accessTokenSource, List<String> list) {
        if (date == null) {
            date = c;
        }
        Date date3 = date;
        if (date2 == null) {
            date2 = d;
        }
        Date date4 = date2;
        if (accessTokenSource == null) {
            accessTokenSource = e;
        }
        AccessToken accessToken = new AccessToken(str, date3, list, null, accessTokenSource, date4);
        return accessToken;
    }

    public static AccessToken createFromNativeLinkingIntent(Intent intent) {
        Validate.notNull(intent, "intent");
        if (intent.getExtras() == null) {
            return null;
        }
        return a(null, intent.getExtras(), AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{AccessToken");
        sb.append(" token:");
        sb.append(d());
        a(sb);
        sb.append("}");
        return sb.toString();
    }

    static AccessToken a() {
        AccessToken accessToken = new AccessToken("", f, null, null, AccessTokenSource.NONE, d);
        return accessToken;
    }

    static AccessToken a(String str, List<String> list, AccessTokenSource accessTokenSource) {
        AccessToken accessToken = new AccessToken(str, c, list, null, accessTokenSource, d);
        return accessToken;
    }

    static AccessToken a(Bundle bundle, AccessTokenSource accessTokenSource) {
        return a(bundle.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS), null, bundle.getString(NativeProtocol.EXTRA_ACCESS_TOKEN), a(bundle, NativeProtocol.EXTRA_EXPIRES_SECONDS_SINCE_EPOCH, new Date(0)), accessTokenSource);
    }

    static AccessToken a(List<String> list, Bundle bundle, AccessTokenSource accessTokenSource) {
        Date a2 = a(bundle, Facebook.EXPIRES, new Date());
        String string = bundle.getString("access_token");
        String string2 = bundle.getString("granted_scopes");
        if (!Utility.isNullOrEmpty(string2)) {
            list = new ArrayList<>(Arrays.asList(string2.split(",")));
        }
        String string3 = bundle.getString("denied_scopes");
        ArrayList arrayList = null;
        if (!Utility.isNullOrEmpty(string3)) {
            arrayList = new ArrayList(Arrays.asList(string3.split(",")));
        }
        return a(list, arrayList, string, a2, accessTokenSource);
    }

    @SuppressLint({"FieldGetter"})
    static AccessToken a(AccessToken accessToken, Bundle bundle) {
        if (accessToken.k == AccessTokenSource.FACEBOOK_APPLICATION_WEB || accessToken.k == AccessTokenSource.FACEBOOK_APPLICATION_NATIVE || accessToken.k == AccessTokenSource.FACEBOOK_APPLICATION_SERVICE) {
            Date a2 = a(bundle, Facebook.EXPIRES, new Date(0));
            return a(accessToken.getPermissions(), accessToken.getDeclinedPermissions(), bundle.getString("access_token"), a2, accessToken.k);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid token source: ");
        sb.append(accessToken.k);
        throw new FacebookException(sb.toString());
    }

    static AccessToken a(AccessToken accessToken, List<String> list, List<String> list2) {
        AccessToken accessToken2 = new AccessToken(accessToken.j, accessToken.g, list, list2, accessToken.k, accessToken.l);
        return accessToken2;
    }

    private static AccessToken a(List<String> list, List<String> list2, String str, Date date, AccessTokenSource accessTokenSource) {
        if (Utility.isNullOrEmpty(str) || date == null) {
            return a();
        }
        AccessToken accessToken = new AccessToken(str, date, list, list2, accessTokenSource, new Date());
        return accessToken;
    }

    static AccessToken a(Bundle bundle) {
        AccessToken accessToken = new AccessToken(bundle.getString(TokenCachingStrategy.TOKEN_KEY), TokenCachingStrategy.a(bundle, TokenCachingStrategy.EXPIRATION_DATE_KEY), a(bundle, TokenCachingStrategy.PERMISSIONS_KEY), a(bundle, TokenCachingStrategy.DECLINED_PERMISSIONS_KEY), TokenCachingStrategy.getSource(bundle), TokenCachingStrategy.a(bundle, TokenCachingStrategy.LAST_REFRESH_DATE_KEY));
        return accessToken;
    }

    static List<String> a(Bundle bundle, String str) {
        ArrayList stringArrayList = bundle.getStringArrayList(str);
        if (stringArrayList == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList(stringArrayList));
    }

    /* access modifiers changed from: 0000 */
    public Bundle b() {
        Bundle bundle = new Bundle();
        bundle.putString(TokenCachingStrategy.TOKEN_KEY, this.j);
        TokenCachingStrategy.a(bundle, TokenCachingStrategy.EXPIRATION_DATE_KEY, this.g);
        bundle.putStringArrayList(TokenCachingStrategy.PERMISSIONS_KEY, new ArrayList(this.h));
        bundle.putStringArrayList(TokenCachingStrategy.DECLINED_PERMISSIONS_KEY, new ArrayList(this.i));
        bundle.putSerializable(TokenCachingStrategy.TOKEN_SOURCE_KEY, this.k);
        TokenCachingStrategy.a(bundle, TokenCachingStrategy.LAST_REFRESH_DATE_KEY, this.l);
        return bundle;
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return Utility.isNullOrEmpty(this.j) || new Date().after(this.g);
    }

    private static AccessToken a(List<String> list, Bundle bundle, AccessTokenSource accessTokenSource, Date date) {
        String string = bundle.getString("access_token");
        Date a2 = a(bundle, Facebook.EXPIRES, date);
        if (Utility.isNullOrEmpty(string) || a2 == null) {
            return null;
        }
        AccessToken accessToken = new AccessToken(string, a2, list, null, accessTokenSource, new Date());
        return accessToken;
    }

    private String d() {
        if (this.j == null) {
            return "null";
        }
        return Settings.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS) ? this.j : "ACCESS_TOKEN_REMOVED";
    }

    private void a(StringBuilder sb) {
        sb.append(" permissions:");
        if (this.h == null) {
            sb.append("null");
            return;
        }
        sb.append("[");
        sb.append(TextUtils.join(", ", this.h));
        sb.append("]");
    }

    private Object writeReplace() {
        SerializationProxyV2 serializationProxyV2 = new SerializationProxyV2(this.j, this.g, this.h, this.i, this.k, this.l);
        return serializationProxyV2;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Cannot readObject, serialization proxy required");
    }

    private static Date a(Bundle bundle, String str, Date date) {
        long j2;
        if (bundle == null) {
            return null;
        }
        Object obj = bundle.get(str);
        if (obj instanceof Long) {
            j2 = ((Long) obj).longValue();
        } else if (!(obj instanceof String)) {
            return null;
        } else {
            try {
                j2 = Long.parseLong((String) obj);
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        if (j2 == 0) {
            return new Date(Long.MAX_VALUE);
        }
        return new Date(date.getTime() + (j2 * 1000));
    }
}
