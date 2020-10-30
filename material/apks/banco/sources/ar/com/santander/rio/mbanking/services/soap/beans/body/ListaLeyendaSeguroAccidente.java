package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaLeyendaSeguroAccidente implements Parcelable {
    public static final Creator<ListaLeyendaSeguroAccidente> CREATOR = new Creator<ListaLeyendaSeguroAccidente>() {
        public ListaLeyendaSeguroAccidente createFromParcel(Parcel parcel) {
            ListaLeyendaSeguroAccidente listaLeyendaSeguroAccidente = new ListaLeyendaSeguroAccidente();
            listaLeyendaSeguroAccidente.leyenda = parcel.readBundle(LeyendaItem.class.getClassLoader()).getParcelableArrayList(ListaLeyendaSeguroAccidente.LISTA_LEYENDA_ITEM);
            return listaLeyendaSeguroAccidente;
        }

        public ListaLeyendaSeguroAccidente[] newArray(int i) {
            return new ListaLeyendaSeguroAccidente[i];
        }
    };
    private static final String LISTA_LEYENDA_ITEM = "LISTA_LEYENDA_ITEM";
    @SerializedName("leyenda")
    List<LeyendaItem> leyenda;

    public int describeContents() {
        return 0;
    }

    public ListaLeyendaSeguroAccidente(List<LeyendaItem> list) {
        this.leyenda = list;
    }

    public ListaLeyendaSeguroAccidente() {
    }

    protected ListaLeyendaSeguroAccidente(Parcel parcel) {
        this.leyenda = (List) parcel.readParcelable(LeyendaItem.class.getClassLoader());
    }

    public List<LeyendaItem> getLeyenda() {
        return this.leyenda;
    }

    public void setLeyenda(List<LeyendaItem> list) {
        this.leyenda = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_LEYENDA_ITEM, (ArrayList) this.leyenda);
        parcel.writeBundle(bundle);
    }
}
