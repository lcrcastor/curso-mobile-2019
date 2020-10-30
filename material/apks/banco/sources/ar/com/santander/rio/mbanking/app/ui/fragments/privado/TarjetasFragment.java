package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasDetalleConsumoPresenter;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasDetalleConsumoPresenterImp;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasLimitesDisponiblesPresenter;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasLimitesDisponiblesPresenterImp;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasTerminosYCondicionesPresenter;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasTerminosYCondicionesPresenterImp;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasUltimoResumenPresenter;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasUltimoResumenPresenterImp;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasUltimosConsumosPresenter;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasUltimosConsumosPresenterImp;
import ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.UltimoResumenAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.UltimosConsumosAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.LimitesYDisponiblesTCEvent;
import ar.com.santander.rio.mbanking.services.events.UltimoResumenTCEvent;
import ar.com.santander.rio.mbanking.services.events.UltimosConsumosTCEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.AuthTarjeta;
import ar.com.santander.rio.mbanking.services.model.general.Autorizacion;
import ar.com.santander.rio.mbanking.services.model.general.FechaLiquidacion;
import ar.com.santander.rio.mbanking.services.model.general.Limite;
import ar.com.santander.rio.mbanking.services.model.general.Movimiento;
import ar.com.santander.rio.mbanking.services.model.general.MovimientoConsumos;
import ar.com.santander.rio.mbanking.services.model.general.Pago;
import ar.com.santander.rio.mbanking.services.model.general.Saldo;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.model.general.TarjetaConsumos;
import ar.com.santander.rio.mbanking.services.model.general.Tarjetas;
import ar.com.santander.rio.mbanking.services.soap.beans.LimitesYDisponiblesTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimoResumenTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetViajesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.DateView;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.otto.Subscribe;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.inject.Inject;

public class TarjetasFragment extends BaseFragment implements OnClickListener, OnItemClickListener, TarjetasView, IDialogListener {
    public static final String TAG = "ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment";
    public static final String TAG_FRAGMENT_LOAD = "tag_fragment_load";
    public static final int VIEW_DETALLE_CONSUMO_TARJETA = 4;
    public static final int VIEW_TARJETAS = 0;
    public static final int VIEW_TARJETAS_LIMITES_DISPONIBLES = 1;
    public static final int VIEW_ULTIMO_CONSUMO_TARJETA = 3;
    public static final int VIEW_ULTIMO_RESUMEN_TARJETA = 2;
    String a = null;
    private View aA = null;
    /* access modifiers changed from: private */
    public CAccessibility aB;
    private List<ListAdapter> aC = new ArrayList();
    private List<ListAdapter> aD = new ArrayList();
    private List<ListAdapter> aE = new ArrayList();
    private List<ListAdapter> aF = new ArrayList();
    private ArrayList<String> aG = new ArrayList<>();
    /* access modifiers changed from: private */
    public Tarjetas ad = null;
    private ArrayList<String> ae = null;
    private ArrayList<String> af = new ArrayList<>();
    private LimitesYDisponiblesTCBodyResponseBean ag = null;
    /* access modifiers changed from: private */
    public UltimosConsumosTCBodyResponseBean ah = null;
    private UltimoResumenTCBodyResponseBean ai = null;
    /* access modifiers changed from: private */
    public String aj;
    private boolean ak = false;
    /* access modifiers changed from: private */
    public TarjetasLimitesDisponiblesPresenter al;
    /* access modifiers changed from: private */
    public TarjetasUltimosConsumosPresenter am;
    /* access modifiers changed from: private */
    public TarjetasUltimoResumenPresenter an;
    private TarjetasDetalleConsumoPresenter ao;
    private TarjetasTerminosYCondicionesPresenter ap;
    private List<Tarjeta> aq = new ArrayList();

    /* renamed from: ar reason: collision with root package name */
    private HashMap<Integer, String> f241ar = new HashMap<>();
    /* access modifiers changed from: private */
    public List<HashMap<String, String>> as = new ArrayList();
    private List<HashMap<String, String>> at = new ArrayList();
    private Tarjeta au = null;
    @InjectView(2131366069)
    TextView autorizPesos;
    /* access modifiers changed from: private */
    public Tarjeta av = null;
    /* access modifiers changed from: private */
    public Tarjeta aw = null;
    private Tarjeta ax = null;
    private boolean ay = false;
    private boolean az = true;
    TextView b;
    TextView c;
    TextView d;
    @Inject
    IDataManager e;
    @Inject
    SessionManager f;
    @Inject
    AnalyticsManager g;
    /* access modifiers changed from: private */
    public boolean h = false;
    /* access modifiers changed from: private */
    public boolean i = false;
    @InjectView(2131365828)
    ViewFlipper mControlPager;
    @InjectView(2131365215)
    AmountView main_total_autorizaciones;
    @InjectView(2131365216)
    AmountView main_total_autorizaciones_2;
    @InjectView(2131365217)
    AmountView main_total_consumos;
    @InjectView(2131365218)
    AmountView main_total_consumos_2;
    @InjectView(2131365812)
    View pantallaTarjetas;
    @InjectView(2131364606)
    View pantallaTarjetasDetalleConsumo;
    @InjectView(2131365015)
    View pantallaTarjetasLimitesDisponibles;
    @InjectView(2131365815)
    View pantallaTarjetasTerminosCondiciones;
    @InjectView(2131366298)
    View pantallaTarjetasUltimoResumen;
    @InjectView(2131366308)
    View pantallaTarjetasUltimosConsumos;
    @InjectView(2131365333)
    AmountView pendientes_total_consumos;
    @InjectView(2131365334)
    AmountView pendientes_total_consumos_2;
    @InjectView(2131364769)
    View tarjetasView;
    @InjectView(2131365453)
    TextView tvProximoCierre;
    @InjectView(2131365454)
    TextView tvProximoVencimiento;
    @InjectView(2131366025)
    TextView tvTextCardSelected;

    public SessionManager getSessionManager() {
        return null;
    }

    public void goToTarjetasMarcacionPorViaje(GetViajesBodyResponseBean getViajesBodyResponseBean) {
    }

    public void goToTarjetasMarcacionPorViajeRes4(String str, String str2) {
    }

    public void onItemSelected(String str) {
    }

    public void onNegativeButton() {
    }

    public void onPositiveButton() {
    }

    public void setModalInPageAnimation() {
    }

    public void setModalOutPageAnimation() {
    }

    public void setNextPageAnimation() {
    }

    public void setPreviusPageAnimation() {
    }

