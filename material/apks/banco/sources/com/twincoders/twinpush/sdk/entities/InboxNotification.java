package com.twincoders.twinpush.sdk.entities;

import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.Date;

public class InboxNotification {
    String a;
    Date b;
    Date c;
    PushNotification d;

    public String getId() {
        return this.a;
    }

    public void setId(String str) {
        this.a = str;
    }

    public Date getCreatedAt() {
        return this.b;
    }

    public void setCreatedAt(Date date) {
        this.b = date;
    }

    public Date getOpenAt() {
        return this.c;
    }

    public void setOpenAt(Date date) {
        this.c = date;
    }

    public PushNotification getNotification() {
        return this.d;
    }

    public void setNotification(PushNotification pushNotification) {
        this.d = pushNotification;
    }
}
