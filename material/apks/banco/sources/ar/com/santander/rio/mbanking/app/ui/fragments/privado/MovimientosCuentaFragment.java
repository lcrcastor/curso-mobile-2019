package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenterImpl;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimpleView;
import ar.com.santander.rio.mbanking.app.module.accounts.transactions.TransactionPresenter;
import ar.com.santander.rio.mbanking.app.module.accounts.transactions.TransactionPresenterImpl;
import ar.com.santander.rio.mbanking.app.module.accounts.transactions.TransactionView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.TransactionsAccountAdapter;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.DismissListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import ar.com.santander.rio.mbanking.components.infinitescroll.IInfiniteScrollListener;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollListView;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollOnScrollListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.HideAccesibilityEvent;
import ar.com.santander.rio.mbanking.services.events.MovCtasEvent;
import ar.com.santander.rio.mbanking.services.events.ResponseEvent;
import ar.com.santander.rio.mbanking.services.events.ViewsEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransactionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VMovCtas;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class MovimientosCuentaFragment extends BaseFragment implements FiltersSimpleView, TransactionView, IDialogListener, IInfiniteScrollListener {
    private final String a = "tag_dialog_filter";
    /* access modifiers changed from: private */
    public TransactionPresenter b;
    /* access modifiers changed from: private */
    public FiltersSimplePresenter c;
    private TransactionsAccountAdapter d;
    private Cuenta e;
    private boolean f = false;
    public FilterTransactionsAccount filterTransactionsAccount;
    private ProgresIndicator g;
    private boolean h = true;
    @Inject
    public AccountAnalytics mAccountAnalytics;
    @Inject
    public IDataManager mDataManager;
    @InjectView(2131364723)
    public InfiniteScrollListView mListTransactions;
    @Inject
    public SessionManager mSessionManager;
    @InjectView(2131366330)
    public TextView vLabelFilterSelected;
    @InjectView(2131366333)
    public TextView vMessageTransactions;
    @InjectView(2131366334)
    public TextView vMessageTransactions7Days;
    @InjectView(2131366366)
    public CustomSpinner vgCustomerSpinner;
    @InjectView(2131366451)
    public ViewGroup vgWrapperMessageTransactions;

    public void onBackPressed() {
    }

    public void onItemSelected(String str) {
    }

    public void onNegativeButton() {
    }

    public void onPositiveButton() {
    }

    public void onScrollCalled(int i, int i2, int i3) {
    }

    public void onSimpleActionButton() {
    }

    public static MovimientosCuentaFragment getInstance(Cuenta cuenta) {
        MovimientosCuentaFragment movimientosCuentaFragment = new MovimientosCuentaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("index_bundle_account", new Gson().toJson((Object) cuenta));
        movimientosCuentaFragment.setArguments(bundle);
        return movimientosCuentaFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        y();
        this.filterTransactionsAccount = new FilterTransactionsAccount();
        this.b = new TransactionPresenterImpl(this);
        this.c = new FiltersSimplePresenterImpl(this, this.e);
        this.d = new TransactionsAccountAdapter(getActivity()) {
            public void onItemClickTransactionAdapter(TransactionResponseBean transactionResponseBean) {
                MovimientosCuentaFragment.this.b.onTransactionClicked(transactionResponseBean);
            }
        };
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.transactions_account, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.b.onLoadTransactions(this.mDataManager, this.e, this.mSessionManager);
        this.g = ProgresIndicator.newInstance(VMovCtas.nameService);
        this.bus.post(new ViewsEvent(this.mListTransactions));
        this.vgCustomerSpinner.setWrapperArrow(R.drawable.ic_arrow_small);
        this.vMessageTransactions7Days.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FilterTransactionsAccount filterTransactionsAccount = new FilterTransactionsAccount(DateTime.now().minusDays(7).toString(Constants.FORMAT_DATE_WS_1), DateTime.now().toString(Constants.FORMAT_DATE_WS_1), MovimientosCuentaFragment.this.getString(R.string.ID98_ACCOUNTS_CHANGEACC_ACT_MOV7));
                MovimientosCuentaFragment.this.c.setFilterSelected(filterTransactionsAccount.label);
                MovimientosCuentaFragment.this.b.applyFilter(filterTransactionsAccount);
            }
        });
        return inflate;
    }

    @Subscribe
    public void onGetMovCtas(MovCtasEvent movCtasEvent) {
        z();
        this.bus.post(new ResponseEvent(getClass().getSimpleName(), ""));
        this.b.onResponseTransactions(movCtasEvent);
    }

    private void y() {
        try {
            this.e = (Cuenta) new Gson().fromJson(getArguments().getString("index_bundle_account"), Cuenta.class);
        } catch (Exception unused) {
            this.e = new Cuenta();
        }
    }

    public void endIsNear() {
        if (!this.f) {
            this.f = true;
            this.b.onNextTransactionsAccounts();
        }
    }

    public Context getActContext() {
        return getActivity();
    }

    public void setTransactionsAccount(List<TransactionResponseBean> list) {
        this.mListTransactions.appendItems((ArrayList) list);
        this.f = false;
    }

    public void setMessageTransactions(Spanned spanned) {
        if (this.vMessageTransactions != null) {
            this.vMessageTransactions.setText(spanned);
        }
    }

    public void setMessageTransactions7DaysVisibility() {
        if (this.vMessageTransactions7Days == null || !this.vLabelFilterSelected.getText().toString().equalsIgnoreCase(getString(R.string.ID96_ACCOUNTS_CHANGEACC_ACT_MOV1)) || !this.h) {
            this.vMessageTransactions7Days.setVisibility(8);
            return;
        }
        this.vMessageTransactions7Days.setVisibility(0);
        this.h = false;
    }

    public void setVisibilityWrapperMessageTransactions(int i) {
        if (this.vgWrapperMessageTransactions != null) {
            this.vgWrapperMessageTransactions.setVisibility(i);
        }
    }

    private void z() {
        if (this.mListTransactions != null && this.mListTransactions.getAdapter() == null) {
            this.mListTransactions.setListener(new InfiniteScrollOnScrollListener(this));
            this.mListTransactions.setAdapter(this.d);
        }
    }

    public void cleanTransactions() {
        if (this.d != null) {
            this.d.cleanItems();
        }
    }

    @OnClick({2131366366})
    public void onClickSelectorFilters(View view) {
        try {
            a(false);
            this.c.onFilterTransactions();
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        this.vgCustomerSpinner.setEnabled(z);
    }

    public void createAndShowSimpleFilter(ArrayList<String> arrayList, String str) {
        this.mAccountAnalytics.registerScreenSimpleSearchTransactions();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.ID89_ACCOUNTS_ACT_SEARCH), null, arrayList, getString(R.string.ID93_ACCOUNTS_CHANGEACC_BTN_CANCEL), null, null, str);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                MovimientosCuentaFragment.this.mAccountAnalytics.registerEventSimpleSearch(str);
                MovimientosCuentaFragment.this.c.onFilterClicked(str);
                MovimientosCuentaFragment.this.a(true);
            }
        });
        newInstance.setDismissListener(new DismissListener() {
            public void onIsbanDismiss() {
                MovimientosCuentaFragment.this.a(true);
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), "tag_dialog_filter");
    }

    public void onCloseSelectionFilter(FilterTransactionsAccount filterTransactionsAccount2) {
        this.b.applyFilter(filterTransactionsAccount2);
    }

    public void openFilterAdvanced(TypeFilterAdvanced typeFilterAdvanced) {
        cleanFilters(typeFilterAdvanced);
        ((SantanderRioMainActivity) getActivity()).addFragment(FiltrosMovimientosCuentaFragment.getInstance(this.filterTransactionsAccount, typeFilterAdvanced, this.e), R.anim.enter_up_anim, R.anim.exit_down_anim);
        this.bus.post(new HideAccesibilityEvent(4));
    }

    public void cleanFilters(TypeFilterAdvanced typeFilterAdvanced) {
        if (this.filterTransactionsAccount != null) {
            if (this.filterTransactionsAccount.typeFilterAdvanced == null || !this.filterTransactionsAccount.typeFilterAdvanced.equals(typeFilterAdvanced)) {
                this.filterTransactionsAccount = new FilterTransactionsAccount();
            }
            this.filterTransactionsAccount.typeFilterAdvanced = typeFilterAdvanced;
        }
    }

    @Subscribe
    public void onCloseAdvancedFilters(FilterTransactionsAccount filterTransactionsAccount2) {
        this.b.applyFilter(filterTransactionsAccount2);
        this.filterTransactionsAccount = filterTransactionsAccount2;
    }

    public void updateLabelFilters(String str) {
        if (this.vLabelFilterSelected != null) {
            this.vLabelFilterSelected.setText(str);
            this.vLabelFilterSelected.setContentDescription(new CFiltersAccessibility(getActContext()).filterMov(str));
        }
    }

    public void showViewLoading() {
        this.g.show(getActivity().getSupportFragmentManager(), "loading");
    }

    public void hideViewLoading() {
        if (this.g != null && this.g.isVisible()) {
            this.g.dismiss();
        }
    }

    public void showAlert(String str, String str2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, getActivity().getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
        newInstance.setDialogListener(this);
        newInstance.show(getFragmentManager(), "Dialog");
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void onDispatchEventsError(WebServiceEvent webServiceEvent) {
        if (getErrorListener() == null) {
            setErrorListener((SantanderRioMainActivity) getActivity());
        }
        if (getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(webServiceEvent, getTAG());
        }
    }
}
