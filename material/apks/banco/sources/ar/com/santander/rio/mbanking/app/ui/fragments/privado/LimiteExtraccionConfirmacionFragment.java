package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
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
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.CambiarLimiteEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.CambiarLimiteResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimiteExtraccionBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCambiarLimite;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class LimiteExtraccionConfirmacionFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public LimiteExtraccionBean a;
    /* access modifiers changed from: private */
    public String ad;
    /* access modifiers changed from: private */
    public String ae;
    /* access modifiers changed from: private */
    public String af;
    /* access modifiers changed from: private */
    public String ag;
    private String ah;
    /* access modifiers changed from: private */
    public String ai;
    private View b;
    private TextView c;
    private TextView d;
    @Inject
    public IDataManager dataManager;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private Button i;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = (LimiteExtraccionBean) getArguments().getParcelable("selectedItemLimiteExtraccion");
        this.af = getArguments().getString("cajeroBanelcoSR");
        this.ae = getArguments().getString("cajeroBanelcoOB");
        this.ad = getArguments().getString("cajeroLink");
        this.ag = getArguments().getString("limiteValidez");
        this.ah = getArguments().getString("leyendas");
        this.ai = getArguments().getString("leyendasConfirmacion");
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.b = getActivity().getLayoutInflater().inflate(R.layout.limite_extraccion_confirmacion_fragment, viewGroup, false);
        y();
        return this.b;
    }

    private void y() {
        configureActionBar();
        configureLayout();
    }

    public void configureActionBar() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        ((ImageView) ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LimiteExtraccionConfirmacionFragment.this.onBackPressed();
            }
        });
    }

    public void configureLayout() {
        this.c = (TextView) this.b.findViewById(R.id.tvLimiteActualValue);
        this.d = (TextView) this.b.findViewById(R.id.tvNuevoLimiteValue);
        this.e = (TextView) this.b.findViewById(R.id.tvValidezValue);
        this.f = (TextView) this.b.findViewById(R.id.tvRedBanelcoOtrosBancosValue);
        this.g = (TextView) this.b.findViewById(R.id.tvRedLinkValue);
        this.h = (TextView) this.b.findViewById(R.id.tvLegends);
        this.i = (Button) this.b.findViewById(R.id.btnConfirmar);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        this.c.setText(this.af);
        this.d.setText(this.a.getImporte());
        this.e.setText(this.ag);
        this.f.setText(this.ae);
        this.g.setText(this.ad);
        this.i.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LimiteExtraccionConfirmacionFragment.this.z();
            }
        });
        this.h.setText(Html.fromHtml(this.ah));
        AnonymousClass3 r3 = new AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setText(view.getContentDescription());
            }
        };
        try {
            CAccessibility instance = CAccessibility.getInstance(getContext());
            this.c.setContentDescription(instance.applyFilterAmount(this.af));
            this.d.setContentDescription(instance.applyFilterAmount(this.a.getImporte()));
            this.f.setContentDescription(instance.applyFilterAmount(this.ae));
            this.g.setContentDescription(instance.applyFilterAmount(this.ad));
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
        this.f.setAccessibilityDelegate(r3);
        this.g.setAccessibilityDelegate(r3);
        this.c.setAccessibilityDelegate(r3);
        this.d.setAccessibilityDelegate(r3);
    }

    /* access modifiers changed from: private */
    public void z() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("Confirmar", getString(R.string.MSG_USER00532_Cuentas), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), getString(R.string.IDX_ALERT_BTN_CANCEL), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                LimiteExtraccionConfirmacionFragment.this.dataManager.cambiarLimite(LimiteExtraccionConfirmacionFragment.this.a, "P", LimiteExtraccionConfirmacionFragment.this.getActivity());
                LimiteExtraccionConfirmacionFragment.this.showProgress(VCambiarLimite.nameService);
            }

            public void onNegativeButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getFragmentManager(), "DialogNewVersion");
    }

    @Subscribe
    public void onCambiarLimite(CambiarLimiteEvent cambiarLimiteEvent) {
        dismissProgress();
        final CambiarLimiteEvent cambiarLimiteEvent2 = cambiarLimiteEvent;
        AnonymousClass5 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.LIMITE_EXTRACCION, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedItemLimiteExtraccion", LimiteExtraccionConfirmacionFragment.this.a);
                bundle.putParcelable("cambiarLimiteItem", ((CambiarLimiteResponseBean) cambiarLimiteEvent2.getBeanResponse()).cambiarLimiteBodyResponseBean);
                bundle.putString("cajeroBanelcoSR", LimiteExtraccionConfirmacionFragment.this.af);
                bundle.putString("cajeroBanelcoOB", LimiteExtraccionConfirmacionFragment.this.ae);
                bundle.putString("cajeroLink", LimiteExtraccionConfirmacionFragment.this.ad);
                bundle.putString("limiteValidez", LimiteExtraccionConfirmacionFragment.this.ag);
                bundle.putString("leyendasConfirmacion", LimiteExtraccionConfirmacionFragment.this.ai);
                LimiteExtraccionComprobanteFragment limiteExtraccionComprobanteFragment = new LimiteExtraccionComprobanteFragment();
                limiteExtraccionComprobanteFragment.setArguments(bundle);
                ((SantanderRioMainActivity) LimiteExtraccionConfirmacionFragment.this.getActivity()).changeFragmentAnimation(limiteExtraccionComprobanteFragment, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
            }
        };
        r0.handleWSResponse(cambiarLimiteEvent);
    }

    public void onBackPressed() {
        ((SantanderRioMainActivity) getActivity()).backLastFragment();
    }
}
