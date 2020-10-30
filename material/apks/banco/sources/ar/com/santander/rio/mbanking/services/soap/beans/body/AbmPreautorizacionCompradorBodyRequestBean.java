package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import com.google.gson.annotations.SerializedName;

public class AbmPreautorizacionCompradorBodyRequestBean {
    public static final Creator<AbmPreautorizacionCompradorBodyRequestBean> CREATOR = new Creator<AbmPreautorizacionCompradorBodyRequestBean>() {
        public AbmPreautorizacionCompradorBodyRequestBean createFromParcel(Parcel parcel) {
            return new AbmPreautorizacionCompradorBodyRequestBean(parcel);
        }

        public AbmPreautorizacionCompradorBodyRequestBean[] newArray(int i) {
            return new AbmPreautorizacionCompradorBodyRequestBean[i];
        }
    };
    @SerializedName("preautorizacion")
    private DetallePreAutorizacionBean preautorizacion;
    @SerializedName("tipoOperacion")
    private String tipoOperacion;

    public int describeContents() {
        return 0;
    }

    protected AbmPreautorizacionCompradorBodyRequestBean(Parcel parcel) {
        this.tipoOperacion = (String) parcel.readValue(String.class.getClassLoader());
        this.preautorizacion = (DetallePreAutorizacionBean) parcel.readValue(DetallePreAutorizacionBean.class.getClassLoader());
    }

    public AbmPreautorizacionCompradorBodyRequestBean() {
    }

    public AbmPreautorizacionCompradorBodyRequestBean(String str, DetallePreAutorizacionBean detallePreAutorizacionBean) {
        this.tipoOperacion = str;
        this.preautorizacion = detallePreAutorizacionBean;
    }

    public String getTipoOperacion() {
        return this.tipoOperacion;
    }

    public void setTipoOperacion(String str) {
        this.tipoOperacion = str;
    }

    public AbmPreautorizacionCompradorBodyRequestBean withTipoOperacion(String str) {
        this.tipoOperacion = str;
        return this;
    }

    public DetallePreAutorizacionBean getPreautorizacion() {
        return this.preautorizacion;
    }

    public void setPreautorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean) {
        this.preautorizacion = detallePreAutorizacionBean;
    }

    public AbmPreautorizacionCompradorBodyRequestBean withPreautorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean) {
        this.preautorizacion = detallePreAutorizacionBean;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.tipoOperacion);
        parcel.writeValue(this.preautorizacion);
    }
}
