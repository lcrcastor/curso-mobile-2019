package com.davemorrissey.labs.subscaleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLongClickListener;
import android.view.ViewParent;
import com.davemorrissey.labs.subscaleview.decoder.CompatDecoderFactory;
import com.davemorrissey.labs.subscaleview.decoder.DecoderFactory;
import com.davemorrissey.labs.subscaleview.decoder.ImageDecoder;
import com.davemorrissey.labs.subscaleview.decoder.ImageRegionDecoder;
import com.davemorrissey.labs.subscaleview.decoder.SkiaImageDecoder;
import com.davemorrissey.labs.subscaleview.decoder.SkiaImageRegionDecoder;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.HttpStatus;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.bouncycastle.crypto.tls.CipherSuite;

public class SubsamplingScaleImageView extends View {
    public static final int EASE_IN_OUT_QUAD = 2;
    public static final int EASE_OUT_QUAD = 1;
    private static final int MESSAGE_LONG_CLICK = 1;
    public static final int ORIENTATION_0 = 0;
    public static final int ORIENTATION_180 = 180;
    public static final int ORIENTATION_270 = 270;
    public static final int ORIENTATION_90 = 90;
    public static final int ORIENTATION_USE_EXIF = -1;
    public static final int ORIGIN_ANIM = 1;
    public static final int ORIGIN_DOUBLE_TAP_ZOOM = 4;
    public static final int ORIGIN_FLING = 3;
    public static final int ORIGIN_TOUCH = 2;
    public static final int PAN_LIMIT_CENTER = 3;
    public static final int PAN_LIMIT_INSIDE = 1;
    public static final int PAN_LIMIT_OUTSIDE = 2;
    public static final int SCALE_TYPE_CENTER_CROP = 2;
    public static final int SCALE_TYPE_CENTER_INSIDE = 1;
    public static final int SCALE_TYPE_CUSTOM = 3;
    public static final int SCALE_TYPE_START = 4;
    /* access modifiers changed from: private */
    public static final String TAG = "SubsamplingScaleImageView";
    public static final int TILE_SIZE_AUTO = Integer.MAX_VALUE;
    /* access modifiers changed from: private */
    public static final List<Integer> VALID_EASING_STYLES = Arrays.asList(new Integer[]{Integer.valueOf(2), Integer.valueOf(1)});
    private static final List<Integer> VALID_ORIENTATIONS = Arrays.asList(new Integer[]{Integer.valueOf(0), Integer.valueOf(90), Integer.valueOf(180), Integer.valueOf(ORIENTATION_270), Integer.valueOf(-1)});
    private static final List<Integer> VALID_PAN_LIMITS = Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)});
    private static final List<Integer> VALID_SCALE_TYPES = Arrays.asList(new Integer[]{Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(4)});
    private static final List<Integer> VALID_ZOOM_STYLES = Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)});
    public static final int ZOOM_FOCUS_CENTER = 2;
    public static final int ZOOM_FOCUS_CENTER_IMMEDIATE = 3;
    public static final int ZOOM_FOCUS_FIXED = 1;
    private static Config preferredBitmapConfig;
    /* access modifiers changed from: private */
    public Anim anim;
    private Bitmap bitmap;
    private DecoderFactory<? extends ImageDecoder> bitmapDecoderFactory;
    private boolean bitmapIsCached;
    private boolean bitmapIsPreview;
    private Paint bitmapPaint;
    private boolean debug;
    private Paint debugLinePaint;
    private Paint debugTextPaint;
    private ImageRegionDecoder decoder;
    /* access modifiers changed from: private */
    public final ReadWriteLock decoderLock;
    private final float density;
    private GestureDetector detector;
    private int doubleTapZoomDuration;
    private float doubleTapZoomScale;
    private int doubleTapZoomStyle;
    private final float[] dstArray;
    private boolean eagerLoadingEnabled;
    private Executor executor;
    private int fullImageSampleSize;
    private final Handler handler;
    private boolean imageLoadedSent;
    private boolean isPanning;
    /* access modifiers changed from: private */
    public boolean isQuickScaling;
    /* access modifiers changed from: private */
    public boolean isZooming;
    private Matrix matrix;
    private float maxScale;
    private int maxTileHeight;
    private int maxTileWidth;
    /* access modifiers changed from: private */
    public int maxTouchCount;
    private float minScale;
    private int minimumScaleType;
    private int minimumTileDpi;
    /* access modifiers changed from: private */
    public OnImageEventListener onImageEventListener;
    /* access modifiers changed from: private */
    public OnLongClickListener onLongClickListener;
    private OnStateChangedListener onStateChangedListener;
    private int orientation;
    private Rect pRegion;
    /* access modifiers changed from: private */
    public boolean panEnabled;
    private int panLimit;
    private Float pendingScale;
    /* access modifiers changed from: private */
    public boolean quickScaleEnabled;
    /* access modifiers changed from: private */
    public float quickScaleLastDistance;
    /* access modifiers changed from: private */
    public boolean quickScaleMoved;
    /* access modifiers changed from: private */
    public PointF quickScaleSCenter;
    private final float quickScaleThreshold;
    /* access modifiers changed from: private */
    public PointF quickScaleVLastPoint;
    /* access modifiers changed from: private */
    public PointF quickScaleVStart;
    /* access modifiers changed from: private */
    public boolean readySent;
    private DecoderFactory<? extends ImageRegionDecoder> regionDecoderFactory;
    private int sHeight;
    private int sOrientation;
    private PointF sPendingCenter;
    private RectF sRect;
    /* access modifiers changed from: private */
    public Rect sRegion;
    private PointF sRequestedCenter;
    private int sWidth;
    private ScaleAndTranslate satTemp;
    /* access modifiers changed from: private */
    public float scale;
    /* access modifiers changed from: private */
    public float scaleStart;
    private GestureDetector singleDetector;
    private final float[] srcArray;
    private Paint tileBgPaint;
    private Map<Integer, List<Tile>> tileMap;
    private Uri uri;
    /* access modifiers changed from: private */
    public PointF vCenterStart;
    private float vDistStart;
    /* access modifiers changed from: private */
    public PointF vTranslate;
    private PointF vTranslateBefore;
    /* access modifiers changed from: private */
    public PointF vTranslateStart;
    /* access modifiers changed from: private */
    public boolean zoomEnabled;

    static class Anim {
        /* access modifiers changed from: private */
        public long duration;
        /* access modifiers changed from: private */
        public int easing;
        /* access modifiers changed from: private */
        public boolean interruptible;
        /* access modifiers changed from: private */
        public OnAnimationEventListener listener;
        /* access modifiers changed from: private */
        public int origin;
        /* access modifiers changed from: private */
        public PointF sCenterEnd;
        /* access modifiers changed from: private */
        public PointF sCenterEndRequested;
        /* access modifiers changed from: private */
        public PointF sCenterStart;
        /* access modifiers changed from: private */
        public float scaleEnd;
        /* access modifiers changed from: private */
        public float scaleStart;
        /* access modifiers changed from: private */
        public long time;
        /* access modifiers changed from: private */
        public PointF vFocusEnd;
        /* access modifiers changed from: private */
        public PointF vFocusStart;

        private Anim() {
            this.duration = 500;
            this.interruptible = true;
            this.easing = 2;
            this.origin = 1;
            this.time = System.currentTimeMillis();
        }
    }

    public final class AnimationBuilder {
        private long duration;
        private int easing;
        private boolean interruptible;
        private OnAnimationEventListener listener;
        private int origin;
        private boolean panLimited;
        private final PointF targetSCenter;
        private final float targetScale;
        private final PointF vFocus;

        private AnimationBuilder(PointF pointF) {
            this.duration = 500;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = SubsamplingScaleImageView.this.scale;
            this.targetSCenter = pointF;
            this.vFocus = null;
        }

        private AnimationBuilder(float f) {
            this.duration = 500;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = f;
            this.targetSCenter = SubsamplingScaleImageView.this.getCenter();
            this.vFocus = null;
        }

        private AnimationBuilder(float f, PointF pointF) {
            this.duration = 500;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = f;
            this.targetSCenter = pointF;
            this.vFocus = null;
        }

        private AnimationBuilder(float f, PointF pointF, PointF pointF2) {
            this.duration = 500;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = f;
            this.targetSCenter = pointF;
            this.vFocus = pointF2;
        }

        @NonNull
        public AnimationBuilder withDuration(long j) {
            this.duration = j;
            return this;
        }

        @NonNull
        public AnimationBuilder withInterruptible(boolean z) {
            this.interruptible = z;
            return this;
        }

        @NonNull
        public AnimationBuilder withEasing(int i) {
            if (!SubsamplingScaleImageView.VALID_EASING_STYLES.contains(Integer.valueOf(i))) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown easing type: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            }
            this.easing = i;
            return this;
        }

        @NonNull
        public AnimationBuilder withOnAnimationEventListener(OnAnimationEventListener onAnimationEventListener) {
            this.listener = onAnimationEventListener;
            return this;
        }

        /* access modifiers changed from: private */
        @NonNull
        public AnimationBuilder withPanLimited(boolean z) {
            this.panLimited = z;
            return this;
        }

        /* access modifiers changed from: private */
        @NonNull
        public AnimationBuilder withOrigin(int i) {
            this.origin = i;
            return this;
        }

        public void start() {
            if (!(SubsamplingScaleImageView.this.anim == null || SubsamplingScaleImageView.this.anim.listener == null)) {
                try {
                    SubsamplingScaleImageView.this.anim.listener.onInterruptedByNewAnim();
                } catch (Exception e) {
                    Log.w(SubsamplingScaleImageView.TAG, "Error thrown by animation listener", e);
                }
            }
            int paddingLeft = SubsamplingScaleImageView.this.getPaddingLeft() + (((SubsamplingScaleImageView.this.getWidth() - SubsamplingScaleImageView.this.getPaddingRight()) - SubsamplingScaleImageView.this.getPaddingLeft()) / 2);
            int paddingTop = SubsamplingScaleImageView.this.getPaddingTop() + (((SubsamplingScaleImageView.this.getHeight() - SubsamplingScaleImageView.this.getPaddingBottom()) - SubsamplingScaleImageView.this.getPaddingTop()) / 2);
            float access$6500 = SubsamplingScaleImageView.this.limitedScale(this.targetScale);
            PointF access$6600 = this.panLimited ? SubsamplingScaleImageView.this.limitedSCenter(this.targetSCenter.x, this.targetSCenter.y, access$6500, new PointF()) : this.targetSCenter;
            SubsamplingScaleImageView.this.anim = new Anim();
            SubsamplingScaleImageView.this.anim.scaleStart = SubsamplingScaleImageView.this.scale;
            SubsamplingScaleImageView.this.anim.scaleEnd = access$6500;
            SubsamplingScaleImageView.this.anim.time = System.currentTimeMillis();
            SubsamplingScaleImageView.this.anim.sCenterEndRequested = access$6600;
            SubsamplingScaleImageView.this.anim.sCenterStart = SubsamplingScaleImageView.this.getCenter();
            SubsamplingScaleImageView.this.anim.sCenterEnd = access$6600;
            SubsamplingScaleImageView.this.anim.vFocusStart = SubsamplingScaleImageView.this.sourceToViewCoord(access$6600);
            SubsamplingScaleImageView.this.anim.vFocusEnd = new PointF((float) paddingLeft, (float) paddingTop);
            SubsamplingScaleImageView.this.anim.duration = this.duration;
            SubsamplingScaleImageView.this.anim.interruptible = this.interruptible;
            SubsamplingScaleImageView.this.anim.easing = this.easing;
            SubsamplingScaleImageView.this.anim.origin = this.origin;
            SubsamplingScaleImageView.this.anim.time = System.currentTimeMillis();
            SubsamplingScaleImageView.this.anim.listener = this.listener;
            if (this.vFocus != null) {
                float f = this.vFocus.x - (SubsamplingScaleImageView.this.anim.sCenterStart.x * access$6500);
                float f2 = this.vFocus.y - (SubsamplingScaleImageView.this.anim.sCenterStart.y * access$6500);
                ScaleAndTranslate scaleAndTranslate = new ScaleAndTranslate(access$6500, new PointF(f, f2));
                SubsamplingScaleImageView.this.fitToBounds(true, scaleAndTranslate);
                SubsamplingScaleImageView.this.anim.vFocusEnd = new PointF(this.vFocus.x + (scaleAndTranslate.vTranslate.x - f), this.vFocus.y + (scaleAndTranslate.vTranslate.y - f2));
            }
            SubsamplingScaleImageView.this.invalidate();
        }
    }

    static class BitmapLoadTask extends AsyncTask<Void, Void, Integer> {
        private Bitmap bitmap;
        private final WeakReference<Context> contextRef;
        private final WeakReference<DecoderFactory<? extends ImageDecoder>> decoderFactoryRef;
        private Exception exception;
        private final boolean preview;
        private final Uri source;
        private final WeakReference<SubsamplingScaleImageView> viewRef;

        BitmapLoadTask(SubsamplingScaleImageView subsamplingScaleImageView, Context context, DecoderFactory<? extends ImageDecoder> decoderFactory, Uri uri, boolean z) {
            this.viewRef = new WeakReference<>(subsamplingScaleImageView);
            this.contextRef = new WeakReference<>(context);
            this.decoderFactoryRef = new WeakReference<>(decoderFactory);
            this.source = uri;
            this.preview = z;
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... voidArr) {
            try {
                String uri = this.source.toString();
                Context context = (Context) this.contextRef.get();
                DecoderFactory decoderFactory = (DecoderFactory) this.decoderFactoryRef.get();
                SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) this.viewRef.get();
                if (!(context == null || decoderFactory == null || subsamplingScaleImageView == null)) {
                    subsamplingScaleImageView.debug("BitmapLoadTask.doInBackground", new Object[0]);
                    this.bitmap = ((ImageDecoder) decoderFactory.make()).decode(context, this.source);
                    return Integer.valueOf(subsamplingScaleImageView.getExifOrientation(context, uri));
                }
            } catch (Exception e) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to load bitmap", e);
                this.exception = e;
            } catch (OutOfMemoryError e2) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to load bitmap - OutOfMemoryError", e2);
                this.exception = new RuntimeException(e2);
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer num) {
            SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) this.viewRef.get();
            if (subsamplingScaleImageView == null) {
                return;
            }
            if (this.bitmap == null || num == null) {
                if (this.exception != null && subsamplingScaleImageView.onImageEventListener != null) {
                    if (this.preview) {
                        subsamplingScaleImageView.onImageEventListener.onPreviewLoadError(this.exception);
                    } else {
                        subsamplingScaleImageView.onImageEventListener.onImageLoadError(this.exception);
                    }
                }
            } else if (this.preview) {
                subsamplingScaleImageView.onPreviewLoaded(this.bitmap);
            } else {
                subsamplingScaleImageView.onImageLoaded(this.bitmap, num.intValue(), false);
            }
        }
    }

    public static class DefaultOnAnimationEventListener implements OnAnimationEventListener {
        public void onComplete() {
        }

        public void onInterruptedByNewAnim() {
        }

        public void onInterruptedByUser() {
        }
    }

    public static class DefaultOnImageEventListener implements OnImageEventListener {
        public void onImageLoadError(Exception exc) {
        }

        public void onImageLoaded() {
        }

        public void onPreviewLoadError(Exception exc) {
        }

        public void onPreviewReleased() {
        }

        public void onReady() {
        }

        public void onTileLoadError(Exception exc) {
        }
    }

    public static class DefaultOnStateChangedListener implements OnStateChangedListener {
        public void onCenterChanged(PointF pointF, int i) {
        }

        public void onScaleChanged(float f, int i) {
        }
    }

    public interface OnAnimationEventListener {
        void onComplete();

        void onInterruptedByNewAnim();

        void onInterruptedByUser();
    }

    public interface OnImageEventListener {
        void onImageLoadError(Exception exc);

        void onImageLoaded();

        void onPreviewLoadError(Exception exc);

        void onPreviewReleased();

        void onReady();

        void onTileLoadError(Exception exc);
    }

    public interface OnStateChangedListener {
        void onCenterChanged(PointF pointF, int i);

        void onScaleChanged(float f, int i);
    }

    static class ScaleAndTranslate {
        /* access modifiers changed from: private */
        public float scale;
        /* access modifiers changed from: private */
        public final PointF vTranslate;

        private ScaleAndTranslate(float f, PointF pointF) {
            this.scale = f;
            this.vTranslate = pointF;
        }
    }

    static class Tile {
        /* access modifiers changed from: private */
        public Bitmap bitmap;
        /* access modifiers changed from: private */
        public Rect fileSRect;
        /* access modifiers changed from: private */
        public boolean loading;
        /* access modifiers changed from: private */
        public Rect sRect;
        /* access modifiers changed from: private */
        public int sampleSize;
        /* access modifiers changed from: private */
        public Rect vRect;
        /* access modifiers changed from: private */
        public boolean visible;

        private Tile() {
        }
    }

    static class TileLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private final WeakReference<ImageRegionDecoder> decoderRef;
        private Exception exception;
        private final WeakReference<Tile> tileRef;
        private final WeakReference<SubsamplingScaleImageView> viewRef;

        TileLoadTask(SubsamplingScaleImageView subsamplingScaleImageView, ImageRegionDecoder imageRegionDecoder, Tile tile) {
            this.viewRef = new WeakReference<>(subsamplingScaleImageView);
            this.decoderRef = new WeakReference<>(imageRegionDecoder);
            this.tileRef = new WeakReference<>(tile);
            tile.loading = true;
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            SubsamplingScaleImageView subsamplingScaleImageView;
            try {
                subsamplingScaleImageView = (SubsamplingScaleImageView) this.viewRef.get();
                ImageRegionDecoder imageRegionDecoder = (ImageRegionDecoder) this.decoderRef.get();
                Tile tile = (Tile) this.tileRef.get();
                if (imageRegionDecoder == null || tile == null || subsamplingScaleImageView == null || !imageRegionDecoder.isReady() || !tile.visible) {
                    if (tile != null) {
                        tile.loading = false;
                    }
                    return null;
                }
                subsamplingScaleImageView.debug("TileLoadTask.doInBackground, tile.sRect=%s, tile.sampleSize=%d", tile.sRect, Integer.valueOf(tile.sampleSize));
                subsamplingScaleImageView.decoderLock.readLock().lock();
                if (imageRegionDecoder.isReady()) {
                    subsamplingScaleImageView.fileSRect(tile.sRect, tile.fileSRect);
                    if (subsamplingScaleImageView.sRegion != null) {
                        tile.fileSRect.offset(subsamplingScaleImageView.sRegion.left, subsamplingScaleImageView.sRegion.top);
                    }
                    Bitmap decodeRegion = imageRegionDecoder.decodeRegion(tile.fileSRect, tile.sampleSize);
                    subsamplingScaleImageView.decoderLock.readLock().unlock();
                    return decodeRegion;
                }
                tile.loading = false;
                subsamplingScaleImageView.decoderLock.readLock().unlock();
                return null;
            } catch (Exception e) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to decode tile", e);
                this.exception = e;
            } catch (OutOfMemoryError e2) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to decode tile - OutOfMemoryError", e2);
                this.exception = new RuntimeException(e2);
            } catch (Throwable th) {
                subsamplingScaleImageView.decoderLock.readLock().unlock();
                throw th;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) this.viewRef.get();
            Tile tile = (Tile) this.tileRef.get();
            if (subsamplingScaleImageView != null && tile != null) {
                if (bitmap != null) {
                    tile.bitmap = bitmap;
                    tile.loading = false;
                    subsamplingScaleImageView.onTileLoaded();
                } else if (this.exception != null && subsamplingScaleImageView.onImageEventListener != null) {
                    subsamplingScaleImageView.onImageEventListener.onTileLoadError(this.exception);
                }
            }
        }
    }

    static class TilesInitTask extends AsyncTask<Void, Void, int[]> {
        private final WeakReference<Context> contextRef;
        private ImageRegionDecoder decoder;
        private final WeakReference<DecoderFactory<? extends ImageRegionDecoder>> decoderFactoryRef;
        private Exception exception;
        private final Uri source;
        private final WeakReference<SubsamplingScaleImageView> viewRef;

        TilesInitTask(SubsamplingScaleImageView subsamplingScaleImageView, Context context, DecoderFactory<? extends ImageRegionDecoder> decoderFactory, Uri uri) {
            this.viewRef = new WeakReference<>(subsamplingScaleImageView);
            this.contextRef = new WeakReference<>(context);
            this.decoderFactoryRef = new WeakReference<>(decoderFactory);
            this.source = uri;
        }

        /* access modifiers changed from: protected */
        public int[] doInBackground(Void... voidArr) {
            try {
                String uri = this.source.toString();
                Context context = (Context) this.contextRef.get();
                DecoderFactory decoderFactory = (DecoderFactory) this.decoderFactoryRef.get();
                SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) this.viewRef.get();
                if (!(context == null || decoderFactory == null || subsamplingScaleImageView == null)) {
                    subsamplingScaleImageView.debug("TilesInitTask.doInBackground", new Object[0]);
                    this.decoder = (ImageRegionDecoder) decoderFactory.make();
                    Point init = this.decoder.init(context, this.source);
                    int i = init.x;
                    int i2 = init.y;
                    int access$5200 = subsamplingScaleImageView.getExifOrientation(context, uri);
                    if (subsamplingScaleImageView.sRegion != null) {
                        subsamplingScaleImageView.sRegion.left = Math.max(0, subsamplingScaleImageView.sRegion.left);
                        subsamplingScaleImageView.sRegion.top = Math.max(0, subsamplingScaleImageView.sRegion.top);
                        subsamplingScaleImageView.sRegion.right = Math.min(i, subsamplingScaleImageView.sRegion.right);
                        subsamplingScaleImageView.sRegion.bottom = Math.min(i2, subsamplingScaleImageView.sRegion.bottom);
                        i = subsamplingScaleImageView.sRegion.width();
                        i2 = subsamplingScaleImageView.sRegion.height();
                    }
                    return new int[]{i, i2, access$5200};
                }
            } catch (Exception e) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to initialise bitmap decoder", e);
                this.exception = e;
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(int[] iArr) {
            SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) this.viewRef.get();
            if (subsamplingScaleImageView == null) {
                return;
            }
            if (this.decoder != null && iArr != null && iArr.length == 3) {
                subsamplingScaleImageView.onTilesInited(this.decoder, iArr[0], iArr[1], iArr[2]);
            } else if (this.exception != null && subsamplingScaleImageView.onImageEventListener != null) {
                subsamplingScaleImageView.onImageEventListener.onImageLoadError(this.exception);
            }
        }
    }

    private float easeInOutQuad(long j, float f, float f2, long j2) {
        float f3 = ((float) j) / (((float) j2) / 2.0f);
        if (f3 < 1.0f) {
            return ((f2 / 2.0f) * f3 * f3) + f;
        }
        float f4 = f3 - 1.0f;
        return (((-f2) / 2.0f) * ((f4 * (f4 - 2.0f)) - 1.0f)) + f;
    }

    private float easeOutQuad(long j, float f, float f2, long j2) {
        float f3 = ((float) j) / ((float) j2);
        return ((-f2) * f3 * (f3 - 2.0f)) + f;
    }

    /* access modifiers changed from: protected */
    public void onImageLoaded() {
    }

    /* access modifiers changed from: protected */
    public void onReady() {
    }

    public SubsamplingScaleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.orientation = 0;
        this.maxScale = 2.0f;
        this.minScale = minScale();
        this.minimumTileDpi = -1;
        this.panLimit = 1;
        this.minimumScaleType = 1;
        this.maxTileWidth = TILE_SIZE_AUTO;
        this.maxTileHeight = TILE_SIZE_AUTO;
        this.executor = AsyncTask.THREAD_POOL_EXECUTOR;
        this.eagerLoadingEnabled = true;
        this.panEnabled = true;
        this.zoomEnabled = true;
        this.quickScaleEnabled = true;
        this.doubleTapZoomScale = 1.0f;
        this.doubleTapZoomStyle = 1;
        this.doubleTapZoomDuration = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        this.decoderLock = new ReentrantReadWriteLock(true);
        this.bitmapDecoderFactory = new CompatDecoderFactory(SkiaImageDecoder.class);
        this.regionDecoderFactory = new CompatDecoderFactory(SkiaImageRegionDecoder.class);
        this.srcArray = new float[8];
        this.dstArray = new float[8];
        this.density = getResources().getDisplayMetrics().density;
        setMinimumDpi(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
        setDoubleTapZoomDpi(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
        setMinimumTileDpi(320);
        setGestureDetector(context);
        this.handler = new Handler(new Callback() {
            public boolean handleMessage(Message message) {
                if (message.what == 1 && SubsamplingScaleImageView.this.onLongClickListener != null) {
                    SubsamplingScaleImageView.this.maxTouchCount = 0;
                    SubsamplingScaleImageView.super.setOnLongClickListener(SubsamplingScaleImageView.this.onLongClickListener);
                    SubsamplingScaleImageView.this.performLongClick();
                    SubsamplingScaleImageView.super.setOnLongClickListener(null);
                }
                return true;
            }
        });
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.SubsamplingScaleImageView);
            if (obtainStyledAttributes.hasValue(R.styleable.SubsamplingScaleImageView_assetName)) {
                String string = obtainStyledAttributes.getString(R.styleable.SubsamplingScaleImageView_assetName);
                if (string != null && string.length() > 0) {
                    setImage(ImageSource.asset(string).tilingEnabled());
                }
            }
            if (obtainStyledAttributes.hasValue(R.styleable.SubsamplingScaleImageView_src)) {
                int resourceId = obtainStyledAttributes.getResourceId(R.styleable.SubsamplingScaleImageView_src, 0);
                if (resourceId > 0) {
                    setImage(ImageSource.resource(resourceId).tilingEnabled());
                }
            }
            if (obtainStyledAttributes.hasValue(R.styleable.SubsamplingScaleImageView_panEnabled)) {
                setPanEnabled(obtainStyledAttributes.getBoolean(R.styleable.SubsamplingScaleImageView_panEnabled, true));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.SubsamplingScaleImageView_zoomEnabled)) {
                setZoomEnabled(obtainStyledAttributes.getBoolean(R.styleable.SubsamplingScaleImageView_zoomEnabled, true));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.SubsamplingScaleImageView_quickScaleEnabled)) {
                setQuickScaleEnabled(obtainStyledAttributes.getBoolean(R.styleable.SubsamplingScaleImageView_quickScaleEnabled, true));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.SubsamplingScaleImageView_tileBackgroundColor)) {
                setTileBackgroundColor(obtainStyledAttributes.getColor(R.styleable.SubsamplingScaleImageView_tileBackgroundColor, Color.argb(0, 0, 0, 0)));
            }
            obtainStyledAttributes.recycle();
        }
        this.quickScaleThreshold = TypedValue.applyDimension(1, 20.0f, context.getResources().getDisplayMetrics());
    }

    public SubsamplingScaleImageView(Context context) {
        this(context, null);
    }

    public static Config getPreferredBitmapConfig() {
        return preferredBitmapConfig;
    }

    public static void setPreferredBitmapConfig(Config config) {
        preferredBitmapConfig = config;
    }

    public final void setOrientation(int i) {
        if (!VALID_ORIENTATIONS.contains(Integer.valueOf(i))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid orientation: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        this.orientation = i;
        reset(false);
        invalidate();
        requestLayout();
    }

    public final void setImage(@NonNull ImageSource imageSource) {
        setImage(imageSource, null, null);
    }

    public final void setImage(@NonNull ImageSource imageSource, ImageViewState imageViewState) {
        setImage(imageSource, null, imageViewState);
    }

    public final void setImage(@NonNull ImageSource imageSource, ImageSource imageSource2) {
        setImage(imageSource, imageSource2, null);
    }

    public final void setImage(@NonNull ImageSource imageSource, ImageSource imageSource2, ImageViewState imageViewState) {
        if (imageSource == null) {
            throw new NullPointerException("imageSource must not be null");
        }
        reset(true);
        if (imageViewState != null) {
            restoreState(imageViewState);
        }
        if (imageSource2 != null) {
            if (imageSource.getBitmap() != null) {
                throw new IllegalArgumentException("Preview image cannot be used when a bitmap is provided for the main image");
            } else if (imageSource.getSWidth() <= 0 || imageSource.getSHeight() <= 0) {
                throw new IllegalArgumentException("Preview image cannot be used unless dimensions are provided for the main image");
            } else {
                this.sWidth = imageSource.getSWidth();
                this.sHeight = imageSource.getSHeight();
                this.pRegion = imageSource2.getSRegion();
                if (imageSource2.getBitmap() != null) {
                    this.bitmapIsCached = imageSource2.isCached();
                    onPreviewLoaded(imageSource2.getBitmap());
                } else {
                    Uri uri2 = imageSource2.getUri();
                    if (uri2 == null && imageSource2.getResource() != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("android.resource://");
                        sb.append(getContext().getPackageName());
                        sb.append("/");
                        sb.append(imageSource2.getResource());
                        uri2 = Uri.parse(sb.toString());
                    }
                    BitmapLoadTask bitmapLoadTask = new BitmapLoadTask(this, getContext(), this.bitmapDecoderFactory, uri2, true);
                    execute(bitmapLoadTask);
                }
            }
        }
        if (imageSource.getBitmap() != null && imageSource.getSRegion() != null) {
            onImageLoaded(Bitmap.createBitmap(imageSource.getBitmap(), imageSource.getSRegion().left, imageSource.getSRegion().top, imageSource.getSRegion().width(), imageSource.getSRegion().height()), 0, false);
        } else if (imageSource.getBitmap() != null) {
            onImageLoaded(imageSource.getBitmap(), 0, imageSource.isCached());
        } else {
            this.sRegion = imageSource.getSRegion();
            this.uri = imageSource.getUri();
            if (this.uri == null && imageSource.getResource() != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("android.resource://");
                sb2.append(getContext().getPackageName());
                sb2.append("/");
                sb2.append(imageSource.getResource());
                this.uri = Uri.parse(sb2.toString());
            }
            if (imageSource.getTile() || this.sRegion != null) {
                execute(new TilesInitTask(this, getContext(), this.regionDecoderFactory, this.uri));
                return;
            }
            BitmapLoadTask bitmapLoadTask2 = new BitmapLoadTask(this, getContext(), this.bitmapDecoderFactory, this.uri, false);
            execute(bitmapLoadTask2);
        }
    }

    /* JADX INFO: finally extract failed */
    private void reset(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("reset newImage=");
        sb.append(z);
        debug(sb.toString(), new Object[0]);
        this.scale = BitmapDescriptorFactory.HUE_RED;
        this.scaleStart = BitmapDescriptorFactory.HUE_RED;
        this.vTranslate = null;
        this.vTranslateStart = null;
        this.vTranslateBefore = null;
        this.pendingScale = Float.valueOf(BitmapDescriptorFactory.HUE_RED);
        this.sPendingCenter = null;
        this.sRequestedCenter = null;
        this.isZooming = false;
        this.isPanning = false;
        this.isQuickScaling = false;
        this.maxTouchCount = 0;
        this.fullImageSampleSize = 0;
        this.vCenterStart = null;
        this.vDistStart = BitmapDescriptorFactory.HUE_RED;
        this.quickScaleLastDistance = BitmapDescriptorFactory.HUE_RED;
        this.quickScaleMoved = false;
        this.quickScaleSCenter = null;
        this.quickScaleVLastPoint = null;
        this.quickScaleVStart = null;
        this.anim = null;
        this.satTemp = null;
        this.matrix = null;
        this.sRect = null;
        if (z) {
            this.uri = null;
            this.decoderLock.writeLock().lock();
            try {
                if (this.decoder != null) {
                    this.decoder.recycle();
                    this.decoder = null;
                }
                this.decoderLock.writeLock().unlock();
                if (this.bitmap != null && !this.bitmapIsCached) {
                    this.bitmap.recycle();
                }
                if (!(this.bitmap == null || !this.bitmapIsCached || this.onImageEventListener == null)) {
                    this.onImageEventListener.onPreviewReleased();
                }
                this.sWidth = 0;
                this.sHeight = 0;
                this.sOrientation = 0;
                this.sRegion = null;
                this.pRegion = null;
                this.readySent = false;
                this.imageLoadedSent = false;
                this.bitmap = null;
                this.bitmapIsPreview = false;
                this.bitmapIsCached = false;
            } catch (Throwable th) {
                this.decoderLock.writeLock().unlock();
                throw th;
            }
        }
        if (this.tileMap != null) {
            for (Entry value : this.tileMap.entrySet()) {
                for (Tile tile : (List) value.getValue()) {
                    tile.visible = false;
                    if (tile.bitmap != null) {
                        tile.bitmap.recycle();
                        tile.bitmap = null;
                    }
                }
            }
            this.tileMap = null;
        }
        setGestureDetector(getContext());
    }

    /* access modifiers changed from: private */
    public void setGestureDetector(final Context context) {
        this.detector = new GestureDetector(context, new SimpleOnGestureListener() {
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (!SubsamplingScaleImageView.this.panEnabled || !SubsamplingScaleImageView.this.readySent || SubsamplingScaleImageView.this.vTranslate == null || motionEvent == null || motionEvent2 == null || ((Math.abs(motionEvent.getX() - motionEvent2.getX()) <= 50.0f && Math.abs(motionEvent.getY() - motionEvent2.getY()) <= 50.0f) || ((Math.abs(f) <= 500.0f && Math.abs(f2) <= 500.0f) || SubsamplingScaleImageView.this.isZooming))) {
                    return super.onFling(motionEvent, motionEvent2, f, f2);
                }
                PointF pointF = new PointF(SubsamplingScaleImageView.this.vTranslate.x + (f * 0.25f), SubsamplingScaleImageView.this.vTranslate.y + (f2 * 0.25f));
                new AnimationBuilder(new PointF((((float) (SubsamplingScaleImageView.this.getWidth() / 2)) - pointF.x) / SubsamplingScaleImageView.this.scale, (((float) (SubsamplingScaleImageView.this.getHeight() / 2)) - pointF.y) / SubsamplingScaleImageView.this.scale)).withEasing(1).withPanLimited(false).withOrigin(3).start();
                return true;
            }

            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                SubsamplingScaleImageView.this.performClick();
                return true;
            }

            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (!SubsamplingScaleImageView.this.zoomEnabled || !SubsamplingScaleImageView.this.readySent || SubsamplingScaleImageView.this.vTranslate == null) {
                    return super.onDoubleTapEvent(motionEvent);
                }
                SubsamplingScaleImageView.this.setGestureDetector(context);
                if (SubsamplingScaleImageView.this.quickScaleEnabled) {
                    SubsamplingScaleImageView.this.vCenterStart = new PointF(motionEvent.getX(), motionEvent.getY());
                    SubsamplingScaleImageView.this.vTranslateStart = new PointF(SubsamplingScaleImageView.this.vTranslate.x, SubsamplingScaleImageView.this.vTranslate.y);
                    SubsamplingScaleImageView.this.scaleStart = SubsamplingScaleImageView.this.scale;
                    SubsamplingScaleImageView.this.isQuickScaling = true;
                    SubsamplingScaleImageView.this.isZooming = true;
                    SubsamplingScaleImageView.this.quickScaleLastDistance = -1.0f;
                    SubsamplingScaleImageView.this.quickScaleSCenter = SubsamplingScaleImageView.this.viewToSourceCoord(SubsamplingScaleImageView.this.vCenterStart);
                    SubsamplingScaleImageView.this.quickScaleVStart = new PointF(motionEvent.getX(), motionEvent.getY());
                    SubsamplingScaleImageView.this.quickScaleVLastPoint = new PointF(SubsamplingScaleImageView.this.quickScaleSCenter.x, SubsamplingScaleImageView.this.quickScaleSCenter.y);
                    SubsamplingScaleImageView.this.quickScaleMoved = false;
                    return false;
                }
                SubsamplingScaleImageView.this.doubleTapZoom(SubsamplingScaleImageView.this.viewToSourceCoord(new PointF(motionEvent.getX(), motionEvent.getY())), new PointF(motionEvent.getX(), motionEvent.getY()));
                return true;
            }
        });
        this.singleDetector = new GestureDetector(context, new SimpleOnGestureListener() {
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                SubsamplingScaleImageView.this.performClick();
                return true;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        debug("onSizeChanged %dx%d -> %dx%d", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i), Integer.valueOf(i2));
        PointF center = getCenter();
        if (this.readySent && center != null) {
            this.anim = null;
            this.pendingScale = Float.valueOf(this.scale);
            this.sPendingCenter = center;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        boolean z = false;
        boolean z2 = mode != 1073741824;
        if (mode2 != 1073741824) {
            z = true;
        }
        if (this.sWidth > 0 && this.sHeight > 0) {
            if (z2 && z) {
                size = sWidth();
                size2 = sHeight();
            } else if (z) {
                size2 = (int) ((((double) sHeight()) / ((double) sWidth())) * ((double) size));
            } else if (z2) {
                size = (int) ((((double) sWidth()) / ((double) sHeight())) * ((double) size2));
            }
        }
        setMeasuredDimension(Math.max(size, getSuggestedMinimumWidth()), Math.max(size2, getSuggestedMinimumHeight()));
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        boolean z = true;
        if (this.anim == null || this.anim.interruptible) {
            if (!(this.anim == null || this.anim.listener == null)) {
                try {
                    this.anim.listener.onInterruptedByUser();
                } catch (Exception e) {
                    Log.w(TAG, "Error thrown by animation listener", e);
                }
            }
            this.anim = null;
            if (this.vTranslate == null) {
                if (this.singleDetector != null) {
                    this.singleDetector.onTouchEvent(motionEvent);
                }
                return true;
            } else if (this.isQuickScaling || (this.detector != null && !this.detector.onTouchEvent(motionEvent))) {
                if (this.vTranslateStart == null) {
                    this.vTranslateStart = new PointF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                }
                if (this.vTranslateBefore == null) {
                    this.vTranslateBefore = new PointF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                }
                if (this.vCenterStart == null) {
                    this.vCenterStart = new PointF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                }
                float f = this.scale;
                this.vTranslateBefore.set(this.vTranslate);
                boolean onTouchEventInternal = onTouchEventInternal(motionEvent);
                sendStateChanged(f, this.vTranslateBefore, 2);
                if (!onTouchEventInternal && !super.onTouchEvent(motionEvent)) {
                    z = false;
                }
                return z;
            } else {
                this.isZooming = false;
                this.isPanning = false;
                this.maxTouchCount = 0;
                return true;
            }
        } else {
            requestDisallowInterceptTouchEvent(true);
            return true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:123:0x03da  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean onTouchEventInternal(@android.support.annotation.NonNull android.view.MotionEvent r13) {
        /*
            r12 = this;
            int r0 = r13.getPointerCount()
            int r1 = r13.getAction()
            r2 = 1073741824(0x40000000, float:2.0)
            r3 = 2
            r4 = 0
            r5 = 1
            switch(r1) {
                case 0: goto L_0x0453;
                case 1: goto L_0x03e3;
                case 2: goto L_0x0012;
                case 5: goto L_0x0453;
                case 6: goto L_0x03e3;
                case 261: goto L_0x0453;
                case 262: goto L_0x03e3;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x04d6
        L_0x0012:
            int r1 = r12.maxTouchCount
            if (r1 <= 0) goto L_0x03d7
            r1 = 1084227584(0x40a00000, float:5.0)
            if (r0 < r3) goto L_0x0186
            float r0 = r13.getX(r4)
            float r6 = r13.getX(r5)
            float r7 = r13.getY(r4)
            float r8 = r13.getY(r5)
            float r0 = r12.distance(r0, r6, r7, r8)
            float r6 = r13.getX(r4)
            float r7 = r13.getX(r5)
            float r6 = r6 + r7
            float r6 = r6 / r2
            float r7 = r13.getY(r4)
            float r13 = r13.getY(r5)
            float r7 = r7 + r13
            float r7 = r7 / r2
            boolean r13 = r12.zoomEnabled
            if (r13 == 0) goto L_0x03d7
            android.graphics.PointF r13 = r12.vCenterStart
            float r13 = r13.x
            android.graphics.PointF r2 = r12.vCenterStart
            float r2 = r2.y
            float r13 = r12.distance(r13, r6, r2, r7)
            int r13 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r13 > 0) goto L_0x0066
            float r13 = r12.vDistStart
            float r13 = r0 - r13
            float r13 = java.lang.Math.abs(r13)
            int r13 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r13 > 0) goto L_0x0066
            boolean r13 = r12.isPanning
            if (r13 == 0) goto L_0x03d7
        L_0x0066:
            r12.isZooming = r5
            r12.isPanning = r5
            float r13 = r12.scale
            double r1 = (double) r13
            float r13 = r12.maxScale
            float r8 = r12.vDistStart
            float r8 = r0 / r8
            float r9 = r12.scaleStart
            float r8 = r8 * r9
            float r13 = java.lang.Math.min(r13, r8)
            r12.scale = r13
            float r13 = r12.scale
            float r8 = r12.minScale()
            int r13 = (r13 > r8 ? 1 : (r13 == r8 ? 0 : -1))
            if (r13 > 0) goto L_0x009d
            r12.vDistStart = r0
            float r13 = r12.minScale()
            r12.scaleStart = r13
            android.graphics.PointF r13 = r12.vCenterStart
            r13.set(r6, r7)
            android.graphics.PointF r13 = r12.vTranslateStart
            android.graphics.PointF r0 = r12.vTranslate
            r13.set(r0)
            goto L_0x017c
        L_0x009d:
            boolean r13 = r12.panEnabled
            if (r13 == 0) goto L_0x0127
            android.graphics.PointF r13 = r12.vCenterStart
            float r13 = r13.x
            android.graphics.PointF r3 = r12.vTranslateStart
            float r3 = r3.x
            float r13 = r13 - r3
            android.graphics.PointF r3 = r12.vCenterStart
            float r3 = r3.y
            android.graphics.PointF r8 = r12.vTranslateStart
            float r8 = r8.y
            float r3 = r3 - r8
            float r8 = r12.scale
            float r9 = r12.scaleStart
            float r8 = r8 / r9
            float r13 = r13 * r8
            float r8 = r12.scale
            float r9 = r12.scaleStart
            float r8 = r8 / r9
            float r3 = r3 * r8
            android.graphics.PointF r8 = r12.vTranslate
            float r13 = r6 - r13
            r8.x = r13
            android.graphics.PointF r13 = r12.vTranslate
            float r3 = r7 - r3
            r13.y = r3
            int r13 = r12.sHeight()
            double r8 = (double) r13
            double r8 = r8 * r1
            int r13 = r12.getHeight()
            double r10 = (double) r13
            int r13 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r13 >= 0) goto L_0x00ef
            float r13 = r12.scale
            int r3 = r12.sHeight()
            float r3 = (float) r3
            float r13 = r13 * r3
            int r3 = r12.getHeight()
            float r3 = (float) r3
            int r13 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x0111
        L_0x00ef:
            int r13 = r12.sWidth()
            double r8 = (double) r13
            double r1 = r1 * r8
            int r13 = r12.getWidth()
            double r8 = (double) r13
            int r13 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r13 >= 0) goto L_0x017c
            float r13 = r12.scale
            int r1 = r12.sWidth()
            float r1 = (float) r1
            float r13 = r13 * r1
            int r1 = r12.getWidth()
            float r1 = (float) r1
            int r13 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r13 < 0) goto L_0x017c
        L_0x0111:
            r12.fitToBounds(r5)
            android.graphics.PointF r13 = r12.vCenterStart
            r13.set(r6, r7)
            android.graphics.PointF r13 = r12.vTranslateStart
            android.graphics.PointF r1 = r12.vTranslate
            r13.set(r1)
            float r13 = r12.scale
            r12.scaleStart = r13
            r12.vDistStart = r0
            goto L_0x017c
        L_0x0127:
            android.graphics.PointF r13 = r12.sRequestedCenter
            if (r13 == 0) goto L_0x0152
            android.graphics.PointF r13 = r12.vTranslate
            int r0 = r12.getWidth()
            int r0 = r0 / r3
            float r0 = (float) r0
            float r1 = r12.scale
            android.graphics.PointF r2 = r12.sRequestedCenter
            float r2 = r2.x
            float r1 = r1 * r2
            float r0 = r0 - r1
            r13.x = r0
            android.graphics.PointF r13 = r12.vTranslate
            int r0 = r12.getHeight()
            int r0 = r0 / r3
            float r0 = (float) r0
            float r1 = r12.scale
            android.graphics.PointF r2 = r12.sRequestedCenter
            float r2 = r2.y
            float r1 = r1 * r2
            float r0 = r0 - r1
            r13.y = r0
            goto L_0x017c
        L_0x0152:
            android.graphics.PointF r13 = r12.vTranslate
            int r0 = r12.getWidth()
            int r0 = r0 / r3
            float r0 = (float) r0
            float r1 = r12.scale
            int r2 = r12.sWidth()
            int r2 = r2 / r3
            float r2 = (float) r2
            float r1 = r1 * r2
            float r0 = r0 - r1
            r13.x = r0
            android.graphics.PointF r13 = r12.vTranslate
            int r0 = r12.getHeight()
            int r0 = r0 / r3
            float r0 = (float) r0
            float r1 = r12.scale
            int r2 = r12.sHeight()
            int r2 = r2 / r3
            float r2 = (float) r2
            float r1 = r1 * r2
            float r0 = r0 - r1
            r13.y = r0
        L_0x017c:
            r12.fitToBounds(r5)
            boolean r13 = r12.eagerLoadingEnabled
            r12.refreshRequiredTiles(r13)
            goto L_0x02f3
        L_0x0186:
            boolean r0 = r12.isQuickScaling
            if (r0 == 0) goto L_0x02f6
            android.graphics.PointF r0 = r12.quickScaleVStart
            float r0 = r0.y
            float r1 = r13.getY()
            float r0 = r0 - r1
            float r0 = java.lang.Math.abs(r0)
            float r0 = r0 * r2
            float r1 = r12.quickScaleThreshold
            float r0 = r0 + r1
            float r1 = r12.quickScaleLastDistance
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x01a6
            r12.quickScaleLastDistance = r0
        L_0x01a6:
            float r1 = r13.getY()
            android.graphics.PointF r2 = r12.quickScaleVLastPoint
            float r2 = r2.y
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x01b4
            r1 = 1
            goto L_0x01b5
        L_0x01b4:
            r1 = 0
        L_0x01b5:
            android.graphics.PointF r2 = r12.quickScaleVLastPoint
            float r13 = r13.getY()
            r6 = 0
            r2.set(r6, r13)
            float r13 = r12.quickScaleLastDistance
            float r13 = r0 / r13
            r2 = 1065353216(0x3f800000, float:1.0)
            float r13 = r2 - r13
            float r13 = java.lang.Math.abs(r13)
            r7 = 1056964608(0x3f000000, float:0.5)
            float r13 = r13 * r7
            r7 = 1022739087(0x3cf5c28f, float:0.03)
            int r7 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r7 > 0) goto L_0x01da
            boolean r7 = r12.quickScaleMoved
            if (r7 == 0) goto L_0x02e9
        L_0x01da:
            r12.quickScaleMoved = r5
            float r7 = r12.quickScaleLastDistance
            int r7 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r7 <= 0) goto L_0x01e8
            if (r1 == 0) goto L_0x01e7
            float r13 = r13 + r2
            r2 = r13
            goto L_0x01e8
        L_0x01e7:
            float r2 = r2 - r13
        L_0x01e8:
            float r13 = r12.scale
            double r7 = (double) r13
            float r13 = r12.minScale()
            float r1 = r12.maxScale
            float r9 = r12.scale
            float r9 = r9 * r2
            float r1 = java.lang.Math.min(r1, r9)
            float r13 = java.lang.Math.max(r13, r1)
            r12.scale = r13
            boolean r13 = r12.panEnabled
            if (r13 == 0) goto L_0x0294
            android.graphics.PointF r13 = r12.vCenterStart
            float r13 = r13.x
            android.graphics.PointF r1 = r12.vTranslateStart
            float r1 = r1.x
            float r13 = r13 - r1
            android.graphics.PointF r1 = r12.vCenterStart
            float r1 = r1.y
            android.graphics.PointF r2 = r12.vTranslateStart
            float r2 = r2.y
            float r1 = r1 - r2
            float r2 = r12.scale
            float r3 = r12.scaleStart
            float r2 = r2 / r3
            float r13 = r13 * r2
            float r2 = r12.scale
            float r3 = r12.scaleStart
            float r2 = r2 / r3
            float r1 = r1 * r2
            android.graphics.PointF r2 = r12.vTranslate
            android.graphics.PointF r3 = r12.vCenterStart
            float r3 = r3.x
            float r3 = r3 - r13
            r2.x = r3
            android.graphics.PointF r13 = r12.vTranslate
            android.graphics.PointF r2 = r12.vCenterStart
            float r2 = r2.y
            float r2 = r2 - r1
            r13.y = r2
            int r13 = r12.sHeight()
            double r1 = (double) r13
            double r1 = r1 * r7
            int r13 = r12.getHeight()
            double r9 = (double) r13
            int r13 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r13 >= 0) goto L_0x0257
            float r13 = r12.scale
            int r1 = r12.sHeight()
            float r1 = (float) r1
            float r13 = r13 * r1
            int r1 = r12.getHeight()
            float r1 = (float) r1
            int r13 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r13 >= 0) goto L_0x0279
        L_0x0257:
            int r13 = r12.sWidth()
            double r1 = (double) r13
            double r7 = r7 * r1
            int r13 = r12.getWidth()
            double r1 = (double) r13
            int r13 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r13 >= 0) goto L_0x02e9
            float r13 = r12.scale
            int r1 = r12.sWidth()
            float r1 = (float) r1
            float r13 = r13 * r1
            int r1 = r12.getWidth()
            float r1 = (float) r1
            int r13 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r13 < 0) goto L_0x02e9
        L_0x0279:
            r12.fitToBounds(r5)
            android.graphics.PointF r13 = r12.vCenterStart
            android.graphics.PointF r0 = r12.quickScaleSCenter
            android.graphics.PointF r0 = r12.sourceToViewCoord(r0)
            r13.set(r0)
            android.graphics.PointF r13 = r12.vTranslateStart
            android.graphics.PointF r0 = r12.vTranslate
            r13.set(r0)
            float r13 = r12.scale
            r12.scaleStart = r13
            r0 = 0
            goto L_0x02e9
        L_0x0294:
            android.graphics.PointF r13 = r12.sRequestedCenter
            if (r13 == 0) goto L_0x02bf
            android.graphics.PointF r13 = r12.vTranslate
            int r1 = r12.getWidth()
            int r1 = r1 / r3
            float r1 = (float) r1
            float r2 = r12.scale
            android.graphics.PointF r6 = r12.sRequestedCenter
            float r6 = r6.x
            float r2 = r2 * r6
            float r1 = r1 - r2
            r13.x = r1
            android.graphics.PointF r13 = r12.vTranslate
            int r1 = r12.getHeight()
            int r1 = r1 / r3
            float r1 = (float) r1
            float r2 = r12.scale
            android.graphics.PointF r3 = r12.sRequestedCenter
            float r3 = r3.y
            float r2 = r2 * r3
            float r1 = r1 - r2
            r13.y = r1
            goto L_0x02e9
        L_0x02bf:
            android.graphics.PointF r13 = r12.vTranslate
            int r1 = r12.getWidth()
            int r1 = r1 / r3
            float r1 = (float) r1
            float r2 = r12.scale
            int r6 = r12.sWidth()
            int r6 = r6 / r3
            float r6 = (float) r6
            float r2 = r2 * r6
            float r1 = r1 - r2
            r13.x = r1
            android.graphics.PointF r13 = r12.vTranslate
            int r1 = r12.getHeight()
            int r1 = r1 / r3
            float r1 = (float) r1
            float r2 = r12.scale
            int r6 = r12.sHeight()
            int r6 = r6 / r3
            float r3 = (float) r6
            float r2 = r2 * r3
            float r1 = r1 - r2
            r13.y = r1
        L_0x02e9:
            r12.quickScaleLastDistance = r0
            r12.fitToBounds(r5)
            boolean r13 = r12.eagerLoadingEnabled
            r12.refreshRequiredTiles(r13)
        L_0x02f3:
            r13 = 1
            goto L_0x03d8
        L_0x02f6:
            boolean r0 = r12.isZooming
            if (r0 != 0) goto L_0x03d7
            float r0 = r13.getX()
            android.graphics.PointF r2 = r12.vCenterStart
            float r2 = r2.x
            float r0 = r0 - r2
            float r0 = java.lang.Math.abs(r0)
            float r2 = r13.getY()
            android.graphics.PointF r3 = r12.vCenterStart
            float r3 = r3.y
            float r2 = r2 - r3
            float r2 = java.lang.Math.abs(r2)
            float r3 = r12.density
            float r3 = r3 * r1
            int r1 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x0324
            int r1 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x0324
            boolean r1 = r12.isPanning
            if (r1 == 0) goto L_0x03d7
        L_0x0324:
            android.graphics.PointF r1 = r12.vTranslate
            android.graphics.PointF r6 = r12.vTranslateStart
            float r6 = r6.x
            float r7 = r13.getX()
            android.graphics.PointF r8 = r12.vCenterStart
            float r8 = r8.x
            float r7 = r7 - r8
            float r6 = r6 + r7
            r1.x = r6
            android.graphics.PointF r1 = r12.vTranslate
            android.graphics.PointF r6 = r12.vTranslateStart
            float r6 = r6.y
            float r13 = r13.getY()
            android.graphics.PointF r7 = r12.vCenterStart
            float r7 = r7.y
            float r13 = r13 - r7
            float r6 = r6 + r13
            r1.y = r6
            android.graphics.PointF r13 = r12.vTranslate
            float r13 = r13.x
            android.graphics.PointF r1 = r12.vTranslate
            float r1 = r1.y
            r12.fitToBounds(r5)
            android.graphics.PointF r6 = r12.vTranslate
            float r6 = r6.x
            int r13 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r13 == 0) goto L_0x035d
            r13 = 1
            goto L_0x035e
        L_0x035d:
            r13 = 0
        L_0x035e:
            android.graphics.PointF r6 = r12.vTranslate
            float r6 = r6.y
            int r6 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r6 == 0) goto L_0x0368
            r6 = 1
            goto L_0x0369
        L_0x0368:
            r6 = 0
        L_0x0369:
            if (r13 == 0) goto L_0x0375
            int r7 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r7 <= 0) goto L_0x0375
            boolean r7 = r12.isPanning
            if (r7 != 0) goto L_0x0375
            r7 = 1
            goto L_0x0376
        L_0x0375:
            r7 = 0
        L_0x0376:
            if (r6 == 0) goto L_0x0382
            int r8 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r8 <= 0) goto L_0x0382
            boolean r8 = r12.isPanning
            if (r8 != 0) goto L_0x0382
            r8 = 1
            goto L_0x0383
        L_0x0382:
            r8 = 0
        L_0x0383:
            android.graphics.PointF r9 = r12.vTranslate
            float r9 = r9.y
            int r1 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r1 != 0) goto L_0x0395
            r1 = 1077936128(0x40400000, float:3.0)
            float r1 = r1 * r3
            int r1 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0395
            r1 = 1
            goto L_0x0396
        L_0x0395:
            r1 = 0
        L_0x0396:
            if (r7 != 0) goto L_0x03a7
            if (r8 != 0) goto L_0x03a7
            if (r13 == 0) goto L_0x03a4
            if (r6 == 0) goto L_0x03a4
            if (r1 != 0) goto L_0x03a4
            boolean r13 = r12.isPanning
            if (r13 == 0) goto L_0x03a7
        L_0x03a4:
            r12.isPanning = r5
            goto L_0x03b9
        L_0x03a7:
            int r13 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r13 > 0) goto L_0x03af
            int r13 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r13 <= 0) goto L_0x03b9
        L_0x03af:
            r12.maxTouchCount = r4
            android.os.Handler r13 = r12.handler
            r13.removeMessages(r5)
            r12.requestDisallowInterceptTouchEvent(r4)
        L_0x03b9:
            boolean r13 = r12.panEnabled
            if (r13 != 0) goto L_0x03d0
            android.graphics.PointF r13 = r12.vTranslate
            android.graphics.PointF r0 = r12.vTranslateStart
            float r0 = r0.x
            r13.x = r0
            android.graphics.PointF r13 = r12.vTranslate
            android.graphics.PointF r0 = r12.vTranslateStart
            float r0 = r0.y
            r13.y = r0
            r12.requestDisallowInterceptTouchEvent(r4)
        L_0x03d0:
            boolean r13 = r12.eagerLoadingEnabled
            r12.refreshRequiredTiles(r13)
            goto L_0x02f3
        L_0x03d7:
            r13 = 0
        L_0x03d8:
            if (r13 == 0) goto L_0x04d6
            android.os.Handler r13 = r12.handler
            r13.removeMessages(r5)
            r12.invalidate()
            return r5
        L_0x03e3:
            android.os.Handler r1 = r12.handler
            r1.removeMessages(r5)
            boolean r1 = r12.isQuickScaling
            if (r1 == 0) goto L_0x03f9
            r12.isQuickScaling = r4
            boolean r1 = r12.quickScaleMoved
            if (r1 != 0) goto L_0x03f9
            android.graphics.PointF r1 = r12.quickScaleSCenter
            android.graphics.PointF r2 = r12.vCenterStart
            r12.doubleTapZoom(r1, r2)
        L_0x03f9:
            int r1 = r12.maxTouchCount
            if (r1 <= 0) goto L_0x044a
            boolean r1 = r12.isZooming
            if (r1 != 0) goto L_0x0405
            boolean r1 = r12.isPanning
            if (r1 == 0) goto L_0x044a
        L_0x0405:
            boolean r1 = r12.isZooming
            if (r1 == 0) goto L_0x043b
            if (r0 != r3) goto L_0x043b
            r12.isPanning = r5
            android.graphics.PointF r1 = r12.vTranslateStart
            android.graphics.PointF r2 = r12.vTranslate
            float r2 = r2.x
            android.graphics.PointF r6 = r12.vTranslate
            float r6 = r6.y
            r1.set(r2, r6)
            int r1 = r13.getActionIndex()
            if (r1 != r5) goto L_0x042e
            android.graphics.PointF r1 = r12.vCenterStart
            float r2 = r13.getX(r4)
            float r13 = r13.getY(r4)
            r1.set(r2, r13)
            goto L_0x043b
        L_0x042e:
            android.graphics.PointF r1 = r12.vCenterStart
            float r2 = r13.getX(r5)
            float r13 = r13.getY(r5)
            r1.set(r2, r13)
        L_0x043b:
            r13 = 3
            if (r0 >= r13) goto L_0x0440
            r12.isZooming = r4
        L_0x0440:
            if (r0 >= r3) goto L_0x0446
            r12.isPanning = r4
            r12.maxTouchCount = r4
        L_0x0446:
            r12.refreshRequiredTiles(r5)
            return r5
        L_0x044a:
            if (r0 != r5) goto L_0x0452
            r12.isZooming = r4
            r12.isPanning = r4
            r12.maxTouchCount = r4
        L_0x0452:
            return r5
        L_0x0453:
            r1 = 0
            r12.anim = r1
            r12.requestDisallowInterceptTouchEvent(r5)
            int r1 = r12.maxTouchCount
            int r1 = java.lang.Math.max(r1, r0)
            r12.maxTouchCount = r1
            if (r0 < r3) goto L_0x04b0
            boolean r0 = r12.zoomEnabled
            if (r0 == 0) goto L_0x04a8
            float r0 = r13.getX(r4)
            float r1 = r13.getX(r5)
            float r3 = r13.getY(r4)
            float r6 = r13.getY(r5)
            float r0 = r12.distance(r0, r1, r3, r6)
            float r1 = r12.scale
            r12.scaleStart = r1
            r12.vDistStart = r0
            android.graphics.PointF r0 = r12.vTranslateStart
            android.graphics.PointF r1 = r12.vTranslate
            float r1 = r1.x
            android.graphics.PointF r3 = r12.vTranslate
            float r3 = r3.y
            r0.set(r1, r3)
            android.graphics.PointF r0 = r12.vCenterStart
            float r1 = r13.getX(r4)
            float r3 = r13.getX(r5)
            float r1 = r1 + r3
            float r1 = r1 / r2
            float r3 = r13.getY(r4)
            float r13 = r13.getY(r5)
            float r3 = r3 + r13
            float r3 = r3 / r2
            r0.set(r1, r3)
            goto L_0x04aa
        L_0x04a8:
            r12.maxTouchCount = r4
        L_0x04aa:
            android.os.Handler r13 = r12.handler
            r13.removeMessages(r5)
            goto L_0x04d5
        L_0x04b0:
            boolean r0 = r12.isQuickScaling
            if (r0 != 0) goto L_0x04d5
            android.graphics.PointF r0 = r12.vTranslateStart
            android.graphics.PointF r1 = r12.vTranslate
            float r1 = r1.x
            android.graphics.PointF r2 = r12.vTranslate
            float r2 = r2.y
            r0.set(r1, r2)
            android.graphics.PointF r0 = r12.vCenterStart
            float r1 = r13.getX()
            float r13 = r13.getY()
            r0.set(r1, r13)
            android.os.Handler r13 = r12.handler
            r0 = 600(0x258, double:2.964E-321)
            r13.sendEmptyMessageDelayed(r5, r0)
        L_0x04d5:
            return r5
        L_0x04d6:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.onTouchEventInternal(android.view.MotionEvent):boolean");
    }

    private void requestDisallowInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    /* access modifiers changed from: private */
    public void doubleTapZoom(PointF pointF, PointF pointF2) {
        if (!this.panEnabled) {
            if (this.sRequestedCenter != null) {
                pointF.x = this.sRequestedCenter.x;
                pointF.y = this.sRequestedCenter.y;
            } else {
                pointF.x = (float) (sWidth() / 2);
                pointF.y = (float) (sHeight() / 2);
            }
        }
        float min = Math.min(this.maxScale, this.doubleTapZoomScale);
        boolean z = ((double) this.scale) <= ((double) min) * 0.9d || this.scale == this.minScale;
        if (!z) {
            min = minScale();
        }
        float f = min;
        if (this.doubleTapZoomStyle == 3) {
            setScaleAndCenter(f, pointF);
        } else if (this.doubleTapZoomStyle == 2 || !z || !this.panEnabled) {
            new AnimationBuilder(f, pointF).withInterruptible(false).withDuration((long) this.doubleTapZoomDuration).withOrigin(4).start();
        } else if (this.doubleTapZoomStyle == 1) {
            AnimationBuilder animationBuilder = new AnimationBuilder(f, pointF, pointF2);
            animationBuilder.withInterruptible(false).withDuration((long) this.doubleTapZoomDuration).withOrigin(4).start();
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        Iterator it;
        int i2;
        Iterator it2;
        Tile tile;
        Canvas canvas2 = canvas;
        super.onDraw(canvas);
        createPaints();
        if (this.sWidth != 0 && this.sHeight != 0 && getWidth() != 0 && getHeight() != 0) {
            if (this.tileMap == null && this.decoder != null) {
                initialiseBaseLayer(getMaxBitmapDimensions(canvas));
            }
            if (checkReady()) {
                preDraw();
                if (!(this.anim == null || this.anim.vFocusStart == null)) {
                    float f = this.scale;
                    if (this.vTranslateBefore == null) {
                        this.vTranslateBefore = new PointF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    }
                    this.vTranslateBefore.set(this.vTranslate);
                    long currentTimeMillis = System.currentTimeMillis() - this.anim.time;
                    boolean z = currentTimeMillis > this.anim.duration;
                    long min = Math.min(currentTimeMillis, this.anim.duration);
                    this.scale = ease(this.anim.easing, min, this.anim.scaleStart, this.anim.scaleEnd - this.anim.scaleStart, this.anim.duration);
                    float ease = ease(this.anim.easing, min, this.anim.vFocusStart.x, this.anim.vFocusEnd.x - this.anim.vFocusStart.x, this.anim.duration);
                    float ease2 = ease(this.anim.easing, min, this.anim.vFocusStart.y, this.anim.vFocusEnd.y - this.anim.vFocusStart.y, this.anim.duration);
                    this.vTranslate.x -= sourceToViewX(this.anim.sCenterEnd.x) - ease;
                    this.vTranslate.y -= sourceToViewY(this.anim.sCenterEnd.y) - ease2;
                    fitToBounds(z || this.anim.scaleStart == this.anim.scaleEnd);
                    sendStateChanged(f, this.vTranslateBefore, this.anim.origin);
                    refreshRequiredTiles(z);
                    if (z) {
                        if (this.anim.listener != null) {
                            try {
                                this.anim.listener.onComplete();
                            } catch (Exception e) {
                                Log.w(TAG, "Error thrown by animation listener", e);
                            }
                        }
                        this.anim = null;
                    }
                    invalidate();
                }
                if (this.tileMap == null || !isBaseLayerReady()) {
                    i = 35;
                    if (this.bitmap != null) {
                        float f2 = this.scale;
                        float f3 = this.scale;
                        if (this.bitmapIsPreview) {
                            f2 = this.scale * (((float) this.sWidth) / ((float) this.bitmap.getWidth()));
                            f3 = this.scale * (((float) this.sHeight) / ((float) this.bitmap.getHeight()));
                        }
                        if (this.matrix == null) {
                            this.matrix = new Matrix();
                        }
                        this.matrix.reset();
                        this.matrix.postScale(f2, f3);
                        this.matrix.postRotate((float) getRequiredRotation());
                        this.matrix.postTranslate(this.vTranslate.x, this.vTranslate.y);
                        if (getRequiredRotation() == 180) {
                            this.matrix.postTranslate(this.scale * ((float) this.sWidth), this.scale * ((float) this.sHeight));
                        } else if (getRequiredRotation() == 90) {
                            this.matrix.postTranslate(this.scale * ((float) this.sHeight), BitmapDescriptorFactory.HUE_RED);
                        } else if (getRequiredRotation() == 270) {
                            this.matrix.postTranslate(BitmapDescriptorFactory.HUE_RED, this.scale * ((float) this.sWidth));
                        }
                        if (this.tileBgPaint != null) {
                            if (this.sRect == null) {
                                this.sRect = new RectF();
                            }
                            this.sRect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) (this.bitmapIsPreview ? this.bitmap.getWidth() : this.sWidth), (float) (this.bitmapIsPreview ? this.bitmap.getHeight() : this.sHeight));
                            this.matrix.mapRect(this.sRect);
                            canvas2.drawRect(this.sRect, this.tileBgPaint);
                        }
                        canvas2.drawBitmap(this.bitmap, this.matrix, this.bitmapPaint);
                    }
                } else {
                    int min2 = Math.min(this.fullImageSampleSize, calculateInSampleSize(this.scale));
                    boolean z2 = false;
                    for (Entry entry : this.tileMap.entrySet()) {
                        if (((Integer) entry.getKey()).intValue() == min2) {
                            for (Tile tile2 : (List) entry.getValue()) {
                                if (tile2.visible && (tile2.loading || tile2.bitmap == null)) {
                                    z2 = true;
                                }
                            }
                        }
                    }
                    Iterator it3 = this.tileMap.entrySet().iterator();
                    while (it3.hasNext()) {
                        Entry entry2 = (Entry) it3.next();
                        if (((Integer) entry2.getKey()).intValue() == min2 || z2) {
                            for (Iterator it4 = ((List) entry2.getValue()).iterator(); it4.hasNext(); it4 = it) {
                                Tile tile3 = (Tile) it4.next();
                                sourceToViewRect(tile3.sRect, tile3.vRect);
                                if (tile3.loading || tile3.bitmap == null) {
                                    tile = tile3;
                                    it = it4;
                                    it2 = it3;
                                    i2 = min2;
                                    if (tile.loading && this.debug) {
                                        canvas2.drawText("LOADING", (float) (tile.vRect.left + px(5)), (float) (tile.vRect.top + px(35)), this.debugTextPaint);
                                        if (tile.visible && this.debug) {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("ISS ");
                                            sb.append(tile.sampleSize);
                                            sb.append(" RECT ");
                                            sb.append(tile.sRect.top);
                                            sb.append(",");
                                            sb.append(tile.sRect.left);
                                            sb.append(",");
                                            sb.append(tile.sRect.bottom);
                                            sb.append(",");
                                            sb.append(tile.sRect.right);
                                            canvas2.drawText(sb.toString(), (float) (tile.vRect.left + px(5)), (float) (tile.vRect.top + px(15)), this.debugTextPaint);
                                        }
                                        it3 = it2;
                                        min2 = i2;
                                    }
                                } else {
                                    if (this.tileBgPaint != null) {
                                        canvas2.drawRect(tile3.vRect, this.tileBgPaint);
                                    }
                                    if (this.matrix == null) {
                                        this.matrix = new Matrix();
                                    }
                                    this.matrix.reset();
                                    tile = tile3;
                                    it = it4;
                                    it2 = it3;
                                    i2 = min2;
                                    setMatrixArray(this.srcArray, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) tile3.bitmap.getWidth(), BitmapDescriptorFactory.HUE_RED, (float) tile3.bitmap.getWidth(), (float) tile3.bitmap.getHeight(), BitmapDescriptorFactory.HUE_RED, (float) tile3.bitmap.getHeight());
                                    if (getRequiredRotation() == 0) {
                                        setMatrixArray(this.dstArray, (float) tile.vRect.left, (float) tile.vRect.top, (float) tile.vRect.right, (float) tile.vRect.top, (float) tile.vRect.right, (float) tile.vRect.bottom, (float) tile.vRect.left, (float) tile.vRect.bottom);
                                    } else if (getRequiredRotation() == 90) {
                                        setMatrixArray(this.dstArray, (float) tile.vRect.right, (float) tile.vRect.top, (float) tile.vRect.right, (float) tile.vRect.bottom, (float) tile.vRect.left, (float) tile.vRect.bottom, (float) tile.vRect.left, (float) tile.vRect.top);
                                    } else if (getRequiredRotation() == 180) {
                                        setMatrixArray(this.dstArray, (float) tile.vRect.right, (float) tile.vRect.bottom, (float) tile.vRect.left, (float) tile.vRect.bottom, (float) tile.vRect.left, (float) tile.vRect.top, (float) tile.vRect.right, (float) tile.vRect.top);
                                    } else if (getRequiredRotation() == 270) {
                                        setMatrixArray(this.dstArray, (float) tile.vRect.left, (float) tile.vRect.bottom, (float) tile.vRect.left, (float) tile.vRect.top, (float) tile.vRect.right, (float) tile.vRect.top, (float) tile.vRect.right, (float) tile.vRect.bottom);
                                    }
                                    this.matrix.setPolyToPoly(this.srcArray, 0, this.dstArray, 0, 4);
                                    canvas2.drawBitmap(tile.bitmap, this.matrix, this.bitmapPaint);
                                    if (this.debug) {
                                        canvas2.drawRect(tile.vRect, this.debugLinePaint);
                                    }
                                }
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("ISS ");
                                sb2.append(tile.sampleSize);
                                sb2.append(" RECT ");
                                sb2.append(tile.sRect.top);
                                sb2.append(",");
                                sb2.append(tile.sRect.left);
                                sb2.append(",");
                                sb2.append(tile.sRect.bottom);
                                sb2.append(",");
                                sb2.append(tile.sRect.right);
                                canvas2.drawText(sb2.toString(), (float) (tile.vRect.left + px(5)), (float) (tile.vRect.top + px(15)), this.debugTextPaint);
                                it3 = it2;
                                min2 = i2;
                            }
                        }
                        it3 = it3;
                        min2 = min2;
                    }
                    i = 35;
                }
                if (this.debug) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Scale: ");
                    sb3.append(String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(this.scale)}));
                    sb3.append(" (");
                    sb3.append(String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(minScale())}));
                    sb3.append(" - ");
                    sb3.append(String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(this.maxScale)}));
                    sb3.append(")");
                    canvas2.drawText(sb3.toString(), (float) px(5), (float) px(15), this.debugTextPaint);
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Translate: ");
                    sb4.append(String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(this.vTranslate.x)}));
                    sb4.append(":");
                    sb4.append(String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(this.vTranslate.y)}));
                    canvas2.drawText(sb4.toString(), (float) px(5), (float) px(30), this.debugTextPaint);
                    PointF center = getCenter();
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("Source center: ");
                    sb5.append(String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(center.x)}));
                    sb5.append(":");
                    sb5.append(String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(center.y)}));
                    canvas2.drawText(sb5.toString(), (float) px(5), (float) px(45), this.debugTextPaint);
                    if (this.anim != null) {
                        PointF sourceToViewCoord = sourceToViewCoord(this.anim.sCenterStart);
                        PointF sourceToViewCoord2 = sourceToViewCoord(this.anim.sCenterEndRequested);
                        PointF sourceToViewCoord3 = sourceToViewCoord(this.anim.sCenterEnd);
                        canvas2.drawCircle(sourceToViewCoord.x, sourceToViewCoord.y, (float) px(10), this.debugLinePaint);
                        this.debugLinePaint.setColor(SupportMenu.CATEGORY_MASK);
                        canvas2.drawCircle(sourceToViewCoord2.x, sourceToViewCoord2.y, (float) px(20), this.debugLinePaint);
                        this.debugLinePaint.setColor(-16776961);
                        canvas2.drawCircle(sourceToViewCoord3.x, sourceToViewCoord3.y, (float) px(25), this.debugLinePaint);
                        this.debugLinePaint.setColor(-16711681);
                        canvas2.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) px(30), this.debugLinePaint);
                    }
                    if (this.vCenterStart != null) {
                        this.debugLinePaint.setColor(SupportMenu.CATEGORY_MASK);
                        canvas2.drawCircle(this.vCenterStart.x, this.vCenterStart.y, (float) px(20), this.debugLinePaint);
                    }
                    if (this.quickScaleSCenter != null) {
                        this.debugLinePaint.setColor(-16776961);
                        canvas2.drawCircle(sourceToViewX(this.quickScaleSCenter.x), sourceToViewY(this.quickScaleSCenter.y), (float) px(i), this.debugLinePaint);
                    }
                    if (this.quickScaleVStart != null && this.isQuickScaling) {
                        this.debugLinePaint.setColor(-16711681);
                        canvas2.drawCircle(this.quickScaleVStart.x, this.quickScaleVStart.y, (float) px(30), this.debugLinePaint);
                    }
                    this.debugLinePaint.setColor(-65281);
                }
            }
        }
    }

    private void setMatrixArray(float[] fArr, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        fArr[3] = f4;
        fArr[4] = f5;
        fArr[5] = f6;
        fArr[6] = f7;
        fArr[7] = f8;
    }

    private boolean isBaseLayerReady() {
        boolean z = true;
        if (this.bitmap != null && !this.bitmapIsPreview) {
            return true;
        }
        if (this.tileMap == null) {
            return false;
        }
        for (Entry entry : this.tileMap.entrySet()) {
            if (((Integer) entry.getKey()).intValue() == this.fullImageSampleSize) {
                for (Tile tile : (List) entry.getValue()) {
                    if (tile.loading || tile.bitmap == null) {
                        z = false;
                    }
                }
            }
        }
        return z;
    }

    private boolean checkReady() {
        boolean z = getWidth() > 0 && getHeight() > 0 && this.sWidth > 0 && this.sHeight > 0 && (this.bitmap != null || isBaseLayerReady());
        if (!this.readySent && z) {
            preDraw();
            this.readySent = true;
            onReady();
            if (this.onImageEventListener != null) {
                this.onImageEventListener.onReady();
            }
        }
        return z;
    }

    private boolean checkImageLoaded() {
        boolean isBaseLayerReady = isBaseLayerReady();
        if (!this.imageLoadedSent && isBaseLayerReady) {
            preDraw();
            this.imageLoadedSent = true;
            onImageLoaded();
            if (this.onImageEventListener != null) {
                this.onImageEventListener.onImageLoaded();
            }
        }
        return isBaseLayerReady;
    }

    private void createPaints() {
        if (this.bitmapPaint == null) {
            this.bitmapPaint = new Paint();
            this.bitmapPaint.setAntiAlias(true);
            this.bitmapPaint.setFilterBitmap(true);
            this.bitmapPaint.setDither(true);
        }
        if ((this.debugTextPaint == null || this.debugLinePaint == null) && this.debug) {
            this.debugTextPaint = new Paint();
            this.debugTextPaint.setTextSize((float) px(12));
            this.debugTextPaint.setColor(-65281);
            this.debugTextPaint.setStyle(Style.FILL);
            this.debugLinePaint = new Paint();
            this.debugLinePaint.setColor(-65281);
            this.debugLinePaint.setStyle(Style.STROKE);
            this.debugLinePaint.setStrokeWidth((float) px(1));
        }
    }

    private synchronized void initialiseBaseLayer(@NonNull Point point) {
        debug("initialiseBaseLayer maxTileDimensions=%dx%d", Integer.valueOf(point.x), Integer.valueOf(point.y));
        this.satTemp = new ScaleAndTranslate(BitmapDescriptorFactory.HUE_RED, new PointF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        fitToBounds(true, this.satTemp);
        this.fullImageSampleSize = calculateInSampleSize(this.satTemp.scale);
        if (this.fullImageSampleSize > 1) {
            this.fullImageSampleSize /= 2;
        }
        if (this.fullImageSampleSize != 1 || this.sRegion != null || sWidth() >= point.x || sHeight() >= point.y) {
            initialiseTileMap(point);
            for (Tile tileLoadTask : (List) this.tileMap.get(Integer.valueOf(this.fullImageSampleSize))) {
                execute(new TileLoadTask(this, this.decoder, tileLoadTask));
            }
            refreshRequiredTiles(true);
        } else {
            this.decoder.recycle();
            this.decoder = null;
            BitmapLoadTask bitmapLoadTask = new BitmapLoadTask(this, getContext(), this.bitmapDecoderFactory, this.uri, false);
            execute(bitmapLoadTask);
        }
    }

    private void refreshRequiredTiles(boolean z) {
        if (this.decoder != null && this.tileMap != null) {
            int min = Math.min(this.fullImageSampleSize, calculateInSampleSize(this.scale));
            for (Entry value : this.tileMap.entrySet()) {
                for (Tile tile : (List) value.getValue()) {
                    if (tile.sampleSize < min || (tile.sampleSize > min && tile.sampleSize != this.fullImageSampleSize)) {
                        tile.visible = false;
                        if (tile.bitmap != null) {
                            tile.bitmap.recycle();
                            tile.bitmap = null;
                        }
                    }
                    if (tile.sampleSize == min) {
                        if (tileVisible(tile)) {
                            tile.visible = true;
                            if (!tile.loading && tile.bitmap == null && z) {
                                execute(new TileLoadTask(this, this.decoder, tile));
                            }
                        } else if (tile.sampleSize != this.fullImageSampleSize) {
                            tile.visible = false;
                            if (tile.bitmap != null) {
                                tile.bitmap.recycle();
                                tile.bitmap = null;
                            }
                        }
                    } else if (tile.sampleSize == this.fullImageSampleSize) {
                        tile.visible = true;
                    }
                }
            }
        }
    }

    private boolean tileVisible(Tile tile) {
        return viewToSourceX(BitmapDescriptorFactory.HUE_RED) <= ((float) tile.sRect.right) && ((float) tile.sRect.left) <= viewToSourceX((float) getWidth()) && viewToSourceY(BitmapDescriptorFactory.HUE_RED) <= ((float) tile.sRect.bottom) && ((float) tile.sRect.top) <= viewToSourceY((float) getHeight());
    }

    private void preDraw() {
        if (getWidth() != 0 && getHeight() != 0 && this.sWidth > 0 && this.sHeight > 0) {
            if (!(this.sPendingCenter == null || this.pendingScale == null)) {
                this.scale = this.pendingScale.floatValue();
                if (this.vTranslate == null) {
                    this.vTranslate = new PointF();
                }
                this.vTranslate.x = ((float) (getWidth() / 2)) - (this.scale * this.sPendingCenter.x);
                this.vTranslate.y = ((float) (getHeight() / 2)) - (this.scale * this.sPendingCenter.y);
                this.sPendingCenter = null;
                this.pendingScale = null;
                fitToBounds(true);
                refreshRequiredTiles(true);
            }
            fitToBounds(false);
        }
    }

    private int calculateInSampleSize(float f) {
        int i;
        if (this.minimumTileDpi > 0) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            f *= ((float) this.minimumTileDpi) / ((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f);
        }
        int sWidth2 = (int) (((float) sWidth()) * f);
        int sHeight2 = (int) (((float) sHeight()) * f);
        if (sWidth2 == 0 || sHeight2 == 0) {
            return 32;
        }
        int i2 = 1;
        if (sHeight() > sHeight2 || sWidth() > sWidth2) {
            i = Math.round(((float) sHeight()) / ((float) sHeight2));
            int round = Math.round(((float) sWidth()) / ((float) sWidth2));
            if (i >= round) {
                i = round;
            }
        } else {
            i = 1;
        }
        while (true) {
            int i3 = i2 * 2;
            if (i3 >= i) {
                return i2;
            }
            i2 = i3;
        }
    }

    /* access modifiers changed from: private */
    public void fitToBounds(boolean z, ScaleAndTranslate scaleAndTranslate) {
        float f;
        float f2;
        if (this.panLimit == 2 && isReady()) {
            z = false;
        }
        PointF access$4800 = scaleAndTranslate.vTranslate;
        float limitedScale = limitedScale(scaleAndTranslate.scale);
        float sWidth2 = ((float) sWidth()) * limitedScale;
        float sHeight2 = ((float) sHeight()) * limitedScale;
        if (this.panLimit == 3 && isReady()) {
            access$4800.x = Math.max(access$4800.x, ((float) (getWidth() / 2)) - sWidth2);
            access$4800.y = Math.max(access$4800.y, ((float) (getHeight() / 2)) - sHeight2);
        } else if (z) {
            access$4800.x = Math.max(access$4800.x, ((float) getWidth()) - sWidth2);
            access$4800.y = Math.max(access$4800.y, ((float) getHeight()) - sHeight2);
        } else {
            access$4800.x = Math.max(access$4800.x, -sWidth2);
            access$4800.y = Math.max(access$4800.y, -sHeight2);
        }
        float f3 = 0.5f;
        float paddingLeft = (getPaddingLeft() > 0 || getPaddingRight() > 0) ? ((float) getPaddingLeft()) / ((float) (getPaddingLeft() + getPaddingRight())) : 0.5f;
        if (getPaddingTop() > 0 || getPaddingBottom() > 0) {
            f3 = ((float) getPaddingTop()) / ((float) (getPaddingTop() + getPaddingBottom()));
        }
        if (this.panLimit == 3 && isReady()) {
            f = (float) Math.max(0, getWidth() / 2);
            f2 = (float) Math.max(0, getHeight() / 2);
        } else if (z) {
            f = Math.max(BitmapDescriptorFactory.HUE_RED, (((float) getWidth()) - sWidth2) * paddingLeft);
            f2 = Math.max(BitmapDescriptorFactory.HUE_RED, (((float) getHeight()) - sHeight2) * f3);
        } else {
            f = (float) Math.max(0, getWidth());
            f2 = (float) Math.max(0, getHeight());
        }
        access$4800.x = Math.min(access$4800.x, f);
        access$4800.y = Math.min(access$4800.y, f2);
        scaleAndTranslate.scale = limitedScale;
    }

    private void fitToBounds(boolean z) {
        boolean z2;
        if (this.vTranslate == null) {
            z2 = true;
            this.vTranslate = new PointF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        } else {
            z2 = false;
        }
        if (this.satTemp == null) {
            this.satTemp = new ScaleAndTranslate(BitmapDescriptorFactory.HUE_RED, new PointF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        }
        this.satTemp.scale = this.scale;
        this.satTemp.vTranslate.set(this.vTranslate);
        fitToBounds(z, this.satTemp);
        this.scale = this.satTemp.scale;
        this.vTranslate.set(this.satTemp.vTranslate);
        if (z2 && this.minimumScaleType != 4) {
            this.vTranslate.set(vTranslateForSCenter((float) (sWidth() / 2), (float) (sHeight() / 2), this.scale));
        }
    }

    private void initialiseTileMap(Point point) {
        Point point2 = point;
        int i = 1;
        debug("initialiseTileMap maxTileDimensions=%dx%d", Integer.valueOf(point2.x), Integer.valueOf(point2.y));
        this.tileMap = new LinkedHashMap();
        int i2 = this.fullImageSampleSize;
        int i3 = 1;
        int i4 = 1;
        while (true) {
            int sWidth2 = sWidth() / i3;
            int sHeight2 = sHeight() / i4;
            int i5 = sWidth2 / i2;
            int i6 = sHeight2 / i2;
            while (true) {
                if (i5 + i3 + i > point2.x || (((double) i5) > ((double) getWidth()) * 1.25d && i2 < this.fullImageSampleSize)) {
                    i3++;
                    sWidth2 = sWidth() / i3;
                    i5 = sWidth2 / i2;
                    i = 1;
                }
            }
            while (true) {
                if (i6 + i4 + i > point2.y || (((double) i6) > ((double) getHeight()) * 1.25d && i2 < this.fullImageSampleSize)) {
                    i4++;
                    sHeight2 = sHeight() / i4;
                    i6 = sHeight2 / i2;
                    i = 1;
                }
            }
            ArrayList arrayList = new ArrayList(i3 * i4);
            int i7 = 0;
            while (i7 < i3) {
                int i8 = 0;
                while (i8 < i4) {
                    Tile tile = new Tile();
                    tile.sampleSize = i2;
                    tile.visible = i2 == this.fullImageSampleSize;
                    tile.sRect = new Rect(i7 * sWidth2, i8 * sHeight2, i7 == i3 + -1 ? sWidth() : (i7 + 1) * sWidth2, i8 == i4 + -1 ? sHeight() : (i8 + 1) * sHeight2);
                    tile.vRect = new Rect(0, 0, 0, 0);
                    tile.fileSRect = new Rect(tile.sRect);
                    arrayList.add(tile);
                    i8++;
                }
                i7++;
            }
            this.tileMap.put(Integer.valueOf(i2), arrayList);
            if (i2 != 1) {
                i2 /= 2;
                i = 1;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void onTilesInited(ImageRegionDecoder imageRegionDecoder, int i, int i2, int i3) {
        debug("onTilesInited sWidth=%d, sHeight=%d, sOrientation=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.orientation));
        if (this.sWidth > 0 && this.sHeight > 0 && !(this.sWidth == i && this.sHeight == i2)) {
            reset(false);
            if (this.bitmap != null) {
                if (!this.bitmapIsCached) {
                    this.bitmap.recycle();
                }
                this.bitmap = null;
                if (this.onImageEventListener != null && this.bitmapIsCached) {
                    this.onImageEventListener.onPreviewReleased();
                }
                this.bitmapIsPreview = false;
                this.bitmapIsCached = false;
            }
        }
        this.decoder = imageRegionDecoder;
        this.sWidth = i;
        this.sHeight = i2;
        this.sOrientation = i3;
        checkReady();
        if (!checkImageLoaded() && this.maxTileWidth > 0 && this.maxTileWidth != Integer.MAX_VALUE && this.maxTileHeight > 0 && this.maxTileHeight != Integer.MAX_VALUE && getWidth() > 0 && getHeight() > 0) {
            initialiseBaseLayer(new Point(this.maxTileWidth, this.maxTileHeight));
        }
        invalidate();
        requestLayout();
    }

    /* access modifiers changed from: private */
    public synchronized void onTileLoaded() {
        debug("onTileLoaded", new Object[0]);
        checkReady();
        checkImageLoaded();
        if (isBaseLayerReady() && this.bitmap != null) {
            if (!this.bitmapIsCached) {
                this.bitmap.recycle();
            }
            this.bitmap = null;
            if (this.onImageEventListener != null && this.bitmapIsCached) {
                this.onImageEventListener.onPreviewReleased();
            }
            this.bitmapIsPreview = false;
            this.bitmapIsCached = false;
        }
        invalidate();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0043, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onPreviewLoaded(android.graphics.Bitmap r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.lang.String r0 = "onPreviewLoaded"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0049 }
            r4.debug(r0, r1)     // Catch:{ all -> 0x0049 }
            android.graphics.Bitmap r0 = r4.bitmap     // Catch:{ all -> 0x0049 }
            if (r0 != 0) goto L_0x0044
            boolean r0 = r4.imageLoadedSent     // Catch:{ all -> 0x0049 }
            if (r0 == 0) goto L_0x0012
            goto L_0x0044
        L_0x0012:
            android.graphics.Rect r0 = r4.pRegion     // Catch:{ all -> 0x0049 }
            if (r0 == 0) goto L_0x0031
            android.graphics.Rect r0 = r4.pRegion     // Catch:{ all -> 0x0049 }
            int r0 = r0.left     // Catch:{ all -> 0x0049 }
            android.graphics.Rect r1 = r4.pRegion     // Catch:{ all -> 0x0049 }
            int r1 = r1.top     // Catch:{ all -> 0x0049 }
            android.graphics.Rect r2 = r4.pRegion     // Catch:{ all -> 0x0049 }
            int r2 = r2.width()     // Catch:{ all -> 0x0049 }
            android.graphics.Rect r3 = r4.pRegion     // Catch:{ all -> 0x0049 }
            int r3 = r3.height()     // Catch:{ all -> 0x0049 }
            android.graphics.Bitmap r5 = android.graphics.Bitmap.createBitmap(r5, r0, r1, r2, r3)     // Catch:{ all -> 0x0049 }
            r4.bitmap = r5     // Catch:{ all -> 0x0049 }
            goto L_0x0033
        L_0x0031:
            r4.bitmap = r5     // Catch:{ all -> 0x0049 }
        L_0x0033:
            r5 = 1
            r4.bitmapIsPreview = r5     // Catch:{ all -> 0x0049 }
            boolean r5 = r4.checkReady()     // Catch:{ all -> 0x0049 }
            if (r5 == 0) goto L_0x0042
            r4.invalidate()     // Catch:{ all -> 0x0049 }
            r4.requestLayout()     // Catch:{ all -> 0x0049 }
        L_0x0042:
            monitor-exit(r4)
            return
        L_0x0044:
            r5.recycle()     // Catch:{ all -> 0x0049 }
            monitor-exit(r4)
            return
        L_0x0049:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.onPreviewLoaded(android.graphics.Bitmap):void");
    }

    /* access modifiers changed from: private */
    public synchronized void onImageLoaded(Bitmap bitmap2, int i, boolean z) {
        debug("onImageLoaded", new Object[0]);
        if (this.sWidth > 0 && this.sHeight > 0 && !(this.sWidth == bitmap2.getWidth() && this.sHeight == bitmap2.getHeight())) {
            reset(false);
        }
        if (this.bitmap != null && !this.bitmapIsCached) {
            this.bitmap.recycle();
        }
        if (!(this.bitmap == null || !this.bitmapIsCached || this.onImageEventListener == null)) {
            this.onImageEventListener.onPreviewReleased();
        }
        this.bitmapIsPreview = false;
        this.bitmapIsCached = z;
        this.bitmap = bitmap2;
        this.sWidth = bitmap2.getWidth();
        this.sHeight = bitmap2.getHeight();
        this.sOrientation = i;
        boolean checkReady = checkReady();
        boolean checkImageLoaded = checkImageLoaded();
        if (checkReady || checkImageLoaded) {
            invalidate();
            requestLayout();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:17|16|24|25|(2:27|55)(1:54)) */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0060, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0061, code lost:
        r10 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0072, code lost:
        r10.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0063 */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    @android.support.annotation.AnyThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getExifOrientation(android.content.Context r10, java.lang.String r11) {
        /*
            r9 = this;
            java.lang.String r0 = "content"
            boolean r0 = r11.startsWith(r0)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0076
            r0 = 0
            java.lang.String[] r5 = new java.lang.String[r1]     // Catch:{ Exception -> 0x0063 }
            java.lang.String r1 = "orientation"
            r5[r2] = r1     // Catch:{ Exception -> 0x0063 }
            android.content.ContentResolver r3 = r10.getContentResolver()     // Catch:{ Exception -> 0x0063 }
            android.net.Uri r4 = android.net.Uri.parse(r11)     // Catch:{ Exception -> 0x0063 }
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r10 = r3.query(r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0063 }
            if (r10 == 0) goto L_0x0057
            boolean r11 = r10.moveToFirst()     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            if (r11 == 0) goto L_0x0057
            int r11 = r10.getInt(r2)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            java.util.List<java.lang.Integer> r0 = VALID_ORIENTATIONS     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            boolean r0 = r0.contains(r1)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            if (r0 == 0) goto L_0x003c
            r0 = -1
            if (r11 == r0) goto L_0x003c
            goto L_0x0058
        L_0x003c:
            java.lang.String r0 = TAG     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            r1.<init>()     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            java.lang.String r3 = "Unsupported orientation: "
            r1.append(r3)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            r1.append(r11)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            java.lang.String r11 = r1.toString()     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            android.util.Log.w(r0, r11)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            goto L_0x0057
        L_0x0053:
            r11 = move-exception
            goto L_0x0070
        L_0x0055:
            r0 = r10
            goto L_0x0063
        L_0x0057:
            r11 = 0
        L_0x0058:
            if (r10 == 0) goto L_0x005d
            r10.close()
        L_0x005d:
            r2 = r11
            goto L_0x00d8
        L_0x0060:
            r11 = move-exception
            r10 = r0
            goto L_0x0070
        L_0x0063:
            java.lang.String r10 = TAG     // Catch:{ all -> 0x0060 }
            java.lang.String r11 = "Could not get orientation of image from media store"
            android.util.Log.w(r10, r11)     // Catch:{ all -> 0x0060 }
            if (r0 == 0) goto L_0x00d8
            r0.close()
            goto L_0x00d8
        L_0x0070:
            if (r10 == 0) goto L_0x0075
            r10.close()
        L_0x0075:
            throw r11
        L_0x0076:
            java.lang.String r10 = "file:///"
            boolean r10 = r11.startsWith(r10)
            if (r10 == 0) goto L_0x00d8
            java.lang.String r10 = "file:///android_asset/"
            boolean r10 = r11.startsWith(r10)
            if (r10 != 0) goto L_0x00d8
            android.support.media.ExifInterface r10 = new android.support.media.ExifInterface     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r0 = "file:///"
            int r0 = r0.length()     // Catch:{ Exception -> 0x00d1 }
            int r0 = r0 - r1
            java.lang.String r11 = r11.substring(r0)     // Catch:{ Exception -> 0x00d1 }
            r10.<init>(r11)     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r11 = "Orientation"
            int r10 = r10.getAttributeInt(r11, r1)     // Catch:{ Exception -> 0x00d1 }
            if (r10 == r1) goto L_0x00d8
            if (r10 != 0) goto L_0x00a1
            goto L_0x00d8
        L_0x00a1:
            r11 = 6
            if (r10 != r11) goto L_0x00a9
            r10 = 90
            r2 = 90
            goto L_0x00d8
        L_0x00a9:
            r11 = 3
            if (r10 != r11) goto L_0x00b1
            r10 = 180(0xb4, float:2.52E-43)
            r2 = 180(0xb4, float:2.52E-43)
            goto L_0x00d8
        L_0x00b1:
            r11 = 8
            if (r10 != r11) goto L_0x00ba
            r10 = 270(0x10e, float:3.78E-43)
            r2 = 270(0x10e, float:3.78E-43)
            goto L_0x00d8
        L_0x00ba:
            java.lang.String r11 = TAG     // Catch:{ Exception -> 0x00d1 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00d1 }
            r0.<init>()     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r1 = "Unsupported EXIF orientation: "
            r0.append(r1)     // Catch:{ Exception -> 0x00d1 }
            r0.append(r10)     // Catch:{ Exception -> 0x00d1 }
            java.lang.String r10 = r0.toString()     // Catch:{ Exception -> 0x00d1 }
            android.util.Log.w(r11, r10)     // Catch:{ Exception -> 0x00d1 }
            goto L_0x00d8
        L_0x00d1:
            java.lang.String r10 = TAG
            java.lang.String r11 = "Could not get EXIF orientation of image"
            android.util.Log.w(r10, r11)
        L_0x00d8:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.getExifOrientation(android.content.Context, java.lang.String):int");
    }

    private void execute(AsyncTask<Void, Void, ?> asyncTask) {
        asyncTask.executeOnExecutor(this.executor, new Void[0]);
    }

    private void restoreState(ImageViewState imageViewState) {
        if (imageViewState != null && VALID_ORIENTATIONS.contains(Integer.valueOf(imageViewState.getOrientation()))) {
            this.orientation = imageViewState.getOrientation();
            this.pendingScale = Float.valueOf(imageViewState.getScale());
            this.sPendingCenter = imageViewState.getCenter();
            invalidate();
        }
    }

    public void setMaxTileSize(int i) {
        this.maxTileWidth = i;
        this.maxTileHeight = i;
    }

    public void setMaxTileSize(int i, int i2) {
        this.maxTileWidth = i;
        this.maxTileHeight = i2;
    }

    @NonNull
    private Point getMaxBitmapDimensions(Canvas canvas) {
        return new Point(Math.min(canvas.getMaximumBitmapWidth(), this.maxTileWidth), Math.min(canvas.getMaximumBitmapHeight(), this.maxTileHeight));
    }

    private int sWidth() {
        int requiredRotation = getRequiredRotation();
        if (requiredRotation == 90 || requiredRotation == 270) {
            return this.sHeight;
        }
        return this.sWidth;
    }

    private int sHeight() {
        int requiredRotation = getRequiredRotation();
        if (requiredRotation == 90 || requiredRotation == 270) {
            return this.sWidth;
        }
        return this.sHeight;
    }

    /* access modifiers changed from: private */
    @AnyThread
    public void fileSRect(Rect rect, Rect rect2) {
        if (getRequiredRotation() == 0) {
            rect2.set(rect);
        } else if (getRequiredRotation() == 90) {
            rect2.set(rect.top, this.sHeight - rect.right, rect.bottom, this.sHeight - rect.left);
        } else if (getRequiredRotation() == 180) {
            rect2.set(this.sWidth - rect.right, this.sHeight - rect.bottom, this.sWidth - rect.left, this.sHeight - rect.top);
        } else {
            rect2.set(this.sWidth - rect.bottom, rect.left, this.sWidth - rect.top, rect.right);
        }
    }

    @AnyThread
    private int getRequiredRotation() {
        if (this.orientation == -1) {
            return this.sOrientation;
        }
        return this.orientation;
    }

    private float distance(float f, float f2, float f3, float f4) {
        float f5 = f - f2;
        float f6 = f3 - f4;
        return (float) Math.sqrt((double) ((f5 * f5) + (f6 * f6)));
    }

    public void recycle() {
        reset(true);
        this.bitmapPaint = null;
        this.debugTextPaint = null;
        this.debugLinePaint = null;
        this.tileBgPaint = null;
    }

    private float viewToSourceX(float f) {
        if (this.vTranslate == null) {
            return Float.NaN;
        }
        return (f - this.vTranslate.x) / this.scale;
    }

    private float viewToSourceY(float f) {
        if (this.vTranslate == null) {
            return Float.NaN;
        }
        return (f - this.vTranslate.y) / this.scale;
    }

    public void viewToFileRect(Rect rect, Rect rect2) {
        if (this.vTranslate != null && this.readySent) {
            rect2.set((int) viewToSourceX((float) rect.left), (int) viewToSourceY((float) rect.top), (int) viewToSourceX((float) rect.right), (int) viewToSourceY((float) rect.bottom));
            fileSRect(rect2, rect2);
            rect2.set(Math.max(0, rect2.left), Math.max(0, rect2.top), Math.min(this.sWidth, rect2.right), Math.min(this.sHeight, rect2.bottom));
            if (this.sRegion != null) {
                rect2.offset(this.sRegion.left, this.sRegion.top);
            }
        }
    }

    public void visibleFileRect(Rect rect) {
        if (this.vTranslate != null && this.readySent) {
            rect.set(0, 0, getWidth(), getHeight());
            viewToFileRect(rect, rect);
        }
    }

    @Nullable
    public final PointF viewToSourceCoord(PointF pointF) {
        return viewToSourceCoord(pointF.x, pointF.y, new PointF());
    }

    @Nullable
    public final PointF viewToSourceCoord(float f, float f2) {
        return viewToSourceCoord(f, f2, new PointF());
    }

    @Nullable
    public final PointF viewToSourceCoord(PointF pointF, @NonNull PointF pointF2) {
        return viewToSourceCoord(pointF.x, pointF.y, pointF2);
    }

    @Nullable
    public final PointF viewToSourceCoord(float f, float f2, @NonNull PointF pointF) {
        if (this.vTranslate == null) {
            return null;
        }
        pointF.set(viewToSourceX(f), viewToSourceY(f2));
        return pointF;
    }

    private float sourceToViewX(float f) {
        if (this.vTranslate == null) {
            return Float.NaN;
        }
        return (f * this.scale) + this.vTranslate.x;
    }

    private float sourceToViewY(float f) {
        if (this.vTranslate == null) {
            return Float.NaN;
        }
        return (f * this.scale) + this.vTranslate.y;
    }

    @Nullable
    public final PointF sourceToViewCoord(PointF pointF) {
        return sourceToViewCoord(pointF.x, pointF.y, new PointF());
    }

    @Nullable
    public final PointF sourceToViewCoord(float f, float f2) {
        return sourceToViewCoord(f, f2, new PointF());
    }

    @Nullable
    public final PointF sourceToViewCoord(PointF pointF, @NonNull PointF pointF2) {
        return sourceToViewCoord(pointF.x, pointF.y, pointF2);
    }

    @Nullable
    public final PointF sourceToViewCoord(float f, float f2, @NonNull PointF pointF) {
        if (this.vTranslate == null) {
            return null;
        }
        pointF.set(sourceToViewX(f), sourceToViewY(f2));
        return pointF;
    }

    private void sourceToViewRect(@NonNull Rect rect, @NonNull Rect rect2) {
        rect2.set((int) sourceToViewX((float) rect.left), (int) sourceToViewY((float) rect.top), (int) sourceToViewX((float) rect.right), (int) sourceToViewY((float) rect.bottom));
    }

    @NonNull
    private PointF vTranslateForSCenter(float f, float f2, float f3) {
        int paddingLeft = getPaddingLeft() + (((getWidth() - getPaddingRight()) - getPaddingLeft()) / 2);
        int paddingTop = getPaddingTop() + (((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2);
        if (this.satTemp == null) {
            this.satTemp = new ScaleAndTranslate(BitmapDescriptorFactory.HUE_RED, new PointF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        }
        this.satTemp.scale = f3;
        this.satTemp.vTranslate.set(((float) paddingLeft) - (f * f3), ((float) paddingTop) - (f2 * f3));
        fitToBounds(true, this.satTemp);
        return this.satTemp.vTranslate;
    }

    /* access modifiers changed from: private */
    @NonNull
    public PointF limitedSCenter(float f, float f2, float f3, @NonNull PointF pointF) {
        PointF vTranslateForSCenter = vTranslateForSCenter(f, f2, f3);
        pointF.set((((float) (getPaddingLeft() + (((getWidth() - getPaddingRight()) - getPaddingLeft()) / 2))) - vTranslateForSCenter.x) / f3, (((float) (getPaddingTop() + (((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2))) - vTranslateForSCenter.y) / f3);
        return pointF;
    }

    private float minScale() {
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        if (this.minimumScaleType == 2 || this.minimumScaleType == 4) {
            return Math.max(((float) (getWidth() - paddingLeft)) / ((float) sWidth()), ((float) (getHeight() - paddingBottom)) / ((float) sHeight()));
        }
        if (this.minimumScaleType != 3 || this.minScale <= BitmapDescriptorFactory.HUE_RED) {
            return Math.min(((float) (getWidth() - paddingLeft)) / ((float) sWidth()), ((float) (getHeight() - paddingBottom)) / ((float) sHeight()));
        }
        return this.minScale;
    }

    /* access modifiers changed from: private */
    public float limitedScale(float f) {
        return Math.min(this.maxScale, Math.max(minScale(), f));
    }

    private float ease(int i, long j, float f, float f2, long j2) {
        switch (i) {
            case 1:
                return easeOutQuad(j, f, f2, j2);
            case 2:
                return easeInOutQuad(j, f, f2, j2);
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected easing type: ");
                sb.append(i);
                throw new IllegalStateException(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    @AnyThread
    public void debug(String str, Object... objArr) {
        if (this.debug) {
            Log.d(TAG, String.format(str, objArr));
        }
    }

    private int px(int i) {
        return (int) (this.density * ((float) i));
    }

    public final void setRegionDecoderClass(@NonNull Class<? extends ImageRegionDecoder> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Decoder class cannot be set to null");
        }
        this.regionDecoderFactory = new CompatDecoderFactory(cls);
    }

    public final void setRegionDecoderFactory(@NonNull DecoderFactory<? extends ImageRegionDecoder> decoderFactory) {
        if (decoderFactory == null) {
            throw new IllegalArgumentException("Decoder factory cannot be set to null");
        }
        this.regionDecoderFactory = decoderFactory;
    }

    public final void setBitmapDecoderClass(@NonNull Class<? extends ImageDecoder> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Decoder class cannot be set to null");
        }
        this.bitmapDecoderFactory = new CompatDecoderFactory(cls);
    }

    public final void setBitmapDecoderFactory(@NonNull DecoderFactory<? extends ImageDecoder> decoderFactory) {
        if (decoderFactory == null) {
            throw new IllegalArgumentException("Decoder factory cannot be set to null");
        }
        this.bitmapDecoderFactory = decoderFactory;
    }

    public final void getPanRemaining(RectF rectF) {
        if (isReady()) {
            float sWidth2 = this.scale * ((float) sWidth());
            float sHeight2 = this.scale * ((float) sHeight());
            if (this.panLimit == 3) {
                rectF.top = Math.max(BitmapDescriptorFactory.HUE_RED, -(this.vTranslate.y - ((float) (getHeight() / 2))));
                rectF.left = Math.max(BitmapDescriptorFactory.HUE_RED, -(this.vTranslate.x - ((float) (getWidth() / 2))));
                rectF.bottom = Math.max(BitmapDescriptorFactory.HUE_RED, this.vTranslate.y - (((float) (getHeight() / 2)) - sHeight2));
                rectF.right = Math.max(BitmapDescriptorFactory.HUE_RED, this.vTranslate.x - (((float) (getWidth() / 2)) - sWidth2));
            } else if (this.panLimit == 2) {
                rectF.top = Math.max(BitmapDescriptorFactory.HUE_RED, -(this.vTranslate.y - ((float) getHeight())));
                rectF.left = Math.max(BitmapDescriptorFactory.HUE_RED, -(this.vTranslate.x - ((float) getWidth())));
                rectF.bottom = Math.max(BitmapDescriptorFactory.HUE_RED, this.vTranslate.y + sHeight2);
                rectF.right = Math.max(BitmapDescriptorFactory.HUE_RED, this.vTranslate.x + sWidth2);
            } else {
                rectF.top = Math.max(BitmapDescriptorFactory.HUE_RED, -this.vTranslate.y);
                rectF.left = Math.max(BitmapDescriptorFactory.HUE_RED, -this.vTranslate.x);
                rectF.bottom = Math.max(BitmapDescriptorFactory.HUE_RED, (sHeight2 + this.vTranslate.y) - ((float) getHeight()));
                rectF.right = Math.max(BitmapDescriptorFactory.HUE_RED, (sWidth2 + this.vTranslate.x) - ((float) getWidth()));
            }
        }
    }

    public final void setPanLimit(int i) {
        if (!VALID_PAN_LIMITS.contains(Integer.valueOf(i))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid pan limit: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        this.panLimit = i;
        if (isReady()) {
            fitToBounds(true);
            invalidate();
        }
    }

    public final void setMinimumScaleType(int i) {
        if (!VALID_SCALE_TYPES.contains(Integer.valueOf(i))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid scale type: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        this.minimumScaleType = i;
        if (isReady()) {
            fitToBounds(true);
            invalidate();
        }
    }

    public final void setMaxScale(float f) {
        this.maxScale = f;
    }

    public final void setMinScale(float f) {
        this.minScale = f;
    }

    public final void setMinimumDpi(int i) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        setMaxScale(((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f) / ((float) i));
    }

    public final void setMaximumDpi(int i) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        setMinScale(((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f) / ((float) i));
    }

    public float getMaxScale() {
        return this.maxScale;
    }

    public final float getMinScale() {
        return minScale();
    }

    public void setMinimumTileDpi(int i) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.minimumTileDpi = (int) Math.min((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f, (float) i);
        if (isReady()) {
            reset(false);
            invalidate();
        }
    }

    @Nullable
    public final PointF getCenter() {
        return viewToSourceCoord((float) (getWidth() / 2), (float) (getHeight() / 2));
    }

    public final float getScale() {
        return this.scale;
    }

    public final void setScaleAndCenter(float f, @Nullable PointF pointF) {
        this.anim = null;
        this.pendingScale = Float.valueOf(f);
        this.sPendingCenter = pointF;
        this.sRequestedCenter = pointF;
        invalidate();
    }

    public final void resetScaleAndCenter() {
        this.anim = null;
        this.pendingScale = Float.valueOf(limitedScale(BitmapDescriptorFactory.HUE_RED));
        if (isReady()) {
            this.sPendingCenter = new PointF((float) (sWidth() / 2), (float) (sHeight() / 2));
        } else {
            this.sPendingCenter = new PointF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        }
        invalidate();
    }

    public final boolean isReady() {
        return this.readySent;
    }

    public final boolean isImageLoaded() {
        return this.imageLoadedSent;
    }

    public final int getSWidth() {
        return this.sWidth;
    }

    public final int getSHeight() {
        return this.sHeight;
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final int getAppliedOrientation() {
        return getRequiredRotation();
    }

    @Nullable
    public final ImageViewState getState() {
        if (this.vTranslate == null || this.sWidth <= 0 || this.sHeight <= 0) {
            return null;
        }
        return new ImageViewState(getScale(), getCenter(), getOrientation());
    }

    public final boolean isZoomEnabled() {
        return this.zoomEnabled;
    }

    public final void setZoomEnabled(boolean z) {
        this.zoomEnabled = z;
    }

    public final boolean isQuickScaleEnabled() {
        return this.quickScaleEnabled;
    }

    public final void setQuickScaleEnabled(boolean z) {
        this.quickScaleEnabled = z;
    }

    public final boolean isPanEnabled() {
        return this.panEnabled;
    }

    public final void setPanEnabled(boolean z) {
        this.panEnabled = z;
        if (!z && this.vTranslate != null) {
            this.vTranslate.x = ((float) (getWidth() / 2)) - (this.scale * ((float) (sWidth() / 2)));
            this.vTranslate.y = ((float) (getHeight() / 2)) - (this.scale * ((float) (sHeight() / 2)));
            if (isReady()) {
                refreshRequiredTiles(true);
                invalidate();
            }
        }
    }

    public final void setTileBackgroundColor(int i) {
        if (Color.alpha(i) == 0) {
            this.tileBgPaint = null;
        } else {
            this.tileBgPaint = new Paint();
            this.tileBgPaint.setStyle(Style.FILL);
            this.tileBgPaint.setColor(i);
        }
        invalidate();
    }

    public final void setDoubleTapZoomScale(float f) {
        this.doubleTapZoomScale = f;
    }

    public final void setDoubleTapZoomDpi(int i) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        setDoubleTapZoomScale(((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f) / ((float) i));
    }

    public final void setDoubleTapZoomStyle(int i) {
        if (!VALID_ZOOM_STYLES.contains(Integer.valueOf(i))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid zoom style: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        this.doubleTapZoomStyle = i;
    }

    public final void setDoubleTapZoomDuration(int i) {
        this.doubleTapZoomDuration = Math.max(0, i);
    }

    public void setExecutor(@NonNull Executor executor2) {
        if (executor2 == null) {
            throw new NullPointerException("Executor must not be null");
        }
        this.executor = executor2;
    }

    public void setEagerLoadingEnabled(boolean z) {
        this.eagerLoadingEnabled = z;
    }

    public final void setDebug(boolean z) {
        this.debug = z;
    }

    public boolean hasImage() {
        return (this.uri == null && this.bitmap == null) ? false : true;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener2) {
        this.onLongClickListener = onLongClickListener2;
    }

    public void setOnImageEventListener(OnImageEventListener onImageEventListener2) {
        this.onImageEventListener = onImageEventListener2;
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener2) {
        this.onStateChangedListener = onStateChangedListener2;
    }

    private void sendStateChanged(float f, PointF pointF, int i) {
        if (!(this.onStateChangedListener == null || this.scale == f)) {
            this.onStateChangedListener.onScaleChanged(this.scale, i);
        }
        if (this.onStateChangedListener != null && !this.vTranslate.equals(pointF)) {
            this.onStateChangedListener.onCenterChanged(getCenter(), i);
        }
    }

    @Nullable
    public AnimationBuilder animateCenter(PointF pointF) {
        if (!isReady()) {
            return null;
        }
        return new AnimationBuilder(pointF);
    }

    @Nullable
    public AnimationBuilder animateScale(float f) {
        if (!isReady()) {
            return null;
        }
        return new AnimationBuilder(f);
    }

    @Nullable
    public AnimationBuilder animateScaleAndCenter(float f, PointF pointF) {
        if (!isReady()) {
            return null;
        }
        return new AnimationBuilder(f, pointF);
    }
}
