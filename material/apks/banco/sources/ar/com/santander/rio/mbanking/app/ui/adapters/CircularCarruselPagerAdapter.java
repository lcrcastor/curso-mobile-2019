package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.WebTextViewFragment;
import java.util.ArrayList;

public class CircularCarruselPagerAdapter extends CarruselPagerAdapter {
    protected Boolean mIsCircular;
    protected OnPageChangeListener mListener;
    protected ViewPager mPager;

    public CircularCarruselPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public CircularCarruselPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager) {
        this(fragmentManager);
        this.mPager = viewPager;
    }

    public CircularCarruselPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager, Boolean bool) {
        this(fragmentManager, viewPager);
        this.mIsCircular = bool;
    }

    public Boolean isListenerAvailable() {
        return Boolean.valueOf(this.mListener != null);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (!isListenerAvailable().booleanValue()) {
            this.mListener = onPageChangeListener;
        }
    }

    public void setIsCircular(Boolean bool) {
        this.mIsCircular = bool;
    }

    public void prepareCarrusel() {
        boolean z = true;
        if (this.mIsCircular != null) {
            z = this.mIsCircular.booleanValue();
        } else if (getCount() <= 1) {
            z = false;
        }
        this.mIsCircular = Boolean.valueOf(z);
        if (this.mIsCircular.booleanValue()) {
            prepareCircularCarrusel();
        } else {
            prepareLinearCarrusel();
        }
    }

    /* access modifiers changed from: protected */
    public void prepareLinearCarrusel() {
        this.mPager.addOnPageChangeListener(this.mListener);
    }

    /* access modifiers changed from: protected */
    public void prepareCircularCarrusel() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(((WebTextViewFragment) this.fragments.get(this.fragments.size() - 1)).getClone());
        arrayList.addAll(this.fragments);
        arrayList.add(((WebTextViewFragment) this.fragments.get(0)).getClone());
        this.fragments = arrayList;
        this.mPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int i) {
                int currentItem = CircularCarruselPagerAdapter.this.getCurrentItem();
                if (CircularCarruselPagerAdapter.this.isListenerAvailable().booleanValue() && CircularCarruselPagerAdapter.this.isCurrentItemOnRange(currentItem).booleanValue()) {
                    CircularCarruselPagerAdapter.this.mListener.onPageSelected(currentItem);
                }
            }

            public void onPageScrolled(int i, float f, int i2) {
                if (CircularCarruselPagerAdapter.this.isListenerAvailable().booleanValue()) {
                    CircularCarruselPagerAdapter.this.mListener.onPageScrolled(i, f, i2);
                }
            }

            public void onPageScrollStateChanged(int i) {
                if (i == 0) {
                    int count = CircularCarruselPagerAdapter.this.getCount();
                    int currentItem = CircularCarruselPagerAdapter.this.mPager.getCurrentItem();
                    if (currentItem == 0) {
                        CircularCarruselPagerAdapter.this.mPager.setCurrentItem(count - 2, false);
                    } else if (currentItem == count - 1) {
                        CircularCarruselPagerAdapter.this.mPager.setCurrentItem(1, false);
                    }
                }
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0017, code lost:
        if (r3 <= (r2.mIsCircular.booleanValue() ? getCount() - 3 : getCount() - 1)) goto L_0x001b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Boolean isCurrentItemOnRange(int r3) {
        /*
            r2 = this;
            r0 = 1
            if (r3 < 0) goto L_0x001a
            java.lang.Boolean r1 = r2.mIsCircular
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x0012
            int r1 = r2.getCount()
            int r1 = r1 + -3
            goto L_0x0017
        L_0x0012:
            int r1 = r2.getCount()
            int r1 = r1 - r0
        L_0x0017:
            if (r3 > r1) goto L_0x001a
            goto L_0x001b
        L_0x001a:
            r0 = 0
        L_0x001b:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.adapters.CircularCarruselPagerAdapter.isCurrentItemOnRange(int):java.lang.Boolean");
    }

    public int getCurrentItem() {
        return this.mIsCircular.booleanValue() ? this.mPager.getCurrentItem() - 1 : this.mPager.getCurrentItem();
    }

    public void setCurrentItem(int i) {
        ViewPager viewPager = this.mPager;
        if (this.mIsCircular.booleanValue()) {
            i++;
        }
        viewPager.setCurrentItem(i);
    }
}
