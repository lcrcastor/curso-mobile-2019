package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaCtaOperativaBean implements Parcelable {
    public static final Creator<ListaCtaOperativaBean> CREATOR = new Creator<ListaCtaOperativaBean>() {
        public ListaCtaOperativaBean createFromParcel(Parcel parcel) {
            return new ListaCtaOperativaBean(parcel);
        }

        public ListaCtaOperativaBean[] newArray(int i) {
            return new ListaCtaOperativaBean[i];
        }
    };
    @SerializedName("cuentaOperativa")
    public List<CuentaOperativaBean> cuentasOperativasBean;

    public int describeContents() {
        return 0;
    }

    public ListaCtaOperativaBean() {
    }

    public ListaCtaOperativaBean(List<CuentaOperativaBean> list) {
        this.cuentasOperativasBean = list;
    }

    protected ListaCtaOperativaBean(Parcel parcel) {
        this.cuentasOperativasBean = parcel.createTypedArrayList(CuentaOperativaBean.CREATOR);
    }

    public List<CuentaOperativaBean> getCuentasOperativasBean() {
        return this.cuentasOperativasBean;
    }

    public void setCuentasOperativasBean(List<CuentaOperativaBean> list) {
        this.cuentasOperativasBean = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.cuentasOperativasBean);
    }
}
