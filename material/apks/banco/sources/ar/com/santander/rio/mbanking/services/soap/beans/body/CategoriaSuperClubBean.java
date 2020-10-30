package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CategoriaSuperClubBean implements Parcelable {
    public static final Creator<CategoriaSuperClubBean> CREATOR = new Creator<CategoriaSuperClubBean>() {
        public CategoriaSuperClubBean createFromParcel(Parcel parcel) {
            return new CategoriaSuperClubBean(parcel);
        }

        public CategoriaSuperClubBean[] newArray(int i) {
            return new CategoriaSuperClubBean[i];
        }
    };
    @SerializedName("categorias")
    public CategoriasSuperClubBean categorias;
    @SerializedName("id")

    /* renamed from: id reason: collision with root package name */
    public String f254id;
    @SerializedName("idAtributo")
    public String idAtributo;
    @SerializedName("idClase")
    public String idClase;
    @SerializedName("idPadre")
    public String idPadre;
    @SerializedName("imagen")
    public String imagen;
    @SerializedName("nombre")
    public String nombre;

    public int describeContents() {
        return 0;
    }

    public CategoriaSuperClubBean() {
        this.categorias = new CategoriasSuperClubBean();
    }

    public CategoriaSuperClubBean(String str, String str2, String str3, String str4, String str5, String str6, CategoriasSuperClubBean categoriasSuperClubBean) {
        this.f254id = str;
        this.idPadre = str2;
        this.nombre = str3;
        this.imagen = str4;
        this.idAtributo = str5;
        this.idClase = str6;
        this.categorias = categoriasSuperClubBean;
    }

    private CategoriaSuperClubBean(Parcel parcel) {
        this.f254id = parcel.readString();
        this.idPadre = parcel.readString();
        this.nombre = parcel.readString();
        this.imagen = parcel.readString();
        this.idAtributo = parcel.readString();
        this.idClase = parcel.readString();
        this.categorias = (CategoriasSuperClubBean) parcel.readParcelable(CategoriasSuperClubBean.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f254id);
        parcel.writeString(this.idPadre);
        parcel.writeString(this.nombre);
        parcel.writeString(this.imagen);
        parcel.writeString(this.idAtributo);
        parcel.writeString(this.idClase);
        parcel.writeParcelable(this.categorias, i);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof CategoriaSuperClubBean)) {
            return false;
        }
        CategoriaSuperClubBean categoriaSuperClubBean = (CategoriaSuperClubBean) obj;
        if (this.f254id.equalsIgnoreCase(categoriaSuperClubBean.f254id) && this.idPadre.equalsIgnoreCase(categoriaSuperClubBean.idPadre) && this.nombre.equalsIgnoreCase(categoriaSuperClubBean.nombre) && this.imagen.equalsIgnoreCase(categoriaSuperClubBean.imagen) && this.idAtributo.equalsIgnoreCase(categoriaSuperClubBean.idAtributo) && this.idClase.equalsIgnoreCase(categoriaSuperClubBean.idClase) && this.categorias.equals(categoriaSuperClubBean.categorias)) {
            z = true;
        }
        return z;
    }

    public Boolean hasSubcategories() {
        return Boolean.valueOf(this.categorias.categoria.size() != 0);
    }
}
