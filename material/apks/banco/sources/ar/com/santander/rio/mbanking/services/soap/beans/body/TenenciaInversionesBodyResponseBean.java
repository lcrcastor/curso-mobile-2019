package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TenenciaInversionesBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<TenenciaInversionesBodyResponseBean> CREATOR = new Creator<TenenciaInversionesBodyResponseBean>() {
        public TenenciaInversionesBodyResponseBean createFromParcel(Parcel parcel) {
            return new TenenciaInversionesBodyResponseBean(parcel);
        }

        public TenenciaInversionesBodyResponseBean[] newArray(int i) {
            return new TenenciaInversionesBodyResponseBean[i];
        }
    };
    private BancaPrivada bancaPrivada;
    private Individuos individuos;
    private ListaLeyendas listaLeyendas;

    public int describeContents() {
        return 0;
    }

    protected TenenciaInversionesBodyResponseBean(Parcel parcel) {
        super(parcel);
        this.bancaPrivada = (BancaPrivada) parcel.readParcelable(BancaPrivada.class.getClassLoader());
        this.individuos = (Individuos) parcel.readParcelable(Individuos.class.getClassLoader());
        this.listaLeyendas = (ListaLeyendas) parcel.readParcelable(ListaLeyendas.class.getClassLoader());
    }

    public BancaPrivada getBancaPrivada() {
        return this.bancaPrivada;
    }

    public void setBancaPrivada(BancaPrivada bancaPrivada2) {
        this.bancaPrivada = bancaPrivada2;
    }

    public Individuos getIndividuos() {
        return this.individuos;
    }

    public void setIndividuos(Individuos individuos2) {
        this.individuos = individuos2;
    }

    public ListaLeyendas getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendas listaLeyendas2) {
        this.listaLeyendas = listaLeyendas2;
    }

    public static Creator<TenenciaInversionesBodyResponseBean> getCREATOR() {
        return CREATOR;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.bancaPrivada, i);
        parcel.writeParcelable(this.individuos, i);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
