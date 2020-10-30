package com.twincoders.twinpush.sdk.controllers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.entities.InboxNotification;
import java.util.List;

public abstract class NotifListAdapter extends BaseAdapter implements com.twincoders.twinpush.sdk.controllers.NotificationListItemView.Listener {
    List<InboxNotification> a;
    Listener b;
    Context c;

    public interface Listener {
        void onNotificationLongClicked(InboxNotification inboxNotification);

        void onNotificationSelected(InboxNotification inboxNotification);
    }

    public long getItemId(int i) {
        return 0;
    }

    public abstract NotificationListItemView getViewInstance(Context context);

    public NotifListAdapter(Context context) {
        this.c = context;
    }

    public int getCount() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public InboxNotification getItem(int i) {
        if (i < this.a.size()) {
            return (InboxNotification) this.a.get(i);
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        NotificationListItemView notificationListItemView = view instanceof NotificationListItemView ? (NotificationListItemView) view : null;
        if (view == null || notificationListItemView == null) {
            notificationListItemView = getViewInstance(this.c);
        }
        if (notificationListItemView != null) {
            notificationListItemView.setNotification(getItem(i));
            notificationListItemView.setListener(this);
        }
        return (View) notificationListItemView;
    }

    public void setListener(Listener listener) {
        this.b = listener;
    }

    public void setNotifications(List<InboxNotification> list) {
        this.a = list;
    }

    public List<InboxNotification> getNotifications() {
        return this.a;
    }

    public void onNotificationSelected(InboxNotification inboxNotification) {
        if (this.b != null) {
            TwinPushSDK.getInstance(this.c).onNotificationOpen(inboxNotification.getNotification());
            this.b.onNotificationSelected(inboxNotification);
        }
    }

    public void onNotificationLongClicked(InboxNotification inboxNotification) {
        if (this.b != null) {
            this.b.onNotificationLongClicked(inboxNotification);
        }
    }
}
