package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.seguros.ComprobanteContratacionPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.ComprobanteContratacionView;
import ar.com.santander.rio.mbanking.app.module.seguros.ConfirmarContratacionPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.ConfirmarContratacionView;
import ar.com.santander.rio.mbanking.app.module.seguros.ContratacionSeguroPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.ContratacionSeguroView;
import ar.com.santander.rio.mbanking.app.module.seguros.SeguroMovilPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.SeguroMovilView;
import ar.com.santander.rio.mbanking.app.module.seguros.SolicitarSeguroPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.SolicitarSeguroView;
import ar.com.santander.rio.mbanking.app.module.seguros.TutorialPatronPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.TutorialPatronView;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.adapters.SegurosPlanesAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.CodRamo;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.TipoAlta;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.idLeyenda;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroMovilEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BajaSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroMovilBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaItem;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OcupacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDeviceInfo;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultsKeys;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SeguroMovilActivity extends MvpPrivateMenuActivity implements ComprobanteContratacionView, ConfirmarContratacionView, ContratacionSeguroView, SeguroMovilView, SolicitarSeguroView, TutorialPatronView {
    public static final String MESSAGE_TO_SHOW_CONSTANT = "MESSAGE_TO_SHOW";
    public static final String TAG_DIALOG = "DialogTag";
    private ImageView A;
    /* access modifiers changed from: private */
    public String B = "";
    /* access modifiers changed from: private */
    public String C = "";
    private String D = "";
    /* access modifiers changed from: private */
    public OptionsToShare E;
    /* access modifiers changed from: private */
    public boolean F = false;
    @InjectView(2131363647)
    TextView F27_34_lbl_formapago_baja;
    @InjectView(2131363656)
    TextView F27_34_lbl_poliza;
    /* access modifiers changed from: private */
    public IsbanDialogFragment G;
    /* access modifiers changed from: private */
    public OcupacionBean H;
    /* access modifiers changed from: private */
    public SeguroMovilPresenter I;
    private SolicitarSeguroPresenter J;
    /* access modifiers changed from: private */
    public ContratacionSeguroPresenter K;
    /* access modifiers changed from: private */
    public ConfirmarContratacionPresenter L;
    /* access modifiers changed from: private */
    public ComprobanteContratacionPresenter M;
    private TutorialPatronPresenter N;
    /* access modifiers changed from: private */
    public SegurosBean O = new SegurosBean((List<SeguroBean>) new ArrayList<SeguroBean>());
    private boolean P = false;
    /* access modifiers changed from: private */
    public String Q;
    /* access modifiers changed from: private */
    public SeguroBean R;
    /* access modifiers changed from: private */
    public PlanSeguroBean S;
    /* access modifiers changed from: private */
    public CotizacionBean T;
    /* access modifiers changed from: private */
    public CuentaShortBean U = null;
    /* access modifiers changed from: private */
    public TarjetaBean V = null;
    /* access modifiers changed from: private */
    public String W;
    /* access modifiers changed from: private */
    public String X;
    private String Y;
    /* access modifiers changed from: private */
    public boolean Z = false;
    /* access modifiers changed from: private */
    public String aa = "";
    private List<PlanSeguroBean> ab;
    @InjectView(2131363549)
    Button btnActualizarCob;
    @InjectView(2131363551)
    Button btnAsegurarNuevo;
    @InjectView(2131363572)
    Button btnConfirmarContratacion;
    @InjectView(2131363676)
    Button btnContinuarContratacion;
    @InjectView(2131363552)
    Button btnReemplazarDisp;
    @InjectView(2131363528)
    Button btnTipoCoberturaContinuar;
    @InjectView(2131363550)
    Button btnVolverF27_27;
    @InjectView(2131363618)
    Button compBtnVolver;
    @InjectView(2131363621)
    TextView compDataCobertura;
    @InjectView(2131363624)
    TextView compDataFechaBaja;
    @InjectView(2131363623)
    TextView compDataFechaInicio;
    @InjectView(2131363625)
    TextView compDataFormaPago;
    @InjectView(2131363626)
    TextView compDataFormaPagoBaja;
    @InjectView(2131363627)
    TextView compDataImei;
    @InjectView(2131363628)
    TextView compDataImeiBaja;
    @InjectView(2131363629)
    TextView compDataImporteMensual;
    @InjectView(2131363630)
    TextView compDataMarca;
    @InjectView(2131363631)
    TextView compDataMarcaBaja;
    @InjectView(2131363632)
    TextView compDataMedioPago;
    @InjectView(2131363633)
    TextView compDataMedioPagoBaja;
    @InjectView(2131363634)
    TextView compDataModelo;
    @InjectView(2131363635)
    TextView compDataModeloBaja;
    @InjectView(2131363636)
    TextView compDataPoliza;
    @InjectView(2131363637)
    TextView compDataPolizaBaja;
    @InjectView(2131363638)
    TextView compDataPropietario;
    @InjectView(2131363639)
    TextView compDataPropietarioBaja;
    @InjectView(2131363640)
    TextView compDataSumaAsegurada1;
    @InjectView(2131363641)
    TextView compDataSumaAsegurada2;
    @InjectView(2131363642)
    TextView compDataSumaBaja;
    @InjectView(2131363648)
    TextView compImei;
    @InjectView(2131363649)
    TextView compLabelImeiBaja;
    @InjectView(2131363660)
    TextView compLabelSumaAsegurada1;
    @InjectView(2131363661)
    TextView compLabelSumaAsegurada2;
    @InjectView(2131364334)
    TextView compLeyendaSeguro;
    @InjectView(2131363663)
    LinearLayout compLinearBaja;
    @InjectView(2131363576)
    TextView confDataCobertura;
    @InjectView(2131363578)
    TextView confDataFormaPago;
    @InjectView(2131363579)
    TextView confDataFormaPagoBaja;
    @InjectView(2131363580)
    TextView confDataImei;
    @InjectView(2131363581)
    TextView confDataImeiBaja;
    @InjectView(2131363582)
    TextView confDataImporteMensual;
    @InjectView(2131363583)
    TextView confDataMarca;
    @InjectView(2131363584)
    TextView confDataMarcaBaja;
    @InjectView(2131363585)
    TextView confDataMedioPago;
    @InjectView(2131363586)
    TextView confDataMedioPagoBaja;
    @InjectView(2131363587)
    TextView confDataModelo;
    @InjectView(2131363588)
    TextView confDataModeloBaja;
    @InjectView(2131363589)
    TextView confDataOcupacion;
    @InjectView(2131363590)
    TextView confDataPolizaBaja;
    @InjectView(2131363591)
    TextView confDataPropietario;
    @InjectView(2131363592)
    TextView confDataPropietarioBaja;
    @InjectView(2131363593)
    TextView confDataSumaAsegurada1;
    @InjectView(2131363594)
    TextView confDataSumaAsegurada2;
    @InjectView(2131363595)
    TextView confDataSumaBaja;
    @InjectView(2131363596)
    TextView confDispAsegurado;
    @InjectView(2131363577)
    TextView confFechaInicioBaja;
    @InjectView(2131363612)
    TextView confLabelSumaAsegurada1;
    @InjectView(2131363613)
    TextView confLabelSumaAsegurada2;
    @InjectView(2131363615)
    LinearLayout confLinearCoberturaBaja;
    @InjectView(2131363616)
    TextView confTerminos;
    @InjectView(2131363600)
    TextView confTextViewImei;
    @InjectView(2131363601)
    TextView confTextViewImeiID2;
    @InjectView(2131363573)
    ImageView confirmarCheckBox;
    @InjectView(2131363533)
    TextView contratacionDataCobertura;
    @InjectView(2131363535)
    TextView contratacionDataFormaPago;
    @InjectView(2131363532)
    TextView contratacionDataImporte;
    @InjectView(2131363536)
    TextView contratacionDataMedioPago;
    @InjectView(2131363537)
    TextView contratacionDataOcupacion;
    @InjectView(2131363538)
    EditText contratacionDataPropietario;
    @InjectView(2131363539)
    TextView contratacionDataSumaAsegurada1;
    @InjectView(2131363540)
    TextView contratacionDataSumaAsegurada2;
    @InjectView(2131363534)
    TextView contratacionDispositivoAAnular;
    @InjectView(2131363542)
    TextView contratacionLabelSumaAsegurada1;
    @InjectView(2131363543)
    TextView contratacionLabelSumaAsegurada2;
    @InjectView(2131363554)
    ImageView imagenNoAsegurable;
    @InjectView(2131363657)
    TextView labelDataPolizaBaja;
    @InjectView(2131363555)
    LinearLayout layoutAsegurable;
    @InjectView(2131364891)
    RelativeLayout layoutCondiciones;
    @InjectView(2131364928)
    LinearLayout layoutFechaInicio;
    @InjectView(2131363556)
    RelativeLayout layoutNoAsegurable;
    @InjectView(2131364927)
    LinearLayout layout_fecha_baja;
    @InjectView(2131364933)
    LinearLayout layout_id_dispositivo_comp;
    @InjectView(2131364934)
    LinearLayout layout_id_dispositivo_confirmar;
    @InjectView(2131364939)
    LinearLayout layout_marca_comprobante;
    @InjectView(2131364940)
    LinearLayout layout_marca_confirmar;
    @InjectView(2131364946)
    LinearLayout layout_modelo;
    @InjectView(2131364947)
    LinearLayout layout_modelo_confirmar;
    @InjectView(2131364963)
    LinearLayout layout_propietario;
    @InjectView(2131364964)
    LinearLayout layout_propietario_confirmar;
    @InjectView(2131365007)
    TextView leyendas_label;
    @InjectView(2131365079)
    LinearLayout linearLayoutSumaAsegurada1;
    @InjectView(2131365080)
    LinearLayout linearLayoutSumaAsegurada1Comprobante;
    @InjectView(2131365081)
    LinearLayout linearLayoutSumaAsegurada1Confirmar;
    @InjectView(2131365082)
    LinearLayout linearLayoutSumaAsegurada2;
    @InjectView(2131365083)
    LinearLayout linearLayoutSumaAsegurada2Comprobante;
    @InjectView(2131365084)
    LinearLayout linearLayoutSumaAsegurada2Confirmar;
    @InjectView(2131363526)
    ListView listViewPlanes;
    @InjectView(2131365673)
    ViewFlipper mControlPager;
    public String messageToShow = "";
    @Inject
    AnalyticsManager p;
    @InjectView(2131363531)
    TextView propietarioTextView;
    @Inject
    SettingsManager q;
    ViewGroup r;
    @InjectView(2131365539)
    RelativeLayout relativeLayoutMedioDePagoLabel;
    @InjectView(2131365548)
    RelativeLayout relativeLayoutPropietarioLabel;
    @InjectView(2131363545)
    RelativeLayout rowContratacionDataOcupacion;
    @InjectView(2131363541)
    RelativeLayout rowDispositivoAnular;
    ViewGroup s;
    @InjectView(2131363547)
    View separatorDispositivoAnular;
    TextView t;
    @InjectView(2131365976)
    TextView textViewMedioDePago;
    @InjectView(2131363544)
    TextView textViewOcupacion;
    @InjectView(2131363609)
    TextView textViewPoliza;
    @InjectView(2131363559)
    TextView textoNoAsegurable;
    @InjectView(2131363560)
    TextView tituloSeguroMovil;
    @InjectView(2131363557)
    TextView txtDispositivoAsegurable;
    @InjectView(2131363558)
    TextView txtDispositivoNoAsegurable;
    TextView u;
    TextView v;
    @InjectView(2131363664)
    ScrollView viewComprobanteOperacion;
    @Inject
    SettingsManager w;
    private final List<LeyendaItem> x = new ArrayList();
    /* access modifiers changed from: private */
    public final BajaSeguroBean y = new BajaSeguroBean();
    /* access modifiers changed from: private */
    public final CAccessibility z = new CAccessibility(this);

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public Context getContext() {
        return this;
    }

    public int getMainLayout() {
        return R.layout.seguro_movil_activity_main;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.p.trackScreen(getString(R.string.f195analytics_screen_Cambio_dispositivo_mvil_asegurado));
        this.I = new SeguroMovilPresenter(this.mBus, this.mDataManager, this);
        this.J = new SolicitarSeguroPresenter(this.mBus, this.mDataManager);
        this.K = new ContratacionSeguroPresenter(this.mBus, this.mDataManager);
        this.L = new ConfirmarContratacionPresenter(this.mBus, this.mDataManager, this);
        this.M = new ComprobanteContratacionPresenter(this.mBus, this.mDataManager, this);
        this.N = new TutorialPatronPresenter(this.mBus);
        attachView();
        this.Q = getIntent().getStringExtra(SegurosConstants.INTENT_TIPO_ALTA_MOVIL);
        StringBuilder sb = new StringBuilder();
        sb.append(UtilDeviceInfo.getMarca());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(UtilDeviceInfo.getModelo());
        this.D = sb.toString();
        if (this.Q.equalsIgnoreCase(TipoAlta.ALTA_DISPOSITIVO)) {
            this.T = (CotizacionBean) getIntent().getParcelableExtra(SegurosConstants.INTENT_COTIZACION);
        }
        String str = this.Q;
        char c = 65535;
        switch (str.hashCode()) {
            case -1963721043:
                if (str.equals(TipoAlta.DISPOSITIVO_ASEGURADO)) {
                    c = 2;
                    break;
                }
                break;
            case -1498703863:
                if (str.equals(TipoAlta.NO_ASEGURABLE)) {
                    c = 4;
                    break;
                }
                break;
            case -451882672:
                if (str.equals(TipoAlta.ALTA_O_CAMBIO_DISPOSITIVO)) {
                    c = 1;
                    break;
                }
                break;
            case -277298990:
                if (str.equals(TipoAlta.SEGURO_CARTERA)) {
                    c = 3;
                    break;
                }
                break;
            case 490558090:
                if (str.equals(TipoAlta.ALTA_DISPOSITIVO)) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.Z = true;
                goToSeleccionarCobertura(this.T);
                return;
            case 1:
                this.O = (SegurosBean) getIntent().getParcelableExtra(SegurosConstants.INTENT_LISTA_SEGUROS);
                if (this.O == null || this.O.getListaSeguros() == null || this.O.getListaSeguros().isEmpty()) {
                    this.Q = TipoAlta.CAMBIO_DISPOSITIVO;
                } else {
                    this.Q = TipoAlta.ALTA_DISPOSITIVO;
                }
                this.I.onCreatePage();
                return;
            case 2:
            case 3:
                this.O = (SegurosBean) getIntent().getParcelableExtra(SegurosConstants.INTENT_LISTA_SEGUROS);
                this.P = getIntent().getBooleanExtra(SegurosConstants.INTENT_SEGUROS_CARTERA, false);
                this.I.onCreatePage();
                return;
            case 4:
                this.I.onCreatePage();
                return;
            default:
                return;
        }
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        ((ImageView) this.mActionBar.findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SeguroMovilActivity.this.onBackPressed();
            }
        });
    }

    private void a(int i, boolean z2) {
        detachView();
        if (this.mControlPager != null) {
            switch (i) {
                case 0:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                    break;
                case 1:
                    if (z2 && !this.Z) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    } else {
                        if (z2) {
                            boolean z3 = this.Z;
                        }
                        if (!z2) {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                            break;
                        }
                    }
                    break;
                case 2:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 3:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 4:
                    if (!z2) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
            configureActionBar();
            if (i == 5) {
                lockMenu(false);
            } else {
                lockMenu(true);
            }
        }
    }

    private void c(int i) {
        a(i, true);
    }

    @Subscribe
    public void onGetCotizacionSeguroMovil(GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent) {
        dismissProgressIndicator();
        final GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent2 = getCotizacionSeguroMovilEvent;
        AnonymousClass2 r0 = new BaseWSResponseHandler(getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, (BaseActivity) getContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                SeguroMovilActivity.this.Z = true;
                SeguroMovilActivity.this.goToSeleccionarCobertura(getCotizacionSeguroMovilEvent2.getResponse().getCotizacionSeguroMovilBodyResponseBean.getCotizacion());
            }
        };
        r0.handleWSResponse(getCotizacionSeguroMovilEvent);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setContratarSeguroMovilView() {
        /*
            r4 = this;
            r4.configureActionBar()
            java.lang.String r0 = r4.Q
            int r1 = r0.hashCode()
            r2 = 0
            switch(r1) {
                case -1963721043: goto L_0x0036;
                case -1498703863: goto L_0x002c;
                case -277298990: goto L_0x0022;
                case 490558090: goto L_0x0018;
                case 1903415307: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0040
        L_0x000e:
            java.lang.String r1 = "CAMBIO_DISPOSITIVO"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = 2
            goto L_0x0041
        L_0x0018:
            java.lang.String r1 = "ALTA_DISPOSITIVO"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = 3
            goto L_0x0041
        L_0x0022:
            java.lang.String r1 = "SEGURO_CARTERA"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = 1
            goto L_0x0041
        L_0x002c:
            java.lang.String r1 = "NO_ASEGURABLE"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = 4
            goto L_0x0041
        L_0x0036:
            java.lang.String r1 = "DISPOSITIVO_ASEGURADO"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = 0
            goto L_0x0041
        L_0x0040:
            r0 = -1
        L_0x0041:
            r1 = 8
            switch(r0) {
                case 0: goto L_0x00ad;
                case 1: goto L_0x00ad;
                case 2: goto L_0x0087;
                case 3: goto L_0x0087;
                case 4: goto L_0x0048;
                default: goto L_0x0046;
            }
        L_0x0046:
            goto L_0x0114
        L_0x0048:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r0 = r4.p
            r3 = 2131757698(0x7f100a82, float:1.914634E38)
            java.lang.String r3 = r4.getString(r3)
            r0.trackScreen(r3)
            android.widget.RelativeLayout r0 = r4.layoutNoAsegurable
            r0.setVisibility(r2)
            android.widget.LinearLayout r0 = r4.layoutAsegurable
            r0.setVisibility(r1)
            android.widget.ImageView r0 = r4.imagenNoAsegurable
            android.content.Context r1 = r4.getContext()
            android.content.res.Resources r1 = r1.getResources()
            r2 = 2131230955(0x7f0800eb, float:1.8077977E38)
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r2)
            r0.setBackground(r1)
            android.widget.TextView r0 = r4.textoNoAsegurable
            r1 = 2131756663(0x7f100677, float:1.914424E38)
            java.lang.String r1 = r4.getString(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r4.txtDispositivoNoAsegurable
            java.lang.String r1 = r4.D
            r0.setText(r1)
            goto L_0x0114
        L_0x0087:
            android.widget.Button r0 = r4.btnAsegurarNuevo
            ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$5 r3 = new ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$5
            r3.<init>(r4)
            r0.setOnClickListener(r3)
            android.widget.Button r0 = r4.btnReemplazarDisp
            ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$6 r3 = new ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$6
            r3.<init>(r4)
            r0.setOnClickListener(r3)
            android.widget.RelativeLayout r0 = r4.layoutNoAsegurable
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r4.layoutAsegurable
            r0.setVisibility(r2)
            android.widget.TextView r0 = r4.txtDispositivoAsegurable
            java.lang.String r1 = r4.D
            r0.setText(r1)
            goto L_0x0114
        L_0x00ad:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r0 = r4.p
            r3 = 2131757697(0x7f100a81, float:1.9146337E38)
            java.lang.String r3 = r4.getString(r3)
            r0.trackScreen(r3)
            android.widget.RelativeLayout r0 = r4.layoutNoAsegurable
            r0.setVisibility(r2)
            android.widget.LinearLayout r0 = r4.layoutAsegurable
            r0.setVisibility(r1)
            r0 = 2131363561(0x7f0a06e9, float:1.8346934E38)
            android.view.View r0 = r4.findViewById(r0)
            android.widget.ScrollView r0 = (android.widget.ScrollView) r0
            r0.setVisibility(r1)
            android.widget.TextView r0 = r4.textoNoAsegurable
            r1 = 2131756662(0x7f100676, float:1.9144238E38)
            java.lang.String r1 = r4.getString(r1)
            r0.setText(r1)
            android.widget.ImageView r0 = r4.imagenNoAsegurable
            android.content.res.Resources r1 = r4.getResources()
            r3 = 2131230998(0x7f080116, float:1.8078065E38)
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r3)
            r0.setBackground(r1)
            android.widget.TextView r0 = r4.txtDispositivoNoAsegurable
            java.lang.String r1 = r4.D
            r0.setText(r1)
            boolean r0 = r4.P
            if (r0 != 0) goto L_0x0114
            android.widget.Button r0 = r4.btnActualizarCob
            r0.setVisibility(r2)
            android.widget.Button r0 = r4.btnActualizarCob
            ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$3 r1 = new ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$3
            r1.<init>()
            r0.setOnClickListener(r1)
            android.widget.Button r0 = r4.btnVolverF27_27
            r0.setVisibility(r2)
            android.widget.Button r0 = r4.btnVolverF27_27
            ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$4 r1 = new ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$4
            r1.<init>()
            r0.setOnClickListener(r1)
        L_0x0114:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity.setContratarSeguroMovilView():void");
    }

    public void visibilityErrorDisplay(boolean z2) {
        if (z2) {
            ((LinearLayout) findViewById(R.id.LAYOUT_ERROR_PANTALLA)).setVisibility(0);
            ((LinearLayout) findViewById(R.id.LAYOUT_PANTALLA_OK)).setVisibility(8);
            return;
        }
        ((LinearLayout) findViewById(R.id.LAYOUT_ERROR_PANTALLA)).setVisibility(8);
        ((LinearLayout) findViewById(R.id.LAYOUT_PANTALLA_OK)).setVisibility(0);
    }

    public void dispositivoNoAsegurable(String str, String str2) {
        this.p.trackScreen(getString(R.string.analytics_screen_Aviso_imposibilidad_asegurar_dispositivo));
        visibilityErrorDisplay(true);
        c(2);
        ((TextView) findViewById(R.id.tv_title_tutorial)).setText("Atención");
        ((ImageView) findViewById(R.id.IMG_ERROR_PANTALLA)).setBackground(getContext().getResources().getDrawable(R.drawable.error_continuacion));
        ((TextView) findViewById(R.id.TITULO_ERROR)).setText(str);
        ((TextView) findViewById(R.id.DESCRIPCION_ERROR)).setText(str2);
        Button button = (Button) findViewById(R.id.tutorial_btn_continuar);
        button.setText("Volver");
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SeguroMovilActivity.this.onBackPressed();
            }
        });
    }

    public void setSolicitarSeguroView(CotizacionBean cotizacionBean) {
        this.T = cotizacionBean;
        if (!(cotizacionBean.getListaLeyendas() == null || cotizacionBean.getListaLeyendas().getLeyenda() == null)) {
            for (LeyendaItem add : cotizacionBean.getListaLeyendas().getLeyenda()) {
                this.x.add(add);
            }
        }
        String str = this.Q;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 490558090) {
            if (hashCode == 1903415307 && str.equals(TipoAlta.CAMBIO_DISPOSITIVO)) {
                c = 1;
            }
        } else if (str.equals(TipoAlta.ALTA_DISPOSITIVO)) {
            c = 0;
        }
        switch (c) {
            case 0:
                this.p.trackScreen(getString(R.string.f206analytics_screen_Solicitar_tipo_cobertura_seguro_mvil));
                break;
            case 1:
                this.p.trackScreen(getString(R.string.f205analytics_screen_Solicitar_tipo_cobertura_nuevo_seguro_mvil));
                break;
        }
        configureActionBar();
        List<PlanSeguroBean> listaPlanes = cotizacionBean.getPlanes().getListaPlanes();
        for (PlanSeguroBean checked : listaPlanes) {
            checked.setChecked(false);
        }
        ((PlanSeguroBean) listaPlanes.get(0)).setChecked(true);
        if (this.r == null && this.s == null) {
            this.r = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_seguro_movil_seleccion_cobertura_header, null, false);
            this.s = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_seguro_movil_seleccion_cobertura_footer, null, false);
            this.listViewPlanes.addHeaderView(this.r);
            this.listViewPlanes.addFooterView(this.s);
        }
        this.u = (TextView) this.s.findViewById(R.id.F27_21_lbl_detalle_cobertura);
        this.v = (TextView) this.r.findViewById(R.id.F27_21_lbl_data_importe);
        this.v.setText(((PlanSeguroBean) listaPlanes.get(0)).getCuota());
        try {
            this.v.setContentDescription(this.z.applyFilterGeneral(this.v.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.listViewPlanes.setAdapter(new SegurosPlanesAdapter(this, R.layout.item_seguros_plan, listaPlanes) {
            public void checkboxChanged(String str, String str2) {
                SeguroMovilActivity.this.v.setText(str2);
                try {
                    SeguroMovilActivity.this.v.setContentDescription(SeguroMovilActivity.this.z.applyFilterGeneral(SeguroMovilActivity.this.v.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.listViewPlanes.setScrollContainer(false);
        this.t = (TextView) this.r.findViewById(R.id.F27_21_dispositivo_a_asegurar);
        this.t.setText(this.D);
        this.u.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SeguroMovilActivity.this.Q.equals(TipoAlta.CAMBIO_DISPOSITIVO)) {
                    SeguroMovilActivity.this.p.trackScreen(SeguroMovilActivity.this.getString(R.string.analytics_screen_Detalle_cobertura_fa));
                } else {
                    SeguroMovilActivity.this.p.trackScreen(SeguroMovilActivity.this.getString(R.string.analytics_screen_Detalle_cobertura_seguro_movil));
                }
                SeguroMovilActivity.this.a(idLeyenda.SEGURO_MOVIL_DETALLE);
                SeguroMovilActivity.this.a(SeguroMovilActivity.this.B, SeguroMovilActivity.this.C);
            }
        });
        this.ab = listaPlanes;
        this.btnTipoCoberturaContinuar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SeguroMovilActivity.this.sessionManager.isValidateDisplayOK()) {
                    SeguroMovilActivity.this.irContratacionSeguro();
                } else {
                    SeguroMovilActivity.this.gotoTutorialPatron();
                }
            }
        });
    }

    public void changeFragment(Fragment fragment, String str) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.content_frame, fragment, str);
        beginTransaction.addToBackStack(str);
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.commit();
    }

    public void configActionBarDefault() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.A = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.A.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SeguroMovilActivity.this.setResult(0, new Intent());
                SeguroMovilActivity.this.onBackPressed();
            }
        });
    }

    public void setContratacionSeguroView(PlanSeguroBean planSeguroBean) {
        configureActionBar();
        this.V = null;
        this.U = null;
        this.contratacionDataImporte.setText(Html.fromHtml(planSeguroBean.getCuota()));
        this.contratacionDataCobertura.setText(Html.fromHtml(planSeguroBean.getNombre()));
        TextView textView = this.contratacionLabelSumaAsegurada1;
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.ID_4072_SEGUROS_LBL_SUMA_ASEG));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(Html.fromHtml(planSeguroBean.getDescripcionSuma1()));
        sb.append(": ");
        textView.setText(sb.toString());
        this.contratacionDataSumaAsegurada1.setText(Html.fromHtml(planSeguroBean.getSumaAsegurada1()));
        TextView textView2 = this.contratacionLabelSumaAsegurada2;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getContext().getString(R.string.ID_4072_SEGUROS_LBL_SUMA_ASEG));
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(Html.fromHtml(planSeguroBean.getDescripcionSuma2()));
        sb2.append(": ");
        textView2.setText(sb2.toString());
        this.contratacionDataSumaAsegurada2.setText(Html.fromHtml(planSeguroBean.getSumaAsegurada2()));
        setUpLabel(planSeguroBean.getSumaAsegurada1(), this.linearLayoutSumaAsegurada1, this.contratacionDataSumaAsegurada1, "AMOUNT", getContext());
        setUpLabel(planSeguroBean.getSumaAsegurada2(), this.linearLayoutSumaAsegurada2, this.contratacionDataSumaAsegurada2, "AMOUNT", getContext());
        this.contratacionDataMedioPago.setText(getContext().getString(R.string.F24_20_lbl_data_seleccionar));
        this.aa = this.contratacionDataMedioPago.getText().toString();
        if (this.H == null) {
            this.contratacionDataOcupacion.setText(getContext().getString(R.string.F24_20_lbl_data_seleccionar));
        } else {
            this.contratacionDataOcupacion.setText(Html.fromHtml(this.H.getDescOcupacion()));
        }
        try {
            this.contratacionDataMedioPago.setContentDescription(this.z.applyFilterGeneral(this.contratacionDataMedioPago.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.contratacionDataPropietario.setText("");
        h();
        this.contratacionDataPropietario.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                SeguroMovilActivity.this.h();
            }
        });
        this.contratacionDataMedioPago.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SeguroMovilActivity.this.Q.equalsIgnoreCase(TipoAlta.ALTA_DISPOSITIVO)) {
                    SeguroMovilActivity.this.p.trackEvent(SeguroMovilActivity.this.getString(R.string.analytics_trackevent_category_seguros), SeguroMovilActivity.this.getString(R.string.f220analytics_trackevent_action_seleccionar_medio_pago_mvil), SeguroMovilActivity.this.getString(R.string.analytics_trackevent_label_medio_pago));
                } else {
                    SeguroMovilActivity.this.p.trackEvent(SeguroMovilActivity.this.getString(R.string.analytics_trackevent_category_seguros), SeguroMovilActivity.this.getString(R.string.f221x88700815), SeguroMovilActivity.this.getString(R.string.analytics_trackevent_label_medio_pago));
                }
                SeguroMovilActivity.this.f();
            }
        });
        h();
        this.rowContratacionDataOcupacion.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SeguroMovilActivity.this.K.getOcupaciones(this, SeguroMovilActivity.this.contratacionDataOcupacion.getText().toString(), SeguroMovilActivity.this.sessionManager.getListaOcupaciones(), SeguroMovilActivity.this.sessionManager);
            }
        });
        String str = this.Q;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1963721043) {
            if (hashCode != -277298990) {
                if (hashCode != 490558090) {
                    if (hashCode == 1903415307 && str.equals(TipoAlta.CAMBIO_DISPOSITIVO)) {
                        c = 1;
                    }
                } else if (str.equals(TipoAlta.ALTA_DISPOSITIVO)) {
                    c = 0;
                }
            } else if (str.equals(TipoAlta.SEGURO_CARTERA)) {
                c = 3;
            }
        } else if (str.equals(TipoAlta.DISPOSITIVO_ASEGURADO)) {
            c = 2;
        }
        switch (c) {
            case 0:
                this.p.trackScreen(getString(R.string.f202analytics_screen_Contratar_seguro_mvil));
                this.rowDispositivoAnular.setVisibility(8);
                this.separatorDispositivoAnular.setVisibility(8);
                break;
            case 1:
                this.rowDispositivoAnular.setVisibility(0);
                this.separatorDispositivoAnular.setVisibility(0);
                if (getSegurosContratados(this.O.getListaSeguros()) <= 1) {
                    this.contratacionDispositivoAAnular.setTextColor(getResources().getColor(R.color.generic_grey));
                    this.contratacionDispositivoAAnular.setText(((SeguroBean) this.O.getListaSeguros().get(0)).getPropietario());
                    this.R = (SeguroBean) this.O.getListaSeguros().get(0);
                    break;
                } else {
                    this.contratacionDispositivoAAnular.setText(getString(R.string.F27_21_lbl_seleccionar));
                    this.contratacionDispositivoAAnular.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
                    this.contratacionDispositivoAAnular.setCompoundDrawablePadding(Utils.dpToPx(5, (Context) this));
                    this.contratacionDispositivoAAnular.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            SeguroMovilActivity.this.g();
                        }
                    });
                    break;
                }
            case 2:
            case 3:
                this.rowDispositivoAnular.setVisibility(8);
                this.separatorDispositivoAnular.setVisibility(8);
                break;
        }
        try {
            this.contratacionDataSumaAsegurada1.setContentDescription(this.z.applyFilterAmount(this.contratacionDataSumaAsegurada1.getText().toString()));
            this.contratacionDataSumaAsegurada2.setContentDescription(this.z.applyFilterAmount(this.contratacionDataSumaAsegurada2.getText().toString()));
            this.contratacionDataImporte.setContentDescription(this.z.applyFilterAmount(this.contratacionDataImporte.getText().toString()));
            RelativeLayout relativeLayout = this.relativeLayoutPropietarioLabel;
            CAccessibility cAccessibility = this.z;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.propietarioTextView.getText().toString());
            sb3.append(this.contratacionDataPropietario.getHint().toString());
            relativeLayout.setContentDescription(cAccessibility.applyFilterGeneral(sb3.toString()));
            RelativeLayout relativeLayout2 = this.relativeLayoutMedioDePagoLabel;
            CAccessibility cAccessibility2 = this.z;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this.textViewMedioDePago.getText().toString());
            sb4.append(this.z.applyFilterAccount(this.contratacionDataMedioPago.getText().toString()));
            relativeLayout2.setContentDescription(cAccessibility2.applyFilterGeneral(sb4.toString()));
            RelativeLayout relativeLayout3 = this.rowContratacionDataOcupacion;
            CAccessibility cAccessibility3 = this.z;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(this.textViewOcupacion.getText().toString());
            sb5.append(this.contratacionDataOcupacion.getText().toString());
            relativeLayout3.setContentDescription(cAccessibility3.applyFilterGeneral(sb5.toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if (!this.x.isEmpty()) {
            for (LeyendaItem leyendaItem : this.x) {
                if (leyendaItem.getIdLeyenda().equalsIgnoreCase(str)) {
                    this.B = leyendaItem.getTitulo();
                    this.C = leyendaItem.getDescripcion();
                    return;
                }
            }
        }
    }

    public int getSegurosContratados(List<SeguroBean> list) {
        int i = 0;
        for (SeguroBean seguroBean : list) {
            if (!seguroBean.getCodRamo().equals(CodRamo.PROTECCION_CARTERA) && seguroBean.getIdDispositivo() != null && !seguroBean.getIdDispositivo().isEmpty()) {
                i++;
            }
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0170  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x02a6  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0354  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setConfirmarContratacionView() {
        /*
            r11 = this;
            r11.configureActionBar()
            java.lang.String r0 = "SEG_TYC_MOV"
            r11.a(r0)
            android.widget.TextView r0 = r11.confDataImporteMensual
            android.widget.TextView r1 = r11.contratacionDataImporte
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataCobertura
            android.widget.TextView r1 = r11.contratacionDataCobertura
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confLabelSumaAsegurada1
            android.widget.TextView r1 = r11.contratacionLabelSumaAsegurada1
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataSumaAsegurada1
            android.widget.TextView r1 = r11.contratacionDataSumaAsegurada1
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confLabelSumaAsegurada2
            android.widget.TextView r1 = r11.contratacionLabelSumaAsegurada2
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataSumaAsegurada2
            android.widget.TextView r1 = r11.contratacionDataSumaAsegurada2
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataPropietario
            android.widget.EditText r1 = r11.contratacionDataPropietario
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = r1.trim()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataMarca
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilDeviceInfo.getMarca()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataModelo
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilDeviceInfo.getModelo()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataImei
            ar.com.santander.rio.mbanking.managers.preferences.SettingsManager r1 = r11.q
            java.lang.String r1 = r1.getKeyUniqueID()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataMedioPago
            android.widget.TextView r1 = r11.contratacionDataMedioPago
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataFormaPago
            android.widget.TextView r1 = r11.contratacionDataFormaPago
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r0 = r11.S
            java.lang.String r0 = r0.getSumaAsegurada1()
            android.widget.LinearLayout r1 = r11.linearLayoutSumaAsegurada1Confirmar
            android.widget.TextView r2 = r11.confDataSumaAsegurada1
            java.lang.String r3 = "AMOUNT"
            android.content.Context r4 = r11.getContext()
            setUpLabel(r0, r1, r2, r3, r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r0 = r11.S
            java.lang.String r0 = r0.getSumaAsegurada2()
            android.widget.LinearLayout r1 = r11.linearLayoutSumaAsegurada2Confirmar
            android.widget.TextView r2 = r11.confDataSumaAsegurada2
            java.lang.String r3 = "AMOUNT"
            android.content.Context r4 = r11.getContext()
            setUpLabel(r0, r1, r2, r3, r4)
            android.widget.ImageView r0 = r11.confirmarCheckBox
            r1 = 0
            java.lang.String r2 = java.lang.String.valueOf(r1)
            r0.setTag(r2)
            android.widget.TextView r0 = r11.confDataOcupacion
            android.widget.TextView r2 = r11.contratacionDataOcupacion
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)
            r0.setText(r2)
            android.widget.ImageView r0 = r11.confirmarCheckBox
            java.lang.String r2 = "false"
            r0.setTag(r2)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r1)
            r11.a(r0)
            android.widget.ImageView r0 = r11.confirmarCheckBox
            android.content.res.Resources r2 = r11.getResources()
            r3 = 2131231008(0x7f080120, float:1.8078085E38)
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r3)
            r0.setBackground(r2)
            android.widget.ImageView r0 = r11.confirmarCheckBox
            ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$16 r2 = new ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$16
            r2.<init>()
            r0.setOnClickListener(r2)
            android.widget.TextView r0 = r11.confTerminos
            ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$17 r2 = new ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$17
            r2.<init>()
            r0.setOnClickListener(r2)
            android.widget.RelativeLayout r0 = r11.layoutCondiciones
            android.content.Context r2 = r11.getContext()
            r3 = 2131756628(0x7f100654, float:1.9144169E38)
            java.lang.String r2 = r2.getString(r3)
            r0.setContentDescription(r2)
            java.lang.String r0 = r11.Q
            int r2 = r0.hashCode()
            r3 = -1963721043(0xffffffff8af3fead, float:-2.3495847E-32)
            if (r2 == r3) goto L_0x0152
            r3 = -277298990(0xffffffffef78c0d2, float:-7.698539E28)
            if (r2 == r3) goto L_0x0148
            r3 = 490558090(0x1d3d528a, float:2.5056613E-21)
            if (r2 == r3) goto L_0x013e
            r3 = 1903415307(0x7173d00b, float:1.20730185E30)
            if (r2 == r3) goto L_0x0134
            goto L_0x015c
        L_0x0134:
            java.lang.String r2 = "CAMBIO_DISPOSITIVO"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x015c
            r0 = 1
            goto L_0x015d
        L_0x013e:
            java.lang.String r2 = "ALTA_DISPOSITIVO"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x015c
            r0 = 0
            goto L_0x015d
        L_0x0148:
            java.lang.String r2 = "SEGURO_CARTERA"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x015c
            r0 = 3
            goto L_0x015d
        L_0x0152:
            java.lang.String r2 = "DISPOSITIVO_ASEGURADO"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x015c
            r0 = 2
            goto L_0x015d
        L_0x015c:
            r0 = -1
        L_0x015d:
            r2 = 2131755443(0x7f1001b3, float:1.9141765E38)
            r3 = 2131756669(0x7f10067d, float:1.9144252E38)
            r4 = 2131756608(0x7f100640, float:1.9144128E38)
            r5 = 2131757706(0x7f100a8a, float:1.9146355E38)
            r6 = 8
            switch(r0) {
                case 0: goto L_0x0354;
                case 1: goto L_0x02a6;
                case 2: goto L_0x0170;
                case 3: goto L_0x0170;
                default: goto L_0x016e;
            }
        L_0x016e:
            goto L_0x0379
        L_0x0170:
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r0 = new ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean
            r0.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean r7 = r11.O
            java.util.List r7 = r7.getListaSeguros()
            java.util.Iterator r7 = r7.iterator()
        L_0x017f:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x019d
            java.lang.Object r8 = r7.next()
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r8 = (ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean) r8
            java.lang.String r9 = r8.getIdDispositivo()
            ar.com.santander.rio.mbanking.managers.preferences.SettingsManager r10 = r11.q
            java.lang.String r10 = r10.getKeyUniqueID()
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x017f
            r0 = r8
            goto L_0x017f
        L_0x019d:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r7 = r11.p
            java.lang.String r5 = r11.getString(r5)
            r7.trackScreen(r5)
            android.widget.LinearLayout r5 = r11.confLinearCoberturaBaja
            r5.setVisibility(r1)
            android.widget.TextView r1 = r11.confDataPolizaBaja
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = r0.getNumPoliza()
            r5.append(r7)
            java.lang.String r7 = "/"
            r5.append(r7)
            java.lang.String r7 = r0.getNumCertificado()
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            r1.setText(r5)
            android.widget.TextView r1 = r11.confDataSumaBaja
            java.lang.String r5 = r0.getSumaAsegurada()
            r1.setText(r5)
            android.widget.LinearLayout r1 = r11.layoutFechaInicio
            r1.setVisibility(r6)
            android.widget.TextView r1 = r11.confDataFormaPagoBaja
            java.lang.String r4 = r11.getString(r4)
            r1.setText(r4)
            android.widget.TextView r1 = r11.confDataMedioPagoBaja
            java.lang.String r4 = r0.getMedioPago()
            r1.setText(r4)
            android.widget.LinearLayout r1 = r11.layout_propietario_confirmar
            r1.setVisibility(r6)
            android.widget.LinearLayout r1 = r11.layout_marca_confirmar
            r1.setVisibility(r6)
            android.widget.LinearLayout r1 = r11.layout_modelo_confirmar
            r1.setVisibility(r6)
            android.widget.LinearLayout r1 = r11.layout_id_dispositivo_confirmar
            r1.setVisibility(r6)
            android.widget.TextView r1 = r11.confDispAsegurado
            java.lang.String r3 = r11.getString(r3)
            r1.setText(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.body.BajaSeguroBean r1 = r11.y
            java.lang.String r3 = r0.getNumCertificado()
            r1.setNumCertificado(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.body.BajaSeguroBean r1 = r11.y
            java.lang.String r0 = r0.getNumPoliza()
            r1.setNumPoliza(r0)
            android.widget.TextView r0 = r11.leyendas_label
            r0.setText(r2)
            android.widget.TextView r0 = r11.confDataPolizaBaja     // Catch:{ Exception -> 0x02a0 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r2 = r11.confDataPolizaBaja     // Catch:{ Exception -> 0x02a0 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x02a0 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r0 = r11.textViewPoliza     // Catch:{ Exception -> 0x02a0 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r2 = r11.textViewPoliza     // Catch:{ Exception -> 0x02a0 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x02a0 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r0 = r11.confDataSumaBaja     // Catch:{ Exception -> 0x02a0 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r2 = r11.confDataSumaBaja     // Catch:{ Exception -> 0x02a0 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x02a0 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r0 = r11.confDataMedioPagoBaja     // Catch:{ Exception -> 0x02a0 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r2 = r11.confDataMedioPagoBaja     // Catch:{ Exception -> 0x02a0 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x02a0 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r0 = r11.confTextViewImei     // Catch:{ Exception -> 0x02a0 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r2 = r11.confTextViewImei     // Catch:{ Exception -> 0x02a0 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x02a0 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r0 = r11.confTextViewImeiID2     // Catch:{ Exception -> 0x02a0 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x02a0 }
            android.widget.TextView r2 = r11.confTextViewImeiID2     // Catch:{ Exception -> 0x02a0 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02a0 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x02a0 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x02a0 }
            goto L_0x0379
        L_0x02a0:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0379
        L_0x02a6:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r0 = r11.p
            java.lang.String r5 = r11.getString(r5)
            r0.trackScreen(r5)
            android.widget.LinearLayout r0 = r11.confLinearCoberturaBaja
            r0.setVisibility(r1)
            android.widget.TextView r0 = r11.confDataPolizaBaja
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r5 = r11.R
            java.lang.String r5 = r5.getNumPoliza()
            r1.append(r5)
            java.lang.String r5 = "/"
            r1.append(r5)
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r5 = r11.R
            java.lang.String r5 = r5.getNumCertificado()
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataSumaBaja
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r1 = r11.R
            java.lang.String r1 = r1.getSumaAsegurada()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confFechaInicioBaja
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r1 = r11.R
            java.lang.String r1 = r1.getFechaInicio()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataFormaPagoBaja
            java.lang.String r1 = r11.getString(r4)
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataMedioPagoBaja
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r1 = r11.R
            java.lang.String r1 = r1.getMedioPago()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataPropietarioBaja
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r1 = r11.R
            java.lang.String r1 = r1.getPropietario()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataMarcaBaja
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r1 = r11.R
            java.lang.String r1 = r1.getMarca()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataModeloBaja
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r1 = r11.R
            java.lang.String r1 = r1.getModelo()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDataImeiBaja
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r1 = r11.R
            java.lang.String r1 = r1.getIdDispositivo()
            r0.setText(r1)
            android.widget.TextView r0 = r11.confDispAsegurado
            java.lang.String r1 = r11.getString(r3)
            r0.setText(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.BajaSeguroBean r0 = r11.y
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r1 = r11.R
            java.lang.String r1 = r1.getNumCertificado()
            r0.setNumCertificado(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.BajaSeguroBean r0 = r11.y
            ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean r1 = r11.R
            java.lang.String r1 = r1.getNumPoliza()
            r0.setNumPoliza(r1)
            android.widget.TextView r0 = r11.leyendas_label
            r0.setText(r2)
            goto L_0x0379
        L_0x0354:
            android.widget.TextView r0 = r11.confDispAsegurado
            r1 = 2131756668(0x7f10067c, float:1.914425E38)
            java.lang.String r1 = r11.getString(r1)
            r0.setText(r1)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r0 = r11.p
            r1 = 2131757705(0x7f100a89, float:1.9146353E38)
            java.lang.String r1 = r11.getString(r1)
            r0.trackScreen(r1)
            android.widget.LinearLayout r0 = r11.confLinearCoberturaBaja
            r0.setVisibility(r6)
            android.widget.TextView r0 = r11.leyendas_label
            r1 = 2131755437(0x7f1001ad, float:1.9141753E38)
            r0.setText(r1)
        L_0x0379:
            android.widget.TextView r0 = r11.confDataImporteMensual     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confDataImporteMensual     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confDataSumaAsegurada1     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confDataSumaAsegurada1     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confDataSumaAsegurada2     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confDataSumaAsegurada2     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confDataImei     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confDataImei     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confDataMedioPago     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confDataMedioPago     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confDataPolizaBaja     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confDataPolizaBaja     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.textViewPoliza     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.textViewPoliza     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confDataSumaBaja     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confDataSumaBaja     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confFechaInicioBaja     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confFechaInicioBaja     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterDate(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confDataMedioPagoBaja     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confDataMedioPagoBaja     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confDataImeiBaja     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confDataImeiBaja     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confTextViewImei     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confTextViewImei     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r0 = r11.confTextViewImeiID2     // Catch:{ Exception -> 0x048b }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r11.z     // Catch:{ Exception -> 0x048b }
            android.widget.TextView r2 = r11.confTextViewImeiID2     // Catch:{ Exception -> 0x048b }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x048b }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x048b }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x048b }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x048b }
            goto L_0x048f
        L_0x048b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x048f:
            android.widget.TextView r0 = r11.leyendas_label
            android.content.Context r1 = r11.getContext()
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)
            android.widget.TextView r2 = r11.leyendas_label
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            java.lang.String r1 = r1.applyFilter_Email(r2)
            r0.setContentDescription(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity.setConfirmarContratacionView():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0174  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0238  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x02aa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setComprobanteContratacionView(ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroMovilBodyResponseBean r7) {
        /*
            r6 = this;
            r6.k()
            r0 = 0
            r6.lockMenu(r0)
            java.lang.String r1 = r7.getNumCertificado()
            r6.W = r1
            java.lang.String r1 = r7.getNumPoliza()
            r6.X = r1
            android.widget.TextView r1 = r6.compDataImporteMensual
            android.widget.TextView r2 = r6.confDataImporteMensual
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compDataCobertura
            android.widget.TextView r2 = r6.confDataCobertura
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compLabelSumaAsegurada1
            android.widget.TextView r2 = r6.contratacionLabelSumaAsegurada1
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compDataSumaAsegurada1
            android.widget.TextView r2 = r6.confDataSumaAsegurada1
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compLabelSumaAsegurada2
            android.widget.TextView r2 = r6.contratacionLabelSumaAsegurada2
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compDataSumaAsegurada2
            android.widget.TextView r2 = r6.confDataSumaAsegurada2
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compDataFechaInicio
            java.lang.String r2 = r7.getFechaInicio()
            r1.setText(r2)
            android.widget.TextView r1 = r6.F27_34_lbl_poliza
            android.content.Context r2 = r6.getContext()
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r2 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r2)
            android.widget.TextView r3 = r6.F27_34_lbl_poliza
            java.lang.CharSequence r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            java.lang.String r2 = r2.applyFilterNumeroGradoBarra(r3)
            r1.setContentDescription(r2)
            android.widget.TextView r1 = r6.compDataPoliza
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r7.getNumPoliza()
            r2.append(r3)
            java.lang.String r3 = "/"
            r2.append(r3)
            java.lang.String r3 = r6.W
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.setText(r2)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r7.getNumPoliza()
            r1.append(r2)
            java.lang.String r2 = "/"
            r1.append(r2)
            java.lang.String r2 = r6.W
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r6.Y = r1
            android.widget.TextView r1 = r6.compDataPropietario
            android.widget.TextView r2 = r6.confDataPropietario
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compDataMarca
            android.widget.TextView r2 = r6.confDataMarca
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compDataModelo
            android.widget.TextView r2 = r6.confDataModelo
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compDataImei
            android.widget.TextView r2 = r6.confDataImei
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compDataMedioPago
            android.widget.TextView r2 = r6.confDataMedioPago
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.TextView r1 = r6.compDataFormaPago
            android.widget.TextView r2 = r6.confDataFormaPago
            java.lang.CharSequence r2 = r2.getText()
            r1.setText(r2)
            android.widget.Button r1 = r6.compBtnVolver
            ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$18 r2 = new ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity$18
            r2.<init>()
            r1.setOnClickListener(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r1 = r6.S
            java.lang.String r1 = r1.getSumaAsegurada1()
            android.widget.LinearLayout r2 = r6.linearLayoutSumaAsegurada1Comprobante
            android.widget.TextView r3 = r6.compDataSumaAsegurada1
            java.lang.String r4 = "AMOUNT"
            android.content.Context r5 = r6.getContext()
            setUpLabel(r1, r2, r3, r4, r5)
            ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean r1 = r6.S
            java.lang.String r1 = r1.getSumaAsegurada2()
            android.widget.LinearLayout r2 = r6.linearLayoutSumaAsegurada2Comprobante
            android.widget.TextView r3 = r6.compDataSumaAsegurada2
            java.lang.String r4 = "AMOUNT"
            android.content.Context r5 = r6.getContext()
            setUpLabel(r1, r2, r3, r4, r5)
            java.lang.String r1 = r6.Q
            int r2 = r1.hashCode()
            r3 = -1963721043(0xffffffff8af3fead, float:-2.3495847E-32)
            if (r2 == r3) goto L_0x015f
            r3 = -277298990(0xffffffffef78c0d2, float:-7.698539E28)
            if (r2 == r3) goto L_0x0155
            r3 = 490558090(0x1d3d528a, float:2.5056613E-21)
            if (r2 == r3) goto L_0x014b
            r3 = 1903415307(0x7173d00b, float:1.20730185E30)
            if (r2 == r3) goto L_0x0141
            goto L_0x0169
        L_0x0141:
            java.lang.String r2 = "CAMBIO_DISPOSITIVO"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0169
            r1 = 1
            goto L_0x016a
        L_0x014b:
            java.lang.String r2 = "ALTA_DISPOSITIVO"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0169
            r1 = 0
            goto L_0x016a
        L_0x0155:
            java.lang.String r2 = "SEGURO_CARTERA"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0169
            r1 = 3
            goto L_0x016a
        L_0x015f:
            java.lang.String r2 = "DISPOSITIVO_ASEGURADO"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0169
            r1 = 2
            goto L_0x016a
        L_0x0169:
            r1 = -1
        L_0x016a:
            r2 = 2131757703(0x7f100a87, float:1.914635E38)
            r3 = 8
            switch(r1) {
                case 0: goto L_0x02aa;
                case 1: goto L_0x0238;
                case 2: goto L_0x0174;
                case 3: goto L_0x0174;
                default: goto L_0x0172;
            }
        L_0x0172:
            goto L_0x02bb
        L_0x0174:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r6.p
            java.lang.String r2 = r6.getString(r2)
            r1.trackScreen(r2)
            android.widget.LinearLayout r1 = r6.compLinearBaja
            r1.setVisibility(r0)
            android.widget.TextView r0 = r6.compDataPolizaBaja
            android.widget.TextView r1 = r6.confDataPolizaBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataSumaBaja
            android.widget.TextView r1 = r6.confDataSumaBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.LinearLayout r0 = r6.layout_fecha_baja
            r0.setVisibility(r3)
            android.widget.TextView r0 = r6.compDataMedioPagoBaja
            android.widget.TextView r1 = r6.confDataMedioPagoBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataFormaPagoBaja
            android.widget.TextView r1 = r6.confDataFormaPagoBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.LinearLayout r0 = r6.layout_propietario
            r0.setVisibility(r3)
            android.widget.LinearLayout r0 = r6.layout_marca_comprobante
            r0.setVisibility(r3)
            android.widget.LinearLayout r0 = r6.layout_modelo
            r0.setVisibility(r3)
            android.widget.LinearLayout r0 = r6.layout_id_dispositivo_comp
            r0.setVisibility(r3)
            android.widget.TextView r0 = r6.compDataSumaBaja     // Catch:{ Exception -> 0x0232 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x0232 }
            android.widget.TextView r2 = r6.compDataSumaBaja     // Catch:{ Exception -> 0x0232 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x0232 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0232 }
            android.widget.TextView r0 = r6.compDataFechaBaja     // Catch:{ Exception -> 0x0232 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x0232 }
            android.widget.TextView r2 = r6.compDataFechaBaja     // Catch:{ Exception -> 0x0232 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r1 = r1.applyFilterDate(r2)     // Catch:{ Exception -> 0x0232 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0232 }
            android.widget.TextView r0 = r6.compDataMedioPagoBaja     // Catch:{ Exception -> 0x0232 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x0232 }
            android.widget.TextView r2 = r6.compDataMedioPagoBaja     // Catch:{ Exception -> 0x0232 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x0232 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0232 }
            android.widget.TextView r0 = r6.compDataImeiBaja     // Catch:{ Exception -> 0x0232 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x0232 }
            android.widget.TextView r2 = r6.compDataImeiBaja     // Catch:{ Exception -> 0x0232 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x0232 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0232 }
            android.widget.TextView r0 = r6.compLeyendaSeguro     // Catch:{ Exception -> 0x0232 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x0232 }
            android.widget.TextView r2 = r6.compLeyendaSeguro     // Catch:{ Exception -> 0x0232 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r1 = r1.applyFilter_Email(r2)     // Catch:{ Exception -> 0x0232 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0232 }
            goto L_0x02bb
        L_0x0232:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x02bb
        L_0x0238:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r6.p
            java.lang.String r2 = r6.getString(r2)
            r1.trackScreen(r2)
            android.widget.LinearLayout r1 = r6.compLinearBaja
            r1.setVisibility(r0)
            android.widget.TextView r0 = r6.compDataPolizaBaja
            android.widget.TextView r1 = r6.confDataPolizaBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataSumaBaja
            android.widget.TextView r1 = r6.confDataSumaBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataFechaBaja
            android.widget.TextView r1 = r6.confFechaInicioBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataMedioPagoBaja
            android.widget.TextView r1 = r6.confDataMedioPagoBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataFormaPagoBaja
            android.widget.TextView r1 = r6.confDataFormaPagoBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataPropietarioBaja
            android.widget.TextView r1 = r6.confDataPropietarioBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataMarcaBaja
            android.widget.TextView r1 = r6.confDataMarcaBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataModeloBaja
            android.widget.TextView r1 = r6.confDataModeloBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            android.widget.TextView r0 = r6.compDataImeiBaja
            android.widget.TextView r1 = r6.confDataImeiBaja
            java.lang.CharSequence r1 = r1.getText()
            r0.setText(r1)
            goto L_0x02bb
        L_0x02aa:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r0 = r6.p
            r1 = 2131757702(0x7f100a86, float:1.9146347E38)
            java.lang.String r1 = r6.getString(r1)
            r0.trackScreen(r1)
            android.widget.LinearLayout r0 = r6.compLinearBaja
            r0.setVisibility(r3)
        L_0x02bb:
            android.widget.TextView r0 = r6.compLeyendaSeguro     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compLeyendaSeguro     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilter_Email(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataImporteMensual     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataImporteMensual     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataSumaAsegurada1     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataSumaAsegurada1     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataSumaAsegurada2     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataSumaAsegurada2     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataFechaInicio     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataFechaInicio     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterDate(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataPoliza     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataPoliza     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compImei     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compImei     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataMedioPago     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataMedioPago     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.labelDataPolizaBaja     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.labelDataPolizaBaja     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataPolizaBaja     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataPolizaBaja     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataSumaBaja     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataSumaBaja     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataFechaBaja     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataFechaBaja     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterDate(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataMedioPagoBaja     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataMedioPagoBaja     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compLabelImeiBaja     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compLabelImeiBaja     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r0 = r6.compDataImeiBaja     // Catch:{ Exception -> 0x03f7 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = r6.z     // Catch:{ Exception -> 0x03f7 }
            android.widget.TextView r2 = r6.compDataImeiBaja     // Catch:{ Exception -> 0x03f7 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03f7 }
            java.lang.String r1 = r1.applyFilterNumeroGradoBarra(r2)     // Catch:{ Exception -> 0x03f7 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03f7 }
            goto L_0x03fb
        L_0x03f7:
            r0 = move-exception
            r0.printStackTrace()
        L_0x03fb:
            java.lang.String r0 = r7.getFechaInicio()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r7 = r7.getNumPoliza()
            r1.append(r7)
            java.lang.String r7 = "/"
            r1.append(r7)
            java.lang.String r7 = r6.W
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r6.p
            r2 = 2131758176(0x7f100c60, float:1.9147309E38)
            java.lang.String r2 = r6.getString(r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r7)
            java.lang.String r7 = " "
            r3.append(r7)
            r3.append(r0)
            java.lang.String r7 = r3.toString()
            r1.trackTransaction(r2, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity.setComprobanteContratacionView(ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroMovilBodyResponseBean):void");
    }

    public void goToSeleccionarCobertura(CotizacionBean cotizacionBean) {
        c(1);
        this.J.onCreatePage(cotizacionBean);
    }

    private void b() {
        a(1, false);
    }

    private void c() {
        a(0, false);
    }

    private void d() {
        a(2, false);
    }

    public void gotoContratacionSeguro(PlanSeguroBean planSeguroBean) {
        c(3);
        this.K.onCreatePage(planSeguroBean);
    }

    private void e() {
        a(3, false);
    }

    public void gotoConfirmarContratacion() {
        c(4);
        this.L.onCreatePage();
    }

    public void gotoTutorialPatron() {
        c(2);
        this.N.onCreatePage();
    }

    public void gotoComprobanteContratacion(ContratarSeguroMovilBodyResponseBean contratarSeguroMovilBodyResponseBean) {
        c(5);
        this.M.onCreatePage(contratarSeguroMovilBodyResponseBean);
    }

    public void attachView() {
        if (this.mControlPager != null) {
            switch (this.mControlPager.getDisplayedChild()) {
                case 0:
                    if (!this.I.isViewAttached()) {
                        this.I.attachView(this);
                        return;
                    }
                    return;
                case 1:
                    if (!this.J.isViewAttached()) {
                        this.J.attachView(this);
                        return;
                    }
                    return;
                case 2:
                    if (!this.N.isViewAttached()) {
                        this.N.attachView(this);
                        return;
                    }
                    return;
                case 3:
                    if (!this.K.isViewAttached()) {
                        this.K.attachView(this);
                        return;
                    }
                    return;
                case 4:
                    if (!this.L.isViewAttached()) {
                        this.L.attachView(this);
                        return;
                    }
                    return;
                case 5:
                    if (!this.M.isViewAttached()) {
                        this.M.attachView(this);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void showSeleccionarOcupacion(GetOcupacionesBodyResponseBean getOcupacionesBodyResponseBean, String str) {
        this.sessionManager.setListaOcupaciones(getOcupacionesBodyResponseBean);
        Intent intent = new Intent(this, SeleccionarOcupacionAltaSeguroActivity.class);
        intent.putExtra(SegurosConstants.INTENT_EXTRA_OCUPACIONES, (ArrayList) getOcupacionesBodyResponseBean.getOcupaciones().getListOcupaciones());
        intent.putExtra(SegurosConstants.INTENT_EXTRA_OCUPACION, this.H);
        this.messageToShow = "";
        startActivityForResult(intent, 3);
    }

    public void detachView() {
        if (this.I.isViewAttached()) {
            this.I.detachView();
        }
        if (this.J.isViewAttached()) {
            this.J.detachView();
        }
        if (this.N.isViewAttached()) {
            this.N.detachView();
        }
        if (this.K.isViewAttached()) {
            this.K.detachView();
        }
        if (this.L.isViewAttached()) {
            this.L.detachView();
        }
        if (this.M.isViewAttached()) {
            this.M.detachView();
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        String obj = Html.fromHtml(str2).toString();
        try {
            obj = CAccessibility.getInstance(this).applyFilterDateInText(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            obj = CAccessibility.getInstance(this).applyFilterAmount(obj);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            obj = CAccessibility.getInstance(getContext()).applyFilterCharacterToCharacterSpecifict(obj);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        String applyGuion = CAccessibility.getInstance(this).applyGuion(CAccessibility.getInstance(this).applyFilter_BsAs(CAccessibility.getInstance(this).applyFilterPagina(CAccessibility.getInstance(this).applyFilter_IGJ(CAccessibility.getInstance(this).applyFilterSiglasTelefono(CAccessibility.getInstance(this).applyFilterTelephoneMask(CAccessibility.getInstance(this).applyFilterCUIT(CAccessibility.getInstance(this).applyFilterNumeroGradoBarra(CAccessibility.getInstance(this).applyFilter_pisos(obj)))))))));
        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, str);
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str2);
        intent.putExtra(InfoActivity.CONTENT_DESCRIPTION, applyGuion);
        intent.putExtra(InfoActivity.ICON_TO_SHOW, R.drawable.ic_close);
        startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void f() {
        final ArrayList<Tarjeta> arrayList = new ArrayList<>();
        final ArrayList<Cuenta> arrayList2 = new ArrayList<>();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        if (!(this.sessionManager.getLoginUnico().getProductos().getTarjetas() == null || this.sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas() == null || this.sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas().isEmpty())) {
            for (Tarjeta tarjeta : this.sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas()) {
                if (!tarjeta.getClase().equalsIgnoreCase("T") && !tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                    arrayList.add(tarjeta);
                }
            }
        }
        if (!(this.sessionManager.getLoginUnico().getProductos().getCuentas() == null || this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas() == null || this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas().isEmpty())) {
            for (Cuenta cuenta : this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas()) {
                if (!cuenta.getTipo().equals(LoginConstants.TIPO_CUENTA_CJA_AHO_DOLAR) && !cuenta.getTipo().equals(LoginConstants.TIPO_CUENTA_CTA_CTE_DOLAR) && !cuenta.getTipo().equals(LoginConstants.TIPO_CUENTA_CU_DOLAR)) {
                    cuenta.setTipo(cuenta.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU) ? LoginConstants.TIPO_CUENTA_CU_PESOS : cuenta.getTipo());
                    arrayList2.add(cuenta);
                }
            }
        }
        for (Tarjeta mascaraTarjeta : arrayList) {
            String mascaraTarjeta2 = Utils.mascaraTarjeta(mascaraTarjeta);
            arrayList3.add(mascaraTarjeta2);
            try {
                arrayList4.add(this.z.applyFilterCreditCard(mascaraTarjeta2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Cuenta cuenta2 : arrayList2) {
            String abreviatureAndAccountFormat = UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.sessionManager), cuenta2.getTipo(), cuenta2.getNroSuc(), cuenta2.getNumero());
            arrayList3.add(abreviatureAndAccountFormat);
            try {
                arrayList4.add(this.z.applyFilterAccount(abreviatureAndAccountFormat));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpMedioPago", null, null, arrayList3, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, this.aa, arrayList4);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                SeguroMovilActivity.this.contratacionDataMedioPago.setText(str);
                try {
                    SeguroMovilActivity.this.contratacionDataMedioPago.setContentDescription(SeguroMovilActivity.this.z.applyFilterGeneral(SeguroMovilActivity.this.contratacionDataMedioPago.getText().toString()));
                    RelativeLayout relativeLayout = SeguroMovilActivity.this.relativeLayoutMedioDePagoLabel;
                    CAccessibility b2 = SeguroMovilActivity.this.z;
                    StringBuilder sb = new StringBuilder();
                    sb.append(SeguroMovilActivity.this.textViewMedioDePago.getText().toString());
                    sb.append(SeguroMovilActivity.this.z.applyFilterAccount(SeguroMovilActivity.this.contratacionDataMedioPago.getText().toString()));
                    relativeLayout.setContentDescription(b2.applyFilterGeneral(sb.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SeguroMovilActivity.this.V = null;
                SeguroMovilActivity.this.U = null;
                for (Tarjeta tarjeta : arrayList) {
                    if (Utils.mascaraTarjeta(tarjeta).equalsIgnoreCase(str)) {
                        SeguroMovilActivity.this.V = new TarjetaBean(tarjeta.getTipo(), tarjeta.getTipo().equalsIgnoreCase("05") ? tarjeta.getNroTarjetaDebito() : tarjeta.getNroTarjetaCredito(), null);
                    }
                }
                for (Cuenta cuenta : arrayList2) {
                    if (UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(SeguroMovilActivity.this.sessionManager), cuenta.getTipo(), cuenta.getNroSuc(), cuenta.getNumero()).equalsIgnoreCase(str)) {
                        SeguroMovilActivity.this.U = SeguroMovilActivity.this.a(cuenta);
                    }
                }
                SeguroMovilActivity.this.aa = str;
                SeguroMovilActivity.this.h();
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        h();
        newInstance.show(getSupportFragmentManager(), "popUpMedioPago");
    }

    /* access modifiers changed from: private */
    public void g() {
        ArrayList arrayList = new ArrayList();
        if (!this.O.getListaSeguros().isEmpty()) {
            for (SeguroBean seguroBean : this.O.getListaSeguros()) {
                if (!seguroBean.getCodRamo().equalsIgnoreCase(CodRamo.PROTECCION_CARTERA) && seguroBean.getIdDispositivo() != null && !seguroBean.getIdDispositivo().isEmpty()) {
                    arrayList.add(seguroBean.getPropietario());
                }
            }
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpDispAnular", null, null, arrayList, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, null, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                SeguroMovilActivity.this.contratacionDispositivoAAnular.setText(str);
                for (SeguroBean seguroBean : SeguroMovilActivity.this.O.getListaSeguros()) {
                    if (seguroBean.getPropietario().equalsIgnoreCase(str)) {
                        SeguroMovilActivity.this.R = seguroBean;
                    }
                }
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        h();
        newInstance.show(getSupportFragmentManager(), "popUpDispAnular");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent) && i == 3) {
            if (i2 == -1 && intent != null) {
                OcupacionBean ocupacionBean = (OcupacionBean) intent.getParcelableExtra(SegurosConstants.INTENT_EXTRA_OCUPACION);
                this.contratacionDataOcupacion.setText(Html.fromHtml(ocupacionBean.getDescOcupacion()));
                this.H = ocupacionBean;
            }
            h();
        }
        if (i == 5 && i2 == -1) {
            String stringExtra = intent.getStringExtra(ResultsKeys.SCREEN_RESULT_VALUE);
            if (stringExtra.equals(ResultValues.CLEARED)) {
                this.messageToShow = "No podemos asegurar este dispositivo.\nDetectamos una falla en la pantalla de tu celular.";
                dispositivoNoAsegurable("Lo sentimos", this.messageToShow);
            } else if (stringExtra.equals(ResultValues.FAIL)) {
                this.messageToShow = "No podemos asegurar este dispositivo.\nDetectamos una falla en la pantalla de tu celular.";
                dispositivoNoAsegurable("Lo sentimos", this.messageToShow);
            } else if (stringExtra.equals(ResultValues.OK)) {
                this.sessionManager.setValidateDisplayOK(true);
                irContratacionSeguro();
            }
        }
    }

    public void irContratacionSeguro() {
        PlanSeguroBean planSeguroBean = new PlanSeguroBean();
        for (PlanSeguroBean planSeguroBean2 : this.ab) {
            if (planSeguroBean2.isChecked()) {
                planSeguroBean = planSeguroBean2;
            }
        }
        this.S = planSeguroBean;
        gotoContratacionSeguro(planSeguroBean);
    }

    /* access modifiers changed from: private */
    public void h() {
        if (this.contratacionDataPropietario.getText().toString().isEmpty() || this.contratacionDataPropietario.getText().toString().replaceAll(UtilsCuentas.SEPARAOR2, "").isEmpty() || this.contratacionDataMedioPago.getText().toString().equalsIgnoreCase(getString(R.string.F27_22_lbl_medio_pago_seleccionar)) || this.contratacionDataOcupacion.getText().toString().equalsIgnoreCase(getString(R.string.F27_22_lbl_medio_pago_seleccionar))) {
            this.btnContinuarContratacion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris));
            this.btnContinuarContratacion.setEnabled(false);
            return;
        }
        this.btnContinuarContratacion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        this.btnContinuarContratacion.setEnabled(true);
        if (this.Q.equalsIgnoreCase(TipoAlta.CAMBIO_DISPOSITIVO) && this.contratacionDispositivoAAnular.getText().toString().equalsIgnoreCase(getString(R.string.F27_22_lbl_medio_pago_seleccionar))) {
            this.btnContinuarContratacion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris));
            this.btnContinuarContratacion.setEnabled(false);
        }
        this.btnContinuarContratacion.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SeguroMovilActivity.this.hideKeyboard();
                if (!SeguroMovilActivity.this.Q.equals(TipoAlta.CAMBIO_DISPOSITIVO) && !SeguroMovilActivity.this.Q.equals(TipoAlta.ALTA_DISPOSITIVO) && ((!SeguroMovilActivity.this.Q.equals(TipoAlta.DISPOSITIVO_ASEGURADO) && !SeguroMovilActivity.this.Q.equals(TipoAlta.SEGURO_CARTERA)) || SeguroMovilActivity.this.O.getListaSeguros().isEmpty())) {
                    SeguroMovilActivity.this.gotoConfirmarContratacion();
                } else if (SeguroMovilActivity.this.i()) {
                    SeguroMovilActivity.this.gotoConfirmarContratacion();
                } else {
                    SeguroMovilActivity.this.j();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(Boolean bool) {
        if (bool.booleanValue()) {
            this.btnConfirmarContratacion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
            this.btnConfirmarContratacion.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SeguroMovilActivity.this.L.showConfirmarMovilDialog(SeguroMovilActivity.this.Q.equalsIgnoreCase(TipoAlta.ALTA_DISPOSITIVO) ? "A" : "R", CodRamo.SEGURO_MOVIL, SeguroMovilActivity.this.T.getCodProducto(), SeguroMovilActivity.this.T.getNumCotizacion(), SeguroMovilActivity.this.S.getCodPlan(), SeguroMovilActivity.this.confDataPropietario.getText().toString(), SeguroMovilActivity.this.H.getCodOcupacion(), SeguroMovilActivity.this.y, SeguroMovilActivity.this.U, SeguroMovilActivity.this.V);
                }
            });
            return;
        }
        this.btnConfirmarContratacion.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris));
        this.btnConfirmarContratacion.setOnClickListener(null);
    }

    /* access modifiers changed from: private */
    public boolean i() {
        if (this.Q.equals(TipoAlta.CAMBIO_DISPOSITIVO)) {
            if (this.contratacionDispositivoAAnular.getText().toString().equals(this.contratacionDataPropietario.getText().toString()) || !a(this.O)) {
                return true;
            }
        } else if (this.Q.equals(TipoAlta.ALTA_DISPOSITIVO)) {
            if (this.O.getListaSeguros().isEmpty() || b(this.O) || !a(this.O)) {
                return true;
            }
        } else if ((this.Q.equals(TipoAlta.DISPOSITIVO_ASEGURADO) || this.Q.equals(TipoAlta.SEGURO_CARTERA)) && (b(this.O) || !a(this.O))) {
            return true;
        }
        return false;
    }

    private boolean a(SegurosBean segurosBean) {
        boolean z2 = false;
        for (int i = 0; i < segurosBean.getListaSeguros().size(); i++) {
            if (((SeguroBean) segurosBean.getListaSeguros().get(i)).getPropietario().equalsIgnoreCase(this.contratacionDataPropietario.getText().toString())) {
                z2 = true;
            }
        }
        return z2;
    }

    private boolean b(SegurosBean segurosBean) {
        boolean z2 = false;
        for (int i = 0; i < segurosBean.getListaSeguros().size(); i++) {
            if (((SeguroBean) segurosBean.getListaSeguros().get(i)).getIdDispositivo().equals(this.w.getKeyUniqueID()) && ((SeguroBean) segurosBean.getListaSeguros().get(i)).getPropietario().equalsIgnoreCase(this.contratacionDataPropietario.getText().toString())) {
                z2 = true;
            }
        }
        return z2;
    }

    /* access modifiers changed from: private */
    public void j() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("errorPropietarioDialog", getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), "Ya existe un \"Propietario\" con ese nombre. Por favor, ingresá otro.", getString(R.string.ID1_ALERT_BTN_ACCEPT));
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
        newInstance.show(getSupportFragmentManager(), "errorPropietarioDialog");
    }

    public void onBackPressed() {
        String str = null;
        if (!this.P && this.O != null) {
            for (SeguroBean seguroBean : this.O.getListaSeguros()) {
                if (seguroBean.getCodRamo().equalsIgnoreCase(CodRamo.SEGURO_MOVIL)) {
                    str = seguroBean.getCodRamo();
                }
            }
        }
        int id2 = this.mControlPager.getCurrentView().getId();
        if (id2 != R.id.tutorial_patron) {
            switch (id2) {
                case R.id.contratar_seguro_movil /*2131364429*/:
                    setResult(0);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return;
                case R.id.contratar_seguro_movil_comprobante /*2131364430*/:
                    if (!this.F) {
                        this.E.showAlert();
                        return;
                    }
                    setResult(-1);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return;
                case R.id.contratar_seguro_movil_confirmar /*2131364431*/:
                    e();
                    return;
                case R.id.contratar_seguro_movil_contratacion /*2131364432*/:
                    if (o()) {
                        d();
                        return;
                    } else {
                        b();
                        return;
                    }
                case R.id.contratar_seguro_movil_seleccion_plan /*2131364433*/:
                    if (this.Q.equalsIgnoreCase(TipoAlta.DISPOSITIVO_ASEGURADO) || this.Q.equalsIgnoreCase(TipoAlta.SEGURO_CARTERA) || str == null) {
                        finish();
                        return;
                    } else {
                        c();
                        return;
                    }
                default:
                    return;
            }
        } else {
            b();
        }
    }

    public void endActivity() {
        setResult(-1);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void k() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!SeguroMovilActivity.this.F) {
                        SeguroMovilActivity.this.E.showAlert();
                    } else {
                        SeguroMovilActivity.this.switchDrawer();
                    }
                }
            });
        }
        l();
    }

    private void l() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.menu);
        findViewById.setContentDescription(getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        n();
        findViewById.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SeguroMovilActivity.this.Q.equalsIgnoreCase(TipoAlta.ALTA_DISPOSITIVO)) {
                    SeguroMovilActivity.this.p.trackEvent(SeguroMovilActivity.this.getString(R.string.analytics_trackevent_category_seguros), SeguroMovilActivity.this.getString(R.string.analytics_trackevent_action_contratacion_seguro_movil), SeguroMovilActivity.this.getString(R.string.analytics_trackevent_label_ver_poliza_compartir_comprobante_descargar_comprobante_cancelar));
                } else {
                    SeguroMovilActivity.this.p.trackEvent(SeguroMovilActivity.this.getString(R.string.analytics_trackevent_category_seguros), SeguroMovilActivity.this.getString(R.string.analytics_trackevent_action_contratacion_nuevo_seguro_movil), SeguroMovilActivity.this.getString(R.string.analytics_trackevent_label_ver_poliza_compartir_comprobante_descargar_comprobante_cancelar));
                }
                SeguroMovilActivity.this.G.show(SeguroMovilActivity.this.getSupportFragmentManager(), "Dialog");
                SeguroMovilActivity.this.F = true;
            }
        });
    }

    private OptionsToShare m() {
        this.F = true;
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public String getSubjectReceiptToShare() {
                return "Comprobante de Alta Seguro";
            }

            public View getViewToShare() {
                return SeguroMovilActivity.this.viewComprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                SeguroMovilActivity.this.startActivity(Intent.createChooser(intent, SeguroMovilActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                String string = SeguroMovilActivity.this.getString(R.string.F27_10_COMPROBANTE_CONTRATACION_SEGURO_MOVIL_FILENAME);
                StringBuilder sb = new StringBuilder();
                sb.append(" - ");
                sb.append(SeguroMovilActivity.this.W);
                return Html.fromHtml(string.concat(sb.toString())).toString();
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                SeguroMovilActivity.this.F = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                SeguroMovilActivity.this.F = true;
            }

            public void onOption1Button() {
                if (ContextCompat.checkSelfPermission(SeguroMovilActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    showRequestPermission(1);
                } else {
                    super.onOption1Button();
                }
            }

            public void onOption2Button() {
                super.onOption2Button();
            }

            public void onOption3Button() {
                if (ContextCompat.checkSelfPermission(SeguroMovilActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    showRequestPermission(1);
                } else {
                    super.onOption3Button();
                }
            }

            public void onAbortShare() {
                super.onAbortShare();
                SeguroMovilActivity.this.F = true;
                SeguroMovilActivity.this.onBackPressed();
            }
        };
    }

    private void n() {
        this.E = m();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.ID_4106_SEGUROS_LBL_VER_POLIZA));
        arrayList.add(getResources().getString(R.string.ID_4107_SEGUROS_LBL_COMP_COMPRO));
        arrayList.add(getResources().getString(R.string.ID_4108_SEGUROS_LBL_DESC_COMPRO));
        this.G = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.G.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(SeguroMovilActivity.this.getResources().getString(R.string.ID_4107_SEGUROS_LBL_COMP_COMPRO))) {
                    SeguroMovilActivity.this.E.optionShareSelected();
                } else if (str.equalsIgnoreCase(SeguroMovilActivity.this.getResources().getString(R.string.ID_4108_SEGUROS_LBL_DESC_COMPRO))) {
                    SeguroMovilActivity.this.E.optionDownloadSelected();
                } else if (!str.equalsIgnoreCase(SeguroMovilActivity.this.getResources().getString(R.string.ID_4106_SEGUROS_LBL_VER_POLIZA))) {
                } else {
                    if (ContextCompat.checkSelfPermission(SeguroMovilActivity.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        SeguroMovilActivity.this.showRequestPermissionExplation(1);
                    } else {
                        SeguroMovilActivity.this.M.getPoliza(CodRamo.SEGURO_MOVIL, SeguroMovilActivity.this.X, SeguroMovilActivity.this.W);
                    }
                }
            }
        });
        this.G.setCancelable(true);
    }

    public boolean canExit() {
        if (this.F) {
            switchDrawer();
        } else if (ContextCompat.checkSelfPermission(getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            showRequestPermissionExplation(1);
        } else {
            new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public String getSubjectReceiptToShare() {
                    return "Comprobante de Alias";
                }

                public View getViewToShare() {
                    return SeguroMovilActivity.this.viewComprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    SeguroMovilActivity.this.startActivity(Intent.createChooser(intent, SeguroMovilActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    return "Comprobante Seguro - ".concat(SeguroMovilActivity.this.W);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    SeguroMovilActivity.this.F = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    SeguroMovilActivity.this.F = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    SeguroMovilActivity.this.F = true;
                }
            }.showAlert();
        }
        return this.F;
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.p;
    }

    /* access modifiers changed from: private */
    public CuentaShortBean a(Cuenta cuenta) {
        return new CuentaShortBean(cuenta.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU) ? LoginConstants.TIPO_CUENTA_CU_PESOS : cuenta.getTipo(), cuenta.getNroSuc(), cuenta.getNumero());
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.E.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void showRequestPermissionExplation(final int i) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                if (VERSION.SDK_INT >= 23) {
                    SeguroMovilActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i);
                }
            }
        });
        newInstance.show(getSupportFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    public void initTutorial() {
        irContratacionSeguro();
    }

    private boolean o() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService("accessibility");
        return accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();
    }

    public static void setUpLabel(String str, RelativeLayout relativeLayout, TextView textView) {
        setUpLabel(str, relativeLayout, textView, (String) null, (Context) null);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setUpLabel(java.lang.String r1, android.widget.RelativeLayout r2, android.widget.TextView r3, java.lang.String r4, android.content.Context r5) {
        /*
            if (r1 != 0) goto L_0x0009
            r1 = 8
            r2.setVisibility(r1)
            goto L_0x00a6
        L_0x0009:
            r0 = 0
            r2.setVisibility(r0)
            r3.setText(r1)
            if (r4 == 0) goto L_0x00a6
            if (r5 == 0) goto L_0x00a6
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r5)
            r2 = -1
            int r5 = r4.hashCode()     // Catch:{ Exception -> 0x00a6 }
            switch(r5) {
                case -436740361: goto L_0x0048;
                case 2090926: goto L_0x003e;
                case 523665477: goto L_0x0034;
                case 790540518: goto L_0x002a;
                case 1934443608: goto L_0x0021;
                default: goto L_0x0020;
            }     // Catch:{ Exception -> 0x00a6 }
        L_0x0020:
            goto L_0x0052
        L_0x0021:
            java.lang.String r5 = "AMOUNT"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00a6 }
            if (r4 == 0) goto L_0x0052
            goto L_0x0053
        L_0x002a:
            java.lang.String r5 = "LEYENDA"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00a6 }
            if (r4 == 0) goto L_0x0052
            r0 = 4
            goto L_0x0053
        L_0x0034:
            java.lang.String r5 = "CHARACTERTOCHARACTER"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00a6 }
            if (r4 == 0) goto L_0x0052
            r0 = 1
            goto L_0x0053
        L_0x003e:
            java.lang.String r5 = "DATE"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00a6 }
            if (r4 == 0) goto L_0x0052
            r0 = 2
            goto L_0x0053
        L_0x0048:
            java.lang.String r5 = "PERCENTAJE"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x00a6 }
            if (r4 == 0) goto L_0x0052
            r0 = 3
            goto L_0x0053
        L_0x0052:
            r0 = -1
        L_0x0053:
            switch(r0) {
                case 0: goto L_0x0097;
                case 1: goto L_0x0087;
                case 2: goto L_0x0077;
                case 3: goto L_0x0067;
                case 4: goto L_0x0057;
                default: goto L_0x0056;
            }     // Catch:{ Exception -> 0x00a6 }
        L_0x0056:
            goto L_0x00a6
        L_0x0057:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r1 = r1.applyFilterSellado(r2)     // Catch:{ Exception -> 0x00a6 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00a6
        L_0x0067:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r1 = r1.applyFilterTasaValue(r2)     // Catch:{ Exception -> 0x00a6 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00a6
        L_0x0077:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r1 = r1.applyFilterDate(r2)     // Catch:{ Exception -> 0x00a6 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00a6
        L_0x0087:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x00a6 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00a6
        L_0x0097:
            java.lang.CharSequence r2 = r3.getText()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r1 = r1.applyFilterAmount(r2)     // Catch:{ Exception -> 0x00a6 }
            r3.setContentDescription(r1)     // Catch:{ Exception -> 0x00a6 }
        L_0x00a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity.setUpLabel(java.lang.String, android.widget.RelativeLayout, android.widget.TextView, java.lang.String, android.content.Context):void");
    }

    public static void setUpLabel(String str, LinearLayout linearLayout, TextView textView) {
        setUpLabel(str, linearLayout, textView, (String) null, (Context) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0060 A[Catch:{ Exception -> 0x00a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0061 A[Catch:{ Exception -> 0x00a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0071 A[Catch:{ Exception -> 0x00a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0081 A[Catch:{ Exception -> 0x00a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0091 A[Catch:{ Exception -> 0x00a6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setUpLabel(java.lang.String r2, android.widget.LinearLayout r3, android.widget.TextView r4, java.lang.String r5, android.content.Context r6) {
        /*
            if (r2 == 0) goto L_0x00a1
            java.lang.String r0 = ""
            boolean r0 = r2.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x000c
            goto L_0x00a1
        L_0x000c:
            r0 = 0
            r3.setVisibility(r0)
            r4.setText(r2)
            if (r5 == 0) goto L_0x00a6
            if (r6 == 0) goto L_0x00a6
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r2 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r6)
            r3 = -1
            int r6 = r5.hashCode()     // Catch:{ Exception -> 0x00a6 }
            r1 = -436740361(0xffffffffe5f7def7, float:-1.4631719E23)
            if (r6 == r1) goto L_0x0052
            r1 = 2090926(0x1fe7ae, float:2.930011E-39)
            if (r6 == r1) goto L_0x0048
            r1 = 523665477(0x1f368045, float:3.86461E-20)
            if (r6 == r1) goto L_0x003e
            r1 = 1934443608(0x734d4458, float:1.6262925E31)
            if (r6 == r1) goto L_0x0035
            goto L_0x005c
        L_0x0035:
            java.lang.String r6 = "AMOUNT"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x00a6 }
            if (r5 == 0) goto L_0x005c
            goto L_0x005d
        L_0x003e:
            java.lang.String r6 = "CHARACTERTOCHARACTER"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x00a6 }
            if (r5 == 0) goto L_0x005c
            r0 = 1
            goto L_0x005d
        L_0x0048:
            java.lang.String r6 = "DATE"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x00a6 }
            if (r5 == 0) goto L_0x005c
            r0 = 2
            goto L_0x005d
        L_0x0052:
            java.lang.String r6 = "PERCENTAJE"
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x00a6 }
            if (r5 == 0) goto L_0x005c
            r0 = 3
            goto L_0x005d
        L_0x005c:
            r0 = -1
        L_0x005d:
            switch(r0) {
                case 0: goto L_0x0091;
                case 1: goto L_0x0081;
                case 2: goto L_0x0071;
                case 3: goto L_0x0061;
                default: goto L_0x0060;
            }     // Catch:{ Exception -> 0x00a6 }
        L_0x0060:
            goto L_0x00a6
        L_0x0061:
            java.lang.CharSequence r3 = r4.getText()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = r2.applyFilterTasaValue(r3)     // Catch:{ Exception -> 0x00a6 }
            r4.setContentDescription(r2)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00a6
        L_0x0071:
            java.lang.CharSequence r3 = r4.getText()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = r2.applyFilterDate(r3)     // Catch:{ Exception -> 0x00a6 }
            r4.setContentDescription(r2)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00a6
        L_0x0081:
            java.lang.CharSequence r3 = r4.getText()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = r2.applyFilterCharacterToCharacter(r3)     // Catch:{ Exception -> 0x00a6 }
            r4.setContentDescription(r2)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00a6
        L_0x0091:
            java.lang.CharSequence r3 = r4.getText()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = r2.applyFilterAmount(r3)     // Catch:{ Exception -> 0x00a6 }
            r4.setContentDescription(r2)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00a6
        L_0x00a1:
            r2 = 8
            r3.setVisibility(r2)
        L_0x00a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity.setUpLabel(java.lang.String, android.widget.LinearLayout, android.widget.TextView, java.lang.String, android.content.Context):void");
    }
}
