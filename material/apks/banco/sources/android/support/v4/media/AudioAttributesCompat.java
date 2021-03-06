package android.support.v4.media;

import android.media.AudioAttributes;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.SparseIntArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public class AudioAttributesCompat {
    public static final int CONTENT_TYPE_MOVIE = 3;
    public static final int CONTENT_TYPE_MUSIC = 2;
    public static final int CONTENT_TYPE_SONIFICATION = 4;
    public static final int CONTENT_TYPE_SPEECH = 1;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    public static final int FLAG_AUDIBILITY_ENFORCED = 1;
    public static final int FLAG_HW_AV_SYNC = 16;
    public static final int USAGE_ALARM = 4;
    public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
    public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
    public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
    public static final int USAGE_ASSISTANT = 16;
    public static final int USAGE_GAME = 14;
    public static final int USAGE_MEDIA = 1;
    public static final int USAGE_NOTIFICATION = 5;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
    public static final int USAGE_NOTIFICATION_EVENT = 10;
    public static final int USAGE_NOTIFICATION_RINGTONE = 6;
    public static final int USAGE_UNKNOWN = 0;
    public static final int USAGE_VOICE_COMMUNICATION = 2;
    public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
    private static final SparseIntArray e = new SparseIntArray();
    /* access modifiers changed from: private */
    public static boolean f;
    private static final int[] g = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16};
    int a;
    int b;
    int c;
    Integer d;
    /* access modifiers changed from: private */
    public Wrapper h;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AttributeContentType {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AttributeUsage {
    }

    public static class Builder {
        private int a = 0;
        private int b = 0;
        private int c = 0;
        private Integer d;
        private Object e;

        public Builder() {
        }

        public Builder(AudioAttributesCompat audioAttributesCompat) {
            this.a = audioAttributesCompat.a;
            this.b = audioAttributesCompat.b;
            this.c = audioAttributesCompat.c;
            this.d = audioAttributesCompat.d;
            this.e = audioAttributesCompat.unwrap();
        }

        public AudioAttributesCompat build() {
            if (AudioAttributesCompat.f || VERSION.SDK_INT < 21) {
                AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
                audioAttributesCompat.b = this.b;
                audioAttributesCompat.c = this.c;
                audioAttributesCompat.a = this.a;
                audioAttributesCompat.d = this.d;
                audioAttributesCompat.h = null;
                return audioAttributesCompat;
            } else if (this.e != null) {
                return AudioAttributesCompat.wrap(this.e);
            } else {
                android.media.AudioAttributes.Builder usage = new android.media.AudioAttributes.Builder().setContentType(this.b).setFlags(this.c).setUsage(this.a);
                if (this.d != null) {
                    usage.setLegacyStreamType(this.d.intValue());
                }
                return AudioAttributesCompat.wrap(usage.build());
            }
        }

        public Builder setUsage(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    this.a = i;
                    break;
                case 16:
                    if (!AudioAttributesCompat.f && VERSION.SDK_INT > 25) {
                        this.a = i;
                        break;
                    } else {
                        this.a = 12;
                        break;
                    }
                default:
                    this.a = 0;
                    break;
            }
            return this;
        }

        public Builder setContentType(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    this.b = i;
                    break;
                default:
                    this.a = 0;
                    break;
            }
            return this;
        }

        public Builder setFlags(int i) {
            this.c = (i & 1023) | this.c;
            return this;
        }

        public Builder setLegacyStreamType(int i) {
            if (i == 10) {
                throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
            }
            this.d = Integer.valueOf(i);
            this.a = AudioAttributesCompat.c(i);
            return this;
        }
    }

    /* access modifiers changed from: private */
    public static int c(int i) {
        switch (i) {
            case 0:
                return 2;
            case 1:
            case 7:
                return 13;
            case 2:
                return 6;
            case 3:
                return 1;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 2;
            case 8:
                return 3;
            case 10:
                return 11;
            default:
                return 0;
        }
    }

    static {
        e.put(5, 1);
        e.put(6, 2);
        e.put(7, 2);
        e.put(8, 1);
        e.put(9, 1);
        e.put(10, 1);
    }

    private AudioAttributesCompat() {
        this.a = 0;
        this.b = 0;
        this.c = 0;
    }

    public int getVolumeControlStream() {
        if (this == null) {
            throw new IllegalArgumentException("Invalid null audio attributes");
        } else if (VERSION.SDK_INT < 26 || f || unwrap() == null) {
            return a(true, this);
        } else {
            return ((AudioAttributes) unwrap()).getVolumeControlStream();
        }
    }

    @Nullable
    public Object unwrap() {
        if (this.h != null) {
            return this.h.a();
        }
        return null;
    }

    public int getLegacyStreamType() {
        if (this.d != null) {
            return this.d.intValue();
        }
        if (VERSION.SDK_INT < 21 || f) {
            return a(false, this.c, this.a);
        }
        return AudioAttributesCompatApi21.a(this.h);
    }

    @Nullable
    public static AudioAttributesCompat wrap(@NonNull Object obj) {
        if (VERSION.SDK_INT < 21 || f) {
            return null;
        }
        AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
        audioAttributesCompat.h = Wrapper.a((AudioAttributes) obj);
        return audioAttributesCompat;
    }

    public int getContentType() {
        if (VERSION.SDK_INT < 21 || f || this.h == null) {
            return this.b;
        }
        return this.h.a().getContentType();
    }

    public int getUsage() {
        if (VERSION.SDK_INT < 21 || f || this.h == null) {
            return this.a;
        }
        return this.h.a().getUsage();
    }

    public int getFlags() {
        if (VERSION.SDK_INT >= 21 && !f && this.h != null) {
            return this.h.a().getFlags();
        }
        int i = this.c;
        int legacyStreamType = getLegacyStreamType();
        if (legacyStreamType == 6) {
            i |= 4;
        } else if (legacyStreamType == 7) {
            i |= 1;
        }
        return i & 273;
    }

    public int hashCode() {
        if (VERSION.SDK_INT >= 21 && !f && this.h != null) {
            return this.h.a().hashCode();
        }
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.b), Integer.valueOf(this.c), Integer.valueOf(this.a), this.d});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (unwrap() != null) {
            sb.append(" audioattributes=");
            sb.append(unwrap());
        } else {
            if (this.d != null) {
                sb.append(" stream=");
                sb.append(this.d);
                sb.append(" derived");
            }
            sb.append(" usage=");
            sb.append(a());
            sb.append(" content=");
            sb.append(this.b);
            sb.append(" flags=0x");
            sb.append(Integer.toHexString(this.c).toUpperCase());
        }
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        return a(this.a);
    }

    static String a(int i) {
        switch (i) {
            case 0:
                return new String("USAGE_UNKNOWN");
            case 1:
                return new String("USAGE_MEDIA");
            case 2:
                return new String("USAGE_VOICE_COMMUNICATION");
            case 3:
                return new String("USAGE_VOICE_COMMUNICATION_SIGNALLING");
            case 4:
                return new String("USAGE_ALARM");
            case 5:
                return new String("USAGE_NOTIFICATION");
            case 6:
                return new String("USAGE_NOTIFICATION_RINGTONE");
            case 7:
                return new String("USAGE_NOTIFICATION_COMMUNICATION_REQUEST");
            case 8:
                return new String("USAGE_NOTIFICATION_COMMUNICATION_INSTANT");
            case 9:
                return new String("USAGE_NOTIFICATION_COMMUNICATION_DELAYED");
            case 10:
                return new String("USAGE_NOTIFICATION_EVENT");
            case 11:
                return new String("USAGE_ASSISTANCE_ACCESSIBILITY");
            case 12:
                return new String("USAGE_ASSISTANCE_NAVIGATION_GUIDANCE");
            case 13:
                return new String("USAGE_ASSISTANCE_SONIFICATION");
            case 14:
                return new String("USAGE_GAME");
            case 16:
                return new String("USAGE_ASSISTANT");
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("unknown usage ");
                sb.append(i);
                return new String(sb.toString());
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static void setForceLegacyBehavior(boolean z) {
        f = z;
    }

    static int a(boolean z, AudioAttributesCompat audioAttributesCompat) {
        return a(z, audioAttributesCompat.getFlags(), audioAttributesCompat.getUsage());
    }

    static int a(boolean z, int i, int i2) {
        int i3 = 1;
        if ((i & 1) == 1) {
            if (!z) {
                i3 = 7;
            }
            return i3;
        }
        int i4 = 0;
        if ((i & 4) == 4) {
            if (!z) {
                i4 = 6;
            }
            return i4;
        }
        int i5 = 3;
        switch (i2) {
            case 0:
                if (z) {
                    i5 = Integer.MIN_VALUE;
                }
                return i5;
            case 1:
            case 12:
            case 14:
            case 16:
                return 3;
            case 2:
                return 0;
            case 3:
                if (!z) {
                    i4 = 8;
                }
                return i4;
            case 4:
                return 4;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5;
            case 6:
                return 2;
            case 11:
                return 10;
            case 13:
                return 1;
            default:
                if (!z) {
                    return 3;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown usage value ");
                sb.append(i2);
                sb.append(" in audio attributes");
                throw new IllegalArgumentException(sb.toString());
        }
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AudioAttributesCompat audioAttributesCompat = (AudioAttributesCompat) obj;
        if (VERSION.SDK_INT >= 21 && !f && this.h != null) {
            return this.h.a().equals(audioAttributesCompat.unwrap());
        }
        if (!(this.b == audioAttributesCompat.getContentType() && this.c == audioAttributesCompat.getFlags() && this.a == audioAttributesCompat.getUsage() && (this.d == null ? audioAttributesCompat.d == null : this.d.equals(audioAttributesCompat.d)))) {
            z = false;
        }
        return z;
    }
}
