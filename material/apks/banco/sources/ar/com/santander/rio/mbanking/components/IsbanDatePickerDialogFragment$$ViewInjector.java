package ar.com.santander.rio.mbanking.components;

import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.DatePicker;
import butterknife.ButterKnife.Finder;

public class IsbanDatePickerDialogFragment$$ViewInjector {
    public static void inject(Finder finder, IsbanDatePickerDialogFragment isbanDatePickerDialogFragment, Object obj) {
        isbanDatePickerDialogFragment.titleView = (TextView) finder.findRequiredView(obj, R.id.dialogTitle, "field 'titleView'");
        isbanDatePickerDialogFragment.aceptarButton = (Button) finder.findRequiredView(obj, R.id.aceptarButton, "field 'aceptarButton'");
        isbanDatePickerDialogFragment.cancelButton = (Button) finder.findRequiredView(obj, R.id.cancelButton, "field 'cancelButton'");
        isbanDatePickerDialogFragment.datePicker = (DatePicker) finder.findRequiredView(obj, R.id.datePicker, "field 'datePicker'");
    }

    public static void reset(IsbanDatePickerDialogFragment isbanDatePickerDialogFragment) {
        isbanDatePickerDialogFragment.titleView = null;
        isbanDatePickerDialogFragment.aceptarButton = null;
        isbanDatePickerDialogFragment.cancelButton = null;
        isbanDatePickerDialogFragment.datePicker = null;
    }
}
