package ar.com.santander.rio.mbanking.app.module.creditos.model;

public class SimulacionProd {
    public String cantidadCuotas;
    public String destino;
    public String importeMaxCredito;
    public String importeMinCredito;
    public String importeSolicitud;
    public String leyenda;
    public String primeraCuota;
    public String tasaNominalAnual;
    public String tipoTasa;

    public String getImporteSolicitud() {
        return this.importeSolicitud;
    }

    public void setImporteSolicitud(String str) {
        this.importeSolicitud = str;
    }

    public String getImporteMinCredito() {
        return this.importeMinCredito;
    }

    public void setImporteMinCredito(String str) {
        this.importeMinCredito = str;
    }

    public String getImporteMaxCredito() {
        return this.importeMaxCredito;
    }

    public void setImporteMaxCredito(String str) {
        this.importeMaxCredito = str;
    }

    public String getCantidadCuotas() {
        return this.cantidadCuotas;
    }

    public void setCantidadCuotas(String str) {
        this.cantidadCuotas = str;
    }

    public String getPrimeraCuota() {
        return this.primeraCuota;
    }

    public void setPrimeraCuota(String str) {
        this.primeraCuota = str;
    }

    public String getDestino() {
        return this.destino;
    }

    public void setDestino(String str) {
        this.destino = str;
    }

    public String getTipoTasa() {
        return this.tipoTasa;
    }

    public void setTipoTasa(String str) {
        this.tipoTasa = str;
    }

    public String getTasaNominalAnual() {
        return this.tasaNominalAnual;
    }

    public void setTasaNominalAnual(String str) {
        this.tasaNominalAnual = str;
    }

    public String getLeyenda() {
        return this.leyenda;
    }

    public void setLeyenda(String str) {
        this.leyenda = str;
    }
}
