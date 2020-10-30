package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class TokenOBPActivity$$InjectAdapter extends Binding<TokenOBPActivity> implements MembersInjector<TokenOBPActivity>, Provider<TokenOBPActivity> {
    private Binding<SessionManager> a;
    private Binding<IDataManager> b;
    private Binding<SettingsManager> c;
    private Binding<SoftTokenManager> d;
    private Binding<BaseMvpActivity> e;

    public TokenOBPActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.TokenOBPActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.TokenOBPActivity", false, TokenOBPActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", TokenOBPActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", TokenOBPActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", TokenOBPActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", TokenOBPActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", TokenOBPActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public TokenOBPActivity get() {
        TokenOBPActivity tokenOBPActivity = new TokenOBPActivity();
        injectMembers(tokenOBPActivity);
        return tokenOBPActivity;
    }

    public void injectMembers(TokenOBPActivity tokenOBPActivity) {
        tokenOBPActivity.p = (SessionManager) this.a.get();
        tokenOBPActivity.q = (IDataManager) this.b.get();
        tokenOBPActivity.r = (SettingsManager) this.c.get();
        tokenOBPActivity.s = (SoftTokenManager) this.d.get();
        this.e.injectMembers(tokenOBPActivity);
    }
}
