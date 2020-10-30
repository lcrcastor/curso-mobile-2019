package android.support.v4.app;

abstract class BaseFragmentActivityEclair extends BaseFragmentActivityDonut {
    BaseFragmentActivityEclair() {
    }

    /* access modifiers changed from: 0000 */
    public void onBackPressedNotHandled() {
        super.onBackPressed();
    }
}
