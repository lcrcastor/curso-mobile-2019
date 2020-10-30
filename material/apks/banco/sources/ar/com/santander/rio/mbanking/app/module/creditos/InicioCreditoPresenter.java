package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBean;

public interface InicioCreditoPresenter {
    void getResponseConsultaPrestamosPermitidos(ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean);

    void onCreatePage(ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean, AccountRequestBean accountRequestBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean);

    void onItemClicked(int i);

    void sendRequestConsultaPrestamosPermitidos(IDataManager iDataManager, boolean z);

    void validate();
}
