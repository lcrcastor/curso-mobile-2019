package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.TableViewRowCreator;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.AltaManualDestinatarioTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.AltaManualDestinatarioTransferenciaView;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.ComprobanteABMDestinatarioTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.ComprobanteABMDestinatarioTransferenciaView;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.ConfirmarABMDestinatarioTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.ConfirmarABMDestinatarioTransferenciaView;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.EditarDestinatarioTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.EditarDestinatarioTransferenciaView;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.NuevoDestinatarioTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.NuevoDestinatarioTransferenciaView;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.CuentaBanco;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.Origen;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.TipoAlta;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CtaMigradaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.IHorizontalScrollListListener;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.ToggleItem;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class NuevoDestinatarioTransferenciaActivity extends MvpPrivateMenuActivity implements OnClickListener, AltaManualDestinatarioTransferenciaView, ComprobanteABMDestinatarioTransferenciaView, ConfirmarABMDestinatarioTransferenciaView, EditarDestinatarioTransferenciaView, NuevoDestinatarioTransferenciaView, IHorizontalScrollListListener {
    public static final int REQUEST_CODE_ABM_TRANSF_COMPROBANTE_ALTA = 99;
    /* access modifiers changed from: private */
    public String A;
    /* access modifiers changed from: private */
    public String B;
    /* access modifiers changed from: private */
    public String C;
    /* access modifiers changed from: private */
    public String D = TipoAlta.CBU;
    /* access modifiers changed from: private */
    public CAccessibility E = new CAccessibility(this);
    /* access modifiers changed from: private */
    public OptionsToShare F;
    /* access modifiers changed from: private */
    public boolean G = false;
    /* access modifiers changed from: private */
    public boolean H = false;
    private boolean I = false;
    /* access modifiers changed from: private */
    public IsbanDialogFragment J;
    private VerificaDatosInicialesTransfOBResponseBean K = null;
    private String L;
    private String M;
    @InjectView(2131362764)
    Button btnAgenda;
    @InjectView(2131362770)
    Button btnClearFields;
    @InjectView(2131362810)
    Button btnContinuarPantallaVerificar;
    @InjectView(2131362771)
    Button btnContinuarSinVerificar;
    @InjectView(2131362790)
    Button btnGuardarAltaManual;
    @InjectView(2131362836)
    TextView btnGuardarConfirmar;
    @InjectView(2131362765)
    Button btnTransferir;
    @InjectView(2131362772)
    Button btnVerificar;
    @InjectView(2131362774)
    EditText cEditTextNuevoDest;
    @InjectView(2131362768)
    ScrollView comprobanteOperacion;
    @InjectView(2131362791)
    EditText editTextBancoAltaManual;
    @InjectView(2131362792)
    EditText editTextCUITAltaManual;
    @InjectView(2131362793)
    EditText editTextEmailAltaManual;
    @InjectView(2131362811)
    EditText editTextEmailVerificar;
    @InjectView(2131362794)
    EditText editTextRecordatorioAltaManual;
    @InjectView(2131362812)
    EditText editTextRecordatorioVerificar;
    @InjectView(2131362795)
    EditText editTextTitularAltaManual;
    public String inputNumeroComprobante = "";
    @InjectView(2131362775)
    EditText inputTercerosBSR1;
    @InjectView(2131362776)
    EditText inputTercerosBSR2;
    @InjectView(2131362777)
    EditText inputTercerosBSR3;
    @InjectView(2131362813)
    TextView lblAliasVerificar;
    @InjectView(2131362817)
    TextView lblCUITHeaderVerificar;
    @InjectView(2131362779)
    TextView lblInputHint;
    @InjectView(2131362781)
    TextView lblSelectorTipoAlta;
    @InjectView(2131362829)
    TextView lblTipoCuentaVerificar;
    @InjectView(2131362787)
    LinearLayout lnlSelectorCuenta;
    @InjectView(2131362786)
    LinearLayout lnlSelectorTipoAlta;
    @InjectView(2131365269)
    ViewFlipper mControlPager;
    @InjectView(2131362784)
    LinearLayout nuevoDestinatarioPorCBU;
    @InjectView(2131362785)
    LinearLayout nuevoDestinatarioPorCuentaBSR;
    @Inject
    SoftTokenManager p;
    @Inject
    AnalyticsManager q;
    private NuevoDestinatarioTransferenciaPresenter r;
    @InjectView(2131362832)
    LinearLayout rowCBUVerificar;
    @InjectView(2131362833)
    LinearLayout rowCUITVerificar;
    @InjectView(2131362834)
    LinearLayout rowNumeroCuentaVerificar;
    @InjectView(2131362835)
    LinearLayout rowTipoCuentaVerificar;
    private AltaManualDestinatarioTransferenciaPresenter s;
    private EditarDestinatarioTransferenciaPresenter t;
    @InjectView(2131362773)
    HorizontalScrollList tabSelector;
    @InjectView(2131362769)
    TableLayout tableLayoutComprobante;
    @InjectView(2131362841)
    TableLayout tableLayoutConfirmar;
    @InjectView(2131362780)
    TextView textViewAlertInvalidCBU;
    @InjectView(2131362818)
    TextView txtAliasVerificar;
    @InjectView(2131362819)
    TextView txtBancoVerificar;
    @InjectView(2131362799)
    TextView txtCBUAltaManual;
    @InjectView(2131362820)
    TextView txtCBUVerificar;
    @InjectView(2131362822)
    TextView txtCUITHeaderVerificar;
    @InjectView(2131362821)
    TextView txtCUITVerificar;
    @InjectView(2131362823)
    TextView txtNumeroCuentaVerificar;
    @InjectView(2131362778)
    TextView txtTipoCuenta;
    @InjectView(2131362824)
    TextView txtTipoCuentaVerificar;
    @InjectView(2131362837)
    TextView txtTitleConfirmar;
    @InjectView(2131362830)
    TextView txtTitleVerificar;
    @InjectView(2131362825)
    TextView txtTitularVerificar;
    /* access modifiers changed from: private */
    public ConfirmarABMDestinatarioTransferenciaPresenter u;
    private ComprobanteABMDestinatarioTransferenciaPresenter v;
    /* access modifiers changed from: private */
    public DatosCuentasDestBSRBean w = null;
    /* access modifiers changed from: private */
    public DatosCuentasDestOBBean x = null;
    private CtaMigradaBean y;
    private String z;

    public void OnCheckedChangeListener(List<ToggleItem> list) {
    }

    public Activity getActivity() {
        return this;
    }

    public int getMainLayout() {
        return R.layout.nuevo_destinatario_activity;
    }

    public void setComprobanteABMDestinatarioView(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean) {
    }

    public void setEditarDestinatarioView() {
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
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        initialize();
        configureActionBar();
    }

    public void initialize() {
        this.r = new NuevoDestinatarioTransferenciaPresenter(this.mBus, this.mDataManager, this.sessionManager, this);
        this.t = new EditarDestinatarioTransferenciaPresenter(this.mBus, this.mDataManager);
        this.s = new AltaManualDestinatarioTransferenciaPresenter(this.mBus, this.mDataManager);
        ConfirmarABMDestinatarioTransferenciaPresenter confirmarABMDestinatarioTransferenciaPresenter = new ConfirmarABMDestinatarioTransferenciaPresenter(this.mBus, this.mDataManager, this.p, this, this.q);
        this.u = confirmarABMDestinatarioTransferenciaPresenter;
        this.v = new ComprobanteABMDestinatarioTransferenciaPresenter(this.mBus, this.mDataManager, this.sessionManager, this);
        this.z = getIntent().getStringExtra("ORIGEN");
        this.y = (CtaMigradaBean) getIntent().getParcelableExtra(TransferenciasConstants.INTENT_CUENTA_SELECCIONADA_MIGRADA);
        goToNuevoDestinatario();
    }

    /* JADX WARNING: Removed duplicated region for block: B:133:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x02bc  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0305  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0329  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r12) {
        /*
            r11 = this;
            int r12 = r12.getId()
            r0 = 2131757134(0x7f10084e, float:1.9145195E38)
            r1 = 0
            r2 = 0
            r3 = 1
            r4 = -1
            switch(r12) {
                case 2131362764: goto L_0x037c;
                case 2131362765: goto L_0x0360;
                case 2131362770: goto L_0x0357;
                case 2131362771: goto L_0x0350;
                case 2131362772: goto L_0x0279;
                case 2131362786: goto L_0x0274;
                case 2131362787: goto L_0x026f;
                case 2131362790: goto L_0x0190;
                case 2131362810: goto L_0x0015;
                case 2131362836: goto L_0x0010;
                default: goto L_0x000e;
            }
        L_0x000e:
            goto L_0x0391
        L_0x0010:
            r11.showDialogConfirmarAlta()
            goto L_0x0391
        L_0x0015:
            r11.hideKeyboard()
            android.widget.EditText r12 = r11.editTextEmailVerificar
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            java.lang.Boolean r12 = isEmailValid(r12)
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x0187
            java.lang.String r12 = r11.A
            java.lang.String r0 = "05"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x00d0
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r12 = r11.x
            android.widget.EditText r0 = r11.editTextEmailVerificar
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            r12.setEmail(r0)
            android.widget.EditText r12 = r11.editTextRecordatorioVerificar
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            boolean r12 = r12.isEmpty()
            if (r12 != 0) goto L_0x0065
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r12 = r11.x
            android.widget.EditText r0 = r11.editTextRecordatorioVerificar
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            r12.setDescripcion(r0)
            goto L_0x0083
        L_0x0065:
            android.widget.EditText r12 = r11.editTextRecordatorioVerificar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r0 = r11.x
            java.lang.String r0 = r0.getNombreTitular()
            java.lang.String r0 = r11.formatRecordatorio(r0)
            r12.setText(r0)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r12 = r11.x
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r0 = r11.x
            java.lang.String r0 = r0.getNombreTitular()
            java.lang.String r0 = r11.formatRecordatorio(r0)
            r12.setDescripcion(r0)
        L_0x0083:
            android.widget.EditText r12 = r11.editTextRecordatorioVerificar
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            java.lang.Boolean r12 = isRecordatorioValid(r12)
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x00c1
            java.lang.String r12 = r11.z
            java.lang.String r0 = "NuevoAgenda"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x00a8
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r12 = r11.x
            r11.goToConfirmarABMDestinatario(r12, r1)
            goto L_0x0391
        L_0x00a8:
            java.lang.String r12 = r11.z
            java.lang.String r0 = "Para"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x0391
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r1 = r11.K
            r11.finishConfirmacionABM(r12, r0, r1)
            goto L_0x0391
        L_0x00c1:
            android.widget.EditText r12 = r11.editTextRecordatorioVerificar
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            r11.onErrorVerificacionRecordatorio(r12)
            goto L_0x0391
        L_0x00d0:
            java.lang.String r12 = r11.A
            java.lang.String r0 = "04"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x0391
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r12 = r11.w
            android.widget.EditText r0 = r11.editTextEmailVerificar
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            r12.setEmail(r0)
            android.widget.EditText r12 = r11.editTextRecordatorioVerificar
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            boolean r12 = r12.isEmpty()
            if (r12 != 0) goto L_0x0109
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r12 = r11.w
            android.widget.EditText r0 = r11.editTextRecordatorioVerificar
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            r12.setDescripcion(r0)
            goto L_0x0127
        L_0x0109:
            android.widget.EditText r12 = r11.editTextRecordatorioVerificar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = r11.w
            java.lang.String r0 = r0.getNombreTitular()
            java.lang.String r0 = r11.formatRecordatorio(r0)
            r12.setText(r0)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r12 = r11.w
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = r11.w
            java.lang.String r0 = r0.getNombreTitular()
            java.lang.String r0 = r11.formatRecordatorio(r0)
            r12.setDescripcion(r0)
        L_0x0127:
            java.lang.String r12 = r11.i()
            java.lang.String r0 = "BSR"
            boolean r12 = r12.equalsIgnoreCase(r0)
            if (r12 == 0) goto L_0x013a
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r12 = r11.w
            java.lang.String r0 = ""
            r12.setCbuDestino(r0)
        L_0x013a:
            android.widget.EditText r12 = r11.editTextRecordatorioVerificar
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            java.lang.Boolean r12 = isRecordatorioValid(r12)
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x0178
            java.lang.String r12 = r11.z
            java.lang.String r0 = "NuevoAgenda"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x015f
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r12 = r11.w
            r11.goToConfirmarABMDestinatario(r1, r12)
            goto L_0x0391
        L_0x015f:
            java.lang.String r12 = r11.z
            java.lang.String r0 = "Para"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x0391
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r1 = r11.K
            r11.finishConfirmacionABM(r12, r0, r1)
            goto L_0x0391
        L_0x0178:
            android.widget.EditText r12 = r11.editTextRecordatorioVerificar
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            r11.onErrorVerificacionRecordatorio(r12)
            goto L_0x0391
        L_0x0187:
            java.lang.String r12 = r11.getString(r0)
            r11.onInvalidInputError(r12)
            goto L_0x0391
        L_0x0190:
            r11.hideKeyboard()
            android.widget.EditText r12 = r11.editTextRecordatorioAltaManual
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 == 0) goto L_0x01b6
            android.widget.EditText r12 = r11.editTextRecordatorioAltaManual
            android.widget.EditText r2 = r11.editTextTitularAltaManual
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            java.lang.String r2 = r11.formatRecordatorio(r2)
            r12.setText(r2)
        L_0x01b6:
            android.widget.EditText r12 = r11.editTextCUITAltaManual
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            java.lang.Boolean r12 = ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT.isValid(r12)
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x0223
            android.widget.EditText r12 = r11.editTextEmailAltaManual
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            java.lang.Boolean r12 = isEmailValid(r12)
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x0223
            android.widget.EditText r12 = r11.editTextRecordatorioAltaManual
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            java.lang.Boolean r12 = isRecordatorioValid(r12)
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x0223
            r11.k()
            java.lang.String r12 = "MANUAL"
            r11.D = r12
            java.lang.String r12 = r11.z
            java.lang.String r0 = "NuevoAgenda"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x020a
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r12 = r11.x
            r11.goToConfirmarABMDestinatario(r12, r1)
            goto L_0x0391
        L_0x020a:
            java.lang.String r12 = r11.z
            java.lang.String r0 = "Para"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x0391
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r1 = r11.K
            r11.finishConfirmacionABM(r12, r0, r1)
            goto L_0x0391
        L_0x0223:
            android.widget.EditText r12 = r11.editTextRecordatorioAltaManual
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            java.lang.Boolean r12 = isRecordatorioValid(r12)
            boolean r12 = r12.booleanValue()
            if (r12 != 0) goto L_0x0246
            android.widget.EditText r12 = r11.editTextRecordatorioAltaManual
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            r11.onErrorVerificacionRecordatorio(r12)
            goto L_0x0391
        L_0x0246:
            android.widget.EditText r12 = r11.editTextEmailAltaManual
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            java.lang.Boolean r12 = isEmailValid(r12)
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x0266
            r12 = 2131757167(0x7f10086f, float:1.9145262E38)
            java.lang.String r12 = r11.getString(r12)
            r11.onInvalidInputError(r12)
            goto L_0x0391
        L_0x0266:
            java.lang.String r12 = r11.getString(r0)
            r11.onInvalidInputError(r12)
            goto L_0x0391
        L_0x026f:
            r11.c()
            goto L_0x0391
        L_0x0274:
            r11.d()
            goto L_0x0391
        L_0x0279:
            r11.hideKeyboard()
            java.lang.String r12 = r11.i()
            r11.D = r12
            java.lang.String r12 = r11.D
            int r0 = r12.hashCode()
            r1 = 66081(0x10221, float:9.2599E-41)
            if (r0 == r1) goto L_0x02ac
            r1 = 63350320(0x3c6a630, float:1.1675549E-36)
            if (r0 == r1) goto L_0x02a2
            r1 = 1302695049(0x4da58c89, float:3.47181344E8)
            if (r0 == r1) goto L_0x0298
            goto L_0x02b6
        L_0x0298:
            java.lang.String r0 = "CBU/CVU"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x02b6
            r12 = 0
            goto L_0x02b7
        L_0x02a2:
            java.lang.String r0 = "Alias"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x02b6
            r12 = 1
            goto L_0x02b7
        L_0x02ac:
            java.lang.String r0 = "BSR"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x02b6
            r12 = 2
            goto L_0x02b7
        L_0x02b6:
            r12 = -1
        L_0x02b7:
            switch(r12) {
                case 0: goto L_0x0329;
                case 1: goto L_0x0305;
                case 2: goto L_0x02bc;
                default: goto L_0x02ba;
            }
        L_0x02ba:
            goto L_0x0391
        L_0x02bc:
            java.lang.String r12 = "04"
            r11.A = r12
            java.lang.String r12 = r11.B
            java.lang.String r0 = "02"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x02ce
            java.lang.String r12 = "09"
            r11.B = r12
        L_0x02ce:
            ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf.NuevoDestinatarioTransferenciaPresenter r3 = r11.r
            java.lang.String r4 = r11.A
            java.lang.String r5 = r11.B
            android.widget.EditText r12 = r11.inputTercerosBSR1
            android.text.Editable r12 = r12.getText()
            java.lang.String r6 = r12.toString()
            android.widget.EditText r12 = r11.inputTercerosBSR2
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            android.widget.EditText r0 = r11.inputTercerosBSR3
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r12 = r12.concat(r0)
            java.lang.String r7 = ar.com.santander.rio.mbanking.utils.UtilAccount.formatNumeroCuenta(r12)
            r8 = 0
            r9 = 0
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r2)
            r3.onVerificar(r4, r5, r6, r7, r8, r9, r10)
            goto L_0x0391
        L_0x0305:
            java.lang.String r12 = "05"
            r11.A = r12
            android.widget.EditText r12 = r11.cEditTextNuevoDest
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            boolean r12 = ar.com.santander.rio.mbanking.utils.UtilAccount.validarAlias(r12)
            if (r12 == 0) goto L_0x031e
            r11.j()
            goto L_0x0391
        L_0x031e:
            r12 = 2131756500(0x7f1005d4, float:1.914391E38)
            java.lang.String r12 = r11.getString(r12)
            r11.onInvalidInputError(r12)
            goto L_0x0391
        L_0x0329:
            java.lang.String r12 = "05"
            r11.A = r12
            android.widget.EditText r12 = r11.cEditTextNuevoDest
            android.text.Editable r12 = r12.getText()
            java.lang.String r12 = r12.toString()
            java.lang.Boolean r12 = ar.com.santander.rio.mbanking.utils.UtilAccount.isCBUValid(r12)
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x0345
            r11.j()
            goto L_0x0391
        L_0x0345:
            r12 = 2131756501(0x7f1005d5, float:1.9143911E38)
            java.lang.String r12 = r11.getString(r12)
            r11.onInvalidInputError(r12)
            goto L_0x0391
        L_0x0350:
            r11.hideKeyboard()
            r11.goToAltaSinVerificar()
            goto L_0x0391
        L_0x0357:
            r11.clearScreenData()
            android.widget.EditText r12 = r11.inputTercerosBSR1
            r12.requestFocus()
            goto L_0x0391
        L_0x0360:
            r11.H = r3
            r11.I = r2
            boolean r12 = r11.G
            if (r12 != 0) goto L_0x036e
            ar.com.santander.rio.mbanking.components.share.OptionsToShare r12 = r11.F
            r12.showAlert()
            goto L_0x0391
        L_0x036e:
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r1 = r11.K
            r11.finishConfirmacionABM(r12, r0, r1)
            goto L_0x0391
        L_0x037c:
            r11.H = r2
            r11.I = r3
            boolean r12 = r11.G
            if (r12 != 0) goto L_0x038a
            ar.com.santander.rio.mbanking.components.share.OptionsToShare r12 = r11.F
            r12.showAlert()
            goto L_0x0391
        L_0x038a:
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            r11.finishConfirmacionABM(r12)
        L_0x0391:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.NuevoDestinatarioTransferenciaActivity.onClick(android.view.View):void");
    }

    public void onBackPressed() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                finishConfirmacionABM(Integer.valueOf(0));
                hideKeyboard();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case 1:
                hideKeyboard();
                goBackToNuevoDestinatario();
                return;
            case 2:
                hideKeyboard();
                goBackToNuevoDestinatario();
                return;
            case 3:
                if (this.D.equals(TipoAlta.MANUAL)) {
                    goBackToAltaSinVerificar();
                    return;
                } else {
                    goBackToEditarDestinatario();
                    return;
                }
            case 4:
                this.H = false;
                this.I = true;
                if (!this.G) {
                    this.F.showAlert();
                    return;
                } else {
                    finishConfirmacionABM(Integer.valueOf(-1));
                    return;
                }
            default:
                return;
        }
    }

    public void clearScreenData() {
        try {
            this.cEditTextNuevoDest.setText("");
            this.inputTercerosBSR1.setText("");
            this.inputTercerosBSR2.setText("");
            this.inputTercerosBSR3.setText("");
        } catch (Exception unused) {
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
                    }
                    hideKeyboard();
                    break;
                case 1:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    }
                    hideKeyboard();
                    break;
                case 2:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    }
                    hideKeyboard();
                    break;
                case 3:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    hideKeyboard();
                    break;
                case 4:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
        }
    }

    public void goToNuevoDestinatario() {
        gotoPage(0);
        this.r.onCreatePage();
        configureActionBar();
    }

    public void goBackToNuevoDestinatario() {
        gotoPage(0, false);
    }

    public void goToEditarDestinatario(VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean, CtaMigradaBean ctaMigradaBean) {
        if (ctaMigradaBean != null) {
            verificaDatosInicialesTransfOBResponseBean.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().setNumero(ctaMigradaBean.getNumero());
            verificaDatosInicialesTransfOBResponseBean.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().setSucursal(ctaMigradaBean.getSucursal());
        }
        gotoPage(1);
        this.t.onCreatePage(verificaDatosInicialesTransfOBResponseBean, ctaMigradaBean);
    }

    public void goBackToEditarDestinatario() {
        gotoPage(1, false);
    }

    public void goToAltaSinVerificar() {
        gotoPage(2);
        this.s.onCreatePage();
    }

    public void goBackToAltaSinVerificar() {
        gotoPage(2, false);
    }

    public void goToConfirmarABMDestinatario(DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
        gotoPage(3);
        this.u.onCreatePage(datosCuentasDestOBBean, datosCuentasDestBSRBean);
    }

    public void goToComprobanteABMDestinatario(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean) {
        gotoPage(4);
        configureActionBar();
        this.v.onCreatePage(aBMDestinatarioTransfBodyResponseBean, this.x, this.w);
    }

    public void supermetododeprueba(CtaMigradaBean ctaMigradaBean) {
        this.y = ctaMigradaBean;
        b();
    }

    private void b() {
        if (this.y != null) {
            this.tabSelector.removeItems();
            this.tabSelector.addItem(getString(R.string.ID_3813_TRANSFERENCE_BY_CBU_OR_ALIAS), false, this.L.toString());
            this.tabSelector.addItem(getString(R.string.ID_3814_TRANSFERENCE_TBSR), true, this.M);
            this.A = "04";
            this.btnContinuarSinVerificar.setVisibility(8);
            this.textViewAlertInvalidCBU.setVisibility(8);
            this.nuevoDestinatarioPorCBU.setVisibility(8);
            this.nuevoDestinatarioPorCuentaBSR.setVisibility(0);
            this.q.trackScreen(getString(R.string.analytics_screen_name_nuevo_destinatario_tsr));
            clearScreenData();
            this.inputTercerosBSR1.setText(this.y.getSucursal() != null ? this.y.getSucursal() : "");
            this.inputTercerosBSR2.setText(this.y.getNumero() != null ? this.y.getNumero() : "");
            this.inputTercerosBSR3.setText((this.y.getNumero() == null || this.y.getNumero().length() <= 6) ? "" : this.y.getNumero().substring(6, 7));
        } else {
            this.tabSelector.addItem(getString(R.string.ID_3813_TRANSFERENCE_BY_CBU_OR_ALIAS), true, this.L.toString());
            this.tabSelector.addItem(getString(R.string.ID_3814_TRANSFERENCE_TBSR), false, this.M);
        }
        this.tabSelector.setHorizontalScrollListener(this);
        this.tabSelector.show();
    }

    public void setNuevoDestinatarioView() {
        this.A = this.nuevoDestinatarioPorCBU.getVisibility() == 0 ? "05" : "04";
        this.D = i();
        this.x = null;
        this.w = null;
        if (this.tabSelector.isEntrySetEmpty().booleanValue()) {
            try {
                this.L = this.E.filterTabNuevoDestinatarioCBUCVU(getString(R.string.ID_3813_TRANSFERENCE_BY_CBU_OR_ALIAS));
                this.M = this.E.applyFilterGeneral(getString(R.string.ID_3814_TRANSFERENCE_TBSR));
            } catch (Exception unused) {
                this.L = "";
                this.M = "";
            }
            this.tabSelector.setLayoutParams(new LayoutParams(-1, -1, 10.0f));
            b();
        }
        this.lnlSelectorCuenta.setOnClickListener(this);
        this.lnlSelectorTipoAlta.setOnClickListener(this);
        this.btnClearFields.setOnClickListener(this);
        this.btnVerificar.setOnClickListener(new OneClickListener(new OneClicked() {
            public void onClicked(View view) {
                NuevoDestinatarioTransferenciaActivity.this.onClick(view);
            }
        }));
        this.btnContinuarSinVerificar.setOnClickListener(this);
        a(this.D);
        this.inputTercerosBSR1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                NuevoDestinatarioTransferenciaActivity.this.a(NuevoDestinatarioTransferenciaActivity.this.btnVerificar, NuevoDestinatarioTransferenciaActivity.this.g());
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (NuevoDestinatarioTransferenciaActivity.this.inputTercerosBSR1.getText().toString().length() == 3) {
                    NuevoDestinatarioTransferenciaActivity.this.inputTercerosBSR1.clearFocus();
                    NuevoDestinatarioTransferenciaActivity.this.inputTercerosBSR2.requestFocus();
                    NuevoDestinatarioTransferenciaActivity.this.inputTercerosBSR2.setCursorVisible(true);
                }
            }
        });
        this.inputTercerosBSR2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                NuevoDestinatarioTransferenciaActivity.this.a(NuevoDestinatarioTransferenciaActivity.this.btnVerificar, NuevoDestinatarioTransferenciaActivity.this.g());
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (NuevoDestinatarioTransferenciaActivity.this.inputTercerosBSR2.getText().toString().length() == 6) {
                    NuevoDestinatarioTransferenciaActivity.this.inputTercerosBSR2.clearFocus();
                    NuevoDestinatarioTransferenciaActivity.this.inputTercerosBSR3.requestFocus();
                    NuevoDestinatarioTransferenciaActivity.this.inputTercerosBSR3.setCursorVisible(true);
                }
            }
        });
        this.inputTercerosBSR3.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                NuevoDestinatarioTransferenciaActivity.this.a(NuevoDestinatarioTransferenciaActivity.this.btnVerificar, NuevoDestinatarioTransferenciaActivity.this.g());
            }
        });
    }

    public void setEditarDestinatarioView(VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean, CtaMigradaBean ctaMigradaBean) {
        String str;
        String str2;
        String str3;
        String str4;
        VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean = verificaDatosInicialesTransfOBResponseBean.getVerificaDatosInicialesTransfOBBodyResponseBean();
        this.K = verificaDatosInicialesTransfOBResponseBean;
        this.editTextEmailVerificar.setText("");
        this.editTextRecordatorioVerificar.setText("");
        this.A = verificaDatosInicialesTransfOBBodyResponseBean.getTipoDestino();
        configureLayout();
        this.txtTitularVerificar.setText(Html.fromHtml(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getTitular()));
        if (this.D.equals("Alias")) {
            this.txtAliasVerificar.setText(this.cEditTextNuevoDest.getText().toString());
        }
        String obj = this.D.equals(TipoAlta.CBU) ? this.cEditTextNuevoDest.getText().toString() : verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getCbu();
        this.txtCBUVerificar.setText(obj);
        try {
            this.txtCBUVerificar.setContentDescription(this.E.applyFilterCharacterToCharacterSpecifict(this.txtCBUVerificar.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.A.equals("04")) {
            if (this.D.equals(TipoAlta.CBU) || this.D.equals("Alias")) {
                str4 = verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getTipo();
                str3 = verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getTipoDescripcion();
                str = UtilAccount.formatNumeroCuenta(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getNumero());
                str2 = UtilAccount.formatSucursalCuenta(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getSucursal());
            } else {
                str4 = this.B;
                str3 = this.C;
                if (ctaMigradaBean != null) {
                    str = UtilAccount.formatNumeroCuenta(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getNumero());
                    str2 = UtilAccount.formatSucursalCuenta(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getSucursal());
                } else {
                    str = UtilAccount.formatNumeroCuenta(this.inputTercerosBSR2.getText().toString().concat(this.inputTercerosBSR3.getText().toString()));
                    str2 = this.inputTercerosBSR1.getText().toString();
                }
            }
            this.w = new DatosCuentasDestBSRBean();
            if (this.D.equals("Alias")) {
                this.w.setAlias(this.cEditTextNuevoDest.getText().toString());
                this.txtCUITVerificar.setText(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getNumeroCuil());
                try {
                    this.txtCUITVerificar.setContentDescription(this.E.applyFilterCharacterToCharacter(this.txtCUITVerificar.getText().toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.w.setTipo(str4);
            this.w.setNumero(str);
            this.w.setSucursal(str2);
            this.w.setNombreTitular(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getTitular());
            this.w.setCbuDestino(obj);
            this.w.setTipoDestino(verificaDatosInicialesTransfOBBodyResponseBean.getTipoDestino());
            this.w.setTipoDescripcion(str3);
            this.lblCUITHeaderVerificar.setText(Html.fromHtml(str3));
            this.txtCUITHeaderVerificar.setText(UtilAccount.getAccountFormat(str2, str));
            this.txtBancoVerificar.setText(getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR));
            this.txtTipoCuentaVerificar.setText(Html.fromHtml(str3));
            this.txtNumeroCuentaVerificar.setText(UtilAccount.getAccountFormat(str2, str));
        } else if (this.A.equals("05")) {
            this.x = new DatosCuentasDestOBBean();
            this.x.setBeneficiario(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getNumeroCuil());
            this.x.setNombreTitular(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getTitular());
            this.x.setBancoDestino(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getBancoDestino());
            this.x.setCbu(obj);
            this.x.setDiferido(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getDiferido());
            this.x.setTipoDestino(verificaDatosInicialesTransfOBBodyResponseBean.getTipoDestino());
            if (this.D.equals("Alias")) {
                this.x.setAlias(this.cEditTextNuevoDest.getText().toString());
                this.txtCUITVerificar.setText(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getNumeroCuil());
            } else {
                this.txtCUITHeaderVerificar.setText(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getNumeroCuil());
            }
            this.txtCBUVerificar.setText(obj);
            this.txtBancoVerificar.setText(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getBancoDestino());
            this.txtTipoCuentaVerificar.setText(Html.fromHtml(UtilAccount.getOBAccountTypeDescription(getBaseContext(), verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getTipoCtaToBane())));
            this.txtNumeroCuentaVerificar.setText(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getNumero());
            try {
                this.txtCBUVerificar.setContentDescription(this.E.applyFilterCharacterToCharacter(this.txtCBUVerificar.getText().toString()));
                this.txtCUITHeaderVerificar.setContentDescription(this.E.applyFilterCharacterToCharacter(this.txtCUITHeaderVerificar.getText().toString()));
                this.txtCUITVerificar.setContentDescription(this.E.applyFilterCharacterToCharacter(this.txtCUITVerificar.getText().toString()));
                this.txtBancoVerificar.setContentDescription(this.E.applyFilterGeneral(this.txtBancoVerificar.getText().toString()));
                this.txtNumeroCuentaVerificar.setContentDescription(this.E.applyFilterCharacterToCharacter(this.txtNumeroCuentaVerificar.getText().toString()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        this.btnContinuarPantallaVerificar.setOnClickListener(this);
        if (this.z.equals(Origen.ORIGEN_NUEVO)) {
            if (this.D.equals(TipoAlta.CBU)) {
                this.q.trackScreen(getString(R.string.analytics_screen_name_verificar_nuevo_destinatario_cbu));
            } else if (this.D.equals(TipoAlta.CUENTABSR)) {
                this.q.trackScreen(getString(R.string.analytics_screen_name_verificar_nuevo_destinatario_tsr));
            }
        } else if (!this.z.equals(Origen.ORIGEN_PARA)) {
        } else {
            if (this.D.equals(TipoAlta.CBU)) {
                this.q.trackScreen(getString(R.string.analytics_screen_name_verificar_destinatario_para_cbu));
            } else if (this.D.equals(TipoAlta.CUENTABSR)) {
                this.q.trackScreen(getString(R.string.analytics_screen_name_verificar_destinatario_para_tsr));
            }
        }
    }

    public void setAltaManualDestinatarioView() {
        this.txtCBUAltaManual.setText(this.cEditTextNuevoDest.getText().toString());
        try {
            this.txtCBUAltaManual.setContentDescription(this.E.applyFilterCharacterToCharacter(this.txtCBUAltaManual.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        a(this.btnGuardarAltaManual, h());
        AnonymousClass5 r0 = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                NuevoDestinatarioTransferenciaActivity.this.a(NuevoDestinatarioTransferenciaActivity.this.btnGuardarAltaManual, NuevoDestinatarioTransferenciaActivity.this.h());
            }
        };
        this.editTextTitularAltaManual.addTextChangedListener(r0);
        this.editTextCUITAltaManual.addTextChangedListener(r0);
        this.editTextBancoAltaManual.addTextChangedListener(r0);
        this.btnGuardarAltaManual.setOnClickListener(this);
        this.q.trackScreen(getString(R.string.analytics_screen_name_nuevo_ingreso_manual_cbu));
        this.editTextTitularAltaManual.setText("");
        this.editTextCUITAltaManual.setText("");
        this.editTextBancoAltaManual.setText("");
        this.editTextRecordatorioAltaManual.setText("");
        this.editTextEmailAltaManual.setText("");
    }

    public void setConfirmarABMDestinatarioView(DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
        configureLayout();
        this.tableLayoutConfirmar.removeAllViews();
        CAccessibility cAccessibility = new CAccessibility(this);
        if (this.A.equals("05")) {
            String oBAccountTypeDescription = UtilAccount.getOBAccountTypeDescription(getBaseContext(), this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getTipoCtaToBane());
            String str = "";
            if (!TextUtils.isEmpty(this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getNumero())) {
                str = this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getNumero();
            }
            String str2 = str;
            try {
                String str3 = str2;
                String str4 = oBAccountTypeDescription;
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), datosCuentasDestOBBean.getNombreTitular(), datosCuentasDestOBBean.getNombreTitular()));
                if (this.D.equals("Alias")) {
                    this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), datosCuentasDestOBBean.getAlias(), datosCuentasDestOBBean.getAlias()));
                }
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL), getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL).replaceAll("/", UtilsCuentas.SEPARAOR2), datosCuentasDestOBBean.getBeneficiario(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestOBBean.getBeneficiario())));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), datosCuentasDestOBBean.getBancoDestino(), cAccessibility.applyFilterGeneral(datosCuentasDestOBBean.getBancoDestino())));
                if (!TextUtils.isEmpty(str4)) {
                    this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), str4, str4));
                }
                if (!TextUtils.isEmpty(str3)) {
                    this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT), cAccessibility.applyFilterGeneral(getString(R.string.ACCESSIBILITY_ALIAS_NRO_CUENTA)), str3, cAccessibility.applyFilterCharacterToCharacter(str3)));
                }
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), datosCuentasDestOBBean.getCbu(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestOBBean.getCbu())));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), Html.fromHtml(datosCuentasDestOBBean.getDescripcion()).toString(), datosCuentasDestOBBean.getDescripcion()));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL), cAccessibility.applyFilterGeneral(getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL)), datosCuentasDestOBBean.getEmail(), cAccessibility.applyFilterGeneral(datosCuentasDestOBBean.getEmail())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (this.A.equals("04")) {
            try {
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), datosCuentasDestBSRBean.getNombreTitular(), datosCuentasDestBSRBean.getNombreTitular()));
                String str5 = this.D;
                char c = 65535;
                int hashCode = str5.hashCode();
                if (hashCode != 66081) {
                    if (hashCode != 63350320) {
                        if (hashCode == 1302695049) {
                            if (str5.equals(TipoAlta.CBU)) {
                                c = 1;
                            }
                        }
                    } else if (str5.equals("Alias")) {
                        c = 0;
                    }
                } else if (str5.equals(TipoAlta.CUENTABSR)) {
                    c = 2;
                }
                switch (c) {
                    case 0:
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), datosCuentasDestBSRBean.getAlias(), datosCuentasDestBSRBean.getAlias()));
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL), getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL).replaceAll("/", UtilsCuentas.SEPARAOR2), this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getNumeroCuil(), cAccessibility.applyFilterCharacterToCharacter(this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getNumeroCuil())));
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR)));
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), Html.fromHtml(datosCuentasDestBSRBean.getTipoDescripcion()).toString(), datosCuentasDestBSRBean.getTipoDescripcion()));
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT), cAccessibility.applyFilterGeneral(getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT)), UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()), cAccessibility.applyFilterAccount(UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()))));
                        if (datosCuentasDestBSRBean.getCbuDestino() != null && !datosCuentasDestBSRBean.getCbuDestino().isEmpty()) {
                            this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), datosCuentasDestBSRBean.getCbuDestino(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestBSRBean.getCbuDestino())));
                            break;
                        }
                    case 1:
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR)));
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT), cAccessibility.applyFilterGeneral(getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT)), UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()), cAccessibility.applyFilterAccount(UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()))));
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), Html.fromHtml(datosCuentasDestBSRBean.getTipoDescripcion()).toString(), datosCuentasDestBSRBean.getTipoDescripcion()));
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), datosCuentasDestBSRBean.getCbuDestino(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestBSRBean.getCbuDestino())));
                        break;
                    case 2:
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), Html.fromHtml(datosCuentasDestBSRBean.getTipoDescripcion()).toString(), datosCuentasDestBSRBean.getTipoDescripcion()));
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT), cAccessibility.applyFilterGeneral(getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT)), UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()), cAccessibility.applyFilterAccount(UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()))));
                        this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR)));
                        if (datosCuentasDestBSRBean.getCbuDestino() != null && !datosCuentasDestBSRBean.getCbuDestino().isEmpty()) {
                            this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), datosCuentasDestBSRBean.getCbuDestino(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestBSRBean.getCbuDestino())));
                            break;
                        }
                }
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), Html.fromHtml(datosCuentasDestBSRBean.getDescripcion()).toString(), datosCuentasDestBSRBean.getDescripcion()));
                this.tableLayoutConfirmar.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_formulario, getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL), cAccessibility.applyFilterGeneral(getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL)), datosCuentasDestBSRBean.getEmail(), cAccessibility.applyFilterGeneral(datosCuentasDestBSRBean.getEmail())));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.btnGuardarConfirmar.setOnClickListener(this);
        if (this.A.equals(TipoAlta.CBU)) {
            this.q.trackScreen(getString(R.string.analytics_screen_name_confirmar_nuevo_destinatario_cbu));
        } else if (this.A.equals(TipoAlta.CUENTABSR)) {
            this.q.trackScreen(getString(R.string.analytics_screen_name_confirmar_nuevo_destinatario_tsr));
        }
    }

    public void setComprobanteABMDestinatarioView(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean, DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
        ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean2 = aBMDestinatarioTransfBodyResponseBean;
        configureLayout();
        this.inputNumeroComprobante = aBMDestinatarioTransfBodyResponseBean2.nroComprobante;
        this.tableLayoutConfirmar.removeAllViews();
        CAccessibility cAccessibility = new CAccessibility(this);
        try {
            if (this.A.equals("05")) {
                String oBAccountTypeDescription = UtilAccount.getOBAccountTypeDescription(getBaseContext(), this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getTipoCtaToBane());
                String str = "";
                if (!TextUtils.isEmpty(this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getNumero())) {
                    str = this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getNumero();
                }
                String str2 = str;
                String str3 = oBAccountTypeDescription;
                String str4 = str2;
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), datosCuentasDestOBBean.getNombreTitular(), datosCuentasDestOBBean.getNombreTitular()));
                if (this.D.equals("Alias")) {
                    this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), datosCuentasDestOBBean.getAlias(), datosCuentasDestOBBean.getAlias()));
                }
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL), getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL).replaceAll("/", UtilsCuentas.SEPARAOR2), datosCuentasDestOBBean.getBeneficiario(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestOBBean.getBeneficiario())));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), datosCuentasDestOBBean.getBancoDestino(), cAccessibility.applyFilterGeneral(datosCuentasDestOBBean.getBancoDestino())));
                if (!TextUtils.isEmpty(str3)) {
                    this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), Html.fromHtml(str3).toString(), str3));
                }
                if (!TextUtils.isEmpty(str4)) {
                    this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT), cAccessibility.applyFilterGeneral(getString(R.string.ACCESSIBILITY_ALIAS_NRO_CUENTA)), str4, cAccessibility.applyFilterCharacterToCharacter(str4)));
                }
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), datosCuentasDestOBBean.getCbu(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestOBBean.getCbu())));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), Html.fromHtml(datosCuentasDestOBBean.getDescripcion()).toString(), datosCuentasDestOBBean.getDescripcion()));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL), cAccessibility.applyFilterGeneral(getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL)), datosCuentasDestOBBean.getEmail(), cAccessibility.applyFilterGeneral(datosCuentasDestOBBean.getEmail())));
            } else if (this.A.equals("04")) {
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), getString(R.string.ID_447_TRANSFERENCE_MYACCOUNTS_TITLE), datosCuentasDestBSRBean.getNombreTitular(), datosCuentasDestBSRBean.getNombreTitular()));
                String str5 = this.D;
                char c = 65535;
                int hashCode = str5.hashCode();
                if (hashCode != 66081) {
                    if (hashCode != 63350320) {
                        if (hashCode == 1302695049) {
                            if (str5.equals(TipoAlta.CBU)) {
                                c = 1;
                            }
                        }
                    } else if (str5.equals("Alias")) {
                        c = 0;
                    }
                } else if (str5.equals(TipoAlta.CUENTABSR)) {
                    c = 2;
                }
                switch (c) {
                    case 0:
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), getString(R.string.ID_3821_TRANSFERENCE_LBL_ALIAS), datosCuentasDestBSRBean.getAlias(), datosCuentasDestBSRBean.getAlias()));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL), getString(R.string.ID_3819_TRANSFERENCE_LBL_CUIT_CUIL).replaceAll("/", UtilsCuentas.SEPARAOR2), this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getNumeroCuil(), cAccessibility.applyFilterCharacterToCharacter(this.K.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getNumeroCuil())));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR)));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), Html.fromHtml(datosCuentasDestBSRBean.getTipoDescripcion()).toString(), datosCuentasDestBSRBean.getTipoDescripcion()));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT), cAccessibility.applyFilterGeneral(getString(R.string.ACCESSIBILITY_ALIAS_NRO_CUENTA)), UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()), cAccessibility.applyFilterAccount(UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()))));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), datosCuentasDestBSRBean.getCbuDestino(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestBSRBean.getCbuDestino())));
                        break;
                    case 1:
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR)));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT), cAccessibility.applyFilterGeneral(getString(R.string.ACCESSIBILITY_ALIAS_NRO_CUENTA)), UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()), cAccessibility.applyFilterAccount(UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()))));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), Html.fromHtml(datosCuentasDestBSRBean.getTipoDescripcion()).toString(), datosCuentasDestBSRBean.getTipoDescripcion()));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), datosCuentasDestBSRBean.getCbuDestino(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestBSRBean.getCbuDestino())));
                        break;
                    case 2:
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), getString(R.string.ID448_TRANSFERENCE_MYACCOUNTS_TYPE), Html.fromHtml(datosCuentasDestBSRBean.getTipoDescripcion()).toString(), datosCuentasDestBSRBean.getTipoDescripcion()));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3834_TRANSFERENCE_LBL_DESTINATION_ACCOUNT), cAccessibility.applyFilterGeneral(getString(R.string.ACCESSIBILITY_ALIAS_NRO_CUENTA)), UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()), cAccessibility.applyFilterAccount(UtilAccount.getAccountFormat(datosCuentasDestBSRBean.getSucursal(), datosCuentasDestBSRBean.getNumero()))));
                        this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.ID_3820_TRANSFERENCE_LBL_BANK), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR)));
                        if (datosCuentasDestBSRBean.getCbuDestino() != null && !datosCuentasDestBSRBean.getCbuDestino().isEmpty()) {
                            this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU), cAccessibility.filterCBUCVU(getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU)), datosCuentasDestBSRBean.getCbuDestino(), cAccessibility.applyFilterCharacterToCharacter(datosCuentasDestBSRBean.getCbuDestino())));
                            break;
                        }
                }
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_RECORDATORIO), Html.fromHtml(datosCuentasDestBSRBean.getDescripcion()).toString(), datosCuentasDestBSRBean.getDescripcion()));
                this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL), cAccessibility.applyFilterGeneral(getString(R.string.ID_3822_TRANSFERENCE_LBL_EMAIL)), datosCuentasDestBSRBean.getEmail(), cAccessibility.applyFilterGeneral(datosCuentasDestBSRBean.getEmail())));
            }
            this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_489_TRANSFERENCE_PROOF_DONE_LBL_SELECT), cAccessibility.applyFilterGeneral(getString(R.string.ACCESSIBILITY_ALIAS_NRO_OPERACION)), this.inputNumeroComprobante, cAccessibility.applyFilterCharacterToCharacter(this.inputNumeroComprobante)));
            String dateFormat2 = UtilDate.getDateFormat2(aBMDestinatarioTransfBodyResponseBean2.fechaOperacion);
            this.tableLayoutComprobante.addView(TableViewRowCreator.createRowLabelValue(this, R.layout.row_tableview_comprobante, getString(R.string.ID_458_TRANSFERENCE_MAIN_EXEDATE), getString(R.string.ID_458_TRANSFERENCE_MAIN_EXEDATE), dateFormat2, cAccessibility.applyFilterDate(dateFormat2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.btnTransferir.setOnClickListener(this);
        this.btnAgenda.setOnClickListener(this);
        if (this.D.equals(TipoAlta.CBU)) {
            this.q.trackScreen(getString(R.string.analytics_screen_name_comprobante_nuevo_destinatario_cbu));
        }
    }

    public void configureActionBar() {
        int displayedChild = this.mControlPager.getDisplayedChild();
        if (displayedChild == 0) {
            setActionBarType(ActionBarType.BACK);
            lockMenu(true);
            enableBackButton();
        } else if (displayedChild == 4) {
            setActionBarType(ActionBarType.MAIN_WITH_MENU);
            lockMenu(false);
            enableMenuButton();
            enableShareButton();
        }
    }

    public void enableBackButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NuevoDestinatarioTransferenciaActivity.this.onBackPressed();
                }
            });
        }
    }

    public void enableMenuButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NuevoDestinatarioTransferenciaActivity.this.switchDrawer();
                }
            });
        }
    }

    public void enableShareButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.menu);
        this.F = f();
        e();
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NuevoDestinatarioTransferenciaActivity.this.J.show(NuevoDestinatarioTransferenciaActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    public void showDialogConfirmarAlta() {
        this.J = IsbanDialogFragment.newInstance("popUpConfirmacionAlta", getString(R.string.ID_3812_TRANSFERENCE_LBL_NEW_RECIPIENT), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_CONFIRM_BODY), getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO));
        this.J.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                NuevoDestinatarioTransferenciaActivity.this.u.onConfirmar(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_NUEVO, "A", NuevoDestinatarioTransferenciaActivity.this.A.equals("05") ? CuentaBanco.OTROS : CuentaBanco.BANCO_SANTANDER_RIO, null, NuevoDestinatarioTransferenciaActivity.this.w, NuevoDestinatarioTransferenciaActivity.this.x, Boolean.valueOf(false));
            }

            public void onNegativeButton() {
                NuevoDestinatarioTransferenciaActivity.this.J.closeDialog();
            }
        });
        this.J.show(getSupportFragmentManager(), "popupConfirmation");
    }

    private void c() {
        final ListTableBean consDescripcionTPOCTALARGA = CConsDescripciones.getConsDescripcionTPOCTALARGA(this.sessionManager);
        ArrayList arrayList = new ArrayList();
        if (consDescripcionTPOCTALARGA != null) {
            for (ListGroupBean listGroupBean : consDescripcionTPOCTALARGA.listGroupBeans) {
                if (!listGroupBean.code.equals("09") && !listGroupBean.code.equals("10")) {
                    arrayList.add(listGroupBean.getLabel());
                }
            }
        }
        this.J = IsbanDialogFragment.newInstance(getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSEE_ACCOUNT_TYPE), null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, this.C);
        this.J.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                NuevoDestinatarioTransferenciaActivity.this.C = str;
                NuevoDestinatarioTransferenciaActivity.this.txtTipoCuenta.setText(NuevoDestinatarioTransferenciaActivity.this.C);
                if (consDescripcionTPOCTALARGA != null) {
                    for (ListGroupBean listGroupBean : consDescripcionTPOCTALARGA.listGroupBeans) {
                        if (listGroupBean.getLabel().equals(str)) {
                            NuevoDestinatarioTransferenciaActivity.this.B = listGroupBean.code;
                        }
                    }
                }
                NuevoDestinatarioTransferenciaActivity.this.a(NuevoDestinatarioTransferenciaActivity.this.btnVerificar, NuevoDestinatarioTransferenciaActivity.this.g());
            }

            public void onSimpleActionButton() {
                NuevoDestinatarioTransferenciaActivity.this.J.dismiss();
            }
        });
        this.J.show(getSupportFragmentManager(), "popupSelectorTipoCuenta");
    }

    private void d() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.add(TipoAlta.CBU);
        arrayList.add("Alias");
        int i = 0;
        while (i < arrayList.size()) {
            try {
                arrayList2.add(new CAccessibility(getActivity()).filterCBUCVU((String) arrayList.get(i)));
                i++;
            } catch (Exception unused) {
            }
        }
        this.J = IsbanDialogFragment.newInstance("popUpTipoAlta", null, null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, this.D, arrayList2);
        this.J.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                NuevoDestinatarioTransferenciaActivity.this.D = str;
                NuevoDestinatarioTransferenciaActivity.this.lblSelectorTipoAlta.setText(str);
                if (NuevoDestinatarioTransferenciaActivity.this.lblSelectorTipoAlta.getText().toString().equals(NuevoDestinatarioTransferenciaActivity.this.getString(R.string.ID456_TRANSFERENCE_OTHER_CBU_CVU))) {
                    NuevoDestinatarioTransferenciaActivity.this.lblSelectorTipoAlta.setContentDescription(NuevoDestinatarioTransferenciaActivity.this.E.filterCBUCVU(NuevoDestinatarioTransferenciaActivity.this.lblSelectorTipoAlta.getText().toString()));
                } else {
                    NuevoDestinatarioTransferenciaActivity.this.lblSelectorTipoAlta.setContentDescription(NuevoDestinatarioTransferenciaActivity.this.E.filterCBUCVU(NuevoDestinatarioTransferenciaActivity.this.lblSelectorTipoAlta.getText().toString()));
                }
                NuevoDestinatarioTransferenciaActivity.this.a(str);
                if (NuevoDestinatarioTransferenciaActivity.this.btnContinuarSinVerificar.getVisibility() == 0) {
                    NuevoDestinatarioTransferenciaActivity.this.textViewAlertInvalidCBU.setVisibility(8);
                    NuevoDestinatarioTransferenciaActivity.this.btnContinuarSinVerificar.setVisibility(8);
                }
            }

            public void onSimpleActionButton() {
                NuevoDestinatarioTransferenciaActivity.this.J.dismiss();
            }
        });
        this.J.show(getSupportFragmentManager(), "popUpTipoAlta");
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        final int i;
        this.cEditTextNuevoDest.setText("");
        int i2 = 22;
        if (str.equals(TipoAlta.CBU)) {
            this.cEditTextNuevoDest.setInputType(2);
            EditText editText = this.cEditTextNuevoDest;
            StringBuilder sb = new StringBuilder();
            sb.append("<small>");
            sb.append(getString(R.string.ID_3838_TRANSFERENCE_LBL_CBU_ENTER));
            sb.append("</small>");
            editText.setHint(Html.fromHtml(sb.toString()));
            this.cEditTextNuevoDest.setContentDescription(this.E.filterCBUCVU(this.cEditTextNuevoDest.getHint().toString()));
            this.lblInputHint.setText(getString(R.string.ID_3816_TRANSFERENCE_LBL_CBU_MASK));
            this.lblInputHint.setContentDescription(this.E.filterCBUCVU(this.lblInputHint.getText().toString()));
            this.q.trackScreen(getString(R.string.analytics_screen_name_nuevo_destinatario_cbu));
            i = 22;
        } else {
            i2 = 20;
            i = 6;
            this.cEditTextNuevoDest.setInputType(1);
            this.cEditTextNuevoDest.setHint(getString(R.string.ID_XXXX_TRANSFERENCE_LBL_ALIAS_ENTER));
            try {
                this.cEditTextNuevoDest.setContentDescription(this.E.applyFilterGeneral(this.cEditTextNuevoDest.getHint().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.lblInputHint.setText(getString(R.string.ID_XXXX_TRANSFERENCE_LBL_ALIAS_MASK));
        }
        this.cEditTextNuevoDest.setFilters(new InputFilter[]{new LengthFilter(i2)});
        this.cEditTextNuevoDest.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                NuevoDestinatarioTransferenciaActivity.this.cEditTextNuevoDest.setContentDescription(NuevoDestinatarioTransferenciaActivity.this.E.filterCBUCVU(NuevoDestinatarioTransferenciaActivity.this.cEditTextNuevoDest.getHint().toString()));
            }

            public void afterTextChanged(Editable editable) {
                NuevoDestinatarioTransferenciaActivity.this.a(NuevoDestinatarioTransferenciaActivity.this.btnVerificar, Boolean.valueOf(NuevoDestinatarioTransferenciaActivity.this.cEditTextNuevoDest.getText().toString().length() >= i));
                if (NuevoDestinatarioTransferenciaActivity.this.btnContinuarSinVerificar.getVisibility() == 0) {
                    NuevoDestinatarioTransferenciaActivity.this.btnContinuarSinVerificar.setBackground(NuevoDestinatarioTransferenciaActivity.this.getResources().getDrawable(R.drawable.boton_redondeado_blanco_borde_gris));
                    NuevoDestinatarioTransferenciaActivity.this.btnContinuarSinVerificar.setTextColor(NuevoDestinatarioTransferenciaActivity.this.getResources().getColor(R.color.grey_medium_light));
                    NuevoDestinatarioTransferenciaActivity.this.btnContinuarSinVerificar.setEnabled(false);
                }
            }
        });
    }

    private void e() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.J = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.J.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(NuevoDestinatarioTransferenciaActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    NuevoDestinatarioTransferenciaActivity.this.F.optionShareSelected();
                } else if (str.equalsIgnoreCase(NuevoDestinatarioTransferenciaActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    NuevoDestinatarioTransferenciaActivity.this.F.optionDownloadSelected();
                }
            }
        });
        this.J.setCancelable(true);
    }

    private OptionsToShare f() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public String getSubjectReceiptToShare() {
                return "Comprobante de Alta";
            }

            public View getViewToShare() {
                return NuevoDestinatarioTransferenciaActivity.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                NuevoDestinatarioTransferenciaActivity.this.startActivityForResult(Intent.createChooser(intent, NuevoDestinatarioTransferenciaActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
            }

            public String getFileName() {
                return "ABM Destinatario Transferencia - ".concat(NuevoDestinatarioTransferenciaActivity.this.inputNumeroComprobante);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                NuevoDestinatarioTransferenciaActivity.this.G = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                NuevoDestinatarioTransferenciaActivity.this.G = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                NuevoDestinatarioTransferenciaActivity.this.G = true;
            }
        };
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.F.onRequestPermissionsResult(i, strArr, iArr);
    }

    public boolean canExit(int i) {
        if (!this.G) {
            final int i2 = i;
            AnonymousClass15 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public String getFileName() {
                    return "comprobantealta";
                }

                public String getSubjectReceiptToShare() {
                    return "Comprobante de Alta";
                }

                public View getViewToShare() {
                    return NuevoDestinatarioTransferenciaActivity.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    NuevoDestinatarioTransferenciaActivity.this.startActivityForResult(Intent.createChooser(intent, NuevoDestinatarioTransferenciaActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    NuevoDestinatarioTransferenciaActivity.this.G = true;
                    if (NuevoDestinatarioTransferenciaActivity.this.H) {
                        NuevoDestinatarioTransferenciaActivity.this.btnTransferir.callOnClick();
                    } else {
                        NuevoDestinatarioTransferenciaActivity.this.btnAgenda.callOnClick();
                    }
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    NuevoDestinatarioTransferenciaActivity.this.G = true;
                    if (NuevoDestinatarioTransferenciaActivity.this.H) {
                        NuevoDestinatarioTransferenciaActivity.this.btnTransferir.callOnClick();
                    } else {
                        NuevoDestinatarioTransferenciaActivity.this.btnAgenda.callOnClick();
                    }
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    NuevoDestinatarioTransferenciaActivity.this.G = true;
                    NuevoDestinatarioTransferenciaActivity.this.onClickItem(i2);
                    if (NuevoDestinatarioTransferenciaActivity.this.H) {
                        NuevoDestinatarioTransferenciaActivity.this.btnTransferir.callOnClick();
                    } else {
                        NuevoDestinatarioTransferenciaActivity.this.btnAgenda.callOnClick();
                    }
                }
            };
            r1.showAlert();
        }
        return this.G;
    }

    /* access modifiers changed from: private */
    public Boolean g() {
        boolean z2 = true;
        if (this.inputTercerosBSR1.getText().toString().length() != 3 || this.inputTercerosBSR2.getText().toString().length() < 5 || this.inputTercerosBSR3.getText().toString().length() != 1 || this.B == null) {
            z2 = false;
        }
        return Boolean.valueOf(z2);
    }

    /* access modifiers changed from: private */
    public Boolean h() {
        return Boolean.valueOf(this.editTextCUITAltaManual.getText().toString().length() == 11 && this.editTextTitularAltaManual.getText().toString().length() > 0 && this.editTextBancoAltaManual.getText().toString().length() > 0);
    }

    /* access modifiers changed from: private */
    public void a(Button button, Boolean bool) {
        if (bool.booleanValue()) {
            button.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        } else {
            button.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris));
        }
        button.setEnabled(bool.booleanValue());
    }

    public void onErrorVerificacionCBU() {
        a(this.btnVerificar, Boolean.valueOf(false));
        if (this.nuevoDestinatarioPorCBU.getVisibility() == 0) {
            this.textViewAlertInvalidCBU.setVisibility(0);
            this.btnContinuarSinVerificar.setVisibility(0);
            this.btnContinuarSinVerificar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_blanco_borde_rojo));
            this.btnContinuarSinVerificar.setTextColor(getResources().getColor(R.color.red_light));
            this.btnContinuarSinVerificar.setEnabled(true);
            this.q.trackScreen(getString(R.string.analytics_screen_name_nuevo_habilitacion_btn_continuar_validaok));
        }
    }

    public void onInvalidInputError(String str) {
        this.J = IsbanDialogFragment.newInstance("popUpInvalidInput", getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT));
        this.J.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                NuevoDestinatarioTransferenciaActivity.this.J.dismiss();
            }
        });
        this.J.show(getSupportFragmentManager(), "popUpInvalidInput");
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

    public void OnNewItemSelected(ToggleItem toggleItem) {
        if (toggleItem.getLabel().equalsIgnoreCase(getString(R.string.ID_3813_TRANSFERENCE_BY_CBU_OR_ALIAS))) {
            this.A = "05";
            this.nuevoDestinatarioPorCBU.setVisibility(0);
            this.nuevoDestinatarioPorCuentaBSR.setVisibility(8);
            clearScreenData();
        } else if (toggleItem.getLabel().equalsIgnoreCase(getString(R.string.ID_3814_TRANSFERENCE_TBSR))) {
            this.A = "04";
            this.btnContinuarSinVerificar.setVisibility(8);
            this.textViewAlertInvalidCBU.setVisibility(8);
            this.nuevoDestinatarioPorCBU.setVisibility(8);
            this.nuevoDestinatarioPorCuentaBSR.setVisibility(0);
            this.q.trackScreen(getString(R.string.analytics_screen_name_nuevo_destinatario_tsr));
            clearScreenData();
        }
    }

    public void configureLayout() {
        int displayedChild = this.mControlPager.getDisplayedChild();
        if (displayedChild == 1) {
            this.txtTitleVerificar.setText(getString(R.string.ID_3818_TRANSFERENCE_LBL_VERIFY_RECIPIENT));
            if (this.D.equals("Alias")) {
                this.lblAliasVerificar.setVisibility(0);
                this.txtAliasVerificar.setVisibility(0);
                this.lblCUITHeaderVerificar.setVisibility(8);
                this.txtCUITHeaderVerificar.setVisibility(8);
                this.rowCUITVerificar.setVisibility(0);
            } else {
                this.lblAliasVerificar.setVisibility(8);
                this.txtAliasVerificar.setVisibility(8);
                this.lblCUITHeaderVerificar.setVisibility(0);
                this.txtCUITHeaderVerificar.setVisibility(0);
                this.rowCUITVerificar.setVisibility(8);
            }
            if (this.D.equals(TipoAlta.CUENTABSR)) {
                this.rowCBUVerificar.setVisibility(8);
                this.rowTipoCuentaVerificar.setVisibility(8);
                this.rowNumeroCuentaVerificar.setVisibility(8);
                return;
            }
            this.rowCBUVerificar.setVisibility(0);
        } else if (displayedChild == 3) {
            this.txtTitleConfirmar.setText(getString(R.string.ID_3825_TRANSFERENCE_LBL_CONFIRM_NEW_RECIPIENT));
            this.btnGuardarConfirmar.setText(getString(R.string.ID_3823_TRANSFERENCE_SAVE_RECIPIENT));
        }
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.r.isViewAttached()) {
                    this.r.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.t.isViewAttached()) {
                    this.t.attachView(this);
                    return;
                }
                return;
            case 2:
                if (!this.s.isViewAttached()) {
                    this.s.attachView(this);
                    return;
                }
                return;
            case 3:
                if (!this.u.isViewAttached()) {
                    this.u.attachView(this);
                    return;
                }
                return;
            case 4:
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
        if (this.r.isViewAttached()) {
            this.r.detachView();
        }
        if (this.t.isViewAttached()) {
            this.t.detachView();
        }
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
        if (this.u.isViewAttached()) {
            this.u.detachView();
        }
        if (this.v.isViewAttached()) {
            this.v.detachView();
        }
    }

    public void finishConfirmacionABM(Integer num, Boolean bool, VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean) {
        Intent intent = new Intent();
        if (verificaDatosInicialesTransfOBResponseBean != null) {
            intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO, generarDestinatarioForIntent());
            if (this.A.equals("05")) {
                intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_OB_BEAN, this.x);
            } else {
                intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_BSR_BEAN, this.w);
            }
            intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_EJECUTAR_ALTA, bool);
            if (this.K != null) {
                intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_VERIFICA_DATOS_TRANSF, verificaDatosInicialesTransfOBResponseBean.getVerificaDatosInicialesTransfOBBodyResponseBean());
            }
        }
        setResult(num.intValue(), intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
        if (this.D.equals(TipoAlta.CUENTABSR) && num.intValue() == 1) {
            this.q.trackScreen(getString(R.string.analytics_screen_name_nuevo_destinatario_alerta_verificacion));
        }
    }

    public void finishConfirmacionABM(Integer num) {
        setResult(num.intValue(), new Intent());
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }

    private String i() {
        if (this.nuevoDestinatarioPorCBU.getVisibility() != 0) {
            return TipoAlta.CUENTABSR;
        }
        String charSequence = this.lblSelectorTipoAlta.getText().toString();
        this.lblSelectorTipoAlta.setContentDescription(this.E.filterCBUCVU(this.lblSelectorTipoAlta.getText().toString()));
        return charSequence;
    }

    private void j() {
        String tipo = ((Cuenta) this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas().get(0)).getTipo();
        if (tipo.equals("02")) {
            tipo = "09";
        }
        this.r.onVerificar(this.A, tipo, UtilAccount.formatSucursalCuenta(((Cuenta) this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas().get(0)).getNroSuc()), UtilAccount.formatNumeroCuenta(((Cuenta) this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas().get(0)).getNumero()), this.D.equals(TipoAlta.CBU) ? this.cEditTextNuevoDest.getText().toString() : null, this.D.equals("Alias") ? this.cEditTextNuevoDest.getText().toString() : null, Boolean.valueOf(true));
    }

    private void k() {
        this.x = new DatosCuentasDestOBBean();
        this.x.setBeneficiario(this.editTextCUITAltaManual.getText().toString());
        if (!this.editTextRecordatorioAltaManual.getText().toString().isEmpty()) {
            this.x.setDescripcion(this.editTextRecordatorioAltaManual.getText().toString());
        } else {
            this.x.setDescripcion(this.editTextTitularAltaManual.getText().toString());
        }
        this.x.setNombreTitular(this.editTextTitularAltaManual.getText().toString());
        this.x.setBancoDestino(this.editTextBancoAltaManual.getText().toString());
        this.x.setEmail(this.editTextEmailAltaManual.getText().toString());
        this.x.setCbu(this.cEditTextNuevoDest.getText().toString());
        this.x.setDiferido("1");
        this.x.setTipoDestino("03");
        VerificaDatosSalidaOBBean verificaDatosSalidaOBBean = new VerificaDatosSalidaOBBean();
        verificaDatosSalidaOBBean.setNumeroCuil(this.x.getBeneficiario());
        verificaDatosSalidaOBBean.setTitular(this.x.getNombreTitular());
        verificaDatosSalidaOBBean.setBancoDestino(this.x.getBancoDestino());
        verificaDatosSalidaOBBean.setCbu(this.x.getCbu());
        verificaDatosSalidaOBBean.setDiferido(this.x.getDiferido());
        verificaDatosSalidaOBBean.setTipoCtaToBane("");
        verificaDatosSalidaOBBean.setTipoCtaFromBane("");
        verificaDatosSalidaOBBean.setBancoReceptor("");
        verificaDatosSalidaOBBean.setFiid("");
        verificaDatosSalidaOBBean.setUser("");
        this.K = new VerificaDatosInicialesTransfOBResponseBean(new VerificaDatosInicialesTransfOBBodyResponseBean(verificaDatosSalidaOBBean));
        this.K.verificaDatosInicialesTransfOBBodyResponseBean.setTipoDestino(this.x.getTipoDestino());
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

    private String a(DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
        return CAccounts.getInstance(this.sessionManager).getAbrevAccount(new Cuenta(datosCuentasDestBSRBean.getTipo(), datosCuentasDestBSRBean.getNumero(), datosCuentasDestBSRBean.getSucursal()));
    }

    public AgendaDestinatarios generarDestinatarioForIntent() {
        if (this.A.equals("05")) {
            AgendaDestinatarios agendaDestinatarios = new AgendaDestinatarios(this.x.getNombreTitular(), "", TransferenciasConstants.cBANCO_OB, this.x.getCbu(), Boolean.valueOf(false), "", this.x.getEmail(), this.A, this.x.getDescripcion(), this.x.getBancoDestino(), this.x.getBeneficiario(), this.x.getAlias());
            return agendaDestinatarios;
        } else if (!this.A.equals("04")) {
            return null;
        } else {
            AgendaDestinatarios agendaDestinatarios2 = new AgendaDestinatarios(this.w.getNombreTitular(), "", TransferenciasConstants.cBANCO_SR_TERCEROS, a(this.w), Boolean.valueOf(false), this.w.getTipoDescripcion(), this.w.getEmail(), this.A, this.w.getDescripcion(), getString(R.string.IDXX_TRANSFERENCE_ABM_ADDRESSE_BANK_BSR), "", this.w.getAlias());
            return agendaDestinatarios2;
        }
    }
}
