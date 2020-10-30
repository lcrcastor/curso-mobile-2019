package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.location.Location;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDistanceAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollAdapter;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collection;

public abstract class MapInfiniteSucursalesArrayAdapter extends InfiniteScrollAdapter {
    private ArrayList<SucursalBean> a = new ArrayList<>();
    private ImageLoader b;
    private CFiltersAccessibility c;

    public abstract String getDistanceMarkers(LatLng latLng);

    public MapInfiniteSucursalesArrayAdapter(Context context) {
        super(context);
        this.c = new CFiltersAccessibility(context);
        this.b = ImageLoaderFactory.getImageLoader(context);
    }

    public ArrayList getItems() {
        return this.a;
    }

    public void addItems(Collection collection) {
        if (collection.size() > 0) {
            this.a.addAll(collection);
        } else {
            super.setDoneLoading();
        }
        notifyDataSetChanged();
    }

    public Object getRealItem(int i) {
        return this.a.get(i);
    }

    public View getRealView(LayoutInflater layoutInflater, int i, View view, ViewGroup viewGroup) {
        View inflate = layoutInflater.inflate(R.layout.list_item_sucursal, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.imgCajero);
        TextView textView = (TextView) inflate.findViewById(R.id.txtNombre);
        TextView textView2 = (TextView) inflate.findViewById(R.id.txtDist);
        TextView textView3 = (TextView) inflate.findViewById(R.id.txtDirec);
        TextView textView4 = (TextView) inflate.findViewById(R.id.txtDesc);
        SucursalBean sucursalBean = (SucursalBean) this.a.get(i);
        if (sucursalBean != null) {
            this.b.loadImage(sucursalBean.thumbnailSmall, imageView);
            textView.setText(Html.fromHtml(sucursalBean.nombre));
            Location location = new Location("");
            location.setLatitude(Double.parseDouble(sucursalBean.latitud));
            location.setLongitude(Double.parseDouble(sucursalBean.longitud));
            String distanceMarkers = getDistanceMarkers(new LatLng(location.getLatitude(), location.getLongitude()));
            if (distanceMarkers != null) {
                textView2.setText(distanceMarkers);
            }
            new CDistanceAcc().applyFilter(textView2, textView2.getText().toString());
            textView4.setText(sucursalBean.descripcion);
            textView4.setContentDescription(sucursalBean.descripcion != null ? this.c.filterBetweenTimeFormat(sucursalBean.descripcion) : "");
            textView3.setText(Html.fromHtml(sucursalBean.direccion));
        }
        return inflate;
    }

    public View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.list_loading, null);
    }
}
