package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@GwtIncompatible
public class FinalizableReferenceQueue implements Closeable {
    /* access modifiers changed from: private */
    public static final Logger d = Logger.getLogger(FinalizableReferenceQueue.class.getName());
    private static final Method e = a(a(new SystemLoader(), new DecoupledLoader(), new DirectLoader()));
    final ReferenceQueue<Object> a = new ReferenceQueue<>();
    final PhantomReference<Object> b = new PhantomReference<>(this, this.a);
    final boolean c;

    static class DecoupledLoader implements FinalizerLoader {
        DecoupledLoader() {
        }

        @Nullable
        public Class<?> a() {
            try {
                return a(b()).loadClass("com.google.common.base.internal.Finalizer");
            } catch (Exception e) {
                FinalizableReferenceQueue.d.log(Level.WARNING, "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.", e);
                return null;
            }
        }

        /* access modifiers changed from: 0000 */
        public URL b() {
            StringBuilder sb = new StringBuilder();
            sb.append("com.google.common.base.internal.Finalizer".replace('.', '/'));
            sb.append(".class");
            String sb2 = sb.toString();
            URL resource = getClass().getClassLoader().getResource(sb2);
            if (resource == null) {
                throw new FileNotFoundException(sb2);
            }
            String url = resource.toString();
            if (url.endsWith(sb2)) {
                return new URL(resource, url.substring(0, url.length() - sb2.length()));
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unsupported path style: ");
            sb3.append(url);
            throw new IOException(sb3.toString());
        }

        /* access modifiers changed from: 0000 */
        public URLClassLoader a(URL url) {
            return new URLClassLoader(new URL[]{url}, null);
        }
    }

    interface FinalizerLoader {
        @Nullable
        Class<?> a();
    }

    static class SystemLoader implements FinalizerLoader {
        @VisibleForTesting
        static boolean a;

        SystemLoader() {
        }

        @Nullable
        public Class<?> a() {
            if (a) {
                return null;
            }
            try {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                if (systemClassLoader == null) {
                    return null;
                }
                try {
                    return systemClassLoader.loadClass("com.google.common.base.internal.Finalizer");
                } catch (ClassNotFoundException unused) {
                    return null;
                }
            } catch (SecurityException unused2) {
                FinalizableReferenceQueue.d.info("Not allowed to access system class loader.");
                return null;
            }
        }
    }

    static class DirectLoader implements FinalizerLoader {
        DirectLoader() {
        }

        public Class<?> a() {
            try {
                return Class.forName("com.google.common.base.internal.Finalizer");
            } catch (ClassNotFoundException e) {
                throw new AssertionError(e);
            }
        }
    }

    public FinalizableReferenceQueue() {
        boolean z = true;
        try {
            e.invoke(null, new Object[]{FinalizableReference.class, this.a, this.b});
        } catch (IllegalAccessException e2) {
            throw new AssertionError(e2);
        } catch (Throwable th) {
            d.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", th);
            z = false;
        }
        this.c = z;
    }

    public void close() {
        this.b.enqueue();
        a();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (!this.c) {
            while (true) {
                Reference poll = this.a.poll();
                if (poll != null) {
                    poll.clear();
                    try {
                        ((FinalizableReference) poll).finalizeReferent();
                    } catch (Throwable th) {
                        d.log(Level.SEVERE, "Error cleaning up after reference.", th);
                    }
                } else {
                    return;
                }
            }
        }
    }

    private static Class<?> a(FinalizerLoader... finalizerLoaderArr) {
        for (FinalizerLoader a2 : finalizerLoaderArr) {
            Class<?> a3 = a2.a();
            if (a3 != null) {
                return a3;
            }
        }
        throw new AssertionError();
    }

    static Method a(Class<?> cls) {
        try {
            return cls.getMethod("startFinalizer", new Class[]{Class.class, ReferenceQueue.class, PhantomReference.class});
        } catch (NoSuchMethodException e2) {
            throw new AssertionError(e2);
        }
    }
}
