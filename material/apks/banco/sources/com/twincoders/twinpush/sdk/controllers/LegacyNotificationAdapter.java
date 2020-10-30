package com.twincoders.twinpush.sdk.controllers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.List;

public abstract class LegacyNotificationAdapter extends BaseAdapter implements com.twincoders.twinpush.sdk.controllers.LegacyNotificationItemView.Listener {
    List<PushNotification> a;
    Listener b;
    Context c;

    public interface Listener {
        void onNotificationLongClicked(PushNotification pushNotification);

        void onNotificationSelected(PushNotification pushNotification);
    }

    public long getItemId(int i) {
        return 0;
    }

    public abstract LegacyNotificationItemView getViewInstance(Context context);

    public LegacyNotificationAdapter(Context context) {
        this.c = context;
    }

    public int getCount() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public PushNotification getItem(int i) {
        if (i < this.a.size()) {
            return (PushNotification) this.a.get(i);
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        LegacyNotificationItemView legacyNotificationItemView = view instanceof LegacyNotificationItemView ? (LegacyNotificationItemView) view : null;
        if (view == null || legacyNotificationItemView == null) {
            legacyNotificationItemView = getViewInstance(this.c);
        }
        if (legacyNotificationItemView != null) {
            legacyNotificationItemView.setNotification(getItem(i));
            legacyNotificationItemView.setListener(this);
        }
        return (View) legacyNotificationItemView;
    }

    public void setListener(Listener listener) {
        this.b = listener;
    }

    public void setNotifications(List<PushNotification> list) {
        this.a = list;
    }

    public List<PushNotification> getNotifications() {
        return this.a;
    }

    public void onNotificationSelected(PushNotification pushNotification) {
        if (this.b != null) {
            TwinPushSDK.getInstance(this.c).onNotificationOpen(pushNotification);
            this.b.onNotificationSelected(pushNotification);
        }
    }

    public void onNotificationLongClicked(PushNotification pushNotification) {
        if (this.b != null) {
            this.b.onNotificationLongClicked(pushNotification);
        }
    }
}
