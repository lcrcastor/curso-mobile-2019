package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SoftTokenFragment$$ViewInjector {
    public static void inject(Finder finder, SoftTokenFragment softTokenFragment, Object obj) {
        softTokenFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.vgSoftToken, "field 'mControlPager'");
        softTokenFragment.lbl_softtokensubtitulo = (TextView) finder.findRequiredView(obj, R.id.F18_00_LBL_SUBTITULO, "field 'lbl_softtokensubtitulo'");
        softTokenFragment.lbl_softtokentitulo = (TextView) finder.findRequiredView(obj, R.id.F18_00_LBL_TITULO, "field 'lbl_softtokentitulo'");
        softTokenFragment.lbl_subtitulClaveToken = (TextView) finder.findRequiredView(obj, R.id.F18_00_LBL_SUBTITULO_CLAVE_TOKEN, "field 'lbl_subtitulClaveToken'");
        softTokenFragment.lbl_subtituloCodigoAsociacion = (TextView) finder.findRequiredView(obj, R.id.F18_00_LBL_SUBTITULO_CODIGO_ASOCIACION, "field 'lbl_subtituloCodigoAsociacion'");
        softTokenFragment.mCodigoView = (EditText) finder.findRequiredView(obj, R.id.F18_00_INP_CODIGO_ASOCIACION, "field 'mCodigoView'");
        softTokenFragment.mClaveView = (EditText) finder.findRequiredView(obj, R.id.F18_00_INP_CLAVE_TOKEN, "field 'mClaveView'");
        softTokenFragment.mTerminosCondicionesView = (ImageView) finder.findRequiredView(obj, R.id.F18_00_IMG_ICONO_TERMINOS_Y_CONDICIONES, "field 'mTerminosCondicionesView'");
        softTokenFragment.mContinuarButton = (Button) finder.findRequiredView(obj, R.id.F18_00_BTN_CONFIRMAR, "field 'mContinuarButton'");
        softTokenFragment.label1_termsCondition = (TextView) finder.findRequiredView(obj, R.id.F18_00_LBL_TEXTO1_TERMINOS_Y_CONDICIONES, "field 'label1_termsCondition'");
        softTokenFragment.label2_termsCondition = (TextView) finder.findRequiredView(obj, R.id.F18_00_LBL_TEXTO2_TERMINOS_Y_CONDICIONES, "field 'label2_termsCondition'");
        softTokenFragment.lbl_recuperar = (TextView) finder.findRequiredView(obj, R.id.F18_00_LBL_RECUPERAR, "field 'lbl_recuperar'");
        softTokenFragment.lbl_stfTitulo = (TextView) finder.findRequiredView(obj, R.id.F18_00F_LBL_TITULO, "field 'lbl_stfTitulo'");
        softTokenFragment.lbl_stfsubtitulo = (TextView) finder.findRequiredView(obj, R.id.F18_00F_LBL_SUBTITULO, "field 'lbl_stfsubtitulo'");
        softTokenFragment.mVolverButton = (Button) finder.findRequiredView(obj, R.id.F18_00F_BTN_VOLVER, "field 'mVolverButton'");
        softTokenFragment.mGetTokenButton = (Button) finder.findRequiredView(obj, R.id.F18_00F_BTN_VER_TOKEN, "field 'mGetTokenButton'");
        softTokenFragment.lbl_sttTitulo = (TextView) finder.findRequiredView(obj, R.id.F18_02_LBL_TITULO, "field 'lbl_sttTitulo'");
        softTokenFragment.lbl_sttSubtitulo = (TextView) finder.findRequiredView(obj, R.id.F18_02_LBL_SUBTITULO, "field 'lbl_sttSubtitulo'");
        softTokenFragment.lbl_sttOpciones = (TextView) finder.findRequiredView(obj, R.id.F18_02_LBL_OPCIONES, "field 'lbl_sttOpciones'");
        softTokenFragment.lbl_sttTokenBloqueado = (TextView) finder.findRequiredView(obj, R.id.F18_02_LBL_TOKEN_BLOQUEADO, "field 'lbl_sttTokenBloqueado'");
        softTokenFragment.countDownText = (TextView) finder.findRequiredView(obj, R.id.F18_02_LBL_CUENTA_REGRESIVA, "field 'countDownText'");
        softTokenFragment.countDownProgressBar = (ProgressBar) finder.findRequiredView(obj, R.id.F18_02_PGB_CUENTA_REGRESIVA, "field 'countDownProgressBar'");
        softTokenFragment.mSincronizarHorario = (TextView) finder.findRequiredView(obj, R.id.F18_02_LBL_SINCRONIZAR_HORARIO, "field 'mSincronizarHorario'");
        softTokenFragment.mSubtituloClaveToken = (TextView) finder.findRequiredView(obj, R.id.F18_02_LBL_SUBTITULO_CLAVE_TOKEN, "field 'mSubtituloClaveToken'");
        softTokenFragment.mNuevoToken = (TextView) finder.findRequiredView(obj, R.id.F18_02_LBL_NUEVO_TOKEN, "field 'mNuevoToken'");
    }

    public static void reset(SoftTokenFragment softTokenFragment) {
        softTokenFragment.mControlPager = null;
        softTokenFragment.lbl_softtokensubtitulo = null;
        softTokenFragment.lbl_softtokentitulo = null;
        softTokenFragment.lbl_subtitulClaveToken = null;
        softTokenFragment.lbl_subtituloCodigoAsociacion = null;
        softTokenFragment.mCodigoView = null;
        softTokenFragment.mClaveView = null;
        softTokenFragment.mTerminosCondicionesView = null;
        softTokenFragment.mContinuarButton = null;
        softTokenFragment.label1_termsCondition = null;
        softTokenFragment.label2_termsCondition = null;
        softTokenFragment.lbl_recuperar = null;
        softTokenFragment.lbl_stfTitulo = null;
        softTokenFragment.lbl_stfsubtitulo = null;
        softTokenFragment.mVolverButton = null;
        softTokenFragment.mGetTokenButton = null;
        softTokenFragment.lbl_sttTitulo = null;
        softTokenFragment.lbl_sttSubtitulo = null;
        softTokenFragment.lbl_sttOpciones = null;
        softTokenFragment.lbl_sttTokenBloqueado = null;
        softTokenFragment.countDownText = null;
        softTokenFragment.countDownProgressBar = null;
        softTokenFragment.mSincronizarHorario = null;
        softTokenFragment.mSubtituloClaveToken = null;
        softTokenFragment.mNuevoToken = null;
    }
}
