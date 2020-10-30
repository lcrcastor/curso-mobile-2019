package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderCallBack;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.app.ui.activities.MiUbicacionActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SucursalDetalleActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.ZonaActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.MapInfiniteSucursalesArrayAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.NesrConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.SlidingUpPanelLayout;
import ar.com.santander.rio.mbanking.components.SlidingUpPanelLayout.PanelSlideListener;
import ar.com.santander.rio.mbanking.components.infinitescroll.IInfiniteScrollListener;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollListView;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollOnScrollListener;
import ar.com.santander.rio.mbanking.components.mapInfo.MyMarker;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetSucursalesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ValoresBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VSucursales;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import ar.com.santander.rio.mbanking.view.ClearableEditText.OnClearListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.inject.Inject;

public class SucursalesFragment extends BaseFragment implements PanelSlideListener, IInfiniteScrollListener, ConnectionCallbacks, OnConnectionFailedListener, LocationListener, OnMapReadyCallback {
    public static final String ACCESS_FINE_LOCATION_DIALOG_TAG = "ACCESS_FINE_LOCATION_DIALOG";
    protected static final int FRAGMENT_LOCALIZACION = 2;
    protected static final String IDCV = "9";
    public static final int INTERVAL_CONSTANT = 10000;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION_2 = 2;
    protected static final int REFRESH_TIME = 5000;
    protected static Location location = null;
    /* access modifiers changed from: protected */
    public static int paginaActual = 1;
    @Inject
    SessionManager a;
    protected MapInfiniteSucursalesArrayAdapter adapter;
    /* access modifiers changed from: protected */
    @Inject
    public AnalyticsManager analyticsManager;
    /* access modifiers changed from: protected */
    @Inject
    public IDataManager dataManager;
    /* access modifiers changed from: protected */
    @InjectView(2131364643)
    public ClearableEditText editText;
    /* access modifiers changed from: protected */
    public boolean executing = false;
    protected GoogleApiClient googleApiClient;
    protected ImageLoader imageLoader;
    @InjectView(2131365118)
    protected RelativeLayout llButtonMap;
    protected LocalizacionBean localizacionBean = new LocalizacionBean(String.valueOf(-34.606737d), String.valueOf(-58.373381d));
    protected LocationManager locationManager;
    protected Activity mActivity;
    protected Location mLastLocation;
    /* access modifiers changed from: protected */
    @InjectView(2131365104)
    public InfiniteScrollListView mListView;
    protected GoogleMap mMap;
    /* access modifiers changed from: protected */
    public HashMap<Marker, MyMarker> mMarkersHashMap;
    /* access modifiers changed from: protected */
    public ArrayList<MyMarker> mMyMarkersArray = new ArrayList<>();
    /* access modifiers changed from: protected */
    @InjectView(2131365739)
    public SlidingUpPanelLayout mSlidingUpPanelLayout;
    protected SupportMapFragment mapFragment;
    protected Marker myMarkerPosition;
    /* access modifiers changed from: protected */
    @InjectView(2131365259)
    public LinearLayout noBusqueda;
    /* access modifiers changed from: protected */
    public int paginaMaxima;
    /* access modifiers changed from: protected */
    public boolean pendingRequest;
    protected List<String> valoresSucursal = new ArrayList();

    public class MarkerInfoWindowAdapter implements InfoWindowAdapter {
        private final View b;

        public View getInfoWindow(Marker marker) {
            return null;
        }

        public MarkerInfoWindowAdapter(int i) {
            this.b = SucursalesFragment.this.mActivity.getLayoutInflater().inflate(i, null);
        }

