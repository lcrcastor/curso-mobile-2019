package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CotizacionBean implements Parcelable {
    public static final Creator<CotizacionBean> CREATOR = new Creator<CotizacionBean>() {
        public CotizacionBean createFromParcel(Parcel parcel) {
            return new CotizacionBean(parcel);
        }

        public CotizacionBean[] newArray(int i) {
            return new CotizacionBean[i];
        }
    };
    @SerializedName("codProducto")
    private String codProducto;
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("listaLeyendas")
    ListaLeyendaSeguroMovil listaLeyendas;
    @SerializedName("numCotizacion")
    private String numCotizacion;
    @SerializedName("planes")
    private PlanesSeguroBean planes;

    public int describeContents() {
        return 0;
    }

    public CotizacionBean() {
    }

    public CotizacionBean(String str, String str2, String str3, PlanesSeguroBean planesSeguroBean, ListaLeyendaSeguroMovil listaLeyendaSeguroMovil) {
        this.codRamo = str;
        this.codProducto = str2;
        this.numCotizacion = str3;
        this.planes = planesSeguroBean;
        this.listaLeyendas = listaLeyendaSeguroMovil;
    }

    protected CotizacionBean(Parcel parcel) {
        this.codRamo = parcel.readString();
        this.codProducto = parcel.readString();
        this.numCotizacion = parcel.readString();
        this.planes = (PlanesSeguroBean) parcel.readParcelable(PlanesSeguroBean.class.getClassLoader());
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

    public PlanesSeguroBean getPlanes() {
        return this.planes;
    }

    public void setPlanes(PlanesSeguroBean planesSeguroBean) {
        this.planes = planesSeguroBean;
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
        parcel.writeParcelable(this.planes, i);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
