package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.module.softtoken.ISoftTokenState;
import ar.com.santander.rio.mbanking.app.ui.activities.RecargaSubeActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;

public class RegistrarSubeFragment extends BaseFragment {
    @Inject
    SettingsManager a;
    @Inject
    AnalyticsManager b;
    @InjectView(2131365772)
    Button btnRegistrarSube;
    @Inject
    SoftTokenManager c;
    private View d;
    public ActionBar mActionBar;
    @Inject
    public IDataManager mDataManager;
    @InjectView(2131366236)
    TextView tvLegend;
    @InjectView(2131366253)
    TextView tvTitle;
    @InjectView(2131366229)
    TextView tvheader;

    public static RegistrarSubeFragment newInstance() {
        return new RegistrarSubeFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.d = layoutInflater.inflate(R.layout.fragment_recarga_sube_welcome, viewGroup, false);
        ButterKnife.inject((Object) this, this.d);
        initialize();
        z();
        y();
        return this.d;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void initialize() {
        String str;
        String str2;
        if (getArguments() == null || getArguments().get("SUBE_TITULO") == null) {
            str = getContext().getResources().getString(R.string.registra_sube_label);
        } else {
            str = getArguments().getString("SUBE_TITULO");
        }
        if (getArguments() == null || getArguments().get("SUBE_DESCRIPCION") == null) {
            str2 = getContext().getResources().getString(R.string.registra_primera_vez_label);
        } else {
            str2 = getArguments().getString("SUBE_DESCRIPCION");
        }
        this.tvheader.setText(getContext().getResources().getString(R.string.recarga_label_sube));
        this.tvTitle.setText(Html.fromHtml(str));
        this.tvLegend.setText(Html.fromHtml(str2));
        this.btnRegistrarSube = (Button) this.d.findViewById(R.id.sube_next_button);
        this.btnRegistrarSube.setText(getContext().getResources().getString(R.string.registrar_tarjeta_sube));
    }

    private void y() {
        this.btnRegistrarSube.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                new SoftTokenHandler(RegistrarSubeFragment.this.getContext(), RegistrarSubeFragment.this.c, RegistrarSubeFragment.this.b).a(RegistrarSubeFragment.this.getString(R.string.MSG_USER00561_RecargaSube), new ISoftTokenState() {
                    public void isNotAvaliable() {
                    }

                    public void isAvaliable() {
                        Intent intent = new Intent();
                        intent.putExtra("SUBE_FLOW_GO_TO_LOGIN", true);
                        ((RecargaSubeActivity) RegistrarSubeFragment.this.getActivity()).finishOK(intent);
                    }
                });
            }
        });
    }

    private void z() {
        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.PUSH_CLOSE);
        supportActionBar.getCustomView().findViewById(R.id.toggle).setVisibility(8);
        supportActionBar.getCustomView().findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((RecargaSubeActivity) RegistrarSubeFragment.this.getActivity()).goBackToHome();
            }
        });
    }
}
