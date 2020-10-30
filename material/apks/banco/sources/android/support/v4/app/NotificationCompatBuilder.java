package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.RemoteInput;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.Style;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestrictTo({Scope.LIBRARY_GROUP})
class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
    private final Builder a;
    private final NotificationCompat.Builder b;
    private RemoteViews c;
    private RemoteViews d;
    private final List<Bundle> e = new ArrayList();
    private final Bundle f = new Bundle();
    private int g;
    private RemoteViews h;

    NotificationCompatBuilder(NotificationCompat.Builder builder) {
        this.b = builder;
        if (VERSION.SDK_INT >= 26) {
            this.a = new Builder(builder.mContext, builder.F);
        } else {
            this.a = new Builder(builder.mContext);
        }
        Notification notification = builder.K;
        this.a.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, builder.e).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(builder.a).setContentText(builder.b).setContentInfo(builder.g).setContentIntent(builder.c).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(builder.d, (notification.flags & 128) != 0).setLargeIcon(builder.f).setNumber(builder.h).setProgress(builder.o, builder.p, builder.q);
        if (VERSION.SDK_INT < 21) {
            this.a.setSound(notification.sound, notification.audioStreamType);
        }
        if (VERSION.SDK_INT >= 16) {
            this.a.setSubText(builder.m).setUsesChronometer(builder.k).setPriority(builder.i);
            Iterator it = builder.mActions.iterator();
            while (it.hasNext()) {
                a((Action) it.next());
            }
            if (builder.y != null) {
                this.f.putAll(builder.y);
            }
            if (VERSION.SDK_INT < 20) {
                if (builder.u) {
                    this.f.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true);
                }
                if (builder.r != null) {
                    this.f.putString(NotificationCompatExtras.EXTRA_GROUP_KEY, builder.r);
                    if (builder.s) {
                        this.f.putBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY, true);
                    } else {
                        this.f.putBoolean(NotificationManagerCompat.EXTRA_USE_SIDE_CHANNEL, true);
                    }
                }
                if (builder.t != null) {
                    this.f.putString(NotificationCompatExtras.EXTRA_SORT_KEY, builder.t);
                }
            }
            this.c = builder.C;
            this.d = builder.D;
        }
        if (VERSION.SDK_INT >= 19) {
            this.a.setShowWhen(builder.j);
            if (VERSION.SDK_INT < 21 && builder.mPeople != null && !builder.mPeople.isEmpty()) {
                this.f.putStringArray(NotificationCompat.EXTRA_PEOPLE, (String[]) builder.mPeople.toArray(new String[builder.mPeople.size()]));
            }
        }
        if (VERSION.SDK_INT >= 20) {
            this.a.setLocalOnly(builder.u).setGroup(builder.r).setGroupSummary(builder.s).setSortKey(builder.t);
            this.g = builder.J;
        }
        if (VERSION.SDK_INT >= 21) {
            this.a.setCategory(builder.x).setColor(builder.z).setVisibility(builder.A).setPublicVersion(builder.B).setSound(notification.sound, notification.audioAttributes);
            Iterator it2 = builder.mPeople.iterator();
            while (it2.hasNext()) {
                this.a.addPerson((String) it2.next());
            }
            this.h = builder.E;
        }
        if (VERSION.SDK_INT >= 24) {
            this.a.setExtras(builder.y).setRemoteInputHistory(builder.n);
            if (builder.C != null) {
                this.a.setCustomContentView(builder.C);
            }
            if (builder.D != null) {
                this.a.setCustomBigContentView(builder.D);
            }
            if (builder.E != null) {
                this.a.setCustomHeadsUpContentView(builder.E);
            }
        }
        if (VERSION.SDK_INT >= 26) {
            this.a.setBadgeIconType(builder.G).setShortcutId(builder.H).setTimeoutAfter(builder.I).setGroupAlertBehavior(builder.J);
            if (builder.w) {
                this.a.setColorized(builder.v);
            }
            if (!TextUtils.isEmpty(builder.F)) {
                this.a.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
            }
        }
    }

    public Builder getBuilder() {
        return this.a;
    }

    public Notification a() {
        Style style = this.b.l;
        if (style != null) {
            style.apply(this);
        }
        RemoteViews makeContentView = style != null ? style.makeContentView(this) : null;
        Notification b2 = b();
        if (makeContentView != null) {
            b2.contentView = makeContentView;
        } else if (this.b.C != null) {
            b2.contentView = this.b.C;
        }
        if (VERSION.SDK_INT >= 16 && style != null) {
            RemoteViews makeBigContentView = style.makeBigContentView(this);
            if (makeBigContentView != null) {
                b2.bigContentView = makeBigContentView;
            }
        }
        if (VERSION.SDK_INT >= 21 && style != null) {
            RemoteViews makeHeadsUpContentView = this.b.l.makeHeadsUpContentView(this);
            if (makeHeadsUpContentView != null) {
                b2.headsUpContentView = makeHeadsUpContentView;
            }
        }
        if (VERSION.SDK_INT >= 16 && style != null) {
            Bundle extras = NotificationCompat.getExtras(b2);
            if (extras != null) {
                style.addCompatExtras(extras);
            }
        }
        return b2;
    }

    private void a(Action action) {
        Bundle bundle;
        if (VERSION.SDK_INT >= 20) {
            Notification.Action.Builder builder = new Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
            if (action.getRemoteInputs() != null) {
                for (RemoteInput addRemoteInput : RemoteInput.a(action.getRemoteInputs())) {
                    builder.addRemoteInput(addRemoteInput);
                }
            }
            if (action.getExtras() != null) {
                bundle = new Bundle(action.getExtras());
            } else {
                bundle = new Bundle();
            }
            bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
            if (VERSION.SDK_INT >= 24) {
                builder.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
            }
            builder.addExtras(bundle);
            this.a.addAction(builder.build());
        } else if (VERSION.SDK_INT >= 16) {
            this.e.add(NotificationCompatJellybean.a(this.a, action));
        }
    }

    /* access modifiers changed from: protected */
    public Notification b() {
        if (VERSION.SDK_INT >= 26) {
            return this.a.build();
        }
        if (VERSION.SDK_INT >= 24) {
            Notification build = this.a.build();
            if (this.g != 0) {
                if (!(build.getGroup() == null || (build.flags & 512) == 0 || this.g != 2)) {
                    a(build);
                }
                if (build.getGroup() != null && (build.flags & 512) == 0 && this.g == 1) {
                    a(build);
                }
            }
            return build;
        } else if (VERSION.SDK_INT >= 21) {
            this.a.setExtras(this.f);
            Notification build2 = this.a.build();
            if (this.c != null) {
                build2.contentView = this.c;
            }
            if (this.d != null) {
                build2.bigContentView = this.d;
            }
            if (this.h != null) {
                build2.headsUpContentView = this.h;
            }
            if (this.g != 0) {
                if (!(build2.getGroup() == null || (build2.flags & 512) == 0 || this.g != 2)) {
                    a(build2);
                }
                if (build2.getGroup() != null && (build2.flags & 512) == 0 && this.g == 1) {
                    a(build2);
                }
            }
            return build2;
        } else if (VERSION.SDK_INT >= 20) {
            this.a.setExtras(this.f);
            Notification build3 = this.a.build();
            if (this.c != null) {
                build3.contentView = this.c;
            }
            if (this.d != null) {
                build3.bigContentView = this.d;
            }
            if (this.g != 0) {
                if (!(build3.getGroup() == null || (build3.flags & 512) == 0 || this.g != 2)) {
                    a(build3);
                }
                if (build3.getGroup() != null && (build3.flags & 512) == 0 && this.g == 1) {
                    a(build3);
                }
            }
            return build3;
        } else if (VERSION.SDK_INT >= 19) {
            SparseArray a2 = NotificationCompatJellybean.a(this.e);
            if (a2 != null) {
                this.f.putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, a2);
            }
            this.a.setExtras(this.f);
            Notification build4 = this.a.build();
            if (this.c != null) {
                build4.contentView = this.c;
            }
            if (this.d != null) {
                build4.bigContentView = this.d;
            }
            return build4;
        } else if (VERSION.SDK_INT < 16) {
            return this.a.getNotification();
        } else {
            Notification build5 = this.a.build();
            Bundle extras = NotificationCompat.getExtras(build5);
            Bundle bundle = new Bundle(this.f);
            for (String str : this.f.keySet()) {
                if (extras.containsKey(str)) {
                    bundle.remove(str);
                }
            }
            extras.putAll(bundle);
            SparseArray a3 = NotificationCompatJellybean.a(this.e);
            if (a3 != null) {
                NotificationCompat.getExtras(build5).putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, a3);
            }
            if (this.c != null) {
                build5.contentView = this.c;
            }
            if (this.d != null) {
                build5.bigContentView = this.d;
            }
            return build5;
        }
    }

    private void a(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -2;
        notification.defaults &= -3;
    }
}
