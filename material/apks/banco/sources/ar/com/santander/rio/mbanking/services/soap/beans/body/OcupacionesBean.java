package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OcupacionesBean implements Parcelable {
    public static final Creator<OcupacionesBean> CREATOR = new Creator<OcupacionesBean>() {
        public OcupacionesBean createFromParcel(Parcel parcel) {
            return new OcupacionesBean(parcel);
        }

        public OcupacionesBean[] newArray(int i) {
            return new OcupacionesBean[i];
        }
    };
    @SerializedName("ocupacion")
    private List<OcupacionBean> listOcupaciones;

    public int describeContents() {
        return 0;
    }

    public OcupacionesBean() {
    }

    public OcupacionesBean(List<OcupacionBean> list) {
        this.listOcupaciones = list;
    }

    protected OcupacionesBean(Parcel parcel) {
        this.listOcupaciones = parcel.createTypedArrayList(OcupacionBean.CREATOR);
    }

    public List<OcupacionBean> getListOcupaciones() {
        return this.listOcupaciones;
    }

    public void setListOcupaciones(List<OcupacionBean> list) {
        this.listOcupaciones = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listOcupaciones);
    }
}
