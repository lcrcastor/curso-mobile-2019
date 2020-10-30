package ar.com.santander.rio.mbanking.app.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios;
import ar.com.santander.rio.mbanking.app.ui.adapters.AdapterDestinatarios;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.BAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMAgendadosEnvEfe;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaAgendadosEnvEfe;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import ar.com.santander.rio.mbanking.view.ISBANToast;
import butterknife.InjectView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

public class ActivityEnvioDineroListadoDestinatarios extends BaseActivity {
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    /* access modifiers changed from: private */
    public static float v;
    /* access modifiers changed from: private */
    public static float w;
    private ArrayList<Object[]> A = null;
    /* access modifiers changed from: private */
    public String B;
    /* access modifiers changed from: private */
    public Integer C;
    /* access modifiers changed from: private */
    public String D;
    /* access modifiers changed from: private */
    public String E;
    /* access modifiers changed from: private */
    public String F;
    /* access modifiers changed from: private */
    public String G;
    /* access modifiers changed from: private */
    public String H;
    private ArrayList<ObjectDestinatarios> I;
    private View J;
    /* access modifiers changed from: private */
    public IsbanDialogFragment K;
    /* access modifiers changed from: private */
    public boolean L = false;
    /* access modifiers changed from: private */
    public String M = "NONE";
    private float N = Float.NaN;
    /* access modifiers changed from: private */
    public View O;
    /* access modifiers changed from: private */
    public Boolean P = Boolean.valueOf(true);
    @InjectView(2131362320)
    Button btn_nuevoDestinarioDesdeContactosFiltroVacio;
    @InjectView(2131362321)
    Button btn_nuevoDestinarioDesdeContactosVacio;
    @InjectView(2131362322)
    Button btn_nuevoDestinarioFiltroVacio;
    @InjectView(2131362323)
    Button btn_nuevoDestinarioVacio;
    public OnTouchListener gestureListItemListener;
    @InjectView(2131362330)
    TextView lbl_incorporaDestinatario;
    @InjectView(2131362331)
    TextView lbl_listadoFiltroVacio_1;
    @InjectView(2131362332)
    TextView lbl_listadoFiltroVacio_2;
    @InjectView(2131362333)
    TextView lbl_listadoFiltroVacio_3;
    @InjectView(2131362334)
    TextView lbl_listadoVacio;
    public List<ObjectDestinatarios> listObjectItemData = new ArrayList();
    @InjectView(2131362337)
    ListView listaDestinatarios;
    @InjectView(2131364766)
    View listadoDestinatariosView;
    @InjectView(2131362324)
    Button mButtonEnviarADestinatario;
    @Inject
    AnalyticsManager p;
    @Inject
    IDataManager q;
    @Inject
    SessionManager r;
    @InjectView(2131362338)
    RelativeLayout rll_listadoDestinatarios;
    @InjectView(2131362339)
    RelativeLayout rll_listadoDestinatariosFiltroVacio;
    @InjectView(2131362340)
    RelativeLayout rll_listadoDestinatariosVacio;
    @Inject
    Bus s;
    @InjectView(2131362327)
    ClearableEditText searchInput;
    @InjectView(2131362335)
    LinearLayout sideIndex;
    AdapterDestinatarios t = null;
    Intent u;
    private GestureDetector x;
    private int y;
    private int z;

    class SideIndexGestureListener extends SimpleOnGestureListener {
        SideIndexGestureListener() {
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            ActivityEnvioDineroListadoDestinatarios.v = ActivityEnvioDineroListadoDestinatarios.v - f;
            ActivityEnvioDineroListadoDestinatarios.w = ActivityEnvioDineroListadoDestinatarios.w - f2;
            if (ActivityEnvioDineroListadoDestinatarios.v >= BitmapDescriptorFactory.HUE_RED && ActivityEnvioDineroListadoDestinatarios.w >= BitmapDescriptorFactory.HUE_RED) {
                ActivityEnvioDineroListadoDestinatarios.this.displayListItem();
            }
            return super.onScroll(motionEvent, motionEvent2, f, f2);
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
            ActivityEnvioDineroListadoDestinatarios.this.M = "NONE";
        }

        private void a(View view) {
            ActivityEnvioDineroListadoDestinatarios.this.a((ObjectDestinatarios) ActivityEnvioDineroListadoDestinatarios.this.listaDestinatarios.getItemAtPosition(((Integer) view.getTag()).intValue()), (RelativeLayout) view.findViewById(R.id.F12_06A_rll_contenedor_datos_item), (RelativeLayout) view.findViewById(R.id.F12_06A_rll_botones_acciones), 0, false);
        }

