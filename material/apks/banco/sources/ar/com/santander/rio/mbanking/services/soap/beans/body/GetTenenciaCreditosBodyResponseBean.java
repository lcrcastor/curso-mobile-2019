package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CuentaOperativa;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.ListaCategoriasCreditos;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.ListaCtaOperativa;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.Listaleyendas;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GetTenenciaCreditosBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetTenenciaCreditosBodyResponseBean> CREATOR = new Creator<GetTenenciaCreditosBodyResponseBean>() {
        public GetTenenciaCreditosBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetTenenciaCreditosBodyResponseBean(parcel);
        }

        public GetTenenciaCreditosBodyResponseBean[] newArray(int i) {
            return new GetTenenciaCreditosBodyResponseBean[i];
        }
    };
    @SerializedName("listaCategoriasCreditos")
    public ListaCategoriasCreditos listacategoriascreditos;
    @SerializedName("listaCtaOperativa")
    public ListaCtaOperativa listactaoperativa;
    @SerializedName("leyenda")
    public Listaleyendas listaleyendas;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    public List<CuentaOperativa> getCuentaoperativa() {
        return this.listactaoperativa.getCuentaoperativa();
    }

    public GetTenenciaCreditosBodyResponseBean(Parcel parcel) {
    }

    public GetTenenciaCreditosBodyResponseBean() {
    }

    public void setRes(String str) {
        this.res = str;
    }

    public String getRes() {
        return this.res;
    }

    public void setListacategoriascreditos(ListaCategoriasCreditos listaCategoriasCreditos) {
        this.listacategoriascreditos = listaCategoriasCreditos;
    }

    public ListaCategoriasCreditos getListacategoriascreditos() {
        return this.listacategoriascreditos;
    }

    public void setListaleyendas(Listaleyendas listaleyendas2) {
        this.listaleyendas = listaleyendas2;
    }

    public Listaleyendas getListaleyendas() {
        return this.listaleyendas;
    }

    public ListaCtaOperativa getListactaoperativa() {
        return this.listactaoperativa;
    }

    public void setListactaoperativa(ListaCtaOperativa listaCtaOperativa) {
        this.listactaoperativa = listaCtaOperativa;
    }
}
