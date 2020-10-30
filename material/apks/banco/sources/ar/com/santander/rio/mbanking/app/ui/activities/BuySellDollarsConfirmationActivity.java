package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.buySellDollars.ConfirmationBuySellDollarsPresenter;
import ar.com.santander.rio.mbanking.app.module.buySellDollars.ConfirmationBuySellDollarsView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.BuySellDollarsConstants.Extras;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.CompraVentaDolarResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public abstract class BuySellDollarsConfirmationActivity extends BaseMvpActivity implements OnClickListener, ConfirmationBuySellDollarsView {
    @InjectView(2131366350)
    TextView activityTitle;
    @InjectView(2131362681)
    Button btn_continue;
    protected ConfirmationBuySellDollarsPresenter confirmationBuySellDollarsPresenter;
    @InjectView(2131362719)
    TextView lbl_data_destinationAccount;
    @InjectView(2131362720)
    TextView lbl_data_dollarsToOperate;
    @InjectView(2131362721)
    TextView lbl_data_exchangeRate;
    @InjectView(2131362722)
    TextView lbl_data_originAccount;
    @InjectView(2131362723)
    TextView lbl_data_pesosToOperate;
    @InjectView(2131362725)
    TextView lbl_dollarsToOperate;
    @InjectView(2131362726)
    TextView lbl_legals;
    @InjectView(2131362728)
    TextView lbl_pesosToOperate;
    protected String mAmount;
    protected String mAmountToBeDebited;
    protected String mAmountToBeDeposited;
    protected CAmount mCAmount;
    protected String mDateTime;
    protected CompraVentaDolaresCuentaBean mDestinationAccount;
    protected String mExchangeRate;
    protected String mLegals1;
    protected String mLegals2;
    protected String mOperationNbr;
    protected CompraVentaDolaresCuentaBean mOriginAccount;
    protected String mReceiptNbr;
    protected String mSelectedCurrency;
    @Inject
    public SessionManager mSessionManager;
    @Inject
    AnalyticsManager p;
    ImageView q;

    public void clearScreenData() {
    }

    public void exchangeRateValidationFailed() {
    }

    public void offHoursValidationFailed() {
    }

    public void attachView() {
        if (!this.confirmationBuySellDollarsPresenter.isViewAttached()) {
            this.confirmationBuySellDollarsPresenter.attachView(this);
        }
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.q = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        this.q.setOnClickListener(this);
    }

    public void detachView() {
        if (this.confirmationBuySellDollarsPresenter.isViewAttached()) {
            this.confirmationBuySellDollarsPresenter.detachView();
        }
    }

    public void initialize() {
        Intent intent = getIntent();
        this.mOriginAccount = (CompraVentaDolaresCuentaBean) intent.getExtras().getParcelable(Extras.originAccount);
        this.mDestinationAccount = (CompraVentaDolaresCuentaBean) intent.getExtras().getParcelable(Extras.destinationAccount);
        this.mAmount = intent.getStringExtra(Extras.amount);
        this.mAmountToBeDebited = intent.getStringExtra(Extras.amountToBeDebited);
        this.mAmountToBeDeposited = intent.getStringExtra(Extras.amountToBeDeposited);
        this.mSelectedCurrency = intent.getStringExtra(Extras.selectedCurrency);
        this.mExchangeRate = intent.getStringExtra(Extras.exchangeRate);
        this.mLegals1 = intent.getStringExtra(Extras.legals);
        this.btn_continue.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 3) {
            Intent intent2 = new Intent();
            if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
            } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
            }
            setResult(-1, intent2);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_buy_sell_dollars_confirmation);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        this.confirmationBuySellDollarsPresenter = new ConfirmationBuySellDollarsPresenter(this.mBus, this.mDataManager, this);
        this.confirmationBuySellDollarsPresenter.attachView(this);
        showConfirmation(this.mOriginAccount, this.mDestinationAccount, this.mAmountToBeDebited, this.mAmountToBeDeposited, this.mExchangeRate, this.mLegals1);
    }

    /* access modifiers changed from: protected */
    public void showConfirmation(CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2, String str, String str2, String str3, String str4) {
        this.mCAmount = new CAmount(str3.replace(".", "").replace(',', '.'));
        this.mCAmount.setSymbolCurrencyDollarOrPeso(false);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_LABEL)).append(": ").append(getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_VALUE, new Object[]{this.mCAmount.getAmount()}));
        spannableStringBuilder.setSpan(new CalligraphyTypefaceSpan(TypefaceUtils.load(getApplicationContext().getAssets(), "fonts/OpenSans-Semibold.otf")), getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_LABEL).length() + 2, spannableStringBuilder.length(), 33);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.generic_black)), getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_LABEL).length() + 2, spannableStringBuilder.length(), 33);
        this.lbl_data_exchangeRate.setText(spannableStringBuilder, BufferType.SPANNABLE);
        try {
            this.lbl_data_exchangeRate.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_exchangeRate.getText().toString()));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        this.lbl_data_destinationAccount.setText(compraVentaDolaresCuentaBean2.getAbreviatureAndAccountFormat(this.mSessionManager));
        this.lbl_data_originAccount.setText(compraVentaDolaresCuentaBean.getAbreviatureAndAccountFormat(this.mSessionManager));
        try {
            this.lbl_data_originAccount.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(compraVentaDolaresCuentaBean.getAbreviatureAndAccountFormat(this.mSessionManager)));
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
        try {
            this.lbl_data_destinationAccount.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(compraVentaDolaresCuentaBean2.getAbreviatureAndAccountFormat(this.mSessionManager)));
        } catch (Exception e3) {
            e3.fillInStackTrace();
        }
        this.lbl_legals.setText(Html.fromHtml(str4));
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.toggle) {
            onBackPressed();
        }
    }

    public void onCompraVentaDolar(CompraVentaDolarResponseBean compraVentaDolarResponseBean) {
        String str = compraVentaDolarResponseBean.compraVentaDolarBodyResponseBean.fecha;
        String str2 = compraVentaDolarResponseBean.compraVentaDolarBodyResponseBean.hora;
        this.mLegals1 = compraVentaDolarResponseBean.compraVentaDolarBodyResponseBean.legales1;
        this.mLegals2 = compraVentaDolarResponseBean.compraVentaDolarBodyResponseBean.legales2;
        this.mOperationNbr = compraVentaDolarResponseBean.compraVentaDolarBodyResponseBean.numeroDeOperacion;
        this.mReceiptNbr = compraVentaDolarResponseBean.compraVentaDolarBodyResponseBean.numeroDeComprobante;
        this.mDateTime = String.format("%s %s", new Object[]{UtilDate.getDateFormat(str, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), str2});
        displayReceipt();
    }

    /* access modifiers changed from: protected */
    public Intent setExtraInformation() {
        Intent intent = new Intent();
        intent.putExtra(Extras.dateTime, this.mDateTime);
        intent.putExtra(Extras.operationNbr, this.mOperationNbr);
        intent.putExtra(Extras.receiptNbr, this.mReceiptNbr);
        intent.putExtra(Extras.originAccount, this.mOriginAccount);
        intent.putExtra(Extras.destinationAccount, this.mDestinationAccount);
        intent.putExtra(Extras.amountToBeDebited, this.mAmountToBeDebited);
        intent.putExtra(Extras.amountToBeDeposited, this.mAmountToBeDeposited);
        intent.putExtra(Extras.exchangeRate, this.mExchangeRate);
        intent.putExtra(Extras.legals, this.mLegals1);
        intent.putExtra(Extras.legals2, this.mLegals2);
        return intent;
    }
}
