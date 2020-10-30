package ar.com.santander.rio.mbanking.app.module.creditos;

import android.text.Html;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CCreditos;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Simulacion;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEvent;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitido;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamo;
import ar.com.santander.rio.mbanking.services.model.creditos.ListaDestino;
import ar.com.santander.rio.mbanking.services.model.creditos.ListaDestinoPrestamo;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBean;
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

public class SimulacionPresenterImp implements SimulacionPresenter {
    public static String DEFAULT_DESTINO_SELECTION = "Seleccionar";
    public static String FONDOS_COMERC = "9900";
    public static String PLACE_HOLDER_MAXIMPCUOTA = "\\{MAXIMPCUOTA\\}";
    public static String PLACE_HOLDER_MAXIMPORTE = "\\{MAXIMPORTE\\}";
    public static String PLACE_HOLDER_MINIMPORTE = "\\{MINIMPORTE\\}";
    SolicitudPrestamoPreacordadoBodyRequestBean a;
    ConsultaSolicitudCrediticiaBodyResponseBean b;
    ConsultaPrestamosPermitidosBodyResponseBean c;
    DatosPrestamoPermitido d;
    private CreditosView e;
    private Date f;
    private Date g;
    private AccountRequestBean h;
    private String i;
    private String j;
    private Date k;
    private String l;

    public String getDefaultDate() {
        return null;
    }

    public SimulacionPresenterImp(CreditosView creditosView) {
        this.e = creditosView;
    }

    public void onCreatePage(ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean, DatosPrestamoPermitido datosPrestamoPermitido) {
        this.e.setTitleLayout(this.e.getActContext().getResources().getString(R.string.ID138_CREDITS_LENDCONFIRM_LBL_SUPERLEND));
        this.b = consultaSolicitudCrediticiaBodyResponseBean;
        this.d = datosPrestamoPermitido;
        this.c = consultaPrestamosPermitidosBodyResponseBean;
        Simulacion simulacion = new Simulacion();
        simulacion.setImporteSolicitud("");
        CAmount cAmount = new CAmount(datosPrestamoPermitido.getMinValPrest());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        simulacion.setImporteMinCredito(cAmount.getAmount());
        CAmount cAmount2 = new CAmount(datosPrestamoPermitido.getMaxValPrest());
        cAmount2.setSymbolCurrencyDollarOrPeso(false);
        simulacion.setImporteMaxCredito(cAmount2.getAmount());
        this.j = Integer.valueOf(datosPrestamoPermitido.getMinCantCuotas()).toString();
        simulacion.setCantidadCuotas(this.j);
        simulacion.setPrimeraCuota(getDateDefault());
        this.i = DEFAULT_DESTINO_SELECTION;
        simulacion.setDestino(this.i);
        simulacion.setTipoTasa(CCreditos.formatTipoTasaVariableFija(datosPrestamoPermitido.getTipoTasa()));
        String replace = datosPrestamoPermitido.getValorTasa().replace(".", ",");
        StringBuilder sb = new StringBuilder();
        sb.append(replace);
        sb.append(Constants.SYMBOL_PERCENTAGE);
        simulacion.setTasaNominalAnual(sb.toString());
        simulacion.setLeyenda(a(Utils.formatIsbanHTMLCode(CCreditos.getLeyenda(consultaPrestamosPermitidosBodyResponseBean.getListaLeyenda(), "PRE_TYC_SOL")), datosPrestamoPermitido.getMinValPrest(), String.valueOf(cAmount2.getFormatDoubleAmount()), consultaSolicitudCrediticiaBodyResponseBean.getConsultaCalificacionCrediticia().getDatosCalificacionCrediticia().getImpDispCuota()));
        simulacion.setLeyendaTasa(CCreditos.getLeyendaTasa(consultaPrestamosPermitidosBodyResponseBean.getListaLeyenda(), Constants.LEYENDA_TASA_CRED_TNA, datosPrestamoPermitido.getValorTasa()));
        this.e.setSimulacionView(simulacion);
    }

