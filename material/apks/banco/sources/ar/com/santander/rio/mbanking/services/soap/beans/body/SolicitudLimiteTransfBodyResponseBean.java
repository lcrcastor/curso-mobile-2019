package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class SolicitudLimiteTransfBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<SolicitudLimiteTransfBodyResponseBean> CREATOR = new Creator<SolicitudLimiteTransfBodyResponseBean>() {
        public SolicitudLimiteTransfBodyResponseBean createFromParcel(Parcel parcel) {
            return new SolicitudLimiteTransfBodyResponseBean(parcel);
        }

        public SolicitudLimiteTransfBodyResponseBean[] newArray(int i) {
            return new SolicitudLimiteTransfBodyResponseBean[i];
        }
    };
    @SerializedName("nroComprobante")
    private String nroComprobante;

    public int describeContents() {
        return 0;
    }

    public SolicitudLimiteTransfBodyResponseBean() {
    }

    public SolicitudLimiteTransfBodyResponseBean(String str) {
        this.nroComprobante = str;
    }

    protected SolicitudLimiteTransfBodyResponseBean(Parcel parcel) {
        this.nroComprobante = parcel.readString();
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public void setNroComprobante(String str) {
        this.nroComprobante = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nroComprobante);
    }
}
