package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetCotizacionSeguroAccidenteBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetCotizacionSeguroAccidenteBodyResponseBean> CREATOR = new Creator<GetCotizacionSeguroAccidenteBodyResponseBean>() {
        public GetCotizacionSeguroAccidenteBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetCotizacionSeguroAccidenteBodyResponseBean(parcel);
        }

        public GetCotizacionSeguroAccidenteBodyResponseBean[] newArray(int i) {
            return new GetCotizacionSeguroAccidenteBodyResponseBean[i];
        }
    };
    @SerializedName("cotizacion")
    private CotizacionSeguroAccidenteBean cotizacion;
    @SerializedName("listaLeyendas")
    ListaLeyendaSeguroAccidente listaLeyendas;

    public int describeContents() {
        return 0;
    }

    public GetCotizacionSeguroAccidenteBodyResponseBean() {
    }

    public GetCotizacionSeguroAccidenteBodyResponseBean(CotizacionSeguroAccidenteBean cotizacionSeguroAccidenteBean) {
        this.cotizacion = cotizacionSeguroAccidenteBean;
    }

    protected GetCotizacionSeguroAccidenteBodyResponseBean(Parcel parcel) {
        this.cotizacion = (CotizacionSeguroAccidenteBean) parcel.readParcelable(CotizacionSeguroAccidenteBean.class.getClassLoader());
        this.listaLeyendas = (ListaLeyendaSeguroAccidente) parcel.readParcelable(ListaLeyendaSeguroAccidente.class.getClassLoader());
    }

    public CotizacionSeguroAccidenteBean getCotizacion() {
        return this.cotizacion;
    }

    public void setCotizacion(CotizacionSeguroAccidenteBean cotizacionSeguroAccidenteBean) {
        this.cotizacion = cotizacionSeguroAccidenteBean;
    }

    public ListaLeyendaSeguroAccidente getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendaSeguroAccidente listaLeyendaSeguroAccidente) {
        this.listaLeyendas = listaLeyendaSeguroAccidente;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.cotizacion, i);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
