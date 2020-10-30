package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetDetalleDebinCompradorBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetDetalleDebinCompradorBodyResponseBean> CREATOR = new Creator<GetDetalleDebinCompradorBodyResponseBean>() {
        public GetDetalleDebinCompradorBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetDetalleDebinCompradorBodyResponseBean(parcel);
        }

        public GetDetalleDebinCompradorBodyResponseBean[] newArray(int i) {
            return new GetDetalleDebinCompradorBodyResponseBean[i];
        }
    };
    @SerializedName("detalleDebin")
    private DetalleDebinBean detalleDebinBean;
    @SerializedName("leyendaDebin")
    private String leyendaDebin;

    public int describeContents() {
        return 0;
    }

    public GetDetalleDebinCompradorBodyResponseBean() {
    }

    public GetDetalleDebinCompradorBodyResponseBean(DetalleDebinBean detalleDebinBean2, String str) {
        this.detalleDebinBean = detalleDebinBean2;
        this.leyendaDebin = str;
    }

    protected GetDetalleDebinCompradorBodyResponseBean(Parcel parcel) {
        this.detalleDebinBean = (DetalleDebinBean) parcel.readParcelable(DetalleDebinBean.class.getClassLoader());
        this.leyendaDebin = parcel.readString();
    }

    public DetalleDebinBean getDetalleDebinBean() {
        return this.detalleDebinBean;
    }

    public void setDetalleDebinBean(DetalleDebinBean detalleDebinBean2) {
        this.detalleDebinBean = detalleDebinBean2;
    }

    public String getLeyendaDebin() {
        return this.leyendaDebin;
    }

    public void setLeyendaDebin(String str) {
        this.leyendaDebin = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.detalleDebinBean, i);
        parcel.writeString(this.leyendaDebin);
    }
}
