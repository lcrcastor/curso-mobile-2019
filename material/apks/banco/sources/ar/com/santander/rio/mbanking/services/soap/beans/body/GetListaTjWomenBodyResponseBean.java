package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios;
import ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.Usuario;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Iterator;

public class GetListaTjWomenBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetListaTjWomenBodyResponseBean> CREATOR = new Creator<GetListaTjWomenBodyResponseBean>() {
        public GetListaTjWomenBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetListaTjWomenBodyResponseBean(parcel);
        }

        public GetListaTjWomenBodyResponseBean[] newArray(int i) {
            return new GetListaTjWomenBodyResponseBean[i];
        }
    };
    @SerializedName("adicionalNoTitular")
    @Expose
    private String adicionalNoTitular;
    @SerializedName("listaLeyendas")
    @Expose
    private ListaLeyendas listaLeyendas;
    @SerializedName("listaUsuarios")
    @Expose
    private ListaUsuarios listaUsuarios;
    @SerializedName("suscriptoAdicional")
    @Expose
    private String suscriptoAdicional;
    @SerializedName("suscriptoTitular")
    @Expose
    private String suscriptoTitular;
    @SerializedName("titular")
    @Expose
    private String titular;

    public int describeContents() {
        return 0;
    }

    protected GetListaTjWomenBodyResponseBean(Parcel parcel) {
        this.res = (String) parcel.readValue(String.class.getClassLoader());
        this.titular = (String) parcel.readValue(String.class.getClassLoader());
        this.suscriptoTitular = (String) parcel.readValue(String.class.getClassLoader());
        this.suscriptoAdicional = (String) parcel.readValue(String.class.getClassLoader());
        this.adicionalNoTitular = (String) parcel.readValue(String.class.getClassLoader());
        this.listaUsuarios = (ListaUsuarios) parcel.readValue(ListaUsuarios.class.getClassLoader());
        this.listaLeyendas = (ListaLeyendas) parcel.readValue(ListaLeyendas.class.getClassLoader());
    }

    public GetListaTjWomenBodyResponseBean() {
    }

    public GetListaTjWomenBodyResponseBean(String str, String str2, String str3, String str4, String str5, ListaUsuarios listaUsuarios2, ListaLeyendas listaLeyendas2) {
        this.res = str;
        this.titular = str2;
        this.suscriptoTitular = str3;
        this.suscriptoAdicional = str4;
        this.adicionalNoTitular = str5;
        this.listaUsuarios = listaUsuarios2;
        this.listaLeyendas = listaLeyendas2;
    }

    public String getRes() {
        return this.res;
    }

    public void setRes(String str) {
        this.res = str;
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String str) {
        this.titular = str;
    }

    public String getSuscriptoTitular() {
        return this.suscriptoTitular;
    }

    public void setSuscriptoTitular(String str) {
        this.suscriptoTitular = str;
    }

    public String getSuscriptoAdicional() {
        return this.suscriptoAdicional;
    }

    public void setSuscriptoAdicional(String str) {
        this.suscriptoAdicional = str;
    }

    public String getAdicionalNoTitular() {
        return this.adicionalNoTitular;
    }

    public void setAdicionalNoTitular(String str) {
        this.adicionalNoTitular = str;
    }

    public ListaUsuarios getListaUsuarios() {
        return this.listaUsuarios;
    }

    public void setListaUsuarios(ListaUsuarios listaUsuarios2) {
        this.listaUsuarios = listaUsuarios2;
    }

    public ListaLeyendas getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendas listaLeyendas2) {
        this.listaLeyendas = listaLeyendas2;
    }

    public Leyenda getLeyendaByTag(String str) {
        for (Leyenda leyenda : this.listaLeyendas.getLstLeyendas()) {
            if (leyenda.idLeyenda.equals(str)) {
                return leyenda;
            }
        }
        return Leyenda.LeyendaEmpty();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.res);
        parcel.writeValue(this.titular);
        parcel.writeValue(this.suscriptoTitular);
        parcel.writeValue(this.suscriptoAdicional);
        parcel.writeValue(this.adicionalNoTitular);
        parcel.writeValue(this.listaUsuarios);
        parcel.writeValue(this.listaLeyendas);
    }

    public boolean hasAtLeastOneWomenMark() {
        for (Usuario listaTarjetas : this.listaUsuarios.getUsuario()) {
            Iterator it = listaTarjetas.getListaTarjetas().getTarjeta().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((Tarjeta) it.next()).getMarcaWomen().equalsIgnoreCase("1")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
