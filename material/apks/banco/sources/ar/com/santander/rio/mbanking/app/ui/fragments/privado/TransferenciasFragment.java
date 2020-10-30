package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.module.transfers.AgendaDestinatariosPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.AgendaDestinatariosPresenterImp;
import ar.com.santander.rio.mbanking.app.module.transfers.ComprobanteTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.ComprobanteTransferenciaPresenterImp;
import ar.com.santander.rio.mbanking.app.module.transfers.ConfirmacionTransferenciaPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.ConfirmacionTransferenciaPresenterImp;
import ar.com.santander.rio.mbanking.app.module.transfers.LegalesPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.LegalesPresenterImp;
import ar.com.santander.rio.mbanking.app.module.transfers.LimitesHorariosTransferenciasPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.LimitesHorariosTransferenciasPresenterImp;
import ar.com.santander.rio.mbanking.app.module.transfers.TerminosCondicionesPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.TerminosCondicionesPresenterImp;
import ar.com.santander.rio.mbanking.app.module.transfers.TransferenciasView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.EditarEliminarDestinatarioTransferenciaActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.NuevoDestinatarioTransferenciaActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SolicitudAumentoActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter.ViewHolder;
import ar.com.santander.rio.mbanking.app.ui.constants.BuySellDollarsConstants.Dialogs;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.Origen;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.accionVerificarDatosTransf;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ConsultaDatosInicialesTransfEvent;
import ar.com.santander.rio.mbanking.services.events.TransferenciasEvent;
import ar.com.santander.rio.mbanking.services.events.VerificaDatosInicialesTransfEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaDatosInicialesTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferenciasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CargaInfoTransfBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsDescriptionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CtaMigradaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosIniciales;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaSalidaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaDatosInicialesTransf;
import ar.com.santander.rio.mbanking.services.soap.versions.VVerificaDatosInicialesTransf;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnFocusChange;
import com.squareup.otto.Subscribe;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class TransferenciasFragment extends BaseFragment implements OnClickListener, TransferenciasView {
    public static final String LOG_TAG = "ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment";
    public static final String TAG_FRAGMENT_LOAD = "tag_fragment_load";
    View a;
    private String aA = "";
    private String aB = "";
    private String aC = "";
    private ArrayList<String> aD = null;
    private ArrayList<String> aE = null;
    /* access modifiers changed from: private */
    public ConsDescriptionBodyResponseBean aF = null;
    private CuentasPropiasBean aG = null;
    /* access modifiers changed from: private */
    public DatosCuentasBean aH = null;
    /* access modifiers changed from: private */
    public LeyendasBean aI = null;
    private AgendadosBean aJ = null;
    /* access modifiers changed from: private */
    public DatosCuentasDestBSRBean aK = null;
    /* access modifiers changed from: private */
    public DatosCuentasDestOBBean aL = null;
    /* access modifiers changed from: private */
    public DatosCuentasBean aM = null;
    /* access modifiers changed from: private */
    public VerificaDatosInicialesTransfOBResponseBean aN = null;
    private CFiltersAccessibility aO = null;
    /* access modifiers changed from: private */
    public ProgresIndicator aP;
    private boolean aQ = false;
    private boolean aR = false;
    private boolean aS = false;
    private boolean aT = false;
    private String aU;
    /* access modifiers changed from: private */
    public String aV = TransferenciasConstants.cBANCO_CUENTAS_PROPIAS;
    /* access modifiers changed from: private */
    public CargaInfoTransfBean aW;
    /* access modifiers changed from: private */
    public String aX = "NONE";
    /* access modifiers changed from: private */
    public View aY;
    /* access modifiers changed from: private */
    public boolean aZ = false;
    /* access modifiers changed from: private */
    public String ad;
    /* access modifiers changed from: private */
    public String ae = "N";
    /* access modifiers changed from: private */
    public AgendaDestinatarios af = null;
    private LimitesHorariosTransferenciasPresenter ag;
    /* access modifiers changed from: private */
    public ConfirmacionTransferenciaPresenter ah;
    private AgendaDestinatariosPresenter ai;
    private TerminosCondicionesPresenter aj;
    private LegalesPresenter ak;
    private ComprobanteTransferenciaPresenter al;
    /* access modifiers changed from: private */
    public AgendaDestinatariosAdapter am;
    /* access modifiers changed from: private */
    public ArrayList<String> an;
    private String ao;
    private String ap;
    /* access modifiers changed from: private */
    public String aq;

    /* renamed from: ar reason: collision with root package name */
    private String f243ar;
    @InjectView(2131364163)
    ImageView arrowright;
    private String as;
    private String at;
    private String au;
    private String av;
    private String aw;
    private String ax;
    private String ay;
    private String az;
    @Inject
    SessionManager b;
    /* access modifiers changed from: private */
    public String ba = "ABM_ONLY";
    private String bb = "";
    /* access modifiers changed from: private */
    public AgendaDestinatarios bc;
    /* access modifiers changed from: private */
    public boolean bd = false;
    private IsbanDialogFragment be;
    /* access modifiers changed from: private */
    public OptionsToShare bf;
    /* access modifiers changed from: private */
    public ArrayList<AgendaDestinatarios> bg = new ArrayList<>();
    private ArrayList<AgendaDestinatarios> bh = new ArrayList<>();
    /* access modifiers changed from: private */
    public boolean bi = false;
    private boolean bj = false;
    private CtaMigradaBean bk;
    private FragmentActivity bl;
    /* access modifiers changed from: private */
    public LinearLayout bm;
    @Inject
    IDataManager c;
    @InjectView(2131364340)
    View comprobanteTransferencia;
    @Inject
    AnalyticsManager d;
    @Inject
    SoftTokenManager e;
    @InjectView(2131364648)
    NumericEditTextWithPrefixAccesibility etImporte;
    @InjectView(2131364652)
    EditText etReferenciaConcepto;
    OnSingleClickListener f = null;
    OnSingleClickListener g = null;
    public OnTouchListener gestureListItemListener;
    TextView h;
    private String i;
    @InjectView(2131364805)
    ImageView imgSelectedCurrency;
    @InjectView(2131365032)
    View linearLayoutAlias;
    @InjectView(2131365036)
    View linearLayoutBancoDestino;
    @InjectView(2131365038)
    View linearLayoutCBU;
    @InjectView(2131365041)
    View linearLayoutCUIT;
    @InjectView(2131365068)
    View linearLayoutNumero;
    @InjectView(2131365087)
    View linearLayoutTipo;
    @InjectView(2131365088)
    View linearLayoutTipoCuenta;
    @InjectView(2131365093)
    View linearLayoutTitular;
    @InjectView(2131365094)
    View linearLayoutTitularDivider;
    @InjectView(2131365105)
    ListView listaDestinatarios;
    protected Context mContext;
    @InjectView(2131366073)
    ViewFlipper mControlPager;
    @InjectView(2131364143)
    View pantallaAgendaDestinatarios;
    @InjectView(2131364349)
    View pantallaComprobanteTransferencia;
    @InjectView(2131364408)
    View pantallaConfirmacionTransferencia;
    @InjectView(2131364991)
    View pantallaLegales;
    @InjectView(2131365016)
    View pantallaLimitesHorarios;
    @InjectView(2131365847)
    View pantallaTerminosCondiciones;
    @InjectView(2131366072)
    View pantallaTransferencias;
    @InjectView(2131365681)
    RelativeLayout selectorImporteRLL;
    @InjectView(2131365796)
    View svComprobante;
    @InjectView(2131365999)
    TextView textViewSeleccionar;
    @InjectView(2131364776)
    View transferenciasView;
    @InjectView(2131364820)
    TextView txtSelectedCurrency;

    public class SortBasedOnName implements Comparator {
        public SortBasedOnName() {
        }

        public int compare(Object obj, Object obj2) {
            return ((AgendaDestinatarios) obj).getTitulo().compareToIgnoreCase(((AgendaDestinatarios) obj2).getTitulo());
        }
    }

    class SwipeListItemListener implements OnTouchListener {
        private int b = 50;
        private int c = 0;
        private int d = 0;
        private int e = 0;
        private int f = 0;
        private int g = 0;
        private int h = 0;
        private int i = 0;

        SwipeListItemListener() {
        }

        private void a() {
            this.f = 0;
            this.g = 0;
            this.h = 0;
            this.i = 0;
            TransferenciasFragment.this.aX = "NONE";
        }

        private void a(View view) {
            try {
                TransferenciasFragment.this.a((AgendaDestinatarios) TransferenciasFragment.this.listaDestinatarios.getItemAtPosition(((ViewHolder) view.getTag()).position), (RelativeLayout) view.findViewById(R.id.rllGroup_datos_item), (RelativeLayout) view.findViewById(R.id.rllGroup_botones_acciones), 0, false);
            } catch (Exception unused) {
            }
        }

        private void b(View view) {
            if (TransferenciasFragment.this.aY != null && TransferenciasFragment.this.aY != view) {
                a(TransferenciasFragment.this.aY);
                TransferenciasFragment.this.aY = null;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:44:0x0161  */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0176  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x0195  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouch(android.view.View r10, android.view.MotionEvent r11) {
            /*
                r9 = this;
                java.lang.Object r0 = r10.getTag()
                ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter$ViewHolder r0 = (ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter.ViewHolder) r0
                int r0 = r0.position
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r1 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                android.widget.ListView r1 = r1.listaDestinatarios
                java.lang.Object r0 = r1.getItemAtPosition(r0)
                r2 = r0
                ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r2 = (ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios) r2
                r0 = 2131365578(0x7f0a0eca, float:1.8351025E38)
                android.view.View r0 = r10.findViewById(r0)
                r3 = r0
                android.widget.RelativeLayout r3 = (android.widget.RelativeLayout) r3
                r0 = 2131365577(0x7f0a0ec9, float:1.8351023E38)
                android.view.View r0 = r10.findViewById(r0)
                r4 = r0
                android.widget.RelativeLayout r4 = (android.widget.RelativeLayout) r4
                android.view.ViewGroup$LayoutParams r0 = r3.getLayoutParams()
                android.widget.RelativeLayout$LayoutParams r0 = (android.widget.RelativeLayout.LayoutParams) r0
                r1 = 2131364204(0x7f0a096c, float:1.8348238E38)
                android.view.View r1 = r10.findViewById(r1)
                android.widget.Button r1 = (android.widget.Button) r1
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r5 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                android.content.Context r5 = r5.getContext()
                r6 = 2131758250(0x7f100caa, float:1.9147459E38)
                java.lang.String r5 = r5.getString(r6)
                r1.setContentDescription(r5)
                r1 = 2131364205(0x7f0a096d, float:1.834824E38)
                android.view.View r1 = r10.findViewById(r1)
                android.widget.Button r1 = (android.widget.Button) r1
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r5 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                android.content.Context r5 = r5.getContext()
                r6 = 2131758251(0x7f100cab, float:1.914746E38)
                java.lang.String r5 = r5.getString(r6)
                r1.setContentDescription(r5)
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r1 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                android.content.res.Resources r1 = r1.getResources()
                android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
                r7 = 1
                r5 = 1119092736(0x42b40000, float:90.0)
                float r1 = android.util.TypedValue.applyDimension(r7, r5, r1)
                int r1 = (int) r1
                int r1 = r1 * -2
                int r5 = r11.getAction()
                r6 = -1
                r8 = 0
                switch(r5) {
                    case 0: goto L_0x0254;
                    case 1: goto L_0x01b3;
                    case 2: goto L_0x0083;
                    case 3: goto L_0x007e;
                    default: goto L_0x007c;
                }
            L_0x007c:
                goto L_0x0278
            L_0x007e:
                r9.a(r10)
                goto L_0x0278
            L_0x0083:
                r9.b(r10)
                float r10 = r11.getX()
                int r10 = (int) r10
                r9.h = r10
                float r10 = r11.getY()
                int r10 = (int) r10
                r9.i = r10
                int r10 = r9.h
                int r11 = r9.f
                int r10 = r10 - r11
                r9.c = r10
                int r10 = r9.i
                int r11 = r9.g
                int r10 = r10 - r11
                r9.d = r10
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r10 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                int r11 = r9.c
                int r2 = r9.b
                int r11 = r11 - r2
                if (r11 <= 0) goto L_0x00ae
                java.lang.String r11 = "RIGHT"
                goto L_0x00ba
            L_0x00ae:
                int r11 = r9.c
                int r2 = r9.b
                int r11 = r11 + r2
                if (r11 >= 0) goto L_0x00b8
                java.lang.String r11 = "LEFT"
                goto L_0x00ba
            L_0x00b8:
                java.lang.String r11 = "NONE"
            L_0x00ba:
                r10.aX = r11
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r10 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r11 = r11.aX
                java.lang.String r2 = "NONE"
                boolean r11 = r11.equals(r2)
                if (r11 != 0) goto L_0x00d4
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r11 = r11.aX
                goto L_0x00ea
            L_0x00d4:
                int r11 = r9.d
                int r2 = r9.b
                int r11 = r11 - r2
                if (r11 <= 0) goto L_0x00de
                java.lang.String r11 = "UP"
                goto L_0x00ea
            L_0x00de:
                int r11 = r9.d
                int r2 = r9.b
                int r11 = r11 + r2
                if (r11 >= 0) goto L_0x00e8
                java.lang.String r11 = "DOWN"
                goto L_0x00ea
            L_0x00e8:
                java.lang.String r11 = "NONE"
            L_0x00ea:
                r10.aX = r11
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r10 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r10 = r10.aX
                java.lang.String r11 = "NONE"
                boolean r10 = r10.equals(r11)
                if (r10 != 0) goto L_0x0123
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r10 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r10 = r10.aX
                java.lang.String r11 = "UP"
                boolean r10 = r10.equals(r11)
                if (r10 != 0) goto L_0x0123
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r10 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r10 = r10.aX
                java.lang.String r11 = "DOWN"
                boolean r10 = r10.equals(r11)
                if (r10 != 0) goto L_0x0123
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r10 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                r10.aZ = r7
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r10 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                android.widget.ListView r10 = r10.listaDestinatarios
                r10.requestDisallowInterceptTouchEvent(r7)
            L_0x0123:
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r10 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r10 = r10.aX
                int r11 = r10.hashCode()
                r2 = 2332679(0x239807, float:3.26878E-39)
                if (r11 == r2) goto L_0x0151
                r2 = 2402104(0x24a738, float:3.366065E-39)
                if (r11 == r2) goto L_0x0147
                r2 = 77974012(0x4a5c9fc, float:3.8976807E-36)
                if (r11 == r2) goto L_0x013d
                goto L_0x015b
            L_0x013d:
                java.lang.String r11 = "RIGHT"
                boolean r10 = r10.equals(r11)
                if (r10 == 0) goto L_0x015b
                r10 = 1
                goto L_0x015c
            L_0x0147:
                java.lang.String r11 = "NONE"
                boolean r10 = r10.equals(r11)
                if (r10 == 0) goto L_0x015b
                r10 = 2
                goto L_0x015c
            L_0x0151:
                java.lang.String r11 = "LEFT"
                boolean r10 = r10.equals(r11)
                if (r10 == 0) goto L_0x015b
                r10 = 0
                goto L_0x015c
            L_0x015b:
                r10 = -1
            L_0x015c:
                switch(r10) {
                    case 0: goto L_0x0195;
                    case 1: goto L_0x0176;
                    case 2: goto L_0x0161;
                    default: goto L_0x015f;
                }
            L_0x015f:
                goto L_0x0278
            L_0x0161:
                int r10 = r9.e
                int r10 = r10 * -1
                r0.setMargins(r8, r8, r10, r8)
                r3.setLayoutParams(r0)
                int r10 = r9.e
                if (r10 != 0) goto L_0x0171
                r8 = 8
            L_0x0171:
                r4.setVisibility(r8)
                goto L_0x0278
            L_0x0176:
                int r10 = r9.e
                int r11 = r9.c
                int r10 = r10 + r11
                if (r10 > 0) goto L_0x0183
                int r10 = r9.e
                int r11 = r9.c
                int r10 = r10 + r11
                goto L_0x0184
            L_0x0183:
                r10 = 0
            L_0x0184:
                r9.c = r10
                int r10 = r9.c
                int r10 = r10 * -1
                r0.setMargins(r8, r8, r10, r8)
                r3.setLayoutParams(r0)
                r4.setVisibility(r8)
                goto L_0x0278
            L_0x0195:
                int r10 = r9.e
                int r11 = r9.c
                int r10 = r10 + r11
                if (r10 < r1) goto L_0x01a2
                int r10 = r9.e
                int r11 = r9.c
                int r1 = r10 + r11
            L_0x01a2:
                r9.c = r1
                int r10 = r9.c
                int r10 = r10 * -1
                r0.setMargins(r8, r8, r10, r8)
                r3.setLayoutParams(r0)
                r4.setVisibility(r8)
                goto L_0x0278
            L_0x01b3:
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                r11.aZ = r8
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                android.widget.ListView r11 = r11.listaDestinatarios
                r11.requestDisallowInterceptTouchEvent(r8)
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r11 = r11.d
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r0 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                r5 = 2131757795(0x7f100ae3, float:1.9146536E38)
                java.lang.String r0 = r0.getString(r5)
                r11.trackScreen(r0)
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r11 = r11.aX
                java.lang.String r0 = "NONE"
                boolean r11 = r11.equals(r0)
                if (r11 != 0) goto L_0x0211
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r11 = r11.aX
                java.lang.String r0 = "UP"
                boolean r11 = r11.equals(r0)
                if (r11 != 0) goto L_0x0211
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r11 = r11.aX
                java.lang.String r0 = "DOWN"
                boolean r11 = r11.equals(r0)
                if (r11 != 0) goto L_0x0211
                int r11 = r9.c
                int r0 = r1 / 2
                if (r11 >= r0) goto L_0x0209
                int r1 = r1 * -1
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                r11.aY = r10
                r5 = r1
                r6 = 1
                goto L_0x020b
            L_0x0209:
                r5 = 0
                r6 = 0
            L_0x020b:
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r1 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                r1.a(r2, r3, r4, r5, r6)
                goto L_0x0250
            L_0x0211:
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.String r11 = r11.aX
                java.lang.String r0 = "NONE"
                boolean r11 = r11.equals(r0)
                if (r11 == 0) goto L_0x0250
                java.lang.Boolean r11 = r2.getSwiped()
                boolean r11 = r11.booleanValue()
                if (r11 != 0) goto L_0x0231
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r11 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                r11.aY = r10
                r4.setVisibility(r8)
            L_0x0231:
                ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment r10 = ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.this
                java.lang.Boolean r11 = r2.getSwiped()
                boolean r11 = r11.booleanValue()
                if (r11 == 0) goto L_0x023f
                r5 = 0
                goto L_0x0242
            L_0x023f:
                int r1 = r1 * -1
                r5 = r1
            L_0x0242:
                java.lang.Boolean r11 = r2.getSwiped()
                boolean r11 = r11.booleanValue()
                r6 = r11 ^ 1
                r1 = r10
                r1.a(r2, r3, r4, r5, r6)
            L_0x0250:
                r9.a()
                goto L_0x0278
            L_0x0254:
                r9.a()
                r9.b(r10)
                float r10 = r11.getX()
                int r10 = (int) r10
                r9.f = r10
                float r10 = r11.getY()
                int r10 = (int) r10
                r9.g = r10
                android.view.ViewGroup$LayoutParams r10 = r3.getLayoutParams()
                android.widget.RelativeLayout$LayoutParams r10 = (android.widget.RelativeLayout.LayoutParams) r10
                int r10 = r10.rightMargin
                r9.e = r10
                int r10 = r9.e
                if (r10 <= 0) goto L_0x0278
                r9.e = r1
            L_0x0278:
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.SwipeListItemListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
        }
    }

    public SessionManager getSessionManager() {
        return null;
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

    public void showOnBoarding() {
    }

    public void onBackPressed() {
        if (this.mControlPager != null) {
            if (this.mControlPager.getDisplayedChild() == 0) {
                switchDrawer();
            }
            switch (this.mControlPager.getDisplayedChild()) {
                case 1:
                case 3:
                    this.aQ = true;
                    goToTransferencias();
                    return;
                case 2:
                    goToTransferencias();
                    return;
                case 4:
                case 5:
                    this.aQ = true;
                    gotoPage(2);
                    ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK);
                    ((SantanderRioMainActivity) getActivity()).lockMenu(true);
                    G();
                    return;
                case 6:
                    if (!this.ae.equals("S")) {
                        z();
                        return;
                    } else if (!this.bd) {
                        this.bd = true;
                        this.bf = L();
                        K();
                        this.bf.showAlert();
                        return;
                    } else {
                        z();
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public void onCreate(Bundle bundle) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(bundle);
        this.ag = new LimitesHorariosTransferenciasPresenterImp(this);
        this.ai = new AgendaDestinatariosPresenterImp(this);
        this.ah = new ConfirmacionTransferenciaPresenterImp(this, this.e);
        this.aj = new TerminosCondicionesPresenterImp(this);
        this.ak = new LegalesPresenterImp(this);
        this.al = new ComprobanteTransferenciaPresenterImp(this);
        showOnBoarding();
    }

    private void y() {
        this.b.cleanTransferenciasData();
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.a = layoutInflater.inflate(R.layout.transferencias_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, this.a);
        setTAG(FragmentConstants.TRANSFERENCIAS);
        this.aO = new CFiltersAccessibility(getActivity());
        b(this.a);
        return this.a;
    }

    public void onResume() {
        super.onResume();
        F();
    }

    private void b(View view) {
        CAccessibility cAccessibility = new CAccessibility(getActivity().getApplicationContext());
        y();
        H();
        resetDestinatarioSeleccionado();
        setSelectedCurrency("Pesos");
        this.selectorImporteRLL.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TransferenciasFragment.this.onSelectCurrency();
            }
        });
        ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewPlazoAcreditacion)).setText(Html.fromHtml(getValueConsDescription(this.aF, "TRANSF_CMB_TIPT", "INM")));
        this.i = getValueConsDescription(this.aF, "TRANSF_TR_CMB_CONCEP", "VAR");
        TextView textView = (TextView) this.pantallaTransferencias.findViewById(R.id.textViewConcepto);
        textView.setText(Html.fromHtml(this.i));
        try {
            textView.setContentDescription(cAccessibility.applyFilterGeneral(textView.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.bm = (LinearLayout) this.pantallaTransferencias.findViewById(R.id.linearLayoutLeyemda);
        this.bm.setVisibility(8);
        this.etReferenciaConcepto.setText("");
        this.etReferenciaConcepto.setHint(this.i);
        this.aD = new ArrayList<>();
        this.aD = getValuesConsDescription(this.aF, "TRANSF_TR_CMB_CONCEP");
        this.aq = (String) this.aD.get(0);
        this.etImporte.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                TransferenciasFragment.this.setContinueButtonState();
            }
        });
        this.aS = false;
        callConsultaDatosInicialesTransf();
        this.pantallaTransferencias.findViewById(R.id.textViewCuentaOrigen).requestFocus();
        this.pantallaTransferencias.findViewById(R.id.linearLayoutDe).setOnClickListener(this);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutPara).setOnClickListener(this);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutConcepto).setOnClickListener(this);
        this.pantallaTransferencias.findViewById(R.id.buttonContinue).setOnClickListener(this);
        this.pantallaTransferencias.findViewById(R.id.buttonContinue).setEnabled(false);
        activarMenuTransferencias();
        enableOptionMenuButton();
        enableMenuButton();
        this.d.trackScreen(getString(R.string.analytics_screen_name_transferencias_home));
    }

    public void callConsultaDatosInicialesTransf() {
        try {
            DatosIniciales datosIniciales = new DatosIniciales(this.b.getLoginUnico().getDatosPersonales().getNroDocumento(), this.b.getLoginUnico().getDatosPersonales().getFechaNacimiento());
            this.aP = ProgresIndicator.newInstance(VConsultaDatosInicialesTransf.nameService);
            showProgress();
            this.c.consultaDatosInicialesTransf(datosIniciales);
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
    }

    public void resetDestinatarioSeleccionado() {
        this.af = null;
        this.aF = this.b.getConsDescripciones();
        this.textViewSeleccionar.setText(getString(R.string.ID446_TRANSFERENCE_BTN_SELECT));
        B();
        enableContinueButton(Boolean.valueOf(false));
    }

    public void resetCuentaOrigenSeleccionada() {
        ArrayList filterOptionsDe = filterOptionsDe(this.ad);
        if (filterOptionsDe.size() > 1) {
            enableLinearLayoutDe(Boolean.valueOf(true));
            disableLinearLayoutDe(Boolean.valueOf(false));
            setCuentaOrigen((String) filterOptionsDe.get(0));
        } else if (filterOptionsDe.size() == 1) {
            setCuentaOrigen((String) filterOptionsDe.get(0));
            disableLinearLayoutDe(Boolean.valueOf(true));
        } else {
            enableLinearLayoutDe(Boolean.valueOf(false));
        }
    }

    public void enableLinearLayoutDe(Boolean bool) {
        if (bool.booleanValue()) {
            ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewCuentaOrigen)).setTextColor(getResources().getColor(R.color.generic_black));
        } else {
            ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewCuentaOrigen)).setText(getString(R.string.ID446_TRANSFERENCE_BTN_SELECT));
            ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewCuentaOrigen)).setTextColor(getResources().getColor(R.color.generic_black));
        }
        this.pantallaTransferencias.findViewById(R.id.linearLayoutDe).setEnabled(bool.booleanValue());
    }

    public void disableLinearLayoutDe(Boolean bool) {
        if (bool.booleanValue()) {
            ((LinearLayout) this.pantallaTransferencias.findViewById(R.id.linearLayoutDe)).setClickable(false);
            ((ImageView) this.pantallaTransferencias.findViewById(R.id.arrow_right)).setVisibility(8);
            return;
        }
        ((LinearLayout) this.pantallaTransferencias.findViewById(R.id.linearLayoutDe)).setClickable(true);
        ((ImageView) this.pantallaTransferencias.findViewById(R.id.arrow_right)).setVisibility(0);
    }

    public void setContinueButtonState() {
        if (this.af == null || TextUtils.isEmpty(this.etImporte.getText())) {
            enableContinueButton(Boolean.valueOf(false));
        } else {
            enableContinueButton(Boolean.valueOf(true));
        }
    }

    public void enableContinueButton(Boolean bool) {
        if (bool.booleanValue()) {
            this.pantallaTransferencias.findViewById(R.id.buttonContinue).setBackground(getResources().getDrawable(R.drawable.button_active));
        } else {
            this.pantallaTransferencias.findViewById(R.id.buttonContinue).setBackground(getResources().getDrawable(R.drawable.button_dissable));
        }
        this.pantallaTransferencias.findViewById(R.id.buttonContinue).setEnabled(bool.booleanValue());
    }

    private void z() {
        this.ae = "N";
        goToTransferencias();
        b(this.a);
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0232, code lost:
        if (r11.equals(ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.cBANCO_OB) == false) goto L_0x0249;
     */
    /* JADX WARNING: Removed duplicated region for block: B:122:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x024f  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x025d  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x026b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r11) {
        /*
            r10 = this;
            int r0 = r11.getId()
            r1 = 2
            r2 = 1
            switch(r0) {
                case 2131364219: goto L_0x071b;
                case 2131364223: goto L_0x0279;
                case 2131364229: goto L_0x01e1;
                case 2131364795: goto L_0x0104;
                case 2131365045: goto L_0x00ef;
                case 2131365049: goto L_0x00d6;
                case 2131365070: goto L_0x00bf;
                case 2131365278: goto L_0x008f;
                case 2131365721: goto L_0x0029;
                case 2131365964: goto L_0x0022;
                case 2131366002: goto L_0x001b;
                case 2131366059: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x071e
        L_0x000b:
            android.support.v4.app.FragmentActivity r11 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r11 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r11
            r11.hideKeyboard()
            r10.aQ = r2
            r10.goToTransferencias()
            goto L_0x071e
        L_0x001b:
            ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean r11 = r10.aI
            r10.goToTerminosCondiciones(r11)
            goto L_0x071e
        L_0x0022:
            ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean r11 = r10.aI
            r10.goToLegales(r11)
            goto L_0x071e
        L_0x0029:
            r11 = 2131756479(0x7f1005bf, float:1.9143867E38)
            java.lang.String r0 = r10.getString(r11)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r11 = r10.getString(r11)
            r1.append(r11)
            java.lang.String r11 = "-"
            r1.append(r11)
            android.view.View r11 = r10.pantallaComprobanteTransferencia
            r2 = 2131365987(0x7f0a1063, float:1.8351855E38)
            android.view.View r11 = r11.findViewById(r2)
            android.widget.TextView r11 = (android.widget.TextView) r11
            java.lang.CharSequence r11 = r11.getText()
            java.lang.String r11 = r11.toString()
            r1.append(r11)
            java.lang.String r11 = r1.toString()
            r10.showActionShareReceipt(r0, r11)
            android.view.View r11 = r10.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x0089 }
            android.view.View r11 = r11.findViewById(r2)     // Catch:{ Exception -> 0x0089 }
            android.support.v4.app.FragmentActivity r0 = r10.getActivity()     // Catch:{ Exception -> 0x0089 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x0089 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x0089 }
            android.view.View r1 = r10.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x0089 }
            android.view.View r1 = r1.findViewById(r2)     // Catch:{ Exception -> 0x0089 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x0089 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0089 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0089 }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r1)     // Catch:{ Exception -> 0x0089 }
            r11.setContentDescription(r0)     // Catch:{ Exception -> 0x0089 }
            goto L_0x071e
        L_0x0089:
            r11 = move-exception
            r11.printStackTrace()
            goto L_0x071e
        L_0x008f:
            r10.aQ = r2
            android.widget.ViewFlipper r11 = r10.mControlPager
            int r11 = r11.getDisplayedChild()
            if (r11 == r2) goto L_0x00ba
            switch(r11) {
                case 4: goto L_0x009e;
                case 5: goto L_0x009e;
                default: goto L_0x009c;
            }
        L_0x009c:
            goto L_0x071e
        L_0x009e:
            r10.gotoPage(r1)
            android.support.v4.app.FragmentActivity r11 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.base.BaseActivity r11 = (ar.com.santander.rio.mbanking.app.base.BaseActivity) r11
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r0 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.BACK
            r11.setActionBarType(r0)
            android.support.v4.app.FragmentActivity r11 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r11 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r11
            r11.lockMenu(r2)
            r10.G()
            goto L_0x071e
        L_0x00ba:
            r10.goToTransferencias()
            goto L_0x071e
        L_0x00bf:
            java.lang.String r11 = LOG_TAG
            java.lang.String r0 = "onClick Para"
            android.util.Log.d(r11, r0)
            android.support.v4.app.FragmentActivity r11 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r11 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r11
            r11.hideKeyboard()
            java.lang.String r11 = "SINGLE_CHOICE"
            r10.onGoToAgendaDestinatarios(r11)
            goto L_0x071e
        L_0x00d6:
            java.lang.String r11 = LOG_TAG
            java.lang.String r0 = "onClick De"
            android.util.Log.d(r11, r0)
            android.support.v4.app.FragmentActivity r11 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r11 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r11
            r11.hideKeyboard()
            ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean r11 = r10.aG
            if (r11 == 0) goto L_0x071e
            r10.onClickLinearLayoutDe()
            goto L_0x071e
        L_0x00ef:
            java.lang.String r11 = LOG_TAG
            java.lang.String r0 = "onClick Concepto"
            android.util.Log.d(r11, r0)
            android.support.v4.app.FragmentActivity r11 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r11 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r11
            r11.hideKeyboard()
            r10.onClickLinearLayoutConcepto()
            goto L_0x071e
        L_0x0104:
            boolean r0 = r10.bi
            if (r0 != 0) goto L_0x071e
            r10.bi = r2
            java.lang.String r0 = "N"
            r10.ae = r0
            android.support.v4.app.FragmentActivity r0 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.base.BaseActivity r0 = (ar.com.santander.rio.mbanking.app.base.BaseActivity) r0
            r0.hideKeyboard()
            r0 = 2131364795(0x7f0a0bbb, float:1.8349437E38)
            android.view.View r0 = r11.findViewById(r0)
            android.widget.ImageView r0 = (android.widget.ImageView) r0
            java.lang.Object r1 = r11.getTag()
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = (ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios) r1
            r10.af = r1
            ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter r1 = r10.am
            r1.updateAdapterChecked()
            ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter r1 = r10.am
            r1.updateCheckBox(r0, r2)
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = r10.af
            r1.setChecked(r2)
            r10.B()
            java.lang.String r1 = "Otros Bancos"
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r3 = r10.af
            java.lang.String r3 = r3.getInfo1()
            java.lang.String r3 = r3.toLowerCase()
            java.lang.String r4 = "propia"
            boolean r3 = r3.contains(r4)
            if (r3 == 0) goto L_0x0151
            java.lang.String r1 = "Propia"
            goto L_0x0165
        L_0x0151:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r3 = r10.af
            java.lang.String r3 = r3.getInfo1()
            java.lang.String r3 = r3.toLowerCase()
            java.lang.String r4 = "terceros"
            boolean r3 = r3.contains(r4)
            if (r3 == 0) goto L_0x0165
            java.lang.String r1 = "Terceros Santander"
        L_0x0165:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r3 = r10.af
            java.lang.String r3 = r3.getInfo2()
            android.support.v4.app.FragmentActivity r4 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            ar.com.santander.rio.mbanking.managers.session.SessionManager r4 = r4.sessionManager
            r4.setTipoCuenta(r1)
            android.support.v4.app.FragmentActivity r4 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            ar.com.santander.rio.mbanking.managers.session.SessionManager r4 = r4.sessionManager
            r4.setDestinatario(r3)
            r10.b(r3)
            android.support.v4.app.FragmentActivity r4 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            ar.com.santander.rio.mbanking.managers.session.SessionManager r4 = r4.sessionManager
            java.lang.String r4 = r4.getCuentaOrigenDestino()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01da
            java.lang.String r3 = "Propia"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x01da
            android.content.res.Resources r1 = r11.getResources()
            r2 = 2131231077(0x7f080165, float:1.8078225E38)
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r2)
            r0.setImageDrawable(r1)
            java.lang.String r2 = "Error"
            android.content.Context r11 = r11.getContext()
            android.content.res.Resources r11 = r11.getResources()
            r0 = 2131757127(0x7f100847, float:1.914518E38)
            java.lang.String r3 = r11.getString(r0)
            r4 = 0
            java.lang.String r5 = "Aceptar"
            r6 = 0
            r7 = 0
            r8 = 0
            ar.com.santander.rio.mbanking.components.IsbanDialogFragment r11 = ar.com.santander.rio.mbanking.components.IsbanDialogFragment.newInstance(r2, r3, r4, r5, r6, r7, r8)
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$4 r0 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$4
            r0.<init>()
            r11.setDialogListener(r0)
            android.support.v4.app.FragmentManager r0 = r10.getFragmentManager()
            java.lang.String r1 = "DialogError"
            r11.show(r0, r1)
            goto L_0x071e
        L_0x01da:
            r10.aQ = r2
            r10.comprobarDestinatarioSeleccionado()
            goto L_0x071e
        L_0x01e1:
            r11 = 2131757164(0x7f10086c, float:1.9145256E38)
            java.lang.String r3 = r10.getString(r11)
            r11 = 2131757162(0x7f10086a, float:1.9145252E38)
            java.lang.String r4 = r10.getString(r11)
            r5 = 0
            r6 = 0
            r11 = 2131756512(0x7f1005e0, float:1.9143934E38)
            java.lang.String r7 = r10.getString(r11)
            r11 = 2131756510(0x7f1005de, float:1.914393E38)
            java.lang.String r8 = r10.getString(r11)
            r9 = 0
            ar.com.santander.rio.mbanking.components.IsbanDialogFragment r11 = ar.com.santander.rio.mbanking.components.IsbanDialogFragment.newInstance(r3, r4, r5, r6, r7, r8, r9)
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$3 r0 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$3
            r0.<init>()
            r11.setDialogListener(r0)
            android.support.v4.app.FragmentManager r0 = r10.getFragmentManager()
            java.lang.String r3 = "DialogConfirm"
            r11.show(r0, r3)
            java.lang.String r11 = r10.aV
            r0 = -1
            int r3 = r11.hashCode()
            r4 = -1895936037(0xffffffff8efe4fdb, float:-6.2692732E-30)
            if (r3 == r4) goto L_0x023f
            r4 = -1888990333(0xffffffff8f684b83, float:-1.1453026E-29)
            if (r3 == r4) goto L_0x0235
            r2 = -1494459257(0xffffffffa6ec5c87, float:-1.6400869E-15)
            if (r3 == r2) goto L_0x022c
            goto L_0x0249
        L_0x022c:
            java.lang.String r2 = "Otros Bancos"
            boolean r11 = r11.equals(r2)
            if (r11 == 0) goto L_0x0249
            goto L_0x024a
        L_0x0235:
            java.lang.String r1 = "Terceros Santander"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x0249
            r1 = 1
            goto L_0x024a
        L_0x023f:
            java.lang.String r1 = "Propia"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x0249
            r1 = 0
            goto L_0x024a
        L_0x0249:
            r1 = -1
        L_0x024a:
            switch(r1) {
                case 0: goto L_0x026b;
                case 1: goto L_0x025d;
                case 2: goto L_0x024f;
                default: goto L_0x024d;
            }
        L_0x024d:
            goto L_0x071e
        L_0x024f:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r11 = r10.d
            r0 = 2131757899(0x7f100b4b, float:1.9146747E38)
            java.lang.String r0 = r10.getString(r0)
            r11.trackScreen(r0)
            goto L_0x071e
        L_0x025d:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r11 = r10.d
            r0 = 2131757900(0x7f100b4c, float:1.9146749E38)
            java.lang.String r0 = r10.getString(r0)
            r11.trackScreen(r0)
            goto L_0x071e
        L_0x026b:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r11 = r10.d
            r0 = 2131757898(0x7f100b4a, float:1.9146745E38)
            java.lang.String r0 = r10.getString(r0)
            r11.trackScreen(r0)
            goto L_0x071e
        L_0x0279:
            java.lang.String r11 = LOG_TAG
            java.lang.String r0 = "onClick Continuar"
            android.util.Log.d(r11, r0)
            android.support.v4.app.FragmentActivity r11 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r11 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r11
            r11.hideKeyboard()
            android.view.View r11 = r10.pantallaTransferencias
            r0 = 2131364652(0x7f0a0b2c, float:1.8349147E38)
            android.view.View r11 = r11.findViewById(r0)
            android.widget.EditText r11 = (android.widget.EditText) r11
            android.text.Editable r11 = r11.getText()
            java.lang.String r11 = r11.toString()
            java.lang.String r1 = "^.*[^a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ. ].*$"
            boolean r11 = r11.matches(r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r10.an = r1
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r1 = r10.etImporte
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "\\s+"
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replaceAll(r2, r3)
            java.lang.String r2 = ""
            boolean r1 = r1.equals(r2)
            r2 = 2131757128(0x7f100848, float:1.9145183E38)
            r3 = 0
            if (r1 != 0) goto L_0x0708
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r1 = r10.etImporte
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x02d9
            goto L_0x0708
        L_0x02d9:
            java.lang.String r1 = r10.f243ar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r4 = r10.aH
            java.lang.String r4 = r4.getIdMoneda()
            java.lang.String r5 = r10.ap
            int r1 = r10.importeValido(r1, r4, r5)
            if (r1 == 0) goto L_0x0364
            java.lang.String r11 = r10.f243ar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r0 = r10.aH
            java.lang.String r0 = r0.getIdMoneda()
            java.lang.String r1 = r10.ap
            int r11 = r10.importeValido(r11, r0, r1)
            if (r11 <= 0) goto L_0x0349
            java.lang.String r11 = "Error"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            android.content.res.Resources r1 = r10.getResources()
            r2 = 2131757129(0x7f100849, float:1.9145185E38)
            java.lang.String r1 = r1.getString(r2)
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r1 = r10.aH
            java.lang.String r1 = r1.getMoneda()
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getSimbolCurrencyFromString(r1)
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            ar.com.santander.rio.mbanking.app.commons.CAmountIU r1 = new ar.com.santander.rio.mbanking.app.commons.CAmountIU
            r1.<init>()
            java.lang.String r2 = r10.f243ar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r4 = r10.aH
            java.lang.String r4 = r4.getIdMoneda()
            java.lang.String r5 = r10.ap
            java.lang.String r2 = r10.getImporteMaximo(r2, r4, r5)
            java.util.Locale r4 = ar.com.santander.rio.mbanking.app.ui.Constants.LOCALE_DEFAULT_APP
            java.lang.String r1 = r1.getOutputUserFromString(r2, r4)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r10.customErrorDialog(r11, r0, r3)
            goto L_0x0356
        L_0x0349:
            java.lang.String r11 = "Error"
            android.content.res.Resources r0 = r10.getResources()
            java.lang.String r0 = r0.getString(r2)
            r10.customErrorDialog(r11, r0, r3)
        L_0x0356:
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r11 = r10.etImporte
            r11.requestFocus()
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r11 = r10.etImporte
            java.lang.String r0 = ""
            r11.setText(r0)
            goto L_0x071e
        L_0x0364:
            android.view.View r1 = r10.pantallaTransferencias
            android.view.View r1 = r1.findViewById(r0)
            android.widget.EditText r1 = (android.widget.EditText) r1
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = ""
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x03af
            android.view.View r1 = r10.pantallaTransferencias
            android.view.View r1 = r1.findViewById(r0)
            android.widget.EditText r1 = (android.widget.EditText) r1
            java.lang.CharSequence r1 = r1.getHint()
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = ""
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x03af
            java.lang.String r11 = "Error"
            android.content.res.Resources r1 = r10.getResources()
            r2 = 2131757130(0x7f10084a, float:1.9145187E38)
            java.lang.String r1 = r1.getString(r2)
            r10.customErrorDialog(r11, r1, r3)
            android.view.View r11 = r10.pantallaTransferencias
            android.view.View r11 = r11.findViewById(r0)
            r11.requestFocus()
            goto L_0x071e
        L_0x03af:
            if (r11 == 0) goto L_0x03cc
            java.lang.String r11 = "Error"
            android.content.res.Resources r1 = r10.getResources()
            r2 = 2131757131(0x7f10084b, float:1.914519E38)
            java.lang.String r1 = r1.getString(r2)
            r10.customErrorDialog(r11, r1, r3)
            android.view.View r11 = r10.pantallaTransferencias
            android.view.View r11 = r11.findViewById(r0)
            r11.requestFocus()
            goto L_0x071e
        L_0x03cc:
            java.lang.String r11 = r10.ap
            java.lang.String r1 = "Propia"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x03e9
            boolean r11 = r10.A()
            if (r11 == 0) goto L_0x03e9
            android.support.v4.app.FragmentActivity r11 = r10.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r11 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r11
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.COMPRAVENTA_DOLARES
            r11.goToOption(r0)
            goto L_0x071e
        L_0x03e9:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r11 = r10.af
            java.lang.String r11 = r11.getTipoDestino()
            java.lang.String r1 = "03"
            boolean r11 = r11.equals(r1)
            if (r11 != 0) goto L_0x0405
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r11 = r10.af
            java.lang.String r11 = r11.getTipoDestino()
            java.lang.String r1 = "05"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x0418
        L_0x0405:
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r11 = r10.aN
            java.lang.String r11 = r10.a(r11)
            java.lang.String r1 = r10.ad
            boolean r11 = r11.equals(r1)
            if (r11 != 0) goto L_0x0418
            r10.C()
            goto L_0x071e
        L_0x0418:
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "selectedTipoCuenta "
            r1.append(r2)
            java.lang.String r2 = r10.ap
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r11, r1)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            java.lang.String r1 = r10.ap
            r11.add(r1)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "textViewFechaEjecucion "
            r1.append(r2)
            android.view.View r2 = r10.pantallaTransferencias
            r3 = 2131365933(0x7f0a102d, float:1.8351745E38)
            android.view.View r2 = r2.findViewById(r3)
            android.widget.TextView r2 = (android.widget.TextView) r2
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r11, r1)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            android.view.View r1 = r10.pantallaTransferencias
            android.view.View r1 = r1.findViewById(r3)
            android.widget.TextView r1 = (android.widget.TextView) r1
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            r11.add(r1)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "sSignoMoneda "
            r1.append(r2)
            java.lang.String r2 = r10.as
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r11, r1)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            java.lang.String r1 = r10.as
            r11.add(r1)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "editTextImporte "
            r1.append(r2)
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r2 = r10.etImporte
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r11, r1)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r1 = r10.etImporte
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            r11.add(r1)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "textViewPlazoAcreditacion "
            r1.append(r2)
            android.view.View r2 = r10.pantallaTransferencias
            r3 = 2131365996(0x7f0a106c, float:1.8351873E38)
            android.view.View r2 = r2.findViewById(r3)
            android.widget.TextView r2 = (android.widget.TextView) r2
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r11, r1)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            android.view.View r1 = r10.pantallaTransferencias
            android.view.View r1 = r1.findViewById(r3)
            android.widget.TextView r1 = (android.widget.TextView) r1
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            r11.add(r1)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "textViewConcepto "
            r1.append(r2)
            android.view.View r2 = r10.pantallaTransferencias
            r3 = 2131365901(0x7f0a100d, float:1.835168E38)
            android.view.View r2 = r2.findViewById(r3)
            android.widget.TextView r2 = (android.widget.TextView) r2
            java.lang.CharSequence r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r11, r1)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            android.view.View r1 = r10.pantallaTransferencias
            android.view.View r1 = r1.findViewById(r3)
            android.widget.TextView r1 = (android.widget.TextView) r1
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            r11.add(r1)
            android.view.View r11 = r10.pantallaTransferencias
            android.view.View r11 = r11.findViewById(r0)
            android.widget.EditText r11 = (android.widget.EditText) r11
            android.text.Editable r0 = r11.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = ""
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x056c
            java.lang.CharSequence r11 = r11.getHint()
            java.lang.String r11 = r11.toString()
            goto L_0x0574
        L_0x056c:
            android.text.Editable r11 = r11.getText()
            java.lang.String r11 = r11.toString()
        L_0x0574:
            java.util.ArrayList<java.lang.String> r0 = r10.an
            r0.add(r11)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "textViewTitular "
            r0.append(r1)
            android.view.View r1 = r10.pantallaTransferencias
            r2 = 2131366009(0x7f0a1079, float:1.83519E38)
            android.view.View r1 = r1.findViewById(r2)
            android.widget.TextView r1 = (android.widget.TextView) r1
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r11, r0)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            android.view.View r0 = r10.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r2)
            android.widget.TextView r0 = (android.widget.TextView) r0
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            r11.add(r0)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sBancoDestino "
            r0.append(r1)
            java.lang.String r1 = r10.at
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r11, r0)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            java.lang.String r0 = r10.at
            r11.add(r0)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sNumeroCUIT "
            r0.append(r1)
            java.lang.String r1 = r10.au
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r11, r0)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            java.lang.String r0 = r10.au
            r11.add(r0)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sTipoCtaToBane "
            r0.append(r1)
            java.lang.String r1 = r10.av
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r11, r0)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            java.lang.String r0 = r10.av
            r11.add(r0)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sTipoCtaFromBane "
            r0.append(r1)
            java.lang.String r1 = r10.aw
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r11, r0)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            java.lang.String r0 = r10.aw
            r11.add(r0)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sBancoReceptor "
            r0.append(r1)
            java.lang.String r1 = r10.ax
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r11, r0)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            java.lang.String r0 = r10.ax
            r11.add(r0)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sFiid "
            r0.append(r1)
            java.lang.String r1 = r10.ay
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r11, r0)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            java.lang.String r0 = r10.ay
            r11.add(r0)
            java.lang.String r11 = r10.getTAG()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sUser "
            r0.append(r1)
            java.lang.String r1 = r10.az
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r11, r0)
            java.util.ArrayList<java.lang.String> r11 = r10.an
            java.lang.String r0 = r10.az
            r11.add(r0)
            android.view.View r11 = r10.pantallaTransferencias
            r0 = 2131365889(0x7f0a1001, float:1.8351656E38)
            android.view.View r11 = r11.findViewById(r0)
            android.widget.TextView r11 = (android.widget.TextView) r11
            java.lang.CharSequence r11 = r11.getText()
            java.lang.String r11 = r11.toString()
            r10.aC = r11
            android.view.View r11 = r10.pantallaTransferencias     // Catch:{ Exception -> 0x06df }
            android.view.View r11 = r11.findViewById(r0)     // Catch:{ Exception -> 0x06df }
            android.support.v4.app.FragmentActivity r1 = r10.getActivity()     // Catch:{ Exception -> 0x06df }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x06df }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x06df }
            android.view.View r2 = r10.pantallaTransferencias     // Catch:{ Exception -> 0x06df }
            android.view.View r0 = r2.findViewById(r0)     // Catch:{ Exception -> 0x06df }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x06df }
            java.lang.CharSequence r0 = r0.getText()     // Catch:{ Exception -> 0x06df }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x06df }
            java.lang.String r0 = r1.applyFilterCharacterToCharacter(r0)     // Catch:{ Exception -> 0x06df }
            r11.setContentDescription(r0)     // Catch:{ Exception -> 0x06df }
            goto L_0x06e3
        L_0x06df:
            r11 = move-exception
            r11.printStackTrace()
        L_0x06e3:
            java.lang.String r11 = r10.au
            r10.aA = r11
            java.lang.String r11 = r10.at
            r10.aB = r11
            java.util.ArrayList<java.lang.String> r1 = r10.an
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r2 = r10.aH
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r3 = r10.aM
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r4 = r10.aK
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r5 = r10.aL
            ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean r6 = r10.aI
            r0 = r10
            r0.goToConfirmacionTransferencia(r1, r2, r3, r4, r5, r6)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r11 = r10.d
            r0 = 2131757823(0x7f100aff, float:1.9146593E38)
            java.lang.String r0 = r10.getString(r0)
            r11.trackScreen(r0)
            goto L_0x071e
        L_0x0708:
            java.lang.String r11 = "Error"
            android.content.res.Resources r0 = r10.getResources()
            java.lang.String r0 = r0.getString(r2)
            r10.customErrorDialog(r11, r0, r3)
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r11 = r10.etImporte
            r11.requestFocus()
            goto L_0x071e
        L_0x071b:
            r10.onBackPressed()
        L_0x071e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.onClick(android.view.View):void");
    }

    private boolean A() {
        String charSequence = ((TextView) getActivity().findViewById(R.id.textViewCuentaOrigen)).getText().toString();
        return (!charSequence.contains(Constants.SYMBOL_CURRENCY_DOLAR) && this.af.getInfo2().contains(Constants.SYMBOL_CURRENCY_DOLAR)) || (charSequence.contains(Constants.SYMBOL_CURRENCY_DOLAR) && !this.af.getInfo2().contains(Constants.SYMBOL_CURRENCY_DOLAR));
    }

    private void b(String str) {
        this.d.trackScreen(getString(R.string.analytics_screen_name_cambio_moneda));
    }

    public void onClickLinearLayoutDe() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("Seleccionar Cuenta", null, filterOptionsDe(this.ad), PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, this.ao);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                TransferenciasFragment.this.setCuentaOrigen(str);
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), "Dialog");
    }

    private void B() {
        this.linearLayoutTitularDivider.setVisibility(8);
        this.linearLayoutTitular.setVisibility(8);
        this.linearLayoutAlias.setVisibility(8);
        this.linearLayoutTipoCuenta.setVisibility(8);
        this.linearLayoutTipo.setVisibility(8);
        this.linearLayoutNumero.setVisibility(8);
        this.linearLayoutCUIT.setVisibility(8);
        this.linearLayoutBancoDestino.setVisibility(8);
        this.linearLayoutCBU.setVisibility(8);
    }

    public void setCuentaOrigen(String str) {
        CAccessibility cAccessibility = new CAccessibility(getActivity().getApplicationContext());
        TextView textView = (TextView) this.pantallaTransferencias.findViewById(R.id.textViewCuentaOrigen);
        this.ao = str;
        textView.setText(formatMedioPago(this.ao));
        try {
            textView.setContentDescription(cAccessibility.applyFilterGeneral(this.ao));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.b.setCuentaOrigenDebito(this.ao);
        String replace = this.ao.replace("\n", UtilsCuentas.SEPARAOR2);
        for (int i2 = 0; i2 < this.aG.getListDatosCuentasBean().size(); i2++) {
            if (((DatosCuentasBean) this.aG.getListDatosCuentasBean().get(i2)).getDescCtaDebito().equals(replace)) {
                this.aH = (DatosCuentasBean) this.aG.getListDatosCuentasBean().get(i2);
                this.b.setCuentaOrigenDestino(((DatosCuentasBean) this.aG.getListDatosCuentasBean().get(i2)).getDescCtaDestino());
            }
        }
    }

    public void onGoToAgendaDestinatarios(String str) {
        if (this.aG == null && this.aJ == null) {
            errorEmptyPara();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getLoginUnico().getDatosPersonales().getNombre());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.b.getLoginUnico().getDatosPersonales().getApellido().toUpperCase());
        String sb2 = sb.toString();
        this.aQ = true;
        goToAgendaDestinatarios(sb2, this.b.getCuentaOrigenDestino(), this.aG, this.aJ, str);
    }

    public void onClickLinearLayoutConcepto() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("Seleccionar Concepto", null, this.aD, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, this.aq);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                TransferenciasFragment.this.aq = str;
                ((TextView) TransferenciasFragment.this.pantallaTransferencias.findViewById(R.id.textViewConcepto)).setText(TransferenciasFragment.this.aq);
                TransferenciasFragment.this.bm.setVisibility(8);
                for (ListTableBean listGroupBeans : TransferenciasFragment.this.aF.listTableBeans) {
                    for (ListGroupBean listGroupBean : listGroupBeans.getListGroupBeans()) {
                        if (listGroupBean.getDescription().equalsIgnoreCase(str)) {
                            for (LeyendaBean leyendaBean : TransferenciasFragment.this.aW.getLeyendasBean().getLeyendasBean()) {
                                if (leyendaBean.getIdLeyenda().equals(listGroupBean.code)) {
                                    TransferenciasFragment.this.bm.setVisibility(0);
                                    TransferenciasFragment.this.h = (TextView) TransferenciasFragment.this.pantallaTransferencias.findViewById(R.id.leyenda);
                                    TransferenciasFragment.this.h.setText(Html.fromHtml(leyendaBean.getDescripcion()));
                                }
                            }
                        }
                    }
                }
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), "Dialog");
    }

    public void errorEmptyPara() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(ResultValues.ERROR, "Vacío", null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null);
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
        newInstance.show(getFragmentManager(), "DialogNewVersion");
    }

    public String getValueConsDescription(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean, String str, String str2) {
        if (consDescriptionBodyResponseBean != null) {
            List<ListTableBean> listTableBeans = consDescriptionBodyResponseBean.getListTableBeans();
            if (listTableBeans != null) {
                for (ListTableBean listTableBean : listTableBeans) {
                    if (listTableBean.idTable.equals(str)) {
                        List<ListGroupBean> list = listTableBean.listGroupBeans;
                        if (list != null) {
                            for (ListGroupBean listGroupBean : list) {
                                if (listGroupBean.code.equals(str2)) {
                                    return listGroupBean.getLabel();
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        return "";
    }

    public ArrayList<String> getValuesConsDescription(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean, String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (consDescriptionBodyResponseBean != null) {
            List<ListTableBean> listTableBeans = consDescriptionBodyResponseBean.getListTableBeans();
            if (listTableBeans != null) {
                for (ListTableBean listTableBean : listTableBeans) {
                    if (listTableBean.idTable.equals(str)) {
                        List<ListGroupBean> list = listTableBean.listGroupBeans;
                        if (list != null) {
                            for (ListGroupBean label : list) {
                                arrayList.add(label.getLabel());
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public String getImporteMaximo(String str, String str2, String str3) {
        String str4;
        String str5 = "0";
        try {
            if (str3.equals(TransferenciasConstants.cBANCO_PROPIA)) {
                if (str.equals("01") && str2.equals("0")) {
                    str4 = this.aG.getImpMaxP();
                    return str4;
                } else if (str.equals("01") && str2.equals("1")) {
                    str4 = this.aG.getImpMaxD();
                    return str4;
                }
            } else if (str3.equals(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
                if ((str.equals("02") || str.equals("04")) && str2.equals("0")) {
                    str4 = ((AgDestBSRBean) this.aJ.getListAgDestBSRBean().get(0)).getImpMaxP();
                    return str4;
                } else if ((str.equals("02") || str.equals("04")) && str2.equals("1")) {
                    str4 = ((AgDestBSRBean) this.aJ.getListAgDestBSRBean().get(0)).getImpMaxD();
                    return str4;
                }
            } else if (str3.equals(TransferenciasConstants.cBANCO_OB)) {
                if ((str.equals("03") || str.equals("05")) && str2.equals("0")) {
                    str4 = ((AgDestOBBean) this.aJ.getListAgDestOBBean().get(0)).getImpMaxP();
                    return str4;
                } else if ((str.equals("03") || str.equals("05")) && str2.equals("1")) {
                    str4 = ((AgDestOBBean) this.aJ.getListAgDestOBBean().get(0)).getImpMaxD();
                    return str4;
                }
            }
            str4 = str5;
            return str4;
        } catch (Exception unused) {
            return str5;
        }
    }

    public int importeValido(String str, String str2, String str3) {
        try {
            return CAmountIU.getInstance().getDoubleFromInputUser(this.etImporte.getText().toString()).doubleValue() <= 0.0d ? -1 : 0;
        } catch (Exception unused) {
            return 1;
        }
    }

    public DatosCuentasBean getDatosCuentasBeanPara(String str) {
        for (int i2 = 0; i2 < this.aG.getListDatosCuentasBean().size(); i2++) {
            if (((DatosCuentasBean) this.aG.getListDatosCuentasBean().get(i2)).getDescCtaDestino().equals(str)) {
                return (DatosCuentasBean) this.aG.getListDatosCuentasBean().get(i2);
            }
        }
        return null;
    }

    public DatosCuentasDestBSRBean getDatosCuentasDestBSRBeanPara(String str) {
        for (int i2 = 0; i2 < ((AgDestBSRBean) this.aJ.getListAgDestBSRBean().get(0)).getListDatosCuentasDestBSRBean().size(); i2++) {
            if (((DatosCuentasDestBSRBean) ((AgDestBSRBean) this.aJ.getListAgDestBSRBean().get(0)).getListDatosCuentasDestBSRBean().get(i2)).getDescCtaDestinoBSR().equals(str)) {
                return (DatosCuentasDestBSRBean) ((AgDestBSRBean) this.aJ.getListAgDestBSRBean().get(0)).getListDatosCuentasDestBSRBean().get(i2);
            }
        }
        return null;
    }

    public DatosCuentasDestOBBean getDatosCuentasDestOBBeanPara(String str) {
        if (this.aJ.getListAgDestOBBean().size() <= 0) {
            return null;
        }
        for (int i2 = 0; i2 < ((AgDestOBBean) this.aJ.getListAgDestOBBean().get(0)).getListDatosCuentasDestOBBean().size(); i2++) {
            if (((DatosCuentasDestOBBean) ((AgDestOBBean) this.aJ.getListAgDestOBBean().get(0)).getListDatosCuentasDestOBBean().get(i2)).getCbu().equals(str)) {
                return (DatosCuentasDestOBBean) ((AgDestOBBean) this.aJ.getListAgDestOBBean().get(0)).getListDatosCuentasDestOBBean().get(i2);
            }
        }
        return null;
    }

    public void comprobarDestinatarioSeleccionado() {
        String name = TransferenciasFragment.class.getName();
        StringBuilder sb = new StringBuilder();
        sb.append("comprobarDestinatarioSeleccionado ");
        sb.append(this.b.getDestinatario());
        Log.d(name, sb.toString());
        if (!this.b.getTipoCuenta().equals("") && !this.b.getDestinatario().equals("")) {
            String tipoCuenta = this.b.getTipoCuenta();
            String destinatario = this.b.getDestinatario();
            TypedValue.applyDimension(1, (float) ((int) (getResources().getDimension(R.dimen.general_margin) / getResources().getDisplayMetrics().density)), getResources().getDisplayMetrics());
            TypedValue.applyDimension(1, (float) ((int) (getResources().getDimension(R.dimen.padding_data_row) / getResources().getDisplayMetrics().density)), getResources().getDisplayMetrics());
            this.ap = tipoCuenta;
            if (this.ap.equals(TransferenciasConstants.cBANCO_PROPIA)) {
                this.d.trackScreen(getString(R.string.analytics_screen_name_transferencias_cuentas_propias));
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTitularDivider).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTitular).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTipoCuenta).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTipo).setVisibility(8);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutNumero).setVisibility(8);
                TextView textView = (TextView) this.pantallaTransferencias.findViewById(R.id.textViewTitular);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.b.getLoginUnico().getDatosPersonales().getNombre());
                sb2.append(UtilsCuentas.SEPARAOR2);
                sb2.append(this.b.getLoginUnico().getDatosPersonales().getApellido().toUpperCase());
                textView.setText(Html.fromHtml(sb2.toString()));
                if (this.af.getAlias() != null && !this.af.getAlias().isEmpty()) {
                    ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewAlias)).setText(this.af.getAlias());
                }
                ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewTipoCuenta)).setText(Html.fromHtml(this.ap));
                this.textViewSeleccionar.setText(Html.fromHtml(destinatario));
                ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewPlazoAcreditacion)).setText(getString(R.string.IDXX_TRANSFERENCE_LBL_PLAZO_ACREDITACION_ACTO));
                this.pantallaTransferencias.findViewById(R.id.linearLayoutCUIT).setVisibility(8);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutBancoDestino).setVisibility(8);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutCBU).setVisibility(8);
                this.aM = getDatosCuentasBeanPara(destinatario);
                this.f243ar = this.aM.getTipoDestino();
                this.bi = false;
                goToTransferencias();
            } else if (this.ap.equals(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
                this.d.trackScreen(getString(R.string.analytics_screen_name_transferencias_cuentas_terceros));
                this.aK = getDatosCuentasDestBSRBeanPara(destinatario);
                this.f243ar = this.aK.getTipoDestino();
                VerificaDatosBean verificaDatosBean = new VerificaDatosBean(this.aK.getTipoDestino(), this.aK.getTipo(), this.aK.getSucursal(), this.aK.getNumero(), this.b.getLoginUnico().getDatosPersonales().getNroDocumento(), this.b.getLoginUnico().getDatosPersonales().getFechaNacimiento(), this.aK.getAlias());
                this.aP = ProgresIndicator.newInstance(VVerificaDatosInicialesTransf.nameService);
                showProgress();
                this.bb = accionVerificarDatosTransf.SELECCION;
                this.c.verificaDatosInicialesTransf(verificaDatosBean);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTitularDivider).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTitular).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTipoCuenta).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTipo).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutNumero).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutCUIT).setVisibility(8);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutBancoDestino).setVisibility(8);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutCBU).setVisibility(8);
                if (this.aK.getAlias() == null || this.aK.getAlias().isEmpty()) {
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutAlias).setVisibility(8);
                } else {
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutAlias).setVisibility(0);
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutTipoCuenta).setVisibility(8);
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutBancoDestino).setVisibility(0);
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutCBU).setVisibility(0);
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutCUIT).setVisibility(0);
                }
            } else {
                this.d.trackScreen(getString(R.string.analytics_screen_name_transferencias_cuentas_otros_bancos));
                this.aL = getDatosCuentasDestOBBeanPara(destinatario);
                this.f243ar = this.aL.getTipoDestino();
                VerificaDatosOBBean verificaDatosOBBean = new VerificaDatosOBBean(this.aL.getTipoDestino(), this.aH.getTipo(), this.aH.getSucursal(), this.aH.getNumero(), this.b.getLoginUnico().getDatosPersonales().getNroDocumento(), this.b.getLoginUnico().getDatosPersonales().getFechaNacimiento(), (this.aL.getAlias() == null || String.valueOf(this.aL).isEmpty()) ? this.aL.getCbu() : null, this.aL.getAlias());
                this.aP = ProgresIndicator.newInstance(VVerificaDatosInicialesTransf.nameService);
                showProgress();
                this.bb = accionVerificarDatosTransf.SELECCION;
                this.c.verificaDatosInicialesTransfOB(verificaDatosOBBean);
                if (this.aL.getAlias() == null || this.aL.getAlias().isEmpty()) {
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutAlias).setVisibility(8);
                } else {
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutAlias).setVisibility(0);
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutTipo).setVisibility(0);
                    this.pantallaTransferencias.findViewById(R.id.linearLayoutNumero).setVisibility(0);
                }
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTitularDivider).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTitular).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutCUIT).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutBancoDestino).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutCBU).setVisibility(0);
            }
            enableContinueButton(Boolean.valueOf(true));
        }
    }

    public void goToTransferencias() {
        if (this.mControlPager.getDisplayedChild() != 0) {
            gotoPage(0);
            ((SantanderRioMainActivity) getActivity()).hideKeyboard();
            activarMenuTransferencias();
            enableMenuButton();
            enableOptionMenuButton();
            this.bd = false;
        }
    }

    public void mostrarInfoDestinatario() {
        if (this.af.getTipoDestino().equals("02") || this.af.getTipoDestino().equals("04")) {
            if (this.aK.getAlias() == null || this.aK.getAlias().isEmpty()) {
                this.pantallaTransferencias.findViewById(R.id.linearLayoutAlias).setVisibility(8);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTitularDivider).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTitular).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTipoCuenta).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTipo).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutNumero).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutCUIT).setVisibility(8);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutBancoDestino).setVisibility(8);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutCBU).setVisibility(8);
                return;
            }
            this.pantallaTransferencias.findViewById(R.id.linearLayoutAlias).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutTitularDivider).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutTitular).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutCUIT).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutBancoDestino).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutTipo).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutNumero).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutCBU).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutTipoCuenta).setVisibility(8);
        } else if (this.af.getTipoDestino().equals("03") || this.af.getTipoDestino().equals("05")) {
            if (this.aL.getAlias() == null || this.aL.getAlias().isEmpty()) {
                this.pantallaTransferencias.findViewById(R.id.linearLayoutAlias).setVisibility(8);
            } else {
                this.pantallaTransferencias.findViewById(R.id.linearLayoutAlias).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutTipo).setVisibility(0);
                this.pantallaTransferencias.findViewById(R.id.linearLayoutNumero).setVisibility(0);
            }
            this.pantallaTransferencias.findViewById(R.id.linearLayoutTitularDivider).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutTitular).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutCUIT).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutBancoDestino).setVisibility(0);
            this.pantallaTransferencias.findViewById(R.id.linearLayoutCBU).setVisibility(0);
        }
    }

    public void activarMenuTransferencias() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_MENU);
        ((SantanderRioMainActivity) getActivity()).lockMenu(false);
    }

    public void enableMenuButton() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            ((ImageView) customView.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (TransferenciasFragment.this.mControlPager.getDisplayedChild() != 6 || TransferenciasFragment.this.bd) {
                        TransferenciasFragment.this.switchDrawer();
                        return;
                    }
                    TransferenciasFragment.this.bf = TransferenciasFragment.this.L();
                    TransferenciasFragment.this.bf.showAlert();
                }
            });
        }
    }

    public void goToNuevoDestinatarioActivity(String str) {
        if (!this.e.isSoftTokenAvailable().booleanValue()) {
            showNuevoDestUnavailableTokenError();
            return;
        }
        Intent intent = new Intent(getActivity(), NuevoDestinatarioTransferenciaActivity.class);
        intent.putExtra(TransferenciasConstants.INTENT_CUENTA_SELECCIONADA, this.aH);
        intent.putExtra("ORIGEN", str);
        startActivityForResult(intent, 888);
    }

    public void goToNuevoDestinatarioActivityCtaMigrada(String str) {
        if (!this.e.isSoftTokenAvailable().booleanValue()) {
            showNuevoDestUnavailableTokenError();
            return;
        }
        Intent intent = new Intent(getActivity(), NuevoDestinatarioTransferenciaActivity.class);
        intent.putExtra(TransferenciasConstants.INTENT_CUENTA_SELECCIONADA, this.aH);
        intent.putExtra("ORIGEN", str);
        intent.putExtra(TransferenciasConstants.INTENT_CUENTA_SELECCIONADA_MIGRADA, this.bk);
        startActivityForResult(intent, 888);
    }

    public void showNuevoDestUnavailableTokenError() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getResources().getString(R.string.USER00099_TRANSFERENCIAS_ACTIVAR_TOKEN), getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), getResources().getString(R.string.USER200008_BTN));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                newInstance.dismiss();
            }

            public void onNegativeButton() {
                TransferenciasFragment.this.launchTokenInfoActivity();
            }
        });
        newInstance.show(getFragmentManager(), "TokenErrorDialog");
        this.d.trackScreen(getString(R.string.analytics_screen_name_token_nuevo_destinatario));
    }

    public void showChangedAliasError(String str, final VerificaDatosSalidaOBBean verificaDatosSalidaOBBean) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), getResources().getString(R.string.IDX_ALERT_BTN_CANCEL));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                TransferenciasFragment.this.bc = TransferenciasFragment.this.af;
                TransferenciasFragment.this.goToEditarDestinatarios(TransferenciasFragment.this.bc, Boolean.valueOf(true), verificaDatosSalidaOBBean);
                TransferenciasFragment.this.J();
            }

            public void onNegativeButton() {
                TransferenciasFragment.this.J();
            }
        });
        newInstance.show(getFragmentManager(), "aliasPopUp");
    }

    public void launchTokenInfoActivity() {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG_TIT));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG));
        this.d.trackScreen(getString(R.string.analytics_softtoken_ayuda_acerca_token_virtual));
        startActivity(intent);
    }

    private void C() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpCurrencyError", getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER0000XX_Transferencias_errorDestinatarioDistintaMoneda), getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT));
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
        newInstance.show(getFragmentManager(), "popUpCurrencyError");
    }

    public void enableOptionMenuButton() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            ImageView imageView = (ImageView) customView.findViewById(R.id.menu);
            imageView.setContentDescription(getString(R.string.IDXX_CONTENT_DESCRIPTION_BTN_OPTIONS));
            imageView.setVisibility(0);
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ((SantanderRioMainActivity) TransferenciasFragment.this.getActivity()).hideKeyboard();
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(TransferenciasFragment.this.getString(R.string.ID442_TRANSFERENCE_LBL_AGENDA_DE_DESTINATARIOS));
                    arrayList.add(TransferenciasFragment.this.getString(R.string.ID442_TRANSFERENCE_LBL_NUEVO_DESTINATARIO));
                    arrayList.add(TransferenciasFragment.this.getString(R.string.F09_00_A_Btn_Solicitar_Aumento_de_Limite));
                    arrayList.add(TransferenciasFragment.this.getString(R.string.ID443_TRANSFERENCE_LBL_TIMETABLE));
                    final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, null, arrayList, TransferenciasFragment.this.getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, null, arrayList);
                    newInstance.setDialogListener(new IDialogListener() {
                        public void onNegativeButton() {
                        }

                        public void onPositiveButton() {
                        }

                        public void onItemSelected(String str) {
                            if (str.equalsIgnoreCase(TransferenciasFragment.this.getString(R.string.ID443_TRANSFERENCE_LBL_TIMETABLE))) {
                                TransferenciasFragment.this.goToLimitesHorariosTransferencias(TransferenciasFragment.this.aI);
                            } else if (str.equalsIgnoreCase(TransferenciasFragment.this.getString(R.string.ID442_TRANSFERENCE_LBL_NUEVO_DESTINATARIO))) {
                                TransferenciasFragment.this.goToNuevoDestinatarioActivity(Origen.ORIGEN_NUEVO);
                            } else if (str.equalsIgnoreCase(TransferenciasFragment.this.getString(R.string.ID442_TRANSFERENCE_LBL_AGENDA_DE_DESTINATARIOS))) {
                                TransferenciasFragment.this.onGoToAgendaDestinatarios("ABM_ONLY");
                            } else if (str.equalsIgnoreCase(TransferenciasFragment.this.getString(R.string.F09_00_A_Btn_Solicitar_Aumento_de_Limite))) {
                                TransferenciasFragment.this.goToSolicitarAumentoLimite();
                            }
                        }

                        public void onSimpleActionButton() {
                            newInstance.dismiss();
                        }
                    });
                    newInstance.show(TransferenciasFragment.this.getFragmentManager(), "DialogConfirm");
                    TransferenciasFragment.this.d.trackScreen(TransferenciasFragment.this.getString(R.string.analytics_screen_name_opciones_de_transferencias));
                }
            });
        }
    }

    public void goToSolicitarAumentoLimite() {
        Intent intent = new Intent(getActivity(), SolicitudAumentoActivity.class);
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_SOLICITUD_AUMENTO, this.aI);
        intent.putExtra(TransferenciasConstants.INTENT_CUENTA_SELECCIONADA, this.aH);
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_CUENTAS_PROPIAS, this.aG);
        startActivityForResult(intent, 555);
    }

    public void goToLimitesHorariosTransferencias(LeyendasBean leyendasBean) {
        this.d.trackScreen(getString(R.string.analytics_screen_name_limites_horarios));
        for (int i2 = 0; i2 < leyendasBean.getLeyendasBean().size(); i2++) {
            if (((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getIdLeyenda().equals("TRANSF_LYH")) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, ((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getDescripcion());
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, ((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getTitulo());
                startActivity(intent);
            }
        }
    }

    public void goToConfirmacionTransferencia(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean, LeyendasBean leyendasBean) {
        this.aR = true;
        gotoPage(2);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        G();
        this.ah.onCreatePage(arrayList, datosCuentasBean, datosCuentasBean2, datosCuentasDestBSRBean, datosCuentasDestOBBean, leyendasBean, getActivity());
    }

    public void setConfirmacionTransferenciaView(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean, LeyendasBean leyendasBean) {
        String str;
        ArrayList<String> arrayList2 = arrayList;
        this.aO = new CFiltersAccessibility(getActivity());
        LinearLayout linearLayout = (LinearLayout) this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutLeyemda);
        linearLayout.setVisibility(8);
        if (this.bm.getVisibility() == 0) {
            linearLayout.setVisibility(0);
            ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.leyenda)).setText(this.h.getText());
        }
        LinearLayout linearLayout2 = (LinearLayout) this.pantallaComprobanteTransferencia.findViewById(R.id.linearLayoutLeyemda);
        linearLayout2.setVisibility(8);
        if (this.bm.getVisibility() == 0) {
            linearLayout2.setVisibility(0);
            ((TextView) this.pantallaComprobanteTransferencia.findViewById(R.id.leyenda)).setText(this.h.getText());
        }
        this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewTerminosCondiciones).setOnClickListener(this);
        this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewLegales).setOnClickListener(this);
        this.pantallaConfirmacionTransferencia.findViewById(R.id.buttonTransfer).setOnClickListener(this);
        I();
        this.aV = (String) arrayList2.get(0);
        ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewFechaEjecucion)).setText((String) arrayList2.get(1));
        ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewPlazoAcreditacion)).setText((String) arrayList2.get(4));
        ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewConcepto)).setText((String) arrayList2.get(5));
        ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewReferenciaConcepto)).setText((String) arrayList2.get(6));
        ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewTitular)).setText((String) arrayList2.get(7));
        CAccessibility cAccessibility = new CAccessibility(getActivity().getApplicationContext());
        this.aU = this.ad.equals("USD") ? "U$S" : "$";
        TextView textView = (TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewImporteSignoMoneda);
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.ID468_TRANSFERENCE_PROOF_PAYMENT));
        sb.append(" %s");
        textView.setText(String.format(sb.toString(), new Object[]{this.aU}));
        try {
            str = datosCuentasBean.getDescCtaDestino();
            try {
                TextView textView2 = (TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewCuentaOrigen);
                textView2.setText(str);
                textView2.setContentDescription(cAccessibility.applyFilterAccount(textView2.getText().toString()));
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            str = null;
        }
        String str2 = (String) arrayList2.get(3);
        if (str != null) {
            if (!str.contains(Constants.SYMBOL_CURRENCY_DOLAR)) {
                this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewImporteSignoMoneda).setContentDescription(getResources().getString(R.string.ACCESSIBILITY_TRANSFERENCIAS_IMPORTE_PESO_ARGENTINO));
            } else {
                this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewImporteSignoMoneda).setContentDescription(getResources().getString(R.string.ACCESSIBILITY_TRANSFERENCIAS_IMPORTE_DOLAR));
            }
        }
        ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewImporte)).setText(str2);
        for (int i2 = 0; i2 < leyendasBean.getLeyendasBean().size(); i2++) {
            if (((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getIdLeyenda().equals("TRANSF_CONF") && !TextUtils.isEmpty(((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getDescripcion())) {
                ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewLeyendaConfirmacion)).setText(Html.fromHtml(((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getDescripcion()));
            }
        }
        try {
            TextView textView3 = (TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewCuentaDestino);
            if (this.aV.equals(TransferenciasConstants.cBANCO_PROPIA)) {
                this.d.trackScreen(getString(R.string.analytics_screen_name_confirmacion_transferencias_cuentas_propias));
                textView3.setText(Html.fromHtml(datosCuentasBean2.getDescCtaDestino()));
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerTipo).setVisibility(8);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerNumero).setVisibility(8);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutBancoDestino).setVisibility(8);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerBancoDestino).setVisibility(8);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutCUIT).setVisibility(8);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerCUIT).setVisibility(8);
            } else if (this.aV.equals(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
                this.d.trackScreen(getString(R.string.analytics_screen_name_confirmacion_transferencias_cuentas_terceros));
                textView3.setText(Html.fromHtml(datosCuentasDestBSRBean.getDescripcion()));
                ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewTipo)).setText(Html.fromHtml(datosCuentasDestBSRBean.getTipoDescripcion()));
                TextView textView4 = (TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewNumero);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(datosCuentasDestBSRBean.getSucursal());
                sb2.append("-");
                sb2.append(datosCuentasDestBSRBean.getNumero().substring(0, 6));
                sb2.append("/");
                sb2.append(datosCuentasDestBSRBean.getNumero().substring(6, 7));
                textView4.setText(Html.fromHtml(sb2.toString()));
                this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewNumero).setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterCharacterToCharacter(((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewNumero)).getText().toString()));
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerTipo).setVisibility(0);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutTipo).setVisibility(0);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerNumero).setVisibility(0);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutNumero).setVisibility(0);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutBancoDestino).setVisibility(8);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerBancoDestino).setVisibility(8);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutCUIT).setVisibility(8);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerCUIT).setVisibility(8);
            } else {
                this.d.trackScreen(getString(R.string.analytics_screen_name_confirmacion_transferencias_cuentas_otros_bancos));
                String str3 = (String) arrayList2.get(8);
                String str4 = (String) arrayList2.get(9);
                textView3.setText(Html.fromHtml(datosCuentasDestOBBean.getDescripcion()));
                ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewBancoDestino)).setText(Html.fromHtml(str3));
                ((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewCUIT)).setText(Html.fromHtml(str4));
                this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewCUIT).setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterCharacterToCharacter(((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewCUIT)).getText().toString()));
                this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewBancoDestino).setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterGeneral(((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewBancoDestino)).getText().toString()));
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerBancoDestino).setVisibility(0);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutBancoDestino).setVisibility(0);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.dividerCUIT).setVisibility(0);
                this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutCUIT).setVisibility(0);
            }
            textView3.setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterGeneral(textView3.getText().toString()));
        } catch (Exception unused3) {
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.bl = getActivity();
    }

    public void goToComprobanteTransferencia(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosTransferenciaSalidaBean datosTransferenciaSalidaBean, LeyendasBean leyendasBean) {
        this.aR = true;
        gotoPage(6);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        ((SantanderRioMainActivity) getActivity()).lockMenu(false);
        enableMenuButton();
        this.d.trackScreen(getString(R.string.analytics_screen_name_comprobante_transferencia));
        this.al.onCreatePage(arrayList, datosCuentasBean, datosCuentasBean2, datosCuentasDestBSRBean, datosTransferenciaSalidaBean, leyendasBean);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x01ad  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x01b0  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x026b  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x02b0  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x03a5 A[Catch:{ Exception -> 0x03fd }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x03d2 A[Catch:{ Exception -> 0x03fd }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setComprobanteTransferenciaView(java.util.ArrayList<java.lang.String> r7, ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r8, ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r9, ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r10, ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaSalidaBean r11, ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean r12) {
        /*
            r6 = this;
            android.support.v4.app.FragmentActivity r8 = r6.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r8 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r8
            android.support.v7.app.ActionBar r8 = r8.getSupportActionBar()
            android.view.View r8 = r8.getCustomView()
            r0 = 2131365721(0x7f0a0f59, float:1.8351315E38)
            android.view.View r1 = r8.findViewById(r0)
            r2 = 0
            r1.setVisibility(r2)
            android.view.View r8 = r8.findViewById(r0)
            r8.setOnClickListener(r6)
            r6.initializeComprobanteTransferencia()
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility r8 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility
            android.support.v4.app.FragmentActivity r0 = r6.getActivity()
            r8.<init>(r0)
            r6.aO = r8
            java.lang.Object r8 = r7.get(r2)
            java.lang.String r8 = (java.lang.String) r8
            r6.aV = r8
            r8 = 0
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x0098 }
            android.support.v4.app.FragmentActivity r1 = r6.getActivity()     // Catch:{ Exception -> 0x0098 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x0098 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0098 }
            r1 = 1
            java.lang.Object r1 = r7.get(r1)     // Catch:{ Exception -> 0x0098 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0098 }
            android.view.View r8 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x0096 }
            r3 = 2131365908(0x7f0a1014, float:1.8351695E38)
            android.view.View r8 = r8.findViewById(r3)     // Catch:{ Exception -> 0x0096 }
            android.widget.TextView r8 = (android.widget.TextView) r8     // Catch:{ Exception -> 0x0096 }
            if (r1 == 0) goto L_0x005d
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.commons.CAccounts.getMaskAccount(r1)     // Catch:{ Exception -> 0x0096 }
            goto L_0x005f
        L_0x005d:
            java.lang.String r3 = ""
        L_0x005f:
            r8.setText(r3)     // Catch:{ Exception -> 0x0096 }
            java.lang.CharSequence r3 = r8.getText()     // Catch:{ Exception -> 0x0096 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0096 }
            java.lang.String r3 = r0.applyFilterAccount(r3)     // Catch:{ Exception -> 0x0096 }
            r8.setContentDescription(r3)     // Catch:{ Exception -> 0x0096 }
            r8 = 2
            java.lang.Object r8 = r7.get(r8)     // Catch:{ Exception -> 0x0096 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Exception -> 0x0096 }
            android.view.View r3 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x0096 }
            r4 = 2131365912(0x7f0a1018, float:1.8351703E38)
            android.view.View r3 = r3.findViewById(r4)     // Catch:{ Exception -> 0x0096 }
            android.widget.TextView r3 = (android.widget.TextView) r3     // Catch:{ Exception -> 0x0096 }
            r3.setText(r8)     // Catch:{ Exception -> 0x0096 }
            java.lang.CharSequence r8 = r3.getText()     // Catch:{ Exception -> 0x0096 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0096 }
            java.lang.String r8 = r0.applyFilterAccount(r8)     // Catch:{ Exception -> 0x0096 }
            r3.setContentDescription(r8)     // Catch:{ Exception -> 0x0096 }
            goto L_0x00a2
        L_0x0096:
            r8 = move-exception
            goto L_0x009b
        L_0x0098:
            r0 = move-exception
            r1 = r8
            r8 = r0
        L_0x009b:
            java.lang.String r0 = "@dev"
            java.lang.String r3 = "Error en accesibilidad"
            android.util.Log.e(r0, r3, r8)
        L_0x00a2:
            r8 = 3
            java.lang.Object r8 = r7.get(r8)
            java.lang.String r8 = (java.lang.String) r8
            android.view.View r0 = r6.pantallaComprobanteTransferencia
            r3 = 2131366010(0x7f0a107a, float:1.8351901E38)
            android.view.View r0 = r0.findViewById(r3)
            android.widget.TextView r0 = (android.widget.TextView) r0
            android.text.Spanned r8 = android.text.Html.fromHtml(r8)
            r0.setText(r8)
            r8 = 5
            java.lang.Object r8 = r7.get(r8)
            java.lang.String r8 = (java.lang.String) r8
            android.view.View r0 = r6.pantallaComprobanteTransferencia
            r3 = 2131365996(0x7f0a106c, float:1.8351873E38)
            android.view.View r0 = r0.findViewById(r3)
            android.widget.TextView r0 = (android.widget.TextView) r0
            android.text.Spanned r8 = android.text.Html.fromHtml(r8)
            r0.setText(r8)
            r8 = 6
            java.lang.Object r8 = r7.get(r8)
            java.lang.String r8 = (java.lang.String) r8
            android.view.View r0 = r6.pantallaComprobanteTransferencia
            r3 = 2131365905(0x7f0a1011, float:1.8351688E38)
            android.view.View r0 = r0.findViewById(r3)
            android.widget.TextView r0 = (android.widget.TextView) r0
            android.text.Spanned r8 = android.text.Html.fromHtml(r8)
            r0.setText(r8)
            r8 = 7
            java.lang.Object r7 = r7.get(r8)
            java.lang.String r7 = (java.lang.String) r7
            android.view.View r8 = r6.pantallaComprobanteTransferencia
            r0 = 2131365963(0x7f0a104b, float:1.8351806E38)
            android.view.View r8 = r8.findViewById(r0)
            android.widget.TextView r8 = (android.widget.TextView) r8
            android.text.Spanned r7 = android.text.Html.fromHtml(r7)
            r8.setText(r7)
            if (r1 == 0) goto L_0x018d
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_DOLAR
            boolean r7 = r1.contains(r7)
            r8 = 2131365952(0x7f0a1040, float:1.8351784E38)
            if (r7 != 0) goto L_0x0141
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_PESOS
            java.lang.String r0 = r11.getImporteOrigen()
            java.lang.String r0 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getFormattedAmountInArsFromString(r0)
            android.view.View r1 = r6.pantallaComprobanteTransferencia
            android.view.View r1 = r1.findViewById(r8)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            android.content.res.Resources r4 = r6.getResources()
            r5 = 2131755077(0x7f100045, float:1.9141023E38)
            java.lang.String r4 = r4.getString(r5)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.setContentDescription(r3)
            goto L_0x016e
        L_0x0141:
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_DOLAR
            java.lang.String r0 = r11.getImporteOrigen()
            java.lang.String r0 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getFormattedAmountInArsFromString(r0)
            android.view.View r1 = r6.pantallaComprobanteTransferencia
            android.view.View r1 = r1.findViewById(r8)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            android.content.res.Resources r4 = r6.getResources()
            r5 = 2131755074(0x7f100042, float:1.9141017E38)
            java.lang.String r4 = r4.getString(r5)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.setContentDescription(r3)
        L_0x016e:
            android.view.View r1 = r6.pantallaComprobanteTransferencia
            android.view.View r8 = r1.findViewById(r8)
            android.widget.TextView r8 = (android.widget.TextView) r8
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r7)
            java.lang.String r7 = " "
            r1.append(r7)
            r1.append(r0)
            java.lang.String r7 = r1.toString()
            r8.setText(r7)
        L_0x018d:
            java.lang.String r7 = r11.getFechaTransferencia()
            android.view.View r8 = r6.pantallaComprobanteTransferencia
            r0 = 2131365925(0x7f0a1025, float:1.835173E38)
            android.view.View r8 = r8.findViewById(r0)
            android.widget.TextView r8 = (android.widget.TextView) r8
            java.lang.String r7 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat2(r7)
            r8.setText(r7)
            java.lang.String r7 = r11.getNroComprobante()
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 == 0) goto L_0x01b0
            java.lang.String r7 = ""
            goto L_0x01b4
        L_0x01b0:
            java.lang.String r7 = r11.getNroComprobante()
        L_0x01b4:
            android.view.View r8 = r6.pantallaComprobanteTransferencia
            r0 = 2131365987(0x7f0a1063, float:1.8351855E38)
            android.view.View r8 = r8.findViewById(r0)
            android.widget.TextView r8 = (android.widget.TextView) r8
            r8.setText(r7)
            android.view.View r8 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x01ec }
            android.view.View r8 = r8.findViewById(r0)     // Catch:{ Exception -> 0x01ec }
            android.support.v4.app.FragmentActivity r1 = r6.getActivity()     // Catch:{ Exception -> 0x01ec }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x01ec }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x01ec }
            android.view.View r3 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x01ec }
            android.view.View r0 = r3.findViewById(r0)     // Catch:{ Exception -> 0x01ec }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x01ec }
            java.lang.CharSequence r0 = r0.getText()     // Catch:{ Exception -> 0x01ec }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x01ec }
            java.lang.String r0 = r1.applyFilterCharacterToCharacter(r0)     // Catch:{ Exception -> 0x01ec }
            r8.setContentDescription(r0)     // Catch:{ Exception -> 0x01ec }
            goto L_0x01f0
        L_0x01ec:
            r8 = move-exception
            r8.printStackTrace()
        L_0x01f0:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r8 = r6.d
            r0 = 2131758498(0x7f100da2, float:1.9147962E38)
            java.lang.String r0 = r6.getString(r0)
            r8.trackTransaction(r0, r7)
            r7 = 0
        L_0x01fd:
            java.util.List r8 = r12.getLeyendasBean()
            int r8 = r8.size()
            if (r7 >= r8) goto L_0x0250
            java.util.List r8 = r12.getLeyendasBean()
            java.lang.Object r8 = r8.get(r7)
            ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean r8 = (ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean) r8
            java.lang.String r8 = r8.getIdLeyenda()
            java.lang.String r0 = "TRANSF_COMP"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x024d
            java.util.List r8 = r12.getLeyendasBean()
            java.lang.Object r8 = r8.get(r7)
            ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean r8 = (ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean) r8
            java.lang.String r8 = r8.getDescripcion()
            java.lang.String r0 = "#A"
            java.lang.String r1 = "<"
            java.lang.String r8 = ar.com.santander.rio.mbanking.utils.UtilString.replaceCharacters(r8, r0, r1)
            java.lang.String r0 = "#C"
            java.lang.String r1 = ">"
            java.lang.String r8 = ar.com.santander.rio.mbanking.utils.UtilString.replaceCharacters(r8, r0, r1)
            android.view.View r0 = r6.pantallaComprobanteTransferencia
            r1 = 2131365966(0x7f0a104e, float:1.8351812E38)
            android.view.View r0 = r0.findViewById(r1)
            android.widget.TextView r0 = (android.widget.TextView) r0
            android.text.Spanned r8 = android.text.Html.fromHtml(r8)
            r0.setText(r8)
        L_0x024d:
            int r7 = r7 + 1
            goto L_0x01fd
        L_0x0250:
            java.lang.String r7 = r6.aV
            java.lang.String r8 = "Propia"
            boolean r7 = r7.equals(r8)
            r8 = 2131365046(0x7f0a0cb6, float:1.8349946E38)
            r12 = 2131364629(0x7f0a0b15, float:1.83491E38)
            r0 = 2131364628(0x7f0a0b14, float:1.8349098E38)
            r1 = 2131365917(0x7f0a101d, float:1.8351713E38)
            r3 = 2131364630(0x7f0a0b16, float:1.8349102E38)
            r4 = 8
            if (r7 == 0) goto L_0x02b0
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r7 = r6.d
            r10 = 2131757817(0x7f100af9, float:1.914658E38)
            java.lang.String r10 = r6.getString(r10)
            r7.trackScreen(r10)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r3)
            r7.setVisibility(r2)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r8)
            r7.setVisibility(r2)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r1)
            android.widget.TextView r7 = (android.widget.TextView) r7
            java.lang.String r8 = r9.getTipoDescripcion()
            android.text.Spanned r8 = android.text.Html.fromHtml(r8)
            r7.setText(r8)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r0)
            r7.setVisibility(r4)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r12)
            r7.setVisibility(r4)
            goto L_0x0386
        L_0x02b0:
            java.lang.String r7 = r6.aV
            java.lang.String r9 = "Terceros Santander"
            boolean r7 = r7.equals(r9)
            if (r7 == 0) goto L_0x02ff
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r7 = r6.d
            r9 = 2131757818(0x7f100afa, float:1.9146582E38)
            java.lang.String r9 = r6.getString(r9)
            r7.trackScreen(r9)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r3)
            r7.setVisibility(r2)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r8)
            r7.setVisibility(r2)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r1)
            android.widget.TextView r7 = (android.widget.TextView) r7
            java.lang.String r8 = r10.getDescripcion()
            android.text.Spanned r8 = android.text.Html.fromHtml(r8)
            r7.setText(r8)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r0)
            r7.setVisibility(r4)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r12)
            r7.setVisibility(r4)
            goto L_0x0386
        L_0x02ff:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r7 = r6.d
            r8 = 2131757816(0x7f100af8, float:1.9146578E38)
            java.lang.String r8 = r6.getString(r8)
            r7.trackScreen(r8)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r3)
            r7.setVisibility(r4)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r0)
            r7.setVisibility(r2)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            r8 = 2131365038(0x7f0a0cae, float:1.834993E38)
            android.view.View r7 = r7.findViewById(r8)
            r7.setVisibility(r2)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            android.view.View r7 = r7.findViewById(r12)
            r7.setVisibility(r2)
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            r8 = 2131365041(0x7f0a0cb1, float:1.8349936E38)
            android.view.View r7 = r7.findViewById(r8)
            r7.setVisibility(r2)
            android.view.View r7 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x0370 }
            r8 = 2131365893(0x7f0a1005, float:1.8351664E38)
            android.view.View r7 = r7.findViewById(r8)     // Catch:{ Exception -> 0x0370 }
            android.widget.TextView r7 = (android.widget.TextView) r7     // Catch:{ Exception -> 0x0370 }
            java.lang.String r8 = r6.aC     // Catch:{ Exception -> 0x0370 }
            r7.setText(r8)     // Catch:{ Exception -> 0x0370 }
            android.view.View r7 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x0370 }
            r8 = 2131365896(0x7f0a1008, float:1.835167E38)
            android.view.View r7 = r7.findViewById(r8)     // Catch:{ Exception -> 0x0370 }
            android.widget.TextView r7 = (android.widget.TextView) r7     // Catch:{ Exception -> 0x0370 }
            java.lang.String r8 = r6.aA     // Catch:{ Exception -> 0x0370 }
            r7.setText(r8)     // Catch:{ Exception -> 0x0370 }
            android.view.View r7 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x0370 }
            android.view.View r7 = r7.findViewById(r1)     // Catch:{ Exception -> 0x0370 }
            android.widget.TextView r7 = (android.widget.TextView) r7     // Catch:{ Exception -> 0x0370 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r8 = r6.aL     // Catch:{ Exception -> 0x0370 }
            java.lang.String r8 = r8.getDescripcion()     // Catch:{ Exception -> 0x0370 }
            r7.setText(r8)     // Catch:{ Exception -> 0x0370 }
            goto L_0x037a
        L_0x0370:
            r7 = move-exception
            java.lang.String r8 = ""
            java.lang.String r7 = r7.toString()
            android.util.Log.e(r8, r7)
        L_0x037a:
            java.lang.String r7 = ""
            r6.aC = r7
            java.lang.String r7 = ""
            r6.aA = r7
            java.lang.String r7 = ""
            r6.aB = r7
        L_0x0386:
            android.view.View r7 = r6.pantallaComprobanteTransferencia
            r8 = 2131364219(0x7f0a097b, float:1.8348269E38)
            android.view.View r7 = r7.findViewById(r8)
            r7.setOnClickListener(r6)
            java.lang.String r7 = r11.getNroComprobanteAltaDest()     // Catch:{ Exception -> 0x03fd }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x03fd }
            r8 = 2131365900(0x7f0a100c, float:1.8351678E38)
            r9 = 2131365988(0x7f0a1064, float:1.8351857E38)
            r10 = 2131365044(0x7f0a0cb4, float:1.8349942E38)
            if (r7 != 0) goto L_0x03d2
            android.view.View r7 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r7.findViewById(r10)     // Catch:{ Exception -> 0x03fd }
            android.widget.LinearLayout r7 = (android.widget.LinearLayout) r7     // Catch:{ Exception -> 0x03fd }
            r7.setVisibility(r2)     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r7.findViewById(r9)     // Catch:{ Exception -> 0x03fd }
            android.widget.TextView r7 = (android.widget.TextView) r7     // Catch:{ Exception -> 0x03fd }
            java.lang.String r9 = r11.getNroComprobanteAltaDest()     // Catch:{ Exception -> 0x03fd }
            r7.setText(r9)     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r7.findViewById(r8)     // Catch:{ Exception -> 0x03fd }
            android.widget.TextView r7 = (android.widget.TextView) r7     // Catch:{ Exception -> 0x03fd }
            r8 = 2131756367(0x7f10054f, float:1.914364E38)
            java.lang.String r8 = r6.getString(r8)     // Catch:{ Exception -> 0x03fd }
            r7.setText(r8)     // Catch:{ Exception -> 0x03fd }
            goto L_0x0407
        L_0x03d2:
            android.view.View r7 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r7.findViewById(r10)     // Catch:{ Exception -> 0x03fd }
            android.widget.LinearLayout r7 = (android.widget.LinearLayout) r7     // Catch:{ Exception -> 0x03fd }
            r7.setVisibility(r4)     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r7.findViewById(r9)     // Catch:{ Exception -> 0x03fd }
            android.widget.TextView r7 = (android.widget.TextView) r7     // Catch:{ Exception -> 0x03fd }
            java.lang.String r9 = ""
            r7.setText(r9)     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r6.pantallaComprobanteTransferencia     // Catch:{ Exception -> 0x03fd }
            android.view.View r7 = r7.findViewById(r8)     // Catch:{ Exception -> 0x03fd }
            android.widget.TextView r7 = (android.widget.TextView) r7     // Catch:{ Exception -> 0x03fd }
            r8 = 2131756164(0x7f100484, float:1.9143228E38)
            java.lang.String r8 = r6.getString(r8)     // Catch:{ Exception -> 0x03fd }
            r7.setText(r8)     // Catch:{ Exception -> 0x03fd }
            goto L_0x0407
        L_0x03fd:
            r7 = move-exception
            java.lang.String r8 = ""
            java.lang.String r7 = r7.toString()
            android.util.Log.e(r8, r7)
        L_0x0407:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.setComprobanteTransferenciaView(java.util.ArrayList, ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean, ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean, ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean, ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaSalidaBean, ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean):void");
    }

    public void goToAgendaDestinatarios(String str, String str2, CuentasPropiasBean cuentasPropiasBean, AgendadosBean agendadosBean, String str3) {
        this.ba = str3;
        gotoPage(3);
        D();
        E();
        F();
        getActivity().invalidateOptionsMenu();
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        this.ai.onCreatePage(str, str2, cuentasPropiasBean, agendadosBean, this.af, this.ad);
    }

    private void D() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK_WITH_ADD);
    }

    private void E() {
        View findViewById = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.findViewById(R.id.back_imgButton).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TransferenciasFragment.this.onBackPressed();
                }
            });
        }
    }

    private void F() {
        final View findViewById = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.add_imgButton);
        if (findViewById != null) {
            findViewById.findViewById(R.id.add_imgButton).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    findViewById.findViewById(R.id.add_imgButton).setOnClickListener(null);
                    if (TransferenciasFragment.this.ba.equals("SINGLE_CHOICE")) {
                        TransferenciasFragment.this.goToNuevoDestinatarioActivity(Origen.ORIGEN_PARA);
                    } else {
                        TransferenciasFragment.this.goToNuevoDestinatarioActivity(Origen.ORIGEN_NUEVO);
                    }
                }
            });
            findViewById.setContentDescription(getString(R.string.ID442_TRANSFERENCE_LBL_NUEVO_DESTINATARIO));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x01b3 A[Catch:{ Exception -> 0x0203 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x01b6 A[Catch:{ Exception -> 0x0203 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x01c7 A[Catch:{ Exception -> 0x0203 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01ca A[Catch:{ Exception -> 0x0203 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void goToEditarDestinatarios(ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r34, java.lang.Boolean r35, ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r36) {
        /*
            r33 = this;
            r1 = r33
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r15 = new ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios
            java.lang.String r3 = r34.getTitulo()
            java.lang.String r4 = r34.getNombre()
            java.lang.String r5 = r34.getInfo1()
            java.lang.String r6 = r34.getInfo2()
            r14 = 0
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r14)
            java.lang.String r8 = r34.getTipoDescripcion()
            java.lang.String r9 = r34.getEmail()
            java.lang.String r10 = r34.getTipoDestino()
            java.lang.String r11 = r34.getDescripcion()
            java.lang.String r12 = r34.getBanco()
            java.lang.String r13 = r34.getBeneficiario()
            java.lang.String r16 = r34.getAlias()
            r2 = r15
            r14 = r16
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            android.content.Intent r2 = new android.content.Intent
            android.support.v4.app.FragmentActivity r3 = r33.getActivity()
            java.lang.Class<ar.com.santander.rio.mbanking.app.ui.activities.EditarEliminarDestinatarioTransferenciaActivity> r4 = ar.com.santander.rio.mbanking.app.ui.activities.EditarEliminarDestinatarioTransferenciaActivity.class
            r2.<init>(r3, r4)
            java.lang.String r3 = "ORIGEN"
            java.lang.String r4 = "EDITAR"
            r2.putExtra(r3, r4)
            if (r36 != 0) goto L_0x0097
            boolean r3 = r35.booleanValue()
            if (r3 == 0) goto L_0x006d
            java.lang.String r3 = "CUENTA_AGENDA"
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r4 = r1.af
            java.lang.String r4 = r4.getTipoDestino()
            java.lang.String r5 = "02"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0068
            java.lang.String r4 = "SR"
            goto L_0x006a
        L_0x0068:
            java.lang.String r4 = "OB"
        L_0x006a:
            r2.putExtra(r3, r4)
        L_0x006d:
            java.lang.String r3 = r15.getBanco()
            java.lang.String r4 = "Terceros Santander"
            boolean r3 = r3.equalsIgnoreCase(r4)
            if (r3 == 0) goto L_0x0088
            java.lang.String r3 = r15.getInfo2()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r3 = r1.getDatosCuentasDestBSRBeanPara(r3)
            java.lang.String r4 = "DESTINATARIOBSRBEAN"
            r2.putExtra(r4, r3)
            goto L_0x038d
        L_0x0088:
            java.lang.String r3 = r15.getInfo2()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r3 = r1.getDatosCuentasDestOBBeanPara(r3)
            java.lang.String r4 = "DESTINATARIOOBBEAN"
            r2.putExtra(r4, r3)
            goto L_0x038d
        L_0x0097:
            boolean r4 = r35.booleanValue()
            if (r4 == 0) goto L_0x00a4
            java.lang.String r4 = "EJECUTAR_REASIGNACION"
            r5 = 1
            r2.putExtra(r4, r5)
            goto L_0x00aa
        L_0x00a4:
            java.lang.String r4 = "EJECUTAR_REASIGNACION"
            r5 = 0
            r2.putExtra(r4, r5)
        L_0x00aa:
            java.lang.String r4 = r36.getBancoDestino()
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            r5 = 0
            if (r4 != 0) goto L_0x022a
            java.lang.String r4 = r36.getBancoDestino()
            java.lang.String r6 = "BANCO SANTANDER"
            boolean r4 = r4.equalsIgnoreCase(r6)
            if (r4 == 0) goto L_0x022a
            boolean r4 = r35.booleanValue()
            if (r4 == 0) goto L_0x00e2
            java.lang.String r4 = r15.getTipoDestino()
            java.lang.String r6 = "03"
            boolean r4 = r4.equalsIgnoreCase(r6)
            if (r4 == 0) goto L_0x00db
            java.lang.String r4 = "CUENTA_AGENDA"
            java.lang.String r6 = "OB"
            r2.putExtra(r4, r6)
            goto L_0x00e2
        L_0x00db:
            java.lang.String r4 = "CUENTA_AGENDA"
            java.lang.String r6 = "SR"
            r2.putExtra(r4, r6)
        L_0x00e2:
            java.lang.String r4 = r15.getInfo2()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r4 = r1.getDatosCuentasDestBSRBeanPara(r4)
            boolean r6 = r35.booleanValue()
            if (r6 == 0) goto L_0x0223
            if (r4 != 0) goto L_0x0142
            java.lang.String r4 = r15.getInfo2()     // Catch:{ Exception -> 0x013a }
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r4 = r1.getDatosCuentasDestOBBeanPara(r4)     // Catch:{ Exception -> 0x013a }
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r6 = new ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean     // Catch:{ Exception -> 0x013a }
            r6.<init>()     // Catch:{ Exception -> 0x013a }
            java.lang.String r5 = "02"
            r6.setTipoDestino(r5)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r5 = r4.getDescripcion()     // Catch:{ Exception -> 0x0137 }
            r6.setDescripcion(r5)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r5 = r4.getEmail()     // Catch:{ Exception -> 0x0137 }
            r6.setEmail(r5)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r5 = r4.getAlias()     // Catch:{ Exception -> 0x0137 }
            r6.setAlias(r5)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r5 = "Terceros Santander"
            r15.setInfo1(r5)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r5 = r4.getEmail()     // Catch:{ Exception -> 0x0137 }
            r15.setEmail(r5)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r5 = "02"
            r15.setTipoDestino(r5)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r4 = r4.getDescripcion()     // Catch:{ Exception -> 0x0137 }
            r15.setDescripcion(r4)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r4 = "Terceros Santander"
            r15.setBanco(r4)     // Catch:{ Exception -> 0x0137 }
            goto L_0x0140
        L_0x0137:
            r0 = move-exception
            r4 = r0
            goto L_0x013d
        L_0x013a:
            r0 = move-exception
            r4 = r0
            r6 = r5
        L_0x013d:
            r4.fillInStackTrace()
        L_0x0140:
            r4 = r6
            goto L_0x018a
        L_0x0142:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean
            java.lang.String r17 = r4.getTipoDestino()
            java.lang.String r18 = r4.getTipo()
            java.lang.String r19 = r4.getNumero()
            java.lang.String r20 = r4.getSucursal()
            java.lang.String r21 = r4.getDiasAgValido()
            java.lang.String r22 = r4.getMoneda()
            java.lang.String r23 = r4.getIdMoneda()
            java.lang.String r24 = r4.getTipoDescripcion()
            java.lang.String r25 = r4.getDescCtaDestinoBSR()
            java.lang.String r26 = r4.getDescripcion()
            java.lang.String r27 = r4.getNombreTitular()
            java.lang.String r28 = r4.getNombreCuenta()
            java.lang.String r29 = r4.getEmail()
            java.lang.String r30 = r4.getCbuDestino()
            java.lang.String r31 = r4.getDescripcionModificada()
            java.lang.String r32 = r4.getAlias()
            r16 = r5
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)
            r4 = r5
        L_0x018a:
            java.lang.String r5 = r36.getTipo()     // Catch:{ Exception -> 0x0203 }
            r4.setTipo(r5)     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r36.getNumero()     // Catch:{ Exception -> 0x0203 }
            r4.setNumero(r5)     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r36.getSucursal()     // Catch:{ Exception -> 0x0203 }
            r4.setSucursal(r5)     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r36.getTipoDescripcion()     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r5.toUpperCase()     // Catch:{ Exception -> 0x0203 }
            java.lang.String r6 = "Dólares"
            java.lang.String r6 = r6.toUpperCase()     // Catch:{ Exception -> 0x0203 }
            boolean r5 = r5.contains(r6)     // Catch:{ Exception -> 0x0203 }
            if (r5 == 0) goto L_0x01b6
            java.lang.String r5 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_DOLLAR_STR     // Catch:{ Exception -> 0x0203 }
            goto L_0x01b8
        L_0x01b6:
            java.lang.String r5 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_PESOS_STR     // Catch:{ Exception -> 0x0203 }
        L_0x01b8:
            r4.setMoneda(r5)     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r4.getMoneda()     // Catch:{ Exception -> 0x0203 }
            java.lang.String r6 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_PESOS_STR     // Catch:{ Exception -> 0x0203 }
            boolean r5 = r5.equalsIgnoreCase(r6)     // Catch:{ Exception -> 0x0203 }
            if (r5 == 0) goto L_0x01ca
            java.lang.String r5 = "0"
            goto L_0x01cc
        L_0x01ca:
            java.lang.String r5 = "1"
        L_0x01cc:
            r4.setIdMoneda(r5)     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r36.getTipoDescripcion()     // Catch:{ Exception -> 0x0203 }
            r4.setTipoDescripcion(r5)     // Catch:{ Exception -> 0x0203 }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r5 = r1.b     // Catch:{ Exception -> 0x0203 }
            ar.com.santander.rio.mbanking.app.commons.CAccounts r5 = ar.com.santander.rio.mbanking.app.commons.CAccounts.getInstance(r5)     // Catch:{ Exception -> 0x0203 }
            ar.com.santander.rio.mbanking.services.model.general.Cuenta r6 = new ar.com.santander.rio.mbanking.services.model.general.Cuenta     // Catch:{ Exception -> 0x0203 }
            java.lang.String r7 = r36.getTipo()     // Catch:{ Exception -> 0x0203 }
            java.lang.String r8 = r36.getNumero()     // Catch:{ Exception -> 0x0203 }
            java.lang.String r9 = r36.getSucursal()     // Catch:{ Exception -> 0x0203 }
            r6.<init>(r7, r8, r9)     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r5.getAbrevAccount(r6)     // Catch:{ Exception -> 0x0203 }
            r4.setDescCtaDestinoBSR(r5)     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r36.getTitular()     // Catch:{ Exception -> 0x0203 }
            r4.setNombreTitular(r5)     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r36.getCbu()     // Catch:{ Exception -> 0x0203 }
            r4.setCbuDestino(r5)     // Catch:{ Exception -> 0x0203 }
            goto L_0x0208
        L_0x0203:
            r0 = move-exception
            r5 = r0
            r5.fillInStackTrace()
        L_0x0208:
            java.lang.String r5 = r36.getTitular()     // Catch:{ Exception -> 0x021e }
            r15.setTitulo(r5)     // Catch:{ Exception -> 0x021e }
            java.lang.String r5 = r4.getDescCtaDestinoBSR()     // Catch:{ Exception -> 0x021e }
            r15.setInfo2(r5)     // Catch:{ Exception -> 0x021e }
            java.lang.String r3 = r36.getTipoDescripcion()     // Catch:{ Exception -> 0x021e }
            r15.setTipoDescripcion(r3)     // Catch:{ Exception -> 0x021e }
            goto L_0x0223
        L_0x021e:
            r0 = move-exception
            r3 = r0
            r3.fillInStackTrace()
        L_0x0223:
            java.lang.String r3 = "DESTINATARIOBSRBEAN"
            r2.putExtra(r3, r4)
            goto L_0x038d
        L_0x022a:
            boolean r4 = r35.booleanValue()
            if (r4 == 0) goto L_0x024b
            java.lang.String r4 = r15.getTipoDestino()
            java.lang.String r6 = "03"
            boolean r4 = r4.equalsIgnoreCase(r6)
            if (r4 == 0) goto L_0x0244
            java.lang.String r4 = "CUENTA_AGENDA"
            java.lang.String r6 = "OB"
            r2.putExtra(r4, r6)
            goto L_0x024b
        L_0x0244:
            java.lang.String r4 = "CUENTA_AGENDA"
            java.lang.String r6 = "SR"
            r2.putExtra(r4, r6)
        L_0x024b:
            java.lang.String r4 = r15.getInfo2()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r4 = r1.getDatosCuentasDestOBBeanPara(r4)
            boolean r6 = r35.booleanValue()
            if (r6 == 0) goto L_0x0388
            if (r4 != 0) goto L_0x02e6
            java.lang.String r4 = r15.getInfo2()     // Catch:{ Exception -> 0x02e0 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r4 = r1.getDatosCuentasDestBSRBeanPara(r4)     // Catch:{ Exception -> 0x02e0 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r6 = new ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r17 = r4.getTipoDestino()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r18 = r4.getTipo()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r19 = r4.getNumero()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r20 = r4.getSucursal()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r21 = r4.getDiasAgValido()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r22 = r4.getMoneda()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r23 = r4.getIdMoneda()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r24 = r4.getTipoDescripcion()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r25 = r4.getDescCtaDestinoBSR()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r26 = r4.getDescripcion()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r27 = r4.getNombreTitular()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r28 = r4.getNombreCuenta()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r29 = r4.getEmail()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r30 = r4.getCbuDestino()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r31 = r4.getDescripcionModificada()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r32 = r4.getAlias()     // Catch:{ Exception -> 0x02e0 }
            r16 = r6
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)     // Catch:{ Exception -> 0x02e0 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r4 = new ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean     // Catch:{ Exception -> 0x02e0 }
            r4.<init>()     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r5 = "03"
            r4.setTipoDestino(r5)     // Catch:{ Exception -> 0x02dd }
            java.lang.String r5 = r6.getDescripcion()     // Catch:{ Exception -> 0x02dd }
            r4.setDescripcion(r5)     // Catch:{ Exception -> 0x02dd }
            java.lang.String r5 = r6.getEmail()     // Catch:{ Exception -> 0x02dd }
            r4.setEmail(r5)     // Catch:{ Exception -> 0x02dd }
            java.lang.String r5 = r6.getAlias()     // Catch:{ Exception -> 0x02dd }
            r4.setAlias(r5)     // Catch:{ Exception -> 0x02dd }
            java.lang.String r5 = r4.getEmail()     // Catch:{ Exception -> 0x02dd }
            r15.setEmail(r5)     // Catch:{ Exception -> 0x02dd }
            java.lang.String r5 = "03"
            r15.setTipoDestino(r5)     // Catch:{ Exception -> 0x02dd }
            java.lang.String r5 = r4.getDescripcion()     // Catch:{ Exception -> 0x02dd }
            r15.setDescripcion(r5)     // Catch:{ Exception -> 0x02dd }
            goto L_0x0326
        L_0x02dd:
            r0 = move-exception
            r5 = r4
            goto L_0x02e1
        L_0x02e0:
            r0 = move-exception
        L_0x02e1:
            r4 = r0
            r4.fillInStackTrace()
            goto L_0x0325
        L_0x02e6:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r5 = new ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean
            java.lang.String r17 = r4.getTipoDestino()
            java.lang.String r18 = r4.getCbu()
            java.lang.String r19 = r4.getBeneficiario()
            java.lang.String r20 = r4.getDiasAgValido()
            java.lang.String r21 = r4.getCaracteristica()
            java.lang.String r22 = r4.getDescripcion()
            java.lang.String r23 = r4.getNombreTitular()
            java.lang.String r24 = r4.getBanco()
            java.lang.String r25 = r4.getBancoDestino()
            java.lang.String r26 = r4.getEmail()
            java.lang.String r27 = r4.getCbuDestino()
            java.lang.String r28 = r4.getDescripcionModificada()
            java.lang.String r29 = r4.getAlias()
            java.lang.String r30 = r4.getDiferido()
            r16 = r5
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30)
        L_0x0325:
            r4 = r5
        L_0x0326:
            java.lang.String r5 = r36.getCbu()     // Catch:{ Exception -> 0x034f }
            r4.setCbu(r5)     // Catch:{ Exception -> 0x034f }
            java.lang.String r5 = r36.getNumeroCuil()     // Catch:{ Exception -> 0x034f }
            r4.setBeneficiario(r5)     // Catch:{ Exception -> 0x034f }
            java.lang.String r5 = "02"
            r4.setCaracteristica(r5)     // Catch:{ Exception -> 0x034f }
            java.lang.String r5 = r36.getTitular()     // Catch:{ Exception -> 0x034f }
            r4.setNombreTitular(r5)     // Catch:{ Exception -> 0x034f }
            java.lang.String r5 = r36.getBancoDestino()     // Catch:{ Exception -> 0x034f }
            r4.setBanco(r5)     // Catch:{ Exception -> 0x034f }
            java.lang.String r5 = r36.getDiferido()     // Catch:{ Exception -> 0x034f }
            r4.setDiferido(r5)     // Catch:{ Exception -> 0x034f }
            goto L_0x0354
        L_0x034f:
            r0 = move-exception
            r5 = r0
            r5.fillInStackTrace()
        L_0x0354:
            java.lang.String r5 = r36.getTitular()     // Catch:{ Exception -> 0x0383 }
            r15.setTitulo(r5)     // Catch:{ Exception -> 0x0383 }
            java.lang.String r5 = r36.getBancoDestino()     // Catch:{ Exception -> 0x0383 }
            java.lang.String r5 = r5.toUpperCase()     // Catch:{ Exception -> 0x0383 }
            r15.setInfo1(r5)     // Catch:{ Exception -> 0x0383 }
            java.lang.String r5 = r36.getCbu()     // Catch:{ Exception -> 0x0383 }
            r15.setInfo2(r5)     // Catch:{ Exception -> 0x0383 }
            java.lang.String r5 = r36.getTipoDescripcion()     // Catch:{ Exception -> 0x0383 }
            r15.setTipoDescripcion(r5)     // Catch:{ Exception -> 0x0383 }
            java.lang.String r5 = r36.getBancoDestino()     // Catch:{ Exception -> 0x0383 }
            r15.setBanco(r5)     // Catch:{ Exception -> 0x0383 }
            java.lang.String r3 = r36.getNumeroCuil()     // Catch:{ Exception -> 0x0383 }
            r15.setBeneficiario(r3)     // Catch:{ Exception -> 0x0383 }
            goto L_0x0388
        L_0x0383:
            r0 = move-exception
            r3 = r0
            r3.fillInStackTrace()
        L_0x0388:
            java.lang.String r3 = "DESTINATARIOOBBEAN"
            r2.putExtra(r3, r4)
        L_0x038d:
            java.lang.String r3 = "DESTINATARIO"
            r2.putExtra(r3, r15)
            java.lang.String r3 = "CUENTA_PARA"
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r4 = r1.aH
            r2.putExtra(r3, r4)
            r3 = 666(0x29a, float:9.33E-43)
            r1.startActivityForResult(r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.goToEditarDestinatarios(ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios, java.lang.Boolean, ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean):void");
    }

    public void goToEliminarDestinatarios(AgendaDestinatarios agendaDestinatarios) {
        Intent intent = new Intent(getActivity(), EditarEliminarDestinatarioTransferenciaActivity.class);
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO, agendaDestinatarios);
        if (agendaDestinatarios.getBanco().equalsIgnoreCase(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
            intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_BSR_BEAN, getDatosCuentasDestBSRBeanPara(agendaDestinatarios.getInfo2()));
        } else {
            intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_OB_BEAN, getDatosCuentasDestOBBeanPara(agendaDestinatarios.getInfo2()));
        }
        intent.putExtra("ORIGEN", TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR);
        startActivityForResult(intent, 777);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:63:0x025e, code lost:
        if (r0.equals("ABM_ONLY") != false) goto L_0x0262;
     */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0269  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x029c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setAgendaDestinatariosView(java.lang.String r24, java.lang.String r25, ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean r26, ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean r27, ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r28, java.lang.String r29) {
        /*
            r23 = this;
            r8 = r23
            r0 = r27
            r1 = r29
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r2 = r8.d
            r3 = 2131757793(0x7f100ae1, float:1.9146532E38)
            java.lang.String r3 = r8.getString(r3)
            r2.trackScreen(r3)
            android.view.View r2 = r8.pantallaAgendaDestinatarios
            r9 = 2131365651(0x7f0a0f13, float:1.8351173E38)
            android.view.View r2 = r2.findViewById(r9)
            android.widget.EditText r2 = (android.widget.EditText) r2
            java.lang.String r3 = ""
            r2.setText(r3)
            java.util.ArrayList<ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios> r2 = r8.bg
            r2.clear()
            java.lang.String r2 = r8.ba
            java.lang.String r3 = "SINGLE_CHOICE"
            boolean r2 = r2.equalsIgnoreCase(r3)
            r3 = 0
            if (r2 == 0) goto L_0x00c7
            if (r26 == 0) goto L_0x00c7
            r4 = 0
        L_0x0035:
            java.util.List r5 = r26.getListDatosCuentasBean()
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x00c7
            java.util.List r5 = r26.getListDatosCuentasBean()
            java.lang.Object r5 = r5.get(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean) r5
            java.lang.String r5 = r5.getTipoDestino()
            java.lang.String r6 = "01"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x00c1
            java.lang.String r5 = "\\s+"
            java.lang.String r6 = ""
            r7 = r25
            java.lang.String r5 = r7.replaceAll(r5, r6)
            java.util.List r6 = r26.getListDatosCuentasBean()
            java.lang.Object r6 = r6.get(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r6 = (ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean) r6
            java.lang.String r6 = r6.getDescCtaDestino()
            java.lang.String r10 = "\\s+"
            java.lang.String r11 = ""
            java.lang.String r6 = r6.replaceAll(r10, r11)
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x00c3
            java.util.List r5 = r26.getListDatosCuentasBean()
            java.lang.Object r5 = r5.get(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean) r5
            java.lang.String r5 = r5.getMoneda()
            boolean r5 = r5.equalsIgnoreCase(r1)
            if (r5 == 0) goto L_0x00c3
            java.util.ArrayList<ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios> r5 = r8.bg
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r6 = new ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios
            java.lang.String r12 = ""
            java.lang.String r13 = "Tipo de Cuenta: Propia"
            java.util.List r10 = r26.getListDatosCuentasBean()
            java.lang.Object r10 = r10.get(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean r10 = (ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean) r10
            java.lang.String r14 = r10.getDescCtaDestino()
            java.lang.Boolean r15 = java.lang.Boolean.valueOf(r3)
            java.lang.String r16 = ""
            java.lang.String r17 = ""
            java.lang.String r18 = ""
            java.lang.String r19 = ""
            java.lang.String r20 = ""
            java.lang.String r21 = ""
            java.lang.String r22 = ""
            r10 = r6
            r11 = r24
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            r5.add(r6)
            goto L_0x00c3
        L_0x00c1:
            r7 = r25
        L_0x00c3:
            int r4 = r4 + 1
            goto L_0x0035
        L_0x00c7:
            if (r0 == 0) goto L_0x032f
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$SwipeListItemListener r2 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$SwipeListItemListener
            r2.<init>()
            r8.gestureListItemListener = r2
            ar.com.santander.rio.mbanking.components.OnSingleClickListener r2 = r8.f
            if (r2 == 0) goto L_0x00d8
            ar.com.santander.rio.mbanking.components.OnSingleClickListener r2 = r8.g
            if (r2 != 0) goto L_0x00e6
        L_0x00d8:
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$15 r2 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$15
            r2.<init>()
            r8.f = r2
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$16 r2 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$16
            r2.<init>()
            r8.g = r2
        L_0x00e6:
            java.lang.String r2 = r27.getListadoCompleto()
            java.lang.String r4 = "S"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0303
            java.util.List r2 = r27.getListAgDestBSRBean()
            if (r2 == 0) goto L_0x0193
            r2 = 0
        L_0x00f9:
            java.util.List r4 = r27.getListAgDestBSRBean()
            int r4 = r4.size()
            if (r2 >= r4) goto L_0x0193
            r4 = 0
        L_0x0104:
            java.util.List r5 = r27.getListAgDestBSRBean()
            java.lang.Object r5 = r5.get(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestBSRBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestBSRBean) r5
            java.util.List r5 = r5.getListDatosCuentasDestBSRBean()
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x018f
            java.util.List r5 = r27.getListAgDestBSRBean()
            java.lang.Object r5 = r5.get(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestBSRBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestBSRBean) r5
            java.util.List r5 = r5.getListDatosCuentasDestBSRBean()
            java.lang.Object r5 = r5.get(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean) r5
            java.lang.String r6 = r5.getTipoDestino()
            java.lang.String r7 = "02"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x018b
            java.lang.String r6 = r8.ba
            java.lang.String r7 = "ABM_ONLY"
            boolean r6 = r6.equalsIgnoreCase(r7)
            if (r6 != 0) goto L_0x0156
            java.lang.String r6 = r8.ba
            java.lang.String r7 = "SINGLE_CHOICE"
            boolean r6 = r6.equalsIgnoreCase(r7)
            if (r6 == 0) goto L_0x018b
            java.lang.String r6 = r5.getMoneda()
            boolean r6 = r6.equalsIgnoreCase(r1)
            if (r6 == 0) goto L_0x018b
        L_0x0156:
            java.util.ArrayList<ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios> r6 = r8.bg
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r7 = new ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios
            java.lang.String r11 = r5.getNombreTitular()
            java.lang.String r12 = r5.getDescripcion()
            java.lang.String r13 = "Terceros Santander"
            java.lang.String r14 = r5.getDescCtaDestinoBSR()
            java.lang.Boolean r15 = java.lang.Boolean.valueOf(r3)
            java.lang.String r16 = r5.getTipoDescripcion()
            java.lang.String r17 = r5.getEmail()
            java.lang.String r18 = r5.getTipoDestino()
            java.lang.String r19 = r5.getDescripcion()
            java.lang.String r20 = "Terceros Santander"
            java.lang.String r21 = ""
            java.lang.String r22 = r5.getAlias()
            r10 = r7
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            r6.add(r7)
        L_0x018b:
            int r4 = r4 + 1
            goto L_0x0104
        L_0x018f:
            int r2 = r2 + 1
            goto L_0x00f9
        L_0x0193:
            java.util.List r1 = r27.getListAgDestOBBean()
            if (r1 == 0) goto L_0x0232
            r1 = 0
        L_0x019a:
            java.util.List r2 = r27.getListAgDestOBBean()
            int r2 = r2.size()
            if (r1 >= r2) goto L_0x0232
            r2 = 0
        L_0x01a5:
            java.util.List r4 = r27.getListAgDestOBBean()
            java.lang.Object r4 = r4.get(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean r4 = (ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean) r4
            java.util.List r4 = r4.getListDatosCuentasDestOBBean()
            int r4 = r4.size()
            if (r2 >= r4) goto L_0x022e
            java.util.List r4 = r27.getListAgDestOBBean()
            java.lang.Object r4 = r4.get(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean r4 = (ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean) r4
            java.util.List r4 = r4.getListDatosCuentasDestOBBean()
            java.lang.Object r4 = r4.get(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r4 = (ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean) r4
            java.util.List r5 = r27.getListAgDestOBBean()
            java.lang.Object r5 = r5.get(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean) r5
            java.util.List r5 = r5.getListDatosCuentasDestOBBean()
            java.lang.Object r5 = r5.get(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r5 = (ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean) r5
            java.lang.String r5 = r5.getTipoDestino()
            java.lang.String r6 = "03"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x022a
            java.util.ArrayList<ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios> r5 = r8.bg
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r6 = new ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios
            java.lang.String r11 = r4.getNombreTitular()
            java.lang.String r12 = r4.getDescripcion()
            java.lang.String r7 = r4.getBanco()
            java.lang.String r13 = r7.toUpperCase()
            java.lang.String r14 = r4.getCbu()
            java.lang.Boolean r15 = java.lang.Boolean.valueOf(r3)
            java.lang.String r16 = ""
            java.lang.String r17 = r4.getEmail()
            java.lang.String r18 = r4.getTipoDestino()
            java.lang.String r19 = r4.getDescripcion()
            java.lang.String r20 = r4.getBanco()
            java.lang.String r21 = r4.getBeneficiario()
            java.lang.String r22 = r4.getAlias()
            r10 = r6
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            r5.add(r6)
        L_0x022a:
            int r2 = r2 + 1
            goto L_0x01a5
        L_0x022e:
            int r1 = r1 + 1
            goto L_0x019a
        L_0x0232:
            java.util.ArrayList<ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios> r0 = r8.bg
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$SortBasedOnName r1 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$SortBasedOnName
            r1.<init>()
            java.util.Collections.sort(r0, r1)
            java.lang.String r0 = r8.ba
            r1 = -1
            int r2 = r0.hashCode()
            r4 = -1941134401(0xffffffff8c4ca3bf, float:-1.5764864E-31)
            if (r2 == r4) goto L_0x0258
            r3 = -1072532104(0xffffffffc0127578, float:-2.2884197)
            if (r2 == r3) goto L_0x024e
            goto L_0x0261
        L_0x024e:
            java.lang.String r2 = "SINGLE_CHOICE"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0261
            r3 = 1
            goto L_0x0262
        L_0x0258:
            java.lang.String r2 = "ABM_ONLY"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0261
            goto L_0x0262
        L_0x0261:
            r3 = -1
        L_0x0262:
            r0 = 2131366000(0x7f0a1070, float:1.8351881E38)
            switch(r3) {
                case 0: goto L_0x029c;
                case 1: goto L_0x0269;
                default: goto L_0x0268;
            }
        L_0x0268:
            goto L_0x02d1
        L_0x0269:
            android.view.View r1 = r8.pantallaAgendaDestinatarios
            android.view.View r0 = r1.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 2131756178(0x7f100492, float:1.9143256E38)
            java.lang.String r1 = r8.getString(r1)
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter r10 = new ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter
            android.support.v4.app.FragmentActivity r1 = r23.getActivity()
            java.util.ArrayList<ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios> r2 = r8.bg
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r10
            r3 = r8
            r4 = r28
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r8.am = r10
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r0 = r8.d
            r1 = 2131757796(0x7f100ae4, float:1.9146538E38)
            java.lang.String r1 = r8.getString(r1)
            r0.trackScreen(r1)
            goto L_0x02d1
        L_0x029c:
            android.view.View r1 = r8.pantallaAgendaDestinatarios
            android.view.View r0 = r1.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 2131756130(0x7f100462, float:1.9143159E38)
            java.lang.String r1 = r8.getString(r1)
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter r10 = new ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter
            android.support.v4.app.FragmentActivity r1 = r23.getActivity()
            java.util.ArrayList<ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios> r2 = r8.bg
            android.view.View$OnTouchListener r5 = r8.gestureListItemListener
            ar.com.santander.rio.mbanking.components.OnSingleClickListener r6 = r8.g
            ar.com.santander.rio.mbanking.components.OnSingleClickListener r7 = r8.f
            r0 = r10
            r3 = r8
            r4 = r28
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r8.am = r10
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r0 = r8.d
            r1 = 2131757794(0x7f100ae2, float:1.9146534E38)
            java.lang.String r1 = r8.getString(r1)
            r0.trackScreen(r1)
        L_0x02d1:
            android.widget.ListView r0 = r8.listaDestinatarios
            ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter r1 = r8.am
            r0.setAdapter(r1)
            android.view.View r0 = r8.pantallaAgendaDestinatarios
            android.view.View r0 = r0.findViewById(r9)
            ar.com.santander.rio.mbanking.view.ClearableEditText r0 = (ar.com.santander.rio.mbanking.view.ClearableEditText) r0
            android.graphics.drawable.Drawable r0 = r0.imgClearButton
            android.content.res.Resources r1 = r23.getResources()
            r2 = 2131099764(0x7f060074, float:1.781189E38)
            int r1 = r1.getColor(r2)
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.SRC_IN
            r0.setColorFilter(r1, r2)
            android.view.View r0 = r8.pantallaAgendaDestinatarios
            android.view.View r0 = r0.findViewById(r9)
            android.widget.EditText r0 = (android.widget.EditText) r0
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$17 r1 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$17
            r1.<init>()
            r0.addTextChangedListener(r1)
            goto L_0x032f
        L_0x0303:
            java.lang.String r9 = "Error"
            java.lang.String r10 = r27.getMensajeAgendados()
            r11 = 0
            r2 = 2131755637(0x7f100275, float:1.9142159E38)
            java.lang.String r12 = r8.getString(r2)
            r13 = 0
            r14 = 0
            r15 = 0
            ar.com.santander.rio.mbanking.components.IsbanDialogFragment r2 = ar.com.santander.rio.mbanking.components.IsbanDialogFragment.newInstance(r9, r10, r11, r12, r13, r14, r15)
            ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$18 r3 = new ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment$18
            r4 = r28
            r3.<init>(r0, r1, r4)
            r2.setDialogListener(r3)
            android.support.v4.app.FragmentActivity r0 = r23.getActivity()
            android.support.v4.app.FragmentManager r0 = r0.getSupportFragmentManager()
            java.lang.String r1 = "Dialog"
            r2.show(r0, r1)
        L_0x032f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.setAgendaDestinatariosView(java.lang.String, java.lang.String, ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean, ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean, ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios, java.lang.String):void");
    }

    public void callVerificaDatosInicialesTransf(AgendaDestinatarios agendaDestinatarios) {
        if (agendaDestinatarios.getBanco().equalsIgnoreCase(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
            DatosCuentasDestBSRBean datosCuentasDestBSRBeanPara = getDatosCuentasDestBSRBeanPara(agendaDestinatarios.getInfo2());
            a(datosCuentasDestBSRBeanPara);
            VerificaDatosBean verificaDatosBean = new VerificaDatosBean(datosCuentasDestBSRBeanPara.getTipoDestino(), datosCuentasDestBSRBeanPara.getTipo(), datosCuentasDestBSRBeanPara.getSucursal(), datosCuentasDestBSRBeanPara.getNumero(), this.b.getLoginUnico().getDatosPersonales().getNroDocumento(), this.b.getLoginUnico().getDatosPersonales().getFechaNacimiento(), datosCuentasDestBSRBeanPara.getAlias());
            this.aP = ProgresIndicator.newInstance(VVerificaDatosInicialesTransf.nameService);
            showProgress();
            this.bb = accionVerificarDatosTransf.EDITAR;
            this.c.verificaDatosInicialesTransf(verificaDatosBean);
            return;
        }
        DatosCuentasDestOBBean datosCuentasDestOBBeanPara = getDatosCuentasDestOBBeanPara(agendaDestinatarios.getInfo2());
        VerificaDatosOBBean verificaDatosOBBean = new VerificaDatosOBBean(datosCuentasDestOBBeanPara.getTipoDestino(), this.aH.getTipo(), this.aH.getSucursal(), this.aH.getNumero(), this.b.getLoginUnico().getDatosPersonales().getNroDocumento(), this.b.getLoginUnico().getDatosPersonales().getFechaNacimiento(), (datosCuentasDestOBBeanPara.getAlias() != null || !datosCuentasDestOBBeanPara.getAlias().isEmpty()) ? datosCuentasDestOBBeanPara.getCbu() : null, datosCuentasDestOBBeanPara.getAlias());
        this.aP = ProgresIndicator.newInstance(VVerificaDatosInicialesTransf.nameService);
        showProgress();
        this.bb = accionVerificarDatosTransf.EDITAR;
        this.c.verificaDatosInicialesTransfOB(verificaDatosOBBean);
    }

    public void goToTerminosCondiciones(LeyendasBean leyendasBean) {
        this.d.trackScreen(getString(R.string.analytics_screen_name_terminos_condiciones));
        for (int i2 = 0; i2 < leyendasBean.getLeyendasBean().size(); i2++) {
            if (((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getIdLeyenda().equals("TRANSF_TYC")) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, ((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getDescripcion());
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, ((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getTitulo());
                startActivity(intent);
            }
        }
    }

    public void goToLegales(LeyendasBean leyendasBean) {
        this.d.trackScreen(getString(R.string.analytics_screen_name_legales));
        for (int i2 = 0; i2 < leyendasBean.getLeyendasBean().size(); i2++) {
            if (((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getIdLeyenda().equals("TRANSF_LEG")) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, ((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getDescripcion());
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, ((LeyendaBean) leyendasBean.getLeyendasBean().get(i2)).getTitulo());
                startActivity(intent);
            }
        }
    }

    public void showProgress() {
        super.showProgress("");
    }

    public void dismissProgress() {
        super.dismissProgress();
    }

    public IDataManager getDataManager() {
        return this.c;
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
                    if (!this.aQ) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.no_animation));
                        this.mControlPager.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down));
                        break;
                    }
                case 1:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToUpAnimation());
                    break;
                case 2:
                    if (!this.aQ) {
                        if (!this.aR) {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                            break;
                        } else {
                            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                            break;
                        }
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromUpAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToDownAnimation());
                        break;
                    }
                case 3:
                    this.mControlPager.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));
                    this.mControlPager.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.no_animation));
                    break;
                case 4:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToUpAnimation());
                    break;
                case 5:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToUpAnimation());
                    break;
                case 6:
                    if (!this.aR) {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                        break;
                    } else {
                        this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                        this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                        break;
                    }
            }
            this.aQ = false;
            this.aR = false;
            this.mControlPager.setDisplayedChild(i2);
        }
    }

    public int getIndexViewPage(View view) {
        return this.mControlPager.indexOfChild(view);
    }

    private void G() {
        View findViewById = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TransferenciasFragment.this.onBackPressed();
                }
            });
        }
    }

    private void H() {
        this.pantallaTransferencias.findViewById(R.id.linearLayoutAlias).setVisibility(8);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutTitularDivider).setVisibility(8);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutTitular).setVisibility(8);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutTipoCuenta).setVisibility(8);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutTipo).setVisibility(8);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutNumero).setVisibility(8);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutCUIT).setVisibility(8);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutBancoDestino).setVisibility(8);
        this.pantallaTransferencias.findViewById(R.id.linearLayoutCBU).setVisibility(8);
        enableContinueButton(Boolean.valueOf(false));
    }

    private void I() {
        this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutTipo).setVisibility(8);
        this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutNumero).setVisibility(8);
        this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutCUIT).setVisibility(8);
        this.pantallaConfirmacionTransferencia.findViewById(R.id.linearLayoutBancoDestino).setVisibility(8);
    }

    public void initializeComprobanteTransferencia() {
        this.pantallaComprobanteTransferencia.findViewById(R.id.linearLayoutCtaDestino).setVisibility(8);
        this.pantallaComprobanteTransferencia.findViewById(R.id.dividerCtaDestino).setVisibility(8);
        this.pantallaComprobanteTransferencia.findViewById(R.id.linearLayoutCBU).setVisibility(8);
        this.pantallaComprobanteTransferencia.findViewById(R.id.linearLayoutCUIT).setVisibility(8);
    }

    @Subscribe
    public void onConsultaDatosInicialesTransf(ConsultaDatosInicialesTransfEvent consultaDatosInicialesTransfEvent) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onConsultaDatosInicialesTransf ");
        sb.append(consultaDatosInicialesTransfEvent.getResult().toString());
        Log.d(str, sb.toString());
        dismissProgress();
        this.bi = false;
        this.ae = "N";
        if (consultaDatosInicialesTransfEvent.getResult() == TypeResult.OK) {
            this.aW = ((ConsultaDatosInicialesTransfResponseBean) consultaDatosInicialesTransfEvent.getBeanResponse()).getConsultaDatosInicialesTransfBodyResponseBean().getCargaInfoTransfBean();
            if (this.aS) {
                if (this.am != null) {
                    this.am.clearData();
                }
                this.aJ = this.aW.getAgendadosBean();
                this.b.setAgendaTransferencia(this.aJ);
                if (this.aT) {
                    onGoToAgendaDestinatarios("ABM_ONLY");
                    return;
                }
                return;
            }
            this.transferenciasView.setVisibility(0);
            ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewFechaEjecucion)).setText(UtilDate.getDateFormat2(this.aW.getFechaEjecucion()));
            this.aG = this.aW.getCuentasPropiasBean();
            this.aE = new ArrayList<>();
            for (int i2 = 0; i2 < this.aW.getCuentasPropiasBean().getListDatosCuentasBean().size(); i2++) {
                this.aE.add(formatMedioPago(((DatosCuentasBean) this.aW.getCuentasPropiasBean().getListDatosCuentasBean().get(i2)).getDescCtaDebito()));
            }
            if (this.b.getCuentaOrigenDebito().equals("")) {
                resetCuentaOrigenSeleccionada();
            } else {
                TextView textView = (TextView) this.pantallaTransferencias.findViewById(R.id.textViewCuentaOrigen);
                textView.setText(formatMedioPago(this.b.getCuentaOrigenDebito()));
                try {
                    textView.setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterGeneral(textView.getText().toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.ao = this.b.getCuentaOrigenDebito();
            }
            this.as = getValueConsDescription(this.aF, PRE_AUTORIZACIONES.MONEDADESCSIMBOLO, this.aH.getMoneda());
            this.aJ = this.aW.getAgendadosBean();
            this.b.setAgendaTransferencia(this.aJ);
            this.aI = this.aW.getLeyendasBean();
            this.pantallaTransferencias.findViewById(R.id.editTextImporte).requestFocus();
            comprobarDestinatarioSeleccionado();
        } else if (getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(consultaDatosInicialesTransfEvent, getTAG());
        }
    }

    @Subscribe
    public void onVerificaDatosInicialesTransf(VerificaDatosInicialesTransfEvent verificaDatosInicialesTransfEvent) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onVerificaDatosInicialesTransf ");
        sb.append(verificaDatosInicialesTransfEvent.getResult().toString());
        Log.d(str, sb.toString());
        dismissProgress();
        this.bi = false;
        this.ae = "N";
        if (verificaDatosInicialesTransfEvent.getResult() == TypeResult.OK) {
            VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean = ((VerificaDatosInicialesTransfOBResponseBean) verificaDatosInicialesTransfEvent.getBeanResponse()).getVerificaDatosInicialesTransfOBBodyResponseBean();
            this.bk = verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean();
            if (!this.bb.equalsIgnoreCase(accionVerificarDatosTransf.SELECCION)) {
                return;
            }
            if (verificaDatosInicialesTransfOBBodyResponseBean.resCod != null && !verificaDatosInicialesTransfOBBodyResponseBean.resCod.isEmpty()) {
                showChangedAliasError(verificaDatosInicialesTransfOBBodyResponseBean.resDesc, verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean());
            } else if (verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean() != null) {
                this.bk = verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean();
                showDialogConfirmationCtaMigrada((AppCompatActivity) this.bl, verificaDatosInicialesTransfOBBodyResponseBean);
            } else {
                goToTransferencias();
                enableContinueButton(Boolean.valueOf(true));
                String str2 = LOG_TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Seleccionar: ");
                sb2.append(this.aK.getDescripcion());
                Log.d(str2, sb2.toString());
                this.textViewSeleccionar.setText(Html.fromHtml(this.aK.getDescripcion()));
                try {
                    this.textViewSeleccionar.setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterGeneral(this.textViewSeleccionar.getText().toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                String str3 = LOG_TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Titular: ");
                sb3.append(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getTitular());
                Log.d(str3, sb3.toString());
                ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewTitular)).setText(Html.fromHtml(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getTitular()));
                if (this.af.getAlias() != null && !this.af.getAlias().isEmpty()) {
                    String str4 = LOG_TAG;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Alias: ");
                    sb4.append(this.af.getAlias());
                    Log.d(str4, sb4.toString());
                    ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewAlias)).setText(this.af.getAlias());
                }
                String str5 = LOG_TAG;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Tipo cuenta: ");
                sb5.append(this.ap);
                Log.d(str5, sb5.toString());
                ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewTipoCuenta)).setText(Html.fromHtml(this.ap));
                String str6 = LOG_TAG;
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Tipo: descripción ");
                sb6.append(this.aK.getTipoDescripcion());
                Log.d(str6, sb6.toString());
                ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewTipo)).setText(Html.fromHtml(this.aK.getTipoDescripcion()));
                String str7 = LOG_TAG;
                StringBuilder sb7 = new StringBuilder();
                sb7.append("Numero: ");
                sb7.append(this.aK.getSucursal());
                sb7.append("-");
                sb7.append(this.aK.getNumero().substring(0, 6));
                sb7.append("/");
                sb7.append(this.aK.getNumero().substring(6, 7));
                Log.d(str7, sb7.toString());
                TextView textView = (TextView) this.pantallaTransferencias.findViewById(R.id.textViewNumero);
                StringBuilder sb8 = new StringBuilder();
                sb8.append(this.aK.getSucursal());
                sb8.append("-");
                sb8.append(this.aK.getNumero().substring(0, 6));
                sb8.append("/");
                sb8.append(this.aK.getNumero().substring(6, 7));
                textView.setText(sb8.toString());
                try {
                    textView.setContentDescription(new CAccessibility(getActivity().getApplicationContext()).applyFilterCharacterToCharacter(textView.getText().toString()));
                } catch (Exception unused) {
                }
                ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewPlazoAcreditacion)).setText(getString(R.string.IDXX_TRANSFERENCE_LBL_PLAZO_ACREDITACION_ACTO));
                if (!TextUtils.isEmpty(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getCbu())) {
                    ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewCBU)).setText(Html.fromHtml(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getCbu()));
                }
                this.at = verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getBancoDestino();
                if (!TextUtils.isEmpty(this.at)) {
                    ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewBancoDestino)).setText(Html.fromHtml(this.at));
                }
                this.au = verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getNumeroCuil();
                if (!TextUtils.isEmpty(this.au)) {
                    ((TextView) this.pantallaTransferencias.findViewById(R.id.textViewCUIT)).setText(Html.fromHtml(this.au));
                }
                try {
                    this.pantallaTransferencias.findViewById(R.id.textViewCUIT).setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterCharacterToCharacter(((TextView) this.pantallaTransferencias.findViewById(R.id.textViewCUIT)).getText().toString()));
                    this.pantallaTransferencias.findViewById(R.id.textViewBancoDestino).setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterGeneral(((TextView) this.pantallaTransferencias.findViewById(R.id.textViewBancoDestino)).getText().toString()));
                    this.pantallaTransferencias.findViewById(R.id.textViewCBU).setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterCharacterToCharacter(((TextView) this.pantallaTransferencias.findViewById(R.id.textViewCBU)).getText().toString()));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        } else if (this.bb.equalsIgnoreCase(accionVerificarDatosTransf.SELECCION)) {
            J();
            if (getErrorListener() != null) {
                getErrorListener().onWebServiceErrorEvent(verificaDatosInicialesTransfEvent, getTAG());
            }
        } else if (this.bb.equalsIgnoreCase(accionVerificarDatosTransf.EDITAR) && getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(verificaDatosInicialesTransfEvent, getTAG());
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:12|(1:16)|17|(2:18|19)|22|(1:27)(1:26)|28|29|30|31|51) */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x021d */
    @com.squareup.otto.Subscribe
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onVerificaDatosInicialesTransfOB(ar.com.santander.rio.mbanking.services.events.VerificaDatosInicialesTransfOBEvent r7) {
        /*
            r6 = this;
            r6.dismissProgress()
            r0 = 0
            r6.bi = r0
            java.lang.String r0 = "N"
            r6.ae = r0
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r0 = r7.getResult()
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r1 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.OK
            if (r0 != r1) goto L_0x0234
            com.indra.httpclient.beans.IBeanWS r7 = r7.getBeanResponse()
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r7 = (ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean) r7
            r6.aN = r7
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r7 = r6.aN
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean r7 = r7.getVerificaDatosInicialesTransfOBBodyResponseBean()
            java.lang.String r0 = r7.resCod
            if (r0 == 0) goto L_0x0037
            java.lang.String r0 = r7.resCod
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0037
            java.lang.String r0 = r7.resDesc
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r7 = r7.getVerificaDatosSalidaOBBean()
            r6.showChangedAliasError(r0, r7)
            goto L_0x026e
        L_0x0037:
            r6.goToTransferencias()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r0 = r6.aL
            if (r0 == 0) goto L_0x004b
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r0 = r6.aL
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r1 = r1.getDiferido()
            r0.setDiferido(r1)
        L_0x004b:
            java.lang.String r0 = r6.bb
            java.lang.String r1 = "SELECCION_DEST"
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x026e
            r0 = 1
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r6.enableContinueButton(r0)
            android.widget.TextView r0 = r6.textViewSeleccionar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r1 = r6.aL
            java.lang.String r1 = r1.getDescripcion()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.view.View r0 = r6.pantallaTransferencias
            r1 = 2131366009(0x7f0a1079, float:1.83519E38)
            android.view.View r0 = r0.findViewById(r1)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r1 = r1.getTitular()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.af
            java.lang.String r0 = r0.getAlias()
            if (r0 == 0) goto L_0x00ae
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r6.af
            java.lang.String r0 = r0.getAlias()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x00ae
            android.view.View r0 = r6.pantallaTransferencias
            r1 = 2131365879(0x7f0a0ff7, float:1.8351636E38)
            android.view.View r0 = r0.findViewById(r1)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = r6.af
            java.lang.String r1 = r1.getAlias()
            r0.setText(r1)
        L_0x00ae:
            android.view.View r0 = r6.pantallaTransferencias
            r1 = 2131365894(0x7f0a1006, float:1.8351666E38)
            android.view.View r0 = r0.findViewById(r1)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r2 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r2 = r2.getNumeroCuil()
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)
            r0.setText(r2)
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getNumeroCuil()
            r6.au = r0
            android.view.View r0 = r6.pantallaTransferencias
            r2 = 2131365885(0x7f0a0ffd, float:1.8351648E38)
            android.view.View r0 = r0.findViewById(r2)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r3 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r3 = r3.getBancoDestino()
            android.text.Spanned r3 = android.text.Html.fromHtml(r3)
            r0.setText(r3)
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getBancoDestino()
            r6.at = r0
            android.view.View r0 = r6.pantallaTransferencias
            r3 = 2131365889(0x7f0a1001, float:1.8351656E38)
            android.view.View r0 = r0.findViewById(r3)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r4 = r6.aL
            java.lang.String r4 = r4.getCbu()
            android.text.Spanned r4 = android.text.Html.fromHtml(r4)
            r0.setText(r4)
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getTipoCtaToBane()
            r6.av = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getTipoCtaFromBane()
            r6.aw = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getBancoReceptor()
            r6.ax = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getFiid()
            r6.ay = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getUser()
            r6.az = r0
            android.view.View r0 = r6.pantallaTransferencias     // Catch:{ Exception -> 0x01bc }
            android.view.View r0 = r0.findViewById(r1)     // Catch:{ Exception -> 0x01bc }
            android.support.v4.app.FragmentActivity r4 = r6.getActivity()     // Catch:{ Exception -> 0x01bc }
            android.content.Context r4 = r4.getApplicationContext()     // Catch:{ Exception -> 0x01bc }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r4 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r4)     // Catch:{ Exception -> 0x01bc }
            android.view.View r5 = r6.pantallaTransferencias     // Catch:{ Exception -> 0x01bc }
            android.view.View r1 = r5.findViewById(r1)     // Catch:{ Exception -> 0x01bc }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x01bc }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x01bc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x01bc }
            java.lang.String r1 = r4.applyFilterCharacterToCharacter(r1)     // Catch:{ Exception -> 0x01bc }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x01bc }
            android.view.View r0 = r6.pantallaTransferencias     // Catch:{ Exception -> 0x01bc }
            android.view.View r0 = r0.findViewById(r2)     // Catch:{ Exception -> 0x01bc }
            android.support.v4.app.FragmentActivity r1 = r6.getActivity()     // Catch:{ Exception -> 0x01bc }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x01bc }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x01bc }
            android.view.View r4 = r6.pantallaTransferencias     // Catch:{ Exception -> 0x01bc }
            android.view.View r2 = r4.findViewById(r2)     // Catch:{ Exception -> 0x01bc }
            android.widget.TextView r2 = (android.widget.TextView) r2     // Catch:{ Exception -> 0x01bc }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x01bc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x01bc }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x01bc }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x01bc }
            android.view.View r0 = r6.pantallaTransferencias     // Catch:{ Exception -> 0x01bc }
            android.view.View r0 = r0.findViewById(r3)     // Catch:{ Exception -> 0x01bc }
            android.support.v4.app.FragmentActivity r1 = r6.getActivity()     // Catch:{ Exception -> 0x01bc }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x01bc }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x01bc }
            android.view.View r2 = r6.pantallaTransferencias     // Catch:{ Exception -> 0x01bc }
            android.view.View r2 = r2.findViewById(r3)     // Catch:{ Exception -> 0x01bc }
            android.widget.TextView r2 = (android.widget.TextView) r2     // Catch:{ Exception -> 0x01bc }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x01bc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x01bc }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x01bc }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x01bc }
            goto L_0x01c0
        L_0x01bc:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01c0:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getDiferido()
            r1 = 2131756505(0x7f1005d9, float:1.914392E38)
            r2 = 2131365996(0x7f0a106c, float:1.8351873E38)
            if (r0 == 0) goto L_0x01f0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r7.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getDiferido()
            java.lang.String r3 = "1"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x01f0
            android.view.View r0 = r6.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r2)
            android.widget.TextView r0 = (android.widget.TextView) r0
            java.lang.String r1 = r6.getString(r1)
            r0.setText(r1)
            goto L_0x01ff
        L_0x01f0:
            android.view.View r0 = r6.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r2)
            android.widget.TextView r0 = (android.widget.TextView) r0
            java.lang.String r1 = r6.getString(r1)
            r0.setText(r1)
        L_0x01ff:
            android.view.View r0 = r6.pantallaTransferencias     // Catch:{ Exception -> 0x021d }
            r1 = 2131366003(0x7f0a1073, float:1.8351887E38)
            android.view.View r0 = r0.findViewById(r1)     // Catch:{ Exception -> 0x021d }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x021d }
            android.support.v4.app.FragmentActivity r1 = r6.getActivity()     // Catch:{ Exception -> 0x021d }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r2 = r7.getVerificaDatosSalidaOBBean()     // Catch:{ Exception -> 0x021d }
            java.lang.String r2 = r2.getTipoCtaToBane()     // Catch:{ Exception -> 0x021d }
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilAccount.getOBAccountTypeDescription(r1, r2)     // Catch:{ Exception -> 0x021d }
            r0.setText(r1)     // Catch:{ Exception -> 0x021d }
        L_0x021d:
            android.view.View r0 = r6.pantallaTransferencias     // Catch:{ Exception -> 0x026e }
            r1 = 2131365986(0x7f0a1062, float:1.8351853E38)
            android.view.View r0 = r0.findViewById(r1)     // Catch:{ Exception -> 0x026e }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x026e }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r7 = r7.getVerificaDatosSalidaOBBean()     // Catch:{ Exception -> 0x026e }
            java.lang.String r7 = r7.getNumero()     // Catch:{ Exception -> 0x026e }
            r0.setText(r7)     // Catch:{ Exception -> 0x026e }
            goto L_0x026e
        L_0x0234:
            java.lang.String r0 = r6.bb
            java.lang.String r1 = "SELECCION_DEST"
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x0253
            r6.J()
            ar.com.santander.rio.mbanking.app.base.IErrorListener r0 = r6.getErrorListener()
            if (r0 == 0) goto L_0x026e
            ar.com.santander.rio.mbanking.app.base.IErrorListener r0 = r6.getErrorListener()
            java.lang.String r1 = r6.getTAG()
            r0.onWebServiceErrorEvent(r7, r1)
            goto L_0x026e
        L_0x0253:
            java.lang.String r0 = r6.bb
            java.lang.String r1 = "EDITAR_DEST"
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x026e
            ar.com.santander.rio.mbanking.app.base.IErrorListener r0 = r6.getErrorListener()
            if (r0 == 0) goto L_0x026e
            ar.com.santander.rio.mbanking.app.base.IErrorListener r0 = r6.getErrorListener()
            java.lang.String r1 = r6.getTAG()
            r0.onWebServiceErrorEvent(r7, r1)
        L_0x026e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.onVerificaDatosInicialesTransfOB(ar.com.santander.rio.mbanking.services.events.VerificaDatosInicialesTransfOBEvent):void");
    }

    /* access modifiers changed from: private */
    public void J() {
        try {
            if (this.af != null) {
                this.af.setChecked(false);
                View childAt = this.listaDestinatarios.getChildAt(this.af.getPosition());
                if (childAt != null) {
                    ImageView imageView = (ImageView) childAt.findViewById(R.id.imageViewCheck);
                    if (imageView != null) {
                        this.am.updateCheckBox(imageView, false);
                    }
                }
            }
            resetDestinatarioSeleccionado();
            this.am.updateAdapterChecked();
            this.am.notifyDataSetChanged();
            enableContinueButton(Boolean.valueOf(false));
            B();
        } catch (Exception unused) {
        }
    }

    @Subscribe
    public void onTransferencias(TransferenciasEvent transferenciasEvent) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onTransferencias ");
        sb.append(transferenciasEvent.getResult().toString());
        Log.i(str, sb.toString());
        dismissProgress();
        this.bi = false;
        if (transferenciasEvent.getResult() == TypeResult.OK) {
            DatosTransferenciaSalidaBean datosTransferenciaSalidaBean = ((TransferenciasResponseBean) transferenciasEvent.getBeanResponse()).getTransferenciasBodyResponseBean().getDatosTransferenciaSalidaBean();
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.aV);
            arrayList.add(((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewCuentaOrigen)).getText().toString());
            if (datosTransferenciaSalidaBean == null || datosTransferenciaSalidaBean.getCtaDestino() == null) {
                arrayList.add("");
            } else {
                arrayList.add(datosTransferenciaSalidaBean.getCtaDestino());
            }
            arrayList.add(((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewTitular)).getText().toString());
            arrayList.add(this.aU);
            arrayList.add(((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewPlazoAcreditacion)).getText().toString());
            arrayList.add(((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewConcepto)).getText().toString());
            arrayList.add(((TextView) this.pantallaConfirmacionTransferencia.findViewById(R.id.textViewReferenciaConcepto)).getText().toString());
            goToComprobanteTransferencia(arrayList, this.aH, this.aM, this.aK, datosTransferenciaSalidaBean, this.aI);
        } else if (getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(transferenciasEvent, getTAG());
        }
    }

    public void showActionShareReceipt(String str, String str2) {
        final String str3 = str2;
        final String str4 = str;
        AnonymousClass20 r0 = new OptionsToShareImpl(this, getActivity().getApplicationContext(), getActivity().getSupportFragmentManager()) {
            public void optionDownloadSelected() {
                TransferenciasFragment.this.bd = true;
                super.optionDownloadSelected();
            }

            public void optionShareSelected() {
                TransferenciasFragment.this.bd = true;
                super.optionShareSelected();
            }

            public View getViewToShare() {
                return TransferenciasFragment.this.comprobanteTransferencia;
            }

            public void receiveIntentAppShare(Intent intent) {
                TransferenciasFragment.this.startActivityForResult(Intent.createChooser(intent, TransferenciasFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }
        };
        this.bf = r0;
        this.bf.show(getActivity().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL));
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (activityResultHandler(i3, intent, FragmentConstants.TRANSFERENCIAS)) {
            return;
        }
        if (i2 != 666) {
            if (i2 != 777) {
                if (i2 != 888) {
                    if (i2 != 999) {
                        super.onActivityResult(i2, i3, intent);
                    } else {
                        UtilFile.deleteDirectory(new File(UtilFile.getPathReceiptTmp()));
                    }
                } else if (i3 != -1) {
                } else {
                    if (intent == null || !intent.hasExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO)) {
                        goToAgendaFromABMResult();
                        return;
                    }
                    goToTransferenciasFromABMResult(intent);
                    if (Boolean.valueOf(intent.getBooleanExtra(TransferenciasConstants.cINTENT_EXTRA_EJECUTAR_ALTA, false)).booleanValue()) {
                        this.ae = "S";
                    } else {
                        this.ae = "N";
                        callConsultaDatosInicialesTransf();
                    }
                    setContinueButtonState();
                }
            } else if (i3 == -1) {
                goToAgendaFromABMResult();
                this.aY = null;
                resetDestinatarioSeleccionado();
            }
        } else if (i3 != -1) {
        } else {
            if (intent == null || !intent.hasExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO)) {
                goToAgendaFromABMResult();
                return;
            }
            goToTransferenciasFromABMResult(intent);
            callConsultaDatosInicialesTransf();
            this.ae = "N";
            setContinueButtonState();
            if (intent.hasExtra(TransferenciasConstants.cINTENT_EXTRA_EJECUTAR_REASIGNACION)) {
                VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean = (VerificaDatosInicialesTransfOBBodyResponseBean) intent.getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_VERIFICA_DATOS_TRANSF);
                String stringExtra = intent.getStringExtra(TransferenciasConstants.cINTENT_EXTRA_EJECUTAR_REASIGNACION);
                if (TextUtils.isEmpty(stringExtra)) {
                    stringExtra = "";
                }
                showChangedAliasError(stringExtra, verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean());
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:50|(1:52)|53|(1:57)|58|59|60|61|62|63|(1:65)) */
    /* JADX WARNING: Can't wrap try/catch for region: R(17:13|(1:15)|16|(3:18|19|20)|23|(3:25|26|27)|30|(1:32)|33|(1:37)|38|39|40|41|42|(1:45)|(2:46|47)) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x01fe */
    /* JADX WARNING: Missing exception handler attribute for start block: B:60:0x02e2 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:62:0x02f9 */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x021f  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x030b A[Catch:{ Exception -> 0x0352 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void goToTransferenciasFromABMResult(android.content.Intent r10) {
        /*
            r9 = this;
            r9.resetDestinatarioSeleccionado()
            r0 = 1
            r9.aS = r0
            r1 = 0
            r9.aT = r1
            java.lang.String r1 = "DESTINATARIO"
            android.os.Parcelable r1 = r10.getParcelableExtra(r1)
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = (ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios) r1
            r9.af = r1
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = r9.af
            r1.setChecked(r0)
            java.lang.String r0 = "DESTINATARIOBSRBEAN"
            android.os.Parcelable r0 = r10.getParcelableExtra(r0)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = (ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean) r0
            r9.aK = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = r9.aK
            if (r0 == 0) goto L_0x0079
            java.lang.String r0 = r9.ad
            java.lang.String r1 = "USD"
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x0079
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = r9.aK
            java.lang.String r0 = r0.getTipo()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants.TIPO_CUENTA_CU_PESOS
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 != 0) goto L_0x004c
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = r9.aK
            java.lang.String r0 = r0.getTipo()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants.TIPO_CUENTA_CU
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x0079
        L_0x004c:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = r9.aK
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants.TIPO_CUENTA_CU_DOLAR
            r0.setTipo(r1)
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r9.af
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r9.b
            ar.com.santander.rio.mbanking.app.commons.CAccounts r1 = ar.com.santander.rio.mbanking.app.commons.CAccounts.getInstance(r1)
            ar.com.santander.rio.mbanking.services.model.general.Cuenta r2 = new ar.com.santander.rio.mbanking.services.model.general.Cuenta
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r3 = r9.aK
            java.lang.String r3 = r3.getTipo()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r4 = r9.aK
            java.lang.String r4 = r4.getNumero()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r5 = r9.aK
            java.lang.String r5 = r5.getSucursal()
            r2.<init>(r3, r4, r5)
            java.lang.String r1 = r1.getAbrevAccount(r2)
            r0.setInfo2(r1)
        L_0x0079:
            java.lang.String r0 = "DESTINATARIOOBBEAN"
            android.os.Parcelable r0 = r10.getParcelableExtra(r0)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r0 = (ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean) r0
            r9.aL = r0
            java.lang.String r0 = "VERIFICA_DATOS_TRANSF"
            boolean r0 = r10.hasExtra(r0)
            r1 = 0
            r2 = 2131365986(0x7f0a1062, float:1.8351853E38)
            r3 = 2131366003(0x7f0a1073, float:1.8351887E38)
            r4 = 2131365889(0x7f0a1001, float:1.8351656E38)
            if (r0 == 0) goto L_0x0350
            java.lang.String r0 = "VERIFICA_DATOS_TRANSF"
            android.os.Parcelable r0 = r10.getParcelableExtra(r0)
            boolean r0 = r0 instanceof ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean
            r5 = 2131365879(0x7f0a0ff7, float:1.8351636E38)
            r6 = 2131366009(0x7f0a1079, float:1.83519E38)
            if (r0 == 0) goto L_0x0267
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r0 = r9.aN
            if (r0 != 0) goto L_0x00b0
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r0 = new ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean
            r0.<init>()
            r9.aN = r0
        L_0x00b0:
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r0 = r9.aN
            java.lang.String r1 = "VERIFICA_DATOS_TRANSF"
            android.os.Parcelable r10 = r10.getParcelableExtra(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean r10 = (ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean) r10
            r0.setVerificaDatosInicialesTransfOBBodyResponseBean(r10)
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r10 = r9.aN
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean r10 = r10.getVerificaDatosInicialesTransfOBBodyResponseBean()
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getNumeroCuil()
            r9.au = r0
            java.lang.String r0 = r9.au
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0117
            android.view.View r0 = r9.pantallaTransferencias
            r1 = 2131365894(0x7f0a1006, float:1.8351666E38)
            android.view.View r0 = r0.findViewById(r1)
            android.widget.TextView r0 = (android.widget.TextView) r0
            java.lang.String r7 = r9.au
            android.text.Spanned r7 = android.text.Html.fromHtml(r7)
            r0.setText(r7)
            android.view.View r0 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0113 }
            android.view.View r0 = r0.findViewById(r1)     // Catch:{ Exception -> 0x0113 }
            android.support.v4.app.FragmentActivity r7 = r9.getActivity()     // Catch:{ Exception -> 0x0113 }
            android.content.Context r7 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0113 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r7 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r7)     // Catch:{ Exception -> 0x0113 }
            android.view.View r8 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0113 }
            android.view.View r1 = r8.findViewById(r1)     // Catch:{ Exception -> 0x0113 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x0113 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0113 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0113 }
            java.lang.String r1 = r7.applyFilterCharacterToCharacter(r1)     // Catch:{ Exception -> 0x0113 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0113 }
            goto L_0x0117
        L_0x0113:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0117:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getBancoDestino()
            r9.at = r0
            java.lang.String r0 = r9.at
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x016b
            android.view.View r0 = r9.pantallaTransferencias
            r1 = 2131365885(0x7f0a0ffd, float:1.8351648E38)
            android.view.View r0 = r0.findViewById(r1)
            android.widget.TextView r0 = (android.widget.TextView) r0
            java.lang.String r7 = r9.at
            android.text.Spanned r7 = android.text.Html.fromHtml(r7)
            r0.setText(r7)
            android.view.View r0 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0167 }
            android.view.View r0 = r0.findViewById(r1)     // Catch:{ Exception -> 0x0167 }
            android.support.v4.app.FragmentActivity r7 = r9.getActivity()     // Catch:{ Exception -> 0x0167 }
            android.content.Context r7 = r7.getApplicationContext()     // Catch:{ Exception -> 0x0167 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r7 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r7)     // Catch:{ Exception -> 0x0167 }
            android.view.View r8 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0167 }
            android.view.View r1 = r8.findViewById(r1)     // Catch:{ Exception -> 0x0167 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x0167 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0167 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0167 }
            java.lang.String r1 = r7.applyFilterGeneral(r1)     // Catch:{ Exception -> 0x0167 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0167 }
            goto L_0x016b
        L_0x0167:
            r0 = move-exception
            r0.printStackTrace()
        L_0x016b:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getTipoCtaToBane()
            r9.av = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getTipoCtaFromBane()
            r9.aw = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getBancoReceptor()
            r9.ax = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getFiid()
            r9.ay = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getUser()
            r9.az = r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getTitular()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x01be
            android.view.View r0 = r9.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r6)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r1 = r1.getTitular()
            r0.setText(r1)
        L_0x01be:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r9.af
            java.lang.String r0 = r0.getAlias()
            if (r0 == 0) goto L_0x01e3
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r9.af
            java.lang.String r0 = r0.getAlias()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x01e3
            android.view.View r0 = r9.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r5)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = r9.af
            java.lang.String r1 = r1.getAlias()
            r0.setText(r1)
        L_0x01e3:
            android.view.View r0 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x01fe }
            android.view.View r0 = r0.findViewById(r3)     // Catch:{ Exception -> 0x01fe }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x01fe }
            android.support.v4.app.FragmentActivity r1 = r9.getActivity()     // Catch:{ Exception -> 0x01fe }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r5 = r10.getVerificaDatosSalidaOBBean()     // Catch:{ Exception -> 0x01fe }
            java.lang.String r5 = r5.getTipoCtaToBane()     // Catch:{ Exception -> 0x01fe }
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilAccount.getOBAccountTypeDescription(r1, r5)     // Catch:{ Exception -> 0x01fe }
            r0.setText(r1)     // Catch:{ Exception -> 0x01fe }
        L_0x01fe:
            android.view.View r0 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0211 }
            android.view.View r0 = r0.findViewById(r2)     // Catch:{ Exception -> 0x0211 }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x0211 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r10.getVerificaDatosSalidaOBBean()     // Catch:{ Exception -> 0x0211 }
            java.lang.String r1 = r1.getNumero()     // Catch:{ Exception -> 0x0211 }
            r0.setText(r1)     // Catch:{ Exception -> 0x0211 }
        L_0x0211:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getCbu()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0236
            android.view.View r0 = r9.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r4)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r10 = r10.getVerificaDatosSalidaOBBean()
            java.lang.String r10 = r10.getCbu()
            android.text.Spanned r10 = android.text.Html.fromHtml(r10)
            r0.setText(r10)
        L_0x0236:
            android.view.View r10 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0261 }
            android.view.View r10 = r10.findViewById(r4)     // Catch:{ Exception -> 0x0261 }
            android.support.v4.app.FragmentActivity r0 = r9.getActivity()     // Catch:{ Exception -> 0x0261 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x0261 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x0261 }
            android.view.View r1 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0261 }
            android.view.View r1 = r1.findViewById(r4)     // Catch:{ Exception -> 0x0261 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x0261 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0261 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0261 }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r1)     // Catch:{ Exception -> 0x0261 }
            r10.setContentDescription(r0)     // Catch:{ Exception -> 0x0261 }
            goto L_0x0352
        L_0x0261:
            r10 = move-exception
            r10.printStackTrace()
            goto L_0x0352
        L_0x0267:
            r9.aN = r1
            java.lang.String r0 = "VERIFICA_DATOS_TRANSF"
            android.os.Parcelable r10 = r10.getParcelableExtra(r0)
            ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean r10 = (ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean) r10
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean r0 = r10.getVerificaDatosInicialesTransfOBBodyResponseBean()
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r0.getVerificaDatosSalidaOBBean()
            java.lang.String r0 = r0.getTitular()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x029e
            android.view.View r0 = r9.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r6)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean r1 = r10.getVerificaDatosInicialesTransfOBBodyResponseBean()
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r1.getVerificaDatosSalidaOBBean()
            java.lang.String r1 = r1.getTitular()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
        L_0x029e:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r9.af
            java.lang.String r0 = r0.getAlias()
            if (r0 == 0) goto L_0x02c3
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r9.af
            java.lang.String r0 = r0.getAlias()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x02c3
            android.view.View r0 = r9.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r5)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r1 = r9.af
            java.lang.String r1 = r1.getAlias()
            r0.setText(r1)
        L_0x02c3:
            android.view.View r0 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x02e2 }
            android.view.View r0 = r0.findViewById(r3)     // Catch:{ Exception -> 0x02e2 }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x02e2 }
            android.support.v4.app.FragmentActivity r1 = r9.getActivity()     // Catch:{ Exception -> 0x02e2 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean r5 = r10.getVerificaDatosInicialesTransfOBBodyResponseBean()     // Catch:{ Exception -> 0x02e2 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r5 = r5.getVerificaDatosSalidaOBBean()     // Catch:{ Exception -> 0x02e2 }
            java.lang.String r5 = r5.getTipoCtaToBane()     // Catch:{ Exception -> 0x02e2 }
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilAccount.getOBAccountTypeDescription(r1, r5)     // Catch:{ Exception -> 0x02e2 }
            r0.setText(r1)     // Catch:{ Exception -> 0x02e2 }
        L_0x02e2:
            android.view.View r0 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x02f9 }
            android.view.View r0 = r0.findViewById(r2)     // Catch:{ Exception -> 0x02f9 }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x02f9 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean r1 = r10.getVerificaDatosInicialesTransfOBBodyResponseBean()     // Catch:{ Exception -> 0x02f9 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r1 = r1.getVerificaDatosSalidaOBBean()     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = r1.getNumero()     // Catch:{ Exception -> 0x02f9 }
            r0.setText(r1)     // Catch:{ Exception -> 0x02f9 }
        L_0x02f9:
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean r0 = r10.getVerificaDatosInicialesTransfOBBodyResponseBean()     // Catch:{ Exception -> 0x0352 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r0 = r0.getVerificaDatosSalidaOBBean()     // Catch:{ Exception -> 0x0352 }
            java.lang.String r0 = r0.getCbu()     // Catch:{ Exception -> 0x0352 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0352 }
            if (r0 != 0) goto L_0x0352
            android.view.View r0 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0352 }
            android.view.View r0 = r0.findViewById(r4)     // Catch:{ Exception -> 0x0352 }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x0352 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean r10 = r10.getVerificaDatosInicialesTransfOBBodyResponseBean()     // Catch:{ Exception -> 0x0352 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean r10 = r10.getVerificaDatosSalidaOBBean()     // Catch:{ Exception -> 0x0352 }
            java.lang.String r10 = r10.getCbu()     // Catch:{ Exception -> 0x0352 }
            android.text.Spanned r10 = android.text.Html.fromHtml(r10)     // Catch:{ Exception -> 0x0352 }
            r0.setText(r10)     // Catch:{ Exception -> 0x0352 }
            android.view.View r10 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0352 }
            android.view.View r10 = r10.findViewById(r4)     // Catch:{ Exception -> 0x0352 }
            android.support.v4.app.FragmentActivity r0 = r9.getActivity()     // Catch:{ Exception -> 0x0352 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x0352 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x0352 }
            android.view.View r1 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0352 }
            android.view.View r1 = r1.findViewById(r4)     // Catch:{ Exception -> 0x0352 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x0352 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0352 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0352 }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r1)     // Catch:{ Exception -> 0x0352 }
            r10.setContentDescription(r0)     // Catch:{ Exception -> 0x0352 }
            goto L_0x0352
        L_0x0350:
            r9.aN = r1
        L_0x0352:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r10 = r9.aK
            if (r10 == 0) goto L_0x0426
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r10 = r9.aK
            java.lang.String r10 = r10.getTipoDestino()
            java.lang.String r0 = "04"
            boolean r10 = r10.equalsIgnoreCase(r0)
            if (r10 == 0) goto L_0x036b
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r10 = r9.aK
            java.lang.String r0 = "02"
            r10.setTipoDestino(r0)
        L_0x036b:
            android.widget.TextView r10 = r9.textViewSeleccionar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = r9.aK
            java.lang.String r0 = r0.getDescripcion()
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)
            r10.setText(r0)
            java.lang.String r10 = "Terceros Santander"
            r9.aV = r10
            java.lang.String r10 = "Terceros Santander"
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = r9.aK
            java.lang.String r0 = r0.getTipoDestino()
            r9.f243ar = r0
            android.view.View r0 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x039d }
            android.view.View r0 = r0.findViewById(r3)     // Catch:{ Exception -> 0x039d }
            android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x039d }
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r1 = r9.aK     // Catch:{ Exception -> 0x039d }
            java.lang.String r1 = r1.getTipoDescripcion()     // Catch:{ Exception -> 0x039d }
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)     // Catch:{ Exception -> 0x039d }
            r0.setText(r1)     // Catch:{ Exception -> 0x039d }
        L_0x039d:
            android.view.View r0 = r9.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r2)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r1 = r9.aK
            java.lang.String r1 = r1.getSucursal()
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r2 = r9.aK
            java.lang.String r2 = r2.getNumero()
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilAccount.getAccountFormat(r1, r2)
            r0.setText(r1)
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = new ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility     // Catch:{ Exception -> 0x03d4 }
            android.support.v4.app.FragmentActivity r2 = r9.getActivity()     // Catch:{ Exception -> 0x03d4 }
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ Exception -> 0x03d4 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x03d4 }
            java.lang.CharSequence r2 = r0.getText()     // Catch:{ Exception -> 0x03d4 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03d4 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x03d4 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x03d4 }
        L_0x03d4:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r0 = r9.aK
            java.lang.String r0 = r0.getCbuDestino()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x03f5
            android.view.View r0 = r9.pantallaTransferencias
            android.view.View r0 = r0.findViewById(r4)
            android.widget.TextView r0 = (android.widget.TextView) r0
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean r1 = r9.aK
            java.lang.String r1 = r1.getCbuDestino()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
        L_0x03f5:
            android.view.View r0 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0420 }
            android.view.View r0 = r0.findViewById(r4)     // Catch:{ Exception -> 0x0420 }
            android.support.v4.app.FragmentActivity r1 = r9.getActivity()     // Catch:{ Exception -> 0x0420 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x0420 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0420 }
            android.view.View r2 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0420 }
            android.view.View r2 = r2.findViewById(r4)     // Catch:{ Exception -> 0x0420 }
            android.widget.TextView r2 = (android.widget.TextView) r2     // Catch:{ Exception -> 0x0420 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0420 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0420 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x0420 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0420 }
            goto L_0x0512
        L_0x0420:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0512
        L_0x0426:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r10 = r9.aL
            java.lang.String r10 = r10.getTipoDestino()
            java.lang.String r0 = "05"
            boolean r10 = r10.equalsIgnoreCase(r0)
            if (r10 == 0) goto L_0x043b
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r10 = r9.aL
            java.lang.String r0 = "03"
            r10.setTipoDestino(r0)
        L_0x043b:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r10 = r9.aL
            java.lang.String r10 = r10.getCbu()
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 != 0) goto L_0x045c
            android.view.View r10 = r9.pantallaTransferencias
            android.view.View r10 = r10.findViewById(r4)
            android.widget.TextView r10 = (android.widget.TextView) r10
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r0 = r9.aL
            java.lang.String r0 = r0.getCbu()
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)
            r10.setText(r0)
        L_0x045c:
            android.view.View r10 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0486 }
            android.view.View r10 = r10.findViewById(r4)     // Catch:{ Exception -> 0x0486 }
            android.support.v4.app.FragmentActivity r0 = r9.getActivity()     // Catch:{ Exception -> 0x0486 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x0486 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x0486 }
            android.view.View r1 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x0486 }
            android.view.View r1 = r1.findViewById(r4)     // Catch:{ Exception -> 0x0486 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x0486 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0486 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0486 }
            java.lang.String r0 = r0.applyFilterCharacterToCharacter(r1)     // Catch:{ Exception -> 0x0486 }
            r10.setContentDescription(r0)     // Catch:{ Exception -> 0x0486 }
            goto L_0x048a
        L_0x0486:
            r10 = move-exception
            r10.printStackTrace()
        L_0x048a:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r10 = r9.aL
            java.lang.String r10 = r10.getDiferido()
            r0 = 2131756505(0x7f1005d9, float:1.914392E38)
            r1 = 2131365996(0x7f0a106c, float:1.8351873E38)
            if (r10 == 0) goto L_0x04b6
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r10 = r9.aL
            java.lang.String r10 = r10.getDiferido()
            java.lang.String r2 = "1"
            boolean r10 = r10.equals(r2)
            if (r10 == 0) goto L_0x04b6
            android.view.View r10 = r9.pantallaTransferencias
            android.view.View r10 = r10.findViewById(r1)
            android.widget.TextView r10 = (android.widget.TextView) r10
            java.lang.String r0 = r9.getString(r0)
            r10.setText(r0)
            goto L_0x04c5
        L_0x04b6:
            android.view.View r10 = r9.pantallaTransferencias
            android.view.View r10 = r10.findViewById(r1)
            android.widget.TextView r10 = (android.widget.TextView) r10
            java.lang.String r0 = r9.getString(r0)
            r10.setText(r0)
        L_0x04c5:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r10 = r9.aL
            java.lang.String r10 = r10.getDescripcion()
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 != 0) goto L_0x0504
            android.widget.TextView r10 = r9.textViewSeleccionar
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r0 = r9.aL
            java.lang.String r0 = r0.getDescripcion()
            android.text.Spanned r0 = android.text.Html.fromHtml(r0)
            r10.setText(r0)
            android.widget.TextView r10 = r9.textViewSeleccionar     // Catch:{ Exception -> 0x0500 }
            android.support.v4.app.FragmentActivity r0 = r9.getActivity()     // Catch:{ Exception -> 0x0500 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x0500 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r0)     // Catch:{ Exception -> 0x0500 }
            android.widget.TextView r1 = r9.textViewSeleccionar     // Catch:{ Exception -> 0x0500 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ Exception -> 0x0500 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0500 }
            java.lang.String r0 = r0.applyFilterGeneral(r1)     // Catch:{ Exception -> 0x0500 }
            r10.setContentDescription(r0)     // Catch:{ Exception -> 0x0500 }
            goto L_0x0504
        L_0x0500:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0504:
            java.lang.String r10 = "Otros Bancos"
            r9.aV = r10
            java.lang.String r10 = "Otros Bancos"
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean r0 = r9.aL
            java.lang.String r0 = r0.getTipoDestino()
            r9.f243ar = r0
        L_0x0512:
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r9.af
            java.lang.String r0 = r0.getInfo2()
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r9.b
            r1.setTipoCuenta(r10)
            r9.ap = r10
            ar.com.santander.rio.mbanking.managers.session.SessionManager r10 = r9.b
            r10.setDestinatario(r0)
            android.widget.TextView r10 = r9.textViewSeleccionar     // Catch:{ Exception -> 0x0544 }
            android.support.v4.app.FragmentActivity r1 = r9.getActivity()     // Catch:{ Exception -> 0x0544 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x0544 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r1)     // Catch:{ Exception -> 0x0544 }
            android.widget.TextView r2 = r9.textViewSeleccionar     // Catch:{ Exception -> 0x0544 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x0544 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0544 }
            java.lang.String r1 = r1.applyFilterGeneral(r2)     // Catch:{ Exception -> 0x0544 }
            r10.setContentDescription(r1)     // Catch:{ Exception -> 0x0544 }
            goto L_0x0548
        L_0x0544:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0548:
            android.view.View r10 = r9.pantallaTransferencias     // Catch:{ Exception -> 0x055c }
            r1 = 2131366004(0x7f0a1074, float:1.835189E38)
            android.view.View r10 = r10.findViewById(r1)     // Catch:{ Exception -> 0x055c }
            android.widget.TextView r10 = (android.widget.TextView) r10     // Catch:{ Exception -> 0x055c }
            java.lang.String r1 = r9.ap     // Catch:{ Exception -> 0x055c }
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)     // Catch:{ Exception -> 0x055c }
            r10.setText(r1)     // Catch:{ Exception -> 0x055c }
        L_0x055c:
            r9.b(r0)
            r9.mostrarInfoDestinatario()
            r9.goToTransferencias()
            ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter r10 = r9.am     // Catch:{ Exception -> 0x056d }
            ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios r0 = r9.af     // Catch:{ Exception -> 0x056d }
            r10.updateDestinatarioSeleccionado(r0)     // Catch:{ Exception -> 0x056d }
            goto L_0x0571
        L_0x056d:
            r10 = move-exception
            r10.fillInStackTrace()
        L_0x0571:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.goToTransferenciasFromABMResult(android.content.Intent):void");
    }

    public void goToAgendaFromABMResult() {
        this.aS = true;
        this.aT = true;
        callConsultaDatosInicialesTransf();
        this.ae = "N";
    }

    @OnFocusChange({2131364648})
    public void focusChangeAmount(boolean z) {
        if (this.etImporte != null) {
            this.etImporte.onFocusChange(this.etImporte, z);
        }
    }

    @OnFocusChange({2131364652})
    public void focusChangeConcept(boolean z) {
        if (this.etReferenciaConcepto != null && this.i != null && z && this.i.equals(this.etReferenciaConcepto.getText().toString())) {
            this.etReferenciaConcepto.setText("");
            this.etReferenciaConcepto.setHint("");
        }
    }

    /* access modifiers changed from: private */
    public String a(DatosCuentasDestOBBean datosCuentasDestOBBean) {
        AgDestOBBean agDestOBBean = (AgDestOBBean) this.aJ.getListAgDestOBBean().get(0);
        String str = "02";
        boolean z = false;
        for (int i2 = 0; i2 < agDestOBBean.getListDatosCuentasDestOBBean().size() && !z; i2++) {
            if (((DatosCuentasDestOBBean) agDestOBBean.getListDatosCuentasDestOBBean().get(i2)).getCbu().equals(datosCuentasDestOBBean.getCbu())) {
                str = ((DatosCuentasDestOBBean) agDestOBBean.getListDatosCuentasDestOBBean().get(i2)).getCaracteristica();
                z = true;
            }
        }
        return str;
    }

    /* access modifiers changed from: protected */
    public String formatMedioPago(String str) {
        String replaceAll = str.replaceAll("\n", UtilsCuentas.SEPARAOR2);
        String[] split = replaceAll.split("/");
        if (split.length < 2) {
            return replaceAll;
        }
        String[] split2 = split[1].split(UtilsCuentas.SEPARAOR2);
        StringBuilder sb = new StringBuilder();
        sb.append(split[0]);
        sb.append("/");
        sb.append(split2[0]);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(split2[1]);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(split2[2]);
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append("\n");
        sb5.append(sb4);
        return sb5.toString();
    }

    /* access modifiers changed from: private */
    public void a(final AgendaDestinatarios agendaDestinatarios, final RelativeLayout relativeLayout, final RelativeLayout relativeLayout2, int i2, final boolean z) {
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{((LayoutParams) relativeLayout.getLayoutParams()).rightMargin, i2});
        ofInt.setDuration(500);
        ofInt.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LayoutParams layoutParams = (LayoutParams) relativeLayout.getLayoutParams();
                layoutParams.setMargins(0, 0, ((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
                relativeLayout.setLayoutParams(layoutParams);
            }
        });
        ofInt.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                relativeLayout2.setVisibility(z ? 0 : 4);
                agendaDestinatarios.setSwiped(Boolean.valueOf(z));
            }
        });
        ofInt.start();
    }

    public void setSelectedCurrency() {
        setSelectedCurrency("Pesos");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setSelectedCurrency(java.lang.String r7) {
        /*
            r6 = this;
            int r0 = r7.hashCode()
            r1 = -1309599940(0xffffffffb1f1173c, float:-7.0166646E-9)
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x001b
            r1 = 77004642(0x496ff62, float:3.5499363E-36)
            if (r0 == r1) goto L_0x0011
            goto L_0x0025
        L_0x0011:
            java.lang.String r0 = "Pesos"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0025
            r0 = 1
            goto L_0x0026
        L_0x001b:
            java.lang.String r0 = "Dólares"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0025
            r0 = 0
            goto L_0x0026
        L_0x0025:
            r0 = -1
        L_0x0026:
            switch(r0) {
                case 0: goto L_0x002f;
                case 1: goto L_0x002a;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x0033
        L_0x002a:
            java.lang.String r0 = "ARS"
            r6.ad = r0
            goto L_0x0033
        L_0x002f:
            java.lang.String r0 = "USD"
            r6.ad = r0
        L_0x0033:
            android.text.SpannableStringBuilder r0 = new android.text.SpannableStringBuilder
            r0.<init>()
            java.lang.String r1 = "%s %s"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r5 = 2131756022(0x7f1003f6, float:1.914294E38)
            java.lang.String r5 = r6.getString(r5)
            r4[r2] = r5
            r4[r3] = r7
            java.lang.String r1 = java.lang.String.format(r1, r4)
            r0.append(r1)
            uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan r1 = new uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan
            android.support.v4.app.FragmentActivity r2 = r6.getActivity()
            android.content.Context r2 = r2.getApplicationContext()
            android.content.res.AssetManager r2 = r2.getAssets()
            java.lang.String r3 = "fonts/OpenSans-Semibold.otf"
            android.graphics.Typeface r2 = uk.co.chrisjenx.calligraphy.TypefaceUtils.load(r2, r3)
            r1.<init>(r2)
            int r2 = r0.length()
            int r3 = r7.length()
            int r2 = r2 - r3
            int r3 = r0.length()
            r4 = 33
            r0.setSpan(r1, r2, r3, r4)
            android.text.style.ForegroundColorSpan r1 = new android.text.style.ForegroundColorSpan
            android.content.res.Resources r2 = r6.getResources()
            r3 = 2131099749(0x7f060065, float:1.781186E38)
            int r2 = r2.getColor(r3)
            r1.<init>(r2)
            int r2 = r0.length()
            int r3 = r7.length()
            int r2 = r2 - r3
            int r3 = r0.length()
            r0.setSpan(r1, r2, r3, r4)
            android.widget.TextView r1 = r6.txtSelectedCurrency
            android.widget.TextView$BufferType r2 = android.widget.TextView.BufferType.SPANNABLE
            r1.setText(r0, r2)
            android.widget.ImageView r0 = r6.imgSelectedCurrency
            android.widget.TextView r1 = r6.txtSelectedCurrency
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskSelector(r1)
            r0.setContentDescription(r1)
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r0 = r6.etImporte
            java.lang.String r1 = ""
            r0.setText(r1)
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r0 = r6.etImporte
            java.lang.String r1 = "Dólares"
            boolean r1 = r1.equals(r7)
            if (r1 == 0) goto L_0x00c6
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_DOLAR
            goto L_0x00d3
        L_0x00c6:
            java.lang.String r1 = "Pesos"
            boolean r7 = r1.equals(r7)
            if (r7 == 0) goto L_0x00d1
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_PESOS
            goto L_0x00d3
        L_0x00d1:
            java.lang.String r7 = ""
        L_0x00d3:
            r0.setPrefix(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment.setSelectedCurrency(java.lang.String):void");
    }

    public void onSelectCurrency() {
        String str = this.ad.equals("USD") ? "Dólares" : this.ad.equals("ARS") ? "Pesos" : "";
        String str2 = str;
        ArrayList arrayList = new ArrayList();
        arrayList.add("Pesos");
        arrayList.add("Dólares");
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(Dialogs.currencySelector, getString(R.string.ID457_TRANSFERENCE_MAIN_PAYMENT_SELECTOR), null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, str2);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase("Pesos") && TransferenciasFragment.this.ad.equalsIgnoreCase("USD")) {
                    TransferenciasFragment.this.setSelectedCurrency("Pesos");
                    TransferenciasFragment.this.resetDestinatarioSeleccionado();
                    TransferenciasFragment.this.resetCuentaOrigenSeleccionada();
                } else if (str.equalsIgnoreCase("Dólares") && TransferenciasFragment.this.ad.equalsIgnoreCase("ARS")) {
                    TransferenciasFragment.this.setSelectedCurrency("Dólares");
                    TransferenciasFragment.this.resetDestinatarioSeleccionado();
                    TransferenciasFragment.this.resetCuentaOrigenSeleccionada();
                }
            }
        });
        newInstance.setCancelable(true);
        newInstance.show(getFragmentManager(), TenenciaCreditosFragment.DIALOG_SELECTOR);
    }

    public ArrayList<String> filterOptionsDe(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator it = this.aE.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (str.equalsIgnoreCase("USD") && str2.contains("U$S")) {
                arrayList.add(str2);
            } else if (str.equals("ARS") && !str2.contains("U$S")) {
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void a(DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
        if (!datosCuentasDestBSRBean.getTipo().equalsIgnoreCase("02")) {
            return;
        }
        if (this.ad.equalsIgnoreCase("ARS")) {
            datosCuentasDestBSRBean.setIdMoneda("0");
            datosCuentasDestBSRBean.setTipo("09");
            return;
        }
        datosCuentasDestBSRBean.setIdMoneda("1");
        datosCuentasDestBSRBean.setTipo("10");
    }

    private String a(VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean) {
        String str = this.ad;
        String tipoCtaToBane = verificaDatosInicialesTransfOBResponseBean.getVerificaDatosInicialesTransfOBBodyResponseBean().getVerificaDatosSalidaOBBean().getTipoCtaToBane();
        if (tipoCtaToBane == null) {
            return str;
        }
        if (tipoCtaToBane.equalsIgnoreCase(getString(R.string.IDXX_OB_ACCOUNT_TYPE_01)) || tipoCtaToBane.equalsIgnoreCase(getString(R.string.IDXX_OB_ACCOUNT_TYPE_11)) || tipoCtaToBane.equalsIgnoreCase(getString(R.string.IDXX_OB_ACCOUNT_TYPE_21))) {
            return "ARS";
        }
        return (tipoCtaToBane.equalsIgnoreCase(getString(R.string.IDXX_OB_ACCOUNT_TYPE_02)) || tipoCtaToBane.equalsIgnoreCase(getString(R.string.IDXX_OB_ACCOUNT_TYPE_12)) || tipoCtaToBane.equalsIgnoreCase(getString(R.string.IDXX_OB_ACCOUNT_TYPE_22))) ? "USD" : str;
    }

    /* access modifiers changed from: private */
    public void K() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        arrayList.add(PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT);
        this.be = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.be.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(TransferenciasFragment.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    if (ContextCompat.checkSelfPermission(TransferenciasFragment.this.getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        TransferenciasFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                        return;
                    }
                    TransferenciasFragment.this.bf.optionShareSelected();
                } else if (str.equalsIgnoreCase(TransferenciasFragment.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    if (ContextCompat.checkSelfPermission(TransferenciasFragment.this.getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        TransferenciasFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                        return;
                    }
                    TransferenciasFragment.this.bf.optionDownloadSelected();
                } else if (str.equalsIgnoreCase(PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT)) {
                    TransferenciasFragment.this.onBackPressed();
                }
            }
        });
        this.be.setCancelable(true);
    }

    public void showRequestPermissionExplation(final int i2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
                TransferenciasFragment.this.K();
            }

            public void onSimpleActionButton() {
                TransferenciasFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
            }
        });
        newInstance.show(getFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    /* access modifiers changed from: private */
    public OptionsToShare L() {
        return new OptionsToShareImpl(this, getActivity().getApplicationContext(), getFragmentManager()) {
            public String getSubjectReceiptToShare() {
                return "Comprobante de Transferencia";
            }

            public View getViewToShare() {
                return TransferenciasFragment.this.svComprobante;
            }

            public void receiveIntentAppShare(Intent intent) {
                TransferenciasFragment.this.startActivityForResult(Intent.createChooser(intent, TransferenciasFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return "Comprobante Transferencia - ".concat(((TextView) TransferenciasFragment.this.pantallaComprobanteTransferencia.findViewById(R.id.textViewNumeroComprobante)).getText().toString());
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                TransferenciasFragment.this.bd = true;
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                TransferenciasFragment.this.bd = true;
            }

            public void optionCancelSelected() {
                super.optionCancelSelected();
                TransferenciasFragment.this.bd = true;
            }
        };
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        this.bf.onRequestPermissionsResult(i2, strArr, iArr);
    }

    public void showDialogConfirmationCtaMigrada(AppCompatActivity appCompatActivity, VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean) {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("DIALOGCONFIRMATIONABMGETCTAMIGRADA", appCompatActivity.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean().getMensajeCtaMigrada(), null, null, appCompatActivity.getString(R.string.BTN_POSITIVE_ACEPTAR_CTA_MIGRADA), appCompatActivity.getString(R.string.BTN_NEGATIVE_CANCELAR_CTA_MIGRADA), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                TransferenciasFragment.this.goToNuevoDestinatarioActivityCtaMigrada(Origen.ORIGEN_NUEVO);
                TransferenciasFragment.this.resetDestinatarioSeleccionado();
            }

            public void onNegativeButton() {
                TransferenciasFragment.this.resetDestinatarioSeleccionado();
                newInstance.closeDialog();
            }
        });
        newInstance.show(appCompatActivity.getSupportFragmentManager(), "DIALOGCONFIRMATIONABMGETCTAMIGRADA");
    }
}
