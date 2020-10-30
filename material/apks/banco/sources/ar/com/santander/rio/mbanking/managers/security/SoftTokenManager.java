package ar.com.santander.rio.mbanking.managers.security;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import ar.com.santander.rio.mbanking.managers.preferences.SecuredPreferences;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.Environment;
import com.vusecurity.androidsdkwithbankcode.SeedCallback;
import com.vusecurity.androidsdkwithbankcode.ServerException;
import com.vusecurity.androidsdkwithbankcode.TimeDeltaCallback;
import com.vusecurity.androidsdkwithbankcode.VUMobileTokenSDK;
import java.util.concurrent.TimeUnit;

public class SoftTokenManager {
    private static String a = "";
    private Context b;
    private int c = 4;
    private SecuredPreferences d;

    public interface ObtainSeedDeviceListener {
        void onCodigoAsociacionError(String str);

        void onMaxRetriesPasscodeReached();

        void onNoConnectionError();

        void onPasscodeError(String str);

        void onSuccess();

        void onSynchronizeError(String str);
    }

    public enum SeedState {
        ACTIVE_SEED,
        INACTIVE_SEED,
        NO_SEED
    }

    public interface SinchronizeDeviceListener {
        void onError(String str);

        void onSuccess();
    }

    public SoftTokenManager(Context context) {
        this.b = context;
        this.d = SecuredPreferences.getInstance(context);
    }

    private String a() {
        a = ManagerTypeEnvironment.getUrlofToken(ManagerTypeEnvironment.getCurrentEnvironmentToken());
        return a;
    }

    public int getRetriesPasscode() {
        return this.d.getInt("isban.rio.temporal.banelco.retriespasscode", Integer.valueOf(0)).intValue();
    }

    public void setRetriesPasscode(int i) {
        a("isban.rio.temporal.banelco.retriespasscode", Integer.valueOf(i));
    }

    public String getCodigoAsociacion() {
        return this.d.getString("isban.rio.temporal.banelco.codigoasociacion", null);
    }

    public void setCodigoAsociacion(String str) {
        a("isban.rio.temporal.banelco.codigoasociacion", str);
    }

    public SeedState getSeedState() {
        return SeedState.valueOf(this.d.getString("isban.rio.temporal.banelco.seedstate", SeedState.NO_SEED.name()));
    }

    public void setSeedState(SeedState seedState) {
        a("isban.rio.temporal.banelco.seedstate", seedState);
    }

    public boolean isSyncEveryAvailable() {
        return this.d.contains("isban.rio.temporal.banelco.syncevery").booleanValue();
    }

    public int getSyncEvery() {
        return this.d.getInt("isban.rio.temporal.banelco.syncevery", Integer.valueOf(0)).intValue();
    }

    public void setSyncEvery(int i) {
        a("isban.rio.temporal.banelco.syncevery", Integer.valueOf(i));
    }

    public long getDelta() {
        return this.d.getLong("isban.rio.temporal.banelco.delta", Long.valueOf(0)).longValue();
    }

    public void setDelta(long j) {
        a("isban.rio.temporal.banelco.delta", Long.valueOf(j));
    }

    public int getInitAppCounter() {
        return this.d.getInt("isban.rio.temporal.banelco.initappcounter", Integer.valueOf(0)).intValue();
    }

    public void setInitAppCounter(int i) {
        a("isban.rio.temporal.banelco.initappcounter", Integer.valueOf(i));
    }

    public String getEncryptedSeed() {
        return this.d.getString("isban.rio.temporal.banelco.encryptedseed", null);
    }

    public void setEncryptedSeed(String str) {
        a("isban.rio.temporal.banelco.encryptedseed", str);
    }

    public String getLastTotp() {
        return this.d.getString("isban.rio.temporal.banelco.lasttotp", "");
    }

    public void setLastTotp(String str) {
        a("isban.rio.temporal.banelco.lasttotp", str);
    }

    public int getMaxRetriesPasscode() {
        return this.c;
    }

