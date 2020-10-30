package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.debin.adhesion.ComprobanteGestionAdhesionDebinPresenter;
import ar.com.santander.rio.mbanking.app.module.debin.adhesion.ComprobanteGestionAdhesionDebinView;
import ar.com.santander.rio.mbanking.app.module.debin.adhesion.ConfirmarGestionAdhesionDebinPresenter;
import ar.com.santander.rio.mbanking.app.module.debin.adhesion.ConfirmarGestionAdhesionDebinView;
import ar.com.santander.rio.mbanking.app.module.debin.adhesion.GestionAdhesionDebinPresenter;
import ar.com.santander.rio.mbanking.app.module.debin.adhesion.GestionAdhesionDebinView;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.adapters.GestionAdhesionCuentasDebinAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.GestionAdhesionCuentasDebinAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmAdhesionVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarAdhesionVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaVendedor;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCuentasVendedorBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class GestionAdhesionDebinActivity extends MvpPrivateMenuActivity implements OnClickListener, ComprobanteGestionAdhesionDebinView, ConfirmarGestionAdhesionDebinView, GestionAdhesionDebinView, OnItemClickListener {
    /* access modifiers changed from: private */
    public String A = "";
    /* access modifiers changed from: private */
    public GestionAdhesionCuentasDebinAdapter B;
    /* access modifiers changed from: private */
    public IsbanDialogFragment C;
    /* access modifiers changed from: private */
    public OptionsToShare D;
    /* access modifiers changed from: private */
    public boolean E = false;
    /* access modifiers changed from: private */
    public String F = "";
    /* access modifiers changed from: private */
    public String G = "";
    @InjectView(2131363904)
    TextView btnConfirmarn;
    @InjectView(2131363912)
    Button btnVolverComprobante;
    @InjectView(2131363911)
    ScrollView comprobanteOperacion;
    @InjectView(2131363913)
    TextView lbl_comprobante_operacion_cuenta;
    @InjectView(2131363920)
    TextView lbl_comprobante_terminos_legales;
    @InjectView(2131363925)
    TextView lbl_comprobante_title;
    @InjectView(2131363905)
    TextView lbl_confirmar_cuentaAdhesion;
    @InjectView(2131363910)
    TextView lbl_confirmar_title;
    @InjectView(2131363915)
    TextView lbl_data_comprobante_fechaOperacion;
    @InjectView(2131363916)
    TextView lbl_data_comprobante_nroOperacion;
    @InjectView(2131363917)
    TextView lbl_data_comprobante_numeroCuentaAdhesion;
    @InjectView(2131363914)
    TextView lbl_data_comprobante_tipoCuentaAdhesion;
    @InjectView(2131363907)
    TextView lbl_data_confirmacion_numeroCuentaAdhesion;
    @InjectView(2131363906)
    TextView lbl_data_tipoCuentaAdhesion;
    @InjectView(2131364717)
    ViewFlipper mControlPager;
    @InjectView(2131363738)
    RecyclerView mRecyclerView;
    @Inject
    AnalyticsManager p;
    @Inject
    SessionManager q;
    @Inject
    SoftTokenManager r;
    private String s;
    private GestionAdhesionDebinPresenter t;
    private ConfirmarGestionAdhesionDebinPresenter u;
    private ComprobanteGestionAdhesionDebinPresenter v;
    /* access modifiers changed from: private */
    public CuentaVendedor w = null;
    private ListaCuentasVendedorBean x;
    private ArrayList<CuentaVendedor> y = new ArrayList<>();
    private ArrayList<CuentaVendedor> z;

    public void clearScreenData() {
    }

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public Context getContext() {
        return this;
    }

    public int getMainLayout() {
        return R.layout.layout_gestion_adhesion_debin_main;
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.p;
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.p.trackScreen(getString(R.string.analytics_screen_name_Cuentas_adheridas_Debin_F_32_20));
        this.t = new GestionAdhesionDebinPresenter(this.mBus, this.mDataManager, this);
        this.u = new ConfirmarGestionAdhesionDebinPresenter(this.mBus, this.mDataManager, this);
        this.v = new ComprobanteGestionAdhesionDebinPresenter(this.mBus);
        this.x = (ListaCuentasVendedorBean) getIntent().getParcelableExtra(DebinConstants.INTENT_EXTRA_CUENTAS_ADHERIDAS);
        this.z = a(CAccounts.getInstance(this.q).getListAccountsUserFromSession());
        b();
        goToListaCuentas(true);
    }

    private void b() {
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    private ArrayList<CuentaVendedor> a(List<Cuenta> list) {
        ArrayList<CuentaVendedor> arrayList = new ArrayList<>();
        for (Cuenta cuenta : list) {
            if (cuenta.getTipo().equalsIgnoreCase("02")) {
                cuenta.setTipo("09");
                if (CAccounts.getInstance(this.q).isAccountOperational(cuenta).booleanValue() && !a(cuenta)) {
                    CuentaVendedor cuentaVendedor = new CuentaVendedor("", "", cuenta.getClaveBancariaUnificada(), cuenta.getAlias(), TransferenciasConstants.cBANCO_DESTINO_BSR, "09", cuenta.getNroSuc(), cuenta.getNumero(), 1);
                    arrayList.add(cuentaVendedor);
                }
                cuenta.setTipo("10");
                if (CAccounts.getInstance(this.q).isAccountOperational(cuenta).booleanValue() && !a(cuenta)) {
                    CuentaVendedor cuentaVendedor2 = new CuentaVendedor("", "", cuenta.getClaveBancariaUnificada(), cuenta.getAlias(), TransferenciasConstants.cBANCO_DESTINO_BSR, "10", cuenta.getNroSuc(), cuenta.getNumero(), 1);
                    arrayList.add(cuentaVendedor2);
                }
                cuenta.setTipo("02");
            } else if (CAccounts.getInstance(this.q).isAccountOperational(cuenta).booleanValue() && !a(cuenta)) {
                CuentaVendedor cuentaVendedor3 = new CuentaVendedor("", "", cuenta.getClaveBancariaUnificada(), cuenta.getAlias(), TransferenciasConstants.cBANCO_DESTINO_BSR, cuenta.getTipo(), cuenta.getNroSuc(), cuenta.getNumero(), 1);
                arrayList.add(cuentaVendedor3);
            }
        }
        return arrayList;
    }

    private void c() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = this.y.iterator();
        while (it.hasNext()) {
            CuentaVendedor cuentaVendedor = (CuentaVendedor) it.next();
            if (cuentaVendedor.getStatusAdhesion().intValue() == 0) {
                arrayList.add(cuentaVendedor);
            } else {
                arrayList2.add(cuentaVendedor);
            }
        }
        this.y.clear();
        this.y.addAll(a(arrayList));
        this.y.addAll(a(arrayList2));
    }

    private ArrayList<CuentaVendedor> a(ArrayList<CuentaVendedor> arrayList) {
        Collections.sort(arrayList, new Comparator<CuentaVendedor>() {
            /* renamed from: a */
            public int compare(CuentaVendedor cuentaVendedor, CuentaVendedor cuentaVendedor2) {
                return (cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) || (cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS)) || ((cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_PESOS) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR)) || ((cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_DOLAR) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_PESOS)) || ((cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CTA_CTE_PESOS) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_PESOS) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_DOLAR)) || ((cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CTA_CTE_DOLAR) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_PESOS) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_DOLAR) && !cuentaVendedor2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CTA_CTE_PESOS)) || (!cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) && !cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR) && !cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_PESOS) && !cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CJA_AHO_DOLAR) && !cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CTA_CTE_PESOS) && !cuentaVendedor.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CTA_CTE_DOLAR))))))) ? -1 : 1;
            }
        });
        return arrayList;
    }

    private boolean a(Cuenta cuenta) {
        if (!(this.x == null || this.x.getCuentaVendedor() == null)) {
            for (CuentaVendedor cuentaVendedor : this.x.getCuentaVendedor()) {
                if (Integer.valueOf(cuentaVendedor.getSucursal()).equals(Integer.valueOf(cuenta.getNroSuc())) && Integer.valueOf(cuentaVendedor.getNumero()).equals(Integer.valueOf(cuenta.getNumero())) && cuentaVendedor.getTipo().equals(cuenta.getTipo())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void goToListaCuentas(boolean z2) {
        gotoPage(0, z2);
        if (z2) {
            this.t.onCreatePage();
            return;
        }
        this.mRecyclerView.setVisibility(8);
        this.t.consultarAdhesionVendedor();
    }

    public void setActionBarListaCuentas() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        d();
    }

    private void d() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    GestionAdhesionDebinActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonListaCuentas() {
        Intent intent = new Intent();
        if (!this.q.getCuentasDEBINAdheridas().isEmpty()) {
            intent.putExtra(DebinConstants.INTENT_EXTRA_HAY_CUENTAS_ADHERIDAS, true);
        }
        setResult(0, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void setListaCuentasView() {
        setActionBarListaCuentas();
        this.mRecyclerView.setVisibility(0);
        this.y.clear();
        if (!(this.x == null || this.x.getCuentaVendedor() == null)) {
            this.y.addAll(this.x.getCuentaVendedor());
        }
        if (this.z != null && this.z.size() > 0) {
            this.y.addAll(this.z);
        }
        c();
        this.B = new GestionAdhesionCuentasDebinAdapter(this, this.q, this.y, getAnalyticsManager());
        this.B.setOnClickListener(this);
        this.mRecyclerView.setAdapter(this.B);
        this.w = null;
    }

    public void onItemClick(View view) {
        this.w = (CuentaVendedor) this.y.get(this.mRecyclerView.getChildPosition(view));
        this.B.setOnClickListener(null);
        final Context context = getContext();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                GestionAdhesionDebinActivity.this.B.setOnClickListener((OnItemClickListener) context);
            }
        }, 1000);
        if (this.w.getStatusAdhesion().intValue() == 0 || (this.w.getStatusAdhesion().intValue() == 1 && this.r.isSoftTokenAvailable().booleanValue())) {
            this.t.onCuentaSelected();
        } else {
            mostrarPopUpSinToken();
        }
    }

    public void mostrarPopUpSinToken() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getResources().getString(R.string.MSG_USER00514_DEBIN), getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), getResources().getString(R.string.USER200008_BTN));
        newInstance.setAutoClose(Boolean.valueOf(false));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                if (!newInstance.getAutoClose().booleanValue()) {
                    newInstance.setAutoClose(Boolean.valueOf(true));
                    newInstance.closeDialog();
                }
            }

            public void onNegativeButton() {
                Intent intent = new Intent(GestionAdhesionDebinActivity.this.getContext(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, GestionAdhesionDebinActivity.this.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG_TIT));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, GestionAdhesionDebinActivity.this.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG));
                GestionAdhesionDebinActivity.this.startActivity(intent);
            }
        });
        newInstance.show(getSupportFragmentManager(), "DialogNewVersion");
    }

    public void gotoConfirmarGestionCuenta() {
        gotoPage(1);
        this.u.onCreatePage();
    }

    public void setActionBarConfirmarGestion() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        e();
    }

    private void e() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    GestionAdhesionDebinActivity.this.onBackPressed();
                }
            });
        }
    }

    public void backButtonConfirmar() {
        gotoPage(0, false);
        setActionBarListaCuentas();
    }

    public void setConfirmarGestionCuentaView() {
        setActionBarConfirmarGestion();
        if (this.w.getStatusAdhesion().intValue() == 0) {
            this.p.trackScreen(getString(R.string.f217analytics_screen_name_Confirmar_desvinculacin_cuenta_F_32_21));
            this.lbl_confirmar_title.setText(getString(R.string.ID_4451_DEBIN_CONFIRMARDESVINCULACIONDECUENTA));
            this.lbl_confirmar_cuentaAdhesion.setText(getString(R.string.ID_4452_DEBIN_CUENTAADESVINCULAR));
        } else {
            this.p.trackScreen(getString(R.string.f215analytics_screen_name_Confirmar_adhesin_cuenta_F_32_26));
            this.lbl_confirmar_title.setText(getString(R.string.ID_4447_DEBIN_CONFIRMARADHESIONDECUENTA));
            this.lbl_confirmar_cuentaAdhesion.setText(getString(R.string.ID_4448_DEBIN_CUENTAAADHERIR));
        }
        this.lbl_data_tipoCuentaAdhesion.setText("");
        this.lbl_data_tipoCuentaAdhesion.setText(UtilAccount.getAccountTypeDescription(this.q, "", this.w.getTipo()));
        try {
            this.lbl_data_tipoCuentaAdhesion.setContentDescription(this.lbl_data_tipoCuentaAdhesion.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.lbl_data_confirmacion_numeroCuentaAdhesion.setText(UtilAccount.getAccountFormat(this.w.getSucursal(), this.w.getNumero()));
        try {
            this.lbl_data_confirmacion_numeroCuentaAdhesion.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.lbl_data_confirmacion_numeroCuentaAdhesion.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.btnConfirmarn.setOnClickListener(this);
    }

    public void gotoComprobanteGestionAdhesionDebinView(AbmAdhesionVendedorBodyResponseBean abmAdhesionVendedorBodyResponseBean, Integer num) {
        gotoPage(2);
        this.v.onCreatePage(abmAdhesionVendedorBodyResponseBean, num);
    }

    public void setActionBarComprobanteGestion() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        f();
    }

    private void f() {
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    GestionAdhesionDebinActivity.this.switchDrawer();
                }
            });
        }
        g();
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (GestionAdhesionDebinActivity.this.w.getStatusAdhesion().intValue() == 0) {
                        GestionAdhesionDebinActivity.this.p.trackEvent(GestionAdhesionDebinActivity.this.getString(R.string.analytics_trackevent_category_debin), GestionAdhesionDebinActivity.this.getString(R.string.analytics_trackevent_action_adherir_cuenta), GestionAdhesionDebinActivity.this.getString(R.string.analytics_trackevent_label_Descargar_compartir_comprobante));
                    } else {
                        GestionAdhesionDebinActivity.this.p.trackEvent(GestionAdhesionDebinActivity.this.getString(R.string.analytics_trackevent_category_debin), GestionAdhesionDebinActivity.this.getString(R.string.analytics_trackevent_action_desvincular_cuenta), GestionAdhesionDebinActivity.this.getString(R.string.analytics_trackevent_label_Descargar_compartir_comprobante));
                    }
                    GestionAdhesionDebinActivity.this.C.show(GestionAdhesionDebinActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void g() {
        this.D = h();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.C = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.C.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(GestionAdhesionDebinActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    GestionAdhesionDebinActivity.this.D.optionShareSelected();
                } else if (str.equalsIgnoreCase(GestionAdhesionDebinActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    GestionAdhesionDebinActivity.this.D.optionDownloadSelected();
                }
            }
        });
        this.C.setCancelable(true);
    }

    private OptionsToShare h() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return GestionAdhesionDebinActivity.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                GestionAdhesionDebinActivity.this.startActivity(Intent.createChooser(intent, GestionAdhesionDebinActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                return Html.fromHtml(GestionAdhesionDebinActivity.this.F).toString().concat(GestionAdhesionDebinActivity.this.A);
            }

            public String getSubjectReceiptToShare() {
                String obj = Html.fromHtml(GestionAdhesionDebinActivity.this.G).toString();
                StringBuilder sb = new StringBuilder();
                sb.append("-");
                sb.append(GestionAdhesionDebinActivity.this.A);
                return obj.concat(sb.toString());
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                GestionAdhesionDebinActivity.this.E = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                GestionAdhesionDebinActivity.this.E = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                GestionAdhesionDebinActivity.this.E = true;
                GestionAdhesionDebinActivity.this.onBackPressed();
            }
        };
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.D.onRequestPermissionsResult(i, strArr, iArr);
    }

    public boolean canExit(int i) {
        if (!this.E) {
            final int i2 = i;
            AnonymousClass10 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public View getViewToShare() {
                    return GestionAdhesionDebinActivity.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    GestionAdhesionDebinActivity.this.startActivity(Intent.createChooser(intent, GestionAdhesionDebinActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    return Html.fromHtml(GestionAdhesionDebinActivity.this.F).toString().concat(GestionAdhesionDebinActivity.this.A);
                }

                public String getSubjectReceiptToShare() {
                    String obj = Html.fromHtml(GestionAdhesionDebinActivity.this.G).toString();
                    StringBuilder sb = new StringBuilder();
                    sb.append("-");
                    sb.append(GestionAdhesionDebinActivity.this.A);
                    return obj.concat(sb.toString());
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    GestionAdhesionDebinActivity.this.E = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    GestionAdhesionDebinActivity.this.E = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    GestionAdhesionDebinActivity.this.E = true;
                    GestionAdhesionDebinActivity.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.E;
    }

    public void setComprobanteGestionAdhesionDebinView(AbmAdhesionVendedorBodyResponseBean abmAdhesionVendedorBodyResponseBean, Integer num) {
        setActionBarComprobanteGestion();
        this.w.setStatusAdhesion(num);
        if (this.w.getStatusAdhesion().intValue() == 0) {
            this.w.setStatusAdhesion(Integer.valueOf(1));
        } else {
            this.w.setStatusAdhesion(Integer.valueOf(0));
        }
        if (this.w.getStatusAdhesion().intValue() == 1) {
            this.p.trackScreen(getString(R.string.f213analytics_screen_name_Comprobante_desvinculacin_cuenta_F_32_22));
            this.lbl_comprobante_title.setText(getString(R.string.ID_4453_DEBIN_COMPROBANTEDEDESVINCULACIONDECUENTA));
            this.F = getString(R.string.ID_4453_DEBIN_COMPROBANTEDEDESVINCULACIONDECUENTAARCHIVO);
            this.G = getString(R.string.ID_4453_DEBIN_COMPROBANTEDEDESVINCULACIONDECUENTASUBJECT);
            this.lbl_comprobante_operacion_cuenta.setText(getString(R.string.ID_4454_DEBIN_CUENTADESVINCULADA));
        } else {
            this.p.trackScreen(getString(R.string.f211analytics_screen_name_Comprobante_adhesin_cuenta_F_32_27));
            this.lbl_comprobante_title.setText(getString(R.string.ID_4449_DEBIN_COMPROBANTEDEADHESIONDECUENTA));
            this.F = getString(R.string.ID_4449_DEBIN_COMPROBANTEDEADHESIONDECUENTAARCHIVO);
            this.G = getString(R.string.ID_4449_DEBIN_COMPROBANTEDEADHESIONDECUENTASUBJECT);
            this.lbl_comprobante_operacion_cuenta.setText(getString(R.string.ID_4450_DEBIN_CUENTAADHERIDA));
        }
        this.lbl_data_comprobante_tipoCuentaAdhesion.setText(UtilAccount.getAccountTypeDescription(this.q, "", this.w.getTipo()));
        try {
            this.lbl_data_comprobante_tipoCuentaAdhesion.setContentDescription(this.lbl_data_comprobante_tipoCuentaAdhesion.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.lbl_data_comprobante_numeroCuentaAdhesion.setText(UtilAccount.getAccountFormat(this.w.getSucursal(), this.w.getNumero()));
        try {
            this.lbl_data_comprobante_numeroCuentaAdhesion.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.lbl_data_comprobante_numeroCuentaAdhesion.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.lbl_data_comprobante_fechaOperacion.setText(abmAdhesionVendedorBodyResponseBean.getFechaOperacion());
        this.s = abmAdhesionVendedorBodyResponseBean.getFechaOperacion();
        this.lbl_data_comprobante_nroOperacion.setText(abmAdhesionVendedorBodyResponseBean.getNroComprobante());
        try {
            this.lbl_data_comprobante_nroOperacion.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.lbl_data_comprobante_nroOperacion.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.A = abmAdhesionVendedorBodyResponseBean.getNroComprobante();
        if (abmAdhesionVendedorBodyResponseBean.getLeyendaComp() != null) {
            this.lbl_comprobante_terminos_legales.setText(Html.fromHtml(abmAdhesionVendedorBodyResponseBean.getLeyendaComp()));
        }
        try {
            this.lbl_comprobante_terminos_legales.setContentDescription(CAccessibility.getInstance(this).applyFilterGeneral(this.lbl_comprobante_terminos_legales.getText().toString()));
            this.lbl_data_comprobante_fechaOperacion.setContentDescription(CAccessibility.getInstance(this).applyFilterDate(this.lbl_data_comprobante_fechaOperacion.getText().toString()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.btnVolverComprobante.setOnClickListener(this);
        if (this.w.getStatusAdhesion().intValue() == 1) {
            AnalyticsManager analyticsManager = this.p;
            String string = getString(R.string.analytics_transaction_hits_debin);
            StringBuilder sb = new StringBuilder();
            sb.append(this.A);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.s);
            analyticsManager.trackTransaction(string, sb.toString());
            return;
        }
        AnalyticsManager analyticsManager2 = this.p;
        String string2 = getString(R.string.analytics_transaction_hits_debin);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.A);
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(this.s);
        analyticsManager2.trackTransaction(string2, sb2.toString());
    }

    public void actualizarDatosCuentaSessionManager() {
        boolean z2;
        ConsultarAdhesionVendedorBodyResponseBean consultarAdhesionVendedorBodyResponseBean = this.q.getConsultarAdhesionVendedorBodyResponseBean();
        if (consultarAdhesionVendedorBodyResponseBean != null && consultarAdhesionVendedorBodyResponseBean.getListaCuentasVendedorBean() != null) {
            if (consultarAdhesionVendedorBodyResponseBean.getListaCuentasVendedorBean().getCuentaVendedor() == null) {
                consultarAdhesionVendedorBodyResponseBean.getListaCuentasVendedorBean().setCuentaVendedor(new ArrayList());
            }
            List cuentaVendedor = consultarAdhesionVendedorBodyResponseBean.getListaCuentasVendedorBean().getCuentaVendedor();
            if (cuentaVendedor.size() == 0) {
                cuentaVendedor.add(this.w);
            } else {
                Iterator it = cuentaVendedor.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    }
                    CuentaVendedor cuentaVendedor2 = (CuentaVendedor) it.next();
                    if (Integer.valueOf(cuentaVendedor2.getSucursal()).equals(Integer.valueOf(this.w.getSucursal())) && Integer.valueOf(cuentaVendedor2.getNumero()).equals(Integer.valueOf(this.w.getNumero())) && Integer.valueOf(cuentaVendedor2.getTipo()).equals(Integer.valueOf(this.w.getTipo()))) {
                        z2 = true;
                        cuentaVendedor2.setStatusAdhesion(this.w.getStatusAdhesion());
                        break;
                    }
                }
                if (!z2) {
                    cuentaVendedor.add(this.w);
                }
            }
            this.q.setConsultarAdhesionVendedorBodyResponseBean(consultarAdhesionVendedorBodyResponseBean);
        }
    }

    private void i() {
        this.w.setAlias(null);
        this.w.setTipoCuenta(null);
        this.w.setBanco(null);
        this.w.setCbu(null);
        this.w.setNumeroCuenta(null);
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F32_26_btn_confirmar) {
            i();
            this.u.callAbmAdhesionVendedor(this.w.getStatusAdhesion().intValue() == 0 ? "B" : "A", this.w);
        } else if (id2 == R.id.F32_27_btn_volver) {
            onBackPressed();
        }
    }

    public void gotoPage(int i) {
        gotoPage(i, true);
    }

    public void gotoPage(int i, boolean z2) {
        if (this.mControlPager != null) {
            detachView();
            switch (i) {
                case 0:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                    break;
                case 1:
                    if (z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                case 2:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
            hideKeyboard();
        }
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.t.isViewAttached()) {
                    this.t.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.u.isViewAttached()) {
                    this.u.attachView(this);
                    return;
                }
                return;
            case 2:
                if (!this.v.isViewAttached()) {
                    this.v.attachView(this);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void detachView() {
        if (this.t.isViewAttached()) {
            this.t.detachView();
        }
        if (this.u.isViewAttached()) {
            this.u.detachView();
        }
        if (this.v.isViewAttached()) {
            this.v.detachView();
        }
    }

    public void onBackPressed() {
        int id2 = this.mControlPager.getCurrentView().getId();
        if (id2 != R.id.layout_comprobante_gestion_adhesion_debin) {
            if (id2 == R.id.layout_confirmar_gestion_adhesion_debin) {
                backButtonConfirmar();
            } else if (id2 == R.id.layout_lista_cuentas_gestion_adhesion_debin) {
                backButtonListaCuentas();
            }
        } else if (!this.E) {
            this.D.showAlert();
        } else {
            this.E = false;
            goToListaCuentas(false);
        }
    }

    public void setStatusACuenta(Integer num) {
        this.w.setStatusAdhesion(num);
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }

    public void setCuentasAdheridas(ListaCuentasVendedorBean listaCuentasVendedorBean) {
        this.x = listaCuentasVendedorBean;
        this.z = a(CAccounts.getInstance(this.q).getListAccountsUserFromSession());
    }
}
