package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.nuevopago.SeleccionarEmpresaNuevoPagoPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevopago.SeleccionarEmpresaNuevoPagoView;
import ar.com.santander.rio.mbanking.app.ui.adapters.EmpresaPagoAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.EmpresaPagoAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SeleccionarEmpresaNuevoPagoActivity extends BaseMvpActivity implements OnClickListener, SeleccionarEmpresaNuevoPagoView, OnItemClickListener {
    @InjectView(2131362121)
    RecyclerView lstCompanies;
    protected String mCodigoBarras;
    protected List<CuentaDebitoBean> mCuentas;
    protected List<CnsEmpresaDatosEmpresa> mEmpresas;
    protected EmpresaPagoAdapter mEmpresasAdapter;
    ImageView p;
    protected SeleccionarEmpresaNuevoPagoPresenter presenter;
    @Inject
    AnalyticsManager q;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public void initialize() {
        this.mCuentas = getIntent().getParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS);
        this.mEmpresas = getIntent().getParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESAS);
        this.mCodigoBarras = getIntent().getStringExtra(NuevoPagoServiciosConstants.EXTRA_CODIGO_BARRAS);
    }

    /* access modifiers changed from: protected */
    public void configureListAdapter(List<CnsEmpresaDatosEmpresa> list) {
        this.mEmpresasAdapter = new EmpresaPagoAdapter(this, list);
        this.mEmpresasAdapter.setOnClickListener(this);
        this.lstCompanies.setAdapter(this.mEmpresasAdapter);
    }

    /* access modifiers changed from: protected */
    public void initializeList() {
        this.lstCompanies.setHasFixedSize(true);
        this.lstCompanies.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        this.p.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_select_company_nuevo_pago);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        initializeList();
        this.presenter = new SeleccionarEmpresaNuevoPagoPresenter(this.mBus, this.mDataManager, this);
        this.presenter.attachView(this);
        this.presenter.showSelectCompany();
        this.q.trackScreen(getString(R.string.analytics_screen_name_payment_seleccionar_empresa_escaneada));
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

    public void showSelectCompany() {
        configureListAdapter(this.mEmpresas);
    }

    public void showPrepararPagoElectronico(DatosDeudaBean datosDeudaBean, List<CuentaDebitoBean> list) {
        Intent intent = new Intent(this, PrepareInvoicePaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_CB);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void showPrepararPagoAfip(DatosDeudaBean datosDeudaBean, List<CuentaDebitoBean> list) {
        Intent intent = new Intent(this, PrepareAfipPaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_CB);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void showSelectPayment(List<DatosDeudaBean> list) {
        Intent intent = new Intent(this, SeleccionarPagoNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA, ((DatosDeudaBean) list.get(0)).datosEmpresa);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_PAGO_ELECTRONICO, ((DatosDeudaBean) list.get(0)).identificacion);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) this.mCuentas);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_DEUDAS, (ArrayList) list);
        startActivityForResult(intent, 5);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.back_imgButton) {
            onBackPressed();
        }
    }

    public void onItemClick(View view) {
        this.presenter.onCompanySelected(this.mCodigoBarras, (CnsEmpresaDatosEmpresa) this.mEmpresas.get(this.lstCompanies.getChildPosition(view)));
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 0 && i2 == -1) {
            Intent intent2 = new Intent();
            if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                    intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                }
            } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
            }
            setResult(-1, intent2);
            finish();
        }
    }
}
