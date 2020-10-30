package com.google.common.reflect;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.Attributes.Name;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@Beta
public final class ClassPath {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger(ClassPath.class.getName());
    private static final Predicate<ClassInfo> b = new Predicate<ClassInfo>() {
        /* renamed from: a */
        public boolean apply(ClassInfo classInfo) {
            return classInfo.b.indexOf(36) == -1;
        }
    };
    /* access modifiers changed from: private */
    public static final Splitter c = Splitter.on(UtilsCuentas.SEPARAOR2).omitEmptyStrings();
    private final ImmutableSet<ResourceInfo> d;

    static abstract class Scanner {
        private final Set<File> a = Sets.newHashSet();

        /* access modifiers changed from: protected */
        public abstract void a(ClassLoader classLoader, File file);

        /* access modifiers changed from: protected */
        public abstract void a(ClassLoader classLoader, JarFile jarFile);

        Scanner() {
        }

        public final void a(ClassLoader classLoader) {
            Iterator it = b(classLoader).entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                a((File) entry.getKey(), (ClassLoader) entry.getValue());
            }
        }

        /* access modifiers changed from: 0000 */
        @VisibleForTesting
        public final void a(File file, ClassLoader classLoader) {
            if (this.a.add(file.getCanonicalFile())) {
                b(file, classLoader);
            }
        }

        private void b(File file, ClassLoader classLoader) {
            try {
                if (file.exists()) {
                    if (file.isDirectory()) {
                        a(classLoader, file);
                    } else {
                        c(file, classLoader);
                    }
                }
            } catch (SecurityException e) {
                Logger a2 = ClassPath.a;
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot access ");
                sb.append(file);
                sb.append(": ");
                sb.append(e);
                a2.warning(sb.toString());
            }
        }

        private void c(File file, ClassLoader classLoader) {
            try {
                JarFile jarFile = new JarFile(file);
                try {
                    Iterator it = a(file, jarFile.getManifest()).iterator();
                    while (it.hasNext()) {
                        a((File) it.next(), classLoader);
                    }
                    a(classLoader, jarFile);
                } finally {
                    try {
                        jarFile.close();
                    } catch (IOException unused) {
                    }
                }
            } catch (IOException unused2) {
            }
        }

        @VisibleForTesting
        static ImmutableSet<File> a(File file, @Nullable Manifest manifest) {
            if (manifest == null) {
                return ImmutableSet.of();
            }
            Builder builder = ImmutableSet.builder();
            String value = manifest.getMainAttributes().getValue(Name.CLASS_PATH.toString());
            if (value != null) {
                for (String str : ClassPath.c.split(value)) {
                    try {
                        URL a2 = a(file, str);
                        if (a2.getProtocol().equals("file")) {
                            builder.add((Object) new File(a2.getFile()));
                        }
                    } catch (MalformedURLException unused) {
                        Logger a3 = ClassPath.a;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Invalid Class-Path entry: ");
                        sb.append(str);
                        a3.warning(sb.toString());
                    }
                }
            }
            return builder.build();
        }

        @VisibleForTesting
        static ImmutableMap<File, ClassLoader> b(ClassLoader classLoader) {
            URL[] uRLs;
            LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
            ClassLoader parent = classLoader.getParent();
            if (parent != null) {
                newLinkedHashMap.putAll(b(parent));
            }
            if (classLoader instanceof URLClassLoader) {
                for (URL url : ((URLClassLoader) classLoader).getURLs()) {
                    if (url.getProtocol().equals("file")) {
                        File file = new File(url.getFile());
                        if (!newLinkedHashMap.containsKey(file)) {
                            newLinkedHashMap.put(file, classLoader);
                        }
                    }
                }
            }
            return ImmutableMap.copyOf((Map<? extends K, ? extends V>) newLinkedHashMap);
        }

