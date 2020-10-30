package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.CouponDetailSuperClubPresenter;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.CouponDetailSuperClubView;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public abstract class CouponDetailSuperClubActivity extends BaseMvpActivity implements OnClickListener, CouponDetailSuperClubView {
    @InjectView(2131362695)
    Button btnChange;
    @InjectView(2131362683)
    ImageView imgCoupon;
    @InjectView(2131362684)
    ImageView imgCouponGroup;
    @InjectView(2131362685)
    ImageView imgStores;
    @InjectView(2131362693)
    LinearLayout layoutLocalesAdheridos;
    @InjectView(2131362691)
    TextView lblStores;
    protected CategoriaSuperClubBean mCategoryData;
    protected CuponSuperClubBean mCouponData;
    protected ImageLoader mImageLoader;
    protected String mPoints;
    protected CategoriaSuperClubBean mSubcategoryData;
    ImageView p;
    ImageView q;
    @Inject
    AnalyticsManager r;
    /* access modifiers changed from: private */
    public CouponDetailSuperClubPresenter s;
    @InjectView(2131362686)
    TextView txtCoupon;
    @InjectView(2131362687)
    TextView txtCouponGroup;
    @InjectView(2131362689)
    TextView txtLegal;
    @InjectView(2131362688)
    TextView txtPoints;

    public void clearScreenData() {
    }

    public void initialize() {
        this.mCategoryData = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_CATEGORY);
        this.mPoints = getIntent().getStringExtra(SuperClubConstants.EXTRA_POINTS);
        this.mCouponData = (CuponSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_COMODIN);
        this.mImageLoader = ImageLoaderFactory.getImageLoader(getApplicationContext());
        this.btnChange.setOnClickListener(this);
        this.lblStores.setContentDescription(CAccessibility.applyFilterMaskVinculo(getString(R.string.ID3005_COMODINES_SUPER_CLUB_BTN_LOCALES)));
        this.imgStores.setContentDescription(CAccessibility.applyFilterMaskVinculo(getString(R.string.ID3005_COMODINES_SUPER_CLUB_BTN_LOCALES)));
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_WITH_HELP);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        this.q = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.img_help);
        this.p.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                CouponDetailSuperClubActivity.this.onBackPressed();
            }
        });
        this.q.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                Intent intent = new Intent(CouponDetailSuperClubActivity.this, InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT_WITH_ACCESSIBILITY_LOADING);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, CouponDetailSuperClubActivity.this.getString(R.string.IDXX_COMODINES_SUPER_CLUB_AYUDA_TITLE));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, CouponDetailSuperClubActivity.this.getString(R.string.IDXX_COMODINES_SUPER_CLUB_AYUDA_BODY));
                CouponDetailSuperClubActivity.this.startActivity(intent);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_coupon_detail_superclub);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        this.s = new CouponDetailSuperClubPresenter(this.mBus, this.mDataManager, this);
        this.s.attachView(this);
        this.s.showCouponDetail();
    }

    public void attachView() {
        if (!this.s.isViewAttached()) {
            this.s.attachView(this);
        }
    }

    public void detachView() {
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
    }

    public void onBackPressed() {
        Log.w("LMB", "ComodinDetail.onBackPressed()");
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showComodinDetail(String str, String str2, String str3, String str4, String str5, String str6) {
        this.txtPoints.setText(Utils.getDecimalGroupingFormat(str));
        this.mImageLoader.loadImage(str3, this.imgCouponGroup);
        this.txtCouponGroup.setText(Html.fromHtml(str2).toString());
        this.mImageLoader.loadImage(str5, this.imgCoupon);
        this.imgCoupon.setContentDescription(this.mCouponData.descripcion);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String decimalGroupingFormat = Utils.getDecimalGroupingFormat(str4);
        spannableStringBuilder.append(decimalGroupingFormat).append(UtilsCuentas.SEPARAOR2).append(getString(R.string.ID3004_COMODINES_SUPER_CLUB_LBL_PUNTOS));
        spannableStringBuilder.setSpan(new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/OpenSans-Regular.otf")), 0, decimalGroupingFormat.length(), 33);
        this.txtCoupon.setText(spannableStringBuilder, BufferType.SPANNABLE);
        this.txtLegal.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(str6)));
        try {
            this.txtLegal.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.txtLegal.getText().toString()));
        } catch (Exception unused) {
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.F20_03_BTN_CHANGE) {
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.ID3008_COMODINES_SUPER_CLUB_LBL_CANJE), Html.fromHtml(getString(R.string.ID3009_COMODINES_SUPER_CLUB_LBL_CONFIRMA)).toString(), null, null, getString(R.string.ID3011_COMODINES_SUPER_CLUB_BTN_SI), getString(R.string.ID3010_COMODINES_SUPER_CLUB_BTN_NO), null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    CouponDetailSuperClubActivity.this.s.onCanjearComodin(CouponDetailSuperClubActivity.this.mCouponData);
                }

                public void onNegativeButton() {
                    CouponDetailSuperClubActivity.this.r.trackEvent(CouponDetailSuperClubActivity.this.getString(R.string.analytics_screen_category_comodines_superclub), CouponDetailSuperClubActivity.this.getString(R.string.analytics_screen_action_cancelar_aviso_confirmacion), CouponDetailSuperClubActivity.this.getString(R.string.analytics_screen_label_aviso_confirmacion_canje_comodin));
                }
            });
            this.r.trackEvent(getString(R.string.analytics_screen_category_comodines_superclub), getString(R.string.analytics_screen_action_menu_comprobante), getString(R.string.analytics_screen_label_comprobante));
            newInstance.show(getSupportFragmentManager(), "popupConfirmation");
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 4) {
            Intent intent2 = new Intent();
            if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
            } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
            }
            setResult(-1, intent2);
            finish();
        }
    }
}
