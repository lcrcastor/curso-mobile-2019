package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaLegalesBean implements Parcelable {
    public static final Creator<ListaLegalesBean> CREATOR = new Creator<ListaLegalesBean>() {
        public ListaLegalesBean createFromParcel(Parcel parcel) {
            return new ListaLegalesBean(parcel);
        }

        public ListaLegalesBean[] newArray(int i) {
            return new ListaLegalesBean[i];
        }
    };
    @SerializedName("leyenda")
    private List<LegalBean> legalBeans;

    public int describeContents() {
        return 0;
    }

    public ListaLegalesBean(List<LegalBean> list) {
        this.legalBeans = list;
    }

    public ListaLegalesBean() {
    }

    protected ListaLegalesBean(Parcel parcel) {
        this.legalBeans = parcel.createTypedArrayList(LegalBean.CREATOR);
    }

    public List<LegalBean> getLegalBeans() {
        return this.legalBeans;
    }

    public void setLegalBeans(List<LegalBean> list) {
        this.legalBeans = list;
    }

    public LegalBean getLegalById(String str) {
        if (this.legalBeans != null) {
            for (LegalBean legalBean : this.legalBeans) {
                if (legalBean.getIdLegal().equalsIgnoreCase(str)) {
                    return legalBean;
                }
            }
        }
        return null;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.legalBeans);
    }
}
