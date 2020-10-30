package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.CouponReceiptSuperClubPresenter;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.CouponReceiptSuperClubView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaSuperClubBean;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.Utils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Iterator;
import javax.inject.Inject;

public abstract class CouponReceiptSuperClubActivity extends MvpPrivateMenuActivity implements OnClickListener, CouponReceiptSuperClubView {
    @InjectView(2131362694)
    Button btnBack;
    @InjectView(2131362703)
    TextView btnStores;
    protected CategoriaSuperClubBean mCategoryData;
    protected CuponSuperClubBean mCouponData;
    protected String mReceiptChangeDate;
    protected ArrayList<LeyendaSuperClubBean> mReceiptLeyends;
    protected String mReceiptNumber;
    protected String mReceiptPoints;
    protected String mReceiptTitle;
    protected CategoriaSuperClubBean mSubcategoryData;
    ImageView p;
    ImageView q;
    @Inject
    AnalyticsManager r;
    protected Boolean receiptHasBeenDownloaded;
    private CouponReceiptSuperClubPresenter s;
    @InjectView(2131362705)
    ScrollView scrReceipt;
    protected OptionsToShare sharingOptions;
    protected OptionsToShare sharingOptionsAtExit;
    private CAccessibility t;
    @InjectView(2131362696)
    TextView txtChangeDate;
    @InjectView(2131362699)
    TextView txtDescription;
    @InjectView(2131362700)
    TextView txtLegal;
    @InjectView(2131362697)
    TextView txtPoints;
    @InjectView(2131362698)
    TextView txtReceiptNumber;

    public void clearScreenData() {
    }

    public int getMainLayout() {
        return R.layout.activity_coupon_receipt_superclub;
    }

    public void initialize() {
        this.receiptHasBeenDownloaded = Boolean.valueOf(false);
        this.mCategoryData = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_CATEGORY);
        this.mCouponData = (CuponSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_COMODIN);
        this.mReceiptTitle = getIntent().getStringExtra(SuperClubConstants.EXTRA_RECEIPT_TITLE);
        this.mReceiptNumber = getIntent().getStringExtra(SuperClubConstants.EXTRA_RECEIPT_NUMBER);
        this.mReceiptChangeDate = getIntent().getStringExtra(SuperClubConstants.EXTRA_RECEIPT_CHANGE_DATE);
        this.mReceiptPoints = getIntent().getStringExtra(SuperClubConstants.EXTRA_POINTS);
        this.mReceiptLeyends = getIntent().getParcelableArrayListExtra(SuperClubConstants.EXTRA_LEYENDS);
        this.btnBack.setOnClickListener(this);
        this.btnStores.setContentDescription(CAccessibility.applyFilterMaskVinculo(getString(R.string.ID3018_COMODINES_SUPER_CLUB_LINK_LOCALES)));
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        this.q = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        this.p.setOnClickListener(this);
        this.q.setOnClickListener(this);
    }

    public String getSelectedOption() {
        return getString(R.string.IDXX_PRIVATEMENU_BTN_COMODINESSUPERCLUB);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureOptionsShare();
        configureLayout();
        this.t = new CAccessibility(getApplicationContext());
        this.s = new CouponReceiptSuperClubPresenter(this.mBus, this.mDataManager);
        this.s.attachView(this);
        this.s.showComodinReceipt();
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
        Log.w("LMB", "ComodinReceipt.onBackPressed()");
        setResult(-1, new Intent());
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private String a(ArrayList<LeyendaSuperClubBean> arrayList) {
        String str = "";
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            str = String.format("%s<p>%s</p>", new Object[]{str, ((LeyendaSuperClubBean) it.next()).descripcion});
        }
        return str;
    }

    public void showComodinReceipt() {
        showComodinReceipt(this.mReceiptTitle, this.mReceiptChangeDate, this.mReceiptPoints, this.mReceiptNumber, this.mReceiptLeyends);
    }

    public void showComodinReceipt(String str, String str2, String str3, String str4, ArrayList<LeyendaSuperClubBean> arrayList) {
        this.txtDescription.setText(Html.fromHtml(str));
        this.txtChangeDate.setText(UtilDate.getDateFormat(str2, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2));
        this.txtPoints.setText(Utils.getDecimalGroupingFormat(str3));
        this.txtReceiptNumber.setText(str4);
        try {
            this.txtChangeDate.setContentDescription(this.t.applyFilterDateTime(str2));
            this.txtReceiptNumber.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtReceiptNumber.getText().toString()));
        } catch (Exception unused) {
        }
        String a = a(arrayList);
        if (!TextUtils.isEmpty(a)) {
            this.txtLegal.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(a)));
        }
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.F20_03_BTN_BACK) {
            onBackPressed();
        } else if (id2 == R.id.toggle) {
            switchDrawer();
        }
    }
}
