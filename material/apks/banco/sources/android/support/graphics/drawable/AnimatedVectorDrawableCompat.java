package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.Animatable2Compat.AnimationCallback;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

public class AnimatedVectorDrawableCompat extends VectorDrawableCommon implements Animatable2Compat {
    AnimatedVectorDrawableDelegateState a;
    final Callback b;
    private AnimatedVectorDrawableCompatState d;
    private Context e;
    private ArgbEvaluator f;
    private AnimatorListener g;
    /* access modifiers changed from: private */
    public ArrayList<AnimationCallback> h;

    static class AnimatedVectorDrawableCompatState extends ConstantState {
        int a;
        VectorDrawableCompat b;
        AnimatorSet c;
        ArrayMap<Animator, String> d;
        /* access modifiers changed from: private */
        public ArrayList<Animator> e;

        public AnimatedVectorDrawableCompatState(Context context, AnimatedVectorDrawableCompatState animatedVectorDrawableCompatState, Callback callback, Resources resources) {
            if (animatedVectorDrawableCompatState != null) {
                this.a = animatedVectorDrawableCompatState.a;
                if (animatedVectorDrawableCompatState.b != null) {
                    ConstantState constantState = animatedVectorDrawableCompatState.b.getConstantState();
                    if (resources != null) {
                        this.b = (VectorDrawableCompat) constantState.newDrawable(resources);
                    } else {
                        this.b = (VectorDrawableCompat) constantState.newDrawable();
                    }
                    this.b = (VectorDrawableCompat) this.b.mutate();
                    this.b.setCallback(callback);
                    this.b.setBounds(animatedVectorDrawableCompatState.b.getBounds());
                    this.b.a(false);
                }
                if (animatedVectorDrawableCompatState.e != null) {
                    int size = animatedVectorDrawableCompatState.e.size();
                    this.e = new ArrayList<>(size);
                    this.d = new ArrayMap<>(size);
                    for (int i = 0; i < size; i++) {
                        Animator animator = (Animator) animatedVectorDrawableCompatState.e.get(i);
                        Animator clone = animator.clone();
                        String str = (String) animatedVectorDrawableCompatState.d.get(animator);
                        clone.setTarget(this.b.a(str));
                        this.e.add(clone);
                        this.d.put(clone, str);
                    }
                    a();
                }
            }
        }

        public Drawable newDrawable() {
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }

        public Drawable newDrawable(Resources resources) {
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }

        public int getChangingConfigurations() {
            return this.a;
        }

        public void a() {
            if (this.c == null) {
                this.c = new AnimatorSet();
            }
            this.c.playTogether(this.e);
        }
    }

    @RequiresApi(24)
    static class AnimatedVectorDrawableDelegateState extends ConstantState {
        private final ConstantState a;

        public AnimatedVectorDrawableDelegateState(ConstantState constantState) {
            this.a = constantState;
        }

        public Drawable newDrawable() {
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat = new AnimatedVectorDrawableCompat();
            animatedVectorDrawableCompat.c = this.a.newDrawable();
            animatedVectorDrawableCompat.c.setCallback(animatedVectorDrawableCompat.b);
            return animatedVectorDrawableCompat;
        }

        public Drawable newDrawable(Resources resources) {
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat = new AnimatedVectorDrawableCompat();
            animatedVectorDrawableCompat.c = this.a.newDrawable(resources);
            animatedVectorDrawableCompat.c.setCallback(animatedVectorDrawableCompat.b);
            return animatedVectorDrawableCompat;
        }

        public Drawable newDrawable(Resources resources, Theme theme) {
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat = new AnimatedVectorDrawableCompat();
            animatedVectorDrawableCompat.c = this.a.newDrawable(resources, theme);
            animatedVectorDrawableCompat.c.setCallback(animatedVectorDrawableCompat.b);
            return animatedVectorDrawableCompat;
        }

