package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.module.superClub.SuperClubCatalogoPresenter;
import ar.com.santander.rio.mbanking.app.module.superClub.SuperClubCatalogoView;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.webviews.SuperClubDownloadListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSCBodyResponseBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;

public class SuperClubFragment extends BaseMvpFragment implements SuperClubCatalogoView {
    @Inject
    SessionManager a;
    @Inject
    AnalyticsManager b;
    private SuperClubCatalogoPresenter c;
    @InjectView(2131366423)
    WebView wbv_catalogo;

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public void onCreate(Bundle bundle) {
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        this.mRootView = layoutInflater.inflate(R.layout.layout_super_club_catalogo, viewGroup, false);
        ButterKnife.inject((Object) this, this.mRootView);
        initialize();
        return this.mRootView;
    }

    public void initialize() {
        this.c = new SuperClubCatalogoPresenter(this.mBus, this.mDataManager, getActivity(), this.a);
        attachView();
        configureActionBar();
        this.c.getFirmaSC();
    }

    public void setCatalogoView(GetFirmaSCBodyResponseBean getFirmaSCBodyResponseBean) {
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        y();
        dismissProgressIndicator();
        this.wbv_catalogo.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                SuperClubFragment.this.dismissProgressIndicator();
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                SuperClubFragment.this.dismissProgressIndicator();
            }

            public void onReceivedSslError(WebView webView, final SslErrorHandler sslErrorHandler, SslError sslError) {
                SuperClubFragment.this.dismissProgressIndicator();
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(SuperClubFragment.this.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), SuperClubFragment.this.getString(R.string.SSL_ERROR_MESSAGE_SUPERCLUB_SUBASTA), null, null, SuperClubFragment.this.getString(R.string.ID1_ALERT_BTN_ACCEPT), SuperClubFragment.this.getString(R.string.F24_TXT_DIALOG_CANCELAR), null);
                newInstance.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onSimpleActionButton() {
                    }

                    public void onPositiveButton() {
                        sslErrorHandler.proceed();
                    }

                    public void onNegativeButton() {
                        SuperClubFragment.this.onBackPressed();
                    }
                });
                newInstance.show(SuperClubFragment.this.getFragmentManager(), "dialog");
            }
        });
        this.wbv_catalogo.getSettings().setJavaScriptEnabled(true);
        this.wbv_catalogo.getSettings().setDomStorageEnabled(true);
        this.wbv_catalogo.setDownloadListener(new SuperClubDownloadListener(this, getContext(), getFragmentManager()));
        this.wbv_catalogo.loadUrl(getFirmaSCBodyResponseBean.getFirmaSuperClub());
    }

    private void y() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        View findViewById = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SuperClubFragment.this.switchDrawer();
                }
            });
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void attachView() {
        this.c.attachView(this);
    }

    public void detachView() {
        if (this.c.isViewAttached()) {
            this.c.detachView();
        }
    }
}
