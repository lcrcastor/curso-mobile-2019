package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AgendadosBean implements Parcelable {
    public static final Creator<AgendadosBean> CREATOR = new Creator<AgendadosBean>() {
        public AgendadosBean createFromParcel(Parcel parcel) {
            return new AgendadosBean(parcel);
        }

        public AgendadosBean[] newArray(int i) {
            return new AgendadosBean[i];
        }
    };
    @SerializedName("listaAgDestBSR")
    private List<AgDestBSRBean> listAgDestBSRBean;
    @SerializedName("listaAgDestOB")
    private List<AgDestOBBean> listAgDestOBBean;
    @SerializedName("listadoCompleto")
    private String listadoCompleto;
    @SerializedName("mensajeAgendados")
    private String mensajeAgendados;

    public int describeContents() {
        return 0;
    }

    public AgendadosBean(String str, String str2, List<AgDestBSRBean> list, List<AgDestOBBean> list2) {
        this.listadoCompleto = str;
        this.listAgDestBSRBean = list;
        this.listAgDestOBBean = list2;
        if (!str2.equals("")) {
            this.mensajeAgendados = str2;
        }
    }

    public String getListadoCompleto() {
        return this.listadoCompleto;
    }

    public String getMensajeAgendados() {
        return this.mensajeAgendados;
    }

    public List<AgDestBSRBean> getListAgDestBSRBean() {
        return this.listAgDestBSRBean;
    }

    public List<AgDestOBBean> getListAgDestOBBean() {
        return this.listAgDestOBBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.listadoCompleto);
        parcel.writeList(this.listAgDestBSRBean);
        parcel.writeList(this.listAgDestOBBean);
    }

    private AgendadosBean(Parcel parcel) {
        this.listadoCompleto = parcel.readString();
        parcel.readTypedList(this.listAgDestBSRBean, null);
        parcel.readTypedList(this.listAgDestOBBean, null);
    }
}
