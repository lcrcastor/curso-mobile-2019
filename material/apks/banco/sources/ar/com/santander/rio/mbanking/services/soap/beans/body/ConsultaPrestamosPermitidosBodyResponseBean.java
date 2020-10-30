package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamos;
import ar.com.santander.rio.mbanking.services.model.creditos.PrestamosPermitidos;
import ar.com.santander.rio.mbanking.services.model.general.ListaLeyenda;
import com.google.gson.annotations.SerializedName;

public class ConsultaPrestamosPermitidosBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<ConsultaPrestamosPermitidosBodyResponseBean> CREATOR = new Creator<ConsultaPrestamosPermitidosBodyResponseBean>() {
        public ConsultaPrestamosPermitidosBodyResponseBean createFromParcel(Parcel parcel) {
            return new ConsultaPrestamosPermitidosBodyResponseBean(parcel);
        }

        public ConsultaPrestamosPermitidosBodyResponseBean[] newArray(int i) {
            return new ConsultaPrestamosPermitidosBodyResponseBean[i];
        }
    };
    @SerializedName("datosCuenta")
    AccountRequestBean datosCuenta;
    @SerializedName("datosPrest")
    DatosPrestamos datosPrestamos;
    @SerializedName("listaLeyendas")
    ListaLeyenda listaLeyenda;
    @SerializedName("prestPermitidos")
    PrestamosPermitidos prestamosPermitidos;

    public int describeContents() {
        return 0;
    }

    public ConsultaPrestamosPermitidosBodyResponseBean(Parcel parcel) {
        this.datosCuenta = (AccountRequestBean) parcel.readParcelable(AccountRequestBean.class.getClassLoader());
        this.datosPrestamos = (DatosPrestamos) parcel.readParcelable(DatosPrestamos.class.getClassLoader());
        this.prestamosPermitidos = (PrestamosPermitidos) parcel.readParcelable(PrestamosPermitidos.class.getClassLoader());
        this.listaLeyenda = (ListaLeyenda) parcel.readParcelable(ListaLeyenda.class.getClassLoader());
    }

    public ConsultaPrestamosPermitidosBodyResponseBean() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.datosCuenta, i);
        parcel.writeParcelable(this.datosPrestamos, i);
        parcel.writeParcelable(this.prestamosPermitidos, i);
        parcel.writeParcelable(this.listaLeyenda, i);
    }

    public AccountRequestBean getDatosCuenta() {
        return this.datosCuenta;
    }

    public void setDatosCuenta(AccountRequestBean accountRequestBean) {
        this.datosCuenta = accountRequestBean;
    }

    public DatosPrestamos getDatosPrestamos() {
        return this.datosPrestamos;
    }

    public void setDatosPrestamos(DatosPrestamos datosPrestamos2) {
        this.datosPrestamos = datosPrestamos2;
    }

    public PrestamosPermitidos getPrestamosPermitidos() {
        return this.prestamosPermitidos;
    }

    public void setPrestamosPermitidos(PrestamosPermitidos prestamosPermitidos2) {
        this.prestamosPermitidos = prestamosPermitidos2;
    }

    public ListaLeyenda getListaLeyenda() {
        return this.listaLeyenda;
    }

    public void setListaLeyenda(ListaLeyenda listaLeyenda2) {
        this.listaLeyenda = listaLeyenda2;
    }
}
