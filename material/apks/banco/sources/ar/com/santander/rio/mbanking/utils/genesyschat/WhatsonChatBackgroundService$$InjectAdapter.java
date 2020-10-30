package ar.com.santander.rio.mbanking.utils.genesyschat;

import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class WhatsonChatBackgroundService$$InjectAdapter extends Binding<WhatsonChatBackgroundService> implements MembersInjector<WhatsonChatBackgroundService>, Provider<WhatsonChatBackgroundService> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;

    public WhatsonChatBackgroundService$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.utils.genesyschat.WhatsonChatBackgroundService", "members/ar.com.santander.rio.mbanking.utils.genesyschat.WhatsonChatBackgroundService", false, WhatsonChatBackgroundService.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", WhatsonChatBackgroundService.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", WhatsonChatBackgroundService.class, getClass().getClassLoader());
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public WhatsonChatBackgroundService get() {
        WhatsonChatBackgroundService whatsonChatBackgroundService = new WhatsonChatBackgroundService();
        injectMembers(whatsonChatBackgroundService);
        return whatsonChatBackgroundService;
    }

    public void injectMembers(WhatsonChatBackgroundService whatsonChatBackgroundService) {
        whatsonChatBackgroundService.bus = (Bus) this.a.get();
        whatsonChatBackgroundService.mDatamanager = (IDataManager) this.b.get();
    }
}
