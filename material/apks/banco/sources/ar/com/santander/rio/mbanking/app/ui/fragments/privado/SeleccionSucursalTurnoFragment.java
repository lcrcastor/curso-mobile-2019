package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.adapters.MapInfiniteSucursalesTurnoAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.NesrConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.SucursalesFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.SlidingUpPanelLayout;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollListView;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollOnScrollListener;
import ar.com.santander.rio.mbanking.components.itrsa.AdapterView.OnItemClickedListener.OneItemClicked;
import ar.com.santander.rio.mbanking.components.mapInfo.MyMarker;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.services.events.GetCircuitoTurnoEvent;
import ar.com.santander.rio.mbanking.services.events.GetSucursalesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UbicacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ValoresBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCircuitoTurno;
import ar.com.santander.rio.mbanking.services.soap.versions.VSucursales;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import ar.com.santander.rio.mbanking.view.ClearableEditText.OnClearListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.HashMap;

public class SeleccionSucursalTurnoFragment extends SucursalesFragment implements OneItemClicked {
    public static final String GET_SUCURSALES_TURNO = "GET_SUCURSALES_TURNO";
    public static final String GET_SUCURSAL_SELECCIONADA = "GET_SUCURSAL_SELECCIONADA";
    public static final String GET_TURNO = "GET_TURNO";
    /* access modifiers changed from: private */
    public MapInfiniteSucursalesTurnoAdapter ad;
    /* access modifiers changed from: private */
    public Context ae;
    /* access modifiers changed from: private */
    public Activity af;
    /* access modifiers changed from: private */
    public SucursalBean ag;
    /* access modifiers changed from: private */
    public IActionCustom ah;
    private GetSucursalesBodyResponseBean b;
    /* access modifiers changed from: private */
    public GetTurnoBodyResponseBean c;
    private TextView d;
    private TextView e;
    private ImageButton f;
    private ImageView g;
    private TextView h;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener i;

    public class MarkerInfoWindowAdapter implements InfoWindowAdapter {
        private final View b;

        public View getInfoWindow(Marker marker) {
            return null;
        }

        public MarkerInfoWindowAdapter(int i) {
            this.b = SeleccionSucursalTurnoFragment.this.af.getLayoutInflater().inflate(i, null);
        }

