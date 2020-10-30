package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ClasificatorsMixBean {
    @SerializedName("ClasificadorMix")
    public List<ClasificatorMixBean> clasificatorMixBean;
}
