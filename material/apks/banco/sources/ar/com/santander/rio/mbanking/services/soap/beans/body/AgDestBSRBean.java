package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AgDestBSRBean implements Parcelable {
    public static final Creator<AgDestBSRBean> CREATOR = new Creator<AgDestBSRBean>() {
        public AgDestBSRBean createFromParcel(Parcel parcel) {
            return new AgDestBSRBean(parcel);
        }

        public AgDestBSRBean[] newArray(int i) {
            return new AgDestBSRBean[i];
        }
    };
    @SerializedName("impMaxD")
    private String impMaxD;
    @SerializedName("impMaxP")
    private String impMaxP;
    @SerializedName("datosCuenta")
    private List<DatosCuentasDestBSRBean> listDatosCuentasDestBSRBean;

    public int describeContents() {
        return 0;
    }

    public AgDestBSRBean(String str, String str2, List<DatosCuentasDestBSRBean> list) {
        this.impMaxP = str;
        this.impMaxD = str2;
        this.listDatosCuentasDestBSRBean = list;
    }

    protected AgDestBSRBean(Parcel parcel) {
        this.impMaxP = parcel.readString();
        this.impMaxD = parcel.readString();
        this.listDatosCuentasDestBSRBean = parcel.createTypedArrayList(DatosCuentasDestBSRBean.CREATOR);
    }

    public String getImpMaxP() {
        return this.impMaxP;
    }

    public String getImpMaxD() {
        return this.impMaxD;
    }

    public List<DatosCuentasDestBSRBean> getListDatosCuentasDestBSRBean() {
        return this.listDatosCuentasDestBSRBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.impMaxP);
        parcel.writeString(this.impMaxD);
        parcel.writeTypedList(this.listDatosCuentasDestBSRBean);
    }
}
