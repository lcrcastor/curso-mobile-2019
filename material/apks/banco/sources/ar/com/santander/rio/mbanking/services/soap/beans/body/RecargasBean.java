package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RecargasBean implements Parcelable {
    public static final Creator<RecargasBean> CREATOR = new Creator<RecargasBean>() {
        public RecargasBean createFromParcel(Parcel parcel) {
            return new RecargasBean(parcel);
        }

        public RecargasBean[] newArray(int i) {
            return new RecargasBean[i];
        }
    };
    @SerializedName("listaCuenta")
    private List<CuentaRecargaBean> listaCuenta;
    @SerializedName("listaRecargas")
    private List<RecargaBean> listaRecargas;
    @SerializedName("listaValores")
    private List<ValorRecargaBean> listaValores;

    public int describeContents() {
        return 0;
    }

    public RecargasBean() {
    }

    protected RecargasBean(Parcel parcel) {
        this.listaRecargas = parcel.createTypedArrayList(RecargaBean.CREATOR);
        this.listaCuenta = parcel.createTypedArrayList(CuentaRecargaBean.CREATOR);
        this.listaValores = parcel.createTypedArrayList(ValorRecargaBean.CREATOR);
    }

    public List<RecargaBean> getListaRecargas() {
        return this.listaRecargas;
    }

    public void setListaRecargas(List<RecargaBean> list) {
        this.listaRecargas = list;
    }

    public List<CuentaRecargaBean> getListaCuenta() {
        return this.listaCuenta;
    }

    public void setListaCuenta(List<CuentaRecargaBean> list) {
        this.listaCuenta = list;
    }

    public List<ValorRecargaBean> getListaValores() {
        return this.listaValores;
    }

    public void setListaValores(List<ValorRecargaBean> list) {
        this.listaValores = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listaRecargas);
        parcel.writeTypedList(this.listaCuenta);
        parcel.writeTypedList(this.listaValores);
    }
}
