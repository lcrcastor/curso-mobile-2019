package ar.com.santander.rio.mbanking.app.module.nuevopago;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.io.IOException;

public class CameraPreview extends SurfaceView implements Callback {
    private SurfaceHolder a = getHolder();
    private Camera b;
    private PreviewCallback c;
    private AutoFocusCallback d;

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public CameraPreview(Context context, Camera camera, PreviewCallback previewCallback, AutoFocusCallback autoFocusCallback) {
        super(context);
        this.b = camera;
        this.c = previewCallback;
        this.d = autoFocusCallback;
        this.a.addCallback(this);
        this.a.setType(3);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            this.b.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error setting camera preview: ");
            sb.append(e.getMessage());
            Log.d("DBG", sb.toString());
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:3|4|5|6|9) */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0030, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0031, code lost:
        r3 = new java.lang.StringBuilder();
        r3.append("Error starting camera preview: ");
        r3.append(r1.getMessage());
        android.util.Log.d("DBG", r3.toString());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x000e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void surfaceChanged(android.view.SurfaceHolder r1, int r2, int r3, int r4) {
        /*
            r0 = this;
            android.view.SurfaceHolder r1 = r0.a
            android.view.Surface r1 = r1.getSurface()
            if (r1 != 0) goto L_0x0009
            return
        L_0x0009:
            android.hardware.Camera r1 = r0.b     // Catch:{ Exception -> 0x000e }
            r1.stopPreview()     // Catch:{ Exception -> 0x000e }
        L_0x000e:
            android.hardware.Camera r1 = r0.b     // Catch:{ Exception -> 0x0030 }
            r2 = 90
            r1.setDisplayOrientation(r2)     // Catch:{ Exception -> 0x0030 }
            android.hardware.Camera r1 = r0.b     // Catch:{ Exception -> 0x0030 }
            android.view.SurfaceHolder r2 = r0.a     // Catch:{ Exception -> 0x0030 }
            r1.setPreviewDisplay(r2)     // Catch:{ Exception -> 0x0030 }
            android.hardware.Camera r1 = r0.b     // Catch:{ Exception -> 0x0030 }
            android.hardware.Camera$PreviewCallback r2 = r0.c     // Catch:{ Exception -> 0x0030 }
            r1.setPreviewCallback(r2)     // Catch:{ Exception -> 0x0030 }
            android.hardware.Camera r1 = r0.b     // Catch:{ Exception -> 0x0030 }
            r1.startPreview()     // Catch:{ Exception -> 0x0030 }
            android.hardware.Camera r1 = r0.b     // Catch:{ Exception -> 0x0030 }
            android.hardware.Camera$AutoFocusCallback r2 = r0.d     // Catch:{ Exception -> 0x0030 }
            r1.autoFocus(r2)     // Catch:{ Exception -> 0x0030 }
            goto L_0x004b
        L_0x0030:
            r1 = move-exception
            java.lang.String r2 = "DBG"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Error starting camera preview: "
            r3.append(r4)
            java.lang.String r1 = r1.getMessage()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            android.util.Log.d(r2, r1)
        L_0x004b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.module.nuevopago.CameraPreview.surfaceChanged(android.view.SurfaceHolder, int, int, int):void");
    }
}
