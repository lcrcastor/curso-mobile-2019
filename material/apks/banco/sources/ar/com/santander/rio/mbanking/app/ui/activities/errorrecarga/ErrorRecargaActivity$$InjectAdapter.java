package ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ErrorRecargaActivity$$InjectAdapter extends Binding<ErrorRecargaActivity> implements MembersInjector<ErrorRecargaActivity>, Provider<ErrorRecargaActivity> {
    private Binding<SessionManager> a;
    private Binding<BaseMvpActivity> b;

    public ErrorRecargaActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga.ErrorRecargaActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga.ErrorRecargaActivity", false, ErrorRecargaActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ErrorRecargaActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", ErrorRecargaActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ErrorRecargaActivity get() {
        ErrorRecargaActivity errorRecargaActivity = new ErrorRecargaActivity();
        injectMembers(errorRecargaActivity);
        return errorRecargaActivity;
    }

    public void injectMembers(ErrorRecargaActivity errorRecargaActivity) {
        errorRecargaActivity.q = (SessionManager) this.a.get();
        this.b.injectMembers(errorRecargaActivity);
    }
}
