package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Parcelable {
    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        public Usuario createFromParcel(Parcel parcel) {
            return new Usuario(parcel);
        }

        public Usuario[] newArray(int i) {
            return new Usuario[i];
        }
    };
    @SerializedName("listaTarjetas")
    @Expose
    private ListaTarjetas listaTarjetas;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public int describeContents() {
        return 0;
    }

    protected Usuario(Parcel parcel) {
        this.nombre = (String) parcel.readValue(String.class.getClassLoader());
        this.listaTarjetas = (ListaTarjetas) parcel.readValue(ListaTarjetas.class.getClassLoader());
    }

    public Usuario(Usuario usuario) {
        ListaTarjetas listaTarjetas2 = new ListaTarjetas();
        listaTarjetas2.setTarjeta((List) ((ArrayList) usuario.listaTarjetas.getTarjeta()).clone());
        this.listaTarjetas = listaTarjetas2;
        this.nombre = usuario.nombre;
    }

    public Usuario() {
    }

    public Usuario(String str, ListaTarjetas listaTarjetas2) {
        this.nombre = str;
        this.listaTarjetas = listaTarjetas2;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public ListaTarjetas getListaTarjetas() {
        return this.listaTarjetas;
    }

    public List<Tarjeta> getTarjetaSelected() {
        return this.listaTarjetas.getTarjetaSelected();
    }

    public void setFilteredSelectedCards(List<Tarjeta> list) {
        this.listaTarjetas.setTarjeta(list);
    }

    public void setListaTarjetas(ListaTarjetas listaTarjetas2) {
        this.listaTarjetas = listaTarjetas2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.nombre);
        parcel.writeValue(this.listaTarjetas);
    }
}
