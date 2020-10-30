package ar.com.santander.rio.mbanking.app.base;

import com.squareup.otto.Bus;

public class ReceiptEventBus extends Bus {
    private static final ReceiptEventBus a = new ReceiptEventBus();

    public static ReceiptEventBus getInstance() {
        return a;
    }

    private ReceiptEventBus() {
    }
}
