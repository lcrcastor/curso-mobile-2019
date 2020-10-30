package com.squareup.picasso;

import android.app.Notification;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.twincoders.twinpush.sdk.services.NotificationIntentService;

abstract class RemoteViewsAction extends Action<RemoteViewsTarget> {
    final RemoteViews m;
    final int n;
    private RemoteViewsTarget o;

    static class AppWidgetAction extends RemoteViewsAction {
        private final int[] o;

        /* access modifiers changed from: 0000 */
        public /* synthetic */ Object d() {
            return RemoteViewsAction.super.d();
        }

        AppWidgetAction(Picasso picasso, Request request, RemoteViews remoteViews, int i, int[] iArr, int i2, int i3, String str, Object obj, int i4) {
            super(picasso, request, remoteViews, i, i4, i2, i3, obj, str);
            this.o = iArr;
        }

        /* access modifiers changed from: 0000 */
        public void n() {
            AppWidgetManager.getInstance(this.a.c).updateAppWidget(this.o, this.m);
        }
    }

    static class NotificationAction extends RemoteViewsAction {
        private final int o;
        private final Notification p;

        /* access modifiers changed from: 0000 */
        public /* synthetic */ Object d() {
            return RemoteViewsAction.super.d();
        }

        NotificationAction(Picasso picasso, Request request, RemoteViews remoteViews, int i, int i2, Notification notification, int i3, int i4, String str, Object obj, int i5) {
            super(picasso, request, remoteViews, i, i5, i3, i4, obj, str);
            this.o = i2;
            this.p = notification;
        }

        /* access modifiers changed from: 0000 */
        public void n() {
            ((NotificationManager) Utils.a(this.a.c, NotificationIntentService.EXTRA_NOTIFICATION)).notify(this.o, this.p);
        }
    }

    static class RemoteViewsTarget {
        final RemoteViews a;
        final int b;

        RemoteViewsTarget(RemoteViews remoteViews, int i) {
            this.a = remoteViews;
            this.b = i;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            RemoteViewsTarget remoteViewsTarget = (RemoteViewsTarget) obj;
            if (this.b != remoteViewsTarget.b || !this.a.equals(remoteViewsTarget.a)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void n();

    RemoteViewsAction(Picasso picasso, Request request, RemoteViews remoteViews, int i, int i2, int i3, int i4, Object obj, String str) {
        super(picasso, null, request, i3, i4, i2, null, str, obj, false);
        this.m = remoteViews;
        this.n = i;
    }

    /* access modifiers changed from: 0000 */
    public void a(Bitmap bitmap, LoadedFrom loadedFrom) {
        this.m.setImageViewBitmap(this.n, bitmap);
        n();
    }

    public void a() {
        if (this.g != 0) {
            a(this.g);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: m */
    public RemoteViewsTarget d() {
        if (this.o == null) {
            this.o = new RemoteViewsTarget(this.m, this.n);
        }
        return this.o;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
        this.m.setImageViewResource(this.n, i);
        n();
    }
}
