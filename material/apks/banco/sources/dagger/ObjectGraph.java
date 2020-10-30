package dagger;

import dagger.internal.Binding;
import dagger.internal.BindingsGroup;
import dagger.internal.FailoverLoader;
import dagger.internal.Keys;
import dagger.internal.Linker;
import dagger.internal.Loader;
import dagger.internal.ModuleAdapter;
import dagger.internal.Modules;
import dagger.internal.ProblemDetector;
import dagger.internal.SetBinding;
import dagger.internal.StaticInjection;
import dagger.internal.ThrowingErrorHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ObjectGraph {

    static class DaggerObjectGraph extends ObjectGraph {
        private final DaggerObjectGraph a;
        private final Linker b;
        private final Loader c;
        private final Map<Class<?>, StaticInjection> d;
        private final Map<String, Class<?>> e;
        private final List<SetBinding<?>> f;

        DaggerObjectGraph(DaggerObjectGraph daggerObjectGraph, Linker linker, Loader loader, Map<Class<?>, StaticInjection> map, Map<String, Class<?>> map2, List<SetBinding<?>> list) {
            this.a = daggerObjectGraph;
            this.b = (Linker) a(linker, "linker");
            this.c = (Loader) a(loader, "plugin");
            this.d = (Map) a(map, "staticInjections");
            this.e = (Map) a(map2, "injectableTypes");
            this.f = (List) a(list, "setBindings");
        }

        private static <T> T a(T t, String str) {
            if (t != null) {
                return t;
            }
            throw new NullPointerException(str);
        }

        /* access modifiers changed from: private */
        public static ObjectGraph b(DaggerObjectGraph daggerObjectGraph, Loader loader, Object... objArr) {
            Linker linker;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            StandardBindings standardBindings = daggerObjectGraph == null ? new StandardBindings() : new StandardBindings(daggerObjectGraph.f);
            BindingsGroup overridesBindings = new OverridesBindings();
            Iterator it = Modules.loadModules(loader, objArr).entrySet().iterator();
            while (true) {
                linker = null;
                if (!it.hasNext()) {
                    break;
                }
                Entry entry = (Entry) it.next();
                ModuleAdapter moduleAdapter = (ModuleAdapter) entry.getKey();
                for (String put : moduleAdapter.injectableTypes) {
                    linkedHashMap.put(put, moduleAdapter.moduleClass);
                }
                for (Class<?> put2 : moduleAdapter.staticInjections) {
                    linkedHashMap2.put(put2, null);
                }
                try {
                    moduleAdapter.getBindings(moduleAdapter.overrides ? overridesBindings : standardBindings, entry.getValue());
                } catch (IllegalArgumentException e2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(moduleAdapter.moduleClass.getSimpleName());
                    sb.append(": ");
                    sb.append(e2.getMessage());
                    throw new IllegalArgumentException(sb.toString(), e2);
                }
            }
            if (daggerObjectGraph != null) {
                linker = daggerObjectGraph.b;
            }
            Linker linker2 = new Linker(linker, loader, new ThrowingErrorHandler());
            linker2.installBindings(standardBindings);
            linker2.installBindings(overridesBindings);
            DaggerObjectGraph daggerObjectGraph2 = new DaggerObjectGraph(daggerObjectGraph, linker2, loader, linkedHashMap2, linkedHashMap, standardBindings.a);
            return daggerObjectGraph2;
        }

        public ObjectGraph plus(Object... objArr) {
            c();
            return b(this, this.c, objArr);
        }

        private void a() {
            for (Entry entry : this.d.entrySet()) {
                StaticInjection staticInjection = (StaticInjection) entry.getValue();
                if (staticInjection == null) {
                    staticInjection = this.c.getStaticInjection((Class) entry.getKey());
                    entry.setValue(staticInjection);
                }
                staticInjection.attach(this.b);
            }
        }

        private void b() {
            for (Entry entry : this.e.entrySet()) {
                this.b.requestBinding((String) entry.getKey(), entry.getValue(), ((Class) entry.getValue()).getClassLoader(), false, true);
            }
        }

        public void validate() {
            new ProblemDetector().detectProblems(c().values());
        }

        private Map<String, Binding<?>> c() {
            Map<String, Binding<?>> fullyLinkedBindings = this.b.fullyLinkedBindings();
            if (fullyLinkedBindings != null) {
                return fullyLinkedBindings;
            }
            synchronized (this.b) {
                Map<String, Binding<?>> fullyLinkedBindings2 = this.b.fullyLinkedBindings();
                if (fullyLinkedBindings2 != null) {
                    return fullyLinkedBindings2;
                }
                a();
                b();
                Map<String, Binding<?>> linkAll = this.b.linkAll();
                return linkAll;
            }
        }

        public void injectStatics() {
            synchronized (this.b) {
                a();
                this.b.linkRequested();
                a();
            }
            for (Entry value : this.d.entrySet()) {
                ((StaticInjection) value.getValue()).inject();
            }
        }

        public <T> T get(Class<T> cls) {
            String str = Keys.get(cls);
            return a(cls.getClassLoader(), cls.isInterface() ? str : Keys.getMembersKey(cls), str).get();
        }

        public <T> T inject(T t) {
            String membersKey = Keys.getMembersKey(t.getClass());
            a(t.getClass().getClassLoader(), membersKey, membersKey).injectMembers(t);
            return t;
        }

        private Binding<?> a(ClassLoader classLoader, String str, String str2) {
            Binding<?> requestBinding;
            Class cls = null;
            for (DaggerObjectGraph daggerObjectGraph = this; daggerObjectGraph != null; daggerObjectGraph = daggerObjectGraph.a) {
                cls = (Class) daggerObjectGraph.e.get(str);
                if (cls != null) {
                    break;
                }
            }
            if (cls == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("No inject registered for ");
                sb.append(str);
                sb.append(". You must explicitly add it to the 'injects' option in one of your modules.");
                throw new IllegalArgumentException(sb.toString());
            }
            synchronized (this.b) {
                requestBinding = this.b.requestBinding(str2, cls, classLoader, false, true);
                if (requestBinding == null || !requestBinding.isLinked()) {
                    this.b.linkRequested();
                    requestBinding = this.b.requestBinding(str2, cls, classLoader, false, true);
                }
            }
            return requestBinding;
        }
    }

    static final class OverridesBindings extends BindingsGroup {
        OverridesBindings() {
        }

        public Binding<?> contributeSetBinding(String str, SetBinding<?> setBinding) {
            throw new IllegalArgumentException("Module overrides cannot contribute set bindings.");
        }
    }

    static final class StandardBindings extends BindingsGroup {
        /* access modifiers changed from: private */
        public final List<SetBinding<?>> a;

        public StandardBindings() {
            this.a = new ArrayList();
        }

        public StandardBindings(List<SetBinding<?>> list) {
            this.a = new ArrayList(list.size());
            for (SetBinding setBinding : list) {
                SetBinding setBinding2 = new SetBinding(setBinding);
                this.a.add(setBinding2);
                put(setBinding2.provideKey, setBinding2);
            }
        }

        public Binding<?> contributeSetBinding(String str, SetBinding<?> setBinding) {
            this.a.add(setBinding);
            return super.put(str, setBinding);
        }
    }

    public abstract <T> T get(Class<T> cls);

    public abstract <T> T inject(T t);

    public abstract void injectStatics();

    public abstract ObjectGraph plus(Object... objArr);

    public abstract void validate();

    ObjectGraph() {
    }

    public static ObjectGraph create(Object... objArr) {
        return DaggerObjectGraph.b(null, new FailoverLoader(), objArr);
    }
}
