package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AbmDebinCompradorBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<AbmDebinCompradorBodyResponseBean> CREATOR = new Creator<AbmDebinCompradorBodyResponseBean>() {
        public AbmDebinCompradorBodyResponseBean createFromParcel(Parcel parcel) {
            return new AbmDebinCompradorBodyResponseBean(parcel);
        }

        public AbmDebinCompradorBodyResponseBean[] newArray(int i) {
            return new AbmDebinCompradorBodyResponseBean[i];
        }
    };
    @SerializedName("ctaMigrada")
    private CtaMigradaBean ctaMigradaBean;
    @SerializedName("fechaOperacion")
    private String fechaOperacion;
    @SerializedName("leyendaComp")
    private String leyendaComp;
    @SerializedName("nroComprobante")
    private String nroComprobante;

    public int describeContents() {
        return 0;
    }

    public AbmDebinCompradorBodyResponseBean() {
    }

    protected AbmDebinCompradorBodyResponseBean(Parcel parcel) {
        this.nroComprobante = parcel.readString();
        this.fechaOperacion = parcel.readString();
        this.leyendaComp = parcel.readString();
        this.ctaMigradaBean = (CtaMigradaBean) parcel.readParcelable(CtaMigradaBean.class.getClassLoader());
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public void setCtaMigradaBean(CtaMigradaBean ctaMigradaBean2) {
        this.ctaMigradaBean = ctaMigradaBean2;
    }

    public CtaMigradaBean getCtaMigradaBean() {
        return this.ctaMigradaBean;
    }

    public void setNroComprobante(String str) {
        this.nroComprobante = str;
    }

    public String getFechaOperacion() {
        return this.fechaOperacion;
    }

    public void setFechaOperacion(String str) {
        this.fechaOperacion = str;
    }

    public String getLeyendaComp() {
        return this.leyendaComp;
    }

    public void setLeyendaComp(String str) {
        this.leyendaComp = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nroComprobante);
        parcel.writeString(this.fechaOperacion);
        parcel.writeString(this.leyendaComp);
        parcel.writeParcelable(this.ctaMigradaBean, i);
    }
}
