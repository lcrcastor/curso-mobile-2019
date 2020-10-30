package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AbmTurnoBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<AbmTurnoBodyResponseBean> CREATOR = new Creator<AbmTurnoBodyResponseBean>() {
        public AbmTurnoBodyResponseBean createFromParcel(Parcel parcel) {
            return new AbmTurnoBodyResponseBean(parcel);
        }

        public AbmTurnoBodyResponseBean[] newArray(int i) {
            return new AbmTurnoBodyResponseBean[i];
        }
    };
    @SerializedName("fechaOperacion")
    private String fechaOperacion;
    @SerializedName("horaOperacion")
    private String horaOperacion;
    @SerializedName("listaLeyendas")
    private ListaLegalesBean listaLegales;
    @SerializedName("nroComprobante")
    private String nroComprobante;
    @SerializedName("turno")
    private TurnoBean turno;

    public int describeContents() {
        return 0;
    }

    public AbmTurnoBodyResponseBean() {
    }

    public AbmTurnoBodyResponseBean(String str, String str2, String str3, TurnoBean turnoBean, ListaLegalesBean listaLegalesBean) {
        this.nroComprobante = str;
        this.fechaOperacion = str2;
        this.horaOperacion = str3;
        this.turno = turnoBean;
        this.listaLegales = listaLegalesBean;
    }

    protected AbmTurnoBodyResponseBean(Parcel parcel) {
        this.nroComprobante = parcel.readString();
        this.fechaOperacion = parcel.readString();
        this.horaOperacion = parcel.readString();
        this.turno = (TurnoBean) parcel.readParcelable(TurnoBean.class.getClassLoader());
        this.listaLegales = (ListaLegalesBean) parcel.readParcelable(ListaLegalesBean.class.getClassLoader());
    }

    public void setNroComprobante(String str) {
        this.nroComprobante = str;
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public void setFechaOperacion(String str) {
        this.fechaOperacion = str;
    }

    public String getFechaOperacion() {
        return this.fechaOperacion;
    }

    public void setHoraOperacion(String str) {
        this.horaOperacion = str;
    }

    public String getHoraOperacion() {
        return this.horaOperacion;
    }

    public void setTurno(TurnoBean turnoBean) {
        this.turno = turnoBean;
    }

    public TurnoBean getTurno() {
        return this.turno;
    }

    public ListaLegalesBean getListaLegales() {
        return this.listaLegales;
    }

    public void setListaLegales(ListaLegalesBean listaLegalesBean) {
        this.listaLegales = listaLegalesBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nroComprobante);
        parcel.writeString(this.fechaOperacion);
        parcel.writeString(this.horaOperacion);
        parcel.writeParcelable(this.turno, i);
        parcel.writeParcelable(this.listaLegales, i);
    }
}
