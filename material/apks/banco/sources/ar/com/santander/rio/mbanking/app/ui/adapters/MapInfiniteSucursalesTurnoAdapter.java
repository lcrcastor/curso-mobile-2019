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
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDistanceAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.app.ui.constants.NesrConstants;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollAdapter;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collection;

public abstract class MapInfiniteSucursalesTurnoAdapter extends InfiniteScrollAdapter {
    private ArrayList<SucursalBean> a = new ArrayList<>();
    private Context b;
    private ImageLoader c;
    private CFiltersAccessibility d;

    public abstract String getDistanceMarkers(LatLng latLng);

    public MapInfiniteSucursalesTurnoAdapter(Context context) {
        super(context);
        this.d = new CFiltersAccessibility(context);
        this.c = ImageLoaderFactory.getImageLoader(context);
        this.b = context;
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
        View inflate = layoutInflater.inflate(R.layout.list_item_sucursal_turno, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.imgCajero);
        TextView textView = (TextView) inflate.findViewById(R.id.txtNombre);
        TextView textView2 = (TextView) inflate.findViewById(R.id.txtDist);
        TextView textView3 = (TextView) inflate.findViewById(R.id.txtDirec);
        TextView textView4 = (TextView) inflate.findViewById(R.id.txtDesc);
        TextView textView5 = (TextView) inflate.findViewById(R.id.tvTiempo);
        ImageView imageView2 = (ImageView) inflate.findViewById(R.id.arrow_left_row);
        SucursalBean sucursalBean = (SucursalBean) this.a.get(i);
        if (sucursalBean != null) {
            this.c.loadImage(sucursalBean.imagen, imageView);
            textView.setText(Html.fromHtml(sucursalBean.descripcion));
            Location location = new Location("");
            textView5.setText(sucursalBean.tiempoEspera);
            location.setLatitude(Double.parseDouble(sucursalBean.latitud));
            location.setLongitude(Double.parseDouble(sucursalBean.longitud));
            String distanceMarkers = getDistanceMarkers(new LatLng(location.getLatitude(), location.getLongitude()));
            if (distanceMarkers != null) {
                textView2.setText(distanceMarkers);
            }
            new CDistanceAcc().applyFilter(textView2, textView2.getText().toString());
            textView.setContentDescription(CAccessibility.getInstance(this.b).applyGuion(textView.getText().toString()));
            StringBuilder sb = new StringBuilder();
            sb.append(this.b.getString(R.string.ID_4889_TURNOS_LBL_HORARIO_DE_));
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(sucursalBean.horaApertura);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.b.getString(R.string.ID_4890_TURNOS_LBL__A_));
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(sucursalBean.horaCierre);
            textView4.setText(sb.toString());
            String str = "";
            try {
                str = CAccessibility.getInstance(this.b).applyFilterTime(textView4.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            textView4.setContentDescription(str);
            textView3.setText(Html.fromHtml(sucursalBean.direccion));
            textView3.setContentDescription(CAccessibility.getInstance(this.b).applyFilter_BsAs(CAccessibility.getInstance(this.b).applyFilter_Ciudad(CAccessibility.getInstance(this.b).applyFilterDireccion(textView3.getText().toString()))));
            if (sucursalBean.tiempoEspera.equalsIgnoreCase(NesrConstants.SUCURSAL_CERRADA)) {
                imageView2.setVisibility(8);
            }
        }
        return inflate;
    }

    public View getLoadingView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.list_loading, null);
    }
}
