package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zze;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions extends AbstractSafeParcelable implements Optional, ReflectedParcelable {
    public static final Creator<GoogleSignInOptions> CREATOR = new zzb();
    public static final GoogleSignInOptions DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
    public static final Scope hd = new Scope(Scopes.PROFILE);
    public static final Scope he = new Scope("email");
    public static final Scope hf = new Scope("openid");
    private static Comparator<Scope> i = new Comparator<Scope>() {
        /* renamed from: a */
        public int compare(Scope scope, Scope scope2) {
            return scope.zzaqg().compareTo(scope2.zzaqg());
        }
    };
    final int a;
    /* access modifiers changed from: private */
    public final ArrayList<Scope> b;
    /* access modifiers changed from: private */
    public Account c;
    /* access modifiers changed from: private */
    public boolean d;
    /* access modifiers changed from: private */
    public final boolean e;
    /* access modifiers changed from: private */
    public final boolean f;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public String h;

    public static final class Builder {
        private Set<Scope> a = new HashSet();
        private boolean b;
        private boolean c;
        private boolean d;
        private String e;
        private Account f;
        private String g;

        public Builder() {
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            zzac.zzy(googleSignInOptions);
            this.a = new HashSet(googleSignInOptions.b);
            this.b = googleSignInOptions.e;
            this.c = googleSignInOptions.f;
            this.d = googleSignInOptions.d;
            this.e = googleSignInOptions.g;
            this.f = googleSignInOptions.c;
            this.g = googleSignInOptions.h;
        }

        private String a(String str) {
            zzac.zzhz(str);
            zzac.zzb(this.e == null || this.e.equals(str), (Object) "two different server client ids provided");
            return str;
        }

        public GoogleSignInOptions build() {
            if (this.d && (this.f == null || !this.a.isEmpty())) {
                requestId();
            }
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions((Set) this.a, this.f, this.d, this.b, this.c, this.e, this.g);
            return googleSignInOptions;
        }

        public Builder requestEmail() {
            this.a.add(GoogleSignInOptions.he);
            return this;
        }

        public Builder requestId() {
            this.a.add(GoogleSignInOptions.hf);
            return this;
        }

        public Builder requestIdToken(String str) {
            this.d = true;
            this.e = a(str);
            return this;
        }

        public Builder requestProfile() {
            this.a.add(GoogleSignInOptions.hd);
            return this;
        }

        public Builder requestScopes(Scope scope, Scope... scopeArr) {
            this.a.add(scope);
            this.a.addAll(Arrays.asList(scopeArr));
            return this;
        }

        public Builder requestServerAuthCode(String str) {
            return requestServerAuthCode(str, false);
        }

        public Builder requestServerAuthCode(String str, boolean z) {
            this.b = true;
            this.e = a(str);
            this.c = z;
            return this;
        }

        public Builder setAccountName(String str) {
            this.f = new Account(zzac.zzhz(str), "com.google");
            return this;
        }

        public Builder setHostedDomain(String str) {
            this.g = zzac.zzhz(str);
            return this;
        }
    }

    GoogleSignInOptions(int i2, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2) {
        this.a = i2;
        this.b = arrayList;
        this.c = account;
        this.d = z;
        this.e = z2;
        this.f = z3;
        this.g = str;
        this.h = str2;
    }

    private GoogleSignInOptions(Set<Scope> set, Account account, boolean z, boolean z2, boolean z3, String str, String str2) {
        this(2, new ArrayList<>(set), account, z, z2, z3, str, str2);
    }

    private JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.b, i);
            Iterator it = this.b.iterator();
            while (it.hasNext()) {
                jSONArray.put(((Scope) it.next()).zzaqg());
            }
            jSONObject.put("scopes", jSONArray);
            if (this.c != null) {
                jSONObject.put("accountName", this.c.name);
            }
            jSONObject.put("idTokenRequested", this.d);
            jSONObject.put("forceCodeForRefreshToken", this.f);
            jSONObject.put("serverAuthRequested", this.e);
            if (!TextUtils.isEmpty(this.g)) {
                jSONObject.put("serverClientId", this.g);
            }
            if (!TextUtils.isEmpty(this.h)) {
                jSONObject.put("hostedDomain", this.h);
            }
            return jSONObject;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Nullable
    public static GoogleSignInOptions zzfy(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            hashSet.add(new Scope(jSONArray.getString(i2)));
        }
        String optString = jSONObject.optString("accountName", null);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions(hashSet, !TextUtils.isEmpty(optString) ? new Account(optString, "com.google") : null, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null));
        return googleSignInOptions;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        if (r3.c.equals(r4.getAccount()) != false) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0057, code lost:
        if (r3.g.equals(r4.zzahn()) != false) goto L_0x0059;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r4 = (com.google.android.gms.auth.api.signin.GoogleSignInOptions) r4     // Catch:{ ClassCastException -> 0x0072 }
            java.util.ArrayList<com.google.android.gms.common.api.Scope> r1 = r3.b     // Catch:{ ClassCastException -> 0x0072 }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x0072 }
            java.util.ArrayList r2 = r4.zzahj()     // Catch:{ ClassCastException -> 0x0072 }
            int r2 = r2.size()     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 != r2) goto L_0x0072
            java.util.ArrayList<com.google.android.gms.common.api.Scope> r1 = r3.b     // Catch:{ ClassCastException -> 0x0072 }
            java.util.ArrayList r2 = r4.zzahj()     // Catch:{ ClassCastException -> 0x0072 }
            boolean r1 = r1.containsAll(r2)     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 != 0) goto L_0x0023
            return r0
        L_0x0023:
            android.accounts.Account r1 = r3.c     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 != 0) goto L_0x002e
            android.accounts.Account r1 = r4.getAccount()     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 != 0) goto L_0x0072
            goto L_0x003a
        L_0x002e:
            android.accounts.Account r1 = r3.c     // Catch:{ ClassCastException -> 0x0072 }
            android.accounts.Account r2 = r4.getAccount()     // Catch:{ ClassCastException -> 0x0072 }
            boolean r1 = r1.equals(r2)     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 == 0) goto L_0x0072
        L_0x003a:
            java.lang.String r1 = r3.g     // Catch:{ ClassCastException -> 0x0072 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 == 0) goto L_0x004d
            java.lang.String r1 = r4.zzahn()     // Catch:{ ClassCastException -> 0x0072 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 == 0) goto L_0x0072
            goto L_0x0059
        L_0x004d:
            java.lang.String r1 = r3.g     // Catch:{ ClassCastException -> 0x0072 }
            java.lang.String r2 = r4.zzahn()     // Catch:{ ClassCastException -> 0x0072 }
            boolean r1 = r1.equals(r2)     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 == 0) goto L_0x0072
        L_0x0059:
            boolean r1 = r3.f     // Catch:{ ClassCastException -> 0x0072 }
            boolean r2 = r4.zzahm()     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 != r2) goto L_0x0072
            boolean r1 = r3.d     // Catch:{ ClassCastException -> 0x0072 }
            boolean r2 = r4.zzahk()     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 != r2) goto L_0x0072
            boolean r1 = r3.e     // Catch:{ ClassCastException -> 0x0072 }
            boolean r4 = r4.zzahl()     // Catch:{ ClassCastException -> 0x0072 }
            if (r1 != r4) goto L_0x0072
            r0 = 1
        L_0x0072:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.GoogleSignInOptions.equals(java.lang.Object):boolean");
    }

    public Account getAccount() {
        return this.c;
    }

    public Scope[] getScopeArray() {
        return (Scope[]) this.b.toArray(new Scope[this.b.size()]);
    }

    public int hashCode() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            arrayList.add(((Scope) it.next()).zzaqg());
        }
        Collections.sort(arrayList);
        return new zze().zzq(arrayList).zzq(this.c).zzq(this.g).zzbd(this.f).zzbd(this.d).zzbd(this.e).zzahv();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zzb.a(this, parcel, i2);
    }

    public String zzahg() {
        return a().toString();
    }

    public ArrayList<Scope> zzahj() {
        return new ArrayList<>(this.b);
    }

    public boolean zzahk() {
        return this.d;
    }

    public boolean zzahl() {
        return this.e;
    }

    public boolean zzahm() {
        return this.f;
    }

    public String zzahn() {
        return this.g;
    }

    public String zzaho() {
        return this.h;
    }
}
