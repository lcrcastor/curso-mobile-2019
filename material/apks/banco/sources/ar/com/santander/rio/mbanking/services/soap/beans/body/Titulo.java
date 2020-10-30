package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.List;

public class Titulo implements Parcelable {
    public static final Creator<Titulo> CREATOR = new Creator<Titulo>() {
        public Titulo createFromParcel(Parcel parcel) {
            return new Titulo(parcel);
        }

        public Titulo[] newArray(int i) {
            return new Titulo[i];
        }
    };
    private String amortCobradas;
    private String cantValorNominal;
    private String codEspecie;
    private String descripcion;
    private String divCobrados;
    private String estado;
    private String moneda;
    private String precioCompra;
    private String precioMercado;
    private String rentasCobradas;
    private String resultado;
    private String tenValHoy;
    private String tipoEspecie;
    private String ultCotizacion;
    private String valuacionCosto;

    public int describeContents() {
        return 0;
    }

    protected Titulo(Parcel parcel) {
        this.descripcion = parcel.readString();
        this.moneda = parcel.readString();
        this.codEspecie = parcel.readString();
        this.tipoEspecie = parcel.readString();
        this.cantValorNominal = parcel.readString();
        this.precioMercado = parcel.readString();
        this.ultCotizacion = parcel.readString();
        this.tenValHoy = parcel.readString();
        this.precioCompra = parcel.readString();
        this.valuacionCosto = parcel.readString();
        this.rentasCobradas = parcel.readString();
        this.amortCobradas = parcel.readString();
        this.divCobrados = parcel.readString();
        this.resultado = parcel.readString();
        this.estado = parcel.readString();
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getCodEspecie() {
        if (TextUtils.isEmpty(this.codEspecie)) {
            return "";
        }
        return this.codEspecie;
    }

    public void setCodEspecie(String str) {
        this.codEspecie = str;
    }

    public String getTipoEspecie() {
        return this.tipoEspecie;
    }

    public void setTipoEspecie(String str) {
        this.tipoEspecie = str;
    }

    public String getCantValorNominal() {
        return this.cantValorNominal;
    }

    public void setCantValorNominal(String str) {
        this.cantValorNominal = str;
    }

    public String getPrecioMercado() {
        return this.precioMercado;
    }

    public void setPrecioMercado(String str) {
        this.precioMercado = str;
    }

    public String getUltCotizacion() {
        return this.ultCotizacion;
    }

    public void setUltCotizacion(String str) {
        this.ultCotizacion = str;
    }

    public String getTenValHoy() {
        return this.tenValHoy;
    }

    public void setTenValHoy(String str) {
        this.tenValHoy = str;
    }

    public String getPrecioCompra() {
        return this.precioCompra;
    }

    public void setPrecioCompra(String str) {
        this.precioCompra = str;
    }

    public String getValuacionCosto() {
        return this.valuacionCosto;
    }

    public void setValuacionCosto(String str) {
        this.valuacionCosto = str;
    }

    public String getRentasCobradas() {
        return this.rentasCobradas;
    }

    public void setRentasCobradas(String str) {
        this.rentasCobradas = str;
    }

    public String getAmortCobradas() {
        return this.amortCobradas;
    }

    public void setAmortCobradas(String str) {
        this.amortCobradas = str;
    }

    public String getDivCobrados() {
        return this.divCobrados;
    }

    public void setDivCobrados(String str) {
        this.divCobrados = str;
    }

    public String getResultado() {
        return this.resultado;
    }

    public void setResultado(String str) {
        this.resultado = str;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String str) {
        this.estado = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.descripcion);
        parcel.writeString(this.moneda);
        parcel.writeString(this.codEspecie);
        parcel.writeString(this.tipoEspecie);
        parcel.writeString(this.cantValorNominal);
        parcel.writeString(this.precioMercado);
        parcel.writeString(this.ultCotizacion);
        parcel.writeString(this.tenValHoy);
        parcel.writeString(this.precioCompra);
        parcel.writeString(this.valuacionCosto);
        parcel.writeString(this.rentasCobradas);
        parcel.writeString(this.amortCobradas);
        parcel.writeString(this.divCobrados);
        parcel.writeString(this.resultado);
        parcel.writeString(this.estado);
    }

    public List<String> getValuesAsList() {
        return Arrays.asList(new String[]{this.tipoEspecie, this.cantValorNominal, this.precioMercado, this.ultCotizacion, this.tenValHoy, this.precioCompra, this.valuacionCosto, this.divCobrados, this.resultado, this.estado});
    }
}
