package com.ocado.basket;

import java.io.IOException;
import java.util.*;


public class BasketSplitter {
    public BasketSplitter(String absolutePathToConfigFile)
    {
        this.absolutePathToConfigFile = absolutePathToConfigFile;
    }

    /**
     * Optimally splits items from customer's order between their corresponding delivery methods.
     *
     * @param items List of items` names included in the customer's basket.
     * @return HashMap with optimal assignment of items between available delivery methods where delivery method's name is a key and list of items' names assigned to it is a value.
     * @throws IOException When loading of configuration JSON file failed.
     * */
    public Map<String, List<String>> split(List<String> items) throws IOException {
        Basket basket = new Basket(items, this.absolutePathToConfigFile);
        Map<String, List<String>> solution = new HashMap<>();

        // Algorithm takes a method that matches maximal number of items in basket and assign all matching
        // items to it. Items that have been assigned to this method aren't in the basket anymore.
        // Then algorithm again takes method that matches maximal number of items in basket and loops until
        // the basket is empty.
        while (!basket.isEmpty())
        {
            DeliveryMethod currentDeliveryMethod = Collections.max(
                    basket.getPossibleDeliveryMethods(),
                    (o1, o2) -> o1.getNumberOfMatchingItemsInBasket() - o2.getNumberOfMatchingItemsInBasket()
            );

            Iterator<Item> basketItemsIterator = basket.getItemsFromBasket().iterator();
            while (basketItemsIterator.hasNext())
            {
                Item itemFromBasket = basketItemsIterator.next();
                if (!itemFromBasket.canBeDeliveredWith(currentDeliveryMethod))
                    continue;
                itemFromBasket.chooseDeliveryMethod(currentDeliveryMethod);
                basketItemsIterator.remove();
            }
        }

        for (DeliveryMethod method : basket.getPossibleDeliveryMethods())
            if (method.getNumberOfAssignedItems() > 0)
                solution.put(method.getName(), method.getAssignedItemsNames());

        return solution;
    }

    private String absolutePathToConfigFile;
}
