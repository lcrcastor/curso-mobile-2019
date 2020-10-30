package ar.com.santander.rio.mbanking.components;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseDialogFragment;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import javax.inject.Inject;

public class ProgresIndicator extends BaseDialogFragment {
    private static boolean ae = false;
    private static int af;
    @Inject
    IDataManager ad;
    @InjectView(2131364203)
    Button btnVolver;
    @InjectView(2131366286)
    TextView txtMensaje;

    public void onBackPressed() {
    }

    public static ProgresIndicator newInstance(String str) {
        ProgresIndicator progresIndicator = new ProgresIndicator();
        if (!TextUtils.isEmpty(str)) {
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(str)) {
                bundle.putString("tag", str);
            }
            progresIndicator.setArguments(bundle);
        }
        return progresIndicator;
    }

    public static ProgresIndicator newInstance() {
        ProgresIndicator progresIndicator = new ProgresIndicator();
        Bundle bundle = new Bundle();
        bundle.putString("tag", "");
        progresIndicator.setArguments(bundle);
        return progresIndicator;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, 16973839);
        af = Constants.DEFAULT_TIME_OUT_SERVER * 2;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_progress, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setCancelable(false);
        if (getArguments() != null) {
            final String string = getArguments().getString("tag");
            this.btnVolver.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(string)) {
                        ProgresIndicator.this.ad.cancelRequest(string);
                    }
                    ProgresIndicator.this.dismiss();
                }
            });
            return;
        }
        this.btnVolver.setVisibility(8);
        this.txtMensaje.setVisibility(8);
    }

    public void setAutoDismissTimeSeconds(int i) {
        af = i;
    }

    public void setAutoDismiss(boolean z) {
        ae = z;
    }

    public void show(FragmentManager fragmentManager, String str, boolean z) {
        fragmentManager.beginTransaction().add((Fragment) this, str).commitAllowingStateLoss();
        if (z) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ProgresIndicator.this.dismiss();
                }
            }, (long) (af * 1000));
        }
    }

    public void show(FragmentManager fragmentManager, String str) {
        show(fragmentManager, str, ae);
    }
}
