package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaMensajes implements Parcelable {
    public static final Creator<ListaSugerencias> CREATOR = new Creator<ListaSugerencias>() {
        public ListaSugerencias createFromParcel(Parcel parcel) {
            return new ListaSugerencias(parcel);
        }

        public ListaSugerencias[] newArray(int i) {
            return new ListaSugerencias[i];
        }
    };
    @SerializedName("mensaje")
    @Expose
    public List<MensajeGenesysChat> mensaje = new ArrayList();

    public int describeContents() {
        return 0;
    }

    protected ListaMensajes(Parcel parcel) {
        parcel.readList(this.mensaje, String.class.getClassLoader());
    }

    public ListaMensajes() {
    }

    public ListaMensajes(List<MensajeGenesysChat> list) {
        this.mensaje = list;
    }

    public List<MensajeGenesysChat> getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(List<MensajeGenesysChat> list) {
        this.mensaje = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.mensaje);
    }
}
