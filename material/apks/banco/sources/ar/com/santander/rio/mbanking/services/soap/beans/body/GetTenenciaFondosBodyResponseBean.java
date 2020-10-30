package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetTenenciaFondosBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetTenenciaFondosBodyResponseBean> CREATOR = new Creator<GetTenenciaFondosBodyResponseBean>() {
        public GetTenenciaFondosBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetTenenciaFondosBodyResponseBean(parcel);
        }

        public GetTenenciaFondosBodyResponseBean[] newArray(int i) {
            return new GetTenenciaFondosBodyResponseBean[i];
        }
    };
    @SerializedName("contratoSusc")
    public String contratoSusc;
    @SerializedName("contratoTransf")
    public String contratoTransf;
    @SerializedName("listaCtaOperativa")
    public ListaCtaOperativaBean listaCtaOperativaBean;
    @SerializedName("cuentas")
    public ListaCuentasFondosBean listaCuentasFondosBean;
    @SerializedName("leyenda")
    public ListaLeyendas listaLeyendas;

    public int describeContents() {
        return 0;
    }

    public GetTenenciaFondosBodyResponseBean() {
    }

    public GetTenenciaFondosBodyResponseBean(String str, String str2, ListaCuentasFondosBean listaCuentasFondosBean2, ListaCtaOperativaBean listaCtaOperativaBean2, ListaLeyendas listaLeyendas2) {
        this.contratoSusc = str;
        this.contratoTransf = str2;
        this.listaCuentasFondosBean = listaCuentasFondosBean2;
        this.listaCtaOperativaBean = listaCtaOperativaBean2;
        this.listaLeyendas = listaLeyendas2;
    }

    protected GetTenenciaFondosBodyResponseBean(Parcel parcel) {
        this.contratoSusc = parcel.readString();
        this.contratoTransf = parcel.readString();
        this.listaLeyendas = (ListaLeyendas) parcel.readParcelable(ListaLeyendas.class.getClassLoader());
    }

    public String getContratoSusc() {
        return this.contratoSusc;
    }

    public void setContratoSusc(String str) {
        this.contratoSusc = str;
    }

    public String getContratoTransf() {
        return this.contratoTransf;
    }

    public void setContratoTransf(String str) {
        this.contratoTransf = str;
    }

    public ListaCuentasFondosBean getListaCuentasFondosBean() {
        return this.listaCuentasFondosBean;
    }

    public void setListaCuentasFondosBean(ListaCuentasFondosBean listaCuentasFondosBean2) {
        this.listaCuentasFondosBean = listaCuentasFondosBean2;
    }

    public ListaCtaOperativaBean getListaCtaOperativaBean() {
        return this.listaCtaOperativaBean;
    }

    public void setListaCtaOperativaBean(ListaCtaOperativaBean listaCtaOperativaBean2) {
        this.listaCtaOperativaBean = listaCtaOperativaBean2;
    }

    public ListaLeyendas getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendas listaLeyendas2) {
        this.listaLeyendas = listaLeyendas2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.contratoSusc);
        parcel.writeString(this.contratoTransf);
        parcel.writeParcelable(this.listaLeyendas, i);
    }
}
