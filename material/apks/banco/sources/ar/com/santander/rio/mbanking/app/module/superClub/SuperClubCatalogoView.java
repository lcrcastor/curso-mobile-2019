package ar.com.santander.rio.mbanking.app.module.superClub;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSCBodyResponseBean;

public interface SuperClubCatalogoView extends IBaseView {
    void setCatalogoView(GetFirmaSCBodyResponseBean getFirmaSCBodyResponseBean);
}
