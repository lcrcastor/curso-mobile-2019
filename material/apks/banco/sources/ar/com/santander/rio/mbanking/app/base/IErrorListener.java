package ar.com.santander.rio.mbanking.app.base;

import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;

public interface IErrorListener {
    void onWebServiceErrorEvent(WebServiceEvent webServiceEvent, String str);
}
