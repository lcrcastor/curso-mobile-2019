package android.support.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.VectorDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.support.v4.graphics.PathParser.PathDataNode;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VectorDrawableCompat extends VectorDrawableCommon {
    static final Mode a = Mode.SRC_IN;
    private VectorDrawableCompatState b;
    private PorterDuffColorFilter d;
    private ColorFilter e;
    private boolean f;
    private boolean g;
    private ConstantState h;
    private final float[] i;
    private final Matrix j;
    private final Rect k;

    static class VClipPath extends VPath {
        public boolean a() {
            return true;
        }

        public VClipPath() {
        }

        public VClipPath(VClipPath vClipPath) {
            super(vClipPath);
        }

        public void a(Resources resources, AttributeSet attributeSet, Theme theme, XmlPullParser xmlPullParser) {
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.d);
                a(obtainAttributes);
                obtainAttributes.recycle();
            }
        }

        private void a(TypedArray typedArray) {
            String string = typedArray.getString(0);
            if (string != null) {
                this.n = string;
            }
            String string2 = typedArray.getString(1);
            if (string2 != null) {
                this.m = PathParser.createNodesFromPathData(string2);
            }
        }
    }

    static class VFullPath extends VPath {
        int a = 0;
        float b = BitmapDescriptorFactory.HUE_RED;
        int c = 0;
        float d = 1.0f;
        int e = 0;
        float f = 1.0f;
        float g = BitmapDescriptorFactory.HUE_RED;
        float h = 1.0f;
        float i = BitmapDescriptorFactory.HUE_RED;
        Cap j = Cap.BUTT;
        Join k = Join.MITER;
        float l = 4.0f;
        private int[] p;

        public VFullPath() {
        }

        public VFullPath(VFullPath vFullPath) {
            super(vFullPath);
            this.p = vFullPath.p;
            this.a = vFullPath.a;
            this.b = vFullPath.b;
            this.d = vFullPath.d;
            this.c = vFullPath.c;
            this.e = vFullPath.e;
            this.f = vFullPath.f;
            this.g = vFullPath.g;
            this.h = vFullPath.h;
            this.i = vFullPath.i;
            this.j = vFullPath.j;
            this.k = vFullPath.k;
            this.l = vFullPath.l;
        }

        private Cap a(int i2, Cap cap) {
            switch (i2) {
                case 0:
                    return Cap.BUTT;
                case 1:
                    return Cap.ROUND;
                case 2:
                    return Cap.SQUARE;
                default:
                    return cap;
            }
        }

        private Join a(int i2, Join join) {
            switch (i2) {
                case 0:
                    return Join.MITER;
                case 1:
                    return Join.ROUND;
                case 2:
                    return Join.BEVEL;
                default:
                    return join;
            }
        }

        public void a(Resources resources, AttributeSet attributeSet, Theme theme, XmlPullParser xmlPullParser) {
            TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.c);
            a(obtainAttributes, xmlPullParser);
            obtainAttributes.recycle();
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.p = null;
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                String string = typedArray.getString(0);
                if (string != null) {
                    this.n = string;
                }
                String string2 = typedArray.getString(2);
                if (string2 != null) {
                    this.m = PathParser.createNodesFromPathData(string2);
                }
                this.c = TypedArrayUtils.getNamedColor(typedArray, xmlPullParser, "fillColor", 1, this.c);
                this.f = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "fillAlpha", 12, this.f);
                this.j = a(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.j);
                this.k = a(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.k);
                this.l = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.l);
                this.a = TypedArrayUtils.getNamedColor(typedArray, xmlPullParser, "strokeColor", 3, this.a);
                this.d = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeAlpha", 11, this.d);
                this.b = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeWidth", 4, this.b);
                this.h = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathEnd", 6, this.h);
                this.i = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathOffset", 7, this.i);
                this.g = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathStart", 5, this.g);
                this.e = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 13, this.e);
            }
        }

        /* access modifiers changed from: 0000 */
        public int getStrokeColor() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public void setStrokeColor(int i2) {
            this.a = i2;
        }

        /* access modifiers changed from: 0000 */
        public float getStrokeWidth() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public void setStrokeWidth(float f2) {
            this.b = f2;
        }

        /* access modifiers changed from: 0000 */
        public float getStrokeAlpha() {
            return this.d;
        }

        /* access modifiers changed from: 0000 */
        public void setStrokeAlpha(float f2) {
            this.d = f2;
        }

        /* access modifiers changed from: 0000 */
        public int getFillColor() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public void setFillColor(int i2) {
            this.c = i2;
        }

        /* access modifiers changed from: 0000 */
        public float getFillAlpha() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public void setFillAlpha(float f2) {
            this.f = f2;
        }

        /* access modifiers changed from: 0000 */
        public float getTrimPathStart() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        public void setTrimPathStart(float f2) {
            this.g = f2;
        }

        /* access modifiers changed from: 0000 */
        public float getTrimPathEnd() {
            return this.h;
        }

        /* access modifiers changed from: 0000 */
        public void setTrimPathEnd(float f2) {
            this.h = f2;
        }

        /* access modifiers changed from: 0000 */
        public float getTrimPathOffset() {
            return this.i;
        }

        /* access modifiers changed from: 0000 */
        public void setTrimPathOffset(float f2) {
            this.i = f2;
        }
    }

    static class VGroup {
        final ArrayList<Object> a = new ArrayList<>();
        float b = BitmapDescriptorFactory.HUE_RED;
        int c;
        /* access modifiers changed from: private */
        public final Matrix d = new Matrix();
        private float e = BitmapDescriptorFactory.HUE_RED;
        private float f = BitmapDescriptorFactory.HUE_RED;
        private float g = 1.0f;
        private float h = 1.0f;
        private float i = BitmapDescriptorFactory.HUE_RED;
        private float j = BitmapDescriptorFactory.HUE_RED;
        /* access modifiers changed from: private */
        public final Matrix k = new Matrix();
        private int[] l;
        private String m = null;

        public VGroup(VGroup vGroup, ArrayMap<String, Object> arrayMap) {
            VPath vPath;
            this.b = vGroup.b;
            this.e = vGroup.e;
            this.f = vGroup.f;
            this.g = vGroup.g;
            this.h = vGroup.h;
            this.i = vGroup.i;
            this.j = vGroup.j;
            this.l = vGroup.l;
            this.m = vGroup.m;
            this.c = vGroup.c;
            if (this.m != null) {
                arrayMap.put(this.m, this);
            }
            this.k.set(vGroup.k);
            ArrayList<Object> arrayList = vGroup.a;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                Object obj = arrayList.get(i2);
                if (obj instanceof VGroup) {
                    this.a.add(new VGroup((VGroup) obj, arrayMap));
                } else {
                    if (obj instanceof VFullPath) {
                        vPath = new VFullPath((VFullPath) obj);
                    } else if (obj instanceof VClipPath) {
                        vPath = new VClipPath((VClipPath) obj);
                    } else {
                        throw new IllegalStateException("Unknown object in the tree!");
                    }
                    this.a.add(vPath);
                    if (vPath.n != null) {
                        arrayMap.put(vPath.n, vPath);
                    }
                }
            }
        }

        public VGroup() {
        }

        public String getGroupName() {
            return this.m;
        }

        public Matrix getLocalMatrix() {
            return this.k;
        }

        public void a(Resources resources, AttributeSet attributeSet, Theme theme, XmlPullParser xmlPullParser) {
            TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.b);
            a(obtainAttributes, xmlPullParser);
            obtainAttributes.recycle();
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.l = null;
            this.b = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "rotation", 5, this.b);
            this.e = typedArray.getFloat(1, this.e);
            this.f = typedArray.getFloat(2, this.f);
            this.g = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleX", 3, this.g);
            this.h = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleY", 4, this.h);
            this.i = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateX", 6, this.i);
            this.j = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateY", 7, this.j);
            String string = typedArray.getString(0);
            if (string != null) {
                this.m = string;
            }
            a();
        }

        private void a() {
            this.k.reset();
            this.k.postTranslate(-this.e, -this.f);
            this.k.postScale(this.g, this.h);
            this.k.postRotate(this.b, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
            this.k.postTranslate(this.i + this.e, this.j + this.f);
        }

        public float getRotation() {
            return this.b;
        }

        public void setRotation(float f2) {
            if (f2 != this.b) {
                this.b = f2;
                a();
            }
        }

        public float getPivotX() {
            return this.e;
        }

        public void setPivotX(float f2) {
            if (f2 != this.e) {
                this.e = f2;
                a();
            }
        }

        public float getPivotY() {
            return this.f;
        }

        public void setPivotY(float f2) {
            if (f2 != this.f) {
                this.f = f2;
                a();
            }
        }

        public float getScaleX() {
            return this.g;
        }

        public void setScaleX(float f2) {
            if (f2 != this.g) {
                this.g = f2;
                a();
            }
        }

        public float getScaleY() {
            return this.h;
        }

        public void setScaleY(float f2) {
            if (f2 != this.h) {
                this.h = f2;
                a();
            }
        }

        public float getTranslateX() {
            return this.i;
        }

        public void setTranslateX(float f2) {
            if (f2 != this.i) {
                this.i = f2;
                a();
            }
        }

        public float getTranslateY() {
            return this.j;
        }

        public void setTranslateY(float f2) {
            if (f2 != this.j) {
                this.j = f2;
                a();
            }
        }
    }

    static class VPath {
        protected PathDataNode[] m = null;
        String n;
        int o;

        public boolean a() {
            return false;
        }

        public VPath() {
        }

        public VPath(VPath vPath) {
            this.n = vPath.n;
            this.o = vPath.o;
            this.m = PathParser.deepCopyNodes(vPath.m);
        }

        public void a(Path path) {
            path.reset();
            if (this.m != null) {
                PathDataNode.nodesToPath(this.m, path);
            }
        }

        public String getPathName() {
            return this.n;
        }

        public PathDataNode[] getPathData() {
            return this.m;
        }

        public void setPathData(PathDataNode[] pathDataNodeArr) {
            if (!PathParser.canMorph(this.m, pathDataNodeArr)) {
                this.m = PathParser.deepCopyNodes(pathDataNodeArr);
            } else {
                PathParser.updateNodes(this.m, pathDataNodeArr);
            }
        }
    }

    static class VPathRenderer {
        private static final Matrix k = new Matrix();
        final VGroup a;
        float b;
        float c;
        float d;
        float e;
        int f;
        String g;
        final ArrayMap<String, Object> h;
        private final Path i;
        private final Path j;
        private final Matrix l;
        /* access modifiers changed from: private */
        public Paint m;
        /* access modifiers changed from: private */
        public Paint n;
        private PathMeasure o;
        private int p;

        private static float a(float f2, float f3, float f4, float f5) {
            return (f2 * f5) - (f3 * f4);
        }

        public VPathRenderer() {
            this.l = new Matrix();
            this.b = BitmapDescriptorFactory.HUE_RED;
            this.c = BitmapDescriptorFactory.HUE_RED;
            this.d = BitmapDescriptorFactory.HUE_RED;
            this.e = BitmapDescriptorFactory.HUE_RED;
            this.f = 255;
            this.g = null;
            this.h = new ArrayMap<>();
            this.a = new VGroup();
            this.i = new Path();
            this.j = new Path();
        }

        public void setRootAlpha(int i2) {
            this.f = i2;
        }

        public int getRootAlpha() {
            return this.f;
        }

        public void setAlpha(float f2) {
            setRootAlpha((int) (f2 * 255.0f));
        }

        public float getAlpha() {
            return ((float) getRootAlpha()) / 255.0f;
        }

        public VPathRenderer(VPathRenderer vPathRenderer) {
            this.l = new Matrix();
            this.b = BitmapDescriptorFactory.HUE_RED;
            this.c = BitmapDescriptorFactory.HUE_RED;
            this.d = BitmapDescriptorFactory.HUE_RED;
            this.e = BitmapDescriptorFactory.HUE_RED;
            this.f = 255;
            this.g = null;
            this.h = new ArrayMap<>();
            this.a = new VGroup(vPathRenderer.a, this.h);
            this.i = new Path(vPathRenderer.i);
            this.j = new Path(vPathRenderer.j);
            this.b = vPathRenderer.b;
            this.c = vPathRenderer.c;
            this.d = vPathRenderer.d;
            this.e = vPathRenderer.e;
            this.p = vPathRenderer.p;
            this.f = vPathRenderer.f;
            this.g = vPathRenderer.g;
            if (vPathRenderer.g != null) {
                this.h.put(vPathRenderer.g, this);
            }
        }

        private void a(VGroup vGroup, Matrix matrix, Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            vGroup.d.set(matrix);
            vGroup.d.preConcat(vGroup.k);
            canvas.save();
            for (int i4 = 0; i4 < vGroup.a.size(); i4++) {
                Object obj = vGroup.a.get(i4);
                if (obj instanceof VGroup) {
                    a((VGroup) obj, vGroup.d, canvas, i2, i3, colorFilter);
                } else if (obj instanceof VPath) {
                    a(vGroup, (VPath) obj, canvas, i2, i3, colorFilter);
                }
            }
            canvas.restore();
        }

        public void a(Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            a(this.a, k, canvas, i2, i3, colorFilter);
        }

        private void a(VGroup vGroup, VPath vPath, Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            float f2 = ((float) i2) / this.d;
            float f3 = ((float) i3) / this.e;
            float min = Math.min(f2, f3);
            Matrix a2 = vGroup.d;
            this.l.set(a2);
            this.l.postScale(f2, f3);
            float a3 = a(a2);
            if (a3 != BitmapDescriptorFactory.HUE_RED) {
                vPath.a(this.i);
                Path path = this.i;
                this.j.reset();
                if (vPath.a()) {
                    this.j.addPath(path, this.l);
                    canvas.clipPath(this.j);
                } else {
                    VFullPath vFullPath = (VFullPath) vPath;
                    if (!(vFullPath.g == BitmapDescriptorFactory.HUE_RED && vFullPath.h == 1.0f)) {
                        float f4 = (vFullPath.g + vFullPath.i) % 1.0f;
                        float f5 = (vFullPath.h + vFullPath.i) % 1.0f;
                        if (this.o == null) {
                            this.o = new PathMeasure();
                        }
                        this.o.setPath(this.i, false);
                        float length = this.o.getLength();
                        float f6 = f4 * length;
                        float f7 = f5 * length;
                        path.reset();
                        if (f6 > f7) {
                            this.o.getSegment(f6, length, path, true);
                            this.o.getSegment(BitmapDescriptorFactory.HUE_RED, f7, path, true);
                        } else {
                            this.o.getSegment(f6, f7, path, true);
                        }
                        path.rLineTo(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    }
                    this.j.addPath(path, this.l);
                    if (vFullPath.c != 0) {
                        if (this.n == null) {
                            this.n = new Paint();
                            this.n.setStyle(Style.FILL);
                            this.n.setAntiAlias(true);
                        }
                        Paint paint = this.n;
                        paint.setColor(VectorDrawableCompat.a(vFullPath.c, vFullPath.f));
                        paint.setColorFilter(colorFilter);
                        this.j.setFillType(vFullPath.e == 0 ? FillType.WINDING : FillType.EVEN_ODD);
                        canvas.drawPath(this.j, paint);
                    }
                    if (vFullPath.a != 0) {
                        if (this.m == null) {
                            this.m = new Paint();
                            this.m.setStyle(Style.STROKE);
                            this.m.setAntiAlias(true);
                        }
                        Paint paint2 = this.m;
                        if (vFullPath.k != null) {
                            paint2.setStrokeJoin(vFullPath.k);
                        }
                        if (vFullPath.j != null) {
                            paint2.setStrokeCap(vFullPath.j);
                        }
                        paint2.setStrokeMiter(vFullPath.l);
                        paint2.setColor(VectorDrawableCompat.a(vFullPath.a, vFullPath.d));
                        paint2.setColorFilter(colorFilter);
                        paint2.setStrokeWidth(vFullPath.b * min * a3);
                        canvas.drawPath(this.j, paint2);
                    }
                }
            }
        }

        private float a(Matrix matrix) {
            float[] fArr = {BitmapDescriptorFactory.HUE_RED, 1.0f, 1.0f, BitmapDescriptorFactory.HUE_RED};
            matrix.mapVectors(fArr);
            float hypot = (float) Math.hypot((double) fArr[0], (double) fArr[1]);
            float hypot2 = (float) Math.hypot((double) fArr[2], (double) fArr[3]);
            float a2 = a(fArr[0], fArr[1], fArr[2], fArr[3]);
            float max = Math.max(hypot, hypot2);
            if (max > BitmapDescriptorFactory.HUE_RED) {
                return Math.abs(a2) / max;
            }
            return BitmapDescriptorFactory.HUE_RED;
        }
    }

    static class VectorDrawableCompatState extends ConstantState {
        int a;
        VPathRenderer b;
        ColorStateList c;
        Mode d;
        boolean e;
        Bitmap f;
        ColorStateList g;
        Mode h;
        int i;
        boolean j;
        boolean k;
        Paint l;

        public VectorDrawableCompatState(VectorDrawableCompatState vectorDrawableCompatState) {
            this.c = null;
            this.d = VectorDrawableCompat.a;
            if (vectorDrawableCompatState != null) {
                this.a = vectorDrawableCompatState.a;
                this.b = new VPathRenderer(vectorDrawableCompatState.b);
                if (vectorDrawableCompatState.b.n != null) {
                    this.b.n = new Paint(vectorDrawableCompatState.b.n);
                }
                if (vectorDrawableCompatState.b.m != null) {
                    this.b.m = new Paint(vectorDrawableCompatState.b.m);
                }
                this.c = vectorDrawableCompatState.c;
                this.d = vectorDrawableCompatState.d;
                this.e = vectorDrawableCompatState.e;
            }
        }

        public void a(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            canvas.drawBitmap(this.f, null, rect, a(colorFilter));
        }

        public boolean a() {
            return this.b.getRootAlpha() < 255;
        }

        public Paint a(ColorFilter colorFilter) {
            if (!a() && colorFilter == null) {
                return null;
            }
            if (this.l == null) {
                this.l = new Paint();
                this.l.setFilterBitmap(true);
            }
            this.l.setAlpha(this.b.getRootAlpha());
            this.l.setColorFilter(colorFilter);
            return this.l;
        }

        public void a(int i2, int i3) {
            this.f.eraseColor(0);
            this.b.a(new Canvas(this.f), i2, i3, (ColorFilter) null);
        }

        public void b(int i2, int i3) {
            if (this.f == null || !c(i2, i3)) {
                this.f = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
                this.k = true;
            }
        }

        public boolean c(int i2, int i3) {
            return i2 == this.f.getWidth() && i3 == this.f.getHeight();
        }

        public boolean b() {
            return !this.k && this.g == this.c && this.h == this.d && this.j == this.e && this.i == this.b.getRootAlpha();
        }

        public void c() {
            this.g = this.c;
            this.h = this.d;
            this.i = this.b.getRootAlpha();
            this.j = this.e;
            this.k = false;
        }

        public VectorDrawableCompatState() {
            this.c = null;
            this.d = VectorDrawableCompat.a;
            this.b = new VPathRenderer();
        }

        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        public Drawable newDrawable(Resources resources) {
            return new VectorDrawableCompat(this);
        }

        public int getChangingConfigurations() {
            return this.a;
        }
    }

    @RequiresApi(24)
    static class VectorDrawableDelegateState extends ConstantState {
        private final ConstantState a;

        public VectorDrawableDelegateState(ConstantState constantState) {
            this.a = constantState;
        }

        public Drawable newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.c = (VectorDrawable) this.a.newDrawable();
            return vectorDrawableCompat;
        }

        public Drawable newDrawable(Resources resources) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.c = (VectorDrawable) this.a.newDrawable(resources);
            return vectorDrawableCompat;
        }

        public Drawable newDrawable(Resources resources, Theme theme) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.c = (VectorDrawable) this.a.newDrawable(resources, theme);
            return vectorDrawableCompat;
        }

        public boolean canApplyTheme() {
            return this.a.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.a.getChangingConfigurations();
        }
    }

    public /* bridge */ /* synthetic */ void applyTheme(Theme theme) {
        super.applyTheme(theme);
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

    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i2) {
        super.setChangingConfigurations(i2);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(int i2, Mode mode) {
        super.setColorFilter(i2, mode);
    }

    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    public /* bridge */ /* synthetic */ void setHotspot(float f2, float f3) {
        super.setHotspot(f2, f3);
    }

    public /* bridge */ /* synthetic */ void setHotspotBounds(int i2, int i3, int i4, int i5) {
        super.setHotspotBounds(i2, i3, i4, i5);
    }

    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    VectorDrawableCompat() {
        this.g = true;
        this.i = new float[9];
        this.j = new Matrix();
        this.k = new Rect();
        this.b = new VectorDrawableCompatState();
    }

    VectorDrawableCompat(@NonNull VectorDrawableCompatState vectorDrawableCompatState) {
        this.g = true;
        this.i = new float[9];
        this.j = new Matrix();
        this.k = new Rect();
        this.b = vectorDrawableCompatState;
        this.d = a(this.d, vectorDrawableCompatState.c, vectorDrawableCompatState.d);
    }

    public Drawable mutate() {
        if (this.c != null) {
            this.c.mutate();
            return this;
        }
        if (!this.f && super.mutate() == this) {
            this.b = new VectorDrawableCompatState(this.b);
            this.f = true;
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Object a(String str) {
        return this.b.b.h.get(str);
    }

    public ConstantState getConstantState() {
        if (this.c != null && VERSION.SDK_INT >= 24) {
            return new VectorDrawableDelegateState(this.c.getConstantState());
        }
        this.b.a = getChangingConfigurations();
        return this.b;
    }

    public void draw(Canvas canvas) {
        if (this.c != null) {
            this.c.draw(canvas);
            return;
        }
        copyBounds(this.k);
        if (this.k.width() > 0 && this.k.height() > 0) {
            ColorFilter colorFilter = this.e == null ? this.d : this.e;
            canvas.getMatrix(this.j);
            this.j.getValues(this.i);
            float abs = Math.abs(this.i[0]);
            float abs2 = Math.abs(this.i[4]);
            float abs3 = Math.abs(this.i[1]);
            float abs4 = Math.abs(this.i[3]);
            if (!(abs3 == BitmapDescriptorFactory.HUE_RED && abs4 == BitmapDescriptorFactory.HUE_RED)) {
                abs = 1.0f;
                abs2 = 1.0f;
            }
            int height = (int) (((float) this.k.height()) * abs2);
            int min = Math.min(2048, (int) (((float) this.k.width()) * abs));
            int min2 = Math.min(2048, height);
            if (min > 0 && min2 > 0) {
                int save = canvas.save();
                canvas.translate((float) this.k.left, (float) this.k.top);
                if (a()) {
                    canvas.translate((float) this.k.width(), BitmapDescriptorFactory.HUE_RED);
                    canvas.scale(-1.0f, 1.0f);
                }
                this.k.offsetTo(0, 0);
                this.b.b(min, min2);
                if (!this.g) {
                    this.b.a(min, min2);
                } else if (!this.b.b()) {
                    this.b.a(min, min2);
                    this.b.c();
                }
                this.b.a(canvas, colorFilter, this.k);
                canvas.restoreToCount(save);
            }
        }
    }

    public int getAlpha() {
        if (this.c != null) {
            return DrawableCompat.getAlpha(this.c);
        }
        return this.b.b.getRootAlpha();
    }

    public void setAlpha(int i2) {
        if (this.c != null) {
            this.c.setAlpha(i2);
            return;
        }
        if (this.b.b.getRootAlpha() != i2) {
            this.b.b.setRootAlpha(i2);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.c != null) {
            this.c.setColorFilter(colorFilter);
            return;
        }
        this.e = colorFilter;
        invalidateSelf();
    }

    /* access modifiers changed from: 0000 */
    public PorterDuffColorFilter a(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    public void setTint(int i2) {
        if (this.c != null) {
            DrawableCompat.setTint(this.c, i2);
        } else {
            setTintList(ColorStateList.valueOf(i2));
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        if (this.c != null) {
            DrawableCompat.setTintList(this.c, colorStateList);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.b;
        if (vectorDrawableCompatState.c != colorStateList) {
            vectorDrawableCompatState.c = colorStateList;
            this.d = a(this.d, colorStateList, vectorDrawableCompatState.d);
            invalidateSelf();
        }
    }

    public void setTintMode(Mode mode) {
        if (this.c != null) {
            DrawableCompat.setTintMode(this.c, mode);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.b;
        if (vectorDrawableCompatState.d != mode) {
            vectorDrawableCompatState.d = mode;
            this.d = a(this.d, vectorDrawableCompatState.c, mode);
            invalidateSelf();
        }
    }

    public boolean isStateful() {
        if (this.c != null) {
            return this.c.isStateful();
        }
        return super.isStateful() || !(this.b == null || this.b.c == null || !this.b.c.isStateful());
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        if (this.c != null) {
            return this.c.setState(iArr);
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.b;
        if (vectorDrawableCompatState.c == null || vectorDrawableCompatState.d == null) {
            return false;
        }
        this.d = a(this.d, vectorDrawableCompatState.c, vectorDrawableCompatState.d);
        invalidateSelf();
        return true;
    }

    public int getOpacity() {
        if (this.c != null) {
            return this.c.getOpacity();
        }
        return -3;
    }

    public int getIntrinsicWidth() {
        if (this.c != null) {
            return this.c.getIntrinsicWidth();
        }
        return (int) this.b.b.b;
    }

    public int getIntrinsicHeight() {
        if (this.c != null) {
            return this.c.getIntrinsicHeight();
        }
        return (int) this.b.b.c;
    }

    public boolean canApplyTheme() {
        if (this.c != null) {
            DrawableCompat.canApplyTheme(this.c);
        }
        return false;
    }

    public boolean isAutoMirrored() {
        if (this.c != null) {
            return DrawableCompat.isAutoMirrored(this.c);
        }
        return this.b.e;
    }

    public void setAutoMirrored(boolean z) {
        if (this.c != null) {
            DrawableCompat.setAutoMirrored(this.c, z);
        } else {
            this.b.e = z;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public float getPixelSize() {
        if (this.b == null || this.b.b == null || this.b.b.b == BitmapDescriptorFactory.HUE_RED || this.b.b.c == BitmapDescriptorFactory.HUE_RED || this.b.b.e == BitmapDescriptorFactory.HUE_RED || this.b.b.d == BitmapDescriptorFactory.HUE_RED) {
            return 1.0f;
        }
        float f2 = this.b.b.b;
        float f3 = this.b.b.c;
        return Math.min(this.b.b.d / f2, this.b.b.e / f3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0034 A[Catch:{ XmlPullParserException -> 0x004a, IOException -> 0x0041 }] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003c A[Catch:{ XmlPullParserException -> 0x004a, IOException -> 0x0041 }] */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.support.graphics.drawable.VectorDrawableCompat create(@android.support.annotation.NonNull android.content.res.Resources r4, @android.support.annotation.DrawableRes int r5, @android.support.annotation.Nullable android.content.res.Resources.Theme r6) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 24
            if (r0 < r1) goto L_0x001f
            android.support.graphics.drawable.VectorDrawableCompat r0 = new android.support.graphics.drawable.VectorDrawableCompat
            r0.<init>()
            android.graphics.drawable.Drawable r4 = android.support.v4.content.res.ResourcesCompat.getDrawable(r4, r5, r6)
            r0.c = r4
            android.support.graphics.drawable.VectorDrawableCompat$VectorDrawableDelegateState r4 = new android.support.graphics.drawable.VectorDrawableCompat$VectorDrawableDelegateState
            android.graphics.drawable.Drawable r5 = r0.c
            android.graphics.drawable.Drawable$ConstantState r5 = r5.getConstantState()
            r4.<init>(r5)
            r0.h = r4
            return r0
        L_0x001f:
            android.content.res.XmlResourceParser r5 = r4.getXml(r5)     // Catch:{ XmlPullParserException -> 0x004a, IOException -> 0x0041 }
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r5)     // Catch:{ XmlPullParserException -> 0x004a, IOException -> 0x0041 }
        L_0x0027:
            int r1 = r5.next()     // Catch:{ XmlPullParserException -> 0x004a, IOException -> 0x0041 }
            r2 = 2
            if (r1 == r2) goto L_0x0032
            r3 = 1
            if (r1 == r3) goto L_0x0032
            goto L_0x0027
        L_0x0032:
            if (r1 == r2) goto L_0x003c
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x004a, IOException -> 0x0041 }
            java.lang.String r5 = "No start tag found"
            r4.<init>(r5)     // Catch:{ XmlPullParserException -> 0x004a, IOException -> 0x0041 }
            throw r4     // Catch:{ XmlPullParserException -> 0x004a, IOException -> 0x0041 }
        L_0x003c:
            android.support.graphics.drawable.VectorDrawableCompat r4 = createFromXmlInner(r4, r5, r0, r6)     // Catch:{ XmlPullParserException -> 0x004a, IOException -> 0x0041 }
            return r4
        L_0x0041:
            r4 = move-exception
            java.lang.String r5 = "VectorDrawableCompat"
            java.lang.String r6 = "parser error"
            android.util.Log.e(r5, r6, r4)
            goto L_0x0052
        L_0x004a:
            r4 = move-exception
            java.lang.String r5 = "VectorDrawableCompat"
            java.lang.String r6 = "parser error"
            android.util.Log.e(r5, r6, r4)
        L_0x0052:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.VectorDrawableCompat.create(android.content.res.Resources, int, android.content.res.Resources$Theme):android.support.graphics.drawable.VectorDrawableCompat");
    }

    public static VectorDrawableCompat createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
        vectorDrawableCompat.inflate(resources, xmlPullParser, attributeSet, theme);
        return vectorDrawableCompat;
    }

    static int a(int i2, float f2) {
        return (i2 & ViewCompat.MEASURED_SIZE_MASK) | (((int) (((float) Color.alpha(i2)) * f2)) << 24);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        if (this.c != null) {
            this.c.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        if (this.c != null) {
            DrawableCompat.inflate(this.c, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.b;
        vectorDrawableCompatState.b = new VPathRenderer();
        TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.a);
        a(obtainAttributes, xmlPullParser);
        obtainAttributes.recycle();
        vectorDrawableCompatState.a = getChangingConfigurations();
        vectorDrawableCompatState.k = true;
        a(resources, xmlPullParser, attributeSet, theme);
        this.d = a(this.d, vectorDrawableCompatState.c, vectorDrawableCompatState.d);
    }

    private static Mode a(int i2, Mode mode) {
        if (i2 == 3) {
            return Mode.SRC_OVER;
        }
        if (i2 == 5) {
            return Mode.SRC_IN;
        }
        if (i2 == 9) {
            return Mode.SRC_ATOP;
        }
        switch (i2) {
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                return Mode.ADD;
            default:
                return mode;
        }
    }

    private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
        VectorDrawableCompatState vectorDrawableCompatState = this.b;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.b;
        vectorDrawableCompatState.d = a(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "tintMode", 6, -1), Mode.SRC_IN);
        ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList != null) {
            vectorDrawableCompatState.c = colorStateList;
        }
        vectorDrawableCompatState.e = TypedArrayUtils.getNamedBoolean(typedArray, xmlPullParser, "autoMirrored", 5, vectorDrawableCompatState.e);
        vPathRenderer.d = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportWidth", 7, vPathRenderer.d);
        vPathRenderer.e = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportHeight", 8, vPathRenderer.e);
        if (vPathRenderer.d <= BitmapDescriptorFactory.HUE_RED) {
            StringBuilder sb = new StringBuilder();
            sb.append(typedArray.getPositionDescription());
            sb.append("<vector> tag requires viewportWidth > 0");
            throw new XmlPullParserException(sb.toString());
        } else if (vPathRenderer.e <= BitmapDescriptorFactory.HUE_RED) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(typedArray.getPositionDescription());
            sb2.append("<vector> tag requires viewportHeight > 0");
            throw new XmlPullParserException(sb2.toString());
        } else {
            vPathRenderer.b = typedArray.getDimension(3, vPathRenderer.b);
            vPathRenderer.c = typedArray.getDimension(2, vPathRenderer.c);
            if (vPathRenderer.b <= BitmapDescriptorFactory.HUE_RED) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(typedArray.getPositionDescription());
                sb3.append("<vector> tag requires width > 0");
                throw new XmlPullParserException(sb3.toString());
            } else if (vPathRenderer.c <= BitmapDescriptorFactory.HUE_RED) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(typedArray.getPositionDescription());
                sb4.append("<vector> tag requires height > 0");
                throw new XmlPullParserException(sb4.toString());
            } else {
                vPathRenderer.setAlpha(TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "alpha", 4, vPathRenderer.getAlpha()));
                String string = typedArray.getString(0);
                if (string != null) {
                    vPathRenderer.g = string;
                    vPathRenderer.h.put(string, vPathRenderer);
                }
            }
        }
    }

    private void a(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        VectorDrawableCompatState vectorDrawableCompatState = this.b;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.b;
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(vPathRenderer.a);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z = true;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                VGroup vGroup = (VGroup) arrayDeque.peek();
                if (ClientCookie.PATH_ATTR.equals(name)) {
                    VFullPath vFullPath = new VFullPath();
                    vFullPath.a(resources, attributeSet, theme, xmlPullParser);
                    vGroup.a.add(vFullPath);
                    if (vFullPath.getPathName() != null) {
                        vPathRenderer.h.put(vFullPath.getPathName(), vFullPath);
                    }
                    z = false;
                    vectorDrawableCompatState.a = vFullPath.o | vectorDrawableCompatState.a;
                } else if ("clip-path".equals(name)) {
                    VClipPath vClipPath = new VClipPath();
                    vClipPath.a(resources, attributeSet, theme, xmlPullParser);
                    vGroup.a.add(vClipPath);
                    if (vClipPath.getPathName() != null) {
                        vPathRenderer.h.put(vClipPath.getPathName(), vClipPath);
                    }
                    vectorDrawableCompatState.a = vClipPath.o | vectorDrawableCompatState.a;
                } else if ("group".equals(name)) {
                    VGroup vGroup2 = new VGroup();
                    vGroup2.a(resources, attributeSet, theme, xmlPullParser);
                    vGroup.a.add(vGroup2);
                    arrayDeque.push(vGroup2);
                    if (vGroup2.getGroupName() != null) {
                        vPathRenderer.h.put(vGroup2.getGroupName(), vGroup2);
                    }
                    vectorDrawableCompatState.a = vGroup2.c | vectorDrawableCompatState.a;
                }
            } else if (eventType == 3) {
                if ("group".equals(xmlPullParser.getName())) {
                    arrayDeque.pop();
                }
            }
            eventType = xmlPullParser.next();
        }
        if (z) {
            throw new XmlPullParserException("no path defined");
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        this.g = z;
    }

    private boolean a() {
        boolean z = false;
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        if (isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        if (this.c != null) {
            this.c.setBounds(rect);
        }
    }

    public int getChangingConfigurations() {
        if (this.c != null) {
            return this.c.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.b.getChangingConfigurations();
    }

    public void invalidateSelf() {
        if (this.c != null) {
            this.c.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    public void scheduleSelf(Runnable runnable, long j2) {
        if (this.c != null) {
            this.c.scheduleSelf(runnable, j2);
        } else {
            super.scheduleSelf(runnable, j2);
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        if (this.c != null) {
            return this.c.setVisible(z, z2);
        }
        return super.setVisible(z, z2);
    }

    public void unscheduleSelf(Runnable runnable) {
        if (this.c != null) {
            this.c.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }
}
