package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaOpcionesWatson implements Parcelable {
    public static final Creator<ListaOpcionesWatson> CREATOR = new Creator<ListaOpcionesWatson>() {
        public ListaOpcionesWatson createFromParcel(Parcel parcel) {
            return new ListaOpcionesWatson(parcel);
        }

        public ListaOpcionesWatson[] newArray(int i) {
            return new ListaOpcionesWatson[i];
        }
    };
    @SerializedName("opcion")
    @Expose
    private List<String> opcion = new ArrayList();

    public int describeContents() {
        return 0;
    }

    protected ListaOpcionesWatson(Parcel parcel) {
        parcel.readList(this.opcion, String.class.getClassLoader());
    }

    public ListaOpcionesWatson() {
    }

    public ListaOpcionesWatson(List<String> list) {
        this.opcion = list;
    }

    public List<String> getOpcion() {
        return this.opcion;
    }

    public void setOpcion(List<String> list) {
        this.opcion = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.opcion);
    }
}
