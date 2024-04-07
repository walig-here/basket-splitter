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
            assertEquals(optimalSolution, solution);
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
            assertEquals(optimalSolution, solution);
        } catch (IOException e) {
            fail("Loading of configuration file failed!");
        }
    }

    @Test(expected = IOException.class)
    public void split_basketWithInvalidItem() throws IOException {
        List<String> itemsNames = Arrays.asList(
                "Chocolate - Unsweetened",
                "Bagel - Whole White Sesame",
                "Juice - Ocean Spray Cranberry",
                "Cabbage - Nappa",
                "V8 Splash Strawberry Banana",
                "Fudge - Chocolate Fudge",
                "Mix - Cocktail Ice Cream",
                "Haggis",
                "Cookies - Englishbay Wht",
                "Nut - Almond, Blanched, Whole",
                "Pineapple - Canned, Rings",
                "this-item-does-not-exist");

        BasketSplitter basketSplitter = new BasketSplitter(this.getPathToConfigFile());
        basketSplitter.split(itemsNames);
    }

    @Test(expected = IOException.class)
    public void split_invalidConfigFile() throws IOException {
        List<String> itemsNames = Arrays.asList(
                "Chocolate - Unsweetened",
                "Bagel - Whole White Sesame",
                "Juice - Ocean Spray Cranberry",
                "Cabbage - Nappa",
                "V8 Splash Strawberry Banana",
                "Fudge - Chocolate Fudge",
                "Mix - Cocktail Ice Cream",
                "Haggis",
                "Cookies - Englishbay Wht",
                "Nut - Almond, Blanched, Whole",
                "Pineapple - Canned, Rings");

        BasketSplitter basketSplitter = new BasketSplitter("invalid-path");
        basketSplitter.split(itemsNames);
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
