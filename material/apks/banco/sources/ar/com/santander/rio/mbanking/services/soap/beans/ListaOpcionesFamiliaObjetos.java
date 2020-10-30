package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OpcionBean;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaOpcionesFamiliaObjetos implements Parcelable {
    public static final Creator<ListaOpcionesFamiliaObjetos> CREATOR = new Creator<ListaOpcionesFamiliaObjetos>() {
        public ListaOpcionesFamiliaObjetos createFromParcel(Parcel parcel) {
            ListaOpcionesFamiliaObjetos listaOpcionesFamiliaObjetos = new ListaOpcionesFamiliaObjetos();
            listaOpcionesFamiliaObjetos.opcion = parcel.readBundle(OpcionBean.class.getClassLoader()).getParcelableArrayList(ListaOpcionesFamiliaObjetos.LISTA_OPCIONES_ITEM);
            return listaOpcionesFamiliaObjetos;
        }

        public ListaOpcionesFamiliaObjetos[] newArray(int i) {
            return new ListaOpcionesFamiliaObjetos[i];
        }
    };
    private static final String LISTA_OPCIONES_ITEM = "LISTA_OPCIONES_ITEM";
    /* access modifiers changed from: private */
    @SerializedName("opcion")
    public List<OpcionBean> opcion;

    public int describeContents() {
        return 0;
    }

    public ListaOpcionesFamiliaObjetos() {
    }

    protected ListaOpcionesFamiliaObjetos(Parcel parcel) {
        this.opcion = (List) parcel.readParcelable(OpcionBean.class.getClassLoader());
    }

    public List<OpcionBean> getOpcion() {
        return this.opcion;
    }

    public void setOpcion(List<OpcionBean> list) {
        this.opcion = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_OPCIONES_ITEM, (ArrayList) this.opcion);
        parcel.writeBundle(bundle);
    }
}
