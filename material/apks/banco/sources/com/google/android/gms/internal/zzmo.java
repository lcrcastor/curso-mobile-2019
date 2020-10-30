package com.google.android.gms.internal;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.zzg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzmo extends zzg<zzmo> {
    private final List<Product> a = new ArrayList();
    private final List<Promotion> b = new ArrayList();
    private final Map<String, List<Product>> c = new HashMap();
    private ProductAction d;

    public String toString() {
        HashMap hashMap = new HashMap();
        if (!this.a.isEmpty()) {
            hashMap.put("products", this.a);
        }
        if (!this.b.isEmpty()) {
            hashMap.put("promotions", this.b);
        }
        if (!this.c.isEmpty()) {
            hashMap.put("impressions", this.c);
        }
        hashMap.put("productAction", this.d);
        return zzj(hashMap);
    }

    public void zza(Product product, String str) {
        if (product != null) {
            if (str == null) {
                str = "";
            }
            if (!this.c.containsKey(str)) {
                this.c.put(str, new ArrayList());
            }
            ((List) this.c.get(str)).add(product);
        }
    }

    /* renamed from: zza */
    public void zzb(zzmo zzmo) {
        zzmo.a.addAll(this.a);
        zzmo.b.addAll(this.b);
        for (Entry entry : this.c.entrySet()) {
            String str = (String) entry.getKey();
            for (Product zza : (List) entry.getValue()) {
                zzmo.zza(zza, str);
            }
        }
        if (this.d != null) {
            zzmo.d = this.d;
        }
    }

    public ProductAction zzzj() {
        return this.d;
    }

    public List<Product> zzzk() {
        return Collections.unmodifiableList(this.a);
    }

    public Map<String, List<Product>> zzzl() {
        return this.c;
    }

    public List<Promotion> zzzm() {
        return Collections.unmodifiableList(this.b);
    }
}
