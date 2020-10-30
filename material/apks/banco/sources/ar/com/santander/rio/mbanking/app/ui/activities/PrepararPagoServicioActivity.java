package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.nuevopago.PrepararPagoServicioPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevopago.PrepararPagoServicioView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Iterator;
import javax.inject.Inject;

public abstract class PrepararPagoServicioActivity extends BaseMvpActivity implements OnClickListener, PrepararPagoServicioView {
    @InjectView(2131361848)
    TextView btnContinuar;
    @InjectView(2131361850)
    EditText inpCUR;
    @InjectView(2131361849)
    EditText inpCuitEmpleador;
    @InjectView(2131361851)
    EditText inpDinamico1;
    @InjectView(2131361852)
    EditText inpDinamico2;
    @InjectView(2131361853)
    EditText inpDinamico4;
    @InjectView(2131361854)
    EditText inpImporte;
    @InjectView(2131361855)
    EditText inpInfoAdicional;
    @InjectView(2131361857)
    TextView lblCUITEmpleado;
    @InjectView(2131361858)
    TextView lblCUITEmpleador;
    @InjectView(2131361873)
    TextView lblDatosAdicionales;
    @InjectView(2131361874)
    TextView lblDinamico1;
    @InjectView(2131361875)
    TextView lblDinamico2;
    @InjectView(2131361876)
    TextView lblDinamico3;
    @InjectView(2131361877)
    TextView lblDinamico4;
    protected CuentaDebitoBean mCuentaSeleccionada;
    protected ArrayList<CuentaDebitoBean> mCuentas;
    protected ArrayList<CuentaDebitoBean> mCuentasDolares;
    protected ArrayList<CuentaDebitoBean> mCuentasPesos;
    protected DatosDeudaBean mDeuda;
    protected String mOrigen;
    @Inject
    SessionManager p;
    protected PrepararPagoServicioPresenter prepararPagoPresenter;
    ImageView q;
    IsbanDialogFragment r;
    @InjectView(2131361888)
    LinearLayout rowAnticipoCuota;
    @InjectView(2131361889)
    LinearLayout rowCUITempleado;
    @InjectView(2131361890)
    LinearLayout rowCUITempleador;
    @InjectView(2131361891)
    LinearLayout rowCUR;
    @InjectView(2131361892)
    LinearLayout rowDatosAdicionales;
    @InjectView(2131361893)
    LinearLayout rowDinamico1;
    @InjectView(2131361894)
    LinearLayout rowDinamico2;
    @InjectView(2131361895)
    LinearLayout rowDinamico3;
    @InjectView(2131361896)
    LinearLayout rowDinamico4;
    @InjectView(2131361897)
    LinearLayout rowFactura;
    @InjectView(2131361898)
    LinearLayout rowIdentificador;
    @InjectView(2131361899)
    LinearLayout rowInformacionAdicional;
    @InjectView(2131361900)
    LinearLayout rowPeriodo;
    @InjectView(2131361901)
    LinearLayout rowVEP;
    FragmentManager s;
    @InjectView(2131361860)
    TextView txtAnticipoCuota;
    @InjectView(2131361861)
    TextView txtCuitEmpleado;
    @InjectView(2131361862)
    TextView txtCuitEmpleador;
    @InjectView(2131361863)
    TextView txtDatosAdicionales;
    @InjectView(2131361864)
    TextView txtDinamico3;
    @InjectView(2131361865)
    TextView txtEmpresa;
    @InjectView(2131361866)
    TextView txtFactura;
    @InjectView(2131361867)
    TextView txtIdentificador;
    @InjectView(2131361868)
    TextView txtImporte;
    @InjectView(2131361869)
    TextView txtMedioPago;
    @InjectView(2131361870)
    TextView txtPeriodo;
    @InjectView(2131361871)
    TextView txtVencimiento;
    @InjectView(2131361872)
    TextView txtVep;

    public void clearScreenData() {
    }

    /* access modifiers changed from: protected */
    public abstract int isDataValid();

