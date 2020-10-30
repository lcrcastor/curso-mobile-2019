package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SucursalesBean implements Parcelable {
    public static final Creator<SucursalesBean> CREATOR = new Creator<SucursalesBean>() {
        public SucursalesBean createFromParcel(Parcel parcel) {
            return new SucursalesBean(parcel);
        }

        public SucursalesBean[] newArray(int i) {
            return new SucursalesBean[i];
        }
    };
    @SerializedName("sucursal")
    public List<SucursalBean> sucursalBean;

    public int describeContents() {
        return 0;
    }

    public SucursalesBean() {
    }

    protected SucursalesBean(Parcel parcel) {
        this.sucursalBean = parcel.createTypedArrayList(SucursalBean.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.sucursalBean);
    }
}
