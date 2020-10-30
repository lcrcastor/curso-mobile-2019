package ar.com.santander.rio.mbanking.app.module.tokenOBP;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.EnviarTokenOBPEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class TokenOBPPresenter extends BasePresenter<TokenOBPView> {
    protected Context context;

    public TokenOBPPresenter(Bus bus) {
        super(bus);
    }

    public TokenOBPPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void enviarTokenOBP(String str, String str2, String str3, String str4) {
        this.mDataManager.enviarTokenOBP(str, str2, str3, str4);
    }

    @Subscribe
    public void onEnviarTokenOBP(EnviarTokenOBPEvent enviarTokenOBPEvent) {
        ((TokenOBPView) getBaseView()).dismissProgressIndicator();
        if (enviarTokenOBPEvent.getResult() == TypeResult.OK) {
            ((TokenOBPView) getBaseView()).cerrarApp();
        } else if (enviarTokenOBPEvent.getResult() == TypeResult.BEAN_ERROR_RES_4) {
            ((TokenOBPView) getBaseView()).goToCaraTriste();
        } else {
            ((TokenOBPView) getBaseView()).cerrarApp();
        }
    }
}
