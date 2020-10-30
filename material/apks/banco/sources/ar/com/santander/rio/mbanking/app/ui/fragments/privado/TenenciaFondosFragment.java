package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.TenenciaFondosPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.TenenciaFondosView;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.ui.activities.DetalleFondoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarFondoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarInformacionFondoActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants.LeyendasLegales;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.utils.CuentasUtils;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaFondosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaFondosBean;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class TenenciaFondosFragment extends BaseMvpFragment implements OnClickListener, TenenciaFondosView {
    @Inject
    IDataManager a;
    private String ad = null;
    private List<Leyenda> ae;
    private CuentaFondosBean af = null;
    private String ag;
    private String ah;
    private String ai;
    @Inject
    SoftTokenManager b;
    @InjectView(2131362870)
    Button btnConoceMas;
    @InjectView(2131362871)
    Button btnSuscribirFondo;
    @Inject
    SessionManager c;
    @Inject
    AnalyticsManager d;
    private InversionesAnalyticsImpl e;
    /* access modifiers changed from: private */
    public TenenciaFondosPresenter f;
    public ArrayList<FondoBean> fondos;
    private GetTenenciaFondosBodyResponseBean g;
    /* access modifiers changed from: private */
    public IsbanDialogFragment h;
    private String i = null;
    @InjectView(2131362874)
    TextView lblCuentaTitulo;
    @InjectView(2131362879)
    TextView lblDataLegalesCuenta;
    @InjectView(2131362880)
    TextView lblDataTotalDolares;
    @InjectView(2131362881)
    TextView lblDataTotalPesos;
    @InjectView(2131362905)
    TextView lblTxtErrorRes4;
    @InjectView(2131362891)
    LinearLayout lytLista;
    public CuentaFondosBean mCuentaSeleccionada;
    public ArrayList<CuentaFondosBean> mListaCuentas;
    public View mMainView;
    @InjectView(2131362895)
    RelativeLayout mainView;
    @InjectView(2131362906)
    HtmlTextView noTenenciasTextMessage;
    @InjectView(2131362893)
    RelativeLayout rwCaraTriste;
    @InjectView(2131362902)
    LinearLayout rwLnImporte;
    @InjectView(2131362898)
    RelativeLayout rwSignoMas;
    @InjectView(2131365636)
    ScrollView scrollTenencias;
    @InjectView(2131362907)
    CustomSpinner vgSelectorAccountTenencias;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public ArrayList<FondoBean> getFondos() {
        return this.fondos;
    }

    public void setFondos(ArrayList<FondoBean> arrayList) {
        this.fondos = arrayList;
    }

    public static TenenciaFondosFragment newInstance(String str, String str2) {
        TenenciaFondosFragment tenenciaFondosFragment = new TenenciaFondosFragment();
        tenenciaFondosFragment.ag = str2;
        Bundle bundle = new Bundle();
        bundle.putString("NRO_CUENTA", str);
        tenenciaFondosFragment.setArguments(bundle);
        return tenenciaFondosFragment;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.d.trackScreen(getString(R.string.analytics_screen_mostrar_fondos_inversion));
        this.mMainView = layoutInflater.inflate(R.layout.fragment_tenencia_fondos, viewGroup, false);
        this.e = new InversionesAnalyticsImpl(getContext(), this.d);
        ButterKnife.inject((Object) this, this.mMainView);
        if (getArguments() != null) {
            this.ah = getArguments().getString("NRO_CUENTA");
        }
        initialize();
        configureActionBar();
        return this.mMainView;
    }

    public void onStart() {
        super.onStart();
        this.d.setScreenName(getString(R.string.analytics_screen_mostrar_fondos_inversion));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.F24_00_BTN_CONOCE_MAS /*2131362870*/:
                this.f.callGetFondos(FondosConstants.ORIGEN_INFORMACION);
                return;
            case R.id.F24_00_BTN_SUSCRIBIR_FONDO /*2131362871*/:
                this.f.callGetFondos(FondosConstants.ORIGEN_SUSCRIBIR);
                return;
            case R.id.F24_00_vgSelectorAccountTenencias /*2131362907*/:
                this.f.showSelectAccountDialog(this.mCuentaSeleccionada, this.mListaCuentas);
                return;
            default:
                return;
        }
    }

    public void setTenenciaFondosView(List<CuentaFondosBean> list, Boolean bool, String str, String str2, List<Leyenda> list2, String str3) {
        this.i = str;
        this.ad = str2;
        this.ae = list2;
        this.mainView.setVisibility(0);
        this.mListaCuentas = (ArrayList) list;
        Iterator it = this.mListaCuentas.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            CuentaFondosBean cuentaFondosBean = (CuentaFondosBean) it.next();
            if (cuentaFondosBean.getTipoCuenta().equalsIgnoreCase("08") || (Integer.valueOf(cuentaFondosBean.getSucursalCuenta()).intValue() >= 250 && Integer.valueOf(cuentaFondosBean.getSucursalCuenta()).intValue() <= 259)) {
                i2++;
            }
        }
        if (i2 < 2) {
            this.vgSelectorAccountTenencias.setVisibility(8);
        }
        this.vgSelectorAccountTenencias.setOnClickListener(this);
        this.btnSuscribirFondo.setOnClickListener(this);
        this.btnSuscribirFondo.setVisibility(0);
        this.btnConoceMas.setOnClickListener(this);
        CuentaFondosBean cuentaFondosBean2 = null;
        if (this.af == null) {
            Iterator it2 = this.mListaCuentas.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                CuentaFondosBean cuentaFondosBean3 = (CuentaFondosBean) it2.next();
                if (cuentaFondosBean3.getTipoCuenta().equalsIgnoreCase("08") || (Integer.valueOf(cuentaFondosBean3.getSucursalCuenta()).intValue() >= 250 && Integer.valueOf(cuentaFondosBean3.getSucursalCuenta()).intValue() <= 259)) {
                    cuentaFondosBean2 = cuentaFondosBean3;
                }
            }
            if (TextUtils.isEmpty(this.ah) && this.mListaCuentas.size() > 0 && !bool.booleanValue()) {
                setSelectedAccount(cuentaFondosBean2);
            } else if (!TextUtils.isEmpty(this.ah) && this.mListaCuentas.size() > 0 && !bool.booleanValue()) {
                setSelectedAccount((CuentaFondosBean) this.mListaCuentas.get(CuentasUtils.getFondosAccountIndex(this.mListaCuentas, CuentasUtils.getCuentaFromString(this.ah))));
            } else if (this.mListaCuentas.size() > 0) {
                setErrorScreen(str3);
                setSelectedAccount(cuentaFondosBean2);
            } else {
                ((SantanderRioMainActivity) getActivity()).setErrorFragment(getString(R.string.F24_00_TXT_SIN_CUENTAS_TITULO), (int) R.drawable.cruz_blanca_fondo_celeste, getActivity().getResources().getString(R.string.F24_00_LBL_TITULO_TENENCIA_DE_FONDOS));
            }
        } else {
            a(this.af);
            this.af = null;
        }
        y();
        b(this.ah);
    }

    private void y() {
        if (this.ae != null && this.ae.size() > 0) {
            for (int i2 = 0; i2 < this.ae.size(); i2++) {
                if (((Leyenda) this.ae.get(i2)).getIdLeyenda().equals(LeyendasLegales.TENENCIA)) {
                    this.lblDataLegalesCuenta.setText(Html.fromHtml(((Leyenda) this.ae.get(i2)).getDescripcion()).toString());
                }
            }
        }
        try {
            this.lblDataLegalesCuenta.setContentDescription(new CAccessibility(getActivity()).applyFilterGeneral(this.lblDataLegalesCuenta.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void b(String str) {
        if (str != null) {
            int sucCuentaFromString = CuentasUtils.getSucCuentaFromString(str);
            if (sucCuentaFromString < 250 || sucCuentaFromString > 259) {
                this.btnSuscribirFondo.setVisibility(0);
            } else {
                this.btnSuscribirFondo.setVisibility(8);
            }
        }
    }

    private void a(CuentaFondosBean cuentaFondosBean) {
        if (this.mListaCuentas.size() > 0) {
            Iterator it = this.mListaCuentas.iterator();
            while (it.hasNext()) {
                CuentaFondosBean cuentaFondosBean2 = (CuentaFondosBean) it.next();
                if (cuentaFondosBean.getNumero().equalsIgnoreCase(cuentaFondosBean2.getNumero()) && cuentaFondosBean.getTipoCuenta().equalsIgnoreCase(cuentaFondosBean2.getTipoCuenta()) && cuentaFondosBean.getSucursalCuenta().equalsIgnoreCase(cuentaFondosBean2.getSucursalCuenta())) {
                    setSelectedAccount(cuentaFondosBean2);
                    return;
                }
            }
        }
    }

    public void setErrorScreen(String str) {
        this.rwLnImporte.setVisibility(8);
        this.lytLista.setVisibility(8);
        this.rwCaraTriste.setVisibility(0);
        this.btnSuscribirFondo.setOnClickListener(this);
        if (TextUtils.isEmpty(str)) {
            this.lblTxtErrorRes4.setText(getString(R.string.F24_00_TXT_CARA_TRISTE));
        } else {
            this.lblTxtErrorRes4.setText(Html.fromHtml(str));
        }
    }

    public void setSelectedAccount(CuentaFondosBean cuentaFondosBean) {
        this.scrollTenencias.scrollTo(0, 0);
        if (Integer.valueOf(cuentaFondosBean.getSucursalCuenta()).intValue() < 250 || Integer.valueOf(cuentaFondosBean.getSucursalCuenta()).intValue() > 259) {
            this.e.registerEventCambiarCuentaFondosComunesTitular();
        } else {
            this.e.registerEventCambiarCuentaFondosComunesBP();
        }
        this.mCuentaSeleccionada = cuentaFondosBean;
        this.lblCuentaTitulo.setText(cuentaFondosBean.getFormattedSucCuentaLarge());
        try {
            this.lblCuentaTitulo.setContentDescription(new CAccessibility(getActivity()).applyFilterGeneral(cuentaFondosBean.getFormattedSucCuentaLarge()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.mCuentaSeleccionada.getImportePesos() == null) {
            this.lblDataTotalPesos.setText("$ 0,00");
        } else {
            this.lblDataTotalPesos.setText(this.mCuentaSeleccionada.getImportePesos());
        }
        if (this.mCuentaSeleccionada.getImporteDolares() == null) {
            this.lblDataTotalPesos.setText("U$S 0,00");
        } else {
            this.lblDataTotalPesos.setText(this.mCuentaSeleccionada.getImportePesos());
        }
        try {
            this.lblDataTotalPesos.setContentDescription(new CAccessibility(getActivity()).applyFilterAmount(this.mCuentaSeleccionada.getImportePesos()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.lblDataTotalDolares.setText(this.mCuentaSeleccionada.getImporteDolares());
        try {
            this.lblDataTotalDolares.setContentDescription(new CAccessibility(getActivity()).applyFilterAmount(this.mCuentaSeleccionada.getImporteDolares()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        y();
        if (this.mCuentaSeleccionada.getListaFondos() == null || this.mCuentaSeleccionada.getListaFondos().getFondosBean().size() <= 0) {
            this.fondos = null;
            this.rwLnImporte.setVisibility(8);
            this.lytLista.setVisibility(8);
            this.lblDataLegalesCuenta.setVisibility(8);
            if (this.mCuentaSeleccionada.isBancaPrivada() || !this.mCuentaSeleccionada.hasFondosBloqueados()) {
                this.btnConoceMas.setVisibility(0);
            } else {
                this.btnConoceMas.setVisibility(8);
            }
            if (this.rwCaraTriste.getVisibility() == 0) {
                this.rwSignoMas.setVisibility(8);
                this.btnConoceMas.setVisibility(8);
                this.btnSuscribirFondo.setVisibility(8);
            } else {
                if (this.mCuentaSeleccionada == null || !this.mCuentaSeleccionada.isBancaPrivada()) {
                    this.btnSuscribirFondo.setVisibility(0);
                } else {
                    this.btnSuscribirFondo.setVisibility(8);
                }
                String string = getString(R.string.F24_00_TXT_SIGNO_MAS);
                if (!TextUtils.isEmpty(cuentaFondosBean.getMensajeDescripcion())) {
                    string = cuentaFondosBean.getMensajeDescripcion();
                }
                this.noTenenciasTextMessage.setText(string);
                this.rwSignoMas.setVisibility(0);
                this.d.trackScreen(getString(R.string.analytics_screen_mostrar_fondos_sin_suscripcion));
                this.d.setScreenName(getString(R.string.analytics_screen_mostrar_fondos_inversion));
            }
            this.btnSuscribirFondo.setOnClickListener(this);
            return;
        }
        this.fondos = (ArrayList) this.mCuentaSeleccionada.getListaFondos().getFondosBean();
        addBlockBodyList(this.f.getViewList(this.fondos));
        this.rwLnImporte.setVisibility(0);
        this.lytLista.setVisibility(0);
        this.lblDataLegalesCuenta.setVisibility(0);
        this.rwSignoMas.setVisibility(8);
        this.btnConoceMas.setVisibility(8);
        this.btnSuscribirFondo.setOnClickListener(this);
        if (this.mCuentaSeleccionada.isBancaPrivada() || this.rwCaraTriste.getVisibility() == 0) {
            this.btnSuscribirFondo.setVisibility(8);
        } else {
            this.btnSuscribirFondo.setVisibility(0);
        }
    }

    public void addBlockBodyList(View view) {
        this.lytLista.removeAllViews();
        this.lytLista.addView(view);
    }

    public void gotoDetalleFondo(FondoBean fondoBean) {
        Intent intent = new Intent(getActivity(), DetalleFondoActivity.class);
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, fondoBean);
        intent.putExtra("CUENTA", this.mCuentaSeleccionada);
        intent.putExtra("CUENTAS", this.mListaCuentas);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO_TRANSF, this.ad);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO_SUSC, this.i);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, (ArrayList) this.ae);
        intent.putExtra("ORIGEN", "DETALLE_FONDO");
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, getCuentasOperativasFromResponse(this.g));
        startActivityForResult(intent, 1);
    }

    public void initialize() {
        this.ai = this.c.getLoginUnico().getDatosPersonales().getTipoCliente();
        TenenciaFondosPresenter tenenciaFondosPresenter = new TenenciaFondosPresenter(this.mBus, this.mDataManager, this.c, getActivity(), this.d);
        this.f = tenenciaFondosPresenter;
        this.f.attachView(this);
        this.f.onGetTenencias();
    }

    public void configureActionBar() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TenenciaFondosFragment.this.switchDrawer();
                }
            });
        }
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TenenciaFondosFragment.this.z();
                    if (TenenciaFondosFragment.this.h != null) {
                        TenenciaFondosFragment.this.h.show(TenenciaFondosFragment.this.getFragmentManager(), "Dialog");
                        TenenciaFondosFragment.this.d.trackEvent(TenenciaFondosFragment.this.getString(R.string.analytics_event_category_fondos), TenenciaFondosFragment.this.getString(R.string.analytics_event_action_cancelar), TenenciaFondosFragment.this.getString(R.string.analytics_event_label_opciones_fondos));
                    }
                }
            });
        }
    }

    public void attachView() {
        if (!this.f.isViewAttached()) {
            this.f.attachView(this);
        }
    }

    public void detachView() {
        if (this.f.isViewAttached()) {
            this.f.detachView();
        }
    }

    /* access modifiers changed from: private */
    public void z() {
        if (this.mCuentaSeleccionada != null) {
            ArrayList arrayList = new ArrayList();
            boolean[] zArr = new boolean[5];
            arrayList.add(getResources().getString(R.string.F24_00_ACTIONSHEET_INFORMACION_FONDOS));
            zArr[0] = true;
            if (!this.mCuentaSeleccionada.isBancaPrivada()) {
                arrayList.add(getResources().getString(R.string.F24_06_LBL_TITULO_ULTIMOS_MOVIMIENTOS));
                zArr[1] = true;
                if (this.mCuentaSeleccionada == null || this.rwCaraTriste.getVisibility() == 0) {
                    arrayList.add(getResources().getString(R.string.F24_00_ACTIONSHEET_SUSCRIBIR));
                    zArr[2] = false;
                } else {
                    arrayList.add(getResources().getString(R.string.F24_00_ACTIONSHEET_SUSCRIBIR));
                    zArr[2] = true;
                }
                arrayList.add(getResources().getString(R.string.F24_00_ACTIONSHEET_TRANSFERIR));
                arrayList.add(getResources().getString(R.string.F24_00_ACTIONSHEET_RESCATAR));
                if (this.rwCaraTriste.getVisibility() == 0 || this.mCuentaSeleccionada.getListaFondos() == null || this.mCuentaSeleccionada.getListaFondos().getFondosBean().size() <= 0) {
                    zArr[3] = false;
                    zArr[4] = false;
                } else {
                    if (b(this.mCuentaSeleccionada) == null || b(this.mCuentaSeleccionada).size() <= 0) {
                        zArr[3] = false;
                    } else {
                        zArr[3] = true;
                    }
                    zArr[4] = true;
                }
            }
            this.h = IsbanDialogFragment.newInstance("mPopupMenu", null, null, arrayList, getString(R.string.F24_00_ACTIONSHEET_CANCELAR), null, null, null, null, zArr);
            this.h.setDialogListener(new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onItemSelected(String str) {
                    if (str.equalsIgnoreCase(TenenciaFondosFragment.this.getResources().getString(R.string.F24_00_ACTIONSHEET_RESCATAR))) {
                        TenenciaFondosFragment.this.gotoRescatarFondo();
                    } else if (str.equalsIgnoreCase(TenenciaFondosFragment.this.getResources().getString(R.string.F24_06_LBL_TITULO_ULTIMOS_MOVIMIENTOS))) {
                        TenenciaFondosFragment.this.f.callGetFondos(FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS);
                    } else if (str.equalsIgnoreCase(TenenciaFondosFragment.this.getResources().getString(R.string.F24_00_ACTIONSHEET_SUSCRIBIR))) {
                        TenenciaFondosFragment.this.f.callGetFondos(FondosConstants.ORIGEN_SUSCRIBIR);
                    } else if (str.equalsIgnoreCase(TenenciaFondosFragment.this.getResources().getString(R.string.F24_00_ACTIONSHEET_INFORMACION_FONDOS))) {
                        TenenciaFondosFragment.this.f.callGetFondos(FondosConstants.ORIGEN_INFORMACION);
                    } else if (str.equalsIgnoreCase(TenenciaFondosFragment.this.getResources().getString(R.string.F24_00_ACTIONSHEET_TRANSFERIR))) {
                        TenenciaFondosFragment.this.gotoTransferirFondo();
                    } else if (str.equalsIgnoreCase(PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT)) {
                        TenenciaFondosFragment.this.d.trackEvent(TenenciaFondosFragment.this.getString(R.string.analytics_event_category_fondos), TenenciaFondosFragment.this.getString(R.string.analytics_event_action_cancelar), TenenciaFondosFragment.this.getString(R.string.analytics_event_label_opciones_fondos));
                    }
                }

                public void onSimpleActionButton() {
                    TenenciaFondosFragment.this.d.trackEvent(TenenciaFondosFragment.this.getString(R.string.analytics_event_category_fondos), TenenciaFondosFragment.this.getString(R.string.analytics_event_action_cancelar), TenenciaFondosFragment.this.getString(R.string.analytics_event_label_cancelar_consulta_fondos));
                }
            });
        }
    }

    public void gotoInformacionFondosActivity(List<CategoriaFondosBean> list) {
        Intent intent = new Intent(getActivity(), SeleccionarInformacionFondoActivity.class);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CATEGORIAS, (ArrayList) list);
        intent.putExtra("CUENTAS", this.mListaCuentas);
        intent.putExtra("ORIGEN", FondosConstants.ORIGEN_INFORMACION);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO, this.i);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, (ArrayList) this.ae);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, getCuentasOperativasFromResponse(this.g));
        startActivityForResult(intent, 3);
    }

    private ArrayList<CategoriaFondosBean> a(List<CategoriaFondosBean> list) {
        ArrayList<CategoriaFondosBean> arrayList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (CategoriaFondosBean categoriaFondosBean : list) {
                if (categoriaFondosBean.getFondosBean() != null && categoriaFondosBean.getFondosBean().getFondosBean().size() > 0) {
                    ArrayList arrayList2 = new ArrayList();
                    for (FondoBean fondoBean : categoriaFondosBean.getFondosBean().getFondosBean()) {
                        if (fondoBean.getAptoSuscrip().equalsIgnoreCase("S")) {
                            arrayList2.add(fondoBean);
                        }
                    }
                    if (arrayList2.size() > 0) {
                        arrayList.add(new CategoriaFondosBean(categoriaFondosBean.getIdCategoria(), categoriaFondosBean.getNombreCategoria(), new ListaFondosBean((List<FondoBean>) arrayList2)));
                    }
                }
            }
        }
        return arrayList;
    }

    private ArrayList<FondoBean> b(CuentaFondosBean cuentaFondosBean) {
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

    public void gotoSuscribirFondoActivity(List<CategoriaFondosBean> list, GetTenenciaFondosBodyResponseBean getTenenciaFondosBodyResponseBean) {
        Intent intent = new Intent(getActivity(), SeleccionarInformacionFondoActivity.class);
        intent.putExtra("ORIGEN", FondosConstants.ORIGEN_SUSCRIBIR);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO, this.i);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CATEGORIAS, a(list));
        intent.putParcelableArrayListExtra("CUENTAS", this.mListaCuentas);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, (ArrayList) this.ae);
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, getCuentasOperativasFromResponse(this.g));
        intent.putExtra("CUENTA", this.mCuentaSeleccionada);
        startActivityForResult(intent, 6);
    }

    public void gotoUltimosMovimientosActivity(List<CategoriaFondosBean> list) {
        Intent intent = new Intent(getActivity(), SeleccionarInformacionFondoActivity.class);
        intent.putExtra("ORIGEN", FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CATEGORIAS, (ArrayList) list);
        intent.putExtra("CUENTA", this.mCuentaSeleccionada);
        startActivityForResult(intent, 6);
    }

    public void gotoRescatarFondo() {
        this.d.trackScreen(getString(R.string.analytics_screen_seleccionar_fondo_rescatar));
        Intent intent = new Intent(getActivity(), SeleccionarFondoActivity.class);
        intent.putExtra(FondosConstants.INTENT_EXTRA_ACCION, FondosConstants.ACCION_RESCATAR);
        intent.putParcelableArrayListExtra("CUENTAS", A());
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_CUENTAS_OPERATIVAS, (ArrayList) this.g.getListaCtaOperativaBean().getCuentasOperativasBean());
        intent.putExtra("CUENTA", this.mCuentaSeleccionada);
        startActivityForResult(intent, 4);
    }

    public void gotoTransferirFondo() {
        Intent intent = new Intent(getActivity(), SeleccionarFondoActivity.class);
        intent.putExtra(FondosConstants.INTENT_EXTRA_ACCION, FondosConstants.ACCION_TRANSFERIR);
        intent.putExtra(FondosConstants.INTENT_EXTRA_CONTRATO, this.ad);
        ArrayList B = B();
        intent.putParcelableArrayListExtra("CUENTAS", B);
        if (b(this.mCuentaSeleccionada).size() > 0) {
            CuentaFondosBean cuentaFondosBean = new CuentaFondosBean(this.mCuentaSeleccionada.getTipoCuenta(), this.mCuentaSeleccionada.getSucursalCuenta(), this.mCuentaSeleccionada.getNumero(), this.mCuentaSeleccionada.getImportePesos(), this.mCuentaSeleccionada.getImporteDolares(), new ListaFondosBean((List<FondoBean>) b(this.mCuentaSeleccionada)), this.mCuentaSeleccionada.getMensajeCodigo(), this.mCuentaSeleccionada.getMensajeTitulo(), this.mCuentaSeleccionada.getMensajeDescripcion());
            intent.putExtra("CUENTA", cuentaFondosBean);
        } else if (B != null && B.size() > 0) {
            intent.putExtra("CUENTA", (Parcelable) B.get(0));
        }
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LEGALES, (ArrayList) this.ae);
        startActivityForResult(intent, 5);
    }

    private ArrayList<CuentaFondosBean> A() {
        ArrayList<CuentaFondosBean> arrayList = new ArrayList<>();
        if (this.mListaCuentas != null && this.mListaCuentas.size() > 0) {
            Iterator it = this.mListaCuentas.iterator();
            while (it.hasNext()) {
                CuentaFondosBean cuentaFondosBean = (CuentaFondosBean) it.next();
                if (!cuentaFondosBean.isBancaPrivada()) {
                    arrayList.add(cuentaFondosBean);
                }
            }
        }
        return arrayList;
    }

    private ArrayList<CuentaFondosBean> B() {
        ArrayList<CuentaFondosBean> arrayList = new ArrayList<>();
        if (this.mListaCuentas != null && this.mListaCuentas.size() > 0) {
            Iterator it = this.mListaCuentas.iterator();
            while (it.hasNext()) {
                CuentaFondosBean cuentaFondosBean = (CuentaFondosBean) it.next();
                if (cuentaFondosBean.getListaFondos().getFondosBean() != null && cuentaFondosBean.getListaFondos().getFondosBean().size() > 0 && !cuentaFondosBean.isBancaPrivada()) {
                    ArrayList arrayList2 = new ArrayList();
                    for (FondoBean fondoBean : cuentaFondosBean.getListaFondos().getFondosBean()) {
                        if (fondoBean.getAptoSuscrip().equalsIgnoreCase("S")) {
                            arrayList2.add(fondoBean);
                        }
                    }
                    if (arrayList2.size() > 0) {
                        CuentaFondosBean cuentaFondosBean2 = new CuentaFondosBean(cuentaFondosBean.getTipoCuenta(), cuentaFondosBean.getSucursalCuenta(), cuentaFondosBean.getNumero(), cuentaFondosBean.getImportePesos(), cuentaFondosBean.getImporteDolares(), new ListaFondosBean((List<FondoBean>) arrayList2), cuentaFondosBean.getMensajeCodigo(), cuentaFondosBean.getMensajeTitulo(), cuentaFondosBean.getMensajeDescripcion());
                        arrayList.add(cuentaFondosBean2);
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0045, code lost:
        if (r4.equals(ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants.GO_TO_LOGIN) != false) goto L_0x0067;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r3, int r4, android.content.Intent r5) {
        /*
            r2 = this;
            r3 = 0
            r0 = -1
            if (r4 != r0) goto L_0x001d
            java.lang.String r1 = "PrivateMenuSelectedOptionPosition"
            boolean r1 = r5.hasExtra(r1)
            if (r1 == 0) goto L_0x001d
            android.support.v4.app.FragmentActivity r4 = r2.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            java.lang.String r0 = "PrivateMenuSelectedOptionPosition"
            int r3 = r5.getIntExtra(r0, r3)
            r4.onPrivateMenuOptionSelectedInNestedActivity(r3)
            goto L_0x00e3
        L_0x001d:
            if (r4 != r0) goto L_0x00cc
            java.lang.String r1 = "WS_ERROR_DO_ACTION"
            boolean r1 = r5.hasExtra(r1)
            if (r1 == 0) goto L_0x00cc
            java.lang.String r4 = "WS_ERROR_DO_ACTION"
            java.lang.String r4 = r5.getStringExtra(r4)
            int r1 = r4.hashCode()
            switch(r1) {
                case -1667304550: goto L_0x005c;
                case -1442009346: goto L_0x0052;
                case -1365838438: goto L_0x0048;
                case -171755572: goto L_0x003f;
                case 4216548: goto L_0x0035;
                default: goto L_0x0034;
            }
        L_0x0034:
            goto L_0x0066
        L_0x0035:
            java.lang.String r3 = "GO_TO_HOME_ERROR_CLOCK"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0066
            r3 = 4
            goto L_0x0067
        L_0x003f:
            java.lang.String r1 = "GO_TO_HOME"
            boolean r4 = r4.equals(r1)
            if (r4 == 0) goto L_0x0066
            goto L_0x0067
        L_0x0048:
            java.lang.String r3 = "GO_TO_HOME_FUNCIONALIDAD"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0066
            r3 = 1
            goto L_0x0067
        L_0x0052:
            java.lang.String r3 = "GO_TO_CUENTAS"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0066
            r3 = 2
            goto L_0x0067
        L_0x005c:
            java.lang.String r3 = "GO_TO_HOME_ERROR_FRAGMENT"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0066
            r3 = 3
            goto L_0x0067
        L_0x0066:
            r3 = -1
        L_0x0067:
            r4 = 2131755210(0x7f1000ca, float:1.9141293E38)
            switch(r3) {
                case 0: goto L_0x00c2;
                case 1: goto L_0x00b6;
                case 2: goto L_0x00aa;
                case 3: goto L_0x008e;
                case 4: goto L_0x006f;
                default: goto L_0x006d;
            }
        L_0x006d:
            goto L_0x00e3
        L_0x006f:
            android.support.v4.app.FragmentActivity r3 = r2.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            java.lang.String r0 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r5 = r5.getStringExtra(r0)
            r0 = 2131231076(0x7f080164, float:1.8078223E38)
            android.support.v4.app.FragmentActivity r1 = r2.getActivity()
            android.content.res.Resources r1 = r1.getResources()
            java.lang.String r4 = r1.getString(r4)
            r3.setErrorFragment(r5, r0, r4)
            goto L_0x00e3
        L_0x008e:
            android.support.v4.app.FragmentActivity r3 = r2.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            java.lang.String r0 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r5 = r5.getStringExtra(r0)
            android.support.v4.app.FragmentActivity r0 = r2.getActivity()
            android.content.res.Resources r0 = r0.getResources()
            java.lang.String r4 = r0.getString(r4)
            r3.setErrorFragment(r5, r4)
            goto L_0x00e3
        L_0x00aa:
            android.support.v4.app.FragmentActivity r3 = r2.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS
            r3.goToOption(r4)
            goto L_0x00e3
        L_0x00b6:
            android.support.v4.app.FragmentActivity r3 = r2.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            java.lang.String r4 = "FONDOS_INVERSION"
            r3.goToOption(r4)
            goto L_0x00e3
        L_0x00c2:
            android.support.v4.app.FragmentActivity r3 = r2.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            r3.gotoHome()
            goto L_0x00e3
        L_0x00cc:
            if (r4 != r0) goto L_0x00e3
            java.lang.String r3 = "RECARGAR_LISTADO_FONDOS"
            boolean r3 = r5.hasExtra(r3)
            if (r3 == 0) goto L_0x00e3
            java.lang.String r3 = "CUENTA"
            android.os.Parcelable r3 = r5.getParcelableExtra(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean r3 = (ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean) r3
            r2.af = r3
            r2.initialize()
        L_0x00e3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaFondosFragment.onActivityResult(int, int, android.content.Intent):void");
    }

    public void setTenenciaFondosBodyResponseBean(GetTenenciaFondosBodyResponseBean getTenenciaFondosBodyResponseBean) {
        this.g = getTenenciaFondosBodyResponseBean;
    }

    public boolean isFromDeepLink() {
        return this.ag != null && this.ag.equals(FragmentConstants.FONDOS_SUSCRIPCION);
    }

    public ArrayList<CuentaOperativaBean> getCuentasOperativasFromResponse(GetTenenciaFondosBodyResponseBean getTenenciaFondosBodyResponseBean) {
        return (ArrayList) getTenenciaFondosBodyResponseBean.getListaCtaOperativaBean().getCuentasOperativasBean();
    }
}
