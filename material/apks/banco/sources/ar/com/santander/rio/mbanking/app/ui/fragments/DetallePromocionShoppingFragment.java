package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.IHorizontalScrollListListener;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.ToggleItem;
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
import java.util.List;
import javax.inject.Inject;

public class DetallePromocionShoppingFragment extends BaseFragment implements ISharedListener, IHorizontalScrollListListener, OnMapReadyCallback {
    @Inject
    SessionManager a;
    private SupportMapFragment ad;
    @Inject
    IDataManager b;
    @Inject
    AnalyticsManager c;
    private MyMarker d;
    /* access modifiers changed from: private */
    public GoogleMap e;
    private String f;
    private String g;
    private ImageLoader h;
    private String i;
    @InjectView(2131364801)
    ImageView imgCajero;
    @InjectView(2131364811)
    View imgShopping;
    @InjectView(2131364812)
    View imgShoppingSeparator;
    @InjectView(2131365630)
    LinearLayout scrollDetalle;
    @InjectView(2131365634)
    LinearLayout scrollLocales;
    @InjectView(2131365635)
    ScrollView scrollMap;
    @InjectView(2131365806)
    HorizontalScrollList tabSelector;
    @InjectView(2131366272)
    TextView txtDesc;
    @InjectView(2131366273)
    TextView txtDesc2;
    @InjectView(2131366274)
    TextView txtDirec;
    @InjectView(2131366275)
    TextView txtDist;
    @InjectView(2131366281)
    TextView txtHtmlDesc;
    @InjectView(2131366430)
    TextView txtLegales;
    @InjectView(2131366284)
    WebView txtLocales;
    @InjectView(2131366287)
    TextView txtNombre;

    public void OnCheckedChangeListener(List<ToggleItem> list) {
    }

    public void onBackPressed() {
    }

    public static DetallePromocionShoppingFragment newInstance(MyMarker myMarker) {
        DetallePromocionShoppingFragment detallePromocionShoppingFragment = new DetallePromocionShoppingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("marker", myMarker);
        detallePromocionShoppingFragment.setArguments(bundle);
        return detallePromocionShoppingFragment;
    }

    public void onSharedClick() {
        try {
            startActivity(Intent.createChooser(new DispatchShareIntent(this.a, getActivity().getPackageManager(), getActivity().getApplicationContext()).getListIntentToShare(this.d), getResources().getString(R.string.TEXT_TITLE_COMPARTIR)));
        } catch (EmptyAppShare unused) {
            Toast.makeText(getActivity(), getString(R.string.IDXX_EMPTY_APP_SHARE), 1).show();
        } catch (Exception unused2) {
            Toast.makeText(getActivity(), getString(R.string.MSG_USER000029_General_errorGenerico), 1).show();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.d = (MyMarker) getArguments().getParcelable("marker");
        }
        if (this.d == null) {
            getActivity().finish();
        }
        this.h = ImageLoaderFactory.getImageLoader(getActivity().getApplicationContext());
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_promocion_shopping, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.f = getResources().getString(R.string.ID357_PROMO_DATA_TAB_DETAIL);
        this.g = getResources().getString(R.string.ID358_PROMO_DATA_TAB_ADSHOP);
        this.tabSelector.addItem(this.f, true, (int) R.color.grey_light);
        this.tabSelector.addItem(this.g, false, (int) R.color.grey_light);
        this.tabSelector.setHorizontalScrollListener(this);
        this.tabSelector.show();
        if (this.d.desc2 != null) {
            this.txtDesc2.setText(Html.fromHtml(this.d.desc2));
            this.txtDesc2.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (this.d.desc != null) {
            this.txtDesc.setText(Html.fromHtml(this.d.desc));
            this.txtDesc.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.h.loadImage(this.d.icon, this.imgCajero);
        this.txtDirec.setText(Html.fromHtml(this.d.direc));
        if (this.d.dist != null) {
            this.txtDist.setText(this.d.dist);
            this.txtDist.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.txtNombre.setText(Html.fromHtml(this.d.title));
        this.txtNombre.setMovementMethod(LinkMovementMethod.getInstance());
        this.b.getDetallesPromocion(this.d.f245id);
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        y();
    }

    private void a(LatLng latLng) {
        CameraPosition build = new Builder().target(latLng).zoom(17.0f).build();
        try {
            this.e.animateCamera(CameraUpdateFactory.newCameraPosition(build));
        } catch (NullPointerException unused) {
            MapsInitializer.initialize(getActivity());
            this.e.animateCamera(CameraUpdateFactory.newCameraPosition(build));
        }
    }

    private void a(MyMarker myMarker) {
        this.e.clear();
        LatLng latLng = new LatLng(myMarker.latitude.doubleValue(), myMarker.longitude.doubleValue());
        this.e.addMarker(new MarkerOptions().position(latLng));
        a(latLng);
    }

    private void y() {
        if (this.e == null) {
            this.ad = SupportMapFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.sucursal_detalle_map_view, this.ad).commit();
            this.ad.getMapAsync(this);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.e = googleMap;
        this.e.setPadding(0, 0, 0, (int) ((getResources().getDisplayMetrics().density * 65.0f) + 0.5f));
        a(this.d);
    }

    @Subscribe
    public void onGetDetallesPromocion(PromocionDetalleEvent promocionDetalleEvent) {
        PromocionDetalleResponseBean promocionDetalleResponseBean = (PromocionDetalleResponseBean) promocionDetalleEvent.getBeanResponse();
        if (promocionDetalleEvent.getResult() == TypeResult.OK) {
            PromocionDetalleBodyResponseBean promocionDetalleBodyResponseBean = promocionDetalleResponseBean.getPromocionDetalleBodyResponseBean;
            if (promocionDetalleBodyResponseBean.datosPromocion != null) {
                Spanned fromHtml = Html.fromHtml(Utils.formatIsbanHTMLCode(promocionDetalleBodyResponseBean.datosPromocion.descHtml));
                this.txtHtmlDesc.setText(fromHtml);
                this.txtHtmlDesc.setMovementMethod(LinkMovementMethod.getInstance());
                this.txtLegales.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(promocionDetalleBodyResponseBean.datosPromocion.legales)));
                this.txtLegales.setMovementMethod(LinkMovementMethod.getInstance());
                this.i = getResources().getString(R.string.MSG_USER000003_Shared, new Object[]{fromHtml, this.d.url});
                if (!(this.d == null || this.d.tipo == null || (!this.d.tipo.equalsIgnoreCase("AS") && !this.d.tipo.equalsIgnoreCase("AH")))) {
                    this.imgShopping.setVisibility(0);
                    this.imgShoppingSeparator.setVisibility(0);
                }
                if (promocionDetalleBodyResponseBean.datosPromocion.descHtml2 != null) {
                    this.txtLocales.loadData(Utils.formatIsbanHTMLCode(promocionDetalleBodyResponseBean.datosPromocion.descHtml2), "text/html", "UTF-8");
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

    public void OnNewItemSelected(ToggleItem toggleItem) {
        if (toggleItem.getLabel().equals(this.f)) {
            this.scrollDetalle.setVisibility(0);
            this.scrollLocales.setVisibility(8);
        } else if (toggleItem.getLabel().equals(this.g)) {
            this.c.trackEvent(getString(R.string.analytics_category_promociones), getString(R.string.analytics_action_promociones_ficha), getString(R.string.analytics_label_promociones_clic_locales));
            this.scrollLocales.setVisibility(0);
            this.scrollDetalle.setVisibility(8);
        }
    }
}
