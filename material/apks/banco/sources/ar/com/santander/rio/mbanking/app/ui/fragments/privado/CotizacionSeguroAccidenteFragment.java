package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroAccidenteActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.services.events.ContratarSeguroAccidenteEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaItem;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetContratarSeguroAccidente;
import com.squareup.otto.Subscribe;

public class CotizacionSeguroAccidenteFragment extends BaseFragment {
    Fragment a = new Fragment();
    /* access modifiers changed from: private */
    public ContratarSeguroAccidenteActivity b;
    /* access modifiers changed from: private */
    public GetCotizacionSeguroAccidenteBodyResponseBean c;
    /* access modifiers changed from: private */
    public String d = "";
    /* access modifiers changed from: private */
    public String e = "";
    /* access modifiers changed from: private */
    public String f = "";
    private CAccessibility g = new CAccessibility(getActivity());
    /* access modifiers changed from: private */
    public String h = "";

    public CotizacionSeguroAccidenteFragment() {
    }

    @SuppressLint({"ValidFragment"})
    public CotizacionSeguroAccidenteFragment(GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean) {
        this.c = getCotizacionSeguroAccidenteBodyResponseBean;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = (ContratarSeguroAccidenteActivity) getActivity();
        ((ContratarSeguroAccidenteActivity) getActivity()).getBus().register(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_cotizacion_seguro_accidente, viewGroup, false);
        b(inflate);
        return inflate;
    }

