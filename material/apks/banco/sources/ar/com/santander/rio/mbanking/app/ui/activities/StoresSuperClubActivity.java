package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.components.share.intent.AllIntent;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesAdheridosSuperClub;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ZonaSuperClubBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import javax.inject.Inject;

public class StoresSuperClubActivity extends BaseActivity implements OnClickListener {
    protected View mActionBar;
    protected CategoriaSuperClubBean mCategoryData;
    protected CuponSuperClubBean mCouponData;
    protected CategoriaSuperClubBean mSubcategoryData;
    ImageView p;
    ImageView q;
    @Inject
    AnalyticsManager r;
    /* access modifiers changed from: private */
    public String s;
    @InjectView(2131362708)
    ScrollView scrStores;
    /* access modifiers changed from: private */
    public LocalesAdheridosSuperClub t;
    @InjectView(2131362707)
    TextView txtBrand;
    @InjectView(2131362706)
    TextView txtStores;
    /* access modifiers changed from: private */
    public OptionsToShare u;

    public static String getTitleStoresShare(Context context, String str) {
        String string = context.getString(R.string.ID3030_COMODINES_SUPER_CLUB_LBL_LOCALES);
        if (TextUtils.isEmpty(str)) {
            return string;
        }
        return String.format(context.getString(R.string.ID3026_COMODINES_SUPER_CLUB_ASUNTO_MAIL_LOCALES), new Object[]{str});
    }

    public static String getBodyStoresShare(Context context, String str, LocalesAdheridosSuperClub localesAdheridosSuperClub) {
        String concat = context.getString(R.string.ID3030_COMODINES_SUPER_CLUB_LBL_LOCALES).concat("<br>");
        if (!TextUtils.isEmpty(str) && localesAdheridosSuperClub.zonas.zona.size() > 0) {
            concat = String.format(context.getString(R.string.ID3027_COMODINES_SUPER_CLUB_CUERPO_MAIL_LOCALES_SR), new Object[]{str}).concat("<br>");
        }
        for (ZonaSuperClubBean zonaSuperClubBean : localesAdheridosSuperClub.zonas.zona) {
            String concat2 = concat.concat(zonaSuperClubBean.nombreZona);
            for (LocalSuperClubBean localSuperClubBean : zonaSuperClubBean.locales.local) {
                String valueOf = String.valueOf("");
                if (!TextUtils.isEmpty(localSuperClubBean.shopping)) {
                    if (!TextUtils.isEmpty(valueOf)) {
                        valueOf = valueOf.concat(" - ");
                    }
                    valueOf = valueOf.concat(localSuperClubBean.shopping);
                }
                if (!TextUtils.isEmpty(localSuperClubBean.direccion)) {
                    if (!TextUtils.isEmpty(valueOf)) {
                        valueOf = valueOf.concat(" - ");
                    }
                    valueOf = valueOf.concat(localSuperClubBean.direccion);
                }
                if (!TextUtils.isEmpty(localSuperClubBean.localNumero)) {
                    if (!TextUtils.isEmpty(valueOf)) {
                        valueOf = valueOf.concat(" - Loc. ");
                    }
                    valueOf = valueOf.concat(localSuperClubBean.localNumero);
                }
                if (!TextUtils.isEmpty(localSuperClubBean.localidad)) {
                    if (!TextUtils.isEmpty(valueOf)) {
                        valueOf = valueOf.concat(" - ");
                    }
                    valueOf = valueOf.concat(localSuperClubBean.localidad);
                }
                if (!TextUtils.isEmpty(localSuperClubBean.provincia)) {
                    if (!TextUtils.isEmpty(valueOf)) {
                        valueOf = valueOf.concat(" - ");
                    }
                    valueOf = valueOf.concat(localSuperClubBean.provincia);
                }
                if (!TextUtils.isEmpty(valueOf)) {
                    concat2 = concat2.concat("<br>").concat("&#8226; ").concat(valueOf);
                }
            }
            concat = concat2.concat("<br><br>");
        }
        return concat.concat(context.getString(R.string.ID3028_COMODINES_SUPER_CLUB_FIRMA_MAIL_ENV_ANDROID));
    }

