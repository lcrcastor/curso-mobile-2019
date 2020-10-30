package ar.com.santander.rio.mbanking.services.model.creditos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.app.commons.CCreditos;
import com.google.common.collect.ComparisonChain;
import com.google.gson.annotations.SerializedName;
import java.util.Comparator;

public class DatosPrestamoPermitido implements Parcelable {
    public static final Creator<DatosPrestamoPermitido> CREATOR = new Creator<DatosPrestamoPermitido>() {
        public DatosPrestamoPermitido createFromParcel(Parcel parcel) {
            return new DatosPrestamoPermitido(parcel);
        }

        public DatosPrestamoPermitido[] newArray(int i) {
            return new DatosPrestamoPermitido[i];
        }
    };
    @SerializedName("codProdUG")
    String codProdUG;
    @SerializedName("codSubprodUG")
    String codSubprodUG;
    @SerializedName("codTasaUg")
    String codTasaUg;
    @SerializedName("destFondosComerc")
    String destFondosComerc;
    @SerializedName("indicadorUVA")
    String indicadorUVA;
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

    public static class ComparatorTipoPrestamo implements Comparator<DatosPrestamoPermitido> {
        public int compare(DatosPrestamoPermitido datosPrestamoPermitido, DatosPrestamoPermitido datosPrestamoPermitido2) {
            String formatTipoTasa = CCreditos.formatTipoTasa(datosPrestamoPermitido.getTipoTasa(), datosPrestamoPermitido.getIndicadorUVA());
            String formatTipoTasa2 = CCreditos.formatTipoTasa(datosPrestamoPermitido2.getTipoTasa(), datosPrestamoPermitido2.getIndicadorUVA());
            Integer valueOf = Integer.valueOf(Integer.parseInt(datosPrestamoPermitido.getMinCantCuotas()));
            Integer valueOf2 = Integer.valueOf(Integer.parseInt(datosPrestamoPermitido2.getMinCantCuotas()));
            Integer valueOf3 = Integer.valueOf(Integer.parseInt(datosPrestamoPermitido.getMaxCantCuotas()));
            Integer valueOf4 = Integer.valueOf(Integer.parseInt(datosPrestamoPermitido2.getMaxCantCuotas()));
            if (formatTipoTasa == null || formatTipoTasa2 == null) {
                return ComparisonChain.start().compare((Comparable<?>) valueOf, (Comparable<?>) valueOf2).compare((Comparable<?>) valueOf3, (Comparable<?>) valueOf4).result();
            }
            return ComparisonChain.start().compare((Comparable<?>) formatTipoTasa, (Comparable<?>) formatTipoTasa2).compare((Comparable<?>) valueOf, (Comparable<?>) valueOf2).compare((Comparable<?>) valueOf3, (Comparable<?>) valueOf4).result();
        }
    }

    public int describeContents() {
        return 0;
    }

    protected DatosPrestamoPermitido(Parcel parcel) {
        this.minCantCuotas = parcel.readString();
        this.maxCantCuotas = parcel.readString();
        this.tipoTasa = parcel.readString();
        this.valorTasa = parcel.readString();
        this.minValPrest = parcel.readString();
        this.maxValPrest = parcel.readString();
        this.codProdUG = parcel.readString();
        this.codSubprodUG = parcel.readString();
        this.marcaEmpl = parcel.readString();
        this.tipoPolizaDs = parcel.readString();
        this.tipoRiesgoDs = parcel.readString();
        this.codTasaUg = parcel.readString();
        this.marPlanSueldoBg = parcel.readString();
        this.indicadorUVA = parcel.readString();
        this.destFondosComerc = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.minCantCuotas);
        parcel.writeString(this.maxCantCuotas);
        parcel.writeString(this.tipoTasa);
        parcel.writeString(this.valorTasa);
        parcel.writeString(this.minValPrest);
        parcel.writeString(this.maxValPrest);
        parcel.writeString(this.codProdUG);
        parcel.writeString(this.codSubprodUG);
        parcel.writeString(this.marcaEmpl);
        parcel.writeString(this.tipoPolizaDs);
        parcel.writeString(this.tipoRiesgoDs);
        parcel.writeString(this.codTasaUg);
        parcel.writeString(this.marPlanSueldoBg);
        parcel.writeString(this.indicadorUVA);
        parcel.writeString(this.destFondosComerc);
    }

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

    public String getIndicadorUVA() {
        return this.indicadorUVA;
    }

    public void setIndicadorUVA(String str) {
        this.indicadorUVA = str;
    }

    public void setMarPlanSueldoBg(String str) {
        this.marPlanSueldoBg = str;
    }

    public String getDestFondosComerc() {
        return this.destFondosComerc;
    }

    public void setDestFondosComerc(String str) {
        this.destFondosComerc = str;
    }
}
