package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.model.general.Destinos;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.ListaProductos;
import com.google.gson.annotations.SerializedName;

public class ConsultaSuscripcionMyAResponeBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<ConsultaSuscripcionMyAResponeBodyResponseBean> CREATOR = new Creator<ConsultaSuscripcionMyAResponeBodyResponseBean>() {
        public ConsultaSuscripcionMyAResponeBodyResponseBean createFromParcel(Parcel parcel) {
            return new ConsultaSuscripcionMyAResponeBodyResponseBean(parcel);
        }

        public ConsultaSuscripcionMyAResponeBodyResponseBean[] newArray(int i) {
            return new ConsultaSuscripcionMyAResponeBodyResponseBean[i];
        }
    };
    @SerializedName("destinos")
    Destinos destinos;
    @SerializedName("listaLeyendas")
    ListaLeyendas listaLeyendas;
    @SerializedName("listaProductos")
    ListaProductos listaProductos;
    @SerializedName("urlSorpresa")
    UrlSorpresa urlSorpresa;

    public int describeContents() {
        return 0;
    }

    protected ConsultaSuscripcionMyAResponeBodyResponseBean(Parcel parcel) {
        this.listaLeyendas = (ListaLeyendas) parcel.readParcelable(ListaLeyendas.class.getClassLoader());
        this.urlSorpresa = (UrlSorpresa) parcel.readParcelable(UrlSorpresa.class.getClassLoader());
    }

    public Destinos getDestinos() {
        return this.destinos;
    }

    public void setDestinos(Destinos destinos2) {
        this.destinos = destinos2;
    }

    public UrlSorpresa getUrlSorpresa() {
        return this.urlSorpresa;
    }

    public void setUrlSorpresa(UrlSorpresa urlSorpresa2) {
        this.urlSorpresa = urlSorpresa2;
    }

    public ListaProductos getListaProductos() {
        return this.listaProductos;
    }

    public void setListaProductos(ListaProductos listaProductos2) {
        this.listaProductos = listaProductos2;
    }

    public ListaLeyendas getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendas listaLeyendas2) {
        this.listaLeyendas = listaLeyendas2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.listaLeyendas, i);
        parcel.writeParcelable(this.urlSorpresa, i);
    }
}
