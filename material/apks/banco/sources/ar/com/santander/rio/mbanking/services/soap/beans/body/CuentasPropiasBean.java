package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CuentasPropiasBean implements Parcelable {
    public static final Creator<CuentasPropiasBean> CREATOR = new Creator<CuentasPropiasBean>() {
        public CuentasPropiasBean createFromParcel(Parcel parcel) {
            return new CuentasPropiasBean(parcel);
        }

        public CuentasPropiasBean[] newArray(int i) {
            return new CuentasPropiasBean[i];
        }
    };
    @SerializedName("impMaxD")
    private String impMaxD;
    @SerializedName("impMaxP")
    private String impMaxP;
    @SerializedName("datosCuenta")
    private List<DatosCuentasBean> listDatosCuentasBean;

    public int describeContents() {
        return 0;
    }

    public CuentasPropiasBean() {
    }

    public CuentasPropiasBean(String str, String str2, List<DatosCuentasBean> list) {
        this.impMaxP = str;
        this.impMaxD = str2;
        this.listDatosCuentasBean = list;
    }

    protected CuentasPropiasBean(Parcel parcel) {
        this.impMaxP = parcel.readString();
        this.impMaxD = parcel.readString();
        this.listDatosCuentasBean = parcel.createTypedArrayList(DatosCuentasBean.CREATOR);
    }

    public String getImpMaxP() {
        return this.impMaxP;
    }

    public void setImpMaxP(String str) {
        this.impMaxP = str;
    }

    public String getImpMaxD() {
        return this.impMaxD;
    }

    public void setImpMaxD(String str) {
        this.impMaxD = str;
    }

    public List<DatosCuentasBean> getListDatosCuentasBean() {
        return this.listDatosCuentasBean;
    }

    public void setListDatosCuentasBean(List<DatosCuentasBean> list) {
        this.listDatosCuentasBean = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.impMaxP);
        parcel.writeString(this.impMaxD);
        parcel.writeTypedList(this.listDatosCuentasBean);
    }
}
