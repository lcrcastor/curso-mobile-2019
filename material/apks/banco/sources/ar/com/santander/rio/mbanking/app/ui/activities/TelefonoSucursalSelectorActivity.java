package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class TelefonoSucursalSelectorActivity extends BaseActivity implements OnClickListener {
    @Inject
    Bus p;
    @Inject
    AnalyticsManager q;
    private String r = null;
    @InjectView(2131365981)
    TextView textViewNumUtilesCall;
    @InjectView(2131365983)
    TextView textViewNumUtilesCopy;
    @InjectView(2131365984)
    TextView textViewNumUtilesNumber;
    @InjectView(2131365985)
    TextView textViewNumUtilesSave;

    /* access modifiers changed from: 0000 */
    @OnClick({2131365982})
    public void b() {
        finish();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.p.register(this);
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.p.unregister(this);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.simpleselector);
        ButterKnife.inject((Activity) this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.r = extras.getString("telefono");
        }
        if (this.r == null) {
            b();
        }
        this.textViewNumUtilesSave.setOnClickListener(this);
        this.textViewNumUtilesCall.setOnClickListener(this);
        this.textViewNumUtilesCopy.setOnClickListener(this);
        this.textViewNumUtilesNumber.setText(this.r);
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.textViewNumUtilesCall) {
            this.q.trackEvent(getString(R.string.analytics_category_sucursales), getString(R.string.analytics_action_sucursales_ficha), getString(R.string.analytics_label_sucursales_llamar));
            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(this.r);
            String sb2 = sb.toString();
            Intent intent = new Intent("android.intent.action.DIAL");
            intent.setData(Uri.parse(sb2));
            intent.setFlags(268435456);
            getApplicationContext().startActivity(intent);
        } else if (id2 == R.id.textViewNumUtilesCopy) {
            this.q.trackEvent(getString(R.string.analytics_category_sucursales), getString(R.string.analytics_action_sucursales_ficha), getString(R.string.analytics_label_sucursales_copiar));
            ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(getString(R.string.ID117_ACCOUNTS_AVDSEARCH_BTN_COPY), this.r));
            Toast.makeText(this, getString(R.string.ID117_ACCOUNTS_AVDSEARCH_BTN_COPY_AUX), 0).show();
            finish();
        } else if (id2 == R.id.textViewNumUtilesSave) {
            this.q.trackEvent(getString(R.string.analytics_category_sucursales), getString(R.string.analytics_action_sucursales_ficha), getString(R.string.analytics_label_sucursales_guardar));
            Intent intent2 = new Intent("android.intent.action.INSERT");
            intent2.setType("vnd.android.cursor.dir/raw_contact");
            intent2.putExtra("phone", this.r);
            startActivity(intent2);
        }
    }
}
