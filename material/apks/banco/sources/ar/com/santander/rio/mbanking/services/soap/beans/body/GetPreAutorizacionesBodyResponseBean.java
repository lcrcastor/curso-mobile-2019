package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.PreautorizacionBean;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class GetPreAutorizacionesBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetPreAutorizacionesBodyResponseBean> CREATOR = new Creator<GetPreAutorizacionesBodyResponseBean>() {
        public GetPreAutorizacionesBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetPreAutorizacionesBodyResponseBean(parcel);
        }

        public GetPreAutorizacionesBodyResponseBean[] newArray(int i) {
            return new GetPreAutorizacionesBodyResponseBean[i];
        }
    };
    @SerializedName("preautorizaciones")
    private List<PreautorizacionBean> preautorizacionBean;
    @SerializedName("siguientePagina")
    private String siguientePagina;

    public int describeContents() {
        return 0;
    }

    public String getSiguientePagina() {
        return this.siguientePagina;
    }

    public void setSiguientePagina(String str) {
        this.siguientePagina = str;
    }

    public List<PreautorizacionBean> getListPreautorizaciones() {
        return this.preautorizacionBean;
    }

    public void setListPreautorizaciones(List<PreautorizacionBean> list) {
        this.preautorizacionBean = list;
    }

    protected GetPreAutorizacionesBodyResponseBean(Parcel parcel) {
        this.preautorizacionBean = new ArrayList();
        parcel.readTypedList(this.preautorizacionBean, PreautorizacionBean.CREATOR);
        this.siguientePagina = parcel.readString();
    }

    public GetPreAutorizacionesBodyResponseBean() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.preautorizacionBean);
        parcel.writeString(this.siguientePagina);
    }
}
