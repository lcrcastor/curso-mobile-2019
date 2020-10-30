package butterknife.internal;

final class Parameter {
    static final Parameter[] a = new Parameter[0];
    private final int b;
    private final String c;

    Parameter(int i, String str) {
        this.b = i;
        this.c = str;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        return this.c;
    }
}
