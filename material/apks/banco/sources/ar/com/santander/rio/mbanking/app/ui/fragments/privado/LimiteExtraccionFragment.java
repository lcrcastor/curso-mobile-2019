package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.view.AmountView;

public class LimiteExtraccionFragment extends BaseFragment {
    private View a;
    private AmountView b;
    private TextView c;
    private TextView d;
    /* access modifiers changed from: private */
    public String e;
    /* access modifiers changed from: private */
    public String f;
    /* access modifiers changed from: private */
    public String g;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.e = getArguments().getString("cajeroBanelcoSR");
        this.f = getArguments().getString("cajeroBanelcoOB");
        this.g = getArguments().getString("cajeroLink");
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.a = getActivity().getLayoutInflater().inflate(R.layout.limite_extraccion_fragment, viewGroup, false);
        initialize();
        return this.a;
    }

    public void initialize() {
        configureActionBar();
        configureLayout();
    }

    public void configureActionBar() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        ((ImageView) ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LimiteExtraccionFragment.this.onBackPressed();
            }
        });
    }

    public void configureLayout() {
        this.b = (AmountView) this.a.findViewById(R.id.avLimiteExtraccion);
        this.c = (TextView) this.a.findViewById(R.id.tvRedBanelcoOtrosBancosAmount);
        this.d = (TextView) this.a.findViewById(R.id.tvRedLinkAmount);
        ((Button) this.a.findViewById(R.id.btnModificarLimite)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("cajeroBanelcoSR", LimiteExtraccionFragment.this.e);
                bundle.putString("cajeroBanelcoOB", LimiteExtraccionFragment.this.f);
                bundle.putString("cajeroLink", LimiteExtraccionFragment.this.g);
                LimiteExtraccionModificacionFragment limiteExtraccionModificacionFragment = new LimiteExtraccionModificacionFragment();
                limiteExtraccionModificacionFragment.setArguments(bundle);
                ((SantanderRioMainActivity) LimiteExtraccionFragment.this.getActivity()).changeFragmentAnimation(limiteExtraccionModificacionFragment, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
            }
        });
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        CAccessibility instance = CAccessibility.getInstance(getContext());
        this.b.setText(this.e);
        this.c.setText(this.f);
        this.d.setText(this.g);
        try {
            this.b.setContentDescription(instance.applyFilterAmount(this.e));
            this.c.setContentDescription(instance.applyFilterAmount(this.f));
            this.d.setContentDescription(instance.applyFilterAmount(this.g));
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
        AnonymousClass3 r2 = new AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setText(view.getContentDescription());
            }
        };
        this.b.setAccessibilityDelegate(r2);
        this.c.setAccessibilityDelegate(r2);
        this.d.setAccessibilityDelegate(r2);
    }

    public void onBackPressed() {
        ((SantanderRioMainActivity) getActivity()).restartActionBar();
        ((SantanderRioMainActivity) getActivity()).backLastFragment();
    }
}
