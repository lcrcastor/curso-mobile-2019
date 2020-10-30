package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaCuentasVendedorBean implements Parcelable {
    public static final Creator<ListaCuentasVendedorBean> CREATOR = new Creator<ListaCuentasVendedorBean>() {
        public ListaCuentasVendedorBean createFromParcel(Parcel parcel) {
            return new ListaCuentasVendedorBean(parcel);
        }

        public ListaCuentasVendedorBean[] newArray(int i) {
            return new ListaCuentasVendedorBean[i];
        }
    };
    @SerializedName("cuentaVendedor")
    private List<CuentaVendedor> cuentaVendedor;

    public int describeContents() {
        return 0;
    }

    public ListaCuentasVendedorBean() {
    }

    public ListaCuentasVendedorBean(List<CuentaVendedor> list) {
        this.cuentaVendedor = list;
    }

    protected ListaCuentasVendedorBean(Parcel parcel) {
        this.cuentaVendedor = parcel.createTypedArrayList(CuentaVendedor.CREATOR);
    }

    public List<CuentaVendedor> getCuentaVendedor() {
        return this.cuentaVendedor;
    }

    public void setCuentaVendedor(List<CuentaVendedor> list) {
        this.cuentaVendedor = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.cuentaVendedor);
    }
}
