package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.brodcast.LocationBrodcast;
import ar.com.santander.rio.mbanking.app.brodcast.LocationBrodcast.LocationBrodcastListener;
import ar.com.santander.rio.mbanking.app.storage.MyPlacesSharedPref;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.Model.Favorito;
import ar.com.santander.rio.mbanking.app.ui.adapters.PlacesAutoCompleteAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.mapInfo.FinderPlaces;
import ar.com.santander.rio.mbanking.components.mapInfo.FinderPlacesFactory;
import ar.com.santander.rio.mbanking.components.mapInfo.FinderPlacesFactory.TypeAutoComplete;
import ar.com.santander.rio.mbanking.components.popup.ActionItem;
import ar.com.santander.rio.mbanking.components.popup.ActionItem.CustomTypeFace;
import ar.com.santander.rio.mbanking.components.popup.QuickAction;
import ar.com.santander.rio.mbanking.components.popup.QuickAction.OnActionItemClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.PlaceMap;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalizacionBean;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager.OnLocationChangeListener;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.view.ClearableAutoCompleteTextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Bus;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import org.json.JSONObject;

public class MiUbicacionActivity extends BaseActivity implements OnItemClickListener, LocationBrodcastListener, OnLocationChangeListener, ConnectionCallbacks, OnConnectionFailedListener, LocationListener, OnCameraChangeListener, OnMapLongClickListener, OnMarkerClickListener, OnMapReadyCallback {
    public static final String ACCESS_FINE_LOCATION_DIALOG_TAG = "ACCESS_FINE_LOCATION_DIALOG_TAG";
    public static final String INDEX_PARAM_TYPE_UBICACION = "index_type_ubicacion";
    public static final String MIUBICACION_LAT = "mi_ubicacion_lat";
    public static final String MIUBICACION_LON = "mi_ubicacion_lon";
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION_2 = 1;
    private FinderPlaces A;
    /* access modifiers changed from: private */
    public GoogleMap B;
    private Location C;
    private LocationBrodcast D;
    private LocationRequest E;
    private int F = 5000;
    private PlacesAutoCompleteAdapter G = null;
    private MyPlacesSharedPref H;
    private ArrayList<PlaceMap> I;
    /* access modifiers changed from: private */
    public LatLng J;
    private GoogleApiClient K;
    private SupportMapFragment L;
    private Marker M;
    /* access modifiers changed from: private */
    public Marker N;
    private LocationManager O;
    private Bundle P;
    private ActionItem Q;
    @InjectView(2131364643)
    ClearableAutoCompleteTextView eTxtSearch;
    @InjectView(2131364807)
    ImageView imageMarker;
    @InjectView(2131364804)
    ImageView imgGPS;
    @InjectView(2131364895)
    ViewGroup layoutInfo;
    QuickAction p;
    @Inject
    SessionManager q;
    @Inject
    SettingsManager r;
    @Inject
    AnalyticsManager s;
    @Inject
    Bus t;
    private final int u = 3;
    private final int v = 2;
    private LocalizacionBean w = new LocalizacionBean(String.valueOf(-34.606737d), String.valueOf(-58.373381d));
    private float x = 14.0f;
    private Float y = Float.valueOf(0.5f);
    private Float z = Float.valueOf(0.5f);

    /* access modifiers changed from: private */
    public void b(String str) {
    }

