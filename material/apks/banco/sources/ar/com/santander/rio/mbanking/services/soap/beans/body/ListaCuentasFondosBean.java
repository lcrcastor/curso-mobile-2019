package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaCuentasFondosBean implements Parcelable {
    public static final Creator<ListaCuentasFondosBean> CREATOR = new Creator<ListaCuentasFondosBean>() {
        public ListaCuentasFondosBean createFromParcel(Parcel parcel) {
            return new ListaCuentasFondosBean(parcel);
        }

        public ListaCuentasFondosBean[] newArray(int i) {
            return new ListaCuentasFondosBean[i];
        }
    };
    @SerializedName("cuenta")
    public List<CuentaFondosBean> cuentasFondosBean;

    public int describeContents() {
        return 0;
    }

    public ListaCuentasFondosBean(List<CuentaFondosBean> list) {
        this.cuentasFondosBean = list;
    }

    public ListaCuentasFondosBean() {
        this.cuentasFondosBean = new ArrayList();
    }

    protected ListaCuentasFondosBean(Parcel parcel) {
        this.cuentasFondosBean = parcel.createTypedArrayList(CuentaFondosBean.CREATOR);
    }

    public List<CuentaFondosBean> getCuentasFondosBean() {
        return this.cuentasFondosBean;
    }

    public void setCuentasFondosBean(List<CuentaFondosBean> list) {
        this.cuentasFondosBean = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.cuentasFondosBean);
    }
}
