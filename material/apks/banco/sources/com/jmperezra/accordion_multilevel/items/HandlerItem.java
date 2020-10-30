package com.jmperezra.accordion_multilevel.items;

import com.jmperezra.accordion_multilevel.commons.TypeStateCheck;
import com.jmperezra.accordion_multilevel.listeners.AccordionMultilevelListener;
import com.jmperezra.accordion_multilevel.listeners.ItemViewListener;
import java.util.List;

public class HandlerItem implements ItemViewListener {
    private ItemState a;
    private HandlerItemView b;
    private HandlerItem c;
    private List<HandlerItem> d;
    private AccordionMultilevelListener e;
    private TypeStateCheck f = TypeStateCheck.UNCHECK;
    private int g;

    public HandlerItem(ItemState itemState, HandlerItemView handlerItemView, int i) {
        this.a = itemState;
        this.b = handlerItemView;
        this.g = i;
    }

    public ItemState getItemState() {
        return this.a;
    }

    public HandlerItemView getHandlerItemView() {
        return this.b;
    }

    public List<HandlerItem> getChildren() {
        return this.d;
    }

    public HandlerItem getParent() {
        return this.c;
    }

    public void init() {
        a();
    }

    public void initUI() {
        b();
    }

    private void a() {
        if (this.c != null && this.c.getItemState().isSelected()) {
            this.a.setSelected(true);
        }
    }

    private void b() {
        this.b.setPaddingLeft(this.g);
        this.b.setItemViewListener(this);
        this.b.setTextLabel(this.a.getLabel());
        this.b.setVisibilityCheckBox(this.a.getVisibilityCheckBox());
        this.b.setVisibilityArrow(hasChildren());
        this.b.setStyleItem(this.a.getStyleItem());
        c();
        d();
    }

    private void c() {
        if (this.a.isExpand() != null || this.b.isExpand()) {
            this.b.updateVisibilityChildren(this.a.isExpand());
            if (hasChildren()) {
                this.b.rotateDownArrow();
            }
        }
    }

    private void d() {
        if (this.a.isSelected()) {
            this.b.setStateCheckBox(TypeStateCheck.CHECK_ALL);
        } else if (hasChildren()) {
            this.b.setStateCheckBox(a(this.a.getChildren()));
        }
    }

    public boolean hasChildren() {
        return (this.a == null || this.a.getChildren() == null || this.a.getChildren().size() < 0) ? false : true;
    }

    public void setChildrenHandlerItem(List<HandlerItem> list) {
        this.d = list;
    }

    public void setParentHandlerItem(HandlerItem handlerItem) {
        this.c = handlerItem;
    }

    public void setAccordionMultilevelListener(AccordionMultilevelListener accordionMultilevelListener) {
        if (accordionMultilevelListener != null) {
            this.e = accordionMultilevelListener;
        }
    }

    public boolean onClickLabel() {
        if (this.e != null) {
            return this.e.onClickLabel(this);
        }
        return hasChildren();
    }

    public boolean onClickArrow() {
        if (this.e != null) {
            return this.e.onClickArrow(this);
        }
        return hasChildren();
    }

    public boolean onClickCheckBox() {
        if (this.e != null) {
            return this.e.onClickCheckBox(this);
        }
        return true;
    }

    public void onExpandItems() {
        if (this.e != null) {
            this.e.onExpandItems(this);
        }
    }

    public void onCollapseItems() {
        if (this.e != null) {
            this.e.onCollapseItems(this);
        }
    }

    public void onCheckItem() {
        new Thread(new Runnable() {
            public void run() {
                HandlerItem.this.b(this);
            }
        }).start();
        if (this.e != null) {
            this.e.onCheckItem(this);
        }
    }

    public void onUnCheckItem() {
        new Thread(new Runnable() {
            public void run() {
                HandlerItem.this.e();
            }
        }).start();
        if (this.e != null) {
            this.e.onUnCheckItem(this);
        }
    }

    /* access modifiers changed from: private */
    public void b(HandlerItem handlerItem) {
        handlerItem.a.setSelected(true);
        handlerItem.b.setStateCheckBox(true);
        a(true, handlerItem.getChildren());
        c(handlerItem.c);
    }

    /* access modifiers changed from: private */
    public void e() {
        this.a.setSelected(false);
        this.b.setStateCheckBox(false);
        a(false, getChildren());
        c(this.c);
    }

    private TypeStateCheck a(List<ItemState> list) {
        int size = list.size();
        int i = 0;
        int i2 = 0;
        for (ItemState itemState : this.a.getChildren()) {
            if (itemState.isSelected()) {
                i++;
            } else if (TypeStateCheck.CHECK_PARTIAL.equals(itemState.getTypeStateCheckBox())) {
                i2++;
            }
        }
        if (size == i) {
            return TypeStateCheck.CHECK_ALL;
        }
        if (i == 0 && i2 == 0) {
            return TypeStateCheck.UNCHECK;
        }
        return TypeStateCheck.CHECK_PARTIAL;
    }

    private void a(boolean z, List<HandlerItem> list) {
        if (list != null) {
            for (HandlerItem handlerItem : list) {
                handlerItem.a.setSelected(z);
                handlerItem.b.setStateCheckBox(z);
                if (handlerItem.hasChildren()) {
                    a(z, handlerItem.getChildren());
                }
            }
        }
    }

    private void c(HandlerItem handlerItem) {
        if (handlerItem != null) {
            a(handlerItem, handlerItem.b(handlerItem.a.getChildren()));
            if (handlerItem.getParent() != null) {
                c(handlerItem.getParent());
            }
        }
    }

    private void a(HandlerItem handlerItem, TypeStateCheck typeStateCheck) {
        if (typeStateCheck.equals(TypeStateCheck.CHECK_ALL)) {
            handlerItem.a.setSelected(true);
        } else if (typeStateCheck.equals(TypeStateCheck.UNCHECK)) {
            handlerItem.a.setSelected(false);
        } else {
            handlerItem.a.setSelected(false);
        }
        handlerItem.a.setTypeStateCheck(typeStateCheck);
        handlerItem.b.setStateCheckBox(typeStateCheck);
    }

    private TypeStateCheck b(List<ItemState> list) {
        if (list != null && list.size() > 0) {
            return a(list);
        }
        if (this.c == null || !this.c.a.isSelected()) {
            return TypeStateCheck.UNCHECK;
        }
        return TypeStateCheck.CHECK_ALL;
    }

    public void updateStateUpToDown() {
        d(this);
    }

    private void d(HandlerItem handlerItem) {
        if (handlerItem != null) {
            handlerItem.b.setStateCheckBox(handlerItem.f);
            if (handlerItem.hasChildren()) {
                for (HandlerItem d2 : handlerItem.d) {
                    d(d2);
                }
            }
        }
    }

    public boolean isCheckedFromView() {
        return this.b.isChecked();
    }
}
