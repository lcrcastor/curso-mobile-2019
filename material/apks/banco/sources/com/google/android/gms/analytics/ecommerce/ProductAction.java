package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ProductAction {
    public static final String ACTION_ADD = "add";
    public static final String ACTION_CHECKOUT = "checkout";
    public static final String ACTION_CHECKOUT_OPTION = "checkout_option";
    @Deprecated
    public static final String ACTION_CHECKOUT_OPTIONS = "checkout_options";
    public static final String ACTION_CLICK = "click";
    public static final String ACTION_DETAIL = "detail";
    public static final String ACTION_PURCHASE = "purchase";
    public static final String ACTION_REFUND = "refund";
    public static final String ACTION_REMOVE = "remove";
    Map<String, String> a = new HashMap();

    public ProductAction(String str) {
        a("&pa", str);
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, String str2) {
        zzac.zzb(str, (Object) "Name should be non-null");
        this.a.put(str, str2);
    }

    public Map<String, String> build() {
        return new HashMap(this.a);
    }

    public ProductAction setCheckoutOptions(String str) {
        a("&col", str);
        return this;
    }

    public ProductAction setCheckoutStep(int i) {
        a("&cos", Integer.toString(i));
        return this;
    }

    public ProductAction setProductActionList(String str) {
        a("&pal", str);
        return this;
    }

    public ProductAction setProductListSource(String str) {
        a("&pls", str);
        return this;
    }

    public ProductAction setTransactionAffiliation(String str) {
        a("&ta", str);
        return this;
    }

    public ProductAction setTransactionCouponCode(String str) {
        a("&tcc", str);
        return this;
    }

    public ProductAction setTransactionId(String str) {
        a("&ti", str);
        return this;
    }

    public ProductAction setTransactionRevenue(double d) {
        a("&tr", Double.toString(d));
        return this;
    }

    public ProductAction setTransactionShipping(double d) {
        a("&ts", Double.toString(d));
        return this;
    }

    public ProductAction setTransactionTax(double d) {
        a("&tt", Double.toString(d));
        return this;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        for (Entry entry : this.a.entrySet()) {
            hashMap.put(((String) entry.getKey()).startsWith("&") ? ((String) entry.getKey()).substring(1) : (String) entry.getKey(), (String) entry.getValue());
        }
        return zzg.zzap(hashMap);
    }
}
