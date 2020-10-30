package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel.ProximasCuotas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProximasCuotasBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetProximasCuotasBodyResponseBean> CREATOR = new Creator<GetProximasCuotasBodyResponseBean>() {
        public GetProximasCuotasBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetProximasCuotasBodyResponseBean(parcel);
        }

        public GetProximasCuotasBodyResponseBean[] newArray(int i) {
            return new GetProximasCuotasBodyResponseBean[i];
        }
    };
    @SerializedName("proximasCuotas")
    @Expose
    public ProximasCuotas proximasCuotas;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    public GetProximasCuotasBodyResponseBean(Parcel parcel) {
    }

    public GetProximasCuotasBodyResponseBean() {
    }

    public ProximasCuotas getProximasCuotas() {
        return this.proximasCuotas;
    }

    public void setProximasCuotas(ProximasCuotas proximasCuotas2) {
        this.proximasCuotas = proximasCuotas2;
    }

    public void setRes(String str) {
        this.res = str;
    }

    public String getRes() {
        return this.res;
    }
}