        @VisibleForTesting
        static URL a(File file, String str) {
            return new URL(file.toURI().toURL(), str);
        }
    }

    @Beta
    public static final class ClassInfo extends ResourceInfo {
        /* access modifiers changed from: private */
        public final String b;

        ClassInfo(String str, ClassLoader classLoader) {
            super(str, classLoader);
            this.b = ClassPath.a(str);
        }

        public String getPackageName() {
            return Reflection.getPackageName(this.b);
        }

        public String getSimpleName() {
            int lastIndexOf = this.b.lastIndexOf(36);
            if (lastIndexOf != -1) {
                return CharMatcher.digit().trimLeadingFrom(this.b.substring(lastIndexOf + 1));
            }
            String packageName = getPackageName();
            if (packageName.isEmpty()) {
                return this.b;
            }
            return this.b.substring(packageName.length() + 1);
        }

        public String getName() {
            return this.b;
        }

        public Class<?> load() {
            try {
                return this.a.loadClass(this.b);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }

        public String toString() {
            return this.b;
        }
    }

    @VisibleForTesting
    static final class DefaultScanner extends Scanner {
        private final SetMultimap<ClassLoader, String> a = MultimapBuilder.hashKeys().linkedHashSetValues().build();

        DefaultScanner() {
        }

        /* access modifiers changed from: 0000 */
        public ImmutableSet<ResourceInfo> a() {
            Builder builder = ImmutableSet.builder();
            for (Entry entry : this.a.entries()) {
                builder.add((Object) ResourceInfo.a((String) entry.getValue(), (ClassLoader) entry.getKey()));
            }
            return builder.build();
        }

        /* access modifiers changed from: protected */
        public void a(ClassLoader classLoader, JarFile jarFile) {
            Enumeration entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) entries.nextElement();
                if (!jarEntry.isDirectory() && !jarEntry.getName().equals("META-INF/MANIFEST.MF")) {
                    this.a.get(classLoader).add(jarEntry.getName());
                }
            }
        }

        /* access modifiers changed from: protected */
        public void a(ClassLoader classLoader, File file) {
            a(file, classLoader, "");
        }

        private void a(File file, ClassLoader classLoader, String str) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                Logger a2 = ClassPath.a;
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot read directory ");
                sb.append(file);
                a2.warning(sb.toString());
                return;
            }
            for (File file2 : listFiles) {
                String name = file2.getName();
                if (file2.isDirectory()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(name);
                    sb2.append("/");
                    a(file2, classLoader, sb2.toString());
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append(name);
                    String sb4 = sb3.toString();
                    if (!sb4.equals("META-INF/MANIFEST.MF")) {
                        this.a.get(classLoader).add(sb4);
                    }
                }
            }
        }
    }

    @Beta
    public static class ResourceInfo {
        final ClassLoader a;
        private final String b;

        static ResourceInfo a(String str, ClassLoader classLoader) {
            if (str.endsWith(".class")) {
                return new ClassInfo(str, classLoader);
            }
            return new ResourceInfo(str, classLoader);
        }

        ResourceInfo(String str, ClassLoader classLoader) {
            this.b = (String) Preconditions.checkNotNull(str);
            this.a = (ClassLoader) Preconditions.checkNotNull(classLoader);
        }

        public final URL url() {
            URL resource = this.a.getResource(this.b);
            if (resource != null) {
                return resource;
            }
            throw new NoSuchElementException(this.b);
        }

        public final ByteSource asByteSource() {
            return Resources.asByteSource(url());
        }

        public final CharSource asCharSource(Charset charset) {
            return Resources.asCharSource(url(), charset);
        }

        public final String getResourceName() {
            return this.b;
        }

        public int hashCode() {
            return this.b.hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof ResourceInfo)) {
                return false;
            }
            ResourceInfo resourceInfo = (ResourceInfo) obj;
            if (this.b.equals(resourceInfo.b) && this.a == resourceInfo.a) {
                z = true;
            }
            return z;
        }

        public String toString() {
            return this.b;
        }
    }

    private ClassPath(ImmutableSet<ResourceInfo> immutableSet) {
        this.d = immutableSet;
    }

    public static ClassPath from(ClassLoader classLoader) {
        DefaultScanner defaultScanner = new DefaultScanner();
        defaultScanner.a(classLoader);
        return new ClassPath(defaultScanner.a());
    }

    public ImmutableSet<ResourceInfo> getResources() {
        return this.d;
    }

    public ImmutableSet<ClassInfo> getAllClasses() {
        return FluentIterable.from((Iterable<E>) this.d).filter(ClassInfo.class).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses() {
        return FluentIterable.from((Iterable<E>) this.d).filter(ClassInfo.class).filter(b).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses(String str) {
        Preconditions.checkNotNull(str);
        Builder builder = ImmutableSet.builder();
        Iterator it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            ClassInfo classInfo = (ClassInfo) it.next();
            if (classInfo.getPackageName().equals(str)) {
                builder.add((Object) classInfo);
            }
        }
        return builder.build();
    }

    public ImmutableSet<ClassInfo> getTopLevelClassesRecursive(String str) {
        Preconditions.checkNotNull(str);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append('.');
        String sb2 = sb.toString();
        Builder builder = ImmutableSet.builder();
        Iterator it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            ClassInfo classInfo = (ClassInfo) it.next();
            if (classInfo.getName().startsWith(sb2)) {
                builder.add((Object) classInfo);
            }
        }
        return builder.build();
    }

    @VisibleForTesting
    static String a(String str) {
        return str.substring(0, str.length() - ".class".length()).replace('/', '.');
    }
}
