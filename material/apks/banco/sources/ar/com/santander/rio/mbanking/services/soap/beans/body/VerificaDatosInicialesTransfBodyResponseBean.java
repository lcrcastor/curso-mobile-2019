package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class VerificaDatosInicialesTransfBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<VerificaDatosInicialesTransfBodyResponseBean> CREATOR = new Creator<VerificaDatosInicialesTransfBodyResponseBean>() {
        public VerificaDatosInicialesTransfBodyResponseBean createFromParcel(Parcel parcel) {
            return new VerificaDatosInicialesTransfBodyResponseBean(parcel);
        }

        public VerificaDatosInicialesTransfBodyResponseBean[] newArray(int i) {
            return new VerificaDatosInicialesTransfBodyResponseBean[i];
        }
    };
    @SerializedName("ctaMigrada")
    private CtaMigradaBean ctaMigradaBean;
    @SerializedName("tipoDestino")
    private String tipoDestino;
    @SerializedName("datos")
    private VerificaDatosSalidaBean verificaDatosSalidaBean;

    public int describeContents() {
        return 0;
    }

    public VerificaDatosInicialesTransfBodyResponseBean(VerificaDatosSalidaBean verificaDatosSalidaBean2) {
        this.verificaDatosSalidaBean = verificaDatosSalidaBean2;
    }

    public VerificaDatosInicialesTransfBodyResponseBean(String str, VerificaDatosSalidaBean verificaDatosSalidaBean2) {
        this.tipoDestino = str;
        this.verificaDatosSalidaBean = verificaDatosSalidaBean2;
    }

    protected VerificaDatosInicialesTransfBodyResponseBean(Parcel parcel) {
        this.tipoDestino = parcel.readString();
        this.verificaDatosSalidaBean = (VerificaDatosSalidaBean) parcel.readParcelable(VerificaDatosSalidaBean.class.getClassLoader());
        this.ctaMigradaBean = (CtaMigradaBean) parcel.readParcelable(CtaMigradaBean.class.getClassLoader());
    }

    public VerificaDatosSalidaBean getVerificaDatosSalidaBean() {
        return this.verificaDatosSalidaBean;
    }

    public String getTipoDestino() {
        return this.tipoDestino;
    }

    public CtaMigradaBean getCtaMigradaBean() {
        return this.ctaMigradaBean;
    }

    public void setCtaMigradaBean(CtaMigradaBean ctaMigradaBean2) {
        this.ctaMigradaBean = ctaMigradaBean2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoDestino);
        parcel.writeParcelable(this.verificaDatosSalidaBean, i);
        parcel.writeParcelable(this.ctaMigradaBean, i);
    }
}
