package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollListView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class PagosFragment$$ViewInjector {
    public static void inject(Finder finder, final PagosFragment pagosFragment, Object obj) {
        pagosFragment.vPageTablePaymentServices = finder.findRequiredView(obj, R.id.vTablePaymentServices, "field 'vPageTablePaymentServices'");
        pagosFragment.vPagePreparePaymentServices = finder.findRequiredView(obj, R.id.vPreparePaymentService, "field 'vPagePreparePaymentServices'");
        pagosFragment.vPageConfirmPaymentServices = finder.findRequiredView(obj, R.id.vConfirmPaymentServices, "field 'vPageConfirmPaymentServices'");
        pagosFragment.vPageReceiptPaymentServices = finder.findRequiredView(obj, R.id.vReceiptPaymentServices, "field 'vPageReceiptPaymentServices'");
        pagosFragment.svPagePrepare = (ScrollView) finder.findRequiredView(obj, R.id.svPagePrepare, "field 'svPagePrepare'");
        pagosFragment.svPageConfirm = (ScrollView) finder.findRequiredView(obj, R.id.svPageConfirm, "field 'svPageConfirm'");
        pagosFragment.svPageReceipt = (ScrollView) finder.findRequiredView(obj, R.id.svPageReceipt, "field 'svPageReceipt'");
        pagosFragment.vgButtonReceipt = finder.findRequiredView(obj, R.id.vgWrapperButton, "field 'vgButtonReceipt'");
        pagosFragment.layoutLink = (LinearLayout) finder.findRequiredView(obj, R.id.layoutLeyendaLink, "field 'layoutLink'");
        pagosFragment.descripcionLeyenda = (TextView) finder.findRequiredView(obj, R.id.descripcionLink, "field 'descripcionLeyenda'");
        pagosFragment.linkSeguro = (TextView) finder.findRequiredView(obj, R.id.link, "field 'linkSeguro'");
        pagosFragment.mListPaymentServices = (InfiniteScrollListView) finder.findRequiredView(obj, R.id.gvList, "field 'mListPaymentServices'");
        pagosFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.vgPaymentServices, "field 'mControlPager'");
        pagosFragment.vgWrapperFormPreparePayment = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperFormPreparePayment, "field 'vgWrapperFormPreparePayment'");
        pagosFragment.vgWrapperTableConfirmPayment = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperTableConfirmPayment, "field 'vgWrapperTableConfirmPayment'");
        pagosFragment.vgWrapperReceiptTopPayment = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperReceiptTopPayment, "field 'vgWrapperReceiptTopPayment'");
        pagosFragment.vgWrapperReceiptBottomPayment = (ViewGroup) finder.findRequiredView(obj, R.id.vgWrapperReceiptBottomPayment, "field 'vgWrapperReceiptBottomPayment'");
        pagosFragment.tvLegendTop = (TextView) finder.findRequiredView(obj, R.id.tvLegendTop, "field 'tvLegendTop'");
        pagosFragment.tvLegendBottom = (TextView) finder.findRequiredView(obj, R.id.tvLegendBottom, "field 'tvLegendBottom'");
        pagosFragment.wrapperLegend = finder.findRequiredView(obj, R.id.wrapperLegend, "field 'wrapperLegend'");
        pagosFragment.viewContainer = finder.findRequiredView(obj, R.id.pagoContainer, "field 'viewContainer'");
        View findRequiredView = finder.findRequiredView(obj, R.id.btnConfirmPayment, "field 'btnConfirmPayment' and method 'onClickButtonConfirmPayment'");
        pagosFragment.btnConfirmPayment = (Button) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                pagosFragment.onClickButtonConfirmPayment();
            }
        });
        finder.findRequiredView(obj, R.id.btnPreparePaymentService, "method 'onClickPreparePaymentService'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                pagosFragment.onClickPreparePaymentService();
            }
        });
        finder.findRequiredView(obj, R.id.btnReceiptPayment, "method 'onClickReceiptPayment'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                pagosFragment.onClickReceiptPayment();
            }
        });
    }

    public static void reset(PagosFragment pagosFragment) {
        pagosFragment.vPageTablePaymentServices = null;
        pagosFragment.vPagePreparePaymentServices = null;
        pagosFragment.vPageConfirmPaymentServices = null;
        pagosFragment.vPageReceiptPaymentServices = null;
        pagosFragment.svPagePrepare = null;
        pagosFragment.svPageConfirm = null;
        pagosFragment.svPageReceipt = null;
        pagosFragment.vgButtonReceipt = null;
        pagosFragment.layoutLink = null;
        pagosFragment.descripcionLeyenda = null;
        pagosFragment.linkSeguro = null;
        pagosFragment.mListPaymentServices = null;
        pagosFragment.mControlPager = null;
        pagosFragment.vgWrapperFormPreparePayment = null;
        pagosFragment.vgWrapperTableConfirmPayment = null;
        pagosFragment.vgWrapperReceiptTopPayment = null;
        pagosFragment.vgWrapperReceiptBottomPayment = null;
        pagosFragment.tvLegendTop = null;
        pagosFragment.tvLegendBottom = null;
        pagosFragment.wrapperLegend = null;
        pagosFragment.viewContainer = null;
        pagosFragment.btnConfirmPayment = null;
    }
}
