package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesDistribucionLimitesAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesDistribucionLimitesAdapter.Event;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaProductosRecalificacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ProductoRecalificacionItem;
import java.util.ArrayList;

public class RecalificacionesDistribucionLimitesFragment extends BaseFragment {
    private View a;
    private int ad = 0;
    private String ae = "03";
    private RecyclerView b;
    private Button c;
    private RecalificacionesDistribucionLimitesAdapter d;
    private int e;
    private int f;
    private ListaProductosRecalificacionBean g;
    private ListaLeyendas h;
    private String i;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.e = getArguments().getInt("nuevaLineaCrediticia");
        this.f = getArguments().getInt("limiteSinAsignar");
        this.g = (ListaProductosRecalificacionBean) getArguments().getParcelable("listaProductos");
        this.h = (ListaLeyendas) getArguments().getParcelable("listaLeyendas");
        this.i = getArguments().getString("codReca");
        y();
    }

    private void y() {
        String str;
        this.d = new RecalificacionesDistribucionLimitesAdapter(new Event() {
            public void onVariacionChange(int i) {
                RecalificacionesDistribucionLimitesFragment.this.c(i);
            }
        }, this.e, getContext());
        RecalificacionesDistribucionLimitesAdapter recalificacionesDistribucionLimitesAdapter = this.d;
        if (this.i == null || !this.i.equals("1")) {
            str = getString(R.string.ID_4838_RECALIFICACION_LBL_LINEA_TOTAL_CREDITICIA_STRING);
        } else {
            str = getString(R.string.ID_4843_RECALIFICACION_LBL_NUEVA_LINEA_TOTAL_CREDITICIA);
        }
        recalificacionesDistribucionLimitesAdapter.setTitle(str);
        ArrayList arrayList = new ArrayList();
        if (this.h != null) {
            Leyenda leyendaByTag = this.h.getLeyendaByTag("NVO_LIM_TARJETA");
            if (leyendaByTag != null) {
                arrayList.add(leyendaByTag);
            }
        }
        for (ProductoRecalificacionItem productoRecalificacionItem : this.g.producto) {
            productoRecalificacionItem.setLimiteAnteriorProducto(String.valueOf(productoRecalificacionItem.getLimiteActualProd()));
            productoRecalificacionItem.setNuevoLimiteProducto(productoRecalificacionItem.getIdProducto().equalsIgnoreCase(this.ae) ? "" : String.valueOf(productoRecalificacionItem.getLimiteActualProd()));
            productoRecalificacionItem.setNuevoLimiteProductoAux(productoRecalificacionItem.getLimiteActualProd().intValue());
            this.ad += productoRecalificacionItem.getIdProducto().equalsIgnoreCase(this.ae) ? 0 : productoRecalificacionItem.getLimiteActualProd().intValue();
        }
        this.ad = this.e - this.ad;
        this.d.updateList(this.g.producto, arrayList, this.f, this.ad);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.a = layoutInflater.inflate(R.layout.fragment_recalificaciones_distribucion_limites, viewGroup, false);
        z();
        this.b = (RecyclerView) this.a.findViewById(R.id.rvDisribucionLimites);
        this.c = (Button) this.a.findViewById(R.id.btnConfirmar);
        this.b.setLayoutManager(new LinearLayoutManager(getContext()));
        this.b.setHasFixedSize(true);
        return this.a;
    }

    private void z() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        ((ImageView) ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecalificacionesDistribucionLimitesFragment.this.onBackPressed();
            }
        });
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.c.setText(R.string.ID_4852_RECALIFICACION_BTN_CONTINUAR);
        this.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecalificacionesDistribucionLimitesFragment.this.gotoRecalificacionesConfirmarFragment();
            }
        });
        this.b.setAdapter(this.d);
    }

    /* access modifiers changed from: private */
    public void c(int i2) {
        this.f = i2;
        this.d.updateNotAssignedLimit(this.f);
        this.d.notifyDataSetChanged();
    }

    public void gotoRecalificacionesConfirmarFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("limiteTotalActual", this.e);
        bundle.putInt("nuevoLimiteTotal", this.e);
        bundle.putParcelable("listaProductos", this.g);
        bundle.putParcelable("listaLeyendas", this.h);
        bundle.putString("codReca", this.i);
        bundle.putInt("limiteSinAsignar", this.f);
        RecalificacionConfirmarFragment newInstance = RecalificacionConfirmarFragment.newInstance();
        newInstance.setArguments(bundle);
        ((SantanderRioMainActivity) getActivity()).changeFragmentAnimation(newInstance, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
    }

    public void onBackPressed() {
        ((SantanderRioMainActivity) getActivity()).backLastFragment();
    }
}
