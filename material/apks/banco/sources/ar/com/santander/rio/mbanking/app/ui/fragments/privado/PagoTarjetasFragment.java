package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CCnsDeuda;
import ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.AyudaTarjetaPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.AyudaTarjetaPresenterImp;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.ComprobantePagoPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.ComprobantePagoPresenterImp;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.ComprobanteStopDebitPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.ComprobanteStopDebitPresenterImp;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.ConfirmarPagoPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.ConfirmarPagoPresenterImp;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.ConfirmarStopDebitPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.ConfirmarStopDebitPresenterImp;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.DetalleTarjetaPagoTotalDolaresPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.DetalleTarjetaPagoTotalDolaresPresenterImp;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.DetalleTarjetaPagoTotalPesosPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.DetalleTarjetaPagoTotalPesosPresenterImp;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.PagoTarjetasView;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.PrepararPagoPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.creditCards.PrepararPagoPresenterImp;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.PagoTarjetasAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollListView;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaEvent;
import ar.com.santander.rio.mbanking.services.events.PagoTarjetaEvent;
import ar.com.santander.rio.mbanking.services.events.StopDebitEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaPTResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoTarjetaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.StopDebitResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaPTBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeuda;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import ar.com.santander.rio.mbanking.view.DateView;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnFocusChange;
import com.squareup.otto.Subscribe;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class PagoTarjetasFragment extends BaseFragment implements OnClickListener, PagoTarjetasView {
    public static final String TAG = "ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment";
    public static final String TAG_FRAGMENT_LOAD = "tag_fragment_load";
    @Inject
    SessionManager a;
    private DetalleTarjetaPagoTotalPesosPresenter ad;
    private ConfirmarStopDebitPresenter ae;
    private ComprobanteStopDebitPresenter af;
    /* access modifiers changed from: private */
    public ConfirmarPagoPresenter ag;
    private ComprobantePagoPresenter ah;
    private int ai;
    /* access modifiers changed from: private */
    public ArrayList<String> aj = new ArrayList<>();
    private ArrayList<String> ak = new ArrayList<>();
    private CnsDeudaPTBodyResponseBean al = null;
    private int am = -1;
    private boolean an = false;
    private boolean ao = false;
    private String ap = "";
    private boolean aq = false;

    /* renamed from: ar reason: collision with root package name */
    private OptionsToShare f240ar;
    private SantanderRioMainActivity as;
    /* access modifiers changed from: private */
    public boolean at = false;
    @Inject
    IDataManager b;
    @Inject
    AnalyticsManager c;
    @InjectView(2131364339)
    View comprobanteStopDebit;
    /* access modifiers changed from: private */
    public ProgresIndicator d;
    private PagoTarjetasAdapter e;
    @InjectView(2131365347)
    NumericEditText etValueDollars;
    @InjectView(2131365356)
    NumericEditText etValuePesos;
    /* access modifiers changed from: private */
    public PagoTarjetasController f;
    private PrepararPagoPresenter g;
    private AyudaTarjetaPresenter h;
    private DetalleTarjetaPagoTotalDolaresPresenter i;
    @InjectView(2131365396)
    ViewFlipper mControlPager;
    @InjectView(16908298)
    InfiniteScrollListView mCreditCardsList;
    @InjectView(2131364172)
    View pantallaAyudaTarjeta;
    @InjectView(2131364344)
    View pantallaComprobantePago;
    @InjectView(2131364348)
    View pantallaComprobanteStopDebit;
    @InjectView(2131364412)
    View pantallaConfirmarPago;
    @InjectView(2131364414)
    View pantallaConfirmarStopDebit;
    @InjectView(2131364615)
    View pantallaDetalleTarjetaPagoTotalDolares;
    @InjectView(2131364616)
    View pantallaDetalleTarjetaPagoTotalPesos;
    @InjectView(2131365340)
    View pantallaPrepararPago;
    @InjectView(2131365797)
    ScrollView svComprobantePagoTarjeta;
    @InjectView(2131365798)
    ScrollView svComprobanteStopDebit;

    public SessionManager getSessionManager() {
        return null;
    }

    public void setModalInPageAnimation() {
    }

    public void setModalOutPageAnimation() {
    }

    public void setNextPageAnimation() {
    }

    public void setPreviusPageAnimation() {
    }

    public void showMessage(String str, String str2) {
    }

    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        if (this.as != null) {
            this.as.hideKeyboard();
        }
        if (this.mControlPager.getDisplayedChild() > 1) {
            this.an = false;
            this.ao = false;
            switch (this.mControlPager.getDisplayedChild()) {
                case 2:
                    previousPage();
                    return;
                case 3:
                    if (isForgetShareReceipt()) {
                        onClickShowActionShareReceipt(this.svComprobantePagoTarjeta);
                        return;
                    } else {
                        switchDrawer();
                        return;
                    }
                case 4:
                    gotoPage(1);
                    return;
                case 5:
                    if (isForgetShareReceipt()) {
                        onClickShowActionShareReceipt(this.svComprobanteStopDebit);
                        return;
                    } else {
                        goToPagoTarjetas();
                        return;
                    }
                case 6:
                    gotoPage(1);
                    return;
                case 7:
                    gotoPage(1);
                    return;
                case 8:
                    this.an = true;
                    gotoPage(1);
                    return;
                default:
                    return;
            }
        } else if (this.mControlPager.getDisplayedChild() == 1) {
            goToPagoTarjetas();
        } else {
            switchDrawer();
        }
    }

    public void onAttach(Context context) {
        Log.d(TAG, "onAttach()");
        super.onAttach(context);
        this.as = (SantanderRioMainActivity) getActivity();
        if (this.as != null) {
            this.as.setActionBarType(ActionBarType.MENU);
            this.as.lockMenu(false);
        }
        enableMenuButton();
    }

    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate()");
        super.onCreate(bundle);
        this.g = new PrepararPagoPresenterImp(this);
        this.h = new AyudaTarjetaPresenterImp(this);
        this.i = new DetalleTarjetaPagoTotalDolaresPresenterImp(this);
        this.ad = new DetalleTarjetaPagoTotalPesosPresenterImp(this);
        this.ae = new ConfirmarStopDebitPresenterImp(this);
        this.af = new ComprobanteStopDebitPresenterImp(this);
        this.ag = new ConfirmarPagoPresenterImp(this);
        this.ah = new ComprobantePagoPresenterImp(this);
        this.d = ProgresIndicator.newInstance(VCnsDeuda.nameService);
        showProgress();
        this.b.cnsDeuda();
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Log.d(TAG, "onCreateView()");
        this.c.trackScreen(getString(R.string.analytics_screen_name_pago_tarjetas_home));
        View inflate = layoutInflater.inflate(R.layout.pago_tarjetas_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        View inflate2 = layoutInflater.inflate(R.layout.list_header_pago_tarjetas, this.mCreditCardsList, false);
        inflate2.findViewById(R.id.credit_cards_list_header_1_container).setOnClickListener(null);
        inflate2.findViewById(R.id.credit_cards_list_header_2_container).setOnClickListener(null);
        this.mCreditCardsList.addHeaderView(inflate2);
        new ColorDrawable(getResources().getColor(R.color.grey_light));
        setTAG(FragmentConstants.PAGO_TARJETAS);
        return inflate;
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private boolean a(SessionManager sessionManager, int i2) {
        return CCnsDeuda.getCodigo(sessionManager, i2).equals("AD");
    }

    private ArrayList<Map<String, String>> a(CnsDeudaPTBodyResponseBean cnsDeudaPTBodyResponseBean) {
        this.al = cnsDeudaPTBodyResponseBean;
        List tarjetas = cnsDeudaPTBodyResponseBean.getTarjetas().getTarjetas();
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("cnsDeuda 1.2 Número de tarjetas: ");
        sb.append(tarjetas.size());
        Log.d(str, sb.toString());
        for (int i2 = 0; i2 < tarjetas.size(); i2++) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Fecha de vencimiento ");
            sb2.append(((Tarjeta) tarjetas.get(i2)).getVencimiento());
            Log.d(str2, sb2.toString());
            String expireDateFormat = UtilDate.getExpireDateFormat(((Tarjeta) tarjetas.get(i2)).getVencimiento());
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Fecha de vencimiento (dd/mm/aa) ");
            sb3.append(expireDateFormat);
            Log.d(str3, sb3.toString());
            String str4 = TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Tipo Tarjeta ");
            sb4.append(((Tarjeta) tarjetas.get(i2)).getNombreTarjeta().toLowerCase());
            Log.d(str4, sb4.toString());
            String numTarjeta = ((Tarjeta) tarjetas.get(i2)).getNumTarjeta();
            String str5 = PagoTarjetasConstants.ARS_AND_USD_FORMATTED_AMOUNTS_ASTERISK;
            String label = ((ListGroupBean) ((ListTableBean) this.a.getConsDescripciones().getListTableBeans().get(2)).getListGroupBeans().get(0)).getLabel();
            String str6 = PagoTarjetasConstants.ARS_AND_USD_FORMATTED_AMOUNTS_ASTERISK;
            String label2 = ((ListGroupBean) ((ListTableBean) this.a.getConsDescripciones().getListTableBeans().get(2)).getListGroupBeans().get(1)).getLabel();
            String str7 = TAG;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Código Tarjeta ");
            sb5.append(((Tarjeta) tarjetas.get(i2)).getCodigo());
            Log.d(str7, sb5.toString());
            if (!a(this.a, i2)) {
                String str8 = TAG;
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Importe en pesos (###,###,###,##0.00) ");
                sb6.append(UtilCurrency.getFormattedAmountInArsFromString(((Tarjeta) tarjetas.get(i2)).getImporteP()));
                Log.d(str8, sb6.toString());
                String str9 = TAG;
                StringBuilder sb7 = new StringBuilder();
                sb7.append("Importe en dólares (###,###,###,##0.00) ");
                sb7.append(UtilCurrency.getFormattedAmountInArsFromString(((Tarjeta) tarjetas.get(i2)).getImporteD()));
                Log.d(str9, sb7.toString());
                str5 = UtilCurrency.getFormattedAmountInArsFromString(((Tarjeta) tarjetas.get(i2)).getImporteP());
                str6 = UtilCurrency.getFormattedAmountInArsFromString(((Tarjeta) tarjetas.get(i2)).getImporteD());
            }
            String str10 = str5;
            String str11 = str6;
            String upperCase = ((Tarjeta) tarjetas.get(i2)).getNombreTarjeta().toUpperCase();
            StringBuilder sb8 = new StringBuilder();
            sb8.append(label);
            sb8.append(str10);
            String sb9 = sb8.toString();
            StringBuilder sb10 = new StringBuilder();
            sb10.append(label2);
            sb10.append(str11);
            arrayList.add(a(expireDateFormat, upperCase, numTarjeta, sb9, sb10.toString()));
            CnsDeudaPTBodyResponseBean cnsDeuda = this.a.getCnsDeuda();
            ((Tarjeta) cnsDeuda.getTarjetas().getTarjetas().get(i2)).setVencimientoFormatted(expireDateFormat);
            ((Tarjeta) cnsDeuda.getTarjetas().getTarjetas().get(i2)).setNombreTarjeta(((Tarjeta) tarjetas.get(i2)).getNombreTarjeta().toUpperCase());
            ((Tarjeta) cnsDeuda.getTarjetas().getTarjetas().get(i2)).setNumTarjetaFormatted(numTarjeta);
            ((Tarjeta) cnsDeuda.getTarjetas().getTarjetas().get(i2)).setImportePFormatted(str10);
            ((Tarjeta) cnsDeuda.getTarjetas().getTarjetas().get(i2)).setImporteDFormatted(str11);
            this.a.setCnsDeuda(cnsDeuda);
        }
        return arrayList;
    }

    private HashMap<String, String> a(String str, String str2, String str3, String str4, String str5) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("credit_cards_list_exp_date", str);
        hashMap.put("credit_cards_list_credit_card", str2);
        hashMap.put("credit_cards_list_credit_card_detail", str3);
        hashMap.put("credit_cards_list_amount_in_pesos", str4);
        hashMap.put("credit_cards_list_amount_in_dollars", str5);
        return hashMap;
    }

    public void onItemClick1(AdapterView<?> adapterView, View view, int i2, long j) {
        G();
        int i3 = (int) j;
        this.f = new PagoTarjetasController(this.as, getFragmentManager(), this.a, i3);
        this.f.setPantallaPrepararPago(this.pantallaPrepararPago);
        this.f.setPantallaAyudaTarjeta(this.pantallaAyudaTarjeta);
        this.f.setPantallaDetalleTarjetaPagoTotalDolares(this.pantallaDetalleTarjetaPagoTotalDolares);
        this.f.setPantallaDetalleTarjetaPagoTotalPesos(this.pantallaDetalleTarjetaPagoTotalPesos);
        this.f.setPantallaConfirmarStopDebit(this.pantallaConfirmarStopDebit);
        this.f.setPantallaComprobanteStopDebit(this.pantallaComprobanteStopDebit);
        this.f.setPantallaConfirmarPago(this.pantallaConfirmarPago);
        this.f.setPantallaComprobantePago(this.pantallaComprobantePago);
        this.f.setPantallaAyudaTarjeta(this.pantallaAyudaTarjeta);
        goToPrepararPago(i3);
        this.am = i2 - 1;
        this.mCreditCardsList.setOnClickListener(null);
    }

    /* access modifiers changed from: private */
    public int y() {
        return this.ai;
    }

    private void c(int i2) {
        this.ai = i2;
    }

    public void goToPagoTarjetas() {
        this.aq = false;
        this.f.setSelectedOptionCurrency(null);
        this.f.initializeCurrencyCombo();
        if (this.mControlPager.getDisplayedChild() != 0) {
            gotoPage(0);
            this.as.setActionBarType(ActionBarType.MENU);
            this.as.lockMenu(false);
            enableMenuButton();
        }
    }

    public void goToPrepararPago(int i2) {
        nextPage();
        a((ScrollView) this.pantallaPrepararPago);
        this.as.setActionBarType(ActionBarType.BACK_ONLY);
        this.as.lockMenu(true);
        enableBackButton();
        this.g.onCreatePage(i2);
    }

    public void setPrepararPagoView(int i2) {
        c(i2);
        PagoTarjetasController pagoTarjetasController = this.f;
        StringBuilder sb = new StringBuilder();
        sb.append(CCnsDeuda.getNombreTarjeta(this.a, i2));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(CCnsDeuda.getNumeroTarjetaFormateado(this.a, i2));
        pagoTarjetasController.setPrepararPagoCreditCard(sb.toString());
        this.f.setPrepararPagoExpireDate();
        this.f.setPrepararPagoBalanceInPesos();
        this.f.setPrepararPagoBalanceInDolares();
        this.f.setPrepararPagoMinimumPayment();
        this.f.resetComboOptions();
        this.f.initializePrepararPagoAmountPayableHelpLinkAndButtons(this.c);
        this.f.setPrepararPagoDebitAccountComboOptions();
        this.f.setPrepararPagoDebitAccountInPesos();
        this.f.setPrepararPagoDebitAccountInDolares();
        this.f.initializeCurrencyCombo();
        this.f.initializePrepararPagoQuoteOfTheDay();
        this.f.initializePrepararPagoPaymentDateCombo();
        this.f.resetMontos();
        this.f.setPantallaAD();
        enableOnClickListeners();
    }

    public void goToAyudaTarjeta() {
        this.c.trackScreen(getString(R.string.analytics_screen_name_ayuda_tarjeta_adherida));
        Intent intent = new Intent(this.as, InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, this.f.getAyudaTarjetaMensaje());
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getString(R.string.ID228_CARDPAYMENT_MAIN_LBL_TITLE));
        startActivity(intent);
    }

    public void goToDetalleTarjetaPagoTotalDolares() {
        this.ao = true;
        gotoPage(7);
        this.as.setActionBarType(ActionBarType.BACK_ONLY);
        this.as.lockMenu(true);
        enableBackButton();
        this.i.onCreatePage();
    }

    public void setDetalleTarjetaPagoTotalDolaresView() {
        this.c.trackScreen(getString(R.string.analytics_screen_name_detalle_pago_total_pesos));
        this.f.setDetalleTarjetaPagoTotalDolaresBalanceInArs();
        this.f.setDetalleTarjetaPagoTotalDolaresConversionToUsd();
        this.f.setDetalleTarjetaPagoTotalDolaresQuoteOfTheDay();
        this.f.setDetalleTarjetaPagoTotalDolaresBalanceInUsd();
        this.f.setDetalleTarjetaPagoTotalDolaresTotalPaymentInUsd();
    }

    public void goToDetalleTarjetaPagoTotalPesos() {
        this.ao = true;
        gotoPage(6);
        this.as.setActionBarType(ActionBarType.BACK_ONLY);
        this.as.lockMenu(true);
        enableBackButton();
        this.ad.onCreatePage();
    }

    public void setDetalleTarjetaPagoTotalPesosView() {
        this.c.trackScreen(getString(R.string.analytics_screen_name_detalle_pago_total_dolares));
        this.f.setDetalleTarjetaPagoTotalPesosBalanceInUsd();
        this.f.setDetalleTarjetaPagoTotalPesosConversionToArs();
        this.f.setDetalleTarjetaPagoTotalPesosQuoteOfTheDay();
        this.f.setDetalleTarjetaPagoTotalPesosBalanceInArs();
        this.f.setDetalleTarjetaPagoTotalPesosTotalPaymentInArs();
    }

    public void goToConfirmarStopDebit() {
        this.ao = true;
        gotoPage(4);
        this.as.setActionBarType(ActionBarType.BACK_ONLY);
        this.as.lockMenu(true);
        enableBackButton();
        this.ae.onCreatePage();
    }

    public void setConfirmarStopDebitView() {
        this.c.trackScreen(getString(R.string.analytics_screen_name_confirmacion_stop_debit));
        this.f.setConfirmarStopDebitTarjeta();
        this.f.setConfirmarStopDebitIdentificacion();
        if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArs(this.a)).toString())) {
            this.f.setConfirmarStopDebitCuentaDebitoInArs(0);
        } else if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyUsd(this.a)).toString())) {
            this.f.setConfirmarStopDebitCuentaDebitoInArs(1);
        } else if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(this.a)).toString())) {
            this.f.setConfirmarStopDebitCuentaDebitoInArs(2);
        }
        ((DateView) this.pantallaConfirmarStopDebit.findViewById(R.id.fechaComprobanteConf)).setStringDate(z());
        this.pantallaConfirmarStopDebit.findViewById(R.id.confirm_stop_debit_form_button_confirm).setOnClickListener(this);
    }

    private String z() {
        return new DateTime((Object) Calendar.getInstance().getTime()).toString(getString(R.string.WS_FORMAT_SHORT_DATE));
    }

    public void goToComprobanteStopDebit(String str, String str2) {
        this.ao = true;
        gotoPage(5);
        this.as.setActionBarType(ActionBarType.MENU);
        this.as.lockMenu(false);
        enableMenuButton();
        this.af.onCreatePage(str, str2);
    }

    public void setComprobanteStopDebitView(String str, String str2) {
        this.c.trackScreen(getString(R.string.analytics_screen_name_comprobante_stop_debit));
        ActionBar supportActionBar = this.as.getSupportActionBar();
        View customView = supportActionBar != null ? supportActionBar.getCustomView() : null;
        if (customView != null) {
            customView.findViewById(R.id.share).setVisibility(0);
            customView.findViewById(R.id.share).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PagoTarjetasFragment.this.onClickShowActionShareReceipt(PagoTarjetasFragment.this.svComprobanteStopDebit);
                }
            });
            customView.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (PagoTarjetasFragment.this.isForgetShareReceipt()) {
                        PagoTarjetasFragment.this.rememberShareReceipt(PagoTarjetasFragment.this.svComprobanteStopDebit);
                    } else {
                        PagoTarjetasFragment.this.switchDrawer();
                    }
                }
            });
        }
        this.f.setComprobanteStopDebitTarjeta();
        this.f.setComprobanteStopDebitNumeroTarjeta();
        if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArs(this.a)).toString())) {
            this.f.setComprobanteStopDebitAccount(this.f.getPrepararPagoDebitAccountInPesos());
        } else if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyUsd(this.a)).toString())) {
            this.f.setComprobanteStopDebitAccount(this.f.getPrepararPagoDebitAccountInDolares());
        } else if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(this.a)).toString())) {
            this.f.setComprobanteStopDebitAccount(this.f.getPrepararPagoDebitAccountInPesos());
        }
        ((DateView) this.pantallaComprobanteStopDebit.findViewById(R.id.fechaComprobanteComp)).setStringDate(str2);
        this.f.setComprobanteStopDebitNumero(str);
        this.c.trackTransaction(getString(R.string.transaction_hit_product_name_stop_debit), str);
        this.pantallaComprobanteStopDebit.findViewById(R.id.proof_of_stop_debit_form_button_back).setOnClickListener(this);
    }

    public void goToConfirmarPago() {
        this.ao = true;
        gotoPage(2);
        if (this.as != null) {
            this.as.setActionBarType(ActionBarType.BACK_ONLY);
            this.as.lockMenu(true);
        }
        enableBackButton();
        this.ag.onCreatePage();
    }

    public void setConfirmarPagoView() {
        this.c.trackScreen(getString(R.string.analytics_screen_name_confirmacion_pago_tarjeta));
        this.f.setConfirmarPagoTarjeta();
        this.f.setConfirmarPagoImporte();
        if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArs(this.a)).toString())) {
            this.f.setConfirmarPagoCuentaDebitoInArs();
            this.f.setConfirmarPagoAmountInArs();
            this.f.showDebitAccountAndAmountInArs();
            this.f.hideDebitAccountAndAmountInUsd();
        } else if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyUsd(this.a)).toString())) {
            this.f.setConfirmarPagoCuentaDebitoInUsd();
            this.f.setConfirmarPagoAmountInUsd();
            this.f.showDebitAccountAndAmountInUsd();
            this.f.hideDebitAccountAndAmountInArs();
        } else if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(this.a)).toString())) {
            this.f.setConfirmarPagoCuentaDebitoInArs();
            this.f.setConfirmarPagoCuentaDebitoInUsd();
            this.f.setConfirmarPagoAmountInArs();
            this.f.setConfirmarPagoAmountInUsd();
            this.f.showDebitAccountAndAmountInArs();
            this.f.showDebitAccountAndAmountInUsd();
        }
        this.f.setConfirmarPagoQuoteOfTheDay();
        this.f.setConfirmarPagoPaymentDate();
        this.pantallaConfirmarPago.findViewById(R.id.confirm_payment_form_button_pay).setOnClickListener(this);
    }

    public void goToComprobantePago(String str) {
        this.ao = true;
        gotoPage(3);
        this.as.setActionBarType(ActionBarType.MENU);
        this.as.lockMenu(false);
        enableMenuButton();
        this.ah.onCreatePage(str);
    }

    public void setComprobantePagoView(String str) {
        this.c.trackScreen(getString(R.string.analytics_screen_name_comprobante_pago_tarjeta));
        ActionBar supportActionBar = this.as.getSupportActionBar();
        View customView = supportActionBar != null ? supportActionBar.getCustomView() : null;
        if (customView != null) {
            customView.findViewById(R.id.share).setVisibility(0);
            customView.findViewById(R.id.share).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PagoTarjetasFragment.this.onClickShowActionShareReceipt(PagoTarjetasFragment.this.svComprobantePagoTarjeta);
                }
            });
        }
        this.f.setComprobantePagoTarjeta();
        if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArs(this.a)).toString())) {
            this.f.setComprobantePagoCuentaDebitoInArs();
            this.f.setComprobantePagoAmountInArs();
            this.f.hideComprobantePagoDebitAccountAndAmountInUsd();
        } else if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyUsd(this.a)).toString())) {
            this.f.setComprobantePagoCuentaDebitoInUsd();
            this.f.setComprobantePagoAmountInUsd();
            this.f.hideComprobantePagoDebitAccountAndAmountInArs();
        } else if (this.f.getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(this.a)).toString())) {
            this.f.setComprobantePagoCuentaDebitoInArs();
            this.f.setComprobantePagoCuentaDebitoInUsd();
            this.f.setComprobantePagoAmountInArs();
            this.f.setComprobantePagoAmountInUsd();
            this.f.showComprobantePagoDebitAccountAndAmountInArsAndUsd();
        }
        this.f.setComprobantePagoQuoteOfTheDay();
        this.f.setComprobantePagoPaymentDate();
        this.f.setComprobantePagoNumero(str);
        this.c.trackTransaction(getString(R.string.transaction_hit_product_name_pago_tarjeta), str);
        this.pantallaComprobantePago.findViewById(R.id.proof_of_payment_form_button_back).setOnClickListener(this);
    }

    public void showProgress() {
        super.showProgress("");
    }

    public void dismissProgress() {
        super.dismissProgress();
    }

    public IDataManager getDataManager() {
        return this.b;
    }

    public void nextPage() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
            this.mControlPager.showNext();
        }
    }

    public void previousPage() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
            this.mControlPager.showPrevious();
        }
    }

    public void gotoPage(int i2) {
        if (this.mControlPager != null) {
            if (i2 == 8) {
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToUpAnimation());
            } else if (i2 == 1 && this.an) {
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromUpAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToDownAnimation());
            } else if (this.ao) {
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
            } else {
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
            }
            this.mControlPager.setDisplayedChild(i2);
        }
    }

    public int getIndexViewPage(View view) {
        return this.mControlPager.indexOfChild(view);
    }

    public void enableOnClickListeners() {
        if (this.f.showPrepararPagoHelpLink()) {
            this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_help).setOnClickListener(this);
        }
        if (this.f.showPrepararPagoContinueButton()) {
            this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_button_continue).setVisibility(0);
            this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_button_continue).setOnClickListener(this);
        }
        if (this.f.showPrepararPagoPagarButton()) {
            this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_button_pay).setVisibility(0);
            this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_button_pay).setOnClickListener(this);
        }
        if (this.f.showPrepararPagoStopDebitButton()) {
            this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_button_stop_debit).setVisibility(0);
            this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_button_stop_debit).setOnClickListener(this);
        }
        this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_payment_date_container).setOnClickListener(this);
        this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_amount_payable_container).setOnClickListener(this);
        this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_currency_container).setOnClickListener(this);
        this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_debit_account_in_pesos_container).setOnClickListener(this);
        this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_debit_account_in_dollars_container).setOnClickListener(this);
        this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_amount_in_pesos_see_detail).setOnClickListener(this);
        this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_amount_in_dollars_see_detail).setOnClickListener(this);
    }

    private void A() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("Seleccionar Importe a Pagar", null, this.f.getAmountPayableComboOptions(), PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, this.f.getSelectedOptionAmountPayable());
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                PagoTarjetasFragment.this.f.setPrepararPagoChosenAmountPayable(Html.fromHtml(str).toString());
                PagoTarjetasFragment.this.f.prepararPagoAmountPayableAndCurrencyBehaviour();
                PagoTarjetasFragment.this.pantallaPrepararPago.findViewById(R.id.prepare_payment_form_currency_container).setOnClickListener(PagoTarjetasFragment.this);
            }
        });
        newInstance.show(getFragmentManager(), "Dialog");
    }

    private void B() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("Seleccionar Moneda", null, this.f.getCurrencyComboOptions(), PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, this.f.getSelectedOptionCurrency());
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                PagoTarjetasFragment.this.f.setPrepararPagoChosenCurrency(Html.fromHtml(str).toString());
                PagoTarjetasFragment.this.f.prepararPagoAmountPayableAndCurrencyBehaviour();
                PagoTarjetasFragment.this.f.initializePrepararPagoQuoteOfTheDay();
            }
        });
        newInstance.show(getFragmentManager(), "Dialog");
    }

    private void C() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("Seleccionar Cuenta Débito $", null, this.f.getArsDebitAccountComboOptions(), PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, this.f.getSelectedOptionDebitAccountInPesos());
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                PagoTarjetasFragment.this.f.setSelectedOptionDebitAccountInPesos(str);
                PagoTarjetasFragment.this.f.setPrepararPagoDebitAccountInPesos();
            }
        });
        newInstance.show(getFragmentManager(), "Dialog");
    }

    private void D() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("Seleccionar Cuenta Débito U$S", null, this.f.getUsdDebitAccountComboOptions(), PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, this.f.getSelectedOptionDebitAccountInDollars());
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                PagoTarjetasFragment.this.f.setSelectedOptionDebitAccountInDollars(str);
                PagoTarjetasFragment.this.f.setPrepararPagoDebitAccountInDolares();
            }
        });
        newInstance.show(getFragmentManager(), "Dialog");
    }

    private void E() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("Seleccionar Fecha de Pago", null, this.f.getPaymentDateComboOptions(), PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, this.f.getSelectedOptionPaymentDate());
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                PagoTarjetasFragment.this.f.setSelectedOptionPaymentDate(str);
                if (PagoTarjetasFragment.this.f.getSelectedOptionPaymentDate().equals(CCnsDeuda.getPaymentDateToday(PagoTarjetasFragment.this.a))) {
                    PagoTarjetasFragment.this.f.setPrepararPagoChosenPaymentDate(CCnsDeuda.getCurrentDate(PagoTarjetasFragment.this.a), false);
                } else if (PagoTarjetasFragment.this.f.getSelectedOptionPaymentDate().equals(CCnsDeuda.getPaymentDateVencimiento(PagoTarjetasFragment.this.a))) {
                    PagoTarjetasFragment.this.f.setPrepararPagoChosenPaymentDate(CCnsDeuda.getExpireDateFormatted(PagoTarjetasFragment.this.a, PagoTarjetasFragment.this.y()), true);
                }
            }
        });
        newInstance.show(getFragmentManager(), "Dialog");
    }

    private void F() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, getResources().getString(R.string.MSG_USER000039_PagoTarj_errorImporte), null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }
        });
        newInstance.show(getFragmentManager(), "DialogNewVersion");
    }

    /* JADX WARNING: Removed duplicated region for block: B:106:0x0762 A[Catch:{ Exception -> 0x092f }] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0771 A[Catch:{ Exception -> 0x092f }] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0799 A[Catch:{ Exception -> 0x092f }] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x089c A[Catch:{ Exception -> 0x092f }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r9) {
        /*
            r8 = this;
            int r9 = r9.getId()
            r0 = 0
            switch(r9) {
                case 2131364374: goto L_0x020b;
                case 2131364394: goto L_0x0107;
                case 2131365348: goto L_0x0102;
                case 2131365357: goto L_0x00fd;
                case 2131365360: goto L_0x00f8;
                case 2131365366: goto L_0x00e1;
                case 2131365367: goto L_0x007f;
                case 2131365368: goto L_0x0035;
                case 2131365373: goto L_0x0030;
                case 2131365376: goto L_0x002b;
                case 2131365380: goto L_0x0026;
                case 2131365385: goto L_0x0021;
                case 2131365389: goto L_0x001c;
                case 2131365415: goto L_0x0933;
                case 2131365438: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x0942
        L_0x000a:
            boolean r9 = r8.isForgetShareReceipt()
            if (r9 == 0) goto L_0x0017
            android.widget.ScrollView r9 = r8.svComprobanteStopDebit
            r8.onClickShowActionShareReceipt(r9)
            goto L_0x0942
        L_0x0017:
            r8.goToPagoTarjetas()
            goto L_0x0942
        L_0x001c:
            r8.E()
            goto L_0x0942
        L_0x0021:
            r8.goToAyudaTarjeta()
            goto L_0x0942
        L_0x0026:
            r8.C()
            goto L_0x0942
        L_0x002b:
            r8.D()
            goto L_0x0942
        L_0x0030:
            r8.B()
            goto L_0x0942
        L_0x0035:
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r9 = r8.as
            r9.hideKeyboard()
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f
            r0 = 1
            boolean r9 = r9.isManualAmountOk(r0)
            if (r9 == 0) goto L_0x007a
            r9 = 2131755896(0x7f100378, float:1.9142684E38)
            java.lang.String r0 = r8.getString(r9)
            ar.com.santander.rio.mbanking.managers.session.SessionManager r9 = r8.a
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getStopMensajeMes(r9)
            r2 = 0
            r3 = 0
            java.lang.String r4 = "Aceptar"
            java.lang.String r5 = "Cancelar"
            r6 = 0
            ar.com.santander.rio.mbanking.components.IsbanDialogFragment r9 = ar.com.santander.rio.mbanking.components.IsbanDialogFragment.newInstance(r0, r1, r2, r3, r4, r5, r6)
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment$10 r0 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment$10
            r0.<init>()
            r9.setDialogListener(r0)
            android.support.v4.app.FragmentManager r0 = r8.getFragmentManager()
            java.lang.String r1 = "confirmStopDebitDialog"
            r9.show(r0, r1)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r9 = r8.c
            r0 = 2131757799(0x7f100ae7, float:1.9146544E38)
            java.lang.String r0 = r8.getString(r0)
            r9.trackScreen(r0)
            goto L_0x0942
        L_0x007a:
            r8.F()
            goto L_0x0942
        L_0x007f:
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r9 = r8.as
            r9.hideKeyboard()
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f
            boolean r9 = r9.isManualAmountOk(r0)
            if (r9 == 0) goto L_0x00dc
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f
            boolean r9 = r9.showPrepararPagoStopDebitButton()
            if (r9 == 0) goto L_0x00d7
            r9 = 2131755895(0x7f100377, float:1.9142682E38)
            java.lang.String r0 = r8.getString(r9)
            ar.com.santander.rio.mbanking.managers.session.SessionManager r9 = r8.a
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getStopDebitRecordatorio(r9)
            r2 = 0
            r3 = 0
            java.lang.String r4 = "Continuar"
            java.lang.String r5 = "Cancelar"
            r6 = 0
            ar.com.santander.rio.mbanking.components.IsbanDialogFragment r9 = ar.com.santander.rio.mbanking.components.IsbanDialogFragment.newInstance(r0, r1, r2, r3, r4, r5, r6)
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment$11 r0 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment$11
            r0.<init>()
            r9.setDialogListener(r0)
            android.support.v4.app.FragmentManager r0 = r8.getFragmentManager()
            java.lang.String r1 = "continueWithoutStopDebitDialog"
            r9.show(r0, r1)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r9 = r8.c
            r0 = 2131757798(0x7f100ae6, float:1.9146542E38)
            java.lang.String r0 = r8.getString(r0)
            r9.trackScreen(r0)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r9 = r8.c
            r0 = 2131757797(0x7f100ae5, float:1.914654E38)
            java.lang.String r0 = r8.getString(r0)
            r9.trackScreen(r0)
            goto L_0x0942
        L_0x00d7:
            r8.goToConfirmarPago()
            goto L_0x0942
        L_0x00dc:
            r8.F()
            goto L_0x0942
        L_0x00e1:
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r9 = r8.as
            r9.hideKeyboard()
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f
            boolean r9 = r9.isManualAmountOk(r0)
            if (r9 == 0) goto L_0x00f3
            r8.goToConfirmarPago()
            goto L_0x0942
        L_0x00f3:
            r8.F()
            goto L_0x0942
        L_0x00f8:
            r8.A()
            goto L_0x0942
        L_0x00fd:
            r8.goToDetalleTarjetaPagoTotalPesos()
            goto L_0x0942
        L_0x0102:
            r8.goToDetalleTarjetaPagoTotalDolares()
            goto L_0x0942
        L_0x0107:
            java.util.ArrayList<java.lang.String> r9 = r8.ak
            r9.clear()
            java.lang.String r9 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "tipo cuenta debito "
            r0.append(r1)
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r8.a
            int r2 = r8.y()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getTipoCuentaDebito(r1, r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r9, r0)
            java.util.ArrayList<java.lang.String> r9 = r8.ak
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r8.a
            int r1 = r8.y()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getTipoCuentaDebito(r0, r1)
            r9.add(r0)
            java.lang.String r9 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sucursal cuenta debito "
            r0.append(r1)
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r8.a
            int r2 = r8.y()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getSucursalCuentaDebito(r1, r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r9, r0)
            java.util.ArrayList<java.lang.String> r9 = r8.ak
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r8.a
            int r1 = r8.y()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getSucursalCuentaDebito(r0, r1)
            r9.add(r0)
            java.lang.String r9 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "numero cuenta debito "
            r0.append(r1)
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r8.a
            int r2 = r8.y()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getNumeroCuentaDebito(r1, r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r9, r0)
            java.util.ArrayList<java.lang.String> r9 = r8.ak
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r8.a
            int r1 = r8.y()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getNumeroCuentaDebito(r0, r1)
            r9.add(r0)
            java.lang.String r9 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "codigo "
            r0.append(r1)
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r8.a
            int r2 = r8.y()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCodigo(r1, r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r9, r0)
            java.util.ArrayList<java.lang.String> r9 = r8.ak
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r8.a
            int r1 = r8.y()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCodigo(r0, r1)
            r9.add(r0)
            java.lang.String r9 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "numero cuenta producto "
            r0.append(r1)
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r8.a
            int r2 = r8.y()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getNumeroCuentaProducto(r1, r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r9, r0)
            java.util.ArrayList<java.lang.String> r9 = r8.ak
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r8.a
            int r1 = r8.y()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getNumeroCuentaProducto(r0, r1)
            r9.add(r0)
            java.lang.String r9 = "stopDebit"
            ar.com.santander.rio.mbanking.components.ProgresIndicator r9 = ar.com.santander.rio.mbanking.components.ProgresIndicator.newInstance(r9)
            r8.d = r9
            r8.showProgress()
            ar.com.santander.rio.mbanking.app.module.payments.creditCards.ConfirmarStopDebitPresenter r9 = r8.ae
            java.util.ArrayList<java.lang.String> r0 = r8.ak
            r9.sendRequestStopDebit(r0)
            goto L_0x0942
        L_0x020b:
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ Exception -> 0x092f }
            r9.<init>()     // Catch:{ Exception -> 0x092f }
            r8.aj = r9     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = ""
            java.lang.String r1 = ""
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r3 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyArs(r3)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r3 = android.text.Html.fromHtml(r3)     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x092f }
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x092f }
            r3 = 2131365381(0x7f0a0e05, float:1.8350626E38)
            if (r2 == 0) goto L_0x025b
            android.view.View r9 = r8.pantallaPrepararPago     // Catch:{ Exception -> 0x092f }
            android.view.View r9 = r9.findViewById(r3)     // Catch:{ Exception -> 0x092f }
            android.widget.TextView r9 = (android.widget.TextView) r9     // Catch:{ Exception -> 0x092f }
            java.lang.CharSequence r9 = r9.getText()     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r3.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r4 = "PAYMENT IN ARS. CHOSEN ACCOUNT: "
            r3.append(r4)     // Catch:{ Exception -> 0x092f }
            r3.append(r9)     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r3)     // Catch:{ Exception -> 0x092f }
            goto L_0x02e0
        L_0x025b:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r4 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyUsd(r4)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r4 = android.text.Html.fromHtml(r4)     // Catch:{ Exception -> 0x092f }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x092f }
            boolean r2 = r2.equals(r4)     // Catch:{ Exception -> 0x092f }
            r4 = 2131365377(0x7f0a0e01, float:1.8350618E38)
            if (r2 == 0) goto L_0x029f
            android.view.View r9 = r8.pantallaPrepararPago     // Catch:{ Exception -> 0x092f }
            android.view.View r9 = r9.findViewById(r4)     // Catch:{ Exception -> 0x092f }
            android.widget.TextView r9 = (android.widget.TextView) r9     // Catch:{ Exception -> 0x092f }
            java.lang.CharSequence r9 = r9.getText()     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r3.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r4 = "PAYMENT IN USD. CHOSEN ACCOUNT: "
            r3.append(r4)     // Catch:{ Exception -> 0x092f }
            r3.append(r9)     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r3)     // Catch:{ Exception -> 0x092f }
            goto L_0x02e0
        L_0x029f:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r5 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyArsUsd(r5)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r5 = android.text.Html.fromHtml(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x092f }
            boolean r2 = r2.equals(r5)     // Catch:{ Exception -> 0x092f }
            if (r2 == 0) goto L_0x02e0
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r1 = "BIMONETARY PAYMENT"
            android.util.Log.d(r9, r1)     // Catch:{ Exception -> 0x092f }
            android.view.View r9 = r8.pantallaPrepararPago     // Catch:{ Exception -> 0x092f }
            android.view.View r9 = r9.findViewById(r3)     // Catch:{ Exception -> 0x092f }
            android.widget.TextView r9 = (android.widget.TextView) r9     // Catch:{ Exception -> 0x092f }
            java.lang.CharSequence r9 = r9.getText()     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x092f }
            android.view.View r1 = r8.pantallaPrepararPago     // Catch:{ Exception -> 0x092f }
            android.view.View r1 = r1.findViewById(r4)     // Catch:{ Exception -> 0x092f }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x092f }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x092f }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x092f }
        L_0x02e0:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.CCnsDeuda r2 = r2.getcCnsDeuda()     // Catch:{ Exception -> 0x092f }
            java.util.Map<java.lang.String, ar.com.santander.rio.mbanking.services.model.general.Cuenta> r2 = r2.mapCuenta     // Catch:{ Exception -> 0x092f }
            java.lang.Object r9 = r2.get(r9)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.services.model.general.Cuenta r9 = (ar.com.santander.rio.mbanking.services.model.general.Cuenta) r9     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.CCnsDeuda r2 = r2.getcCnsDeuda()     // Catch:{ Exception -> 0x092f }
            java.util.Map<java.lang.String, ar.com.santander.rio.mbanking.services.model.general.Cuenta> r2 = r2.mapCuenta     // Catch:{ Exception -> 0x092f }
            java.lang.Object r1 = r2.get(r1)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.services.model.general.Cuenta r1 = (ar.com.santander.rio.mbanking.services.model.general.Cuenta) r1     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r3.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r4 = "--> sucursalCuentaD inicial: "
            r3.append(r4)     // Catch:{ Exception -> 0x092f }
            java.lang.String r4 = r9.getSucursal()     // Catch:{ Exception -> 0x092f }
            r3.append(r4)     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r3)     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r9.getSucursal()     // Catch:{ Exception -> 0x092f }
            r2.<init>(r3)     // Catch:{ Exception -> 0x092f }
        L_0x031f:
            int r3 = r2.length()     // Catch:{ Exception -> 0x092f }
            r4 = 3
            if (r3 <= r4) goto L_0x032b
            java.lang.StringBuilder r2 = r2.deleteCharAt(r0)     // Catch:{ Exception -> 0x092f }
            goto L_0x031f
        L_0x032b:
            java.lang.String r3 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r5.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = "--> sucursalCuentaD final: "
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = r2.toString()     // Catch:{ Exception -> 0x092f }
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r3, r5)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r3 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            r3.add(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r3.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "--> tipoCuentaDebito: "
            r3.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r9.getTipo()     // Catch:{ Exception -> 0x092f }
            r3.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r3)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r9.getTipo()     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = "02"
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x092f }
            if (r2 == 0) goto L_0x03d3
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r3 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyArs(r3)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r3 = android.text.Html.fromHtml(r3)     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x092f }
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x092f }
            if (r2 != 0) goto L_0x03cb
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r3 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyArsUsd(r3)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r3 = android.text.Html.fromHtml(r3)     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x092f }
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x092f }
            if (r2 == 0) goto L_0x03a9
            goto L_0x03cb
        L_0x03a9:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r3 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyUsd(r3)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r3 = android.text.Html.fromHtml(r3)     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x092f }
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x092f }
            if (r2 == 0) goto L_0x03dc
            java.util.ArrayList<java.lang.String> r2 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = "03"
            r2.add(r3)     // Catch:{ Exception -> 0x092f }
            goto L_0x03dc
        L_0x03cb:
            java.util.ArrayList<java.lang.String> r2 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = "00"
            r2.add(r3)     // Catch:{ Exception -> 0x092f }
            goto L_0x03dc
        L_0x03d3:
            java.util.ArrayList<java.lang.String> r2 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r9.getTipo()     // Catch:{ Exception -> 0x092f }
            r2.add(r3)     // Catch:{ Exception -> 0x092f }
        L_0x03dc:
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r3.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "--> numCuentaDebito inicial: "
            r3.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r9.getNumero()     // Catch:{ Exception -> 0x092f }
            r3.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r3)     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getNumero()     // Catch:{ Exception -> 0x092f }
            r2.<init>(r9)     // Catch:{ Exception -> 0x092f }
        L_0x03ff:
            int r9 = r2.length()     // Catch:{ Exception -> 0x092f }
            r3 = 7
            if (r9 <= r3) goto L_0x040b
            java.lang.StringBuilder r2 = r2.deleteCharAt(r0)     // Catch:{ Exception -> 0x092f }
            goto L_0x03ff
        L_0x040b:
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r5.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = "--> numCuentaDebito final: "
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = r2.toString()     // Catch:{ Exception -> 0x092f }
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r5)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ar.com.santander.rio.mbanking.utils.UtilAccount.formatNumeroCuentaDebito(r2)     // Catch:{ Exception -> 0x092f }
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r2.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "Chosen Credit Card Index: "
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            int r5 = r8.y()     // Catch:{ Exception -> 0x092f }
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaPTBodyResponseBean r9 = r8.al     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.services.model.general.Tarjetas r9 = r9.getTarjetas()     // Catch:{ Exception -> 0x092f }
            java.util.List r9 = r9.getTarjetas()     // Catch:{ Exception -> 0x092f }
            int r2 = r8.y()     // Catch:{ Exception -> 0x092f }
            java.lang.Object r9 = r9.get(r2)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.services.model.general.Tarjeta r9 = (ar.com.santander.rio.mbanking.services.model.general.Tarjeta) r9     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r5.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = "--> tipoTarjetaCredito: "
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = r9.getTipoTarjeta()     // Catch:{ Exception -> 0x092f }
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r5)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r2 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r9.getTipoTarjeta()     // Catch:{ Exception -> 0x092f }
            r2.add(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r5.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = "--> sucursalCuentaT inicial: "
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = r9.getSucursalCuentaDebito()     // Catch:{ Exception -> 0x092f }
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r5)     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r2.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "0"
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r9.getSucursalCuentaDebito()     // Catch:{ Exception -> 0x092f }
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r6.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r7 = "--> sucursalCuentaT final: "
            r6.append(r7)     // Catch:{ Exception -> 0x092f }
            r6.append(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r5, r6)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r5 = r8.aj     // Catch:{ Exception -> 0x092f }
            r5.add(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r5.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = "--> numTarjetaCredito: "
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = r9.getNumTarjeta()     // Catch:{ Exception -> 0x092f }
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r5)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r2 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r9.getNumTarjeta()     // Catch:{ Exception -> 0x092f }
            r2.add(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r5.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = "--> codigoTitularidad: "
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = r9.getCodigo()     // Catch:{ Exception -> 0x092f }
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r9.getCodigo()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "TI"
            boolean r2 = r2.equals(r5)     // Catch:{ Exception -> 0x092f }
            if (r2 == 0) goto L_0x051e
            java.util.ArrayList<java.lang.String> r2 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "T"
            r2.add(r5)     // Catch:{ Exception -> 0x092f }
            goto L_0x0531
        L_0x051e:
            java.lang.String r2 = r9.getCodigo()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "AD"
            boolean r2 = r2.equals(r5)     // Catch:{ Exception -> 0x092f }
            if (r2 == 0) goto L_0x0531
            java.util.ArrayList<java.lang.String> r2 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "A"
            r2.add(r5)     // Catch:{ Exception -> 0x092f }
        L_0x0531:
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r5.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = "--> numCuentaT inicial: "
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = r9.getNumCuentaProduc()     // Catch:{ Exception -> 0x092f }
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r5)     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getNumCuentaProduc()     // Catch:{ Exception -> 0x092f }
            r2.<init>(r9)     // Catch:{ Exception -> 0x092f }
        L_0x0554:
            int r9 = r2.length()     // Catch:{ Exception -> 0x092f }
            r5 = 10
            if (r9 <= r5) goto L_0x0561
            java.lang.StringBuilder r2 = r2.deleteCharAt(r0)     // Catch:{ Exception -> 0x092f }
            goto L_0x0554
        L_0x0561:
            java.lang.String r9 = r2.toString()     // Catch:{ Exception -> 0x092f }
        L_0x0565:
            int r2 = r9.length()     // Catch:{ Exception -> 0x092f }
            if (r2 >= r5) goto L_0x057d
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r2.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = "0"
            r2.append(r6)     // Catch:{ Exception -> 0x092f }
            r2.append(r9)     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r2.toString()     // Catch:{ Exception -> 0x092f }
            goto L_0x0565
        L_0x057d:
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r5.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r6 = "--> numCuentaT final: "
            r5.append(r6)     // Catch:{ Exception -> 0x092f }
            r5.append(r9)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r5)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r2 = r8.aj     // Catch:{ Exception -> 0x092f }
            r2.add(r9)     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> modoPagoTC: 2"
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "2"
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.isPrepararPagoChosenAmountPayableMinimum()     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x05bd
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> tipoPagoTC: 00"
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "00"
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            goto L_0x05ea
        L_0x05bd:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.isPrepararPagoChosenAmountPayableTotal()     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x05d4
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> tipoPagoTC: 01"
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "01"
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            goto L_0x05ea
        L_0x05d4:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.isPrepararPagoChosenAmountPayableOther()     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x05ea
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> tipoPagoTC: 02"
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "02"
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
        L_0x05ea:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getSelectedOptionPaymentDate()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getPaymentDateToday(r2)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.equals(r2)     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x0613
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> momentoPagoTC: 0"
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "0"
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            goto L_0x063b
        L_0x0613:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getSelectedOptionPaymentDate()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getPaymentDateVencimiento(r2)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.equals(r2)     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x063b
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> momentoPagoTC: 1"
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "1"
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
        L_0x063b:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyArs(r2)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.equals(r2)     // Catch:{ Exception -> 0x092f }
            if (r9 != 0) goto L_0x06e3
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyArsUsd(r2)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.equals(r2)     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x0670
            goto L_0x06e3
        L_0x0670:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyUsd(r2)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.equals(r2)     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x073a
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.isPrepararPagoChosenAmountPayableTotal()     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x06bb
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r2.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "--> importePagoTC: "
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r5 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.getPrepararPagoAmountInDolares()     // Catch:{ Exception -> 0x092f }
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getPrepararPagoAmountInDolares()     // Catch:{ Exception -> 0x092f }
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            goto L_0x073a
        L_0x06bb:
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r2.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "--> importePagoTC: "
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r5 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.getPrepararPagoManualAmountInUsd()     // Catch:{ Exception -> 0x092f }
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getPrepararPagoManualAmountInUsd()     // Catch:{ Exception -> 0x092f }
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            goto L_0x073a
        L_0x06e3:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.isPrepararPagoChosenAmountPayableOther()     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x0713
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r2.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "--> importePagoTC: "
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r5 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.getPrepararPagoManualAmountInArs()     // Catch:{ Exception -> 0x092f }
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getPrepararPagoManualAmountInArs()     // Catch:{ Exception -> 0x092f }
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            goto L_0x073a
        L_0x0713:
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r2.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "--> importePagoTC: "
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r5 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r5.getPrepararPagoAmountInPesos()     // Catch:{ Exception -> 0x092f }
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r2 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.getPrepararPagoAmountInPesos()     // Catch:{ Exception -> 0x092f }
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
        L_0x073a:
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> codigoMoneda: "
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ""
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyArsUsd(r2)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.equals(r2)     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x0771
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> cuentaBDolares (Bimonetary): 03"
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "03"
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
            goto L_0x077f
        L_0x0771:
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> cuentaBDolares (ARS/USD): "
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ""
            r9.add(r2)     // Catch:{ Exception -> 0x092f }
        L_0x077f:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyArsUsd(r2)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.equals(r2)     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x089c
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r2.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "--> sucursalBDolares inicial: "
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r1.getSucursal()     // Catch:{ Exception -> 0x092f }
            r2.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r1.getSucursal()     // Catch:{ Exception -> 0x092f }
            r9.<init>(r2)     // Catch:{ Exception -> 0x092f }
        L_0x07bc:
            int r2 = r9.length()     // Catch:{ Exception -> 0x092f }
            if (r2 <= r4) goto L_0x07c7
            java.lang.StringBuilder r9 = r9.deleteCharAt(r0)     // Catch:{ Exception -> 0x092f }
            goto L_0x07bc
        L_0x07c7:
            java.lang.String r2 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r4.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = "--> sucursalBDolares final: "
            r4.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r5 = r9.toString()     // Catch:{ Exception -> 0x092f }
            r4.append(r5)     // Catch:{ Exception -> 0x092f }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r2, r4)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r2 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x092f }
            r2.add(r9)     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r2.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r4 = "--> numCuentaBDolares inicial: "
            r2.append(r4)     // Catch:{ Exception -> 0x092f }
            java.lang.String r4 = r1.getNumero()     // Catch:{ Exception -> 0x092f }
            r2.append(r4)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r2)     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            java.lang.String r1 = r1.getNumero()     // Catch:{ Exception -> 0x092f }
            r9.<init>(r1)     // Catch:{ Exception -> 0x092f }
        L_0x080d:
            int r1 = r9.length()     // Catch:{ Exception -> 0x092f }
            if (r1 <= r3) goto L_0x0818
            java.lang.StringBuilder r9 = r9.deleteCharAt(r0)     // Catch:{ Exception -> 0x092f }
            goto L_0x080d
        L_0x0818:
            java.lang.String r0 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r1.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = "--> numCuentaBDolares final: "
            r1.append(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r2 = r9.toString()     // Catch:{ Exception -> 0x092f }
            r1.append(r2)     // Catch:{ Exception -> 0x092f }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r0, r1)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r0 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x092f }
            r0.add(r9)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.isPrepararPagoChosenAmountPayableTotal()     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x086c
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r0.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r1 = "--> importeDolares: "
            r0.append(r1)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r1 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r1 = r1.getPrepararPagoAmountInDolares()     // Catch:{ Exception -> 0x092f }
            r0.append(r1)     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r0)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r0 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = r0.getPrepararPagoAmountInDolares()     // Catch:{ Exception -> 0x092f }
            r9.add(r0)     // Catch:{ Exception -> 0x092f }
            goto L_0x08fa
        L_0x086c:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.isPrepararPagoChosenAmountPayableOther()     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x08fa
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x092f }
            r0.<init>()     // Catch:{ Exception -> 0x092f }
            java.lang.String r1 = "--> importeDolares: "
            r0.append(r1)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r1 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r1 = r1.getPrepararPagoManualAmountInUsd()     // Catch:{ Exception -> 0x092f }
            r0.append(r1)     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x092f }
            android.util.Log.d(r9, r0)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r0 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = r0.getPrepararPagoManualAmountInUsd()     // Catch:{ Exception -> 0x092f }
            r9.add(r0)     // Catch:{ Exception -> 0x092f }
            goto L_0x08fa
        L_0x089c:
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyArs(r0)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.equals(r0)     // Catch:{ Exception -> 0x092f }
            if (r9 != 0) goto L_0x08d0
            ar.com.santander.rio.mbanking.app.commons.PagoTarjetasController r9 = r8.f     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = r9.getSelectedOptionCurrency()     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r8.a     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.CCnsDeuda.getCurrencyUsd(r0)     // Catch:{ Exception -> 0x092f }
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x092f }
            boolean r9 = r9.equals(r0)     // Catch:{ Exception -> 0x092f }
            if (r9 == 0) goto L_0x08fa
        L_0x08d0:
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = "--> sucursalBDolares: 000"
            android.util.Log.d(r9, r0)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = "000"
            r9.add(r0)     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = "--> numCuentaBDolares: 0000000"
            android.util.Log.d(r9, r0)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = "0000000"
            r9.add(r0)     // Catch:{ Exception -> 0x092f }
            java.lang.String r9 = TAG     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = "--> importeDolares: 000000000000000"
            android.util.Log.d(r9, r0)     // Catch:{ Exception -> 0x092f }
            java.util.ArrayList<java.lang.String> r9 = r8.aj     // Catch:{ Exception -> 0x092f }
            java.lang.String r0 = "000000000000000"
            r9.add(r0)     // Catch:{ Exception -> 0x092f }
        L_0x08fa:
            r9 = 2131757164(0x7f10086c, float:1.9145256E38)
            java.lang.String r0 = r8.getString(r9)     // Catch:{ Exception -> 0x092f }
            r9 = 2131757161(0x7f100869, float:1.914525E38)
            java.lang.String r1 = r8.getString(r9)     // Catch:{ Exception -> 0x092f }
            r2 = 0
            r3 = 0
            r9 = 2131756512(0x7f1005e0, float:1.9143934E38)
            java.lang.String r4 = r8.getString(r9)     // Catch:{ Exception -> 0x092f }
            r9 = 2131756510(0x7f1005de, float:1.914393E38)
            java.lang.String r5 = r8.getString(r9)     // Catch:{ Exception -> 0x092f }
            r6 = 0
            ar.com.santander.rio.mbanking.components.IsbanDialogFragment r9 = ar.com.santander.rio.mbanking.components.IsbanDialogFragment.newInstance(r0, r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x092f }
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment$12 r0 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment$12     // Catch:{ Exception -> 0x092f }
            r0.<init>()     // Catch:{ Exception -> 0x092f }
            r9.setDialogListener(r0)     // Catch:{ Exception -> 0x092f }
            android.support.v4.app.FragmentManager r0 = r8.getFragmentManager()     // Catch:{ Exception -> 0x092f }
            java.lang.String r1 = "DialogConfirm"
            r9.show(r0, r1)     // Catch:{ Exception -> 0x092f }
            goto L_0x0942
        L_0x092f:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0933:
            boolean r9 = r8.isForgetShareReceipt()
            if (r9 == 0) goto L_0x093f
            android.widget.ScrollView r9 = r8.svComprobantePagoTarjeta
            r8.onClickShowActionShareReceipt(r9)
            goto L_0x0942
        L_0x093f:
            r8.goToPagoTarjetas()
        L_0x0942:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment.onClick(android.view.View):void");
    }

    private void a(final ScrollView scrollView) {
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(0, 0);
                }
            });
        }
    }

    public void enableBackButton() {
        ActionBar supportActionBar = this.as.getSupportActionBar();
        View customView = supportActionBar != null ? supportActionBar.getCustomView() : null;
        if (customView != null) {
            View findViewById = customView.findViewById(R.id.back_imgButton);
            if (findViewById != null) {
                findViewById.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        PagoTarjetasFragment.this.onBackPressed();
                    }
                });
            }
        }
    }

    public void enableMenuButton() {
        ActionBar supportActionBar = this.as.getSupportActionBar();
        View customView = supportActionBar != null ? supportActionBar.getCustomView() : null;
        if (customView != null) {
            ((ImageView) customView.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (PagoTarjetasFragment.this.isForgetShareReceipt()) {
                        PagoTarjetasFragment.this.rememberShareReceipt(PagoTarjetasFragment.this.svComprobanteStopDebit);
                    } else {
                        PagoTarjetasFragment.this.switchDrawer();
                    }
                }
            });
        }
    }

    @Subscribe
    public void onCnsDeuda(CnsDeudaEvent cnsDeudaEvent) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onCnsDeuda ");
        sb.append(cnsDeudaEvent.getResult());
        Log.d(str, sb.toString());
        dismissProgress();
        final CnsDeudaEvent cnsDeudaEvent2 = cnsDeudaEvent;
        AnonymousClass16 r2 = new BaseWSResponseHandler(this.as, TypeOption.INITIAL_VIEW, FragmentConstants.PAGO_TARJETAS, this, this.as) {
            /* access modifiers changed from: protected */
            public void onOk() {
                PagoTarjetasFragment.this.onCnsDeudaResponseOk(cnsDeudaEvent2);
            }
        };
        r2.handleWSResponse(cnsDeudaEvent);
    }

    public void onCnsDeudaResponseOk(CnsDeudaEvent cnsDeudaEvent) {
        try {
            CnsDeudaPTResponseBean cnsDeudaPTResponseBean = (CnsDeudaPTResponseBean) cnsDeudaEvent.getBeanResponse();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("response getHoraBancaria() ");
            sb.append(cnsDeudaPTResponseBean.getCnsDeudaPTBodyResponseBean().getHoraBancaria());
            Log.d(str, sb.toString());
            this.e = new PagoTarjetasAdapter(this.as, a(cnsDeudaPTResponseBean.getCnsDeudaPTBodyResponseBean()));
            if (cnsDeudaPTResponseBean.getCnsDeudaPTBodyResponseBean().getHoraBancaria().equals("0")) {
                Log.d(TAG, "Dentro del horario bancario");
                if (this.mCreditCardsList != null) {
                    this.mCreditCardsList.setAdapter(this.e);
                    this.mCreditCardsList.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                            PagoTarjetasFragment.this.onItemClick2(adapterView, view, i, j);
                        }
                    });
                }
            } else if (cnsDeudaPTResponseBean.getCnsDeudaPTBodyResponseBean().getHoraBancaria().equals("1")) {
                Log.d(TAG, "Fuera del horario bancario");
                if (this.mCreditCardsList != null) {
                    this.mCreditCardsList.setAdapter(this.e);
                    this.mCreditCardsList.setOnItemClickListener(null);
                }
                customErrorDialog(PagoTarjetasConstants.ISBAN_DIALOG_WARNING_TITLE, cnsDeudaPTResponseBean.getCnsDeudaPTBodyResponseBean().getMensajeHBancario(), null);
            }
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsDeudaResponseOk: ", e2);
        }
    }

    @Subscribe
    public void onStopDebit(StopDebitEvent stopDebitEvent) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onStopDebit ");
        sb.append(stopDebitEvent.getResult());
        Log.d(str, sb.toString());
        this.aq = false;
        dismissProgress();
        final StopDebitEvent stopDebitEvent2 = stopDebitEvent;
        AnonymousClass18 r1 = new BaseWSResponseHandler(this.as, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.PAGO_TARJETAS, this, this.as) {
            /* access modifiers changed from: protected */
            public void onOk() {
                PagoTarjetasFragment.this.onStopDebitResultOk(stopDebitEvent2);
            }
        };
        r1.handleWSResponse(stopDebitEvent);
    }

    public void onStopDebitResultOk(StopDebitEvent stopDebitEvent) {
        try {
            StopDebitResponseBean stopDebitResponseBean = (StopDebitResponseBean) stopDebitEvent.getBeanResponse();
            this.ap = stopDebitResponseBean.getStopDebitBodyResponseBean().getNumComprobante();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Número de comprobante ");
            sb.append(stopDebitResponseBean.getStopDebitBodyResponseBean().getNumComprobante());
            Log.d(str, sb.toString());
            goToComprobanteStopDebit(stopDebitResponseBean.getStopDebitBodyResponseBean().getNumComprobante(), stopDebitResponseBean.getStopDebitBodyResponseBean().getFechaComprobante());
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onStopDebitResultOk: ", e2);
        }
    }

    @Subscribe
    public void onPagoTarjeta(PagoTarjetaEvent pagoTarjetaEvent) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onPagoTarjeta ");
        sb.append(pagoTarjetaEvent.getResult());
        Log.d(str, sb.toString());
        dismissProgress();
        final PagoTarjetaEvent pagoTarjetaEvent2 = pagoTarjetaEvent;
        AnonymousClass19 r2 = new BaseWSResponseHandler(this.as, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.PAGO_TARJETAS, this, this.as) {
            /* access modifiers changed from: protected */
            public void onOk() {
                PagoTarjetasFragment.this.onPagoTarjetaResultOk(pagoTarjetaEvent2);
            }
        };
        r2.handleWSResponse(pagoTarjetaEvent);
    }

    public void onPagoTarjetaResultOk(PagoTarjetaEvent pagoTarjetaEvent) {
        try {
            PagoTarjetaResponseBean pagoTarjetaResponseBean = (PagoTarjetaResponseBean) pagoTarjetaEvent.getBeanResponse();
            this.ap = pagoTarjetaResponseBean.getPagoTarjetaBodyResponseBean().getNumComprobante();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Número de comprobante ");
            sb.append(pagoTarjetaResponseBean.getPagoTarjetaBodyResponseBean().getNumComprobante());
            Log.d(str, sb.toString());
            goToComprobantePago(pagoTarjetaResponseBean.getPagoTarjetaBodyResponseBean().getNumComprobante());
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onPagoTarjetaResultOk: ", e2);
        }
    }

    public final void configActionShareReceipt(View view, String str, String str2) {
        final View view2 = view;
        final String str3 = str2;
        final String str4 = str;
        AnonymousClass20 r0 = new OptionsToShareImpl(this, this.as, getFragmentManager()) {
            public View getViewToShare() {
                return view2;
            }

            public void receiveIntentAppShare(Intent intent) {
                PagoTarjetasFragment.this.startActivityForResult(Intent.createChooser(intent, PagoTarjetasFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
            }

            public void optionShareSelected() {
                super.optionShareSelected();
            }

            public void optionCancelSelected() {
                super.optionCancelSelected();
            }
        };
        this.f240ar = r0;
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        this.f240ar.onRequestPermissionsResult(i2, strArr, iArr);
    }

    public void showRequestPermissionExplation(final int i2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
                PagoTarjetasFragment.this.rememberShareReceipt(PagoTarjetasFragment.this.svComprobanteStopDebit);
            }

            public void onSimpleActionButton() {
                PagoTarjetasFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
            }
        });
        newInstance.show(getFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    public void rememberShareReceipt(View view) {
        if (!this.aq) {
            if (this.f240ar == null) {
                b(view);
            }
            this.aq = true;
            this.f240ar.showAlert();
        }
    }

    private void b(View view) {
        configActionShareReceipt(view, view.getTag().toString(), view.getTag().toString().concat("-").concat(this.ap));
    }

    public boolean isForgetShareReceipt() {
        return (this.mControlPager.getDisplayedChild() == 5 || this.mControlPager.getDisplayedChild() == 3) && !this.aq;
    }

    public void onClickShowActionShareReceipt(View view) {
        this.aq = true;
        b(view);
        if (this.f240ar != null) {
            this.f240ar.show();
        }
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 999) {
            UtilFile.deleteDirectory(new File(UtilFile.getPathReceiptTmp()));
        } else {
            super.onActivityResult(i2, i3, intent);
        }
    }

    private void G() {
        if (this.etValueDollars != null) {
            this.etValueDollars.setText(getString(R.string.ID978_CCARDS_PREPARAR_PAGO_LABEL_MONTO_HINT));
        }
        if (this.etValuePesos != null) {
            this.etValuePesos.setText(getString(R.string.ID978_CCARDS_PREPARAR_PAGO_LABEL_MONTO_HINT));
        }
    }

    @OnFocusChange({2131365347})
    public void onFocusValueDollars(boolean z) {
        this.etValueDollars.onFocusChangeExtend(this.etValueDollars, z);
    }

    @OnFocusChange({2131365356})
    public void onFocusValuePesos(boolean z) {
        this.etValuePesos.onFocusChangeExtend(this.etValuePesos, z);
    }

    public void onItemClick2(AdapterView<?> adapterView, View view, int i2, long j) {
        if (!this.at) {
            this.at = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    PagoTarjetasFragment.this.at = false;
                }
            }, LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            G();
            int i3 = (int) j;
            this.f = new PagoTarjetasController(this.as, getFragmentManager(), this.a, i3);
            this.f.setPantallaPrepararPago(this.pantallaPrepararPago);
            this.f.setPantallaAyudaTarjeta(this.pantallaAyudaTarjeta);
            this.f.setPantallaDetalleTarjetaPagoTotalDolares(this.pantallaDetalleTarjetaPagoTotalDolares);
            this.f.setPantallaDetalleTarjetaPagoTotalPesos(this.pantallaDetalleTarjetaPagoTotalPesos);
            this.f.setPantallaConfirmarStopDebit(this.pantallaConfirmarStopDebit);
            this.f.setPantallaComprobanteStopDebit(this.pantallaComprobanteStopDebit);
            this.f.setPantallaConfirmarPago(this.pantallaConfirmarPago);
            this.f.setPantallaComprobantePago(this.pantallaComprobantePago);
            this.f.setPantallaAyudaTarjeta(this.pantallaAyudaTarjeta);
            goToPrepararPago(i3);
            this.am = i2 - 1;
        }
    }
}
