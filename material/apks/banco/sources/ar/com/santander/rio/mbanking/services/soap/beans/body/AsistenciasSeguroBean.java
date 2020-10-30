package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AsistenciasSeguroBean implements Parcelable {
    public static final Creator<AsistenciasSeguroBean> CREATOR = new Creator<AsistenciasSeguroBean>() {
        public AsistenciasSeguroBean createFromParcel(Parcel parcel) {
            return new AsistenciasSeguroBean(parcel);
        }

        public AsistenciasSeguroBean[] newArray(int i) {
            return new AsistenciasSeguroBean[i];
        }
    };
    @SerializedName("asistencia")
    private List<AsistenciaSeguroBean> listaAsistenciasBean;

    public int describeContents() {
        return 0;
    }

    public AsistenciasSeguroBean() {
    }

    public AsistenciasSeguroBean(List<AsistenciaSeguroBean> list) {
        this.listaAsistenciasBean = list;
    }

    public List<AsistenciaSeguroBean> getListaAsistenciasBean() {
        return this.listaAsistenciasBean;
    }

    public void setListaAsistenciasBean(List<AsistenciaSeguroBean> list) {
        this.listaAsistenciasBean = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.listaAsistenciasBean);
    }

    protected AsistenciasSeguroBean(Parcel parcel) {
        this.listaAsistenciasBean = parcel.createTypedArrayList(AsistenciaSeguroBean.CREATOR);
    }
}
