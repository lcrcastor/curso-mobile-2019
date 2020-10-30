package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuscripcionWomenBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<SuscripcionWomenBodyResponseBean> CREATOR = new Creator<SuscripcionWomenBodyResponseBean>() {
        public SuscripcionWomenBodyResponseBean createFromParcel(Parcel parcel) {
            return new SuscripcionWomenBodyResponseBean(parcel);
        }

        public SuscripcionWomenBodyResponseBean[] newArray(int i) {
            return new SuscripcionWomenBodyResponseBean[i];
        }
    };
    @SerializedName("fechaOperacion")
    @Expose
    private String fechaOperacion;
    @SerializedName("listaLeyendas")
    @Expose
    private ListaLeyendas listaLeyendas;
    @SerializedName("nroComprobante")
    @Expose
    private String nroComprobante;
    @SerializedName("res")
    @Expose
    private String res;

    public int describeContents() {
        return 0;
    }

    private SuscripcionWomenBodyResponseBean(Parcel parcel) {
        this.res = (String) parcel.readValue(String.class.getClassLoader());
        this.nroComprobante = (String) parcel.readValue(String.class.getClassLoader());
        this.fechaOperacion = parcel.readString();
        this.listaLeyendas = (ListaLeyendas) parcel.readValue(ListaLeyendas.class.getClassLoader());
    }

    public SuscripcionWomenBodyResponseBean() {
    }

    public SuscripcionWomenBodyResponseBean(String str, String str2, ListaLeyendas listaLeyendas2) {
        this.res = str;
        this.nroComprobante = str2;
        this.listaLeyendas = listaLeyendas2;
    }

    public String getRes() {
        return this.res;
    }

    public void setRes(String str) {
        this.res = str;
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public void setNroComprobante(String str) {
        this.nroComprobante = str;
    }

    public ListaLeyendas getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendas listaLeyendas2) {
        this.listaLeyendas = listaLeyendas2;
    }

    public String getFechaOperacion() {
        return this.fechaOperacion;
    }

    public void setFechaOperacion(String str) {
        this.fechaOperacion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.res);
        parcel.writeValue(this.nroComprobante);
        parcel.writeString(this.fechaOperacion);
        parcel.writeValue(this.listaLeyendas);
    }

    public Leyenda getLeyendaByTag(String str) {
        for (Leyenda leyenda : this.listaLeyendas.getLstLeyendas()) {
            if (leyenda.idLeyenda.equals(str)) {
                return leyenda;
            }
        }
        return Leyenda.LeyendaEmpty();
    }
}
