package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompradorBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VendedorBean;
import com.google.gson.annotations.SerializedName;

public class PreautorizacionBean implements Parcelable {
    public static final Creator<PreautorizacionBean> CREATOR = new Creator<PreautorizacionBean>() {
        public PreautorizacionBean createFromParcel(Parcel parcel) {
            return new PreautorizacionBean(parcel);
        }

        public PreautorizacionBean[] newArray(int i) {
            return new PreautorizacionBean[i];
        }
    };
    @SerializedName("codEstado")
    private String codEstado;
    @SerializedName("comprador")
    private CompradorBean compradorBean;
    @SerializedName("fechaCreacion")
    private String fechaCreacion;
    @SerializedName("idPreautorizacion")
    private String idPreautorizacion;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("vendedor")
    private VendedorBean vendedorBean;

    public int describeContents() {
        return 0;
    }

    public PreautorizacionBean() {
    }

    protected PreautorizacionBean(Parcel parcel) {
        this.compradorBean = (CompradorBean) parcel.readParcelable(CompradorBean.class.getClassLoader());
        this.vendedorBean = (VendedorBean) parcel.readParcelable(VendedorBean.class.getClassLoader());
        this.idPreautorizacion = parcel.readString();
        this.codEstado = parcel.readString();
        this.moneda = parcel.readString();
        this.fechaCreacion = parcel.readString();
    }

    public PreautorizacionBean(CompradorBean compradorBean2, VendedorBean vendedorBean2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.compradorBean = compradorBean2;
        this.vendedorBean = vendedorBean2;
        this.idPreautorizacion = str;
        this.codEstado = str2;
        this.moneda = str5;
        this.fechaCreacion = str7;
    }

    public CompradorBean getComprador() {
        return this.compradorBean;
    }

    public void setComprador(CompradorBean compradorBean2) {
        this.compradorBean = compradorBean2;
    }

    public VendedorBean getVendedorBean() {
        return this.vendedorBean;
    }

    public void setVendedorBean(VendedorBean vendedorBean2) {
        this.vendedorBean = vendedorBean2;
    }

    public String getIdPreautorizacion() {
        return this.idPreautorizacion;
    }

    public void setIdPreautorizacion(String str) {
        this.idPreautorizacion = str;
    }

    public String getCodEstado() {
        return this.codEstado;
    }

    public void setCodEstado(String str) {
        this.codEstado = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(String str) {
        this.fechaCreacion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.compradorBean, i);
        parcel.writeParcelable(this.vendedorBean, i);
        parcel.writeString(this.idPreautorizacion);
        parcel.writeString(this.codEstado);
        parcel.writeString(this.moneda);
        parcel.writeString(this.fechaCreacion);
    }
}
