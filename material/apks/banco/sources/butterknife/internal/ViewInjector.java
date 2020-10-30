package butterknife.internal;

import com.google.android.gms.analytics.ecommerce.Promotion;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class ViewInjector {
    private final Map<Integer, ViewInjection> a = new LinkedHashMap();
    private final Map<CollectionBinding, int[]> b = new LinkedHashMap();
    private final String c;
    private final String d;
    private final String e;
    private String f;

    ViewInjector(String str, String str2, String str3) {
        this.c = str;
        this.d = str2;
        this.e = str3;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, ViewBinding viewBinding) {
        b(i).a(viewBinding);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i, ListenerClass listenerClass, ListenerMethod listenerMethod, ListenerBinding listenerBinding) {
        ViewInjection b2 = b(i);
        if (b2.a(listenerClass, listenerMethod) && !"void".equals(listenerMethod.returnType())) {
            return false;
        }
        b2.a(listenerClass, listenerMethod, listenerBinding);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void a(int[] iArr, CollectionBinding collectionBinding) {
        this.b.put(collectionBinding, iArr);
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        this.f = str;
    }

    /* access modifiers changed from: 0000 */
    public ViewInjection a(int i) {
        return (ViewInjection) this.a.get(Integer.valueOf(i));
    }

    private ViewInjection b(int i) {
        ViewInjection viewInjection = (ViewInjection) this.a.get(Integer.valueOf(i));
        if (viewInjection != null) {
            return viewInjection;
        }
        ViewInjection viewInjection2 = new ViewInjection(i);
        this.a.put(Integer.valueOf(i), viewInjection2);
        return viewInjection2;
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.c);
        sb.append(".");
        sb.append(this.d);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        StringBuilder sb = new StringBuilder();
        sb.append("// Generated code from Butter Knife. Do not modify!\n");
        sb.append("package ");
        sb.append(this.c);
        sb.append(";\n\n");
        sb.append("import android.view.View;\n");
        sb.append("import butterknife.ButterKnife.Finder;\n\n");
        sb.append("public class ");
        sb.append(this.d);
        sb.append(" {\n");
        a(sb);
        sb.append(10);
        b(sb);
        sb.append("}\n");
        return sb.toString();
    }

    private void a(StringBuilder sb) {
        sb.append("  public static void inject(Finder finder, final ");
        sb.append(this.e);
        sb.append(" target, Object source) {\n");
        if (this.f != null) {
            sb.append("    ");
            sb.append(this.f);
            sb.append(".inject(finder, target, source);\n\n");
        }
        sb.append("    View view;\n");
        for (ViewInjection a2 : this.a.values()) {
            a(sb, a2);
        }
        for (Entry entry : this.b.entrySet()) {
            a(sb, (CollectionBinding) entry.getKey(), (int[]) entry.getValue());
        }
        sb.append("  }\n");
    }

    private void a(StringBuilder sb, CollectionBinding collectionBinding, int[] iArr) {
        sb.append("    target.");
        sb.append(collectionBinding.b());
        sb.append(" = ");
        switch (collectionBinding.d()) {
            case ARRAY:
                sb.append("Finder.arrayOf(");
                break;
            case LIST:
                sb.append("Finder.listOf(");
                break;
            default:
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Unknown kind: ");
                sb2.append(collectionBinding.d());
                throw new IllegalStateException(sb2.toString());
        }
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append("\n        ");
            a(sb, collectionBinding.c());
            if (collectionBinding.e()) {
                sb.append("finder.findRequiredView(source, ");
                sb.append(iArr[i]);
                sb.append(", \"");
                sb.append(collectionBinding.b());
                sb.append("\")");
            } else {
                sb.append("finder.findOptionalView(source, ");
                sb.append(iArr[i]);
                sb.append(")");
            }
        }
        sb.append("\n    );");
    }

    private void a(StringBuilder sb, ViewInjection viewInjection) {
        sb.append("    view = ");
        List d2 = viewInjection.d();
        if (d2.isEmpty()) {
            sb.append("finder.findOptionalView(source, ");
            sb.append(viewInjection.a());
            sb.append(");\n");
        } else if (viewInjection.a() == -1) {
            sb.append("target;\n");
        } else {
            sb.append("finder.findRequiredView(source, ");
            sb.append(viewInjection.a());
            sb.append(", \"");
            a(sb, d2);
            sb.append("\");\n");
        }
        b(sb, viewInjection);
        c(sb, viewInjection);
    }

    private void b(StringBuilder sb, ViewInjection viewInjection) {
        Collection<ViewBinding> b2 = viewInjection.b();
        if (!b2.isEmpty()) {
            for (ViewBinding viewBinding : b2) {
                sb.append("    target.");
                sb.append(viewBinding.b());
                sb.append(" = ");
                a(sb, viewBinding.c());
                sb.append("view;\n");
            }
        }
    }

    private void c(StringBuilder sb, ViewInjection viewInjection) {
        StringBuilder sb2 = sb;
        Map c2 = viewInjection.c();
        if (!c2.isEmpty()) {
            String str = "";
            boolean isEmpty = viewInjection.d().isEmpty();
            if (isEmpty) {
                sb2.append("    if (view != null) {\n");
                str = "  ";
            }
            for (Entry entry : c2.entrySet()) {
                ListenerClass listenerClass = (ListenerClass) entry.getKey();
                Map map = (Map) entry.getValue();
                boolean z = !"android.view.View".equals(listenerClass.targetType());
                sb2.append(str);
                sb2.append("    ");
                if (z) {
                    sb2.append("((");
                    sb2.append(listenerClass.targetType());
                    if (listenerClass.genericArguments() > 0) {
                        sb2.append('<');
                        for (int i = 0; i < listenerClass.genericArguments(); i++) {
                            if (i > 0) {
                                sb2.append(", ");
                            }
                            sb2.append('?');
                        }
                        sb2.append('>');
                    }
                    sb2.append(") ");
                }
                sb2.append(Promotion.ACTION_VIEW);
                if (z) {
                    sb2.append(')');
                }
                sb2.append('.');
                sb2.append(listenerClass.setter());
                sb2.append("(\n");
                sb2.append(str);
                sb2.append("      new ");
                sb2.append(listenerClass.type());
                sb2.append("() {\n");
                for (ListenerMethod listenerMethod : a(listenerClass)) {
                    sb2.append(str);
                    sb2.append("        @Override public ");
                    sb2.append(listenerMethod.returnType());
                    sb2.append(TokenParser.SP);
                    sb2.append(listenerMethod.name());
                    sb2.append("(\n");
                    String[] parameters = listenerMethod.parameters();
                    int length = parameters.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        sb2.append(str);
                        sb2.append("          ");
                        sb2.append(parameters[i2]);
                        sb2.append(" p");
                        sb2.append(i2);
                        if (i2 < length - 1) {
                            sb2.append(',');
                        }
                        sb2.append(10);
                    }
                    sb2.append(str);
                    sb2.append("        ) {\n");
                    sb2.append(str);
                    sb2.append("          ");
                    boolean z2 = !"void".equals(listenerMethod.returnType());
                    if (z2) {
                        sb2.append("return ");
                    }
                    if (map.containsKey(listenerMethod)) {
                        Iterator it = ((Set) map.get(listenerMethod)).iterator();
                        while (it.hasNext()) {
                            ListenerBinding listenerBinding = (ListenerBinding) it.next();
                            sb2.append("target.");
                            sb2.append(listenerBinding.b());
                            sb2.append('(');
                            List c3 = listenerBinding.c();
                            String[] parameters2 = listenerMethod.parameters();
                            int size = c3.size();
                            for (int i3 = 0; i3 < size; i3++) {
                                Parameter parameter = (Parameter) c3.get(i3);
                                int a2 = parameter.a();
                                a(sb2, parameters2[a2], parameter.b());
                                sb2.append('p');
                                sb2.append(a2);
                                if (i3 < size - 1) {
                                    sb2.append(", ");
                                }
                            }
                            sb2.append(");");
                            if (it.hasNext()) {
                                sb2.append("\n");
                                sb2.append("          ");
                            }
                        }
                    } else if (z2) {
                        sb2.append(listenerMethod.defaultReturn());
                        sb2.append(';');
                    }
                    sb2.append(10);
                    sb2.append(str);
                    sb2.append("        }\n");
                }
                sb2.append(str);
                sb2.append("      });\n");
            }
            if (isEmpty) {
                sb2.append("    }\n");
            }
        }
    }

    static List<ListenerMethod> a(ListenerClass listenerClass) {
        Enum[] enumArr;
        if (listenerClass.method().length == 1) {
            return Arrays.asList(listenerClass.method());
        }
        try {
            ArrayList arrayList = new ArrayList();
            Class callbacks = listenerClass.callbacks();
            for (Enum enumR : (Enum[]) callbacks.getEnumConstants()) {
                ListenerMethod listenerMethod = (ListenerMethod) callbacks.getField(enumR.name()).getAnnotation(ListenerMethod.class);
                if (listenerMethod == null) {
                    throw new IllegalStateException(String.format("@%s's %s.%s missing @%s annotation.", new Object[]{callbacks.getEnclosingClass().getSimpleName(), callbacks.getSimpleName(), enumR.name(), ListenerMethod.class.getSimpleName()}));
                }
                arrayList.add(listenerMethod);
            }
            return arrayList;
        } catch (NoSuchFieldException e2) {
            throw new AssertionError(e2);
        }
    }

    private void b(StringBuilder sb) {
        sb.append("  public static void reset(");
        sb.append(this.e);
        sb.append(" target) {\n");
        if (this.f != null) {
            sb.append("    ");
            sb.append(this.f);
            sb.append(".reset(target);\n\n");
        }
        for (ViewInjection b2 : this.a.values()) {
            for (ViewBinding viewBinding : b2.b()) {
                sb.append("    target.");
                sb.append(viewBinding.b());
                sb.append(" = null;\n");
            }
        }
        for (CollectionBinding collectionBinding : this.b.keySet()) {
            sb.append("    target.");
            sb.append(collectionBinding.b());
            sb.append(" = null;\n");
        }
        sb.append("  }\n");
    }

    static void a(StringBuilder sb, String str) {
        a(sb, "android.view.View", str);
    }

    static void a(StringBuilder sb, String str, String str2) {
        if (!str.equals(str2)) {
            sb.append('(');
            sb.append(str2);
            sb.append(") ");
        }
    }

    static void a(StringBuilder sb, List<Binding> list) {
        switch (list.size()) {
            case 1:
                sb.append(((Binding) list.get(0)).a());
                return;
            case 2:
                sb.append(((Binding) list.get(0)).a());
                sb.append(" and ");
                sb.append(((Binding) list.get(1)).a());
                return;
            default:
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Binding binding = (Binding) list.get(i);
                    if (i != 0) {
                        sb.append(", ");
                    }
                    if (i == size - 1) {
                        sb.append("and ");
                    }
                    sb.append(binding.a());
                }
                return;
        }
    }
}
