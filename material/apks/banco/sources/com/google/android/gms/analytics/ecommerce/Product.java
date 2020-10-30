package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzc;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Product {
    Map<String, String> a = new HashMap();

    /* access modifiers changed from: 0000 */
    public void a(String str, String str2) {
        zzac.zzb(str, (Object) "Name should be non-null");
        this.a.put(str, str2);
    }

    public Product setBrand(String str) {
        a("br", str);
        return this;
    }

    public Product setCategory(String str) {
        a("ca", str);
        return this;
    }

    public Product setCouponCode(String str) {
        a("cc", str);
        return this;
    }

    public Product setCustomDimension(int i, String str) {
        a(zzc.zzbt(i), str);
        return this;
    }

    public Product setCustomMetric(int i, int i2) {
        a(zzc.zzbu(i), Integer.toString(i2));
        return this;
    }

    public Product setId(String str) {
        a("id", str);
        return this;
    }

    public Product setName(String str) {
        a("nm", str);
        return this;
    }

    public Product setPosition(int i) {
        a("ps", Integer.toString(i));
        return this;
    }

    public Product setPrice(double d) {
        a("pr", Double.toString(d));
        return this;
    }

    public Product setQuantity(int i) {
        a("qt", Integer.toString(i));
        return this;
    }

    public Product setVariant(String str) {
        a("va", str);
        return this;
    }

    public String toString() {
        return zzg.zzap(this.a);
    }

    public Map<String, String> zzem(String str) {
        HashMap hashMap = new HashMap();
        for (Entry entry : this.a.entrySet()) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf((String) entry.getKey());
            hashMap.put(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), (String) entry.getValue());
        }
        return hashMap;
    }
}
