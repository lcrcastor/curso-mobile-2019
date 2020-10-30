package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaSegurosObjetoBean implements Parcelable {
    public static final Creator<ListaSegurosObjetoBean> CREATOR = new Creator<ListaSegurosObjetoBean>() {
        public ListaSegurosObjetoBean createFromParcel(Parcel parcel) {
            return new ListaSegurosObjetoBean(parcel);
        }

        public ListaSegurosObjetoBean[] newArray(int i) {
            return new ListaSegurosObjetoBean[i];
        }
    };
    @SerializedName("seguro")
    private List<SeguroObjetoBean> listaObjetos;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    public ListaSegurosObjetoBean(List<SeguroObjetoBean> list) {
        this.listaObjetos = list;
    }

    protected ListaSegurosObjetoBean(Parcel parcel) {
    }

    public List<SeguroObjetoBean> getListaObjetos() {
        return this.listaObjetos;
    }

    public void setListaObjetos(List<SeguroObjetoBean> list) {
        this.listaObjetos = list;
    }
}
