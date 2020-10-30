package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class RecargaCelularesBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<RecargaCelularesBodyResponseBean> CREATOR = new Creator<RecargaCelularesBodyResponseBean>() {
        public RecargaCelularesBodyResponseBean createFromParcel(Parcel parcel) {
            return new RecargaCelularesBodyResponseBean(parcel);
        }

        public RecargaCelularesBodyResponseBean[] newArray(int i) {
            return new RecargaCelularesBodyResponseBean[i];
        }
    };
    public String alertaDA;
    public String anticipoCuota;
    public String cuit;
    public String cur;
    public String descCtaDebito;
    public String empDescr;
    public String empServ;
    public String factura;
    public String fecha;
    public String fechaHora;
    public String hora;
    public String identificacion;
    public String importe;
    public String infoICS;
    public String leyendaComp;
    @SerializedName("linkSeguro")
    LinkSeguroBean linkSeguro;
    @SerializedName("leyenda")
    private ListaLeyendas lstLeyendas;
    public String mensajeDA;
    public String moneda;
    public String nroComp;
    public String nroControl;
    public String numVep;
    public String numeroCta;
    public String periodo;
    public String sucursal;

    public int describeContents() {
        return 0;
    }

    public RecargaCelularesBodyResponseBean() {
    }

    public RecargaCelularesBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.nroControl = str;
        this.nroComp = str2;
        this.empServ = str3;
        this.moneda = str4;
        this.importe = str5;
        this.sucursal = str6;
        this.numeroCta = str7;
    }

    public RecargaCelularesBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, ListaLeyendas listaLeyendas, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21) {
        this.nroControl = str;
        this.nroComp = str2;
        this.empServ = str3;
        this.moneda = str4;
        this.importe = str5;
        this.sucursal = str6;
        this.numeroCta = str7;
        this.fechaHora = str8;
        this.lstLeyendas = listaLeyendas;
        this.empDescr = str9;
        this.identificacion = str10;
        this.descCtaDebito = str11;
        this.factura = str12;
        this.cur = str13;
        this.numVep = str14;
        this.infoICS = str15;
        this.cuit = str16;
        this.periodo = str17;
        this.anticipoCuota = str18;
        this.fecha = str19;
        this.hora = str20;
        this.leyendaComp = str21;
    }

    private RecargaCelularesBodyResponseBean(Parcel parcel) {
        this.nroControl = parcel.readString();
        this.nroComp = parcel.readString();
        this.empServ = parcel.readString();
        this.moneda = parcel.readString();
        this.importe = parcel.readString();
        this.sucursal = parcel.readString();
        this.numeroCta = parcel.readString();
        this.fechaHora = parcel.readString();
        this.lstLeyendas = (ListaLeyendas) parcel.readParcelable(ListaLeyendas.class.getClassLoader());
        this.empDescr = parcel.readString();
        this.identificacion = parcel.readString();
        this.descCtaDebito = parcel.readString();
        this.factura = parcel.readString();
        this.cur = parcel.readString();
        this.numVep = parcel.readString();
        this.infoICS = parcel.readString();
        this.cuit = parcel.readString();
        this.periodo = parcel.readString();
        this.anticipoCuota = parcel.readString();
        this.fecha = parcel.readString();
        this.hora = parcel.readString();
        this.leyendaComp = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nroControl);
        parcel.writeString(this.nroComp);
        parcel.writeString(this.empServ);
        parcel.writeString(this.moneda);
        parcel.writeString(this.importe);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.numeroCta);
        parcel.writeString(this.fechaHora);
        parcel.writeParcelable(this.lstLeyendas, i);
        parcel.writeString(this.empDescr);
        parcel.writeString(this.identificacion);
        parcel.writeString(this.descCtaDebito);
        parcel.writeString(this.factura);
        parcel.writeString(this.cur);
        parcel.writeString(this.numVep);
        parcel.writeString(this.infoICS);
        parcel.writeString(this.cuit);
        parcel.writeString(this.periodo);
        parcel.writeString(this.anticipoCuota);
        parcel.writeString(this.fecha);
        parcel.writeString(this.hora);
        parcel.writeString(this.leyendaComp);
    }

    public ListaLeyendas getLstLeyendas() {
        return this.lstLeyendas;
    }

    public void setLstLeyendas(ListaLeyendas listaLeyendas) {
        this.lstLeyendas = listaLeyendas;
    }

    public String getFactura() {
        return this.factura;
    }

    public void setFactura(String str) {
        this.factura = str;
    }

    public String getCur() {
        return this.cur;
    }

    public void setCur(String str) {
        this.cur = str;
    }

    public String getNumVep() {
        return this.numVep;
    }

    public void setNumVep(String str) {
        this.numVep = str;
    }

    public String getCuit() {
        return this.cuit;
    }

    public void setCuit(String str) {
        this.cuit = str;
    }

    public String getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(String str) {
        this.periodo = str;
    }

    public String getAnticipoCuota() {
        return this.anticipoCuota;
    }

    public void setAnticipoCuota(String str) {
        this.anticipoCuota = str;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public String getNumeroCta() {
        return this.numeroCta;
    }

    public void setNumeroCta(String str) {
        this.numeroCta = str;
    }

    public ListaLeyendas getListaLeyendas() {
        return this.lstLeyendas;
    }

    public void setListaLeyendas(ListaLeyendas listaLeyendas) {
        this.lstLeyendas = listaLeyendas;
    }

    public LinkSeguroBean getLinkSeguro() {
        return this.linkSeguro;
    }

    public void setLinkSeguro(LinkSeguroBean linkSeguroBean) {
        this.linkSeguro = linkSeguroBean;
    }
}
