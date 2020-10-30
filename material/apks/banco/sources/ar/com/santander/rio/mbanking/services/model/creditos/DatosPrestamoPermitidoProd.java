package ar.com.santander.rio.mbanking.services.model.creditos;

import com.google.gson.annotations.SerializedName;

public class DatosPrestamoPermitidoProd {
    @SerializedName("codProdUG")
    String codProdUG;
    @SerializedName("codSubprodUG")
    String codSubprodUG;
    @SerializedName("codTasaUg")
    String codTasaUg;
    @SerializedName("listaDestinoPrest")
    ListaDestinoPrestamo listaDestinoPrestamo;
    @SerializedName("marPlanSueldoBg")
    String marPlanSueldoBg;
    @SerializedName("marcaEmpl")
    String marcaEmpl;
    @SerializedName("maxCantCuotas")
    String maxCantCuotas;
    @SerializedName("maxValPrest")
    String maxValPrest;
    @SerializedName("minCantCuotas")
    String minCantCuotas;
    @SerializedName("minValPrest")
    String minValPrest;
    @SerializedName("tipoPolizaDs")
    String tipoPolizaDs;
    @SerializedName("tipoRiesgoDs")
    String tipoRiesgoDs;
    @SerializedName("tipoTasa")
    String tipoTasa;
    @SerializedName("valorTasa")
    String valorTasa;

    public ListaDestinoPrestamo getListaDestinoPrestamo() {
        return this.listaDestinoPrestamo;
    }

    public void setListaDestinoPrestamo(ListaDestinoPrestamo listaDestinoPrestamo2) {
        this.listaDestinoPrestamo = listaDestinoPrestamo2;
    }

    public String getMinCantCuotas() {
        return this.minCantCuotas;
    }

    public void setMinCantCuotas(String str) {
        this.minCantCuotas = str;
    }

    public String getMaxCantCuotas() {
        return this.maxCantCuotas;
    }

    public void setMaxCantCuotas(String str) {
        this.maxCantCuotas = str;
    }

    public String getTipoTasa() {
        return this.tipoTasa;
    }

    public void setTipoTasa(String str) {
        this.tipoTasa = str;
    }

    public String getValorTasa() {
        return this.valorTasa;
    }

    public void setValorTasa(String str) {
        this.valorTasa = str;
    }

    public String getMinValPrest() {
        return this.minValPrest;
    }

    public void setMinValPrest(String str) {
        this.minValPrest = str;
    }

    public String getMaxValPrest() {
        return this.maxValPrest;
    }

    public void setMaxValPrest(String str) {
        this.maxValPrest = str;
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

    public String getMarcaEmpl() {
        return this.marcaEmpl;
    }

    public void setMarcaEmpl(String str) {
        this.marcaEmpl = str;
    }

    public String getTipoPolizaDs() {
        return this.tipoPolizaDs;
    }

    public void setTipoPolizaDs(String str) {
        this.tipoPolizaDs = str;
    }

    public String getTipoRiesgoDs() {
        return this.tipoRiesgoDs;
    }

    public void setTipoRiesgoDs(String str) {
        this.tipoRiesgoDs = str;
    }

    public String getCodTasaUg() {
        return this.codTasaUg;
    }

    public void setCodTasaUg(String str) {
        this.codTasaUg = str;
    }

    public String getMarPlanSueldoBg() {
        return this.marPlanSueldoBg;
    }

    public void setMarPlanSueldoBg(String str) {
        this.marPlanSueldoBg = str;
    }
}
