package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.AbmTurnoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class UbicacionTurnoFragment extends BaseFragment {
    public static final String GET_TURNO_BODY_RESPONSE = "GET_TURNO_BODY_RESPONSE";
    public static final String GET_TURNO_CANCELACION = "GET_TURNO_CANCELACION";
    private TextView a;
    private View ad;
    private Button ae;
    /* access modifiers changed from: private */
    public GoogleMap af;
    /* access modifiers changed from: private */
    public GetTurnoBodyResponseBean ag;
    private HtmlTextView b;
    private HtmlTextView c;
    private HtmlTextView d;
    private HtmlTextView e;
    private HtmlTextView f;
    private HtmlTextView g;
    private TextView h;
    private MapView i;
    @Inject
    public IDataManager mDataManager;

    public static Fragment newInstance(GetTurnoBodyResponseBean getTurnoBodyResponseBean) {
        UbicacionTurnoFragment ubicacionTurnoFragment = new UbicacionTurnoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GET_TURNO_BODY_RESPONSE, getTurnoBodyResponseBean);
        ubicacionTurnoFragment.setArguments(bundle);
        return ubicacionTurnoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.ag = (GetTurnoBodyResponseBean) getArguments().getParcelable(GET_TURNO_BODY_RESPONSE);
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.ad = layoutInflater.inflate(R.layout.fragment_ubicacion_turno, viewGroup, false);
        y();
        z();
        g(bundle);
        return this.ad;
    }

    private void g(Bundle bundle) {
        this.i.onCreate(bundle);
        this.i.onResume();
        MapsInitializer.initialize(getContext());
        this.i.getMapAsync(new OnMapReadyCallback() {
            public void onMapReady(GoogleMap googleMap) {
                UbicacionTurnoFragment.this.af = googleMap;
                UbicacionTurnoFragment.this.af.getUiSettings().setMyLocationButtonEnabled(false);
                UbicacionTurnoFragment.this.af.getUiSettings().setAllGesturesEnabled(false);
                LatLng latLng = new LatLng(Double.valueOf(UbicacionTurnoFragment.this.ag.getTurno().getSucursal().latitud).doubleValue(), Double.valueOf(UbicacionTurnoFragment.this.ag.getTurno().getSucursal().longitud).doubleValue());
                UbicacionTurnoFragment.this.af.addMarker(new MarkerOptions().position(latLng));
                UbicacionTurnoFragment.this.af.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(latLng).zoom(15.0f).build()));
            }
        });
    }

    private void y() {
        this.a = (TextView) this.ad.findViewById(R.id.functionality_title);
        this.b = (HtmlTextView) this.ad.findViewById(R.id.tvSucursal);
        this.d = (HtmlTextView) this.ad.findViewById(R.id.tvTurnoParaEl);
        this.e = (HtmlTextView) this.ad.findViewById(R.id.tvNroTicketTurno);
        this.f = (HtmlTextView) this.ad.findViewById(R.id.tvTiempoEstimado);
        this.g = (HtmlTextView) this.ad.findViewById(R.id.tvTurnoSolicitado);
        this.c = (HtmlTextView) this.ad.findViewById(R.id.tvDireccion);
        this.h = (TextView) this.ad.findViewById(R.id.leyenda);
        this.ae = (Button) this.ad.findViewById(R.id.cancelButton);
        this.i = (MapView) this.ad.findViewById(R.id.map);
    }

    private void z() {
        this.ae.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UbicacionTurnoFragment.this.A();
            }
        });
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        this.a.setText(getContext().getString(R.string.ID_4872_TURNOS_TIT_TICKET_PARA_ATENCION_EN_CAJA));
        this.a.setTypeface(null, 1);
        this.b.setText(this.ag.getTurno().getSucursal().descripcion);
        this.b.setContentDescription(CAccessibility.getInstance(getContext()).applyGuion(this.b.getText().toString()));
        this.c.setText(this.ag.getTurno().getSucursal().direccion);
        this.c.setContentDescription(CAccessibility.getInstance(getContext()).applyFilter_BsAs(CAccessibility.getInstance(getContext()).applyFilter_Ciudad(CAccessibility.getInstance(getContext()).applyGuion(CAccessibility.getInstance(getContext()).applyFilterDireccion(Html.fromHtml(this.ag.getTurno().getSucursal().direccion).toString())))));
        this.d.setText(getContext().getString(R.string.ID_4873_TURNOS_LBL_RECORDA_LOS_DATOS_DE_ESTE_TICKET_PARA_SER_ATENDIDO_EL));
        try {
            this.d.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterDate(this.d.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.e.setText(this.ag.getTurno().getNumeroTicket());
        this.f.setText(this.ag.getTurno().getTiempoEspera());
        try {
            this.f.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterTime(this.f.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        HtmlTextView htmlTextView = this.g;
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.ID_4875_TURNOS_LBL_TICKET_SOLICITADO_EL_));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.ag.getTurno().getFechaCreacion());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getContext().getString(R.string.ID_4876_TURNOS_LBL_A_LAS_));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.ag.getTurno().getHoraCreacion());
        htmlTextView.setText(sb.toString());
        try {
            this.g.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterHS(CAccessibility.getInstance(getContext()).applyFilterDate(this.g.getText().toString())));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.h.setText(Html.fromHtml(this.ag.getListaLegales().getLegalById("TICKET_TURNO") != null ? this.ag.getListaLegales().getLegalById("TICKET_TURNO").getDescripcion() : "").toString());
        this.ae.setText(getContext().getString(R.string.ID_4877_TURNOS_BTN_CANCELAR_TICKET));
    }

    /* access modifiers changed from: private */
    public void A() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("CancelDialog", "Confirmar", getContext().getString(R.string.MSG_USER00534_Turnos), null, null, "Sí", "No", null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                UbicacionTurnoFragment.this.mDataManager.abmTurno("B", "", "", UbicacionTurnoFragment.this.ag.getTurno().getIdTurno());
            }

            public void onNegativeButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(((SantanderRioMainActivity) getActivity()).getSupportFragmentManager(), "CancelDialog");
    }

    @Subscribe
    public void abmTurno(AbmTurnoEvent abmTurnoEvent) {
        dismissProgress();
        final AbmTurnoEvent abmTurnoEvent2 = abmTurnoEvent;
        AnonymousClass4 r0 = new BaseWSResponseHandler(getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_COTIZACION, (BaseActivity) getContext(), "Confirmar ticket para atención en caja") {
            /* access modifiers changed from: protected */
            public void onOk() {
                UbicacionTurnoFragment.this.changeFragmentAnimation(ComprobanteTurnoFragment.newInstance(abmTurnoEvent2.getResponse().getAbmTurnoBodyResponseBean(), UbicacionTurnoFragment.this.ag.getTurno().getSucursal(), UbicacionTurnoFragment.this.ag.getTurno(), "B"));
            }
        };
        r0.handleWSResponse(abmTurnoEvent);
    }

    public void changeFragmentAnimation(Fragment fragment) {
        getFragmentManager().beginTransaction().addToBackStack(fragment.getClass().getSimpleName()).setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left).replace(R.id.content_frame, fragment, "tag_fragment_load").commit();
    }
}
