package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GetNumerosUtilesBodyResponseBean extends ErrorBodyBean {
    @SerializedName("datosUtiles")
    public List<NumerosUtilesBean> numerosUtilesList;
    public String result;
}
