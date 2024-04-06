package com.ocado.basket;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void testEquals() {
        Item item1 = new Item("item-1", new ArrayList<DeliveryMethod>());
        Item item2 = new Item("item-2", new ArrayList<DeliveryMethod>());
        Item item1Copy = new Item("item-1", new ArrayList<DeliveryMethod>());

        assertEquals(
                "Items " + item1.getName() + " & " + item1Copy.getName() + " should be equal!",
                item1, item1Copy
        );
        assertNotEquals(
                "Methods " + item1.getName() + " & " + item2.getName() + "shouldn't be equal!",
                item1, item2
        );
    }

    @Test
    public void canBeDeliveredWith_validMethod() {
        DeliveryMethod deliveryMethod1 = new DeliveryMethod("method-1");
        DeliveryMethod deliveryMethod2 = new DeliveryMethod("method-2");
        Item item = new Item("item-1", Arrays.asList(deliveryMethod1, deliveryMethod2));

        assertTrue(
                "Item " + item.getName() + " can be delivered with method " + deliveryMethod2.getName(),
                item.canBeDeliveredWith(deliveryMethod2)
        );
    }

    @Test
    public void canBeDeliveredWith_invalidMethod() {
        DeliveryMethod deliveryMethod1 = new DeliveryMethod("method-1");
        DeliveryMethod deliveryMethod2 = new DeliveryMethod("method-2");
        DeliveryMethod deliveryMethod3 = new DeliveryMethod("method-3");
        Item item = new Item("item-1", Arrays.asList(deliveryMethod1, deliveryMethod2));

        assertFalse(
                "Item " + item.getName() + " can't be delivered with method " + deliveryMethod3.getName(),
                item.canBeDeliveredWith(deliveryMethod3)
        );
    }

    @Test
    public void setCurrentDeliveryMethod_methodValid() {
        DeliveryMethod method1 = new DeliveryMethod("method-1");
        DeliveryMethod method2 = new DeliveryMethod("method-2");
        Item item = new Item("item-1", Arrays.asList(method2, method1));

        item.chooseDeliveryMethod(method1);
        assertEquals(item.getCurrentDeliveryMethod(), method1);

        item.chooseDeliveryMethod(method2);
        assertEquals(item.getCurrentDeliveryMethod(), method1);
    }

    @Test
    public void setCurrentDeliveryMethod_methodInvalid() {
        DeliveryMethod method1 = new DeliveryMethod("method-1");
        DeliveryMethod method2 = new DeliveryMethod("method-2");
        DeliveryMethod method3 = new DeliveryMethod("method-3");
        Item item = new Item("item-1", Arrays.asList(method2, method1));

        item.chooseDeliveryMethod(method3);
        assertNull(item.getCurrentDeliveryMethod());

        item.chooseDeliveryMethod(method1);
        item.chooseDeliveryMethod(method3);
        assertEquals(item.getCurrentDeliveryMethod(), method1);
    }
}