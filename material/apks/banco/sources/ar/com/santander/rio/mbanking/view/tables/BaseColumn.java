package ar.com.santander.rio.mbanking.view.tables;

import android.view.View;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;

public interface BaseColumn {

    public enum TypeStyle {
        TYPE_STYLE_LABEL,
        TYPE_STYLE_DATA_VALUE
    }

    CElementAcc getCElementAcc();

    View getViewColumn();

    void setCElementAcc(CElementAcc cElementAcc);
}
