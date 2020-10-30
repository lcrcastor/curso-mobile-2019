package ar.com.santander.rio.mbanking.app.ui.forms;

import android.graphics.Canvas;

public interface IFormViewGroup {
    void drawCanvas(Canvas canvas);

    int getTotalHeight();
}
