package com.google.android.gms.internal;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;

public final class zzapw {
    public static final zzaot<Class> bmS = new zzaot<Class>() {
        /* renamed from: a */
        public Class zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Class cls) {
            if (cls == null) {
                zzaqa.bx();
                return;
            }
            String valueOf = String.valueOf(cls.getName());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 76);
            sb.append("Attempted to serialize java.lang.Class: ");
            sb.append(valueOf);
            sb.append(". Forgot to register a type adapter?");
            throw new UnsupportedOperationException(sb.toString());
        }
    };
    public static final zzaou bmT = zza(Class.class, bmS);
    public static final zzaot<BitSet> bmU = new zzaot<BitSet>() {
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0058, code lost:
            if (java.lang.Integer.parseInt(r1) != 0) goto L_0x0085;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x005b, code lost:
            r5 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0083, code lost:
            if (r7.nextInt() != 0) goto L_0x0085;
         */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.BitSet zzb(com.google.android.gms.internal.zzapy r7) {
            /*
                r6 = this;
                com.google.android.gms.internal.zzapz r0 = r7.bn()
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.NULL
                if (r0 != r1) goto L_0x000d
                r7.nextNull()
                r7 = 0
                return r7
            L_0x000d:
                java.util.BitSet r0 = new java.util.BitSet
                r0.<init>()
                r7.beginArray()
                com.google.android.gms.internal.zzapz r1 = r7.bn()
                r2 = 0
                r3 = 0
            L_0x001b:
                com.google.android.gms.internal.zzapz r4 = com.google.android.gms.internal.zzapz.END_ARRAY
                if (r1 == r4) goto L_0x0091
                int[] r4 = com.google.android.gms.internal.zzapw.AnonymousClass26.a
                int r5 = r1.ordinal()
                r4 = r4[r5]
                r5 = 1
                switch(r4) {
                    case 1: goto L_0x007f;
                    case 2: goto L_0x007a;
                    case 3: goto L_0x0050;
                    default: goto L_0x002b;
                }
            L_0x002b:
                com.google.android.gms.internal.zzaoq r7 = new com.google.android.gms.internal.zzaoq
                java.lang.String r0 = java.lang.String.valueOf(r1)
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = java.lang.String.valueOf(r0)
                int r2 = r2.length()
                int r2 = r2 + 27
                r1.<init>(r2)
                java.lang.String r2 = "Invalid bitset value type: "
                r1.append(r2)
                r1.append(r0)
                java.lang.String r0 = r1.toString()
                r7.<init>(r0)
                throw r7
            L_0x0050:
                java.lang.String r1 = r7.nextString()
                int r4 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x005d }
                if (r4 == 0) goto L_0x005b
                goto L_0x0085
            L_0x005b:
                r5 = 0
                goto L_0x0085
            L_0x005d:
                com.google.android.gms.internal.zzaoq r7 = new com.google.android.gms.internal.zzaoq
                java.lang.String r0 = "Error: Expecting: bitset number value (1, 0), Found: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                int r2 = r1.length()
                if (r2 == 0) goto L_0x0070
                java.lang.String r0 = r0.concat(r1)
                goto L_0x0076
            L_0x0070:
                java.lang.String r1 = new java.lang.String
                r1.<init>(r0)
                r0 = r1
            L_0x0076:
                r7.<init>(r0)
                throw r7
            L_0x007a:
                boolean r5 = r7.nextBoolean()
                goto L_0x0085
            L_0x007f:
                int r1 = r7.nextInt()
                if (r1 == 0) goto L_0x005b
            L_0x0085:
                if (r5 == 0) goto L_0x008a
                r0.set(r3)
            L_0x008a:
                int r3 = r3 + 1
                com.google.android.gms.internal.zzapz r1 = r7.bn()
                goto L_0x001b
            L_0x0091:
                r7.endArray()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzapw.AnonymousClass12.zzb(com.google.android.gms.internal.zzapy):java.util.BitSet");
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, BitSet bitSet) {
            if (bitSet == null) {
                zzaqa.bx();
                return;
            }
            zzaqa.bt();
            for (int i = 0; i < bitSet.length(); i++) {
                zzaqa.zzcu(bitSet.get(i) ? 1 : 0);
            }
            zzaqa.bu();
        }
    };
    public static final zzaou bmV = zza(BitSet.class, bmU);
    public static final zzaot<Boolean> bmW = new zzaot<Boolean>() {
        /* renamed from: a */
        public Boolean zzb(zzapy zzapy) {
            if (zzapy.bn() != zzapz.NULL) {
                return zzapy.bn() == zzapz.STRING ? Boolean.valueOf(Boolean.parseBoolean(zzapy.nextString())) : Boolean.valueOf(zzapy.nextBoolean());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Boolean bool) {
            if (bool == null) {
                zzaqa.bx();
            } else {
                zzaqa.zzdf(bool.booleanValue());
            }
        }
    };
    public static final zzaot<Boolean> bmX = new zzaot<Boolean>() {
        /* renamed from: a */
        public Boolean zzb(zzapy zzapy) {
            if (zzapy.bn() != zzapz.NULL) {
                return Boolean.valueOf(zzapy.nextString());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Boolean bool) {
            zzaqa.zzut(bool == null ? "null" : bool.toString());
        }
    };
    public static final zzaou bmY = zza(Boolean.TYPE, Boolean.class, bmW);
    public static final zzaot<Number> bmZ = new zzaot<Number>() {
        /* renamed from: a */
        public Number zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            try {
                return Byte.valueOf((byte) zzapy.nextInt());
            } catch (NumberFormatException e) {
                throw new zzaoq((Throwable) e);
            }
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Number number) {
            zzaqa.zza(number);
        }
    };
    public static final zzaot<UUID> bnA = new zzaot<UUID>() {
        /* renamed from: a */
        public UUID zzb(zzapy zzapy) {
            if (zzapy.bn() != zzapz.NULL) {
                return UUID.fromString(zzapy.nextString());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, UUID uuid) {
            zzaqa.zzut(uuid == null ? null : uuid.toString());
        }
    };
    public static final zzaou bnB = zza(UUID.class, bnA);
    public static final zzaou bnC = new zzaou() {
        public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
            if (zzapx.by() != Timestamp.class) {
                return null;
            }
            final zzaot zzk = zzaob.zzk(Date.class);
            return new zzaot<Timestamp>() {
                /* renamed from: a */
                public Timestamp zzb(zzapy zzapy) {
                    Date date = (Date) zzk.zzb(zzapy);
                    if (date != null) {
                        return new Timestamp(date.getTime());
                    }
                    return null;
                }

                /* renamed from: a */
                public void zza(zzaqa zzaqa, Timestamp timestamp) {
                    zzk.zza(zzaqa, timestamp);
                }
            };
        }
    };
    public static final zzaot<Calendar> bnD = new zzaot<Calendar>() {
        /* renamed from: a */
        public Calendar zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            zzapy.beginObject();
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (zzapy.bn() != zzapz.END_OBJECT) {
                String nextName = zzapy.nextName();
                int nextInt = zzapy.nextInt();
                if ("year".equals(nextName)) {
                    i = nextInt;
                } else if ("month".equals(nextName)) {
                    i2 = nextInt;
                } else if ("dayOfMonth".equals(nextName)) {
                    i3 = nextInt;
                } else if ("hourOfDay".equals(nextName)) {
                    i4 = nextInt;
                } else if ("minute".equals(nextName)) {
                    i5 = nextInt;
                } else if ("second".equals(nextName)) {
                    i6 = nextInt;
                }
            }
            zzapy.endObject();
            GregorianCalendar gregorianCalendar = new GregorianCalendar(i, i2, i3, i4, i5, i6);
            return gregorianCalendar;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Calendar calendar) {
            if (calendar == null) {
                zzaqa.bx();
                return;
            }
            zzaqa.bv();
            zzaqa.zzus("year");
            zzaqa.zzcu((long) calendar.get(1));
            zzaqa.zzus("month");
            zzaqa.zzcu((long) calendar.get(2));
            zzaqa.zzus("dayOfMonth");
            zzaqa.zzcu((long) calendar.get(5));
            zzaqa.zzus("hourOfDay");
            zzaqa.zzcu((long) calendar.get(11));
            zzaqa.zzus("minute");
            zzaqa.zzcu((long) calendar.get(12));
            zzaqa.zzus("second");
            zzaqa.zzcu((long) calendar.get(13));
            zzaqa.bw();
        }
    };
    public static final zzaou bnE = zzb(Calendar.class, GregorianCalendar.class, bnD);
    public static final zzaot<Locale> bnF = new zzaot<Locale>() {
        /* renamed from: a */
        public Locale zzb(zzapy zzapy) {
            String str = null;
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(zzapy.nextString(), EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            if (stringTokenizer.hasMoreElements()) {
                str = stringTokenizer.nextToken();
            }
            return (nextToken2 == null && str == null) ? new Locale(nextToken) : str == null ? new Locale(nextToken, nextToken2) : new Locale(nextToken, nextToken2, str);
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Locale locale) {
            zzaqa.zzut(locale == null ? null : locale.toString());
        }
    };
    public static final zzaou bnG = zza(Locale.class, bnF);
    public static final zzaot<zzaoh> bnH = new zzaot<zzaoh>() {
        /* renamed from: a */
        public zzaoh zzb(zzapy zzapy) {
            switch (AnonymousClass26.a[zzapy.bn().ordinal()]) {
                case 1:
                    return new zzaon((Number) new zzape(zzapy.nextString()));
                case 2:
                    return new zzaon(Boolean.valueOf(zzapy.nextBoolean()));
                case 3:
                    return new zzaon(zzapy.nextString());
                case 4:
                    zzapy.nextNull();
                    return zzaoj.bld;
                case 5:
                    zzaoe zzaoe = new zzaoe();
                    zzapy.beginArray();
                    while (zzapy.hasNext()) {
                        zzaoe.zzc((zzaoh) zzb(zzapy));
                    }
                    zzapy.endArray();
                    return zzaoe;
                case 6:
                    zzaok zzaok = new zzaok();
                    zzapy.beginObject();
                    while (zzapy.hasNext()) {
                        zzaok.zza(zzapy.nextName(), (zzaoh) zzb(zzapy));
                    }
                    zzapy.endObject();
                    return zzaok;
                default:
                    throw new IllegalArgumentException();
            }
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, zzaoh zzaoh) {
            if (zzaoh == null || zzaoh.aV()) {
                zzaqa.bx();
            } else if (zzaoh.aU()) {
                zzaon aY = zzaoh.aY();
                if (aY.bb()) {
                    zzaqa.zza(aY.aQ());
                } else if (aY.ba()) {
                    zzaqa.zzdf(aY.getAsBoolean());
                } else {
                    zzaqa.zzut(aY.aR());
                }
            } else if (zzaoh.aS()) {
                zzaqa.bt();
                Iterator it = zzaoh.aX().iterator();
                while (it.hasNext()) {
                    zza(zzaqa, (zzaoh) it.next());
                }
                zzaqa.bu();
            } else if (zzaoh.aT()) {
                zzaqa.bv();
                for (Entry entry : zzaoh.aW().entrySet()) {
                    zzaqa.zzus((String) entry.getKey());
                    zza(zzaqa, (zzaoh) entry.getValue());
                }
                zzaqa.bw();
            } else {
                String valueOf = String.valueOf(zzaoh.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 15);
                sb.append("Couldn't write ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
        }
    };
    public static final zzaou bnI = zzb(zzaoh.class, bnH);
    public static final zzaou bnJ = new zzaou() {
        public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
            Class<Enum> by = zzapx.by();
            if (!Enum.class.isAssignableFrom(by) || by == Enum.class) {
                return null;
            }
            if (!by.isEnum()) {
                by = by.getSuperclass();
            }
            return new zza(by);
        }
    };
    public static final zzaou bna = zza(Byte.TYPE, Byte.class, bmZ);
    public static final zzaot<Number> bnb = new zzaot<Number>() {
        /* renamed from: a */
        public Number zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            try {
                return Short.valueOf((short) zzapy.nextInt());
            } catch (NumberFormatException e) {
                throw new zzaoq((Throwable) e);
            }
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Number number) {
            zzaqa.zza(number);
        }
    };
    public static final zzaou bnc = zza(Short.TYPE, Short.class, bnb);
    public static final zzaot<Number> bnd = new zzaot<Number>() {
        /* renamed from: a */
        public Number zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            try {
                return Integer.valueOf(zzapy.nextInt());
            } catch (NumberFormatException e) {
                throw new zzaoq((Throwable) e);
            }
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Number number) {
            zzaqa.zza(number);
        }
    };
    public static final zzaou bne = zza(Integer.TYPE, Integer.class, bnd);
    public static final zzaot<Number> bnf = new zzaot<Number>() {
        /* renamed from: a */
        public Number zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            try {
                return Long.valueOf(zzapy.nextLong());
            } catch (NumberFormatException e) {
                throw new zzaoq((Throwable) e);
            }
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Number number) {
            zzaqa.zza(number);
        }
    };
    public static final zzaot<Number> bng = new zzaot<Number>() {
        /* renamed from: a */
        public Number zzb(zzapy zzapy) {
            if (zzapy.bn() != zzapz.NULL) {
                return Float.valueOf((float) zzapy.nextDouble());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Number number) {
            zzaqa.zza(number);
        }
    };
    public static final zzaot<Number> bnh = new zzaot<Number>() {
        /* renamed from: a */
        public Number zzb(zzapy zzapy) {
            if (zzapy.bn() != zzapz.NULL) {
                return Double.valueOf(zzapy.nextDouble());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Number number) {
            zzaqa.zza(number);
        }
    };
    public static final zzaot<Number> bni = new zzaot<Number>() {
        /* renamed from: a */
        public Number zzb(zzapy zzapy) {
            zzapz bn = zzapy.bn();
            int i = AnonymousClass26.a[bn.ordinal()];
            if (i == 1) {
                return new zzape(zzapy.nextString());
            }
            if (i != 4) {
                String valueOf = String.valueOf(bn);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
                sb.append("Expecting number, got: ");
                sb.append(valueOf);
                throw new zzaoq(sb.toString());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Number number) {
            zzaqa.zza(number);
        }
    };
    public static final zzaou bnj = zza(Number.class, bni);
    public static final zzaot<Character> bnk = new zzaot<Character>() {
        /* renamed from: a */
        public Character zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            String nextString = zzapy.nextString();
            if (nextString.length() == 1) {
                return Character.valueOf(nextString.charAt(0));
            }
            String str = "Expecting character, got: ";
            String valueOf = String.valueOf(nextString);
            throw new zzaoq(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Character ch) {
            zzaqa.zzut(ch == null ? null : String.valueOf(ch));
        }
    };
    public static final zzaou bnl = zza(Character.TYPE, Character.class, bnk);
    public static final zzaot<String> bnm = new zzaot<String>() {
        /* renamed from: a */
        public String zzb(zzapy zzapy) {
            zzapz bn = zzapy.bn();
            if (bn != zzapz.NULL) {
                return bn == zzapz.BOOLEAN ? Boolean.toString(zzapy.nextBoolean()) : zzapy.nextString();
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, String str) {
            zzaqa.zzut(str);
        }
    };
    public static final zzaot<BigDecimal> bnn = new zzaot<BigDecimal>() {
        /* renamed from: a */
        public BigDecimal zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            try {
                return new BigDecimal(zzapy.nextString());
            } catch (NumberFormatException e) {
                throw new zzaoq((Throwable) e);
            }
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, BigDecimal bigDecimal) {
            zzaqa.zza(bigDecimal);
        }
    };
    public static final zzaot<BigInteger> bno = new zzaot<BigInteger>() {
        /* renamed from: a */
        public BigInteger zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            try {
                return new BigInteger(zzapy.nextString());
            } catch (NumberFormatException e) {
                throw new zzaoq((Throwable) e);
            }
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, BigInteger bigInteger) {
            zzaqa.zza(bigInteger);
        }
    };
    public static final zzaou bnp = zza(String.class, bnm);
    public static final zzaot<StringBuilder> bnq = new zzaot<StringBuilder>() {
        /* renamed from: a */
        public StringBuilder zzb(zzapy zzapy) {
            if (zzapy.bn() != zzapz.NULL) {
                return new StringBuilder(zzapy.nextString());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, StringBuilder sb) {
            zzaqa.zzut(sb == null ? null : sb.toString());
        }
    };
    public static final zzaou bnr = zza(StringBuilder.class, bnq);
    public static final zzaot<StringBuffer> bns = new zzaot<StringBuffer>() {
        /* renamed from: a */
        public StringBuffer zzb(zzapy zzapy) {
            if (zzapy.bn() != zzapz.NULL) {
                return new StringBuffer(zzapy.nextString());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, StringBuffer stringBuffer) {
            zzaqa.zzut(stringBuffer == null ? null : stringBuffer.toString());
        }
    };
    public static final zzaou bnt = zza(StringBuffer.class, bns);
    public static final zzaot<URL> bnu = new zzaot<URL>() {
        /* renamed from: a */
        public URL zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            String nextString = zzapy.nextString();
            if ("null".equals(nextString)) {
                return null;
            }
            return new URL(nextString);
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, URL url) {
            zzaqa.zzut(url == null ? null : url.toExternalForm());
        }
    };
    public static final zzaou bnv = zza(URL.class, bnu);
    public static final zzaot<URI> bnw = new zzaot<URI>() {
        /* renamed from: a */
        public URI zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            try {
                String nextString = zzapy.nextString();
                if ("null".equals(nextString)) {
                    return null;
                }
                return new URI(nextString);
            } catch (URISyntaxException e) {
                throw new zzaoi((Throwable) e);
            }
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, URI uri) {
            zzaqa.zzut(uri == null ? null : uri.toASCIIString());
        }
    };
    public static final zzaou bnx = zza(URI.class, bnw);
    public static final zzaot<InetAddress> bny = new zzaot<InetAddress>() {
        /* renamed from: a */
        public InetAddress zzb(zzapy zzapy) {
            if (zzapy.bn() != zzapz.NULL) {
                return InetAddress.getByName(zzapy.nextString());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, InetAddress inetAddress) {
            zzaqa.zzut(inetAddress == null ? null : inetAddress.getHostAddress());
        }
    };
    public static final zzaou bnz = zzb(InetAddress.class, bny);

    /* renamed from: com.google.android.gms.internal.zzapw$26 reason: invalid class name */
    static /* synthetic */ class AnonymousClass26 {
        static final /* synthetic */ int[] a = new int[zzapz.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|(3:19|20|22)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|22) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.google.android.gms.internal.zzapz[] r0 = com.google.android.gms.internal.zzapz.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.NUMBER     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.BOOLEAN     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.STRING     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.NULL     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.BEGIN_ARRAY     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.BEGIN_OBJECT     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.END_DOCUMENT     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.NAME     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006e }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.END_OBJECT     // Catch:{ NoSuchFieldError -> 0x006e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006e }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006e }
            L_0x006e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x007a }
                com.google.android.gms.internal.zzapz r1 = com.google.android.gms.internal.zzapz.END_ARRAY     // Catch:{ NoSuchFieldError -> 0x007a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007a }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007a }
            L_0x007a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzapw.AnonymousClass26.<clinit>():void");
        }
    }

    static final class zza<T extends Enum<T>> extends zzaot<T> {
        private final Map<String, T> a = new HashMap();
        private final Map<T, String> b = new HashMap();

        public zza(Class<T> cls) {
            Enum[] enumArr;
            try {
                for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                    String name = enumR.name();
                    zzaow zzaow = (zzaow) cls.getField(name).getAnnotation(zzaow.class);
                    if (zzaow != null) {
                        name = zzaow.value();
                        for (String put : zzaow.be()) {
                            this.a.put(put, enumR);
                        }
                    }
                    this.a.put(name, enumR);
                    this.b.put(enumR, name);
                }
            } catch (NoSuchFieldException unused) {
                throw new AssertionError();
            }
        }

        /* renamed from: a */
        public T zzb(zzapy zzapy) {
            if (zzapy.bn() != zzapz.NULL) {
                return (Enum) this.a.get(zzapy.nextString());
            }
            zzapy.nextNull();
            return null;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, T t) {
            zzaqa.zzut(t == null ? null : (String) this.b.get(t));
        }
    }

    public static <TT> zzaou zza(final zzapx<TT> zzapx, final zzaot<TT> zzaot) {
        return new zzaou() {
            public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
                if (zzapx.equals(zzapx)) {
                    return zzaot;
                }
                return null;
            }
        };
    }

    public static <TT> zzaou zza(final Class<TT> cls, final zzaot<TT> zzaot) {
        return new zzaou() {
            public String toString() {
                String valueOf = String.valueOf(cls.getName());
                String valueOf2 = String.valueOf(zzaot);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23 + String.valueOf(valueOf2).length());
                sb.append("Factory[type=");
                sb.append(valueOf);
                sb.append(",adapter=");
                sb.append(valueOf2);
                sb.append("]");
                return sb.toString();
            }

            public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
                if (zzapx.by() == cls) {
                    return zzaot;
                }
                return null;
            }
        };
    }

    public static <TT> zzaou zza(final Class<TT> cls, final Class<TT> cls2, final zzaot<? super TT> zzaot) {
        return new zzaou() {
            public String toString() {
                String valueOf = String.valueOf(cls2.getName());
                String valueOf2 = String.valueOf(cls.getName());
                String valueOf3 = String.valueOf(zzaot);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
                sb.append("Factory[type=");
                sb.append(valueOf);
                sb.append(Constants.SYMBOL_POSITIVE);
                sb.append(valueOf2);
                sb.append(",adapter=");
                sb.append(valueOf3);
                sb.append("]");
                return sb.toString();
            }

            public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
                Class by = zzapx.by();
                if (by == cls || by == cls2) {
                    return zzaot;
                }
                return null;
            }
        };
    }

    public static <TT> zzaou zzb(final Class<TT> cls, final zzaot<TT> zzaot) {
        return new zzaou() {
            public String toString() {
                String valueOf = String.valueOf(cls.getName());
                String valueOf2 = String.valueOf(zzaot);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32 + String.valueOf(valueOf2).length());
                sb.append("Factory[typeHierarchy=");
                sb.append(valueOf);
                sb.append(",adapter=");
                sb.append(valueOf2);
                sb.append("]");
                return sb.toString();
            }

            public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
                if (cls.isAssignableFrom(zzapx.by())) {
                    return zzaot;
                }
                return null;
            }
        };
    }

    public static <TT> zzaou zzb(final Class<TT> cls, final Class<? extends TT> cls2, final zzaot<? super TT> zzaot) {
        return new zzaou() {
            public String toString() {
                String valueOf = String.valueOf(cls.getName());
                String valueOf2 = String.valueOf(cls2.getName());
                String valueOf3 = String.valueOf(zzaot);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
                sb.append("Factory[type=");
                sb.append(valueOf);
                sb.append(Constants.SYMBOL_POSITIVE);
                sb.append(valueOf2);
                sb.append(",adapter=");
                sb.append(valueOf3);
                sb.append("]");
                return sb.toString();
            }

            public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
                Class by = zzapx.by();
                if (by == cls || by == cls2) {
                    return zzaot;
                }
                return null;
            }
        };
    }
}
