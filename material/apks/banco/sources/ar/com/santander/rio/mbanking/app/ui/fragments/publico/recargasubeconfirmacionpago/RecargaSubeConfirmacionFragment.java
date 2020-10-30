package ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.recargasubecomprobantepago.RecargaSubeComprobantePagoActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago.RecargaSubeConfirmacionPagoContract.View;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.components.share.intent.AllIntent;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.RechargeSubeAnalytics.EventHit;
import ar.com.santander.rio.mbanking.managers.analytics.RechargeSubeAnalytics.Screen;
import ar.com.santander.rio.mbanking.services.model.general.RecargaComprobantePago;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaRecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ValorRecargaBean;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.UtilsSube;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;

public class RecargaSubeConfirmacionFragment extends BaseMvpFragment implements View {
    public static final String RESPONSE_TAG = "response";
    public static final String TARJETA_NOMBRE_TAG = "tarjeta_nombre";
    public static final String TARJETA_NRO_TAG = "tarjeta_nro";
    private static int b = 1011;
    @Inject
    AnalyticsManager a;
    @InjectView(2131365468)
    android.view.View actionbarView;
    @InjectView(2131364213)
    Button btnVolverHome;
    private RecargaSubeConfirmacionPresenter c;
    @InjectView(2131364300)
    android.view.View closeView;
    private PagoServiciosBodyResponseBean d;
    private String e;
    private List<CuentaRecargaBean> f;
    private List<ValorRecargaBean> g;
    private List<RecargaBean> h;
    @InjectView(2131364746)
    ImageView iconOk;
    @InjectView(2131364960)
    LinearLayout layoutPdf;
    @InjectView(2131364973)
    LinearLayout layoutSubeApp;
    public OnFragmentInteractionListener mListener;
    @InjectView(2131365713)
    android.view.View separatorView;
    @InjectView(2131366227)
    TextView tvFechaPdf;
    @InjectView(2131366231)
    TextView tvImporteMonto;
    @InjectView(2131366237)
    TextView tvLegendCredit;
    @InjectView(2131366238)
    TextView tvMedioPago;
    @InjectView(2131366239)
    TextView tvMedioPagoPdf;
    @InjectView(2131366241)
    TextView tvMontoPdf;
    @InjectView(2131366244)
    TextView tvNroComprobantePdf;
    @InjectView(2131366246)
    TextView tvTarjeta;
    @InjectView(2131366249)
    TextView tvTarjetaNro;
    @InjectView(2131366250)
    TextView tvTarjetaPdf;
    @InjectView(2131366252)
    TextView tvTerminosCondicionesPdf;

    public interface OnFragmentInteractionListener {
        void backToPrincipalPage();
    }

    private void z() {
    }

    public void configureLayout() {
    }

