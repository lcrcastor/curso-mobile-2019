package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import ar.com.santander.rio.mbanking.app.module.nuevoalias.NuevoAliasComprobantePresenter;
import ar.com.santander.rio.mbanking.app.module.nuevoalias.NuevoAliasComprobanteView;
import ar.com.santander.rio.mbanking.app.module.nuevoalias.NuevoAliasConfirmacionPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevoalias.NuevoAliasConfirmacionView;
import ar.com.santander.rio.mbanking.app.module.nuevoalias.NuevoAliasPresenter;
import ar.com.santander.rio.mbanking.app.module.nuevoalias.NuevoAliasView;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
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

public class NuevoAliasActivity extends MvpPrivateMenuActivity implements OnClickListener, NuevoAliasComprobanteView, NuevoAliasConfirmacionView, NuevoAliasView {
    private NuevoAliasComprobantePresenter A;
    /* access modifiers changed from: private */
    public boolean B = false;
    /* access modifiers changed from: private */
    public String C = "";
    @InjectView(2131361808)
    Button botonContinuarConfirmarAlias;
    @InjectView(2131362810)
    Button botonContinuarCrearAlias;
    @InjectView(2131361809)
    Button botonVolverComprobanteAlias;
    @InjectView(2131364090)
    ImageView btnAyudaNuevoAlias;
    @InjectView(2131361812)
    TextView cbuComprobanteAlias;
    @InjectView(2131362851)
    TextView cbuConfirmarAlias;
    @InjectView(2131362862)
    TextView cbuCrearAlias;
    @InjectView(2131365641)
    ScrollView comprobanteOperacion;
    @InjectView(2131364089)
    TextView comprobanteTitulo;
    @InjectView(2131361813)
    TextView dniComprobanteAlias;
    @InjectView(2131362852)
    TextView dniConfirmarAlias;
    @InjectView(2131362863)
    TextView dniCrearAlias;
    @InjectView(2131361805)
    EditText editCrearAlias;
    @InjectView(2131361814)
    TextView fechaEmisionComprobanteAlias;
    public String inputNumeroComprobante = "";
    @InjectView(2131362860)
    TextView lblDNICrearAlias;
    @InjectView(2131362861)
    TextView lblDniComprobante;
    @InjectView(2131362850)
    TextView lblDniConfirmacion;
    @InjectView(2131365266)
    ViewFlipper mControlPager;
    @InjectView(2131361817)
    TextView nroComprobanteComprobanteAlias;
    @InjectView(2131361822)
    TextView nroCuentaComprobanteAlias;
    @InjectView(2131362853)
    TextView nroCuentaConfirmarAlias;
    @InjectView(2131361818)
    TextView nuevoAliasComprobanteAlias;
    @InjectView(2131361811)
    TextView nuevoAliasCrearAlias;
    ImageView p;
    String q = "";
    @Inject
    SessionManager r;
    Cuenta s = new Cuenta();
    @InjectView(2131362864)
    TextView seleccionarCuentaCrearAlias;
    List<Cuenta> t;
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
    String u;
    /* access modifiers changed from: private */
    public OptionsToShare v;
    /* access modifiers changed from: private */
    public IsbanDialogFragment w;
    /* access modifiers changed from: private */
    public IsbanDialogFragment x;
    private NuevoAliasPresenter y;
    private NuevoAliasConfirmacionPresenter z;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public int getMainLayout() {
        return R.layout.nuevo_alias_activity;
    }

