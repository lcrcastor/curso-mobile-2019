package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import java.util.List;

public interface DetalleFondoView extends IBaseView {
    void gotoMovimiento(List<MovimientoFondosBean> list, String str);

    void setDetalleFondoView();

    void suscribirFondo(MovCtasBodyResponseBean movCtasBodyResponseBean);
}
