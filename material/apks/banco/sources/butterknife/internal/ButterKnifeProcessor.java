package butterknife.internal;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnItemSelected;
import butterknife.OnLongClick;
import butterknife.OnPageChange;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import butterknife.Optional;
import butterknife.internal.ListenerClass.NONE;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

public final class ButterKnifeProcessor extends AbstractProcessor {
    public static final String ANDROID_PREFIX = "android.";
    public static final String JAVA_PREFIX = "java.";
    public static final String SUFFIX = "$$ViewInjector";
    static final /* synthetic */ boolean a = true;
    private static final String b = List.class.getCanonicalName();
    private static final List<Class<? extends Annotation>> c = Arrays.asList(new Class[]{OnCheckedChanged.class, OnClick.class, OnEditorAction.class, OnFocusChange.class, OnItemClick.class, OnItemLongClick.class, OnItemSelected.class, OnLongClick.class, OnPageChange.class, OnTextChanged.class, OnTouch.class});
    private Elements d;
    private Types e;
    private Filer f;

    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        ButterKnifeProcessor.super.init(processingEnvironment);
        this.d = processingEnvironment.getElementUtils();
        this.e = processingEnvironment.getTypeUtils();
        this.f = processingEnvironment.getFiler();
    }

    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(InjectView.class.getCanonicalName());
        linkedHashSet.add(InjectViews.class.getCanonicalName());
        for (Class canonicalName : c) {
            linkedHashSet.add(canonicalName.getCanonicalName());
        }
        return linkedHashSet;
    }

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Entry entry : a(roundEnvironment).entrySet()) {
            Element element = (TypeElement) entry.getKey();
            ViewInjector viewInjector = (ViewInjector) entry.getValue();
            try {
                Writer openWriter = this.f.createSourceFile(viewInjector.a(), new Element[]{element}).openWriter();
                openWriter.write(viewInjector.b());
                openWriter.flush();
                openWriter.close();
            } catch (IOException e2) {
                a(element, "Unable to write injector for type %s: %s", element, e2.getMessage());
            }
        }
        return true;
    }

    private Map<TypeElement, ViewInjector> a(RoundEnvironment roundEnvironment) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(InjectView.class)) {
            try {
                a(element, (Map<TypeElement, ViewInjector>) linkedHashMap, (Set<String>) linkedHashSet);
            } catch (Exception e2) {
                StringWriter stringWriter = new StringWriter();
                e2.printStackTrace(new PrintWriter(stringWriter));
                a(element, "Unable to generate view injector for @InjectView.\n\n%s", stringWriter);
            }
        }
        for (Element element2 : roundEnvironment.getElementsAnnotatedWith(InjectViews.class)) {
            try {
                b(element2, linkedHashMap, linkedHashSet);
            } catch (Exception e3) {
                StringWriter stringWriter2 = new StringWriter();
                e3.printStackTrace(new PrintWriter(stringWriter2));
                a(element2, "Unable to generate view injector for @InjectViews.\n\n%s", stringWriter2);
            }
        }
        for (Class a2 : c) {
            a(roundEnvironment, a2, (Map<TypeElement, ViewInjector>) linkedHashMap, (Set<String>) linkedHashSet);
        }
        for (Entry entry : linkedHashMap.entrySet()) {
            String a3 = a((TypeElement) entry.getKey(), (Set<String>) linkedHashSet);
            if (a3 != null) {
                ViewInjector viewInjector = (ViewInjector) entry.getValue();
                StringBuilder sb = new StringBuilder();
                sb.append(a3);
                sb.append(SUFFIX);
                viewInjector.a(sb.toString());
            }
        }
        return linkedHashMap;
    }

    private boolean a(Class<? extends Annotation> cls, String str, Element element) {
        boolean z;
        TypeElement enclosingElement = element.getEnclosingElement();
        Set modifiers = element.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.STATIC)) {
            a(element, "@%s %s must not be private or static. (%s.%s)", cls.getSimpleName(), str, enclosingElement.getQualifiedName(), element.getSimpleName());
            z = true;
        } else {
            z = false;
        }
        if (enclosingElement.getKind() != ElementKind.CLASS) {
            a((Element) enclosingElement, "@%s %s may only be contained in classes. (%s.%s)", cls.getSimpleName(), str, enclosingElement.getQualifiedName(), element.getSimpleName());
            z = true;
        }
        if (!enclosingElement.getModifiers().contains(Modifier.PRIVATE)) {
            return z;
        }
        a((Element) enclosingElement, "@%s %s may not be contained in private classes. (%s.%s)", cls.getSimpleName(), str, enclosingElement.getQualifiedName(), element.getSimpleName());
        return true;
    }

    private boolean a(Class<? extends Annotation> cls, Element element) {
        String obj = element.getEnclosingElement().getQualifiedName().toString();
        if (obj.startsWith(ANDROID_PREFIX)) {
            a(element, "@%s-annotated class incorrectly in Android framework package. (%s)", cls.getSimpleName(), obj);
            return true;
        } else if (!obj.startsWith(JAVA_PREFIX)) {
            return false;
        } else {
            a(element, "@%s-annotated class incorrectly in Java framework package. (%s)", cls.getSimpleName(), obj);
            return true;
        }
    }

    private void a(Element element, Map<TypeElement, ViewInjector> map, Set<String> set) {
        boolean z;
        TypeElement enclosingElement = element.getEnclosingElement();
        TypeMirror asType = element.asType();
        if (asType instanceof TypeVariable) {
            asType = ((TypeVariable) asType).getUpperBound();
        }
        boolean z2 = false;
        if (!a(asType, "android.view.View")) {
            a(element, "@InjectView fields must extend from View. (%s.%s)", enclosingElement.getQualifiedName(), element.getSimpleName());
            z = true;
        } else {
            z = false;
        }
        boolean a2 = z | a(InjectView.class, "fields", element) | a(InjectView.class, element);
        if (element.getAnnotation(InjectViews.class) != null) {
            a(element, "Only one of @InjectView and @InjectViews is allowed. (%s.%s)", enclosingElement.getQualifiedName(), element.getSimpleName());
            a2 = true;
        }
        if (!a2) {
            int value = ((InjectView) element.getAnnotation(InjectView.class)).value();
            ViewInjector viewInjector = (ViewInjector) map.get(enclosingElement);
            if (viewInjector != null) {
                ViewInjection a3 = viewInjector.a(value);
                if (a3 != null) {
                    Iterator it = a3.b().iterator();
                    if (it.hasNext()) {
                        a(element, "Attempt to use @InjectView for an already injected ID %d on '%s'. (%s.%s)", Integer.valueOf(value), ((ViewBinding) it.next()).b(), enclosingElement.getQualifiedName(), element.getSimpleName());
                        return;
                    }
                }
            }
            String obj = element.getSimpleName().toString();
            String typeMirror = asType.toString();
            if (element.getAnnotation(Optional.class) == null) {
                z2 = true;
            }
            a(map, enclosingElement).a(value, new ViewBinding(obj, typeMirror, z2));
            set.add(enclosingElement.toString());
        }
    }

    private void b(Element element, Map<TypeElement, ViewInjector> map, Set<String> set) {
        Kind kind;
        boolean z;
        TypeElement enclosingElement = element.getEnclosingElement();
        ArrayType asType = element.asType();
        String a2 = a((TypeMirror) asType);
        TypeMirror typeMirror = null;
        boolean z2 = false;
        if (asType.getKind() == TypeKind.ARRAY) {
            typeMirror = asType.getComponentType();
            kind = Kind.ARRAY;
            z = false;
        } else if (b.equals(a2)) {
            List typeArguments = ((DeclaredType) asType).getTypeArguments();
            if (typeArguments.size() != 1) {
                a(element, "@InjectViews List must have a generic component. (%s.%s)", enclosingElement.getQualifiedName(), element.getSimpleName());
                z = true;
            } else {
                typeMirror = (TypeMirror) typeArguments.get(0);
                z = false;
            }
            kind = Kind.LIST;
        } else {
            a(element, "@InjectViews must be a List or array. (%s.%s)", enclosingElement.getQualifiedName(), element.getSimpleName());
            kind = null;
            z = true;
        }
        if (typeMirror instanceof TypeVariable) {
            typeMirror = ((TypeVariable) typeMirror).getUpperBound();
        }
        if (typeMirror != null && !a(typeMirror, "android.view.View")) {
            a(element, "@InjectViews type must extend from View. (%s.%s)", enclosingElement.getQualifiedName(), element.getSimpleName());
            z = true;
        }
        if (!(z | a(InjectViews.class, "fields", element)) && !a(InjectViews.class, element)) {
            String obj = element.getSimpleName().toString();
            int[] value = ((InjectViews) element.getAnnotation(InjectViews.class)).value();
            if (value.length == 0) {
                a(element, "@InjectViews must specify at least one ID. (%s.%s)", enclosingElement.getQualifiedName(), element.getSimpleName());
                return;
            }
            Integer a3 = a(value);
            if (a3 != null) {
                a(element, "@InjectViews annotation contains duplicate ID %d. (%s.%s)", a3, enclosingElement.getQualifiedName(), element.getSimpleName());
            }
            if (a || typeMirror != null) {
                String typeMirror2 = typeMirror.toString();
                if (element.getAnnotation(Optional.class) == null) {
                    z2 = true;
                }
                a(map, enclosingElement).a(value, new CollectionBinding(obj, typeMirror2, kind, z2));
                set.add(enclosingElement.toString());
                return;
            }
            throw new AssertionError();
        }
    }

    private static Integer a(int[] iArr) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (int i : iArr) {
            if (!linkedHashSet.add(Integer.valueOf(i))) {
                return Integer.valueOf(i);
            }
        }
        return null;
    }

    private String a(TypeMirror typeMirror) {
        String typeMirror2 = this.e.erasure(typeMirror).toString();
        int indexOf = typeMirror2.indexOf(60);
        return indexOf != -1 ? typeMirror2.substring(0, indexOf) : typeMirror2;
    }

    private void a(RoundEnvironment roundEnvironment, Class<? extends Annotation> cls, Map<TypeElement, ViewInjector> map, Set<String> set) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(cls)) {
            try {
                a(cls, element, map, set);
            } catch (Exception e2) {
                StringWriter stringWriter = new StringWriter();
                e2.printStackTrace(new PrintWriter(stringWriter));
                a(element, "Unable to generate view injector for @%s.\n\n%s", cls.getSimpleName(), stringWriter.toString());
            }
        }
    }

    private void a(Class<? extends Annotation> cls, Element element, Map<TypeElement, ViewInjector> map, Set<String> set) {
        ListenerMethod listenerMethod;
        String[] parameters;
        boolean z;
        Class<? extends Annotation> cls2 = cls;
        Element element2 = element;
        int i = 1;
        if (!(element2 instanceof ExecutableElement) || element.getKind() != ElementKind.METHOD) {
            throw new IllegalStateException(String.format("@%s annotation must be on a method.", new Object[]{cls.getSimpleName()}));
        }
        ExecutableElement executableElement = (ExecutableElement) element2;
        TypeElement enclosingElement = element.getEnclosingElement();
        Annotation annotation = element2.getAnnotation(cls2);
        Method declaredMethod = cls2.getDeclaredMethod(TarjetasConstants.VALUE, new Class[0]);
        if (declaredMethod.getReturnType() != int[].class) {
            throw new IllegalStateException(String.format("@%s annotation value() type not int[].", new Object[]{cls2}));
        }
        int[] iArr = (int[]) declaredMethod.invoke(annotation, new Object[0]);
        String obj = executableElement.getSimpleName().toString();
        boolean z2 = element2.getAnnotation(Optional.class) == null;
        boolean a2 = a(cls2, "methods", element2) | a(cls, element);
        Integer a3 = a(iArr);
        int i2 = 2;
        if (a3 != null) {
            a(element2, "@%s annotation for method contains duplicate ID %d. (%s.%s)", cls.getSimpleName(), a3, enclosingElement.getQualifiedName(), element.getSimpleName());
            a2 = true;
        }
        ListenerClass listenerClass = (ListenerClass) cls2.getAnnotation(ListenerClass.class);
        if (listenerClass == null) {
            throw new IllegalStateException(String.format("No @%s defined on @%s.", new Object[]{ListenerClass.class.getSimpleName(), cls.getSimpleName()}));
        }
        int length = iArr.length;
        boolean z3 = a2;
        int i3 = 0;
        while (i3 < length) {
            int i4 = iArr[i3];
            if (i4 == -1) {
                if (iArr.length == i) {
                    if (!z2) {
                        Object[] objArr = new Object[i2];
                        objArr[0] = enclosingElement.getQualifiedName();
                        objArr[i] = element.getSimpleName();
                        a(element2, "ID free injection must not be annotated with @Optional. (%s.%s)", objArr);
                        z3 = true;
                    }
                    String targetType = listenerClass.targetType();
                    if (!a(enclosingElement.asType(), targetType)) {
                        a(element2, "@%s annotation without an ID may only be used with an object of type \"%s\". (%s.%s)", cls.getSimpleName(), targetType, enclosingElement.getQualifiedName(), element.getSimpleName());
                        z = true;
                    } else {
                        z = z3;
                    }
                    z3 = z;
                } else {
                    a(element2, "@%s annotation contains invalid ID %d. (%s.%s)", cls.getSimpleName(), Integer.valueOf(i4), enclosingElement.getQualifiedName(), element.getSimpleName());
                    z3 = true;
                }
            }
            i3++;
            i = 1;
            i2 = 2;
        }
        ListenerMethod[] method = listenerClass.method();
        if (method.length > 1) {
            throw new IllegalStateException(String.format("Multiple listener methods specified on @%s.", new Object[]{cls.getSimpleName()}));
        }
        if (method.length != 1) {
            Enum enumR = (Enum) cls2.getDeclaredMethod("callback", new Class[0]).invoke(annotation, new Object[0]);
            ListenerMethod listenerMethod2 = (ListenerMethod) enumR.getDeclaringClass().getField(enumR.name()).getAnnotation(ListenerMethod.class);
            if (listenerMethod2 == null) {
                throw new IllegalStateException(String.format("No @%s defined on @%s's %s.%s.", new Object[]{ListenerMethod.class.getSimpleName(), cls.getSimpleName(), enumR.getDeclaringClass().getSimpleName(), enumR.name()}));
            }
            listenerMethod = listenerMethod2;
        } else if (listenerClass.callbacks() != NONE.class) {
            throw new IllegalStateException(String.format("Both method() and callback() defined on @%s.", new Object[]{cls.getSimpleName()}));
        } else {
            listenerMethod = method[0];
        }
        List parameters2 = executableElement.getParameters();
        if (parameters2.size() > listenerMethod.parameters().length) {
            a(element2, "@%s methods can have at most %s parameter(s). (%s.%s)", cls.getSimpleName(), Integer.valueOf(listenerMethod.parameters().length), enclosingElement.getQualifiedName(), element.getSimpleName());
            z3 = true;
        }
        TypeMirror returnType = executableElement.getReturnType();
        if (returnType instanceof TypeVariable) {
            returnType = ((TypeVariable) returnType).getUpperBound();
        }
        if (!returnType.toString().equals(listenerMethod.returnType())) {
            a(element2, "@%s methods must have a '%s' return type. (%s.%s)", cls.getSimpleName(), listenerMethod.returnType(), enclosingElement.getQualifiedName(), element.getSimpleName());
            z3 = true;
        }
        if (!z3) {
            Parameter[] parameterArr = Parameter.a;
            if (!parameters2.isEmpty()) {
                parameterArr = new Parameter[parameters2.size()];
                BitSet bitSet = new BitSet(parameters2.size());
                String[] parameters3 = listenerMethod.parameters();
                int i5 = 0;
                while (i5 < parameters2.size()) {
                    TypeMirror asType = ((VariableElement) parameters2.get(i5)).asType();
                    ListenerClass listenerClass2 = listenerClass;
                    if (asType instanceof TypeVariable) {
                        asType = ((TypeVariable) asType).getUpperBound();
                    }
                    int[] iArr2 = iArr;
                    int i6 = 0;
                    while (true) {
                        if (i6 >= parameters3.length) {
                            break;
                        } else if (!bitSet.get(i6) && a(asType, parameters3[i6])) {
                            parameterArr[i5] = new Parameter(i6, asType.toString());
                            bitSet.set(i6);
                            break;
                        } else {
                            i6++;
                        }
                    }
                    if (parameterArr[i5] == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unable to match @");
                        sb.append(cls.getSimpleName());
                        sb.append(" method arguments. (");
                        sb.append(enclosingElement.getQualifiedName());
                        sb.append('.');
                        sb.append(element.getSimpleName());
                        sb.append(')');
                        int i7 = 0;
                        while (i7 < parameterArr.length) {
                            Parameter parameter = parameterArr[i7];
                            sb.append("\n\n  Parameter #");
                            int i8 = i7 + 1;
                            sb.append(i8);
                            sb.append(": ");
                            sb.append(((VariableElement) parameters2.get(i7)).asType().toString());
                            sb.append("\n    ");
                            if (parameter == null) {
                                sb.append("did not match any listener parameters");
                            } else {
                                sb.append("matched listener parameter #");
                                sb.append(parameter.a() + 1);
                                sb.append(": ");
                                sb.append(parameter.b());
                            }
                            i7 = i8;
                        }
                        sb.append("\n\nMethods may have up to ");
                        sb.append(listenerMethod.parameters().length);
                        sb.append(" parameter(s):\n");
                        for (String str : listenerMethod.parameters()) {
                            sb.append("\n  ");
                            sb.append(str);
                        }
                        sb.append("\n\nThese may be listed in any order but will be searched for from top to bottom.");
                        a((Element) executableElement, sb.toString(), new Object[0]);
                        return;
                    }
                    i5++;
                    listenerClass = listenerClass2;
                    iArr = iArr2;
                }
            }
            int[] iArr3 = iArr;
            ListenerClass listenerClass3 = listenerClass;
            ListenerBinding listenerBinding = new ListenerBinding(obj, Arrays.asList(parameterArr), z2);
            ViewInjector a4 = a(map, enclosingElement);
            int[] iArr4 = iArr3;
            int length2 = iArr4.length;
            int i9 = 0;
            while (i9 < length2) {
                int i10 = iArr4[i9];
                ListenerClass listenerClass4 = listenerClass3;
                if (!a4.a(i10, listenerClass4, listenerMethod, listenerBinding)) {
                    a(element2, "Multiple listener methods with return value specified for ID %d. (%s.%s)", Integer.valueOf(i10), enclosingElement.getQualifiedName(), element.getSimpleName());
                    return;
                } else {
                    i9++;
                    listenerClass3 = listenerClass4;
                }
            }
            set.add(enclosingElement.toString());
        }
    }

    private boolean a(TypeMirror typeMirror, String str) {
        if (str.equals(typeMirror.toString())) {
            return true;
        }
        if (!(typeMirror instanceof DeclaredType)) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List typeArguments = declaredType.getTypeArguments();
        if (typeArguments.size() > 0) {
            StringBuilder sb = new StringBuilder(declaredType.asElement().toString());
            sb.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append('?');
            }
            sb.append('>');
            if (sb.toString().equals(str)) {
                return true;
            }
        }
        TypeElement asElement = declaredType.asElement();
        if (!(asElement instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = asElement;
        if (a(typeElement.getSuperclass(), str)) {
            return true;
        }
        for (TypeMirror a2 : typeElement.getInterfaces()) {
            if (a(a2, str)) {
                return true;
            }
        }
        return false;
    }

    private ViewInjector a(Map<TypeElement, ViewInjector> map, TypeElement typeElement) {
        ViewInjector viewInjector = (ViewInjector) map.get(typeElement);
        if (viewInjector != null) {
            return viewInjector;
        }
        String obj = typeElement.getQualifiedName().toString();
        String a2 = a(typeElement);
        StringBuilder sb = new StringBuilder();
        sb.append(a(typeElement, a2));
        sb.append(SUFFIX);
        ViewInjector viewInjector2 = new ViewInjector(a2, sb.toString(), obj);
        map.put(typeElement, viewInjector2);
        return viewInjector2;
    }

    private static String a(TypeElement typeElement, String str) {
        return typeElement.getQualifiedName().toString().substring(str.length() + 1).replace('.', '$');
    }

    private String a(TypeElement typeElement, Set<String> set) {
        do {
            DeclaredType superclass = typeElement.getSuperclass();
            if (superclass.getKind() == TypeKind.NONE) {
                return null;
            }
            typeElement = (TypeElement) superclass.asElement();
        } while (!set.contains(typeElement.toString()));
        String a2 = a(typeElement);
        StringBuilder sb = new StringBuilder();
        sb.append(a2);
        sb.append(".");
        sb.append(a(typeElement, a2));
        return sb.toString();
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void a(Element element, String str, Object... objArr) {
        if (objArr.length > 0) {
            str = String.format(str, objArr);
        }
        this.processingEnv.getMessager().printMessage(Kind.ERROR, str, element);
    }

    private String a(TypeElement typeElement) {
        return this.d.getPackageOf(typeElement).getQualifiedName().toString();
    }
}
