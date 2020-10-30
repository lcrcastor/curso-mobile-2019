package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetDetalleDebinVendedorBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetDetalleDebinVendedorBodyResponseBean> CREATOR = new Creator<GetDetalleDebinVendedorBodyResponseBean>() {
        public GetDetalleDebinVendedorBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetDetalleDebinVendedorBodyResponseBean(parcel);
        }

        public GetDetalleDebinVendedorBodyResponseBean[] newArray(int i) {
            return new GetDetalleDebinVendedorBodyResponseBean[i];
        }
    };
    @SerializedName("detalleDebin")
    private DetalleDebinBean detalleDebinBean;

    public int describeContents() {
        return 0;
    }

    public GetDetalleDebinVendedorBodyResponseBean(DetalleDebinBean detalleDebinBean2) {
        this.detalleDebinBean = detalleDebinBean2;
    }

    protected GetDetalleDebinVendedorBodyResponseBean(Parcel parcel) {
        this.detalleDebinBean = (DetalleDebinBean) parcel.readParcelable(DetalleDebinBean.class.getClassLoader());
    }

    public DetalleDebinBean getDetalleDebinBean() {
        return this.detalleDebinBean;
    }

    public void setDetalleDebinBean(DetalleDebinBean detalleDebinBean2) {
        this.detalleDebinBean = detalleDebinBean2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.detalleDebinBean, i);
    }
}
