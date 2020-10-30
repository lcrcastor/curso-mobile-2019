package ar.com.santander.rio.mbanking.app.module.creditos;

import android.text.Html;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CCreditosProd;
import ar.com.santander.rio.mbanking.app.module.creditos.model.SimulacionProd;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEventProd;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitidoProd;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamoProd;
import ar.com.santander.rio.mbanking.services.model.creditos.ListaDestino;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.utils.UtilAmount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.TableView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.joda.time.DateTime;

public class SimulacionPresenterProdImp implements SimulacionPresenterProd {
    public static String DEFAULT_DESTINO_SELECTION = "Seleccionar";
    public static String FONDOS_COMERC = "9900";
    public static String PLACE_HOLDER_MAXIMPCUOTA = "\\{MAXIMPCUOTA\\}";
    public static String PLACE_HOLDER_MAXIMPORTE = "\\{MAXIMPORTE\\}";
    public static String PLACE_HOLDER_MINIMPORTE = "\\{MINIMPORTE\\}";
    SolicitudPrestamoPreacordadoBodyRequestBeanProd a;
    ConsultaSolicitudCrediticiaBodyResponseBeanProd b;
    ConsultaPrestamosPermitidosBodyResponseBeanProd c;
    DatosPrestamoPermitidoProd d;
    private CreditosViewProd e;
    private Date f;
    private Date g;
    private String h;
    private String i;
    private Date j;
    private String k;

    public String getDefaultDate() {
        return null;
    }

    public SimulacionPresenterProdImp(CreditosViewProd creditosViewProd) {
        this.e = creditosViewProd;
    }

    public void onCreatePage(ConsultaSolicitudCrediticiaBodyResponseBeanProd consultaSolicitudCrediticiaBodyResponseBeanProd, ConsultaPrestamosPermitidosBodyResponseBeanProd consultaPrestamosPermitidosBodyResponseBeanProd, DatosPrestamoPermitidoProd datosPrestamoPermitidoProd) {
        this.e.setTitleLayout(this.e.getActContext().getResources().getString(R.string.ID138_CREDITS_LENDCONFIRM_LBL_SUPERLEND));
        this.b = consultaSolicitudCrediticiaBodyResponseBeanProd;
        this.d = datosPrestamoPermitidoProd;
        this.c = consultaPrestamosPermitidosBodyResponseBeanProd;
        SimulacionProd simulacionProd = new SimulacionProd();
        simulacionProd.setImporteSolicitud("");
        CAmount cAmount = new CAmount(datosPrestamoPermitidoProd.getMinValPrest());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        simulacionProd.setImporteMinCredito(cAmount.getAmount());
        CAmount cAmount2 = new CAmount(datosPrestamoPermitidoProd.getMaxValPrest());
        cAmount2.setSymbolCurrencyDollarOrPeso(false);
        simulacionProd.setImporteMaxCredito(cAmount2.getAmount());
        this.i = Integer.valueOf(datosPrestamoPermitidoProd.getMinCantCuotas()).toString();
        simulacionProd.setCantidadCuotas(this.i);
        simulacionProd.setPrimeraCuota(getDateDefault());
        this.h = DEFAULT_DESTINO_SELECTION;
        simulacionProd.setDestino(this.h);
        simulacionProd.setTipoTasa(CCreditosProd.formatTipoTasa(datosPrestamoPermitidoProd.getTipoTasa()));
        CAmount cAmount3 = new CAmount(datosPrestamoPermitidoProd.getValorTasa(), "#,##0.000");
        StringBuilder sb = new StringBuilder();
        sb.append(cAmount3.getAmount());
        sb.append(Constants.SYMBOL_PERCENTAGE);
        simulacionProd.setTasaNominalAnual(sb.toString());
        simulacionProd.setLeyenda(a(Utils.formatIsbanHTMLCode(CCreditosProd.getLeyenda(consultaPrestamosPermitidosBodyResponseBeanProd.getListaLeyendaProd(), "PRE_TYC_SOL")), datosPrestamoPermitidoProd.getMinValPrest(), String.valueOf(cAmount2.getFormatDoubleAmount()), consultaSolicitudCrediticiaBodyResponseBeanProd.getConsultaCalificacionCrediticiaProd().getDatosCalificacionCrediticiaProd().getImpDispCuota()));
        this.e.setSimulacionProdView(simulacionProd);
    }

    private String a(String str, String str2, String str3, String str4) {
        return str.replaceAll(PLACE_HOLDER_MINIMPORTE, str2).replaceAll(PLACE_HOLDER_MAXIMPORTE, str3).replaceAll(PLACE_HOLDER_MAXIMPCUOTA, str4).replaceAll("\n", "<br>");
    }

