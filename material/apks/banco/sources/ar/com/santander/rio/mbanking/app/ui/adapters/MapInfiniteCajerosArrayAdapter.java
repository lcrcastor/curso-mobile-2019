package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.location.Location;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDistanceAcc;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollAdapter;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CajeroBean;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collection;

public abstract class MapInfiniteCajerosArrayAdapter extends InfiniteScrollAdapter {
    private ArrayList<CajeroBean> a = new ArrayList<>();
    private ImageLoader b;

    public abstract String getDistanceMarkers(LatLng latLng);

    public MapInfiniteCajerosArrayAdapter(Context context) {
        super(context);
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
        View inflate = layoutInflater.inflate(R.layout.list_item_cajero, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.imgCajero);
        TextView textView = (TextView) inflate.findViewById(R.id.txtNombre);
        TextView textView2 = (TextView) inflate.findViewById(R.id.txtDist);
        TextView textView3 = (TextView) inflate.findViewById(R.id.txtDirec);
        TextView textView4 = (TextView) inflate.findViewById(R.id.txtDesc);
        CajeroBean cajeroBean = (CajeroBean) this.a.get(i);
        if (cajeroBean != null) {
            this.b.loadImage(cajeroBean.getThumbnailSmall(), imageView);
            textView.setText(Html.fromHtml(cajeroBean.getNombre()));
            Location location = new Location("");
            location.setLatitude(Double.parseDouble(cajeroBean.getLatitud()));
            location.setLongitude(Double.parseDouble(cajeroBean.getLongitud()));
            String distanceMarkers = getDistanceMarkers(new LatLng(location.getLatitude(), location.getLongitude()));
            if (distanceMarkers != null) {
                textView2.setText(distanceMarkers);
            }
            new CDistanceAcc().applyFilter(textView2, textView2.getText().toString());
            textView3.setText(Html.fromHtml(cajeroBean.getDireccion()));
            if (TextUtils.isEmpty(cajeroBean.getDescripcion())) {
                textView4.setVisibility(8);
            } else {
                textView4.setVisibility(0);
                textView4.setText(Html.fromHtml(cajeroBean.getDescripcion()));
            }
        }
        return inflate;
    }

    public View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.list_loading, null);
    }
}
