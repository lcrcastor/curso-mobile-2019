package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife.Finder;

public class FavoritosActivity$$ViewInjector {
    public static void inject(Finder finder, FavoritosActivity favoritosActivity, Object obj) {
        favoritosActivity.eTxtSearch = (ClearableEditText) finder.findRequiredView(obj, R.id.eTxtSearch, "field 'eTxtSearch'");
    }

    public static void reset(FavoritosActivity favoritosActivity) {
        favoritosActivity.eTxtSearch = null;
    }
}
