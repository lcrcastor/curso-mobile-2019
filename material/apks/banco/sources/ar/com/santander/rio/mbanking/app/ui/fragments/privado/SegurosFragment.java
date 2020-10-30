package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.TableViewRowCreator;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.module.seguros.DetalleAsistenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.DetalleAsistenciaView;
import ar.com.santander.rio.mbanking.app.module.seguros.DetalleSeguroPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.DetalleSeguroView;
import ar.com.santander.rio.mbanking.app.module.seguros.SeguroFamiliaObjetosPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.SeguroFamiliaObjetosView;
import ar.com.santander.rio.mbanking.app.module.seguros.SolicitarAsistenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.SolicitarAsistenciaView;
import ar.com.santander.rio.mbanking.app.module.seguros.TenenciaSegurosPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.TenenciaSegurosView;
import ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SeguroObjetosActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.CodRamo;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.TipoAlta;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetFirmaSeguroEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AsistenciaSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AsistenciasSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatoSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSeguroBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroObjetoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFirmaSeguros;
import ar.com.santander.rio.mbanking.utils.UtilDeviceInfo;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultsKeys;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.TestBatteryActivity;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import javax.inject.Inject;

public class SegurosFragment extends BaseMvpFragment implements DetalleAsistenciaView, DetalleSeguroView, SeguroFamiliaObjetosView, SolicitarAsistenciaView, TenenciaSegurosView {
    public static final String ACCESS_PHONE_STATUS_DIALOG_TAG = "ACCESS_PHONE_STATUS_DIALOG_TAG";
    public static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 1455;
    @Inject
    AnalyticsManager a;
    private boolean ad = false;
    private SegurosBean ae = new SegurosBean();
    private boolean af = false;
    /* access modifiers changed from: private */
    public SegurosBean ag = new SegurosBean();
    private SeguroBean ah = null;
    /* access modifiers changed from: private */
    public int ai = 0;
    private String aj = "";
    private String ak;
    private SeguroFamiliaObjetosPresenter al;
    @Inject
    SettingsManager b;
    @InjectView(2131363397)
    Button btnContratarSeguro;
    @InjectView(2131363670)
    Button btnLlamarAsistencia;
    @InjectView(2131363421)
    Button btnSolicitarAsistencia;
    @Inject
    SessionManager c;
    /* access modifiers changed from: private */
    public Boolean d = Boolean.valueOf(false);
    private TenenciaSegurosPresenter e;
    /* access modifiers changed from: private */
    public DetalleSeguroPresenter f;
    private SolicitarAsistenciaPresenter g;
    private DetalleAsistenciaPresenter h;
    /* access modifiers changed from: private */
    public String i;
    @InjectView(2131363398)
    ImageView icoSinMedioPago;
    @InjectView(2131363422)
    TextView lblAseguradoraDetalle;
    @InjectView(2131363423)
    TextView lblCuotaDetalle;
    @InjectView(2131363424)
    TextView lblDataEmail;
    @InjectView(2131363428)
    TextView lblDataOcupacion;
    @InjectView(2131363410)
    LinearLayout lblDispositivoNoAsegurado;
    @InjectView(2131363425)
    TextView lblFechaInicioDetalle;
    @InjectView(2131363426)
    TextView lblFormaPagoDetalle;
    @InjectView(2131363403)
    TextView lblLinkDescripcion;
    @InjectView(2131363404)
    TextView lblMasInformacion;
    @InjectView(2131363427)
    TextView lblMedioPagoDetalle;
    @InjectView(2131363429)
    TextView lblPolizaDetalle;
    @InjectView(2131363430)
    TextView lblSeparadorDatosDetalle;
    @InjectView(2131363405)
    TextView lblSinMedioPago;
    @InjectView(2131363406)
    TextView lblSinSeguros;
    @InjectView(2131363431)
    TextView lblSumaAseguradaDetalle;
    @InjectView(2131363671)
    TextView lblTelefonoAsistencia;
    @InjectView(2131363432)
    TextView lblTipoPoliza;
    @InjectView(2131363433)
    TextView lblTituloDetalle;
    @InjectView(2131364084)
    LinearLayout ll_email;
    @InjectView(2131364085)
    LinearLayout ll_ocupaciones;
    @InjectView(2131363409)
    LinearLayout lnlHeaderTenenciaSeguros;
    @InjectView(2131363435)
    LinearLayout lnlWrapperDatosDetalle;
    @InjectView(2131365674)
    ViewFlipper mControlPager;
    @InjectView(2131363436)
    LinearLayout rowCuota;
    @InjectView(2131363437)
    LinearLayout rowFechaInicio;
    @InjectView(2131363420)
    View rowMedioPago;
    @InjectView(2131363438)
    LinearLayout rowTipoPoliza;
    @InjectView(2131363439)
    ScrollView scrollViewDetalle;
    public SeguroBean seguroSeleccionado;
    @InjectView(2131363411)
    View separatorLeyenda;
    @InjectView(2131363669)
    TableLayout tblAsistencias;
    @InjectView(2131363441)
    TableLayout tblDatosDetalle;
    @InjectView(2131363419)
    TableLayout tblTenenciaSeguros;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public static SegurosFragment getInstance(String str) {
        SegurosFragment segurosFragment = new SegurosFragment();
        segurosFragment.ak = str;
        return segurosFragment;
    }

