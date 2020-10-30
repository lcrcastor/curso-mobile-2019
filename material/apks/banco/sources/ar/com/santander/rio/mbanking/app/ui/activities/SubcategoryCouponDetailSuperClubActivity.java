package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesAdheridosSuperClub;
import butterknife.InjectView;
import java.util.ArrayList;

public class SubcategoryCouponDetailSuperClubActivity extends CouponDetailSuperClubActivity {
    @InjectView(2131362685)
    ImageView imgLocalesAdheridos;
    protected LocalesAdheridosSuperClub mStoresData;
    @InjectView(2131362691)
    TextView txtLocalesAdheridos;

    public void initialize() {
        super.initialize();
        this.mSubcategoryData = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_SUBCATEGORY);
        this.mStoresData = (LocalesAdheridosSuperClub) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_STORES);
        this.txtLocalesAdheridos.setOnClickListener(this);
        this.imgLocalesAdheridos.setOnClickListener(this);
    }

    public void configureLayout() {
        LayoutParams layoutParams = new LayoutParams((int) getResources().getDimension(R.dimen.ComodinesListForSubcategoryComodinesSuperClub_width), (int) getResources().getDimension(R.dimen.ComodinesListForSubcategoryComodinesSuperClub_height));
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.HeaderGroupComodinesListForSubcategoryComodinesSuperClub_marginLeft), (int) getResources().getDimension(R.dimen.HeaderGroupComodinesListForSubcategoryComodinesSuperClub_marginTop), (int) getResources().getDimension(R.dimen.HeaderGroupComodinesListForSubcategoryComodinesSuperClub_marginRight), (int) getResources().getDimension(R.dimen.HeaderGroupComodinesListForSubcategoryComodinesSuperClub_marginBottom));
        this.imgCouponGroup.setLayoutParams(layoutParams);
        this.layoutLocalesAdheridos.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.r.trackScreen(getString(R.string.analytics_screen_superclub_detalle_comodin, new Object[]{this.mCategoryData.nombre, this.mSubcategoryData.nombre, this.mCouponData.descripcion}));
        this.r.trackScreen(getString(R.string.analytics_screen_superclub_legal_comodines_con_rubro, new Object[]{this.mCategoryData.nombre, this.mSubcategoryData.nombre, this.mCouponData.descripcion}));
    }

    public void showCouponDetail() {
        showComodinDetail(this.mPoints, this.mSubcategoryData.nombre, this.mSubcategoryData.imagen, this.mCouponData.puntos, this.mCouponData.imagenDescuentoMedioDePago, this.mCouponData.legales);
    }

    public void showComodinReceipt(String str, String str2, ArrayList<LeyendaSuperClubBean> arrayList) {
        Intent intent = new Intent(this, SubcategoryCouponReceiptSuperClubActivity.class);
        intent.putExtra(SuperClubConstants.EXTRA_RECEIPT_TITLE, this.mCouponData.descripcion);
        intent.putExtra(SuperClubConstants.EXTRA_RECEIPT_NUMBER, str);
        intent.putExtra(SuperClubConstants.EXTRA_RECEIPT_CHANGE_DATE, str2);
        intent.putExtra(SuperClubConstants.EXTRA_POINTS, this.mCouponData.puntos);
        intent.putParcelableArrayListExtra(SuperClubConstants.EXTRA_LEYENDS, arrayList);
        intent.putExtra(SuperClubConstants.EXTRA_STORES_NAME, this.mStoresData != null ? this.mStoresData.marca : null);
        intent.putExtra(SuperClubConstants.EXTRA_STORES, this.mStoresData);
        intent.putExtra(SuperClubConstants.EXTRA_COMODIN, this.mCouponData);
        intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, this.mCategoryData);
        intent.putExtra(SuperClubConstants.EXTRA_SUBCATEGORY, this.mSubcategoryData);
        startActivityForResult(intent, 4);
    }

    public void onClick(View view) {
        super.onClick(view);
        int id2 = view.getId();
        if (id2 == R.id.F20_02_IMG_STORES || id2 == R.id.F20_02_LBL_STORES) {
            if (this.mStoresData != null) {
                Intent intent = new Intent(this, StoresSuperClubActivity.class);
                intent.putExtra(SuperClubConstants.EXTRA_STORES_NAME, this.mStoresData != null ? this.mStoresData.marca : null);
                intent.putExtra(SuperClubConstants.EXTRA_STORES, this.mStoresData);
                intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, this.mCategoryData);
                intent.putExtra(SuperClubConstants.EXTRA_SUBCATEGORY, this.mSubcategoryData);
                intent.putExtra(SuperClubConstants.EXTRA_COMODIN, this.mCouponData);
                startActivity(intent);
                return;
            }
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.USER00004_TITLE), Html.fromHtml(getString(R.string.USER00004_BODY)).toString(), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }
            });
            newInstance.show(getSupportFragmentManager(), "Dialog");
        } else if (id2 == R.id.F20_03_BTN_CHANGE) {
            this.r.trackScreen(getString(R.string.analytics_screen_superclub_confirmacion_canje, new Object[]{this.mCategoryData.nombre, this.mSubcategoryData.nombre, this.mCouponData.descripcion}));
        }
    }
}
