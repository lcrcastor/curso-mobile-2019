package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.ui.activities.SugerirSeguroObjetosActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.ListaFamiliaObjetoAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.ListaFamiliaObjetoAdapter.Data;
import ar.com.santander.rio.mbanking.app.ui.adapters.ListaFamiliaObjetoAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.utils.VerticalDividerItemDecoration;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.PreguntasFamiliaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSugerencia;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFamiliasObjetos;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SeleccionFamiliaObjetoFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener a;
    @Inject
    public AnalyticsManager analyticsManager;
    private Context b;
    private List<FamiliaBean> c;
    private ListaFamiliaObjetoAdapter d;
    /* access modifiers changed from: private */
    public FragmentActivity e;
    private TextView f;
    /* access modifiers changed from: private */
    public GetFamiliasObjetosBodyResponseBean g;
    /* access modifiers changed from: private */
    public FamiliaBean h;
    private IActionCustom i;
    @Inject
    public IDataManager mDataManager;

    public interface OnFragmentInteractionListener extends IFragmentBase {
    }

    public static SeleccionFamiliaObjetoFragment newInstance(GetFamiliasObjetosBodyResponseBean getFamiliasObjetosBodyResponseBean) {
        SeleccionFamiliaObjetoFragment seleccionFamiliaObjetoFragment = new SeleccionFamiliaObjetoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("GET_FAMILIA_OBJETO", getFamiliasObjetosBodyResponseBean);
        seleccionFamiliaObjetoFragment.setArguments(bundle);
        return seleccionFamiliaObjetoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.g = (GetFamiliasObjetosBodyResponseBean) getArguments().getParcelable("GET_FAMILIA_OBJETO");
        }
        this.c = new ArrayList();
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_seleccion_familia_objeto, viewGroup, false);
        initUI(inflate);
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.analyticsManager.trackScreen(Screens.objectInsurance());
    }

    public void initUI(View view) {
        this.a.configureBackActionBar();
        VerticalDividerItemDecoration verticalDividerItemDecoration = new VerticalDividerItemDecoration(this.b);
        this.d = new ListaFamiliaObjetoAdapter(getContext(), getListData(this.g.getListaFamilias().getFamilia(), this.g.getLinkSugerencia()));
        this.d.setmOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(FamiliaBean familiaBean) {
                SeleccionFamiliaObjetoFragment.this.a.showProgressBar(VGetFamiliasObjetos.nameService);
                SeleccionFamiliaObjetoFragment.this.mDataManager.getPreguntasFamilia(familiaBean.getIdFamilia());
                SeleccionFamiliaObjetoFragment.this.h = familiaBean;
            }

            public void onFooterLinkClick() {
                Intent intent = new Intent(SeleccionFamiliaObjetoFragment.this.e, SugerirSeguroObjetosActivity.class);
                intent.putExtra("leyenda", SeleccionFamiliaObjetoFragment.this.g.getListaLeyendas().getLeyendaByTag("SEGOBJ_SUGE").descripcion);
                SeleccionFamiliaObjetoFragment.this.startActivity(intent);
            }
        });
        this.f = (TextView) view.findViewById(R.id.title).findViewById(R.id.functionality_title);
        TextView textView = (TextView) view.findViewById(R.id.subtitle).findViewById(R.id.section_title);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_familia_objeto);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(verticalDividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.d);
        this.f.setText(getString(R.string.ID_4790_SEGUROS_TIT_SEGURO_DE_OBJETOS));
        textView.setText("Seleccioná el objeto que queres cotizar");
        this.c.addAll(this.g.getListaFamilias().getFamilia());
        this.d.notifyDataSetChanged();
    }

    @Subscribe
    public void onGetPreguntasFamilia(PreguntasFamiliaEvent preguntasFamiliaEvent) {
        this.a.dismissProgressBar();
        final PreguntasFamiliaEvent preguntasFamiliaEvent2 = preguntasFamiliaEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.FIRMA_SEGURO, (BaseActivity) getActivity(), this.f.getText().toString()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                SeleccionFamiliaObjetoFragment.this.a.changeFragment(PreguntasFamiliaFragment.newInstance(preguntasFamiliaEvent2.getResponse(), SeleccionFamiliaObjetoFragment.this.h), FragmentConstants.PREGUNTAS_FAMILIA);
            }

            /* access modifiers changed from: protected */
            public void onRes5Error(WebServiceEvent webServiceEvent) {
                super.onRes5Error(webServiceEvent);
            }
        };
        r1.handleWSResponse(preguntasFamiliaEvent);
    }

    public List<Data> getListData(List<FamiliaBean> list, LinkSugerencia linkSugerencia) {
        ArrayList arrayList = new ArrayList();
        for (FamiliaBean familiaBean : list) {
            Data data = new Data();
            data.setFamiliaBean(familiaBean);
            arrayList.add(data);
        }
        Data data2 = new Data();
        data2.setLeyendaBean(linkSugerencia);
        arrayList.add(data2);
        return arrayList;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.a = (OnFragmentInteractionListener) context;
            this.i = (IActionCustom) context;
            this.b = context;
            this.e = getActivity();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.a = null;
        this.i = null;
    }
}
