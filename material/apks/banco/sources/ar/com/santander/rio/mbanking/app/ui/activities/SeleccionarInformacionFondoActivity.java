package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.module.funds.SeleccionarInformacionFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.SeleccionarInformacionFondoView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.adapters.SeleccionarInformacionFondoAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.SeleccionarInformacionFondoAdapter.OnFondoClicked;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants.InfoAdmViewMode;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.InformacionFondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesAdmFondos;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilAmount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class SeleccionarInformacionFondoActivity extends MvpPrivateMenuActivity implements SeleccionarInformacionFondoView, OnFondoClicked {
    private String A;
    private ArrayList<Leyenda> B;
    private ArrayList<CuentaOperativaBean> C;
    @InjectView(2131363010)
    ClearableEditText inpSearch;
    @InjectView(2131363011)
    TextView lblTitulo;
    SeleccionarInformacionFondoAdapter p;
    List<FondoBean> q = new ArrayList();
    List<FondoBean> r = new ArrayList();
    ArrayList<CuentaFondosBean> s;
    CuentaFondosBean t;
    ArrayList<CategoriaFondosBean> u = new ArrayList<>();
    String v;
    @Inject
    AnalyticsManager w;
    private RecyclerView x;
    /* access modifiers changed from: private */
    public IsbanDialogFragment y;
    /* access modifiers changed from: private */
    public SeleccionarInformacionFondoPresenter z;

    public void configureLayout() {
    }

    public int getMainLayout() {
        return R.layout.layout_seleccionar_informacion_fondo;
    }

    public void initialize() {
        this.z = new SeleccionarInformacionFondoPresenter(this.mBus, this.mDataManager, this);
        this.z.attachView(this);
        this.v = getIntent().getStringExtra("ORIGEN");
        this.u = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_CATEGORIAS);
        this.s = getIntent().getParcelableArrayListExtra("CUENTAS");
        this.C = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS);
        this.t = (CuentaFondosBean) getIntent().getParcelableExtra("CUENTA");
        this.B = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES);
        if (this.v.equalsIgnoreCase(FondosConstants.ORIGEN_SUSCRIBIR)) {
            this.r = a(this.t);
        } else {
            this.r = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_FONDOS);
        }
        this.A = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_CONTRATO);
        configureActionBar();
        g();
        b();
    }

    private void b() {
        h();
        ArrayList arrayList = new ArrayList();
        if (this.r != null && this.r.size() > 0) {
            CategoriaFondosBean categoriaFondosBean = new CategoriaFondosBean();
            categoriaFondosBean.setNombreCategoria(getString(R.string.F24_10_LBL_CATEGORIA_FONDOS_PROPIOS));
            arrayList.add(categoriaFondosBean);
            arrayList.addAll(this.r);
        }
        Iterator it = this.u.iterator();
        while (it.hasNext()) {
            CategoriaFondosBean categoriaFondosBean2 = (CategoriaFondosBean) it.next();
            arrayList.add(categoriaFondosBean2);
            arrayList.addAll(categoriaFondosBean2.getFondosBean().getFondosBean());
        }
        this.p = new SeleccionarInformacionFondoAdapter(this, arrayList);
        this.x.setAdapter(this.p);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.p != null) {
            this.p.notifyDataSetChanged();
        }
    }

    public void configureActionBar() {
        if (this.v.equalsIgnoreCase(FondosConstants.ORIGEN_INFORMACION)) {
            c();
        } else if (this.v.equalsIgnoreCase(FondosConstants.ORIGEN_SUSCRIBIR)) {
            d();
        } else if (this.v.equalsIgnoreCase(FondosConstants.ORIGEN_TRANSFERIR)) {
            e();
        } else if (this.v.equalsIgnoreCase(FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS)) {
            f();
        }
    }

    private void c() {
        this.lblTitulo.setText(getString(R.string.F24_10_LBL_TITULO_INFORMACION_DE_FONDO));
        setActionBarType(ActionBarType.BACK_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeleccionarInformacionFondoActivity.this.onBackPressed();
                }
            });
        }
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeleccionarInformacionFondoActivity.this.i();
                    SeleccionarInformacionFondoActivity.this.y.show(SeleccionarInformacionFondoActivity.this.getSupportFragmentManager(), "DialogOpciones");
                    SeleccionarInformacionFondoActivity.this.w.trackEvent(SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_event_category_fondos), SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_event_action_cancelar), SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_event_label_opciones_informacion_fondo));
                }
            });
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void d() {
        this.lblTitulo.setText(getString(R.string.F24_10_LBL_TITULO_SELECCIONAR_SUSCRIBIR));
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeleccionarInformacionFondoActivity.this.onBackPressed();
                    SeleccionarInformacionFondoActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }

    private void e() {
        this.lblTitulo.setText(getString(R.string.F24_10_LBL_TITULO_SELECCIONAR_DESTINO));
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeleccionarInformacionFondoActivity.this.onBackPressed();
                    SeleccionarInformacionFondoActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }

    private void f() {
        this.lblTitulo.setText(getString(R.string.F24_10_LBL_TITULO_ULTIMOS_MOVIMIENTOS_FONDOS));
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeleccionarInformacionFondoActivity.this.onBackPressed();
                    SeleccionarInformacionFondoActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }

    public void attachView() {
        if (!this.z.isViewAttached()) {
            this.z.attachView(this);
        }
    }

    public void detachView() {
        if (this.z.isViewAttached()) {
            this.z.detachView();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        this.w.trackScreen(getString(R.string.analytics_screen_pantalla_informacion_fondos));
        initialize();
    }

    private void g() {
        this.x = (RecyclerView) findViewById(R.id.F24_10_LST_RECYCLERVIEW);
        this.x.setHasFixedSize(true);
        this.x.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.inpSearch.imgClearButton.setColorFilter(getResources().getColor(R.color.grey_light), Mode.SRC_IN);
        this.inpSearch.setOnTextWatcher(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                SeleccionarInformacionFondoActivity.this.p.getFilter().filter(editable.toString());
            }
        });
    }

    private void h() {
        this.q.clear();
        if (this.r != null && this.r.size() > 0) {
            this.q.addAll(this.r);
        }
        if (this.u != null && this.u.size() > 0) {
            for (int i = 0; i < this.u.size(); i++) {
                this.q.addAll(((CategoriaFondosBean) this.u.get(i)).getFondosBean().getFondosBean());
            }
        }
    }

    public void clearScreenData() {
        this.inpSearch.setText("");
    }

    /* access modifiers changed from: private */
    public void i() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.F24_10_MENU_ITEM_COTIZACIONES));
        arrayList.add(getResources().getString(R.string.F24_10_MENU_ITEM_HONORARIOS));
        arrayList.add(getResources().getString(R.string.ID_3902_FONDOS_BTN_HORARIOS_OPERACION));
        this.y = IsbanDialogFragment.newInstance("mPopupMenuOpciones", null, null, arrayList, getString(R.string.F24_00_ACTIONSHEET_CANCELAR), null, null, null, arrayList);
        this.y.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(SeleccionarInformacionFondoActivity.this.getResources().getString(R.string.F24_10_MENU_ITEM_COTIZACIONES))) {
                    SeleccionarInformacionFondoActivity.this.z.callGetCotizaciones();
                    SeleccionarInformacionFondoActivity.this.w.trackEvent(SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_event_category_fondos), SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_event_action_pantalla_seleccionar_fondo), SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_event_label_pantalla_seleccionar_fondo_valor_cuota));
                } else if (str.equalsIgnoreCase(SeleccionarInformacionFondoActivity.this.getResources().getString(R.string.F24_10_MENU_ITEM_HONORARIOS))) {
                    SeleccionarInformacionFondoActivity.this.y.dismiss();
                    SeleccionarInformacionFondoActivity.this.w.trackScreen(SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_screen_pantalla_honorarios));
                    SeleccionarInformacionFondoActivity.this.z.callGetInfoAdmFondos(InfoAdmViewMode.VIEWMODE_HONORARIOS);
                } else if (str.equalsIgnoreCase(SeleccionarInformacionFondoActivity.this.getResources().getString(R.string.ID_3902_FONDOS_BTN_HORARIOS_OPERACION))) {
                    SeleccionarInformacionFondoActivity.this.y.dismiss();
                    SeleccionarInformacionFondoActivity.this.z.callGetInfoAdmFondos(InfoAdmViewMode.VIEWMODE_HORARIOS);
                    SeleccionarInformacionFondoActivity.this.w.trackScreen(SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_screen_pantalla_horarios));
                }
            }

            public void onSimpleActionButton() {
                SeleccionarInformacionFondoActivity.this.w.trackEvent(SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_event_category_fondos), SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_event_action_cancelar), SeleccionarInformacionFondoActivity.this.getString(R.string.analytics_event_label_cancelar_honorarios_cotozacione));
            }
        });
    }

    public void onFondoSelected(FondoBean fondoBean) {
        if (fondoBean.getMoneda().equalsIgnoreCase("0")) {
            fondoBean.setMoneda(Constants.SYMBOL_CURRENCY_PESOS_DESCRIPTION);
        } else if (fondoBean.getMoneda().equalsIgnoreCase("2")) {
            fondoBean.setMoneda(Constants.SYMBOL_CURRENCY_DOLLAR_DESCRIPTION);
        }
        this.z.gotoNextFlow(this.v, fondoBean, this.t);
    }

    public void gotoInformacionFondo(InformacionFondoBean informacionFondoBean, FondoBean fondoBean) {
        Intent intent = new Intent(this, InformacionFondoActivity.class);
        informacionFondoBean.setAptoSuscrip(fondoBean.getAptoSuscrip());
        intent.putExtra("DETALLE_FONDO", informacionFondoBean);
        intent.putParcelableArrayListExtra("CUENTAS", this.s);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO, this.A);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, this.B);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, this.C);
        startActivityForResult(intent, 3);
    }

    public void gotoCotizacionesFondos(List<CategoriaFondosBean> list, LegalesFondosBean legalesFondosBean) {
        Intent intent = new Intent(this, CotizacionesFondoActivity.class);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_CATEGORIAS, (ArrayList) list);
        intent.putParcelableArrayListExtra("CUENTAS", this.s);
        intent.putExtra(FondosConstants.INTENT_EXTRA_LEGALES, legalesFondosBean);
        startActivityForResult(intent, 3);
    }

    private CuentaFondosBean a(FondoBean fondoBean) {
        Boolean valueOf = Boolean.valueOf(false);
        if (this.s != null && this.s.size() > 0) {
            Iterator it = this.s.iterator();
            while (it.hasNext()) {
                CuentaFondosBean cuentaFondosBean = (CuentaFondosBean) it.next();
                if (cuentaFondosBean.getListaFondos() != null) {
                    Iterator it2 = cuentaFondosBean.getListaFondos().getFondosBean().iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (a((FondoBean) it2.next(), fondoBean)) {
                                valueOf = Boolean.valueOf(true);
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                if (valueOf.booleanValue()) {
                    return cuentaFondosBean;
                }
            }
        }
        return null;
    }

    private boolean a(FondoBean fondoBean, FondoBean fondoBean2) {
        try {
            if (!fondoBean.getNombre().equalsIgnoreCase(fondoBean2.getNombre()) || !fondoBean.getId().equalsIgnoreCase(fondoBean2.getId()) || !fondoBean.getVariacionCotizaDiaria().equalsIgnoreCase(fondoBean2.getVariacionCotizaDiaria()) || !fondoBean.getCantidadCuotapartes().equalsIgnoreCase(fondoBean2.getCantidadCuotapartes()) || !fondoBean.getValorCuotaParte().equalsIgnoreCase(fondoBean2.getValorCuotaParte()) || !fondoBean.getImporte().equalsIgnoreCase(fondoBean2.getImporte()) || !fondoBean.getMoneda().equalsIgnoreCase(fondoBean2.getMoneda()) || !Html.fromHtml(fondoBean.getPlazoPago()).toString().equalsIgnoreCase(Html.fromHtml(fondoBean2.getPlazoPago()).toString()) || !fondoBean.getHorarioDesde().equalsIgnoreCase(fondoBean2.getHorarioDesde()) || !fondoBean.getHorarioHasta().equalsIgnoreCase(fondoBean2.getHorarioHasta())) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private Boolean a(String str) {
        String str2;
        Boolean valueOf = Boolean.valueOf(false);
        List<Cuenta> listAccountsUserFromSession = CAccounts.getInstance(this.sessionManager).getListAccountsUserFromSession();
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
            if (UtilAccount.getCurrencyOfAccount(this.sessionManager, cuenta).equalsIgnoreCase(str2)) {
                return Boolean.valueOf(true);
            }
        }
        return valueOf;
    }

    private void j() {
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

    public void gotoSuscribirFondo(MovCtasBodyResponseBean movCtasBodyResponseBean, FondoBean fondoBean) {
        if (!a(fondoBean.getMoneda()).booleanValue()) {
            j();
            return;
        }
        Intent intent = new Intent(this, SuscribirFondoActivity.class);
        CuentaFondosBean a = a(fondoBean);
        if (a != null) {
            intent.putExtra("CUENTA", a);
            intent.putExtra("ORIGEN", FondosConstants.ORIGEN_TENENCIAS);
            intent.putExtra(FondosConstants.INTENT_EXTRA_ACCION, FondosConstants.ACCION_SUSCRIBIR_CUOTAPARTES);
        } else {
            intent.putExtra("CUENTA", this.t);
            intent.putExtra("ORIGEN", FondosConstants.ORIGEN_TENENCIAS);
            intent.putExtra(FondosConstants.INTENT_EXTRA_ACCION, FondosConstants.ACCION_SUSCRIBIR_NUEVO);
            if (TextUtils.isEmpty(fondoBean.getMoneda())) {
                try {
                    if (this.t.getListaFondos() != null && this.t.getListaFondos().getFondosBean().size() > 0) {
                        fondoBean.setMoneda(((FondoBean) this.t.getListaFondos().getFondosBean().get(0)).getMoneda());
                    } else if (UtilAmount.getAmount(this.t.getImportePesos()) > 0.0d) {
                        fondoBean.setMoneda(Constants.SYMBOL_CURRENCY_PESOS_DESCRIPTION);
                    } else {
                        fondoBean.setMoneda(Constants.SYMBOL_CURRENCY_DOLLAR_DESCRIPTION);
                    }
                } catch (Exception unused) {
                    fondoBean.setMoneda(Constants.SYMBOL_CURRENCY_PESOS_DESCRIPTION);
                }
            } else if (fondoBean.getMoneda().equalsIgnoreCase("0")) {
                fondoBean.setMoneda(Constants.SYMBOL_CURRENCY_PESOS_DESCRIPTION);
            } else if (fondoBean.getMoneda().equalsIgnoreCase("2")) {
                fondoBean.setMoneda(Constants.SYMBOL_CURRENCY_DOLLAR_DESCRIPTION);
            }
        }
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, fondoBean);
        intent.putExtra("CUENTAS", this.s);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, this.C);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO, this.A);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, this.B);
        startActivityForResult(intent, 6);
    }

    public void gotoTransferirFondo(FondoBean fondoBean) {
        Intent intent = new Intent();
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, fondoBean);
        setResult(-1, intent);
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void gotoUltimosMovimientosFondo(FondoBean fondoBean, List<MovimientoFondosBean> list) {
        Intent intent = new Intent(this, DetalleFondoActivity.class);
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, fondoBean);
        intent.putExtra("ORIGEN", FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS);
        intent.putExtra("CUENTA", this.t);
        intent.putExtra(FondosConstants.INTENT_EXTRA_LST_MOVIMIENTOS, (ArrayList) list);
        startActivity(intent);
    }

    public void gotoInfoAdmFondos(ListaFondosBean listaFondosBean, LegalesAdmFondos legalesAdmFondos, String str) {
        Intent intent = new Intent(this, InfoAdmFondosActivity.class);
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDOS, listaFondosBean);
        intent.putExtra(FondosConstants.INTENT_EXTRA_LEGALES, legalesAdmFondos);
        intent.putExtra(FondosConstants.INTENT_EXTRA_INFOADM_VIEWMODE, str);
        startActivity(intent);
        this.w.trackEvent(getString(R.string.analytics_event_category_fondos), getString(R.string.analytics_event_action_cancelar), getString(R.string.analytics_event_label_vista_pantalla_documentacion_fondos));
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent)) {
            if (i == 3) {
                dismissProgressIndicator();
            } else if (i != 6) {
                return;
            }
            if (i2 == -1 && intent != null) {
                setResult(-1, intent);
                finish();
            }
        }
    }

    private ArrayList<FondoBean> a(CuentaFondosBean cuentaFondosBean) {
        ArrayList<FondoBean> arrayList = new ArrayList<>();
        if (cuentaFondosBean.getListaFondos() != null && cuentaFondosBean.getListaFondos().getFondosBean().size() > 0) {
            for (FondoBean fondoBean : cuentaFondosBean.getListaFondos().getFondosBean()) {
                if (fondoBean.getAptoSuscrip().equalsIgnoreCase("S")) {
                    arrayList.add(fondoBean);
                }
            }
        }
        return arrayList;
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }
}
