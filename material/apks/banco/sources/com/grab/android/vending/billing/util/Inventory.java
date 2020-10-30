package com.grab.android.vending.billing.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    Map<String, SkuDetails> a = new HashMap();
    Map<String, Purchase> b = new HashMap();

    Inventory() {
    }

    public SkuDetails getSkuDetails(String str) {
        return (SkuDetails) this.a.get(str);
    }

    public Purchase getPurchase(String str) {
        return (Purchase) this.b.get(str);
    }

    public boolean hasPurchase(String str) {
        return this.b.containsKey(str);
    }

    public boolean hasDetails(String str) {
        return this.a.containsKey(str);
    }

    public void erasePurchase(String str) {
        if (this.b.containsKey(str)) {
            this.b.remove(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public List<String> a(String str) {
        ArrayList arrayList = new ArrayList();
        for (Purchase purchase : this.b.values()) {
            if (purchase.getItemType().equals(str)) {
                arrayList.add(purchase.getSku());
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public void a(SkuDetails skuDetails) {
        this.a.put(skuDetails.getSku(), skuDetails);
    }

    /* access modifiers changed from: 0000 */
    public void a(Purchase purchase) {
        this.b.put(purchase.getSku(), purchase);
    }
}
