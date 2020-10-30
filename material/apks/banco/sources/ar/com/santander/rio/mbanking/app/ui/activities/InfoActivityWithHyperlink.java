package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Model.Hyperlink;
import ar.com.santander.rio.mbanking.app.ui.adapters.HyperlinkAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.HyperlinkAdapter.OnHyperlinkClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoActivityWithHyperlink extends BaseActivity {
    public static final String HYPERLINKS_ACTIVITIES = "INFO_ACTIVITY_WITH_HYPERLINK_ACTIVITIES";
    public static final String HYPERLINKS_SUBTITLES = "INFO_ACTIVITY_WITH_HYPERLINK_SUBTITLES";
    public static final String HYPERLINKS_TEXTS = "INFO_ACTIVITY_WITH_HYPERLINK_TEXTS";
    public static final String TITLE = "INFO_ACTIVITY_WITH_HYPERLINK_TITLE";
    protected View mActionBar;
    protected HyperlinkAdapter mAdapter;
    protected List<Hyperlink> mHyperlinks = new ArrayList();
    protected Map<Integer, Intent> mHyperlinksActions = new HashMap();
    protected String mTitle;
    @InjectView(2131364830)
    ListView vHyperlinks;
    @InjectView(2131364832)
    TextView vTitle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_info_activity_with_hyperlink);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        ButterKnife.inject((Activity) this);
        setActionBarType(ActionBarType.CONFIRMAR);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) this.mActionBar.findViewById(R.id.confirm_imgButton);
        this.mActionBar.findViewById(R.id.confirm_imgButton).setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_LISTO)));
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                InfoActivityWithHyperlink.this.onBackPressed();
            }
        });
        if (getIntent().hasExtra(TITLE)) {
            this.mTitle = getIntent().getStringExtra(TITLE);
        }
        if (getIntent().hasExtra(HYPERLINKS_SUBTITLES) && getIntent().hasExtra(HYPERLINKS_TEXTS) && getIntent().hasExtra(HYPERLINKS_ACTIVITIES)) {
            ArrayList stringArrayListExtra = getIntent().getStringArrayListExtra(HYPERLINKS_SUBTITLES);
            ArrayList stringArrayListExtra2 = getIntent().getStringArrayListExtra(HYPERLINKS_TEXTS);
            ArrayList parcelableArrayListExtra = getIntent().getParcelableArrayListExtra(HYPERLINKS_ACTIVITIES);
            if (stringArrayListExtra.size() == stringArrayListExtra2.size() && stringArrayListExtra2.size() == parcelableArrayListExtra.size()) {
                for (int i = 0; i < stringArrayListExtra.size(); i++) {
                    this.mHyperlinks.add(new Hyperlink((String) stringArrayListExtra.get(i), (String) stringArrayListExtra2.get(i)));
                    this.mHyperlinksActions.put(Integer.valueOf(i), parcelableArrayListExtra.get(i));
                }
            }
        }
        this.mAdapter = new HyperlinkAdapter(this, this.mHyperlinks, new OnHyperlinkClickListener() {
            public void onHyperlinkClick(Integer num) {
                InfoActivityWithHyperlink.this.startActivity((Intent) InfoActivityWithHyperlink.this.mHyperlinksActions.get(num));
            }
        });
    }

    public void onResume() {
        super.onResume();
        b();
    }

    private void b() {
        if (!TextUtils.isEmpty(this.mTitle)) {
            this.vTitle.setText(this.mTitle);
        }
        this.vHyperlinks.setAdapter(this.mAdapter);
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
