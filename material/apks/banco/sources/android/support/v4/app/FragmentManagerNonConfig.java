package android.support.v4.app;

import android.arch.lifecycle.ViewModelStore;
import java.util.List;

public class FragmentManagerNonConfig {
    private final List<Fragment> a;
    private final List<FragmentManagerNonConfig> b;
    private final List<ViewModelStore> c;

    FragmentManagerNonConfig(List<Fragment> list, List<FragmentManagerNonConfig> list2, List<ViewModelStore> list3) {
        this.a = list;
        this.b = list2;
        this.c = list3;
    }

    /* access modifiers changed from: 0000 */
    public List<Fragment> a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public List<FragmentManagerNonConfig> b() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public List<ViewModelStore> c() {
        return this.c;
    }
}
