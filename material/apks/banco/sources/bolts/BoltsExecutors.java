package bolts;

import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class BoltsExecutors {
    private static final BoltsExecutors a = new BoltsExecutors();
    private final ExecutorService b;
    private final Executor c;

    static class ImmediateExecutor implements Executor {
        private ThreadLocal<Integer> a;

        private ImmediateExecutor() {
            this.a = new ThreadLocal<>();
        }

        private int a() {
            Integer num = (Integer) this.a.get();
            if (num == null) {
                num = Integer.valueOf(0);
            }
            int intValue = num.intValue() + 1;
            this.a.set(Integer.valueOf(intValue));
            return intValue;
        }

        private int b() {
            Integer num = (Integer) this.a.get();
            if (num == null) {
                num = Integer.valueOf(0);
            }
            int intValue = num.intValue() - 1;
            if (intValue == 0) {
                this.a.remove();
            } else {
                this.a.set(Integer.valueOf(intValue));
            }
            return intValue;
        }

        public void execute(Runnable runnable) {
            if (a() <= 15) {
                try {
                    runnable.run();
                } catch (Throwable th) {
                    b();
                    throw th;
                }
            } else {
                BoltsExecutors.a().execute(runnable);
            }
            b();
        }
    }

    private static boolean c() {
        String property = System.getProperty("java.runtime.name");
        if (property == null) {
            return false;
        }
        return property.toLowerCase(Locale.US).contains(AbstractSpiCall.ANDROID_CLIENT_TYPE);
    }

    private BoltsExecutors() {
        this.b = !c() ? Executors.newCachedThreadPool() : AndroidExecutors.a();
        this.c = new ImmediateExecutor();
    }

    public static ExecutorService a() {
        return a.b;
    }

    static Executor b() {
        return a.c;
    }
}
