package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.MediaActionSound;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.utils.ImageCompressionUtils;
import ar.com.santander.rio.mbanking.utils.ImageSurfaceView;
import cz.msebera.android.httpclient.util.TextUtils;
import javax.inject.Inject;

public class FotoObjetoFragment extends BaseFragment {
    public static final String CAMARA_ERROR = "CAMARA_ERROR";
    public static final String CAMERA_PERMISSION_REQUEST_TAG = "CAMERA_PERMISSION_REQUEST";
    public static final String CAMERA_SERVICE_TAG = "CAMERA_SERVICE";
    public static final String ENCODING_ERROR = "ENCODING_ERROR";
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener a;
    @Inject
    public AnalyticsManager analyticsManager;
    /* access modifiers changed from: private */
    public Camera b;
    private ViewGroup c;
    private ViewGroup d;
    private FrameLayout e;
    /* access modifiers changed from: private */
    public byte[] f;
    private boolean g = false;
    private String h;
    /* access modifiers changed from: private */
    public PictureCallback i = new PictureCallback() {
        public void onPictureTaken(byte[] bArr, Camera camera) {
            if (bArr != null) {
                FotoObjetoFragment.this.f = bArr;
                FotoObjetoFragment.this.E();
                return;
            }
            FotoObjetoFragment.this.a.onError(FotoObjetoFragment.ENCODING_ERROR);
        }
    };

    public interface OnFragmentInteractionListener {
        void configureActionFotoActionBar();

        void onError(String str);

        void onStoreImage(String str);
    }

    class SavePhotoTask extends AsyncTask<byte[], Void, String> {
        private SavePhotoTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            FotoObjetoFragment.this.showProgress(FotoObjetoFragment.CAMERA_SERVICE_TAG);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(byte[]... bArr) {
            return ImageCompressionUtils.compressAndEncodingImageStringBase64(FotoObjetoFragment.this.getContext(), bArr[0]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            if (TextUtils.isBlank(str)) {
                FotoObjetoFragment.this.a.onError(FotoObjetoFragment.ENCODING_ERROR);
            } else {
                FotoObjetoFragment.this.a.onStoreImage(str);
            }
            FotoObjetoFragment.this.dismissProgress();
        }
    }

    public void onPause() {
        if (this.b != null) {
            this.b.stopPreview();
        }
        super.onPause();
    }

    public static FotoObjetoFragment newInstance(String str) {
        FotoObjetoFragment fotoObjetoFragment = new FotoObjetoFragment();
        fotoObjetoFragment.h = str;
        return fotoObjetoFragment;
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_foto_objeto, viewGroup, false);
        this.c = (ViewGroup) inflate.findViewById(R.id.buttons_layout);
        this.d = (ViewGroup) inflate.findViewById(R.id.fav_button_layout);
        Button button = (Button) inflate.findViewById(R.id.cancel_button);
        Button button2 = (Button) inflate.findViewById(R.id.confirm_button);
        ((FloatingActionButton) inflate.findViewById(R.id.shot_button)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                new MediaActionSound().play(0);
                FotoObjetoFragment.this.b.takePicture(null, null, FotoObjetoFragment.this.i);
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FotoObjetoFragment.this.D();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                new SavePhotoTask().execute(new byte[][]{FotoObjetoFragment.this.f});
            }
        });
        this.e = (FrameLayout) inflate.findViewById(R.id.camera_preview);
        if (ContextCompat.checkSelfPermission(getContext(), "android.permission.CAMERA") != 0) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
        } else {
            z();
        }
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.h != null && !this.h.isEmpty()) {
            this.analyticsManager.trackScreen(Screens.objectPhoto(this.h));
        }
    }

    public void onResume() {
        super.onResume();
        this.a.configureActionFotoActionBar();
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i2 == 1 && iArr[0] == 0) {
            z();
        } else {
            y();
        }
    }

    private void y() {
        onBackPressed();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.a = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        releaseCameraService();
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    private void z() {
        this.b = C();
        if (this.b == null) {
            this.a.onError("CAMARA_ERROR");
        }
        this.e.addView(new ImageSurfaceView(getContext(), this.b));
        D();
    }

    private void A() {
        if (this.b != null && !this.g) {
            this.b.startPreview();
            this.g = true;
        }
    }

    private void B() {
        if (this.b != null && this.g) {
            this.b.stopPreview();
            this.g = false;
        }
    }

    private Camera C() {
        Camera camera;
        try {
            camera = Camera.open();
            try {
                camera.setDisplayOrientation(90);
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            camera = null;
            this.a.onError("CAMARA_ERROR");
            return camera;
        }
        return camera;
    }

    public void releaseCameraService() {
        if (this.b != null) {
            this.a = null;
            this.b.release();
            this.b = null;
        }
    }

    /* access modifiers changed from: private */
    public void D() {
        A();
        this.d.setVisibility(0);
        this.c.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void E() {
        B();
        this.d.setVisibility(8);
        this.c.setVisibility(0);
    }
}
