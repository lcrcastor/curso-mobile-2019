package ar.com.santander.rio.mbanking.app.ui.activities;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.SubcategoriesListSuperClubPresenter;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.SubcategoriesListSuperClubView;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.adapters.ListSuperClubAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.ListSuperClubAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.adapters.SearchListSuperClubAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.SearchListSuperClubAdapter.OnSearchItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesAdheridosSuperClub;
import ar.com.santander.rio.mbanking.utils.KeyboardWatcher;
import ar.com.santander.rio.mbanking.utils.KeyboardWatcher.OnKeyboardToggleListener;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import ar.com.santander.rio.mbanking.view.ClearableEditText.OnClearListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SubcategoriesListSuperClubActivity extends BaseMvpActivity implements SubcategoriesListSuperClubView, OnItemClickListener, OnSearchItemClickListener, OnKeyboardToggleListener {
    @InjectView(2131362666)
    RelativeLayout headersuperclub;
    @InjectView(2131362661)
    LinearLayout homeSuperClub;
    @InjectView(2131362662)
    RelativeLayout lnlSearch;
    @InjectView(2131362663)
    RecyclerView lstData;
    @InjectView(2131362664)
    RecyclerView lstSearch;
    protected List<CategoriaSuperClubBean> mData;
    protected ListSuperClubAdapter mDataAdapter;
    protected String mPoints;
    protected SearchListSuperClubAdapter mSearchAdapter;
    protected List<CategoriaSuperClubBean> mSearchData;
    protected CategoriaSuperClubBean mSelected;
    ImageView p;
    ImageView q;
    @Inject
    AnalyticsManager r;
    private SubcategoriesListSuperClubPresenter s;
    @InjectView(2131362659)
    RelativeLayout screenSuperClub;
    private CategoriaSuperClubBean t;
    @InjectView(2131362652)
    TextView txtPoints;
    @InjectView(2131362645)
    ClearableEditText txtSearch;
    private KeyboardWatcher u;
    /* access modifiers changed from: private */
    public boolean v = true;
    /* access modifiers changed from: private */
    public boolean w = false;
    /* access modifiers changed from: private */
    public int x;
    private long y = 300;

    public void clearScreenData() {
    }

    public void onSearchNoResultsItemClick() {
    }

    public void initialize() {
        this.mPoints = getIntent().getStringExtra(SuperClubConstants.EXTRA_POINTS);
        this.t = (CategoriaSuperClubBean) getIntent().getParcelableExtra(SuperClubConstants.EXTRA_CATEGORY);
        this.mData = getIntent().getParcelableArrayListExtra(SuperClubConstants.EXTRA_SUBCATEGORIES);
        this.txtSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                String obj = SubcategoriesListSuperClubActivity.this.txtSearch.getText().toString();
                if (!TextUtils.isEmpty(obj)) {
                    SubcategoriesListSuperClubActivity.this.lnlSearch.setVisibility(0);
                    SubcategoriesListSuperClubActivity.this.mSearchAdapter.getFilter().filter(obj.toLowerCase());
                    return;
                }
                SubcategoriesListSuperClubActivity.this.lnlSearch.setVisibility(8);
            }
        });
        this.txtSearch.setOnClearListener(new OnClearListener() {
            public void onClear() {
                SubcategoriesListSuperClubActivity.this.txtSearch.setText("");
                SubcategoriesListSuperClubActivity.this.lnlSearch.setVisibility(8);
            }
        });
        this.txtSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (SubcategoriesListSuperClubActivity.this.v) {
                    SubcategoriesListSuperClubActivity.this.x = SubcategoriesListSuperClubActivity.this.homeSuperClub.getHeight();
                    SubcategoriesListSuperClubActivity.this.v = false;
                }
                if (!z) {
                    ((InputMethodManager) SubcategoriesListSuperClubActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 2);
                }
            }
        });
        this.lstData.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                SubcategoriesListSuperClubActivity.this.hideKeyboard();
            }
        });
        this.lstSearch.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                SubcategoriesListSuperClubActivity.this.hideKeyboard();
            }
        });
        this.txtSearch.clearFocus();
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_WITH_HELP);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.p = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        this.q = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.img_help);
        this.p.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                SubcategoriesListSuperClubActivity.this.onBackPressed();
            }
        });
        this.q.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                Intent intent = new Intent(SubcategoriesListSuperClubActivity.this, InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT_WITH_ACCESSIBILITY_LOADING);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, SubcategoriesListSuperClubActivity.this.getString(R.string.IDXX_COMODINES_SUPER_CLUB_AYUDA_TITLE));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, SubcategoriesListSuperClubActivity.this.getString(R.string.IDXX_COMODINES_SUPER_CLUB_AYUDA_BODY));
                SubcategoriesListSuperClubActivity.this.r.trackScreen(SubcategoriesListSuperClubActivity.this.getString(R.string.analytics_screen_superclub_ayuda));
                SubcategoriesListSuperClubActivity.this.startActivity(intent);
            }
        });
    }

    public void configureListDataAdapter(List<CategoriaSuperClubBean> list) {
        this.mDataAdapter = ListSuperClubAdapter.getSubCategoryAdapter(getApplicationContext(), this.mData);
        this.mDataAdapter.setOnItemClickListener(this);
        this.lstData.setAdapter(this.mDataAdapter);
    }

    public void configureLayout() {
        this.txtSearch.setHint(String.format(getString(R.string.ID3003_COMODINES_SUPER_CLUB_TBX_BUSCAR_RUBRO), new Object[]{Html.fromHtml(this.t.nombre).toString()}));
        this.txtSearch.imgClearButton.setColorFilter(getResources().getColor(R.color.grey_light), Mode.SRC_IN);
    }

    /* access modifiers changed from: protected */
    public void configureListSearchAdapter(List<CategoriaSuperClubBean> list) {
        this.mSearchAdapter = new SearchListSuperClubAdapter(this, this.mSearchData);
        this.mSearchAdapter.setOnSearchItemClickListener(this);
        this.lstSearch.setAdapter(this.mSearchAdapter);
    }

    /* access modifiers changed from: protected */
    public void initializeList() {
        this.lstData.setHasFixedSize(true);
        this.lstData.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.lstSearch.setHasFixedSize(true);
        this.lstSearch.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.fragment_list_superclub);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
        initializeList();
        this.s = new SubcategoriesListSuperClubPresenter(this.mBus, this.mDataManager, this);
        this.s.attachView(this);
        this.s.showSubcategoriesList();
        this.u = new KeyboardWatcher(this);
        this.u.setListener(this);
        this.r.trackScreen(getString(R.string.analytics_screen_superclub_seleccion_subrubro, new Object[]{this.t.nombre}));
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
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showList(String str) {
        this.txtPoints.setText(Utils.getDecimalGroupingFormat(str));
    }

    public void showList(List<CategoriaSuperClubBean> list) {
        configureListDataAdapter(list);
    }

    public void showMatchesList(List<CategoriaSuperClubBean> list) {
        configureListSearchAdapter(list);
    }

    public void showSubcategoriesList() {
        showList(this.mPoints);
        showList(this.mData);
        ArrayList arrayList = new ArrayList();
        for (CategoriaSuperClubBean add : this.mData) {
            arrayList.add(add);
        }
        this.mSearchData = arrayList;
        showMatchesList(this.mSearchData);
    }

    public void showComodinesList(List<CuponSuperClubBean> list, LocalesAdheridosSuperClub localesAdheridosSuperClub) {
        Intent intent = new Intent(this, SubcategoryCouponsListSuperClubActivity.class);
        intent.putExtra(SuperClubConstants.EXTRA_POINTS, this.mPoints);
        intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, this.t);
        intent.putExtra(SuperClubConstants.EXTRA_SUBCATEGORY, this.mSelected);
        intent.putExtra(SuperClubConstants.EXTRA_COMODINES, (ArrayList) list);
        intent.putExtra(SuperClubConstants.EXTRA_STORES, localesAdheridosSuperClub);
        startActivityForResult(intent, 2);
    }

    public void OnItemClick(View view) {
        this.mSelected = (CategoriaSuperClubBean) this.mData.get(this.lstData.getChildPosition(view));
        StringBuilder sb = new StringBuilder();
        sb.append("OnItemClick(), Position: ");
        sb.append(this.lstData.getChildPosition(view));
        sb.append(", Item: ");
        sb.append(this.mSelected.nombre);
        Log.w("LMB", sb.toString());
        this.s.onSubcategorySelected(this.mSelected);
    }

    public void onSearchItemClick(View view) {
        CategoriaSuperClubBean categoriaSuperClubBean = (CategoriaSuperClubBean) this.mSearchAdapter.mFilteredData.get(this.lstSearch.getChildPosition(view));
        String str = "LMB";
        StringBuilder sb = new StringBuilder();
        sb.append("OnSearchItemClick(), Nombre: ");
        sb.append(categoriaSuperClubBean.nombre);
        sb.append(", Tipo: ");
        sb.append(TextUtils.isEmpty(categoriaSuperClubBean.idPadre) ? "Categoria" : "Subcategoria");
        Log.w(str, sb.toString());
        this.txtSearch.setText("");
        this.mSelected = categoriaSuperClubBean;
        this.s.onSubcategorySelected(this.mSelected);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 2) {
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

    private void b() {
        LinearLayout linearLayout = this.homeSuperClub;
        final int height = this.headersuperclub.getHeight();
        if (!this.w) {
            if (linearLayout.getTop() > 0) {
                linearLayout.setTop(0);
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(linearLayout, "y", new float[]{(float) height, 0.0f});
            ofFloat.setDuration(this.y);
            ofFloat.addListener(new AnimatorListener() {
                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    LayoutParams layoutParams = new LayoutParams(((LayoutParams) SubcategoriesListSuperClubActivity.this.homeSuperClub.getLayoutParams()).width, SubcategoriesListSuperClubActivity.this.x + height);
                    layoutParams.addRule(3, R.id.F20_00_LBL_DATA_POINTS);
                    SubcategoriesListSuperClubActivity.this.homeSuperClub.setLayoutParams(layoutParams);
                    SubcategoriesListSuperClubActivity.this.w = true;
                }

                public void onAnimationEnd(Animator animator) {
                    SubcategoriesListSuperClubActivity.this.headersuperclub.setVisibility(4);
                }
            });
            ofFloat.start();
        }
    }

    private void c() {
        LinearLayout linearLayout = this.homeSuperClub;
        int height = this.headersuperclub.getHeight();
        if (this.w) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(linearLayout, "y", new float[]{0.0f, (float) height});
            ofFloat.setDuration(this.y);
            ofFloat.addListener(new AnimatorListener() {
                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    SubcategoriesListSuperClubActivity.this.headersuperclub.setVisibility(0);
                    LayoutParams layoutParams = new LayoutParams(((LayoutParams) SubcategoriesListSuperClubActivity.this.homeSuperClub.getLayoutParams()).width, SubcategoriesListSuperClubActivity.this.x);
                    layoutParams.addRule(3, R.id.F20_00_LBL_DATA_POINTS);
                    SubcategoriesListSuperClubActivity.this.homeSuperClub.setLayoutParams(layoutParams);
                }

                public void onAnimationEnd(Animator animator) {
                    SubcategoriesListSuperClubActivity.this.w = false;
                }
            });
            ofFloat.start();
        }
    }

    public void onKeyboardShown(int i) {
        b();
    }

    public void onKeyboardClosed() {
        c();
    }
}
