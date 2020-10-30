package ar.com.santander.rio.mbanking.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.inject.GraphRetriever;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public abstract class BaseDialogFragment extends DialogFragment implements BackEventListener {
    @Inject
    public Bus bus;

    public void onAttach(Context context) {
        super.onAttach(context);
        GraphRetriever.from(context).inject(this);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFade;
    }

    public void onResume() {
        super.onResume();
        this.bus.register(this);
    }

    public void onPause() {
        super.onPause();
        this.bus.unregister(this);
    }
}
