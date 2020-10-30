package ar.com.santander.rio.mbanking.components.popup;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;

public class ActionItem {
    private int a;
    private Drawable b;
    private String c;
    private int d;
    private boolean e;
    private Typeface f;
    private View g;

    public enum CustomTypeFace {
        BOLD,
        NORMAL
    }

    public ActionItem(int i, String str, Drawable drawable, int i2) {
        this.d = -1;
        this.c = str;
        this.b = drawable;
        this.d = i;
        this.a = i2;
    }

    public ActionItem() {
        this(-1, null, null, ViewCompat.MEASURED_STATE_MASK);
    }

    public ActionItem(int i, String str) {
        this(i, str, null, ViewCompat.MEASURED_STATE_MASK);
    }

    public ActionItem(Drawable drawable) {
        this(-1, null, drawable, ViewCompat.MEASURED_STATE_MASK);
    }

    public ActionItem(int i, String str, Drawable drawable) {
        this(i, null, drawable, ViewCompat.MEASURED_STATE_MASK);
    }

    public ActionItem(int i, Drawable drawable) {
        this(i, null, drawable, ViewCompat.MEASURED_STATE_MASK);
    }

    public ActionItem(int i, String str, int i2) {
        this(i, str, null, i2);
    }

    public ActionItem(int i, Drawable drawable, int i2) {
        this(i, null, drawable, i2);
    }

    public Drawable getIcon() {
        return this.b;
    }

    public void setIcon(Drawable drawable) {
        this.b = drawable;
    }

    public String getTitle() {
        return this.c;
    }

    public void setTitle(String str) {
        this.c = str;
        a(str);
    }

    public int getActionId() {
        return this.d;
    }

    public void setActionId(int i) {
        this.d = i;
    }

    public boolean isSticky() {
        return this.e;
    }

    public void setSticky(boolean z) {
        this.e = z;
    }

    public int getColor() {
        return this.a;
    }

    public void setColor(int i) {
        this.a = i;
    }

    public void setTypeFace(AssetManager assetManager, CustomTypeFace customTypeFace) {
        if (CustomTypeFace.BOLD.equals(customTypeFace)) {
            this.f = a(assetManager);
        } else {
            this.f = b(assetManager);
        }
    }

    private Typeface a(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/OpenSans-Bold.otf");
    }

    private Typeface b(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/OpenSans-Regular.otf");
    }

    public Typeface getTypeface() {
        return this.f;
    }

    public View getMainView() {
        return this.g;
    }

    public void setMainView(View view) {
        this.g = view;
    }

    private void a(String str) {
        if (this.g != null) {
            TextView textView = (TextView) this.g.findViewById(R.id.view_title);
            if (textView != null) {
                textView.setText(str);
            }
        }
    }
}
