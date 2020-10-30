package com.jmperezra.accordion_multilevel.items;

import com.jmperezra.accordion_multilevel.commons.TypeStateCheck;
import com.jmperezra.accordion_multilevel.items.styles.StyleItem;

public abstract class BaseItemState implements ItemState {
    private Boolean isExpand = null;
    private boolean isInitExpanded = false;
    private boolean isSelecteable = true;
    private boolean isSelected = false;
    protected StyleItem styleItem = null;
    private TypeStateCheck typeStateCheck;
    private int visibilityCheckBox = 0;

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public boolean isInitExpanded() {
        return this.isInitExpanded;
    }

    public void setInitExpanded(boolean z) {
        this.isInitExpanded = z;
    }

    public boolean isSelectable() {
        return this.isSelecteable;
    }

    public void setSelecteable(boolean z) {
        this.isSelecteable = z;
    }

    public int getVisibilityCheckBox() {
        return this.visibilityCheckBox;
    }

    public void setVisibilityCheckBox(int i) {
        this.visibilityCheckBox = i;
    }

    public Boolean isExpand() {
        return this.isExpand;
    }

    public void setExpand(boolean z) {
        this.isExpand = Boolean.valueOf(z);
    }

    public StyleItem getStyleItem() {
        return this.styleItem;
    }

    public TypeStateCheck getTypeStateCheckBox() {
        return this.typeStateCheck;
    }

    public void setStyleItem(StyleItem styleItem2) {
        this.styleItem = styleItem2;
    }

    public void setTypeStateCheck(TypeStateCheck typeStateCheck2) {
        this.typeStateCheck = typeStateCheck2;
    }
}