        private void b(View view) {
            if (ActivityEnvioDineroListadoDestinatarios.this.O != null && ActivityEnvioDineroListadoDestinatarios.this.O != view) {
                a(ActivityEnvioDineroListadoDestinatarios.this.O);
                ActivityEnvioDineroListadoDestinatarios.this.O = null;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:44:0x015c  */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0171  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x0190  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouch(android.view.View r10, android.view.MotionEvent r11) {
            /*
                r9 = this;
                java.lang.Object r0 = r10.getTag()
                java.lang.Integer r0 = (java.lang.Integer) r0
                int r0 = r0.intValue()
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r1 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                android.widget.ListView r1 = r1.listaDestinatarios
                java.lang.Object r0 = r1.getItemAtPosition(r0)
                r2 = r0
                ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios r2 = (ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios) r2
                r0 = 2131362319(0x7f0a020f, float:1.8344415E38)
                android.view.View r0 = r10.findViewById(r0)
                r3 = r0
                android.widget.RelativeLayout r3 = (android.widget.RelativeLayout) r3
                r0 = 2131362318(0x7f0a020e, float:1.8344413E38)
                android.view.View r0 = r10.findViewById(r0)
                r4 = r0
                android.widget.RelativeLayout r4 = (android.widget.RelativeLayout) r4
                android.view.ViewGroup$LayoutParams r0 = r3.getLayoutParams()
                android.widget.RelativeLayout$LayoutParams r0 = (android.widget.RelativeLayout.LayoutParams) r0
                r1 = 2131362315(0x7f0a020b, float:1.8344407E38)
                android.view.View r1 = r10.findViewById(r1)
                android.widget.Button r1 = (android.widget.Button) r1
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r5 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                android.content.Context r5 = r5.getApplicationContext()
                r6 = 2131758250(0x7f100caa, float:1.9147459E38)
                java.lang.String r5 = r5.getString(r6)
                r1.setContentDescription(r5)
                r1 = 2131362316(0x7f0a020c, float:1.834441E38)
                android.view.View r1 = r10.findViewById(r1)
                android.widget.Button r1 = (android.widget.Button) r1
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r5 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                android.content.Context r5 = r5.getApplicationContext()
                r6 = 2131758251(0x7f100cab, float:1.914746E38)
                java.lang.String r5 = r5.getString(r6)
                r1.setContentDescription(r5)
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r1 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
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
                    case 0: goto L_0x0238;
                    case 1: goto L_0x01ae;
                    case 2: goto L_0x0085;
                    case 3: goto L_0x0080;
                    default: goto L_0x007e;
                }
            L_0x007e:
                goto L_0x025c
            L_0x0080:
                r9.a(r10)
                goto L_0x025c
            L_0x0085:
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
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r10 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                int r11 = r9.c
                int r2 = r9.b
                int r11 = r11 - r2
                if (r11 <= 0) goto L_0x00b0
                java.lang.String r11 = "RIGHT"
                goto L_0x00bc
            L_0x00b0:
                int r11 = r9.c
                int r2 = r9.b
                int r11 = r11 + r2
                if (r11 >= 0) goto L_0x00ba
                java.lang.String r11 = "LEFT"
                goto L_0x00bc
            L_0x00ba:
                java.lang.String r11 = "NONE"
            L_0x00bc:
                r10.M = r11
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r10 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r11 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r11 = r11.M
                java.lang.String r2 = "NONE"
                boolean r11 = r11.equals(r2)
                if (r11 != 0) goto L_0x00d6
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r11 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r11 = r11.M
                goto L_0x00ec
            L_0x00d6:
                int r11 = r9.d
                int r2 = r9.b
                int r11 = r11 - r2
                if (r11 <= 0) goto L_0x00e0
                java.lang.String r11 = "UP"
                goto L_0x00ec
            L_0x00e0:
                int r11 = r9.d
                int r2 = r9.b
                int r11 = r11 + r2
                if (r11 >= 0) goto L_0x00ea
                java.lang.String r11 = "DOWN"
                goto L_0x00ec
            L_0x00ea:
                java.lang.String r11 = "NONE"
            L_0x00ec:
                r10.M = r11
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r10 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r10 = r10.M
                java.lang.String r11 = "NONE"
                boolean r10 = r10.equals(r11)
                if (r10 != 0) goto L_0x011e
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r10 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r10 = r10.M
                java.lang.String r11 = "UP"
                boolean r10 = r10.equals(r11)
                if (r10 != 0) goto L_0x011e
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r10 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r10 = r10.M
                java.lang.String r11 = "DOWN"
                boolean r10 = r10.equals(r11)
                if (r10 != 0) goto L_0x011e
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r10 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                r10.L = r7
            L_0x011e:
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r10 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r10 = r10.M
                int r11 = r10.hashCode()
                r2 = 2332679(0x239807, float:3.26878E-39)
                if (r11 == r2) goto L_0x014c
                r2 = 2402104(0x24a738, float:3.366065E-39)
                if (r11 == r2) goto L_0x0142
                r2 = 77974012(0x4a5c9fc, float:3.8976807E-36)
                if (r11 == r2) goto L_0x0138
                goto L_0x0156
            L_0x0138:
                java.lang.String r11 = "RIGHT"
                boolean r10 = r10.equals(r11)
                if (r10 == 0) goto L_0x0156
                r10 = 1
                goto L_0x0157
            L_0x0142:
                java.lang.String r11 = "NONE"
                boolean r10 = r10.equals(r11)
                if (r10 == 0) goto L_0x0156
                r10 = 2
                goto L_0x0157
            L_0x014c:
                java.lang.String r11 = "LEFT"
                boolean r10 = r10.equals(r11)
                if (r10 == 0) goto L_0x0156
                r10 = 0
                goto L_0x0157
            L_0x0156:
                r10 = -1
            L_0x0157:
                switch(r10) {
                    case 0: goto L_0x0190;
                    case 1: goto L_0x0171;
                    case 2: goto L_0x015c;
                    default: goto L_0x015a;
                }
            L_0x015a:
                goto L_0x025c
            L_0x015c:
                int r10 = r9.e
                int r10 = r10 * -1
                r0.setMargins(r8, r8, r10, r8)
                r3.setLayoutParams(r0)
                int r10 = r9.e
                if (r10 != 0) goto L_0x016c
                r8 = 8
            L_0x016c:
                r4.setVisibility(r8)
                goto L_0x025c
            L_0x0171:
                int r10 = r9.e
                int r11 = r9.c
                int r10 = r10 + r11
                if (r10 > 0) goto L_0x017e
                int r10 = r9.e
                int r11 = r9.c
                int r10 = r10 + r11
                goto L_0x017f
            L_0x017e:
                r10 = 0
            L_0x017f:
                r9.c = r10
                int r10 = r9.c
                int r10 = r10 * -1
                r0.setMargins(r8, r8, r10, r8)
                r3.setLayoutParams(r0)
                r4.setVisibility(r8)
                goto L_0x025c
            L_0x0190:
                int r10 = r9.e
                int r11 = r9.c
                int r10 = r10 + r11
                if (r10 < r1) goto L_0x019d
                int r10 = r9.e
                int r11 = r9.c
                int r1 = r10 + r11
            L_0x019d:
                r9.c = r1
                int r10 = r9.c
                int r10 = r10 * -1
                r0.setMargins(r8, r8, r10, r8)
                r3.setLayoutParams(r0)
                r4.setVisibility(r8)
                goto L_0x025c
            L_0x01ae:
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r11 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                r11.L = r8
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r11 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r11 = r11.M
                java.lang.String r0 = "NONE"
                boolean r11 = r11.equals(r0)
                if (r11 != 0) goto L_0x01f5
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r11 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r11 = r11.M
                java.lang.String r0 = "UP"
                boolean r11 = r11.equals(r0)
                if (r11 != 0) goto L_0x01f5
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r11 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r11 = r11.M
                java.lang.String r0 = "DOWN"
                boolean r11 = r11.equals(r0)
                if (r11 != 0) goto L_0x01f5
                int r11 = r9.c
                int r0 = r1 / 2
                if (r11 >= r0) goto L_0x01ed
                int r1 = r1 * -1
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r11 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                r11.O = r10
                r5 = r1
                r6 = 1
                goto L_0x01ef
            L_0x01ed:
                r5 = 0
                r6 = 0
            L_0x01ef:
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r1 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                r1.a(r2, r3, r4, r5, r6)
                goto L_0x0234
            L_0x01f5:
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r11 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.String r11 = r11.M
                java.lang.String r0 = "NONE"
                boolean r11 = r11.equals(r0)
                if (r11 == 0) goto L_0x0234
                java.lang.Boolean r11 = r2.getSwiped()
                boolean r11 = r11.booleanValue()
                if (r11 != 0) goto L_0x0215
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r11 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                r11.O = r10
                r4.setVisibility(r8)
            L_0x0215:
                ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios r10 = ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.this
                java.lang.Boolean r11 = r2.getSwiped()
                boolean r11 = r11.booleanValue()
                if (r11 == 0) goto L_0x0223
                r5 = 0
                goto L_0x0226
            L_0x0223:
                int r1 = r1 * -1
                r5 = r1
            L_0x0226:
                java.lang.Boolean r11 = r2.getSwiped()
                boolean r11 = r11.booleanValue()
                r6 = r11 ^ 1
                r1 = r10
                r1.a(r2, r3, r4, r5, r6)
            L_0x0234:
                r9.a()
                goto L_0x025c
            L_0x0238:
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
                if (r10 <= 0) goto L_0x025c
                r9.e = r1
            L_0x025c:
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.SwipeListItemListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
        }
    }

    private void d() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.ID2035_ENVIARDINERO_BTN_NUEVODEST));
        arrayList.add(getResources().getString(R.string.ID2016_ENVEFECT_BTN_INCDATOS));
        this.K = IsbanDialogFragment.newInstance("mPopupMenu", null, null, arrayList, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, null, arrayList);
        this.K.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(ActivityEnvioDineroListadoDestinatarios.this.getResources().getString(R.string.ID2035_ENVIARDINERO_BTN_NUEVODEST))) {
                    ActivityEnvioDineroListadoDestinatarios.this.a("", "");
                } else if (str.equalsIgnoreCase(ActivityEnvioDineroListadoDestinatarios.this.getResources().getString(R.string.ID2016_ENVEFECT_BTN_INCDATOS))) {
                    ActivityEnvioDineroListadoDestinatarios.this.p.trackEvent(ActivityEnvioDineroListadoDestinatarios.this.getString(R.string.analytics_category_enviodinero), ActivityEnvioDineroListadoDestinatarios.this.getString(R.string.analytics_action_incorporar_destinatario_desde_contacto), ActivityEnvioDineroListadoDestinatarios.this.getString(R.string.analytics_label_efectivo_a_destinatarios));
                    ActivityEnvioDineroListadoDestinatarios.this.g();
                }
            }
        });
        this.K.setCancelable(true);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0132  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r4) {
        /*
            r3 = this;
            r0 = 2130772000(0x7f010020, float:1.7147106E38)
            r1 = 2130772003(0x7f010023, float:1.7147112E38)
            r3.overridePendingTransition(r0, r1)
            super.onCreate(r4)
            r4 = 2131492939(0x7f0c004b, float:1.8609344E38)
            r3.setContentView(r4)
            butterknife.ButterKnife.inject(r3)
            android.content.Intent r4 = r3.getIntent()
            r3.u = r4
            android.content.Intent r4 = r3.u
            android.content.res.Resources r0 = r3.getResources()
            r1 = 2131755167(0x7f10009f, float:1.9141206E38)
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r4 = r4.getStringExtra(r0)
            r3.B = r4
            java.lang.String r4 = r3.B
            r3.a(r4)
            ar.com.santander.rio.mbanking.view.ClearableEditText r4 = r3.searchInput
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$2 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$2
            r0.<init>()
            r4.addTextChangedListener(r0)
            ar.com.santander.rio.mbanking.view.ClearableEditText r4 = r3.searchInput
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$3 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$3
            r0.<init>()
            r4.setOnClearListener(r0)
            android.content.Intent r4 = r3.u
            android.content.res.Resources r0 = r3.getResources()
            r1 = 2131755162(0x7f10009a, float:1.9141195E38)
            java.lang.String r0 = r0.getString(r1)
            java.util.ArrayList r4 = r4.getParcelableArrayListExtra(r0)
            r3.I = r4
            java.lang.String r4 = r3.B
            int r0 = r4.hashCode()
            r1 = -1941134401(0xffffffff8c4ca3bf, float:-1.5764864E-31)
            r2 = 0
            if (r0 == r1) goto L_0x0085
            r1 = -1072532104(0xffffffffc0127578, float:-2.2884197)
            if (r0 == r1) goto L_0x007b
            r1 = 1121961648(0x42dfc6b0, float:111.88806)
            if (r0 == r1) goto L_0x0071
            goto L_0x008f
        L_0x0071:
            java.lang.String r0 = "MULTIPLE_CHOICE"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008f
            r4 = 2
            goto L_0x0090
        L_0x007b:
            java.lang.String r0 = "SINGLE_CHOICE"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008f
            r4 = 1
            goto L_0x0090
        L_0x0085:
            java.lang.String r0 = "ABM_ONLY"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008f
            r4 = 0
            goto L_0x0090
        L_0x008f:
            r4 = -1
        L_0x0090:
            r0 = 8
            switch(r4) {
                case 0: goto L_0x0132;
                case 1: goto L_0x00aa;
                case 2: goto L_0x0097;
                default: goto L_0x0095;
            }
        L_0x0095:
            goto L_0x0143
        L_0x0097:
            android.widget.Button r4 = r3.mButtonEnviarADestinatario
            r4.setVisibility(r0)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r4 = r3.p
            r0 = 2131757573(0x7f100a05, float:1.9146086E38)
            java.lang.String r0 = r3.getString(r0)
            r4.trackScreen(r0)
            goto L_0x0143
        L_0x00aa:
            android.content.Intent r4 = r3.u
            android.content.res.Resources r0 = r3.getResources()
            r1 = 2131757020(0x7f1007dc, float:1.9144964E38)
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r4 = r4.getStringExtra(r0)
            r3.H = r4
            android.content.Intent r4 = r3.u
            android.content.res.Resources r0 = r3.getResources()
            r1 = 2131757017(0x7f1007d9, float:1.9144958E38)
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r4 = r4.getStringExtra(r0)
            r3.D = r4
            android.content.Intent r4 = r3.u
            android.content.res.Resources r0 = r3.getResources()
            r1 = 2131757028(0x7f1007e4, float:1.914498E38)
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r4 = r4.getStringExtra(r0)
            r3.E = r4
            android.content.Intent r4 = r3.u
            android.content.res.Resources r0 = r3.getResources()
            r1 = 2131757027(0x7f1007e3, float:1.9144978E38)
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r4 = r4.getStringExtra(r0)
            r3.F = r4
            android.content.Intent r4 = r3.u
            android.content.res.Resources r0 = r3.getResources()
            r1 = 2131757026(0x7f1007e2, float:1.9144976E38)
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r4 = r4.getStringExtra(r0)
            r3.G = r4
            android.content.Intent r4 = r3.u
            android.content.res.Resources r0 = r3.getResources()
            r1 = 2131757018(0x7f1007da, float:1.914496E38)
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r4 = r4.getStringExtra(r0)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3.C = r4
            android.widget.Button r4 = r3.mButtonEnviarADestinatario
            r4.setVisibility(r2)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r4 = r3.p
            r0 = 2131757575(0x7f100a07, float:1.914609E38)
            java.lang.String r0 = r3.getString(r0)
            r4.trackScreen(r0)
            goto L_0x0143
        L_0x0132:
            android.widget.Button r4 = r3.mButtonEnviarADestinatario
            r4.setVisibility(r0)
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r4 = r3.p
            r0 = 2131757567(0x7f1009ff, float:1.9146073E38)
            java.lang.String r0 = r3.getString(r0)
            r4.trackScreen(r0)
        L_0x0143:
            android.widget.Button r4 = r3.btn_nuevoDestinarioFiltroVacio
            android.widget.Button r0 = r3.btn_nuevoDestinarioFiltroVacio
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r4.setContentDescription(r0)
            android.widget.Button r4 = r3.btn_nuevoDestinarioDesdeContactosFiltroVacio
            android.widget.Button r0 = r3.btn_nuevoDestinarioDesdeContactosFiltroVacio
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r4.setContentDescription(r0)
            android.widget.Button r4 = r3.btn_nuevoDestinarioVacio
            android.widget.Button r0 = r3.btn_nuevoDestinarioVacio
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r4.setContentDescription(r0)
            android.widget.Button r4 = r3.btn_nuevoDestinarioDesdeContactosVacio
            android.widget.Button r0 = r3.btn_nuevoDestinarioDesdeContactosVacio
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r4.setContentDescription(r0)
            android.widget.Button r4 = r3.mButtonEnviarADestinatario
            android.widget.Button r0 = r3.mButtonEnviarADestinatario
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r4.setContentDescription(r0)
            r3.displayScreen()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.onCreate(android.os.Bundle):void");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.s.register(this);
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.s.unregister(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x009e, code lost:
        if (r0.equals("ABM_ONLY") != false) goto L_0x00a2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00b8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void displayScreen() {
        /*
            r8 = this;
            android.view.View r0 = r8.listadoDestinatariosView
            r1 = 0
            r0.setVisibility(r1)
            r0 = 0
            r8.O = r0
            android.content.Intent r0 = r8.u
            r2 = 2131755178(0x7f1000aa, float:1.9141228E38)
            java.lang.String r2 = r8.getString(r2)
            java.util.ArrayList r0 = r0.getParcelableArrayListExtra(r2)
            android.widget.Button r2 = r8.mButtonEnviarADestinatario
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$4 r3 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$4
            r3.<init>()
            r2.setOnClickListener(r3)
            android.widget.Button r2 = r8.btn_nuevoDestinarioDesdeContactosFiltroVacio
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$5 r3 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$5
            r3.<init>()
            r2.setOnClickListener(r3)
            android.widget.Button r2 = r8.btn_nuevoDestinarioFiltroVacio
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$6 r3 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$6
            r3.<init>()
            r2.setOnClickListener(r3)
            android.widget.Button r2 = r8.btn_nuevoDestinarioDesdeContactosVacio
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$7 r3 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$7
            r3.<init>()
            r2.setOnClickListener(r3)
            android.widget.Button r2 = r8.btn_nuevoDestinarioVacio
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$8 r3 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$8
            r3.<init>()
            r2.setOnClickListener(r3)
            java.util.ArrayList<ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios> r2 = r8.I
            r8.listObjectItemData = r2
            java.util.List<ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios> r2 = r8.listObjectItemData
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x005c
            r8.sortListDestinatarios()
            if (r0 == 0) goto L_0x005c
            r8.a(r0)
        L_0x005c:
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$SwipeListItemListener r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$SwipeListItemListener
            r0.<init>()
            r8.gestureListItemListener = r0
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$9 r6 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$9
            r6.<init>()
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$10 r5 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$10
            r5.<init>()
            java.lang.String r0 = r8.B
            r2 = -1
            int r3 = r0.hashCode()
            r4 = -1941134401(0xffffffff8c4ca3bf, float:-1.5764864E-31)
            if (r3 == r4) goto L_0x0098
            r1 = -1072532104(0xffffffffc0127578, float:-2.2884197)
            if (r3 == r1) goto L_0x008e
            r1 = 1121961648(0x42dfc6b0, float:111.88806)
            if (r3 == r1) goto L_0x0084
            goto L_0x00a1
        L_0x0084:
            java.lang.String r1 = "MULTIPLE_CHOICE"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00a1
            r1 = 2
            goto L_0x00a2
        L_0x008e:
            java.lang.String r1 = "SINGLE_CHOICE"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00a1
            r1 = 1
            goto L_0x00a2
        L_0x0098:
            java.lang.String r3 = "ABM_ONLY"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00a1
            goto L_0x00a2
        L_0x00a1:
            r1 = -1
        L_0x00a2:
            switch(r1) {
                case 0: goto L_0x00b8;
                case 1: goto L_0x00a6;
                case 2: goto L_0x00a6;
                default: goto L_0x00a5;
            }
        L_0x00a5:
            goto L_0x00c8
        L_0x00a6:
            ar.com.santander.rio.mbanking.app.ui.adapters.AdapterDestinatarios r7 = new ar.com.santander.rio.mbanking.app.ui.adapters.AdapterDestinatarios
            r2 = 2131493254(0x7f0c0186, float:1.8609983E38)
            java.util.List<ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios> r3 = r8.listObjectItemData
            r4 = 0
            r5 = 0
            r6 = 0
            r0 = r7
            r1 = r8
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r8.t = r7
            goto L_0x00c8
        L_0x00b8:
            ar.com.santander.rio.mbanking.app.ui.adapters.AdapterDestinatarios r7 = new ar.com.santander.rio.mbanking.app.ui.adapters.AdapterDestinatarios
            r2 = 2131493254(0x7f0c0186, float:1.8609983E38)
            java.util.List<ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios> r3 = r8.listObjectItemData
            android.view.View$OnTouchListener r4 = r8.gestureListItemListener
            r0 = r7
            r1 = r8
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r8.t = r7
        L_0x00c8:
            android.widget.ListView r0 = r8.listaDestinatarios
            ar.com.santander.rio.mbanking.app.ui.adapters.AdapterDestinatarios r1 = r8.t
            r0.setAdapter(r1)
            android.widget.ListView r0 = r8.listaDestinatarios
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$11 r1 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$11
            r1.<init>()
            r0.setOnItemClickListener(r1)
            android.view.GestureDetector r0 = new android.view.GestureDetector
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$SideIndexGestureListener r1 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$SideIndexGestureListener
            r1.<init>()
            r0.<init>(r8, r1)
            r8.x = r0
            r8.setLayoutVisibility()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.displayScreen():void");
    }

    @Subscribe
    public void onConsultaAgendados(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        dismissProgress();
        final ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent2 = consultaAgendadosEnvEfeEvent;
        AnonymousClass12 r0 = new BaseWSResponseHandler(this, TypeOption.INTERMDIATE_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                ActivityEnvioDineroListadoDestinatarios.this.a(consultaAgendadosEnvEfeEvent2);
            }
        };
        r0.handleWSResponse(consultaAgendadosEnvEfeEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        try {
            this.I = CExtEnv.getDestinatarios(((ConsultaAgendadosEnvEfeResponseBean) consultaAgendadosEnvEfeEvent.getBeanResponse()).consultaAgendadosEnvEfeBodyResponseBean.listaDestinatarios);
            displayScreen();
            dismissProgress();
            ISBANToast.show(this, ISBANToast.INFORMATION, getString(R.string.USER200017), ISBANToast.LENGTH_LONG);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaAgendadosResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    @Subscribe
    public void onBajaDestinatario(BAgendadosEnvEfeEvent bAgendadosEnvEfeEvent) {
        dismissProgress();
        final BAgendadosEnvEfeEvent bAgendadosEnvEfeEvent2 = bAgendadosEnvEfeEvent;
        AnonymousClass13 r0 = new BaseWSResponseHandler(this, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                ActivityEnvioDineroListadoDestinatarios.this.a(bAgendadosEnvEfeEvent2);
            }
        };
        r0.handleWSResponse(bAgendadosEnvEfeEvent);
    }

    /* access modifiers changed from: private */
    public void a(BAgendadosEnvEfeEvent bAgendadosEnvEfeEvent) {
        try {
            this.p.trackScreen(getString(R.string.analytics_enviodinero_confirmacion_destinatario_eliminado));
            showProgress(VConsultaAgendadosEnvEfe.nameService);
            CExtEnv.consultaAgendados(this.q);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaAgendadosResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    private void a(ArrayList<ObjectDestinatarios> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            ObjectDestinatarios objectDestinatarios = (ObjectDestinatarios) arrayList.get(i);
            int i2 = 0;
            while (true) {
                if (i2 >= this.listObjectItemData.size()) {
                    break;
                }
                ObjectDestinatarios objectDestinatarios2 = (ObjectDestinatarios) this.listObjectItemData.get(i2);
                if (objectDestinatarios2.equals(objectDestinatarios)) {
                    objectDestinatarios2.setSelected(Boolean.valueOf(true));
                    break;
                }
                i2++;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -1941134401(0xffffffff8c4ca3bf, float:-1.5764864E-31)
            if (r0 == r1) goto L_0x0028
            r1 = -1072532104(0xffffffffc0127578, float:-2.2884197)
            if (r0 == r1) goto L_0x001e
            r1 = 1121961648(0x42dfc6b0, float:111.88806)
            if (r0 == r1) goto L_0x0014
            goto L_0x0032
        L_0x0014:
            java.lang.String r0 = "MULTIPLE_CHOICE"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0032
            r3 = 0
            goto L_0x0033
        L_0x001e:
            java.lang.String r0 = "SINGLE_CHOICE"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0032
            r3 = 2
            goto L_0x0033
        L_0x0028:
            java.lang.String r0 = "ABM_ONLY"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0032
            r3 = 1
            goto L_0x0033
        L_0x0032:
            r3 = -1
        L_0x0033:
            switch(r3) {
                case 0: goto L_0x008d;
                case 1: goto L_0x0038;
                case 2: goto L_0x0038;
                default: goto L_0x0036;
            }
        L_0x0036:
            goto L_0x00e6
        L_0x0038:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.BACK_WITH_ADD
            r2.setActionBarType(r3)
            android.support.v7.app.ActionBar r3 = r2.getSupportActionBar()
            android.view.View r3 = r3.getCustomView()
            r2.J = r3
            r2.d()
            android.view.View r3 = r2.J
            r0 = 2131364173(0x7f0a094d, float:1.8348176E38)
            android.view.View r3 = r3.findViewById(r0)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r0 = 2131755130(0x7f10007a, float:1.914113E38)
            java.lang.String r0 = r2.getString(r0)
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r3.setContentDescription(r0)
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$16 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$16
            r0.<init>()
            r3.setOnClickListener(r0)
            android.view.View r3 = r2.J
            r0 = 2131364140(0x7f0a092c, float:1.8348109E38)
            android.view.View r3 = r3.findViewById(r0)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r0 = 2131755660(0x7f10028c, float:1.9142206E38)
            java.lang.String r0 = r2.getString(r0)
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r0)
            r3.setContentDescription(r0)
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$17 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$17
            r0.<init>()
            r3.setOnClickListener(r0)
            goto L_0x00e6
        L_0x008d:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.CANCELAR_CONFIRMAR
            r2.setActionBarType(r3)
            android.support.v7.app.ActionBar r3 = r2.getSupportActionBar()
            android.view.View r3 = r3.getCustomView()
            r2.J = r3
            android.view.View r3 = r2.J
            r0 = 2131364245(0x7f0a0995, float:1.8348322E38)
            android.view.View r3 = r3.findViewById(r0)
            r1 = 2131755133(0x7f10007d, float:1.9141137E38)
            java.lang.String r1 = r2.getString(r1)
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r1)
            r3.setContentDescription(r1)
            android.view.View r3 = r2.J
            android.view.View r3 = r3.findViewById(r0)
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$14 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$14
            r0.<init>()
            r3.setOnClickListener(r0)
            android.view.View r3 = r2.J
            r0 = 2131364360(0x7f0a0a08, float:1.8348555E38)
            android.view.View r3 = r3.findViewById(r0)
            r1 = 2131755146(0x7f10008a, float:1.9141163E38)
            java.lang.String r1 = r2.getString(r1)
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.applyFilterMaskBotton(r1)
            r3.setContentDescription(r1)
            android.view.View r3 = r2.J
            android.view.View r3 = r3.findViewById(r0)
            ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$15 r0 = new ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios$15
            r0.<init>()
            r3.setOnClickListener(r0)
        L_0x00e6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios.a(java.lang.String):void");
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.x.onTouchEvent(motionEvent);
    }

    private ArrayList<Object[]> e() {
        ArrayList<Object[]> arrayList = new ArrayList<>();
        String str = "";
        int i = 0;
        int i2 = 0;
        for (char c = 'A'; c <= 'Z'; c = (char) (c + 1)) {
            i++;
            String substring = String.valueOf(c).substring(0, 1);
            if (!substring.equals(str)) {
                Object[] objArr = {str, Integer.valueOf(i2 - 1), Integer.valueOf(i - 1)};
                int i3 = i + 1;
                arrayList.add(objArr);
                i2 = i3;
                str = substring;
            }
        }
        return arrayList;
    }

    public void onWindowFocusChanged(boolean z2) {
        super.onWindowFocusChanged(z2);
        rebuildIndex();
    }

    public void rebuildIndex() {
        this.y = this.sideIndex.getHeight();
        this.sideIndex.removeAllViews();
        this.A = e();
        this.z = this.A.size();
        int floor = (int) Math.floor((double) (this.sideIndex.getHeight() / 20));
        int i = this.z;
        while (i > floor) {
            i /= 2;
        }
        if (i > 0) {
            double d = (double) (this.z / i);
            for (double d2 = 1.0d; d2 <= ((double) this.z); d2 += d) {
                String obj = ((Object[]) this.A.get(((int) d2) - 1))[0].toString();
                TextView textView = new TextView(this);
                textView.setText(obj);
                textView.setGravity(17);
                textView.setTextSize(9.0f);
                textView.setTextColor(getResources().getColor(R.color.grey_medium_dark));
                textView.setLayoutParams(new LayoutParams(-2, -2, 1.0f));
                this.sideIndex.addView(textView);
            }
        }
        this.sideIndex.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ActivityEnvioDineroListadoDestinatarios.v = motionEvent.getX();
                ActivityEnvioDineroListadoDestinatarios.w = motionEvent.getY();
                ActivityEnvioDineroListadoDestinatarios.this.displayListItem();
                return false;
            }
        });
    }

    public void displayListItem() {
        double d = ((double) this.y) / ((double) this.z);
        int i = (int) (((double) w) / d);
        int i2 = (int) (((double) i) * d);
        Object[] objArr = (Object[]) this.A.get(i);
        int parseInt = Integer.parseInt(objArr[1].toString());
        this.listaDestinatarios.setSelection((int) (((double) parseInt) + (((double) (w - ((float) i2))) / (d / ((double) Math.max(1, Integer.parseInt(objArr[2].toString()) - parseInt))))));
    }

    public void sortListDestinatarios() {
        Collections.sort(this.listObjectItemData, new Comparator<ObjectDestinatarios>() {
            /* renamed from: a */
            public int compare(ObjectDestinatarios objectDestinatarios, ObjectDestinatarios objectDestinatarios2) {
                return objectDestinatarios.getNombre().compareToIgnoreCase(objectDestinatarios2.getNombre());
            }
        });
        rebuildIndex();
    }

    public void setLayoutVisibility() {
        if (this.t != null && this.t.getFilteredDisplayedCount() > 0) {
            this.searchInput.setEnabled(true);
            this.rll_listadoDestinatariosFiltroVacio.setVisibility(8);
            this.rll_listadoDestinatariosVacio.setVisibility(8);
            this.rll_listadoDestinatarios.setVisibility(0);
        } else if (this.searchInput.getText().toString().equals("")) {
            this.searchInput.setEnabled(false);
            this.rll_listadoDestinatariosFiltroVacio.setVisibility(8);
            this.rll_listadoDestinatariosVacio.setVisibility(0);
            this.rll_listadoDestinatarios.setVisibility(8);
        } else {
            this.searchInput.setEnabled(true);
            this.rll_listadoDestinatariosFiltroVacio.setVisibility(0);
            this.rll_listadoDestinatariosVacio.setVisibility(8);
            this.rll_listadoDestinatarios.setVisibility(8);
            this.lbl_listadoFiltroVacio_2.setText(Html.fromHtml(String.format(getString(R.string.ID2013_ENVEFECT_LBL_MENSAJE_2), new Object[]{this.searchInput.getText().toString()})));
            TextView textView = this.lbl_listadoFiltroVacio_1;
            StringBuilder sb = new StringBuilder();
            sb.append(this.lbl_listadoFiltroVacio_1.getText().toString());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.lbl_listadoFiltroVacio_2.getText().toString());
            textView.setContentDescription(sb.toString());
            TextView textView2 = this.lbl_listadoFiltroVacio_2;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.lbl_listadoFiltroVacio_1.getText().toString());
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(this.lbl_listadoFiltroVacio_2.getText().toString());
            textView2.setContentDescription(sb2.toString());
            this.p.trackScreen(getString(R.string.analytics_enviodinero_busqueda_destinatario_vacio));
        }
    }

