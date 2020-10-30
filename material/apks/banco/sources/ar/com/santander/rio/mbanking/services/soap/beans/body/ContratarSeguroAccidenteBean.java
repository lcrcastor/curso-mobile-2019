package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ContratarSeguroAccidenteBean implements Parcelable {
    public static final Creator<ContratarSeguroAccidenteBean> CREATOR = new Creator<ContratarSeguroAccidenteBean>() {
        public ContratarSeguroAccidenteBean createFromParcel(Parcel parcel) {
            return new ContratarSeguroAccidenteBean(parcel);
        }

        public ContratarSeguroAccidenteBean[] newArray(int i) {
            return new ContratarSeguroAccidenteBean[i];
        }
    };
    @SerializedName("cuota")
    private String cuota;
    @SerializedName("fechaInicio")
    private String fechaInicio;
    @SerializedName("listaLeyendas")
    ListaLeyendaSeguroAccidente listaLeyendas;
    @SerializedName("numCertificado")
    private String numCertificado;
    @SerializedName("numPoliza")
    private String numPoliza;

    public int describeContents() {
        return 0;
    }

    public ContratarSeguroAccidenteBean(String str, String str2, String str3, String str4, ListaLeyendaSeguroAccidente listaLeyendaSeguroAccidente) {
        this.fechaInicio = str;
        this.numPoliza = str2;
        this.numCertificado = str3;
        this.cuota = str4;
        this.listaLeyendas = listaLeyendaSeguroAccidente;
    }

    public ContratarSeguroAccidenteBean() {
    }

    protected ContratarSeguroAccidenteBean(Parcel parcel) {
        this.fechaInicio = parcel.readString();
        this.numPoliza = parcel.readString();
        this.numCertificado = parcel.readString();
        this.cuota = parcel.readString();
        this.listaLeyendas = (ListaLeyendaSeguroAccidente) parcel.readParcelable(ListaLeyendaSeguroAccidente.class.getClassLoader());
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String str) {
        this.fechaInicio = str;
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

    public ListaLeyendaSeguroAccidente getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendaSeguroAccidente listaLeyendaSeguroAccidente) {
        this.listaLeyendas = listaLeyendaSeguroAccidente;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fechaInicio);
        parcel.writeString(this.numPoliza);
        parcel.writeString(this.numCertificado);
        parcel.writeString(this.cuota);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
