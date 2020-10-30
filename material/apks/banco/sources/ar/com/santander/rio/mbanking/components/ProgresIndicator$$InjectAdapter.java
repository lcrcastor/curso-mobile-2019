package ar.com.santander.rio.mbanking.components;

import ar.com.santander.rio.mbanking.app.base.BaseDialogFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ProgresIndicator$$InjectAdapter extends Binding<ProgresIndicator> implements MembersInjector<ProgresIndicator>, Provider<ProgresIndicator> {
    private Binding<IDataManager> a;
    private Binding<BaseDialogFragment> b;

    public ProgresIndicator$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.components.ProgresIndicator", "members/ar.com.santander.rio.mbanking.components.ProgresIndicator", false, ProgresIndicator.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ProgresIndicator.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseDialogFragment", ProgresIndicator.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ProgresIndicator get() {
        ProgresIndicator progresIndicator = new ProgresIndicator();
        injectMembers(progresIndicator);
        return progresIndicator;
    }

    public void injectMembers(ProgresIndicator progresIndicator) {
        progresIndicator.ad = (IDataManager) this.a.get();
        this.b.injectMembers(progresIndicator);
    }
}
