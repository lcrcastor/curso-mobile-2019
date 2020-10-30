package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LeyendasBean implements Parcelable {
    public static final Creator<LeyendasBean> CREATOR = new Creator<LeyendasBean>() {
        public LeyendasBean createFromParcel(Parcel parcel) {
            return new LeyendasBean(parcel);
        }

        public LeyendasBean[] newArray(int i) {
            return new LeyendasBean[i];
        }
    };
    @SerializedName("leyenda")
    private List<LeyendaBean> listLeyendaBean;

    public int describeContents() {
        return 0;
    }

    public LeyendasBean() {
    }

    public LeyendasBean(List<LeyendaBean> list) {
        this.listLeyendaBean = list;
    }

    public List<LeyendaBean> getLeyendasBean() {
        return this.listLeyendaBean;
    }

    public void setListLeyendaBean(List<LeyendaBean> list) {
        this.listLeyendaBean = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listLeyendaBean);
    }

    protected LeyendasBean(Parcel parcel) {
        this.listLeyendaBean = parcel.createTypedArrayList(LeyendaBean.CREATOR);
    }
}
