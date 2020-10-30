package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.ui.Model.Favorito;
import ar.com.santander.rio.mbanking.app.ui.adapters.FavoritosAdapter;
import ar.com.santander.rio.mbanking.components.listswipe.SwipeMenu;
import ar.com.santander.rio.mbanking.components.listswipe.SwipeMenuCreator;
import ar.com.santander.rio.mbanking.components.listswipe.SwipeMenuItem;
import ar.com.santander.rio.mbanking.components.listswipe.SwipeMenuListView;
import ar.com.santander.rio.mbanking.components.listswipe.SwipeMenuListView.OnMenuItemClickListener;
import ar.com.santander.rio.mbanking.inject.qualifiers.FavoritoPreference;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.StringPreference;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import ar.com.santander.rio.mbanking.view.ClearableEditText.OnClearListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import javax.inject.Inject;

public class FavoritosActivity extends BaseActivity {
    @InjectView(2131364643)
    ClearableEditText eTxtSearch;
    ImageView p;
    @Inject
    @FavoritoPreference
    StringPreference q;
    @Inject
    AnalyticsManager r;
    /* access modifiers changed from: private */
    public List<Favorito> s;
    /* access modifiers changed from: private */
    public FavoritosAdapter t;

    public void onBackPressed() {
        setResult(0);
        f();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        this.s = (List) new Gson().fromJson(this.q.get(), new TypeToken<List<Favorito>>() {
        }.getType());
        if (this.s == null || this.s.size() == 0) {
            d();
        } else {
            c();
        }
        this.r.trackScreen(getString(R.string.analytics_screen_name_ubicacion_lugares_favoritos));
        b();
    }

    private void b() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayUseLogoEnabled(false);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back_row, null);
        supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1));
        this.p = (ImageView) inflate.findViewById(R.id.ok);
        this.p.setVisibility(4);
        ((ImageView) inflate.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FavoritosActivity.this.onBackPressed();
            }
        });
    }

    private void c() {
        setContentView((int) R.layout.activity_favoritos);
        ButterKnife.inject((Activity) this);
        if (this.s == null || this.s.size() == 0) {
            findViewById(R.id.noBusqueda).setVisibility(0);
        } else {
            findViewById(R.id.noBusqueda).setVisibility(8);
            SwipeMenuListView swipeMenuListView = (SwipeMenuListView) findViewById(R.id.listView);
            swipeMenuListView.setEmptyView(findViewById(R.id.noBusqueda));
            this.t = new FavoritosAdapter(getApplicationContext(), this.s);
            swipeMenuListView.setAdapter((ListAdapter) this.t);
            swipeMenuListView.setMenuCreator(new SwipeMenuCreator() {
                public void create(SwipeMenu swipeMenu) {
                    SwipeMenuItem swipeMenuItem = new SwipeMenuItem(FavoritosActivity.this.getApplicationContext());
                    swipeMenuItem.setBackground((Drawable) new ColorDrawable(FavoritosActivity.this.getResources().getColor(R.color.red_corporative_text)));
                    swipeMenuItem.setWidth(FavoritosActivity.this.c(90));
                    swipeMenuItem.setTitle((int) R.string.ID82_MYLOCATION_BTN_DELETE);
                    swipeMenuItem.setTitleColor(-1);
                    swipeMenuItem.setTitleSize(16);
                    swipeMenu.addMenuItem(swipeMenuItem);
                }
            });
            swipeMenuListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                public boolean onMenuItemClick(int i, SwipeMenu swipeMenu, int i2) {
                    if (i2 == 0) {
                        FavoritosActivity.this.s.remove(i);
                        a();
                        if (FavoritosActivity.this.s == null || FavoritosActivity.this.s.size() == 0) {
                            FavoritosActivity.this.e();
                        }
                        FavoritosActivity.this.t.updateList(FavoritosActivity.this.s);
                    }
                    return false;
                }

                private void a() {
                    FavoritosActivity.this.q.set(new Gson().toJson((Object) FavoritosActivity.this.s));
                }
            });
            swipeMenuListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    Favorito favorito = (Favorito) FavoritosActivity.this.s.get(i);
                    Intent intent = new Intent();
                    intent.putExtra(MiUbicacionActivity.MIUBICACION_LAT, favorito.lat);
                    intent.putExtra(MiUbicacionActivity.MIUBICACION_LON, favorito.lon);
                    FavoritosActivity.this.setResult(-1, intent);
                    FavoritosActivity.this.f();
                }
            });
        }
        if (this.t != null) {
            this.eTxtSearch.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void afterTextChanged(Editable editable) {
                    FavoritosActivity.this.t.filter(FavoritosActivity.this.eTxtSearch.getText().toString());
                }
            });
        }
        this.eTxtSearch.setOnClearListener(new OnClearListener() {
            public void onClear() {
                FavoritosActivity.this.eTxtSearch.setText("");
            }
        });
    }

    private void d() {
        setContentView((int) R.layout.layout_empty_favoritos);
        TextView textView = (TextView) findViewById(R.id.tvContent);
        if (textView != null) {
            textView.setText(Html.fromHtml(getString(R.string.ID549_MYLOCATION_HELP_LBL_NOLOCATION)));
        }
        e();
    }

    /* access modifiers changed from: private */
    public void e() {
        View findViewById = findViewById(R.id.layout_empty);
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public int c(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
    }

    /* access modifiers changed from: private */
    public void f() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
