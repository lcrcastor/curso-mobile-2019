package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaCoberturas implements Parcelable {
    public static final Creator<ListaCoberturas> CREATOR = new Creator<ListaCoberturas>() {
        public ListaCoberturas createFromParcel(Parcel parcel) {
            ListaCoberturas listaCoberturas = new ListaCoberturas();
            listaCoberturas.listaCoberturas = parcel.readBundle(CoberturaBean.class.getClassLoader()).getParcelableArrayList(ListaCoberturas.LISTA_COBERTURAS_ITEM);
            return listaCoberturas;
        }

        public ListaCoberturas[] newArray(int i) {
            return new ListaCoberturas[i];
        }
    };
    private static final String LISTA_COBERTURAS_ITEM = "LISTA_COBERTURAS_ITEM";
    /* access modifiers changed from: private */
    @SerializedName("cobertura")
    public List<CoberturaBean> listaCoberturas;

    public int describeContents() {
        return 0;
    }

    public ListaCoberturas() {
    }

    protected ListaCoberturas(Parcel parcel) {
        this.listaCoberturas = parcel.createTypedArrayList(CoberturaBean.CREATOR);
    }

    public List<CoberturaBean> getListaCoberturas() {
        return this.listaCoberturas;
    }

    public void setListaCoberturas(List<CoberturaBean> list) {
        this.listaCoberturas = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_COBERTURAS_ITEM, (ArrayList) this.listaCoberturas);
        parcel.writeBundle(bundle);
    }
}
