package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSelectionSelectionMedioPago.MedioPago;
import java.util.List;

public interface IFormDataSelectionMedioPago extends IFormDataSelection {
    MedioPago getMedioPagoValue();

    List<MedioPago> getStringMedioPagoList();

    IFormDataSelectionMedioPago setStringMedioPagoList(List<MedioPago> list);

    void setValueTextData(String str);
}
