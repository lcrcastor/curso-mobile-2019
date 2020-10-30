package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.activities.PreAutorizacionDebinActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.PreautorizacionDebinAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.PreautorizacionDebinAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.utils.PreAutorizacionesDebinUtil;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean;
import ar.com.santander.rio.mbanking.utils.EndlessRecyclerViewScrollListener;
import ar.com.santander.rio.mbanking.utils.EndlessRecyclerViewScrollListener.EndlessRecyclerViewScrollListenerInteface;
import butterknife.ButterKnife;
import java.util.List;

public class CosultaTenenciaPreAutorizacionesRecibidasFragment extends Fragment implements OnClickListener, OnItemClickListener, EndlessRecyclerViewScrollListenerInteface {
    private Adapter a;
    private TextView ad;
    private TextView ae;
    private RecyclerView af;
    private EndlessRecyclerViewScrollListener ag;
    private LayoutManager ah;
    /* access modifiers changed from: private */
    public int ai = 0;
    private ImageView aj;
    /* access modifiers changed from: private */
    public PreAutorizacionDebinActivity b;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener c;
    private List<PreautorizacionBean> d;
    private SessionManager e;
    private SettingsManager f;
    private String g;
    private String h;
    private LinearLayout i;

    public interface OnFragmentInteractionListener {
        void busquedaAvanzadaPreAutorizaciones(String str);

        void irADetallePreAutorizacion(PreautorizacionBean preautorizacionBean);
    }

    public static CosultaTenenciaPreAutorizacionesRecibidasFragment newInstance(SessionManager sessionManager, SettingsManager settingsManager, String str, List<PreautorizacionBean> list, String str2, String str3) {
        CosultaTenenciaPreAutorizacionesRecibidasFragment cosultaTenenciaPreAutorizacionesRecibidasFragment = new CosultaTenenciaPreAutorizacionesRecibidasFragment();
        cosultaTenenciaPreAutorizacionesRecibidasFragment.setArguments(new Bundle());
        cosultaTenenciaPreAutorizacionesRecibidasFragment.d = list;
        cosultaTenenciaPreAutorizacionesRecibidasFragment.h = str;
        cosultaTenenciaPreAutorizacionesRecibidasFragment.g = str3;
        cosultaTenenciaPreAutorizacionesRecibidasFragment.e = sessionManager;
        cosultaTenenciaPreAutorizacionesRecibidasFragment.f = settingsManager;
        return cosultaTenenciaPreAutorizacionesRecibidasFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = (PreAutorizacionDebinActivity) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_cosulta_pre_autorizaciones, viewGroup, false);
        b(inflate);
        return inflate;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            this.c = (OnFragmentInteractionListener) activity;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(activity.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.c = null;
    }

    public void onLoadMore(int i2, RecyclerView recyclerView) {
        Log.d("HOLA", "HOOLA");
    }

    public void onPreAutorizacionSelected(View view, PreautorizacionBean preautorizacionBean) {
        this.c.irADetallePreAutorizacion(preautorizacionBean);
    }

    private void b(View view) {
        this.af = (RecyclerView) view.findViewById(R.id.F32_01_RCV_DEBINESS);
        this.af.setHasFixedSize(true);
        this.ah = new LinearLayoutManager(getActivity());
        this.ae = (TextView) view.findViewById(R.id.buscar);
        this.aj = (ImageView) view.findViewById(R.id.img_arrow);
        this.ae.setText(this.g);
        this.ae.setOnClickListener(this);
        this.aj.setOnClickListener(this);
        this.ag = new EndlessRecyclerViewScrollListener(this.ah, (EndlessRecyclerViewScrollListenerInteface) this);
        this.af.setOnScrollListener(this.ag);
        this.af.setLayoutManager(this.ah);
        this.a = new PreautorizacionDebinAdapter(this.d, getActivity(), this.e, this);
        this.af.setAdapter(this.a);
        ButterKnife.inject((Object) this, view);
        this.i = (LinearLayout) view.findViewById(R.id.sinDebinesPreAutorzaciones);
        this.ad = (TextView) view.findViewById(R.id.textoErrorPreAutorizaciones);
    }

    public void setListPreautorizacionesBeans(List<PreautorizacionBean> list) {
        if (list != null) {
            this.d.clear();
            this.a.notifyDataSetChanged();
            this.d.addAll(list);
            this.a.notifyDataSetChanged();
            return;
        }
        this.i.setVisibility(0);
    }

    public void setSiguientePagina(String str) {
        this.g = str;
    }

    public void setErrorMessage(String str) {
        this.h = str;
    }

    public void onClick(final View view) {
        if (view.getId() == R.id.buscar || view.getId() == R.id.img_arrow) {
            PreAutorizacionesDebinUtil.showPreautorizacionesOptions((AppCompatActivity) getActivity(), this.b.getListTableEstpreaut(), this.ai, new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    ((TextView) view).setText(str);
                    CosultaTenenciaPreAutorizacionesRecibidasFragment.this.ai = PreAutorizacionesDebinUtil.getPosEstadoInTableList(str, CosultaTenenciaPreAutorizacionesRecibidasFragment.this.b.getListTableEstpreaut());
                    CosultaTenenciaPreAutorizacionesRecibidasFragment.this.c.busquedaAvanzadaPreAutorizaciones(PreAutorizacionesDebinUtil.getCodEstadoInTableList(str, CosultaTenenciaPreAutorizacionesRecibidasFragment.this.b.getListTableEstpreaut()));
                }
            });
        }
    }
}
