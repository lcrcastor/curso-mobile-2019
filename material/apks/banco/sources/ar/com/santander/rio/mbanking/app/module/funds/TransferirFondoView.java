package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularTransferenciaFondoBodyResponseBean;
import java.util.List;

public interface TransferirFondoView extends IBaseView {
    Context getContext();

    void gotoConfirmacion(SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean);

    void gotoFueraHorarioFondo(String str);

    boolean isValidDataTransferir();

    void popUpErrorDownload();

    void setSelectedAccount(CuentaFondosBean cuentaFondosBean);

    void setSelectedAmountTypeCuotapartes();

    void setSelectedAmountTypeOtro();

    void setSelectedAmountTypeTotal();

    void setSelectedFondoDestino(FondoBean fondoBean);

    void setSelectedFondoOrigen(FondoBean fondoBean);

    void setTransferirFondoView();

    void showSelectDestinyFundActivity(List<CategoriaFondosBean> list);
}
