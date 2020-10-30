package ar.com.santander.rio.mbanking.services.model.creditos;

import com.google.gson.annotations.SerializedName;

public class DatosSolicitudPrestamo {
    @SerializedName("IvaTasaAdicional")
    String IvaTasaAdicional;
    @SerializedName("IvaTasaGral")
    String IvaTasaGral;
    @SerializedName("tipoPolizaDS")
    String TipoPolizaDS;
    @SerializedName("cantCuotas")
    String cantCuotas;
    @SerializedName("capitalEinteres")
    String capitalEinteres;
    @SerializedName("codDivisa")
    String codDivisa;
    @SerializedName("codProdUG")
    String codProdUG;
    @SerializedName("codSubprodUG")
    String codSubprodUG;
    @SerializedName("comision")
    String comision;
    @SerializedName("cotizacionUVA")
    String cotizacionUVA;
    @SerializedName("cuotaPuraUVA")
    String cuotaPuraUVA;
    @SerializedName("destFondosComerc")
    String destFondosComerc;
    @SerializedName("destFondosUG")
    String destFondosUG;
    @SerializedName("destinoPrestSelect")
    String destinoPrestSelect;
    @SerializedName("esEmpleado")
    String esEmpleado;
    @SerializedName("fechaCotizaUVA")
    String fechaCotizaUVA;
    @SerializedName("fechaPrimerCuota")
    String fechaPrimerCuota;
    @SerializedName("fechaPrimerVenc")
    String fechaPrimerVenc;
    @SerializedName("gastos")
    String gastos;
    @SerializedName("gastosOtorgam")
    String gastosOtorgam;
    @SerializedName("importSolicitar")
    String importSolicitar;
    @SerializedName("importeCuota")
    String importeCuota;
    @SerializedName("importeCuotaUVA")
    String importeCuotaUVA;
    @SerializedName("importeNetoAcred")
    String importeNetoAcred;
    @SerializedName("importePrest")
    String importePrest;
    @SerializedName("importeSolUVA")
    String importeSolUVA;
    @SerializedName("indicadorUVA")
    String indicadorUVA;
    @SerializedName("iva")
    String iva;
    @SerializedName("leyendaCanc")
    String leyendaCanc;
    @SerializedName("leyendaConf")
    String leyendaConf;
    @SerializedName("moneda")
    String moneda;
    @SerializedName("nroCta")
    String nroCta;
    @SerializedName("nroComprobante")
    String numComprobante;
    @SerializedName("numPaquete")
    String numPaquete;
    @SerializedName("opcion")
    String opcion;
    @SerializedName("otrosImpCuota")
    String otrosImpCuota;
    @SerializedName("otrosImpuestos")
    String otrosImpuestos;
    @SerializedName("plazoPrest")
    String plazoPrest;
    @SerializedName("seguros")
    String seguros;
    @SerializedName("sucPaquete")
    String sucPaquete;
    @SerializedName("sucursalCta")
    String sucursalCta;
    @SerializedName("tasaCFTNA")
    String tasaCFTNA;
    @SerializedName("tasaCFTNAIVA")
    String tasaCFTNAIVA;
    @SerializedName("tasaEfectAnual")
    String tasaEfectAnual;
    @SerializedName("tasaNomAnual")
    String tasaNomAnual;
    @SerializedName("tipoCta")
    String tipoCta;
    @SerializedName("tipoRiesgoDS")
    String tipoRiesgoDS;
    @SerializedName("tipoTasa")
    String tipoTasa;
    @SerializedName("valorTasa")
    String valorTasa;

    public String getIndicadorUVA() {
        return this.indicadorUVA;
    }

    public void setIndicadorUVA(String str) {
        this.indicadorUVA = str;
    }

    public String getOpcion() {
        return this.opcion;
    }

    public void setOpcion(String str) {
        this.opcion = str;
    }

    public String getTipoCta() {
        return this.tipoCta;
    }

    public void setTipoCta(String str) {
        this.tipoCta = str;
    }

    public String getSucursalCta() {
        return this.sucursalCta;
    }

    public void setSucursalCta(String str) {
        this.sucursalCta = str;
    }

    public String getNroCta() {
        return this.nroCta;
    }

    public void setNroCta(String str) {
        this.nroCta = str;
    }

    public String getImportePrest() {
        return this.importePrest;
    }

    public void setImportePrest(String str) {
        this.importePrest = str;
    }

    public String getCantCuotas() {
        return this.cantCuotas;
    }

    public void setCantCuotas(String str) {
        this.cantCuotas = str;
    }

    public String getFechaPrimerCuota() {
        return this.fechaPrimerCuota;
    }

    public void setFechaPrimerCuota(String str) {
        this.fechaPrimerCuota = str;
    }

    public String getCodProdUG() {
        return this.codProdUG;
    }

    public void setCodProdUG(String str) {
        this.codProdUG = str;
    }

    public String getCodSubprodUG() {
        return this.codSubprodUG;
    }

    public void setCodSubprodUG(String str) {
        this.codSubprodUG = str;
    }

    public String getDestFondosUG() {
        return this.destFondosUG;
    }

    public void setDestFondosUG(String str) {
        this.destFondosUG = str;
    }

    public String getCodDivisa() {
        return this.codDivisa;
    }

    public void setCodDivisa(String str) {
        this.codDivisa = str;
    }

    public String getValorTasa() {
        return this.valorTasa;
    }

    public void setValorTasa(String str) {
        this.valorTasa = str;
    }

    public String getSucPaquete() {
        return this.sucPaquete;
    }

    public void setSucPaquete(String str) {
        this.sucPaquete = str;
    }

