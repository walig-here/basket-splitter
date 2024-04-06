package com.ocado.basket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Basket {

    Basket(List<String> itemsNames, String absolutePathToConfigFile) throws IOException
    {
        this.possibleDeliveryMethods = new ArrayList<>();
        this.itemsInBasket = new ArrayList<>();

        byte[] fileContent = Files.readAllBytes(Paths.get(absolutePathToConfigFile));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode configFileTree = objectMapper.readTree(fileContent);

        for (String itemName : itemsNames)
        {
            List<String> itemDeliveryMethodsNames = objectMapper.readValue(
                    configFileTree.path(itemName).toString(),
                    new TypeReference<List<String>>() {}
            );
            List<DeliveryMethod> itemDeliveryMethods = new ArrayList<>();

            for (String itemDeliveryMethodName : itemDeliveryMethodsNames)
            {
                DeliveryMethod method = new DeliveryMethod(itemDeliveryMethodName);
                int methodIndex = this.possibleDeliveryMethods.indexOf(method);

                // method is new and should be added to the list of methods for that basket
                if ( methodIndex == -1 )
                {
                    itemDeliveryMethods.add(method);
                    method.incrementNumberOfMatchingItemsInBasket();
                    this.possibleDeliveryMethods.add(method);
                }
                // method has been already added to the list of methods -> there's no need to add a duplicate
                else
                {
                    itemDeliveryMethods.add(this.possibleDeliveryMethods.get(methodIndex));
                    this.possibleDeliveryMethods.get(methodIndex).incrementNumberOfMatchingItemsInBasket();
                }
            }
            this.itemsInBasket.add(new Item(itemName, itemDeliveryMethods));
        }
    }

    List<DeliveryMethod> getPossibleDeliveryMethods()
    {
        return this.possibleDeliveryMethods;
    }

    List<Item> getItemsFromBasket()
    {
        return this.itemsInBasket;
    }

    boolean isEmpty()
    {
        return this.itemsInBasket.isEmpty();
    }

    private List<Item> itemsInBasket;
    private List<DeliveryMethod> possibleDeliveryMethods;
}
