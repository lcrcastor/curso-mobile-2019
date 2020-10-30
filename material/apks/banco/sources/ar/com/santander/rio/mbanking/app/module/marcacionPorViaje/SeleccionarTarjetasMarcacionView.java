package ar.com.santander.rio.mbanking.app.module.marcacionPorViaje;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean;

public interface SeleccionarTarjetasMarcacionView extends IBaseView {
    void setSeleccionarTarjetaMarcacionView(UsuariosMarcacionBean usuariosMarcacionBean);
}
