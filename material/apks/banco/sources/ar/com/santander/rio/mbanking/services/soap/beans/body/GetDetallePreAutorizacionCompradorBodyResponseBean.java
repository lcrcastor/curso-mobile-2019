package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import com.google.gson.annotations.SerializedName;

public class GetDetallePreAutorizacionCompradorBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetDetallePreAutorizacionCompradorBodyResponseBean> CREATOR = new Creator<GetDetallePreAutorizacionCompradorBodyResponseBean>() {
        public GetDetallePreAutorizacionCompradorBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetDetallePreAutorizacionCompradorBodyResponseBean(parcel);
        }

        public GetDetallePreAutorizacionCompradorBodyResponseBean[] newArray(int i) {
            return new GetDetallePreAutorizacionCompradorBodyResponseBean[i];
        }
    };
    @SerializedName("leyendaPreautorizacion")
    private String leyendaPreautorizacion;
    @SerializedName("preautorizacion")
    private DetallePreAutorizacionBean preautorizacion;

    public int describeContents() {
        return 0;
    }

    public GetDetallePreAutorizacionCompradorBodyResponseBean() {
    }

    public DetallePreAutorizacionBean getPreautorizacion() {
        return this.preautorizacion;
    }

    public void setPreautorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean) {
        this.preautorizacion = detallePreAutorizacionBean;
    }

    public String getLeyendaPreautorizacion() {
        return this.leyendaPreautorizacion;
    }

    public void setLeyendaPreautorizacion(String str) {
        this.leyendaPreautorizacion = str;
    }

    protected GetDetallePreAutorizacionCompradorBodyResponseBean(Parcel parcel) {
        this.preautorizacion = (DetallePreAutorizacionBean) parcel.readParcelable(DetallePreAutorizacionBean.class.getClassLoader());
        this.leyendaPreautorizacion = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.preautorizacion, i);
        parcel.writeString(this.leyendaPreautorizacion);
    }
}
