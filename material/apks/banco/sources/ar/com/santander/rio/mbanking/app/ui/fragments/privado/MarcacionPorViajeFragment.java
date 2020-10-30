package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.MarcacionPorViajeComprobantePresenter;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.MarcacionPorViajeComprobanteView;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.MarcacionPorViajeConsultaPresenter;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.MarcacionPorViajeConsultaView;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.MarcacionPorViajeDestinosPresenter;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.MarcacionPorViajeDestinosView;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.MarcacionPorViajePresenter;
import ar.com.santander.rio.mbanking.app.module.marcacionPorViaje.MarcacionPorViajeView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarTarjetasMarcacionActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.MarcacionDestinosSeleccionadosAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.MarcacionDestinosSeleccionadosAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.adapters.MarcacionListaViajesAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.MarcacionSearchListDestinos;
import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetasMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuarioMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajesBean;
import ar.com.santander.rio.mbanking.utils.KeyboardUtils;
import ar.com.santander.rio.mbanking.utils.KeyboardUtils.KeyboardVisibilityListener;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class MarcacionPorViajeFragment extends BaseMvpFragment implements OnClickListener, MarcacionPorViajeComprobanteView, MarcacionPorViajeConsultaView, MarcacionPorViajeDestinosView, MarcacionPorViajeView, OnItemClickListener, MarcacionSearchListDestinos.OnItemClickListener {
    static final Boolean a = Boolean.valueOf(true);
    static final Boolean b = Boolean.valueOf(false);
    /* access modifiers changed from: private */
    public Boolean ad = Boolean.valueOf(false);
    /* access modifiers changed from: private */
    public ViajeBean ae;
    /* access modifiers changed from: private */
    public MarcacionPorViajePresenter af;
    /* access modifiers changed from: private */
    public MarcacionPorViajeConsultaPresenter ag;
    private MarcacionPorViajeDestinosPresenter ah;
    private MarcacionPorViajeComprobantePresenter ai;
    private GetTarjPaisesBodyResponseBean aj;
    private List<TarjetaMarcacionBean> ak = new ArrayList();
    private String al;
    @InjectView(2131363355)
    RelativeLayout barraBusqueda;
    @InjectView(2131363375)
    Button btnComprobanteVolver;
    @InjectView(2131363349)
    Button btnDestinoGuardar;
    @InjectView(2131363336)
    Button btnEditarDetalleViajeGuardar;
    @InjectView(2131363324)
    Button btnInformarViajeHabilitar;
    final List<PaisBean> c = new ArrayList();
    String d = "DialogDate";
    ImageView e;
    ImageView f;
    ImageView g;
    @Inject
    SessionManager h;
    @Inject
    AnalyticsManager i;
    @InjectView(2131363376)
    ImageView imgComprobante;
    @InjectView(2131363328)
    LinearLayout layoutCruzHabilitar;
    @InjectView(2131363377)
    TextView lblComprobanteMensajeOk;
    @InjectView(2131363378)
    TextView lblComprobanteTitulo;
    @InjectView(2131363338)
    TextView lblDataConsultarEditarCantDestinos;
    @InjectView(2131363341)
    TextView lblDataConsultarEditarCantTarjetas;
    @InjectView(2131363337)
    EditText lblDataConsultarEditarEmail;
    @InjectView(2131363339)
    TextView lblDataConsultarEditarFechaDesde;
    @InjectView(2131363340)
    TextView lblDataConsultarEditarFechaHasta;
    @InjectView(2131363344)
    TextView lblFechaDesde;
    @InjectView(2131363345)
    TextView lblFechaHasta;
    @InjectView(2131363347)
    TextView lblHabilitarTitulo;
    @InjectView(2131363353)
    ListView listaBarraBusqueda;
    public List<PaisBean> listaPaisesCompleta = new ArrayList();
    public List<PaisBean> listaPaisesDestino = new ArrayList();
    @InjectView(2131363356)
    RelativeLayout lnlSearch;
    @InjectView(2131363327)
    LinearLayout lnlTenenciaRes4Error;
    @InjectView(2131363329)
    ListView lvItems;
    @InjectView(2131363352)
    ListView lvPaisesDestino;
    @InjectView(2131365220)
    ViewFlipper mControlPager;
    @InjectView(2131365590)
    RelativeLayout rowMail;
    @InjectView(2131365696)
    View separador;
    @InjectView(2131363388)
    TextView txtAgregarPaises;
    @InjectView(2131363325)
    TextView txtHabilitarTarjetasHabilitar;
    @InjectView(2131363350)
    ClearableEditText txtSearch;
    @InjectView(2131363326)
    TextView txtTenenciaRes4Error;

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public void onCreate(Bundle bundle) {
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        this.mRootView = getActivity().getLayoutInflater().inflate(R.layout.layout_marcacion_por_viaje_main, viewGroup, false);
        ButterKnife.inject((Object) this, this.mRootView);
        initialize();
        return this.mRootView;
    }

    public void initialize() {
        this.af = new MarcacionPorViajePresenter(this.mBus, this.mDataManager, getContext());
        this.ag = new MarcacionPorViajeConsultaPresenter(this.mBus, this.mDataManager, getContext());
        this.ah = new MarcacionPorViajeDestinosPresenter(this.mBus, this.mDataManager);
        this.ai = new MarcacionPorViajeComprobantePresenter(this.mBus, this.mDataManager);
        this.af.attachView(this);
        this.af.callGetViajes();
    }

    public void configureActionBarBackOnly() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        this.e = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.e.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MarcacionPorViajeFragment.this.onBackPressed();
            }
        });
    }

    public void configureActionBarWithMenu() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_WITH_MENU);
        this.mActionBar = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        this.e = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        this.e.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MarcacionPorViajeFragment.this.onBackPressed();
            }
        });
        this.f = (ImageView) this.mActionBar.findViewById(R.id.menu);
        this.f.setContentDescription(getString(R.string.IDXX_CONTENT_DESCRIPTION_BTN_OPTIONS));
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MarcacionPorViajeFragment.this.showOptionsMenuEditar();
            }
        });
    }

    public void configureActionBarCancelWithConfirm() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.CANCEL_WITH_CONFIRMAR);
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        View findViewById = customView.findViewById(R.id.cancel_imgButton);
        View findViewById2 = customView.findViewById(R.id.confirm_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MarcacionPorViajeFragment.this.onBackPressed();
                }
            });
        }
        if (!this.ae.getAcciones().equals("1") && !this.ae.getAcciones().equals("3") && !this.ae.getAcciones().equals("4")) {
            findViewById2.setVisibility(8);
        } else if (findViewById2 != null) {
            findViewById2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MarcacionPorViajeFragment.this.ae.getPaises().getListaPaises().clear();
                    MarcacionPorViajeFragment.this.a(MarcacionPorViajeFragment.this.ae);
                    MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_guardar_tilde), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_destinos));
                    MarcacionPorViajeFragment.this.goBackToDetalleViaje();
                }
            });
        }
    }

    private void y() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        View findViewById = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        ((SantanderRioMainActivity) getActivity()).lockMenu(false);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MarcacionPorViajeFragment.this.switchDrawer();
                }
            });
        }
    }

    private void b(final String str) {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_HELP);
        this.mActionBar = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        ((ImageView) this.mActionBar.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MarcacionPorViajeFragment.this.switchDrawer();
            }
        });
        this.g = (ImageView) this.mActionBar.findViewById(R.id.img_help);
        if (str == null || str.isEmpty()) {
            this.g.setVisibility(8);
        } else {
            this.g.setVisibility(0);
            this.g.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MarcacionPorViajeFragment.this.showHelp(str);
                }
            });
        }
        ((SantanderRioMainActivity) getActivity()).lockMenu(false);
    }

    public void showOptionsMenuEditar() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.ID4010_MARCACION_VIAJE_MENU_ELIMINAR_VIAJE));
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpEditar", null, null, arrayList, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, null, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(MarcacionPorViajeFragment.this.getString(R.string.ID4010_MARCACION_VIAJE_MENU_ELIMINAR_VIAJE))) {
                    MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_eliminar), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_viaje));
                    MarcacionPorViajeFragment.this.showMessageEliminar();
                }
            }

            public void onSimpleActionButton() {
                MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_cancelar), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_cancelar_eliminar_viaje));
                newInstance.dismiss();
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), "popUpEditar");
    }

    public void showMessageEliminar() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpEliminar", getString(R.string.IDXX_MARCACION_VIAJE_ELIMINAR_VIAJE_TITULO), getString(R.string.IDXX_MARCACION_VIAJE_ELIMINAR_VIAJE_MENSAJE), null, null, getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onPositiveButton() {
                MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_si), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_eliminar_viaje));
                MarcacionPorViajeFragment.this.ae.setAcciones("2");
                MarcacionPorViajeFragment.this.ag.callABMViajes("B", MarcacionPorViajeFragment.this.ae.getMail(), MarcacionPorViajeFragment.this.ae.getId(), MarcacionPorViajeFragment.this.ae.getFechaInicio(), MarcacionPorViajeFragment.this.ae.getFechaFin(), MarcacionPorViajeFragment.this.ae.getReintento(), MarcacionPorViajeFragment.this.ae.getPaises(), MarcacionPorViajeFragment.this.ae.getTarjetas());
                MarcacionPorViajeFragment.this.ae.setReintento("true");
            }

            public void onNegativeButton() {
                newInstance.dismiss();
                MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_no), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_cancelar_eliminar_viaje));
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), "popUpEliminar");
    }

    public void showHelp(String str) {
        this.i.trackScreen(getString(R.string.analytics_screen_name_travels_ayuda));
        Intent intent = new Intent(getContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getString(R.string.IDXX_MARCACION_VIAJE_LBL_TITLE_AYUDA));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str);
        startActivityForResult(intent, 1);
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.af.isViewAttached()) {
                    this.af.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.ag.isViewAttached()) {
                    this.ag.attachView(this);
                    return;
                }
                return;
            case 2:
                if (!this.ah.isViewAttached()) {
                    this.ah.attachView(this);
                    return;
                }
                return;
            case 3:
                if (!this.ai.isViewAttached()) {
                    this.ai.attachView(this);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void detachView() {
        if (this.af.isViewAttached()) {
            this.af.detachView();
        }
        if (this.ag.isViewAttached()) {
            this.ag.detachView();
        }
        if (this.ah.isViewAttached()) {
            this.ah.detachView();
        }
        if (this.ai.isViewAttached()) {
            this.ai.detachView();
        }
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F26_00_BTN_INFORMAR_VIAJE) {
            configureActionBarBackOnly();
            this.ae = new ViajeBean();
            this.ae.setAcciones("4");
            this.ae.setId("");
            this.ae.setReintento("");
            this.ae.setTarjetas(new TarjetasMarcacionBean());
            this.ae.setPaises(new PaisesBean());
            this.ae.setMail("");
            this.ae.setFechaFin("");
            this.ae.setFechaInicio("");
            this.ae.setTotalDestinos("0");
            this.ae.setTotalDias("0");
            this.ae.setTotalTarjetas("0");
            this.ae.setUsuarios(new UsuariosMarcacionBean());
            this.af.onGetTarjPaises();
        } else if (id2 == R.id.F26_02_BTN_GUARDAR) {
            String isValidData = isValidData();
            if (isValidData.isEmpty()) {
                if (this.ae.getAcciones().equals("4")) {
                    this.i.trackEvent(getString(R.string.analytics_action_travels_habilitacion_tarjeta), getString(R.string.analytics_action_travels_guardar), getString(R.string.analytics_action_travels_formulario_completado));
                } else if (this.ae.getAcciones().equals("1") || this.ae.getAcciones().equals("3")) {
                    this.i.trackEvent(getString(R.string.analytics_action_travels_habilitacion_tarjeta), getString(R.string.analytics_action_travels_guardar), getString(R.string.analytics_action_travels_cambios_formulario));
                }
                String dateFormat = UtilDate.getDateFormat(this.ae.getFechaInicio(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_DASH);
                String dateFormat2 = UtilDate.getDateFormat(this.ae.getFechaFin(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_DASH);
                String str = "M";
                if (this.ae.getAcciones().equalsIgnoreCase("4")) {
                    this.ae.setMail(this.lblDataConsultarEditarEmail.getText().toString());
                    this.ae.setFechaInicio(dateFormat);
                    this.ae.setFechaFin(dateFormat2);
                    this.ae.setPaises(new PaisesBean());
                    a(this.ae);
                    str = "A";
                }
                String str2 = str;
                this.ae.setTarjetas(new TarjetasMarcacionBean(a(this.ae.getUsuarios())));
                this.ag.callABMViajes(str2, this.ae.getMail(), this.ae.getId(), this.ae.getFechaInicio(), this.ae.getFechaFin(), this.ae.getReintento(), this.ae.getPaises(), this.ae.getTarjetas());
                this.ae.setReintento("true");
                return;
            }
            showInvalidDataPopup(isValidData);
        }
    }

    private List<TarjetaMarcacionBean> a(UsuariosMarcacionBean usuariosMarcacionBean) {
        ArrayList arrayList = new ArrayList();
        for (UsuarioMarcacionBean usuarioMarcacionBean : usuariosMarcacionBean.getListaUsuarios()) {
            for (TarjetaMarcacionBean tarjetaMarcacionBean : usuarioMarcacionBean.getTarjetas().getListaTarjetas()) {
                if (tarjetaMarcacionBean.isSelected().booleanValue()) {
                    TarjetaMarcacionBean tarjetaMarcacionBean2 = new TarjetaMarcacionBean(usuarioMarcacionBean.getNombre().toString(), tarjetaMarcacionBean.getDescripcion().toString(), tarjetaMarcacionBean.getCondicion().toString());
                    tarjetaMarcacionBean2.setSelected(null);
                    arrayList.add(tarjetaMarcacionBean2);
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void a(ViajeBean viajeBean) {
        for (PaisBean paisBean : this.listaPaisesDestino) {
            viajeBean.getPaises().getListaPaises().add(new PaisBean(paisBean.getId().toString(), paisBean.getDescripcion().toString()));
        }
        viajeBean.setTotalDestinos(String.valueOf(this.ae.getPaises().getListaPaises().size()));
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String isValidData() {
        /*
            r8 = this;
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1
            r0.<init>(r1)
            r1 = 0
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r2 = r8.ae     // Catch:{ ParseException -> 0x0030 }
            java.lang.String r2 = r2.getFechaInicio()     // Catch:{ ParseException -> 0x0030 }
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_DASH     // Catch:{ ParseException -> 0x0030 }
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1     // Catch:{ ParseException -> 0x0030 }
            java.lang.String r2 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r2, r3, r4)     // Catch:{ ParseException -> 0x0030 }
            java.util.Date r2 = r0.parse(r2)     // Catch:{ ParseException -> 0x0030 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r3 = r8.ae     // Catch:{ ParseException -> 0x002e }
            java.lang.String r3 = r3.getFechaFin()     // Catch:{ ParseException -> 0x002e }
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_DASH     // Catch:{ ParseException -> 0x002e }
            java.lang.String r5 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1     // Catch:{ ParseException -> 0x002e }
            java.lang.String r3 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r3, r4, r5)     // Catch:{ ParseException -> 0x002e }
            java.util.Date r3 = r0.parse(r3)     // Catch:{ ParseException -> 0x002e }
            r1 = r3
            goto L_0x0035
        L_0x002e:
            r3 = move-exception
            goto L_0x0032
        L_0x0030:
            r3 = move-exception
            r2 = r1
        L_0x0032:
            r3.printStackTrace()
        L_0x0035:
            if (r1 != 0) goto L_0x0039
            if (r2 == 0) goto L_0x00c5
        L_0x0039:
            boolean r3 = ar.com.santander.rio.mbanking.utils.UtilDate.isDateAfter(r2, r1)
            if (r3 == 0) goto L_0x0047
            r0 = 2131757179(0x7f10087b, float:1.9145286E38)
            java.lang.String r0 = r8.getString(r0)
            return r0
        L_0x0047:
            java.util.Date r3 = new java.util.Date
            r3.<init>()
            java.util.Date r3 = ar.com.santander.rio.mbanking.utils.UtilDate.resetHours(r3)
            boolean r3 = ar.com.santander.rio.mbanking.utils.UtilDate.isDateAfter(r3, r2)
            if (r3 == 0) goto L_0x005e
            r0 = 2131757178(0x7f10087a, float:1.9145284E38)
            java.lang.String r0 = r8.getString(r0)
            return r0
        L_0x005e:
            long r3 = ar.com.santander.rio.mbanking.utils.UtilDate.getDifferenceBetweenDates(r1, r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean r5 = r8.aj
            java.lang.String r5 = r5.getDuracionMax()
            java.lang.Long r5 = java.lang.Long.valueOf(r5)
            long r5 = r5.longValue()
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x008c
            r0 = 2131757182(0x7f10087e, float:1.9145293E38)
            java.lang.String r0 = r8.getString(r0)
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r2 = 0
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean r3 = r8.aj
            java.lang.String r3 = r3.getDuracionMax()
            r1[r2] = r3
            java.lang.String r0 = java.lang.String.format(r0, r1)
            return r0
        L_0x008c:
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r3 = r8.ae
            ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean r3 = r3.getUsuarios()
            java.util.ArrayList r1 = r8.a(r3, r2, r1)
            boolean r3 = r1.isEmpty()
            if (r3 != 0) goto L_0x00a1
            java.lang.String r0 = r8.a(r1)
            return r0
        L_0x00a1:
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean r1 = r8.aj     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r1 = r1.getFechaInicioMax()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_2     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r1, r3, r4)     // Catch:{ Exception -> 0x00c1 }
            java.util.Date r0 = r0.parse(r1)     // Catch:{ Exception -> 0x00c1 }
            boolean r0 = ar.com.santander.rio.mbanking.utils.UtilDate.isDateAfter(r2, r0)     // Catch:{ Exception -> 0x00c1 }
            if (r0 == 0) goto L_0x00c5
            r0 = 2131757180(0x7f10087c, float:1.9145288E38)
            java.lang.String r0 = r8.getString(r0)     // Catch:{ Exception -> 0x00c1 }
            return r0
        L_0x00c1:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00c5:
            android.widget.EditText r0 = r8.lblDataConsultarEditarEmail
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.Boolean r0 = ar.com.santander.rio.mbanking.utils.UtilString.isEmailValid(r0)
            boolean r0 = r0.booleanValue()
            if (r0 != 0) goto L_0x00e1
            r0 = 2131757140(0x7f100854, float:1.9145207E38)
            java.lang.String r0 = r8.getString(r0)
            return r0
        L_0x00e1:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment.isValidData():java.lang.String");
    }

    public void setMarcacionPorViajeComprobanteView(Boolean bool, String str, String str2) {
        if (bool.booleanValue()) {
            this.imgComprobante.setBackground(getResources().getDrawable(R.drawable.ic_big_check));
            this.lblComprobanteTitulo.setText(getString(R.string.F26_07_TITULO));
            String acciones = this.ae.getAcciones();
            char c2 = 65535;
            switch (acciones.hashCode()) {
                case 49:
                    if (acciones.equals("1")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 50:
                    if (acciones.equals("2")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 51:
                    if (acciones.equals("3")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 52:
                    if (acciones.equals("4")) {
                        c2 = 3;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    this.lblComprobanteMensajeOk.setText(getString(R.string.F26_07_MENSAJE_OK_EDITAR));
                    break;
                case 1:
                    this.lblComprobanteMensajeOk.setText(getString(R.string.F26_07_MENSAJE_OK_ELIMINAR));
                    break;
                case 2:
                    this.lblComprobanteMensajeOk.setText(getString(R.string.F26_07_MENSAJE_OK_EDITAR));
                    break;
                case 3:
                    this.lblComprobanteMensajeOk.setText(getString(R.string.ID3998_MENSAJE_OK_NUEVO));
                    this.i.trackScreen(getString(R.string.analytics_screen_name_travels_confirmacion));
                    break;
            }
            this.btnComprobanteVolver.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_volver), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_a_resumen));
                    MarcacionPorViajeFragment.this.onBackPressed();
                }
            });
        } else if (str.equalsIgnoreCase(MarcacionViajeConstants.RESCODE_USER000454)) {
            this.imgComprobante.setBackground(getResources().getDrawable(R.drawable.error_continuacion));
            this.lblComprobanteMensajeOk.setText(getString(R.string.f192MSG_USER00454_Marcacin_por_Viaje));
            this.btnComprobanteVolver.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MarcacionPorViajeFragment.this.goBackToMarcacionViajeHabilitar(Boolean.valueOf(false));
                }
            });
        } else {
            this.imgComprobante.setBackground(getResources().getDrawable(R.drawable.sin_tenencia));
            this.lblComprobanteMensajeOk.setText(Html.fromHtml(str2));
            this.btnComprobanteVolver.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_volver), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_a_resumen));
                    MarcacionPorViajeFragment.this.goBackToMarcacionViajeHabilitar(Boolean.valueOf(false));
                }
            });
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setMarcacionPorViajeConsultaView(final ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean r9) {
        /*
            r8 = this;
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r0 = r8.ae
            java.lang.String r0 = r0.getReintento()
            if (r0 == 0) goto L_0x0016
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r0 = r8.ae
            java.lang.String r0 = r0.getReintento()
            java.lang.String r1 = ""
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x001d
        L_0x0016:
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r0 = r8.ae
            java.lang.String r1 = "false"
            r0.setReintento(r1)
        L_0x001d:
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r0 = r8.ae
            java.lang.String r0 = r0.getAcciones()
            java.lang.String r1 = "4"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0038
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r0 = r8.i
            r1 = 2131757934(0x7f100b6e, float:1.9146818E38)
            java.lang.String r1 = r8.getString(r1)
            r0.trackScreen(r1)
            goto L_0x0044
        L_0x0038:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r0 = r8.i
            r1 = 2131757932(0x7f100b6c, float:1.9146814E38)
            java.lang.String r1 = r8.getString(r1)
            r0.trackScreen(r1)
        L_0x0044:
            android.widget.TextView r0 = r8.lblDataConsultarEditarFechaDesde
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r1 = r8.ae
            java.lang.String r1 = r1.getFechaInicio()
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_DASH
            r3 = 2131755509(0x7f1001f5, float:1.91419E38)
            java.lang.String r4 = r8.getString(r3)
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r1, r2, r4)
            r0.setText(r1)
            android.widget.TextView r0 = r8.lblDataConsultarEditarFechaHasta
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r1 = r8.ae
            java.lang.String r1 = r1.getFechaFin()
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_DASH
            java.lang.String r3 = r8.getString(r3)
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r1, r2, r3)
            r0.setText(r1)
            r0 = 0
            if (r9 == 0) goto L_0x0118
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r1 = r8.ae
            java.lang.String r1 = r1.getAcciones()
            java.lang.String r2 = "1"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x009e
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r1 = r8.ae
            java.lang.String r1 = r1.getAcciones()
            java.lang.String r2 = "3"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x009e
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r1 = r8.ae
            java.lang.String r1 = r1.getAcciones()
            java.lang.String r2 = "4"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0118
        L_0x009e:
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r1 = r8.ae
            java.lang.String r1 = r1.getAcciones()
            java.lang.String r2 = "4"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x00b8
            ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean r1 = r9.getUsuarios()
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r0)
            r8.a(r1, r2)
            goto L_0x00fc
        L_0x00b8:
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r1 = r8.ae
            ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean r1 = r1.getUsuarios()
            ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean r2 = r9.getUsuarios()
            r8.a(r1, r2)
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1
            r1.<init>(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r2 = r8.ae     // Catch:{ ParseException -> 0x00f8 }
            java.lang.String r2 = r2.getFechaInicio()     // Catch:{ ParseException -> 0x00f8 }
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_DASH     // Catch:{ ParseException -> 0x00f8 }
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1     // Catch:{ ParseException -> 0x00f8 }
            java.lang.String r2 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r2, r3, r4)     // Catch:{ ParseException -> 0x00f8 }
            java.util.Date r2 = r1.parse(r2)     // Catch:{ ParseException -> 0x00f8 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r3 = r8.ae     // Catch:{ ParseException -> 0x00f8 }
            java.lang.String r3 = r3.getFechaFin()     // Catch:{ ParseException -> 0x00f8 }
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_DASH     // Catch:{ ParseException -> 0x00f8 }
            java.lang.String r5 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1     // Catch:{ ParseException -> 0x00f8 }
            java.lang.String r3 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r3, r4, r5)     // Catch:{ ParseException -> 0x00f8 }
            java.util.Date r1 = r1.parse(r3)     // Catch:{ ParseException -> 0x00f8 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean r3 = r9.getUsuarios()     // Catch:{ ParseException -> 0x00f8 }
            r8.b(r3, r2, r1)     // Catch:{ ParseException -> 0x00f8 }
            goto L_0x00fc
        L_0x00f8:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00fc:
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r1 = r8.ae
            ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean r2 = r9.getUsuarios()
            r1.setUsuarios(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r1 = r8.ae
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r2 = r8.ae
            ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean r2 = r2.getUsuarios()
            int r2 = r8.b(r2)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r1.setTotalTarjetas(r2)
        L_0x0118:
            r8.llenarLblDestinosTarjetas()
            android.widget.TextView r1 = r8.lblDataConsultarEditarCantDestinos
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$14 r2 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$14
            r2.<init>(r9)
            r1.setOnClickListener(r2)
            android.widget.TextView r9 = r8.lblDataConsultarEditarCantTarjetas
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$15 r1 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$15
            r1.<init>()
            r9.setOnClickListener(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r9 = r8.ae
            java.lang.String r9 = r9.getMail()
            boolean r9 = r9.isEmpty()
            r1 = 8
            if (r9 != 0) goto L_0x0161
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r9 = r8.ae
            java.lang.String r9 = r9.getMail()
            java.lang.String r2 = ""
            boolean r9 = r9.equalsIgnoreCase(r2)
            if (r9 != 0) goto L_0x0161
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r9 = r8.ae
            java.lang.String r9 = r9.getMail()
            if (r9 != 0) goto L_0x0154
            goto L_0x0161
        L_0x0154:
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r2 = r8.ae
            java.lang.String r2 = r2.getMail()
            r9.setText(r2)
            goto L_0x01ea
        L_0x0161:
            boolean r9 = r8.A()
            if (r9 == 0) goto L_0x01e0
            ar.com.santander.rio.mbanking.managers.session.SessionManager r9 = r8.h
            ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean r9 = r9.getLoginUnico()
            ar.com.santander.rio.mbanking.services.model.general.DestinosMyA r9 = r9.getDestinosMyA()
            ar.com.santander.rio.mbanking.services.model.general.Destinos r9 = r9.getDestinos()
            java.util.List r9 = r9.getDestinos()
            boolean r9 = r9.isEmpty()
            if (r9 != 0) goto L_0x01e0
            ar.com.santander.rio.mbanking.managers.session.SessionManager r9 = r8.h
            ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean r9 = r9.getLoginUnico()
            ar.com.santander.rio.mbanking.services.model.general.DestinosMyA r9 = r9.getDestinosMyA()
            ar.com.santander.rio.mbanking.services.model.general.Destinos r9 = r9.getDestinos()
            java.util.List r9 = r9.getDestinos()
            java.util.Iterator r9 = r9.iterator()
        L_0x0195:
            boolean r2 = r9.hasNext()
            if (r2 == 0) goto L_0x01ea
            java.lang.Object r2 = r9.next()
            ar.com.santander.rio.mbanking.services.model.general.Destino r2 = (ar.com.santander.rio.mbanking.services.model.general.Destino) r2
            java.lang.String r3 = r2.getDestinoTipo()
            java.lang.String r4 = "MAIL"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01d5
            java.lang.String r9 = r2.getDestinoDescripcion()
            java.lang.String r3 = ""
            boolean r9 = r9.equalsIgnoreCase(r3)
            if (r9 != 0) goto L_0x01ca
            java.lang.String r9 = r2.getDestinoDescripcion()
            if (r9 != 0) goto L_0x01c0
            goto L_0x01ca
        L_0x01c0:
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            java.lang.String r2 = r2.getDestinoDescripcion()
            r9.setText(r2)
            goto L_0x01ea
        L_0x01ca:
            android.widget.RelativeLayout r9 = r8.rowMail
            r9.setVisibility(r1)
            android.view.View r9 = r8.separador
            r9.setVisibility(r1)
            goto L_0x01ea
        L_0x01d5:
            android.widget.RelativeLayout r2 = r8.rowMail
            r2.setVisibility(r1)
            android.view.View r2 = r8.separador
            r2.setVisibility(r1)
            goto L_0x0195
        L_0x01e0:
            android.widget.RelativeLayout r9 = r8.rowMail
            r9.setVisibility(r1)
            android.view.View r9 = r8.separador
            r9.setVisibility(r1)
        L_0x01ea:
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$16 r2 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$16
            r2.<init>()
            r9.addTextChangedListener(r2)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$17 r2 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$17
            r2.<init>()
            r9.setOnFocusChangeListener(r2)
            android.widget.Button r9 = r8.btnEditarDetalleViajeGuardar
            r9.setOnClickListener(r8)
            ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean r9 = r8.ae
            java.lang.String r9 = r9.getAcciones()
            r2 = -1
            int r3 = r9.hashCode()
            r4 = 1
            switch(r3) {
                case 48: goto L_0x023b;
                case 49: goto L_0x0231;
                case 50: goto L_0x0227;
                case 51: goto L_0x021d;
                case 52: goto L_0x0213;
                default: goto L_0x0212;
            }
        L_0x0212:
            goto L_0x0245
        L_0x0213:
            java.lang.String r3 = "4"
            boolean r9 = r9.equals(r3)
            if (r9 == 0) goto L_0x0245
            r9 = 4
            goto L_0x0246
        L_0x021d:
            java.lang.String r3 = "3"
            boolean r9 = r9.equals(r3)
            if (r9 == 0) goto L_0x0245
            r9 = 3
            goto L_0x0246
        L_0x0227:
            java.lang.String r3 = "2"
            boolean r9 = r9.equals(r3)
            if (r9 == 0) goto L_0x0245
            r9 = 2
            goto L_0x0246
        L_0x0231:
            java.lang.String r3 = "1"
            boolean r9 = r9.equals(r3)
            if (r9 == 0) goto L_0x0245
            r9 = 1
            goto L_0x0246
        L_0x023b:
            java.lang.String r3 = "0"
            boolean r9 = r9.equals(r3)
            if (r9 == 0) goto L_0x0245
            r9 = 0
            goto L_0x0246
        L_0x0245:
            r9 = -1
        L_0x0246:
            r2 = 2131756070(0x7f100426, float:1.9143037E38)
            r3 = 2131230951(0x7f0800e7, float:1.807797E38)
            r5 = 0
            r6 = 2131099752(0x7f060068, float:1.7811866E38)
            r7 = 2131099768(0x7f060078, float:1.7811899E38)
            switch(r9) {
                case 0: goto L_0x0519;
                case 1: goto L_0x047d;
                case 2: goto L_0x03ea;
                case 3: goto L_0x035b;
                case 4: goto L_0x0258;
                default: goto L_0x0256;
            }
        L_0x0256:
            goto L_0x05ac
        L_0x0258:
            android.widget.TextView r9 = r8.lblHabilitarTitulo
            r1 = 2131756061(0x7f10041d, float:1.9143019E38)
            r9.setText(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaDesde
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskSelector(r1)
            r9.setContentDescription(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaHasta
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskSelector(r1)
            r9.setContentDescription(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            r1 = 2131755406(0x7f10018e, float:1.914169E38)
            java.lang.String r2 = r8.getString(r1)
            r9.setText(r2)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            java.lang.String r1 = r8.getString(r1)
            r9.setText(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$22 r1 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$22
            r1.<init>()
            r9.setOnClickListener(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$23 r1 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$23
            r1.<init>()
            r9.setOnClickListener(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r1 = r8.getResources()
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r3)
            r9.setBackground(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r7)
            r9.setTextColor(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setHintTextColor(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            r9.setEnabled(r4)
            boolean r9 = r8.A()
            if (r9 == 0) goto L_0x0330
            ar.com.santander.rio.mbanking.managers.session.SessionManager r9 = r8.h
            ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean r9 = r9.getLoginUnico()
            ar.com.santander.rio.mbanking.services.model.general.DestinosMyA r9 = r9.getDestinosMyA()
            ar.com.santander.rio.mbanking.services.model.general.Destinos r9 = r9.getDestinos()
            java.util.List r9 = r9.getDestinos()
            boolean r9 = r9.isEmpty()
            if (r9 != 0) goto L_0x0330
            ar.com.santander.rio.mbanking.managers.session.SessionManager r9 = r8.h
            ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean r9 = r9.getLoginUnico()
            ar.com.santander.rio.mbanking.services.model.general.DestinosMyA r9 = r9.getDestinosMyA()
            ar.com.santander.rio.mbanking.services.model.general.Destinos r9 = r9.getDestinos()
            java.util.List r9 = r9.getDestinos()
            java.util.Iterator r9 = r9.iterator()
        L_0x030f:
            boolean r1 = r9.hasNext()
            if (r1 == 0) goto L_0x0330
            java.lang.Object r1 = r9.next()
            ar.com.santander.rio.mbanking.services.model.general.Destino r1 = (ar.com.santander.rio.mbanking.services.model.general.Destino) r1
            java.lang.String r2 = r1.getDestinoTipo()
            java.lang.String r3 = "MAIL"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x030f
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            java.lang.String r1 = r1.getDestinoDescripcion()
            r9.setText(r1)
        L_0x0330:
            android.widget.RelativeLayout r9 = r8.rowMail
            r9.setVisibility(r0)
            android.view.View r9 = r8.separador
            r9.setVisibility(r0)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setTextColor(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setTextColor(r1)
            android.widget.Button r9 = r8.btnEditarDetalleViajeGuardar
            r9.setVisibility(r0)
            goto L_0x05ac
        L_0x035b:
            r8.configureActionBarWithMenu()
            android.widget.TextView r9 = r8.lblHabilitarTitulo
            r9.setText(r2)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaDesde
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskSelector(r1)
            r9.setContentDescription(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaHasta
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskSelector(r1)
            r9.setContentDescription(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$20 r1 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$20
            r1.<init>()
            r9.setOnClickListener(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$21 r1 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$21
            r1.<init>()
            r9.setOnClickListener(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r7)
            r9.setTextColor(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r1 = r8.getResources()
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r3)
            r9.setBackground(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            r9.setEnabled(r4)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setTextColor(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setTextColor(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setHintTextColor(r1)
            android.widget.Button r9 = r8.btnEditarDetalleViajeGuardar
            r9.setVisibility(r0)
            goto L_0x05ac
        L_0x03ea:
            r8.configureActionBarWithMenu()
            android.widget.TextView r9 = r8.lblHabilitarTitulo
            r9.setText(r2)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            r9.setBackground(r5)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            r9.setEnabled(r0)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            android.content.res.Resources r0 = r8.getResources()
            int r0 = r0.getColor(r7)
            r9.setTextColor(r0)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            r9.setOnClickListener(r5)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            r9.setOnClickListener(r5)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            android.content.res.Resources r0 = r8.getResources()
            int r0 = r0.getColor(r7)
            r9.setTextColor(r0)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r0 = r8.getResources()
            int r0 = r0.getColor(r7)
            r9.setTextColor(r0)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r0 = r8.getResources()
            int r0 = r0.getColor(r7)
            r9.setHintTextColor(r0)
            android.widget.Button r9 = r8.btnEditarDetalleViajeGuardar
            r9.setVisibility(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde     // Catch:{ Exception -> 0x0477 }
            android.content.Context r0 = r8.getContext()     // Catch:{ Exception -> 0x0477 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x0477 }
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaDesde     // Catch:{ Exception -> 0x0477 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0477 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0477 }
            java.lang.String r0 = r0.applyFilterGeneral(r1)     // Catch:{ Exception -> 0x0477 }
            r9.setContentDescription(r0)     // Catch:{ Exception -> 0x0477 }
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta     // Catch:{ Exception -> 0x0477 }
            android.content.Context r0 = r8.getContext()     // Catch:{ Exception -> 0x0477 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x0477 }
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaHasta     // Catch:{ Exception -> 0x0477 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0477 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0477 }
            java.lang.String r0 = r0.applyFilterGeneral(r1)     // Catch:{ Exception -> 0x0477 }
            r9.setContentDescription(r0)     // Catch:{ Exception -> 0x0477 }
            goto L_0x05ac
        L_0x0477:
            r9 = move-exception
            r9.printStackTrace()
            goto L_0x05ac
        L_0x047d:
            r8.configureActionBarBackOnly()
            android.widget.TextView r9 = r8.lblHabilitarTitulo
            r9.setText(r2)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaDesde
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskSelector(r1)
            r9.setContentDescription(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaHasta
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskSelector(r1)
            r9.setContentDescription(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$18 r1 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$18
            r1.<init>()
            r9.setOnClickListener(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setTextColor(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setTextColor(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$19 r1 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment$19
            r1.<init>()
            r9.setOnClickListener(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setTextColor(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r7)
            r9.setTextColor(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r1 = r8.getResources()
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r3)
            r9.setBackground(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r1 = r8.getResources()
            int r1 = r1.getColor(r6)
            r9.setHintTextColor(r1)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            r9.setEnabled(r4)
            android.widget.Button r9 = r8.btnEditarDetalleViajeGuardar
            r9.setVisibility(r0)
            goto L_0x05ac
        L_0x0519:
            r8.configureActionBarBackOnly()
            android.widget.TextView r9 = r8.lblHabilitarTitulo
            r2 = 2131756408(0x7f100578, float:1.9143723E38)
            r9.setText(r2)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            android.content.res.Resources r2 = r8.getResources()
            int r2 = r2.getColor(r7)
            r9.setTextColor(r2)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde
            r9.setOnClickListener(r5)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            android.content.res.Resources r2 = r8.getResources()
            int r2 = r2.getColor(r7)
            r9.setTextColor(r2)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta
            r9.setOnClickListener(r5)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r2 = r8.getResources()
            int r2 = r2.getColor(r7)
            r9.setTextColor(r2)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            android.content.res.Resources r2 = r8.getResources()
            int r2 = r2.getColor(r7)
            r9.setHintTextColor(r2)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            r9.setBackground(r5)
            android.widget.EditText r9 = r8.lblDataConsultarEditarEmail
            r9.setEnabled(r0)
            android.widget.Button r9 = r8.btnEditarDetalleViajeGuardar
            r9.setVisibility(r1)
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaDesde     // Catch:{ Exception -> 0x05a8 }
            android.content.Context r0 = r8.getContext()     // Catch:{ Exception -> 0x05a8 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x05a8 }
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaDesde     // Catch:{ Exception -> 0x05a8 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x05a8 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x05a8 }
            java.lang.String r0 = r0.applyFilterGeneral(r1)     // Catch:{ Exception -> 0x05a8 }
            r9.setContentDescription(r0)     // Catch:{ Exception -> 0x05a8 }
            android.widget.TextView r9 = r8.lblDataConsultarEditarFechaHasta     // Catch:{ Exception -> 0x05a8 }
            android.content.Context r0 = r8.getContext()     // Catch:{ Exception -> 0x05a8 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x05a8 }
            android.widget.TextView r1 = r8.lblDataConsultarEditarFechaHasta     // Catch:{ Exception -> 0x05a8 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x05a8 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x05a8 }
            java.lang.String r0 = r0.applyFilterGeneral(r1)     // Catch:{ Exception -> 0x05a8 }
            r9.setContentDescription(r0)     // Catch:{ Exception -> 0x05a8 }
            goto L_0x05ac
        L_0x05a8:
            r9 = move-exception
            r9.printStackTrace()
        L_0x05ac:
            r8.validarBotonGuardar()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment.setMarcacionPorViajeConsultaView(ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean):void");
    }

    private ArrayList<String> a(UsuariosMarcacionBean usuariosMarcacionBean, Date date, Date date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.FORMAT_DATE_WS_1);
        ArrayList<String> arrayList = new ArrayList<>();
        for (UsuarioMarcacionBean tarjetas : usuariosMarcacionBean.getListaUsuarios()) {
            for (TarjetaMarcacionBean tarjetaMarcacionBean : tarjetas.getTarjetas().getListaTarjetas()) {
                if (tarjetaMarcacionBean.isSelected().booleanValue()) {
                    List<String> listaFechas = tarjetaMarcacionBean.getListaFechasInhabilitadas().getListaFechas();
                    if (!listaFechas.isEmpty()) {
                        for (String dateFormat : listaFechas) {
                            try {
                                Date parse = simpleDateFormat.parse(UtilDate.getDateFormat(dateFormat, Constants.FORMAT_DATE_WS_2, Constants.FORMAT_DATE_WS_1));
                                if ((UtilDate.isDateAfter(parse, date) || UtilDate.isDateEqual(parse, date)) && ((UtilDate.isDateBefore(parse, date2) || UtilDate.isDateEqual(parse, date)) && !arrayList.contains(tarjetaMarcacionBean.getDescripcion()))) {
                                    arrayList.add(tarjetaMarcacionBean.getDescripcion());
                                }
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private String a(List<String> list) {
        String string = getString(R.string.IDXX_MARCACION_VIAJE_TARJETAS_INHABILITADAS_ERROR);
        String str = "";
        for (String str2 : list) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(", ");
            str = str.concat(sb.toString());
        }
        return String.format(string, new Object[]{str});
    }

    /* access modifiers changed from: private */
    public void a(TextView textView, String str, Boolean bool) {
        crearSelectorFecha(textView, str, bool).show(getActivity().getSupportFragmentManager(), this.d);
    }

    public IsbanDatePickerDialogFragment crearSelectorFecha(final TextView textView, final String str, final Boolean bool) {
        String str2;
        if (bool == a) {
            str2 = this.ae.getFechaInicio();
        } else {
            str2 = this.ae.getFechaFin();
        }
        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), UtilDate.getDateFormat(str2, Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_APP), Constants.FORMAT_DATE_APP);
        newInstance.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                textView.setText(UtilDate.getDateFormat(UtilDate.getDateFormat(date, Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, MarcacionPorViajeFragment.this.getString(R.string.FORMAT_FULL_DATE)));
                TextView textView = textView;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(textView.getText().toString());
                textView.setContentDescription(CAccessibility.applyFilterMaskSelector(sb.toString()));
                String dateFormat = UtilDate.getDateFormat(date, Constants.FORMAT_DATE_APP_2);
                if (bool == MarcacionPorViajeFragment.a) {
                    MarcacionPorViajeFragment.this.ae.setFechaInicio(UtilDate.getDateFormat(dateFormat, Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_DASH));
                } else {
                    MarcacionPorViajeFragment.this.ae.setFechaFin(UtilDate.getDateFormat(dateFormat, Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_DASH));
                }
                MarcacionPorViajeFragment.this.validarBotonGuardar();
            }
        });
        return newInstance;
    }

    private int b(UsuariosMarcacionBean usuariosMarcacionBean) {
        int i2 = 0;
        for (UsuarioMarcacionBean tarjetas : usuariosMarcacionBean.getListaUsuarios()) {
            for (TarjetaMarcacionBean isSelected : tarjetas.getTarjetas().getListaTarjetas()) {
                if (isSelected.isSelected().booleanValue()) {
                    i2++;
                }
            }
        }
        return i2;
    }

    public void gotoComprobante(Boolean bool, String str, String str2) {
        gotoPage(3);
        y();
        this.ai.onCreatePage(bool, str, str2);
    }

    public void setMarcacionPorViajeDestinosView(ViajeBean viajeBean, GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean) {
        Context context = getContext();
        this.listaPaisesCompleta.clear();
        this.listaPaisesDestino.clear();
        ArrayList<PaisBean> arrayList = new ArrayList<>();
        this.ad = Boolean.valueOf(false);
        if (getTarjPaisesBodyResponseBean != null) {
            for (PaisBean paisBean : getTarjPaisesBodyResponseBean.getPaises().getListaPaises()) {
                this.listaPaisesCompleta.add(new PaisBean(paisBean.getId().toString(), paisBean.getDescripcion().toString()));
            }
        } else if (getTarjPaisesBodyResponseBean == null || !this.ae.getPaises().getListaPaises().isEmpty()) {
            for (PaisBean paisBean2 : this.ae.getPaises().getListaPaises()) {
                this.listaPaisesDestino.add(new PaisBean(paisBean2.getId().toString(), paisBean2.getDescripcion().toString()));
            }
        }
        if (!this.ae.getPaises().getListaPaises().isEmpty()) {
            for (PaisBean paisBean3 : this.ae.getPaises().getListaPaises()) {
                for (PaisBean paisBean4 : this.listaPaisesCompleta) {
                    if (paisBean4.getDescripcion().toLowerCase().equals(paisBean3.getDescripcion().toLowerCase())) {
                        this.listaPaisesDestino.add(paisBean4);
                        arrayList.add(paisBean4);
                    }
                }
            }
        }
        if (!arrayList.isEmpty()) {
            for (PaisBean remove : arrayList) {
                this.listaPaisesCompleta.remove(remove);
            }
        }
        if (this.lnlSearch.getVisibility() == 0) {
            this.txtSearch.callOnClear();
        }
        final MarcacionDestinosSeleccionadosAdapter marcacionDestinosSeleccionadosAdapter = new MarcacionDestinosSeleccionadosAdapter(getContext(), R.layout.marcacion_row_lista_destinos_seleccionados, this.listaPaisesDestino, Integer.parseInt(this.ae.getAcciones()));
        this.lvPaisesDestino.setAdapter(marcacionDestinosSeleccionadosAdapter);
        if (this.ae.getAcciones().equals("0") || this.ae.getAcciones().equals("2")) {
            this.barraBusqueda.setVisibility(8);
            this.btnDestinoGuardar.setVisibility(4);
            this.i.trackScreen(getString(R.string.analytics_screen_name_travels_destinos_seleccionados));
            return;
        }
        if (this.listaPaisesDestino.isEmpty()) {
            this.txtAgregarPaises.setVisibility(0);
        } else {
            this.txtAgregarPaises.setVisibility(8);
        }
        this.barraBusqueda.setVisibility(0);
        final MarcacionSearchListDestinos marcacionSearchListDestinos = new MarcacionSearchListDestinos(context, R.layout.marcacion_row_lista_destinos, this.c);
        marcacionDestinosSeleccionadosAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void OnItemClick(View view) {
                PaisBean paisBean;
                String charSequence = ((Button) view.findViewById(R.id.F26_MARCACION_LISTADESTINOS_CLEAR_BUTTON)).getText().toString();
                Iterator it = MarcacionPorViajeFragment.this.listaPaisesDestino.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        paisBean = null;
                        break;
                    }
                    paisBean = (PaisBean) it.next();
                    if (Html.fromHtml(paisBean.getDescripcion()).toString().equalsIgnoreCase(charSequence)) {
                        break;
                    }
                }
                MarcacionPorViajeFragment.this.listaPaisesDestino.remove(paisBean);
                MarcacionPorViajeFragment.this.listaPaisesCompleta.add(paisBean);
                MarcacionPorViajeFragment.this.ad = Boolean.valueOf(true);
                marcacionDestinosSeleccionadosAdapter.notifyDataSetChanged();
                if (MarcacionPorViajeFragment.this.listaPaisesDestino.isEmpty()) {
                    MarcacionPorViajeFragment.this.txtAgregarPaises.setVisibility(0);
                } else {
                    MarcacionPorViajeFragment.this.txtAgregarPaises.setVisibility(8);
                }
            }
        });
        this.txtSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_seleccionar), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_texto_predictivo_destino));
                } else if (MarcacionPorViajeFragment.this.listaPaisesDestino.isEmpty()) {
                    MarcacionPorViajeFragment.this.txtAgregarPaises.setVisibility(0);
                } else {
                    MarcacionPorViajeFragment.this.txtAgregarPaises.setVisibility(8);
                }
            }
        });
        KeyboardUtils.setKeyboardVisibilityListener(getActivity(), new KeyboardVisibilityListener() {
            public void onKeyboardVisibilityChanged(boolean z) {
                if (z) {
                    MarcacionPorViajeFragment.this.btnDestinoGuardar.setVisibility(8);
                } else {
                    MarcacionPorViajeFragment.this.btnDestinoGuardar.setVisibility(0);
                }
            }
        });
        this.txtSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                MarcacionPorViajeFragment.this.c.clear();
                for (PaisBean paisBean : MarcacionPorViajeFragment.this.listaPaisesCompleta) {
                    if (paisBean.getDescripcion().toLowerCase().contains(editable.toString().toLowerCase())) {
                        MarcacionPorViajeFragment.this.c.add(paisBean);
                    }
                }
                MarcacionPorViajeFragment.this.listaBarraBusqueda.setAdapter(marcacionSearchListDestinos);
                if (!TextUtils.isEmpty(editable)) {
                    MarcacionPorViajeFragment.this.lnlSearch.setVisibility(0);
                    if (MarcacionPorViajeFragment.this.c.size() == 0) {
                        MarcacionPorViajeFragment.this.c.add(new PaisBean("", MarcacionPorViajeFragment.this.getString(R.string.F26_00_MENSAJE_NO_SE_ENCONTRARON_PAISES)));
                    }
                } else {
                    MarcacionPorViajeFragment.this.lnlSearch.setVisibility(8);
                }
                marcacionSearchListDestinos.notifyDataSetChanged();
            }
        });
        marcacionSearchListDestinos.setOnItemClickListener(this);
        this.btnDestinoGuardar.setVisibility(0);
        this.btnDestinoGuardar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MarcacionPorViajeFragment.this.ae.getPaises().getListaPaises().clear();
                MarcacionPorViajeFragment.this.a(MarcacionPorViajeFragment.this.ae);
                MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_guardar_boton), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_destinos));
                MarcacionPorViajeFragment.this.goBackToDetalleViaje();
            }
        });
        this.i.trackScreen(getString(R.string.analytics_screen_name_travels_agregar_destino));
    }

    public void setMarcacionPorViajeView(ViajesBean viajesBean, String str) {
        this.al = str;
        b(str);
        setMarcacionPorViajeView(viajesBean);
    }

    public void setMarcacionPorViajeView(ViajesBean viajesBean) {
        configureActionBar();
        Boolean valueOf = Boolean.valueOf(true);
        Boolean.valueOf(true);
        List tarjetas = this.h.getLoginUnico().getProductos().getTarjetas().getTarjetas();
        for (ViajeBean usuarios : viajesBean.getListaViajes()) {
            a(usuarios.getUsuarios(), Boolean.valueOf(true));
        }
        Iterator it = tarjetas.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            } else if (!((Tarjeta) it.next()).getClase().equals("N")) {
                valueOf = Boolean.valueOf(false);
                break;
            } else {
                valueOf = Boolean.valueOf(true);
            }
        }
        Iterator it2 = tarjetas.iterator();
        while (true) {
            if (it2.hasNext()) {
                if (!((Tarjeta) it2.next()).getClase().equals("H")) {
                    Boolean.valueOf(false);
                    break;
                }
            } else {
                break;
            }
        }
        char c2 = valueOf.booleanValue() ? 1 : viajesBean.getListaViajes().isEmpty() ? (char) 2 : 3;
        switch (c2) {
            case 1:
                this.layoutCruzHabilitar.setVisibility(0);
                this.lvItems.setVisibility(8);
                this.btnInformarViajeHabilitar.setEnabled(false);
                this.btnInformarViajeHabilitar.setBackground(getResources().getDrawable(R.drawable.button_dissable));
                this.txtHabilitarTarjetasHabilitar.setText(getString(R.string.ID4010_MARCACION_VIAJE_LBL_LEYENDA_SELECCIONAR_TARJETAS));
                return;
            case 2:
                this.layoutCruzHabilitar.setVisibility(0);
                this.lvItems.setVisibility(8);
                this.btnInformarViajeHabilitar.setEnabled(true);
                this.btnInformarViajeHabilitar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
                this.txtHabilitarTarjetasHabilitar.setText(getString(R.string.F26_TXT_HABILITA_TUS_TARJETAS_INTERNACIONALES));
                this.btnInformarViajeHabilitar.setOnClickListener(this);
                this.i.trackScreen(getString(R.string.analytics_screen_name_travels_sin_tarjetas_habilitadas));
                return;
            case 3:
                this.layoutCruzHabilitar.setVisibility(8);
                this.lvItems.setVisibility(0);
                this.btnInformarViajeHabilitar.setEnabled(true);
                this.btnInformarViajeHabilitar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
                this.btnInformarViajeHabilitar.setOnClickListener(this);
                agregarViajes(viajesBean);
                this.i.trackScreen(getString(R.string.analytics_screen_name_travels_resumen_tarjetas));
                return;
            default:
                return;
        }
    }

    private void b(UsuariosMarcacionBean usuariosMarcacionBean, Date date, Date date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.FORMAT_DATE_WS_1);
        for (UsuarioMarcacionBean tarjetas : usuariosMarcacionBean.getListaUsuarios()) {
            for (TarjetaMarcacionBean tarjetaMarcacionBean : tarjetas.getTarjetas().getListaTarjetas()) {
                if (tarjetaMarcacionBean.isSelected().booleanValue()) {
                    ArrayList arrayList = new ArrayList();
                    for (String str : tarjetaMarcacionBean.getListaFechasInhabilitadas().getListaFechas()) {
                        try {
                            Date parse = simpleDateFormat.parse(UtilDate.getDateFormat(str, Constants.FORMAT_DATE_WS_2, Constants.FORMAT_DATE_WS_1));
                            if ((UtilDate.isDateAfter(parse, date) || UtilDate.isDateEqual(parse, date)) && (UtilDate.isDateBefore(parse, date2) || UtilDate.isDateEqual(parse, date2))) {
                                arrayList.add(str);
                            }
                        } catch (ParseException e2) {
                            e2.printStackTrace();
                        }
                    }
                    tarjetaMarcacionBean.getListaFechasInhabilitadas().getListaFechas().removeAll(arrayList);
                }
            }
        }
    }

    private void a(UsuariosMarcacionBean usuariosMarcacionBean, UsuariosMarcacionBean usuariosMarcacionBean2) {
        for (UsuarioMarcacionBean usuarioMarcacionBean : usuariosMarcacionBean2.getListaUsuarios()) {
            for (TarjetaMarcacionBean tarjetaMarcacionBean : usuarioMarcacionBean.getTarjetas().getListaTarjetas()) {
                for (UsuarioMarcacionBean usuarioMarcacionBean2 : usuariosMarcacionBean.getListaUsuarios()) {
                    if (usuarioMarcacionBean.getNombre().equalsIgnoreCase(usuarioMarcacionBean2.getNombre())) {
                        usuarioMarcacionBean.setOtrasTarjetas(usuarioMarcacionBean2.getOtrasTarjetas() != null ? usuarioMarcacionBean2.getOtrasTarjetas() : "");
                    }
                    for (TarjetaMarcacionBean tarjetaMarcacionBean2 : usuarioMarcacionBean2.getTarjetas().getListaTarjetas()) {
                        if (tarjetaMarcacionBean.getDescripcion().equalsIgnoreCase(tarjetaMarcacionBean2.getDescripcion()) && tarjetaMarcacionBean.getCondicion().equalsIgnoreCase(tarjetaMarcacionBean2.getCondicion())) {
                            tarjetaMarcacionBean.setSelected(tarjetaMarcacionBean2.isSelected());
                        }
                    }
                }
            }
        }
    }

    private void a(UsuariosMarcacionBean usuariosMarcacionBean, Boolean bool) {
        for (UsuarioMarcacionBean tarjetas : usuariosMarcacionBean.getListaUsuarios()) {
            for (TarjetaMarcacionBean selected : tarjetas.getTarjetas().getListaTarjetas()) {
                selected.setSelected(bool);
            }
        }
    }

    public void agregarViajes(ViajesBean viajesBean) {
        final List listaViajes = viajesBean.getListaViajes();
        this.lvItems.setAdapter(new MarcacionListaViajesAdapter(getContext(), R.layout.viaje_lista_fragment, listaViajes));
        this.lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                MarcacionPorViajeFragment.this.ae = ((ViajeBean) listaViajes.get(i)).clone();
                MarcacionPorViajeFragment.this.ae.setFechaInicio(UtilDate.getDateFormat(MarcacionPorViajeFragment.this.ae.getFechaInicio(), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_DASH));
                MarcacionPorViajeFragment.this.ae.setFechaFin(UtilDate.getDateFormat(MarcacionPorViajeFragment.this.ae.getFechaFin(), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_DASH));
                if (MarcacionPorViajeFragment.this.ae.getAcciones().equals("0") || MarcacionPorViajeFragment.this.ae.getAcciones().equals("2")) {
                    MarcacionPorViajeFragment.this.goToDetalleViajeNoEditable(MarcacionPorViajeFragment.this.ae);
                } else if (MarcacionPorViajeFragment.this.ae.getAcciones().equals("1") || MarcacionPorViajeFragment.this.ae.getAcciones().equals("3")) {
                    MarcacionPorViajeFragment.this.af.onGetTarjPaises();
                } else if (MarcacionPorViajeFragment.this.ae.getAcciones().isEmpty() || MarcacionPorViajeFragment.this.ae.getAcciones() == null || MarcacionPorViajeFragment.this.ae.getAcciones().equalsIgnoreCase("")) {
                    MarcacionPorViajeFragment.this.ae.setAcciones("0");
                    MarcacionPorViajeFragment.this.goToDetalleViajeNoEditable(MarcacionPorViajeFragment.this.ae);
                }
            }
        });
    }

    public void setMarcacionPorViajeRes4View(String str, String str2) {
        b(str2);
        this.layoutCruzHabilitar.setVisibility(8);
        this.lvItems.setVisibility(8);
        this.btnInformarViajeHabilitar.setVisibility(8);
        this.lnlTenenciaRes4Error.setVisibility(0);
        this.txtTenenciaRes4Error.setText(Html.fromHtml(str));
    }

    public void goToDetalleViajeNoEditable(ViajeBean viajeBean) {
        gotoPage(1);
        this.ag.onCreatePage(null);
    }

    public void goToDetalleViajeEditable(GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean) {
        gotoPage(1);
        this.ag.onCreatePage(getTarjPaisesBodyResponseBean);
        this.aj = getTarjPaisesBodyResponseBean;
    }

    public void OnItemClick(View view) {
        String charSequence = ((TextView) view.findViewById(R.id.F26_MARCACION_ROW_DESTINOS_DATA)).getText().toString();
        if (!charSequence.equals(getString(R.string.F26_00_MENSAJE_NO_SE_ENCONTRARON_PAISES))) {
            PaisBean paisBean = new PaisBean();
            for (PaisBean paisBean2 : this.listaPaisesCompleta) {
                if (Html.fromHtml(charSequence).toString().equalsIgnoreCase(Html.fromHtml(paisBean2.getDescripcion()).toString())) {
                    paisBean = paisBean2;
                }
            }
            if (!paisBean.getDescripcion().equals(getString(R.string.F26_00_MENSAJE_NO_SE_ENCONTRARON_PAISES))) {
                this.txtSearch.callOnClear();
                this.listaPaisesDestino.add(paisBean);
                this.listaPaisesCompleta.remove(paisBean);
                this.ad = Boolean.valueOf(true);
                if (this.listaPaisesDestino.isEmpty()) {
                    this.txtAgregarPaises.setVisibility(0);
                } else {
                    this.txtAgregarPaises.setVisibility(8);
                }
            }
        }
    }

    public void gotoPage(int i2) {
        gotoPage(i2, true);
    }

    public void gotoPage(int i2, boolean z) {
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
                    if (this.mControlPager.getDisplayedChild() != 3) {
                        if (!z) {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.noAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToDownAnimation());
                            break;
                        } else {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                            break;
                        }
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                case 2:
                    if (z) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.noAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.outToUpAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToDownAnimation());
                        break;
                    }
                case 3:
                    if (!z) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
            }
            this.mControlPager.setDisplayedChild(i2);
            attachView();
        }
    }

    public void goBackToMarcacionViajeHabilitar(Boolean bool) {
        gotoPage(0, false);
        b(this.al);
        if (bool.booleanValue()) {
            this.af.callGetViajes();
        }
    }

    public void goBackToDetalleViaje() {
        gotoPage(1, false);
        this.lblDataConsultarEditarCantDestinos.setText(this.ae.getTotalDestinos());
        if (this.ae.getAcciones().equals("2") || this.ae.getAcciones().equals("3")) {
            configureActionBarWithMenu();
        } else if (this.ae.getAcciones().equals("0") || this.ae.getAcciones().equals("1") || this.ae.getAcciones().equals("4")) {
            configureActionBarBackOnly();
        }
        llenarLblDestinosTarjetas();
        validarBotonGuardar();
    }

    public void llenarLblDestinosTarjetas() {
        String str;
        String str2;
        if (Integer.parseInt(this.ae.getTotalDestinos()) > 1 || Integer.parseInt(this.ae.getTotalDestinos()) == 0) {
            str = getString(R.string.F26_00_ITEM_DESTINOS);
        } else {
            str = getString(R.string.F26_00_ITEM_DESTINO);
        }
        TextView textView = this.lblDataConsultarEditarCantDestinos;
        StringBuilder sb = new StringBuilder();
        sb.append(this.ae.getTotalDestinos());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(str);
        textView.setText(sb.toString());
        try {
            TextView textView2 = this.lblDataConsultarEditarCantDestinos;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(CAccessibility.getInstance(getContext()).applyFilterNumberOne(this.ae.getTotalDestinos()));
            sb2.append(str);
            textView2.setContentDescription(sb2.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (Integer.parseInt(this.ae.getTotalTarjetas()) > 1 || Integer.parseInt(this.ae.getTotalTarjetas()) == 0) {
            str2 = getString(R.string.F26_00_ITEM_TARJETAS);
        } else {
            str2 = getString(R.string.F26_00_ITEM_TARJETA);
        }
        TextView textView3 = this.lblDataConsultarEditarCantTarjetas;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.ae.getTotalTarjetas());
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(str2);
        textView3.setText(sb3.toString());
        try {
            TextView textView4 = this.lblDataConsultarEditarCantTarjetas;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(CAccessibility.getInstance(getContext()).applyFilterNumberOne(this.ae.getTotalTarjetas()));
            sb4.append(str2);
            textView4.setContentDescription(sb4.toString());
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void goToDestinosViaje(ViajeBean viajeBean, GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean) {
        gotoPage(2);
        configureActionBarCancelWithConfirm();
        this.ah.onCreatePage(viajeBean, getTarjPaisesBodyResponseBean);
    }

    public void goToSeleccionTarjetas(UsuariosMarcacionBean usuariosMarcacionBean) {
        Intent intent = new Intent(getContext(), SeleccionarTarjetasMarcacionActivity.class);
        intent.putExtra(MarcacionViajeConstants.cINTENT_EXTRA_USUARIOS, usuariosMarcacionBean);
        intent.putExtra(MarcacionViajeConstants.cINTENT_EXTRA_ACCION, this.ae.getAcciones());
        startActivityForResult(intent, 999);
    }

    public void onBackPressed() {
        switch (this.mControlPager.getCurrentView().getId()) {
            case R.id.layout_marcacion_consultar_editar_viaje /*2131364941*/:
                goBackToMarcacionViajeHabilitar(Boolean.valueOf(false));
                return;
            case R.id.layout_marcacion_por_viaje_comprobante /*2131364942*/:
                goBackToMarcacionViajeHabilitar(Boolean.valueOf(true));
                return;
            case R.id.layout_marcacion_por_viaje_listadestinos /*2131364944*/:
                if (this.ae.getAcciones().equals("0") || this.ae.getAcciones().equals("2")) {
                    this.i.trackEvent(getString(R.string.analytics_action_travels_habilitacion_tarjeta), getString(R.string.analytics_action_travels_cerrar), getString(R.string.analytics_action_travels_consulta_destinos));
                }
                if (this.ad.booleanValue()) {
                    z();
                    return;
                } else {
                    goBackToDetalleViaje();
                    return;
                }
            default:
                return;
        }
    }

    public void validarBotonGuardar() {
        if (TextUtils.isEmpty(this.ae.getFechaInicio()) || TextUtils.isEmpty(this.ae.getFechaFin()) || Integer.valueOf(this.ae.getTotalDestinos()).intValue() == 0 || Integer.valueOf(this.ae.getTotalTarjetas()).intValue() == 0 || TextUtils.isEmpty(this.lblDataConsultarEditarEmail.getText().toString())) {
            disableBotonGuardar();
        } else {
            enableBotonGuardar();
        }
    }

    public void showInvalidDataPopup(String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpInvalidData", getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, getString(R.string.ID1_ALERT_BTN_ACCEPT));
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
        newInstance.show(getActivity().getSupportFragmentManager(), "invalidDataPopup");
    }

    public void enableBotonGuardar() {
        this.btnEditarDetalleViajeGuardar.setEnabled(true);
        this.btnEditarDetalleViajeGuardar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        this.i.trackScreen(getString(R.string.analytics_screen_name_travels_formulario_completado));
    }

    public void disableBotonGuardar() {
        this.btnEditarDetalleViajeGuardar.setEnabled(false);
        this.btnEditarDetalleViajeGuardar.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
    }

    private void z() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpCancelar", getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.IDXX_MARCACION_VIAJE_CANCELAR_SELECCION), getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                newInstance.dismiss();
                MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_si), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_no_guardar_destinos));
                MarcacionPorViajeFragment.this.configureActionBarBackOnly();
                MarcacionPorViajeFragment.this.goBackToDetalleViaje();
            }

            public void onNegativeButton() {
                newInstance.dismiss();
                MarcacionPorViajeFragment.this.i.trackEvent(MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_habilitacion_tarjeta), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_no), MarcacionPorViajeFragment.this.getString(R.string.analytics_action_travels_guardar_destinos));
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), "popUpCancelar");
    }

    private boolean A() {
        return (this.h.getLoginUnico().getDestinosMyA().getDestinos() == null || this.h.getLoginUnico().getDestinosMyA().getDestinos().getDestinos() == null) ? false : true;
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 != 999 || i3 != -1) {
            return;
        }
        if (this.ae.getAcciones().equals("3") || this.ae.getAcciones().equals("1") || this.ae.getAcciones().equals("4")) {
            UsuariosMarcacionBean usuariosMarcacionBean = (UsuariosMarcacionBean) intent.getParcelableExtra(MarcacionViajeConstants.cINTENT_EXTRA_USUARIOS);
            this.ae.setUsuarios(usuariosMarcacionBean);
            this.ae.setTotalTarjetas(String.valueOf(b(usuariosMarcacionBean)));
            goBackToDetalleViaje();
        }
    }
}
