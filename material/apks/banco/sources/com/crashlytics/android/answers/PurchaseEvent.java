package com.crashlytics.android.answers;

import com.facebook.Response;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.math.BigDecimal;
import java.util.Currency;

public class PurchaseEvent extends PredefinedEvent<PurchaseEvent> {
    static final BigDecimal a = BigDecimal.valueOf(1000000);

    /* access modifiers changed from: 0000 */
    public String a() {
        return ProductAction.ACTION_PURCHASE;
    }

    public PurchaseEvent putItemId(String str) {
        this.d.a("itemId", str);
        return this;
    }

    public PurchaseEvent putItemName(String str) {
        this.d.a("itemName", str);
        return this;
    }

    public PurchaseEvent putItemType(String str) {
        this.d.a("itemType", str);
        return this;
    }

    public PurchaseEvent putItemPrice(BigDecimal bigDecimal) {
        if (!this.b.a((Object) bigDecimal, "itemPrice")) {
            this.d.a("itemPrice", (Number) Long.valueOf(a(bigDecimal)));
        }
        return this;
    }

    public PurchaseEvent putCurrency(Currency currency) {
        if (!this.b.a((Object) currency, "currency")) {
            this.d.a("currency", currency.getCurrencyCode());
        }
        return this;
    }

    public PurchaseEvent putSuccess(boolean z) {
        this.d.a(Response.SUCCESS_KEY, Boolean.toString(z));
        return this;
    }

    /* access modifiers changed from: 0000 */
    public long a(BigDecimal bigDecimal) {
        return a.multiply(bigDecimal).longValue();
    }
}
