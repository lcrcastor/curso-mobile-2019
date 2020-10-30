package ar.com.santander.rio.mbanking.services.soap.environments;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import java.util.ArrayList;
import java.util.List;

public class ManagerTypeEnvironment {
    private static Environment currentEnvironment;
    private static EnvironmentToken currentEnvironmentToken;
    private static Environment environment;

    public enum Environment {
        CUSTOM,
        LOCAL,
        PRODUCTION,
        MOCKS,
        MB_1,
        MB_2,
        MB_3,
        MB_4,
        MB_5,
        MB_6,
        MB_7,
        MB_8,
        MBH_1,
        MBH_2,
        MBH_3,
        MBH_4,
        MBH_5,
        MBH_6,
        MBH_7,
        MBH_8,
        BETA8_5,
        WASI01,
        WASI02,
        WASI03,
        WASI04,
        WASI05,
        WASI06,
        WASI07,
        WASI08
    }

    public enum EnvironmentToken {
        HOMO,
        CERT,
        PROD,
        CUSTOM
    }

    public enum EnvironmentType {
        DESARROLLO,
        HOMOLOGACION,
        PRODUCCION,
        MOCKS,
        CUSTOM
    }

    public static Environment getCurrentEnvironment(Context context) {
        try {
            if (environment != null) {
                return environment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Environment(Environment.PRODUCTION, getUrlofEnvironment(Environment.PRODUCTION, context));
    }

    public static EnvironmentToken getCurrentEnvironmentToken() {
        if (currentEnvironmentToken != null) {
            return currentEnvironmentToken;
        }
        return EnvironmentToken.PROD;
    }

    public static void setCurrentEnvironment(Environment environment2, Context context, EnvironmentToken environmentToken) {
        currentEnvironmentToken = environmentToken;
        currentEnvironment = environment2;
        environment = new Environment(currentEnvironment, getUrlofEnvironment(currentEnvironment, context));
    }

    public static void setCurrentEnvironment(Environment environment2, String str, EnvironmentToken environmentToken) {
        currentEnvironmentToken = environmentToken;
        currentEnvironment = environment2;
        environment = new Environment(currentEnvironment, str);
    }

    public static void setCurrentEnvironment(String str, Context context) {
        try {
            currentEnvironment = Environment.valueOf(str);
            environment = new Environment(currentEnvironment, getUrlofEnvironment(currentEnvironment, context));
            currentEnvironmentToken = EnvironmentToken.PROD;
        } catch (Exception unused) {
            currentEnvironment = Environment.PRODUCTION;
            environment = new Environment(Environment.PRODUCTION, getUrlofEnvironment(Environment.PRODUCTION, context));
            currentEnvironmentToken = EnvironmentToken.PROD;
        }
    }

    public static List<Environment> getEnvironmentsOfType(EnvironmentType environmentType) {
        ArrayList arrayList = new ArrayList();
        switch (environmentType) {
            case DESARROLLO:
                arrayList.add(Environment.MB_1);
                arrayList.add(Environment.MB_2);
                arrayList.add(Environment.MB_3);
                arrayList.add(Environment.MB_4);
                arrayList.add(Environment.MB_5);
                arrayList.add(Environment.MB_6);
                arrayList.add(Environment.MB_7);
                arrayList.add(Environment.MB_8);
                return arrayList;
            case HOMOLOGACION:
                arrayList.add(Environment.MBH_1);
                arrayList.add(Environment.MBH_2);
                arrayList.add(Environment.MBH_3);
                arrayList.add(Environment.MBH_4);
                arrayList.add(Environment.MBH_5);
                arrayList.add(Environment.MBH_6);
                arrayList.add(Environment.MBH_7);
                arrayList.add(Environment.MBH_8);
                return arrayList;
            case PRODUCCION:
                arrayList.add(Environment.PRODUCTION);
                arrayList.add(Environment.BETA8_5);
                arrayList.add(Environment.WASI01);
                arrayList.add(Environment.WASI02);
                arrayList.add(Environment.WASI03);
                arrayList.add(Environment.WASI04);
                arrayList.add(Environment.WASI05);
                arrayList.add(Environment.WASI06);
                arrayList.add(Environment.WASI07);
                arrayList.add(Environment.WASI08);
                return arrayList;
            case MOCKS:
                arrayList.add(Environment.MOCKS);
                return arrayList;
            case CUSTOM:
                arrayList.add(Environment.CUSTOM);
                return arrayList;
            default:
                arrayList.add(Environment.MOCKS);
                arrayList.add(Environment.CUSTOM);
                return arrayList;
        }
    }

    public static List<EnvironmentToken> getEnvironmentsToken() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(EnvironmentToken.HOMO);
        arrayList.add(EnvironmentToken.CERT);
        arrayList.add(EnvironmentToken.PROD);
        arrayList.add(EnvironmentToken.CUSTOM);
        return arrayList;
    }

    public static String getUrlofEnvironment(Environment environment2, Context context) {
        switch (environment2) {
            case MB_1:
                return context.getString(R.string.MB_1_ENVIRONMENT);
            case MB_2:
                return context.getString(R.string.MB_2_ENVIRONMENT);
            case MB_3:
                return context.getString(R.string.MB_3_ENVIRONMENT);
            case MB_4:
                return context.getString(R.string.MB_4_ENVIRONMENT);
            case MB_5:
                return context.getString(R.string.MB_5_ENVIRONMENT);
            case MB_6:
                return context.getString(R.string.MB_6_ENVIRONMENT);
            case MB_7:
                return context.getString(R.string.MB_7_ENVIRONMENT);
            case MB_8:
                return context.getString(R.string.MB_8_ENVIRONMENT);
            case MBH_1:
                return context.getString(R.string.MBH_1_ENVIRONMENT);
            case MBH_2:
                return context.getString(R.string.MBH_2_ENVIRONMENT);
            case MBH_3:
                return context.getString(R.string.MBH_3_ENVIRONMENT);
            case MBH_4:
                return context.getString(R.string.MBH_4_ENVIRONMENT);
            case MBH_5:
                return context.getString(R.string.MBH_5_ENVIRONMENT);
            case MBH_6:
                return context.getString(R.string.MBH_6_ENVIRONMENT);
            case MBH_7:
                return context.getString(R.string.MBH_7_ENVIRONMENT);
            case MBH_8:
                return context.getString(R.string.MBH_8_ENVIRONMENT);
            case PRODUCTION:
                return context.getString(R.string.PRO_ENVIRONMENT);
            case BETA8_5:
                return context.getString(R.string.BETA85_ENVIRONMENT);
            case WASI01:
                return context.getString(R.string.WASI01_ENVIRONMENT);
            case WASI02:
                return context.getString(R.string.WASI02_ENVIRONMENT);
            case WASI03:
                return context.getString(R.string.WASI03_ENVIRONMENT);
            case WASI04:
                return context.getString(R.string.WASI04_ENVIRONMENT);
            case WASI05:
                return context.getString(R.string.WASI05_ENVIRONMENT);
            case WASI06:
                return context.getString(R.string.WASI06_ENVIRONMENT);
            case WASI07:
                return context.getString(R.string.WASI07_ENVIRONMENT);
            case WASI08:
                return context.getString(R.string.WASI08_ENVIRONMENT);
            default:
                return "";
        }
    }

    public static String getUrlofToken(EnvironmentToken environmentToken) {
        switch (environmentToken) {
            case HOMO:
                return "https://tokenvhomo.banelcoservices.com.ar";
            case CERT:
                return "https://tokenvcert.banelcoservices.com.ar";
            case PROD:
                return "https://tokenv.banelcoservices.com.ar";
            case CUSTOM:
                return "";
            default:
                return "";
        }
    }
}
