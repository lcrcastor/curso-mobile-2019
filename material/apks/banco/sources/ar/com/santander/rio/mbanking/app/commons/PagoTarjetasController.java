package ar.com.santander.rio.mbanking.app.commons;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.DateView;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.message.TokenParser;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PagoTarjetasController {
    private CCnsDeuda A;
    private ArrayList<String> B = new ArrayList<>();
    private ArrayList<String> C = new ArrayList<>();
    private ArrayList<String> D = new ArrayList<>();
    private ArrayList<String> E = new ArrayList<>();
    private ArrayList<String> F = new ArrayList<>();
    private String G;
    private String H;
    private String I;
    private String J;
    private String K;
    private final String a = PagoTarjetasController.class.getName();
    private Context b;
    private FragmentManager c;
    private View d;
    private View e;
    private View f;
    private View g;
    private View h;
    private View i;
    private View j;
    private View k;
    private SessionManager l;
    private int m;
    private boolean n = false;
    private boolean o = false;
    private boolean p = false;
    private boolean q = false;
    private boolean r = false;
    private boolean s = false;
    private boolean t = false;
    private boolean u = false;
    private boolean v = false;
    private boolean w = false;
    private boolean x = false;
    private boolean y = true;
    private CAccessibility z;

    public PagoTarjetasController(Context context, FragmentManager fragmentManager, SessionManager sessionManager, int i2) {
        this.b = context;
        this.z = new CAccessibility(context);
        this.c = fragmentManager;
        this.l = sessionManager;
        this.m = i2;
        this.A = new CCnsDeuda();
    }

    public View getPantallaPrepararPago() {
        return this.d;
    }

    public void setPantallaPrepararPago(View view) {
        this.d = view;
    }

    public View getPantallaConfirmarStopDebit() {
        return this.e;
    }

    public void setPantallaConfirmarStopDebit(View view) {
        this.e = view;
    }

    public View getPantallaComprobanteStopDebit() {
        return this.f;
    }

    public void setPantallaComprobanteStopDebit(View view) {
        this.f = view;
    }

    public View getPantallaConfirmarPago() {
        return this.g;
    }

    public void setPantallaConfirmarPago(View view) {
        this.g = view;
    }

    public View getPantallaComprobantePago() {
        return this.h;
    }

    public void setPantallaComprobantePago(View view) {
        this.h = view;
    }

    public View getPantallaAyudaTarjeta() {
        return this.i;
    }

    public void setPantallaAyudaTarjeta(View view) {
        this.i = view;
    }

    public View getPantallaDetalleTarjetaPagoTotalDolares() {
        return this.k;
    }

    public void setPantallaDetalleTarjetaPagoTotalDolares(View view) {
        this.k = view;
    }

    public View getPantallaDetalleTarjetaPagoTotalPesos() {
        return this.j;
    }

    public void setPantallaDetalleTarjetaPagoTotalPesos(View view) {
        this.j = view;
    }

    public void resetComboOptions() {
        this.B = new ArrayList<>();
        this.C = new ArrayList<>();
        this.D = new ArrayList<>();
        this.E = new ArrayList<>();
        this.F = new ArrayList<>();
    }

    public Context getContext() {
        return this.b;
    }

    public FragmentManager getFragmentManager() {
        return this.c;
    }

    public SessionManager getSessionManager() {
        return this.l;
    }

    public int getChosenCreditCard() {
        return this.m;
    }

    public ArrayList<String> getAmountPayableComboOptions() {
        return this.B;
    }

    public void setAmountPayableComboOptions(ArrayList<String> arrayList) {
        this.B = arrayList;
    }

    public ArrayList<String> getCurrencyComboOptions() {
        return this.C;
    }

    public void setCurrencyComboOptions(ArrayList<String> arrayList) {
        this.C = arrayList;
    }

    public ArrayList<String> getArsDebitAccountComboOptions() {
        return this.D;
    }

    public void setArsDebitAccountComboOptions(ArrayList<String> arrayList) {
        this.D = arrayList;
    }

    public ArrayList<String> getUsdDebitAccountComboOptions() {
        return this.E;
    }

    public void setUsdDebitAccountComboOptions(ArrayList<String> arrayList) {
        this.E = arrayList;
    }

    public ArrayList<String> getPaymentDateComboOptions() {
        return this.F;
    }

    public void setPaymentDateComboOptions(ArrayList<String> arrayList) {
        this.F = arrayList;
    }

    public String getSelectedOptionAmountPayable() {
        return this.G;
    }

    public void setSelectedOptionAmountPayable(String str) {
        this.G = str;
    }

    public String getSelectedOptionCurrency() {
        return this.H;
    }

    public void setSelectedOptionCurrency(String str) {
        this.H = str;
    }

    public String getSelectedOptionDebitAccountInPesos() {
        return this.I;
    }

    public void setSelectedOptionDebitAccountInPesos(String str) {
        this.I = str;
    }

    public String getSelectedOptionDebitAccountInDollars() {
        return this.J;
    }

    public void setSelectedOptionDebitAccountInDollars(String str) {
        this.J = str;
    }

    public String getSelectedOptionPaymentDate() {
        return this.K;
    }

    public void setSelectedOptionPaymentDate(String str) {
        this.K = str;
    }

    private boolean a() {
        String str = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append("importeP ");
        sb.append(CCnsDeuda.getImporteP(getSessionManager(), getChosenCreditCard()));
        Log.d(str, sb.toString());
        return CCnsDeuda.getImporteP(getSessionManager(), getChosenCreditCard()) != null && Integer.parseInt(UtilCurrency.getFormattedAmountInArsFromString(CCnsDeuda.getImporteP(getSessionManager(), getChosenCreditCard())).replaceAll("\\.", "").replaceAll(",", "")) > 0;
    }

    private boolean b() {
        return CCnsDeuda.getImportePM(getSessionManager(), getChosenCreditCard()) != null && Integer.parseInt(CCnsDeuda.getImportePM(getSessionManager(), getChosenCreditCard()).replaceAll("\\.", "").replaceAll(",", "")) > 0;
    }

    public boolean isImporteDGreatThanZero() {
        return CCnsDeuda.getImporteD(getSessionManager(), getChosenCreditCard()) != null && Integer.parseInt(UtilCurrency.getFormattedAmountInArsFromString(CCnsDeuda.getImporteD(getSessionManager(), getChosenCreditCard())).replaceAll("\\.", "").replaceAll(",", "")) > 0;
    }

    private boolean c() {
        return CCnsDeuda.getImporteTotalPesos(getSessionManager(), getChosenCreditCard()) != null && Long.parseLong(UtilCurrency.getFormattedAmountInArsFromString(CCnsDeuda.getImporteTotalPesos(getSessionManager(), getChosenCreditCard()).replaceAll("\\.", "").replaceAll(",", "")).replaceAll("\\.", "").replaceAll(",", "")) > 0;
    }

    private boolean d() {
        return CCnsDeuda.getImporteTotalDolares(getSessionManager(), getChosenCreditCard()) != null && Long.parseLong(UtilCurrency.getFormattedAmountInArsFromString(CCnsDeuda.getImporteTotalDolares(getSessionManager(), getChosenCreditCard()).replaceAll("\\.", "").replaceAll(",", "")).replaceAll("\\.", "").replaceAll(",", "")) > 0;
    }

    public boolean isAutomaticDebit() {
        return CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard()).equals("1") || CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard()).equals("2") || CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard()).equals("3");
    }

    public boolean isAdditionalCard() {
        return CCnsDeuda.getCodigo(getSessionManager(), getChosenCreditCard()).equals("AD");
    }

    public boolean isFormaMonedaZero() {
        return CCnsDeuda.getFormaMoneda(getSessionManager()).equals("0");
    }

    public void setHelpLink(boolean z2) {
        this.n = z2;
    }

    public boolean showPrepararPagoHelpLink() {
        return this.n;
    }

    public void viewPrepararPagoHelpLink() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_help).setVisibility(0);
    }

    public void hidePrepararPagoHelpLink() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_help).setVisibility(8);
    }

    public void setContinueButton(boolean z2) {
        this.o = z2;
    }

    public boolean showPrepararPagoContinueButton() {
        return this.o;
    }

    public void hidePrepararPagoContinueButton() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_button_continue).setVisibility(8);
    }

    public void setPagarButton(boolean z2) {
        this.q = z2;
    }

    public boolean showPrepararPagoPagarButton() {
        return this.q;
    }

    public void hidePrepararPagoPagarButton() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_button_pay).setVisibility(8);
    }

    public void setStopDebitButton(boolean z2) {
        this.p = z2;
    }

    public boolean showPrepararPagoStopDebitButton() {
        return this.p;
    }

    public void hidePrepararPagoStopDebitButton() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_button_stop_debit).setVisibility(8);
    }

    public String getPrepararPagoCreditCard() {
        return ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_credit_card_content)).getText().toString();
    }

    public void setPrepararPagoCreditCard(String str) {
        TextView textView = (TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_credit_card_content);
        textView.setText(str);
        try {
            textView.setContentDescription(this.z.applyFilterCreditCard(str));
        } catch (Exception unused) {
        }
    }

    public void setPrepararPagoExpireDate() {
        ((DateView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_expire_date_content)).setCElementAcc(new CDateAcc());
        if (Html.fromHtml(CCnsDeuda.getExpireDateFormatted(getSessionManager(), getChosenCreditCard())).toString() == null || !UtilDate.isDate(Html.fromHtml(CCnsDeuda.getExpireDateFormatted(getSessionManager(), getChosenCreditCard())).toString(), Constants.FORMAT_DATE_APP)) {
            ((DateView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_expire_date_content)).setDateStr(Constants.FORMAT_DATE_NULL);
        } else {
            ((DateView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_expire_date_content)).setDateStr(UtilDate.getDateFormat(Html.fromHtml(CCnsDeuda.getExpireDateFormatted(getSessionManager(), getChosenCreditCard())).toString(), Constants.FORMAT_DATE_APP));
        }
    }

    public void setPrepararPagoBalanceInPesos() {
        if (isAdditionalCard()) {
            getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_balance_in_pesos_content).setContentDescription(PagoTarjetasConstants.IMPORTE_TARJETA_ADICIONAL);
            ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_balance_in_pesos_content)).setText(Html.fromHtml(CCnsDeuda.getImportePFormatted(getSessionManager(), getChosenCreditCard())).toString());
            return;
        }
        CAmount cAmount = new CAmount(Html.fromHtml(CCnsDeuda.getImportePFormatted(getSessionManager(), getChosenCreditCard())).toString());
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_balance_in_pesos_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_balance_in_pesos_content)).setAmount(cAmount.getAmount());
    }

    public void setPrepararPagoBalanceInDolares() {
        if (isAdditionalCard()) {
            getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_balance_in_dollars_content).setContentDescription(PagoTarjetasConstants.IMPORTE_TARJETA_ADICIONAL);
            ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_balance_in_dollars_content)).setText(Html.fromHtml(CCnsDeuda.getImporteDFormatted(getSessionManager(), getChosenCreditCard())).toString());
            return;
        }
        CAmount cAmount = new CAmount(Html.fromHtml(CCnsDeuda.getImporteDFormatted(getSessionManager(), getChosenCreditCard())).toString());
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_balance_in_dollars_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_balance_in_dollars_content)).setAmount(cAmount.getAmount());
    }

    private void e() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_divider).setVisibility(0);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_container).setVisibility(0);
        CAccessibility cAccessibility = new CAccessibility(getContext());
        try {
            String quoteOfTheDay = CCnsDeuda.getQuoteOfTheDay(getSessionManager());
            if (quoteOfTheDay == null) {
                quoteOfTheDay = "";
            }
            String replace = quoteOfTheDay.replace(UtilsCuentas.SEPARAOR2, "").replace("=", "");
            String replace2 = replace.replace(Constants.SYMBOL_CURRENCY_DOLAR, Constants.SYMBOL_CURRENCY_PESOS);
            String quoteOfTheDayArs = CCnsDeuda.getQuoteOfTheDayArs(getSessionManager());
            String applyFilterAmount = cAccessibility.applyFilterAmount(quoteOfTheDayArs);
            AmountView amountView = (AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_ars_content);
            StringBuilder sb = new StringBuilder();
            sb.append(replace);
            sb.append(" = ");
            sb.append(quoteOfTheDayArs);
            amountView.setText(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(replace2);
            sb2.append(" = ");
            sb2.append(applyFilterAmount);
            amountView.setContentDescription(sb2.toString());
        } catch (Exception unused) {
        }
    }

    private void f() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_divider).setVisibility(8);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_container).setVisibility(8);
    }

    public void initializePrepararPagoQuoteOfTheDay() {
        if (!g() || ((!a() || !isImporteDGreatThanZero()) && ((!a() || !C()) && (!isImporteDGreatThanZero() || !B())))) {
            f();
        } else {
            e();
        }
    }

    private boolean g() {
        String quoteOfTheDayArs = CCnsDeuda.getQuoteOfTheDayArs(getSessionManager());
        if (quoteOfTheDayArs == null || quoteOfTheDayArs.length() <= 0) {
            return false;
        }
        try {
            if (Float.parseFloat(quoteOfTheDayArs.replace(',', '.').replace('$', TokenParser.SP).trim()) > BitmapDescriptorFactory.HUE_RED) {
                return true;
            }
            return false;
        } catch (NumberFormatException e2) {
            e2.fillInStackTrace();
            Log.d(getClass().getCanonicalName(), "La cotización no es válida.");
            return false;
        }
    }

    public String getPrepararPagoQuoteOfTheDay() {
        return ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_ars_content)).getText().toString();
    }

    public String getPrepararPagoQuoteOfTheDayLabel() {
        return ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_content)).getText().toString();
    }

    public void setPrepararPagoMinimumPayment() {
        CAmount cAmount = new CAmount(Html.fromHtml(CCnsDeuda.getImportePM(getSessionManager(), getChosenCreditCard())).toString());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_minimum_payment_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_minimum_payment_content)).setAmount(cAmount.getAmount());
    }

    public String getPrepararPagoChosenAmountPayable() {
        return ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_payable_content)).getText().toString();
    }

    public void setPrepararPagoChosenAmountPayable(String str) {
        ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_payable_content)).setText(str);
        if (str.toUpperCase().matches(getContext().getString(R.string.ID498_CCARDS_MAIN_LBL_AUX_OTROS))) {
            resetMontos();
        }
    }

    public String getPrepararPagoChosenCurrency() {
        return ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_currency_content)).getText().toString();
    }

    public void setPrepararPagoChosenCurrency(String str) {
        ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_currency_content)).setText(str);
    }

    public void setPrepararPagoChosenPaymentDate(String str, boolean z2) {
        if (z2) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
            try {
                str = new SimpleDateFormat("ddMMyyyy").format(simpleDateFormat.parse(str));
            } catch (ParseException unused) {
            }
        }
        ((DateView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_payment_date_content)).setCElementAcc(new CDateAcc());
        if (Html.fromHtml(UtilDate.getCurrentDateFormat(str)).toString() == null || !UtilDate.isDate(Html.fromHtml(UtilDate.getCurrentDateFormat(str)).toString(), Constants.FORMAT_DATE_APP)) {
            ((DateView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_payment_date_content)).setDateStr(Constants.FORMAT_DATE_NULL);
        } else {
            ((DateView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_payment_date_content)).setDateStr(UtilDate.getDateFormat(Html.fromHtml(UtilDate.getCurrentDateFormat(str)).toString(), Constants.FORMAT_DATE_APP));
        }
    }

    public String getPrepararPagoChosenPaymentDate() {
        return ((DateView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_payment_date_content)).getText().toString();
    }

    private boolean h() {
        String str = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append("fecha vencimiento ");
        sb.append(Integer.parseInt(CCnsDeuda.getExpireDate(getSessionManager(), getChosenCreditCard())));
        Log.d(str, sb.toString());
        String str2 = this.a;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("fecha actual ");
        sb2.append(Integer.parseInt(UtilDate.getCurrentDate(CCnsDeuda.getCurrentDate(getSessionManager()))));
        Log.d(str2, sb2.toString());
        return Integer.parseInt(CCnsDeuda.getExpireDate(getSessionManager(), getChosenCreditCard())) >= Integer.parseInt(UtilDate.getCurrentDate(CCnsDeuda.getCurrentDate(getSessionManager())));
    }

    private void a(boolean z2) {
        this.v = z2;
    }

    public boolean showPrepararPagoPaymentDateCombo() {
        return this.v;
    }

    public void initializePrepararPagoPaymentDateCombo() {
        if (h()) {
            if (getSelectedOptionPaymentDate() == null) {
                setSelectedOptionPaymentDate(Html.fromHtml(CCnsDeuda.getPaymentDateToday(getSessionManager())).toString());
                setPrepararPagoChosenPaymentDate(CCnsDeuda.getCurrentDate(getSessionManager()), false);
            } else if (getSelectedOptionPaymentDate().equals(Html.fromHtml(CCnsDeuda.getPaymentDateToday(getSessionManager())).toString())) {
                setPrepararPagoChosenPaymentDate(CCnsDeuda.getCurrentDate(getSessionManager()), false);
            } else if (getSelectedOptionPaymentDate().equals(Html.fromHtml(CCnsDeuda.getPaymentDateVencimiento(getSessionManager())).toString())) {
                setPrepararPagoChosenPaymentDate(CCnsDeuda.getExpireDateFormatted(getSessionManager(), getChosenCreditCard()), true);
            }
            getPaymentDateComboOptions().add(Html.fromHtml(CCnsDeuda.getPaymentDateToday(getSessionManager())).toString());
            getPaymentDateComboOptions().add(Html.fromHtml(CCnsDeuda.getPaymentDateVencimiento(getSessionManager())).toString());
            a(true);
            return;
        }
        setPrepararPagoChosenPaymentDate(CCnsDeuda.getCurrentDate(getSessionManager()), false);
        setSelectedOptionPaymentDate(Html.fromHtml(CCnsDeuda.getPaymentDateToday(getSessionManager())).toString());
        getPaymentDateComboOptions().add(Html.fromHtml(CCnsDeuda.getPaymentDateToday(getSessionManager())).toString());
    }

    private boolean a(String str) {
        return str.equals("00") || str.equals("01");
    }

    private boolean b(String str) {
        return str.equals("03") || str.equals("04");
    }

    private boolean c(String str) {
        return str.equals("02");
    }

    public void setPrepararPagoDebitAccountComboOptions() {
        List listGroupBeans = CConsDescripciones.getConsDescripcionTPOCTACORTA(getSessionManager()).getListGroupBeans();
        for (int i2 = 0; i2 < CCnsDeuda.getCuentas(getSessionManager()).size(); i2++) {
            String tipoCuenta = CCnsDeuda.getTipoCuenta(getSessionManager(), i2);
            String sucursalCuenta = CCnsDeuda.getSucursalCuenta(getSessionManager(), i2);
            String numeroCuentaFormateado = this.A.getNumeroCuentaFormateado(getSessionManager(), i2);
            if (a(tipoCuenta)) {
                StringBuilder sb = new StringBuilder();
                sb.append(((ListGroupBean) listGroupBeans.get(Integer.parseInt(tipoCuenta))).getLabel());
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(sucursalCuenta);
                sb.append("-");
                sb.append(numeroCuentaFormateado);
                String sb2 = sb.toString();
                getArsDebitAccountComboOptions().add(sb2);
                this.A.addMapCuentas(getSessionManager(), sb2, i2);
            } else if (b(tipoCuenta)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(((ListGroupBean) listGroupBeans.get(Integer.parseInt(tipoCuenta))).getLabel());
                sb3.append(UtilsCuentas.SEPARAOR2);
                sb3.append(sucursalCuenta);
                sb3.append("-");
                sb3.append(numeroCuentaFormateado);
                String sb4 = sb3.toString();
                getUsdDebitAccountComboOptions().add(sb4);
                this.A.addMapCuentas(getSessionManager(), sb4, i2);
            } else if (c(tipoCuenta)) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(((ListGroupBean) listGroupBeans.get(5)).getLabel());
                sb5.append(UtilsCuentas.SEPARAOR2);
                sb5.append(sucursalCuenta);
                sb5.append("-");
                sb5.append(numeroCuentaFormateado);
                String sb6 = sb5.toString();
                getArsDebitAccountComboOptions().add(sb6);
                this.A.addMapCuentas(getSessionManager(), sb6, i2);
                StringBuilder sb7 = new StringBuilder();
                sb7.append(((ListGroupBean) listGroupBeans.get(6)).getLabel());
                sb7.append(UtilsCuentas.SEPARAOR2);
                sb7.append(sucursalCuenta);
                sb7.append("-");
                sb7.append(numeroCuentaFormateado);
                String sb8 = sb7.toString();
                getUsdDebitAccountComboOptions().add(sb8);
                this.A.addMapCuentas(getSessionManager(), sb8, i2);
            }
        }
    }

    public void setPrepararPagoDebitAccountInPesos() {
        if (getArsDebitAccountComboOptions().size() > 0) {
            new CFiltersAccessibility(getContext());
            if (getSelectedOptionDebitAccountInPesos() != null) {
                ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_pesos_content)).setText(getSelectedOptionDebitAccountInPesos());
                try {
                    getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_pesos_content).setContentDescription(this.z.applyFilterAccount(getSelectedOptionDebitAccountInPesos()));
                } catch (Exception unused) {
                }
            } else {
                ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_pesos_content)).setText((CharSequence) getArsDebitAccountComboOptions().get(0));
                try {
                    getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_pesos_content).setContentDescription(this.z.applyFilterAccount((String) getArsDebitAccountComboOptions().get(0)));
                } catch (Exception unused2) {
                }
                setSelectedOptionDebitAccountInPesos((String) getArsDebitAccountComboOptions().get(0));
            }
        }
    }

    public String getPrepararPagoDebitAccountInPesos() {
        return ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_pesos_content)).getText().toString();
    }

    public void setPrepararPagoDebitAccountInDolares() {
        if (getUsdDebitAccountComboOptions().size() > 0) {
            CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(getContext());
            if (getSelectedOptionDebitAccountInDollars() != null) {
                String filterSymbolAccounts = cFiltersAccessibility.filterSymbolAccounts(getSelectedOptionDebitAccountInDollars());
                String filterAccountsNumber = cFiltersAccessibility.filterAccountsNumber(getSelectedOptionDebitAccountInDollars().substring(5));
                String str = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("filterSymbolAccounts ");
                sb.append(filterSymbolAccounts);
                Log.d(str, sb.toString());
                String str2 = this.a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("filterAccountsNumber ");
                sb2.append(filterAccountsNumber);
                Log.d(str2, sb2.toString());
                View findViewById = getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_dollars_content);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(filterSymbolAccounts);
                sb3.append(UtilsCuentas.SEPARAOR2);
                sb3.append(filterAccountsNumber);
                findViewById.setContentDescription(sb3.toString());
                ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_dollars_content)).setText(getSelectedOptionDebitAccountInDollars());
                return;
            }
            String filterSymbolAccounts2 = cFiltersAccessibility.filterSymbolAccounts(((String) getUsdDebitAccountComboOptions().get(0)).toString());
            String filterAccountsNumber2 = cFiltersAccessibility.filterAccountsNumber(((String) getUsdDebitAccountComboOptions().get(0)).toString().substring(5));
            String str3 = this.a;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("filterSymbolAccounts ");
            sb4.append(filterSymbolAccounts2);
            Log.d(str3, sb4.toString());
            String str4 = this.a;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("filterAccountsNumber ");
            sb5.append(filterAccountsNumber2);
            Log.d(str4, sb5.toString());
            View findViewById2 = getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_dollars_content);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(filterSymbolAccounts2);
            sb6.append(UtilsCuentas.SEPARAOR2);
            sb6.append(filterAccountsNumber2);
            findViewById2.setContentDescription(sb6.toString());
            ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_dollars_content)).setText((CharSequence) getUsdDebitAccountComboOptions().get(0));
            setSelectedOptionDebitAccountInDollars((String) getUsdDebitAccountComboOptions().get(0));
        }
    }

    public String getPrepararPagoDebitAccountInDolares() {
        return ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_dollars_content)).getText().toString();
    }

    public String getPrepararPagoAmountInPesos() {
        return ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_content)).getText().toString();
    }

    public void setPrepararPagoAmountInPesos(String str) {
        CAmount cAmount = new CAmount(str);
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_content)).setAmount(cAmount.getAmount());
    }

    public String getPrepararPagoAmountInDolares() {
        return ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_content)).getText().toString();
    }

    public void setPrepararPagoAmountInDolares(String str) {
        CAmount cAmount = new CAmount(str);
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_content)).setAmount(cAmount.getAmount());
    }

    private void i() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_container).setVisibility(0);
    }

    private void j() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_container).setVisibility(8);
    }

    private void k() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_editable_container).setVisibility(0);
    }

    public String getPrepararPagoManualAmountInArs() {
        String obj = ((NumericEditText) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_editable_content)).getText().toString();
        if (obj.contains(",")) {
            return obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(obj);
        sb.append(",00");
        return sb.toString();
    }

    private void l() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_editable_container).setVisibility(8);
    }

    private void b(boolean z2) {
        this.w = z2;
    }

    private void m() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_see_detail_container).setVisibility(0);
        b(true);
    }

    public boolean showAmountInArsDetail() {
        return this.w;
    }

    private void n() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_see_detail_container).setVisibility(8);
    }

    private void o() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_container).setVisibility(0);
    }

    private void p() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_container).setVisibility(8);
    }

    private void q() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_editable_container).setVisibility(0);
        NumericEditText numericEditText = (NumericEditText) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_editable_content);
    }

    public String getPrepararPagoManualAmountInUsd() {
        return ((NumericEditText) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_editable_content)).getText().toString();
    }

    private void r() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_editable_container).setVisibility(8);
    }

    private void c(boolean z2) {
        this.x = z2;
    }

    private void s() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_see_detail_container).setVisibility(0);
        c(true);
    }

    public boolean showAmountInUsdDetail() {
        return this.x;
    }

    private void t() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_see_detail_container).setVisibility(8);
    }

    private void u() {
        ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_payable_content)).setText(Html.fromHtml(CCnsDeuda.getAmountPayableTotal(getSessionManager())).toString());
        setSelectedOptionAmountPayable(Html.fromHtml(CCnsDeuda.getAmountPayableTotal(getSessionManager())).toString());
        String str = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append("amount payable total ");
        sb.append(Html.fromHtml(CCnsDeuda.getAmountPayableTotal(getSessionManager())).toString());
        Log.d(str, sb.toString());
        if (B()) {
            if (c()) {
                setPrepararPagoAmountInPesos(Html.fromHtml(CCnsDeuda.getImporteTotalPesos(getSessionManager(), getChosenCreditCard())).toString());
                if (g()) {
                    m();
                }
            } else {
                setPrepararPagoAmountInPesos(Html.fromHtml(CCnsDeuda.getImportePFormatted(getSessionManager(), getChosenCreditCard())).toString());
                n();
            }
        } else if (!C()) {
            setPrepararPagoAmountInPesos(Html.fromHtml(CCnsDeuda.getImportePFormatted(getSessionManager(), getChosenCreditCard())).toString());
            n();
            setPrepararPagoAmountInDolares(CCnsDeuda.getImporteDFormatted(getSessionManager(), getChosenCreditCard()));
            t();
        } else if (d()) {
            setPrepararPagoAmountInDolares(CCnsDeuda.getImporteTotalDolares(getSessionManager(), getChosenCreditCard()));
            if (g()) {
                s();
            }
        } else {
            setPrepararPagoAmountInDolares(CCnsDeuda.getImporteDFormatted(getSessionManager(), getChosenCreditCard()));
            t();
        }
        i();
        o();
        l();
        r();
    }

    public boolean isPrepararPagoChosenAmountPayableTotal() {
        return ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_payable_content)).getText().toString().equals(Html.fromHtml(CCnsDeuda.getAmountPayableTotal(getSessionManager())).toString());
    }

    private void v() {
        setSelectedOptionAmountPayable(Html.fromHtml(CCnsDeuda.getAmountPayableMinimum(getSessionManager())).toString());
        setPrepararPagoChosenAmountPayable(Html.fromHtml(CCnsDeuda.getAmountPayableMinimum(getSessionManager())).toString());
        setPrepararPagoAmountInPesos(Html.fromHtml(CCnsDeuda.getImportePM(getSessionManager(), getChosenCreditCard())).toString());
        i();
        l();
        n();
    }

    public boolean isPrepararPagoChosenAmountPayableMinimum() {
        return ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_payable_content)).getText().toString().equals(Html.fromHtml(CCnsDeuda.getAmountPayableMinimum(getSessionManager())).toString());
    }

    private void w() {
        setSelectedOptionAmountPayable(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString());
        setPrepararPagoChosenAmountPayable(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString());
        k();
        q();
        j();
        p();
        n();
        t();
    }

    public boolean isPrepararPagoChosenAmountPayableOther() {
        return ((TextView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_payable_content)).getText().toString().equals(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString());
    }

    private void d(boolean z2) {
        this.r = z2;
    }

    public boolean showPrepararPagoAmountPayableCombo() {
        return this.r;
    }

    private void a(int i2) {
        getAmountPayableComboOptions().clear();
        switch (i2) {
            case 0:
                w();
                getAmountPayableComboOptions().add(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString());
                d(false);
                break;
            case 1:
                if (getSelectedOptionAmountPayable() != null) {
                    String str = this.a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("selectedOptionAmountPayable ");
                    sb.append(getSelectedOptionAmountPayable());
                    Log.d(str, sb.toString());
                    if (getSelectedOptionAmountPayable().equals(Html.fromHtml(CCnsDeuda.getAmountPayableTotal(getSessionManager())).toString())) {
                        u();
                    } else if (getSelectedOptionAmountPayable().equals(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString())) {
                        w();
                    }
                } else {
                    u();
                }
                getAmountPayableComboOptions().add(Html.fromHtml(CCnsDeuda.getAmountPayableTotal(getSessionManager())).toString());
                getAmountPayableComboOptions().add(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString());
                d(true);
                break;
            case 2:
                if (getSelectedOptionAmountPayable() != null) {
                    String str2 = this.a;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("selectedOptionAmountPayable ");
                    sb2.append(getSelectedOptionAmountPayable());
                    Log.d(str2, sb2.toString());
                    if (getSelectedOptionAmountPayable().equals(Html.fromHtml(CCnsDeuda.getAmountPayableMinimum(getSessionManager())).toString())) {
                        v();
                    } else if (getSelectedOptionAmountPayable().equals(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString())) {
                        w();
                    }
                } else {
                    v();
                }
                getAmountPayableComboOptions().add(Html.fromHtml(CCnsDeuda.getAmountPayableMinimum(getSessionManager())).toString());
                getAmountPayableComboOptions().add(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString());
                d(true);
                break;
            case 3:
                if (getSelectedOptionAmountPayable() != null) {
                    String str3 = this.a;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("selectedOptionAmountPayable ");
                    sb3.append(getSelectedOptionAmountPayable());
                    Log.d(str3, sb3.toString());
                    if (getSelectedOptionAmountPayable().equals(Html.fromHtml(CCnsDeuda.getAmountPayableTotal(getSessionManager())).toString())) {
                        u();
                    } else if (getSelectedOptionAmountPayable().equals(Html.fromHtml(CCnsDeuda.getAmountPayableMinimum(getSessionManager())).toString())) {
                        v();
                    } else if (getSelectedOptionAmountPayable().equals(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString())) {
                        w();
                    }
                } else {
                    v();
                }
                getAmountPayableComboOptions().add(Html.fromHtml(CCnsDeuda.getAmountPayableMinimum(getSessionManager())).toString());
                getAmountPayableComboOptions().add(Html.fromHtml(CCnsDeuda.getAmountPayableTotal(getSessionManager())).toString());
                getAmountPayableComboOptions().add(Html.fromHtml(CCnsDeuda.getAmountPayableOther(getSessionManager())).toString());
                d(true);
                break;
        }
        setPrepararPagoChosenAmountPayable(Html.fromHtml((String) getAmountPayableComboOptions().get(0)).toString());
        prepararPagoAmountPayableAndCurrencyBehaviour();
    }

    public void evaluateFormaPagoTCredito(int i2) {
        switch (i2) {
            case 0:
                a(3);
                if (!a()) {
                    a(0);
                    return;
                } else if (!b()) {
                    a(1);
                    return;
                } else {
                    a(3);
                    return;
                }
            case 1:
                a(1);
                if (a()) {
                    a(1);
                    return;
                } else {
                    a(0);
                    return;
                }
            case 2:
                a(2);
                if (!b() || !a()) {
                    a(0);
                    return;
                } else {
                    a(2);
                    return;
                }
            case 3:
                a(0);
                return;
            default:
                return;
        }
    }

    public void initializePrepararPagoAmountPayableHelpLinkAndButtons(AnalyticsManager analyticsManager) {
        if (isAutomaticDebit()) {
            if (isAdditionalCard()) {
                Log.d(this.a, "Codigo Tarjeta == AD");
                a(0);
                hidePrepararPagoHelpLink();
            } else {
                Log.d(this.a, "Codigo Tarjeta <> AD");
                evaluateFormaPagoTCredito(Integer.parseInt(CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard())));
                setHelpLink(true);
                viewPrepararPagoHelpLink();
            }
            hidePrepararPagoContinueButton();
            setStopDebitButton(true);
            setPagarButton(true);
            analyticsManager.trackScreen(getContext().getString(R.string.analytics_screen_name_detalle_tarjeta_adherida_debito_automatico));
            return;
        }
        if (isAdditionalCard()) {
            Log.d(this.a, "Codigo Tarjeta == AD");
            a(0);
        } else {
            Log.d(this.a, "Codigo Tarjeta <> AD");
            evaluateFormaPagoTCredito(Integer.parseInt(CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard())));
        }
        hidePrepararPagoHelpLink();
        setContinueButton(true);
        hidePrepararPagoStopDebitButton();
        hidePrepararPagoPagarButton();
        analyticsManager.trackScreen(getContext().getString(R.string.analytics_screen_name_detalle_tarjeta_no_adherida_debito_automatico));
    }

    private void x() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_pesos_divider).setVisibility(0);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_pesos_container).setVisibility(0);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_divider).setVisibility(0);
    }

    private void y() {
        this.y = false;
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_pesos_divider).setVisibility(8);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_pesos_container).setVisibility(8);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_divider).setVisibility(8);
        j();
        l();
        n();
    }

    private void z() {
        this.y = true;
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_dollars_divider).setVisibility(0);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_dollars_container).setVisibility(0);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_divider).setVisibility(0);
    }

    private void A() {
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_dollars_divider).setVisibility(8);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_debit_account_in_dollars_container).setVisibility(8);
        getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_divider).setVisibility(8);
        p();
        r();
        t();
    }

    private void e(boolean z2) {
        this.s = z2;
    }

    public boolean showPrepararPagoCurrencyCombo() {
        return this.s;
    }

    private void f(boolean z2) {
        this.t = z2;
    }

    public boolean showPrepararPagoDebitAccountInArsCombo() {
        return this.t;
    }

    private void g(boolean z2) {
        this.u = z2;
    }

    public boolean showPrepararPagoDebitAccountInUsdCombo() {
        return this.u;
    }

    private boolean B() {
        return getPrepararPagoChosenCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArs(getSessionManager())).toString());
    }

    private boolean C() {
        return getPrepararPagoChosenCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyUsd(getSessionManager())).toString());
    }

    private boolean D() {
        return getPrepararPagoChosenCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(getSessionManager())).toString());
    }

    public void initializeCurrencyCombo() {
        if (isFormaMonedaZero()) {
            Log.d(this.a, "formaMoneda == 0. Se mostrarán las tres opciones");
            b(3);
            return;
        }
        Log.d(this.a, "formaMoneda <> 0. Sólo puedo tener importe en Pesos");
        b(0);
    }

    private void b(int i2) {
        switch (i2) {
            case 0:
                setPrepararPagoChosenCurrency(Html.fromHtml(CCnsDeuda.getCurrencyArs(getSessionManager())).toString());
                setSelectedOptionCurrency(Html.fromHtml(CCnsDeuda.getCurrencyArs(getSessionManager())).toString());
                x();
                A();
                if (getArsDebitAccountComboOptions().size() > 1) {
                    f(true);
                    return;
                }
                return;
            case 1:
                setPrepararPagoChosenCurrency(Html.fromHtml(CCnsDeuda.getCurrencyUsd(getSessionManager())).toString());
                setSelectedOptionCurrency(Html.fromHtml(CCnsDeuda.getCurrencyUsd(getSessionManager())).toString());
                z();
                y();
                if (getUsdDebitAccountComboOptions().size() > 1) {
                    g(true);
                    return;
                }
                return;
            case 2:
                setPrepararPagoChosenCurrency(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(getSessionManager())).toString());
                setSelectedOptionCurrency(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(getSessionManager())).toString());
                x();
                z();
                if (getArsDebitAccountComboOptions().size() > 1) {
                    f(true);
                }
                if (getUsdDebitAccountComboOptions().size() > 1) {
                    g(true);
                    return;
                }
                return;
            case 3:
                if (getSelectedOptionCurrency() != null) {
                    String str = this.a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("selectedOptionCurrency ");
                    sb.append(getSelectedOptionCurrency());
                    Log.d(str, sb.toString());
                    if (getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArs(getSessionManager())).toString())) {
                        b(0);
                    } else if (getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyUsd(getSessionManager())).toString())) {
                        b(1);
                    } else if (getSelectedOptionCurrency().equals(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(getSessionManager())).toString())) {
                        b(2);
                    }
                } else {
                    b(0);
                }
                if (isPrepararPagoChosenAmountPayableMinimum()) {
                    d(Html.fromHtml(CCnsDeuda.getCurrencyArs(getSessionManager())).toString());
                } else {
                    d(Html.fromHtml(CCnsDeuda.getCurrencyArs(getSessionManager())).toString());
                    d(Html.fromHtml(CCnsDeuda.getCurrencyUsd(getSessionManager())).toString());
                    d(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(getSessionManager())).toString());
                }
                if (!isPrepararPagoChosenAmountPayableMinimum()) {
                    e(true);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void d(String str) {
        if (str != null && str.length() > 0) {
            ArrayList currencyComboOptions = getCurrencyComboOptions();
            if (currencyComboOptions == null) {
                return;
            }
            if (currencyComboOptions.isEmpty()) {
                currencyComboOptions.add(str);
                return;
            }
            currencyComboOptions.trimToSize();
            int i2 = 0;
            boolean z2 = false;
            while (i2 < currencyComboOptions.size()) {
                String str2 = (String) currencyComboOptions.get(i2);
                if (str2 != null && str2.equalsIgnoreCase(str)) {
                    i2 = currencyComboOptions.size();
                    z2 = true;
                }
                i2++;
            }
            if (!z2) {
                getCurrencyComboOptions().add(str);
            }
        }
    }

    public void prepararPagoAmountPayableAndCurrencyBehaviour() {
        String str = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append("chosenAmountPayable ");
        sb.append(getPrepararPagoChosenAmountPayable());
        Log.d(str, sb.toString());
        Integer.parseInt(CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard()));
        if (isPrepararPagoChosenAmountPayableTotal() || isPrepararPagoChosenAmountPayableOther()) {
            if (getCurrencyComboOptions().size() > 0) {
                e(true);
            }
            if (isPrepararPagoChosenAmountPayableTotal()) {
                Log.d(this.a, "Ha seleccionado Importe a Pagar Total");
                if (B() && !a() && !c()) {
                    w();
                } else if (C() && !isImporteDGreatThanZero() && !d()) {
                    w();
                } else if (!D() || (a() && isImporteDGreatThanZero())) {
                    u();
                } else {
                    w();
                }
            } else {
                Log.d(this.a, "Ha seleccionado Importe a Pagar Otro");
                w();
            }
            this.C = new ArrayList<>();
            d(Html.fromHtml(CCnsDeuda.getCurrencyArs(getSessionManager())).toString());
            d(Html.fromHtml(CCnsDeuda.getCurrencyUsd(getSessionManager())).toString());
            d(Html.fromHtml(CCnsDeuda.getCurrencyArsUsd(getSessionManager())).toString());
            if (B()) {
                b(0);
            } else if (C()) {
                b(1);
            } else if (D()) {
                b(2);
            }
        } else if (isPrepararPagoChosenAmountPayableMinimum()) {
            Log.d(this.a, "Ha seleccionado Importe a Pagar Mínimo");
            v();
            b(0);
            this.C = new ArrayList<>();
            d(Html.fromHtml(CCnsDeuda.getCurrencyArs(getSessionManager())).toString());
            setSelectedOptionCurrency(CCnsDeuda.getCurrencyArs(getSessionManager()));
            e(false);
        }
    }

    public void setConfirmarPagoTarjeta() {
        TextView textView = (TextView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_credit_card_content);
        textView.setText(getPrepararPagoCreditCard());
        try {
            textView.setContentDescription(this.z.applyFilterCreditCard(textView.getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void setConfirmarPagoImporte() {
        ((TextView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_amount_content)).setText(getPrepararPagoChosenAmountPayable());
    }

    public void setConfirmarPagoCuentaDebitoInArs() {
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(getContext());
        String filterSymbolAccounts = cFiltersAccessibility.filterSymbolAccounts(getPrepararPagoDebitAccountInPesos());
        String filterAccountsNumber = cFiltersAccessibility.filterAccountsNumber(getPrepararPagoDebitAccountInPesos().substring(5));
        String str = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append("filterSymbolAccounts ");
        sb.append(filterSymbolAccounts);
        Log.d(str, sb.toString());
        String str2 = this.a;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("filterAccountsNumber ");
        sb2.append(filterAccountsNumber);
        Log.d(str2, sb2.toString());
        ((TextView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_pesos_content)).setText(getPrepararPagoDebitAccountInPesos());
        try {
            getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_pesos_content).setContentDescription(this.z.applyFilterAccount(getPrepararPagoDebitAccountInPesos()));
        } catch (Exception unused) {
        }
    }

    public void setConfirmarPagoCuentaDebitoInUsd() {
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(getContext());
        String filterSymbolAccounts = cFiltersAccessibility.filterSymbolAccounts(getPrepararPagoDebitAccountInDolares());
        String filterAccountsNumber = cFiltersAccessibility.filterAccountsNumber(getPrepararPagoDebitAccountInDolares().substring(5));
        String str = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append("filterSymbolAccounts ");
        sb.append(filterSymbolAccounts);
        Log.d(str, sb.toString());
        String str2 = this.a;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("filterAccountsNumber ");
        sb2.append(filterAccountsNumber);
        Log.d(str2, sb2.toString());
        ((TextView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_dollars_content)).setText(getPrepararPagoDebitAccountInDolares());
        try {
            getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_dollars_content).setContentDescription(this.z.applyFilterAccount(getPrepararPagoDebitAccountInDolares()));
        } catch (Exception unused) {
        }
    }

    public void setConfirmarPagoAmountInArs() {
        Object obj;
        if (isPrepararPagoChosenAmountPayableTotal() || isPrepararPagoChosenAmountPayableMinimum()) {
            String prepararPagoAmountInPesos = getPrepararPagoAmountInPesos();
            NumberFormat.getInstance(Constants.LOCALE_DEFAULT_APP);
            CAmount cAmount = new CAmount(prepararPagoAmountInPesos.toString());
            cAmount.setSymbolCurrency("");
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_pesos_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_pesos_content)).setAmount(cAmount.getAmount());
        } else if (isPrepararPagoChosenAmountPayableOther()) {
            try {
                obj = NumberFormat.getInstance(Constants.LOCALE_DEFAULT_APP).parse(getPrepararPagoManualAmountInArs());
            } catch (ParseException unused) {
                String str = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("[Formato incorrecto]:");
                sb.append(null);
                Log.d(str, sb.toString());
                obj = null;
            }
            CAmount cAmount2 = new CAmount(obj.toString());
            cAmount2.setSymbolCurrency("");
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_pesos_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_pesos_content)).setAmount(cAmount2.getAmount());
        }
    }

    public void setConfirmarPagoAmountInUsd() {
        Object obj;
        if (isPrepararPagoChosenAmountPayableTotal() || isPrepararPagoChosenAmountPayableMinimum()) {
            String prepararPagoAmountInDolares = getPrepararPagoAmountInDolares();
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_dollars_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_dollars_content)).setAmount(prepararPagoAmountInDolares);
        } else if (isPrepararPagoChosenAmountPayableOther()) {
            try {
                obj = NumberFormat.getInstance(Constants.LOCALE_DEFAULT_APP).parse(getPrepararPagoManualAmountInUsd());
            } catch (ParseException unused) {
                String str = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("[Formato incorrecto]:");
                sb.append(null);
                Log.d(str, sb.toString());
                obj = null;
            }
            CAmount cAmount = new CAmount(obj.toString());
            cAmount.setSymbolCurrency("");
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_dollars_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_dollars_content)).setAmount(cAmount.getAmount());
        }
    }

    public void showDebitAccountAndAmountInArs() {
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_pesos_divider).setVisibility(0);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_pesos_container).setVisibility(0);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_pesos_divider).setVisibility(0);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_pesos_container).setVisibility(0);
    }

    public void showDebitAccountAndAmountInUsd() {
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_dollars_divider).setVisibility(0);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_dollars_container).setVisibility(0);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_dollars_divider).setVisibility(0);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_dollars_container).setVisibility(0);
    }

    public void hideDebitAccountAndAmountInArs() {
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_pesos_divider).setVisibility(8);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_pesos_container).setVisibility(8);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_pesos_divider).setVisibility(8);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_pesos_container).setVisibility(8);
    }

    public void hideDebitAccountAndAmountInUsd() {
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_dollars_divider).setVisibility(8);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_debit_account_in_dollars_container).setVisibility(8);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_dollars_divider).setVisibility(8);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_balance_in_dollars_container).setVisibility(8);
    }

    public void setConfirmarPagoQuoteOfTheDay() {
        if (G().booleanValue()) {
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_quote_of_the_day_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_quote_of_the_day_content)).setAmount(E());
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_quote_of_the_day_ars_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_quote_of_the_day_ars_content)).setAmount(F());
            getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_quote_of_the_day_divider).setVisibility(0);
            getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_quote_of_the_day_container).setVisibility(0);
            return;
        }
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_quote_of_the_day_divider).setVisibility(8);
        getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_quote_of_the_day_container).setVisibility(8);
    }

    public void setConfirmarPagoPaymentDate() {
        ((DateView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_payment_date_content)).setCElementAcc(new CDateAcc());
        if (getPrepararPagoChosenPaymentDate() == null || !UtilDate.isDate(getPrepararPagoChosenPaymentDate(), Constants.FORMAT_DATE_APP)) {
            ((DateView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_payment_date_content)).setDateStr(Constants.FORMAT_DATE_NULL);
        } else {
            ((DateView) getPantallaConfirmarPago().findViewById(R.id.confirm_payment_form_payment_date_content)).setDateStr(UtilDate.getDateFormat(getPrepararPagoChosenPaymentDate(), Constants.FORMAT_DATE_APP));
        }
    }

    public void setComprobantePagoTarjeta() {
        TextView textView = (TextView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_credit_card_content);
        textView.setText(getPrepararPagoCreditCard());
        try {
            textView.setContentDescription(this.z.applyFilterAccount(textView.getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void setComprobantePagoCuentaDebitoInArs() {
        ((TextView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_pesos_content)).setText(CAccounts.getMaskAccount(getPrepararPagoDebitAccountInPesos()));
        try {
            getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_pesos_content).setContentDescription(this.z.applyFilterAccount(((TextView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_pesos_content)).getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void setComprobantePagoCuentaDebitoInUsd() {
        ((TextView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_dollars_content)).setText(CAccounts.getMaskAccount(getPrepararPagoDebitAccountInDolares()));
        try {
            getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_dollars_content).setContentDescription(this.z.applyFilterAccount(((TextView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_dollars_content)).getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void setComprobantePagoAmountInArs() {
        AmountView amountView = (AmountView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_pesos_content);
        amountView.setCElementAcc(new CAmountAcc());
        String str = "";
        if (isPrepararPagoChosenAmountPayableTotal() || isPrepararPagoChosenAmountPayableMinimum()) {
            str = getPrepararPagoAmountInPesos();
        } else if (isPrepararPagoChosenAmountPayableOther()) {
            str = getPrepararPagoManualAmountInArs();
        }
        amountView.setText(str);
    }

    public void setComprobantePagoAmountInUsd() {
        String str;
        AmountView amountView = (AmountView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_dollars_content);
        amountView.setCElementAcc(new CAmountAcc());
        String str2 = "";
        if (isPrepararPagoChosenAmountPayableTotal() || isPrepararPagoChosenAmountPayableMinimum()) {
            str = CAmountIU.getInstance().getOutputUserFromString(getPrepararPagoAmountInDolares(), Constants.LOCALE_DEFAULT_APP);
        } else {
            if (isPrepararPagoChosenAmountPayableOther()) {
                try {
                    str = CAmountIU.getInstance().getOutputUserFromString(getPrepararPagoManualAmountInUsd(), Constants.LOCALE_DEFAULT_APP);
                } catch (Exception unused) {
                }
            }
            str = str2;
        }
        amountView.setAmount(str);
    }

    public void hideComprobantePagoDebitAccountAndAmountInArs() {
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_pesos_divider).setVisibility(8);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_pesos_container).setVisibility(8);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_pesos_divider).setVisibility(8);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_pesos_container).setVisibility(8);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_dollars_divider).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_dollars_container).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_dollars_divider).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_dollars_container).setVisibility(0);
    }

    public void hideComprobantePagoDebitAccountAndAmountInUsd() {
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_dollars_divider).setVisibility(8);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_dollars_container).setVisibility(8);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_dollars_divider).setVisibility(8);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_dollars_container).setVisibility(8);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_pesos_divider).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_pesos_container).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_pesos_divider).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_pesos_container).setVisibility(0);
    }

    public void showComprobantePagoDebitAccountAndAmountInArsAndUsd() {
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_pesos_divider).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_pesos_container).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_pesos_divider).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_pesos_container).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_dollars_divider).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_debit_account_in_dollars_container).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_dollars_divider).setVisibility(0);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_balance_in_dollars_container).setVisibility(0);
    }

    public void setComprobantePagoQuoteOfTheDay() {
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_quote_of_the_day_divider).setVisibility(8);
        getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_quote_of_the_day_container).setVisibility(8);
    }

    public void setComprobantePagoPaymentDate() {
        ((DateView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_payment_date_content)).setCElementAcc(new CDateAcc());
        if (getPrepararPagoChosenPaymentDate() != null) {
            String prepararPagoChosenPaymentDate = getPrepararPagoChosenPaymentDate();
            String[] split = prepararPagoChosenPaymentDate.split("/");
            if (split.length == 3 && split[2].length() == 2) {
                StringBuilder sb = new StringBuilder();
                sb.append(split[0]);
                sb.append("/");
                sb.append(split[1]);
                sb.append("/20");
                sb.append(split[2]);
                prepararPagoChosenPaymentDate = sb.toString();
            }
            ((DateView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_payment_date_content)).setDateStr(prepararPagoChosenPaymentDate);
            return;
        }
        ((DateView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_payment_date_content)).setDateStr(Constants.FORMAT_DATE_NULL);
    }

    public void setComprobantePagoNumero(String str) {
        ((TextView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_number_content)).setText(str);
        try {
            getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_number_content).setContentDescription(this.z.applyFilterCharacterToCharacter(((TextView) getPantallaComprobantePago().findViewById(R.id.proof_of_payment_form_number_content)).getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void setConfirmarStopDebitTarjeta() {
        TextView textView = (TextView) getPantallaConfirmarStopDebit().findViewById(R.id.confirm_stop_debit_form_credit_card_name_content);
        textView.setText(CCnsDeuda.getNombreTarjeta(getSessionManager(), getChosenCreditCard()));
        try {
            textView.setContentDescription(this.z.applyFilterGeneral(textView.getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void setConfirmarStopDebitIdentificacion() {
        TextView textView = (TextView) getPantallaConfirmarStopDebit().findViewById(R.id.confirm_stop_debit_form_credit_card_number_content);
        textView.setText(CCnsDeuda.getNumeroTarjetaFormateado(getSessionManager(), getChosenCreditCard()));
        try {
            textView.setContentDescription(this.z.applyFilterGeneral(textView.getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void setConfirmarStopDebitCuentaDebitoInArs(int i2) {
        switch (i2) {
            case 0:
                TextView textView = (TextView) getPantallaConfirmarStopDebit().findViewById(R.id.confirm_stop_debit_form_debit_account_in_pesos_content);
                textView.setText(getPrepararPagoDebitAccountInPesos());
                textView.setContentDescription(this.z.applyFilterGeneral(textView.getText().toString()));
                return;
            case 1:
                TextView textView2 = (TextView) getPantallaConfirmarStopDebit().findViewById(R.id.confirm_stop_debit_form_debit_account_in_pesos_content);
                textView2.setText(getPrepararPagoDebitAccountInDolares());
                textView2.setContentDescription(this.z.applyFilterGeneral(textView2.getText().toString()));
                return;
            case 2:
                TextView textView3 = (TextView) getPantallaConfirmarStopDebit().findViewById(R.id.confirm_stop_debit_form_debit_account_in_pesos_content);
                textView3.setText(getPrepararPagoDebitAccountInPesos());
                try {
                    textView3.setContentDescription(this.z.applyFilterGeneral(textView3.getText().toString()));
                    return;
                } catch (Exception unused) {
                }
            default:
                return;
        }
    }

    public void setComprobanteStopDebitTarjeta() {
        TextView textView = (TextView) getPantallaComprobanteStopDebit().findViewById(R.id.proof_of_stop_debit_form_credit_card_content);
        textView.setText(CCnsDeuda.getNombreTarjeta(getSessionManager(), getChosenCreditCard()));
        try {
            textView.setContentDescription(this.z.applyFilterGeneral(textView.getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void setComprobanteStopDebitNumeroTarjeta() {
        TextView textView = (TextView) getPantallaComprobanteStopDebit().findViewById(R.id.proof_of_stop_debit_form_identification_content);
        textView.setText(CCnsDeuda.getNumeroTarjetaFormateado(getSessionManager(), getChosenCreditCard()));
        try {
            textView.setContentDescription(this.z.applyFilterGeneral(textView.getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void setComprobanteStopDebitAccount(String str) {
        TextView textView = (TextView) getPantallaComprobanteStopDebit().findViewById(R.id.proof_of_stop_debit_form_debit_account_in_pesos_content);
        textView.setText(CAccounts.getMaskAccount(str));
        try {
            textView.setContentDescription(this.z.applyFilterAccount(CAccounts.getMaskAccount(str)));
        } catch (Exception unused) {
        }
    }

    public void setComprobanteStopDebitNumero(String str) {
        ((TextView) getPantallaComprobanteStopDebit().findViewById(R.id.proof_of_stop_debit_form_number_content)).setText(str);
    }

    public String getAyudaTarjetaMensaje() {
        String string = getContext().getString(R.string.MSG_USER000047_PagoTarj_avisoAyuda_static_1);
        if (CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard()).equals("1") || CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard()).equals("3")) {
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(" mínimo ");
            string = sb.toString();
        } else if (CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard()).equals("2")) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(string);
            sb2.append(" total ");
            string = sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(string);
        sb3.append(getContext().getString(R.string.MSG_USER000047_PagoTarj_avisoAyuda_static_2));
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(CCnsDeuda.getNombreTarjeta(getSessionManager(), getChosenCreditCard()));
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(CCnsDeuda.getNumeroTarjetaFormateado(getSessionManager(), getChosenCreditCard()));
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(getContext().getString(R.string.MSG_USER000047_PagoTarj_avisoAyuda_static_3));
        return sb3.toString();
    }

    public void setDetalleTarjetaPagoTotalDolaresBalanceInArs() {
        CAmount cAmount = new CAmount(CCnsDeuda.getImportePFormatted(getSessionManager(), getChosenCreditCard()));
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_balance_in_pesos_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_balance_in_pesos_content)).setAmount(cAmount.getAmount());
    }

    public void setDetalleTarjetaPagoTotalDolaresConversionToUsd() {
        CAmount cAmount = new CAmount(CCnsDeuda.getImportePConvertido(getSessionManager(), getChosenCreditCard()));
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_conversion_to_dollars_content)).setCElementAcc(new CAmountAcc());
        if (cAmount.getAmount() == null || cAmount.getAmount().length() <= 0 || cAmount.getAmount().toUpperCase().contains("NULL")) {
            ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_conversion_to_dollars_content)).setAmount(getContext().getString(R.string.ID498_CCARDS_MAIN_LBL_ZERO));
        } else {
            ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_conversion_to_dollars_content)).setAmount(cAmount.getAmount());
        }
    }

    public void setDetalleTarjetaPagoTotalDolaresQuoteOfTheDay() {
        if (g()) {
            ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.prepare_payment_form_quote_of_the_day_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.prepare_payment_form_quote_of_the_day_content)).setAmount(E());
            ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.prepare_payment_form_quote_of_the_day_ars_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.prepare_payment_form_quote_of_the_day_ars_content)).setAmount(F());
            return;
        }
        getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_quote_of_the_day_linearlayout).setVisibility(8);
        getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_quote_of_the_day_view_separation).setVisibility(8);
    }

    public void setDetalleTarjetaPagoTotalDolaresBalanceInUsd() {
        CAmount cAmount = new CAmount(CCnsDeuda.getImporteDFormatted(getSessionManager(), getChosenCreditCard()));
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_balance_in_dollars_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_balance_in_dollars_content)).setAmount(cAmount.getAmount());
    }

    public void setDetalleTarjetaPagoTotalDolaresTotalPaymentInUsd() {
        if (CCnsDeuda.getImporteTotalDolares(getSessionManager(), getChosenCreditCard()) != null) {
            CAmount cAmount = new CAmount(CCnsDeuda.getImporteTotalDolares(getSessionManager(), getChosenCreditCard()));
            cAmount.setSymbolCurrency("");
            ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_total_payment_in_dollars_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_total_payment_in_dollars_content)).setAmount(cAmount.getAmount());
            return;
        }
        ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_total_payment_in_dollars_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaDetalleTarjetaPagoTotalDolares().findViewById(R.id.credit_card_detail_total_payment_in_dollars_form_total_payment_in_dollars_content)).setAmount(getContext().getString(R.string.ID498_CCARDS_MAIN_LBL_ZERO));
    }

    public void setDetalleTarjetaPagoTotalPesosBalanceInUsd() {
        CAmount cAmount = new CAmount(CCnsDeuda.getImporteDFormatted(getSessionManager(), getChosenCreditCard()));
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_balance_in_dollars_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_balance_in_dollars_content)).setAmount(cAmount.getAmount());
    }

    public void setDetalleTarjetaPagoTotalPesosConversionToArs() {
        CAmount cAmount = new CAmount(CCnsDeuda.getImporteDConvertido(getSessionManager(), getChosenCreditCard()));
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_conversion_to_pesos_content)).setCElementAcc(new CAmountAcc());
        if (cAmount.getAmount() == null || cAmount.getAmount().length() <= 0 || cAmount.getAmount().toUpperCase().contains("NULL")) {
            ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_conversion_to_pesos_content)).setAmount(getContext().getString(R.string.ID498_CCARDS_MAIN_LBL_ZERO));
        } else {
            ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_conversion_to_pesos_content)).setAmount(cAmount.getAmount());
        }
    }

    public void setDetalleTarjetaPagoTotalPesosQuoteOfTheDay() {
        if (g()) {
            ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.prepare_payment_form_quote_of_the_day_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.prepare_payment_form_quote_of_the_day_content)).setAmount(E());
            ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.prepare_payment_form_quote_of_the_day_ars_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.prepare_payment_form_quote_of_the_day_ars_content)).setAmount(F());
            return;
        }
        getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_quote_of_the_day_view_separation).setVisibility(8);
        getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_quote_of_the_day_view).setVisibility(8);
    }

    public void setDetalleTarjetaPagoTotalPesosBalanceInArs() {
        CAmount cAmount = new CAmount(CCnsDeuda.getImportePFormatted(getSessionManager(), getChosenCreditCard()));
        cAmount.setSymbolCurrency("");
        ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_balance_in_pesos_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_balance_in_pesos_content)).setAmount(cAmount.getAmount());
    }

    public void setDetalleTarjetaPagoTotalPesosTotalPaymentInArs() {
        if (CCnsDeuda.getImporteTotalPesos(getSessionManager(), getChosenCreditCard()) != null) {
            CAmount cAmount = new CAmount(CCnsDeuda.getImporteTotalPesos(getSessionManager(), getChosenCreditCard()));
            cAmount.setSymbolCurrency("");
            ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_total_payment_in_pesos_content)).setCElementAcc(new CAmountAcc());
            ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_total_payment_in_pesos_content)).setAmount(cAmount.getAmount());
            return;
        }
        ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_total_payment_in_pesos_content)).setCElementAcc(new CAmountAcc());
        ((AmountView) getPantallaDetalleTarjetaPagoTotalPesos().findViewById(R.id.credit_card_detail_total_payment_in_pesos_form_total_payment_in_pesos_content)).setAmount(getContext().getString(R.string.ID498_CCARDS_MAIN_LBL_ZERO));
    }

    private boolean e(String str) {
        if (str == null) {
            return false;
        }
        try {
            if (NumberFormat.getInstance(Constants.LOCALE_DEFAULT_APP).parse(str).doubleValue() > 0.0d) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    private boolean f(String str) {
        return Long.valueOf(str.replaceAll("\\.", "").replaceAll(",", "")).longValue() > 0;
    }

    public boolean isManualAmountOk(boolean z2) {
        if (isPrepararPagoChosenAmountPayableOther()) {
            if (B()) {
                if (getPrepararPagoManualAmountInArs().isEmpty() && !z2) {
                    return false;
                }
                if (!z2 && !e(getPrepararPagoManualAmountInArs())) {
                    return false;
                }
            } else if (C()) {
                if (getPrepararPagoManualAmountInUsd().isEmpty() && !z2) {
                    return false;
                }
                if (!z2 && !f(getPrepararPagoManualAmountInUsd())) {
                    return false;
                }
            } else if (D()) {
                if ((getPrepararPagoManualAmountInArs().isEmpty() || getPrepararPagoManualAmountInUsd().isEmpty()) && !z2) {
                    return false;
                }
                if (!z2 && !e(getPrepararPagoManualAmountInArs()) && f(getPrepararPagoManualAmountInUsd())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isAccountInPesosShowed() {
        return this.y;
    }

    public void setAccountInPesosShowed(boolean z2) {
        this.y = z2;
    }

    public CCnsDeuda getcCnsDeuda() {
        return this.A;
    }

    public void setcCnsDeuda(CCnsDeuda cCnsDeuda) {
        this.A = cCnsDeuda;
    }

    public void resetMontos() {
        if (getPrepararPagoChosenAmountPayable().toUpperCase().matches(getContext().getString(R.string.ID498_CCARDS_MAIN_LBL_AUX_OTROS))) {
            ((NumericEditText) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_pesos_editable_content)).setText(getContext().getString(R.string.ID978_CCARDS_PREPARAR_PAGO_LABEL_MONTO_HINT));
            ((NumericEditText) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_amount_in_dollars_editable_content)).setText(getContext().getString(R.string.ID978_CCARDS_PREPARAR_PAGO_LABEL_MONTO_HINT));
        }
    }

    public void setPantallaAD() {
        if (CCnsDeuda.getCodigo(getSessionManager(), getChosenCreditCard()).toUpperCase().equals("AD") || CCnsDeuda.getFormaPagoTCredito(getSessionManager(), getChosenCreditCard()).toUpperCase().equals("0")) {
            hidePrepararPagoHelpLink();
            hidePrepararPagoStopDebitButton();
            this.p = false;
            return;
        }
        viewPrepararPagoHelpLink();
        showPrepararPagoStopDebitButton();
        this.p = true;
    }

    private String E() {
        return ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_content)).getText().toString();
    }

    private String F() {
        return ((AmountView) getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_ars_content)).getText().toString();
    }

    private Boolean G() {
        return Boolean.valueOf(H() == 0);
    }

    private int H() {
        return getPantallaPrepararPago().findViewById(R.id.prepare_payment_form_quote_of_the_day_container).getVisibility();
    }
}
