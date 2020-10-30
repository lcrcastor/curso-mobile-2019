package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.buySellDollars.ReceiptBuySellDollarsPresenter;
import ar.com.santander.rio.mbanking.app.module.buySellDollars.ReceiptBuySellDollarsView;
import ar.com.santander.rio.mbanking.app.ui.constants.BuySellDollarsConstants.Extras;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerThreeOptions;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public abstract class BuySellDollarsReceiptActivity extends MvpPrivateMenuActivity implements OnClickListener, ReceiptBuySellDollarsView, IDialogListenerThreeOptions, OptionsToShare {
    public static final int PERMISSION_REQUEST_CODE = 1;
    private String A;
    private String B;
    /* access modifiers changed from: private */
    public Context C;
    private IsbanDialogFragment D;
    @InjectView(2131366350)
    TextView activityTitle;
    @InjectView(2131362730)
    Button btn_continuar;
    @InjectView(2131362732)
    TextView lbl_data_dollarsToOperate;
    @InjectView(2131362733)
    TextView lbl_data_exchangeRate;
    @InjectView(2131362734)
    TextView lbl_data_pesosToOperate;
    @InjectView(2131362735)
    TextView lbl_data_receipt_debitedAmount;
    @InjectView(2131362736)
    TextView lbl_data_receipt_depositedAmount;
    @InjectView(2131362737)
    TextView lbl_data_receipt_destinationAccount;
    @InjectView(2131362738)
    TextView lbl_data_receipt_exchangeRate;
    @InjectView(2131362739)
    TextView lbl_data_receipt_operationNbr;
    @InjectView(2131362740)
    TextView lbl_data_receipt_originAccount;
    @InjectView(2131362741)
    TextView lbl_data_receipt_receiptNbr;
    @InjectView(2131362742)
    TextView lbl_data_receipt_timeStamp;
    @InjectView(2131362743)
    TextView lbl_dollarsToOperate;
    @InjectView(2131362744)
    TextView lbl_pesosToOperate;
    @InjectView(2131362749)
    TextView lbl_receipt_legals;
    @InjectView(2131362750)
    TextView lbl_receipt_operationNbr;
    @InjectView(2131362752)
    TextView lbl_receipt_receiptNbr;
    @InjectView(2131362754)
    LinearLayout lnlReceipt;
    @InjectView(2131362755)
    LinearLayout lnlStatusOverview;
    protected CAmount mCAmount;
    protected String mReceiptNbr;
    @Inject
    public SessionManager mSessionManager;
    @Inject
    AnalyticsManager p;
    ImageView q;
    ImageView r;
    protected Boolean receiptHasBeenDownloaded;
    private ReceiptBuySellDollarsPresenter s;
    protected OptionsToShare sharingOptions;
    protected OptionsToShare sharingOptionsAtExit;
    protected OptionsToShare sharingOptionsForPrivateMenu;
    private String t;
    private String u;
    private CompraVentaDolaresCuentaBean v;
    private CompraVentaDolaresCuentaBean w;
    private String x;
    private String y;
    private String z;

    public void clearScreenData() {
    }

    public int getMainLayout() {
        return R.layout.activity_buy_sell_dollars_receipt;
    }

    /* access modifiers changed from: protected */
    public abstract void showSharedOption();

    public String getSelectedOption() {
        return getString(R.string.IDXX_PRIVATEMENU_BTN_BUYSELL_DOLLARS);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureOptionsShare();
        configureLayout();
        this.C = this;
        this.s = new ReceiptBuySellDollarsPresenter(this.mBus, this.mDataManager);
        this.s.attachView(this);
        this.s.showReceipt();
    }

    public void showReceipt() {
        showReceipt(this.t, this.mReceiptNbr, this.u, this.v, this.w, this.x, this.y, this.z, this.A, this.B);
    }

    /* access modifiers changed from: protected */
    public void showReceipt(String str, String str2, String str3, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2, String str4, String str5, String str6, String str7, String str8) {
        String str9 = str2;
        String str10 = str3;
        CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean3 = compraVentaDolaresCuentaBean;
        CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean4 = compraVentaDolaresCuentaBean2;
        this.mCAmount = new CAmount(str6.replace(".", "").replace(',', '.'));
        this.mCAmount.setSymbolCurrencyDollarOrPeso(false);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_LABEL)).append(": ").append(getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_VALUE, new Object[]{this.mCAmount.getAmount()}));
        spannableStringBuilder.setSpan(new CalligraphyTypefaceSpan(TypefaceUtils.load(getApplicationContext().getAssets(), "fonts/OpenSans-Semibold.otf")), getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_LABEL).length() + 2, spannableStringBuilder.length(), 33);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.generic_black)), getString(R.string.ID3520_COMPRA_VENTA_LBL_COTIZACION_LABEL).length() + 2, spannableStringBuilder.length(), 33);
        this.lbl_data_exchangeRate.setText(spannableStringBuilder, BufferType.SPANNABLE);
        this.lbl_data_receipt_exchangeRate.setText(this.mCAmount.getAmount());
        try {
            this.lbl_data_receipt_exchangeRate.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_receipt_exchangeRate.getText().toString()));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        try {
            this.lbl_data_exchangeRate.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.lbl_data_exchangeRate.getText().toString()));
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
        this.lbl_data_receipt_timeStamp.setText(str);
        this.lbl_data_receipt_destinationAccount.setText(compraVentaDolaresCuentaBean4.getAbreviatureAndAccountFormat(this.mSessionManager));
        this.lbl_data_receipt_originAccount.setText(compraVentaDolaresCuentaBean3.getAbreviatureAndAccountFormat(this.mSessionManager));
        try {
            this.lbl_data_receipt_originAccount.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(compraVentaDolaresCuentaBean3.getAbreviatureAndAccountFormat(this.mSessionManager)));
        } catch (Exception e3) {
            e3.fillInStackTrace();
        }
        try {
            this.lbl_data_receipt_destinationAccount.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(compraVentaDolaresCuentaBean4.getAbreviatureAndAccountFormat(this.mSessionManager)));
        } catch (Exception e4) {
            e4.fillInStackTrace();
        }
        this.lbl_data_receipt_receiptNbr.setText(str9);
        this.lbl_data_receipt_operationNbr.setText(str10);
        try {
            this.lbl_receipt_receiptNbr.setContentDescription(CAccessibility.getInstance(this).applyFilterControlNumber(this.lbl_receipt_receiptNbr.getText().toString()));
            this.lbl_data_receipt_receiptNbr.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(str9));
            this.lbl_receipt_operationNbr.setContentDescription(CAccessibility.getInstance(this).applyFilterControlNumber(this.lbl_receipt_operationNbr.getText().toString()));
            this.lbl_data_receipt_operationNbr.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(str10));
        } catch (Exception e5) {
            e5.fillInStackTrace();
        }
        String format = String.format("<p>%s</p>", new Object[]{str7});
        if (str8 != null) {
            format = String.format("%s<p>%s</p>", new Object[]{format, str8});
        }
        this.lbl_receipt_legals.setText(Html.fromHtml(format));
    }

    public void initialize() {
        this.receiptHasBeenDownloaded = Boolean.valueOf(false);
        Intent intent = getIntent();
        this.t = intent.getStringExtra(Extras.dateTime);
        this.mReceiptNbr = intent.getStringExtra(Extras.receiptNbr);
        this.u = intent.getStringExtra(Extras.operationNbr);
        this.v = (CompraVentaDolaresCuentaBean) intent.getExtras().getParcelable(Extras.originAccount);
        this.w = (CompraVentaDolaresCuentaBean) intent.getExtras().getParcelable(Extras.destinationAccount);
        this.x = intent.getStringExtra(Extras.amountToBeDeposited);
        this.y = intent.getStringExtra(Extras.amountToBeDebited);
        this.z = intent.getStringExtra(Extras.exchangeRate);
        this.A = intent.getStringExtra(Extras.legals);
        this.B = intent.getStringExtra(Extras.legals2);
        this.btn_continuar.setOnClickListener(this);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.q = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        this.r = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        this.q.setOnClickListener(this);
        this.r.setOnClickListener(this);
    }

    public void attachView() {
        if (!this.s.isViewAttached()) {
            this.s.attachView(this);
        }
    }

    public void detachView() {
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
    }

    public void shareReceiptBackAction() {
        if (!this.receiptHasBeenDownloaded.booleanValue()) {
            new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public View getViewToShare() {
                    return BuySellDollarsReceiptActivity.this.lnlReceipt;
                }

                public void receiveIntentAppShare(Intent intent) {
                    BuySellDollarsReceiptActivity.this.startActivity(Intent.createChooser(intent, BuySellDollarsReceiptActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    return String.format(BuySellDollarsReceiptActivity.this.getString(R.string.IDXX_COMPRA_DOLARES_ARCHIVO_JPG_COMPROBANTE), new Object[]{BuySellDollarsReceiptActivity.this.mReceiptNbr});
                }

                public String getSubjectReceiptToShare() {
                    return String.format(BuySellDollarsReceiptActivity.this.getString(R.string.IDXX_COMPRA_DOLARES_ASUNTO_MAIL), new Object[]{BuySellDollarsReceiptActivity.this.mReceiptNbr});
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    BuySellDollarsReceiptActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    BuySellDollarsReceiptActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    BuySellDollarsReceiptActivity.this.receiptHasBeenDownloaded = Boolean.valueOf(true);
                }
            }.showAlert();
        }
    }

    public boolean canExit(int i) {
        if (!this.receiptHasBeenDownloaded.booleanValue()) {
            configureOptionsShare(i);
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.ID3715_COMPRA_VENTA_LBL_AVISO), Html.fromHtml(getString(R.string.MSG_USER000054_exit_without_save)).toString(), null, null, getString(R.string.ID3610_COMPRA_VENTA_BTN_SI), getString(R.string.ID3605_COMPRA_VENTA_BTN_NO), null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    BuySellDollarsReceiptActivity.this.onBackPressed();
                }

                public void onNegativeButton() {
                    BuySellDollarsReceiptActivity.this.sharingOptionsAtExit.onAbortShare();
                }
            });
            newInstance.show(getSupportFragmentManager(), "popupConfirmation");
        }
        return this.receiptHasBeenDownloaded.booleanValue();
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F21_02_BTN_IR_CUENTAS) {
            onBackPressed();
        } else if (id2 == R.id.menu) {
            showSharedOptionActionBarMenu();
        } else if (id2 == R.id.toggle) {
            if (this.receiptHasBeenDownloaded.booleanValue()) {
                switchDrawer();
            } else if (ContextCompat.checkSelfPermission(this.C, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                showRequestPermissionExplation(1);
            } else {
                shareReceiptBackAction();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void showSharedOptionActionBarMenu() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.TITLE_RECEIPT_SHARE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(BuySellDollarsReceiptActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    BuySellDollarsReceiptActivity.this.sharingOptions.optionShareSelected();
                } else if (!str.equalsIgnoreCase(BuySellDollarsReceiptActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                } else {
                    if (ContextCompat.checkSelfPermission(BuySellDollarsReceiptActivity.this.C, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        BuySellDollarsReceiptActivity.this.showRequestPermissionExplation(1);
                    } else {
                        BuySellDollarsReceiptActivity.this.sharingOptions.optionDownloadSelected();
                    }
                }
            }
        });
        newInstance.setCancelable(true);
        newInstance.show(getSupportFragmentManager(), "mPopupMenu");
    }

    public void createDialogExitWithoutSave(String str, String str2) {
        this.D = IsbanDialogFragment.newInstance(str, str2, null, getBaseContext().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_SAVE), getBaseContext().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DONT_DOWNLOAD), getBaseContext().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_SEND));
        this.D.setDialogListenerThreeOptions(this);
    }

    public void onBackPressed() {
        if (this.receiptHasBeenDownloaded.booleanValue()) {
            b();
        } else if (ContextCompat.checkSelfPermission(this.C, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            showRequestPermissionExplation(1);
        } else {
            shareReceiptBackAction();
        }
    }

    public void showRequestPermissionExplation(int i) {
        if (VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i);
        }
    }

    private void b() {
        setResult(-1, new Intent());
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onOption1Button() {
        optionDownloadSelected();
    }

    public void onOption2Button() {
        optionCancelSelected();
    }

    public void onOption3Button() {
        optionShareSelected();
    }
}
