package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.TableViewRowCreator;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.ComprobanteABMDestinatarioTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.ComprobanteABMDestinatarioTransferenciaView;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.ConfirmarABMDestinatarioTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.ConfirmarABMDestinatarioTransferenciaView;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.EditarDestinatarioTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.EditarDestinatarioTransferenciaView;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.CuentaBanco;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CtaMigradaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import javax.inject.Inject;

public class EditarEliminarDestinatarioTransferenciaActivity extends MvpPrivateMenuActivity implements OnClickListener, ComprobanteABMDestinatarioTransferenciaView, ConfirmarABMDestinatarioTransferenciaView, EditarDestinatarioTransferenciaView {
    public static final int REQUEST_CODE_ABM_TRANSF_COMPROBANTE = 99;
    private ComprobanteABMDestinatarioTransferenciaPresenter A;
    /* access modifiers changed from: private */
    public ConfirmarABMDestinatarioTransferenciaPresenter B;
    /* access modifiers changed from: private */
    public AgendaDestinatarios C;
    /* access modifiers changed from: private */
    public String D;
    /* access modifiers changed from: private */
    public DatosCuentasDestOBBean E = null;
    /* access modifiers changed from: private */
    public DatosCuentasDestBSRBean F = null;
    private DatosCuentasBean G;
    /* access modifiers changed from: private */
    public Boolean H = Boolean.valueOf(false);
    /* access modifiers changed from: private */
    public String I = null;
    @InjectView(2131362842)
    Button btnAgendaComprobante;
    @InjectView(2131362810)
    Button btnContinuarEdit;
    @InjectView(2131362836)
    Button btnGuardarConfirmar;
    @InjectView(2131362843)
    Button btnTransferirComprobante;
    @InjectView(2131362832)
    LinearLayout cbuRowEdit;
    @InjectView(2131362848)
    ScrollView comprobanteOperacion;
    @InjectView(2131362811)
    EditText inpEmail;
    @InjectView(2131362812)
    EditText inpRecordatorio;
    public String inputNumeroComprobante = "";
    @InjectView(2131362818)
    TextView lblAlias;
    @InjectView(2131362819)
    TextView lblBancoEdit;
    @InjectView(2131362820)
    TextView lblCbuEdit;
    @InjectView(2131362817)
    TextView lblCuentasEdit;
    @InjectView(2131362822)
    TextView lblCuitCuentasEdit;
    @InjectView(2131362827)
    TextView lblNumeroCuentaDestinoEdit;
    @InjectView(2131362823)
    TextView lblNumeroCuentaEdit;
    @InjectView(2131362824)
    TextView lblTipoCuentaEdit;
    @InjectView(2131362844)
    TextView lblTitleComprobante;
    @InjectView(2131362837)
    TextView lblTitleConfirmar;
    @InjectView(2131362830)
    TextView lblTitleEdit;
    @InjectView(2131362825)
    TextView lblTitularEdit;
    @InjectView(2131364658)
    ViewFlipper mControlPager;
    @InjectView(2131362834)
    LinearLayout numeroCuentaRowEdit;
    @Inject
    AnalyticsManager p;
    @Inject
    SoftTokenManager q;
    ImageView r;
    @InjectView(2131362833)
    LinearLayout rowCUITVerificar;
    ImageView s;
    @Inject
    SessionManager t;
    @InjectView(2131362847)
    TableLayout tableLayoutComprobante;
    @InjectView(2131362841)
    TableLayout tableLayoutConfirmar;
    @InjectView(2131362835)
    LinearLayout tipoCuentaRowEdit;
    @InjectView(2131362813)
    TextView titleAlias;
    @InjectView(2131362821)
    TextView txtCUITVerificar;
    /* access modifiers changed from: private */
    public OptionsToShare u;
    /* access modifiers changed from: private */
    public boolean v = false;
    /* access modifiers changed from: private */
    public boolean w = false;
    private boolean x = false;
    /* access modifiers changed from: private */
    public IsbanDialogFragment y;
    private EditarDestinatarioTransferenciaPresenter z;

    public void clearScreenData() {
    }

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public Activity getActivity() {
        return this;
    }

    public int getMainLayout() {
        return R.layout.editar_destinatario_activity;
    }

    public void setComprobanteABMDestinatarioView(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean, DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
    }

    public void setEditarDestinatarioView(VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean, CtaMigradaBean ctaMigradaBean) {
    }

