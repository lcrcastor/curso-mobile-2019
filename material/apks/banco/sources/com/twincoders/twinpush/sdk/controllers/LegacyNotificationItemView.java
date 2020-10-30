package com.twincoders.twinpush.sdk.controllers;

import com.twincoders.twinpush.sdk.notifications.PushNotification;

public interface LegacyNotificationItemView {

    public interface Listener {
        void onNotificationLongClicked(PushNotification pushNotification);

        void onNotificationSelected(PushNotification pushNotification);
    }

    void setListener(Listener listener);

    void setNotification(PushNotification pushNotification);
}
