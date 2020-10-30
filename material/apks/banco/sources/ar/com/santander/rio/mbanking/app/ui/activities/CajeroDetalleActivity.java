package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.commons.CAnalitycs;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDistanceAcc;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.components.mapInfo.MyMarker;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetCajerosDetalleEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCajerosDetalleResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCajerosDetalleBodyResponseBean;
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
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class CajeroDetalleActivity extends BaseActivity implements OnMapReadyCallback {
    @InjectView(2131364801)
    ImageView imgCajero;
    @InjectView(2131365124)
    LinearLayout llDesc;
    @InjectView(2131365128)
    LinearLayout llEstado;
    @InjectView(2131365129)
    LinearLayout llExpende;
    @Inject
    IDataManager p;
    @Inject
    SessionManager q;
    @Inject
    AnalyticsManager r;
    @Inject
    Bus s;
    private GoogleMap t;
    @InjectView(2131366272)
    TextView txtDesc;
    @InjectView(2131366274)
    TextView txtDirec;
    @InjectView(2131366275)
    TextView txtDist;
    @InjectView(2131366276)
    TextView txtEstado;
    @InjectView(2131366278)
    TextView txtExpende;
    @InjectView(2131366287)
    TextView txtNombre;
    private ImageLoader u;
    private SupportMapFragment v;
    private MyMarker w;

    /* access modifiers changed from: protected */
    public void onResume() {
        this.s.register(this);
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.s.unregister(this);
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
        setContentView((int) R.layout.cajero_detalle_activity);
        this.u = ImageLoaderFactory.getImageLoader(getApplicationContext());
        ButterKnife.inject((Activity) this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayUseLogoEnabled(false);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back_row, null);
        ((ImageView) inflate.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CajeroDetalleActivity.this.finish();
                CajeroDetalleActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1));
        this.w = (MyMarker) getIntent().getParcelableExtra("marker");
        if (this.w.desc != null) {
            this.llDesc.setVisibility(0);
            this.txtDesc.setText(Html.fromHtml(this.w.desc));
        } else {
            this.llDesc.setVisibility(8);
        }
        String hitDetalleCajero = CAnalitycs.getHitDetalleCajero(getApplicationContext(), this.w.icon);
        if (hitDetalleCajero != null) {
            this.r.trackScreen(getString(R.string.analytics_screen_name_cajeros_detalle, new Object[]{hitDetalleCajero}));
        }
        this.u.loadImage(this.w.icon, this.imgCajero);
        this.txtDirec.setText(Html.fromHtml(this.w.direc));
        if (this.w.dist != null) {
            this.txtDist.setText(this.w.dist);
            new CDistanceAcc().applyFilter(this.txtDist, this.txtDist.getText().toString());
        }
        this.txtNombre.setText(Html.fromHtml(this.w.title));
        this.p.getCajerosDetalle(this.w.f245id);
        b();
    }

    @Subscribe
    public void onGetCajerosDetalle(GetCajerosDetalleEvent getCajerosDetalleEvent) {
        GetCajerosDetalleResponseBean getCajerosDetalleResponseBean = (GetCajerosDetalleResponseBean) getCajerosDetalleEvent.getBeanResponse();
        if (getCajerosDetalleEvent.getResult() == TypeResult.OK) {
            GetCajerosDetalleBodyResponseBean getCajerosDetalleBodyResponseBean = getCajerosDetalleResponseBean.getGetCajerosDetalleBodyResponseBean();
            if (getCajerosDetalleBodyResponseBean.datosCajeros != null) {
                this.llExpende.setVisibility(0);
                this.llEstado.setVisibility(0);
                this.txtEstado.setText(getCajerosDetalleBodyResponseBean.datosCajeros.habilitado);
                this.txtExpende.setText(getCajerosDetalleBodyResponseBean.datosCajeros.expendeDolares);
                return;
            }
            this.llExpende.setVisibility(4);
            this.llEstado.setVisibility(4);
        }
    }

    private void a(LatLng latLng) {
        this.t.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(latLng).zoom(15.0f).build()));
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
