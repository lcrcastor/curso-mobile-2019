package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants.InfoAdmViewMode;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesAdmFondos;
import com.squareup.otto.Bus;
import java.util.List;

public class InfoAdmFondosPresenter extends BasePresenter<InfoAdmFondosView> {
    protected Context mContext;

    public InfoAdmFondosPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void onCreate(List<FondoBean> list, LegalesAdmFondos legalesAdmFondos, String str) {
        if (str.equals(InfoAdmViewMode.VIEWMODE_HONORARIOS)) {
            ((InfoAdmFondosView) getBaseView()).setHonorariosFondoView(list, legalesAdmFondos.getLegalHonorarios());
        } else if (str.equals(InfoAdmViewMode.VIEWMODE_HORARIOS)) {
            ((InfoAdmFondosView) getBaseView()).setHorariosFondoView(list, legalesAdmFondos.getLegalHorarios());
        }
    }
}
