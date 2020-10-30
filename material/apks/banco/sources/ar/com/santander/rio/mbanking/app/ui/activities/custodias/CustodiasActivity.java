package ar.com.santander.rio.mbanking.app.ui.activities.custodias;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.ui.activities.custodiadetalles.CustodiaDetallesActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.custodias.CustodiasAdapter.ChildClickListener;
import ar.com.santander.rio.mbanking.app.ui.activities.custodias.CustodiasContract.View;
import ar.com.santander.rio.mbanking.app.ui.activities.custodiaterminos.CustodiaTerminosActivity;
import ar.com.santander.rio.mbanking.app.ui.utils.CuentasUtils;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.DismissListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Custodia;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.IHorizontalScrollListListener;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.ToggleItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class CustodiasActivity extends BaseMvpActivity implements View, IDialogListenerExtended, IHorizontalScrollListListener {
    public static final String CUENTAS_TAG = "CUENTAS";
    public static final String IDX_CUENTA_TAG = "IDX_CUENTA";
    public static final String NRO_CUENTA_TAG = "NRO_CUENTA";
    public static final String SUC_CUENTA_TAG = "SUC_CUENTA";
    public static final String TIPO_CLIENTE_TAG = "TIPO_CLIENTE";
    public static final String TIPO_CUENTA_TAG = "TIPO_CUENTA";
    private String A;
    /* access modifiers changed from: private */
    public String B;
    private CustodiasAdapter C;
    private ArrayList<Cuenta> D;
    private CustodiasPresenter E;
    private InversionesAnalyticsImpl F;
    @Inject
    public AnalyticsManager analyticsManager;
    @InjectView(2131364797)
    ImageView ivError;
    @InjectView(2131364827)
    android.view.View layoutError;
    @InjectView(2131364976)
    LinearLayout layoutOKHeader;
    @InjectView(2131364957)
    LinearLayout layoutOKResult;
    @Inject
    SessionManager p;
    private ChildClickListener q = new ChildClickListener() {
        public void onClick(Custodia custodia) {
            Intent intent = new Intent(CustodiasActivity.this, CustodiaDetallesActivity.class);
            intent.putExtra(CustodiaDetallesActivity.CUSTODIA_DETALLE_TAG, custodia);
            intent.putExtra("TERMINOS_CONDICIONES", CustodiasActivity.this.B);
            CustodiasActivity.this.startActivity(intent);
        }
    };
    private int r;
    @InjectView(2131365503)
    RecyclerView rvCustodias;
    private ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta s;
    @InjectView(2131365755)
    CustomSpinner selectorAccount;
    private Cuenta t;
    @InjectView(2131365806)
    HorizontalScrollList tabSelector;
    @InjectView(2131366217)
    TextView tvCustodiaDolaresLabel1;
    @InjectView(2131366218)
    TextView tvCustodiaDolaresLabel2;
    @InjectView(2131366219)
    TextView tvCustodiaDolaresLabel3;
    @InjectView(2131366221)
    TextView tvCustodiaPesosLabel1;
    @InjectView(2131366222)
    TextView tvCustodiaPesosLabel2;
    @InjectView(2131366223)
    TextView tvCustodiaPesosLabel3;
    @InjectView(2131364597)
    TextView tvDescriptionError;
    @InjectView(2131364716)
    TextView tvFunctionalityTitle;
    @InjectView(2131366114)
    TextView tvLabelTitular;
    @InjectView(2131366116)
    TextView tvLabelValueDolar;
    @InjectView(2131366117)
    TextView tvLabelValuePesos;
    @InjectView(2131365664)
    TextView tvSectionMsjError;
    @InjectView(2131366421)
    TextView tvTitle;
    @InjectView(2131366044)
    TextView tvTitleError;
    @InjectView(2131366262)
    TextView tvVerTerminosCondiciones;
    private String u;
    private int v;
    @InjectView(2131366418)
    android.view.View viewParcialError;
    private int w;
    private String x;
    private String y;
    private String z;

    public void OnCheckedChangeListener(List<ToggleItem> list) {
    }

    public void attachView() {
    }

    public void configureLayout() {
    }

    public void detachView() {
    }

    public void onNegativeButton(String str) {
    }

    public void onPositiveButton(String str) {
    }

    public void onSimpleActionButton(String str) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_custodia_monetaria);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        this.F = new InversionesAnalyticsImpl(this, this.analyticsManager);
    }

    public void hideSelector() {
        if (CuentasUtils.getCuentasBPCuentasRTLNumber(this.D) < 2) {
            this.selectorAccount.setVisibility(8);
        }
    }

    public void setCustodia(ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta cuenta) {
        this.s = cuenta;
        this.t = (Cuenta) this.D.get(CuentasUtils.getAssoiatedLoginAccountIndex(this.D, this.v));
        this.layoutOKHeader.setVisibility(0);
        try {
            this.tvTitle.setText(Html.fromHtml(this.t.getFormattedSucCuentaLarge()));
            this.tvLabelTitular.setText(cuenta.getIntervinientes());
            this.tvLabelValuePesos.setText(cuenta.getTotalPesos());
            this.tvLabelValueDolar.setText(cuenta.getTotalDolares());
            this.tvCustodiaDolaresLabel1.setText(cuenta.getTenReexpresada().getPesos().getExpreDolares());
            this.tvCustodiaDolaresLabel2.setText(cuenta.getTenReexpresada().getPesos().getTotalDolares());
            this.tvCustodiaDolaresLabel3.setText(cuenta.getTenReexpresada().getPesos().getTotalExpreDolares());
            this.tvCustodiaPesosLabel1.setText(cuenta.getTenReexpresada().getDolares().getExprePesos());
            this.tvCustodiaPesosLabel2.setText(cuenta.getTenReexpresada().getDolares().getTotalPesos());
            this.tvCustodiaPesosLabel3.setText(cuenta.getTenReexpresada().getDolares().getTotalExprePesos());
        } catch (Exception unused) {
            this.tvSectionMsjError.setText(this.A);
            errorResult();
        }
        b();
    }

    private void b() {
        try {
            if (a(this.t.getSucursalPaq())) {
                this.F.TenenciaCustodiabp();
            } else {
                this.F.TenenciaCustodiartl();
            }
        } catch (Exception unused) {
        }
    }

    private boolean a(String str) {
        return Integer.valueOf(str).intValue() >= 250 && Integer.valueOf(str).intValue() <= 259;
    }

    public void setRecyclerData(List<Custodia> list) {
        this.layoutOKResult.setVisibility(0);
        this.layoutError.setVisibility(8);
        this.rvCustodias.removeAllViewsInLayout();
        this.C.setCustodia(list);
    }

    public void initialize() {
        CustodiasPresenter custodiasPresenter = new CustodiasPresenter(this.mBus, this.mDataManager, this, this.p, this.analyticsManager);
        this.E = custodiasPresenter;
        this.E.attachView(this);
        f();
        e();
        c();
        this.selectorAccount.setWrapperArrow(R.drawable.arrow_down);
        this.tvFunctionalityTitle.setText(R.string.ID_4939_INVERSIONES_LBL_CUSTODIA_MONETARIA);
    }

    private void c() {
        this.r = getIntent().getIntExtra("IDX_CUENTA", 0);
        this.u = getIntent().getStringExtra("TIPO_CLIENTE");
        this.v = getIntent().getIntExtra("NRO_CUENTA", 0);
        this.w = getIntent().getIntExtra("SUC_CUENTA", -1);
        this.x = getIntent().getStringExtra("TIPO_CUENTA");
        this.D = getIntent().getParcelableArrayListExtra("CUENTAS");
        this.t = (Cuenta) this.D.get(CuentasUtils.getAssoiatedLoginAccountIndex(this.D, this.v));
        hideSelector();
        d();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.E.detachView();
        super.onDestroy();
    }

    private void d() {
        this.E.getCustodia(this.x, this.w, this.v, this.u);
    }

    private void e() {
        this.C = new CustodiasAdapter(this.q);
        this.rvCustodias.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.rvCustodias.setAdapter(this.C);
    }

    private void f() {
        this.y = getResources().getString(R.string.TEXT_NAME_PESOS);
        this.z = getResources().getString(R.string.ID_4940_INVERSIONES_OTRAS_MONEDAS);
        this.tabSelector.removeItems();
        this.tabSelector.addItem(this.y, true, (int) R.string.ID_4314_INVERSIONES_BTN_PESOS);
        this.tabSelector.addItem(this.z, false, (int) R.string.ID_4940_INVERSIONES_OTRAS_MONEDAS);
        this.tabSelector.setHorizontalScrollListener(this);
        this.tabSelector.show();
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        enableBackButton();
    }

    public void enableBackButton() {
        android.view.View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(android.view.View view) {
                    CustodiasActivity.this.finish();
                    CustodiasActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }

    public void OnNewItemSelected(ToggleItem toggleItem) {
        this.E.getRecyclerData(toggleItem.getLabel());
    }

    @OnClick({2131365755})
    public void onChangeCuentaListener() {
        b(false);
        try {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Iterator it = this.D.iterator();
            while (it.hasNext()) {
                Cuenta cuenta = (Cuenta) it.next();
                if (cuenta.getNroSucInt() == 0 || (cuenta.getNroSucInt() >= 250 && cuenta.getNroSucInt() <= 259)) {
                    arrayList.add(cuenta.getFormattedSucCuentaLarge());
                    try {
                        arrayList2.add(new CAccessibility(this).applyFilterAccount(cuenta.getFormattedSucCuentaLarge()));
                    } catch (Exception unused) {
                    }
                }
            }
            if (arrayList.size() > 1) {
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_cuenta", "Seleccionar Cuenta", null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, this.t.getFormattedSucCuentaLarge(), arrayList2);
                newInstance.setDialogListenerExtended(this);
                newInstance.setCancelable(true);
                newInstance.show(getSupportFragmentManager(), "Dialog");
                newInstance.setDismissListener(new DismissListener() {
                    public void onIsbanDismiss() {
                        CustodiasActivity.this.b(true);
                    }
                });
            }
        } catch (Exception unused2) {
            b(true);
        }
    }

    /* access modifiers changed from: private */
    public void b(boolean z2) {
        if (this.selectorAccount != null) {
            this.selectorAccount.setEnabled(z2);
        }
    }

    public void onItemSelected(String str, String str2) {
        f();
        this.v = CuentasUtils.getCuentaFromString(str);
        this.w = CuentasUtils.getSucCuentaFromString(str);
        d();
    }

    public void okResult() {
        this.layoutError.setVisibility(8);
        this.layoutOKResult.setVisibility(0);
        this.layoutOKHeader.setVisibility(0);
        this.tabSelector.setVisibility(0);
        this.rvCustodias.removeAllViewsInLayout();
    }

    public void errorResult() {
        this.layoutError.setVisibility(0);
        this.layoutOKResult.setVisibility(8);
        this.t = (Cuenta) this.D.get(CuentasUtils.getAssoiatedLoginAccountIndex(this.D, this.v));
        this.tvTitle.setText(Html.fromHtml(this.t.getFormattedSucCuentaLarge()));
        this.tvLabelTitular.setText("-");
        this.tvLabelValuePesos.setText("$ 0,00");
        this.tvLabelValueDolar.setText("U$S 0,00");
        setErrorCustodias();
    }

    public void setErrorCustodias() {
        this.layoutError.setVisibility(0);
        this.layoutOKResult.setVisibility(8);
        this.tvTitleError.setText(R.string.ERROR_NO_HAY_TENENCIA);
        this.tvTitleError.setTextSize(25.0f);
        this.ivError.setImageResource(R.drawable.error_empty_fetch);
    }

    public void setLeyenda(String str, final String str2) {
        this.B = str2;
        this.tvVerTerminosCondiciones.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View view) {
                Intent intent = new Intent(CustodiasActivity.this.getBaseContext(), CustodiaTerminosActivity.class);
                intent.putExtra("TERMINOS_CONDICIONES", str2);
                CustodiasActivity.this.startActivity(intent);
            }
        });
    }

    public void setPartialErrorMsg(String str, int i) {
        this.A = str;
        this.viewParcialError.setVisibility(i);
        this.tvSectionMsjError.setText(Html.fromHtml(this.A));
    }

    public void unknowError() {
        setErrorView(getString(R.string.error_servicios_title), getString(R.string.error_servicios_label));
        this.F = new InversionesAnalyticsImpl(this, this.analyticsManager);
        this.F.TenenciaCustodiaErrorTotal();
    }

    public void setErrorView(String str, String str2) {
        this.layoutError.setVisibility(0);
        this.layoutOKResult.setVisibility(8);
        this.layoutOKHeader.setVisibility(8);
        this.tabSelector.setVisibility(8);
        this.t = (Cuenta) this.D.get(CuentasUtils.getAssoiatedLoginAccountIndex(this.D, this.v));
        this.tvTitle.setText(Html.fromHtml(this.t.getFormattedSucCuentaLarge()));
        this.tvTitleError.setText(str);
        this.tvTitleError.setTextSize(25.0f);
        this.ivError.setImageResource(R.drawable.error_continuacion);
        this.tvDescriptionError.setText(str2);
    }
}
