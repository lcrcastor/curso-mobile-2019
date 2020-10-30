package ar.com.santander.rio.mbanking.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;

public class ISBANToast {
    public static int DEBUG = 2131231060;
    public static int ERROR = 2131231059;
    public static int INFORMATION = 2131231061;
    public static int LENGTH_LONG = 1;
    public static int LENGTH_SHORT = 0;
    public static int WARNING = 2131231060;

    private ISBANToast() {
    }

    public static void show(Context context, int i, String str, int i2) {
        show(context, i, str, i2, 16);
    }

    public static void show(Context context, int i, String str, int i2, int i3) {
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.layout_isban_toast, (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout_root));
        ((TextView) inflate.findViewById(R.id.toast_layout_text)).setText(str);
        ((ImageView) inflate.findViewById(R.id.toast_layout_img)).setImageResource(i);
        Toast toast = new Toast(context);
        toast.setGravity(i3, 0, 0);
        toast.setDuration(i2);
        toast.setView(inflate);
        toast.show();
    }
}
