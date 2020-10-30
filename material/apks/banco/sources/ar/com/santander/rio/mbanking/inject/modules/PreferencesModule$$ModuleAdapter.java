package ar.com.santander.rio.mbanking.inject.modules;

import android.content.SharedPreferences;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.managers.preferences.StringPreference;
import dagger.internal.Binding;
import dagger.internal.BindingsGroup;
import dagger.internal.Linker;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import java.util.Set;
import javax.inject.Provider;

public final class PreferencesModule$$ModuleAdapter extends ModuleAdapter<PreferencesModule> {
    private static final String[] a = new String[0];
    private static final Class<?>[] b = new Class[0];
    private static final Class<?>[] c = new Class[0];

    public static final class ProvideDataVersionPreferenceProvidesAdapter extends ProvidesBinding<StringPreference> implements Provider<StringPreference> {
        private final PreferencesModule a;
        private Binding<SharedPreferences> b;

        public ProvideDataVersionPreferenceProvidesAdapter(PreferencesModule preferencesModule) {
            super("@ar.com.santander.rio.mbanking.inject.qualifiers.ExamplePreference()/ar.com.santander.rio.mbanking.managers.preferences.StringPreference", true, "ar.com.santander.rio.mbanking.inject.modules.PreferencesModule", "provideDataVersionPreference");
            this.a = preferencesModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("android.content.SharedPreferences", PreferencesModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
        }

        public StringPreference get() {
            return this.a.a((SharedPreferences) this.b.get());
        }
    }

    public static final class ProvideFavoritoPreferenceProvidesAdapter extends ProvidesBinding<StringPreference> implements Provider<StringPreference> {
        private final PreferencesModule a;
        private Binding<SharedPreferences> b;

        public ProvideFavoritoPreferenceProvidesAdapter(PreferencesModule preferencesModule) {
            super("@ar.com.santander.rio.mbanking.inject.qualifiers.FavoritoPreference()/ar.com.santander.rio.mbanking.managers.preferences.StringPreference", true, "ar.com.santander.rio.mbanking.inject.modules.PreferencesModule", "provideFavoritoPreference");
            this.a = preferencesModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("android.content.SharedPreferences", PreferencesModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
        }

        public StringPreference get() {
            return this.a.b((SharedPreferences) this.b.get());
        }
    }

    public static final class ProvideSharedPreferencesProvidesAdapter extends ProvidesBinding<SharedPreferences> implements Provider<SharedPreferences> {
        private final PreferencesModule a;
        private Binding<BaseApplication> b;

        public ProvideSharedPreferencesProvidesAdapter(PreferencesModule preferencesModule) {
            super("android.content.SharedPreferences", true, "ar.com.santander.rio.mbanking.inject.modules.PreferencesModule", "provideSharedPreferences");
            this.a = preferencesModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", PreferencesModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
        }

        public SharedPreferences get() {
            return this.a.a((BaseApplication) this.b.get());
        }
    }

    public PreferencesModule$$ModuleAdapter() {
        super(PreferencesModule.class, a, b, false, c, false, true);
    }

    public PreferencesModule newModule() {
        return new PreferencesModule();
    }

    public void getBindings(BindingsGroup bindingsGroup, PreferencesModule preferencesModule) {
        bindingsGroup.contributeProvidesBinding("android.content.SharedPreferences", new ProvideSharedPreferencesProvidesAdapter(preferencesModule));
        bindingsGroup.contributeProvidesBinding("@ar.com.santander.rio.mbanking.inject.qualifiers.ExamplePreference()/ar.com.santander.rio.mbanking.managers.preferences.StringPreference", new ProvideDataVersionPreferenceProvidesAdapter(preferencesModule));
        bindingsGroup.contributeProvidesBinding("@ar.com.santander.rio.mbanking.inject.qualifiers.FavoritoPreference()/ar.com.santander.rio.mbanking.managers.preferences.StringPreference", new ProvideFavoritoPreferenceProvidesAdapter(preferencesModule));
    }
}
