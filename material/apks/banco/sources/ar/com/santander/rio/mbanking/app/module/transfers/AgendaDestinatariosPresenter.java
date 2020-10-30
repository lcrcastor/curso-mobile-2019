package ar.com.santander.rio.mbanking.app.module.transfers;

import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean;

public interface AgendaDestinatariosPresenter {
    void onCreatePage(String str, String str2, CuentasPropiasBean cuentasPropiasBean, AgendadosBean agendadosBean, AgendaDestinatarios agendaDestinatarios, String str3);
}
