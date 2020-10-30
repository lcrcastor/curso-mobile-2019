package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.ConsultaLeyendasGenerales;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.LoginInfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SuscripcionActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SecuredPreferences;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CheckVersionEvent;
import ar.com.santander.rio.mbanking.services.events.ConsDescripcionesEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaLeyendasPrimerIngresoEvent;
import ar.com.santander.rio.mbanking.services.events.LoginEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsDescriptionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaLeyendasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaLeyendasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VLoginUnico;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.view.EditTextValidator;
import ar.com.santander.rio.mbanking.view.EditTextValidator.ListenerValidator;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.scottyab.rootbeer.RootBeer;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class LoginFragment extends BaseFragment implements OnClickListener, IDialogListener, ListenerValidator {
    public static String FECHA_SINONIMO_REMEMBER = "FECHA_SINONIMO_REMEMBER";
    public static String NUM_DOCUMENTO_ALTA_USUARIO_REMEMBER = "DOCUMENTO_ALTA_USUARIO_REMEMBER";
    public static String NUM_DOCUMENTO_PRIMER_INGRESO_REMEMBER = "DOCUMENTO_PRIMER_INGRESO_REMEMBER";
    public static String NUM_DOCUMENTO_REMEMBER = "NUM_DOCUMENTO_REMEMBER";
    public static String NUM_DOCUMENTO_SINONIMO_REMEMBER = "DOCUMENTO_SINONIMO_REMEMBER";
    @Inject
    IDataManager a;
    /* access modifiers changed from: private */
    public String ad = "";
    private String ae = "";
    private String af = "";
    private ILoginListener ag;
    private TextWatcher ah;
    /* access modifiers changed from: private */
    public boolean ai = false;
    private List<LoginEvent> aj = new ArrayList();
    private Activity ak;
    private Context al;
    private SecuredPreferences am;
    private boolean an = false;
    @InjectView(2131365175)
    EditTextValidator antiguaClaveLoginClaveVencida;
    @InjectView(2131365172)
    EditTextValidator antiguoUsuarioLoginCambioUsuario;
    @InjectView(2131365176)
    EditTextValidator antiguoUsuarioLoginClaveVencida;
    @Inject
    AnalyticsManager b;
    @Inject
    SessionManager c;
    @InjectView(2131365181)
    EditTextValidator claveAntiguaLoginPrimerIngreso;
    @InjectView(2131365168)
    EditTextValidator claveLoginAltaUsuario;
    @InjectView(2131365189)
    EditTextValidator claveLoginSinonimo;
    @InjectView(2131365193)
    EditTextValidator claveLoginSinonimoCA;
    @InjectView(2131365198)
    EditTextValidator claveLoginTradicional;
    private LoginType d;
    @InjectView(2131365169)
    EditTextValidator documentoLoginAltaUsuario;
    @InjectView(2131365186)
    EditTextValidator documentoLoginPrimerIngreso;
    @InjectView(2131365191)
    EditTextValidator documentoLoginSinonimo;
    @InjectView(2131365195)
    EditTextValidator documentoLoginSinonimoCA;
    @InjectView(2131365199)
    EditTextValidator documentoLoginTradicional;
    private String e = "";
    /* access modifiers changed from: private */
    public boolean f;
    @InjectView(2131365190)
    EditTextValidator fechaLoginSinonimo;
    @InjectView(2131365194)
    EditTextValidator fechaLoginSinonimoCA;
    @InjectView(2131365166)
    EditTextValidator fechaNacimientoLoginAltaUsuario;
    @InjectView(2131365167)
    View fechaNacimientoLoginAltaUsuarioSeparator;
    @InjectView(2131365182)
    EditTextValidator fechaNacimientoLoginPrimerIngreso;
    @InjectView(2131365183)
    View fechaNacimientoLoginPrimerIngresoSeparator;
    private boolean g;
    @InjectView(2131365171)
    EditTextValidator generaUsuarioLoginAltaUsuario;
    @InjectView(2131365184)
    EditTextValidator generaUsuarioLoginPrimerIngreso;
    private boolean h = false;
    private String i = "";
    @InjectView(2131365159)
    View loginAltaUsuario;
    @InjectView(2131365160)
    View loginCambioUsuario;
    @InjectView(2131365161)
    View loginClaveVencida;
    @InjectView(2131365201)
    Button loginIngresar;
    @InjectView(2131365202)
    Button loginIngresarNoTradicional;
    @InjectView(2131365162)
    View loginPrimerIngreso;
    @InjectView(2131365156)
    View loginPrimerIngresoTextAction;
    @InjectView(2131365164)
    View loginSinonimo;
    @InjectView(2131365165)
    View loginSinonimoCallToAction;
    @InjectView(2131365205)
    TextView loginTextAction;
    @InjectView(2131365206)
    TextView loginTextActionNoTradicional;
    @InjectView(2131365163)
    View loginTradicional;
    @InjectView(2131366376)
    public ViewFlipper mControlPager;
    @InjectView(2131365177)
    EditTextValidator nuevaClaveLoginClaveVencida;
    @InjectView(2131365185)
    EditTextValidator nuevaClaveLoginPrimerIngreso;
    @InjectView(2131365173)
    EditTextValidator nuevoUsuarioLoginCambioUsuario;
    @InjectView(2131365178)
    EditTextValidator nuevoUsuarioLoginClaveVencida;
    @InjectView(2131365498)
    LinearLayout recuerdaDocumentoLayout;
    @InjectView(2131365499)
    LinearLayout recuerdaDocumentoLayoutNoTradicional;
    @InjectView(2131365170)
    EditTextValidator regeneraUsuarioLoginAltaUsuario;
    @InjectView(2131365179)
    EditTextValidator reingresaClaveLoginClaveVencida;
    @InjectView(2131365187)
    EditTextValidator reingresaClaveLoginPrimerIngreso;
    @InjectView(2131365174)
    EditTextValidator reingresaUsuarioLoginCambioUsuario;
    @InjectView(2131365180)
    EditTextValidator reingresaUsuarioLoginClaveVencida;
    @InjectView(2131365188)
    EditTextValidator reingresaUsuarioLoginPrimerIngreso;
    @InjectView(2131365196)
    EditTextValidator reusuarioLoginSinonimoCA;
    @InjectView(2131366060)
    ToggleButton toggleRecuerdaDocumento;
    @InjectView(2131366061)
    ToggleButton toggleRecuerdaDocumentoNoTradicional;
    @InjectView(2131364730)
    ConstraintLayout topView;
    @InjectView(2131364729)
    ConstraintLayout topViewNoTradicional;
    @InjectView(2131365192)
    EditTextValidator usuarioLoginSinonimo;
    @InjectView(2131365197)
    EditTextValidator usuarioLoginSinonimoCA;
    @InjectView(2131365200)
    EditTextValidator usuarioLoginTradicional;
    @InjectView(2131366410)
    ImageView viewAyudaAltaUsuario;
    @InjectView(2131366411)
    ImageView viewAyudaCambioUsuario;
    @InjectView(2131366412)
    ImageView viewAyudaClaveVencida;
    @InjectView(2131366413)
    ImageView viewAyudaPrimerIngreso;
    @InjectView(2131366414)
    ImageView viewAyudaSinonimo;
    @InjectView(2131366415)
    ImageView viewAyudaSinonimoCA;
    @InjectView(2131366416)
    ImageView viewAyudaTradicional;

    public interface ILoginListener {
        void onLoginOk();
    }

    public enum LoginType {
        TRADICIONAL,
        SINONIMO,
        CLAVE_VENCIDA,
        PRIMER_INGRESO,
        ALTA_USUARIO,
        CAMBIO_USUARIO
    }

    public void onItemSelected(String str) {
    }

    public void onNegativeButton() {
    }

    public void onPositiveButton() {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.al = context;
        this.ak = getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.login_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.am = SecuredPreferences.getInstance(getActivity());
        LayoutParams layoutParams = (LayoutParams) this.topView.getLayoutParams();
        layoutParams.height = z();
        this.topView.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = (LayoutParams) this.topViewNoTradicional.getLayoutParams();
        layoutParams2.height = z();
        this.topViewNoTradicional.setLayoutParams(layoutParams2);
        y();
        this.loginIngresar.setOnClickListener(this);
        this.loginIngresarNoTradicional.setOnClickListener(this);
        this.loginPrimerIngresoTextAction.setOnClickListener(this);
        this.loginTextAction.setOnClickListener(this);
        this.loginTextActionNoTradicional.setOnClickListener(this);
        a(false);
        setListenerValidators();
        y();
        this.recuerdaDocumentoLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!LoginFragment.this.isHasAction()) {
                    LoginFragment.this.toggleRecuerdaDocumento.toggle();
                    if (LoginFragment.this.toggleRecuerdaDocumentoNoTradicional.isChecked() != LoginFragment.this.toggleRecuerdaDocumento.isChecked()) {
                        LoginFragment.this.toggleRecuerdaDocumentoNoTradicional.toggle();
                    }
                    if (LoginFragment.this.toggleRecuerdaDocumento.isChecked()) {
                        LoginFragment.this.b.trackEvent(LoginFragment.this.getString(R.string.analytics_category_login), LoginFragment.this.getString(R.string.analytics_action_login_recordar_documento), LoginFragment.this.getString(R.string.analytics_label_login_actualizacion_remember_me));
                    }
                }
            }
        });
        this.recuerdaDocumentoLayoutNoTradicional.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!LoginFragment.this.isHasAction()) {
                    LoginFragment.this.toggleRecuerdaDocumentoNoTradicional.toggle();
                    if (LoginFragment.this.toggleRecuerdaDocumentoNoTradicional.isChecked() != LoginFragment.this.toggleRecuerdaDocumento.isChecked()) {
                        LoginFragment.this.toggleRecuerdaDocumento.toggle();
                    }
                    if (LoginFragment.this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
                        LoginFragment.this.b.trackEvent(LoginFragment.this.getString(R.string.analytics_category_login), LoginFragment.this.getString(R.string.analytics_action_login_recordar_documento), LoginFragment.this.getString(R.string.analytics_label_login_actualizacion_remember_me));
                    }
                }
            }
        });
        this.toggleRecuerdaDocumento.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!LoginFragment.this.isHasAction()) {
                    if (LoginFragment.this.toggleRecuerdaDocumentoNoTradicional.isChecked() != LoginFragment.this.toggleRecuerdaDocumento.isChecked()) {
                        LoginFragment.this.toggleRecuerdaDocumentoNoTradicional.toggle();
                    }
                    if (LoginFragment.this.toggleRecuerdaDocumento.isChecked()) {
                        LoginFragment.this.b.trackEvent(LoginFragment.this.getString(R.string.analytics_category_login), LoginFragment.this.getString(R.string.analytics_action_login_recordar_documento), LoginFragment.this.getString(R.string.analytics_label_login_actualizacion_remember_me));
                    }
                }
            }
        });
        this.toggleRecuerdaDocumentoNoTradicional.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!LoginFragment.this.isHasAction()) {
                    if (LoginFragment.this.toggleRecuerdaDocumentoNoTradicional.isChecked() != LoginFragment.this.toggleRecuerdaDocumento.isChecked()) {
                        LoginFragment.this.toggleRecuerdaDocumento.toggle();
                    }
                    if (LoginFragment.this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
                        LoginFragment.this.b.trackEvent(LoginFragment.this.getString(R.string.analytics_category_login), LoginFragment.this.getString(R.string.analytics_action_login_recordar_documento), LoginFragment.this.getString(R.string.analytics_label_login_actualizacion_remember_me));
                    }
                }
            }
        });
        this.b.trackScreen(getString(R.string.analytics_screen_name_login_general));
        return inflate;
    }

    private void a(boolean z) {
        this.f = U();
        a(this.f ? LoginType.SINONIMO : LoginType.TRADICIONAL, z);
    }

    private void y() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        ActionBar supportActionBar = ((SantanderRioMainActivity) getActivity()).getSupportActionBar();
        supportActionBar.show();
        ImageView imageView = (ImageView) supportActionBar.getCustomView().findViewById(R.id.toggle);
        imageView.setOnClickListener(new OneClickListener(new OneClicked() {
            public void onClicked(View view) {
                if ((LoginFragment.this.loginTradicional.isShown() || LoginFragment.this.loginSinonimo.isShown()) && LoginFragment.this.f) {
                    LoginFragment.this.getActivity().onBackPressed();
                }
                if (LoginFragment.this.getActivity().getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    ((SantanderRioMainActivity) LoginFragment.this.getActivity()).backLastFragment();
                } else {
                    LoginFragment.this.getActivity().onBackPressed();
                }
            }
        }));
        supportActionBar.getCustomView().findViewById(R.id.ok).setVisibility(8);
        imageView.requestFocus();
        imageView.setFocusable(true);
        imageView.requestFocus();
        imageView.sendAccessibilityEvent(32768);
    }

    private int z() {
        Display defaultDisplay = this.ak.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return (point.y * 24) / 100;
    }

    private void A() {
        String str;
        showProgress("");
        C();
        FragmentActivity activity = getActivity();
        String str2 = "";
        try {
            str = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            e2.fillInStackTrace();
            String str3 = NumerosUtilesFragment.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("PackageManager.NameNotFoundException: ");
            sb.append(e2);
            Log.e(str3, sb.toString());
            str = str2;
        }
        String str4 = !new RootBeer(activity).isRootedWithoutBusyBoxCheck() ? "888" : "";
        if (!this.c.getCheckVersion()) {
            this.a.checkVersion(Constants.SPLASH_ID_RUNTIME, str, str4, getActivity(), false);
        }
        this.a.consDescripciones(false, true);
    }

    @Subscribe
    public void onCheckVersion(CheckVersionEvent checkVersionEvent) {
        final FragmentActivity activity = getActivity();
        this.c.setCheckVersionEvent(checkVersionEvent);
        if (activity != null) {
            CheckVersionManager checkVersionManager = new CheckVersionManager(this.c, (SantanderRioMainActivity) getActivity());
            final String result = checkVersionManager.getResult(checkVersionEvent);
            if (checkVersionManager.isUpdateAvailable()) {
                B();
                final CheckVersionManager checkVersionManager2 = checkVersionManager;
                final CheckVersionEvent checkVersionEvent2 = checkVersionEvent;
                AnonymousClass6 r0 = new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onSimpleActionButton() {
                    }

                    public void onPositiveButton() {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(checkVersionManager2.getUpdateBean(checkVersionEvent2).getUrl()));
                        intent.setFlags(268435456);
                        activity.startActivity(intent);
                        activity.setResult(-1);
                        activity.finish();
                    }

                    public void onNegativeButton() {
                        if (result.equals("3")) {
                            activity.setResult(-1);
                            activity.finish();
                        } else if (result.equals("2")) {
                            LoginFragment.this.c.setCheckVersionWarningRaised(Boolean.valueOf(true));
                        }
                    }
                };
                checkVersionManager.check(checkVersionEvent, r0);
                return;
            }
            switch (checkVersionEvent.getResult()) {
                case OK:
                    if (this.c.getConsDescripciones() != null) {
                        a(this.d);
                    }
                    return;
                case SERVER_ERROR:
                    if (this.an) {
                        B();
                        b(activity.getString(R.string.USER200047_error_conexion));
                        break;
                    }
                    break;
                case BEAN_ERROR_RES_1:
                    B();
                    checkVersionManager.check(checkVersionEvent, new IDialogListener() {
                        public void onItemSelected(String str) {
                        }

                        public void onNegativeButton() {
                        }

                        public void onPositiveButton() {
                        }

                        public void onSimpleActionButton() {
                        }
                    });
                    break;
            }
            this.an = false;
        }
    }

    private void B() {
        dismissProgress();
        this.loginIngresar.setClickable(true);
        this.loginIngresarNoTradicional.setClickable(true);
    }

    private void C() {
        this.loginIngresar.setClickable(false);
        this.loginIngresarNoTradicional.setClickable(false);
    }

    public void onClick(View view) {
        if (!isHasAction()) {
            switch (view.getId()) {
                case R.id.lnl_loginPrimerIngresoTextAction /*2131365156*/:
                    this.ad = this.documentoLoginTradicional.getText().toString();
                    a(LoginType.ALTA_USUARIO, false);
                    this.h = true;
                    break;
                case R.id.loginIngresar /*2131365201*/:
                case R.id.loginIngresarNoTracional /*2131365202*/:
                    this.an = true;
                    CheckVersionEvent checkVersionEvent = this.c.getCheckVersionEvent();
                    if (J() && (checkVersionEvent == null || this.c.getConsDescripciones() == null || checkVersionEvent.getResult() == TypeResult.SERVER_ERROR)) {
                        if (this.loginIngresar.isClickable()) {
                            A();
                            break;
                        }
                    } else {
                        a(this.d);
                        break;
                    }
                    break;
                case R.id.loginTextAction /*2131365205*/:
                case R.id.loginTextActionNoTradicional /*2131365206*/:
                    Intent intent = new Intent(getActivity(), InfoActivity.class);
                    String charSequence = ((TextView) view).getText().toString();
                    if (!charSequence.equalsIgnoreCase(getResources().getString(R.string.ID31_LOGIN_BTN_FORGOTTENPASSWD))) {
                        if (charSequence.equalsIgnoreCase(getResources().getString(R.string.ID45_LOGIN_EXPIRED_BTN_HELP))) {
                            intent.putExtra(InfoActivity.TYPE_INFO, InfoType.AYUDA);
                            intent.putExtra(InfoActivity.TEXT_TO_SHOW, getResources().getString(R.string.LOGIN_AYUDA_CLAVE_VENCIDA));
                            getActivity().startActivity(intent);
                            break;
                        }
                    } else {
                        showProgress(VLoginUnico.nameService);
                        this.b.trackScreen(getString(R.string.analytics_screen_name_login_olvido_clave));
                        this.a.consultaLeyendasGeneralesPrimerIngreso(ConsultaLeyendasGenerales.getOlvidasteTuclave());
                        break;
                    }
                    break;
            }
        }
    }

    private void a(LoginType loginType) {
        this.an = false;
        if (loginType == LoginType.TRADICIONAL) {
            K();
        } else if (loginType == LoginType.SINONIMO) {
            if (this.h) {
                I();
            } else {
                H();
            }
        } else if (loginType == LoginType.CLAVE_VENCIDA) {
            F();
        } else if (loginType == LoginType.PRIMER_INGRESO) {
            E();
        } else if (loginType == LoginType.ALTA_USUARIO) {
            D();
        } else if (loginType == LoginType.CAMBIO_USUARIO) {
            G();
        }
    }

    private void D() {
        if (!this.documentoLoginAltaUsuario.validate() || !this.claveLoginAltaUsuario.validate() || !this.generaUsuarioLoginAltaUsuario.validate() || !this.regeneraUsuarioLoginAltaUsuario.validate() || !this.generaUsuarioLoginAltaUsuario.getText().toString().equalsIgnoreCase(this.regeneraUsuarioLoginAltaUsuario.getText().toString())) {
            O();
        } else if (this.c.getConsDescripciones() != null) {
            this.ad = this.documentoLoginAltaUsuario.getText().toString();
            d(this.fechaNacimientoLoginAltaUsuario.getText().toString());
            showProgress(VLoginUnico.nameService);
            hasActionEnable();
            this.a.loginUnicoAltaUsuario(this.documentoLoginAltaUsuario.getText().toString(), this.claveLoginAltaUsuario.getText().toString(), this.generaUsuarioLoginAltaUsuario.getText().toString(), this.regeneraUsuarioLoginAltaUsuario.getText().toString(), N(), getActivity());
        }
    }

    @Subscribe
    public void onConsDescripciones(ConsDescripcionesEvent consDescripcionesEvent) {
        hasActionDisable();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            if (consDescripcionesEvent.getBeanResponse() != null) {
                this.c.setConsDescripciones(((ConsDescriptionResponseBean) consDescripcionesEvent.getBeanResponse()).consDescriptionBodyResponseBean);
            }
            CheckVersionEvent checkVersionEvent = this.c.getCheckVersionEvent();
            CheckVersionManager checkVersionManager = new CheckVersionManager(this.c, (SantanderRioMainActivity) getActivity());
            switch (consDescripcionesEvent.getResult()) {
                case OK:
                    if (this.c.getCheckVersion() && !checkVersionManager.isUpdateAvailable() && this.an) {
                        a(this.d);
                        break;
                    }
                case SERVER_ERROR:
                    if (checkVersionEvent != null && checkVersionEvent.getResult() == TypeResult.OK && this.an) {
                        B();
                        b(activity.getString(R.string.USER200047_error_conexion));
                        break;
                    }
            }
            this.an = false;
        }
    }

    private void E() {
        if (!this.documentoLoginPrimerIngreso.validate() || !this.nuevaClaveLoginPrimerIngreso.validate() || !this.reingresaClaveLoginPrimerIngreso.validate() || !this.generaUsuarioLoginPrimerIngreso.validate() || !this.reingresaUsuarioLoginPrimerIngreso.validate() || !this.claveAntiguaLoginPrimerIngreso.validate() || !this.nuevaClaveLoginPrimerIngreso.getText().toString().equalsIgnoreCase(this.reingresaClaveLoginPrimerIngreso.getText().toString()) || !this.generaUsuarioLoginPrimerIngreso.getText().toString().equalsIgnoreCase(this.reingresaUsuarioLoginPrimerIngreso.getText().toString())) {
            O();
        } else if (this.c.getConsDescripciones() != null) {
            this.ad = this.documentoLoginPrimerIngreso.getText().toString();
            d(this.fechaNacimientoLoginPrimerIngreso.getText().toString());
            showProgress(VLoginUnico.nameService);
            hasActionEnable();
            this.a.loginUnicoPrimerIngreso(this.documentoLoginPrimerIngreso.getText().toString(), this.claveAntiguaLoginPrimerIngreso.getText().toString(), this.nuevaClaveLoginPrimerIngreso.getText().toString(), this.generaUsuarioLoginPrimerIngreso.getText().toString(), this.reingresaUsuarioLoginPrimerIngreso.getText().toString(), N(), getActivity());
        }
    }

    private void F() {
        if (!this.antiguaClaveLoginClaveVencida.validate() || !this.nuevaClaveLoginClaveVencida.validate() || !this.reingresaClaveLoginClaveVencida.validate() || !this.antiguoUsuarioLoginClaveVencida.validate() || !this.nuevoUsuarioLoginClaveVencida.validate() || !this.reingresaUsuarioLoginClaveVencida.validate() || !this.nuevaClaveLoginClaveVencida.getText().toString().equalsIgnoreCase(this.reingresaClaveLoginClaveVencida.getText().toString()) || !this.nuevoUsuarioLoginClaveVencida.getText().toString().equalsIgnoreCase(this.reingresaUsuarioLoginClaveVencida.getText().toString())) {
            O();
        } else if (this.c.getConsDescripciones() != null) {
            showProgress(VLoginUnico.nameService);
            hasActionEnable();
            this.a.loginUnicoClaveVencida(this.ae, this.antiguaClaveLoginClaveVencida.getText().toString(), this.nuevaClaveLoginClaveVencida.getText().toString(), this.reingresaClaveLoginClaveVencida.getText().toString(), this.antiguoUsuarioLoginClaveVencida.getText().toString(), this.nuevoUsuarioLoginClaveVencida.getText().toString(), this.reingresaUsuarioLoginClaveVencida.getText().toString(), N(), getActivity());
        }
    }

    private void G() {
        if (!this.antiguoUsuarioLoginCambioUsuario.validate() || !this.nuevoUsuarioLoginCambioUsuario.validate() || !this.reingresaUsuarioLoginCambioUsuario.validate() || !this.nuevoUsuarioLoginCambioUsuario.getText().toString().equalsIgnoreCase(this.reingresaUsuarioLoginCambioUsuario.getText().toString())) {
            O();
        } else if (this.c.getConsDescripciones() != null) {
            showProgress(VLoginUnico.nameService);
            hasActionEnable();
            this.a.loginUnicoCambioUsuario(this.ae, this.af, this.antiguoUsuarioLoginCambioUsuario.getText().toString(), this.nuevoUsuarioLoginCambioUsuario.getText().toString(), this.reingresaUsuarioLoginCambioUsuario.getText().toString(), N(), getActivity());
        }
    }

    private void H() {
        if (!this.documentoLoginSinonimo.validate() || !this.fechaLoginSinonimo.validate() || !this.claveLoginSinonimo.validate() || !this.usuarioLoginSinonimo.validate()) {
            O();
        } else if (this.c.getConsDescripciones() != null) {
            this.ad = this.documentoLoginSinonimo.getText().toString();
            this.ae = this.ad;
            this.af = this.claveLoginSinonimo.getText().toString();
            showProgress(VLoginUnico.nameService);
            d(this.fechaLoginSinonimo.getText().toString());
            hasActionEnable();
            this.a.loginUnicoSinonimo(this.documentoLoginSinonimo.getText().toString(), this.fechaLoginSinonimo.getText().toString(), this.claveLoginSinonimo.getText().toString(), this.usuarioLoginSinonimo.getText().toString(), getActivity());
        }
    }

    private void I() {
        if (!this.documentoLoginSinonimoCA.validate() || !this.fechaLoginSinonimoCA.validate() || !this.claveLoginSinonimoCA.validate() || !this.usuarioLoginSinonimoCA.validate() || !this.reusuarioLoginSinonimoCA.validate() || !this.usuarioLoginSinonimoCA.getText().toString().equalsIgnoreCase(this.reusuarioLoginSinonimoCA.getText().toString())) {
            O();
        } else if (this.c.getConsDescripciones() != null) {
            this.ad = this.documentoLoginSinonimoCA.getText().toString();
            this.ae = this.ad;
            showProgress(VLoginUnico.nameService);
            d(this.fechaLoginSinonimoCA.getText().toString());
            hasActionEnable();
            this.a.loginUnicoSinonimoCallToAction(this.documentoLoginSinonimoCA.getText().toString(), this.fechaLoginSinonimoCA.getText().toString(), this.claveLoginSinonimoCA.getText().toString(), this.usuarioLoginSinonimoCA.getText().toString(), getActivity());
        }
    }

    private boolean J() {
        return this.documentoLoginTradicional.validate() && this.claveLoginTradicional.validate() && this.usuarioLoginTradicional.validate();
    }

    private void K() {
        if (!J()) {
            O();
        } else if (this.c.getConsDescripciones() != null) {
            this.ad = this.documentoLoginTradicional.getText().toString();
            this.ae = this.ad;
            this.af = this.claveLoginTradicional.getText().toString();
            hasActionEnable();
            this.a.logOutSession();
            showProgress(VLoginUnico.nameService);
            hasActionEnable();
            this.a.loginUnicoTradicional(this.documentoLoginTradicional.getText().toString(), this.claveLoginTradicional.getText().toString(), this.usuarioLoginTradicional.getText().toString(), N(), getActivity());
        }
    }

    public void onStart() {
        P();
        super.onStart();
    }

    private void b(String str) {
        IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), Utils.formatIsbanHTMLCode(str), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null).show(getActivity().getSupportFragmentManager(), "Dialog");
    }

    /* access modifiers changed from: private */
    public void a(LoginType loginType, boolean z) {
        P();
        this.g = false;
        this.d = loginType;
        if (loginType == LoginType.TRADICIONAL) {
            if (S()) {
                this.documentoLoginTradicional.setText(b(loginType));
            }
            this.viewAyudaTradicional.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!LoginFragment.this.isHasAction()) {
                        LoginFragment.this.infoChecked();
                    }
                }
            });
            this.h = false;
            goToTradicionalLogin(z);
            this.f = false;
            this.loginTradicional.setVisibility(0);
            this.loginTextAction.setText(getResources().getString(R.string.ID31_LOGIN_BTN_FORGOTTENPASSWD));
            TextView textView = this.loginTextAction;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.ACCESSIBILITY_LOGIN_VINCULO));
            sb.append(getResources().getString(R.string.ID31_LOGIN_BTN_FORGOTTENPASSWD));
            textView.setContentDescription(sb.toString());
            this.loginIngresar.setText(getResources().getString(R.string.ID32_LOGIN_BTN_ACCESS));
            this.recuerdaDocumentoLayout.setVisibility(0);
        } else if (loginType == LoginType.SINONIMO) {
            goToNonTradicionalLogin(z);
            this.loginAltaUsuario.setVisibility(8);
            this.loginClaveVencida.setVisibility(8);
            this.loginCambioUsuario.setVisibility(8);
            this.loginPrimerIngreso.setVisibility(8);
            this.viewAyudaSinonimo.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!LoginFragment.this.isHasAction()) {
                        LoginFragment.this.infoChecked();
                    }
                }
            });
            this.viewAyudaSinonimoCA.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!LoginFragment.this.isHasAction()) {
                        LoginFragment.this.infoChecked();
                    }
                }
            });
            if (this.h) {
                if (this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
                    this.documentoLoginSinonimoCA.setText(R());
                    this.fechaLoginSinonimoCA.setText(V());
                    a(this.documentoLoginSinonimoCA);
                } else {
                    this.documentoLoginSinonimoCA.setText("");
                    this.fechaLoginSinonimoCA.setText("");
                }
                this.b.trackScreen(getString(R.string.analytics_screen_name_login_usuario_sinonimo_primer_ingreso));
                this.loginTextActionNoTradicional.setText("");
                this.loginSinonimo.setVisibility(8);
                this.loginSinonimoCallToAction.setVisibility(0);
                this.loginIngresarNoTradicional.setText(getResources().getString(R.string.ID47_LOGIN_EXPIRED_BTN_ACCEPT));
            } else {
                if (S()) {
                    this.documentoLoginSinonimo.setText(b(loginType));
                    this.fechaLoginSinonimo.setText(V());
                    a(this.documentoLoginSinonimo);
                } else {
                    this.documentoLoginSinonimo.setText("");
                    this.fechaLoginSinonimo.setText("");
                }
                this.b.trackScreen(getString(R.string.analytics_screen_name_login_usuario_sinonimo));
                this.loginSinonimo.setVisibility(0);
                this.loginSinonimoCallToAction.setVisibility(8);
                this.loginIngresarNoTradicional.setText(getResources().getString(R.string.ID32_LOGIN_BTN_ACCESS));
            }
            this.recuerdaDocumentoLayoutNoTradicional.setVisibility(0);
        } else if (loginType == LoginType.CLAVE_VENCIDA) {
            goToNonTradicionalLogin(z);
            this.viewAyudaClaveVencida.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!LoginFragment.this.isHasAction()) {
                        LoginFragment.this.infoChecked();
                    }
                }
            });
            this.b.trackScreen(getString(R.string.analytics_screen_name_login_clave_vencida));
            this.loginSinonimo.setVisibility(8);
            this.loginSinonimoCallToAction.setVisibility(8);
            this.loginAltaUsuario.setVisibility(8);
            this.loginClaveVencida.setVisibility(0);
            this.loginCambioUsuario.setVisibility(8);
            this.loginPrimerIngreso.setVisibility(8);
            this.loginTextActionNoTradicional.setText(getResources().getString(R.string.ID45_LOGIN_EXPIRED_BTN_HELP));
            TextView textView2 = this.loginTextActionNoTradicional;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getResources().getString(R.string.ACCESSIBILITY_LOGIN_VINCULO));
            sb2.append(getResources().getString(R.string.ID45_LOGIN_EXPIRED_BTN_HELP));
            textView2.setContentDescription(sb2.toString());
            this.loginTextActionNoTradicional.setVisibility(0);
            this.loginIngresarNoTradicional.setText(getResources().getString(R.string.ID47_LOGIN_EXPIRED_BTN_ACCEPT));
            this.recuerdaDocumentoLayoutNoTradicional.setVisibility(4);
        } else if (loginType == LoginType.PRIMER_INGRESO) {
            goToNonTradicionalLogin(z);
            this.viewAyudaPrimerIngreso.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!LoginFragment.this.isHasAction()) {
                        LoginFragment.this.infoChecked();
                    }
                }
            });
            if (this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
                this.documentoLoginPrimerIngreso.setText(R());
            }
            if (this.f) {
                this.b.trackScreen(getString(R.string.analytics_screen_name_login_usuario_sinonimo_primer_ingreso_clave_vencida));
                if (this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
                    this.fechaNacimientoLoginPrimerIngreso.setText(this.i);
                } else {
                    this.fechaNacimientoLoginPrimerIngreso.setText("");
                }
                this.fechaNacimientoLoginPrimerIngreso.setVisibility(0);
                this.fechaNacimientoLoginPrimerIngresoSeparator.setVisibility(0);
            } else {
                this.b.trackScreen(getString(R.string.analytics_screen_name_login_primer_ingreso));
                this.fechaNacimientoLoginPrimerIngreso.setVisibility(8);
                this.fechaNacimientoLoginPrimerIngresoSeparator.setVisibility(8);
            }
            this.loginSinonimo.setVisibility(8);
            this.loginSinonimoCallToAction.setVisibility(8);
            this.loginAltaUsuario.setVisibility(8);
            this.loginClaveVencida.setVisibility(8);
            this.loginCambioUsuario.setVisibility(8);
            this.loginPrimerIngreso.setVisibility(0);
            this.loginTextActionNoTradicional.setVisibility(8);
            this.loginIngresarNoTradicional.setText(getResources().getString(R.string.ID39bis_LOGIN_1ACCESS_BTN_ACCESS));
            this.recuerdaDocumentoLayoutNoTradicional.setVisibility(0);
        } else if (loginType == LoginType.ALTA_USUARIO) {
            goToNonTradicionalLogin(z);
            if (!z) {
                this.g = true;
            }
            this.viewAyudaAltaUsuario.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!LoginFragment.this.isHasAction()) {
                        LoginFragment.this.infoChecked();
                    }
                }
            });
            this.b.trackScreen(getString(R.string.analytics_screen_name_login_primer_ingreso_clave_vencida));
            if (this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
                this.documentoLoginAltaUsuario.setText(R());
            }
            if (this.f) {
                if (this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
                    this.fechaNacimientoLoginAltaUsuario.setText(this.i);
                } else {
                    this.fechaNacimientoLoginAltaUsuario.setText("");
                }
                this.fechaNacimientoLoginAltaUsuario.setVisibility(0);
                this.fechaNacimientoLoginAltaUsuarioSeparator.setVisibility(0);
            } else {
                this.fechaNacimientoLoginAltaUsuario.setVisibility(8);
                this.fechaNacimientoLoginAltaUsuarioSeparator.setVisibility(8);
            }
            this.loginSinonimo.setVisibility(8);
            this.loginSinonimoCallToAction.setVisibility(8);
            this.loginAltaUsuario.setVisibility(0);
            this.loginClaveVencida.setVisibility(8);
            this.loginCambioUsuario.setVisibility(8);
            this.loginPrimerIngreso.setVisibility(8);
            this.loginTextActionNoTradicional.setText("");
            this.loginIngresarNoTradicional.setText(getResources().getString(R.string.ID32_LOGIN_BTN_ACCESS));
            this.recuerdaDocumentoLayoutNoTradicional.setVisibility(0);
        } else if (loginType == LoginType.CAMBIO_USUARIO) {
            goToNonTradicionalLogin(z);
            this.viewAyudaCambioUsuario.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!LoginFragment.this.isHasAction()) {
                        LoginFragment.this.infoChecked();
                    }
                }
            });
            this.b.trackScreen(getString(R.string.analytics_screen_name_login_cambio_usuario));
            this.loginSinonimo.setVisibility(8);
            this.loginSinonimoCallToAction.setVisibility(8);
            this.loginAltaUsuario.setVisibility(8);
            this.loginClaveVencida.setVisibility(8);
            this.loginCambioUsuario.setVisibility(0);
            this.loginPrimerIngreso.setVisibility(8);
            this.loginTextActionNoTradicional.setVisibility(8);
            this.loginIngresarNoTradicional.setText(getResources().getString(R.string.ID47_LOGIN_EXPIRED_BTN_ACCEPT));
            this.recuerdaDocumentoLayoutNoTradicional.setVisibility(4);
        }
    }

    private void a(final EditTextValidator editTextValidator) {
        this.ah = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (!LoginFragment.this.ad.equals(editable)) {
                    editTextValidator.removeTextChangedListener(this);
                    LoginFragment.this.f = false;
                    LoginFragment.this.e("");
                    LoginFragment.this.e(false);
                    LoginFragment.this.P();
                    LoginFragment.this.documentoLoginTradicional.setText(editable);
                    LoginFragment.this.onBackPressed();
                }
            }
        };
        editTextValidator.addTextChangedListener(this.ah);
    }

    private void L() {
        this.documentoLoginSinonimoCA.removeTextChangedListener(this.ah);
        this.documentoLoginSinonimo.removeTextChangedListener(this.ah);
    }

    @Subscribe
    public void logado(final LoginEvent loginEvent) {
        hasActionDisable();
        B();
        P();
        if (loginEvent.getResult() == TypeResult.OK) {
            L();
            a(((LoginUnicoResponseBean) loginEvent.getBeanResponse()).getLoginUnicoBodyResponseBean());
            return;
        }
        if (getErrorListener() != null) {
            if (loginEvent.getResult() == TypeResult.BEAN_ERROR_RES_1 || loginEvent.getResult() == TypeResult.BEAN_ERROR_RES_2) {
                final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(loginEvent.getTitleToShow(), loginEvent.getMessageToShow(), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                newInstance.show(getActivity().getSupportFragmentManager(), "DialogNewVersion");
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
            } else if (loginEvent.getResult() == TypeResult.BEAN_ERROR_RES_6) {
                this.g = false;
                IsbanDialogFragment newInstance2 = IsbanDialogFragment.newInstance(loginEvent.getTitleToShow(), loginEvent.getMessageToShow(), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                newInstance2.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onNegativeButton() {
                    }

                    public void onPositiveButton() {
                    }

                    public void onSimpleActionButton() {
                        LoginFragment.this.a(LoginType.TRADICIONAL, true);
                    }
                });
                newInstance2.show(getActivity().getSupportFragmentManager(), "DialogNewVersion");
            } else if (loginEvent.getResult() == TypeResult.BEAN_WARNING) {
                this.ai = true;
                IsbanDialogFragment newInstance3 = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), Utils.formatIsbanHTMLCode(Html.fromHtml(loginEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                newInstance3.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onNegativeButton() {
                    }

                    public void onPositiveButton() {
                    }

                    public void onSimpleActionButton() {
                        LoginFragment.this.ai = false;
                        loginEvent.setResult(TypeResult.OK);
                        LoginFragment.this.a.loginUnicoResendEvent(loginEvent);
                    }
                });
                newInstance3.show(getFragmentManager(), "DialogNewVersion");
            } else {
                getErrorListener().onWebServiceErrorEvent(loginEvent, getTAG());
            }
        }
        if (this.g) {
            a(LoginType.TRADICIONAL, true);
        }
    }

    private void a(LoginUnicoBodyResponseBean loginUnicoBodyResponseBean) {
        e(this.ad);
        if (loginUnicoBodyResponseBean.getDestinosMyA() != null) {
            this.e = loginUnicoBodyResponseBean.getDestinosMyA().getEstadoSuscripcion();
        }
        if (loginUnicoBodyResponseBean.getEstado().equalsIgnoreCase(LoginUnicoBodyResponseBean.ESTADO_SI)) {
            this.f = true;
            a(LoginType.SINONIMO, true);
        } else if (loginUnicoBodyResponseBean.getEstado().equalsIgnoreCase(LoginUnicoBodyResponseBean.ESTADO_OK)) {
            if (loginUnicoBodyResponseBean.getDatosPersonales().getAceptaTyC().equalsIgnoreCase(LoginConstants.NO_ACEPTA_TYC)) {
                M();
            } else if (this.e.equalsIgnoreCase(LoginConstants.SUSC_NO_SUSC) || this.e.equalsIgnoreCase(LoginConstants.SUSC_ALTA_CEL) || this.e.equalsIgnoreCase(LoginConstants.SUSC_ALTA_MAIL)) {
                c(this.e);
            }
        } else if (loginUnicoBodyResponseBean.getEstado().equalsIgnoreCase(LoginUnicoBodyResponseBean.ESTADO_AU)) {
            a(LoginType.ALTA_USUARIO, true);
        } else if (loginUnicoBodyResponseBean.getEstado().equalsIgnoreCase(LoginUnicoBodyResponseBean.ESTADO_PI)) {
            a(LoginType.PRIMER_INGRESO, true);
        } else if (loginUnicoBodyResponseBean.getEstado().equalsIgnoreCase(LoginUnicoBodyResponseBean.ESTADO_CV)) {
            this.b.trackScreen(getString(R.string.analytics_screen_name_login_aviso_clave_vencida));
            a(LoginType.CLAVE_VENCIDA, true);
        } else if (loginUnicoBodyResponseBean.getEstado().equalsIgnoreCase(LoginUnicoBodyResponseBean.ESTADO_CU)) {
            a(LoginType.CAMBIO_USUARIO, true);
        }
    }

    private void M() {
        showProgress(VLoginUnico.nameService);
        this.a.consultaLeyendasGeneralesPrimerIngreso(ConsultaLeyendasGenerales.getTerminosCondiciones());
        this.b.trackScreen(getString(R.string.analytics_screen_name_login_terminos_y_condiciones));
    }

    @Subscribe
    public void onConsultaLeyenda(ConsultaLeyendasPrimerIngresoEvent consultaLeyendasPrimerIngresoEvent) {
        hasActionDisable();
        ConsultaLeyendasResponseBean consultaLeyendasResponseBean = (ConsultaLeyendasResponseBean) consultaLeyendasPrimerIngresoEvent.getBeanResponse();
        if (consultaLeyendasResponseBean != null && consultaLeyendasResponseBean.getConsultaLeyendasBodyResponseBean() != null) {
            dismissProgress();
            if (consultaLeyendasPrimerIngresoEvent.getResult() == TypeResult.OK) {
                ConsultaLeyendasResponseBean consultaLeyendasResponseBean2 = (ConsultaLeyendasResponseBean) consultaLeyendasPrimerIngresoEvent.getBeanResponse();
                if (consultaLeyendasResponseBean2 != null) {
                    ConsultaLeyendasBodyResponseBean consultaLeyendasBodyResponseBean = consultaLeyendasResponseBean2.getConsultaLeyendasBodyResponseBean();
                    if (consultaLeyendasBodyResponseBean != null) {
                        String formatIsbanHTMLCode = Utils.formatIsbanHTMLCode(consultaLeyendasBodyResponseBean.leyenda.getDescripcion());
                        Intent intent = new Intent(getActivity(), InfoActivity.class);
                        if (consultaLeyendasBodyResponseBean.leyenda.getIdLeyenda().equalsIgnoreCase(ConsultaLeyendasGenerales.getOlvidasteTuclave())) {
                            intent.putExtra(InfoActivity.TYPE_INFO, InfoType.OLVIDASTE_TU_CLAVE_2);
                            intent.putExtra(InfoActivity.TITLE_TO_SHOW, getResources().getString(R.string.RECUPERAR_PASSWORD_TITLE));
                        } else if (consultaLeyendasBodyResponseBean.leyenda.getIdLeyenda().equalsIgnoreCase(ConsultaLeyendasGenerales.getTerminosCondiciones())) {
                            intent.putExtra(InfoActivity.TYPE_INFO, InfoType.CONDICIONES);
                        }
                        intent.putExtra(InfoActivity.TEXT_TO_SHOW, formatIsbanHTMLCode);
                        startActivityForResult(intent, 1);
                    }
                }
            } else if (getErrorListener() != null) {
                getErrorListener().onWebServiceErrorEvent(consultaLeyendasPrimerIngresoEvent, getTAG());
            }
        }
    }

    private void c(String str) {
        Intent intent = new Intent(getActivity(), SuscripcionActivity.class);
        intent.putExtra(SuscripcionActivity.ESTADO_SUSCRIPCION, str);
        startActivityForResult(intent, 2);
    }

    private String N() {
        return this.f ? this.i : "";
    }

    private void d(String str) {
        this.i = str;
        f(str);
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 1) {
            if (i3 == -1) {
                String stringExtra = intent.getStringExtra(LoginConstants.ACEPTA_TYC);
                if (stringExtra.equalsIgnoreCase(ResultValues.OK)) {
                    if (this.e.equalsIgnoreCase(LoginConstants.SUSC_NO_SUSC) || this.e.equalsIgnoreCase(LoginConstants.SUSC_ALTA_CEL) || this.e.equalsIgnoreCase(LoginConstants.SUSC_ALTA_MAIL)) {
                        c(this.e);
                    } else if (this.ag != null) {
                        this.ag.onLoginOk();
                    }
                } else if (stringExtra.equalsIgnoreCase(LoginConstants.CANCEL)) {
                    a(false);
                    this.a.logOutSession();
                }
            }
        } else if (i2 == 2) {
            if (i3 == -1) {
                String stringExtra2 = intent.getStringExtra(LoginConstants.SUSCRIPCION);
                if (stringExtra2.equalsIgnoreCase(ResultValues.OK)) {
                    if (this.ag != null) {
                        this.ag.onLoginOk();
                    }
                } else if (stringExtra2.equalsIgnoreCase(LoginConstants.CANCEL)) {
                    a(false);
                    this.a.logOutSession();
                }
            }
        } else if (i2 != 3 || i3 != -1) {
        } else {
            if (intent.getBooleanExtra(LoginInfoActivity.ACTION_GENERATE, false)) {
                this.ad = this.documentoLoginTradicional.getText().toString();
                a(LoginType.ALTA_USUARIO, false);
                this.h = true;
                return;
            }
            a(LoginType.TRADICIONAL, false);
            this.h = false;
        }
    }

    public void onResume() {
        super.onResume();
        B();
    }

    public void onStop() {
        super.onStop();
    }

    public void onSimpleActionButton() {
        P();
    }

    private void O() {
        B();
        b(getResources().getString(R.string.MSG_USER000008_Login_errorDatos));
        P();
    }

    /* access modifiers changed from: private */
    public void P() {
        if (!this.toggleRecuerdaDocumento.isChecked()) {
            this.documentoLoginTradicional.setText("");
        }
        this.claveLoginTradicional.setText("");
        this.usuarioLoginTradicional.setText("");
        if (!this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
            this.documentoLoginSinonimo.setText("");
            this.fechaLoginSinonimo.setText("");
        }
        this.claveLoginSinonimo.setText("");
        this.usuarioLoginSinonimo.setText("");
        if (!this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
            this.documentoLoginSinonimoCA.setText("");
            this.fechaLoginSinonimoCA.setText("");
        }
        this.claveLoginSinonimoCA.setText("");
        this.usuarioLoginSinonimoCA.setText("");
        this.reusuarioLoginSinonimoCA.setText("");
        this.antiguaClaveLoginClaveVencida.setText("");
        this.nuevaClaveLoginClaveVencida.setText("");
        this.reingresaClaveLoginClaveVencida.setText("");
        this.antiguoUsuarioLoginClaveVencida.setText("");
        this.nuevoUsuarioLoginClaveVencida.setText("");
        this.reingresaUsuarioLoginClaveVencida.setText("");
        this.claveLoginAltaUsuario.setText("");
        this.generaUsuarioLoginAltaUsuario.setText("");
        this.regeneraUsuarioLoginAltaUsuario.setText("");
        this.antiguoUsuarioLoginCambioUsuario.setText("");
        this.nuevoUsuarioLoginCambioUsuario.setText("");
        this.reingresaUsuarioLoginCambioUsuario.setText("");
        this.claveAntiguaLoginPrimerIngreso.setText("");
        this.nuevaClaveLoginPrimerIngreso.setText("");
        this.reingresaClaveLoginPrimerIngreso.setText("");
        this.generaUsuarioLoginPrimerIngreso.setText("");
        this.reingresaUsuarioLoginPrimerIngreso.setText("");
    }

    public void onBackPressed() {
        if (!this.loginTradicional.isShown() && (!this.loginSinonimo.isShown() || !this.f)) {
            this.claveLoginTradicional.setText("");
            this.usuarioLoginTradicional.setText("");
            a(LoginType.TRADICIONAL, true);
        } else if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 1) {
            ((SantanderRioMainActivity) getActivity()).backLastFragment();
        } else {
            this.ak.setResult(0);
            this.ak.finish();
            this.ak.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    public ILoginListener getLoginListener() {
        return this.ag;
    }

    public void setLoginListener(ILoginListener iLoginListener) {
        this.ag = iLoginListener;
    }

    public void setListenerValidators() {
        this.documentoLoginTradicional.setListenerValidator(this);
        this.claveLoginTradicional.setListenerValidator(this);
        this.documentoLoginSinonimo.setListenerValidator(this);
        this.fechaLoginSinonimo.setListenerValidator(this);
        this.claveLoginSinonimo.setListenerValidator(this);
        this.documentoLoginPrimerIngreso.setListenerValidator(this);
        this.nuevaClaveLoginPrimerIngreso.setListenerValidator(this);
        this.reingresaClaveLoginPrimerIngreso.setListenerValidator(this);
        this.reingresaUsuarioLoginPrimerIngreso.setListenerValidator(this);
        this.claveAntiguaLoginPrimerIngreso.setListenerValidator(this);
        this.antiguaClaveLoginClaveVencida.setListenerValidator(this);
        this.nuevaClaveLoginClaveVencida.setListenerValidator(this);
        this.reingresaClaveLoginClaveVencida.setListenerValidator(this);
        this.antiguoUsuarioLoginClaveVencida.setListenerValidator(this);
        this.reingresaUsuarioLoginClaveVencida.setListenerValidator(this);
        this.antiguoUsuarioLoginCambioUsuario.setListenerValidator(this);
        this.reingresaUsuarioLoginCambioUsuario.setListenerValidator(this);
        this.documentoLoginAltaUsuario.setListenerValidator(this);
        this.claveLoginAltaUsuario.setListenerValidator(this);
        this.regeneraUsuarioLoginAltaUsuario.setListenerValidator(this);
        this.documentoLoginSinonimoCA.setListenerValidator(this);
        this.fechaLoginSinonimoCA.setListenerValidator(this);
        this.claveLoginSinonimoCA.setListenerValidator(this);
        this.reusuarioLoginSinonimoCA.setListenerValidator(this);
    }

    public void inValidate(String str) {
        this.b.trackEvent(getString(R.string.analytics_category_login), Q(), str);
    }

    public void infoChecked() {
        boolean z = this.d == LoginType.TRADICIONAL;
        Intent intent = new Intent(getActivity(), LoginInfoActivity.class);
        intent.putExtra(LoginInfoActivity.CALL_TO_ACTION, z);
        startActivityForResult(intent, 3);
    }

    private String Q() {
        if (this.d == LoginType.TRADICIONAL) {
            return getString(R.string.analytics_action_login_error_validacion);
        }
        if (this.d == LoginType.SINONIMO) {
            return getString(R.string.analytics_action_login_error_validacion_login_usuario_sinonimo);
        }
        if (this.d == LoginType.CLAVE_VENCIDA) {
            return getString(R.string.analytics_action_login_error_validacion_clave_vencida);
        }
        if (this.d == LoginType.CAMBIO_USUARIO) {
            return getString(R.string.analytics_action_login_error_validacion_cambio_usuario);
        }
        return this.d == LoginType.PRIMER_INGRESO ? getString(R.string.analytics_action_login_error_validacion_primer_ingreso) : "";
    }

    private String R() {
        if ("".equalsIgnoreCase(this.ad)) {
            this.ad = this.documentoLoginTradicional.getText().toString();
        }
        return this.ad;
    }

    private boolean S() {
        if (!T() && !U()) {
            return false;
        }
        if (!this.toggleRecuerdaDocumento.isChecked()) {
            this.toggleRecuerdaDocumento.toggle();
        }
        if (!this.toggleRecuerdaDocumentoNoTradicional.isChecked()) {
            this.toggleRecuerdaDocumentoNoTradicional.toggle();
        }
        return true;
    }

    private boolean T() {
        return this.am.contains(NUM_DOCUMENTO_REMEMBER).booleanValue();
    }

    private boolean U() {
        return this.am.contains(NUM_DOCUMENTO_SINONIMO_REMEMBER).booleanValue();
    }

    /* access modifiers changed from: private */
    public void e(String str) {
        if (!this.toggleRecuerdaDocumento.isChecked() || str.isEmpty()) {
            this.ad = "";
            this.am.remove(NUM_DOCUMENTO_REMEMBER);
            e(false);
            return;
        }
        this.ad = str;
        this.am.putString(NUM_DOCUMENTO_REMEMBER, str, Boolean.valueOf(false));
        e(this.f);
    }

    /* access modifiers changed from: private */
    public void e(boolean z) {
        if (z) {
            this.am.putString(NUM_DOCUMENTO_SINONIMO_REMEMBER, NUM_DOCUMENTO_SINONIMO_REMEMBER, Boolean.valueOf(false));
            return;
        }
        this.am.remove(NUM_DOCUMENTO_SINONIMO_REMEMBER);
        f("");
    }

    private void f(String str) {
        if (!this.toggleRecuerdaDocumentoNoTradicional.isChecked() || str.isEmpty()) {
            this.am.remove(FECHA_SINONIMO_REMEMBER);
        } else {
            this.am.putString(FECHA_SINONIMO_REMEMBER, str, Boolean.valueOf(false));
        }
    }

    private String b(LoginType loginType) {
        String str = "";
        switch (loginType) {
            case TRADICIONAL:
            case SINONIMO:
                str = this.am.getString(NUM_DOCUMENTO_REMEMBER, "");
                break;
            case ALTA_USUARIO:
                str = this.am.getString(NUM_DOCUMENTO_ALTA_USUARIO_REMEMBER, "");
                break;
            case PRIMER_INGRESO:
                str = this.am.getString(NUM_DOCUMENTO_PRIMER_INGRESO_REMEMBER, "");
                break;
        }
        return !this.ad.equalsIgnoreCase("") ? R() : str;
    }

    private String V() {
        String string = this.am.getString(FECHA_SINONIMO_REMEMBER, "");
        return (this.i == null || this.i.equalsIgnoreCase("")) ? string : N();
    }

    public void goToNonTradicionalLogin(boolean z) {
        if (this.mControlPager != null) {
            if (!z) {
                this.mControlPager.clearAnimation();
            } else {
                if (this.mControlPager.getDisplayedChild() == 1) {
                    this.mControlPager.clearAnimation();
                    this.mControlPager.setDisplayedChild(0);
                }
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
            }
            this.mControlPager.setDisplayedChild(1);
        }
    }

    public void goToTradicionalLogin(boolean z) {
        if (this.mControlPager != null) {
            if (!z) {
                this.mControlPager.clearAnimation();
            } else {
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
            }
            this.mControlPager.setDisplayedChild(0);
        }
    }

    public boolean getHasToQueueLoginEvent() {
        return this.ai;
    }

    public List<LoginEvent> getQueuedLoginEvents() {
        return this.aj;
    }
}
