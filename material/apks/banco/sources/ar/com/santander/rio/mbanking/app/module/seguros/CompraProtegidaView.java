package ar.com.santander.rio.mbanking.app.module.seguros;

import android.app.FragmentManager;
import ar.com.santander.rio.mbanking.app.base.IBaseView;

public interface CompraProtegidaView extends IBaseView {
    FragmentManager getFragmentManager();

    void gotoSeleccionarCobertura();

    void initializeOptionsCardDialog();

    void setCompraProtegidaView();
}
