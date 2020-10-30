package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity;

public class TutorialPatronFragment extends Fragment {
    /* access modifiers changed from: private */
    public SeguroMovilActivity a;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = (SeguroMovilActivity) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_tutorial_patron, viewGroup, false);
        this.a.configActionBarDefault();
        b(inflate);
        return inflate;
    }

    private void b(View view) {
        ((TextView) view.findViewById(R.id.txt_leyenda1)).setText("Antes de continuar, se debera verificar el estado de su pantalla\n\nPor favor, complete el patron marcando cada uno de sus puntos en toda la pantalla");
        ((Button) view.findViewById(R.id.tutorial_btn_continuar)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TutorialPatronFragment.this.a.irContratacionSeguro();
            }
        });
    }
}
