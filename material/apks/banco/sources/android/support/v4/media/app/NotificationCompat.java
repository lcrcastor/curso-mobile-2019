package android.support.v4.media.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.media.session.MediaSession;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.mediacompat.R;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.widget.RemoteViews;

public class NotificationCompat {

    public static class DecoratedMediaCustomViewStyle extends MediaStyle {
        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(a((android.app.Notification.MediaStyle) new android.app.Notification.DecoratedMediaCustomViewStyle()));
            } else {
                super.apply(notificationBuilderWithBuilderAccessor);
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            boolean z = false;
            boolean z2 = this.mBuilder.getContentView() != null;
            if (VERSION.SDK_INT >= 21) {
                if (z2 || this.mBuilder.getBigContentView() != null) {
                    z = true;
                }
                if (z) {
                    RemoteViews b = b();
                    if (z2) {
                        buildIntoRemoteViews(b, this.mBuilder.getContentView());
                    }
                    a(b);
                    return b;
                }
            } else {
                RemoteViews b2 = b();
                if (z2) {
                    buildIntoRemoteViews(b2, this.mBuilder.getContentView());
                    return b2;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            if (this.mBuilder.getContentView() != null) {
                return R.layout.notification_template_media_custom;
            }
            return super.a();
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews remoteViews;
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            if (this.mBuilder.getBigContentView() != null) {
                remoteViews = this.mBuilder.getBigContentView();
            } else {
                remoteViews = this.mBuilder.getContentView();
            }
            if (remoteViews == null) {
                return null;
            }
            RemoteViews c = c();
            buildIntoRemoteViews(c, remoteViews);
            if (VERSION.SDK_INT >= 21) {
                a(c);
            }
            return c;
        }

        /* access modifiers changed from: 0000 */
        public int a(int i) {
            return i <= 3 ? R.layout.notification_template_big_media_narrow_custom : R.layout.notification_template_big_media_custom;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews remoteViews;
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            if (this.mBuilder.getHeadsUpContentView() != null) {
                remoteViews = this.mBuilder.getHeadsUpContentView();
            } else {
                remoteViews = this.mBuilder.getContentView();
            }
            if (remoteViews == null) {
                return null;
            }
            RemoteViews c = c();
            buildIntoRemoteViews(c, remoteViews);
            if (VERSION.SDK_INT >= 21) {
                a(c);
            }
            return c;
        }

        private void a(RemoteViews remoteViews) {
            int i;
            if (this.mBuilder.getColor() != 0) {
                i = this.mBuilder.getColor();
            } else {
                i = this.mBuilder.mContext.getResources().getColor(R.color.notification_material_background_media_default_color);
            }
            remoteViews.setInt(R.id.status_bar_latest_event_content, "setBackgroundColor", i);
        }
    }

    public static class MediaStyle extends Style {
        int[] a = null;
        Token b;
        boolean c;
        PendingIntent g;

        public static Token getMediaSession(Notification notification) {
            Bundle extras = android.support.v4.app.NotificationCompat.getExtras(notification);
            if (extras != null) {
                if (VERSION.SDK_INT >= 21) {
                    Parcelable parcelable = extras.getParcelable(android.support.v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
                    if (parcelable != null) {
                        return Token.fromToken(parcelable);
                    }
                } else {
                    IBinder binder = BundleCompat.getBinder(extras, android.support.v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
                    if (binder != null) {
                        Parcel obtain = Parcel.obtain();
                        obtain.writeStrongBinder(binder);
                        obtain.setDataPosition(0);
                        Token token = (Token) Token.CREATOR.createFromParcel(obtain);
                        obtain.recycle();
                        return token;
                    }
                }
            }
            return null;
        }

        public MediaStyle() {
        }

        public MediaStyle(Builder builder) {
            setBuilder(builder);
        }

        public MediaStyle setShowActionsInCompactView(int... iArr) {
            this.a = iArr;
            return this;
        }

        public MediaStyle setMediaSession(Token token) {
            this.b = token;
            return this;
        }

        public MediaStyle setShowCancelButton(boolean z) {
            if (VERSION.SDK_INT < 21) {
                this.c = z;
            }
            return this;
        }

        public MediaStyle setCancelButtonIntent(PendingIntent pendingIntent) {
            this.g = pendingIntent;
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 21) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(a(new android.app.Notification.MediaStyle()));
            } else if (this.c) {
                notificationBuilderWithBuilderAccessor.getBuilder().setOngoing(true);
            }
        }

        /* access modifiers changed from: 0000 */
        @RequiresApi(21)
        public android.app.Notification.MediaStyle a(android.app.Notification.MediaStyle mediaStyle) {
            if (this.a != null) {
                mediaStyle.setShowActionsInCompactView(this.a);
            }
            if (this.b != null) {
                mediaStyle.setMediaSession((MediaSession.Token) this.b.getToken());
            }
            return mediaStyle;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 21) {
                return null;
            }
            return b();
        }

        /* access modifiers changed from: 0000 */
        public RemoteViews b() {
            int i;
            RemoteViews applyStandardTemplate = applyStandardTemplate(false, a(), true);
            int size = this.mBuilder.mActions.size();
            if (this.a == null) {
                i = 0;
            } else {
                i = Math.min(this.a.length, 3);
            }
            applyStandardTemplate.removeAllViews(R.id.media_actions);
            if (i > 0) {
                for (int i2 = 0; i2 < i; i2++) {
                    if (i2 >= size) {
                        throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", new Object[]{Integer.valueOf(i2), Integer.valueOf(size - 1)}));
                    }
                    applyStandardTemplate.addView(R.id.media_actions, a((Action) this.mBuilder.mActions.get(this.a[i2])));
                }
            }
            if (this.c) {
                applyStandardTemplate.setViewVisibility(R.id.end_padder, 8);
                applyStandardTemplate.setViewVisibility(R.id.cancel_action, 0);
                applyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, this.g);
                applyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
            } else {
                applyStandardTemplate.setViewVisibility(R.id.end_padder, 0);
                applyStandardTemplate.setViewVisibility(R.id.cancel_action, 8);
            }
            return applyStandardTemplate;
        }