        public View getInfoContents(final Marker marker) {
            MyMarker myMarker = (MyMarker) SucursalesFragment.this.mMarkersHashMap.get(marker);
            if (myMarker == null || !myMarker.isClickable) {
                return null;
            }
            ((TextView) this.b.findViewById(R.id.txtTitle)).setText(Html.fromHtml(myMarker.title));
            ((TextView) this.b.findViewById(R.id.txtDirec)).setText(Html.fromHtml(myMarker.direc));
            RelativeLayout relativeLayout = (RelativeLayout) this.b.findViewById(R.id.rrTiempoDeEsperaYLinkTicket);
            RelativeLayout relativeLayout2 = (RelativeLayout) this.b.findViewById(R.id.rrVer);
            TextView textView = (TextView) this.b.findViewById(R.id.tiempoDeEsperaInfoPopUp);
            TextView textView2 = (TextView) this.b.findViewById(R.id.SolicitarTicketInfoPopUp);
            if (myMarker.tiempoEspera != null) {
                int i = 0;
                relativeLayout.setVisibility(0);
                if (myMarker.tiempoEspera.equals(NesrConstants.SUCURSAL_CERRADA)) {
                    i = 4;
                }
                textView2.setVisibility(i);
                relativeLayout2.setVisibility(8);
                textView.setText(myMarker.tiempoEspera);
            }
            TextView textView3 = (TextView) this.b.findViewById(R.id.txtDist);
            if (myMarker.dist != null) {
                textView3.setText(myMarker.dist);
            }
            SucursalesFragment.this.imageLoader.loadImage(myMarker.icon, (ImageView) this.b.findViewById(R.id.imgMap), new ImageLoaderCallBack() {
                public void onError() {
                }

                public void onSuccess() {
                    if (marker != null && marker.isInfoWindowShown()) {
                        marker.hideInfoWindow();
                        marker.showInfoWindow();
                        SucursalesFragment.this.analyticsManager.trackScreen(SucursalesFragment.this.getString(R.string.analytics_screen_name_sucursales_globa));
                    }
                }
            });
            return this.b;
        }
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void onScrollCalled(int i, int i2, int i3) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mMarkersHashMap = new HashMap<>();
        this.imageLoader = ImageLoaderFactory.getImageLoader(this.mActivity.getApplicationContext());
        initializeGoogleApiClient();
        this.valoresSucursal = new ArrayList();
        this.valoresSucursal.add(IDCV);
        this.locationManager = (LocationManager) this.mActivity.getSystemService("location");
        createLocationRequest();
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_map_sucursales, viewGroup, false);
        try {
            this.analyticsManager.trackScreen(getString(R.string.analytics_screen_name_sucursales_home));
            ButterKnife.inject((Object) this, inflate);
            showProgress(VSucursales.nameService);
            initializeMap();
        } catch (Exception e) {
            dismissProgress();
            Log.e("@dev", "Error al inflar la vista de cajeros.", e);
        }
        return inflate;
    }

    public void initialize() {
        try {
            this.mSlidingUpPanelLayout.setEnableDragViewTouchEvents(true);
            this.mSlidingUpPanelLayout.setPanelSlideListener(this);
            this.mSlidingUpPanelLayout.setVisibility(4);
            this.editText.setOnClearListener(new OnClearListener() {
                public void onClear() {
                    SucursalesFragment.this.editText.setText("");
                }
            });
            this.editText.setOnEditorActionListener(new OnEditorActionListener() {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 3) {
                        return false;
                    }
                    SucursalesFragment.this.performSearch();
                    return false;
                }
            });
            resetMarkersMapAndSucursal();
            initializeListSucursales();
        } catch (Exception e) {
            dismissProgress();
            Log.e("@dev", "Error al inflar la vista de sucursales.", e);
        }
    }

    public void initializeMap() {
        if (this.mapFragment == null) {
            this.mapFragment = SupportMapFragment.newInstance();
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().replace(R.id.sucursal_map_fragmet, this.mapFragment).commit();
                this.mapFragment.getMapAsync(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initializeGoogleApiClient() {
        if (this.googleApiClient == null) {
            this.googleApiClient = new Builder(this.mActivity).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
    }

    /* access modifiers changed from: protected */
    public void createAndSaveMarkerMyLocation(LatLng latLng) {
        if (latLng != null && this.mMap != null) {
            this.myMarkerPosition = this.mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)).anchor(0.5f, 0.5f));
        }
    }

    /* access modifiers changed from: protected */
    public void updateMyLocationMarker(LatLng latLng) {
        if (this.myMarkerPosition != null) {
            this.myMarkerPosition.remove();
            this.myMarkerPosition = null;
        }
        createAndSaveMarkerMyLocation(latLng);
    }

    public void requestSucursales(String str, String str2, LatLng latLng, ValoresBean valoresBean) {
        this.dataManager.getSucursales(str, str2, latLng, valoresBean);
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131366285})
    public void A() {
        this.analyticsManager.trackEvent(getString(R.string.analytics_category_sucursales), getString(R.string.analytics_action_sucursales_ubicacion), getString(R.string.analytics_label_sucursales_ubicacion));
        Intent intent = new Intent(this.mActivity, MiUbicacionActivity.class);
        intent.putExtra(MiUbicacionActivity.INDEX_PARAM_TYPE_UBICACION, 7);
        startActivityForResult(intent, 2);
    }

    /* access modifiers changed from: protected */
    @OnClick({2131364198})
    public void expand() {
        if (this.mMyMarkersArray.size() > 0) {
            this.analyticsManager.trackEvent(getString(R.string.analytics_category_sucursales), getString(R.string.analytics_action_sucursales_mapa), getString(R.string.analytics_label_sucursales_volver));
            this.mSlidingUpPanelLayout.openPanel();
            this.llButtonMap.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    @OnClick({2131364195})
    public void cargarMas() {
        if (!this.pendingRequest) {
            this.analyticsManager.trackEvent(getString(R.string.analytics_category_sucursales), getString(R.string.analytics_action_sucursales_mapa), getString(R.string.analytics_label_sucursales_cargar));
            if (paginaActual < this.paginaMaxima) {
                showProgress(VSucursales.nameService);
                paginaActual++;
                requestSucursales(this.editText.getText().toString(), String.valueOf(paginaActual), getUserPosition(), new ValoresBean(this.valoresSucursal));
            }
            this.pendingRequest = true;
        }
    }

    /* access modifiers changed from: protected */
    @OnClick({2131364810})
    public void performSearch() {
        this.analyticsManager.trackEvent(getString(R.string.analytics_category_sucursales), getString(R.string.analytics_action_sucursales_busqueda), getString(R.string.analytics_label_sucursales_busqueda));
        actionInitLoadData();
        InputMethodManager inputMethodManager = (InputMethodManager) this.mActivity.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 2);
        }
        resetMarkersMapAndSucursal();
        LatLng userPosition = getUserPosition();
        updateMyLocationMarker(userPosition);
        requestSucursales(this.editText.getText().toString(), String.valueOf(paginaActual), userPosition, new ValoresBean());
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
    }

    /* access modifiers changed from: protected */
    public void addMarkersInMap(ArrayList<MyMarker> arrayList) {
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                MyMarker myMarker = (MyMarker) it.next();
                this.mMarkersHashMap.put(this.mMap.addMarker(new MarkerOptions().position(new LatLng(myMarker.latitude.doubleValue(), myMarker.longitude.doubleValue()))), myMarker);
            }
        }
    }

    @Subscribe
    public void onGetSucursales(GetSucursalesEvent getSucursalesEvent) {
        try {
            GetSucursalesResponseBean getSucursalesResponseBean = (GetSucursalesResponseBean) getSucursalesEvent.getBeanResponse();
            if (getSucursalesEvent.getResult() == TypeResult.OK) {
                GetSucursalesBodyResponseBean getSucursalesBodyResponseBean = getSucursalesResponseBean.getGetSucursalesBodyResponseBean();
                this.executing = false;
                this.paginaMaxima = Integer.parseInt(getSucursalesBodyResponseBean.paginas);
                if (hasSucursales(getSucursalesBodyResponseBean)) {
                    if (this.paginaMaxima != 0) {
                        if (!TextUtils.isEmpty(this.editText.getText().toString())) {
                            this.analyticsManager.trackScreen(getString(R.string.analytics_screen_name_sucursales_home_con_busqueda));
                        }
                        this.noBusqueda.setVisibility(8);
                        this.mListView.appendItems(new ArrayList(getSucursalesBodyResponseBean.sucursalesBean.sucursalBean));
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < getSucursalesBodyResponseBean.sucursalesBean.sucursalBean.size(); i++) {
                            MyMarker myMarker = new MyMarker((SucursalBean) getSucursalesBodyResponseBean.sucursalesBean.sucursalBean.get(i));
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
                        } catch (Exception e) {
                            Log.e("@dev", "Error en la localización", e);
                        }
                        dismissProgress();
                    }
                }
                this.noBusqueda.setVisibility(0);
                this.analyticsManager.trackScreen(getString(R.string.analytics_screen_name_sucursales_busqueda_no_resultados));
                dismissProgress();
            } else {
                actionClosePanel();
                dismissProgress();
                if (getErrorListener() != null) {
                    getErrorListener().onWebServiceErrorEvent(getSucursalesEvent, getTAG());
                }
            }
        } catch (Exception e2) {
            Log.e("@dev", "Error en la petición de sucursales", e2);
            dismissProgress();
        } catch (Throwable th) {
            this.pendingRequest = false;
            throw th;
        }
        this.pendingRequest = false;
    }

    /* access modifiers changed from: protected */
    public List<Marker> getAllMarkers() {
        ArrayList arrayList = new ArrayList();
        for (Entry key : this.mMarkersHashMap.entrySet()) {
            arrayList.add((Marker) key.getKey());
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public String getDistanceFromMyPosition(LatLng latLng) {
        try {
            LatLng userPosition = getUserPosition();
            if (userPosition != null) {
                Location location2 = new Location("");
                location2.setLatitude(userPosition.latitude);
                location2.setLongitude(userPosition.longitude);
                Location location3 = new Location("");
                location3.setLatitude(latLng.latitude);
                location3.setLongitude(latLng.longitude);
                return Utils.formatDist(location2.distanceTo(location3));
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al calcular la distancia", e);
        }
        return null;
    }

    public void endIsNear() {
        if (!this.executing && paginaActual < this.paginaMaxima) {
            this.analyticsManager.trackEvent(getString(R.string.analytics_category_sucursales), getString(R.string.analytics_action_sucursales_lista), getString(R.string.analytics_label_sucursales_scoll));
            this.executing = true;
            LatLng userPosition = getUserPosition();
            if (userPosition != null) {
                this.localizacionBean.updateLocation(userPosition);
            }
            String obj = this.editText.getText().toString();
            int i = paginaActual + 1;
            paginaActual = i;
            requestSucursales(obj, String.valueOf(i), userPosition, new ValoresBean(this.valoresSucursal));
        } else if (!this.executing && paginaActual == this.paginaMaxima) {
            this.mListView.appendItems(new ArrayList());
        }
    }

    public void onBackPressed() {
        try {
            this.mActivity.finish();
        } catch (Exception e) {
            Log.e("@dev", "Error al destruir la actividad", e);
        }
    }

    public void onPanelSlide(View view, float f) {
        if (((double) f) >= 1.0d) {
            if (!this.mListView.isScrollEnabled()) {
                this.mListView.setScrollEnabled(true);
            }
        } else if (this.mListView.isScrollEnabled()) {
            this.mListView.setScrollEnabled(false);
        }
    }

    public void onPanelCollapsed(View view) {
        this.llButtonMap.setVisibility(0);
        this.mListView.setScrollEnabled(false);
    }

    public void onPanelExpanded(View view) {
        this.llButtonMap.setVisibility(8);
        this.mListView.setScrollEnabled(true);
    }

    public void onPanelAnchored(View view) {
        this.llButtonMap.setVisibility(8);
        this.mListView.setScrollEnabled(false);
    }

    public void onPanelHidden(View view) {
        this.llButtonMap.setVisibility(0);
        this.mListView.setScrollEnabled(false);
    }

    public void resetMarkersMapAndSucursal() {
        if (this.adapter != null) {
            this.adapter.cleanItems();
        }
        if (this.mMyMarkersArray != null) {
            this.mMyMarkersArray.clear();
        }
        if (this.mMap != null) {
            this.mMap.clear();
        }
        this.myMarkerPosition = null;
        this.mMarkersHashMap = new HashMap<>();
        paginaActual = 1;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 2 && i2 == -1) {
            try {
                double doubleExtra = intent.getDoubleExtra(MiUbicacionActivity.MIUBICACION_LAT, 0.0d);
                double doubleExtra2 = intent.getDoubleExtra(MiUbicacionActivity.MIUBICACION_LON, 0.0d);
                actionInitLoadData();
                resetMarkersMapAndSucursal();
                this.a.setLocation(new LatLng(doubleExtra, doubleExtra2));
                ArrayList stringArrayListExtra = intent.getStringArrayListExtra(ZonaActivity.ZONA_ARRAY);
                if (stringArrayListExtra != null) {
                    this.valoresSucursal.clear();
                    this.valoresSucursal.addAll(stringArrayListExtra);
                }
                updateMapWithMoreSucursal();
            } catch (Exception e) {
                dismissProgress();
                updateMyLocationMarker(getUserPosition());
                Log.e("@dev", "Error al recoger los valores de mi ubicación", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void actionInitLoadData() {
        showProgress(VSucursales.nameService);
        this.mSlidingUpPanelLayout.setVisibility(4);
    }

    /* access modifiers changed from: protected */
    public void actionsOpenPanel() {
        this.mSlidingUpPanelLayout.openPanel();
        this.mSlidingUpPanelLayout.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void actionClosePanel() {
        this.mSlidingUpPanelLayout.closePanel();
        this.llButtonMap.setVisibility(0);
        this.mSlidingUpPanelLayout.setVisibility(0);
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            onInitRequestPermissionResponse();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this.mActivity, "android.permission.ACCESS_FINE_LOCATION")) {
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
        } else {
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
        }
    }

    @SuppressLint({"MissingPermission"})
    public void onInitRequestPermissionResponse() {
        if (this.mMap != null) {
            this.mMap.getUiSettings().setMyLocationButtonEnabled(false);
            this.mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(R.layout.infopopup));
            this.mMap.getUiSettings().setMapToolbarEnabled(false);
            this.mMap.setOnMapClickListener(new OnMapClickListener() {
                public void onMapClick(LatLng latLng) {
                    if (SucursalesFragment.this.mSlidingUpPanelLayout.isAnchored()) {
                        SucursalesFragment.this.mSlidingUpPanelLayout.closePanel();
                    }
                }
            });
            this.mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
                public void onInfoWindowClick(Marker marker) {
                    SucursalesFragment.this.analyticsManager.trackEvent(SucursalesFragment.this.getString(R.string.analytics_category_sucursales), SucursalesFragment.this.getString(R.string.analytics_action_sucursales_mapa), SucursalesFragment.this.getString(R.string.analytics_label_sucursales_clic_poi));
                    if (((MyMarker) SucursalesFragment.this.mMarkersHashMap.get(marker)).tiempoEspera != null) {
                        MyMarker myMarker = (MyMarker) SucursalesFragment.this.mMarkersHashMap.get(marker);
                        Intent intent = new Intent(SucursalesFragment.this.mActivity, SucursalDetalleActivity.class);
                        intent.putExtra("marker", myMarker);
                        SucursalesFragment.this.mActivity.startActivity(intent);
                    } else if (SucursalesFragment.this.mMarkersHashMap.get(marker) != null) {
                        MyMarker myMarker2 = (MyMarker) SucursalesFragment.this.mMarkersHashMap.get(marker);
                        Intent intent2 = new Intent(SucursalesFragment.this.mActivity, SucursalDetalleActivity.class);
                        intent2.putExtra("marker", myMarker2);
                        SucursalesFragment.this.mActivity.startActivity(intent2);
                    }
                }
            });
            initialize();
            updateMapWithMoreSucursal();
            return;
        }
        Toast.makeText(this.mActivity.getApplicationContext(), getString(R.string.IDXX_ERROR_LOAD_MAP), 0).show();
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        switch (i) {
            case 1:
                if (iArr.length <= 0 || iArr[0] != 0) {
                    onInitRequestPermissionResponse();
                    return;
                } else {
                    onInitRequestPermissionResponse();
                    return;
                }
            case 2:
                if (iArr.length <= 0 || iArr[0] != 0) {
                    onConectedRequestPermissionResponseOk();
                    return;
                } else {
                    onConectedRequestPermissionResponseOk();
                    return;
                }
            default:
                return;
        }
    }

    public void showRequestPermissionExplation(final int i) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.ACCESS_FINE_LOCATION_USER_EXPLAIN), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                SucursalesFragment.this.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, i);
            }
        });
        newInstance.show(getFragmentManager(), ACCESS_FINE_LOCATION_DIALOG_TAG);
    }

    public void onStart() {
        super.onStart();
        if (this.googleApiClient != null) {
            this.googleApiClient.connect();
        }
    }

    public void onStop() {
        if (this.googleApiClient != null) {
            this.googleApiClient.disconnect();
        }
        super.onStop();
        this.a.setFiltrosSucursales(null);
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mapFragment.onLowMemory();
    }

    public void createAdapter() {
        this.adapter = new MapInfiniteSucursalesArrayAdapter(this.mActivity) {
            public String getDistanceMarkers(LatLng latLng) {
                return SucursalesFragment.this.getDistanceFromMyPosition(latLng);
            }
        };
        this.mListView.setAdapter(this.adapter);
    }

    public void onConnected(Bundle bundle) {
        if (this.googleApiClient != null && isLocalizationActive()) {
            onConectedRequestPermissionResponseOk();
        }
    }

    /* access modifiers changed from: protected */
    public void onConectedRequestPermissionResponseOk() {
        if (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.mMap.setMyLocationEnabled(true);
            this.mLastLocation = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
            return;
        }
        this.mMap.setMyLocationEnabled(false);
    }

    public void onConnectionSuspended(int i) {
        this.googleApiClient.connect();
    }

    /* access modifiers changed from: protected */
    public void zoomMapForViewAllMarkers(List<Marker> list) {
        if (!this.mSlidingUpPanelLayout.isExpanded()) {
            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(getUserPosition());
            for (Marker position : list) {
                builder.include(position.getPosition());
            }
            this.mMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
                public void onMapLoaded() {
                    SucursalesFragment.this.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public LatLng getUserPosition() {
        LatLng positionFromSession = getPositionFromSession();
        if (positionFromSession != null) {
            return positionFromSession;
        }
        LatLng positionFromService = getPositionFromService();
        if (positionFromService == null) {
            return getPositionFromWsBean();
        }
        this.a.setLocation(positionFromService);
        updateMyLocationMarker(positionFromService);
        return positionFromService;
    }

    /* access modifiers changed from: protected */
    public LatLng getPositionFromSession() {
        try {
            return SessionManager.getLocation();
        } catch (Exception e) {
            Log.e("@dev", "Error al obtener la localización de la sessión", e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public LatLng getPositionFromService() {
        try {
            if (this.mLastLocation != null) {
                return new LatLng(this.mLastLocation.getLatitude(), this.mLastLocation.getLongitude());
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al obtener la posición desde el servicio", e);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public LatLng getPositionFromWsBean() {
        try {
            if (this.localizacionBean != null) {
                return new LatLng(Double.valueOf(this.localizacionBean.getLatitud()).doubleValue(), Double.valueOf(this.localizacionBean.getLongitud()).doubleValue());
            }
        } catch (Exception e) {
            Log.e("@dev", "Erro al obtener la posición desde el bean de ws", e);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(100);
    }

    /* access modifiers changed from: protected */
    public boolean isLocalizationActive() {
        return this.locationManager.isProviderEnabled("gps") || this.locationManager.isProviderEnabled("network");
    }

    /* access modifiers changed from: protected */
    public void startLocationUpdates() {
        try {
            if (this.googleApiClient != null && isLocalizationActive() && ContextCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                onConectedRequestPermissionResponseOk();
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al actualizar la localización", e);
        }
    }

    /* access modifiers changed from: protected */
    public void stopLocationUpdates() {
        try {
            if (this.googleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, (LocationListener) this);
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al actualizar la localización", e);
        }
    }

    public void onLocationChanged(Location location2) {
        this.mLastLocation = location2;
        updateMyLocationMarker(new LatLng(this.mLastLocation.getLatitude(), this.mLastLocation.getLongitude()));
    }

    public void onResume() {
        super.onResume();
        if (this.googleApiClient.isConnected() && isLocalizationActive()) {
            startLocationUpdates();
        }
    }

    public boolean hasSucursales(GetSucursalesBodyResponseBean getSucursalesBodyResponseBean) {
        if (getSucursalesBodyResponseBean != null) {
            try {
                if (UtilString.isNumber(getSucursalesBodyResponseBean.paginas) && Integer.parseInt(getSucursalesBodyResponseBean.paginas) > 0 && getSucursalesBodyResponseBean.sucursalesBean != null && getSucursalesBodyResponseBean.sucursalesBean.sucursalBean != null && getSucursalesBodyResponseBean.sucursalesBean.sucursalBean.size() > 0) {
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    public void initializeListSucursales() {
        this.mListView.setListener(new InfiniteScrollOnScrollListener(this));
        this.mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (SucursalesFragment.this.mMyMarkersArray.size() > 0 && SucursalesFragment.this.mMyMarkersArray.size() > i) {
                    MyMarker myMarker = (MyMarker) SucursalesFragment.this.mMyMarkersArray.get(i);
                    Intent intent = new Intent(SucursalesFragment.this.mActivity, SucursalDetalleActivity.class);
                    intent.putExtra("marker", myMarker);
                    SucursalesFragment.this.mActivity.startActivity(intent);
                }
            }
        });
        createAdapter();
    }

    public void updateMapWithMoreSucursal() {
        LatLng userPosition = getUserPosition();
        if (userPosition != null) {
            this.localizacionBean.updateLocation(userPosition);
        }
        updateMyLocationMarker(userPosition);
        requestSucursales(this.editText.getText().toString(), String.valueOf(paginaActual), userPosition, new ValoresBean(this.valoresSucursal));
    }
}
