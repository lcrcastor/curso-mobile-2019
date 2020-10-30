package butterknife.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class ViewInjection {
    private final int a;
    private final Set<ViewBinding> b = new LinkedHashSet();
    private final LinkedHashMap<ListenerClass, Map<ListenerMethod, Set<ListenerBinding>>> c = new LinkedHashMap<>();

    ViewInjection(int i) {
        this.a = i;
    }

    public int a() {
        return this.a;
    }

    public Collection<ViewBinding> b() {
        return this.b;
    }

    public Map<ListenerClass, Map<ListenerMethod, Set<ListenerBinding>>> c() {
        return this.c;
    }

    public boolean a(ListenerClass listenerClass, ListenerMethod listenerMethod) {
        Map map = (Map) this.c.get(listenerClass);
        return map != null && map.containsKey(listenerMethod);
    }

    public void a(ListenerClass listenerClass, ListenerMethod listenerMethod, ListenerBinding listenerBinding) {
        Set set;
        Map map = (Map) this.c.get(listenerClass);
        if (map == null) {
            map = new LinkedHashMap();
            this.c.put(listenerClass, map);
            set = null;
        } else {
            set = (Set) map.get(listenerMethod);
        }
        if (set == null) {
            set = new LinkedHashSet();
            map.put(listenerMethod, set);
        }
        set.add(listenerBinding);
    }

    public void a(ViewBinding viewBinding) {
        this.b.add(viewBinding);
    }

    public List<Binding> d() {
        ArrayList arrayList = new ArrayList();
        for (ViewBinding viewBinding : this.b) {
            if (viewBinding.d()) {
                arrayList.add(viewBinding);
            }
        }
        for (Map values : this.c.values()) {
            for (Set<ListenerBinding> it : values.values()) {
                for (ListenerBinding listenerBinding : it) {
                    if (listenerBinding.d()) {
                        arrayList.add(listenerBinding);
                    }
                }
            }
        }
        return arrayList;
    }
}
