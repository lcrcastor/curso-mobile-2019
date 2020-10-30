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
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CambiarLimiteBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimiteExtraccionBean;

public class LimiteExtraccionComprobanteFragment extends ITRSABaseFragment {
    private View a;
    private TextView ad;
    private ScrollView ae;
    private Button af;
    private LimiteExtraccionBean ag;
    private CambiarLimiteBodyResponseBean ah;
    private String ai;
    private String aj;
    private String ak;
    private String al;
    private String am;
    private TextView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setShareReceiptListener((SantanderRioMainActivity) getActivity());
        this.ag = (LimiteExtraccionBean) getArguments().getParcelable("selectedItemLimiteExtraccion");
        this.ah = (CambiarLimiteBodyResponseBean) getArguments().getParcelable("cambiarLimiteItem");
        this.ai = getArguments().getString("cajeroBanelcoSR");
        this.aj = getArguments().getString("cajeroBanelcoOB");
        this.ak = getArguments().getString("cajeroLink");
        this.al = getArguments().getString("limiteValidez");
        this.am = getArguments().getString("leyendasConfirmacion");
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.a = getActivity().getLayoutInflater().inflate(R.layout.limite_extraccion_comprobante_fragment, viewGroup, false);
        y();
        return this.a;
    }

    private void y() {
        z();
    }

    private void z() {
        this.b = (TextView) this.a.findViewById(R.id.tvTitle);
        this.c = (TextView) this.a.findViewById(R.id.tvLimiteActualValue);
        this.d = (TextView) this.a.findViewById(R.id.tvNuevoLimiteValue);
        this.e = (TextView) this.a.findViewById(R.id.tvValidezValue);
        this.f = (TextView) this.a.findViewById(R.id.tvRedBanelcoOtrosBancosValue);
        this.g = (TextView) this.a.findViewById(R.id.tvRedLinkValue);
        this.h = (TextView) this.a.findViewById(R.id.tvLegends);
        this.i = (TextView) this.a.findViewById(R.id.tvFechaSolicitudValue);
        this.ad = (TextView) this.a.findViewById(R.id.tvNroComprobanteValue);
        this.ae = (ScrollView) this.a.findViewById(R.id.svInfoContainer);
        this.af = (Button) this.a.findViewById(R.id.btnVolver);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        this.b.setText(getString(R.string.ID_4765_CUENTAS_TIT_COMPROBANTE_DE_CAMBIO_DE_LiMITE_DE_EXTRACCION));
        this.c.setText(this.ai);
        this.d.setText(this.ag.getImporte());
        this.e.setText(this.al);
        this.f.setText(this.aj);
        this.g.setText(this.ak);
        this.i.setText(this.ah.fechaOperacion);
        this.ad.setText(this.ah.nroComprobante);
        this.h.setText(Html.fromHtml(this.am));
        configureShareReceipt(this.ae, this.ah.nroComprobante, getString(R.string.ID_4765_CUENTAS_TIT_COMPROBANTE_DE_CAMBIO_DE_LiMITE_DE_EXTRACCION));
        this.af.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LimiteExtraccionComprobanteFragment.this.canExit();
            }
        });
        try {
            CAccessibility instance = CAccessibility.getInstance(getContext());
            this.c.setContentDescription(instance.applyFilterAmount(this.ai));
            this.d.setContentDescription(instance.applyFilterAmount(this.ag.getImporte()));
            this.g.setContentDescription(instance.applyFilterAmount(this.ak));
            this.f.setContentDescription(instance.applyFilterAmount(this.aj));
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
        AnonymousClass2 r2 = new AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setText(view.getContentDescription());
            }
        };
        this.c.setAccessibilityDelegate(r2);
        this.d.setAccessibilityDelegate(r2);
        this.g.setAccessibilityDelegate(r2);
        this.f.setAccessibilityDelegate(r2);
    }
}
