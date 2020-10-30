package ar.com.santander.rio.mbanking.services.model.general;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaLeyenda implements Parcelable {
    public static final Creator<ListaLeyenda> CREATOR = new Creator<ListaLeyenda>() {
        public ListaLeyenda createFromParcel(Parcel parcel) {
            return new ListaLeyenda(parcel);
        }

        public ListaLeyenda[] newArray(int i) {
            return new ListaLeyenda[i];
        }
    };
    @SerializedName("leyenda")
    public List<Leyenda> leyendaList;

    public int describeContents() {
        return 0;
    }

    protected ListaLeyenda(Parcel parcel) {
        this.leyendaList = parcel.createTypedArrayList(Leyenda.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.leyendaList);
    }

    public List<Leyenda> getLeyendaList() {
        return this.leyendaList;
    }

    public void setLeyendaList(List<Leyenda> list) {
        this.leyendaList = list;
    }
}
