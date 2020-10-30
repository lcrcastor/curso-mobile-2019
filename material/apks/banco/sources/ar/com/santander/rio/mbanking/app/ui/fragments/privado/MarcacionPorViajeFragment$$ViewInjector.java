package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife.Finder;

public class MarcacionPorViajeFragment$$ViewInjector {
    public static void inject(Finder finder, MarcacionPorViajeFragment marcacionPorViajeFragment, Object obj) {
        marcacionPorViajeFragment.mControlPager = (ViewFlipper) finder.findRequiredView(obj, R.id.marcacion_por_viaje_view_flipper, "field 'mControlPager'");
        marcacionPorViajeFragment.layoutCruzHabilitar = (LinearLayout) finder.findRequiredView(obj, R.id.F26_00_LNL_SIN_VIAJES, "field 'layoutCruzHabilitar'");
        marcacionPorViajeFragment.btnInformarViajeHabilitar = (Button) finder.findRequiredView(obj, R.id.F26_00_BTN_INFORMAR_VIAJE, "field 'btnInformarViajeHabilitar'");
        marcacionPorViajeFragment.txtHabilitarTarjetasHabilitar = (TextView) finder.findRequiredView(obj, R.id.F26_00_LBL_SIN_VIAJES_HABILITAR, "field 'txtHabilitarTarjetasHabilitar'");
        marcacionPorViajeFragment.lnlTenenciaRes4Error = (LinearLayout) finder.findRequiredView(obj, R.id.F26_00_LNL_ERROR_RES4, "field 'lnlTenenciaRes4Error'");
        marcacionPorViajeFragment.txtTenenciaRes4Error = (TextView) finder.findRequiredView(obj, R.id.F26_00_LBL__DATA_ERROR_RES4, "field 'txtTenenciaRes4Error'");
        marcacionPorViajeFragment.lvItems = (ListView) finder.findRequiredView(obj, R.id.F26_00_LST_VIAJES, "field 'lvItems'");
        marcacionPorViajeFragment.lblDataConsultarEditarFechaDesde = (TextView) finder.findRequiredView(obj, R.id.F26_02_LBL_DATA_FECHA_DESDE, "field 'lblDataConsultarEditarFechaDesde'");
        marcacionPorViajeFragment.lblDataConsultarEditarFechaHasta = (TextView) finder.findRequiredView(obj, R.id.F26_02_LBL_DATA_FECHA_HASTA, "field 'lblDataConsultarEditarFechaHasta'");
        marcacionPorViajeFragment.lblDataConsultarEditarCantDestinos = (TextView) finder.findRequiredView(obj, R.id.F26_02_LBL_DATA_DESTINOS, "field 'lblDataConsultarEditarCantDestinos'");
        marcacionPorViajeFragment.lblDataConsultarEditarCantTarjetas = (TextView) finder.findRequiredView(obj, R.id.F26_02_LBL_DATA_TARJETAS, "field 'lblDataConsultarEditarCantTarjetas'");
        marcacionPorViajeFragment.lblDataConsultarEditarEmail = (EditText) finder.findRequiredView(obj, R.id.F26_02_INP_EMAIL, "field 'lblDataConsultarEditarEmail'");
        marcacionPorViajeFragment.txtSearch = (ClearableEditText) finder.findRequiredView(obj, R.id.F26_03_INP_SEARCH, "field 'txtSearch'");
        marcacionPorViajeFragment.lnlSearch = (RelativeLayout) finder.findRequiredView(obj, R.id.F26_03_RLL_SEARCH_PREDICTIVO, "field 'lnlSearch'");
        marcacionPorViajeFragment.barraBusqueda = (RelativeLayout) finder.findRequiredView(obj, R.id.F26_03_RLL_SEARCH_BAR, "field 'barraBusqueda'");
        marcacionPorViajeFragment.lblHabilitarTitulo = (TextView) finder.findRequiredView(obj, R.id.F26_02_LBL_TITLE, "field 'lblHabilitarTitulo'");
        marcacionPorViajeFragment.btnEditarDetalleViajeGuardar = (Button) finder.findRequiredView(obj, R.id.F26_02_BTN_GUARDAR, "field 'btnEditarDetalleViajeGuardar'");
        marcacionPorViajeFragment.rowMail = (RelativeLayout) finder.findRequiredView(obj, R.id.rowMail, "field 'rowMail'");
        marcacionPorViajeFragment.separador = finder.findRequiredView(obj, R.id.separadorLinea, "field 'separador'");
        marcacionPorViajeFragment.lblComprobanteTitulo = (TextView) finder.findRequiredView(obj, R.id.F26_07_LBL_TITLE, "field 'lblComprobanteTitulo'");
        marcacionPorViajeFragment.lblComprobanteMensajeOk = (TextView) finder.findRequiredView(obj, R.id.F26_07_LBL_MENSAJE_OK, "field 'lblComprobanteMensajeOk'");
        marcacionPorViajeFragment.btnComprobanteVolver = (Button) finder.findRequiredView(obj, R.id.F26_07_BTN_VOLVER, "field 'btnComprobanteVolver'");
        marcacionPorViajeFragment.listaBarraBusqueda = (ListView) finder.findRequiredView(obj, R.id.F26_03_LST_SEARCH_PREDICTIVO, "field 'listaBarraBusqueda'");
        marcacionPorViajeFragment.lvPaisesDestino = (ListView) finder.findRequiredView(obj, R.id.F26_03_LST_DESTINOS, "field 'lvPaisesDestino'");
        marcacionPorViajeFragment.btnDestinoGuardar = (Button) finder.findRequiredView(obj, R.id.F26_03_BTN_GUARDAR, "field 'btnDestinoGuardar'");
        marcacionPorViajeFragment.lblFechaDesde = (TextView) finder.findRequiredView(obj, R.id.F26_02_LBL_FECHA_DESDE, "field 'lblFechaDesde'");
        marcacionPorViajeFragment.lblFechaHasta = (TextView) finder.findRequiredView(obj, R.id.F26_02_LBL_FECHA_HASTA, "field 'lblFechaHasta'");
        marcacionPorViajeFragment.imgComprobante = (ImageView) finder.findRequiredView(obj, R.id.F26_07_IMG_OK, "field 'imgComprobante'");
        marcacionPorViajeFragment.txtAgregarPaises = (TextView) finder.findRequiredView(obj, R.id.F26_MARCACION_DETALLE_AGREGAR_DESTINOS, "field 'txtAgregarPaises'");
    }

