package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel.DatosCuota;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito;
import ar.com.santander.rio.mbanking.view.AmountView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetalleCuotaActivity extends BaseActivity {
    @InjectView(2131361794)
    public TextView CFTNAData;
    @InjectView(2131361796)
    public RelativeLayout CFTNARl;
    @InjectView(2131361797)
    public TextView CFTNAsIvaData;
    @InjectView(2131361798)
    public RelativeLayout CFTNAsIvaRl;
    @InjectView(2131364113)
    public TextView accountData;
    @InjectView(2131364116)
    public RelativeLayout accountRl;
    @InjectView(2131364144)
    public TextView ajusteCapitalMoraData;
    @InjectView(2131364146)
    public RelativeLayout ajusteCapitalMoraRl;
    @InjectView(2131364247)
    public TextView capitalData;
    @InjectView(2131364248)
    public RelativeLayout capitalRl;
    @InjectView(2131364249)
    public TextView capitalUvaData;
    @InjectView(2131364252)
    public RelativeLayout capitalUvaRl;
    @InjectView(2131364256)
    public TextView cargoSeguroVidaData;
    @InjectView(2131364257)
    public RelativeLayout cargoSeguroVidaRl;
    @InjectView(2131364305)
    public TextView coeficienteData;
    @InjectView(2131364306)
    public RelativeLayout coeficienteRl;
    @InjectView(2131364484)
    public AmountView creditHeaderData;
    @InjectView(2131364485)
    public TextView creditHeaderDesc;
    @InjectView(2131364486)
    public TextView creditHeaderTitle;
    @InjectView(2131364071)
    public TextView creditTitle;
    @InjectView(2131364564)
    public TextView cuotaNumberData;
    @InjectView(2131364565)
    public RelativeLayout cuotaNumberRl;
    @InjectView(2131364566)
    public TextView cuotaValueData;
    @InjectView(2131364567)
    public RelativeLayout cuotaValueRl;
    @InjectView(2131364576)
    public TextView dateData;
    @InjectView(2131364583)
    public RelativeLayout dateRl;
    @InjectView(2131365843)
    Button detailNextButton;
    @InjectView(2131364625)
    public TextView disponible_uso_data;
    @InjectView(2131364626)
    public RelativeLayout disponible_uso_rl;
    @InjectView(2131364695)
    public RelativeLayout fechaCotizacionDeUVAs_rl;
    @InjectView(2131364696)
    public TextView fechaDeCotizacionDeUVAs_data;
    @InjectView(2131364697)
    public TextView fechaVtoData;
    @InjectView(2131364698)
    public RelativeLayout fechaVtoRl;
    @InjectView(2131364720)
    public TextView goodAssuranceData;
    @InjectView(2131364721)
    public RelativeLayout goodAssuranceRl;
    @InjectView(2131364842)
    public TextView interesPesosUvaData;
    @InjectView(2131364843)
    public RelativeLayout interesPesosUvaRl;
    @InjectView(2131364844)
    public TextView interesesCompData;
    @InjectView(2131364845)
    public RelativeLayout interesesCompRl;
    @InjectView(2131364847)
    public TextView interesesPunitoriosData;
    @InjectView(2131364849)
    public RelativeLayout interesesPunitoriosRl;
    @InjectView(2131364853)
    public TextView interesesUvaData;
    @InjectView(2131364856)
    public RelativeLayout interesesUvaRl;
    @InjectView(2131364874)
    public RelativeLayout iva_rl;
    @InjectView(2131364996)
    public TextView leyendaData;
    @InjectView(2131365002)
    public RelativeLayout leyendaRl;
    @InjectView(2131364999)
    public TextView leyenda_int_punitorios;
    @InjectView(2131365003)
    public TextView leyenda_tasa_detalle;
    @InjectView(2131365021)
    public TextView linea_credito_data;
    @InjectView(2131365022)
    public RelativeLayout linea_credito_rl;
    @InjectView(2131365157)
    public TextView loanNumberData;
    @InjectView(2131365158)
    public RelativeLayout loanNumberRl;
    private DatosCredito p;
    private DatosCuota q;
    private String r;
    @InjectView(2131365508)
    public RelativeLayout relativeImporteDeCuotaPuraUVAS;
    private String s;
    @InjectView(2131362192)
    public TextView saldoAnteriorDeCapital;
    @InjectView(2131365616)
    public TextView saldoAnteriorSinAjustarData;
    @InjectView(2131365617)
    public RelativeLayout saldoAnteriorSinAjustarRl;
    @InjectView(2131365670)
    public TextView seguroIncendioData;
    @InjectView(2131365671)
    public RelativeLayout seguroIncendioRl;
    private String t;
    @InjectView(2131365836)
    public TextView taxesData;
    @InjectView(2131365837)
    public RelativeLayout taxesRl;
    @InjectView(2131365839)
    public TextView teaData;
    @InjectView(2131365840)
    public RelativeLayout teaRl;
    @InjectView(2131365939)
    public TextView textViewIVAData;
    @InjectView(2131365949)
    public TextView textViewImporteDeCuotaPuraUVAS_data;
    @InjectView(2131366052)
    public TextView tnaData;
    @InjectView(2131366053)
    public RelativeLayout tnaRl;
    private String u;
    @InjectView(2131366354)
    public TextView valorUvaData;
    @InjectView(2131366355)
    public RelativeLayout valorUvaRl;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_detalle_de_cuota_de_prestamo);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.r = extras.getString("cuenta_debito");
            this.s = extras.getString("plazo_credito");
            this.t = extras.getString("tasaLeyendas");
            this.u = extras.getString("leyenda_int_punit");
            this.q = (DatosCuota) extras.getParcelable("cuota");
            this.p = (DatosCredito) extras.getParcelable("selected_credit");
            ButterKnife.inject((Activity) this);
            configureActionBar();
            b();
        }
    }

    private void b() {
        this.detailNextButton.setVisibility(8);
        configDetailUI();
    }

    public void configDetailUI() {
        CAmount cAmount = new CAmount();
        if (!(this.q == null || this.q.getImporteCuota() == null)) {
            cAmount = new CAmount(this.q.getImporteCuota());
            cAmount.setSymbolCurrencyDollarOrPeso(this.p.getMoneda());
        }
        if (this.p.getNombrecredito() != null) {
            TextView textView = this.creditTitle;
            StringBuilder sb = new StringBuilder();
            sb.append("Detalle de cuota de Préstamo ");
            sb.append(Html.fromHtml(this.p.getNombrecredito()));
            textView.setText(sb.toString());
        }
        if (this.p.getNombrecredito() != null) {
            TextView textView2 = this.creditHeaderTitle;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Préstamo ");
            sb2.append(Html.fromHtml(this.p.getNombrecredito()));
            textView2.setText(sb2.toString());
        }
        if (!(this.q == null || this.q.getImporteCuota() == null)) {
            this.creditHeaderData.setText(cAmount.getAmountPossitive());
        }
        if (this.q != null) {
            setUpLabel(this.q.getFechaVencimiento(), this.fechaVtoRl, this.fechaVtoData);
        }
        if (this.q != null) {
            setUpLabel(cAmount.getAmountPossitive(), this.cuotaValueRl, this.cuotaValueData);
        }
        this.saldoAnteriorDeCapital.setText(getString(R.string.Saldo_Anterior_De_Capital_Sin_Ajustar_UVAS));
        setUpLabel(this.p.getDescripcredito(), this.loanNumberRl, this.loanNumberData);
        setUpLabel(this.r, this.accountRl, this.accountData);
        setUpLabel(this.p.getNroCuotaCredito(), this.cuotaNumberRl, this.cuotaNumberData);
        setUpLabel(this.s, this.dateRl, this.dateData);
        setUpLabel(this.p.getSaldoSinAjustar(), this.saldoAnteriorSinAjustarRl, this.saldoAnteriorSinAjustarData);
        setUpLabel(this.p.getCoeficiente(), this.coeficienteRl, this.coeficienteData);
        setUpLabel(this.p.getCapitalAjustado(), this.interesPesosUvaRl, this.interesPesosUvaData);
        setUpLabel(this.p.getImporteCuotaUVA(), this.relativeImporteDeCuotaPuraUVAS, this.textViewImporteDeCuotaPuraUVAS_data);
        setUpLabel(this.p.getValorUVA(), this.valorUvaRl, this.valorUvaData);
        setUpLabel(this.p.getCapitalUVA(), this.capitalUvaRl, this.capitalUvaData);
        setUpLabel(this.p.getFechaCotizaUVA(), this.fechaCotizacionDeUVAs_rl, this.fechaDeCotizacionDeUVAs_data);
        setUpLabel(this.p.getInteresesUVA(), this.interesesUvaRl, this.interesesUvaData);
        setUpLabel(this.p.getCapitalcuotacredito(), this.capitalRl, this.capitalData);
        setUpLabel(this.p.getAjusteCapitalMora(), this.ajusteCapitalMoraRl, this.ajusteCapitalMoraData);
        setUpLabel(this.p.getInteresescomp(), this.interesesCompRl, this.interesesCompData);
        setUpLabel(this.p.getInteresesPunitorios(), this.interesesPunitoriosRl, this.interesesPunitoriosData);
        setUpLabel(this.p.getCargoSeguroVida(), this.cargoSeguroVidaRl, this.cargoSeguroVidaData);
        setUpLabel(this.p.getOtrosImpuestos(), this.taxesRl, this.taxesData);
        setUpLabel(this.p.getSeguroBien(), this.goodAssuranceRl, this.goodAssuranceData);
        setUpLabel(this.p.getSeguroIncendio(), this.seguroIncendioRl, this.seguroIncendioData);
        setUpLabel(this.p.getIva(), this.iva_rl, this.textViewIVAData);
        setUpLabel(null, this.disponible_uso_rl, this.disponible_uso_data);
        setUpLabel(null, this.linea_credito_rl, this.linea_credito_data);
        setUpLabel(this.p.getTasatea(), this.teaRl, this.teaData);
        setUpLabel(this.p.getTasatna(), this.tnaRl, this.tnaData);
        setUpLabel(this.p.getTasacftna(), this.CFTNARl, this.CFTNAData);
        setUpLabel(this.p.getTasactfnaiva(), this.CFTNAsIvaRl, this.CFTNAsIvaData);
        setUpLabel(null, this.leyendaRl, this.leyendaData);
        setUpLabel(this.u, this.leyendaRl, this.leyenda_int_punitorios);
        if (this.t != null) {
            this.leyenda_tasa_detalle.setText(this.t.trim());
        } else {
            this.leyenda_tasa_detalle.setVisibility(8);
        }
        if (this.p.getHabpagarcredito().equals("S")) {
            this.detailNextButton.setEnabled(true);
        } else {
            this.detailNextButton.setEnabled(false);
        }
        try {
            this.creditHeaderData.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.creditHeaderData.getText().toString()));
            this.loanNumberData.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.loanNumberData.getText().toString()));
            this.accountData.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(this.accountData.getText().toString()));
            this.saldoAnteriorSinAjustarData.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.saldoAnteriorSinAjustarData.getText().toString()));
            this.interesPesosUvaData.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.interesPesosUvaData.getText().toString()));
            this.cuotaValueData.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.cuotaValueData.getText().toString()));
            this.interesesCompData.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.interesesCompData.getText().toString()));
            this.taxesData.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.taxesData.getText().toString()));
            this.textViewIVAData.setContentDescription(CAccessibility.getInstance(this).applyFilterTasaInteres(this.textViewIVAData.getText().toString()));
            this.teaData.setContentDescription(CAccessibility.getInstance(this).applyFilterTasaValue(this.teaData.getText().toString()));
            this.tnaData.setContentDescription(CAccessibility.getInstance(this).applyFilterTasaValue(this.tnaData.getText().toString()));
            this.CFTNAData.setContentDescription(CAccessibility.getInstance(this).applyFilterTasaValue(this.CFTNAData.getText().toString()));
            this.CFTNAsIvaData.setContentDescription(CAccessibility.getInstance(this).applyCFTEA_IVA_3(this.CFTNAsIvaData.getText().toString()));
            this.leyenda_tasa_detalle.setContentDescription(CAccessibility.getInstance(this).applyCFTEA(CAccessibility.getInstance(this).applyFilterAcronymTasas(CAccessibility.getInstance(this).applyCFTEA_IVA_3(this.t))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUpLabel(String str, RelativeLayout relativeLayout, TextView textView) {
        if (str == null) {
            relativeLayout.setVisibility(8);
            return;
        }
        relativeLayout.setVisibility(0);
        textView.setText(Html.fromHtml(str).toString().trim());
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.PUSH_CLOSE);
        enableCloseButton();
    }

    public void enableCloseButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.ok);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DetalleCuotaActivity.this.finish();
                    DetalleCuotaActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
                }
            });
        }
    }
}
