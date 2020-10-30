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
import ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaProductosRecalificacionBean;
import ar.com.santander.rio.mbanking.view.AmountView;

public class RecalificacionesConsultaLimitesFragment extends BaseFragment {
    private AmountView a;
    /* access modifiers changed from: private */
    public int ad;
    /* access modifiers changed from: private */
    public ListaProductosRecalificacionBean ae;
    /* access modifiers changed from: private */
    public String af;
    /* access modifiers changed from: private */
    public ListaLeyendas ag;
    private AccessibilityDelegate ah = new AccessibilityDelegate() {
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setText(view.getContentDescription());
        }
    };
    private TextView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private Button g;
    private int h;
    /* access modifiers changed from: private */
    public int i;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.h = getArguments().getInt("limiteTotalActual");
        this.i = getArguments().getInt("nuevaLineaCrediticia");
        this.ad = getArguments().getInt("limiteSinAsignar");
        this.af = getArguments().getString("codReca");
        this.ae = (ListaProductosRecalificacionBean) getArguments().getParcelable("listaProductos");
        this.ag = (ListaLeyendas) getArguments().getParcelable("listaLeyendas");
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_recalificaciones_consulta_limites, viewGroup, false);
        y();
        this.b = (TextView) inflate.findViewById(R.id.functionality_title);
        this.c = (TextView) inflate.findViewById(R.id.subTittleUpAmount);
        this.d = (TextView) inflate.findViewById(R.id.tvLineaTotalCrediticiaActual);
        this.a = (AmountView) inflate.findViewById(R.id.avLineaTotalCrediticiaNueva);
        this.e = (TextView) inflate.findViewById(R.id.tvLegends1);
        this.f = (TextView) inflate.findViewById(R.id.tvLegends2);
        this.g = (Button) inflate.findViewById(R.id.btnConfirmar);
        return inflate;
    }

    private void y() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        ((ImageView) ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecalificacionesConsultaLimitesFragment.this.onBackPressed();
            }
        });
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.b.setText(R.string.ID_4842_RECALIFICACION_TIT_CONSULTA_DE_NUEVOS_LIMITES);
        if (this.af != null) {
            if (this.af.equals("1")) {
                this.c.setText(R.string.ID_4843_RECALIFICACION_LBL_NUEVA_LINEA_TOTAL_CREDITICIA);
                this.e.setText(R.string.ID_4844_RECALIFICACION_LBL_DISTRIBUI_TU_NUEVA_LINEA_TOTAL_CREDITICIA);
                this.f.setText(R.string.ID_4845_RECALIFICACION_LBL_VOS_PODES_ACCEDER_A_ESTA_NUEVA_LINEA_TOTAL_CREDITICIA_PRIMERO_TE_INVITAMOS_A_DISTRIBUIRLA_ENTRE_TUS_PRODUCTOS_Y_ASIGNARLE_UN_NUEVO_LIMITE_A_CADA_UNO_LUEGO_TUS_NUEVOS_LIMITES_QUEDARAN_CONFIRMADOS);
                this.g.setText(R.string.ID_4846_RECALIFICACION_BTN_DISTRIBUIR_MI_NUEVA_LINEA);
                this.d.setText(getString(R.string.ID_4838_RECALIFICACION_LBL_LINEA_TOTAL_CREDITICIA, CFormatterAmounts.getAmountAndCurrencyFromDouble((double) this.h)));
            } else if (this.af.equals("2")) {
                this.c.setVisibility(8);
                this.e.setText(R.string.recalificacion_4861_);
                this.f.setText(R.string.ID_4862_RECALIFICACION_LBL_DISCULPA_NO_PODEMOS_AUMENTAR_TU_LINEA_TOTAL_CREDITICIA_POR_ESTE_CANAL_ACERCARTE_A_TU_SUCURSAL_PARA_QUE_UN_EJECUTIVO_TE_AYUDESI_QUERES_TE_INVITAMOS_A_DISTRIBUIR_TU_ACTUAL_LINEA_TOTAL_CREDITICIA_ENTRE_TODOS_TUS_PRODUCTOS);
                this.g.setText(R.string.recalificacion_4906_);
            }
        }
        this.a.setText(CFormatterAmounts.getAmountAndCurrencyFromDouble((double) this.i));
        z();
        this.g.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("nuevaLineaCrediticia", RecalificacionesConsultaLimitesFragment.this.i);
                bundle.putInt("limiteSinAsignar", RecalificacionesConsultaLimitesFragment.this.ad);
                bundle.putParcelable("listaProductos", RecalificacionesConsultaLimitesFragment.this.ae);
                bundle.putParcelable("listaLeyendas", RecalificacionesConsultaLimitesFragment.this.ag);
                bundle.putString("codReca", RecalificacionesConsultaLimitesFragment.this.af);
                RecalificacionesDistribucionLimitesFragment recalificacionesDistribucionLimitesFragment = new RecalificacionesDistribucionLimitesFragment();
                recalificacionesDistribucionLimitesFragment.setArguments(bundle);
                ((SantanderRioMainActivity) RecalificacionesConsultaLimitesFragment.this.getActivity()).changeFragmentAnimation(recalificacionesDistribucionLimitesFragment, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
            }
        });
    }

    private void z() {
        try {
            this.a.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.a.getText().toString()));
            this.d.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.d.getText().toString()));
            this.a.setAccessibilityDelegate(this.ah);
            this.d.setAccessibilityDelegate(this.ah);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onBackPressed() {
        ((SantanderRioMainActivity) getActivity()).restartActionBar();
        ((SantanderRioMainActivity) getActivity()).backLastFragment();
    }
}
