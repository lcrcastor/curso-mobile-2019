package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoader;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.CouponsListSuperClubPresenter;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.CouponsListSuperClubView;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.adapters.CouponsListSuperClubAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.CouponsListSuperClubAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public abstract class CouponsListSuperClubActivity extends BaseMvpActivity implements CouponsListSuperClubView, OnItemClickListener {
    protected ImageView btnActionBarBack;
    protected ImageView btnActionBarHelp;
    protected CouponsListSuperClubPresenter comodinesPresenter;
    @InjectView(2131362684)
    ImageView imgCouponGroup;
    @InjectView(2131362680)
    LinearLayout lst_table_view;
    protected CategoriaSuperClubBean mCategoryData;
    protected CouponsListSuperClubAdapter mComodinesAdapter;
    protected List<CuponSuperClubBean> mCouponsData;
    protected ImageLoader mImageLoader;
    protected String mPoints;
    protected CategoriaSuperClubBean mSubcategoryData;
    @Inject
    AnalyticsManager p;
    @InjectView(2131362687)
    TextView txtCouponGroup;
    @InjectView(2131362676)
    TextView txtPoints;

    public void OnItemClick(View view) {
    }

    public void clearScreenData() {
    }

    public void initialize() {
        this.mPoints = getIntent().getStringExtra(SuperClubConstants.EXTRA_POINTS);
        this.mImageLoader = ImageLoaderFactory.getImageLoader(getApplicationContext());
        this.mCategoryData = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_CATEGORY);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_WITH_HELP);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.btnActionBarBack = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        this.btnActionBarHelp = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.img_help);
        this.btnActionBarBack.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                CouponsListSuperClubActivity.this.onBackPressed();
            }
        });
        this.btnActionBarHelp.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                Intent intent = new Intent(CouponsListSuperClubActivity.this, InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT_WITH_ACCESSIBILITY_LOADING);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, CouponsListSuperClubActivity.this.getString(R.string.IDXX_COMODINES_SUPER_CLUB_AYUDA_TITLE));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, CouponsListSuperClubActivity.this.getString(R.string.IDXX_COMODINES_SUPER_CLUB_AYUDA_BODY));
                CouponsListSuperClubActivity.this.startActivity(intent);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void configureComodinListAdapter(List<CuponSuperClubBean> list) {
        ImageLoader imageLoader = ImageLoaderFactory.getImageLoader(this);
        this.mCouponsData = list;
        TableLayout tableLayout = new TableLayout(this);
        int i = 0;
        for (CuponSuperClubBean cuponSuperClubBean : list) {
            LinearLayout linearLayout = (LinearLayout) ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.coupon_list_superclub, null);
            ImageView imageView = (ImageView) linearLayout.findViewById(R.id.F20_01_IMG_COUPON);
            TextView textView = (TextView) linearLayout.findViewById(R.id.F20_01_LBL_COUPON);
            imageLoader.loadImage(cuponSuperClubBean.imagenDescuentoMedioDePago, imageView);
            imageView.setContentDescription(cuponSuperClubBean.descripcion);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            String decimalGroupingFormat = Utils.getDecimalGroupingFormat(cuponSuperClubBean.puntos);
            spannableStringBuilder.append(decimalGroupingFormat).append(UtilsCuentas.SEPARAOR2).append(getString(R.string.ID3004_COMODINES_SUPER_CLUB_LBL_PUNTOS));
            spannableStringBuilder.setSpan(new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/OpenSans-Regular.otf")), 0, decimalGroupingFormat.length(), 33);
            textView.setText(spannableStringBuilder, BufferType.SPANNABLE);
            linearLayout.setTag(Integer.valueOf(i));
            linearLayout.setClickable(true);
            linearLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CouponsListSuperClubActivity.this.comodinesPresenter.onComodinSelected((CuponSuperClubBean) CouponsListSuperClubActivity.this.mCouponsData.get(Integer.parseInt(view.getTag().toString())));
                }
            });
            tableLayout.addView(linearLayout, i);
            i++;
        }
        this.lst_table_view.addView(tableLayout);
    }

    /* access modifiers changed from: protected */
    public void initializeComodinList() {
        this.lst_table_view.removeAllViews();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.fragment_coupons_list_superclub);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        initializeComodinList();
        this.comodinesPresenter = new CouponsListSuperClubPresenter(this.mBus, this.mDataManager);
        this.comodinesPresenter.attachView(this);
        this.comodinesPresenter.showComodinesList();
    }

    public void attachView() {
        if (!this.comodinesPresenter.isViewAttached()) {
            this.comodinesPresenter.attachView(this);
        }
    }

    public void detachView() {
        if (this.comodinesPresenter.isViewAttached()) {
            this.comodinesPresenter.detachView();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showComodinesList(String str, String str2, String str3, List<CuponSuperClubBean> list) {
        this.txtPoints.setText(Utils.getDecimalGroupingFormat(str));
        this.mImageLoader.loadImage(str2, this.imgCouponGroup);
        this.txtCouponGroup.setText(Html.fromHtml(str3));
        configureComodinListAdapter(list);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 3) {
            Intent intent2 = new Intent();
            if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
            } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
            } else {
                intent2.putExtra("recargarHome", true);
            }
            setResult(-1, intent2);
            finish();
        }
    }
}
