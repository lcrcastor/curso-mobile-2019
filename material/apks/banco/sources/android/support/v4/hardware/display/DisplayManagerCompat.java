package android.support.v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.internal.ServerProtocol;
import java.util.WeakHashMap;

public abstract class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    private static final WeakHashMap<Context, DisplayManagerCompat> a = new WeakHashMap<>();

    static class DisplayManagerCompatApi14Impl extends DisplayManagerCompat {
        private final WindowManager a;

        DisplayManagerCompatApi14Impl(Context context) {
            this.a = (WindowManager) context.getSystemService("window");
        }

        public Display getDisplay(int i) {
            Display defaultDisplay = this.a.getDefaultDisplay();
            if (defaultDisplay.getDisplayId() == i) {
                return defaultDisplay;
            }
            return null;
        }

        public Display[] getDisplays() {
            return new Display[]{this.a.getDefaultDisplay()};
        }

        public Display[] getDisplays(String str) {
            return str == null ? getDisplays() : new Display[0];
        }
    }

    @RequiresApi(17)
    static class DisplayManagerCompatApi17Impl extends DisplayManagerCompat {
        private final DisplayManager a;

        DisplayManagerCompatApi17Impl(Context context) {
            this.a = (DisplayManager) context.getSystemService(ServerProtocol.DIALOG_PARAM_DISPLAY);
        }

        public Display getDisplay(int i) {
            return this.a.getDisplay(i);
        }

        public Display[] getDisplays() {
            return this.a.getDisplays();
        }

        public Display[] getDisplays(String str) {
            return this.a.getDisplays(str);
        }
    }

    @Nullable
    public abstract Display getDisplay(int i);

    @NonNull
    public abstract Display[] getDisplays();

    @NonNull
    public abstract Display[] getDisplays(String str);

    DisplayManagerCompat() {
    }

    @NonNull
    public static DisplayManagerCompat getInstance(@NonNull Context context) {
        DisplayManagerCompat displayManagerCompat;
        synchronized (a) {
            displayManagerCompat = (DisplayManagerCompat) a.get(context);
            if (displayManagerCompat == null) {
                if (VERSION.SDK_INT >= 17) {
                    displayManagerCompat = new DisplayManagerCompatApi17Impl(context);
                } else {
                    displayManagerCompat = new DisplayManagerCompatApi14Impl(context);
                }
                a.put(context, displayManagerCompat);
            }
        }
        return displayManagerCompat;
    }
}
