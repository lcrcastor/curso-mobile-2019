package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetDebinesBodyRequestBean implements Parcelable {
    public static final Creator<GetDebinesBodyRequestBean> CREATOR = new Creator<GetDebinesBodyRequestBean>() {
        public GetDebinesBodyRequestBean createFromParcel(Parcel parcel) {
            return new GetDebinesBodyRequestBean(parcel);
        }

        public GetDebinesBodyRequestBean[] newArray(int i) {
            return new GetDebinesBodyRequestBean[i];
        }
    };
    @SerializedName("codEstado")
    private String codEstado;
    @SerializedName("fechaDesde")
    private String fechaDesde;
    @SerializedName("fechaHasta")
    private String fechaHasta;
    @SerializedName("nroPagina")
    private String nroPagina;
    @SerializedName("tipoConsulta")
    private String tipoConsulta;

    public int describeContents() {
        return 0;
    }

    public GetDebinesBodyRequestBean(String str, String str2, String str3, String str4, String str5) {
        this.tipoConsulta = str;
        this.fechaDesde = str2;
        this.fechaHasta = str3;
        this.codEstado = str4;
        this.nroPagina = str5;
    }

    public GetDebinesBodyRequestBean(String str) {
        this.tipoConsulta = str;
    }

    public GetDebinesBodyRequestBean() {
    }

    protected GetDebinesBodyRequestBean(Parcel parcel) {
        this.tipoConsulta = parcel.readString();
        this.fechaDesde = parcel.readString();
        this.fechaHasta = parcel.readString();
        this.codEstado = parcel.readString();
        this.nroPagina = parcel.readString();
    }

    public String getTipoConsulta() {
        return this.tipoConsulta;
    }

    public void setTipoConsulta(String str) {
        this.tipoConsulta = str;
    }

    public String getFechaDesde() {
        return this.fechaDesde;
    }

    public void setFechaDesde(String str) {
        this.fechaDesde = str;
    }

    public String getFechaHasta() {
        return this.fechaHasta;
    }

    public void setFechaHasta(String str) {
        this.fechaHasta = str;
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

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoConsulta);
        parcel.writeString(this.fechaDesde);
        parcel.writeString(this.fechaHasta);
        parcel.writeString(this.codEstado);
        parcel.writeString(this.nroPagina);
    }
}
