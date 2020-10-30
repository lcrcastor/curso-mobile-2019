package android.support.v4.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.v4.os.BuildCompat;
import android.text.Editable;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    static final TextViewCompatBaseImpl a;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoSizeTextType {
    }

    @RequiresApi(16)
    static class TextViewCompatApi16Impl extends TextViewCompatBaseImpl {
        TextViewCompatApi16Impl() {
        }

        public int a(TextView textView) {
            return textView.getMaxLines();
        }

        public int b(TextView textView) {
            return textView.getMinLines();
        }
    }

    @RequiresApi(17)
    static class TextViewCompatApi17Impl extends TextViewCompatApi16Impl {
        TextViewCompatApi17Impl() {
        }

        public void a(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            Drawable drawable5 = z ? drawable3 : drawable;
            if (!z) {
                drawable = drawable3;
            }
            textView.setCompoundDrawables(drawable5, drawable2, drawable, drawable4);
        }

        public void b(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            Drawable drawable5 = z ? drawable3 : drawable;
            if (!z) {
                drawable = drawable3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable5, drawable2, drawable, drawable4);
        }

        public void a(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            int i5 = z ? i3 : i;
            if (!z) {
                i = i3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(i5, i2, i, i4);
        }

        public Drawable[] c(@NonNull TextView textView) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            if (z) {
                Drawable drawable = compoundDrawables[2];
                Drawable drawable2 = compoundDrawables[0];
                compoundDrawables[0] = drawable;
                compoundDrawables[2] = drawable2;
            }
            return compoundDrawables;
        }
    }

    @RequiresApi(18)
    static class TextViewCompatApi18Impl extends TextViewCompatApi17Impl {
        TextViewCompatApi18Impl() {
        }

        public void a(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        }

        public void b(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }

        public void a(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
        }

        public Drawable[] c(@NonNull TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }
    }

    @RequiresApi(23)
    static class TextViewCompatApi23Impl extends TextViewCompatApi18Impl {
        TextViewCompatApi23Impl() {
        }

        public void a(@NonNull TextView textView, @StyleRes int i) {
            textView.setTextAppearance(i);
        }
    }

    @RequiresApi(26)
    static class TextViewCompatApi26Impl extends TextViewCompatApi23Impl {
        TextViewCompatApi26Impl() {
        }

        public void a(final TextView textView, final Callback callback) {
            if (VERSION.SDK_INT == 26 || VERSION.SDK_INT == 27) {
                textView.setCustomSelectionActionModeCallback(new Callback() {
                    private Class d;
                    private Method e;
                    private boolean f;
                    private boolean g = false;

                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        return callback.onCreateActionMode(actionMode, menu);
                    }

                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        a(menu);
                        return callback.onPrepareActionMode(actionMode, menu);
                    }

                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                        return callback.onActionItemClicked(actionMode, menuItem);
                    }

                    public void onDestroyActionMode(ActionMode actionMode) {
                        callback.onDestroyActionMode(actionMode);
                    }

                    private void a(Menu menu) {
                        Method method;
                        Context context = textView.getContext();
                        PackageManager packageManager = context.getPackageManager();
                        if (!this.g) {
                            this.g = true;
                            try {
                                this.d = Class.forName("com.android.internal.view.menu.MenuBuilder");
                                this.e = this.d.getDeclaredMethod("removeItemAt", new Class[]{Integer.TYPE});
                                this.f = true;
                            } catch (ClassNotFoundException | NoSuchMethodException unused) {
                                this.d = null;
                                this.e = null;
                                this.f = false;
                            }
                        }
                        try {
                            if (!this.f || !this.d.isInstance(menu)) {
                                method = menu.getClass().getDeclaredMethod("removeItemAt", new Class[]{Integer.TYPE});
                            } else {
                                method = this.e;
                            }
                            for (int size = menu.size() - 1; size >= 0; size--) {
                                MenuItem item = menu.getItem(size);
                                if (item.getIntent() != null && "android.intent.action.PROCESS_TEXT".equals(item.getIntent().getAction())) {
                                    method.invoke(menu, new Object[]{Integer.valueOf(size)});
                                }
                            }
                            List a2 = a(context, packageManager);
                            for (int i = 0; i < a2.size(); i++) {
                                ResolveInfo resolveInfo = (ResolveInfo) a2.get(i);
                                menu.add(0, 0, i + 100, resolveInfo.loadLabel(packageManager)).setIntent(a(resolveInfo, textView)).setShowAsAction(1);
                            }
                        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused2) {
                        }
                    }

                    private List<ResolveInfo> a(Context context, PackageManager packageManager) {
                        ArrayList arrayList = new ArrayList();
                        if (!(context instanceof Activity)) {
                            return arrayList;
                        }
                        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(a(), 0)) {
                            if (a(resolveInfo, context)) {
                                arrayList.add(resolveInfo);
                            }
                        }
                        return arrayList;
                    }

                    private boolean a(ResolveInfo resolveInfo, Context context) {
                        boolean z = true;
                        if (context.getPackageName().equals(resolveInfo.activityInfo.packageName)) {
                            return true;
                        }
                        if (!resolveInfo.activityInfo.exported) {
                            return false;
                        }
                        if (!(resolveInfo.activityInfo.permission == null || context.checkSelfPermission(resolveInfo.activityInfo.permission) == 0)) {
                            z = false;
                        }
                        return z;
                    }

                    private Intent a(ResolveInfo resolveInfo, TextView textView) {
                        return a().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", !a(textView)).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    }

                    private boolean a(TextView textView) {
                        return (textView instanceof Editable) && textView.onCheckIsTextEditor() && textView.isEnabled();
                    }

                    private Intent a() {
                        return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType(HTTP.PLAIN_TEXT_TYPE);
                    }
                });
            } else {
                super.a(textView, callback);
            }
        }
    }

    @RequiresApi(27)
    static class TextViewCompatApi27Impl extends TextViewCompatApi26Impl {
        TextViewCompatApi27Impl() {
        }

        public void b(TextView textView, int i) {
            textView.setAutoSizeTextTypeWithDefaults(i);
        }

        public void b(TextView textView, int i, int i2, int i3, int i4) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        }

        public void a(TextView textView, @NonNull int[] iArr, int i) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        }

        public int d(TextView textView) {
            return textView.getAutoSizeTextType();
        }

        public int e(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        public int f(TextView textView) {
            return textView.getAutoSizeMinTextSize();
        }

        public int g(TextView textView) {
            return textView.getAutoSizeMaxTextSize();
        }

        public int[] h(TextView textView) {
            return textView.getAutoSizeTextAvailableSizes();
        }
    }

    static class TextViewCompatBaseImpl {
        private static Field a;
        private static boolean b;
        private static Field c;
        private static boolean d;
        private static Field e;
        private static boolean f;
        private static Field g;
        private static boolean h;

        TextViewCompatBaseImpl() {
        }

        public void a(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        }

        public void b(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }

        public void a(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
            textView.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
        }

        private static Field a(String str) {
            Field field;
            try {
                field = TextView.class.getDeclaredField(str);
                try {
                    field.setAccessible(true);
                } catch (NoSuchFieldException unused) {
                }
            } catch (NoSuchFieldException unused2) {
                field = null;
                StringBuilder sb = new StringBuilder();
                sb.append("Could not retrieve ");
                sb.append(str);
                sb.append(" field.");
                Log.e("TextViewCompatBase", sb.toString());
                return field;
            }
            return field;
        }

        private static int a(Field field, TextView textView) {
            try {
                return field.getInt(textView);
            } catch (IllegalAccessException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("Could not retrieve value of ");
                sb.append(field.getName());
                sb.append(" field.");
                Log.d("TextViewCompatBase", sb.toString());
                return -1;
            }
        }

        public int a(TextView textView) {
            if (!d) {
                c = a("mMaxMode");
                d = true;
            }
            if (c != null && a(c, textView) == 1) {
                if (!b) {
                    a = a("mMaximum");
                    b = true;
                }
                if (a != null) {
                    return a(a, textView);
                }
            }
            return -1;
        }

        public int b(TextView textView) {
            if (!h) {
                g = a("mMinMode");
                h = true;
            }
            if (g != null && a(g, textView) == 1) {
                if (!f) {
                    e = a("mMinimum");
                    f = true;
                }
                if (e != null) {
                    return a(e, textView);
                }
            }
            return -1;
        }

        public void a(TextView textView, @StyleRes int i) {
            textView.setTextAppearance(textView.getContext(), i);
        }

        public Drawable[] c(@NonNull TextView textView) {
            return textView.getCompoundDrawables();
        }

        public void b(TextView textView, int i) {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeWithDefaults(i);
            }
        }

        public void b(TextView textView, int i, int i2, int i3, int i4) {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
            }
        }

        public void a(TextView textView, @NonNull int[] iArr, int i) {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
            }
        }

        public int d(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeTextType();
            }
            return 0;
        }

        public int e(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeStepGranularity();
            }
            return -1;
        }

        public int f(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMinTextSize();
            }
            return -1;
        }

        public int g(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMaxTextSize();
            }
            return -1;
        }

        public int[] h(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeTextAvailableSizes();
            }
            return new int[0];
        }

        public void a(TextView textView, Callback callback) {
            textView.setCustomSelectionActionModeCallback(callback);
        }
    }

    private TextViewCompat() {
    }

    static {
        if (BuildCompat.isAtLeastOMR1()) {
            a = new TextViewCompatApi27Impl();
        } else if (VERSION.SDK_INT >= 26) {
            a = new TextViewCompatApi26Impl();
        } else if (VERSION.SDK_INT >= 23) {
            a = new TextViewCompatApi23Impl();
        } else if (VERSION.SDK_INT >= 18) {
            a = new TextViewCompatApi18Impl();
        } else if (VERSION.SDK_INT >= 17) {
            a = new TextViewCompatApi17Impl();
        } else if (VERSION.SDK_INT >= 16) {
            a = new TextViewCompatApi16Impl();
        } else {
            a = new TextViewCompatBaseImpl();
        }
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        a.a(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        a.b(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
        a.a(textView, i, i2, i3, i4);
    }

    public static int getMaxLines(@NonNull TextView textView) {
        return a.a(textView);
    }

    public static int getMinLines(@NonNull TextView textView) {
        return a.b(textView);
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int i) {
        a.a(textView, i);
    }

    @NonNull
    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        return a.c(textView);
    }

    public static void setAutoSizeTextTypeWithDefaults(@NonNull TextView textView, int i) {
        a.b(textView, i);
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(@NonNull TextView textView, int i, int i2, int i3, int i4) {
        a.b(textView, i, i2, i3, i4);
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull TextView textView, @NonNull int[] iArr, int i) {
        a.a(textView, iArr, i);
    }

    public static int getAutoSizeTextType(@NonNull TextView textView) {
        return a.d(textView);
    }

    public static int getAutoSizeStepGranularity(@NonNull TextView textView) {
        return a.e(textView);
    }

    public static int getAutoSizeMinTextSize(@NonNull TextView textView) {
        return a.f(textView);
    }

    public static int getAutoSizeMaxTextSize(@NonNull TextView textView) {
        return a.g(textView);
    }

    @NonNull
    public static int[] getAutoSizeTextAvailableSizes(@NonNull TextView textView) {
        return a.h(textView);
    }

    public static void setCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull Callback callback) {
        a.a(textView, callback);
    }
}
