package ar.com.santander.rio.mbanking.app.module.softtoken;

public interface ISoftTokenPage {
    public static final int ANIMATION_BOTTOM_TOP = 4;
    public static final int ANIMATION_LEFT_RIGHT = 1;
    public static final int ANIMATION_RIGHT_LEFT = 2;
    public static final int ANIMATION_TOP_BOTTOM = 3;
    public static final int ANIMATION_VOID = 0;

    void goToPage(Integer num);

    void goToPage(Integer num, int i);

    void goToPage(Integer num, ISoftTokenPage iSoftTokenPage, int i);

    void nextPage();

    void nextPage(int i);

    void onCreatePage();

    void onFinishPage();

    void previousPage();

    void previousPage(int i);
}
