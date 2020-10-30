package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.base.ShareReceiptPolizaListener;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroAccidenteActivity.ValidatePermission;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetPolizaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaItem;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPoliza;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class ComprobanteSeguroAccidenteFragment extends ITRSABaseFragment implements ShareReceiptPolizaListener, ValidatePermission {
    @Inject
    IDataManager a;
    private RelativeLayout ad;
    private RelativeLayout ae;
    private RelativeLayout af;
    private RelativeLayout ag;
    private TextView ah;
    private TextView ai;
    private TextView aj;
    private TextView ak;
    private TextView al;
    private TextView am;
    private TextView an;
    private TextView ao;
    private String ap;
    private View aq;
    private ContratarSeguroAccidenteBean b;
    private View c;
    private GetCotizacionSeguroAccidenteBodyResponseBean d;
    private ValidatePermission e;
    private RelativeLayout f;
    private RelativeLayout g;
    private LinearLayout h;
    private RelativeLayout i;

    public ComprobanteSeguroAccidenteFragment() {
    }

    @SuppressLint({"ValidFragment"})
    public ComprobanteSeguroAccidenteFragment(ContratarSeguroAccidenteBean contratarSeguroAccidenteBean, GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean, String str) {
        this.b = contratarSeguroAccidenteBean;
        this.d = getCotizacionSeguroAccidenteBodyResponseBean;
        this.ap = str;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_comprobante_seguro_accidente, viewGroup, false);
        b(inflate);
        this.aq = inflate.findViewById(R.id.F10_00_scrl);
        this.e = this;
        configureShareReceiptPoliza(this.aq, this.b.getNumCertificado(), getActivity().getString(R.string.message_share_seguro));
        setShareReceiptPolizaListener(this);
        return inflate;
    }

    public void onResume() {
        super.onResume();
    }

    private void b(View view) {
        this.c = view.findViewById(R.id.F10_00_scrl);
        ((TextView) view.findViewById(R.id.tvTitleSeguro)).setText(Html.fromHtml(this.d.getCotizacion().getNombre()));
        TextView textView = (TextView) view.findViewById(R.id.F27_12_lbl_data_importe_mensual);
        textView.setText(this.d.getCotizacion().getCuota());
        TextView textView2 = (TextView) view.findViewById(R.id.F27_12_lbl_data_cuota_mensual);
        textView2.setText(this.d.getCotizacion().getCuota());
        TextView textView3 = (TextView) view.findViewById(R.id.F27_12_lbl_data_suma_asegurada);
        textView3.setText(this.d.getCotizacion().getSumaAsegurada());
        TextView textView4 = (TextView) view.findViewById(R.id.F27_12_lbl_data_cobertura);
        textView4.setText(Html.fromHtml(this.d.getCotizacion().getDescCobertura()).toString());
        TextView textView5 = (TextView) view.findViewById(R.id.F27_12_lbl_data_medio_envio_poliza);
        textView5.setText(Html.fromHtml(this.d.getCotizacion().getFormaPago()));
        TextView textView6 = (TextView) view.findViewById(R.id.F27_12_lbl_data_medio_pago);
        textView6.setText(this.ap);
        TextView textView7 = (TextView) view.findViewById(R.id.ID_4678_DATA_SEGUROS_BENEFICIARIOS);
        textView7.setText(this.d.getCotizacion().getBeneficiarios());
        TextView textView8 = (TextView) view.findViewById(R.id.F27_12_lbl_data_fecha_inicio);
        textView8.setText(this.b.getFechaInicio());
        TextView textView9 = (TextView) view.findViewById(R.id.F27_12_lbl_data_poliza);
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getNumPoliza());
        sb.append("/");
        sb.append(this.b.getNumCertificado());
        textView9.setText(sb.toString());
        for (LeyendaItem leyendaItem : this.b.getListaLeyendas().getLeyenda()) {
            if (leyendaItem.getIdLeyenda().equals("SEG_ACC_COMP")) {
                TextView textView10 = (TextView) view.findViewById(R.id.F27_12_txt_leyenda2);
                textView10.setText(Html.fromHtml(leyendaItem.getDescripcion()).toString());
                textView10.setContentDescription(CAccessibility.getInstance(getActivity()).m0applyFilterSSNNmero(CAccessibility.getInstance(getActivity()).applyFilterGuion(CAccessibility.getInstance(getActivity()).applyFilter_pisos(CAccessibility.getInstance(getActivity()).applyFilterCUIT(CAccessibility.getInstance(getActivity()).applyFilterTelephoneMask(CAccessibility.getInstance(getActivity()).applyFilterPagina(CAccessibility.getInstance(getActivity()).applyFilter_IGJ(CAccessibility.getInstance(getActivity()).applyFilter_BsAs(CAccessibility.getInstance(getActivity()).applyFilterSiglasTelefono(CAccessibility.getInstance(getActivity()).applyFilterNumeroGradoBarra(textView10.getText().toString())))))))))));
            }
        }
        ((Button) view.findViewById(R.id.f2ID_4061_SEGUROS_BOTN_CONTRATARAHORA)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ComprobanteSeguroAccidenteFragment.this.canExit();
            }
        });
        try {
            this.f = (RelativeLayout) view.findViewById(R.id.LAYOUT_SEGUROS_LABEL_SUMAASEGURADA);
            this.g = (RelativeLayout) view.findViewById(R.id.LAYOUT_SEGUROS_CUOTA_MENSUAL);
            this.h = (LinearLayout) view.findViewById(R.id.LAYOUT_SEGUROS_LABEL_COBERTURA);
            this.i = (RelativeLayout) view.findViewById(R.id.LAYOUT_SEGUROS_LABEL_MEDIODEPAGO);
            this.ad = (RelativeLayout) view.findViewById(R.id.LAYOUT_SEGUROS_MEDIO_ENVIO_POLIZA);
            this.ae = (RelativeLayout) view.findViewById(R.id.LAYOUT_SEGUROS_BENEFICIARIOS);
            this.af = (RelativeLayout) view.findViewById(R.id.LAYOUT_SEGUROS_LABEL_FECHADEINICIO);
            this.ag = (RelativeLayout) view.findViewById(R.id.LAYOUT_F27_12_lbl_poliza);
            this.ah = (TextView) view.findViewById(R.id.ID_4052_SEGUROS_LABEL_SUMAASEGURADA);
            this.ai = (TextView) view.findViewById(R.id.ID_4683_SEGUROS_CUOTA_MENSUAL);
            this.aj = (TextView) view.findViewById(R.id.ID_4051_SEGUROS_LABEL_COBERTURA);
            this.ak = (TextView) view.findViewById(R.id.ID_4028_SEGUROS_LABEL_MEDIODEPAGO);
            this.al = (TextView) view.findViewById(R.id.ID_4029_SEGUROS_MEDIO_ENVIO_POLIZA);
            this.am = (TextView) view.findViewById(R.id.ID_4678_SEGUROS_BENEFICIARIOS);
            this.an = (TextView) view.findViewById(R.id.ID_4026_SEGUROS_LABEL_FECHADEINICIO);
            this.ao = (TextView) view.findViewById(R.id.F27_12_lbl_poliza);
            textView.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(textView.getText().toString()));
            textView3.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(textView3.getText().toString()));
            RelativeLayout relativeLayout = this.f;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.ah.getText());
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(textView3.getContentDescription().toString());
            relativeLayout.setContentDescription(sb2.toString());
            textView2.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(textView2.getText().toString()));
            RelativeLayout relativeLayout2 = this.g;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.ai.getText());
            sb3.append(UtilsCuentas.SEPARAOR2);
            sb3.append(textView2.getContentDescription().toString());
            relativeLayout2.setContentDescription(sb3.toString());
            textView4.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(textView4.getText().toString()));
            LinearLayout linearLayout = this.h;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this.aj.getText());
            sb4.append(UtilsCuentas.SEPARAOR2);
            sb4.append(textView4.getContentDescription().toString());
            linearLayout.setContentDescription(sb4.toString());
            textView6.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterAccount(textView6.getText().toString()));
            RelativeLayout relativeLayout3 = this.i;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(this.ak.getText());
            sb5.append(UtilsCuentas.SEPARAOR2);
            sb5.append(textView6.getContentDescription().toString());
            relativeLayout3.setContentDescription(sb5.toString());
            textView5.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(textView5.getText().toString()));
            RelativeLayout relativeLayout4 = this.ad;
            StringBuilder sb6 = new StringBuilder();
            sb6.append(this.al.getText());
            sb6.append(UtilsCuentas.SEPARAOR2);
            sb6.append(textView5.getContentDescription().toString());
            relativeLayout4.setContentDescription(sb6.toString());
            RelativeLayout relativeLayout5 = this.ae;
            StringBuilder sb7 = new StringBuilder();
            sb7.append(this.am.getText());
            sb7.append(UtilsCuentas.SEPARAOR2);
            sb7.append(textView7.getText());
            relativeLayout5.setContentDescription(sb7.toString());
            RelativeLayout relativeLayout6 = this.af;
            StringBuilder sb8 = new StringBuilder();
            sb8.append(this.an.getText());
            sb8.append(UtilsCuentas.SEPARAOR2);
            sb8.append(textView8.getText());
            relativeLayout6.setContentDescription(sb8.toString());
            textView9.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(textView9.getText().toString()));
            RelativeLayout relativeLayout7 = this.ag;
            StringBuilder sb9 = new StringBuilder();
            sb9.append(this.ao.getText());
            sb9.append(UtilsCuentas.SEPARAOR2);
            sb9.append(textView9.getContentDescription().toString());
            relativeLayout7.setContentDescription(sb9.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Subscribe
    public void getPoliza(GetPolizaEvent getPolizaEvent) {
        dismissProgress();
        final GetPolizaEvent getPolizaEvent2 = getPolizaEvent;
        AnonymousClass2 r0 = new BaseWSResponseHandler(getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_COTIZACION, (BaseActivity) getContext(), getContext().getString(R.string.f182ID_4078_SEGUROS_LABEL_COMPROBANTEDECONTRATACINDESEGURO)) {
            /* access modifiers changed from: protected */
            public void onOk() {
                try {
                    if (UtilFile.canDisplayPdf(ComprobanteSeguroAccidenteFragment.this.getContext())) {
                        UtilFile.showPdf(UtilFile.saveFileFromBase64(getPolizaEvent2.getResponse().getPolizaBodyResponseBean.getArchivoPoliza(), String.format(SegurosConstants.POLIZA_FILE_NAME, new Object[]{getPolizaEvent2.getResponse().getPolizaBodyResponseBean.getArchivoNombre()}), ComprobanteSeguroAccidenteFragment.this.getContext()), ComprobanteSeguroAccidenteFragment.this.getContext());
                        return;
                    }
                    ComprobanteSeguroAccidenteFragment.this.showNoPdfApplicationPopUp(ComprobanteSeguroAccidenteFragment.this.getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        r0.handleWSResponse(getPolizaEvent);
    }

    public void onClikPoliza() {
        showProgress(VGetPoliza.nameService);
        this.a.getPoliza(this.d.getCotizacion().getCodRamo(), this.b.getNumPoliza(), this.b.getNumCertificado());
    }
}
