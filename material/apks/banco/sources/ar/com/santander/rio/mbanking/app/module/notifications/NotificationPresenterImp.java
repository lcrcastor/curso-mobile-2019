package ar.com.santander.rio.mbanking.app.module.notifications;

import ar.com.santander.rio.mbanking.services.soap.beans.body.Crm;
import ar.com.santander.rio.mbanking.utils.UtilString;

public class NotificationPresenterImp implements NotificationPresenter {
    private NotificationView a;

    public NotificationPresenterImp(NotificationView notificationView) {
        this.a = notificationView;
    }

    public void onCreateView(Crm crm) {
        if (crm != null && UtilString.isNumber(crm.cantidad) && Integer.parseInt(crm.cantidad) > 0) {
            this.a.showListNotifications(crm.getNotificaciones().getListNotificaciones());
        }
    }
}
