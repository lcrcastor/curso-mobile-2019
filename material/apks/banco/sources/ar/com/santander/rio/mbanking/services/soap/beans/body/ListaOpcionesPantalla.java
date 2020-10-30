package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaOpcionesPantalla implements Parcelable {
    public static final Creator<ListaOpcionesPantalla> CREATOR = new Creator<ListaOpcionesPantalla>() {
        public ListaOpcionesPantalla createFromParcel(Parcel parcel) {
            ListaOpcionesPantalla listaOpcionesPantalla = new ListaOpcionesPantalla();
            listaOpcionesPantalla.opcion = parcel.readBundle(OpcionPantallaBean.class.getClassLoader()).getParcelableArrayList(ListaOpcionesPantalla.LISTA_OPCION_ITEM);
            return listaOpcionesPantalla;
        }

        public ListaOpcionesPantalla[] newArray(int i) {
            return new ListaOpcionesPantalla[i];
        }
    };
    private static final String LISTA_OPCION_ITEM = "LISTA_OPCION_ITEM";
    /* access modifiers changed from: private */
    @SerializedName("opcion")
    public List<OpcionPantallaBean> opcion;

    public int describeContents() {
        return 0;
    }

    public ListaOpcionesPantalla() {
    }

    public ListaOpcionesPantalla(List<OpcionPantallaBean> list) {
        this.opcion = list;
    }

    protected ListaOpcionesPantalla(Parcel parcel) {
        this.opcion = parcel.createTypedArrayList(OpcionPantallaBean.CREATOR);
    }

    public List<OpcionPantallaBean> getOpcion() {
        return this.opcion;
    }

    public void setOpcion(List<OpcionPantallaBean> list) {
        this.opcion = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_OPCION_ITEM, (ArrayList) this.opcion);
        parcel.writeBundle(bundle);
    }
}
