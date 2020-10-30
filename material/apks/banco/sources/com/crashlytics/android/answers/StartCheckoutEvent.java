package com.crashlytics.android.answers;

import java.math.BigDecimal;
import java.util.Currency;

public class StartCheckoutEvent extends PredefinedEvent<StartCheckoutEvent> {
    static final BigDecimal a = BigDecimal.valueOf(1000000);

    /* access modifiers changed from: 0000 */
    public String a() {
        return "startCheckout";
    }

    public StartCheckoutEvent putItemCount(int i) {
        this.d.a("itemCount", (Number) Integer.valueOf(i));
        return this;
    }

    public StartCheckoutEvent putTotalPrice(BigDecimal bigDecimal) {
        if (!this.b.a((Object) bigDecimal, "totalPrice")) {
            this.d.a("totalPrice", (Number) Long.valueOf(a(bigDecimal)));
        }
        return this;
    }

    public StartCheckoutEvent putCurrency(Currency currency) {
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
