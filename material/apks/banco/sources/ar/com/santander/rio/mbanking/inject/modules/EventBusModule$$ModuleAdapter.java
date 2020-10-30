package ar.com.santander.rio.mbanking.inject.modules;

import com.squareup.otto.Bus;
import dagger.internal.BindingsGroup;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import javax.inject.Provider;

public final class EventBusModule$$ModuleAdapter extends ModuleAdapter<EventBusModule> {
    private static final String[] a = new String[0];
    private static final Class<?>[] b = new Class[0];
    private static final Class<?>[] c = new Class[0];

    public static final class ProvideEventBusProvidesAdapter extends ProvidesBinding<Bus> implements Provider<Bus> {
        private final EventBusModule a;

        public ProvideEventBusProvidesAdapter(EventBusModule eventBusModule) {
            super("com.squareup.otto.Bus", true, "ar.com.santander.rio.mbanking.inject.modules.EventBusModule", "provideEventBus");
            this.a = eventBusModule;
            setLibrary(true);
        }

        public Bus get() {
            return this.a.a();
        }
    }

    public EventBusModule$$ModuleAdapter() {
        super(EventBusModule.class, a, b, false, c, false, true);
    }

    public EventBusModule newModule() {
        return new EventBusModule();
    }

    public void getBindings(BindingsGroup bindingsGroup, EventBusModule eventBusModule) {
        bindingsGroup.contributeProvidesBinding("com.squareup.otto.Bus", new ProvideEventBusProvidesAdapter(eventBusModule));
    }
}
