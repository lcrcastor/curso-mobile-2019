package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.exceptions.AccountEmptyException;
import ar.com.santander.rio.mbanking.app.module.buySellDollars.HomeBuySellDollarsPresenter;
import ar.com.santander.rio.mbanking.app.module.buySellDollars.HomeBuySellDollarsView;
import ar.com.santander.rio.mbanking.app.ui.activities.BuyDollarsConfirmationActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SellDollarsConfirmationActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.BuySellDollarsConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.BuySellDollarsConstants.Dialogs;
import ar.com.santander.rio.mbanking.app.ui.constants.BuySellDollarsConstants.Extras;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.components.OnBoardingTextStylingSet;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.SimulacionDolarEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDatosInicialesDolaresResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.IHorizontalScrollListListener;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.ToggleItem;
import ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class BuySellDollarsFragment extends BaseMvpFragment implements OnClickListener, HomeBuySellDollarsView, IDialogListenerExtended, IHorizontalScrollListListener {
    @Inject
    AnalyticsManager a;
    @InjectView(2131366350)
    TextView activityTitle;
    private String ad;
    private String ae;
    private String af;
    private String ag;
    private String ah;
    private String ai;
    private CAmount aj;
    /* access modifiers changed from: private */
    public HomeBuySellDollarsPresenter ak;
    private OnBoardingTextStylingSet al;
    ImageView b;
    @InjectView(2131362639)
    Button btnContinue;
    @InjectView(2131362648)
    TextView buySellDollars_destinationAccount;
    @InjectView(2131362649)
    TextView buySellDollars_destinationAmount;
    @InjectView(2131362670)
    TextView buySellDollars_exchangeRate;
    @InjectView(2131362667)
    RelativeLayout buySellDollars_home;
    @InjectView(2131362650)
    TextView buySellDollars_originAccount;
    @InjectView(2131362651)
    TextView buySellDollars_originAmount;
    @InjectView(2131362669)
    RelativeLayout buySellDollars_rll_selectedCurrency;
    @InjectView(2131362657)
    TextView buySellDollars_selectedCurrency;
    @InjectView(2131362643)
    ImageView buySellDollars_selectedCurrencyChevron;
    private SharedPreferences c;
    private CompraVentaDolaresCuentaBean d;
    private CompraVentaDolaresCuentaBean e;
    /* access modifiers changed from: private */
    public String f;
    private List<CompraVentaDolaresCuentaBean> g;
    private List<CompraVentaDolaresCuentaBean> h;
    private String i;
    @InjectView(2131362644)
    NumericEditTextWithPrefixAccesibility inputAmount;
    @Inject
    public SessionManager mSessionManager;
    @InjectView(2131365806)
    HorizontalScrollList tabSelector;

    public void OnCheckedChangeListener(List<ToggleItem> list) {
    }

    public void clearScreenData() {
    }

    public void onNegativeButton(String str) {
    }

    public void onPositiveButton(String str) {
    }

    public void onSimpleActionButton(String str) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mRootView = layoutInflater.inflate(R.layout.fragment_buy_sell_dollars, viewGroup, false);
        ButterKnife.inject((Object) this, this.mRootView);
        initialize();
        configureActionBar();
        configureLayout();
        this.ak = new HomeBuySellDollarsPresenter(this.mBus, this.mDataManager, this.mSessionManager);
        this.ak.attachView(this);
        this.ak.getDatosIniciales(this.f);
        this.a.trackScreen(getString(R.string.analytics_screen_compra_dolares));
        return this.mRootView;
    }

    public void initialize() {
        ((RelativeLayout) this.mRootView.findViewById(R.id.F20_00_rll_scroll)).setMinimumHeight(getActivity().findViewById(16908290).getHeight());
        setSelectedCurrency();
        this.f = "1";
        this.btnContinue.setOnClickListener(this);
        this.buySellDollars_originAccount.setOnClickListener(this);
        this.buySellDollars_originAmount.setOnClickListener(this);
        this.buySellDollars_destinationAccount.setOnClickListener(this);
        this.buySellDollars_destinationAmount.setOnClickListener(this);
        this.buySellDollars_selectedCurrency.setOnClickListener(this);
        this.buySellDollars_rll_selectedCurrency.setOnClickListener(this);
        this.buySellDollars_selectedCurrencyChevron.setOnClickListener(this);
        this.inputAmount.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                editable.toString();
                try {
                    if (BuySellDollarsFragment.this.inputAmount.getText().length() > 0) {
                        BuySellDollarsFragment.this.btnContinue.setEnabled(true);
                        BuySellDollarsFragment.this.btnContinue.setBackground(BuySellDollarsFragment.this.getResources().getDrawable(R.drawable.boton_redondeado_rojo));
                        return;
                    }
                    BuySellDollarsFragment.this.btnContinue.setEnabled(false);
                    BuySellDollarsFragment.this.btnContinue.setBackground(BuySellDollarsFragment.this.getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        });
    }

    public void configureActionBar() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        this.mActionBar = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
        this.b = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        this.b.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BuySellDollarsFragment.this.switchDrawer();
            }
        });
        ((SantanderRioMainActivity) getActivity()).lockMenu(false);
    }

    public void configureLayout() {
        this.buySellDollars_home.setVisibility(4);
        this.btnContinue.setEnabled(false);
        this.activityTitle.setText(getString(R.string.ID3500_COMPRA_VENTA_LBL_TIT));
        this.tabSelector.addItem(getString(R.string.ID3545_COMPRA_VENTA_BTN_COMPRAR), true, (int) R.color.grey_light);
        this.tabSelector.addItem(getString(R.string.ID3550_COMPRA_VENTA_BTN_VENDER), false, (int) R.color.grey_light);
        this.tabSelector.setHorizontalScrollListener(this);
        this.tabSelector.show();
        this.al = new OnBoardingTextStylingSet();
        this.al.put(Integer.valueOf(R.id.F20_XX_LBL_TEXT1_ONBOARDING_PAGE1), getString(R.string.IDXX_COMPRA_VENTA_LBL_ONBOARDING_HTML));
    }

    public void attachView() {
        if (!this.ak.isViewAttached()) {
            this.ak.attachView(this);
        }
    }

    public void detachView() {
        if (this.ak.isViewAttached()) {
            this.ak.detachView();
        }
    }

    public void OnNewItemSelected(ToggleItem toggleItem) {
        if (toggleItem.getLabel().equalsIgnoreCase(getString(R.string.ID3545_COMPRA_VENTA_BTN_COMPRAR))) {
            this.f = "1";
        } else if (toggleItem.getLabel().equalsIgnoreCase(getString(R.string.ID3550_COMPRA_VENTA_BTN_VENDER))) {
            this.f = "2";
        }
        setSelectedCurrency();
        this.ak.getDatosIniciales(this.f);
    }

    public void onSelectOriginAccount() {
        a(Dialogs.originAccountSelector, "Seleccione Cuenta Origen", this.g, this.d.getAbreviatureAndAccountFormat(this.mSessionManager));
    }

    public void showOnBoarding() {
        this.c = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!this.c.getBoolean(BuySellDollarsConstants.PREFERENCE_ONBOARDING, false)) {
            showOnBoarding(R.layout.onboarding_buyselldollars, R.id.F20_XX_BTN_CLOSE_PAGE1, R.id.F20_XX_FLP_ONBOARDING, BuySellDollarsConstants.PREFERENCE_ONBOARDING, this.al);
        }
    }

    public void onSelectDestinationAccount() {
        a(Dialogs.destinationAccountSelector, "Seleccione Cuenta Destino", this.h, this.e.getAbreviatureAndAccountFormat(this.mSessionManager));
    }

    public void onSelectCurrency() {
        String str = this.i.equals("USD") ? "Dólares" : this.i.equals("ARS") ? "Pesos" : "";
        showDialogSelector(Dialogs.currencySelector, getString(R.string.ID3600_COMPRA_VENTA_LBL_IMPORTE_EN), BuySellDollarsConstants.currenciesList, str);
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F20_00_BTN_CONTINUE) {
            y();
            CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean = new CompraVentaDolaresCuentaBean(this.d.tipo, this.d.sucursal, this.d.numero, "", "");
            CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2 = new CompraVentaDolaresCuentaBean(this.e.tipo, this.e.sucursal, this.e.numero, "", "");
            this.ak.doSimulacionDolar(this.f, this.i, this.ag, this.ae, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
        } else if (id2 == R.id.F20_00_IMG_SELECTED_CURRENCY_CHEVRON || id2 == R.id.F20_00_LBL_SELECTED_CURRENCY || id2 == R.id.F20_00_RLL_SELECTED_CURRENCY) {
            onSelectCurrency();
        } else {
            switch (id2) {
                case R.id.F20_00_LBL_DATA_DESTINATION_ACCOUNT /*2131362648*/:
                case R.id.F20_00_LBL_DATA_DESTINATION_AMOUNT /*2131362649*/:
                    onSelectDestinationAccount();
                    return;
                case R.id.F20_00_LBL_DATA_ORIGIN_ACCOUNT /*2131362650*/:
                case R.id.F20_00_LBL_DATA_ORIGIN_AMOUNT /*2131362651*/:
                    onSelectOriginAccount();
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onItemSelected(java.lang.String r3, java.lang.String r4) {
        /*
            r2 = this;
            int r0 = r4.hashCode()
            r1 = -221796592(0xfffffffff2c7a710, float:-7.909054E30)
            if (r0 == r1) goto L_0x0028
            r1 = -7898306(0xffffffffff877b3e, float:NaN)
            if (r0 == r1) goto L_0x001e
            r1 = 1249213734(0x4a757d26, float:4022089.5)
            if (r0 == r1) goto L_0x0014
            goto L_0x0032
        L_0x0014:
            java.lang.String r0 = "originAccountSelector"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0032
            r4 = 1
            goto L_0x0033
        L_0x001e:
            java.lang.String r0 = "destinationAccountSelector"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0032
            r4 = 0
            goto L_0x0033
        L_0x0028:
            java.lang.String r0 = "currencySelector"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0032
            r4 = 2
            goto L_0x0033
        L_0x0032:
            r4 = -1
        L_0x0033:
            switch(r4) {
                case 0: goto L_0x004e;
                case 1: goto L_0x003b;
                case 2: goto L_0x0037;
                default: goto L_0x0036;
            }
        L_0x0036:
            goto L_0x0057
        L_0x0037:
            r2.setSelectedCurrency(r3)
            goto L_0x0057
        L_0x003b:
            java.util.List<ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean> r4 = r2.g
            ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean r3 = r2.getAccountFromString(r4, r3)
            r2.setOriginAccount(r3)
            ar.com.santander.rio.mbanking.app.module.buySellDollars.HomeBuySellDollarsPresenter r3 = r2.ak
            java.lang.String r4 = r2.f
            ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean r0 = r2.d
            r3.getDatosIniciales(r4, r0)
            goto L_0x0057
        L_0x004e:
            java.util.List<ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean> r4 = r2.h
            ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean r3 = r2.getAccountFromString(r4, r3)
            r2.setDestinationAccount(r3)
        L_0x0057:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.BuySellDollarsFragment.onItemSelected(java.lang.String, java.lang.String):void");
    }

    public void onGetDatosInicialesDolares(GetDatosInicialesDolaresResponseBean getDatosInicialesDolaresResponseBean) {
        this.aj = new CAmount(getDatosInicialesDolaresResponseBean.getDatosInicialesDolaresBodyResponseBean.cotizacionDolar);
        b(this.aj.getAmount());
        this.g = getDatosInicialesDolaresResponseBean.getDatosInicialesDolaresBodyResponseBean.cuentasOrigen.cuenta;
        this.h = getDatosInicialesDolaresResponseBean.getDatosInicialesDolaresBodyResponseBean.cuentasDestino.cuenta;
        if (!(this.g == null || this.h == null)) {
            int i2 = (int) (getResources().getDisplayMetrics().density * 15.0f);
            showOnBoarding();
            setOriginAccount((CompraVentaDolaresCuentaBean) this.g.get(0));
            setDestinationAccount((CompraVentaDolaresCuentaBean) this.h.get(0));
            this.buySellDollars_originAccount.setClickable(this.g.size() > 1);
            this.buySellDollars_originAccount.setTextColor(getResources().getColor(R.color.generic_black));
            this.buySellDollars_originAmount.setClickable(this.g.size() > 1);
            this.buySellDollars_originAmount.setTextColor(getResources().getColor(R.color.generic_black));
            if (this.g.size() > 1) {
                this.buySellDollars_originAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
                this.buySellDollars_originAccount.setPadding(0, 0, 0, 0);
            } else {
                this.buySellDollars_originAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                this.buySellDollars_originAccount.setPadding(0, 0, i2, 0);
            }
            this.buySellDollars_destinationAccount.setClickable(this.h.size() > 1);
            this.buySellDollars_destinationAccount.setTextColor(getResources().getColor(R.color.generic_black));
            this.buySellDollars_destinationAmount.setClickable(this.h.size() > 1);
            this.buySellDollars_destinationAmount.setTextColor(getResources().getColor(R.color.generic_black));
            if (this.h.size() > 1) {
                this.buySellDollars_destinationAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
                this.buySellDollars_destinationAccount.setPadding(0, 0, 0, 0);
            } else {
                this.buySellDollars_destinationAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                this.buySellDollars_destinationAccount.setPadding(0, 0, i2, 0);
            }
        }
        this.buySellDollars_home.setVisibility(0);
    }

    public void setOriginAccount(CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean) {
        this.d = compraVentaDolaresCuentaBean;
        if (this.d != null) {
            this.aj = new CAmount(compraVentaDolaresCuentaBean.saldo);
            String str = this.f;
            char c2 = 65535;
            switch (str.hashCode()) {
                case 49:
                    if (str.equals("1")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        c2 = 1;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    this.aj.setSymbolCurrencyDollarOrPeso(false);
                    break;
                case 1:
                    this.aj.setSymbolCurrencyDollarOrPeso(true);
                    break;
            }
            this.buySellDollars_originAccount.setText(compraVentaDolaresCuentaBean.getAbreviatureAndAccountFormat(this.mSessionManager));
            this.buySellDollars_originAmount.setText(this.aj.getAmount());
            try {
                this.buySellDollars_originAccount.setContentDescription(new CAccessibility(getActivity()).applyFilterAccount(compraVentaDolaresCuentaBean.getAbreviatureAndAccountFormat(this.mSessionManager)));
                this.buySellDollars_originAmount.setContentDescription(new CAccessibility(getActivity()).applyFilterAmount(this.aj.getAmount()));
            } catch (Exception e2) {
                e2.fillInStackTrace();
            }
        }
    }

    public void setDestinationAccount(CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean) {
        this.e = compraVentaDolaresCuentaBean;
        if (this.e != null) {
            this.aj = new CAmount(compraVentaDolaresCuentaBean.saldo);
            String str = this.f;
            char c2 = 65535;
            switch (str.hashCode()) {
                case 49:
                    if (str.equals("1")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        c2 = 1;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    this.aj.setSymbolCurrencyDollarOrPeso(true);
                    break;
                case 1:
                    this.aj.setSymbolCurrencyDollarOrPeso(false);
                    break;
            }
            this.buySellDollars_destinationAccount.setText(this.e.getAbreviatureAndAccountFormat(this.mSessionManager));
            this.buySellDollars_destinationAmount.setText(this.aj.getAmount());
            try {
                this.buySellDollars_destinationAccount.setContentDescription(new CAccessibility(getActivity()).applyFilterAccount(this.e.getAbreviatureAndAccountFormat(this.mSessionManager)));
                this.buySellDollars_destinationAmount.setContentDescription(new CAccessibility(getActivity()).applyFilterAmount(this.aj.getAmount()));
            } catch (Exception e2) {
                e2.fillInStackTrace();
            }
        }
    }

    public void setSelectedCurrency() {
        setSelectedCurrency("Dólares");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00d2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setSelectedCurrency(java.lang.String r7) {
        /*
            r6 = this;
            int r0 = r7.hashCode()
            r1 = -1309599940(0xffffffffb1f1173c, float:-7.0166646E-9)
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x001b
            r1 = 77004642(0x496ff62, float:3.5499363E-36)
            if (r0 == r1) goto L_0x0011
            goto L_0x0025
        L_0x0011:
            java.lang.String r0 = "Pesos"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0025
            r0 = 1
            goto L_0x0026
        L_0x001b:
            java.lang.String r0 = "Dólares"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0025
            r0 = 0
            goto L_0x0026
        L_0x0025:
            r0 = -1
        L_0x0026:
            switch(r0) {
                case 0: goto L_0x002f;
                case 1: goto L_0x002a;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x0033
        L_0x002a:
            java.lang.String r0 = "ARS"
            r6.i = r0
            goto L_0x0033
        L_0x002f:
            java.lang.String r0 = "USD"
            r6.i = r0
        L_0x0033:
            android.text.SpannableStringBuilder r0 = new android.text.SpannableStringBuilder
            r0.<init>()
            java.lang.String r1 = "%s %s"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r5 = 2131756022(0x7f1003f6, float:1.914294E38)
            java.lang.String r5 = r6.getString(r5)
            r4[r2] = r5
            r4[r3] = r7
            java.lang.String r1 = java.lang.String.format(r1, r4)
            r0.append(r1)
            uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan r1 = new uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan
            android.support.v4.app.FragmentActivity r2 = r6.getActivity()
            android.content.Context r2 = r2.getApplicationContext()
            android.content.res.AssetManager r2 = r2.getAssets()
            java.lang.String r3 = "fonts/OpenSans-Bold.otf"
            android.graphics.Typeface r2 = uk.co.chrisjenx.calligraphy.TypefaceUtils.load(r2, r3)
            r1.<init>(r2)
            int r2 = r0.length()
            int r3 = r7.length()
            int r2 = r2 - r3
            int r3 = r0.length()
            r4 = 33
            r0.setSpan(r1, r2, r3, r4)
            android.text.style.ForegroundColorSpan r1 = new android.text.style.ForegroundColorSpan
            android.content.res.Resources r2 = r6.getResources()
            r3 = 2131099749(0x7f060065, float:1.781186E38)
            int r2 = r2.getColor(r3)
            r1.<init>(r2)
            int r2 = r0.length()
            int r3 = r7.length()
            int r2 = r2 - r3
            int r3 = r0.length()
            r0.setSpan(r1, r2, r3, r4)
            android.widget.TextView r1 = r6.buySellDollars_selectedCurrency
            android.widget.TextView$BufferType r2 = android.widget.TextView.BufferType.SPANNABLE
            r1.setText(r0, r2)
            android.widget.TextView r0 = r6.buySellDollars_selectedCurrency
            android.content.Context r1 = r6.getContext()
            r2 = 2131821080(0x7f110218, float:1.9274893E38)
            r0.setTextAppearance(r1, r2)
            android.widget.ImageView r0 = r6.buySellDollars_selectedCurrencyChevron
            android.widget.TextView r1 = r6.buySellDollars_selectedCurrency
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskSelector(r1)
            r0.setContentDescription(r1)
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r0 = r6.inputAmount
            java.lang.String r1 = ""
            r0.setText(r1)
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r0 = r6.inputAmount
            java.lang.String r1 = "Dólares"
            boolean r1 = r1.equals(r7)
            if (r1 == 0) goto L_0x00d2
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_DOLAR
            goto L_0x00df
        L_0x00d2:
            java.lang.String r1 = "Pesos"
            boolean r7 = r1.equals(r7)
            if (r7 == 0) goto L_0x00dd
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_PESOS
            goto L_0x00df
        L_0x00dd:
            java.lang.String r7 = ""
        L_0x00df:
            r0.setPrefix(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.BuySellDollarsFragment.setSelectedCurrency(java.lang.String):void");
    }

    private void y() {
        this.ag = this.inputAmount.getText().toString();
    }

    private void b(String str) {
        this.ae = str;
        this.aj = new CAmount(this.ae);
        this.aj.setSymbolCurrencyDollarOrPeso(false);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_LABEL)).append(": ").append(getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_VALUE, this.aj.getAmount()));
        spannableStringBuilder.setSpan(new CalligraphyTypefaceSpan(TypefaceUtils.load(getContext().getAssets(), "fonts/OpenSans-Semibold.otf")), getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_LABEL).length() + 2, spannableStringBuilder.length(), 33);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.generic_black)), getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_LABEL).length() + 2, spannableStringBuilder.length(), 33);
        this.buySellDollars_exchangeRate.setText(spannableStringBuilder, BufferType.SPANNABLE);
        try {
            this.buySellDollars_exchangeRate.setContentDescription(new CAccessibility(getActivity()).applyFilterAmount(this.buySellDollars_exchangeRate.getText().toString()));
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
    }

    private void a(String str, String str2, List<CompraVentaDolaresCuentaBean> list, String str3) {
        try {
            ArrayList arrayList = new ArrayList();
            for (CompraVentaDolaresCuentaBean abreviatureAndAccountFormat : list) {
                arrayList.add(abreviatureAndAccountFormat.getAbreviatureAndAccountFormat(this.mSessionManager));
            }
            if (arrayList.size() > 0) {
                showDialogSelector(str, str2, arrayList, str3);
                return;
            }
            throw new AccountEmptyException(getActivity().getString(R.string.ERR_ACCOUNT_EMPTY));
        } catch (AccountEmptyException e2) {
            e2.fillInStackTrace();
        }
    }

    public void showDialogSelector(String str, String str2, ArrayList<String> arrayList, String str3) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, arrayList, getString(R.string.ID93_ACCOUNTS_CHANGEACC_BTN_CANCEL), null, null, str3);
        newInstance.setDialogListenerExtended(this);
        newInstance.setCancelable(true);
        newInstance.show(getActivity().getSupportFragmentManager(), TenenciaCreditosFragment.DIALOG_SELECTOR);
    }

    public void showSadError(String str) {
        ((SantanderRioMainActivity) getActivity()).restartActionBar();
        ((SantanderRioMainActivity) getActivity()).setErrorFragment(str);
    }

    public void warnExchangeRateChanged(SimulacionDolarEvent simulacionDolarEvent) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(Html.fromHtml(simulacionDolarEvent.getTitleToShow()).toString(), Utils.formatIsbanHTMLCode(Html.fromHtml(simulacionDolarEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BuySellDollarsFragment.this.ak.getDatosIniciales(BuySellDollarsFragment.this.f);
            }
        });
        newInstance.show(getFragmentManager(), "dialog");
    }

    public void showTimeError(String str) {
        ((SantanderRioMainActivity) getActivity()).restartActionBar();
        ((SantanderRioMainActivity) getActivity()).setErrorFragment(str, (int) R.drawable.ico_reloj_gris);
    }

    public Context getContext() {
        return getActivity();
    }

    public CompraVentaDolaresCuentaBean getAccountFromString(List<CompraVentaDolaresCuentaBean> list, String str) {
        if (str != null) {
            try {
                for (CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean : list) {
                    if (str.equals(compraVentaDolaresCuentaBean.getAbreviatureAndAccountFormat(this.mSessionManager))) {
                        return compraVentaDolaresCuentaBean;
                    }
                }
            } catch (Exception e2) {
                e2.fillInStackTrace();
            }
        }
        return null;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onSimulacionDolar(ar.com.santander.rio.mbanking.services.soap.beans.SimulacionDolarResponseBean r3) {
        /*
            r2 = this;
            ar.com.santander.rio.mbanking.app.commons.CAmount r0 = new ar.com.santander.rio.mbanking.app.commons.CAmount
            ar.com.santander.rio.mbanking.services.soap.beans.body.SimulacionDolarBodyResponseBean r1 = r3.simulacionDolarBodyResponseBean
            java.lang.String r1 = r1.importeAAcreditar
            r0.<init>(r1)
            r2.aj = r0
            ar.com.santander.rio.mbanking.app.commons.CAmount r0 = r2.aj
            java.lang.String r0 = r0.getAmount()
            r2.ai = r0
            ar.com.santander.rio.mbanking.app.commons.CAmount r0 = new ar.com.santander.rio.mbanking.app.commons.CAmount
            ar.com.santander.rio.mbanking.services.soap.beans.body.SimulacionDolarBodyResponseBean r1 = r3.simulacionDolarBodyResponseBean
            java.lang.String r1 = r1.importeADebitar
            r0.<init>(r1)
            r2.aj = r0
            ar.com.santander.rio.mbanking.app.commons.CAmount r0 = r2.aj
            java.lang.String r0 = r0.getAmount()
            r2.ah = r0
            ar.com.santander.rio.mbanking.app.commons.CAmount r0 = new ar.com.santander.rio.mbanking.app.commons.CAmount
            ar.com.santander.rio.mbanking.services.soap.beans.body.SimulacionDolarBodyResponseBean r1 = r3.simulacionDolarBodyResponseBean
            java.lang.String r1 = r1.cotizacionDolar
            r0.<init>(r1)
            r2.aj = r0
            ar.com.santander.rio.mbanking.app.commons.CAmount r0 = r2.aj
            java.lang.String r0 = r0.getAmount()
            r2.ae = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.SimulacionDolarBodyResponseBean r0 = r3.simulacionDolarBodyResponseBean
            java.lang.String r0 = r0.legales1
            r2.af = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.SimulacionDolarBodyResponseBean r3 = r3.simulacionDolarBodyResponseBean
            java.lang.String r3 = r3.monedaImporteADebitar
            r2.ad = r3
            java.lang.String r3 = r2.f
            int r0 = r3.hashCode()
            switch(r0) {
                case 49: goto L_0x0059;
                case 50: goto L_0x004f;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x0063
        L_0x004f:
            java.lang.String r0 = "2"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0063
            r3 = 1
            goto L_0x0064
        L_0x0059:
            java.lang.String r0 = "1"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0063
            r3 = 0
            goto L_0x0064
        L_0x0063:
            r3 = -1
        L_0x0064:
            switch(r3) {
                case 0: goto L_0x006c;
                case 1: goto L_0x0068;
                default: goto L_0x0067;
            }
        L_0x0067:
            goto L_0x006f
        L_0x0068:
            r2.A()
            goto L_0x006f
        L_0x006c:
            r2.z()
        L_0x006f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.BuySellDollarsFragment.onSimulacionDolar(ar.com.santander.rio.mbanking.services.soap.beans.SimulacionDolarResponseBean):void");
    }

    private void z() {
        Intent B = B();
        B.setClass(getActivity(), BuyDollarsConfirmationActivity.class);
        startActivityForResult(B, 2);
    }

    private void A() {
        Intent B = B();
        B.setClass(getActivity(), SellDollarsConfirmationActivity.class);
        startActivityForResult(B, 2);
    }

    private Intent B() {
        Intent intent = new Intent();
        intent.putExtra(Extras.originAccount, this.d);
        intent.putExtra(Extras.destinationAccount, this.e);
        intent.putExtra(Extras.amountToBeDeposited, this.ai);
        intent.putExtra(Extras.amount, this.ag);
        intent.putExtra(Extras.amountToBeDebited, this.ah);
        intent.putExtra(Extras.selectedCurrency, this.ad);
        intent.putExtra(Extras.exchangeRate, this.ae);
        intent.putExtra(Extras.legals, this.af);
        return intent;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0046, code lost:
        if (r5.equals(ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants.GO_TO_LOGIN) != false) goto L_0x0068;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r5, int r6, android.content.Intent r7) {
        /*
            r4 = this;
            r0 = 0
            r1 = -1
            if (r6 != r1) goto L_0x001d
            java.lang.String r2 = "PrivateMenuSelectedOptionPosition"
            boolean r2 = r7.hasExtra(r2)
            if (r2 == 0) goto L_0x001d
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = "PrivateMenuSelectedOptionPosition"
            int r6 = r7.getIntExtra(r6, r0)
            r5.onPrivateMenuOptionSelectedInNestedActivity(r6)
            goto L_0x00b8
        L_0x001d:
            r2 = 2
            if (r6 != r1) goto L_0x00a8
            java.lang.String r3 = "WS_ERROR_DO_ACTION"
            boolean r3 = r7.hasExtra(r3)
            if (r3 == 0) goto L_0x00a8
            java.lang.String r5 = "WS_ERROR_DO_ACTION"
            java.lang.String r5 = r7.getStringExtra(r5)
            int r6 = r5.hashCode()
            switch(r6) {
                case -1667304550: goto L_0x005d;
                case -1442009346: goto L_0x0053;
                case -1365838438: goto L_0x0049;
                case -171755572: goto L_0x0040;
                case 4216548: goto L_0x0036;
                default: goto L_0x0035;
            }
        L_0x0035:
            goto L_0x0067
        L_0x0036:
            java.lang.String r6 = "GO_TO_HOME_ERROR_CLOCK"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0067
            r0 = 4
            goto L_0x0068
        L_0x0040:
            java.lang.String r6 = "GO_TO_HOME"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0067
            goto L_0x0068
        L_0x0049:
            java.lang.String r6 = "GO_TO_HOME_FUNCIONALIDAD"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0067
            r0 = 1
            goto L_0x0068
        L_0x0053:
            java.lang.String r6 = "GO_TO_CUENTAS"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0067
            r0 = 2
            goto L_0x0068
        L_0x005d:
            java.lang.String r6 = "GO_TO_HOME_ERROR_FRAGMENT"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0067
            r0 = 3
            goto L_0x0068
        L_0x0067:
            r0 = -1
        L_0x0068:
            switch(r0) {
                case 0: goto L_0x009e;
                case 1: goto L_0x00b8;
                case 2: goto L_0x0092;
                case 3: goto L_0x0082;
                case 4: goto L_0x006c;
                default: goto L_0x006b;
            }
        L_0x006b:
            goto L_0x00b8
        L_0x006c:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r6 = r7.getStringExtra(r6)
            r7 = 2131231076(0x7f080164, float:1.8078223E38)
            r0 = 2131756001(0x7f1003e1, float:1.9142897E38)
            r5.setErrorFragment(r6, r7, r0)
            goto L_0x00b8
        L_0x0082:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r6 = r7.getStringExtra(r6)
            r5.setErrorFragment(r6)
            goto L_0x00b8
        L_0x0092:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS
            r5.goToOption(r6)
            goto L_0x00b8
        L_0x009e:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            r5.gotoHome()
            goto L_0x00b8
        L_0x00a8:
            if (r6 != r1) goto L_0x00b8
            if (r5 == r2) goto L_0x00ad
            goto L_0x00b8
        L_0x00ad:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS
            r5.goToOption(r6)
        L_0x00b8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.BuySellDollarsFragment.onActivityResult(int, int, android.content.Intent):void");
    }
}
