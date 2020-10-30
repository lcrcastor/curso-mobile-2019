package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetCircuitoTurnoBodyRequestBean implements Parcelable {
    public static final Creator<GetCircuitoTurnoBodyRequestBean> CREATOR = new Creator<GetCircuitoTurnoBodyRequestBean>() {
        public GetCircuitoTurnoBodyRequestBean createFromParcel(Parcel parcel) {
            return new GetCircuitoTurnoBodyRequestBean(parcel);
        }

        public GetCircuitoTurnoBodyRequestBean[] newArray(int i) {
            return new GetCircuitoTurnoBodyRequestBean[i];
        }
    };
    @SerializedName("numeroSucursal")
    private String numeroSucursal;

    public int describeContents() {
        return 0;
    }

    public GetCircuitoTurnoBodyRequestBean() {
    }

    public GetCircuitoTurnoBodyRequestBean(String str) {
        this.numeroSucursal = str;
    }

    protected GetCircuitoTurnoBodyRequestBean(Parcel parcel) {
        this.numeroSucursal = parcel.readString();
    }

    public String getNumeroSucursal() {
        return this.numeroSucursal;
    }

    public void setNumeroSucursal(String str) {
        this.numeroSucursal = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.numeroSucursal);
    }
}
