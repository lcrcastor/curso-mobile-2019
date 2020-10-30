package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetDetallePreAutorizacionCompradorBodyRequestBean implements Parcelable {
    public static final Creator<GetDetallePreAutorizacionCompradorBodyRequestBean> CREATOR = new Creator<GetDetallePreAutorizacionCompradorBodyRequestBean>() {
        public GetDetallePreAutorizacionCompradorBodyRequestBean createFromParcel(Parcel parcel) {
            return new GetDetallePreAutorizacionCompradorBodyRequestBean(parcel);
        }

        public GetDetallePreAutorizacionCompradorBodyRequestBean[] newArray(int i) {
            return new GetDetallePreAutorizacionCompradorBodyRequestBean[i];
        }
    };
    @SerializedName("codEstado")
    private String codEstado;
    @SerializedName("idPreautorizacion")
    private String idPreautorizacion;

    public int describeContents() {
        return 0;
    }

    public GetDetallePreAutorizacionCompradorBodyRequestBean(String str, String str2) {
        this.idPreautorizacion = str;
        this.codEstado = str2;
    }

    public GetDetallePreAutorizacionCompradorBodyRequestBean() {
    }

    public String getIdPreautorizacion() {
        return this.idPreautorizacion;
    }

    public void setIdPreautorizacion(String str) {
        this.idPreautorizacion = str;
    }

    public String getCodEstado() {
        return this.codEstado;
    }

    public void setCodEstado(String str) {
        this.codEstado = str;
    }

    protected GetDetallePreAutorizacionCompradorBodyRequestBean(Parcel parcel) {
        this.codEstado = parcel.readString();
        this.idPreautorizacion = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idPreautorizacion);
        parcel.writeString(this.codEstado);
    }
}
