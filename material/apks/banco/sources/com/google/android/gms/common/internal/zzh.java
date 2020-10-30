package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzxa;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzh {
    private final Account a;
    private final Set<Scope> b;
    private final Set<Scope> c;
    private final Map<Api<?>, zza> d;
    private final int e;
    private final View f;
    private final String g;
    private final String h;
    private final zzxa i;
    private Integer j;

    public static final class zza {
        public final boolean Cb;
        public final Set<Scope> hm;

        public zza(Set<Scope> set, boolean z) {
            zzac.zzy(set);
            this.hm = Collections.unmodifiableSet(set);
            this.Cb = z;
        }
    }

    public zzh(Account account, Set<Scope> set, Map<Api<?>, zza> map, int i2, View view, String str, String str2, zzxa zzxa) {
        this.a = account;
        this.b = set == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(set);
        if (map == null) {
            map = Collections.EMPTY_MAP;
        }
        this.d = map;
        this.f = view;
        this.e = i2;
        this.g = str;
        this.h = str2;
        this.i = zzxa;
        HashSet hashSet = new HashSet(this.b);
        for (zza zza2 : this.d.values()) {
            hashSet.addAll(zza2.hm);
        }
        this.c = Collections.unmodifiableSet(hashSet);
    }

    public static zzh zzcd(Context context) {
        return new Builder(context).zzaqd();
    }

    public Account getAccount() {
        return this.a;
    }

    @Deprecated
    public String getAccountName() {
        if (this.a != null) {
            return this.a.name;
        }
        return null;
    }

    public Account zzatv() {
        return this.a != null ? this.a : new Account("<<default account>>", "com.google");
    }

    public int zzauf() {
        return this.e;
    }

    public Set<Scope> zzaug() {
        return this.b;
    }

    public Set<Scope> zzauh() {
        return this.c;
    }

    public Map<Api<?>, zza> zzaui() {
        return this.d;
    }

    public String zzauj() {
        return this.g;
    }

    public String zzauk() {
        return this.h;
    }

    public View zzaul() {
        return this.f;
    }

    public zzxa zzaum() {
        return this.i;
    }

    public Integer zzaun() {
        return this.j;
    }

    public Set<Scope> zzb(Api<?> api) {
        zza zza2 = (zza) this.d.get(api);
        if (zza2 == null || zza2.hm.isEmpty()) {
            return this.b;
        }
        HashSet hashSet = new HashSet(this.b);
        hashSet.addAll(zza2.hm);
        return hashSet;
    }

    public void zzc(Integer num) {
        this.j = num;
    }
}
