package ar.com.santander.rio.mbanking.managers.data;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.module.creditos.CreditosConstants;
import ar.com.santander.rio.mbanking.app.module.creditos.CreditosConstantsProd;
import ar.com.santander.rio.mbanking.app.module.longtermdeposit.LongTermDepositPresenterImp;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ABMAliasEvent;
import ar.com.santander.rio.mbanking.services.events.ABMDestinatarioTransfEvent;
import ar.com.santander.rio.mbanking.services.events.ABMViajesEvent;
import ar.com.santander.rio.mbanking.services.events.AMAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.AbmAdhesionVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.AbmDebinCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.AbmDebinVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.AbmPreautorizacionCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.AbmTurnoEvent;
import ar.com.santander.rio.mbanking.services.events.AceptaTerminosEvent;
import ar.com.santander.rio.mbanking.services.events.ActualizarMensajesMyAEvent;
import ar.com.santander.rio.mbanking.services.events.BAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.BlanqueoPINEvent;
import ar.com.santander.rio.mbanking.services.events.CambiarLimiteEvent;
import ar.com.santander.rio.mbanking.services.events.CancelaMandatoExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.CanjearPuntosSuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.CargaDatosInicialesExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.CheckVersionEvent;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaCBEvent;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaEvent;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaManualEvent;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaRecargaEvent;
import ar.com.santander.rio.mbanking.services.events.CnsEmpresaEvent;
import ar.com.santander.rio.mbanking.services.events.CnsTasasPFTEvent;
import ar.com.santander.rio.mbanking.services.events.CnsTenenciasEvent;
import ar.com.santander.rio.mbanking.services.events.CompraVentaDolarEvent;
import ar.com.santander.rio.mbanking.services.events.ConfirmarPagoEvent;
import ar.com.santander.rio.mbanking.services.events.ConfirmarRecalificacionEvent;
import ar.com.santander.rio.mbanking.services.events.ConsDescripcionesEvent;
import ar.com.santander.rio.mbanking.services.events.ConstPlazoFijoEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaAliasEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaDatosInicialesTransfEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaLeyendasEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaLeyendasPrimerIngresoEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaMandatosExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaPrestamosPermitidosEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaPrestamosPermitidosEventProd;
import ar.com.santander.rio.mbanking.services.events.ConsultaSolicitudCrediticiaEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaSolicitudCrediticiaEventProd;
import ar.com.santander.rio.mbanking.services.events.ConsultaSuscripcionMyAEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultarAdhesionVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultarTitularCuentaEvent;
import ar.com.santander.rio.mbanking.services.events.ContratarCompraProtegidaEvent;
import ar.com.santander.rio.mbanking.services.events.ContratarSeguroAccidenteEvent;
import ar.com.santander.rio.mbanking.services.events.ContratarSeguroMovilEvent;
import ar.com.santander.rio.mbanking.services.events.ContratarSeguroObjetoEvent;
import ar.com.santander.rio.mbanking.services.events.DetCtasEvent;
import ar.com.santander.rio.mbanking.services.events.EnviarSugerenciaObjetoEvent;
import ar.com.santander.rio.mbanking.services.events.EnviarTokenOBPEvent;
import ar.com.santander.rio.mbanking.services.events.GeneraMandatoExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.GenesysChatBackgroundEvent;
import ar.com.santander.rio.mbanking.services.events.GenesysChatEvent;
import ar.com.santander.rio.mbanking.services.events.GetCajerosDetalleEvent;
import ar.com.santander.rio.mbanking.services.events.GetCategoriasSuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.GetCircuitoTurnoEvent;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionCompraProtegidaEvent;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroAccidenteEvent;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroMovilEvent;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroObjetoEvent;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionesFondoEvent;
import ar.com.santander.rio.mbanking.services.events.GetCuponesCategorySuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.GetCuponesSubcategorySuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.GetDatosInicialesDolaresEvent;
import ar.com.santander.rio.mbanking.services.events.GetDebinesBusquedaAvanzadaEvent;
import ar.com.santander.rio.mbanking.services.events.GetDebinesEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleCuotaTenenciaCreditoEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleDebinCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleDebinVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleFondoEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetallePreAutorizacionCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.GetFamiliasObjetosEvent;
import ar.com.santander.rio.mbanking.services.events.GetFirmaCredinEvent;
import ar.com.santander.rio.mbanking.services.events.GetFirmaSCEvent;
import ar.com.santander.rio.mbanking.services.events.GetFirmaSeguroEvent;
import ar.com.santander.rio.mbanking.services.events.GetFondosEvent;
import ar.com.santander.rio.mbanking.services.events.GetInfoAdmFondosEvent;
import ar.com.santander.rio.mbanking.services.events.GetLimitesExtraccionEvent;
import ar.com.santander.rio.mbanking.services.events.GetLimitesProductosEvent;
import ar.com.santander.rio.mbanking.services.events.GetListaTjWomenEvent;
import ar.com.santander.rio.mbanking.services.events.GetMovimientosFondoEvent;
import ar.com.santander.rio.mbanking.services.events.GetNotificacionesPushEvent;
import ar.com.santander.rio.mbanking.services.events.GetNumerosUtilesEvent;
import ar.com.santander.rio.mbanking.services.events.GetOcupacionesEvent;
import ar.com.santander.rio.mbanking.services.events.GetPolizaEvent;
import ar.com.santander.rio.mbanking.services.events.GetPreAutorizacionesEvent;
import ar.com.santander.rio.mbanking.services.events.GetPromocionPushEvent;
import ar.com.santander.rio.mbanking.services.events.GetPromocionesHomeEvent;
import ar.com.santander.rio.mbanking.services.events.GetProximasCuotasCreditoEvent;
import ar.com.santander.rio.mbanking.services.events.GetPuntosSuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.GetRecargasEvent;
import ar.com.santander.rio.mbanking.services.events.GetSegurosEvent;
import ar.com.santander.rio.mbanking.services.events.GetSucursalesEvent;
import ar.com.santander.rio.mbanking.services.events.GetSucursalesSolicitudEvent;
import ar.com.santander.rio.mbanking.services.events.GetTarjPaisesEvent;
import ar.com.santander.rio.mbanking.services.events.GetTenenciaCreditosEvent;
import ar.com.santander.rio.mbanking.services.events.GetTenenciaFondosEvent;
import ar.com.santander.rio.mbanking.services.events.GetTurnoEvent;
import ar.com.santander.rio.mbanking.services.events.GetViajesEvent;
import ar.com.santander.rio.mbanking.services.events.LimitesYDisponiblesTCEvent;
import ar.com.santander.rio.mbanking.services.events.LogOutEvent;
import ar.com.santander.rio.mbanking.services.events.LoginEvent;
import ar.com.santander.rio.mbanking.services.events.ModificarSuscripcionEvent;
import ar.com.santander.rio.mbanking.services.events.MovCtasEvent;
import ar.com.santander.rio.mbanking.services.events.ObtenerSuscripcionEvent;
import ar.com.santander.rio.mbanking.services.events.PagoServicioCBEvent;
import ar.com.santander.rio.mbanking.services.events.PagoServiciosEvent;
import ar.com.santander.rio.mbanking.services.events.PagoTarjetaEvent;
import ar.com.santander.rio.mbanking.services.events.PreguntasFamiliaEvent;
import ar.com.santander.rio.mbanking.services.events.PromocionDetalleEvent;
import ar.com.santander.rio.mbanking.services.events.RecargaCelularesEvent;
import ar.com.santander.rio.mbanking.services.events.RegistrarSuscripcionEvent;
import ar.com.santander.rio.mbanking.services.events.RescatarFondoEvent;
import ar.com.santander.rio.mbanking.services.events.SimulacionDolarEvent;
import ar.com.santander.rio.mbanking.services.events.SimularPagoCuotaEvent;
import ar.com.santander.rio.mbanking.services.events.SimularSuscripcionFondoEvent;
import ar.com.santander.rio.mbanking.services.events.SimularTransferenciaFondoEvent;
import ar.com.santander.rio.mbanking.services.events.SolicitudLimiteTransfEvent;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEvent;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEventProd;
import ar.com.santander.rio.mbanking.services.events.StopDebitEvent;
import ar.com.santander.rio.mbanking.services.events.SuscribirFondoEvent;
import ar.com.santander.rio.mbanking.services.events.SuscripcionWomenEvent;
import ar.com.santander.rio.mbanking.services.events.TenenciaCustodiaEvent;
import ar.com.santander.rio.mbanking.services.events.TenenciaInversionesEvent;
import ar.com.santander.rio.mbanking.services.events.TenenciaTitulosEvent;
import ar.com.santander.rio.mbanking.services.events.TransferenciasEvent;
import ar.com.santander.rio.mbanking.services.events.TransferirFondoEvent;
import ar.com.santander.rio.mbanking.services.events.UltimoResumenTCEvent;
import ar.com.santander.rio.mbanking.services.events.UltimosConsumosTCEvent;
import ar.com.santander.rio.mbanking.services.events.VAbmTurno;
import ar.com.santander.rio.mbanking.services.events.VerificaDatosInicialesTransfEvent;
import ar.com.santander.rio.mbanking.services.events.VerificaDatosInicialesTransfOBEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.Destinos;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.ListaMensajes;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.ListaProductos;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.Mensaje;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.Producto;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMAliasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMAliasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMDestinatarioTransfRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMDestinatarioTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMViajesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMViajesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AMAgendadosEnvEfeRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AMAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmAdhesionVendedorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmAdhesionVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinCompradorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinVendedorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmPreautorizacionCompradorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmPreautorizacionCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmTurnoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmTurnoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AceptaTerminosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AceptaTerminosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ActualizarMensajesMyARequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ActualizarMensajesMyARequestResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.BAgendadosEnvEfeRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.BAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CambiarLimiteRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CambiarLimiteResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CancelaMandatoExtEnvRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CancelaMandatoExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CanjearPuntosSuperClubRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CanjearPuntosSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CargaDatosInicialesExtEnvRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CargaDatosInicialesExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CheckVersionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CheckVersionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaCBRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaCBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaManualRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaManualResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaPTRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaPTResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaRecargaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaRecargaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsEmpresaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsEmpresaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTasasPFTRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTasasPFTResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTenenciasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTenenciasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CompraVentaDolarRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CompraVentaDolarResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConfirmarPagoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConfirmarPagoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsDescriptionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsDescriptionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConstPlazoFijoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConstPlazoFijoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAliasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAliasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaDatosInicialesTransfRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaDatosInicialesTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaLeyendasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaLeyendasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSuscripcionMyARequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSuscripcionMyAResponeBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarAdhesionVendedorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarAdhesionVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarTitularCuentaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarTitularCuentaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarCompraProtegidaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarCompraProtegidaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroAccidenteRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroAccidenteResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroMovilRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroMovilResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroObjetoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroObjetoResquestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarSugerenciaObjetoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarSugerenciaObjetoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarTokenOBPRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarTokenOBPResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtEnvRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GenesysChatRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GenesysChatResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCajerosDetalleRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCajerosDetalleResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCategoriasSuperClubRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCategoriasSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionCompraProtegidaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionCompraProtegidaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionSeguroMovilRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionSeguroMovilResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCuponesSuperClubRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCuponesSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDatosInicialesDolaresRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDatosInicialesDolaresResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDebinesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDebinesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleCuotaTenenciaCreditoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleCuotaTenenciaCreditoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinCompradorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinVendedorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetallePreAutorizacionCompradorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetallePreAutorizacionCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaCredinResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSeguroRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSeguroResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFondosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetInfoAdmFondosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetInfoAdmFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetLimitesExtraccionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetLimitesExtraccionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetListaTjWomenRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetMovimientosFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetMovimientosFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNotificacionesPushRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNotificacionesPushResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetOcupacionesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetOcupacionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPolizaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPolizaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesHomeRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesHomeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesPushRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesPushResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetProximasCuotasCreditosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetProximasCuotasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPuntosSuperClubRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPuntosSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetRecargasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetRecargasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSegurosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSegurosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTarjPaisesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTarjPaisesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaFondosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetViajesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetViajesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LimitesYDisponiblesTCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LimitesYDisponiblesTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaTarjetas;
import ar.com.santander.rio.mbanking.services.soap.beans.LogOutRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LogOutResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ModificarSuscripcionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ModificarSuscripcionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ObtenerEstadoSuscripcionMyARequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoServicioCBRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoServicioCBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoServiciosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoServiciosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoTarjetaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoTarjetaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PreguntasFamiliaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PreguntasFamiliaResquestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PromocionDetalleRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PromocionDetalleResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RecargaCelularesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RecargaCelularesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RegistrarSuscripcionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RegistrarSuscripcionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RescatarFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RescatarFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RetryServerRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimulacionDolarRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimulacionDolarResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularPagosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularPagosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularSuscripcionFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularTransferenciaFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudLimiteTransfRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudLimiteTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.StopDebitRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.StopDebitResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscribirFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscribirFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscripcionWomenRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscripcionWomenResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TimeServer;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferenciasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferenciasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferirFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferirFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimoResumenTCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimoResumenTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMAliasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMViajesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmAdhesionVendedorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinCompradorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmPreautorizacionCompradorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AceptaTerminosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ActualizarMensajesMyABodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BAgendadosEnvEfeBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BajaSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CambiarLimiteBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CancelaMandatoExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CanjearPuntosSuperClubBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CargaDatosInicialesExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CheckVersionBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaCBBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaManualBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaPTBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaRecargaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolarBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarPagoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsDescriptionBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConstPlazoFijoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaAliasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaDatosInicialesTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaLeyendasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaMandatosExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSuscripcionMyABodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarAdhesionVendedorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarTitularCuentaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroMovilBodyRequestBean;
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
import ar.com.santander.rio.mbanking.services.soap.beans.body.EnviarSugerenciaObjetoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.EnviarTokenOBPBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GenesysChatBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCajerosDetalleBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCategoriasSuperClubBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionCompraProtegidaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroMovilBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCuponesSuperClubBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDatosInicialesDolaresBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDebinesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleCuotaTenenciaCreditoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleDebinCompradorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleDebinVendedorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetallePreAutorizacionCompradorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaCredinBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaCredinRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSeguroBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFondosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetInfoAdmFondosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesExtraccionBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetMovimientosFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetNotificacionesPushBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetNumerosUtilesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPolizaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesPushBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetProximasCuotasCreditosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetRecargasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSegurosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSimularPagosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaFondosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetViajesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimiteExtraccionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntasFamilia;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaProductosRecalificacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaRespuestas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LogOutBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ModificarSuscripcionnBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ObtenerEstadoSuscripcionMyABodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServicioCBBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoTarjetaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntasFamiliaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionDetalleBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaCelularesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RegistrarSuscripcionBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimulacionDolarBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudLimiteTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.StopDebitBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscripcionWomenBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetasMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferenciasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UbicacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ValoresBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.builders.header.ManagerHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.ABMAliasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ABMDestinatarioTransfRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ABMViajesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AMAgendadosEnvEfeRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AbmAdhesionVendedorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AbmDebinCompradorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AbmDebinVendedorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AbmPreautorizacionCompradorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AbmTurnoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AceptaTerminosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ActualizarMensajesMyARequest;
import ar.com.santander.rio.mbanking.services.soap.request.BAgendadosEnvEfeRequest;
import ar.com.santander.rio.mbanking.services.soap.request.BaseRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CambiarLimiteRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CancelaMandatoExtEnvRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CanjearPuntosSuperClubRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CargaDatosInicialesExtEnvRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CheckVersionRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsDeudaCBRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsDeudaManualRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsDeudaPTRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsDeudaRecargaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsDeudaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsEmpresaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsTasasPFTRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsTenenciasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CompraVentaDolarRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConfirmarPagoCuotaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsDescripcionesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConstPlazoFijoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaAgendadosEnvEfeRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaAliasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaDatosInicialesTransfRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaLeyendasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaMandatosExtEnvRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaPrestamosPermitidosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaPrestamosPermitidosRequestProd;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaSolicitudCrediticiaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaSolicitudCrediticiaRequestProd;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaSuscripcionMyARequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultarAdhesionVendedorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultarTitularCuentaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ContratarCompraProtegidaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ContratarSeguroAccidenteRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ContratarSeguroMovilRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ContratarSeguroObjetoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.EnviarSugerenciaObjetoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.EnviarTokenOBPRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GeneraMandatoExtEnvRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GeneraMandatoExtRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GenesysChatRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetCajerosDetalleRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetCategoriasSuperClubRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetCotizacionCompraProtegidaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetCotizacionSeguroMovilRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetCotizacionesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetCuponesSuperClubRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDatosInicialesDolaresRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDebinesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDetalleCuotaTenenciaCreditoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDetalleDebinCompradorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDetalleDebinVendedorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDetalleFondoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDetallePreAutorizacionCompradorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetFirmaCredinRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetFirmaSCRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetFirmaSeguroRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetFondosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetInfoAdmFondosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetLimitesExtraccionRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetListaTjWomenRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetMovimientosFondoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetNotificacionesPushRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetNumerosUtilesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetOcupacionesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetPolizaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetPreAutorizacionesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetPromocionesHomeRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetPromocionesPushRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetProximasCuotasCreditosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetPuntosSuperClubRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetRecargasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetSegurosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetSucursalesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetSucursalesTurnoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetTarjPaisesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetTenenciaCreditosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetTenenciaFondosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetViajesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.LimitesYDisponiblesTCRequest;
import ar.com.santander.rio.mbanking.services.soap.request.LogOutRequest;
import ar.com.santander.rio.mbanking.services.soap.request.LoginUnicoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ModificarSuscripcionMyARequest;
import ar.com.santander.rio.mbanking.services.soap.request.MovCtasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ObtenerEstadoSuscripcionMyARequest;
import ar.com.santander.rio.mbanking.services.soap.request.PagoServicioCBRequest;
import ar.com.santander.rio.mbanking.services.soap.request.PagoServiciosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.PagoTarjetaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.PreguntasFamiliaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.PromocionDetalleRequest;
import ar.com.santander.rio.mbanking.services.soap.request.RecargaCelularesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.RegistrarSuscripcionMyARequest;
import ar.com.santander.rio.mbanking.services.soap.request.RescatarFondoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.RetryServerRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SimulacionDolarRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SimularPagoCuotasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SimularSuscripcionFondoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SimularTransferenciaFondoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SolicitudLimiteTransfRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SolicitudPrestamoPreacordadoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SolicitudPrestamoPreacordadoRequestProd;
import ar.com.santander.rio.mbanking.services.soap.request.StopDebitRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SuscribirFondoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SuscripcionWomenRequest;
import ar.com.santander.rio.mbanking.services.soap.request.TransferenciasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.TransferirFondoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.UltimoResumenTCRequest;
import ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest;
import ar.com.santander.rio.mbanking.services.soap.request.VerificaDatosInicialesTransfOBRequest;
import ar.com.santander.rio.mbanking.services.soap.request.VerificaDatosInicialesTransfRequest;
import ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMAgendadosEnvEfe;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMAlias;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMDestinatarioTransf;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMViajes;
import ar.com.santander.rio.mbanking.services.soap.versions.VAbmAdhesionVendedorDebin;
import ar.com.santander.rio.mbanking.services.soap.versions.VAbmPreautorizacionComprador;
import ar.com.santander.rio.mbanking.services.soap.versions.VAceptaTerminos;
import ar.com.santander.rio.mbanking.services.soap.versions.VActualizarMensajesMyA;
import ar.com.santander.rio.mbanking.services.soap.versions.VCajerosDetalle;
import ar.com.santander.rio.mbanking.services.soap.versions.VCambiarLimite;
import ar.com.santander.rio.mbanking.services.soap.versions.VCancelaMandatoExtEnv;
import ar.com.santander.rio.mbanking.services.soap.versions.VCanjearPuntosSuperClub;
import ar.com.santander.rio.mbanking.services.soap.versions.VCargaDatosInicialesExtEnv;
import ar.com.santander.rio.mbanking.services.soap.versions.VCheckVersion;
import ar.com.santander.rio.mbanking.services.soap.versions.VCndDeuda;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeuda;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeudaCB;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeudaManual;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeudaRecarga;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeudaTarjetas;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsEmpresa;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsTasasPFT;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsTenencias;
import ar.com.santander.rio.mbanking.services.soap.versions.VCompraVentaDolar;
import ar.com.santander.rio.mbanking.services.soap.versions.VConfirmarPagoCuota;
import ar.com.santander.rio.mbanking.services.soap.versions.VConfirmarRecalificacion;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsDescription;
import ar.com.santander.rio.mbanking.services.soap.versions.VConstPlazoFijo;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaAgendadosEnvEfe;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaAlias;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaDatosInicialesTransf;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaMandatosExtEnv;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaPrestamosPermitidos;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaPrestamosPermitidosProd;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaSolicitudCrediticia;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaSolicitudCrediticiaProd;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaSuscripcionMyA;
import ar.com.santander.rio.mbanking.services.soap.versions.VContratarCompraProtegida;
import ar.com.santander.rio.mbanking.services.soap.versions.VContratarSeguroMovil;
import ar.com.santander.rio.mbanking.services.soap.versions.VContratarSeguroObjeto;
import ar.com.santander.rio.mbanking.services.soap.versions.VDCTendenciaCredito;
import ar.com.santander.rio.mbanking.services.soap.versions.VDetCtas;
import ar.com.santander.rio.mbanking.services.soap.versions.VEnviarSugerenciaObjeto;
import ar.com.santander.rio.mbanking.services.soap.versions.VEnviarTokenOBP;
import ar.com.santander.rio.mbanking.services.soap.versions.VGeneraMandatoExtEnv;
import ar.com.santander.rio.mbanking.services.soap.versions.VGenesysChat;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetAbmDebinComprador;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetAbmDebinVendedor;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCategoriasSuperClub;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCircuitoTurno;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetConsultarAdhesionVendedor;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetConsultarTitularCuenta;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetContratarSeguroAccidente;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionCompraProtegida;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroAccidente;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroMovil;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroObjeto;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizaciones;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCuponesSuperClub;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDatosInicialesDolares;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDebines;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetalleDebinComprador;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetalleDebinVendedor;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetalleFondo;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetallePreAutorizacionComprador;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFamiliasObjetos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFirmaCredin;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFirmaSC;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFirmaSeguros;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFondos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetInfoAdmFondos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetLimitesExtraccion;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetLimitesProductos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetListaTjWomen;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetMovimientosFondo;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetNotificacionesPush;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetOcupaciones;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPoliza;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPreAutorizaciones;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPreguntasFamilia;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPromocionPush;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPuntosSuperClub;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetRecalificacion;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetSeguros;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetSucursalesTurno;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTarjPaises;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTenenciaCreditos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTenenciaFondos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTurno;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetViajes;
import ar.com.santander.rio.mbanking.services.soap.versions.VLimitesYDisponiblesTC;
import ar.com.santander.rio.mbanking.services.soap.versions.VLogOut;
import ar.com.santander.rio.mbanking.services.soap.versions.VLoginUnico;
import ar.com.santander.rio.mbanking.services.soap.versions.VModificarSuscripcion;
import ar.com.santander.rio.mbanking.services.soap.versions.VMovCtas;
import ar.com.santander.rio.mbanking.services.soap.versions.VNumerosUtiles;
import ar.com.santander.rio.mbanking.services.soap.versions.VObtenerEstadoSuscripcion;
import ar.com.santander.rio.mbanking.services.soap.versions.VPagoServicio;
import ar.com.santander.rio.mbanking.services.soap.versions.VPagoServicioCB;
import ar.com.santander.rio.mbanking.services.soap.versions.VPagoTarjeta;
import ar.com.santander.rio.mbanking.services.soap.versions.VPromocionDetalle;
import ar.com.santander.rio.mbanking.services.soap.versions.VPromocionesHome;
import ar.com.santander.rio.mbanking.services.soap.versions.VProximasCuotasCreditos;
import ar.com.santander.rio.mbanking.services.soap.versions.VRecargaCelulares;
import ar.com.santander.rio.mbanking.services.soap.versions.VRecargas;
import ar.com.santander.rio.mbanking.services.soap.versions.VRegistrarSuscripcion;
import ar.com.santander.rio.mbanking.services.soap.versions.VRescatarFondo;
import ar.com.santander.rio.mbanking.services.soap.versions.VRetryServer;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimulacionDolar;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimularPagos;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimularRescateFondo;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimularSuscripcionFondo;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimularTransferenciaFondo;
import ar.com.santander.rio.mbanking.services.soap.versions.VSolicitudLimiteTransf;
import ar.com.santander.rio.mbanking.services.soap.versions.VSolicitudPrestamoPreacordado;
import ar.com.santander.rio.mbanking.services.soap.versions.VSolicitudPrestamoPreacordadoProd;
import ar.com.santander.rio.mbanking.services.soap.versions.VStopDebit;
import ar.com.santander.rio.mbanking.services.soap.versions.VSucursales;
import ar.com.santander.rio.mbanking.services.soap.versions.VSuscribirFondo;
import ar.com.santander.rio.mbanking.services.soap.versions.VTenenciaCustodia;
import ar.com.santander.rio.mbanking.services.soap.versions.VTenenciaInversiones;
import ar.com.santander.rio.mbanking.services.soap.versions.VTenenciaTitulosValores;
import ar.com.santander.rio.mbanking.services.soap.versions.VTransferencias;
import ar.com.santander.rio.mbanking.services.soap.versions.VTransferirFondo;
import ar.com.santander.rio.mbanking.services.soap.versions.VUltimoResumenTC;
import ar.com.santander.rio.mbanking.services.soap.versions.VUltimosConsumosTC;
import ar.com.santander.rio.mbanking.services.soap.versions.VVerificaDatosInicialesTransf;
import ar.com.santander.rio.mbanking.services.soap.versions.VblanqueoPIN;
import ar.com.santander.rio.mbanking.services.soap.versions.vSuscripcionWomen;
import ar.com.santander.rio.mbanking.utils.BackgroundThread;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.LatLng;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.request.VolleyRequestSingleton;
import com.squareup.otto.Bus;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import java.util.ArrayList;
import java.util.List;

public class DataManager implements IDataManager {
    public static final String TAG = "ar.com.santander.rio.mbanking.managers.data.DataManager";
    SettingsManager a;
    BaseApplication b;
    Bus c;
    /* access modifiers changed from: private */
    public Handler d = new Handler();
    public SessionManager sessionManager;

    public DataManager(BaseApplication baseApplication, Bus bus, SessionManager sessionManager2, SettingsManager settingsManager) {
        this.b = baseApplication;
        this.c = bus;
        this.sessionManager = sessionManager2;
        this.a = settingsManager;
    }

    public void cancelRequest(String str) {
        VolleyRequestSingleton.getInstance(this.b).getRequestQueue().cancelAll((Object) str);
    }

