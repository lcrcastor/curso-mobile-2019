package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreguntasFamiliaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class PreguntasFamiliaResponseBean extends BaseResponseBean implements Parcelable, IBeanErroWS, IBeanWS {
    public static final Creator<PreguntasFamiliaResponseBean> CREATOR = new Creator<PreguntasFamiliaResponseBean>() {
        public PreguntasFamiliaResponseBean createFromParcel(Parcel parcel) {
            return new PreguntasFamiliaResponseBean(parcel);
        }

        public PreguntasFamiliaResponseBean[] newArray(int i) {
            return new PreguntasFamiliaResponseBean[i];
        }
    };
    @SerializedName("body")
    public GetPreguntasFamiliaBodyResponseBean getPreguntasFamiliaBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public int describeContents() {
        return 0;
    }

    public void setJsonElement(JSONObject jSONObject) {
    }

    public PreguntasFamiliaResponseBean() {
        this.getPreguntasFamiliaBodyResponseBean = new GetPreguntasFamiliaBodyResponseBean();
    }

    public PreguntasFamiliaResponseBean(PrivateHeaderBean privateHeaderBean, GetPreguntasFamiliaBodyResponseBean getPreguntasFamiliaBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getPreguntasFamiliaBodyResponseBean = getPreguntasFamiliaBodyResponseBean2;
    }

    protected PreguntasFamiliaResponseBean(Parcel parcel) {
        this.getPreguntasFamiliaBodyResponseBean = (GetPreguntasFamiliaBodyResponseBean) parcel.readParcelable(GetPreguntasFamiliaBodyResponseBean.class.getClassLoader());
    }

    public Object getErrorBeanObject() {
        return this.getPreguntasFamiliaBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public GetPreguntasFamiliaBodyResponseBean getGetPreguntasFamiliaBodyResponseBean() {
        return this.getPreguntasFamiliaBodyResponseBean;
    }

    public void setGetPreguntasFamiliaBodyResponseBean(GetPreguntasFamiliaBodyResponseBean getPreguntasFamiliaBodyResponseBean2) {
        this.getPreguntasFamiliaBodyResponseBean = getPreguntasFamiliaBodyResponseBean2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.getPreguntasFamiliaBodyResponseBean, i);
    }
}
