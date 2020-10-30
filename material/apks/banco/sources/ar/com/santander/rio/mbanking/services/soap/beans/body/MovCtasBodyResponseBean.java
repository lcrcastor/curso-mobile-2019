package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class MovCtasBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<MovCtasBodyResponseBean> CREATOR = new Creator<MovCtasBodyResponseBean>() {
        public MovCtasBodyResponseBean createFromParcel(Parcel parcel) {
            return new MovCtasBodyResponseBean(parcel);
        }

        public MovCtasBodyResponseBean[] newArray(int i) {
            return new MovCtasBodyResponseBean[i];
        }
    };
    @SerializedName("datosCuenta")
    public AccountResponseBean accountResponseBean;
    public String cantMovMostrar;
    public String cantPagTotal;
    public String idTrx;
    @SerializedName("limitesExtraccion")
    public ExtractionLimitBean limitesExtraccion;
    public String masMov;
    public String saldo;
    @SerializedName("datosMovimientos")
    public TransactionsResponseBean transactionsResponseBean;

    public int describeContents() {
        return 0;
    }

    public MovCtasBodyResponseBean() {
        this.accountResponseBean = new AccountResponseBean();
        this.transactionsResponseBean = new TransactionsResponseBean();
        this.limitesExtraccion = new ExtractionLimitBean();
    }

    public MovCtasBodyResponseBean(AccountResponseBean accountResponseBean2, String str, String str2, String str3, String str4, String str5, TransactionsResponseBean transactionsResponseBean2, ExtractionLimitBean extractionLimitBean) {
        this.accountResponseBean = accountResponseBean2;
        this.idTrx = str;
        this.masMov = str2;
        this.cantMovMostrar = str3;
        this.cantPagTotal = str4;
        this.saldo = str5;
        this.transactionsResponseBean = transactionsResponseBean2;
        this.limitesExtraccion = extractionLimitBean;
    }

    protected MovCtasBodyResponseBean(Parcel parcel) {
        this.accountResponseBean = (AccountResponseBean) parcel.readParcelable(AccountResponseBean.class.getClassLoader());
        this.idTrx = parcel.readString();
        this.masMov = parcel.readString();
        this.cantMovMostrar = parcel.readString();
        this.cantPagTotal = parcel.readString();
        this.saldo = parcel.readString();
        this.transactionsResponseBean = (TransactionsResponseBean) parcel.readParcelable(TransactionsResponseBean.class.getClassLoader());
        this.limitesExtraccion = (ExtractionLimitBean) parcel.readParcelable(ExtractionLimitBean.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.accountResponseBean, i);
        parcel.writeString(this.idTrx);
        parcel.writeString(this.masMov);
        parcel.writeString(this.cantMovMostrar);
        parcel.writeString(this.cantPagTotal);
        parcel.writeString(this.saldo);
        parcel.writeParcelable(this.transactionsResponseBean, i);
        parcel.writeParcelable(this.limitesExtraccion, i);
    }
}