    public String getDateDefault() {
        Calendar instance = Calendar.getInstance();
        instance.add(5, instance.getActualMaximum(5));
        this.g = instance.getTime();
        this.f = this.g;
        Calendar instance2 = Calendar.getInstance();
        instance2.add(5, 90);
        this.j = instance2.getTime();
        return new DateTime((Object) instance.getTime()).toString(this.e.getActContext().getString(R.string.FORMAT_SHORT_DATE));
    }

    private String a() {
        return new DateTime((Object) this.f).toString(this.e.getActContext().getString(R.string.WS_FORMAT_SHORT_DATE));
    }

    public void sendRequestSimulacionPrestamoPermitido() {
        this.e.showProgress();
        this.a = new SolicitudPrestamoPreacordadoBodyRequestBeanProd();
        DatosSolicitudPrestamoProd datosSolicitudPrestamoProd = new DatosSolicitudPrestamoProd();
        datosSolicitudPrestamoProd.setOpcion(CreditosConstantsProd.SIMULACION);
        datosSolicitudPrestamoProd.setImportePrest(this.k);
        datosSolicitudPrestamoProd.setCantCuotas(Integer.valueOf(this.i).toString());
        datosSolicitudPrestamoProd.setFechaPrimerCuota(a());
        datosSolicitudPrestamoProd.setTipoTasa(this.d.getTipoTasa());
        datosSolicitudPrestamoProd.setCodProdUG(this.d.getCodProdUG());
        datosSolicitudPrestamoProd.setCodSubprodUG(this.d.getCodSubprodUG());
        datosSolicitudPrestamoProd.setDestFondosUG(CCreditosProd.getDestino(this.d.getListaDestinoPrestamo().getListaDestinoPrest(), this.h));
        datosSolicitudPrestamoProd.setDestFondosComerc(FONDOS_COMERC);
        datosSolicitudPrestamoProd.setCodDivisa(this.c.getDatosPrestamosProd().getValorDivisa());
        datosSolicitudPrestamoProd.setValorTasa(this.d.getValorTasa());
        datosSolicitudPrestamoProd.setEsEmpleado(this.d.getMarcaEmpl());
        datosSolicitudPrestamoProd.setTipoRiesgoDS(this.d.getTipoRiesgoDs());
        datosSolicitudPrestamoProd.setTipoPolizaDS(this.d.getTipoPolizaDs());
        datosSolicitudPrestamoProd.setTipoCta(this.c.getDatosCuenta().tipo);
        datosSolicitudPrestamoProd.setSucursalCta(this.b.getConsultaCalificacionCrediticiaProd().getAccountResponseBean().sucursal);
        datosSolicitudPrestamoProd.setNroCta(this.b.getConsultaCalificacionCrediticiaProd().getAccountResponseBean().numero);
        datosSolicitudPrestamoProd.setSucPaquete(this.e.getCuenta().getSucursalPaq());
        datosSolicitudPrestamoProd.setNumPaquete(this.e.getCuenta().getNroPaq());
        this.a.setDatosSolicitudPrestamoProd(datosSolicitudPrestamoProd);
        this.e.setSolicitudSimulacion(this.a);
        this.e.getDataManager().solicitudPrestamoPreacordadoProd(this.a, this.e.getActivity());
    }

    public void getResponseSimulacionPrestamoPermitido(SolicitudPrestamoPreacordadoEventProd solicitudPrestamoPreacordadoEventProd) {
        this.e.goToConfirmacion(((SolicitudPrestamoPreacordadoResponseBeanProd) solicitudPrestamoPreacordadoEventProd.getBeanResponse()).solicitudPrestamoPreacordadoBodyResponseBeanProd.getDatosSolicitudPrestamoProd());
        this.e.dismisProgress();
    }

    public void onCantidadCuotasClicked() {
        int intValue = Integer.valueOf(this.d.getMaxCantCuotas()).intValue();
        ArrayList arrayList = new ArrayList();
        for (int intValue2 = Integer.valueOf(this.d.getMinCantCuotas()).intValue(); intValue2 <= intValue; intValue2++) {
            arrayList.add(String.valueOf(intValue2));
        }
        this.e.showCantidadCuotaPicker(arrayList, this.i);
    }

    public void onPrimeraCuotaClicked() {
        this.e.showPrimeraCuotaPicker();
    }

