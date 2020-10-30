package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class UsuariosMarcacionBean implements Parcelable {
    public static final Creator<UsuariosMarcacionBean> CREATOR = new Creator<UsuariosMarcacionBean>() {
        public UsuariosMarcacionBean createFromParcel(Parcel parcel) {
            return new UsuariosMarcacionBean(parcel);
        }

        public UsuariosMarcacionBean[] newArray(int i) {
            return new UsuariosMarcacionBean[i];
        }
    };
    @SerializedName("usuario")
    private List<UsuarioMarcacionBean> listaUsuarios;

    public int describeContents() {
        return 0;
    }

    public UsuariosMarcacionBean() {
        this.listaUsuarios = new ArrayList();
    }

    public UsuariosMarcacionBean(List<UsuarioMarcacionBean> list) {
        this.listaUsuarios = list;
    }

    public List<UsuarioMarcacionBean> getListaUsuarios() {
        return this.listaUsuarios;
    }

    public void setListaUsuarios(List<UsuarioMarcacionBean> list) {
        this.listaUsuarios = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.listaUsuarios);
    }

    protected UsuariosMarcacionBean(Parcel parcel) {
        this.listaUsuarios = new ArrayList();
        parcel.readList(this.listaUsuarios, UsuarioMarcacionBean.class.getClassLoader());
    }
}
