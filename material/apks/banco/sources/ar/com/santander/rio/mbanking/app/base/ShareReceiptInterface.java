package ar.com.santander.rio.mbanking.app.base;

public interface ShareReceiptInterface {
    boolean canExit();

    boolean canExitMenu();

    boolean shareReceiptBackAction();

    void shareReceiptExplation();

    boolean shareReceiptMenuAction();
}
