package ar.com.santander.rio.mbanking.app.module.modificacionAlias;

import ar.com.santander.rio.mbanking.app.base.IBaseView;

public interface ModificacionAliasConfirmacionView extends IBaseView {
    void goToComprobanteABMAlias(String str, String str2, String str3);

    void setModificacionAliasConfirmacionView();

    void showDialogReasigna(String str, String str2);
}
