package ar.com.santander.rio.mbanking.app.commons;

import android.util.Log;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import java.util.List;

public class CConsDescripciones {

    public enum TypeConsDescripciones {
        TPOCTACORTA,
        TPOCTALARGA,
        MONEDADESCSIMBOLO,
        MONEDADESCPALABRA,
        COMBOGRUPMOVPER,
        COMBOACCIONVTO,
        COMBOTARMONEDA,
        COMBOFORMAPAGO,
        COMBOFECHAPAGO,
        TRANSF_CMB_AGDE,
        TRANSF_TR_CMB_CONC,
        TRANSF_CMB_TIPT,
        RECARG_MONTO,
        TPOEMPRCEL,
        EXTENV_ESTADO,
        ENV_TIPODOC,
        ESTDEBIN,
        CONDEBIN,
        TRANSF_TR_CMB_CONCEP,
        SUBE
    }

    public static ListTableBean getConsDescripcionTPOCTACORTA(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.TPOCTACORTA.name());
    }

    public static ListTableBean getConsDescripcionTPOEMPRCEL(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.TPOEMPRCEL.name());
    }

    public static ListTableBean getConsDescripcionTPOCTALARGA(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.TPOCTALARGA.name());
    }

    public static ListTableBean getConsDescripcionMONEDADESCSIMBOLO(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.MONEDADESCSIMBOLO.name());
    }

    public static ListTableBean getConsDescripcionMONEDADESCPALABRA(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.MONEDADESCPALABRA.name());
    }

    public static ListTableBean getConsDescripcionCOMBOGRUPMOVPER(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.COMBOGRUPMOVPER.name());
    }

    public static ListTableBean getConsDescripcionCOMBOACCIONVTO(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.COMBOACCIONVTO.name());
    }

    public static ListTableBean getConsDescripcionCOMBOTARMONEDA(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.COMBOTARMONEDA.name());
    }

    public static ListTableBean getConsDescripcionCOMBOFORMAPAGO(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.COMBOFORMAPAGO.name());
    }

    public static ListTableBean getConsDescripcionCOMBOFECHAPAGO(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.COMBOFECHAPAGO.name());
    }

    public static ListTableBean getConsDescripcionTRANSF_CMB_AGDE(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.TRANSF_CMB_AGDE.name());
    }

    public static ListTableBean getConsDescripcionTRANSF_TR_CMB_CONC(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.TRANSF_TR_CMB_CONC.name());
    }

    public static ListTableBean getConsDescripcionTRANSF_TR_CMB_CONCEP(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.TRANSF_TR_CMB_CONCEP.name());
    }

    public static ListTableBean getConsDescripcionTRANSF_CMB_TIPT(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.TRANSF_CMB_TIPT.name());
    }

    public static ListTableBean getConsDescripcionRECARG_MONTO(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.RECARG_MONTO.name());
    }

    public static ListTableBean getConsDescripcionRECARG_SUBE(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.SUBE.name());
    }

    public static ListTableBean getConsDescripcionEXTENV_ESTADO(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.EXTENV_ESTADO.name());
    }

    public static ListTableBean getConsDescripcionENV_TIPODOC(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.ENV_TIPODOC.name());
    }

    public static ListTableBean getConsDescripcionESTDEBIN(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.ESTDEBIN.name());
    }

    public static ListTableBean getConsDescripcionCONDEBIN(SessionManager sessionManager) {
        return getConsDescripcionByIndex(sessionManager, TypeConsDescripciones.CONDEBIN.name());
    }

    public static ListTableBean getConsDescripcionByIndex(SessionManager sessionManager, String str) {
        try {
            List<ListTableBean> list = sessionManager.getConsDescripciones().listTableBeans;
            if (list == null || list.size() <= 0) {
                return null;
            }
            for (ListTableBean listTableBean : list) {
                if (str.equals(listTableBean.idTable)) {
                    return listTableBean;
                }
            }
            return null;
        } catch (Exception e) {
            e.fillInStackTrace();
            Log.e("@dev", "Error al recuperar una table de ConsDescripciones", e);
            return null;
        }
    }
}
