package ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores.TitulosValoresAdapter.ChildClickListener;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores.TitulosValoresContract.Presenter;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores.TitulosValoresContract.View;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail.TitulosValoresDetailActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.TerminosCondicionesActivity;
import ar.com.santander.rio.mbanking.app.ui.utils.CuentasUtils;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.DismissListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Titulo;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.IHorizontalScrollListListener;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.ToggleItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.inject.Inject;

public class TitulosValoresActivity extends BaseMvpActivity implements View, IDialogListenerExtended, IHorizontalScrollListListener {
    private TitulosValoresAdapter A;
    /* access modifiers changed from: private */
    public String B;
    /* access modifiers changed from: private */
    public String C;
    private String D;
    private ArrayList<Cuenta> E;
    private ChildClickListener F = new ChildClickListener() {
        public void onClick(Titulo titulo) {
            Intent intent = new Intent(TitulosValoresActivity.this.getBaseContext(), TitulosValoresDetailActivity.class);
            intent.putExtra("titulo", titulo);
            intent.putExtra("titulo_leyenda", TitulosValoresActivity.this.B);
            intent.putExtra("desc_leyenda", TitulosValoresActivity.this.C);
            intent.putExtra("TIPO_CUENTA", TitulosValoresActivity.this.w);
            TitulosValoresActivity.this.startActivity(intent);
        }
    };
    private Presenter G;
    @Inject
    public AnalyticsManager analyticsManager;
    @InjectView(2131364797)
    ImageView ivError;
    @InjectView(2131364827)
    android.view.View layoutError;
    @InjectView(2131364956)
    LinearLayout layoutOKHeader;
    @InjectView(2131364957)
    LinearLayout layoutOKResult;
    @Inject
    SessionManager p;
    private InversionesAnalyticsImpl q;
    private int r;
    @InjectView(2131365505)
    RecyclerView rvTitulosValores;
    private ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta s;
    @InjectView(2131365637)
    NestedScrollView scrollView;
    @InjectView(2131365755)
    CustomSpinner selectorAccount;
    private Cuenta t;
    @InjectView(2131365806)
    HorizontalScrollList tabSelector;
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
    /* access modifiers changed from: private */
    public int w;
    private String x;
    private String y;
    private String z;

    public void OnCheckedChangeListener(List<ToggleItem> list) {
    }

    public void attachView() {
    }

