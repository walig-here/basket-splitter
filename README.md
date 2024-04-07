# Dawid_Waligórski_Java_Wrocław

Java library that solves optimization problems by splitting items from a customer's order among multiple delivery methods. The project was commissioned by *Ocado* as a recruitment task.

## Analysys

### Problem description

Each product in the shop's offer requires different delivery methods. Thus it's crucial to find an algorithm that would analyse each customer's order and assign its elements to their proper delivery methods. What's more, it needs to be done as optimally. Order **elements should be split among as few delivery methods as possible**. Additionally, **number of items assigned to the method with the maximal amount of assignments should be as big as possible**.

### Input data

1. Customer's order (basket) in the form of items' names list. Example basket form:

```json
[ "product-name-1", "product-name-2", "product-name-3" ]
```

2. Configuration file in JSON format that contains a list of all products in the shop's offer and defines lists of names of their proper delivery methods. Implemented **library would require an absolute path to this file**. Example configuration file contents:

```json
{
  "product-name-1": [ "delivery-method-1", "delivery-method-2" ],
  "product-name-2": [ "delivery-method-1" ],
  "product-name-3": [ "delivery-method-2" ],
  "product-name-4": [ "delivery-method-1", "delivery-method-3" ]
}
```

### Required output

A map that would represent the assignment of the order's products between delivery methods. **Delivery method name should be a key** and **list of products' names assigned to it should be a value**. Example output:

```json
{
  "delivery-method-1": [ "product-name-1", "product-name-2" ],
  "delivery-method-2": [ "product-name-3" ]  
}
```

### Constraints

* Shop's offer would contain at most 1000 products
* There would be at most 10 different delivery methods
* Customer's basket would contain at most 100 different products

## Implementation & Tests

### Implementation details and structure
This library is a *Maven* project implemented with *Java 17* which was enriched by an external [*Jackson*](https://github.com/FasterXML/jackson) library (for easier parsing of JSON files). The source code is available in the directory `source-code\src\main\java`. The class structure of the implemented library is shown in the figure below.

![](./gfx/splitter.png)

### Tests
Most non-private methods of implemented classes were tested with *JUnit 4*. Tests' code is available in the directory `source-code\src\test\java`.

## Deployment

This library is available as a non-executable JAR file named `basket-splitter.jar`. It already includes all required dependencies. 

## Usage example

To use the `basket-splitter` library it's required to add `basket-splitter.jar` as a dependency to your Java project. From that point, it's possible to create instances of the `BasketSplitter` class. While creating an instance of that class it's required to pass a `String` with an absolute path to the configuration JSON file.

```java
/* ... */

import com.ocado.basket.BasketSplitter;

public class App 
{
    /* ... */

    public static void main( String[] args )
    {
        BasketSplitter basketSplitter = new BasketSplitter("absolutePathtToConfigFile");
    }

    /* ... */
}
```

With the new `BasketSplitter` instance created it's now possible to perform splitting of customer's orders with the `split()` method. As an input parameter, it takes a collection of order elements' names in the form of `List<String>`. The method returns a `HashMap<String, List<String>>` that describes an assignment of the order's elements to delivery methods where the delivery method name is a key and the list of elements' names assigned to it is a value. The `split()` method could also throw an `IOException` when loading and parsing the JSON configuration file has failed.

```java
/* ... */

Map<String, List<String>> splittedBasket = basketSplitter.split(Arrays.asList(
                "Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daisies",
                "Fond - Chocolate",
                "Cookies - Englishbay Wht"));
System.out.println(splittedBasket);

/* ... */
```

The solution (after additional formating) given by the `basket-splitter` library for this particular example is:
```
{
  Mailbox delivery=[
    Fond - Chocolate
  ],
  Courier=[
    Cocoa Butter,
    Tart - Raisin And Pecan,
    Table Cloth 54x72 White,
    Flower - Daisies,
    Cookies - Englishbay Wht
  ]
}
```
