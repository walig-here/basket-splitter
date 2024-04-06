package com.ocado.basket;

import java.util.List;

public class Basket {

    Basket(List<String> itemsNames, String absolutePathToConfigFile)
    {
        // TODO: 06.04.2024
        throw new UnsupportedOperationException();
    }

    List<DeliveryMethod> getPossibleDeliveryMethods()
    {
        // TODO: 06.04.2024
        throw new UnsupportedOperationException();
    }

    void pullItemOutOfBasket(Item item)
    {
        // TODO: 06.04.2024
        throw new UnsupportedOperationException();
    }

    List<Item> getItemsFromBasket()
    {
        // TODO: 06.04.2024
        throw new UnsupportedOperationException();
    }

    private List<Item> itemsInBasket;
    private List<DeliveryMethod> possibleDeliveryMethods;
}
