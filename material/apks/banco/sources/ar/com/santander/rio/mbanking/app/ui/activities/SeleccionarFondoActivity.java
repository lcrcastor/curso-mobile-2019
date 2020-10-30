package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.SeleccionarFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.SeleccionarFondoView;
import ar.com.santander.rio.mbanking.app.ui.adapters.SeleccionarFondoAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.SeleccionarFondoAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SeleccionarFondoActivity extends BaseMvpActivity implements OnClickListener, SeleccionarFondoView, OnItemClickListener {
    @InjectView(2131362911)
    TextView lblCuentaSeleccionada;
    @InjectView(2131362908)
    TextView lblTitle;
    @InjectView(2131362909)
    RecyclerView lstFondos;
    protected SeleccionarFondoAdapter mSeleccionarFondosAdapter;
    ImageView p;
    protected SeleccionarFondoPresenter presenter;
    @Inject
    AnalyticsManager q;
    private ArrayList<CuentaFondosBean> r;
    private ArrayList<CuentaOperativaBean> s;
    private CuentaFondosBean t;
    private String u;
    private String v;
    @InjectView(2131362918)
    CustomSpinner vgSelectorAccount;
    private ArrayList<Leyenda> w;

    public void clearScreenData() {
    }

    public Context getContext() {
        return this;
    }

    public void initialize() {
        this.v = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_ACCION);
        this.r = getIntent().getParcelableArrayListExtra("CUENTAS");
        this.t = (CuentaFondosBean) getIntent().getParcelableExtra("CUENTA");
        this.u = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_CONTRATO);
        this.w = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES);
        this.s = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS);
        initializeList();
        setSelectedAccount(this.t);
    }

    public void setSelectedAccount(CuentaFondosBean cuentaFondosBean) {
        List list;
        this.t = cuentaFondosBean;
        TextView textView = this.lblCuentaSeleccionada;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.F24_21_lbl_cuentaTitulo));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(UtilAccount.formatCuentaTitulo(this.t.getNumero()));
        textView.setText(sb.toString());
        try {
            TextView textView2 = this.lblCuentaSeleccionada;
            CAccessibility cAccessibility = new CAccessibility(getContext());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getString(R.string.F24_21_lbl_cuentaTitulo));
            sb2.append(UtilAccount.formatCuentaTitulo(cuentaFondosBean.getNumero()));
            textView2.setContentDescription(cAccessibility.applyFilterGeneral(sb2.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.t.getListaFondos() != null) {
            list = this.t.getListaFondos().getFondosBean();
        } else {
            list = new ArrayList();
        }
        configureListAdapter(list);
    }

    /* access modifiers changed from: protected */
    public void initializeList() {
        this.lstFondos.setHasFixedSize(true);
        this.lstFondos.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    /* access modifiers changed from: protected */
    public void configureListAdapter(List<FondoBean> list) {
        this.mSeleccionarFondosAdapter = new SeleccionarFondoAdapter(this, list);
        this.mSeleccionarFondosAdapter.setOnClickListener(this);
        this.lstFondos.setAdapter(this.mSeleccionarFondosAdapter);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        if (this.p != null) {
            this.p.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeleccionarFondoActivity.this.finish();
                    SeleccionarFondoActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }

    public void configureLayout() {
        if (this.v.equalsIgnoreCase(FondosConstants.ACCION_RESCATAR)) {
            this.lblTitle.setText(getString(R.string.F24_02_LBL_TITLE));
        } else if (this.v.equalsIgnoreCase(FondosConstants.ACCION_TRANSFERIR)) {
            this.lblTitle.setText(getString(R.string.F24_03_LBL_TITLE));
        }
        if (this.r.size() < 2) {
            this.vgSelectorAccount.setVisibility(8);
        }
        this.vgSelectorAccount.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_seleccionar_fondo);
        this.q.trackScreen(getString(R.string.analytics_screen_seleccion_fondo_destino_transferir));
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        this.presenter = new SeleccionarFondoPresenter(this.mBus, this.mDataManager, this);
        this.presenter.attachView(this);
    }

    public void attachView() {
        if (!this.presenter.isViewAttached()) {
            this.presenter.attachView(this);
        }
    }

    public void detachView() {
        if (this.presenter.isViewAttached()) {
            this.presenter.detachView();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void gotoNextFlow(FondoBean fondoBean) {
        if (this.v.equalsIgnoreCase(FondosConstants.ACCION_TRANSFERIR)) {
            Intent intent = new Intent(this, TransferirFondoActivity.class);
            intent.putParcelableArrayListExtra("CUENTAS", this.r);
            intent.putExtra("CUENTA", this.t);
            intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, fondoBean);
            intent.putExtra("ORIGEN", FondosConstants.ORIGEN_TENENCIAS);
            intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO, this.u);
            intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, this.w);
            startActivityForResult(intent, 5);
        } else if (this.v.equalsIgnoreCase(FondosConstants.ACCION_RESCATAR)) {
            Intent intent2 = new Intent(this, RescatarFondoActivity.class);
            intent2.putParcelableArrayListExtra("CUENTAS", this.r);
            intent2.putExtra("CUENTA", this.t);
            intent2.putExtra(FondosConstants.INTENT_EXTRA_FONDO, fondoBean);
            intent2.putExtra("ORIGEN", FondosConstants.ORIGEN_TENENCIAS);
            intent2.putExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, this.s);
            startActivityForResult(intent2, 4);
        }
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F24_02_vgSelectorAccount) {
            this.presenter.showSelectAccountDialog(this.t, this.r);
        } else if (id2 == R.id.back_imgButton) {
            onBackPressed();
        }
    }

    public void onItemClick(View view) {
        FondoBean fondoBean = (FondoBean) this.t.getListaFondos().getFondosBean().get(this.lstFondos.getChildPosition(view));
        this.mSeleccionarFondosAdapter.setOnClickListener(null);
        this.presenter.onFundSelected(fondoBean);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        this.mSeleccionarFondosAdapter.setOnClickListener(this);
        if (!activityResultHandler(i2, intent)) {
            switch (i) {
                case 4:
                case 5:
                    if (i2 == -1 && intent != null) {
                        setResult(-1, intent);
                        finish();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }
}