    public void synchronizeDevice(final SinchronizeDeviceListener sinchronizeDeviceListener) {
        Log.w("TOKEN_MANAGER", "Sincronizando dispositivo...");
        VUMobileTokenSDK.getTimeDelta(a(), false, "RIOP", new TimeDeltaCallback() {
            public void onResultsAvailable(long j, int i) {
                SoftTokenManager.this.setDelta(j);
                SoftTokenManager.this.setSyncEvery(i);
                SoftTokenManager.this.setInitAppCounter(0);
                StringBuilder sb = new StringBuilder();
                sb.append("Sincronizacion ok, delta: ");
                sb.append(SoftTokenManager.this.getDelta());
                Log.w("TOKEN_MANAGER", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Sincronizacion ok, syncEvery: ");
                sb2.append(SoftTokenManager.this.getSyncEvery());
                Log.w("TOKEN_MANAGER", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Sincronizacion ok, appCounter: ");
                sb3.append(SoftTokenManager.this.getInitAppCounter());
                Log.w("TOKEN_MANAGER", sb3.toString());
                sinchronizeDeviceListener.onSuccess();
            }

            public void onError(Throwable th) {
                StringBuilder sb = new StringBuilder();
                sb.append("Sincronizacion error, mensaje: ");
                sb.append(th.getMessage());
                Log.w("TOKEN_MANAGER", sb.toString());
                sinchronizeDeviceListener.onError(th.getMessage());
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(String str, final ObtainSeedDeviceListener obtainSeedDeviceListener) {
        Log.w("TOKEN_MANAGER", "Desencriptando semilla encriptada...");
        StringBuilder sb = new StringBuilder();
        sb.append("Desencriptando, semilla >");
        sb.append(getEncryptedSeed());
        sb.append("<");
        Log.w("TOKEN_MANAGER", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Desencriptando, passcode >");
        sb2.append(str);
        sb2.append("<");
        Log.w("TOKEN_MANAGER", sb2.toString());
        if (getRetriesPasscode() >= getMaxRetriesPasscode()) {
            obtainSeedDeviceListener.onMaxRetriesPasscodeReached();
            return;
        }
        String decryptedSeed = VUMobileTokenSDK.decryptedSeed(getEncryptedSeed(), str);
        if (decryptedSeed != null) {
            Log.w("TOKEN_MANAGER", "Semilla desencriptada ok");
            setEncryptedSeed(VUMobileTokenSDK.encryptSeed(decryptedSeed, "=488r^.7=t%77_:4%B3!^5%+m9A%=85+|*3^^G~%79=*!+h_2;!m-|!8==X!y4RK!|.=.J4z%+_~9J:5=X|3||;!8kH5224-%..Ws!34yY%9-.^v.6-YB+!g^!fQ:P-T"));
            Log.w("TOKEN_MANAGER", "Encriptando semilla desencriptada ok");
            setSeedState(SeedState.ACTIVE_SEED);
            setCodigoAsociacion("");
            synchronizeDevice(new SinchronizeDeviceListener() {
                public void onSuccess() {
                    obtainSeedDeviceListener.onSuccess();
                }

                public void onError(String str) {
                    SoftTokenManager.this.setDelta(0);
                    SoftTokenManager.this.setSyncEvery(1);
                    SoftTokenManager.this.setInitAppCounter(0);
                    obtainSeedDeviceListener.onSynchronizeError(str);
                }
            });
        } else {
            Log.w("TOKEN_MANAGER", "Semilla desencriptada error");
            setRetriesPasscode(getRetriesPasscode() + 1);
            if (getRetriesPasscode() >= getMaxRetriesPasscode()) {
                obtainSeedDeviceListener.onMaxRetriesPasscodeReached();
            } else {
                obtainSeedDeviceListener.onPasscodeError("El passcode es incorrecto");
            }
        }
    }

    private void a(String str, String str2, String str3, final SeedCallback seedCallback) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                seedCallback.onResultsAvailable(VUMobileTokenSDK.encryptSeed("SEMILLA", "123456"));
            }
        }, 2000);
    }

    public void obtainSeedDevice(final String str, final String str2, final ObtainSeedDeviceListener obtainSeedDeviceListener) {
        SeedState seedState = getSeedState();
        Log.w("TOKEN_MANAGER", "Obteniendo semilla encriptada...");
        StringBuilder sb = new StringBuilder();
        sb.append("Obteniendo, cupon >");
        sb.append(str);
        sb.append("< cupon viejo >");
        sb.append(getCodigoAsociacion());
        sb.append("<");
        Log.w("TOKEN_MANAGER", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Obteniendo, passcode >");
        sb2.append(str2);
        sb2.append("< passcode retries >");
        sb2.append(getRetriesPasscode());
        sb2.append("<");
        Log.w("TOKEN_MANAGER", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Obteniendo, seedstate >");
        sb3.append(getSeedState().name());
        sb3.append("<");
        Log.w("TOKEN_MANAGER", sb3.toString());
        if (seedState != SeedState.NO_SEED && seedState != SeedState.ACTIVE_SEED && (seedState != SeedState.INACTIVE_SEED || str.equals(getCodigoAsociacion()))) {
            a(str2, obtainSeedDeviceListener);
        } else if (ManagerTypeEnvironment.getCurrentEnvironment(this.b).getEnvironmentType().equals(Environment.MOCKS)) {
            a(a(), str, "RIOP", new SeedCallback() {
                public void onResultsAvailable(String str) {
                    SoftTokenManager.this.setRetriesPasscode(0);
                    SoftTokenManager.this.setCodigoAsociacion(str);
                    SoftTokenManager.this.setEncryptedSeed(str);
                    SoftTokenManager.this.setSeedState(SeedState.INACTIVE_SEED);
                    SoftTokenManager.this.a(str2, obtainSeedDeviceListener);
                }

                public void onError(Throwable th) {
                    if (((ServerException) th).getCode() != VUMobileTokenSDK.ERROR_CUPON_EXPIRADO || !th.getMessage().contains("Unable to resolve host")) {
                        obtainSeedDeviceListener.onCodigoAsociacionError(th.getMessage());
                    } else {
                        obtainSeedDeviceListener.onNoConnectionError();
                    }
                }
            });
        } else {
            VUMobileTokenSDK.getEncryptedSeed(a(), str, "RIOP", new SeedCallback() {
                public void onResultsAvailable(String str) {
                    SoftTokenManager.this.setRetriesPasscode(0);
                    SoftTokenManager.this.setCodigoAsociacion(str);
                    SoftTokenManager.this.setEncryptedSeed(str);
                    SoftTokenManager.this.setSeedState(SeedState.INACTIVE_SEED);
                    SoftTokenManager.this.a(str2, obtainSeedDeviceListener);
                }

                public void onError(Throwable th) {
                    if (((ServerException) th).getCode() != VUMobileTokenSDK.ERROR_CUPON_EXPIRADO || !th.getMessage().contains("Unable to resolve host")) {
                        obtainSeedDeviceListener.onCodigoAsociacionError(th.getMessage());
                    } else {
                        obtainSeedDeviceListener.onNoConnectionError();
                    }
                }
            });
        }
    }

    public Boolean isSoftTokenAvailable() {
        return Boolean.valueOf(getSeedState() == SeedState.ACTIVE_SEED);
    }

    public String getToken() {
        return VUMobileTokenSDK.genTotp(VUMobileTokenSDK.decryptedSeed(getEncryptedSeed(), "=488r^.7=t%77_:4%B3!^5%+m9A%=85+|*3^^G~%79=*!+h_2;!m-|!8==X!y4RK!|.=.J4z%+_~9J:5=X|3||;!8kH5224-%..Ws!34yY%9-.^v.6-YB+!g^!fQ:P-T"), getDelta());
    }

    public long getTokenTimeLeft() {
        return VUMobileTokenSDK.getTotpTtl(getDelta()) * 1000;
    }

    public String getNewToken() {
        if (isSoftTokenAvailable().booleanValue()) {
            String token = getToken();
            if (getLastTotp().equalsIgnoreCase(token)) {
                Long valueOf = Long.valueOf(getTokenTimeLeft());
                StringBuilder sb = new StringBuilder();
                sb.append("getNewToken: PROXIMO TOKEN EN ");
                sb.append(valueOf.longValue() / 1000);
                sb.append(" segundos");
                Log.i("TOKEN_MANAGER", sb.toString());
                try {
                    TimeUnit.MILLISECONDS.sleep(valueOf.longValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("TOKEN_MANAGER", "getNewToken: TOKEN GENERADO CORRECTAMENTE");
                String token2 = getToken();
                setLastTotp(token2);
                return token2;
            }
            Log.i("TOKEN_MANAGER", "getNewToken: TOKEN GENERADO CORRECTAMENTE");
            setLastTotp(token);
            return token;
        }
        Log.i("TOKEN_MANAGER", "getNewToken: TOKEN NO DISPOBILE");
        String str = "";
        setLastTotp(str);
        return str;
    }

    private void a(String str, String str2) {
        this.d.putString(str, str2);
    }

    private void a(String str, Integer num) {
        this.d.putInt(str, num, Boolean.valueOf(true));
    }

    private void a(String str, Long l) {
        this.d.putLong(str, l, Boolean.valueOf(true));
    }

    private void a(String str, SeedState seedState) {
        this.d.putString(str, seedState.name());
    }
}
