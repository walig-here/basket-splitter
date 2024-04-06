package com.ocado.basket;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeliveryMethodTest
{

    @Test
    public void testTestEquals() {
        DeliveryMethod method1 = new DeliveryMethod("delivery-method-1");
        DeliveryMethod method2 = new DeliveryMethod("delivery-method-2");
        DeliveryMethod method1Copy = new DeliveryMethod("delivery-method-1");

        assertEquals(
                "Methods " + method1.getName() + " & " + method1Copy.getName() + " should be equal!",
                method1, method1Copy
        );
        assertNotEquals(
                "Methods " + method1.getName() + " & " + method2.getName() + "shouldn't be equal!",
                method1, method2
        );
    }
}
