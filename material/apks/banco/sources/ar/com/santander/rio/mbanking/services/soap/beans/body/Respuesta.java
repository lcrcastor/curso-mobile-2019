package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Respuesta implements Parcelable {
    public static final Creator<Respuesta> CREATOR = new Creator<Respuesta>() {
        public Respuesta createFromParcel(Parcel parcel) {
            return new Respuesta(parcel);
        }

        public Respuesta[] newArray(int i) {
            return new Respuesta[i];
        }
    };
    @SerializedName("fechaSeleccionada")
    @Expose
    private String fechaSeleccionada;
    @SerializedName("idOpcion")
    @Expose
    private String idOpcion;
    @SerializedName("textoIngresado")
    @Expose
    private String textoIngresado;
    @SerializedName("textoOpcion")
    @Expose
    private String textoOpcion;

    public int describeContents() {
        return 0;
    }

    protected Respuesta(Parcel parcel) {
        this.idOpcion = (String) parcel.readValue(String.class.getClassLoader());
        this.textoOpcion = (String) parcel.readValue(String.class.getClassLoader());
        this.fechaSeleccionada = (String) parcel.readValue(String.class.getClassLoader());
        this.textoIngresado = (String) parcel.readValue(String.class.getClassLoader());
    }

    public Respuesta() {
    }

    public Respuesta(String str, String str2, String str3, String str4) {
        this.idOpcion = str;
        this.textoOpcion = str2;
        this.fechaSeleccionada = str3;
        this.textoIngresado = str4;
    }

    public String getIdOpcion() {
        return this.idOpcion;
    }

    public void setIdOpcion(String str) {
        this.idOpcion = str;
    }

    public String getTextoOpcion() {
        return this.textoOpcion;
    }

    public void setTextoOpcion(String str) {
        this.textoOpcion = str;
    }

    public String getFechaSeleccionada() {
        return this.fechaSeleccionada;
    }

    public void setFechaSeleccionada(String str) {
        this.fechaSeleccionada = str;
    }

    public String getTextoIngresado() {
        return this.textoIngresado;
    }

    public void setTextoIngresado(String str) {
        this.textoIngresado = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.idOpcion);
        parcel.writeValue(this.textoOpcion);
        parcel.writeValue(this.fechaSeleccionada);
        parcel.writeValue(this.textoIngresado);
    }
}
