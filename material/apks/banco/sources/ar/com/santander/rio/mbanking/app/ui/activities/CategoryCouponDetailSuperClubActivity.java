package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaSuperClubBean;
import java.util.ArrayList;

public class CategoryCouponDetailSuperClubActivity extends CouponDetailSuperClubActivity {
    public void initialize() {
        super.initialize();
    }

    public void configureLayout() {
        LayoutParams layoutParams = new LayoutParams((int) getResources().getDimension(R.dimen.ComodinesListForCategoryComodinesSuperClub_width), (int) getResources().getDimension(R.dimen.ComodinesListForCategoryComodinesSuperClub_height));
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.HeaderGroupComodinesListForCategoryComodinesSuperClub_marginLeft), (int) getResources().getDimension(R.dimen.HeaderGroupComodinesListForCategoryComodinesSuperClub_marginTop), (int) getResources().getDimension(R.dimen.HeaderGroupComodinesListForCategoryComodinesSuperClub_marginRight), (int) getResources().getDimension(R.dimen.HeaderGroupComodinesListForCategoryComodinesSuperClub_marginBottom));
        this.imgCouponGroup.setLayoutParams(layoutParams);
        this.layoutLocalesAdheridos.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.r.trackScreen(getString(R.string.analytics_screen_superclub_detalle_comodin_2, new Object[]{this.mCategoryData.nombre, this.mCouponData.descripcion}));
        this.r.trackScreen(getString(R.string.analytics_screen_superclub_legal_comodines_sin_rubro, new Object[]{this.mCategoryData.nombre, this.mCouponData.descripcion}));
    }

    public void showCouponDetail() {
        showComodinDetail(this.mPoints, this.mCategoryData.nombre, this.mCategoryData.imagen, this.mCouponData.puntos, this.mCouponData.imagenDescuentoMedioDePago, this.mCouponData.legales);
    }

    public void showComodinReceipt(String str, String str2, ArrayList<LeyendaSuperClubBean> arrayList) {
        Intent intent = new Intent(this, CategoryCouponReceiptSuperClubActivity.class);
        intent.putExtra(SuperClubConstants.EXTRA_RECEIPT_TITLE, this.mCouponData.descripcion);
        intent.putExtra(SuperClubConstants.EXTRA_RECEIPT_NUMBER, str);
        intent.putExtra(SuperClubConstants.EXTRA_RECEIPT_CHANGE_DATE, str2);
        intent.putExtra(SuperClubConstants.EXTRA_POINTS, this.mCouponData.puntos);
        intent.putParcelableArrayListExtra(SuperClubConstants.EXTRA_LEYENDS, arrayList);
        intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, this.mCategoryData);
        intent.putExtra(SuperClubConstants.EXTRA_COMODIN, this.mCouponData);
        startActivityForResult(intent, 4);
    }

    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.F20_03_BTN_CHANGE) {
            this.r.trackScreen(getString(R.string.analytics_screen_superclub_confirmacion_canje_2, new Object[]{this.mCategoryData.nombre, this.mCouponData.descripcion}));
        }
    }
}
