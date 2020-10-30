package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CotizacionSeguroObjetoBean implements Parcelable {
    public static final Creator<CotizacionSeguroObjetoBean> CREATOR = new Creator<CotizacionSeguroObjetoBean>() {
        public CotizacionSeguroObjetoBean createFromParcel(Parcel parcel) {
            return new CotizacionSeguroObjetoBean(parcel);
        }

        public CotizacionSeguroObjetoBean[] newArray(int i) {
            return new CotizacionSeguroObjetoBean[i];
        }
    };
    @SerializedName("codProducto")
    private String codProducto;
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("htmlEjFotoIdObjeto")
    private String htmlEjFotoIdObjeto;
    @SerializedName("htmlEjFotoObjeto")
    private String htmlEjFotoObjeto;
    @SerializedName("listaLeyendas")
    ListaLeyendaSeguroMovil listaLeyendas;
    @SerializedName("listaPlanes")
    private PlanesSeguroBean listaPlanes;
    @SerializedName("numCotizacion")
    private String numCotizacion;
    @SerializedName("rangoSalto")
    private String rangoSalto;
    @SerializedName("sumaAseguradaMaxima")
    private String sumaAseguradaMaxima;
    @SerializedName("sumaAseguradaMinima")
    private String sumaAseguradaMinima;
    @SerializedName("titEjFotoIdObjeto")
    private String titEjFotoIdObjeto;
    @SerializedName("titEjFotoObjeto")
    private String titEjFotoObjeto;

    public int describeContents() {
        return 0;
    }

    public CotizacionSeguroObjetoBean() {
    }

    protected CotizacionSeguroObjetoBean(Parcel parcel) {
        this.codRamo = parcel.readString();
        this.codProducto = parcel.readString();
        this.numCotizacion = parcel.readString();
        this.sumaAseguradaMaxima = parcel.readString();
        this.sumaAseguradaMinima = parcel.readString();
        this.rangoSalto = parcel.readString();
        this.listaPlanes = (PlanesSeguroBean) parcel.readParcelable(PlanesSeguroBean.class.getClassLoader());
        this.titEjFotoObjeto = parcel.readString();
        this.htmlEjFotoObjeto = parcel.readString();
        this.titEjFotoIdObjeto = parcel.readString();
        this.htmlEjFotoIdObjeto = parcel.readString();
        this.listaLeyendas = (ListaLeyendaSeguroMovil) parcel.readParcelable(ListaLeyendaSeguroMovil.class.getClassLoader());
    }

    public String getCodRamo() {
        return this.codRamo;
    }

    public void setCodRamo(String str) {
        this.codRamo = str;
    }

    public String getCodProducto() {
        return this.codProducto;
    }

    public void setCodProducto(String str) {
        this.codProducto = str;
    }

    public String getNumCotizacion() {
        return this.numCotizacion;
    }

    public void setNumCotizacion(String str) {
        this.numCotizacion = str;
    }

    public String getSumaAseguradaMaxima() {
        return this.sumaAseguradaMaxima;
    }

    public void setSumaAseguradaMaxima(String str) {
        this.sumaAseguradaMaxima = str;
    }

    public String getSumaAseguradaMinima() {
        return this.sumaAseguradaMinima;
    }

    public void setSumaAseguradaMinima(String str) {
        this.sumaAseguradaMinima = str;
    }

    public String getRangoSalto() {
        return this.rangoSalto;
    }

    public void setRangoSalto(String str) {
        this.rangoSalto = str;
    }

    public PlanesSeguroBean getListaPlanes() {
        return this.listaPlanes;
    }

    public void setListaPlanes(PlanesSeguroBean planesSeguroBean) {
        this.listaPlanes = planesSeguroBean;
    }

    public String getTitEjFotoObjeto() {
        return this.titEjFotoObjeto;
    }

    public void setTitEjFotoObjeto(String str) {
        this.titEjFotoObjeto = str;
    }

    public String getHtmlEjFotoObjeto() {
        return this.htmlEjFotoObjeto;
    }

    public void setHtmlEjFotoObjeto(String str) {
        this.htmlEjFotoObjeto = str;
    }

    public String getTitEjFotoIdObjeto() {
        return this.titEjFotoIdObjeto;
    }

    public void setTitEjFotoIdObjeto(String str) {
        this.titEjFotoIdObjeto = str;
    }

    public String getHtmlEjFotoIdObjeto() {
        return this.htmlEjFotoIdObjeto;
    }

    public void setHtmlEjFotoIdObjeto(String str) {
        this.htmlEjFotoIdObjeto = str;
    }

    public ListaLeyendaSeguroMovil getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendaSeguroMovil listaLeyendaSeguroMovil) {
        this.listaLeyendas = listaLeyendaSeguroMovil;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codRamo);
        parcel.writeString(this.codProducto);
        parcel.writeString(this.numCotizacion);
        parcel.writeString(this.sumaAseguradaMaxima);
        parcel.writeString(this.sumaAseguradaMinima);
        parcel.writeString(this.rangoSalto);
        parcel.writeParcelable(this.listaPlanes, i);
        parcel.writeString(this.titEjFotoObjeto);
        parcel.writeString(this.htmlEjFotoObjeto);
        parcel.writeString(this.titEjFotoIdObjeto);
        parcel.writeString(this.htmlEjFotoIdObjeto);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