    public void initialize() {
        if (getIntent().hasExtra(SuperClubConstants.EXTRA_STORES_NAME)) {
            this.s = getIntent().getStringExtra(SuperClubConstants.EXTRA_STORES_NAME);
        }
        if (getIntent().hasExtra(SuperClubConstants.EXTRA_STORES)) {
            this.t = (LocalesAdheridosSuperClub) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_STORES);
        }
        if (getIntent().hasExtra(SuperClubConstants.EXTRA_CATEGORY)) {
            this.mCategoryData = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_CATEGORY);
        }
        if (getIntent().hasExtra(SuperClubConstants.EXTRA_SUBCATEGORY)) {
            this.mSubcategoryData = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_SUBCATEGORY);
        }
        if (getIntent().hasExtra(SuperClubConstants.EXTRA_COMODIN)) {
            this.mCouponData = (CuponSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_COMODIN);
        }
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        this.q = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.menu);
        this.p.setOnClickListener(this);
        this.q.setOnClickListener(this);
    }

    public void configureOptionsShare() {
        this.u = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public String getFileName() {
                return "LOCALES_ADHERIDOS_SUPER_CLUB";
            }

            public String getSubjectReceiptToShare() {
                return "Locales Adheridos SuperClub";
            }

            public View getViewToShare() {
                return StoresSuperClubActivity.this.scrStores;
            }

            public void receiveIntentAppShare(Intent intent) {
                Bundle bundle = new Bundle();
                bundle.putString("android.intent.extra.TEXT", Html.fromHtml(StoresSuperClubActivity.getBodyStoresShare(StoresSuperClubActivity.this.getApplicationContext(), StoresSuperClubActivity.this.s, StoresSuperClubActivity.this.t)).toString());
                bundle.putString("android.intent.extra.SUBJECT", Html.fromHtml(StoresSuperClubActivity.getTitleStoresShare(StoresSuperClubActivity.this.getApplicationContext(), StoresSuperClubActivity.this.s)).toString());
                AllIntent allIntent = new AllIntent();
                allIntent.setExtras(bundle);
                StoresSuperClubActivity.this.startActivityForResult(Intent.createChooser(allIntent.getAllIntents(), StoresSuperClubActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 4);
            }
        };
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_stores_super_club);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureOptionsShare();
        this.r.trackScreen(getString(R.string.analytics_screen_superclub_locales_adheridos, new Object[]{this.mSubcategoryData.nombre, this.mCouponData.descripcion}));
        showStores();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private String a(String str) {
        String valueOf = String.valueOf("");
        if (!TextUtils.isEmpty(str)) {
            valueOf = str.concat("<br>");
        }
        return valueOf.concat("Locales adheridos");
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.u.onRequestPermissionsResult(i, strArr, iArr);
    }

    private String a(LocalesAdheridosSuperClub localesAdheridosSuperClub) {
        String valueOf = String.valueOf("");
        for (ZonaSuperClubBean zonaSuperClubBean : localesAdheridosSuperClub.zonas.zona) {
            String concat = valueOf.concat("<p>").concat("<font color=\"#ee332b\">").concat("<b>").concat(zonaSuperClubBean.nombreZona).concat("</b>").concat("</font>");
            for (LocalSuperClubBean localSuperClubBean : zonaSuperClubBean.locales.local) {
                String valueOf2 = String.valueOf("");
                if (!TextUtils.isEmpty(localSuperClubBean.shopping)) {
                    if (!TextUtils.isEmpty(valueOf2)) {
                        valueOf2 = valueOf2.concat(" - ");
                    }
                    valueOf2 = valueOf2.concat(localSuperClubBean.shopping);
                }
                if (!TextUtils.isEmpty(localSuperClubBean.direccion)) {
                    if (!TextUtils.isEmpty(valueOf2)) {
                        valueOf2 = valueOf2.concat(" - ");
                    }
                    valueOf2 = valueOf2.concat(localSuperClubBean.direccion);
                }
                if (!TextUtils.isEmpty(localSuperClubBean.localNumero)) {
                    if (!TextUtils.isEmpty(valueOf2)) {
                        valueOf2 = valueOf2.concat(" - Loc. ");
                    }
                    valueOf2 = valueOf2.concat(localSuperClubBean.localNumero);
                }
                if (!TextUtils.isEmpty(localSuperClubBean.localidad)) {
                    if (!TextUtils.isEmpty(valueOf2)) {
                        valueOf2 = valueOf2.concat(" - ");
                    }
                    valueOf2 = valueOf2.concat(localSuperClubBean.localidad);
                }
                if (!TextUtils.isEmpty(localSuperClubBean.provincia)) {
                    if (!TextUtils.isEmpty(valueOf2)) {
                        valueOf2 = valueOf2.concat(" - ");
                    }
                    valueOf2 = valueOf2.concat(localSuperClubBean.provincia);
                }
                if (!TextUtils.isEmpty(valueOf2)) {
                    concat = concat.concat("<br>").concat("&#8226; ").concat(valueOf2);
                }
            }
            valueOf = concat.concat("</p>");
        }
        return valueOf;
    }

    public void showStores() {
        this.txtBrand.setText(Html.fromHtml(a(this.s)));
        this.txtStores.setText(Html.fromHtml(a(this.t)));
        String apllyFilterStores = CAccessibility.apllyFilterStores(this.txtStores.getText().toString());
        try {
            apllyFilterStores = CAccessibility.getInstance(this).applyFilterDistance(apllyFilterStores);
        } catch (Exception unused) {
        }
        this.txtStores.setContentDescription(apllyFilterStores);
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.back_imgButton) {
            onBackPressed();
        } else if (id2 == R.id.menu) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(getResources().getString(R.string.ID3020_COMODINES_SUPER_CLUB_BTN_COMPARTIR));
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.ID3030_COMODINES_SUPER_CLUB_LBL_LOCALES), null, arrayList, getString(R.string.ID3032_COMODINES_SUPER_CLUB_BTN_CANCELAR), null, null, null, arrayList);
            newInstance.setDialogListener(new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    if (str.equalsIgnoreCase(StoresSuperClubActivity.this.getResources().getString(R.string.ID3020_COMODINES_SUPER_CLUB_BTN_COMPARTIR))) {
                        StoresSuperClubActivity.this.r.trackEvent(StoresSuperClubActivity.this.getString(R.string.analytics_screen_category_comodines_superclub), StoresSuperClubActivity.this.getString(R.string.analytics_screen_action_compartir_locales_adheridos), StoresSuperClubActivity.this.getString(R.string.analytics_screen_label_locales_adheridos));
                        StoresSuperClubActivity.this.u.optionShareSelected();
                    }
                }
            });
            newInstance.setCancelable(true);
            newInstance.show(getSupportFragmentManager(), "mPopupMenu");
            this.r.trackEvent(getString(R.string.analytics_screen_category_comodines_superclub), getString(R.string.analytics_screen_action_menu_locales_adheridos), getString(R.string.analytics_screen_label_locales_adheridos));
            this.r.trackScreen(getString(R.string.analytics_screen_superclub_locales_adheridos_menu, new Object[]{this.mSubcategoryData.nombre, this.mCouponData.descripcion}));
        }
    }
}
