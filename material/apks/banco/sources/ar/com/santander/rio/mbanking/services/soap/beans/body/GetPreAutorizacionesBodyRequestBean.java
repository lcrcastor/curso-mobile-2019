package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetPreAutorizacionesBodyRequestBean implements Parcelable {
    public static final Creator<GetPreAutorizacionesBodyRequestBean> CREATOR = new Creator<GetPreAutorizacionesBodyRequestBean>() {
        public GetPreAutorizacionesBodyRequestBean createFromParcel(Parcel parcel) {
            return new GetPreAutorizacionesBodyRequestBean(parcel);
        }

        public GetPreAutorizacionesBodyRequestBean[] newArray(int i) {
            return new GetPreAutorizacionesBodyRequestBean[i];
        }
    };
    @SerializedName("codEstado")
    private String codEstado;
    @SerializedName("nroPagina")
    private String nroPagina;
    @SerializedName("tipoConsulta")
    private String tipoConsulta;

    public int describeContents() {
        return 0;
    }

    protected GetPreAutorizacionesBodyRequestBean(Parcel parcel) {
    }

    public GetPreAutorizacionesBodyRequestBean(String str, String str2, String str3) {
        this.tipoConsulta = str;
        this.codEstado = str2;
        this.nroPagina = str3;
    }

    public GetPreAutorizacionesBodyRequestBean(String str) {
        this.tipoConsulta = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoConsulta);
        parcel.writeString(this.codEstado);
        parcel.writeString(this.nroPagina);
    }

    public String getTipoConsulta() {
        return this.tipoConsulta;
    }

    public void setTipoConsulta(String str) {
        this.tipoConsulta = str;
    }

    public String getCodEstado() {
        return this.codEstado;
    }

    public void setCodEstado(String str) {
        this.codEstado = str;
    }

    public String getNroPagina() {
        return this.nroPagina;
    }

    public void setNroPagina(String str) {
        this.nroPagina = str;
    }
}
