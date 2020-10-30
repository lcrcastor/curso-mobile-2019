package com.jmperezra.accordion_multilevel.listeners;

public interface ItemViewListener {
    void onCheckItem();

    boolean onClickArrow();

    boolean onClickCheckBox();

    boolean onClickLabel();

    void onCollapseItems();

    void onExpandItems();

    void onUnCheckItem();
}
