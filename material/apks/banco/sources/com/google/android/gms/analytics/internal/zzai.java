package com.google.android.gms.analytics.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.zzac;
import java.util.UUID;

public class zzai extends zzd {
    /* access modifiers changed from: private */
    public SharedPreferences a;
    private long b;
    private long c = -1;
    private final zza d;

    public final class zza {
        private final String b;
        private final long c;

        private zza(String str, long j) {
            zzac.zzhz(str);
            zzac.zzbs(j > 0);
            this.b = str;
            this.c = j;
        }

        private void a() {
            long currentTimeMillis = zzai.this.zzaan().currentTimeMillis();
            Editor edit = zzai.this.a.edit();
            edit.remove(e());
            edit.remove(zzafq());
            edit.putLong(d(), currentTimeMillis);
            edit.commit();
        }

        private long b() {
            long c2 = c();
            if (c2 == 0) {
                return 0;
            }
            return Math.abs(c2 - zzai.this.zzaan().currentTimeMillis());
        }

        private long c() {
            return zzai.this.a.getLong(d(), 0);
        }

        private String d() {
            return String.valueOf(this.b).concat(":start");
        }

        private String e() {
            return String.valueOf(this.b).concat(":count");
        }

        public Pair<String, Long> zzafm() {
            long b2 = b();
            if (b2 < this.c) {
                return null;
            }
            if (b2 > this.c * 2) {
                a();
                return null;
            }
            String string = zzai.this.a.getString(zzafq(), null);
            long j = zzai.this.a.getLong(e(), 0);
            a();
            if (string == null || j <= 0) {
                return null;
            }
            return new Pair<>(string, Long.valueOf(j));
        }

        /* access modifiers changed from: protected */
        public String zzafq() {
            return String.valueOf(this.b).concat(":value");
        }

        public void zzfd(String str) {
            if (c() == 0) {
                a();
            }
            if (str == null) {
                str = "";
            }
            synchronized (this) {
                long j = zzai.this.a.getLong(e(), 0);
                if (j <= 0) {
                    Editor edit = zzai.this.a.edit();
                    edit.putString(zzafq(), str);
                    edit.putLong(e(), 1);
                    edit.apply();
                    return;
                }
                long j2 = j + 1;
                boolean z = (UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE) < Long.MAX_VALUE / j2;
                Editor edit2 = zzai.this.a.edit();
                if (z) {
                    edit2.putString(zzafq(), str);
                }
                edit2.putLong(e(), j2);
                edit2.apply();
            }
        }
    }

    protected zzai(zzf zzf) {
        super(zzf);
        zza zza2 = new zza("monitoring", zzaap().zzadz());
        this.d = zza2;
    }

    public long zzafe() {
        zzyl();
        zzaax();
        if (this.b == 0) {
            long j = this.a.getLong("first_run", 0);
            if (j == 0) {
                j = zzaan().currentTimeMillis();
                Editor edit = this.a.edit();
                edit.putLong("first_run", j);
                if (!edit.commit()) {
                    zzes("Failed to commit first run time");
                }
            }
            this.b = j;
        }
        return this.b;
    }

    public zzal zzaff() {
        return new zzal(zzaan(), zzafe());
    }

    public long zzafg() {
        zzyl();
        zzaax();
        if (this.c == -1) {
            this.c = this.a.getLong("last_dispatch", 0);
        }
        return this.c;
    }

    public void zzafh() {
        zzyl();
        zzaax();
        long currentTimeMillis = zzaan().currentTimeMillis();
        Editor edit = this.a.edit();
        edit.putLong("last_dispatch", currentTimeMillis);
        edit.apply();
        this.c = currentTimeMillis;
    }

    public String zzafi() {
        zzyl();
        zzaax();
        String string = this.a.getString("installation_campaign", null);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    public zza zzafj() {
        return this.d;
    }

    public void zzfc(String str) {
        zzyl();
        zzaax();
        Editor edit = this.a.edit();
        if (TextUtils.isEmpty(str)) {
            edit.remove("installation_campaign");
        } else {
            edit.putString("installation_campaign", str);
        }
        if (!edit.commit()) {
            zzes("Failed to commit campaign data");
        }
    }

    /* access modifiers changed from: protected */
    public void zzym() {
        this.a = getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }
}
