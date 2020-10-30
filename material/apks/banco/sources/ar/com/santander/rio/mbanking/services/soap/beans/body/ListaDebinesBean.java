package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaDebinesBean implements Parcelable {
    public static final Creator<ListaDebinesBean> CREATOR = new Creator<ListaDebinesBean>() {
        public ListaDebinesBean createFromParcel(Parcel parcel) {
            return new ListaDebinesBean(parcel);
        }

        public ListaDebinesBean[] newArray(int i) {
            return new ListaDebinesBean[i];
        }
    };
    @SerializedName("debin")
    private List<ListDebinesBean> listDebinesBean;

    public int describeContents() {
        return 0;
    }

    public ListaDebinesBean() {
    }

    public ListaDebinesBean(List<ListDebinesBean> list) {
        this.listDebinesBean = list;
    }

    protected ListaDebinesBean(Parcel parcel) {
    }

    public List<ListDebinesBean> getListDebinesBean() {
        return this.listDebinesBean;
    }

    public void setListDebinesBean(List<ListDebinesBean> list) {
        this.listDebinesBean = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.listDebinesBean);
    }
}
