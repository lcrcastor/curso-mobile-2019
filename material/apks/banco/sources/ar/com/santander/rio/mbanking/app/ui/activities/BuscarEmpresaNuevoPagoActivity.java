package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.nuevopago.BuscarEmpresaNuevoPagoPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevopago.BuscarEmpresaNuevoPagoView;
import ar.com.santander.rio.mbanking.app.ui.adapters.EmpresaPagoAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.EmpresaPagoAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

public class BuscarEmpresaNuevoPagoActivity extends BaseMvpActivity implements OnClickListener, BuscarEmpresaNuevoPagoView, OnItemClickListener {
    @InjectView(2131362056)
    Button btnScanBarcode;
    @InjectView(2131362059)
    ClearableEditText inpSearch;
    @InjectView(2131362062)
    RecyclerView lstCompanies;
    protected List<CuentaDebitoBean> mCuentas;
    protected List<CnsEmpresaDatosEmpresa> mEmpresas;
    protected EmpresaPagoAdapter mEmpresasAdapter;
    protected List<CnsEmpresaDatosEmpresa> mEmpresasEncontradas;
    ImageView p;
    protected BuscarEmpresaNuevoPagoPresenter presenter;
    @Inject
    AnalyticsManager q;
    @Inject
    SettingsManager r;
    Spanned s;
    @InjectView(2131362060)
    TextView txtDescription;

    public void configureLayout() {
    }

