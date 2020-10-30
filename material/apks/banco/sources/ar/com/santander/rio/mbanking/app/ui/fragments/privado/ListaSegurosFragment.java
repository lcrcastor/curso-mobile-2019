package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.SegurosBotonesAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.SegurosBotonesAdapter.AdapterListenes;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.CodRamo;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.services.events.GetFirmaSeguroEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BotonSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class ListaSegurosFragment extends BaseFragment {
    Fragment a = new Fragment();
    private TextView ad;
    boolean b = false;
    /* access modifiers changed from: private */
    public ContratarSeguroActivity c;
    private RecyclerView d;
    private LinearLayoutManager e;
    private SegurosBean f;
    private String g;
    private boolean h = false;
    /* access modifiers changed from: private */
    public BotonSeguroBean i;

    @SuppressLint({"ValidFragment"})
    public ListaSegurosFragment(SegurosBean segurosBean, String str) {
        this.f = segurosBean;
        this.g = str;
    }

    public ListaSegurosFragment() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = (ContratarSeguroActivity) getActivity();
    }

    public void onResume() {
        super.onResume();
        this.h = false;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_lista_seguros, viewGroup, false);
        this.c.configActionBarDefault();
        validarTarjetasAseguradas(this.f);
        b(inflate);
        return inflate;
    }

    private void b(View view) {
        this.c.analyticsManager.trackScreen(getString(R.string.analytics_screen_Contratar_nuevo_seguro));
        this.d = (RecyclerView) view.findViewById(R.id.rvBotonesSeguros);
        this.d.setHasFixedSize(true);
        this.e = new LinearLayoutManager(getActivity());
        this.ad = (TextView) view.findViewById(R.id.tvTitleListSeguros);
        ArrayList arrayList = new ArrayList();
        a(arrayList, getContext().getString(R.string.ID_4049_SEGUROS_LBL_SGRO_OBJETOS), getContext().getString(R.string.ID_4050_SEGUROS_LBL_LEYENDA_OBJETOS));
        a(arrayList, getContext().getString(R.string.ID_4047_SEGUROS_LBL_SGRO_MOVIL), getContext().getString(R.string.ID_4048_SEGUROS_LBL_LEYENDA));
        a(arrayList, getContext().getString(R.string.ID_4045_SEGUROS_LBL_COMPRA_PROT), getContext().getString(R.string.ID_4046_SEGUROS_LBL_LEYENDA));
        if (this.f.getListaBotones() != null) {
            arrayList.addAll(this.f.getListaBotones());
        }
        SegurosBotonesAdapter segurosBotonesAdapter = new SegurosBotonesAdapter(arrayList, new AdapterListenes() {
            public void onClickItemListener(View view, BotonSeguroBean botonSeguroBean) {
                if (botonSeguroBean.getNombre().equals(ListaSegurosFragment.this.getString(R.string.ID_4045_SEGUROS_LBL_COMPRA_PROT))) {
                    if (ListaSegurosFragment.this.b) {
                        ListaSegurosFragment.this.c.contratarSeguroPresenter.goToCompraProtegidaTarjetasYaAseguradas();
                    } else {
                        ListaSegurosFragment.this.c.contratarSeguroPresenter.obtenerCotizacionCompraProtegida();
                    }
                } else if (botonSeguroBean.getNombre().equals(ListaSegurosFragment.this.getContext().getString(R.string.ID_4047_SEGUROS_LBL_SGRO_MOVIL))) {
                    ListaSegurosFragment.this.c.verifyBatery();
                } else if (botonSeguroBean.getNombre().equals(ListaSegurosFragment.this.getContext().getString(R.string.ID_4049_SEGUROS_LBL_SGRO_OBJETOS))) {
                    ListaSegurosFragment.this.c.contratarSeguroPresenter.obtenerFamiliaObjetos();
                } else {
                    ListaSegurosFragment.this.a(botonSeguroBean);
                }
            }
        });
        this.d.setLayoutManager(this.e);
        this.d.setAdapter(segurosBotonesAdapter);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.g != null && !this.g.isEmpty()) {
            String str = this.g;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 2052559) {
                if (hashCode == 1734766764 && str.equals(FragmentConstants.SEGURO_WEB_VIEW_VIVIENDA)) {
                    c2 = 1;
                }
            } else if (str.equals(FragmentConstants.SEGURO_WEB_VIEW_AUTO)) {
                c2 = 0;
            }
            switch (c2) {
                case 0:
                case 1:
                    if (this.f.getListaBotones() != null) {
                        a(a(this.f.getListaBotones(), this.g));
                        break;
                    }
                    break;
            }
            this.g = null;
        }
    }

    private BotonSeguroBean a(List<BotonSeguroBean> list, String str) {
        for (BotonSeguroBean botonSeguroBean : list) {
            if (botonSeguroBean.getNombre().equals(str)) {
                return botonSeguroBean;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void a(BotonSeguroBean botonSeguroBean) {
        if (!this.h) {
            this.c.contratarSeguroPresenter.showProgress();
            this.i = botonSeguroBean;
            this.c.mDataManager.getFirmaSeguro();
            this.h = true;
        }
    }

    public void validarTarjetasAseguradas(SegurosBean segurosBean) {
        if (segurosBean != null && segurosBean.getListaSeguros() != null) {
            for (SeguroBean seguroBean : segurosBean.getListaSeguros()) {
                if (!seguroBean.getCodRamo().equalsIgnoreCase(CodRamo.TENENCIA_COMPRA_PROTEGIDA)) {
                    if (seguroBean.getCodRamo().equalsIgnoreCase(CodRamo.TENENCIA_COMPRA_PROTEGIDA_2)) {
                    }
                }
                this.b = true;
                return;
            }
        }
    }

    @Subscribe
    public void onGetFirmaSeguro(GetFirmaSeguroEvent getFirmaSeguroEvent) {
        this.c.contratarSeguroPresenter.closeProgress();
        final GetFirmaSeguroEvent getFirmaSeguroEvent2 = getFirmaSeguroEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.FIRMA_SEGURO, (BaseActivity) getActivity(), this.ad.getText().toString()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ((ContratarSeguroActivity) ListaSegurosFragment.this.getActivity()).changeFragment(new SeguroWebViewFragment(getFirmaSeguroEvent2.getResponse().getFirmaSeguroBodyResponseBean, ListaSegurosFragment.this.i), FragmentConstants.SEGURO_WEB_VIEW);
            }
        };
        r1.handleWSResponse(getFirmaSeguroEvent);
    }

    private void a(List<BotonSeguroBean> list, String str, String str2) {
        BotonSeguroBean botonSeguroBean = new BotonSeguroBean();
        botonSeguroBean.setNombre(str);
        botonSeguroBean.setDescripcion(str2);
        list.add(botonSeguroBean);
    }
}
