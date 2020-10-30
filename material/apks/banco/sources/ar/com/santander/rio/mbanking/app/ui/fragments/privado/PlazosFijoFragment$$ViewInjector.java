package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.AmountView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class PlazosFijoFragment$$ViewInjector {
    public static void inject(Finder finder, final PlazosFijoFragment plazosFijoFragment, Object obj) {
        plazosFijoFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.vfLongTermDeposit, "field 'mControlPager'");
        plazosFijoFragment.pageCreate = finder.findRequiredView(obj, R.id.pageCreate, "field 'pageCreate'");
        plazosFijoFragment.pageConfirm = finder.findRequiredView(obj, R.id.pageConfirm, "field 'pageConfirm'");
        plazosFijoFragment.pageReceipt = finder.findRequiredView(obj, R.id.pageReceipt, "field 'pageReceipt'");
        plazosFijoFragment.pageList = finder.findRequiredView(obj, R.id.pageList, "field 'pageList'");
        plazosFijoFragment.pageDetail = finder.findRequiredView(obj, R.id.pageDetail, "field 'pageDetail'");
        plazosFijoFragment.pageRates = finder.findRequiredView(obj, R.id.pageRates, "field 'pageRates'");
        plazosFijoFragment.vgWrapperListLongTermDeposit = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperListLongTermDeposit, "field 'vgWrapperListLongTermDeposit'");
        plazosFijoFragment.vgWrapperRatesLongTermDeposit = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperRatesLongTermDeposit, "field 'vgWrapperRatesLongTermDeposit'");
        plazosFijoFragment.lblMontoMinimo = (TextView) finder.findRequiredView(obj, R.id.F10_02_lbl_montoMinimo, "field 'lblMontoMinimo'");
        plazosFijoFragment.vgWrapperCreateLongTermDeposit = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperCreateLongTermDeposit, "field 'vgWrapperCreateLongTermDeposit'");
        plazosFijoFragment.vgWrapperConfirmLongTermDeposit = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperConfirmLongTermDeposit, "field 'vgWrapperConfirmLongTermDeposit'");
        plazosFijoFragment.vgWrapperReceiptLongTermDeposit = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperReceiptLongTermDeposit, "field 'vgWrapperReceiptLongTermDeposit'");
        plazosFijoFragment.txtMontoInDetail = (AmountView) finder.findRequiredView(obj, R.id.F10_01_lbl_monto, "field 'txtMontoInDetail'");
        plazosFijoFragment.txtVencimientoInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_vencimiento, "field 'txtVencimientoInDetail'");
        plazosFijoFragment.txtTipoInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_tipoPlazoFijo, "field 'txtTipoInDetail'");
        plazosFijoFragment.txtCapitalInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_capital, "field 'txtCapitalInDetail'");
        plazosFijoFragment.txtCapitalUviLabel = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_capital_uvi, "field 'txtCapitalUviLabel'");
        plazosFijoFragment.txtCapitalUviInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_capital_uvi, "field 'txtCapitalUviInDetail'");
        plazosFijoFragment.txtInteresesLabel = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_intereses, "field 'txtInteresesLabel'");
        plazosFijoFragment.txtInteresesInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_intereses, "field 'txtInteresesInDetail'");
        plazosFijoFragment.txtValorUvaDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_valor_uva, "field 'txtValorUvaDetail'");
        plazosFijoFragment.rltValorUva = (RelativeLayout) finder.findRequiredView(obj, R.id.F10_01_rlt_valor_uva, "field 'rltValorUva'");
        plazosFijoFragment.txtTasaLabel = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_tasa, "field 'txtTasaLabel'");
        plazosFijoFragment.txtTasaInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_tasa, "field 'txtTasaInDetail'");
        plazosFijoFragment.txtImpuestosInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_impuestos, "field 'txtImpuestosInDetail'");
        plazosFijoFragment.txtConstitucionInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_fechaConstitucion, "field 'txtConstitucionInDetail'");
        plazosFijoFragment.txtCertificadoInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_certificado, "field 'txtCertificadoInDetail'");
        plazosFijoFragment.txtSucursalLabel = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_sucRadicacion, "field 'txtSucursalLabel'");
        plazosFijoFragment.txtSucursalInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_sucRadicacion, "field 'txtSucursalInDetail'");
        plazosFijoFragment.lnlCapitalUvi = (LinearLayout) finder.findRequiredView(obj, R.id.F10_01_LNL_CAPITAL_UVI, "field 'lnlCapitalUvi'");
        plazosFijoFragment.txtCanalInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_canal, "field 'txtCanalInDetail'");
        plazosFijoFragment.txtAccionVencimientoLabel = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_accionVto, "field 'txtAccionVencimientoLabel'");
        plazosFijoFragment.txtAccionVencimientoInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_data_accionVto, "field 'txtAccionVencimientoInDetail'");
        plazosFijoFragment.txtLblLeyendaInDetail = (TextView) finder.findRequiredView(obj, R.id.F10_01_lbl_leyenda, "field 'txtLblLeyendaInDetail'");
        View findRequiredView = finder.findRequiredView(obj, R.id.btnContinueInCreate, "field 'btnContinueInCreate' and method 'onClickContinueInCreate'");
        plazosFijoFragment.btnContinueInCreate = (Button) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                plazosFijoFragment.onClickContinueInCreate();
            }
        });
        View findRequiredView2 = finder.findRequiredView(obj, R.id.btnConstituirInConfirm, "field 'btnConstituirInConfirm' and method 'onClickConstituirInConfirm'");
        plazosFijoFragment.btnConstituirInConfirm = (Button) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                plazosFijoFragment.onClickConstituirInConfirm();
            }
        });
        View findRequiredView3 = finder.findRequiredView(obj, R.id.btnReturnInReceipt, "field 'btnReturnInReceipt' and method 'onClickReceipt'");
        plazosFijoFragment.btnReturnInReceipt = (Button) findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                plazosFijoFragment.onClickReceipt();
            }
        });
        plazosFijoFragment.tvLegendConfirm = (TextView) finder.findRequiredView(obj, R.id.tvLegendConfirm, "field 'tvLegendConfirm'");
        plazosFijoFragment.tvLegendReceipt = (TextView) finder.findRequiredView(obj, R.id.tvLegendReceipt, "field 'tvLegendReceipt'");
        plazosFijoFragment.vgLegendConfirm = (ViewGroup) finder.findRequiredView(obj, R.id.wpLegendConfirm, "field 'vgLegendConfirm'");
        plazosFijoFragment.vgLegendReceipt = (ViewGroup) finder.findRequiredView(obj, R.id.wpLegendReceipt, "field 'vgLegendReceipt'");
        plazosFijoFragment.svPageCreate = (ScrollView) finder.findRequiredView(obj, R.id.svPageCreate, "field 'svPageCreate'");
        plazosFijoFragment.svPageConfirm = (ScrollView) finder.findRequiredView(obj, R.id.svPageConfirm, "field 'svPageConfirm'");
        plazosFijoFragment.svPageReceipt = (ScrollView) finder.findRequiredView(obj, R.id.svPageReceipt, "field 'svPageReceipt'");
        plazosFijoFragment.vgListWithoutRowsLongTermDeposit = (LinearLayout) finder.findRequiredView(obj, R.id.vgListWithoutRowsLongTermDeposit, "field 'vgListWithoutRowsLongTermDeposit'");
        plazosFijoFragment.vgErrorWSLongTermDeposit = (LinearLayout) finder.findRequiredView(obj, R.id.vgErrorWSLongTermDeposit, "field 'vgErrorWSLongTermDeposit'");
        plazosFijoFragment.lbl_errorWs = (TextView) finder.findRequiredView(obj, R.id.F10_00_lbl_errorWs, "field 'lbl_errorWs'");
        plazosFijoFragment.vtitlebp = (TextView) finder.findRequiredView(obj, R.id.vTitleBP, "field 'vtitlebp'");
        plazosFijoFragment.txtMsjNoPlazos = (TextView) finder.findRequiredView(obj, R.id.vgListWithoutRowsLongTermDepositTxt, "field 'txtMsjNoPlazos'");
        plazosFijoFragment.txtMsjNoPlazosBold = (TextView) finder.findRequiredView(obj, R.id.vgListWithoutRowsLongTermDepositBoldTxt, "field 'txtMsjNoPlazosBold'");
        plazosFijoFragment.btnLinkTasas = (TextView) finder.findRequiredView(obj, R.id.F10_00_lbl_linkTasas, "field 'btnLinkTasas'");
    }

    public static void reset(PlazosFijoFragment plazosFijoFragment) {
        plazosFijoFragment.mControlPager = null;
        plazosFijoFragment.pageCreate = null;
        plazosFijoFragment.pageConfirm = null;
        plazosFijoFragment.pageReceipt = null;
        plazosFijoFragment.pageList = null;
        plazosFijoFragment.pageDetail = null;
        plazosFijoFragment.pageRates = null;
        plazosFijoFragment.vgWrapperListLongTermDeposit = null;
        plazosFijoFragment.vgWrapperRatesLongTermDeposit = null;
        plazosFijoFragment.lblMontoMinimo = null;
        plazosFijoFragment.vgWrapperCreateLongTermDeposit = null;
        plazosFijoFragment.vgWrapperConfirmLongTermDeposit = null;
        plazosFijoFragment.vgWrapperReceiptLongTermDeposit = null;
        plazosFijoFragment.txtMontoInDetail = null;
        plazosFijoFragment.txtVencimientoInDetail = null;
        plazosFijoFragment.txtTipoInDetail = null;
        plazosFijoFragment.txtCapitalInDetail = null;
        plazosFijoFragment.txtCapitalUviLabel = null;
        plazosFijoFragment.txtCapitalUviInDetail = null;
        plazosFijoFragment.txtInteresesLabel = null;
        plazosFijoFragment.txtInteresesInDetail = null;
        plazosFijoFragment.txtValorUvaDetail = null;
        plazosFijoFragment.rltValorUva = null;
        plazosFijoFragment.txtTasaLabel = null;
        plazosFijoFragment.txtTasaInDetail = null;
        plazosFijoFragment.txtImpuestosInDetail = null;
        plazosFijoFragment.txtConstitucionInDetail = null;
        plazosFijoFragment.txtCertificadoInDetail = null;
        plazosFijoFragment.txtSucursalLabel = null;
        plazosFijoFragment.txtSucursalInDetail = null;
        plazosFijoFragment.lnlCapitalUvi = null;
        plazosFijoFragment.txtCanalInDetail = null;
        plazosFijoFragment.txtAccionVencimientoLabel = null;
        plazosFijoFragment.txtAccionVencimientoInDetail = null;
        plazosFijoFragment.txtLblLeyendaInDetail = null;
        plazosFijoFragment.btnContinueInCreate = null;
        plazosFijoFragment.btnConstituirInConfirm = null;
        plazosFijoFragment.btnReturnInReceipt = null;
        plazosFijoFragment.tvLegendConfirm = null;
        plazosFijoFragment.tvLegendReceipt = null;
        plazosFijoFragment.vgLegendConfirm = null;
        plazosFijoFragment.vgLegendReceipt = null;
        plazosFijoFragment.svPageCreate = null;
        plazosFijoFragment.svPageConfirm = null;
        plazosFijoFragment.svPageReceipt = null;
        plazosFijoFragment.vgListWithoutRowsLongTermDeposit = null;
        plazosFijoFragment.vgErrorWSLongTermDeposit = null;
        plazosFijoFragment.lbl_errorWs = null;
        plazosFijoFragment.vtitlebp = null;
        plazosFijoFragment.txtMsjNoPlazos = null;
        plazosFijoFragment.txtMsjNoPlazosBold = null;
        plazosFijoFragment.btnLinkTasas = null;
    }
}