        public boolean canApplyTheme() {
            return this.a.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.a.getChangingConfigurations();
        }
    }

    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i) {
        super.setChangingConfigurations(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(int i, Mode mode) {
        super.setColorFilter(i, mode);
    }

    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    public /* bridge */ /* synthetic */ void setHotspot(float f2, float f3) {
        super.setHotspot(f2, f3);
    }

    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    AnimatedVectorDrawableCompat() {
        this(null, null, null);
    }

    private AnimatedVectorDrawableCompat(@Nullable Context context) {
        this(context, null, null);
    }

    private AnimatedVectorDrawableCompat(@Nullable Context context, @Nullable AnimatedVectorDrawableCompatState animatedVectorDrawableCompatState, @Nullable Resources resources) {
        this.f = null;
        this.g = null;
        this.h = null;
        this.b = new Callback() {
            public void invalidateDrawable(Drawable drawable) {
                AnimatedVectorDrawableCompat.this.invalidateSelf();
            }

            public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                AnimatedVectorDrawableCompat.this.scheduleSelf(runnable, j);
            }

            public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                AnimatedVectorDrawableCompat.this.unscheduleSelf(runnable);
            }
        };
        this.e = context;
        if (animatedVectorDrawableCompatState != null) {
            this.d = animatedVectorDrawableCompatState;
        } else {
            this.d = new AnimatedVectorDrawableCompatState(context, animatedVectorDrawableCompatState, this.b, resources);
        }
    }

    public Drawable mutate() {
        if (this.c != null) {
            this.c.mutate();
        }
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0047 A[Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004f A[Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }] */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.support.graphics.drawable.AnimatedVectorDrawableCompat create(@android.support.annotation.NonNull android.content.Context r4, @android.support.annotation.DrawableRes int r5) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 24
            if (r0 < r1) goto L_0x002e
            android.support.graphics.drawable.AnimatedVectorDrawableCompat r0 = new android.support.graphics.drawable.AnimatedVectorDrawableCompat
            r0.<init>(r4)
            android.content.res.Resources r1 = r4.getResources()
            android.content.res.Resources$Theme r4 = r4.getTheme()
            android.graphics.drawable.Drawable r4 = android.support.v4.content.res.ResourcesCompat.getDrawable(r1, r5, r4)
            r0.c = r4
            android.graphics.drawable.Drawable r4 = r0.c
            android.graphics.drawable.Drawable$Callback r5 = r0.b
            r4.setCallback(r5)
            android.support.graphics.drawable.AnimatedVectorDrawableCompat$AnimatedVectorDrawableDelegateState r4 = new android.support.graphics.drawable.AnimatedVectorDrawableCompat$AnimatedVectorDrawableDelegateState
            android.graphics.drawable.Drawable r5 = r0.c
            android.graphics.drawable.Drawable$ConstantState r5 = r5.getConstantState()
            r4.<init>(r5)
            r0.a = r4
            return r0
        L_0x002e:
            android.content.res.Resources r0 = r4.getResources()
            android.content.res.XmlResourceParser r5 = r0.getXml(r5)     // Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r5)     // Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }
        L_0x003a:
            int r1 = r5.next()     // Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }
            r2 = 2
            if (r1 == r2) goto L_0x0045
            r3 = 1
            if (r1 == r3) goto L_0x0045
            goto L_0x003a
        L_0x0045:
            if (r1 == r2) goto L_0x004f
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }
            java.lang.String r5 = "No start tag found"
            r4.<init>(r5)     // Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }
            throw r4     // Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }
        L_0x004f:
            android.content.res.Resources r1 = r4.getResources()     // Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }
            android.content.res.Resources$Theme r2 = r4.getTheme()     // Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }
            android.support.graphics.drawable.AnimatedVectorDrawableCompat r4 = createFromXmlInner(r4, r1, r5, r0, r2)     // Catch:{ XmlPullParserException -> 0x0065, IOException -> 0x005c }
            return r4
        L_0x005c:
            r4 = move-exception
            java.lang.String r5 = "AnimatedVDCompat"
            java.lang.String r0 = "parser error"
            android.util.Log.e(r5, r0, r4)
            goto L_0x006d
        L_0x0065:
            r4 = move-exception
            java.lang.String r5 = "AnimatedVDCompat"
            java.lang.String r0 = "parser error"
            android.util.Log.e(r5, r0, r4)
        L_0x006d:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.AnimatedVectorDrawableCompat.create(android.content.Context, int):android.support.graphics.drawable.AnimatedVectorDrawableCompat");
    }

    public static AnimatedVectorDrawableCompat createFromXmlInner(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = new AnimatedVectorDrawableCompat(context);
        animatedVectorDrawableCompat.inflate(resources, xmlPullParser, attributeSet, theme);
        return animatedVectorDrawableCompat;
    }

    public ConstantState getConstantState() {
        if (this.c == null || VERSION.SDK_INT < 24) {
            return null;
        }
        return new AnimatedVectorDrawableDelegateState(this.c.getConstantState());
    }

    public int getChangingConfigurations() {
        if (this.c != null) {
            return this.c.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.d.a;
    }

    public void draw(Canvas canvas) {
        if (this.c != null) {
            this.c.draw(canvas);
            return;
        }
        this.d.b.draw(canvas);
        if (this.d.c.isStarted()) {
            invalidateSelf();
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        if (this.c != null) {
            this.c.setBounds(rect);
        } else {
            this.d.b.setBounds(rect);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        if (this.c != null) {
            return this.c.setState(iArr);
        }
        return this.d.b.setState(iArr);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i) {
        if (this.c != null) {
            return this.c.setLevel(i);
        }
        return this.d.b.setLevel(i);
    }

    public int getAlpha() {
        if (this.c != null) {
            return DrawableCompat.getAlpha(this.c);
        }
        return this.d.b.getAlpha();
    }

    public void setAlpha(int i) {
        if (this.c != null) {
            this.c.setAlpha(i);
        } else {
            this.d.b.setAlpha(i);
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.c != null) {
            this.c.setColorFilter(colorFilter);
        } else {
            this.d.b.setColorFilter(colorFilter);
        }
    }

    public void setTint(int i) {
        if (this.c != null) {
            DrawableCompat.setTint(this.c, i);
        } else {
            this.d.b.setTint(i);
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        if (this.c != null) {
            DrawableCompat.setTintList(this.c, colorStateList);
        } else {
            this.d.b.setTintList(colorStateList);
        }
    }

    public void setTintMode(Mode mode) {
        if (this.c != null) {
            DrawableCompat.setTintMode(this.c, mode);
        } else {
            this.d.b.setTintMode(mode);
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        if (this.c != null) {
            return this.c.setVisible(z, z2);
        }
        this.d.b.setVisible(z, z2);
        return super.setVisible(z, z2);
    }

    public boolean isStateful() {
        if (this.c != null) {
            return this.c.isStateful();
        }
        return this.d.b.isStateful();
    }

    public int getOpacity() {
        if (this.c != null) {
            return this.c.getOpacity();
        }
        return this.d.b.getOpacity();
    }

    public int getIntrinsicWidth() {
        if (this.c != null) {
            return this.c.getIntrinsicWidth();
        }
        return this.d.b.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        if (this.c != null) {
            return this.c.getIntrinsicHeight();
        }
        return this.d.b.getIntrinsicHeight();
    }

    public boolean isAutoMirrored() {
        if (this.c != null) {
            return DrawableCompat.isAutoMirrored(this.c);
        }
        return this.d.b.isAutoMirrored();
    }

    public void setAutoMirrored(boolean z) {
        if (this.c != null) {
            DrawableCompat.setAutoMirrored(this.c, z);
        } else {
            this.d.b.setAutoMirrored(z);
        }
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        if (this.c != null) {
            DrawableCompat.inflate(this.c, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                if ("animated-vector".equals(name)) {
                    TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.e);
                    int resourceId = obtainAttributes.getResourceId(0, 0);
                    if (resourceId != 0) {
                        VectorDrawableCompat create = VectorDrawableCompat.create(resources, resourceId, theme);
                        create.a(false);
                        create.setCallback(this.b);
                        if (this.d.b != null) {
                            this.d.b.setCallback(null);
                        }
                        this.d.b = create;
                    }
                    obtainAttributes.recycle();
                } else if ("target".equals(name)) {
                    TypedArray obtainAttributes2 = resources.obtainAttributes(attributeSet, AndroidResources.f);
                    String string = obtainAttributes2.getString(0);
                    int resourceId2 = obtainAttributes2.getResourceId(1, 0);
                    if (resourceId2 != 0) {
                        if (this.e != null) {
                            a(string, AnimatorInflaterCompat.loadAnimator(this.e, resourceId2));
                        } else {
                            obtainAttributes2.recycle();
                            throw new IllegalStateException("Context can't be null when inflating animators");
                        }
                    }
                    obtainAttributes2.recycle();
                } else {
                    continue;
                }
            }
            eventType = xmlPullParser.next();
        }
        this.d.a();
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        inflate(resources, xmlPullParser, attributeSet, null);
    }

    public void applyTheme(Theme theme) {
        if (this.c != null) {
            DrawableCompat.applyTheme(this.c, theme);
        }
    }

    public boolean canApplyTheme() {
        if (this.c != null) {
            return DrawableCompat.canApplyTheme(this.c);
        }
        return false;
    }

    private void a(Animator animator) {
        if (animator instanceof AnimatorSet) {
            ArrayList childAnimations = ((AnimatorSet) animator).getChildAnimations();
            if (childAnimations != null) {
                for (int i = 0; i < childAnimations.size(); i++) {
                    a((Animator) childAnimations.get(i));
                }
            }
        }
        if (animator instanceof ObjectAnimator) {
            ObjectAnimator objectAnimator = (ObjectAnimator) animator;
            String propertyName = objectAnimator.getPropertyName();
            if ("fillColor".equals(propertyName) || "strokeColor".equals(propertyName)) {
                if (this.f == null) {
                    this.f = new ArgbEvaluator();
                }
                objectAnimator.setEvaluator(this.f);
            }
        }
    }

    private void a(String str, Animator animator) {
        animator.setTarget(this.d.b.a(str));
        if (VERSION.SDK_INT < 21) {
            a(animator);
        }
        if (this.d.e == null) {
            this.d.e = new ArrayList();
            this.d.d = new ArrayMap<>();
        }
        this.d.e.add(animator);
        this.d.d.put(animator, str);
    }

    public boolean isRunning() {
        if (this.c != null) {
            return ((AnimatedVectorDrawable) this.c).isRunning();
        }
        return this.d.c.isRunning();
    }

    public void start() {
        if (this.c != null) {
            ((AnimatedVectorDrawable) this.c).start();
        } else if (!this.d.c.isStarted()) {
            this.d.c.start();
            invalidateSelf();
        }
    }

    public void stop() {
        if (this.c != null) {
            ((AnimatedVectorDrawable) this.c).stop();
        } else {
            this.d.c.end();
        }
    }

    @RequiresApi(23)
    private static boolean a(AnimatedVectorDrawable animatedVectorDrawable, AnimationCallback animationCallback) {
        return animatedVectorDrawable.unregisterAnimationCallback(animationCallback.a());
    }

    public void registerAnimationCallback(@NonNull AnimationCallback animationCallback) {
        if (this.c != null) {
            b((AnimatedVectorDrawable) this.c, animationCallback);
        } else if (animationCallback != null) {
            if (this.h == null) {
                this.h = new ArrayList<>();
            }
            if (!this.h.contains(animationCallback)) {
                this.h.add(animationCallback);
                if (this.g == null) {
                    this.g = new AnimatorListenerAdapter() {
                        public void onAnimationStart(Animator animator) {
                            ArrayList arrayList = new ArrayList(AnimatedVectorDrawableCompat.this.h);
                            int size = arrayList.size();
                            for (int i = 0; i < size; i++) {
                                ((AnimationCallback) arrayList.get(i)).onAnimationStart(AnimatedVectorDrawableCompat.this);
                            }
                        }

                        public void onAnimationEnd(Animator animator) {
                            ArrayList arrayList = new ArrayList(AnimatedVectorDrawableCompat.this.h);
                            int size = arrayList.size();
                            for (int i = 0; i < size; i++) {
                                ((AnimationCallback) arrayList.get(i)).onAnimationEnd(AnimatedVectorDrawableCompat.this);
                            }
                        }
                    };
                }
                this.d.c.addListener(this.g);
            }
        }
    }

    @RequiresApi(23)
    private static void b(@NonNull AnimatedVectorDrawable animatedVectorDrawable, @NonNull AnimationCallback animationCallback) {
        animatedVectorDrawable.registerAnimationCallback(animationCallback.a());
    }

    private void a() {
        if (this.g != null) {
            this.d.c.removeListener(this.g);
            this.g = null;
        }
    }

    public boolean unregisterAnimationCallback(@NonNull AnimationCallback animationCallback) {
        if (this.c != null) {
            a((AnimatedVectorDrawable) this.c, animationCallback);
        }
        if (this.h == null || animationCallback == null) {
            return false;
        }
        boolean remove = this.h.remove(animationCallback);
        if (this.h.size() == 0) {
            a();
        }
        return remove;
    }

    public void clearAnimationCallbacks() {
        if (this.c != null) {
            ((AnimatedVectorDrawable) this.c).clearAnimationCallbacks();
            return;
        }
        a();
        if (this.h != null) {
            this.h.clear();
        }
    }

    public static void registerAnimationCallback(Drawable drawable, AnimationCallback animationCallback) {
        if (drawable != null && animationCallback != null && (drawable instanceof Animatable)) {
            if (VERSION.SDK_INT >= 24) {
                b((AnimatedVectorDrawable) drawable, animationCallback);
            } else {
                ((AnimatedVectorDrawableCompat) drawable).registerAnimationCallback(animationCallback);
            }
        }
    }

    public static boolean unregisterAnimationCallback(Drawable drawable, AnimationCallback animationCallback) {
        if (drawable == null || animationCallback == null || !(drawable instanceof Animatable)) {
            return false;
        }
        if (VERSION.SDK_INT >= 24) {
            return a((AnimatedVectorDrawable) drawable, animationCallback);
        }
        return ((AnimatedVectorDrawableCompat) drawable).unregisterAnimationCallback(animationCallback);
    }

    public static void clearAnimationCallbacks(Drawable drawable) {
        if (drawable != null && (drawable instanceof Animatable)) {
            if (VERSION.SDK_INT >= 24) {
                ((AnimatedVectorDrawable) drawable).clearAnimationCallbacks();
            } else {
                ((AnimatedVectorDrawableCompat) drawable).clearAnimationCallbacks();
            }
        }
    }
}
