package com.jmperezra.accordion_multilevel.listeners;

import com.jmperezra.accordion_multilevel.items.HandlerItem;
import java.util.List;

public interface AccordionMultilevelListener {
    void onCheckItem(HandlerItem handlerItem);

    boolean onClickArrow(HandlerItem handlerItem);

    boolean onClickCheckBox(HandlerItem handlerItem);

    boolean onClickLabel(HandlerItem handlerItem);

    void onCollapseItems(HandlerItem handlerItem);

    void onExpandItems(HandlerItem handlerItem);

    void onShowAccordionMultilevel(List<HandlerItem> list);

    void onShowItemAccordion(HandlerItem handlerItem);

    void onUnCheckItem(HandlerItem handlerItem);
}
