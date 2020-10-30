package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AbmDebinCompradorBodyRequestBean implements Parcelable {
    public static final Creator<AbmDebinCompradorBodyRequestBean> CREATOR = new Creator<AbmDebinCompradorBodyRequestBean>() {
        public AbmDebinCompradorBodyRequestBean createFromParcel(Parcel parcel) {
            return new AbmDebinCompradorBodyRequestBean(parcel);
        }

        public AbmDebinCompradorBodyRequestBean[] newArray(int i) {
            return new AbmDebinCompradorBodyRequestBean[i];
        }
    };
    @SerializedName("detalleDebin")
    private DetalleDebinBean detalleDebinBean;
    @SerializedName("estadoMigrado")
    private String estadoMigrado;
    @SerializedName("tipoOperacion")
    private String tipoOperacion;

    public int describeContents() {
        return 0;
    }

    public AbmDebinCompradorBodyRequestBean() {
    }

    public AbmDebinCompradorBodyRequestBean(String str, String str2, DetalleDebinBean detalleDebinBean2) {
        this.tipoOperacion = str;
        this.estadoMigrado = str2;
        this.detalleDebinBean = detalleDebinBean2;
    }

    protected AbmDebinCompradorBodyRequestBean(Parcel parcel) {
        this.tipoOperacion = parcel.readString();
        this.estadoMigrado = parcel.readString();
        this.detalleDebinBean = (DetalleDebinBean) parcel.readParcelable(DetalleDebinBean.class.getClassLoader());
    }

    public String getTipoOperacion() {
        return this.tipoOperacion;
    }

    public void setTipoOperacion(String str) {
        this.tipoOperacion = str;
    }

    public DetalleDebinBean getDetalleDebinBean() {
        return this.detalleDebinBean;
    }

    public void setDetalleDebinBean(DetalleDebinBean detalleDebinBean2) {
        this.detalleDebinBean = detalleDebinBean2;
    }

    public String getEstadoMigrado() {
        return this.estadoMigrado;
    }

    public void setEstadoMigrado(String str) {
        this.estadoMigrado = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoOperacion);
        parcel.writeString(this.estadoMigrado);
        parcel.writeParcelable(this.detalleDebinBean, i);
    }
}
