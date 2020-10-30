package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class FormularioSugerirSeguroObjetosFragment$$ViewInjector {
    public static void inject(Finder finder, FormularioSugerirSeguroObjetosFragment formularioSugerirSeguroObjetosFragment, Object obj) {
        formularioSugerirSeguroObjetosFragment.editTextTipoDeProducto = (EditText) finder.findRequiredView(obj, R.id.editTextTipoDeProducto, "field 'editTextTipoDeProducto'");
        formularioSugerirSeguroObjetosFragment.editTextMarca = (EditText) finder.findRequiredView(obj, R.id.editTextMarca, "field 'editTextMarca'");
        formularioSugerirSeguroObjetosFragment.editTextModelo = (EditText) finder.findRequiredView(obj, R.id.editTextModelo, "field 'editTextModelo'");
        formularioSugerirSeguroObjetosFragment.editTextValorEstimado = (EditText) finder.findRequiredView(obj, R.id.editTextValorEstimado, "field 'editTextValorEstimado'");
        formularioSugerirSeguroObjetosFragment.tvSugerencia = (TextView) finder.findRequiredView(obj, R.id.tvSugerencia, "field 'tvSugerencia'");
        formularioSugerirSeguroObjetosFragment.buttonEnviarSugerirObjetoAsegurar = (Button) finder.findRequiredView(obj, R.id.buttonEnviarSugerirObjetoAsegurar, "field 'buttonEnviarSugerirObjetoAsegurar'");
    }

    public static void reset(FormularioSugerirSeguroObjetosFragment formularioSugerirSeguroObjetosFragment) {
        formularioSugerirSeguroObjetosFragment.editTextTipoDeProducto = null;
        formularioSugerirSeguroObjetosFragment.editTextMarca = null;
        formularioSugerirSeguroObjetosFragment.editTextModelo = null;
        formularioSugerirSeguroObjetosFragment.editTextValorEstimado = null;
        formularioSugerirSeguroObjetosFragment.tvSugerencia = null;
        formularioSugerirSeguroObjetosFragment.buttonEnviarSugerirObjetoAsegurar = null;
    }
}
