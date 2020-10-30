package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaCreditosFragment;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleCuotaTenenciaCreditoBodyResponseBean;
import ar.com.santander.rio.mbanking.view.AmountView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ListDetailsActivity extends BaseActivity {
    @InjectView(2131361794)
    public TextView CFTNAData;
    @InjectView(2131361796)
    public LinearLayout CFTNARl;
    @InjectView(2131362159)
    public TextView CFTNA_IVA_Name;
    @InjectView(2131361797)
    public TextView CFTNAsIvaData;
    @InjectView(2131361798)
    public LinearLayout CFTNAsIvaRl;
    @InjectView(2131364113)
    public TextView accountData;
    @InjectView(2131364115)
    public LinearLayout accountR2;
    @InjectView(2131364116)
    public LinearLayout accountRl;
    @InjectView(2131364303)
    public TextView coefData;
    @InjectView(2131364304)
    public LinearLayout coefRl;
    @InjectView(2131364436)
    public TextView cotizacionUva;
    @InjectView(2131364437)
    public LinearLayout cotizacionUvaRw;
    @InjectView(2131364484)
    public AmountView creditHeaderData;
    @InjectView(2131364485)
    public TextView creditHeaderDesc;
    @InjectView(2131364486)
    public TextView creditHeaderTitle;
    @InjectView(2131364487)
    public TextView creditTitle;
    @InjectView(2131364114)
    public TextView dateAprove;
    @InjectView(2131364574)
    public TextView dateCotizacionUva;
    @InjectView(2131364575)
    public LinearLayout dateCotizacionUvaRw;
    @InjectView(2131364576)
    public TextView dateData;
    @InjectView(2131364583)
    public LinearLayout dateRl;
    @InjectView(2131364821)
    public LinearLayout importeR3;
    @InjectView(2131364822)
    public TextView importeUVA;
    @InjectView(2131365004)
    public TextView leyendaTasaDetallePrestamo;
    @InjectView(2131365157)
    public TextView loanNumberData;
    @InjectView(2131365158)
    public LinearLayout loanNumberRl;
    @InjectView(2131365245)
    public TextView motivoData;
    @InjectView(2131365246)
    public LinearLayout motivoRl;
    @InjectView(2131365461)
    public TextView ratesData;
    @InjectView(2131365462)
    public RelativeLayout ratesRl;
    @InjectView(2131365839)
    public TextView teaData;
    @InjectView(2131365840)
    public LinearLayout teaRl;
    @InjectView(2131366052)
    public TextView tnaData;
    @InjectView(2131366053)
    public LinearLayout tnaRl;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_detalle_del_prestamo);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        ButterKnife.inject((Activity) this);
        b();
        configureActionBar();
    }

    private void b() {
        GetDetalleCuotaTenenciaCreditoBodyResponseBean getDetalleCuotaTenenciaCreditoBodyResponseBean;
        DatosCredito datosCredito;
        String str;
        Bundle extras = getIntent().getExtras();
        CAccessibility instance = CAccessibility.getInstance(getBaseContext());
        if (extras != null) {
            datosCredito = (DatosCredito) extras.getParcelable("credito_seleccionado");
            getDetalleCuotaTenenciaCreditoBodyResponseBean = (GetDetalleCuotaTenenciaCreditoBodyResponseBean) extras.getParcelable("detalle_cuota");
            str = extras.getString("tasa_leyendas");
        } else {
            str = null;
            datosCredito = null;
            getDetalleCuotaTenenciaCreditoBodyResponseBean = null;
        }
        if (getDetalleCuotaTenenciaCreditoBodyResponseBean != null && datosCredito != null) {
            this.creditHeaderDesc.setText("Importe solicitado");
            if (datosCredito.getNombrecredito() != null) {
                TextView textView = this.creditTitle;
                StringBuilder sb = new StringBuilder();
                sb.append("Detalle de Préstamo ");
                sb.append(Html.fromHtml(datosCredito.getNombrecredito()));
                textView.setText(sb.toString());
            }
            if (getDetalleCuotaTenenciaCreditoBodyResponseBean.getImporteSolicitado() != null) {
                TextView textView2 = this.creditHeaderTitle;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Préstamo ");
                sb2.append(Html.fromHtml(datosCredito.getNombrecredito()));
                textView2.setText(sb2.toString());
            }
            if (getDetalleCuotaTenenciaCreditoBodyResponseBean.getImporteSolicitado() != null) {
                this.creditHeaderData.setText(Html.fromHtml(getDetalleCuotaTenenciaCreditoBodyResponseBean.getImporteSolicitado()));
                try {
                    this.creditHeaderData.setContentDescription(instance.applyFilterAmount(this.creditHeaderData.getText().toString()));
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
            setUpLabel(datosCredito.getDescripcredito(), this.loanNumberRl, this.loanNumberData, "CHARACTERTOCHARACTER", getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getCuentaDebito(), this.accountRl, this.accountData, TenenciaCreditosFragment.ACCOUNTNAME, getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getFechaSolicitud(), this.accountR2, this.dateAprove, "DATE", getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getPlazoCredito(), this.dateRl, this.dateData);
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getImpSolUva(), this.importeR3, this.importeUVA, TenenciaCreditosFragment.ACCOUNTNAME, getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTtasaTNA(), this.tnaRl, this.tnaData, TenenciaCreditosFragment.PERCENTAJE, getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTasaTEA(), this.teaRl, this.teaData, TenenciaCreditosFragment.PERCENTAJE, getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTtasaCFTNA(), this.CFTNARl, this.CFTNAData, TenenciaCreditosFragment.PERCENTAJE, getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTtasaCTFNAIVA(), this.CFTNAsIvaRl, this.CFTNAsIvaData, TenenciaCreditosFragment.PERCENTAJE, getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getCotizacionUva(), this.cotizacionUvaRw, this.cotizacionUva, "AMOUNT", getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getFechaCotizaUva(), this.dateCotizacionUvaRw, this.dateCotizacionUva, "DATE", getBaseContext());
            setUpLabel(getDetalleCuotaTenenciaCreditoBodyResponseBean.getMotivoCredito(), this.motivoRl, this.motivoData);
            setUpLabel(null, this.coefRl, this.coefData);
            if (str == null || str.isEmpty()) {
                this.leyendaTasaDetallePrestamo.setVisibility(8);
            } else {
                this.leyendaTasaDetallePrestamo.setText(str);
            }
            try {
                this.leyendaTasaDetallePrestamo.setContentDescription(CAccessibility.getInstance(this).applyCFTEA(CAccessibility.getInstance(this).applyFilterAcronymTasas(CAccessibility.getInstance(this).applyCFTEA_IVA_3(str))));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                this.CFTNAsIvaRl.setContentDescription(CAccessibility.getInstance(this).applyCFTN_IVA(this.CFTNA_IVA_Name.getText().toString(), instance.applyFilterTasaValue(this.CFTNAsIvaData.getText().toString())));
            } catch (Exception e3) {
                e3.fillInStackTrace();
            }
        }
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
                    ListDetailsActivity.this.onBackPressed();
                }
            });
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public static void setUpLabel(String str, LinearLayout linearLayout, TextView textView) {
        setUpLabel(str, linearLayout, textView, null, null);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setUpLabel(java.lang.String r1, android.widget.LinearLayout r2, android.widget.TextView r3, java.lang.String r4, android.content.Context r5) {
        /*
            if (r1 != 0) goto L_0x0009
            r1 = 8
            r2.setVisibility(r1)
            goto L_0x00c5
        L_0x0009:
            r0 = 0
            r2.setVisibility(r0)
            r3.setText(r1)
            if (r4 == 0) goto L_0x00c5
            if (r5 == 0) goto L_0x00c5
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r5)
            r2 = -1
            int r5 = r4.hashCode()     // Catch:{ Exception -> 0x00c1 }
            switch(r5) {
                case -1275086312: goto L_0x0052;
                case -436740361: goto L_0x0048;
                case 2090926: goto L_0x003e;
                case 523665477: goto L_0x0034;
                case 637834440: goto L_0x002a;
                case 1934443608: goto L_0x0021;
                default: goto L_0x0020;
            }     // Catch:{ Exception -> 0x00c1 }
        L_0x0020:
            goto L_0x005c
        L_0x0021:
            java.lang.String r5 = "AMOUNT"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c1 }
            if (r4 == 0) goto L_0x005c
            goto L_0x005d
        L_0x002a:
            java.lang.String r5 = "GENERAL"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c1 }
            if (r4 == 0) goto L_0x005c
            r0 = 4
            goto L_0x005d
        L_0x0034:
            java.lang.String r5 = "CHARACTERTOCHARACTER"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c1 }
            if (r4 == 0) goto L_0x005c
            r0 = 1
            goto L_0x005d
        L_0x003e:
            java.lang.String r5 = "DATE"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c1 }
            if (r4 == 0) goto L_0x005c
            r0 = 2
            goto L_0x005d
        L_0x0048:
            java.lang.String r5 = "PERCENTAJE"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c1 }
            if (r4 == 0) goto L_0x005c
            r0 = 3
            goto L_0x005d
        L_0x0052:
            java.lang.String r5 = "ACCOUNTNAME"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00c1 }
            if (r4 == 0) goto L_0x005c
            r0 = 5
            goto L_0x005d
        L_0x005c:
            r0 = -1
        L_0x005d:
            switch(r0) {
                case 0: goto L_0x00b1;
                case 1: goto L_0x00a1;
                case 2: goto L_0x0091;
                case 3: goto L_0x0081;
                case 4: goto L_0x0071;
                case 5: goto L_0x0061;
                default: goto L_0x0060;
            }     // Catch:{ Exception -> 0x00c1 }
        L_0x0060:
            goto L_0x00c5
        L_0x0061:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r1 = r1.applyFilterAccount(r2)     // Catch:{ Exception -> 0x00c1 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c1 }
            goto L_0x00c5
        L_0x0071:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x00c1 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c1 }
            goto L_0x00c5
        L_0x0081:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r1 = r1.applyFilterTasaValue(r2)     // Catch:{ Exception -> 0x00c1 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c1 }
            goto L_0x00c5
        L_0x0091:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r1 = r1.applyFilterDate(r2)     // Catch:{ Exception -> 0x00c1 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c1 }
            goto L_0x00c5
        L_0x00a1:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x00c1 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c1 }
            goto L_0x00c5
        L_0x00b1:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x00c1 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00c1 }
            goto L_0x00c5
        L_0x00c1:
            r1 = move-exception
            r1.fillInStackTrace()
        L_0x00c5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ListDetailsActivity.setUpLabel(java.lang.String, android.widget.LinearLayout, android.widget.TextView, java.lang.String, android.content.Context):void");
    }
}
