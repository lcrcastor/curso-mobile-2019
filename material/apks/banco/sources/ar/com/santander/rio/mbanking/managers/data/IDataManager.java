package ar.com.santander.rio.mbanking.managers.data;

import android.app.Activity;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.LoginEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Destinos;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TimeServer;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ActualizarMensajesMyABodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BajaSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CancelaMandatoExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaRecargaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConstPlazoFijoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaMandatosExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroAccidenteBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaVendedor;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestinoBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestinoOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosIniciales;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleDebinBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimiteExtraccionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntasFamilia;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaProductosRecalificacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaRespuestas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServicioCBBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaCelularesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetasMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UbicacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ValoresBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosOBBean;
import ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public interface IDataManager {
    void ADestinatario(AMAgendadosEnvEfeDestinatarioBean aMAgendadosEnvEfeDestinatarioBean, Activity activity);

    void BDestinatario(int i, BAgendadosEnvEfeDestinatarioBean bAgendadosEnvEfeDestinatarioBean);

    void MDestinatario(AMAgendadosEnvEfeDestinatarioBean aMAgendadosEnvEfeDestinatarioBean, Activity activity);

    void abmAdhesionVendedor(String str, CuentaVendedor cuentaVendedor, Activity activity, boolean z);

    void abmAlias(String str, String str2, String str3, String str4, CuentaShortBean cuentaShortBean);

    void abmAlias(String str, String str2, String str3, String str4, String str5, CuentaShortBean cuentaShortBean);

    void abmDebinComprador(String str, DetalleDebinBean detalleDebinBean, Activity activity, boolean z);

    void abmDebinComprador(String str, String str2, DetalleDebinBean detalleDebinBean, Activity activity, boolean z);

    void abmDebinVendedor(String str, DetalleDebinBean detalleDebinBean, String str2, Activity activity, boolean z);

    void abmDestinatarioTransf(ABMDestinatarioTransfBodyRequestBean aBMDestinatarioTransfBodyRequestBean, Activity activity, Boolean bool);

    void abmPreautorizacionComprador(String str, DetallePreAutorizacionBean detallePreAutorizacionBean, Activity activity, boolean z);

    void abmTurno(String str, String str2, String str3, String str4);

    void abmViajes(String str, String str2, String str3, String str4, String str5, String str6, PaisesBean paisesBean, TarjetasMarcacionBean tarjetasMarcacionBean);

    void aceptaTerminos(String str);

    void actualizarMensajesMyA(ActualizarMensajesMyABodyRequestBean actualizarMensajesMyABodyRequestBean);

    void blanqueoPIN(BlanqueoPinBodyRequestBean blanqueoPinBodyRequestBean, Activity activity);

    void cambiarLimite(LimiteExtraccionBean limiteExtraccionBean, String str, Activity activity);

    void cancelRequest(String str);

    void cancelaMandato(CancelaMandatoExtEnvBodyRequestBean cancelaMandatoExtEnvBodyRequestBean);

    void canjearPuntosSuperClub(String str, String str2, String str3, String str4, String str5, String str6);

    void cargaDatosInicialesEnv();

    void cargaDatosInicialesExt();

    void checkVersion(String str, String str2, String str3, Activity activity, boolean z);

    void cnsDeuda();

    void cnsDeudaCB(String str, String str2, String str3);

    void cnsDeudaManual(String str, String str2);

    void cnsDeudaRecarga(CnsDeudaRecargaBodyRequestBean cnsDeudaRecargaBodyRequestBean, EVersionServices eVersionServices);

    void cnsEmpresa(String str);

    void cnsTasasPFT();

    void cnsTenencias(String str);

    void compraVentaDolar(String str, String str2, String str3, String str4, String str5, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2);

    void confirmarRecalificacion(ListaProductosRecalificacionBean listaProductosRecalificacionBean, int i);

    void consDescripciones(boolean z, boolean z2);

    void consultaAgendados();

    void consultaAlias(String str, String str2, String str3);

    void consultaDatosInicialesTransf(DatosIniciales datosIniciales);

    void consultaLeyendasGenerales(String str);

    void consultaLeyendasGeneralesPrimerIngreso(String str);

    void consultaMandatos(ConsultaMandatosExtEnvBodyRequestBean consultaMandatosExtEnvBodyRequestBean);

    void consultaPrestamosPermitidos(ConsultaPrestamosPermitidosBodyRequestBean consultaPrestamosPermitidosBodyRequestBean, boolean z);

    void consultaPrestamosPermitidosProd(ConsultaPrestamosPermitidosBodyRequestBeanProd consultaPrestamosPermitidosBodyRequestBeanProd, boolean z);

    void consultaSolicitudCrediticia(ConsultaSolicitudCrediticiaBodyRequestBean consultaSolicitudCrediticiaBodyRequestBean);

    void consultaSolicitudCrediticiaProd(ConsultaSolicitudCrediticiaBodyRequestBeanProd consultaSolicitudCrediticiaBodyRequestBeanProd);

    void consultaSuscripcionMyA(String str, String str2);

    void consultarAdhesionVendedor();

    void consultarTitularCuenta(String str, String str2);

    void contratarCompraProtegida(String str, String str2, String str3, String str4, List<TarjetaBean> list, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean);

    void contratarSeguroAccidente(CotizacionSeguroAccidenteBean cotizacionSeguroAccidenteBean, String str, String str2, String str3);

    void contratarSeguroMovil(String str, String str2, String str3, String str4, String str5, String str6, String str7, BajaSeguroBean bajaSeguroBean, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean);

    void contratarSeguroObjeto(ContratarSeguroObjetoBodyRequestBean contratarSeguroObjetoBodyRequestBean);

    void enviarSugerenciaObjeto(String str, String str2, String str3, String str4);

    void enviarTokenOBP(String str, String str2, String str3, String str4);

    void generaMandato(GeneraMandatoEnvBodyRequestBean generaMandatoEnvBodyRequestBean, Activity activity);

    void generaMandato(GeneraMandatoExtBodyRequestBean generaMandatoExtBodyRequestBean, Activity activity);

    void genesysChat(String str, String str2);

    void genesysChatBackground(String str, String str2);

    void getCajerosDetalle(String str);

    void getCategoriasSuperClub();

    void getCategoriasSuperClub(String str);

    void getCircuitoTurno(String str);

    void getCnsDeuda(CnsDeudaBodyRequestBean cnsDeudaBodyRequestBean);

    void getConfirmarPago(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8);

    void getConstPlazoFijo(ConstPlazoFijoBodyRequestBean constPlazoFijoBodyRequestBean);

    void getCotizacionCompraProtegida();

    void getCotizacionSeguroAccidente();

    void getCotizacionSeguroMovil();

    void getCotizacionSeguroObjeto(String str, ListaPreguntasFamilia listaPreguntasFamilia);

    void getCotizacionesFondos();

    void getCuponesCategorySuperClub(String str, String str2, String str3);

    void getCuponesSubcategorySuperClub(String str, String str2, String str3);

    void getCustodia(String str, String str2, String str3, String str4);

    void getDatosInicialesDolares(String str);

    void getDatosInicialesDolares(String str, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean);

    void getDebines(String str, String str2, String str3, String str4, String str5);

    void getDebinesBusquedaAvanzada(String str, String str2, String str3, String str4, String str5);

    void getDetCtas(MovCtasBodyRequestBean movCtasBodyRequestBean);

    void getDetCtas(String str, String str2, String str3);

    void getDetalleCuotaTenenciaCredito(String str, String str2, String str3);

    void getDetalleDebinComprador(String str);

    void getDetalleDebinVendedor(String str);

    void getDetalleFondo(String str);

    void getDetallePreautorizacionComprador(String str, String str2);

    void getDetallesPromocion(String str);

    void getFamiliasObjetos();

    void getFirmaCredin(Activity activity);

    void getFirmaSC();

    void getFirmaSeguro();

    void getFondos();

    void getInfoAdmFondos();

    void getLimitesExtraccion();

    void getLimitesProductos();

    void getListaTjWomen();

    void getMovCtas(MovCtasBodyRequestBean movCtasBodyRequestBean);

    void getMovimientosFondo(String str, String str2);

    void getMovimientosFondo(String str, String str2, Boolean bool);

    void getMovimientosFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    void getNotificacionesPush(String str);

    void getNumerosUtiles();

    void getOcupaciones();

    void getPagoServicios(PagoServiciosBodyRequestBean pagoServiciosBodyRequestBean, Activity activity, boolean z);

    void getPoliza(String str, String str2, String str3);

    void getPreautorizaciones(String str, String str2, String str3);

    void getPreguntasFamilia(String str);

    void getPromocionPush(LocalizacionBean localizacionBean, String str);

    void getPromocionesHome();

    void getProximasCuotas(String str, String str2, String str3);

    void getPuntosSuperClub();

    void getRecargas(String str, String str2, String str3, String str4, String str5);

    void getRecotizacionSeguroObjeto(String str, String str2, String str3);

    void getRetryTimeOutServer(TimeServer timeServer);

    void getSeguros();

    SessionManager getSessionManager();

    void getSimularPagos(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    void getSucursales(String str, String str2, LatLng latLng, ValoresBean valoresBean);

    void getSucursalesTurno(String str, String str2, UbicacionBean ubicacionBean);

    void getSucursalesTurnoConfirmar(String str, String str2, UbicacionBean ubicacionBean);

    void getSucursalesTurnoSolicitud(String str, String str2, UbicacionBean ubicacionBean);

    void getTarjPaises();

    void getTenenciaCreditos();

    void getTenenciaFondos();

    void getTenenciaInversiones(String str);

    void getTenenciaTitulos(String str, String str2, String str3, String str4);

    void getTurno();

    void getViajes();

    void limitesYDisponiblesTC(LimitesYDisponiblesTCBodyRequestBean limitesYDisponiblesTCBodyRequestBean, TypeOption typeOption, Tarjeta tarjeta);

    void logOutSession();

    void logOutSession(String str, ListaRespuestas listaRespuestas);

    void loginUnicoAltaUsuario(String str, String str2, String str3, String str4, String str5, Activity activity);

    void loginUnicoCambioUsuario(String str, String str2, String str3, String str4, String str5, String str6, Activity activity);

    void loginUnicoClaveVencida(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, Activity activity);

    void loginUnicoPrimerIngreso(String str, String str2, String str3, String str4, String str5, String str6, Activity activity);

    void loginUnicoResend(List<LoginEvent> list);

    void loginUnicoResendEvent(LoginEvent loginEvent);

    void loginUnicoSinonimo(String str, String str2, String str3, String str4, Activity activity);

    void loginUnicoSinonimoCallToAction(String str, String str2, String str3, String str4, Activity activity);

    void loginUnicoTradicional(String str, String str2, String str3, String str4, Activity activity);

    void modificarSuscripcionMyA(Destinos destinos);

    void obtenerEstadoSuscripcionMyA();

    void pagoServicioCB(PagoServicioCBBodyRequestBean pagoServicioCBBodyRequestBean, Activity activity);

    void pagoTarjeta(ArrayList<String> arrayList);

    void recargaCelulares(RecargaCelularesBodyRequestBean recargaCelularesBodyRequestBean, Activity activity);

    void registrarSuscripcionMyA(String str, String str2, String str3, String str4);

    void rescatarFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11);

    void simulacionDolar(String str, String str2, String str3, String str4, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2);

    void simularRescateFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9);

    void simularSuscripcionFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9);

    void simularTransferenciaFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    void solicitudLimiteTransf(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10);

    void solicitudPrestamoPreacordado(SolicitudPrestamoPreacordadoBodyRequestBean solicitudPrestamoPreacordadoBodyRequestBean, Activity activity);

    void solicitudPrestamoPreacordadoProd(SolicitudPrestamoPreacordadoBodyRequestBeanProd solicitudPrestamoPreacordadoBodyRequestBeanProd, Activity activity);

    void stopDebit(ArrayList<String> arrayList);

    void suscribirFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13);

    void transferencias(DatosTransferenciaBean datosTransferenciaBean, DatosCuentaDebitoBean datosCuentaDebitoBean, DatosCuentasDestinoBSRBean datosCuentasDestinoBSRBean, Activity activity);

    void transferencias(DatosTransferenciaBean datosTransferenciaBean, DatosCuentaDebitoBean datosCuentaDebitoBean, DatosCuentasDestinoOBBean datosCuentasDestinoOBBean, Activity activity);

    void transferirFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13);

    void ultimoResumenTC(UltimoResumenTCBodyRequestBean ultimoResumenTCBodyRequestBean);

    void ultimosConsumosTC(UltimosConsumosTCBodyRequestBean ultimosConsumosTCBodyRequestBean, Tarjeta tarjeta);

    void verificaDatosInicialesTransf(VerificaDatosBean verificaDatosBean);

    void verificaDatosInicialesTransfOB(VerificaDatosOBBean verificaDatosOBBean);

    void womenProgramSuscription(String str, List<ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta> list);
}