    private String a(String str, String str2, String str3, String str4) {
        return str.replaceAll(PLACE_HOLDER_MINIMPORTE, str2).replaceAll(PLACE_HOLDER_MAXIMPORTE, str3).replaceAll(PLACE_HOLDER_MAXIMPCUOTA, str4).replaceAll("\n", "<br>");
    }

    public void setCuentaSelected(AccountRequestBean accountRequestBean) {
        this.h = accountRequestBean;
    }

    public String getDateDefault() {
        Calendar instance = Calendar.getInstance();
        instance.add(5, instance.getActualMaximum(5));
        this.g = instance.getTime();
        this.f = this.g;
        Calendar instance2 = Calendar.getInstance();
        instance2.add(5, 90);
        this.k = instance2.getTime();
        return new DateTime((Object) instance.getTime()).toString(this.e.getActContext().getString(R.string.FORMAT_SHORT_DATE));
    }

    private String a() {
        return new DateTime((Object) this.f).toString(this.e.getActContext().getString(R.string.WS_FORMAT_SHORT_DATE));
    }

    public void sendRequestSimulacionPrestamoPermitido() {
        this.e.showProgress();
        this.a = new SolicitudPrestamoPreacordadoBodyRequestBean();
        DatosSolicitudPrestamo datosSolicitudPrestamo = new DatosSolicitudPrestamo();
        datosSolicitudPrestamo.setOpcion(CreditosConstants.SIMULACION);
        datosSolicitudPrestamo.setImportePrest(this.l);
        datosSolicitudPrestamo.setCantCuotas(Integer.valueOf(this.j).toString());
        datosSolicitudPrestamo.setIndicadorUVA(this.d.getIndicadorUVA());
        datosSolicitudPrestamo.setFechaPrimerCuota(a());
        datosSolicitudPrestamo.setTipoTasa(this.d.getTipoTasa());
        datosSolicitudPrestamo.setCodProdUG(this.d.getCodProdUG());
        datosSolicitudPrestamo.setCodSubprodUG(this.d.getCodSubprodUG());
        datosSolicitudPrestamo.setDestFondosUG(CCreditos.getDestino(this.d.getListaDestinoPrestamo().getListaDestinoPrest(), this.i));
        datosSolicitudPrestamo.setDestFondosComerc(this.d.getDestFondosComerc());
        datosSolicitudPrestamo.setCodDivisa(this.c.getDatosPrestamos().getValorDivisa());
        datosSolicitudPrestamo.setValorTasa(this.d.getValorTasa());
        datosSolicitudPrestamo.setEsEmpleado(this.d.getMarcaEmpl());
        datosSolicitudPrestamo.setTipoRiesgoDS(this.d.getTipoRiesgoDs());
        datosSolicitudPrestamo.setTipoPolizaDS(this.d.getTipoPolizaDs());
        datosSolicitudPrestamo.setTipoCta(this.c.getDatosCuenta().tipo);
        datosSolicitudPrestamo.setSucursalCta(this.b.getConsultaCalificacionCrediticia().getAccountResponseBean().sucursal);
        datosSolicitudPrestamo.setNroCta(this.b.getConsultaCalificacionCrediticia().getAccountResponseBean().numero);
        datosSolicitudPrestamo.setSucPaquete(this.h.sucursalPaq);
        datosSolicitudPrestamo.setNumPaquete(this.h.nroPaq);
        this.a.setDatosSolicitudPrestamo(datosSolicitudPrestamo);
        this.e.setSolicitudSimulacion(this.a);
        this.e.getDataManager().solicitudPrestamoPreacordado(this.a, this.e.getActivity());
    }

