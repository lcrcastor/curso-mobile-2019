package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class WebViewHome implements Parcelable {
    public static final Creator<WebViewHome> CREATOR = new Creator<WebViewHome>() {
        public WebViewHome createFromParcel(Parcel parcel) {
            return new WebViewHome(parcel);
        }

        public WebViewHome[] newArray(int i) {
            return new WebViewHome[i];
        }
    };
    private String urlCajeros;
    private String urlPromociones;
    private String urlSucursales;

    public int describeContents() {
        return 0;
    }

    private WebViewHome(Parcel parcel) {
        this.urlPromociones = parcel.readString();
        this.urlCajeros = parcel.readString();
        this.urlSucursales = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.urlPromociones);
        parcel.writeString(this.urlCajeros);
        parcel.writeString(this.urlSucursales);
    }

    public String getUrlPromociones() {
        return this.urlPromociones;
    }

    public void setUrlPromociones(String str) {
        this.urlPromociones = str;
    }

    public String getUrlCajeros() {
        return this.urlCajeros;
    }

    public void setUrlCajeros(String str) {
        this.urlCajeros = str;
    }

    public String getUrlSucursales() {
        return this.urlSucursales;
    }

    public void setUrlSucursales(String str) {
        this.urlSucursales = str;
    }

    public static Creator<WebViewHome> getCREATOR() {
        return CREATOR;
    }
}
