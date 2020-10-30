package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaPantallasBean implements Parcelable {
    public static final Creator<ListaPantallasBean> CREATOR = new Creator<ListaPantallasBean>() {
        public ListaPantallasBean createFromParcel(Parcel parcel) {
            ListaPantallasBean listaPantallasBean = new ListaPantallasBean();
            listaPantallasBean.listaPantallas = parcel.readBundle(OpcionPantallaBean.class.getClassLoader()).getParcelableArrayList(ListaPantallasBean.LISTA_PANTALLAS_ITEM);
            return listaPantallasBean;
        }

        public ListaPantallasBean[] newArray(int i) {
            return new ListaPantallasBean[i];
        }
    };
    private static final String LISTA_PANTALLAS_ITEM = "LISTA_PANTALLAS_ITEM";
    /* access modifiers changed from: private */
    @SerializedName("pantalla")
    public List<PantallaBean> listaPantallas;

    public int describeContents() {
        return 0;
    }

    public ListaPantallasBean() {
    }

    public ListaPantallasBean(List<PantallaBean> list) {
        this.listaPantallas = list;
    }

    protected ListaPantallasBean(Parcel parcel) {
        this.listaPantallas = parcel.createTypedArrayList(PantallaBean.CREATOR);
    }

    public List<PantallaBean> getListaPantallas() {
        return this.listaPantallas;
    }

    public void setListaPantallas(List<PantallaBean> list) {
        this.listaPantallas = list;
    }

    public PantallaBean getPantallaById(String str) {
        if (this.listaPantallas != null) {
            for (PantallaBean pantallaBean : this.listaPantallas) {
                if (pantallaBean.getIdPantalla().equalsIgnoreCase(str)) {
                    return pantallaBean;
                }
            }
        }
        return null;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_PANTALLAS_ITEM, (ArrayList) this.listaPantallas);
        parcel.writeBundle(bundle);
    }
}
