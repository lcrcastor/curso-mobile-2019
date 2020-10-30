package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.softtoken.ISoftTokenState;
import ar.com.santander.rio.mbanking.app.ui.activities.RecargaSubeActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.slider.impl.SliderImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.RechargeSubeAnalytics.EventHit;
import ar.com.santander.rio.mbanking.managers.analytics.RechargeSubeAnalytics.Screen;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaRecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ValorRecargaBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.crashlytics.android.Crashlytics;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class RecargaSubeFragment extends BaseFragment {
    public static final String LISTA_CUENTAS = "LISTA_CUENTAS";
    public static final String LISTA_RECARGAS = "LISTA_RECARGAS";
    public static final String LISTA_VALORES = "LISTA_VALORES";
    public static final String SESION_USUARIO = "SESION_USUARIO";
    @Inject
    SoftTokenManager a;
    @InjectView(2131365467)
    RelativeLayout actionBarLayout;
    /* access modifiers changed from: private */
    public String ad;
    /* access modifiers changed from: private */
    public CuentaRecargaBean ae;
    /* access modifiers changed from: private */
    public CuentaRecargaBean af;
    /* access modifiers changed from: private */
    public ValorRecargaBean ag;
    /* access modifiers changed from: private */
    public ValorRecargaBean ah;
    @Inject
    AnalyticsManager b;
    @InjectView(2131366082)
    TextView btnAgregarTarjeta;
    @InjectView(2131365772)
    Button btn_continuar;
    boolean c = false;
    boolean d = false;
    private View e;
    /* access modifiers changed from: private */
    public ViewPager f;
    /* access modifiers changed from: private */
    public List<CuentaRecargaBean> g;
    /* access modifiers changed from: private */
    public List<ValorRecargaBean> h;
    /* access modifiers changed from: private */
    public List<RecargaBean> i;
    @Inject
    public SessionManager sessionManager;
    @InjectView(2131365758)
    View viewSpinnerPago;

    public static RecargaSubeFragment newInstance(List list, List list2, List list3, String str, boolean z) {
        RecargaSubeFragment recargaSubeFragment = new RecargaSubeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_CUENTAS, (ArrayList) list);
        bundle.putParcelableArrayList(LISTA_RECARGAS, (ArrayList) list2);
        bundle.putParcelableArrayList(LISTA_VALORES, (ArrayList) list3);
        bundle.putString(SESION_USUARIO, str);
        bundle.putBoolean(FragmentConstants.INTENT_DATA_SUBE_BACK_TRANSITION, z);
        recargaSubeFragment.setArguments(bundle);
        return recargaSubeFragment;
    }

    public static RecargaSubeFragment newInstance(List list, List list2, List list3, String str) {
        RecargaSubeFragment recargaSubeFragment = new RecargaSubeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_CUENTAS, (ArrayList) list);
        bundle.putParcelableArrayList(LISTA_RECARGAS, (ArrayList) list2);
        bundle.putParcelableArrayList(LISTA_VALORES, (ArrayList) list3);
        bundle.putString(SESION_USUARIO, str);
        recargaSubeFragment.setArguments(bundle);
        return recargaSubeFragment;
    }

    public void onDestroy() {
        super.onDestroy();
        getActivity().overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.e = layoutInflater.inflate(R.layout.fragment_recarga_sube, viewGroup, false);
        ButterKnife.inject((Object) this, this.e);
        dismissProgress();
        initialize();
        configureActionBar();
        C();
        return this.e;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.b.trackScreen(Screen.config());
    }

    public void initialize() {
        this.btn_continuar.setText(getString(R.string.CONTINUAR));
        this.btn_continuar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
        this.btn_continuar.setEnabled(false);
        this.c = false;
        this.d = false;
        ValorRecargaBean valorRecargaBean = new ValorRecargaBean();
        valorRecargaBean.setMoneda("");
        valorRecargaBean.setImporte("");
        valorRecargaBean.setDescripcion("Seleccionar");
        this.i = (List) getArguments().getSerializable(LISTA_RECARGAS);
        this.h = (List) getArguments().getSerializable(LISTA_VALORES);
        this.h.add(0, valorRecargaBean);
        this.g = (List) getArguments().getSerializable(LISTA_CUENTAS);
        this.ad = getArguments().getString(SESION_USUARIO);
        B();
        A();
        z();
        if (getArguments().getBoolean(FragmentConstants.INTENT_DATA_SUBE_BACK_TRANSITION, false)) {
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (D()) {
            getActivity().overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
        } else {
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /* access modifiers changed from: private */
    public void y() {
        if (!this.c || !this.d) {
            this.btn_continuar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
            this.btn_continuar.setEnabled(false);
            return;
        }
        this.btn_continuar.setEnabled(true);
        this.btn_continuar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
    }

    private void z() {
        this.f = SliderImpl.getViewPager(this.e, SliderImpl.getServicesItems(this.i));
    }

    private void A() {
        View findViewById = this.e.findViewById(R.id.spinner_importe);
        final Spinner spinner = (Spinner) findViewById.findViewById(R.id.spinner);
        findViewById.setImportantForAccessibility(2);
        this.ag = null;
        final AnonymousClass1 r0 = new ArrayAdapter<ValorRecargaBean>(getContext(), 17367049, this.h) {
            public View getDropDownView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
                String str;
                View view2 = super.getView(i, view, viewGroup);
                ((TextView) view2.findViewById(16908308)).setTextColor(RecargaSubeFragment.this.getResources().getColor(R.color.generic_black));
                if (i > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(((ValorRecargaBean) RecargaSubeFragment.this.h.get(i)).getDescripcion().substring(1));
                    sb.append(" pesos");
                    str = sb.toString();
                } else {
                    str = "Seleccionar";
                }
                view2.setContentDescription(str);
                return view2;
            }

            @NonNull
            public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
                String str;
                View view2 = super.getView(i, view, viewGroup);
                ((TextView) view2.findViewById(16908308)).setTextColor(RecargaSubeFragment.this.getResources().getColor(R.color.generic_black));
                if (i > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(((ValorRecargaBean) RecargaSubeFragment.this.h.get(i)).getDescripcion().substring(1));
                    sb.append(" pesos");
                    str = sb.toString();
                } else {
                    str = "Seleccionar";
                }
                view2.setContentDescription(str);
                return view2;
            }
        };
        r0.setDropDownViewResource(17367050);
        spinner.setAdapter(r0);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                String str;
                RecargaSubeFragment.this.a(adapterView, i, true);
                a(i);
                RecargaSubeFragment.this.y();
                if (RecargaSubeFragment.this.ah == null || RecargaSubeFragment.this.ag != null) {
                    RecargaSubeFragment.this.ag = (ValorRecargaBean) adapterView.getSelectedItem();
                } else {
                    adapterView.setSelection(r0.getPosition(RecargaSubeFragment.this.ah));
                    RecargaSubeFragment.this.ag = RecargaSubeFragment.this.ah;
                }
                if (view != null) {
                    if (i > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(RecargaSubeFragment.this.ag.getDescripcion().replace("$", ""));
                        sb.append(TarjetasConstants.PESOS);
                        str = sb.toString();
                    } else {
                        str = "Seleccionar";
                    }
                    view.setContentDescription(str);
                    spinner.setContentDescription(view.getContentDescription());
                }
            }

            private void a(int i) {
                RecargaSubeFragment.this.d = i > 0;
            }
        });
    }

    private void B() {
        View findViewById = this.e.findViewById(R.id.spinner_pago);
        final Spinner spinner = (Spinner) findViewById.findViewById(R.id.spinner);
        ImageView imageView = (ImageView) findViewById.findViewById(R.id.img_down_arrow);
        this.ae = null;
        final AnonymousClass3 r2 = new ArrayAdapter<CuentaRecargaBean>(getContext(), 17367049, this.g) {
            public View getDropDownView(int i, View view, @NonNull ViewGroup viewGroup) {
                View view2 = super.getView(i, view, viewGroup);
                ((TextView) view2.findViewById(16908308)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                try {
                    view2.setContentDescription(new CAccessibility(view2.getContext()).applyFilterAccount(((CuentaRecargaBean) RecargaSubeFragment.this.g.get(i)).getDescripcionCuenta()).replace("$", TarjetasConstants.PESOS));
                } catch (Exception e) {
                    Crashlytics.logException(e);
                }
                return view2;
            }

            @NonNull
            public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
                View view2 = super.getView(i, view, viewGroup);
                ((TextView) view2.findViewById(16908308)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                try {
                    view2.setContentDescription(new CAccessibility(view2.getContext()).applyFilterAccount(((CuentaRecargaBean) RecargaSubeFragment.this.g.get(i)).getDescripcionCuenta()).replace("$", TarjetasConstants.PESOS));
                } catch (Exception e) {
                    Crashlytics.logException(e);
                }
                return view2;
            }
        };
        spinner.setAdapter(r2);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                RecargaSubeFragment.this.a(adapterView, i, false);
                a(i);
                RecargaSubeFragment.this.y();
                if (RecargaSubeFragment.this.af == null || RecargaSubeFragment.this.ae != null) {
                    RecargaSubeFragment.this.ae = (CuentaRecargaBean) adapterView.getSelectedItem();
                } else {
                    adapterView.setSelection(r2.getPosition(RecargaSubeFragment.this.af));
                    RecargaSubeFragment.this.ae = RecargaSubeFragment.this.af;
                }
                if (view != null) {
                    spinner.setContentDescription(view.getContentDescription());
                }
            }

            private void a(int i) {
                if (i >= 0) {
                    RecargaSubeFragment.this.c = true;
                }
            }
        });
        int i2 = 0;
        spinner.setEnabled(this.g.size() > 1);
        if (this.g.size() <= 1) {
            i2 = 8;
        }
        imageView.setVisibility(i2);
    }

    /* access modifiers changed from: private */
    public void a(AdapterView<?> adapterView, int i2, boolean z) {
        if (i2 != 0 || !z) {
            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.generic_black));
            return;
        }
        try {
            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.generic_grey));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void C() {
        this.btn_continuar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if ((RecargaSubeFragment.this.ag != null && RecargaSubeFragment.this.ae != null) || (!RecargaSubeFragment.this.ag.getImporte().equals("Seleccionar") && !RecargaSubeFragment.this.ae.getDescripcionCuenta().equals("Seleccionar"))) {
                    Bundle bundle = new Bundle();
                    String alias = ((RecargaBean) RecargaSubeFragment.this.i.get(RecargaSubeFragment.this.f.getCurrentItem())).getAlias();
                    String identificacion = ((RecargaBean) RecargaSubeFragment.this.i.get(RecargaSubeFragment.this.f.getCurrentItem())).getIdentificacion();
                    bundle.putParcelable("valorParcelable", RecargaSubeFragment.this.ag);
                    bundle.putParcelable("cuentaParcelable", RecargaSubeFragment.this.ae);
                    bundle.putString("nroTarjeta", identificacion);
                    bundle.putString("nombreTarjeta", alias);
                    bundle.putString(RecargaSubeFragment.this.getString(R.string.SESSION_USER), RecargaSubeFragment.this.ad);
                    RecargaSubeFragment.this.h.remove(0);
                    bundle.putParcelableArrayList(RecargaSubeFragment.LISTA_CUENTAS, (ArrayList) RecargaSubeFragment.this.g);
                    bundle.putParcelableArrayList(RecargaSubeFragment.LISTA_RECARGAS, (ArrayList) RecargaSubeFragment.this.i);
                    bundle.putParcelableArrayList(RecargaSubeFragment.LISTA_VALORES, (ArrayList) RecargaSubeFragment.this.h);
                    RecargaSubeFragment.this.ah = RecargaSubeFragment.this.ag;
                    RecargaSubeFragment.this.af = RecargaSubeFragment.this.ae;
                    ((RecargaSubeActivity) RecargaSubeFragment.this.getActivity()).changeFragmentToShow(new RecargaSubeRevisarFragment(), bundle);
                }
            }
        });
        this.btnAgregarTarjeta.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                new SoftTokenHandler(RecargaSubeFragment.this.getContext(), RecargaSubeFragment.this.a, RecargaSubeFragment.this.b).a(RecargaSubeFragment.this.getString(R.string.MSG_USER00561_RecargaSube), new ISoftTokenState() {
                    public void isNotAvaliable() {
                    }

                    public void isAvaliable() {
                        RecargaSubeFragment.this.b.trackEvent(EventHit.addSube());
                        ((RecargaSubeActivity) RecargaSubeFragment.this.getActivity()).gotoRecargaSubeWelcome(RecargaSubeFragment.this.getString(R.string.registrar_tarjeta_sube));
                    }
                });
            }
        });
    }

    private boolean D() {
        return this.sessionManager.getSession().isEmpty();
    }

    public void configureActionBar() {
        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        supportActionBar.setShowHideAnimationEnabled(false);
        supportActionBar.hide();
        this.actionBarLayout.findViewById(R.id.toggle).setVisibility(8);
        this.actionBarLayout.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((RecargaSubeActivity) RecargaSubeFragment.this.getActivity()).goBackToHome();
            }
        });
    }
}
