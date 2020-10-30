package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.CEditTextUtils;
import ar.com.santander.rio.mbanking.app.commons.CustomActionBarListener;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersAdvancedPresenter;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersAdvancedPresenterImpl;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersAdvancedView;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.HideAccesibilityEvent;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import javax.inject.Inject;

public class FiltrosMovimientosCuentaFragment extends BaseFragment implements OnClickListener, OnFocusChangeListener, CustomActionBarListener, FiltersAdvancedView {
    IsbanDialogFragment a;
    IsbanDialogFragment b;
    SantanderRioMainActivity c;
    private final int d = 0;
    private String e = "DialogDate";
    @InjectView(2131364644)
    EditText editAdvancedSearch;
    private TypeFilterAdvanced f;
    public FilterTransactionsAccount filterTransactionsAccount;
    private Cuenta g;
    public ImageView ivLeft;
    public ImageView ivRight;
    @InjectView(2131366384)
    RelativeLayout layoutTypeTransactions;
    @Inject
    public AccountAnalytics mAccountAnalytics;
    public IsbanDatePickerDialogFragment mDatePickerFrom;
    public IsbanDatePickerDialogFragment mDatePickerTo;
    @Inject
    public SessionManager mSessionManager;
    public FiltersAdvancedPresenter presenterFiltersAdvanced;
    @InjectView(2131366103)
    public TextView tvFromDate;
    @InjectView(2131366192)
    public TextView tvTitleFilter;
    @InjectView(2131366206)
    public TextView tvTypeAmount;
    @InjectView(2131366207)
    public TextView tvTypeTransaction;
    @InjectView(2131366315)
    public Button vButtonSearch;
    @InjectView(2131366327)
    public NumericEditText vFromAmount;
    @InjectView(2131366328)
    public TextView vFromDate;
    @InjectView(2131366342)
    public View vSeparator2;
    @InjectView(2131366343)
    public View vSeparator3;
    @InjectView(2131366345)
    public View vSeparator5;
    @InjectView(2131366352)
    public NumericEditText vToAmount;
    @InjectView(2131366353)
    public TextView vToDate;
    @InjectView(2131366361)
    RelativeLayout vgAdvancedSearch;
    @InjectView(2131366362)
    public ViewGroup vgAmount;
    @InjectView(2131366382)
    public ViewGroup vgToDate;
    @InjectView(2131366383)
    public ViewGroup vgTypeAmount;

    public static FiltrosMovimientosCuentaFragment getInstance(FilterTransactionsAccount filterTransactionsAccount2, TypeFilterAdvanced typeFilterAdvanced, Cuenta cuenta) {
        FiltrosMovimientosCuentaFragment filtrosMovimientosCuentaFragment = new FiltrosMovimientosCuentaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("index_bundle_filter", new Gson().toJson((Object) filterTransactionsAccount2));
        bundle.putString("index_bundle_type_filter", typeFilterAdvanced.name());
        bundle.putParcelable("cuenta", cuenta);
        filtrosMovimientosCuentaFragment.setArguments(bundle);
        return filtrosMovimientosCuentaFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        z();
        A();
        this.presenterFiltersAdvanced = new FiltersAdvancedPresenterImpl(this, this.g);
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i = 0;
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_filters_advanced, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.mAccountAnalytics.registerScreenAdvancedSearchTransactions();
        this.vFromDate.setOnClickListener(this);
        this.vToDate.setOnClickListener(this);
        this.vButtonSearch.setOnClickListener(this);
        this.vFromAmount.setOnFocusChangeListener(this);
        this.vToAmount.setOnFocusChangeListener(this);
        this.editAdvancedSearch.setOnFocusChangeListener(this);
        this.presenterFiltersAdvanced.onStartPresenter();
        this.presenterFiltersAdvanced.createFilterForm(this.mSessionManager.getConsDescripciones(), this.f, this.filterTransactionsAccount);
        C();
        D();
        if (this.g.isBancaPrivada()) {
            this.layoutTypeTransactions.setVisibility(8);
            this.vgTypeAmount.setVisibility(8);
            this.vgAdvancedSearch.setVisibility(0);
        } else {
            this.layoutTypeTransactions.setVisibility(0);
            this.vgTypeAmount.setVisibility(0);
            this.vgAdvancedSearch.setVisibility(8);
            ViewGroup viewGroup2 = this.vgTypeAmount;
            if (y()) {
                i = 8;
            }
            viewGroup2.setVisibility(i);
        }
        return inflate;
    }

    private boolean y() {
        return this.f.name().equalsIgnoreCase(TypeFilterAdvanced.GO_TO_DATE.name());
    }

    public Context getContext() {
        return getActivity();
    }

