package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class PaisesBean implements Parcelable {
    public static final Creator<PaisesBean> CREATOR = new Creator<PaisesBean>() {
        public PaisesBean createFromParcel(Parcel parcel) {
            return new PaisesBean(parcel);
        }

        public PaisesBean[] newArray(int i) {
            return new PaisesBean[i];
        }
    };
    @SerializedName("pais")
    private List<PaisBean> listaPaises;

    public int describeContents() {
        return 0;
    }

    public PaisesBean() {
        this.listaPaises = new ArrayList();
    }

    public PaisesBean(List<PaisBean> list) {
        this.listaPaises = list;
    }

    protected PaisesBean(Parcel parcel) {
        this.listaPaises = parcel.createTypedArrayList(PaisBean.CREATOR);
    }

    public List<PaisBean> getListaPaises() {
        return this.listaPaises;
    }

    public void setListaPaises(List<PaisBean> list) {
        this.listaPaises = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listaPaises);
    }
}
