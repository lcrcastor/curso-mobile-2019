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
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionComprobanteAdapter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaProductosRecalificacionBean;
import java.util.ArrayList;
import javax.inject.Inject;

public class RecalificacionComprobanteFragment extends ITRSABaseFragment {
    private View a;
    private String ad;
    private RecyclerView b;
    private Button c;
    private String d;
    private String e;
    private ListaProductosRecalificacionBean f;
    private ListaLeyendas g;
    private String h;
    private String i;
    @Inject
    public IDataManager mDataManager;

    public static RecalificacionComprobanteFragment newInstance() {
        return new RecalificacionComprobanteFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setShareReceiptListener((SantanderRioMainActivity) getActivity());
        this.e = getArguments().getString("codReca");
        this.h = getArguments().getString("limiteSinAsignar");
        this.d = getArguments().getString("nuevaLineaCrediticia");
        this.i = getArguments().getString("fechaSolicitud");
        this.ad = getArguments().getString("nroComprobante");
        this.f = (ListaProductosRecalificacionBean) getArguments().getParcelable("listaProductos");
        this.g = (ListaLeyendas) getArguments().getParcelable("listaLeyendas");
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.a = layoutInflater.inflate(R.layout.fragment_recalificacion_comprobante, viewGroup, false);
        this.b = (RecyclerView) this.a.findViewById(R.id.rvProductos);
        this.c = (Button) this.a.findViewById(R.id.btnConfirmar);
        configureShareReceipt(this.a.findViewById(R.id.relativeLayout), this.ad, getString(R.string.IDXX_SHARE_RECALIFICACION));
        createActionBarSharedreceipt();
        this.b.setLayoutManager(new LinearLayoutManager(getContext()));
        return this.a;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        String string = getString(this.e.equalsIgnoreCase("1") ? R.string.ID_4843_RECALIFICACION_LBL_NUEVA_LINEA_TOTAL_CREDITICIA : R.string.ID_4838_RECALIFICACION_LBL_LINEA_TOTAL_CREDITICIA_STRING);
        this.c.setText(R.string.ID_4859_RECALIFICACION_BTN_VOLVER);
        this.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecalificacionComprobanteFragment.this.canExit();
            }
        });
        this.b.setHasFixedSize(true);
        RecalificacionComprobanteAdapter recalificacionComprobanteAdapter = new RecalificacionComprobanteAdapter(getContext(), string, this.d, this.f.producto, this.g != null ? this.g.lstLeyendas : new ArrayList(), this.h, this.i, this.ad);
        this.b.setAdapter(recalificacionComprobanteAdapter);
    }
}