    /* access modifiers changed from: private */
    public void a(final int i, final ObjectDestinatarios objectDestinatarios) {
        this.p.trackScreen(getString(R.string.analytics_enviodinero_aviso_confirmacion_eliminar_destinatario));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.USER200027_TITLE), getResources().getString(R.string.USER200027).replace(getResources().getString(R.string.STRING_PARAM_KEY), objectDestinatarios.getNombre()), null, null, "Sí", "No", null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ActivityEnvioDineroListadoDestinatarios.this.showProgress(VABMAgendadosEnvEfe.nameService);
                CExtEnv.bajaAgendado(ActivityEnvioDineroListadoDestinatarios.this.q, i, new BAgendadosEnvEfeDestinatarioBean(objectDestinatarios.getTipoDoc(), String.valueOf(CAmountIU.getInstance().getDoubleFromInputUser(objectDestinatarios.getDocumento()).intValue()), objectDestinatarios.getNombre(), objectDestinatarios.getMail()));
            }

            public void onNegativeButton() {
                ActivityEnvioDineroListadoDestinatarios.this.p.trackEvent(ActivityEnvioDineroListadoDestinatarios.this.getString(R.string.analytics_category_enviodinero), ActivityEnvioDineroListadoDestinatarios.this.getString(R.string.analytics_action_cancelar_eliminacion_destinatario), ActivityEnvioDineroListadoDestinatarios.this.getString(R.string.analytics_label_cancelacion_eliminacion_destinatario));
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    /* access modifiers changed from: private */
    public void a(ObjectDestinatarios objectDestinatarios) {
        Intent intent = new Intent(this, ActivityEnvioDineroNuevoContacto.class);
        intent.putExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIO_INFO_MODE), getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIO_INFO_EDIT));
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NOMBRE), objectDestinatarios.getNombre());
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_TIPODOC), objectDestinatarios.getTipoDoc());
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_DOCUMENTO), objectDestinatarios.getDocumento());
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_MAIL), objectDestinatarios.getMail());
        startActivityForResult(intent, 5);
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        Intent intent = new Intent(this, ActivityEnvioDineroNuevoContacto.class);
        intent.putExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIO_INFO_MODE), getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIO_INFO_CREATE));
        intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_NOMBRE), str);
        if (!str2.isEmpty()) {
            intent.putExtra(getResources().getString(R.string.INTENT_PUT_EXTRA_MAIL), str2);
        }
        startActivityForResult(intent, 2);
    }

    private void f() {
        startActivityForResult(new Intent("android.intent.action.PICK", Contacts.CONTENT_URI), 1);
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(this.lbl_listadoFiltroVacio_1, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_listadoFiltroVacio_2, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_listadoFiltroVacio_3, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_listadoVacio, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_incorporaDestinatario, BitmapDescriptorFactory.HUE_RED);
    }

    /* access modifiers changed from: private */
    public void g() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CONTACTS"}, 0);
            return;
        }
        f();
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 0) {
            f();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 5) {
            switch (i) {
                case 1:
                    if (i2 == -1) {
                        String str = "";
                        String str2 = "";
                        Cursor query = getContentResolver().query(intent.getData(), null, null, null, null);
                        if (query != null) {
                            if (query.moveToFirst()) {
                                str = query.getString(query.getColumnIndex("display_name"));
                            }
                            Cursor query2 = getContentResolver().query(Email.CONTENT_URI, null, "contact_id = ?", new String[]{query.getString(query.getColumnIndex("_id"))}, null);
                            while (query2.moveToNext()) {
                                str2 = query2.getString(query2.getColumnIndex("data1"));
                            }
                            a(str, str2);
                            query.close();
                            return;
                        }
                        return;
                    }
                    return;
                case 2:
                    if (i2 == -1 && intent != null) {
                        if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                            Intent intent2 = new Intent();
                            intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                            if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                                intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                            }
                            setResult(-1, intent2);
                            finish();
                            return;
                        }
                        this.P = Boolean.valueOf(false);
                        this.searchInput.setText("");
                        this.I = intent.getParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAAGENDADOS));
                        displayScreen();
                        return;
                    }
                    return;
                case 3:
                    if (i2 == -1) {
                        Intent intent3 = new Intent();
                        if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                            intent3.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                            if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                                intent3.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                            }
                        } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                            intent3.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
                        }
                        intent3.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_ENV, false));
                        intent3.putExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, intent.getBooleanExtra(EnvioConstants.RECARGAR_PANTALLA_INICIAL_EXT, false));
                        setResult(-1, intent3);
                        finish();
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (i2 == -1 && intent != null) {
            if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                Intent intent4 = new Intent();
                intent4.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                    intent4.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                }
                setResult(-1, intent4);
                finish();
                return;
            }
            this.P = Boolean.valueOf(false);
            this.searchInput.setText("");
            this.I = intent.getParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAAGENDADOS));
            displayScreen();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.L) {
            motionEvent.setLocation(motionEvent.getX(), this.N);
        } else {
            this.N = motionEvent.getY();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* access modifiers changed from: private */
    public void a(final ObjectDestinatarios objectDestinatarios, final RelativeLayout relativeLayout, final RelativeLayout relativeLayout2, int i, final boolean z2) {
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{((RelativeLayout.LayoutParams) relativeLayout.getLayoutParams()).rightMargin, i});
        ofInt.setDuration(500);
        ofInt.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                layoutParams.setMargins(0, 0, ((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
                relativeLayout.setLayoutParams(layoutParams);
            }
        });
        ofInt.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                relativeLayout2.setVisibility(z2 ? 0 : 8);
                objectDestinatarios.setSwiped(Boolean.valueOf(z2));
            }
        });
        ofInt.start();
    }
}