    public static RecargaSubeConfirmacionFragment newInstance(RecargaComprobantePago recargaComprobantePago) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Comprobante_Pago", recargaComprobantePago);
        RecargaSubeConfirmacionFragment recargaSubeConfirmacionFragment = new RecargaSubeConfirmacionFragment();
        recargaSubeConfirmacionFragment.setArguments(bundle);
        return recargaSubeConfirmacionFragment;
    }

    public android.view.View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mRootView = layoutInflater.inflate(R.layout.fragment_recarga_sube_confirmar_pago, viewGroup, false);
        ButterKnife.inject((Object) this, this.mRootView);
        this.c = new RecargaSubeConfirmacionPresenter(this.mBus, this.mDataManager);
        this.c.attachView(this);
        initialize();
        C();
        configureLayout();
        z();
        return this.mRootView;
    }

    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.c.checkSubeApp(getContext().getPackageManager(), getString(R.string.name_package_app_sube));
        this.a.trackScreen(Screen.voucher());
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void initialize() {
        getExtras();
        this.c.initialize(this.d, this.e);
    }

    public void getExtras() {
        this.d = (PagoServiciosBodyResponseBean) getArguments().getParcelable("response");
        this.e = getArguments().getString("tarjeta_nombre");
        this.h = (List) getArguments().getSerializable(RecargaSubeFragment.LISTA_RECARGAS);
        this.g = (List) getArguments().getSerializable(RecargaSubeFragment.LISTA_VALORES);
        this.f = (List) getArguments().getSerializable(RecargaSubeFragment.LISTA_CUENTAS);
    }

    public void configureActionBar() {
        enableBackButton();
        this.separatorView.setVisibility(0);
    }

    public void enableBackButton() {
        android.view.View findViewById = this.actionbarView.findViewById(R.id.ok);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(android.view.View view) {
                    RecargaSubeConfirmacionFragment.this.A();
                }
            });
        }
    }

    public void attachView() {
        if (!this.c.isViewAttached()) {
            this.c.attachView(this);
        }
    }

    public void detachView() {
        if (this.c.isViewAttached()) {
            this.c.detachView();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({2131364965, 2131364922, 2131364967, 2131364209, 2131364213, 2131364973})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.btn_realizar_recarga /*2131364209*/:
                y();
                return;
            case R.id.btn_volver_home /*2131364213*/:
                A();
                return;
            case R.id.layout_download /*2131364922*/:
                try {
                    a(false);
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            case R.id.layout_receipt /*2131364965*/:
                B();
                return;
            case R.id.layout_share /*2131364967*/:
                try {
                    a(true);
                    return;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    return;
                }
            case R.id.layout_sube_app /*2131364973*/:
                try {
                    getContext().startActivity(getContext().getPackageManager().getLaunchIntentForPackage(getString(R.string.name_package_app_sube)));
                    return;
                } catch (Exception e4) {
                    e4.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }

    private void y() {
        this.mListener.backToPrincipalPage();
    }

    /* access modifiers changed from: private */
    public void A() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(67108864);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
    }

    private void B() {
        Intent intent = new Intent(getActivity(), RecargaSubeComprobantePagoActivity.class);
        intent.putExtra("response", this.d);
        intent.putExtra("tarjeta_nombre", this.e);
        intent.putExtra(RecargaSubeFragment.LISTA_CUENTAS, (ArrayList) this.f);
        intent.putExtra(RecargaSubeFragment.LISTA_RECARGAS, (ArrayList) this.h);
        intent.putExtra(RecargaSubeFragment.LISTA_VALORES, (ArrayList) this.g);
        startActivity(intent);
    }

    private void a(final boolean z) {
        this.a.trackEvent(EventHit.rechargeVoucher());
        StrictMode.setVmPolicy(new Builder().build());
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || VERSION.SDK_INT < 23) {
            this.layoutPdf.setVisibility(0);
            showProgressIndicator("");
            new Timer().schedule(new TimerTask() {
                public void run() {
                    RecargaSubeConfirmacionFragment.this.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            File file;
                            String format = String.format(Locale.getDefault(), "Recarga SUBE %s - %2s.jpg", new Object[]{RecargaSubeConfirmacionFragment.this.tvTarjetaPdf.getText(), RecargaSubeConfirmacionFragment.this.tvNroComprobantePdf.getText()});
                            try {
                                if (z) {
                                    file = UtilFile.saveTmpBitmap(format, UtilFile.getBitmapFromView(RecargaSubeConfirmacionFragment.this.layoutPdf), RecargaSubeConfirmacionFragment.this.getContext());
                                } else {
                                    file = UtilFile.saveBitmap(format, UtilFile.getBitmapFromView(RecargaSubeConfirmacionFragment.this.layoutPdf));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                file = null;
                            }
                            RecargaSubeConfirmacionFragment.this.layoutPdf.setVisibility(8);
                            RecargaSubeConfirmacionFragment.this.dismissProgressIndicator();
                            if (!z || file == null) {
                                RecargaSubeConfirmacionFragment.this.downloadCompleted();
                            } else {
                                RecargaSubeConfirmacionFragment.this.sharePDF(file);
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
        Toast.makeText(getActivity(), getActivity().getString(R.string.MSG_USER0000XX_DOWNLOAD_OK), 1).show();
    }

    public void sharePDF(File file) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Comprobante de ");
            sb.append(file.getName().replace(Constants.EXTENSION_FILE_STORAGE, ""));
            startActivity(Intent.createChooser(getOtherIntentImg(sb.toString(), file, getContext()), "Comprobante de pago"));
        } catch (Exception e2) {
            Log.d("Exception", e2.getMessage());
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
        if (setContentUriPermissionsByIntent(context, intent, uriForFile).booleanValue() && verifyAppReceiveIntent(getContext().getPackageManager(), intent).booleanValue()) {
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

    public void setView(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean, String str) {
        String str2;
        try {
            CAccessibility cAccessibility = new CAccessibility(getContext());
            this.a.trackTransaction("recarga sube", pagoServiciosBodyResponseBean.nroComp);
            this.tvImporteMonto.setText(UtilsSube.subeImporteformater(pagoServiciosBodyResponseBean.importe));
            this.tvTarjeta.setText(b(str));
            this.tvTarjetaNro.setText(UtilsSube.separarIdentificadorSube(pagoServiciosBodyResponseBean.identificacion));
            this.tvMedioPago.setText(pagoServiciosBodyResponseBean.descCtaDebito);
            this.tvMontoPdf.setText(this.tvImporteMonto.getText());
            this.tvTarjetaPdf.setText(UtilsSube.separarIdentificadorSube(pagoServiciosBodyResponseBean.identificacion));
            this.tvMedioPagoPdf.setText(pagoServiciosBodyResponseBean.descCtaDebito);
            String str3 = pagoServiciosBodyResponseBean.fecha;
            if (TextUtils.isEmpty(pagoServiciosBodyResponseBean.hora)) {
                str2 = "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(pagoServiciosBodyResponseBean.hora);
                str2 = sb.toString();
            }
            this.tvFechaPdf.setText(str3.concat(str2));
            this.tvNroComprobantePdf.setText(pagoServiciosBodyResponseBean.nroComp);
            this.tvTerminosCondicionesPdf.setText(Html.fromHtml(pagoServiciosBodyResponseBean.leyendaComp));
            TextView textView = this.tvImporteMonto;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.tvImporteMonto.getText().toString().replace("$", ""));
            sb2.append(TarjetasConstants.PESOS);
            textView.setContentDescription(sb2.toString());
            this.tvMedioPago.setContentDescription(cAccessibility.applyFilterAccount(pagoServiciosBodyResponseBean.descCtaDebito).replace("$", TarjetasConstants.PESOS));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void visibilityLogoSube(boolean z) {
        if (z) {
            this.layoutSubeApp.setVisibility(0);
            this.tvLegendCredit.setVisibility(8);
            return;
        }
        this.layoutSubeApp.setVisibility(8);
        this.tvLegendCredit.setVisibility(0);
    }

    private String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return getString(R.string.TARJETA_SUBE);
        }
        return getString(R.string.revisar_tarjeta_nombre, str);
    }

    private void C() {
        this.closeView.setVisibility(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int width = RecargaSubeConfirmacionFragment.this.actionbarView.getWidth() / 2;
                int y = ((int) RecargaSubeConfirmacionFragment.this.iconOk.getY()) + (RecargaSubeConfirmacionFragment.this.iconOk.getHeight() / 2) + RecargaSubeConfirmacionFragment.this.actionbarView.getHeight();
                float hypot = (float) Math.hypot((double) RecargaSubeConfirmacionFragment.this.mRootView.getWidth(), (double) RecargaSubeConfirmacionFragment.this.mRootView.getHeight());
                if (VERSION.SDK_INT >= 21) {
                    Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(RecargaSubeConfirmacionFragment.this.closeView, width, y, hypot, BitmapDescriptorFactory.HUE_RED);
                    createCircularReveal.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            RecargaSubeConfirmacionFragment.this.closeView.setVisibility(8);
                            RecargaSubeConfirmacionFragment.this.configureActionBar();
                        }
                    });
                    createCircularReveal.setDuration(750);
                    createCircularReveal.start();
                    return;
                }
                RecargaSubeConfirmacionFragment.this.closeView.setVisibility(8);
                RecargaSubeConfirmacionFragment.this.configureActionBar();
            }
        }, 50);
    }
}
