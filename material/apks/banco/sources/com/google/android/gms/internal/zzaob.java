package com.google.android.gms.internal;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzaob {
    final zzaof a;
    final zzaoo b;
    private final ThreadLocal<Map<zzapx<?>, zza<?>>> c;
    private final Map<zzapx<?>, zzaot<?>> d;
    private final List<zzaou> e;
    private final zzapb f;
    private final boolean g;
    private final boolean h;
    private final boolean i;
    private final boolean j;

    static class zza<T> extends zzaot<T> {
        private zzaot<T> a;

        zza() {
        }

        public void a(zzaot<T> zzaot) {
            if (this.a != null) {
                throw new AssertionError();
            }
            this.a = zzaot;
        }

        public void zza(zzaqa zzaqa, T t) {
            if (this.a == null) {
                throw new IllegalStateException();
            }
            this.a.zza(zzaqa, t);
        }

        public T zzb(zzapy zzapy) {
            if (this.a != null) {
                return this.a.zzb(zzapy);
            }
            throw new IllegalStateException();
        }
    }

    public zzaob() {
        this(zzapc.blF, zzanz.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, zzaor.DEFAULT, Collections.emptyList());
    }

    zzaob(zzapc zzapc, zzaoa zzaoa, Map<Type, zzaod<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, zzaor zzaor, List<zzaou> list) {
        this.c = new ThreadLocal<>();
        this.d = Collections.synchronizedMap(new HashMap());
        this.a = new zzaof() {
        };
        this.b = new zzaoo() {
        };
        this.f = new zzapb(map);
        this.g = z;
        this.i = z3;
        this.h = z4;
        this.j = z5;
        ArrayList arrayList = new ArrayList();
        arrayList.add(zzapw.bnI);
        arrayList.add(zzapr.bmp);
        arrayList.add(zzapc);
        arrayList.addAll(list);
        arrayList.add(zzapw.bnp);
        arrayList.add(zzapw.bne);
        arrayList.add(zzapw.bmY);
        arrayList.add(zzapw.bna);
        arrayList.add(zzapw.bnc);
        arrayList.add(zzapw.zza(Long.TYPE, Long.class, a(zzaor)));
        arrayList.add(zzapw.zza(Double.TYPE, Double.class, a(z6)));
        arrayList.add(zzapw.zza(Float.TYPE, Float.class, b(z6)));
        arrayList.add(zzapw.bnj);
        arrayList.add(zzapw.bnl);
        arrayList.add(zzapw.bnr);
        arrayList.add(zzapw.bnt);
        arrayList.add(zzapw.zza(BigDecimal.class, zzapw.bnn));
        arrayList.add(zzapw.zza(BigInteger.class, zzapw.bno));
        arrayList.add(zzapw.bnv);
        arrayList.add(zzapw.bnx);
        arrayList.add(zzapw.bnB);
        arrayList.add(zzapw.bnG);
        arrayList.add(zzapw.bnz);
        arrayList.add(zzapw.bmV);
        arrayList.add(zzapm.bmp);
        arrayList.add(zzapw.bnE);
        arrayList.add(zzapu.bmp);
        arrayList.add(zzapt.bmp);
        arrayList.add(zzapw.bnC);
        arrayList.add(zzapk.bmp);
        arrayList.add(zzapw.bmT);
        arrayList.add(new zzapl(this.f));
        arrayList.add(new zzapq(this.f, z2));
        arrayList.add(new zzapn(this.f));
        arrayList.add(zzapw.bnJ);
        arrayList.add(new zzaps(this.f, zzaoa, zzapc));
        this.e = Collections.unmodifiableList(arrayList);
    }

    private zzaot<Number> a(zzaor zzaor) {
        return zzaor == zzaor.DEFAULT ? zzapw.bnf : new zzaot<Number>() {
            /* renamed from: a */
            public Number zzb(zzapy zzapy) {
                if (zzapy.bn() != zzapz.NULL) {
                    return Long.valueOf(zzapy.nextLong());
                }
                zzapy.nextNull();
                return null;
            }

            /* renamed from: a */
            public void zza(zzaqa zzaqa, Number number) {
                if (number == null) {
                    zzaqa.bx();
                } else {
                    zzaqa.zzut(number.toString());
                }
            }
        };
    }

    private zzaot<Number> a(boolean z) {
        return z ? zzapw.bnh : new zzaot<Number>() {
            /* renamed from: a */
            public Double zzb(zzapy zzapy) {
                if (zzapy.bn() != zzapz.NULL) {
                    return Double.valueOf(zzapy.nextDouble());
                }
                zzapy.nextNull();
                return null;
            }

            /* renamed from: a */
            public void zza(zzaqa zzaqa, Number number) {
                if (number == null) {
                    zzaqa.bx();
                    return;
                }
                zzaob.this.a(number.doubleValue());
                zzaqa.zza(number);
            }
        };
    }

    /* access modifiers changed from: private */
    public void a(double d2) {
        if (Double.isNaN(d2) || Double.isInfinite(d2)) {
            StringBuilder sb = new StringBuilder(168);
            sb.append(d2);
            sb.append(" is not a valid double value as per JSON specification. To override this");
            sb.append(" behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static void a(Object obj, zzapy zzapy) {
        if (obj != null) {
            try {
                if (zzapy.bn() != zzapz.END_DOCUMENT) {
                    throw new zzaoi("JSON document was not fully consumed.");
                }
            } catch (zzaqb e2) {
                throw new zzaoq((Throwable) e2);
            } catch (IOException e3) {
                throw new zzaoi((Throwable) e3);
            }
        }
    }

    private zzaot<Number> b(boolean z) {
        return z ? zzapw.bng : new zzaot<Number>() {
            /* renamed from: a */
            public Float zzb(zzapy zzapy) {
                if (zzapy.bn() != zzapz.NULL) {
                    return Float.valueOf((float) zzapy.nextDouble());
                }
                zzapy.nextNull();
                return null;
            }

            /* renamed from: a */
            public void zza(zzaqa zzaqa, Number number) {
                if (number == null) {
                    zzaqa.bx();
                    return;
                }
                zzaob.this.a((double) number.floatValue());
                zzaqa.zza(number);
            }
        };
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{serializeNulls:");
        sb.append(this.g);
        sb.append("factories:");
        sb.append(this.e);
        sb.append(",instanceCreators:");
        sb.append(this.f);
        sb.append("}");
        return sb.toString();
    }

    public <T> zzaot<T> zza(zzaou zzaou, zzapx<T> zzapx) {
        boolean z = !this.e.contains(zzaou);
        for (zzaou zzaou2 : this.e) {
            if (z) {
                zzaot<T> zza2 = zzaou2.zza(this, zzapx);
                if (zza2 != null) {
                    return zza2;
                }
            } else if (zzaou2 == zzaou) {
                z = true;
            }
        }
        String valueOf = String.valueOf(zzapx);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
        sb.append("GSON cannot serialize ");
        sb.append(valueOf);
        throw new IllegalArgumentException(sb.toString());
    }

    public <T> zzaot<T> zza(zzapx<T> zzapx) {
        zzaot<T> zzaot = (zzaot) this.d.get(zzapx);
        if (zzaot != null) {
            return zzaot;
        }
        Map map = (Map) this.c.get();
        boolean z = false;
        if (map == null) {
            map = new HashMap();
            this.c.set(map);
            z = true;
        }
        zza zza2 = (zza) map.get(zzapx);
        if (zza2 != null) {
            return zza2;
        }
        try {
            zza zza3 = new zza();
            map.put(zzapx, zza3);
            for (zzaou zza4 : this.e) {
                zzaot<T> zza5 = zza4.zza(this, zzapx);
                if (zza5 != null) {
                    zza3.a(zza5);
                    this.d.put(zzapx, zza5);
                    return zza5;
                }
            }
            String valueOf = String.valueOf(zzapx);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19);
            sb.append("GSON cannot handle ");
            sb.append(valueOf);
            throw new IllegalArgumentException(sb.toString());
        } finally {
            map.remove(zzapx);
            if (z) {
                this.c.remove();
            }
        }
    }

    public zzaqa zza(Writer writer) {
        if (this.i) {
            writer.write(")]}'\n");
        }
        zzaqa zzaqa = new zzaqa(writer);
        if (this.j) {
            zzaqa.setIndent("  ");
        }
        zzaqa.zzdi(this.g);
        return zzaqa;
    }

    public <T> T zza(zzaoh zzaoh, Class<T> cls) {
        return zzaph.zzp(cls).cast(zza(zzaoh, (Type) cls));
    }

    public <T> T zza(zzaoh zzaoh, Type type) {
        if (zzaoh == null) {
            return null;
        }
        return zza((zzapy) new zzapo(zzaoh), type);
    }

    public <T> T zza(zzapy zzapy, Type type) {
        boolean isLenient = zzapy.isLenient();
        zzapy.setLenient(true);
        try {
            zzapy.bn();
            T zzb = zza(zzapx.zzl(type)).zzb(zzapy);
            zzapy.setLenient(isLenient);
            return zzb;
        } catch (EOFException e2) {
            if (1 != 0) {
                zzapy.setLenient(isLenient);
                return null;
            }
            throw new zzaoq((Throwable) e2);
        } catch (IllegalStateException e3) {
            throw new zzaoq((Throwable) e3);
        } catch (IOException e4) {
            throw new zzaoq((Throwable) e4);
        } catch (Throwable th) {
            zzapy.setLenient(isLenient);
            throw th;
        }
    }

    public <T> T zza(Reader reader, Type type) {
        zzapy zzapy = new zzapy(reader);
        T zza2 = zza(zzapy, type);
        a((Object) zza2, zzapy);
        return zza2;
    }

    public <T> T zza(String str, Type type) {
        if (str == null) {
            return null;
        }
        return zza((Reader) new StringReader(str), type);
    }

    public void zza(zzaoh zzaoh, zzaqa zzaqa) {
        boolean isLenient = zzaqa.isLenient();
        zzaqa.setLenient(true);
        boolean bJ = zzaqa.bJ();
        zzaqa.zzdh(this.h);
        boolean bK = zzaqa.bK();
        zzaqa.zzdi(this.g);
        try {
            zzapi.zzb(zzaoh, zzaqa);
            zzaqa.setLenient(isLenient);
            zzaqa.zzdh(bJ);
            zzaqa.zzdi(bK);
        } catch (IOException e2) {
            throw new zzaoi((Throwable) e2);
        } catch (Throwable th) {
            zzaqa.setLenient(isLenient);
            zzaqa.zzdh(bJ);
            zzaqa.zzdi(bK);
            throw th;
        }
    }

    public void zza(zzaoh zzaoh, Appendable appendable) {
        try {
            zza(zzaoh, zza(zzapi.zza(appendable)));
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void zza(Object obj, Type type, zzaqa zzaqa) {
        zzaot zza2 = zza(zzapx.zzl(type));
        boolean isLenient = zzaqa.isLenient();
        zzaqa.setLenient(true);
        boolean bJ = zzaqa.bJ();
        zzaqa.zzdh(this.h);
        boolean bK = zzaqa.bK();
        zzaqa.zzdi(this.g);
        try {
            zza2.zza(zzaqa, obj);
            zzaqa.setLenient(isLenient);
            zzaqa.zzdh(bJ);
            zzaqa.zzdi(bK);
        } catch (IOException e2) {
            throw new zzaoi((Throwable) e2);
        } catch (Throwable th) {
            zzaqa.setLenient(isLenient);
            zzaqa.zzdh(bJ);
            zzaqa.zzdi(bK);
            throw th;
        }
    }

    public void zza(Object obj, Type type, Appendable appendable) {
        try {
            zza(obj, type, zza(zzapi.zza(appendable)));
        } catch (IOException e2) {
            throw new zzaoi((Throwable) e2);
        }
    }

    public String zzb(zzaoh zzaoh) {
        StringWriter stringWriter = new StringWriter();
        zza(zzaoh, (Appendable) stringWriter);
        return stringWriter.toString();
    }

    public String zzc(Object obj, Type type) {
        StringWriter stringWriter = new StringWriter();
        zza(obj, type, (Appendable) stringWriter);
        return stringWriter.toString();
    }

    public String zzcl(Object obj) {
        return obj == null ? zzb(zzaoj.bld) : zzc(obj, obj.getClass());
    }

    public <T> T zzf(String str, Class<T> cls) {
        return zzaph.zzp(cls).cast(zza(str, (Type) cls));
    }

    public <T> zzaot<T> zzk(Class<T> cls) {
        return zza(zzapx.zzr(cls));
    }
}
