package ar.com.santander.rio.mbanking.components.webviews;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.utils.ImageSurfaceView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class ShowZoomImage extends Fragment implements OnTouchListener {
    private int a = 0;
    private Matrix b = new Matrix();
    private Matrix c = new Matrix();
    private PointF d = new PointF();
    private PointF e = new PointF();
    private float f = 1.0f;
    private float[] g = null;
    /* access modifiers changed from: private */
    public ImageView h;
    private Target i = new Target() {
        public void onBitmapFailed(Drawable drawable) {
        }

        public void onPrepareLoad(Drawable drawable) {
        }

        public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
            ShowZoomImage.this.h.setImageBitmap(bitmap);
        }
    };

    public static ShowZoomImage newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", str);
        ShowZoomImage showZoomImage = new ShowZoomImage();
        showZoomImage.setArguments(bundle);
        return showZoomImage;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.vista_watson_mensaje_zoom_imagen, viewGroup, false);
        this.h = (ImageView) inflate.findViewById(R.id.zoomImageView);
        this.h.setOnTouchListener(this);
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            Picasso.with(getActivity()).load(arguments.getString("URL")).resize(1000, 1000).centerInside().into(this.h, new Callback() {
                public void onError() {
                }

                public void onSuccess() {
                    Log.i(ImageSurfaceView.TAG, "### onSuccess");
                }
            });
        }
    }

    public void onDestroy() {
        Picasso.with(getActivity()).cancelRequest(this.i);
        super.onDestroy();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        try {
            ImageView imageView = (ImageView) view;
            ((ImageView) view).setScaleType(ScaleType.MATRIX);
            switch (motionEvent.getAction() & 255) {
                case 0:
                    this.c.set(this.b);
                    this.d.set(motionEvent.getX(), motionEvent.getY());
                    this.a = 1;
                    this.g = null;
                    break;
                case 1:
                case 6:
                    this.a = 0;
                    this.g = null;
                    break;
                case 2:
                    if (this.a != 1) {
                        if (this.a == 2) {
                            float a2 = (float) a(motionEvent);
                            if (a2 > 5.0f) {
                                float f2 = a2 / this.f;
                                float[] fArr = new float[9];
                                this.b.getValues(fArr);
                                if (fArr[0] <= 1.0f) {
                                    if (f2 > 2.5f) {
                                        this.b.set(this.c);
                                        this.b.postScale(f2, f2, this.e.x, this.e.y);
                                        imageView.setImageMatrix(this.b);
                                        break;
                                    }
                                } else {
                                    this.b.set(this.c);
                                    this.b.postScale(f2, f2, this.e.x, this.e.y);
                                    imageView.setImageMatrix(this.b);
                                    break;
                                }
                            }
                        }
                    } else {
                        this.b.set(this.c);
                        this.b.postTranslate(motionEvent.getX() - this.d.x, motionEvent.getY() - this.d.y);
                        break;
                    }
                    break;
                case 5:
                    this.f = (float) a(motionEvent);
                    if (this.f > 10.0f) {
                        this.c.set(this.b);
                        a(this.e, motionEvent);
                        this.a = 2;
                    }
                    this.g = new float[4];
                    this.g[0] = motionEvent.getX(0);
                    this.g[1] = motionEvent.getX(1);
                    this.g[2] = motionEvent.getY(0);
                    this.g[3] = motionEvent.getY(1);
                    break;
            }
            imageView.setImageMatrix(this.b);
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
        return true;
    }

    private static double a(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return Math.sqrt((double) ((x * x) + (y * y)));
    }

    private static void a(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }
}
