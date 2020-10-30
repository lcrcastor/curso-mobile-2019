package ar.com.santander.rio.mbanking.app.ui.adapters;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class WatsonMessagesRecyclerViewAdapter$$InjectAdapter extends Binding<WatsonMessagesRecyclerViewAdapter> implements MembersInjector<WatsonMessagesRecyclerViewAdapter> {
    private Binding<AnalyticsManager> a;

    public WatsonMessagesRecyclerViewAdapter$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.adapters.WatsonMessagesRecyclerViewAdapter", false, WatsonMessagesRecyclerViewAdapter.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", WatsonMessagesRecyclerViewAdapter.class, getClass().getClassLoader());
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
    }

    public void injectMembers(WatsonMessagesRecyclerViewAdapter watsonMessagesRecyclerViewAdapter) {
        watsonMessagesRecyclerViewAdapter.a = (AnalyticsManager) this.a.get();
    }
}
