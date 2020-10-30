package ar.com.santander.rio.mbanking.utils;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Iterator;

public class ImageSurfaceView extends SurfaceView implements Callback {
    public static final String ORIENTATION_PARAMETER = "orientation";
    public static final String PORTRAIT = "portrait";
    public static final String TAG = "TAG";
    private SurfaceHolder a;
    private Camera b;

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public ImageSurfaceView(Context context) {
        super(context);
    }

    public ImageSurfaceView(Context context, Camera camera) {
        super(context);
        this.b = camera;
        this.a = getHolder();
        this.a.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            this.b.setPreviewDisplay(surfaceHolder);
            this.b.startPreview();
        } catch (IOException e) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Error setting camera preview: ");
            sb.append(e.getMessage());
            Log.d(str, sb.toString());
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (this.a.getSurface() != null) {
            try {
                this.b.reconnect();
                this.b.stopPreview();
            } catch (IOException e) {
                e.fillInStackTrace();
                e.fillInStackTrace();
            }
            Parameters parameters = this.b.getParameters();
            Size a2 = a(i2, i3, parameters);
            Size a3 = a(parameters);
            if (!(a2 == null || a3 == null)) {
                parameters.setPictureSize(a3.width, a3.height);
                parameters.setPictureFormat(256);
                parameters.setJpegQuality(75);
                if (parameters.getSupportedFocusModes().contains("continuous-picture")) {
                    parameters.setFocusMode("continuous-picture");
                }
                parameters.set(ORIENTATION_PARAMETER, PORTRAIT);
                this.b.setParameters(parameters);
            }
            try {
                this.b.setPreviewDisplay(this.a);
                this.b.startPreview();
            } catch (Exception e2) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Error starting camera preview: ");
                sb.append(e2.getMessage());
                Log.d(str, sb.toString());
                e2.fillInStackTrace();
            }
        }
    }

    private static Size a(int i, int i2, Parameters parameters) {
        int round = (int) Math.round((i >= 800 ? 800.0d / (((double) i) * 1.0d) : 1.0d) * ((double) i2) * 1.0d);
        Size size = null;
        for (Size size2 : parameters.getSupportedPreviewSizes()) {
            if (size2.width <= 800 && size2.height <= round) {
                if (size == null || size2.width * size2.height > size.width * size.height) {
                    size = size2;
                }
            }
        }
        return size;
    }

    private static Size a(Parameters parameters) {
        Size size;
        Iterator it = parameters.getSupportedPictureSizes().iterator();
        while (true) {
            if (!it.hasNext()) {
                size = null;
                break;
            }
            size = (Size) it.next();
            if (size.width >= 600) {
                break;
            }
        }
        return (size != null || parameters.getSupportedPictureSizes().size() < 1) ? size : (Size) parameters.getSupportedPictureSizes().get(parameters.getSupportedPictureSizes().size() - 1);
    }
}
