package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PlanesSeguroBean implements Parcelable {
    public static final Creator<PlanesSeguroBean> CREATOR = new Creator<PlanesSeguroBean>() {
        public PlanesSeguroBean createFromParcel(Parcel parcel) {
            return new PlanesSeguroBean(parcel);
        }

        public PlanesSeguroBean[] newArray(int i) {
            return new PlanesSeguroBean[i];
        }
    };
    @SerializedName("plan")
    private List<PlanSeguroBean> listaPlanes;

    public int describeContents() {
        return 0;
    }

    public PlanesSeguroBean() {
    }

    public PlanesSeguroBean(List<PlanSeguroBean> list) {
        this.listaPlanes = list;
    }

    public List<PlanSeguroBean> getListaPlanes() {
        return this.listaPlanes;
    }

    public void setListaPlanes(List<PlanSeguroBean> list) {
        this.listaPlanes = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listaPlanes);
    }

    protected PlanesSeguroBean(Parcel parcel) {
        this.listaPlanes = parcel.createTypedArrayList(PlanSeguroBean.CREATOR);
    }
}
