package com.ocado.basket;

import java.util.List;

public class Item {

    Item(String name, List<String> deliveryMethodsNames)
    {
        // TODO: 06.04.2024
        throw new UnsupportedOperationException();
    }

    void setCurrentDeliveryMethod(DeliveryMethod method)
    {
        // TODO: 06.04.2024  
        throw new UnsupportedOperationException();
    }

    String getName()
    {
        // TODO: 06.04.2024  
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj)
    {
        // TODO: 06.04.2024
        throw new UnsupportedOperationException();
    }
    
    private boolean canBeDeliveredWith(DeliveryMethod method)
    {
        // TODO: 06.04.2024  
        throw new UnsupportedOperationException();
    }

    private List<String> possibleDeliveryMethodsNames;
    private String name;
    private DeliveryMethod currentDeliveryMethod;
}
