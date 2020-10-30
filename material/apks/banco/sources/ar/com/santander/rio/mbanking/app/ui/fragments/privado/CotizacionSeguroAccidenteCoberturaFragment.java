package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.IOptionActionBar;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaItem;

public class CotizacionSeguroAccidenteCoberturaFragment extends BaseFragment {
    private GetCotizacionSeguroAccidenteBodyResponseBean a;
    private final CAccessibility b = new CAccessibility(getActivity());
    private IOptionActionBar c;

    public CotizacionSeguroAccidenteCoberturaFragment() {
    }

    @SuppressLint({"ValidFragment"})
    public CotizacionSeguroAccidenteCoberturaFragment(GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean) {
        this.a = getCotizacionSeguroAccidenteBodyResponseBean;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_detalle_cobertura_seguro_accidente, viewGroup, false);
        this.c = (IOptionActionBar) getContext();
        loadBarClose();
        b(inflate);
        return inflate;
    }

    private void b(View view) {
        for (LeyendaItem leyendaItem : this.a.getListaLeyendas().getLeyenda()) {
            if (leyendaItem.getIdLeyenda().equals("SEG_INI_DET_ACC")) {
                TextView textView = (TextView) view.findViewById(R.id.leyenda_cobertura_1);
                textView.setText(Html.fromHtml(leyendaItem.getDescripcion()).toString());
                textView.setVisibility(0);
            }
        }
        for (LeyendaItem leyendaItem2 : this.a.getListaLeyendas().getLeyenda()) {
            if (leyendaItem2.getIdLeyenda().equals("SEG_DET_ACC")) {
                ((TextView) view.findViewById(R.id.leyenda_cobertura_2)).setText(Html.fromHtml(leyendaItem2.getDescripcion()).toString());
            }
        }
    }

    public void loadBarClose() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.PUSH_CLOSE);
        this.c.inflateActionBarClose();
    }
}