    public String getNumPaquete() {
        return this.numPaquete;
    }

    public void setNumPaquete(String str) {
        this.numPaquete = str;
    }

    public String getDestFondosComerc() {
        return this.destFondosComerc;
    }

    public void setDestFondosComerc(String str) {
        this.destFondosComerc = str;
    }

    public String getEsEmpleado() {
        return this.esEmpleado;
    }

    public void setEsEmpleado(String str) {
        this.esEmpleado = str;
    }

    public String getTipoRiesgoDS() {
        return this.tipoRiesgoDS;
    }

    public void setTipoRiesgoDS(String str) {
        this.tipoRiesgoDS = str;
    }

    public String getTipoPolizaDS() {
        return this.TipoPolizaDS;
    }

    public void setTipoPolizaDS(String str) {
        this.TipoPolizaDS = str;
    }

    public String getNumComprobante() {
        return this.numComprobante;
    }

    public void setNumComprobante(String str) {
        this.numComprobante = str;
    }

    public String getTasaNomAnual() {
        return this.tasaNomAnual;
    }

    public void setTasaNomAnual(String str) {
        this.tasaNomAnual = str;
    }

    public String getIvaTasaGral() {
        return this.IvaTasaGral;
    }

    public void setIvaTasaGral(String str) {
        this.IvaTasaGral = str;
    }

    public String getIvaTasaAdicional() {
        return this.IvaTasaAdicional;
    }

    public void setIvaTasaAdicional(String str) {
        this.IvaTasaAdicional = str;
    }

    public String getOtrosImpuestos() {
        return this.otrosImpuestos;
    }

    public void setOtrosImpuestos(String str) {
        this.otrosImpuestos = str;
    }

    public String getGastos() {
        return this.gastos;
    }

    public void setGastos(String str) {
        this.gastos = str;
    }

    public String getGastosOtorgam() {
        return this.gastosOtorgam;
    }

    public void setGastosOtorgam(String str) {
        this.gastosOtorgam = str;
    }

    public String getImporteNetoAcred() {
        return this.importeNetoAcred;
    }

    public void setImporteNetoAcred(String str) {
        this.importeNetoAcred = str;
    }

    public String getCapitalEinteres() {
        return this.capitalEinteres;
    }

    public void setCapitalEinteres(String str) {
        this.capitalEinteres = str;
    }

    public String getSeguros() {
        return this.seguros;
    }

    public void setSeguros(String str) {
        this.seguros = str;
    }

    public String getIva() {
        return this.iva;
    }

    public void setIva(String str) {
        this.iva = str;
    }

    public String getOtrosImpCuota() {
        return this.otrosImpCuota;
    }

    public void setOtrosImpCuota(String str) {
        this.otrosImpCuota = str;
    }

    public String getComision() {
        return this.comision;
    }

    public void setComision(String str) {
        this.comision = str;
    }

    public String getImporteCuota() {
        return this.importeCuota;
    }

    public void setImporteCuota(String str) {
        this.importeCuota = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getImportSolicitar() {
        return this.importSolicitar;
    }

    public void setImportSolicitar(String str) {
        this.importSolicitar = str;
    }

    public String getPlazoPrest() {
        return this.plazoPrest;
    }

    public void setPlazoPrest(String str) {
        this.plazoPrest = str;
    }

    public String getTipoTasa() {
        return this.tipoTasa;
    }

    public void setTipoTasa(String str) {
        this.tipoTasa = str;
    }

    public String getFechaPrimerVenc() {
        return this.fechaPrimerVenc;
    }

    public void setFechaPrimerVenc(String str) {
        this.fechaPrimerVenc = str;
    }

    public String getDestinoPrestSelect() {
        return this.destinoPrestSelect;
    }

    public void setDestinoPrestSelect(String str) {
        this.destinoPrestSelect = str;
    }

    public String getLeyendaConf() {
        return this.leyendaConf;
    }

    public void setLeyendaConf(String str) {
        this.leyendaConf = str;
    }

    public String getLeyendaCanc() {
        return this.leyendaCanc;
    }

    public void setLeyendaCanc(String str) {
        this.leyendaCanc = str;
    }

    public String getTasaCFTNA() {
        return this.tasaCFTNA;
    }

    public void setTasaCFTNA(String str) {
        this.tasaCFTNA = str;
    }

    public String getTasaCFTNAIVA() {
        return this.tasaCFTNAIVA;
    }

    public void setTasaCFTNAIVA(String str) {
        this.tasaCFTNAIVA = str;
    }

    public String getTasaEfectAnual() {
        return this.tasaEfectAnual;
    }

    public void setTasaEfectAnual(String str) {
        this.tasaEfectAnual = str;
    }

    public String getImporteSolUVA() {
        return this.importeSolUVA;
    }

    public void setImporteSolUVA(String str) {
        this.importeSolUVA = str;
    }

    public String getCuotaPuraUVA() {
        return this.cuotaPuraUVA;
    }

    public void setCuotaPuraUVA(String str) {
        this.cuotaPuraUVA = str;
    }

    public String getCotizacionUVA() {
        return this.cotizacionUVA;
    }

    public void setCotizacionUVA(String str) {
        this.cotizacionUVA = str;
    }

    public String getImporteCuotaUVA() {
        return this.importeCuotaUVA;
    }

    public void setImporteCuotaUVA(String str) {
        this.importeCuotaUVA = str;
    }

    public String getFechaCotizaUVA() {
        return this.fechaCotizaUVA;
    }

    public void setFechaCotizaUVA(String str) {
        this.fechaCotizaUVA = str;
    }
}
