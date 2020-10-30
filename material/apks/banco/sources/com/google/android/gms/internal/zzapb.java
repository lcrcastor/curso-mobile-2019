package com.google.android.gms.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public final class zzapb {
    private final Map<Type, zzaod<?>> a;

    public zzapb(Map<Type, zzaod<?>> map) {
        this.a = map;
    }

    private <T> zzapg<T> a(Class<? super T> cls) {
        try {
            final Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return new zzapg<T>() {
                public T bg() {
                    try {
                        return declaredConstructor.newInstance(null);
                    } catch (InstantiationException e) {
                        String valueOf = String.valueOf(declaredConstructor);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 30);
                        sb.append("Failed to invoke ");
                        sb.append(valueOf);
                        sb.append(" with no args");
                        throw new RuntimeException(sb.toString(), e);
                    } catch (InvocationTargetException e2) {
                        String valueOf2 = String.valueOf(declaredConstructor);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 30);
                        sb2.append("Failed to invoke ");
                        sb2.append(valueOf2);
                        sb2.append(" with no args");
                        throw new RuntimeException(sb2.toString(), e2.getTargetException());
                    } catch (IllegalAccessException e3) {
                        throw new AssertionError(e3);
                    }
                }
            };
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    private <T> zzapg<T> a(final Type type, Class<? super T> cls) {
        if (Collection.class.isAssignableFrom(cls)) {
            return SortedSet.class.isAssignableFrom(cls) ? new zzapg<T>() {
                public T bg() {
                    return new TreeSet();
                }
            } : EnumSet.class.isAssignableFrom(cls) ? new zzapg<T>() {
                public T bg() {
                    if (type instanceof ParameterizedType) {
                        Type type = ((ParameterizedType) type).getActualTypeArguments()[0];
                        if (type instanceof Class) {
                            return EnumSet.noneOf((Class) type);
                        }
                        String str = "Invalid EnumSet type: ";
                        String valueOf = String.valueOf(type.toString());
                        throw new zzaoi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    }
                    String str2 = "Invalid EnumSet type: ";
                    String valueOf2 = String.valueOf(type.toString());
                    throw new zzaoi(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                }
            } : Set.class.isAssignableFrom(cls) ? new zzapg<T>() {
                public T bg() {
                    return new LinkedHashSet();
                }
            } : Queue.class.isAssignableFrom(cls) ? new zzapg<T>() {
                public T bg() {
                    return new LinkedList();
                }
            } : new zzapg<T>() {
                public T bg() {
                    return new ArrayList();
                }
            };
        }
        if (Map.class.isAssignableFrom(cls)) {
            return SortedMap.class.isAssignableFrom(cls) ? new zzapg<T>() {
                public T bg() {
                    return new TreeMap();
                }
            } : (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(zzapx.zzl(((ParameterizedType) type).getActualTypeArguments()[0]).by())) ? new zzapg<T>() {
                public T bg() {
                    return new zzapf();
                }
            } : new zzapg<T>() {
                public T bg() {
                    return new LinkedHashMap();
                }
            };
        }
        return null;
    }

    private <T> zzapg<T> b(final Type type, final Class<? super T> cls) {
        return new zzapg<T>() {
            private final zzapj d = zzapj.bl();

            public T bg() {
                try {
                    return this.d.zzf(cls);
                } catch (Exception e) {
                    String valueOf = String.valueOf(type);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 116);
                    sb.append("Unable to invoke no-args constructor for ");
                    sb.append(valueOf);
                    sb.append(". ");
                    sb.append("Register an InstanceCreator with Gson for this type may fix this problem.");
                    throw new RuntimeException(sb.toString(), e);
                }
            }
        };
    }

    public String toString() {
        return this.a.toString();
    }

    public <T> zzapg<T> zzb(zzapx<T> zzapx) {
        final Type bz = zzapx.bz();
        Class by = zzapx.by();
        final zzaod zzaod = (zzaod) this.a.get(bz);
        if (zzaod != null) {
            return new zzapg<T>() {
                public T bg() {
                    return zzaod.zza(bz);
                }
            };
        }
        final zzaod zzaod2 = (zzaod) this.a.get(by);
        if (zzaod2 != null) {
            return new zzapg<T>() {
                public T bg() {
                    return zzaod2.zza(bz);
                }
            };
        }
        zzapg<T> a2 = a(by);
        if (a2 != null) {
            return a2;
        }
        zzapg<T> a3 = a(bz, by);
        return a3 != null ? a3 : b(bz, by);
    }
}
