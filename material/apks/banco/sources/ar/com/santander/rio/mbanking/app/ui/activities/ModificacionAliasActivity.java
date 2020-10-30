package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.modificacionAlias.ModificacionAliasComprobantePresenter;
import ar.com.santander.rio.mbanking.app.module.modificacionAlias.ModificacionAliasComprobanteView;
import ar.com.santander.rio.mbanking.app.module.modificacionAlias.ModificacionAliasConfirmacionPresenter;
import ar.com.santander.rio.mbanking.app.module.modificacionAlias.ModificacionAliasConfirmacionView;
import ar.com.santander.rio.mbanking.app.module.modificacionAlias.ModificacionAliasPresenter;
import ar.com.santander.rio.mbanking.app.module.modificacionAlias.ModificacionAliasView;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.CuentasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.Utils.Documento;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ModificacionAliasActivity extends MvpPrivateMenuActivity implements OnClickListener, ModificacionAliasComprobanteView, ModificacionAliasConfirmacionView, ModificacionAliasView {
    public static String HTML_FORMAT = "<html><head><style type=\"text/css\"></style><style type=\"text/css\"></style></head><body>VALOR_SERVICIO</body></html>";
    private ModificacionAliasPresenter A;
    private ModificacionAliasConfirmacionPresenter B;
    private ModificacionAliasComprobantePresenter C;
    /* access modifiers changed from: private */
    public boolean D = false;
    private String E = "";
    private ArrayList<String> F = new ArrayList<>();
    private ArrayList<String> G = new ArrayList<>();
    /* access modifiers changed from: private */
    public Context H;
    @InjectView(2131361815)
    TextView aliasAnteriorComprobanteAlias;
    @InjectView(2131361810)
    TextView aliasOriginalCrearAlias;
    @InjectView(2131361808)
    Button botonContinuarConfirmarAlias;
    @InjectView(2131362810)
    Button botonContinuarCrearAlias;
    @InjectView(2131361809)
    Button botonVolverComprobanteAlias;
    @InjectView(2131365237)
    ImageView btnAyudaModificacionAlias;
    @InjectView(2131361812)
    TextView cbuComprobanteAlias;
    @InjectView(2131362851)
    TextView cbuConfirmarAlias;
    @InjectView(2131362862)
    TextView cbuCrearAlias;
    @InjectView(2131365643)
    ScrollView comprobanteOperacion;
    @InjectView(2131365236)
    TextView comprobanteTitulo;
    @InjectView(2131361813)
    TextView dniComprobanteAlias;
    @InjectView(2131362852)
    TextView dniConfirmarAlias;
    @InjectView(2131362863)
    TextView dniCrearAlias;
    @InjectView(2131364655)
    EditText editModificarAlias;
    @InjectView(2131361814)
    TextView fechaEmisionComprobanteAlias;
    public String inputNumeroComprobante = "";
    @InjectView(2131362860)
    TextView lblDNICrearAlias;
    @InjectView(2131362861)
    TextView lblDniComprobante;
    @InjectView(2131362850)
    TextView lblDniConfirmacion;
    @InjectView(2131365238)
    ViewFlipper mControlPager;
    @InjectView(2131361816)
    TextView modificacionAliasComprobanteAlias;
    @InjectView(2131361811)
    TextView modificacionAliasCrearAlias;
    @InjectView(2131361817)
    TextView nroComprobanteComprobanteAlias;
    @InjectView(2131361822)
    TextView nroCuentaComprobanteAlias;
    @InjectView(2131362853)
    TextView nroCuentaConfirmarAlias;
    ImageView p;
    @Inject
    AnalyticsManager q;
    String r = "";
    @Inject
    SessionManager s;
    @InjectView(2131362864)
    TextView seleccionarCuentaCrearAlias;
    Cuenta t = new Cuenta();
    @InjectView(2131361819)
    TextView tipoCuentaComprobanteAlias;
    @InjectView(2131362854)
    TextView tipoCuentaConfirmarAlias;
    @InjectView(2131362865)
    TextView tipoCuentaCrearAlias;
    @InjectView(2131361821)
    TextView titularComprobanteAlias;
    @InjectView(2131362855)
    TextView titularConfirmarAlias;
    @InjectView(2131362866)
    TextView titularCrearAlias;
    Cuenta u;
    List<Cuenta> v;
    String w;
    /* access modifiers changed from: private */
    public OptionsToShare x;
    /* access modifiers changed from: private */
    public IsbanDialogFragment y;
    /* access modifiers changed from: private */
    public IsbanDialogFragment z;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public int getMainLayout() {
        return R.layout.modificacion_alias_activity;
    }

    public void onClick(View view) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        configureActionBar();
        initialize();
        this.H = this;
    }

    public void initialize() {
        this.A = new ModificacionAliasPresenter(this.mBus, this.mDataManager);
        this.C = new ModificacionAliasComprobantePresenter(this.mBus, this.mDataManager);
        this.B = new ModificacionAliasConfirmacionPresenter(this.mBus, this.mDataManager, this);
        ButterKnife.inject((Activity) this);
        setModificacionAliasView();
        this.A.attachView(this);
        this.A.onCreatePage(this);
        b();
        cargarDatosIniciales();
    }

    public void detachView() {
        if (this.A.isViewAttached()) {
            this.A.detachView();
        }
        if (this.C.isViewAttached()) {
            this.C.detachView();
        }
        if (this.B.isViewAttached()) {
            this.B.detachView();
        }
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.A.isViewAttached()) {
                    this.A.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.B.isViewAttached()) {
                    this.B.attachView(this);
                    return;
                }
                return;
            case 2:
                if (!this.C.isViewAttached()) {
                    this.C.attachView(this);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.p.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ModificacionAliasActivity.this.onBackPressed();
            }
        });
    }

    public void showDialogReasigna(String str, final String str2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpReasigna", PagoTarjetasConstants.ISBAN_DIALOG_WARNING_TITLE, str, getString(R.string.ID1_ALERT_BTN_ACCEPT), getString(R.string.IDX_ALERT_BTN_CANCEL));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ModificacionAliasActivity.this.a("S", str2);
            }

            public void onNegativeButton() {
                ModificacionAliasActivity.this.f();
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpReasigna");
    }

    public boolean canExit(int i) {
        if (!this.D) {
            final int i2 = i;
            AnonymousClass3 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public View getViewToShare() {
                    return ModificacionAliasActivity.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    ModificacionAliasActivity.this.startActivityForResult(Intent.createChooser(intent, ModificacionAliasActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
                }

                public String getFileName() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Html.fromHtml(ModificacionAliasActivity.this.comprobanteTitulo.getText().toString()).toString());
                    sb.append(" - ");
                    return sb.toString().concat(ModificacionAliasActivity.this.inputNumeroComprobante);
                }

                public String getSubjectReceiptToShare() {
                    return Html.fromHtml(ModificacionAliasActivity.this.comprobanteTitulo.getText().toString()).toString();
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    ModificacionAliasActivity.this.D = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    ModificacionAliasActivity.this.D = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    ModificacionAliasActivity.this.D = true;
                    ModificacionAliasActivity.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.D;
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.x.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void cargarDatosIniciales() {
        obtenerCuenta();
        this.editModificarAlias.setText(Html.fromHtml(this.t.getAlias()).toString());
        TextView textView = this.titularCrearAlias;
        StringBuilder sb = new StringBuilder();
        sb.append(this.s.getLoginUnico().getDatosPersonales().getNombre());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.s.getLoginUnico().getDatosPersonales().getApellido());
        textView.setText(sb.toString());
        this.cbuCrearAlias.setText(this.t.getClaveBancariaUnificada());
        this.seleccionarCuentaCrearAlias.setText(UtilAccount.getAccountFormat(this.t.getNroSuc(), this.t.getNumero()));
        this.tipoCuentaCrearAlias.setText(UtilAccount.getAccountTypeDescription(this.s, "", this.t.getTipo()));
        this.dniCrearAlias.setText(Documento.format(this.s.getLoginUnico().getDatosPersonales().getTipoDocumento(), this.s.getLoginUnico().getDatosPersonales().getNroDocumento()));
        try {
            this.dniCrearAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.dniCrearAlias.getText().toString()));
            this.editModificarAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.editModificarAlias.getText().toString()));
            TextView textView2 = this.titularCrearAlias;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(new CAccessibility(getBaseContext()).applyFilterGeneral(this.s.getLoginUnico().getDatosPersonales().getNombre()));
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(new CAccessibility(getBaseContext()).applyFilterGeneral(this.s.getLoginUnico().getDatosPersonales().getApellido()));
            textView2.setContentDescription(sb2.toString());
            this.cbuCrearAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.t.getClaveBancariaUnificada()));
            this.seleccionarCuentaCrearAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(this.seleccionarCuentaCrearAlias.getText().toString()));
            this.tipoCuentaCrearAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.tipoCuentaCrearAlias.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void llenarAlias() {
        this.r = this.editModificarAlias.getText().toString();
        this.modificacionAliasCrearAlias.setText(this.r);
        try {
            this.modificacionAliasCrearAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.modificacionAliasCrearAlias.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtenerCuenta() {
        this.u = (Cuenta) getIntent().getParcelableExtra(CuentasConstants.cINTENT_EXTRA_ALIAS_CUENTA);
        this.t = (Cuenta) this.u.clone();
        if (this.t.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) || this.t.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR)) {
            this.t.setTipo(LoginConstants.TIPO_CUENTA_CU);
        }
    }

    private void b() {
        c();
        for (Cuenta cuenta : this.v) {
            if (CAccounts.getInstance(this.s).isAccountOperational(cuenta).booleanValue()) {
                String accountFormat = UtilAccount.getAccountFormat(cuenta.getNroSuc(), cuenta.getNumero());
                this.F.add(accountFormat);
                try {
                    this.G.add(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(accountFormat));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void c() {
        this.v = this.s.getLoginUnico().getProductos().getCuentas() != null ? d() : e();
    }

    private List<Cuenta> d() {
        return this.s.getLoginUnico().getProductos().getCuentas().getCuentas();
    }

    private List<Cuenta> e() {
        return this.s.getLoginUnico().getProductos().getCuentasBP().getCuentasBP();
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
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    hideKeyboard();
                    break;
                case 2:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
        }
    }

    public void goToModificacionAliasConfirmacion() {
        gotoPage(1);
        this.B.onCreatePage();
    }

    public void goToComprobanteABMAlias(String str, String str2, String str3) {
        gotoPage(2);
        this.C.onCreatePage(str, str2, str3);
    }

    /* access modifiers changed from: private */
    public void f() {
        gotoPage(0, false);
        configureActionBar();
    }

    public void onBackPressed() {
        switch (this.mControlPager.getCurrentView().getId()) {
            case R.id.layout_modificacion_alias /*2131364948*/:
                setResult(0);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.layout_modificacion_alias_comprobante /*2131364949*/:
                if (!this.D) {
                    this.x.showAlert();
                    return;
                }
                setResult(-1);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.layout_modificacion_alias_confirmacion /*2131364950*/:
                f();
                return;
            default:
                return;
        }
    }

    public void setModificacionAliasConfirmacionView() {
        this.q.trackScreen(getString(R.string.analytics_screen_modificacion_alias_confirmacion));
        g();
        this.aliasOriginalCrearAlias.setText(Html.fromHtml(this.u.getAlias()).toString());
        this.titularConfirmarAlias.setText(this.titularCrearAlias.getText().toString());
        this.dniConfirmarAlias.setText(this.dniCrearAlias.getText().toString());
        this.cbuConfirmarAlias.setText(this.cbuCrearAlias.getText().toString());
        this.nroCuentaConfirmarAlias.setText(this.seleccionarCuentaCrearAlias.getText().toString());
        this.tipoCuentaConfirmarAlias.setText(this.tipoCuentaCrearAlias.getText().toString());
        try {
            this.dniConfirmarAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.dniConfirmarAlias.getText().toString()));
            this.aliasOriginalCrearAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.aliasOriginalCrearAlias.getText().toString()));
            this.titularConfirmarAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.titularConfirmarAlias.getText().toString()));
            this.cbuConfirmarAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterCharacterToCharacter(this.cbuConfirmarAlias.getText().toString()));
            this.nroCuentaConfirmarAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterAccount(this.nroCuentaConfirmarAlias.getText().toString()));
            this.tipoCuentaConfirmarAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.tipoCuentaConfirmarAlias.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.botonContinuarConfirmarAlias.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ModificacionAliasActivity.this.a("N", (String) null);
            }
        });
    }

    private void g() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.p.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ModificacionAliasActivity.this.onBackPressed();
            }
        });
    }

    private void h() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        ((ImageView) this.mActionBar.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!ModificacionAliasActivity.this.D) {
                    ModificacionAliasActivity.this.x.showAlert();
                } else {
                    ModificacionAliasActivity.this.switchDrawer();
                }
            }
        });
        enableShareButton();
    }

    public void enableShareButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.menu);
        findViewById.setContentDescription(getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        this.x = j();
        i();
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ModificacionAliasActivity.this.y.show(ModificacionAliasActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void i() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.y = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.y.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(ModificacionAliasActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    ModificacionAliasActivity.this.x.optionShareSelected();
                } else if (str.equalsIgnoreCase(ModificacionAliasActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    ModificacionAliasActivity.this.x.optionDownloadSelected();
                }
            }
        });
        this.y.setCancelable(true);
    }

    private OptionsToShare j() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return ModificacionAliasActivity.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                ModificacionAliasActivity.this.startActivityForResult(Intent.createChooser(intent, ModificacionAliasActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
            }

            public String getFileName() {
                StringBuilder sb = new StringBuilder();
                sb.append(Html.fromHtml(ModificacionAliasActivity.this.comprobanteTitulo.getText().toString()).toString());
                sb.append(" - ");
                return sb.toString().concat(ModificacionAliasActivity.this.inputNumeroComprobante);
            }

            public String getSubjectReceiptToShare() {
                return Html.fromHtml(ModificacionAliasActivity.this.comprobanteTitulo.getText().toString()).toString();
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                ModificacionAliasActivity.this.D = true;
            }

            public void optionCancelSelected() {
                super.optionCancelSelected();
                ModificacionAliasActivity.this.D = true;
                ModificacionAliasActivity.this.onBackPressed();
            }

            public void optionShareSelected() {
                if (ContextCompat.checkSelfPermission(ModificacionAliasActivity.this.H, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    showRequestPermission(1);
                    return;
                }
                super.optionShareSelected();
                ModificacionAliasActivity.this.D = true;
            }
        };
    }

    public void showRequestPermissionExplation(final int i) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
                ModificacionAliasActivity.this.x.showAlert();
            }

            public void onSimpleActionButton() {
                if (VERSION.SDK_INT >= 23) {
                    ModificacionAliasActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i);
                }
            }
        });
        newInstance.show(getSupportFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    public void setModificacionAliasView() {
        this.q.trackScreen(getString(R.string.analytics_screen_modificacion_alias_home));
        lockMenu(true);
        configureActionBar();
        this.botonContinuarCrearAlias.setEnabled(false);
        this.botonContinuarCrearAlias.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris));
        this.btnAyudaModificacionAlias.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ModificacionAliasActivity.this.l();
            }
        });
        this.editModificarAlias.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() < 6) {
                    ModificacionAliasActivity.this.botonContinuarCrearAlias.setEnabled(false);
                    ModificacionAliasActivity.this.botonContinuarCrearAlias.setBackground(ModificacionAliasActivity.this.getResources().getDrawable(R.drawable.boton_redondeado_gris));
                    return;
                }
                ModificacionAliasActivity.this.k();
                ModificacionAliasActivity.this.botonContinuarCrearAlias.setEnabled(true);
                ModificacionAliasActivity.this.botonContinuarCrearAlias.setBackground(ModificacionAliasActivity.this.getResources().getDrawable(R.drawable.boton_redondeado_rojo));
            }
        });
    }

    public void setModificacionAliasComprobanteView(String str, String str2, String str3) {
        this.q.trackScreen(getString(R.string.analytics_screen_modificacion_alias_comprobante));
        h();
        this.inputNumeroComprobante = str;
        this.titularComprobanteAlias.setText(this.titularConfirmarAlias.getText().toString());
        this.dniComprobanteAlias.setText(CUIT.format(str3));
        this.cbuComprobanteAlias.setText(this.cbuConfirmarAlias.getText().toString());
        this.nroCuentaComprobanteAlias.setText(this.nroCuentaConfirmarAlias.getText().toString());
        this.tipoCuentaComprobanteAlias.setText(this.tipoCuentaConfirmarAlias.getText().toString());
        this.modificacionAliasComprobanteAlias.setText(this.r);
        this.aliasAnteriorComprobanteAlias.setText(Html.fromHtml(this.u.getAlias()));
        this.nroComprobanteComprobanteAlias.setText(str);
        this.fechaEmisionComprobanteAlias.setText(UtilDate.getDateFormat2(str2));
        try {
            this.titularComprobanteAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.titularComprobanteAlias.getText().toString()));
            this.cbuComprobanteAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterCharacterToCharacter(this.cbuComprobanteAlias.getText().toString()));
            this.nroCuentaComprobanteAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(this.nroCuentaComprobanteAlias.getText().toString()));
            this.tipoCuentaComprobanteAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.tipoCuentaComprobanteAlias.getText().toString()));
            this.modificacionAliasComprobanteAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.modificacionAliasComprobanteAlias.getText().toString()));
            this.aliasAnteriorComprobanteAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.aliasAnteriorComprobanteAlias.getText().toString()));
            this.nroComprobanteComprobanteAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.nroComprobanteComprobanteAlias.getText().toString()));
            this.fechaEmisionComprobanteAlias.setContentDescription(new CAccessibility(getBaseContext()).applyFilterDate(this.fechaEmisionComprobanteAlias.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.botonVolverComprobanteAlias.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ModificacionAliasActivity.this.onBackPressed();
            }
        });
    }

    /* access modifiers changed from: private */
    public void k() {
        this.botonContinuarCrearAlias.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ModificacionAliasActivity.this.llenarAlias();
                if (!UtilAccount.validarAlias(ModificacionAliasActivity.this.r)) {
                    ModificacionAliasActivity.this.n();
                } else if (!ModificacionAliasActivity.this.r.equalsIgnoreCase(Html.fromHtml(ModificacionAliasActivity.this.u.getAlias()).toString())) {
                    ModificacionAliasActivity.this.goToModificacionAliasConfirmacion();
                } else {
                    ModificacionAliasActivity.this.m();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void l() {
        String replace = HTML_FORMAT.replace("VALOR_SERVICIO", getString(R.string.F01_20_STR_ALIASAYUDA));
        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getString(R.string.F01_20_LBL_CARACTERISTICA_ALIAS));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, replace);
        startActivityForResult(intent, 1);
    }

    /* access modifiers changed from: private */
    public void m() {
        this.z = IsbanDialogFragment.newInstance("ErrorMismoAlias", getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.FXX_00_MSG_MISMO_ALIAS), getString(R.string.ID1_ALERT_BTN_ACCEPT), null);
        this.z.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }
        });
        this.z.show(getSupportFragmentManager(), PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE);
    }

    /* access modifiers changed from: private */
    public void n() {
        this.z = IsbanDialogFragment.newInstance(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, PagoTarjetasConstants.ISBAN_DIALOG_WARNING_TITLE, getString(R.string.F01_20_STR_ALIASERROR), PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, "Más info");
        this.z.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ModificacionAliasActivity.this.z.dismiss();
            }

            public void onNegativeButton() {
                ModificacionAliasActivity.this.w = ModificacionAliasActivity.this.getString(R.string.F01_20_STR_ALIASAYUDA);
                Intent intent = new Intent(ModificacionAliasActivity.this.getApplicationContext(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, "Características del Alias");
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, ModificacionAliasActivity.this.w);
                ModificacionAliasActivity.this.startActivityForResult(intent, 1);
            }
        });
        this.z.show(getSupportFragmentManager(), PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE);
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        this.B.onConfirmar("M", this.r, Html.fromHtml(this.u.getAlias()).toString(), str, str2, new CuentaShortBean(this.t.getTipo(), UtilAccount.formatSucursalCuenta(this.t.getNroSuc()), UtilAccount.formatNumeroCuenta(this.t.getNumero())));
        if (str.equalsIgnoreCase("S")) {
            this.comprobanteTitulo.setText(getString(R.string.FXX_02_LBL_TITULO_COMPROBANTE_REASIGNACION_MODIFICAR_ALIAS));
        }
    }
}
