package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaLeyendas implements Parcelable {
    public static final Creator<ListaLeyendas> CREATOR = new Creator<ListaLeyendas>() {
        public ListaLeyendas createFromParcel(Parcel parcel) {
            return new ListaLeyendas(parcel);
        }

        public ListaLeyendas[] newArray(int i) {
            return new ListaLeyendas[i];
        }
    };
    @SerializedName("leyenda")
    public List<Leyenda> lstLeyendas;

    public int describeContents() {
        return 0;
    }

    public ListaLeyendas() {
        this.lstLeyendas = new ArrayList();
    }

    public ListaLeyendas(List<Leyenda> list) {
        this.lstLeyendas = list;
    }

    private ListaLeyendas(Parcel parcel) {
        this.lstLeyendas = new ArrayList();
        parcel.readTypedList(this.lstLeyendas, Leyenda.CREATOR);
    }

    public static Creator<ListaLeyendas> getCREATOR() {
        return CREATOR;
    }

    public List<Leyenda> getLstLeyendas() {
        return this.lstLeyendas;
    }

    public void setLstLeyendas(List<Leyenda> list) {
        this.lstLeyendas = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.lstLeyendas);
    }

    public Leyenda getLeyendaByTag(String str) {
        for (Leyenda leyenda : getLstLeyendas()) {
            if (leyenda.idLeyenda.equals(str)) {
                return leyenda;
            }
        }
        return Leyenda.LeyendaEmpty();
    }
}