    public void initialize() {
        this.mOrigen = getIntent().getStringExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN);
        this.mCuentas = getIntent().getParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS);
        this.mDeuda = (DatosDeudaBean) getIntent().getParcelableExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA);
        this.mCuentasPesos = new ArrayList<>();
        this.mCuentasDolares = new ArrayList<>();
        if (this.mCuentas != null && this.mCuentas.size() > 0) {
            Iterator it = this.mCuentas.iterator();
            while (it.hasNext()) {
                CuentaDebitoBean cuentaDebitoBean = (CuentaDebitoBean) it.next();
                if (this.mDeuda.moneda.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR) && (cuentaDebitoBean.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CTA_CTE_PESOS) || cuentaDebitoBean.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_PESOS) || cuentaDebitoBean.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS))) {
                    this.mCuentasPesos.add(cuentaDebitoBean);
                } else if (this.mDeuda.moneda.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLLAR_STR) && (cuentaDebitoBean.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CTA_CTE_DOLAR) || cuentaDebitoBean.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_DOLAR) || cuentaDebitoBean.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR))) {
                    this.mCuentasDolares.add(cuentaDebitoBean);
                }
            }
        }
        if (this.mDeuda.moneda.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR)) {
            if (this.mCuentasPesos.size() >= 2) {
                this.txtMedioPago.setOnClickListener(this);
                this.txtMedioPago.setText(formatMedioPago(getString(R.string.F24_20_lbl_data_seleccionar)));
                this.txtMedioPago.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf"));
                this.txtMedioPago.setTextColor(getResources().getColor(R.color.generic_black));
                this.txtMedioPago.setTextSize(13.0f);
                this.txtMedioPago.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
                this.txtMedioPago.setCompoundDrawablePadding(Utils.dpToPx(5, getApplicationContext()));
            } else if (this.mCuentasPesos.size() == 1) {
                this.txtMedioPago.setText(formatMedioPago(((CuentaDebitoBean) this.mCuentasPesos.get(0)).getDescCtaDebito()));
                this.txtMedioPago.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf"));
                this.txtMedioPago.setTextColor(getResources().getColor(R.color.generic_black));
                this.txtMedioPago.setTextSize(13.0f);
                this.mCuentaSeleccionada = (CuentaDebitoBean) this.mCuentasPesos.get(0);
            } else {
                this.txtMedioPago.setText(getString(R.string.ID94_ACCOUNTS_CHANGEACC_LBL_SELECT));
                this.txtMedioPago.setTextColor(getResources().getColor(R.color.generic_black));
            }
        } else if (this.mDeuda.moneda.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLLAR_STR)) {
            if (this.mCuentasDolares.size() >= 2) {
                this.txtMedioPago.setOnClickListener(this);
                this.txtMedioPago.setText(formatMedioPago(getString(R.string.F24_20_lbl_data_seleccionar)));
                this.txtMedioPago.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf"));
                this.txtMedioPago.setTextColor(getResources().getColor(R.color.generic_black));
                this.txtMedioPago.setTextSize(13.0f);
                this.txtMedioPago.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
                this.txtMedioPago.setCompoundDrawablePadding(Utils.dpToPx(5, getApplicationContext()));
            } else if (this.mCuentasDolares.size() == 1) {
                this.txtMedioPago.setText(formatMedioPago(((CuentaDebitoBean) this.mCuentasDolares.get(0)).getDescCtaDebito()));
                this.txtMedioPago.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Semibold.otf"));
                this.txtMedioPago.setTextColor(getResources().getColor(R.color.generic_black));
                this.txtMedioPago.setTextSize(13.0f);
                this.mCuentaSeleccionada = (CuentaDebitoBean) this.mCuentasDolares.get(0);
            }
        }
        this.btnContinuar.setOnClickListener(this);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.q = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.q.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PrepararPagoServicioActivity.this.onBackPressed();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_preparar_pago_servicio);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        this.prepararPagoPresenter = new PrepararPagoServicioPresenter(this.mBus, this.mDataManager);
        this.prepararPagoPresenter.attachView(this);
        this.prepararPagoPresenter.showPrepararPago();
    }

    public void attachView() {
        if (!this.prepararPagoPresenter.isViewAttached()) {
            this.prepararPagoPresenter.attachView(this);
        }
    }

    public void detachView() {
        if (this.prepararPagoPresenter.isViewAttached()) {
            this.prepararPagoPresenter.detachView();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        setResult(0);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showPrepararPago() {
        this.txtEmpresa.setText(this.mDeuda.datosEmpresa.empDescr);
        this.txtVencimiento.setText(this.mDeuda.vencimiento);
        this.txtFactura.setText(this.mDeuda.factura);
        this.txtIdentificador.setText(this.mDeuda.identificacion);
        this.inpInfoAdicional.setText(this.mDeuda.infoAdicional);
        this.txtMedioPago.setText(formatMedioPago(this.mCuentaSeleccionada.getDescCtaDebito()));
        try {
            this.txtMedioPago.setContentDescription(formatAccessibilityMedioPago(this.mCuentaSeleccionada.getDescCtaDebito()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public String formatMedioPago(String str) {
        String replaceAll = str.replaceAll("\n", UtilsCuentas.SEPARAOR2);
        String[] split = replaceAll.split("/");
        if (split.length < 2) {
            return replaceAll;
        }
        String[] split2 = split[1].split(UtilsCuentas.SEPARAOR2);
        StringBuilder sb = new StringBuilder();
        sb.append(split[0]);
        sb.append("/");
        sb.append(split2[0]);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(split2[1]);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(split2[2]);
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append("\n");
        sb5.append(sb4);
        return sb5.toString();
    }

    /* access modifiers changed from: protected */
    public String formatAccessibilityMedioPago(String str) {
        String[] split = str.split("/");
        if (split.length < 2) {
            return str;
        }
        String[] split2 = split[1].split(UtilsCuentas.SEPARAOR2);
        try {
            CAccessibility instance = CAccessibility.getInstance(getApplicationContext());
            StringBuilder sb = new StringBuilder();
            sb.append(split[0]);
            sb.append("/");
            sb.append(split2[0]);
            String applyFilterAccount = instance.applyFilterAccount(sb.toString());
            CAccessibility instance2 = CAccessibility.getInstance(getApplicationContext());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(split2[1]);
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(split2[2]);
            String applyFilterAmount = instance2.applyFilterAmount(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(applyFilterAccount);
            sb3.append("\n");
            sb3.append(applyFilterAmount);
            return sb3.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /* access modifiers changed from: protected */
    public void showAccountSelector() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mCuentas.size(); i++) {
            if (this.mDeuda.moneda.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS_STR) && (((CuentaDebitoBean) this.mCuentas.get(i)).getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CTA_CTE_PESOS) || ((CuentaDebitoBean) this.mCuentas.get(i)).getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_PESOS) || ((CuentaDebitoBean) this.mCuentas.get(i)).getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS))) {
                arrayList.add(((CuentaDebitoBean) this.mCuentas.get(i)).descCtaDebito);
            } else if (this.mDeuda.moneda.equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLLAR_STR) && (((CuentaDebitoBean) this.mCuentas.get(i)).getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CTA_CTE_DOLAR) || ((CuentaDebitoBean) this.mCuentas.get(i)).getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_DOLAR) || ((CuentaDebitoBean) this.mCuentas.get(i)).getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR))) {
                arrayList.add(((CuentaDebitoBean) this.mCuentas.get(i)).descCtaDebito);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCIONAR));
        sb.append(" Cuenta");
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(sb.toString(), null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                int i = 0;
                while (true) {
                    if (i >= PrepararPagoServicioActivity.this.mCuentas.size()) {
                        break;
                    } else if (((CuentaDebitoBean) PrepararPagoServicioActivity.this.mCuentas.get(i)).getDescCtaDebito().equalsIgnoreCase(str)) {
                        PrepararPagoServicioActivity.this.mCuentaSeleccionada = (CuentaDebitoBean) PrepararPagoServicioActivity.this.mCuentas.get(i);
                        break;
                    } else {
                        i++;
                    }
                }
                PrepararPagoServicioActivity.this.txtMedioPago.setText(PrepararPagoServicioActivity.this.formatMedioPago(PrepararPagoServicioActivity.this.mCuentaSeleccionada.getDescCtaDebito()));
                try {
                    PrepararPagoServicioActivity.this.txtMedioPago.setContentDescription(PrepararPagoServicioActivity.this.formatAccessibilityMedioPago(PrepararPagoServicioActivity.this.mCuentaSeleccionada.getDescCtaDebito()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    public Boolean isDinamicFieldValid(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        int i;
        int i2;
        try {
            if (TextUtils.isEmpty(str)) {
                return Boolean.valueOf(false);
            }
            if (!str2.equalsIgnoreCase("M")) {
                if (!str2.equalsIgnoreCase("M")) {
                    if (!str3.equalsIgnoreCase("2")) {
                        if (str3.equalsIgnoreCase("3")) {
                        }
                    }
                }
                if (!str2.equalsIgnoreCase("M") && ((str3.equalsIgnoreCase("1") || str3.equalsIgnoreCase("5")) && (Integer.valueOf(str).intValue() < Integer.valueOf(str4).intValue() || Integer.valueOf(str).intValue() > Integer.valueOf(str5).intValue()))) {
                    return Boolean.valueOf(false);
                }
                return Boolean.valueOf(true);
            }
            String[] split = str.split("-");
            if (split.length == 2) {
                i2 = Integer.valueOf(split[0]).intValue();
                i = Integer.valueOf(split[1]).intValue();
            } else if (split.length != 1) {
                return Boolean.valueOf(false);
            } else {
                int intValue = Integer.valueOf(str.substring(0, 2)).intValue();
                i = Integer.valueOf(str.substring(2)).intValue();
                i2 = intValue;
            }
            if (i2 >= Integer.valueOf(str4).intValue() && i2 <= Integer.valueOf(str5).intValue() && i >= Integer.valueOf(str6).intValue()) {
                if (i > Integer.valueOf(str7).intValue()) {
                }
                return Boolean.valueOf(true);
            }
            return Boolean.valueOf(false);
        } catch (Exception unused) {
            return Boolean.valueOf(false);
        }
    }

    public Boolean isCurValid(String str) {
        boolean z = false;
        int i = 0;
        int i2 = 0;
        while (i < str.length() - 1) {
            i++;
            i2 += Integer.valueOf(Character.valueOf(str.charAt(i)).toString()).intValue() * i;
        }
        if (i2 % 10 == Integer.valueOf(str.substring(str.length() - 1)).intValue()) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F06_02_BTN_CONTINUAR) {
            switch (isDataValid()) {
                case 0:
                    showConfirmarPago();
                    return;
                case 1:
                    this.r = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.IDxxxx_F06_XX_MSG_MONTO_MAYOR_CERO), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                    this.s = getSupportFragmentManager();
                    this.r.show(this.s, "Dialog");
                    return;
                case 2:
                    this.r = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.IDxxxx_F06_XX_MSG_MONTO_MAYOR_IMPORTE_INFORMADO), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                    this.s = getSupportFragmentManager();
                    this.r.show(this.s, "Dialog");
                    return;
                case 3:
                    this.r = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.IDxxxx_F06_XX_MSG_CUIT_INVALIDO), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                    this.s = getSupportFragmentManager();
                    this.r.show(this.s, "Dialog");
                    return;
                case 4:
                    this.r = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.IDxxxx_F06_XX_MSG_CUR_INVALIDO), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                    this.s = getSupportFragmentManager();
                    this.r.show(this.s, "Dialog");
                    return;
                case 5:
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.mDeuda.datosAdicionales.leyenda);
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(getString(R.string.IDxxxx_F06_XX_MSG_DINAMICO_INVALIDO));
                    this.r = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), sb.toString(), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                    this.s = getSupportFragmentManager();
                    this.r.show(this.s, "Dialog");
                    return;
                case 6:
                    this.r = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.TEXT_MUST_SELECTED_ACCOUNT), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                    this.s = getSupportFragmentManager();
                    this.r.show(this.s, "Dialog");
                    return;
                default:
                    return;
            }
        } else if (id2 == R.id.F06_02_LBL_DATA_MEDIO_PAGO) {
            showAccountSelector();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1 && i2 == -1) {
            Intent intent2 = new Intent();
            if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                    intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                }
            } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
            }
            intent2.putExtra("recargarHome", true);
            setResult(-1, intent2);
            finish();
        }
    }
}
