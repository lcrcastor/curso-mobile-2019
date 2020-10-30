package android.support.v4.media.session;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.asn1.eac.EACTags;

public final class PlaybackStateCompat implements Parcelable {
    public static final long ACTION_FAST_FORWARD = 64;
    public static final long ACTION_PAUSE = 2;
    public static final long ACTION_PLAY = 4;
    public static final long ACTION_PLAY_FROM_MEDIA_ID = 1024;
    public static final long ACTION_PLAY_FROM_SEARCH = 2048;
    public static final long ACTION_PLAY_FROM_URI = 8192;
    public static final long ACTION_PLAY_PAUSE = 512;
    public static final long ACTION_PREPARE = 16384;
    public static final long ACTION_PREPARE_FROM_MEDIA_ID = 32768;
    public static final long ACTION_PREPARE_FROM_SEARCH = 65536;
    public static final long ACTION_PREPARE_FROM_URI = 131072;
    public static final long ACTION_REWIND = 8;
    public static final long ACTION_SEEK_TO = 256;
    public static final long ACTION_SET_CAPTIONING_ENABLED = 1048576;
    public static final long ACTION_SET_RATING = 128;
    public static final long ACTION_SET_REPEAT_MODE = 262144;
    public static final long ACTION_SET_SHUFFLE_MODE = 2097152;
    @Deprecated
    public static final long ACTION_SET_SHUFFLE_MODE_ENABLED = 524288;
    public static final long ACTION_SKIP_TO_NEXT = 32;
    public static final long ACTION_SKIP_TO_PREVIOUS = 16;
    public static final long ACTION_SKIP_TO_QUEUE_ITEM = 4096;
    public static final long ACTION_STOP = 1;
    public static final Creator<PlaybackStateCompat> CREATOR = new Creator<PlaybackStateCompat>() {
        /* renamed from: a */
        public PlaybackStateCompat createFromParcel(Parcel parcel) {
            return new PlaybackStateCompat(parcel);
        }

        /* renamed from: a */
        public PlaybackStateCompat[] newArray(int i) {
            return new PlaybackStateCompat[i];
        }
    };
    public static final int ERROR_CODE_ACTION_ABORTED = 10;
    public static final int ERROR_CODE_APP_ERROR = 1;
    public static final int ERROR_CODE_AUTHENTICATION_EXPIRED = 3;
    public static final int ERROR_CODE_CONCURRENT_STREAM_LIMIT = 5;
    public static final int ERROR_CODE_CONTENT_ALREADY_PLAYING = 8;
    public static final int ERROR_CODE_END_OF_QUEUE = 11;
    public static final int ERROR_CODE_NOT_AVAILABLE_IN_REGION = 7;
    public static final int ERROR_CODE_NOT_SUPPORTED = 2;
    public static final int ERROR_CODE_PARENTAL_CONTROL_RESTRICTED = 6;
    public static final int ERROR_CODE_PREMIUM_ACCOUNT_REQUIRED = 4;
    public static final int ERROR_CODE_SKIP_LIMIT_REACHED = 9;
    public static final int ERROR_CODE_UNKNOWN_ERROR = 0;
    public static final long PLAYBACK_POSITION_UNKNOWN = -1;
    public static final int REPEAT_MODE_ALL = 2;
    public static final int REPEAT_MODE_GROUP = 3;
    public static final int REPEAT_MODE_INVALID = -1;
    public static final int REPEAT_MODE_NONE = 0;
    public static final int REPEAT_MODE_ONE = 1;
    public static final int SHUFFLE_MODE_ALL = 1;
    public static final int SHUFFLE_MODE_GROUP = 2;
    public static final int SHUFFLE_MODE_INVALID = -1;
    public static final int SHUFFLE_MODE_NONE = 0;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_CONNECTING = 8;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FAST_FORWARDING = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_REWINDING = 5;
    public static final int STATE_SKIPPING_TO_NEXT = 10;
    public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    public static final int STATE_STOPPED = 1;
    final int a;
    final long b;
    final long c;
    final float d;
    final long e;
    final int f;
    final CharSequence g;
    final long h;
    List<CustomAction> i;
    final long j;
    final Bundle k;
    private Object l;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Actions {
    }

    public static final class Builder {
        private final List<CustomAction> a = new ArrayList();
        private int b;
        private long c;
        private long d;
        private float e;
        private long f;
        private int g;
        private CharSequence h;
        private long i;
        private long j = -1;
        private Bundle k;

        public Builder() {
        }

        public Builder(PlaybackStateCompat playbackStateCompat) {
            this.b = playbackStateCompat.a;
            this.c = playbackStateCompat.b;
            this.e = playbackStateCompat.d;
            this.i = playbackStateCompat.h;
            this.d = playbackStateCompat.c;
            this.f = playbackStateCompat.e;
            this.g = playbackStateCompat.f;
            this.h = playbackStateCompat.g;
            if (playbackStateCompat.i != null) {
                this.a.addAll(playbackStateCompat.i);
            }
            this.j = playbackStateCompat.j;
            this.k = playbackStateCompat.k;
        }

