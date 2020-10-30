package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.ui.Model.Favorito;
import ar.com.santander.rio.mbanking.inject.qualifiers.FavoritoPreference;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.StringPreference;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class StorageMyFavoriteActivity extends BaseActivity {
    @InjectView(2131364642)
    EditText eTxtFavorito;
    @InjectView(2131365116)
    TextView llBuscar;
    @Inject
    @FavoritoPreference
    StringPreference p;
    ImageView q;
    @Inject
    AnalyticsManager r;
    private Favorito s;

    /* access modifiers changed from: 0000 */
    @OnClick({2131364187})
    public void b() {
        if (!TextUtils.isEmpty(this.eTxtFavorito.getText().toString())) {
            this.s.nombre = this.eTxtFavorito.getText().toString();
        } else {
            this.s.nombre = this.s.direccion;
        }
        Gson gson = new Gson();
        List list = (List) gson.fromJson(this.p.get(), new TypeToken<List<Favorito>>() {
        }.getType());
        if (list == null) {
            list = new ArrayList();
        }
        list.add(this.s);
        this.p.set(gson.toJson((Object) list));
        c();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        this.r.trackScreen(getString(R.string.analytics_screen_name_ubicacion_agregar_favorito));
        setContentView((int) R.layout.activity_add_favoritos);
        ButterKnife.inject((Activity) this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayUseLogoEnabled(false);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back, null);
        this.q = (ImageView) inflate.findViewById(R.id.ok);
        this.q.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StorageMyFavoriteActivity.this.b();
            }
        });
        inflate.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StorageMyFavoriteActivity.this.c();
            }
        });
        supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1));
        this.q.setVisibility(0);
        this.s = (Favorito) getIntent().getParcelableExtra("favorito");
        if (this.s == null) {
            c();
        }
        TextView textView = this.llBuscar;
        StringBuilder sb = new StringBuilder();
        sb.append("<b>Dirección: </b>");
        sb.append(this.s.direccion);
        textView.setText(Html.fromHtml(sb.toString()));
    }

    /* access modifiers changed from: private */
    public void c() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public void onBackPressed() {
        c();
        super.onBackPressed();
    }
}
