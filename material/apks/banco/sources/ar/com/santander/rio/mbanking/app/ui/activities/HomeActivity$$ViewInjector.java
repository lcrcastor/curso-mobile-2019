package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.CirclePagerIndicator;
import butterknife.ButterKnife.Finder;

public class HomeActivity$$ViewInjector {
    public static void inject(Finder finder, HomeActivity homeActivity, Object obj) {
        homeActivity.progressBar = (ProgressBar) finder.findRequiredView(obj, R.id.progressBar, "field 'progressBar'");
        homeActivity.mobileBankingButton = (ImageButton) finder.findRequiredView(obj, R.id.mobile_banking_button, "field 'mobileBankingButton'");
        homeActivity.promocionesButton = (ImageButton) finder.findRequiredView(obj, R.id.promociones_button, "field 'promocionesButton'");
        homeActivity.promocionButton = (ImageButton) finder.findRequiredView(obj, R.id.promocion_button, "field 'promocionButton'");
        homeActivity.subeButton = (ImageButton) finder.findRequiredView(obj, R.id.recargaSube_button, "field 'subeButton'");
        homeActivity.sucursalesButton = (Button) finder.findRequiredView(obj, R.id.button_sucursales, "field 'sucursalesButton'");
        homeActivity.numerosUtilesButton = (Button) finder.findRequiredView(obj, R.id.button_numeros_utiles, "field 'numerosUtilesButton'");
        homeActivity.cajerosButton = (Button) finder.findRequiredView(obj, R.id.button_cajeros, "field 'cajerosButton'");
        homeActivity.softTokenButton = (Button) finder.findRequiredView(obj, R.id.button_soft_token, "field 'softTokenButton'");
        homeActivity.promocionesViewPager = (ViewPager) finder.findRequiredView(obj, R.id.carruselViewPager, "field 'promocionesViewPager'");
        homeActivity.circlePagerIndicator = (CirclePagerIndicator) finder.findRequiredView(obj, R.id.circlePagerIndicator, "field 'circlePagerIndicator'");
        homeActivity.carrusel = (ConstraintLayout) finder.findRequiredView(obj, R.id.carrusel, "field 'carrusel'");
        homeActivity.mLayoutOptions = (LinearLayout) finder.findRequiredView(obj, R.id.options, "field 'mLayoutOptions'");
        homeActivity.mLayoutHomeTop = (ConstraintLayout) finder.findRequiredView(obj, R.id.f119hometop, "field 'mLayoutHomeTop'");
        homeActivity.mLayoutHomeTopDisabled = (LinearLayout) finder.findRequiredView(obj, R.id.layoutHomeTopDisabled, "field 'mLayoutHomeTopDisabled'");
        homeActivity.mLayoutHomeBody = (RelativeLayout) finder.findRequiredView(obj, R.id.home_body_image, "field 'mLayoutHomeBody'");
        homeActivity.mLayoutPromotions = (ConstraintLayout) finder.findRequiredView(obj, R.id.promociones, "field 'mLayoutPromotions'");
        homeActivity.mLayoutRecargaSube = (ConstraintLayout) finder.findRequiredView(obj, R.id.recargaSube, "field 'mLayoutRecargaSube'");
        homeActivity.mobileBankingText = (TextView) finder.findRequiredView(obj, R.id.MobileBankingText, "field 'mobileBankingText'");
        homeActivity.imageViewCellPhone = (ImageView) finder.findRequiredView(obj, R.id.imageViewHome, "field 'imageViewCellPhone'");
    }

    public static void reset(HomeActivity homeActivity) {
        homeActivity.progressBar = null;
        homeActivity.mobileBankingButton = null;
        homeActivity.promocionesButton = null;
        homeActivity.promocionButton = null;
        homeActivity.subeButton = null;
        homeActivity.sucursalesButton = null;
        homeActivity.numerosUtilesButton = null;
        homeActivity.cajerosButton = null;
        homeActivity.softTokenButton = null;
        homeActivity.promocionesViewPager = null;
        homeActivity.circlePagerIndicator = null;
        homeActivity.carrusel = null;
        homeActivity.mLayoutOptions = null;
        homeActivity.mLayoutHomeTop = null;
        homeActivity.mLayoutHomeTopDisabled = null;
        homeActivity.mLayoutHomeBody = null;
        homeActivity.mLayoutPromotions = null;
        homeActivity.mLayoutRecargaSube = null;
        homeActivity.mobileBankingText = null;
        homeActivity.imageViewCellPhone = null;
    }
}
