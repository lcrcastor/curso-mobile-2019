package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.ClearableAutoCompleteTextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class MiUbicacionActivity$$ViewInjector {
    public static void inject(Finder finder, final MiUbicacionActivity miUbicacionActivity, Object obj) {
        View findRequiredView = finder.findRequiredView(obj, R.id.eTxtSearch, "field 'eTxtSearch' and method 'onClickSearchBox'");
        miUbicacionActivity.eTxtSearch = (ClearableAutoCompleteTextView) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                miUbicacionActivity.onClickSearchBox();
            }
        });
        miUbicacionActivity.layoutInfo = (ViewGroup) finder.findRequiredView(obj, R.id.layoutInfo, "field 'layoutInfo'");
        miUbicacionActivity.imageMarker = (ImageView) finder.findRequiredView(obj, R.id.imgMarker, "field 'imageMarker'");
        View findRequiredView2 = finder.findRequiredView(obj, R.id.imgGPS, "field 'imgGPS' and method 'onClickButtonGps'");
        miUbicacionActivity.imgGPS = (ImageView) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                miUbicacionActivity.onClickButtonGps();
            }
        });
        finder.findRequiredView(obj, R.id.txtZona, "method 'clickChooseZone'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                miUbicacionActivity.b();
            }
        });
        finder.findRequiredView(obj, R.id.txtFav, "method 'clickChooseFavorite'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                miUbicacionActivity.c();
            }
        });
    }

    public static void reset(MiUbicacionActivity miUbicacionActivity) {
        miUbicacionActivity.eTxtSearch = null;
        miUbicacionActivity.layoutInfo = null;
        miUbicacionActivity.imageMarker = null;
        miUbicacionActivity.imgGPS = null;
    }
}
