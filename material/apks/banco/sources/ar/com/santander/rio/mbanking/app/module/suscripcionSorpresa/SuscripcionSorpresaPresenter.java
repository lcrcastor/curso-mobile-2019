package ar.com.santander.rio.mbanking.app.module.suscripcionSorpresa;

import ar.com.santander.rio.mbanking.services.model.general.Destinos;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ActualizarMensajesMyABodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSuscripcionMyAResponeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.view.PhoneSelectorView;
import ar.com.santander.rio.mbanking.view.tables.RowTwoColumnViewStyled;

public interface SuscripcionSorpresaPresenter {
    void getResponseActualizarMensajes(ErrorBodyBean errorBodyBean);

    void getResponseConsultaSuscripcion(ConsultaSuscripcionMyAResponeBodyResponseBean consultaSuscripcionMyAResponeBodyResponseBean);

    void getResponseModificarSuscripcion(ErrorBodyBean errorBodyBean);

    void getResponseRequisitos();

    void getResponseTerminosYCondiciones();

    boolean isOnlyMailModified();

    boolean isSuscrito();

    void onButtonAction(RowTwoColumnViewStyled rowTwoColumnViewStyled, PhoneSelectorView phoneSelectorView, PhoneSelectorView phoneSelectorView2);

    void onPageCreated();

    void sendRequestActualizarMensajes(ActualizarMensajesMyABodyRequestBean actualizarMensajesMyABodyRequestBean, boolean z);

    void sendRequestConsultaSuscripcion();

    void sendRequestModificarSuscripcion(Destinos destinos);

    void terminosAceptados(boolean z);
}
