package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AbmDebinVendedorBodyRequestBean implements Parcelable {
    public static final Creator<AbmDebinVendedorBodyRequestBean> CREATOR = new Creator<AbmDebinVendedorBodyRequestBean>() {
        public AbmDebinVendedorBodyRequestBean createFromParcel(Parcel parcel) {
            return new AbmDebinVendedorBodyRequestBean(parcel);
        }

        public AbmDebinVendedorBodyRequestBean[] newArray(int i) {
            return new AbmDebinVendedorBodyRequestBean[i];
        }
    };
    @SerializedName("detalleDebin")
    private DetalleDebinBean detalleDebinBean;
    @SerializedName("idDebin")
    private String idDebin;
    @SerializedName("tipoOperacion")
    private String tipoOperacion;

    public int describeContents() {
        return 0;
    }

    public AbmDebinVendedorBodyRequestBean(String str, DetalleDebinBean detalleDebinBean2, String str2) {
        this.tipoOperacion = str;
        this.detalleDebinBean = detalleDebinBean2;
        this.idDebin = str2;
    }

    protected AbmDebinVendedorBodyRequestBean(Parcel parcel) {
        this.tipoOperacion = parcel.readString();
        this.detalleDebinBean = (DetalleDebinBean) parcel.readParcelable(DetalleDebinBean.class.getClassLoader());
        this.idDebin = parcel.readString();
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

    public String getIdDebin() {
        return this.idDebin;
    }

    public void setIdDebin(String str) {
        this.idDebin = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoOperacion);
        parcel.writeParcelable(this.detalleDebinBean, i);
        parcel.writeString(this.idDebin);
    }
}
