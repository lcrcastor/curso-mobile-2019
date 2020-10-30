package ar.com.santander.rio.mbanking.app.module.debin;

import android.support.v4.app.FragmentManager;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleDebinBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListDebinesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCuentasVendedorBean;
import java.util.List;

public interface DebinView extends IBaseView {
    void deshabilitarBotones();

    FragmentManager getFragmentManager();

    void goToAbmDebin(DetalleDebinBean detalleDebinBean, ListDebinesBean listDebinesBean, String str, String str2);

    void goToBuscador();

    void goToDebin();

    void goToFirmaCredin(String str, String str2);

    void goToGestionAdhesionDebin(ListaCuentasVendedorBean listaCuentasVendedorBean);

    void goToNuevoDebin(ListaCuentasVendedorBean listaCuentasVendedorBean, String str, String str2);

    void goToPreAutorizacion(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean, String str);

    void habilitarBotones();

    void mostrarPopUpSinToken();

    void setBackgroundVisibleInit();

    void setDebinesView(List<ListDebinesBean> list, String str, String str2);

    void setDetalleDebinCompradorView(DetalleDebinBean detalleDebinBean, String str);

    void setDetalleDebinVendedorView(DetalleDebinBean detalleDebinBean);

    void setErrorRecallDebinesView(String str);

    void setErrorRecallPreAutorizacionesView(String str);

    void setPreAutorizacionesView(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean, String str);

    void setVisibilityDetalles();
}
