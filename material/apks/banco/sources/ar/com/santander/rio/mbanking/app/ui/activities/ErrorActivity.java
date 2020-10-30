package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ErrorActivity extends MvpPrivateMenuActivity {
    @InjectView(2131364936)
    RelativeLayout gralLayout;
    @InjectView(2131364790)
    ImageView imgError;
    private ActionBarType p;
    private String q;
    private String r;
    private int s = 0;
    @InjectView(2131366013)
    TextView tvErrorTitle;
    @InjectView(2131366022)
    TextView txtMessage;

    public void attachView() {
    }

    public void clearScreenData() {
    }

    public void detachView() {
    }

    public int getMainLayout() {
        return R.layout.activity_error_drawer_screen;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
    }

    public void initialize() {
        this.p = (ActionBarType) getIntent().getExtras().getSerializable(WSErrorHandlerConstants.INTENT_EXTRA_MENU_TYPE);
        this.q = getIntent().getExtras().getString(WSErrorHandlerConstants.INTENT_EXTRA_TITLE);
        this.r = getIntent().getExtras().getString(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE);
        this.s = getIntent().getExtras().getInt(WSErrorHandlerConstants.INTENT_EXTRA_ERROR_IMG, 0);
    }

    public void configureActionBar() {
        setActionBarType(this.p);
        View customView = getSupportActionBar().getCustomView();
        if (this.p.equals(ActionBarType.MENU)) {
            lockMenu(false);
            ImageView imageView = (ImageView) customView.findViewById(R.id.toggle);
            if (imageView != null) {
                imageView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ErrorActivity.this.onBackPressed();
                    }
                });
            }
        } else if (this.p.equals(ActionBarType.BACK_ONLY)) {
            lockMenu(true);
            ImageView imageView2 = (ImageView) customView.findViewById(R.id.back_imgButton);
            if (imageView2 != null) {
                imageView2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ErrorActivity.this.onBackPressed();
                    }
                });
            }
        }
    }

    public void configureLayout() {
        this.gralLayout.setVisibility(0);
        if (this.s == 0) {
            this.imgError.setImageResource(R.drawable.error_continuacion);
        } else {
            this.imgError.setImageResource(this.s);
        }
        this.imgError.setImportantForAccessibility(2);
        this.txtMessage.setText(Html.fromHtml(this.r));
        if (this.q != null && !this.q.equals("")) {
            this.tvErrorTitle.setText(Html.fromHtml(this.q));
        }
        try {
            this.txtMessage.setContentDescription(new CAccessibility(this).applyFilterGeneral(this.txtMessage.getText().toString()));
            this.tvErrorTitle.setContentDescription(new CAccessibility(this).applyFilterGeneral(this.tvErrorTitle.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        if (this.p.equals(ActionBarType.MENU)) {
            switchDrawer();
        } else if (this.p.equals(ActionBarType.BACK_ONLY)) {
            super.onBackPressed();
            hideKeyboard();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}
