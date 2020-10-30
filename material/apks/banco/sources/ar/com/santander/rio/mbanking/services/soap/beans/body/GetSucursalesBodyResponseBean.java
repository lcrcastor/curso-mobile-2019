package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetSucursalesBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetSucursalesBodyResponseBean> CREATOR = new Creator<GetSucursalesBodyResponseBean>() {
        public GetSucursalesBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetSucursalesBodyResponseBean(parcel);
        }

        public GetSucursalesBodyResponseBean[] newArray(int i) {
            return new GetSucursalesBodyResponseBean[i];
        }
    };
    @SerializedName("listaSucursales")
    public SucursalesBean listaSucursales;
    public String pagina;
    public String paginas;
    @SerializedName("sucursales")
    public SucursalesBean sucursalesBean;

    public int describeContents() {
        return 0;
    }

    public GetSucursalesBodyResponseBean() {
    }

    public GetSucursalesBodyResponseBean(String str, String str2, SucursalesBean sucursalesBean2, SucursalesBean sucursalesBean3) {
        this.pagina = str;
        this.paginas = str2;
        this.sucursalesBean = sucursalesBean2;
        this.listaSucursales = sucursalesBean3;
    }

    protected GetSucursalesBodyResponseBean(Parcel parcel) {
        this.pagina = parcel.readString();
        this.paginas = parcel.readString();
        this.sucursalesBean = (SucursalesBean) parcel.readParcelable(SucursalesBean.class.getClassLoader());
        this.listaSucursales = (SucursalesBean) parcel.readParcelable(SucursalesBean.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pagina);
        parcel.writeString(this.paginas);
        parcel.writeParcelable(this.sucursalesBean, i);
        parcel.writeParcelable(this.listaSucursales, i);
    }
}
