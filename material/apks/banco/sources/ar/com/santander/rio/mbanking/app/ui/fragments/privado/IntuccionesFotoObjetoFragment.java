package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.ui.activities.FotoObjetoActivity;

public class IntuccionesFotoObjetoFragment extends BaseFragment {
    public static final String INSTRUCTIONS_ARG = "INSTRUCTIONS";
    public static final String SHOW_INSTRUCTIONS_ARG = "SHOW_INSTRUCTIONS_ARG";
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener a;
    private String b = "";
    private String c = "";
    private TextView d;
    private WebView e;
    private Button f;

    public interface OnFragmentInteractionListener {
        void configureActionBar();

        void gotoPhotoFragment();
    }

    public static IntuccionesFotoObjetoFragment newInstance(String str, String str2) {
        IntuccionesFotoObjetoFragment intuccionesFotoObjetoFragment = new IntuccionesFotoObjetoFragment();
        Bundle bundle = new Bundle();
        intuccionesFotoObjetoFragment.setArguments(bundle);
        bundle.putString(INSTRUCTIONS_ARG, str);
        bundle.putString(FotoObjetoActivity.TITLE_INSTRUCTION, str2);
        return intuccionesFotoObjetoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.b = getArguments().getString(INSTRUCTIONS_ARG);
            this.c = getArguments().getString(FotoObjetoActivity.TITLE_INSTRUCTION);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_intucciones_foto_objeto, viewGroup, false);
        this.d = (TextView) inflate.findViewById(R.id.title).findViewById(R.id.functionality_title);
        this.e = (WebView) inflate.findViewById(R.id.instrucciones_web_view);
        this.f = (Button) inflate.findViewById(R.id.btn_selection);
        this.d.setText(this.c);
        this.f.setText(R.string.ID_4066_SEGUROS_LBL_CONTINUAR);
        this.e.loadDataWithBaseURL(null, this.b, null, "utf-8", null);
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                IntuccionesFotoObjetoFragment.this.a.gotoPhotoFragment();
            }
        });
        return inflate;
    }

    public void onResume() {
        super.onResume();
        this.a.configureActionBar();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.a = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.a = null;
    }
}
