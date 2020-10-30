package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.module.softtoken.ISoftTokenState;
import ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import javax.inject.Inject;

public class RecargaSubeWelcomeFragment extends BaseFragment {
    private static String e = "SUBE_TITULO";
    private static String f = "SUBE_DESCRIPCION";
    private static String g = "SUBE_BUTTON";
    private static String h = "SUBE_FLOW";
    @Inject
    SoftTokenManager a;
    private String ad;
    private String ae;
    private ActionBar af;
    private Button ag;
    private View ah;
    private boolean ai = false;
    @Inject
    AnalyticsManager b;
    TextView c;
    TextView d;
    /* access modifiers changed from: private */
    public String i;

    public static RecargaSubeWelcomeFragment newInstance(String str, String str2, String str3, String str4) {
        Bundle bundle = new Bundle();
        RecargaSubeWelcomeFragment recargaSubeWelcomeFragment = new RecargaSubeWelcomeFragment();
        bundle.putString(e, str2);
        bundle.putString(f, str3);
        bundle.putString(g, str4);
        bundle.putString(h, str);
        recargaSubeWelcomeFragment.setArguments(bundle);
        return recargaSubeWelcomeFragment;
    }

    public void onPause() {
        super.onPause();
        if (this.ai) {
            getActivity().overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
        } else if (this.i.equals(FragmentConstants.AGREGAR_SUBE) || this.i.equals(FragmentConstants.SUBE_FLOW_AGREGAR)) {
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            getActivity().overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
        }
    }

    public void onCreate(Bundle bundle) {
        G();
        if (this.i.equals(FragmentConstants.AGREGAR_SUBE) || this.i.equals(FragmentConstants.SUBE_FLOW_AGREGAR)) {
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            getActivity().overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
        }
        this.ai = false;
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.ah = layoutInflater.inflate(R.layout.fragment_recarga_sube_welcome, viewGroup, false);
        this.af = ((SantanderRioMainActivity) getActivity()).getSupportActionBar();
        B();
        y();
        z();
        return this.ah;
    }

    private void y() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.PUSH_CLOSE);
        this.af.getCustomView().findViewById(R.id.toggle).setVisibility(8);
        this.af.getCustomView().findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecargaSubeWelcomeFragment.this.F();
            }
        });
        if (this.i.equalsIgnoreCase(FragmentConstants.AGREGAR_SUBE) || this.i.equalsIgnoreCase(FragmentConstants.SUBE_FLOW_AGREGAR)) {
            this.af.getCustomView().findViewById(R.id.toggle).setVisibility(0);
            this.af.getCustomView().findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RecargaSubeWelcomeFragment.this.onBackPressed();
                }
            });
        }
    }

    private void z() {
        this.ag.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (RecargaSubeWelcomeFragment.this.i.equals(FragmentConstants.AGREGAR_SUBE) || RecargaSubeWelcomeFragment.this.i.equals(FragmentConstants.AGREGAR_SUBE_SIN_TARJETA) || RecargaSubeWelcomeFragment.this.i.equals(FragmentConstants.SUBE_FLOW_AGREGAR)) {
                    new SoftTokenHandler(RecargaSubeWelcomeFragment.this.getContext(), RecargaSubeWelcomeFragment.this.a, RecargaSubeWelcomeFragment.this.b).a(RecargaSubeWelcomeFragment.this.getString(R.string.MSG_USER00561_RecargaSube), new ISoftTokenState() {
                        public void isNotAvaliable() {
                        }

                        public void isAvaliable() {
                            if (RecargaSubeWelcomeFragment.this.A()) {
                                RecargaSubeWelcomeFragment.this.I();
                            } else {
                                ((SantanderRioMainActivity) RecargaSubeWelcomeFragment.this.getContext()).goToAgregarTarjetaSube();
                            }
                        }
                    });
                } else {
                    RecargaSubeWelcomeFragment.this.I();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean A() {
        return ((SantanderRioMainActivity) getContext()).sessionManager.getSession().isEmpty();
    }

    private void B() {
        this.c = (TextView) this.ah.findViewById(R.id.tv_title);
        this.d = (TextView) this.ah.findViewById(R.id.tv_legend);
        this.ag = (Button) this.ah.findViewById(R.id.sube_next_button);
        D();
        C();
    }

    private void C() {
        try {
            if (getArguments().get(g) != null && !g.isEmpty()) {
                this.ag.setText(getArguments().getString(g));
            } else if (!this.ad.isEmpty()) {
                this.ag.setText(getString(R.string.registrar_tarjeta_sube));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void D() {
        try {
            if (getArguments().get(f) != null) {
                this.ae = getArguments().getString(f);
            }
            if (!this.ae.isEmpty()) {
                this.d.setText(Html.fromHtml(this.ae));
            }
            if (getArguments().get(e) != null) {
                this.ad = getArguments().getString(e);
            }
            if (!this.ad.isEmpty()) {
                this.c.setText(Html.fromHtml(this.ad));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onBackPressed() {
        if (H()) {
            if (getActivity() instanceof SantanderRioMainActivity) {
                ((SantanderRioMainActivity) getActivity()).chequearTarjetasUsuarioRegistradas(((SantanderRioMainActivity) getActivity()).sessionManager.getPersonalDate());
            } else {
                E();
            }
        } else if (this.i.equals(FragmentConstants.SUBE_FLOW_AGREGAR)) {
            ((SantanderRioMainActivity) getActivity()).chequearTarjetasUsuarioRegistradas(((SantanderRioMainActivity) getActivity()).sessionManager.getPersonalDate());
        } else {
            F();
        }
    }

    private void E() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() < 1) {
            fragmentManager.popBackStackImmediate();
        } else {
            getActivity().finish();
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    /* access modifiers changed from: private */
    public void F() {
        this.ai = true;
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(67108864);
        startActivity(intent);
        getActivity().finish();
    }

    private void G() {
        this.i = getArguments().get(h) != null ? getArguments().getString(h) : "";
    }

    private boolean H() {
        return this.i.equals(FragmentConstants.AGREGAR_SUBE);
    }

    /* access modifiers changed from: private */
    public void I() {
        LoginFragment loginFragment = new LoginFragment();
        ((SantanderRioMainActivity) getActivity()).setFragmentErrorListener(loginFragment, FragmentConstants.MOBILE_BANKING);
        ((SantanderRioMainActivity) getActivity()).changeFragmentAnimation(loginFragment);
    }
}
