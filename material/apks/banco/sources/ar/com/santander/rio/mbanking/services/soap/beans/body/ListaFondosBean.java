package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaFondosBean implements Parcelable {
    public static final Creator<ListaFondosBean> CREATOR = new Creator<ListaFondosBean>() {
        public ListaFondosBean createFromParcel(Parcel parcel) {
            return new ListaFondosBean(parcel);
        }

        public ListaFondosBean[] newArray(int i) {
            return new ListaFondosBean[i];
        }
    };
    @SerializedName("fondo")
    private List<FondoBean> fondosBean;

    public int describeContents() {
        return 0;
    }

    public ListaFondosBean(List<FondoBean> list) {
        this.fondosBean = list;
    }

    public ListaFondosBean() {
        this.fondosBean = new ArrayList();
    }

    public List<FondoBean> getFondosBean() {
        return this.fondosBean;
    }

    public void setFondosBean(List<FondoBean> list) {
        this.fondosBean = list;
    }

    protected ListaFondosBean(Parcel parcel) {
        this.fondosBean = new ArrayList();
        parcel.readList(this.fondosBean, FondoBean.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.fondosBean);
    }
}