    private void z() {
        Gson gson = new Gson();
        Bundle arguments = getArguments();
        this.g = (Cuenta) arguments.getParcelable("cuenta");
        this.f = b(arguments.getString("index_bundle_type_filter", TypeFilterAdvanced.ADVANCED_SEARCH.name()));
        this.filterTransactionsAccount = (FilterTransactionsAccount) gson.fromJson(arguments.getString("index_bundle_filter"), FilterTransactionsAccount.class);
    }

    private TypeFilterAdvanced b(String str) {
        if (TypeFilterAdvanced.GO_TO_DATE.name().equals(str)) {
            return TypeFilterAdvanced.GO_TO_DATE;
        }
        return TypeFilterAdvanced.ADVANCED_SEARCH;
    }

    public void setSelectorAmount(ArrayList<String> arrayList) {
        this.a = IsbanDialogFragment.newInstance(getString(R.string.ID89_ACCOUNTS_ACT_SEARCH), null, arrayList, getString(R.string.ID93_ACCOUNTS_CHANGEACC_BTN_CANCEL), null, null, null);
        this.a.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                FiltrosMovimientosCuentaFragment.this.mAccountAnalytics.registerEventSimpleSearch(str);
                FiltrosMovimientosCuentaFragment.this.setTextTypeAmount(str);
            }
        });
    }

    public void setSelectorTransactions(ArrayList<String> arrayList) {
        this.b = IsbanDialogFragment.newInstance(getString(R.string.ID89_ACCOUNTS_ACT_SEARCH), null, arrayList, getString(R.string.ID93_ACCOUNTS_CHANGEACC_BTN_CANCEL), null, null, null);
        this.b.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                FiltrosMovimientosCuentaFragment.this.mAccountAnalytics.registerEventSimpleSearch(str);
                FiltrosMovimientosCuentaFragment.this.setTextTypeTransaction(str);
            }
        });
    }

    public TextView getViewFromDate() {
        return this.vFromDate;
    }

    public TextView getViewToDate() {
        return this.vToDate;
    }

    public NumericEditText getViewFromAmount() {
        return this.vFromAmount;
    }

    public EditText getEditAdvancedSearch() {
        return this.editAdvancedSearch;
    }

    public NumericEditText getViewToAmount() {
        return this.vToAmount;
    }

    public Button getViewButtonSearch() {
        return this.vButtonSearch;
    }

    public IsbanDatePickerDialogFragment getDatePickerDialogFrom() {
        this.mDatePickerFrom = IsbanDatePickerDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), UtilDate.getDateFormat(this.vFromDate.getText().toString(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_APP), Constants.FORMAT_DATE_APP);
        this.mDatePickerFrom.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                FiltrosMovimientosCuentaFragment.this.presenterFiltersAdvanced.onDatePickerFromClicked(date);
            }
        });
        return this.mDatePickerFrom;
    }

    public IsbanDatePickerDialogFragment getDatePickerDialogTo() {
        this.mDatePickerTo = IsbanDatePickerDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), UtilDate.getDateFormat(this.vToDate.getText().toString(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_APP), Constants.FORMAT_DATE_APP);
        this.mDatePickerTo.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                FiltrosMovimientosCuentaFragment.this.presenterFiltersAdvanced.onDatePickerToClicked(date);
            }
        });
        return this.mDatePickerTo;
    }

    public void showDatePicker(IsbanDatePickerDialogFragment isbanDatePickerDialogFragment) {
        isbanDatePickerDialogFragment.show(getActivity().getSupportFragmentManager(), this.e);
    }

    public void onClick(View view) {
        try {
            if (view.getId() == getViewFromDate().getId()) {
                showDatePicker(getDatePickerDialogFrom());
            } else if (view.getId() == getViewToDate().getId()) {
                showDatePicker(getDatePickerDialogTo());
            } else if (view.getId() == getViewButtonSearch().getId()) {
                onClickButtonSearch();
            } else if (view.getId() == this.ivLeft.getId()) {
                this.presenterFiltersAdvanced.cancelFilters();
            } else if (view.getId() == this.ivRight.getId()) {
                this.presenterFiltersAdvanced.acceptFilters();
            } else if (view.getId() == this.tvTypeAmount.getId()) {
                showSelectorTypeAmount();
            } else if (view.getId() == this.tvTypeTransaction.getId()) {
                showSelectorTypeTransaction();
            }
        } catch (Exception unused) {
        }
    }

    public void setDateFrom(String str) {
        if (this.vFromDate != null) {
            this.vFromDate.setText(str);
        }
    }

    public void setDateTo(String str) {
        if (this.vToDate != null) {
            this.vToDate.setText(str);
        }
    }

    public void hideViewGroupToDate() {
        if (this.vSeparator2 != null) {
            this.vSeparator2.setVisibility(8);
        }
        if (this.vgToDate != null) {
            this.vgToDate.setVisibility(8);
        }
    }

    public void hideViewGroupAmount() {
        if (this.vSeparator3 != null) {
            this.vSeparator3.setVisibility(8);
        }
        if (this.vgAmount != null) {
            this.vgAmount.setVisibility(8);
        }
    }

    public void hideViewTypeAmount() {
        if (this.vgTypeAmount != null) {
            this.vgTypeAmount.setVisibility(8);
        }
        if (this.vSeparator5 != null) {
            this.vSeparator5.setVisibility(8);
        }
    }

    public void onClickButtonSearch() {
        this.presenterFiltersAdvanced.acceptFilters();
    }

    public void onAcceptFiltersClicked(FilterTransactionsAccount filterTransactionsAccount2) {
        this.mAccountAnalytics.registerEventManagerSearch(this.f, filterTransactionsAccount2);
        this.bus.post(filterTransactionsAccount2);
    }

    public void restoreMainActionBar() {
        B().setActionBarType(ActionBarType.MENU);
        B().lockMenu(false);
        enableMenuButton();
    }

    public void enableMenuButton() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            ImageView imageView = (ImageView) customView.findViewById(R.id.toggle);
            this.c = B();
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        FiltrosMovimientosCuentaFragment.this.c.switchDrawer();
                    } catch (Exception e) {
                        Log.e("@dev", "onClick: menú privado en pantalla de Filtro de Movimiento de cuenta", e);
                    }
                }
            });
        }
    }

    public void setTitleFilter(int i) {
        if (this.tvTitleFilter != null) {
            this.tvTitleFilter.setText(getString(i));
        }
    }

    public void showMessage(String str) {
        IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null).show(getActivity().getSupportFragmentManager(), "Dialog");
    }

    public void onBackPressed() {
        this.presenterFiltersAdvanced.cancelFilters();
    }

    public TypeFilterAdvanced getCurrentTypeFilter() {
        return this.f;
    }

    public void setLabelFromDate(int i) {
        if (this.tvFromDate != null) {
            this.tvFromDate.setText(getString(i));
        }
    }

    public void lockMenu(boolean z) {
        ((SantanderRioMainActivity) getActivity()).lockMenu(z);
    }

    public String getTextTypeAmount() {
        if (this.tvTypeAmount != null) {
            return this.tvTypeAmount.getText().toString();
        }
        return null;
    }

    public void setTextTypeAmount(String str) {
        if (this.tvTypeAmount != null) {
            this.tvTypeAmount.setText(str);
        }
    }

    public String getTextTypeTransaction() {
        if (this.tvTypeTransaction != null) {
            return "TODOS";
        }
        return null;
    }

    public void setTextTypeTransaction(String str) {
        if (this.tvTypeTransaction != null) {
            this.tvTypeTransaction.setText(str);
        }
    }

    public void setFromAmount(String str) {
        if (getViewFromAmount() != null) {
            getViewFromAmount().setText(str);
        }
    }

    public void setEditAdvanceSearch(String str) {
        if (getEditAdvancedSearch() != null) {
            getEditAdvancedSearch().setText(str);
        }
    }

    public void setToAmount(String str) {
        if (getViewToAmount() != null) {
            getViewToAmount().setText(str);
        }
    }

    public void showSelectorTypeAmount() {
        if (this.a != null) {
            FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
            this.a.setSelectedOption(this.tvTypeAmount.getText().toString());
            this.a.show(supportFragmentManager, "TAG_DIALOG_FILTER_AMOUNT");
        }
    }

    public void showSelectorTypeTransaction() {
        if (this.b != null) {
            FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
            this.b.setSelectedOption(this.tvTypeTransaction.getText().toString());
            this.b.show(supportFragmentManager, "TAG_DIALOG_FILTER_TRANSACTION");
        }
    }

    public void onDestroyView() {
        this.bus.post(new HideAccesibilityEvent(0));
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void A() {
        B().loadActionBarModal(0, this);
        B().lockMenu(true);
    }

    public void actionBarLoaded(int i, View view) {
        if (i == 0) {
            ((SantanderRioMainActivity) getContext()).setActionBarType(ActionBarType.BACK_CANCEL);
        }
        b(((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView());
    }

    private void b(View view) {
        if (view != null) {
            this.ivLeft = (ImageView) view.findViewById(R.id.toggle);
            this.ivRight = (ImageView) view.findViewById(R.id.ok);
            this.ivLeft.setOnClickListener(this);
            this.ivRight.setOnClickListener(this);
        }
    }

    private SantanderRioMainActivity B() {
        return (SantanderRioMainActivity) getActivity();
    }

    private void C() {
        if (this.tvTypeAmount != null) {
            this.tvTypeAmount.setOnClickListener(this);
        }
    }

    private void D() {
        if (this.tvTypeTransaction != null) {
            this.tvTypeTransaction.setOnClickListener(this);
        }
    }

    public void onFocusChange(View view, boolean z) {
        if (view instanceof EditText) {
            CEditTextUtils.cleanHintFocus((EditText) view, z);
        }
        if (view instanceof NumericEditText) {
            ((NumericEditText) view).onFocusChangeExtend(view, z);
        }
    }
}
