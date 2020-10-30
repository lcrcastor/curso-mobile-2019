package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetLimitesProductosBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetLimitesProductosBodyResponseBean> CREATOR = new Creator<GetLimitesProductosBodyResponseBean>() {
        public GetLimitesProductosBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetLimitesProductosBodyResponseBean(parcel);
        }

        public GetLimitesProductosBodyResponseBean[] newArray(int i) {
            return new GetLimitesProductosBodyResponseBean[i];
        }
    };
    public AvisoItemBean aviso;
    public String codReca;
    public int limiteSinAsignar;
    public int limiteTotalActual;
    public ListaLeyendas listaLeyendas;
    public ListaProductosRecalificacionBean listaProductos;
    public String msjCodReca;
    public String msjDescReca;
    public String msjTituloReca;
    public int nuevaLineaCrediticia;

    public int describeContents() {
        return 0;
    }

    protected GetLimitesProductosBodyResponseBean() {
    }

    protected GetLimitesProductosBodyResponseBean(Parcel parcel) {
        this.limiteTotalActual = parcel.readInt();
        this.codReca = parcel.readString();
        this.msjCodReca = parcel.readString();
        this.msjTituloReca = parcel.readString();
        this.msjDescReca = parcel.readString();
        this.nuevaLineaCrediticia = parcel.readInt();
        this.limiteSinAsignar = parcel.readInt();
        this.listaProductos = (ListaProductosRecalificacionBean) parcel.readParcelable(ListaProductosRecalificacionBean.class.getClassLoader());
        this.aviso = (AvisoItemBean) parcel.readParcelable(AvisoItemBean.class.getClassLoader());
        this.listaLeyendas = (ListaLeyendas) parcel.readParcelable(ListaLeyendaSeguroMovil.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.limiteTotalActual);
        parcel.writeString(this.codReca);
        parcel.writeString(this.msjCodReca);
        parcel.writeString(this.msjTituloReca);
        parcel.writeString(this.msjDescReca);
        parcel.writeInt(this.nuevaLineaCrediticia);
        parcel.writeInt(this.limiteSinAsignar);
        parcel.writeParcelable(this.listaProductos, i);
        parcel.writeParcelable(this.aviso, i);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
