package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import java.util.List;

public interface InfoAdmFondosView extends IBaseView {
    void setHonorariosFondoView(List<FondoBean> list, String str);

    void setHorariosFondoView(List<FondoBean> list, String str);
}