        public View getInfoContents(Marker marker) {
            MyMarker myMarker = (MyMarker) SeleccionSucursalTurnoFragment.this.mMarkersHashMap.get(marker);
            if (myMarker == null || !myMarker.isClickable) {
                return null;
            }
            TextView textView = (TextView) this.b.findViewById(R.id.txtTitle);
            StringBuilder sb = new StringBuilder();
            sb.append(Html.fromHtml(myMarker.numeroSucursal).toString());
            sb.append(" - ");
            sb.append(Html.fromHtml(myMarker.title).toString());
            textView.setText(sb.toString());
            textView.setContentDescription(CAccessibility.getInstance(SeleccionSucursalTurnoFragment.this.ae).applyGuion(textView.getText().toString()));
            ((TextView) this.b.findViewById(R.id.txtDirec)).setText(Html.fromHtml(myMarker.direc));
            RelativeLayout relativeLayout = (RelativeLayout) this.b.findViewById(R.id.rrTiempoDeEsperaYLinkTicket);
            RelativeLayout relativeLayout2 = (RelativeLayout) this.b.findViewById(R.id.rrVer);
            TextView textView2 = (TextView) this.b.findViewById(R.id.tiempoDeEsperaInfoPopUp);
            TextView textView3 = (TextView) this.b.findViewById(R.id.SolicitarTicketInfoPopUp);
            if (myMarker.tiempoEspera != null) {
                int i = 0;
                relativeLayout.setVisibility(0);
                if (myMarker.tiempoEspera.equals(NesrConstants.SUCURSAL_CERRADA)) {
                    i = 4;
                }
                textView3.setVisibility(i);
                relativeLayout2.setVisibility(8);
                textView2.setText(myMarker.tiempoEspera);
                try {
                    textView2.setContentDescription(CAccessibility.getInstance(SeleccionSucursalTurnoFragment.this.ae).applyFilterTime(myMarker.tiempoEspera));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            TextView textView4 = (TextView) this.b.findViewById(R.id.txtDist);
            if (myMarker.dist != null) {
                textView4.setText(myMarker.dist);
            }
            return this.b;
        }
    }

    public interface OnFragmentInteractionListener extends IFragmentBase {
    }

    public interface OnItemClickDetails {
        void onItemClickDetailsSucursalTurno();
    }

    public static SeleccionSucursalTurnoFragment newInstance(GetSucursalesBodyResponseBean getSucursalesBodyResponseBean, GetTurnoBodyResponseBean getTurnoBodyResponseBean) {
        SeleccionSucursalTurnoFragment seleccionSucursalTurnoFragment = new SeleccionSucursalTurnoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GET_SUCURSALES_TURNO, getSucursalesBodyResponseBean);
        bundle.putParcelable(GET_TURNO, getTurnoBodyResponseBean);
        seleccionSucursalTurnoFragment.setArguments(bundle);
        return seleccionSucursalTurnoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.b = (GetSucursalesBodyResponseBean) getArguments().getParcelable(GET_SUCURSALES_TURNO);
            this.c = (GetTurnoBodyResponseBean) getArguments().getParcelable(GET_TURNO);
        }
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_seleccion_sucursal, viewGroup, false);
        try {
            initUI(inflate);
            this.analyticsManager.trackScreen(getString(R.string.analytics_screen_name_sucursales_home));
            this.i.configureBackActionBar();
            showProgress(VSucursales.nameService);
            initializeMap();
        } catch (Exception e2) {
            dismissProgress();
            Log.e("@dev", "Error al inflar la vista de sucursales.", e2);
        }
        return inflate;
    }

    /* access modifiers changed from: protected */
    public void initializeMap() {
        this.mapFragment = SupportMapFragment.newInstance();
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().replace(R.id.sucursal_map_fragmet, this.mapFragment).commit();
            this.mapFragment.getMapAsync(this);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        Handler handler = new Handler();
        if (ContextCompat.checkSelfPermission(this.af, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    SeleccionSucursalTurnoFragment.this.onInitRequestPermissionResponse();
                }
            }, 1000);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this.af, "android.permission.ACCESS_FINE_LOCATION")) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    SeleccionSucursalTurnoFragment.this.onInitRequestPermissionResponse();
                }
            }, 1000);
        } else {
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
        }
    }

    public void initUI(View view) {
        b(view);
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SeleccionSucursalTurnoFragment.this.expand();
            }
        });
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SeleccionSucursalTurnoFragment.this.cargarMas();
            }
        });
        this.g.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SeleccionSucursalTurnoFragment.this.performSearch();
            }
        });
        this.d.setText(getContext().getString(R.string.ID_4865_TURNOS_TIT_SOLICITAR_TICKET_PARA_ATENCION_EN_CAJA));
        this.d.setTypeface(null, 1);
        this.e.setText(getContext().getString(R.string.ID_4887_TURNOS_LBL_SELECCIONA_LA_SUCURSAL_DONDE_QUERES_REALIZAR_TU_TRAMITE));
        this.editText.setHint(getContext().getString(R.string.ID_4888_TURNOS_LBL_BUSCAR_SUCURSAL));
    }

    public void onDetach() {
        super.onDetach();
        this.i = null;
        this.ah = null;
    }

    public void showRequestPermissionExplation(final int i2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.ACCESS_FINE_LOCATION_USER_EXPLAIN_SUCURSALES), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                SeleccionSucursalTurnoFragment.this.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, i2);
            }
        });
        newInstance.show(getFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    /* access modifiers changed from: protected */
    public void requestSucursales(String str, String str2, LatLng latLng, ValoresBean valoresBean) {
        UbicacionBean ubicacionBean = new UbicacionBean();
        ubicacionBean.setLatitud(String.valueOf(latLng.latitude));
        ubicacionBean.setLongitud(String.valueOf(latLng.longitude));
        this.dataManager.getSucursalesTurno(str, str2, ubicacionBean);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.i = (OnFragmentInteractionListener) context;
            this.ah = (IActionCustom) context;
            this.ae = context;
            this.af = getActivity();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    private void b(View view) {
        this.d = (TextView) view.findViewById(R.id.functionality_title);
        this.e = (TextView) view.findViewById(R.id.section_title);
        this.mListView = (InfiniteScrollListView) view.findViewById(R.id.list);
        this.mSlidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.fragmentMap);
        this.editText = (ClearableEditText) view.findViewById(R.id.eTxtSearch);
        this.llButtonMap = (RelativeLayout) view.findViewById(R.id.llButtonMap);
        this.f = (ImageButton) view.findViewById(R.id.btnOpen);
        this.h = (TextView) view.findViewById(R.id.btnMasSucursales);
        this.noBusqueda = (LinearLayout) view.findViewById(R.id.noBusqueda);
        this.g = (ImageView) view.findViewById(R.id.imgSearch);
    }

    /* access modifiers changed from: protected */
    public void initializeListSucursales() {
        this.mListView.setListener(new InfiniteScrollOnScrollListener(this));
        this.mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                SeleccionSucursalTurnoFragment.this.ag = (SucursalBean) SeleccionSucursalTurnoFragment.this.ad.getItem(i);
                if (!SeleccionSucursalTurnoFragment.this.ag.tiempoEspera.equalsIgnoreCase(NesrConstants.SUCURSAL_CERRADA)) {
                    SeleccionSucursalTurnoFragment.this.showProgress(VGetCircuitoTurno.nameService);
                    SeleccionSucursalTurnoFragment.this.dataManager.getCircuitoTurno(SeleccionSucursalTurnoFragment.this.ag.numero);
                }
            }
        });
        createAdapter();
    }

    @Subscribe
    public void geCircuitoTurno(GetCircuitoTurnoEvent getCircuitoTurnoEvent) {
        dismissProgress();
        final GetCircuitoTurnoEvent getCircuitoTurnoEvent2 = getCircuitoTurnoEvent;
        AnonymousClass8 r0 = new BaseWSResponseHandler(getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_COTIZACION, (BaseActivity) getContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                SeleccionSucursalTurnoFragment.this.i.changeFragment(SolicitarTurnoCajaFragment.newInstance(getCircuitoTurnoEvent2.getResponse().getCircuitoTurnoBodyResponseBean, SeleccionSucursalTurnoFragment.this.ag, SeleccionSucursalTurnoFragment.this.c), FragmentConstants.NERS_SOLICITAR_TURNO_CAJA);
            }

            /* access modifiers changed from: protected */
            public void onRes5Error(WebServiceEvent webServiceEvent) {
                super.onRes5Error(webServiceEvent, SeleccionSucursalTurnoFragment.this.ah);
            }
        };
        r0.handleWSResponse(getCircuitoTurnoEvent);
    }

    @Subscribe
    public void getSucursalesTurno(GetSucursalesEvent getSucursalesEvent) {
        dismissProgress();
        hideKeyboard(this.af);
        final GetSucursalesEvent getSucursalesEvent2 = getSucursalesEvent;
        AnonymousClass9 r1 = new BaseWSResponseHandler(getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_COTIZACION, (BaseActivity) getContext(), "Solicitar Ticket para Atención en Caja") {
            /* access modifiers changed from: protected */
            public void onOk() {
                try {
                    GetSucursalesResponseBean getSucursalesResponseBean = (GetSucursalesResponseBean) getSucursalesEvent2.getBeanResponse();
                    if (getSucursalesEvent2.getResult() == TypeResult.OK) {
                        GetSucursalesBodyResponseBean getSucursalesBodyResponseBean = getSucursalesResponseBean.getGetSucursalesBodyResponseBean();
                        SeleccionSucursalTurnoFragment.this.executing = false;
                        SeleccionSucursalTurnoFragment.this.paginaMaxima = Integer.parseInt(getSucursalesBodyResponseBean.paginas);
                        if (SeleccionSucursalTurnoFragment.this.hasSucursales(getSucursalesBodyResponseBean)) {
                            if (SeleccionSucursalTurnoFragment.this.paginaMaxima != 0) {
                                if (!TextUtils.isEmpty(SeleccionSucursalTurnoFragment.this.editText.getText().toString())) {
                                    SeleccionSucursalTurnoFragment.this.analyticsManager.trackScreen(SeleccionSucursalTurnoFragment.this.getString(R.string.analytics_screen_name_sucursales_home_con_busqueda));
                                }
                                SeleccionSucursalTurnoFragment.this.noBusqueda.setVisibility(8);
                                SeleccionSucursalTurnoFragment.this.mListView.appendItems(new ArrayList(getSucursalesBodyResponseBean.listaSucursales.sucursalBean));
                                ArrayList arrayList = new ArrayList();
                                for (int i = 0; i < getSucursalesBodyResponseBean.listaSucursales.sucursalBean.size(); i++) {
                                    MyMarker myMarker = new MyMarker((SucursalBean) getSucursalesBodyResponseBean.listaSucursales.sucursalBean.get(i));
                                    String a2 = SeleccionSucursalTurnoFragment.this.getDistanceFromMyPosition(new LatLng(myMarker.latitude.doubleValue(), myMarker.longitude.doubleValue()));
                                    if (a2 != null) {
                                        myMarker.dist = a2;
                                    }
                                    arrayList.add(myMarker);
                                    SeleccionSucursalTurnoFragment.this.mMyMarkersArray.add(myMarker);
                                }
                                SeleccionSucursalTurnoFragment.this.addMarkersInMap(arrayList);
                                try {
                                    if (SeleccionSucursalTurnoFragment.paginaActual <= 1) {
                                        SeleccionSucursalTurnoFragment.this.actionsOpenPanel();
                                    }
                                    SeleccionSucursalTurnoFragment.this.zoomMapForViewAllMarkers(SeleccionSucursalTurnoFragment.this.getAllMarkers());
                                } catch (Exception e) {
                                    Log.e("@dev", "Error en la localización", e);
                                }
                                SeleccionSucursalTurnoFragment.this.dismissProgress();
                            }
                        }
                        SeleccionSucursalTurnoFragment.this.noBusqueda.setVisibility(0);
                        SeleccionSucursalTurnoFragment.this.analyticsManager.trackScreen(SeleccionSucursalTurnoFragment.this.getString(R.string.analytics_screen_name_sucursales_busqueda_no_resultados));
                        SeleccionSucursalTurnoFragment.this.dismissProgress();
                    } else {
                        SeleccionSucursalTurnoFragment.this.actionClosePanel();
                        SeleccionSucursalTurnoFragment.this.dismissProgress();
                        if (SeleccionSucursalTurnoFragment.this.getErrorListener() != null) {
                            SeleccionSucursalTurnoFragment.this.getErrorListener().onWebServiceErrorEvent(getSucursalesEvent2, SeleccionSucursalTurnoFragment.this.getTAG());
                        }
                    }
                } catch (Exception e2) {
                    Log.e("@dev", "Error en la petición de sucursales", e2);
                    SeleccionSucursalTurnoFragment.this.dismissProgress();
                } catch (Throwable th) {
                    SeleccionSucursalTurnoFragment.this.pendingRequest = false;
                    throw th;
                }
                SeleccionSucursalTurnoFragment.this.pendingRequest = false;
            }

            /* access modifiers changed from: protected */
            public void onRes5Error(WebServiceEvent webServiceEvent) {
                super.onRes5Error(webServiceEvent, SeleccionSucursalTurnoFragment.this.ah);
            }
        };
        r1.handleWSResponse(getSucursalesEvent);
    }

    /* access modifiers changed from: protected */
    public void resetMarkersMapAndSucursal() {
        if (this.ad != null) {
            this.ad.cleanItems();
        }
        if (this.mMyMarkersArray != null) {
            this.mMyMarkersArray.clear();
        }
        if (this.mMap != null) {
            this.mMap.clear();
        }
        this.myMarkerPosition = null;
        this.mMarkersHashMap = new HashMap();
        paginaActual = 1;
    }

    /* access modifiers changed from: protected */
    public void onInitRequestPermissionResponse() {
        if (this.mMap != null) {
            this.mMap.getUiSettings().setMyLocationButtonEnabled(false);
            this.mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(R.layout.infopopup_turno));
            this.mMap.getUiSettings().setMapToolbarEnabled(false);
            this.mMap.setOnMapClickListener(new OnMapClickListener() {
                public void onMapClick(LatLng latLng) {
                    if (SeleccionSucursalTurnoFragment.this.mSlidingUpPanelLayout.isAnchored()) {
                        SeleccionSucursalTurnoFragment.this.mSlidingUpPanelLayout.closePanel();
                    }
                }
            });
            this.mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
                public void onInfoWindowClick(Marker marker) {
                    SeleccionSucursalTurnoFragment.this.analyticsManager.trackEvent(SeleccionSucursalTurnoFragment.this.getString(R.string.analytics_category_sucursales), SeleccionSucursalTurnoFragment.this.getString(R.string.analytics_action_sucursales_mapa), SeleccionSucursalTurnoFragment.this.getString(R.string.analytics_label_sucursales_clic_poi));
                    if (SeleccionSucursalTurnoFragment.this.mMarkersHashMap.get(marker) != null && !((MyMarker) SeleccionSucursalTurnoFragment.this.mMarkersHashMap.get(marker)).tiempoEspera.equals(NesrConstants.SUCURSAL_CERRADA)) {
                        SeleccionSucursalTurnoFragment.this.ag = SeleccionSucursalTurnoFragment.this.a(marker);
                        SeleccionSucursalTurnoFragment.this.showProgress(VGetCircuitoTurno.nameService);
                        SeleccionSucursalTurnoFragment.this.dataManager.getCircuitoTurno(SeleccionSucursalTurnoFragment.this.ag.numero);
                    }
                }
            });
            initialize();
            updateMapWithMoreSucursal();
            return;
        }
        Toast.makeText(this.af.getApplicationContext(), getString(R.string.IDXX_ERROR_LOAD_MAP), 0).show();
    }

    /* access modifiers changed from: protected */
    public void initialize() {
        try {
            this.mSlidingUpPanelLayout.setEnableDragViewTouchEvents(true);
            this.mSlidingUpPanelLayout.setPanelSlideListener(this);
            this.mSlidingUpPanelLayout.setVisibility(4);
            this.mSlidingUpPanelLayout.isTouchEnabled();
            this.editText.setOnClearListener(new OnClearListener() {
                public void onClear() {
                    SeleccionSucursalTurnoFragment.this.editText.setText("");
                    LatLng B = SeleccionSucursalTurnoFragment.this.getUserPosition();
                    SeleccionSucursalTurnoFragment.this.updateMyLocationMarker(B);
                    if (SeleccionSucursalTurnoFragment.this.ad != null) {
                        SeleccionSucursalTurnoFragment.this.ad.cleanItems();
                        SeleccionSucursalTurnoFragment.this.ad.notifyDataSetChanged();
                    }
                    SeleccionSucursalTurnoFragment.this.requestSucursales("", String.valueOf(SeleccionSucursalTurnoFragment.paginaActual), B, new ValoresBean());
                }
            });
            this.editText.setOnEditorActionListener(new OnEditorActionListener() {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 3) {
                        return false;
                    }
                    SeleccionSucursalTurnoFragment.this.performSearch();
                    return false;
                }
            });
            resetMarkersMapAndSucursal();
            initializeListSucursales();
        } catch (Exception e2) {
            dismissProgress();
            Log.e("@dev", "Error al inflar la vista de sucursales.", e2);
        }
    }

    /* access modifiers changed from: private */
    public SucursalBean a(Marker marker) {
        for (SucursalBean sucursalBean : this.b.listaSucursales.sucursalBean) {
            if (sucursalBean.latitud.equalsIgnoreCase(String.valueOf(marker.getPosition().latitude)) && sucursalBean.longitud.equalsIgnoreCase(String.valueOf(marker.getPosition().longitude))) {
                return sucursalBean;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean hasSucursales(GetSucursalesBodyResponseBean getSucursalesBodyResponseBean) {
        if (getSucursalesBodyResponseBean != null) {
            try {
                if (UtilString.isNumber(getSucursalesBodyResponseBean.paginas) && Integer.parseInt(getSucursalesBodyResponseBean.paginas) > 0 && getSucursalesBodyResponseBean.listaSucursales != null && getSucursalesBodyResponseBean.listaSucursales.sucursalBean != null && getSucursalesBodyResponseBean.listaSucursales.sucursalBean.size() > 0) {
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    /* access modifiers changed from: protected */
    public void updateMapWithMoreSucursal() {
        this.mListView.appendItems(new ArrayList(this.b.listaSucursales.sucursalBean));
        this.executing = false;
        this.paginaMaxima = Integer.parseInt(this.b.paginas);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.b.listaSucursales.sucursalBean.size(); i2++) {
            MyMarker myMarker = new MyMarker((SucursalBean) this.b.listaSucursales.sucursalBean.get(i2));
            String distanceFromMyPosition = getDistanceFromMyPosition(new LatLng(myMarker.latitude.doubleValue(), myMarker.longitude.doubleValue()));
            if (distanceFromMyPosition != null) {
                myMarker.dist = distanceFromMyPosition;
            }
            arrayList.add(myMarker);
            this.mMyMarkersArray.add(myMarker);
        }
        addMarkersInMap(arrayList);
        try {
            if (paginaActual <= 1) {
                actionsOpenPanel();
            }
            zoomMapForViewAllMarkers(getAllMarkers());
        } catch (Exception e2) {
            Log.e("@dev", "Error en la localización", e2);
        }
        dismissProgress();
    }

    /* access modifiers changed from: protected */
    public void createAdapter() {
        this.ad = new MapInfiniteSucursalesTurnoAdapter(this.af) {
            public String getDistanceMarkers(LatLng latLng) {
                return SeleccionSucursalTurnoFragment.this.getDistanceFromMyPosition(latLng);
            }
        };
        this.mListView.setAdapter(this.ad);
    }

    public void onItemClicked(AdapterView<?> adapterView, View view, int i2, long j) {
        this.ag = (SucursalBean) this.ad.getItem(i2);
        showProgress(VGetCircuitoTurno.nameService);
        this.dataManager.getCircuitoTurno(this.ag.numero);
    }
}
