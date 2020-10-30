package ar.com.santander.rio.mbanking.app.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetallesMovimientoFragment;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransactionResponseBean;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;

public class DetailsTransactionActivity extends BaseActivity implements OnClickListener {
    public static final String INDEX_ACCOUNT = "INDEX_ACCOUNT";
    public static final String INDEX_TRANSACTION_BEAN = "INDEX_TRANSACTION_BEAN";
    public ActionBar mActionBar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_details_transaction);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        e();
        Fragment b = b();
        if (b != null) {
            a(b);
        }
    }

    private Fragment b() {
        if (!getIntent().hasExtra(INDEX_ACCOUNT) || !getIntent().hasExtra(INDEX_TRANSACTION_BEAN)) {
            return null;
        }
        return DetallesMovimientoFragment.getInstance(c(), d());
    }

    private Cuenta c() {
        return (Cuenta) new Gson().fromJson(getIntent().getStringExtra(INDEX_ACCOUNT), Cuenta.class);
    }

    private TransactionResponseBean d() {
        return (TransactionResponseBean) new Gson().fromJson(getIntent().getStringExtra(INDEX_TRANSACTION_BEAN), TransactionResponseBean.class);
    }

    private void e() {
        this.mActionBar = getSupportActionBar();
        this.mActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        this.mActionBar.setDisplayOptions(16);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back_row, null);
        inflate.findViewById(R.id.toggle).setOnClickListener(this);
        inflate.findViewById(R.id.ok).setVisibility(4);
        this.mActionBar.setCustomView(inflate, new LayoutParams(-1, -1));
    }

    private void a(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().addToBackStack(fragment.getClass().getSimpleName()).replace(R.id.vgWrapperDetails, fragment, "TAG_FRAG").commit();
    }

    public void onBackPressed() {
        super.onBackPressed();
        f();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.toggle) {
            f();
        }
    }

    private void f() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
