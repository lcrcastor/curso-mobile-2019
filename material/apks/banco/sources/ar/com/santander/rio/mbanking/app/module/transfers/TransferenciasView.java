package ar.com.santander.rio.mbanking.app.module.transfers;

import ar.com.santander.rio.mbanking.app.commons.StepsView;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaSalidaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;
import java.util.ArrayList;

public interface TransferenciasView extends StepsView {
    void dismissProgress();

    IDataManager getDataManager();

    SessionManager getSessionManager();

    void goToAgendaDestinatarios(String str, String str2, CuentasPropiasBean cuentasPropiasBean, AgendadosBean agendadosBean, String str3);

    void goToComprobanteTransferencia(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosTransferenciaSalidaBean datosTransferenciaSalidaBean, LeyendasBean leyendasBean);

    void goToConfirmacionTransferencia(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean, LeyendasBean leyendasBean);

    void goToLegales(LeyendasBean leyendasBean);

    void goToLimitesHorariosTransferencias(LeyendasBean leyendasBean);

    void goToTerminosCondiciones(LeyendasBean leyendasBean);

    void goToTransferencias();

    void setAgendaDestinatariosView(String str, String str2, CuentasPropiasBean cuentasPropiasBean, AgendadosBean agendadosBean, AgendaDestinatarios agendaDestinatarios, String str3);

    void setComprobanteTransferenciaView(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosTransferenciaSalidaBean datosTransferenciaSalidaBean, LeyendasBean leyendasBean);

    void setConfirmacionTransferenciaView(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean, LeyendasBean leyendasBean);

    void showMessage(String str, String str2);

    void showProgress();
}
