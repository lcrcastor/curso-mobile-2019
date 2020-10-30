package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeader;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class VerificaDatosInicialesTransfOBResponseBean extends BaseResponseBean implements Parcelable, IBeanErroWS, IBeanWS {
    public static final Creator<VerificaDatosInicialesTransfOBResponseBean> CREATOR = new Creator<VerificaDatosInicialesTransfOBResponseBean>() {
        public VerificaDatosInicialesTransfOBResponseBean createFromParcel(Parcel parcel) {
            return new VerificaDatosInicialesTransfOBResponseBean(parcel);
        }

        public VerificaDatosInicialesTransfOBResponseBean[] newArray(int i) {
            return new VerificaDatosInicialesTransfOBResponseBean[i];
        }
    };
    @SerializedName("header")
    public PrivateHeader headerBean;
    @SerializedName("body")
    public VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean;

    public int describeContents() {
        return 0;
    }

    public VerificaDatosInicialesTransfOBResponseBean(VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean2) {
        this.verificaDatosInicialesTransfOBBodyResponseBean = verificaDatosInicialesTransfOBBodyResponseBean2;
    }

    public VerificaDatosInicialesTransfOBResponseBean() {
    }

    protected VerificaDatosInicialesTransfOBResponseBean(Parcel parcel) {
        this.headerBean = (PrivateHeader) parcel.readParcelable(PrivateHeader.class.getClassLoader());
        this.verificaDatosInicialesTransfOBBodyResponseBean = (VerificaDatosInicialesTransfOBBodyResponseBean) parcel.readParcelable(VerificaDatosInicialesTransfOBBodyResponseBean.class.getClassLoader());
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.verificaDatosInicialesTransfOBBodyResponseBean;
    }

    public VerificaDatosInicialesTransfOBBodyResponseBean getVerificaDatosInicialesTransfOBBodyResponseBean() {
        return this.verificaDatosInicialesTransfOBBodyResponseBean;
    }

    public void setVerificaDatosInicialesTransfOBBodyResponseBean(VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean2) {
        this.verificaDatosInicialesTransfOBBodyResponseBean = verificaDatosInicialesTransfOBBodyResponseBean2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.headerBean, i);
        parcel.writeParcelable(this.verificaDatosInicialesTransfOBBodyResponseBean, i);
    }
}
