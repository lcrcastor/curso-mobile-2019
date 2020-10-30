package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ConsultaPrestamosPermitidosEventProd;
import ar.com.santander.rio.mbanking.services.events.ConsultaSolicitudCrediticiaEventProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;

public interface InicioCreditoPresenterProd {
    void getResponseConsultaPrestamosPermitidos(ConsultaPrestamosPermitidosEventProd consultaPrestamosPermitidosEventProd);

    void onCreatePage(ConsultaSolicitudCrediticiaEventProd consultaSolicitudCrediticiaEventProd, AccountRequestBean accountRequestBean);

    void onItemClicked(int i);

    void sendRequestConsultaPrestamosPermitidos(IDataManager iDataManager, boolean z);

    void validate();
}
