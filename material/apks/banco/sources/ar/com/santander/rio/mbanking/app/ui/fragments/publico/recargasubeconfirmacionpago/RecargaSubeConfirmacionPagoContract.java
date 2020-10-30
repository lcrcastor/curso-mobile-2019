package ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago;

import android.content.pm.PackageManager;
import ar.com.santander.rio.mbanking.app.base.IBasePresenter;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;

interface RecargaSubeConfirmacionPagoContract {

    public interface Presenter extends IBasePresenter<View> {
        void checkSubeApp(PackageManager packageManager, String str);

        void initialize(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean, String str);
    }

    public interface View extends IBaseView {
        void setView(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean, String str);

        void visibilityLogoSube(boolean z);
    }
}
