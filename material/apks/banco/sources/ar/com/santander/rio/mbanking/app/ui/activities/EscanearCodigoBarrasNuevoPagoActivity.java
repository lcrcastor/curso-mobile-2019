package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.nuevopago.CameraPreview;
import ar.com.santander.rio.mbanking.app.module.nuevopago.EscanearCodigoBarrasNuevoPagoPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevopago.EscanearCodigoBarrasNuevoPagoView;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;

public class EscanearCodigoBarrasNuevoPagoActivity extends BaseMvpActivity implements OnClickListener, EscanearCodigoBarrasNuevoPagoView {
    /* access modifiers changed from: private */
    public Runnable A;
    private Runnable B;
    /* access modifiers changed from: private */
    public AutoFocusCallback C;
    ImageView p;
    @InjectView(2131364239)
    FrameLayout preview;
    @Inject
    AnalyticsManager q;
    private List<CuentaDebitoBean> r;
    /* access modifiers changed from: private */
    public EscanearCodigoBarrasNuevoPagoPresenter s;
    /* access modifiers changed from: private */
    public ImageScanner t;
    /* access modifiers changed from: private */
    public Camera u;
    private CameraPreview v;
    /* access modifiers changed from: private */
    public Handler w;
    private Handler x;
    /* access modifiers changed from: private */
    public boolean y = false;
    private PreviewCallback z;

    static {
        System.loadLibrary("iconv");
    }

    public static Camera getCameraInstance() {
        try {
            return Camera.open();
        } catch (Exception unused) {
            return null;
        }
    }

