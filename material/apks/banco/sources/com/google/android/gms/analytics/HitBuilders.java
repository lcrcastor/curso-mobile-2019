package com.google.android.gms.analytics;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.internal.zzae;
import com.google.android.gms.analytics.internal.zzao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HitBuilders {

    @Deprecated
    public static class AppViewBuilder extends HitBuilder<AppViewBuilder> {
        public AppViewBuilder() {
            set("&t", "screenview");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }
    }

    public static class EventBuilder extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            set("&t", "event");
        }

        public EventBuilder(String str, String str2) {
            this();
            setCategory(str);
            setAction(str2);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public EventBuilder setAction(String str) {
            set("&ea", str);
            return this;
        }

        public EventBuilder setCategory(String str) {
            set("&ec", str);
            return this;
        }

        public EventBuilder setLabel(String str) {
            set("&el", str);
            return this;
        }

        public EventBuilder setValue(long j) {
            set("&ev", Long.toString(j));
            return this;
        }
    }

    public static class ExceptionBuilder extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            set("&t", "exception");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public ExceptionBuilder setDescription(String str) {
            set("&exd", str);
            return this;
        }

        public ExceptionBuilder setFatal(boolean z) {
            set("&exf", zzao.zzaw(z));
            return this;
        }
    }

    public static class HitBuilder<T extends HitBuilder> {
        ProductAction a;
        Map<String, List<Product>> b = new HashMap();
        List<Promotion> c = new ArrayList();
        List<Product> d = new ArrayList();
        private Map<String, String> e = new HashMap();

        protected HitBuilder() {
        }

        private T a(String str, String str2) {
            if (str == null) {
                zzae.zzdf("HitBuilder.setIfNonNull() called with a null paramName.");
            } else if (str2 != null) {
                this.e.put(str, str2);
                return this;
            }
            return this;
        }

        public T addImpression(Product product, String str) {
            if (product == null) {
                zzae.zzdf("product should be non-null");
                return this;
            }
            if (str == null) {
                str = "";
            }
            if (!this.b.containsKey(str)) {
                this.b.put(str, new ArrayList());
            }
            ((List) this.b.get(str)).add(product);
            return this;
        }

        public T addProduct(Product product) {
            if (product == null) {
                zzae.zzdf("product should be non-null");
                return this;
            }
            this.d.add(product);
            return this;
        }

        public T addPromotion(Promotion promotion) {
            if (promotion == null) {
                zzae.zzdf("promotion should be non-null");
                return this;
            }
            this.c.add(promotion);
            return this;
        }

        public Map<String, String> build() {
            HashMap hashMap = new HashMap(this.e);
            if (this.a != null) {
                hashMap.putAll(this.a.build());
            }
            int i = 1;
            for (Promotion zzem : this.c) {
                hashMap.putAll(zzem.zzem(zzc.zzbo(i)));
                i++;
            }
            int i2 = 1;
            for (Product zzem2 : this.d) {
                hashMap.putAll(zzem2.zzem(zzc.zzbm(i2)));
                i2++;
            }
            int i3 = 1;
            for (Entry entry : this.b.entrySet()) {
                List<Product> list = (List) entry.getValue();
                String zzbr = zzc.zzbr(i3);
                int i4 = 1;
                for (Product product : list) {
                    String valueOf = String.valueOf(zzbr);
                    String valueOf2 = String.valueOf(zzc.zzbq(i4));
                    hashMap.putAll(product.zzem(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                    i4++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                    String valueOf3 = String.valueOf(zzbr);
                    String valueOf4 = String.valueOf("nm");
                    hashMap.put(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3), (String) entry.getKey());
                }
                i3++;
            }
            return hashMap;
        }

        /* access modifiers changed from: protected */
        public String get(String str) {
            return (String) this.e.get(str);
        }

        public final T set(String str, String str2) {
            if (str != null) {
                this.e.put(str, str2);
                return this;
            }
            zzae.zzdf("HitBuilder.set() called with a null paramName.");
            return this;
        }

        public final T setAll(Map<String, String> map) {
            if (map == null) {
                return this;
            }
            this.e.putAll(new HashMap(map));
            return this;
        }

        public T setCampaignParamsFromUrl(String str) {
            String zzfh = zzao.zzfh(str);
            if (TextUtils.isEmpty(zzfh)) {
                return this;
            }
            Map zzff = zzao.zzff(zzfh);
            a("&cc", (String) zzff.get("utm_content"));
            a("&cm", (String) zzff.get("utm_medium"));
            a("&cn", (String) zzff.get("utm_campaign"));
            a("&cs", (String) zzff.get("utm_source"));
            a("&ck", (String) zzff.get("utm_term"));
            a("&ci", (String) zzff.get("utm_id"));
            a("&anid", (String) zzff.get("anid"));
            a("&gclid", (String) zzff.get("gclid"));
            a("&dclid", (String) zzff.get("dclid"));
            a("&aclid", (String) zzff.get("aclid"));
            a("&gmob_t", (String) zzff.get("gmob_t"));
            return this;
        }

        public T setCustomDimension(int i, String str) {
            set(zzc.zzbi(i), str);
            return this;
        }

        public T setCustomMetric(int i, float f) {
            set(zzc.zzbk(i), Float.toString(f));
            return this;
        }

        /* access modifiers changed from: protected */
        public T setHitType(String str) {
            set("&t", str);
            return this;
        }

        public T setNewSession() {
            set("&sc", "start");
            return this;
        }

        public T setNonInteraction(boolean z) {
            set("&ni", zzao.zzaw(z));
            return this;
        }

        public T setProductAction(ProductAction productAction) {
            this.a = productAction;
            return this;
        }

        public T setPromotionAction(String str) {
            this.e.put("&promoa", str);
            return this;
        }
    }

    @Deprecated
    public static class ItemBuilder extends HitBuilder<ItemBuilder> {
        public ItemBuilder() {
            set("&t", "item");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public ItemBuilder setCategory(String str) {
            set("&iv", str);
            return this;
        }

        public ItemBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }

        public ItemBuilder setName(String str) {
            set("&in", str);
            return this;
        }

        public ItemBuilder setPrice(double d) {
            set("&ip", Double.toString(d));
            return this;
        }

        public ItemBuilder setQuantity(long j) {
            set("&iq", Long.toString(j));
            return this;
        }

        public ItemBuilder setSku(String str) {
            set("&ic", str);
            return this;
        }

        public ItemBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }
    }

    public static class ScreenViewBuilder extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            set("&t", "screenview");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }
    }

    public static class SocialBuilder extends HitBuilder<SocialBuilder> {
        public SocialBuilder() {
            set("&t", NotificationCompat.CATEGORY_SOCIAL);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public SocialBuilder setAction(String str) {
            set("&sa", str);
            return this;
        }

        public SocialBuilder setNetwork(String str) {
            set("&sn", str);
            return this;
        }

        public SocialBuilder setTarget(String str) {
            set("&st", str);
            return this;
        }
    }

    public static class TimingBuilder extends HitBuilder<TimingBuilder> {
        public TimingBuilder() {
            set("&t", "timing");
        }

        public TimingBuilder(String str, String str2, long j) {
            this();
            setVariable(str2);
            setValue(j);
            setCategory(str);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public TimingBuilder setCategory(String str) {
            set("&utc", str);
            return this;
        }

        public TimingBuilder setLabel(String str) {
            set("&utl", str);
            return this;
        }

        public TimingBuilder setValue(long j) {
            set("&utt", Long.toString(j));
            return this;
        }

        public TimingBuilder setVariable(String str) {
            set("&utv", str);
            return this;
        }
    }

    @Deprecated
    public static class TransactionBuilder extends HitBuilder<TransactionBuilder> {
        public TransactionBuilder() {
            set("&t", "transaction");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public TransactionBuilder setAffiliation(String str) {
            set("&ta", str);
            return this;
        }

        public TransactionBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }

        public TransactionBuilder setRevenue(double d) {
            set("&tr", Double.toString(d));
            return this;
        }

        public TransactionBuilder setShipping(double d) {
            set("&ts", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTax(double d) {
            set("&tt", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }
    }
}
