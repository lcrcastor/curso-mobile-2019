package ar.com.santander.rio.mbanking.app.module.buzonNotifPush;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.NotificacionPushBean;
import java.util.List;

public interface BuzonNotifPushView extends IBaseView {
    Context getFragmentContext();

    void setBuzonNotifPushView(List<NotificacionPushBean> list, String str, String str2);
}
