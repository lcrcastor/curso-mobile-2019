package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetDetalleDebinVendedorBodyRequestBean implements Parcelable {
    public static final Creator<GetDetalleDebinVendedorBodyRequestBean> CREATOR = new Creator<GetDetalleDebinVendedorBodyRequestBean>() {
        public GetDetalleDebinVendedorBodyRequestBean createFromParcel(Parcel parcel) {
            return new GetDetalleDebinVendedorBodyRequestBean(parcel);
        }

        public GetDetalleDebinVendedorBodyRequestBean[] newArray(int i) {
            return new GetDetalleDebinVendedorBodyRequestBean[i];
        }
    };
    @SerializedName("idDebin")
    private String idDebin;

    public int describeContents() {
        return 0;
    }

    public GetDetalleDebinVendedorBodyRequestBean() {
    }

    public GetDetalleDebinVendedorBodyRequestBean(String str) {
        this.idDebin = str;
    }

    protected GetDetalleDebinVendedorBodyRequestBean(Parcel parcel) {
        this.idDebin = parcel.readString();
    }

    public String getIdDebin() {
        return this.idDebin;
    }

    public void setIdDebin(String str) {
        this.idDebin = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idDebin);
    }
}
