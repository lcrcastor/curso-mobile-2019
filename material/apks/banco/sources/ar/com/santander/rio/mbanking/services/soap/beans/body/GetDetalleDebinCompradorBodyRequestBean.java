package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetDetalleDebinCompradorBodyRequestBean implements Parcelable {
    public static final Creator<GetDetalleDebinCompradorBodyRequestBean> CREATOR = new Creator<GetDetalleDebinCompradorBodyRequestBean>() {
        public GetDetalleDebinCompradorBodyRequestBean createFromParcel(Parcel parcel) {
            return new GetDetalleDebinCompradorBodyRequestBean(parcel);
        }

        public GetDetalleDebinCompradorBodyRequestBean[] newArray(int i) {
            return new GetDetalleDebinCompradorBodyRequestBean[i];
        }
    };
    @SerializedName("idDebin")
    private String idDebin;

    public int describeContents() {
        return 0;
    }

    public GetDetalleDebinCompradorBodyRequestBean() {
    }

    public GetDetalleDebinCompradorBodyRequestBean(String str) {
        this.idDebin = str;
    }

    protected GetDetalleDebinCompradorBodyRequestBean(Parcel parcel) {
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
