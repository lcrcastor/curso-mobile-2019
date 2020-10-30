package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class BlanqueoPinBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<BlanqueoPinBodyResponseBean> CREATOR = new Creator<BlanqueoPinBodyResponseBean>() {
        public BlanqueoPinBodyResponseBean createFromParcel(Parcel parcel) {
            return new BlanqueoPinBodyResponseBean(parcel);
        }

        public BlanqueoPinBodyResponseBean[] newArray(int i) {
            return new BlanqueoPinBodyResponseBean[i];
        }
    };
    private String codResp;
    private ListaLeyendas listaLeyendas;
    private String mensaje;

    public int describeContents() {
        return 0;
    }

    public BlanqueoPinBodyResponseBean() {
    }

    public BlanqueoPinBodyResponseBean(String str, String str2, ListaLeyendas listaLeyendas2) {
        this.codResp = str;
        this.mensaje = str2;
        this.listaLeyendas = listaLeyendas2;
    }

    protected BlanqueoPinBodyResponseBean(Parcel parcel) {
        this.codResp = parcel.readString();
        this.mensaje = parcel.readString();
        this.listaLeyendas = (ListaLeyendas) parcel.readParcelable(ListaLeyendas.class.getClassLoader());
    }

    public String getCodResp() {
        return this.codResp;
    }

    public void setCodResp(String str) {
        this.codResp = str;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String str) {
        this.mensaje = str;
    }

    public ListaLeyendas getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendas listaLeyendas2) {
        this.listaLeyendas = listaLeyendas2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codResp);
        parcel.writeString(this.mensaje);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
