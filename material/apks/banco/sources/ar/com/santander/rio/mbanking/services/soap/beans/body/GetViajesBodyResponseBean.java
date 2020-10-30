package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetViajesBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetViajesBodyResponseBean> CREATOR = new Creator<GetViajesBodyResponseBean>() {
        public GetViajesBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetViajesBodyResponseBean(parcel);
        }

        public GetViajesBodyResponseBean[] newArray(int i) {
            return new GetViajesBodyResponseBean[i];
        }
    };
    @SerializedName("ayuda")
    private String ayuda;
    @SerializedName("viajes")
    private ViajesBean viajes;

    public int describeContents() {
        return 0;
    }

    public GetViajesBodyResponseBean() {
    }

    public GetViajesBodyResponseBean(ViajesBean viajesBean, String str) {
        this.viajes = viajesBean;
        this.ayuda = str;
    }

    public ViajesBean getViajes() {
        return this.viajes;
    }

    public String getAyuda() {
        return this.ayuda;
    }

    public void setViajes(ViajesBean viajesBean) {
        this.viajes = viajesBean;
    }

    public void setAyuda(String str) {
        this.ayuda = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.viajes, i);
        parcel.writeString(this.ayuda);
    }

    protected GetViajesBodyResponseBean(Parcel parcel) {
        this.viajes = (ViajesBean) parcel.readParcelable(ViajesBean.class.getClassLoader());
        this.ayuda = parcel.readString();
    }
}