        public Builder setState(int i2, long j2, float f2) {
            return setState(i2, j2, f2, SystemClock.elapsedRealtime());
        }

        public Builder setState(int i2, long j2, float f2, long j3) {
            this.b = i2;
            this.c = j2;
            this.i = j3;
            this.e = f2;
            return this;
        }

        public Builder setBufferedPosition(long j2) {
            this.d = j2;
            return this;
        }

        public Builder setActions(long j2) {
            this.f = j2;
            return this;
        }

        public Builder addCustomAction(String str, String str2, int i2) {
            return addCustomAction(new CustomAction(str, str2, i2, null));
        }

        public Builder addCustomAction(CustomAction customAction) {
            if (customAction == null) {
                throw new IllegalArgumentException("You may not add a null CustomAction to PlaybackStateCompat.");
            }
            this.a.add(customAction);
            return this;
        }

        public Builder setActiveQueueItemId(long j2) {
            this.j = j2;
            return this;
        }

        public Builder setErrorMessage(CharSequence charSequence) {
            this.h = charSequence;
            return this;
        }

        public Builder setErrorMessage(int i2, CharSequence charSequence) {
            this.g = i2;
            this.h = charSequence;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.k = bundle;
            return this;
        }

        public PlaybackStateCompat build() {
            int i2 = this.b;
            long j2 = this.c;
            long j3 = this.d;
            float f2 = this.e;
            long j4 = this.f;
            int i3 = this.g;
            CharSequence charSequence = this.h;
            long j5 = this.i;
            List<CustomAction> list = this.a;
            long j6 = this.j;
            PlaybackStateCompat playbackStateCompat = new PlaybackStateCompat(i2, j2, j3, f2, j4, i3, charSequence, j5, list, j6, this.k);
            return playbackStateCompat;
        }
    }

    public static final class CustomAction implements Parcelable {
        public static final Creator<CustomAction> CREATOR = new Creator<CustomAction>() {
            /* renamed from: a */
            public CustomAction createFromParcel(Parcel parcel) {
                return new CustomAction(parcel);
            }

            /* renamed from: a */
            public CustomAction[] newArray(int i) {
                return new CustomAction[i];
            }
        };
        private final String a;
        private final CharSequence b;
        private final int c;
        private final Bundle d;
        private Object e;

        public static final class Builder {
            private final String a;
            private final CharSequence b;
            private final int c;
            private Bundle d;

            public Builder(String str, CharSequence charSequence, int i) {
                if (TextUtils.isEmpty(str)) {
                    throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
                } else if (TextUtils.isEmpty(charSequence)) {
                    throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
                } else if (i == 0) {
                    throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
                } else {
                    this.a = str;
                    this.b = charSequence;
                    this.c = i;
                }
            }

            public Builder setExtras(Bundle bundle) {
                this.d = bundle;
                return this;
            }

            public CustomAction build() {
                return new CustomAction(this.a, this.b, this.c, this.d);
            }
        }

        public int describeContents() {
            return 0;
        }

        CustomAction(String str, CharSequence charSequence, int i, Bundle bundle) {
            this.a = str;
            this.b = charSequence;
            this.c = i;
            this.d = bundle;
        }

        CustomAction(Parcel parcel) {
            this.a = parcel.readString();
            this.b = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.c = parcel.readInt();
            this.d = parcel.readBundle();
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            TextUtils.writeToParcel(this.b, parcel, i);
            parcel.writeInt(this.c);
            parcel.writeBundle(this.d);
        }

        public static CustomAction fromCustomAction(Object obj) {
            if (obj == null || VERSION.SDK_INT < 21) {
                return null;
            }
            CustomAction customAction = new CustomAction(CustomAction.a(obj), CustomAction.b(obj), CustomAction.c(obj), CustomAction.d(obj));
            customAction.e = obj;
            return customAction;
        }

        public Object getCustomAction() {
            if (this.e != null || VERSION.SDK_INT < 21) {
                return this.e;
            }
            this.e = CustomAction.a(this.a, this.b, this.c, this.d);
            return this.e;
        }

        public String getAction() {
            return this.a;
        }

        public CharSequence getName() {
            return this.b;
        }

        public int getIcon() {
            return this.c;
        }

