package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetTurnoBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetTurnoBodyResponseBean> CREATOR = new Creator<GetTurnoBodyResponseBean>() {
        public GetTurnoBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetTurnoBodyResponseBean(parcel);
        }

        public GetTurnoBodyResponseBean[] newArray(int i) {
            return new GetTurnoBodyResponseBean[i];
        }
    };
    @SerializedName("listaLeyendas")
    private ListaLegalesBean listaLegales;
    @SerializedName("turno")
    private TurnoBean turno;

    public int describeContents() {
        return 0;
    }

    public GetTurnoBodyResponseBean() {
    }

    public GetTurnoBodyResponseBean(TurnoBean turnoBean, ListaLegalesBean listaLegalesBean) {
        this.turno = turnoBean;
        this.listaLegales = listaLegalesBean;
    }

    protected GetTurnoBodyResponseBean(Parcel parcel) {
        this.turno = (TurnoBean) parcel.readParcelable(TurnoBean.class.getClassLoader());
        this.listaLegales = (ListaLegalesBean) parcel.readParcelable(ListaLegalesBean.class.getClassLoader());
    }

    public TurnoBean getTurno() {
        return this.turno;
    }

    public void setTurno(TurnoBean turnoBean) {
        this.turno = turnoBean;
    }

    public ListaLegalesBean getListaLegales() {
        return this.listaLegales;
    }

    public void setListaLegales(ListaLegalesBean listaLegalesBean) {
        this.listaLegales = listaLegalesBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.turno, i);
        parcel.writeParcelable(this.listaLegales, i);
    }
}
