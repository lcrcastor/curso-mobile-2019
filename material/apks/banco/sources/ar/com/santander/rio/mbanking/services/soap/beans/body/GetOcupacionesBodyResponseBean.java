package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetOcupacionesBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetOcupacionesBodyResponseBean> CREATOR = new Creator<GetOcupacionesBodyResponseBean>() {
        public GetOcupacionesBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetOcupacionesBodyResponseBean(parcel);
        }

        public GetOcupacionesBodyResponseBean[] newArray(int i) {
            return new GetOcupacionesBodyResponseBean[i];
        }
    };
    @SerializedName("hashCode")
    private String hashCode;
    @SerializedName("ocupaciones")
    private OcupacionesBean ocupaciones;

    public int describeContents() {
        return 0;
    }

    public GetOcupacionesBodyResponseBean() {
    }

    public GetOcupacionesBodyResponseBean(String str, OcupacionesBean ocupacionesBean) {
        this.hashCode = str;
        this.ocupaciones = ocupacionesBean;
    }

    protected GetOcupacionesBodyResponseBean(Parcel parcel) {
        this.hashCode = parcel.readString();
        this.ocupaciones = (OcupacionesBean) parcel.readParcelable(OcupacionesBean.class.getClassLoader());
    }

    public String getHashCode() {
        return this.hashCode;
    }

    public void setHashCode(String str) {
        this.hashCode = str;
    }

    public OcupacionesBean getOcupaciones() {
        return this.ocupaciones;
    }

    public void setOcupaciones(OcupacionesBean ocupacionesBean) {
        this.ocupaciones = ocupacionesBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.hashCode);
        parcel.writeParcelable(this.ocupaciones, i);
    }
}
