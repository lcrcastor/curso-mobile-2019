package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.client.CustomRetry;
import com.indra.httpclient.json.JSONObject;
import com.indra.httpclient.senders.XmlBeanSender;

public abstract class BaseRequest implements IBeanRequestWS {
    protected Context mAppContext;
    protected ErrorRequestServer mErrorRequestServer;
    protected String mUrlWS;
    protected XmlBeanSender mXmlBeanSender;

    public abstract IBeanWS getBeanToRequest();

    public int getMethod() {
        return 0;
    }

    public abstract void onResponseBean(IBeanWS iBeanWS);

    public abstract void onUnknowError(Exception exc);

    public BaseRequest(Context context) {
        this.mAppContext = context;
        createXmlBeanSender(false);
        setDefaultUrlService();
    }

    public BaseRequest(Context context, boolean z) {
        this.mAppContext = context;
        createXmlBeanSender(z);
        setDefaultUrlService();
    }

    protected BaseRequest() {
    }

    public void setDefaultUrlService() {
        this.mUrlWS = ManagerTypeEnvironment.getCurrentEnvironment(this.mAppContext).getUrlWebService();
    }

    public void setTimeOutResponse(int i) {
        if (this.mXmlBeanSender != null) {
            this.mXmlBeanSender.setRetryPolicy(new CustomRetry(i));
        }
    }

    private void createXmlBeanSender(boolean z) {
        this.mXmlBeanSender = new XmlBeanSender(this.mAppContext, z) {
            public void onVolleyErrorServer(VolleyError volleyError) {
                BaseRequest.this.mErrorRequestServer.onErrorServer(volleyError);
            }

            public void onResponseServer(IBeanWS iBeanWS) {
                if (iBeanWS != null) {
                    BaseRequest.this.parserResponse(iBeanWS);
                    return;
                }
                Exception exc = new Exception("Error al parsear");
                exc.fillInStackTrace();
                BaseRequest.this.onUnknowError(exc);
            }

            public void onResponseServer(JSONObject jSONObject) {
                if (jSONObject != null) {
                    BaseRequest.this.parserResponse(jSONObject);
                    return;
                }
                Exception exc = new Exception("Error al parsear");
                exc.fillInStackTrace();
                BaseRequest.this.onUnknowError(exc);
            }
        };
    }

    public boolean parserResponse(JSONObject jSONObject) {
        Boolean valueOf;
        Boolean valueOf2 = Boolean.valueOf(false);
        try {
            Gson gson = new Gson();
            JSONObject jSONObject2 = new JSONObject();
            if (jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body") != null) {
                jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").getJSONObject("body");
            } else if (jSONObject.getJSONObject("xml").getJSONObject("body") != null) {
                jSONObject2 = jSONObject.getJSONObject("xml").getJSONObject("body");
            }
            ErrorBodyBean errorBodyBean = (ErrorBodyBean) gson.fromJson(jSONObject2.toString(), ErrorBodyBean.class);
            if (errorBodyBean.res != null) {
                Integer valueOf3 = Integer.valueOf(Integer.parseInt(errorBodyBean.res));
                if (valueOf3.intValue() == 0) {
                    valueOf = Boolean.valueOf(true);
                } else if (valueOf3.intValue() == -1) {
                    launchError(valueOf3.intValue(), errorBodyBean);
                    valueOf = Boolean.valueOf(true);
                } else {
                    launchError(valueOf3.intValue(), errorBodyBean);
                    return valueOf2.booleanValue();
                }
                valueOf2 = valueOf;
                return valueOf2.booleanValue();
            }
            throw new Exception();
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
            onUnknowError(e);
        }
    }

    public void parserResponse(IBeanWS iBeanWS) {
        try {
            ErrorBodyBean errorBodyBean = (ErrorBodyBean) ((IBeanErroWS) iBeanWS).getErrorBeanObject();
            if (errorBodyBean.res != null) {
                Integer valueOf = Integer.valueOf(Integer.parseInt(errorBodyBean.res));
                if (valueOf.intValue() == 0) {
                    onResponseBean(iBeanWS);
                } else if (valueOf.intValue() == -1) {
                    launchError(valueOf.intValue(), errorBodyBean);
                    onResponseBean(iBeanWS);
                } else {
                    launchError(valueOf.intValue(), errorBodyBean);
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            onUnknowError(e);
        }
    }

    private void launchError(int i, ErrorBodyBean errorBodyBean) {
        if (i < 0) {
            if (this.mErrorRequestServer != null) {
                this.mErrorRequestServer.onWarningBean(errorBodyBean);
            } else {
                onUnknowError(new Exception("ErrorRequestServer is null"));
            }
        } else if (i == 1) {
            if (this.mErrorRequestServer != null) {
                this.mErrorRequestServer.onErrorResp1(errorBodyBean);
            } else {
                onUnknowError(new Exception("ErrorRequestServer is null"));
            }
        } else if (i == 2) {
            if (this.mErrorRequestServer != null) {
                this.mErrorRequestServer.onErrorResp2(errorBodyBean);
            } else {
                onUnknowError(new Exception("ErrorRequestServer is null"));
            }
        } else if (i == 3) {
            if (this.mErrorRequestServer != null) {
                this.mErrorRequestServer.onErrorResp3(errorBodyBean);
            } else {
                onUnknowError(new Exception("ErrorRequestServer is null"));
            }
        } else if (i == 4) {
            if (this.mErrorRequestServer != null) {
                this.mErrorRequestServer.onErrorResp4(errorBodyBean);
            } else {
                onUnknowError(new Exception("ErrorRequestServer is null"));
            }
        } else if (i == 5) {
            if (this.mErrorRequestServer != null) {
                this.mErrorRequestServer.onErrorResp5(errorBodyBean);
            } else {
                onUnknowError(new Exception("ErrorRequestServer is null"));
            }
        } else if (i == 6) {
            if (this.mErrorRequestServer != null) {
                this.mErrorRequestServer.onErrorResp6(errorBodyBean);
            } else {
                onUnknowError(new Exception("ErrorRequestServer is null"));
            }
        } else if (i == 7) {
            if (this.mErrorRequestServer != null) {
                this.mErrorRequestServer.onErrorResp7(errorBodyBean);
            } else {
                onUnknowError(new Exception("ErrorRequestServer is null"));
            }
        } else if (i == 8) {
            if (this.mErrorRequestServer != null) {
                this.mErrorRequestServer.onErrorResp8(errorBodyBean);
            } else {
                onUnknowError(new Exception("ErrorRequestServer is null"));
            }
        } else if (i != 9) {
            onUnknowError(new Exception("ErrorRequestServer is null"));
        } else if (this.mErrorRequestServer != null) {
            this.mErrorRequestServer.onErrorResp9(errorBodyBean);
        } else {
            onUnknowError(new Exception("ErrorRequestServer is null"));
        }
    }

    public String getUrlWS() {
        return this.mUrlWS;
    }

    public void cancellAllRequest() {
        this.mXmlBeanSender.cancelRequest();
    }

    public void cancelRequest(String str) {
        this.mXmlBeanSender.cancelRequest(str);
    }

    public void setTagRequest(String str) {
        this.mXmlBeanSender.setTagRequest(str);
    }
}