    public void showMessage(String str, String str2) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.aB = new CAccessibility(getActivity().getApplicationContext());
        this.al = new TarjetasLimitesDisponiblesPresenterImp(this);
        this.am = new TarjetasUltimosConsumosPresenterImp(this);
        this.an = new TarjetasUltimoResumenPresenterImp(this);
        this.ao = new TarjetasDetalleConsumoPresenterImp(this);
        this.ap = new TarjetasTerminosYCondicionesPresenterImp(this);
        this.az = true;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.f != null) {
            this.f.setSelected_tarjeta(null);
        }
    }

    public void onBackPressed() {
        if (this.mControlPager.getDisplayedChild() > 1) {
            switch (this.mControlPager.getDisplayedChild()) {
                case 1:
                    this.ag = null;
                    goToTarjetas();
                    return;
                case 2:
                    this.ai = null;
                    goToTarjetas();
                    return;
                case 3:
                    this.ah = null;
                    ((LinearLayout) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_list)).removeAllViews();
                    ((LinearLayout) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_pendientes_list)).removeAllViews();
                    goToTarjetas();
                    return;
                case 4:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                    if (this.ay) {
                        this.mControlPager.setDisplayedChild(2);
                        return;
                    } else {
                        this.mControlPager.setDisplayedChild(3);
                        return;
                    }
                case 5:
                    this.ak = true;
                    gotoPage(2);
                    ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
                    ((SantanderRioMainActivity) getActivity()).lockMenu(true);
                    F();
                    return;
                default:
                    return;
            }
        } else if (this.mControlPager.getDisplayedChild() == 1) {
            goToTarjetas();
        } else {
            this.f.setLimitesYDisponiblesTC(null);
            this.f.setUltimoResumenTC(null);
            this.f.setSelected_tarjeta(null);
            super.onBackPressed();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.tarjetas_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        setTAG(FragmentConstants.TARJETAS);
        if (this.pantallaTarjetasLimitesDisponibles != null) {
            this.b = (TextView) this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.text_tarjeta);
        }
        if (this.pantallaTarjetasUltimosConsumos != null) {
            this.d = (TextView) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.text_tarjeta);
        }
        if (this.pantallaTarjetasUltimoResumen != null) {
            this.c = (TextView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.text_tarjeta);
        }
        this.g.trackScreen(getResources().getString(R.string.analytics_screen_name_cards_home));
        this.pantallaTarjetas.findViewById(R.id.principal_root_layout).setVisibility(8);
        this.pantallaTarjetas.findViewById(R.id.selector_root).setVisibility(8);
        ((TextView) this.pantallaTarjetas.findViewById(R.id.text4)).setText(((TextView) this.pantallaTarjetas.findViewById(R.id.text4)).getText().toString().replace(TokenParser.SP, 10));
        ((TextView) this.pantallaTarjetas.findViewById(R.id.text5)).setText(((TextView) this.pantallaTarjetas.findViewById(R.id.text5)).getText().toString().replace(TokenParser.SP, 10));
        this.pantallaTarjetas.findViewById(R.id.nav_pagar).setOnClickListener(this);
        this.ad = this.f.getLoginUnico().getProductos().getTarjetas();
        y();
        H();
        if (this.ae.size() < 1) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.CUENTAS);
            }
        } else if (this.ae.size() < 2) {
            this.pantallaTarjetas.findViewById(R.id.nav_selector_tarjeta).setVisibility(4);
        } else {
            this.pantallaTarjetas.findViewById(R.id.nav_selector_tarjeta).setVisibility(0);
            this.pantallaTarjetas.findViewById(R.id.nav_selector_tarjeta).setOnClickListener(this);
        }
        if (this.ag == null || this.ag != this.f.getLimitesYDisponiblesTC()) {
            showProgress();
            this.e.limitesYDisponiblesTC(d(this.f.getSelected_tarjeta()), TypeOption.INITIAL_VIEW, this.f.getSelected_tarjeta());
        } else {
            e(false);
        }
        return inflate;
    }

    @Subscribe
    public void limitesYDisponiblesTC(LimitesYDisponiblesTCEvent limitesYDisponiblesTCEvent) {
        dismissProgress();
        this.au = limitesYDisponiblesTCEvent.getTarjetaSeleccionada();
        c(limitesYDisponiblesTCEvent.getTarjetaSeleccionada());
        this.b.setText(Utils.mascaraTarjeta(this.au));
        try {
            this.b.setContentDescription(this.aB.applyFilterCreditCard(this.b.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.tarjetasView.setVisibility(0);
        this.aj = Utils.mascaraTarjeta(this.au);
        this.g.trackEvent(getResources().getString(R.string.analytics_category_cards), getResources().getString(R.string.analytics_action_change_card), limitesYDisponiblesTCEvent.getTarjetaSeleccionada().getTipo().equals(TarjetasConstants.CODIGO_TARJETA_VISA) ? "visa" : TarjetasConstants.MARCA_AMEX);
        a(limitesYDisponiblesTCEvent.getTarjetaSeleccionada());
        if (limitesYDisponiblesTCEvent.getResult() == TypeResult.OK) {
            if (!this.aG.contains("Límites y Disponibles")) {
                this.aG.add("Límites y Disponibles");
            }
            this.ag = ((LimitesYDisponiblesTCResponseBean) limitesYDisponiblesTCEvent.getBeanResponse()).getBody();
            this.f.setLimitesYDisponiblesTC(this.ag);
            b(this.f.getSelected_tarjeta());
            z();
        } else if (limitesYDisponiblesTCEvent.getResult() == TypeResult.BEAN_ERROR_RES_4 || limitesYDisponiblesTCEvent.getResult() == TypeResult.SERVER_ERROR) {
            R();
            this.h = false;
            e(false);
            ((TextView) this.pantallaTarjetas.findViewById(R.id.saldoNoDisponible)).setText(limitesYDisponiblesTCEvent.getResult() == TypeResult.BEAN_ERROR_RES_4 ? Html.fromHtml(limitesYDisponiblesTCEvent.getMessageToShow()) : getString(R.string.IDXX_CCARDS_ERROR_CONEXION_LYD));
            ((TextView) this.pantallaTarjetas.findViewById(R.id.saldoNoDisponible)).setVisibility(0);
        } else {
            R();
            if (getErrorListener() != null) {
                this.ag = null;
                getErrorListener().onWebServiceErrorEvent(limitesYDisponiblesTCEvent, getTAG());
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(Tarjeta tarjeta) {
        if (tarjeta.getClase().toUpperCase().equals("T")) {
            this.i = true;
        } else {
            this.i = false;
        }
    }

    private void b(Tarjeta tarjeta) {
        g(tarjeta);
        this.h = true;
        this.pantallaTarjetas.findViewById(R.id.principal_root_layout).setVisibility(0);
        this.pantallaTarjetas.findViewById(R.id.selector_root).setVisibility(0);
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                e(true);
                return;
            case 1:
                G();
                return;
            default:
                return;
        }
    }

    @Subscribe
    public void ultimosConsumosTC(UltimosConsumosTCEvent ultimosConsumosTCEvent) {
        dismissProgress();
        final UltimosConsumosTCEvent ultimosConsumosTCEvent2 = ultimosConsumosTCEvent;
        AnonymousClass1 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.ENVIO_EFECTIVO, this, (BaseActivity) getActivity()) {
            public void onOk() {
                ((LinearLayout) TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_list)).removeAllViews();
                ((LinearLayout) TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_pendientes_list)).removeAllViews();
                Tarjeta tarjeta = ultimosConsumosTCEvent2.tarjetaSeleccionada;
                TarjetasFragment.this.a(ultimosConsumosTCEvent2.getTarjetaSeleccionada());
                TarjetasFragment.this.aj = Utils.mascaraTarjeta(tarjeta);
                TarjetasFragment.this.c(ultimosConsumosTCEvent2.getTarjetaSeleccionada());
                switch (TarjetasFragment.this.mControlPager.getDisplayedChild()) {
                    case 2:
                        TarjetasFragment.this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list).setVisibility(4);
                        TarjetasFragment.this.as.clear();
                        TarjetasFragment.this.aw = tarjeta;
                        TarjetasFragment.this.c.setText(Utils.mascaraTarjeta(tarjeta));
                        try {
                            TarjetasFragment.this.c.setContentDescription(TarjetasFragment.this.aB.applyFilterCreditCard(TarjetasFragment.this.c.getText().toString()));
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    case 3:
                        TarjetasFragment.this.av = ultimosConsumosTCEvent2.getTarjetaSeleccionada();
                        TarjetasFragment.this.d.setText(Utils.mascaraTarjeta(ultimosConsumosTCEvent2.getTarjetaSeleccionada()));
                        try {
                            TarjetasFragment.this.d.setContentDescription(TarjetasFragment.this.aB.applyFilterCreditCard(TarjetasFragment.this.d.getText().toString()));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        ((LinearLayout) TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_pendientes_list)).removeAllViews();
                        ((LinearLayout) TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_list)).removeAllViews();
                        TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_title).setVisibility(8);
                        TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_pendientes_head).setVisibility(8);
                        TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.autorizaciones_warning).setVisibility(8);
                        TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.autorizaciones_table_head).setVisibility(8);
                        TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_head).setVisibility(8);
                        TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_head).setVisibility(8);
                        TarjetasFragment.this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_main_footer).setVisibility(8);
                        break;
                }
                TarjetasFragment.this.a(ultimosConsumosTCEvent2.getTarjetaSeleccionada());
                TarjetasFragment.this.ah = ((UltimosConsumosTCResponseBean) ultimosConsumosTCEvent2.getBeanResponse()).getBody();
                TarjetasFragment.this.N();
                ((BaseActivity) TarjetasFragment.this.getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
                ((SantanderRioMainActivity) TarjetasFragment.this.getActivity()).lockMenu(true);
                TarjetasFragment.this.F();
                TarjetasFragment.this.gotoPage(3);
                TarjetasFragment.this.A();
            }

            /* access modifiers changed from: protected */
            public void onRes1Error(WebServiceEvent webServiceEvent) {
                final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, PagoTarjetasConstants.ISBAN_DIALOG_WARNING_TITLE, ultimosConsumosTCEvent2.getMessageToShow(), null, null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null);
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
                newInstance.show(TarjetasFragment.this.getFragmentManager(), "optionsMenu");
            }
        };
        r0.handleWSResponse(ultimosConsumosTCEvent);
    }

    @Subscribe
    public void ultimoResumenTC(UltimoResumenTCEvent ultimoResumenTCEvent) {
        dismissProgress();
        try {
            if (ultimoResumenTCEvent.getResult() == TypeResult.OK) {
                this.ai = ((UltimoResumenTCResponseBean) ultimoResumenTCEvent.getBeanResponse()).getBody();
                Q();
                ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
                ((SantanderRioMainActivity) getActivity()).lockMenu(true);
                F();
                gotoPage(2);
                B();
            } else if (ultimoResumenTCEvent.getResult() == TypeResult.BEAN_ERROR_RES_1) {
                final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, ultimoResumenTCEvent.getMessageToShow(), null, null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null);
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
                newInstance.show(getFragmentManager(), "optionsMenu");
            } else if (getErrorListener() != null) {
                this.ai = null;
                getErrorListener().onWebServiceErrorEvent(ultimoResumenTCEvent, getTAG());
            }
        } catch (Exception unused) {
            if (getErrorListener() != null) {
                this.ai = null;
                getErrorListener().onWebServiceErrorEvent(ultimoResumenTCEvent, getTAG());
            }
        }
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.ok) {
            this.ak = true;
            gotoPage(2);
            ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
            ((SantanderRioMainActivity) getActivity()).lockMenu(true);
            F();
        } else if (id2 != R.id.tarjetas_row_item) {
            switch (id2) {
                case R.id.nav_pagar /*2131365252*/:
                    ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.PAGO_TARJETAS);
                    return;
                case R.id.nav_selector_tarjeta /*2131365253*/:
                    IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, getString(R.string.ID538_CCARDS_LIMITS_BTN_SELECT), null, this.ae, getString(R.string.ID537_CCARDS_LIMITS_BTN_CANCEL), null, null, this.aj, this.af);
                    newInstance.setDialogListener(new IDialogListener() {
                        public void onNegativeButton() {
                        }

                        public void onPositiveButton() {
                        }

                        public void onSimpleActionButton() {
                        }

                        public void onItemSelected(String str) {
                            Tarjeta selected_tarjeta = TarjetasFragment.this.f.getSelected_tarjeta();
                            Iterator it = TarjetasFragment.this.ad.getTarjetas().iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                Tarjeta tarjeta = (Tarjeta) it.next();
                                if (Utils.mascaraTarjeta(tarjeta).equalsIgnoreCase(str)) {
                                    selected_tarjeta = tarjeta;
                                    break;
                                }
                            }
                            switch (TarjetasFragment.this.mControlPager.getDisplayedChild()) {
                                case 0:
                                case 1:
                                    TarjetasFragment.this.showProgress();
                                    TarjetasFragment.this.al.sendRequestLimitesYDisponiblesTC(TarjetasFragment.this.d(selected_tarjeta), selected_tarjeta);
                                    break;
                                case 2:
                                    TarjetasFragment.this.showProgress();
                                    TarjetasFragment.this.an.sendRequestUltimoResumenTC(TarjetasFragment.this.e(selected_tarjeta));
                                    break;
                                case 3:
                                    TarjetasFragment.this.showProgress();
                                    TarjetasFragment.this.am.sendRequestUltimosConsumosTC(TarjetasFragment.this.f(selected_tarjeta), selected_tarjeta);
                                    break;
                            }
                            if (selected_tarjeta.getClase().toUpperCase().equals("T")) {
                                TarjetasFragment.this.i = true;
                            } else {
                                TarjetasFragment.this.i = false;
                            }
                        }
                    });
                    newInstance.show(getActivity().getSupportFragmentManager(), "Dialog");
                    return;
                case R.id.nav_terminos /*2131365254*/:
                    goToTarjetasTerminosCondiciones();
                    return;
                default:
                    return;
            }
        } else {
            this.ay = false;
            if (((ViewGroup) view.getParent()).getId() == R.id.tarjetas_ultimos_consumos_pendientes_list) {
                Log.d(TAG, "Tarjetas - Listado Últimos Consumos Pendientes");
                if (view.getTag() instanceof Integer) {
                    HashMap hashMap = (HashMap) this.at.get(((Integer) view.getTag()).intValue());
                    if (((String) hashMap.get(TarjetasConstants.ESTADO)).equals("true")) {
                        goToTarjetasDetalleConsumo((String) hashMap.get(TarjetasConstants.TARJETA), hashMap);
                    }
                }
            } else if (((ViewGroup) view.getParent()).getId() == R.id.tarjetas_ultimos_consumos_consumos_list) {
                Log.d(TAG, "Tarjetas - Listado Últimos Consumos");
                if (view.getTag() instanceof Integer) {
                    int intValue = ((Integer) view.getTag()).intValue();
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Posición listado: ");
                    sb.append(intValue);
                    Log.d(str, sb.toString());
                    String str2 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Detalles Consumo: ");
                    sb2.append(this.as.get(1));
                    Log.d(str2, sb2.toString());
                    HashMap hashMap2 = (HashMap) this.as.get(intValue);
                    if (((String) hashMap2.get(TarjetasConstants.ESTADO)).equals("true")) {
                        goToTarjetasDetalleConsumo((String) hashMap2.get(TarjetasConstants.TARJETA), hashMap2);
                    }
                }
            }
        }
    }

    public void onSimpleActionButton() {
        b(this.f.getSelected_tarjeta());
    }

    private void y() {
        if (this.f.getSelected_tarjeta() == null) {
            this.f.setSelected_tarjeta(S());
        }
        this.tvTextCardSelected.setText(Utils.mascaraTarjeta(this.f.getSelected_tarjeta()));
        a(this.tvTextCardSelected);
        try {
            this.tvTextCardSelected.setContentDescription(this.aB.applyFilterCreditCard(this.tvTextCardSelected.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void z() {
        if (this.f.getSelected_tarjeta() == null) {
            this.f.setSelected_tarjeta(S());
        }
        if (this.b != null) {
            this.b.setText(Utils.mascaraTarjeta(this.f.getSelected_tarjeta()));
            a(this.b);
            try {
                this.b.setContentDescription(this.aB.applyFilterCreditCard(this.b.getText().toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void A() {
        if (this.f.getSelected_tarjeta() == null) {
            this.f.setSelected_tarjeta(S());
        }
        if (this.d != null) {
            this.d.setText(Utils.mascaraTarjeta(this.f.getSelected_tarjeta()));
            a(this.d);
            try {
                this.d.setContentDescription(this.aB.applyFilterCreditCard(this.d.getText().toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void B() {
        if (this.f.getSelected_tarjeta() == null) {
            this.f.setSelected_tarjeta(S());
        }
        if (this.c != null) {
            this.c.setText(Utils.mascaraTarjeta(this.f.getSelected_tarjeta()));
            a(this.c);
            try {
                this.c.setContentDescription(this.aB.applyFilterCreditCard(this.c.getText().toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void c(Tarjeta tarjeta) {
        this.f.setSelected_tarjeta(tarjeta);
    }

    /* access modifiers changed from: private */
    public LimitesYDisponiblesTCBodyRequestBean d(Tarjeta tarjeta) {
        String str;
        String str2 = tarjeta.getTipo().equals(TarjetasConstants.CODIGO_TARJETA_VISA) ? TarjetasConstants.CODIGO_TARJETA_VISA_REQUEST : TarjetasConstants.CODIGO_TARJETA_AMEX_REQUEST;
        String numero = tarjeta.getNumero();
        if (tarjeta.getTipo().equals(TarjetasConstants.CODIGO_TARJETA_VISA)) {
            str = numero.substring(numero.length() - 10);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(numero.substring(numero.length() - 9));
            sb.append(tarjeta.getClaveBancariaUnificada().charAt(18));
            str = sb.toString();
        }
        return new LimitesYDisponiblesTCBodyRequestBean(str, tarjeta.getNroTarjetaCredito(), str2);
    }

    /* access modifiers changed from: private */
    public UltimoResumenTCBodyRequestBean e(Tarjeta tarjeta) {
        String str;
        String str2 = tarjeta.getTipo().equals(TarjetasConstants.CODIGO_TARJETA_VISA) ? TarjetasConstants.CODIGO_TARJETA_VISA_REQUEST : TarjetasConstants.CODIGO_TARJETA_AMEX_REQUEST;
        String numero = tarjeta.getNumero();
        if (tarjeta.getTipo().equals(TarjetasConstants.CODIGO_TARJETA_VISA)) {
            str = numero.substring(numero.length() - 10);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(numero.substring(numero.length() - 9));
            sb.append(tarjeta.getClaveBancariaUnificada().charAt(18));
            str = sb.toString();
        }
        return new UltimoResumenTCBodyRequestBean(str, tarjeta.getNroTarjetaCredito(), str2);
    }

    /* access modifiers changed from: private */
    public UltimosConsumosTCBodyRequestBean f(Tarjeta tarjeta) {
        String str;
        String str2 = tarjeta.getTipo().equals(TarjetasConstants.CODIGO_TARJETA_VISA) ? TarjetasConstants.CODIGO_TARJETA_VISA_REQUEST : TarjetasConstants.CODIGO_TARJETA_AMEX_REQUEST;
        String numero = tarjeta.getNumero();
        if (tarjeta.getTipo().equals(TarjetasConstants.CODIGO_TARJETA_VISA)) {
            str = numero.substring(numero.length() - 10);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(numero.substring(numero.length() - 9));
            sb.append(tarjeta.getClaveBancariaUnificada().charAt(18));
            str = sb.toString();
        }
        return new UltimosConsumosTCBodyRequestBean(str, tarjeta.getNroTarjetaCredito(), str2);
    }

    private void C() {
        try {
            this.tvProximoCierre.setText(UtilDate.getDateFormat(this.ag.getTarjetas().getDocument().getFechas().getCierre(), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_CREDIT_CARDS_MAIN));
            this.tvProximoCierre.setContentDescription(this.aB.applyFilterDate(this.ag.getTarjetas().getDocument().getFechas().getCierre()));
            this.tvProximoVencimiento.setText(UtilDate.getDateFormat(this.ag.getTarjetas().getDocument().getFechas().getVencimiento(), Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_CREDIT_CARDS_MAIN));
            this.tvProximoVencimiento.setContentDescription(this.aB.applyFilterDate(this.ag.getTarjetas().getDocument().getFechas().getVencimiento()));
        } catch (Exception unused) {
        }
    }

    private void D() {
        if (this.ag.getTarjetas().getDocument().getAutorizaciones().getTotalPesos().equalsIgnoreCase("")) {
            this.ag.getTarjetas().getDocument().getAutorizaciones().setTotalPesos("0");
        }
        CAmount cAmount = new CAmount(this.ag.getTarjetas().getDocument().getAutorizaciones().getTotalPesos());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        ((AmountView) this.pantallaTarjetas.findViewById(R.id.total_autorizaciones_pesos)).setCElementAcc(new CAmountAcc());
        ((AmountView) this.pantallaTarjetas.findViewById(R.id.total_autorizaciones_pesos)).setAmount(cAmount.getAmount());
        if (this.ag.getTarjetas().getDocument().getAutorizaciones().getTotalDolares().equalsIgnoreCase("")) {
            this.ag.getTarjetas().getDocument().getAutorizaciones().setTotalDolares("0");
        }
        if (this.ag.getTarjetas().getDocument().getAutorizaciones().getTotalDolares() == null || this.ag.getTarjetas().getDocument().getAutorizaciones().getTotalDolares().isEmpty()) {
            this.pantallaTarjetas.findViewById(R.id.total_autorizaciones_dolares).setVisibility(8);
            return;
        }
        CAmount cAmount2 = new CAmount(this.ag.getTarjetas().getDocument().getAutorizaciones().getTotalDolares());
        cAmount2.setSymbolCurrencyDollarOrPeso(true);
        ((AmountView) this.pantallaTarjetas.findViewById(R.id.total_autorizaciones_dolares)).setCElementAcc(new CAmountAcc());
        ((AmountView) this.pantallaTarjetas.findViewById(R.id.total_autorizaciones_dolares)).setAmount(cAmount2.getAmount());
        if (!this.ag.getTarjetas().getDocument().getAutorizaciones().getTotalDolares().contains("-")) {
            String amount = cAmount2.getAmount();
            StringBuilder sb = new StringBuilder();
            sb.append(amount.substring(0, 3));
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(amount.substring(3));
            ((AmountView) this.pantallaTarjetas.findViewById(R.id.total_autorizaciones_dolares)).setAmount(sb.toString());
        }
    }

    private void a(boolean z) {
        if (z) {
            for (Limite limite : this.ag.getTarjetas().getDocument().getSaldoenCuenta().getLimites().getLimites()) {
                if (limite.getDescripcion().equals(TarjetasConstants.COMPRADISP)) {
                    boolean equals = limite.getMoneda().equals("U$S");
                    CAmount cAmount = new CAmount(limite.getMonto());
                    cAmount.setSymbolCurrencyDollarOrPeso(equals);
                    ((AmountView) this.pantallaTarjetas.findViewById(R.id.compraDisp)).setColorAmount(cAmount.isAmountPossite());
                    ((AmountView) this.pantallaTarjetas.findViewById(R.id.compraDisp)).setCElementAcc(new CAmountAcc());
                    ((AmountView) this.pantallaTarjetas.findViewById(R.id.compraDisp)).setAmount(cAmount.getAmount());
                    return;
                }
            }
        }
    }

    public void goToTarjetas() {
        H();
        if (this.mControlPager.getDisplayedChild() != 0) {
            if (this.ae.size() > 1) {
                this.pantallaTarjetas.findViewById(R.id.nav_selector_tarjeta).setVisibility(0);
            } else {
                this.pantallaTarjetas.findViewById(R.id.nav_selector_tarjeta).setVisibility(4);
            }
            if (this.f.getSelected_tarjeta() != this.ax) {
                this.ax = this.f.getSelected_tarjeta();
                y();
                if (this.az) {
                    showProgress();
                    this.e.limitesYDisponiblesTC(d(this.f.getSelected_tarjeta()), TypeOption.INITIAL_VIEW, this.f.getSelected_tarjeta());
                }
            }
            gotoPage(0);
            ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_MENU);
            ((SantanderRioMainActivity) getActivity()).lockMenu(false);
            enableMenuButton();
            enableOptionsButton();
        }
    }

    public void enableMenuButton() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            ((ImageView) customView.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TarjetasFragment.this.switchDrawer();
                }
            });
        }
    }

    public void enableOptionsButton() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            ImageView imageView = (ImageView) customView.findViewById(R.id.menu);
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TarjetasFragment.this.E();
                }
            });
            imageView.setContentDescription(getString(R.string.IDXX_CONTENT_DESCRIPTION_BTN_OPTIONS));
        }
    }

    /* access modifiers changed from: private */
    public void E() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, this.aG, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            /* JADX WARNING: Removed duplicated region for block: B:22:0x0046  */
            /* JADX WARNING: Removed duplicated region for block: B:23:0x0055  */
            /* JADX WARNING: Removed duplicated region for block: B:24:0x005b  */
            /* JADX WARNING: Removed duplicated region for block: B:25:0x0061  */
            /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onItemSelected(java.lang.String r3) {
                /*
                    r2 = this;
                    int r0 = r3.hashCode()
                    r1 = -1523350694(0xffffffffa533835a, float:-1.5570279E-16)
                    if (r0 == r1) goto L_0x0037
                    r1 = -1272021666(0xffffffffb42e7d5e, float:-1.6250621E-7)
                    if (r0 == r1) goto L_0x002d
                    r1 = 677400515(0x28604fc3, float:1.24517934E-14)
                    if (r0 == r1) goto L_0x0023
                    r1 = 1915448650(0x722b6d4a, float:3.3954598E30)
                    if (r0 == r1) goto L_0x0019
                    goto L_0x0041
                L_0x0019:
                    java.lang.String r0 = "Último Resumen"
                    boolean r3 = r3.equals(r0)
                    if (r3 == 0) goto L_0x0041
                    r3 = 2
                    goto L_0x0042
                L_0x0023:
                    java.lang.String r0 = "Últimos Consumos"
                    boolean r3 = r3.equals(r0)
                    if (r3 == 0) goto L_0x0041
                    r3 = 1
                    goto L_0x0042
                L_0x002d:
                    java.lang.String r0 = "Límites y Disponibles"
                    boolean r3 = r3.equals(r0)
                    if (r3 == 0) goto L_0x0041
                    r3 = 0
                    goto L_0x0042
                L_0x0037:
                    java.lang.String r0 = "Habilitar Tarjetas por Viaje"
                    boolean r3 = r3.equals(r0)
                    if (r3 == 0) goto L_0x0041
                    r3 = 3
                    goto L_0x0042
                L_0x0041:
                    r3 = -1
                L_0x0042:
                    switch(r3) {
                        case 0: goto L_0x0061;
                        case 1: goto L_0x005b;
                        case 2: goto L_0x0055;
                        case 3: goto L_0x0046;
                        default: goto L_0x0045;
                    }
                L_0x0045:
                    goto L_0x006e
                L_0x0046:
                    ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment r3 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment.this
                    r3.showProgress()
                    ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment r3 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment.this
                    ar.com.santander.rio.mbanking.app.module.creditCards.TarjetasLimitesDisponiblesPresenter r3 = r3.al
                    r3.sendRequestGetViajesMarcacion()
                    goto L_0x006e
                L_0x0055:
                    ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment r3 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment.this
                    r3.goToTarjetasUltimoResumen()
                    goto L_0x006e
                L_0x005b:
                    ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment r3 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment.this
                    r3.goToTarjetasUltimosConsumos()
                    goto L_0x006e
                L_0x0061:
                    ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment r3 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment.this
                    boolean r3 = r3.h
                    if (r3 == 0) goto L_0x006e
                    ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment r3 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment.this
                    r3.goToTarjetasLimitesDisponibles()
                L_0x006e:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment.AnonymousClass6.onItemSelected(java.lang.String):void");
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getFragmentManager(), "optionsMenu");
    }

    public void goToTarjetasLimitesDisponibles() {
        gotoPage(1);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        F();
        this.al.onCreatePage();
    }

    public void setTarjetasLimitesDisponiblesView() {
        this.g.trackScreen(getResources().getString(R.string.analytics_screen_name_cards_limites_disponibles));
        this.ad = this.f.getLoginUnico().getProductos().getTarjetas();
        I();
        this.ag = this.f.getLimitesYDisponiblesTC();
        if (this.aq.size() < 2) {
            this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.nav_selector_tarjeta).setVisibility(4);
        } else {
            this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.nav_selector_tarjeta).setVisibility(0);
            this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.nav_selector_tarjeta).setOnClickListener(this);
        }
        G();
    }

    public void goToTarjetasUltimosConsumos() {
        this.am.onCreatePage();
    }

    public void setTarjetasUltimosConsumosView() {
        this.g.trackScreen(getResources().getString(R.string.analytics_screen_name_cards_ultimos_consumos));
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_pendientes_head).setVisibility(8);
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.autorizaciones_warning).setVisibility(8);
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.autorizaciones_table_head).setVisibility(8);
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_head).setVisibility(8);
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_main_footer).setVisibility(8);
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_title).setVisibility(8);
        TextView textView = (TextView) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.text4);
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getString(R.string.ID518_CCARDS_LASTBRIEF_LBL_TOTAL));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getResources().getString(R.string.ID500_CCARDS_MAIN_BTN_LASTPAYMENTS));
        textView.setText(sb.toString());
        TextView textView2 = (TextView) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.text7);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getString(R.string.ID518_CCARDS_LASTBRIEF_LBL_TOTAL));
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(getResources().getString(R.string.ID500_CCARDS_MAIN_BTN_PENDAUTH));
        textView2.setText(sb2.toString());
        this.ad = this.f.getLoginUnico().getProductos().getTarjetas();
        K();
        if (this.f.getLoginUnico().getProductos().getTarjetas().getTarjetas().size() > 0) {
            showProgress();
            this.am.sendRequestUltimosConsumosTC(f(this.f.getSelected_tarjeta()), this.f.getSelected_tarjeta());
        } else {
            FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, "No hay tarjetas que mostrar", null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                    TarjetasFragment.this.onBackPressed();
                }
            });
            newInstance.show(supportFragmentManager, "Dialog");
        }
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.nav_selector_tarjeta).setOnClickListener(this);
    }

    public void goToTarjetasUltimoResumen() {
        this.an.onCreatePage();
    }

    public void setTarjetasUltimoResumenView() {
        this.g.trackScreen(getResources().getString(R.string.analytics_screen_name_cards_ultimo_resumen));
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.tarjetas_ultimo_resumen_list_header, (ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list), false);
        if (((ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list)).getHeaderViewsCount() > 0) {
            ((ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list)).removeHeaderView(inflate);
        } else {
            ((ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list)).addHeaderView(inflate, null, false);
        }
        if (this.aA == null) {
            this.aA = getActivity().getLayoutInflater().inflate(R.layout.tarjetas_ultimo_resumen_list_footer, (ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list), false);
        }
        J();
        if (this.f.getLoginUnico().getProductos().getTarjetas().getTarjetas().size() > 0) {
            showProgress();
            this.an.sendRequestUltimoResumenTC(e(this.f.getSelected_tarjeta()));
        } else {
            FragmentManager fragmentManager = getFragmentManager();
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, "No hay tarjetas que mostrar", null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                    TarjetasFragment.this.onBackPressed();
                }
            });
            newInstance.show(fragmentManager, "Dialog");
        }
        this.aA.findViewById(R.id.nav_terminos).setOnClickListener(this);
        this.pantallaTarjetasUltimoResumen.findViewById(R.id.nav_selector_tarjeta).setOnClickListener(this);
    }

    public void goToTarjetasTerminosCondiciones() {
        this.g.trackScreen(getResources().getString(R.string.analytics_screen_name_cards_terminos_condiciones));
        String legales = this.f.getUltimoResumenTC().getTarjeta().getLiquidacion().getDetalleLiquidacion().getLegales();
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getString(R.string.ID540_CCARDS_LIMITS_LBL_TERMSANDCOND));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, legales);
        startActivityForResult(intent, 1);
    }

    public void setTarjetasTerminosCondicionesView() {
        this.g.trackScreen(getResources().getString(R.string.analytics_screen_name_cards_terminos_condiciones));
        ((TextView) this.pantallaTarjetasTerminosCondiciones.findViewById(R.id.text_legales)).setText(Html.fromHtml(this.f.getUltimoResumenTC().getTarjeta().getLiquidacion().getDetalleLiquidacion().getLegales().replace(TarjetasConstants.OPEN_HTML_TAG, "<").replace(TarjetasConstants.CLOSE_HTML_TAG, ">")));
    }

    public void goToTarjetasDetalleConsumo(String str, HashMap<String, String> hashMap) {
        gotoPage(4);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        F();
        this.ao.onCreatePage(str, hashMap);
    }

    public void setTarjetasDetalleConsumoView(String str, HashMap<String, String> hashMap) {
        this.g.trackScreen(getResources().getString(R.string.analytics_screen_name_cards_detalle));
        ((TextView) this.pantallaTarjetasDetalleConsumo.findViewById(R.id.detalle_tarjeta)).setText(str);
        DateView dateView = (DateView) this.pantallaTarjetasDetalleConsumo.findViewById(R.id.detalle_fecha);
        DateView dateView2 = (DateView) this.pantallaTarjetasDetalleConsumo.findViewById(R.id.detalle_fecha_head);
        if (UtilDate.isDate((String) hashMap.get(TarjetasConstants.FECHA), Constants.FORMAT_DATE_APP)) {
            dateView.setDateStr(UtilDate.getDateFormat((String) hashMap.get(TarjetasConstants.FECHA), Constants.FORMAT_DATE_APP));
            dateView2.setDateStr(UtilDate.getDateWithNameMonth((String) hashMap.get(TarjetasConstants.FECHA), Constants.FORMAT_DATE_APP).toUpperCase());
        } else {
            dateView.setDateStr(Constants.FORMAT_DATE_APP_2);
            dateView2.setDateStr(UtilDate.getDateWithNameMonth((String) hashMap.get(TarjetasConstants.FECHA), Constants.FORMAT_DATE_APP_2).toUpperCase());
        }
        dateView.setCElementAcc(new CDateAcc());
        dateView2.setCElementAcc(new CDateAcc());
        TextView textView = (TextView) this.pantallaTarjetasDetalleConsumo.findViewById(R.id.detalle_desc);
        textView.setText(String.valueOf(Html.fromHtml(((String) hashMap.get(TarjetasConstants.DESC)).toLowerCase())));
        TextView textView2 = (TextView) this.pantallaTarjetasDetalleConsumo.findViewById(R.id.detalle_desc_head);
        textView2.setText(String.valueOf(Html.fromHtml(((String) hashMap.get(TarjetasConstants.DESC)).toLowerCase())));
        textView.setContentDescription(String.valueOf(Html.fromHtml(((String) hashMap.get(TarjetasConstants.DESC)).toLowerCase())));
        textView2.setContentDescription(String.valueOf(Html.fromHtml(((String) hashMap.get(TarjetasConstants.DESC)).toLowerCase())));
        CAmount cAmount = new CAmount((String) hashMap.get(TarjetasConstants.VALUE));
        cAmount.setSymbolCurrencyDollarOrPeso(!((String) hashMap.get(TarjetasConstants.MONEDA)).equals(TarjetasConstants.PESOS));
        AmountView amountView = (AmountView) this.pantallaTarjetasDetalleConsumo.findViewById(R.id.detalle_value);
        amountView.setAmount(cAmount.getAmount());
        amountView.setCElementAcc(new CAmountAcc());
        CAmount cAmount2 = new CAmount((String) hashMap.get(TarjetasConstants.VALUE));
        boolean z = !((String) hashMap.get(TarjetasConstants.MONEDA)).equals(TarjetasConstants.PESOS);
        cAmount2.setSymbolCurrencyDollarOrPeso(z);
        AmountView amountView2 = (AmountView) this.pantallaTarjetasDetalleConsumo.findViewById(R.id.detalle_value_head);
        amountView2.setAmount(cAmount2.getAmount());
        amountView2.setCElementAcc(new CAmountAcc());
        if (z) {
            ((TextView) this.pantallaTarjetasDetalleConsumo.findViewById(R.id.detalle_moneda)).setText("Dólares");
        } else {
            ((TextView) this.pantallaTarjetasDetalleConsumo.findViewById(R.id.detalle_moneda)).setText("Pesos");
        }
    }

    public void showProgress() {
        super.showProgress("");
    }

    public void dismissProgress() {
        super.dismissProgress();
    }

    public IDataManager getDataManager() {
        return this.e;
    }

    public void nextPage() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
            this.mControlPager.showNext();
        }
    }

    public void previousPage() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
            this.mControlPager.showPrevious();
        }
    }

    public void gotoPage(int i2) {
        if (this.mControlPager != null) {
            switch (i2) {
                case 0:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                    break;
                case 1:
                    if (this.mControlPager.getDisplayedChild() == i2) {
                        this.mControlPager.setInAnimation(null);
                        this.mControlPager.setOutAnimation(null);
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 2:
                    if (this.mControlPager.getDisplayedChild() != i2) {
                        if (!this.ak) {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                            break;
                        } else {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromUpAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToDownAnimation());
                            break;
                        }
                    } else {
                        this.mControlPager.setInAnimation(null);
                        this.mControlPager.setOutAnimation(null);
                        break;
                    }
                case 3:
                    if (this.mControlPager.getDisplayedChild() == i2) {
                        this.mControlPager.setInAnimation(null);
                        this.mControlPager.setOutAnimation(null);
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
                case 4:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
                case 5:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToUpAnimation());
                    break;
            }
            this.ak = false;
            this.mControlPager.setDisplayedChild(i2);
        }
    }

    public int getIndexViewPage(View view) {
        return this.mControlPager.indexOfChild(view);
    }

    /* access modifiers changed from: private */
    public void F() {
        View findViewById = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TarjetasFragment.this.onBackPressed();
                }
            });
        }
    }

    private void e(boolean z) {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_MENU);
        ((SantanderRioMainActivity) getActivity()).lockMenu(false);
        enableMenuButton();
        enableOptionsButton();
        this.aG.clear();
        this.aG.add("Límites y Disponibles");
        this.aG.add("Últimos Consumos");
        this.aG.add("Último Resumen");
        this.pantallaTarjetas.findViewById(R.id.principal_root_layout).setVisibility(0);
        this.pantallaTarjetas.findViewById(R.id.selector_root).setVisibility(0);
        this.pantallaTarjetas.findViewById(R.id.total_autorizaciones_pesos).setVisibility(0);
        this.pantallaTarjetas.findViewById(R.id.total_autorizaciones_dolares).setVisibility(0);
        this.tvProximoCierre.setVisibility(0);
        this.tvProximoVencimiento.setVisibility(0);
        this.pantallaTarjetas.findViewById(R.id.area_cierre_venc).setVisibility(0);
        this.pantallaTarjetas.findViewById(R.id.area_saldo).setVisibility(0);
        this.pantallaTarjetas.findViewById(R.id.area_autorizaciones).setVisibility(0);
        if (this.i) {
            this.pantallaTarjetas.findViewById(R.id.nav_pagar).setVisibility(8);
        } else {
            this.pantallaTarjetas.findViewById(R.id.nav_pagar).setVisibility(0);
        }
        this.az = false;
        if (z) {
            this.pantallaTarjetas.findViewById(R.id.compraDisp).setVisibility(0);
            this.pantallaTarjetas.findViewById(R.id.saldoNoDisponible).setVisibility(8);
        } else {
            this.pantallaTarjetas.findViewById(R.id.compraDisp).setVisibility(8);
            this.pantallaTarjetas.findViewById(R.id.saldoNoDisponible).setVisibility(0);
            this.aG.remove("Límites y Disponibles");
        }
        y();
        if (Utils.isAdicional(this.f.getSelected_tarjeta()) && !Utils.isRecargable(this.f.getSelected_tarjeta())) {
            if (z) {
                C();
            }
            this.aG.remove("Último Resumen");
            this.aG.remove("Límites y Disponibles");
            if (!this.f.usuarioTieneCuentas()) {
                this.pantallaTarjetas.findViewById(R.id.nav_pagar).setVisibility(8);
            }
            this.pantallaTarjetas.findViewById(R.id.area_saldo).setVisibility(8);
            this.pantallaTarjetas.findViewById(R.id.area_autorizaciones).setVisibility(8);
        } else if (Utils.isRecargable(this.f.getSelected_tarjeta())) {
            a(z);
            if (z) {
                if (!this.ag.getTarjetas().getDocument().getAutorizaciones().getResAutorizacion().equals("0")) {
                    this.pantallaTarjetas.findViewById(R.id.area_autorizaciones).setVisibility(0);
                }
                D();
            }
            this.pantallaTarjetas.findViewById(R.id.area_cierre_venc).setVisibility(8);
            this.aG.remove("Último Resumen");
            this.pantallaTarjetas.findViewById(R.id.nav_pagar).setVisibility(8);
        } else {
            a(z);
            if (z) {
                if (z && !this.ag.getTarjetas().getDocument().getAutorizaciones().getResAutorizacion().equals("0")) {
                    this.pantallaTarjetas.findViewById(R.id.area_autorizaciones).setVisibility(8);
                }
                D();
            }
            if (z) {
                C();
            }
            if (this.f.getLoginUnico().getProductos().getCuentas() == null || this.f.getLoginUnico().getProductos().getCuentas().getCuentas().size() <= 0) {
                this.pantallaTarjetas.findViewById(R.id.nav_pagar).setVisibility(8);
            }
        }
    }

    private void a(TextView textView) {
        if (textView.getText().toString().contains(TarjetasConstants.VISAR_CARD_MASK)) {
            textView.setTextSize(0, getResources().getDimension(R.dimen.titulo2_small_text_size));
        } else {
            textView.setTextSize(0, getResources().getDimension(R.dimen.titulo2_text_size));
        }
    }

    private void G() {
        for (Limite limite : this.ag.getTarjetas().getDocument().getSaldoenCuenta().getLimites().getLimites()) {
            CAmount cAmount = new CAmount(limite.getMonto());
            cAmount.setSymbolCurrencyDollarOrPeso(limite.getMoneda().equals("U$S") || limite.getMoneda().equals("u$s"));
            if (limite.getDescripcion().equals(TarjetasConstants.COMPRADISP)) {
                ((AmountView) this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.disponible_compra)).setCElementAcc(new CAmountAcc());
                ((AmountView) this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.disponible_compra)).setAmount(cAmount.getAmount());
            } else if (limite.getDescripcion().equals(TarjetasConstants.COMPADELDISP)) {
                ((AmountView) this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.disponible_adelanto)).setCElementAcc(new CAmountAcc());
                ((AmountView) this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.disponible_adelanto)).setAmount(cAmount.getAmount());
            } else if (limite.getDescripcion().equals(TarjetasConstants.COMPRA)) {
                ((AmountView) this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.limite_compra)).setCElementAcc(new CAmountAcc());
                ((AmountView) this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.limite_compra)).setAmount(cAmount.getAmount());
            } else if (limite.getDescripcion().equals(TarjetasConstants.ADELANTOS)) {
                ((AmountView) this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.limite_adelanto)).setCElementAcc(new CAmountAcc());
                ((AmountView) this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.limite_adelanto)).setAmount(cAmount.getAmount());
            }
        }
    }

    private void H() {
        this.ae = new ArrayList<>();
        for (Tarjeta tarjeta : this.ad.getTarjetas()) {
            if (!tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                String mascaraTarjeta = Utils.mascaraTarjeta(tarjeta);
                this.ae.add(mascaraTarjeta);
                try {
                    this.af.add(new CAccessibility(getActivity().getApplicationContext()).applyFilterCreditCard(Utils.mascaraTarjeta(tarjeta)));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    this.af.add("");
                }
                if (tarjeta.getNroTarjetaCredito().equals(this.f.getSelected_tarjeta().getNroTarjetaCredito())) {
                    this.aj = mascaraTarjeta;
                }
            }
        }
    }

    private void I() {
        this.aq = new ArrayList();
        int i2 = 0;
        for (Tarjeta tarjeta : this.ad.getTarjetas()) {
            if (!tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                this.aq.add(tarjeta);
                this.f241ar.put(Integer.valueOf(i2), tarjeta.getNroTarjetaCredito());
                i2++;
            }
        }
        z();
        if (this.aq.size() < 2) {
            this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.nav_selector_tarjeta).setVisibility(8);
            return;
        }
        this.pantallaTarjetasLimitesDisponibles.findViewById(R.id.nav_selector_tarjeta).setVisibility(0);
        this.ae = new ArrayList<>();
        for (Tarjeta tarjeta2 : this.aq) {
            if (!tarjeta2.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                this.ae.add(Utils.mascaraTarjeta(tarjeta2));
            }
        }
    }

    private void J() {
        this.aq = new ArrayList();
        int i2 = 0;
        for (Tarjeta tarjeta : this.ad.getTarjetas()) {
            if (!Utils.isRecargable(tarjeta) && !tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                this.aq.add(tarjeta);
                this.f241ar.put(Integer.valueOf(i2), tarjeta.getNroTarjetaCredito());
                i2++;
            }
        }
        B();
        if (this.aq.size() < 2) {
            this.pantallaTarjetasUltimosConsumos.findViewById(R.id.nav_selector_tarjeta).setVisibility(8);
            return;
        }
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.nav_selector_tarjeta).setVisibility(0);
        this.ae = new ArrayList<>();
        for (Tarjeta tarjeta2 : this.aq) {
            if (!tarjeta2.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                this.ae.add(Utils.mascaraTarjeta(tarjeta2));
            }
        }
    }

    private void K() {
        A();
        B();
        this.ae = new ArrayList<>();
        for (Tarjeta tarjeta : this.f.getLoginUnico().getProductos().getTarjetas().getTarjetas()) {
            if (!tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                this.ae.add(Utils.mascaraTarjeta(tarjeta));
            }
        }
        if (this.ae.size() < 2) {
            this.pantallaTarjetasUltimosConsumos.findViewById(R.id.nav_selector_tarjeta).setVisibility(4);
            this.pantallaTarjetasUltimoResumen.findViewById(R.id.nav_selector_tarjeta).setVisibility(4);
            return;
        }
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.nav_selector_tarjeta).setVisibility(0);
        this.pantallaTarjetasUltimoResumen.findViewById(R.id.nav_selector_tarjeta).setVisibility(0);
    }

    private HashMap<String, String> a(String str, String str2, String str3, String str4, String str5) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(TarjetasConstants.ESTADO, str);
        hashMap.put(TarjetasConstants.FECHA, str2);
        hashMap.put(TarjetasConstants.DESC, str3.toUpperCase());
        hashMap.put(TarjetasConstants.VALUE, str4);
        hashMap.put(TarjetasConstants.MONEDA, str5);
        return hashMap;
    }

    private HashMap<String, String> a(String str, String str2, String str3, String str4, String str5, String str6) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(TarjetasConstants.ESTADO, str);
        hashMap.put(TarjetasConstants.FECHA, str2);
        hashMap.put(TarjetasConstants.DESC, str3.toUpperCase());
        hashMap.put(TarjetasConstants.VALUE, str4);
        hashMap.put(TarjetasConstants.MONEDA, str5);
        hashMap.put(TarjetasConstants.TARJETA, str6);
        return hashMap;
    }

    private HashMap<String, String> a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(TarjetasConstants.ESTADO, str);
        hashMap.put(TarjetasConstants.FECHA, str2);
        hashMap.put(TarjetasConstants.DESC, str3.toUpperCase());
        hashMap.put(TarjetasConstants.VALUE, str4);
        hashMap.put(TarjetasConstants.MONEDA, str5);
        hashMap.put(TarjetasConstants.VALUE2, str6);
        hashMap.put(TarjetasConstants.MONEDA2, str7);
        return hashMap;
    }

    private HashMap<String, String> a(String str, String str2, String str3, String str4) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cell_date", str);
        hashMap.put("cell_desc", str2);
        hashMap.put("cell_value", str3);
        hashMap.put("cell_moneda", str4);
        return hashMap;
    }

    private HashMap<String, String> b(String str, String str2, String str3, String str4, String str5) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cell_date", str);
        hashMap.put("cell_desc", str2);
        hashMap.put("cell_value", str3);
        hashMap.put("cell_moneda", str4);
        hashMap.put("cell_tarjeta", str5);
        return hashMap;
    }

    private HashMap<String, String> b(String str, String str2, String str3, String str4, String str5, String str6) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cell_date", str);
        hashMap.put("cell_desc", str2);
        hashMap.put("cell_value", str3);
        hashMap.put("cell_moneda", str4);
        hashMap.put("cell_value_2", str5);
        hashMap.put("cell_moneda_2", str6);
        return hashMap;
    }

    private HashMap<String, String> a(String str, String str2, String str3) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sublist_title", str);
        hashMap.put("sublist_footer_total", str2);
        hashMap.put("sublist_footer_total_2", str3);
        return hashMap;
    }

    private void L() {
        if (this.ag != null) {
            this.aC = new ArrayList();
            this.aD = new ArrayList();
            this.at.clear();
            try {
                for (AuthTarjeta authTarjeta : this.ah.getDocument().getAutorizaciones().getTarjeta()) {
                    Tarjeta tarjeta = null;
                    Iterator it = this.f.getLoginUnico().getProductos().getTarjetas().getTarjetas().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Tarjeta tarjeta2 = (Tarjeta) it.next();
                        if (tarjeta2.getNroTarjetaCredito().equals(authTarjeta.getNumero())) {
                            tarjeta = new Tarjeta();
                            tarjeta.setNroTarjetaCredito(tarjeta2.getNroTarjetaCredito());
                            tarjeta.setClase(tarjeta2.getClase());
                            tarjeta.setTipo(tarjeta2.getTipo());
                            break;
                        }
                    }
                    if (tarjeta == null) {
                        tarjeta = new Tarjeta();
                        tarjeta.setClase(this.f.getSelected_tarjeta().getClase());
                        tarjeta.setTipo(this.f.getSelected_tarjeta().getTipo());
                        tarjeta.setNroTarjetaCredito(authTarjeta.getNumero());
                    }
                    ArrayList arrayList = new ArrayList();
                    if (authTarjeta.getAutorizacion() != null) {
                        for (Autorizacion autorizacion : authTarjeta.getAutorizacion()) {
                            arrayList.add(b(autorizacion.getFecha(), autorizacion.getEstablecimiento().getContent(), autorizacion.getImporte(), autorizacion.getMoneda(), Utils.mascaraTarjeta(tarjeta)));
                            this.at.add(a("true", autorizacion.getFecha(), autorizacion.getEstablecimiento().getContent(), autorizacion.getImporte(), autorizacion.getMoneda(), Utils.mascaraTarjeta(tarjeta)));
                        }
                    } else {
                        arrayList.add(new HashMap());
                    }
                    this.aC.add(new UltimoResumenAdapter(getActivity(), arrayList));
                    ArrayList arrayList2 = new ArrayList();
                    StringBuilder sb = new StringBuilder();
                    sb.append(getActivity().getResources().getString(R.string.ID508_CCARDS_LAST_LBL_TOTAL));
                    sb.append(UtilsCuentas.SEPARAOR2);
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append(Utils.mascaraTarjeta(tarjeta));
                    String sb4 = sb3.toString();
                    this.at.add(a(Reintento.Reintento_Falso, "", "", "", "", ""));
                    arrayList2.add(a(sb4, authTarjeta.getPesos(), authTarjeta.getDolares()));
                    this.aD.add(new UltimosConsumosAdapter(getActivity(), arrayList2));
                }
            } catch (Exception e2) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("buildDataAutorizaciones: ");
                sb5.append(e2.toString());
                Log.d("@dev", sb5.toString());
            }
        }
    }

    private void M() {
        if (this.ag != null) {
            this.aE = new ArrayList();
            this.aF = new ArrayList();
            this.as.clear();
            try {
                for (TarjetaConsumos tarjetaConsumos : this.ah.getDocument().getMovimientos().getTarjetas()) {
                    Tarjeta tarjeta = null;
                    Iterator it = this.f.getLoginUnico().getProductos().getTarjetas().getTarjetas().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Tarjeta tarjeta2 = (Tarjeta) it.next();
                        if (tarjeta2.getNroTarjetaCredito().equals(tarjetaConsumos.getCodigoTarjeta())) {
                            tarjeta = new Tarjeta();
                            tarjeta.setNroTarjetaCredito(tarjeta2.getNroTarjetaCredito());
                            tarjeta.setClase(tarjeta2.getClase());
                            tarjeta.setTipo(tarjeta2.getTipo());
                            break;
                        }
                    }
                    if (tarjeta == null) {
                        tarjeta = new Tarjeta();
                        tarjeta.setClase(this.f.getSelected_tarjeta().getClase());
                        tarjeta.setTipo(this.f.getSelected_tarjeta().getTipo());
                        tarjeta.setNroTarjetaCredito(tarjetaConsumos.getCodigoTarjeta());
                    }
                    ArrayList arrayList = new ArrayList();
                    if (tarjetaConsumos.getMovimientos() != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Tarjeta: ");
                        sb.append(tarjetaConsumos.getCodigoTarjeta());
                        sb.append(" Movimientos: ");
                        sb.append(tarjetaConsumos.getMovimientos().size());
                        Log.d("@dev", sb.toString());
                        for (MovimientoConsumos movimientoConsumos : tarjetaConsumos.getMovimientos()) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Fecha: ");
                            sb2.append(movimientoConsumos.getFecha());
                            sb2.append(" Establecimiento: ");
                            sb2.append(movimientoConsumos.getEstablecimiento());
                            sb2.append(" Importe: ");
                            sb2.append(movimientoConsumos.getImporte());
                            sb2.append(" Moneda: ");
                            sb2.append(movimientoConsumos.getTipoMoneda());
                            Log.d("@dev", sb2.toString());
                            arrayList.add(b(movimientoConsumos.getFecha(), movimientoConsumos.getEstablecimiento(), movimientoConsumos.getImporte(), movimientoConsumos.getTipoMoneda(), Utils.mascaraTarjeta(tarjeta)));
                            this.as.add(a("true", movimientoConsumos.getFecha(), movimientoConsumos.getEstablecimiento(), movimientoConsumos.getImporte(), movimientoConsumos.getTipoMoneda(), Utils.mascaraTarjeta(tarjeta)));
                        }
                    } else {
                        arrayList.add(new HashMap());
                    }
                    this.aE.add(new UltimoResumenAdapter(getActivity(), arrayList));
                    ArrayList arrayList2 = new ArrayList();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(getActivity().getResources().getString(R.string.ID518_CCARDS_LASTBRIEF_LBL_TOTAL));
                    sb3.append(UtilsCuentas.SEPARAOR2);
                    String sb4 = sb3.toString();
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(sb4);
                    sb5.append(Utils.mascaraTarjeta(tarjeta));
                    String sb6 = sb5.toString();
                    this.as.add(a(Reintento.Reintento_Falso, "", "", "", ""));
                    arrayList2.add(a(sb6, tarjetaConsumos.getPesos(), tarjetaConsumos.getDolares()));
                    this.aF.add(new UltimosConsumosAdapter(getActivity(), arrayList2));
                }
            } catch (Exception e2) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append("buildData: ");
                sb7.append(e2.toString());
                Log.d("@dev", sb7.toString());
            }
        }
    }

    /* access modifiers changed from: private */
    public void N() {
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_pendientes_head).setVisibility(0);
        this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_title).setVisibility(0);
        try {
            if (this.ah.getDocument().getAutorizaciones().getResAutorizacion().equals("0")) {
                L();
                this.pantallaTarjetasUltimosConsumos.findViewById(R.id.autorizaciones_table_head).setVisibility(0);
                this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_head).setVisibility(0);
                int i2 = 0;
                for (int i3 = 0; i3 < this.aC.size(); i3++) {
                    int i4 = i2;
                    for (int i5 = 0; i5 < ((ListAdapter) this.aC.get(i3)).getCount(); i5++) {
                        View view = ((ListAdapter) this.aC.get(i3)).getView(i5, null, null);
                        view.setTag(Integer.valueOf(i4));
                        i4++;
                        view.setOnClickListener(this);
                        ((LinearLayout) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_pendientes_list)).addView(view);
                    }
                    if (this.aD.size() > 0) {
                        for (int i6 = 0; i6 < ((ListAdapter) this.aD.get(i3)).getCount(); i6++) {
                            View view2 = ((ListAdapter) this.aD.get(i3)).getView(i6, null, null);
                            view2.setTag(Integer.valueOf(i4));
                            i4++;
                            view2.setOnClickListener(this);
                            ((LinearLayout) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_pendientes_list)).addView(view2);
                        }
                    }
                    i2 = i4;
                }
            } else {
                this.pantallaTarjetasUltimosConsumos.findViewById(R.id.autorizaciones_warning).setVisibility(0);
                ((TextView) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.autorizaciones_warning)).setText(Html.fromHtml(Utils.formatIsbanHTMLCode(this.ah.getDocument().getAutorizaciones().getMensajeAutorizacion())));
                this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_pendientes_footer).setVisibility(8);
            }
            this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_head).setVisibility(0);
            M();
            StringBuilder sb = new StringBuilder();
            sb.append("Items en listado Últimos Consumos tras buildData() ");
            sb.append(this.aE.size());
            Log.d("@dev", sb.toString());
            int i7 = 0;
            int i8 = 0;
            while (i7 < this.aE.size()) {
                int i9 = i8;
                for (int i10 = 0; i10 < ((ListAdapter) this.aE.get(i7)).getCount(); i10++) {
                    View view3 = ((ListAdapter) this.aE.get(i7)).getView(i10, null, null);
                    view3.setTag(Integer.valueOf(i9));
                    i9++;
                    view3.setOnClickListener(this);
                    ((LinearLayout) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_list)).addView(view3);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("detalle: ");
                    sb2.append(i7);
                    sb2.append(UtilsCuentas.SEPARAOR2);
                    sb2.append(i10);
                    Log.d("@dev", sb2.toString());
                }
                for (int i11 = 0; i11 < ((ListAdapter) this.aF.get(i7)).getCount(); i11++) {
                    View view4 = ((ListAdapter) this.aF.get(i7)).getView(i11, null, null);
                    view4.setTag(Integer.valueOf(i9));
                    i9++;
                    view4.setOnClickListener(this);
                    ((LinearLayout) this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_consumos_list)).addView(view4);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("footer: ");
                    sb3.append(i7);
                    sb3.append(UtilsCuentas.SEPARAOR2);
                    sb3.append(i11);
                    Log.d("@dev", sb3.toString());
                }
                i7++;
                i8 = i9;
            }
            String pesos = this.ah.getDocument().getMovimientos().getTotales().getPesos();
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Totales pesos: ");
            sb4.append(pesos);
            Log.d("@dev", sb4.toString());
            if (pesos == null || pesos.equals("0") || pesos.isEmpty()) {
                pesos = TarjetasConstants.FORMATTED_ZERO;
            }
            CAmount cAmount = new CAmount(pesos);
            cAmount.setSymbolCurrencyDollarOrPeso(false);
            this.main_total_consumos.setCElementAcc(new CAmountAcc());
            this.main_total_consumos.setAmount(cAmount.getAmount());
            String dolares = this.ah.getDocument().getMovimientos().getTotales().getDolares();
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Totales dolares: ");
            sb5.append(dolares);
            Log.d("@dev", sb5.toString());
            if (dolares == null || dolares.equals("0") || dolares.isEmpty()) {
                dolares = TarjetasConstants.FORMATTED_ZERO;
            }
            CAmount cAmount2 = new CAmount(dolares);
            cAmount2.setSymbolCurrencyDollarOrPeso(true);
            this.main_total_consumos_2.setCElementAcc(new CAmountAcc());
            this.main_total_consumos_2.setAmount(cAmount2.getAmount());
            if (this.ah.getDocument().getAutorizaciones().getResAutorizacion().equals("0")) {
                this.pantallaTarjetasUltimosConsumos.findViewById(R.id.consumos_main_total_pendientes).setVisibility(0);
                String pesos2 = this.ah.getDocument().getAutorizaciones().getTotales().getPesos();
                if (pesos2 == null || pesos2.equals("0") || pesos2.isEmpty()) {
                    pesos2 = TarjetasConstants.FORMATTED_ZERO;
                }
                CAmount cAmount3 = new CAmount(pesos2, "#.##0,00");
                cAmount3.setSymbolCurrencyDollarOrPeso(false);
                this.pendientes_total_consumos.setCElementAcc(new CAmountAcc());
                this.pendientes_total_consumos.setAmount(cAmount3.getAmount());
                String dolares2 = this.ah.getDocument().getAutorizaciones().getTotales().getDolares();
                if (dolares2 == null || dolares2.equals("0") || dolares2.isEmpty()) {
                    dolares2 = TarjetasConstants.FORMATTED_ZERO;
                }
                CAmount cAmount4 = new CAmount(dolares2, "#.##0,00");
                cAmount4.setSymbolCurrencyDollarOrPeso(true);
                this.pendientes_total_consumos_2.setCElementAcc(new CAmountAcc());
                this.pendientes_total_consumos_2.setAmount(cAmount4.getAmount());
                String pesos3 = this.ah.getDocument().getTotalMovAuto().getPesos();
                if (pesos3 == null || pesos3.equals("0") || pesos3.isEmpty()) {
                    pesos3 = TarjetasConstants.FORMATTED_ZERO;
                }
                CAmount cAmount5 = new CAmount(pesos3, "#.##0,00");
                cAmount5.setSymbolCurrencyDollarOrPeso(false);
                this.main_total_autorizaciones.setCElementAcc(new CAmountAcc());
                this.main_total_autorizaciones.setAmount(cAmount5.getAmount());
                String dolares3 = this.ah.getDocument().getTotalMovAuto().getDolares();
                if (dolares3 == null || dolares3.equals("0") || dolares3.isEmpty()) {
                    dolares3 = TarjetasConstants.FORMATTED_ZERO;
                }
                CAmount cAmount6 = new CAmount(dolares3, "#.##0,00");
                cAmount6.setSymbolCurrencyDollarOrPeso(true);
                this.main_total_autorizaciones_2.setCElementAcc(new CAmountAcc());
                this.main_total_autorizaciones_2.setAmount(cAmount6.getAmount());
            } else {
                this.pantallaTarjetasUltimosConsumos.findViewById(R.id.consumos_main_total_pendientes).setVisibility(8);
            }
            this.pantallaTarjetasUltimosConsumos.findViewById(R.id.tarjetas_ultimos_consumos_main_footer).setVisibility(0);
        } catch (Exception e2) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Event ultimosConsumosTC: ");
            sb6.append(e2.toString());
            Log.d("@dev", sb6.toString());
        }
    }

    private void O() {
        if (this.aA == null) {
            this.aA = getActivity().getLayoutInflater().inflate(R.layout.tarjetas_ultimo_resumen_list_footer, (ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list), false);
            ((ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list)).addFooterView(this.aA, null, false);
        } else if (((ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list)).getFooterViewsCount() == 0) {
            ((ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list)).addFooterView(this.aA, null, false);
        }
        CAmount cAmount = new CAmount(((Saldo) this.ai.getTarjeta().getLiquidacion().getSaldos().getSaldos().get(0)).getTotal());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        ((AmountView) this.aA.findViewById(R.id.ultimo_resumen_total)).setAmount(cAmount.getAmount());
        ((AmountView) this.aA.findViewById(R.id.ultimo_resumen_total)).setCElementAcc(new CAmountAcc());
        CAmount cAmount2 = new CAmount(((Saldo) this.ai.getTarjeta().getLiquidacion().getSaldos().getSaldos().get(0)).getUsdTotal());
        cAmount2.setSymbolCurrencyDollarOrPeso(true);
        ((AmountView) this.aA.findViewById(R.id.ultimo_resumen_total_dolares)).setAmount(cAmount2.getAmount());
        ((AmountView) this.aA.findViewById(R.id.ultimo_resumen_total_dolares)).setCElementAcc(new CAmountAcc());
        Iterator it = this.ai.getTarjeta().getLiquidacion().getPagos().getPagos().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Pago pago = (Pago) it.next();
            if (pago.getDescripcion().equals(TarjetasConstants.MINIMO) && pago.getTipoMoneda().equals(TarjetasConstants.PESOS)) {
                CAmount cAmount3 = new CAmount(pago.getTotal());
                cAmount3.setSymbolCurrencyDollarOrPeso(false);
                ((AmountView) this.aA.findViewById(R.id.ultimo_resumen_pagos_pesos)).setCElementAcc(new CAmountAcc());
                ((AmountView) this.aA.findViewById(R.id.ultimo_resumen_pagos_pesos)).setAmount(cAmount3.getAmount());
                break;
            }
        }
        String str = "";
        Iterator it2 = this.ai.getTarjeta().getLiquidacion().getFechas().getFechas().iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            FechaLiquidacion fechaLiquidacion = (FechaLiquidacion) it2.next();
            if (fechaLiquidacion.getDescripcion().equals(TarjetasConstants.PAGO_ACTUAL)) {
                str = fechaLiquidacion.getVencimiento();
                break;
            }
        }
        ((TextView) this.aA.findViewById(R.id.ultimo_resumen_vencimiento)).setText(str);
        ((TextView) this.aA.findViewById(R.id.ultimo_resumen_proximo)).setText(((FechaLiquidacion) this.ai.getTarjeta().getLiquidacion().getFechas().getFechas().get(0)).getCierre());
        ((TextView) this.aA.findViewById(R.id.ultimo_resumen_proximo_2)).setText(((FechaLiquidacion) this.ai.getTarjeta().getLiquidacion().getFechas().getFechas().get(0)).getVencimiento());
        this.aA.findViewById(R.id.nav_terminos).setOnClickListener(this);
    }

    private ArrayList<HashMap<String, String>> P() {
        HashMap hashMap;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        this.as.clear();
        for (Movimiento movimiento : this.ai.getTarjeta().getLiquidacion().getDetalleLiquidacion().getMovimientos().getMovimientos()) {
            String montoPesos = movimiento.getMontoPesos();
            String montoDolares = movimiento.getMontoDolares();
            String str = (montoPesos == null || montoDolares == null) ? montoPesos != null ? TarjetasConstants.PESOS : montoDolares != null ? TarjetasConstants.DOLAR : "" : TarjetasConstants.BIMONETARIO;
            String str2 = str;
            String str3 = "";
            String fecha = movimiento.getFecha();
            if (!str2.equals(TarjetasConstants.BIMONETARIO)) {
                char c2 = 65535;
                int hashCode = str2.hashCode();
                if (hashCode != 106557314) {
                    if (hashCode == 1836633440 && str2.equals(TarjetasConstants.DOLAR)) {
                        c2 = 1;
                    }
                } else if (str2.equals(TarjetasConstants.PESOS)) {
                    c2 = 0;
                }
                switch (c2) {
                    case 0:
                        str3 = montoPesos;
                        break;
                    case 1:
                        str3 = montoDolares;
                        break;
                }
                arrayList.add(a(fecha, movimiento.getDescripcion(), str3, str2));
            } else {
                arrayList.add(b(fecha, movimiento.getDescripcion(), montoPesos, TarjetasConstants.PESOS, montoDolares, TarjetasConstants.DOLAR));
            }
            String str4 = str3;
            if (fecha == null || fecha.isEmpty()) {
                this.as.add(a(Reintento.Reintento_Falso, "", "", "", ""));
            } else {
                List<HashMap<String, String>> list = this.as;
                if (str2.equals(TarjetasConstants.BIMONETARIO)) {
                    hashMap = a("true", movimiento.getFecha(), movimiento.getDescripcion(), montoPesos, TarjetasConstants.PESOS, montoDolares, TarjetasConstants.DOLAR);
                } else {
                    hashMap = a("true", movimiento.getFecha(), movimiento.getDescripcion(), str4, str2);
                }
                list.add(hashMap);
            }
        }
        return arrayList;
    }

    private void Q() {
        ((ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list)).setAdapter(new UltimoResumenAdapter(getActivity(), P()));
        O();
        ((ListView) this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list)).setOnItemClickListener(this);
        this.pantallaTarjetasUltimoResumen.findViewById(R.id.ultimo_resumen_list).setVisibility(0);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onPreAutorizacionSelected position ");
        sb.append(i2);
        Log.d(str, sb.toString());
        HashMap hashMap = (HashMap) this.as.get(i2 - 1);
        Log.d(TAG, "------onPreAutorizacionSelected mapaDetalles  ---------------------------------");
        for (Entry entry : hashMap.entrySet()) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("key ");
            sb2.append((String) entry.getKey());
            Log.d(str2, sb2.toString());
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("val ");
            sb3.append((String) entry.getValue());
            Log.d(str3, sb3.toString());
        }
        Log.d(TAG, "----------------------------------------------------------------------");
        if (((String) hashMap.get(TarjetasConstants.ESTADO)).equals("true")) {
            this.ay = true;
            goToTarjetasDetalleConsumo(this.aj, hashMap);
        }
    }

    private void g(Tarjeta tarjeta) {
        this.a = tarjeta.getNroTarjetaCredito();
    }

    private void R() {
        ((TextView) this.pantallaTarjetas.findViewById(R.id.saldoNoDisponible)).setText("");
        ((AmountView) this.pantallaTarjetas.findViewById(R.id.total_autorizaciones_dolares)).setAmount(getString(R.string.ID498_CCARDS_MAIN_LBL_NODATA));
        this.autorizPesos.setText(R.string.ID498_CCARDS_MAIN_LBL_NODATA);
        this.tvProximoCierre.setText(R.string.ID498_CCARDS_MAIN_LBL_NODATA);
        this.tvProximoVencimiento.setText(R.string.ID498_CCARDS_MAIN_LBL_NODATA);
        this.aG.remove("Límites y Disponibles");
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (!activityResultHandler(i3, intent, FragmentConstants.TARJETAS)) {
            super.onActivityResult(i2, i3, intent);
        }
    }

    private Tarjeta S() {
        Tarjeta tarjeta;
        Iterator it = this.ad.getTarjetas().iterator();
        while (true) {
            if (!it.hasNext()) {
                tarjeta = null;
                break;
            }
            tarjeta = (Tarjeta) it.next();
            if (!tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                break;
            }
        }
        return tarjeta == null ? (Tarjeta) this.ad.getTarjetas().get(0) : tarjeta;
    }
}
