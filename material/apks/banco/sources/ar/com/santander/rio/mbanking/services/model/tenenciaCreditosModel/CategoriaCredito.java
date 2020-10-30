package ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CategoriaCredito implements Parcelable {
    public static final Creator<CategoriaCredito> CREATOR = new Creator<CategoriaCredito>() {
        public CategoriaCredito createFromParcel(Parcel parcel) {
            return new CategoriaCredito(parcel);
        }

        public CategoriaCredito[] newArray(int i) {
            return new CategoriaCredito[i];
        }
    };
    @SerializedName("descCategoriaCredito")
    public String desccategoriacredito;
    @SerializedName("idCategoriaCredito")
    public String idcategoriacredito;
    @SerializedName("listaCreditos")
    public ListaCreditos listacreditos;

    public int describeContents() {
        return 0;
    }

    public CategoriaCredito(Parcel parcel) {
        this.idcategoriacredito = parcel.readString();
        this.desccategoriacredito = parcel.readString();
    }

    public CategoriaCredito() {
    }

    public void setIdcategoriacredito(String str) {
        this.idcategoriacredito = str;
    }

    public String getIdcategoriacredito() {
        return this.idcategoriacredito;
    }

    public void setDesccategoriacredito(String str) {
        this.desccategoriacredito = str;
    }

    public String getDesccategoriacredito() {
        return this.desccategoriacredito;
    }

    public void setListacreditos(ListaCreditos listaCreditos) {
        this.listacreditos = listaCreditos;
    }

    public ListaCreditos getListacreditos() {
        return this.listacreditos;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idcategoriacredito);
        parcel.writeString(this.desccategoriacredito);
        parcel.writeList((List) this.listacreditos);
    }
}
