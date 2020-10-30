package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;

public class WebTextViewFragment extends Fragment {
    private String a;

    public static WebTextViewFragment newInstance(String str) {
        WebTextViewFragment webTextViewFragment = new WebTextViewFragment();
        webTextViewFragment.a = str;
        Bundle bundle = new Bundle();
        bundle.putString("HTML_TEXT", str);
        webTextViewFragment.setArguments(bundle);
        webTextViewFragment.setRetainInstance(true);
        return webTextViewFragment;
    }

    public WebTextViewFragment getClone() {
        return newInstance(this.a);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.web_view_fragment, viewGroup, false);
        this.a = getArguments() != null ? getArguments().getString("HTML_TEXT") : "";
        ((TextView) inflate.findViewById(R.id.webTextView)).setText(Html.fromHtml(this.a));
        return inflate;
    }
}
