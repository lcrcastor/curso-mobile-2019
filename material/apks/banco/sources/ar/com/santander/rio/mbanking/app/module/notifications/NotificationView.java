package ar.com.santander.rio.mbanking.app.module.notifications;

import ar.com.santander.rio.mbanking.services.model.general.Notificacion;
import java.util.List;

public interface NotificationView {
    void showListNotifications(List<Notificacion> list);
}
