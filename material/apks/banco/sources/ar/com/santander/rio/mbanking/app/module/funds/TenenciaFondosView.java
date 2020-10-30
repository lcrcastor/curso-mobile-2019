package ar.com.santander.rio.mbanking.app.module.funds;

import android.support.v4.app.FragmentManager;
import android.view.View;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaFondosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import java.util.List;

public interface TenenciaFondosView extends IBaseView {
    void addBlockBodyList(View view);

    FragmentManager getFragmentManager();

    void gotoDetalleFondo(FondoBean fondoBean);

    void gotoInformacionFondosActivity(List<CategoriaFondosBean> list);

    void gotoSuscribirFondoActivity(List<CategoriaFondosBean> list, GetTenenciaFondosBodyResponseBean getTenenciaFondosBodyResponseBean);

    void gotoUltimosMovimientosActivity(List<CategoriaFondosBean> list);

    boolean isFromDeepLink();

    void setSelectedAccount(CuentaFondosBean cuentaFondosBean);

    void setTenenciaFondosBodyResponseBean(GetTenenciaFondosBodyResponseBean getTenenciaFondosBodyResponseBean);

    void setTenenciaFondosView(List<CuentaFondosBean> list, Boolean bool, String str, String str2, List<Leyenda> list2, String str3);
}
