package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class CouponDetailSuperClubActivity$$InjectAdapter extends Binding<CouponDetailSuperClubActivity> implements MembersInjector<CouponDetailSuperClubActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseMvpActivity> b;

    public CouponDetailSuperClubActivity$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.activities.CouponDetailSuperClubActivity", false, CouponDetailSuperClubActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CouponDetailSuperClubActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", CouponDetailSuperClubActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public void injectMembers(CouponDetailSuperClubActivity couponDetailSuperClubActivity) {
        couponDetailSuperClubActivity.r = (AnalyticsManager) this.a.get();
        this.b.injectMembers(couponDetailSuperClubActivity);
    }
}
