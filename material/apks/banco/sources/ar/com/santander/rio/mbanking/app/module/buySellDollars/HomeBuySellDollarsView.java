package ar.com.santander.rio.mbanking.app.module.buySellDollars;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.events.SimulacionDolarEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDatosInicialesDolaresResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimulacionDolarResponseBean;
import java.util.ArrayList;

public interface HomeBuySellDollarsView extends IBaseView {
    Context getContext();

    void onGetDatosInicialesDolares(GetDatosInicialesDolaresResponseBean getDatosInicialesDolaresResponseBean);

    void onSelectDestinationAccount();

    void onSelectOriginAccount();

    void onSimulacionDolar(SimulacionDolarResponseBean simulacionDolarResponseBean);

    void showDialogSelector(String str, String str2, ArrayList<String> arrayList, String str3);

    void showOnBoarding();

    void showSadError(String str);

    void showTimeError(String str);

    void warnExchangeRateChanged(SimulacionDolarEvent simulacionDolarEvent);
}
