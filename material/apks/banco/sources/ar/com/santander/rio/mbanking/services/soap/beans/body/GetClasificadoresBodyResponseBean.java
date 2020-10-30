package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GetClasificadoresBodyResponseBean extends ErrorBodyBean {
    @SerializedName("clasificadorValores")
    public ClasificatorValuesBean clasificadorValores = new ClasificatorValuesBean();
    @SerializedName("clasificadores")
    public ClasificatorsBean clasificadores = new ClasificatorsBean();
    @SerializedName("clasificadoresMix")
    public ClasificatorsMixBean clasificadoresMix = new ClasificatorsMixBean();
    @SerializedName("configuraciones")
    public ConfigsBean configuraciones = new ConfigsBean();
    public String hashCode;
    public String result;

    public GetClasificadoresBodyResponseBean() {
    }

    public GetClasificadoresBodyResponseBean(String str, String str2, List<ClasificatorsBean> list, List<ClasificatorValuesBean> list2, List<ClasificatorsMixBean> list3, List<ConfigsBean> list4) {
        this.result = str;
        this.hashCode = str2;
    }

    public boolean hasNullValue() {
        return this.clasificadores == null || this.clasificadorValores == null || this.clasificadoresMix == null || this.configuraciones == null;
    }
}
