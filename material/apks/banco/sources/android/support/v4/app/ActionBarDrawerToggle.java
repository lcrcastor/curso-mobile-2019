package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.reflect.Method;

@Deprecated
public class ActionBarDrawerToggle implements DrawerListener {
    private static final int[] b = {16843531};
    final Activity a;
    private final Delegate c;
    private final DrawerLayout d;
    private boolean e;
    private boolean f;
    private Drawable g;
    private Drawable h;
    private SlideDrawable i;
    private final int j;
    private final int k;
    private final int l;
    private SetIndicatorInfo m;

    @Deprecated
    public interface Delegate {
        @Nullable
        Drawable getThemeUpIndicator();

        void setActionBarDescription(@StringRes int i);

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i);
    }

    @Deprecated
    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate();
    }

    static class SetIndicatorInfo {
        Method a;
        Method b;
        ImageView c;

        SetIndicatorInfo(Activity activity) {
            try {
                this.a = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", new Class[]{Drawable.class});
                this.b = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", new Class[]{Integer.TYPE});
            } catch (NoSuchMethodException unused) {
                View findViewById = activity.findViewById(16908332);
                if (findViewById != null) {
                    ViewGroup viewGroup = (ViewGroup) findViewById.getParent();
                    if (viewGroup.getChildCount() == 2) {
                        View childAt = viewGroup.getChildAt(0);
                        View childAt2 = viewGroup.getChildAt(1);
                        if (childAt.getId() != 16908332) {
                            childAt2 = childAt;
                        }
                        if (childAt2 instanceof ImageView) {
                            this.c = (ImageView) childAt2;
                        }
                    }
                }
            }
        }
    }

    class SlideDrawable extends InsetDrawable implements Callback {
        private final boolean b;
        private final Rect c;
        private float d;
        private float e;

        SlideDrawable(Drawable drawable) {
            boolean z = false;
            super(drawable, 0);
            if (VERSION.SDK_INT > 18) {
                z = true;
            }
            this.b = z;
            this.c = new Rect();
        }

        public void a(float f) {
            this.d = f;
            invalidateSelf();
        }

        public float a() {
            return this.d;
        }

        public void b(float f) {
            this.e = f;
            invalidateSelf();
        }

        public void draw(@NonNull Canvas canvas) {
            copyBounds(this.c);
            canvas.save();
            int i = 1;
            boolean z = ViewCompat.getLayoutDirection(ActionBarDrawerToggle.this.a.getWindow().getDecorView()) == 1;
            if (z) {
                i = -1;
            }
            float width = (float) this.c.width();
            canvas.translate((-this.e) * width * this.d * ((float) i), BitmapDescriptorFactory.HUE_RED);
            if (z && !this.b) {
                canvas.translate(width, BitmapDescriptorFactory.HUE_RED);
                canvas.scale(-1.0f, 1.0f);
            }
            super.draw(canvas);
            canvas.restore();
        }
    }

    public void onDrawerStateChanged(int i2) {
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @DrawableRes int i2, @StringRes int i3, @StringRes int i4) {
        this(activity, drawerLayout, !a((Context) activity), i2, i3, i4);
    }

    private static boolean a(Context context) {
        return context.getApplicationInfo().targetSdkVersion >= 21 && VERSION.SDK_INT >= 21;
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, boolean z, @DrawableRes int i2, @StringRes int i3, @StringRes int i4) {
        this.e = true;
        this.a = activity;
        if (activity instanceof DelegateProvider) {
            this.c = ((DelegateProvider) activity).getDrawerToggleDelegate();
        } else {
            this.c = null;
        }
        this.d = drawerLayout;
        this.j = i2;
        this.k = i3;
        this.l = i4;
        this.g = a();
        this.h = ContextCompat.getDrawable(activity, i2);
        this.i = new SlideDrawable(this.h);
        this.i.b(z ? 0.33333334f : BitmapDescriptorFactory.HUE_RED);
    }

    public void syncState() {
        if (this.d.isDrawerOpen((int) GravityCompat.START)) {
            this.i.a(1.0f);
        } else {
            this.i.a(BitmapDescriptorFactory.HUE_RED);
        }
        if (this.e) {
            a(this.i, this.d.isDrawerOpen((int) GravityCompat.START) ? this.l : this.k);
        }
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        if (drawable == null) {
            this.g = a();
            this.f = false;
        } else {
            this.g = drawable;
            this.f = true;
        }
        if (!this.e) {
            a(this.g, 0);
        }
    }

    public void setHomeAsUpIndicator(int i2) {
        setHomeAsUpIndicator(i2 != 0 ? ContextCompat.getDrawable(this.a, i2) : null);
    }

    public void setDrawerIndicatorEnabled(boolean z) {
        if (z != this.e) {
            if (z) {
                a(this.i, this.d.isDrawerOpen((int) GravityCompat.START) ? this.l : this.k);
            } else {
                a(this.g, 0);
            }
            this.e = z;
        }
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.e;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.f) {
            this.g = a();
        }
        this.h = ContextCompat.getDrawable(this.a, this.j);
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332 || !this.e) {
            return false;
        }
        if (this.d.isDrawerVisible((int) GravityCompat.START)) {
            this.d.closeDrawer((int) GravityCompat.START);
        } else {
            this.d.openDrawer((int) GravityCompat.START);
        }
        return true;
    }

    public void onDrawerSlide(View view, float f2) {
        float f3;
        float a2 = this.i.a();
        if (f2 > 0.5f) {
            f3 = Math.max(a2, Math.max(BitmapDescriptorFactory.HUE_RED, f2 - 0.5f) * 2.0f);
        } else {
            f3 = Math.min(a2, f2 * 2.0f);
        }
        this.i.a(f3);
    }

    public void onDrawerOpened(View view) {
        this.i.a(1.0f);
        if (this.e) {
            a(this.l);
        }
    }

    public void onDrawerClosed(View view) {
        this.i.a(BitmapDescriptorFactory.HUE_RED);
        if (this.e) {
            a(this.k);
        }
    }

    private Drawable a() {
        Context context;
        if (this.c != null) {
            return this.c.getThemeUpIndicator();
        }
        if (VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.a.getActionBar();
            if (actionBar != null) {
                context = actionBar.getThemedContext();
            } else {
                context = this.a;
            }
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, b, 16843470, 0);
            Drawable drawable = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            return drawable;
        }
        TypedArray obtainStyledAttributes2 = this.a.obtainStyledAttributes(b);
        Drawable drawable2 = obtainStyledAttributes2.getDrawable(0);
        obtainStyledAttributes2.recycle();
        return drawable2;
    }

    private void a(Drawable drawable, int i2) {
        if (this.c != null) {
            this.c.setActionBarUpIndicator(drawable, i2);
            return;
        }
        if (VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.a.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(drawable);
                actionBar.setHomeActionContentDescription(i2);
            }
        } else {
            if (this.m == null) {
                this.m = new SetIndicatorInfo(this.a);
            }
            if (this.m.a != null) {
                try {
                    ActionBar actionBar2 = this.a.getActionBar();
                    this.m.a.invoke(actionBar2, new Object[]{drawable});
                    this.m.b.invoke(actionBar2, new Object[]{Integer.valueOf(i2)});
                } catch (Exception e2) {
                    Log.w("ActionBarDrawerToggle", "Couldn't set home-as-up indicator via JB-MR2 API", e2);
                }
            } else if (this.m.c != null) {
                this.m.c.setImageDrawable(drawable);
            } else {
                Log.w("ActionBarDrawerToggle", "Couldn't set home-as-up indicator");
            }
        }
    }

    private void a(int i2) {
        if (this.c != null) {
            this.c.setActionBarDescription(i2);
            return;
        }
        if (VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.a.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(i2);
            }
        } else {
            if (this.m == null) {
                this.m = new SetIndicatorInfo(this.a);
            }
            if (this.m.a != null) {
                try {
                    ActionBar actionBar2 = this.a.getActionBar();
                    this.m.b.invoke(actionBar2, new Object[]{Integer.valueOf(i2)});
                    actionBar2.setSubtitle(actionBar2.getSubtitle());
                } catch (Exception e2) {
                    Log.w("ActionBarDrawerToggle", "Couldn't set content description via JB-MR2 API", e2);
                }
            }
        }
    }
}
