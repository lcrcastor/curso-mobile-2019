package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarPagoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ConfirmarPagoResponseBean implements Parcelable, IBeanErroWS, IBeanWS {
    public static final Creator<ConfirmarPagoResponseBean> CREATOR = new Creator<ConfirmarPagoResponseBean>() {
        public ConfirmarPagoResponseBean createFromParcel(Parcel parcel) {
            return new ConfirmarPagoResponseBean(parcel);
        }

        public ConfirmarPagoResponseBean[] newArray(int i) {
            return new ConfirmarPagoResponseBean[i];
        }
    };
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public ConfirmarPagoBodyResponseBean mGeConfirmarPagoResponseBean;

    public int describeContents() {
        return 0;
    }

    public void setJsonElement(JSONObject jSONObject) {
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    public ConfirmarPagoResponseBean() {
    }

    protected ConfirmarPagoResponseBean(Parcel parcel) {
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public ConfirmarPagoBodyResponseBean getmGeConfirmarPagoResponseBean() {
        return this.mGeConfirmarPagoResponseBean;
    }

    public void setmGeConfirmarPagoResponseBean(ConfirmarPagoBodyResponseBean confirmarPagoBodyResponseBean) {
        this.mGeConfirmarPagoResponseBean = confirmarPagoBodyResponseBean;
    }

    public ConfirmarPagoResponseBean(PrivateHeaderBean privateHeaderBean, ConfirmarPagoBodyResponseBean confirmarPagoBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.mGeConfirmarPagoResponseBean = confirmarPagoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.mGeConfirmarPagoResponseBean;
    }
}
