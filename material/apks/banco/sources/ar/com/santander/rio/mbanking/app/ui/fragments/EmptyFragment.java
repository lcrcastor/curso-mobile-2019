package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;

public class EmptyFragment extends BaseFragment {
    public static final String IMAGE_ERROR = "IMAGE_ERROR";
    public static final String MESSAGE_ERROR = "MESSAGE_ERROR";
    public static final String TITLE_ERROR = "TITLE_ERROR";
    public static final String TITLE_TENENCIA = "TENENCIA";
    private String a;
    private String b;
    private int c;

    public void onBackPressed() {
    }

    public static EmptyFragment newInstance(String str, String str2, int i) {
        EmptyFragment emptyFragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_ERROR, str);
        bundle.putString(MESSAGE_ERROR, str2);
        bundle.putInt(IMAGE_ERROR, i);
        emptyFragment.setArguments(bundle);
        return emptyFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.a = getArguments().getString(TITLE_ERROR);
            this.b = getArguments().getString(MESSAGE_ERROR);
            this.c = getArguments().getInt(IMAGE_ERROR);
        }
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.empty_fragment, viewGroup, false);
        inflate.findViewById(R.id.layout_ko_result).setVisibility(0);
        TextView textView = (TextView) inflate.findViewById(R.id.textViewTituloDelError);
        TextView textView2 = (TextView) inflate.findViewById(R.id.text_ko_result);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.imageView3);
        textView.setText(this.a);
        textView2.setText(this.b);
        imageView.setImageResource(this.c);
        inflate.findViewById(R.id.empty_separator);
        TextView textView3 = (TextView) inflate.findViewById(R.id.we_sorry);
        if (this.a.equals(TITLE_TENENCIA)) {
            textView3.setVisibility(0);
            textView.setText("Créditos");
        } else if (!this.a.equals(getActivity().getString(R.string.ID_4692_WOMEN_WOMEN_LBL))) {
            textView3.setVisibility(8);
            textView.setText(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE);
        }
        return inflate;
    }
}