        public Bundle getExtras() {
            return this.d;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Action:mName='");
            sb.append(this.b);
            sb.append(", mIcon=");
            sb.append(this.c);
            sb.append(", mExtras=");
            sb.append(this.d);
            return sb.toString();
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaKeyAction {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShuffleMode {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static int toKeyCode(long j2) {
        if (j2 == 4) {
            return EACTags.NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE;
        }
        if (j2 == 2) {
            return CertificateBody.profileType;
        }
        if (j2 == 32) {
            return 87;
        }
        if (j2 == 16) {
            return 88;
        }
        if (j2 == 1) {
            return 86;
        }
        if (j2 == 64) {
            return 90;
        }
        if (j2 == 8) {
            return 89;
        }
        return j2 == 512 ? 85 : 0;
    }

    public int describeContents() {
        return 0;
    }

    PlaybackStateCompat(int i2, long j2, long j3, float f2, long j4, int i3, CharSequence charSequence, long j5, List<CustomAction> list, long j6, Bundle bundle) {
        this.a = i2;
        this.b = j2;
        this.c = j3;
        this.d = f2;
        this.e = j4;
        this.f = i3;
        this.g = charSequence;
        this.h = j5;
        this.i = new ArrayList(list);
        this.j = j6;
        this.k = bundle;
    }

    PlaybackStateCompat(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readLong();
        this.d = parcel.readFloat();
        this.h = parcel.readLong();
        this.c = parcel.readLong();
        this.e = parcel.readLong();
        this.g = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.i = parcel.createTypedArrayList(CustomAction.CREATOR);
        this.j = parcel.readLong();
        this.k = parcel.readBundle();
        this.f = parcel.readInt();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PlaybackState {");
        sb.append("state=");
        sb.append(this.a);
        sb.append(", position=");
        sb.append(this.b);
        sb.append(", buffered position=");
        sb.append(this.c);
        sb.append(", speed=");
        sb.append(this.d);
        sb.append(", updated=");
        sb.append(this.h);
        sb.append(", actions=");
        sb.append(this.e);
        sb.append(", error code=");
        sb.append(this.f);
        sb.append(", error message=");
        sb.append(this.g);
        sb.append(", custom actions=");
        sb.append(this.i);
        sb.append(", active item id=");
        sb.append(this.j);
        sb.append("}");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.a);
        parcel.writeLong(this.b);
        parcel.writeFloat(this.d);
        parcel.writeLong(this.h);
        parcel.writeLong(this.c);
        parcel.writeLong(this.e);
        TextUtils.writeToParcel(this.g, parcel, i2);
        parcel.writeTypedList(this.i);
        parcel.writeLong(this.j);
        parcel.writeBundle(this.k);
        parcel.writeInt(this.f);
    }

    public int getState() {
        return this.a;
    }

    public long getPosition() {
        return this.b;
    }

    public long getBufferedPosition() {
        return this.c;
    }

    public float getPlaybackSpeed() {
        return this.d;
    }

    public long getActions() {
        return this.e;
    }

    public List<CustomAction> getCustomActions() {
        return this.i;
    }

    public int getErrorCode() {
        return this.f;
    }

    public CharSequence getErrorMessage() {
        return this.g;
    }

    public long getLastPositionUpdateTime() {
        return this.h;
    }

    public long getActiveQueueItemId() {
        return this.j;
    }

    @Nullable
    public Bundle getExtras() {
        return this.k;
    }

    public static PlaybackStateCompat fromPlaybackState(Object obj) {
        List list;
        Object obj2 = obj;
        Bundle bundle = null;
        if (obj2 == null || VERSION.SDK_INT < 21) {
            return null;
        }
        List<Object> h2 = PlaybackStateCompatApi21.h(obj);
        if (h2 != null) {
            ArrayList arrayList = new ArrayList(h2.size());
            for (Object fromCustomAction : h2) {
                arrayList.add(CustomAction.fromCustomAction(fromCustomAction));
            }
            list = arrayList;
        } else {
            list = null;
        }
        if (VERSION.SDK_INT >= 22) {
            bundle = PlaybackStateCompatApi22.a(obj);
        }
        PlaybackStateCompat playbackStateCompat = new PlaybackStateCompat(PlaybackStateCompatApi21.a(obj), PlaybackStateCompatApi21.b(obj), PlaybackStateCompatApi21.c(obj), PlaybackStateCompatApi21.d(obj), PlaybackStateCompatApi21.e(obj), 0, PlaybackStateCompatApi21.f(obj), PlaybackStateCompatApi21.g(obj), list, PlaybackStateCompatApi21.i(obj), bundle);
        playbackStateCompat.l = obj2;
        return playbackStateCompat;
    }

    public Object getPlaybackState() {
        if (this.l == null && VERSION.SDK_INT >= 21) {
            ArrayList arrayList = null;
            if (this.i != null) {
                arrayList = new ArrayList(this.i.size());
                for (CustomAction customAction : this.i) {
                    arrayList.add(customAction.getCustomAction());
                }
            }
            ArrayList arrayList2 = arrayList;
            if (VERSION.SDK_INT >= 22) {
                this.l = PlaybackStateCompatApi22.a(this.a, this.b, this.c, this.d, this.e, this.g, this.h, arrayList2, this.j, this.k);
            } else {
                this.l = PlaybackStateCompatApi21.a(this.a, this.b, this.c, this.d, this.e, this.g, this.h, arrayList2, this.j);
            }
        }
        return this.l;
    }
}
