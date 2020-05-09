package machine;
 // high cohesion principle respected (entire output moved in a dedicated class)
public class UI {
    public static void displayMainMenu()
    {
        System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
    }

    public static void displayFillWater()
    {
        System.out.println("Write how many ml of water do you want to add:");
    }

    public static void displayFillMilk()
    {
        System.out.println("Write how many ml of milk do you want to add:");
    }

    public static void displayFillBeans()
    {
        System.out.println("Write how many disposable cups of coffee do you want to add:");
    }

    public static void displayFillCups()
    {
        System.out.println("Write how many ml of milk do you want to add:");
    }

    public static void displayBuyMenu()
    {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
    }

    public static void displayCoffeeIsDone()
    {
        System.out.println("I have enough resources, making you a coffee!");
    }
    public static void displayRemaining(int[] quantities)
    {
         System.out.println("\nThe coffee machine has:\n" +
                 quantities[CoffeeMachine.Quantity.WATER.ordinal()] + " of water\n" +
                 quantities[CoffeeMachine.Quantity.MILK.ordinal()] + " of milk\n" +
                 quantities[CoffeeMachine.Quantity.BEANS.ordinal()] + " of coffee beans\n" +
                 quantities[CoffeeMachine.Quantity.CUPS.ordinal()] + " of disposable cups\n$" +
                 quantities[CoffeeMachine.Quantity.MONEY.ordinal()] + " of money");
    }

    public static void displayUnknownAction(){
         System.out.println("Action not recognized, try again!");
    }

    public static void displayIGaveYouMoney(int[] quantities){
        System.out.println("I gave you $" + quantities[CoffeeMachine.Quantity.MONEY.ordinal()]);
    }

    public static void displayNotEnoughWater(){
        System.out.println("Sorry, not enough water!");
    }

    public static void displayNotEnoughMilk(){
         System.out.println("Sorry, not enough milk!");
    }

    public static void displayNotEnoughBeans(){
        System.out.println("Sorry, not enough coffee beans!");
    }

    public static void displayNotEnoughCups(){
        System.out.println("Sorry, not enough coffee disposable cups!");
    }


 }

