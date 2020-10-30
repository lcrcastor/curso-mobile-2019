package ar.com.santander.rio.mbanking.app.module.marcacionPorViaje;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean;
import com.squareup.otto.Bus;

public class SeleccionarTarjetasMarcacionPresenter extends BasePresenter<SeleccionarTarjetasMarcacionView> {
    protected Context mContext;

    public SeleccionarTarjetasMarcacionPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void onCreatePage(UsuariosMarcacionBean usuariosMarcacionBean) {
        ((SeleccionarTarjetasMarcacionView) getBaseView()).setSeleccionarTarjetaMarcacionView(usuariosMarcacionBean);
    }
}
