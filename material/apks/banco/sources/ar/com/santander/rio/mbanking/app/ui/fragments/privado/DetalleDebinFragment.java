package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Html;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.debin.BuscadorDebinPresenter;
import ar.com.santander.rio.mbanking.app.module.debin.DebinActivity;
import ar.com.santander.rio.mbanking.app.module.debin.DebinView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.AbmDebinActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.BuscadorDebinActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.FirmaCredinActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.GestionAdhesionDebinActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.NuevoDebinActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.PreAutorizacionDebinActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.DebinesAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.DebinesAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.STATUS_FLAGS;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleDebinBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListDebinesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCuentasVendedorBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.IHorizontalScrollListListener;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.ToggleItem;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

public class DetalleDebinFragment extends BaseMvpFragment implements DebinView, OnItemClickListener, IHorizontalScrollListListener {
    public static final String LIST_PRE_AUTORIZACIONES = "LIST_PRE_AUTORIZACIONES";
    public static final String MENSAJE_ERROR = "MENSAJE_ERROR";
    public static final String SIGUIENTE_PAGINA = "SIGUIENTE_PAGINA";
    public static final String TAG = TarjetasFragment.class.getName();
    @Inject
    SoftTokenManager a;
    /* access modifiers changed from: private */
    public String ad = UtilDate.getDateFormat(UtilDate.getDateFormat(Calendar.getInstance().getTime(), Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
    /* access modifiers changed from: private */
    public ListDebinesBean ae;
    /* access modifiers changed from: private */
    public String af = "";
    /* access modifiers changed from: private */
    public List<ListDebinesBean> ag = new ArrayList();
    /* access modifiers changed from: private */
    public List<ListTableBean> ah = new ArrayList();
    /* access modifiers changed from: private */
    public String ai;
    /* access modifiers changed from: private */
    public boolean aj = false;
    /* access modifiers changed from: private */
    public int ak;
    /* access modifiers changed from: private */
    public boolean al = false;
    @Inject
    SessionManager b;
    @InjectView(2131363704)
    View blockBuscador;
    @InjectView(2131363705)
    View blockTabsEnviadas;
    @InjectView(2131363706)
    View blockTabsRecibidas;
    @InjectView(2131363773)
    Button btnAnular;
    @InjectView(2131364215)
    RelativeLayout buscadorDebin;
    @InjectView(2131363772)
    LinearLayout buttomsContainer;
    @InjectView(2131363731)
    Button buttonAceptar;
    @InjectView(2131363730)
    Button buttonDesconocer;
    @InjectView(2131363732)
    Button buttonRechazar;
    @Inject
    AnalyticsManager c;
    @InjectView(2131364237)
    LinearLayout cabeceraDebin;
    OnClickListener d;
    @InjectView(2131363768)
    LinearLayout detalleComprador;
    @InjectView(2131363800)
    LinearLayout detalleVendedor;
    /* access modifiers changed from: private */
    public DebinActivity e;
    private BuscadorDebinPresenter f;
    /* access modifiers changed from: private */
    public DebinesAdapter g;
    /* access modifiers changed from: private */
    public String h = "C";
    /* access modifiers changed from: private */
    public String i = UtilDate.getDateFormat(UtilDate.getDateFormat(Calendar.getInstance().getTime(), Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
    @InjectView(2131363751)
    TextView lblCBU;
    @InjectView(2131363784)
    TextView lblCBUV;
    @InjectView(2131363752)
    TextView lblConcepto;
    @InjectView(2131363785)
    TextView lblConceptoV;
    @InjectView(2131363747)
    TextView lblDataAlias;
    @InjectView(2131363780)
    TextView lblDataAliasV;
    @InjectView(2131363753)
    TextView lblDataDescripcion;
    @InjectView(2131363786)
    TextView lblDataDescripcionV;
    @InjectView(2131363754)
    TextView lblEstado;
    @InjectView(2131363787)
    TextView lblEstadoV;
    @InjectView(2131363755)
    TextView lblFechaSolicitud;
    @InjectView(2131363788)
    TextView lblFechaSolicitudV;
    @InjectView(2131363756)
    TextView lblFechaVencimiento;
    @InjectView(2131363789)
    TextView lblFechaVencimientoV;
    @InjectView(2131363757)
    TextView lblIdDebin;
    @InjectView(2131363790)
    TextView lblIdDebinV;
    @InjectView(2131363763)
    TextView lblImporte;
    @InjectView(2131363796)
    TextView lblImporteV;
    @InjectView(2131363765)
    TextView lblMoneda;
    @InjectView(2131363797)
    TextView lblMonedaV;
    @InjectView(2131363750)
    TextView lblSaldoCDebito;
    @InjectView(2131363783)
    TextView lblSaldoCDebitoV;
    @InjectView(2131363766)
    TextView lblSolicitante;
    @InjectView(2131363767)
    TextView lblSolicitanteDato;
    @InjectView(2131363799)
    TextView lblSolicitanteDatoV;
    @InjectView(2131363722)
    TextView lblSolicitanteDestinatario;
    @InjectView(2131363798)
    TextView lblSolicitanteV;
    @InjectView(2131363748)
    TextView lblbanco;
    @InjectView(2131363781)
    TextView lblbancoV;
    @InjectView(2131363749)
    TextView lblcDebito;
    @InjectView(2131363782)
    TextView lblcDebitoV;
    @InjectView(2131363764)
    TextView leyendaDetalleComprador;
    @InjectView(2131363716)
    RecyclerView lstDebines;
    @InjectView(2131364589)
    ViewFlipper mControlPager;
    @InjectView(2131364586)
    View pantallaDebin;
    @InjectView(2131365580)
    RelativeLayout relativeComprador;
    @InjectView(2131365581)
    RelativeLayout relativeVendedor;
    @InjectView(2131363769)
    LinearLayout rowAlias;
    @InjectView(2131363801)
    LinearLayout rowAliasV;
    @InjectView(2131363771)
    LinearLayout rowBanco;
    @InjectView(2131363802)
    LinearLayout rowBancoV;
    @InjectView(2131363770)
    LinearLayout rowDescripcion;
    @InjectView(2131363804)
    LinearLayout rowDescripcionV;
    @InjectView(2131365627)
    ScrollView scrollC;
    @InjectView(2131365628)
    ScrollView scrollV;
    @InjectView(2131363709)
    RelativeLayout selectorDebinEnviados;
    @InjectView(2131363710)
    RelativeLayout selectorDebinRecibidos;
    @InjectView(2131365731)
    LinearLayout sinDebines;
    @InjectView(2131363745)
    TextView tVCBUComprador;
    @InjectView(2131363778)
    TextView tVCBUVendedor;
    @InjectView(2131365877)
    TextView titleConsulta;
    @InjectView(2131366283)
    TextView txtLeyend;
    @InjectView(2131363711)
    TextView txtSelectorEnviados;
    @InjectView(2131363712)
    TextView txtSelectorRecibidos;
    @InjectView(2131366057)
    TextView txtTodasSolicitudes;
    @InjectView(2131366028)
    TextView txt_error;
    @InjectView(2131363713)
    View viewSelectorEnviados;
    @InjectView(2131363714)
    View viewSelectorRecibidos;

    public void OnCheckedChangeListener(List<ToggleItem> list) {
    }

    public void OnNewItemSelected(ToggleItem toggleItem) {
    }

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public Context getFragmentContext() {
        return getActivity();
    }

    public void onCreate(Bundle bundle) {
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        this.mRootView = getActivity().getLayoutInflater().inflate(R.layout.layout_debin_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, this.mRootView);
        initialize();
        return this.mRootView;
    }

    public void initialize() {
        DebinActivity debinActivity = new DebinActivity(this.mBus, this.mDataManager, getActivity(), this.b, getActivity());
        this.e = debinActivity;
        this.f = new BuscadorDebinPresenter(this.mBus, this.mDataManager, getActivity(), this.b);
        attachView();
        this.c.trackScreen(getString(R.string.analytics_screen_name_Consulta_de_Debin_F_32_00));
        this.ah = this.b.getConsDescripciones().getListTableBeans();
        this.pantallaDebin.setVisibility(8);
        B();
        this.ai = a("01", "ESTDEBIN", this.ah);
        configureActionBar();
        A();
        c(this.h);
        this.blockTabsRecibidas.setVisibility(8);
        this.e.consultarDebines(this.h, "", "", "", "", true);
        try {
            this.tVCBUComprador.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(this.tVCBUComprador.getText().toString()));
            this.tVCBUVendedor.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(this.tVCBUVendedor.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void configureActionBar() {
        if (this.mControlPager != null) {
            switch (this.mControlPager.getDisplayedChild()) {
                case 0:
                    z();
                    return;
                case 1:
                case 2:
                    y();
                    return;
                default:
                    return;
            }
        }
    }

    private void y() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        this.mActionBar = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DetalleDebinFragment.this.onBackPressed();
                }
            });
        }
    }

    private void z() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DetalleDebinFragment.this.switchDrawer();
                }
            });
        }
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        DetalleDebinFragment.this.c.trackEvent(DetalleDebinFragment.this.getString(R.string.analytics_trackevent_category_debin), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_action_click), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_label_Modal_enviar_adherirse_debin));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    DetalleDebinFragment.this.e.showDebinOptions(DetalleDebinFragment.this.a);
                }
            });
        }
    }

    private void A() {
        this.lstDebines.setScrollContainer(true);
        this.lstDebines.setHasFixedSize(true);
        this.lstDebines.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.lstDebines.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
            }

            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom() - (recyclerView.getHeight() + recyclerView.getScrollY()) == 0 && !DetalleDebinFragment.this.af.equalsIgnoreCase("0") && !DetalleDebinFragment.this.aj) {
                    DetalleDebinFragment.this.c(DetalleDebinFragment.this.h);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (!DetalleDebinFragment.this.al) {
                                DetalleDebinFragment.this.e.consultarDebines(DetalleDebinFragment.this.h, "", "", "", DetalleDebinFragment.this.af, false);
                            } else {
                                DetalleDebinFragment.this.e.consultarDebines(DetalleDebinFragment.this.h, UtilDate.getDateFormat(DetalleDebinFragment.this.i, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), UtilDate.getDateFormat(DetalleDebinFragment.this.ad, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), DetalleDebinFragment.this.a(DetalleDebinFragment.this.ai, "ESTDEBIN"), DetalleDebinFragment.this.af, false);
                            }
                        }
                    }, 1000);
                }
            }
        });
        this.ag.clear();
        this.g = new DebinesAdapter(getActivity(), this.ag, this.b);
        this.g.setOnItemClickListener(this);
        this.lstDebines.setAdapter(this.g);
    }

    public void attachView() {
        if (this.mControlPager != null) {
            switch (this.mControlPager.getDisplayedChild()) {
                case 0:
                case 1:
                case 2:
                    if (!this.e.isViewAttached()) {
                        this.e.attachView(this);
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
    }

    public void onBackPressed() {
        if (this.mControlPager == null) {
            return;
        }
        if (this.mControlPager.getDisplayedChild() == 0) {
            super.onBackPressed();
        } else if (this.mControlPager.getDisplayedChild() == 1 || this.mControlPager.getDisplayedChild() == 2) {
            c(0);
        }
    }

    private void c(int i2) {
        a(i2, true);
    }

    private void a(int i2, boolean z) {
        detachView();
        if (this.mControlPager != null) {
            detachView();
            switch (i2) {
                case 0:
                    if (z) {
                        if (this.mControlPager.getDisplayedChild() == 1 || this.mControlPager.getDisplayedChild() == 2) {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                            break;
                        }
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    }
                case 1:
                case 2:
                    if (!z) {
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
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

    private void d(int i2) {
        detachView();
        if (this.mControlPager != null) {
            detachView();
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.noAnimation());
            this.mControlPager.setDisplayedChild(i2);
            attachView();
            configureActionBar();
        }
    }

    public void setDetalleDebinCompradorView(final DetalleDebinBean detalleDebinBean, final String str) {
        this.buttonAceptar.setVisibility(8);
        this.buttonRechazar.setVisibility(8);
        this.buttonDesconocer.setVisibility(8);
        this.buttomsContainer.setVisibility(8);
        if (detalleDebinBean.getMostrarAceptarRechazar().equals(STATUS_FLAGS.POSITIVE)) {
            this.buttonAceptar.setVisibility(0);
            this.buttonRechazar.setVisibility(0);
            this.buttomsContainer.setVisibility(0);
        } else if (detalleDebinBean.getMostrarDesconocimiento().equals(STATUS_FLAGS.POSITIVE)) {
            this.buttonDesconocer.setVisibility(0);
            this.buttomsContainer.setVisibility(0);
        }
        this.scrollC.scrollTo(0, 0);
        if (this.mControlPager.getDisplayedChild() != 1) {
            c(1);
        }
        if (!detalleDebinBean.getCodEstado().equalsIgnoreCase("01")) {
            if (detalleDebinBean.getCodEstado().equalsIgnoreCase("04")) {
                this.c.trackScreen(getString(R.string.analytics_screen_name_Detalle_Debin_recibido_pagado_F_32_02));
            } else {
                this.c.trackScreen(getString(R.string.analytics_screen_name_Detalle_Debin_recibido_vencida_F_32_04));
            }
            this.buttonAceptar.setVisibility(8);
            this.buttonRechazar.setVisibility(8);
        } else {
            this.c.trackScreen(getString(R.string.analytics_screen_name_Detalle_Debin_recibido_pendiente_F_32_03));
        }
        String tipo = detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getTipo();
        String numero = detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getNumero();
        String sucursal = detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getSucursal();
        this.lblSolicitante.setText(detalleDebinBean.getVendedorBean().getTitular());
        this.lblSolicitanteDato.setText(detalleDebinBean.getVendedorBean().getCuit());
        this.lblCBU.setText(detalleDebinBean.getVendedorBean().getCuentaVendedor().getCbu());
        if (!TextUtils.isEmpty(str)) {
            this.leyendaDetalleComprador.setText(Html.fromHtml(str));
            this.leyendaDetalleComprador.setVisibility(0);
        } else {
            this.leyendaDetalleComprador.setVisibility(8);
            this.leyendaDetalleComprador.setText("");
        }
        if (detalleDebinBean.getVendedorBean().getCuentaVendedor().getAlias() != null) {
            this.rowAlias.setVisibility(0);
            this.lblDataAlias.setText(detalleDebinBean.getVendedorBean().getCuentaVendedor().getAlias());
        } else {
            this.rowAlias.setVisibility(8);
        }
        if (detalleDebinBean.getVendedorBean().getCuentaVendedor().getBanco() != null) {
            this.rowBanco.setVisibility(0);
            this.lblbanco.setText(detalleDebinBean.getVendedorBean().getCuentaVendedor().getBanco());
        } else {
            this.rowBanco.setVisibility(8);
        }
        this.lblFechaSolicitud.setText(detalleDebinBean.getFechaCreacion());
        this.lblFechaVencimiento.setText(detalleDebinBean.getFechaVencimiento());
        this.lblIdDebin.setText(this.ae.getIdDebin());
        if (detalleDebinBean.getDescripcion() != null) {
            this.rowDescripcion.setVisibility(0);
            this.lblDataDescripcion.setText(Html.fromHtml(detalleDebinBean.getDescripcion()));
        } else {
            this.rowDescripcion.setVisibility(8);
        }
        this.lblEstado.setText(a(detalleDebinBean.getCodEstado(), "ESTDEBIN", this.ah));
        this.lblConcepto.setText(a(detalleDebinBean.getCodConcepto(), PRE_AUTORIZACIONES.CONDEBIN, this.ah));
        TextView textView = this.lblMoneda;
        StringBuilder sb = new StringBuilder();
        sb.append(a(detalleDebinBean.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.ah));
        sb.append(UtilsCuentas.SEPARAOR2);
        textView.setText(sb.toString());
        this.lblImporte.setText(detalleDebinBean.getImporte());
        this.lblcDebito.setText(UtilAccount.getAbreviatureAndAccountFormat(b(PRE_AUTORIZACIONES.TPOCTACORTA), tipo, sucursal, numero));
        if (detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getSaldo() != null) {
            this.lblSaldoCDebito.setVisibility(0);
            TextView textView2 = this.lblSaldoCDebito;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a(detalleDebinBean.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.ah));
            sb2.append(detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getSaldo());
            textView2.setText(sb2.toString());
        } else {
            this.lblSaldoCDebito.setVisibility(8);
        }
        this.buttonAceptar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    DetalleDebinFragment.this.c.trackEvent(DetalleDebinFragment.this.getString(R.string.analytics_trackevent_category_debin), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_action_click), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_label_Pagar_rechazar_debin_recibido));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DetalleDebinFragment.this.goToAbmDebin(detalleDebinBean, DetalleDebinFragment.this.ae, "P", str);
            }
        });
        this.buttonRechazar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    DetalleDebinFragment.this.c.trackEvent(DetalleDebinFragment.this.getString(R.string.analytics_trackevent_category_debin), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_action_click), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_label_Pagar_rechazar_debin_recibido));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DetalleDebinFragment.this.goToAbmDebin(detalleDebinBean, DetalleDebinFragment.this.ae, "R", "");
            }
        });
        this.buttonDesconocer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    DetalleDebinFragment.this.c.trackEvent(DetalleDebinFragment.this.getString(R.string.analytics_trackevent_category_debin), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_action_click), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_label_Pagar_desconocer_debin_recibido));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DetalleDebinFragment.this.goToAbmDebin(detalleDebinBean, DetalleDebinFragment.this.ae, "D", str);
            }
        });
        try {
            this.leyendaDetalleComprador.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterGeneral(this.leyendaDetalleComprador.getText().toString().toLowerCase()));
            RelativeLayout relativeLayout = this.relativeComprador;
            CAccessibility instance = CAccessibility.getInstance(getFragmentContext());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.lblMoneda.getText().toString());
            sb3.append(UtilsCuentas.SEPARAOR2);
            sb3.append(this.lblImporte.getText().toString());
            relativeLayout.setContentDescription(instance.applyFilterGeneral(sb3.toString()));
            this.lblSolicitanteDato.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterCharacterToCharacter(this.lblSolicitanteDato.getText().toString()));
            this.lblIdDebin.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterCharacterToCharacter(this.lblIdDebin.getText().toString()));
            this.lblcDebito.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterGeneral(this.lblcDebito.getText().toString()));
            this.lblSaldoCDebito.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterGeneral(this.lblSaldoCDebito.getText().toString()));
            this.lblDataDescripcion.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterGeneral(this.lblDataDescripcion.getText().toString()));
            this.lblFechaSolicitud.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterDate(this.lblFechaSolicitud.getText().toString()));
            this.lblFechaVencimiento.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterDate(this.lblFechaVencimiento.getText().toString()));
            this.lblCBU.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterCharacterToCharacter(this.lblCBU.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setDetalleDebinVendedorView(final DetalleDebinBean detalleDebinBean) {
        this.scrollV.scrollTo(0, 0);
        if (this.mControlPager.getDisplayedChild() != 2) {
            c(2);
        }
        if (!detalleDebinBean.getCodEstado().equalsIgnoreCase("01")) {
            if (detalleDebinBean.getCodEstado().equalsIgnoreCase("04")) {
                this.c.trackScreen(getString(R.string.analytics_screen_name_Detalle_Debin_enviado_pagado_F_32_05));
            } else {
                this.c.trackScreen(getString(R.string.analytics_screen_name_Detalle_Debin_enviado_vencida_F_32_07));
            }
            this.btnAnular.setVisibility(8);
        } else {
            this.c.trackScreen(getString(R.string.analytics_screen_name_Detalle_Debin_enviado_pendiente_F_32_06));
            this.btnAnular.setVisibility(0);
        }
        String tipo = detalleDebinBean.getVendedorBean().getCuentaVendedor().getTipo();
        String numero = detalleDebinBean.getVendedorBean().getCuentaVendedor().getNumero();
        String sucursal = detalleDebinBean.getVendedorBean().getCuentaVendedor().getSucursal();
        this.lblSolicitanteV.setText(detalleDebinBean.getCompradorBean().getTitular());
        this.lblSolicitanteDatoV.setText(detalleDebinBean.getCompradorBean().getCuit());
        this.lblCBUV.setText(detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getCbu());
        if (detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getAlias() != null) {
            this.rowAliasV.setVisibility(0);
            this.lblDataAliasV.setText(detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getAlias());
        } else {
            this.rowAliasV.setVisibility(8);
        }
        if (detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getBanco() != null) {
            this.rowBancoV.setVisibility(0);
            this.lblbancoV.setText(detalleDebinBean.getCompradorBean().getCuentaCompradorBean().getBanco());
        } else {
            this.rowBancoV.setVisibility(8);
        }
        this.lblFechaSolicitudV.setText(detalleDebinBean.getFechaCreacion());
        this.lblFechaVencimientoV.setText(detalleDebinBean.getFechaVencimiento());
        this.lblIdDebinV.setText(this.ae.getIdDebin());
        if (detalleDebinBean.getDescripcion() != null) {
            this.rowDescripcionV.setVisibility(0);
            this.lblDataDescripcionV.setText(Html.fromHtml(detalleDebinBean.getDescripcion()));
        } else {
            this.rowDescripcionV.setVisibility(8);
        }
        this.lblEstadoV.setText(a(detalleDebinBean.getCodEstado(), "ESTDEBIN", this.ah));
        this.lblConceptoV.setText(a(detalleDebinBean.getCodConcepto(), PRE_AUTORIZACIONES.CONDEBIN, this.ah));
        TextView textView = this.lblMonedaV;
        StringBuilder sb = new StringBuilder();
        sb.append(a(detalleDebinBean.getMoneda(), PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.ah));
        sb.append(UtilsCuentas.SEPARAOR2);
        textView.setText(sb.toString());
        this.lblImporteV.setText(detalleDebinBean.getImporte());
        this.lblcDebitoV.setText(UtilAccount.getAccountFormat(detalleDebinBean.getVendedorBean().getCuentaVendedor().getSucursal(), detalleDebinBean.getVendedorBean().getCuentaVendedor().getNumeroCuenta()));
        this.lblcDebitoV.setText(UtilAccount.getAbreviatureAndAccountFormat(b(PRE_AUTORIZACIONES.TPOCTACORTA), tipo, sucursal, numero));
        this.lblSaldoCDebitoV.setVisibility(8);
        this.btnAnular.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    DetalleDebinFragment.this.c.trackEvent(DetalleDebinFragment.this.getString(R.string.analytics_trackevent_category_debin), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_action_click), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_label_Anular_debin_enviado));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DetalleDebinFragment.this.goToAbmDebin(detalleDebinBean, DetalleDebinFragment.this.ae, "A", "");
            }
        });
        try {
            RelativeLayout relativeLayout = this.relativeVendedor;
            CAccessibility instance = CAccessibility.getInstance(getFragmentContext());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.lblMonedaV.getText().toString());
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(this.lblImporteV.getText().toString());
            relativeLayout.setContentDescription(instance.applyFilterGeneral(sb2.toString()));
            this.lblSolicitanteDatoV.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterCharacterToCharacter(this.lblSolicitanteDatoV.getText().toString()));
            this.lblIdDebinV.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterCharacterToCharacter(this.lblIdDebinV.getText().toString()));
            this.lblcDebitoV.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterGeneral(this.lblcDebitoV.getText().toString()));
            this.lblSaldoCDebitoV.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterGeneral(this.lblSaldoCDebitoV.getText().toString()));
            this.lblDataDescripcionV.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterGeneral(this.lblDataDescripcionV.getText().toString()));
            this.lblFechaSolicitudV.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterDate(this.lblFechaSolicitud.getText().toString()));
            this.lblFechaVencimientoV.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterDate(this.lblFechaVencimientoV.getText().toString()));
            this.lblCBUV.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterCharacterToCharacter(this.lblCBUV.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void goToAbmDebin(DetalleDebinBean detalleDebinBean, ListDebinesBean listDebinesBean, String str, String str2) {
        Intent intent = new Intent(getActivity(), AbmDebinActivity.class);
        intent.putExtra(DebinConstants.INTENT_EXTRA_DETALLE_DEBIN, detalleDebinBean);
        intent.putExtra(DebinConstants.INTENT_EXTRA_DEBIN, listDebinesBean);
        intent.putExtra(DebinConstants.INTENT_EXTRA_TIPO_ACCION, str);
        intent.putExtra(DebinConstants.INTENT_EXTRA_LEYENDA, str2);
        startActivityForResult(intent, 2);
    }

    public void goToFirmaCredin(String str, String str2) {
        Intent intent = new Intent(getFragmentContext(), FirmaCredinActivity.class);
        intent.putExtra(DebinConstants.INTENT_FIRMA_CREDIN, str);
        intent.putExtra(DebinConstants.INTENT_URL_CREDIN, str2);
        startActivity(intent);
    }

    public void setBackgroundVisibleInit() {
        this.mControlPager.setVisibility(0);
    }

    public void setDebinesView(List<ListDebinesBean> list, String str, String str2) {
        DebinesAdapter debinesAdapter = this.g;
        DebinesAdapter.setErrorMessage(null);
        if (str == null) {
            this.af = "0";
        } else {
            this.af = str;
        }
        if (this.al && str.equalsIgnoreCase("0")) {
            this.al = false;
        }
        this.pantallaDebin.setVisibility(0);
        TextView textView = this.txtTodasSolicitudes;
        StringBuilder sb = new StringBuilder();
        sb.append("DEBIN ");
        sb.append(this.ai);
        textView.setText(sb.toString());
        try {
            this.txtTodasSolicitudes.setContentDescription(CAccessibility.getInstance(getFragmentContext()).applyFilterGeneral(this.txtTodasSolicitudes.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (list.size() > 0) {
            if (this.ag.size() == 0) {
                this.ag.add(new ListDebinesBean());
            }
            this.ag.addAll(this.ag.size() - 1, list);
            if (this.af.equalsIgnoreCase("0")) {
                this.ag.remove(this.ag.size() - 1);
            }
            this.lstDebines.setVisibility(0);
            this.cabeceraDebin.setVisibility(0);
            this.sinDebines.setVisibility(8);
            this.g.notifyDataSetChanged();
            if (!this.af.equalsIgnoreCase("0")) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (((LinearLayoutManager) DetalleDebinFragment.this.lstDebines.getLayoutManager()).findLastCompletelyVisibleItemPosition() < DetalleDebinFragment.this.g.getItemCount() - 2) {
                            DetalleDebinFragment.this.C();
                        } else if (!DetalleDebinFragment.this.al) {
                            DetalleDebinFragment.this.e.consultarDebines(DetalleDebinFragment.this.h, "", "", "", DetalleDebinFragment.this.af, false);
                        } else {
                            DetalleDebinFragment.this.e.consultarDebines(DetalleDebinFragment.this.h, UtilDate.getDateFormat(DetalleDebinFragment.this.i, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), UtilDate.getDateFormat(DetalleDebinFragment.this.ad, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), DetalleDebinFragment.this.a(DetalleDebinFragment.this.ai, "ESTDEBIN"), DetalleDebinFragment.this.af, false);
                        }
                    }
                }, 500);
            } else {
                C();
            }
        } else {
            this.c.trackScreen(getString(R.string.analytics_screen_name_Consulta_Debin_sin_datos_F_32_00_B));
            this.lstDebines.setVisibility(8);
            this.cabeceraDebin.setVisibility(8);
            this.txt_error.setText(Html.fromHtml(str2));
            this.sinDebines.setVisibility(0);
            C();
        }
        this.buscadorDebin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (DetalleDebinFragment.this.ak == R.id.F32_00_SELECTOR_DEBIN_RECIBIDOS) {
                    try {
                        DetalleDebinFragment.this.c.trackEvent(DetalleDebinFragment.this.getString(R.string.analytics_trackevent_category_debin), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_action_buscar_recibidos), DetalleDebinFragment.this.getString(R.string.f227analytics_trackevent_label_Realizar_bsqueda_solicitudes));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (DetalleDebinFragment.this.ak == R.id.F32_00_SELECTOR_DEBIN_ENVIADOS) {
                    try {
                        DetalleDebinFragment.this.c.trackEvent(DetalleDebinFragment.this.getString(R.string.analytics_trackevent_category_debin), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_action_buscar_enviados), DetalleDebinFragment.this.getString(R.string.f227analytics_trackevent_label_Realizar_bsqueda_solicitudes));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (!DetalleDebinFragment.this.aj) {
                    DetalleDebinFragment.this.goToBuscador();
                }
            }
        });
    }

    public void setErrorRecallDebinesView(String str) {
        C();
        this.af = "0";
        this.pantallaDebin.setVisibility(0);
        this.lstDebines.setVisibility(0);
        this.cabeceraDebin.setVisibility(0);
        this.sinDebines.setVisibility(8);
        String valueOf = String.valueOf(Html.fromHtml(str));
        DebinesAdapter debinesAdapter = this.g;
        DebinesAdapter.setErrorMessage(valueOf);
        this.g.notifyDataSetChanged();
    }

    public void goToDebin() {
        if (this.mControlPager.getDisplayedChild() != 0) {
            c(0);
        }
    }

    public void goToBuscador() {
        Intent intent = new Intent(getActivity(), BuscadorDebinActivity.class);
        intent.putExtra("TipoConsulta", this.h);
        intent.putExtra("fechaDesde", this.i);
        intent.putExtra("fechaHasta", this.ad);
        intent.putExtra("estadoSeleccionado", this.ai);
        startActivityForResult(intent, 3);
    }

    private ListTableBean b(String str) {
        ListTableBean listTableBean = new ListTableBean();
        for (ListTableBean listTableBean2 : this.ah) {
            if (listTableBean2.getIdTable().equalsIgnoreCase(str)) {
                listTableBean = listTableBean2;
            }
        }
        return listTableBean;
    }

    private void B() {
        this.ak = R.id.F32_00_SELECTOR_DEBIN_RECIBIDOS;
        Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Regular.otf");
        final Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Bold.otf");
        this.txtSelectorRecibidos.setTextColor(getResources().getColor(R.color.generic_red));
        this.txtSelectorRecibidos.setTypeface(createFromAsset);
        this.viewSelectorRecibidos.setBackgroundColor(getResources().getColor(R.color.generic_red));
        this.txtSelectorEnviados.setTextColor(getResources().getColor(R.color.generic_grey));
        this.txtSelectorEnviados.setTypeface(createFromAsset);
        this.viewSelectorEnviados.setBackgroundColor(getResources().getColor(R.color.generic_grey));
        this.d = new OnClickListener() {
            public void onClick(View view) {
                if (DetalleDebinFragment.this.ak != view.getId() && !DetalleDebinFragment.this.aj) {
                    DetalleDebinFragment.this.sinDebines.setVisibility(8);
                    DetalleDebinFragment.this.lstDebines.removeAllViews();
                    DetalleDebinFragment.this.ag.clear();
                    DetalleDebinFragment.this.ak = view.getId();
                    DetalleDebinFragment.this.af = "";
                    DetalleDebinFragment.this.al = false;
                    try {
                        DetalleDebinFragment.this.c.trackEvent(DetalleDebinFragment.this.getString(R.string.analytics_trackevent_category_debin), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_action_consulta), DetalleDebinFragment.this.getString(R.string.analytics_trackevent_label_Consulta_debin_enviadas_recibidas));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (view.getId() == R.id.F32_00_SELECTOR_DEBIN_RECIBIDOS) {
                        DetalleDebinFragment.this.c.trackScreen(DetalleDebinFragment.this.getString(R.string.analytics_screen_name_Buscar_solicitudes_recibidas_F_32_01));
                        DetalleDebinFragment.this.h = "C";
                        DetalleDebinFragment.this.lblSolicitanteDestinatario.setText(R.string.F32_00_4406);
                        DetalleDebinFragment.this.c(DetalleDebinFragment.this.h);
                        DetalleDebinFragment.this.txtSelectorRecibidos.setTextColor(DetalleDebinFragment.this.getResources().getColor(R.color.generic_red));
                        DetalleDebinFragment.this.txtSelectorRecibidos.setTypeface(createFromAsset);
                        DetalleDebinFragment.this.viewSelectorRecibidos.setBackgroundColor(DetalleDebinFragment.this.getResources().getColor(R.color.generic_red));
                        DetalleDebinFragment.this.txtSelectorEnviados.setTextColor(DetalleDebinFragment.this.getResources().getColor(R.color.generic_grey));
                        DetalleDebinFragment.this.txtSelectorEnviados.setTypeface(createFromAsset);
                        DetalleDebinFragment.this.viewSelectorEnviados.setBackgroundColor(DetalleDebinFragment.this.getResources().getColor(R.color.generic_grey));
                        DetalleDebinFragment.this.e.consultarDebines(DetalleDebinFragment.this.h, "", "", "", DetalleDebinFragment.this.af, true);
                    } else if (view.getId() == R.id.F32_00_SELECTOR_DEBIN_ENVIADOS) {
                        DetalleDebinFragment.this.c.trackScreen(DetalleDebinFragment.this.getString(R.string.analytics_screen_name_Buscar_solicitudes_enviadas_F_32_01_A));
                        DetalleDebinFragment.this.h = "V";
                        DetalleDebinFragment.this.lblSolicitanteDestinatario.setText(R.string.F32_00_4408);
                        DetalleDebinFragment.this.c(DetalleDebinFragment.this.h);
                        DetalleDebinFragment.this.txtSelectorRecibidos.setTextColor(DetalleDebinFragment.this.getResources().getColor(R.color.generic_grey));
                        DetalleDebinFragment.this.txtSelectorRecibidos.setTypeface(createFromAsset);
                        DetalleDebinFragment.this.viewSelectorRecibidos.setBackgroundColor(DetalleDebinFragment.this.getResources().getColor(R.color.generic_grey));
                        DetalleDebinFragment.this.txtSelectorEnviados.setTextColor(DetalleDebinFragment.this.getResources().getColor(R.color.generic_red));
                        DetalleDebinFragment.this.txtSelectorEnviados.setTypeface(createFromAsset);
                        DetalleDebinFragment.this.viewSelectorEnviados.setBackgroundColor(DetalleDebinFragment.this.getResources().getColor(R.color.generic_red));
                        DetalleDebinFragment.this.e.consultarDebines(DetalleDebinFragment.this.h, "", "", "", DetalleDebinFragment.this.af, true);
                    }
                    DetalleDebinFragment.this.ai = DetalleDebinFragment.this.a("01", "ESTDEBIN", DetalleDebinFragment.this.ah);
                    DetalleDebinFragment.this.i = UtilDate.getDateFormat(UtilDate.getDateFormat(Calendar.getInstance().getTime(), Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
                    DetalleDebinFragment.this.ad = UtilDate.getDateFormat(UtilDate.getDateFormat(Calendar.getInstance().getTime(), Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
                }
            }
        };
        this.selectorDebinEnviados.setOnClickListener(this.d);
        this.selectorDebinRecibidos.setOnClickListener(this.d);
    }

    public void setPreAutorizacionesView(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean, String str) {
        dismissProgressIndicator();
        goToPreAutorizacion(getPreAutorizacionesBodyResponseBean, str);
    }

    public void setErrorRecallPreAutorizacionesView(String str) {
        Logger.d("Mensaje", str);
    }

    /* access modifiers changed from: private */
    public void c(String str) {
        this.aj = true;
        this.blockBuscador.setVisibility(0);
        if (str.equalsIgnoreCase("C")) {
            this.txtLeyend.setVisibility(0);
            this.blockTabsEnviadas.setVisibility(0);
        } else if (str.equalsIgnoreCase("V")) {
            this.txtLeyend.setVisibility(8);
            this.blockTabsRecibidas.setVisibility(0);
        }
        this.selectorDebinEnviados.setOnClickListener(null);
        this.selectorDebinRecibidos.setOnClickListener(null);
    }

    /* access modifiers changed from: private */
    public void C() {
        this.aj = false;
        this.blockBuscador.setVisibility(8);
        this.blockTabsEnviadas.setVisibility(8);
        this.blockTabsRecibidas.setVisibility(8);
        this.selectorDebinEnviados.setOnClickListener(this.d);
        this.selectorDebinRecibidos.setOnClickListener(this.d);
    }

    /* access modifiers changed from: private */
    public String a(String str, String str2, List<ListTableBean> list) {
        String str3 = new String();
        for (ListTableBean listTableBean : list) {
            if (listTableBean.getIdTable().equalsIgnoreCase(str2)) {
                for (ListGroupBean listGroupBean : listTableBean.getListGroupBeans()) {
                    if (listGroupBean.getCode().equalsIgnoreCase(str)) {
                        str3 = listGroupBean.getLabel();
                    }
                }
            }
        }
        return str3;
    }

    public void goToNuevoDebin(ListaCuentasVendedorBean listaCuentasVendedorBean, String str, String str2) {
        Intent intent = new Intent(getActivity(), NuevoDebinActivity.class);
        intent.putExtra("mensaje", str);
        intent.putExtra("leyenda", str2);
        startActivityForResult(intent, 1);
    }

    public void goToGestionAdhesionDebin(ListaCuentasVendedorBean listaCuentasVendedorBean) {
        Intent intent = new Intent(getActivity(), GestionAdhesionDebinActivity.class);
        intent.putExtra(DebinConstants.INTENT_EXTRA_CUENTAS_ADHERIDAS, listaCuentasVendedorBean);
        startActivityForResult(intent, 1);
    }

    public void goToPreAutorizacion(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean, String str) {
        Intent intent = new Intent(getActivity(), PreAutorizacionDebinActivity.class);
        intent.putExtra(LIST_PRE_AUTORIZACIONES, getPreAutorizacionesBodyResponseBean);
        intent.putExtra(MENSAJE_ERROR, str);
        startActivityForResult(intent, 5);
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
                Intent intent = new Intent(DetalleDebinFragment.this.getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, DetalleDebinFragment.this.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG_TIT));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, DetalleDebinFragment.this.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG));
                DetalleDebinFragment.this.startActivity(intent);
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), "DialogNewVersion");
    }

    private void a(ListDebinesBean listDebinesBean) {
        this.ae = listDebinesBean;
        this.e.detalleDebinComprador(listDebinesBean);
    }

    private void b(ListDebinesBean listDebinesBean) {
        this.ae = listDebinesBean;
        this.e.detalleDebinVendedor(listDebinesBean);
    }

    public void habilitarBotones() {
        this.selectorDebinEnviados.setOnClickListener(this.d);
        this.selectorDebinRecibidos.setOnClickListener(this.d);
    }

    public void deshabilitarBotones() {
        this.selectorDebinEnviados.setOnClickListener(null);
        this.selectorDebinRecibidos.setOnClickListener(null);
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (!activityResultHandler(i3, intent, FragmentConstants.DEBIN)) {
            switch (i2) {
                case 1:
                case 2:
                    if (i3 == -1) {
                        if (i2 == 2) {
                            try {
                                this.detalleComprador.setVisibility(8);
                                this.detalleVendedor.setVisibility(8);
                                d(0);
                            } catch (Exception e2) {
                                Log.e("DetalleDebinFragment", "onActivityResult: ", e2);
                                return;
                            }
                        }
                        this.al = false;
                        attachView();
                        this.ag.clear();
                        this.g = new DebinesAdapter(getActivity(), this.ag, this.b);
                        this.g.setOnItemClickListener(this);
                        this.lstDebines.setAdapter(this.g);
                        this.af = "";
                        c(this.h);
                        this.e.consultarDebines(this.h, "", "", "", "", true);
                        return;
                    }
                    return;
                case 3:
                    int visibility = this.sinDebines.getVisibility();
                    this.sinDebines.setVisibility(8);
                    if (i3 == -1) {
                        c(this.h);
                        this.lstDebines.removeAllViews();
                        this.ag.clear();
                        this.i = intent.getStringExtra("fechaDesde");
                        this.ad = intent.getStringExtra("fechaHasta");
                        this.af = intent.getStringExtra(ConsultaTenenciaPreAutorizacionesRecibidasFragment.SIGUIENTE_PAGINA);
                        this.ai = intent.getStringExtra("estadoSeleccionado");
                        final String stringExtra = intent.getStringExtra("mensaje");
                        if (!this.af.equalsIgnoreCase("0")) {
                            this.al = true;
                        }
                        final ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("lista");
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                DetalleDebinFragment.this.setDebinesView(parcelableArrayListExtra, DetalleDebinFragment.this.af, stringExtra);
                            }
                        }, 1000);
                        return;
                    } else if (i3 == 0) {
                        this.sinDebines.setVisibility(visibility);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public void onItemClick(View view) {
        setVisibilityDetalles();
        if (this.h.equalsIgnoreCase("C")) {
            a((ListDebinesBean) this.ag.get(this.lstDebines.getChildPosition(view)));
        } else {
            b((ListDebinesBean) this.ag.get(this.lstDebines.getChildPosition(view)));
        }
    }

    /* access modifiers changed from: private */
    public String a(String str, String str2) {
        String str3 = new String();
        for (ListTableBean listTableBean : this.ah) {
            if (listTableBean.getIdTable().equalsIgnoreCase(str2)) {
                for (ListGroupBean listGroupBean : listTableBean.getListGroupBeans()) {
                    if (listGroupBean.getLabel().equalsIgnoreCase(str)) {
                        str3 = listGroupBean.getCode();
                    }
                }
            }
        }
        return str3;
    }

    public void setVisibilityDetalles() {
        this.detalleComprador.setVisibility(0);
        this.detalleVendedor.setVisibility(0);
    }
}
