package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DatosSeguroBean implements Parcelable {
    public static final Creator<DatosSeguroBean> CREATOR = new Creator<DatosSeguroBean>() {
        public DatosSeguroBean createFromParcel(Parcel parcel) {
            return new DatosSeguroBean(parcel);
        }

        public DatosSeguroBean[] newArray(int i) {
            return new DatosSeguroBean[i];
        }
    };
    @SerializedName("dato")
    private List<DatoSeguroBean> listaDatosBean;

    public int describeContents() {
        return 0;
    }

    public DatosSeguroBean() {
    }

    public DatosSeguroBean(List<DatoSeguroBean> list) {
        this.listaDatosBean = list;
    }

    public List<DatoSeguroBean> getListaDatosBean() {
        return this.listaDatosBean;
    }

    public void setListaDatosBean(List<DatoSeguroBean> list) {
        this.listaDatosBean = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listaDatosBean);
    }

    protected DatosSeguroBean(Parcel parcel) {
        this.listaDatosBean = parcel.createTypedArrayList(DatoSeguroBean.CREATOR);
    }
}
