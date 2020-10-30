package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CategoriaFondosBean implements Parcelable {
    public static final Creator<CategoriaFondosBean> CREATOR = new Creator<CategoriaFondosBean>() {
        public CategoriaFondosBean createFromParcel(Parcel parcel) {
            return new CategoriaFondosBean(parcel);
        }

        public CategoriaFondosBean[] newArray(int i) {
            return new CategoriaFondosBean[i];
        }
    };
    @SerializedName("fondos")
    private ListaFondosBean fondosBean;
    @SerializedName("idCategoria")
    private String idCategoria;
    @SerializedName("nombreCategoria")
    private String nombreCategoria;

    public int describeContents() {
        return 0;
    }

    public CategoriaFondosBean() {
    }

    public CategoriaFondosBean(String str, String str2, ListaFondosBean listaFondosBean) {
        this.idCategoria = str;
        this.nombreCategoria = str2;
        this.fondosBean = listaFondosBean;
    }

    protected CategoriaFondosBean(Parcel parcel) {
        this.idCategoria = parcel.readString();
        this.nombreCategoria = parcel.readString();
        this.fondosBean = (ListaFondosBean) parcel.readParcelable(ListaFondosBean.class.getClassLoader());
    }

    public String getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(String str) {
        this.idCategoria = str;
    }

    public String getNombreCategoria() {
        return this.nombreCategoria;
    }

    public void setNombreCategoria(String str) {
        this.nombreCategoria = str;
    }

    public ListaFondosBean getFondosBean() {
        return this.fondosBean;
    }

    public void setFondosBean(ListaFondosBean listaFondosBean) {
        this.fondosBean = listaFondosBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idCategoria);
        parcel.writeString(this.nombreCategoria);
        parcel.writeParcelable(this.fondosBean, i);
    }
}
