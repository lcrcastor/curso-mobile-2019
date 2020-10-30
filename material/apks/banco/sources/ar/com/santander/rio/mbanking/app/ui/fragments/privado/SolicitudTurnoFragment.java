package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.activities.GestionTurnoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.SolicitudTurnoAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.SolicitudTurnoAdapter.Data;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.SucursalesFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetSucursalesSolicitudEvent;
import ar.com.santander.rio.mbanking.services.events.GetTurnoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UbicacionBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetSucursalesTurno;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager.OnLocationChangeListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.location.LocationServices;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SolicitudTurnoFragment extends BaseFragment implements LocationListener, OnLocationChangeListener, ConnectionCallbacks, OnConnectionFailedListener {
    @Inject
    SessionManager a;
    private boolean ad = false;
    private boolean ae = false;
    private boolean af = false;
    private View b;
    private TextView c;
    private Button d;
    private RecyclerView e;
    /* access modifiers changed from: private */
    public GetTurnoEvent f;
    /* access modifiers changed from: private */
    public GoogleApiClient g;
    private Location h;
    private LocationManager i;
    @Inject
    public IDataManager mDataManager;

    public interface OnFragmentInteractionListener {
    }

    public void onLocationListener(Location location) {
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i2, Bundle bundle) {
    }

    public static SolicitudTurnoFragment newInstance(GetTurnoEvent getTurnoEvent) {
        SolicitudTurnoFragment solicitudTurnoFragment = new SolicitudTurnoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(UbicacionTurnoFragment.GET_TURNO_BODY_RESPONSE, getTurnoEvent);
        solicitudTurnoFragment.setArguments(bundle);
        return solicitudTurnoFragment;
    }

    public static SolicitudTurnoFragment newInstance(boolean z, GetTurnoEvent getTurnoEvent) {
        SolicitudTurnoFragment solicitudTurnoFragment = new SolicitudTurnoFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("FUERA_HORARIO", z);
        bundle.putParcelable(UbicacionTurnoFragment.GET_TURNO_BODY_RESPONSE, getTurnoEvent);
        solicitudTurnoFragment.setArguments(bundle);
        return solicitudTurnoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.f = (GetTurnoEvent) getArguments().getParcelable(UbicacionTurnoFragment.GET_TURNO_BODY_RESPONSE);
            this.ad = getArguments().getBoolean("FUERA_HORARIO");
        }
        this.i = (LocationManager) getContext().getSystemService("location");
        initializeGoogleApiClient();
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.b = layoutInflater.inflate(R.layout.fragment_solicitud_turno, viewGroup, false);
        if (this.f.getResult() != TypeResult.BEAN_ERROR_RES_4) {
            initializeLayout();
            validateTurno();
        } else {
            a(this.f);
        }
        return this.b;
    }

    public void validateTurno() {
        if (this.ad) {
            View findViewById = this.b.findViewById(R.id.fuera_horario);
            View findViewById2 = this.b.findViewById(R.id.sub_title);
            TextView textView = (TextView) this.b.findViewById(R.id.title_error);
            TextView textView2 = (TextView) this.b.findViewById(R.id.description_error);
            ((ImageView) this.b.findViewById(R.id.image_error)).setBackgroundResource(R.drawable.ico_reloj_gris);
            findViewById.setVisibility(0);
            findViewById2.setVisibility(8);
            this.e.setVisibility(8);
            this.d.setVisibility(8);
            textView.setText("Atención");
            textView2.setText(Html.fromHtml(this.f.getMessageToShow()));
            return;
        }
        setValueFields();
    }

    private void a(GetTurnoEvent getTurnoEvent) {
        AnonymousClass1 r0 = new BaseWSResponseHandler(getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.NERS_SOLICITAR_TURNO_CAJA, this, (BaseActivity) getActivity(), "Solicitar Ticket para Atención en Caja") {
            /* access modifiers changed from: protected */
            public void onOk() {
            }
        };
        r0.handleWSResponse(getTurnoEvent);
    }

    public List<Data> getListaDescription() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Data(R.drawable.ubicacion, getContext().getString(R.string.ID_4887_TURNOS_LBL_SELECCIONA_LA_SUCURSAL_DONDE_QUERES_REALIZAR_TU_TRAMITE), getContext().getText(R.string.ID_4868_TURNOS_LBL_TIP_TE_MOSTRAREMOS_UN_MAPA_DONDE_PODRAS_VER_LAS_SUCURSALES_MAS_CERCANAS_A_TU_UBICACION_Y_EL_TIEMPO_ESTIMADO_DE_ESPERA_DE_CADA_UNA_DE_ELLAS).toString()));
        arrayList.add(new Data(R.drawable.radio, getContext().getString(R.string.ID_4869_TURNOS_LBL_INDICA_LA_TRANSACCION_QUE_QUERES_REALIZAR_EN_LA_CAJA)));
        arrayList.add(new Data(R.drawable.check_item, getContext().getString(R.string.ID_4870_TURNOS_LBL_LISTO)));
        return arrayList;
    }

    public void initializeLayout() {
        TextView textView = (TextView) this.b.findViewById(R.id.functionality_title);
        this.c = (TextView) this.b.findViewById(R.id.section_title);
        this.d = (Button) this.b.findViewById(R.id.qoute_button);
        this.e = (RecyclerView) this.b.findViewById(R.id.family_questions_form);
        this.e.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        textView.setText(getContext().getString(R.string.ID_4865_TURNOS_TIT_SOLICITAR_TICKET_PARA_ATENCION_EN_CAJA));
        textView.setTypeface(null, 1);
        this.d.setText(getContext().getString(R.string.ID_4871_TURNOS_BTN_SOLICITAR_TICKET_PARA_CAJA));
        this.d.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SolicitudTurnoFragment.this.showProgress(VGetSucursalesTurno.nameService);
                SolicitudTurnoFragment.this.z();
            }
        });
        this.e.setAdapter(new SolicitudTurnoAdapter(getListaDescription()));
    }

    public void setValueFields() {
        if (this.f.getResponse().getTurnoBodyResponseBean.getListaLegales().getLegalById("TICKET_TURNO").getDescripcion() != null) {
            this.c.setText(getContext().getString(R.string.ID_4866_TURNOS_LBL_AHORRA_TIEMPO_Y_SOLICITA_ONLINE_TU_TICKET_PARA_SER_ATENDIDO_EN_CAJA));
            this.c.setGravity(3);
        }
    }

    @Subscribe
    public void getSucursalesTurnoSolicitud(GetSucursalesSolicitudEvent getSucursalesSolicitudEvent) {
        dismissProgress();
        final GetSucursalesSolicitudEvent getSucursalesSolicitudEvent2 = getSucursalesSolicitudEvent;
        AnonymousClass3 r0 = new BaseWSResponseHandler(getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.NERS_ENCOLADOR, this, (BaseActivity) getActivity(), "Solicitar Ticket para Atención en Caja") {
            /* access modifiers changed from: protected */
            public void onOk() {
                Intent intent = new Intent(SolicitudTurnoFragment.this.getContext(), GestionTurnoActivity.class);
                intent.putExtra(SeleccionSucursalTurnoFragment.GET_SUCURSALES_TURNO, getSucursalesSolicitudEvent2.getResponseBean().getGetSucursalesBodyResponseBean());
                intent.putExtra(SeleccionSucursalTurnoFragment.GET_TURNO, SolicitudTurnoFragment.this.f.getResponse().getTurnoBodyResponseBean);
                SolicitudTurnoFragment.this.startActivity(intent);
                ((SantanderRioMainActivity) SolicitudTurnoFragment.this.getActivity()).overridePendingTransition(0, 0);
            }
        };
        r0.handleWSResponse(getSucursalesSolicitudEvent);
    }

    /* access modifiers changed from: protected */
    public void initializeGoogleApiClient() {
        if (this.g == null) {
            this.g = new Builder(getContext()).addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ)).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
    }

    private boolean y() {
        return this.i.isProviderEnabled("gps");
    }

    /* access modifiers changed from: private */
    public void z() {
        if (!y()) {
            a(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.ACCESS_FINE_LOCATION_USER_EXPLAIN_SUCURSALES));
        } else if (ContextCompat.checkSelfPermission(getContext(), "android.permission.ACCESS_FINE_LOCATION") != 0) {
            dismissProgress();
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 2);
        } else {
            A();
        }
    }

    private void A() {
        if (this.g == null) {
            initializeGoogleApiClient();
        }
        B();
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        if (i2 == 2) {
            for (int i3 = 0; i3 < strArr.length; i3++) {
                String str = strArr[i3];
                int i4 = iArr[i3];
                if (str.equals("android.permission.ACCESS_FINE_LOCATION")) {
                    if (i4 == 0) {
                        A();
                    } else {
                        showProgress(VGetSucursalesTurno.nameService);
                        UbicacionBean ubicacionBean = new UbicacionBean();
                        ubicacionBean.setLongitud(String.valueOf(-58.373381d));
                        ubicacionBean.setLatitud(String.valueOf(-34.606737d));
                        this.mDataManager.getSucursalesTurnoSolicitud("", "1", ubicacionBean);
                    }
                }
            }
        }
    }

    private void B() {
        Handler handler = new Handler();
        setTimeOut(true);
        setRefreshGPS(false);
        this.g.connect();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (SolicitudTurnoFragment.this.isTimeOut() || !SolicitudTurnoFragment.this.isRefreshGPS()) {
                    SolicitudTurnoFragment.this.g.clearDefaultAccountAndReconnect();
                }
            }
        }, LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        handler.postDelayed(new Runnable() {
            public void run() {
                if (SolicitudTurnoFragment.this.isTimeOut()) {
                    SolicitudTurnoFragment.this.dismissProgress();
                    SolicitudTurnoFragment.this.b(SolicitudTurnoFragment.this.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), SolicitudTurnoFragment.this.getString(R.string.MSG_USER000004));
                }
            }
        }, LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS);
    }

    public void onConnected(Bundle bundle) {
        setTimeOut(false);
        C();
    }

    private void a(String str, String str2) {
        dismissProgress();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
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
        newInstance.show(getFragmentManager(), SucursalesFragment.ACCESS_FINE_LOCATION_DIALOG_TAG);
    }

    /* access modifiers changed from: private */
    public void b(String str, String str2) {
        dismissProgress();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                SolicitudTurnoFragment.this.showProgress(VGetSucursalesTurno.nameService);
                UbicacionBean ubicacionBean = new UbicacionBean();
                ubicacionBean.setLongitud(String.valueOf(-58.373381d));
                ubicacionBean.setLatitud(String.valueOf(-34.606737d));
                SolicitudTurnoFragment.this.mDataManager.getSucursalesTurnoSolicitud("", "1", ubicacionBean);
            }
        });
        newInstance.show(getFragmentManager(), SucursalesFragment.ACCESS_FINE_LOCATION_DIALOG_TAG);
    }

    public void onConnectionSuspended(int i2) {
        dismissProgress();
        b(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000004));
    }

    private void C() {
        if (ContextCompat.checkSelfPermission(getContext(), "android.permission.ACCESS_FINE_LOCATION") == 0) {
            this.h = LocationServices.FusedLocationApi.getLastLocation(this.g);
            setRefreshGPS(true);
            a(this.h);
        }
    }

    private void a(Location location) {
        if (location == null || String.valueOf(location.getLongitude()).isEmpty() || String.valueOf(location.getLatitude()).isEmpty()) {
            b(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000004));
            return;
        }
        UbicacionBean ubicacionBean = new UbicacionBean();
        ubicacionBean.setLongitud(String.valueOf(location.getLongitude()));
        ubicacionBean.setLatitud(String.valueOf(location.getLatitude()));
        this.mDataManager.getSucursalesTurnoSolicitud("", "1", ubicacionBean);
    }

    public void onLocationChanged(Location location) {
        this.h = location;
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        dismissProgress();
        b(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000004));
    }

    public boolean isTimeOut() {
        return this.ae;
    }

    public void setTimeOut(boolean z) {
        this.ae = z;
    }

    public boolean isRefreshGPS() {
        return this.af;
    }

    public void setRefreshGPS(boolean z) {
        this.af = z;
    }
}
