package com.ocado.basket;

import java.util.ArrayList;
import java.util.List;

public class DeliveryMethod {

    DeliveryMethod(String name)
    {
        this.name = name;
        this.assignedItems = new ArrayList<>();
        this.numberOfMatchingItemsInBasket = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DeliveryMethod)
            return this.name.equals(((DeliveryMethod) obj).name);
        return false;
    }

    void incrementNumberOfMatchingItemsInBasket()
    {
        this.numberOfMatchingItemsInBasket++;
    }
    void decrementNumberOfMatchingItemsInBasket()
    {
        this.numberOfMatchingItemsInBasket--;
    }

    int getNumberOfAssignedItems()
    {
        return this.assignedItems.size();
    }

    int getNumberOfMatchingItemsInBasket()
    {
        return this.numberOfMatchingItemsInBasket;
    }

    String getName()
    {
        return this.name;
    }

    void assignItem(Item item)
    {
        if (this.assignedItems.contains(item))
            return;
        this.assignedItems.add(item);
        this.decrementNumberOfMatchingItemsInBasket();
    }

    List<String> getAssignedItemsNames(){
        List<String> assignedItemsNames = new ArrayList<>();
        for (Item assignedItem : this.assignedItems)
            assignedItemsNames.add(assignedItem.getName());
        return assignedItemsNames;
    }

    private String name;
    private List<Item> assignedItems;
    private int numberOfMatchingItemsInBasket;
}
