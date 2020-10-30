package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class WatsonFragment$$InjectAdapter extends Binding<WatsonFragment> implements MembersInjector<WatsonFragment>, Provider<WatsonFragment> {
    private Binding<IDataManager> a;
    private Binding<BaseFragment> b;

    public WatsonFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.WatsonFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.WatsonFragment", false, WatsonFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", WatsonFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", WatsonFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public WatsonFragment get() {
        WatsonFragment watsonFragment = new WatsonFragment();
        injectMembers(watsonFragment);
        return watsonFragment;
    }

    public void injectMembers(WatsonFragment watsonFragment) {
        watsonFragment.a = (IDataManager) this.a.get();
        this.b.injectMembers(watsonFragment);
    }
}
