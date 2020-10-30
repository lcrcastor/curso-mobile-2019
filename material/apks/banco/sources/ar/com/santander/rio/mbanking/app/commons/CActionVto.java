package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import java.util.ArrayList;
import java.util.List;

public class CActionVto {
    private SessionManager a;

    public CActionVto(SessionManager sessionManager) {
        this.a = sessionManager;
    }

    public ArrayList<String> getListStringExpiredActions() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (ListGroupBean listGroupBean : getListExpiredActions()) {
            for (String equals : Constants.VALUES_EXPIRED_ACTION_LONG_TERM_DEPOSIT) {
                if (equals.equals(listGroupBean.code)) {
                    arrayList.add(listGroupBean.getLabel());
                }
            }
        }
        return arrayList;
    }

    public List<ListGroupBean> getListExpiredActions() {
        ArrayList arrayList = new ArrayList();
        ListTableBean consDescripcionCOMBOACCIONVTO = CConsDescripciones.getConsDescripcionCOMBOACCIONVTO(this.a);
        if (consDescripcionCOMBOACCIONVTO != null) {
            for (ListGroupBean listGroupBean : consDescripcionCOMBOACCIONVTO.listGroupBeans) {
                for (String equals : Constants.VALUES_EXPIRED_ACTION_LONG_TERM_DEPOSIT) {
                    if (equals.equals(listGroupBean.code)) {
                        arrayList.add(listGroupBean);
                    }
                }
            }
        }
        return arrayList;
    }

    public String getDefaultExpiredActions() {
        ArrayList listStringExpiredActions = getListStringExpiredActions();
        return (listStringExpiredActions == null || listStringExpiredActions.size() <= 0) ? "" : (String) listStringExpiredActions.get(0);
    }

    public ListGroupBean findExpiredAction(String str) {
        List listExpiredActions = getListExpiredActions();
        if (listExpiredActions != null && listExpiredActions.size() > 0) {
            for (int i = 0; i < listExpiredActions.size(); i++) {
                if (str.equals(((ListGroupBean) listExpiredActions.get(i)).getLabel())) {
                    return (ListGroupBean) listExpiredActions.get(i);
                }
            }
        }
        return null;
    }

    public ListGroupBean findExpiredActionByCode(String str) {
        List listExpiredActions = getListExpiredActions();
        if (listExpiredActions != null && listExpiredActions.size() > 0) {
            for (int i = 0; i < listExpiredActions.size(); i++) {
                if (str.equals(((ListGroupBean) listExpiredActions.get(i)).code)) {
                    return (ListGroupBean) listExpiredActions.get(i);
                }
            }
        }
        return null;
    }
}
