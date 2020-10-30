package ar.com.santander.rio.mbanking.app.ui.activities.recargasubecomprobantepago;

import ar.com.santander.rio.mbanking.app.base.IBasePresenter;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;

interface RecargaSubeComprobantePagoContract {

    public interface Presenter extends IBasePresenter<View> {
        void initialize(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean, String str);
    }

    public interface View extends IBaseView {
        void setView(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean, String str);
    }
}
