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

public class CotizacionSegAccidenteCondicionesFragment extends BaseFragment {
    private GetCotizacionSeguroAccidenteBodyResponseBean a;
    private IOptionActionBar b;

    public CotizacionSegAccidenteCondicionesFragment() {
    }

    @SuppressLint({"ValidFragment"})
    public CotizacionSegAccidenteCondicionesFragment(GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean) {
        this.a = getCotizacionSeguroAccidenteBodyResponseBean;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_terminos_condiciones_seguro_accidente, viewGroup, false);
        this.b = (IOptionActionBar) getContext();
        loadBarClose();
        b(inflate);
        return inflate;
    }

    private void b(View view) {
        for (LeyendaItem leyendaItem : this.a.getListaLeyendas().getLeyenda()) {
            if (leyendaItem.getIdLeyenda().equals("SEG_TYC_ACC")) {
                TextView textView = (TextView) view.findViewById(R.id.F27_12_txt_leyenda1);
                textView.setText(Html.fromHtml(leyendaItem.getDescripcion()).toString());
                try {
                    textView.setContentDescription(CAccessibility.getInstance(getContext()).applyFilter_pisos(CAccessibility.getInstance(getContext()).applyFilterPagina(CAccessibility.getInstance(getContext()).applyFilter_IGJ(CAccessibility.getInstance(getContext()).applyFilterSiglasTelefono(CAccessibility.getInstance(getContext()).applyFilterTelephoneMask(CAccessibility.getInstance(getContext()).applyFilterCUIT(CAccessibility.getInstance(getContext()).applyGuion(CAccessibility.getInstance(getContext()).applyFilterNumeroGradoBarra(textView.getText().toString())))))))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadBarClose() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.PUSH_CLOSE);
        this.b.inflateActionBarClose();
    }
}
