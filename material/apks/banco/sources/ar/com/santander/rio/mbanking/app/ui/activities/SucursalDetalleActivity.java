package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDistanceAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.components.mapInfo.MyMarker;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.view.TelephoneRow;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class SucursalDetalleActivity extends BaseActivity implements OnMapReadyCallback {
    @InjectView(2131364801)
    ImageView imgCajero;
    @Inject
    IDataManager p;
    @Inject
    SessionManager q;
    @Inject
    Bus r;
    @Inject
    AnalyticsManager s;
    private GoogleMap t;
    @InjectView(2131365841)
    TelephoneRow telephoneRow;
    @InjectView(2131366272)
    TextView txtDesc;
    @InjectView(2131366274)
    TextView txtDirec;
    @InjectView(2131366275)
    TextView txtDist;
    @InjectView(2131366287)
    TextView txtNombre;
    private ImageLoader u;
    private SupportMapFragment v;
    private MyMarker w;

    /* access modifiers changed from: protected */
    public void onResume() {
        this.r.register(this);
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.r.unregister(this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView((int) R.layout.sucursal_detalle_activity);
        this.u = ImageLoaderFactory.getImageLoader(getApplicationContext());
        this.s.trackScreen(getString(R.string.analytics_screen_name_sucursal_detalle));
        ButterKnife.inject((Activity) this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayUseLogoEnabled(false);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back_row, null);
        inflate.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SucursalDetalleActivity.this.finish();
                SucursalDetalleActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1));
        this.w = (MyMarker) getIntent().getParcelableExtra("marker");
        this.u.loadImage(this.w.icon, this.imgCajero);
        this.txtDirec.setText(this.w.direc != null ? Html.fromHtml(this.w.direc) : "");
        if (this.w.dist != null) {
            this.txtDist.setText(this.w.dist);
            new CDistanceAcc().applyFilter(this.txtDist, this.txtDist.getText().toString());
        }
        this.txtNombre.setText(this.w.title != null ? Html.fromHtml(this.w.title) : "");
        this.txtDesc.setText(this.w.desc != null ? Html.fromHtml(this.w.desc) : "");
        this.txtDesc.setContentDescription(this.w.desc != null ? new CFiltersAccessibility(getApplicationContext()).filterHs(this.w.desc) : "");
        if (this.w.desc2 != null) {
            final String[] split = this.w.desc2.split(":");
            if (split.length > 1) {
                this.telephoneRow.setNumber(split[1]);
                this.telephoneRow.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("tel:");
                        sb.append(split[1]);
                        String sb2 = sb.toString();
                        Intent intent = new Intent("android.intent.action.DIAL");
                        intent.setData(Uri.parse(sb2));
                        intent.setFlags(268435456);
                        SucursalDetalleActivity.this.startActivity(intent);
                    }
                });
            } else if (split.length == 1) {
                this.telephoneRow.setNumber(split[0]);
                this.telephoneRow.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("tel:");
                        sb.append(split[0]);
                        String sb2 = sb.toString();
                        Intent intent = new Intent("android.intent.action.DIAL");
                        intent.setData(Uri.parse(sb2));
                        intent.setFlags(268435456);
                        SucursalDetalleActivity.this.startActivity(intent);
                    }
                });
            }
        }
        this.p.getCajerosDetalle(this.w.f245id);
        b();
    }

    private void a(LatLng latLng) {
        this.t.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(latLng).zoom(17.0f).build()));
    }

    private void a(MyMarker myMarker) {
        this.t.clear();
        LatLng latLng = new LatLng(myMarker.latitude.doubleValue(), myMarker.longitude.doubleValue());
        this.t.addMarker(new MarkerOptions().position(latLng));
        a(latLng);
    }

    private void b() {
        if (this.v == null) {
            this.v = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.sucursal_detalle_map_view, this.v).commit();
            this.v.getMapAsync(this);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.t = googleMap;
        this.t.setPadding(0, 0, 0, (int) ((getResources().getDisplayMetrics().density * 65.0f) + 0.5f));
        a(this.w);
    }
}
