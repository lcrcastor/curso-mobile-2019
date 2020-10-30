package com.google.android.gms.tagmanager;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zzdn extends zzdk {
    private static final String a = zzaf.UNIVERSAL_ANALYTICS.toString();
    private static final String b = zzag.ACCOUNT.toString();
    private static final String c = zzag.ANALYTICS_PASS_THROUGH.toString();
    private static final String d = zzag.ENABLE_ECOMMERCE.toString();
    private static final String e = zzag.ECOMMERCE_USE_DATA_LAYER.toString();
    private static final String f = zzag.ECOMMERCE_MACRO_DATA.toString();
    private static final String g = zzag.ANALYTICS_FIELDS.toString();
    private static final String h = zzag.TRACK_TRANSACTION.toString();
    private static final String i = zzag.TRANSACTION_DATALAYER_MAP.toString();
    private static final String j = zzag.TRANSACTION_ITEM_DATALAYER_MAP.toString();
    private static final List<String> k = Arrays.asList(new String[]{ProductAction.ACTION_DETAIL, ProductAction.ACTION_CHECKOUT, ProductAction.ACTION_CHECKOUT_OPTION, "click", ProductAction.ACTION_ADD, ProductAction.ACTION_REMOVE, ProductAction.ACTION_PURCHASE, ProductAction.ACTION_REFUND});
    private static final Pattern l = Pattern.compile("dimension(\\d+)");
    private static final Pattern m = Pattern.compile("metric(\\d+)");
    private static Map<String, String> n;
    private static Map<String, String> o;
    private final Set<String> p;
    private final zzdj q;
    private final DataLayer r;

    public zzdn(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new zzdj(context));
    }

    zzdn(Context context, DataLayer dataLayer, zzdj zzdj) {
        super(a, new String[0]);
        this.r = dataLayer;
        this.q = zzdj;
        this.p = new HashSet();
        this.p.add("");
        this.p.add("0");
        this.p.add(Reintento.Reintento_Falso);
    }

    private ProductAction a(String str, Map<String, Object> map) {
        ProductAction productAction = new ProductAction(str);
        Object obj = map.get("id");
        if (obj != null) {
            productAction.setTransactionId(String.valueOf(obj));
        }
        Object obj2 = map.get("affiliation");
        if (obj2 != null) {
            productAction.setTransactionAffiliation(String.valueOf(obj2));
        }
        Object obj3 = map.get("coupon");
        if (obj3 != null) {
            productAction.setTransactionCouponCode(String.valueOf(obj3));
        }
        Object obj4 = map.get("list");
        if (obj4 != null) {
            productAction.setProductActionList(String.valueOf(obj4));
        }
        Object obj5 = map.get("option");
        if (obj5 != null) {
            productAction.setCheckoutOptions(String.valueOf(obj5));
        }
        Object obj6 = map.get("revenue");
        if (obj6 != null) {
            productAction.setTransactionRevenue(a(obj6).doubleValue());
        }
        Object obj7 = map.get("tax");
        if (obj7 != null) {
            productAction.setTransactionTax(a(obj7).doubleValue());
        }
        Object obj8 = map.get("shipping");
        if (obj8 != null) {
            productAction.setTransactionShipping(a(obj8).doubleValue());
        }
        Object obj9 = map.get("step");
        if (obj9 != null) {
            productAction.setCheckoutStep(b(obj9).intValue());
        }
        return productAction;
    }

    private Promotion a(Map<String, String> map) {
        Promotion promotion = new Promotion();
        String str = (String) map.get("id");
        if (str != null) {
            promotion.setId(String.valueOf(str));
        }
        String str2 = (String) map.get("name");
        if (str2 != null) {
            promotion.setName(String.valueOf(str2));
        }
        String str3 = (String) map.get("creative");
        if (str3 != null) {
            promotion.setCreative(String.valueOf(str3));
        }
        String str4 = (String) map.get("position");
        if (str4 != null) {
            promotion.setPosition(String.valueOf(str4));
        }
        return promotion;
    }

    private Double a(Object obj) {
        if (obj instanceof String) {
            try {
                return Double.valueOf((String) obj);
            } catch (NumberFormatException e2) {
                String str = "Cannot convert the object to Double: ";
                String valueOf = String.valueOf(e2.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        } else {
            if (obj instanceof Double) {
                return (Double) obj;
            }
            String str2 = "Cannot convert the object to Double: ";
            String valueOf2 = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
        }
    }

    private String a(String str) {
        Object obj = this.r.get(str);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    private Map<String, String> a(zza zza) {
        Object zzl = zzdm.zzl(zza);
        if (!(zzl instanceof Map)) {
            return null;
        }
        Map map = (Map) zzl;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private void a(Tracker tracker, Map<String, zza> map) {
        String a2 = a("transactionId");
        if (a2 == null) {
            zzbo.e("Cannot find transactionId in data layer.");
            return;
        }
        LinkedList<Map> linkedList = new LinkedList<>();
        try {
            Map b2 = b((zza) map.get(g));
            b2.put("&t", "transaction");
            for (Entry entry : c(map).entrySet()) {
                a(b2, (String) entry.getValue(), a((String) entry.getKey()));
            }
            linkedList.add(b2);
            List<Map> b3 = b("transactionProducts");
            if (b3 != null) {
                for (Map map2 : b3) {
                    if (map2.get("name") == null) {
                        zzbo.e("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map b4 = b((zza) map.get(g));
                    b4.put("&t", "item");
                    b4.put("&ti", a2);
                    for (Entry entry2 : d(map).entrySet()) {
                        a(b4, (String) entry2.getValue(), (String) map2.get(entry2.getKey()));
                    }
                    linkedList.add(b4);
                }
            }
            for (Map send : linkedList) {
                tracker.send(send);
            }
        } catch (IllegalArgumentException e2) {
            zzbo.zzb("Unable to send transaction", e2);
        }
    }

    private void a(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private boolean a(Map<String, zza> map, String str) {
        zza zza = (zza) map.get(str);
        if (zza == null) {
            return false;
        }
        return zzdm.zzk(zza).booleanValue();
    }

    private Product b(Map<String, Object> map) {
        String str;
        String valueOf;
        String str2;
        Product product = new Product();
        Object obj = map.get("id");
        if (obj != null) {
            product.setId(String.valueOf(obj));
        }
        Object obj2 = map.get("name");
        if (obj2 != null) {
            product.setName(String.valueOf(obj2));
        }
        Object obj3 = map.get("brand");
        if (obj3 != null) {
            product.setBrand(String.valueOf(obj3));
        }
        Object obj4 = map.get("category");
        if (obj4 != null) {
            product.setCategory(String.valueOf(obj4));
        }
        Object obj5 = map.get("variant");
        if (obj5 != null) {
            product.setVariant(String.valueOf(obj5));
        }
        Object obj6 = map.get("coupon");
        if (obj6 != null) {
            product.setCouponCode(String.valueOf(obj6));
        }
        Object obj7 = map.get("position");
        if (obj7 != null) {
            product.setPosition(b(obj7).intValue());
        }
        Object obj8 = map.get("price");
        if (obj8 != null) {
            product.setPrice(a(obj8).doubleValue());
        }
        Object obj9 = map.get("quantity");
        if (obj9 != null) {
            product.setQuantity(b(obj9).intValue());
        }
        for (String str3 : map.keySet()) {
            Matcher matcher = l.matcher(str3);
            if (matcher.matches()) {
                try {
                    product.setCustomDimension(Integer.parseInt(matcher.group(1)), String.valueOf(map.get(str3)));
                } catch (NumberFormatException unused) {
                    str = "illegal number in custom dimension value: ";
                    valueOf = String.valueOf(str3);
                    if (valueOf.length() == 0) {
                        str2 = new String(str);
                        zzbo.zzdf(str2);
                    }
                    str2 = str.concat(valueOf);
                    zzbo.zzdf(str2);
                }
            } else {
                Matcher matcher2 = m.matcher(str3);
                if (matcher2.matches()) {
                    try {
                        product.setCustomMetric(Integer.parseInt(matcher2.group(1)), b(map.get(str3)).intValue());
                    } catch (NumberFormatException unused2) {
                        str = "illegal number in custom metric value: ";
                        valueOf = String.valueOf(str3);
                        if (valueOf.length() == 0) {
                            str2 = new String(str);
                            zzbo.zzdf(str2);
                        }
                        str2 = str.concat(valueOf);
                        zzbo.zzdf(str2);
                    }
                }
            }
        }
        return product;
    }

    private Integer b(Object obj) {
        if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (NumberFormatException e2) {
                String str = "Cannot convert the object to Integer: ";
                String valueOf = String.valueOf(e2.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (obj instanceof Double) {
            return Integer.valueOf(((Double) obj).intValue());
        } else {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            String str2 = "Cannot convert the object to Integer: ";
            String valueOf2 = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
        }
    }

    private List<Map<String, String>> b(String str) {
        Object obj = this.r.get(str);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            throw new IllegalArgumentException("transactionProducts should be of type List.");
        }
        List<Map<String, String>> list = (List) obj;
        for (Object obj2 : list) {
            if (!(obj2 instanceof Map)) {
                throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
            }
        }
        return list;
    }

    private Map<String, String> b(zza zza) {
        if (zza == null) {
            return new HashMap();
        }
        Map<String, String> a2 = a(zza);
        if (a2 == null) {
            return new HashMap();
        }
        String str = (String) a2.get("&aip");
        if (str != null && this.p.contains(str.toLowerCase())) {
            a2.remove("&aip");
        }
        return a2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0027, code lost:
        if ((r8 instanceof java.util.Map) != false) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0038, code lost:
        if ((r8 instanceof java.util.Map) != false) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x003d, code lost:
        r8 = null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x011d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(com.google.android.gms.analytics.Tracker r7, java.util.Map<java.lang.String, com.google.android.gms.internal.zzai.zza> r8) {
        /*
            r6 = this;
            com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder r0 = new com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder
            r0.<init>()
            java.lang.String r1 = g
            java.lang.Object r1 = r8.get(r1)
            com.google.android.gms.internal.zzai$zza r1 = (com.google.android.gms.internal.zzai.zza) r1
            java.util.Map r1 = r6.b(r1)
            r0.setAll(r1)
            java.lang.String r2 = e
            boolean r2 = r6.a(r8, r2)
            r3 = 0
            if (r2 == 0) goto L_0x002a
            com.google.android.gms.tagmanager.DataLayer r8 = r6.r
            java.lang.String r2 = "ecommerce"
            java.lang.Object r8 = r8.get(r2)
            boolean r2 = r8 instanceof java.util.Map
            if (r2 == 0) goto L_0x003d
            goto L_0x003a
        L_0x002a:
            java.lang.String r2 = f
            java.lang.Object r8 = r8.get(r2)
            com.google.android.gms.internal.zzai$zza r8 = (com.google.android.gms.internal.zzai.zza) r8
            java.lang.Object r8 = com.google.android.gms.tagmanager.zzdm.zzl(r8)
            boolean r2 = r8 instanceof java.util.Map
            if (r2 == 0) goto L_0x003d
        L_0x003a:
            java.util.Map r8 = (java.util.Map) r8
            goto L_0x003e
        L_0x003d:
            r8 = r3
        L_0x003e:
            if (r8 == 0) goto L_0x01b8
            java.lang.String r2 = "&cu"
            java.lang.Object r1 = r1.get(r2)
            java.lang.String r1 = (java.lang.String) r1
            if (r1 != 0) goto L_0x0052
            java.lang.String r1 = "currencyCode"
            java.lang.Object r1 = r8.get(r1)
            java.lang.String r1 = (java.lang.String) r1
        L_0x0052:
            if (r1 == 0) goto L_0x0059
            java.lang.String r2 = "&cu"
            r0.set(r2, r1)
        L_0x0059:
            java.lang.String r1 = "impressions"
            java.lang.Object r1 = r8.get(r1)
            boolean r2 = r1 instanceof java.util.List
            if (r2 == 0) goto L_0x00a4
            java.util.List r1 = (java.util.List) r1
            java.util.Iterator r1 = r1.iterator()
        L_0x0069:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x00a4
            java.lang.Object r2 = r1.next()
            java.util.Map r2 = (java.util.Map) r2
            com.google.android.gms.analytics.ecommerce.Product r4 = r6.b(r2)     // Catch:{ RuntimeException -> 0x0085 }
            java.lang.String r5 = "list"
            java.lang.Object r2 = r2.get(r5)     // Catch:{ RuntimeException -> 0x0085 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ RuntimeException -> 0x0085 }
            r0.addImpression(r4, r2)     // Catch:{ RuntimeException -> 0x0085 }
            goto L_0x0069
        L_0x0085:
            r2 = move-exception
            java.lang.String r4 = "Failed to extract a product from DataLayer. "
            java.lang.String r2 = r2.getMessage()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r5 = r2.length()
            if (r5 == 0) goto L_0x009b
            java.lang.String r2 = r4.concat(r2)
            goto L_0x00a0
        L_0x009b:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r4)
        L_0x00a0:
            com.google.android.gms.tagmanager.zzbo.e(r2)
            goto L_0x0069
        L_0x00a4:
            java.lang.String r1 = "promoClick"
            boolean r1 = r8.containsKey(r1)
            if (r1 == 0) goto L_0x00be
            java.lang.String r1 = "promoClick"
        L_0x00ae:
            java.lang.Object r1 = r8.get(r1)
            java.util.Map r1 = (java.util.Map) r1
            java.lang.String r2 = "promotions"
            java.lang.Object r1 = r1.get(r2)
            r3 = r1
            java.util.List r3 = (java.util.List) r3
            goto L_0x00c9
        L_0x00be:
            java.lang.String r1 = "promoView"
            boolean r1 = r8.containsKey(r1)
            if (r1 == 0) goto L_0x00c9
            java.lang.String r1 = "promoView"
            goto L_0x00ae
        L_0x00c9:
            r1 = 1
            if (r3 == 0) goto L_0x011b
            java.util.Iterator r2 = r3.iterator()
        L_0x00d0:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0103
            java.lang.Object r3 = r2.next()
            java.util.Map r3 = (java.util.Map) r3
            com.google.android.gms.analytics.ecommerce.Promotion r3 = r6.a(r3)     // Catch:{ RuntimeException -> 0x00e4 }
            r0.addPromotion(r3)     // Catch:{ RuntimeException -> 0x00e4 }
            goto L_0x00d0
        L_0x00e4:
            r3 = move-exception
            java.lang.String r4 = "Failed to extract a promotion from DataLayer. "
            java.lang.String r3 = r3.getMessage()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            int r5 = r3.length()
            if (r5 == 0) goto L_0x00fa
            java.lang.String r3 = r4.concat(r3)
            goto L_0x00ff
        L_0x00fa:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r4)
        L_0x00ff:
            com.google.android.gms.tagmanager.zzbo.e(r3)
            goto L_0x00d0
        L_0x0103:
            java.lang.String r2 = "promoClick"
            boolean r2 = r8.containsKey(r2)
            if (r2 == 0) goto L_0x0114
            java.lang.String r1 = "&promoa"
            java.lang.String r2 = "click"
            r0.set(r1, r2)
            r1 = 0
            goto L_0x011b
        L_0x0114:
            java.lang.String r2 = "&promoa"
            java.lang.String r3 = "view"
            r0.set(r2, r3)
        L_0x011b:
            if (r1 == 0) goto L_0x01b8
            java.util.List<java.lang.String> r1 = k
            java.util.Iterator r1 = r1.iterator()
        L_0x0123:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x01b8
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            boolean r3 = r8.containsKey(r2)
            if (r3 == 0) goto L_0x0123
            java.lang.Object r8 = r8.get(r2)
            java.util.Map r8 = (java.util.Map) r8
            java.lang.String r1 = "products"
            java.lang.Object r1 = r8.get(r1)
            java.util.List r1 = (java.util.List) r1
            if (r1 == 0) goto L_0x017c
            java.util.Iterator r1 = r1.iterator()
        L_0x0149:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x017c
            java.lang.Object r3 = r1.next()
            java.util.Map r3 = (java.util.Map) r3
            com.google.android.gms.analytics.ecommerce.Product r3 = r6.b(r3)     // Catch:{ RuntimeException -> 0x015d }
            r0.addProduct(r3)     // Catch:{ RuntimeException -> 0x015d }
            goto L_0x0149
        L_0x015d:
            r3 = move-exception
            java.lang.String r4 = "Failed to extract a product from DataLayer. "
            java.lang.String r3 = r3.getMessage()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            int r5 = r3.length()
            if (r5 == 0) goto L_0x0173
            java.lang.String r3 = r4.concat(r3)
            goto L_0x0178
        L_0x0173:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r4)
        L_0x0178:
            com.google.android.gms.tagmanager.zzbo.e(r3)
            goto L_0x0149
        L_0x017c:
            java.lang.String r1 = "actionField"
            boolean r1 = r8.containsKey(r1)     // Catch:{ RuntimeException -> 0x019a }
            if (r1 == 0) goto L_0x0191
            java.lang.String r1 = "actionField"
            java.lang.Object r8 = r8.get(r1)     // Catch:{ RuntimeException -> 0x019a }
            java.util.Map r8 = (java.util.Map) r8     // Catch:{ RuntimeException -> 0x019a }
            com.google.android.gms.analytics.ecommerce.ProductAction r8 = r6.a(r2, r8)     // Catch:{ RuntimeException -> 0x019a }
            goto L_0x0196
        L_0x0191:
            com.google.android.gms.analytics.ecommerce.ProductAction r8 = new com.google.android.gms.analytics.ecommerce.ProductAction     // Catch:{ RuntimeException -> 0x019a }
            r8.<init>(r2)     // Catch:{ RuntimeException -> 0x019a }
        L_0x0196:
            r0.setProductAction(r8)     // Catch:{ RuntimeException -> 0x019a }
            goto L_0x01b8
        L_0x019a:
            r8 = move-exception
            java.lang.String r1 = "Failed to extract a product action from DataLayer. "
            java.lang.String r8 = r8.getMessage()
            java.lang.String r8 = java.lang.String.valueOf(r8)
            int r2 = r8.length()
            if (r2 == 0) goto L_0x01b0
            java.lang.String r8 = r1.concat(r8)
            goto L_0x01b5
        L_0x01b0:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r1)
        L_0x01b5:
            com.google.android.gms.tagmanager.zzbo.e(r8)
        L_0x01b8:
            java.util.Map r8 = r0.build()
            r7.send(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzdn.b(com.google.android.gms.analytics.Tracker, java.util.Map):void");
    }

    private Map<String, String> c(Map<String, zza> map) {
        zza zza = (zza) map.get(i);
        if (zza != null) {
            return a(zza);
        }
        if (n == null) {
            HashMap hashMap = new HashMap();
            hashMap.put("transactionId", "&ti");
            hashMap.put("transactionAffiliation", "&ta");
            hashMap.put("transactionTax", "&tt");
            hashMap.put("transactionShipping", "&ts");
            hashMap.put("transactionTotal", "&tr");
            hashMap.put("transactionCurrency", "&cu");
            n = hashMap;
        }
        return n;
    }

    private Map<String, String> d(Map<String, zza> map) {
        zza zza = (zza) map.get(j);
        if (zza != null) {
            return a(zza);
        }
        if (o == null) {
            HashMap hashMap = new HashMap();
            hashMap.put("name", "&in");
            hashMap.put("sku", "&ic");
            hashMap.put("category", "&iv");
            hashMap.put("price", "&ip");
            hashMap.put("quantity", "&iq");
            hashMap.put("currency", "&cu");
            o = hashMap;
        }
        return o;
    }

    public /* bridge */ /* synthetic */ zza zzaw(Map map) {
        return super.zzaw(map);
    }

    public void zzay(Map<String, zza> map) {
        Tracker zzpv = this.q.zzpv("_GTM_DEFAULT_TRACKER_");
        zzpv.enableAdvertisingIdCollection(a(map, "collect_adid"));
        if (a(map, d)) {
            b(zzpv, map);
        } else if (a(map, c)) {
            zzpv.send(b((zza) map.get(g)));
        } else if (a(map, h)) {
            a(zzpv, map);
        } else {
            zzbo.zzdf("Ignoring unknown tag.");
        }
    }

    public /* bridge */ /* synthetic */ boolean zzcds() {
        return super.zzcds();
    }

    public /* bridge */ /* synthetic */ String zzcff() {
        return super.zzcff();
    }

    public /* bridge */ /* synthetic */ Set zzcfg() {
        return super.zzcfg();
    }
}