    public void onClick(View view) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        configureActionBar();
        initialize();
    }

    public void initialize() {
        this.y = new NuevoAliasPresenter(this.mBus, this.mDataManager);
        this.A = new NuevoAliasComprobantePresenter(this.mBus, this.mDataManager);
        this.z = new NuevoAliasConfirmacionPresenter(this.mBus, this.mDataManager, this);
        ButterKnife.inject((Activity) this);
        setNuevoAliasView();
        this.y.attachView(this);
        this.y.onCreatePage(this);
        cargarDatosIniciales();
        if (this.t.size() > 1) {
            this.seleccionarCuentaCrearAlias.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NuevoAliasActivity.this.e();
                }
            });
        }
    }

    public void detachView() {
        if (this.y.isViewAttached()) {
            this.y.detachView();
        }
        if (this.A.isViewAttached()) {
            this.A.detachView();
        }
        if (this.z.isViewAttached()) {
            this.z.detachView();
        }
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.y.isViewAttached()) {
                    this.y.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.z.isViewAttached()) {
                    this.z.attachView(this);
                    return;
                }
                return;
            case 2:
                if (!this.A.isViewAttached()) {
                    this.A.attachView(this);
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
                NuevoAliasActivity.this.onBackPressed();
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
                NuevoAliasActivity.this.a("S", str2);
            }

            public void onNegativeButton() {
                NuevoAliasActivity.this.f();
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpReasigna");
    }

    public boolean canExit(int i) {
        if (!this.B) {
            final int i2 = i;
            AnonymousClass4 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public View getViewToShare() {
                    return NuevoAliasActivity.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    NuevoAliasActivity.this.startActivityForResult(Intent.createChooser(intent, NuevoAliasActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
                }

                public String getFileName() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Html.fromHtml(NuevoAliasActivity.this.comprobanteTitulo.getText().toString()).toString());
                    sb.append(" - ");
                    return sb.toString().concat(NuevoAliasActivity.this.inputNumeroComprobante);
                }

                public String getSubjectReceiptToShare() {
                    return Html.fromHtml(NuevoAliasActivity.this.comprobanteTitulo.getText().toString()).toString();
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    NuevoAliasActivity.this.B = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    NuevoAliasActivity.this.B = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    NuevoAliasActivity.this.B = true;
                    NuevoAliasActivity.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.B;
    }

    public void cargarDatosIniciales() {
        obtenerCuenta();
        TextView textView = this.titularCrearAlias;
        StringBuilder sb = new StringBuilder();
        sb.append(this.r.getLoginUnico().getDatosPersonales().getNombre());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.r.getLoginUnico().getDatosPersonales().getApellido());
        textView.setText(sb.toString());
        b();
        this.cbuCrearAlias.setText(this.s.getClaveBancariaUnificada());
        this.seleccionarCuentaCrearAlias.setText(UtilAccount.getAccountFormat(this.s.getNroSuc(), this.s.getNumero()));
        try {
            this.seleccionarCuentaCrearAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.seleccionarCuentaCrearAlias.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tipoCuentaCrearAlias.setText(UtilAccount.getAccountTypeDescription(this.r, "", this.s.getTipo()));
        this.dniCrearAlias.setText(Documento.format(this.r.getLoginUnico().getDatosPersonales().getTipoDocumento(), this.r.getLoginUnico().getDatosPersonales().getNroDocumento()));
    }

    private void b() {
        this.t = this.r.getLoginUnico().getProductos().getCuentas() != null ? c() : d();
    }

    private List<Cuenta> c() {
        return this.r.getLoginUnico().getProductos().getCuentas().getCuentas();
    }

    private List<Cuenta> d() {
        return this.r.getLoginUnico().getProductos().getCuentasBP().getCuentasBP();
    }

    public void llenarAlias() {
        this.q = this.editCrearAlias.getText().toString();
        this.nuevoAliasCrearAlias.setText(this.q);
    }

    public void obtenerCuenta() {
        this.s = (Cuenta) ((Cuenta) getIntent().getParcelableExtra("NombreCuenta")).clone();
        if (this.s.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) || this.s.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR)) {
            this.s.setTipo(LoginConstants.TIPO_CUENTA_CU);
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (this.C.isEmpty()) {
            this.C = UtilAccount.getAccountFormat(this.s.getNroSuc(), this.s.getNumero());
        }
        for (Cuenta cuenta : this.t) {
            if (CAccounts.getInstance(this.r).isAccountOperational(cuenta).booleanValue()) {
                String accountFormat = UtilAccount.getAccountFormat(cuenta.getNroSuc(), cuenta.getNumero());
                arrayList.add(accountFormat);
                try {
                    arrayList2.add(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(accountFormat));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.w = IsbanDialogFragment.newInstance("SeleccionCuenta", "Seleccione Cuenta", null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, this.C, arrayList2);
        this.w.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                NuevoAliasActivity.this.seleccionarCuentaCrearAlias.setText(str);
                for (Cuenta cuenta : NuevoAliasActivity.this.t) {
                    if (UtilAccount.getAccountFormat(cuenta.getNroSuc(), cuenta.getNumero()).equals(str)) {
                        NuevoAliasActivity.this.s = (Cuenta) cuenta.clone();
                        if (NuevoAliasActivity.this.s.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) || NuevoAliasActivity.this.s.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR)) {
                            NuevoAliasActivity.this.s.setTipo(LoginConstants.TIPO_CUENTA_CU);
                        }
                        NuevoAliasActivity.this.cbuCrearAlias.setText(NuevoAliasActivity.this.s.getClaveBancariaUnificada());
                        NuevoAliasActivity.this.tipoCuentaCrearAlias.setText(UtilAccount.getAccountTypeDescription(NuevoAliasActivity.this.r, "", NuevoAliasActivity.this.s.getTipo()));
                        NuevoAliasActivity.this.C = UtilAccount.getAccountFormat(NuevoAliasActivity.this.s.getNroSuc(), NuevoAliasActivity.this.s.getNumero());
                    }
                }
            }

            public void onSimpleActionButton() {
                NuevoAliasActivity.this.w.dismiss();
            }
        });
        this.w.show(getSupportFragmentManager(), "SeleccionCuenta");
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

    public void goToNuevoAliasConfirmacion() {
        gotoPage(1);
        this.z.onCreatePage();
    }

    public void goToComprobanteABMAlias(String str, String str2, String str3) {
        gotoPage(2);
        this.A.onCreatePage(str, str2, str3);
    }

    /* access modifiers changed from: private */
    public void f() {
        gotoPage(0, false);
        configureActionBar();
    }

    public void onBackPressed() {
        switch (this.mControlPager.getCurrentView().getId()) {
            case R.id.layout_nuevo_alias /*2131364953*/:
                setResult(0);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.layout_nuevo_alias_comprobante /*2131364954*/:
                if (!this.B) {
                    this.v.showAlert();
                    return;
                }
                setResult(-1);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.layout_nuevo_alias_confirmacion /*2131364955*/:
                f();
                return;
            default:
                return;
        }
    }

    public void setNuevoAliasConfirmacionView() {
        g();
        this.titularConfirmarAlias.setText(this.titularCrearAlias.getText().toString());
        this.dniConfirmarAlias.setText(this.dniCrearAlias.getText().toString());
        this.cbuConfirmarAlias.setText(this.cbuCrearAlias.getText().toString());
        this.nroCuentaConfirmarAlias.setText(this.seleccionarCuentaCrearAlias.getText().toString());
        this.tipoCuentaConfirmarAlias.setText(this.tipoCuentaCrearAlias.getText().toString());
        try {
            this.cbuConfirmarAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.cbuConfirmarAlias.getText().toString()));
            this.lblDniConfirmacion.setContentDescription(CAccessibility.getInstance(this).applyFilterGeneral(this.lblDniConfirmacion.getText().toString().replaceAll("/", UtilsCuentas.SEPARAOR2)));
            this.nroCuentaConfirmarAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.nroCuentaConfirmarAlias.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.botonContinuarConfirmarAlias.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NuevoAliasActivity.this.a("N", (String) null);
            }
        });
    }

    private void g() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.p.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NuevoAliasActivity.this.onBackPressed();
            }
        });
    }

    private void h() {
        setActionBarType(ActionBarType.BACK_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.p.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NuevoAliasActivity.this.onBackPressed();
            }
        });
        enableShareButton();
    }

    public void enableShareButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.menu);
        findViewById.setContentDescription(getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        this.v = j();
        i();
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NuevoAliasActivity.this.w.show(NuevoAliasActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void i() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.w = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.w.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(NuevoAliasActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    NuevoAliasActivity.this.v.optionShareSelected();
                } else if (str.equalsIgnoreCase(NuevoAliasActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    NuevoAliasActivity.this.v.optionDownloadSelected();
                }
            }
        });
        this.w.setCancelable(true);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.v.onRequestPermissionsResult(i, strArr, iArr);
    }

    private OptionsToShare j() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return NuevoAliasActivity.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                NuevoAliasActivity.this.startActivityForResult(Intent.createChooser(intent, NuevoAliasActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
            }

            public String getFileName() {
                StringBuilder sb = new StringBuilder();
                sb.append(Html.fromHtml(NuevoAliasActivity.this.comprobanteTitulo.getText().toString()).toString());
                sb.append(" - ");
                return sb.toString().concat(NuevoAliasActivity.this.inputNumeroComprobante);
            }

            public String getSubjectReceiptToShare() {
                return Html.fromHtml(NuevoAliasActivity.this.comprobanteTitulo.getText().toString()).toString();
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                NuevoAliasActivity.this.B = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                NuevoAliasActivity.this.B = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                NuevoAliasActivity.this.B = true;
                NuevoAliasActivity.this.onBackPressed();
            }
        };
    }

    public void setNuevoAliasView() {
        lockMenu(true);
        configureActionBar();
        this.botonContinuarCrearAlias.setEnabled(false);
        this.botonContinuarCrearAlias.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris));
        this.btnAyudaNuevoAlias.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NuevoAliasActivity.this.l();
            }
        });
        this.editCrearAlias.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() < 6) {
                    NuevoAliasActivity.this.botonContinuarCrearAlias.setEnabled(false);
                    NuevoAliasActivity.this.botonContinuarCrearAlias.setBackground(NuevoAliasActivity.this.getResources().getDrawable(R.drawable.boton_redondeado_gris));
                    return;
                }
                NuevoAliasActivity.this.k();
                NuevoAliasActivity.this.botonContinuarCrearAlias.setEnabled(true);
                NuevoAliasActivity.this.botonContinuarCrearAlias.setBackground(NuevoAliasActivity.this.getResources().getDrawable(R.drawable.boton_redondeado_rojo));
            }
        });
        try {
            this.lblDNICrearAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterGeneral(this.lblDNICrearAlias.getText().toString().replaceAll("/", UtilsCuentas.SEPARAOR2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNuevoAliasComprobanteView(String str, String str2, String str3) {
        lockMenu(false);
        h();
        this.inputNumeroComprobante = str;
        this.titularComprobanteAlias.setText(this.titularConfirmarAlias.getText().toString());
        this.dniComprobanteAlias.setText(CUIT.format(str3));
        this.cbuComprobanteAlias.setText(this.cbuConfirmarAlias.getText().toString());
        this.nroCuentaComprobanteAlias.setText(this.nroCuentaConfirmarAlias.getText().toString());
        this.tipoCuentaComprobanteAlias.setText(this.tipoCuentaConfirmarAlias.getText().toString());
        this.nuevoAliasComprobanteAlias.setText(this.q);
        this.nroComprobanteComprobanteAlias.setText(str);
        this.fechaEmisionComprobanteAlias.setText(UtilDate.getDateFormat2(str2));
        try {
            this.lblDniComprobante.setContentDescription(CAccessibility.getInstance(this).applyFilterGeneral(this.lblDniComprobante.getText().toString().replaceAll("/", UtilsCuentas.SEPARAOR2)));
            this.nroCuentaComprobanteAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.nroCuentaComprobanteAlias.getText().toString()));
            this.nroComprobanteComprobanteAlias.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.nroComprobanteComprobanteAlias.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.botonVolverComprobanteAlias.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NuevoAliasActivity.this.onBackPressed();
            }
        });
    }

    /* access modifiers changed from: private */
    public void k() {
        this.botonContinuarCrearAlias.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NuevoAliasActivity.this.llenarAlias();
                if (UtilAccount.validarAlias(NuevoAliasActivity.this.q)) {
                    NuevoAliasActivity.this.goToNuevoAliasConfirmacion();
                } else {
                    NuevoAliasActivity.this.m();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void l() {
        this.u = getString(R.string.F01_20_STR_ALIASAYUDA);
        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getString(R.string.F01_20_LBL_CARACTERISTICA_ALIAS));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, this.u);
        startActivityForResult(intent, 1);
    }

    /* access modifiers changed from: private */
    public void m() {
        this.x = IsbanDialogFragment.newInstance(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, PagoTarjetasConstants.ISBAN_DIALOG_WARNING_TITLE, getString(R.string.F01_20_STR_ALIASERROR), PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, "Más info");
        this.x.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                NuevoAliasActivity.this.x.dismiss();
            }

            public void onNegativeButton() {
                NuevoAliasActivity.this.u = NuevoAliasActivity.this.getString(R.string.F01_20_STR_ALIASAYUDA);
                Intent intent = new Intent(NuevoAliasActivity.this.getApplicationContext(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, "Características del Alias");
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, NuevoAliasActivity.this.u);
                NuevoAliasActivity.this.startActivityForResult(intent, 1);
            }
        });
        this.x.show(getSupportFragmentManager(), PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE);
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        this.z.onConfirmar("A", this.q, str, str2, new CuentaShortBean(this.s.getTipo(), UtilAccount.formatSucursalCuenta(this.s.getNroSuc()), UtilAccount.formatNumeroCuenta(this.s.getNumero())));
        if (str.equalsIgnoreCase("S")) {
            this.comprobanteTitulo.setText(getString(R.string.IDXX_LBL_TITULO_COMPROBANTE_REASIGNACION_NUEVO_ALIAS));
        }
    }
}
