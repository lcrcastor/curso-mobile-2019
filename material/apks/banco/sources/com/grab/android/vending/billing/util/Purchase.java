package com.grab.android.vending.billing.util;

import org.json.JSONObject;

public class Purchase {
    String a;
    String b;
    String c;
    String d;
    long e;
    int f;
    String g;
    String h;
    String i;
    String j;

    public Purchase(String str, String str2, String str3) {
        this.a = str;
        this.i = str2;
        JSONObject jSONObject = new JSONObject(this.i);
        this.b = jSONObject.optString("orderId");
        this.c = jSONObject.optString("packageName");
        this.d = jSONObject.optString("productId");
        this.e = jSONObject.optLong("purchaseTime");
        this.f = jSONObject.optInt("purchaseState");
        this.g = jSONObject.optString("developerPayload");
        this.h = jSONObject.optString("token", jSONObject.optString("purchaseToken"));
        this.j = str3;
    }

    public String getItemType() {
        return this.a;
    }

    public String getOrderId() {
        return this.b;
    }

    public String getPackageName() {
        return this.c;
    }

    public String getSku() {
        return this.d;
    }

    public long getPurchaseTime() {
        return this.e;
    }

    public int getPurchaseState() {
        return this.f;
    }

    public String getDeveloperPayload() {
        return this.g;
    }

    public String getToken() {
        return this.h;
    }

    public String getOriginalJson() {
        return this.i;
    }

    public String getSignature() {
        return this.j;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PurchaseInfo(type:");
        sb.append(this.a);
        sb.append("):");
        sb.append(this.i);
        return sb.toString();
    }
}
