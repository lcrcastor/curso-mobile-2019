package ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Listaleyendas implements Parcelable {
    public static final Creator<Listaleyendas> CREATOR = new Creator<Listaleyendas>() {
        public Listaleyendas createFromParcel(Parcel parcel) {
            Listaleyendas listaleyendas = new Listaleyendas();
            listaleyendas.datosleyenda = parcel.readBundle(DatosLeyenda.class.getClassLoader()).getParcelableArrayList(Listaleyendas.LISTA_DATOS_LEYENDA_ITEM);
            return listaleyendas;
        }

        public Listaleyendas[] newArray(int i) {
            return new Listaleyendas[i];
        }
    };
    private static final String LISTA_DATOS_LEYENDA_ITEM = "LISTA_DATOS_LEYENDA_ITEM";
    /* access modifiers changed from: private */
    @SerializedName("datosLeyenda")
    public List<DatosLeyenda> datosleyenda;

    public int describeContents() {
        return 0;
    }

    public Listaleyendas(Parcel parcel) {
    }

    public Listaleyendas() {
    }

    public List<DatosLeyenda> getDatosleyenda() {
        return this.datosleyenda;
    }

    public void setDatosleyenda(List<DatosLeyenda> list) {
        this.datosleyenda = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_DATOS_LEYENDA_ITEM, (ArrayList) this.datosleyenda);
        parcel.writeBundle(bundle);
    }
}
