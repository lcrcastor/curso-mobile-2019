package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.widget.LinearLayout.LayoutParams;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesAdheridosSuperClub;
import java.util.ArrayList;

public class SubcategoryCouponsListSuperClubActivity extends CouponsListSuperClubActivity {
    protected LocalesAdheridosSuperClub mStoresData;

    public void initialize() {
        super.initialize();
        this.mCategoryData = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_CATEGORY);
        this.mSubcategoryData = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_SUBCATEGORY);
        this.mStoresData = (LocalesAdheridosSuperClub) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_STORES);
    }

    public void configureLayout() {
        LayoutParams layoutParams = new LayoutParams((int) getResources().getDimension(R.dimen.ComodinesListForSubcategoryComodinesSuperClub_width), (int) getResources().getDimension(R.dimen.ComodinesListForSubcategoryComodinesSuperClub_height));
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.ComodinesListForSubcategoryComodinesSuperClub_marginLeft), (int) getResources().getDimension(R.dimen.ComodinesListForSubcategoryComodinesSuperClub_marginTop), (int) getResources().getDimension(R.dimen.ComodinesListForSubcategoryComodinesSuperClub_marginRight), (int) getResources().getDimension(R.dimen.ComodinesListForSubcategoryComodinesSuperClub_marginBottom));
        this.imgCouponGroup.setLayoutParams(layoutParams);
    }

    public void showComodinesList() {
        ArrayList parcelableArrayListExtra = getIntent().getParcelableArrayListExtra(SuperClubConstants.EXTRA_COMODINES);
        this.p.trackScreen(getString(R.string.analytics_screen_superclub_comodines_subrubro, new Object[]{this.mCategoryData.nombre, this.mSubcategoryData.nombre}));
        showComodinesList(this.mPoints, this.mSubcategoryData.imagen, this.mSubcategoryData.nombre, parcelableArrayListExtra);
    }

    public void showComodinDetail(CuponSuperClubBean cuponSuperClubBean) {
        Intent intent = new Intent(this, SubcategoryCouponDetailSuperClubActivity.class);
        intent.putExtra(SuperClubConstants.EXTRA_POINTS, this.mPoints);
        intent.putExtra(SuperClubConstants.EXTRA_COMODIN, cuponSuperClubBean);
        intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, this.mCategoryData);
        intent.putExtra(SuperClubConstants.EXTRA_SUBCATEGORY, this.mSubcategoryData);
        intent.putExtra(SuperClubConstants.EXTRA_STORES, this.mStoresData);
        startActivityForResult(intent, 3);
    }
}
