package ar.com.santander.rio.mbanking.components.listswipe;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SwipeMenuView extends LinearLayout implements OnClickListener {
    private SwipeMenuListView a;
    private SwipeMenuLayout b;
    private SwipeMenu c;
    private OnSwipeItemClickListener d;
    private int e;

    public interface OnSwipeItemClickListener {
        void onItemClick(SwipeMenuView swipeMenuView, SwipeMenu swipeMenu, int i);
    }

    public int getPosition() {
        return this.e;
    }

    public void setPosition(int i) {
        this.e = i;
    }

    public SwipeMenuView(SwipeMenu swipeMenu, SwipeMenuListView swipeMenuListView) {
        super(swipeMenu.getContext());
        this.a = swipeMenuListView;
        this.c = swipeMenu;
        int i = 0;
        for (SwipeMenuItem a2 : swipeMenu.getMenuItems()) {
            int i2 = i + 1;
            a(a2, i);
            i = i2;
        }
    }

    private void a(SwipeMenuItem swipeMenuItem, int i) {
        LayoutParams layoutParams = new LayoutParams(swipeMenuItem.getWidth(), -1);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setId(i);
        linearLayout.setGravity(17);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setBackgroundDrawable(swipeMenuItem.getBackground());
        linearLayout.setOnClickListener(this);
        addView(linearLayout);
        if (swipeMenuItem.getIcon() != null) {
            linearLayout.addView(a(swipeMenuItem));
        }
        if (!TextUtils.isEmpty(swipeMenuItem.getTitle())) {
            linearLayout.addView(b(swipeMenuItem));
        }
    }

    private ImageView a(SwipeMenuItem swipeMenuItem) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(swipeMenuItem.getIcon());
        return imageView;
    }

    private TextView b(SwipeMenuItem swipeMenuItem) {
        TextView textView = new TextView(getContext());
        textView.setText(swipeMenuItem.getTitle());
        textView.setGravity(17);
        textView.setTextSize((float) swipeMenuItem.getTitleSize());
        textView.setTextColor(swipeMenuItem.getTitleColor());
        return textView;
    }

    public void onClick(View view) {
        if (this.d != null && this.b.isOpen()) {
            this.d.onItemClick(this, this.c, view.getId());
        }
    }

    public OnSwipeItemClickListener getOnSwipeItemClickListener() {
        return this.d;
    }

    public void setOnSwipeItemClickListener(OnSwipeItemClickListener onSwipeItemClickListener) {
        this.d = onSwipeItemClickListener;
    }

    public void setLayout(SwipeMenuLayout swipeMenuLayout) {
        this.b = swipeMenuLayout;
    }
}
