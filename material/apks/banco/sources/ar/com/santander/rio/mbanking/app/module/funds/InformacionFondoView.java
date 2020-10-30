package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.InformacionFondoBean;

public interface InformacionFondoView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    Context getContext();

    void popUpErrorDownload();

    void setInformacionFondoView(InformacionFondoBean informacionFondoBean);
}
