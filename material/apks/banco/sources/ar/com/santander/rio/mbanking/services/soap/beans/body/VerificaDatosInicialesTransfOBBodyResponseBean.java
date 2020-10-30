package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class VerificaDatosInicialesTransfOBBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<VerificaDatosInicialesTransfOBBodyResponseBean> CREATOR = new Creator<VerificaDatosInicialesTransfOBBodyResponseBean>() {
        public VerificaDatosInicialesTransfOBBodyResponseBean createFromParcel(Parcel parcel) {
            return new VerificaDatosInicialesTransfOBBodyResponseBean(parcel);
        }

        public VerificaDatosInicialesTransfOBBodyResponseBean[] newArray(int i) {
            return new VerificaDatosInicialesTransfOBBodyResponseBean[i];
        }
    };
    @SerializedName("ctaMigrada")
    private CtaMigradaBean ctaMigradaBean;
    private String tipoDestino;
    @SerializedName("datos")
    private VerificaDatosSalidaOBBean verificaDatosSalidaOBBean;

    public int describeContents() {
        return 0;
    }

    public VerificaDatosInicialesTransfOBBodyResponseBean(VerificaDatosSalidaOBBean verificaDatosSalidaOBBean2) {
        this.verificaDatosSalidaOBBean = verificaDatosSalidaOBBean2;
    }

    protected VerificaDatosInicialesTransfOBBodyResponseBean(Parcel parcel) {
        this.tipoDestino = parcel.readString();
        this.verificaDatosSalidaOBBean = (VerificaDatosSalidaOBBean) parcel.readParcelable(VerificaDatosSalidaOBBean.class.getClassLoader());
        this.ctaMigradaBean = (CtaMigradaBean) parcel.readParcelable(CtaMigradaBean.class.getClassLoader());
    }

    public VerificaDatosSalidaOBBean getVerificaDatosSalidaOBBean() {
        return this.verificaDatosSalidaOBBean;
    }

    public void setVerificaDatosSalidaOBBean(VerificaDatosSalidaOBBean verificaDatosSalidaOBBean2) {
        this.verificaDatosSalidaOBBean = verificaDatosSalidaOBBean2;
    }

    public CtaMigradaBean getCtaMigradaBean() {
        return this.ctaMigradaBean;
    }

    public void setCtaMigradaBean(CtaMigradaBean ctaMigradaBean2) {
        this.ctaMigradaBean = ctaMigradaBean2;
    }

    public String getTipoDestino() {
        return this.tipoDestino;
    }

    public void setTipoDestino(String str) {
        this.tipoDestino = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoDestino);
        parcel.writeParcelable(this.verificaDatosSalidaOBBean, i);
        parcel.writeParcelable(this.ctaMigradaBean, i);
    }
}
