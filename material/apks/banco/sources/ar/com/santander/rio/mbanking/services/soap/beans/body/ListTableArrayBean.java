package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.app.module.accounts.ConstantsTransactions;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListTableArrayBean {
    @SerializedName("idTabla")
    public List<String> idTable = new ArrayList();

    public ListTableArrayBean() {
    }

    public ListTableArrayBean(boolean z) {
        this.idTable.add(PRE_AUTORIZACIONES.TPOCTACORTA);
        this.idTable.add("TPOCTALARGA");
        this.idTable.add(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO);
        this.idTable.add("MONEDADESCPALABRA");
        this.idTable.add(ConstantsTransactions.INDEX_TYPE_ACCOUNT_LARGE);
        this.idTable.add("COMBOACCIONVTO");
        this.idTable.add("COMBOTARMONEDA");
        this.idTable.add("COMBOFORMAPAGO");
        this.idTable.add("COMBOFECHAPAGO");
        this.idTable.add("TRANSF_CMB_AGDE");
        this.idTable.add("TRANSF_TR_CMB_CONC");
        this.idTable.add("TRANSF_CMB_TIPT");
        this.idTable.add("RECARG_MONTO");
        this.idTable.add("TPOEMPRCEL");
        this.idTable.add("EXTENV_ESTADO");
        this.idTable.add("ENV_TIPODOC");
        this.idTable.add(PRE_AUTORIZACIONES.CONDEBIN);
        this.idTable.add("ESTDEBIN");
        this.idTable.add(PRE_AUTORIZACIONES.ESTREPEAUT);
        this.idTable.add(PRE_AUTORIZACIONES.PERIODOPREAUT);
        this.idTable.add("TRANSF_TR_CMB_CONCEP");
        this.idTable.add("SUBE");
    }
}
