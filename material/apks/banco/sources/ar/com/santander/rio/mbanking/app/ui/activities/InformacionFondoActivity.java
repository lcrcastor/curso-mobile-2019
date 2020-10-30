package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.InformacionFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.InformacionFondoView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.InformacionFondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCotizacionesFondosBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class InformacionFondoActivity extends BaseMvpActivity implements OnClickListener, InformacionFondoView {
    @InjectView(2131363017)
    Button btnSuscribir;
    @InjectView(2131363021)
    TextView lblConoceMas;
    @InjectView(2131363022)
    TextView lblData30Dias;
    @InjectView(2131363023)
    TextView lblDataAdmin;
    @InjectView(2131363024)

    /* renamed from: lblDataAño reason: contains not printable characters */
    TextView f230lblDataAo;
    @InjectView(2131363025)
    TextView lblDataDescripcionCorta;
    @InjectView(2131363026)
    TextView lblDataEntrada;
    @InjectView(2131363027)
    TextView lblDataHorario;
    @InjectView(2131363028)
    TextView lblDataHoy;
    @InjectView(2131363029)
    TextView lblDataInfoFinal;
    @InjectView(2131363030)
    TextView lblDataMoneda;
    @InjectView(2131363031)
    TextView lblDataPlazoPago;
    @InjectView(2131363032)
    TextView lblDataSalida;
    @InjectView(2131363033)
    TextView lblDataValorCuotaparte;
    @InjectView(2131363043)
    TextView lblTituloFondo;
    @InjectView(2131363045)
    LinearLayout lnlCotizaciones;
    @Inject
    SessionManager p;
    ArrayList<CuentaFondosBean> q;
    InformacionFondoBean r;
    @Inject
    AnalyticsManager s;
    private String t;
    /* access modifiers changed from: private */
    public InformacionFondoPresenter u;
    private ArrayList<Leyenda> v;
    private ArrayList<CuentaOperativaBean> w = new ArrayList<>();

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public Context getContext() {
        return this;
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        this.s.trackScreen(getString(R.string.analytics_screen_detalle_informacion_fondos));
        setContentView((int) R.layout.layout_informacion_fondo);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.F24_11_BTN_SUSCRIBIR) {
            gotoSuscribirFondo();
        }
    }

    private Boolean a(String str) {
        String str2;
        Boolean valueOf = Boolean.valueOf(false);
        List<Cuenta> listAccountsUserFromSession = CAccounts.getInstance(this.p).getListAccountsUserFromSession();
        if (str.equalsIgnoreCase("0")) {
            str2 = Constants.SYMBOL_CURRENCY_PESOS;
        } else if (str.equalsIgnoreCase("2")) {
            str2 = Constants.SYMBOL_CURRENCY_DOLAR;
        } else {
            str2 = UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(str).toString());
        }
        for (Cuenta cuenta : listAccountsUserFromSession) {
            if (TextUtils.isEmpty(str2) || cuenta.getTipo().equalsIgnoreCase("02")) {
                return Boolean.valueOf(true);
            }
            if (UtilAccount.getCurrencyOfAccount(this.p, cuenta).equalsIgnoreCase(str2)) {
                return Boolean.valueOf(true);
            }
        }
        return valueOf;
    }

    private void b() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.F24_XX_SIN_CUENTAS_OPERATIVAS), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
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
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    public void gotoSuscribirFondo() {
        if (!a(this.r.getMoneda()).booleanValue()) {
            b();
            return;
        }
        Intent intent = new Intent(this, SuscribirFondoActivity.class);
        FondoBean fondoBean = new FondoBean();
        fondoBean.setNombre(this.r.getNombre());
        fondoBean.setId(this.r.getId());
        fondoBean.setMoneda(this.r.getMoneda());
        fondoBean.setValorCuotaParte(this.r.getValorCuotaParte());
        fondoBean.setHorarioDesde(this.r.getHorarioDesde());
        fondoBean.setHorarioHasta(this.r.getHorarioHasta());
        fondoBean.setHonorarios(this.r.getHonorariosFondosBean());
        intent.putExtra("CUENTA", (Parcelable) this.q.get(0));
        intent.putExtra("ORIGEN", "DETALLE_FONDO");
        intent.putExtra(FondosConstants.INTENT_EXTRA_ACCION, FondosConstants.ACCION_SUSCRIBIR_NUEVO);
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, fondoBean);
        intent.putExtra("CUENTAS", this.q);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO, this.t);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, this.v);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, this.w);
        startActivityForResult(intent, 6);
    }

    public void initialize() {
        this.u = new InformacionFondoPresenter(this.mBus, this.mDataManager, this);
        this.u.attachView(this);
        this.r = (InformacionFondoBean) getIntent().getParcelableExtra("DETALLE_FONDO");
        this.q = getIntent().getParcelableArrayListExtra("CUENTAS");
        this.t = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_CONTRATO);
        this.v = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES);
        this.w = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS);
        this.u.onCreatePage(this.r);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_WITH_MENU);
        enableBackButton();
        enableOptionsMenuButton();
    }

    public void enableBackButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    InformacionFondoActivity.this.finish();
                    InformacionFondoActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void enableOptionsMenuButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.menu);
        if (findViewById == null) {
            return;
        }
        if (!TextUtils.isEmpty(this.r.getCartera()) || !TextUtils.isEmpty(this.r.getReglamento())) {
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    InformacionFondoActivity.this.u.showOptionsDialog(InformacionFondoActivity.this.getSupportFragmentManager(), InformacionFondoActivity.this.r);
                }
            });
            return;
        }
        findViewById.setVisibility(8);
    }

    public void attachView() {
        if (!this.u.isViewAttached()) {
            this.u.attachView(this);
        }
    }

    public void detachView() {
        if (this.u.isViewAttached()) {
            this.u.detachView();
        }
    }

    public void setInformacionFondoView(final InformacionFondoBean informacionFondoBean) {
        this.lblTituloFondo.setText(Html.fromHtml(informacionFondoBean.getNombre()));
        try {
            this.lblTituloFondo.setContentDescription(FondosConstants.applyAccesibilityFilterName(getContext(), this.lblTituloFondo.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.lblDataDescripcionCorta.setText(Html.fromHtml(informacionFondoBean.getDescripcionCorta()));
        this.lblDataValorCuotaparte.setText(informacionFondoBean.getValorCuotaParte());
        try {
            this.lblDataValorCuotaparte.setContentDescription(new CAccessibility(getContext()).applyFilterAmount(informacionFondoBean.getValorCuotaParte()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.lblDataMoneda.setText(Html.fromHtml(informacionFondoBean.getMoneda()));
        this.lblDataPlazoPago.setText(Html.fromHtml(informacionFondoBean.getPlazoPago()));
        try {
            this.lblDataPlazoPago.setContentDescription(new CAccessibility(getContext()).applyFilterGeneral(Html.fromHtml(informacionFondoBean.getPlazoPago()).toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.lblDataHorario.setText(String.format("%s a %shs", new Object[]{informacionFondoBean.getHorarioDesde(), informacionFondoBean.getHorarioHasta()}));
        try {
            TextView textView = this.lblDataHorario;
            StringBuilder sb = new StringBuilder();
            sb.append(informacionFondoBean.getHorarioDesde());
            sb.append(" a ");
            sb.append(new CAccessibility(getContext()).applyFilterTime(informacionFondoBean.getHorarioHasta()));
            textView.setContentDescription(sb.toString());
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.lblDataAdmin.setText(informacionFondoBean.getHonorariosFondosBean().getAdmin());
        try {
            this.lblDataAdmin.setContentDescription(new CAccessibility(getContext()).applyFilterTasaValue(informacionFondoBean.getHonorariosFondosBean().getAdmin()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.lblDataEntrada.setText(informacionFondoBean.getHonorariosFondosBean().getEntrada());
        try {
            this.lblDataEntrada.setContentDescription(new CAccessibility(getContext()).applyFilterTasaValue(informacionFondoBean.getHonorariosFondosBean().getEntrada()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        this.lblDataSalida.setText(informacionFondoBean.getHonorariosFondosBean().getSalida());
        try {
            this.lblDataSalida.setContentDescription(new CAccessibility(getContext()).applyFilterTasaInteres(informacionFondoBean.getHonorariosFondosBean().getSalida()));
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        this.lblDataInfoFinal.setText(Html.fromHtml(a(informacionFondoBean.getLegalesFondosBean().getLeyendaLegales())));
        if (informacionFondoBean.getListaCotizacionesFondosBean() != null) {
            this.lnlCotizaciones.setVisibility(0);
            a(informacionFondoBean.getListaCotizacionesFondosBean());
        } else {
            this.lnlCotizaciones.setVisibility(8);
        }
        if (!informacionFondoBean.getDescripcionLarga().isEmpty()) {
            this.lblConoceMas.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    InformacionFondoActivity.this.a(InformacionFondoActivity.this.getString(R.string.IDXX_INFOACTIVITY_INFORMACION_FONDO_TITLE), informacionFondoBean.getNombre(), informacionFondoBean.getDescripcionLarga());
                }
            });
        } else {
            this.lblConoceMas.setVisibility(8);
        }
        this.btnSuscribir.setVisibility(8);
        this.btnSuscribir.setOnClickListener(this);
    }

    private void a(ListaCotizacionesFondosBean listaCotizacionesFondosBean) {
        for (CotizacionFondosBean cotizacionFondosBean : listaCotizacionesFondosBean.getCotizacionesFondosBean()) {
            String valor = cotizacionFondosBean.getValor();
            if (cotizacionFondosBean.getDetalle().equalsIgnoreCase(getString(R.string.F24_11_LBL_HOY))) {
                this.lblDataHoy.setText(valor);
                try {
                    this.lblDataHoy.setContentDescription(new CAccessibility(getContext()).applyFilterTasaValue(valor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                a(this.lblDataHoy, valor);
            } else if (cotizacionFondosBean.getDetalle().equalsIgnoreCase(getString(R.string.F24_11_LBL_MES))) {
                this.lblData30Dias.setText(valor);
                try {
                    this.lblData30Dias.setContentDescription(new CAccessibility(getContext()).applyFilterTasaValue(valor));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                a(this.lblData30Dias, valor);
            } else if (cotizacionFondosBean.getDetalle().equalsIgnoreCase(getString(R.string.F24_11_LBL_ANUAL))) {
                this.f230lblDataAo.setText(valor);
                try {
                    this.f230lblDataAo.setContentDescription(new CAccessibility(getContext()).applyFilterTasaValue(valor));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                a(this.f230lblDataAo, valor);
            }
        }
    }

    private String a(List<String> list) {
        String str = "";
        for (String concat : list) {
            str = str.concat("\n").concat(concat);
        }
        return str;
    }

    private void a(TextView textView, String str) {
        if (str.contains("-")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.red_arrow), null, null, null);
        } else if (str.contains(Constants.SYMBOL_POSITIVE)) {
            textView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.green_arrow), null, null, null);
        }
        if (textView.getText().toString().contains(Constants.SYMBOL_POSITIVE)) {
            textView.setText(textView.getText().toString().replace(Constants.SYMBOL_POSITIVE, ""));
        } else if (textView.getText().toString().contains("-")) {
            textView.setText(textView.getText().toString().replace("-", ""));
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2, String str3) {
        this.s.trackScreen(getString(R.string.analytics_screen_pantalla_acerca_del_fondo));
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, str);
        intent.putExtra(InfoActivity.SUBTITLE_TO_SHOW, str2);
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str3);
        intent.putExtra(InfoActivity.ICON_TO_SHOW, R.drawable.ic_close);
        startActivity(intent);
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent) && i == 6 && i2 == -1 && intent != null) {
            setResult(-1, intent);
            finish();
        }
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.s;
    }

    public void popUpErrorDownload() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.USER200029_TITLE), getContext().getString(R.string.MSG_USER0000XX_FILE_DOWNLOAD_ERROR), null, getContext().getString(R.string.accept), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                newInstance.closeDialog();
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpErrorDownload");
    }
}
