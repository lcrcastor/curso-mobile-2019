package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class TerminosCondicionesActivity extends BaseActivity {
    public static final String DESC_TERMINO_TAG = "DESC_TERMINO_TAG";
    public static final String TITULO_TERMINO_TAG = "TITULO_TERMINO_TAG";
    private String p;
    private String q;
    @InjectView(2131366225)
    TextView tvDescripcionTermino;
    @InjectView(2131364716)
    TextView tvTitleView;
    @InjectView(2131366257)
    TextView tvTituloTermino;

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_terminos_condiciones);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
    }

    public void initialize() {
        this.p = getIntent().getStringExtra(TITULO_TERMINO_TAG);
        this.q = getIntent().getStringExtra(DESC_TERMINO_TAG);
        this.tvTitleView.setText(R.string.terminos_condiciones_view_title);
        this.tvTituloTermino.setText(Html.fromHtml(this.p));
        this.tvTituloTermino.setVisibility(8);
        this.tvDescripcionTermino.setText(Html.fromHtml(this.q));
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.CONFIRMAR);
        enableBackButton();
    }

    public void enableBackButton() {
        ImageView imageView = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.confirm_imgButton);
        if (imageView != null) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TerminosCondicionesActivity.this.finish();
                    TerminosCondicionesActivity.this.onBackPressed();
                }
            });
        }
    }
}
