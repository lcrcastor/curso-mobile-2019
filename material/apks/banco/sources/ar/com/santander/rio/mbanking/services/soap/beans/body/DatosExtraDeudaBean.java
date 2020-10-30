package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class DatosExtraDeudaBean implements Parcelable {
    public static final Creator<DatosExtraDeudaBean> CREATOR = new Creator<DatosExtraDeudaBean>() {
        public DatosExtraDeudaBean createFromParcel(Parcel parcel) {
            return new DatosExtraDeudaBean(parcel);
        }

        public DatosExtraDeudaBean[] newArray(int i) {
            return new DatosExtraDeudaBean[i];
        }
    };
    @SerializedName("dato")
    public List<DatoDeudaBean> lstDatoBean;

    public int describeContents() {
        return 0;
    }

    public DatosExtraDeudaBean() {
        this.lstDatoBean = new ArrayList();
    }

    public DatosExtraDeudaBean(List<DatoDeudaBean> list) {
        this.lstDatoBean = list;
    }

    private DatosExtraDeudaBean(Parcel parcel) {
        this.lstDatoBean = new ArrayList();
        parcel.readTypedList(this.lstDatoBean, DatoDeudaBean.CREATOR);
    }

    public List<DatoDeudaBean> getLstDatoBean() {
        return this.lstDatoBean;
    }

    public void setLstDatoBean(List<DatoDeudaBean> list) {
        this.lstDatoBean = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.lstDatoBean);
    }
}