    public void checkVersion(String str, String str2, String str3, Activity activity, boolean z) {
        final CheckVersionEvent checkVersionEvent = new CheckVersionEvent();
        checkVersionEvent.setRetry(z);
        this.sessionManager.setCheckVersionEvent(null);
        AnonymousClass1 r0 = new CheckVersionRequest(this.b.getApplicationContext(), new CheckVersionRequestBean(a(VCheckVersion.getData(EVersionServices.CURRENT), Boolean.valueOf(true), Boolean.valueOf(false), activity), new CheckVersionBodyRequestBean(str, str2), str3), createErrorRequestServer(checkVersionEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                CheckVersionResponseBean checkVersionResponseBean = (CheckVersionResponseBean) iBeanWS;
                checkVersionEvent.setBeanResponse(checkVersionResponseBean);
                checkVersionEvent.setResult(TypeResult.OK);
                DataManager.this.sessionManager.setCheckVersionEvent(checkVersionEvent);
                DataManager.this.a.setLastIdTerminal(checkVersionResponseBean.getCheckVersionBodyResponseBean().getIdTerminal());
                DataManager.this.sessionManager.setCheckVersion(true);
                DataManager.this.a.setWebViewURLHome(checkVersionResponseBean.getCheckVersionBodyResponseBean().getWebViewHome());
                DataManager.this.sessionManager.setToken(checkVersionResponseBean.getCheckVersionBodyResponseBean().getToken());
                DataManager.this.sendBusEvent(checkVersionEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, checkVersionEvent, VCheckVersion.nameService);
                DataManager.this.sessionManager.setCheckVersionEvent(checkVersionEvent);
                DataManager.this.sessionManager.setCheckVersion(false);
            }
        };
        r0.setTimeOutResponse(Constants.CHECK_VERSION_TIME_OUT_SERVER * 1000);
        r0.sendRequest(VCheckVersion.nameService);
    }

