package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class MensajeGenesysChat implements Parcelable {
    public static final Creator<MensajeGenesysChat> CREATOR = new Creator<MensajeGenesysChat>() {
        public MensajeGenesysChat createFromParcel(Parcel parcel) {
            return new MensajeGenesysChat(parcel);
        }

        public MensajeGenesysChat[] newArray(int i) {
            return new MensajeGenesysChat[i];
        }
    };
    String fecha = "";
    @SerializedName("imagen")
    String imagen;
    @SerializedName("texto")
    String texto;
    int type;

    public int describeContents() {
        return 0;
    }

    public MensajeGenesysChat(Parcel parcel) {
        this.texto = parcel.readString();
        this.imagen = parcel.readString();
    }

    public MensajeGenesysChat() {
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String str) {
        this.texto = str;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String str) {
        this.imagen = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.texto);
        parcel.writeString(this.imagen);
    }
}
