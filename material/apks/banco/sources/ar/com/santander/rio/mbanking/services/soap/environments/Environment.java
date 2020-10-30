package ar.com.santander.rio.mbanking.services.soap.environments;

public class Environment implements IEnvironment {
    private static ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.Environment TYPE = null;
    private static String URL = "";

    public Environment(ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.Environment environment, String str) {
        URL = str;
        TYPE = environment;
    }

    public String getUrlWebService() {
        return URL;
    }

    public ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.Environment getEnvironmentType() {
        return TYPE;
    }
}