    public static void reset(MarcacionPorViajeFragment marcacionPorViajeFragment) {
        marcacionPorViajeFragment.mControlPager = null;
        marcacionPorViajeFragment.layoutCruzHabilitar = null;
        marcacionPorViajeFragment.btnInformarViajeHabilitar = null;
        marcacionPorViajeFragment.txtHabilitarTarjetasHabilitar = null;
        marcacionPorViajeFragment.lnlTenenciaRes4Error = null;
        marcacionPorViajeFragment.txtTenenciaRes4Error = null;
        marcacionPorViajeFragment.lvItems = null;
        marcacionPorViajeFragment.lblDataConsultarEditarFechaDesde = null;
        marcacionPorViajeFragment.lblDataConsultarEditarFechaHasta = null;
        marcacionPorViajeFragment.lblDataConsultarEditarCantDestinos = null;
        marcacionPorViajeFragment.lblDataConsultarEditarCantTarjetas = null;
        marcacionPorViajeFragment.lblDataConsultarEditarEmail = null;
        marcacionPorViajeFragment.txtSearch = null;
        marcacionPorViajeFragment.lnlSearch = null;
        marcacionPorViajeFragment.barraBusqueda = null;
        marcacionPorViajeFragment.lblHabilitarTitulo = null;
        marcacionPorViajeFragment.btnEditarDetalleViajeGuardar = null;
        marcacionPorViajeFragment.rowMail = null;
        marcacionPorViajeFragment.separador = null;
        marcacionPorViajeFragment.lblComprobanteTitulo = null;
        marcacionPorViajeFragment.lblComprobanteMensajeOk = null;
        marcacionPorViajeFragment.btnComprobanteVolver = null;
        marcacionPorViajeFragment.listaBarraBusqueda = null;
        marcacionPorViajeFragment.lvPaisesDestino = null;
        marcacionPorViajeFragment.btnDestinoGuardar = null;
        marcacionPorViajeFragment.lblFechaDesde = null;
        marcacionPorViajeFragment.lblFechaHasta = null;
        marcacionPorViajeFragment.imgComprobante = null;
        marcacionPorViajeFragment.txtAgregarPaises = null;
    }
}