    public void onDestinosClicked() {
        List<ListaDestino> listaDestinoPrest = this.d.getListaDestinoPrestamo().getListaDestinoPrest();
        ArrayList arrayList = new ArrayList();
        if (listaDestinoPrest != null) {
            for (ListaDestino destPrestamo : listaDestinoPrest) {
                arrayList.add(Html.fromHtml(destPrestamo.getDestPrestamo()).toString());
            }
        }
        this.e.showDestinoPicker(arrayList, this.h);
    }

    public void validate(String str) {
        double amount = UtilAmount.getAmount(str);
        this.k = String.valueOf((int) amount);
        if (amount == 0.0d) {
            this.e.showMessage(this.e.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), this.e.getActContext().getResources().getString(R.string.MSG_USER000050));
            this.e.clearImporte();
            return;
        }
        CAmount cAmount = new CAmount(this.b.getConsultaCalificacionCrediticiaProd().getDatosCalificacionCrediticiaProd().getImpDispPrest());
        CAmount cAmount2 = new CAmount(this.d.getMinValPrest());
        CAmount cAmount3 = new CAmount(this.d.getMaxValPrest());
        Double valueOf = Double.valueOf(cAmount.getFormatDoubleAmount());
        if (cAmount3.getFormatDoubleAmount() < valueOf.doubleValue()) {
            valueOf = Double.valueOf(cAmount3.getFormatDoubleAmount());
        }
        if (amount > valueOf.doubleValue()) {
            this.e.showMessage(this.e.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.e.getActContext().getResources().getString(R.string.MSG_USER000041_Prestamos_errorMontoSuperior));
            this.e.clearImporte();
        } else if (amount < cAmount2.getFormatDoubleAmount()) {
            this.e.showMessage(this.e.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.e.getActContext().getResources().getString(R.string.MSG_USER000040_Prestamos_errorMontoInferior));
            this.e.clearImporte();
        } else if (amount > cAmount3.getFormatDoubleAmount()) {
            this.e.showMessage(this.e.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.e.getActContext().getResources().getString(R.string.MSG_USER000041_Prestamos_errorMontoSuperior));
            this.e.clearImporte();
        } else if (this.f.before(this.g)) {
            this.e.showMessage(this.e.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.e.getActContext().getResources().getString(R.string.MSG_USER000042_Prestamos_errorFechaVencimiento));
        } else if (this.f.after(this.j)) {
            this.e.showMessage(this.e.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.e.getActContext().getResources().getString(R.string.MSG_USER0000XX_CREDITOS_90_DIAS));
        } else if (this.h.equalsIgnoreCase(DEFAULT_DESTINO_SELECTION)) {
            this.e.showMessage(this.e.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.e.getActContext().getResources().getString(R.string.MSG_USER000051));
        } else {
            sendRequestSimulacionPrestamoPermitido();
        }
    }

    public void onPrimeraCuotaSelected(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.setTime(this.g);
        if (gregorianCalendar.get(6) == gregorianCalendar2.get(6)) {
            this.f = this.g;
        } else {
            this.f = date;
        }
        this.e.setPrimeraCuota(new DateTime((Object) this.f).toString(this.e.getActContext().getString(R.string.FORMAT_SHORT_DATE_FULL_YEAR)));
    }

    public void onNumCuotasSelected(String str) {
        this.i = str;
    }

    public void onDestinoSelected(String str) {
        this.h = str;
    }

    public View getViewForm(SimulacionProd simulacionProd) {
        TableView tableView = new TableView(this.e.getActContext());
        tableView.addRowView(RowViewCreatorCreditosProd.getRowImporteSolicitado(this.e.getActContext(), "0"));
        tableView.addRowView(RowViewCreatorCreditosProd.getRowImporteMinimo(this.e.getActContext(), simulacionProd.getImporteMinCredito(), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreatorCreditosProd.getRowImporteMaximo(this.e.getActContext(), simulacionProd.getImporteMaxCredito(), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreatorCreditosProd.getRowCantidadCuotas(this.e.getActContext(), simulacionProd.getCantidadCuotas()));
        tableView.addRowView(RowViewCreatorCreditosProd.getRowFechaPrimeraCuota(this.e.getActContext(), UtilDate.getDateFormat(simulacionProd.getPrimeraCuota(), Constants.FORMAT_DATE_APP, Constants.FORMAT_DATE_APP_2)));
        tableView.addRowView(RowViewCreatorCreditosProd.getRowDestino(this.e.getActContext(), simulacionProd.getDestino()));
        tableView.addRowView(RowViewCreatorCreditosProd.getRowTipoTasa(this.e.getActContext(), simulacionProd.getTipoTasa(), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreatorCreditosProd.getRowTasaNominal(this.e.getActContext(), simulacionProd.getTasaNominalAnual(), TypeStyle.TYPE_STYLE_LABEL));
        return tableView;
    }
}
