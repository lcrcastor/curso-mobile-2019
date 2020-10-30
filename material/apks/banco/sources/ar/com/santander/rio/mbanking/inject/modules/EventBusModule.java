package ar.com.santander.rio.mbanking.inject.modules;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true)
public class EventBusModule {
    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public Bus a() {
        return new Bus(ThreadEnforcer.ANY);
    }
}
