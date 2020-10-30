package butterknife.internal;

final class CollectionBinding implements Binding {
    private final String a;
    private final String b;
    private final Kind c;
    private final boolean d;

    enum Kind {
        ARRAY,
        LIST
    }

    CollectionBinding(String str, String str2, Kind kind, boolean z) {
        this.a = str;
        this.b = str2;
        this.c = kind;
        this.d = z;
    }

    public String b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }

    public Kind d() {
        return this.c;
    }

    public boolean e() {
        return this.d;
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        sb.append("field '");
        sb.append(this.a);
        sb.append("'");
        return sb.toString();
    }
}
