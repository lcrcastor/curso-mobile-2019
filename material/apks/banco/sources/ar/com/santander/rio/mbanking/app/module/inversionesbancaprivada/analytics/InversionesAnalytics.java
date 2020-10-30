package ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics;

public interface InversionesAnalytics {
    void PerfilInversorMasInfoConcentracionActivos();

    void TenenciaCustodiaDetallebp();

    void TenenciaCustodiaDetallertl();

    void TenenciaCustodiaErrorParcial();

    void TenenciaCustodiaErrorTotal();

    void TenenciaCustodiaMonedaPeso();

    void TenenciaCustodiaMonedausd();

    void TenenciaCustodiabp();

    void TenenciaCustodiartl();

    void TenenciaPlazoFijoErrorParcial();

    void TenenciaPlazoFijoErrorTotal();

    void TenenciaTituloValoresMonedaPeso();

    void TenenciaTituloValoresMonedausd();

    void TenenciaTitulosValoresErrorParcial();

    void TenenciaTitulosValoresErrortotal();

    void TenenciaTitulosValoresbp();

    void TenenciaTitulosValoresrtl();

    void cambiarclientebp();

    void cambiarclientertl();

    void detalleFondoComun(String str);

    void fondosComunesDetallebp();

    void fondosComunesDetallertl();

    void fondosComunesErrorTotal();

    void registerEventCambiarCuentaFondosComunesBP();

    void registerEventCambiarCuentaFondosComunesTitular();

    void registerEventCambiarCuentaPrivada();

    void registerEventCambiarCuentaRetail();

    void registerEventCambiarCuentaTenenciaPlazoFijoBP();

    void registerEventCambiarCuentaTenenciaPlazoFijoRTL();

    void registerEventCambiarCuentaTitulosValores();

    void registerEventCancelarVisualizacionMenuFondosInversion();

    void registerEventCuentaUnica();

    void registerEventError();

    void registerEventErrorConsultarFondosComunes();

    void registerEventErrorConsultarFondosComunesPlazosFijos();

    void registerEventFondoComunesFueraHorario();

    void registerEventFondoInversionEnPeso();

    void registerEventFondoInversionEnPesoCuentaUnica();

    void registerEventFondoInversionEnUSD();

    void registerEventFondoInversionEnUSDCuentaUnica();

    void registerEventImpresionNoTenesInversionesPeso();

    void registerEventImpresionNoTenesInversionesUSD();

    void registerEventSeleccionMonedaPeso();

    void registerEventSeleccionMonedaUSD();

    void registerEventSeleccionTeneciaMonedaPeso();

    void registerEventSeleccionTenenciaMonedaUSD();

    void registerEventSinInversionesEnEstaMoneda();

    void registerEventSinTenenciaInversiones();

    void registerEventSuscribirNuevoPlazoFijo();

    void registerEventTitulosValores();

    void registerEventVisualizacionMenuFondosInversion();

    void registerScreenHome(String str);

    void registerScreenNonProfile();

    void registerScreenProfileAgresivo();

    void registerScreenProfileConservador();

    void registerScreenProfileModerado();

    void registerScreenTenenciaReexpresadaDolares();

    void registerScreenTenenciaReexpresadaPesos();

    void tenenciaInversionesErrorParcial();

    void tenenciaInversionesErrorTotal();

    void tenenciaPlazoFijoDetallebp();

    void tenenciaPlazoFijoDetallertl();

    void tenenciaTitulosValoresAccionDetallebp();

    void tenenciaTitulosValoresAccionDetallertl();

    void tenenciaTitulosValoresBonoDetallebp();

    void tenenciaTitulosValoresBonoDetallertl();
}
