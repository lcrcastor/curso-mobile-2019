package ar.com.santander.rio.mbanking.services.soap.environments;

import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.Environment;

public interface IEnvironment {
    Environment getEnvironmentType();

    String getUrlWebService();
}
