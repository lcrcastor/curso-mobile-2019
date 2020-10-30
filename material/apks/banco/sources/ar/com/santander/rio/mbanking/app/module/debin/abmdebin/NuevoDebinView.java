package ar.com.santander.rio.mbanking.app.module.debin.abmdebin;

import android.app.FragmentManager;
import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarTitularCuentaBodyResponseBean;

public interface NuevoDebinView extends IBaseView {
    Context getContext();

    FragmentManager getFragmentManager();

    void setComprobanteDebinView(AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean);

    void setDebinRes4View(String str, Boolean bool);

    void setGenerarDebinView(ConsultarTitularCuentaBodyResponseBean consultarTitularCuentaBodyResponseBean);

    void setLayoutSinCuentasVisible();

    void setNuevoDebinView();
}
