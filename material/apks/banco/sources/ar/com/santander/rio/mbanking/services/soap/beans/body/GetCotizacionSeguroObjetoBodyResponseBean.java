package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetCotizacionSeguroObjetoBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetCotizacionSeguroObjetoBodyResponseBean> CREATOR = new Creator<GetCotizacionSeguroObjetoBodyResponseBean>() {
        public GetCotizacionSeguroObjetoBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetCotizacionSeguroObjetoBodyResponseBean(parcel);
        }

        public GetCotizacionSeguroObjetoBodyResponseBean[] newArray(int i) {
            return new GetCotizacionSeguroObjetoBodyResponseBean[i];
        }
    };
    public CotizacionSeguroObjetoBean cotizacion;

    public int describeContents() {
        return 0;
    }

    protected GetCotizacionSeguroObjetoBodyResponseBean(Parcel parcel) {
        this.cotizacion = (CotizacionSeguroObjetoBean) parcel.readParcelable(CotizacionSeguroObjetoBean.class.getClassLoader());
    }

    public GetCotizacionSeguroObjetoBodyResponseBean() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.cotizacion, i);
    }
}
