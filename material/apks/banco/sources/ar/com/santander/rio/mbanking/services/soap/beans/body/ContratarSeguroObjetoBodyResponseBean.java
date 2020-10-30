package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContratarSeguroObjetoBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<ContratarSeguroObjetoBodyResponseBean> CREATOR = new Creator<ContratarSeguroObjetoBodyResponseBean>() {
        public ContratarSeguroObjetoBodyResponseBean createFromParcel(Parcel parcel) {
            return new ContratarSeguroObjetoBodyResponseBean(parcel);
        }

        public ContratarSeguroObjetoBodyResponseBean[] newArray(int i) {
            return new ContratarSeguroObjetoBodyResponseBean[i];
        }
    };
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("cuota")
    private String cuota;
    @SerializedName("fechaInicio")
    private String fechaInicio;
    @SerializedName("listaPreguntas")
    @Expose
    private ListaPreguntasFamilia listaPreguntas;
    @SerializedName("numCertificado")
    private String numCertificado;
    @SerializedName("numPoliza")
    private String numPoliza;

    public int describeContents() {
        return 0;
    }

    public ContratarSeguroObjetoBodyResponseBean() {
    }

    protected ContratarSeguroObjetoBodyResponseBean(Parcel parcel) {
        this.fechaInicio = parcel.readString();
        this.codRamo = parcel.readString();
        this.numPoliza = parcel.readString();
        this.numCertificado = parcel.readString();
        this.cuota = parcel.readString();
        this.listaPreguntas = (ListaPreguntasFamilia) parcel.readParcelable(ListaPreguntasFamilia.class.getClassLoader());
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String str) {
        this.fechaInicio = str;
    }

    public String getCodRamo() {
        return this.codRamo;
    }

    public void setCodRamo(String str) {
        this.codRamo = str;
    }

    public String getNumPoliza() {
        return this.numPoliza;
    }

    public void setNumPoliza(String str) {
        this.numPoliza = str;
    }

    public String getNumCertificado() {
        return this.numCertificado;
    }

    public void setNumCertificado(String str) {
        this.numCertificado = str;
    }

    public String getCuota() {
        return this.cuota;
    }

    public void setCuota(String str) {
        this.cuota = str;
    }

    public ListaPreguntasFamilia getListaPreguntas() {
        return this.listaPreguntas;
    }

    public void setListaPreguntas(ListaPreguntasFamilia listaPreguntasFamilia) {
        this.listaPreguntas = listaPreguntasFamilia;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fechaInicio);
        parcel.writeString(this.codRamo);
        parcel.writeString(this.numPoliza);
        parcel.writeString(this.numCertificado);
        parcel.writeString(this.cuota);
        parcel.writeParcelable(this.listaPreguntas, i);
    }
}
