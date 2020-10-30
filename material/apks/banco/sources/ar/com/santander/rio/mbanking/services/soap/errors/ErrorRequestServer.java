package ar.com.santander.rio.mbanking.services.soap.errors;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import com.android.volley.VolleyError;

public interface ErrorRequestServer {
    void onErrorResp1(ErrorBodyBean errorBodyBean);

    void onErrorResp2(ErrorBodyBean errorBodyBean);

    void onErrorResp3(ErrorBodyBean errorBodyBean);

    void onErrorResp4(ErrorBodyBean errorBodyBean);

    void onErrorResp5(ErrorBodyBean errorBodyBean);

    void onErrorResp6(ErrorBodyBean errorBodyBean);

    void onErrorResp7(ErrorBodyBean errorBodyBean);

    void onErrorResp8(ErrorBodyBean errorBodyBean);

    void onErrorResp9(ErrorBodyBean errorBodyBean);

    void onErrorServer(VolleyError volleyError);

    void onWarningBean(ErrorBodyBean errorBodyBean);
}