    public void onLocationListener(Location location) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        setContentView((int) R.layout.activity_map_ubicacion);
        ButterKnife.inject((Activity) this);
        this.D = new LocationBrodcast();
        this.D.setLocationBrodcastListener(this);
        this.P = getIntent().getExtras();
        if (this.P == null) {
            this.P = new Bundle();
        }
        createLocationRequest();
        d();
        this.O = (LocationManager) getSystemService("location");
        this.A = i();
        this.H = new MyPlacesSharedPref(getApplicationContext(), 5);
        initializeGoogleApiClient();
        j();
        e();
        c(this.q.isShowLeyenda());
        m();
        this.s.trackScreen(getString(R.string.analytics_screen_name_ubicacion_home));
    }

    private void d() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayUseLogoEnabled(false);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back, null);
        inflate.findViewById(R.id.toggle).setVisibility(4);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.ok);
        imageView.setVisibility(0);
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MiUbicacionActivity.this.B != null) {
                    MiUbicacionActivity.this.J = MiUbicacionActivity.this.B.getCameraPosition().target;
                }
                MiUbicacionActivity.this.q.setLocation(MiUbicacionActivity.this.J);
                MiUbicacionActivity.this.e(MiUbicacionActivity.this.J);
            }
        });
        supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1));
    }

    private void e() {
        if (this.L == null) {
            this.L = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.mi_ubicacion_map_fragmet, this.L).commit();
            this.L.getMapAsync(this);
        }
    }

    /* access modifiers changed from: protected */
    public void initializeGoogleApiClient() {
        if (this.K == null) {
            this.K = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.t.register(this);
        super.onResume();
        try {
            getApplicationContext().registerReceiver(this.D, new IntentFilter("android.location.PROVIDERS_CHANGED"));
            if (this.K.isConnected() && h()) {
                startLocationUpdates();
            }
            if (this.p != null) {
                this.p.dismiss();
            }
        } catch (IllegalArgumentException | RuntimeException unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.t.unregister(this);
        getApplicationContext().unregisterReceiver(this.D);
        stopLocationUpdates();
    }

    public void onStop() {
        if (this.K != null) {
            this.K.disconnect();
        }
        super.onStop();
        if (this.p != null) {
            this.p.dismiss();
        }
    }

    public void onStart() {
        super.onStart();
        if (this.K != null) {
            this.K.connect();
        }
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131366291})
    public void b() {
        if (this.q.getGetClasificadores() == null || this.q.getGetClasificadores().hasNullValue()) {
            this.r.setClasificadoresPrevHashCode("0");
            IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), Html.fromHtml(getResources().getString(R.string.MSG_USER000004_ErrorFiltrosyLogin)).toString(), null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null).show(getSupportFragmentManager(), "Dialog");
            return;
        }
        Intent intent = new Intent(this, ZonaActivity.class);
        intent.putExtra(ZonaActivity.INDEX_ZONA_ID, p());
        startActivityForResult(intent, 1);
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131366280})
    public void c() {
        startActivityForResult(new Intent(this, FavoritosActivity.class), 2);
    }

    @OnClick({2131364643})
    public void onClickSearchBox() {
        this.G.resetPlaces(q());
        this.eTxtSearch.showDropDown();
    }

    @OnClick({2131364804})
    public void onClickButtonGps() {
        if (!h()) {
            g();
            return;
        }
        LatLng t2 = t();
        if (t2 != null) {
            b(this.N, t2);
            a(Double.valueOf(t2.latitude), Double.valueOf(t2.longitude));
            b(true);
            return;
        }
        b(false);
    }

    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
        f();
    }

    private void f() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    private void g() {
        Toast.makeText(this, getString(R.string.IDXX_GPS_OFF), 1).show();
        b(false);
    }

    private boolean h() {
        return this.O.isProviderEnabled("gps") || this.O.isProviderEnabled("network");
    }

    private FinderPlaces i() {
        try {
            String str = this.q.getGetClasificadores().configuraciones.getConfigBeanForKey(Constants.STR_ID_GOOGLE_GEOCODER).value;
            if (str != null && str.contains(Constants.PLACES_API_BASE)) {
                return FinderPlacesFactory.getFinderPlace(TypeAutoComplete.G_PLACES);
            }
        } catch (Exception unused) {
        }
        return FinderPlacesFactory.getFinderPlace(TypeAutoComplete.G_GEO);
    }

    private void a(LatLng latLng) {
        this.M = this.B.addMarker(new MarkerOptions().position(latLng).anchor(this.y.floatValue(), this.z.floatValue()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)).draggable(false));
    }

    private void b(LatLng latLng) {
        this.N = this.B.addMarker(new MarkerOptions().position(latLng).draggable(false));
    }

    private void a(Marker marker, LatLng latLng) {
        if (marker == null) {
            a(latLng);
        }
        if (marker != null) {
            marker.setVisible(false);
            marker.setPosition(latLng);
            marker.setVisible(true);
        }
    }

    /* access modifiers changed from: private */
    public void b(Marker marker, LatLng latLng) {
        if (marker == null) {
            b(latLng);
        }
        if (marker != null) {
            marker.setVisible(false);
            marker.setPosition(latLng);
            marker.setVisible(true);
        }
    }

    private void j() {
        this.G = new PlacesAutoCompleteAdapter(this, R.layout.list_item_autocomplete_places) {
            public ArrayList<PlaceMap> autocomplete(String str) {
                return MiUbicacionActivity.this.a(str);
            }
        };
        this.eTxtSearch.setAdapter(this.G);
        this.eTxtSearch.setOnItemClickListener(this);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0098  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.ArrayList<ar.com.santander.rio.mbanking.services.model.general.PlaceMap> a(java.lang.String r8) {
        /*
            r7 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = 0
            java.net.URL r3 = new java.net.URL     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            ar.com.santander.rio.mbanking.components.mapInfo.FinderPlaces r4 = r7.A     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            ar.com.santander.rio.mbanking.managers.session.SessionManager r5 = r7.q     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.GetClasificadoresBodyResponseBean r5 = r5.getGetClasificadores()     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.ConfigsBean r5 = r5.configuraciones     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r6 = "GoogleGeocoder.Query"
            ar.com.santander.rio.mbanking.services.soap.beans.body.ConfigBean r5 = r5.getConfigBeanForKey(r6)     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r5 = r5.value     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            android.content.Context r6 = r7.getApplicationContext()     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r8 = r4.getStringToSearch(r5, r8, r6)     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            r3.<init>(r8)     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            java.net.URLConnection r8 = r3.openConnection()     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch:{ MalformedURLException -> 0x0096, IOException -> 0x0090, Exception -> 0x008a, all -> 0x0083 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ MalformedURLException -> 0x0081, IOException -> 0x007f, Exception -> 0x007d, all -> 0x007a }
            java.io.InputStream r4 = r8.getInputStream()     // Catch:{ MalformedURLException -> 0x0081, IOException -> 0x007f, Exception -> 0x007d, all -> 0x007a }
            r3.<init>(r4)     // Catch:{ MalformedURLException -> 0x0081, IOException -> 0x007f, Exception -> 0x007d, all -> 0x007a }
            r4 = 1024(0x400, float:1.435E-42)
            char[] r4 = new char[r4]     // Catch:{ MalformedURLException -> 0x0081, IOException -> 0x007f, Exception -> 0x007d, all -> 0x007a }
        L_0x003d:
            int r5 = r3.read(r4)     // Catch:{ MalformedURLException -> 0x0081, IOException -> 0x007f, Exception -> 0x007d, all -> 0x007a }
            r6 = -1
            if (r5 == r6) goto L_0x0049
            r6 = 0
            r1.append(r4, r6, r5)     // Catch:{ MalformedURLException -> 0x0081, IOException -> 0x007f, Exception -> 0x007d, all -> 0x007a }
            goto L_0x003d
        L_0x0049:
            if (r8 == 0) goto L_0x004e
            r8.disconnect()
        L_0x004e:
            ar.com.santander.rio.mbanking.components.mapInfo.FinderPlaces r8 = r7.A     // Catch:{ Exception -> 0x0079 }
            java.util.ArrayList r8 = r8.getListPlaceMap(r1)     // Catch:{ Exception -> 0x0079 }
            if (r8 != 0) goto L_0x0071
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x006f }
            r0.<init>()     // Catch:{ Exception -> 0x006f }
            ar.com.santander.rio.mbanking.services.model.general.PlaceMap r8 = new ar.com.santander.rio.mbanking.services.model.general.PlaceMap     // Catch:{ Exception -> 0x0079 }
            r1 = 2131756412(0x7f10057c, float:1.914373E38)
            java.lang.String r1 = r7.getString(r1)     // Catch:{ Exception -> 0x0079 }
            java.lang.String r3 = "-38.416097"
            java.lang.String r4 = "-63.61667199999999"
            r8.<init>(r1, r3, r4, r2)     // Catch:{ Exception -> 0x0079 }
            r0.add(r8)     // Catch:{ Exception -> 0x0079 }
            goto L_0x0072
        L_0x006f:
            r0 = r8
            goto L_0x0079
        L_0x0071:
            r0 = r8
        L_0x0072:
            java.util.ArrayList r8 = r7.q()     // Catch:{ Exception -> 0x0079 }
            r0.addAll(r8)     // Catch:{ Exception -> 0x0079 }
        L_0x0079:
            return r0
        L_0x007a:
            r0 = move-exception
            r2 = r8
            goto L_0x0084
        L_0x007d:
            r2 = r8
            goto L_0x008a
        L_0x007f:
            r2 = r8
            goto L_0x0090
        L_0x0081:
            r2 = r8
            goto L_0x0096
        L_0x0083:
            r0 = move-exception
        L_0x0084:
            if (r2 == 0) goto L_0x0089
            r2.disconnect()
        L_0x0089:
            throw r0
        L_0x008a:
            if (r2 == 0) goto L_0x008f
            r2.disconnect()
        L_0x008f:
            return r0
        L_0x0090:
            if (r2 == 0) goto L_0x0095
            r2.disconnect()
        L_0x0095:
            return r0
        L_0x0096:
            if (r2 == 0) goto L_0x009b
            r2.disconnect()
        L_0x009b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.MiUbicacionActivity.a(java.lang.String):java.util.ArrayList");
    }

    private String c(LatLng latLng) {
        String str;
        try {
            List fromLocation = new Geocoder(this, Locale.getDefault()).getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (fromLocation == null || fromLocation.size() <= 0) {
                return "";
            }
            Address address = (Address) fromLocation.get(0);
            if (address.getMaxAddressLineIndex() <= 0 || address.getAddressLine(0).equalsIgnoreCase("")) {
                String str2 = "%s, %s";
                Object[] objArr = new Object[2];
                objArr[0] = address.getLocality() != null ? address.getLocality() : "";
                objArr[1] = address.getCountryName();
                str = String.format(str2, objArr);
            } else {
                if (address.getLocality() != null) {
                    if (!address.getLocality().equalsIgnoreCase("")) {
                        str = String.format("%s, %s, %s", new Object[]{address.getAddressLine(0), address.getLocality(), address.getCountryName()});
                    }
                }
                str = String.format("%s, %s", new Object[]{address.getAddressLine(0), address.getCountryName()});
            }
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private void a(final PlaceMap placeMap) {
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("?placeid=");
        sb2.append(placeMap.getPlace_id());
        sb.append(sb2.toString());
        if (!sb.toString().toString().contains("key=")) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("&key=");
            sb3.append(getApplicationContext().getString(R.string.google_maps_api_key));
            sb.append(sb3.toString());
        }
        if (!sb.toString().toString().contains("language=")) {
            sb.append("&language=es");
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, sb.toString(), new Listener<String>() {
            /* renamed from: a */
            public void onResponse(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject == null || !jSONObject.has("status") || !ResultValues.OK.toLowerCase().equals(jSONObject.get("status").toString().toLowerCase())) {
                        MiUbicacionActivity.this.b(MiUbicacionActivity.this.getString(R.string.IDXX_ERROR_FIND_PLACE));
                        return;
                    }
                    JSONObject jSONObject2 = jSONObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location");
                    placeMap.setLat(jSONObject2.getString("lat"));
                    placeMap.setLon(jSONObject2.getString("lng"));
                    MiUbicacionActivity.this.b(placeMap);
                    MiUbicacionActivity.this.b(MiUbicacionActivity.this.N, placeMap.getLatLng());
                    MiUbicacionActivity.this.a(Double.valueOf(placeMap.getLatLng().latitude), Double.valueOf(placeMap.getLatLng().longitude));
                } catch (Exception unused) {
                    MiUbicacionActivity.this.b(MiUbicacionActivity.this.getString(R.string.IDXX_ERROR_FIND_PLACE));
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                MiUbicacionActivity.this.b(MiUbicacionActivity.this.getString(R.string.IDXX_ERROR_FIND_PLACE));
            }
        }));
    }

    public void onMapReady(GoogleMap googleMap) {
        this.B = googleMap;
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 0);
            return;
        }
        k();
    }

    private void k() {
        if (this.B != null) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                this.B.setMyLocationEnabled(true);
            }
            this.B.setOnMarkerClickListener(this);
            this.B.getUiSettings().setMapToolbarEnabled(false);
            this.B.getUiSettings().setMyLocationButtonEnabled(false);
            this.B.setOnCameraChangeListener(this);
            this.B.setOnMapLongClickListener(this);
            d(r());
        }
    }

    public void onConnected(Bundle bundle) {
        if (this.K != null && h()) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
                return;
            }
            l();
        }
    }

    private void l() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            this.C = LocationServices.FusedLocationApi.getLastLocation(this.K);
        }
    }

    private void d(LatLng latLng) {
        this.J = latLng;
        this.q.setLocation(this.J);
        b(this.N, this.J);
        a(this.M, this.J);
        a(Double.valueOf(this.J.latitude), Double.valueOf(this.J.longitude));
    }

    public void onConnectionSuspended(int i) {
        this.K.connect();
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("@dev", "Error en la conexió");
    }

    public void onMapLongClick(LatLng latLng) {
        b(this.N, latLng);
        this.q.setShowLeyenda(false);
        c(false);
        a(Double.valueOf(latLng.latitude), Double.valueOf(latLng.longitude));
    }

    private void m() {
        this.Q = new ActionItem(1, getString(R.string.IDXX_ADDRESS, new Object[]{""}));
        this.Q.setTypeFace(getAssets(), CustomTypeFace.NORMAL);
        this.Q.setColor(getResources().getColor(R.color.generic_black));
        this.Q.setSticky(true);
        ActionItem actionItem = new ActionItem(2, getString(R.string.ID78_MYLOCATION_BTN_STABLISHLOC), getResources().getDrawable(R.drawable.ic_localizacion), getResources().getColor(R.color.red_medium_light));
        actionItem.setTypeFace(getAssets(), CustomTypeFace.BOLD);
        actionItem.setSticky(true);
        ActionItem actionItem2 = new ActionItem(3, getString(R.string.ID79_MYLOCATION_BTN_BOOKMARK), getResources().getDrawable(R.drawable.ic_favoritos), getResources().getColor(R.color.red_medium_light));
        actionItem2.setSticky(true);
        actionItem2.setTypeFace(getAssets(), CustomTypeFace.BOLD);
        this.p = new QuickAction(getApplicationContext());
        this.p.addActionItem(this.Q);
        this.p.addActionItem(actionItem);
        this.p.addActionItem(actionItem2);
        this.p.removeSeparator();
        this.p.setOnActionItemClickListener(new OnActionItemClickListener() {
            public void onItemClick(QuickAction quickAction, int i, int i2) {
                try {
                    ActionItem actionItem = MiUbicacionActivity.this.p.getActionItem(i);
                    if (actionItem != null) {
                        if (actionItem.getActionId() == 3) {
                            Intent intent = new Intent(MiUbicacionActivity.this, StorageMyFavoriteActivity.class);
                            Favorito favorito = new Favorito(MiUbicacionActivity.this.B.getCameraPosition().target.latitude, MiUbicacionActivity.this.B.getCameraPosition().target.longitude, actionItem.getTitle());
                            intent.putExtra("favorito", favorito);
                            MiUbicacionActivity.this.startActivity(intent);
                        } else if (actionItem.getActionId() == 2) {
                            MiUbicacionActivity.this.J = MiUbicacionActivity.this.B.getCameraPosition().target;
                            MiUbicacionActivity.this.q.setLocation(MiUbicacionActivity.this.J);
                            MiUbicacionActivity.this.e(MiUbicacionActivity.this.J);
                        }
                    }
                    if (MiUbicacionActivity.this.p == null) {
                        return;
                    }
                } catch (Exception e) {
                    Log.e("@dev", "Error en la globa del mapa", e);
                    if (MiUbicacionActivity.this.p == null) {
                        return;
                    }
                } catch (Throwable th) {
                    if (MiUbicacionActivity.this.p != null) {
                        MiUbicacionActivity.this.p.dismiss();
                    }
                    throw th;
                }
                MiUbicacionActivity.this.p.dismiss();
            }
        });
    }

    private void n() {
        String c = c(this.B.getCameraPosition().target);
        if (this.Q != null) {
            this.Q.setTitle(Html.fromHtml(getString(R.string.IDXX_ADDRESS, new Object[]{c})).toString());
            this.p.show(this.imageMarker);
            this.s.trackScreen(getString(R.string.analytics_screen_name_ubicacion_home));
        }
    }

    /* access modifiers changed from: private */
    public void e(LatLng latLng) {
        this.s.trackEvent(getString(R.string.analytics_category_ubicacion), getString(R.string.analytics_action_ubicacion), getString(R.string.analytics_label_ubicacion_establecer));
        Intent intent = new Intent();
        this.P.putDouble(MIUBICACION_LAT, latLng.latitude);
        this.P.putDouble(MIUBICACION_LON, latLng.longitude);
        intent.putExtras(this.P);
        setResult(-1, intent);
        f();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 1:
                if (i2 == -1) {
                    this.P.putAll(intent.getExtras());
                    this.J = this.B.getCameraPosition().target;
                    this.q.setLocation(this.J);
                    e(this.J);
                    return;
                }
                return;
            case 2:
                if (i2 == -1) {
                    try {
                        double doubleExtra = intent.getDoubleExtra(MIUBICACION_LAT, 0.0d);
                        double doubleExtra2 = intent.getDoubleExtra(MIUBICACION_LON, 0.0d);
                        b(this.N, new LatLng(doubleExtra, doubleExtra2));
                        a(Double.valueOf(doubleExtra), Double.valueOf(doubleExtra2));
                        return;
                    } catch (Exception unused) {
                        d(this.J);
                        return;
                    }
                } else {
                    return;
                }
            default:
                return;
        }
    }

    private void b(boolean z2) {
        if (z2) {
            this.imgGPS.setVisibility(0);
        } else {
            this.imgGPS.setVisibility(8);
        }
    }

    private void c(boolean z2) {
        if (!z2) {
            this.layoutInfo.setVisibility(8);
        }
    }

    private boolean o() {
        return this.layoutInfo.getVisibility() == 0;
    }

    private int p() {
        if (getIntent() == null || !getIntent().hasExtra(INDEX_PARAM_TYPE_UBICACION)) {
            return 0;
        }
        return getIntent().getIntExtra(INDEX_PARAM_TYPE_UBICACION, 0);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        hideKeyboard();
        PlaceMap item = this.G.getItem(i);
        if (item != null) {
            if (item.getLat() == null || item.getLon() == null) {
                a(item);
            } else {
                b(this.N, item.getLatLng());
                a(Double.valueOf(item.getLatLng().latitude), Double.valueOf(item.getLatLng().longitude));
            }
        }
        this.I = null;
    }

    /* access modifiers changed from: private */
    public void b(PlaceMap placeMap) {
        this.H.addNewPlace(placeMap);
    }

    private ArrayList<PlaceMap> q() {
        if (this.I != null) {
            return this.I;
        }
        ArrayList<PlaceMap> arrayList = (ArrayList) this.H.getMyPlacesFromStorage();
        if (arrayList != null && arrayList.size() > 0) {
            PlaceMap placeMap = new PlaceMap();
            placeMap.setName(getString(R.string.last_places));
            placeMap.isHeader = true;
            arrayList.add(0, placeMap);
        }
        return arrayList;
    }

    public boolean onMarkerClick(Marker marker) {
        this.s.trackEvent(getString(R.string.analytics_category_ubicacion), getString(R.string.analytics_action_ubicacion), getString(R.string.analytics_label_ubicacion_clic_poi));
        a(Double.valueOf(marker.getPosition().latitude), Double.valueOf(marker.getPosition().longitude));
        return true;
    }

    public void onLocationChanged(Location location) {
        this.C = location;
    }

    private LatLng r() {
        LatLng s2 = s();
        if (s2 != null) {
            return s2;
        }
        LatLng t2 = t();
        return t2 == null ? u() : t2;
    }

    private LatLng s() {
        try {
            if (this.q != null) {
                SessionManager sessionManager = this.q;
                return SessionManager.getLocation();
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al obtener la localización de la sessión", e);
        }
        return null;
    }

    private LatLng t() {
        try {
            if (this.C != null) {
                return new LatLng(this.C.getLatitude(), this.C.getLongitude());
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al obtener la posición desde el servicio", e);
        }
        return null;
    }

    private LatLng u() {
        try {
            if (this.w != null) {
                return new LatLng(Double.valueOf(this.w.getLatitud()).doubleValue(), Double.valueOf(this.w.getLongitud()).doubleValue());
            }
        } catch (Exception e) {
            Log.e("@dev", "Erro al obtener la posición desde el bean de ws", e);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void a(Double d, Double d2) {
        if (d != null && d2 != null && this.B != null) {
            this.B.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(d.doubleValue(), d2.doubleValue()), this.x));
        }
    }

    public void onCameraChange(CameraPosition cameraPosition) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        if ((this.N != null && decimalFormat.format(this.N.getPosition().latitude).equals(decimalFormat.format(cameraPosition.target.latitude)) && decimalFormat.format(this.N.getPosition().longitude).equals(decimalFormat.format(cameraPosition.target.longitude))) || (this.M != null && decimalFormat.format(this.M.getPosition().latitude).equals(decimalFormat.format(cameraPosition.target.latitude)) && decimalFormat.format(this.M.getPosition().longitude).equals(decimalFormat.format(cameraPosition.target.longitude)))) {
            n();
        }
    }

    /* access modifiers changed from: protected */
    public void createLocationRequest() {
        this.E = new LocationRequest();
        this.E.setInterval(LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS);
        this.E.setFastestInterval((long) this.F);
        this.E.setPriority(100);
    }

    /* access modifiers changed from: protected */
    public void startLocationUpdates() {
        try {
            if (this.K != null && this.E != null) {
                if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                    ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
                    return;
                }
                l();
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al actualizar la localización", e);
        }
    }

    /* access modifiers changed from: protected */
    public void stopLocationUpdates() {
        try {
            if (this.K != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(this.K, (LocationListener) this);
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al parar la actualización de la localización", e);
        }
    }

    public void getLocationStatus(boolean z2) {
        b(z2 && !o());
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        switch (i) {
            case 0:
                if (iArr.length <= 0 || iArr[0] != 0) {
                    onBackPressed();
                    return;
                } else {
                    k();
                    return;
                }
            case 1:
                if (iArr.length <= 0 || iArr[0] != 0) {
                    onBackPressed();
                    return;
                } else {
                    l();
                    return;
                }
            default:
                return;
        }
    }
}
