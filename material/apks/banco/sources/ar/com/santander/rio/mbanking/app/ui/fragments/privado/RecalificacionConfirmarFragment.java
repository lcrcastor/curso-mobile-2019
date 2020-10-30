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
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CFormatterAmounts;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsConfirmarAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ConfirmarRecalificacionEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaProductosRecalificacionBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VConfirmarRecalificacion;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import javax.inject.Inject;

public class RecalificacionConfirmarFragment extends BaseFragment {
    private RecyclerView a;
    private Button b;
    /* access modifiers changed from: private */
    public int c;
    /* access modifiers changed from: private */
    public String d;
    private int e;
    private String f;
    /* access modifiers changed from: private */
    public ListaProductosRecalificacionBean g;
    private ListaLeyendas h;
    @Inject
    public IDataManager mDataManager;

    public static RecalificacionConfirmarFragment newInstance() {
        return new RecalificacionConfirmarFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.g = (ListaProductosRecalificacionBean) getArguments().getParcelable("listaProductos");
        this.h = (ListaLeyendas) getArguments().getParcelable("listaLeyendas");
        this.c = getArguments().getInt("nuevoLimiteTotal");
        this.d = getArguments().getString("codReca");
        this.e = getArguments().getInt("limiteSinAsignar");
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_recalificacion_confirmar, viewGroup, false);
        y();
        this.a = (RecyclerView) inflate.findViewById(R.id.rvProductos);
        this.b = (Button) inflate.findViewById(R.id.btnConfirmar);
        this.a.setLayoutManager(new LinearLayoutManager(getContext()));
        return inflate;
    }

    private void y() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        ((ImageView) ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecalificacionConfirmarFragment.this.onBackPressed();
            }
        });
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        String amountAndCurrencyFromDouble = CFormatterAmounts.getAmountAndCurrencyFromDouble((double) this.c);
        if (this.d.equals("1")) {
            this.f = getString(R.string.ID_4843_RECALIFICACION_LBL_NUEVA_LINEA_TOTAL_CREDITICIA);
        } else if (this.d.equals("2")) {
            this.f = getString(R.string.ID_4838_RECALIFICACION_LBL_LINEA_TOTAL_CREDITICIA_STRING);
        }
        this.b.setText(R.string.ID_4854_RECALIFICACION_BTN_CONFIRMAR_NUEVOS_LIMITES);
        this.b.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecalificacionConfirmarFragment.this.gotoRecalificacionesComprobanteFragment();
            }
        });
        this.a.setHasFixedSize(true);
        RecalificacionesProductsConfirmarAdapter recalificacionesProductsConfirmarAdapter = new RecalificacionesProductsConfirmarAdapter(getContext(), this.g.producto, (this.h == null || this.h.lstLeyendas == null) ? new ArrayList() : this.h.lstLeyendas, amountAndCurrencyFromDouble, this.f, this.e);
        this.a.setAdapter(recalificacionesProductsConfirmarAdapter);
    }

    public void gotoRecalificacionesComprobanteFragment() {
        final SantanderRioMainActivity santanderRioMainActivity = (SantanderRioMainActivity) getActivity();
        if (santanderRioMainActivity != null) {
            final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(santanderRioMainActivity.getString(R.string.recalificacion_confirmar), santanderRioMainActivity.getString(R.string.recalificacion_confirmar_cambio_limites_pregunta), null, null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    santanderRioMainActivity.showProgress(VConfirmarRecalificacion.nameService);
                    RecalificacionConfirmarFragment.this.mDataManager.confirmarRecalificacion(RecalificacionConfirmarFragment.this.g, RecalificacionConfirmarFragment.this.c);
                }

                public void onNegativeButton() {
                    newInstance.dismiss();
                }
            });
            newInstance.show(getFragmentManager(), "Cambio de limites popUp");
        }
    }

    @Subscribe
    public void onConfirmarRecalificacionEvent(ConfirmarRecalificacionEvent confirmarRecalificacionEvent) {
        ((SantanderRioMainActivity) getActivity()).dismissProgress();
        final ConfirmarRecalificacionEvent confirmarRecalificacionEvent2 = confirmarRecalificacionEvent;
        AnonymousClass4 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.RECALIFICACIONES, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ConfirmarRecalificacionResponseBean confirmarRecalificacionResponseBean = confirmarRecalificacionEvent2.getConfirmarRecalificacionResponseBean();
                Bundle bundle = new Bundle();
                bundle.putString("codReca", RecalificacionConfirmarFragment.this.d);
                bundle.putString("limiteSinAsignar", confirmarRecalificacionResponseBean.confirmarRecalificacionBodyResponseBean.limiteSinAsignar);
                bundle.putString("nuevaLineaCrediticia", confirmarRecalificacionResponseBean.confirmarRecalificacionBodyResponseBean.nuevaLineaCrediticia);
                bundle.putString("fechaSolicitud", confirmarRecalificacionResponseBean.confirmarRecalificacionBodyResponseBean.fechaSolicitud);
                bundle.putString("nroComprobante", confirmarRecalificacionResponseBean.confirmarRecalificacionBodyResponseBean.nroComprobante);
                bundle.putParcelable("listaProductos", confirmarRecalificacionResponseBean.confirmarRecalificacionBodyResponseBean.listaProductos);
                bundle.putParcelable("listaLeyendas", confirmarRecalificacionResponseBean.confirmarRecalificacionBodyResponseBean.listaLeyendas);
                RecalificacionComprobanteFragment newInstance = RecalificacionComprobanteFragment.newInstance();
                newInstance.setArguments(bundle);
                ((SantanderRioMainActivity) RecalificacionConfirmarFragment.this.getActivity()).changeFragmentAnimation(newInstance, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
            }
        };
        r1.handleWSResponse(confirmarRecalificacionEvent);
    }

    public void onBackPressed() {
        ((SantanderRioMainActivity) getActivity()).backLastFragment();
    }
}
