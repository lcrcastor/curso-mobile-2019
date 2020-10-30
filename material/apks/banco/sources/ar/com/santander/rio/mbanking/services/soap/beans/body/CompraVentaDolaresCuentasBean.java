package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class CompraVentaDolaresCuentasBean implements Parcelable {
    public static final Creator<CompraVentaDolaresCuentasBean> CREATOR = new Creator<CompraVentaDolaresCuentasBean>() {
        public CompraVentaDolaresCuentasBean createFromParcel(Parcel parcel) {
            return new CompraVentaDolaresCuentasBean(parcel);
        }

        public CompraVentaDolaresCuentasBean[] newArray(int i) {
            return new CompraVentaDolaresCuentasBean[i];
        }
    };
    @SerializedName("cuenta")
    public List<CompraVentaDolaresCuentaBean> cuenta;

    public int describeContents() {
        return 0;
    }

    public CompraVentaDolaresCuentasBean() {
    }

    protected CompraVentaDolaresCuentasBean(Parcel parcel) {
        this.cuenta = new ArrayList();
        parcel.readTypedList(this.cuenta, CompraVentaDolaresCuentaBean.CREATOR);
    }

    public CompraVentaDolaresCuentasBean(List<CompraVentaDolaresCuentaBean> list) {
        this.cuenta = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.cuenta);
    }
}
