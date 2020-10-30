package ar.com.santander.rio.mbanking.app.module.nuevoalias;

import ar.com.santander.rio.mbanking.app.base.IBaseView;

public interface NuevoAliasConfirmacionView extends IBaseView {
    void goToComprobanteABMAlias(String str, String str2, String str3);

    void setNuevoAliasConfirmacionView();

    void showDialogReasigna(String str, String str2);
}
