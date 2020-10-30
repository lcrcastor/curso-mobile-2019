package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.app.exceptions.EmptyAppShare;
import ar.com.santander.rio.mbanking.app.module.promotions.DispatchShareIntent;
import ar.com.santander.rio.mbanking.components.mapInfo.MyMarker;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.PromocionDetalleEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.PromocionDetalleResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionDetalleBodyResponseBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class DetallePromocionStandardFragment extends BaseFragment implements ISharedListener, OnMapReadyCallback {
    @Inject
    IDataManager a;
    @Inject
    SessionManager b;
    @Inject
    AnalyticsManager c;
    /* access modifiers changed from: private */
    public GoogleMap d;
    private ImageLoader e;
    private MyMarker f;
    private SupportMapFragment g;
    @InjectView(2131364801)
    ImageView imgCajero;
    @InjectView(2131364811)
    View imgShopping;
    @InjectView(2131364812)
    View imgShoppingSeparator;
    @InjectView(2131365635)
    ScrollView scrollMap;
    @InjectView(2131366272)
    TextView txtDesc;
    @InjectView(2131366273)
    TextView txtDesc2;
    @InjectView(2131366274)
    TextView txtDirec;
    @InjectView(2131366275)
    TextView txtDist;
    @InjectView(2131366287)
    TextView txtNombre;
    @InjectView(2131366430)
    WebView webViewLegales;

    public void onBackPressed() {
    }

    public static DetallePromocionStandardFragment newInstance(MyMarker myMarker) {
        DetallePromocionStandardFragment detallePromocionStandardFragment = new DetallePromocionStandardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("marker", myMarker);
        detallePromocionStandardFragment.setArguments(bundle);
        return detallePromocionStandardFragment;
    }

    public void onSharedClick() {
        try {
            startActivity(Intent.createChooser(new DispatchShareIntent(this.b, getActivity().getPackageManager(), getActivity().getApplicationContext()).getListIntentToShare(this.f), getResources().getString(R.string.TEXT_TITLE_COMPARTIR)));
        } catch (EmptyAppShare unused) {
            Toast.makeText(getActivity(), getString(R.string.IDXX_EMPTY_APP_SHARE), 1).show();
        } catch (Exception unused2) {
            Toast.makeText(getActivity(), getString(R.string.MSG_USER000029_General_errorGenerico), 1).show();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.f = (MyMarker) getArguments().getParcelable("marker");
        }
        if (this.f == null) {
            getActivity().finish();
        }
        this.e = ImageLoaderFactory.getImageLoader(getActivity().getApplicationContext());
        MapsInitializer.initialize(getActivity());
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_promocion_standard, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        if (this.f.desc2 != null) {
            this.txtDesc2.setText(Html.fromHtml(this.f.desc2));
            this.txtDesc2.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (this.f.desc != null) {
            this.txtDesc.setText(Html.fromHtml(this.f.desc));
            this.txtDesc.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.e.loadImage(this.f.icon, this.imgCajero);
        this.txtDirec.setText(Html.fromHtml(this.f.direc));
        if (this.f.dist != null) {
            this.txtDist.setText(this.f.dist);
            this.txtDist.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.txtNombre.setText(Html.fromHtml(this.f.title));
        this.txtNombre.setMovementMethod(LinkMovementMethod.getInstance());
        this.a.getDetallesPromocion(this.f.f245id);
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        y();
    }

    private void a(LatLng latLng) {
        CameraPosition build = new Builder().target(latLng).zoom(17.0f).build();
        try {
            this.d.animateCamera(CameraUpdateFactory.newCameraPosition(build));
        } catch (NullPointerException unused) {
            MapsInitializer.initialize(getActivity());
            this.d.animateCamera(CameraUpdateFactory.newCameraPosition(build));
        }
    }

    private void a(MyMarker myMarker) {
        this.d.clear();
        LatLng latLng = new LatLng(myMarker.latitude.doubleValue(), myMarker.longitude.doubleValue());
        this.d.addMarker(new MarkerOptions().position(latLng));
        a(latLng);
    }

    private void y() {
        if (this.d == null) {
            this.g = SupportMapFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.sucursal_detalle_map_view, this.g).commit();
            this.g.getMapAsync(this);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.d = googleMap;
        this.d.setPadding(0, 0, 0, (int) ((getResources().getDisplayMetrics().density * 65.0f) + 0.5f));
        a(this.f);
    }

    @Subscribe
    public void onGetDetallesPromocion(PromocionDetalleEvent promocionDetalleEvent) {
        PromocionDetalleResponseBean promocionDetalleResponseBean = (PromocionDetalleResponseBean) promocionDetalleEvent.getBeanResponse();
        if (promocionDetalleEvent.getResult() == TypeResult.OK) {
            PromocionDetalleBodyResponseBean promocionDetalleBodyResponseBean = promocionDetalleResponseBean.getPromocionDetalleBodyResponseBean;
            if (promocionDetalleBodyResponseBean.datosPromocion != null) {
                this.webViewLegales.setVisibility(8);
                WebView webView = this.webViewLegales;
                StringBuilder sb = new StringBuilder();
                sb.append(promocionDetalleBodyResponseBean.datosPromocion.descHtml);
                sb.append(promocionDetalleBodyResponseBean.datosPromocion.legales);
                webView.loadData("<html xmlns=\\\"http://www.w3.org/1999/xhtml\\\"><head><meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=utf-8\\\"></head><body>CONTENT_HTML</body></html>".replace("CONTENT_HTML", sb.toString()), "text/html", "UTF-8");
                this.webViewLegales.reload();
                this.webViewLegales.setVisibility(0);
                if ("AH".equalsIgnoreCase(this.f.tipo)) {
                    this.imgShopping.setVisibility(0);
                    this.imgShoppingSeparator.setVisibility(0);
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
    }
}
