package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzj extends zzdk {
    static final String a;
    private static final String b = zzaf.ARBITRARY_PIXEL.toString();
    private static final String c = zzag.URL.toString();
    private static final String d = zzag.ADDITIONAL_PARAMS.toString();
    private static final String e = zzag.UNREPEATABLE.toString();
    private static final Set<String> f = new HashSet();
    private final zza g;
    private final Context h;

    public interface zza {
        zzat zzcdt();
    }

    static {
        String str = b;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 17);
        sb.append("gtm_");
        sb.append(str);
        sb.append("_unrepeatable");
        a = sb.toString();
    }

    public zzj(final Context context) {
        this(context, new zza() {
            public zzat zzcdt() {
                return zzaa.zzea(context);
            }
        });
    }

    zzj(Context context, zza zza2) {
        super(b, c);
        this.g = zza2;
        this.h = context;
    }

    private synchronized boolean c(String str) {
        if (b(str)) {
            return true;
        }
        if (!a(str)) {
            return false;
        }
        f.add(str);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(String str) {
        return this.h.getSharedPreferences(a, 0).contains(str);
    }

    /* access modifiers changed from: 0000 */
    public boolean b(String str) {
        return f.contains(str);
    }

    public void zzay(Map<String, com.google.android.gms.internal.zzai.zza> map) {
        String zzg = map.get(e) != null ? zzdm.zzg((com.google.android.gms.internal.zzai.zza) map.get(e)) : null;
        if (zzg == null || !c(zzg)) {
            Builder buildUpon = Uri.parse(zzdm.zzg((com.google.android.gms.internal.zzai.zza) map.get(c))).buildUpon();
            com.google.android.gms.internal.zzai.zza zza2 = (com.google.android.gms.internal.zzai.zza) map.get(d);
            if (zza2 != null) {
                Object zzl = zzdm.zzl(zza2);
                if (!(zzl instanceof List)) {
                    String str = "ArbitraryPixel: additional params not a list: not sending partial hit: ";
                    String valueOf = String.valueOf(buildUpon.build().toString());
                    zzbo.e(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    return;
                }
                for (Object next : (List) zzl) {
                    if (!(next instanceof Map)) {
                        String str2 = "ArbitraryPixel: additional params contains non-map: not sending partial hit: ";
                        String valueOf2 = String.valueOf(buildUpon.build().toString());
                        zzbo.e(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                        return;
                    }
                    for (Entry entry : ((Map) next).entrySet()) {
                        buildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
            String uri = buildUpon.build().toString();
            this.g.zzcdt().zzph(uri);
            String str3 = "ArbitraryPixel: url = ";
            String valueOf3 = String.valueOf(uri);
            zzbo.v(valueOf3.length() != 0 ? str3.concat(valueOf3) : new String(str3));
            if (zzg != null) {
                synchronized (zzj.class) {
                    f.add(zzg);
                    zzdd.a(this.h, a, zzg, "true");
                }
            }
        }
    }
}
