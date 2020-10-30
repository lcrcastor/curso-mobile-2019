package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.ISharedListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import javax.inject.Inject;

public class PromocionDetalleActivity extends BaseActivity {
    @Inject
    AnalyticsManager p;
    @Inject
    SessionManager q;
    ISharedListener r;

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x009d, code lost:
        if (r3.equals("ST") == false) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00e8, code lost:
        r6.p.trackScreen(getString(ar.com.santander.rio.mbanking.R.string.analytics_screen_name_promociones_ficha_superclub));
        r7 = ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionShoppingFragment.newInstance(r0);
        setSharedListener(r7);
        getSupportFragmentManager().beginTransaction().add((int) ar.com.santander.rio.mbanking.R.id.container, (android.support.v4.app.Fragment) r7).commit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0114, code lost:
        r6.p.trackScreen(getString(ar.com.santander.rio.mbanking.R.string.analytics_screen_name_promociones_ficha_superclub));
        r7 = ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionStandardFragment.newInstance(r0);
        setSharedListener(r7);
        getSupportFragmentManager().beginTransaction().add((int) ar.com.santander.rio.mbanking.R.id.container, (android.support.v4.app.Fragment) r7).commit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0108  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r7) {
        /*
            r6 = this;
            super.onCreate(r7)
            r0 = 2131492956(0x7f0c005c, float:1.8609379E38)
            r6.setContentView(r0)
            r0 = 2130772000(0x7f010020, float:1.7147106E38)
            r1 = 2130772003(0x7f010023, float:1.7147112E38)
            r6.overridePendingTransition(r0, r1)
            butterknife.ButterKnife.inject(r6)
            android.support.v7.app.ActionBar r0 = r6.getSupportActionBar()
            r1 = 0
            r0.setElevation(r1)
            r1 = 0
            r0.setDisplayHomeAsUpEnabled(r1)
            r0.setHomeButtonEnabled(r1)
            r2 = 1
            r0.setDisplayShowCustomEnabled(r2)
            r0.setDisplayShowTitleEnabled(r1)
            r0.setDisplayUseLogoEnabled(r1)
            java.lang.String r3 = "layout_inflater"
            java.lang.Object r3 = r6.getSystemService(r3)
            android.view.LayoutInflater r3 = (android.view.LayoutInflater) r3
            r4 = 2131492898(0x7f0c0022, float:1.860926E38)
            r5 = 0
            android.view.View r3 = r3.inflate(r4, r5)
            r4 = 2131366059(0x7f0a10ab, float:1.8352E38)
            android.view.View r4 = r3.findViewById(r4)
            ar.com.santander.rio.mbanking.app.ui.activities.PromocionDetalleActivity$1 r5 = new ar.com.santander.rio.mbanking.app.ui.activities.PromocionDetalleActivity$1
            r5.<init>()
            r4.setOnClickListener(r5)
            r4 = 2131365721(0x7f0a0f59, float:1.8351315E38)
            android.view.View r4 = r3.findViewById(r4)
            ar.com.santander.rio.mbanking.app.ui.activities.PromocionDetalleActivity$2 r5 = new ar.com.santander.rio.mbanking.app.ui.activities.PromocionDetalleActivity$2
            r5.<init>()
            r4.setOnClickListener(r5)
            android.support.v7.app.ActionBar$LayoutParams r4 = new android.support.v7.app.ActionBar$LayoutParams
            r5 = -1
            r4.<init>(r5, r5)
            r0.setCustomView(r3, r4)
            android.content.Intent r0 = r6.getIntent()
            java.lang.String r3 = "marker"
            android.os.Parcelable r0 = r0.getParcelableExtra(r3)
            ar.com.santander.rio.mbanking.components.mapInfo.MyMarker r0 = (ar.com.santander.rio.mbanking.components.mapInfo.MyMarker) r0
            android.content.Intent r3 = r6.getIntent()
            java.lang.String r4 = "tipo"
            java.lang.String r3 = r3.getStringExtra(r4)
            r0.setTipo(r3)
            if (r7 != 0) goto L_0x0133
            if (r3 == 0) goto L_0x0133
            int r7 = r3.hashCode()
            r4 = 2087(0x827, float:2.925E-42)
            if (r7 == r4) goto L_0x00b4
            r2 = 2098(0x832, float:2.94E-42)
            if (r7 == r2) goto L_0x00aa
            r2 = 2645(0xa55, float:3.706E-42)
            if (r7 == r2) goto L_0x00a0
            r2 = 2657(0xa61, float:3.723E-42)
            if (r7 == r2) goto L_0x0097
            goto L_0x00be
        L_0x0097:
            java.lang.String r7 = "ST"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x00be
            goto L_0x00bf
        L_0x00a0:
            java.lang.String r7 = "SH"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x00be
            r1 = 2
            goto L_0x00bf
        L_0x00aa:
            java.lang.String r7 = "AS"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x00be
            r1 = 3
            goto L_0x00bf
        L_0x00b4:
            java.lang.String r7 = "AH"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x00be
            r1 = 1
            goto L_0x00bf
        L_0x00be:
            r1 = -1
        L_0x00bf:
            r7 = 2131757893(0x7f100b45, float:1.9146735E38)
            r2 = 2131364424(0x7f0a0a48, float:1.8348685E38)
            switch(r1) {
                case 0: goto L_0x0108;
                case 1: goto L_0x0114;
                case 2: goto L_0x00dc;
                case 3: goto L_0x00e8;
                default: goto L_0x00c8;
            }
        L_0x00c8:
            android.support.v4.app.FragmentManager r7 = r6.getSupportFragmentManager()
            android.support.v4.app.FragmentTransaction r7 = r7.beginTransaction()
            ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionStandardFragment r0 = ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionStandardFragment.newInstance(r0)
            android.support.v4.app.FragmentTransaction r7 = r7.add(r2, r0)
            r7.commit()
            goto L_0x0133
        L_0x00dc:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r6.p
            r3 = 2131757892(0x7f100b44, float:1.9146733E38)
            java.lang.String r3 = r6.getString(r3)
            r1.trackScreen(r3)
        L_0x00e8:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r6.p
            java.lang.String r7 = r6.getString(r7)
            r1.trackScreen(r7)
            ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionShoppingFragment r7 = ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionShoppingFragment.newInstance(r0)
            r6.setSharedListener(r7)
            android.support.v4.app.FragmentManager r0 = r6.getSupportFragmentManager()
            android.support.v4.app.FragmentTransaction r0 = r0.beginTransaction()
            android.support.v4.app.FragmentTransaction r7 = r0.add(r2, r7)
            r7.commit()
            goto L_0x0133
        L_0x0108:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r6.p
            r3 = 2131757891(0x7f100b43, float:1.914673E38)
            java.lang.String r3 = r6.getString(r3)
            r1.trackScreen(r3)
        L_0x0114:
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r6.p
            java.lang.String r7 = r6.getString(r7)
            r1.trackScreen(r7)
            ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionStandardFragment r7 = ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionStandardFragment.newInstance(r0)
            r6.setSharedListener(r7)
            android.support.v4.app.FragmentManager r0 = r6.getSupportFragmentManager()
            android.support.v4.app.FragmentTransaction r0 = r0.beginTransaction()
            android.support.v4.app.FragmentTransaction r7 = r0.add(r2, r7)
            r7.commit()
        L_0x0133:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.PromocionDetalleActivity.onCreate(android.os.Bundle):void");
    }

    public ISharedListener getSharedListener() {
        return this.r;
    }

    public void setSharedListener(ISharedListener iSharedListener) {
        this.r = iSharedListener;
    }
}
