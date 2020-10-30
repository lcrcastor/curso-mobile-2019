package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<GoogleSignInAccount> CREATOR = new zza();
    public static zze gT = zzh.zzaxj();
    private static Comparator<Scope> m = new Comparator<Scope>() {
        /* renamed from: a */
        public int compare(Scope scope, Scope scope2) {
            return scope.zzaqg().compareTo(scope2.zzaqg());
        }
    };
    final int a;
    List<Scope> b;
    private String c;
    private String d;
    private String e;
    private String f;
    private Uri g;
    private String h;
    private long i;
    private String j;
    private String k;
    private String l;

    GoogleSignInAccount(int i2, String str, String str2, String str3, String str4, Uri uri, String str5, long j2, String str6, List<Scope> list, String str7, String str8) {
        this.a = i2;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = str4;
        this.g = uri;
        this.h = str5;
        this.i = j2;
        this.j = str6;
        this.b = list;
        this.k = str7;
        this.l = str8;
    }

    private JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (getId() != null) {
                jSONObject.put("id", getId());
            }
            if (getIdToken() != null) {
                jSONObject.put("tokenId", getIdToken());
            }
            if (getEmail() != null) {
                jSONObject.put("email", getEmail());
            }
            if (getDisplayName() != null) {
                jSONObject.put("displayName", getDisplayName());
            }
            if (getGivenName() != null) {
                jSONObject.put("givenName", getGivenName());
            }
            if (getFamilyName() != null) {
                jSONObject.put("familyName", getFamilyName());
            }
            if (getPhotoUrl() != null) {
                jSONObject.put("photoUrl", getPhotoUrl().toString());
            }
            if (getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.i);
            jSONObject.put("obfuscatedIdentifier", zzahf());
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.b, m);
            for (Scope zzaqg : this.b) {
                jSONArray.put(zzaqg.zzaqg());
            }
            jSONObject.put("grantedScopes", jSONArray);
            return jSONObject;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static GoogleSignInAccount zza(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable Uri uri, @Nullable Long l2, @NonNull String str7, @NonNull Set<Scope> set) {
        GoogleSignInAccount googleSignInAccount = new GoogleSignInAccount(3, str, str2, str3, str4, uri, null, (l2 == null ? Long.valueOf(gT.currentTimeMillis() / 1000) : l2).longValue(), zzac.zzhz(str7), new ArrayList((Collection) zzac.zzy(set)), str5, str6);
        return googleSignInAccount;
    }

    @Nullable
    public static GoogleSignInAccount zzfw(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        String optString = jSONObject.optString("photoUrl", null);
        Uri parse = !TextUtils.isEmpty(optString) ? Uri.parse(optString) : null;
        long parseLong = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            hashSet.add(new Scope(jSONArray.getString(i2)));
        }
        return zza(jSONObject.optString("id"), jSONObject.optString("tokenId", null), jSONObject.optString("email", null), jSONObject.optString("displayName", null), jSONObject.optString("givenName", null), jSONObject.optString("familyName", null), parse, Long.valueOf(parseLong), jSONObject.getString("obfuscatedIdentifier"), hashSet).zzfx(jSONObject.optString("serverAuthCode", null));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GoogleSignInAccount)) {
            return false;
        }
        return ((GoogleSignInAccount) obj).zzahg().equals(zzahg());
    }

    @Nullable
    public String getDisplayName() {
        return this.f;
    }

    @Nullable
    public String getEmail() {
        return this.e;
    }

    @Nullable
    public String getFamilyName() {
        return this.l;
    }

    @Nullable
    public String getGivenName() {
        return this.k;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() {
        return new HashSet(this.b);
    }

    @Nullable
    public String getId() {
        return this.c;
    }

    @Nullable
    public String getIdToken() {
        return this.d;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return this.g;
    }

    @Nullable
    public String getServerAuthCode() {
        return this.h;
    }

    public int hashCode() {
        return zzahg().hashCode();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zza.a(this, parcel, i2);
    }

    public boolean zza() {
        return gT.currentTimeMillis() / 1000 >= this.i - 300;
    }

    public long zzahe() {
        return this.i;
    }

    @NonNull
    public String zzahf() {
        return this.j;
    }

    public String zzahg() {
        return a().toString();
    }

    public String zzahh() {
        JSONObject a2 = a();
        a2.remove("serverAuthCode");
        return a2.toString();
    }

    public GoogleSignInAccount zzfx(String str) {
        this.h = str;
        return this;
    }
}
