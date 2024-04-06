package com.ocado.basket;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BasketTest {

    @Test
    public void createBasket_existingFileValidItemNames() throws URISyntaxException {
        DeliveryMethod pickUpPoint = new DeliveryMethod("Pick-up point");
        DeliveryMethod parcelLocker = new DeliveryMethod("Parcel locker");
        DeliveryMethod inStorePickUp = new DeliveryMethod("In-store pick-up");
        DeliveryMethod sameDayDelivery = new DeliveryMethod("Same day delivery");
        DeliveryMethod courier = new DeliveryMethod("Courier");
        DeliveryMethod expressCollection = new DeliveryMethod("Express Collection");
        DeliveryMethod mailboxDelivery = new DeliveryMethod("Mailbox delivery");
        DeliveryMethod nextDayShipping = new DeliveryMethod("Next day shipping");

        List<Item> items = new ArrayList<>(Arrays.asList(
                new Item("Cookies Oatmeal Raisin", Arrays.asList(pickUpPoint, parcelLocker)),
                new Item("Sole - Dover, Whole, Fresh", Arrays.asList(inStorePickUp)),
                new Item("Chocolate - Unsweetened", Arrays.asList(inStorePickUp, parcelLocker, sameDayDelivery, pickUpPoint, courier, expressCollection, mailboxDelivery, nextDayShipping))
        ));

        URL pathToConfigFile = getClass().getClassLoader().getResource("config.json");
        File configFile = Paths.get(pathToConfigFile.toURI()).toFile();

        List<DeliveryMethod> deliveryMethods = Arrays.asList(pickUpPoint, parcelLocker, inStorePickUp, sameDayDelivery, pickUpPoint, courier, expressCollection, mailboxDelivery, nextDayShipping);

        try {
            Basket basket = new Basket(
                    Arrays.asList(
                            items.get(0).getName(),
                            items.get(1).getName(),
                            items.get(2).getName()
                    ),
                    configFile.getAbsolutePath()
            );

            assertEquals(
                    "Not enough items in basket!",
                    basket.getItemsFromBasket().size(), items.size()
            );
            for (Item item : items)
            {
                int itemIndex = basket.getItemsFromBasket().indexOf(item);
                assertNotEquals(
                        "Item " + item.getName() + " should be in basket!",
                        itemIndex,
                        -1
                );
                assertEquals(
                        "Item" + item.getName() + " do not have enough delivery methods!",
                        item.getPossibleDeliveryMethods(),
                        basket.getItemsFromBasket().get(itemIndex).getPossibleDeliveryMethods()
                );
            }

            assertEquals(
                    "Not enough delivery methods detected!",
                    basket.getPossibleDeliveryMethods().size(), 8
            );
            for (DeliveryMethod method : deliveryMethods)
            {
                assertTrue(
                        "Method " + method.getName() + " should be possible!",
                        basket.getPossibleDeliveryMethods().contains(method)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("There shouldn't be an exception!");
        }
    }

}