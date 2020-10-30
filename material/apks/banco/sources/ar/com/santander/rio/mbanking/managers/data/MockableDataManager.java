package ar.com.santander.rio.mbanking.managers.data;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.module.creditos.CreditosConstants;
import ar.com.santander.rio.mbanking.app.module.creditos.CreditosConstantsProd;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.Estado;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.TipoCuentaDestino;
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
import ar.com.santander.rio.mbanking.services.events.GeneraMandatoExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.GenesysChatEvent;
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
import ar.com.santander.rio.mbanking.services.events.LoginEvent;
import ar.com.santander.rio.mbanking.services.events.ModificarSuscripcionEvent;
import ar.com.santander.rio.mbanking.services.events.MovCtasEvent;
import ar.com.santander.rio.mbanking.services.events.PagoServiciosEvent;
import ar.com.santander.rio.mbanking.services.events.PagoTarjetaEvent;
import ar.com.santander.rio.mbanking.services.events.PreguntasFamiliaEvent;
import ar.com.santander.rio.mbanking.services.events.RecargaCelularesEvent;
import ar.com.santander.rio.mbanking.services.events.RescatarFondoEvent;
import ar.com.santander.rio.mbanking.services.events.SimulacionDolarEvent;
import ar.com.santander.rio.mbanking.services.events.SimularSuscripcionFondoEvent;
import ar.com.santander.rio.mbanking.services.events.SimularTransferenciaFondoEvent;
import ar.com.santander.rio.mbanking.services.events.SolicitudLimiteTransfEvent;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEvent;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEventProd;
import ar.com.santander.rio.mbanking.services.events.SuscribirFondoEvent;
import ar.com.santander.rio.mbanking.services.events.SuscripcionWomenEvent;
import ar.com.santander.rio.mbanking.services.events.TenenciaInversionesEvent;
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
import ar.com.santander.rio.mbanking.services.soap.beans.ABMAliasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMDestinatarioTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMViajesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AMAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmAdhesionVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmPreautorizacionCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmTurnoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmTurnoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.BAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CambiarLimiteResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CancelaMandatoExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CanjearPuntosSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CargaDatosInicialesExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CheckVersionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaCBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaManualResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaPTResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaRecargaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaRecargaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsEmpresaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTasasPFTResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsTenenciasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CompraVentaDolarResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConfirmarPagoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsDescriptionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConstPlazoFijoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAliasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaDatosInicialesTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSuscripcionMyAResponeBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarAdhesionVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarTitularCuentaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarCompraProtegidaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroAccidenteRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroAccidenteResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroMovilResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroObjetoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarSugerenciaObjetoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.EnviarSugerenciaObjetoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GenesysChatResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCategoriasSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionCompraProtegidaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionSeguroMovilRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionSeguroMovilResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCuponesSuperClubRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCuponesSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDatosInicialesDolaresResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDebinesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleCuotaTenenciaCreditoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaCredinResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSeguroResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetInfoAdmFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetLimitesExtraccionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetMovimientosFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNotificacionesPushResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetOcupacionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPolizaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesHomeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetProximasCuotasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPuntosSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetRecargasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSegurosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTarjPaisesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetViajesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LimitesYDisponiblesTCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LimitesYDisponiblesTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoServiciosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoTarjetaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PreguntasFamiliaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RecargaCelularesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RescatarFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimulacionDolarResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularSuscripcionFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularTransferenciaFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudLimiteTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscribirFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscripcionWomenResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferenciasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferenciasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferirFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimoResumenTCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimoResumenTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BAgendadosEnvEfeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BajaSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BeneficiarioMandatoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CancelaMandatoExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CancelaMandatoExtEnvBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CanjearPuntosSuperClubBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CargaDatosInicialesExtEnvBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriasSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaRecargaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTListaPlazoFijoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTListaTasasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTPlazoFijoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTTasaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasDatosPFBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasLeyendaPFBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasListaDatosPFBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasListaLeyendasPFBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolarBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ComprobanteCancelacionMandatoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConstPlazoFijoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaAgendadosEnvEfeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaAgendadosEnvEfeListaDestinatariosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaMandatosExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaMandatosExtEnvBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaMandatosExtEnvListaMandatosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroAccidenteBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaVendedor;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponesSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestinoBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosIniciales;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleDebinBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleMandatoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.EnviarSugerenciaObjetoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvDatosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvFiltrosMandatosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvLeyendaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvListaCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvListaLeyendasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtEnvBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtEnvComprobanteBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCategoriasSuperClubBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCuponesSuperClubBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCuponesSuperClubBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDatosInicialesDolaresBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPuntosSuperClubBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimiteExtraccionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendasSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntasFamilia;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaProductosRecalificacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaRespuestas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MandatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MandatoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaCelularesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetasMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesRequest;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferenciasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UbicacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.builders.header.ManagerHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.request.ABMDestinatarioTransfRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AbmAdhesionVendedorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AbmDebinCompradorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AbmDebinVendedorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.AbmTurnoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CheckVersionRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsDeudaPTRequest;
import ar.com.santander.rio.mbanking.services.soap.request.CnsDeudaRecargaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConstPlazoFijoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaAliasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaDatosInicialesTransfRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaPrestamosPermitidosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaPrestamosPermitidosRequestProd;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaSolicitudCrediticiaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultaSolicitudCrediticiaRequestProd;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultarAdhesionVendedorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ConsultarTitularCuentaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ContratarCompraProtegidaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ContratarSeguroAccidenteRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ContratarSeguroMovilRequest;
import ar.com.santander.rio.mbanking.services.soap.request.ContratarSeguroObjetoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.EnviarSugerenciaObjetoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GenesysChatRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetCotizacionSeguroMovilRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetCuponesSuperClubRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDebinesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDetalleDebinCompradorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetDetalleDebinVendedorRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetLimitesExtraccionRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetListaTjWomenRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetNotificacionesPushRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetOcupacionesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetPolizaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetRecargasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetSegurosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetSucursalesTurnoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetTenenciaCreditosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetTenenciaFondosRequest;
import ar.com.santander.rio.mbanking.services.soap.request.GetViajesRequest;
import ar.com.santander.rio.mbanking.services.soap.request.LimitesYDisponiblesTCRequest;
import ar.com.santander.rio.mbanking.services.soap.request.LoginUnicoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.PagoTarjetaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.PreguntasFamiliaRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SolicitudLimiteTransfRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SolicitudPrestamoPreacordadoRequest;
import ar.com.santander.rio.mbanking.services.soap.request.SolicitudPrestamoPreacordadoRequestProd;
import ar.com.santander.rio.mbanking.services.soap.request.SuscripcionWomenRequest;
import ar.com.santander.rio.mbanking.services.soap.request.TransferenciasRequest;
import ar.com.santander.rio.mbanking.services.soap.request.UltimoResumenTCRequest;
import ar.com.santander.rio.mbanking.services.soap.request.UltimosConsumosTCRequest;
import ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMDestinatarioTransf;
import ar.com.santander.rio.mbanking.services.soap.versions.VAbmAdhesionVendedorDebin;
import ar.com.santander.rio.mbanking.services.soap.versions.VCheckVersion;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeuda;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeudaRecarga;
import ar.com.santander.rio.mbanking.services.soap.versions.VConstPlazoFijo;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaAlias;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaDatosInicialesTransf;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaPrestamosPermitidos;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaPrestamosPermitidosProd;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaSolicitudCrediticia;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaSolicitudCrediticiaProd;
import ar.com.santander.rio.mbanking.services.soap.versions.VContratarCompraProtegida;
import ar.com.santander.rio.mbanking.services.soap.versions.VContratarSeguroMovil;
import ar.com.santander.rio.mbanking.services.soap.versions.VContratarSeguroObjeto;
import ar.com.santander.rio.mbanking.services.soap.versions.VEnviarSugerenciaObjeto;
import ar.com.santander.rio.mbanking.services.soap.versions.VGenesysChat;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCircuitoTurno;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetConsultarAdhesionVendedor;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetConsultarTitularCuenta;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetContratarSeguroAccidente;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroAccidente;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroMovil;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroObjeto;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCuponesSuperClub;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDebines;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetalleDebinComprador;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetalleDebinVendedor;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFamiliasObjetos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetLimitesExtraccion;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetLimitesProductos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetListaTjWomen;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetNotificacionesPush;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetOcupaciones;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPoliza;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPreguntasFamilia;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetSeguros;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetSucursalesTurno;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTenenciaCreditos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTenenciaFondos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTurno;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetViajes;
import ar.com.santander.rio.mbanking.services.soap.versions.VLimitesYDisponiblesTC;
import ar.com.santander.rio.mbanking.services.soap.versions.VLoginUnico;
import ar.com.santander.rio.mbanking.services.soap.versions.VPagoTarjeta;
import ar.com.santander.rio.mbanking.services.soap.versions.VRecargas;
import ar.com.santander.rio.mbanking.services.soap.versions.VSolicitudLimiteTransf;
import ar.com.santander.rio.mbanking.services.soap.versions.VSolicitudPrestamoPreacordado;
import ar.com.santander.rio.mbanking.services.soap.versions.VSolicitudPrestamoPreacordadoProd;
import ar.com.santander.rio.mbanking.services.soap.versions.VTenenciaInversiones;
import ar.com.santander.rio.mbanking.services.soap.versions.VTransferencias;
import ar.com.santander.rio.mbanking.services.soap.versions.VUltimoResumenTC;
import ar.com.santander.rio.mbanking.services.soap.versions.VUltimosConsumosTC;
import ar.com.santander.rio.mbanking.services.soap.versions.VblanqueoPIN;
import ar.com.santander.rio.mbanking.services.soap.versions.vSuscripcionWomen;
import com.android.volley.VolleyError;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;
import com.indra.httpclient.utils.UtilBeanXml;
import com.squareup.otto.Bus;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockableDataManager extends DataManager {
    public static final String TAG = DataManager.class.getName();
    SettingsManager d;
    BaseApplication e;
    Bus f;
    Date g = new Date();
    Boolean h = Boolean.valueOf(true);
    int i = 0;
    public SessionManager sessionManager;

    static final class mockEnabledServices {
        /* access modifiers changed from: private */
        public static Boolean A = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean B = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean C = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean D = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean E = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean F = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean G = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean H = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean I = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean J = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean K = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean L = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean M = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean N = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean O = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean P = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean Q = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean R = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean S = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean T = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean U = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean V = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean W = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean X = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean Y = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean Z = Boolean.valueOf(true);
        public static boolean a = true;
        /* access modifiers changed from: private */
        public static Boolean aA = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aB = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aC = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aD = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aE = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aF = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aG = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aH = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aI = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aJ = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aK = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aL = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aM = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aN = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aO = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aP = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aQ = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aR = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aS = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aT = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aU = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aV = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aW = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aX = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aY = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aa = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ab = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ac = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ad = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ae = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean af = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ag = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ah = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ai = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aj = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ak = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean al = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean am = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean an = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ao = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ap = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aq = Boolean.valueOf(true);
        /* access modifiers changed from: private */

        /* renamed from: ar reason: collision with root package name */
        public static Boolean f246ar = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean as = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean at = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean au = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean av = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean aw = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ax = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean ay = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean az = Boolean.valueOf(true);
        public static boolean b = true;
        public static boolean c = true;
        public static boolean d = true;
        public static boolean e = true;
        public static boolean f = true;
        /* access modifiers changed from: private */
        public static Boolean g = Boolean.valueOf(true);
        private static Boolean h = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean i = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean j = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean k = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean l = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean m = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean n = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean o = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean p = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean q = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean r = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean s = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean t = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean u = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean v = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean w = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean x = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean y = Boolean.valueOf(true);
        /* access modifiers changed from: private */
        public static Boolean z = Boolean.valueOf(true);

        private mockEnabledServices() {
        }
    }

    public MockableDataManager(BaseApplication baseApplication, Bus bus, SessionManager sessionManager2, SettingsManager settingsManager) {
        super(baseApplication, bus, sessionManager2, settingsManager);
        this.sessionManager = sessionManager2;
        this.d = settingsManager;
        this.e = baseApplication;
        this.f = bus;
    }

    public void consDescripciones(boolean z, boolean z2) {
        if (!mockEnabledServices.k.booleanValue()) {
            super.consDescripciones(z, z2);
            return;
        }
        ConsDescripcionesEvent consDescripcionesEvent = new ConsDescripcionesEvent();
        consDescripcionesEvent.setBeanResponse(a((int) R.string.mocks_consDescripciones, ConsDescriptionResponseBean.class));
        consDescripcionesEvent.setResult(TypeResult.OK);
        this.sessionManager.setConsDescripciones(((ConsDescriptionResponseBean) consDescripcionesEvent.getBeanResponse()).consDescriptionBodyResponseBean);
        a((WebServiceEvent) consDescripcionesEvent, 1000);
    }

    public void checkVersion(String str, String str2, String str3, Activity activity, boolean z) {
        if (!mockEnabledServices.l.booleanValue()) {
            super.checkVersion(str, str2, str3, activity, z);
            return;
        }
        this.sessionManager.setCheckVersionEvent(null);
        final CheckVersionEvent checkVersionEvent = new CheckVersionEvent();
        final AnonymousClass1 r3 = new CheckVersionRequest(super.createErrorRequestServer(checkVersionEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                CheckVersionResponseBean checkVersionResponseBean = (CheckVersionResponseBean) iBeanWS;
                checkVersionEvent.setBeanResponse(checkVersionResponseBean);
                checkVersionEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sessionManager.setCheckVersionEvent(checkVersionEvent);
                MockableDataManager.this.d.setLastIdTerminal(checkVersionResponseBean.getCheckVersionBodyResponseBean().getIdTerminal());
                MockableDataManager.this.sendBusEvent(checkVersionEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, checkVersionEvent, VCheckVersion.nameService);
                MockableDataManager.this.sessionManager.setCheckVersionEvent(checkVersionEvent);
            }
        };
        new Handler().postDelayed(new Runnable() {
            public void run() {
                r3.parserResponse(MockableDataManager.this.b(R.string.mocks_checkVersion_OK, CheckVersionResponseBean.class));
            }
        }, 500);
    }

    public void getPromocionesHome() {
        if (!mockEnabledServices.m.booleanValue()) {
            super.getPromocionesHome();
            return;
        }
        GetPromocionesHomeEvent getPromocionesHomeEvent = new GetPromocionesHomeEvent();
        GetPromocionesHomeResponseBean getPromocionesHomeResponseBean = (GetPromocionesHomeResponseBean) a((int) R.string.mocks_getPromocionesHome, GetPromocionesHomeResponseBean.class);
        getPromocionesHomeEvent.setBeanResponse(getPromocionesHomeResponseBean);
        getPromocionesHomeEvent.setResult(TypeResult.OK);
        this.sessionManager.setGetPromocionesHome(getPromocionesHomeResponseBean.getPromocionesHomeBodyResponseBean);
        a((WebServiceEvent) getPromocionesHomeEvent, 250);
    }

    public void loginUnicoTradicional(String str, String str2, String str3, String str4, Activity activity) {
        if (!mockEnabledServices.n.booleanValue()) {
            super.loginUnicoTradicional(str, str2, str3, str4, activity);
            return;
        }
        final LoginEvent loginEvent = new LoginEvent();
        new LoginUnicoRequest(super.createErrorRequestServer(loginEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                LoginUnicoResponseBean loginUnicoResponseBean = (LoginUnicoResponseBean) iBeanWS;
                MockableDataManager.this.sessionManager.setLoginUnico(loginUnicoResponseBean.getLoginUnicoBodyResponseBean());
                loginEvent.setBeanResponse(loginUnicoResponseBean);
                loginEvent.setResult(TypeResult.OK);
                loginEvent.setMessageToShow(loginUnicoResponseBean.getLoginUnicoBodyResponseBean().resDesc);
                loginEvent.setTitleToShow(loginUnicoResponseBean.getLoginUnicoBodyResponseBean().resTitle);
                MockableDataManager.this.a((WebServiceEvent) loginEvent, 500);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, loginEvent, VLoginUnico.nameService);
            }
        }.parserResponse(b(R.string.mocks_loginUnico_tipo_de_cliente, LoginUnicoResponseBean.class));
    }

    public void logOutSession() {
        logOutSession("", null);
    }

    public void logOutSession(String str, ListaRespuestas listaRespuestas) {
        this.sessionManager.setCustomErrorLogging("", "", "");
        this.sessionManager.cleanPrivateData();
    }

    public void getMovCtas(MovCtasBodyRequestBean movCtasBodyRequestBean) {
        if (!mockEnabledServices.o.booleanValue()) {
            super.getMovCtas(movCtasBodyRequestBean);
            return;
        }
        MovCtasEvent movCtasEvent = new MovCtasEvent();
        movCtasEvent.setResult(TypeResult.OK);
        movCtasEvent.setResponse((MovCtasResponseBean) a((int) R.string.mocks_cnsMovCtasInd_conMovimientos, MovCtasResponseBean.class));
        movCtasEvent.setBeanResponse(movCtasEvent.getResponse());
        a((WebServiceEvent) movCtasEvent, 500);
    }

    public void getNumerosUtiles() {
        if (!mockEnabledServices.r.booleanValue()) {
            super.getNumerosUtiles();
            return;
        }
        GetNumerosUtilesEvent getNumerosUtilesEvent = new GetNumerosUtilesEvent();
        getNumerosUtilesEvent.setResult(TypeResult.OK);
        getNumerosUtilesEvent.setResponse((GetNumerosUtilesResponseBean) a((int) R.string.mocks_getNumerosUtiles, GetNumerosUtilesResponseBean.class));
        getNumerosUtilesEvent.setBeanResponse(getNumerosUtilesEvent.getResponse());
        a((WebServiceEvent) getNumerosUtilesEvent, 500);
    }

    public void getDetCtas(String str, String str2, String str3) {
        MovCtasBodyRequestBean movCtasBodyRequestBean = new MovCtasBodyRequestBean(new AccountRequestBean("08", TipoCuentaDestino.CAJA_AHORRO_DOLARES, "370534890001"), "12/12/12", "12/12/16", "10", "20", "ARS", "1", "123", "1");
        getDetCtas(movCtasBodyRequestBean);
    }

    public void getDetCtas(MovCtasBodyRequestBean movCtasBodyRequestBean) {
        if (!mockEnabledServices.s.booleanValue()) {
            super.getDetCtas(movCtasBodyRequestBean);
            return;
        }
        DetCtasEvent detCtasEvent = new DetCtasEvent();
        detCtasEvent.setResult(TypeResult.OK);
        detCtasEvent.setBeanResponse(a((int) R.string.mocks_cnsDetCtasInd, MovCtasResponseBean.class));
        a((WebServiceEvent) detCtasEvent, 500);
    }

    public void getLimitesExtraccion() {
        if (!mockEnabledServices.p.booleanValue()) {
            super.getLimitesExtraccion();
            return;
        }
        final GetLimitesExtraccionEvent getLimitesExtraccionEvent = new GetLimitesExtraccionEvent();
        new GetLimitesExtraccionRequest(super.createErrorRequestServer(getLimitesExtraccionEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getLimitesExtraccionEvent.setBeanResponse((GetLimitesExtraccionResponseBean) iBeanWS);
                getLimitesExtraccionEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(getLimitesExtraccionEvent);
                MockableDataManager.this.a((WebServiceEvent) getLimitesExtraccionEvent, 500);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getLimitesExtraccionEvent, VGetLimitesExtraccion.nameService);
            }
        }.parserResponse(b(R.string.mocks_getLimitesExtraccion, GetLimitesExtraccionResponseBean.class));
    }

    public void cambiarLimite(LimiteExtraccionBean limiteExtraccionBean, String str, Activity activity) {
        if (!mockEnabledServices.q.booleanValue()) {
            super.cambiarLimite(limiteExtraccionBean, str, activity);
            return;
        }
        CambiarLimiteEvent cambiarLimiteEvent = new CambiarLimiteEvent();
        cambiarLimiteEvent.setResult(TypeResult.OK);
        cambiarLimiteEvent.setBeanResponse(a((int) R.string.mocks_cambiarLimite, CambiarLimiteResponseBean.class));
        a((WebServiceEvent) cambiarLimiteEvent, 500);
    }

    public void limitesYDisponiblesTC(LimitesYDisponiblesTCBodyRequestBean limitesYDisponiblesTCBodyRequestBean, TypeOption typeOption, Tarjeta tarjeta) {
        if (!mockEnabledServices.t.booleanValue()) {
            super.limitesYDisponiblesTC(limitesYDisponiblesTCBodyRequestBean, typeOption, tarjeta);
            return;
        }
        final LimitesYDisponiblesTCEvent limitesYDisponiblesTCEvent = new LimitesYDisponiblesTCEvent();
        limitesYDisponiblesTCEvent.setTarjetaSeleccionada(tarjeta);
        LimitesYDisponiblesTCRequestBean limitesYDisponiblesTCRequestBean = new LimitesYDisponiblesTCRequestBean();
        limitesYDisponiblesTCRequestBean.setHeader(a(VLimitesYDisponiblesTC.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()));
        limitesYDisponiblesTCRequestBean.setBody(limitesYDisponiblesTCBodyRequestBean);
        AnonymousClass5 r1 = new LimitesYDisponiblesTCRequest(this.e.getApplicationContext(), limitesYDisponiblesTCRequestBean, createErrorRequestServer(limitesYDisponiblesTCEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                LimitesYDisponiblesTCResponseBean limitesYDisponiblesTCResponseBean = (LimitesYDisponiblesTCResponseBean) iBeanWS;
                limitesYDisponiblesTCEvent.setBeanResponse(limitesYDisponiblesTCResponseBean);
                limitesYDisponiblesTCEvent.setResult(TypeResult.OK);
                limitesYDisponiblesTCEvent.setResponse(limitesYDisponiblesTCResponseBean);
                MockableDataManager.this.sessionManager.setLimitesYDisponiblesTC(limitesYDisponiblesTCResponseBean.getBody());
                MockableDataManager.this.sendBusEvent(limitesYDisponiblesTCEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, limitesYDisponiblesTCEvent, VLimitesYDisponiblesTC.nameService);
            }
        };
        r1.parserResponse(b(R.string.mocks_limitesYDisponiblesTC, LimitesYDisponiblesTCResponseBean.class));
    }

    public void ultimosConsumosTC(UltimosConsumosTCBodyRequestBean ultimosConsumosTCBodyRequestBean, Tarjeta tarjeta) {
        if (!mockEnabledServices.u.booleanValue()) {
            super.ultimosConsumosTC(ultimosConsumosTCBodyRequestBean, tarjeta);
            return;
        }
        final UltimosConsumosTCEvent ultimosConsumosTCEvent = new UltimosConsumosTCEvent();
        ultimosConsumosTCEvent.setTarjetaSeleccionada(tarjeta);
        UltimosConsumosTCRequestBean ultimosConsumosTCRequestBean = new UltimosConsumosTCRequestBean();
        ultimosConsumosTCRequestBean.setHeader(a(VUltimosConsumosTC.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()));
        ultimosConsumosTCRequestBean.setBody(ultimosConsumosTCBodyRequestBean);
        AnonymousClass6 r1 = new UltimosConsumosTCRequest(this.e.getApplicationContext(), ultimosConsumosTCRequestBean, createErrorRequestServer(ultimosConsumosTCEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                UltimosConsumosTCResponseBean ultimosConsumosTCResponseBean = (UltimosConsumosTCResponseBean) iBeanWS;
                ultimosConsumosTCEvent.setBeanResponse(ultimosConsumosTCResponseBean);
                ultimosConsumosTCEvent.setResult(TypeResult.OK);
                ultimosConsumosTCEvent.setResponse(ultimosConsumosTCResponseBean);
                MockableDataManager.this.sendBusEvent(ultimosConsumosTCEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, ultimosConsumosTCEvent, VUltimosConsumosTC.nameService);
            }
        };
        r1.parserResponse(b(R.string.mocks_ultimosConsumosTC_2, UltimosConsumosTCResponseBean.class));
    }

    public void ultimoResumenTC(UltimoResumenTCBodyRequestBean ultimoResumenTCBodyRequestBean) {
        if (!mockEnabledServices.aE.booleanValue()) {
            super.ultimoResumenTC(ultimoResumenTCBodyRequestBean);
            return;
        }
        final UltimoResumenTCEvent ultimoResumenTCEvent = new UltimoResumenTCEvent();
        UltimoResumenTCRequestBean ultimoResumenTCRequestBean = new UltimoResumenTCRequestBean();
        ultimoResumenTCRequestBean.setHeader(a(VUltimoResumenTC.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()));
        ultimoResumenTCRequestBean.setBody(ultimoResumenTCBodyRequestBean);
        AnonymousClass7 r1 = new UltimoResumenTCRequest(this.e.getApplicationContext(), ultimoResumenTCRequestBean, createErrorRequestServer(ultimoResumenTCEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                Log.d("@dev", "Ha entrado en DataManager.ultimoResumenTC.onResponseBean");
                UltimoResumenTCResponseBean ultimoResumenTCResponseBean = (UltimoResumenTCResponseBean) iBeanWS;
                ultimoResumenTCEvent.setBeanResponse(ultimoResumenTCResponseBean);
                ultimoResumenTCEvent.setResult(TypeResult.OK);
                ultimoResumenTCEvent.setResponse(ultimoResumenTCResponseBean);
                MockableDataManager.this.sessionManager.setUltimoResumenTC(ultimoResumenTCResponseBean.getBody());
                MockableDataManager.this.sendBusEvent(ultimoResumenTCEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, ultimoResumenTCEvent, VUltimoResumenTC.nameService);
            }
        };
        r1.parserResponse(b(R.string.mocks_ultimoResumenTC, UltimoResumenTCResponseBean.class));
    }

    public void cnsDeuda() {
        if (!mockEnabledServices.v.booleanValue()) {
            super.cnsDeuda();
            return;
        }
        final CnsDeudaEvent cnsDeudaEvent = new CnsDeudaEvent();
        new CnsDeudaPTRequest(createErrorRequestServer(cnsDeudaEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                CnsDeudaPTResponseBean cnsDeudaPTResponseBean = (CnsDeudaPTResponseBean) iBeanWS;
                cnsDeudaEvent.setBeanResponse(cnsDeudaPTResponseBean);
                cnsDeudaEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sessionManager.setCnsDeuda(cnsDeudaPTResponseBean.cnsDeudaPTBodyResponseBean);
                MockableDataManager.this.sendBusEvent(cnsDeudaEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, cnsDeudaEvent, VCnsDeuda.nameService);
            }
        }.parserResponse(b(R.string.mocks_cnsDeudaTarjetas, CnsDeudaPTResponseBean.class));
    }

    public void cnsDeudaRecarga(CnsDeudaRecargaBodyRequestBean cnsDeudaRecargaBodyRequestBean, EVersionServices eVersionServices) {
        if (!mockEnabledServices.v.booleanValue()) {
            super.cnsDeudaRecarga(cnsDeudaRecargaBodyRequestBean, eVersionServices);
            return;
        }
        final CnsDeudaRecargaEvent cnsDeudaRecargaEvent = new CnsDeudaRecargaEvent();
        CnsDeudaRecargaRequestBean cnsDeudaRecargaRequestBean = new CnsDeudaRecargaRequestBean();
        cnsDeudaRecargaRequestBean.headerBean = a(VCnsDeudaRecarga.getData(eVersionServices), this.sessionManager.getSession(), this.sessionManager.getNup());
        cnsDeudaRecargaRequestBean.cnsDeudaRecargaBodyRequestBean = cnsDeudaRecargaBodyRequestBean;
        new CnsDeudaRecargaRequest(createErrorRequestServer(cnsDeudaRecargaEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                CnsDeudaRecargaResponseBean cnsDeudaRecargaResponseBean = (CnsDeudaRecargaResponseBean) iBeanWS;
                cnsDeudaRecargaEvent.setTypeOption(TypeOption.INITIAL_VIEW);
                cnsDeudaRecargaEvent.setBeanResponse(cnsDeudaRecargaResponseBean);
                cnsDeudaRecargaEvent.setResult(TypeResult.OK);
                cnsDeudaRecargaEvent.setResponse(cnsDeudaRecargaResponseBean);
                MockableDataManager.this.sendBusEvent(cnsDeudaRecargaEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, cnsDeudaRecargaEvent, VCnsDeudaRecarga.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_cnsDeudaRecarga, CnsDeudaRecargaResponseBean.class));
    }

    public void enviarSugerenciaObjeto(String str, String str2, String str3, String str4) {
        if (!mockEnabledServices.aT.booleanValue()) {
            super.enviarSugerenciaObjeto(str, str2, str3, str4);
            return;
        }
        final EnviarSugerenciaObjetoEvent enviarSugerenciaObjetoEvent = new EnviarSugerenciaObjetoEvent();
        AnonymousClass10 r1 = new EnviarSugerenciaObjetoRequest(this.e.getApplicationContext(), new EnviarSugerenciaObjetoRequestBean(a(VEnviarSugerenciaObjeto.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new EnviarSugerenciaObjetoBodyRequestBean(str, str2, str3, str4)), super.createErrorRequestServer(enviarSugerenciaObjetoEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                enviarSugerenciaObjetoEvent.setResponse((EnviarSugerenciaObjetoResponseBean) iBeanWS);
                enviarSugerenciaObjetoEvent.setBeanResponse(enviarSugerenciaObjetoEvent.getResponse());
                enviarSugerenciaObjetoEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) enviarSugerenciaObjetoEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, enviarSugerenciaObjetoEvent, VEnviarSugerenciaObjeto.nameService);
            }
        };
        r1.parserResponse(a((int) R.string.mocks_enviar_sugerencias_objetos_1_0_res1, EnviarSugerenciaObjetoResponseBean.class));
    }

    public void getListaTjWomen() {
        if (!mockEnabledServices.aU.booleanValue()) {
            super.getListaTjWomen();
            return;
        }
        final GetListaTjWomenEvent getListaTjWomenEvent = new GetListaTjWomenEvent();
        new GetListaTjWomenRequest(super.createErrorRequestServer(getListaTjWomenEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetListaTjWomenResponseBean getListaTjWomenResponseBean = (GetListaTjWomenResponseBean) iBeanWS;
                getListaTjWomenEvent.setResponse(getListaTjWomenResponseBean);
                getListaTjWomenEvent.setResult(TypeResult.OK);
                getListaTjWomenEvent.setMessageToShow(getListaTjWomenResponseBean.getListaTjWomenBodyResponseBean().resDesc);
                getListaTjWomenEvent.setTitleToShow(getListaTjWomenResponseBean.getListaTjWomenBodyResponseBean().resTitle);
                MockableDataManager.this.a((WebServiceEvent) getListaTjWomenEvent, 500);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getListaTjWomenEvent, VGetListaTjWomen.nameService);
            }
        }.parserResponse(b(R.string.mocks_Lista_Tj_Women_1_0_res_0, GetListaTjWomenResponseBean.class));
    }

    public void womenProgramSuscription(String str, List<ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta> list) {
        if (!mockEnabledServices.aV.booleanValue()) {
            super.womenProgramSuscription(str, list);
            return;
        }
        final SuscripcionWomenEvent suscripcionWomenEvent = new SuscripcionWomenEvent();
        new SuscripcionWomenRequest(super.createErrorRequestServer(suscripcionWomenEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                SuscripcionWomenResponseBean suscripcionWomenResponseBean = (SuscripcionWomenResponseBean) iBeanWS;
                suscripcionWomenEvent.setResponse(suscripcionWomenResponseBean);
                suscripcionWomenEvent.setResult(TypeResult.OK);
                suscripcionWomenEvent.setMessageToShow(suscripcionWomenResponseBean.getSuscripcionWomenBodyResponseBean().resDesc);
                suscripcionWomenEvent.setTitleToShow(suscripcionWomenResponseBean.getSuscripcionWomenBodyResponseBean().resTitle);
                MockableDataManager.this.a((WebServiceEvent) suscripcionWomenEvent, 500);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, suscripcionWomenEvent, vSuscripcionWomen.nameService);
            }
        }.parserResponse(b(R.string.mocks_Suscripcion_Tj_Women_1_1_res_0, SuscripcionWomenResponseBean.class));
    }

    public void getTenenciaCreditos() {
        if (!mockEnabledServices.aJ.booleanValue()) {
            super.getTenenciaCreditos();
            return;
        }
        final GetTenenciaCreditosEvent getTenenciaCreditosEvent = new GetTenenciaCreditosEvent();
        AnonymousClass13 r1 = new GetTenenciaCreditosRequest(this.e.getApplicationContext(), new GetTenenciaCreditosRequestBean(a(VGetTenenciaCreditos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup())), createErrorRequestServer(getTenenciaCreditosEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getTenenciaCreditosEvent.setResponse((GetTenenciaCreditosResponseBean) iBeanWS);
                getTenenciaCreditosEvent.setBeanResponse(getTenenciaCreditosEvent.getResponse());
                getTenenciaCreditosEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getTenenciaCreditosEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getTenenciaCreditosEvent, VGetTenenciaCreditos.nameService);
            }
        };
        r1.parserResponse(b(R.string.mocks_tenencia_creditosUVAS_1_1_res_1, GetTenenciaCreditosResponseBean.class));
    }

    public void getDetalleCuotaTenenciaCredito(String str, String str2, String str3) {
        if (!mockEnabledServices.aO.booleanValue()) {
            super.getDetalleCuotaTenenciaCredito(str, str2, str3);
            return;
        }
        GetDetalleCuotaTenenciaCreditoEvent getDetalleCuotaTenenciaCreditoEvent = new GetDetalleCuotaTenenciaCreditoEvent();
        GetDetalleCuotaTenenciaCreditoResponseBean getDetalleCuotaTenenciaCreditoResponseBean = (GetDetalleCuotaTenenciaCreditoResponseBean) a((int) R.string.mocks_detalle_cuota_tenencia_credito_1_1_res_0, GetDetalleCuotaTenenciaCreditoResponseBean.class);
        getDetalleCuotaTenenciaCreditoEvent.setBeanResponse(getDetalleCuotaTenenciaCreditoResponseBean);
        getDetalleCuotaTenenciaCreditoEvent.setMessageToShow(getDetalleCuotaTenenciaCreditoResponseBean.getDetalleCuotaTenenciaCreditoBodyResponseBean().resDesc);
        getDetalleCuotaTenenciaCreditoEvent.setTitleToShow(getDetalleCuotaTenenciaCreditoResponseBean.getDetalleCuotaTenenciaCreditoBodyResponseBean().resTitle);
        getDetalleCuotaTenenciaCreditoEvent.setResult(TypeResult.OK);
        a((WebServiceEvent) getDetalleCuotaTenenciaCreditoEvent, 1000);
    }

    public void getProximasCuotas(String str, String str2, String str3) {
        if (!mockEnabledServices.aP.booleanValue()) {
            super.getProximasCuotas(str, str2, str3);
            return;
        }
        GetProximasCuotasCreditoEvent getProximasCuotasCreditoEvent = new GetProximasCuotasCreditoEvent();
        GetProximasCuotasResponseBean getProximasCuotasResponseBean = (GetProximasCuotasResponseBean) a((int) R.string.mocks_detalle_cuota_credito_res_0, GetProximasCuotasResponseBean.class);
        getProximasCuotasCreditoEvent.setBeanResponse(getProximasCuotasResponseBean);
        getProximasCuotasCreditoEvent.setMessageToShow(getProximasCuotasResponseBean.mGetProximasCuotasBodyResponseBean.resDesc);
        getProximasCuotasCreditoEvent.setTitleToShow(getProximasCuotasResponseBean.mGetProximasCuotasBodyResponseBean.resTitle);
        getProximasCuotasCreditoEvent.setResult(TypeResult.OK);
        a((WebServiceEvent) getProximasCuotasCreditoEvent, 1000);
    }

    public void pagoTarjeta(ArrayList<String> arrayList) {
        if (!mockEnabledServices.aI.booleanValue()) {
            super.pagoTarjeta(arrayList);
            return;
        }
        final PagoTarjetaEvent pagoTarjetaEvent = new PagoTarjetaEvent();
        new PagoTarjetaRequest(super.createErrorRequestServer(pagoTarjetaEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                PagoTarjetaResponseBean pagoTarjetaResponseBean = (PagoTarjetaResponseBean) iBeanWS;
                pagoTarjetaEvent.setResponseBean(pagoTarjetaResponseBean);
                pagoTarjetaEvent.setBeanResponse(pagoTarjetaResponseBean);
                pagoTarjetaEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) pagoTarjetaEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, pagoTarjetaEvent, VPagoTarjeta.nameService);
            }
        }.parserResponse(b(R.string.mocks_pagoTarjetas, PagoTarjetaResponseBean.class));
    }

    public void consultaSuscripcionMyA(String str, String str2) {
        ConsultaSuscripcionMyAEvent consultaSuscripcionMyAEvent = new ConsultaSuscripcionMyAEvent();
        consultaSuscripcionMyAEvent.setBeanResponse(a((int) R.string.mocks_consultaSuscripcionMyA_suscripto, ConsultaSuscripcionMyAResponeBean.class));
        a((WebServiceEvent) consultaSuscripcionMyAEvent, 750);
    }

    public void modificarSuscripcionMyA(Destinos destinos) {
        ModificarSuscripcionEvent modificarSuscripcionEvent = new ModificarSuscripcionEvent();
        modificarSuscripcionEvent.setResult(TypeResult.OK);
        a((WebServiceEvent) modificarSuscripcionEvent, 750);
    }

    public void consultaSolicitudCrediticiaProd(ConsultaSolicitudCrediticiaBodyRequestBeanProd consultaSolicitudCrediticiaBodyRequestBeanProd) {
        if (!mockEnabledServices.aF.booleanValue()) {
            super.consultaSolicitudCrediticiaProd(consultaSolicitudCrediticiaBodyRequestBeanProd);
            return;
        }
        final ConsultaSolicitudCrediticiaEventProd consultaSolicitudCrediticiaEventProd = new ConsultaSolicitudCrediticiaEventProd();
        ConsultaSolicitudCrediticiaRequestBeanProd consultaSolicitudCrediticiaRequestBeanProd = new ConsultaSolicitudCrediticiaRequestBeanProd();
        consultaSolicitudCrediticiaRequestBeanProd.headerBean = a(VConsultaSolicitudCrediticiaProd.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        consultaSolicitudCrediticiaRequestBeanProd.consultaSolicitudCrediticiaBodyRequestBeanProd = consultaSolicitudCrediticiaBodyRequestBeanProd;
        new ConsultaSolicitudCrediticiaRequestProd(createErrorRequestServer(consultaSolicitudCrediticiaEventProd, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaSolicitudCrediticiaEventProd.setBeanResponse((ConsultaSolicitudCrediticiaResponseBeanProd) iBeanWS);
                consultaSolicitudCrediticiaEventProd.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(consultaSolicitudCrediticiaEventProd);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, consultaSolicitudCrediticiaEventProd, "consultaSolicitudCrediticia");
            }
        }.parserResponse(b(R.string.mocks_consultaSolicitudCrediticia, ConsultaSolicitudCrediticiaResponseBeanProd.class));
    }

    public void consultaPrestamosPermitidosProd(ConsultaPrestamosPermitidosBodyRequestBeanProd consultaPrestamosPermitidosBodyRequestBeanProd, final boolean z) {
        if (!mockEnabledServices.aG.booleanValue()) {
            super.consultaPrestamosPermitidosProd(consultaPrestamosPermitidosBodyRequestBeanProd, z);
            return;
        }
        final ConsultaPrestamosPermitidosEventProd consultaPrestamosPermitidosEventProd = new ConsultaPrestamosPermitidosEventProd();
        ConsultaPrestamosPermitidosRequestBeanProd consultaPrestamosPermitidosRequestBeanProd = new ConsultaPrestamosPermitidosRequestBeanProd();
        consultaPrestamosPermitidosRequestBeanProd.headerBean = a(VConsultaPrestamosPermitidosProd.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        consultaPrestamosPermitidosRequestBeanProd.consultaPrestamosPermitidosBodyRequestBeanProd = consultaPrestamosPermitidosBodyRequestBeanProd;
        new ConsultaPrestamosPermitidosRequestProd(createErrorRequestServer(consultaPrestamosPermitidosEventProd, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaPrestamosPermitidosEventProd.setBeanResponse((ConsultaPrestamosPermitidosResponseBeanProd) iBeanWS);
                consultaPrestamosPermitidosEventProd.setResult(TypeResult.OK);
                if (z) {
                    MockableDataManager.this.sendBusEvent(consultaPrestamosPermitidosEventProd);
                }
            }

            public void onUnknowError(Exception exc) {
                if (z) {
                    MockableDataManager.this.createUnknowErrorEventProcesor(exc, consultaPrestamosPermitidosEventProd, "consultaPrestamosPermitidos");
                }
            }
        }.parserResponse(b(R.string.mocks_consultaPrestamosPermitidos_res0_uva_leyendas, ConsultaPrestamosPermitidosResponseBeanProd.class));
    }

    public void solicitudPrestamoPreacordadoProd(SolicitudPrestamoPreacordadoBodyRequestBeanProd solicitudPrestamoPreacordadoBodyRequestBeanProd, Activity activity) {
        if (!mockEnabledServices.aH.booleanValue()) {
            super.solicitudPrestamoPreacordadoProd(solicitudPrestamoPreacordadoBodyRequestBeanProd, activity);
            return;
        }
        final SolicitudPrestamoPreacordadoEventProd solicitudPrestamoPreacordadoEventProd = new SolicitudPrestamoPreacordadoEventProd();
        String opcion = solicitudPrestamoPreacordadoBodyRequestBeanProd.getDatosSolicitudPrestamoProd().getOpcion();
        SolicitudPrestamoPreacordadoRequestBeanProd solicitudPrestamoPreacordadoRequestBeanProd = new SolicitudPrestamoPreacordadoRequestBeanProd();
        solicitudPrestamoPreacordadoRequestBeanProd.headerBean = a(VSolicitudPrestamoPreacordadoProd.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true), activity);
        solicitudPrestamoPreacordadoRequestBeanProd.solicitudPrestamoPreacordadoBodyRequestBeanProd = solicitudPrestamoPreacordadoBodyRequestBeanProd;
        TypeOption typeOption = TypeOption.INTERMDIATE_VIEW;
        if (opcion.equalsIgnoreCase(CreditosConstantsProd.LIQUIDACION)) {
            typeOption = TypeOption.TRANSACTIONAL_FINAL_VIEW;
        }
        new SolicitudPrestamoPreacordadoRequestProd(createErrorRequestServer(solicitudPrestamoPreacordadoEventProd, typeOption)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                solicitudPrestamoPreacordadoEventProd.setBeanResponse((SolicitudPrestamoPreacordadoResponseBeanProd) iBeanWS);
                solicitudPrestamoPreacordadoEventProd.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(solicitudPrestamoPreacordadoEventProd);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, solicitudPrestamoPreacordadoEventProd, "solicitudPrestamoPreacordado");
            }
        }.parserResponse(b(R.string.mocks_solicitudPrestamoPreacordado_Res0_v1_4_simulacion, SolicitudPrestamoPreacordadoResponseBeanProd.class));
    }

    public void consultaSolicitudCrediticia(ConsultaSolicitudCrediticiaBodyRequestBean consultaSolicitudCrediticiaBodyRequestBean) {
        if (!mockEnabledServices.aF.booleanValue()) {
            super.consultaSolicitudCrediticia(consultaSolicitudCrediticiaBodyRequestBean);
            return;
        }
        final ConsultaSolicitudCrediticiaEvent consultaSolicitudCrediticiaEvent = new ConsultaSolicitudCrediticiaEvent();
        ConsultaSolicitudCrediticiaRequestBean consultaSolicitudCrediticiaRequestBean = new ConsultaSolicitudCrediticiaRequestBean();
        consultaSolicitudCrediticiaRequestBean.headerBean = a(VConsultaSolicitudCrediticia.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        consultaSolicitudCrediticiaRequestBean.consultaSolicitudCrediticiaBodyRequestBean = consultaSolicitudCrediticiaBodyRequestBean;
        new ConsultaSolicitudCrediticiaRequest(createErrorRequestServer(consultaSolicitudCrediticiaEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaSolicitudCrediticiaEvent.setBeanResponse((ConsultaSolicitudCrediticiaResponseBean) iBeanWS);
                consultaSolicitudCrediticiaEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) consultaSolicitudCrediticiaEvent, 1500);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, consultaSolicitudCrediticiaEvent, "consultaSolicitudCrediticia");
            }
        }.parserResponse(b(R.string.mocks_consultaSolicitudCrediticia_res0_v1_0, ConsultaSolicitudCrediticiaResponseBean.class));
    }

    public void consultaPrestamosPermitidos(ConsultaPrestamosPermitidosBodyRequestBean consultaPrestamosPermitidosBodyRequestBean, final boolean z) {
        if (!mockEnabledServices.aG.booleanValue()) {
            super.consultaPrestamosPermitidos(consultaPrestamosPermitidosBodyRequestBean, z);
            return;
        }
        final ConsultaPrestamosPermitidosEvent consultaPrestamosPermitidosEvent = new ConsultaPrestamosPermitidosEvent();
        ConsultaPrestamosPermitidosRequestBean consultaPrestamosPermitidosRequestBean = new ConsultaPrestamosPermitidosRequestBean();
        consultaPrestamosPermitidosRequestBean.headerBean = a(VConsultaPrestamosPermitidos.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        consultaPrestamosPermitidosRequestBean.consultaPrestamosPermitidosBodyRequestBean = consultaPrestamosPermitidosBodyRequestBean;
        new ConsultaPrestamosPermitidosRequest(createErrorRequestServer(consultaPrestamosPermitidosEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaPrestamosPermitidosEvent.setBeanResponse((ConsultaPrestamosPermitidosResponseBean) iBeanWS);
                consultaPrestamosPermitidosEvent.setResult(TypeResult.OK);
                if (z) {
                    MockableDataManager.this.a((WebServiceEvent) consultaPrestamosPermitidosEvent, 1500);
                }
            }

            public void onUnknowError(Exception exc) {
                if (z) {
                    MockableDataManager.this.createUnknowErrorEventProcesor(exc, consultaPrestamosPermitidosEvent, "consultaPrestamosPermitidos");
                }
            }
        }.parserResponse(a("<xml><header><idAplicacion>MBI</idAplicacion><servicio><nombre>consultaPrestamosPermitidos</nombre><version>1.1</version></servicio></header><body><res>0</res><datosCuenta><tipo>09</tipo><sucursal>000</sucursal><numero>0625078</numero></datosCuenta><datosPrest><minImpPrest>1000</minImpPrest><maxImpPrest>2000.00</maxImpPrest><valorDivisa>ARS</valorDivisa></datosPrest><listaLeyendas><leyenda><idLeyenda>PRE_TYC</idLeyenda><descLeyenda><![CDATA[(*) El crédito se encuentra sujeto a las condiciones de contratación y otorgamiento de Santander Río.\nBanco Santander Río S.A, CUIT: 30-50000845-4. Bartolomé Mitre 480, C.A.B.A\n]]></descLeyenda></leyenda><leyenda><idLeyenda>PRE_TYC_SOL</idLeyenda><descLeyenda><![CDATA[<p style=\"color: #333333;\">El plazo del \"primer vencimiento\", no podrá superar los 90 días, desde la fecha de activación del crédito. Tenga en cuenta que el día elegido como \"primer vencimiento\", en adelante  será el día, su fecha  del mes en que vencerá cada cuota de vencimiento mensual. El importe de la primer cuota será calculado de acuerdo a la cantidad de días existentes entre la fecha de liquidación y la fecha hasta la cual se difiera su pago. Al vencimiento de la primer cuota, se deberán abonar los intereses acumulados hasta ese momento.</p>\n]]></descLeyenda></leyenda><leyenda><idLeyenda>CRED_TNA</idLeyenda><descLeyenda><![CDATA[TNA: Tasa Nominal Anual\n]]></descLeyenda></leyenda><leyenda><idLeyenda>CRED_TEA</idLeyenda><descLeyenda><![CDATA[TEA: Tasa Efectiva Anual\n]]></descLeyenda></leyenda><leyenda><idLeyenda>CRED_CFTEA_SIMP</idLeyenda><descLeyenda><![CDATA[CFTEA S/imp: Costo Financiero Total Efectivo Anual sin impuestos\n]]></descLeyenda></leyenda><leyenda><idLeyenda>CRED_CFTEA</idLeyenda><descLeyenda><![CDATA[CFTEA: Costo Financiero Total Efectivo Anual\n]]></descLeyenda></leyenda><leyenda><idLeyenda>CRED_SEGURO</idLeyenda><descLeyenda><![CDATA[<p style=\"color: #333333;\">Este seguro no es obligatorio ni accesorio a otro producto.<br/>Agente Institorio inscripto en el Registro de Agentes Institorios bajo el nro. 139, de conformidad con la Resolución SSN N° 38052 del 20/12/2013: Banco Santander Río S.A.<br/>Empresa Aseguradora: Zurich Santander Seguros Argentina S.A., domicilio 25 de Mayo 140 3° piso, Ciudad Autónoma de Bs. As. CUIT: 30-69896545-9. Inscripto en la Superintendencia de Seguros de la Nación (SSN) mediante Nº 0692. Tel SSN 4338-4000 O 0800-666-8400, www.ssn.gob.ar. Registrada en Inspección General de Justicia bajo N° correlativo 1652779.</p>\n]]></descLeyenda></leyenda><leyenda><idLeyenda>CUOTA_PURA_UVAS</idLeyenda><descLeyenda><![CDATA[(*) Incluye capital e intereses. No incluye impuestos los que serán calculados sobre el importe de la cuota en pesos.\n]]></descLeyenda></leyenda><leyenda><idLeyenda>PRE_TRAD</idLeyenda><descLeyenda><![CDATA[1. El préstamo es un adelanto en cuenta corriente pagadero en cuotas, por lo tanto, si por cualquier causa se cerrara la cuenta, esta operación será  considerada una operación pendiente de pago en la mencionada cuenta, y todo saldo impago será debitado de la cuenta corriente en forma previa al cierre.<br/>2. Las cuotas de capital, los intereses compensatorios del período e impuestos provinciales de corresponder serán debitados al vencimiento, de la cuenta. Cualquier interés impago se capitaliza en el período siguiente. Al momento de puesta a disposición de los fondos se informará la tasa a aplicar para la operación de crédito solicitada.<br/>3. El cliente tiene derecho a pre cancelar total o parcialmente el Préstamo Personal en cualquier momento del plazo del mismo. En dicho caso se cobrará una comisión del 4% + IVA para Black, Platinum e Infinity Gold, y del 6% + IVA para el resto de los segmentos, sobre el monto a cancelar. Para clientes individuos la comisión por cancelación anticipada sólo será aplicada cuando al momento de la cancelación no haya transcurrido al menos la cuarta parte del plazo original del Préstamo Personal o 180 días desde su otorgamiento, de ambos el mayor. Para clientes Advance, la comisión por cancelación anticipada será aplicada en cualquier momento del préstamo.<br/>4. Ud. puede revocar la contratación de este producto dentro de los diez días comunicando tal decisión por escrito o medios electrónicos. Dicha revocación es sin costo ni responsabilidad alguna (siempre y cuando no haya utilizado el producto). En caso de uso, sólo deberá abonar las comisiones y cargos previstos, proporcionales al tiempo transcurrido.<br/>5. Ud. puede consultar el ¿Régimen de Transparencia¿  elaborado por el Banco Central en base a la información proporcionada por los sujetos obligados a fin de comparar los costos, características y requisitos de los productos y servicios financieros, ingresando a http://www.bcra.gob.ar.BCTAyVos/Regimen_de_transparencia.asp<br/>Banco Santander Río S.A. CUIT: 30-50000845-4. Bartolomé Mitre 480, C.A.B.A.\n]]></descLeyenda></leyenda><leyenda><idLeyenda>PRE_UVA</idLeyenda><descLeyenda><![CDATA[<p><b>1. Los saldos adeudados se actualizarán mediante la aplicación del Coeficiente de Estabilización de Referencia (CER) y asimismo se expresarán en cantidades de  Unidades de Valor Adquisitivo (\"UVAs\"), siendo el valor en pesos de cada UVA al momento de su desembolso, el que el Banco Central de la República Argentina publique periódicamente. El importe a reembolsar por el Solicitante, será el equivalente en pesos al capital ajustado, más sus intereses, el que también se exhibirá en la cantidad de \"UVAs\" adeudadas al momento de cada uno de los vencimientos, calculado el valor de la \"UVA\" de la fecha en la que se haga efectivo el pago. Los intereses a pagar se computarán sobre el capital en pesos adeudados al momento del vencimiento de cada cuota, calculado según lo expresado en el párrafo anterior.<br/>Riesgos de la operación:<br/>Los efectos de la inflación y la evolución de los precios tendrán correlación en el monto de capital adeudado  y también en el monto de los intereses a pagar ya que se calcularán sobre un importe que se ajusta en su valor;<br/>Los procesos inflacionarios de la Argentina a lo largo del tiempo muestran que es posible que la inflación alcance niveles elevados;<br/>Tene en cuenta que el valor de la cuota aumentará al mismo ritmo de la inflación y que tu salario o ingresos podrán no incrementarse en la misma proporción<br/>En caso de que se suspenda o se interrumpa la publicación del CER, el Banco para obtener el mismo, utilizará la metodología de cálculo establecida en el Anexo I de la Ley 25.713. En caso de que se suspenda o interrumpa la publicación del IPC elaborado por el INDEC, que se utiliza para construir el CER, se tomará como referencia cualquier otro índice de precios elaborado por las provincias y/o por la Ciudad de Buenos Aires. En caso de que se suspenda o se interrumpa la publicación de cualquiera de los índices mencionados, e inclusive de los que eventualmente los reemplacen, el Banco tomará como referencia cualquier otro índice que, a su criterio razonable, refleje la evolución del CER y permita así actualizar el monto del Préstamo o saldo de deuda.</b></p><p style=\"color: #333333;\">2. El préstamo es un adelanto en cuenta corriente pagadero en cuotas, por lo tanto, si por cualquier causa se cerrara la cuenta, esta operación será  considerada una operación pendiente de pago en la mencionada cuenta, y todo saldo impago será debitado de la cuenta corriente en forma previa al cierre.</p><p style=\"color: #333333;\">3. Las cuotas de capital, los intereses compensatorios del período e impuestos provinciales de corresponder serán debitados al vencimiento, de la cuenta. Cualquier interés impago se capitaliza en el período siguiente. Al momento de puesta a disposición de los fondos se informará la tasa a aplicar para la operación de crédito solicitada.</p><p style=\"color: #333333;\">4. El cliente tiene derecho a pre cancelar total o parcialmente el Préstamo Personal en cualquier momento del plazo del mismo. En dicho caso se cobrará una comisión del 4% + IVA para Black, Platinum e Infinity Gold, y del 6% + IVA para el resto de los segmentos, sobre el monto a cancelar. Para clientes individuos la comisión por cancelación anticipada sólo será aplicada cuando al momento de la cancelación no haya transcurrido al menos la cuarta parte del plazo original del Préstamo Personal o 180 días desde su otorgamiento, de ambos el mayor. Para clientes Advance, la comisión por cancelación anticipada será aplicada en cualquier momento del préstamo.</p><p style=\"color: #333333;\">5. Ud. puede revocar la contratación de este producto dentro de los diez días comunicando tal decisión por escrito o medios electrónicos. Dicha revocación es sin costo ni responsabilidad alguna (siempre y cuando no haya utilizado el producto). En caso de uso, sólo deberá abonar las comisiones y cargos previstos, proporcionales al tiempo transcurrido.</p><p style=\"color: #333333;\">6. Ud. puede consultar el \"Régimen de Transparencia\"  elaborado por el Banco Central en base a la información proporcionada por los sujetos obligados a fin de comparar los costos, características y requisitos de los productos y servicios financieros, ingresando a http://www.bcra.gob.ar.BCTAyVos/Regimen_de_transparencia.asp<br/>Banco Santander Río S.A. CUIT: 30-50000845-4. Bartolomé Mitre 480, C.A.B.A.</p>\n]]></descLeyenda></leyenda></listaLeyendas><prestPermitidos><datosPrestPerm><minCantCuotas>03</minCantCuotas><maxCantCuotas>36</maxCantCuotas><tipoTasa>F</tipoTasa><valorTasa>56.00</valorTasa><minValPrest>1000.00</minValPrest><maxValPrest>2000.00</maxValPrest><codProdUG>35</codProdUG><codSubprodUG>0027</codSubprodUG><destFondosComerc>99000</destFondosComerc><marcaEmpl>N</marcaEmpl><tipoPolizaDs>Z10</tipoPolizaDs><tipoRiesgoDs>VP1</tipoRiesgoDs><codTasaUg>R736</codTasaUg><marPlanSueldoBg>N</marPlanSueldoBg><indicadorUVA>N</indicadorUVA><listaDestinoPrest><listaDestino><destFondo>35001</destFondo><destPrestamo>Viajes / turismo</destPrestamo></listaDestino><listaDestino><destFondo>35002</destFondo><destPrestamo>Compra de auto</destPrestamo></listaDestino><listaDestino><destFondo>35008</destFondo><destPrestamo>Otros destinos</destPrestamo></listaDestino><listaDestino><destFondo>35133</destFondo><destPrestamo>Adquisicion automotores utilitarios</destPrestamo></listaDestino><listaDestino><destFondo>35134</destFondo><destPrestamo>Adquisicion electrodomesticos y art hog</destPrestamo></listaDestino><listaDestino><destFondo>35135</destFondo><destPrestamo>Adquisicion maquinarias, equip y herram</destPrestamo></listaDestino><listaDestino><destFondo>35136</destFondo><destPrestamo>Refinanciacion o cancelac pasivos</destPrestamo></listaDestino><listaDestino><destFondo>35138</destFondo><destPrestamo>Adquisicion de inmuebles</destPrestamo></listaDestino><listaDestino><destFondo>35139</destFondo><destPrestamo>Refaccion o construccion de inmuebles</destPrestamo></listaDestino></listaDestinoPrest></datosPrestPerm><datosPrestPerm><minCantCuotas>37</minCantCuotas><maxCantCuotas>72</maxCantCuotas><tipoTasa>F</tipoTasa><valorTasa>51.00</valorTasa><minValPrest>1000.00</minValPrest><maxValPrest>2000.00</maxValPrest><codProdUG>35</codProdUG><codSubprodUG>0047</codSubprodUG><destFondosComerc>99000</destFondosComerc><marcaEmpl>N</marcaEmpl><tipoPolizaDs>Z10</tipoPolizaDs><tipoRiesgoDs>VP1</tipoRiesgoDs><codTasaUg>R739</codTasaUg><marPlanSueldoBg>N</marPlanSueldoBg><indicadorUVA>N</indicadorUVA><listaDestinoPrest><listaDestino><destFondo>35147</destFondo><destPrestamo>Adquisicion electrod y prod hogar</destPrestamo></listaDestino><listaDestino><destFondo>35148</destFondo><destPrestamo>Adquision de inmuebles</destPrestamo></listaDestino><listaDestino><destFondo>35149</destFondo><destPrestamo>Refaccion construc inmbuebles</destPrestamo></listaDestino><listaDestino><destFondo>35150</destFondo><destPrestamo>Adquisicion automotores utilitario</destPrestamo></listaDestino><listaDestino><destFondo>35151</destFondo><destPrestamo>Compra de auto</destPrestamo></listaDestino><listaDestino><destFondo>35152</destFondo><destPrestamo>Adquisicion maquinaria y herramientas</destPrestamo></listaDestino><listaDestino><destFondo>35153</destFondo><destPrestamo>Viajes y turismo</destPrestamo></listaDestino><listaDestino><destFondo>35154</destFondo><destPrestamo>Refinanciacion o cancelacion de pasivos</destPrestamo></listaDestino></listaDestinoPrest></datosPrestPerm><datosPrestPerm><minCantCuotas>12</minCantCuotas><maxCantCuotas>36</maxCantCuotas><tipoTasa>F</tipoTasa><valorTasa>56.00</valorTasa><minValPrest>1000.00</minValPrest><maxValPrest>2000.00</maxValPrest><codProdUG>35</codProdUG><codSubprodUG>0106</codSubprodUG><destFondosComerc>99000</destFondosComerc><marcaEmpl>N</marcaEmpl><tipoPolizaDs>Z10</tipoPolizaDs><tipoRiesgoDs>VP1</tipoRiesgoDs><codTasaUg>R736</codTasaUg><marPlanSueldoBg>N</marPlanSueldoBg><indicadorUVA>S</indicadorUVA><listaDestinoPrest><listaDestino><destFondo>35001</destFondo><destPrestamo>Viajes / turismo</destPrestamo></listaDestino><listaDestino><destFondo>35002</destFondo><destPrestamo>Compra de auto</destPrestamo></listaDestino><listaDestino><destFondo>35008</destFondo><destPrestamo>Otros destinos</destPrestamo></listaDestino><listaDestino><destFondo>35133</destFondo><destPrestamo>Adquisicion automotores utilitarios</destPrestamo></listaDestino><listaDestino><destFondo>35134</destFondo><destPrestamo>Adquisicion electrodomesticos y art hog</destPrestamo></listaDestino><listaDestino><destFondo>35135</destFondo><destPrestamo>Adquisicion maquinarias, equip y herram</destPrestamo></listaDestino><listaDestino><destFondo>35136</destFondo><destPrestamo>Refinanciacion o cancelac pasivos</destPrestamo></listaDestino><listaDestino><destFondo>35138</destFondo><destPrestamo>Adquisicion de inmuebles</destPrestamo></listaDestino><listaDestino><destFondo>35139</destFondo><destPrestamo>Refaccion o construccion de inmuebles</destPrestamo></listaDestino></listaDestinoPrest></datosPrestPerm><datosPrestPerm><minCantCuotas>37</minCantCuotas><maxCantCuotas>72</maxCantCuotas><tipoTasa>F</tipoTasa><valorTasa>51.00</valorTasa><minValPrest>1000.00</minValPrest><maxValPrest>2000.00</maxValPrest><codProdUG>35</codProdUG><codSubprodUG>0107</codSubprodUG><destFondosComerc>99000</destFondosComerc><marcaEmpl>N</marcaEmpl><tipoPolizaDs>Z10</tipoPolizaDs><tipoRiesgoDs>VP1</tipoRiesgoDs><codTasaUg>R739</codTasaUg><marPlanSueldoBg>N</marPlanSueldoBg><indicadorUVA>S</indicadorUVA><listaDestinoPrest><listaDestino><destFondo>35001</destFondo><destPrestamo>Viajes / turismo</destPrestamo></listaDestino><listaDestino><destFondo>35002</destFondo><destPrestamo>Compra de auto</destPrestamo></listaDestino><listaDestino><destFondo>35008</destFondo><destPrestamo>Otros destinos</destPrestamo></listaDestino><listaDestino><destFondo>35133</destFondo><destPrestamo>Adquisicion automotores utilitarios</destPrestamo></listaDestino><listaDestino><destFondo>35134</destFondo><destPrestamo>Adquisicion electrodomesticos y art hog</destPrestamo></listaDestino><listaDestino><destFondo>35135</destFondo><destPrestamo>Adquisicion maquinarias, equip y herram</destPrestamo></listaDestino><listaDestino><destFondo>35136</destFondo><destPrestamo>Refinanciacion o cancelac pasivos</destPrestamo></listaDestino><listaDestino><destFondo>35138</destFondo><destPrestamo>Adquisicion de inmuebles</destPrestamo></listaDestino><listaDestino><destFondo>35139</destFondo><destPrestamo>Refaccion o construccion de inmuebles</destPrestamo></listaDestino></listaDestinoPrest></datosPrestPerm></prestPermitidos></body></xml>", ConsultaPrestamosPermitidosResponseBean.class));
    }

    public void solicitudPrestamoPreacordado(SolicitudPrestamoPreacordadoBodyRequestBean solicitudPrestamoPreacordadoBodyRequestBean, Activity activity) {
        if (!mockEnabledServices.aH.booleanValue()) {
            super.solicitudPrestamoPreacordado(solicitudPrestamoPreacordadoBodyRequestBean, activity);
            return;
        }
        final SolicitudPrestamoPreacordadoEvent solicitudPrestamoPreacordadoEvent = new SolicitudPrestamoPreacordadoEvent();
        String opcion = solicitudPrestamoPreacordadoBodyRequestBean.getDatosSolicitudPrestamo().getOpcion();
        SolicitudPrestamoPreacordadoRequestBean solicitudPrestamoPreacordadoRequestBean = new SolicitudPrestamoPreacordadoRequestBean();
        solicitudPrestamoPreacordadoRequestBean.headerBean = a(VSolicitudPrestamoPreacordado.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup(), Boolean.valueOf(true), Boolean.valueOf(true), activity);
        solicitudPrestamoPreacordadoRequestBean.solicitudPrestamoPreacordadoBodyRequestBean = solicitudPrestamoPreacordadoBodyRequestBean;
        TypeOption typeOption = TypeOption.INTERMDIATE_VIEW;
        if (opcion.equalsIgnoreCase(CreditosConstants.LIQUIDACION)) {
            typeOption = TypeOption.TRANSACTIONAL_FINAL_VIEW;
        }
        AnonymousClass20 r13 = new SolicitudPrestamoPreacordadoRequest(createErrorRequestServer(solicitudPrestamoPreacordadoEvent, typeOption)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                solicitudPrestamoPreacordadoEvent.setBeanResponse((SolicitudPrestamoPreacordadoResponseBean) iBeanWS);
                solicitudPrestamoPreacordadoEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(solicitudPrestamoPreacordadoEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, solicitudPrestamoPreacordadoEvent, "solicitudPrestamoPreacordado");
            }
        };
        if (opcion.equalsIgnoreCase(CreditosConstants.LIQUIDACION)) {
            r13.parserResponse(b(R.string.mocks_solicitudPrestamoPreacordado_Res0_v1_4_liquidacion, SolicitudPrestamoPreacordadoResponseBean.class));
        } else if (opcion.equalsIgnoreCase(CreditosConstants.SIMULACION)) {
            r13.parserResponse(b(R.string.mocks_solicitudPrestamoPreacordado_Res0_v1_4_simulacion, SolicitudPrestamoPreacordadoResponseBean.class));
        }
    }

    public void contratarSeguroAccidente(CotizacionSeguroAccidenteBean cotizacionSeguroAccidenteBean, String str, String str2, String str3) {
        if (!mockEnabledServices.aR.booleanValue()) {
            super.contratarSeguroAccidente(cotizacionSeguroAccidenteBean, str, str2, str3);
            return;
        }
        final ContratarSeguroAccidenteEvent contratarSeguroAccidenteEvent = new ContratarSeguroAccidenteEvent();
        new ContratarSeguroAccidenteRequestBean().header = a(VGetContratarSeguroAccidente.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        new ContratarSeguroAccidenteRequest(createErrorRequestServer(contratarSeguroAccidenteEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                ContratarSeguroAccidenteResponseBean contratarSeguroAccidenteResponseBean = (ContratarSeguroAccidenteResponseBean) iBeanWS;
                contratarSeguroAccidenteEvent.setResponse(contratarSeguroAccidenteResponseBean);
                contratarSeguroAccidenteEvent.setBeanResponse(contratarSeguroAccidenteResponseBean);
                contratarSeguroAccidenteEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) contratarSeguroAccidenteEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, contratarSeguroAccidenteEvent, VGetContratarSeguroAccidente.nameService);
            }
        }.parserResponse(b(R.string.mocks_contratarSeguroAccidente, ContratarSeguroAccidenteResponseBean.class));
    }

    public void abmTurno(String str, String str2, String str3, String str4) {
        if (!mockEnabledServices.aS.booleanValue()) {
            super.abmTurno(str, str2, str3, str4);
            return;
        }
        final AbmTurnoEvent abmTurnoEvent = new AbmTurnoEvent();
        new AbmTurnoRequestBean().header = a(VAbmTurno.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        new AbmTurnoRequest(createErrorRequestServer(abmTurnoEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                AbmTurnoResponseBean abmTurnoResponseBean = (AbmTurnoResponseBean) iBeanWS;
                abmTurnoEvent.setResponse(abmTurnoResponseBean);
                abmTurnoEvent.setBeanResponse(abmTurnoResponseBean);
                abmTurnoEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) abmTurnoEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, abmTurnoEvent, VAbmTurno.nameService);
            }
        }.parserResponse(b(R.string.mocks_abmTurno, AbmTurnoResponseBean.class));
    }

    public void getCotizacionSeguroAccidente() {
        if (!mockEnabledServices.al.booleanValue()) {
            super.getCotizacionSeguroAccidente();
            return;
        }
        final GetCotizacionSeguroAccidenteEvent getCotizacionSeguroAccidenteEvent = new GetCotizacionSeguroAccidenteEvent();
        new GetCotizacionSeguroAccidenteRequestBean().header = a(VSolicitudPrestamoPreacordado.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        final AnonymousClass23 r1 = new GetCotizacionSeguroAccidenteRequest(createErrorRequestServer(getCotizacionSeguroAccidenteEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetCotizacionSeguroAccidenteResponseBean getCotizacionSeguroAccidenteResponseBean = (GetCotizacionSeguroAccidenteResponseBean) iBeanWS;
                getCotizacionSeguroAccidenteEvent.setResponse(getCotizacionSeguroAccidenteResponseBean);
                getCotizacionSeguroAccidenteEvent.setBeanResponse(getCotizacionSeguroAccidenteResponseBean);
                getCotizacionSeguroAccidenteEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(getCotizacionSeguroAccidenteEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, getCotizacionSeguroAccidenteEvent, VGetCotizacionSeguroAccidente.nameService);
            }
        };
        new Handler().postDelayed(new Runnable() {
            public void run() {
                r1.parserResponse(MockableDataManager.this.b(R.string.mocks_cotizacionSeguroAccidente, GetCotizacionSeguroAccidenteResponseBean.class));
            }
        }, 500);
    }

    public void getTurno() {
        if (!mockEnabledServices.am.booleanValue()) {
            super.getTurno();
            return;
        }
        final GetTurnoEvent getTurnoEvent = new GetTurnoEvent();
        new GetTurnoRequestBean().header = a(VGetTurno.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        final AnonymousClass25 r1 = new GetTurnoRequest(createErrorRequestServer(getTurnoEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetTurnoResponseBean getTurnoResponseBean = (GetTurnoResponseBean) iBeanWS;
                getTurnoEvent.setResponse(getTurnoResponseBean);
                getTurnoEvent.setBeanResponse(getTurnoResponseBean);
                getTurnoEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(getTurnoEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, getTurnoEvent, VGetTurno.nameService);
            }
        };
        new Handler().postDelayed(new Runnable() {
            public void run() {
                r1.parserResponse(MockableDataManager.this.b(R.string.mocks_getTurno_sin_turno, GetTurnoResponseBean.class));
            }
        }, 500);
    }

    public void blanqueoPIN(BlanqueoPinBodyRequestBean blanqueoPinBodyRequestBean, Activity activity) {
        if (!mockEnabledServices.an.booleanValue()) {
            super.blanqueoPIN(blanqueoPinBodyRequestBean, activity);
            return;
        }
        final BlanqueoPINEvent blanqueoPINEvent = new BlanqueoPINEvent();
        new BlanqueoPinRequestBean().header = a(VblanqueoPIN.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        final AnonymousClass27 r5 = new BlanqueoPinRequest(createErrorRequestServer(blanqueoPINEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                BlanqueoPinResponseBean blanqueoPinResponseBean = (BlanqueoPinResponseBean) iBeanWS;
                blanqueoPINEvent.setBlanqueoPinResponseBean(blanqueoPinResponseBean);
                blanqueoPINEvent.setBeanResponse(blanqueoPinResponseBean);
                blanqueoPINEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) blanqueoPINEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, blanqueoPINEvent, VblanqueoPIN.nameService);
            }
        };
        new Handler().postDelayed(new Runnable() {
            public void run() {
                r5.parserResponse(MockableDataManager.this.b(R.string.hello_blank_fragment, BlanqueoPinResponseBean.class));
            }
        }, 500);
    }

    public void getCircuitoTurno(String str) {
        if (!mockEnabledServices.ao.booleanValue()) {
            super.getCircuitoTurno(str);
            return;
        }
        final GetCircuitoTurnoEvent getCircuitoTurnoEvent = new GetCircuitoTurnoEvent();
        new GetCircuitoTurnoRequestBean().header = a(VGetCircuitoTurno.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        final AnonymousClass29 r0 = new GetCircuitoTurnoRequest(createErrorRequestServer(getCircuitoTurnoEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetCircuitoTurnoResponseBean getCircuitoTurnoResponseBean = (GetCircuitoTurnoResponseBean) iBeanWS;
                getCircuitoTurnoEvent.setResponse(getCircuitoTurnoResponseBean);
                getCircuitoTurnoEvent.setBeanResponse(getCircuitoTurnoResponseBean);
                getCircuitoTurnoEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(getCircuitoTurnoEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, getCircuitoTurnoEvent, VGetCircuitoTurno.nameService);
            }
        };
        new Handler().postDelayed(new Runnable() {
            public void run() {
                r0.parserResponse(MockableDataManager.this.b(R.string.mocks_getCircuitoTurno, GetCircuitoTurnoResponseBean.class));
            }
        }, 500);
    }

    public void getSucursalesTurnoConfirmar(String str, String str2, UbicacionBean ubicacionBean) {
        getSucursalesTurno(str, str2, ubicacionBean);
    }

    public void getSucursalesTurnoSolicitud(String str, String str2, UbicacionBean ubicacionBean) {
        if (!mockEnabledServices.ap.booleanValue()) {
            super.getSucursalesTurno(str, str2, ubicacionBean);
            return;
        }
        final GetSucursalesSolicitudEvent getSucursalesSolicitudEvent = new GetSucursalesSolicitudEvent();
        new GetSucursalesRequestBean().headerBean = a(VGetSucursalesTurno.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        final AnonymousClass31 r4 = new GetSucursalesTurnoRequest(createErrorRequestServer(getSucursalesSolicitudEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetSucursalesResponseBean getSucursalesResponseBean = (GetSucursalesResponseBean) iBeanWS;
                getSucursalesSolicitudEvent.setResponseBean(getSucursalesResponseBean);
                getSucursalesSolicitudEvent.setBeanResponse(getSucursalesResponseBean);
                getSucursalesSolicitudEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(getSucursalesSolicitudEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, getSucursalesSolicitudEvent, VGetSucursalesTurno.nameService);
            }
        };
        new Handler().postDelayed(new Runnable() {
            public void run() {
                r4.parserResponse(MockableDataManager.this.b(R.string.mocks_getSucursalTurno, GetSucursalesResponseBean.class));
            }
        }, 500);
    }

    public void getSucursalesTurno(String str, String str2, UbicacionBean ubicacionBean) {
        if (!mockEnabledServices.ap.booleanValue()) {
            super.getSucursalesTurno(str, str2, ubicacionBean);
            return;
        }
        final GetSucursalesEvent getSucursalesEvent = new GetSucursalesEvent();
        new GetSucursalesRequestBean().headerBean = a(VGetSucursalesTurno.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        final AnonymousClass33 r4 = new GetSucursalesTurnoRequest(createErrorRequestServer(getSucursalesEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetSucursalesResponseBean getSucursalesResponseBean = (GetSucursalesResponseBean) iBeanWS;
                getSucursalesEvent.setResponseBean(getSucursalesResponseBean);
                getSucursalesEvent.setBeanResponse(getSucursalesResponseBean);
                getSucursalesEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(getSucursalesEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, getSucursalesEvent, VGetSucursalesTurno.nameService);
            }
        };
        new Handler().postDelayed(new Runnable() {
            public void run() {
                r4.parserResponse(MockableDataManager.this.b(R.string.mocks_getSucursalTurno, GetSucursalesResponseBean.class));
            }
        }, 500);
    }

    public void getDatosInicialesDolares(String str, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean) {
        CompraVentaDolaresCuentasBean compraVentaDolaresCuentasBean;
        String str2;
        CompraVentaDolaresCuentasBean compraVentaDolaresCuentasBean2;
        if (!mockEnabledServices.ah.booleanValue()) {
            super.getDatosInicialesDolares(str, compraVentaDolaresCuentaBean);
            return;
        }
        GetDatosInicialesDolaresEvent getDatosInicialesDolaresEvent = new GetDatosInicialesDolaresEvent();
        ArrayList arrayList = new ArrayList();
        CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2 = new CompraVentaDolaresCuentaBean("00", "111", "123456", "+1532.44", "N");
        arrayList.add(compraVentaDolaresCuentaBean2);
        CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean3 = new CompraVentaDolaresCuentaBean("00", "111", "234567", "+1532.44", "N");
        arrayList.add(compraVentaDolaresCuentaBean3);
        CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean4 = new CompraVentaDolaresCuentaBean("01", "111", "345678", "+1532.44", "N");
        arrayList.add(compraVentaDolaresCuentaBean4);
        ArrayList arrayList2 = new ArrayList();
        CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean5 = new CompraVentaDolaresCuentaBean("03", "111", "654321", "+1532.44", "N");
        arrayList2.add(compraVentaDolaresCuentaBean5);
        if (str.equals("1")) {
            compraVentaDolaresCuentasBean = new CompraVentaDolaresCuentasBean((List<CompraVentaDolaresCuentaBean>) arrayList);
            compraVentaDolaresCuentasBean2 = new CompraVentaDolaresCuentasBean((List<CompraVentaDolaresCuentaBean>) arrayList2);
            str2 = "15.43000";
        } else {
            compraVentaDolaresCuentasBean = new CompraVentaDolaresCuentasBean((List<CompraVentaDolaresCuentaBean>) arrayList2);
            CompraVentaDolaresCuentasBean compraVentaDolaresCuentasBean3 = new CompraVentaDolaresCuentasBean((List<CompraVentaDolaresCuentaBean>) arrayList);
            str2 = "15.02000";
            compraVentaDolaresCuentasBean2 = compraVentaDolaresCuentasBean3;
        }
        getDatosInicialesDolaresEvent.setResponse(new GetDatosInicialesDolaresResponseBean(new PrivateHeaderBean(), new GetDatosInicialesDolaresBodyResponseBean(compraVentaDolaresCuentasBean, compraVentaDolaresCuentasBean2, str2)));
        getDatosInicialesDolaresEvent.setResult(TypeResult.OK);
        getDatosInicialesDolaresEvent.setBeanResponse(getDatosInicialesDolaresEvent.getResponse());
        a((WebServiceEvent) getDatosInicialesDolaresEvent, 1500);
    }

    public void simulacionDolar(String str, String str2, String str3, String str4, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2) {
        if (!mockEnabledServices.au.booleanValue()) {
            super.simulacionDolar(str, str2, str3, str4, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
            return;
        }
        SimulacionDolarEvent simulacionDolarEvent = new SimulacionDolarEvent();
        SimulacionDolarResponseBean simulacionDolarResponseBean = (SimulacionDolarResponseBean) a((int) R.string.mocks_generic_result_0, SimulacionDolarResponseBean.class);
        simulacionDolarEvent.setResponse(simulacionDolarResponseBean);
        simulacionDolarEvent.setBeanResponse(simulacionDolarResponseBean);
        simulacionDolarEvent.setMessageToShow(simulacionDolarResponseBean.simulacionDolarBodyResponseBean.resDesc);
        simulacionDolarEvent.setTitleToShow(simulacionDolarResponseBean.simulacionDolarBodyResponseBean.resTitle);
        simulacionDolarEvent.setResult(TypeResult.OK);
        a((WebServiceEvent) simulacionDolarEvent, 1000);
    }

    public void compraVentaDolar(String str, String str2, String str3, String str4, String str5, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2) {
        CompraVentaDolarEvent compraVentaDolarEvent = new CompraVentaDolarEvent();
        PrivateHeaderBean privateHeaderBean = new PrivateHeaderBean();
        CompraVentaDolarBodyResponseBean compraVentaDolarBodyResponseBean = new CompraVentaDolarBodyResponseBean("20160412", "10:20", "34567864", "54327869", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi quis tincidunt neque, ac ultricies elit. Suspendisse tempor, augue ac feugiat placerat, sem tortor maximus erat, ac ultrices mauris sem vitae magna. Nulla molestie neque vel augue interdum ullamcorper. Aenean in nisi porttitor, suscipit nisl eget, tempor dolor. Morbi tincidunt libero in libero porta, eget vestibulum nisl gravida. In elementum lacinia leo, luctus suscipit turpis faucibus vitae. Donec mollis pharetra mollis. Nulla orci dolor, tristique at sollicitudin eget, fringilla et lacus. Etiam scelerisque tincidunt velit, quis convallis neque bibendum eu. Mauris pretium, tellus ac ultricies sollicitudin, magna est pulvinar justo, nec accumsan massa quam quis urna. In hac habitasse platea dictumst. Fusce id mi ante. Etiam id nibh a risus dictum tempus.", "CONSERVE ESTE TICKET COMO COMPROBANTE S.E.U.O");
        compraVentaDolarEvent.setResponse(new CompraVentaDolarResponseBean(privateHeaderBean, compraVentaDolarBodyResponseBean));
        compraVentaDolarEvent.setResult(TypeResult.OK);
        compraVentaDolarEvent.setBeanResponse(compraVentaDolarEvent.getResponse());
        a((WebServiceEvent) compraVentaDolarEvent, 1500);
    }

    public void consultaDatosInicialesTransf(DatosIniciales datosIniciales) {
        if (!mockEnabledServices.w.booleanValue()) {
            super.consultaDatosInicialesTransf(datosIniciales);
            return;
        }
        final ConsultaDatosInicialesTransfEvent consultaDatosInicialesTransfEvent = new ConsultaDatosInicialesTransfEvent();
        new ConsultaDatosInicialesTransfRequest(super.createErrorRequestServer(consultaDatosInicialesTransfEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaDatosInicialesTransfEvent.setBeanResponse((ConsultaDatosInicialesTransfResponseBean) iBeanWS);
                consultaDatosInicialesTransfEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) consultaDatosInicialesTransfEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, consultaDatosInicialesTransfEvent, VConsultaDatosInicialesTransf.nameService);
            }
        }.parserResponse(b(R.string.mocks_consultaDatosInicialesTransf_new_mocks, ConsultaDatosInicialesTransfResponseBean.class));
    }

    public void verificaDatosInicialesTransfOB(VerificaDatosOBBean verificaDatosOBBean) {
        if (!mockEnabledServices.x.booleanValue()) {
            super.verificaDatosInicialesTransf(verificaDatosOBBean);
            return;
        }
        VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent = new VerificaDatosInicialesTransfOBEvent();
        verificaDatosInicialesTransfOBEvent.setResult(TypeResult.OK);
        if (verificaDatosOBBean.getTipoDestino().equals("02") || verificaDatosOBBean.getTipoDestino().equals("04")) {
            verificaDatosInicialesTransfOBEvent.setResponseOB((VerificaDatosInicialesTransfOBResponseBean) a((int) R.string.mocks_verificaDatosInicialesTransf_TipoDestino04, VerificaDatosInicialesTransfOBResponseBean.class));
        } else if (verificaDatosOBBean.getTipoDestino().equals("03") || verificaDatosOBBean.getTipoDestino().equals("05")) {
            verificaDatosInicialesTransfOBEvent.setResponseOB((VerificaDatosInicialesTransfOBResponseBean) a((int) R.string.mocks_verificaDatosInicialesTransf_TipoDestino05, VerificaDatosInicialesTransfOBResponseBean.class));
        }
        verificaDatosInicialesTransfOBEvent.setBeanResponse(verificaDatosInicialesTransfOBEvent.getResponseOB());
        a((WebServiceEvent) verificaDatosInicialesTransfOBEvent, 1000);
    }

    public void verificaDatosInicialesTransf(VerificaDatosBean verificaDatosBean) {
        if (!mockEnabledServices.y.booleanValue()) {
            super.verificaDatosInicialesTransf(verificaDatosBean);
            return;
        }
        VerificaDatosInicialesTransfEvent verificaDatosInicialesTransfEvent = new VerificaDatosInicialesTransfEvent();
        verificaDatosInicialesTransfEvent.setResult(TypeResult.OK);
        verificaDatosInicialesTransfEvent.setBeanResponse((VerificaDatosInicialesTransfOBResponseBean) a((int) R.string.mocks_verificaDatosInicialesTransf_TipoDestino04, VerificaDatosInicialesTransfOBResponseBean.class));
        a((WebServiceEvent) verificaDatosInicialesTransfEvent, 1000);
    }

    public void abmDestinatarioTransf(ABMDestinatarioTransfBodyRequestBean aBMDestinatarioTransfBodyRequestBean, Activity activity, Boolean bool) {
        if (!mockEnabledServices.z.booleanValue()) {
            super.abmDestinatarioTransf(aBMDestinatarioTransfBodyRequestBean, activity, bool);
            return;
        }
        final ABMDestinatarioTransfEvent aBMDestinatarioTransfEvent = new ABMDestinatarioTransfEvent();
        new ABMDestinatarioTransfRequest(super.createErrorRequestServer(aBMDestinatarioTransfEvent, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                ABMDestinatarioTransfResponseBean aBMDestinatarioTransfResponseBean = (ABMDestinatarioTransfResponseBean) iBeanWS;
                aBMDestinatarioTransfEvent.setResponse(aBMDestinatarioTransfResponseBean);
                aBMDestinatarioTransfEvent.setBeanResponse(aBMDestinatarioTransfResponseBean);
                aBMDestinatarioTransfEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) aBMDestinatarioTransfEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, aBMDestinatarioTransfEvent, VABMDestinatarioTransf.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_abmDestinatarioTransf, ABMDestinatarioTransfResponseBean.class));
    }

    public void solicitudLimiteTransf(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        if (!mockEnabledServices.aC.booleanValue()) {
            super.solicitudLimiteTransf(str, str2, str3, str4, str5, str6, str7, str8, str9, str10);
            return;
        }
        final SolicitudLimiteTransfEvent solicitudLimiteTransfEvent = new SolicitudLimiteTransfEvent();
        new SolicitudLimiteTransfRequest(super.createErrorRequestServer(solicitudLimiteTransfEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                SolicitudLimiteTransfResponseBean solicitudLimiteTransfResponseBean = (SolicitudLimiteTransfResponseBean) iBeanWS;
                solicitudLimiteTransfEvent.setResponse(solicitudLimiteTransfResponseBean);
                solicitudLimiteTransfEvent.setBeanResponse(solicitudLimiteTransfResponseBean);
                solicitudLimiteTransfEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) solicitudLimiteTransfEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, solicitudLimiteTransfEvent, VSolicitudLimiteTransf.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_solicitudLimiteTransf, SolicitudLimiteTransfResponseBean.class));
    }

    public void transferencias(DatosTransferenciaBean datosTransferenciaBean, DatosCuentaDebitoBean datosCuentaDebitoBean, DatosCuentasDestinoBSRBean datosCuentasDestinoBSRBean, Activity activity) {
        if (!mockEnabledServices.aC.booleanValue()) {
            super.transferencias(datosTransferenciaBean, datosCuentaDebitoBean, datosCuentasDestinoBSRBean, activity);
            return;
        }
        TransferenciasEvent transferenciasEvent = new TransferenciasEvent();
        String session = this.sessionManager.getSession();
        String nup = this.sessionManager.getNup();
        Context applicationContext = this.e.getApplicationContext();
        TransferenciasRequestBean transferenciasRequestBean = new TransferenciasRequestBean(a(VTransferencias.getData(EVersionServices.CURRENT), session, nup, Boolean.valueOf(true), Boolean.valueOf(true), activity), new TransferenciasBodyRequestBean(datosTransferenciaBean, datosCuentaDebitoBean, datosCuentasDestinoBSRBean));
        final TransferenciasEvent transferenciasEvent2 = transferenciasEvent;
        AnonymousClass38 r0 = new TransferenciasRequest(applicationContext, transferenciasRequestBean, createErrorRequestServer(transferenciasEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                transferenciasEvent2.setBeanResponse((TransferenciasResponseBean) iBeanWS);
                transferenciasEvent2.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(transferenciasEvent2);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, transferenciasEvent2, VTransferencias.nameService);
            }
        };
        r0.parserResponse(b(R.string.mocks_verificaDatosInicialesTransf_TipoDestino01, TransferenciasResponseBean.class));
    }

    public void getPuntosSuperClub() {
        if (!mockEnabledServices.K.booleanValue()) {
            super.getPuntosSuperClub();
            return;
        }
        GetPuntosSuperClubEvent getPuntosSuperClubEvent = new GetPuntosSuperClubEvent();
        getPuntosSuperClubEvent.setResult(TypeResult.OK);
        getPuntosSuperClubEvent.setResponse(new GetPuntosSuperClubResponseBean(new PrivateHeaderBean(), new GetPuntosSuperClubBodyResponseBean("2400")));
        getPuntosSuperClubEvent.setBeanResponse(getPuntosSuperClubEvent.getResponse());
        a((WebServiceEvent) getPuntosSuperClubEvent, 1000);
    }

    public void getCategoriasSuperClub() {
        if (!mockEnabledServices.L.booleanValue()) {
            super.getCategoriasSuperClub();
            return;
        }
        GetCategoriasSuperClubEvent getCategoriasSuperClubEvent = new GetCategoriasSuperClubEvent();
        getCategoriasSuperClubEvent.setResult(TypeResult.OK);
        ArrayList arrayList = new ArrayList();
        CategoriaSuperClubBean categoriaSuperClubBean = new CategoriaSuperClubBean("201", "101", "Kevingston Ni&ntilde;os", "https://www.dropbox.com/s/isxao2f4n6cwty7/img_subrubro.png?raw=1", "MARCA", "1-1DC0D", new CategoriasSuperClubBean());
        arrayList.add(categoriaSuperClubBean);
        CategoriaSuperClubBean categoriaSuperClubBean2 = new CategoriaSuperClubBean("101", null, "Indumentaria", "https://www.dropbox.com/s/obipym1inj97byq/img_rubro.png?raw=1", null, "1-1DC0D", new CategoriasSuperClubBean((List<CategoriaSuperClubBean>) arrayList));
        CategoriaSuperClubBean categoriaSuperClubBean3 = new CategoriaSuperClubBean("102", null, "Combustibles", "https://www.dropbox.com/s/obipym1inj97byq/img_rubro.png?raw=1", null, "1-1DC0D", new CategoriasSuperClubBean((List<CategoriaSuperClubBean>) new ArrayList<CategoriaSuperClubBean>()));
        CategoriaSuperClubBean categoriaSuperClubBean4 = new CategoriaSuperClubBean("103", null, "Peluquerías", "https://www.dropbox.com/s/obipym1inj97byq/img_rubro.png?raw=1", null, "1-1DC0D", new CategoriasSuperClubBean((List<CategoriaSuperClubBean>) new ArrayList<CategoriaSuperClubBean>()));
        CategoriaSuperClubBean categoriaSuperClubBean5 = new CategoriaSuperClubBean("104", null, "Restaurantes", "https://www.dropbox.com/s/obipym1inj97byq/img_rubro.png?raw=1", null, "1-1DC0D", new CategoriasSuperClubBean((List<CategoriaSuperClubBean>) new ArrayList<CategoriaSuperClubBean>()));
        CategoriaSuperClubBean categoriaSuperClubBean6 = new CategoriaSuperClubBean("105", null, "Supermercados", "https://www.dropbox.com/s/obipym1inj97byq/img_rubro.png?raw=1", null, "1-1DC0D", new CategoriasSuperClubBean((List<CategoriaSuperClubBean>) new ArrayList<CategoriaSuperClubBean>()));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(categoriaSuperClubBean2);
        arrayList2.add(categoriaSuperClubBean3);
        arrayList2.add(categoriaSuperClubBean4);
        arrayList2.add(categoriaSuperClubBean5);
        arrayList2.add(categoriaSuperClubBean6);
        getCategoriasSuperClubEvent.setResponse(new GetCategoriasSuperClubResponseBean(new PrivateHeaderBean(), new GetCategoriasSuperClubBodyResponseBean(new CategoriasSuperClubBean((List<CategoriaSuperClubBean>) arrayList2))));
        getCategoriasSuperClubEvent.setBeanResponse(getCategoriasSuperClubEvent.getResponse());
        a((WebServiceEvent) getCategoriasSuperClubEvent, 1000);
    }

    public void getCuponesCategorySuperClub(String str, String str2, String str3) {
        if (!mockEnabledServices.M.booleanValue()) {
            super.getCuponesCategorySuperClub(str, str2, str3);
            return;
        }
        GetCuponesCategorySuperClubEvent getCuponesCategorySuperClubEvent = new GetCuponesCategorySuperClubEvent();
        getCuponesCategorySuperClubEvent.setResult(TypeResult.OK);
        ArrayList arrayList = new ArrayList();
        CuponSuperClubBean cuponSuperClubBean = r2;
        CuponSuperClubBean cuponSuperClubBean2 = new CuponSuperClubBean("1-AJD-111", null, "Internacional", "1000", "1-7G26", "1-5DZ5", null, null, "https://www.dropbox.com/s/bhm5hdilt31usjr/mb_TD10_1.jpg?raw=1", null, "10% AHORRO COMBUSTIBLE DEBITO", null, null, "Valido del 1/5/2015 al 31/03/2016 o hasta agotar stock 100 u., lo que ocurra primero. Valido cualquier día de la semana, acumulable con cualquier promoción vigente del Banco, para compras realizadas con Tarjeta Santander Rio Debito en comercios de Argentina registrados ante Visa Argentina S.A bajo el rubro Combustible-Estaciones de Servicio. Previamente consulte el rubro del comercio. El reintegro aplicara exclusivamente sobre la primera transacción que realice en el comercio seleccionado y únicamente con la Tarjeta Santander Rio Debito del titular que haya efectuado el canje. El ahorro será efectuado dentro de los 15 días hábiles posteriores a la compra. Tiempo para la utilización del comodín 30 días desde de la fecha del canje, luego de este plazo los comodines perderán valor, no podrán ser reactivados ni reemplazados, ni se reintegraran los puntos SuperClub canjeados. Valido solo un comodín por transacción. Comodines no acumulables entre sí.");
        arrayList.add(cuponSuperClubBean);
        CuponSuperClubBean cuponSuperClubBean3 = new CuponSuperClubBean("1-AJD-78", null, "Internacional", "1700", "1-7G26", "1-5DZ5", null, null, "https://www.dropbox.com/s/6wsbdcov97rihqj/mb_TD20_1.jpg?raw=1", null, "20% AHORRO COMBUSTIBLE DEBITO", null, null, "Valido del 1/5/2015 al 31/03/2016 o hasta agotar stock 100 u., lo que ocurra primero. Valido cualquier día de la semana, acumulable con cualquier promoción vigente del Banco, para compras realizadas con Tarjeta Santander Rio Debito en comercios de Argentina registrados ante Visa Argentina S.A bajo el rubro Combustible-Estaciones de Servicio. Previamente consulte el rubro del comercio. El reintegro aplicara exclusivamente sobre la primera transacción que realice en el comercio seleccionado y únicamente con la Tarjeta Santander Rio Debito del titular que haya efectuado el canje. El ahorro será efectuado dentro de los 15 días hábiles posteriores a la compra. Tiempo para la utilización del comodín 30 días desde de la fecha del canje, luego de este plazo los comodines perderán valor, no podrán ser reactivados ni reemplazados, ni se reintegraran los puntos SuperClub canjeados. Valido solo un comodín por transacción. Comodines no acumulables entre sí.");
        arrayList.add(cuponSuperClubBean3);
        getCuponesCategorySuperClubEvent.setResponse(new GetCuponesSuperClubResponseBean(new PrivateHeaderBean(), new GetCuponesSuperClubBodyResponseBean(new CuponesSuperClubBean(arrayList), null)));
        getCuponesCategorySuperClubEvent.setBeanResponse(getCuponesCategorySuperClubEvent.getResponse());
        a((WebServiceEvent) getCuponesCategorySuperClubEvent, 1000);
    }

    public void getCuponesSubcategorySuperClub(String str, String str2, String str3) {
        if (!mockEnabledServices.N.booleanValue()) {
            super.getCuponesSubcategorySuperClub(str, str2, str3);
            return;
        }
        try {
            new GetCuponesSuperClubRequest(this.e.getApplicationContext(), new GetCuponesSuperClubRequestBean(a(VGetCuponesSuperClub.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup()), new GetCuponesSuperClubBodyRequestBean(str, str2, str3)), new ErrorRequestServer() {
                public void onErrorResp1(ErrorBodyBean errorBodyBean) {
                }

                public void onErrorResp2(ErrorBodyBean errorBodyBean) {
                }

                public void onErrorResp3(ErrorBodyBean errorBodyBean) {
                }

                public void onErrorResp4(ErrorBodyBean errorBodyBean) {
                }

                public void onErrorResp5(ErrorBodyBean errorBodyBean) {
                }

                public void onErrorResp6(ErrorBodyBean errorBodyBean) {
                }

                public void onErrorResp7(ErrorBodyBean errorBodyBean) {
                }

                public void onErrorResp8(ErrorBodyBean errorBodyBean) {
                }

                public void onErrorResp9(ErrorBodyBean errorBodyBean) {
                }

                public void onErrorServer(VolleyError volleyError) {
                }

                public void onWarningBean(ErrorBodyBean errorBodyBean) {
                }
            }) {
                public void onUnknowError(Exception exc) {
                }

                public void onResponseBean(IBeanWS iBeanWS) {
                    GetCuponesSuperClubResponseBean getCuponesSuperClubResponseBean = (GetCuponesSuperClubResponseBean) iBeanWS;
                    GetCuponesSubcategorySuperClubEvent getCuponesSubcategorySuperClubEvent = new GetCuponesSubcategorySuperClubEvent();
                    getCuponesSubcategorySuperClubEvent.setResult(TypeResult.OK);
                    getCuponesSubcategorySuperClubEvent.setResponse(getCuponesSuperClubResponseBean);
                    getCuponesSubcategorySuperClubEvent.setBeanResponse(getCuponesSubcategorySuperClubEvent.getResponse());
                    MockableDataManager.this.a((WebServiceEvent) getCuponesSubcategorySuperClubEvent, 1000);
                }
            }.parserResponse(UtilBeanXml.convertStringXmlToJsonObject("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Header/><soapenv:Body><xml><header><idAplicacion>MBI</idAplicacion><servicio><nombre>getCuponesSuperClub</nombre><version>1.0</version></servicio></header><body><res>0</res><cupones><cupon><idCupon>1-O82X</idCupon><grupo></grupo><segmento>Internacional</segmento><puntos>1300</puntos><idPunto>1-7G26</idPunto><idPartner>1-5DZ5</idPartner><medioDePago></medioDePago><porcentajeDeDescuento></porcentajeDeDescuento><imagenDescuentoMedioDePago>http://srv12z3.ar.bsch:9087/MBI/verImagen.jsp?nombre=mb_VID047_1.jpg</imagenDescuentoMedioDePago><topeDeAhorro></topeDeAhorro><descripcion>20% [^] AHORRO THE NORTH FACE DEBITO</descripcion><vigenciaDesde></vigenciaDesde><vigenciaHasta></vigenciaHasta><legales>Valido del 01/04/2016 al 31/12/2016 o hasta agotar stock 100 u., lo que ocurra primero. Valido cualquier día de la semana, acumulable con cualquier promoción vigente del Banco. El reintegro aplicara exclusivamente sobre la primera transacción que realice en el comercio seleccionado y únicamente con la Tarjeta Santander Rio Debito del titular que haya efectuado el canje. El ahorro será efectuado dentro de los 15 días hábiles posteriores a la compra. Tiempo para la utilización del comodín 30 días desde de la fecha del canje, luego de este plazo los comodines perderán valor, no podrán ser reactivados ni reemplazados, ni se reintegraran los puntos SuperClub canjeados. Valido solo un comodín por transacción. Comodines no acumulables entre sí. Ver Locales Adheridos: http://www.tarjetasantanderrio.com.ar/online/promociones/comodines/the-north-face-comodines</legales><stock>159</stock></cupon><cupon><idCupon>1-2ZE-143</idCupon><grupo></grupo><segmento>Internacional</segmento><puntos>1300</puntos><idPunto>1-7G26</idPunto><idPartner>1-5DZ5</idPartner><medioDePago></medioDePago><porcentajeDeDescuento></porcentajeDeDescuento><imagenDescuentoMedioDePago>http://srv12z3.ar.bsch:9087/MBI/verImagen.jsp?nombre=mb_VIV045_1.jpg</imagenDescuentoMedioDePago><topeDeAhorro></topeDeAhorro><descripcion>20% [^] AHORRO THE NORTH FACE VISA</descripcion><vigenciaDesde></vigenciaDesde><vigenciaHasta></vigenciaHasta><legales>Válido para cualquier día de la semana. Acumulable con cualquier promoción vigente del Banco. Podrá aplicarse solo un comodín por transacción. Los comodines no son acumulables entre sí. Reintegro máximo por transacción: $400. Stock: 100 unidades. Vigencia desde el 01/04/2016 al 31/12/2016 hasta agotar el stock indicado, lo que ocurra primero. El reintegro aplicará sobre la primera transacción que realice en el comercio seleccionado para la tarjeta seleccionada. Efectivización del ahorro vía reintegro en la liquidación siguiente a la realización del consumo. Tiempo válido para la utilización del ahorro: 30 días desde la fecha de canje, luego de este plazo los comodines perderán valor y no podrán ser reactivados ni reemplazados, así como tampoco se reintegrar n los puntos SuperClub utilizados para el canje. Ver Locales Adheridos: http://www.tarjetasantanderrio.com.ar/exec/catalogo/ahorros_sc.jsp [^]</legales><stock>154</stock></cupon><cupon><idCupon>1-O82K</idCupon><grupo></grupo><segmento>Internacional</segmento><puntos>650</puntos><idPunto>1-7G26</idPunto><idPartner>1-5DZ5</idPartner><medioDePago></medioDePago><porcentajeDeDescuento></porcentajeDeDescuento><imagenDescuentoMedioDePago>http://srv12z3.ar.bsch:9087/MBI/verImagen.jsp?nombre=mb_VID046_1.jpg</imagenDescuentoMedioDePago><topeDeAhorro></topeDeAhorro><descripcion>10% [^] AHORRO THE NORTH FACE DEBITO</descripcion><vigenciaDesde></vigenciaDesde><vigenciaHasta></vigenciaHasta><legales>Valido del 01/04/2016 al 31/12/2016 o hasta agotar stock 100 u., lo que ocurra primero. Valido cualquier día de la semana, acumulable con cualquier promoción vigente del Banco. El reintegro aplicara exclusivamente sobre la primera transacción que realice en el comercio seleccionado y únicamente con la Tarjeta Santander Rio Debito del titular que haya efectuado el canje. El ahorro será efectuado dentro de los 15 días hábiles posteriores a la compra. Tiempo para la utilización del comodín 30 días desde de la fecha del canje, luego de este plazo los comodines perderán valor, no podrán ser reactivados ni reemplazados, ni se reintegraran los puntos SuperClub canjeados. Valido solo un comodín por transacción. Comodines no acumulables entre sí. Ver Locales Adheridos: http://www.tarjetasantanderrio.com.ar/online/promociones/comodines/the-north-face-comodines</legales><stock>159</stock></cupon><cupon><idCupon>1-2ZE-141</idCupon><grupo></grupo><segmento>Internacional</segmento><puntos>650</puntos><idPunto>1-7G26</idPunto><idPartner>1-5DZ5</idPartner><medioDePago></medioDePago><porcentajeDeDescuento></porcentajeDeDescuento><imagenDescuentoMedioDePago>http://srv12z3.ar.bsch:9087/MBI/verImagen.jsp?nombre=mb_VIV044_1.jpg</imagenDescuentoMedioDePago><topeDeAhorro></topeDeAhorro><descripcion>10% [^] AHORRO THE NORTH FACE VISA</descripcion><vigenciaDesde></vigenciaDesde><vigenciaHasta></vigenciaHasta><legales>Válido para cualquier día de la semana. Acumulable con cualquier promoción vigente del Banco. Podrá aplicarse solo un comodín por transacción. Los comodines no son acumulables entre sí. Reintegro máximo por transacción: $200. Stock: 100 unidades. Vigencia desde el 01/04/2016 al 31/12/2016 hasta agotar el stock indicado, lo que ocurra primero. El reintegro aplicará sobre la primera transacción que realice en el comercio seleccionado para la tarjeta seleccionada. Efectivización del ahorro vía reintegro en la liquidación siguiente a la realización del consumo. Tiempo válido para la utilización del ahorro: 30 días desde la fecha de canje, luego de este plazo los comodines perderán valor y no podrán ser reactivados ni reemplazados, así como tampoco se reintegrar n los puntos SuperClub utilizados para el canje. Ver Locales Adheridos: http://www.tarjetasantanderrio.com.ar/exec/catalogo/ahorros_sc.jsp [^]</legales><stock>160</stock></cupon></cupones><localesAdheridos><marca>The North Face</marca><zonas><zona><nombreZona>Capital Federal</nombreZona><locales><local><shopping/><direccion>Aguirre 761</direccion><localNumero/><localidad/><provincia/></local><local><shopping>Alto Palermo</shopping><direccion>Av. Santa Fé 3253</direccion><localNumero/><localidad/><provincia/></local><local><shopping>Dot Baires Shopping</shopping><direccion>Vedia 3626</direccion><localNumero/><localidad/><provincia/></local></locales></zona><zona><nombreZona>Buenos Aires</nombreZona><locales><local><shopping>Unicenter Shopping</shopping><direccion>Paraná 3745</direccion><localNumero/><localidad>Martínez</localidad><provincia/></local></locales></zona></zonas></localesAdheridos></body></xml></soapenv:Body></soapenv:Envelope>", null));
        } catch (Exception unused) {
            Log.w("LMB", "Fallo al parsear XML");
        }
    }

    public void canjearPuntosSuperClub(String str, String str2, String str3, String str4, String str5, String str6) {
        if (!mockEnabledServices.O.booleanValue()) {
            super.canjearPuntosSuperClub(str, str2, str3, str4, str5, str6);
            return;
        }
        CanjearPuntosSuperClubEvent canjearPuntosSuperClubEvent = new CanjearPuntosSuperClubEvent();
        canjearPuntosSuperClubEvent.setResult(TypeResult.OK);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new LeyendaSuperClubBean("CUPON_COMP1", "Comprobante de Canje Comodín SuperClub", "Tenes hasta 30 días a partir de hoy para usar este comodín.#Abr#C#Abr#CEl reintegro de este beneficio se realizará entre los 7 y 15 días habiles posteriores a realizada esta compra."));
        arrayList.add(new LeyendaSuperClubBean("CUPON_COMP2", "Comprobante de Canje Comodín SuperClub", "Podes consultar este comprobante ingresando al módulo SuperClub en Online Banking Personas."));
        canjearPuntosSuperClubEvent.setResponse(new CanjearPuntosSuperClubResponseBean(new PrivateHeaderBean(), new CanjearPuntosSuperClubBodyResponseBean("09142523", "04/07/2016 14:01:32", new ListaLeyendasSuperClubBean(arrayList))));
        canjearPuntosSuperClubEvent.setBeanResponse(canjearPuntosSuperClubEvent.getResponse());
        a((WebServiceEvent) canjearPuntosSuperClubEvent, 1000);
    }

    public void cnsTenencias(String str) {
        if (!mockEnabledServices.A.booleanValue()) {
            super.cnsTenencias(this.sessionManager.getLoginUnico().getDatosPersonales().getTipoCliente());
            return;
        }
        CnsTenenciasEvent cnsTenenciasEvent = new CnsTenenciasEvent();
        cnsTenenciasEvent.setResponse(new CnsTenenciasResponseBean(new PrivateHeaderBean(), new CnsTenenciasBodyResponseBean(new CnsTenenciasListaDatosPFBean(new ArrayList<CnsTenenciasDatosPFBean>() {
            {
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean = r1;
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean2 = new CnsTenenciasDatosPFBean("ARS", "2016-11-25", "12.55 %", "$ 100.25", "325", "$ 120.25", "Plazo fijo Tradicional en Pesos", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0342", "mobile", "Renovación capital", "Tradicional en Pesos", "325", "1", "123456789");
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean3 = cnsTenenciasDatosPFBean;
                add(cnsTenenciasDatosPFBean3);
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean4 = new CnsTenenciasDatosPFBean("UVI", "2016-11-25", "12.55 %", "$ 100.25", "325", "$ 120.25", "Plazo fijo Tradicional en Unidades de Vivienda", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0060", "Online Banking", "Renovación capital", "Tradicional en Unidades de Vivienda", "325", "1", "123456789");
                add(cnsTenenciasDatosPFBean4);
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean5 = new CnsTenenciasDatosPFBean("ARS", "2016-11-25", "12.55 %", "$ 100.25", "987", "$ 120.25", "Plazo fijo Tradicional en Pesos", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0062", "mobile", "Renovación capital", "Tradicional en Pesos", "325", "0", "123456789");
                add(cnsTenenciasDatosPFBean5);
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean6 = new CnsTenenciasDatosPFBean("ARS", "2016-11-25", "12.55 %", "$ 100.25", "325", "$ 120.25", "Plazo fijo Tradicional en Pesos", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0342", "mobile", "Renovación capital", "Tradicional en Pesos", "325", "0", "123456789");
                add(cnsTenenciasDatosPFBean6);
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean7 = new CnsTenenciasDatosPFBean("ARS", "2016-11-25", "12.55 %", "$ 100.25", "319", "$ 120.25", "Plazo fijo Tradicional en Pesos", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0998", "mobile", "Renovación capital", "Tradicional en Pesos", "325", "0", "123456789");
                add(cnsTenenciasDatosPFBean7);
            }
        }), new CnsTenenciasListaLeyendasPFBean(new ArrayList<CnsTenenciasLeyendaPFBean>() {
            {
                add(new CnsTenenciasLeyendaPFBean("ID123", "Titulo1", "0060", "* Sobre capital sin ajuste. Al vencimiento del plazo fijo se recalcula el capital original al valor de la UVI a esa fecha.\\nLos intereses se calculan sobre dicho capital."));
                add(new CnsTenenciasLeyendaPFBean("ID787", "Titulo2", "0998", "* Leyenda suproducto 0998"));
            }
        }))));
        new CnsTenenciasResponseBean(new PrivateHeaderBean(), new CnsTenenciasBodyResponseBean(new CnsTenenciasListaDatosPFBean(new ArrayList<CnsTenenciasDatosPFBean>() {
            {
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean = r1;
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean2 = new CnsTenenciasDatosPFBean("ARS", "2016-11-25", "12.55 %", "$ 100.25", "325", "$ 120.25", "Plazo fijo Tradicional en Pesos", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0342", "mobile", "Renovación capital", "Tradicional en Pesos", "325", "1", "123456789");
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean3 = cnsTenenciasDatosPFBean;
                add(cnsTenenciasDatosPFBean3);
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean4 = new CnsTenenciasDatosPFBean("UVI", "2016-11-25", "12.55 %", "$ 100.25", "325", "$ 120.25", "Plazo fijo Tradicional en Unidades de Vivienda", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0060", "Online Banking", "Renovación capital", "Tradicional en Unidades de Vivienda", "325", "1", "123456789");
                add(cnsTenenciasDatosPFBean4);
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean5 = new CnsTenenciasDatosPFBean("ARS", "2016-11-25", "12.55 %", "$ 100.25", "987", "$ 120.25", "Plazo fijo Tradicional en Pesos", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0062", "mobile", "Renovación capital", "Tradicional en Pesos", "325", "0", "123456789");
                add(cnsTenenciasDatosPFBean5);
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean6 = new CnsTenenciasDatosPFBean("ARS", "2016-11-25", "12.55 %", "$ 100.25", "325", "$ 120.25", "Plazo fijo Tradicional en Pesos", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0342", "mobile", "Renovación capital", "Tradicional en Pesos", "325", "0", "123456789");
                add(cnsTenenciasDatosPFBean6);
                CnsTenenciasDatosPFBean cnsTenenciasDatosPFBean7 = new CnsTenenciasDatosPFBean("ARS", "2016-11-25", "12.55 %", "$ 100.25", "319", "$ 120.25", "Plazo fijo Tradicional en Pesos", "$ 20", "$ 1.43", "2018-02-25", "9000003698", "1234", "0998", "mobile", "Renovación capital", "Tradicional en Pesos", "325", "0", "123456789");
                add(cnsTenenciasDatosPFBean7);
            }
        }), new CnsTenenciasListaLeyendasPFBean(new ArrayList<CnsTenenciasLeyendaPFBean>() {
            {
                add(new CnsTenenciasLeyendaPFBean("ID123", "Titulo1", "0060", "* Sobre capital sin ajuste. Al vencimiento del plazo fijo se recalcula el capital original al valor de la UVI a esa fecha.\\nLos intereses se calculan sobre dicho capital."));
                add(new CnsTenenciasLeyendaPFBean("ID787", "Titulo2", "0998", "* Leyenda suproducto 0998"));
            }
        })));
        cnsTenenciasEvent.setResult(TypeResult.OK);
        cnsTenenciasEvent.setBeanResponse(cnsTenenciasEvent.getResponse());
        a((WebServiceEvent) cnsTenenciasEvent, 1000);
    }

    public void cnsTasasPFT() {
        if (!mockEnabledServices.B.booleanValue()) {
            super.cnsTasasPFT();
            return;
        }
        CnsTasasPFTEvent cnsTasasPFTEvent = new CnsTasasPFTEvent();
        cnsTasasPFTEvent.setResponse(new CnsTasasPFTResponseBean(new PrivateHeaderBean(), new CnsTasasPFTBodyResponseBean(new CnsTasasPFTListaPlazoFijoBean(new ArrayList<CnsTasasPFTPlazoFijoBean>() {
            {
                CnsTasasPFTPlazoFijoBean cnsTasasPFTPlazoFijoBean = new CnsTasasPFTPlazoFijoBean("03", "TRADICIONAL", "ARS", "00000000050000", new CnsTasasPFTListaTasasBean(new ArrayList<CnsTasasPFTTasaBean>() {
                    {
                        add(new CnsTasasPFTTasaBean("00030", "01940000"));
                        add(new CnsTasasPFTTasaBean("00060", "01950000"));
                        add(new CnsTasasPFTTasaBean("00090", "01960000"));
                    }
                }));
                add(cnsTasasPFTPlazoFijoBean);
                CnsTasasPFTPlazoFijoBean cnsTasasPFTPlazoFijoBean2 = new CnsTasasPFTPlazoFijoBean("03", "TRADICIONAL", "USD", "00000000050000", new CnsTasasPFTListaTasasBean(new ArrayList<CnsTasasPFTTasaBean>() {
                    {
                        add(new CnsTasasPFTTasaBean("00030", "00245000"));
                        add(new CnsTasasPFTTasaBean("00060", "00246000"));
                        add(new CnsTasasPFTTasaBean("00090", "00247000"));
                    }
                }));
                add(cnsTasasPFTPlazoFijoBean2);
            }
        }))));
        cnsTasasPFTEvent.setResult(TypeResult.OK);
        cnsTasasPFTEvent.setBeanResponse(cnsTasasPFTEvent.getResponse());
        a((WebServiceEvent) cnsTasasPFTEvent, 1000);
    }

    public void generaMandato(GeneraMandatoExtBodyRequestBean generaMandatoExtBodyRequestBean, Activity activity) {
        if (!mockEnabledServices.C.booleanValue()) {
            super.generaMandato(generaMandatoExtBodyRequestBean, activity);
            return;
        }
        GeneraMandatoExtEnvEvent generaMandatoExtEnvEvent = new GeneraMandatoExtEnvEvent();
        generaMandatoExtEnvEvent.setResult(TypeResult.OK);
        generaMandatoExtEnvEvent.setResponse(new GeneraMandatoExtEnvResponseBean(new PrivateHeaderBean(), new GeneraMandatoExtEnvBodyResponseBean(new GeneraMandatoExtEnvComprobanteBean("1234", "5678", new SimpleDateFormat(Constants.FORMAT_DATE_TIME_COMPROBANTE).format(this.g), "12345678"))));
        generaMandatoExtEnvEvent.setBeanResponse(generaMandatoExtEnvEvent.getResponse());
        a((WebServiceEvent) generaMandatoExtEnvEvent, 750);
    }

    public void generaMandato(GeneraMandatoEnvBodyRequestBean generaMandatoEnvBodyRequestBean, Activity activity) {
        if (!mockEnabledServices.D.booleanValue()) {
            super.generaMandato(generaMandatoEnvBodyRequestBean, activity);
            return;
        }
        GeneraMandatoExtEnvEvent generaMandatoExtEnvEvent = new GeneraMandatoExtEnvEvent();
        generaMandatoExtEnvEvent.setResult(TypeResult.OK);
        generaMandatoExtEnvEvent.setResponse(new GeneraMandatoExtEnvResponseBean(new PrivateHeaderBean(), new GeneraMandatoExtEnvBodyResponseBean(new GeneraMandatoExtEnvComprobanteBean("1234", "5678", new SimpleDateFormat(Constants.FORMAT_DATE_TIME_COMPROBANTE).format(this.g), "12345678"))));
        generaMandatoExtEnvEvent.setBeanResponse(generaMandatoExtEnvEvent.getResponse());
        a((WebServiceEvent) generaMandatoExtEnvEvent, 750);
    }

    public void cancelaMandato(CancelaMandatoExtEnvBodyRequestBean cancelaMandatoExtEnvBodyRequestBean) {
        if (!mockEnabledServices.E.booleanValue()) {
            super.cancelaMandato(cancelaMandatoExtEnvBodyRequestBean);
            return;
        }
        CancelaMandatoExtEnvEvent cancelaMandatoExtEnvEvent = new CancelaMandatoExtEnvEvent();
        cancelaMandatoExtEnvEvent.setResult(TypeResult.OK);
        cancelaMandatoExtEnvEvent.setResponse(new CancelaMandatoExtEnvResponseBean(new PrivateHeaderBean(), new CancelaMandatoExtEnvBodyResponseBean(new ComprobanteCancelacionMandatoBean("12/10/2015", "214012102015"))));
        cancelaMandatoExtEnvEvent.setBeanResponse(cancelaMandatoExtEnvEvent.getResponse());
        a((WebServiceEvent) cancelaMandatoExtEnvEvent, 750);
    }

    public void consultaAgendados() {
        if (!mockEnabledServices.F.booleanValue()) {
            super.consultaAgendados();
            return;
        }
        ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent = new ConsultaAgendadosEnvEfeEvent();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Luis AQUINO <dni>", "N", "12345678", "laquino@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("FEDERICO CAMPO PIOMBI <dni extranjero>", "X", "12345678", "CampoPiombi@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Leonardo Campo Piombi <libreta de enrolamiento>", ExifInterface.LONGITUDE_EAST, "12345678", "CampoPiombi@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Ricardo Fuentes <dni extranjero>", "X", "12345678", "Fuentes@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Ana María Hidalgo <dni>", "N", "12345678", "Hidalgo@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("María Hidalgo <dni>", "N", "12345678", "Hidalgo@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Marco Italia <extranjero>", "X", "12345678", "mitalia@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Agustín López <no usar>", "F", "12345678", "LópezAlta@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Carlos López Alta <dni>", "N", "12345678", "LópezAlta@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Gimena Mendoza", "N", "12345678", "mendozag@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Juan Carlos Petruzza <dni>", "N", "12345678", "Petruzza@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Martín Petruzza Enrolamiento <libreta de enrolamiento>", ExifInterface.LONGITUDE_EAST, "12345678", "Petruzza@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Juan Perez <libreta civica>", "C", "12345678", "perezzzz123@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Alberto Renzi <libreta civica>", "C", "12345678", "Renzi@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Juan Carlos Renzi <cuit>", "T", "12345678", "Renzi@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Natalia Suarez <cuit>", "T", "12345678", "Suarez@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Trifan Radu <pasaporte>", "P", "12345678", "rtrifan@gmail.com"));
        arrayList.add(new AMAgendadosEnvEfeDestinatarioBean("Juan Xavier <pasaporte>", "P", "12345678", "profesor_xavier@gmail.com"));
        consultaAgendadosEnvEfeEvent.setResult(TypeResult.OK);
        consultaAgendadosEnvEfeEvent.setResponse(new ConsultaAgendadosEnvEfeResponseBean(new PrivateHeaderBean(), new ConsultaAgendadosEnvEfeBodyResponseBean(new ConsultaAgendadosEnvEfeListaDestinatariosBean((List<AMAgendadosEnvEfeDestinatarioBean>) arrayList))));
        consultaAgendadosEnvEfeEvent.setBeanResponse(consultaAgendadosEnvEfeEvent.getResponse());
        a((WebServiceEvent) consultaAgendadosEnvEfeEvent, 1500);
    }

    public void cargaDatosInicialesEnv() {
        a(TipoOperacion.EnvioDinero);
    }

    public void cargaDatosInicialesExt() {
        a(TipoOperacion.ExtraccionSinTarjeta);
    }

    private void a(String str) {
        CargaDatosInicialesExtEnvEvent cargaDatosInicialesExtEnvEvent = new CargaDatosInicialesExtEnvEvent();
        ArrayList arrayList = new ArrayList();
        ExtEnvCuentaBean extEnvCuentaBean = new ExtEnvCuentaBean("09", "121", "0336347", "CU $ 121-033634/7", "736.990,55");
        arrayList.add(extEnvCuentaBean);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new ExtEnvLeyendaBean("Descripcion Términos y Condiciones", "EXTENV_TYC", "Términos y Condiciones"));
        arrayList2.add(new ExtEnvLeyendaBean("Descripcion Funcionalidad", "EXTENV_FUNC", "Titulo Funcionalidad"));
        arrayList2.add(new ExtEnvLeyendaBean("Descripcion Limites", "EXTENV_LIM", "Titulo Limites"));
        cargaDatosInicialesExtEnvEvent.setResult(TypeResult.OK);
        PrivateHeaderBean privateHeaderBean = new PrivateHeaderBean();
        CargaDatosInicialesExtEnvBodyResponseBean cargaDatosInicialesExtEnvBodyResponseBean = new CargaDatosInicialesExtEnvBodyResponseBean(new ExtEnvFiltrosMandatosBean("30", "30", "15"), new ExtEnvListaCuentasBean(arrayList), "N", new ExtEnvListaLeyendasBean(arrayList2), new ExtEnvDatosBean("2000", "100"));
        cargaDatosInicialesExtEnvEvent.setResponse(new CargaDatosInicialesExtEnvResponseBean(privateHeaderBean, cargaDatosInicialesExtEnvBodyResponseBean));
        cargaDatosInicialesExtEnvEvent.setBeanResponse(cargaDatosInicialesExtEnvEvent.getResponse());
        this.sessionManager.setDatosInicialesExtExv(((CargaDatosInicialesExtEnvResponseBean) cargaDatosInicialesExtEnvEvent.getBeanResponse()).cargaDatosInicialesExtEnvBodyResponseBean);
        a((WebServiceEvent) cargaDatosInicialesExtEnvEvent, 1500);
    }

    public void getCnsDeuda(CnsDeudaBodyRequestBean cnsDeudaBodyRequestBean) {
        CnsDeudaEvent cnsDeudaEvent = new CnsDeudaEvent();
        cnsDeudaEvent.setResponse((CnsDeudaResponseBean) a((int) R.string.mocks_cnsdeuda_pago_servicios, CnsDeudaResponseBean.class));
        cnsDeudaEvent.setResult(TypeResult.OK);
        cnsDeudaEvent.setTypeOption(TypeOption.INITIAL_VIEW);
        cnsDeudaEvent.setBeanResponse(cnsDeudaEvent.getResponse());
        a((WebServiceEvent) cnsDeudaEvent, 1000);
    }

    public void getPagoServicios(PagoServiciosBodyRequestBean pagoServiciosBodyRequestBean, Activity activity, boolean z) {
        PagoServiciosEvent pagoServiciosEvent = new PagoServiciosEvent();
        pagoServiciosEvent.setResult(TypeResult.OK);
        pagoServiciosEvent.setResponse((PagoServiciosResponseBean) a((int) R.string.mocks_pagoservicio, PagoServiciosResponseBean.class));
        pagoServiciosEvent.setBeanResponse(pagoServiciosEvent.getResponse());
        a((WebServiceEvent) pagoServiciosEvent, 1000);
    }

    public void recargaCelulares(RecargaCelularesBodyRequestBean recargaCelularesBodyRequestBean, Activity activity) {
        RecargaCelularesEvent recargaCelularesEvent = new RecargaCelularesEvent();
        recargaCelularesEvent.setResult(TypeResult.OK);
        recargaCelularesEvent.setResponse((RecargaCelularesResponseBean) a((int) R.string.mocks_recargaCelulares_2, RecargaCelularesResponseBean.class));
        recargaCelularesEvent.setBeanResponse(recargaCelularesEvent.getResponse());
        a((WebServiceEvent) recargaCelularesEvent, 1000);
    }

    public void cnsDeudaCB(String str, String str2, String str3) {
        if (!mockEnabledServices.j.booleanValue()) {
            super.cnsDeudaCB(str, str2, str3);
            return;
        }
        CnsDeudaCBEvent cnsDeudaCBEvent = new CnsDeudaCBEvent();
        cnsDeudaCBEvent.setResult(TypeResult.OK);
        if (cnsDeudaCBEvent.getResult().equals(TypeResult.OK)) {
            cnsDeudaCBEvent.setResponse((CnsDeudaCBResponseBean) a((int) R.string.mocks_cnsdeudacb_2_empresas, CnsDeudaCBResponseBean.class));
        } else {
            cnsDeudaCBEvent.setResponse((CnsDeudaCBResponseBean) a((int) R.string.mocks_cnsDeudaCBError, CnsDeudaCBResponseBean.class));
            cnsDeudaCBEvent.setMessageToShow(cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.resDesc);
            cnsDeudaCBEvent.setTitleToShow(cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.resTitle);
        }
        cnsDeudaCBEvent.setBeanResponse(cnsDeudaCBEvent.getResponse());
        a((WebServiceEvent) cnsDeudaCBEvent, 1000);
    }

    public void cnsEmpresa(String str) {
        if (!mockEnabledServices.j.booleanValue()) {
            super.cnsEmpresa(str);
            return;
        }
        CnsEmpresaEvent cnsEmpresaEvent = new CnsEmpresaEvent();
        boolean isEmpty = TextUtils.isEmpty(str);
        int i2 = R.string.mocks_cnsempresa;
        if (!isEmpty) {
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -2049400623) {
                if (hashCode != -29269936) {
                    if (hashCode != 0) {
                        if (hashCode != 2545) {
                            if (hashCode != 79489) {
                                if (hashCode != 2014059) {
                                    if (hashCode == 2138059 && str.equals("ESUR")) {
                                        c = 4;
                                    }
                                } else if (str.equals("ANTR")) {
                                    c = 5;
                                }
                            } else if (str.equals("PRC")) {
                                c = 2;
                            }
                        } else if (str.equals("PA")) {
                            c = 3;
                        }
                    } else if (str.equals("")) {
                        c = 6;
                    }
                } else if (str.equals("PCFCUIT")) {
                    c = 1;
                }
            } else if (str.equals("PCFNOCUIT")) {
                c = 0;
            }
            switch (c) {
                case 0:
                    i2 = R.string.mocks_cnsempresa_pcf_nocuit;
                    break;
                case 1:
                    i2 = R.string.mocks_cnsempresa_pcf_cuit;
                    break;
                case 2:
                    i2 = R.string.mocks_cnsempresa_prc;
                    break;
                case 3:
                    i2 = R.string.mocks_cnsempresa_pa;
                    break;
                case 6:
                    break;
                default:
                    i2 = R.string.mocks_cnsempresa_edesur;
                    break;
            }
            cnsEmpresaEvent.setResult(TypeResult.OK);
            cnsEmpresaEvent.setResponse((CnsEmpresaResponseBean) a(i2, CnsEmpresaResponseBean.class));
            cnsEmpresaEvent.setBeanResponse(cnsEmpresaEvent.getResponse());
        } else {
            cnsEmpresaEvent.setResult(TypeResult.OK);
            cnsEmpresaEvent.setResponse((CnsEmpresaResponseBean) a((int) R.string.mocks_cnsempresa, CnsEmpresaResponseBean.class));
            cnsEmpresaEvent.setBeanResponse(cnsEmpresaEvent.getResponse());
            String str2 = cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean.hashCode;
            if (String.valueOf(str).isEmpty()) {
                if (str2 != null) {
                    this.d.setCnsEmpresaPrevHashCode(str2);
                    this.d.setPagoServiciosEmpresas(cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean);
                } else {
                    cnsEmpresaEvent.getResponse().cnsEmpresaBodyResponseBean = this.d.getPagoServiciosEmpresas();
                }
            }
        }
        a((WebServiceEvent) cnsEmpresaEvent, 1000);
    }

    public void cnsDeudaManual(String str, String str2) {
        CnsDeudaManualEvent cnsDeudaManualEvent = new CnsDeudaManualEvent();
        cnsDeudaManualEvent.setResponse((CnsDeudaManualResponseBean) a((int) R.string.mocks_cnsdeuda_manual_yanitza, CnsDeudaManualResponseBean.class));
        cnsDeudaManualEvent.setBeanResponse(cnsDeudaManualEvent.getResponse());
        cnsDeudaManualEvent.setResult(TypeResult.OK);
        a((WebServiceEvent) cnsDeudaManualEvent, 1000);
    }

    public void consultaMandatos(ConsultaMandatosExtEnvBodyRequestBean consultaMandatosExtEnvBodyRequestBean) {
        if (!mockEnabledServices.G.booleanValue()) {
            super.consultaMandatos(consultaMandatosExtEnvBodyRequestBean);
            return;
        }
        ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent = new ConsultaMandatosExtEnvEvent();
        ArrayList arrayList = new ArrayList();
        MandatarioBean mandatarioBean = new MandatarioBean("01", "001", "0000011", "CU $ 001-000001/1");
        BeneficiarioMandatoBean beneficiarioMandatoBean = new BeneficiarioMandatoBean("X", "11111111", "Alguien <dni extranjero>", "alguien@dniextranjero.com");
        DetalleMandatoBean detalleMandatoBean = new DetalleMandatoBean("1001", "1000000000100001", Estado.Pendiente, "01/01/2015", "02/01/2015", "03/01/2015 00:01 Hs.", "1000", "100101012015");
        arrayList.add(new MandatoBean(mandatarioBean, beneficiarioMandatoBean, detalleMandatoBean));
        MandatarioBean mandatarioBean2 = new MandatarioBean("02", "002", "0000022", "CU $ 002-000002/2");
        BeneficiarioMandatoBean beneficiarioMandatoBean2 = new BeneficiarioMandatoBean("X", "22222222", "", "");
        DetalleMandatoBean detalleMandatoBean2 = new DetalleMandatoBean("2002", "2000000000200002", Estado.Pendiente, "01/02/2015", "02/02/2015", "03/02/2015 00:02 Hs.", "2000", "200201022015");
        arrayList.add(new MandatoBean(mandatarioBean2, beneficiarioMandatoBean2, detalleMandatoBean2));
        MandatarioBean mandatarioBean3 = new MandatarioBean("03", "003", "0000033", "CU $ 003-000003/3");
        BeneficiarioMandatoBean beneficiarioMandatoBean3 = new BeneficiarioMandatoBean("N", "33333333", "Alguien <dni>", "alguien@dni.com");
        DetalleMandatoBean detalleMandatoBean3 = new DetalleMandatoBean("3003", "3000000000300003", Estado.Vencido, "01/03/2015", "02/03/2015", "03/03/2015 00:03 Hs.", "3000", "300301032015");
        arrayList.add(new MandatoBean(mandatarioBean3, beneficiarioMandatoBean3, detalleMandatoBean3));
        MandatarioBean mandatarioBean4 = new MandatarioBean("04", "004", "0000044", "CU $ 004-000004/4");
        BeneficiarioMandatoBean beneficiarioMandatoBean4 = new BeneficiarioMandatoBean("N", "44444444", null, null);
        DetalleMandatoBean detalleMandatoBean4 = new DetalleMandatoBean("4004", "4000000000400004", Estado.Vencido, "01/04/2015", "02/04/2015", "03/04/2015 00:04 Hs.", "4000", "400401042015");
        arrayList.add(new MandatoBean(mandatarioBean4, beneficiarioMandatoBean4, detalleMandatoBean4));
        MandatarioBean mandatarioBean5 = new MandatarioBean("05", "005", "0000055", "CU $ 005-000005/5");
        BeneficiarioMandatoBean beneficiarioMandatoBean5 = new BeneficiarioMandatoBean("C", "55555555", "Alguien <libreta civica>", "alguien@libretacivica.com");
        DetalleMandatoBean detalleMandatoBean5 = new DetalleMandatoBean("5005", "5000000000500005", Estado.Cancelado, "01/05/2015", "02/05/2015", "03/05/2015 00:05 Hs.", "5000", "500501052015");
        arrayList.add(new MandatoBean(mandatarioBean5, beneficiarioMandatoBean5, detalleMandatoBean5));
        MandatarioBean mandatarioBean6 = new MandatarioBean(TarjetasConstants.CODIGO_TARJETA_MASTERCARD, "006", "0000066", "CU $ 006-000006/6");
        BeneficiarioMandatoBean beneficiarioMandatoBean6 = new BeneficiarioMandatoBean("C", "66666666", null, null);
        DetalleMandatoBean detalleMandatoBean6 = new DetalleMandatoBean("6006", "6000000000600006", Estado.Cancelado, "01/06/2015", "02/06/2015", "03/06/2015 00:06 Hs.", "6000", "600601062015");
        arrayList.add(new MandatoBean(mandatarioBean6, beneficiarioMandatoBean6, detalleMandatoBean6));
        MandatarioBean mandatarioBean7 = new MandatarioBean(TarjetasConstants.CODIGO_TARJETA_VISA, "007", "0000077", "CU $ 007-000007/7");
        BeneficiarioMandatoBean beneficiarioMandatoBean7 = new BeneficiarioMandatoBean(ExifInterface.LONGITUDE_EAST, "77777777", "Alguien <libreta de enrolamiento>", "alguien@libretaenrolamiento.com");
        DetalleMandatoBean detalleMandatoBean7 = new DetalleMandatoBean("7007", "7000000000700007", Estado.Cobrado, "01/07/2015", "02/07/2015", "03/07/2015 00:07 Hs.", "7000", "700701072015");
        arrayList.add(new MandatoBean(mandatarioBean7, beneficiarioMandatoBean7, detalleMandatoBean7));
        MandatarioBean mandatarioBean8 = new MandatarioBean("08", "008", "0000088", "CU $ 008-000008/8");
        BeneficiarioMandatoBean beneficiarioMandatoBean8 = new BeneficiarioMandatoBean(ExifInterface.LONGITUDE_EAST, "88888888", null, null);
        DetalleMandatoBean detalleMandatoBean8 = new DetalleMandatoBean("8008", "8000000000800008", Estado.Cobrado, "01/08/2015", "02/08/2015", "03/08/2015 00:08 Hs.", "8000", "800801082015");
        arrayList.add(new MandatoBean(mandatarioBean8, beneficiarioMandatoBean8, detalleMandatoBean8));
        consultaMandatosExtEnvEvent.setResult(TypeResult.OK);
        consultaMandatosExtEnvEvent.setResponse(new ConsultaMandatosExtEnvResponseBean(new PrivateHeaderBean(), new ConsultaMandatosExtEnvBodyResponseBean(new ConsultaMandatosExtEnvListaMandatosBean(arrayList))));
        consultaMandatosExtEnvEvent.setBeanResponse(consultaMandatosExtEnvEvent.getResponse());
        a((WebServiceEvent) consultaMandatosExtEnvEvent, 750);
    }

    public void ADestinatario(AMAgendadosEnvEfeDestinatarioBean aMAgendadosEnvEfeDestinatarioBean, Activity activity) {
        if (!mockEnabledServices.H.booleanValue()) {
            super.ADestinatario(aMAgendadosEnvEfeDestinatarioBean, activity);
            return;
        }
        AMAgendadosEnvEfeEvent aMAgendadosEnvEfeEvent = new AMAgendadosEnvEfeEvent();
        aMAgendadosEnvEfeEvent.setResult(TypeResult.OK);
        aMAgendadosEnvEfeEvent.setResponse(new AMAgendadosEnvEfeResponseBean(new PrivateHeaderBean(), new AMAgendadosEnvEfeBodyResponseBean()));
        aMAgendadosEnvEfeEvent.setBeanResponse(aMAgendadosEnvEfeEvent.getResponse());
        a((WebServiceEvent) aMAgendadosEnvEfeEvent, 1000);
    }

    public void BDestinatario(int i2, BAgendadosEnvEfeDestinatarioBean bAgendadosEnvEfeDestinatarioBean) {
        if (!mockEnabledServices.I.booleanValue()) {
            super.BDestinatario(i2, bAgendadosEnvEfeDestinatarioBean);
            return;
        }
        BAgendadosEnvEfeEvent bAgendadosEnvEfeEvent = new BAgendadosEnvEfeEvent();
        bAgendadosEnvEfeEvent.setResult(TypeResult.OK);
        bAgendadosEnvEfeEvent.setResponse(new BAgendadosEnvEfeResponseBean(new PrivateHeaderBean(), new BAgendadosEnvEfeBodyResponseBean()));
        bAgendadosEnvEfeEvent.setBeanResponse(bAgendadosEnvEfeEvent.getResponse());
        a((WebServiceEvent) bAgendadosEnvEfeEvent, 1000);
    }

    public void MDestinatario(AMAgendadosEnvEfeDestinatarioBean aMAgendadosEnvEfeDestinatarioBean, Activity activity) {
        if (!mockEnabledServices.J.booleanValue()) {
            super.ADestinatario(aMAgendadosEnvEfeDestinatarioBean, activity);
        } else {
            ADestinatario(aMAgendadosEnvEfeDestinatarioBean, activity);
        }
    }

    public void abmAlias(String str, String str2, String str3, String str4, CuentaShortBean cuentaShortBean) {
        abmAlias(str, str2, null, str3, str4, cuentaShortBean);
    }

    public void abmAlias(String str, String str2, String str3, String str4, String str5, CuentaShortBean cuentaShortBean) {
        if (!mockEnabledServices.P.booleanValue()) {
            super.abmAlias(str, str2, str4, str5, cuentaShortBean);
            return;
        }
        ABMAliasEvent aBMAliasEvent = new ABMAliasEvent();
        aBMAliasEvent.setResult(TypeResult.OK);
        aBMAliasEvent.setResponse((ABMAliasResponseBean) a((int) R.string.mocks_abmAlias, ABMAliasResponseBean.class));
        aBMAliasEvent.setBeanResponse(aBMAliasEvent.getResponse());
        a((WebServiceEvent) aBMAliasEvent, 1000);
    }

    public void getViajes() {
        if (!mockEnabledServices.Q.booleanValue()) {
            super.getViajes();
            return;
        }
        final GetViajesEvent getViajesEvent = new GetViajesEvent();
        new GetViajesRequest(super.createErrorRequestServer(getViajesEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getViajesEvent.setResponse((GetViajesResponseBean) iBeanWS);
                getViajesEvent.setBeanResponse(getViajesEvent.getResponse());
                getViajesEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getViajesEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getViajesEvent, VGetViajes.nameService);
            }
        }.parserResponse(b(R.string.mocks_getViajes, GetViajesResponseBean.class));
    }

    public void getTarjPaises() {
        if (!mockEnabledServices.R.booleanValue()) {
            super.getTarjPaises();
            return;
        }
        GetTarjPaisesEvent getTarjPaisesEvent = new GetTarjPaisesEvent();
        getTarjPaisesEvent.setResult(TypeResult.OK);
        getTarjPaisesEvent.setResponse((GetTarjPaisesResponseBean) a((int) R.string.mocks_getTarjPaises, GetTarjPaisesResponseBean.class));
        getTarjPaisesEvent.setBeanResponse(getTarjPaisesEvent.getResponse());
        a((WebServiceEvent) getTarjPaisesEvent, 1000);
    }

    public void abmViajes(String str, String str2, String str3, String str4, String str5, String str6, PaisesBean paisesBean, TarjetasMarcacionBean tarjetasMarcacionBean) {
        if (!mockEnabledServices.S.booleanValue()) {
            super.abmViajes(str, str2, str3, str4, str5, str6, paisesBean, tarjetasMarcacionBean);
            return;
        }
        ABMViajesEvent aBMViajesEvent = new ABMViajesEvent();
        aBMViajesEvent.setResult(TypeResult.OK);
        aBMViajesEvent.setResponse((ABMViajesResponseBean) a((int) R.string.mocks_abmViajes, ABMViajesResponseBean.class));
        a((WebServiceEvent) aBMViajesEvent, 1000);
    }

    public void getSeguros() {
        if (!mockEnabledServices.ai.booleanValue()) {
            super.getSeguros();
            return;
        }
        final GetSegurosEvent getSegurosEvent = new GetSegurosEvent();
        new GetSegurosRequest(super.createErrorRequestServer(getSegurosEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getSegurosEvent.setResponse((GetSegurosResponseBean) iBeanWS);
                getSegurosEvent.setBeanResponse(getSegurosEvent.getResponse());
                getSegurosEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getSegurosEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getSegurosEvent, VGetSeguros.nameService);
            }
        }.parserResponse(b(R.string.mocks_getSeguros_reemplazo_cobertura_movil_2, GetSegurosResponseBean.class));
    }

    public void getRecargas(String str, String str2, String str3, String str4, String str5) {
        if (!mockEnabledServices.aX.booleanValue()) {
            super.getRecargas(str, str2, str3, str4, str5);
            return;
        }
        final GetRecargasEvent getRecargasEvent = new GetRecargasEvent();
        new GetRecargasRequest(super.createErrorRequestServer(getRecargasEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getRecargasEvent.setResponse((GetRecargasResponseBean) iBeanWS);
                getRecargasEvent.setBeanResponse(getRecargasEvent.getResponse());
                getRecargasEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getRecargasEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getRecargasEvent, VRecargas.nameService);
            }
        }.parserResponse(b(R.string.mocks_GetRecargas, GetRecargasResponseBean.class));
    }

    public void getCotizacionSeguroMovil() {
        if (!mockEnabledServices.aj.booleanValue()) {
            super.getCotizacionSeguroMovil();
            return;
        }
        final GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent = new GetCotizacionSeguroMovilEvent();
        new GetCotizacionSeguroMovilRequestBean().header = a(VGetCotizacionSeguroMovil.getData(EVersionServices.CURRENT), this.sessionManager.getSession(), this.sessionManager.getNup());
        new GetCotizacionSeguroMovilRequest(createErrorRequestServer(getCotizacionSeguroMovilEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetCotizacionSeguroMovilResponseBean getCotizacionSeguroMovilResponseBean = (GetCotizacionSeguroMovilResponseBean) iBeanWS;
                getCotizacionSeguroMovilEvent.setResponse(getCotizacionSeguroMovilResponseBean);
                getCotizacionSeguroMovilEvent.setBeanResponse(getCotizacionSeguroMovilResponseBean);
                getCotizacionSeguroMovilEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(getCotizacionSeguroMovilEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.this.createUnknowErrorEventProcesor(exc, getCotizacionSeguroMovilEvent, VGetCotizacionSeguroMovil.nameService);
            }
        }.parserResponse(b(R.string.mocks_getCotizacionSeguroMovil_1_1, GetCotizacionSeguroMovilResponseBean.class));
    }

    public void getCotizacionCompraProtegida() {
        if (!mockEnabledServices.ak.booleanValue()) {
            super.getCotizacionCompraProtegida();
            return;
        }
        GetCotizacionCompraProtegidaEvent getCotizacionCompraProtegidaEvent = new GetCotizacionCompraProtegidaEvent();
        getCotizacionCompraProtegidaEvent.setResult(TypeResult.OK);
        getCotizacionCompraProtegidaEvent.setResponse((GetCotizacionCompraProtegidaResponseBean) a((int) R.string.mocks_getCotizacionCompraProtegida, GetCotizacionCompraProtegidaResponseBean.class));
        getCotizacionCompraProtegidaEvent.setBeanResponse(getCotizacionCompraProtegidaEvent.getResponse());
        a((WebServiceEvent) getCotizacionCompraProtegidaEvent, 1000);
    }

    public void contratarSeguroMovil(String str, String str2, String str3, String str4, String str5, String str6, String str7, BajaSeguroBean bajaSeguroBean, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean) {
        if (!mockEnabledServices.aq.booleanValue()) {
            super.contratarSeguroMovil(str, str2, str3, str4, str5, str6, str7, bajaSeguroBean, cuentaShortBean, tarjetaBean);
            return;
        }
        final ContratarSeguroMovilEvent contratarSeguroMovilEvent = new ContratarSeguroMovilEvent();
        new ContratarSeguroMovilRequest(super.createErrorRequestServer(contratarSeguroMovilEvent, TypeOption.TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                contratarSeguroMovilEvent.setResponse((ContratarSeguroMovilResponseBean) iBeanWS);
                contratarSeguroMovilEvent.setBeanResponse(contratarSeguroMovilEvent.getResponse());
                contratarSeguroMovilEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) contratarSeguroMovilEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, contratarSeguroMovilEvent, VContratarSeguroMovil.nameService);
            }
        }.parserResponse(b(R.string.mocks_contratarSeguro, ContratarSeguroMovilResponseBean.class));
    }

    public void contratarCompraProtegida(String str, String str2, String str3, String str4, List<TarjetaBean> list, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean) {
        if (!mockEnabledServices.f246ar.booleanValue()) {
            super.contratarCompraProtegida(str, str2, str3, str4, list, cuentaShortBean, tarjetaBean);
            return;
        }
        final ContratarCompraProtegidaEvent contratarCompraProtegidaEvent = new ContratarCompraProtegidaEvent();
        new ContratarCompraProtegidaRequest(super.createErrorRequestServer(contratarCompraProtegidaEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                ContratarCompraProtegidaResponseBean contratarCompraProtegidaResponseBean = (ContratarCompraProtegidaResponseBean) iBeanWS;
                contratarCompraProtegidaEvent.setBeanResponse(contratarCompraProtegidaResponseBean);
                contratarCompraProtegidaEvent.setResponse(contratarCompraProtegidaResponseBean);
                contratarCompraProtegidaEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(contratarCompraProtegidaEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, contratarCompraProtegidaEvent, VContratarCompraProtegida.nameService);
            }
        }.parserResponse(b(R.string.mocks_contratarSeguro, ContratarCompraProtegidaResponseBean.class));
    }

    public void getOcupaciones() {
        if (!mockEnabledServices.as.booleanValue()) {
            super.getOcupaciones();
            return;
        }
        final GetOcupacionesEvent getOcupacionesEvent = new GetOcupacionesEvent();
        new GetOcupacionesRequest(super.createErrorRequestServer(getOcupacionesEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetOcupacionesResponseBean getOcupacionesResponseBean = (GetOcupacionesResponseBean) iBeanWS;
                getOcupacionesEvent.setBeanResponse(getOcupacionesResponseBean);
                getOcupacionesEvent.setResponse(getOcupacionesResponseBean);
                getOcupacionesEvent.setResult(TypeResult.OK);
                MockableDataManager.this.d.setGetOcupacionesPrevHashCode(getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().getHashCode());
                if (getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().getOcupaciones() == null || getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().getOcupaciones().getListOcupaciones().size() <= 0) {
                    getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().setOcupaciones(MockableDataManager.this.d.getOcupaciones());
                } else {
                    MockableDataManager.this.d.setOcupaciones(getOcupacionesResponseBean.getGetOcupacionesBodyResponseBean().getOcupaciones());
                }
                MockableDataManager.this.sendBusEvent(getOcupacionesEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getOcupacionesEvent, VGetOcupaciones.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_getOcupaciones, GetOcupacionesResponseBean.class));
    }

    public void getPoliza(String str, String str2, String str3) {
        if (!mockEnabledServices.at.booleanValue()) {
            super.getPoliza(str, str2, str3);
            return;
        }
        final GetPolizaEvent getPolizaEvent = new GetPolizaEvent();
        new GetPolizaRequest(super.createErrorRequestServer(getPolizaEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetPolizaResponseBean getPolizaResponseBean = (GetPolizaResponseBean) iBeanWS;
                getPolizaEvent.setBeanResponse(getPolizaResponseBean);
                getPolizaEvent.setResponse(getPolizaResponseBean);
                getPolizaEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(getPolizaEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getPolizaEvent, VGetPoliza.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_getPoliza_3, GetPolizaResponseBean.class));
    }

    private PrivateHeaderBean a(ServiceHeaderBean serviceHeaderBean, String str, String str2) {
        return ManagerHeaderBean.getPrivateHeaderBean(serviceHeaderBean, this.e.getApplicationContext(), str, str2);
    }

    private PrivateHeaderBean a(ServiceHeaderBean serviceHeaderBean, String str, String str2, Boolean bool, Boolean bool2, Activity activity) {
        return ManagerHeaderBean.getPrivateHeaderBean(serviceHeaderBean, this.e.getApplicationContext(), str, str2, bool, bool2, activity);
    }

    public void getTenenciaFondos() {
        if (!mockEnabledServices.W.booleanValue()) {
            super.getTenenciaFondos();
            return;
        }
        final GetTenenciaFondosEvent getTenenciaFondosEvent = new GetTenenciaFondosEvent();
        new GetTenenciaFondosRequest(super.createErrorRequestServer(getTenenciaFondosEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetTenenciaFondosResponseBean getTenenciaFondosResponseBean = (GetTenenciaFondosResponseBean) iBeanWS;
                getTenenciaFondosEvent.setResponseBean(getTenenciaFondosResponseBean);
                getTenenciaFondosEvent.setBeanResponse(getTenenciaFondosResponseBean);
                getTenenciaFondosEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getTenenciaFondosEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getTenenciaFondosEvent, VGetTenenciaFondos.nameService);
            }
        }.parserResponse(b(R.string.mocks_getTenenciaFondos, GetTenenciaFondosResponseBean.class));
    }

    public void getFirmaSeguro() {
        if (!mockEnabledServices.V.booleanValue()) {
            super.getFirmaSeguro();
            return;
        }
        GetFirmaSeguroEvent getFirmaSeguroEvent = new GetFirmaSeguroEvent();
        getFirmaSeguroEvent.setResult(TypeResult.OK);
        getFirmaSeguroEvent.setResponse((GetFirmaSeguroResponseBean) a((int) R.string.mocks_getFirmaSeguro, GetFirmaSeguroResponseBean.class));
        getFirmaSeguroEvent.setBeanResponse(getFirmaSeguroEvent.getResponse());
        a((WebServiceEvent) getFirmaSeguroEvent, 1000);
    }

    public void getFondos() {
        if (!mockEnabledServices.U.booleanValue()) {
            super.getFondos();
            return;
        }
        GetFondosEvent getFondosEvent = new GetFondosEvent();
        getFondosEvent.setResult(TypeResult.OK);
        getFondosEvent.setResponseBean((GetFondosResponseBean) a((int) R.string.mocks_getFondos, GetFondosResponseBean.class));
        getFondosEvent.setBeanResponse(getFondosEvent.getResponseBean());
        a((WebServiceEvent) getFondosEvent, 1000);
    }

    public void getMovimientosFondo(String str, String str2) {
        getMovimientosFondo(str, str2, Boolean.valueOf(false));
    }

    public void getMovimientosFondo(String str, String str2, Boolean bool) {
        if (!mockEnabledServices.T.booleanValue()) {
            super.getMovimientosFondo(str, str2, bool);
            return;
        }
        GetMovimientosFondoEvent getMovimientosFondoEvent = new GetMovimientosFondoEvent();
        getMovimientosFondoEvent.setResult(TypeResult.OK);
        getMovimientosFondoEvent.setResponseBean((GetMovimientosFondoResponseBean) a((int) R.string.mocks_getMovimientosFondo, GetMovimientosFondoResponseBean.class));
        getMovimientosFondoEvent.setBeanResponse(getMovimientosFondoEvent.getResponseBean());
        a((WebServiceEvent) getMovimientosFondoEvent, 1000);
    }

    public void getDetalleFondo(String str) {
        if (!mockEnabledServices.X.booleanValue()) {
            super.getDetalleFondo(str);
            return;
        }
        GetDetalleFondoEvent getDetalleFondoEvent = new GetDetalleFondoEvent();
        getDetalleFondoEvent.setResult(TypeResult.OK);
        getDetalleFondoEvent.setResponseBean((GetDetalleFondoResponseBean) a((int) R.string.mocks_getDetalleFondo, GetDetalleFondoResponseBean.class));
        getDetalleFondoEvent.setBeanResponse(getDetalleFondoEvent.getResponseBean());
        a((WebServiceEvent) getDetalleFondoEvent, 1000);
    }

    public void getCotizacionesFondos() {
        if (!mockEnabledServices.Y.booleanValue()) {
            super.getCotizacionesFondos();
            return;
        }
        GetCotizacionesFondoEvent getCotizacionesFondoEvent = new GetCotizacionesFondoEvent();
        getCotizacionesFondoEvent.setResult(TypeResult.OK);
        getCotizacionesFondoEvent.setResponseBean((GetCotizacionesResponseBean) a((int) R.string.mocks_getCotizaciones, GetCotizacionesResponseBean.class));
        getCotizacionesFondoEvent.setBeanResponse(getCotizacionesFondoEvent.getResponseBean());
        a((WebServiceEvent) getCotizacionesFondoEvent, 1000);
    }

    public void getInfoAdmFondos() {
        if (!mockEnabledServices.Z.booleanValue()) {
            super.getInfoAdmFondos();
            return;
        }
        GetInfoAdmFondosEvent getInfoAdmFondosEvent = new GetInfoAdmFondosEvent();
        getInfoAdmFondosEvent.setResult(TypeResult.OK);
        getInfoAdmFondosEvent.setResponseBean((GetInfoAdmFondosResponseBean) a((int) R.string.mocks_getInfoAdmFondos, GetInfoAdmFondosResponseBean.class));
        getInfoAdmFondosEvent.setBeanResponse(getInfoAdmFondosEvent.getResponseBean());
        a((WebServiceEvent) getInfoAdmFondosEvent, 1000);
    }

    public void rescatarFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        if (!mockEnabledServices.aa.booleanValue()) {
            super.rescatarFondo(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11);
            return;
        }
        RescatarFondoEvent rescatarFondoEvent = new RescatarFondoEvent();
        rescatarFondoEvent.setResult(TypeResult.OK);
        rescatarFondoEvent.setResponseBean((RescatarFondoResponseBean) a((int) R.string.mocks_rescatarFondo, RescatarFondoResponseBean.class));
        rescatarFondoEvent.setBeanResponse(rescatarFondoEvent.getResponseBean());
        rescatarFondoEvent.setMessageToShow("Fuera de horario");
        a((WebServiceEvent) rescatarFondoEvent, 1000);
    }

    public void simularRescateFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        if (!mockEnabledServices.ab.booleanValue()) {
            super.simularRescateFondo(str, str2, str3, str4, str5, str6, str7, str8, str9);
            return;
        }
        RescatarFondoEvent rescatarFondoEvent = new RescatarFondoEvent();
        rescatarFondoEvent.setResult(TypeResult.OK);
        rescatarFondoEvent.setResponseBean((RescatarFondoResponseBean) a((int) R.string.mocks_simularRescateFondo, RescatarFondoResponseBean.class));
        rescatarFondoEvent.setBeanResponse(rescatarFondoEvent.getResponseBean());
        rescatarFondoEvent.setMessageToShow("Fuera de horario");
        a((WebServiceEvent) rescatarFondoEvent, 1000);
    }

    public void suscribirFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        if (!mockEnabledServices.ac.booleanValue()) {
            super.suscribirFondo(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13);
            return;
        }
        SuscribirFondoEvent suscribirFondoEvent = new SuscribirFondoEvent();
        suscribirFondoEvent.setResult(TypeResult.OK);
        suscribirFondoEvent.setResponseBean((SuscribirFondoResponseBean) a((int) R.string.mocks_suscribirFondo_altaRiesgo_0, SuscribirFondoResponseBean.class));
        suscribirFondoEvent.setBeanResponse(suscribirFondoEvent.getResponseBean());
        suscribirFondoEvent.setMessageToShow("Fuera de horario");
        a((WebServiceEvent) suscribirFondoEvent, 1000);
    }

    public void simularSuscripcionFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        if (!mockEnabledServices.ad.booleanValue()) {
            super.simularSuscripcionFondo(str, str2, str3, str4, str5, str6, str7, str8, str9);
            return;
        }
        SimularSuscripcionFondoEvent simularSuscripcionFondoEvent = new SimularSuscripcionFondoEvent();
        simularSuscripcionFondoEvent.setResult(TypeResult.OK);
        simularSuscripcionFondoEvent.setResponseBean((SimularSuscripcionFondoResponseBean) a((int) R.string.mocks_simularSuscripcionFondo, SimularSuscripcionFondoResponseBean.class));
        simularSuscripcionFondoEvent.setBeanResponse(simularSuscripcionFondoEvent.getResponseBean());
        simularSuscripcionFondoEvent.setMessageToShow("Fuera de horario");
        a((WebServiceEvent) simularSuscripcionFondoEvent, 1000);
    }

    public void transferirFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        if (!mockEnabledServices.ae.booleanValue()) {
            super.transferirFondo(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, "0");
            return;
        }
        TransferirFondoEvent transferirFondoEvent = new TransferirFondoEvent();
        transferirFondoEvent.setResult(TypeResult.OK);
        transferirFondoEvent.setResponseBean((TransferirFondoResponseBean) a((int) R.string.mocks_transferirFondo_altaRiesgo_0, TransferirFondoResponseBean.class));
        transferirFondoEvent.setBeanResponse(transferirFondoEvent.getResponseBean());
        transferirFondoEvent.setMessageToShow("Fuera de horario");
        a((WebServiceEvent) transferirFondoEvent, 1000);
    }

    public void simularTransferenciaFondo(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        if (!mockEnabledServices.af.booleanValue()) {
            super.simularTransferenciaFondo(str, str2, str3, str4, str5, str6, str7);
            return;
        }
        SimularTransferenciaFondoEvent simularTransferenciaFondoEvent = new SimularTransferenciaFondoEvent();
        simularTransferenciaFondoEvent.setResult(TypeResult.OK);
        simularTransferenciaFondoEvent.setResponseBean((SimularTransferenciaFondoResponseBean) a((int) R.string.mocks_simularTransferenciaFondo, SimularTransferenciaFondoResponseBean.class));
        simularTransferenciaFondoEvent.setBeanResponse(simularTransferenciaFondoEvent.getResponseBean());
        simularTransferenciaFondoEvent.setMessageToShow("Fuera de horario");
        a((WebServiceEvent) simularTransferenciaFondoEvent, 1000);
    }

    public void consultaAlias(String str, String str2, String str3) {
        final ConsultaAliasEvent consultaAliasEvent = new ConsultaAliasEvent();
        new ConsultaAliasRequest(super.createErrorRequestServer(consultaAliasEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                consultaAliasEvent.setBeanResponse((ConsultaAliasResponseBean) iBeanWS);
                consultaAliasEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(consultaAliasEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, consultaAliasEvent, VConsultaAlias.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_consultaAlias, ConsultaAliasResponseBean.class));
    }

    public void getNotificacionesPush(String str) {
        if (!mockEnabledServices.ag.booleanValue()) {
            super.getNotificacionesPush(str);
            return;
        }
        final GetNotificacionesPushEvent getNotificacionesPushEvent = new GetNotificacionesPushEvent();
        new GetNotificacionesPushRequest(super.createErrorRequestServer(getNotificacionesPushEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getNotificacionesPushEvent.setResponseBean((GetNotificacionesPushResponseBean) iBeanWS);
                getNotificacionesPushEvent.setResult(TypeResult.OK);
                MockableDataManager.this.sendBusEvent(getNotificacionesPushEvent);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getNotificacionesPushEvent, VGetNotificacionesPush.nameService);
            }
        }.parserResponse(b(R.string.mocks_getNotificacionesPush, GetNotificacionesPushResponseBean.class));
    }

    public void getDebines(String str, String str2, String str3, String str4, String str5) {
        if (!mockEnabledServices.av.booleanValue()) {
            super.getDebines(str, str2, str3, str4, str5);
            return;
        }
        final GetDebinesEvent getDebinesEvent = new GetDebinesEvent();
        AnonymousClass57 r5 = new GetDebinesRequest(super.createErrorRequestServer(getDebinesEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetDebinesResponseBean getDebinesResponseBean = (GetDebinesResponseBean) iBeanWS;
                getDebinesEvent.setResponse(getDebinesResponseBean);
                getDebinesEvent.setBeanResponse(getDebinesResponseBean);
                getDebinesEvent.setResult(TypeResult.BEAN_ERROR_RES_4);
                MockableDataManager.this.a((WebServiceEvent) getDebinesEvent, 500);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getDebinesEvent, VGetDebines.nameService);
            }
        };
        if (this.i < 10) {
            if (str.equalsIgnoreCase("C")) {
                if (TextUtils.isEmpty(str2)) {
                    r5.parserResponse(b(R.string.mocks_getDebines_comprador, GetDebinesResponseBean.class));
                } else {
                    r5.parserResponse(b(R.string.mocks_getDebines_comprador_busqueda_avanzada, GetDebinesResponseBean.class));
                }
            } else if (TextUtils.isEmpty(str2)) {
                r5.parserResponse(b(R.string.mocks_getDebines_vendedor, GetDebinesResponseBean.class));
            } else {
                r5.parserResponse(b(R.string.mocks_getDebines_vendedor_busqueda_avanzada, GetDebinesResponseBean.class));
            }
            this.i++;
            return;
        }
        r5.parserResponse(b(R.string.mocks_generic_result_4, GetDebinesResponseBean.class));
        this.i = 0;
    }

    public void getDebinesBusquedaAvanzada(String str, String str2, String str3, String str4, String str5) {
        if (!mockEnabledServices.av.booleanValue()) {
            super.getDebines(str, str2, str3, str4, str5);
            return;
        }
        final GetDebinesBusquedaAvanzadaEvent getDebinesBusquedaAvanzadaEvent = new GetDebinesBusquedaAvanzadaEvent();
        AnonymousClass58 r4 = new GetDebinesRequest(super.createErrorRequestServer(getDebinesBusquedaAvanzadaEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetDebinesResponseBean getDebinesResponseBean = (GetDebinesResponseBean) iBeanWS;
                getDebinesBusquedaAvanzadaEvent.setResponse(getDebinesResponseBean);
                getDebinesBusquedaAvanzadaEvent.setBeanResponse(getDebinesResponseBean);
                getDebinesBusquedaAvanzadaEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getDebinesBusquedaAvanzadaEvent, 500);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getDebinesBusquedaAvanzadaEvent, VGetDebines.nameService);
            }
        };
        if (this.i < 10) {
            if (str.equalsIgnoreCase("C")) {
                r4.parserResponse(b(R.string.mocks_getDebines_comprador_busqueda_avanzada, GetDebinesResponseBean.class));
            } else {
                r4.parserResponse(b(R.string.mocks_getDebines_vendedor_busqueda_avanzada, GetDebinesResponseBean.class));
            }
            this.i++;
            return;
        }
        r4.parserResponse(b(R.string.mocks_generic_result_4, GetDebinesResponseBean.class));
        this.i = 0;
    }

    public void getDetalleDebinComprador(String str) {
        if (!mockEnabledServices.aw.booleanValue()) {
            super.getDetalleDebinComprador(str);
            return;
        }
        final GetDetalleDebinCompradorEvent getDetalleDebinCompradorEvent = new GetDetalleDebinCompradorEvent();
        new GetDetalleDebinCompradorRequest(super.createErrorRequestServer(getDetalleDebinCompradorEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetDetalleDebinCompradorResponseBean getDetalleDebinCompradorResponseBean = (GetDetalleDebinCompradorResponseBean) iBeanWS;
                getDetalleDebinCompradorEvent.setResponse(getDetalleDebinCompradorResponseBean);
                getDetalleDebinCompradorEvent.setBeanResponse(getDetalleDebinCompradorResponseBean);
                getDetalleDebinCompradorEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getDetalleDebinCompradorEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getDetalleDebinCompradorEvent, VGetDetalleDebinComprador.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_getDetalleDebinComprador, GetDetalleDebinCompradorResponseBean.class));
    }

    public void getDetalleDebinVendedor(String str) {
        if (!mockEnabledServices.a) {
            super.getDetalleDebinVendedor(str);
            return;
        }
        final GetDetalleDebinVendedorEvent getDetalleDebinVendedorEvent = new GetDetalleDebinVendedorEvent();
        new GetDetalleDebinVendedorRequest(super.createErrorRequestServer(getDetalleDebinVendedorEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GetDetalleDebinVendedorResponseBean getDetalleDebinVendedorResponseBean = (GetDetalleDebinVendedorResponseBean) iBeanWS;
                getDetalleDebinVendedorEvent.setResponse(getDetalleDebinVendedorResponseBean);
                getDetalleDebinVendedorEvent.setBeanResponse(getDetalleDebinVendedorResponseBean);
                getDetalleDebinVendedorEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getDetalleDebinVendedorEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getDetalleDebinVendedorEvent, VGetDetalleDebinVendedor.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_getDetalleDebinVendedor, GetDetalleDebinVendedorResponseBean.class));
    }

    public void abmDebinVendedor(String str, DetalleDebinBean detalleDebinBean, String str2, Activity activity, boolean z) {
        if (!mockEnabledServices.ax.booleanValue()) {
            super.abmDebinVendedor(str, detalleDebinBean, str2, activity, z);
            return;
        }
        final AbmDebinVendedorEvent abmDebinVendedorEvent = new AbmDebinVendedorEvent();
        new AbmDebinVendedorRequest(super.createErrorRequestServer(abmDebinVendedorEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                AbmDebinVendedorResponseBean abmDebinVendedorResponseBean = (AbmDebinVendedorResponseBean) iBeanWS;
                abmDebinVendedorEvent.setResponse(abmDebinVendedorResponseBean);
                abmDebinVendedorEvent.setBeanResponse(abmDebinVendedorResponseBean);
                abmDebinVendedorEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) abmDebinVendedorEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, abmDebinVendedorEvent, VGetDetalleDebinVendedor.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_abmDebinVendedor, AbmDebinVendedorResponseBean.class));
    }

    public void abmDebinComprador(String str, DetalleDebinBean detalleDebinBean, Activity activity, boolean z) {
        if (!mockEnabledServices.ay.booleanValue()) {
            super.abmDebinComprador(str, detalleDebinBean, activity, z);
            return;
        }
        final AbmDebinCompradorEvent abmDebinCompradorEvent = new AbmDebinCompradorEvent();
        new AbmDebinCompradorRequest(super.createErrorRequestServer(abmDebinCompradorEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                AbmDebinCompradorResponseBean abmDebinCompradorResponseBean = (AbmDebinCompradorResponseBean) iBeanWS;
                abmDebinCompradorEvent.setResponse(abmDebinCompradorResponseBean);
                abmDebinCompradorEvent.setBeanResponse(abmDebinCompradorResponseBean);
                abmDebinCompradorEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) abmDebinCompradorEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, abmDebinCompradorEvent, VGetDetalleDebinVendedor.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_abmDebinComprador, AbmDebinCompradorResponseBean.class));
    }

    public void abmAdhesionVendedor(String str, CuentaVendedor cuentaVendedor, Activity activity, boolean z) {
        if (!mockEnabledServices.az.booleanValue()) {
            super.abmAdhesionVendedor(str, cuentaVendedor, activity, z);
            return;
        }
        final AbmAdhesionVendedorEvent abmAdhesionVendedorEvent = new AbmAdhesionVendedorEvent();
        new AbmAdhesionVendedorRequest(super.createErrorRequestServer(abmAdhesionVendedorEvent, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                AbmAdhesionVendedorResponseBean abmAdhesionVendedorResponseBean = (AbmAdhesionVendedorResponseBean) iBeanWS;
                abmAdhesionVendedorEvent.setResponse(abmAdhesionVendedorResponseBean);
                abmAdhesionVendedorEvent.setBeanResponse(abmAdhesionVendedorResponseBean);
                abmAdhesionVendedorEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) abmAdhesionVendedorEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, abmAdhesionVendedorEvent, VAbmAdhesionVendedorDebin.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_abmAdhesionVendedor, AbmAdhesionVendedorResponseBean.class));
    }

    public void consultarTitularCuenta(String str, String str2) {
        if (!mockEnabledServices.aA.booleanValue()) {
            super.consultarTitularCuenta(str, str2);
            return;
        }
        final ConsultarTitularCuentaEvent consultarTitularCuentaEvent = new ConsultarTitularCuentaEvent();
        new ConsultarTitularCuentaRequest(super.createErrorRequestServer(consultarTitularCuentaEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                ConsultarTitularCuentaResponseBean consultarTitularCuentaResponseBean = (ConsultarTitularCuentaResponseBean) iBeanWS;
                consultarTitularCuentaEvent.setResponse(consultarTitularCuentaResponseBean);
                consultarTitularCuentaEvent.setBeanResponse(consultarTitularCuentaResponseBean);
                consultarTitularCuentaEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) consultarTitularCuentaEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, consultarTitularCuentaEvent, VGetConsultarTitularCuenta.nameService);
            }
        }.parserResponse(a((int) R.string.mocks_consultarTitularCuenta, ConsultarTitularCuentaResponseBean.class));
    }

    public void consultarAdhesionVendedor() {
        if (!mockEnabledServices.aB.booleanValue()) {
            super.consultarAdhesionVendedor();
            return;
        }
        final ConsultarAdhesionVendedorEvent consultarAdhesionVendedorEvent = new ConsultarAdhesionVendedorEvent();
        AnonymousClass65 r1 = new ConsultarAdhesionVendedorRequest(super.createErrorRequestServer(consultarAdhesionVendedorEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                ConsultarAdhesionVendedorResponseBean consultarAdhesionVendedorResponseBean = (ConsultarAdhesionVendedorResponseBean) iBeanWS;
                consultarAdhesionVendedorEvent.setResponse(consultarAdhesionVendedorResponseBean);
                consultarAdhesionVendedorEvent.setBeanResponse(consultarAdhesionVendedorResponseBean);
                consultarAdhesionVendedorEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) consultarAdhesionVendedorEvent, 1000);
                MockableDataManager.this.sessionManager.setConsultarAdhesionVendedorBodyResponseBean(consultarAdhesionVendedorResponseBean.getConsultarAdhesionVendedorBodyResponseBean());
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, consultarAdhesionVendedorEvent, VGetConsultarAdhesionVendedor.nameService);
            }
        };
        if (this.h.booleanValue()) {
            r1.parserResponse(b(R.string.mocks_consultarAdhesionVendedor, ConsultarAdhesionVendedorResponseBean.class));
        } else {
            r1.parserResponse(b(R.string.mocks_generic_result_1, ConsultarAdhesionVendedorResponseBean.class));
        }
    }

    public void getConstPlazoFijo(ConstPlazoFijoBodyRequestBean constPlazoFijoBodyRequestBean) {
        if (!mockEnabledServices.aD.booleanValue()) {
            super.getConstPlazoFijo(constPlazoFijoBodyRequestBean);
            return;
        }
        final ConstPlazoFijoEvent constPlazoFijoEvent = new ConstPlazoFijoEvent();
        AnonymousClass66 r0 = new ConstPlazoFijoRequest(super.createErrorRequestServer(constPlazoFijoEvent, TypeOption.INTERMDIATE_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                ConstPlazoFijoResponseBean constPlazoFijoResponseBean = (ConstPlazoFijoResponseBean) iBeanWS;
                constPlazoFijoEvent.setResponse(constPlazoFijoResponseBean);
                constPlazoFijoEvent.setBeanResponse(constPlazoFijoResponseBean);
                constPlazoFijoEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) constPlazoFijoEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, constPlazoFijoEvent, VConstPlazoFijo.nameService);
            }
        };
        if (this.h.booleanValue()) {
            r0.parserResponse(a((int) R.string.mocks_constPlazoFijo_simulacion, ConstPlazoFijoResponseBean.class));
            this.h = Boolean.valueOf(false);
            return;
        }
        r0.parserResponse(a((int) R.string.mocks_constPlazoFijo, ConstPlazoFijoResponseBean.class));
        this.h = Boolean.valueOf(true);
    }

    public void getConfirmarPago(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        if (!mockEnabledServices.aQ.booleanValue()) {
            super.getConfirmarPago(str, str2, str3, str4, str5, str6, str7, str8);
            return;
        }
        ConfirmarPagoEvent confirmarPagoEvent = new ConfirmarPagoEvent();
        ConfirmarPagoResponseBean confirmarPagoResponseBean = (ConfirmarPagoResponseBean) a((int) R.string.mocks_confirmar_pago_1_0_res_0, ConfirmarPagoResponseBean.class);
        confirmarPagoEvent.setResponse(confirmarPagoResponseBean);
        confirmarPagoEvent.setBeanResponse(confirmarPagoResponseBean);
        confirmarPagoEvent.setMessageToShow(confirmarPagoResponseBean.getmGeConfirmarPagoResponseBean().resDesc);
        confirmarPagoEvent.setTitleToShow(confirmarPagoResponseBean.getmGeConfirmarPagoResponseBean().resTitle);
        confirmarPagoEvent.setResult(TypeResult.OK);
        a((WebServiceEvent) confirmarPagoEvent, 1000);
    }

    public void getPreautorizaciones(String str, String str2, String str3) {
        if (!mockEnabledServices.c) {
            super.getPreautorizaciones(str, str2, str3);
            return;
        }
        GetPreAutorizacionesEvent getPreAutorizacionesEvent = new GetPreAutorizacionesEvent();
        GetPreAutorizacionesResponseBean getPreAutorizacionesResponseBean = (GetPreAutorizacionesResponseBean) a((int) R.string.mocks_get_preautorizaciones_1_1_res_4, GetPreAutorizacionesResponseBean.class);
        getPreAutorizacionesEvent.setBeanResponse(getPreAutorizacionesResponseBean);
        getPreAutorizacionesEvent.setMessageToShow(getPreAutorizacionesResponseBean.getPreAutorizacionesBodyResponseBean().resDesc);
        getPreAutorizacionesEvent.setTitleToShow(getPreAutorizacionesResponseBean.getPreAutorizacionesBodyResponseBean.resTitle);
        getPreAutorizacionesEvent.setResult(TypeResult.BEAN_ERROR_RES_3);
        a((WebServiceEvent) getPreAutorizacionesEvent, 1000);
    }

    public void genesysChat(String str, String str2) {
        if (!mockEnabledServices.aW.booleanValue()) {
            super.genesysChat(str, str2);
            return;
        }
        final GenesysChatEvent genesysChatEvent = new GenesysChatEvent();
        AnonymousClass67 r1 = new GenesysChatRequest(super.createErrorRequestServer(genesysChatEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                GenesysChatResponseBean genesysChatResponseBean = (GenesysChatResponseBean) iBeanWS;
                genesysChatEvent.setResponse(genesysChatResponseBean);
                genesysChatEvent.setResult(TypeResult.OK);
                genesysChatEvent.setMessageToShow(genesysChatResponseBean.getGenesysChatBodyResponseBean().resDesc);
                genesysChatEvent.setTitleToShow(genesysChatResponseBean.getGenesysChatBodyResponseBean().resTitle);
                MockableDataManager.this.a((WebServiceEvent) genesysChatEvent, 500);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, genesysChatEvent, VGenesysChat.nameService);
            }
        };
        if (str.equalsIgnoreCase("5")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_8_, GenesysChatResponseBean.class));
        } else if (str.equalsIgnoreCase("2") && str2.equalsIgnoreCase("res1")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_1_actualizado_indAccion_2, GenesysChatResponseBean.class));
        } else if (str.equalsIgnoreCase("2") && str2.equalsIgnoreCase("res3")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_3_actualizado_indAccion_2, GenesysChatResponseBean.class));
        } else if (str.equalsIgnoreCase("1")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_indAccion_1, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("2")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_2, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("3")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_3, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("4")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_4, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("1.1")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_1_1, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("1.2")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_1_2, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("1.3")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_1_3, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("1.4")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_1_4, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("1.5")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_1_5, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("1.6")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_1_6, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("1.7")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_1_7, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("1.8")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_1_8, GenesysChatResponseBean.class));
        } else if (str2.equalsIgnoreCase("plazo fijo")) {
            r1.parserResponse(b(R.string.mocks_Genesys_Chat_1_0_res_0_actualizado_mensaje_plazo_fijo, GenesysChatResponseBean.class));
        }
    }

    public void getDetallePreautorizacionComprador(String str, String str2) {
        if (!mockEnabledServices.f) {
            super.getDetallePreautorizacionComprador(str, str2);
            return;
        }
        GetDetallePreAutorizacionCompradorEvent getDetallePreAutorizacionCompradorEvent = new GetDetallePreAutorizacionCompradorEvent();
        GetDetalleDebinCompradorResponseBean getDetalleDebinCompradorResponseBean = (GetDetalleDebinCompradorResponseBean) a((int) R.string.mocks_get_detalle_preautorizacion_1_0_res_4, GetDetalleDebinCompradorResponseBean.class);
        getDetallePreAutorizacionCompradorEvent.setBeanResponse(getDetalleDebinCompradorResponseBean);
        getDetallePreAutorizacionCompradorEvent.setMessageToShow(getDetalleDebinCompradorResponseBean.getDetalleDebinCompradorBodyResponseBean().resDesc);
        getDetallePreAutorizacionCompradorEvent.setTitleToShow(getDetalleDebinCompradorResponseBean.getDetalleDebinCompradorBodyResponseBean().resTitle);
        getDetallePreAutorizacionCompradorEvent.setResult(TypeResult.BEAN_ERROR_RES_4);
        a((WebServiceEvent) getDetallePreAutorizacionCompradorEvent, 1000);
    }

    public void getFirmaCredin(Activity activity) {
        if (!mockEnabledServices.aN.booleanValue()) {
            super.getFirmaCredin(activity);
            return;
        }
        GetFirmaCredinEvent getFirmaCredinEvent = new GetFirmaCredinEvent();
        GetFirmaCredinResponseBean getFirmaCredinResponseBean = (GetFirmaCredinResponseBean) a((int) R.string.mocks_firma_credin_1_0_res_0_otros_datos, GetFirmaCredinResponseBean.class);
        getFirmaCredinEvent.setBeanResponse(getFirmaCredinResponseBean);
        getFirmaCredinEvent.setMessageToShow(getFirmaCredinResponseBean.getGetFirmaCredinBodyResponseBean().resDesc);
        getFirmaCredinEvent.setTitleToShow(getFirmaCredinResponseBean.getGetFirmaCredinBodyResponseBean().resTitle);
        getFirmaCredinEvent.setResult(TypeResult.BEAN_ERROR_RES_5);
        a((WebServiceEvent) getFirmaCredinEvent, 1000);
    }

    public void abmPreautorizacionComprador(String str, DetallePreAutorizacionBean detallePreAutorizacionBean, Activity activity, boolean z) {
        if (!mockEnabledServices.b) {
            super.abmPreautorizacionComprador(str, detallePreAutorizacionBean, activity, z);
            return;
        }
        AbmPreautorizacionCompradorEvent abmPreautorizacionCompradorEvent = new AbmPreautorizacionCompradorEvent();
        AbmPreautorizacionCompradorResponseBean abmPreautorizacionCompradorResponseBean = (AbmPreautorizacionCompradorResponseBean) a((int) R.string.mocks_abm_preautorizacion_comprador_1_0_res_4, AbmPreautorizacionCompradorResponseBean.class);
        abmPreautorizacionCompradorEvent.setBeanResponse(abmPreautorizacionCompradorResponseBean);
        abmPreautorizacionCompradorEvent.setMessageToShow(abmPreautorizacionCompradorResponseBean.getAbmPreautorizacionCompradorResponseBean().resDesc);
        abmPreautorizacionCompradorEvent.setTitleToShow(abmPreautorizacionCompradorResponseBean.getAbmPreautorizacionCompradorResponseBean().resTitle);
        abmPreautorizacionCompradorEvent.setResult(TypeResult.BEAN_ERROR_RES_5);
        a((WebServiceEvent) abmPreautorizacionCompradorEvent, 1000);
    }

    public void getFamiliasObjetos() {
        if (!mockEnabledServices.aK.booleanValue()) {
            super.getFamiliasObjetos();
            return;
        }
        final GetFamiliasObjetosEvent getFamiliasObjetosEvent = new GetFamiliasObjetosEvent();
        new GetFamiliasObjetosRequest(super.createErrorRequestServer(getFamiliasObjetosEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getFamiliasObjetosEvent.setResponse((GetFamiliasObjetosResponseBean) iBeanWS);
                getFamiliasObjetosEvent.setBeanResponse(getFamiliasObjetosEvent.getResponse());
                getFamiliasObjetosEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getFamiliasObjetosEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getFamiliasObjetosEvent, VGetFamiliasObjetos.nameService);
            }
        }.parserResponse(b(R.string.mocks_get_familias_objetos_1_0_res0_con_link_sugerencias, GetFamiliasObjetosResponseBean.class));
    }

    public void getCotizacionSeguroObjeto(String str, ListaPreguntasFamilia listaPreguntasFamilia) {
        a(str, "C", null, null, listaPreguntasFamilia);
    }

    public void getRecotizacionSeguroObjeto(String str, String str2, String str3) {
        a(str, "R", str2, str3, null);
    }

    private void a(String str, String str2, String str3, String str4, ListaPreguntasFamilia listaPreguntasFamilia) {
        if (!mockEnabledServices.aL.booleanValue()) {
            super.getCotizacionSeguroObjeto(str, listaPreguntasFamilia);
        } else if (!mockEnabledServices.aM.booleanValue()) {
            super.getRecotizacionSeguroObjeto(str, str3, str4);
        } else {
            final GetCotizacionSeguroObjetoEvent getCotizacionSeguroObjetoEvent = new GetCotizacionSeguroObjetoEvent();
            AnonymousClass69 r4 = new GetCotizacionSeguroObjetoRequest(super.createErrorRequestServer(getCotizacionSeguroObjetoEvent, TypeOption.INITIAL_VIEW)) {
                public void onResponseBean(IBeanWS iBeanWS) {
                    getCotizacionSeguroObjetoEvent.setGetCotizacionSeguroObjetoResponseBean((GetCotizacionSeguroObjetoResponseBean) iBeanWS);
                    getCotizacionSeguroObjetoEvent.setBeanResponse(getCotizacionSeguroObjetoEvent.getGetCotizacionSeguroObjetoResponseBean());
                    getCotizacionSeguroObjetoEvent.setResult(TypeResult.OK);
                    MockableDataManager.this.a((WebServiceEvent) getCotizacionSeguroObjetoEvent, 1000);
                }

                public void onUnknowError(Exception exc) {
                    MockableDataManager.super.createUnknowErrorEventProcesor(exc, getCotizacionSeguroObjetoEvent, VGetCotizacionSeguroObjeto.nameService);
                }
            };
            if (str2.equalsIgnoreCase("C")) {
                r4.parserResponse(b(R.string.mocks_getCotizacionSeguroObjeto, GetCotizacionSeguroObjetoResponseBean.class));
            } else {
                r4.parserResponse(b(R.string.mocks_getReCotizacionSeguroObjeto, GetCotizacionSeguroObjetoResponseBean.class));
            }
        }
    }

    public void getPreguntasFamilia(String str) {
        if (!mockEnabledServices.d) {
            super.getPreguntasFamilia(str);
            return;
        }
        final PreguntasFamiliaEvent preguntasFamiliaEvent = new PreguntasFamiliaEvent();
        new PreguntasFamiliaRequest(super.createErrorRequestServer(preguntasFamiliaEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                preguntasFamiliaEvent.setResponse((PreguntasFamiliaResponseBean) iBeanWS);
                preguntasFamiliaEvent.setBeanResponse(preguntasFamiliaEvent.getResponse());
                preguntasFamiliaEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) preguntasFamiliaEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, preguntasFamiliaEvent, VGetPreguntasFamilia.nameService);
            }
        }.parserResponse(b(R.string.mocks_getPreguntasFamilia_1, PreguntasFamiliaResponseBean.class));
    }

    public void contratarSeguroObjeto(ContratarSeguroObjetoBodyRequestBean contratarSeguroObjetoBodyRequestBean) {
        if (!mockEnabledServices.e) {
            super.contratarSeguroObjeto(contratarSeguroObjetoBodyRequestBean);
            return;
        }
        final ContratarSeguroObjetoEvent contratarSeguroObjetoEvent = new ContratarSeguroObjetoEvent();
        new ContratarSeguroObjetoRequest(super.createErrorRequestServer(contratarSeguroObjetoEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                contratarSeguroObjetoEvent.setResponse((ContratarSeguroObjetoResponseBean) iBeanWS);
                contratarSeguroObjetoEvent.setBeanResponse(contratarSeguroObjetoEvent.getResponse());
                contratarSeguroObjetoEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) contratarSeguroObjetoEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, contratarSeguroObjetoEvent, VContratarSeguroObjeto.nameService);
            }
        }.parserResponse(b(R.string.mocks_contratarSeguroObjeto_1, ContratarSeguroObjetoResponseBean.class));
    }

    public void getLimitesProductos() {
        if (!mockEnabledServices.g.booleanValue()) {
            super.getLimitesProductos();
            return;
        }
        final GetLimitesProductosEvent getLimitesProductosEvent = new GetLimitesProductosEvent();
        new GetLimitesProductosRequest(super.createErrorRequestServer(getLimitesProductosEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                getLimitesProductosEvent.setResponse((GetLimitesProductosResponseBean) iBeanWS);
                getLimitesProductosEvent.setBeanResponse(getLimitesProductosEvent.getResponse());
                getLimitesProductosEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) getLimitesProductosEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, getLimitesProductosEvent, VGetLimitesProductos.nameService);
            }
        }.parserResponse(b(R.string.mocks_GetLimitesProductos, GetLimitesProductosResponseBean.class));
    }

    public void confirmarRecalificacion(ListaProductosRecalificacionBean listaProductosRecalificacionBean, int i2) {
        if (!mockEnabledServices.i.booleanValue()) {
            super.confirmarRecalificacion(listaProductosRecalificacionBean, i2);
            return;
        }
        final ConfirmarRecalificacionEvent confirmarRecalificacionEvent = new ConfirmarRecalificacionEvent();
        new ConfirmarRecalificacionRequest(super.createErrorRequestServer(confirmarRecalificacionEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                confirmarRecalificacionEvent.setResponse((ConfirmarRecalificacionResponseBean) iBeanWS);
                confirmarRecalificacionEvent.setBeanResponse(confirmarRecalificacionEvent.getResponse());
                confirmarRecalificacionEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) confirmarRecalificacionEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, confirmarRecalificacionEvent, VGetLimitesProductos.nameService);
            }
        }.parserResponse(b(R.string.mocks_ConfirmarRecalificacion, ConfirmarRecalificacionResponseBean.class));
    }

    public void getTenenciaInversiones(String str) {
        if (!mockEnabledServices.aY.booleanValue()) {
            super.getTenenciaInversiones(str);
            return;
        }
        final TenenciaInversionesEvent tenenciaInversionesEvent = new TenenciaInversionesEvent();
        new TenenciaInversionesRequest(super.createErrorRequestServer(tenenciaInversionesEvent, TypeOption.INITIAL_VIEW)) {
            public void onResponseBean(IBeanWS iBeanWS) {
                tenenciaInversionesEvent.setResponse((TenenciaInversionesResponseBean) iBeanWS);
                tenenciaInversionesEvent.setBeanResponse(tenenciaInversionesEvent.getResponse());
                tenenciaInversionesEvent.setResult(TypeResult.OK);
                MockableDataManager.this.a((WebServiceEvent) tenenciaInversionesEvent, 1000);
            }

            public void onUnknowError(Exception exc) {
                MockableDataManager.super.createUnknowErrorEventProcesor(exc, tenenciaInversionesEvent, VTenenciaInversiones.nameService);
            }
        }.parserResponse(b(R.string.mocks_GetTenenciaInversiones_RES_0_AgrupadorCtasVacio_SoloPlazoFijo, TenenciaInversionesResponseBean.class));
    }

    private IBeanWS a(int i2, Class cls) {
        return UtilBeanXml.stringXmlToBean(this.e.getString(R.string.mocks_envelope_noheader, new Object[]{this.e.getString(i2)}), cls);
    }

    private IBeanWS a(String str, Class cls) {
        return UtilBeanXml.stringXmlToBean(this.e.getString(R.string.mocks_envelope_noheader, new Object[]{str}), cls);
    }

    /* access modifiers changed from: private */
    public JSONObject b(int i2, Class cls) {
        return UtilBeanXml.convertStringXmlToJsonObject(this.e.getString(R.string.mocks_envelope_noheader, new Object[]{this.e.getString(i2)}), cls);
    }

    /* access modifiers changed from: private */
    public void a(final WebServiceEvent webServiceEvent, long j) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MockableDataManager.this.sendBusEvent(webServiceEvent);
            }
        }, j);
    }

    /* access modifiers changed from: protected */
    public void sendBusEvent(final WebServiceEvent webServiceEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        BaseApplication baseApplication = this.e;
        if (BaseApplication.isActivityVisible()) {
            handler.post(new Runnable() {
                public void run() {
                    MockableDataManager.this.f.post(webServiceEvent);
                }
            });
            return;
        }
        BaseApplication baseApplication2 = this.e;
        BaseApplication.addPendingEvents(webServiceEvent);
    }
}