    public static Boolean isEmailValid(String str) {
        return Boolean.valueOf(Patterns.EMAIL_ADDRESS.matcher(str).matches() || TextUtils.isEmpty(str));
    }

    public static Boolean isRecordatorioValid(String str) {
        Boolean valueOf = Boolean.valueOf(true);
        if (TextUtils.isEmpty(str)) {
            return Boolean.valueOf(false);
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ".indexOf(Character.valueOf(charArray[i]).charValue()) == -1) {
                valueOf = Boolean.valueOf(false);
                break;
            } else {
                i++;
            }
        }
        return valueOf;
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.z = new EditarDestinatarioTransferenciaPresenter(this.mBus, this.mDataManager);
        this.z.attachView(this);
        ConfirmarABMDestinatarioTransferenciaPresenter confirmarABMDestinatarioTransferenciaPresenter = new ConfirmarABMDestinatarioTransferenciaPresenter(this.mBus, this.mDataManager, this.q, this, this.p);
        this.B = confirmarABMDestinatarioTransferenciaPresenter;
        this.B.attachView(this);
        this.A = new ComprobanteABMDestinatarioTransferenciaPresenter(this.mBus, this.mDataManager, this.t, this);
        this.A.attachView(this);
        this.C = (AgendaDestinatarios) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO);
        this.D = getIntent().getStringExtra("ORIGEN");
        if (getIntent().hasExtra(TransferenciasConstants.cINTENT_EXTRA_CUENTA_AGENDA)) {
            this.I = getIntent().getStringExtra(TransferenciasConstants.cINTENT_EXTRA_CUENTA_AGENDA);
        }
        if (this.D.equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_EDITAR)) {
            if (this.C.getBanco().equalsIgnoreCase(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
                this.F = (DatosCuentasDestBSRBean) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_BSR_BEAN);
            } else {
                this.E = (DatosCuentasDestOBBean) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_OB_BEAN);
            }
            this.G = (DatosCuentasBean) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_CUENTA_PARA);
            this.H = Boolean.valueOf(getIntent().getBooleanExtra(TransferenciasConstants.cINTENT_EXTRA_EJECUTAR_REASIGNACION, false));
            goToEditarDestinatario();
            return;
        }
        if (this.C.getBanco().equalsIgnoreCase(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
            this.F = (DatosCuentasDestBSRBean) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_BSR_BEAN);
        } else {
            this.E = (DatosCuentasDestOBBean) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_OB_BEAN);
        }
        goToConfirmarABMDestinatario(new DatosCuentasDestOBBean(), new DatosCuentasDestBSRBean());
    }

    public void setActionBarEditar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        b();
    }

    public void setActionBarConfirmarEliminarDestinatario() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        b();
    }

    public void setActionBarConfirmar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        c();
    }

    public void setActionBarComprobante() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        d();
        f();
    }

    private void b() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    EditarEliminarDestinatarioTransferenciaActivity.this.onBackPressed();
                }
            });
        }
    }

    private void c() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    EditarEliminarDestinatarioTransferenciaActivity.this.goToEditarDestinatario();
                }
            });
        }
    }

    private void d() {
        this.u = g();
        e();
        this.s = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (this.s != null) {
            this.s.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    EditarEliminarDestinatarioTransferenciaActivity.this.y.show(EditarEliminarDestinatarioTransferenciaActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void e() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.y = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.ID2140_ENVEFECT_LBL_COMPROBANTE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.y.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(EditarEliminarDestinatarioTransferenciaActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    EditarEliminarDestinatarioTransferenciaActivity.this.u.optionShareSelected();
                } else if (str.equalsIgnoreCase(EditarEliminarDestinatarioTransferenciaActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    EditarEliminarDestinatarioTransferenciaActivity.this.u.optionDownloadSelected();
                }
            }
        });
        this.y.setCancelable(true);
    }

    private void f() {
        this.r = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        if (this.r != null) {
            this.r.setOnClickListener(this);
        }
    }

    private OptionsToShare g() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public View getViewToShare() {
                return EditarEliminarDestinatarioTransferenciaActivity.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                EditarEliminarDestinatarioTransferenciaActivity.this.startActivityForResult(Intent.createChooser(intent, EditarEliminarDestinatarioTransferenciaActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
            }

            public String getFileName() {
                return "ABM Destinatario Transferencia - ".concat(EditarEliminarDestinatarioTransferenciaActivity.this.inputNumeroComprobante);
            }

            public String getSubjectReceiptToShare() {
                if (EditarEliminarDestinatarioTransferenciaActivity.this.D.equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR)) {
                    return EditarEliminarDestinatarioTransferenciaActivity.this.getBaseContext().getString(R.string.ID_XXXX_TRANSFERENCE_SUBJECT_SHARE_ELIMINACION);
                }
                return EditarEliminarDestinatarioTransferenciaActivity.this.getBaseContext().getString(R.string.ID_XXXX_TRANSFERENCE_SUBJECT_SHARE_EDICION);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                EditarEliminarDestinatarioTransferenciaActivity.this.v = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                EditarEliminarDestinatarioTransferenciaActivity.this.v = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                EditarEliminarDestinatarioTransferenciaActivity.this.v = true;
            }
        };
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.u.onRequestPermissionsResult(i, strArr, iArr);
    }

    public boolean canExit(int i) {
        if (!this.v) {
            final int i2 = i;
            AnonymousClass6 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public View getViewToShare() {
                    return EditarEliminarDestinatarioTransferenciaActivity.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    EditarEliminarDestinatarioTransferenciaActivity.this.startActivityForResult(Intent.createChooser(intent, EditarEliminarDestinatarioTransferenciaActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
                }

                public String getFileName() {
                    return "ABM Destinatario Transferencia - ".concat(EditarEliminarDestinatarioTransferenciaActivity.this.inputNumeroComprobante);
                }

                public String getSubjectReceiptToShare() {
                    if (EditarEliminarDestinatarioTransferenciaActivity.this.D.equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR)) {
                        return EditarEliminarDestinatarioTransferenciaActivity.this.getBaseContext().getString(R.string.ID_XXXX_TRANSFERENCE_SUBJECT_SHARE_ELIMINACION);
                    }
                    return EditarEliminarDestinatarioTransferenciaActivity.this.getBaseContext().getString(R.string.ID_XXXX_TRANSFERENCE_SUBJECT_SHARE_EDICION);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    EditarEliminarDestinatarioTransferenciaActivity.this.v = true;
                    if (EditarEliminarDestinatarioTransferenciaActivity.this.w) {
                        EditarEliminarDestinatarioTransferenciaActivity.this.btnTransferirComprobante.callOnClick();
                    } else {
                        EditarEliminarDestinatarioTransferenciaActivity.this.btnAgendaComprobante.callOnClick();
                    }
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    EditarEliminarDestinatarioTransferenciaActivity.this.v = true;
                    if (EditarEliminarDestinatarioTransferenciaActivity.this.w) {
                        EditarEliminarDestinatarioTransferenciaActivity.this.btnTransferirComprobante.callOnClick();
                    } else {
                        EditarEliminarDestinatarioTransferenciaActivity.this.btnAgendaComprobante.callOnClick();
                    }
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    EditarEliminarDestinatarioTransferenciaActivity.this.v = true;
                    EditarEliminarDestinatarioTransferenciaActivity.this.onClickItem(i2);
                    if (EditarEliminarDestinatarioTransferenciaActivity.this.w) {
                        EditarEliminarDestinatarioTransferenciaActivity.this.btnTransferirComprobante.callOnClick();
                    } else {
                        EditarEliminarDestinatarioTransferenciaActivity.this.btnAgendaComprobante.callOnClick();
                    }
                }
            };
            r1.showAlert();
        }
        return this.v;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.F23_20_BTN_CONTINUAR /*2131362810*/:
                if (this.H.booleanValue() || !this.C.getEmail().equals(this.inpEmail.getText().toString()) || !this.C.getDescripcion().equals(this.inpRecordatorio.getText().toString())) {
                    if (TextUtils.isEmpty(this.inpRecordatorio.getText().toString())) {
                        this.inpRecordatorio.setText(formatRecordatorio(this.C.getTitulo()));
                    }
                    if (!isEmailValid(this.inpEmail.getText().toString()).booleanValue()) {
                        onErrorVerificacionEmail();
                        return;
                    } else if (isRecordatorioValid(this.inpRecordatorio.getText().toString()).booleanValue()) {
                        if (this.F != null && this.H.booleanValue()) {
                            this.F.setCbuDestino("");
                        }
                        goToConfirmarABMDestinatario(this.E, this.F);
                        return;
                    } else {
                        onErrorVerificacionRecordatorio(this.inpRecordatorio.getText().toString());
                        return;
                    }
                } else {
                    h();
                    return;
                }
            case R.id.F23_21_BTN_GUARDAR /*2131362836*/:
                showDialogConfirmarEdicion();
                return;
            case R.id.F23_22_BTN_AGENDA /*2131362842*/:
                this.w = false;
                this.x = true;
                if (!this.v) {
                    this.u.showAlert();
                    return;
                } else {
                    finishConfirmacionABM(Integer.valueOf(-1));
                    return;
                }
            case R.id.F23_22_BTN_TRANSFERIR /*2131362843*/:
                this.w = true;
                this.x = false;
                if (!this.v) {
                    this.u.showAlert();
                    return;
                } else if (this.D.equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR)) {
                    finishConfirmacionABM(Integer.valueOf(-1));
                    return;
                } else if (this.E != null) {
                    this.A.onVerificar(this.C.getTipoDestino(), this.G.getTipo(), this.G.getSucursal(), this.G.getNumero(), this.E.getCbu(), this.C.getAlias());
                    return;
                } else {
                    this.A.onVerificar(this.C.getTipoDestino(), this.F.getTipo(), this.F.getSucursal(), this.F.getNumero(), null, this.C.getAlias());
                    return;
                }
            case R.id.toggle /*2131366059*/:
                switchDrawer();
                return;
            default:
                return;
        }
    }

    public String formatRecordatorio(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String str2 = "";
        for (char valueOf : str.toCharArray()) {
            Character valueOf2 = Character.valueOf(valueOf);
            if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ".indexOf(valueOf2.charValue()) == -1 || str2.length() >= 30) {
                break;
            }
            str2 = str2.concat(valueOf2.toString());
        }
        return str2;
    }

    public void onBackPressed() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                finishConfirmacionABM(Integer.valueOf(0));
                return;
            case 1:
                if (this.D.equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR)) {
                    finishConfirmacionABM(Integer.valueOf(0));
                    return;
                } else {
                    goBackToEditarDestinatario();
                    return;
                }
            case 2:
                this.w = false;
                this.x = true;
                if (!this.v) {
                    this.u.showAlert();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("ABMDestinatario", true);
                setResult(-1, intent);
                super.onBackPressed();
                hideKeyboard();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            default:
                return;
        }
    }

    public void gotoPage(int i) {
        gotoPage(i, true);
    }

    public void gotoPage(int i, boolean z2) {
        if (this.mControlPager != null) {
            switch (i) {
                case 0:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                    }
                    hideKeyboard();
                    break;
                case 1:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    hideKeyboard();
                    break;
                case 2:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    hideKeyboard();
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
        }
    }

    public void goToEditarDestinatario() {
        gotoPage(0);
        this.z.onCreatePage();
    }

    public void goBackToEditarDestinatario() {
        gotoPage(0, false);
    }

    public void goToConfirmarABMDestinatario(DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
        gotoPage(1);
        this.B.onCreatePage(datosCuentasDestOBBean, datosCuentasDestBSRBean);
    }

    public void goToComprobanteABMDestinatario(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean) {
        gotoPage(2);
        this.A.onCreatePage(aBMDestinatarioTransfBodyResponseBean);
    }

    public void setEditarDestinatarioView() {
        setActionBarEditar();
        this.lblTitleEdit.setText(getString(R.string.WF23_20_TRANSFERENCE_TITLE_EDITAR));
        this.lblTitularEdit.setText(this.C.getTitulo());
        this.inpRecordatorio.setText(this.C.getDescripcion());
        this.inpEmail.setText(this.C.getEmail());
        if (this.C.getAlias() == null || this.C.getAlias().isEmpty()) {
            this.titleAlias.setVisibility(8);
            this.lblAlias.setVisibility(8);
        } else {
            this.titleAlias.setVisibility(0);
            this.lblAlias.setVisibility(0);
            this.lblAlias.setText(this.C.getAlias());
        }
        this.lblBancoEdit.setText(this.C.getInfo1());
        if (this.C.getTipoDestino().equalsIgnoreCase("03")) {
            this.lblCuentasEdit.setVisibility(0);
            this.lblCuitCuentasEdit.setVisibility(0);
            this.lblCbuEdit.setText(this.C.getInfo2());
            this.lblCuitCuentasEdit.setText(CUIT.format(this.C.getBeneficiario()));
            this.tipoCuentaRowEdit.setVisibility(8);
            this.numeroCuentaRowEdit.setVisibility(8);
            try {
                this.lblCbuEdit.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCbuEdit.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.C.getAlias() == null || this.C.getAlias().isEmpty()) {
                this.rowCUITVerificar.setVisibility(8);
            } else {
                this.lblCuentasEdit.setVisibility(8);
                this.lblCuitCuentasEdit.setVisibility(8);
                this.rowCUITVerificar.setVisibility(0);
                this.txtCUITVerificar.setText(CUIT.format(this.C.getBeneficiario()));
            }
        } else {
            this.lblCuentasEdit.setVisibility(8);
            this.lblCuitCuentasEdit.setVisibility(8);
            if (this.F.getCbuDestino() == null || this.F.getCbuDestino().isEmpty()) {
                this.cbuRowEdit.setVisibility(8);
            } else {
                this.cbuRowEdit.setVisibility(0);
                this.lblCbuEdit.setText(this.F.getCbuDestino());
            }
            this.C.setTipoDescripcion(UtilAccount.getAccountTypeDescription(this.sessionManager, this.C.getInfo2(), ""));
            this.lblTipoCuentaEdit.setText(this.C.getTipoDescripcion());
            this.lblNumeroCuentaEdit.setText(UtilAccount.getAccountNumber(this.C.getInfo2()));
            this.lblNumeroCuentaDestinoEdit.setText(getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_DESTINATION_ACCOUNT));
            this.rowCUITVerificar.setVisibility(8);
        }
        try {
            this.txtCUITVerificar.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.txtCUITVerificar.getText().toString()));
            this.lblNumeroCuentaEdit.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblNumeroCuentaEdit.getText().toString()));
            this.lblCbuEdit.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterCharacterToCharacter(this.lblCbuEdit.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.btnContinuarEdit.setText(getString(R.string.WF23_20_TRANSFERENCE_TITLE_EDITAR));
        this.btnContinuarEdit.setOnClickListener(this);
    }

    public void setConfirmarABMDestinatarioView(DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
        setActionBarConfirmarEliminarDestinatario();
        this.tableLayoutConfirmar.removeAllViews();
        CAccessibility cAccessibility = new CAccessibility(this);
        try {
            this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), this.C.getTitulo(), this.C.getTitulo()));
            if (this.C.getAlias() != null && !this.C.getAlias().isEmpty()) {
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), this.C.getAlias(), this.C.getAlias()));
            }
            if (this.C.getTipoDestino().equals("03")) {
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL), getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL).replaceAll("/", UtilsCuentas.SEPARAOR2), CUIT.format(this.C.getBeneficiario()), cAccessibility.applyFilterCharacterToCharacter(CUIT.format(this.C.getBeneficiario()))));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), this.C.getInfo1(), this.C.getInfo1()));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), this.C.getInfo2(), cAccessibility.applyFilterCharacterToCharacter(this.C.getInfo2())));
            } else if (this.C.getTipoDestino().equals("02")) {
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR)));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_DESTINATION_ACCOUNT), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_DESTINATION_ACCOUNT), this.C.getInfo2(), cAccessibility.applyFilterAccount(this.C.getInfo2())));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), this.C.getTipoDescripcion(), this.C.getTipoDescripcion()));
                if (datosCuentasDestBSRBean.getCbuDestino() != null && !datosCuentasDestBSRBean.getCbuDestino().isEmpty()) {
                    this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), datosCuentasDestBSRBean.getCbuDestino(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestBSRBean.getCbuDestino())));
                }
            }
            if (this.D.equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR)) {
                this.lblTitleConfirmar.setText(getString(R.string.ID_3840_TRANSFERENCE_TITLE_CONFIRMAR_ELIMINAR_DESTINATARIO));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), this.C.getDescripcion(), this.C.getDescripcion()));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL), cAccessibility.applyFilterGeneral(getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL_DESCRIPTION)), this.C.getEmail(), cAccessibility.applyFilterGeneral(this.C.getEmail())));
                this.btnGuardarConfirmar.setText(getString(R.string.ID_3841_TRANSFERENCE_BOTON_ELIMINAR_DESTINATARIO));
            } else {
                this.lblTitleConfirmar.setText(getString(R.string.IDXX_TRANSFERENCE_EDITAR_DESTINATARIO_BTN_GUARDAR));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), this.inpRecordatorio.getText().toString(), this.inpRecordatorio.getText().toString()));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL), cAccessibility.applyFilterGeneral(getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL_DESCRIPTION)), this.inpEmail.getText().toString(), cAccessibility.applyFilterGeneral(this.inpEmail.getText().toString())));
                this.btnGuardarConfirmar.setText(getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_BTN_SAVE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.btnGuardarConfirmar.setOnClickListener(this);
    }

    public void setComprobanteABMDestinatarioView(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean) {
        this.inputNumeroComprobante = aBMDestinatarioTransfBodyResponseBean.nroComprobante;
        setActionBarComprobante();
        CAccessibility cAccessibility = new CAccessibility(this);
        this.tableLayoutComprobante.removeAllViews();
        try {
            this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), this.C.getTitulo(), this.C.getTitulo()));
            if (this.C.getAlias() != null && !this.C.getAlias().isEmpty()) {
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), this.C.getAlias(), this.C.getAlias()));
            }
            if (this.C.getTipoDestino().equals("03")) {
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL), getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL).replaceAll("/", UtilsCuentas.SEPARAOR2), CUIT.format(this.C.getBeneficiario()), cAccessibility.applyFilterCharacterToCharacter(CUIT.format(this.C.getBeneficiario()))));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), this.C.getInfo2(), cAccessibility.applyFilterCharacterToCharacter(this.C.getInfo2())));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), this.C.getInfo1(), cAccessibility.applyFilterGeneral(this.C.getInfo1())));
                if (this.D.equals(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_EDITAR)) {
                    String descripcionModificada = this.E.getDescripcionModificada();
                    this.E.setDescripcion(descripcionModificada);
                    this.C.setDescripcion(descripcionModificada);
                }
            } else if (this.C.getTipoDestino().equals("02")) {
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR)));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_DESTINATION_ACCOUNT), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_DESTINATION_ACCOUNT), this.C.getInfo2(), cAccessibility.applyFilterAccount(this.C.getInfo2())));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), this.C.getTipoDescripcion(), this.C.getTipoDescripcion()));
                if (this.F.getCbuDestino() != null && !this.F.getCbuDestino().isEmpty()) {
                    this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), this.F.getCbuDestino(), cAccessibility.applyFilterCharacterToCharacter(this.F.getCbuDestino())));
                }
                if (this.D.equals(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_EDITAR)) {
                    String descripcionModificada2 = this.F.getDescripcionModificada();
                    this.F.setDescripcion(descripcionModificada2);
                    this.C.setDescripcion(descripcionModificada2);
                }
            }
            if (this.D.equals(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR)) {
                this.lblTitleComprobante.setText(getString(R.string.WF23_TRANSFERENCE_LBL_DATA_COMPROBANTE_DE_ELIMINACION));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), this.C.getDescripcion().toString(), this.C.getDescripcion()));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL), cAccessibility.applyFilterGeneral(getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL_DESCRIPTION)), this.C.getEmail(), cAccessibility.applyFilterGeneral(this.C.getEmail())));
                this.btnTransferirComprobante.setVisibility(8);
                this.btnAgendaComprobante.setText(getString(R.string.CONTENT_ATRAS));
                this.btnAgendaComprobante.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
                this.btnAgendaComprobante.setTextColor(getResources().getColor(R.color.white));
            } else if (this.D.equals(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_EDITAR)) {
                this.lblTitleComprobante.setText(getString(R.string.WF23_22_TRANSFERENCE_TITLE_COMPROBANTE_EDICION));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), this.inpRecordatorio.getText().toString(), this.inpRecordatorio.getText().toString()));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL), cAccessibility.applyFilterGeneral(getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL_DESCRIPTION)), this.inpEmail.getText().toString(), cAccessibility.applyFilterGeneral(this.inpEmail.getText().toString())));
                this.btnTransferirComprobante.setOnClickListener(this);
            }
            this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_489_TRANSFERENCE_PROOF_DONE_LBL_SELECT), cAccessibility.applyFilterGeneral(getString(R.string.ID_489_TRANSFERENCE_PROOF_DONE_LBL_SELECT_DESCRIPTION)), aBMDestinatarioTransfBodyResponseBean.nroComprobante, cAccessibility.applyFilterCharacterToCharacter(aBMDestinatarioTransfBodyResponseBean.nroComprobante)));
            String dateFormat2 = UtilDate.getDateFormat2(aBMDestinatarioTransfBodyResponseBean.fechaOperacion);
            this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_458_TRANSFERENCE_MAIN_EXEDATE), getString(R.string.ID_458_TRANSFERENCE_MAIN_EXEDATE), dateFormat2, cAccessibility.applyFilterDate(dateFormat2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.btnAgendaComprobante.setOnClickListener(this);
    }

    public void finishConfirmacionABM(Integer num, Boolean bool, VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean) {
        Intent intent = new Intent();
        if (verificaDatosInicialesTransfOBResponseBean != null) {
            intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO, this.C);
            if (this.E != null) {
                this.E.setDiferido(verificaDatosInicialesTransfOBResponseBean.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getDiferido());
                this.E.setBeneficiario(verificaDatosInicialesTransfOBResponseBean.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getNumeroCuil());
            }
            intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_OB_BEAN, this.E);
            intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_BSR_BEAN, this.F);
            VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean = verificaDatosInicialesTransfOBResponseBean.getVerificaDatosInicialesTransfOBBodyResponseBean();
            intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_VERIFICA_DATOS_TRANSF, verificaDatosInicialesTransfOBBodyResponseBean);
            if (verificaDatosInicialesTransfOBBodyResponseBean.resCod != null && !verificaDatosInicialesTransfOBBodyResponseBean.resCod.isEmpty()) {
                intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_EJECUTAR_REASIGNACION, verificaDatosInicialesTransfOBBodyResponseBean.resDesc);
            }
        }
        setResult(num.intValue(), intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }

    public void finishConfirmacionABM(Integer num) {
        setResult(num.intValue(), new Intent());
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }

    public void showDialogConfirmarEdicion() {
        if (this.D.equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR)) {
            final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.WF23_TRANFERENCE_LBL_TITLE_CONFIRMAR), getString(R.string.ID_3840_TRANSFERENCE_TITLE_CONFIRMAR_ELIMINAR_DESTINATARIO), null, null, getString(R.string.WF23_TRANSFERENCE_LBL_DATA_SI), getString(R.string.WF23_TRANSFERENCE_LBL_DATA_NO), null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    if (EditarEliminarDestinatarioTransferenciaActivity.this.C.getTipoDestino().equalsIgnoreCase("03")) {
                        EditarEliminarDestinatarioTransferenciaActivity.this.E = new DatosCuentasDestOBBean();
                        EditarEliminarDestinatarioTransferenciaActivity.this.E.setDescripcion(EditarEliminarDestinatarioTransferenciaActivity.this.C.getDescripcion());
                        EditarEliminarDestinatarioTransferenciaActivity.this.E.setCbu(EditarEliminarDestinatarioTransferenciaActivity.this.C.getInfo2());
                    } else if (EditarEliminarDestinatarioTransferenciaActivity.this.C.getTipoDestino().equals("02")) {
                        String str = EditarEliminarDestinatarioTransferenciaActivity.this.C.getDescripcion().toString();
                        String str2 = EditarEliminarDestinatarioTransferenciaActivity.this.F.getTipo().toString();
                        String str3 = EditarEliminarDestinatarioTransferenciaActivity.this.F.getSucursal().toString();
                        String str4 = EditarEliminarDestinatarioTransferenciaActivity.this.F.getNumero().toString();
                        EditarEliminarDestinatarioTransferenciaActivity.this.F = new DatosCuentasDestBSRBean();
                        EditarEliminarDestinatarioTransferenciaActivity.this.F.setDescripcion(str);
                        EditarEliminarDestinatarioTransferenciaActivity.this.F.setTipo(str2);
                        EditarEliminarDestinatarioTransferenciaActivity.this.F.setSucursal(str3);
                        EditarEliminarDestinatarioTransferenciaActivity.this.F.setNumero(str4);
                    }
                    EditarEliminarDestinatarioTransferenciaActivity.this.B.onConfirmar(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR, "B", EditarEliminarDestinatarioTransferenciaActivity.this.C.getTipoDestino().equalsIgnoreCase("03") ? CuentaBanco.OTROS : CuentaBanco.BANCO_SANTANDER_RIO, null, EditarEliminarDestinatarioTransferenciaActivity.this.F, EditarEliminarDestinatarioTransferenciaActivity.this.E, Boolean.valueOf(false));
                }

                public void onNegativeButton() {
                    newInstance.closeDialog();
                }
            });
            newInstance.show(getSupportFragmentManager(), "popupConfirmation");
            return;
        }
        final IsbanDialogFragment newInstance2 = IsbanDialogFragment.newInstance(getString(R.string.WF23_20_TRANSFERENCE_TITLE_EDITAR), getString(R.string.WF23_21_TRANSFERENCE_LBL_DATA_CONFIRMA_EDICION), null, null, getString(R.string.WF23_TRANSFERENCE_LBL_DATA_SI), getString(R.string.WF23_TRANSFERENCE_LBL_DATA_NO), null);
        newInstance2.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                if (EditarEliminarDestinatarioTransferenciaActivity.this.C.getTipoDestino().equalsIgnoreCase("03")) {
                    if (EditarEliminarDestinatarioTransferenciaActivity.this.E == null) {
                        EditarEliminarDestinatarioTransferenciaActivity.this.E = new DatosCuentasDestOBBean();
                    }
                    EditarEliminarDestinatarioTransferenciaActivity.this.E.setBancoDestino(EditarEliminarDestinatarioTransferenciaActivity.this.E.getBanco());
                    EditarEliminarDestinatarioTransferenciaActivity.this.E.setBanco(null);
                    EditarEliminarDestinatarioTransferenciaActivity.this.E.setDescripcionModificada(EditarEliminarDestinatarioTransferenciaActivity.this.inpRecordatorio.getText().toString());
                    EditarEliminarDestinatarioTransferenciaActivity.this.E.setEmail(EditarEliminarDestinatarioTransferenciaActivity.this.inpEmail.getText().toString());
                    if (!TextUtils.isEmpty(EditarEliminarDestinatarioTransferenciaActivity.this.E.getDescripcion())) {
                        EditarEliminarDestinatarioTransferenciaActivity.this.E.setDescripcion(EditarEliminarDestinatarioTransferenciaActivity.this.E.getDescripcion());
                    }
                } else {
                    if (EditarEliminarDestinatarioTransferenciaActivity.this.F == null) {
                        EditarEliminarDestinatarioTransferenciaActivity.this.F = new DatosCuentasDestBSRBean();
                    }
                    EditarEliminarDestinatarioTransferenciaActivity.this.F.setDescripcionModificada(EditarEliminarDestinatarioTransferenciaActivity.this.inpRecordatorio.getText().toString());
                    EditarEliminarDestinatarioTransferenciaActivity.this.F.setEmail(EditarEliminarDestinatarioTransferenciaActivity.this.inpEmail.getText().toString());
                    if (!TextUtils.isEmpty(EditarEliminarDestinatarioTransferenciaActivity.this.F.getDescripcion())) {
                        EditarEliminarDestinatarioTransferenciaActivity.this.F.setDescripcion(EditarEliminarDestinatarioTransferenciaActivity.this.F.getDescripcion());
                    }
                }
                EditarEliminarDestinatarioTransferenciaActivity.this.B.onConfirmar(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_EDITAR, EditarEliminarDestinatarioTransferenciaActivity.this.I != null ? "R" : "M", EditarEliminarDestinatarioTransferenciaActivity.this.C.getTipoDestino().equalsIgnoreCase("03") ? CuentaBanco.OTROS : CuentaBanco.BANCO_SANTANDER_RIO, EditarEliminarDestinatarioTransferenciaActivity.this.I, EditarEliminarDestinatarioTransferenciaActivity.this.F, EditarEliminarDestinatarioTransferenciaActivity.this.E, EditarEliminarDestinatarioTransferenciaActivity.this.H);
            }

            public void onNegativeButton() {
                newInstance2.closeDialog();
            }
        });
        newInstance2.show(getSupportFragmentManager(), "popupConfirmation");
    }

    private void h() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpErrorEdicion", getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), "No se han realizado cambios en los datos.", getString(R.string.ID1_ALERT_BTN_ACCEPT));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpErrorEdicion");
    }

    public void onErrorVerificacionEmail() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpErrorEmail", getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000020_SusSorpresa_errorCorreo), getString(R.string.ID1_ALERT_BTN_ACCEPT));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpErrorEmail");
    }

    public void onErrorVerificacionRecordatorio(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            str2 = getString(R.string.MSG_USER0000XX_Transferencia_errorDestinatarioVacio);
        } else {
            str2 = getString(R.string.MSG_USER0000XX_Transferencia_errorDestinatario);
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpErrorRecordatorio", getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str2, getString(R.string.ID1_ALERT_BTN_ACCEPT));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpErrorEmail");
    }

    public void attachView() {
        if (!this.z.isViewAttached()) {
            this.z.attachView(this);
        }
        if (!this.B.isViewAttached()) {
            this.B.attachView(this);
        }
        if (!this.A.isViewAttached()) {
            this.A.attachView(this);
        }
    }

    public void detachView() {
        if (this.z.isViewAttached()) {
            this.z.detachView();
        }
        if (this.B.isViewAttached()) {
            this.B.detachView();
        }
        if (this.A.isViewAttached()) {
            this.A.detachView();
        }
    }
}
