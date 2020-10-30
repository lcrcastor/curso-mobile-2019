package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import android.content.Intent;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import com.squareup.otto.Bus;

public class SolicitarSeguroPresenter extends BasePresenter<SolicitarSeguroView> {
    public SolicitarSeguroPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(CotizacionBean cotizacionBean) {
        ((SolicitarSeguroView) getBaseView()).setSolicitarSeguroView(cotizacionBean);
    }

    public Context getContext() {
        return ((SolicitarSeguroView) getBaseView()).getContext();
    }

    public void showDetalle(String str, String str2) {
        Intent intent = new Intent(getContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, str);
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str2);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.ICON_TO_SHOW, R.drawable.ic_close);
        getContext().startActivity(intent);
    }
}
