package ar.com.santander.rio.mbanking.app.module.transfers;

import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean;

public class AgendaDestinatariosPresenterImp implements AgendaDestinatariosPresenter {
    public TransferenciasView transferenciasView;

    public AgendaDestinatariosPresenterImp(TransferenciasView transferenciasView2) {
        this.transferenciasView = transferenciasView2;
    }

    public void onCreatePage(String str, String str2, CuentasPropiasBean cuentasPropiasBean, AgendadosBean agendadosBean, AgendaDestinatarios agendaDestinatarios, String str3) {
        this.transferenciasView.setAgendaDestinatariosView(str, str2, cuentasPropiasBean, agendadosBean, agendaDestinatarios, str3);
    }
}
