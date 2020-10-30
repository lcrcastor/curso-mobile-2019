package ar.com.santander.rio.mbanking.app.ui.Model;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class TouchableWrapper extends FrameLayout {
    private UpdateMapAfterUserInterection a;

    public interface UpdateMapAfterUserInterection {
        void onMoveMap();

        void onUpdateMapAfterUserInterection();
    }

    public TouchableWrapper(Context context) {
        super(context);
        try {
            this.a = (UpdateMapAfterUserInterection) context;
        } catch (ClassCastException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.toString());
            sb.append(" must implement UpdateMapAfterUserInterection");
            throw new ClassCastException(sb.toString());
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 2:
                this.a.onMoveMap();
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
