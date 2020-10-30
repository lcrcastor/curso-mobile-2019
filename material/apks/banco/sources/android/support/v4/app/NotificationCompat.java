package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.compat.R;
import android.support.v4.text.BidiFormatter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.i18n.TextBundle;

public class NotificationCompat {
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    public static class Action {
        final Bundle a;
        public PendingIntent actionIntent;
        private final RemoteInput[] b;
        private final RemoteInput[] c;
        private boolean d;
        public int icon;
        public CharSequence title;

        public static final class Builder {
            private final int a;
            private final CharSequence b;
            private final PendingIntent c;
            private boolean d;
            private final Bundle e;
            private ArrayList<RemoteInput> f;

            public Builder(int i, CharSequence charSequence, PendingIntent pendingIntent) {
                this(i, charSequence, pendingIntent, new Bundle(), null, true);
            }

            public Builder(Action action) {
                this(action.icon, action.title, action.actionIntent, new Bundle(action.a), action.getRemoteInputs(), action.getAllowGeneratedReplies());
            }

            private Builder(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, boolean z) {
                ArrayList<RemoteInput> arrayList;
                this.d = true;
                this.a = i;
                this.b = Builder.limitCharSequenceLength(charSequence);
                this.c = pendingIntent;
                this.e = bundle;
                if (remoteInputArr == null) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList<>(Arrays.asList(remoteInputArr));
                }
                this.f = arrayList;
                this.d = z;
            }

            public Builder addExtras(Bundle bundle) {
                if (bundle != null) {
                    this.e.putAll(bundle);
                }
                return this;
            }

            public Bundle getExtras() {
                return this.e;
            }

            public Builder addRemoteInput(RemoteInput remoteInput) {
                if (this.f == null) {
                    this.f = new ArrayList<>();
                }
                this.f.add(remoteInput);
                return this;
            }

            public Builder setAllowGeneratedReplies(boolean z) {
                this.d = z;
                return this;
            }

            public Builder extend(Extender extender) {
                extender.extend(this);
                return this;
            }

            public Action build() {
                RemoteInput[] remoteInputArr;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                if (this.f != null) {
                    Iterator it = this.f.iterator();
                    while (it.hasNext()) {
                        RemoteInput remoteInput = (RemoteInput) it.next();
                        if (remoteInput.isDataOnly()) {
                            arrayList.add(remoteInput);
                        } else {
                            arrayList2.add(remoteInput);
                        }
                    }
                }
                RemoteInput[] remoteInputArr2 = null;
                if (arrayList.isEmpty()) {
                    remoteInputArr = null;
                } else {
                    remoteInputArr = (RemoteInput[]) arrayList.toArray(new RemoteInput[arrayList.size()]);
                }
                if (!arrayList2.isEmpty()) {
                    remoteInputArr2 = (RemoteInput[]) arrayList2.toArray(new RemoteInput[arrayList2.size()]);
                }
                Action action = new Action(this.a, this.b, this.c, this.e, remoteInputArr2, remoteInputArr, this.d);
                return action;
            }
        }

        public interface Extender {
            Builder extend(Builder builder);
        }

        public static final class WearableExtender implements Extender {
            private int a = 1;
            private CharSequence b;
            private CharSequence c;
            private CharSequence d;

            public WearableExtender() {
            }

            public WearableExtender(Action action) {
                Bundle bundle = action.getExtras().getBundle("android.wearable.EXTENSIONS");
                if (bundle != null) {
                    this.a = bundle.getInt("flags", 1);
                    this.b = bundle.getCharSequence("inProgressLabel");
                    this.c = bundle.getCharSequence("confirmLabel");
                    this.d = bundle.getCharSequence("cancelLabel");
                }
            }

            public Builder extend(Builder builder) {
                Bundle bundle = new Bundle();
                if (this.a != 1) {
                    bundle.putInt("flags", this.a);
                }
                if (this.b != null) {
                    bundle.putCharSequence("inProgressLabel", this.b);
                }
                if (this.c != null) {
                    bundle.putCharSequence("confirmLabel", this.c);
                }
                if (this.d != null) {
                    bundle.putCharSequence("cancelLabel", this.d);
                }
                builder.getExtras().putBundle("android.wearable.EXTENSIONS", bundle);
                return builder;
            }

            public WearableExtender clone() {
                WearableExtender wearableExtender = new WearableExtender();
                wearableExtender.a = this.a;
                wearableExtender.b = this.b;
                wearableExtender.c = this.c;
                wearableExtender.d = this.d;
                return wearableExtender;
            }

            public WearableExtender setAvailableOffline(boolean z) {
                a(1, z);
                return this;
            }

            public boolean isAvailableOffline() {
                return (this.a & 1) != 0;
            }

            private void a(int i, boolean z) {
                if (z) {
                    this.a = i | this.a;
                    return;
                }
                this.a = (i ^ -1) & this.a;
            }

            public WearableExtender setInProgressLabel(CharSequence charSequence) {
                this.b = charSequence;
                return this;
            }

            public CharSequence getInProgressLabel() {
                return this.b;
            }

            public WearableExtender setConfirmLabel(CharSequence charSequence) {
                this.c = charSequence;
                return this;
            }

            public CharSequence getConfirmLabel() {
                return this.c;
            }

            public WearableExtender setCancelLabel(CharSequence charSequence) {
                this.d = charSequence;
                return this;
            }

            public CharSequence getCancelLabel() {
                return this.d;
            }

            public WearableExtender setHintLaunchesActivity(boolean z) {
                a(2, z);
                return this;
            }

            public boolean getHintLaunchesActivity() {
                return (this.a & 2) != 0;
            }

            public WearableExtender setHintDisplayActionInline(boolean z) {
                a(4, z);
                return this;
            }

            public boolean getHintDisplayActionInline() {
                return (this.a & 4) != 0;
            }
        }

        public Action(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this(i, charSequence, pendingIntent, new Bundle(), null, null, true);
        }

        Action(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2, boolean z) {
            this.icon = i;
            this.title = Builder.limitCharSequenceLength(charSequence);
            this.actionIntent = pendingIntent;
            if (bundle == null) {
                bundle = new Bundle();
            }
            this.a = bundle;
            this.b = remoteInputArr;
            this.c = remoteInputArr2;
            this.d = z;
        }

        public int getIcon() {
            return this.icon;
        }

        public CharSequence getTitle() {
            return this.title;
        }

        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        public Bundle getExtras() {
            return this.a;
        }

        public boolean getAllowGeneratedReplies() {
            return this.d;
        }

        public RemoteInput[] getRemoteInputs() {
            return this.b;
        }

