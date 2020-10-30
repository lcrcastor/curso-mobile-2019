package ar.com.santander.rio.mbanking.app.ui.interfaces;

import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetallePreAutorizacionCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import java.util.List;

public interface PreAutorizacionDebinActivity {
    void consultaPreAutorizaciones(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean, Boolean bool);

    void desconocimientoPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean);

    List<ListGroupBean> getListTableConDebin();

    List<ListGroupBean> getListTableEstpreaut();

    List<ListGroupBean> getListTableMonedaDescSimbolo();

    List<ListGroupBean> getListTablePeriodoPreAut();

    List<ListGroupBean> getListTableTpoCtaCorta();

    /* renamed from: irAComprobanteDeOperación reason: contains not printable characters */
    void m2irAComprobanteDeOperacin(String str, String str2, String str3, DetallePreAutorizacionBean detallePreAutorizacionBean, String str4);

    void irAlFragmentDetallePreAutorizacionComprador(GetDetallePreAutorizacionCompradorBodyResponseBean getDetallePreAutorizacionCompradorBodyResponseBean);

    void procesarPreAutorizacion(String str, DetallePreAutorizacionBean detallePreAutorizacionBean);

    void updatePreAutorizaciones(String str, String str2);
}
