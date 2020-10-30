package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaCotizacionesFondosBean implements Parcelable {
    public static final Creator<ListaCotizacionesFondosBean> CREATOR = new Creator<ListaCotizacionesFondosBean>() {
        public ListaCotizacionesFondosBean createFromParcel(Parcel parcel) {
            return new ListaCotizacionesFondosBean(parcel);
        }

        public ListaCotizacionesFondosBean[] newArray(int i) {
            return new ListaCotizacionesFondosBean[i];
        }
    };
    @SerializedName("cotizacion")
    private List<CotizacionFondosBean> cotizacionesFondosBean;

    public int describeContents() {
        return 0;
    }

    public ListaCotizacionesFondosBean(List<CotizacionFondosBean> list) {
        this.cotizacionesFondosBean = list;
    }

    public ListaCotizacionesFondosBean() {
    }

    protected ListaCotizacionesFondosBean(Parcel parcel) {
        this.cotizacionesFondosBean = new ArrayList();
        parcel.readList(this.cotizacionesFondosBean, CotizacionFondosBean.class.getClassLoader());
    }

    public List<CotizacionFondosBean> getCotizacionesFondosBean() {
        return this.cotizacionesFondosBean;
    }

    public void setCotizacionesFondosBean(List<CotizacionFondosBean> list) {
        this.cotizacionesFondosBean = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.cotizacionesFondosBean);
    }
}