    private void b(View view) {
        ((TextView) view.findViewById(R.id.tvTitleSeguro)).setText(Html.fromHtml(this.c.getCotizacion().getNombre()));
        TextView textView = (TextView) view.findViewById(R.id.F27_12_lbl_data_importe_mensual);
        textView.setText(this.c.getCotizacion().getCuota());
        TextView textView2 = (TextView) view.findViewById(R.id.F27_12_lbl_data_suma_asegurada);
        textView2.setText(this.c.getCotizacion().getSumaAsegurada());
        TextView textView3 = (TextView) view.findViewById(R.id.F27_12_lbl_data_cuota_mensual);
        textView3.setText(this.c.getCotizacion().getCuota());
        TextView textView4 = (TextView) view.findViewById(R.id.F27_12_lbl_data_cobertura);
        textView4.setText(Html.fromHtml(this.c.getCotizacion().getDescCobertura()).toString());
        TextView textView5 = (TextView) view.findViewById(R.id.F27_12_lbl_data_medio_pago);
        this.h = ((ContratarSeguroAccidenteActivity) getActivity()).getIntent().getStringExtra(ContratarSeguroAccidenteActivity.MEDIO_DE_PAGO);
        textView5.setText(this.h);
        TextView textView6 = (TextView) view.findViewById(R.id.F27_12_lbl_data_medio_envio_poliza);
        textView6.setText(Html.fromHtml(this.c.getCotizacion().getFormaPago()));
        TextView textView7 = (TextView) view.findViewById(R.id.F27_12_lbl_data_beneficiarios);
        textView7.setText(Html.fromHtml(this.c.getCotizacion().getBeneficiarios()).toString());
        for (LeyendaItem leyendaItem : this.c.getListaLeyendas().getLeyenda()) {
            if (leyendaItem.getIdLeyenda().equals("SEG_COT_ACC")) {
                TextView textView8 = (TextView) view.findViewById(R.id.F27_12_txt_leyenda2);
                textView8.setText(Html.fromHtml(leyendaItem.getDescripcion()).toString());
                String charSequence = textView8.getText().toString();
                try {
                    charSequence = CAccessibility.getInstance(getContext()).applyFilterDateInText(charSequence);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                String applyFilter_pisos = CAccessibility.getInstance(getContext()).applyFilter_pisos(CAccessibility.getInstance(getContext()).applyFilterNumeroGradoBarra(charSequence));
                try {
                    applyFilter_pisos = CAccessibility.getInstance(getContext()).applyFilterCharacterToCharacterSpecifict(applyFilter_pisos);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                textView8.setContentDescription(CAccessibility.getInstance(getContext()).applyGuion(CAccessibility.getInstance(getContext()).applyFilter_BsAs(CAccessibility.getInstance(getContext()).applyFilterPagina(CAccessibility.getInstance(getContext()).applyFilter_IGJ(CAccessibility.getInstance(getContext()).applyFilterSiglasTelefono(CAccessibility.getInstance(getContext()).applyFilterTelephoneMask(CAccessibility.getInstance(getContext()).applyFilterCUIT(applyFilter_pisos))))))));
            }
        }
        ((TextView) view.findViewById(R.id.ID_4685_SEGUROS_DETALLE_DE_COBERTURA)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CotizacionSeguroAccidenteCoberturaFragment cotizacionSeguroAccidenteCoberturaFragment = new CotizacionSeguroAccidenteCoberturaFragment(CotizacionSeguroAccidenteFragment.this.c);
                FragmentTransaction beginTransaction = CotizacionSeguroAccidenteFragment.this.getFragmentManager().beginTransaction();
                beginTransaction.setCustomAnimations(R.anim.enter_up_anim, R.anim.no_animation, R.anim.no_animation, R.anim.exit_down_anim);
                beginTransaction.replace(R.id.content, cotizacionSeguroAccidenteCoberturaFragment, FragmentConstants.SEGURO_COTIZACION_COBERTURA);
                beginTransaction.addToBackStack(FragmentConstants.SEGURO_COTIZACION_COBERTURA);
                beginTransaction.commit();
            }
        });
        ((TextView) view.findViewById(R.id.f4ID_4686_SEGUROS_TRMINOS_Y_CONDICIONES)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CotizacionSegAccidenteCondicionesFragment cotizacionSegAccidenteCondicionesFragment = new CotizacionSegAccidenteCondicionesFragment(CotizacionSeguroAccidenteFragment.this.c);
                FragmentTransaction beginTransaction = CotizacionSeguroAccidenteFragment.this.getFragmentManager().beginTransaction();
                beginTransaction.setCustomAnimations(R.anim.enter_up_anim, R.anim.no_animation, R.anim.no_animation, R.anim.exit_down_anim);
                beginTransaction.replace(R.id.content, cotizacionSegAccidenteCondicionesFragment, FragmentConstants.SEGURO_COTIZACION_TERMINOS_CONDICIONES);
                beginTransaction.addToBackStack(FragmentConstants.SEGURO_COTIZACION_TERMINOS_CONDICIONES);
                beginTransaction.commit();
            }
        });
        ((Button) view.findViewById(R.id.f2ID_4061_SEGUROS_BOTN_CONTRATARAHORA)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CotizacionSeguroAccidenteFragment.this.y();
            }
        });
        this.d = ((ContratarSeguroAccidenteActivity) getActivity()).getIntent().getStringExtra("TIPO_CUENTA");
        this.e = ((ContratarSeguroAccidenteActivity) getActivity()).getIntent().getStringExtra(ContratarSeguroAccidenteActivity.SUCURSAL_CUENTA);
        this.f = ((ContratarSeguroAccidenteActivity) getActivity()).getIntent().getStringExtra(ContratarSeguroAccidenteActivity.NUMERO_CUENTA);
        ((ContratarSeguroAccidenteActivity) getActivity()).dataManager.getCotizacionCompraProtegida();
        try {
            textView.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterAmount(textView.getText().toString()));
            textView2.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterAmount(textView2.getText().toString()));
            textView3.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterAmount(textView3.getText().toString()));
            textView4.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterGeneral(textView4.getText().toString()));
            textView5.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterAccount(textView5.getText().toString()));
            textView6.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterGeneral(textView6.getText().toString()));
            textView7.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterGeneral(textView7.getText().toString()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void y() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.SEGUROS_CONTRATAR_SGRO), getString(R.string.SEGUROS_CONTRATAR_SGRO_MSG), null, null, getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                CotizacionSeguroAccidenteFragment.this.b.setProgress(VGetContratarSeguroAccidente.nameService);
                ((ContratarSeguroAccidenteActivity) CotizacionSeguroAccidenteFragment.this.getActivity()).dataManager.contratarSeguroAccidente(CotizacionSeguroAccidenteFragment.this.c.getCotizacion(), CotizacionSeguroAccidenteFragment.this.d, CotizacionSeguroAccidenteFragment.this.e, CotizacionSeguroAccidenteFragment.this.f);
            }
        });
        newInstance.show(getFragmentManager(), "DialogConfirm");
    }

    @Subscribe
    public void contratarSeguroAccidente(ContratarSeguroAccidenteEvent contratarSeguroAccidenteEvent) {
        dismissProgress();
        final ContratarSeguroAccidenteEvent contratarSeguroAccidenteEvent2 = contratarSeguroAccidenteEvent;
        AnonymousClass5 r0 = new BaseWSResponseHandler(getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_COTIZACION, (BaseActivity) getContext(), getContext().getString(R.string.f182ID_4078_SEGUROS_LABEL_COMPROBANTEDECONTRATACINDESEGURO)) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ((ContratarSeguroAccidenteActivity) CotizacionSeguroAccidenteFragment.this.getActivity()).showComprobanteSeguroAccidente(contratarSeguroAccidenteEvent2.getResponse().getContratarSeguroAccidenteBean().getContratarSeguroAccidenteBean(), CotizacionSeguroAccidenteFragment.this.h);
            }
        };
        r0.handleWSResponse(contratarSeguroAccidenteEvent);
    }
}
