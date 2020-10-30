package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.CategoriesListSuperClubPresenter;
import ar.com.santander.rio.mbanking.app.module.comodinessuperclub.CategoriesListSuperClubView;
import ar.com.santander.rio.mbanking.app.ui.activities.CategoryCouponsListSuperClubActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SubcategoriesListSuperClubActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SubcategoryCouponsListSuperClubActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.ListSuperClubAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.ListSuperClubAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.adapters.SearchListSuperClubAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.SearchListSuperClubAdapter.OnSearchItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.SuperClubConstants;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesAdheridosSuperClub;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.keyboardvisibilityevent.KeyboardVisibilityEvent;
import ar.com.santander.rio.mbanking.utils.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import ar.com.santander.rio.mbanking.view.ClearableEditText.OnClearListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class CategoriesListSuperClubFragment extends BaseMvpFragment implements CategoriesListSuperClubView, OnItemClickListener, OnSearchItemClickListener {
    @Inject
    AnalyticsManager a;
    ImageView b;
    ImageView c;
    protected CategoriesListSuperClubPresenter categoriesPresenter;
    /* access modifiers changed from: private */
    public boolean d = true;
    /* access modifiers changed from: private */
    public boolean e = false;
    /* access modifiers changed from: private */
    public int f;
    private long g = 300;
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
    @InjectView(2131362659)
    RelativeLayout screenSuperClub;
    @InjectView(2131362652)
    TextView txtPoints;
    @InjectView(2131362645)
    ClearableEditText txtSearch;

    public void clearScreenData() {
    }

    public void onSearchNoResultsItemClick() {
    }

    public static CategoriesListSuperClubFragment getInstance() {
        Bundle bundle = new Bundle();
        CategoriesListSuperClubFragment categoriesListSuperClubFragment = new CategoriesListSuperClubFragment();
        categoriesListSuperClubFragment.setArguments(bundle);
        return categoriesListSuperClubFragment;
    }

    public void initialize() {
        this.txtSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                String obj = CategoriesListSuperClubFragment.this.txtSearch.getText().toString();
                if (!TextUtils.isEmpty(obj)) {
                    CategoriesListSuperClubFragment.this.lnlSearch.setVisibility(0);
                    CategoriesListSuperClubFragment.this.mSearchAdapter.getFilter().filter(obj.toLowerCase());
                    RecyclerView recyclerView = CategoriesListSuperClubFragment.this.lstData;
                    RecyclerView recyclerView2 = CategoriesListSuperClubFragment.this.lstData;
                    recyclerView.setImportantForAccessibility(4);
                    return;
                }
                CategoriesListSuperClubFragment.this.lnlSearch.setVisibility(8);
                RecyclerView recyclerView3 = CategoriesListSuperClubFragment.this.lstData;
                RecyclerView recyclerView4 = CategoriesListSuperClubFragment.this.lstData;
                recyclerView3.setImportantForAccessibility(1);
            }
        });
        this.txtSearch.setOnClearListener(new OnClearListener() {
            public void onClear() {
                CategoriesListSuperClubFragment.this.txtSearch.setText("");
                CategoriesListSuperClubFragment.this.lnlSearch.setVisibility(8);
            }
        });
        this.txtSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (CategoriesListSuperClubFragment.this.d) {
                    CategoriesListSuperClubFragment.this.f = CategoriesListSuperClubFragment.this.homeSuperClub.getHeight();
                    CategoriesListSuperClubFragment.this.d = false;
                }
                if (!z) {
                    ((InputMethodManager) CategoriesListSuperClubFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 2);
                }
            }
        });
        this.lstData.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                ((SantanderRioMainActivity) CategoriesListSuperClubFragment.this.getActivity()).hideKeyboard();
            }
        });
        this.lstSearch.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                ((SantanderRioMainActivity) CategoriesListSuperClubFragment.this.getActivity()).hideKeyboard();
            }
        });
        this.txtSearch.clearFocus();
    }

    /* access modifiers changed from: private */
    public void y() {
        LinearLayout linearLayout = this.homeSuperClub;
        final int height = this.headersuperclub.getHeight();
        if (!this.e) {
            if (linearLayout.getTop() > 0) {
                linearLayout.setTop(0);
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(linearLayout, "y", new float[]{(float) height, 0.0f});
            ofFloat.setDuration(this.g);
            ofFloat.addListener(new AnimatorListener() {
                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    LayoutParams layoutParams = new LayoutParams(((LayoutParams) CategoriesListSuperClubFragment.this.homeSuperClub.getLayoutParams()).width, CategoriesListSuperClubFragment.this.f + height);
                    layoutParams.addRule(3, R.id.F20_00_LBL_DATA_POINTS);
                    CategoriesListSuperClubFragment.this.homeSuperClub.setLayoutParams(layoutParams);
                    CategoriesListSuperClubFragment.this.e = true;
                }
            });
            ofFloat.start();
        }
    }

    /* access modifiers changed from: private */
    public void z() {
        LinearLayout linearLayout = this.homeSuperClub;
        int height = this.headersuperclub.getHeight();
        if (this.e) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(linearLayout, "y", new float[]{0.0f, (float) height});
            ofFloat.setDuration(this.g);
            ofFloat.addListener(new AnimatorListener() {
                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    LayoutParams layoutParams = new LayoutParams(((LayoutParams) CategoriesListSuperClubFragment.this.homeSuperClub.getLayoutParams()).width, CategoriesListSuperClubFragment.this.f);
                    layoutParams.addRule(3, R.id.F20_00_LBL_DATA_POINTS);
                    CategoriesListSuperClubFragment.this.homeSuperClub.setLayoutParams(layoutParams);
                }

                public void onAnimationEnd(Animator animator) {
                    CategoriesListSuperClubFragment.this.e = false;
                }
            });
            ofFloat.start();
        }
    }

    /* access modifiers changed from: protected */
    public void configureListSearchAdapter(List<CategoriaSuperClubBean> list) {
        this.mSearchAdapter = new SearchListSuperClubAdapter(getActivity(), this.mSearchData);
        this.mSearchAdapter.setOnSearchItemClickListener(this);
        this.lstSearch.setAdapter(this.mSearchAdapter);
    }

    /* access modifiers changed from: protected */
    public void initializeList() {
        this.lstData.setHasFixedSize(true);
        this.lstData.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.lstSearch.setHasFixedSize(true);
        this.lstSearch.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
    }

    public void configureActionBar() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_HELP);
        this.mActionBar = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
        this.b = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        this.c = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.img_help);
        this.b.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CategoriesListSuperClubFragment.this.switchDrawer();
            }
        });
        this.c.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                Intent intent = new Intent(CategoriesListSuperClubFragment.this.getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT_WITH_ACCESSIBILITY_LOADING);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, CategoriesListSuperClubFragment.this.getString(R.string.IDXX_COMODINES_SUPER_CLUB_AYUDA_TITLE));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, CategoriesListSuperClubFragment.this.getString(R.string.IDXX_COMODINES_SUPER_CLUB_AYUDA_BODY));
                CategoriesListSuperClubFragment.this.a.trackScreen(CategoriesListSuperClubFragment.this.getString(R.string.analytics_screen_superclub_ayuda));
                CategoriesListSuperClubFragment.this.startActivity(intent);
            }
        });
    }

    public void configureListDataAdapter(List<CategoriaSuperClubBean> list) {
        this.mDataAdapter = ListSuperClubAdapter.getCategoryAdapter(getActivity().getApplicationContext(), this.mData);
        this.mDataAdapter.setOnItemClickListener(this);
        this.lstData.setAdapter(this.mDataAdapter);
    }

    public void configureLayout() {
        this.txtSearch.imgClearButton.setColorFilter(getResources().getColor(R.color.grey_light), Mode.SRC_IN);
        this.screenSuperClub.setVisibility(4);
        this.homeSuperClub.setVisibility(4);
        this.txtSearch.setHint(getString(R.string.ID3002_COMODINES_SUPER_CLUB_TBX_BUSCAR));
    }

    public void onCreate(Bundle bundle) {
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        try {
            KeyboardVisibilityEvent.setEventListener(getActivity(), new KeyboardVisibilityEventListener() {
                public void onVisibilityChanged(boolean z) {
                    if (z) {
                        CategoriesListSuperClubFragment.this.y();
                    } else {
                        CategoriesListSuperClubFragment.this.z();
                    }
                }
            });
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mRootView = layoutInflater.inflate(R.layout.fragment_list_superclub, viewGroup, false);
        ButterKnife.inject((Object) this, this.mRootView);
        initialize();
        configureActionBar();
        configureLayout();
        initializeList();
        this.categoriesPresenter = new CategoriesListSuperClubPresenter(this.mBus, this.mDataManager);
        this.categoriesPresenter.attachView(this);
        this.categoriesPresenter.getPuntosYCategorias();
        this.a.trackScreen(getString(R.string.analytics_screen_superclub_home_comodines));
        return this.mRootView;
    }

    public void attachView() {
        if (!this.categoriesPresenter.isViewAttached()) {
            this.categoriesPresenter.attachView(this);
        }
    }

    public void detachView() {
        if (this.categoriesPresenter.isViewAttached()) {
            this.categoriesPresenter.detachView();
        }
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

    public void showSadError(String str) {
        this.homeSuperClub.setVisibility(4);
        this.screenSuperClub.setVisibility(0);
        ((SantanderRioMainActivity) getActivity()).restartActionBar();
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        this.mActionBar = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
        this.b = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.toggle);
        this.b.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CategoriesListSuperClubFragment.this.switchDrawer();
            }
        });
        ((SantanderRioMainActivity) getActivity()).setErrorFragment(str);
    }

    public void showOnBoarding() {
        showOnBoarding(R.layout.onboarding_superclub, R.id.F20_XX_BTN_CLOSE_PAGE2, R.id.F20_XX_FLP_ONBOARDING, SuperClubConstants.PREFERENCE_ONBOARDING);
    }

    public void showCategoriesList(String str) {
        this.mPoints = str;
        showList(this.mPoints);
    }

    public void showCategoriesList(List<CategoriaSuperClubBean> list) {
        this.mData = list;
        showList(this.mData);
        ArrayList arrayList = new ArrayList();
        for (CategoriaSuperClubBean categoriaSuperClubBean : this.mData) {
            arrayList.add(categoriaSuperClubBean);
            for (CategoriaSuperClubBean add : categoriaSuperClubBean.categorias.categoria) {
                arrayList.add(add);
            }
        }
        this.mSearchData = arrayList;
        showMatchesList(this.mSearchData);
        this.homeSuperClub.setVisibility(0);
    }

    public void makeVisibleHomeScreen() {
        this.screenSuperClub.setVisibility(0);
    }

    public void showSubcategoriesList(List<CategoriaSuperClubBean> list) {
        Intent intent = new Intent(getActivity(), SubcategoriesListSuperClubActivity.class);
        intent.putExtra(SuperClubConstants.EXTRA_POINTS, this.mPoints);
        intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, this.mSelected);
        intent.putExtra(SuperClubConstants.EXTRA_SUBCATEGORIES, (ArrayList) list);
        startActivityForResult(intent, 1);
    }

    public void showComodinesList(List<CuponSuperClubBean> list) {
        Intent intent = new Intent(getActivity(), CategoryCouponsListSuperClubActivity.class);
        intent.putExtra(SuperClubConstants.EXTRA_POINTS, this.mPoints);
        intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, this.mSelected);
        intent.putExtra(SuperClubConstants.EXTRA_COMODINES, (ArrayList) list);
        startActivityForResult(intent, 2);
    }

    public void showComodinesList(List<CuponSuperClubBean> list, LocalesAdheridosSuperClub localesAdheridosSuperClub) {
        CategoriaSuperClubBean categoriaSuperClubBean = new CategoriaSuperClubBean();
        for (int i = 0; i < this.mData.size(); i++) {
            if (((CategoriaSuperClubBean) this.mData.get(i)).f254id.equalsIgnoreCase(this.mSelected.idPadre)) {
                categoriaSuperClubBean = (CategoriaSuperClubBean) this.mData.get(i);
            }
        }
        Intent intent = new Intent(getActivity(), SubcategoryCouponsListSuperClubActivity.class);
        intent.putExtra(SuperClubConstants.EXTRA_POINTS, this.mPoints);
        intent.putExtra(SuperClubConstants.EXTRA_CATEGORY, categoriaSuperClubBean);
        intent.putExtra(SuperClubConstants.EXTRA_SUBCATEGORY, this.mSelected);
        intent.putExtra(SuperClubConstants.EXTRA_COMODINES, (ArrayList) list);
        intent.putExtra(SuperClubConstants.EXTRA_STORES, localesAdheridosSuperClub);
        startActivityForResult(intent, 2);
    }

    public Context getContext() {
        return getActivity();
    }

    public void OnItemClick(View view) {
        ((SantanderRioMainActivity) getActivity()).hideKeyboard();
        this.mSelected = (CategoriaSuperClubBean) this.mData.get(this.lstData.getChildPosition(view));
        StringBuilder sb = new StringBuilder();
        sb.append("OnItemClick(), Position: ");
        sb.append(this.lstData.getChildPosition(view));
        sb.append(", Item: ");
        sb.append(this.mSelected.nombre);
        Log.w("LMB", sb.toString());
        this.categoriesPresenter.onCategorySelected(this.mSelected);
    }

    public void onSearchItemClick(View view) {
        ((SantanderRioMainActivity) getActivity()).hideKeyboard();
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
        this.categoriesPresenter.onCategorySelected(this.mSelected);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0045, code lost:
        if (r4.equals(ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants.GO_TO_LOGIN) != false) goto L_0x0067;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r4, int r5, android.content.Intent r6) {
        /*
            r3 = this;
            r0 = 0
            r1 = -1
            if (r5 != r1) goto L_0x001d
            java.lang.String r2 = "PrivateMenuSelectedOptionPosition"
            boolean r2 = r6.hasExtra(r2)
            if (r2 == 0) goto L_0x001d
            android.support.v4.app.FragmentActivity r4 = r3.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            java.lang.String r5 = "PrivateMenuSelectedOptionPosition"
            int r5 = r6.getIntExtra(r5, r0)
            r4.onPrivateMenuOptionSelectedInNestedActivity(r5)
            goto L_0x00bd
        L_0x001d:
            if (r5 != r1) goto L_0x00a4
            java.lang.String r2 = "WS_ERROR_DO_ACTION"
            boolean r2 = r6.hasExtra(r2)
            if (r2 == 0) goto L_0x00a4
            java.lang.String r4 = "WS_ERROR_DO_ACTION"
            java.lang.String r4 = r6.getStringExtra(r4)
            int r5 = r4.hashCode()
            switch(r5) {
                case -1667304550: goto L_0x005c;
                case -1442009346: goto L_0x0052;
                case -1365838438: goto L_0x0048;
                case -171755572: goto L_0x003f;
                case 4216548: goto L_0x0035;
                default: goto L_0x0034;
            }
        L_0x0034:
            goto L_0x0066
        L_0x0035:
            java.lang.String r5 = "GO_TO_HOME_ERROR_CLOCK"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0066
            r0 = 4
            goto L_0x0067
        L_0x003f:
            java.lang.String r5 = "GO_TO_HOME"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0066
            goto L_0x0067
        L_0x0048:
            java.lang.String r5 = "GO_TO_HOME_FUNCIONALIDAD"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0066
            r0 = 1
            goto L_0x0067
        L_0x0052:
            java.lang.String r5 = "GO_TO_CUENTAS"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0066
            r0 = 2
            goto L_0x0067
        L_0x005c:
            java.lang.String r5 = "GO_TO_HOME_ERROR_FRAGMENT"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0066
            r0 = 3
            goto L_0x0067
        L_0x0066:
            r0 = -1
        L_0x0067:
            switch(r0) {
                case 0: goto L_0x009a;
                case 1: goto L_0x00bd;
                case 2: goto L_0x008e;
                case 3: goto L_0x007e;
                case 4: goto L_0x006b;
                default: goto L_0x006a;
            }
        L_0x006a:
            goto L_0x00bd
        L_0x006b:
            android.support.v4.app.FragmentActivity r4 = r3.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            java.lang.String r5 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r5 = r6.getStringExtra(r5)
            r6 = 2131231076(0x7f080164, float:1.8078223E38)
            r4.setErrorFragment(r5, r6)
            goto L_0x00bd
        L_0x007e:
            android.support.v4.app.FragmentActivity r4 = r3.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            java.lang.String r5 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r5 = r6.getStringExtra(r5)
            r4.setErrorFragment(r5)
            goto L_0x00bd
        L_0x008e:
            android.support.v4.app.FragmentActivity r4 = r3.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            java.lang.String r5 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS
            r4.goToOption(r5)
            goto L_0x00bd
        L_0x009a:
            android.support.v4.app.FragmentActivity r4 = r3.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            r4.gotoHome()
            goto L_0x00bd
        L_0x00a4:
            if (r5 != r1) goto L_0x00bd
            switch(r4) {
                case 1: goto L_0x00aa;
                case 2: goto L_0x00aa;
                default: goto L_0x00a9;
            }
        L_0x00a9:
            goto L_0x00bd
        L_0x00aa:
            java.lang.String r4 = "recargarHome"
            boolean r4 = r6.getBooleanExtra(r4, r0)
            if (r4 == 0) goto L_0x00bd
            r3.configureLayout()
            r3.attachView()
            ar.com.santander.rio.mbanking.app.module.comodinessuperclub.CategoriesListSuperClubPresenter r4 = r3.categoriesPresenter
            r4.getPuntosYCategorias()
        L_0x00bd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.CategoriesListSuperClubFragment.onActivityResult(int, int, android.content.Intent):void");
    }
}
