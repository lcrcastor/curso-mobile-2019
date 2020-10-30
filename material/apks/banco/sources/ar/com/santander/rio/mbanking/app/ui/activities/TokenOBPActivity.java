package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.tokenOBP.TokenOBPPresenter;
import ar.com.santander.rio.mbanking.app.module.tokenOBP.TokenOBPView;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CheckVersionEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.utils.UtilDeviceInfo;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.otto.Subscribe;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Set;
import javax.inject.Inject;

public class TokenOBPActivity extends BaseMvpActivity implements OnClickListener, TokenOBPView {
    @InjectView(2131366062)
    Button btn_Aceptar;
    @InjectView(2131366063)
    Button btn_Cancelar;
    @InjectView(2131366064)
    Button btn_CancelarSinToken;
    @Inject
    SessionManager p;
    @Inject
    IDataManager q;
    @Inject
    SettingsManager r;
    @InjectView(2131365602)
    LinearLayout row_SinToken;
    @InjectView(2131365601)
    LinearLayout row_TokenOK;
    @Inject
    SoftTokenManager s;
    /* access modifiers changed from: private */
    public Uri t;
    @InjectView(2131366065)
    TextView tv_Mensaje;
    /* access modifiers changed from: private */
    public CheckVersionManager u;
    /* access modifiers changed from: private */
    public TokenOBPPresenter v;
    private String w;
    private String x;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public void onClick(View view) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_token_obp);
        ButterKnife.inject((Activity) this);
        this.q.logOutSession();
        initialize();
        configureActionBar();
    }

    public void initialize() {
        this.t = Uri.parse(getIntent().getStringExtra("URI"));
        this.v = new TokenOBPPresenter(this.mBus, this.q);
        if (!b() || !this.s.isSoftTokenAvailable().booleanValue()) {
            goToCaraTriste();
        } else {
            goToTokenOBPView();
        }
        this.u = new CheckVersionManager(this.p, this);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.ONLY_LOGO);
        this.mActionBar = getSupportActionBar().getCustomView();
    }

    public void attachView() {
        if (!this.v.isViewAttached()) {
            this.v.attachView(this);
        }
    }

    public void detachView() {
        if (this.v.isViewAttached()) {
            this.v.detachView();
        }
    }

    private boolean b() {
        Set<String> queryParameterNames = this.t.getQueryParameterNames();
        if (queryParameterNames.size() != 3) {
            return false;
        }
        boolean z = false;
        for (String str : queryParameterNames) {
            if (!str.equalsIgnoreCase("mensaje") && !str.equalsIgnoreCase("nup") && !str.equalsIgnoreCase(SettingsJsonConstants.ICON_HASH_KEY)) {
                return false;
            }
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public String c() {
        this.s = new SoftTokenManager(this);
        this.w = "1";
        if (this.s.isSoftTokenAvailable().booleanValue()) {
            this.w = "0";
        }
        return this.w;
    }

    /* access modifiers changed from: private */
    public String d() {
        this.x = "";
        if (this.s.isSoftTokenAvailable().booleanValue()) {
            this.x = this.s.getToken();
            if (this.s.getLastTotp().equalsIgnoreCase(this.x) || (!this.s.getLastTotp().equalsIgnoreCase(this.x) && this.s.getTokenTimeLeft() < 30000)) {
                showProgressIndicator("token");
                UtilDeviceInfo.getNewToken(this);
            }
        }
        return this.x;
    }

    private void e() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, "¿Desea cancelar la operación?", null, null, "SI", "NO", null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                TokenOBPActivity.this.v.enviarTokenOBP(TokenOBPActivity.this.t.getQueryParameter("nup"), TokenOBPActivity.this.t.getQueryParameter(SettingsJsonConstants.ICON_HASH_KEY), "", TokenOBPActivity.this.c());
            }

            public void onNegativeButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(supportFragmentManager, "Dialog");
    }

    public void onBackPressed() {
        if (!b() || !this.s.isSoftTokenAvailable().booleanValue()) {
            finish();
        } else {
            e();
        }
    }

    @Subscribe
    public void onCheckVersion(final CheckVersionEvent checkVersionEvent) {
        final String result = this.u.getResult(checkVersionEvent);
        if (checkVersionEvent.getResult() == TypeResult.BEAN_ERROR_RES_1) {
            this.u.check(checkVersionEvent, new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }
            });
        } else if (this.u.isUpdateAvailable()) {
            this.u.check(checkVersionEvent, new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(TokenOBPActivity.this.u.getUpdateBean(checkVersionEvent).getUrl()));
                    intent.setFlags(268435456);
                    TokenOBPActivity.this.getApplicationContext().startActivity(intent);
                    TokenOBPActivity.this.finish();
                }

                public void onNegativeButton() {
                    if (result.equals("3")) {
                        TokenOBPActivity.this.finish();
                    } else if (result.equals("2")) {
                        TokenOBPActivity.this.p.setCheckVersionWarningRaised(Boolean.valueOf(true));
                    }
                }
            });
        }
    }

    public void goToCaraTriste() {
        this.row_TokenOK.setVisibility(8);
        this.row_SinToken.setVisibility(0);
        this.btn_CancelarSinToken.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TokenOBPActivity.this.finish();
            }
        });
    }

    public void goToTokenOBPView() {
        this.s = new SoftTokenManager(this);
        this.row_TokenOK.setVisibility(0);
        this.row_SinToken.setVisibility(8);
        this.btn_Aceptar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TokenOBPActivity.this.v.enviarTokenOBP(TokenOBPActivity.this.t.getQueryParameter("nup"), TokenOBPActivity.this.t.getQueryParameter(SettingsJsonConstants.ICON_HASH_KEY), TokenOBPActivity.this.d(), TokenOBPActivity.this.c());
            }
        });
        this.btn_Cancelar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TokenOBPActivity.this.v.enviarTokenOBP(TokenOBPActivity.this.t.getQueryParameter("nup"), TokenOBPActivity.this.t.getQueryParameter(SettingsJsonConstants.ICON_HASH_KEY), "", TokenOBPActivity.this.c());
            }
        });
    }

    public void cerrarApp() {
        finishAffinity();
    }
}
