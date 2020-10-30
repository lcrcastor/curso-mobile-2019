package com.google.android.gms.analytics;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.LogPrinter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class zzd implements zzk {
    private static final Uri a;
    private final LogPrinter b = new LogPrinter(4, "GA/LogCatTransport");

    static {
        Builder builder = new Builder();
        builder.scheme("uri");
        builder.authority("local");
        a = builder.build();
    }

    public void zzb(zze zze) {
        ArrayList<zzg> arrayList = new ArrayList<>(zze.zzxx());
        Collections.sort(arrayList, new Comparator<zzg>() {
            /* renamed from: a */
            public int compare(zzg zzg, zzg zzg2) {
                return zzg.getClass().getCanonicalName().compareTo(zzg2.getClass().getCanonicalName());
            }
        });
        StringBuilder sb = new StringBuilder();
        for (zzg obj : arrayList) {
            String obj2 = obj.toString();
            if (!TextUtils.isEmpty(obj2)) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(obj2);
            }
        }
        this.b.println(sb.toString());
    }

    public Uri zzxl() {
        return a;
    }
}
