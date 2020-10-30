package ar.com.santander.rio.mbanking.app.ui.activities.recargasubecomprobantepago;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PointerIconCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.recargasubecomprobantepago.RecargaSubeComprobantePagoContract.View;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeFragment;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.components.share.intent.AllIntent;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaRecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ValorRecargaBean;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.utils.UtilsSube;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class RecargaSubeComprobantePagoActivity extends BaseMvpActivity implements View {
    public static final String RESPONSE_TAG = "response";
    public static final String TARJETA_NOMBRE = "tarjeta_nombre";
    public static final String TARJETA_NRO = "tarjeta_nro";
    @InjectView(2131364209)
    TextView btnRealizarRecarga;
    @InjectView(2131364213)
    Button btnVolverHome;
    @InjectView(2131364922)
    LinearLayout cardDownload;
    @InjectView(2131364967)
    LinearLayout cardShare;
    @InjectView(2131365265)
    TextView comprobante;
    @InjectView(2131365210)
    ImageView imgLogo;
    @InjectView(2131364960)
    LinearLayout layoutPdf;
    @InjectView(2131364903)
    LinearLayout linearLayout;
    RecargaSubeComprobantePagoPresenter p;
    private PagoServiciosBodyResponseBean q;
    private String r;
    private List<CuentaRecargaBean> s;
    private List<ValorRecargaBean> t;
    @InjectView(2131366226)
    TextView tvFecha;
    @InjectView(2131366238)
    TextView tvMedioPago;
    @InjectView(2131366240)
    TextView tvMonto;
    @InjectView(2131366243)
    TextView tvNroComprobante;
    @InjectView(2131366246)
    TextView tvTarjeta;
    @InjectView(2131366247)
    TextView tvTarjetaAlias;
    @InjectView(2131366251)
    TextView tvTerminosCondiciones;
    private List<RecargaBean> u;

    public void configureLayout() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_recarga_comprobante_pago);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        hideKeyboard();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
        this.p.detachView();
        super.onDestroy();
    }

    public void initialize() {
        this.imgLogo.setVisibility(8);
        setListeners();
        this.p = new RecargaSubeComprobantePagoPresenter(this.mBus, this.mDataManager);
        this.p.attachView(this);
        getExtras();
        this.p.initialize(this.q, this.r);
    }

    public void setListeners() {
        this.cardShare.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View view) {
                try {
                    RecargaSubeComprobantePagoActivity.this.b(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.cardDownload.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View view) {
                try {
                    RecargaSubeComprobantePagoActivity.this.b(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.btnRealizarRecarga.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View view) {
                new Intent().putExtra("COMPROBANTE_PAGO_SUBE", PointerIconCompat.TYPE_NO_DROP);
                RecargaSubeComprobantePagoActivity.this.finish();
            }
        });
        this.btnVolverHome.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View view) {
                RecargaSubeComprobantePagoActivity.this.b();
            }
        });
    }

    /* access modifiers changed from: private */
    public void b() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(67108864);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    public void getExtras() {
        this.q = (PagoServiciosBodyResponseBean) getIntent().getExtras().getParcelable("response");
        this.r = getIntent().getExtras().getString("tarjeta_nombre");
        this.u = (List) getIntent().getExtras().getSerializable(RecargaSubeFragment.LISTA_RECARGAS);
        this.t = (List) getIntent().getExtras().getSerializable(RecargaSubeFragment.LISTA_VALORES);
        this.s = (List) getIntent().getExtras().getSerializable(RecargaSubeFragment.LISTA_CUENTAS);
    }

    public void configureActionBar() {
        getSupportActionBar().show();
        setActionBarType(ActionBarType.WHITE_SUBE);
        enableBackButton();
    }

    public void enableBackButton() {
        android.view.View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.ok);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(android.view.View view) {
                    RecargaSubeComprobantePagoActivity.this.finish();
                    RecargaSubeComprobantePagoActivity.this.overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
                }
            });
        }
    }

    public void attachView() {
        if (!this.p.isViewAttached()) {
            this.p.attachView(this);
        }
    }

    public void detachView() {
        if (this.p.isViewAttached()) {
            this.p.detachView();
        }
    }

    public void setView(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean, String str) {
        CAccessibility cAccessibility = new CAccessibility(this);
        try {
            this.tvTarjeta.setText(UtilsSube.separarIdentificadorSube(pagoServiciosBodyResponseBean.identificacion));
            this.tvTarjetaAlias.setVisibility(TextUtils.isEmpty(str) ? 8 : 0);
            this.tvTarjetaAlias.setText(str);
            this.tvMedioPago.setText(cAccessibility.applyFilterAccount(pagoServiciosBodyResponseBean.descCtaDebito));
            this.tvMonto.setText(UtilsSube.subeImporteformater(pagoServiciosBodyResponseBean.importe));
            TextView textView = this.tvMonto;
            StringBuilder sb = new StringBuilder();
            sb.append(this.tvMonto.getText().toString().replace("$", ""));
            sb.append(TarjetasConstants.PESOS);
            textView.setContentDescription(sb.toString());
            this.tvMedioPago.setText(pagoServiciosBodyResponseBean.descCtaDebito);
            this.tvMedioPago.setContentDescription(cAccessibility.applyFilterAccount(pagoServiciosBodyResponseBean.descCtaDebito).replace("$", TarjetasConstants.PESOS));
            this.tvFecha.setText(UtilsSube.fechaRecarga(pagoServiciosBodyResponseBean));
            this.tvNroComprobante.setText(pagoServiciosBodyResponseBean.nroComp);
            this.tvNroComprobante.setContentDescription(UtilsSube.separarIdentificadorSube(pagoServiciosBodyResponseBean.nroComp));
            this.comprobante.setContentDescription("Número de comprobante");
            this.tvTerminosCondiciones.setText(Html.fromHtml(pagoServiciosBodyResponseBean.leyendaComp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void b(final boolean z) {
        StrictMode.setVmPolicy(new Builder().build());
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || VERSION.SDK_INT < 23) {
            this.imgLogo.setVisibility(0);
            this.imgLogo.requestFocus();
            showProgressIndicator("");
            new Timer().schedule(new TimerTask() {
                public void run() {
                    RecargaSubeComprobantePagoActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            File file;
                            String format = String.format(Locale.getDefault(), "Recarga SUBE %s - %2s.jpg", new Object[]{RecargaSubeComprobantePagoActivity.this.tvTarjeta.getText(), RecargaSubeComprobantePagoActivity.this.tvNroComprobante.getText()});
                            try {
                                if (z) {
                                    file = UtilFile.saveTmpBitmap(format, UtilFile.getBitmapFromView(RecargaSubeComprobantePagoActivity.this.layoutPdf), RecargaSubeComprobantePagoActivity.this.getApplicationContext());
                                } else {
                                    file = UtilFile.saveBitmap(format, UtilFile.getBitmapFromView(RecargaSubeComprobantePagoActivity.this.layoutPdf));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                file = null;
                            }
                            RecargaSubeComprobantePagoActivity.this.imgLogo.setVisibility(8);
                            RecargaSubeComprobantePagoActivity.this.dismissProgressIndicator();
                            if (!z || file == null) {
                                RecargaSubeComprobantePagoActivity.this.downloadCompleted();
                            } else {
                                RecargaSubeComprobantePagoActivity.this.sharePDF(file);
                            }
                        }
                    });
                }
            }, 1000);
            return;
        }
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, OptionsToShareImpl.PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_SHARE);
    }

    public void downloadCompleted() {
        Toast.makeText(this, getString(R.string.MSG_USER0000XX_DOWNLOAD_OK), 1).show();
    }

    public void sharePDF(File file) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Comprobante de ");
            sb.append(file.getName().replace(Constants.EXTENSION_FILE_STORAGE, ""));
            startActivity(Intent.createChooser(getOtherIntentImg(sb.toString(), file, this), "Comprobante de pago"));
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
    }

    public Intent getOtherIntentImg(String str, File file, Context context) {
        Intent intent;
        Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName(), file);
        if (VERSION.SDK_INT >= 21) {
            intent = new Intent("android.intent.action.SEND_MULTIPLE");
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", new ArrayList(Arrays.asList(new Uri[]{uriForFile})));
        } else {
            intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
        }
        intent.setType(AllIntent.MIME_TYPE_IMAGE_JPEG);
        intent.putExtra("android.intent.extra.SUBJECT", str);
        intent.addFlags(1);
        intent.addFlags(2);
        if (setContentUriPermissionsByIntent(context, intent, uriForFile).booleanValue() && verifyAppReceiveIntent(getPackageManager(), intent).booleanValue()) {
            return intent;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Boolean verifyAppReceiveIntent(PackageManager packageManager, Intent intent) {
        boolean z = false;
        if (intent == null) {
            return Boolean.valueOf(false);
        }
        if (packageManager.queryIntentActivities(intent, 65536).size() > 0) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: protected */
    public Boolean setContentUriPermissionsByIntent(Context context, Intent intent, Uri uri) {
        boolean z = false;
        if (intent == null) {
            return Boolean.valueOf(false);
        }
        context.grantUriPermission(context.getPackageName(), uri, 3);
        if (context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
