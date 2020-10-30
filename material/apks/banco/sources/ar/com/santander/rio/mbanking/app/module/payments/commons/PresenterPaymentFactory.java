package ar.com.santander.rio.mbanking.app.module.payments.commons;

import ar.com.santander.rio.mbanking.app.module.payments.phones.PhoneTopUpPresenterImp;
import ar.com.santander.rio.mbanking.app.module.payments.services.PaymentServicesPresenterImp;

public class PresenterPaymentFactory {
    public static PaymentPresenter getPaymentPresenter(TypeDebt typeDebt, PaymentServicesView paymentServicesView) {
        switch (typeDebt) {
            case PS:
                return new PaymentServicesPresenterImp(paymentServicesView);
            case RC:
                return new PhoneTopUpPresenterImp(paymentServicesView);
            default:
                return null;
        }
    }
}
