package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ConfirmarRecalificacionBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<ConfirmarRecalificacionBodyResponseBean> CREATOR = new Creator<ConfirmarRecalificacionBodyResponseBean>() {
        public ConfirmarRecalificacionBodyResponseBean createFromParcel(Parcel parcel) {
            return new ConfirmarRecalificacionBodyResponseBean(parcel);
        }

        public ConfirmarRecalificacionBodyResponseBean[] newArray(int i) {
            return new ConfirmarRecalificacionBodyResponseBean[i];
        }
    };
    public String fechaSolicitud;
    public String limiteSinAsignar;
    public ListaLeyendas listaLeyendas;
    public ListaProductosRecalificacionBean listaProductos;
    public String nroComprobante;
    public String nuevaLineaCrediticia;

    public int describeContents() {
        return 0;
    }

    public ConfirmarRecalificacionBodyResponseBean() {
    }

    protected ConfirmarRecalificacionBodyResponseBean(Parcel parcel) {
        this.nuevaLineaCrediticia = parcel.readString();
        this.listaProductos = (ListaProductosRecalificacionBean) parcel.readParcelable(ListaProductosRecalificacionBean.class.getClassLoader());
        this.limiteSinAsignar = parcel.readString();
        this.fechaSolicitud = parcel.readString();
        this.nroComprobante = parcel.readString();
        this.listaLeyendas = (ListaLeyendas) parcel.readParcelable(ListaLeyendas.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nuevaLineaCrediticia);
        parcel.writeParcelable(this.listaProductos, i);
        parcel.writeString(this.limiteSinAsignar);
        parcel.writeString(this.fechaSolicitud);
        parcel.writeString(this.nroComprobante);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
