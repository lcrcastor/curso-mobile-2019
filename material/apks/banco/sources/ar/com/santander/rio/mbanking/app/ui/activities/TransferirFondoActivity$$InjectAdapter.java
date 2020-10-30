package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class TransferirFondoActivity$$InjectAdapter extends Binding<TransferirFondoActivity> implements MembersInjector<TransferirFondoActivity>, Provider<TransferirFondoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public TransferirFondoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.TransferirFondoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.TransferirFondoActivity", false, TransferirFondoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", TransferirFondoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", TransferirFondoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public TransferirFondoActivity get() {
        TransferirFondoActivity transferirFondoActivity = new TransferirFondoActivity();
        injectMembers(transferirFondoActivity);
        return transferirFondoActivity;
    }

    public void injectMembers(TransferirFondoActivity transferirFondoActivity) {
        transferirFondoActivity.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(transferirFondoActivity);
    }
}