    public void initialize() {
        this.mCuentas = getIntent().getParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS);
        this.q.trackScreen(getString(R.string.analytics_screen_name_payment_services_busqueda));
        this.inpSearch.imgClearButton.setColorFilter(getResources().getColor(R.color.grey_light), Mode.SRC_IN);
        this.inpSearch.setOnTextWatcher(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                BuscarEmpresaNuevoPagoActivity.this.q.trackScreen(BuscarEmpresaNuevoPagoActivity.this.getString(R.string.analytics_screen_name_payment_services_busqueda_resultados));
                if (editable.length() < 3) {
                    BuscarEmpresaNuevoPagoActivity.this.txtDescription.setText(TextUtils.isEmpty(BuscarEmpresaNuevoPagoActivity.this.s) ? "" : BuscarEmpresaNuevoPagoActivity.this.s);
                    BuscarEmpresaNuevoPagoActivity.this.txtDescription.setVisibility(0);
                    BuscarEmpresaNuevoPagoActivity.this.lstCompanies.setVisibility(8);
                    return;
                }
                List filterCompanies = BuscarEmpresaNuevoPagoActivity.this.filterCompanies(editable.toString());
                if (!filterCompanies.isEmpty()) {
                    BuscarEmpresaNuevoPagoActivity.this.txtDescription.setVisibility(8);
                    BuscarEmpresaNuevoPagoActivity.this.lstCompanies.setVisibility(0);
                    BuscarEmpresaNuevoPagoActivity.this.configureListAdapter(filterCompanies);
                    return;
                }
                BuscarEmpresaNuevoPagoActivity.this.q.trackEvent(BuscarEmpresaNuevoPagoActivity.this.getString(R.string.analytics_category_payment_services), BuscarEmpresaNuevoPagoActivity.this.getString(R.string.analytics_action_payment_services_busqueda), BuscarEmpresaNuevoPagoActivity.this.getString(R.string.analytics_label_payment_services_not_found));
                TextView textView = BuscarEmpresaNuevoPagoActivity.this.txtDescription;
                StringBuilder sb = new StringBuilder();
                sb.append(BuscarEmpresaNuevoPagoActivity.this.getString(R.string.IDX53_PAGO_SERVICIO_LBL_FILTRO_SIN_RESULTADO));
                sb.append(" <b>");
                sb.append(editable);
                sb.append("</b>");
                textView.setText(Html.fromHtml(sb.toString()));
                BuscarEmpresaNuevoPagoActivity.this.txtDescription.setVisibility(0);
                BuscarEmpresaNuevoPagoActivity.this.lstCompanies.setVisibility(8);
            }
        });
        this.btnScanBarcode.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void configureListAdapter(List<CnsEmpresaDatosEmpresa> list) {
        this.mEmpresasEncontradas = list;
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
        setContentView((int) R.layout.activity_find_company_nuevo_pago);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        initializeList();
        this.presenter = new BuscarEmpresaNuevoPagoPresenter(this.mBus, this.mDataManager, this.r, this);
        this.presenter.attachView(this);
        if (!Boolean.valueOf(getIntent().getBooleanExtra(NuevoPagoServiciosConstants.EXTRA_FINISHED_OK_CNS_EMPRESA, false)).booleanValue()) {
            this.presenter.showFindCompany();
            return;
        }
        showFindCompany(this.r.getPagoServiciosEmpresas().listaEmpresa.getLstCnsEmpresaDatosEmpresa());
        this.s = (Spanned) getIntent().getCharSequenceExtra(NuevoPagoServiciosConstants.EXTRA_FINISHED_OK_AYUDA);
        setTxtDescription(this.s);
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
        hideKeyboard();
        if (!this.presenter.isBackFromWSError()) {
            Intent intent = new Intent();
            intent.putExtra("recargarHome", true);
            setResult(-1, intent);
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showFindCompany(List<CnsEmpresaDatosEmpresa> list) {
        this.mEmpresas = list;
    }

    public void setTxtDescription(Spanned spanned) {
        this.s = spanned;
        this.txtDescription.setText(TextUtils.isEmpty(this.s) ? "" : this.s);
    }

    public void showElectronicAddPayment(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(this, AgregarPagoElectronicoNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA, cnsEmpresaDatosEmpresa);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 4);
    }

    public void showPhoneAddPayment(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(this, AgregarPagoRecargaCelularNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA, cnsEmpresaDatosEmpresa);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 4);
    }

    public void showAfipAddPayment(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(this, AgregarPagoAfipNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA, cnsEmpresaDatosEmpresa);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 4);
    }

    /* access modifiers changed from: protected */
    public List<CnsEmpresaDatosEmpresa> filterCompanies(String str) {
        ArrayList arrayList = new ArrayList();
        String lowerCase = str.toLowerCase(Locale.getDefault());
        if (lowerCase.length() > 2 && this.mEmpresas != null && !this.mEmpresas.isEmpty()) {
            for (CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa : this.mEmpresas) {
                if (cnsEmpresaDatosEmpresa.empDescr != null && cnsEmpresaDatosEmpresa.empDescr.toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                    arrayList.add(cnsEmpresaDatosEmpresa);
                }
            }
        }
        return arrayList;
    }

    public void showScanBarcode() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
            this.btnScanBarcode.setEnabled(false);
            Intent intent = new Intent(this, EscanearCodigoBarrasNuevoPagoActivity.class);
            intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) this.mCuentas);
            startActivityForResult(intent, 6);
        } else if (VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
        }
    }

    public void showRequestPermissionExplation(final int i) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.camera_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BuscarEmpresaNuevoPagoActivity.this.requestPermissions(new String[]{"android.permission.CAMERA"}, i);
            }
        });
        newInstance.show(getSupportFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    public void onItemClick(View view) {
        this.presenter.onCompanySelected((CnsEmpresaDatosEmpresa) this.mEmpresasEncontradas.get(this.lstCompanies.getChildPosition(view)));
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F06_05_BTN_ESCANEAR) {
            showScanBarcode();
        } else if (id2 == R.id.back_imgButton) {
            onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 4 || i == 6) {
            this.btnScanBarcode.setEnabled(true);
            if (i2 == -1) {
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

    public void warnNoAccounts(String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
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
        newInstance.show(getSupportFragmentManager(), "noAccounts");
    }

    public void clearScreenData() {
        this.inpSearch.setText("");
    }
}
