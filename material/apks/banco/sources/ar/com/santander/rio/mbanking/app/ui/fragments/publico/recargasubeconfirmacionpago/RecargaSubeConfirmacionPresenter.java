package ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago;

import android.content.pm.PackageManager;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago.RecargaSubeConfirmacionPagoContract.Presenter;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago.RecargaSubeConfirmacionPagoContract.View;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.model.general.RecargaComprobantePago;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilDeviceInfo;
import com.squareup.otto.Bus;

public class RecargaSubeConfirmacionPresenter extends BasePresenter<View> implements Presenter {
    private RecargaComprobantePago a;

    public RecargaSubeConfirmacionPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
        RecargaComprobantePago recargaComprobantePago = new RecargaComprobantePago("$150", "6061 0482 0931", "Juan Valdez", "Cuenta Unica 120-345678/9", "13/11/2019 12:34 hs", "000123123123");
        this.a = recargaComprobantePago;
    }

    public void initialize(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean, String str) {
        View view = (View) this.mBaseView;
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        view.setView(pagoServiciosBodyResponseBean, str);
    }

    public void checkSubeApp(PackageManager packageManager, String str) {
        if (UtilDeviceInfo.isPackageInstalled(str, packageManager)) {
            ((View) this.mBaseView).visibilityLogoSube(true);
        } else {
            ((View) this.mBaseView).visibilityLogoSube(false);
        }
    }
}