        private RemoteViews a(Action action) {
            boolean z = action.getActionIntent() == null;
            RemoteViews remoteViews = new RemoteViews(this.mBuilder.mContext.getPackageName(), R.layout.notification_media_action);
            remoteViews.setImageViewResource(R.id.action0, action.getIcon());
            if (!z) {
                remoteViews.setOnClickPendingIntent(R.id.action0, action.getActionIntent());
            }
            if (VERSION.SDK_INT >= 15) {
                remoteViews.setContentDescription(R.id.action0, action.getTitle());
            }
            return remoteViews;
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            return R.layout.notification_template_media;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 21) {
                return null;
            }
            return c();
        }

        /* access modifiers changed from: 0000 */
        public RemoteViews c() {
            int min = Math.min(this.mBuilder.mActions.size(), 5);
            RemoteViews applyStandardTemplate = applyStandardTemplate(false, a(min), false);
            applyStandardTemplate.removeAllViews(R.id.media_actions);
            if (min > 0) {
                for (int i = 0; i < min; i++) {
                    applyStandardTemplate.addView(R.id.media_actions, a((Action) this.mBuilder.mActions.get(i)));
                }
            }
            if (this.c) {
                applyStandardTemplate.setViewVisibility(R.id.cancel_action, 0);
                applyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
                applyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, this.g);
            } else {
                applyStandardTemplate.setViewVisibility(R.id.cancel_action, 8);
            }
            return applyStandardTemplate;
        }

        /* access modifiers changed from: 0000 */
        public int a(int i) {
            return i <= 3 ? R.layout.notification_template_big_media_narrow : R.layout.notification_template_big_media;
        }
    }

    private NotificationCompat() {
    }
}
