package com.crashlytics.android;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.beta.Beta;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsListener;
import com.crashlytics.android.core.PinningInfoProvider;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitGroup;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Crashlytics extends Kit<Void> implements KitGroup {
    public static final String TAG = "Crashlytics";
    public final Answers answers;
    public final Beta beta;
    public final CrashlyticsCore core;
    public final Collection<? extends Kit> kits;

    public static class Builder {
        private Answers a;
        private Beta b;
        private CrashlyticsCore c;
        private com.crashlytics.android.core.CrashlyticsCore.Builder d;

        @Deprecated
        public Builder delay(float f) {
            a().delay(f);
            return this;
        }

        @Deprecated
        public Builder listener(CrashlyticsListener crashlyticsListener) {
            a().listener(crashlyticsListener);
            return this;
        }

        @Deprecated
        public Builder pinningInfo(PinningInfoProvider pinningInfoProvider) {
            a().pinningInfo(pinningInfoProvider);
            return this;
        }

        @Deprecated
        public Builder disabled(boolean z) {
            a().disabled(z);
            return this;
        }

        public Builder answers(Answers answers) {
            if (answers == null) {
                throw new NullPointerException("Answers Kit must not be null.");
            } else if (this.a != null) {
                throw new IllegalStateException("Answers Kit already set.");
            } else {
                this.a = answers;
                return this;
            }
        }

        public Builder beta(Beta beta) {
            if (beta == null) {
                throw new NullPointerException("Beta Kit must not be null.");
            } else if (this.b != null) {
                throw new IllegalStateException("Beta Kit already set.");
            } else {
                this.b = beta;
                return this;
            }
        }

        public Builder core(CrashlyticsCore crashlyticsCore) {
            if (crashlyticsCore == null) {
                throw new NullPointerException("CrashlyticsCore Kit must not be null.");
            } else if (this.c != null) {
                throw new IllegalStateException("CrashlyticsCore Kit already set.");
            } else {
                this.c = crashlyticsCore;
                return this;
            }
        }

        public Crashlytics build() {
            if (this.d != null) {
                if (this.c != null) {
                    throw new IllegalStateException("Must not use Deprecated methods delay(), disabled(), listener(), pinningInfoProvider() with core()");
                }
                this.c = this.d.build();
            }
            if (this.a == null) {
                this.a = new Answers();
            }
            if (this.b == null) {
                this.b = new Beta();
            }
            if (this.c == null) {
                this.c = new CrashlyticsCore();
            }
            return new Crashlytics(this.a, this.b, this.c);
        }

        private synchronized com.crashlytics.android.core.CrashlyticsCore.Builder a() {
            if (this.d == null) {
                this.d = new com.crashlytics.android.core.CrashlyticsCore.Builder();
            }
            return this.d;
        }
    }

    /* access modifiers changed from: protected */
    public Void doInBackground() {
        return null;
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:crashlytics";
    }

    public String getVersion() {
        return "2.6.3.143";
    }

    public Crashlytics() {
        this(new Answers(), new Beta(), new CrashlyticsCore());
    }

    Crashlytics(Answers answers2, Beta beta2, CrashlyticsCore crashlyticsCore) {
        this.answers = answers2;
        this.beta = beta2;
        this.core = crashlyticsCore;
        this.kits = Collections.unmodifiableCollection(Arrays.asList(new Kit[]{answers2, beta2, crashlyticsCore}));
    }

    public Collection<? extends Kit> getKits() {
        return this.kits;
    }

    public static Crashlytics getInstance() {
        return (Crashlytics) Fabric.getKit(Crashlytics.class);
    }

    public static PinningInfoProvider getPinningInfoProvider() {
        a();
        return getInstance().core.getPinningInfoProvider();
    }

    public static void logException(Throwable th) {
        a();
        getInstance().core.logException(th);
    }

    public static void log(String str) {
        a();
        getInstance().core.log(str);
    }

    public static void log(int i, String str, String str2) {
        a();
        getInstance().core.log(i, str, str2);
    }

    public static void setUserIdentifier(String str) {
        a();
        getInstance().core.setUserIdentifier(str);
    }

    public static void setUserName(String str) {
        a();
        getInstance().core.setUserName(str);
    }

    public static void setUserEmail(String str) {
        a();
        getInstance().core.setUserEmail(str);
    }

    public static void setString(String str, String str2) {
        a();
        getInstance().core.setString(str, str2);
    }

    public static void setBool(String str, boolean z) {
        a();
        getInstance().core.setBool(str, z);
    }

    public static void setDouble(String str, double d) {
        a();
        getInstance().core.setDouble(str, d);
    }

    public static void setFloat(String str, float f) {
        a();
        getInstance().core.setFloat(str, f);
    }

    public static void setInt(String str, int i) {
        a();
        getInstance().core.setInt(str, i);
    }

    public static void setLong(String str, long j) {
        a();
        getInstance().core.setLong(str, j);
    }

    public void crash() {
        this.core.crash();
    }

    public boolean verifyPinning(URL url) {
        return this.core.verifyPinning(url);
    }

    @Deprecated
    public synchronized void setListener(CrashlyticsListener crashlyticsListener) {
        this.core.setListener(crashlyticsListener);
    }

    @Deprecated
    public void setDebugMode(boolean z) {
        Fabric.getLogger().w(TAG, "Use of Crashlytics.setDebugMode is deprecated.");
    }

    @Deprecated
    public boolean getDebugMode() {
        Fabric.getLogger().w(TAG, "Use of Crashlytics.getDebugMode is deprecated.");
        getFabric();
        return Fabric.isDebuggable();
    }

    @Deprecated
    public static void setPinningInfoProvider(PinningInfoProvider pinningInfoProvider) {
        Fabric.getLogger().w(TAG, "Use of Crashlytics.setPinningInfoProvider is deprecated");
    }

    private static void a() {
        if (getInstance() == null) {
            throw new IllegalStateException("Crashlytics must be initialized by calling Fabric.with(Context) prior to calling Crashlytics.getInstance()");
        }
    }
}
