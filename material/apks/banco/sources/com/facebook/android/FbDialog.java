package com.facebook.android;

import android.content.Context;
import android.os.Bundle;
import com.facebook.FacebookDialogException;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

@Deprecated
public class FbDialog extends WebDialog {
    private DialogListener a;

    public FbDialog(Context context, String str, DialogListener dialogListener) {
        this(context, str, dialogListener, (int) WebDialog.DEFAULT_THEME);
    }

    public FbDialog(Context context, String str, DialogListener dialogListener, int i) {
        super(context, str, i);
        a(dialogListener);
    }

    public FbDialog(Context context, String str, Bundle bundle, DialogListener dialogListener) {
        super(context, str, bundle, WebDialog.DEFAULT_THEME, null);
        a(dialogListener);
    }

    public FbDialog(Context context, String str, Bundle bundle, DialogListener dialogListener, int i) {
        super(context, str, bundle, i, null);
        a(dialogListener);
    }

    private void a(DialogListener dialogListener) {
        this.a = dialogListener;
        setOnCompleteListener(new OnCompleteListener() {
            public void onComplete(Bundle bundle, FacebookException facebookException) {
                FbDialog.this.a(bundle, facebookException);
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(Bundle bundle, FacebookException facebookException) {
        if (this.a != null) {
            if (bundle != null) {
                this.a.onComplete(bundle);
            } else if (facebookException instanceof FacebookDialogException) {
                FacebookDialogException facebookDialogException = (FacebookDialogException) facebookException;
                this.a.onError(new DialogError(facebookDialogException.getMessage(), facebookDialogException.getErrorCode(), facebookDialogException.getFailingUrl()));
            } else if (facebookException instanceof FacebookOperationCanceledException) {
                this.a.onCancel();
            } else {
                this.a.onFacebookError(new FacebookError(facebookException.getMessage()));
            }
        }
    }
}
