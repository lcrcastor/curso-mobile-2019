package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ConsultarAdhesionVendedorBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<ConsultarAdhesionVendedorBodyResponseBean> CREATOR = new Creator<ConsultarAdhesionVendedorBodyResponseBean>() {
        public ConsultarAdhesionVendedorBodyResponseBean createFromParcel(Parcel parcel) {
            return new ConsultarAdhesionVendedorBodyResponseBean(parcel);
        }

        public ConsultarAdhesionVendedorBodyResponseBean[] newArray(int i) {
            return new ConsultarAdhesionVendedorBodyResponseBean[i];
        }
    };
    @SerializedName("leyendaDebin")
    private String leyendaDebin;
    @SerializedName("cuentasVendedor")
    private ListaCuentasVendedorBean listaCuentasVendedorBean;

    public int describeContents() {
        return 0;
    }

    public ConsultarAdhesionVendedorBodyResponseBean() {
    }

    public ConsultarAdhesionVendedorBodyResponseBean(ListaCuentasVendedorBean listaCuentasVendedorBean2, String str) {
        this.listaCuentasVendedorBean = listaCuentasVendedorBean2;
        this.leyendaDebin = str;
    }

    protected ConsultarAdhesionVendedorBodyResponseBean(Parcel parcel) {
        this.listaCuentasVendedorBean = (ListaCuentasVendedorBean) parcel.readParcelable(ListaCuentasVendedorBean.class.getClassLoader());
        this.leyendaDebin = parcel.readString();
    }

    public ListaCuentasVendedorBean getListaCuentasVendedorBean() {
        return this.listaCuentasVendedorBean;
    }

    public void setListaCuentasVendedorBean(ListaCuentasVendedorBean listaCuentasVendedorBean2) {
        this.listaCuentasVendedorBean = listaCuentasVendedorBean2;
    }

    public String getLeyendaDebin() {
        return this.leyendaDebin;
    }

    public void setLeyendaDebin(String str) {
        this.leyendaDebin = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.listaCuentasVendedorBean, i);
        parcel.writeString(this.leyendaDebin);
    }
}