        public RemoteInput[] getDataOnlyRemoteInputs() {
            return this.c;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BadgeIconType {
    }

    public static class BigPictureStyle extends Style {
        private Bitmap a;
        private Bitmap b;
        private boolean c;

        public BigPictureStyle() {
        }

        public BigPictureStyle(Builder builder) {
            setBuilder(builder);
        }

        public BigPictureStyle setBigContentTitle(CharSequence charSequence) {
            this.d = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence charSequence) {
            this.e = Builder.limitCharSequenceLength(charSequence);
            this.f = true;
            return this;
        }

        public BigPictureStyle bigPicture(Bitmap bitmap) {
            this.a = bitmap;
            return this;
        }

        public BigPictureStyle bigLargeIcon(Bitmap bitmap) {
            this.b = bitmap;
            this.c = true;
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 16) {
                android.app.Notification.BigPictureStyle bigPicture = new android.app.Notification.BigPictureStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.d).bigPicture(this.a);
                if (this.c) {
                    bigPicture.bigLargeIcon(this.b);
                }
                if (this.f) {
                    bigPicture.setSummaryText(this.e);
                }
            }
        }
    }

    public static class BigTextStyle extends Style {
        private CharSequence a;

        public BigTextStyle() {
        }

        public BigTextStyle(Builder builder) {
            setBuilder(builder);
        }

        public BigTextStyle setBigContentTitle(CharSequence charSequence) {
            this.d = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence charSequence) {
            this.e = Builder.limitCharSequenceLength(charSequence);
            this.f = true;
            return this;
        }

        public BigTextStyle bigText(CharSequence charSequence) {
            this.a = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 16) {
                android.app.Notification.BigTextStyle bigText = new android.app.Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.d).bigText(this.a);
                if (this.f) {
                    bigText.setSummaryText(this.e);
                }
            }
        }
    }

    public static class Builder {
        int A;
        Notification B;
        RemoteViews C;
        RemoteViews D;
        RemoteViews E;
        String F;
        int G;
        String H;
        long I;
        int J;
        Notification K;
        CharSequence a;
        CharSequence b;
        PendingIntent c;
        PendingIntent d;
        RemoteViews e;
        Bitmap f;
        CharSequence g;
        int h;
        int i;
        boolean j;
        boolean k;
        Style l;
        CharSequence m;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public ArrayList<Action> mActions;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Context mContext;
        @Deprecated
        public ArrayList<String> mPeople;
        CharSequence[] n;
        int o;
        int p;
        boolean q;
        String r;
        boolean s;
        String t;
        boolean u;
        boolean v;
        boolean w;
        String x;
        Bundle y;
        int z;

        public Builder(@NonNull Context context, @NonNull String str) {
            this.mActions = new ArrayList<>();
            this.j = true;
            this.u = false;
            this.z = 0;
            this.A = 0;
            this.G = 0;
            this.J = 0;
            this.K = new Notification();
            this.mContext = context;
            this.F = str;
            this.K.when = System.currentTimeMillis();
            this.K.audioStreamType = -1;
            this.i = 0;
            this.mPeople = new ArrayList<>();
        }

        @Deprecated
        public Builder(Context context) {
            this(context, null);
        }

        public Builder setWhen(long j2) {
            this.K.when = j2;
            return this;
        }

        public Builder setShowWhen(boolean z2) {
            this.j = z2;
            return this;
        }

        public Builder setUsesChronometer(boolean z2) {
            this.k = z2;
            return this;
        }

        public Builder setSmallIcon(int i2) {
            this.K.icon = i2;
            return this;
        }

        public Builder setSmallIcon(int i2, int i3) {
            this.K.icon = i2;
            this.K.iconLevel = i3;
            return this;
        }

        public Builder setContentTitle(CharSequence charSequence) {
            this.a = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setContentText(CharSequence charSequence) {
            this.b = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setSubText(CharSequence charSequence) {
            this.m = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setRemoteInputHistory(CharSequence[] charSequenceArr) {
            this.n = charSequenceArr;
            return this;
        }

        public Builder setNumber(int i2) {
            this.h = i2;
            return this;
        }

        public Builder setContentInfo(CharSequence charSequence) {
            this.g = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setProgress(int i2, int i3, boolean z2) {
            this.o = i2;
            this.p = i3;
            this.q = z2;
            return this;
        }

        public Builder setContent(RemoteViews remoteViews) {
            this.K.contentView = remoteViews;
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingIntent) {
            this.c = pendingIntent;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent pendingIntent) {
            this.K.deleteIntent = pendingIntent;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent pendingIntent, boolean z2) {
            this.d = pendingIntent;
            a(128, z2);
            return this;
        }

        public Builder setTicker(CharSequence charSequence) {
            this.K.tickerText = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setTicker(CharSequence charSequence, RemoteViews remoteViews) {
            this.K.tickerText = limitCharSequenceLength(charSequence);
            this.e = remoteViews;
            return this;
        }

        public Builder setLargeIcon(Bitmap bitmap) {
            this.f = bitmap;
            return this;
        }

        public Builder setSound(Uri uri) {
            this.K.sound = uri;
            this.K.audioStreamType = -1;
            if (VERSION.SDK_INT >= 21) {
                this.K.audioAttributes = new android.media.AudioAttributes.Builder().setContentType(4).setUsage(5).build();
            }
            return this;
        }

        public Builder setSound(Uri uri, int i2) {
            this.K.sound = uri;
            this.K.audioStreamType = i2;
            if (VERSION.SDK_INT >= 21) {
                this.K.audioAttributes = new android.media.AudioAttributes.Builder().setContentType(4).setLegacyStreamType(i2).build();
            }
            return this;
        }

        public Builder setVibrate(long[] jArr) {
            this.K.vibrate = jArr;
            return this;
        }

        public Builder setLights(@ColorInt int i2, int i3, int i4) {
            this.K.ledARGB = i2;
            this.K.ledOnMS = i3;
            this.K.ledOffMS = i4;
            this.K.flags = ((this.K.ledOnMS == 0 || this.K.ledOffMS == 0) ? 0 : 1) | (this.K.flags & -2);
            return this;
        }

        public Builder setOngoing(boolean z2) {
            a(2, z2);
            return this;
        }

        public Builder setColorized(boolean z2) {
            this.v = z2;
            this.w = true;
            return this;
        }

        public Builder setOnlyAlertOnce(boolean z2) {
            a(8, z2);
            return this;
        }

        public Builder setAutoCancel(boolean z2) {
            a(16, z2);
            return this;
        }

        public Builder setLocalOnly(boolean z2) {
            this.u = z2;
            return this;
        }

        public Builder setCategory(String str) {
            this.x = str;
            return this;
        }

        public Builder setDefaults(int i2) {
            this.K.defaults = i2;
            if ((i2 & 4) != 0) {
                this.K.flags |= 1;
            }
            return this;
        }

        private void a(int i2, boolean z2) {
            if (z2) {
                Notification notification = this.K;
                notification.flags = i2 | notification.flags;
                return;
            }
            Notification notification2 = this.K;
            notification2.flags = (i2 ^ -1) & notification2.flags;
        }

        public Builder setPriority(int i2) {
            this.i = i2;
            return this;
        }

        public Builder addPerson(String str) {
            this.mPeople.add(str);
            return this;
        }

        public Builder setGroup(String str) {
            this.r = str;
            return this;
        }

        public Builder setGroupSummary(boolean z2) {
            this.s = z2;
            return this;
        }

        public Builder setSortKey(String str) {
            this.t = str;
            return this;
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                if (this.y == null) {
                    this.y = new Bundle(bundle);
                } else {
                    this.y.putAll(bundle);
                }
            }
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.y = bundle;
            return this;
        }

        public Bundle getExtras() {
            if (this.y == null) {
                this.y = new Bundle();
            }
            return this.y;
        }

        public Builder addAction(int i2, CharSequence charSequence, PendingIntent pendingIntent) {
            this.mActions.add(new Action(i2, charSequence, pendingIntent));
            return this;
        }

        public Builder addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public Builder setStyle(Style style) {
            if (this.l != style) {
                this.l = style;
                if (this.l != null) {
                    this.l.setBuilder(this);
                }
            }
            return this;
        }

        public Builder setColor(@ColorInt int i2) {
            this.z = i2;
            return this;
        }

        public Builder setVisibility(int i2) {
            this.A = i2;
            return this;
        }

        public Builder setPublicVersion(Notification notification) {
            this.B = notification;
            return this;
        }

        public Builder setCustomContentView(RemoteViews remoteViews) {
            this.C = remoteViews;
            return this;
        }

        public Builder setCustomBigContentView(RemoteViews remoteViews) {
            this.D = remoteViews;
            return this;
        }

        public Builder setCustomHeadsUpContentView(RemoteViews remoteViews) {
            this.E = remoteViews;
            return this;
        }

        public Builder setChannelId(@NonNull String str) {
            this.F = str;
            return this;
        }

        public Builder setTimeoutAfter(long j2) {
            this.I = j2;
            return this;
        }

        public Builder setShortcutId(String str) {
            this.H = str;
            return this;
        }

        public Builder setBadgeIconType(int i2) {
            this.G = i2;
            return this;
        }

        public Builder setGroupAlertBehavior(int i2) {
            this.J = i2;
            return this;
        }

        public Builder extend(Extender extender) {
            extender.extend(this);
            return this;
        }

        @Deprecated
        public Notification getNotification() {
            return build();
        }

        public Notification build() {
            return new NotificationCompatBuilder(this).a();
        }

        protected static CharSequence limitCharSequenceLength(CharSequence charSequence) {
            if (charSequence == null) {
                return charSequence;
            }
            if (charSequence.length() > 5120) {
                charSequence = charSequence.subSequence(0, 5120);
            }
            return charSequence;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getContentView() {
            return this.C;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getBigContentView() {
            return this.D;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getHeadsUpContentView() {
            return this.E;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public long getWhenIfShowing() {
            if (this.j) {
                return this.K.when;
            }
            return 0;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getPriority() {
            return this.i;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getColor() {
            return this.z;
        }
    }

    public static final class CarExtender implements Extender {
        private Bitmap a;
        private UnreadConversation b;
        private int c = 0;

        public static class UnreadConversation {
            private final String[] a;
            private final RemoteInput b;
            private final PendingIntent c;
            private final PendingIntent d;
            private final String[] e;
            private final long f;

            public static class Builder {
                private final List<String> a = new ArrayList();
                private final String b;
                private RemoteInput c;
                private PendingIntent d;
                private PendingIntent e;
                private long f;

                public Builder(String str) {
                    this.b = str;
                }

                public Builder addMessage(String str) {
                    this.a.add(str);
                    return this;
                }

                public Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
                    this.c = remoteInput;
                    this.e = pendingIntent;
                    return this;
                }

                public Builder setReadPendingIntent(PendingIntent pendingIntent) {
                    this.d = pendingIntent;
                    return this;
                }

                public Builder setLatestTimestamp(long j) {
                    this.f = j;
                    return this;
                }

                public UnreadConversation build() {
                    UnreadConversation unreadConversation = new UnreadConversation((String[]) this.a.toArray(new String[this.a.size()]), this.c, this.e, this.d, new String[]{this.b}, this.f);
                    return unreadConversation;
                }
            }

            UnreadConversation(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j) {
                this.a = strArr;
                this.b = remoteInput;
                this.d = pendingIntent2;
                this.c = pendingIntent;
                this.e = strArr2;
                this.f = j;
            }

            public String[] getMessages() {
                return this.a;
            }

            public RemoteInput getRemoteInput() {
                return this.b;
            }

            public PendingIntent getReplyPendingIntent() {
                return this.c;
            }

            public PendingIntent getReadPendingIntent() {
                return this.d;
            }

            public String[] getParticipants() {
                return this.e;
            }

            public String getParticipant() {
                if (this.e.length > 0) {
                    return this.e[0];
                }
                return null;
            }

            public long getLatestTimestamp() {
                return this.f;
            }
        }

        public CarExtender() {
        }

        public CarExtender(Notification notification) {
            Bundle bundle;
            if (VERSION.SDK_INT >= 21) {
                if (NotificationCompat.getExtras(notification) == null) {
                    bundle = null;
                } else {
                    bundle = NotificationCompat.getExtras(notification).getBundle("android.car.EXTENSIONS");
                }
                if (bundle != null) {
                    this.a = (Bitmap) bundle.getParcelable("large_icon");
                    this.c = bundle.getInt("app_color", 0);
                    this.b = a(bundle.getBundle("car_conversation"));
                }
            }
        }

        @RequiresApi(21)
        private static UnreadConversation a(@Nullable Bundle bundle) {
            String[] strArr;
            Bundle bundle2 = bundle;
            RemoteInput remoteInput = null;
            if (bundle2 == null) {
                return null;
            }
            Parcelable[] parcelableArray = bundle2.getParcelableArray("messages");
            if (parcelableArray != null) {
                String[] strArr2 = new String[parcelableArray.length];
                boolean z = false;
                int i = 0;
                while (true) {
                    if (i >= strArr2.length) {
                        z = true;
                        break;
                    } else if (!(parcelableArray[i] instanceof Bundle)) {
                        break;
                    } else {
                        strArr2[i] = ((Bundle) parcelableArray[i]).getString(TextBundle.TEXT_ENTRY);
                        if (strArr2[i] == null) {
                            break;
                        }
                        i++;
                    }
                }
                if (!z) {
                    return null;
                }
                strArr = strArr2;
            } else {
                strArr = null;
            }
            PendingIntent pendingIntent = (PendingIntent) bundle2.getParcelable("on_read");
            PendingIntent pendingIntent2 = (PendingIntent) bundle2.getParcelable("on_reply");
            RemoteInput remoteInput2 = (RemoteInput) bundle2.getParcelable("remote_input");
            String[] stringArray = bundle2.getStringArray("participants");
            if (stringArray == null || stringArray.length != 1) {
                return null;
            }
            if (remoteInput2 != null) {
                remoteInput = new RemoteInput(remoteInput2.getResultKey(), remoteInput2.getLabel(), remoteInput2.getChoices(), remoteInput2.getAllowFreeFormInput(), remoteInput2.getExtras(), null);
            }
            UnreadConversation unreadConversation = new UnreadConversation(strArr, remoteInput, pendingIntent2, pendingIntent, stringArray, bundle2.getLong("timestamp"));
            return unreadConversation;
        }

        @RequiresApi(21)
        private static Bundle a(@NonNull UnreadConversation unreadConversation) {
            Bundle bundle = new Bundle();
            String str = (unreadConversation.getParticipants() == null || unreadConversation.getParticipants().length <= 1) ? null : unreadConversation.getParticipants()[0];
            Parcelable[] parcelableArr = new Parcelable[unreadConversation.getMessages().length];
            for (int i = 0; i < parcelableArr.length; i++) {
                Bundle bundle2 = new Bundle();
                bundle2.putString(TextBundle.TEXT_ENTRY, unreadConversation.getMessages()[i]);
                bundle2.putString("author", str);
                parcelableArr[i] = bundle2;
            }
            bundle.putParcelableArray("messages", parcelableArr);
            RemoteInput remoteInput = unreadConversation.getRemoteInput();
            if (remoteInput != null) {
                bundle.putParcelable("remote_input", new android.app.RemoteInput.Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras()).build());
            }
            bundle.putParcelable("on_reply", unreadConversation.getReplyPendingIntent());
            bundle.putParcelable("on_read", unreadConversation.getReadPendingIntent());
            bundle.putStringArray("participants", unreadConversation.getParticipants());
            bundle.putLong("timestamp", unreadConversation.getLatestTimestamp());
            return bundle;
        }

        public Builder extend(Builder builder) {
            if (VERSION.SDK_INT < 21) {
                return builder;
            }
            Bundle bundle = new Bundle();
            if (this.a != null) {
                bundle.putParcelable("large_icon", this.a);
            }
            if (this.c != 0) {
                bundle.putInt("app_color", this.c);
            }
            if (this.b != null) {
                bundle.putBundle("car_conversation", a(this.b));
            }
            builder.getExtras().putBundle("android.car.EXTENSIONS", bundle);
            return builder;
        }

        public CarExtender setColor(@ColorInt int i) {
            this.c = i;
            return this;
        }

        @ColorInt
        public int getColor() {
            return this.c;
        }

        public CarExtender setLargeIcon(Bitmap bitmap) {
            this.a = bitmap;
            return this;
        }

        public Bitmap getLargeIcon() {
            return this.a;
        }

        public CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
            this.b = unreadConversation;
            return this;
        }

        public UnreadConversation getUnreadConversation() {
            return this.b;
        }
    }

    public static class DecoratedCustomViewStyle extends Style {
        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(new android.app.Notification.DecoratedCustomViewStyle());
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT < 24 && this.mBuilder.getContentView() != null) {
                return a(this.mBuilder.getContentView(), false);
            }
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews bigContentView = this.mBuilder.getBigContentView();
            if (bigContentView == null) {
                bigContentView = this.mBuilder.getContentView();
            }
            if (bigContentView == null) {
                return null;
            }
            return a(bigContentView, true);
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews remoteViews;
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews headsUpContentView = this.mBuilder.getHeadsUpContentView();
            if (headsUpContentView != null) {
                remoteViews = headsUpContentView;
            } else {
                remoteViews = this.mBuilder.getContentView();
            }
            if (headsUpContentView == null) {
                return null;
            }
            return a(remoteViews, true);
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0041  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private android.widget.RemoteViews a(android.widget.RemoteViews r7, boolean r8) {
            /*
                r6 = this;
                int r0 = android.support.compat.R.layout.notification_template_custom_big
                r1 = 1
                r2 = 0
                android.widget.RemoteViews r0 = r6.applyStandardTemplate(r1, r0, r2)
                int r3 = android.support.compat.R.id.actions
                r0.removeAllViews(r3)
                if (r8 == 0) goto L_0x003d
                android.support.v4.app.NotificationCompat$Builder r8 = r6.mBuilder
                java.util.ArrayList<android.support.v4.app.NotificationCompat$Action> r8 = r8.mActions
                if (r8 == 0) goto L_0x003d
                android.support.v4.app.NotificationCompat$Builder r8 = r6.mBuilder
                java.util.ArrayList<android.support.v4.app.NotificationCompat$Action> r8 = r8.mActions
                int r8 = r8.size()
                r3 = 3
                int r8 = java.lang.Math.min(r8, r3)
                if (r8 <= 0) goto L_0x003d
                r3 = 0
            L_0x0025:
                if (r3 >= r8) goto L_0x003e
                android.support.v4.app.NotificationCompat$Builder r4 = r6.mBuilder
                java.util.ArrayList<android.support.v4.app.NotificationCompat$Action> r4 = r4.mActions
                java.lang.Object r4 = r4.get(r3)
                android.support.v4.app.NotificationCompat$Action r4 = (android.support.v4.app.NotificationCompat.Action) r4
                android.widget.RemoteViews r4 = r6.a(r4)
                int r5 = android.support.compat.R.id.actions
                r0.addView(r5, r4)
                int r3 = r3 + 1
                goto L_0x0025
            L_0x003d:
                r1 = 0
            L_0x003e:
                if (r1 == 0) goto L_0x0041
                goto L_0x0043
            L_0x0041:
                r2 = 8
            L_0x0043:
                int r8 = android.support.compat.R.id.actions
                r0.setViewVisibility(r8, r2)
                int r8 = android.support.compat.R.id.action_divider
                r0.setViewVisibility(r8, r2)
                r6.buildIntoRemoteViews(r0, r7)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompat.DecoratedCustomViewStyle.a(android.widget.RemoteViews, boolean):android.widget.RemoteViews");
        }

        private RemoteViews a(Action action) {
            boolean z = action.actionIntent == null;
            RemoteViews remoteViews = new RemoteViews(this.mBuilder.mContext.getPackageName(), z ? R.layout.notification_action_tombstone : R.layout.notification_action);
            remoteViews.setImageViewBitmap(R.id.action_image, createColoredBitmap(action.getIcon(), this.mBuilder.mContext.getResources().getColor(R.color.notification_action_color_filter)));
            remoteViews.setTextViewText(R.id.action_text, action.title);
            if (!z) {
                remoteViews.setOnClickPendingIntent(R.id.action_container, action.actionIntent);
            }
            if (VERSION.SDK_INT >= 15) {
                remoteViews.setContentDescription(R.id.action_container, action.title);
            }
            return remoteViews;
        }
    }

    public interface Extender {
        Builder extend(Builder builder);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupAlertBehavior {
    }

    public static class InboxStyle extends Style {
        private ArrayList<CharSequence> a = new ArrayList<>();

        public InboxStyle() {
        }

        public InboxStyle(Builder builder) {
            setBuilder(builder);
        }

        public InboxStyle setBigContentTitle(CharSequence charSequence) {
            this.d = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public InboxStyle setSummaryText(CharSequence charSequence) {
            this.e = Builder.limitCharSequenceLength(charSequence);
            this.f = true;
            return this;
        }

        public InboxStyle addLine(CharSequence charSequence) {
            this.a.add(Builder.limitCharSequenceLength(charSequence));
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 16) {
                android.app.Notification.InboxStyle bigContentTitle = new android.app.Notification.InboxStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.d);
                if (this.f) {
                    bigContentTitle.setSummaryText(this.e);
                }
                Iterator it = this.a.iterator();
                while (it.hasNext()) {
                    bigContentTitle.addLine((CharSequence) it.next());
                }
            }
        }
    }

    public static class MessagingStyle extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        CharSequence a;
        CharSequence b;
        List<Message> c = new ArrayList();

        public static final class Message {
            private final CharSequence a;
            private final long b;
            private final CharSequence c;
            private Bundle d = new Bundle();
            private String e;
            private Uri f;

            public Message(CharSequence charSequence, long j, CharSequence charSequence2) {
                this.a = charSequence;
                this.b = j;
                this.c = charSequence2;
            }

            public Message setData(String str, Uri uri) {
                this.e = str;
                this.f = uri;
                return this;
            }

            public CharSequence getText() {
                return this.a;
            }

            public long getTimestamp() {
                return this.b;
            }

            public Bundle getExtras() {
                return this.d;
            }

            public CharSequence getSender() {
                return this.c;
            }

            public String getDataMimeType() {
                return this.e;
            }

            public Uri getDataUri() {
                return this.f;
            }

            private Bundle a() {
                Bundle bundle = new Bundle();
                if (this.a != null) {
                    bundle.putCharSequence(TextBundle.TEXT_ENTRY, this.a);
                }
                bundle.putLong("time", this.b);
                if (this.c != null) {
                    bundle.putCharSequence("sender", this.c);
                }
                if (this.e != null) {
                    bundle.putString("type", this.e);
                }
                if (this.f != null) {
                    bundle.putParcelable("uri", this.f);
                }
                if (this.d != null) {
                    bundle.putBundle("extras", this.d);
                }
                return bundle;
            }

            static Bundle[] a(List<Message> list) {
                Bundle[] bundleArr = new Bundle[list.size()];
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    bundleArr[i] = ((Message) list.get(i)).a();
                }
                return bundleArr;
            }

            static List<Message> a(Parcelable[] parcelableArr) {
                ArrayList arrayList = new ArrayList(parcelableArr.length);
                for (int i = 0; i < parcelableArr.length; i++) {
                    if (parcelableArr[i] instanceof Bundle) {
                        Message a2 = a(parcelableArr[i]);
                        if (a2 != null) {
                            arrayList.add(a2);
                        }
                    }
                }
                return arrayList;
            }

            static Message a(Bundle bundle) {
                try {
                    if (bundle.containsKey(TextBundle.TEXT_ENTRY)) {
                        if (bundle.containsKey("time")) {
                            Message message = new Message(bundle.getCharSequence(TextBundle.TEXT_ENTRY), bundle.getLong("time"), bundle.getCharSequence("sender"));
                            if (bundle.containsKey("type") && bundle.containsKey("uri")) {
                                message.setData(bundle.getString("type"), (Uri) bundle.getParcelable("uri"));
                            }
                            if (bundle.containsKey("extras")) {
                                message.getExtras().putAll(bundle.getBundle("extras"));
                            }
                            return message;
                        }
                    }
                    return null;
                } catch (ClassCastException unused) {
                    return null;
                }
            }
        }

        MessagingStyle() {
        }

        public MessagingStyle(@NonNull CharSequence charSequence) {
            this.a = charSequence;
        }

        public CharSequence getUserDisplayName() {
            return this.a;
        }

        public MessagingStyle setConversationTitle(CharSequence charSequence) {
            this.b = charSequence;
            return this;
        }

        public CharSequence getConversationTitle() {
            return this.b;
        }

        public MessagingStyle addMessage(CharSequence charSequence, long j, CharSequence charSequence2) {
            this.c.add(new Message(charSequence, j, charSequence2));
            if (this.c.size() > 25) {
                this.c.remove(0);
            }
            return this;
        }

        public MessagingStyle addMessage(Message message) {
            this.c.add(message);
            if (this.c.size() > 25) {
                this.c.remove(0);
            }
            return this;
        }

        public List<Message> getMessages() {
            return this.c;
        }

        public static MessagingStyle extractMessagingStyleFromNotification(Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            if (extras != null && !extras.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)) {
                return null;
            }
            try {
                MessagingStyle messagingStyle = new MessagingStyle();
                messagingStyle.restoreFromCompatExtras(extras);
                return messagingStyle;
            } catch (ClassCastException unused) {
                return null;
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            CharSequence charSequence;
            if (VERSION.SDK_INT >= 24) {
                android.app.Notification.MessagingStyle conversationTitle = new android.app.Notification.MessagingStyle(this.a).setConversationTitle(this.b);
                for (Message message : this.c) {
                    android.app.Notification.MessagingStyle.Message message2 = new android.app.Notification.MessagingStyle.Message(message.getText(), message.getTimestamp(), message.getSender());
                    if (message.getDataMimeType() != null) {
                        message2.setData(message.getDataMimeType(), message.getDataUri());
                    }
                    conversationTitle.addMessage(message2);
                }
                conversationTitle.setBuilder(notificationBuilderWithBuilderAccessor.getBuilder());
                return;
            }
            Message a2 = a();
            if (this.b != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(this.b);
            } else if (a2 != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(a2.getSender());
            }
            if (a2 != null) {
                android.app.Notification.Builder builder = notificationBuilderWithBuilderAccessor.getBuilder();
                if (this.b != null) {
                    charSequence = a(a2);
                } else {
                    charSequence = a2.getText();
                }
                builder.setContentText(charSequence);
            }
            if (VERSION.SDK_INT >= 16) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                boolean z = this.b != null || b();
                for (int size = this.c.size() - 1; size >= 0; size--) {
                    Message message3 = (Message) this.c.get(size);
                    CharSequence a3 = z ? a(message3) : message3.getText();
                    if (size != this.c.size() - 1) {
                        spannableStringBuilder.insert(0, "\n");
                    }
                    spannableStringBuilder.insert(0, a3);
                }
                new android.app.Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(null).bigText(spannableStringBuilder);
            }
        }

        @Nullable
        private Message a() {
            for (int size = this.c.size() - 1; size >= 0; size--) {
                Message message = (Message) this.c.get(size);
                if (!TextUtils.isEmpty(message.getSender())) {
                    return message;
                }
            }
            if (!this.c.isEmpty()) {
                return (Message) this.c.get(this.c.size() - 1);
            }
            return null;
        }

        private boolean b() {
            for (int size = this.c.size() - 1; size >= 0; size--) {
                if (((Message) this.c.get(size)).getSender() == null) {
                    return true;
                }
            }
            return false;
        }

        private CharSequence a(Message message) {
            BidiFormatter instance = BidiFormatter.getInstance();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            boolean z = VERSION.SDK_INT >= 21;
            int i = z ? ViewCompat.MEASURED_STATE_MASK : -1;
            CharSequence sender = message.getSender();
            if (TextUtils.isEmpty(message.getSender())) {
                sender = this.a == null ? "" : this.a;
                if (z && this.mBuilder.getColor() != 0) {
                    i = this.mBuilder.getColor();
                }
            }
            CharSequence unicodeWrap = instance.unicodeWrap(sender);
            spannableStringBuilder.append(unicodeWrap);
            spannableStringBuilder.setSpan(a(i), spannableStringBuilder.length() - unicodeWrap.length(), spannableStringBuilder.length(), 33);
            spannableStringBuilder.append("  ").append(instance.unicodeWrap(message.getText() == null ? "" : message.getText()));
            return spannableStringBuilder;
        }

        @NonNull
        private TextAppearanceSpan a(int i) {
            TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(i), null);
            return textAppearanceSpan;
        }

        public void addCompatExtras(Bundle bundle) {
            super.addCompatExtras(bundle);
            if (this.a != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.a);
            }
            if (this.b != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.b);
            }
            if (!this.c.isEmpty()) {
                bundle.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, Message.a(this.c));
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public void restoreFromCompatExtras(Bundle bundle) {
            this.c.clear();
            this.a = bundle.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
            this.b = bundle.getString(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            Parcelable[] parcelableArray = bundle.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
            if (parcelableArray != null) {
                this.c = Message.a(parcelableArray);
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationVisibility {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamType {
    }

    public static abstract class Style {
        CharSequence d;
        CharSequence e;
        boolean f = false;
        @RestrictTo({Scope.LIBRARY_GROUP})
        protected Builder mBuilder;

        private static float a(float f2, float f3, float f4) {
            return f2 < f3 ? f3 : f2 > f4 ? f4 : f2;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void addCompatExtras(Bundle bundle) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public void restoreFromCompatExtras(Bundle bundle) {
        }

        public void setBuilder(Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                if (this.mBuilder != null) {
                    this.mBuilder.setStyle(this);
                }
            }
        }

        public Notification build() {
            if (this.mBuilder != null) {
                return this.mBuilder.build();
            }
            return null;
        }

        /* JADX WARNING: Removed duplicated region for block: B:63:0x01a9  */
        /* JADX WARNING: Removed duplicated region for block: B:64:0x01b9  */
        /* JADX WARNING: Removed duplicated region for block: B:70:0x01c7  */
        /* JADX WARNING: Removed duplicated region for block: B:74:0x01e9  */
        /* JADX WARNING: Removed duplicated region for block: B:80:0x022c  */
        /* JADX WARNING: Removed duplicated region for block: B:83:0x0231  */
        /* JADX WARNING: Removed duplicated region for block: B:84:0x0233  */
        /* JADX WARNING: Removed duplicated region for block: B:87:0x023d  */
        @android.support.annotation.RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.widget.RemoteViews applyStandardTemplate(boolean r18, int r19, boolean r20) {
            /*
                r17 = this;
                r0 = r17
                android.support.v4.app.NotificationCompat$Builder r2 = r0.mBuilder
                android.content.Context r2 = r2.mContext
                android.content.res.Resources r2 = r2.getResources()
                android.widget.RemoteViews r9 = new android.widget.RemoteViews
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                android.content.Context r3 = r3.mContext
                java.lang.String r3 = r3.getPackageName()
                r4 = r19
                r9.<init>(r3, r4)
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                int r3 = r3.getPriority()
                r4 = -1
                r10 = 1
                r11 = 0
                if (r3 >= r4) goto L_0x0026
                r3 = 1
                goto L_0x0027
            L_0x0026:
                r3 = 0
            L_0x0027:
                int r5 = android.os.Build.VERSION.SDK_INT
                r6 = 21
                r12 = 16
                if (r5 < r12) goto L_0x005a
                int r5 = android.os.Build.VERSION.SDK_INT
                if (r5 >= r6) goto L_0x005a
                if (r3 == 0) goto L_0x0048
                int r3 = android.support.compat.R.id.notification_background
                java.lang.String r5 = "setBackgroundResource"
                int r7 = android.support.compat.R.drawable.notification_bg_low
                r9.setInt(r3, r5, r7)
                int r3 = android.support.compat.R.id.icon
                java.lang.String r5 = "setBackgroundResource"
                int r7 = android.support.compat.R.drawable.notification_template_icon_low_bg
                r9.setInt(r3, r5, r7)
                goto L_0x005a
            L_0x0048:
                int r3 = android.support.compat.R.id.notification_background
                java.lang.String r5 = "setBackgroundResource"
                int r7 = android.support.compat.R.drawable.notification_bg
                r9.setInt(r3, r5, r7)
                int r3 = android.support.compat.R.id.icon
                java.lang.String r5 = "setBackgroundResource"
                int r7 = android.support.compat.R.drawable.notification_template_icon_bg
                r9.setInt(r3, r5, r7)
            L_0x005a:
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                android.graphics.Bitmap r3 = r3.f
                r13 = 8
                if (r3 == 0) goto L_0x00c3
                int r3 = android.os.Build.VERSION.SDK_INT
                if (r3 < r12) goto L_0x0075
                int r3 = android.support.compat.R.id.icon
                r9.setViewVisibility(r3, r11)
                int r3 = android.support.compat.R.id.icon
                android.support.v4.app.NotificationCompat$Builder r5 = r0.mBuilder
                android.graphics.Bitmap r5 = r5.f
                r9.setImageViewBitmap(r3, r5)
                goto L_0x007a
            L_0x0075:
                int r3 = android.support.compat.R.id.icon
                r9.setViewVisibility(r3, r13)
            L_0x007a:
                if (r18 == 0) goto L_0x010e
                android.support.v4.app.NotificationCompat$Builder r1 = r0.mBuilder
                android.app.Notification r1 = r1.K
                int r1 = r1.icon
                if (r1 == 0) goto L_0x010e
                int r1 = android.support.compat.R.dimen.notification_right_icon_size
                int r1 = r2.getDimensionPixelSize(r1)
                int r3 = android.support.compat.R.dimen.notification_small_icon_background_padding
                int r3 = r2.getDimensionPixelSize(r3)
                int r3 = r3 * 2
                int r3 = r1 - r3
                int r5 = android.os.Build.VERSION.SDK_INT
                if (r5 < r6) goto L_0x00ae
                android.support.v4.app.NotificationCompat$Builder r4 = r0.mBuilder
                android.app.Notification r4 = r4.K
                int r4 = r4.icon
                android.support.v4.app.NotificationCompat$Builder r5 = r0.mBuilder
                int r5 = r5.getColor()
                android.graphics.Bitmap r1 = r0.a(r4, r1, r3, r5)
                int r3 = android.support.compat.R.id.right_icon
                r9.setImageViewBitmap(r3, r1)
                goto L_0x00bd
            L_0x00ae:
                int r1 = android.support.compat.R.id.right_icon
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                android.app.Notification r3 = r3.K
                int r3 = r3.icon
                android.graphics.Bitmap r3 = r0.createColoredBitmap(r3, r4)
                r9.setImageViewBitmap(r1, r3)
            L_0x00bd:
                int r1 = android.support.compat.R.id.right_icon
                r9.setViewVisibility(r1, r11)
                goto L_0x010e
            L_0x00c3:
                if (r18 == 0) goto L_0x010e
                android.support.v4.app.NotificationCompat$Builder r1 = r0.mBuilder
                android.app.Notification r1 = r1.K
                int r1 = r1.icon
                if (r1 == 0) goto L_0x010e
                int r1 = android.support.compat.R.id.icon
                r9.setViewVisibility(r1, r11)
                int r1 = android.os.Build.VERSION.SDK_INT
                if (r1 < r6) goto L_0x00ff
                int r1 = android.support.compat.R.dimen.notification_large_icon_width
                int r1 = r2.getDimensionPixelSize(r1)
                int r3 = android.support.compat.R.dimen.notification_big_circle_margin
                int r3 = r2.getDimensionPixelSize(r3)
                int r1 = r1 - r3
                int r3 = android.support.compat.R.dimen.notification_small_icon_size_as_large
                int r3 = r2.getDimensionPixelSize(r3)
                android.support.v4.app.NotificationCompat$Builder r4 = r0.mBuilder
                android.app.Notification r4 = r4.K
                int r4 = r4.icon
                android.support.v4.app.NotificationCompat$Builder r5 = r0.mBuilder
                int r5 = r5.getColor()
                android.graphics.Bitmap r1 = r0.a(r4, r1, r3, r5)
                int r3 = android.support.compat.R.id.icon
                r9.setImageViewBitmap(r3, r1)
                goto L_0x010e
            L_0x00ff:
                int r1 = android.support.compat.R.id.icon
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                android.app.Notification r3 = r3.K
                int r3 = r3.icon
                android.graphics.Bitmap r3 = r0.createColoredBitmap(r3, r4)
                r9.setImageViewBitmap(r1, r3)
            L_0x010e:
                android.support.v4.app.NotificationCompat$Builder r1 = r0.mBuilder
                java.lang.CharSequence r1 = r1.a
                if (r1 == 0) goto L_0x011d
                int r1 = android.support.compat.R.id.title
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                java.lang.CharSequence r3 = r3.a
                r9.setTextViewText(r1, r3)
            L_0x011d:
                android.support.v4.app.NotificationCompat$Builder r1 = r0.mBuilder
                java.lang.CharSequence r1 = r1.b
                if (r1 == 0) goto L_0x012e
                int r1 = android.support.compat.R.id.text
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                java.lang.CharSequence r3 = r3.b
                r9.setTextViewText(r1, r3)
                r1 = 1
                goto L_0x012f
            L_0x012e:
                r1 = 0
            L_0x012f:
                int r3 = android.os.Build.VERSION.SDK_INT
                if (r3 >= r6) goto L_0x013b
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                android.graphics.Bitmap r3 = r3.f
                if (r3 == 0) goto L_0x013b
                r3 = 1
                goto L_0x013c
            L_0x013b:
                r3 = 0
            L_0x013c:
                android.support.v4.app.NotificationCompat$Builder r4 = r0.mBuilder
                java.lang.CharSequence r4 = r4.g
                if (r4 == 0) goto L_0x0153
                int r1 = android.support.compat.R.id.info
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                java.lang.CharSequence r3 = r3.g
                r9.setTextViewText(r1, r3)
                int r1 = android.support.compat.R.id.info
                r9.setViewVisibility(r1, r11)
            L_0x0150:
                r1 = 1
                r14 = 1
                goto L_0x0190
            L_0x0153:
                android.support.v4.app.NotificationCompat$Builder r4 = r0.mBuilder
                int r4 = r4.h
                if (r4 <= 0) goto L_0x0189
                int r1 = android.support.compat.R.integer.status_bar_notification_info_maxnum
                int r1 = r2.getInteger(r1)
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                int r3 = r3.h
                if (r3 <= r1) goto L_0x0171
                int r1 = android.support.compat.R.id.info
                int r3 = android.support.compat.R.string.status_bar_notification_info_overflow
                java.lang.String r3 = r2.getString(r3)
                r9.setTextViewText(r1, r3)
                goto L_0x0183
            L_0x0171:
                java.text.NumberFormat r1 = java.text.NumberFormat.getIntegerInstance()
                int r3 = android.support.compat.R.id.info
                android.support.v4.app.NotificationCompat$Builder r4 = r0.mBuilder
                int r4 = r4.h
                long r4 = (long) r4
                java.lang.String r1 = r1.format(r4)
                r9.setTextViewText(r3, r1)
            L_0x0183:
                int r1 = android.support.compat.R.id.info
                r9.setViewVisibility(r1, r11)
                goto L_0x0150
            L_0x0189:
                int r4 = android.support.compat.R.id.info
                r9.setViewVisibility(r4, r13)
                r14 = r1
                r1 = r3
            L_0x0190:
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                java.lang.CharSequence r3 = r3.m
                if (r3 == 0) goto L_0x01be
                int r3 = android.os.Build.VERSION.SDK_INT
                if (r3 < r12) goto L_0x01be
                int r3 = android.support.compat.R.id.text
                android.support.v4.app.NotificationCompat$Builder r4 = r0.mBuilder
                java.lang.CharSequence r4 = r4.m
                r9.setTextViewText(r3, r4)
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                java.lang.CharSequence r3 = r3.b
                if (r3 == 0) goto L_0x01b9
                int r3 = android.support.compat.R.id.text2
                android.support.v4.app.NotificationCompat$Builder r4 = r0.mBuilder
                java.lang.CharSequence r4 = r4.b
                r9.setTextViewText(r3, r4)
                int r3 = android.support.compat.R.id.text2
                r9.setViewVisibility(r3, r11)
                r3 = 1
                goto L_0x01bf
            L_0x01b9:
                int r3 = android.support.compat.R.id.text2
                r9.setViewVisibility(r3, r13)
            L_0x01be:
                r3 = 0
            L_0x01bf:
                if (r3 == 0) goto L_0x01dd
                int r3 = android.os.Build.VERSION.SDK_INT
                if (r3 < r12) goto L_0x01dd
                if (r20 == 0) goto L_0x01d3
                int r3 = android.support.compat.R.dimen.notification_subtext_size
                int r2 = r2.getDimensionPixelSize(r3)
                float r2 = (float) r2
                int r3 = android.support.compat.R.id.text
                r9.setTextViewTextSize(r3, r11, r2)
            L_0x01d3:
                int r4 = android.support.compat.R.id.line1
                r5 = 0
                r6 = 0
                r7 = 0
                r8 = 0
                r3 = r9
                r3.setViewPadding(r4, r5, r6, r7, r8)
            L_0x01dd:
                android.support.v4.app.NotificationCompat$Builder r2 = r0.mBuilder
                long r2 = r2.getWhenIfShowing()
                r4 = 0
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 == 0) goto L_0x022c
                android.support.v4.app.NotificationCompat$Builder r1 = r0.mBuilder
                boolean r1 = r1.k
                if (r1 == 0) goto L_0x0219
                int r1 = android.os.Build.VERSION.SDK_INT
                if (r1 < r12) goto L_0x0219
                int r1 = android.support.compat.R.id.chronometer
                r9.setViewVisibility(r1, r11)
                int r1 = android.support.compat.R.id.chronometer
                java.lang.String r2 = "setBase"
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                long r3 = r3.getWhenIfShowing()
                long r5 = android.os.SystemClock.elapsedRealtime()
                long r7 = java.lang.System.currentTimeMillis()
                long r15 = r5 - r7
                long r5 = r3 + r15
                r9.setLong(r1, r2, r5)
                int r1 = android.support.compat.R.id.chronometer
                java.lang.String r2 = "setStarted"
                r9.setBoolean(r1, r2, r10)
                goto L_0x022d
            L_0x0219:
                int r1 = android.support.compat.R.id.time
                r9.setViewVisibility(r1, r11)
                int r1 = android.support.compat.R.id.time
                java.lang.String r2 = "setTime"
                android.support.v4.app.NotificationCompat$Builder r3 = r0.mBuilder
                long r3 = r3.getWhenIfShowing()
                r9.setLong(r1, r2, r3)
                goto L_0x022d
            L_0x022c:
                r10 = r1
            L_0x022d:
                int r1 = android.support.compat.R.id.right_side
                if (r10 == 0) goto L_0x0233
                r2 = 0
                goto L_0x0235
            L_0x0233:
                r2 = 8
            L_0x0235:
                r9.setViewVisibility(r1, r2)
                int r1 = android.support.compat.R.id.line3
                if (r14 == 0) goto L_0x023d
                goto L_0x023f
            L_0x023d:
                r11 = 8
            L_0x023f:
                r9.setViewVisibility(r1, r11)
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompat.Style.applyStandardTemplate(boolean, int, boolean):android.widget.RemoteViews");
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Bitmap createColoredBitmap(int i, int i2) {
            return a(i, i2, 0);
        }

        private Bitmap a(int i, int i2, int i3) {
            Drawable drawable = this.mBuilder.mContext.getResources().getDrawable(i);
            int intrinsicWidth = i3 == 0 ? drawable.getIntrinsicWidth() : i3;
            if (i3 == 0) {
                i3 = drawable.getIntrinsicHeight();
            }
            Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, i3, Config.ARGB_8888);
            drawable.setBounds(0, 0, intrinsicWidth, i3);
            if (i2 != 0) {
                drawable.mutate().setColorFilter(new PorterDuffColorFilter(i2, Mode.SRC_IN));
            }
            drawable.draw(new Canvas(createBitmap));
            return createBitmap;
        }

        private Bitmap a(int i, int i2, int i3, int i4) {
            int i5 = R.drawable.notification_icon_background;
            if (i4 == 0) {
                i4 = 0;
            }
            Bitmap a = a(i5, i4, i2);
            Canvas canvas = new Canvas(a);
            Drawable mutate = this.mBuilder.mContext.getResources().getDrawable(i).mutate();
            mutate.setFilterBitmap(true);
            int i6 = (i2 - i3) / 2;
            int i7 = i3 + i6;
            mutate.setBounds(i6, i6, i7, i7);
            mutate.setColorFilter(new PorterDuffColorFilter(-1, Mode.SRC_ATOP));
            mutate.draw(canvas);
            return a;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void buildIntoRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2) {
            a(remoteViews);
            remoteViews.removeAllViews(R.id.notification_main_column);
            remoteViews.addView(R.id.notification_main_column, remoteViews2.clone());
            remoteViews.setViewVisibility(R.id.notification_main_column, 0);
            if (VERSION.SDK_INT >= 21) {
                remoteViews.setViewPadding(R.id.notification_main_column_container, 0, a(), 0, 0);
            }
        }

        private void a(RemoteViews remoteViews) {
            remoteViews.setViewVisibility(R.id.title, 8);
            remoteViews.setViewVisibility(R.id.text2, 8);
            remoteViews.setViewVisibility(R.id.text, 8);
        }

        private int a() {
            Resources resources = this.mBuilder.mContext.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_top_pad);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text);
            float a = (a(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
            return Math.round(((1.0f - a) * ((float) dimensionPixelSize)) + (a * ((float) dimensionPixelSize2)));
        }
    }

    public static final class WearableExtender implements Extender {
        public static final int SCREEN_TIMEOUT_LONG = -1;
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        public static final int SIZE_DEFAULT = 0;
        public static final int SIZE_FULL_SCREEN = 5;
        public static final int SIZE_LARGE = 4;
        public static final int SIZE_MEDIUM = 3;
        public static final int SIZE_SMALL = 2;
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> a = new ArrayList<>();
        private int b = 1;
        private PendingIntent c;
        private ArrayList<Notification> d = new ArrayList<>();
        private Bitmap e;
        private int f;
        private int g = GravityCompat.END;
        private int h = -1;
        private int i = 0;
        private int j;
        private int k = 80;
        private int l;
        private String m;
        private String n;

        public WearableExtender() {
        }

        public WearableExtender(Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            Bundle bundle = extras != null ? extras.getBundle("android.wearable.EXTENSIONS") : null;
            if (bundle != null) {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList("actions");
                if (VERSION.SDK_INT >= 16 && parcelableArrayList != null) {
                    Action[] actionArr = new Action[parcelableArrayList.size()];
                    for (int i2 = 0; i2 < actionArr.length; i2++) {
                        if (VERSION.SDK_INT >= 20) {
                            actionArr[i2] = NotificationCompat.a((android.app.Notification.Action) parcelableArrayList.get(i2));
                        } else if (VERSION.SDK_INT >= 16) {
                            actionArr[i2] = NotificationCompatJellybean.a((Bundle) parcelableArrayList.get(i2));
                        }
                    }
                    Collections.addAll(this.a, actionArr);
                }
                this.b = bundle.getInt("flags", 1);
                this.c = (PendingIntent) bundle.getParcelable("displayIntent");
                Notification[] a2 = NotificationCompat.a(bundle, "pages");
                if (a2 != null) {
                    Collections.addAll(this.d, a2);
                }
                this.e = (Bitmap) bundle.getParcelable("background");
                this.f = bundle.getInt("contentIcon");
                this.g = bundle.getInt("contentIconGravity", GravityCompat.END);
                this.h = bundle.getInt("contentActionIndex", -1);
                this.i = bundle.getInt("customSizePreset", 0);
                this.j = bundle.getInt("customContentHeight");
                this.k = bundle.getInt("gravity", 80);
                this.l = bundle.getInt("hintScreenTimeout");
                this.m = bundle.getString("dismissalId");
                this.n = bundle.getString("bridgeTag");
            }
        }

        public Builder extend(Builder builder) {
            Bundle bundle = new Bundle();
            if (!this.a.isEmpty()) {
                if (VERSION.SDK_INT >= 16) {
                    ArrayList arrayList = new ArrayList(this.a.size());
                    Iterator it = this.a.iterator();
                    while (it.hasNext()) {
                        Action action = (Action) it.next();
                        if (VERSION.SDK_INT >= 20) {
                            arrayList.add(a(action));
                        } else if (VERSION.SDK_INT >= 16) {
                            arrayList.add(NotificationCompatJellybean.a(action));
                        }
                    }
                    bundle.putParcelableArrayList("actions", arrayList);
                } else {
                    bundle.putParcelableArrayList("actions", null);
                }
            }
            if (this.b != 1) {
                bundle.putInt("flags", this.b);
            }
            if (this.c != null) {
                bundle.putParcelable("displayIntent", this.c);
            }
            if (!this.d.isEmpty()) {
                bundle.putParcelableArray("pages", (Parcelable[]) this.d.toArray(new Notification[this.d.size()]));
            }
            if (this.e != null) {
                bundle.putParcelable("background", this.e);
            }
            if (this.f != 0) {
                bundle.putInt("contentIcon", this.f);
            }
            if (this.g != 8388613) {
                bundle.putInt("contentIconGravity", this.g);
            }
            if (this.h != -1) {
                bundle.putInt("contentActionIndex", this.h);
            }
            if (this.i != 0) {
                bundle.putInt("customSizePreset", this.i);
            }
            if (this.j != 0) {
                bundle.putInt("customContentHeight", this.j);
            }
            if (this.k != 80) {
                bundle.putInt("gravity", this.k);
            }
            if (this.l != 0) {
                bundle.putInt("hintScreenTimeout", this.l);
            }
            if (this.m != null) {
                bundle.putString("dismissalId", this.m);
            }
            if (this.n != null) {
                bundle.putString("bridgeTag", this.n);
            }
            builder.getExtras().putBundle("android.wearable.EXTENSIONS", bundle);
            return builder;
        }

        @RequiresApi(20)
        private static android.app.Notification.Action a(Action action) {
            Bundle bundle;
            android.app.Notification.Action.Builder builder = new android.app.Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
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
            RemoteInput[] remoteInputs = action.getRemoteInputs();
            if (remoteInputs != null) {
                for (RemoteInput addRemoteInput : RemoteInput.a(remoteInputs)) {
                    builder.addRemoteInput(addRemoteInput);
                }
            }
            return builder.build();
        }

        public WearableExtender clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.a = new ArrayList<>(this.a);
            wearableExtender.b = this.b;
            wearableExtender.c = this.c;
            wearableExtender.d = new ArrayList<>(this.d);
            wearableExtender.e = this.e;
            wearableExtender.f = this.f;
            wearableExtender.g = this.g;
            wearableExtender.h = this.h;
            wearableExtender.i = this.i;
            wearableExtender.j = this.j;
            wearableExtender.k = this.k;
            wearableExtender.l = this.l;
            wearableExtender.m = this.m;
            wearableExtender.n = this.n;
            return wearableExtender;
        }

        public WearableExtender addAction(Action action) {
            this.a.add(action);
            return this;
        }

        public WearableExtender addActions(List<Action> list) {
            this.a.addAll(list);
            return this;
        }

        public WearableExtender clearActions() {
            this.a.clear();
            return this;
        }

        public List<Action> getActions() {
            return this.a;
        }

        public WearableExtender setDisplayIntent(PendingIntent pendingIntent) {
            this.c = pendingIntent;
            return this;
        }

        public PendingIntent getDisplayIntent() {
            return this.c;
        }

        public WearableExtender addPage(Notification notification) {
            this.d.add(notification);
            return this;
        }

        public WearableExtender addPages(List<Notification> list) {
            this.d.addAll(list);
            return this;
        }

        public WearableExtender clearPages() {
            this.d.clear();
            return this;
        }

        public List<Notification> getPages() {
            return this.d;
        }

        public WearableExtender setBackground(Bitmap bitmap) {
            this.e = bitmap;
            return this;
        }

        public Bitmap getBackground() {
            return this.e;
        }

        public WearableExtender setContentIcon(int i2) {
            this.f = i2;
            return this;
        }

        public int getContentIcon() {
            return this.f;
        }

        public WearableExtender setContentIconGravity(int i2) {
            this.g = i2;
            return this;
        }

        public int getContentIconGravity() {
            return this.g;
        }

        public WearableExtender setContentAction(int i2) {
            this.h = i2;
            return this;
        }

        public int getContentAction() {
            return this.h;
        }

        public WearableExtender setGravity(int i2) {
            this.k = i2;
            return this;
        }

        public int getGravity() {
            return this.k;
        }

        public WearableExtender setCustomSizePreset(int i2) {
            this.i = i2;
            return this;
        }

        public int getCustomSizePreset() {
            return this.i;
        }

        public WearableExtender setCustomContentHeight(int i2) {
            this.j = i2;
            return this;
        }

        public int getCustomContentHeight() {
            return this.j;
        }

        public WearableExtender setStartScrollBottom(boolean z) {
            a(8, z);
            return this;
        }

        public boolean getStartScrollBottom() {
            return (this.b & 8) != 0;
        }

        public WearableExtender setContentIntentAvailableOffline(boolean z) {
            a(1, z);
            return this;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.b & 1) != 0;
        }

        public WearableExtender setHintHideIcon(boolean z) {
            a(2, z);
            return this;
        }

        public boolean getHintHideIcon() {
            return (this.b & 2) != 0;
        }

        public WearableExtender setHintShowBackgroundOnly(boolean z) {
            a(4, z);
            return this;
        }

        public boolean getHintShowBackgroundOnly() {
            return (this.b & 4) != 0;
        }

        public WearableExtender setHintAvoidBackgroundClipping(boolean z) {
            a(16, z);
            return this;
        }

        public boolean getHintAvoidBackgroundClipping() {
            return (this.b & 16) != 0;
        }

        public WearableExtender setHintScreenTimeout(int i2) {
            this.l = i2;
            return this;
        }

        public int getHintScreenTimeout() {
            return this.l;
        }

        public WearableExtender setHintAmbientBigPicture(boolean z) {
            a(32, z);
            return this;
        }

        public boolean getHintAmbientBigPicture() {
            return (this.b & 32) != 0;
        }

        public WearableExtender setHintContentIntentLaunchesActivity(boolean z) {
            a(64, z);
            return this;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.b & 64) != 0;
        }

        public WearableExtender setDismissalId(String str) {
            this.m = str;
            return this;
        }

        public String getDismissalId() {
            return this.m;
        }

        public WearableExtender setBridgeTag(String str) {
            this.n = str;
            return this;
        }

        public String getBridgeTag() {
            return this.n;
        }

        private void a(int i2, boolean z) {
            if (z) {
                this.b = i2 | this.b;
                return;
            }
            this.b = (i2 ^ -1) & this.b;
        }
    }

    static Notification[] a(Bundle bundle, String str) {
        Parcelable[] parcelableArray = bundle.getParcelableArray(str);
        if ((parcelableArray instanceof Notification[]) || parcelableArray == null) {
            return (Notification[]) parcelableArray;
        }
        Notification[] notificationArr = new Notification[parcelableArray.length];
        for (int i = 0; i < parcelableArray.length; i++) {
            notificationArr[i] = (Notification) parcelableArray[i];
        }
        bundle.putParcelableArray(str, notificationArr);
        return notificationArr;
    }

    public static Bundle getExtras(Notification notification) {
        if (VERSION.SDK_INT >= 19) {
            return notification.extras;
        }
        if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.a(notification);
        }
        return null;
    }

    public static int getActionCount(Notification notification) {
        int i = 0;
        if (VERSION.SDK_INT >= 19) {
            if (notification.actions != null) {
                i = notification.actions.length;
            }
            return i;
        } else if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.b(notification);
        } else {
            return 0;
        }
    }

    public static Action getAction(Notification notification, int i) {
        if (VERSION.SDK_INT >= 20) {
            return a(notification.actions[i]);
        }
        Bundle bundle = null;
        if (VERSION.SDK_INT >= 19) {
            android.app.Notification.Action action = notification.actions[i];
            SparseArray sparseParcelableArray = notification.extras.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
            if (sparseParcelableArray != null) {
                bundle = (Bundle) sparseParcelableArray.get(i);
            }
            return NotificationCompatJellybean.a(action.icon, action.title, action.actionIntent, bundle);
        } else if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.a(notification, i);
        } else {
            return null;
        }
    }

    @RequiresApi(20)
    static Action a(android.app.Notification.Action action) {
        RemoteInput[] remoteInputArr;
        boolean z;
        RemoteInput[] remoteInputs = action.getRemoteInputs();
        boolean z2 = false;
        if (remoteInputs == null) {
            remoteInputArr = null;
        } else {
            RemoteInput[] remoteInputArr2 = new RemoteInput[remoteInputs.length];
            for (int i = 0; i < remoteInputs.length; i++) {
                RemoteInput remoteInput = remoteInputs[i];
                RemoteInput remoteInput2 = new RemoteInput(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), remoteInput.getExtras(), null);
                remoteInputArr2[i] = remoteInput2;
            }
            remoteInputArr = remoteInputArr2;
        }
        if (VERSION.SDK_INT >= 24) {
            if (action.getExtras().getBoolean("android.support.allowGeneratedReplies") || action.getAllowGeneratedReplies()) {
                z2 = true;
            }
            z = z2;
        } else {
            z = action.getExtras().getBoolean("android.support.allowGeneratedReplies");
        }
        Action action2 = new Action(action.icon, action.title, action.actionIntent, action.getExtras(), remoteInputArr, null, z);
        return action2;
    }

    public static String getCategory(Notification notification) {
        if (VERSION.SDK_INT >= 21) {
            return notification.category;
        }
        return null;
    }

    public static boolean getLocalOnly(Notification notification) {
        boolean z = false;
        if (VERSION.SDK_INT >= 20) {
            if ((notification.flags & 256) != 0) {
                z = true;
            }
            return z;
        } else if (VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
        } else {
            if (VERSION.SDK_INT >= 16) {
                return NotificationCompatJellybean.a(notification).getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
            }
            return false;
        }
    }

    public static String getGroup(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return notification.getGroup();
        }
        if (VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.a(notification).getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        return null;
    }

    public static boolean isGroupSummary(Notification notification) {
        boolean z = false;
        if (VERSION.SDK_INT >= 20) {
            if ((notification.flags & 512) != 0) {
                z = true;
            }
            return z;
        } else if (VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
        } else {
            if (VERSION.SDK_INT >= 16) {
                return NotificationCompatJellybean.a(notification).getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
            }
            return false;
        }
    }

    public static String getSortKey(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return notification.getSortKey();
        }
        if (VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.a(notification).getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        return null;
    }

    public static String getChannelId(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getChannelId();
        }
        return null;
    }

    public static long getTimeoutAfter(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getTimeoutAfter();
        }
        return 0;
    }

    public static int getBadgeIconType(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getBadgeIconType();
        }
        return 0;
    }

    public static String getShortcutId(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getShortcutId();
        }
        return null;
    }

    public static int getGroupAlertBehavior(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getGroupAlertBehavior();
        }
        return 0;
    }
}
