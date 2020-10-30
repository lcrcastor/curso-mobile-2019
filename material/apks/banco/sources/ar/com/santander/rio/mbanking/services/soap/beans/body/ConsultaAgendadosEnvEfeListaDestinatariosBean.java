package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ConsultaAgendadosEnvEfeListaDestinatariosBean implements Parcelable {
    public static final Creator<ConsultaAgendadosEnvEfeListaDestinatariosBean> CREATOR = new Creator<ConsultaAgendadosEnvEfeListaDestinatariosBean>() {
        public ConsultaAgendadosEnvEfeListaDestinatariosBean createFromParcel(Parcel parcel) {
            return new ConsultaAgendadosEnvEfeListaDestinatariosBean(parcel);
        }

        public ConsultaAgendadosEnvEfeListaDestinatariosBean[] newArray(int i) {
            return new ConsultaAgendadosEnvEfeListaDestinatariosBean[i];
        }
    };
    @SerializedName("destinatario")
    public List<AMAgendadosEnvEfeDestinatarioBean> destinatario;

    public int describeContents() {
        return 0;
    }

    public ConsultaAgendadosEnvEfeListaDestinatariosBean() {
        this.destinatario = new ArrayList();
    }

    public ConsultaAgendadosEnvEfeListaDestinatariosBean(Parcel parcel) {
        this.destinatario = new ArrayList();
        parcel.readTypedList(this.destinatario, AMAgendadosEnvEfeDestinatarioBean.CREATOR);
    }

    public ConsultaAgendadosEnvEfeListaDestinatariosBean(List<AMAgendadosEnvEfeDestinatarioBean> list) {
        this.destinatario = list;
    }

    public boolean equals(ConsultaAgendadosEnvEfeListaDestinatariosBean consultaAgendadosEnvEfeListaDestinatariosBean) {
        return this.destinatario.equals(consultaAgendadosEnvEfeListaDestinatariosBean.destinatario);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.destinatario);
    }
}
