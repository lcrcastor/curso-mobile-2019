package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.seguros.SeleccionarOcupacionAltaSeguroPresenter;
import ar.com.santander.rio.mbanking.app.module.seguros.SeleccionarOcupacionAltaSeguroView;
import ar.com.santander.rio.mbanking.app.ui.adapters.OcupacionSeguroAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.OcupacionSeguroAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OcupacionBean;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SeleccionarOcupacionAltaSeguroActivity extends BaseMvpActivity implements SeleccionarOcupacionAltaSeguroView, OnItemClickListener {
    @InjectView(2131363462)
    ClearableEditText inpSearch;
    @InjectView(2131363466)
    RecyclerView lstOcupaciones;
    GetOcupacionesBodyResponseBean p;
    private OcupacionBean q;
    private List<OcupacionBean> r;
    private List<OcupacionBean> s;
    private SeleccionarOcupacionAltaSeguroPresenter t;
    @InjectView(2131363463)
    TextView txtDescription;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_seleccionar_ocupacion_seguro);
        ButterKnife.inject((Activity) this);
        this.t = new SeleccionarOcupacionAltaSeguroPresenter(this.mBus);
        attachView();
        initialize();
        configureActionBar();
        configureLayout();
    }

    public void initialize() {
        this.r = getIntent().getParcelableArrayListExtra(SegurosConstants.INTENT_EXTRA_OCUPACIONES);
        this.q = (OcupacionBean) getIntent().getParcelableExtra(SegurosConstants.INTENT_EXTRA_OCUPACION);
        initializeList();
        this.inpSearch.imgClearButton.setColorFilter(getResources().getColor(R.color.grey_light), Mode.SRC_IN);
        this.inpSearch.setOnTextWatcher(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                List filtrarOcupaciones = SeleccionarOcupacionAltaSeguroActivity.this.filtrarOcupaciones(editable.toString());
                if (!filtrarOcupaciones.isEmpty()) {
                    SeleccionarOcupacionAltaSeguroActivity.this.txtDescription.setVisibility(8);
                    SeleccionarOcupacionAltaSeguroActivity.this.lstOcupaciones.setVisibility(0);
                    SeleccionarOcupacionAltaSeguroActivity.this.configureListAdapter(filtrarOcupaciones);
                    return;
                }
                TextView textView = SeleccionarOcupacionAltaSeguroActivity.this.txtDescription;
                StringBuilder sb = new StringBuilder();
                sb.append(SeleccionarOcupacionAltaSeguroActivity.this.getString(R.string.F27_12_LBL_FILTRO_SIN_RESULTADO));
                sb.append(" <b>");
                sb.append(editable);
                sb.append("</b>");
                textView.setText(Html.fromHtml(sb.toString()));
                SeleccionarOcupacionAltaSeguroActivity.this.txtDescription.setVisibility(0);
                SeleccionarOcupacionAltaSeguroActivity.this.lstOcupaciones.setVisibility(8);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void configureListAdapter(List<OcupacionBean> list) {
        this.s = list;
        OcupacionSeguroAdapter ocupacionSeguroAdapter = new OcupacionSeguroAdapter(this, list, this.q);
        ocupacionSeguroAdapter.setOnClickListener(this);
        this.lstOcupaciones.setAdapter(ocupacionSeguroAdapter);
    }

    /* access modifiers changed from: protected */
    public void initializeList() {
        this.lstOcupaciones.setHasFixedSize(true);
        this.lstOcupaciones.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        ((ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SeleccionarOcupacionAltaSeguroActivity.this.onBackPressed();
            }
        });
    }

    public void configureLayout() {
        this.t.onCreatePage(this.p);
    }

    public void attachView() {
        if (!this.t.isViewAttached()) {
            this.t.attachView(this);
        }
    }

    public void detachView() {
        if (this.t.isViewAttached()) {
            this.t.detachView();
        }
    }

    public void onBackPressed() {
        hideKeyboard();
        setResult(0);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        hideKeyboard();
    }

    public void clearScreenData() {
        this.inpSearch.setText("");
    }

    /* access modifiers changed from: protected */
    public List<OcupacionBean> filtrarOcupaciones(String str) {
        ArrayList arrayList = new ArrayList();
        String lowerCase = str.toLowerCase(Locale.getDefault());
        if (lowerCase.length() == 0) {
            arrayList.addAll(this.r);
        } else if (this.r != null && !this.r.isEmpty()) {
            for (OcupacionBean ocupacionBean : this.r) {
                if (ocupacionBean != null && ocupacionBean.getDescOcupacion().toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                    arrayList.add(ocupacionBean);
                }
            }
        }
        return arrayList;
    }

    public void onItemClick(View view) {
        OcupacionBean ocupacionBean = (OcupacionBean) this.s.get(this.lstOcupaciones.getChildPosition(view));
        Intent intent = new Intent();
        intent.putExtra(SegurosConstants.INTENT_EXTRA_OCUPACION, ocupacionBean);
        setResult(-1, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        hideKeyboard();
    }

    public void setSeleccionarOcupacionView() {
        configureListAdapter(this.r);
    }
}
