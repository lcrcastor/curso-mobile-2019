package ar.com.santander.rio.mbanking.app.module.notifications;

import ar.com.santander.rio.mbanking.services.soap.beans.body.Crm;

public interface NotificationPresenter {
    void onCreateView(Crm crm);
}
