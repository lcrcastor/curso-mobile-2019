package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class FragmentController {
    private final FragmentHostCallback<?> a;

    @Deprecated
    public void doLoaderDestroy() {
    }

    @Deprecated
    public void doLoaderRetain() {
    }

    @Deprecated
    public void doLoaderStart() {
    }

    @Deprecated
    public void doLoaderStop(boolean z) {
    }

    @Deprecated
    public void dumpLoaders(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Deprecated
    public LoaderManager getSupportLoaderManager() {
        return null;
    }

    @Deprecated
    public void reportLoaderStart() {
    }

    @Deprecated
    public void restoreLoaderNonConfig(SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
    }

    @Deprecated
    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return null;
    }

    public static FragmentController createController(FragmentHostCallback<?> fragmentHostCallback) {
        return new FragmentController(fragmentHostCallback);
    }

    private FragmentController(FragmentHostCallback<?> fragmentHostCallback) {
        this.a = fragmentHostCallback;
    }

    public FragmentManager getSupportFragmentManager() {
        return this.a.e();
    }

    @Nullable
    public Fragment findFragmentByWho(String str) {
        return this.a.d.a(str);
    }

    public int getActiveFragmentsCount() {
        return this.a.d.b();
    }

    public List<Fragment> getActiveFragments(List<Fragment> list) {
        return this.a.d.a();
    }

    public void attachHost(Fragment fragment) {
        this.a.d.a((FragmentHostCallback) this.a, (FragmentContainer) this.a, fragment);
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return this.a.d.onCreateView(view, str, context, attributeSet);
    }

    public void noteStateNotSaved() {
        this.a.d.j();
    }

    public Parcelable saveAllState() {
        return this.a.d.i();
    }

    @Deprecated
    public void restoreAllState(Parcelable parcelable, List<Fragment> list) {
        this.a.d.a(parcelable, new FragmentManagerNonConfig(list, null, null));
    }

    public void restoreAllState(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        this.a.d.a(parcelable, fragmentManagerNonConfig);
    }

    @Deprecated
    public List<Fragment> retainNonConfig() {
        FragmentManagerNonConfig g = this.a.d.g();
        if (g != null) {
            return g.a();
        }
        return null;
    }

    public FragmentManagerNonConfig retainNestedNonConfig() {
        return this.a.d.g();
    }

    public void dispatchCreate() {
        this.a.d.k();
    }

    public void dispatchActivityCreated() {
        this.a.d.l();
    }

    public void dispatchStart() {
        this.a.d.m();
    }

    public void dispatchResume() {
        this.a.d.n();
    }

    public void dispatchPause() {
        this.a.d.o();
    }

    public void dispatchStop() {
        this.a.d.p();
    }

    public void dispatchReallyStop() {
        this.a.d.q();
    }

    public void dispatchDestroyView() {
        this.a.d.r();
    }

    public void dispatchDestroy() {
        this.a.d.s();
    }

    public void dispatchMultiWindowModeChanged(boolean z) {
        this.a.d.a(z);
    }

    public void dispatchPictureInPictureModeChanged(boolean z) {
        this.a.d.b(z);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        this.a.d.a(configuration);
    }

    public void dispatchLowMemory() {
        this.a.d.t();
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        return this.a.d.a(menu, menuInflater);
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        return this.a.d.a(menu);
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        return this.a.d.a(menuItem);
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        return this.a.d.b(menuItem);
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        this.a.d.b(menu);
    }

    public boolean execPendingActions() {
        return this.a.d.d();
    }
}
