package machine;

import java.util.Scanner;

public class CoffeeMachine implements CoffeeMachineAbilities {

    public static void main(String[] args) {

        // the following line returns syntax error because we did not specified witch is the coffee machine that has this quantities
        //quantities = new int[]{water, milk, beans, dispCups, money};
        CoffeeMachine machine = new CoffeeMachine();

        Scanner sc = new Scanner(System.in);

        //cat timp vrea utilizatorul

        while(machine.isRunning()) {
            // se vor executa comenzi
            machine.executeAction(sc.nextLine());
        }
        sc.close();
    }

    private enum State{
        MAINMENU{
            @Override
            public void moveOn(String action, CoffeeMachine machine) {
               machine.processMainMenuAction(action);
            }
        }, BUY{
            @Override
            public void moveOn(String action, CoffeeMachine machine) {
                machine.buy(action);
            }
        }, FILL_WATER{
            @Override
            public void moveOn(String action, CoffeeMachine machine) {
                machine.processFillWater(action);
            }
        }, FILL_MILK{
            @Override
            public void moveOn(String action, CoffeeMachine machine) {
                machine.processFillMilk(action);
            }
        }, FILL_BEANS{
            @Override
            public void moveOn(String action, CoffeeMachine machine) {
                machine.processFillBeans(action);
            }
        }, FILL_CUPS{
            @Override
            public void moveOn(String action, CoffeeMachine machine) {
                machine.processFillCups(action);
            }
        }, EXIT {
            @Override
            public void moveOn(String action, CoffeeMachine machine) {

            }
        };
        public abstract void moveOn(String action, CoffeeMachine machine);
    }

    public enum Quantity {
        WATER, MILK, BEANS, CUPS, MONEY
    }

    private  enum CoffeeType {
        ESPRESSO(250, 0, 16, 1, 4),
        LATTE(350, 75, 20, 1, 7),
        CAPPUCCINO(200, 100, 12, 1, 6);

        private final int waterRequired;
        private final int milkRequired;
        private final int beansRequired;
        private final int cupsRequired;
        private final int price;

        CoffeeType(int waterRequired, int milkRequired, int beansRequired, int cupsRequired, int price) {
            this.waterRequired = waterRequired;
            this.milkRequired = milkRequired;
            this.beansRequired = beansRequired;
            this.cupsRequired = cupsRequired;
            this.price = price;
        }
    }
      // quantities array must be non static in order to have different quantities for each coffee machine
    private final int[] quantities;
    private State state;
    private String waterToFill = "0";
    private String milkToFill = "0";
    private String beansToFill = "0";

    public CoffeeMachine(){
        int water = 400;
        int milk = 540;
        int beans = 120;
        int cups = 9;
        int money = 550;

        quantities = new int[5];
        quantities[Quantity.WATER.ordinal()] = water;
        quantities[Quantity.MILK.ordinal()] = milk;
        quantities[Quantity.MONEY.ordinal()] = money;
        quantities[Quantity.BEANS.ordinal()] = beans;
        quantities[Quantity.CUPS.ordinal()] = cups;

        state = State.MAINMENU;
        UI.displayMainMenu();
    }

    public void executeAction(String action)
    {
        state.moveOn(action, this);
    }

    private void processFillCups(String action) {
        fill(waterToFill, milkToFill, beansToFill, action);
        state = State.MAINMENU;
        UI.displayMainMenu();
    }

    private void processFillBeans(String action) {
        beansToFill = action;
        state = State.FILL_CUPS;
        UI.displayFillCups();
    }

    private void processFillMilk(String action) {
        milkToFill = action;
        state = State.FILL_BEANS;
        UI.displayFillBeans();
    }

    private void processFillWater(String action) {
        waterToFill = action;
        state = State.FILL_MILK;
        UI.displayFillMilk();
    }

    private void processMainMenuAction(String action) {
        switch (action)
        {
            case "buy":
                state = State.BUY;
                UI.displayBuyMenu();
                break;
            case "fill":
                state = State.FILL_WATER;
                UI.displayFillWater();
                break;
            case "take":
                take();
                UI.displayMainMenu();
                break;
            case "remaining":
                UI.displayRemaining(quantities);
                UI.displayMainMenu();
                break;
            case "exit":
                state = State.EXIT;
                break;
            default:
                UI.displayUnknownAction();
                UI.displayMainMenu();
                break;
        }
    }

    private void buy(String buyAction)
    {
        switch (buyAction){
                case "1":
                    makeCoffee(CoffeeType.ESPRESSO);
                    break;
                case "2":
                    makeCoffee(CoffeeType.LATTE);
                    break;
                case "3":
                    makeCoffee(CoffeeType.CAPPUCCINO);
                    break;
                case "back":
                    break;
                default:
                    UI.displayUnknownAction();
                    break;
            }
        state = State.MAINMENU;
        UI.displayMainMenu();
    }

    private void makeCoffee(CoffeeType variant){
        int [] cofeeTypeQuantity = {variant.waterRequired, variant.milkRequired, variant.beansRequired, variant.cupsRequired, variant.price};
        if(checkIfEnoughQuantity(cofeeTypeQuantity)){
            for(int i=0; i<quantities.length-1; i++){
                quantities[i] -= cofeeTypeQuantity[i];
            }
            quantities[Quantity.MONEY.ordinal()] += cofeeTypeQuantity[Quantity.MONEY.ordinal()];
           UI.displayCoffeeIsDone();
        }
    }

    private void fill(String water, String milk, String beans, String cups)
    {
        quantities[Quantity.WATER.ordinal()] += Integer.parseInt(water);
        quantities[Quantity.MILK.ordinal()] += Integer.parseInt(milk);
        quantities[Quantity.BEANS.ordinal()] += Integer.parseInt(beans);
        quantities[Quantity.CUPS.ordinal()] += Integer.parseInt(cups);
    }

    private void take()
    {
        UI.displayIGaveYouMoney(quantities);
        quantities[Quantity.MONEY.ordinal()] = 0;

    }

    private boolean checkIfEnoughQuantity(int[] coffeeTypeQuantity){
        boolean ans = false;
        if(quantities[Quantity.WATER.ordinal()] < coffeeTypeQuantity[Quantity.WATER.ordinal()]){
            UI.displayNotEnoughWater();
        }
        else if(quantities[Quantity.MILK.ordinal()] < coffeeTypeQuantity[Quantity.MILK.ordinal()]) {
            UI.displayNotEnoughMilk();
        }
        else if(quantities[Quantity.BEANS.ordinal()] < coffeeTypeQuantity[Quantity.BEANS.ordinal()]) {
            UI.displayNotEnoughBeans();
        }
        else if(quantities[Quantity.CUPS.ordinal()] < coffeeTypeQuantity[Quantity.CUPS.ordinal()]) {
            UI.displayNotEnoughCups();
        }
        else{
            ans = true;
        }
        return ans;
    }

    private boolean isRunning()
    {
        return state != State.EXIT;
    }
}