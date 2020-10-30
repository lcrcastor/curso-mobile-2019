package android.arch.lifecycle;

import java.util.HashMap;

public class ViewModelStore {
    private final HashMap<String, ViewModel> a = new HashMap<>();

    /* access modifiers changed from: 0000 */
    public final void a(String str, ViewModel viewModel) {
        ViewModel viewModel2 = (ViewModel) this.a.get(str);
        if (viewModel2 != null) {
            viewModel2.onCleared();
        }
        this.a.put(str, viewModel);
    }

    /* access modifiers changed from: 0000 */
    public final ViewModel a(String str) {
        return (ViewModel) this.a.get(str);
    }

    public final void clear() {
        for (ViewModel onCleared : this.a.values()) {
            onCleared.onCleared();
        }
        this.a.clear();
    }
}
