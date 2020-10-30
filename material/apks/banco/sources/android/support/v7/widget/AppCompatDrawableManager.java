package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.LruCache;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo({Scope.LIBRARY_GROUP})
public final class AppCompatDrawableManager {
    private static final Mode a = Mode.SRC_IN;
    private static AppCompatDrawableManager b;
    private static final ColorFilterLruCache c = new ColorFilterLruCache(6);
    private static final int[] d = {R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
    private static final int[] e = {R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha};
    private static final int[] f = {R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl_dark, R.drawable.abc_text_select_handle_middle_mtrl_dark, R.drawable.abc_text_select_handle_right_mtrl_dark, R.drawable.abc_text_select_handle_left_mtrl_light, R.drawable.abc_text_select_handle_middle_mtrl_light, R.drawable.abc_text_select_handle_right_mtrl_light};
    private static final int[] g = {R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
    private static final int[] h = {R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material};
    private static final int[] i = {R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material};
    private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> j;
    private ArrayMap<String, InflateDelegate> k;
    private SparseArrayCompat<String> l;
    private final Object m = new Object();
    private final WeakHashMap<Context, LongSparseArray<WeakReference<ConstantState>>> n = new WeakHashMap<>(0);
    private TypedValue o;
    private boolean p;

    @RequiresApi(11)
    static class AvdcInflateDelegate implements InflateDelegate {
        AvdcInflateDelegate() {
        }

        public Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme) {
            try {
                return AnimatedVectorDrawableCompat.createFromXmlInner(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int i) {
            super(i);
        }

        /* access modifiers changed from: 0000 */
        public PorterDuffColorFilter a(int i, Mode mode) {
            return (PorterDuffColorFilter) get(Integer.valueOf(b(i, mode)));
        }

        /* access modifiers changed from: 0000 */
        public PorterDuffColorFilter a(int i, Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return (PorterDuffColorFilter) put(Integer.valueOf(b(i, mode)), porterDuffColorFilter);
        }

        private static int b(int i, Mode mode) {
            return ((i + 31) * 31) + mode.hashCode();
        }
    }

    interface InflateDelegate {
        Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme);
    }

    static class VdcInflateDelegate implements InflateDelegate {
        VdcInflateDelegate() {
        }

        public Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme) {
            try {
                return VectorDrawableCompat.createFromXmlInner(context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    public static AppCompatDrawableManager get() {
        if (b == null) {
            b = new AppCompatDrawableManager();
            a(b);
        }
        return b;
    }

    private static void a(@NonNull AppCompatDrawableManager appCompatDrawableManager) {
        if (VERSION.SDK_INT < 24) {
            appCompatDrawableManager.a("vector", (InflateDelegate) new VdcInflateDelegate());
            appCompatDrawableManager.a("animated-vector", (InflateDelegate) new AvdcInflateDelegate());
        }
    }

    public Drawable getDrawable(@NonNull Context context, @DrawableRes int i2) {
        return a(context, i2, false);
    }

    /* access modifiers changed from: 0000 */
    public Drawable a(@NonNull Context context, @DrawableRes int i2, boolean z) {
        e(context);
        Drawable c2 = c(context, i2);
        if (c2 == null) {
            c2 = b(context, i2);
        }
        if (c2 == null) {
            c2 = ContextCompat.getDrawable(context, i2);
        }
        if (c2 != null) {
            c2 = a(context, i2, z, c2);
        }
        if (c2 != null) {
            DrawableUtils.a(c2);
        }
        return c2;
    }

    public void onConfigurationChanged(@NonNull Context context) {
        synchronized (this.m) {
            LongSparseArray longSparseArray = (LongSparseArray) this.n.get(context);
            if (longSparseArray != null) {
                longSparseArray.clear();
            }
        }
    }

    private static long a(TypedValue typedValue) {
        return (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
    }

    private Drawable b(@NonNull Context context, @DrawableRes int i2) {
        if (this.o == null) {
            this.o = new TypedValue();
        }
        TypedValue typedValue = this.o;
        context.getResources().getValue(i2, typedValue, true);
        long a2 = a(typedValue);
        Drawable a3 = a(context, a2);
        if (a3 != null) {
            return a3;
        }
        if (i2 == R.drawable.abc_cab_background_top_material) {
            a3 = new LayerDrawable(new Drawable[]{getDrawable(context, R.drawable.abc_cab_background_internal_bg), getDrawable(context, R.drawable.abc_cab_background_top_mtrl_alpha)});
        }
        if (a3 != null) {
            a3.setChangingConfigurations(typedValue.changingConfigurations);
            a(context, a2, a3);
        }
        return a3;
    }

    private Drawable a(@NonNull Context context, @DrawableRes int i2, boolean z, @NonNull Drawable drawable) {
        ColorStateList a2 = a(context, i2);
        if (a2 != null) {
            if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
                drawable = drawable.mutate();
            }
            Drawable wrap = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(wrap, a2);
            Mode a3 = a(i2);
            if (a3 == null) {
                return wrap;
            }
            DrawableCompat.setTintMode(wrap, a3);
            return wrap;
        } else if (i2 == R.drawable.abc_seekbar_track_material) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            a(layerDrawable.findDrawableByLayerId(16908288), ThemeUtils.a(context, R.attr.colorControlNormal), a);
            a(layerDrawable.findDrawableByLayerId(16908303), ThemeUtils.a(context, R.attr.colorControlNormal), a);
            a(layerDrawable.findDrawableByLayerId(16908301), ThemeUtils.a(context, R.attr.colorControlActivated), a);
            return drawable;
        } else if (i2 == R.drawable.abc_ratingbar_material || i2 == R.drawable.abc_ratingbar_indicator_material || i2 == R.drawable.abc_ratingbar_small_material) {
            LayerDrawable layerDrawable2 = (LayerDrawable) drawable;
            a(layerDrawable2.findDrawableByLayerId(16908288), ThemeUtils.c(context, R.attr.colorControlNormal), a);
            a(layerDrawable2.findDrawableByLayerId(16908303), ThemeUtils.a(context, R.attr.colorControlActivated), a);
            a(layerDrawable2.findDrawableByLayerId(16908301), ThemeUtils.a(context, R.attr.colorControlActivated), a);
            return drawable;
        } else if (a(context, i2, drawable) || !z) {
            return drawable;
        } else {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0079 A[Catch:{ Exception -> 0x00a8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0081 A[Catch:{ Exception -> 0x00a8 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable c(@android.support.annotation.NonNull android.content.Context r10, @android.support.annotation.DrawableRes int r11) {
        /*
            r9 = this;
            android.support.v4.util.ArrayMap<java.lang.String, android.support.v7.widget.AppCompatDrawableManager$InflateDelegate> r0 = r9.k
            r1 = 0
            if (r0 == 0) goto L_0x00ba
            android.support.v4.util.ArrayMap<java.lang.String, android.support.v7.widget.AppCompatDrawableManager$InflateDelegate> r0 = r9.k
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x00ba
            android.support.v4.util.SparseArrayCompat<java.lang.String> r0 = r9.l
            if (r0 == 0) goto L_0x002c
            android.support.v4.util.SparseArrayCompat<java.lang.String> r0 = r9.l
            java.lang.Object r0 = r0.get(r11)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r2 = "appcompat_skip_skip"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L_0x002b
            if (r0 == 0) goto L_0x0033
            android.support.v4.util.ArrayMap<java.lang.String, android.support.v7.widget.AppCompatDrawableManager$InflateDelegate> r2 = r9.k
            java.lang.Object r0 = r2.get(r0)
            if (r0 != 0) goto L_0x0033
        L_0x002b:
            return r1
        L_0x002c:
            android.support.v4.util.SparseArrayCompat r0 = new android.support.v4.util.SparseArrayCompat
            r0.<init>()
            r9.l = r0
        L_0x0033:
            android.util.TypedValue r0 = r9.o
            if (r0 != 0) goto L_0x003e
            android.util.TypedValue r0 = new android.util.TypedValue
            r0.<init>()
            r9.o = r0
        L_0x003e:
            android.util.TypedValue r0 = r9.o
            android.content.res.Resources r1 = r10.getResources()
            r2 = 1
            r1.getValue(r11, r0, r2)
            long r3 = a(r0)
            android.graphics.drawable.Drawable r5 = r9.a(r10, r3)
            if (r5 == 0) goto L_0x0053
            return r5
        L_0x0053:
            java.lang.CharSequence r6 = r0.string
            if (r6 == 0) goto L_0x00b0
            java.lang.CharSequence r6 = r0.string
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = ".xml"
            boolean r6 = r6.endsWith(r7)
            if (r6 == 0) goto L_0x00b0
            android.content.res.XmlResourceParser r1 = r1.getXml(r11)     // Catch:{ Exception -> 0x00a8 }
            android.util.AttributeSet r6 = android.util.Xml.asAttributeSet(r1)     // Catch:{ Exception -> 0x00a8 }
        L_0x006d:
            int r7 = r1.next()     // Catch:{ Exception -> 0x00a8 }
            r8 = 2
            if (r7 == r8) goto L_0x0077
            if (r7 == r2) goto L_0x0077
            goto L_0x006d
        L_0x0077:
            if (r7 == r8) goto L_0x0081
            org.xmlpull.v1.XmlPullParserException r10 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r0 = "No start tag found"
            r10.<init>(r0)     // Catch:{ Exception -> 0x00a8 }
            throw r10     // Catch:{ Exception -> 0x00a8 }
        L_0x0081:
            java.lang.String r2 = r1.getName()     // Catch:{ Exception -> 0x00a8 }
            android.support.v4.util.SparseArrayCompat<java.lang.String> r7 = r9.l     // Catch:{ Exception -> 0x00a8 }
            r7.append(r11, r2)     // Catch:{ Exception -> 0x00a8 }
            android.support.v4.util.ArrayMap<java.lang.String, android.support.v7.widget.AppCompatDrawableManager$InflateDelegate> r7 = r9.k     // Catch:{ Exception -> 0x00a8 }
            java.lang.Object r2 = r7.get(r2)     // Catch:{ Exception -> 0x00a8 }
            android.support.v7.widget.AppCompatDrawableManager$InflateDelegate r2 = (android.support.v7.widget.AppCompatDrawableManager.InflateDelegate) r2     // Catch:{ Exception -> 0x00a8 }
            if (r2 == 0) goto L_0x009d
            android.content.res.Resources$Theme r7 = r10.getTheme()     // Catch:{ Exception -> 0x00a8 }
            android.graphics.drawable.Drawable r1 = r2.a(r10, r1, r6, r7)     // Catch:{ Exception -> 0x00a8 }
            r5 = r1
        L_0x009d:
            if (r5 == 0) goto L_0x00b0
            int r0 = r0.changingConfigurations     // Catch:{ Exception -> 0x00a8 }
            r5.setChangingConfigurations(r0)     // Catch:{ Exception -> 0x00a8 }
            r9.a(r10, r3, r5)     // Catch:{ Exception -> 0x00a8 }
            goto L_0x00b0
        L_0x00a8:
            r10 = move-exception
            java.lang.String r0 = "AppCompatDrawableManag"
            java.lang.String r1 = "Exception while inflating drawable"
            android.util.Log.e(r0, r1, r10)
        L_0x00b0:
            if (r5 != 0) goto L_0x00b9
            android.support.v4.util.SparseArrayCompat<java.lang.String> r10 = r9.l
            java.lang.String r0 = "appcompat_skip_skip"
            r10.append(r11, r0)
        L_0x00b9:
            return r5
        L_0x00ba:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatDrawableManager.c(android.content.Context, int):android.graphics.drawable.Drawable");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable a(@android.support.annotation.NonNull android.content.Context r5, long r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.m
            monitor-enter(r0)
            java.util.WeakHashMap<android.content.Context, android.support.v4.util.LongSparseArray<java.lang.ref.WeakReference<android.graphics.drawable.Drawable$ConstantState>>> r1 = r4.n     // Catch:{ all -> 0x002f }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x002f }
            android.support.v4.util.LongSparseArray r1 = (android.support.v4.util.LongSparseArray) r1     // Catch:{ all -> 0x002f }
            r2 = 0
            if (r1 != 0) goto L_0x0010
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return r2
        L_0x0010:
            java.lang.Object r3 = r1.get(r6)     // Catch:{ all -> 0x002f }
            java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3     // Catch:{ all -> 0x002f }
            if (r3 == 0) goto L_0x002d
            java.lang.Object r3 = r3.get()     // Catch:{ all -> 0x002f }
            android.graphics.drawable.Drawable$ConstantState r3 = (android.graphics.drawable.Drawable.ConstantState) r3     // Catch:{ all -> 0x002f }
            if (r3 == 0) goto L_0x002a
            android.content.res.Resources r5 = r5.getResources()     // Catch:{ all -> 0x002f }
            android.graphics.drawable.Drawable r5 = r3.newDrawable(r5)     // Catch:{ all -> 0x002f }
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return r5
        L_0x002a:
            r1.delete(r6)     // Catch:{ all -> 0x002f }
        L_0x002d:
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return r2
        L_0x002f:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatDrawableManager.a(android.content.Context, long):android.graphics.drawable.Drawable");
    }

    private boolean a(@NonNull Context context, long j2, @NonNull Drawable drawable) {
        ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return false;
        }
        synchronized (this.m) {
            LongSparseArray longSparseArray = (LongSparseArray) this.n.get(context);
            if (longSparseArray == null) {
                longSparseArray = new LongSparseArray();
                this.n.put(context, longSparseArray);
            }
            longSparseArray.put(j2, new WeakReference(constantState));
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public Drawable a(@NonNull Context context, @NonNull VectorEnabledTintResources vectorEnabledTintResources, @DrawableRes int i2) {
        Drawable c2 = c(context, i2);
        if (c2 == null) {
            c2 = vectorEnabledTintResources.a(i2);
        }
        if (c2 != null) {
            return a(context, i2, false, c2);
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0061 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean a(@android.support.annotation.NonNull android.content.Context r6, @android.support.annotation.DrawableRes int r7, @android.support.annotation.NonNull android.graphics.drawable.Drawable r8) {
        /*
            android.graphics.PorterDuff$Mode r0 = a
            int[] r1 = d
            boolean r1 = a(r1, r7)
            r2 = 16842801(0x1010031, float:2.3693695E-38)
            r3 = -1
            r4 = 0
            r5 = 1
            if (r1 == 0) goto L_0x0015
            int r2 = android.support.v7.appcompat.R.attr.colorControlNormal
        L_0x0012:
            r7 = 1
            r1 = -1
            goto L_0x0044
        L_0x0015:
            int[] r1 = f
            boolean r1 = a(r1, r7)
            if (r1 == 0) goto L_0x0020
            int r2 = android.support.v7.appcompat.R.attr.colorControlActivated
            goto L_0x0012
        L_0x0020:
            int[] r1 = g
            boolean r1 = a(r1, r7)
            if (r1 == 0) goto L_0x002b
            android.graphics.PorterDuff$Mode r0 = android.graphics.PorterDuff.Mode.MULTIPLY
            goto L_0x0012
        L_0x002b:
            int r1 = android.support.v7.appcompat.R.drawable.abc_list_divider_mtrl_alpha
            if (r7 != r1) goto L_0x003c
            r2 = 16842800(0x1010030, float:2.3693693E-38)
            r7 = 1109603123(0x42233333, float:40.8)
            int r7 = java.lang.Math.round(r7)
            r1 = r7
            r7 = 1
            goto L_0x0044
        L_0x003c:
            int r1 = android.support.v7.appcompat.R.drawable.abc_dialog_material_background
            if (r7 != r1) goto L_0x0041
            goto L_0x0012
        L_0x0041:
            r7 = 0
            r1 = -1
            r2 = 0
        L_0x0044:
            if (r7 == 0) goto L_0x0061
            boolean r7 = android.support.v7.widget.DrawableUtils.canSafelyMutateDrawable(r8)
            if (r7 == 0) goto L_0x0050
            android.graphics.drawable.Drawable r8 = r8.mutate()
        L_0x0050:
            int r6 = android.support.v7.widget.ThemeUtils.a(r6, r2)
            android.graphics.PorterDuffColorFilter r6 = getPorterDuffColorFilter(r6, r0)
            r8.setColorFilter(r6)
            if (r1 == r3) goto L_0x0060
            r8.setAlpha(r1)
        L_0x0060:
            return r5
        L_0x0061:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatDrawableManager.a(android.content.Context, int, android.graphics.drawable.Drawable):boolean");
    }

    private void a(@NonNull String str, @NonNull InflateDelegate inflateDelegate) {
        if (this.k == null) {
            this.k = new ArrayMap<>();
        }
        this.k.put(str, inflateDelegate);
    }

    private static boolean a(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    static Mode a(int i2) {
        if (i2 == R.drawable.abc_switch_thumb_material) {
            return Mode.MULTIPLY;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList a(@NonNull Context context, @DrawableRes int i2) {
        ColorStateList d2 = d(context, i2);
        if (d2 == null) {
            if (i2 == R.drawable.abc_edit_text_material) {
                d2 = AppCompatResources.getColorStateList(context, R.color.abc_tint_edittext);
            } else if (i2 == R.drawable.abc_switch_track_mtrl_alpha) {
                d2 = AppCompatResources.getColorStateList(context, R.color.abc_tint_switch_track);
            } else if (i2 == R.drawable.abc_switch_thumb_material) {
                d2 = d(context);
            } else if (i2 == R.drawable.abc_btn_default_mtrl_shape) {
                d2 = a(context);
            } else if (i2 == R.drawable.abc_btn_borderless_material) {
                d2 = b(context);
            } else if (i2 == R.drawable.abc_btn_colored_material) {
                d2 = c(context);
            } else if (i2 == R.drawable.abc_spinner_mtrl_am_alpha || i2 == R.drawable.abc_spinner_textfield_background_material) {
                d2 = AppCompatResources.getColorStateList(context, R.color.abc_tint_spinner);
            } else if (a(e, i2)) {
                d2 = ThemeUtils.b(context, R.attr.colorControlNormal);
            } else if (a(h, i2)) {
                d2 = AppCompatResources.getColorStateList(context, R.color.abc_tint_default);
            } else if (a(i, i2)) {
                d2 = AppCompatResources.getColorStateList(context, R.color.abc_tint_btn_checkable);
            } else if (i2 == R.drawable.abc_seekbar_thumb_material) {
                d2 = AppCompatResources.getColorStateList(context, R.color.abc_tint_seek_thumb);
            }
            if (d2 != null) {
                a(context, i2, d2);
            }
        }
        return d2;
    }

    private ColorStateList d(@NonNull Context context, @DrawableRes int i2) {
        ColorStateList colorStateList = null;
        if (this.j == null) {
            return null;
        }
        SparseArrayCompat sparseArrayCompat = (SparseArrayCompat) this.j.get(context);
        if (sparseArrayCompat != null) {
            colorStateList = (ColorStateList) sparseArrayCompat.get(i2);
        }
        return colorStateList;
    }

    private void a(@NonNull Context context, @DrawableRes int i2, @NonNull ColorStateList colorStateList) {
        if (this.j == null) {
            this.j = new WeakHashMap<>();
        }
        SparseArrayCompat sparseArrayCompat = (SparseArrayCompat) this.j.get(context);
        if (sparseArrayCompat == null) {
            sparseArrayCompat = new SparseArrayCompat();
            this.j.put(context, sparseArrayCompat);
        }
        sparseArrayCompat.append(i2, colorStateList);
    }

    private ColorStateList a(@NonNull Context context) {
        return e(context, ThemeUtils.a(context, R.attr.colorButtonNormal));
    }

    private ColorStateList b(@NonNull Context context) {
        return e(context, 0);
    }

    private ColorStateList c(@NonNull Context context) {
        return e(context, ThemeUtils.a(context, R.attr.colorAccent));
    }

    private ColorStateList e(@NonNull Context context, @ColorInt int i2) {
        int a2 = ThemeUtils.a(context, R.attr.colorControlHighlight);
        return new ColorStateList(new int[][]{ThemeUtils.a, ThemeUtils.d, ThemeUtils.b, ThemeUtils.h}, new int[]{ThemeUtils.c(context, R.attr.colorButtonNormal), ColorUtils.compositeColors(a2, i2), ColorUtils.compositeColors(a2, i2), i2});
    }

    private ColorStateList d(Context context) {
        int[][] iArr = new int[3][];
        int[] iArr2 = new int[3];
        ColorStateList b2 = ThemeUtils.b(context, R.attr.colorSwitchThumbNormal);
        if (b2 == null || !b2.isStateful()) {
            iArr[0] = ThemeUtils.a;
            iArr2[0] = ThemeUtils.c(context, R.attr.colorSwitchThumbNormal);
            iArr[1] = ThemeUtils.e;
            iArr2[1] = ThemeUtils.a(context, R.attr.colorControlActivated);
            iArr[2] = ThemeUtils.h;
            iArr2[2] = ThemeUtils.a(context, R.attr.colorSwitchThumbNormal);
        } else {
            iArr[0] = ThemeUtils.a;
            iArr2[0] = b2.getColorForState(iArr[0], 0);
            iArr[1] = ThemeUtils.e;
            iArr2[1] = ThemeUtils.a(context, R.attr.colorControlActivated);
            iArr[2] = ThemeUtils.h;
            iArr2[2] = b2.getDefaultColor();
        }
        return new ColorStateList(iArr, iArr2);
    }

    static void a(Drawable drawable, TintInfo tintInfo, int[] iArr) {
        if (!DrawableUtils.canSafelyMutateDrawable(drawable) || drawable.mutate() == drawable) {
            if (tintInfo.d || tintInfo.c) {
                drawable.setColorFilter(a(tintInfo.d ? tintInfo.a : null, tintInfo.c ? tintInfo.b : a, iArr));
            } else {
                drawable.clearColorFilter();
            }
            if (VERSION.SDK_INT <= 23) {
                drawable.invalidateSelf();
            }
            return;
        }
        Log.d("AppCompatDrawableManag", "Mutated drawable is not the same instance as the input.");
    }

    private static PorterDuffColorFilter a(ColorStateList colorStateList, Mode mode, int[] iArr) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return getPorterDuffColorFilter(colorStateList.getColorForState(iArr, 0), mode);
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int i2, Mode mode) {
        PorterDuffColorFilter a2 = c.a(i2, mode);
        if (a2 != null) {
            return a2;
        }
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(i2, mode);
        c.a(i2, mode, porterDuffColorFilter);
        return porterDuffColorFilter;
    }

    private static void a(Drawable drawable, int i2, Mode mode) {
        if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
            drawable = drawable.mutate();
        }
        if (mode == null) {
            mode = a;
        }
        drawable.setColorFilter(getPorterDuffColorFilter(i2, mode));
    }

    private void e(@NonNull Context context) {
        if (!this.p) {
            this.p = true;
            Drawable drawable = getDrawable(context, R.drawable.abc_vector_test);
            if (drawable == null || !a(drawable)) {
                this.p = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
    }

    private static boolean a(@NonNull Drawable drawable) {
        return (drawable instanceof VectorDrawableCompat) || "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName());
    }
}
