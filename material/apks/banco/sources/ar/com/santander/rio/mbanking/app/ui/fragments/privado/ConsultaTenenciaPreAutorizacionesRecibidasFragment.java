package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.ui.activities.PreAutorizacionDebinActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.PreautorizacionDebinAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.PreautorizacionDebinAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.utils.PreAutorizacionesDebinUtil;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.DataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetPreAutorizacionesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.utils.EndlessRecyclerViewScrollListener.EndlessRecyclerViewScrollListenerInteface;
import butterknife.ButterKnife;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class ConsultaTenenciaPreAutorizacionesRecibidasFragment extends Fragment implements OnClickListener, OnItemClickListener, EndlessRecyclerViewScrollListenerInteface {
    public static final String COD_ID_SELECTED = "codIdSelected";
    public static final String INITIL_COD_SEARCH_LABEL = "initilCodSearchLabel";
    public static final String IS_FIRST_CALL = "isFirstCall";
    public static final String IS_LOADING = "isLoading";
    public static final String LIST_PREAUTORIZACIONES_BEANS = "listPreautorizacionesBeans";
    public static final String SELECTED_POS = "selectedPos";
    public static final String SIGUIENTE_PAGINA = "siguientePagina";
    DataManager a;
    private String ad;
    private String ae;
    private LinearLayout af;
    private TextView ag;
    private TextView ah;
    /* access modifiers changed from: private */
    public RecyclerView ai;
    private int aj = 0;
    private Bus ak;
    private View al;
    private List<ListGroupBean> am;
    private List<ListGroupBean> an;
    private List<ListGroupBean> ao;
    private List<ListGroupBean> ap;
    private List<ListGroupBean> aq;

    /* renamed from: ar reason: collision with root package name */
    private RelativeLayout f237ar;
    private boolean as = false;
    /* access modifiers changed from: private */
    public boolean at = false;
    private Adapter b;
    private PreAutorizacionDebinActivity c;
    private OnFragmentInteractionListener d;
    private List<PreautorizacionBean> e;
    private SessionManager f;
    private SettingsManager g;
    private String h;
    private String i;
    public LinearLayoutManager mLayoutManager;

    public interface OnFragmentInteractionListener {
        void busquedaAvanzadaPreAutorizaciones(String str);

        void irADetallePreAutorizacion(PreautorizacionBean preautorizacionBean);
    }

    public static ConsultaTenenciaPreAutorizacionesRecibidasFragment newInstance(SessionManager sessionManager, SettingsManager settingsManager, DataManager dataManager, String str, GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean, String str2) {
        ConsultaTenenciaPreAutorizacionesRecibidasFragment consultaTenenciaPreAutorizacionesRecibidasFragment = new ConsultaTenenciaPreAutorizacionesRecibidasFragment();
        consultaTenenciaPreAutorizacionesRecibidasFragment.setArguments(new Bundle());
        consultaTenenciaPreAutorizacionesRecibidasFragment.e = new ArrayList(getPreAutorizacionesBodyResponseBean.getListPreautorizaciones());
        consultaTenenciaPreAutorizacionesRecibidasFragment.ad = str;
        consultaTenenciaPreAutorizacionesRecibidasFragment.ae = getPreAutorizacionesBodyResponseBean.getSiguientePagina();
        consultaTenenciaPreAutorizacionesRecibidasFragment.i = str2;
        consultaTenenciaPreAutorizacionesRecibidasFragment.f = sessionManager;
        consultaTenenciaPreAutorizacionesRecibidasFragment.g = settingsManager;
        return consultaTenenciaPreAutorizacionesRecibidasFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = (PreAutorizacionDebinActivity) getActivity();
        this.ak = new Bus();
        this.ak.register(this);
        this.a = new DataManager(BaseApplication.get(this.c), this.ak, this.f, this.g);
        if (bundle != null) {
            this.aj = bundle.getInt(SELECTED_POS);
            this.as = bundle.getBoolean(IS_FIRST_CALL);
            this.at = bundle.getBoolean(IS_LOADING);
            this.i = bundle.getString(COD_ID_SELECTED);
            this.h = bundle.getString(INITIL_COD_SEARCH_LABEL);
            this.e = bundle.getParcelableArrayList(LIST_PREAUTORIZACIONES_BEANS);
            this.ae = bundle.getString(SIGUIENTE_PAGINA);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        ArrayList arrayList = new ArrayList(this.e);
        bundle.putInt(SELECTED_POS, this.aj);
        bundle.putBoolean(IS_FIRST_CALL, this.as);
        bundle.putBoolean(IS_LOADING, this.at);
        bundle.putString(COD_ID_SELECTED, this.i);
        bundle.putString(INITIL_COD_SEARCH_LABEL, this.h);
        bundle.putParcelableArrayList(LIST_PREAUTORIZACIONES_BEANS, arrayList);
        bundle.putString(SIGUIENTE_PAGINA, this.ae);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.al = layoutInflater.inflate(R.layout.fragment_cosulta_pre_autorizaciones, viewGroup, false);
        b(this.al);
        if (bundle == null) {
            setInitialValues(this.al);
        } else {
            c(this.al);
        }
        return this.al;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.d = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.d = null;
    }

    public void onLoadMore(int i2, RecyclerView recyclerView) {
        if (!this.at && !this.ae.equals("0")) {
            b(this.i);
        }
    }

    public void onPreAutorizacionSelected(View view, PreautorizacionBean preautorizacionBean) {
        this.d.irADetallePreAutorizacion(preautorizacionBean);
    }

    private void b(View view) {
        ButterKnife.inject((Object) this, view);
        this.f237ar = (RelativeLayout) view.findViewById(R.id.buscadorDebin);
        this.ah = (TextView) view.findViewById(R.id.buscar);
        this.af = (LinearLayout) view.findViewById(R.id.sinDebinesPreAutorzaciones);
        this.ag = (TextView) view.findViewById(R.id.textoErrorPreAutorizaciones);
        this.ai = (RecyclerView) view.findViewById(R.id.F32_01_RCV_DEBINESS);
        this.ai.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(getActivity(), 1, false);
        this.b = new PreautorizacionDebinAdapter(this.e, getActivity(), this.f, this);
    }

    public void setInitialValues(View view) {
        this.at = false;
        y();
        this.h = PreAutorizacionesDebinUtil.getLabelIntableList(this.i, this.am);
        GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean = new GetPreAutorizacionesBodyResponseBean();
        getPreAutorizacionesBodyResponseBean.setListPreautorizaciones(this.e);
        getPreAutorizacionesBodyResponseBean.setSiguientePagina(this.ae);
        setListPreautorizacionesBeans(getPreAutorizacionesBodyResponseBean, this.i);
        this.f237ar.setOnClickListener(this);
        this.ah.setText(this.h);
        this.ai.setAdapter(this.b);
        this.ai.setLayoutManager(this.mLayoutManager);
        this.ai.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (!ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.ai.canScrollVertically(1)) {
                    ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.onLoadMore(ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.mLayoutManager.getItemCount(), ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.ai);
                }
            }
        });
    }

    private void c(View view) {
        this.h = PreAutorizacionesDebinUtil.getLabelIntableList(this.i, this.am);
        GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean = new GetPreAutorizacionesBodyResponseBean();
        getPreAutorizacionesBodyResponseBean.setListPreautorizaciones(this.e);
        getPreAutorizacionesBodyResponseBean.setSiguientePagina(this.ae);
        setListPreautorizacionesBeans(getPreAutorizacionesBodyResponseBean, this.i);
        this.f237ar.setOnClickListener(this);
        this.ah.setText(this.h);
        this.ai.setAdapter(this.b);
        this.ai.setLayoutManager(this.mLayoutManager);
        this.ai.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (!ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.ai.canScrollVertically(1)) {
                    ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.onLoadMore(ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.mLayoutManager.getItemCount(), ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.ai);
                }
            }
        });
    }

    public void clearListPreautorizaciones() {
        if (this.e != null && this.b != null) {
            this.e.clear();
            this.b.notifyDataSetChanged();
        }
    }

    public void setListPreautorizacionesBeans(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean, String str) {
        A();
        ArrayList arrayList = new ArrayList(getPreAutorizacionesBodyResponseBean.getListPreautorizaciones());
        this.ae = getPreAutorizacionesBodyResponseBean.getSiguientePagina();
        this.af.setVisibility(8);
        this.ai.setVisibility(8);
        this.h = PreAutorizacionesDebinUtil.getLabelIntableList(str, this.am);
        this.ah.setText(this.h);
        if (arrayList.size() > 0) {
            this.e.clear();
            this.b.notifyDataSetChanged();
            this.e.addAll(arrayList);
            this.ai.setVisibility(0);
            if (B()) {
                b(str);
                return;
            }
            return;
        }
        this.af.setVisibility(0);
    }

    public void updateListPreautorizacionesBeans(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean, String str) {
        A();
        if (this.as) {
            this.as = false;
            setListPreautorizacionesBeans(getPreAutorizacionesBodyResponseBean, str);
            this.b.notifyDataSetChanged();
            return;
        }
        List listPreautorizaciones = getPreAutorizacionesBodyResponseBean.getListPreautorizaciones();
        this.ae = getPreAutorizacionesBodyResponseBean.getSiguientePagina();
        if (listPreautorizaciones.size() > 0) {
            this.e.addAll(listPreautorizaciones);
            this.b.notifyDataSetChanged();
        }
    }

    public void onClick(final View view) {
        if (view.getId() == R.id.buscadorDebin) {
            PreAutorizacionesDebinUtil.showPreautorizacionesOptions((AppCompatActivity) getActivity(), this.c.getListTableEstpreaut(), this.aj, new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    if (!ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.at) {
                        ConsultaTenenciaPreAutorizacionesRecibidasFragment.this.a(str, view);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, View view) {
        ((TextView) view.findViewById(R.id.buscar)).setText(str);
        this.i = PreAutorizacionesDebinUtil.getCodEstadoInTableList(str, this.c.getListTableEstpreaut());
        this.aj = PreAutorizacionesDebinUtil.getPosEstadoInTableList(str, this.c.getListTableEstpreaut());
        this.ae = null;
        this.as = true;
        clearListPreautorizaciones();
        b(this.i);
    }

    public void rechargeList() {
        this.ae = null;
        this.as = true;
        clearListPreautorizaciones();
        b(this.i);
    }

    public Boolean isListFinalPosition() {
        return Boolean.valueOf(this.mLayoutManager.findLastVisibleItemPosition() >= this.e.size());
    }

    private void y() {
        this.am = this.f.getTableByID(PRE_AUTORIZACIONES.ESTREPEAUT).getListGroupBeans();
        this.an = this.f.getTableByID(PRE_AUTORIZACIONES.CONDEBIN).getListGroupBeans();
        this.aq = this.f.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
        this.ao = this.f.getTableByID(PRE_AUTORIZACIONES.TPOCTACORTA).getListGroupBeans();
        this.ap = this.f.getTableByID(PRE_AUTORIZACIONES.PERIODOPREAUT).getListGroupBeans();
        this.aq = this.f.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
    }

    @Subscribe
    public void onRechargeList(GetPreAutorizacionesEvent getPreAutorizacionesEvent) {
        A();
        this.at = false;
        if (getPreAutorizacionesEvent.getResult() == TypeResult.OK) {
            updateListPreautorizacionesBeans(getPreAutorizacionesEvent.getResponse().getPreAutorizacionesBodyResponseBean, this.i);
        }
    }

    private void z() {
        if (this.af.getVisibility() == 0) {
            this.af.setVisibility(8);
            this.ai.setVisibility(0);
        }
        this.e.add(null);
        this.b.notifyDataSetChanged();
    }

    private void A() {
        if (this.e.size() > 0 && this.e.get(this.e.size() - 1) == null) {
            this.e.remove(this.e.size() - 1);
            this.b.notifyDataSetChanged();
        }
    }

    private boolean B() {
        return !this.ae.equals("0") && !isListFinalPosition().booleanValue();
    }

    private void b(String str) {
        z();
        this.at = true;
        this.a.getPreautorizaciones("C", str, this.ae);
    }
}
