package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaPreAutorizacionesBean implements Parcelable {
    public static final Creator<ListaPreAutorizacionesBean> CREATOR = new Creator<ListaPreAutorizacionesBean>() {
        public ListaPreAutorizacionesBean createFromParcel(Parcel parcel) {
            return new ListaPreAutorizacionesBean(parcel);
        }

        public ListaPreAutorizacionesBean[] newArray(int i) {
            return new ListaPreAutorizacionesBean[i];
        }
    };
    @SerializedName("preautorizacion")
    private List<PreautorizacionBean> listPreAutorizacionesBean;

    public int describeContents() {
        return 0;
    }

    public ListaPreAutorizacionesBean() {
        this.listPreAutorizacionesBean = new ArrayList();
    }

    public ListaPreAutorizacionesBean(Parcel parcel) {
    }

    public ListaPreAutorizacionesBean(List<PreautorizacionBean> list) {
        this.listPreAutorizacionesBean = list;
    }

    public List<PreautorizacionBean> getListPreAutorizacionesBean() {
        return this.listPreAutorizacionesBean;
    }

    public List<PreautorizacionBean> getLisPreAutorizacionesBean() {
        return this.listPreAutorizacionesBean;
    }

    public void setListPreAutorizacionesBean(List<PreautorizacionBean> list) {
        this.listPreAutorizacionesBean = list;
    }

    public void add(PreautorizacionBean preautorizacionBean) {
        if (preautorizacionBean != null) {
            this.listPreAutorizacionesBean.add(preautorizacionBean);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listPreAutorizacionesBean);
    }
}
