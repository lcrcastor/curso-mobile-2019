package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaTarjetas implements Parcelable {
    public static final Creator<ListaTarjetas> CREATOR = new Creator<ListaTarjetas>() {
        public ListaTarjetas createFromParcel(Parcel parcel) {
            return new ListaTarjetas(parcel);
        }

        public ListaTarjetas[] newArray(int i) {
            return new ListaTarjetas[i];
        }
    };
    @SerializedName("tarjeta")
    @Expose
    private List<Tarjeta> tarjeta;

    public int describeContents() {
        return 0;
    }

    protected ListaTarjetas(Parcel parcel) {
        this.tarjeta = (List) parcel.readValue(Tarjeta.class.getClassLoader());
    }

    public ListaTarjetas() {
    }

    public ListaTarjetas(List<Tarjeta> list) {
        this.tarjeta = list;
    }

    public List<Tarjeta> getTarjeta() {
        return this.tarjeta;
    }

    public List<Tarjeta> getTarjetaSelected() {
        if (this.tarjeta == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (Tarjeta tarjeta2 : this.tarjeta) {
            if (tarjeta2.isSelected().booleanValue()) {
                arrayList.add(tarjeta2);
            }
        }
        return arrayList;
    }

    public void setTarjeta(List<Tarjeta> list) {
        this.tarjeta = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.tarjeta);
    }
}
