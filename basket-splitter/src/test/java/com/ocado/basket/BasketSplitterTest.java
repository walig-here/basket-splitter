package com.ocado.basket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class BasketSplitterTest
{
    @Test
    public void split_basket1() {
        List<String> itemsNames = this.loadBasketFromJson("basket-1.json");
        if (itemsNames == null)
            fail("Basket 1 does not exist!");
        Map<String, List<String>> optimalSolution = new HashMap<>();
        optimalSolution.put(
                "Courier",
                Arrays.asList(
                        "Cocoa Butter",
                        "Tart - Raisin And Pecan",
                        "Table Cloth 54x72 White",
                        "Flower - Daisies",
                        "Cookies - Englishbay Wht"
                )
        );
        optimalSolution.put(
                "Mailbox delivery",
                Arrays.asList(
                        "Fond - Chocolate"
                )
        );

        try {
            BasketSplitter basketSplitter = new BasketSplitter(this.getPathToConfigFile());
            Map<String, List<String>> solution = basketSplitter.split(itemsNames);
            for (Map.Entry<String, List<String>> optimalSolutionEntry : optimalSolution.entrySet())
            {
                assertTrue(
                        "Solution lack key " + optimalSolutionEntry.getKey(),
                        solution.containsKey(optimalSolutionEntry.getKey())
                );
                assertEquals(
                        "Key " + optimalSolutionEntry.getKey() + " has invalid value",
                        solution.get(optimalSolutionEntry.getKey()),
                        optimalSolutionEntry.getValue()
                );
            }
        } catch (IOException e) {
            fail("Loading of configuration file failed!");
        }
    }

    @Test
    public void split_basket2() {
        List<String> itemsNames = this.loadBasketFromJson("basket-2.json");
        if (itemsNames == null)
            fail("Basket 2 does not exist!");
        Map<String, List<String>> optimalSolution = new HashMap<>();
        optimalSolution.put(
                "Express Collection",
                Arrays.asList(
                        "Fond - Chocolate",
                        "Chocolate - Unsweetened",
                        "Nut - Almond, Blanched, Whole",
                        "Haggis",
                        "Mushroom - Porcini Frozen",
                        "Longan",
                        "Bag Clear 10 Lb",
                        "Nantucket - Pomegranate Pear",
                        "Puree - Strawberry",
                        "Apples - Spartan",
                        "Cabbage - Nappa",
                        "Bagel - Whole White Sesame",
                        "Tea - Apple Green Tea"
                )
        );
        optimalSolution.put(
                "Same day delivery",
                Arrays.asList(
                        "Sauce - Mint",
                        "Numi - Assorted Teas",
                        "Garlic - Peeled"
                )
        );
        optimalSolution.put(
                "Courier",
                Arrays.asList(
                        "Cake - Miini Cheesecake Cherry"
                )
        );


        try {
            BasketSplitter basketSplitter = new BasketSplitter(this.getPathToConfigFile());
            Map<String, List<String>> solution = basketSplitter.split(itemsNames);

            for (Map.Entry<String, List<String>> optimalSolutionEntry : optimalSolution.entrySet())
            {
                assertTrue(
                        "Solution lack key " + optimalSolutionEntry.getKey(),
                        solution.containsKey(optimalSolutionEntry.getKey())
                );
                assertEquals(
                        "Key " + optimalSolutionEntry.getKey() + " has invalid value",
                        solution.get(optimalSolutionEntry.getKey()),
                        optimalSolutionEntry.getValue()
                );
            }
        } catch (IOException e) {
            fail("Loading of configuration file failed!");
        }
    }

    private List<String> loadBasketFromJson(String basketFileName)
    {
        URL pathToBasket = getClass().getClassLoader().getResource(basketFileName);
        File basketFile = null;
        try {
            basketFile = Paths.get(pathToBasket.toURI()).toFile();
            byte[] fileContent = Files.readAllBytes(Paths.get(basketFile.getAbsolutePath()));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(fileContent, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getPathToConfigFile(){
        URL pathToConfig = getClass().getClassLoader().getResource("config.json");
        try {
            return Paths.get(pathToConfig.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            return "";
        }
    }
}
