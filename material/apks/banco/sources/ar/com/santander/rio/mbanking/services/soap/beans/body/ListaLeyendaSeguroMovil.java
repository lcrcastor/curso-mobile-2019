package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaLeyendaSeguroMovil implements Parcelable {
    public static final Creator<ListaLeyendaSeguroMovil> CREATOR = new Creator<ListaLeyendaSeguroMovil>() {
        public ListaLeyendaSeguroMovil createFromParcel(Parcel parcel) {
            return new ListaLeyendaSeguroMovil(parcel);
        }

        public ListaLeyendaSeguroMovil[] newArray(int i) {
            return new ListaLeyendaSeguroMovil[i];
        }
    };
    @SerializedName("leyenda")
    List<LeyendaItem> leyenda;

    public int describeContents() {
        return 0;
    }

    public ListaLeyendaSeguroMovil(List<LeyendaItem> list) {
        this.leyenda = list;
    }

    public ListaLeyendaSeguroMovil() {
    }

    protected ListaLeyendaSeguroMovil(Parcel parcel) {
        this.leyenda = parcel.createTypedArrayList(LeyendaItem.CREATOR);
    }

    public List<LeyendaItem> getLeyenda() {
        return this.leyenda;
    }

    public LeyendaItem getLeyendaById(String str) {
        LeyendaItem leyendaItem = new LeyendaItem();
        if (this.leyenda == null) {
            return leyendaItem;
        }
        for (LeyendaItem leyendaItem2 : this.leyenda) {
            if (leyendaItem2.getIdLeyenda().equalsIgnoreCase(str)) {
                return leyendaItem2;
            }
        }
        return leyendaItem;
    }

    public void setLeyenda(List<LeyendaItem> list) {
        this.leyenda = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.leyenda);
    }
}
