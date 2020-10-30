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
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollAdapter;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionSucursalBean;
import ar.com.santander.rio.mbanking.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;

public class MapInfinitePromocionesArrayAdapter extends InfiniteScrollAdapter {
    private final Location a;
    private ArrayList<PromocionSucursalBean> b = new ArrayList<>();
    private ImageLoader c;

    public MapInfinitePromocionesArrayAdapter(Context context, Location location) {
        super(context);
        this.c = ImageLoaderFactory.getImageLoader(context);
        this.a = location;
    }

    public ArrayList getItems() {
        return this.b;
    }

    public void addItems(Collection collection) {
        if (collection.size() > 0) {
            this.b.addAll(collection);
        } else {
            super.setDoneLoading();
        }
        notifyDataSetChanged();
    }

    public void cleanItems() {
        if (this.b != null) {
            this.b.clear();
        }
        notifyDataSetChanged();
    }

    public Object getRealItem(int i) {
        return this.b.get(i);
    }

    public View getRealView(LayoutInflater layoutInflater, int i, View view, ViewGroup viewGroup) {
        View inflate = layoutInflater.inflate(R.layout.list_item_cajero, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.imgCajero);
        TextView textView = (TextView) inflate.findViewById(R.id.txtNombre);
        TextView textView2 = (TextView) inflate.findViewById(R.id.txtDist);
        TextView textView3 = (TextView) inflate.findViewById(R.id.txtDirec);
        PromocionSucursalBean promocionSucursalBean = (PromocionSucursalBean) this.b.get(i);
        if (promocionSucursalBean != null) {
            this.c.loadImage(promocionSucursalBean.thumbnailSmall, imageView);
            textView.setText(Html.fromHtml(promocionSucursalBean.nombre));
            Location location = new Location("");
            location.setLatitude(Double.parseDouble(promocionSucursalBean.latitud));
            location.setLongitude(Double.parseDouble(promocionSucursalBean.longitud));
            textView2.setText(Utils.formatDist(this.a.distanceTo(location)));
            new CDistanceAcc().applyFilter(textView2, textView2.getText().toString());
            textView3.setText(Html.fromHtml(promocionSucursalBean.direccion));
        }
        return inflate;
    }

    public View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.list_loading, null);
    }
}