    public void getResponseSimulacionPrestamoPermitido(SolicitudPrestamoPreacordadoEvent solicitudPrestamoPreacordadoEvent, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        this.e.goToConfirmacion(((SolicitudPrestamoPreacordadoResponseBean) solicitudPrestamoPreacordadoEvent.getBeanResponse()).solicitudPrestamoPreacordadoBodyResponseBean.getDatosSolicitudPrestamo(), consultaPrestamosPermitidosBodyResponseBean);
        this.e.dismisProgress();
    }

    public void onCantidadCuotasClicked() {
        int intValue = Integer.valueOf(this.d.getMaxCantCuotas()).intValue();
        ArrayList arrayList = new ArrayList();
        for (int intValue2 = Integer.valueOf(this.d.getMinCantCuotas()).intValue(); intValue2 <= intValue; intValue2++) {
            arrayList.add(String.valueOf(intValue2));
        }
        this.e.showCantidadCuotaPicker(arrayList, this.j);
    }

    public void onPrimeraCuotaClicked() {
        this.e.showPrimeraCuotaPicker();
    }

    public void onDestinosClicked() {
        ListaDestinoPrestamo listaDestinoPrestamo = this.d.getListaDestinoPrestamo();
        List<ListaDestino> listaDestinoPrest = listaDestinoPrestamo != null ? listaDestinoPrestamo.getListaDestinoPrest() : null;
        ArrayList arrayList = new ArrayList();
        if (listaDestinoPrest != null) {
            for (ListaDestino destPrestamo : listaDestinoPrest) {
                arrayList.add(Html.fromHtml(destPrestamo.getDestPrestamo()).toString());
            }
        }
        this.e.showDestinoPicker(arrayList, this.i);
    }

    public void validate(String str) {
        double amount = UtilAmount.getAmount(str);
        this.l = String.valueOf((int) amount);
        if (amount == 0.0d) {
            this.e.showMessage(this.e.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.e.getActContext().getResources().getString(R.string.MSG_USER000050));
            this.e.clearImporte();
            return;
        }
        CAmount cAmount = new CAmount(this.b.getConsultaCalificacionCrediticia().getDatosCalificacionCrediticia().getImpDispPrest());
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
        } else if (this.f.after(this.k)) {
            this.e.showMessage(this.e.getActContext().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.e.getActContext().getResources().getString(R.string.MSG_USER0000XX_CREDITOS_90_DIAS));
        } else if (this.i.equalsIgnoreCase(DEFAULT_DESTINO_SELECTION)) {
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
        this.j = str;
    }

    public void onDestinoSelected(String str) {
        this.i = str;
    }

    public String getDestinoSelected() {
        return this.i;
    }

    public View getViewForm(Simulacion simulacion) {
        TableView tableView = new TableView(this.e.getActContext());
        tableView.addRowView(RowViewCreatorCreditos.getRowImporteSolicitado(this.e.getActContext(), "0"));
        tableView.addRowView(RowViewCreatorCreditos.getRowImporteMinimo(this.e.getActContext(), simulacion.getImporteMinCredito(), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreatorCreditos.getRowImporteMaximo(this.e.getActContext(), simulacion.getImporteMaxCredito(), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreatorCreditos.getRowCantidadCuotas(this.e.getActContext(), simulacion.getCantidadCuotas()));
        tableView.addRowView(RowViewCreatorCreditos.getRowFechaPrimeraCuota(this.e.getActContext(), UtilDate.getDateFormat(simulacion.getPrimeraCuota(), Constants.FORMAT_DATE_APP, Constants.FORMAT_DATE_APP_2)));
        tableView.addRowView(RowViewCreatorCreditos.getRowDestino(this.e.getActContext(), simulacion.getDestino()));
        tableView.addRowView(RowViewCreatorCreditos.getRowTipoTasa(this.e.getActContext(), simulacion.getTipoTasa(), TypeStyle.TYPE_STYLE_LABEL));
        tableView.addRowView(RowViewCreatorCreditos.getRowTasaNominal(this.e.getActContext(), simulacion.getTasaNominalAnual(), TypeStyle.TYPE_STYLE_LABEL));
        return tableView;
    }
}
