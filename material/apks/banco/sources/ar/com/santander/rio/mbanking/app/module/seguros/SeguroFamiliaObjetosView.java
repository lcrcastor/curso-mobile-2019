package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean;

public interface SeguroFamiliaObjetosView extends IBaseView {
    void gotoFamiliaObjeto(GetFamiliasObjetosBodyResponseBean getFamiliasObjetosBodyResponseBean);

    void showOnBoarding();
}
