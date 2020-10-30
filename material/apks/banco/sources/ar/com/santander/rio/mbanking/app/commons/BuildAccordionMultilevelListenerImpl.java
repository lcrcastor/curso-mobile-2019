package ar.com.santander.rio.mbanking.app.commons;

import android.support.v4.app.FragmentManager;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import com.jmperezra.accordion_multilevel.listeners.BuildAccordionMultilevelListener;

public class BuildAccordionMultilevelListenerImpl implements BuildAccordionMultilevelListener {
    private ProgresIndicator a;
    private FragmentManager b;

    public BuildAccordionMultilevelListenerImpl(FragmentManager fragmentManager) {
        this.b = fragmentManager;
    }

    public void onShowDialogBuilding() {
        if (this.a == null || !this.a.isVisible()) {
            this.a = ProgresIndicator.newInstance("");
            this.a.show(this.b, "");
        }
    }

    public void onHideDialogBuilding() {
        if (this.a != null) {
            this.a.dismiss();
        }
    }
}
