package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AgDestOBBean implements Parcelable {
    public static final Creator<AgDestOBBean> CREATOR = new Creator<AgDestOBBean>() {
        public AgDestOBBean createFromParcel(Parcel parcel) {
            return new AgDestOBBean(parcel);
        }

        public AgDestOBBean[] newArray(int i) {
            return new AgDestOBBean[i];
        }
    };
    @SerializedName("impMaxD")
    private String impMaxD;
    @SerializedName("impMaxP")
    private String impMaxP;
    @SerializedName("datosCuenta")
    private List<DatosCuentasDestOBBean> listDatosCuentasDestOBBean;

    public int describeContents() {
        return 0;
    }

    public AgDestOBBean(String str, String str2, List<DatosCuentasDestOBBean> list) {
        this.impMaxP = str;
        this.impMaxD = str2;
        this.listDatosCuentasDestOBBean = list;
    }

    protected AgDestOBBean(Parcel parcel) {
        this.impMaxP = parcel.readString();
        this.impMaxD = parcel.readString();
        this.listDatosCuentasDestOBBean = parcel.createTypedArrayList(DatosCuentasDestOBBean.CREATOR);
    }

    public String getImpMaxP() {
        return this.impMaxP;
    }

    public String getImpMaxD() {
        return this.impMaxD;
    }

    public List<DatosCuentasDestOBBean> getListDatosCuentasDestOBBean() {
        return this.listDatosCuentasDestOBBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.impMaxP);
        parcel.writeString(this.impMaxD);
        parcel.writeTypedList(this.listDatosCuentasDestOBBean);
    }
}
