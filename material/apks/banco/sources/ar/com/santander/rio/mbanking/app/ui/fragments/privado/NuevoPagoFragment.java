package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.module.nuevopago.NuevoPagoServiciosPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevopago.NuevoPagoServiciosView;
import ar.com.santander.rio.mbanking.app.ui.activities.AgregarPagoElectronicoNuevoPagoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.AgregarPagoRecargaCelularNuevoPagoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.BuscarEmpresaNuevoPagoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.PrepareAfipPaymentNuevoPagoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.PrepareInvoicePaymentNuevoPagoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.PrepareNoInvoicePaymentNuevoPagoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.PrivateMenuActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.PagoServicioAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.PagoServicioAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.OnBoardingTextStylingSet;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class NuevoPagoFragment extends BaseMvpFragment implements OnClickListener, NuevoPagoServiciosView, OnItemClickListener {
    @Inject
    AnalyticsManager a;
    @InjectView(2131366350)
    TextView activityTitle;
    @Inject
    SettingsManager b;
    @InjectView(2131361830)
    TextView btnNuevoPago;
    private Boolean c = Boolean.valueOf(false);
    @InjectView(2131361839)
    LinearLayout columna;
    private Spanned d;
    private SharedPreferences e;
    private OnBoardingTextStylingSet f;
    private String g = "";
    @InjectView(2131364732)
    LinearLayout homeScreen;
    @InjectView(2131361826)
    RecyclerView lstDeudas;
    protected List<CuentaDebitoBean> mCuentas;
    protected List<DatosDeudaBean> mDeudas;
    protected PagoServicioAdapter mPagoServicioAdapter;
    protected NuevoPagoServiciosPresenter nuevoPagoPresenter;
    @InjectView(2131361827)
    RelativeLayout relativeSinDeudas;

    public void clearScreenData() {
    }

    public void showPrepararPagoPrepago(DatosDeudaBean datosDeudaBean) {
    }

    public void initialize() {
        this.btnNuevoPago.setOnClickListener(this);
        setResultCnsEmpresa(Boolean.valueOf(false), Html.fromHtml(""));
    }

    public void configureActionBar() {
        this.mActionBar = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
    }

    public void configureLayout() {
        this.activityTitle.setText(getString(R.string.ID173_PAYMENT_MAIN_LBL_TITLE));
        this.homeScreen.setVisibility(4);
    }

    private void a(List<DatosDeudaBean> list) {
        this.mDeudas = list;
        this.mPagoServicioAdapter = new PagoServicioAdapter(getActivity(), this.mDeudas);
        this.mPagoServicioAdapter.setOnItemClickListener(this);
        this.lstDeudas.setAdapter(this.mPagoServicioAdapter);
    }

    private void y() {
        this.lstDeudas.setHasFixedSize(true);
        this.lstDeudas.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
    }

    public void onCreate(Bundle bundle) {
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        this.a.trackScreen(getString(R.string.analytics_screen_name_payment_services_home));
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mRootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_pago_servicios, viewGroup, false);
        ButterKnife.inject((Object) this, this.mRootView);
        initialize();
        configureActionBar();
        configureLayout();
        y();
        this.nuevoPagoPresenter = new NuevoPagoServiciosPresenter(this.mBus, this.mDataManager);
        this.nuevoPagoPresenter.attachView(this);
        if (!(getArguments() == null || getArguments().getString(FragmentConstants.PAGO_SERVICIO_EMPRESA) == null)) {
            this.g = getArguments().getString(FragmentConstants.PAGO_SERVICIO_EMPRESA);
            getArguments().clear();
        }
        this.nuevoPagoPresenter.showHomeScreen(Boolean.valueOf(true), this.g);
        return this.mRootView;
    }

    public void showElectronicAddPayment(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(getContext(), AgregarPagoElectronicoNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA, cnsEmpresaDatosEmpresa);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 4);
        dismissProgress();
    }

    public void showRecargaActivity(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(getContext(), AgregarPagoRecargaCelularNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA, cnsEmpresaDatosEmpresa);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 4);
    }

    public void setResultCnsEmpresa(Boolean bool, Spanned spanned) {
        this.c = bool;
        if (TextUtils.isEmpty(spanned)) {
            spanned = Html.fromHtml("");
        }
        this.d = spanned;
    }

    public Context getContext() {
        return getActivity();
    }

    public void attachView() {
        if (!this.nuevoPagoPresenter.isViewAttached()) {
            this.nuevoPagoPresenter.attachView(this);
        }
    }

    public void detachView() {
        if (this.nuevoPagoPresenter.isViewAttached()) {
            this.nuevoPagoPresenter.detachView();
        }
    }

    public void showOnBoarding() {
        this.e = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!this.e.getBoolean(NuevoPagoServiciosConstants.ONBOARDING, false)) {
            this.a.trackScreen(getString(R.string.analytics_screen_name_payment_services_onboarding_1));
            this.a.trackScreen(getString(R.string.analytics_screen_name_payment_services_onboarding_2));
            this.f = new OnBoardingTextStylingSet();
            this.f.put(Integer.valueOf(R.id.F06_01_LBL_OnBoarding_Page1_Label1), getString(R.string.IDXX6_PAGO_SERVICIO_LBL_ONBOARDING_1));
            this.f.put(Integer.valueOf(R.id.F06_01_LBL_OnBoarding_Page2_Label1), getString(R.string.IDXX7_PAGO_SERVICIO_LBL_ONBOARDING_2));
            showOnBoarding(R.layout.layout_onboarding_pago_servicios, R.id.F06_01_BTN_Comenzar, R.id.F06_01_FLP_OnBoarding, NuevoPagoServiciosConstants.ONBOARDING, this.f);
        }
    }

    public void showPaymentsList(List<DatosDeudaBean> list) {
        this.relativeSinDeudas.setVisibility(8);
        this.btnNuevoPago.setVisibility(0);
        a(list);
        this.homeScreen.setVisibility(0);
    }

    public void setAccountList(List<CuentaDebitoBean> list) {
        this.mCuentas = new ArrayList(Arrays.asList((CuentaDebitoBean[]) list.toArray(new CuentaDebitoBean[list.size()])));
    }

    public void showNoPayments() {
        this.a.trackScreen(getString(R.string.analytics_screen_name_payment_services_home_no_payments));
        this.relativeSinDeudas.setVisibility(0);
        this.columna.setVisibility(8);
        this.homeScreen.setVisibility(0);
    }

    public String getTitle() {
        return this.activityTitle.getText().toString();
    }

    public void showPrepararPagoConFactura(DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(getActivity(), PrepareInvoicePaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_AGENDA);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) this.mCuentas);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void showPrepararPagoSinFactura(DatosDeudaBean datosDeudaBean, String str) {
        Intent intent = new Intent(getActivity(), PrepareNoInvoicePaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, str);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) this.mCuentas);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void showPrepararPagoAfip(DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(getActivity(), PrepareAfipPaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_AGENDA);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) this.mCuentas);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.F06_00_btn_nuevo_pago) {
            Intent intent = new Intent(getActivity(), BuscarEmpresaNuevoPagoActivity.class);
            intent.putExtra(NuevoPagoServiciosConstants.EXTRA_FINISHED_OK_CNS_EMPRESA, this.c);
            intent.putExtra(NuevoPagoServiciosConstants.EXTRA_FINISHED_OK_AYUDA, this.d);
            intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) this.mCuentas);
            startActivityForResult(intent, 3);
        }
    }

    public void onItemClick(View view) {
        DatosDeudaBean datosDeudaBean = (DatosDeudaBean) this.mDeudas.get(this.lstDeudas.getChildPosition(view));
        if (datosDeudaBean.datosEmpresa.empServ.equalsIgnoreCase("SUBE")) {
            showPrepararPagoSinFactura(datosDeudaBean, NuevoPagoServiciosConstants.ORIGEN_AGENDA);
        } else {
            this.nuevoPagoPresenter.onPrepararPago(datosDeudaBean);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        char c2 = 65535;
        if (i2 == -1 && intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
            ((SantanderRioMainActivity) getActivity()).onPrivateMenuOptionSelectedInNestedActivity(intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, 0));
        } else if (i2 != -1 || !intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
            if (i != 0) {
                switch (i) {
                    case 3:
                        break;
                    case 4:
                        configureLayout();
                        this.nuevoPagoPresenter.attachView(this);
                        this.nuevoPagoPresenter.showHomeScreen(Boolean.valueOf(false), null);
                        return;
                    default:
                        return;
                }
            }
            if (i2 == -1 && intent.getBooleanExtra("recargarHome", false)) {
                configureLayout();
                this.nuevoPagoPresenter.attachView(this);
                this.nuevoPagoPresenter.showHomeScreen(Boolean.valueOf(false), null);
            }
        } else {
            String stringExtra = intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION);
            switch (stringExtra.hashCode()) {
                case -1667304550:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_HOME_ERROR_FRAGMENT)) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -1442009346:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_CUENTAS)) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -1365838438:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_HOME_FUNCIONALIDAD)) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -171755572:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_LOGIN)) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 4216548:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_HOME_ERROR_CLOCK)) {
                        c2 = 4;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    ((SantanderRioMainActivity) getActivity()).gotoHome();
                    return;
                case 1:
                    ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.PAGO_SERVICIOS);
                    return;
                case 2:
                    ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.CUENTAS);
                    return;
                case 3:
                    ((SantanderRioMainActivity) getActivity()).setErrorFragment(intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE), this.activityTitle.getText().toString());
                    return;
                case 4:
                    ((SantanderRioMainActivity) getActivity()).setErrorFragment(intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE), (int) R.drawable.ico_reloj_gris);
                    return;
                default:
                    return;
            }
        }
    }
}
