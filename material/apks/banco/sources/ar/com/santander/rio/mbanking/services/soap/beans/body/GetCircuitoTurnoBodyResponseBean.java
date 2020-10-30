package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetCircuitoTurnoBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetCircuitoTurnoBodyResponseBean> CREATOR = new Creator<GetCircuitoTurnoBodyResponseBean>() {
        public GetCircuitoTurnoBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetCircuitoTurnoBodyResponseBean(parcel);
        }

        public GetCircuitoTurnoBodyResponseBean[] newArray(int i) {
            return new GetCircuitoTurnoBodyResponseBean[i];
        }
    };
    @SerializedName("linkRetiroPieza")
    private LinkRetiroPiezaBean linkRetiroPieza;
    @SerializedName("listaPantallas")
    private ListaPantallasBean listaPantallas;

    public int describeContents() {
        return 0;
    }

    public GetCircuitoTurnoBodyResponseBean() {
    }

    public GetCircuitoTurnoBodyResponseBean(LinkRetiroPiezaBean linkRetiroPiezaBean, ListaPantallasBean listaPantallasBean) {
        this.linkRetiroPieza = linkRetiroPiezaBean;
        this.listaPantallas = listaPantallasBean;
    }

    protected GetCircuitoTurnoBodyResponseBean(Parcel parcel) {
        this.linkRetiroPieza = (LinkRetiroPiezaBean) parcel.readParcelable(LinkRetiroPiezaBean.class.getClassLoader());
        this.listaPantallas = (ListaPantallasBean) parcel.readParcelable(ListaPantallasBean.class.getClassLoader());
    }

    public LinkRetiroPiezaBean getLinkRetiroPieza() {
        return this.linkRetiroPieza;
    }

    public void setLinkRetiroPieza(LinkRetiroPiezaBean linkRetiroPiezaBean) {
        this.linkRetiroPieza = linkRetiroPiezaBean;
    }

    public ListaPantallasBean getListaPantallas() {
        return this.listaPantallas;
    }

    public void setListaPantallas(ListaPantallasBean listaPantallasBean) {
        this.listaPantallas = listaPantallasBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.linkRetiroPieza, i);
        parcel.writeParcelable(this.listaPantallas, i);
    }
}
