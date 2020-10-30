package butterknife.internal;

final class ViewBinding implements Binding {
    private final String a;
    private final String b;
    private final boolean c;

    ViewBinding(String str, String str2, boolean z) {
        this.a = str;
        this.b = str2;
        this.c = z;
    }

    public String b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        sb.append("field '");
        sb.append(this.a);
        sb.append("'");
        return sb.toString();
    }

    public boolean d() {
        return this.c;
    }
}
