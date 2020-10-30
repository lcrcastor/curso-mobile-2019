package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.ui.adapters.IntervinientesAdapter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.Arrays;
import javax.inject.Inject;

public class TenenciaInversionesIntervinientesActivity extends BaseActivity {
    public static final String TAG_ACCOUNT = "CUENTA";
    public static final String TAG_INTERVINIENTES = "INTERVINIENTES";
    public static final String TAG_TITLE = "TITLE";
    @InjectView(2131365611)
    RecyclerView intervinientesRv;
    @Inject
    SessionManager p;
    @Inject
    IDataManager q;
    private String r;
    private String s;
    private IntervinientesAdapter t;
    @InjectView(2131366213)
    TextView tvAccount;
    @InjectView(2131366245)
    TextView tvSubtitle;
    @InjectView(2131366228)
    TextView tvTitle;

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_tenencia_inversiones_intervinientes);
        ButterKnife.inject((Activity) this);
        this.r = getIntent().getStringExtra(TAG_INTERVINIENTES);
        this.s = getIntent().getStringExtra("CUENTA");
        this.tvTitle.setText(getIntent().getStringExtra("TITLE"));
        this.tvSubtitle.setText(getString(R.string.ID_4941_INVERSIONES_LBL_INTERVINIENTES_DE_LA_CUENTA));
        this.tvAccount.setText(Html.fromHtml(this.s));
        configureActionBar();
        b();
    }

    private void b() {
        this.t = new IntervinientesAdapter();
        this.t.addItems(Arrays.asList(this.r.split("/ |/")));
        this.intervinientesRv.setLayoutManager(new LinearLayoutManager(getBaseContext(), 1, false));
        this.intervinientesRv.setAdapter(this.t);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        enableBackButton();
    }

    public void enableBackButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TenenciaInversionesIntervinientesActivity.this.finish();
                    TenenciaInversionesIntervinientesActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }
}
