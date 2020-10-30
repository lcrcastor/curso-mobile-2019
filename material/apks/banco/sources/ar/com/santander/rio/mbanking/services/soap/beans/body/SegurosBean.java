package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SegurosBean implements Parcelable {
    public static final Creator<SegurosBean> CREATOR = new Creator<SegurosBean>() {
        public SegurosBean createFromParcel(Parcel parcel) {
            return new SegurosBean(parcel);
        }

        public SegurosBean[] newArray(int i) {
            return new SegurosBean[i];
        }
    };
    @SerializedName("cantidad")
    private String cantidad;
    @SerializedName("linkSeguro")
    private LinkSeguroBean linkSeguroBean;
    @SerializedName("listaBotones")
    private List<BotonSeguroBean> listaBotones;
    @SerializedName("listaObjetos")
    private List<SeguroObjetoBean> listaObjetos;
    @SerializedName("seguro")
    private List<SeguroBean> listaSeguros;
    @SerializedName("urlSeguimiento")
    private String urlSeguimiento;
    @SerializedName("urlSiniestro")
    private String urlSiniestro;

    public int describeContents() {
        return 0;
    }

    public SegurosBean() {
    }

    public SegurosBean(List<SeguroBean> list, List<BotonSeguroBean> list2, LinkSeguroBean linkSeguroBean2, String str, String str2) {
        this.listaSeguros = list;
        this.listaBotones = list2;
        this.linkSeguroBean = linkSeguroBean2;
        this.urlSeguimiento = str;
        this.urlSiniestro = str2;
    }

    public SegurosBean(List<SeguroBean> list, List<BotonSeguroBean> list2, LinkSeguroBean linkSeguroBean2, String str, String str2, String str3) {
        this.listaSeguros = list;
        this.listaBotones = list2;
        this.linkSeguroBean = linkSeguroBean2;
        this.cantidad = str;
        this.urlSeguimiento = str2;
        this.urlSiniestro = str3;
    }

    public SegurosBean(List<SeguroBean> list, List<BotonSeguroBean> list2, LinkSeguroBean linkSeguroBean2, String str, List<SeguroObjetoBean> list3, String str2, String str3) {
        this.listaSeguros = list;
        this.listaBotones = list2;
        this.linkSeguroBean = linkSeguroBean2;
        this.cantidad = str;
        this.listaObjetos = list3;
        this.urlSeguimiento = str2;
        this.urlSiniestro = str3;
    }

    public SegurosBean(List<SeguroBean> list) {
        this.listaSeguros = list;
    }

    protected SegurosBean(Parcel parcel) {
        this.cantidad = parcel.readString();
        this.listaSeguros = parcel.createTypedArrayList(SeguroBean.CREATOR);
        this.listaObjetos = parcel.createTypedArrayList(SeguroObjetoBean.CREATOR);
        this.listaBotones = parcel.createTypedArrayList(BotonSeguroBean.CREATOR);
        this.linkSeguroBean = (LinkSeguroBean) parcel.readParcelable(LinkSeguroBean.class.getClassLoader());
        this.urlSeguimiento = parcel.readString();
        this.urlSiniestro = parcel.readString();
    }

    public List<SeguroBean> getListaSeguros() {
        return this.listaSeguros;
    }

    public void setListaSeguros(List<SeguroBean> list) {
        this.listaSeguros = list;
    }

    public List<BotonSeguroBean> getListaBotones() {
        return this.listaBotones;
    }

    public void setListaBotones(List<BotonSeguroBean> list) {
        this.listaBotones = list;
    }

    public LinkSeguroBean getLinkSeguroBean() {
        return this.linkSeguroBean;
    }

    public void setLinkSeguroBean(LinkSeguroBean linkSeguroBean2) {
        this.linkSeguroBean = linkSeguroBean2;
    }

    public String getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(String str) {
        this.cantidad = str;
    }

    public List<SeguroObjetoBean> getListaObjetos() {
        return this.listaObjetos;
    }

    public void setListaObjetos(List<SeguroObjetoBean> list) {
        this.listaObjetos = list;
    }

    public String getUrlSeguimiento() {
        return this.urlSeguimiento;
    }

    public void setUrlSeguimiento(String str) {
        this.urlSeguimiento = str;
    }

    public String getUrlSiniestro() {
        return this.urlSiniestro;
    }

    public void setUrlSiniestro(String str) {
        this.urlSiniestro = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.cantidad);
        parcel.writeTypedList(this.listaSeguros);
        parcel.writeTypedList(this.listaObjetos);
        parcel.writeTypedList(this.listaBotones);
        parcel.writeParcelable(this.linkSeguroBean, i);
        parcel.writeString(this.urlSeguimiento);
        parcel.writeString(this.urlSiniestro);
    }
}
