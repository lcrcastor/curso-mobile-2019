package com.crashlytics.android.answers;

import java.math.BigDecimal;
import java.util.Currency;

public class AddToCartEvent extends PredefinedEvent<AddToCartEvent> {
    static final BigDecimal a = BigDecimal.valueOf(1000000);

    /* access modifiers changed from: 0000 */
    public String a() {
        return "addToCart";
    }

    public AddToCartEvent putItemId(String str) {
        this.d.a("itemId", str);
        return this;
    }

    public AddToCartEvent putItemName(String str) {
        this.d.a("itemName", str);
        return this;
    }

    public AddToCartEvent putItemType(String str) {
        this.d.a("itemType", str);
        return this;
    }

    public AddToCartEvent putItemPrice(BigDecimal bigDecimal) {
        if (!this.b.a((Object) bigDecimal, "itemPrice")) {
            this.d.a("itemPrice", (Number) Long.valueOf(a(bigDecimal)));
        }
        return this;
    }

    public AddToCartEvent putCurrency(Currency currency) {
        if (!this.b.a((Object) currency, "currency")) {
            this.d.a("currency", currency.getCurrencyCode());
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public long a(BigDecimal bigDecimal) {
        return a.multiply(bigDecimal).longValue();
    }
}
