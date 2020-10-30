package com.marcohc.toasteroid;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Toasteroid {
    private static final int DEFAULT_GRAVITY = 80;
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    private static Toast myToast;

    public enum STYLES {
        SUCCESS,
        INFO,
        WARNING,
        ERROR,
        DELETE
    }

    public static void show(Activity activity, String message, STYLES style) {
        show(activity, message, style, 0, 80);
    }

    public static void show(Activity activity, String message, STYLES style, int duration) {
        show(activity, message, style, duration, 80);
    }

    public static void show(Activity activity, int message, STYLES style) {
        show(activity, message, style, 0, 80);
    }

    public static void show(Activity activity, int message, STYLES style, int duration) {
        show(activity, message, style, duration, 80);
    }

    public static void show(Activity activity, int message, STYLES style, int duration, int gravity) {
        show(activity, activity.getString(message), style, duration, gravity);
    }

    public static void show(Activity activity, String message, STYLES style, int duration, int gravity) {
        View toastView = activity.getLayoutInflater().inflate(R.layout.toasteroid_layout, null);
        TextView toastMessage = (TextView) toastView.findViewById(R.id.toastMessage);
        ViewGroup toastContainer = (ViewGroup) toastView.findViewById(R.id.toastContainer);
        int marginBottom = (int) activity.getResources().getDimension(R.dimen.padding);
        ((ImageView) toastView.findViewById(R.id.toastImage)).setImageResource(getStyleIcon(style));
        ((GradientDrawable) toastContainer.getBackground()).setColor(activity.getResources().getColor(getStyleBackgroundColor(style)));
        toastMessage.setText(message);
        myToast = new Toast(activity);
        myToast.setDuration(duration);
        myToast.setMargin(0.0f, 0.0f);
        myToast.setGravity(gravity, 0, marginBottom);
        myToast.setView(toastView);
        myToast.show();
    }

    public static void dismiss() {
        if (myToast != null) {
            myToast.cancel();
            myToast = null;
        }
    }

    public static boolean isShown() {
        if (myToast == null || !myToast.getView().isShown()) {
            return false;
        }
        return true;
    }

    private static int getStyleIcon(STYLES style) {
        switch (style) {
            case SUCCESS:
                return R.drawable.ic_toasteroid_success;
            case INFO:
                return R.drawable.ic_toasteroid_info;
            case WARNING:
                return R.drawable.ic_toasteroid_warning;
            case ERROR:
                return R.drawable.ic_toasteroid_error;
            case DELETE:
                return R.drawable.ic_toasteroid_delete;
            default:
                return R.drawable.ic_toasteroid_info;
        }
    }

    private static int getStyleBackgroundColor(STYLES style) {
        switch (style) {
            case SUCCESS:
                return R.color.toasteroid_success;
            case INFO:
                return R.color.toasteroid_info;
            case WARNING:
                return R.color.toasteroid_warning;
            case ERROR:
                return R.color.toasteroid_error;
            case DELETE:
                return R.color.toasteroid_deleted;
            default:
                return R.color.toasteroid_info;
        }
    }
}
