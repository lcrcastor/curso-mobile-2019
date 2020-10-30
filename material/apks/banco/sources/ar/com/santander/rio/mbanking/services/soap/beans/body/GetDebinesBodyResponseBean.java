package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetDebinesBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetDebinesBodyResponseBean> CREATOR = new Creator<GetDebinesBodyResponseBean>() {
        public GetDebinesBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetDebinesBodyResponseBean(parcel);
        }

        public GetDebinesBodyResponseBean[] newArray(int i) {
            return new GetDebinesBodyResponseBean[i];
        }
    };
    @SerializedName("debines")
    private ListaDebinesBean listaDebinesBean;
    @SerializedName("siguientePagina")
    private String siguientePagina;

    public int describeContents() {
        return 0;
    }

    public GetDebinesBodyResponseBean(ListaDebinesBean listaDebinesBean2, String str) {
        this.listaDebinesBean = listaDebinesBean2;
        this.siguientePagina = str;
    }

    public GetDebinesBodyResponseBean() {
    }

    protected GetDebinesBodyResponseBean(Parcel parcel) {
        this.listaDebinesBean = (ListaDebinesBean) parcel.readParcelable(ListaDebinesBean.class.getClassLoader());
        this.siguientePagina = parcel.readString();
    }

    public ListaDebinesBean getListaDebinesBean() {
        return this.listaDebinesBean;
    }

    public void setListaDebinesBean(ListaDebinesBean listaDebinesBean2) {
        this.listaDebinesBean = listaDebinesBean2;
    }

    public String getSiguientePagina() {
        return this.siguientePagina;
    }

    public void setSiguientePagina(String str) {
        this.siguientePagina = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.listaDebinesBean, i);
        parcel.writeString(this.siguientePagina);
    }
}
