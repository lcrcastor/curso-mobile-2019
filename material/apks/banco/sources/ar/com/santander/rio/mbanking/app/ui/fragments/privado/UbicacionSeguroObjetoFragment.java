package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.ui.activities.UbicacionObjetoActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import javax.inject.Inject;

public class UbicacionSeguroObjetoFragment extends BaseFragment {
    public static final String ACCESS_FINE_LOCATION_DIALOG_TAG = "ACCESS_FINE_LOCATION_DIALOG_TAG";
    public static final int FINE_LOCATION_REQUEST_ID = 101;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener a;
    private LocationManager ad;
    private LocationListener ae = null;
    private View af;
    private Bundle ag;
    @Inject
    public AnalyticsManager analyticsManager;
    private MapView b;
    /* access modifiers changed from: private */
    public GoogleMap c;
    /* access modifiers changed from: private */
    public Activity d;
    /* access modifiers changed from: private */
    public Location e;
    /* access modifiers changed from: private */
    public Button f;
    private String g = "";
    private String h;
    /* access modifiers changed from: private */
    public EditText i;

    public interface OnFragmentInteractionListener extends IFragmentBase {
        void getLocationError();

        void onMapLoadError(Exception exc);

        void onSelectedPosition(Location location, String str);
    }

    public static UbicacionSeguroObjetoFragment newInstance(String str, String str2) {
        UbicacionSeguroObjetoFragment ubicacionSeguroObjetoFragment = new UbicacionSeguroObjetoFragment();
        ubicacionSeguroObjetoFragment.h = str2;
        Bundle bundle = new Bundle();
        bundle.putString(PrepararCoberturaObjetoFragment.SEGOBJ_UBQ_ID, str);
        ubicacionSeguroObjetoFragment.setArguments(bundle);
        return ubicacionSeguroObjetoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.g = getArguments().getString(PrepararCoberturaObjetoFragment.SEGOBJ_UBQ_ID);
        }
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.af = layoutInflater.inflate(R.layout.fragment_ubicacion_seguro_objeto, viewGroup, false);
        this.a.configureBackActionBar();
        this.ag = bundle;
        E();
        D();
        I();
        A();
        return this.af;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.analyticsManager.trackScreen(Screens.objectLocation(this.h));
    }

    private void y() {
        z();
        this.ad = (LocationManager) this.d.getSystemService("location");
        this.ad.requestLocationUpdates("gps", 0, BitmapDescriptorFactory.HUE_RED, this.ae);
        B();
    }

    private void z() {
        if (ActivityCompat.checkSelfPermission(this.d.getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(this.d.getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
        }
    }

    private void A() {
        if (C()) {
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 101);
            return;
        }
        y();
    }

    private void B() {
        this.b = (MapView) this.af.findViewById(R.id.map);
        this.b.onCreate(this.ag);
        this.b.onResume();
        try {
            MapsInitializer.initialize(this.d.getApplicationContext());
        } catch (Exception e2) {
            this.a.onMapLoadError(e2);
        }
        this.b.getMapAsync(new OnMapReadyCallback() {
            public void onMapReady(GoogleMap googleMap) {
                UbicacionSeguroObjetoFragment.this.c = googleMap;
                UbicacionSeguroObjetoFragment.this.c.getUiSettings().setMyLocationButtonEnabled(false);
                UbicacionSeguroObjetoFragment.this.c.getUiSettings().setAllGesturesEnabled(false);
                if (ContextCompat.checkSelfPermission(UbicacionSeguroObjetoFragment.this.d, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                    UbicacionSeguroObjetoFragment.this.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 101);
                    return;
                }
                UbicacionSeguroObjetoFragment.this.F();
            }
        });
    }

    private boolean C() {
        return VERSION.SDK_INT >= 23;
    }

    private void D() {
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UbicacionSeguroObjetoFragment.this.a.onSelectedPosition(UbicacionSeguroObjetoFragment.this.e, UbicacionSeguroObjetoFragment.this.i.getText() != null ? UbicacionSeguroObjetoFragment.this.i.getText().toString() : "");
            }
        });
    }

    private void E() {
        ((TextView) this.af.findViewById(R.id.title).findViewById(R.id.functionality_title)).setText("Ubicación del Objeto");
        ((TextView) this.af.findViewById(R.id.subtitle).findViewById(R.id.section_title)).setText("Compartinos la ubicación actual del objeto que querés asegurar.");
        this.f = (Button) this.af.findViewById(R.id.acceptButton);
        this.f.setEnabled(false);
        ((TextView) this.af.findViewById(R.id.form).findViewById(R.id.label)).setText("Esta Dirección corresponde a");
        this.i = (EditText) this.af.findViewById(R.id.form).findViewById(R.id.value);
        this.i.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() == 0 || !UbicacionSeguroObjetoFragment.this.a(charSequence)) {
                    UbicacionSeguroObjetoFragment.this.f.setEnabled(false);
                } else {
                    UbicacionSeguroObjetoFragment.this.f.setEnabled(true);
                }
            }
        });
        this.i.setSingleLine();
        this.i.setHint("Ej. Casa,Trabajo");
        ((TextView) this.af.findViewById(R.id.leyenda)).setText(this.g);
        this.f.setText("Seleccionar");
    }

    /* access modifiers changed from: private */
    public boolean a(CharSequence charSequence) {
        return charSequence.toString().trim().length() > 0;
    }

    /* access modifiers changed from: private */
    public void F() {
        if (ActivityCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.c.setMyLocationEnabled(true);
        }
        if (this.ad != null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(1);
            criteria.setPowerRequirement(1);
            if (this.e == null) {
                this.e = H();
            }
            if (this.e != null) {
                LatLng latLng = new LatLng(this.e.getLatitude(), this.e.getLongitude());
                if (latLng != null) {
                    this.c.addMarker(new MarkerOptions().position(latLng));
                    this.c.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(latLng).zoom(15.0f).build()));
                    return;
                }
                return;
            }
            G();
        }
    }

    private void G() {
        View findViewById = this.af.findViewById(R.id.error_ubicacion);
        View findViewById2 = this.af.findViewById(R.id.subtitle);
        View findViewById3 = this.af.findViewById(R.id.map);
        View findViewById4 = this.af.findViewById(R.id.form);
        View findViewById5 = this.af.findViewById(R.id.scroll_leyenda);
        findViewById.setVisibility(0);
        findViewById2.setVisibility(8);
        findViewById3.setVisibility(8);
        findViewById4.setVisibility(8);
        findViewById5.setVisibility(8);
        ImageView imageView = (ImageView) findViewById.findViewById(R.id.image_error);
        TextView textView = (TextView) findViewById.findViewById(R.id.description_error);
        imageView.setBackgroundResource(R.drawable.error_continuacion);
        textView.setText(getContext().getString(R.string.ubicacion_desc_error));
        this.f.setText(getContext().getString(R.string.ID_4105_SEGUROS_LBL_VOLVER));
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UbicacionSeguroObjetoFragment.this.a.getLocationError();
            }
        });
    }

    private Location H() {
        Location location = null;
        for (String str : this.ad.getProviders(true)) {
            z();
            Location lastKnownLocation = this.ad.getLastKnownLocation(str);
            if (lastKnownLocation != null && (location == null || lastKnownLocation.getAccuracy() < location.getAccuracy())) {
                location = lastKnownLocation;
            }
        }
        return location;
    }

    private void I() {
        this.ae = new LocationListener() {
            public void onProviderDisabled(String str) {
            }

            public void onProviderEnabled(String str) {
            }

            public void onStatusChanged(String str, int i, Bundle bundle) {
            }

            public void onLocationChanged(Location location) {
                UbicacionSeguroObjetoFragment.this.e = location;
            }
        };
    }

    public Location getLocation() {
        return this.e;
    }

    public void onResume() {
        super.onResume();
        if (this.b != null) {
            this.b.onResume();
        }
    }

    public void onPause() {
        super.onPause();
        if (this.b != null) {
            this.b.onPause();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.b != null) {
            this.b.onDestroy();
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (this.b != null) {
            this.b.onLowMemory();
        }
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        if (i2 == 101) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                J();
            } else {
                y();
            }
        }
    }

    private void J() {
        if (K()) {
            onBackPressed();
        } else {
            G();
        }
    }

    private boolean K() {
        return ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.ACCESS_FINE_LOCATION");
    }

    public void onBackPressed() {
        ((UbicacionObjetoActivity) getActivity()).onBackPressed();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.a = (OnFragmentInteractionListener) context;
            this.d = getActivity();
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
    }
}
