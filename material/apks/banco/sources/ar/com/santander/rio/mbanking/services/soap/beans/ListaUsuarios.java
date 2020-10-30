package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios implements Parcelable {
    public static final Creator<ListaUsuarios> CREATOR = new Creator<ListaUsuarios>() {
        public ListaUsuarios createFromParcel(Parcel parcel) {
            return new ListaUsuarios(parcel);
        }

        public ListaUsuarios[] newArray(int i) {
            return new ListaUsuarios[i];
        }
    };
    @SerializedName("usuario")
    @Expose
    private List<Usuario> usuario = new ArrayList();

    public int describeContents() {
        return 0;
    }

    protected ListaUsuarios(Parcel parcel) {
        parcel.readList(this.usuario, Usuario.class.getClassLoader());
    }

    public ListaUsuarios() {
    }

    public ListaUsuarios(List<Usuario> list) {
        this.usuario = list;
    }

    public List<Usuario> getUsuario() {
        return this.usuario;
    }

    public void setUsuario(List<Usuario> list) {
        this.usuario = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.usuario);
    }

    public List<Usuario> getSelectedUserList() {
        if (this.usuario == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (Usuario usuario2 : this.usuario) {
            List tarjetaSelected = usuario2.getTarjetaSelected();
            if (!tarjetaSelected.isEmpty()) {
                Usuario usuario3 = new Usuario(usuario2);
                usuario3.setFilteredSelectedCards(tarjetaSelected);
                arrayList.add(usuario3);
            }
        }
        return arrayList;
    }
}
