package com.ocado.basket;

import java.util.List;

public class Item {

    Item(String name, List<DeliveryMethod> deliveryMethods)
    {
        this.name = name;
        this.currentDeliveryMethod = null;
        this.possibleDeliveryMethods = deliveryMethods;
    }

    void chooseDeliveryMethod(DeliveryMethod method)
    {
        // method should be suitable for that instance of an item and item could be assigned only once
        if (this.currentDeliveryMethod != null || !this.canBeDeliveredWith(method))
            return;

        this.currentDeliveryMethod = method;
        this.currentDeliveryMethod.assignItem(this);

        // item is automatically pulled out of basket
        for (DeliveryMethod possibleMethod : this.possibleDeliveryMethods)
            if(!possibleMethod.equals(this.currentDeliveryMethod))
                possibleMethod.decrementNumberOfMatchingItemsInBasket();
    }

    DeliveryMethod getCurrentDeliveryMethod()
    {
        return this.currentDeliveryMethod;
    }

    String getName()
    {
        return this.name;
    }

    List<DeliveryMethod> getPossibleDeliveryMethods()
    {
        return this.possibleDeliveryMethods;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Item)
            return this.name.equals(((Item) obj).name);
        return false;
    }
    
    boolean canBeDeliveredWith(DeliveryMethod method)
    {
        return this.possibleDeliveryMethods.contains(method);
    }

    private List<DeliveryMethod> possibleDeliveryMethods;
    private String name;
    private DeliveryMethod currentDeliveryMethod;
}
