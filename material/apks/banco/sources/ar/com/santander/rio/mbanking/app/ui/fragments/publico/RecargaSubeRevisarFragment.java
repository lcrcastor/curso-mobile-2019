package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.recargaSube.SharedPreferencesData;
import ar.com.santander.rio.mbanking.app.ui.activities.RecargaSubeActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.RechargeSubeAnalytics.EventHit;
import ar.com.santander.rio.mbanking.managers.analytics.RechargeSubeAnalytics.Screen;
import ar.com.santander.rio.mbanking.managers.data.DataManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.PagoServiciosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.DatosPersonales;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaRecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServicioCBDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ValorRecargaBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.UtilsSube;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class RecargaSubeRevisarFragment extends BaseFragment {
    @Inject
    AnalyticsManager a;
    public CuentaDebitoBean accountRequestBean;
    @InjectView(2131365471)
    RelativeLayout actionBarLayout;
    /* access modifiers changed from: private */
    public boolean ad;
    /* access modifiers changed from: private */
    public boolean ae = false;
    /* access modifiers changed from: private */
    public boolean af = false;
    /* access modifiers changed from: private */
    public ObjectAnimator ag;
    /* access modifiers changed from: private */
    public boolean ah = false;
    private SoftTokenManager ai;
    private AnimatorUpdateListener aj = new AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            try {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                RecargaSubeRevisarFragment.this.progressBar.setProgress(intValue);
                if (intValue == 99 && !RecargaSubeRevisarFragment.this.ah) {
                    RecargaSubeRevisarFragment.this.ah = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private AnimatorListener ak = new AnimatorListener() {
        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
            RecargaSubeRevisarFragment.this.btn.setText(RecargaSubeRevisarFragment.this.getResources().getString(R.string.revisar_procesando_pago));
            RecargaSubeRevisarFragment.this.btn.setEnabled(false);
        }

        public void onAnimationEnd(Animator animator) {
            Log.d("Animator", "End");
            if (RecargaSubeRevisarFragment.this.ae) {
                RecargaSubeRevisarFragment.this.C();
            }
        }

        public void onAnimationCancel(Animator animator) {
            Log.d("Animator", "Cancel");
        }
    };
    @Inject
    SettingsManager b;
    @InjectView(2131365773)
    TextView btn;
    private SharedPreferencesData c;
    @InjectView(2131364289)
    ConstraintLayout clMain;
    @InjectView(2131364290)
    ConstraintLayout clMain2;
    private String d;
    @Inject
    public IDataManager dataManager;
    private List<CuentaRecargaBean> e;
    private List<ValorRecargaBean> f;
    @InjectView(2131364703)
    FrameLayout flBtnConfirmar;
    private List<RecargaBean> g;
    /* access modifiers changed from: private */
    public PagoServiciosEvent h;
    /* access modifiers changed from: private */
    public WebServiceEvent i;
    @InjectView(2131364279)
    LinearLayout iconOk;
    @InjectView(2131364280)
    ImageView iconOkRounded;
    @InjectView(2131364873)
    ImageView ivItemButton;
    @Inject
    public Bus mBus;
    public PagoServicioCBDeudaBean pagoServicioCBDeudaBean;
    @InjectView(2131365400)
    ProgressBar progressBar;
    @Inject
    public SessionManager sessionManager;
    @InjectView(2131366215)
    TextView tvCuentaNro;
    @InjectView(2131366232)
    TextView tvImporteNro;
    @InjectView(2131366246)
    TextView tvTarjeta;
    @InjectView(2131366249)
    TextView tvTarjetaNro;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_up_recarga_sube);
        this.ai = new SoftTokenManager(getActivity().getBaseContext());
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_recarga_sube_revisa, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.c = new SharedPreferencesData(getActivity().getApplicationContext());
        B();
        ValorRecargaBean valorRecargaBean = (ValorRecargaBean) getArguments().getParcelable("valorParcelable");
        CuentaRecargaBean cuentaRecargaBean = (CuentaRecargaBean) getArguments().getParcelable("cuentaParcelable");
        this.g = (List) getArguments().getSerializable(RecargaSubeFragment.LISTA_RECARGAS);
        this.f = (List) getArguments().getSerializable(RecargaSubeFragment.LISTA_VALORES);
        this.e = (List) getArguments().getSerializable(RecargaSubeFragment.LISTA_CUENTAS);
        this.tvTarjeta.setText(A());
        this.tvTarjetaNro.setText(UtilsSube.separarIdentificadorSube(getArguments().getString("nroTarjeta")));
        TextView textView = this.tvImporteNro;
        StringBuilder sb = new StringBuilder();
        sb.append("$ ");
        sb.append(UtilsSube.importeSinDecimales(valorRecargaBean.getImporte()));
        textView.setText(sb.toString());
        TextView textView2 = this.tvImporteNro;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(valorRecargaBean.getDescripcion().replace("$", ""));
        sb2.append(TarjetasConstants.PESOS);
        textView2.setContentDescription(sb2.toString());
        this.tvCuentaNro.setText(cuentaRecargaBean.getDescripcionCuenta());
        try {
            this.tvCuentaNro.setContentDescription(new CAccessibility(inflate.getContext()).applyFilterAccount(cuentaRecargaBean.getDescripcionCuenta()).replace("$", TarjetasConstants.PESOS));
        } catch (Exception e2) {
            Crashlytics.logException(e2);
        }
        this.accountRequestBean = new CuentaDebitoBean();
        this.accountRequestBean.setNumero(cuentaRecargaBean.getNumero());
        this.accountRequestBean.setSucursal(cuentaRecargaBean.getSucursal());
        this.accountRequestBean.setTipo(cuentaRecargaBean.getTipo());
        this.accountRequestBean.setDescCtaDebito(cuentaRecargaBean.getDescripcionCuenta());
        this.pagoServicioCBDeudaBean = new PagoServicioCBDeudaBean();
        this.pagoServicioCBDeudaBean.identificacion = this.tvTarjetaNro.getText().toString().replaceAll(UtilsCuentas.SEPARAOR2, "");
        this.pagoServicioCBDeudaBean.importe = valorRecargaBean.getImporte();
        this.pagoServicioCBDeudaBean.moneda = valorRecargaBean.getMoneda();
        this.pagoServicioCBDeudaBean.empServ = "SUBE";
        this.pagoServicioCBDeudaBean.vencimiento = "**/**/**";
        this.ag = ObjectAnimator.ofInt(this.progressBar, NotificationCompat.CATEGORY_PROGRESS, new int[]{this.progressBar.getProgress(), 100}).setDuration(1000);
        this.ag.addUpdateListener(this.aj);
        this.ag.addListener(this.ak);
        y();
        ButterKnife.inject((Object) this, inflate);
        return inflate;
    }

    private void y() {
        this.btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecargaSubeRevisarFragment.this.a.trackScreen(Screen.confim());
                RecargaSubeRevisarFragment.this.a.trackEvent(EventHit.rechargeAmount(RecargaSubeRevisarFragment.this.pagoServicioCBDeudaBean.importe));
                RecargaSubeRevisarFragment.this.ag.start();
                RecargaSubeRevisarFragment.this.pagoServicio(RecargaSubeRevisarFragment.this.accountRequestBean, RecargaSubeRevisarFragment.this.pagoServicioCBDeudaBean, RecargaSubeRevisarFragment.this.z());
            }
        });
    }

    /* access modifiers changed from: private */
    public String z() {
        this.d = (String) getArguments().get("nombreTarjeta");
        return TextUtils.isEmpty(this.d) ? "" : this.d;
    }

    private String A() {
        String z = z();
        if (TextUtils.isEmpty(z)) {
            return getString(R.string.TARJETA_SUBE);
        }
        return getString(R.string.revisar_tarjeta_nombre, z);
    }

    private void B() {
        ((ImageView) this.actionBarLayout.findViewById(R.id.image_steps_line)).setImageResource(R.drawable.line_red_sube);
        this.actionBarLayout.findViewById(R.id.toggle).setOnClickListener(new OneClickListener(new OneClicked() {
            public void onClicked(View view) {
                ((RecargaSubeActivity) RecargaSubeRevisarFragment.this.getActivity()).backToPrincipalPage();
            }
        }));
        this.actionBarLayout.findViewById(R.id.ok).setOnClickListener(new OneClickListener(new OneClicked() {
            public void onClicked(View view) {
                ((RecargaSubeActivity) RecargaSubeRevisarFragment.this.getActivity()).goBackToHome();
            }
        }));
        this.actionBarLayout.setVisibility(0);
    }

    public void onBackPressed() {
        if (!this.af) {
            ((RecargaSubeActivity) getActivity()).backToPrincipalPage();
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    public void pagoServicio(CuentaDebitoBean cuentaDebitoBean, PagoServicioCBDeudaBean pagoServicioCBDeudaBean2, String str) {
        DatosPersonales personalDate = this.sessionManager.getPersonalDate();
        this.sessionManager.setNup(personalDate.getNup());
        this.sessionManager.setSession(getArguments().getString(getString(R.string.SESSION_USER)));
        this.dataManager = new DataManager(BaseApplication.get(getContext()), this.bus, this.sessionManager, this.b);
        pagoServicioCBDeudaBean2.infoAdicional = str;
        PagoServiciosBodyRequestBean pagoServiciosBodyRequestBean = new PagoServiciosBodyRequestBean(cuentaDebitoBean, pagoServicioCBDeudaBean2, "N", "0");
        pagoServiciosBodyRequestBean.setRecargaSubeData(personalDate.getNroDocumento(), personalDate.getFechaNacimiento());
        a(false);
        this.af = true;
        this.dataManager.getPagoServicios(pagoServiciosBodyRequestBean, getActivity(), true);
    }

    @Subscribe
    public void onPagoServicio(PagoServiciosEvent pagoServiciosEvent) {
        final PagoServiciosEvent pagoServiciosEvent2 = pagoServiciosEvent;
        AnonymousClass6 r0 = new BaseWSResponseHandler(getContext(), TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.PAGO_SERVICIOS, (IBaseView) getActivity(), (BaseActivity) getContext()) {
            public void onOk() {
                RecargaSubeRevisarFragment.this.af = false;
                RecargaSubeRevisarFragment.this.ae = true;
                RecargaSubeRevisarFragment.this.ad = true;
                RecargaSubeRevisarFragment.this.h = pagoServiciosEvent2;
                if (!RecargaSubeRevisarFragment.this.ag.isRunning()) {
                    RecargaSubeRevisarFragment.this.C();
                }
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                RecargaSubeRevisarFragment.this.a(true);
                RecargaSubeRevisarFragment.this.af = false;
            }

            /* access modifiers changed from: protected */
            public void onRes1Error(WebServiceEvent webServiceEvent) {
                RecargaSubeRevisarFragment.this.ae = true;
                RecargaSubeRevisarFragment.this.i = webServiceEvent;
                RecargaSubeRevisarFragment.this.ad = false;
                if (!RecargaSubeRevisarFragment.this.ag.isRunning()) {
                    RecargaSubeRevisarFragment.this.C();
                }
            }

            /* access modifiers changed from: protected */
            public void onRes4Error(WebServiceEvent webServiceEvent) {
                RecargaSubeRevisarFragment.this.ae = true;
                RecargaSubeRevisarFragment.this.i = webServiceEvent;
                RecargaSubeRevisarFragment.this.ad = false;
                if (!RecargaSubeRevisarFragment.this.ag.isRunning()) {
                    RecargaSubeRevisarFragment.this.C();
                }
            }
        };
        r0.handleWSResponse(pagoServiciosEvent);
    }

    /* access modifiers changed from: private */
    public void a(WebServiceEvent webServiceEvent) {
        Bundle bundle = new Bundle();
        bundle.putString("title", webServiceEvent.getErrorBodyBean().resTitle);
        bundle.putString("message", webServiceEvent.getErrorBodyBean().resDesc);
        ((RecargaSubeActivity) getActivity()).changeFragmentToShow(new RecargaSubeNotOKFragment(), bundle);
    }

    /* access modifiers changed from: private */
    public void a(PagoServiciosEvent pagoServiciosEvent) {
        try {
            PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean = pagoServiciosEvent.getResponse().pagoServiciosBodyResponseBean;
            Bundle bundle = new Bundle();
            bundle.putParcelable("response", pagoServiciosBodyResponseBean);
            bundle.putString("tarjeta_nombre", this.d);
            bundle.putParcelableArrayList(RecargaSubeFragment.LISTA_CUENTAS, (ArrayList) this.e);
            bundle.putParcelableArrayList(RecargaSubeFragment.LISTA_RECARGAS, (ArrayList) this.g);
            bundle.putParcelableArrayList(RecargaSubeFragment.LISTA_VALORES, (ArrayList) this.f);
            ((RecargaSubeActivity) getActivity()).changeFragmentToShow(new RecargaSubeOKFragment(), bundle);
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onPagoServicioResultOk: ", e2);
            Crashlytics.logException(e2);
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /* access modifiers changed from: private */
    @TargetApi(21)
    public void b(View view) {
        this.clMain.setVisibility(4);
        int x = ((int) this.iconOkRounded.getX()) + (this.iconOkRounded.getWidth() / 2);
        int y = (int) this.iconOkRounded.getY();
        float hypot = (float) Math.hypot((double) view.getWidth(), (double) view.getHeight());
        if (VERSION.SDK_INT >= 21) {
            Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view, x, y, BitmapDescriptorFactory.HUE_RED, hypot);
            view.setVisibility(0);
            createCircularReveal.start();
            return;
        }
        view.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void C() {
        try {
            this.iconOk.setVisibility(0);
            this.flBtnConfirmar.setVisibility(4);
            if (this.ad) {
                this.a.trackScreen(Screen.fdkok());
                this.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_style_ok));
            } else {
                this.a.trackScreen(Screen.fdkNoOk());
                this.iconOk.setBackground(getResources().getDrawable(R.drawable.button_red_empty));
                this.ivItemButton.setImageResource(R.drawable.ic_close_red);
                this.iconOkRounded.setImageDrawable(getResources().getDrawable(R.drawable.ic_error));
                this.iconOkRounded.setBackgroundResource(R.drawable.transition_button_icon_error);
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    RecargaSubeRevisarFragment.this.progressBar.setVisibility(4);
                    RecargaSubeRevisarFragment.this.iconOk.setVisibility(4);
                    RecargaSubeRevisarFragment.this.iconOkRounded.setVisibility(0);
                }
            }, 400);
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (RecargaSubeRevisarFragment.this.ad) {
                        RecargaSubeRevisarFragment.this.b((View) RecargaSubeRevisarFragment.this.clMain2);
                        RecargaSubeRevisarFragment.this.a(true);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                RecargaSubeRevisarFragment.this.a(RecargaSubeRevisarFragment.this.h);
                            }
                        }, 800);
                        return;
                    }
                    RecargaSubeRevisarFragment.this.b((View) RecargaSubeRevisarFragment.this.clMain2);
                    ((RecargaSubeActivity) RecargaSubeRevisarFragment.this.getActivity()).setActionBarType(ActionBarType.WHITE);
                    RecargaSubeRevisarFragment.this.clMain2.setBackgroundResource(R.color.white);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            RecargaSubeRevisarFragment.this.a(RecargaSubeRevisarFragment.this.i);
                        }
                    }, 800);
                }
            }, 800);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        if (!z) {
            this.btn.setOnClickListener(null);
            this.actionBarLayout.findViewById(R.id.toggle).setOnClickListener(null);
            this.actionBarLayout.findViewById(R.id.ok).setOnClickListener(null);
            return;
        }
        y();
        B();
    }
}
