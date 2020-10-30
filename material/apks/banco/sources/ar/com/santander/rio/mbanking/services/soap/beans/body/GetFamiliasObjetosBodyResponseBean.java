package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFamiliasObjetosBodyResponseBean implements Parcelable {
    public static final Creator<GetFamiliasObjetosBodyResponseBean> CREATOR = new Creator<GetFamiliasObjetosBodyResponseBean>() {
        public GetFamiliasObjetosBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetFamiliasObjetosBodyResponseBean(parcel);
        }

        public GetFamiliasObjetosBodyResponseBean[] newArray(int i) {
            return new GetFamiliasObjetosBodyResponseBean[i];
        }
    };
    @SerializedName("linkSugerencia")
    @Expose
    private LinkSugerencia linkSugerencia;
    @SerializedName("listaFamilias")
    @Expose
    private ListaFamilias listaFamilias;
    @SerializedName("listaLeyendas")
    @Expose
    private ListaLeyendas listaLeyendas;

    public int describeContents() {
        return 0;
    }

    protected GetFamiliasObjetosBodyResponseBean(Parcel parcel) {
        this.listaFamilias = (ListaFamilias) parcel.readValue(ListaFamilias.class.getClassLoader());
        this.linkSugerencia = (LinkSugerencia) parcel.readValue(LinkSugerencia.class.getClassLoader());
        this.listaLeyendas = (ListaLeyendas) parcel.readValue(ListaLeyendas.class.getClassLoader());
    }

    public GetFamiliasObjetosBodyResponseBean() {
    }

    public GetFamiliasObjetosBodyResponseBean(ListaFamilias listaFamilias2, LinkSugerencia linkSugerencia2, ListaLeyendas listaLeyendas2) {
        this.listaFamilias = listaFamilias2;
        this.linkSugerencia = linkSugerencia2;
        this.listaLeyendas = listaLeyendas2;
    }

    public ListaFamilias getListaFamilias() {
        return this.listaFamilias;
    }

    public void setListaFamilias(ListaFamilias listaFamilias2) {
        this.listaFamilias = listaFamilias2;
    }

    public LinkSugerencia getLinkSugerencia() {
        return this.linkSugerencia;
    }

    public void setLinkSugerencia(LinkSugerencia linkSugerencia2) {
        this.linkSugerencia = linkSugerencia2;
    }

    public ListaLeyendas getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendas listaLeyendas2) {
        this.listaLeyendas = listaLeyendas2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.listaFamilias);
        parcel.writeValue(this.linkSugerencia);
        parcel.writeValue(this.listaLeyendas);
    }
}