    public void onCreate(Bundle bundle) {
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mRootView = getActivity().getLayoutInflater().inflate(R.layout.seguros_fragment_main, viewGroup, false);
        ButterKnife.inject((Object) this, this.mRootView);
        if (this.ah != null) {
            a(this.ah, this.ag.getUrlSiniestro());
        } else {
            initialize();
        }
        return this.mRootView;
    }

    public void initialize() {
        this.e = new TenenciaSegurosPresenter(this.mBus, this.mDataManager, getActivity(), this.c);
        this.f = new DetalleSeguroPresenter(this.mBus, this.mDataManager, getActivity());
        this.g = new SolicitarAsistenciaPresenter(this.mBus, this.mDataManager);
        this.h = new DetalleAsistenciaPresenter(this.mBus, this.mDataManager);
        this.al = new SeguroFamiliaObjetosPresenter(this.mBus, this.mDataManager, getContext());
        this.e.attachView(this);
        this.e.consultarTenenciaSeguros();
    }

    private void y() {
        if (this.ak != null) {
            String str = this.ak;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -2133810652) {
                if (hashCode != -2020028725) {
                    if (hashCode != 2052559) {
                        if (hashCode == 1734766764 && str.equals(FragmentConstants.SEGURO_WEB_VIEW_VIVIENDA)) {
                            c2 = 3;
                        }
                    } else if (str.equals(FragmentConstants.SEGURO_WEB_VIEW_AUTO)) {
                        c2 = 2;
                    }
                } else if (str.equals(FragmentConstants.SEGURO_OBJETO)) {
                    c2 = 1;
                }
            } else if (str.equals(FragmentConstants.SEGURO_PROTECCION_MOVIL)) {
                c2 = 0;
            }
            switch (c2) {
                case 0:
                    G();
                    return;
                case 1:
                    z();
                    this.al.obtenerFamiliaObjetos();
                    return;
                case 2:
                case 3:
                    if (this.btnContratarSeguro.getVisibility() == 0) {
                        goToContratarSeguro(this.ag, this.i);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void z() {
        if (!this.al.isViewAttached()) {
            this.al.attachView(this);
        }
        this.al.onCreatePage();
    }

    public void setBackgroundVisibleInit() {
        this.mControlPager.setVisibility(0);
    }

    public void onBackPressed() {
        if (this.mControlPager == null) {
            return;
        }
        if (this.mControlPager.getDisplayedChild() == 0) {
            switchDrawer();
        } else if (this.mControlPager.getDisplayedChild() != 3 || !this.d.booleanValue()) {
            this.ah = null;
            a(this.mControlPager.getDisplayedChild() - 1, false);
        } else {
            a(1, false);
        }
    }

    public void configureActionBar() {
        if (this.mControlPager != null) {
            switch (this.mControlPager.getDisplayedChild()) {
                case 0:
                    this.e.consultarTenenciaSeguros();
                    a(this.ag);
                    return;
                case 1:
                    if (this.seguroSeleccionado == null || !this.seguroSeleccionado.getCuota().equalsIgnoreCase("-")) {
                        B();
                        return;
                    } else {
                        C();
                        return;
                    }
                case 2:
                    D();
                    return;
                case 3:
                    if (!this.d.booleanValue()) {
                        C();
                        return;
                    } else {
                        E();
                        return;
                    }
                default:
                    return;
            }
        }
    }

    private void a(final SegurosBean segurosBean) {
        if (segurosBean.getUrlSiniestro() == null && segurosBean.getUrlSeguimiento() == null) {
            A();
            return;
        }
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SegurosFragment.this.switchDrawer();
                }
            });
        }
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        SegurosFragment.this.b(segurosBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void A() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        View findViewById = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SegurosFragment.this.switchDrawer();
                }
            });
        }
    }

    private void B() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_WITH_MENU);
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        View findViewById = customView.findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SegurosFragment.this.onBackPressed();
                }
            });
        }
        View findViewById2 = customView.findViewById(R.id.menu);
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(SegurosFragment.this.getActivity(), "android.permission.READ_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(SegurosFragment.this.getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        SegurosFragment.this.f.showDetalleSeguroMenuOptions();
                        return;
                    }
                    SegurosFragment.this.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, SegurosFragment.MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
                }
            });
        }
    }

    private void C() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        View findViewById = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SegurosFragment.this.onBackPressed();
                }
            });
        }
    }

    private void D() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.PUSH_CLOSE);
        View findViewById = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.ok);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SegurosFragment.this.a.trackEvent(SegurosFragment.this.getString(R.string.analytics_trackevent_category_seguros), SegurosFragment.this.getString(R.string.analytics_trackevent_action_cerrar_pantalla), SegurosFragment.this.getString(R.string.analytics_trackevent_label_asistencia_tipo_seguro));
                    SegurosFragment.this.onBackPressed();
                }
            });
        }
    }

    private void E() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.PUSH_CLOSE);
        View findViewById = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.ok);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SegurosFragment.this.a.trackEvent(SegurosFragment.this.getString(R.string.analytics_trackevent_category_seguros), SegurosFragment.this.getString(R.string.analytics_trackevent_action_cerrar_pantalla), SegurosFragment.this.getString(R.string.analytics_trackevent_label_asistencia_general));
                    SegurosFragment.this.onBackPressed();
                }
            });
        }
    }

    public static String getTipoAltaSeguroMovil(LinkSeguroBean linkSeguroBean) {
        String str = TipoAlta.NO_ASEGURABLE;
        if (linkSeguroBean == null) {
            return str;
        }
        String opcion = linkSeguroBean.getOpcion();
        char c2 = 65535;
        int hashCode = opcion.hashCode();
        if (hashCode != 2160) {
            if (hashCode != 2495) {
                switch (hashCode) {
                    case 2609:
                        if (opcion.equals(SegurosConstants.LINK_OPCION_RC)) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case 2610:
                        if (opcion.equals(SegurosConstants.LINK_OPCION_RD)) {
                            c2 = 2;
                            break;
                        }
                        break;
                }
            } else if (opcion.equals("NM")) {
                c2 = 3;
            }
        } else if (opcion.equals(SegurosConstants.LINK_OPCION_CS)) {
            c2 = 0;
        }
        switch (c2) {
            case 0:
                return TipoAlta.ALTA_DISPOSITIVO;
            case 1:
                return TipoAlta.DISPOSITIVO_ASEGURADO;
            case 2:
                return TipoAlta.ALTA_O_CAMBIO_DISPOSITIVO;
            case 3:
                return TipoAlta.SEGURO_CARTERA;
            default:
                return str;
        }
    }

    public void setTenenciaSegurosView(Boolean bool, final SegurosBean segurosBean, LinkSeguroBean linkSeguroBean) {
        int i2;
        this.ag = segurosBean;
        this.tblTenenciaSeguros.removeAllViews();
        if (segurosBean != null) {
            c(segurosBean);
        }
        if (bool.booleanValue()) {
            if (segurosBean != null) {
                this.i = getTipoAltaSeguroMovil(linkSeguroBean);
            }
            this.icoSinMedioPago.setVisibility(8);
            this.lblSinMedioPago.setVisibility(8);
            this.btnContratarSeguro.setVisibility(0);
            this.separatorLeyenda.setVisibility(8);
            if ((segurosBean == null || segurosBean.getListaSeguros() == null || segurosBean.getListaSeguros().isEmpty()) && (segurosBean.getListaObjetos() == null || segurosBean.getListaObjetos().isEmpty())) {
                this.a.trackScreen(getString(R.string.analytics_screen_Tenencia_seguros_sin_seguros_contratados));
                this.lnlHeaderTenenciaSeguros.setVisibility(8);
                this.tblTenenciaSeguros.setVisibility(8);
                this.lblSinSeguros.setVisibility(0);
            } else {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService("layout_inflater");
                this.lnlHeaderTenenciaSeguros.setVisibility(0);
                this.tblTenenciaSeguros.setVisibility(0);
                this.lblSinSeguros.setVisibility(8);
                if (segurosBean.getUrlSiniestro() == null && segurosBean.getUrlSeguimiento() == null) {
                    A();
                } else {
                    a(segurosBean);
                }
                this.i = getTipoAltaSeguroMovil(linkSeguroBean);
                this.a.trackScreen(getString(R.string.analytics_screen_Tenencia_seguros_con_seguros_contratados));
                if (segurosBean.getListaSeguros() == null || segurosBean.getListaSeguros().isEmpty()) {
                    i2 = 0;
                } else {
                    i2 = 0;
                    for (SeguroBean a2 : segurosBean.getListaSeguros()) {
                        a(segurosBean, layoutInflater, i2, a2);
                        i2++;
                    }
                }
                if (segurosBean.getListaObjetos() != null && !segurosBean.getListaObjetos().isEmpty()) {
                    RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.list_header_tenencia_creditos, null);
                    TextView textView = (TextView) relativeLayout.findViewById(R.id.textViewTittleListTenenciaCreditos);
                    textView.setText(R.string.ID_4790_SEGUROS_TIT_SEGURO_DE_OBJETOS);
                    textView.setTextSize(13.0f);
                    this.tblTenenciaSeguros.addView(relativeLayout);
                    for (SeguroBean a3 : segurosBean.getListaObjetos()) {
                        a(segurosBean, layoutInflater, i2, a3);
                        i2++;
                    }
                }
            }
            if (this.i == null) {
                this.i = TipoAlta.ALTA_O_CAMBIO_DISPOSITIVO;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            float f2 = ((float) displayMetrics.heightPixels) / displayMetrics.ydpi;
            float f3 = ((float) displayMetrics.widthPixels) / displayMetrics.xdpi;
            double sqrt = Math.sqrt((double) ((f3 * f3) + (f2 * f2)));
            if (segurosBean.getLinkSeguroBean().getOpcion().equals("NM") || sqrt >= 7.0d) {
                this.lblDispositivoNoAsegurado.setVisibility(8);
            } else {
                this.lblLinkDescripcion.setText(Html.fromHtml(segurosBean.getLinkSeguroBean().getResDesc()));
                this.lblDispositivoNoAsegurado.setVisibility(0);
                if (segurosBean.getLinkSeguroBean().getOpcion().equals(SegurosConstants.LINK_OPCION_CS) || segurosBean.getLinkSeguroBean().getOpcion().equals(SegurosConstants.LINK_OPCION_RC)) {
                    this.lblMasInformacion.setText(getContext().getString(R.string.F24_11_LBL_CONOCE_MAS));
                } else if (segurosBean.getLinkSeguroBean().getOpcion().equals(SegurosConstants.LINK_OPCION_RD)) {
                    this.lblMasInformacion.setText(getContext().getString(R.string.F24_11_LBL_HACELO_AHORA_DESDE_AQUI));
                }
                this.lblMasInformacion.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SegurosFragment.this.verifyBatery();
                    }
                });
            }
            this.btnContratarSeguro.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SegurosFragment.this.goToContratarSeguro(segurosBean, SegurosFragment.this.i);
                }
            });
        } else {
            this.lnlHeaderTenenciaSeguros.setVisibility(8);
            this.tblTenenciaSeguros.setVisibility(8);
            this.lblSinSeguros.setVisibility(8);
            this.btnContratarSeguro.setVisibility(8);
            this.lblDispositivoNoAsegurado.setVisibility(8);
            this.icoSinMedioPago.setVisibility(0);
            this.lblSinMedioPago.setVisibility(0);
        }
        y();
    }

    private void a(final SegurosBean segurosBean, @NonNull LayoutInflater layoutInflater, int i2, SeguroBean seguroBean) {
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.list_item_seguro, null);
        TextView textView = (TextView) linearLayout.findViewById(R.id.F27_00_LBL_DATA_SEGURO);
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.F27_00_LBL_DATA_PRECIO);
        TextView textView3 = (TextView) linearLayout.findViewById(R.id.F27_00_LBL_DATA_DETALLE);
        if (seguroBean instanceof SeguroObjetoBean) {
            textView.setText(Html.fromHtml(((SeguroObjetoBean) seguroBean).getNombreFamilia()));
            textView2.setText(seguroBean.getCuota());
            if (TextUtils.isEmpty(seguroBean.getPropietario())) {
                textView3.setVisibility(4);
            } else {
                textView3.setText(Html.fromHtml(seguroBean.getPropietario()));
                textView3.setVisibility(0);
            }
        } else {
            textView.setText(Html.fromHtml(seguroBean.getTitulo()));
            textView2.setText(Html.fromHtml(seguroBean.getCuota()));
            textView3.setVisibility(4);
        }
        try {
            textView.setContentDescription(new CAccessibility(getActivity()).applyFilterGeneral(textView.getText().toString()));
            textView2.setContentDescription(new CAccessibility(getActivity()).applyFilterGeneral(textView2.getText().toString()));
            textView3.setContentDescription(new CAccessibility(getActivity()).applyFilterGeneral(textView3.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        linearLayout.setTag(Integer.valueOf(i2));
        linearLayout.setClickable(true);
        linearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int size = segurosBean.getListaSeguros().size();
                int parseInt = Integer.parseInt(view.getTag().toString());
                if (parseInt < size) {
                    SegurosFragment.this.goToDetalleSeguro("SEGURO", (SeguroBean) segurosBean.getListaSeguros().get(parseInt), segurosBean.getUrlSiniestro());
                    return;
                }
                int i = parseInt - size;
                if (i < 0) {
                    i *= -1;
                }
                SegurosFragment.this.goToDetalleSeguro("OBJETO", (SeguroBean) segurosBean.getListaObjetos().get(i), segurosBean.getUrlSiniestro());
            }
        });
        this.tblTenenciaSeguros.addView(linearLayout);
    }

    /* access modifiers changed from: private */
    public void b(SegurosBean segurosBean) {
        ArrayList arrayList = new ArrayList();
        if (segurosBean.getUrlSiniestro() != null) {
            arrayList.add(getResources().getString(R.string.DENUNCIAR_SINIESTRO));
        }
        if (segurosBean.getUrlSeguimiento() != null) {
            arrayList.add(getResources().getString(R.string.SEGUIMIENTO_DENUNCIA));
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpMenuMasOpcionesSeguros", null, null, arrayList, getString(R.string.F24_00_ACTIONSHEET_CANCELAR), null, null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                SegurosFragment.this.ai = 0;
                if (str.equalsIgnoreCase(SegurosFragment.this.getResources().getString(R.string.DENUNCIAR_SINIESTRO))) {
                    SegurosFragment.this.ai = R.string.DENUNCIAR_SINIESTRO;
                    SegurosFragment.this.showProgressIndicator(VGetFirmaSeguros.nameService);
                    SegurosFragment.this.mDataManager.getFirmaSeguro();
                } else if (str.equalsIgnoreCase(SegurosFragment.this.getResources().getString(R.string.SEGUIMIENTO_DENUNCIA))) {
                    SegurosFragment.this.ai = R.string.SEGUIMIENTO_DENUNCIA;
                    SegurosFragment.this.showProgressIndicator(VGetFirmaSeguros.nameService);
                    SegurosFragment.this.mDataManager.getFirmaSeguro();
                }
            }

            public void onSimpleActionButton() {
                newInstance.closeDialog();
            }
        });
        newInstance.show(getFragmentManager(), null);
    }

    @Subscribe
    public void onGetFirmaSeguro(GetFirmaSeguroEvent getFirmaSeguroEvent) {
        if (this.mControlPager.getDisplayedChild() != 1 && this.ak == null) {
            dismissProgressIndicator();
            final GetFirmaSeguroEvent getFirmaSeguroEvent2 = getFirmaSeguroEvent;
            AnonymousClass13 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.FIRMA_SEGURO, (BaseActivity) getActivity()) {
                /* access modifiers changed from: protected */
                public void onOk() {
                    String str = "";
                    if (SegurosFragment.this.ai == R.string.DENUNCIAR_SINIESTRO) {
                        str = SegurosFragment.this.ag.getUrlSiniestro();
                    } else if (SegurosFragment.this.ai == R.string.SEGUIMIENTO_DENUNCIA) {
                        str = SegurosFragment.this.ag.getUrlSeguimiento();
                    }
                    GetFirmaSeguroBodyResponseBean getFirmaSeguroBodyResponseBean = getFirmaSeguroEvent2.getResponse().getFirmaSeguroBodyResponseBean;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("&jwt=");
                    ((SantanderRioMainActivity) SegurosFragment.this.getActivity()).changeFragmentAnimation(new SeguroWebViewFragment(getFirmaSeguroBodyResponseBean, sb.toString()), R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
                }
            };
            r1.handleWSResponse(getFirmaSeguroEvent);
        }
    }

    public void verifyBatery() {
        startActivityForResult(new Intent(getContext(), TestBatteryActivity.class), 6);
    }

    public void goToContratarSeguro(SegurosBean segurosBean, String str) {
        Intent intent = new Intent(getContext(), ContratarSeguroActivity.class);
        intent.putExtra(SegurosConstants.INTENT_TIPO_ALTA_MOVIL, str);
        intent.putExtra(SegurosConstants.INTENT_LISTA_TOTAL_SEGUROS, segurosBean);
        if (this.ad) {
            intent.putExtra(SegurosConstants.INTENT_LISTA_SEGUROS, c(segurosBean));
        }
        intent.putExtra(SegurosConstants.INTENT_SEGUROS_CARTERA, str.equalsIgnoreCase(TipoAlta.SEGURO_CARTERA));
        if (this.ak != null) {
            intent.putExtra(SegurosConstants.SEGURO_DEEPLINK_ACTION, this.ak);
        }
        startActivityForResult(intent, 4);
    }

    public void goToDetalleSeguro(String str, SeguroBean seguroBean, String str2) {
        c(1);
        this.ah = seguroBean;
        this.aj = str;
        this.f.onCreatePage(str, seguroBean, str2);
    }

    private void a(SeguroBean seguroBean, String str) {
        a(1, false);
        this.ah = seguroBean;
        this.f.onCreatePage(this.aj, seguroBean, str);
        setBackgroundVisibleInit();
    }

    public void setDetalleSeguroView(String str, final SeguroBean seguroBean) {
        this.seguroSeleccionado = seguroBean;
        this.a.trackScreen(getString(R.string.f203analytics_screen_Detalle_pliza));
        this.scrollViewDetalle.scrollTo(0, 0);
        this.lblTituloDetalle.setText(seguroBean.getTitulo());
        this.lblAseguradoraDetalle.setText(seguroBean.getAseguradora());
        if (seguroBean.getCuota().equalsIgnoreCase("-")) {
            this.rowCuota.setVisibility(8);
        } else {
            this.rowCuota.setVisibility(0);
        }
        configureActionBar();
        if (TextUtils.isEmpty(seguroBean.getSumaAsegurada())) {
            this.lblSumaAseguradaDetalle.setVisibility(8);
        } else {
            this.lblSumaAseguradaDetalle.setVisibility(0);
            this.lblSumaAseguradaDetalle.setText(getString(R.string.IDXX_SEGUROS_LBL_SUMA_ASEGURADA, seguroBean.getSumaAsegurada()));
        }
        this.lblCuotaDetalle.setText(seguroBean.getCuota());
        if (TextUtils.isEmpty(seguroBean.getFechaInicio())) {
            this.rowFechaInicio.setVisibility(8);
        } else {
            this.rowFechaInicio.setVisibility(0);
            this.lblFechaInicioDetalle.setText(seguroBean.getFechaInicio());
        }
        TextView textView = this.lblPolizaDetalle;
        StringBuilder sb = new StringBuilder();
        sb.append(seguroBean.getNumPoliza());
        sb.append("/");
        sb.append(seguroBean.getNumCertificado());
        textView.setText(sb.toString());
        if (TextUtils.isEmpty(seguroBean.getMedioPago())) {
            this.rowMedioPago.setVisibility(8);
        } else {
            this.rowMedioPago.setVisibility(0);
            this.lblMedioPagoDetalle.setText(seguroBean.getMedioPago());
        }
        this.lblFormaPagoDetalle.setText(getString(R.string.ID_4075_SEGUROS_LBL_DEB_AUTO));
        if (TextUtils.isEmpty(seguroBean.getTipoEnvioPoliza())) {
            this.rowTipoPoliza.setVisibility(8);
        } else {
            this.lblTipoPoliza.setText(Html.fromHtml(seguroBean.getTipoEnvioPoliza()));
            this.rowTipoPoliza.setVisibility(0);
        }
        if (TextUtils.isEmpty(seguroBean.geteMail())) {
            this.ll_email.setVisibility(8);
        } else {
            this.ll_email.setVisibility(0);
            this.lblDataEmail.setText(seguroBean.geteMail());
        }
        if (TextUtils.isEmpty(seguroBean.getOcupacion())) {
            this.ll_ocupaciones.setVisibility(8);
        } else {
            this.ll_ocupaciones.setVisibility(0);
            this.lblDataOcupacion.setText(Html.fromHtml(seguroBean.getOcupacion()));
        }
        if (!seguroBean.getDatos().getListaDatosBean().isEmpty()) {
            this.lblSeparadorDatosDetalle.setText(Html.fromHtml(seguroBean.getSubtituloDatos()));
            a(seguroBean.getDatos());
            this.lnlWrapperDatosDetalle.setVisibility(0);
        } else {
            this.lnlWrapperDatosDetalle.setVisibility(8);
        }
        if (str == null || str.equalsIgnoreCase("OBJETO") || seguroBean.getAsistencias().getListaAsistenciasBean() == null || seguroBean.getAsistencias().getListaAsistenciasBean().size() <= 0) {
            this.btnSolicitarAsistencia.setVisibility(8);
        } else {
            this.btnSolicitarAsistencia.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (seguroBean.getAsistencias().getListaAsistenciasBean().size() > 1) {
                        SegurosFragment.this.a.trackEvent(SegurosFragment.this.getString(R.string.analytics_trackevent_category_seguros), SegurosFragment.this.getString(R.string.analytics_trackevent_action_click_boton_llamar), SegurosFragment.this.getString(R.string.f228analytics_trackevent_label_asistencia_especfica));
                        SegurosFragment.this.d = Boolean.valueOf(false);
                        SegurosFragment.this.goToSolicitarAsistencia(seguroBean.getAsistencias());
                        return;
                    }
                    SegurosFragment.this.a.trackEvent(SegurosFragment.this.getString(R.string.analytics_trackevent_category_seguros), SegurosFragment.this.getString(R.string.analytics_trackevent_action_click_boton_llamar), SegurosFragment.this.getString(R.string.analytics_trackevent_label_asistencia_general));
                    SegurosFragment.this.d = Boolean.valueOf(true);
                    SegurosFragment.this.goToDetalleAsistencia(((AsistenciaSeguroBean) seguroBean.getAsistencias().getListaAsistenciasBean().get(0)).getTel());
                }
            });
            this.btnSolicitarAsistencia.setVisibility(0);
        }
        try {
            this.lblPolizaDetalle.setContentDescription(new CAccessibility(getActivity()).applyFilterCharacterToCharacter(this.lblPolizaDetalle.getText().toString()));
            this.lblMedioPagoDetalle.setContentDescription(new CAccessibility(getActivity()).applyFilterGeneral(this.lblMedioPagoDetalle.getText().toString()));
            this.lblCuotaDetalle.setContentDescription(new CAccessibility(getActivity()).applyFilterGeneral(this.lblCuotaDetalle.getText().toString()));
            this.lblSumaAseguradaDetalle.setContentDescription(new CAccessibility(getActivity()).applyFilterGeneral(this.lblSumaAseguradaDetalle.getText().toString()));
            this.lblFechaInicioDetalle.setContentDescription(new CAccessibility(getActivity()).applyFilterDate(this.lblFechaInicioDetalle.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void goToSolicitarAsistencia(AsistenciasSeguroBean asistenciasSeguroBean) {
        c(2);
        this.g.onCreatePage(asistenciasSeguroBean);
    }

    public void setSolicitarAsistenciaView(final AsistenciasSeguroBean asistenciasSeguroBean) {
        this.a.trackScreen(getString(R.string.analytics_screen_Seleccionar_tipo_seguro_solicitar_asistencia));
        this.tblAsistencias.removeAllViews();
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService("layout_inflater");
        int i2 = 0;
        for (AsistenciaSeguroBean desc : asistenciasSeguroBean.getListaAsistenciasBean()) {
            LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.list_item_asistencia, null);
            ((TextView) linearLayout.findViewById(R.id.F27_40_LBL_ASISTENCIA)).setText(desc.getDesc());
            linearLayout.setTag(Integer.valueOf(i2));
            linearLayout.setClickable(true);
            linearLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SegurosFragment.this.goToDetalleAsistencia(((AsistenciaSeguroBean) asistenciasSeguroBean.getListaAsistenciasBean().get(Integer.parseInt(view.getTag().toString()))).getTel());
                }
            });
            this.tblAsistencias.addView(linearLayout);
            i2++;
        }
    }

    public void goToDetalleAsistencia(String str) {
        c(3);
        this.h.onCreatePage(str);
    }

    public void setDetalleAsistenciaView(final String str) {
        if (this.seguroSeleccionado.getAsistencias().getListaAsistenciasBean().size() > 1) {
            this.a.trackScreen(getString(R.string.f204analytics_screen_Solicitar_asistencia_segn_tipo_seguro));
        } else {
            this.a.trackScreen(getString(R.string.analytics_screen_Solicitar_asistencia_general));
        }
        this.lblTelefonoAsistencia.setText(str);
        try {
            this.lblTelefonoAsistencia.setContentDescription(new CAccessibility(getActivity()).applyFilterPhoneNumber(this.lblTelefonoAsistencia.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.btnLlamarAsistencia.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SegurosFragment.this.b(str);
            }
        });
    }

    public void goToContratarSeguroMovil(CotizacionBean cotizacionBean) {
        Intent intent = new Intent(getActivity(), SeguroMovilActivity.class);
        intent.putExtra(SegurosConstants.INTENT_TIPO_ALTA_MOVIL, TipoAlta.ALTA_DISPOSITIVO);
        intent.putExtra(SegurosConstants.INTENT_COTIZACION, cotizacionBean);
        startActivityForResult(intent, 2);
    }

    public void goToContratarSeguroMovilSinCotizar() {
        Intent intent = new Intent(getActivity(), SeguroMovilActivity.class);
        intent.putExtra(SegurosConstants.INTENT_TIPO_ALTA_MOVIL, this.i);
        if (this.ad) {
            intent.putExtra(SegurosConstants.INTENT_LISTA_SEGUROS, this.ae);
        }
        startActivityForResult(intent, 2);
    }

    private SegurosBean c(SegurosBean segurosBean) {
        SegurosBean segurosBean2 = new SegurosBean();
        ArrayList arrayList = new ArrayList();
        for (SeguroBean seguroBean : segurosBean.getListaSeguros()) {
            if (seguroBean.getCodRamo().equals(CodRamo.SEGURO_MOVIL)) {
                arrayList.add(seguroBean);
            }
        }
        segurosBean2.setListaSeguros(arrayList);
        if (!arrayList.isEmpty()) {
            this.ad = true;
            this.ae.setListaSeguros(arrayList);
        }
        return segurosBean2;
    }

    private void a(DatosSeguroBean datosSeguroBean) {
        this.tblDatosDetalle.removeAllViews();
        Context applicationContext = getActivity().getApplicationContext();
        CAccessibility cAccessibility = new CAccessibility(applicationContext);
        for (DatoSeguroBean datoSeguroBean : datosSeguroBean.getListaDatosBean()) {
            String desc = datoSeguroBean.getDesc();
            String valor = datoSeguroBean.getValor();
            if (desc.equals("Domicilio") || desc.equals("Nombre y Apellido")) {
                try {
                    CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(applicationContext);
                    this.tblDatosDetalle.addView(TableViewRowCreator.createRowLabelValue(applicationContext, R.layout.list_item_dato_seguro, Html.fromHtml(desc).toString(), cAccessibility.applyFilterGeneral(Html.fromHtml(desc).toString()), Html.fromHtml(valor).toString(), cFiltersAccessibility.filterStringComun(Html.fromHtml(valor).toString())));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else if (desc.equals("DNI")) {
                try {
                    this.tblDatosDetalle.addView(TableViewRowCreator.createRowLabelValue(applicationContext, R.layout.list_item_dato_seguro, Html.fromHtml(desc).toString(), cAccessibility.applyFilterCharacterToCharacter(Html.fromHtml(desc).toString()), Html.fromHtml(valor).toString(), cAccessibility.applyFilterGeneral(Html.fromHtml(valor).toString())));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } else {
                try {
                    this.tblDatosDetalle.addView(TableViewRowCreator.createRowLabelValue(applicationContext, R.layout.list_item_dato_seguro, Html.fromHtml(desc).toString(), cAccessibility.applyFilterGeneral(Html.fromHtml(desc).toString()), Html.fromHtml(valor).toString(), cAccessibility.applyFilterGeneral(Html.fromHtml(valor).toString())));
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        Intent intent = new Intent("android.intent.action.DIAL");
        intent.setData(Uri.parse(String.format("tel:%s", new Object[]{str})));
        startActivity(intent);
    }

    private void F() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), "android.permission.READ_PHONE_STATE") != 0) {
            requestPermissions(new String[]{"android.permission.READ_PHONE_STATE"}, 99);
            return;
        }
        G();
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i2 != 99) {
            if (i2 == 1455) {
                if (iArr.length <= 0 || iArr[0] != 0) {
                    onBackPressed();
                } else {
                    this.f.showDetalleSeguroMenuOptions();
                }
            }
        } else if (iArr.length <= 0 || iArr[0] != 0) {
            Toast.makeText(getContext(), R.string.ubucation_permission_request_message, 0).show();
        } else {
            G();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.af) {
            F();
        }
    }

    public void onPause() {
        super.onPause();
        this.af = false;
    }

    private void G() {
        if (this.i.equalsIgnoreCase(TipoAlta.ALTA_O_CAMBIO_DISPOSITIVO)) {
            if (this.ad) {
                this.i = TipoAlta.ALTA_O_CAMBIO_DISPOSITIVO;
            } else {
                this.i = TipoAlta.ALTA_DISPOSITIVO;
            }
        }
        if (this.i.equalsIgnoreCase(TipoAlta.ALTA_DISPOSITIVO)) {
            this.e.obtenerCotizacionSeguroMovil(this.b.getKeyUniqueID(), UtilDeviceInfo.getMarca(), UtilDeviceInfo.getModelo());
        } else {
            this.e.goToContratarSeguroMovilSinCotizar();
        }
    }

    public void attachView() {
        if (this.mControlPager != null) {
            switch (this.mControlPager.getDisplayedChild()) {
                case 0:
                    if (!this.e.isViewAttached()) {
                        this.e.attachView(this);
                        return;
                    }
                    return;
                case 1:
                    if (!this.f.isViewAttached()) {
                        this.f.attachView(this);
                        return;
                    }
                    return;
                case 2:
                    if (!this.g.isViewAttached()) {
                        this.g.attachView(this);
                        return;
                    }
                    return;
                case 3:
                    if (!this.h.isViewAttached()) {
                        this.h.attachView(this);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void detachView() {
        if (this.e.isViewAttached()) {
            this.e.detachView();
        }
        if (this.f.isViewAttached()) {
            this.f.detachView();
        }
        if (this.g.isViewAttached()) {
            this.g.detachView();
        }
        if (this.h.isViewAttached()) {
            this.h.detachView();
        }
        if (this.al.isViewAttached()) {
            this.al.detachView();
        }
    }

    public void onStop() {
        super.onStop();
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (!activityResultHandler(i3, intent, FragmentConstants.SEGUROS)) {
            if (i2 == 2 || i2 == 4) {
                if (i3 == -1) {
                    try {
                        attachView();
                        this.e.consultarTenenciaSeguros();
                    } catch (Exception e2) {
                        Log.e("SegurosFragment", "onActivityResult: ", e2);
                    }
                }
            } else if (i2 == 6 && i3 == -1 && intent != null) {
                String stringExtra = intent.getStringExtra(ResultsKeys.BATTERY_RESULT_VALUE);
                if (stringExtra.equals(ResultValues.OK)) {
                    this.af = true;
                } else if (stringExtra.equals(ResultValues.FAIL)) {
                    this.i = TipoAlta.NO_ASEGURABLE;
                } else if (i3 == 0 && intent != null && stringExtra.equals(ResultValues.ERROR)) {
                    this.i = TipoAlta.NO_ASEGURABLE;
                }
            }
            if (this.ak != null) {
                this.ak = null;
            }
        }
    }

    private void a(int i2, boolean z) {
        detachView();
        if (this.mControlPager != null) {
            detachView();
            switch (i2) {
                case 0:
                    if (!z) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                    break;
                case 1:
                    if (!z) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.noAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToDownAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 2:
                    if (!z) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.noAnimation());
                        break;
                    }
                case 3:
                    if (this.d.booleanValue()) {
                        if (!z) {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromUpAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.noAnimation());
                            break;
                        } else {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.noAnimation());
                            break;
                        }
                    } else if (!z) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
            }
            this.mControlPager.setDisplayedChild(i2);
            attachView();
            configureActionBar();
        }
        if (i2 == 0) {
            ((SantanderRioMainActivity) getActivity()).lockMenu(false);
        } else {
            ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        }
    }

    private void c(int i2) {
        a(i2, true);
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.a;
    }

    public void gotoFamiliaObjeto(GetFamiliasObjetosBodyResponseBean getFamiliasObjetosBodyResponseBean) {
        Intent intent = new Intent(getContext(), SeguroObjetosActivity.class);
        intent.putExtra(SeguroObjetosActivity.GET_FAMILIA_OBJETOS, getFamiliasObjetosBodyResponseBean);
        startActivityForResult(intent, 7);
    }

    public void showOnBoarding() {
        showOnBoarding(R.layout.onboarding_seguros, R.id.F27_XX_BTN_CLOSE_PAGE2, R.id.F27_XX_FLP_ONBOARDING, SegurosConstants.PREFERENCE_ONBOARDING);
    }
}
