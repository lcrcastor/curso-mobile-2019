package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class PantallaBean implements Parcelable {
    public static final Creator<PantallaBean> CREATOR = new Creator<PantallaBean>() {
        public PantallaBean createFromParcel(Parcel parcel) {
            return new PantallaBean(parcel);
        }

        public PantallaBean[] newArray(int i) {
            return new PantallaBean[i];
        }
    };
    @SerializedName("idPantalla")
    private String idPantalla;
    @SerializedName("listaOpciones")
    private ListaOpcionesPantalla listaOpciones;

    public int describeContents() {
        return 0;
    }

    public PantallaBean() {
    }

    public PantallaBean(String str, ListaOpcionesPantalla listaOpcionesPantalla) {
        this.idPantalla = str;
        this.listaOpciones = listaOpcionesPantalla;
    }

    protected PantallaBean(Parcel parcel) {
        this.idPantalla = parcel.readString();
        this.listaOpciones = (ListaOpcionesPantalla) parcel.readParcelable(ListaOpcionesPantalla.class.getClassLoader());
    }

    public String getIdPantalla() {
        return this.idPantalla;
    }

    public void setIdPantalla(String str) {
        this.idPantalla = str;
    }

    public ListaOpcionesPantalla getListaOpciones() {
        return this.listaOpciones;
    }

    public void setListaOpciones(ListaOpcionesPantalla listaOpcionesPantalla) {
        this.listaOpciones = listaOpcionesPantalla;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idPantalla);
        parcel.writeParcelable(this.listaOpciones, i);
    }
}
