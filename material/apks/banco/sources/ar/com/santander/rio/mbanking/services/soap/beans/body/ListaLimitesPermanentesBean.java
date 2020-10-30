package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaLimitesPermanentesBean {
    @SerializedName("limite")
    public List<LimiteExtraccionBean> listaLimitesPermanentes;
}
