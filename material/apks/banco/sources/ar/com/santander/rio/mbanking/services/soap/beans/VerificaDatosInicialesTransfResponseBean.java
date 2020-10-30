package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeader;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class VerificaDatosInicialesTransfResponseBean extends BaseResponseBean implements Parcelable, IBeanErroWS, IBeanWS {
    public static final Creator<VerificaDatosInicialesTransfResponseBean> CREATOR = new Creator<VerificaDatosInicialesTransfResponseBean>() {
        public VerificaDatosInicialesTransfResponseBean createFromParcel(Parcel parcel) {
            return new VerificaDatosInicialesTransfResponseBean(parcel);
        }

        public VerificaDatosInicialesTransfResponseBean[] newArray(int i) {
            return new VerificaDatosInicialesTransfResponseBean[i];
        }
    };
    @SerializedName("header")
    public PrivateHeader headerBean;
    @SerializedName("body")
    public VerificaDatosInicialesTransfBodyResponseBean verificaDatosInicialesTransfBodyResponseBean;

    public int describeContents() {
        return 0;
    }

    public VerificaDatosInicialesTransfResponseBean(VerificaDatosInicialesTransfBodyResponseBean verificaDatosInicialesTransfBodyResponseBean2) {
        this.verificaDatosInicialesTransfBodyResponseBean = verificaDatosInicialesTransfBodyResponseBean2;
    }

    public VerificaDatosInicialesTransfResponseBean() {
    }

    protected VerificaDatosInicialesTransfResponseBean(Parcel parcel) {
        this.headerBean = (PrivateHeader) parcel.readParcelable(PrivateHeader.class.getClassLoader());
        this.verificaDatosInicialesTransfBodyResponseBean = (VerificaDatosInicialesTransfBodyResponseBean) parcel.readParcelable(VerificaDatosInicialesTransfBodyResponseBean.class.getClassLoader());
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.verificaDatosInicialesTransfBodyResponseBean;
    }

    public VerificaDatosInicialesTransfBodyResponseBean getVerificaDatosInicialesTransfBodyResponseBean() {
        return this.verificaDatosInicialesTransfBodyResponseBean;
    }

    public void setVerificaDatosInicialesTransfBodyResponseBean(VerificaDatosInicialesTransfBodyResponseBean verificaDatosInicialesTransfBodyResponseBean2) {
        this.verificaDatosInicialesTransfBodyResponseBean = verificaDatosInicialesTransfBodyResponseBean2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.headerBean, i);
        parcel.writeParcelable(this.verificaDatosInicialesTransfBodyResponseBean, i);
    }
}
