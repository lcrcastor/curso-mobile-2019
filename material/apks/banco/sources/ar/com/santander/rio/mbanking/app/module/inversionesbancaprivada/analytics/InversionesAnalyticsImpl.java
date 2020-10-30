package ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.model.general.TipoCliente;

public class InversionesAnalyticsImpl implements InversionesAnalytics {
    private Context a;
    public AnalyticsManager mAnalyticsManager;

    public InversionesAnalyticsImpl(Context context, AnalyticsManager analyticsManager) {
        this.a = context;
        this.mAnalyticsManager = analyticsManager;
    }

    public void registerScreenHome(String str) {
        if (str.equalsIgnoreCase(TipoCliente.BP.getTipoCliente())) {
            this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_home_bp));
        } else if (str.equalsIgnoreCase(TipoCliente.RTL.getTipoCliente())) {
            this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_home_rtl));
        }
    }

    public void registerScreenProfileModerado() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_perfil_moderado));
    }

    public void registerScreenProfileConservador() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_perfil_conservador));
    }

    public void registerScreenProfileAgresivo() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_perfil_agresivo));
    }

    public void registerScreenNonProfile() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_sin_perfil));
    }

    public void registerScreenTenenciaReexpresadaPesos() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_tenencia_pesos));
    }

    public void registerScreenTenenciaReexpresadaDolares() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_tenencia_dolares));
    }

    public void registerEventFondoComunesFueraHorario() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_fuera_horario));
    }

    public void registerEventErrorConsultarFondosComunes() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_error_fondo_comunes));
    }

    public void registerEventErrorConsultarFondosComunesPlazosFijos() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_error_fondo_comunes_plazos_fijos));
    }

    public void registerEventError() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_error));
    }

    public void registerEventSinInversionesEnEstaMoneda() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_una_moneda));
    }

    public void registerEventSinTenenciaInversiones() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_sin_tenencias));
    }

    public void registerEventTitulosValores() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_titulos_valores));
    }

    public void registerEventCuentaUnica() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_cuenta_unica));
    }

    public void registerEventSeleccionMonedaPeso() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_visualizacion_pesos));
    }

    public void registerEventSeleccionMonedaUSD() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_visualizacion_usd));
    }

    public void registerEventSeleccionTeneciaMonedaPeso() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_visualizacion_pesos_unica));
    }

    public void registerEventSeleccionTenenciaMonedaUSD() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_visualizacion_usd_unica));
    }

    public void registerEventImpresionNoTenesInversionesPeso() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_impresion), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_visualizacion_no_peso));
    }

    public void registerEventImpresionNoTenesInversionesUSD() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_impresion), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_visualizacion_no_dolar));
    }

    public void registerEventVisualizacionMenuFondosInversion() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_impresion), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_menu_tenencia_reexpresada));
    }

    public void registerEventCancelarVisualizacionMenuFondosInversion() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_cancelar), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_visualizacion_tenencia_reexpresada));
    }

    public void registerEventFondoInversionEnPeso() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_fondo_inversion_peso));
    }

    public void registerEventFondoInversionEnUSD() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_fondo_inversion_dolar));
    }

    public void registerEventFondoInversionEnPesoCuentaUnica() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_fondo_inversion_peso_cuenta_unica));
    }

    public void registerEventFondoInversionEnUSDCuentaUnica() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_fondo_inversion_dolar_cuenta_unica));
    }

    public void registerEventCambiarCuentaTitulosValores() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_cambiar_cuenta_titulo));
    }

    public void registerEventCambiarCuentaPrivada() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_cambiar_cuenta_privada));
    }

    public void registerEventCambiarCuentaRetail() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_event_label_cambiar_cuenta_retail));
    }

    public void registerEventCambiarCuentaFondosComunesBP() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_fondos_comunes_bp));
    }

    public void registerEventCambiarCuentaFondosComunesTitular() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_fondos_comunes_rtl));
    }

    public void fondosComunesErrorTotal() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_fondos_comunes_error_total));
    }

    public void fondosComunesDetallertl() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_detalle_fondos_comunes_rtl));
    }

    public void TenenciaTitulosValoresbp() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_titulos_valores_bp));
    }

    public void TenenciaTitulosValoresrtl() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_titulos_valores_rtl));
    }

    public void TenenciaTitulosValoresErrortotal() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_error_total_titulos_valores));
    }

    public void TenenciaTitulosValoresErrorParcial() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_error_parcial_titulos_valores));
    }

    public void tenenciaTitulosValoresBonoDetallebp() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_detalle_bono_banca_privada));
    }

    public void tenenciaTitulosValoresBonoDetallertl() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_detalle_bono_rtl));
    }

    public void tenenciaTitulosValoresAccionDetallertl() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_detalle_accion_rtl));
    }

    public void tenenciaTitulosValoresAccionDetallebp() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_detalle_accion_banca_privada));
    }

    public void TenenciaCustodiabp() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_custodia_banca_privada));
    }

    public void TenenciaCustodiartl() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_custodia_rtl));
    }

    public void TenenciaCustodiaErrorParcial() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_custodia_error_parcial));
    }

    public void TenenciaCustodiaErrorTotal() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_custodia_error_total));
    }

    public void TenenciaCustodiaDetallebp() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_custodia_detalle_banca_privada));
    }

    public void TenenciaCustodiaDetallertl() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_custodia_detalle_rtl));
    }

    public void registerEventCambiarCuentaTenenciaPlazoFijoBP() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_plazo_fijo_banca_privada));
    }

    public void registerEventCambiarCuentaTenenciaPlazoFijoRTL() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_plazo_fijo_rtl));
    }

    public void TenenciaPlazoFijoErrorParcial() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_plazo_fijo_error_parcial));
    }

    public void TenenciaPlazoFijoErrorTotal() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_plazo_fijo_error_total));
    }

    public void tenenciaPlazoFijoDetallertl() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_plazo_fijo_detalle_rtl));
    }

    public void tenenciaPlazoFijoDetallebp() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_plazo_fijo_detalle_bp));
    }

    public void fondosComunesDetallebp() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_detalle_fondos_comunes_bp));
    }

    public void TenenciaTituloValoresMonedaPeso() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_cambiar_visualizacion_moneda_peso));
    }

    public void TenenciaTituloValoresMonedausd() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_cambiar_visualizacion_moneda_usd));
    }

    public void TenenciaCustodiaMonedaPeso() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_cambiar_visualizacion_moneda_peso));
    }

    public void TenenciaCustodiaMonedausd() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_click), this.a.getString(R.string.analytics_tenencia_inversiones_cambiar_visualizacion_moneda_usd));
    }

    public void registerEventSuscribirNuevoPlazoFijo() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_action_cta), this.a.getString(R.string.analytics_tenencia_inversiones_plazo_fijo_constituir_plazo_fijo));
    }

    public void tenenciaInversionesErrorParcial() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_error_parcial_inversiones));
    }

    public void tenenciaInversionesErrorTotal() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screen_name_error_total_inversiones));
    }

    public void PerfilInversorMasInfoConcentracionActivos() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_concentracion_activos));
    }

    public void detalleFondoComun(String str) {
        AnalyticsManager analyticsManager = this.mAnalyticsManager;
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_fondos_comunes));
        sb.append(str);
        sb.append(this.a.getString(R.string.analytics_tenencia_inversiones_screean_name_fondos_comunes_detalle));
        analyticsManager.trackScreen(sb.toString());
    }

    public void cambiarclientebp() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_cambiar_cuenta), this.a.getString(R.string.analytics_tenencia_inversiones_cambiar_visualizacion_bp));
    }

    public void cambiarclientertl() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_tenencia_inversiones_category), this.a.getString(R.string.analytics_tenencia_inversiones_cambiar_cuenta), this.a.getString(R.string.analytics_tenencia_inversiones_cambiar_visualizacion_rtl));
    }
}
