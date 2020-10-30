package ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CtaMigradaBean;

public interface NuevoDestinatarioTransferenciaView extends IBaseView {
    void goToAltaSinVerificar();

    void goToEditarDestinatario(VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean, CtaMigradaBean ctaMigradaBean);

    void goToNuevoDestinatario();

    void onErrorVerificacionCBU();

    void setNuevoDestinatarioView();

    void supermetododeprueba(CtaMigradaBean ctaMigradaBean);
}
