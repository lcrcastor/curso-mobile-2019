package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ExtEnvFiltroBean implements Parcelable {
    public static final Creator<ExtEnvFiltroBean> CREATOR = new Creator<ExtEnvFiltroBean>() {
        public ExtEnvFiltroBean createFromParcel(Parcel parcel) {
            return new ExtEnvFiltroBean(parcel);
        }

        public ExtEnvFiltroBean[] newArray(int i) {
            return new ExtEnvFiltroBean[i];
        }
    };
    @SerializedName("estado")
    public String estado;
    @SerializedName("fechaAltaDesde")
    public String fechaAltaDesde;
    @SerializedName("fechaAltaHasta")
    public String fechaAltaHasta;
    @SerializedName("importeDesde")
    public String importeDesde;
    @SerializedName("importeHasta")
    public String importeHasta;
    @SerializedName("listaDestinatario")
    public ConsultaAgendadosEnvEfeListaDestinatariosBean listaDestinatario;

    public int describeContents() {
        return 0;
    }

    public ExtEnvFiltroBean() {
    }

    public ExtEnvFiltroBean(Parcel parcel) {
        this.estado = parcel.readString();
        this.fechaAltaDesde = parcel.readString();
        this.fechaAltaHasta = parcel.readString();
        this.importeDesde = parcel.readString();
        this.importeHasta = parcel.readString();
        this.listaDestinatario = (ConsultaAgendadosEnvEfeListaDestinatariosBean) parcel.readParcelable(ConsultaAgendadosEnvEfeListaDestinatariosBean.class.getClassLoader());
    }

    public ExtEnvFiltroBean(String str, String str2, String str3, String str4, String str5, ConsultaAgendadosEnvEfeListaDestinatariosBean consultaAgendadosEnvEfeListaDestinatariosBean) {
        this.estado = str;
        this.fechaAltaDesde = str2;
        this.fechaAltaHasta = str3;
        this.importeDesde = str4;
        this.importeHasta = str5;
        this.listaDestinatario = consultaAgendadosEnvEfeListaDestinatariosBean;
    }

    public boolean equals(ExtEnvFiltroBean extEnvFiltroBean) {
        return this.estado.equals(extEnvFiltroBean.estado) && this.fechaAltaDesde.equals(extEnvFiltroBean.fechaAltaDesde) && this.fechaAltaHasta.equals(extEnvFiltroBean.fechaAltaHasta) && this.importeDesde.equals(extEnvFiltroBean.importeDesde) && this.importeHasta.equals(extEnvFiltroBean.importeHasta) && this.listaDestinatario.equals(extEnvFiltroBean.listaDestinatario);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.estado);
        parcel.writeString(this.fechaAltaDesde);
        parcel.writeString(this.fechaAltaHasta);
        parcel.writeString(this.importeDesde);
        parcel.writeString(this.importeHasta);
        parcel.writeParcelable(this.listaDestinatario, i);
    }
}