    public void initialize() {
        this.r = getIntent().getParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS);
        this.w = new Handler();
        this.x = new Handler();
        this.t = new ImageScanner();
        this.t.setConfig(0, 0, 0);
        this.t.setConfig(8, 0, 1);
        this.t.setConfig(9, 0, 1);
        this.t.setConfig(10, 0, 1);
        this.t.setConfig(12, 0, 1);
        this.t.setConfig(13, 0, 1);
        this.t.setConfig(14, 0, 1);
        this.t.setConfig(25, 0, 1);
        this.t.setConfig(34, 0, 1);
        this.t.setConfig(38, 0, 1);
        this.t.setConfig(35, 0, 1);
        this.t.setConfig(39, 0, 1);
        this.t.setConfig(57, 0, 1);
        this.t.setConfig(64, 0, 1);
        this.t.setConfig(93, 0, 1);
        this.t.setConfig(128, 0, 1);
        this.t.setConfig(0, 256, 3);
        this.t.setConfig(0, 257, 3);
        this.z = new PreviewCallback() {
            public void onPreviewFrame(byte[] bArr, Camera camera) {
                Size previewSize = camera.getParameters().getPreviewSize();
                Image image = new Image(previewSize.width, previewSize.height, "Y800");
                image.setData(bArr);
                if (EscanearCodigoBarrasNuevoPagoActivity.this.t.scanImage(image) != 0) {
                    EscanearCodigoBarrasNuevoPagoActivity.this.releaseCamera();
                    Iterator it = EscanearCodigoBarrasNuevoPagoActivity.this.t.getResults().iterator();
                    if (it.hasNext()) {
                        EscanearCodigoBarrasNuevoPagoActivity.this.s.onBarcodeRecognized(((Symbol) it.next()).getData());
                    }
                }
            }
        };
        this.A = new Runnable() {
            public void run() {
                if (EscanearCodigoBarrasNuevoPagoActivity.this.y) {
                    EscanearCodigoBarrasNuevoPagoActivity.this.u.autoFocus(EscanearCodigoBarrasNuevoPagoActivity.this.C);
                }
            }
        };
        this.C = new AutoFocusCallback() {
            public void onAutoFocus(boolean z, Camera camera) {
                EscanearCodigoBarrasNuevoPagoActivity.this.w.postDelayed(EscanearCodigoBarrasNuevoPagoActivity.this.A, 1000);
            }
        };
        this.B = new Runnable() {
            public void run() {
                EscanearCodigoBarrasNuevoPagoActivity.this.releaseCamera();
                if (EscanearCodigoBarrasNuevoPagoActivity.this.s.getRetryCounter() < EscanearCodigoBarrasNuevoPagoActivity.this.s.getMaxRetries()) {
                    EscanearCodigoBarrasNuevoPagoActivity.this.showBarcodeUnrecognized(EscanearCodigoBarrasNuevoPagoActivity.this.getString(R.string.IDxxxx_F06_XX_MSG_CBAR_IMPOSIBLE_LEER_REINTENTO));
                } else {
                    EscanearCodigoBarrasNuevoPagoActivity.this.showBarcodeUnrecognizedExceededRetries();
                }
            }
        };
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        this.p.setOnClickListener(this);
    }

    public void configureLayout() {
        setRequestedOrientation(1);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_nuevo_pago_f06_08);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        this.s = new EscanearCodigoBarrasNuevoPagoPresenter(this.mBus, this.mDataManager, this);
        this.s.attachView(this);
        this.q.trackScreen(getString(R.string.analytics_screen_name_payment_escanear_codigo_barra));
    }

    public void onResume() {
        super.onResume();
        prepareCamera();
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    public void attachView() {
        if (!this.s.isViewAttached()) {
            this.s.attachView(this);
        }
    }

    public void detachView() {
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showPrepararPagoElectronico(DatosDeudaBean datosDeudaBean, List<CuentaDebitoBean> list) {
        Intent intent = new Intent(this, PrepareInvoicePaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_CB);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void showPrepararPagoAfip(DatosDeudaBean datosDeudaBean, List<CuentaDebitoBean> list) {
        Intent intent = new Intent(this, PrepareAfipPaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_CB);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void showSeleccionarEmpresa(String str, List<CnsEmpresaDatosEmpresa> list) {
        Intent intent = new Intent(this, SeleccionarEmpresaNuevoPagoActivity.class);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) this.r);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CODIGO_BARRAS, str);
        startActivityForResult(intent, 7);
    }

    public void showSelectPayment(List<DatosDeudaBean> list) {
        Intent intent = new Intent(this, SeleccionarPagoNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA, ((DatosDeudaBean) list.get(0)).datosEmpresa);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_PAGO_ELECTRONICO, ((DatosDeudaBean) list.get(0)).identificacion);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) this.r);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_DEUDAS, (ArrayList) list);
        startActivityForResult(intent, 5);
    }

    public void showBarcodeUnrecognized(String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, null, null, getString(R.string.IDxxxx_F06_XX_BTN_REINTENTAR), getString(R.string.IDxxxx_F06_XX_BTN_SALIR), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                EscanearCodigoBarrasNuevoPagoActivity.this.s.incremetRetryCounter();
                EscanearCodigoBarrasNuevoPagoActivity.this.releaseCamera();
                EscanearCodigoBarrasNuevoPagoActivity.this.preview.removeAllViews();
                EscanearCodigoBarrasNuevoPagoActivity.this.prepareCamera();
            }

            public void onNegativeButton() {
                EscanearCodigoBarrasNuevoPagoActivity.this.onBackPressed();
            }
        });
        newInstance.show(getSupportFragmentManager(), "confirmarPagoAlert");
    }

    public void showBarcodeUnrecognizedExceededRetries() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.IDxxxx_F06_XX_MSG_CBAR_IMPOSIBLE_LEER_CARGA_MANUAL), null, null, getString(R.string.IDxxxx_F06_XX_BTN_ACEPTAR), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onPositiveButton() {
                EscanearCodigoBarrasNuevoPagoActivity.this.onBackPressed();
            }

            public void onNegativeButton() {
                EscanearCodigoBarrasNuevoPagoActivity.this.onBackPressed();
            }

            public void onSimpleActionButton() {
                EscanearCodigoBarrasNuevoPagoActivity.this.onBackPressed();
            }
        });
        newInstance.show(getSupportFragmentManager(), "confirmarPagoAlert");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 0 || i == 7) {
            if (i2 == -1) {
                Intent intent2 = new Intent();
                if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                    intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                    if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                        intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                    }
                } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                    intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
                }
                setResult(-1, intent2);
                finish();
            }
            this.s.resetRetryCounter();
        }
    }

    public void prepareCamera() {
        this.u = getCameraInstance();
        List supportedFocusModes = this.u.getParameters().getSupportedFocusModes();
        if (this.u != null) {
            this.v = new CameraPreview(this, this.u, this.z, this.C);
            this.preview.addView(this.v);
            this.u.setPreviewCallback(this.z);
            this.u.startPreview();
            this.y = true;
            if (supportedFocusModes != null && supportedFocusModes.contains("auto")) {
                this.u.cancelAutoFocus();
                try {
                    this.u.autoFocus(this.C);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
            this.x.postDelayed(this.B, LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS);
        }
    }

    public void releaseCamera() {
        if (this.u != null) {
            this.y = false;
            this.u.stopPreview();
            this.u.setPreviewCallback(null);
            this.v.getHolder().removeCallback(this.v);
            this.u.release();
            this.u = null;
            this.x.removeCallbacks(this.B);
            return;
        }
        this.x.removeCallbacks(this.B);
    }

    public void cleanScreenPreview() {
        this.preview.removeAllViews();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.back_imgButton) {
            onBackPressed();
        }
    }

    public void clearScreenData() {
        releaseCamera();
        cleanScreenPreview();
        prepareCamera();
    }
}
