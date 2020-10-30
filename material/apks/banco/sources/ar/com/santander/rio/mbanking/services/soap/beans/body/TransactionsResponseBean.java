package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class TransactionsResponseBean implements Parcelable {
    public static final Creator<TransactionsResponseBean> CREATOR = new Creator<TransactionsResponseBean>() {
        public TransactionsResponseBean createFromParcel(Parcel parcel) {
            return new TransactionsResponseBean(parcel);
        }

        public TransactionsResponseBean[] newArray(int i) {
            return new TransactionsResponseBean[i];
        }
    };
    public String fechaSaldo;
    @SerializedName("datosMovimiento")
    public List<TransactionResponseBean> lstTranstactionsResponseBeans;
    public String saldoCAD;
    public String saldoCAP;
    public String saldoCCD;
    public String saldoCCP;
    public String saldoD;
    public String saldoP;

    public int describeContents() {
        return 0;
    }

    public TransactionsResponseBean() {
        this.lstTranstactionsResponseBeans = new ArrayList();
    }

    public TransactionsResponseBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, List<TransactionResponseBean> list) {
        this.fechaSaldo = str;
        this.saldoP = str2;
        this.saldoD = str3;
        this.saldoCCP = str4;
        this.saldoCAP = str5;
        this.saldoCCD = str6;
        this.saldoCAD = str7;
        this.lstTranstactionsResponseBeans = list;
    }

    protected TransactionsResponseBean(Parcel parcel) {
        this.fechaSaldo = parcel.readString();
        this.saldoP = parcel.readString();
        this.saldoD = parcel.readString();
        this.saldoCCP = parcel.readString();
        this.saldoCAP = parcel.readString();
        this.saldoCCD = parcel.readString();
        this.saldoCAD = parcel.readString();
        this.lstTranstactionsResponseBeans = new ArrayList();
        parcel.readList(this.lstTranstactionsResponseBeans, TransactionResponseBean.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fechaSaldo);
        parcel.writeString(this.saldoP);
        parcel.writeString(this.saldoD);
        parcel.writeString(this.saldoCCP);
        parcel.writeString(this.saldoCAP);
        parcel.writeString(this.saldoCCD);
        parcel.writeString(this.saldoCAD);
        parcel.writeList(this.lstTranstactionsResponseBeans);
    }
}
