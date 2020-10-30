package android.support.v4.media;

import android.media.AudioAttributes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(21)
class AudioAttributesCompatApi21 {
    private static Method a;

    static final class Wrapper {
        private AudioAttributes a;

        private Wrapper(AudioAttributes audioAttributes) {
            this.a = audioAttributes;
        }

        public static Wrapper a(@NonNull AudioAttributes audioAttributes) {
            if (audioAttributes != null) {
                return new Wrapper(audioAttributes);
            }
            throw new IllegalArgumentException("AudioAttributesApi21.Wrapper cannot wrap null");
        }

        public AudioAttributes a() {
            return this.a;
        }
    }

    AudioAttributesCompatApi21() {
    }

    public static int a(Wrapper wrapper) {
        AudioAttributes a2 = wrapper.a();
        try {
            if (a == null) {
                a = AudioAttributes.class.getMethod("toLegacyStreamType", new Class[]{AudioAttributes.class});
            }
            return ((Integer) a.invoke(null, new Object[]{a2})).intValue();
        } catch (ClassCastException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.w("AudioAttributesCompat", "getLegacyStreamType() failed on API21+", e);
            return -1;
        }
    }
}
