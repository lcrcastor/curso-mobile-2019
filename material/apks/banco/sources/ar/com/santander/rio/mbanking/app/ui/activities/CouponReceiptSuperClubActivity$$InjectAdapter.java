package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class CouponReceiptSuperClubActivity$$InjectAdapter extends Binding<CouponReceiptSuperClubActivity> implements MembersInjector<CouponReceiptSuperClubActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public CouponReceiptSuperClubActivity$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.activities.CouponReceiptSuperClubActivity", false, CouponReceiptSuperClubActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CouponReceiptSuperClubActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", CouponReceiptSuperClubActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public void injectMembers(CouponReceiptSuperClubActivity couponReceiptSuperClubActivity) {
        couponReceiptSuperClubActivity.r = (AnalyticsManager) this.a.get();
        this.b.injectMembers(couponReceiptSuperClubActivity);
    }
}
