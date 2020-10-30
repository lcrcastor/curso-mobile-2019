package butterknife.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class ListenerBinding implements Binding {
    private final String a;
    private final List<Parameter> b;
    private final boolean c;

    ListenerBinding(String str, List<Parameter> list, boolean z) {
        this.a = str;
        this.b = Collections.unmodifiableList(new ArrayList(list));
        this.c = z;
    }

    public String b() {
        return this.a;
    }

    public List<Parameter> c() {
        return this.b;
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        sb.append("method '");
        sb.append(this.a);
        sb.append("'");
        return sb.toString();
    }

    public boolean d() {
        return this.c;
    }
}
