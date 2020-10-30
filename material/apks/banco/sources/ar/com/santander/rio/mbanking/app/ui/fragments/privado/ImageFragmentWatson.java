package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;

public class ImageFragmentWatson extends Fragment {
    private String a;
    private String b;
    private OnFragmentInteractionListener c;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static ImageFragmentWatson newInstance(String str, String str2) {
        ImageFragmentWatson imageFragmentWatson = new ImageFragmentWatson();
        Bundle bundle = new Bundle();
        bundle.putString("param1", str);
        bundle.putString("param2", str2);
        imageFragmentWatson.setArguments(bundle);
        return imageFragmentWatson;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.a = getArguments().getString("param1");
            this.b = getArguments().getString("param2");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_image_fragment_watson, viewGroup, false);
    }

    public void onButtonPressed(Uri uri) {
        if (this.c != null) {
            this.c.onFragmentInteraction(uri);
        }
    }

    public void onDetach() {
        super.onDetach();
        this.c = null;
    }
}
