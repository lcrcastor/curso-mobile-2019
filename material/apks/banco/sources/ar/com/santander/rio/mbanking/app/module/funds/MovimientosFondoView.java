package ar.com.santander.rio.mbanking.app.module.funds;

import android.app.FragmentManager;
import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import java.util.List;

public interface MovimientosFondoView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    Context getContext();

    FragmentManager getFragmentManager();

    void gotoBusquedaAvanzada();

    void setMovimientosFondoView(List<MovimientoFondosBean> list, String str);
}
