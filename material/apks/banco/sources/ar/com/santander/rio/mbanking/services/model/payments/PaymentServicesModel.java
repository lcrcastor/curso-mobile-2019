package ar.com.santander.rio.mbanking.services.model.payments;

import ar.com.santander.rio.mbanking.app.module.payments.commons.TypeDebt;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;

public class PaymentServicesModel {
    private CuentaDebitoBean mAccountSelected;
    private DatosDeudaBean mDatosDeudaBeanSelected;
    private TypeDebt mTypeDebt;
    private double originalImporte = 0.0d;
}