    public void changeStateSelectorAccount(Boolean bool) {
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
        setContentView((int) R.layout.activity_titulos_valores);
        ButterKnife.inject((Activity) this);
        this.q = new InversionesAnalyticsImpl(this, this.analyticsManager);
        initialize();
        configureActionBar();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (this.w < 0) {
            this.analyticsManager.setScreenName(getString(R.string.analytics_tenencia_inversiones_screean_name_titulos_valores_rtl));
        } else {
            this.analyticsManager.setScreenName(getString(R.string.analytics_tenencia_inversiones_screean_name_titulos_valores_bp));
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.G.detachView();
        super.onDestroy();
    }

    public void initialize() {
        TitulosValoresPresenter titulosValoresPresenter = new TitulosValoresPresenter(this.mBus, this.mDataManager, this, this.p, this.analyticsManager);
        this.G = titulosValoresPresenter;
        this.G.attachView(this);
        setTab();
        b();
        getExtras();
        this.selectorAccount.setWrapperArrow(R.drawable.arrow_down);
        this.tvFunctionalityTitle.setText(R.string.ID_4319_INVERSIONES_LBL_TITULOS_VALORES);
        this.scrollView.post(new Runnable() {
            public void run() {
                TitulosValoresActivity.this.scrollView.scrollTo(0, 0);
            }
        });
    }

    private void b() {
        this.A = new TitulosValoresAdapter(this, this.F);
        this.rvTitulosValores.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.rvTitulosValores.setAdapter(this.A);
    }

    public void getExtras() {
        this.r = getIntent().getIntExtra("IDX_CUENTA", 0);
        this.u = getIntent().getStringExtra("TIPO_CLIENTE");
        this.v = getIntent().getIntExtra("NRO_CUENTA", 0);
        this.w = getIntent().getIntExtra("SUC_CUENTA", -1);
        this.x = getIntent().getStringExtra("TIPO_CUENTA");
        this.E = getIntent().getParcelableArrayListExtra("CUENTAS");
        this.t = (Cuenta) this.E.get(CuentasUtils.getAssoiatedLoginAccountIndex(this.E, this.v));
        hideSelector();
        this.G.getTitulosValores(this.x, this.w, this.v, this.u);
    }

    public void setTab() {
        this.y = getResources().getString(R.string.ID_4314_INVERSIONES_BTN_PESOS);
        this.z = getResources().getString(R.string.TEXT_NAME_DOLLARS);
        this.tabSelector.removeItems();
        this.tabSelector.addItem(this.y, true, (int) R.string.ID_4314_INVERSIONES_BTN_PESOS);
        this.tabSelector.addItem(this.z, false, (int) R.string.ID_4315_INVERSIONES_BTN_DOLARES);
        this.tabSelector.setHorizontalScrollListener(this);
        this.tabSelector.show();
    }

    public void callTitulosValores() {
        this.G.getTitulosValores(this.x, this.w, this.v, this.u);
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
                    TitulosValoresActivity.this.finish();
                    TitulosValoresActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }

    public void setTitulosValores(ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta cuenta) {
        this.s = cuenta;
        this.t = (Cuenta) this.E.get(CuentasUtils.getAssoiatedLoginAccountIndex(this.E, this.v));
        this.layoutOKHeader.setVisibility(0);
        try {
            this.tvTitle.setText(Html.fromHtml(this.t.getFormattedSucCuentaLarge()));
            this.tvLabelTitular.setText(cuenta.getIntervinientes());
            this.tvLabelValuePesos.setText(cuenta.getTotalPesos());
            this.tvLabelValueDolar.setText(cuenta.getTotalDolares());
        } catch (Exception unused) {
            errorResult();
        }
    }

    public void setRecyclerData(LinkedHashMap<String, List<Titulo>> linkedHashMap) {
        this.layoutOKResult.setVisibility(0);
        this.layoutError.setVisibility(8);
        this.rvTitulosValores.removeAllViewsInLayout();
        this.A.setTitulosHashMap(linkedHashMap);
    }

    public void setErrorTitulosValores() {
        this.layoutError.setVisibility(0);
        this.layoutOKResult.setVisibility(8);
        this.tvTitleError.setText(R.string.ERROR_NO_HAY_TENENCIA);
        this.tvTitleError.setTextSize(25.0f);
        this.ivError.setImageResource(R.drawable.error_empty_fetch);
    }

    public void setLeyenda(String str, final String str2) {
        this.B = str;
        this.C = str2;
        this.tvVerTerminosCondiciones.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View view) {
                Intent intent = new Intent(TitulosValoresActivity.this.getBaseContext(), TerminosCondicionesActivity.class);
                intent.putExtra(TerminosCondicionesActivity.TITULO_TERMINO_TAG, "Términos y Condiciones");
                intent.putExtra(TerminosCondicionesActivity.DESC_TERMINO_TAG, str2);
                TitulosValoresActivity.this.startActivity(intent);
            }
        });
    }

    public void okResult() {
        this.layoutError.setVisibility(8);
        this.layoutOKHeader.setVisibility(0);
        this.tabSelector.setVisibility(0);
        this.layoutOKResult.setVisibility(0);
        this.rvTitulosValores.removeAllViewsInLayout();
    }

    public void errorResult() {
        this.layoutError.setVisibility(0);
        this.layoutOKResult.setVisibility(8);
        this.t = (Cuenta) this.E.get(CuentasUtils.getAssoiatedLoginAccountIndex(this.E, this.v));
        this.tvTitle.setText(Html.fromHtml(this.t.getFormattedSucCuentaLarge()));
        this.tvLabelTitular.setText("-");
        this.tvLabelValuePesos.setText("$ 0,00");
        this.tvLabelValueDolar.setText("U$S 0,00");
        setErrorTitulosValores();
    }

    public void setPartialErrorMsg(String str, int i) {
        this.D = str;
        this.viewParcialError.setVisibility(i);
        this.tvSectionMsjError.setText(Html.fromHtml(this.D));
    }

    public void unknowError() {
        setErrorView(getString(R.string.error_servicios_title), getString(R.string.error_servicios_label));
        this.q.TenenciaTitulosValoresErrortotal();
    }

    public void hideSelector() {
        if (CuentasUtils.getCuentasBPCuentasRTLNumber(this.E) < 2) {
            this.selectorAccount.setVisibility(8);
        } else {
            this.selectorAccount.setVisibility(0);
        }
    }

    @OnClick({2131365755})
    public void onChangeCuentaListener() {
        b(false);
        try {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Iterator it = this.E.iterator();
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
                        TitulosValoresActivity.this.b(true);
                    }
                });
            }
        } catch (Exception unused2) {
            b(true);
        }
    }

    /* access modifiers changed from: private */
    public void b(boolean z2) {
        StringBuilder sb = new StringBuilder();
        sb.append("changeStateSelectorAccount: ");
        sb.append(this.selectorAccount);
        sb.append(" isEnabled: ");
        sb.append(z2);
        Log.i("selectorAccount", sb.toString());
        if (this.selectorAccount != null) {
            this.selectorAccount.setEnabled(z2);
        }
    }

    public void onItemSelected(String str, String str2) {
        Log.i("ItemSelected", str);
        this.v = CuentasUtils.getCuentaFromString(str);
        this.w = CuentasUtils.getSucCuentaFromString(str);
        callTitulosValores();
        setTab();
    }

    public void OnNewItemSelected(ToggleItem toggleItem) {
        this.G.getRecyclerData(toggleItem.getLabel());
    }

    public void setErrorView(String str, String str2) {
        this.layoutError.setVisibility(0);
        this.layoutOKResult.setVisibility(8);
        this.layoutOKHeader.setVisibility(8);
        this.tabSelector.setVisibility(8);
        this.t = (Cuenta) this.E.get(CuentasUtils.getAssoiatedLoginAccountIndex(this.E, this.v));
        this.tvTitle.setText(Html.fromHtml(this.t.getFormattedSucCuentaLarge()));
        this.tvTitleError.setText(str);
        this.tvTitleError.setTextSize(25.0f);
        this.ivError.setImageResource(R.drawable.error_continuacion);
        this.tvDescriptionError.setText(str2);
    }
}