    public void getDetallesPromocion(String str) {
        final PromocionDetalleEvent promocionDetalleEvent = new PromocionDetalleEvent();
        AnonymousClass2 r0 = new PromocionDetalleRequest(this.b.getApplicationContext(), new PromocionDetalleRequestBean(a(VPromocionDetalle.getData(EVersionServices.CURRENT)), new PromocionDetalleBodyRequestBean(str)), createErrorRequestServer(promocionDetalleEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                promocionDetalleEvent.setBeanResponse((PromocionDetalleResponseBean) iBeanWS);
                promocionDetalleEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(promocionDetalleEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, promocionDetalleEvent, VPromocionDetalle.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VPromocionDetalle.nameService);
    }

    public void getSucursales(String str, String str2, LatLng latLng, ValoresBean valoresBean) {
        final GetSucursalesEvent getSucursalesEvent = new GetSucursalesEvent();
        AnonymousClass3 r0 = new GetSucursalesRequest(this.b.getApplicationContext(), new GetSucursalesRequestBean(a(VSucursales.getData(EVersionServices.CURRENT)), new GetSucursalesBodyRequestBean(str, str2, new LocalizacionBean(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude)), valoresBean)), createErrorRequestServer(getSucursalesEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getSucursalesEvent.setBeanResponse((GetSucursalesResponseBean) iBeanWS);
                getSucursalesEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getSucursalesEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getSucursalesEvent, VSucursales.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VSucursales.nameService);
    }

    public void getSucursalesTurnoConfirmar(String str, String str2, UbicacionBean ubicacionBean) {
        getSucursalesTurno(str, str2, ubicacionBean);
    }

    public void getSucursalesTurnoSolicitud(String str, String str2, UbicacionBean ubicacionBean) {
        final GetSucursalesSolicitudEvent getSucursalesSolicitudEvent = new GetSucursalesSolicitudEvent();
        AnonymousClass4 r0 = new GetSucursalesTurnoRequest(this.b.getApplicationContext(), new GetSucursalesRequestBean(a(VGetSucursalesTurno.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetSucursalesBodyRequestBean(str2, str, ubicacionBean)), createErrorRequestServer(getSucursalesSolicitudEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetSucursalesResponseBean getSucursalesResponseBean = (GetSucursalesResponseBean) iBeanWS;
                getSucursalesSolicitudEvent.setResponseBean(getSucursalesResponseBean);
                getSucursalesSolicitudEvent.setBeanResponse(getSucursalesResponseBean);
                getSucursalesSolicitudEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getSucursalesSolicitudEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getSucursalesSolicitudEvent, VGetSucursalesTurno.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetSucursalesTurno.nameService);
    }

    public void getSucursalesTurno(String str, String str2, UbicacionBean ubicacionBean) {
        final GetSucursalesEvent getSucursalesEvent = new GetSucursalesEvent();
        AnonymousClass5 r0 = new GetSucursalesTurnoRequest(this.b.getApplicationContext(), new GetSucursalesRequestBean(a(VGetSucursalesTurno.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetSucursalesBodyRequestBean(str2, str, ubicacionBean)), createErrorRequestServer(getSucursalesEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetSucursalesResponseBean getSucursalesResponseBean = (GetSucursalesResponseBean) iBeanWS;
                getSucursalesEvent.setResponseBean(getSucursalesResponseBean);
                getSucursalesEvent.setBeanResponse(getSucursalesResponseBean);
                getSucursalesEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getSucursalesEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getSucursalesEvent, VGetSucursalesTurno.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetSucursalesTurno.nameService);
    }

    public void getProximasCuotas(String str, String str2, String str3) {
        final GetProximasCuotasCreditoEvent getProximasCuotasCreditoEvent = new GetProximasCuotasCreditoEvent();
        AnonymousClass6 r0 = new GetProximasCuotasCreditosRequest(this.b.getApplicationContext(), new GetProximasCuotasCreditosRequestBean(a(VProximasCuotasCreditos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetProximasCuotasCreditosBodyRequestBean(str, str2, str3)), createErrorRequestServer(getProximasCuotasCreditoEvent, TypeOption.INITIAL_VIEW)) {
            public void onUnknowError(Exception exc) {
            }

            public void onResponseBean(IBeanWS iBeanWS) {
                getProximasCuotasCreditoEvent.setBeanResponse((GetProximasCuotasResponseBean) iBeanWS);
                getProximasCuotasCreditoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getProximasCuotasCreditoEvent);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VProximasCuotasCreditos.nameService);
    }

    public void getDetalleCuotaTenenciaCredito(String str, String str2, String str3) {
        final GetDetalleCuotaTenenciaCreditoEvent getDetalleCuotaTenenciaCreditoEvent = new GetDetalleCuotaTenenciaCreditoEvent();
        AnonymousClass7 r0 = new GetDetalleCuotaTenenciaCreditoRequest(this.b.getApplicationContext(), new GetDetalleCuotaTenenciaCreditoRequestBean(a(VDCTendenciaCredito.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetDetalleCuotaTenenciaCreditoBodyRequestBean(str, str2, str3)), createErrorRequestServer(getDetalleCuotaTenenciaCreditoEvent, TypeOption.INITIAL_VIEW)) {
            public void onUnknowError(Exception exc) {
            }

            public void onResponseBean(IBeanWS iBeanWS) {
                getDetalleCuotaTenenciaCreditoEvent.setBeanResponse((GetDetalleCuotaTenenciaCreditoResponseBean) iBeanWS);
                getDetalleCuotaTenenciaCreditoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getDetalleCuotaTenenciaCreditoEvent);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VDCTendenciaCredito.nameService);
    }

    public void getCajerosDetalle(String str) {
        final GetCajerosDetalleEvent getCajerosDetalleEvent = new GetCajerosDetalleEvent();
        AnonymousClass8 r0 = new GetCajerosDetalleRequest(this.b.getApplicationContext(), new GetCajerosDetalleRequestBean(a(VCajerosDetalle.getData(EVersionServices.CURRENT)), new GetCajerosDetalleBodyRequestBean(str)), createErrorRequestServer(getCajerosDetalleEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getCajerosDetalleEvent.setBeanResponse((GetCajerosDetalleResponseBean) iBeanWS);
                getCajerosDetalleEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getCajerosDetalleEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getCajerosDetalleEvent, VCajerosDetalle.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VCajerosDetalle.nameService);
    }

    public void consDescripciones(boolean z, boolean z2) {
        final ConsDescripcionesEvent consDescripcionesEvent = new ConsDescripcionesEvent();
        consDescripcionesEvent.setFromLogin(z2);
        consDescripcionesEvent.setRetry(z);
        AnonymousClass9 r0 = new ConsDescripcionesRequest(this.b.getApplicationContext(), new ConsDescriptionRequestBean(a(VConsDescription.getData(EVersionServices.CURRENT)), new ConsDescriptionBodyRequestBean()), createErrorRequestServer(consDescripcionesEvent, TypeOption.INTERMDIATE_VIEW), "", "nup_mock") {
            public void onResponseBean(IBeanWS iBeanWS) {
                ConsDescriptionResponseBean consDescriptionResponseBean = (ConsDescriptionResponseBean) iBeanWS;
                consDescripcionesEvent.setBeanResponse(consDescriptionResponseBean);
                consDescripcionesEvent.setResult(TypeResult.OK);
                DataManager.this.sessionManager.setConsDescripciones(consDescriptionResponseBean.consDescriptionBodyResponseBean);
                DataManager.this.sendBusEvent(consDescripcionesEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, consDescripcionesEvent, VConsDescription.nameService);
            }
        };
        a((BaseRequest) r0, 7);
        if (this.sessionManager.getConsDescripciones() == null) {
            r0.sendRequest(VConsDescription.nameService);
        }
    }

    public void getPromocionesHome() {
        final GetPromocionesHomeEvent getPromocionesHomeEvent = new GetPromocionesHomeEvent();
        AnonymousClass10 r0 = new GetPromocionesHomeRequest(this.b.getApplicationContext(), new GetPromocionesHomeRequestBean(a(VPromocionesHome.getData(EVersionServices.CURRENT))), createErrorRequestServer(getPromocionesHomeEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetPromocionesHomeResponseBean getPromocionesHomeResponseBean = (GetPromocionesHomeResponseBean) iBeanWS;
                getPromocionesHomeEvent.setBeanResponse(getPromocionesHomeResponseBean);
                getPromocionesHomeEvent.setResult(TypeResult.OK);
                DataManager.this.sessionManager.setGetPromocionesHome(getPromocionesHomeResponseBean.getPromocionesHomeBodyResponseBean);
                DataManager.this.sendBusEvent(getPromocionesHomeEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getPromocionesHomeEvent, VPromocionesHome.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VPromocionesHome.nameService);
    }

    public void loginUnicoResend(List<LoginEvent> list) {
        for (LoginEvent sendBusEvent : list) {
            sendBusEvent(sendBusEvent);
        }
    }

    public void loginUnicoResendEvent(LoginEvent loginEvent) {
        sendBusEvent(loginEvent);
    }

    private void a(String str, String str2, String str3, String str4, String str5, String str6, Activity activity) {
        a(str, str2, str3, str4, str5, str6, Boolean.valueOf(true), Boolean.valueOf(false), activity);
    }

    private void a(String str, String str2, String str3, String str4, String str5, String str6, Boolean bool, Boolean bool2, Activity activity) {
        final String str7 = str;
        final String str8 = str2;
        final String str9 = str3;
        final String str10 = str4;
        final String str11 = str5;
        final String str12 = str6;
        final Boolean bool3 = bool;
        final Boolean bool4 = bool2;
        final Activity activity2 = activity;
        AnonymousClass11 r0 = new Runnable() {
            public void run() {
                final LoginEvent loginEvent = new LoginEvent();
                LoginUnicoBodyRequestBean loginUnicoBodyRequestBean = new LoginUnicoBodyRequestBean(str7, str8, str9, str10, str11, str12, DataManager.this.sessionManager.getToken());
                AnonymousClass1 r0 = new LoginUnicoRequest(DataManager.this.b.getApplicationContext(), new LoginUnicoRequestBean(DataManager.this.a(VLoginUnico.getData(EVersionServices.CURRENT), bool3, bool4, activity2), loginUnicoBodyRequestBean), DataManager.this.createErrorRequestServer(loginEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        LoginUnicoResponseBean loginUnicoResponseBean = (LoginUnicoResponseBean) iBeanWS;
                        DataManager.this.sessionManager.setLoginUnico(loginUnicoResponseBean.getLoginUnicoBodyResponseBean());
                        loginEvent.setBeanResponse(loginUnicoResponseBean);
                        if (loginEvent.getErrorBodyBean() == null || (loginEvent.getErrorBodyBean() != null && loginEvent.getErrorBodyBean().equals("0"))) {
                            loginEvent.setResult(TypeResult.OK);
                            loginEvent.setMessageToShow(loginUnicoResponseBean.getLoginUnicoBodyResponseBean().resDesc);
                            loginEvent.setTitleToShow(loginUnicoResponseBean.getLoginUnicoBodyResponseBean().resTitle);
                        }
                        DataManager.this.sendBusEvent(loginEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, loginEvent, VLoginUnico.nameService);
                    }
                };
                DataManager.this.a((BaseRequest) r0);
                r0.sendRequest(VLoginUnico.nameService);
            }
        };
        a((Runnable) r0, VLoginUnico.nameService);
    }

    public void loginUnicoTradicional(String str, String str2, String str3, String str4, Activity activity) {
        String str5 = "";
        String str6 = "";
        String str7 = str;
        String str8 = str2;
        String str9 = str3;
        a(str7, str8, str5, str9, str6, str4.replace("/", ""), Boolean.valueOf(true), Boolean.valueOf(false), activity);
    }

    public void loginUnicoSinonimo(String str, String str2, String str3, String str4, Activity activity) {
        String str5 = str;
        String str6 = str3;
        String str7 = str4;
        a(str5, str6, "", str7, "", str2.replace("/", ""), activity);
    }

    public void loginUnicoSinonimoCallToAction(String str, String str2, String str3, String str4, Activity activity) {
        String str5 = str;
        String str6 = str3;
        a(str5, str6, "", str4, str4, str2.replace("/", ""), activity);
    }

    public void loginUnicoClaveVencida(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, Activity activity) {
        a(str, str2, str3, str5, str6, str8.replace("/", ""), activity);
    }

    public void loginUnicoCambioUsuario(String str, String str2, String str3, String str4, String str5, String str6, Activity activity) {
        String replace = str6.replace("/", "");
        a(str, str2, "", str3, str4, replace, activity);
    }

    public void loginUnicoAltaUsuario(String str, String str2, String str3, String str4, String str5, Activity activity) {
        String str6 = str;
        String str7 = str2;
        a(str6, str7, "", str3, str3, str5.replace("/", ""), activity);
    }

    public void loginUnicoPrimerIngreso(String str, String str2, String str3, String str4, String str5, String str6, Activity activity) {
        a(str, str2, str3, str4, str5, str6.replace("/", ""), activity);
    }

    public void consultaLeyendasGenerales(String str) {
        String str2;
        String str3;
        final ConsultaLeyendasEvent consultaLeyendasEvent = new ConsultaLeyendasEvent();
        ConsultaLeyendasBodyRequestBean consultaLeyendasBodyRequestBean = new ConsultaLeyendasBodyRequestBean(str);
        if (str.equalsIgnoreCase("OLV_CLAVE")) {
            str2 = "";
            str3 = "";
        } else {
            str2 = this.sessionManager.getSession();
            str3 = this.sessionManager.getNup();
        }
        AnonymousClass12 r0 = new ConsultaLeyendasRequest(this.b.getApplicationContext(), new ConsultaLeyendasRequestBean(a(VConsultaLeyendas.getData(EVersionServices.CURRENT), str2, str3), consultaLeyendasBodyRequestBean), createErrorRequestServer(consultaLeyendasEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaLeyendasEvent.setBeanResponse((ConsultaLeyendasResponseBean) iBeanWS);
                consultaLeyendasEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(consultaLeyendasEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, consultaLeyendasEvent, "consultaLeyendasGenerales");
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest("consultaLeyendasGenerales");
    }

    public void consultaLeyendasGeneralesPrimerIngreso(String str) {
        String str2;
        String str3;
        final ConsultaLeyendasPrimerIngresoEvent consultaLeyendasPrimerIngresoEvent = new ConsultaLeyendasPrimerIngresoEvent();
        ConsultaLeyendasBodyRequestBean consultaLeyendasBodyRequestBean = new ConsultaLeyendasBodyRequestBean(str);
        if (str.equalsIgnoreCase("OLV_CLAVE")) {
            str2 = "";
            str3 = "";
        } else {
            str2 = this.sessionManager.getSession();
            str3 = this.sessionManager.getNup();
        }
        AnonymousClass13 r0 = new ConsultaLeyendasRequest(this.b.getApplicationContext(), new ConsultaLeyendasRequestBean(a(VConsultaLeyendas.getData(EVersionServices.CURRENT), str2, str3), consultaLeyendasBodyRequestBean), createErrorRequestServer(consultaLeyendasPrimerIngresoEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaLeyendasPrimerIngresoEvent.setBeanResponse((ConsultaLeyendasResponseBean) iBeanWS);
                consultaLeyendasPrimerIngresoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(consultaLeyendasPrimerIngresoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, consultaLeyendasPrimerIngresoEvent, "consultaLeyendasGenerales");
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest("consultaLeyendasGenerales");
    }

    public void aceptaTerminos(String str) {
        final AceptaTerminosEvent aceptaTerminosEvent = new AceptaTerminosEvent();
        AnonymousClass14 r0 = new AceptaTerminosRequest(this.b.getApplicationContext(), new AceptaTerminosRequestBean(a(VAceptaTerminos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), str), new AceptaTerminosBodyRequestBean(str)), createErrorRequestServer(aceptaTerminosEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                aceptaTerminosEvent.setBeanResponse((AceptaTerminosResponseBean) iBeanWS);
                aceptaTerminosEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(aceptaTerminosEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, aceptaTerminosEvent, VAceptaTerminos.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VAceptaTerminos.nameService);
    }

    public void registrarSuscripcionMyA(String str, String str2, String str3, String str4) {
        final RegistrarSuscripcionEvent registrarSuscripcionEvent = new RegistrarSuscripcionEvent();
        AnonymousClass15 r0 = new RegistrarSuscripcionMyARequest(this.b.getApplicationContext(), new RegistrarSuscripcionRequestBean(a(VRegistrarSuscripcion.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), str), new RegistrarSuscripcionBodyRequestBean(str, str2, str3, str4)), createErrorRequestServer(registrarSuscripcionEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                registrarSuscripcionEvent.setBeanResponse((RegistrarSuscripcionResponseBean) iBeanWS);
                registrarSuscripcionEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(registrarSuscripcionEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, registrarSuscripcionEvent, VRegistrarSuscripcion.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VRegistrarSuscripcion.nameService);
    }

    public void modificarSuscripcionMyA(Destinos destinos) {
        final ModificarSuscripcionEvent modificarSuscripcionEvent = new ModificarSuscripcionEvent();
        String nup = this.sessionManager.getNup();
        String session = this.sessionManager.getSession();
        ModificarSuscripcionnBodyRequestBean modificarSuscripcionnBodyRequestBean = new ModificarSuscripcionnBodyRequestBean(destinos);
        modificarSuscripcionnBodyRequestBean.nup = nup;
        AnonymousClass16 r0 = new ModificarSuscripcionMyARequest(this.b.getApplicationContext(), new ModificarSuscripcionRequestBean(a(VModificarSuscripcion.getData(EVersionServices.CURRENT), session, nup), modificarSuscripcionnBodyRequestBean), createErrorRequestServer(modificarSuscripcionEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                modificarSuscripcionEvent.setBeanResponse((ModificarSuscripcionResponseBean) iBeanWS);
                modificarSuscripcionEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(modificarSuscripcionEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, modificarSuscripcionEvent, VModificarSuscripcion.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VModificarSuscripcion.nameService);
    }

    public void logOutSession() {
        logOutSession("", null);
    }

    public void logOutSession(String str, ListaRespuestas listaRespuestas) {
        final LogOutEvent logOutEvent = new LogOutEvent();
        String nup = this.sessionManager.getNup();
        String session = this.sessionManager.getSession();
        if (session != null && nup != null && !session.equalsIgnoreCase("") && !nup.equalsIgnoreCase("")) {
            AnonymousClass17 r0 = new LogOutRequest(this.b.getApplicationContext(), new LogOutRequestBean(a(VLogOut.getData(EVersionServices.CURRENT), session, nup), new LogOutBodyRequestBean(session, str, listaRespuestas)), createErrorRequestServer(logOutEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    logOutEvent.setBeanResponse((LogOutResponseBean) iBeanWS);
                    logOutEvent.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(logOutEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, logOutEvent, VLogOut.nameService);
                    DataManager.this.sessionManager.setLoginUnico(null);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VLogOut.nameService);
        }
        this.sessionManager.setCustomErrorLogging("", "", "");
        this.sessionManager.cleanPrivateData();
    }

    public void getNumerosUtiles() {
        final GetNumerosUtilesEvent getNumerosUtilesEvent = new GetNumerosUtilesEvent();
        AnonymousClass18 r0 = new GetNumerosUtilesRequest(this.b.getApplicationContext(), new GetNumerosUtilesRequestBean(a(VNumerosUtiles.getData(EVersionServices.CURRENT)), new GetNumerosUtilesBodyRequestBean()), createErrorRequestServer(getNumerosUtilesEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getNumerosUtilesEvent.setBeanResponse((GetNumerosUtilesResponseBean) iBeanWS);
                getNumerosUtilesEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getNumerosUtilesEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getNumerosUtilesEvent, VNumerosUtiles.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VNumerosUtiles.nameService);
    }

    public void getMovCtas(MovCtasBodyRequestBean movCtasBodyRequestBean) {
        try {
            final MovCtasEvent movCtasEvent = new MovCtasEvent();
            MovCtasRequestBean movCtasRequestBean = new MovCtasRequestBean();
            movCtasRequestBean.headerBean = a(VMovCtas.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            movCtasRequestBean.movCtasBodyRequestBean = movCtasBodyRequestBean;
            AnonymousClass19 r0 = new MovCtasRequest(this.b.getApplicationContext(), movCtasRequestBean, createErrorRequestServer(movCtasEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    MovCtasResponseBean movCtasResponseBean = (MovCtasResponseBean) iBeanWS;
                    movCtasEvent.setBeanResponse(movCtasResponseBean);
                    movCtasEvent.setResult(TypeResult.OK);
                    movCtasEvent.setResponse(movCtasResponseBean);
                    DataManager.this.sendBusEvent(movCtasEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, movCtasEvent, VMovCtas.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VMovCtas.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void consultaMandatos(ConsultaMandatosExtEnvBodyRequestBean consultaMandatosExtEnvBodyRequestBean) {
        try {
            final ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent = new ConsultaMandatosExtEnvEvent();
            ConsultaMandatosExtEnvRequestBean consultaMandatosExtEnvRequestBean = new ConsultaMandatosExtEnvRequestBean();
            consultaMandatosExtEnvRequestBean.headerBean = a(VConsultaMandatosExtEnv.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            consultaMandatosExtEnvRequestBean.consultaMandatosExtEnvBodyRequestBean = consultaMandatosExtEnvBodyRequestBean;
            AnonymousClass20 r0 = new ConsultaMandatosExtEnvRequest(this.b.getApplicationContext(), consultaMandatosExtEnvRequestBean, createErrorRequestServer(consultaMandatosExtEnvEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    ConsultaMandatosExtEnvResponseBean consultaMandatosExtEnvResponseBean = (ConsultaMandatosExtEnvResponseBean) iBeanWS;
                    consultaMandatosExtEnvEvent.setBeanResponse(consultaMandatosExtEnvResponseBean);
                    consultaMandatosExtEnvEvent.setResult(TypeResult.OK);
                    consultaMandatosExtEnvEvent.setResponse(consultaMandatosExtEnvResponseBean);
                    DataManager.this.sendBusEvent(consultaMandatosExtEnvEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, consultaMandatosExtEnvEvent, VConsultaMandatosExtEnv.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VConsultaMandatosExtEnv.nameService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generaMandato(final GeneraMandatoEnvBodyRequestBean generaMandatoEnvBodyRequestBean, final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                try {
                    final GeneraMandatoExtEnvEvent generaMandatoExtEnvEvent = new GeneraMandatoExtEnvEvent();
                    GeneraMandatoExtEnvRequestBean generaMandatoExtEnvRequestBean = new GeneraMandatoExtEnvRequestBean();
                    generaMandatoExtEnvRequestBean.headerBean = DataManager.this.a(VGeneraMandatoExtEnv.getData(EVersionServices.CURRENT), DataManager.this.sessionManager.getSession(), DataManager.this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true), activity);
                    generaMandatoExtEnvRequestBean.generaMandatoEnvBodyRequestBean = generaMandatoEnvBodyRequestBean;
                    final AnonymousClass1 r0 = new GeneraMandatoExtEnvRequest(DataManager.this.b.getApplicationContext(), generaMandatoExtEnvRequestBean, DataManager.this.createErrorRequestServer(generaMandatoExtEnvEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                        public void onResponseBean(IBeanWS iBeanWS) {
                            GeneraMandatoExtEnvResponseBean generaMandatoExtEnvResponseBean = (GeneraMandatoExtEnvResponseBean) iBeanWS;
                            generaMandatoExtEnvEvent.setBeanResponse(generaMandatoExtEnvResponseBean);
                            generaMandatoExtEnvEvent.setResult(TypeResult.OK);
                            generaMandatoExtEnvEvent.setResponse(generaMandatoExtEnvResponseBean);
                            DataManager.this.sendBusEvent(generaMandatoExtEnvEvent);
                        }

                        public void onUnknowError(Exception exc) {
                            DataManager.this.createUnknowErrorEventProcesor(exc, generaMandatoExtEnvEvent, VGeneraMandatoExtEnv.nameService);
                        }
                    };
                    DataManager.this.d.post(new Runnable() {
                        public void run() {
                            DataManager.this.a((BaseRequest) r0);
                            r0.sendRequest(VGeneraMandatoExtEnv.nameService);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, VGeneraMandatoExtEnv.nameService);
    }

    public void generaMandato(final GeneraMandatoExtBodyRequestBean generaMandatoExtBodyRequestBean, final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                try {
                    final GeneraMandatoExtEnvEvent generaMandatoExtEnvEvent = new GeneraMandatoExtEnvEvent();
                    GeneraMandatoExtRequestBean generaMandatoExtRequestBean = new GeneraMandatoExtRequestBean();
                    generaMandatoExtRequestBean.headerBean = DataManager.this.a(VGeneraMandatoExtEnv.getData(EVersionServices.CURRENT), DataManager.this.sessionManager.getSession(), DataManager.this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true), activity);
                    generaMandatoExtRequestBean.generaMandatoExtBodyRequestBean = generaMandatoExtBodyRequestBean;
                    final AnonymousClass1 r0 = new GeneraMandatoExtRequest(DataManager.this.b.getApplicationContext(), generaMandatoExtRequestBean, DataManager.this.createErrorRequestServer(generaMandatoExtEnvEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                        public void onResponseBean(IBeanWS iBeanWS) {
                            GeneraMandatoExtEnvResponseBean generaMandatoExtEnvResponseBean = (GeneraMandatoExtEnvResponseBean) iBeanWS;
                            generaMandatoExtEnvEvent.setBeanResponse(generaMandatoExtEnvResponseBean);
                            generaMandatoExtEnvEvent.setResult(TypeResult.OK);
                            generaMandatoExtEnvEvent.setResponse(generaMandatoExtEnvResponseBean);
                            DataManager.this.sendBusEvent(generaMandatoExtEnvEvent);
                        }

                        public void onUnknowError(Exception exc) {
                            DataManager.this.createUnknowErrorEventProcesor(exc, generaMandatoExtEnvEvent, VGeneraMandatoExtEnv.nameService);
                        }
                    };
                    DataManager.this.d.post(new Runnable() {
                        public void run() {
                            DataManager.this.a((BaseRequest) r0);
                            r0.sendRequest(VGeneraMandatoExtEnv.nameService);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, VGeneraMandatoExtEnv.nameService);
    }

    public void cancelaMandato(CancelaMandatoExtEnvBodyRequestBean cancelaMandatoExtEnvBodyRequestBean) {
        try {
            final CancelaMandatoExtEnvEvent cancelaMandatoExtEnvEvent = new CancelaMandatoExtEnvEvent();
            CancelaMandatoExtEnvRequestBean cancelaMandatoExtEnvRequestBean = new CancelaMandatoExtEnvRequestBean();
            cancelaMandatoExtEnvRequestBean.headerBean = a(VCancelaMandatoExtEnv.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            cancelaMandatoExtEnvRequestBean.cancelaMandatoExtEnvBodyRequestBean = cancelaMandatoExtEnvBodyRequestBean;
            AnonymousClass23 r0 = new CancelaMandatoExtEnvRequest(this.b.getApplicationContext(), cancelaMandatoExtEnvRequestBean, createErrorRequestServer(cancelaMandatoExtEnvEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    CancelaMandatoExtEnvResponseBean cancelaMandatoExtEnvResponseBean = (CancelaMandatoExtEnvResponseBean) iBeanWS;
                    cancelaMandatoExtEnvEvent.setBeanResponse(cancelaMandatoExtEnvResponseBean);
                    cancelaMandatoExtEnvEvent.setResult(TypeResult.OK);
                    cancelaMandatoExtEnvEvent.setResponse(cancelaMandatoExtEnvResponseBean);
                    DataManager.this.sendBusEvent(cancelaMandatoExtEnvEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, cancelaMandatoExtEnvEvent, VCancelaMandatoExtEnv.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VCancelaMandatoExtEnv.nameService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(final AMAgendadosEnvEfeBodyRequestBean aMAgendadosEnvEfeBodyRequestBean, final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                try {
                    final AMAgendadosEnvEfeEvent aMAgendadosEnvEfeEvent = new AMAgendadosEnvEfeEvent();
                    AMAgendadosEnvEfeRequestBean aMAgendadosEnvEfeRequestBean = new AMAgendadosEnvEfeRequestBean();
                    aMAgendadosEnvEfeRequestBean.headerBean = DataManager.this.a(VABMAgendadosEnvEfe.getData(EVersionServices.CURRENT), DataManager.this.sessionManager.getSession(), DataManager.this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true), activity);
                    aMAgendadosEnvEfeRequestBean.amAgendadosEnvEfeBodyRequestBean = aMAgendadosEnvEfeBodyRequestBean;
                    final AnonymousClass1 r0 = new AMAgendadosEnvEfeRequest(DataManager.this.b.getApplicationContext(), aMAgendadosEnvEfeRequestBean, DataManager.this.createErrorRequestServer(aMAgendadosEnvEfeEvent, TypeOption.INTERMDIATE_VIEW)) {
                        public void onResponseBean(IBeanWS iBeanWS) {
                            AMAgendadosEnvEfeResponseBean aMAgendadosEnvEfeResponseBean = (AMAgendadosEnvEfeResponseBean) iBeanWS;
                            aMAgendadosEnvEfeEvent.setBeanResponse(aMAgendadosEnvEfeResponseBean);
                            aMAgendadosEnvEfeEvent.setResult(TypeResult.OK);
                            aMAgendadosEnvEfeEvent.setResponse(aMAgendadosEnvEfeResponseBean);
                            DataManager.this.sendBusEvent(aMAgendadosEnvEfeEvent);
                        }

                        public void onUnknowError(Exception exc) {
                            DataManager.this.createUnknowErrorEventProcesor(exc, aMAgendadosEnvEfeEvent, VABMAgendadosEnvEfe.nameService);
                        }
                    };
                    DataManager.this.d.post(new Runnable() {
                        public void run() {
                            DataManager.this.a((BaseRequest) r0);
                            r0.sendRequest(VABMAgendadosEnvEfe.nameService);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, VABMAgendadosEnvEfe.nameService);
    }

    private void a(int i, BAgendadosEnvEfeBodyRequestBean bAgendadosEnvEfeBodyRequestBean) {
        try {
            final BAgendadosEnvEfeEvent bAgendadosEnvEfeEvent = new BAgendadosEnvEfeEvent();
            bAgendadosEnvEfeEvent.setDestinatarioPosition(i);
            BAgendadosEnvEfeRequestBean bAgendadosEnvEfeRequestBean = new BAgendadosEnvEfeRequestBean();
            bAgendadosEnvEfeRequestBean.headerBean = a(VABMAgendadosEnvEfe.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            bAgendadosEnvEfeRequestBean.bAgendadosEnvEfeBodyRequestBean = bAgendadosEnvEfeBodyRequestBean;
            AnonymousClass25 r0 = new BAgendadosEnvEfeRequest(this.b.getApplicationContext(), bAgendadosEnvEfeRequestBean, createErrorRequestServer(bAgendadosEnvEfeEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    BAgendadosEnvEfeResponseBean bAgendadosEnvEfeResponseBean = (BAgendadosEnvEfeResponseBean) iBeanWS;
                    bAgendadosEnvEfeEvent.setBeanResponse(bAgendadosEnvEfeResponseBean);
                    bAgendadosEnvEfeEvent.setResult(TypeResult.OK);
                    bAgendadosEnvEfeEvent.setResponse(bAgendadosEnvEfeResponseBean);
                    DataManager.this.sendBusEvent(bAgendadosEnvEfeEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, bAgendadosEnvEfeEvent, VABMAgendadosEnvEfe.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VABMAgendadosEnvEfe.nameService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ADestinatario(AMAgendadosEnvEfeDestinatarioBean aMAgendadosEnvEfeDestinatarioBean, Activity activity) {
        a(new AMAgendadosEnvEfeBodyRequestBean("A", aMAgendadosEnvEfeDestinatarioBean), activity);
    }

    public void BDestinatario(int i, BAgendadosEnvEfeDestinatarioBean bAgendadosEnvEfeDestinatarioBean) {
        a(i, new BAgendadosEnvEfeBodyRequestBean("B", bAgendadosEnvEfeDestinatarioBean));
    }

    public void MDestinatario(AMAgendadosEnvEfeDestinatarioBean aMAgendadosEnvEfeDestinatarioBean, Activity activity) {
        a(new AMAgendadosEnvEfeBodyRequestBean("M", aMAgendadosEnvEfeDestinatarioBean), activity);
    }

    public void getDetCtas(String str, String str2, String str3) {
        MovCtasBodyRequestBean movCtasBodyRequestBean = new MovCtasBodyRequestBean();
        movCtasBodyRequestBean.accountRequestBean = new AccountRequestBean(str, str2, str3);
        getDetCtas(movCtasBodyRequestBean);
    }

    public void getDetCtas(MovCtasBodyRequestBean movCtasBodyRequestBean) {
        try {
            final DetCtasEvent detCtasEvent = new DetCtasEvent();
            MovCtasRequestBean movCtasRequestBean = new MovCtasRequestBean();
            movCtasRequestBean.headerBean = a(VDetCtas.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            movCtasRequestBean.movCtasBodyRequestBean = movCtasBodyRequestBean;
            AnonymousClass26 r0 = new MovCtasRequest(this.b.getApplicationContext(), movCtasRequestBean, createErrorRequestServer(detCtasEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    detCtasEvent.setBeanResponse((MovCtasResponseBean) iBeanWS);
                    detCtasEvent.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(detCtasEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, detCtasEvent, VDetCtas.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VDetCtas.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getLimitesExtraccion() {
        GetLimitesExtraccionBodyRequestBean getLimitesExtraccionBodyRequestBean = new GetLimitesExtraccionBodyRequestBean();
        try {
            final GetLimitesExtraccionEvent getLimitesExtraccionEvent = new GetLimitesExtraccionEvent();
            GetLimitesExtraccionRequestBean getLimitesExtraccionRequestBean = new GetLimitesExtraccionRequestBean();
            getLimitesExtraccionRequestBean.headerBean = a(VGetLimitesExtraccion.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            getLimitesExtraccionRequestBean.getLimitesExtraccionBodyRequestBean = getLimitesExtraccionBodyRequestBean;
            AnonymousClass27 r1 = new GetLimitesExtraccionRequest(this.b.getApplicationContext(), getLimitesExtraccionRequestBean, createErrorRequestServer(getLimitesExtraccionEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    getLimitesExtraccionEvent.setBeanResponse((GetLimitesExtraccionResponseBean) iBeanWS);
                    getLimitesExtraccionEvent.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(getLimitesExtraccionEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, getLimitesExtraccionEvent, VGetLimitesExtraccion.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VGetLimitesExtraccion.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void cambiarLimite(LimiteExtraccionBean limiteExtraccionBean, String str, Activity activity) {
        CambiarLimiteBodyRequestBean cambiarLimiteBodyRequestBean = new CambiarLimiteBodyRequestBean(limiteExtraccionBean, str);
        try {
            CambiarLimiteEvent cambiarLimiteEvent = new CambiarLimiteEvent();
            CambiarLimiteRequestBean cambiarLimiteRequestBean = new CambiarLimiteRequestBean();
            cambiarLimiteRequestBean.headerBean = a(VCambiarLimite.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true), activity);
            cambiarLimiteRequestBean.cambiarLimiteBodyRequestBean = cambiarLimiteBodyRequestBean;
            final CambiarLimiteEvent cambiarLimiteEvent2 = cambiarLimiteEvent;
            AnonymousClass28 r1 = new CambiarLimiteRequest(this.b.getApplicationContext(), cambiarLimiteRequestBean, createErrorRequestServer(cambiarLimiteEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    cambiarLimiteEvent2.setBeanResponse((CambiarLimiteResponseBean) iBeanWS);
                    cambiarLimiteEvent2.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(cambiarLimiteEvent2);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, cambiarLimiteEvent2, VCambiarLimite.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VCambiarLimite.nameService);
        } catch (Exception e) {
            Exception exc = e;
            exc.fillInStackTrace();
            exc.printStackTrace();
        }
    }

    public void abmAlias(String str, String str2, String str3, String str4, CuentaShortBean cuentaShortBean) {
        abmAlias(str, str2, null, str3, str4, cuentaShortBean);
    }

    public void abmAlias(String str, String str2, String str3, String str4, String str5, CuentaShortBean cuentaShortBean) {
        final ABMAliasEvent aBMAliasEvent = new ABMAliasEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VABMAlias.getData(EVersionServices.CURRENT), session, nup);
        ABMAliasBodyRequestBean aBMAliasBodyRequestBean = new ABMAliasBodyRequestBean(str, str2, str3, str4, str5, cuentaShortBean);
        AnonymousClass29 r0 = new ABMAliasRequest(applicationContext, new ABMAliasRequestBean(a2, aBMAliasBodyRequestBean), createErrorRequestServer(aBMAliasEvent, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                ABMAliasResponseBean aBMAliasResponseBean = (ABMAliasResponseBean) iBeanWS;
                aBMAliasEvent.setBeanResponse(aBMAliasResponseBean);
                aBMAliasEvent.setResponse(aBMAliasResponseBean);
                aBMAliasEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(aBMAliasEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, aBMAliasEvent, VABMAlias.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VABMAlias.nameService);
    }

    public void getPagoServicios(final PagoServiciosBodyRequestBean pagoServiciosBodyRequestBean, final Activity activity, final boolean z) {
        a((Runnable) new Runnable() {
            public void run() {
                try {
                    final PagoServiciosEvent pagoServiciosEvent = new PagoServiciosEvent();
                    PagoServiciosRequestBean pagoServiciosRequestBean = new PagoServiciosRequestBean();
                    pagoServiciosRequestBean.headerBean = DataManager.this.a(VPagoServicio.getData(EVersionServices.CURRENT), DataManager.this.sessionManager.getSession(), DataManager.this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true ^ z), activity);
                    if (z) {
                        pagoServiciosRequestBean.headerBean.accesoPublico = "S";
                    } else {
                        pagoServiciosRequestBean.headerBean.accesoPublico = "N";
                    }
                    pagoServiciosRequestBean.pagoServiciosBodyRequestBean = pagoServiciosBodyRequestBean;
                    final AnonymousClass1 r0 = new PagoServiciosRequest(DataManager.this.b.getApplicationContext(), pagoServiciosRequestBean, DataManager.this.createErrorRequestServer(pagoServiciosEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                        public void onResponseBean(IBeanWS iBeanWS) {
                            PagoServiciosResponseBean pagoServiciosResponseBean = (PagoServiciosResponseBean) iBeanWS;
                            pagoServiciosEvent.setBeanResponse(pagoServiciosResponseBean);
                            pagoServiciosEvent.setResult(TypeResult.OK);
                            pagoServiciosEvent.setResponse(pagoServiciosResponseBean);
                            DataManager.this.sendBusEvent(pagoServiciosEvent);
                        }

                        public void onUnknowError(Exception exc) {
                            DataManager.this.createUnknowErrorEventProcesor(exc, pagoServiciosEvent, VPagoServicio.nameService);
                        }
                    };
                    DataManager.this.d.post(new Runnable() {
                        public void run() {
                            DataManager.this.a((BaseRequest) r0);
                            r0.sendRequest(VPagoServicio.nameService);
                        }
                    });
                } catch (Exception e) {
                    e.fillInStackTrace();
                    e.printStackTrace();
                }
            }
        }, VABMAgendadosEnvEfe.nameService);
    }

    public void recargaCelulares(final RecargaCelularesBodyRequestBean recargaCelularesBodyRequestBean, Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                try {
                    final RecargaCelularesEvent recargaCelularesEvent = new RecargaCelularesEvent();
                    RecargaCelularesRequestBean recargaCelularesRequestBean = new RecargaCelularesRequestBean();
                    recargaCelularesRequestBean.headerBean = DataManager.this.a(VRecargaCelulares.getData(EVersionServices.CURRENT), DataManager.this.sessionManager.getSession(), DataManager.this.sessionManager.getNup());
                    recargaCelularesRequestBean.recargaCelularesBodyRequestBean = recargaCelularesBodyRequestBean;
                    final AnonymousClass1 r0 = new RecargaCelularesRequest(DataManager.this.b.getApplicationContext(), recargaCelularesRequestBean, DataManager.this.createErrorRequestServer(recargaCelularesEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                        public void onResponseBean(IBeanWS iBeanWS) {
                            RecargaCelularesResponseBean recargaCelularesResponseBean = (RecargaCelularesResponseBean) iBeanWS;
                            recargaCelularesEvent.setBeanResponse(recargaCelularesResponseBean);
                            recargaCelularesEvent.setResult(TypeResult.OK);
                            recargaCelularesEvent.setResponse(recargaCelularesResponseBean);
                            DataManager.this.sendBusEvent(recargaCelularesEvent);
                        }

                        public void onUnknowError(Exception exc) {
                            DataManager.this.createUnknowErrorEventProcesor(exc, recargaCelularesEvent, VRecargaCelulares.nameService);
                        }
                    };
                    DataManager.this.d.post(new Runnable() {
                        public void run() {
                            DataManager.this.a((BaseRequest) r0);
                            r0.sendRequest(VRecargaCelulares.nameService);
                        }
                    });
                } catch (Exception e) {
                    e.fillInStackTrace();
                    e.printStackTrace();
                }
            }
        }, VABMAgendadosEnvEfe.nameService);
    }

    public void getCnsDeuda(CnsDeudaBodyRequestBean cnsDeudaBodyRequestBean) {
        try {
            final CnsDeudaEvent cnsDeudaEvent = new CnsDeudaEvent();
            CnsDeudaRequestBean cnsDeudaRequestBean = new CnsDeudaRequestBean();
            cnsDeudaRequestBean.headerBean = a(VCndDeuda.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            cnsDeudaRequestBean.cnsDeudaBodyRequestBean = cnsDeudaBodyRequestBean;
            AnonymousClass32 r0 = new CnsDeudaRequest(this.b.getApplicationContext(), cnsDeudaRequestBean, createErrorRequestServer(cnsDeudaEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    CnsDeudaResponseBean cnsDeudaResponseBean = (CnsDeudaResponseBean) iBeanWS;
                    cnsDeudaEvent.setTypeOption(TypeOption.INITIAL_VIEW);
                    cnsDeudaEvent.setBeanResponse(cnsDeudaResponseBean);
                    cnsDeudaEvent.setResult(TypeResult.OK);
                    cnsDeudaEvent.setResponse(cnsDeudaResponseBean);
                    DataManager.this.sendBusEvent(cnsDeudaEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, cnsDeudaEvent, VCndDeuda.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VCndDeuda.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void obtenerEstadoSuscripcionMyA() {
        final ObtenerSuscripcionEvent obtenerSuscripcionEvent = new ObtenerSuscripcionEvent();
        ObtenerEstadoSuscripcionMyARequestBean obtenerEstadoSuscripcionMyARequestBean = new ObtenerEstadoSuscripcionMyARequestBean();
        obtenerEstadoSuscripcionMyARequestBean.headerBean = a(VObtenerEstadoSuscripcion.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        ObtenerEstadoSuscripcionMyABodyRequestBean obtenerEstadoSuscripcionMyABodyRequestBean = new ObtenerEstadoSuscripcionMyABodyRequestBean();
        obtenerEstadoSuscripcionMyABodyRequestBean.setNup(this.sessionManager.getNup());
        obtenerEstadoSuscripcionMyARequestBean.obtenerEstadoSuscripcionMyABodyRequestBean = obtenerEstadoSuscripcionMyABodyRequestBean;
        AnonymousClass33 r0 = new ObtenerEstadoSuscripcionMyARequest(this.b.getApplicationContext(), obtenerEstadoSuscripcionMyARequestBean, createErrorRequestServer(obtenerSuscripcionEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                obtenerSuscripcionEvent.setResult(TypeResult.SERVER_ERROR);
                obtenerSuscripcionEvent.setMessageToShow(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE);
                obtenerSuscripcionEvent.setTitleToShow("");
                DataManager.this.sendBusEvent(obtenerSuscripcionEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, obtenerSuscripcionEvent, VObtenerEstadoSuscripcion.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VObtenerEstadoSuscripcion.nameService);
    }

    public void consultaSuscripcionMyA(String str, String str2) {
        final ConsultaSuscripcionMyAEvent consultaSuscripcionMyAEvent = new ConsultaSuscripcionMyAEvent();
        ConsultaSuscripcionMyARequestBean consultaSuscripcionMyARequestBean = new ConsultaSuscripcionMyARequestBean();
        consultaSuscripcionMyARequestBean.headerBean = a(VConsultaSuscripcionMyA.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        ConsultaSuscripcionMyABodyRequestBean consultaSuscripcionMyABodyRequestBean = new ConsultaSuscripcionMyABodyRequestBean();
        ListaProductos listaProductos = new ListaProductos();
        Producto producto = new Producto();
        producto.setNumeroProducto(str);
        ListaMensajes listaMensajes = new ListaMensajes();
        ArrayList arrayList = new ArrayList();
        Mensaje mensaje = new Mensaje();
        mensaje.setNroMensaje(str2);
        arrayList.add(mensaje);
        listaMensajes.setListaMensajes(arrayList);
        producto.setListaMensajes(listaMensajes);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(producto);
        listaProductos.setProductos(arrayList2);
        consultaSuscripcionMyABodyRequestBean.setNup(this.sessionManager.getNup());
        consultaSuscripcionMyABodyRequestBean.setListaProductos(listaProductos);
        consultaSuscripcionMyARequestBean.consultaSuscripcionMyABodyRequestBean = consultaSuscripcionMyABodyRequestBean;
        AnonymousClass34 r0 = new ConsultaSuscripcionMyARequest(this.b.getApplicationContext(), consultaSuscripcionMyARequestBean, createErrorRequestServer(consultaSuscripcionMyAEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaSuscripcionMyAEvent.setBeanResponse((ConsultaSuscripcionMyAResponeBean) iBeanWS);
                consultaSuscripcionMyAEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(consultaSuscripcionMyAEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, consultaSuscripcionMyAEvent, VConsultaSuscripcionMyA.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VConsultaSuscripcionMyA.nameService);
    }

    public void actualizarMensajesMyA(ActualizarMensajesMyABodyRequestBean actualizarMensajesMyABodyRequestBean) {
        final ActualizarMensajesMyAEvent actualizarMensajesMyAEvent = new ActualizarMensajesMyAEvent();
        ActualizarMensajesMyARequestBean actualizarMensajesMyARequestBean = new ActualizarMensajesMyARequestBean();
        actualizarMensajesMyARequestBean.headerBean = a(VActualizarMensajesMyA.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        actualizarMensajesMyABodyRequestBean.setNup(this.sessionManager.getNup());
        actualizarMensajesMyARequestBean.actualizarMensajesMyABodyRequestBean = actualizarMensajesMyABodyRequestBean;
        AnonymousClass35 r0 = new ActualizarMensajesMyARequest(this.b.getApplicationContext(), actualizarMensajesMyARequestBean, createErrorRequestServer(actualizarMensajesMyAEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                actualizarMensajesMyAEvent.setBeanResponse((ActualizarMensajesMyARequestResponseBean) iBeanWS);
                actualizarMensajesMyAEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(actualizarMensajesMyAEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, actualizarMensajesMyAEvent, VActualizarMensajesMyA.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VActualizarMensajesMyA.nameService);
    }

    public void consultaSolicitudCrediticiaProd(ConsultaSolicitudCrediticiaBodyRequestBeanProd consultaSolicitudCrediticiaBodyRequestBeanProd) {
        final ConsultaSolicitudCrediticiaEventProd consultaSolicitudCrediticiaEventProd = new ConsultaSolicitudCrediticiaEventProd();
        ConsultaSolicitudCrediticiaRequestBeanProd consultaSolicitudCrediticiaRequestBeanProd = new ConsultaSolicitudCrediticiaRequestBeanProd();
        consultaSolicitudCrediticiaRequestBeanProd.headerBean = a(VConsultaSolicitudCrediticiaProd.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        consultaSolicitudCrediticiaRequestBeanProd.consultaSolicitudCrediticiaBodyRequestBeanProd = consultaSolicitudCrediticiaBodyRequestBeanProd;
        AnonymousClass36 r0 = new ConsultaSolicitudCrediticiaRequestProd(this.b.getApplicationContext(), consultaSolicitudCrediticiaRequestBeanProd, createErrorRequestServer(consultaSolicitudCrediticiaEventProd, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaSolicitudCrediticiaEventProd.setBeanResponse((ConsultaSolicitudCrediticiaResponseBeanProd) iBeanWS);
                consultaSolicitudCrediticiaEventProd.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(consultaSolicitudCrediticiaEventProd);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, consultaSolicitudCrediticiaEventProd, "consultaSolicitudCrediticia");
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest("consultaSolicitudCrediticia");
    }

    public void consultaPrestamosPermitidosProd(ConsultaPrestamosPermitidosBodyRequestBeanProd consultaPrestamosPermitidosBodyRequestBeanProd, boolean z) {
        try {
            final ConsultaPrestamosPermitidosEventProd consultaPrestamosPermitidosEventProd = new ConsultaPrestamosPermitidosEventProd();
            ConsultaPrestamosPermitidosRequestBeanProd consultaPrestamosPermitidosRequestBeanProd = new ConsultaPrestamosPermitidosRequestBeanProd();
            consultaPrestamosPermitidosRequestBeanProd.headerBean = a(VConsultaPrestamosPermitidosProd.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            consultaPrestamosPermitidosRequestBeanProd.consultaPrestamosPermitidosBodyRequestBeanProd = consultaPrestamosPermitidosBodyRequestBeanProd;
            final boolean z2 = z;
            AnonymousClass37 r0 = new ConsultaPrestamosPermitidosRequestProd(this.b.getApplicationContext(), consultaPrestamosPermitidosRequestBeanProd, createErrorRequestServer(consultaPrestamosPermitidosEventProd, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    consultaPrestamosPermitidosEventProd.setBeanResponse((ConsultaPrestamosPermitidosResponseBeanProd) iBeanWS);
                    consultaPrestamosPermitidosEventProd.setResult(TypeResult.OK);
                    if (z2) {
                        DataManager.this.sendBusEvent(consultaPrestamosPermitidosEventProd);
                    }
                }

                public void onUnknowError(Exception exc) {
                    if (z2) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, consultaPrestamosPermitidosEventProd, "consultaPrestamosPermitidos");
                    }
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest("consultaPrestamosPermitidos");
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void consultaSolicitudCrediticia(ConsultaSolicitudCrediticiaBodyRequestBean consultaSolicitudCrediticiaBodyRequestBean) {
        final ConsultaSolicitudCrediticiaEvent consultaSolicitudCrediticiaEvent = new ConsultaSolicitudCrediticiaEvent();
        ConsultaSolicitudCrediticiaRequestBean consultaSolicitudCrediticiaRequestBean = new ConsultaSolicitudCrediticiaRequestBean();
        consultaSolicitudCrediticiaRequestBean.headerBean = a(VConsultaSolicitudCrediticia.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        consultaSolicitudCrediticiaRequestBean.consultaSolicitudCrediticiaBodyRequestBean = consultaSolicitudCrediticiaBodyRequestBean;
        AnonymousClass38 r0 = new ConsultaSolicitudCrediticiaRequest(this.b.getApplicationContext(), consultaSolicitudCrediticiaRequestBean, createErrorRequestServer(consultaSolicitudCrediticiaEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaSolicitudCrediticiaEvent.setBeanResponse((ConsultaSolicitudCrediticiaResponseBean) iBeanWS);
                consultaSolicitudCrediticiaEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(consultaSolicitudCrediticiaEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, consultaSolicitudCrediticiaEvent, "consultaSolicitudCrediticia");
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest("consultaSolicitudCrediticia");
    }

    public void consultaPrestamosPermitidos(ConsultaPrestamosPermitidosBodyRequestBean consultaPrestamosPermitidosBodyRequestBean, boolean z) {
        try {
            final ConsultaPrestamosPermitidosEvent consultaPrestamosPermitidosEvent = new ConsultaPrestamosPermitidosEvent();
            ConsultaPrestamosPermitidosRequestBean consultaPrestamosPermitidosRequestBean = new ConsultaPrestamosPermitidosRequestBean();
            consultaPrestamosPermitidosRequestBean.headerBean = a(VConsultaPrestamosPermitidos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            consultaPrestamosPermitidosRequestBean.consultaPrestamosPermitidosBodyRequestBean = consultaPrestamosPermitidosBodyRequestBean;
            final boolean z2 = z;
            AnonymousClass39 r0 = new ConsultaPrestamosPermitidosRequest(this.b.getApplicationContext(), consultaPrestamosPermitidosRequestBean, createErrorRequestServer(consultaPrestamosPermitidosEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    consultaPrestamosPermitidosEvent.setBeanResponse((ConsultaPrestamosPermitidosResponseBean) iBeanWS);
                    consultaPrestamosPermitidosEvent.setResult(TypeResult.OK);
                    if (z2) {
                        DataManager.this.sendBusEvent(consultaPrestamosPermitidosEvent);
                    }
                }

                public void onUnknowError(Exception exc) {
                    if (z2) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, consultaPrestamosPermitidosEvent, "consultaPrestamosPermitidos");
                    }
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest("consultaPrestamosPermitidos");
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void solicitudPrestamoPreacordado(final SolicitudPrestamoPreacordadoBodyRequestBean solicitudPrestamoPreacordadoBodyRequestBean, final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                try {
                    final SolicitudPrestamoPreacordadoEvent solicitudPrestamoPreacordadoEvent = new SolicitudPrestamoPreacordadoEvent();
                    String opcion = solicitudPrestamoPreacordadoBodyRequestBean.getDatosSolicitudPrestamo().getOpcion();
                    SolicitudPrestamoPreacordadoRequestBean solicitudPrestamoPreacordadoRequestBean = new SolicitudPrestamoPreacordadoRequestBean();
                    solicitudPrestamoPreacordadoRequestBean.headerBean = DataManager.this.a(VSolicitudPrestamoPreacordado.getData(EVersionServices.CURRENT), DataManager.this.sessionManager.getSession(), DataManager.this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true), activity);
                    solicitudPrestamoPreacordadoRequestBean.solicitudPrestamoPreacordadoBodyRequestBean = solicitudPrestamoPreacordadoBodyRequestBean;
                    TypeOption typeOption = TypeOption.INTERMDIATE_VIEW;
                    if (opcion.equalsIgnoreCase(CreditosConstants.LIQUIDACION)) {
                        typeOption = TypeOption.TRANSACTIONAL_FINAL_VIEW;
                    }
                    AnonymousClass1 r0 = new SolicitudPrestamoPreacordadoRequest(DataManager.this.b.getApplicationContext(), solicitudPrestamoPreacordadoRequestBean, DataManager.this.createErrorRequestServer(solicitudPrestamoPreacordadoEvent, typeOption)) {
                        public void onResponseBean(IBeanWS iBeanWS) {
                            solicitudPrestamoPreacordadoEvent.setBeanResponse((SolicitudPrestamoPreacordadoResponseBean) iBeanWS);
                            solicitudPrestamoPreacordadoEvent.setResult(TypeResult.OK);
                            DataManager.this.sendBusEvent(solicitudPrestamoPreacordadoEvent);
                        }

                        public void onUnknowError(Exception exc) {
                            DataManager.this.createUnknowErrorEventProcesor(exc, solicitudPrestamoPreacordadoEvent, "solicitudPrestamoPreacordado");
                        }
                    };
                    DataManager.this.a((BaseRequest) r0);
                    r0.sendRequest("solicitudPrestamoPreacordado");
                } catch (Exception e) {
                    e.fillInStackTrace();
                    e.printStackTrace();
                }
            }
        }, VABMAgendadosEnvEfe.nameService);
    }

    public void solicitudPrestamoPreacordadoProd(final SolicitudPrestamoPreacordadoBodyRequestBeanProd solicitudPrestamoPreacordadoBodyRequestBeanProd, final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                try {
                    final SolicitudPrestamoPreacordadoEventProd solicitudPrestamoPreacordadoEventProd = new SolicitudPrestamoPreacordadoEventProd();
                    String opcion = solicitudPrestamoPreacordadoBodyRequestBeanProd.getDatosSolicitudPrestamoProd().getOpcion();
                    SolicitudPrestamoPreacordadoRequestBeanProd solicitudPrestamoPreacordadoRequestBeanProd = new SolicitudPrestamoPreacordadoRequestBeanProd();
                    solicitudPrestamoPreacordadoRequestBeanProd.headerBean = DataManager.this.a(VSolicitudPrestamoPreacordadoProd.getData(EVersionServices.CURRENT), DataManager.this.sessionManager.getSession(), DataManager.this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true), activity);
                    solicitudPrestamoPreacordadoRequestBeanProd.solicitudPrestamoPreacordadoBodyRequestBeanProd = solicitudPrestamoPreacordadoBodyRequestBeanProd;
                    TypeOption typeOption = TypeOption.INTERMDIATE_VIEW;
                    if (opcion.equalsIgnoreCase(CreditosConstantsProd.LIQUIDACION)) {
                        typeOption = TypeOption.TRANSACTIONAL_FINAL_VIEW;
                    }
                    final AnonymousClass1 r0 = new SolicitudPrestamoPreacordadoRequestProd(DataManager.this.b.getApplicationContext(), solicitudPrestamoPreacordadoRequestBeanProd, DataManager.this.createErrorRequestServer(solicitudPrestamoPreacordadoEventProd, typeOption)) {
                        public void onResponseBean(IBeanWS iBeanWS) {
                            solicitudPrestamoPreacordadoEventProd.setBeanResponse((SolicitudPrestamoPreacordadoResponseBeanProd) iBeanWS);
                            solicitudPrestamoPreacordadoEventProd.setResult(TypeResult.OK);
                            DataManager.this.sendBusEvent(solicitudPrestamoPreacordadoEventProd);
                        }

                        public void onUnknowError(Exception exc) {
                            DataManager.this.createUnknowErrorEventProcesor(exc, solicitudPrestamoPreacordadoEventProd, "solicitudPrestamoPreacordado");
                        }
                    };
                    DataManager.this.d.post(new Runnable() {
                        public void run() {
                            DataManager.this.a((BaseRequest) r0);
                            r0.sendRequest("solicitudPrestamoPreacordado");
                        }
                    });
                } catch (Exception e) {
                    e.fillInStackTrace();
                    e.printStackTrace();
                }
            }
        }, VABMAgendadosEnvEfe.nameService);
    }

    private HeaderBean a(ServiceHeaderBean serviceHeaderBean) {
        return ManagerHeaderBean.getPublicHeaderBean(serviceHeaderBean, this.b.getApplicationContext());
    }

    /* access modifiers changed from: private */
    public HeaderBean a(ServiceHeaderBean serviceHeaderBean, Boolean bool, Boolean bool2, Activity activity) {
        return ManagerHeaderBean.getPublicHeaderBean(serviceHeaderBean, this.b.getApplicationContext(), bool, bool2, activity);
    }

    /* access modifiers changed from: private */
    public PrivateHeaderBean a(ServiceHeaderBean serviceHeaderBean, String str, String str2) {
        return ManagerHeaderBean.getPrivateHeaderBean(serviceHeaderBean, this.b.getApplicationContext(), str, str2);
    }

    /* access modifiers changed from: private */
    public PrivateHeaderBean a(ServiceHeaderBean serviceHeaderBean, String str, String str2, Boolean bool, Boolean bool2, Activity activity) {
        return ManagerHeaderBean.getPrivateHeaderBean(serviceHeaderBean, this.b.getApplicationContext(), str, str2, bool, bool2, activity);
    }

    public void cnsDeuda() {
        final CnsDeudaEvent cnsDeudaEvent = new CnsDeudaEvent();
        CnsDeudaPTRequestBean cnsDeudaPTRequestBean = new CnsDeudaPTRequestBean();
        cnsDeudaPTRequestBean.headerBean = a(VCnsDeudaTarjetas.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        cnsDeudaPTRequestBean.cnsDeudaPTBodyRequestBean = new CnsDeudaPTBodyRequestBean("PT");
        AnonymousClass42 r0 = new CnsDeudaPTRequest(this.b.getApplicationContext(), cnsDeudaPTRequestBean, createErrorRequestServer(cnsDeudaEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                CnsDeudaPTResponseBean cnsDeudaPTResponseBean = (CnsDeudaPTResponseBean) iBeanWS;
                cnsDeudaEvent.setBeanResponse(cnsDeudaPTResponseBean);
                cnsDeudaEvent.setResult(TypeResult.OK);
                DataManager.this.sessionManager.setCnsDeuda(cnsDeudaPTResponseBean.cnsDeudaPTBodyResponseBean);
                DataManager.this.sendBusEvent(cnsDeudaEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, cnsDeudaEvent, VCnsDeudaTarjetas.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VCnsDeudaTarjetas.nameService);
    }

    public void cnsDeudaRecarga(CnsDeudaRecargaBodyRequestBean cnsDeudaRecargaBodyRequestBean, EVersionServices eVersionServices) {
        try {
            final CnsDeudaRecargaEvent cnsDeudaRecargaEvent = new CnsDeudaRecargaEvent();
            CnsDeudaRecargaRequestBean cnsDeudaRecargaRequestBean = new CnsDeudaRecargaRequestBean();
            cnsDeudaRecargaRequestBean.headerBean = a(VCnsDeudaRecarga.getData(eVersionServices), this.sessionManager.getSession(), this.sessionManager.getNup());
            cnsDeudaRecargaRequestBean.cnsDeudaRecargaBodyRequestBean = cnsDeudaRecargaBodyRequestBean;
            AnonymousClass43 r0 = new CnsDeudaRecargaRequest(this.b.getApplicationContext(), cnsDeudaRecargaRequestBean, createErrorRequestServer(cnsDeudaRecargaEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    CnsDeudaRecargaResponseBean cnsDeudaRecargaResponseBean = (CnsDeudaRecargaResponseBean) iBeanWS;
                    cnsDeudaRecargaEvent.setTypeOption(TypeOption.INITIAL_VIEW);
                    cnsDeudaRecargaEvent.setBeanResponse(cnsDeudaRecargaResponseBean);
                    cnsDeudaRecargaEvent.setResult(TypeResult.OK);
                    cnsDeudaRecargaEvent.setResponse(cnsDeudaRecargaResponseBean);
                    DataManager.this.sendBusEvent(cnsDeudaRecargaEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, cnsDeudaRecargaEvent, VCnsDeudaRecarga.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VCnsDeudaRecarga.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void pagoTarjeta(ArrayList<String> arrayList) {
        final PagoTarjetaEvent pagoTarjetaEvent = new PagoTarjetaEvent();
        PagoTarjetaRequestBean pagoTarjetaRequestBean = new PagoTarjetaRequestBean();
        pagoTarjetaRequestBean.headerBean = a(VPagoTarjeta.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        pagoTarjetaRequestBean.pagoTarjetaBodyRequestBean = new PagoTarjetaBodyRequestBean(arrayList);
        AnonymousClass44 r0 = new PagoTarjetaRequest(this.b.getApplicationContext(), pagoTarjetaRequestBean, createErrorRequestServer(pagoTarjetaEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                pagoTarjetaEvent.setBeanResponse((PagoTarjetaResponseBean) iBeanWS);
                pagoTarjetaEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(pagoTarjetaEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, pagoTarjetaEvent, VPagoTarjeta.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VPagoTarjeta.nameService);
    }

    public void stopDebit(ArrayList<String> arrayList) {
        final StopDebitEvent stopDebitEvent = new StopDebitEvent();
        StopDebitRequestBean stopDebitRequestBean = new StopDebitRequestBean();
        stopDebitRequestBean.headerBean = a(VStopDebit.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        stopDebitRequestBean.stopDebitBodyRequestBean = new StopDebitBodyRequestBean(arrayList);
        AnonymousClass45 r0 = new StopDebitRequest(this.b.getApplicationContext(), stopDebitRequestBean, createErrorRequestServer(stopDebitEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                stopDebitEvent.setBeanResponse((StopDebitResponseBean) iBeanWS);
                stopDebitEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(stopDebitEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, stopDebitEvent, VStopDebit.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VStopDebit.nameService);
    }

    public void ultimoResumenTC(UltimoResumenTCBodyRequestBean ultimoResumenTCBodyRequestBean) {
        try {
            final UltimoResumenTCEvent ultimoResumenTCEvent = new UltimoResumenTCEvent();
            UltimoResumenTCRequestBean ultimoResumenTCRequestBean = new UltimoResumenTCRequestBean();
            ultimoResumenTCRequestBean.setHeader(a(VUltimoResumenTC.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()));
            ultimoResumenTCRequestBean.setBody(ultimoResumenTCBodyRequestBean);
            AnonymousClass46 r0 = new UltimoResumenTCRequest(this.b.getApplicationContext(), ultimoResumenTCRequestBean, createErrorRequestServer(ultimoResumenTCEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    Log.d("@dev", "Ha entrado en DataManager.ultimoResumenTC.onResponseBean");
                    UltimoResumenTCResponseBean ultimoResumenTCResponseBean = (UltimoResumenTCResponseBean) iBeanWS;
                    ultimoResumenTCEvent.setBeanResponse(ultimoResumenTCResponseBean);
                    ultimoResumenTCEvent.setResult(TypeResult.OK);
                    ultimoResumenTCEvent.setResponse(ultimoResumenTCResponseBean);
                    DataManager.this.sessionManager.setUltimoResumenTC(ultimoResumenTCResponseBean.getBody());
                    DataManager.this.sendBusEvent(ultimoResumenTCEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, ultimoResumenTCEvent, VUltimoResumenTC.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VUltimoResumenTC.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void ultimosConsumosTC(UltimosConsumosTCBodyRequestBean ultimosConsumosTCBodyRequestBean, Tarjeta tarjeta) {
        try {
            final UltimosConsumosTCEvent ultimosConsumosTCEvent = new UltimosConsumosTCEvent();
            ultimosConsumosTCEvent.setTarjetaSeleccionada(tarjeta);
            UltimosConsumosTCRequestBean ultimosConsumosTCRequestBean = new UltimosConsumosTCRequestBean();
            ultimosConsumosTCRequestBean.setHeader(a(VUltimosConsumosTC.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()));
            ultimosConsumosTCRequestBean.setBody(ultimosConsumosTCBodyRequestBean);
            AnonymousClass47 r0 = new UltimosConsumosTCRequest(this.b.getApplicationContext(), ultimosConsumosTCRequestBean, createErrorRequestServer(ultimosConsumosTCEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    UltimosConsumosTCResponseBean ultimosConsumosTCResponseBean = (UltimosConsumosTCResponseBean) iBeanWS;
                    ultimosConsumosTCEvent.setBeanResponse(ultimosConsumosTCResponseBean);
                    ultimosConsumosTCEvent.setResult(TypeResult.OK);
                    ultimosConsumosTCEvent.setResponse(ultimosConsumosTCResponseBean);
                    DataManager.this.sendBusEvent(ultimosConsumosTCEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, ultimosConsumosTCEvent, VUltimosConsumosTC.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VUltimosConsumosTC.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void limitesYDisponiblesTC(LimitesYDisponiblesTCBodyRequestBean limitesYDisponiblesTCBodyRequestBean, TypeOption typeOption, Tarjeta tarjeta) {
        try {
            final LimitesYDisponiblesTCEvent limitesYDisponiblesTCEvent = new LimitesYDisponiblesTCEvent();
            limitesYDisponiblesTCEvent.setTarjetaSeleccionada(tarjeta);
            LimitesYDisponiblesTCRequestBean limitesYDisponiblesTCRequestBean = new LimitesYDisponiblesTCRequestBean();
            limitesYDisponiblesTCRequestBean.setHeader(a(VLimitesYDisponiblesTC.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()));
            limitesYDisponiblesTCRequestBean.setBody(limitesYDisponiblesTCBodyRequestBean);
            AnonymousClass48 r0 = new LimitesYDisponiblesTCRequest(this.b.getApplicationContext(), limitesYDisponiblesTCRequestBean, createErrorRequestServer(limitesYDisponiblesTCEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    LimitesYDisponiblesTCResponseBean limitesYDisponiblesTCResponseBean = (LimitesYDisponiblesTCResponseBean) iBeanWS;
                    limitesYDisponiblesTCEvent.setBeanResponse(limitesYDisponiblesTCResponseBean);
                    limitesYDisponiblesTCEvent.setResult(TypeResult.OK);
                    limitesYDisponiblesTCEvent.setResponse(limitesYDisponiblesTCResponseBean);
                    DataManager.this.sessionManager.setLimitesYDisponiblesTC(limitesYDisponiblesTCResponseBean.getBody());
                    DataManager.this.sendBusEvent(limitesYDisponiblesTCEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, limitesYDisponiblesTCEvent, VLimitesYDisponiblesTC.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VLimitesYDisponiblesTC.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void consultaDatosInicialesTransf(DatosIniciales datosIniciales) {
        final ConsultaDatosInicialesTransfEvent consultaDatosInicialesTransfEvent = new ConsultaDatosInicialesTransfEvent();
        AnonymousClass49 r0 = new ConsultaDatosInicialesTransfRequest(this.b.getApplicationContext(), new ConsultaDatosInicialesTransfRequestBean(a(VConsultaDatosInicialesTransf.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new ConsultaDatosInicialesTransfBodyRequestBean(datosIniciales)), createErrorRequestServer(consultaDatosInicialesTransfEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaDatosInicialesTransfEvent.setBeanResponse((ConsultaDatosInicialesTransfResponseBean) iBeanWS);
                consultaDatosInicialesTransfEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(consultaDatosInicialesTransfEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, consultaDatosInicialesTransfEvent, VConsultaDatosInicialesTransf.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VConsultaDatosInicialesTransf.nameService);
    }

    public void solicitudLimiteTransf(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        final String str11 = str;
        final String str12 = str2;
        final String str13 = str3;
        final String str14 = str4;
        final String str15 = str5;
        final String str16 = str6;
        final String str17 = str7;
        final String str18 = str8;
        final String str19 = str9;
        final String str20 = str10;
        AnonymousClass50 r0 = new Runnable() {
            public void run() {
                final SolicitudLimiteTransfEvent solicitudLimiteTransfEvent = new SolicitudLimiteTransfEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                Context applicationContext = DataManager.this.b.getApplicationContext();
                PrivateHeaderBean a2 = DataManager.this.a(VSolicitudLimiteTransf.getData(EVersionServices.CURRENT), session, nup);
                String str = str11;
                String str2 = str12;
                String str3 = str13;
                String str4 = str14;
                String str5 = str15;
                String str6 = str16;
                String str7 = str17;
                String str8 = str18;
                Context context = applicationContext;
                String str9 = str8;
                SolicitudLimiteTransfBodyRequestBean solicitudLimiteTransfBodyRequestBean = new SolicitudLimiteTransfBodyRequestBean(str, str2, str3, str4, str5, str6, str7, str9, str19, str20);
                final AnonymousClass1 r0 = new SolicitudLimiteTransfRequest(context, new SolicitudLimiteTransfRequestBean(a2, solicitudLimiteTransfBodyRequestBean), DataManager.this.createErrorRequestServer(solicitudLimiteTransfEvent, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        SolicitudLimiteTransfResponseBean solicitudLimiteTransfResponseBean = (SolicitudLimiteTransfResponseBean) iBeanWS;
                        solicitudLimiteTransfEvent.setResponse(solicitudLimiteTransfResponseBean);
                        solicitudLimiteTransfEvent.setBeanResponse(solicitudLimiteTransfResponseBean);
                        solicitudLimiteTransfEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(solicitudLimiteTransfEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, solicitudLimiteTransfEvent, VSolicitudLimiteTransf.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VSolicitudLimiteTransf.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VSolicitudLimiteTransf.nameService);
    }

    public void transferencias(DatosTransferenciaBean datosTransferenciaBean, DatosCuentaDebitoBean datosCuentaDebitoBean, DatosCuentasDestinoBSRBean datosCuentasDestinoBSRBean, Activity activity) {
        final Activity activity2 = activity;
        final DatosTransferenciaBean datosTransferenciaBean2 = datosTransferenciaBean;
        final DatosCuentaDebitoBean datosCuentaDebitoBean2 = datosCuentaDebitoBean;
        final DatosCuentasDestinoBSRBean datosCuentasDestinoBSRBean2 = datosCuentasDestinoBSRBean;
        AnonymousClass51 r0 = new Runnable() {
            public void run() {
                final TransferenciasEvent transferenciasEvent = new TransferenciasEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                AnonymousClass1 r0 = new TransferenciasRequest(DataManager.this.b.getApplicationContext(), new TransferenciasRequestBean(DataManager.this.a(VTransferencias.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(true), Boolean.valueOf(true), activity2), new TransferenciasBodyRequestBean(datosTransferenciaBean2, datosCuentaDebitoBean2, datosCuentasDestinoBSRBean2)), DataManager.this.createErrorRequestServer(transferenciasEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        transferenciasEvent.setBeanResponse((TransferenciasResponseBean) iBeanWS);
                        transferenciasEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(transferenciasEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, transferenciasEvent, VTransferencias.nameService);
                    }
                };
                DataManager.this.a((BaseRequest) r0);
                r0.sendRequest(VTransferencias.nameService);
            }
        };
        a((Runnable) r0, VABMAgendadosEnvEfe.nameService);
    }

    public void transferencias(DatosTransferenciaBean datosTransferenciaBean, DatosCuentaDebitoBean datosCuentaDebitoBean, DatosCuentasDestinoOBBean datosCuentasDestinoOBBean, Activity activity) {
        final Activity activity2 = activity;
        final DatosTransferenciaBean datosTransferenciaBean2 = datosTransferenciaBean;
        final DatosCuentaDebitoBean datosCuentaDebitoBean2 = datosCuentaDebitoBean;
        final DatosCuentasDestinoOBBean datosCuentasDestinoOBBean2 = datosCuentasDestinoOBBean;
        AnonymousClass52 r0 = new Runnable() {
            public void run() {
                final TransferenciasEvent transferenciasEvent = new TransferenciasEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                AnonymousClass1 r0 = new TransferenciasRequest(DataManager.this.b.getApplicationContext(), new TransferenciasRequestBean(DataManager.this.a(VTransferencias.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(true), Boolean.valueOf(true), activity2), new TransferenciasBodyRequestBean(datosTransferenciaBean2, datosCuentaDebitoBean2, datosCuentasDestinoOBBean2)), DataManager.this.createErrorRequestServer(transferenciasEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        transferenciasEvent.setBeanResponse((TransferenciasResponseBean) iBeanWS);
                        transferenciasEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(transferenciasEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, transferenciasEvent, VTransferencias.nameService);
                    }
                };
                DataManager.this.a((BaseRequest) r0);
                r0.sendRequest(VTransferencias.nameService);
            }
        };
        a((Runnable) r0, VABMAgendadosEnvEfe.nameService);
    }

    public void verificaDatosInicialesTransf(VerificaDatosBean verificaDatosBean) {
        final VerificaDatosInicialesTransfEvent verificaDatosInicialesTransfEvent = new VerificaDatosInicialesTransfEvent();
        AnonymousClass53 r0 = new VerificaDatosInicialesTransfRequest(this.b.getApplicationContext(), new VerificaDatosInicialesTransfRequestBean(a(VVerificaDatosInicialesTransf.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new VerificaDatosInicialesTransfBodyRequestBean(verificaDatosBean)), createErrorRequestServer(verificaDatosInicialesTransfEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                verificaDatosInicialesTransfEvent.setBeanResponse((VerificaDatosInicialesTransfOBResponseBean) iBeanWS);
                verificaDatosInicialesTransfEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(verificaDatosInicialesTransfEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, verificaDatosInicialesTransfEvent, VVerificaDatosInicialesTransf.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VVerificaDatosInicialesTransf.nameService);
    }

    public void verificaDatosInicialesTransfOB(VerificaDatosOBBean verificaDatosOBBean) {
        final VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent = new VerificaDatosInicialesTransfOBEvent();
        AnonymousClass54 r0 = new VerificaDatosInicialesTransfOBRequest(this.b.getApplicationContext(), new VerificaDatosInicialesTransfOBRequestBean(a(VVerificaDatosInicialesTransf.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new VerificaDatosInicialesTransfOBBodyRequestBean(verificaDatosOBBean)), createErrorRequestServer(verificaDatosInicialesTransfOBEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                verificaDatosInicialesTransfOBEvent.setBeanResponse((VerificaDatosInicialesTransfOBResponseBean) iBeanWS);
                verificaDatosInicialesTransfOBEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(verificaDatosInicialesTransfOBEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, verificaDatosInicialesTransfOBEvent, VVerificaDatosInicialesTransf.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VVerificaDatosInicialesTransf.nameService);
    }

    public void getRetryTimeOutServer(TimeServer timeServer) {
        RetryServerRequestBean retryServerRequestBean = new RetryServerRequestBean();
        retryServerRequestBean.headerBean = a(VRetryServer.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        retryServerRequestBean.timeServer = timeServer;
        AnonymousClass55 r5 = new RetryServerRequest(this.b.getApplicationContext(), retryServerRequestBean, createErrorRequestServer(null, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, null, VRetryServer.nameService);
            }
        };
        a((BaseRequest) r5);
        r5.sendRequest(VRetryServer.nameService);
    }

    public void getDatosInicialesDolares(String str) {
        getDatosInicialesDolares(str, null);
    }

    public void getDatosInicialesDolares(String str, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean) {
        try {
            final GetDatosInicialesDolaresEvent getDatosInicialesDolaresEvent = new GetDatosInicialesDolaresEvent();
            AnonymousClass56 r0 = new GetDatosInicialesDolaresRequest(this.b.getApplicationContext(), new GetDatosInicialesDolaresRequestBean(a(VGetDatosInicialesDolares.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetDatosInicialesDolaresBodyRequestBean(str, compraVentaDolaresCuentaBean)), createErrorRequestServer(getDatosInicialesDolaresEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetDatosInicialesDolaresResponseBean getDatosInicialesDolaresResponseBean = (GetDatosInicialesDolaresResponseBean) iBeanWS;
                    getDatosInicialesDolaresEvent.setBeanResponse(getDatosInicialesDolaresResponseBean);
                    getDatosInicialesDolaresEvent.setResult(TypeResult.OK);
                    getDatosInicialesDolaresEvent.setResponse(getDatosInicialesDolaresResponseBean);
                    DataManager.this.sendBusEvent(getDatosInicialesDolaresEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, getDatosInicialesDolaresEvent, VGetDatosInicialesDolares.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VGetDatosInicialesDolares.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void simulacionDolar(String str, String str2, String str3, String str4, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2) {
        try {
            final SimulacionDolarEvent simulacionDolarEvent = new SimulacionDolarEvent();
            Context applicationContext = this.b.getApplicationContext();
            PrivateHeaderBean a2 = a(VSimulacionDolar.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            SimulacionDolarBodyRequestBean simulacionDolarBodyRequestBean = new SimulacionDolarBodyRequestBean(str, str2, str3, str4, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
            AnonymousClass57 r1 = new SimulacionDolarRequest(applicationContext, new SimulacionDolarRequestBean(a2, simulacionDolarBodyRequestBean), createErrorRequestServer(simulacionDolarEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    SimulacionDolarResponseBean simulacionDolarResponseBean = (SimulacionDolarResponseBean) iBeanWS;
                    simulacionDolarEvent.setBeanResponse(simulacionDolarResponseBean);
                    simulacionDolarEvent.setResult(TypeResult.OK);
                    simulacionDolarEvent.setResponse(simulacionDolarResponseBean);
                    DataManager.this.sendBusEvent(simulacionDolarEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, simulacionDolarEvent, VSimulacionDolar.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VSimulacionDolar.nameService);
        } catch (Exception e) {
            Exception exc = e;
            exc.fillInStackTrace();
            exc.printStackTrace();
        }
    }

    public void compraVentaDolar(String str, String str2, String str3, String str4, String str5, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2) {
        try {
            final CompraVentaDolarEvent compraVentaDolarEvent = new CompraVentaDolarEvent();
            Context applicationContext = this.b.getApplicationContext();
            PrivateHeaderBean a2 = a(VCompraVentaDolar.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            CompraVentaDolarBodyRequestBean compraVentaDolarBodyRequestBean = new CompraVentaDolarBodyRequestBean(str, str2, str3, str4, str5, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
            AnonymousClass58 r1 = new CompraVentaDolarRequest(applicationContext, new CompraVentaDolarRequestBean(a2, compraVentaDolarBodyRequestBean), createErrorRequestServer(compraVentaDolarEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    CompraVentaDolarResponseBean compraVentaDolarResponseBean = (CompraVentaDolarResponseBean) iBeanWS;
                    compraVentaDolarEvent.setBeanResponse(compraVentaDolarResponseBean);
                    compraVentaDolarEvent.setResult(TypeResult.OK);
                    compraVentaDolarEvent.setResponse(compraVentaDolarResponseBean);
                    DataManager.this.sendBusEvent(compraVentaDolarEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, compraVentaDolarEvent, VCompraVentaDolar.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VCompraVentaDolar.nameService);
        } catch (Exception e) {
            Exception exc = e;
            exc.fillInStackTrace();
            exc.printStackTrace();
        }
    }

    public void cnsTenencias(String str) {
        final CnsTenenciasEvent cnsTenenciasEvent = new CnsTenenciasEvent();
        cnsTenenciasEvent.setTypeOption(TypeOption.INITIAL_VIEW);
        AnonymousClass59 r0 = new CnsTenenciasRequest(this.b.getApplicationContext(), new CnsTenenciasRequestBean(a(VCnsTenencias.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new CnsTenenciasBodyRequestBean(str)), createErrorRequestServer(cnsTenenciasEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                cnsTenenciasEvent.setBeanResponse((CnsTenenciasResponseBean) iBeanWS);
                cnsTenenciasEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(cnsTenenciasEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, cnsTenenciasEvent, VCnsTenencias.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VCnsTenencias.nameService);
    }

    public void cnsTasasPFT() {
        final CnsTasasPFTEvent cnsTasasPFTEvent = new CnsTasasPFTEvent();
        AnonymousClass60 r0 = new CnsTasasPFTRequest(this.b.getApplicationContext(), new CnsTasasPFTRequestBean(a(VCnsTasasPFT.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new CnsTasasPFTBodyRequestBean()), createErrorRequestServer(cnsTasasPFTEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                cnsTasasPFTEvent.setBeanResponse((CnsTasasPFTResponseBean) iBeanWS);
                cnsTasasPFTEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(cnsTasasPFTEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, cnsTasasPFTEvent, VCnsTasasPFT.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VCnsTasasPFT.nameService);
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }

    public void getConstPlazoFijo(ConstPlazoFijoBodyRequestBean constPlazoFijoBodyRequestBean) {
        final ConstPlazoFijoEvent constPlazoFijoEvent = new ConstPlazoFijoEvent();
        ConstPlazoFijoRequestBean constPlazoFijoRequestBean = new ConstPlazoFijoRequestBean();
        String str = constPlazoFijoBodyRequestBean.tipoInvocacion;
        constPlazoFijoRequestBean.headerBean = a(VConstPlazoFijo.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        constPlazoFijoRequestBean.constPlazoFijoBodyRequestBean = constPlazoFijoBodyRequestBean;
        TypeOption typeOption = TypeOption.INTERMDIATE_VIEW;
        if (LongTermDepositPresenterImp.TYPE_ALTA.equalsIgnoreCase(str)) {
            typeOption = TypeOption.TRANSACTIONAL_FINAL_VIEW;
        }
        AnonymousClass61 r0 = new ConstPlazoFijoRequest(this.b.getApplicationContext(), constPlazoFijoRequestBean, createErrorRequestServer(constPlazoFijoEvent, typeOption)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                ConstPlazoFijoResponseBean constPlazoFijoResponseBean = (ConstPlazoFijoResponseBean) iBeanWS;
                constPlazoFijoEvent.setBeanResponse(constPlazoFijoResponseBean);
                constPlazoFijoEvent.setResult(TypeResult.OK);
                constPlazoFijoEvent.setResponse(constPlazoFijoResponseBean);
                DataManager.this.sendBusEvent(constPlazoFijoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, constPlazoFijoEvent, VCnsDeuda.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VCnsDeuda.nameService);
    }

    private void a(String str) {
        try {
            final CargaDatosInicialesExtEnvEvent cargaDatosInicialesExtEnvEvent = new CargaDatosInicialesExtEnvEvent();
            AnonymousClass62 r0 = new CargaDatosInicialesExtEnvRequest(this.b.getApplicationContext(), new CargaDatosInicialesExtEnvRequestBean(a(VCargaDatosInicialesExtEnv.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new CargaDatosInicialesExtEnvBodyRequestBean(str)), createErrorRequestServer(cargaDatosInicialesExtEnvEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    CargaDatosInicialesExtEnvResponseBean cargaDatosInicialesExtEnvResponseBean = (CargaDatosInicialesExtEnvResponseBean) iBeanWS;
                    cargaDatosInicialesExtEnvEvent.setBeanResponse(cargaDatosInicialesExtEnvResponseBean);
                    cargaDatosInicialesExtEnvEvent.setResult(TypeResult.OK);
                    cargaDatosInicialesExtEnvEvent.setResponse(cargaDatosInicialesExtEnvResponseBean);
                    DataManager.this.sessionManager.setDatosInicialesExtExv(cargaDatosInicialesExtEnvResponseBean.cargaDatosInicialesExtEnvBodyResponseBean);
                    DataManager.this.sendBusEvent(cargaDatosInicialesExtEnvEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, cargaDatosInicialesExtEnvEvent, VCargaDatosInicialesExtEnv.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VCargaDatosInicialesExtEnv.nameService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargaDatosInicialesEnv() {
        a(TipoOperacion.EnvioDinero);
    }

    public void cargaDatosInicialesExt() {
        a(TipoOperacion.ExtraccionSinTarjeta);
    }

    public void consultaAgendados() {
        try {
            final ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent = new ConsultaAgendadosEnvEfeEvent();
            AnonymousClass63 r0 = new ConsultaAgendadosEnvEfeRequest(this.b.getApplicationContext(), new ConsultaAgendadosEnvEfeRequestBean(a(VConsultaAgendadosEnvEfe.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup())), createErrorRequestServer(consultaAgendadosEnvEfeEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    ConsultaAgendadosEnvEfeResponseBean consultaAgendadosEnvEfeResponseBean = (ConsultaAgendadosEnvEfeResponseBean) iBeanWS;
                    consultaAgendadosEnvEfeEvent.setBeanResponse(consultaAgendadosEnvEfeResponseBean);
                    consultaAgendadosEnvEfeEvent.setResult(TypeResult.OK);
                    consultaAgendadosEnvEfeEvent.setResponse(consultaAgendadosEnvEfeResponseBean);
                    DataManager.this.sendBusEvent(consultaAgendadosEnvEfeEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, consultaAgendadosEnvEfeEvent, VConsultaAgendadosEnvEfe.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VConsultaAgendadosEnvEfe.nameService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPromocionPush(LocalizacionBean localizacionBean, String str) {
        final GetPromocionPushEvent getPromocionPushEvent = new GetPromocionPushEvent();
        GetPromocionesPushRequestBean getPromocionesPushRequestBean = new GetPromocionesPushRequestBean(a(VGetPromocionPush.getData(EVersionServices.CURRENT)));
        GetPromocionesPushBodyRequestBean getPromocionesPushBodyRequestBean = new GetPromocionesPushBodyRequestBean();
        getPromocionesPushBodyRequestBean.setLocalizacionPromocionesBean(localizacionBean);
        getPromocionesPushBodyRequestBean.setId(str);
        getPromocionesPushRequestBean.setGetPromocionesPushBodyRequestBean(getPromocionesPushBodyRequestBean);
        AnonymousClass64 r0 = new GetPromocionesPushRequest(this.b.getApplicationContext(), getPromocionesPushRequestBean, createErrorRequestServer(getPromocionPushEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getPromocionPushEvent.setBeanResponse((GetPromocionesPushResponseBean) iBeanWS);
                getPromocionPushEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getPromocionPushEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getPromocionPushEvent, VPromocionesHome.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VPromocionesHome.nameService);
    }

    public void getPuntosSuperClub() {
        try {
            final GetPuntosSuperClubEvent getPuntosSuperClubEvent = new GetPuntosSuperClubEvent();
            AnonymousClass65 r0 = new GetPuntosSuperClubRequest(this.b.getApplicationContext(), new GetPuntosSuperClubRequestBean(a(VGetPuntosSuperClub.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup())), createErrorRequestServer(getPuntosSuperClubEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetPuntosSuperClubResponseBean getPuntosSuperClubResponseBean = (GetPuntosSuperClubResponseBean) iBeanWS;
                    getPuntosSuperClubEvent.setBeanResponse(getPuntosSuperClubResponseBean);
                    getPuntosSuperClubEvent.setResult(TypeResult.OK);
                    getPuntosSuperClubEvent.setResponse(getPuntosSuperClubResponseBean);
                    DataManager.this.sendBusEvent(getPuntosSuperClubEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, getPuntosSuperClubEvent, VGetPuntosSuperClub.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VGetPuntosSuperClub.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getConfirmarPago(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        final ConfirmarPagoEvent confirmarPagoEvent = new ConfirmarPagoEvent();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VConfirmarPagoCuota.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        ConfirmarPagoBodyRequestBean confirmarPagoBodyRequestBean = new ConfirmarPagoBodyRequestBean(str, str2, str3, str4, str5, str6, str7, str8);
        AnonymousClass66 r0 = new ConfirmarPagoCuotaRequest(applicationContext, new ConfirmarPagoRequestBean(a2, confirmarPagoBodyRequestBean), createErrorRequestServer(confirmarPagoEvent, TypeOption.INITIAL_VIEW)) {
            public void onUnknowError(Exception exc) {
            }

            public void onResponseBean(IBeanWS iBeanWS) {
                ConfirmarPagoResponseBean confirmarPagoResponseBean = (ConfirmarPagoResponseBean) iBeanWS;
                confirmarPagoEvent.setBeanResponse(confirmarPagoResponseBean);
                confirmarPagoEvent.setResult(TypeResult.OK);
                confirmarPagoEvent.setResponse(confirmarPagoResponseBean);
                DataManager.this.sendBusEvent(confirmarPagoEvent);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VConfirmarPagoCuota.nameService);
    }

    public void getSimularPagos(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        final SimularPagoCuotaEvent simularPagoCuotaEvent = new SimularPagoCuotaEvent();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VSimularPagos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        GetSimularPagosBodyRequestBean getSimularPagosBodyRequestBean = new GetSimularPagosBodyRequestBean(str, str2, str3, str4, str5, str6, str7);
        AnonymousClass67 r0 = new SimularPagoCuotasRequest(applicationContext, new SimularPagosRequestBean(a2, getSimularPagosBodyRequestBean), createErrorRequestServer(simularPagoCuotaEvent, TypeOption.INITIAL_VIEW)) {
            public void onUnknowError(Exception exc) {
            }

            public void onResponseBean(IBeanWS iBeanWS) {
                SimularPagosResponseBean simularPagosResponseBean = (SimularPagosResponseBean) iBeanWS;
                simularPagoCuotaEvent.setBeanResponse(simularPagosResponseBean);
                simularPagoCuotaEvent.setResult(TypeResult.OK);
                simularPagoCuotaEvent.setResponse(simularPagosResponseBean);
                DataManager.this.sendBusEvent(simularPagoCuotaEvent);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VSimularPagos.nameService);
    }

    public void getTenenciaCreditos() {
        try {
            final GetTenenciaCreditosEvent getTenenciaCreditosEvent = new GetTenenciaCreditosEvent();
            AnonymousClass68 r0 = new GetTenenciaCreditosRequest(this.b.getApplicationContext(), new GetTenenciaCreditosRequestBean(a(VGetTenenciaCreditos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup())), createErrorRequestServer(getTenenciaCreditosEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetTenenciaCreditosResponseBean getTenenciaCreditosResponseBean = (GetTenenciaCreditosResponseBean) iBeanWS;
                    getTenenciaCreditosEvent.setBeanResponse(getTenenciaCreditosResponseBean);
                    getTenenciaCreditosEvent.setResult(TypeResult.OK);
                    getTenenciaCreditosEvent.setResponse(getTenenciaCreditosResponseBean);
                    DataManager.this.sendBusEvent(getTenenciaCreditosEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createErrorRequestServer(getTenenciaCreditosEvent, TypeOption.INITIAL_VIEW);
                }
            };
            a((BaseRequest) r0, 4000);
            r0.sendRequest(VGetTenenciaCreditos.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getCategoriasSuperClub() {
        try {
            final GetCategoriasSuperClubEvent getCategoriasSuperClubEvent = new GetCategoriasSuperClubEvent();
            AnonymousClass69 r0 = new GetCategoriasSuperClubRequest(this.b.getApplicationContext(), new GetCategoriasSuperClubRequestBean(a(VGetCategoriasSuperClub.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup())), createErrorRequestServer(getCategoriasSuperClubEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetCategoriasSuperClubResponseBean getCategoriasSuperClubResponseBean = (GetCategoriasSuperClubResponseBean) iBeanWS;
                    getCategoriasSuperClubEvent.setBeanResponse(getCategoriasSuperClubResponseBean);
                    getCategoriasSuperClubEvent.setResult(TypeResult.OK);
                    getCategoriasSuperClubEvent.setResponse(getCategoriasSuperClubResponseBean);
                    DataManager.this.sendBusEvent(getCategoriasSuperClubEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, getCategoriasSuperClubEvent, VGetCategoriasSuperClub.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VGetCategoriasSuperClub.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getCategoriasSuperClub(String str) {
        try {
            final GetCategoriasSuperClubEvent getCategoriasSuperClubEvent = new GetCategoriasSuperClubEvent();
            AnonymousClass70 r0 = new GetCategoriasSuperClubRequest(this.b.getApplicationContext(), new GetCategoriasSuperClubRequestBean(a(VGetCategoriasSuperClub.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetCategoriasSuperClubBodyRequestBean(str)), createErrorRequestServer(getCategoriasSuperClubEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetCategoriasSuperClubResponseBean getCategoriasSuperClubResponseBean = (GetCategoriasSuperClubResponseBean) iBeanWS;
                    getCategoriasSuperClubEvent.setBeanResponse(getCategoriasSuperClubResponseBean);
                    getCategoriasSuperClubEvent.setResult(TypeResult.OK);
                    getCategoriasSuperClubEvent.setResponse(getCategoriasSuperClubResponseBean);
                    DataManager.this.sendBusEvent(getCategoriasSuperClubEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, getCategoriasSuperClubEvent, VGetCategoriasSuperClub.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VGetCategoriasSuperClub.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getCuponesCategorySuperClub(String str, String str2, String str3) {
        try {
            final GetCuponesCategorySuperClubEvent getCuponesCategorySuperClubEvent = new GetCuponesCategorySuperClubEvent();
            AnonymousClass71 r0 = new GetCuponesSuperClubRequest(this.b.getApplicationContext(), new GetCuponesSuperClubRequestBean(a(VGetCuponesSuperClub.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetCuponesSuperClubBodyRequestBean(str, str2, str3)), createErrorRequestServer(getCuponesCategorySuperClubEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetCuponesSuperClubResponseBean getCuponesSuperClubResponseBean = (GetCuponesSuperClubResponseBean) iBeanWS;
                    getCuponesCategorySuperClubEvent.setBeanResponse(getCuponesSuperClubResponseBean);
                    getCuponesCategorySuperClubEvent.setResult(TypeResult.OK);
                    getCuponesCategorySuperClubEvent.setResponse(getCuponesSuperClubResponseBean);
                    DataManager.this.sendBusEvent(getCuponesCategorySuperClubEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, getCuponesCategorySuperClubEvent, VGetCuponesSuperClub.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VGetCuponesSuperClub.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getCuponesSubcategorySuperClub(String str, String str2, String str3) {
        try {
            final GetCuponesSubcategorySuperClubEvent getCuponesSubcategorySuperClubEvent = new GetCuponesSubcategorySuperClubEvent();
            AnonymousClass72 r0 = new GetCuponesSuperClubRequest(this.b.getApplicationContext(), new GetCuponesSuperClubRequestBean(a(VGetCuponesSuperClub.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetCuponesSuperClubBodyRequestBean(str, str2, str3)), createErrorRequestServer(getCuponesSubcategorySuperClubEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetCuponesSuperClubResponseBean getCuponesSuperClubResponseBean = (GetCuponesSuperClubResponseBean) iBeanWS;
                    getCuponesSubcategorySuperClubEvent.setBeanResponse(getCuponesSuperClubResponseBean);
                    getCuponesSubcategorySuperClubEvent.setResult(TypeResult.OK);
                    getCuponesSubcategorySuperClubEvent.setResponse(getCuponesSuperClubResponseBean);
                    DataManager.this.sendBusEvent(getCuponesSubcategorySuperClubEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, getCuponesSubcategorySuperClubEvent, VGetCuponesSuperClub.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VGetCuponesSuperClub.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void abmPreautorizacionComprador(String str, DetallePreAutorizacionBean detallePreAutorizacionBean, Activity activity, boolean z) {
        final boolean z2 = z;
        final Activity activity2 = activity;
        final String str2 = str;
        final DetallePreAutorizacionBean detallePreAutorizacionBean2 = detallePreAutorizacionBean;
        AnonymousClass73 r0 = new Runnable() {
            public void run() {
                final AbmPreautorizacionCompradorEvent abmPreautorizacionCompradorEvent = new AbmPreautorizacionCompradorEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new AbmPreautorizacionCompradorRequest(DataManager.this.b.getApplicationContext(), new AbmPreautorizacionCompradorRequestBean(DataManager.this.a(VAbmPreautorizacionComprador.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(z2), Boolean.valueOf(z2), activity2), new AbmPreautorizacionCompradorBodyRequestBean(str2, detallePreAutorizacionBean2)), DataManager.this.createErrorRequestServer(abmPreautorizacionCompradorEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        AbmPreautorizacionCompradorResponseBean abmPreautorizacionCompradorResponseBean = (AbmPreautorizacionCompradorResponseBean) iBeanWS;
                        abmPreautorizacionCompradorEvent.setResponse(abmPreautorizacionCompradorResponseBean);
                        abmPreautorizacionCompradorEvent.setBeanResponse(abmPreautorizacionCompradorResponseBean);
                        abmPreautorizacionCompradorEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(abmPreautorizacionCompradorEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, abmPreautorizacionCompradorEvent, VAbmPreautorizacionComprador.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VAbmPreautorizacionComprador.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VAbmPreautorizacionComprador.nameService);
    }

    public void canjearPuntosSuperClub(String str, String str2, String str3, String str4, String str5, String str6) {
        try {
            final CanjearPuntosSuperClubEvent canjearPuntosSuperClubEvent = new CanjearPuntosSuperClubEvent();
            Context applicationContext = this.b.getApplicationContext();
            PrivateHeaderBean a2 = a(VCanjearPuntosSuperClub.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            CanjearPuntosSuperClubBodyRequestBean canjearPuntosSuperClubBodyRequestBean = new CanjearPuntosSuperClubBodyRequestBean(str, str2, str3, str4, str5, str6);
            AnonymousClass74 r1 = new CanjearPuntosSuperClubRequest(applicationContext, new CanjearPuntosSuperClubRequestBean(a2, canjearPuntosSuperClubBodyRequestBean), createErrorRequestServer(canjearPuntosSuperClubEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    CanjearPuntosSuperClubResponseBean canjearPuntosSuperClubResponseBean = (CanjearPuntosSuperClubResponseBean) iBeanWS;
                    canjearPuntosSuperClubEvent.setBeanResponse(canjearPuntosSuperClubResponseBean);
                    canjearPuntosSuperClubEvent.setResult(TypeResult.OK);
                    canjearPuntosSuperClubEvent.setResponse(canjearPuntosSuperClubResponseBean);
                    DataManager.this.sendBusEvent(canjearPuntosSuperClubEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, canjearPuntosSuperClubEvent, VCanjearPuntosSuperClub.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VCanjearPuntosSuperClub.nameService);
        } catch (Exception e) {
            Exception exc = e;
            exc.fillInStackTrace();
            exc.printStackTrace();
        }
    }

    public void cnsEmpresa(String str) {
        try {
            final CnsEmpresaEvent cnsEmpresaEvent = new CnsEmpresaEvent();
            String cnsEmpresaPrevHashCode = this.a.getCnsEmpresaPrevHashCode();
            if (!TextUtils.isEmpty(str)) {
                cnsEmpresaPrevHashCode = "";
            }
            final String str2 = str;
            AnonymousClass75 r0 = new CnsEmpresaRequest(this.b.getApplicationContext(), new CnsEmpresaRequestBean(a(VCnsEmpresa.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new CnsEmpresaBodyRequestBean(str, cnsEmpresaPrevHashCode)), createErrorRequestServer(cnsEmpresaEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    CnsEmpresaResponseBean cnsEmpresaResponseBean = (CnsEmpresaResponseBean) iBeanWS;
                    cnsEmpresaEvent.setResult(TypeResult.OK);
                    String str = cnsEmpresaResponseBean.cnsEmpresaBodyResponseBean.hashCode;
                    if (String.valueOf(str2).isEmpty()) {
                        if (str != null) {
                            DataManager.this.a.setCnsEmpresaPrevHashCode(str);
                            DataManager.this.a.setPagoServiciosEmpresas(cnsEmpresaResponseBean.cnsEmpresaBodyResponseBean);
                        } else {
                            cnsEmpresaResponseBean.cnsEmpresaBodyResponseBean = DataManager.this.a.getPagoServiciosEmpresas();
                        }
                    }
                    cnsEmpresaEvent.setBeanResponse(cnsEmpresaResponseBean);
                    cnsEmpresaEvent.setResponse(cnsEmpresaResponseBean);
                    DataManager.this.sendBusEvent(cnsEmpresaEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, cnsEmpresaEvent, VCnsEmpresa.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VCnsEmpresa.nameService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cnsDeudaManual(String str, String str2) {
        try {
            final CnsDeudaManualEvent cnsDeudaManualEvent = new CnsDeudaManualEvent();
            AnonymousClass76 r0 = new CnsDeudaManualRequest(this.b.getApplicationContext(), new CnsDeudaManualRequestBean(a(VCnsDeudaManual.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new CnsDeudaManualBodyRequestBean(str, str2)), createErrorRequestServer(cnsDeudaManualEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    CnsDeudaManualResponseBean cnsDeudaManualResponseBean = (CnsDeudaManualResponseBean) iBeanWS;
                    cnsDeudaManualEvent.setBeanResponse(cnsDeudaManualResponseBean);
                    cnsDeudaManualEvent.setResult(TypeResult.OK);
                    cnsDeudaManualEvent.setResponse(cnsDeudaManualResponseBean);
                    DataManager.this.sendBusEvent(cnsDeudaManualEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, cnsDeudaManualEvent, VCnsDeudaManual.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VCnsDeudaManual.nameService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cnsDeudaCB(String str, String str2, String str3) {
        try {
            final CnsDeudaCBEvent cnsDeudaCBEvent = new CnsDeudaCBEvent();
            AnonymousClass77 r0 = new CnsDeudaCBRequest(this.b.getApplicationContext(), new CnsDeudaCBRequestBean(a(VCnsDeudaCB.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new CnsDeudaCBBodyRequestBean(str2, str3)), createErrorRequestServer(cnsDeudaCBEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    CnsDeudaCBResponseBean cnsDeudaCBResponseBean = (CnsDeudaCBResponseBean) iBeanWS;
                    cnsDeudaCBEvent.setBeanResponse(cnsDeudaCBResponseBean);
                    cnsDeudaCBEvent.setResult(TypeResult.OK);
                    cnsDeudaCBEvent.setResponse(cnsDeudaCBResponseBean);
                    DataManager.this.sendBusEvent(cnsDeudaCBEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, cnsDeudaCBEvent, VCnsDeudaCB.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VCnsDeudaCB.nameService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pagoServicioCB(final PagoServicioCBBodyRequestBean pagoServicioCBBodyRequestBean, final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                try {
                    final PagoServicioCBEvent pagoServicioCBEvent = new PagoServicioCBEvent();
                    final AnonymousClass1 r0 = new PagoServicioCBRequest(DataManager.this.b.getApplicationContext(), new PagoServicioCBRequestBean(DataManager.this.a(VPagoServicioCB.getData(EVersionServices.CURRENT), DataManager.this.sessionManager.getSession(), DataManager.this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true), activity), pagoServicioCBBodyRequestBean), DataManager.this.createErrorRequestServer(pagoServicioCBEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                        public void onResponseBean(IBeanWS iBeanWS) {
                            PagoServicioCBResponseBean pagoServicioCBResponseBean = (PagoServicioCBResponseBean) iBeanWS;
                            pagoServicioCBEvent.setBeanResponse(pagoServicioCBResponseBean);
                            pagoServicioCBEvent.setResult(TypeResult.OK);
                            pagoServicioCBEvent.setResponse(pagoServicioCBResponseBean);
                            DataManager.this.sendBusEvent(pagoServicioCBEvent);
                        }

                        public void onUnknowError(Exception exc) {
                            DataManager.this.createUnknowErrorEventProcesor(exc, pagoServicioCBEvent, VPagoServicioCB.nameService);
                        }
                    };
                    DataManager.this.d.post(new Runnable() {
                        public void run() {
                            DataManager.this.a((BaseRequest) r0);
                            r0.sendRequest(VPagoServicioCB.nameService);
                        }
                    });
                } catch (Exception e) {
                    e.fillInStackTrace();
                    e.printStackTrace();
                }
            }
        }, VABMAgendadosEnvEfe.nameService);
    }

    public void abmDestinatarioTransf(final ABMDestinatarioTransfBodyRequestBean aBMDestinatarioTransfBodyRequestBean, final Activity activity, final Boolean bool) {
        a((Runnable) new Runnable() {
            public void run() {
                final ABMDestinatarioTransfEvent aBMDestinatarioTransfEvent = new ABMDestinatarioTransfEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new ABMDestinatarioTransfRequest(DataManager.this.b.getApplicationContext(), new ABMDestinatarioTransfRequestBean(DataManager.this.a(VABMDestinatarioTransf.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(true), bool, activity), aBMDestinatarioTransfBodyRequestBean), DataManager.this.createErrorRequestServer(aBMDestinatarioTransfEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        aBMDestinatarioTransfEvent.setBeanResponse((ABMDestinatarioTransfResponseBean) iBeanWS);
                        aBMDestinatarioTransfEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(aBMDestinatarioTransfEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, aBMDestinatarioTransfEvent, VABMDestinatarioTransf.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VABMDestinatarioTransf.nameService);
                    }
                });
            }
        }, VABMAgendadosEnvEfe.nameService);
    }

    public void getSeguros() {
        a((Runnable) new Runnable() {
            public void run() {
                final GetSegurosEvent getSegurosEvent = new GetSegurosEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetSegurosRequest(DataManager.this.b.getApplicationContext(), new GetSegurosRequestBean(DataManager.this.a(VGetSeguros.getData(EVersionServices.CURRENT), session, nup), new GetSegurosBodyRequestBean()), DataManager.this.createErrorRequestServer(getSegurosEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetSegurosResponseBean getSegurosResponseBean = (GetSegurosResponseBean) iBeanWS;
                        getSegurosEvent.setResponse(getSegurosResponseBean);
                        getSegurosEvent.setBeanResponse(getSegurosResponseBean);
                        getSegurosEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getSegurosEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getSegurosEvent, VGetSeguros.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetSeguros.nameService);
                    }
                });
            }
        }, VGetSeguros.nameService);
    }

    public void getRecargas(String str, String str2, String str3, String str4, String str5) {
        final String str6 = str4;
        final String str7 = str5;
        final String str8 = str;
        final String str9 = str2;
        final String str10 = str3;
        AnonymousClass81 r0 = new Runnable() {
            public void run() {
                final GetRecargasEvent getRecargasEvent = new GetRecargasEvent();
                PrivateHeaderBean a2 = DataManager.this.a(VRecargas.getData(EVersionServices.V1_0), str6, str7, Boolean.valueOf(true), Boolean.valueOf(false), null);
                a2.makePublic();
                GetRecargasRequestBean getRecargasRequestBean = new GetRecargasRequestBean(a2, new GetRecargasBodyRequestBean(str8, str9, str10));
                final AnonymousClass1 r0 = new GetRecargasRequest(DataManager.this.b.getApplicationContext(), getRecargasRequestBean, DataManager.this.createErrorRequestServer(getRecargasEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetRecargasResponseBean getRecargasResponseBean = (GetRecargasResponseBean) iBeanWS;
                        getRecargasEvent.setResponse(getRecargasResponseBean);
                        getRecargasEvent.setBeanResponse(getRecargasResponseBean);
                        getRecargasEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getRecargasEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getRecargasEvent, VRecargas.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VRecargas.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VRecargas.nameService);
    }

    public void getCotizacionSeguroMovil() {
        a((Runnable) new Runnable() {
            public void run() {
                final GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent = new GetCotizacionSeguroMovilEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                AnonymousClass1 r0 = new GetCotizacionSeguroMovilRequest(DataManager.this.b.getApplicationContext(), new GetCotizacionSeguroMovilRequestBean(DataManager.this.a(VGetCotizacionSeguroMovil.getData(EVersionServices.CURRENT), session, nup), new GetCotizacionSeguroMovilBodyRequestBean()), DataManager.this.createErrorRequestServer(getCotizacionSeguroMovilEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetCotizacionSeguroMovilResponseBean getCotizacionSeguroMovilResponseBean = (GetCotizacionSeguroMovilResponseBean) iBeanWS;
                        getCotizacionSeguroMovilEvent.setResponse(getCotizacionSeguroMovilResponseBean);
                        getCotizacionSeguroMovilEvent.setBeanResponse(getCotizacionSeguroMovilResponseBean);
                        getCotizacionSeguroMovilEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getCotizacionSeguroMovilEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getCotizacionSeguroMovilEvent, VGetCotizacionSeguroMovil.nameService);
                    }
                };
                DataManager.this.a((BaseRequest) r0);
                r0.sendRequest(VGetCotizacionSeguroMovil.nameService);
            }
        }, VGetCotizacionSeguroMovil.nameService);
    }

    public void getCotizacionCompraProtegida() {
        a((Runnable) new Runnable() {
            public void run() {
                final GetCotizacionCompraProtegidaEvent getCotizacionCompraProtegidaEvent = new GetCotizacionCompraProtegidaEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetCotizacionCompraProtegidaRequest(DataManager.this.b.getApplicationContext(), new GetCotizacionCompraProtegidaRequestBean(DataManager.this.a(VGetCotizacionCompraProtegida.getData(EVersionServices.CURRENT), session, nup), new GetCotizacionCompraProtegidaBodyRequestBean()), DataManager.this.createErrorRequestServer(getCotizacionCompraProtegidaEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetCotizacionCompraProtegidaResponseBean getCotizacionCompraProtegidaResponseBean = (GetCotizacionCompraProtegidaResponseBean) iBeanWS;
                        getCotizacionCompraProtegidaEvent.setResponse(getCotizacionCompraProtegidaResponseBean);
                        getCotizacionCompraProtegidaEvent.setBeanResponse(getCotizacionCompraProtegidaResponseBean);
                        getCotizacionCompraProtegidaEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getCotizacionCompraProtegidaEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getCotizacionCompraProtegidaEvent, VGetCotizacionCompraProtegida.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetCotizacionCompraProtegida.nameService);
                    }
                });
            }
        }, VGetCotizacionCompraProtegida.nameService);
    }

    public void contratarSeguroMovil(String str, String str2, String str3, String str4, String str5, String str6, String str7, BajaSeguroBean bajaSeguroBean, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean) {
        final String str8 = str;
        final String str9 = str2;
        final String str10 = str3;
        final String str11 = str4;
        final String str12 = str5;
        final String str13 = str6;
        final String str14 = str7;
        final BajaSeguroBean bajaSeguroBean2 = bajaSeguroBean;
        final CuentaShortBean cuentaShortBean2 = cuentaShortBean;
        final TarjetaBean tarjetaBean2 = tarjetaBean;
        AnonymousClass84 r0 = new Runnable() {
            public void run() {
                final ContratarSeguroMovilEvent contratarSeguroMovilEvent = new ContratarSeguroMovilEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                Context applicationContext = DataManager.this.b.getApplicationContext();
                PrivateHeaderBean a2 = DataManager.this.a(VContratarSeguroMovil.getData(EVersionServices.CURRENT), session, nup);
                String str = str8;
                String str2 = str9;
                String str3 = str10;
                String str4 = str11;
                String str5 = str12;
                String str6 = str13;
                String str7 = str14;
                BajaSeguroBean bajaSeguroBean = bajaSeguroBean2;
                Context context = applicationContext;
                BajaSeguroBean bajaSeguroBean2 = bajaSeguroBean;
                ContratarSeguroMovilBodyRequestBean contratarSeguroMovilBodyRequestBean = new ContratarSeguroMovilBodyRequestBean(str, str2, str3, str4, str5, str6, str7, bajaSeguroBean2, cuentaShortBean2, tarjetaBean2);
                final AnonymousClass1 r0 = new ContratarSeguroMovilRequest(context, new ContratarSeguroMovilRequestBean(a2, contratarSeguroMovilBodyRequestBean), DataManager.this.createErrorRequestServer(contratarSeguroMovilEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        ContratarSeguroMovilResponseBean contratarSeguroMovilResponseBean = (ContratarSeguroMovilResponseBean) iBeanWS;
                        contratarSeguroMovilEvent.setResponse(contratarSeguroMovilResponseBean);
                        contratarSeguroMovilEvent.setBeanResponse(contratarSeguroMovilResponseBean);
                        contratarSeguroMovilEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(contratarSeguroMovilEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, contratarSeguroMovilEvent, VContratarSeguroMovil.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VContratarSeguroMovil.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VContratarSeguroMovil.nameService);
    }

    public void contratarCompraProtegida(String str, String str2, String str3, String str4, List<TarjetaBean> list, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean) {
        final String str5 = str;
        final String str6 = str2;
        final String str7 = str3;
        final String str8 = str4;
        final List<TarjetaBean> list2 = list;
        final CuentaShortBean cuentaShortBean2 = cuentaShortBean;
        final TarjetaBean tarjetaBean2 = tarjetaBean;
        AnonymousClass85 r0 = new Runnable() {
            public void run() {
                final ContratarCompraProtegidaEvent contratarCompraProtegidaEvent = new ContratarCompraProtegidaEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                Context applicationContext = DataManager.this.b.getApplicationContext();
                PrivateHeaderBean a2 = DataManager.this.a(VContratarCompraProtegida.getData(EVersionServices.CURRENT), session, nup);
                ContratarCompraProtegidaBodyRequestBean contratarCompraProtegidaBodyRequestBean = new ContratarCompraProtegidaBodyRequestBean(str5, str6, str7, str8, new TarjetasBean(list2), cuentaShortBean2, tarjetaBean2);
                final AnonymousClass1 r0 = new ContratarCompraProtegidaRequest(applicationContext, new ContratarCompraProtegidaRequestBean(a2, contratarCompraProtegidaBodyRequestBean), DataManager.this.createErrorRequestServer(contratarCompraProtegidaEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        ContratarCompraProtegidaResponseBean contratarCompraProtegidaResponseBean = (ContratarCompraProtegidaResponseBean) iBeanWS;
                        contratarCompraProtegidaEvent.setResponse(contratarCompraProtegidaResponseBean);
                        contratarCompraProtegidaEvent.setBeanResponse(contratarCompraProtegidaResponseBean);
                        contratarCompraProtegidaEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(contratarCompraProtegidaEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, contratarCompraProtegidaEvent, VContratarCompraProtegida.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VContratarCompraProtegida.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VContratarCompraProtegida.nameService);
    }

    public void getPoliza(final String str, final String str2, final String str3) {
        a((Runnable) new Runnable() {
            public void run() {
                final GetPolizaEvent getPolizaEvent = new GetPolizaEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetPolizaRequest(DataManager.this.b.getApplicationContext(), new GetPolizaRequestBean(DataManager.this.a(VGetPoliza.getData(EVersionServices.CURRENT), session, nup), new GetPolizaBodyRequestBean(str, str2, str3)), DataManager.this.createErrorRequestServer(getPolizaEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetPolizaResponseBean getPolizaResponseBean = (GetPolizaResponseBean) iBeanWS;
                        getPolizaEvent.setBeanResponse(getPolizaResponseBean);
                        getPolizaEvent.setResponse(getPolizaResponseBean);
                        getPolizaEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getPolizaEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getPolizaEvent, VGetPoliza.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetPoliza.nameService);
                    }
                });
            }
        }, VGetPoliza.nameService);
    }

    public void getOcupaciones() {
        a((Runnable) new Runnable() {
            public void run() {
                final GetOcupacionesEvent getOcupacionesEvent = new GetOcupacionesEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetOcupacionesRequest(DataManager.this.b.getApplicationContext(), new GetOcupacionesRequestBean(DataManager.this.a(VGetOcupaciones.getData(EVersionServices.CURRENT), session, nup), new GetOcupacionesBodyRequestBean(DataManager.this.a.getGetOcupacionesPrevHashCode())), DataManager.this.createErrorRequestServer(getOcupacionesEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetOcupacionesResponseBean getOcupacionesResponseBean = (GetOcupacionesResponseBean) iBeanWS;
                        getOcupacionesEvent.setBeanResponse(getOcupacionesResponseBean);
                        getOcupacionesEvent.setResponse(getOcupacionesResponseBean);
                        getOcupacionesEvent.setResult(TypeResult.OK);
                        DataManager.this.a.setGetOcupacionesPrevHashCode(getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().getHashCode());
                        if (getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().getOcupaciones() == null || getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().getOcupaciones().getListOcupaciones().size() <= 0) {
                            getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().setOcupaciones(DataManager.this.a.getOcupaciones());
                        } else {
                            DataManager.this.a.setOcupaciones(getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().getOcupaciones());
                        }
                        DataManager.this.sendBusEvent(getOcupacionesEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getOcupacionesEvent, VGetOcupaciones.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetOcupaciones.nameService);
                    }
                });
            }
        }, VGetOcupaciones.nameService);
    }

    public void getTenenciaFondos() {
        final GetTenenciaFondosEvent getTenenciaFondosEvent = new GetTenenciaFondosEvent();
        AnonymousClass88 r0 = new GetTenenciaFondosRequest(this.b.getApplicationContext(), new GetTenenciaFondosRequestBean(a(VGetTenenciaFondos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetTenenciaFondosBodyRequestBean()), createErrorRequestServer(getTenenciaFondosEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetTenenciaFondosResponseBean getTenenciaFondosResponseBean = (GetTenenciaFondosResponseBean) iBeanWS;
                getTenenciaFondosEvent.setBeanResponse(getTenenciaFondosResponseBean);
                getTenenciaFondosEvent.setResponseBean(getTenenciaFondosResponseBean);
                getTenenciaFondosEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getTenenciaFondosEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getTenenciaFondosEvent, VGetTenenciaFondos.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetTenenciaFondos.nameService);
    }

    public void getFondos() {
        final GetFondosEvent getFondosEvent = new GetFondosEvent();
        AnonymousClass89 r0 = new GetFondosRequest(this.b.getApplicationContext(), new GetFondosRequestBean(a(VGetFondos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetFondosBodyRequestBean()), createErrorRequestServer(getFondosEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetFondosResponseBean getFondosResponseBean = (GetFondosResponseBean) iBeanWS;
                getFondosEvent.setBeanResponse(getFondosResponseBean);
                getFondosEvent.setResponseBean(getFondosResponseBean);
                getFondosEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getFondosEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getFondosEvent, VGetFondos.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetFondos.nameService);
    }

    public void getMovimientosFondo(String str, String str2) {
        getMovimientosFondo(str, str2, Boolean.valueOf(false));
    }

    public void getMovimientosFondo(String str, String str2, Boolean bool) {
        final GetMovimientosFondoEvent getMovimientosFondoEvent = new GetMovimientosFondoEvent();
        AnonymousClass90 r0 = new GetMovimientosFondoRequest(this.b.getApplicationContext(), new GetMovimientosFondoRequestBean(a(VGetMovimientosFondo.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetMovimientosFondoBodyRequestBean(str, str2, bool)), createErrorRequestServer(getMovimientosFondoEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetMovimientosFondoResponseBean getMovimientosFondoResponseBean = (GetMovimientosFondoResponseBean) iBeanWS;
                getMovimientosFondoEvent.setBeanResponse(getMovimientosFondoResponseBean);
                getMovimientosFondoEvent.setResponseBean(getMovimientosFondoResponseBean);
                getMovimientosFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getMovimientosFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getMovimientosFondoEvent, VGetMovimientosFondo.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetMovimientosFondo.nameService);
    }

    public void getMovimientosFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        final GetMovimientosFondoEvent getMovimientosFondoEvent = new GetMovimientosFondoEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VGetMovimientosFondo.getData(EVersionServices.CURRENT), session, nup);
        GetMovimientosFondoBodyRequestBean getMovimientosFondoBodyRequestBean = new GetMovimientosFondoBodyRequestBean(str, str2, str3, str4, str5, str6, str7);
        AnonymousClass91 r0 = new GetMovimientosFondoRequest(applicationContext, new GetMovimientosFondoRequestBean(a2, getMovimientosFondoBodyRequestBean), createErrorRequestServer(getMovimientosFondoEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetMovimientosFondoResponseBean getMovimientosFondoResponseBean = (GetMovimientosFondoResponseBean) iBeanWS;
                getMovimientosFondoEvent.setBeanResponse(getMovimientosFondoResponseBean);
                getMovimientosFondoEvent.setResponseBean(getMovimientosFondoResponseBean);
                getMovimientosFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getMovimientosFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getMovimientosFondoEvent, VGetMovimientosFondo.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetMovimientosFondo.nameService);
    }

    public void getDetalleFondo(String str) {
        final GetDetalleFondoEvent getDetalleFondoEvent = new GetDetalleFondoEvent();
        AnonymousClass92 r0 = new GetDetalleFondoRequest(this.b.getApplicationContext(), new GetDetalleFondoRequestBean(a(VGetDetalleFondo.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetDetalleFondoBodyRequestBean(str)), createErrorRequestServer(getDetalleFondoEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getDetalleFondoEvent.setBeanResponse((GetDetalleFondoResponseBean) iBeanWS);
                getDetalleFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getDetalleFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getDetalleFondoEvent, VGetDetalleFondo.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetDetalleFondo.nameService);
    }

    public void getCotizacionesFondos() {
        final GetCotizacionesFondoEvent getCotizacionesFondoEvent = new GetCotizacionesFondoEvent();
        AnonymousClass93 r0 = new GetCotizacionesRequest(this.b.getApplicationContext(), new GetCotizacionesRequestBean(a(VGetCotizaciones.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetCotizacionesBodyRequestBean()), createErrorRequestServer(getCotizacionesFondoEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetCotizacionesResponseBean getCotizacionesResponseBean = (GetCotizacionesResponseBean) iBeanWS;
                getCotizacionesFondoEvent.setBeanResponse(getCotizacionesResponseBean);
                getCotizacionesFondoEvent.setResponseBean(getCotizacionesResponseBean);
                getCotizacionesFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getCotizacionesFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getCotizacionesFondoEvent, VGetCotizaciones.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetCotizaciones.nameService);
    }

    public void getInfoAdmFondos() {
        final GetInfoAdmFondosEvent getInfoAdmFondosEvent = new GetInfoAdmFondosEvent();
        AnonymousClass94 r0 = new GetInfoAdmFondosRequest(this.b.getApplicationContext(), new GetInfoAdmFondosRequestBean(a(VGetInfoAdmFondos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetInfoAdmFondosBodyRequestBean()), createErrorRequestServer(getInfoAdmFondosEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetInfoAdmFondosResponseBean getInfoAdmFondosResponseBean = (GetInfoAdmFondosResponseBean) iBeanWS;
                getInfoAdmFondosEvent.setBeanResponse(getInfoAdmFondosResponseBean);
                getInfoAdmFondosEvent.setResponseBean(getInfoAdmFondosResponseBean);
                getInfoAdmFondosEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getInfoAdmFondosEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getInfoAdmFondosEvent, VGetInfoAdmFondos.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetInfoAdmFondos.nameService);
    }

    public void rescatarFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        final RescatarFondoEvent rescatarFondoEvent = new RescatarFondoEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VRescatarFondo.getData(EVersionServices.CURRENT), session, nup);
        RescatarFondoBodyRequestBean rescatarFondoBodyRequestBean = new RescatarFondoBodyRequestBean(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11);
        AnonymousClass95 r0 = new RescatarFondoRequest(applicationContext, new RescatarFondoRequestBean(a2, rescatarFondoBodyRequestBean), createErrorRequestServer(rescatarFondoEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                rescatarFondoEvent.setBeanResponse((RescatarFondoResponseBean) iBeanWS);
                rescatarFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(rescatarFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, rescatarFondoEvent, VRescatarFondo.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VRescatarFondo.nameService);
    }

    public void simularRescateFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        final RescatarFondoEvent rescatarFondoEvent = new RescatarFondoEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VSimularRescateFondo.getData(EVersionServices.CURRENT), session, nup);
        RescatarFondoBodyRequestBean rescatarFondoBodyRequestBean = new RescatarFondoBodyRequestBean(str, str2, str3, str4, str5, str6, str7, str8, str9, null, null);
        AnonymousClass96 r0 = new RescatarFondoRequest(applicationContext, new RescatarFondoRequestBean(a2, rescatarFondoBodyRequestBean), createErrorRequestServer(rescatarFondoEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                rescatarFondoEvent.setBeanResponse((RescatarFondoResponseBean) iBeanWS);
                rescatarFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(rescatarFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, rescatarFondoEvent, VSimularRescateFondo.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VSimularRescateFondo.nameService);
    }

    public void suscribirFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        final SuscribirFondoEvent suscribirFondoEvent = new SuscribirFondoEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VSuscribirFondo.getData(EVersionServices.CURRENT), session, nup);
        SuscribirFondoBodyRequestBean suscribirFondoBodyRequestBean = new SuscribirFondoBodyRequestBean(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13);
        AnonymousClass97 r0 = new SuscribirFondoRequest(applicationContext, new SuscribirFondoRequestBean(a2, suscribirFondoBodyRequestBean), createErrorRequestServer(suscribirFondoEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                SuscribirFondoResponseBean suscribirFondoResponseBean = (SuscribirFondoResponseBean) iBeanWS;
                suscribirFondoEvent.setBeanResponse(suscribirFondoResponseBean);
                suscribirFondoEvent.setResponseBean(suscribirFondoResponseBean);
                suscribirFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(suscribirFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, suscribirFondoEvent, VSuscribirFondo.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VSuscribirFondo.nameService);
    }

    public void simularSuscripcionFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        final SimularSuscripcionFondoEvent simularSuscripcionFondoEvent = new SimularSuscripcionFondoEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VSimularSuscripcionFondo.getData(EVersionServices.CURRENT), session, nup);
        SuscribirFondoBodyRequestBean suscribirFondoBodyRequestBean = new SuscribirFondoBodyRequestBean(str, str2, str3, str4, str5, str6, str7, str8, str9, null, null, null, null);
        AnonymousClass98 r0 = new SimularSuscripcionFondoRequest(applicationContext, new SuscribirFondoRequestBean(a2, suscribirFondoBodyRequestBean), createErrorRequestServer(simularSuscripcionFondoEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                simularSuscripcionFondoEvent.setBeanResponse((SimularSuscripcionFondoResponseBean) iBeanWS);
                simularSuscripcionFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(simularSuscripcionFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, simularSuscripcionFondoEvent, VSimularSuscripcionFondo.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VSimularSuscripcionFondo.nameService);
    }

    public void transferirFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        final TransferirFondoEvent transferirFondoEvent = new TransferirFondoEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VTransferirFondo.getData(EVersionServices.CURRENT), session, nup);
        TransferirFondoBodyRequestBean transferirFondoBodyRequestBean = new TransferirFondoBodyRequestBean(str, str2, str3, str4, str5, str6, str7, str10, str11, str12, str8, str9);
        AnonymousClass99 r0 = new TransferirFondoRequest(applicationContext, new TransferirFondoRequestBean(a2, transferirFondoBodyRequestBean), createErrorRequestServer(transferirFondoEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                transferirFondoEvent.setBeanResponse((TransferirFondoResponseBean) iBeanWS);
                transferirFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(transferirFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, transferirFondoEvent, VTransferirFondo.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VTransferirFondo.nameService);
    }

    public void simularTransferenciaFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        final SimularTransferenciaFondoEvent simularTransferenciaFondoEvent = new SimularTransferenciaFondoEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VSimularTransferenciaFondo.getData(EVersionServices.CURRENT), session, nup);
        TransferirFondoBodyRequestBean transferirFondoBodyRequestBean = new TransferirFondoBodyRequestBean(str, str2, str3, str4, str5, str6, str7);
        AnonymousClass100 r0 = new SimularTransferenciaFondoRequest(applicationContext, new TransferirFondoRequestBean(a2, transferirFondoBodyRequestBean), createErrorRequestServer(simularTransferenciaFondoEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                simularTransferenciaFondoEvent.setBeanResponse((SimularTransferenciaFondoResponseBean) iBeanWS);
                simularTransferenciaFondoEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(simularTransferenciaFondoEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, simularTransferenciaFondoEvent, VSimularTransferenciaFondo.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VSimularTransferenciaFondo.nameService);
    }

    public void consultaAlias(String str, String str2, String str3) {
        final ConsultaAliasEvent consultaAliasEvent = new ConsultaAliasEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        CuentaShortBean cuentaShortBean = new CuentaShortBean(str, str2, str3);
        AnonymousClass101 r0 = new ConsultaAliasRequest(this.b.getApplicationContext(), new ConsultaAliasRequestBean(a(VConsultaAlias.getData(EVersionServices.CURRENT), session, nup), new ConsultaAliasBodyRequestBean(cuentaShortBean)), createErrorRequestServer(consultaAliasEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                ConsultaAliasResponseBean consultaAliasResponseBean = (ConsultaAliasResponseBean) iBeanWS;
                consultaAliasEvent.setBeanResponse(consultaAliasResponseBean);
                consultaAliasEvent.setResponse(consultaAliasResponseBean);
                consultaAliasEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(consultaAliasEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, consultaAliasEvent, VConsultaAlias.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VConsultaAlias.nameService);
    }

    public void getNotificacionesPush(String str) {
        final GetNotificacionesPushEvent getNotificacionesPushEvent = new GetNotificacionesPushEvent();
        AnonymousClass102 r0 = new GetNotificacionesPushRequest(this.b.getApplicationContext(), new GetNotificacionesPushRequestBean(a(VGetNotificacionesPush.getData(EVersionServices.CURRENT)), new GetNotificacionesPushBodyRequestBean(TwinPushSDK.getInstance(this.b.getApplicationContext()).getApiKey(), TwinPushSDK.getInstance(this.b.getApplicationContext()).getAppId(), TwinPushSDK.getInstance(this.b.getApplicationContext()).getDeviceId(), str)), createErrorRequestServer(getNotificacionesPushEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getNotificacionesPushEvent.setResponseBean((GetNotificacionesPushResponseBean) iBeanWS);
                getNotificacionesPushEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getNotificacionesPushEvent);
            }

            public void onUnknowError(Exception exc) {
                DataManager.this.createUnknowErrorEventProcesor(exc, getNotificacionesPushEvent, VGetNotificacionesPush.nameService);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetNotificacionesPush.nameService);
    }

    private void a(Runnable runnable, String str) {
        BackgroundThread backgroundThread = new BackgroundThread(str);
        backgroundThread.start();
        backgroundThread.prepareHandler();
        backgroundThread.postTask(runnable);
    }

    /* access modifiers changed from: protected */
    public void createUnknowErrorEventProcesor(Exception exc, WebServiceEvent webServiceEvent, String str) {
        Crashlytics.logException(exc);
        Log.e(str, "createUnknowErrorEventProcesor: ", exc);
        if (webServiceEvent != null) {
            webServiceEvent.setResult(TypeResult.UNKNOWN_ERROR);
            webServiceEvent.setMessageToShow("");
            webServiceEvent.setTitleToShow("");
            sendBusEvent(webServiceEvent);
        }
    }

    public void getViajes() {
        final GetViajesEvent getViajesEvent = new GetViajesEvent();
        AnonymousClass103 r0 = new GetViajesRequest(this.b.getApplicationContext(), new GetViajesRequestBean(a(VGetViajes.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetViajesBodyRequestBean()), createErrorRequestServer(getViajesEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetViajesResponseBean getViajesResponseBean = (GetViajesResponseBean) iBeanWS;
                getViajesEvent.setBeanResponse(getViajesResponseBean);
                getViajesEvent.setResponse(getViajesResponseBean);
                getViajesEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getViajesEvent);
            }

            public void onUnknowError(Exception exc) {
                getViajesEvent.setResult(TypeResult.UNKNOWN_ERROR);
                getViajesEvent.setMessageToShow("");
                getViajesEvent.setTitleToShow("");
                DataManager.this.sendBusEvent(getViajesEvent);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetViajes.nameService);
    }

    public void getTarjPaises() {
        final GetTarjPaisesEvent getTarjPaisesEvent = new GetTarjPaisesEvent();
        AnonymousClass104 r0 = new GetTarjPaisesRequest(this.b.getApplicationContext(), new GetTarjPaisesRequestBean(a(VGetTarjPaises.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetTarjPaisesBodyRequestBean()), createErrorRequestServer(getTarjPaisesEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetTarjPaisesResponseBean getTarjPaisesResponseBean = (GetTarjPaisesResponseBean) iBeanWS;
                getTarjPaisesEvent.setBeanResponse(getTarjPaisesResponseBean);
                getTarjPaisesEvent.setResponse(getTarjPaisesResponseBean);
                getTarjPaisesEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(getTarjPaisesEvent);
            }

            public void onUnknowError(Exception exc) {
                getTarjPaisesEvent.setResult(TypeResult.UNKNOWN_ERROR);
                getTarjPaisesEvent.setMessageToShow("");
                getTarjPaisesEvent.setTitleToShow("");
                DataManager.this.sendBusEvent(getTarjPaisesEvent);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VGetTarjPaises.nameService);
    }

    public void abmViajes(String str, String str2, String str3, String str4, String str5, String str6, PaisesBean paisesBean, TarjetasMarcacionBean tarjetasMarcacionBean) {
        final ABMViajesEvent aBMViajesEvent = new ABMViajesEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.b.getApplicationContext();
        PrivateHeaderBean a2 = a(VABMViajes.getData(EVersionServices.CURRENT), session, nup);
        ViajeBean viajeBean = new ViajeBean(str3, str4, str5, str6, paisesBean, tarjetasMarcacionBean);
        AnonymousClass105 r0 = new ABMViajesRequest(applicationContext, new ABMViajesRequestBean(a2, new ABMViajesBodyRequestBean(str, str2, viajeBean)), createErrorRequestServer(aBMViajesEvent, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                aBMViajesEvent.setBeanResponse((ABMViajesResponseBean) iBeanWS);
                aBMViajesEvent.setResult(TypeResult.OK);
                DataManager.this.sendBusEvent(aBMViajesEvent);
            }

            public void onUnknowError(Exception exc) {
                aBMViajesEvent.setResult(TypeResult.UNKNOWN_ERROR);
                aBMViajesEvent.setMessageToShow("");
                aBMViajesEvent.setTitleToShow("");
                DataManager.this.sendBusEvent(aBMViajesEvent);
            }
        };
        a((BaseRequest) r0);
        r0.sendRequest(VABMViajes.nameService);
    }

    public void getDebines(String str, String str2, String str3, String str4, String str5) {
        final String str6 = str;
        final String str7 = str2;
        final String str8 = str3;
        final String str9 = str4;
        final String str10 = str5;
        AnonymousClass106 r0 = new Runnable() {
            public void run() {
                final GetDebinesEvent getDebinesEvent = new GetDebinesEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                Context applicationContext = DataManager.this.b.getApplicationContext();
                PrivateHeaderBean a2 = DataManager.this.a(VGetDebines.getData(EVersionServices.CURRENT), session, nup);
                GetDebinesBodyRequestBean getDebinesBodyRequestBean = new GetDebinesBodyRequestBean(str6, str7, str8, str9, str10);
                final AnonymousClass1 r0 = new GetDebinesRequest(applicationContext, new GetDebinesRequestBean(a2, getDebinesBodyRequestBean), DataManager.this.createErrorRequestServer(getDebinesEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetDebinesResponseBean getDebinesResponseBean = (GetDebinesResponseBean) iBeanWS;
                        getDebinesEvent.setResponse(getDebinesResponseBean);
                        getDebinesEvent.setBeanResponse(getDebinesResponseBean);
                        getDebinesEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getDebinesEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getDebinesEvent, VGetDebines.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetDebines.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VGetDebines.nameService);
    }

    public void getDebinesBusquedaAvanzada(String str, String str2, String str3, String str4, String str5) {
        final String str6 = str;
        final String str7 = str2;
        final String str8 = str3;
        final String str9 = str4;
        final String str10 = str5;
        AnonymousClass107 r0 = new Runnable() {
            public void run() {
                final GetDebinesBusquedaAvanzadaEvent getDebinesBusquedaAvanzadaEvent = new GetDebinesBusquedaAvanzadaEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                Context applicationContext = DataManager.this.b.getApplicationContext();
                PrivateHeaderBean a2 = DataManager.this.a(VGetDebines.getData(EVersionServices.CURRENT), session, nup);
                GetDebinesBodyRequestBean getDebinesBodyRequestBean = new GetDebinesBodyRequestBean(str6, str7, str8, str9, str10);
                final AnonymousClass1 r0 = new GetDebinesRequest(applicationContext, new GetDebinesRequestBean(a2, getDebinesBodyRequestBean), DataManager.this.createErrorRequestServer(getDebinesBusquedaAvanzadaEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetDebinesResponseBean getDebinesResponseBean = (GetDebinesResponseBean) iBeanWS;
                        getDebinesBusquedaAvanzadaEvent.setResponse(getDebinesResponseBean);
                        getDebinesBusquedaAvanzadaEvent.setBeanResponse(getDebinesResponseBean);
                        getDebinesBusquedaAvanzadaEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getDebinesBusquedaAvanzadaEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getDebinesBusquedaAvanzadaEvent, VGetDebines.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetDebines.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VGetDebines.nameService);
    }

    public void getDetalleDebinComprador(final String str) {
        a((Runnable) new Runnable() {
            public void run() {
                final GetDetalleDebinCompradorEvent getDetalleDebinCompradorEvent = new GetDetalleDebinCompradorEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetDetalleDebinCompradorRequest(DataManager.this.b.getApplicationContext(), new GetDetalleDebinCompradorRequestBean(DataManager.this.a(VGetDetalleDebinComprador.getData(EVersionServices.CURRENT), session, nup), new GetDetalleDebinCompradorBodyRequestBean(str)), DataManager.this.createErrorRequestServer(getDetalleDebinCompradorEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetDetalleDebinCompradorResponseBean getDetalleDebinCompradorResponseBean = (GetDetalleDebinCompradorResponseBean) iBeanWS;
                        getDetalleDebinCompradorEvent.setResponse(getDetalleDebinCompradorResponseBean);
                        getDetalleDebinCompradorEvent.setBeanResponse(getDetalleDebinCompradorResponseBean);
                        getDetalleDebinCompradorEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getDetalleDebinCompradorEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getDetalleDebinCompradorEvent, VGetDebines.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetDetalleDebinComprador.nameService);
                    }
                });
            }
        }, VGetDebines.nameService);
    }

    public void getDetalleDebinVendedor(final String str) {
        a((Runnable) new Runnable() {
            public void run() {
                final GetDetalleDebinVendedorEvent getDetalleDebinVendedorEvent = new GetDetalleDebinVendedorEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetDetalleDebinVendedorRequest(DataManager.this.b.getApplicationContext(), new GetDetalleDebinVendedorRequestBean(DataManager.this.a(VGetDetalleDebinVendedor.getData(EVersionServices.CURRENT), session, nup), new GetDetalleDebinVendedorBodyRequestBean(str)), DataManager.this.createErrorRequestServer(getDetalleDebinVendedorEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetDetalleDebinVendedorResponseBean getDetalleDebinVendedorResponseBean = (GetDetalleDebinVendedorResponseBean) iBeanWS;
                        getDetalleDebinVendedorEvent.setResponse(getDetalleDebinVendedorResponseBean);
                        getDetalleDebinVendedorEvent.setBeanResponse(getDetalleDebinVendedorResponseBean);
                        getDetalleDebinVendedorEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getDetalleDebinVendedorEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getDetalleDebinVendedorEvent, VGetDebines.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetDetalleDebinVendedor.nameService);
                    }
                });
            }
        }, VGetDebines.nameService);
    }

    public void abmDebinVendedor(String str, DetalleDebinBean detalleDebinBean, String str2, Activity activity, boolean z) {
        final boolean z2 = z;
        final Activity activity2 = activity;
        final String str3 = str;
        final DetalleDebinBean detalleDebinBean2 = detalleDebinBean;
        final String str4 = str2;
        AnonymousClass110 r0 = new Runnable() {
            public void run() {
                final AbmDebinVendedorEvent abmDebinVendedorEvent = new AbmDebinVendedorEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new AbmDebinVendedorRequest(DataManager.this.b.getApplicationContext(), new AbmDebinVendedorRequestBean(DataManager.this.a(VGetAbmDebinVendedor.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(z2), Boolean.valueOf(z2), activity2), new AbmDebinVendedorBodyRequestBean(str3, detalleDebinBean2, str4)), DataManager.this.createErrorRequestServer(abmDebinVendedorEvent, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        AbmDebinVendedorResponseBean abmDebinVendedorResponseBean = (AbmDebinVendedorResponseBean) iBeanWS;
                        abmDebinVendedorEvent.setResponse(abmDebinVendedorResponseBean);
                        abmDebinVendedorEvent.setBeanResponse(abmDebinVendedorResponseBean);
                        abmDebinVendedorEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(abmDebinVendedorEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, abmDebinVendedorEvent, VGetDebines.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetDetalleDebinVendedor.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VGetDebines.nameService);
    }

    public void abmDebinComprador(String str, String str2, DetalleDebinBean detalleDebinBean, Activity activity, boolean z) {
        final boolean z2 = z;
        final Activity activity2 = activity;
        final String str3 = str;
        final String str4 = str2;
        final DetalleDebinBean detalleDebinBean2 = detalleDebinBean;
        AnonymousClass111 r0 = new Runnable() {
            public void run() {
                final AbmDebinCompradorEvent abmDebinCompradorEvent = new AbmDebinCompradorEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new AbmDebinCompradorRequest(DataManager.this.b.getApplicationContext(), new AbmDebinCompradorRequestBean(DataManager.this.a(VGetAbmDebinComprador.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(z2), Boolean.valueOf(z2), activity2), new AbmDebinCompradorBodyRequestBean(str3, str4, detalleDebinBean2)), DataManager.this.createErrorRequestServer(abmDebinCompradorEvent, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        AbmDebinCompradorResponseBean abmDebinCompradorResponseBean = (AbmDebinCompradorResponseBean) iBeanWS;
                        abmDebinCompradorEvent.setResponse(abmDebinCompradorResponseBean);
                        abmDebinCompradorEvent.setBeanResponse(abmDebinCompradorResponseBean);
                        abmDebinCompradorEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(abmDebinCompradorEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, abmDebinCompradorEvent, VGetDebines.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetDetalleDebinVendedor.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VGetDebines.nameService);
    }

    public void abmDebinComprador(String str, DetalleDebinBean detalleDebinBean, Activity activity, boolean z) {
        final boolean z2 = z;
        final Activity activity2 = activity;
        final String str2 = str;
        final DetalleDebinBean detalleDebinBean2 = detalleDebinBean;
        AnonymousClass112 r0 = new Runnable() {
            public void run() {
                final AbmDebinCompradorEvent abmDebinCompradorEvent = new AbmDebinCompradorEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new AbmDebinCompradorRequest(DataManager.this.b.getApplicationContext(), new AbmDebinCompradorRequestBean(DataManager.this.a(VGetAbmDebinComprador.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(z2), Boolean.valueOf(z2), activity2), new AbmDebinCompradorBodyRequestBean(str2, null, detalleDebinBean2)), DataManager.this.createErrorRequestServer(abmDebinCompradorEvent, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        AbmDebinCompradorResponseBean abmDebinCompradorResponseBean = (AbmDebinCompradorResponseBean) iBeanWS;
                        abmDebinCompradorEvent.setResponse(abmDebinCompradorResponseBean);
                        abmDebinCompradorEvent.setBeanResponse(abmDebinCompradorResponseBean);
                        abmDebinCompradorEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(abmDebinCompradorEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, abmDebinCompradorEvent, VGetDebines.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetAbmDebinComprador.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VGetDebines.nameService);
    }

    public void abmAdhesionVendedor(String str, CuentaVendedor cuentaVendedor, Activity activity, boolean z) {
        final boolean z2 = z;
        final Activity activity2 = activity;
        final String str2 = str;
        final CuentaVendedor cuentaVendedor2 = cuentaVendedor;
        AnonymousClass113 r0 = new Runnable() {
            public void run() {
                final AbmAdhesionVendedorEvent abmAdhesionVendedorEvent = new AbmAdhesionVendedorEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new AbmAdhesionVendedorRequest(DataManager.this.b.getApplicationContext(), new AbmAdhesionVendedorRequestBean(DataManager.this.a(VAbmAdhesionVendedorDebin.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(z2), Boolean.valueOf(z2), activity2), new AbmAdhesionVendedorBodyRequestBean(str2, cuentaVendedor2)), DataManager.this.createErrorRequestServer(abmAdhesionVendedorEvent, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        AbmAdhesionVendedorResponseBean abmAdhesionVendedorResponseBean = (AbmAdhesionVendedorResponseBean) iBeanWS;
                        abmAdhesionVendedorEvent.setResponse(abmAdhesionVendedorResponseBean);
                        abmAdhesionVendedorEvent.setBeanResponse(abmAdhesionVendedorResponseBean);
                        abmAdhesionVendedorEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(abmAdhesionVendedorEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, abmAdhesionVendedorEvent, VAbmAdhesionVendedorDebin.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VAbmAdhesionVendedorDebin.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VAbmAdhesionVendedorDebin.nameService);
    }

    public void consultarTitularCuenta(final String str, final String str2) {
        a((Runnable) new Runnable() {
            public void run() {
                final ConsultarTitularCuentaEvent consultarTitularCuentaEvent = new ConsultarTitularCuentaEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new ConsultarTitularCuentaRequest(DataManager.this.b.getApplicationContext(), new ConsultarTitularCuentaRequestBean(DataManager.this.a(VGetConsultarTitularCuenta.getData(EVersionServices.CURRENT), session, nup), new ConsultarTitularCuentaBodyRequestBean(str, str2)), DataManager.this.createErrorRequestServer(consultarTitularCuentaEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        ConsultarTitularCuentaResponseBean consultarTitularCuentaResponseBean = (ConsultarTitularCuentaResponseBean) iBeanWS;
                        consultarTitularCuentaEvent.setResponse(consultarTitularCuentaResponseBean);
                        consultarTitularCuentaEvent.setBeanResponse(consultarTitularCuentaResponseBean);
                        consultarTitularCuentaEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(consultarTitularCuentaEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, consultarTitularCuentaEvent, VGetConsultarTitularCuenta.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetConsultarTitularCuenta.nameService);
                    }
                });
            }
        }, VGetConsultarTitularCuenta.nameService);
    }

    public void consultarAdhesionVendedor() {
        a((Runnable) new Runnable() {
            public void run() {
                final ConsultarAdhesionVendedorEvent consultarAdhesionVendedorEvent = new ConsultarAdhesionVendedorEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new ConsultarAdhesionVendedorRequest(DataManager.this.b.getApplicationContext(), new ConsultarAdhesionVendedorRequestBean(DataManager.this.a(VGetConsultarAdhesionVendedor.getData(EVersionServices.CURRENT), session, nup), new ConsultarAdhesionVendedorBodyRequestBean()), DataManager.this.createErrorRequestServer(consultarAdhesionVendedorEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        ConsultarAdhesionVendedorResponseBean consultarAdhesionVendedorResponseBean = (ConsultarAdhesionVendedorResponseBean) iBeanWS;
                        consultarAdhesionVendedorEvent.setResponse(consultarAdhesionVendedorResponseBean);
                        consultarAdhesionVendedorEvent.setBeanResponse(consultarAdhesionVendedorResponseBean);
                        consultarAdhesionVendedorEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(consultarAdhesionVendedorEvent);
                        DataManager.this.sessionManager.setConsultarAdhesionVendedorBodyResponseBean(consultarAdhesionVendedorResponseBean.getConsultarAdhesionVendedorBodyResponseBean());
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, consultarAdhesionVendedorEvent, VGetConsultarAdhesionVendedor.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetConsultarAdhesionVendedor.nameService);
                    }
                });
            }
        }, VGetConsultarAdhesionVendedor.nameService);
    }

    public void getPreautorizaciones(final String str, final String str2, final String str3) {
        a((Runnable) new Runnable() {
            public void run() {
                final GetPreAutorizacionesEvent getPreAutorizacionesEvent = new GetPreAutorizacionesEvent();
                if (str3 == null || str3.equals("0")) {
                    getPreAutorizacionesEvent.setUpdate(false);
                } else {
                    getPreAutorizacionesEvent.setUpdate(true);
                }
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetPreAutorizacionesRequest(DataManager.this.b.getApplicationContext(), new GetPreAutorizacionesRequestBean(DataManager.this.a(VGetPreAutorizaciones.getData(EVersionServices.CURRENT), session, nup), new GetPreAutorizacionesBodyRequestBean(str, str2, str3)), DataManager.this.createErrorRequestServer(getPreAutorizacionesEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetPreAutorizacionesResponseBean getPreAutorizacionesResponseBean = (GetPreAutorizacionesResponseBean) iBeanWS;
                        getPreAutorizacionesEvent.setResponse(getPreAutorizacionesResponseBean);
                        getPreAutorizacionesEvent.setBeanResponse(getPreAutorizacionesResponseBean);
                        getPreAutorizacionesEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getPreAutorizacionesEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getPreAutorizacionesEvent, VGetPreAutorizaciones.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetPreAutorizaciones.nameService);
                    }
                });
            }
        }, VGetPreAutorizaciones.nameService);
    }

    public void contratarSeguroAccidente(CotizacionSeguroAccidenteBean cotizacionSeguroAccidenteBean, String str, String str2, String str3) {
        final CotizacionSeguroAccidenteBean cotizacionSeguroAccidenteBean2 = cotizacionSeguroAccidenteBean;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        AnonymousClass117 r0 = new Runnable() {
            public void run() {
                final ContratarSeguroAccidenteEvent contratarSeguroAccidenteEvent = new ContratarSeguroAccidenteEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                Context applicationContext = DataManager.this.b.getApplicationContext();
                PrivateHeaderBean a2 = DataManager.this.a(VGetContratarSeguroAccidente.getData(EVersionServices.CURRENT), session, nup);
                ContratarSeguroAccidenteBodyRequestBean contratarSeguroAccidenteBodyRequestBean = new ContratarSeguroAccidenteBodyRequestBean(cotizacionSeguroAccidenteBean2.getCodRamo(), cotizacionSeguroAccidenteBean2.getCodProducto(), cotizacionSeguroAccidenteBean2.getNumCotizacion(), cotizacionSeguroAccidenteBean2.getCodPlan(), str4, str5, str6);
                final AnonymousClass1 r0 = new ContratarSeguroAccidenteRequest(applicationContext, new ContratarSeguroAccidenteRequestBean(a2, contratarSeguroAccidenteBodyRequestBean), DataManager.this.createErrorRequestServer(contratarSeguroAccidenteEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        ContratarSeguroAccidenteResponseBean contratarSeguroAccidenteResponseBean = (ContratarSeguroAccidenteResponseBean) iBeanWS;
                        contratarSeguroAccidenteEvent.setResponse(contratarSeguroAccidenteResponseBean);
                        contratarSeguroAccidenteEvent.setBeanResponse(contratarSeguroAccidenteResponseBean);
                        contratarSeguroAccidenteEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(contratarSeguroAccidenteEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, contratarSeguroAccidenteEvent, VGetContratarSeguroAccidente.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetContratarSeguroAccidente.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VGetContratarSeguroAccidente.nameService);
    }

    public void abmTurno(String str, String str2, String str3, String str4) {
        final String str5 = str;
        final String str6 = str2;
        final String str7 = str3;
        final String str8 = str4;
        AnonymousClass118 r0 = new Runnable() {
            public void run() {
                final AbmTurnoEvent abmTurnoEvent = new AbmTurnoEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new AbmTurnoRequest(DataManager.this.b.getApplicationContext(), new AbmTurnoRequestBean(DataManager.this.a(VAbmTurno.getData(EVersionServices.CURRENT), session, nup), new AbmTurnoBodyRequestBean(str5, str6, str7, str8)), DataManager.this.createErrorRequestServer(abmTurnoEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        AbmTurnoResponseBean abmTurnoResponseBean = (AbmTurnoResponseBean) iBeanWS;
                        abmTurnoEvent.setResponse(abmTurnoResponseBean);
                        abmTurnoEvent.setBeanResponse(abmTurnoResponseBean);
                        abmTurnoEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(abmTurnoEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, abmTurnoEvent, VAbmTurno.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VAbmTurno.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VAbmTurno.nameService);
    }

    public void getPreguntasFamilia(final String str) {
        a((Runnable) new Runnable() {
            public void run() {
                final PreguntasFamiliaEvent preguntasFamiliaEvent = new PreguntasFamiliaEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new PreguntasFamiliaRequest(DataManager.this.b.getApplicationContext(), new PreguntasFamiliaResquestBean(DataManager.this.a(VGetPreguntasFamilia.getData(EVersionServices.CURRENT), session, nup), new PreguntasFamiliaBodyRequestBean(str)), DataManager.this.createErrorRequestServer(preguntasFamiliaEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        PreguntasFamiliaResponseBean preguntasFamiliaResponseBean = (PreguntasFamiliaResponseBean) iBeanWS;
                        preguntasFamiliaEvent.setResponse(preguntasFamiliaResponseBean);
                        preguntasFamiliaEvent.setBeanResponse(preguntasFamiliaResponseBean);
                        preguntasFamiliaEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(preguntasFamiliaEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, preguntasFamiliaEvent, VGetPreguntasFamilia.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetPreguntasFamilia.nameService);
                    }
                });
            }
        }, VGetPreguntasFamilia.nameService);
    }

    public void contratarSeguroObjeto(final ContratarSeguroObjetoBodyRequestBean contratarSeguroObjetoBodyRequestBean) {
        a((Runnable) new Runnable() {
            public void run() {
                final ContratarSeguroObjetoEvent contratarSeguroObjetoEvent = new ContratarSeguroObjetoEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                Context applicationContext = DataManager.this.b.getApplicationContext();
                PrivateHeaderBean a2 = DataManager.this.a(VContratarSeguroObjeto.getData(EVersionServices.CURRENT), session, nup);
                ContratarSeguroObjetoBodyRequestBean contratarSeguroObjetoBodyRequestBean = new ContratarSeguroObjetoBodyRequestBean(contratarSeguroObjetoBodyRequestBean.getCodRamo(), contratarSeguroObjetoBodyRequestBean.getCodProducto(), contratarSeguroObjetoBodyRequestBean.getSumaAsegurada(), contratarSeguroObjetoBodyRequestBean.getNumCotizacion(), contratarSeguroObjetoBodyRequestBean.getCodPlan(), contratarSeguroObjetoBodyRequestBean.getPropietario(), contratarSeguroObjetoBodyRequestBean.getCodOcupacion(), contratarSeguroObjetoBodyRequestBean.getFotoObjeto(), contratarSeguroObjetoBodyRequestBean.getFotoIdObjeto(), contratarSeguroObjetoBodyRequestBean.getUbicacion(), contratarSeguroObjetoBodyRequestBean.getMedioPagoCuenta(), contratarSeguroObjetoBodyRequestBean.getMedioPagoTarjeta());
                final AnonymousClass1 r0 = new ContratarSeguroObjetoRequest(applicationContext, new ContratarSeguroObjetoResquestBean(a2, contratarSeguroObjetoBodyRequestBean), DataManager.this.createErrorRequestServer(contratarSeguroObjetoEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        ContratarSeguroObjetoResponseBean contratarSeguroObjetoResponseBean = (ContratarSeguroObjetoResponseBean) iBeanWS;
                        contratarSeguroObjetoEvent.setResponse(contratarSeguroObjetoResponseBean);
                        contratarSeguroObjetoEvent.setBeanResponse(contratarSeguroObjetoResponseBean);
                        contratarSeguroObjetoEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(contratarSeguroObjetoEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, contratarSeguroObjetoEvent, VContratarSeguroObjeto.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VContratarSeguroObjeto.nameService);
                    }
                });
            }
        }, VContratarSeguroObjeto.nameService);
    }

    public void getFirmaCredin(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                final GetFirmaCredinEvent getFirmaCredinEvent = new GetFirmaCredinEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetFirmaCredinRequest(DataManager.this.b.getApplicationContext(), new GetFirmaCredinRequestBean(DataManager.this.a(VGetFirmaCredin.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(true), Boolean.valueOf(true), activity), new GetFirmaCredinBodyRequestBean()), DataManager.this.createErrorRequestServer(getFirmaCredinEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetFirmaCredinResponseBean getFirmaCredinResponseBean = (GetFirmaCredinResponseBean) iBeanWS;
                        getFirmaCredinEvent.setResponse(getFirmaCredinResponseBean);
                        getFirmaCredinEvent.setBeanResponse(getFirmaCredinResponseBean);
                        getFirmaCredinEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getFirmaCredinEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getFirmaCredinEvent, VGetFirmaCredin.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetFirmaCredin.nameService);
                    }
                });
            }
        }, VGetFirmaCredin.nameService);
    }

    public void getTurno() {
        a((Runnable) new Runnable() {
            public void run() {
                final GetTurnoEvent getTurnoEvent = new GetTurnoEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetTurnoRequest(DataManager.this.b.getApplicationContext(), new GetTurnoRequestBean(DataManager.this.a(VGetTurno.getData(EVersionServices.CURRENT), session, nup), new GetTurnoBodyRequestBean()), DataManager.this.createErrorRequestServer(getTurnoEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetTurnoResponseBean getTurnoResponseBean = (GetTurnoResponseBean) iBeanWS;
                        getTurnoEvent.setResponse(getTurnoResponseBean);
                        getTurnoEvent.setBeanResponse(getTurnoResponseBean);
                        getTurnoEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getTurnoEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getTurnoEvent, VGetTurno.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetTurno.nameService);
                    }
                });
            }
        }, VGetTurno.nameService);
    }

    public void blanqueoPIN(final BlanqueoPinBodyRequestBean blanqueoPinBodyRequestBean, final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                final BlanqueoPINEvent blanqueoPINEvent = new BlanqueoPINEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new BlanqueoPinRequest(DataManager.this.b.getApplicationContext(), new BlanqueoPinRequestBean(DataManager.this.a(VblanqueoPIN.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(true), Boolean.valueOf(true), activity), blanqueoPinBodyRequestBean), DataManager.this.createErrorRequestServer(blanqueoPINEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        BlanqueoPinResponseBean blanqueoPinResponseBean = (BlanqueoPinResponseBean) iBeanWS;
                        blanqueoPINEvent.setBlanqueoPinResponseBean(blanqueoPinResponseBean);
                        blanqueoPINEvent.setBeanResponse(blanqueoPinResponseBean);
                        blanqueoPINEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(blanqueoPINEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, blanqueoPINEvent, VblanqueoPIN.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VblanqueoPIN.nameService);
                    }
                });
            }
        }, VblanqueoPIN.nameService);
    }

    public void getCotizacionSeguroObjeto(String str, ListaPreguntasFamilia listaPreguntasFamilia) {
        a(str, "C", (String) null, (String) null, listaPreguntasFamilia);
    }

    public void getRecotizacionSeguroObjeto(String str, String str2, String str3) {
        a(str, "R", str2, str3, (ListaPreguntasFamilia) null);
    }

    private void a(String str, String str2, String str3, String str4, ListaPreguntasFamilia listaPreguntasFamilia) {
        GetCotizacionSeguroObjetoBodyRequestBean getCotizacionSeguroObjetoBodyRequestBean = new GetCotizacionSeguroObjetoBodyRequestBean(str, str2, str3, str4, listaPreguntasFamilia);
        try {
            final GetCotizacionSeguroObjetoEvent getCotizacionSeguroObjetoEvent = new GetCotizacionSeguroObjetoEvent();
            GetCotizacionSeguroObjetoRequestBean getCotizacionSeguroObjetoRequestBean = new GetCotizacionSeguroObjetoRequestBean();
            getCotizacionSeguroObjetoRequestBean.headerBean = a(VGetCotizacionSeguroObjeto.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            getCotizacionSeguroObjetoRequestBean.getCotizacionSeguroObjetoBodyRequestBean = getCotizacionSeguroObjetoBodyRequestBean;
            AnonymousClass124 r0 = new GetCotizacionSeguroObjetoRequest(this.b.getApplicationContext(), getCotizacionSeguroObjetoRequestBean, createErrorRequestServer(getCotizacionSeguroObjetoEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetCotizacionSeguroObjetoResponseBean getCotizacionSeguroObjetoResponseBean = (GetCotizacionSeguroObjetoResponseBean) iBeanWS;
                    getCotizacionSeguroObjetoEvent.setGetCotizacionSeguroObjetoResponseBean(getCotizacionSeguroObjetoResponseBean);
                    getCotizacionSeguroObjetoEvent.setBeanResponse(getCotizacionSeguroObjetoResponseBean);
                    getCotizacionSeguroObjetoEvent.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(getCotizacionSeguroObjetoEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, getCotizacionSeguroObjetoEvent, VGetCotizacionSeguroObjeto.nameService);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VGetCotizacionSeguroObjeto.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getFamiliasObjetos() {
        a((Runnable) new Runnable() {
            public void run() {
                final GetFamiliasObjetosEvent getFamiliasObjetosEvent = new GetFamiliasObjetosEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetFamiliasObjetosRequest(DataManager.this.b.getApplicationContext(), new GetFamiliasObjetosRequestBean(DataManager.this.a(VGetFamiliasObjetos.getData(EVersionServices.CURRENT), session, nup), new GetFamiliasObjetosBodyRequestBean()), DataManager.this.createErrorRequestServer(getFamiliasObjetosEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetFamiliasObjetosResponseBean getFamiliasObjetosResponseBean = (GetFamiliasObjetosResponseBean) iBeanWS;
                        getFamiliasObjetosEvent.setResponse(getFamiliasObjetosResponseBean);
                        getFamiliasObjetosEvent.setBeanResponse(getFamiliasObjetosResponseBean);
                        getFamiliasObjetosEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getFamiliasObjetosEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getFamiliasObjetosEvent, VGetFirmaSeguros.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetFamiliasObjetos.nameService);
                    }
                });
            }
        }, VGetFamiliasObjetos.nameService);
    }

    public void getCotizacionSeguroAccidente() {
        a((Runnable) new Runnable() {
            public void run() {
                final GetCotizacionSeguroAccidenteEvent getCotizacionSeguroAccidenteEvent = new GetCotizacionSeguroAccidenteEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetCotizacionSeguroAccidenteRequest(DataManager.this.b.getApplicationContext(), new GetCotizacionSeguroAccidenteRequestBean(DataManager.this.a(VGetCotizacionSeguroAccidente.getData(EVersionServices.CURRENT), session, nup), new GetCotizacionSeguroAccidenteBodyRequestBean()), DataManager.this.createErrorRequestServer(getCotizacionSeguroAccidenteEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetCotizacionSeguroAccidenteResponseBean getCotizacionSeguroAccidenteResponseBean = (GetCotizacionSeguroAccidenteResponseBean) iBeanWS;
                        getCotizacionSeguroAccidenteEvent.setResponse(getCotizacionSeguroAccidenteResponseBean);
                        getCotizacionSeguroAccidenteEvent.setBeanResponse(getCotizacionSeguroAccidenteResponseBean);
                        getCotizacionSeguroAccidenteEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getCotizacionSeguroAccidenteEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getCotizacionSeguroAccidenteEvent, VGetFirmaSeguros.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetCotizacionSeguroAccidente.nameService);
                    }
                });
            }
        }, VGetCotizacionSeguroAccidente.nameService);
    }

    public void getCircuitoTurno(final String str) {
        a((Runnable) new Runnable() {
            public void run() {
                final GetCircuitoTurnoEvent getCircuitoTurnoEvent = new GetCircuitoTurnoEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetCircuitoTurnoRequest(DataManager.this.b.getApplicationContext(), new GetCircuitoTurnoRequestBean(DataManager.this.a(VGetCircuitoTurno.getData(EVersionServices.CURRENT), session, nup), new GetCircuitoTurnoBodyRequestBean(str)), DataManager.this.createErrorRequestServer(getCircuitoTurnoEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetCircuitoTurnoResponseBean getCircuitoTurnoResponseBean = (GetCircuitoTurnoResponseBean) iBeanWS;
                        getCircuitoTurnoEvent.setResponse(getCircuitoTurnoResponseBean);
                        getCircuitoTurnoEvent.setBeanResponse(getCircuitoTurnoResponseBean);
                        getCircuitoTurnoEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getCircuitoTurnoEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getCircuitoTurnoEvent, VGetCircuitoTurno.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetCircuitoTurno.nameService);
                    }
                });
            }
        }, VGetCircuitoTurno.nameService);
    }

    public void getFirmaSeguro() {
        a((Runnable) new Runnable() {
            public void run() {
                final GetFirmaSeguroEvent getFirmaSeguroEvent = new GetFirmaSeguroEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetFirmaSeguroRequest(DataManager.this.b.getApplicationContext(), new GetFirmaSeguroRequestBean(DataManager.this.a(VGetFirmaSeguros.getData(EVersionServices.CURRENT), session, nup), new GetFirmaSeguroBodyRequestBean()), DataManager.this.createErrorRequestServer(getFirmaSeguroEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetFirmaSeguroResponseBean getFirmaSeguroResponseBean = (GetFirmaSeguroResponseBean) iBeanWS;
                        getFirmaSeguroEvent.setResponse(getFirmaSeguroResponseBean);
                        getFirmaSeguroEvent.setBeanResponse(getFirmaSeguroResponseBean);
                        getFirmaSeguroEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getFirmaSeguroEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getFirmaSeguroEvent, VGetFirmaSeguros.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetFirmaSeguros.nameService);
                    }
                });
            }
        }, VGetFirmaSeguros.nameService);
    }

    public void getFirmaSC() {
        a((Runnable) new Runnable() {
            public void run() {
                final GetFirmaSCEvent getFirmaSCEvent = new GetFirmaSCEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetFirmaSCRequest(DataManager.this.b.getApplicationContext(), new GetFirmaSCRequestBean(DataManager.this.a(VGetFirmaSC.getData(EVersionServices.CURRENT), session, nup), new GetFirmaSCBodyRequestBean()), DataManager.this.createErrorRequestServer(getFirmaSCEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        GetFirmaSCResponseBean getFirmaSCResponseBean = (GetFirmaSCResponseBean) iBeanWS;
                        getFirmaSCEvent.setResponse(getFirmaSCResponseBean);
                        getFirmaSCEvent.setBeanResponse(getFirmaSCResponseBean);
                        getFirmaSCEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getFirmaSCEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, getFirmaSCEvent, VGetFirmaSC.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetFirmaSC.nameService);
                    }
                });
            }
        }, VGetFirmaSC.nameService);
    }

    public void enviarTokenOBP(String str, String str2, String str3, String str4) {
        final String str5 = str;
        final String str6 = str2;
        final String str7 = str3;
        final String str8 = str4;
        AnonymousClass130 r0 = new Runnable() {
            public void run() {
                final EnviarTokenOBPEvent enviarTokenOBPEvent = new EnviarTokenOBPEvent();
                String session = DataManager.this.sessionManager.getSession();
                final AnonymousClass1 r0 = new EnviarTokenOBPRequest(DataManager.this.b.getApplicationContext(), new EnviarTokenOBPRequestBean(DataManager.this.a(VEnviarTokenOBP.getData(EVersionServices.CURRENT), session, ""), new EnviarTokenOBPBodyRequestBean(str5, str6, str7, str8)), DataManager.this.createErrorRequestServer(enviarTokenOBPEvent, TypeOption.INITIAL_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        EnviarTokenOBPResponseBean enviarTokenOBPResponseBean = (EnviarTokenOBPResponseBean) iBeanWS;
                        enviarTokenOBPEvent.setResponse(enviarTokenOBPResponseBean);
                        enviarTokenOBPEvent.setBeanResponse(enviarTokenOBPResponseBean);
                        enviarTokenOBPEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(enviarTokenOBPEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        DataManager.this.createUnknowErrorEventProcesor(exc, enviarTokenOBPEvent, VEnviarTokenOBP.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VEnviarTokenOBP.nameService);
                    }
                });
            }
        };
        a((Runnable) r0, VEnviarTokenOBP.nameService);
    }

    public void enviarSugerenciaObjeto(String str, String str2, String str3, String str4) {
        try {
            final EnviarSugerenciaObjetoEvent enviarSugerenciaObjetoEvent = new EnviarSugerenciaObjetoEvent();
            AnonymousClass131 r0 = new EnviarSugerenciaObjetoRequest(this.b.getApplicationContext(), new EnviarSugerenciaObjetoRequestBean(a(VEnviarSugerenciaObjeto.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new EnviarSugerenciaObjetoBodyRequestBean(str, str2, str3, str4)), createErrorRequestServer(enviarSugerenciaObjetoEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    EnviarSugerenciaObjetoResponseBean enviarSugerenciaObjetoResponseBean = (EnviarSugerenciaObjetoResponseBean) iBeanWS;
                    enviarSugerenciaObjetoEvent.setBeanResponse(enviarSugerenciaObjetoResponseBean);
                    enviarSugerenciaObjetoEvent.setResult(TypeResult.OK);
                    enviarSugerenciaObjetoEvent.setResponse(enviarSugerenciaObjetoResponseBean);
                    DataManager.this.sendBusEvent(enviarSugerenciaObjetoEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createErrorRequestServer(enviarSugerenciaObjetoEvent, TypeOption.INITIAL_VIEW);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VEnviarSugerenciaObjeto.nameService);
            Log.d("", "");
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getDetallePreautorizacionComprador(final String str, final String str2) {
        a((Runnable) new Runnable() {
            public void run() {
                final GetDetallePreAutorizacionCompradorEvent getDetallePreAutorizacionCompradorEvent = new GetDetallePreAutorizacionCompradorEvent();
                String session = DataManager.this.sessionManager.getSession();
                String nup = DataManager.this.sessionManager.getNup();
                final AnonymousClass1 r0 = new GetDetallePreAutorizacionCompradorRequest(DataManager.this.b.getApplicationContext(), new GetDetallePreAutorizacionCompradorRequestBean(DataManager.this.a(VGetDetallePreAutorizacionComprador.getData(EVersionServices.CURRENT), session, nup), new GetDetallePreAutorizacionCompradorBodyRequestBean(str, str2)), DataManager.this.createErrorRequestServer(getDetallePreAutorizacionCompradorEvent, TypeOption.INTERMDIATE_VIEW)) {
                    public void onResponseBean(IBeanWS iBeanWS) {
                        super.onResponseBean(iBeanWS);
                        GetDetallePreAutorizacionCompradorResponseBean getDetallePreAutorizacionCompradorResponseBean = (GetDetallePreAutorizacionCompradorResponseBean) iBeanWS;
                        getDetallePreAutorizacionCompradorEvent.setBeanResponse(getDetallePreAutorizacionCompradorResponseBean);
                        getDetallePreAutorizacionCompradorEvent.setGetDetallePreAutorizacionCompradorResponseBean(getDetallePreAutorizacionCompradorResponseBean);
                        getDetallePreAutorizacionCompradorEvent.setResult(TypeResult.OK);
                        DataManager.this.sendBusEvent(getDetallePreAutorizacionCompradorEvent);
                    }

                    public void onUnknowError(Exception exc) {
                        super.onUnknowError(exc);
                        DataManager.this.createUnknowErrorEventProcesor(exc, getDetallePreAutorizacionCompradorEvent, VGetDetallePreAutorizacionComprador.nameService);
                    }
                };
                DataManager.this.d.post(new Runnable() {
                    public void run() {
                        DataManager.this.a((BaseRequest) r0);
                        r0.sendRequest(VGetDetallePreAutorizacionComprador.nameService);
                    }
                });
            }
        }, VGetDetallePreAutorizacionComprador.nameService);
    }

    /* access modifiers changed from: private */
    public void a(BaseRequest baseRequest) {
        try {
            baseRequest.setTimeOutResponse(Constants.DEFAULT_TIME_OUT_SERVER * 1000);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private void a(BaseRequest baseRequest, int i) {
        try {
            baseRequest.setTimeOutResponse(i * 1000);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void sendBusEvent(final WebServiceEvent webServiceEvent) {
        if (webServiceEvent == null) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.d.post(new Runnable() {
                public void run() {
                    BaseApplication baseApplication = DataManager.this.b;
                    if (BaseApplication.isActivityVisible()) {
                        DataManager.this.c.post(webServiceEvent);
                        return;
                    }
                    BaseApplication baseApplication2 = DataManager.this.b;
                    BaseApplication.addPendingEvents(webServiceEvent);
                }
            });
            Looper.myLooper().quit();
            return;
        }
        BaseApplication baseApplication = this.b;
        if (BaseApplication.isActivityVisible()) {
            this.c.post(webServiceEvent);
            return;
        }
        BaseApplication baseApplication2 = this.b;
        BaseApplication.addPendingEvents(webServiceEvent);
    }

    /* access modifiers changed from: protected */
    public ErrorRequestServer createErrorRequestServer(final WebServiceEvent webServiceEvent, final TypeOption typeOption) {
        return new ErrorRequestServer() {
            public void onErrorServer(VolleyError volleyError) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.SERVER_ERROR);
                webServiceEvent.setMessageToShow(volleyError.getMessage());
                webServiceEvent.setTitleToShow("");
                webServiceEvent.setVolleyError(volleyError);
                DataManager.this.sendBusEvent(webServiceEvent);
            }

            public void onWarningBean(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_WARNING);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setTitleToShow(errorBodyBean.resTitle);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
            }

            public void onErrorResp1(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_ERROR_RES_1);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setTitleToShow(errorBodyBean.resTitle);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
                DataManager.this.sendBusEvent(webServiceEvent);
            }

            public void onErrorResp2(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_ERROR_RES_2);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setTitleToShow(errorBodyBean.resTitle);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
                DataManager.this.sendBusEvent(webServiceEvent);
            }

            public void onErrorResp3(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_ERROR_RES_3);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setTitleToShow(errorBodyBean.resTitle);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
                DataManager.this.sendBusEvent(webServiceEvent);
            }

            public void onErrorResp4(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_ERROR_RES_4);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setTitleToShow(errorBodyBean.resTitle);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
                DataManager.this.sendBusEvent(webServiceEvent);
            }

            public void onErrorResp5(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_ERROR_RES_5);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setTitleToShow(errorBodyBean.resTitle);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
                DataManager.this.sendBusEvent(webServiceEvent);
            }

            public void onErrorResp6(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_ERROR_RES_6);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setTitleToShow(errorBodyBean.resTitle);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
                DataManager.this.sendBusEvent(webServiceEvent);
            }

            public void onErrorResp7(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_ERROR_RES_7);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setTitleToShow(errorBodyBean.resTitle);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
                DataManager.this.sendBusEvent(webServiceEvent);
            }

            public void onErrorResp8(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_ERROR_RES_8);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
                DataManager.this.sendBusEvent(webServiceEvent);
            }

            public void onErrorResp9(ErrorBodyBean errorBodyBean) {
                webServiceEvent.setTypeOption(typeOption);
                webServiceEvent.setResult(TypeResult.BEAN_ERROR_RES_9);
                webServiceEvent.setMessageToShow(errorBodyBean.resDesc);
                webServiceEvent.setTitleToShow(errorBodyBean.resTitle);
                webServiceEvent.setErrorBodyBean(errorBodyBean);
                DataManager.this.sendBusEvent(webServiceEvent);
            }
        };
    }

    public void getListaTjWomen() {
        try {
            final GetListaTjWomenEvent getListaTjWomenEvent = new GetListaTjWomenEvent();
            AnonymousClass135 r0 = new GetListaTjWomenRequest(this.b.getApplicationContext(), new GetListaTjWomenRequestBean(a(VGetListaTjWomen.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup())), createErrorRequestServer(getListaTjWomenEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetListaTjWomenResponseBean getListaTjWomenResponseBean = (GetListaTjWomenResponseBean) iBeanWS;
                    getListaTjWomenEvent.setBeanResponse(getListaTjWomenResponseBean);
                    getListaTjWomenEvent.setResponse(getListaTjWomenResponseBean);
                    getListaTjWomenEvent.setResult((ErrorBodyBean) getListaTjWomenResponseBean.getListaTjWomenBodyResponseBean);
                    DataManager.this.sendBusEvent(getListaTjWomenEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createErrorRequestServer(getListaTjWomenEvent, TypeOption.INITIAL_VIEW);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VGetListaTjWomen.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void womenProgramSuscription(String str, List<ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta> list) {
        ListaTarjetas listaTarjetas = new ListaTarjetas(list);
        try {
            final SuscripcionWomenEvent suscripcionWomenEvent = new SuscripcionWomenEvent();
            AnonymousClass136 r1 = new SuscripcionWomenRequest(this.b.getApplicationContext(), new SuscripcionWomenRequestBean(a(vSuscripcionWomen.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new SuscripcionWomenBodyRequestBean(str, listaTarjetas)), createErrorRequestServer(suscripcionWomenEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    SuscripcionWomenResponseBean suscripcionWomenResponseBean = (SuscripcionWomenResponseBean) iBeanWS;
                    suscripcionWomenEvent.setBeanResponse(suscripcionWomenResponseBean);
                    suscripcionWomenEvent.setResult(TypeResult.OK);
                    suscripcionWomenEvent.setResponse(suscripcionWomenResponseBean);
                    DataManager.this.sendBusEvent(suscripcionWomenEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createErrorRequestServer(suscripcionWomenEvent, TypeOption.INITIAL_VIEW);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(vSuscripcionWomen.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void genesysChatBackground(String str, String str2) {
        try {
            final GenesysChatBackgroundEvent genesysChatBackgroundEvent = new GenesysChatBackgroundEvent();
            AnonymousClass137 r0 = new GenesysChatRequest(this.b.getApplicationContext(), new GenesysChatRequestBean(a(VGenesysChat.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GenesysChatBodyRequestBean(str, str2)), createErrorRequestServer(genesysChatBackgroundEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GenesysChatResponseBean genesysChatResponseBean = (GenesysChatResponseBean) iBeanWS;
                    genesysChatBackgroundEvent.setBeanResponse(genesysChatResponseBean);
                    genesysChatBackgroundEvent.setResult(TypeResult.OK);
                    genesysChatBackgroundEvent.setResponse(genesysChatResponseBean);
                    DataManager.this.sendBusEvent(genesysChatBackgroundEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createErrorRequestServer(genesysChatBackgroundEvent, TypeOption.INITIAL_VIEW);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest("genesysChat-background");
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void genesysChat(String str, String str2) {
        try {
            final GenesysChatEvent genesysChatEvent = new GenesysChatEvent();
            AnonymousClass138 r0 = new GenesysChatRequest(this.b.getApplicationContext(), new GenesysChatRequestBean(a(VGenesysChat.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GenesysChatBodyRequestBean(str, str2)), createErrorRequestServer(genesysChatEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GenesysChatResponseBean genesysChatResponseBean = (GenesysChatResponseBean) iBeanWS;
                    genesysChatEvent.setBeanResponse(genesysChatResponseBean);
                    genesysChatEvent.setResult(TypeResult.OK);
                    genesysChatEvent.setResponse(genesysChatResponseBean);
                    DataManager.this.sendBusEvent(genesysChatEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createErrorRequestServer(genesysChatEvent, TypeOption.INITIAL_VIEW);
                }
            };
            a((BaseRequest) r0);
            r0.sendRequest(VGenesysChat.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getLimitesProductos() {
        GetLimitesProductosBodyRequestBean getLimitesProductosBodyRequestBean = new GetLimitesProductosBodyRequestBean();
        try {
            final GetLimitesProductosEvent getLimitesProductosEvent = new GetLimitesProductosEvent();
            GetLimitesProductosRequestBean getLimitesProductosRequestBean = new GetLimitesProductosRequestBean();
            getLimitesProductosRequestBean.headerBean = a(VGetLimitesProductos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            getLimitesProductosRequestBean.getLimitesProductosBodyRequestBean = getLimitesProductosBodyRequestBean;
            AnonymousClass139 r1 = new GetLimitesProductosRequest(this.b.getApplicationContext(), getLimitesProductosRequestBean, createErrorRequestServer(getLimitesProductosEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    GetLimitesProductosResponseBean getLimitesProductosResponseBean = (GetLimitesProductosResponseBean) iBeanWS;
                    getLimitesProductosEvent.setResponse(getLimitesProductosResponseBean);
                    getLimitesProductosEvent.setBeanResponse(getLimitesProductosResponseBean);
                    getLimitesProductosEvent.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(getLimitesProductosEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, getLimitesProductosEvent, VGetLimitesProductos.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VGetLimitesProductos.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void confirmarRecalificacion(ListaProductosRecalificacionBean listaProductosRecalificacionBean, int i) {
        ConfirmarRecalificacionBodyRequestBean confirmarRecalificacionBodyRequestBean = new ConfirmarRecalificacionBodyRequestBean();
        try {
            final ConfirmarRecalificacionEvent confirmarRecalificacionEvent = new ConfirmarRecalificacionEvent();
            ConfirmarRecalificacionRequestBean confirmarRecalificacionRequestBean = new ConfirmarRecalificacionRequestBean();
            confirmarRecalificacionRequestBean.headerBean = a(VConfirmarRecalificacion.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            confirmarRecalificacionRequestBean.confirmarRecalificacionBodyRequestBean = confirmarRecalificacionBodyRequestBean;
            confirmarRecalificacionRequestBean.confirmarRecalificacionBodyRequestBean.nuevaLineaCrediticia = i;
            confirmarRecalificacionRequestBean.confirmarRecalificacionBodyRequestBean.listaProductos = listaProductosRecalificacionBean;
            AnonymousClass140 r1 = new ConfirmarRecalificacionRequest(this.b.getApplicationContext(), confirmarRecalificacionRequestBean, createErrorRequestServer(confirmarRecalificacionEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    ConfirmarRecalificacionResponseBean confirmarRecalificacionResponseBean = (ConfirmarRecalificacionResponseBean) iBeanWS;
                    confirmarRecalificacionEvent.setResponse(confirmarRecalificacionResponseBean);
                    confirmarRecalificacionEvent.setBeanResponse(confirmarRecalificacionResponseBean);
                    confirmarRecalificacionEvent.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(confirmarRecalificacionEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, confirmarRecalificacionEvent, VGetRecalificacion.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VConfirmarRecalificacion.nameService);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }
    }

    public void getTenenciaInversiones(String str) {
        TenenciaInversionesBodyRequestBean tenenciaInversionesBodyRequestBean = new TenenciaInversionesBodyRequestBean();
        try {
            final TenenciaInversionesEvent tenenciaInversionesEvent = new TenenciaInversionesEvent();
            TenenciaInversionesRequestBean tenenciaInversionesRequestBean = new TenenciaInversionesRequestBean();
            tenenciaInversionesRequestBean.headerBean = a(VTenenciaInversiones.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            tenenciaInversionesRequestBean.tenenciaInversionesBodyRequestBean = tenenciaInversionesBodyRequestBean;
            tenenciaInversionesRequestBean.tenenciaInversionesBodyRequestBean.tipoCliente = str;
            AnonymousClass141 r1 = new TenenciaInversionesRequest(this.b.getApplicationContext(), tenenciaInversionesRequestBean, createErrorRequestServer(tenenciaInversionesEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    TenenciaInversionesResponseBean tenenciaInversionesResponseBean = (TenenciaInversionesResponseBean) iBeanWS;
                    tenenciaInversionesEvent.setResponse(tenenciaInversionesResponseBean);
                    tenenciaInversionesEvent.setBeanResponse(tenenciaInversionesResponseBean);
                    tenenciaInversionesEvent.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(tenenciaInversionesEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, tenenciaInversionesEvent, VTenenciaInversiones.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VTenenciaInversiones.nameService);
        } catch (Exception e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    public void getTenenciaTitulos(String str, String str2, String str3, String str4) {
        TenenciaTitulosBodyRequestBean tenenciaTitulosBodyRequestBean = new TenenciaTitulosBodyRequestBean();
        try {
            final TenenciaTitulosEvent tenenciaTitulosEvent = new TenenciaTitulosEvent();
            TenenciaTitulosRequestBean tenenciaTitulosRequestBean = new TenenciaTitulosRequestBean();
            tenenciaTitulosRequestBean.headerBean = a(VTenenciaTitulosValores.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            tenenciaTitulosRequestBean.tenenciaTitulosBodyRequestBean = tenenciaTitulosBodyRequestBean;
            tenenciaTitulosRequestBean.tenenciaTitulosBodyRequestBean.tipoCuenta = str;
            tenenciaTitulosRequestBean.tenenciaTitulosBodyRequestBean.sucCuenta = str2;
            tenenciaTitulosRequestBean.tenenciaTitulosBodyRequestBean.nroCuenta = str3;
            tenenciaTitulosRequestBean.tenenciaTitulosBodyRequestBean.tipoCliente = str4;
            AnonymousClass142 r1 = new TenenciaTitulosRequest(this.b.getApplicationContext(), tenenciaTitulosRequestBean, createErrorRequestServer(tenenciaTitulosEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    TenenciaTitulosResponseBean tenenciaTitulosResponseBean = (TenenciaTitulosResponseBean) iBeanWS;
                    tenenciaTitulosEvent.setResponse(tenenciaTitulosResponseBean);
                    tenenciaTitulosEvent.setBeanResponse(tenenciaTitulosResponseBean);
                    tenenciaTitulosEvent.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(tenenciaTitulosEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, tenenciaTitulosEvent, VTenenciaTitulosValores.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VTenenciaTitulosValores.nameService);
        } catch (Exception e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    public void getCustodia(String str, String str2, String str3, String str4) {
        TenenciaCustodiaBodyRequestBean tenenciaCustodiaBodyRequestBean = new TenenciaCustodiaBodyRequestBean();
        try {
            final TenenciaCustodiaEvent tenenciaCustodiaEvent = new TenenciaCustodiaEvent();
            TenenciaCustodiaRequestBean tenenciaCustodiaRequestBean = new TenenciaCustodiaRequestBean();
            tenenciaCustodiaRequestBean.headerBean = a(VTenenciaCustodia.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
            tenenciaCustodiaRequestBean.tenenciaCustodiaBodyRequestBean = tenenciaCustodiaBodyRequestBean;
            tenenciaCustodiaRequestBean.tenenciaCustodiaBodyRequestBean.tipoCuenta = str;
            tenenciaCustodiaRequestBean.tenenciaCustodiaBodyRequestBean.sucCuenta = str2;
            tenenciaCustodiaRequestBean.tenenciaCustodiaBodyRequestBean.nroCuenta = str3;
            tenenciaCustodiaRequestBean.tenenciaCustodiaBodyRequestBean.tipoCliente = str4;
            AnonymousClass143 r1 = new TenenciaCustodiaRequest(this.b.getApplicationContext(), tenenciaCustodiaRequestBean, createErrorRequestServer(tenenciaCustodiaEvent, TypeOption.INTERMDIATE_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    TenenciaCustodiaResponseBean tenenciaCustodiaResponseBean = (TenenciaCustodiaResponseBean) iBeanWS;
                    tenenciaCustodiaEvent.setResponse(tenenciaCustodiaResponseBean);
                    tenenciaCustodiaEvent.setBeanResponse(tenenciaCustodiaResponseBean);
                    tenenciaCustodiaEvent.setResult(TypeResult.OK);
                    DataManager.this.sendBusEvent(tenenciaCustodiaEvent);
                }

                public void onUnknowError(Exception exc) {
                    DataManager.this.createUnknowErrorEventProcesor(exc, tenenciaCustodiaEvent, VTenenciaCustodia.nameService);
                }
            };
            a((BaseRequest) r1);
            r1.sendRequest(VTenenciaCustodia.nameService);
        } catch (Exception e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
    }
}
