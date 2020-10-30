package com.jmperezra.accordion_multilevel.items;

import com.jmperezra.accordion_multilevel.commons.TypeStateCheck;
import com.jmperezra.accordion_multilevel.items.styles.StyleItem;
import java.util.List;

public interface ItemState {
    List<ItemState> getChildren();

    Integer getId();

    String getLabel();

    StyleItem getStyleItem();

    TypeStateCheck getTypeStateCheckBox();

    int getVisibilityCheckBox();

    Boolean isExpand();

    boolean isInitExpanded();

    boolean isSelectable();

    boolean isSelected();

    void setSelected(boolean z);

    void setTypeStateCheck(TypeStateCheck typeStateCheck);
}
