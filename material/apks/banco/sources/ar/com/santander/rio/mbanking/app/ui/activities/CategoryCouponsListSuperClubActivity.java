package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.widget.LinearLayout.LayoutParams;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import java.util.ArrayList;

public class CategoryCouponsListSuperClubActivity extends CouponsListSuperClubActivity {
    public void initialize() {
        super.initialize();
    }

    public void configureLayout() {
        LayoutParams layoutParams = new LayoutParams((int) getResources().getDimension(R.dimen.ComodinesListForCategoryComodinesSuperClub_width), (int) getResources().getDimension(R.dimen.ComodinesListForCategoryComodinesSuperClub_height));
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.ComodinesListForCategoryComodinesSuperClub_marginLeft), (int) getResources().getDimension(R.dimen.ComodinesListForCategoryComodinesSuperClub_marginTop), (int) getResources().getDimension(R.dimen.ComodinesListForCategoryComodinesSuperClub_marginRight), (int) getResources().getDimension(R.dimen.ComodinesListForCategoryComodinesSuperClub_marginBottom));
        this.imgCouponGroup.setLayoutParams(layoutParams);
    }

    public void showComodinesList() {
        ArrayList parcelableArrayListExtra = getIntent().getParcelableArrayListExtra(SuperClubConstants.EXTRA_COMODINES);
        this.p.trackScreen(getString(R.string.analytics_screen_superclub_comodines_rubro));
        showComodinesList(this.mPoints, this.mCategoryData.imagen, this.mCategoryData.nombre, parcelableArrayListExtra);
    }

    public void showComodinDetail(CuponSuperClubBean cuponSuperClubBean) {
        Intent intent = new Intent(this, CategoryCouponDetailSuperClubActivity.class);
        intent.putExtra(SuperClubConstants.EXTRA_POINTS, this.mPoints);
        intent.putExtra(SuperClubConstants.EXTRA_COMODIN, cuponSuperClubBean);
        intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, this.mCategoryData);
        startActivityForResult(intent, 3);
    }
}
