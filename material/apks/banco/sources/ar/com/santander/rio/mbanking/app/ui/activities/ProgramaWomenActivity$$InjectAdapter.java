package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ProgramaWomenActivity$$InjectAdapter extends Binding<ProgramaWomenActivity> implements MembersInjector<ProgramaWomenActivity>, Provider<ProgramaWomenActivity> {
    private Binding<IDataManager> a;
    private Binding<ITRSABaseActivity> b;

    public ProgramaWomenActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ProgramaWomenActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.ProgramaWomenActivity", false, ProgramaWomenActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ProgramaWomenActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity", ProgramaWomenActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ProgramaWomenActivity get() {
        ProgramaWomenActivity programaWomenActivity = new ProgramaWomenActivity();
        injectMembers(programaWomenActivity);
        return programaWomenActivity;
    }

    public void injectMembers(ProgramaWomenActivity programaWomenActivity) {
        programaWomenActivity.p = (IDataManager) this.a.get();
        this.b.injectMembers(programaWomenActivity);
    }
}
